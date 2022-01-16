package com.geohash.geovocab.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.elasticsearch.common.geo.GeoUtils;
import org.elasticsearch.geometry.utils.Geohash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.geohash.geovocab.dto.GeoVocabResponseDTO;
import com.geohash.geovocab.entity.GeoHashEntity;
import com.geohash.geovocab.entity.GeoHashRepo;
import com.geohash.geovocab.entity.GeoVocabPremium;
import com.geohash.geovocab.entity.GeoVocabPremiumRepo;
import com.geohash.geovocab.responsehub.ResponseHandler;

@RestController
@CrossOrigin(maxAge = 3600)
@RequestMapping("/geovocab")
public class GeoHashController {

	@Autowired
	GeoHashRepo geoHashRepo;

	@Autowired
	GeoVocabPremiumRepo geoVocabPremiumRepo;
	
	@RequestMapping("/index")
    public String index() {
        return "index";
    }
	
	@PostMapping("/premium")
	public ResponseEntity<Object> createPremiumGeoVocab(@RequestBody GeoVocabPremium requestBody) throws Exception {
		if (StringUtils.isBlank(requestBody.getGeoHash()))
			return ResponseHandler.generateResponse("Empty Geo Hash Value", HttpStatus.BAD_REQUEST, null);
		if (StringUtils.isBlank(requestBody.getMagicwords()))
			return ResponseHandler.generateResponse("Empty Magic Words Value", HttpStatus.BAD_REQUEST, null);
		List<String> wordList = Arrays.asList(StringUtils.split(requestBody.getMagicwords(), "-"));
		if (wordList.size() != 3)
			return ResponseHandler.generateResponse("Kindly enter the right format. Eg. word1-word2-word3",
					HttpStatus.BAD_REQUEST, null);
		if (!checkIfGeoHashIsValid(requestBody.getGeoHash()))
			return ResponseHandler.generateResponse("Invalid GeoHash", HttpStatus.BAD_REQUEST, null);
		if (StringUtils.length(requestBody.getGeoHash()) != 9)
			return ResponseHandler.generateResponse("Geohash should be 9 characters longs", HttpStatus.BAD_REQUEST,
					null);
		List<GeoHashEntity> geoHashEntityList = geoHashRepo.findByWords(wordList);
		if (geoHashEntityList.size() == 3)
			return ResponseHandler.generateResponse("These words are already mapped to another location",
					HttpStatus.BAD_REQUEST, null);
		geoVocabPremiumRepo.save(requestBody);
		return ResponseHandler.generateResponse("Successfully added a premium GeoVocab", HttpStatus.OK, requestBody);

	}

	@GetMapping("/geohash/{geohash}")
	public GeoHashEntity findByGeoHash(@PathVariable("geohash") String geoHash) {
		GeoHashEntity geoHashEntity = geoHashRepo.findByHashVal(geoHash);
		return geoHashEntity;
	}

	@GetMapping("/geohashes")
	public List<GeoHashEntity> getListOfGeoVocab() {
		List<GeoHashEntity> geoHashEntity = geoHashRepo.findAll();
		return geoHashEntity;
	}

