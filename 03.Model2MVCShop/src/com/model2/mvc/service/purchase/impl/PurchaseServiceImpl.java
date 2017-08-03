package com.model2.mvc.service.purchase.impl;

import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.product.dao.ProductDAO;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.dao.PurchaseDAO;

public class PurchaseServiceImpl implements PurchaseService {
	
	//Field
	private PurchaseDAO dao;
	private ProductDAO prodDAO;
	
	//Constructor
	public PurchaseServiceImpl() {
		dao = new PurchaseDAO();
		prodDAO = new ProductDAO();
	}

	//Method
	@Override
	public void addPurchase(Purchase purchase) throws Exception {
		System.out.println("====[PurchaseServiceImpl].addPurchase()==== ");
		dao.insertPurchase(purchase);
	}

	@Override
	public Purchase getPurchase (int tranNo) throws Exception {
		System.out.println("====[PurchaseServiceImpl].getPurchase()==== ");
		return dao.getPurchase(tranNo);
	}

	@Override
	public Purchase getPurchase2(int prodNo) throws Exception {
		System.out.println("====[PurchaseServiceImpl].getPurchase2()==== ");
		return dao.getPurchase2(prodNo);
	}

	@Override
	public Map<String, Object> getPurchaseList(Search search, String buyerId) throws Exception {
		System.out.println("====[PurchaseServiceImpl].getPurchaseList()==== ");
		return dao.getPurchasList(search, buyerId);
	}

	@Override
	public Map<String, Object> getSaleList(Search search) throws Exception {
		System.out.println("====[PurchaseServiceImpl].getSaleList()==== ");
		return dao.getSaleList(search);
	}

	@Override
	public void updatePurcahse(Purchase purchase) throws Exception {
		System.out.println("====[PurchaseServiceImpl].updatePurchase()==== ");
	}

	@Override
	public void updateTranCode(Purchase purchase) throws Exception {
		System.out.println("====[PurchaseServiceImpl].updateTranCode()==== ");
		dao.updateTranCode(purchase);
	}

}
