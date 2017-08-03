package com.model2.mvc.service.product.dao;

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

public class ProductDAO {
	//Field
//	private static final String TABLE_NAME = "Product";
	//Constructor
	public ProductDAO() {
		// TODO Auto-generated constructor stub
	}
	
	public Product findProduct(int prodNo) throws Exception{
		
		System.out.println("\n-----------------DAO findProduct-----------------");
		
		Connection con = DBUtil.getConnection();
	
//		String sql = "SELECT * FROM product WHERE prod_no = ?";
		String sql = "SELECT t.tran_status_code, p.* FROM product p, transaction t WHERE p.prod_no = t.prod_no AND p.prod_no = ?";
		
		PreparedStatement pStmt = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		pStmt.setInt(1, prodNo);
		
		ResultSet rs = pStmt.executeQuery();
		
		Product product = null;
		
		while(rs.next()) {
			product = new Product();
			product.setProdNo(rs.getInt("prod_no"));
			product.setProdName(rs.getString("prod_name"));
			product.setProdDetail(rs.getString("prod_detail"));
			product.setPrice(rs.getInt("price"));
			product.setManuDate(rs.getString("manufacture_day"));
			product.setFileName(rs.getString("image_file"));
			product.setRegDate(rs.getDate("reg_date"));
			String tranCode = rs.getString("tran_status_code");
			if(tranCode == null) {
				product.setProTranCode("0");
			} else {
				product.setProTranCode(CommonUtil.null2str(tranCode));
			}
		}
		
		System.out.println(product);
		
		con.close();
		
		System.out.println("-----------------DAO findProduct-----------------\n");
		
		return product;
	}
	
	public Map<String, Object> getProductList(Search search) throws Exception{
		
		System.out.println("\n-----------------DAO getList-----------------");
		
		Map<String , Object>  map = new HashMap<String, Object>();
		
		Connection con = DBUtil.getConnection();

		String sql = "SELECT t.tran_status_code, p.* FROM product p, transaction t WHERE p.prod_no = t.prod_no(+)";
		if(search.getSearchCondition() != null) {
			if(search.getSearchCondition().equals("0") &&  !search.getSearchKeyword().equals("") ) {
				sql += " AND p.prod_no = "+search.getSearchKeyword()+"";
			} else if(search.getSearchCondition().equals("1") &&  !search.getSearchKeyword().equals("") ) {
				sql += " AND p.prod_name LIKE '%"+search.getSearchKeyword()+"%'";
			} else if(search.getSearchCondition().equals("2") &&  !search.getSearchKeyword().equals("") ) {
				if(!search.getSearchKeywordPrice().equals("")) {
					sql += " AND p.price BETWEEN "+search.getSearchKeyword()+" AND "+search.getSearchKeywordPrice();
				} else {
					sql += " AND p.price >= "+search.getSearchKeyword();
				}
			} 
		}
		
		if(search.getSearchSoldProd() != null && search.getSearchSoldProd().equals("1")) {
			sql+=" AND t.tran_status_code IS NOT NULL";
		} else {
			sql+=" AND t.tran_status_code IS NULL";
		}
		
		if(search.getSortCondition() != null && !search.getSortCondition().equals("")) {
			if(search.getSortCondition().equals("asc")) {
				sql+=" ORDER BY p.prod_name";
			} else {
				sql+=" ORDER BY p.prod_name DESC";
			}
		} else if(search.getSortCondition2() != null && ! search.getSortCondition2().equals("")) {
			if(search.getSortCondition2().equals("asc")) {
				sql+=" ORDER BY p.price";
			} else {
				sql+=" ORDER BY p.price DESC";
			}
		} else {
			sql+=" ORDER BY p.prod_no";
		}
		
		System.out.println("ProductDAO::Original SQL :: " + sql);
		
		//==> TotalCount GET
		int totalCount = this.getTotalCount(sql);
		System.out.println("ProductDAO :: totalCount  :: " + totalCount);
		
		//==> CurrentPage 게시물만 받도록 Query 다시구성
		sql = makeCurrentPageSql(sql, search);
		PreparedStatement pStmt = con.prepareStatement(sql);
		ResultSet rs = pStmt.executeQuery();
	
		System.out.println(search);

		List<Product> list = new ArrayList<Product>();
		
		
		while(rs.next()) {
				Product product = new Product();
				product.setProdNo(rs.getInt("prod_no"));
				product.setProdName(rs.getString("prod_name"));
				product.setProdDetail(rs.getString("prod_detail"));
				product.setManuDate(rs.getString("manufacture_day"));
				product.setPrice(rs.getInt("price"));
				product.setFileName(rs.getString("image_file"));
				product.setRegDate(rs.getDate("reg_date"));
				
//				String tranCode = rs.getString("tran_status_code");
				String tranCode = rs.getString("tran_status_code");
				System.out.println("tranCode 확인 :::::::"+tranCode);
				if(tranCode == null) {
					product.setProTranCode("0");
				} else {
					product.setProTranCode(CommonUtil.null2str(tranCode));
				}
//				switch(tranCode) {
//					case "1":
//						product.setProTranCode("구매완료");
//						break;
//					case "2":
//						product.setProTranCode("배송중");
//						break;
//					case "3":
//						product.setProTranCode("배송완료");
//						break;
//					default:
//						product.setProTranCode("판매중");
//						break;
//				}
//				product.setProTranCode(tranCode);
				System.out.println("product DOMAIN 확인");
				System.out.println(product);
				list.add(product);
		}
		//==> totalCount 정보 저장
		map.put("totalCount", new Integer(totalCount));
		map.put("productList", list);
		
		System.out.println(":::list size() : "+list.size());
		System.out.println(":::map size() : "+map.size());
		System.out.println("-----------------DAO getList-----------------\n");
		
		con.close();
		pStmt.close();
		rs.close();
		
		return map;
	}
	
