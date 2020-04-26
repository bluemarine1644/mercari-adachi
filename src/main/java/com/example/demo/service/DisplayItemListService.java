package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.Item;
import com.example.demo.domain.Category;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ItemRepository;

/**
 * 商品一覧情報を表示するサービス.
 * 
 * @author sota_adachi
 *
 */
@Service
@Transactional
public class DisplayItemListService {
	@Autowired
	private ItemRepository itemRepository;
	@Autowired
	private CategoryRepository categoryRepository;

	/**
	 * 商品名から商品を（曖昧）検索します.
	 * 
	 * @param searchItemName     商品名
	 * @param searchCategoryName カテゴリ名
	 * @param searchBrandName    ブランド名
	 * @param VIEW_SIZE          1ページに表示す商品数
	 * @param offsetValue        オフセット値
	 * @return 検索された商品情報
	 */
	public List<Item> searchItem(String searchItemName, String searchCategoryName, String searchBrandName, Integer VIEW_SIZE, Integer offsetValue) {
		return itemRepository.searchItem(searchItemName, searchCategoryName, searchBrandName, VIEW_SIZE, offsetValue);
	}

	/**
	 * searchByItemNameメソッドで検索された商品数を返します.
	 * 
	 * @param searchItemName  検索商品名
	 * @param searchBrandName 検索ブランド名
	 * @return
	 */
	public Integer quantityOfItemList(String searchItemName, String searchCategoryName,String searchBrandName) {
		return itemRepository.quantityOfItemList(searchItemName, searchCategoryName, searchBrandName);
	}

	/**
	 * カテゴリー情報を取得します.
	 * 
	 * @return カテゴリー情報
	 */
	public List<Category> findAllCategory() {
		List<Category> bigCategoryList = categoryRepository.findCategoryByCategoryId(null);
		for (Category bigCategory : bigCategoryList) {
			List<Category> middleCategoryList = categoryRepository.findCategoryByCategoryId(bigCategory.getId());
			bigCategory.setChildCategoryList(middleCategoryList);
			for (Category middleCategory : middleCategoryList) {
				List<Category> smallCategoryList = categoryRepository.findCategoryByCategoryId(middleCategory.getId());
				middleCategory.setChildCategoryList(smallCategoryList);
			}
		}
		return bigCategoryList;
	}
}