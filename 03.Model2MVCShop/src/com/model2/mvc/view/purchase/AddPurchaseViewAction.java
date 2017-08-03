package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;

public class AddPurchaseViewAction extends Action {

	public AddPurchaseViewAction() {
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		System.out.println("\n////////////////////////////////////////////////////////////////////");
		System.out.println("[AddPurchaseViewAction]");
		
		Product product = new Product();
		
		String prodNo = request.getParameter("prod_no");
		int no = Integer.parseInt(prodNo);
		System.out.println(":::prodNo : "+no);
		
		
		ProductService service = new ProductServiceImpl();
		product = service.getProduct(no);
		
		System.out.println(product);
		
		request.setAttribute("product", product);
		
		System.out.println("[AddPurchaseViewAction]");
		System.out.println("////////////////////////////////////////////////////////////////////\n");
		
		return "forward:/purchase/addPurchaseView.jsp";
	}

}
