package com.vivek.sampleapp.networktask;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class SendToServer extends AsyncTask<String, Void, ResponseEntity<? extends Object>> {
    private String serverAddress;
    private HttpMethod httpMethod;
    private Object message = null;
    private String TAG = "VopdPatient";
    private String authToken;
    private Context mContext;

    public SendToServer(
                        ) {
        this.serverAddress = "http://107.110.76.84:8080/vopd/patient";
        this.httpMethod = httpMethod;
        this.message = message;
//        mContext = context;
//        authToken = new AppPreference(mContext).getAuthTokend();
        Log.d(TAG, "Send request begun");
    }

    @Override
    protected ResponseEntity<? extends Object> doInBackground(String... params) {

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        // Random generator
//        final String allowedCharacters = "0123456789qwertyuiopasdfghjklzxcvbnm";
//        int sizeOfRandomString = 10;
//        final Random random = new Random();
//        final StringBuilder sb = new StringBuilder(sizeOfRandomString);
//        for (int i = 0; i < sizeOfRandomString; ++i)
//            sb.append(allowedCharacters.charAt(random.nextInt(allowedCharacters.length())));

        ResponseEntity<? extends Object> response = null;
        try {

            HttpHeaders requestHeaders = new HttpHeaders();
//            requestHeaders.add("CSRF-TOKEN", sb.toString());
//            requestHeaders.add("Cookie", "X-CSRF-TOKEN=" + sb.toString());
//            requestHeaders.add("X-AUTH-TOKEN", authToken);
            HttpEntity<?> requestEntity = new HttpEntity<Object>(message, requestHeaders);
            response = restTemplate.exchange(serverAddress, HttpMethod.GET,
                    requestEntity, Object.class);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            e.printStackTrace();
//            responseFail(message);
        }
        return response;
    }

    @Override
    protected void onPostExecute(ResponseEntity<? extends Object> result) {
        Log.d("vvk","success");
    }



}
