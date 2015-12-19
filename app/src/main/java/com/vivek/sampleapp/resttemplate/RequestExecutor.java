package com.vivek.sampleapp.resttemplate;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.vivek.sampleapp.interfaces.Response;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Random;

/**
 * Created by v.vekariya on 12/10/2015.
 */
public class RequestExecutor {

    private static final int MESSAGE_POST_RESULT = 0x1;
    private static final int MESSAGE_POST_PROGRESS = 0x2;
//    private static RestTemplate restTemplate;
    private String serverAddress;
    private HttpMethod httpMethod;
    private Object requestBody;
    //    private HttpResponseCallback httpResponseCallback;
    private String authToken;
    private Response.SuccessListener successListener;
    private Response.ErrorListener errorListener;
    private Class clazz;
    private static InternalHandler sHandler;

    public RequestExecutor(String uri, HttpMethod httpMethod, Object requestBody, String authToken,
                           Response.SuccessListener successListener, Response.ErrorListener errorListener) {

        this.serverAddress = uri;
        this.httpMethod = httpMethod;
        this.requestBody = requestBody;
        this.authToken = authToken;
        this.successListener = successListener;
        this.errorListener = errorListener;

    }

    private final static String TAG = RequestExecutor.class.getSimpleName();

    private static Handler getHandler() {
        synchronized (RequestExecutor.class) {
            if (sHandler == null) {
                sHandler = new InternalHandler();
            }
            return sHandler;
        }
    }

    private Object postResult(Object result) {
        @SuppressWarnings("unchecked")
        Message message = getHandler().obtainMessage(MESSAGE_POST_RESULT,
                new ResultObject(successListener,result));
        message.sendToTarget();
        return result;
    }

    private static class InternalHandler extends Handler {
        public InternalHandler() {
            super(Looper.getMainLooper());
        }

        @SuppressWarnings({"unchecked", "RawUseOfParameterizedType"})
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_POST_RESULT:
                    // There is only one result
                    ResultObject result = (ResultObject) msg.obj;
                    result.successListener.onResponse(result.data);
                    break;
                case MESSAGE_POST_PROGRESS:
//                    result.mTask.onProgressUpdate(result.mData);
                    break;
            }
        }
    }

    private class ResultObject {
        private Response.SuccessListener successListener;
        public Object data;

        ResultObject(Response.SuccessListener successListener, Object data) {
            this.successListener = successListener;
            this.data = data;
        }
    }

    public void executeRequest() {
//        Log.d("vvk","executerequest");
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        // Random generator
        final String allowedCharacters = "0123456789qwertyuiopasdfghjklzxcvbnm";
        int sizeOfRandomString = 10;
        final Random random = new Random();
        final StringBuilder sb = new StringBuilder(sizeOfRandomString);
        for (int i = 0; i < sizeOfRandomString; ++i)
            sb.append(allowedCharacters.charAt(random.nextInt(allowedCharacters.length())));

        ResponseEntity<? extends Object> response = null;
        try {

            HttpHeaders requestHeaders = new HttpHeaders();
//            requestHeaders.add("CSRF-TOKEN", sb.toString());
//            requestHeaders.add("Cookie", "X-CSRF-TOKEN=" + sb.toString());
//            requestHeaders.add("X-AUTH-TOKEN", authToken);
            HttpEntity<?> requestEntity = new HttpEntity<Object>(requestBody, requestHeaders);
            response = restTemplate.exchange(serverAddress, httpMethod,
                    requestEntity, Object.class);
//            Log.d("vvk","before postresult");
            postResult(response.getBody());
        } catch (Exception e) {
            Log.e("vvk", e.toString());
            e.printStackTrace();
            errorListener.onError();
        }
    }

}
