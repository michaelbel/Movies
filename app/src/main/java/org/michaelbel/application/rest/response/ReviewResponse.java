package org.michaelbel.application.rest.response;

import com.google.gson.annotations.SerializedName;

import org.michaelbel.application.rest.model.Review;

import java.util.List;

@SuppressWarnings("all")
public class ReviewResponse {

    @SerializedName("id")
    public int id;

    @SerializedName("page")
    public int page;

    @SerializedName("total_pages")
    public int totalPages;

    @SerializedName("total_results")
    public int totalResults;

    @SerializedName("results")
    public List<Review> reviewList;
}