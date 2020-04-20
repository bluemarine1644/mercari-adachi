package com.example.demo.domain;

/**
 * 孫カテゴリ情報を表すドメイン.
 * 
 * @author sota_adachi
 *
 */
public class GrandChildCategory {
	/** カテゴリID */
	private Integer id;
	/** カテゴリ名前 */
	private String name;
	/** 親カテゴリID */
	private Integer parentId;
	/** 全カテゴリ名 */
	private String nameAll;

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

	public String getNameAll() {
		return nameAll;
	}

	public void setNameAll(String nameAll) {
		this.nameAll = nameAll;
	}

	@Override
	public String toString() {
		return "GrandChildCategory [id=" + id + ", name=" + name + ", parentId=" + parentId + ", nameAll=" + nameAll + "]";
	}
}
