package com.project.afinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserReg extends AppCompatActivity {

    private Button ok;
    private Button cancel;
    private EditText id,pw,email;
    serviceApi service = RetrofitClient.
            getClient("http://ec2-43-200-49-74.ap-northeast-2.compute.amazonaws.com:3000").
            create(serviceApi.class); //서버 주소, 서버 연결
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_reg);

        ok=findViewById(R.id.regok); //ok버튼 할당
        cancel=findViewById(R.id.regcancel);//cancel버튼 할당


        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                id=findViewById(R.id.regid);
                email=findViewById(R.id.regemail);
                pw=findViewById(R.id.regpw);
                String strid=id.getText().toString(); // ID입력값
                String stremail=email.getText().toString(); // Email입력값
                String strpw=pw.getText().toString(); // Pw 입력값


                Pattern pattern = android.util.Patterns.EMAIL_ADDRESS; //이메일 형식 확인용 패턴
                if(strid.length()>=4){ //id 4글자 이상
                    if(strpw.length()>=4){ //pw 4글자 이상
                        if(stremail.length()>0){ //email 1글자 이상
                            if(pattern.matcher(stremail).matches()){ //이메일 형식 확인
                                service.userJoin(new JoinData(strid,strpw,stremail)).enqueue(new Callback<code_message_Response>() { //서버에 id,pw,email 값 전송
                                    @Override
                                    public void onResponse(Call<code_message_Response> call, Response<code_message_Response> response) {
                                        code_message_Response result = response.body();

                                        if(response.isSuccessful()){ //서버로 전송 완료시
                                            if (Integer.parseInt(result.getCode())== 200) { //서버로 부터 값 200을 받았을 경우
                                                Toast.makeText(UserReg.this, strid+"님 회원가입을 환영합니다.", Toast.LENGTH_SHORT).show();
                                                Intent intent=new Intent(getApplicationContext(),MainActivity.class); //MainActivity로 이동
                                                startActivity(intent);
                                            }
                                            else{ // 중복된 아이디
                                                Toast.makeText(UserReg.this, "중복된 아이디입니다.", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<code_message_Response> call, Throwable t) { //서버로 전송 실패시
                                        Toast.makeText(UserReg.this, "회원가입 에러 발생", Toast.LENGTH_SHORT).show();
                                        Log.e("회원가입 에러 발생", t.getMessage());
                                        t.printStackTrace(); // 에러 발생시 에러 발생 원인 단계별로 출력

                                    }
                                });
                            }
                            else {
                                Toast.makeText(UserReg.this, "이메일 형식이 아닙니다.", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(UserReg.this, "이메일을 입력해주세요", Toast.LENGTH_SHORT).show();
                        }

                    }
                    else{
                        Toast.makeText(UserReg.this, "비밀번호를 4자 이상 입력해주세요.", Toast.LENGTH_SHORT).show();
                    }

                }
                else{
                    Toast.makeText(UserReg.this, "아이디를 4자 이상 입력해주세요.", Toast.LENGTH_SHORT).show();
                }


            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            } //액티비티 종료
        });
    }

    @Override
    public void onBackPressed() {

    }

}
