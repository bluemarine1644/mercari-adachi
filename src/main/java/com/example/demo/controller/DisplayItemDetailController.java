package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.domain.Item;
import com.example.demo.service.DisplayItemDetailService;

/**
 * 商品詳細情報を操作するコントローラ.
 * 
 * @author sota_adachi
 *
 */
@Controller
public class DisplayItemDetailController {
	@Autowired
	private DisplayItemDetailService displayItemDetailService;
	
	@RequestMapping("/showItemDetail")
	public String showDetail(Model model, Integer id, Integer pageNumber, String searchItemName) {
		Item itemDetail = displayItemDetailService.showDetail(id);
		model.addAttribute("itemDetail", itemDetail);
		model.addAttribute("pageNumber", pageNumber);
		model.addAttribute("searchItemName", searchItemName);
		return "detail";
	}

}
