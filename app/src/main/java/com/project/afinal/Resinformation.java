package com.project.afinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Resinformation extends AppCompatActivity {
    private TextView id;
    private serviceApi serviceApi = RetrofitClient.
            getClient("http://ec2-3-38-241-60.ap-northeast-2.compute.amazonaws.com:3000").
            create(serviceApi.class); //서버 주소, 서버 연결
    private List<parkiu> parkius;
    ArrayList<Park_User_Data> pud=new ArrayList<Park_User_Data>();
    CancelAdapter cancelAdapter;
    private ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resinformation);


        Intent intent=getIntent();
        String str=intent.getStringExtra("id"); //이전 액티비티로부터 id값 받기
        id=findViewById(R.id.userid);
        id.setText(str+"님의 예약정보");
        lv=findViewById(R.id.cancel_listview); // 리스트뷰 할당

        serviceApi.parkinfo_user(new Park_User_Data(str,"ss")).enqueue(new Callback<parkiu_list>() { //서버로 부터 유저 예약정보 받아오기
            @Override
            public void onResponse(Call<parkiu_list> call, Response<parkiu_list> response) {
                if(response.isSuccessful()){
                    parkius=response.body().getparkiu(); //parkiu 객체를 담은 parkiu_list 객체 할당
                    if(parkius!=null){
                        for(int i=0;i<parkius.size();i++){
                            Park_User_Data data=new Park_User_Data(str,parkius.get(i).parkname);
                            data.setP_number(parkius.get(i).p_number);
                            pud.add(data); //
                        }
                        cancelAdapter=new CancelAdapter(Resinformation.this,parkius,pud); //주차장 데이터, 유저 데이터 리스트 객체 할당
                        lv.setAdapter(cancelAdapter); //리스트뷰에 어댑터 연결

                    }

                }

            }

            @Override
            public void onFailure(Call<parkiu_list> call, Throwable t) {

            }
        });

    }
}
