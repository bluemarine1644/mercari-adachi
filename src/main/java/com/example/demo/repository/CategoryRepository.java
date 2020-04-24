package com.example.demo.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.Category;

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
	 * カテゴリー情報を取得します.
	 * 
	 * @param id カテゴリーID
	 * @return カテゴリー情報
	 */
	public List<Category> findCategoryByCategoryId(Integer categoryId) {
		/**
		 * List<Category>オブジェクトを生成するO/Rマッパー
		 */
		final RowMapper<Category> ROW_MAPPER = new BeanPropertyRowMapper<>(Category.class);
		String sql = "SELECT id, name FROM category WHERE parent_id";
		MapSqlParameterSource param = new MapSqlParameterSource();
		if (categoryId == null) {
			sql += " IS NULL";
		} else {
			sql += " = :categoryId";
			param.addValue("categoryId", categoryId);
		}
		sql += " ORDER BY id";
System.out.println(template.query(sql, param, ROW_MAPPER));
		return template.query(sql, param, ROW_MAPPER);
	}	
}