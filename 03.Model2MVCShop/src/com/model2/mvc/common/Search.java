package com.model2.mvc.common;


public class Search {
	
	///Field
	private int currentPage;
	private String searchCondition;
	private String searchSoldProd;
	private String searchKeyword;
	private String searchKeywordPrice; //가격위해서
	private String sortCondition;
	private String sortCondition2;
	private int pageSize;
	
	///Constructor
	public Search() {
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public String getSearchCondition() {
		return searchCondition;
	}

	public String getSearchSoldProd() {
		return searchSoldProd;
	}

	public String getSearchKeyword() {
		return searchKeyword;
	}

	public String getSearchKeywordPrice() {
		return searchKeywordPrice;
	}

	public String getSortCondition() {
		return sortCondition;
	}

	public String getSortCondition2() {
		return sortCondition2;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public void setSearchCondition(String searchCondition) {
		this.searchCondition = searchCondition;
	}

	public void setSearchSoldProd(String searchSoldProd) {
		this.searchSoldProd = searchSoldProd;
	}

	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
	}

	public void setSearchKeywordPrice(String searchKeywordPrice) {
		this.searchKeywordPrice = searchKeywordPrice;
	}

	public void setSortCondition(String sortCondition) {
		this.sortCondition = sortCondition;
	}

	public void setSortCondition2(String sortCondition2) {
		this.sortCondition2 = sortCondition2;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	@Override
	public String toString() {
		return "Search [currentPage=" + currentPage + ", searchCondition=" + searchCondition + ", searchSoldProd="
				+ searchSoldProd + ", searchKeyword=" + searchKeyword + ", searchKeywordPrice=" + searchKeywordPrice
				+ ", sortCondition=" + sortCondition + ", sortCondition2=" + sortCondition2 + ", pageSize=" + pageSize + "]";
	}
	
	///Method
	
	
	
	
}