package com.project.afinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ParkinglotRes extends AppCompatActivity {
    serviceApi serviceApi = RetrofitClient.
            getClient("http://ec2-3-38-241-60.ap-northeast-2.compute.amazonaws.com:3000").
            create(serviceApi.class); //서버 주소, 서버 연결
    Park_User_Data park_user_data;
    ListView lv;
    List<parkls> pls;
    ArrayList<Park_User_Data> pud=new ArrayList<Park_User_Data>();
    TextView tx;
    ResAdapter resAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent=getIntent();
        park_user_data=(Park_User_Data) intent.getSerializableExtra("data"); //이전 액티비티에서 Park_User_Data 객체 받아오기


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parkinglot_res);
        lv=findViewById(R.id.parking_list); //리스트뷰 할당
        tx=findViewById(R.id.parkname); //텍스트뷰 할당
        tx.setText(park_user_data.getParkName()+"주차장 정보"); //상단 텍스트 설정


        serviceApi.parkinglotspace(park_user_data).enqueue(new Callback<parkls_List>() { //주차장 정보 전송 후 주차장 세부 데이터 수신
            @Override
            public void onResponse(Call<parkls_List> call, Response<parkls_List> response) { //수신 성공 시
                if(response.isSuccessful()){
                    parkls_List pp=response.body(); //서버로 부터 parkls_List 객체 반환
                    pls=pp.getparkls();
                    Log.e("pls : ","f");
                    Log.e("pls : ",pls.get(0).getP_number());

                    for(int i=0;i<pls.size();i++){
                        Park_User_Data pd=new Park_User_Data(park_user_data.getid(),park_user_data.getParkName());
                        pd.setP_number(pls.get(i).p_number);
                        pud.add(pd);
                        Log.e("is_park."+i+" ",pud.get(i).getP_number());
                    }

                    resAdapter =new ResAdapter(ParkinglotRes.this,pls,pud); //주차장 정보 뷰 생성 어댑터에 받아온 주차 정보 할당
                    lv.setAdapter(resAdapter); //리스트뷰에 뷰 생성 어댑터연결

                }else{
                    Log.e("fail : ",park_user_data.getid());

                }

            }

            @Override
            public void onFailure(Call<parkls_List> call, Throwable t) { //수신 실패시

            }
        });

    }

}