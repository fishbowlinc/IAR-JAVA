package com.fb.in.app.reporting.dao;

import java.sql.SQLException;
import java.util.List;

import com.fb.in.app.reporting.model.vo.BrandVo;
import com.fb.in.app.reporting.request.BrandRequest;
import com.fb.in.app.reporting.response.BrandListResponse;
import com.fb.in.app.reporting.response.UserDetailsResponse;

public interface UserDAO {

	public BrandListResponse getBrand(String userId, String clientId, BrandRequest brandRequest)
			throws SQLException, Exception;

	public BrandVo getBrandRecord(String brandId) throws SQLException, Exception;

	public List<BrandVo> getBrandRecordBySiteId(String siteId) throws SQLException, Exception;

	public UserDetailsResponse getUserDetails(String userId) throws SQLException, Exception;

	public UserDetailsResponse getUserDetailsByUserName(String userName) throws SQLException, Exception;

}
