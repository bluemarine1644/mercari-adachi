package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.Item;
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

	/**
	 * 商品名から商品を（曖昧）検索します.
	 * 
	 * @param searchItemName 商品名
	 * @param VIEW_SIZE      1ページに表示す商品数
	 * @param offsetValue    オフセット値
	 * @return 検索された商品情報
	 */
	public List<Item> serchByItemAndBrandName(String searchItemName, String searchBrandName, Integer VIEW_SIZE, Integer offsetValue) {
		return itemRepository.searchByItemAndBrandName(searchItemName, searchBrandName, VIEW_SIZE, offsetValue);
	}

	/**
	 * searchByItemNameメソッドで検索された商品数を返します.
	 * 
	 * @param searchItemName 商品名
	 * @return 商品数
	 */
	public Integer quantityOfItemList(String searchItemName) {
		return itemRepository.quantityOfItemList(searchItemName);
	}
}