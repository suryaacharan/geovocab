package com.geohash.geovocab.dto;

import java.io.Serializable;

public class GeoVocabResponseDTO implements Serializable {

	private static final long serialVersionUID = -1723798766434132024L;

	private String geoVocab;
	private String geoHash;
	private double latitude;
	private double longitude;

	public String getGeoVocab() {
		return geoVocab;
	}

	public void setGeoVocab(String geoVocab) {
		this.geoVocab = geoVocab;
	}

	public String getGeoHash() {
		return geoHash;
	}

	public void setGeoHash(String geoHash) {
		this.geoHash = geoHash;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((geoHash == null) ? 0 : geoHash.hashCode());
		result = prime * result + ((geoVocab == null) ? 0 : geoVocab.hashCode());
		long temp;
		temp = Double.doubleToLongBits(latitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(longitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GeoVocabResponseDTO other = (GeoVocabResponseDTO) obj;
		if (geoHash == null) {
			if (other.geoHash != null)
				return false;
		} else if (!geoHash.equals(other.geoHash))
			return false;
		if (geoVocab == null) {
			if (other.geoVocab != null)
				return false;
		} else if (!geoVocab.equals(other.geoVocab))
			return false;
		if (Double.doubleToLongBits(latitude) != Double.doubleToLongBits(other.latitude))
			return false;
		if (Double.doubleToLongBits(longitude) != Double.doubleToLongBits(other.longitude))
			return false;
		return true;
	}

	public GeoVocabResponseDTO(String geoVocab, String geoHash, double latitude, double longitude) {
		super();
		this.geoVocab = geoVocab;
		this.geoHash = geoHash;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public GeoVocabResponseDTO() {
		super();

	}

}