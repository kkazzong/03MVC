package com.model2.mvc.view.product;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;

public class GetProductAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		System.out.println("\n///////////////////////////////////////////////");
		System.out.println("[GetProductAction]");
		
		int prodNo = Integer.parseInt(request.getParameter("prodNo"));
		
		ProductService service = new ProductServiceImpl();
		Product product = service.getProduct(prodNo);
		
		request.setAttribute("product", product);
		
		Cookie[] cookies = request.getCookies();
		if(cookies == null) {
			response.addCookie(new Cookie("history", prodNo+","));
		} else {
			String value = prodNo+",";
			for(int i = 0; i < cookies.length; i++) {
				if(cookies[i].getName().equals("history")) {
					value = cookies[i].getValue()+prodNo+",";
					System.out.println("@@@@cookie@@@:"+value);
				}
			}
			response.addCookie(new Cookie("history", value));
		}
		
		HttpSession session = request.getSession();
		if(session == null || session.getAttribute("user") == null) {
			System.out.println("세션 널~~~");
			return "redirect:/user/loginView.jsp";
		} 
		
		System.out.println(product);
		String menu = request.getParameter("menu");
		System.out.println(":::menu : "+menu);
		
		String resultPage = null; 
		if(menu.equals("manage")) {
			
			System.out.println("[GetProductAction]");
			System.out.println("///////////////////////////////////////////////\n");
			resultPage = "forward:/updateProductView.do"; //상품수정
		} else  {	
			System.out.println("[GetProductAction]");
			System.out.println("///////////////////////////////////////////////\n");
			resultPage = "forward:/product/getProduct.jsp"; //상품상세보기
			
		}
		
		return resultPage;
	}

}
