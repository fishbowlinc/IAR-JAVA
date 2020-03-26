package com.fb.in.app.reporting.response;

import java.util.List;

import com.fb.in.app.reporting.model.vo.BrandVo;
import com.fb.in.app.reporting.util.FBRestResponse;


public class BrandListResponse extends FBRestResponse{

	private int brandsCount;
	private List<BrandVo> brandList;
	
	public BrandListResponse(){
		
	}
		
	public BrandListResponse(String status,List<BrandVo> brandList,int brandsCount){
		this.brandList = brandList;
		this.brandsCount = brandsCount;
	}

	public int getBrandsCount() {
		return brandsCount;
	}

	public void setBrandsCount(int brandsCount) {
		this.brandsCount = brandsCount;
	}

	public List<BrandVo> getBrandList() {
		return brandList;
	}

	public void setBrandList(List<BrandVo> brandList) {
		this.brandList = brandList;
	}


	
}
