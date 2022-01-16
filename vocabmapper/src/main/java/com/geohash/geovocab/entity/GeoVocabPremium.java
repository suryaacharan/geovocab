package com.geohash.geovocab.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity
@Table(name = "GeoVocabPremium",indexes = { @Index(columnList = "magicwords")})
public class GeoVocabPremium implements Serializable {

	private static final long serialVersionUID = -1723798766434142029L;

	@Id
	private String geoHash;
	@Column
	private String magicwords;

	public String getGeoHash() {
		return geoHash;
	}

	public void setGeoHash(String geoHash) {
		this.geoHash = geoHash;
	}

	public String getMagicwords() {
		return magicwords;
	}

	public void setMagicwords(String magicwords) {
		this.magicwords = magicwords;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((geoHash == null) ? 0 : geoHash.hashCode());
		result = prime * result + ((magicwords == null) ? 0 : magicwords.hashCode());
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
		GeoVocabPremium other = (GeoVocabPremium) obj;
		if (geoHash == null) {
			if (other.geoHash != null)
				return false;
		} else if (!geoHash.equals(other.geoHash))
			return false;
		if (magicwords == null) {
			if (other.magicwords != null)
				return false;
		} else if (!magicwords.equals(other.magicwords))
			return false;
		return true;
	}

	public GeoVocabPremium(String geoHash, String magicwords) {
		super();
		this.geoHash = geoHash;
		this.magicwords = magicwords;
	}

	@Override
	public String toString() {
		return "GeoVocabPremium [geoHash=" + geoHash + ", magicwords=" + magicwords + "]";
	}

	public GeoVocabPremium() {
		super();

	}

}
