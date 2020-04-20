package com.example.demo.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.ParentCategory;
import com.example.demo.domain.ChildCategory;
import com.example.demo.domain.GrandChildCategory;

/**
 * Categoryテーブルを操作するリポジトリ.
 * 
 * @author sota_adachi
 *
 */
@Repository
public class CategoryRepository {
	@Autowired
	private NamedParameterJdbcTemplate template;

	/**
	 * List<ParentCategory>オブジェクトを生成するO/Rマッパー
	 */
	private static final ResultSetExtractor<List<ParentCategory>> CATEGORY_RESULT_SET_EXTRACTER = (rs) -> {
		List<ParentCategory> parentCategoryList = new ArrayList<>();
		List<ChildCategory> childCategoryList = null;
		List<GrandChildCategory> grandChildCategoryList = null;
		int nowParentCategoryId = 1;
		int nowChildCategoryId = 1;
		int temporaryListCount = 0;
		int actualListCount = 0;
		int listSize = 1;
		String rootString = "ParentCategory";
		while (actualListCount < listSize) {
			while (rs.next()) {
				if (nowParentCategoryId == rs.getInt("id") && rootString == "ParentCategory") {
					ParentCategory parentCategory = new ParentCategory();
					nowParentCategoryId = rs.getInt("id");
					parentCategory.setId(nowParentCategoryId);
					parentCategory.setName(rs.getString("name"));
					childCategoryList = new ArrayList<>();
					parentCategory.setChildCategoryList(childCategoryList);
					parentCategoryList.add(parentCategory);
					actualListCount += 1;
					rootString = "ChildCategory";
				} else if (nowParentCategoryId == rs.getInt("parent_id") && rootString == "ChildCategory") {
					ChildCategory childCategory = new ChildCategory();
					nowChildCategoryId = rs.getInt("id");
					childCategory.setId(nowChildCategoryId);
					childCategory.setName(rs.getString("name"));
					grandChildCategoryList = new ArrayList<>();
					childCategory.setGrandChildCategoryList(grandChildCategoryList);
					childCategoryList.add(childCategory);
					actualListCount += 1;
					rootString = "GrandChildCategory";
				} else if (nowChildCategoryId == rs.getInt("parent_id") && rootString == "GrandChildCategory") {
					GrandChildCategory grandChildCategory = new GrandChildCategory();
					grandChildCategory.setId(rs.getInt("id"));
					grandChildCategory.setName(rs.getString("name"));
					grandChildCategory.setParentId(rs.getInt("parent_id"));
					grandChildCategory.setNameAll(rs.getString("name_all"));
					grandChildCategoryList.add(grandChildCategory);
					actualListCount += 1;
				}
				if (nowParentCategoryId == 1) {
					temporaryListCount += 1;
				}
			}
			if (nowParentCategoryId == 1) {
				listSize = temporaryListCount;
			}
			rootString = "ParentCategory";
			nowParentCategoryId += 1;
			temporaryListCount = 0;
		}
		System.out.println("nowParentCategoryId: " + nowParentCategoryId);
		System.out.println("nowChildCategoryId: " + nowChildCategoryId);
		System.out.println("temporaryListCount: " + temporaryListCount);
		System.out.println("actualListCount: " + actualListCount);
		System.out.println("listSize: " + listSize);
		return parentCategoryList;
	};

	/**
	 * カテゴリー情報を取得します.
	 * 
	 * @return カテゴリー情報
	 */
	public List<ParentCategory> findAll() {
		String sql = "SELECT id, name, parent_id, name_all FROM category";
		return template.query(sql, CATEGORY_RESULT_SET_EXTRACTER);
	}
}