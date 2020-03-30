package com.fb.in.app.reporting.request;

public class BrandRequest {
	
	SearchRequest search;
	SortRequest sort;
	PagingRequest paging;
	
	public SearchRequest getSearch() {
		return search;
	}
	public void setSearch(SearchRequest search) {
		this.search = search;
	}
	public SortRequest getSort() {
		return sort;
	}
	public void setSort(SortRequest sort) {
		this.sort = sort;
	}
	public PagingRequest getPaging() {
		return paging;
	}
	public void setPaging(PagingRequest paging) {
		this.paging = paging;
	}

}
