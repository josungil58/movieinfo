package com.example.android.movieinfo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Singhal on 10/31/2015.
 */
public class MovieParse implements Parcelable {
    // http://arsviator.blogspot.kr/2010/10/parcelable%EC%9D%84-
    // %EC%82%AC%EC%9A%A9%ED%95%9C-%EC%98%A4%EB%B8%8C%EC%A0%9D%ED%8A%B8-%EC%A0%84%EB%8B%AC-object.html 참조

    private String title;
    private String poster;
    private String overview;
    private String voteAverage;
    private String releaseDate;

    public MovieParse(String title, String poster, String overview,
                 String voteAverage, String releaseDate){
        this.title = title;
        this.poster = poster;
        this.overview = overview;
        this.voteAverage = voteAverage;
        this.releaseDate = releaseDate;
    }

    public String getTitle() {
        return title;
    }

    public String getPoster() {
        return poster;
    }

    public String getOverview() {
        return overview;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    @Override
    public int describeContents() {
        // Parcel 하려는 Object의 종류를 정의한다
        return 0;
    }
    @Override
    public void writeToParcel(Parcel out, int flags) {
        // public void writeToParcel(Parcel dest, int flags)
        // 실제 Object를 serialization하는 method. Object의 각 element를 각각 parcel해 주어야 함
        out.writeString(title);
        out.writeString(poster);
        out.writeString(overview);
        out.writeString(voteAverage);
        out.writeString(releaseDate);
    }

    private MovieParse(Parcel in) {
        title = in.readString();
        poster = in.readString();
        overview = in.readString();
        voteAverage = in.readString();
        releaseDate = in.readString();
    }

    public static final Parcelable.Creator<MovieParse> CREATOR = new Parcelable.Creator<MovieParse>() {
        @Override
        public MovieParse createFromParcel(Parcel in) {
            return new MovieParse(in);
            // Parcel에서 data를 deserialize하는 단계. Parcelable.Creator type의 변수 CREATOR 정의
        }
        @Override
        public MovieParse[] newArray(int size) {
            return new MovieParse[size];
        }
    };
}
