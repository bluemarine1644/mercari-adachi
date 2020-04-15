package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.domain.Item;
import com.example.demo.form.DisplayItemListForm;
import com.example.demo.service.DisplayItemListService;

/**
 * 商品一覧情報を表示するコントローラー.
 * 
 * @author sota_adachi
 *
 */
@Controller
@RequestMapping("/item")
public class DisplayItemListController {
	@Autowired
	private DisplayItemListService displayItemListService;
	// 1ページに表示する商品数
	private static final Integer VIEW_SIZE = 30;
	// ページ移動
	private static final Integer CHANGE_PAGE = 1;

	/**
	 * 使用するフォームオブジェクトをリクエストスコープに格納する.
	 * 
	 * @return フォーム
	 */
	@ModelAttribute
	public DisplayItemListForm setUpUpdeteItemForm() {
		return new DisplayItemListForm();
	}

	@RequestMapping("/showList")
	public String showList(DisplayItemListForm form, Model model, String searchItemName, Integer pageNumber) {
		model.addAttribute("VIEW_SIZE", VIEW_SIZE);
		model.addAttribute("CHANGE_PAGE", CHANGE_PAGE);
		// 検索文字列が空なら全件検索
		if (searchItemName == null) {
			searchItemName = "";
		}
		model.addAttribute("searchItemName", searchItemName);
		// ページ指定がない場合は初期値
		if (pageNumber == null) {
			pageNumber = 1;
		}
		Integer offsetValue = VIEW_SIZE * (pageNumber - 1);			
		model.addAttribute("offsetValue", offsetValue);
		model.addAttribute("pageNumber", pageNumber);
		// 曖昧検索で検索された商品返す
		Integer quantityOfItemList = displayItemListService.quantityOfItemList(searchItemName);
		model.addAttribute("quantityOfItemList", quantityOfItemList);
		 // 検索文字列があれば曖昧検索
		List<Item> itemList = displayItemListService.serchByItemName(searchItemName, VIEW_SIZE, offsetValue);
		model.addAttribute("itemList", itemList);
		return "list";
	}
}