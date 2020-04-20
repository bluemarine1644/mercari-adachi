package com.example.demo.domain;

import java.util.List;

/**
 * 親カテゴリ情報を表すドメイン.
 * 
 * @author sota_adachi
 *
 */
public class ParentCategory {
	/** カテゴリID */
	private Integer id;
	/** カテゴリ名前 */
	private String name;
	/** 子カテゴリリスト */
	private List<ChildCategory> childCategoryList;

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

	public List<ChildCategory> getChildCategoryList() {
		return childCategoryList;
	}

	public void setChildCategoryList(List<ChildCategory> childCategoryList) {
		this.childCategoryList = childCategoryList;
	}

	@Override
	public String toString() {
		return "ParentCategory [id=" + id + ", name=" + name + ", childCategoryList=" + childCategoryList + "]";
	}
}
