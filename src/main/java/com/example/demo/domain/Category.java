package com.example.demo.domain;

import java.util.List;

/**
 * カテゴリ情報を表すドメイン.
 * 
 * @author sota_adachi
 *
 */
public class Category {
	/** カテゴリID */
	private Integer id;
	/** カテゴリ名 */
	private String name;
	/** 全カテゴリ名 */
	private String nameAll;
	/** 子カテゴリリスト */
	private List<Category> childCategoryList;

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

	public String getNameAll() {
		return nameAll;
	}

	public void setNameAll(String nameAll) {
		this.nameAll = nameAll;
	}

	public List<Category> getChildCategoryList() {
		return childCategoryList;
	}

	public void setChildCategoryList(List<Category> childCategoryList) {
		this.childCategoryList = childCategoryList;
	}

	@Override
	public String toString() {
		return "Category [id=" + id + ", name=" + name + ", nameAll=" + nameAll + ", childCategoryList=" + childCategoryList + "]";
	}
}