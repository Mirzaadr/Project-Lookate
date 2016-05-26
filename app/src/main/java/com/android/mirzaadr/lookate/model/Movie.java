package com.android.mirzaadr.lookate.model;

public class Movie {
	private String title, thumbnailUrl, genre, rating, alamat, testimoni, keterangan, year;
		
	public Movie() {
	}

	public Movie(String name, String thumbnailUrl, String year, String rating,
			String genre, String alamat, String testimoni, String keterangan) {
		this.title = name;
		this.thumbnailUrl = thumbnailUrl;
		this.year = year;
		this.rating = rating;
		this.genre = genre;
		this.alamat = alamat;
		this.testimoni = testimoni;
		this.keterangan = keterangan;
		
	}
	
	public String getThumbnailUrl() {
		return thumbnailUrl;
	}

	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String name) {
		this.title = name;
	}

	public String getTestimoni() {
		return testimoni;
	}

	public void setTestimoni(String testimoni) {
		this.testimoni = testimoni;
	}
	
	public String getAlamat() {
		return alamat;
	}

	public void setAlamat(String alamat) {
		this.alamat = alamat;
	}
	
	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}
	
	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getKeterangan() {
		return keterangan;
	}

	public void setKeterangan(String keterangan) {
		this.keterangan = keterangan;
	}
	
}
