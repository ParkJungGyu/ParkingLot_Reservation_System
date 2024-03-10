package com.project.afinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.overlay.InfoWindow;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.Overlay;
import com.naver.maps.map.util.FusedLocationSource;
import com.naver.maps.map.util.MarkerIcons;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class map extends AppCompatActivity implements OnMapReadyCallback ,Overlay.OnClickListener{
    serviceApi serviceApi = RetrofitClient.getClient("http://ec2-3-38-241-60.ap-northeast-2.compute.amazonaws.com:3000").create(serviceApi.class);
    private long backKeyPressedTime = 0;
    private TextView tx;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(10,TimeUnit.SECONDS)
            .writeTimeout(10,TimeUnit.SECONDS)
            .build();

    private FusedLocationSource locationSource;
    private NaverMap naverMap;
    private Button regif,buf,logout;
    private EditText et;
    private List<parking> lm;
    private InfoWindow mInfoWindow;
    private String id;
    private one_park_space ops;
    private Toast toast;
    private Marker searchMark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        locationSource =
                new FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE);


        regif=findViewById(R.id.info);
        buf=findViewById(R.id.but);
        et=findViewById(R.id.address);
        logout=findViewById(R.id.logout);


        FragmentManager fm = getSupportFragmentManager();
        MapFragment mapFragment = (MapFragment)fm.findFragmentById(R.id.navermap);
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            fm.beginTransaction().add(R.id.navermap, mapFragment).commit();
        }
        mapFragment.getMapAsync(this );
        // 네이버 지도 api fragment에 할당

        Intent intent=getIntent();
        id=intent.getStringExtra("id"); //전 액티비티에서 id값 받기

        logout.setOnClickListener(new View.OnClickListener() { //로그아웃 버튼 눌렀을 때
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent); //메인 액티비티로 이동
                toast=Toast.makeText(map.this,"이용해 주셔서 감사합니다.",Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        regif.setOnClickListener(new View.OnClickListener() { //예약정보 버튼 눌렀을 때
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),Resinformation.class);
                intent.putExtra("id",id);
                //intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent); //예약 정보 액티비티로 이동

            }
        });
        buf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                Gson gson = new GsonBuilder().setLenient().create(); //json 형식으로 데이터를 받기위한 Gson 객체 선언

                Retrofit retrofitnaver = new Retrofit.Builder() //네이버 지도 api 연동 Retrofit 객체 선언
                        .baseUrl("https://naveropenapi.apigw.ntruss.com/")
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .client(client)
                        .build();

                retrofitnaver.create(RetrofitAddress.class).searchAddress(et.getText().toString()).enqueue(new Callback<AddressData>() { //주소검색 버튼을 눌렀을 때
                    @Override
                    public void onResponse(Call<com.project.afinal.AddressData> call, Response<com.project.afinal.AddressData> response) {
                        try{

                            String point = response.body().toString(); //검색한 주소의 데이터
                            String x = point.split("\n")[0]; //검색한 주소의 x좌표 값
                            String y = point.split("\n")[1]; //검색한 주소의 y좌표 값
                            Log.e("res",response.body().toString());


                        CameraPosition cameraPosition = new CameraPosition(
                                new LatLng(Double.parseDouble(y), Double.parseDouble(x)),  // 위치 지정
                                15                          // 줌 레벨
                        );
                            naverMap.setCameraPosition(cameraPosition); //카메라 위치 검색한 좌표로 이동
                            if(searchMark!=null) //마커가 존재한 경우 삭제
                            {
                                searchMark.setMap(null);
                            }
                            searchMark=new Marker(); //새로운 마커 생성
                            searchMark.setIcon(MarkerIcons.BLACK);
                            searchMark.setIconTintColor(Color.RED);
                            searchMark.setPosition(new LatLng(Double.parseDouble(y), Double.parseDouble(x))); //검색한 위치 좌표에 마커 할당
                            searchMark.setOnClickListener(new Overlay.OnClickListener() { //마커를 눌렀을 때
                                @Override
                                public boolean onClick(@NonNull Overlay overlay) {
                                    String url = "nmap://navigation?dlat="+y+"&dlng="+x+"&dname="+et.getText().toString()+"&appname=com.example.myapp";
                                    // 네이버 map 어플로 검색한 위치 이름과 x,y좌표값을 전송하는 url
                                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                    try {
                                        startActivity(intent); //url 실행
                                    } catch (ActivityNotFoundException e) { //url 실행 실패시 playstore에서 네이버 map 앱 검색화면으로 전환
                                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.nhn.android.nmap")));
                                    }
                                    return false;
                                }
                            });
                            searchMark.setMap(naverMap); //지도 객체에 마커 할당

                       }catch (Exception e){
                            Toast.makeText(map.this, "정확한 주소를 입력하세요.", Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<AddressData> call, Throwable t) {

                    }
                });

            }
        });

    }
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (locationSource.onRequestPermissionsResult(
                requestCode, permissions, grantResults)) {
            return;
        }
        super.onRequestPermissionsResult(
                requestCode, permissions, grantResults);
    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) { //네이버 map 객체 초기 할당될 때


        this.naverMap=naverMap;
        UiSettings uiSettings = naverMap.getUiSettings();
        uiSettings.setLocationButtonEnabled(true); //나의 위치 버튼 활성화

        mInfoWindow = new InfoWindow();

        naverMap.setLocationSource(locationSource);
        serviceApi.parkinfoget(new LoginData("aa","22")).enqueue(new Callback<ParkInfoData>() { //서버로 데이터 전송

            @Override
            public void onResponse(Call<ParkInfoData> call, Response<ParkInfoData> response) { //전송 완료시

                if(response.isSuccessful()){ //데이터 수신 완료시
                    ParkInfoData ls=response.body(); //주차장 정보 데이터 리스트 객체 할당
                    lm=ls.getparkinfo();
                    Log.e("1 : ",Integer.toString(lm.size()));
                    if(lm!=null){
                        Log.e("Marker : ","complete");
                        for(int i=0;i<lm.size();i++){
                            Marker marker = new Marker();
                            marker.setPosition(new LatLng(Double.parseDouble(lm.get(i).getParky()), Double.parseDouble(lm.get(i).getParkx())));
                            //주차장 x,y좌표 값대로 마커에 설정
                            marker.setMap(naverMap);
                            //마커 네이버 map에 할당
                            marker.setCaptionText(lm.get(i).getParkname());
                            //마커 이름 주차장 이름으로 설정
                            marker.setOnClickListener(map.this);
                            //마커 클릭 이벤트 핸들러 활성화
                        }

                    }

                }
                else{
                    Log.e("2 : ",Integer.toString(response.code()));
                }
            }
            @Override
            public void onFailure(Call<ParkInfoData> call, Throwable t) {
                t.printStackTrace();

            }
        });


    }
    public boolean onClick(Overlay overlay){ //지도 객체 눌렀을 때
        if (overlay instanceof Marker) { //객체가 마커인 경우
            Marker marker = (Marker) overlay;
            Log.e("onclick","first");
            Park_User_Data data=new Park_User_Data(id,marker.getCaptionText());


            if (marker.getInfoWindow() != null) { //정보창이 열려있을 경우 닫기
                mInfoWindow.close();
            }
            else { //정보창이 열려있지 않을 경우 열기
                serviceApi.get_space(data).enqueue(new Callback<one_park_space>() { //서버로 부터 마커의 주차장 정보 받기

                    @Override
                    public void onResponse(Call<one_park_space> call, Response<one_park_space> response) { //서버로 데이터 전송이 성공했을 때

                        if(response.isSuccessful()){ //데이터 수신 성공 시
                            ops=response.body();
                            mInfoWindow.setAdapter(new InfoWindow.DefaultViewAdapter(map.this) {
                                @NonNull
                                @Override
                                protected View getContentView(@NonNull InfoWindow infoWindow) {

                                    Marker marker = infoWindow.getMarker(); //누른 마커 객체 할당
                                    View view = View.inflate(map.this, R.layout.info_view, null); //map 클래스 객체에 정보창 view 할당
                                    ((TextView) view.findViewById(R.id.title)).setText(marker.getCaptionText()); //정보창에 해당 주차장 이름 할당
                                    ((TextView) view.findViewById(R.id.details)).setText(" 빈 주차 공간 : "+ops.getParkempty()); // 정보창에 해당 주차장의 빈 주차공간 할당
                                    Log.e("Si : ","third");
                                    return view;
                                }
                            });
                            Log.e("ops : " ,"second");
                        }
                        else{
                        }
                    }
                    @Override
                    public void onFailure(Call<one_park_space> call, Throwable t) { //서버로 데이터 전송이 실패했을 때
                        t.printStackTrace();

                    }
                });

                mInfoWindow.open(marker); //누른 마커 위에 정보창 열기
                marker.getInfoWindow().setOnClickListener(new Overlay.OnClickListener() { //마커의 정보창을 클릭했을 때
                    @Override
                    public boolean onClick(@NonNull Overlay overlay) {
                        data.setParkempty(ops.getParkempty()); //주차장 정보 객체에 빈 자리 수 할당
                        data.setParkspace(ops.getParkspace()); //주차장 정보 객체에 총 주차 공간 수 할당
                        Intent intent=new Intent(getApplicationContext(),ParkinglotRes.class); //주차 예약 액티비티 할당
                        intent.putExtra("data",data); //주차장 정보 객체 intent로 할당
                        startActivity(intent); //주차 예약 액티비티로 이동
                        return false;
                    }
                });
            }
            return true;
        }
        return false;
    }
    @Override
    public void onBackPressed() { //뒤로 가기 버튼 눌렀을 경우
        if (System.currentTimeMillis() > backKeyPressedTime + 2500) {
            backKeyPressedTime = System.currentTimeMillis();
            toast=Toast.makeText(this, "뒤로 가기 버튼을 한 번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2500) { //뒤로 가기 버튼 두 번 눌렀을 경우 종료
            toast.cancel();
            toast=Toast.makeText(this,"이용해 주셔서 감사합니다.",Toast.LENGTH_SHORT);
            finishAffinity(); //프로그램 종료
            toast.show();

        }
    }

}