	@GetMapping("/latitude/{latitude}/longitude/{longitude}/words")
	public ResponseEntity<Object> getGeoVocab(@PathVariable("latitude") String latitude,
			@PathVariable("longitude") String longitude) {
		try {
			if (!NumberUtils.isParsable(latitude))
				return ResponseHandler.generateResponse("Invalid Latitude", HttpStatus.BAD_REQUEST, null);
			if (!NumberUtils.isParsable(longitude))
				return ResponseHandler.generateResponse("Invalid Longitude", HttpStatus.BAD_REQUEST, null);
			double lat = NumberUtils.toFloat(latitude);
			double longit = NumberUtils.toFloat(longitude);
			String geovocab = new String();
			if (!GeoUtils.isValidLatitude(lat))
				return ResponseHandler.generateResponse("Invalid Latitude", HttpStatus.BAD_REQUEST, null);
			if (!GeoUtils.isValidLatitude(longit))
				return ResponseHandler.generateResponse("Invalid Longitude", HttpStatus.BAD_REQUEST, null);
			String geoHash = Geohash.stringEncode(longit, lat, 9);
			List<String> subHashes = Arrays.asList(geoHash.split("(?<=\\G.{3})"));
			GeoVocabPremium geoVocabPremium = geoVocabPremiumRepo.findByGeoHash(geoHash);
			if (geoVocabPremium != null) {
				GeoVocabResponseDTO response = new GeoVocabResponseDTO(geoVocabPremium.getMagicwords(), geoHash, lat,
						longit);
				return ResponseHandler.generateResponse("Here's the 3 magic words to your location", HttpStatus.OK,
						response);
			}
			List<GeoHashEntity> geoHashEntityList = geoHashRepo.findByIds(subHashes);
			Map<String, GeoHashEntity> geoHashMap = convertListToMapWithHashAsKey(geoHashEntityList);
			geovocab = geoHashMap.get(subHashes.get(0)).getWord() + "-" + geoHashMap.get(subHashes.get(1)).getWord()
					+ "-" + geoHashMap.get(subHashes.get(2)).getWord();
			GeoVocabResponseDTO response = new GeoVocabResponseDTO(geovocab, geoHash, lat, longit);
			return ResponseHandler.generateResponse("Here's the 3 magic words to your location", HttpStatus.OK,
					response);
		} catch (Exception e) {
			return ResponseHandler.generateResponse(e.toString(), HttpStatus.BAD_REQUEST, null);
		}
	}

	@GetMapping("/words/{words}/location")
	public ResponseEntity<Object> getGeoVocab(@PathVariable("words") String words) {
		try {
			String geoHash = new String();
			List<String> wordList = Arrays.asList(StringUtils.split(words, "-"));
			if (wordList.size() != 3)
				return ResponseHandler.generateResponse("Kindly enter the right format. Eg. word1-word2-word3",
						HttpStatus.BAD_REQUEST, null);
			GeoVocabPremium geoVocabPremium = geoVocabPremiumRepo.findByMagicwords(words);
			if (geoVocabPremium != null) {
				geoHash = geoVocabPremium.getGeoHash();
				double latitude = Geohash.decodeLatitude(geoHash);
				double longitude = Geohash.decodeLongitude(geoHash);
				GeoVocabResponseDTO response = new GeoVocabResponseDTO(geoVocabPremium.getMagicwords(), geoHash, latitude,
						longitude);
				return ResponseHandler.generateResponse("Here's the 3 magic words to your location", HttpStatus.OK,
						response);
			}
			List<GeoHashEntity> geoHashEntityList = geoHashRepo.findByWords(wordList);
			if (geoHashEntityList.size() != 3)
				return ResponseHandler.generateResponse("Could not find a location associated to the geovocab",
						HttpStatus.BAD_REQUEST, null);
			Map<String, GeoHashEntity> geoHashMap = convertListToMapWithWordAsKey(geoHashEntityList);
			geoHash = geoHashMap.get(wordList.get(0)).getHashVal() + geoHashMap.get(wordList.get(1)).getHashVal()
					+ geoHashMap.get(wordList.get(2)).getHashVal();
			double latitude = Geohash.decodeLatitude(geoHash);
			double longitude = Geohash.decodeLongitude(geoHash);
			GeoVocabResponseDTO response = new GeoVocabResponseDTO(words, geoHash, latitude, longitude);
			return ResponseHandler.generateResponse("Here's the location pointed by your 3 magic words", HttpStatus.OK,
					response);
		} catch (Exception e) {
			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
		}
	}

	public Map<String, GeoHashEntity> convertListToMapWithHashAsKey(List<GeoHashEntity> list) {
		Map<String, GeoHashEntity> map = list.stream()
				.collect(Collectors.toMap(GeoHashEntity::getHashVal, Function.identity()));
		return map;
	}

	public Map<String, GeoHashEntity> convertListToMapWithWordAsKey(List<GeoHashEntity> list) {
		Map<String, GeoHashEntity> map = list.stream()
				.collect(Collectors.toMap(GeoHashEntity::getWord, Function.identity()));
		return map;
	}

	public boolean checkIfGeoHashIsValid(String geoHash) {
		char[] BASE_32 = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j',
				'k', 'm', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };
		boolean[] isvalid = new boolean[Character.MAX_VALUE + 1];
		for (char c : BASE_32) {
			isvalid[c] = true;
		}
		for (char c : geoHash.toCharArray()) {
			if (!isvalid[c]) {
				return false;
			}
		}
		return true;
	}
}
