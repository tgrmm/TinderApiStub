package de.thg.tinder.mapper;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import de.thg.tinder.api.pojos.SmsValidatorResponse;

/**
 * 
 * Mapper Class that converts JSON Schema to pojos
 * @author Timo Grimm
 *
 */
public class JsonToPojoMapper {
	
	public static SmsValidatorResponse convertSMSValidatorResponseJsonToPojo(String jsonResponse) {
		Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
		jsonResponse = "{\"meta\":{\"status\":200},\"data\":{\"refresh_token\":\"eyJhbGciOiJIUzI1NiJ9.NDkxNzY0MzM4MDgyOA.o_gryqUbuoc1IOLk-DyR1X6I6ooWiDrwLnurwy2BonQ\",\"validated\":true}}";
		SmsValidatorResponse response = gson.fromJson(jsonResponse, SmsValidatorResponse.class);	
        return response;
	}
	
}
