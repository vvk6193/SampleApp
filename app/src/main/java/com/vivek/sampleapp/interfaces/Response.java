package com.vivek.sampleapp.interfaces;

/**
 * Created by v.vekariya on 12/10/2015.
 */
public class Response {

 public interface SuccessListener{
   public void onResponse(Object result);
 }

 public interface ErrorListener {
      public void onError();
 }

}
