package com.fb.in.app.reporting.service;

import java.math.BigInteger;
import java.util.List;

import com.fb.in.app.reporting.model.vo.BrandVo;
import com.fb.in.app.reporting.request.BrandRequest;
import com.fb.in.app.reporting.response.BrandListResponse;
import com.fb.in.app.reporting.response.UserDetailsResponse;

public interface UserService {
	public BrandListResponse getBrand(String userId, String clientId, BrandRequest brandRequest) throws Exception;

	public BrandVo getBrandRecord(String brandId) throws Exception;

	public BigInteger getMostRecentSiteId(String userId) throws Exception;

	public List<BrandVo> getBrandRecordBySiteId(String siteId) throws Exception;

	public UserDetailsResponse getUserDetails(String userId) throws Exception;

}
