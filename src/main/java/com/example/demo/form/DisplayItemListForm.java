package com.example.demo.form;

/**
 * 商品情報検索時に使用するフォーム.
 *
 * @author sota_adachi
 */
public class DisplayItemListForm {
    /**
     * 検索商品名
     */
    private String searchItemName;
    /**
     * 親カテゴリID
     */
    private Integer bigCategoryId;
    /**
     * 子カテゴリID
     */
    private Integer middleCategoryId;
    /**
     * 孫カテゴリID
     */
    private Integer smallCategoryId;
    /**
     * カテゴリ名
     */
    private String searchCategoryName;
    /**
     * ブランド
     */
    private String searchBrandName;
    /**
     * ページ番号
     */
    private Integer pageNumber;

    public String getSearchItemName() {
        return searchItemName;
    }

    public void setSearchItemName(String searchItemName) {
        this.searchItemName = searchItemName;
    }

    public Integer getBigCategoryId() {
        return bigCategoryId;
    }

    public void setBigCategoryId(Integer bigCategoryId) {
        this.bigCategoryId = bigCategoryId;
    }

    public Integer getMiddleCategoryId() {
        return middleCategoryId;
    }

    public void setMiddleCategoryId(Integer middleCategoryId) {
        this.middleCategoryId = middleCategoryId;
    }

    public Integer getSmallCategoryId() {
        return smallCategoryId;
    }

    public void setSmallCategoryId(Integer smallCategoryId) {
        this.smallCategoryId = smallCategoryId;
    }

    public String getSearchCategoryName() {
        return searchCategoryName;
    }

    public void setSearchCategoryName(String searchCategoryName) {
        this.searchCategoryName = searchCategoryName;
    }

    public String getSearchBrandName() {
        return searchBrandName;
    }

    public void setSearchBrandName(String searchBrandName) {
        this.searchBrandName = searchBrandName;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    @Override
    public String toString() {
        return "DisplayItemListForm [searchItemName=" + searchItemName + ", bigCategoryId=" + bigCategoryId + ", middleCategoryId=" + middleCategoryId + ", smallCategoryId=" + smallCategoryId + ", searchCategoryName=" + searchCategoryName + ", searchBrandName=" + searchBrandName + ", pageNumber=" + pageNumber + "]";
    }
}
