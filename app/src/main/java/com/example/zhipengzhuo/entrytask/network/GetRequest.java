package com.example.zhipengzhuo.entrytask.network;

import com.example.zhipengzhuo.entrytask.model.CategoryInfo;
import com.example.zhipengzhuo.entrytask.model.UserInfo;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetRequest {
    @GET("courseArticle/course/list4Page.do?")
    Observable<CategoryInfo> reqCategoryInfo(@Query("page") int page, @Query("fetchSize") int fetchSize);

    @GET("todo/api/v1.0/getinfo?")
    Observable<UserInfo> reqUserInfo(@Query("id") int id);
}
