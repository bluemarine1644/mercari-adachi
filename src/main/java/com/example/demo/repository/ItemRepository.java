package com.example.demo.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.Category;
import com.example.demo.domain.Item;

/**
 * itemsテーブルを操作するリポジトリ.
 * 
 * @author sota_adachi
 *
 */
@Repository
public class ItemRepository {
	@Autowired
	private NamedParameterJdbcTemplate template;

	/**
	 * 商品名から商品を（曖昧）検索します.
	 * 
	 * @param searchItemName     検索商品名
	 * @param searchCategoryName 検索カテゴリ名
	 * @param searchBrandName    検索ブランド名
	 * @param VIEW_SIZE          1ページに表示す商品数
	 * @param offsetValue        オフセット値
	 * @return 検索された商品情報
	 */
	public List<Item> searchItem(String searchItemName, String searchCategoryName, String searchBrandName, Integer VIEW_SIZE, Integer offsetValue) {
		String sql = "SELECT i.id item_id, i.name item_name, price, name_all category_name_all, brand_name, item_condition_id FROM items i LEFT JOIN category c ON category_id = c.id WHERE i.name LIKE :searchItemName AND name_all LIKE :searchCategoryName ";
		if (searchBrandName != "") {
			sql += "AND brand_name LIKE :searchBrandName ";
		} 
		sql += "ORDER BY i.id LIMIT :VIEW_SIZE OFFSET :offsetValue";

		/**
		 * Itemオブジェクトを生成するO/Rマッパー.
		 */
		final RowMapper<Item> ITEM_ROW_MAPPER = (rs, i) -> {
			Item item = new Item();
			item.setId(rs.getInt("item_id"));
			item.setName(rs.getString("item_name"));
			item.setPrice(rs.getInt("price"));
			Category category = new Category();
			category.setNameAll(rs.getString("category_name_all"));
			item.setCategory(category);
			item.setBrandName(rs.getString("brand_name"));
			item.setItemConditionId(rs.getInt("item_condition_id"));
			return item;
		};

		SqlParameterSource param = new MapSqlParameterSource()
				.addValue("searchItemName", "%" + searchItemName + "%")
				.addValue("searchCategoryName", "%" + searchCategoryName + "%")
				.addValue("searchBrandName", "%" + searchBrandName + "%")
				.addValue("VIEW_SIZE", VIEW_SIZE)
				.addValue("offsetValue", offsetValue);
		List<Item> itemList = template.query(sql, param, ITEM_ROW_MAPPER);
		return itemList;
	}

	/**
	 * searchByItemNameメソッドで検索された商品数を返します.
	 * 
	 * @param searchItemName  検索商品名
	 * @param searchBrandName 検索ブランド名
	 * @return 商品数
	 */
	public Integer quantityOfItemList(String searchItemName, String searchCategoryName, String searchBrandName) {
		String sql = "SELECT COUNT(i.id) FROM items i LEFT JOIN category c ON category_id = c.id WHERE i.name LIKE :searchItemName AND name_all LIKE :searchCategoryName ";
		if (searchBrandName != "") {
			sql += "AND brand_name LIKE :searchBrandName";			
		}
		SqlParameterSource param = new MapSqlParameterSource()
				.addValue("searchItemName", "%" + searchItemName + "%")
				.addValue("searchCategoryName", "%" + searchCategoryName + "%")
				.addValue("searchBrandName", "%" + searchBrandName + "%");
		Integer quantityOfItemList = template.queryForObject(sql, param, Integer.class);
		return quantityOfItemList;
	}
	
	/**
	 * 商品IDから商品詳細情報を取得します.
	 * 
	 * @param id 商品ID
	 * @return 商品詳細情報
	 */
	public Item findById(Integer id) {
		/**
		 * Itemオブジェクトを生成するO/Rマッパー.
		 */
		final RowMapper<Item> ITEM_ROW_MAPPER = (rs, i) -> {
			Item item = new Item();
			item.setId(rs.getInt("id"));
			item.setName(rs.getString("name"));
			item.setItemConditionId(rs.getInt("item_condition_id"));
			item.setCategoryId(rs.getInt("category_id"));
			item.setBrandName(rs.getString("brand_name"));
			item.setPrice(rs.getInt("price"));
			item.setItemDescription(rs.getString("item_description"));
			return item;
		};		
		String sql = "SELECT id, name, item_condition_id, category_id, brand_name, price, item_description FROM items WHERE id = :id";
		SqlParameterSource param = new MapSqlParameterSource()
				.addValue("id", id);
		Item itemDetail = template.queryForObject(sql, param, ITEM_ROW_MAPPER);
		return itemDetail;
	}
}