package com.example.demo.form;

/**
 * 商品情報検索時に使用するフォーム.
 * 
 * @author sota_adachi
 *
 */
public class DisplayItemListForm {
	/** 検索商品名 */
	private String searchItemName;
	/** 大カテゴリ */
	private String parentCategory;
	/** 中カテゴリ */
	private String childCategory;
	/** 小カテゴリ */
	private String grandChild;
	/** ブランド */
	private String brand;
	/** ページ番号 */
	private Integer pageNumber;

	public String getSearchItemName() {
		return searchItemName;
	}

	public void setSearchItemName(String searchItemName) {
		this.searchItemName = searchItemName;
	}

	public String getParentCategory() {
		return parentCategory;
	}

	public void setParentCategory(String parentCategory) {
		this.parentCategory = parentCategory;
	}

	public String getChildCategory() {
		return childCategory;
	}

	public void setChildCategory(String childCategory) {
		this.childCategory = childCategory;
	}

	public String getGrandChild() {
		return grandChild;
	}

	public void setGrandChild(String grandChild) {
		this.grandChild = grandChild;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	@Override
	public String toString() {
		return "DisplayItemListForm [searchItemName=" + searchItemName + ", parentCategory=" + parentCategory + ", childCategory=" + childCategory + ", grandChild=" + grandChild + ", brand=" + brand + ", pageNumber=" + pageNumber + "]";
	}
}
