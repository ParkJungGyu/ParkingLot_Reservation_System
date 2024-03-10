package com.project.afinal;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResAdapter extends BaseAdapter {
    serviceApi serviceApi = RetrofitClient.getClient("http://ec2-3-38-241-60.ap-northeast-2.compute.amazonaws.com:3000").create(serviceApi.class); //서버 주소,연결
    Context context=null;
    LayoutInflater layoutInflater=null;
    List<parkls> parkls;
    //Park_User_Data data;
    ArrayList<Park_User_Data> pud;

    public ResAdapter(Context context, List<parkls> parkls, ArrayList<Park_User_Data> pud){
        this.context=context;
        this.parkls=parkls;
        this.layoutInflater=LayoutInflater.from(context);
        this.pud=pud;
    }

    @Override
    public int getCount() {
        return parkls.size();
    }

    @Override
    public parkls getItem(int position) {
        return parkls.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View cview, ViewGroup cgroup) { //리스트뷰 객체 생성
        View view=layoutInflater.inflate(R.layout.parkinglot_info,null);

        TextView tx1=view.findViewById(R.id.textView1);
        TextView tx2=view.findViewById(R.id.textView2);
        Button bt1=view.findViewById(R.id.button1);

        tx1.setText(parkls.get(position).p_number);


        if(Integer.parseInt(parkls.get(position).is_park)==0&&Integer.parseInt(parkls.get(position).Reserv)==0){ //주차자리 예약 가능
            tx2.setText("예약 가능합니다.");
            Park_User_Data data=pud.get(position);
            bt1.setOnClickListener(new View.OnClickListener() { //버튼 누를 시 주차장 자리 예약
                @Override
                public void onClick(View view) {
                    serviceApi.save_parking(data).enqueue(new Callback<code_message_Response>() {
                        @Override
                        public void onResponse(Call<code_message_Response> call, Response<code_message_Response> response) {
                            if(response.isSuccessful()){
                                bt1.setEnabled(false); //예약 성공 후 버튼 비활성화
                                tx2.setText("예약 불가능합니다..");
                            }else{

                            }
                        }

                        @Override
                        public void onFailure(Call<code_message_Response> call, Throwable t) {

                        }
                    });
                }
            });
        }
        else{ //주차자리 예약 불가능
            tx2.setText("예약 불가능합니다.");
            bt1.setEnabled(false);
        }

        return view;
    }
}
