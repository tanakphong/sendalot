package com.deverdie.sendalot;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.github.gcacace.signaturepad.views.SignaturePad;

public class SignatureActivity extends AppCompatActivity implements View.OnClickListener {

    private SignaturePad mSignaturePad;
    private Button mClearButton;
    private Button mSaveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signature);
        bindView();
    }

    private void bindView() {
        mSignaturePad = findViewById(R.id.signature_pad);
        mClearButton = findViewById(R.id.clear_button);
        mSaveButton = findViewById(R.id.save_button);

        mSignaturePad.setOnSignedListener(new SignaturePad.OnSignedListener() {
            @Override
            public void onStartSigning() {
                Toast.makeText(getApplicationContext(), "OnStartSigning", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSigned() {
                mSaveButton.setEnabled(true);
                mClearButton.setEnabled(true);
            }

            @Override
            public void onClear() {
                mSaveButton.setEnabled(false);
                mClearButton.setEnabled(false);
            }
        });
        mClearButton.setOnClickListener(this);
        mSaveButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save_button:
//                Bitmap signatureBitmap = mSignaturePad.getSignatureBitmap();
//                ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                signatureBitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
//                uploadImage(stream.toByteArray());
                Toast.makeText(getApplicationContext(), "Save Signature.", Toast.LENGTH_SHORT).show();
                break;
            case R.id.clear_button:
                mSignaturePad.clear();
                break;
            default:

        }
    }


//    private void uploadImage(byte[] imageBytes) {
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);
//
//        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), imageBytes);
//
//        MultipartBody.Part body = MultipartBody.Part.createFormData("file", "image.png", requestFile);
//        Call<Response> call = retrofitInterface.uploadImage(body);
//        call.enqueue(new Callback<Response>() {
//            @Override
//            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
//
//                if (response.isSuccessful()) {
//
//                    Response responseBody = response.body();
//                    Snackbar.make(findViewById(android.R.id.content), responseBody.getSuccess(),Snackbar.LENGTH_SHORT).show();
//
//                } else {
//
//                    ResponseBody errorBody = response.errorBody();
//
//                    Gson gson = new Gson();
//
//                    try {
//
//                        Response errorResponse = gson.fromJson(errorBody.string(), Response.class);
//                        Snackbar.make(findViewById(android.R.id.content), errorResponse.getSuccess(),Snackbar.LENGTH_SHORT).show();
//
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Response> call, Throwable t) {
//                Log.d(TAG, "onFailure: "+t.getLocalizedMessage());
//            }
//        });
//    }
}
