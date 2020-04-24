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
	public String displayItemList(DisplayItemListForm form, Model model, String searchItemName, String searchBrandName, Integer pageNumber) {
System.out.println("---displayItemList()---");
//System.out.println("searchItemName:[" + searchItemName + "], pageNumber:[" + pageNumber + "]");
		model.addAttribute("VIEW_SIZE", VIEW_SIZE);
		model.addAttribute("CHANGE_PAGE", CHANGE_PAGE);
		// 商品名検索文字列が空なら全件検索
		if (searchItemName == null) {
			searchItemName = "";
		}
		model.addAttribute("searchItemName", searchItemName);
		// ブランド名検索文字列が空なら全件検索
		if (searchBrandName == null) {
			searchBrandName = "";
		}
		model.addAttribute("searchBrandName", searchBrandName);
		// ページ指定がない場合は初期値
		if (pageNumber == null) {
			pageNumber = 1;
		}
		Integer offsetValue = VIEW_SIZE * (pageNumber - 1);
		model.addAttribute("offsetValue", offsetValue);
		model.addAttribute("pageNumber", pageNumber);
		// 曖昧検索で検索された商品返す
		Integer quantityOfItemList = displayItemListService.quantityOfItemList(searchItemName, searchBrandName);
		model.addAttribute("quantityOfItemList", quantityOfItemList);
		// 検索文字列があれば曖昧検索
		List<Item> itemList = displayItemListService.serchByItemAndBrandName(searchItemName, searchBrandName, VIEW_SIZE, offsetValue);
		model.addAttribute("itemList", itemList);
//System.out.println(itemList);
		return "list";
	}
	
	@Autowired
	private HttpSession session;
	
	@RequestMapping("/categoryList")
	@ResponseBody
	public List<Category> CategoryList() {
System.out.println("---CategoryList()---");
System.out.println(getCategoryList().toString());
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
	
	/**
	 * 検索完了時、カテゴリのプルダウンを維持するために、検索カテゴリ名から大、中、小カテゴリIDを求めてフォームにセットする.
	 * 
	 * @param form フォーム
	 * @param categoryList カテゴリ情報
	 */
	public void setCategoryId(DisplayItemListForm form, List<Category> categoryList) {
		// フォーム内にセットされているIDを全てクリアする
		form.setBigCategoryId(null);
		form.setMiddleCategoryId(null);
		form.setSmallChildCategoryId(null);
		// 検索カテゴリ名がセットされている場合
		if (form.getSearchCategoryName() != null) {
			// 検索カテゴリ名を大・中・小に分離させてそれぞれ配列に格納する
			String[] categoryArray = form.getSearchCategoryName().split("/");
			// 初期値以外の大カテゴリが選択されている場合
			if (categoryArray.length >= 1 && !"".equals(categoryArray[0])) {
				// 大カテゴリ名に対応したIDをフォームに格納する
				Category bigCategory = getCategoryByName(categoryList, categoryArray[0]);
				form.setBigCategoryId(bigCategory.getId());
				// 中カテゴリまで選択されている場合
				if (categoryArray.length >= 2) {
					// 中カテゴリ名に対応したIDをフォームに格納する
					Category middleCategory = getCategoryByName(bigCategory.getChildCategoryList(), categoryArray[1]);
					form.setMiddleCategoryId(middleCategory.getId());
					// 小カテゴリまで選択されている場合
					if (categoryArray.length >= 3) {
						// 小カテゴリ名に対応したIDをフォームに格納する
						Category smallCategory = getCategoryByName(middleCategory.getChildCategoryList(), categoryArray[2]);
						form.setSmallChildCategoryId(smallCategory.getId());
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