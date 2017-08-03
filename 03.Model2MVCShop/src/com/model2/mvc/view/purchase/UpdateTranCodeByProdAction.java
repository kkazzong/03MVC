package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

public class UpdateTranCodeByProdAction extends Action {

	public UpdateTranCodeByProdAction() {

	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		System.out.println("\n///////////////////////////////////////////////");
		System.out.println("[UpdateTranCodeByProdAction]");
		
		int prodNo = Integer.parseInt(request.getParameter("prodNo"));
		String tranCode = request.getParameter("tranCode"); //2
		
//		Purchase purchase = new Purchase();
		
//		ProductService service = new ProductServiceImpl();
//		Product product = service.getProduct(prodNo);
		PurchaseService service = new PurchaseServiceImpl();
		Purchase purchase = service.getPurchase2(prodNo);
		purchase.setTranCode(tranCode);
		
		service.updateTranCode(purchase);
		System.out.println(purchase);
		
		System.out.println("\n///////////////////////////////////////////////");
		System.out.println("[UpdateTranCodeByProdAction]");
		return "forward:/listProduct.do?menu=manage";
	}

}
