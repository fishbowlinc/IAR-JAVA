package com.fb.in.app.reporting.dao.impl;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.fb.in.app.reporting.dao.UserDAO;
import com.fb.in.app.reporting.model.User;
import com.fb.in.app.reporting.model.vo.BrandVo;
import com.fb.in.app.reporting.model.vo.UserDeailsVo;
import com.fb.in.app.reporting.request.BrandRequest;
import com.fb.in.app.reporting.request.SearchRequest;
import com.fb.in.app.reporting.request.SortRequest;
import com.fb.in.app.reporting.response.BrandListResponse;
import com.fb.in.app.reporting.response.UserDetailsResponse;

@Repository
public class UserDAOImpl implements UserDAO {

	@PersistenceContext
	EntityManager entityManager;

	private static Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);

	@Override
	public BrandListResponse getBrand(String userId, String clientId, BrandRequest brandRequest) {

		BrandListResponse brandListResponse = new BrandListResponse();
		SearchRequest searchRequest = brandRequest.getSearch();
		SortRequest sortRequest = brandRequest.getSort();

		StringBuilder sb = new StringBuilder(
				"SELECT DISTINCT NEW com.fb.in.app.reporting.model.vo.BrandVo(b.brandId, b.brandName, b.siteId, d.brandStatusDesc as brandStatus, c.clientName, c.clientId,c.cmpCustomerId, e.clientStatusDesc as clientStatus )");
		sb.append("FROM AuthorizedBrand ab ").append("JOIN Brand b ON b.brandId = ab.brandId ")
				.append("JOIN Client c ON c.clientId = b.clientId ");
		sb.append("JOIN ClientStatus e ON e.clientStatusCode = c.clientStatusCode ")
				.append("JOIN BrandStatus d ON d.brandStatusCode = b.brandStatusCode ");

		if (searchRequest != null) {
			String searchString = searchRequest.getFreeText();
			if (searchString != null && !StringUtils.isEmpty(searchString)) {
				sb.append("AND ( b.brandName LIKE  ").append("'").append("%").append(searchString).append("%")
						.append("'").append(" OR d.brandStatusDesc LIKE ").append("'").append("%").append(searchString)
						.append("%").append("'");
				sb.append(" OR c.clientName LIKE  ").append("'").append("%").append(searchString).append("%")
						.append("'").append(" OR c.cmpCustomerId LIKE ").append("'").append("%").append(searchString)
						.append("%").append("'");
				sb.append(" OR e.clientStatusDesc LIKE  ").append("'").append("%").append(searchString).append("%")
						.append("'").append(" )");
			}
		}

		logger.info("clientId== " + clientId);
		if (!clientId.equalsIgnoreCase("-1")) {
			sb.append(" WHERE ab.userId = :user_id AND b.clientId = :client_id");
			logger.info("sb where clientid is not -1 == " + sb);
		} else {
			sb.append(" WHERE ab.userId = :user_id");
			logger.info("sb where clientid is -1 == " + sb);
		}

		if (null != sortRequest) {
			String field = sortRequest.getField();
			String direction = sortRequest.getDirection();

			if (field != null && !StringUtils.isEmpty(field)) {
				if (field.equals("brandName") || field.equals("brandId") || field.equals("siteId")) {
					sb.append(" ORDER BY b.").append("" + field + "").append(" ");
				} else if (field.equals("brandStatus")) {
					sb.append(" ORDER BY d.brandStatusDesc").append(" ");
				} else if (field.equals("clientStatus")) {
					sb.append(" ORDER BY e.clientStatusDesc").append(" ");
				} else if (field.equals("clientName") || field.equals("cmpCustomerId") || field.equals("clientId")) {
					sb.append(" ORDER BY c.").append("" + field + "").append(" ");
				}
			} else {
				sb.append(" ORDER BY b.brandName ");
			}

			if (null != direction) {
				sb.append(" ").append("" + direction + "");
			} else {
				sb.append(" asc");
			}
		}
		try {
			TypedQuery<BrandVo> query = entityManager.createQuery(sb.toString(), BrandVo.class);
			query.setParameter("user_id", Integer.parseInt(userId));
			if (!clientId.equalsIgnoreCase("-1")) {
				query.setParameter("client_id", Integer.parseInt(clientId));
				logger.info("sb where clientid is -1 == " + sb);
			}
			logger.info("last sb  " + sb);
			List<BrandVo> results = query.getResultList();
			logger.info("brand count: " + results.size());
			brandListResponse.setBrandsCount(results.size());
			brandListResponse.setBrandList(results);

		} catch (Exception e) {
			logger.info(e.getMessage());
		}
		return brandListResponse;
	}

	@Override
	public BrandVo getBrandRecord(String brandId) throws SQLException, Exception {

		BrandListResponse brandListResponse = new BrandListResponse();

		StringBuilder sb = new StringBuilder(
				"SELECT DISTINCT NEW com.fb.in.app.reporting.model.vo.BrandVo(b.brandId, b.brandName, b.siteId, d.brandStatusDesc as brandStatus, c.clientName, c.clientId,c.cmpCustomerId, e.clientStatusDesc as clientStatus )");
		sb.append("FROM AuthorizedBrand ab ").append("JOIN Brand b ON b.brandId = ab.brandId ")
				.append("JOIN Client c ON c.clientId = b.clientId ");
		sb.append("JOIN ClientStatus e ON e.clientStatusCode = c.clientStatusCode ")
				.append("JOIN Brand b ON b.brandId = ab.brandId ")
				.append("JOIN BrandStatus d ON d.brandStatusCode = b.brandStatusCode ");

		sb.append(" WHERE b.brandId = :brandId");

		TypedQuery<BrandVo> query = entityManager.createQuery(sb.toString(), BrandVo.class);
		query.setParameter("brandId", Integer.parseInt(brandId));

		logger.info("last sb  " + sb);
		List<BrandVo> resultCount = query.getResultList();

		List<BrandVo> results = query.getResultList();
		brandListResponse.setBrandsCount(resultCount.size());
		brandListResponse.setBrandList(results);
		return results.get(0);
	}

	@Override
	public List<BrandVo> getBrandRecordBySiteId(String siteId) throws SQLException, Exception {
		List<BrandVo> results = null;
		try {
			StringBuilder sb = new StringBuilder(
					"SELECT DISTINCT NEW com.fb.in.app.reporting.model.vo.BrandVo(b.brandId, b.brandName, b.siteId, d.brandStatusDesc as brandStatus, c.clientName, c.clientId,c.cmpCustomerId, e.clientStatusDesc as clientStatus )");
			sb.append("FROM AuthorizedBrand ab ").append("JOIN Brand b ON b.brandId = ab.brandId ")
					.append("JOIN Client c ON c.clientId = b.clientId ");
			sb.append("JOIN ClientStatus e ON e.clientStatusCode = c.clientStatusCode ")
					.append("JOIN Brand b ON b.brandId = ab.brandId ")
					.append("JOIN BrandStatus d ON d.brandStatusCode = b.brandStatusCode ");

			sb.append(" WHERE b.siteId = :siteId");
			sb.append(" AND  b.brandStatusCode = 'OP'");

			TypedQuery<BrandVo> query = entityManager.createQuery(sb.toString(), BrandVo.class);
			query.setParameter("siteId", new BigInteger(siteId));

			logger.info("last sb  " + sb);

			results = query.getResultList();
			if (null != results) {
				return results;
			}

		} catch (NumberFormatException e) {
			logger.info(e.getMessage());
			System.out.println(e.getMessage());
		}
		return null;
	}

	@Override
	public UserDetailsResponse getUserDetails(String userId) throws SQLException, Exception {
		StringBuilder sb = new StringBuilder(
				"SELECT NEW com.fb.in.app.reporting.model.vo.UserDeailsVo(US.userName, US.firstName, US.lastName, US.title, US.phone, US.email, US.clientId, US.timeZone, RU.roleId)");
		sb.append(" FROM RolesForUser as RU ").append("JOIN User as US ON US.userId = RU.userId ");
		sb.append("WHERE US.userId = :user_id");

		TypedQuery<UserDeailsVo> query = entityManager.createQuery(sb.toString(), UserDeailsVo.class);
		query.setParameter("user_id", Integer.parseInt(userId));
		List<UserDeailsVo> result = query.getResultList();
		UserDetailsResponse userDetailsResponse = new UserDetailsResponse();
		if (result != null) {
			List<Integer> roleIdList = new ArrayList<Integer>();
			for (UserDeailsVo userDetails : result) {
				roleIdList.add(userDetails.getRoleId());
			}
			UserDeailsVo userDetails = result.get(0);
			userDetailsResponse.setUserName(userDetails.getUserName());
			userDetailsResponse.setFirstName(userDetails.getFirstName());
			userDetailsResponse.setLastName(userDetails.getLastName());
			userDetailsResponse.setTitle(userDetails.getTitle());
			userDetailsResponse.setPhone(userDetails.getPhone());
			userDetailsResponse.setEmail(userDetails.getEmail());
			userDetailsResponse.setTimeZone(userDetails.getTimeZone());
			userDetailsResponse.setClientId(userDetails.getClientId());
			userDetailsResponse.setRoleId(roleIdList);
		}
		return userDetailsResponse;
	}

	@Override
	public UserDetailsResponse getUserDetailsByUserName(String userName) {
		StringBuilder sb = new StringBuilder("from User u where u.userName=:_userName");

		TypedQuery<User> query = entityManager.createQuery(sb.toString(), User.class);
		query.setParameter("_userName", userName);
		User result = query.getSingleResult();
		UserDetailsResponse userDetailsResponse = new UserDetailsResponse();
		if (result != null) {
			userDetailsResponse.setUserName(result.getUserName());
			userDetailsResponse.setFirstName(result.getFirstName());
			userDetailsResponse.setLastName(result.getLastName());
			userDetailsResponse.setTitle(result.getTitle());
			userDetailsResponse.setPhone(result.getPhone());
			userDetailsResponse.setEmail(result.getEmail());
			userDetailsResponse.setTimeZone(result.getTimeZone());
			userDetailsResponse.setClientId(result.getClientId());
		}
		return userDetailsResponse;
	}
}
