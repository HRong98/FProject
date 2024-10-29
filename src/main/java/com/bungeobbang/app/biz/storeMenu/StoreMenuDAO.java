package com.bungeobbang.app.biz.storeMenu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.bungeobbang.app.biz.common.JDBCUtil;

@Repository
public class StoreMenuDAO {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	//가게 메뉴정보 추가
	//받는 데이터 : 가게 고유번호(FK), 팥/슈프림, 야채/김치/만두, 미니, 고구마, 아이스크림/초코, 치즈, 페스츄리, 기타 판매 여부(Y/N)
	//추가 데이터 : 메뉴 고유번호(PK), 가게 고유번호(FK), 팥/슈프림, 야채/김치/만두, 미니, 고구마, 아이스크림/초코, 치즈, 페스츄리, 기타 판매 여부(Y/N)
	final String INSERT = "INSERT INTO BB_STORE_MENU (STORE_NUM, "
			+ "STORE_MENU_NORMAL, STORE_MENU_VEG, STORE_MENU_MINI, STORE_MENU_POTATO, "
			+ "STORE_MENU_ICE, STORE_MENU_CHEESE, STORE_MENU_PASTRY, STORE_MENU_OTHER) "
			+ "VALUES(?,?,?,?,?,?,?,?,?)";

	//메뉴별 판매하는 매장 총 갯수 : 팥/슈프림, 야채/김치/만두, 미니, 고구마, 아이스크림/초코, 치즈, 페스츄리, 기타
	//카테고리별 판매하는 총 매장 수 그룹핑 기준 메뉴 고유번호 총 개수 구하기 
	//Y or N 값에 따라 고유번호 기준 중복가게 발생.
	//따라서 Y로만 표현된 데이터 조회하여 중복 가게제거
	final String SELECTALL = "SELECT STORE_MENU_NORMAL, STORE_MENU_VEG, STORE_MENU_MINI, STORE_MENU_POTATO, " +
			"STORE_MENU_ICE, STORE_MENU_CHEESE, STORE_MENU_PASTRY, STORE_MENU_OTHER, COUNT(STORE_MENU_NUM) AS STOREMENU_COUNT " +
			"FROM BB_STORE_MENU " +
			"WHERE STORE_MENU_NORMAL = 'Y' OR STORE_MENU_VEG = 'Y' OR STORE_MENU_MINI = 'Y' OR " +
			"STORE_MENU_POTATO = 'Y' OR STORE_MENU_ICE = 'Y' OR STORE_MENU_CHEESE = 'Y' OR " +
			"STORE_MENU_PASTRY = 'Y' OR STORE_MENU_OTHER = 'Y' " +
			"GROUP BY STORE_MENU_NORMAL, STORE_MENU_VEG, STORE_MENU_MINI, STORE_MENU_POTATO, " +
			"STORE_MENU_ICE, STORE_MENU_CHEESE, STORE_MENU_PASTRY, STORE_MENU_OTHER";

	//특정 가게 메뉴 조회
	//받는 데이터 : 가게 고유번호
	//조회 데이터 : 가게 고유번호(FK), 팥/슈프림, 야채/김치/만두, 미니, 고구마, 아이스크림/초코, 치즈, 페스츄리, 기타 판매 여부(Y/N)
	final String SELECTONE = "SELECT STORE_NUM, STORE_MENU_NORMAL, STORE_MENU_VEG, STORE_MENU_MINI, STORE_MENU_POTATO, " +
			"STORE_MENU_ICE, STORE_MENU_CHEESE, STORE_MENU_PASTRY, STORE_MENU_OTHER " +
			"FROM BB_STORE_MENU WHERE STORE_NUM = ?";

	//가게 메뉴 정보 수정
	//받는 데이터 : 가게 고유번호(FK), 팥/슈프림, 야채/김치/만두, 미니, 고구마, 아이스크림/초코, 치즈, 페스츄리, 기타 판매 여부(Y/N)
	final String UPDATE = "UPDATE BB_STORE_MENU " +
			"SET STORE_MENU_NORMAL = ?, STORE_MENU_VEG = ?, " +
			"STORE_MENU_MINI = ?, STORE_MENU_POTATO = ?, " +
			"STORE_MENU_ICE = ?, STORE_MENU_CHEESE = ?, " +
			"STORE_MENU_PASTRY = ?, STORE_MENU_OTHER = ? " +
			"WHERE STORE_NUM = ?";


	//   StoreMenuDAO insert   ---------------------------------------------- ----------------------------------------   

