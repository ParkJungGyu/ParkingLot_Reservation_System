package com.project.afinal;

import android.content.Context;
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

public class CancelAdapter extends BaseAdapter {
    serviceApi serviceApi = RetrofitClient.getClient("http://ec2-3-38-241-60.ap-northeast-2.compute.amazonaws.com:3000").create(serviceApi.class); //서버 주소, 서버 연결
    Context context=null;
    LayoutInflater layoutInflater=null;
    List<parkiu> parkius=null;
    //Park_User_Data data;
    ArrayList<Park_User_Data> pud;

    public CancelAdapter(Context context, List<parkiu> parkius, ArrayList<Park_User_Data> pud){
        this.context=context;
        this.parkius=parkius;
        this.layoutInflater=LayoutInflater.from(context);
        this.pud=pud;
    }

    @Override
    public int getCount() {
        return parkius.size();
    }

    @Override
    public parkiu getItem(int position) {
        return parkius.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View cview, ViewGroup cgroup) { //리스트뷰의 뷰 생성
        View view=layoutInflater.inflate(R.layout.parkinglot_info,null);

        TextView tx1=view.findViewById(R.id.textView1);
        TextView tx2=view.findViewById(R.id.textView2);
        Button bt1=view.findViewById(R.id.button1);


        tx1.setText(parkius.get(position).parkname);
        tx2.setText(parkius.get(position).p_number+"\n"+parkius.get(position).getRdate());
        Park_User_Data data=pud.get(position);
        bt1.setText("취소");
            bt1.setOnClickListener(new View.OnClickListener() { //취소 버튼 클릭시
                @Override
                public void onClick(View view) {
                    serviceApi.delete_parking(data).enqueue(new Callback<code_message_Response>() { //취소할 유저 예약 데이터 서버로 전송 후 데이터 수신
                        @Override
                        public void onResponse(Call<code_message_Response> call, Response<code_message_Response> response) { //수신 성공시
                            if(response.isSuccessful()){
                                bt1.setEnabled(false); //버튼 비활성화
                                bt1.setText("취소완료");
                            }else{

                            }
                        }

                        @Override
                        public void onFailure(Call<code_message_Response> call, Throwable t) { // 수신 실패시

                        }
                    });
                }});

        return view;
    }
}
