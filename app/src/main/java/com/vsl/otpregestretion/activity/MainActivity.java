package com.vsl.otpregestretion.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.vsl.otpregestretion.constant.Constant;
import com.vsl.otpregestretion.constant.Resource;
import com.vsl.otpregestretion.databinding.ActivityMainBinding;
import com.vsl.otpregestretion.model.OtpModel;
import com.vsl.otpregestretion.viewmodels.OtpViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    String phoneNo, phOtp, data;

    OtpViewModel otpViewModel;

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        otpViewModel = new ViewModelProvider(this).get(OtpViewModel.class);

        binding.button1.setOnClickListener(v -> {
            final int[] i = {0};
            phoneNo = binding.editPhone.getText().toString();
            if (phoneNo.isEmpty()) {
                binding.editPhone.setError("Enter first");
                binding.editPhone.requestFocus();
            } else {
                otpViewModel.getOtpModelData().observe(this, finalData -> {
                    switch (finalData.status) {
                        case SUCCESS:
                            binding.textView.setText(finalData.data.getStatus());
                            binding.textView2.setText(finalData.data.getDetails());
                            while (i[0] < 1) {
                                Toast.makeText(MainActivity.this, Constant.SUCCESS, Toast.LENGTH_SHORT).show();
                                i[0]++;
                            }
                            data = binding.textView2.getText().toString();
                            break;
                        case LOADING:
//                        Toast.makeText(this, "Loading", Toast.LENGTH_SHORT).show();
                            break;
                        case ERROR:
                            while (i[0] < 1) {
                                Toast.makeText(MainActivity.this, Constant.SOMETHING_WRONG, Toast.LENGTH_SHORT).show();
                                i[0]++;
                            }
//                            binding.textView.setText(finalData.data.getStatus());
//                            binding.textView2.setText(finalData.data.getDetails());
                            break;
                    }
                });
                otpViewModel.getApiOtp(phoneNo);
            }
        });


        binding.button2.setOnClickListener(v -> {

            final int[] i = {0};
            phOtp = binding.editotp.getText().toString();

            if (phOtp.isEmpty()) {
                binding.editotp.setError("Enter first");
                binding.editotp.requestFocus();
            } else {
                otpViewModel.getOtpEnterData().observe(this, new Observer<Resource<OtpModel>>() {
                    @Override
                    public void onChanged(Resource<OtpModel> otpModelResource) {
                        if (otpModelResource.data != null) {
                            binding.textView.setText(otpModelResource.data.getStatus());
                            binding.textView2.setText(otpModelResource.data.getDetails());
                            while (i[0] < 1) {
                                Toast.makeText(MainActivity.this, Constant.SUCCESS, Toast.LENGTH_SHORT).show();
                                i[0]++;
                            }
                        } else {

//                            binding.textView.setText(otpModelResource.data.getStatus());
//                            binding.textView2.setText(otpModelResource.data.getDetails());
                        }
                    }
                });
                otpViewModel.checkApiOtp(data, phOtp);
            }
        });



    }
}