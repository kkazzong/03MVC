package com.model2.mvc.view.product;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;

public class ListProductAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		System.out.println("\n///////////////////////////////////////////////");
		System.out.println("[ListProductAction]");
		
		Search search = new Search();
		
		int currentPage = 1;
		if(request.getParameter("currentPage") != null) {
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}
		
		search.setCurrentPage(currentPage);
		search.setSearchCondition(request.getParameter("searchCondition"));
		search.setSearchSoldProd(request.getParameter("searchSoldProd")); //판매된상품
		search.setSearchKeyword(request.getParameter("searchKeyword"));
		search.setSearchKeywordPrice(request.getParameter("searchKeywordPrice")); //가격
		search.setSortCondition(request.getParameter("sortCondition")); //이름정렬
		search.setSortCondition2(request.getParameter("sortCondition2")); //가격정렬
		
		
		int pageUnit = Integer.parseInt(getServletContext().getInitParameter("pageUnit"));
		int pageSize = Integer.parseInt(getServletContext().getInitParameter("pageSize"));
		search.setPageSize(pageSize);
		System.out.println(search);
		
		ProductService service = new ProductServiceImpl();
		Map<String, Object> map = service.getProductList(search);
		
		Page resultPage = new Page(currentPage, ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		request.setAttribute("list", map.get("productList"));
		request.setAttribute("search", search);
		request.setAttribute("resultPage", resultPage);
		
		System.out.println("[ListProductAction]");
		System.out.println("///////////////////////////////////////////////\n");
		
		return "forward:/product/listProduct.jsp";
	}

}
