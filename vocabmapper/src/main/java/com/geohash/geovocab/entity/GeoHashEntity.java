package com.geohash.geovocab.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity
@Table(name = "GeoHashEntity",indexes = { @Index(columnList = "word")})
public class GeoHashEntity implements Serializable {

	private static final long serialVersionUID = -1723798766434132067L;

	@Id
	private String hashVal;
	@Column
	private String word;

	public String getHashVal() {
		return hashVal;
	}

	public void setHashVal(String hashVal) {
		this.hashVal = hashVal;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((hashVal == null) ? 0 : hashVal.hashCode());
		result = prime * result + ((word == null) ? 0 : word.hashCode());
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
		GeoHashEntity other = (GeoHashEntity) obj;
		if (hashVal == null) {
			if (other.hashVal != null)
				return false;
		} else if (!hashVal.equals(other.hashVal))
			return false;
		if (word == null) {
			if (other.word != null)
				return false;
		} else if (!word.equals(other.word))
			return false;
		return true;
	}

	public GeoHashEntity(String hashVal, String word) {
		super();
		this.hashVal = hashVal;
		this.word = word;
	}

	public GeoHashEntity() {
		super();
	}

	@Override
	public String toString() {
		return "words [hashVal=" + hashVal + ", word=" + word + "]";
	}

}
