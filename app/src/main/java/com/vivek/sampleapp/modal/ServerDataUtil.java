///**
// * Copyright  2010 Samsung Electronics Co., Ltd. All rights reserved.
// *
// * Mobile Communication Division,
// * Digital Media & Communications Business, Samsung Electronics Co., Ltd.
// *
// * This software and its documentation are confidential and proprietary
// * information of Samsung Electronics Co., Ltd.  No part of the software and
// * documents may be copied, reproduced, transmitted, translated, or reduced to
// * any electronic medium or machine-readable form without the prior written
// * consent of Samsung Electronics.
// *
// * Samsung Electronics makes no representations with respect to the contents,
// * and assumes no responsibility for any errors that might appear in the
// * software and documents. This publication and the contents hereof are subject
// * to change without notice.
// */
//
//
//package com.vivek.sampleapp.modal;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.UnsupportedEncodingException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.NameValuePair;
//import org.apache.http.client.ClientProtocolException;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.entity.UrlEncodedFormEntity;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.message.BasicNameValuePair;
//import org.apache.http.params.BasicHttpParams;
//import org.apache.http.params.HttpConnectionParams;
//import org.apache.http.params.HttpParams;
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import android.content.Context;
//import android.content.SharedPreferences;
//import android.util.Log;
//
//import com.samsung.snmc.b2b.ipd.common.Constant;
//import com.samsung.snmc.b2b.ipd.common.ServerAddress;
//import com.samsung.snmc.b2b.ipd.db.HandleDatabase;
//import com.samsung.snmc.b2b.ipd.db.MySQLiteHelper;
//import com.samsung.snmc.b2b.ipd.service.DownloadService;
//import com.samsung.snmc.b2b.ipd.vo.FeedbackCategory;
//import com.samsung.snmc.b2b.ipd.vo.FeedbackQuestion;
//import com.samsung.snmc.b2b.ipd.vo.FeedbackQuestionOption;
//import com.samsung.snmc.b2b.ipd.vo.FeedbackQuestionType;
//import com.samsung.snmc.b2b.ipd.vo.Hospital;
//import com.samsung.snmc.b2b.ipd.vo.InsertContent;
//import com.samsung.snmc.b2b.ipd.vo.InsertInfotain;
//import com.samsung.snmc.b2b.ipd.vo.Patient;
//import com.samsung.snmc.b2b.ipd.vo.PatientStory;
//import com.samsung.snmc.b2b.ipd.vo.StaffInformation;
//import com.samsung.snmc.b2b.ipd.vo.Timer;
//import com.samsung.snmc.b2b.ipd.vo.cms.Complaint;
//import com.samsung.snmc.b2b.ipd.vo.cms.ComplaintCategoryLookup;
//import com.samsung.snmc.b2b.ipd.vo.cms.ComplaintRating;
//import com.samsung.snmc.b2b.ipd.vo.cms.FrequentlyLodgedCompliant;
//
//public class ServerDataUtil {
//
//    private static Context context;
//
//    private SharedPreferences prefs;
//
//    public ServerDataUtil(Context ctx) {
//        ServerDataUtil.context = ctx;
//    }
//
//    /**
//     * Fetch Patient data from web service and returns data into json string
//     * format
//     *
//     * @param result
//     * @param params
//     * @return patientData - Json String
//     */
//    public String getPatientDataFromServer(String patientId, String appendUrl) {
//
//        String result = "";
//        ServerAddress serverAddress = ServerAddress.getInstance(context);
//        if (serverAddress != null) {
//            Log.i("ServerDataUtil",
//                    "getPatientDataFromServer() called for url: " + serverAddress.getProfileUrl());
//            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//            String deviceDate = dateFormat.format(new Date());
//            String url = serverAddress.getProfileUrl() + appendUrl;
//            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
//            nameValuePairs.add(new BasicNameValuePair("patient_id", patientId));
//            nameValuePairs.add(new BasicNameValuePair("device_date", deviceDate));
//            result = getServerResponse(url, nameValuePairs);
//        }
//        return result;
//    }
//
//    public static String getPatientStoriesFromServer(String patientId, Context ctx) {
//
//        String result = "";
//        ServerAddress serverAddress = ServerAddress.getInstance(context);
//        String url = serverAddress.getStoriesURL();
//        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
//        nameValuePairs.add(new BasicNameValuePair("patient_id", patientId));
//
//        result = getServerResponse(url, nameValuePairs);
//        return result;
//    }
//
//    public static String getContentStoriesFromServer(String messageType, Context ctx) {
//
//        String result = "";
//        ServerAddress serverAddress = ServerAddress.getInstance(context);
//        String url = serverAddress.getContentUrl();
//        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
//        nameValuePairs.add(new BasicNameValuePair("content_added", Constant.CONTENT_ADDED));
//        result = getServerResponse(url, nameValuePairs);
//        return result;
//    }
//
//    public static String getinfotainStoriesFromServer(String messageType, Context ctx) {
//
//        String result = "";
//        ServerAddress serverAddress = ServerAddress.getInstance(context);
//        String url = serverAddress.getinfotainUrl();
//        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
//        nameValuePairs.add(new BasicNameValuePair("infotain_added", Constant.INFOTAIN_ADDED));
//        result = getServerResponse(url, nameValuePairs);
//        return result;
//    }
//
//    public static List<InsertInfotain> getinfotainStoriesFromJSON(String jsonString) {
//        List<InsertInfotain> infotains = new ArrayList<InsertInfotain>();
//        try {
//            JSONArray jsonArray = new JSONArray(jsonString);
//            for (int i = 0; i < jsonArray.length(); i++) {
//                JSONObject jsonObject = jsonArray.getJSONObject(i);
//                InsertInfotain infotain = new InsertInfotain();
//                infotain.setVideoId(Integer.parseInt(jsonObject.getString("id")));
//                infotain.setTitle(jsonObject.getString("title"));
//                infotain.setCatId(Integer.parseInt(jsonObject.getString("infotaintmentCatId")));
//                infotain.setInfoImgPath(jsonObject.getString("contentImgPath"));
//                infotain.setInfoPath(jsonObject.getString("contentPath"));
//                infotain.setMediaType(jsonObject.getString("mediaType"));
//                infotain.setMediaSize(Integer.parseInt(jsonObject.getString("mediaSize")));
//                infotain.setSubCatId(Integer.parseInt(jsonObject.getString("infotaintmentSubCatId")));
//                infotain.setView(Integer.parseInt(jsonObject.getString("views")));
//                infotain.setDate(jsonObject.getString("date"));
//                infotain.setCatName(jsonObject.getString("infotaintmentCatName"));
//                infotain.setSubCatName(jsonObject.getString("infotaintmentSubCatName"));
//                infotains.add(infotain);
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return infotains;
//    }
//
//    public Patient getPatientDataFromJson(String jsonString) {
//
//        Patient patient = null;
//        ServerAddress serverAddress = ServerAddress.getInstance(context);
//
//        try {
//            JSONObject jObject = new JSONObject(jsonString);
//
//            if (jObject != null) {
//
//                String id = jObject.getString("id");
//                String name = jObject.getString("name");
//                String gender = jObject.getString("gender");
//                String dob = jObject.getString("dob");
//                String doctor = jObject.getString("doctor");
//                String address = jObject.getString("address");
//                String address1 = jObject.getString("address1");
//                String address2 = jObject.getString("address2");
//                String city = jObject.getString("city");
//                String state = jObject.getString("state");
//                String country = jObject.getString("country");
//                String pincode = jObject.getString("pincode");
//                String contactNo = jObject.getString("contactNo");
//                String email = jObject.getString("email");
//                String roomNo = jObject.getString("roomNo");
//                String domeLightId = jObject.getString("domeLightId");
//                String reasonOfStay = jObject.getString("reasonOfStay");
//                String admissionDate = jObject.getString("admissionDate");
//                String patientThumbPath = jObject.getString("patientThumbPath");
//                String alreadyLoggedIn = jObject.getString("alreadyLoggedIn");
//                String isDeviceAlloted = jObject.getString("deviceAlloted");
//                String isAlreadyDischarged = jObject.getString("alreadyDischarged");
//                String attendantName = jObject.getString("attendantName");
//                String attendantEmail = jObject.getString("attendantEmail");
//                String attendantContact = jObject.getString("attendantContact");
//                String nurseId = jObject.getString("nurseId");
//                String attendantImagePath = jObject.getString("attendantImagePath");
//                String isSameDate = jObject.getString("sameDate");
//                String bedId = jObject.getString("bedId");
//                patient = new Patient();
//
//                if (AppUtils.isNotBlankNotNull(id)) {
//                    patient.setId(id);
//                } else {
//                    patient.setId("");
//                }
//
//                if (AppUtils.isNotBlankNotNull(name)) {
//                    patient.setName(name);
//                } else {
//                    patient.setName("");
//                }
//
//                if (AppUtils.isNotBlankNotNull(gender)) {
//                    patient.setGender(gender);
//                } else {
//                    patient.setGender("");
//                }
//
//                if (AppUtils.isNotBlankNotNull(dob)) {
//                    patient.setAge(AppUtils.getAge(dob));
//                } else {
//                    patient.setAge(0);
//                }
//
//                if (AppUtils.isNotBlankNotNull(doctor)) {
//                    patient.setDoctor(doctor);
//                } else {
//                    patient.setDoctor("");
//                }
//
//                if (AppUtils.isNotBlankNotNull(address)) {
//                    patient.setAddress(address);
//                } else {
//                    patient.setAddress("");
//                }
//
//                if (AppUtils.isNotBlankNotNull(address1)) {
//                    patient.setAddress1(address1);
//                } else {
//                    patient.setAddress1("");
//                }
//
//                if (AppUtils.isNotBlankNotNull(address2)) {
//                    patient.setAddress2(address2);
//                } else {
//                    patient.setAddress2("");
//                }
//
//                if (AppUtils.isNotBlankNotNull(city)) {
//                    patient.setCity(city);
//                } else {
//                    patient.setCity("");
//                }
//
//                if (AppUtils.isNotBlankNotNull(state)) {
//                    patient.setState(state);
//                } else {
//                    patient.setState("");
//                }
//
//                if (AppUtils.isNotBlankNotNull(country)) {
//                    patient.setCountry(country);
//                } else {
//                    patient.setCountry("");
//                }
//                if (AppUtils.isNotBlankNotNull(pincode)) {
//                    patient.setPincode(pincode);
//                } else {
//                    patient.setPincode("");
//                }
//
//                if (AppUtils.isNotBlankNotNull(contactNo)) {
//                    patient.setContactNo(contactNo);
//                } else {
//                    patient.setContactNo("");
//                }
//
//                if (AppUtils.isNotBlankNotNull(email)) {
//                    patient.setEmail(email);
//                } else {
//                    patient.setEmail("");
//                }
//
//                if (AppUtils.isNotBlankNotNull(roomNo)) {
//                    patient.setRoomNo(roomNo);
//                } else {
//                    patient.setRoomNo("");
//                }
//
//                if (AppUtils.isNotBlankNotNull(domeLightId)) {
//                    patient.setDomeLightId(domeLightId);
//                } else {
//                    patient.setDomeLightId("");
//                }
//
//                if (AppUtils.isNotBlankNotNull(reasonOfStay)) {
//                    patient.setReasonOfStay(reasonOfStay);
//                } else {
//                    patient.setReasonOfStay("");
//                }
//
//                if (AppUtils.isNotBlankNotNull(admissionDate)) {
//                    patient.setAdmissionDate(admissionDate);
//                } else {
//                    patient.setAdmissionDate("");
//                }
//
//                if (AppUtils.isNotBlankNotNull(patientThumbPath)) {
//                    patient.setImage(patientThumbPath);
//                }
//
//                if (AppUtils.isNotBlank(alreadyLoggedIn)) {
//                    patient.setAlreadyLoggedIn(Boolean.parseBoolean(alreadyLoggedIn));
//                }
//
//                if (AppUtils.isNotBlank(isAlreadyDischarged)) {
//                    patient.setAlreadyDischarged(Boolean.parseBoolean(isAlreadyDischarged));
//                }
//
//                if (AppUtils.isNotBlank(isDeviceAlloted)) {
//                    patient.setDeviceAlloted(Boolean.parseBoolean(isDeviceAlloted));
//                }
//
//                if (AppUtils.isNotBlankNotNull(patientThumbPath)) {
//
//                    String profileImagePath = patientThumbPath;
//                    String commonDirPath = context.getExternalFilesDir(null)
//                            + Constant.DOWNLOADED_DATA_FROM_SERVER + Constant.DOWNLOADED_PICS;
//                    String profileImageName = AppUtils.getFileName(profileImagePath);
//
//                    if (AppUtils.isNotBlank(alreadyLoggedIn)) {
//                        if (AppUtils.isNotBlank(profileImagePath)
//                                && AppUtils.isNotBlank(profileImageName)) {
//                            // Changed from service to thread.
//                            DownloadService download = new DownloadService();
//                            download.downloadFile(serverAddress.getDownloadStoryURL()
//                                    + profileImagePath, profileImageName, commonDirPath, null);
//                        }
//                    }
//                    patient.setImage(commonDirPath + profileImageName);
//                }
//
//                if (AppUtils.isNotBlankNotNull(attendantName)) {
//                    patient.setAttenName(attendantName);
//                }
//                if (AppUtils.isNotBlankNotNull(attendantEmail)) {
//                    patient.setAttenEmail(attendantEmail);
//                }
//                if (AppUtils.isNotBlankNotNull(attendantContact)) {
//                    patient.setAttenContact(attendantContact);
//                }
//                if (AppUtils.isNotBlankNotNull(nurseId)) {
//                    patient.setNurse(nurseId);
//                }
//
//                if (AppUtils.isNotBlankNotNull(bedId)) {
//                    patient.setBedId(bedId);
//                    prefs = context.getSharedPreferences("data", context.MODE_PRIVATE);
//                    prefs.edit().putString("bedId", bedId).commit();
//                }
//                if (AppUtils.isNotBlankNotNull(attendantImagePath)) {
//
//                    String profileImagePath = attendantImagePath;
//                    String commonDirPath = context.getExternalFilesDir(null)
//                            + Constant.DOWNLOADED_DATA_FROM_SERVER + Constant.DOWNLOADED_PICS;
//                    String profileImageName = AppUtils.getFileName(profileImagePath);
//
//                    if (AppUtils.isNotBlank(alreadyLoggedIn)) {
//                        if (!Boolean.parseBoolean(alreadyLoggedIn)) {
//                            if (AppUtils.isNotBlank(profileImagePath)
//                                    && AppUtils.isNotBlank(profileImageName)) {
//                                // Changed from service to thread.
//                                DownloadService download = new DownloadService();
//                                download.downloadFile(serverAddress.getDownloadStoryURL()
//                                        + profileImagePath, profileImageName, commonDirPath, null);
//                            }
//                        }
//                    }
//                    patient.setAttenImage(commonDirPath + profileImageName);
//
//                }
//
//                if (AppUtils.isNotBlankNotNull(isSameDate)) {
//                    patient.setSameDate(Boolean.parseBoolean(isSameDate));
//                } else {
//                    patient.setSameDate(false);
//                }
//
//            }
//        } catch (JSONException e) {
//            Log.e("JSONException", "Error: " + e.toString());
//        }
//        return patient;
//    }
//
//    public static List<PatientStory> getPatientStoriesFromJSON(String jsonString) {
//        List<PatientStory> patientStories = new ArrayList<PatientStory>();
//        String storyName = "";
//        try {
//            JSONArray jsonArray = new JSONArray(jsonString);
//            for (int i = 0; i < jsonArray.length(); i++) {
//                JSONObject jsonObject = jsonArray.getJSONObject(i);
//                int contentType = jsonObject.getInt("contentType");
//                if (contentType == 1) {
//                    storyName = jsonObject.getString("name");
//                } else {
//                    storyName = jsonObject.getString("storyTitle");
//                }
//
//                PatientStory story = new PatientStory(null, null, jsonObject.getString("path"),
//                        jsonObject.getString("openForContact"), null, null, storyName,
//                        jsonObject.getString("contact"), jsonObject.getString("storyDescription"));
//                story.setPic_location(jsonObject.getString("profileImagePath"));
//                story.setMediaType(jsonObject.getString("mediaType"));
//                story.setAdmitDate(jsonObject.getString("admittedDate"));
//                story.setVideoPreviewImg(jsonObject.getString("videoPreview"));
//                story.setEmail(jsonObject.getString("email"));
//                story.setMediaSize(jsonObject.getString("sizeInKb"));
//                story.setContentType(contentType);
//                patientStories.add(story);
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return patientStories;
//    }
//
//    public static List<InsertContent> getContentStoriesFromJSON(String jsonString) {
//        List<InsertContent> contents = new ArrayList<InsertContent>();
//        try {
//            JSONArray jsonArray = new JSONArray(jsonString);
//            for (int i = 0; i < jsonArray.length(); i++) {
//                JSONObject jsonObject = jsonArray.getJSONObject(i);
//                InsertContent content = new InsertContent();
//                content.setId(Integer.parseInt(jsonObject.getString("id")));
//                content.setTitle(jsonObject.getString("title"));
//                content.setContentCat(jsonObject.getString("contentCat"));
//                content.setContentSubCat(jsonObject.getString("contentSubCat"));
//                content.setDepartment(jsonObject.getString("department"));
//                content.setContentImgPath(jsonObject.getString("contentImgPath").toString());
//                content.setMediaType(jsonObject.getString("mediaType"));
//                content.setMediaSize(Integer.parseInt(jsonObject.getString("mediaSize")));
//                content.setContentPath(jsonObject.getString("contentPath"));
//                content.setViews(Integer.parseInt(jsonObject.getString("views")));
//                content.setDate(jsonObject.getString("date"));
//                contents.add(content);
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return contents;
//    }
//
//    public static List<StaffInformation> getStaffInfo(Context context, String Patient_id,
//            boolean update, boolean isFromOBC) {
//        JSONArray stafflist = null;
//        String result = "";
//        List<StaffInformation> staffList = new ArrayList<StaffInformation>();
//        HandleDatabase hd = HandleDatabase.getInstance(context);
//        String url = ServerAddress.getInstance(context).getStaf();
//        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
//        String pId = Patient_id;
//        if (pId != null && !pId.equals("")) {
//            Log.d("StaffInfoActivity",
//                    "Sending message to server to fetch details of staff allotted to patient: "
//                            + pId);
//            nameValuePairs.add(new BasicNameValuePair("patient_id", pId));
//            result = getServerResponse(url, nameValuePairs);
//        }
//
//        try {
//            stafflist = new JSONArray(result);
//        } catch (Exception e) {
//            Log.e("StringBuilding & BufferedReader", "Error converting result " + e.toString());
//        }
//        try {
//            String commonDirPath = context.getExternalFilesDir(null)
//                    + Constant.DOWNLOADED_DATA_FROM_SERVER + Constant.DOWNLOADED_PICS;
//            if (stafflist != null) {
//            	hd.deleteObcStaff();
//                for (int i = 0; i < stafflist.length(); i++) {
//                    JSONObject json = (JSONObject) stafflist.get(i);
//                    String id = json.getString("staffId");
//                    String name = json.getString("name");
//                    String staffType = json.getString("staffType");
//                    String post = "";
//
//                    String schedule = json.getString("schedule");
//                    if (schedule == null) {
//                        schedule = "";
//                    }
//                    String designation = json.getString("designation");
//                    if (designation == null) {
//                        designation = "";
//                    }
//                    String qualification = json.getString("qualification");
//                    if (qualification == null) {
//                        qualification = "";
//                    }
//                    String fellowships = "Underwent knee expert training at Aesculo headquarters in vienna 2012. Member of National Academy"
//                            + "of Medical Science, Indian Orhtopaedic association. ";
//                    String interests = json.getString("speciality");
//                    if (interests == null) {
//                        interests = "";
//                    }
//                    String experience = "He has 30 years of dedicated service in india and abroad";
//                    String thumbUri = json.getString("thumbUri");
//
//                    String thumbUriName = "";
//                    if (AppUtils.isNotBlankNotNull(thumbUri)) {
//                        thumbUriName = thumbUri.substring(thumbUri.lastIndexOf('/'));
//                    }
//                    String pathWidName = commonDirPath + thumbUriName;
//                    StaffInformation staffInfo = new StaffInformation();
//                    int staffTypeId = Constant.DOCTOR;
//                    if (AppUtils.isNotBlank(staffType)) {
//                        staffTypeId = Integer.parseInt(staffType);
//                    }
//                    if (staffTypeId == Constant.DOCTOR && !isFromOBC) {
//                        post = "Doctor";
//                        if (!hd.isStaffExist(id)) {
//                            hd.createStaff(id, name, post, schedule, designation, qualification,
//                                    fellowships, interests, experience, pathWidName, true, false);
//                        } else {
//                            hd.updateStaff(id, name, post, schedule, designation, qualification,
//                                    fellowships, interests, experience, pathWidName, true, false);
//                            hd.updateDoctorFeedback(pathWidName, name, id);
//                        }
//                        if (!hd.isImagePathExist(id)) {
//                            hd.insertImagePath(id, pathWidName);
//                        } else {
//                            hd.updateImagePath(id, pathWidName);
//                        }
//
//                    } else if (staffTypeId == Constant.NURSING_DIVISION) {
//                        // post = "Nurse";
//                        post = designation;
//                        int loginStatus = Integer.parseInt(json.getString("loginStatus"));
//                        if (!isFromOBC) {
//                            if (!hd.isStaffExist(id)) {
//                                hd.createStaff(id, name, post, schedule, designation,
//                                        qualification, fellowships, interests, experience,
//                                        pathWidName, false, true);
//                            } else {
//                                hd.updateStaff(id, name, post, schedule, designation,
//                                        qualification, fellowships, interests, experience,
//                                        pathWidName, false, true);
//                            }
//                        } else {
//                            hd.createObcStaff(id, name, post, schedule, designation, qualification,
//                                    fellowships, interests, experience, pathWidName, false, true,
//                                    loginStatus);
//                        }
//                        if (!hd.isImagePathExist(id)) {
//                            hd.insertImagePath(id, pathWidName);
//                        } else {
//                            hd.updateImagePath(id, pathWidName);
//                        }
//
//                    } else if (staffTypeId == Constant.WARDEN) {
//                        // post = "Head Nurse";
//                        post = designation;
//                        int loginStatus = Integer.parseInt(json.getString("loginStatus"));
//                        if (!isFromOBC) {
//                            if (!hd.isStaffExist(id)) {
//                                hd.createStaff(id, name, post, schedule, designation,
//                                        qualification, fellowships, interests, experience,
//                                        pathWidName, false, false);
//                            } else {
//                                hd.updateStaff(id, name, post, schedule, designation,
//                                        qualification, fellowships, interests, experience,
//                                        pathWidName, false, false);
//                            }
//                        } else {
//                            hd.createObcStaff(id, name, post, schedule, designation, qualification,
//                                    fellowships, interests, experience, pathWidName, false, false,
//                                    loginStatus);
//                        }
//                        if (!hd.isImagePathExist(id)) {
//                            hd.insertImagePath(id, pathWidName);
//                        } else {
//                            hd.updateImagePath(id, pathWidName);
//                        }
//
//                    } else {
//                        Log.e("StaffInfoActivity", "Wrong staffTypeId [" + staffTypeId
//                                + "] for staff [" + name + "]");
//                    }
//                    staffInfo.setStaffId(id);
//                    staffInfo.setStaffName(name);
//                    staffInfo.setstaffPost(post);
//
//                    staffInfo.setStaffSchedule(schedule);
//                    staffInfo.setImageStaff(pathWidName);
//                    staffList.add(staffInfo);
//                    if (AppUtils.isNotBlank(thumbUri) && AppUtils.isNotBlank(thumbUriName)) {
//                        DownloadService downlaod = new DownloadService();
//                        downlaod.downloadFile(ServerAddress.getInstance(context)
//                                .getDownloadStoryURL() + thumbUri, thumbUriName.substring(1),
//                                commonDirPath, null);
//                    }
//                }
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        return staffList;
//    }
//
//    public static Hospital getHospitalInfo(Context context, String patient_id) {
//        InputStream inputStream = null;
//        Hospital hospital = new Hospital();
//        JSONObject json = new JSONObject();
//        HandleDatabase hd = HandleDatabase.getInstance(context);
//
//        HttpClient httpClient = new DefaultHttpClient();
//        HttpPost httppost = new HttpPost(ServerAddress.getInstance(context).getHosiptalInfoUrl());
//        try {
//            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
//            String pId = patient_id;
//            if (pId != null && !pId.equals("")) {
//                Log.d("HospitalInfoActivity",
//                        "Sending message to server to fetch Hospital Details by Patient: " + pId);
//                nameValuePairs.add(new BasicNameValuePair("patient_id", pId));
//                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
//                HttpResponse httpResponse = httpClient.execute(httppost);
//                if (httpResponse.getStatusLine().getStatusCode() == 200) {
//                    HttpEntity httpEntity = httpResponse.getEntity();
//                    inputStream = httpEntity.getContent();
//                } else {
//                    Log.i("HospitalInfoActivity", "No record found.");
//                }
//            }
//        } catch (UnsupportedEncodingException e1) {
//            Log.e("UnsupportedEncodingException", e1.toString());
//            e1.printStackTrace();
//        } catch (ClientProtocolException e2) {
//            Log.e("ClientProtocolException", e2.toString());
//            e2.printStackTrace();
//        } catch (IllegalStateException e3) {
//            Log.e("IllegalStateException", e3.toString());
//            e3.printStackTrace();
//        } catch (IOException e4) {
//            Log.e("IOException", e4.toString());
//            e4.printStackTrace();
//        }
//        String result = "";
//        try {
//            BufferedReader bReader = new BufferedReader(new InputStreamReader(inputStream,
//                    "iso-8859-1"), 8);
//            StringBuilder sBuilder = new StringBuilder();
//            String line = null;
//            while ((line = bReader.readLine()) != null) {
//                sBuilder.append(line + "\n");
//            }
//
//            result = sBuilder.toString();
//            Log.d("HospitalInfoActivity", "JSON String: " + result);
//            json = new JSONObject(result);
//        } catch (Exception e) {
//            Log.e("StringBuilding & BufferedReader", "Error converting result " + e.toString());
//        } finally {
//        	if (inputStream != null)
//				try {
//					inputStream.close();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//        }
//        try {
//            if (json != null) {
//                int id = 1;
//                String hospital_name = json.getString("hospitalName");
//                String about = json.getString("about");
//                String hospital_icon_uri = json.getString("hospitalIconUri");
//                /*
//                 * String thumbUriName = ""; if
//                 * (AppUtils.isNotBlankNotNull(hospital_icon_uri)) {
//                 * thumbUriName =
//                 * hospital_icon_uri.substring(hospital_icon_uri.lastIndexOf
//                 * ('/')); } if (AppUtils.isNotBlank(hospital_icon_uri) &&
//                 * AppUtils.isNotBlank(thumbUriName)) { DownloadService downlaod
//                 * = new DownloadService(); downlaod.downloadFile
//                 * (ServerAddress.getInstance(context) .getDownloadStoryURL() +
//                 * hospital_icon_uri, thumbUriName.substring(1), commonDirPath,
//                 * null); }
//                 */
//                if (AppUtils.isTableEmpty(context, MySQLiteHelper.TABLE_HOSPITAL_INFO)) {
//                    hd.insertHospitalInfo(String.valueOf(id), hospital_name, about,
//                            hospital_icon_uri);
//                } else {
//                    hd.updateHospitalInfo(String.valueOf(id), hospital_name, about,
//                            hospital_icon_uri);
//                }
//
//                hospital.setId(id);
//                hospital.setHospitalName(hospital_name);
//                hospital.setAbout(about);
//                hospital.setHospitalIconUri(hospital_icon_uri);
//
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        return hospital;
//    }
//
//    public static Timer getTimerInfo(Context baseContext) {
//        InputStream inputStream = null;
//        JSONObject json = new JSONObject();
//        Timer timerData = new Timer();
//        HandleDatabase hd = HandleDatabase.getInstance(context);
//
//        HttpClient httpClient = new DefaultHttpClient();
//        HttpPost httppost = new HttpPost(ServerAddress.getInstance(context).getTimerDataUrl());
//        try {
//            Log.d("DashboardActivity",
//                    "Sending message to server to fetch Timer Details by Patient: ");
//            HttpResponse httpResponse = httpClient.execute(httppost);
//            if (httpResponse.getStatusLine().getStatusCode() == 200) {
//                HttpEntity httpEntity = httpResponse.getEntity();
//                inputStream = httpEntity.getContent();
//            }
//        } catch (UnsupportedEncodingException e1) {
//            Log.e("UnsupportedEncodingException", e1.toString());
//            e1.printStackTrace();
//        } catch (ClientProtocolException e2) {
//            Log.e("ClientProtocolException", e2.toString());
//            e2.printStackTrace();
//        } catch (IllegalStateException e3) {
//            Log.e("IllegalStateException", e3.toString());
//            e3.printStackTrace();
//        } catch (IllegalArgumentException e3) {
//            Log.e("IllegalArgumentException", e3.toString());
//            e3.printStackTrace();
//        } catch (IOException e4) {
//            Log.e("IOException", e4.toString());
//            e4.printStackTrace();
//        }
//        String result = "";
//        try {
//            BufferedReader bReader = new BufferedReader(new InputStreamReader(inputStream,
//                    "iso-8859-1"), 8);
//            StringBuilder sBuilder = new StringBuilder();
//            String line = null;
//            while ((line = bReader.readLine()) != null) {
//                sBuilder.append(line + "\n");
//            }
//
//            result = sBuilder.toString();
//            Log.d("DashboardActivity", "JSON String: " + result);
//            json = new JSONObject(result);
//
//            if (json != null) {
//
//                timerData.setMax_respond_time(json.getLong(MySQLiteHelper.COLUMN_MAX_RESPOND_TIME));
//                timerData.setMax_reach_time(json.getLong(MySQLiteHelper.COLUMN_MAX_REACH_TIME));
//                timerData.setAvg_service_time(json.getLong(MySQLiteHelper.COLUMN_AVG_SERVICE_TIME));
//                timerData.setDoc_visit_time(json.getLong(MySQLiteHelper.COLUMN_AVG_SERVICE_TIME));
//                hd.insertTimerData(timerData.getMax_respond_time(), timerData.getMax_reach_time(),
//                        timerData.getAvg_service_time(), timerData.getDoc_visit_time());
//
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//            Log.e("DashboardActivity", "JSONException in initializing Timer data");
//        } catch (Exception e) {
//            Log.e("StringBuilding & BufferedReader", "Error converting result " + e.toString());
//        } finally {
//        	  if (inputStream != null)
//				try {
//					inputStream.close();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//        }
//        return timerData;
//
//    }
//
//    public static String getServerResponse(String url, List<NameValuePair> nameValuePairs) {
//        HttpClient httpClient = new DefaultHttpClient();
//        InputStream inputStream = null;
//        String result = "";
//        HttpPost httppost = new HttpPost(url);
//
//        try {
//            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
//            HttpResponse httpResponse = httpClient.execute(httppost);
//            if (httpResponse.getStatusLine().getStatusCode() == 200) {
//                HttpEntity httpEntity = httpResponse.getEntity();
//                inputStream = httpEntity.getContent();
//            } else {
//                Log.i("getServerResponse : ", "No record found.");
//            }
//
//        } catch (UnsupportedEncodingException e1) {
//            Log.e("UnsupportedEncodingException", e1.toString());
//            e1.printStackTrace();
//        } catch (ClientProtocolException e2) {
//            Log.e("ClientProtocolException", e2.toString());
//            e2.printStackTrace();
//        } catch (IllegalStateException e3) {
//            Log.e("IllegalStateException", e3.toString());
//            e3.printStackTrace();
//        } catch (IllegalArgumentException e4) {
//            Log.e("IllegalArgumentException", e4.toString());
//            e4.printStackTrace();
//            result = "one";
//            return result;
//        } catch (IOException e4) {
//            // TODO: HttpHostConnectException comes when the WIFI is OFF. It
//            // comes under IOException.
//            Log.e("IOException", e4.toString());
//            e4.printStackTrace();
//            result = "nine";
//            return result;
//        }
//
//        try {
//            BufferedReader bReader = new BufferedReader(new InputStreamReader(inputStream,
//                    "iso-8859-1"), 8);
//            StringBuilder sBuilder = new StringBuilder();
//            String line = null;
//            while ((line = bReader.readLine()) != null) {
//                sBuilder.append(line);
//            }
//
//            result = sBuilder.toString();
//            Log.d("getServerResponse ", "JSON String: " + result);
//        } catch (Exception e) {
//            Log.e("StringBuilding & BufferedReader", "Error converting result " + e.toString());
//        } finally {
//        	if (inputStream != null)
//				try {
//					inputStream.close();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//        }
//
//        return result;
//    }
//
//    /**
//     * Fetch feedback Question data from web service and returns data into json
//     * string format
//     *
//     * @param result
//     * @param params
//     * @return Feedback - Json String
//     */
//    public static String getFeedbackQuestionDataFromServer(int type) {
//
//        String url = "";
//        String result = "";
//        ServerAddress serverAddress = ServerAddress.getInstance(context);
//
//        switch (type) {
//            case Constant.TYPE_FEEDBACK_QUESTION:
//                url = serverAddress.getFeedbackQuestionUrl();
//                break;
//            case Constant.TYPE_FEEDBACK_CATEGORY:
//                url = serverAddress.getFeedbackCategoryUrl();
//                break;
//            case Constant.TYPE_FEEDBACK_QUESTION_OPTION:
//                url = serverAddress.getFeedbackQuestionOptionUrl();
//                break;
//            case Constant.TYPE_FEEDBACK_QUESTION_TYPE:
//                url = serverAddress.getFeedbackQuestionTypeUrl();
//                break;
//        }
//
//        Log.i("ServerDataUtil", "getFeedbackQuestionDataFromServer() called for url: " + url);
//
//        result = getFeedbackServerResponse(url);
//
//        return result;
//    }
//
//    /**
//     * Send request to server to get the feedback questions
//     *
//     * @param url
//     * @return
//     */
//
//    public static String getFeedbackServerResponse(String url) {
//        HttpClient httpClient = new DefaultHttpClient();
//        InputStream inputStream = null;
//        String result = "";
//        HttpPost httppost = new HttpPost(url);
//
//        try {
//            HttpResponse httpResponse = httpClient.execute(httppost);
//            if (httpResponse.getStatusLine().getStatusCode() == 200) {
//                HttpEntity httpEntity = httpResponse.getEntity();
//                inputStream = httpEntity.getContent();
//            } else {
//                Log.i("getServerResponse : ", "No record found.");
//            }
//
//        } catch (UnsupportedEncodingException e1) {
//            Log.e("UnsupportedEncodingException", e1.toString());
//            e1.printStackTrace();
//        } catch (ClientProtocolException e2) {
//            Log.e("ClientProtocolException", e2.toString());
//            e2.printStackTrace();
//        } catch (IllegalStateException e3) {
//            Log.e("IllegalStateException", e3.toString());
//            e3.printStackTrace();
//        } catch (IOException e4) {
//            Log.e("IOException", e4.toString());
//            e4.printStackTrace();
//        }
//
//        try {
//            BufferedReader bReader = new BufferedReader(new InputStreamReader(inputStream,
//                    "iso-8859-1"), 8);
//            StringBuilder sBuilder = new StringBuilder();
//            String line = null;
//            while ((line = bReader.readLine()) != null) {
//                sBuilder.append(line + "\n");
//            }
//
//            result = sBuilder.toString();
//            Log.d("getServerResponse ", "JSON String: " + result);
//        } catch (Exception e) {
//            Log.e("StringBuilding & BufferedReader", "Error converting result " + e.toString());
//        } finally {
//        	if (inputStream != null)
//				try {
//					inputStream.close();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//        }
//
//        return result;
//    }
//
//    public static String getFrequentlyLodgedComplaintFromServer() {
//
//        Log.i("CMS", "ServerDataUtil: getFrequentlyLodgedComplaintFromServer()");
//        String result = "";
//        ServerAddress serverAddress = ServerAddress.getInstance(context);
//        String url = serverAddress.getFrequentlyLodgedComplaintUrl();
//        result = getFrequentlyLodgedComplaintServerResponse(url);
//        return result;
//    }
//
//    public static String getFrequentlyLodgedComplaintServerResponse(String url) {
//        HttpClient httpClient = new DefaultHttpClient();
//        InputStream inputStream = null;
//        String result = "";
//        HttpParams httpParameters = new BasicHttpParams();
//        HttpConnectionParams.setConnectionTimeout(httpParameters, 50000);
//        HttpConnectionParams.setSoTimeout(httpParameters, 50000);
//        HttpPost httppost = new HttpPost(url);
//        try {
//            HttpResponse httpResponse = httpClient.execute(httppost);
//            if (httpResponse.getStatusLine().getStatusCode() == 200) {
//                HttpEntity httpEntity = httpResponse.getEntity();
//                inputStream = httpEntity.getContent();
//            } else {
//                Log.i("getServerResponse : ", "No record found.");
//            }
//
//        } catch (UnsupportedEncodingException e1) {
//            Log.e("UnsupportedEncodingException", e1.toString());
//            e1.printStackTrace();
//        } catch (ClientProtocolException e2) {
//            Log.e("ClientProtocolException", e2.toString());
//            e2.printStackTrace();
//        } catch (IllegalStateException e3) {
//            Log.e("IllegalStateException", e3.toString());
//            e3.printStackTrace();
//        } catch (IOException e4) {
//            Log.e("IOException", e4.toString());
//            e4.printStackTrace();
//        }
//
//        try {
//            BufferedReader bReader = new BufferedReader(new InputStreamReader(inputStream,
//                    "iso-8859-1"), 8);
//            StringBuilder sBuilder = new StringBuilder();
//            String line = null;
//            while ((line = bReader.readLine()) != null) {
//                sBuilder.append(line + "\n");
//            }
//
//            result = sBuilder.toString();
//            Log.d("getServerResponse ", "JSON String: " + result);
//        } catch (Exception e) {
//            Log.e("StringBuilding & BufferedReader", "Error converting result " + e.toString());
//        } finally {
//        	if (inputStream != null)
//				try {
//					inputStream.close();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//        }
//
//        return result;
//    }
//
//    public static String getComplaintCategoryLookupFromServer() {
//        Log.i("CMS", "ServerDataUtil: getComplaintCategoryLookupFromServer()");
//        String result = "";
//        ServerAddress serverAddress = ServerAddress.getInstance(context);
//        String url = serverAddress.getComplaintCategoryLookupUrl();
//        result = getComplaintCategoryLooukupServerResponse(url);
//        return result;
//    }
//
//    public static String getComplaintCategoryLooukupServerResponse(String url) {
//        HttpClient httpClient = new DefaultHttpClient();
//        InputStream inputStream = null;
//        String result = "";
//        HttpPost httppost = new HttpPost(url);
//        HttpParams httpParameters = new BasicHttpParams();
//        HttpConnectionParams.setConnectionTimeout(httpParameters, 50000);
//        HttpConnectionParams.setSoTimeout(httpParameters, 50000);
//        try {
//            HttpResponse httpResponse = httpClient.execute(httppost);
//            if (httpResponse.getStatusLine().getStatusCode() == 200) {
//                HttpEntity httpEntity = httpResponse.getEntity();
//                inputStream = httpEntity.getContent();
//            } else {
//                Log.i("getServerResponse : ", "No record found");
//            }
//
//        } catch (UnsupportedEncodingException e1) {
//            Log.e("UnsupportedEncodingException", e1.toString());
//            e1.printStackTrace();
//        } catch (ClientProtocolException e2) {
//            Log.e("ClientProtocolException", e2.toString());
//            e2.printStackTrace();
//        } catch (IllegalStateException e3) {
//            Log.e("IllegalStateException", e3.toString());
//            e3.printStackTrace();
//        } catch (IOException e4) {
//            Log.e("IOException", e4.toString());
//            e4.printStackTrace();
//        }
//
//        try {
//            BufferedReader bReader = new BufferedReader(new InputStreamReader(inputStream,
//                    "iso-8859-1"), 8);
//            StringBuilder sBuilder = new StringBuilder();
//            String line = null;
//            while ((line = bReader.readLine()) != null) {
//                sBuilder.append(line + "\n");
//            }
//
//            result = sBuilder.toString();
//            Log.d("getServerResponse ", "JSON String: " + result);
//        } catch (Exception e) {
//            Log.e("StringBuilding & BufferedReader", "Error converting result " + e.toString());
//        } finally {
//        	 if (inputStream != null)
//				try {
//					inputStream.close();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//        }
//
//        return result;
//    }
//
//    public static String getMyComplaintsFromServer(String patientId) {
//        Log.i("CMS", "ServerDataUtil: String getMyComplaintsFromServer(String patientId)");
//        String result = "";
//        ServerAddress serverAddress = ServerAddress.getInstance(context);
//        String url = serverAddress.getMyComplaintsUrl();
//        result = getMyComplaintsServerResponse(url, patientId);
//        return result;
//    }
//
//    public static String getMyComplaintsServerResponse(String url, String patientId) {
//        HttpClient httpClient = new DefaultHttpClient();
//        InputStream inputStream = null;
//        String result = "";
//        HttpPost httppost = new HttpPost(url);
//        HttpParams httpParameters = new BasicHttpParams();
//        HttpConnectionParams.setConnectionTimeout(httpParameters, 50000);
//        HttpConnectionParams.setSoTimeout(httpParameters, 50000);
//        SharedPreferences myPrefs;
//        myPrefs = context.getSharedPreferences("data", Context.MODE_PRIVATE);
//
//        try {
//
//            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
//            if (patientId != null && !patientId.equals("")) {
//                Log.d("CMS", "Sending patientId to server to get patient's complaints " + patientId);
//                nameValuePairs.add(new BasicNameValuePair("source_id", patientId));
//
//                Log.i("CMS", "patientId :" + patientId);
//                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
//                HttpResponse httpResponse = httpClient.execute(httppost);
//
//                Log.i("CMS", "getMyComplaintsServerResponse(): after http execution");
//
//                if (httpResponse.getStatusLine().getStatusCode() == 200) {
//                    HttpEntity httpEntity = httpResponse.getEntity();
//                    inputStream = httpEntity.getContent();
//                } else {
//                    Log.i("CMS", "getMyComplaintsServerResponse(): No record found.");
//                }
//            }
//        } catch (UnsupportedEncodingException e1) {
//            Log.e("UnsupportedEncodingException", e1.toString());
//            e1.printStackTrace();
//        } catch (ClientProtocolException e2) {
//            Log.e("ClientProtocolException", e2.toString());
//            e2.printStackTrace();
//        } catch (IllegalStateException e3) {
//            Log.e("IllegalStateException", e3.toString());
//            e3.printStackTrace();
//        } catch (IOException e4) {
//            Log.e("IOException", e4.toString());
//            e4.printStackTrace();
//        }
//
//        try {
//            BufferedReader bReader = new BufferedReader(new InputStreamReader(inputStream,
//                    "iso-8859-1"), 8);
//            StringBuilder sBuilder = new StringBuilder();
//            String line = null;
//            while ((line = bReader.readLine()) != null) {
//                sBuilder.append(line + "\n");
//            }
//
//            result = sBuilder.toString();
//            Log.d("getServerResponse ", "JSON String: " + result);
//        } catch (Exception e) {
//            Log.e("StringBuilding & BufferedReader", "Error converting result " + e.toString());
//        }
//
//        finally {
//        	if (inputStream != null)
//				try {
//					inputStream.close();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//        }
//       return result;
//    }
//
//    @SuppressWarnings("unchecked")
//    public static List<Object> getFeedbackFromJSON(String jsonString, int type) {
//
//        try {
//            JSONArray jsonArray = new JSONArray(jsonString);
//
//            switch (type) {
//                case Constant.TYPE_FEEDBACK_QUESTION:
//                    List<FeedbackQuestion> questionList = new ArrayList<FeedbackQuestion>();
//                    for (int i = 0; i < jsonArray.length(); i++) {
//
//                        JSONObject jsonObject = jsonArray.getJSONObject(i);
//                        if (jsonObject.getString("description").toString().isEmpty()) {
//                            continue;
//                        }
//
//                        FeedbackQuestion question = new FeedbackQuestion();
//                        question.setQuestionId(jsonObject.getString("questionId").toString());
//                        question.setCategoryId(jsonObject.getString("categoryId").toString());
//                        question.setQuestionType(jsonObject.getInt("qTypeId"));
//                        question.setFeedbackType(jsonObject.getString("feedbackType").toString());
//                        question.setDescription(jsonObject.getString("description").toString());
//
//                        questionList.add(question);
//                    }
//                    return (List<Object>) (List<?>) questionList;
//                case Constant.TYPE_FEEDBACK_CATEGORY:
//                    List<FeedbackCategory> categoryList = new ArrayList<FeedbackCategory>();
//                    for (int i = 0; i < jsonArray.length(); i++) {
//
//                        JSONObject jsonObject = jsonArray.getJSONObject(i);
//                        if (jsonObject.getString("description").toString().isEmpty()) {
//                            continue;
//                        }
//
//                        FeedbackCategory category = new FeedbackCategory();
//                        category.setCategoryId(jsonObject.getString("categoryId").toString());
//                        category.setDescription(jsonObject.getString("description").toString());
//
//                        categoryList.add(category);
//                    }
//                    return (List<Object>) (List<?>) categoryList;
//
//                case Constant.TYPE_FEEDBACK_QUESTION_OPTION:
//                    List<FeedbackQuestionOption> questionOptionList = new ArrayList<FeedbackQuestionOption>();
//                    for (int i = 0; i < jsonArray.length(); i++) {
//
//                        JSONObject jsonObject = jsonArray.getJSONObject(i);
//                        if (jsonObject.getString("optionName").toString().isEmpty()) {
//                            continue;
//                        }
//                        FeedbackQuestionOption option = new FeedbackQuestionOption();
//                        option.setId(jsonObject.getInt("id"));
//                        option.setOptionId(jsonObject.getString("optionId").toString());
//                        option.setOptionName(jsonObject.getString("optionName").toString());
//                        option.setQuestionId(jsonObject.getString("questionId").toString());
//
//                        questionOptionList.add(option);
//                    }
//                    return (List<Object>) (List<?>) questionOptionList;
//
//                case Constant.TYPE_FEEDBACK_QUESTION_TYPE:
//
//                    List<FeedbackQuestionType> questionTypeList = new ArrayList<FeedbackQuestionType>();
//                    for (int i = 0; i < jsonArray.length(); i++) {
//
//                        JSONObject jsonObject = jsonArray.getJSONObject(i);
//
//                        FeedbackQuestionType qtype = new FeedbackQuestionType();
//                        qtype.setQuestionId(jsonObject.getString("id").toString());
//                        qtype.setQuestionType(jsonObject.getString("qType").toString());
//                        qtype.setOptionCount(jsonObject.getString("optionCount").toString());
//
//                        questionTypeList.add(qtype);
//                    }
//                    return (List<Object>) (List<?>) questionTypeList;
//
//            }
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    public static List<FrequentlyLodgedCompliant> getFrequentlyLodgedComplaintFromJson(
//            String jsonString) {
//
//        try {
//            JSONArray jsonArray = new JSONArray(jsonString);
//            List<FrequentlyLodgedCompliant> frequentlyLodgedCompliantList = new ArrayList<FrequentlyLodgedCompliant>();
//
//            for (int i = 0; i < jsonArray.length(); i++) {
//
//                JSONObject jsonObject = jsonArray.getJSONObject(i);
//
//                FrequentlyLodgedCompliant frequentlyLodgedCompliant = new FrequentlyLodgedCompliant();
//                frequentlyLodgedCompliant.setId(jsonObject.getInt("id"));
//                frequentlyLodgedCompliant.setFrequentlyLodgedComplaint(jsonObject
//                        .getString("frequentlyLodgedComplaint"));
//                frequentlyLodgedCompliant.setComplaintCategoryId(jsonObject
//                        .getLong("complaintCategory"));
//                frequentlyLodgedCompliantList.add(frequentlyLodgedCompliant);
//            }
//            return frequentlyLodgedCompliantList;
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return null;
//
//    }
//
//    public static List<ComplaintCategoryLookup> getComplaintCategoryLookupFromJson(String jsonString) {
//
//        try {
//            JSONArray jsonArray = new JSONArray(jsonString);
//            List<ComplaintCategoryLookup> complaintCategoryLookupList = new ArrayList<ComplaintCategoryLookup>();
//
//            for (int i = 0; i < jsonArray.length(); i++) {
//
//                JSONObject jsonObject = jsonArray.getJSONObject(i);
//
//                ComplaintCategoryLookup complaintCategoryLookup = new ComplaintCategoryLookup();
//
//                complaintCategoryLookup.setId(jsonObject.getLong("id"));
//
//                complaintCategoryLookup.setName(jsonObject.getString("name"));
//
//                complaintCategoryLookup.setType(jsonObject.getString("type"));
//
//                complaintCategoryLookup.setTypeId(jsonObject.getString("typeId"));
//
//                complaintCategoryLookup.setParentId(jsonObject.getLong("parentId"));
//
//                complaintCategoryLookup.setIconPath(jsonObject.getString("iconPath"));
//
//                complaintCategoryLookupList.add(complaintCategoryLookup);
//            }
//
//            return complaintCategoryLookupList;
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return null;
//
//    }
//
//    public static List<Complaint> getMyComplaintFromJson(String jsonString) {
//
//        // Log.i("CMS", "ServerDataUtil: json string from server : " +
//        // jsonString);
//        try {
//            JSONArray jsonArray = new JSONArray(jsonString);
//            List<Complaint> complaintList = new ArrayList<Complaint>();
//
//            for (int i = 0; i < jsonArray.length(); i++) {
//
//                JSONObject jsonObject = jsonArray.getJSONObject(i);
//                Complaint complaint = new Complaint();
//
//                complaint.setComplaint(jsonObject.getString("complaint"));
//                complaint.setComplaintId(jsonObject.getLong("complaintId"));
//                complaint.setComplaintAgainstCategoryId(jsonObject.getLong("leafCategoryId"));
//                complaint.setCurrentlyAssignedTo(jsonObject.getString("assignedToFullName"));
//                // CmsDatabase cmsDB = new CmsDatabase();
//                // cmsDB.getRootCategoryId(context,
//                // complaint.getComplaintAgainstCategoryId());
//                complaint.setPermissions(jsonObject.getString("permissions"));
//                complaint.setDateOfLodging(jsonObject.getString("dateOfLodging"));
//                complaint.setTimeOfLodging(jsonObject.getString("timeOfLodging"));
//                complaint.setCurrentStatus(jsonObject.getString("status"));
//                complaint.setSourceId(jsonObject.getString("sourceId"));
//                JSONObject statusJson = new JSONObject(jsonObject.getString("statusBean"));
//                complaint.setStatusRemarks(statusJson.getString("statusRemarks"));
//                complaint.setStatusDate(statusJson.getString("dateOfStatus"));
//                ComplaintRating complaintRating = new ComplaintRating();
//                if (jsonObject.isNull("rating")) {
//                    complaintRating.setRating((float) 0);
//                } else {
//                    complaintRating.setRating((float) jsonObject.getInt("rating"));
//                }
//                complaint.setComplaintRating(complaintRating);
//                complaintList.add(complaint);
//            }
//            Log.i("CMS", "ServerDataUtil: complaintList: " + complaintList);
//            return complaintList;
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return null;
//
//    }
//
//    /**
//     * Places a request to the server to send an email.
//     *
//     * @param patientId the id of the patient requesting email.
//     * @param emailId the email id to which the mail has to be sent.
//     * @param subject the subject of the email.
//     * @param msg the body of the email
//     * @return "success" if email is sent successfully, false otherwise
//     */
//    public String sendEmailRequestToServer(String patientId, String emailId, String subject,
//            String msg, String type) {
//        String result = "";
//        ServerAddress serverAddress = ServerAddress.getInstance(context);
//        if (serverAddress != null) {
//            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
//            nameValuePairs.add(new BasicNameValuePair("patientId", patientId));
//            nameValuePairs.add(new BasicNameValuePair("emailId", emailId));
//            nameValuePairs.add(new BasicNameValuePair("subject", subject));
//            nameValuePairs.add(new BasicNameValuePair("msg", msg));
//
//            if (type.equals(Constant.EMAIL_TO_VISITOR)) {
//                String url = serverAddress.emailRequestUrl();
//                result = getServerResponse(url, nameValuePairs);
//            } else if (type.equals(Constant.EMAIL_DISCHARGE_DIARY)) {
//                String url = serverAddress.emailDischargeDiaryUrl();
//                result = getServerResponse(url, nameValuePairs);
//            }
//        }
//        return result;
//    }
//
//    /**
//     * Places a request to the server to send an SMS.
//     *
//     * @param patientId the id of the patient requesting email.
//     * @param contactNo the contact no. to which the SMS has to be sent.
//     * @param msg the body of the SMS
//     * @return "success" if SMS is sent successfully, false otherwise
//     */
//
//    public String sendSMSRequestToServer(String patientId, String contactNo, String msg) {
//        String result = "";
//        ServerAddress serverAddress = ServerAddress.getInstance(context);
//        if (serverAddress != null) {
//            String url = serverAddress.smsRequestUrl();
//            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
//            nameValuePairs.add(new BasicNameValuePair("patientId", patientId));
//            nameValuePairs.add(new BasicNameValuePair("contactNo", contactNo));
//            nameValuePairs.add(new BasicNameValuePair("msg", msg));
//            result = getServerResponse(url, nameValuePairs);
//        }
//        return result;
//    }
//
//
//}
