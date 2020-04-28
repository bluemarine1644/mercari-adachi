package com.example.demo.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.domain.Category;
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

	@RequestMapping("/")
	public String displayItemList(DisplayItemListForm form, Model model) {
		model.addAttribute("VIEW_SIZE", VIEW_SIZE);
		model.addAttribute("CHANGE_PAGE", CHANGE_PAGE);
		// 商品名検索文字列が空なら全件検索
		String searchItemName = form.getSearchItemName();
		if (searchItemName == null) {
			searchItemName = "";
		}
		model.addAttribute("searchItemName", searchItemName);
		// 検索カテゴリIDをフォームにセット
		setCategoryId(form, getCategoryList());
		// カテゴリ名検索文字列が空なら全件検索
		String searchCategoryName = form.getSearchCategoryName();
		if (searchCategoryName == null) {
			searchCategoryName = "";
		}
		String searchBrandName = form.getSearchBrandName();
		// ブランド名検索文字列が空なら全件検索
		if (searchBrandName == null) {
			searchBrandName = "";
		}
		model.addAttribute("searchBrandName", searchBrandName);
		Integer pageNumber = form.getPageNumber();
		// ページ指定がない場合は初期値
		if (pageNumber == null) {
			pageNumber = 1;
		}
		Integer offsetValue = VIEW_SIZE * (pageNumber - 1);
		model.addAttribute("offsetValue", offsetValue);
		model.addAttribute("pageNumber", pageNumber);
		// 曖昧検索で検索された商品返す
		Integer quantityOfItemList = displayItemListService.quantityOfItemList(searchItemName, searchCategoryName, searchBrandName);
		model.addAttribute("quantityOfItemList", quantityOfItemList);
		// 検索文字列があれば曖昧検索
		List<Item> itemList = displayItemListService.searchItem(searchItemName, searchCategoryName, searchBrandName, VIEW_SIZE, offsetValue);
		System.out.println("itemList: " + itemList);
		model.addAttribute("itemList", itemList);
		System.out.println(model);
		return "list";
	}

	@Autowired
	private HttpSession session;
	
	@RequestMapping("/categoryList")
	@ResponseBody
	public List<Category> CategoryList() {
		return getCategoryList();
	}
	
	/**
	 * カテゴリ情報を取得する.
	 * セッションスコープに保持し、セッションにない場合のみDBから取得する.
	 * 
	 * @return カテゴリ情報
	 */
	private List<Category> getCategoryList() {
		@SuppressWarnings("unchecked")
		List<Category> categoryList = (List<Category>) session.getAttribute("categoryList");
		if (categoryList == null) {
			categoryList = displayItemListService.findAllCategory();
			session.setAttribute("categoryList", categoryList);
		}
		return categoryList;
	}

	private void setCategoryId(DisplayItemListForm form, List<Category> categoryList) {
		// 一旦全てクリアする
		form.setBigCategoryId(null);
		form.setMiddleCategoryId(null);
		form.setSmallCategoryId(null);
		// カテゴリ検索が行われた場合、検索されたカテゴリ名を分解し配列に格納する
		if (form.getSearchCategoryName() != null) {
			String[] categoryArray = form.getSearchCategoryName().split("/");
			// 大カテゴリが選択されている場合そのカテゴリIDをフォームにセットする
			if (categoryArray.length >= 1 && !"".equals(categoryArray[0])) {
				Category bigCategory = getCategoryByName(categoryList, categoryArray[0]);
				form.setBigCategoryId(bigCategory.getId());
				// 中カテゴリが選択されている場合そのカテゴリIDをフォームにセットする
				if (categoryArray.length >= 2) {
					Category middleCategory = getCategoryByName(bigCategory.getChildCategoryList(), categoryArray[1]);
					form.setMiddleCategoryId(middleCategory.getId());
					// 小カテゴリが選択されている場合そのカテゴリIDをフォームにセットする
					if (categoryArray.length >= 3) {
						Category smallCategory = getCategoryByName(middleCategory.getChildCategoryList(), categoryArray[2]);
						form.setSmallCategoryId(smallCategory.getId());
					}
				}
			}
		}
	}

	/**
	 * カテゴリ名からカテゴリ情報を取得する.
	 *
	 * @param categoryList カテゴリ情報リスト
	 * @param categoryName カテゴリ名
	 * @return カテゴリ情報
	 */
	private Category getCategoryByName(List<Category> categoryList, String categoryName) {
		for (Category category : categoryList) {
			if (category.getName().equals(categoryName)) {
				return category;
			}
		}
		return null;
	}
}