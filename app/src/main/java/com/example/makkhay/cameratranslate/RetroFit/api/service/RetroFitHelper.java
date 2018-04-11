package com.example.makkhay.cameratranslate.RetroFit.api.service;

import com.example.makkhay.cameratranslate.RetroFit.api.model.WordHelper;
import com.google.android.gms.common.api.Api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetroFitHelper {
    /*
     * Retrofit get annotation with our URL
     * And our method that will return us details of student.
     */
    @GET("/api/v1.5/tr.json/translate?key=trnsl.1.1.20150923T140958Z.118b6f83cd292e46.b16c1a7d395aba18d6cf3f4c087a3d29433a7cbb")
    Call<WordHelper> findMeaning(@Query("lang") String lang, @Query("text") String text);


    @GET("/api/v1.5/tr.json/translate?key=trnsl.1.1.20150923T140958Z.118b6f83cd292e46.b16c1a7d395aba18d6cf3f4c087a3d29433a7cbb")
    Call<WordHelper> findMeaning(@Query("lang") String lang);


}
