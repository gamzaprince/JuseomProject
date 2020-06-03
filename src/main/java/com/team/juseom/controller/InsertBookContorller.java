package com.team.juseom.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.team.juseom.domain.Book;
import com.team.juseom.service.InsertSearchService;

@Controller
@SessionAttributes("book")
public class InsertBookContorller {
	
	@ModelAttribute("book")
	public Book formData() {
		return new Book();
	}
	
	@RequestMapping("/shop/insert/book")
	public String insertBookForm(
			@RequestParam("isbn") String isbn,
			ModelMap model) {
		String clientId = "";
		String clientSecret = "";
		String text = null;
		try {
			text = URLEncoder.encode(isbn, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("검색어 인코딩 실패",e);
		}

		String apiURL = "https://openapi.naver.com/v1/search/book.xml?d_isbn="+ text; // xml 결과

		Map<String, String> requestHeaders = new HashMap<>();
		requestHeaders.put("X-Naver-Client-Id", clientId);
		requestHeaders.put("X-Naver-Client-Secret", clientSecret);
		String responseBody = InsertSearchService.get(apiURL,requestHeaders);
		
		Book book = InsertSearchService.parse(responseBody).get(0);
		model.put("book", book);
		return "Insert";		
	}
	
//	@RequestMappg("/shop/insert/sale")
//	public String insertBook() {
//		
//	}
//	
//	@RequestMappg("/shop/insert/share")
//	public String insertBook() {
//		
//	}
//	
//	@RequestMappg("/shop/insert/auction")
//	public String insertBook() {
//		
//	}
	
	
	
}