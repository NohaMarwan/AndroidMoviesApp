package com.life.ammar.movies;

import java.util.ArrayList;
import java.util.List;

public class MovieEntry {

private boolean adult;
private String backdropPath;
private List<Integer> genreIds = new ArrayList<Integer>();
private int id;
private String originalLanguage;
private String originalTitle;
private String overview;
private double popularity;
private String posterPath;
private String releaseDate;
private String title;
private boolean video;
private double voteAverage;
private int voteCount;


public MovieEntry() {}

public MovieEntry(boolean adult, String backdropPath, List<Integer> genreIds, int id,
                  String originalLanguage, String originalTitle, String overview,
                  double popularity, String posterPath, String releaseDate, String title,
                  boolean video, double voteAverage, int voteCount) {
    this.adult = adult;
    this.backdropPath = backdropPath;
    this.genreIds = genreIds;
    this.id = id;
    this.originalLanguage = originalLanguage;
    this.originalTitle = originalTitle;
    this.overview = overview;
    this.popularity = popularity;
    this.posterPath = posterPath;
    this.releaseDate = releaseDate;
    this.title = title;
    this.video = video;
    this.voteAverage = voteAverage;
    this.voteCount = voteCount;
}


public boolean isAdult() {
    return adult;
}


public void setAdult(boolean adult) {
    this.adult = adult;
}

public MovieEntry withAdult(boolean adult) {
    this.adult = adult;
    return this;
}

public String getBackdropPath() {
    return backdropPath;
}

public void setBackdropPath(String backdropPath) {
    this.backdropPath = backdropPath;
}

public MovieEntry withBackdropPath(String backdropPath) {
    this.backdropPath = backdropPath;
    return this;
}

public List<Integer> getGenreIds() {
    return genreIds;
}


public void setGenreIds(List<Integer> genreIds) {
    this.genreIds = genreIds;
}

public MovieEntry withGenreIds(List<Integer> genreIds) {
    this.genreIds = genreIds;
    return this;
}

public int getId() {
    return id;
}


public void setId(int id) {
    this.id = id;
}

public MovieEntry withId(int id) {
    this.id = id;
    return this;
}


public String getOriginalLanguage() {
return originalLanguage;
}

/**
*
* @param originalLanguage
* The original_language
*/
public void setOriginalLanguage(String originalLanguage) {
this.originalLanguage = originalLanguage;
}

public MovieEntry withOriginalLanguage(String originalLanguage) {
this.originalLanguage = originalLanguage;
return this;
}

/**
*
* @return
* The originalTitle
*/
public String getOriginalTitle() {
return originalTitle;
}

/**
*
* @param originalTitle
* The original_title
*/
public void setOriginalTitle(String originalTitle) {
this.originalTitle = originalTitle;
}

public MovieEntry withOriginalTitle(String originalTitle) {
this.originalTitle = originalTitle;
return this;
}

/**
*
* @return
* The overview
*/
public String getOverview() {
return overview;
}

/**
*
* @param overview
* The overview
*/
public void setOverview(String overview) {
this.overview = overview;
}

public MovieEntry withOverview(String overview) {
this.overview = overview;
return this;
}

/**
*
* @return
* The popularity
*/
public double getPopularity() {
return popularity;
}

/**
*
* @param popularity
* The popularity
*/
public void setPopularity(double popularity) {
this.popularity = popularity;
}

public MovieEntry withPopularity(double popularity) {
this.popularity = popularity;
return this;
}

/**
*
* @return
* The posterPath
*/
public String getPosterPath() {
return posterPath;
}

/**
*
* @param posterPath
* The poster_path
*/
public void setPosterPath(String posterPath) {
this.posterPath = posterPath;
}

public MovieEntry withPosterPath(String posterPath) {
this.posterPath = posterPath;
return this;
}

/**
*
* @return
* The releaseDate
*/
public String getReleaseDate() {
return releaseDate;
}

/**
*
* @param releaseDate
* The release_date
*/
public void setReleaseDate(String releaseDate) {
this.releaseDate = releaseDate;
}

public MovieEntry withReleaseDate(String releaseDate) {
this.releaseDate = releaseDate;
return this;
}

/**
*
* @return
* The title
*/
public String getTitle() {
return title;
}

/**
*
* @param title
* The title
*/
public void setTitle(String title) {
this.title = title;
}

public MovieEntry withTitle(String title) {
this.title = title;
return this;
}

/**
*
* @return
* The video
*/
public boolean isVideo() {
return video;
}

/**
*
* @param video
* The video
*/
public void setVideo(boolean video) {
this.video = video;
}

public MovieEntry withVideo(boolean video) {
this.video = video;
return this;
}

/**
*
* @return
* The voteAverage
*/
public double getVoteAverage() {
return voteAverage;
}

/**
*
* @param voteAverage
* The vote_average
*/
public void setVoteAverage(double voteAverage) {
this.voteAverage = voteAverage;
}

public MovieEntry withVoteAverage(double voteAverage) {
this.voteAverage = voteAverage;
return this;
}

/**
*
* @return
* The voteCount
*/
public int getVoteCount() {
return voteCount;
}

/**
*
* @param voteCount
* The vote_count
*/
public void setVoteCount(int voteCount) {
this.voteCount = voteCount;
}

public MovieEntry withVoteCount(int voteCount) {
    this.voteCount = voteCount;
    return this;
}
}
