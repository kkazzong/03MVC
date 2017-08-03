package com.model2.mvc.service.purchase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.CommonUtil;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;


public class PurchaseDAO {

	public PurchaseDAO() {
	}
	
	public Purchase getPurchase2(int prodNo) throws Exception {
		System.out.println("\n-----------------DAO getPurchase2-----------------");
		Connection con = DBUtil.getConnection();
		
		String sql = "SELECT p.prod_no, t.tran_status_code FROM product p, transaction t WHERE p.prod_no = t.prod_no AND p.prod_no =?";
		
		Purchase purchase = new Purchase();
		
		PreparedStatement pStmt = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		pStmt.setInt(1, prodNo);
		
		ResultSet rs = pStmt.executeQuery();
		while(rs.next()) {
			Product product = new Product();
			product.setProdNo(rs.getInt("prod_no"));
			purchase.setPurchaseProd(product);
//			purchase.setTranCode(rs.getString("tran_status_code"));
		}
		con.close();
		pStmt.close();
		rs.close();
		System.out.println("\n-----------------DAO getPurchase2-----------------");
		return purchase;
	}
	
	public Purchase getPurchase (int tranNo) throws Exception {
		System.out.println("\n-----------------DAO getPurchase-----------------");
		Connection con = DBUtil.getConnection();
		
		String sql = "SELECT p.prod_no, t.tran_no, t.tran_status_code FROM product p, transaction t WHERE p.prod_no = t.prod_no AND t.tran_no =?";
		Purchase purchase = new Purchase();
		
		PreparedStatement pStmt = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		pStmt.setInt(1, tranNo);
		
		ResultSet rs = pStmt.executeQuery();
		while(rs.next()) {
			Product product = new Product();
			product.setProdNo(rs.getInt("prod_no"));
			purchase.setPurchaseProd(product);
			purchase.setTranNo(rs.getInt("tran_no"));
		}
		con.close();
		pStmt.close();
		rs.close();
		System.out.println("-----------------DAO getPurchase-----------------\n");
		return purchase;
		
	}
	
	public Purchase findPurchase(int tranNo) throws Exception{
		System.out.println("\n-----------------DAO findPurchase-----------------");
		System.out.println("-----------------DAO findPurchase-----------------\n");
		return null;
	}
	
	public Map<String, Object> getPurchasList(Search search, String buyerId) throws Exception {
	
		System.out.println("\n-----------------DAO getPurchaseList-----------------");
		
		Connection con = DBUtil.getConnection();
		Map<String, Object> map = new HashMap<String, Object>();
		
//		String sql = "SELECT t.tran_no, p.prod_no, u.user_id, u.user_name, u.cell_phone, t.tran_status_code"+
//				" FROM users u, product p, transaction t"+
//				" WHERE u.user_id = t.buyer_id"+ 
//				" AND p.prod_no = t.prod_no"+
//				" AND u.user_id ='"+ buyerId+"'";
//		if(searchVO.getSearchCondition() != null) {
//			if(searchVO.getSearchCondition().equals("0")) {
//				
//			} else {
//				
//			}
//		}
		String sql = "SELECT t.tran_no, p.prod_no, p.price, p.prod_name, u.user_id, u.user_name, u.cell_phone, t.order_data, t.tran_status_code"+
				" FROM users u, product p, transaction t"+
				" WHERE u.user_id = t.buyer_id"+ 
				" AND p.prod_no = t.prod_no"+
				" AND u.user_id ='"+ buyerId+"'";
		sql += " ORDER BY t.tran_no";
		
		System.out.println(sql);
		
		int totalCount = this.getTotalCount(sql);
		
		map.put("totalCount", new Integer(totalCount));
		
		sql = this.makeCurrentPageSql(sql, search);
		
		PreparedStatement pStmt = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		
		ResultSet rs = pStmt.executeQuery();
		
//		System.out.println(":::searchVO.getPage() : "+search.getCurrentPage());
//		System.out.println(":::searchVO.getPageSize() : "+search.getPageSize());
		
		List<Purchase> list = new ArrayList<Purchase>();
	
		while(rs.next()) {
				
				User user = new User();
				user.setUserId(rs.getString("user_id"));
				user.setPhone(rs.getString("cell_phone"));
				user.setUserName(rs.getString("user_name"));
//				System.out.println(user);
				
				Product product = new Product();
				product.setProdNo(rs.getInt("prod_no"));
				product.setPrice(rs.getInt("price"));
				product.setProdName(rs.getString("prod_name"));
//				System.out.println(product);
				
				Purchase purchase = new Purchase();
				
				purchase.setTranNo(rs.getInt("tran_no"));
				purchase.setOrderDate(rs.getDate("order_data"));
				purchase.setPurchaseProd(product);
				purchase.setBuyer(user);
				purchase.setTranCode(CommonUtil.null2str(rs.getString("tran_status_code")));

				list.add(purchase);
				System.out.println(purchase);
			}
		
	
		System.out.println("list.size() : "+ list.size());
		map.put("purchaseList", list);
		System.out.println("map().size() : "+ map.size());
		
		con.close();
		pStmt.close();
		rs.close();
		
		System.out.println("-----------------DAO getPurchaseList-----------------\n");
		return map;
	}
	
