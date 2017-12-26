package org.michaelbel.application.rest.api;

import org.michaelbel.application.rest.model.Person;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

@SuppressWarnings("all")
public interface PEOPLE {

    @GET("person/{person_id}?")
    Call<Person> getDetails(
        @Path("person_id") int id,
        @Query("api_key") String apiKey,
        @Query("language") String language,
        @Query("append_to_response") String responce
    );

    @GET("person/{person_id}/movie_credits?")
    Call<?> getMovieCredits(
            @Path("person_id") int id,
            @Query("api_key") String apiKey,
            @Query("language") String language
    );

    @GET("person/{person_id}/tv_credits?")
    Call<?> getTVCredits(
            @Path("person_id") int id,
            @Query("api_key") String apiKey,
            @Query("language") String language
    );

    @GET("person/{person_id}/combined_credits?")
    Call<?> getCombinedCredits(
            @Path("person_id") int id,
            @Query("api_key") String apiKey,
            @Query("language") String language
    );

    @GET("person/{person_id}/external_ids?")
    Call<?> getExternalIDs(
            @Path("person_id") int id,
            @Query("api_key") String apiKey,
            @Query("language") String language
    );

    @GET("person/{person_id}/images?")
    Call<?> getImages(
            @Path("person_id") int id,
            @Query("api_key") String apiKey
    );

    @GET("person/{person_id}/tagged_images?")
    Call<?> getTaggedImages(
            @Path("person_id") int id,
            @Query("api_key") String apiKey,
            @Query("page") int page
    );

    @GET("person/{person_id}/changes?")
    Call<?> getChanges(
            @Path("person_id") int id,
            @Query("api_key") String apiKey,
            @Query("end_date") String endDate,
            @Query("start_date") String startDate,
            @Query("page") int page
    );

    @GET("person/latest?")
    Call<?> getLatest(
            @Query("api_key") String apiKey,
            @Query("language") String language
    );

    @GET("person/popular?")
    Call<?> getLatest(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int page
    );
}