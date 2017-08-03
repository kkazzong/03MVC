package com.model2.mvc.view.product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;

public class UpdateProductAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		System.out.println("\n///////////////////////////////////////////////");
		System.out.println("[UpdateProductAction]");
		
		int prodNo = Integer.parseInt(request.getParameter("prodNo"));
		System.out.println(":::prodNo : "+prodNo);
		
		Product product = new Product();
		
		product.setProdNo(prodNo);
		product.setProdName(request.getParameter("prodName"));
		product.setProdDetail(request.getParameter("prodDetail"));
		product.setPrice(Integer.parseInt(request.getParameter("price")));
		product.setManuDate(request.getParameter("manuDate").replaceAll("-", ""));
		product.setFileName(request.getParameter("fileName"));
		product.setProTranCode("∆«∏≈¡ﬂ");
		
		ProductService service = new ProductServiceImpl();
		service.updateProduct(product);
		product = service.getProduct(prodNo);
		
		request.setAttribute("product", product);
		
		System.out.println("[UpdateProductAction]");
		System.out.println("///////////////////////////////////////////////\n");
		
		return "forward:/product/getProduct.jsp?menu=manage";
//		return "forward:/getProduct.do?prodNo="+prodNo;
	}

}
