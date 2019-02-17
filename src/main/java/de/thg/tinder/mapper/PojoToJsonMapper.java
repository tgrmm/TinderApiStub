package de.thg.tinder.mapper;

import java.lang.reflect.Type;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import de.thg.tinder.api.pojos.Location;
import de.thg.tinder.api.pojos.LoginRequest;
import de.thg.tinder.api.pojos.PhoneNumber;
import de.thg.tinder.api.pojos.Profile;
import de.thg.tinder.api.pojos.SmsValidatorRequest;
import de.thg.tinder.api.pojos.User;

/**
 * Mapper Class that converts pojos to JSON schema
 * 
 * @author Timo Grimm
 *
 */
public class PojoToJsonMapper {
	
	public static String convertLocationPojoToJson(String latitude, String longitude) {
		Location location = new Location();
		location.setForceFetchResources(Boolean.TRUE);
		location.setLat(Double.valueOf(latitude));
		location.setLon(Double.valueOf(longitude));
        Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
        Type type = new TypeToken<Location>() {}.getType();
        return gson.toJson(location, type);		
	}
	
	public static String convertPhoneNumberPojoToJson(String phoneNumber) {
		PhoneNumber number = new PhoneNumber();
		number.setPhoneNumber(phoneNumber);
        Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
        Type type = new TypeToken<PhoneNumber>() {}.getType();
        return gson.toJson(number, type);		
	}
	
	public static String convertSMSValidatorRequestPojoToJson(String phoneNumber, String optCode) {
		SmsValidatorRequest request = new SmsValidatorRequest();
		request.setPhoneNumber(phoneNumber);
		request.setIsUpdate(Boolean.FALSE);
		request.setOtpCode(optCode);
        Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
        Type type = new TypeToken<SmsValidatorRequest>() {}.getType();
        return gson.toJson(request, type);		
	}
	
	public static String convertLoginRequestPojoToJson(String phoneNumber, String refreshToken) {
		LoginRequest request = new LoginRequest();
		request.setPhoneNumber(phoneNumber);
		request.setRefreshToken(refreshToken);
        Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
        Type type = new TypeToken<SmsValidatorRequest>() {}.getType();
        return gson.toJson(request, type);		
	}
	
	public static String convertProfilePojoToJson(int minimumAge, int maximumAge, int maximumDistance) {
		Profile profile = new Profile();
		profile.setUser(new User());
		profile.getUser().setAgeFilterMax(maximumAge);
		profile.getUser().setAgeFilterMin(minimumAge);
		profile.getUser().setDistanceFilter(maximumDistance);
        Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
        Type type = new TypeToken<Profile>() {}.getType();
        return gson.toJson(profile, type);		
	}
}
