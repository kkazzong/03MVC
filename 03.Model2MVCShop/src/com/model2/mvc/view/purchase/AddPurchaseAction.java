package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;


public class AddPurchaseAction extends Action {

	public AddPurchaseAction() {
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		System.out.println("\n////////////////////////////////////////////////////////////////////");
		System.out.println("[AddPurchaseAction]");
		
		Purchase purchase = new Purchase();
		
		String buyerId = request.getParameter("buyerId");
		int prodNo = Integer.parseInt(request.getParameter("prodNo"));
		
		System.out.println(":::buyerId : "+buyerId+" :::prodNo :"+prodNo);
		
		HttpSession session = request.getSession();
		if(session == null || session.getAttribute("user") == null) {
			
			System.out.println("!!!! SESSION NULL !!!!");
//			return "redirect:/user/loginView.jsp";
			
		} else {
			
			User user = (User)session.getAttribute("user");
			if(user.getUserId().equals(buyerId)) {
				purchase.setBuyer(user);
				System.out.println(user);
			}
		}
		
		ProductService productService = new ProductServiceImpl();
		Product product = productService.getProduct(prodNo);
		
		purchase.setPurchaseProd(product);
		System.out.println(product);
		purchase.setPaymentOption(request.getParameter("paymentOption"));
		purchase.setReceiverName(request.getParameter("receiverName"));
		purchase.setReceiverPhone(request.getParameter("receiverPhone"));
		purchase.setDivyAddr(request.getParameter("receiverAddr"));
		purchase.setDivyRequest(request.getParameter("receiverRequest"));
		purchase.setDivyDate(request.getParameter("receiverDate"));
		purchase.setTranCode("1");
		
		PurchaseService service = new PurchaseServiceImpl();
		service.addPurchase(purchase);
		
		request.setAttribute("purchase", purchase);
		
		System.out.println("[AddPurchaseAction]");
		System.out.println("////////////////////////////////////////////////////////////////////\n");
		
		return "forward:/purchase/addPurchase.jsp";
	}

}
