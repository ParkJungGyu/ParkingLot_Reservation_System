package com.project.afinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private long backKeyPressedTime = 0;
    private Button login;
    private TextView reg;
    private EditText etid,etpw;
    private String id, pw;
    private Toast toast;
    serviceApi service = RetrofitClient.getClient("http://ec2-3-38-241-60.ap-northeast-2.compute.amazonaws.com:3000").
            create(serviceApi.class); //서버 주소, 서버 연결

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login=findViewById(R.id.mainlogin); //로그인 버튼 할당
        reg=findViewById(R.id.mainreg); //회원가입 버튼 할당

        etpw=findViewById(R.id.mainpw); //패스워드 텍스트 할당
        etid=findViewById(R.id.mainid); //아이디 텍스트 할당

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id=etid.getText().toString(); //입력된 아이디 값
                pw=etpw.getText().toString(); //입력된 패스워드 값
                service.userLogin(new LoginData(id,pw)).enqueue(new Callback<code_message_Response>() { //서버로 LoginData 객체 전송 후 code_message_Response 객체 반환 받음
                    @Override
                    public void onResponse(Call<code_message_Response> call, Response<code_message_Response> response) { //전송이 성공했을 때

                        if(response.isSuccessful()){
                            code_message_Response result = response.body();
                            if(Integer.parseInt(result.getCode())==200){ //정상적으로 로그인이 되었을 경우
                                Toast.makeText(MainActivity.this, id+"님 환영합니다.", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(getApplicationContext(),map.class);
                                intent.putExtra("id",id);
                                startActivity(intent);
                            }
                            else if(Integer.parseInt(result.getCode())==205){ //아이디가 없을 경우
                                Toast.makeText(MainActivity.this, "아이디가 존재하지 않습니다.", Toast.LENGTH_SHORT).show();
                            }
                            else if(Integer.parseInt(result.getCode())==204){ //패스워드가 틀렸을 경우
                                Toast.makeText(MainActivity.this, "패스워드가 틀렸습니다.", Toast.LENGTH_SHORT).show();
                            }
                            else{ //예외의 경우
                                Toast.makeText(MainActivity.this, "정보를 입력해주세요", Toast.LENGTH_SHORT).show();
                            }
                            Log.e("res",response.body().getMessage());
                        }

                    }
                    @Override
                    public void onFailure(Call<code_message_Response> call, Throwable t) { //통신 에러 발생 시
                        Toast.makeText(MainActivity.this, "로그인 에러 발생", Toast.LENGTH_SHORT).show();
                        Log.e("로그인 에러 발생", t.getMessage());
                        t.printStackTrace(); // 에러 발생시 에러 발생 원인 단계별로 출력해줌

                    }
                });

            }
        });

        reg.setOnClickListener(new View.OnClickListener() { //회원가입 버튼 눌렸을 때
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),UserReg.class); //UserReg 액티비티 할당
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent); //UserReg 액티비티로 이동
            }
        });
    }
    @Override
    public void onBackPressed() { //뒤로 가기 버튼 눌렀을 경우
        if (System.currentTimeMillis() > backKeyPressedTime + 2500) {
            backKeyPressedTime = System.currentTimeMillis();
            toast=Toast.makeText(this, "뒤로 가기 버튼을 한 번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2500) {  //뒤로 가기 버튼 두 번 눌렀을 때 종료
            toast.cancel();
            toast=Toast.makeText(this,"이용해 주셔서 감사합니다.",Toast.LENGTH_SHORT);
            finishAffinity();
            toast.show();

        }
    }
}