	public boolean insert(StoreMenuDTO storeMenuDTO) { // 신규등록가게 메뉴추가
		System.out.println("log_StoreMenuDAO_insert : start");

		// 비정상 프로그램 종료 방지 위한 try-catch 진행
		try { 
			String query =INSERT;

			Object[] args = {
					storeMenuDTO.getStoreNum()             // 가게 고유번호(FK) 입력
					, storeMenuDTO.getStoreMenuNormal()       // 팥/슈프림 붕어빵 판매 여부(Y/N) 입력
					, storeMenuDTO.getStoreMenuVeg()    // 야채/김치/만두 판매 여부(Y/N) 입력
					, storeMenuDTO.getStoreMenuMini()       // 미니 붕어빵 판매 여부(Y/N) 입력
					, storeMenuDTO.getStoreMenuPotato()       // 고구마 붕어빵 판매 여부(Y/N) 입력
					, storeMenuDTO.getStoreMenuIce()    // 아이스크림/초코 붕어빵 판매 여부(Y/N) 입력
					, storeMenuDTO.getStoreMenuCheese()      // 치즈 붕어빵 판매 여부(Y/N) 입력
					, storeMenuDTO.getStoreMenuPastry()       // 페스츄리 붕어빵 판매 여부(Y/N) 입력
					, storeMenuDTO.getStoreMenuOther()
			};       // 기타 붕어빵 해당 여부(Y/N) 입력


			// rs 변수 선언 : INSERT 쿼리문 실행
			int rs = jdbcTemplate.update(query,args);
			System.out.println("log_StoreMenuDAO_insert_excuteUpdate() complete");

			//[6] 예외처리 : 정상실행 되지 않았을 경우, false
			if (rs <= 0) { //rs >= 1(success) / rs = 0 (fail)
				System.err.println("log_StoreMenuDAO_insert_executeUpdate() fail : if(rs <= 0)");
				return false; 
			}
		} catch (Exception e) {
			System.err.println("log_StoreMenuDAO_insert_Exception fail : Exception e ");
			e.printStackTrace();
			return false;
		} 
		System.out.println("log : StoreMenu insert success");
		return true;
	}

	//   StoreMenuDAO Update ------------------------------------------------------------------------------------   
	//가게 메뉴 판매 정보 수정
	public boolean update(StoreMenuDTO storeMenuDTO) {
		System.out.println("log_StoreMenuDAO_update : start");
		String query = "";

		// 비정상 프로그램 종료 방지 위한 try-catch 진행
		query =UPDATE; // input값 storeDTO 이하 입력
		System.out.println("log_StoreMenuDAO_update_pstmt conn");

		//[4] 인자값으로 받은 데이터 쿼리문에 삽입
		Object[] args = {
				storeMenuDTO.getStoreMenuNormal()      // 팥/슈프림 붕어빵 판매 여부(Y/N) 입력
				,storeMenuDTO.getStoreMenuVeg()   // 야채/김치/만두 판매 여부(Y/N) 입력
				,storeMenuDTO.getStoreMenuMini()     // 미니 붕어빵 판매 여부(Y/N) 입력
				,storeMenuDTO.getStoreMenuPotato()      // 고구마 붕어빵 판매 여부(Y/N) 입력
				,storeMenuDTO.getStoreMenuIce()   // 아이스크림/초코 붕어빵 판매 여부(Y/N) 입력
				,storeMenuDTO.getStoreMenuCheese()      // 치즈 붕어빵 판매 여부(Y/N) 입력
				,storeMenuDTO.getStoreMenuPastry()      // 페스츄리 붕어빵 판매 여부(Y/N) 입력
				,storeMenuDTO.getStoreMenuOther()      // 기타 붕어빵 해당 여부(Y/N) 입력
				,storeMenuDTO.getStoreNum()         // 가게 고유번호 입력
		};

		try {
			//UPDATE 쿼리문 실행
			int rs = jdbcTemplate.update(query, args);

			if (rs <= 0) { //rs >= 1(success) / rs = 0 (fail)
				System.err.println("log_StoreMenuDAO_update_executeUpdate() fail : if(rs <= 0)");
				return false;
			}
		} catch (Exception e) {
			System.err.println("log_StoreMenuDAO_update_Exception fail : Exception e ");
			e.printStackTrace();
			return false; 
		}
		System.out.println("log_StoreMenuDAO_update_true!");
		return true;
	}

	private boolean delete(StoreMenuDTO storeMenuDTO) { // 신규등록가게 추가
		return false;
	}
	// StoreMenuDAO selectAll ------------------------------------------------------------------------------------   
	private ArrayList<StoreMenuDTO> selectAll(StoreMenuDTO storeMenuDTO) { // 메뉴 카테고리별 총 개수 조회
		return null; // 데이터 반환
	}
	//selectOne 특정가게 정보 조회---------------------------------------------------------------------------
	private StoreMenuDTO selectOne(StoreMenuDTO storeMenuDTO) {
		return null;
	}
}   
