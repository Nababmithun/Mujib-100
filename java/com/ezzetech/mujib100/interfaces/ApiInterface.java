package com.ezzetech.mujib100.interfaces;

import com.ezzetech.mujib100.DocumentariesApiModel.DocumentariesResponse;
import com.ezzetech.mujib100.GlobalNewsApiModel.GlobalNewsResponse;
import com.ezzetech.mujib100.booksApiModel.BooksResponse;
import com.ezzetech.mujib100.eventsApiModel.EventsResponse;
import com.ezzetech.mujib100.lettersApiModel.LettersResponse;
import com.ezzetech.mujib100.newsFeedApiModel.NewsFeedResponse;
import com.ezzetech.mujib100.photoAchiveApiModel.PhotoArchiveResponse;
import com.ezzetech.mujib100.pressRelease.PressReleaseResponse;
import com.ezzetech.mujib100.recognitionApiModel.RecognitionResponse;
import com.ezzetech.mujib100.speechesApiModel.SpeechesResponse;
import com.ezzetech.mujib100.stempApiModel.StempResponse;
import com.ezzetech.mujib100.timeLineApiModel.TimeLineResponse;
import com.ezzetech.mujib100.webApi.WishesResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {
    @GET("api/timelines")
    Call<TimeLineResponse> getTimeLineResponse();

    @GET("api/photo-archive")
    Call<PhotoArchiveResponse> getPhotoArchiveResponse();

    @GET("api/recognitions")
    Call<RecognitionResponse> getRecognitionResponse();

    @GET("api/speeches")
    Call<SpeechesResponse> getSpeechesResponse();

    @GET("api/documentaries")
    Call<DocumentariesResponse> getDocumentariesResponse();

    @GET("api/stamp")
    Call<StempResponse> getStampResponse();

    @GET("api/events")
    Call<EventsResponse> getEventResponse();

    @GET("api/press-release")
    Call<PressReleaseResponse> getPressReleaseResponse();

    @GET("api/books")
    Call<BooksResponse> getBooksResponse();

    @GET("api/letters")
    Call<LettersResponse> getLettersResponse();

    @GET("api/wishes")
    Call<WishesResponse> getwishesResponse();

    @GET("api/news-feed")
    Call<NewsFeedResponse> getNewsFeedResponse();

    @GET("api/global-news")
    Call<GlobalNewsResponse> getGlobalNewsResponse();
}
