package com.fb.in.app.reporting.service.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fb.in.app.reporting.dao.UserDAO;
import com.fb.in.app.reporting.model.vo.BrandVo;
import com.fb.in.app.reporting.request.BrandRequest;
import com.fb.in.app.reporting.response.BrandListResponse;
import com.fb.in.app.reporting.response.UserDetailsResponse;
import com.fb.in.app.reporting.service.UserService;


@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	UserDAO userDAO;
	
	private static Map<String,UserDetailsResponse> UserDetailsMap = new HashMap<String,UserDetailsResponse>();
	



	@Override
	public BrandListResponse getBrand(String userId, String clientId, BrandRequest brandRequest) throws SQLException,Exception{
		 
		BrandListResponse userBrands = userDAO.getBrand(userId,clientId,brandRequest);
		return userBrands;
	
	}
	
	@Override
	public BrandVo getBrandRecord(String brandId) throws SQLException,Exception{
		return userDAO.getBrandRecord(brandId);
	}

	@Override
	public BrandVo getBrandRecordBySiteId(String siteId) throws SQLException,Exception{
		return userDAO.getBrandRecordBySiteId(siteId);
	}

	@Override
	public UserDetailsResponse getUserDetails(String userId) throws SQLException,Exception{
		if(UserDetailsMap.containsKey(userId) && UserDetailsMap.get(userId)!=null) {
			return UserDetailsMap.get(userId);
		}else {
			UserDetailsResponse userDetails = userDAO.getUserDetails(userId);
			UserDetailsMap.put(userId, userDetails);
			return userDetails;
		}
	}
}