	public void insertPurchase(Purchase purchase) throws Exception {
		
		System.out.println("\n-----------------DAO insertPurchase-----------------");
		
		Connection con = DBUtil.getConnection();
		
		System.out.println("purchase 확인 : : : "+purchase);
		
		String sql = "INSERT INTO transaction VALUES (seq_transaction_tran_no.nextVal, ?, ?, ?, ?, ?, ?, ?, ?, SYSDATE, ?)";
		
		PreparedStatement pStmt = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		pStmt.setInt(1, purchase.getPurchaseProd().getProdNo());
		pStmt.setString(2, purchase.getBuyer().getUserId());
		pStmt.setString(3, purchase.getPaymentOption());
		pStmt.setString(4, purchase.getReceiverName());
		pStmt.setString(5, purchase.getReceiverPhone());
		pStmt.setString(6, purchase.getDivyAddr());
		pStmt.setString(7, purchase.getDivyRequest());
		pStmt.setString(8, purchase.getTranCode());
		pStmt.setString(9, purchase.getDivyDate());
		int complete = pStmt.executeUpdate();
		
		if(complete == 1) {
			System.out.println("DB INSERT COMPLETE");
		}
		
		con.close();
		pStmt.close();
		System.out.println("-----------------DAO insertPurchase-----------------\n");
	}
	
	public void updatePurchase(Purchase purchase) {
		System.out.println("\n-----------------DAO updatePurchase-----------------");
		System.out.println("-----------------DAO updatePurchase-----------------\n");
	}
	
	public void updateTranCode(Purchase purchase) throws Exception{
		System.out.println("\n-----------------DAO updateTranCode-----------------");
		//1->2 배송하기
		Connection con = DBUtil.getConnection();
		
		
		System.out.println("tranCode :::"+purchase.getTranCode());
		
		String sql = "UPDATE transaction SET tran_status_code = ? WHERE prod_no =? ";
		PreparedStatement pStmt = con.prepareStatement(sql);
		pStmt.setString(1, purchase.getTranCode());
		pStmt.setInt(2, purchase.getPurchaseProd().getProdNo());
		int complete = pStmt.executeUpdate();
		if(complete == 1) {
			System.out.println("UPDATE COMPLETE !!!");
		}
		System.out.println("-----------------DAO updateTranCode-----------------\n");
	}
	
	public Map<String, Object> getSaleList(Search search) throws Exception {
		System.out.println("-----------------DAO getSaleList-----------------\n");
		Connection con = DBUtil.getConnection();
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = "SELECT t.tran_no, p.prod_no, p.prod_name, p.price, p.reg_date, u.user_id, u.user_name, u.cell_phone, t.tran_status_code"+
				" FROM users u, product p, transaction t"+
				" WHERE u.user_id = t.buyer_id"+ 
				" AND p.prod_no = t.prod_no"+
				" AND t.tran_status_code IS NOT NULL";
		if(search.getSearchCondition() != null) {
			if(search.getSearchCondition().equals("0")  && search.getSearchKeyword() != "") {
				sql += " AND p.prod_no = "+search.getSearchKeyword()+"";
			} else if(search.getSearchCondition().equals("1")  && search.getSearchKeyword() != "") {
				sql += " AND p.prod_name LIKE '%"+search.getSearchKeyword()+"%'";
			} else if(search.getSearchCondition().equals("2")  && search.getSearchKeyword() != "") {
				sql += " AND p.price = "+search.getSearchKeyword()+"";
			}
		}
		sql += " ORDER BY t.tran_no";
		System.out.println(sql);
		
		int totalCount = getTotalCount(sql);
		map.put("totalCount", totalCount);
		
		sql = this.makeCurrentPageSql(sql, search);
		System.out.println(sql);
		
		List<Purchase> list = new ArrayList<Purchase>();
		PreparedStatement pStmt = con.prepareStatement(sql);
		ResultSet rs = pStmt.executeQuery();
		while(rs.next()){
			User user = new User();
			user.setUserId(rs.getString("user_id"));
			user.setPhone(rs.getString("cell_phone"));
			user.setUserName(rs.getString("user_name"));
			System.out.println(user);
			
			Product product = new Product();
			product.setProdNo(rs.getInt("prod_no"));
			product.setProdName(rs.getString("prod_name"));
			product.setPrice(rs.getInt("price"));
			product.setRegDate(rs.getDate("reg_date"));
			System.out.println(product);
			
			Purchase purchase = new Purchase();
			
			purchase.setTranNo(rs.getInt("tran_no"));
			purchase.setPurchaseProd(product);
			purchase.setBuyer(user);
			purchase.setTranCode(CommonUtil.null2str(rs.getString("tran_status_code")));

			list.add(purchase);
			System.out.println(purchase);
		}
		map.put("saleList", list);
		con.close();
		pStmt.close();
		rs.close();
		System.out.println("-----------------DAO getSaleList-----------------\n");
		return map;
	}
//		게시판 Page 처리를 위한 전체 Row(totalCount)  return
		private int getTotalCount(String sql) throws Exception {
					
					sql = "SELECT COUNT(*) "+
					          "FROM ( " +sql+ ") countTable";
					
					Connection con = DBUtil.getConnection();
					PreparedStatement pStmt = con.prepareStatement(sql);
					ResultSet rs = pStmt.executeQuery();
					
					int totalCount = 0;
					if( rs.next() ){
						totalCount = rs.getInt(1);
					}
					
					pStmt.close();
					con.close();
					rs.close();
					
					return totalCount;
				}
				
// 		게시판 currentPage Row 만  return 
		private String makeCurrentPageSql(String sql , Search search){
					sql = 	"SELECT * "+ 
								"FROM (SELECT inner_table. * ,  ROWNUM AS row_seq " +
												" 	FROM (	"+sql+" ) inner_table "+
												"	WHERE ROWNUM <="+search.getCurrentPage()*search.getPageSize()+" ) " +
								"WHERE row_seq BETWEEN "+((search.getCurrentPage()-1)*search.getPageSize()+1) +" AND "+search.getCurrentPage()*search.getPageSize();
					
					System.out.println("PurchaseDAO :: make SQL :: "+ sql);	
					
					return sql;
		}
		
		
	}

