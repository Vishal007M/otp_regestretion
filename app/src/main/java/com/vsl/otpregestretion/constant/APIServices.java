package com.vsl.otpregestretion.constant;

import com.vsl.otpregestretion.model.OtpModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface APIServices {

    @GET("+91{phone_no}/AUTOGEN")
    Call<OtpModel> getOtp(@Path("phone_no") String phone_no);

    @GET("VERIFY/{session_id}/{otp_input}")
    Call<OtpModel> getOtpCheck(@Path("session_id") String session_id,@Path("otp_input")  String otp_input);



}
