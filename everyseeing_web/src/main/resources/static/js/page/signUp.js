const signUp = (function() {
	
	function init() {
		_headerBtnChange();
		_eventInit();
	};
	
	// 이벤트 초기화 
	function _eventInit() {
		let evo = $("[data-src='signUp'][data-act]").off();
		evo.on("click", function(e) {
			_eventAction(e);
		});
	};
	
	// 이벤트 분기
	function _eventAction(e) {
		let evo = $(e.currentTarget);
		
		let action = evo.attr("data-act");
		
		let type = e.type;
		
		if(type == "click") {
			if(action == "clickLogin") {
				_event.clickLogin();
			} else if(action == "clickSignUp") {
				_event.clickSignUp();
			}
		};
	};
	
	// 이벤트 실행
	let _event = {
		clickLogin: function() {
			location.href = "/";
		},
		
		clickSignUp: function() {
			let url_v = "/member/signUp";
			
			let data_v = {
				email : "cher1605@naver.com",
				password : "password1234"
			};
			
			$.ajax({
				type : "POST",
				url : url_v,
				data : data_v,
				success : function() {
					
				}
			});
		},
	};
	
	// 헤더 버튼 수정
	function _headerBtnChange() {
		let btn = $("#headerBtn");
		btn.html("로그인");
		btn.attr("data-src", "signUp");
		btn.attr("data-act", "clickLogin");
	};
	
	return {
		init,
	};
})();