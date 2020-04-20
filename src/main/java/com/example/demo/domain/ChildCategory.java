package com.example.demo.domain;

import java.util.List;

/**
 * 子カテゴリ情報を表すドメイン.
 * 
 * @author sota_adachi
 *
 */
public class ChildCategory {
	/** カテゴリID */
	private Integer id;
	/** カテゴリ名前 */
	private String name;
	/** 親カテゴリID */
	private Integer parentId;
	/** 孫カテゴリリスト */
	private List<GrandChildCategory> grandChildCategoryList;

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

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public List<GrandChildCategory> getGrandChildCategoryList() {
		return grandChildCategoryList;
	}

	public void setGrandChildCategoryList(List<GrandChildCategory> grandChildCategoryList) {
		this.grandChildCategoryList = grandChildCategoryList;
	}

	@Override
	public String toString() {
		return "ChildCategory [id=" + id + ", name=" + name + ", parentId=" + parentId + ", grandChildCategoryList=" + grandChildCategoryList + "]";
	}
}
