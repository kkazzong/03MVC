package com.model2.mvc.view.purchase;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

public class ListPurchaseAction extends Action {

	public ListPurchaseAction() {
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		System.out.println("\n///////////////////////////////////////////////");
		System.out.println("[ListPurchaseAction]");
		Search search = new Search();
		
		int currentPage = 1;
		if(request.getParameter("currentPage")!= null) {
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}
		
		search.setCurrentPage(currentPage);
//		searchVO.setSearchCondition(request.getParameter("searchCondition"));
//		searchVO.setSearchKeyword(request.getParameter("searchKeyword"));
		
		int pageUnit = Integer.parseInt(getServletContext().getInitParameter("pageUnit"));
		int pageSize = Integer.parseInt(getServletContext().getInitParameter("pageSize"));
		search.setPageSize(pageSize);
		
		String buyerId = null;
		HttpSession session = request.getSession();
		if(session == null || session.getAttribute("user") == null) {
			return "redirect:/user/loginView.jsp";
		} else {
			User user = (User)session.getAttribute("user");
			buyerId = user.getUserId();
		}
		System.out.println(":::buyerId = "+buyerId);
		PurchaseService service = new PurchaseServiceImpl();
		Map<String, Object> map = service.getPurchaseList(search, buyerId);
		
		Page resultPage = new Page(currentPage, ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		request.setAttribute("list", map.get("purchaseList"));
		request.setAttribute("search", search);
		request.setAttribute("resultPage", resultPage);
		
		System.out.println("///////////////////////////////////////////////\n");
		System.out.println("[ListPurchaseAction]");
		
		return "forward:/purchase/listPurchase.jsp";
	}

}
