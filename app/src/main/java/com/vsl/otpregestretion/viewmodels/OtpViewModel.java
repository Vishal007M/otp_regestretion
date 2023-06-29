package com.vsl.otpregestretion.viewmodels;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vsl.otpregestretion.constant.APIServices;
import com.vsl.otpregestretion.constant.Constant;
import com.vsl.otpregestretion.constant.Resource;
import com.vsl.otpregestretion.constant.RetroInstance;
import com.vsl.otpregestretion.model.OtpModel;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Path;


public class OtpViewModel extends ViewModel {

    private MutableLiveData<Resource<OtpModel>> otpModelData;
    private MutableLiveData<Resource<OtpModel>> otpEnterData;

    public OtpViewModel() {
        otpModelData = new MutableLiveData<>();
        otpEnterData = new MutableLiveData<>();
    }
    public MutableLiveData<Resource<OtpModel>> getOtpModelData() {
        return otpModelData;
    }
    public MutableLiveData<Resource<OtpModel>> getOtpEnterData() {
        return otpEnterData;
    }

    public void getApiOtp(String phone_no) {

        APIServices apiServices = RetroInstance.getRetroClient().create(APIServices.class);
        Call<OtpModel> call = apiServices.getOtp(phone_no);
        call.enqueue(new Callback<OtpModel>() {
            @Override
            public void onResponse(@NonNull Call<OtpModel> call, @NonNull Response<OtpModel> response) {

                OtpModel body = response.body();
                try {
                    if (body==null) {
                        otpModelData.setValue(Resource.error(Constant.SOMETHING_WRONG,null));
                    } else {
                        otpModelData.setValue(Resource.success(body));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<OtpModel> call, Throwable t) {
                try {
                    otpModelData.setValue(Resource.error(t.getMessage(),call.execute().body()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Log.e("Error :", t.getMessage());
            }
        });
    }

    public void checkApiOtp(String session_id, String otp_input) {
        APIServices apiServices = RetroInstance.getRetroClient().create(APIServices.class);
        Call<OtpModel> call = apiServices.getOtpCheck(session_id,otp_input);
        call.enqueue(new Callback<OtpModel>() {
            @Override
            public void onResponse(@NonNull Call<OtpModel> call, @NonNull Response<OtpModel> response) {
                try {
                    if (response.body() == null) {
                        otpEnterData.setValue(Resource.error(Constant.SOMETHING_WRONG,null));
                    } else {
                        OtpModel body = response.body();
                        otpEnterData.setValue(Resource.success(body));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<OtpModel> call, Throwable t) {
                otpEnterData.setValue(Resource.error(t.getMessage(),null));
                Log.e("Error :", t.getMessage());
            }
        });

    }

}
