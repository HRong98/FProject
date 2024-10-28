<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="path" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML>

<html>
<head>
<title>가게 상세정보</title>
<meta charset="utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, user-scalable=no" />

<link rel="stylesheet" href="${path}/resources/assets/css/main.css" />
<link rel="stylesheet"
	href="${path}/resources/assets/css/pagination.css">
<link rel="stylesheet" href="${path}/resources/assets/css/store.css">

<!-- 맵을 그리기 위한 script -->
<!-- appkey에 발급받은 APP KEY를 넣음 -->
<!-- 추가 기능 사용 시 &libraries=services 코드 추가(주소를 좌표로) -->
<script type="text/javascript"
	src="//dapi.kakao.com/v2/maps/sdk.js?appkey=c29b2d7f614a4d9b5ef9ee4c2ec83a48&libraries=services"></script>
<!-- sweetAlert을 사용하기 위한 script -->
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<!-- bootstrap icon 사용 -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.1/font/bootstrap-icons.css">

<!-- js를 쓰므로 jquery 사용 -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<!-- 외부 script 파일 사용 -->
<script src="${path}/resource/assets/js/fontAndIcon.js"></script>
<script src="${path}/resources/assets/js/store/copy.js"></script>
<script src="${path}/resources/assets/js/map/imageMap.js"></script>
<script src="${path}/resources/assets/js/store/store.js"></script>

</head>
<body class="subpage">
	<div id="page-wrapper">

			<custom:header />

		<!-- Content -->
		<div class="container">

			<br>

			<!-- 페이지 제목-->
			<div class="row text-center">
				<div class="col-12">
					<h2>가게 상세 정보</h2>
					<hr>
				</div>
			</div>

			<br>
			
			<!-- 상세 페이지 내용-->
			<div class="row">
				<div class="col-12">
					<!-- 가게 상세 -->
					<div class="row viewContainer">
						<div class="col-12 col-md-6">
							<!-- 왼쪽 부분 -->
							<div class="row">
								<div class="col-12">
									<!-- 가게 이름 -->
									<h4 class="storeName">
										${storeInfo.storeName}{가게이름}
										<!-- 가게 폐점 여부 상태에 따라 변경 -->
										<c:if test="${data.storeClosed eq N or true}"><span class="tag" id="openStore">영업중</span></c:if>
										<c:if test="${data.storeClosed eq Y and false}"><span class="tag" id="closedStore">폐업</span></c:if>
									</h4>
									<br>
									<!-- 별점 플러그인 -->
								</div>
							</div>

							<br>
							<div class="row">
								<div class="col-12">
									<!-- 영업 요일 -->
									<h5>
										<i class="far fa-calendar"></i> 영업 요일
									</h5>
									<div class="tagContainer2">
										<ul class="weekList">
											<!-- 영업일 리스트로 받아 반복문으로 출력 -->
											<c:forEach var="data" items="${datas}">
												<li class="listValue"><span class="tag"><span
														class="week">${data.storeWorkWeek}</span><span
														class="time">${data.storeWorkOpen} ~
															${data.storeWorkClose}</span></span></li>
											</c:forEach>
											<!-- 나중에 데이터가 들어오면 삭제할 부분 -->
											<li class="listValue"><span class="tag"><span
													class="week">월요일</span><span class="time">시작시간 ~
														종료시간</span></span></li>
											<!-- end -->
										</ul>
									</div>
								</div>
							</div>

							<br>
							<div class="row">
								<div class="col-12">
									<!-- 메뉴 -->
									<h5>
										<i class="bi bi-cart-fill"></i> 메뉴
									</h5>
									<div class="tagContainer">
									<c:if test="${data.storeMenuNormal eq 'Y'or true}"><span class="tag">팥/슈크림</span></c:if>
									<c:if test="${data.storeMenuVEG eq 'Y'or true}"><span class="tag">야채/김치/만두</span></c:if>
									<c:if test="${data.storeMenuMini eq 'Y'or true}"><span class="tag">미니붕어빵</span></c:if>
									<c:if test="${data.storeMenuPotato eq 'Y'or true}"><span class="tag">고구마</span></c:if>
									<c:if test="${data.storeMenuIce eq 'Y'or true}"><span class="tag">아이스크림/초코</span></c:if>
									<c:if test="${data.storeMenuCheese eq 'Y'or true}"><span class="tag">치즈</span></c:if>
									<c:if test="${data.storeMenuPastury eq 'Y'or true}"><span class="tag">패스츄리</span></c:if>
									<c:if test="${data.storeMenuOther eq 'Y'or true}"><span class="tag">기타</span></c:if>
									</div>
								</div>
							</div>

							<br>
							<div class="row">
								<div class="col-12">
									<!-- 결제 방식 -->
									<h5>
										<i class="fas fa-money-bill"></i> 결제방식
									</h5>
									<div class="tagContainer">
									<c:if test="${data.StorePaymentCashMoney eq 'Y'or true}"><span class="tag">현금결제</span></c:if>
									<c:if test="${data.StorePaymentCard eq 'Y'or true}"><span class="tag">카드결제</span></c:if>
									<c:if test="${data.StorePaymentAccount eq 'Y'or true}"><span class="tag">계좌이체</span></c:if>
									</div>
								</div>
							</div>

						</div>
						
						<br>
						<div class="col-12 col-md-6">
							<!-- 오른쪽 부분-->
							<div class="row justify-content-center">
								<div class="col-9 justify-content-end warningBox">
									<!-- 폐점 신고 버튼 -->
									<button class="closedReport" id="closedReport"> <i class="fas fa-exclamation-circle"></i>
										<span>폐점 신고</span></button>
									<!-- 관리자에게만 보이는 삭제 버튼 -->
									<c:if test="${role eq ADMIN or true}">
									<form action="deleteStored.do" method="POST">
										<!-- 가게 번호를 전송해야 함 -->
										<input type="hidden" id="storeNum" value="${data.storeNum}">
										<button type="submit" id="deleteBtn">가게 삭제</button>
									</form>
									</c:if>
								</div>
							</div>
							<div class="row map-height justify-content-center">
								<div class="col-9">
									<!-- 지도 -->
									<div class="map" id="map"></div>
								</div>
							</div>

							<div class="row  data-height justify-content-center">
								<div class="col-9 text-center">
									<!-- 주소, 전화번호 -->
									<div class="storeData">
										<div class="storeDataContent">
											<i class="fas fa-map"></i> <span id="address">서울 강남구 테헤란로43길 10
												<br> 13층
											</span>
											<button class="copy" value="${store.address} ${store.addressDetail}">복사</button>
										</div>
										<div class="storeDataContent">
											<i class="fas fa-phone"></i> <span>${store.Contact}010-0000-0000</span>
											<button class="copy" value="${store.Contact}">복사</button>
										</div>
									</div>
								</div>
							</div>

						</div>
					</div>
				</div>
			</div>
			<br>
			<br>

		</div>

			<custom:footer />

	</div>

</body>
</html>