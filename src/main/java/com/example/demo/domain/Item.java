package com.example.demo.domain;

/**
 * 商品情報を表すドメイン.
 * 
 * @author sota_adachi
 *
 */
public class Item {
	/** 商品ID */
	private Integer id;
	/** 商品名 */
	private String name;
	/** 販売状況 */
	private Integer itemConditionId;
	/** カテゴリID */
	private Integer categoryId;
	/** カテゴリ */
	private ParentCategory category;
	/** ブランド名 */
	private String brandName;
	/** 価格 */
	private Integer price;
	/** 配送状況 */
	private Integer shipping;
	/** 商品説明 */
	private String itemDescription;

	/**
	 * 引数なしのコンストラクタ
	 */
	public Item() {
	}
	
	/**
	 * @param id 商品ID
	 * @param name 商品名
	 * @param itemConditionId 販売状況
	 * @param categoryId カテゴリID
	 * @param categoriyList カテゴリリスト
	 * @param brandName ブランド名
	 * @param price 価格
	 * @param shipping 配送状況
	 * @param itemDescription 商品説明
	 */
	public Item(Integer id, String name, Integer itemConditionId, Integer categoryId, ParentCategory category, String brandName, Integer price, Integer shipping, String itemDescription) {
		super();
		this.id = id;
		this.name = name;
		this.itemConditionId = itemConditionId;
		this.categoryId = categoryId;
		this.category = category;
		this.brandName = brandName;
		this.price = price;
		this.shipping = shipping;
		this.itemDescription = itemDescription;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getItemConditionId() {
		return itemConditionId;
	}

	public void setItemConditionId(Integer itemConditionId) {
		this.itemConditionId = itemConditionId;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public ParentCategory getCategory() {
		return category;
	}

	public void setCategory(ParentCategory category) {
		this.category = category;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getShipping() {
		return shipping;
	}

	public void setShipping(Integer shipping) {
		this.shipping = shipping;
	}

	public String getItemDescription() {
		return itemDescription;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

	@Override
	public String toString() {
		return "Item [id=" + id + ", name=" + name + ", itemConditionId=" + itemConditionId + ", categoryId=" + categoryId + ", category=" + category + ", brandName=" + brandName + ", price=" + price + ", shipping=" + shipping + ", itemDescription=" + itemDescription + "]";
	}
}
