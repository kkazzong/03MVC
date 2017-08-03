package com.model2.mvc.service.product.impl;

import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.dao.ProductDAO;

public class ProductServiceImpl implements ProductService {
	//Field
	private ProductDAO dao;

	//Constructor
	public ProductServiceImpl() {
		dao = new ProductDAO();
	}
	
	//Method
	@Override
	public void addProduct(Product product) throws Exception {
		System.out.println("====[ProductServiceImpl].addProduct()==== ");
		dao.insertProduct(product);
	}

	@Override
	public Product getProduct(int prodNo) throws Exception {
		System.out.println("====[ProductServiceImpl].getProduct()==== ");
		return dao.findProduct(prodNo);
	}

	@Override
	public Map<String, Object> getProductList(Search search) throws Exception {
		System.out.println("====[ProductServiceImpl].getProductList()==== ");
		return dao.getProductList(search);
	}

	@Override
	public void updateProduct(Product product) throws Exception {
		System.out.println("====[ProductServiceImpl].updateProduct()==== ");
		dao.updateProduct(product);
	}

}
