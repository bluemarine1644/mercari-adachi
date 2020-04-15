package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.Item;
import com.example.demo.repository.ItemRepository;

/**
 * 商品詳細情報を操作するサービス.
 * 
 * @author sota_adachi
 *
 */
@Service
@Transactional
public class DisplayItemDetailService {
	@Autowired
	private ItemRepository itemRepository;

	/**
	 * 商品IDから商品詳細情報を取得します.
	 * 
	 * @param id 商品ID
	 * @return 商品詳細情報
	 */
	public Item showDetail(Integer id) {
		Item itemDetail = itemRepository.findById(id);
		return itemDetail;
	}

}
