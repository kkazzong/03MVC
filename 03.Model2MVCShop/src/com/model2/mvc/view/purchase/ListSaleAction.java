package com.model2.mvc.view.purchase;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

public class ListSaleAction extends Action {

	public ListSaleAction() {
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		System.out.println("\n///////////////////////////////////////////////");
		System.out.println("[ListSaleAction]");
		
		Search search = new Search();
		
		int currentPage = 1;
		if(request.getParameter("currentPage") != null) {
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}
		
		search.setCurrentPage(currentPage);
		search.setSearchCondition(request.getParameter("searchCondition"));
		search.setSearchKeyword(request.getParameter("searchKeyword"));
		
		int pageUnit = Integer.parseInt(getServletContext().getInitParameter("pageUnit"));
		int pageSize = Integer.parseInt(getServletContext().getInitParameter("pageSize"));
		search.setPageSize(pageSize);
		System.out.println(search);
		
		PurchaseService service = new PurchaseServiceImpl();
		Map<String, Object> map = service.getSaleList(search);
		
		Page resultPage = new Page(currentPage, ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		request.setAttribute("list", map.get("saleList"));
		request.setAttribute("search", search);
		request.setAttribute("resultPage", resultPage);
		
		System.out.println("[ListSaleAction]");
		System.out.println("///////////////////////////////////////////////\n");
		
		return "forward:/purchase/listSale.jsp";
	}

}