	public void insertProduct(Product vo)  throws Exception{
		
		System.out.println("\n-----------------DAO insert-----------------");
		
		Connection con = DBUtil.getConnection();
		
		String sql = "INSERT INTO product VALUES (seq_product_prod_no.nextval,?,?,?,?,?,SYSDATE)";
		
		PreparedStatement pStmt = con.prepareStatement(sql);
		pStmt.setString(1, vo.getProdName());
		pStmt.setString(2, vo.getProdDetail());
		pStmt.setString(3, vo.getManuDate());
		pStmt.setInt(4, vo.getPrice());
		pStmt.setString(5, vo.getFileName());
		int complete = pStmt.executeUpdate();
		
		if(complete == 1) {
			System.out.println("INSERT comlete!!");
		}
		
		con.close();
		System.out.println("-----------------DAO insert-----------------\n");
	}
	
	public void updateProduct(Product vo) throws Exception {
		System.out.println("\n-----------------DAO updateProduct-----------------");
		Connection con = DBUtil.getConnection();
		
		String sql = "UPDATE product SET prod_name=?, prod_detail=?, manufacture_day=?, price=?, image_file=?, reg_date=SYSDATE WHERE prod_no=?";
		
		PreparedStatement pStmt = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		pStmt.setString(1, vo.getProdName());
		pStmt.setString(2, vo.getProdDetail());
		pStmt.setString(3, vo.getManuDate());
		pStmt.setInt(4, vo.getPrice());
		pStmt.setString(5, vo.getFileName());
		pStmt.setInt(6, vo.getProdNo());
		int complete = pStmt.executeUpdate();
		
		if(complete == 1) {
			System.out.println("UPDATE comlete!!");
		}
		
		System.out.println("-----------------DAO updateProduct-----------------\n");
	}
	
	// 게시판 Page 처리를 위한 전체 Row(totalCount)  return
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
			
	// 게시판 currentPage Row 만  return 
	private String makeCurrentPageSql(String sql , Search search){
				sql = 	"SELECT * "+ 
							"FROM (		SELECT inner_table. * ,  ROWNUM AS row_seq " +
											" 	FROM (	"+sql+" ) inner_table "+
											"	WHERE ROWNUM <="+search.getCurrentPage()*search.getPageSize()+" ) " +
							"WHERE row_seq BETWEEN "+((search.getCurrentPage()-1)*search.getPageSize()+1) +" AND "+search.getCurrentPage()*search.getPageSize();
				
				System.out.println("UserDAO :: make SQL :: "+ sql);	
				
				return sql;
			}
	}
