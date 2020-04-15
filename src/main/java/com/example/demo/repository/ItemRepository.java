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
	/**
	 * Itemオブジェクトを生成するローマッパー.
	 */
	private static final RowMapper<Item> ITEM_ROW_MAPPER = (rs, i) -> {
		Item item = new Item();
		item.setId(rs.getInt("item_id"));
		item.setName(rs.getString("item_name"));
		item.setItemConditionId(rs.getInt("item_condition_id"));
		item.setCategoryId(rs.getInt("category_id"));
		Category category = new Category();
		category.setName(rs.getString("category_name"));
		category.setParentId(rs.getInt("parent_id"));
		category.setNameAll(rs.getString("name_all"));
		item.setCategory(category);
		item.setBrandName(rs.getString("brand_name"));
		item.setPrice(rs.getInt("price"));
		item.setShipping(rs.getInt("shipping"));
		item.setItemDescription(rs.getString("item_description"));
		return item;
	};
	
	public static final String SQL = "SELECT i.id item_id, i.name item_name, i.item_condition_id, i.category_id, c.name category_name, c.parent_id, c.name_all, i.brand_name, i.price, i.shipping, i.item_description FROM items i FULL OUTER JOIN category c ON i.category_id = c.id";
	
	@Autowired
	private NamedParameterJdbcTemplate template;

	/**
	 * 商品名から商品を（曖昧）検索します.
	 * 
	 * @param searchItemName  検索商品名
	 * @param searchBrandName 検索ブランド名
	 * @param VIEW_SIZE       1ページに表示す商品数
	 * @param offsetValue     オフセット値
	 * @return 検索された商品情報
	 */
	public List<Item> searchByItemAndBrandName(String searchItemName, String searchBrandName, Integer VIEW_SIZE, Integer offsetValue) {
		String searchByItemNameSql = " WHERE i.name LIKE :searchItemName AND i.brand_name LIKE :searchBrandName ORDER BY i.name LIMIT :VIEW_SIZE OFFSET :offsetValue";
		SqlParameterSource param = new MapSqlParameterSource().addValue("searchItemName", "%" + searchItemName + "%").addValue("searchBrandName", "%" + searchBrandName + "%").addValue("VIEW_SIZE", VIEW_SIZE).addValue("offsetValue", offsetValue);
		List<Item> itemList = template.query(SQL + searchByItemNameSql, param, ITEM_ROW_MAPPER);
		return itemList;
	}

	/**
	 * searchByItemNameメソッドで検索された商品数を返します.
	 * 
	 * @param searchItemName  検索商品名
	 * @param searchBrandName 検索ブランド名
	 * @return 商品数
	 */
	public Integer quantityOfItemList(String searchItemName, String searchBrandName) {
		String quantityOfItemListSql = "SELECT COUNT(id) FROM items WHERE name LIKE :searchItemName AND brand_name LIKE :searchBrandName";
		SqlParameterSource param = new MapSqlParameterSource().addValue("searchItemName", "%" + searchItemName + "%").addValue("searchBrandName", "%" + searchBrandName + "%");
		Integer quantityOfItemList = template.queryForObject(quantityOfItemListSql, param, Integer.class);
		return quantityOfItemList;
	}
	
	/**
	 * 商品IDから商品詳細情報を取得します.
	 * 
	 * @param id 商品ID
	 * @return 商品詳細情報
	 */
	public Item findById(Integer id) {
		String findByIdSql = " WHERE i.id = :id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		Item itemDetail = template.queryForObject(SQL + findByIdSql, param, ITEM_ROW_MAPPER);
		return itemDetail;
	}
}