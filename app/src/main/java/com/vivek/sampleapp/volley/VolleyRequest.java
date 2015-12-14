//package com.vivek.sampleapp.volley;
//
//import com.android.volley.AuthFailureError;
//import com.android.volley.NetworkResponse;
//import com.android.volley.ParseError;
//import com.android.volley.Request;
//import com.android.volley.Response;
//import com.android.volley.VolleyLog;
//import com.android.volley.toolbox.HttpHeaderParser;
//import com.google.gson.Gson;
//import com.google.gson.JsonSyntaxException;
//
//import java.io.UnsupportedEncodingException;
//import java.lang.reflect.Type;
//import java.util.Map;
//import java.util.Objects;
//
///**
// * Created by vivek-pc on 12/5/2015.
// */
//public class VolleyRequest<T> extends Request<T> {
//
//
//    private final Gson gson = new Gson();
//    private final Type clazz;
//    private final Map<String, String> headers;
//    private Response.Listener<T> successListener;
//    private Response.ErrorListener errorListener;
//    private final Map<String, String> params;
//    protected static final String PROTOCOL_CHARSET = "utf-8";
//    private final String mRequestBody;
//
//    /** Content type for request. */
//    private static final String PROTOCOL_CONTENT_TYPE =
//            String.format("application/x-www-form-urlencoded; charset=%s", PROTOCOL_CHARSET);
//
//    public VolleyRequest(int method, String url,String requestBody, Map<String, String> headers,Map<String, String> params, Type clazz, Response.Listener successListener,Response.ErrorListener errorListener) {
//        super(method, url, errorListener);
//        this.clazz = clazz;
//        this.headers = headers;
//        this.successListener = successListener;
//        this.errorListener = errorListener;
//        this.params = params;
//        this.mRequestBody = requestBody;
//    }
//
//    @Override
//    public byte[] getBody() {
//        try {
//            return mRequestBody == null ? null : mRequestBody.getBytes(PROTOCOL_CHARSET);
//        } catch (UnsupportedEncodingException uee) {
//            VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s",
//                    mRequestBody, PROTOCOL_CHARSET);
//            return null;
//        }
//    }
//
//    @Override
//    public String getBodyContentType() {
//        return PROTOCOL_CONTENT_TYPE;
//    }
//
//    @Override
//    public Map<String, String> getParams() {
//        return params;
//    }
//
//    @Override
//    public Map<String, String> getHeaders() throws AuthFailureError {
//        return headers != null ? headers : super.getHeaders();
//    }
//
//    @Override
//    protected Response<T> parseNetworkResponse(NetworkResponse response) {
//         try {
//            String json = new String(
//                    response.data,
//                    HttpHeaderParser.parseCharset(response.headers));
//            return Response.success(
//                    (T)gson.fromJson(json, clazz),
//                    HttpHeaderParser.parseCacheHeaders(response));
//        } catch (UnsupportedEncodingException e) {
//            return Response.error(new ParseError(e));
//        } catch (JsonSyntaxException e) {
//            return Response.error(new ParseError(e));
//        }
//    }
//
//    @Override
//    protected void deliverResponse(T response) {
//        successListener.onResponse(response);
//    }
//
//}