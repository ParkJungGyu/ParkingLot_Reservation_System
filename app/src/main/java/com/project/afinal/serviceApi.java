package com.project.afinal;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface serviceApi {
    @POST("/user/login") //서버의 login 대응 , LoginData 객체 전송, code_message_Response 객체 받음
    Call<code_message_Response> userLogin(@Body LoginData data); //로그인

    @POST("/user/join") //서버의 join 대응 , JoinData 객체 전송, code_message_Response 객체 받음
    Call<code_message_Response> userJoin(@Body JoinData data); //회원 가입

    @POST("/user/parkinfo") //서버의 parkinfo 대응 , LoginData 객체 전송, ParkInfoData 객체 받음
    Call<ParkInfoData> parkinfoget(@Body LoginData data); //주차장 정보 받기

    @POST("/user/one_park_space") //서버의 one_park_space 대응 , Park_User_Data 객체 전송, one_park_space 객체 받음
    Call<one_park_space> get_space(@Body Park_User_Data data); // 주차 자리 정보 받기

    @POST("/user/parkinglotspace") //서버의 parkinglotspace 대응 , Park_User_Data 객체 전송, parkls_List 객체 받음
    Call<parkls_List> parkinglotspace(@Body Park_User_Data data); //주차장 공간 조회

    @POST("/user/save_parkinfo") //서버의 save_parkinfo 대응 , Park_User_Data 객체 전송, code_message_Response 객체 받음
    Call<code_message_Response> save_parking(@Body Park_User_Data data); //주차장 예약

    @POST("/user/delete_parkinfo") //서버의 delete_parkinfo 대응 , Park_User_Data 객체 전송, code_message_Response 객체 받음
    Call<code_message_Response> delete_parking(@Body Park_User_Data data); //주차장 예약 취소

    @POST("/user/parkinfo_user") //서버의 parkinfo_user 대응 , Park_User_Data 객체 전송, parkiu_list 객체 받음
    Call<parkiu_list> parkinfo_user(@Body Park_User_Data data); //유저 예약 정보 받기

}