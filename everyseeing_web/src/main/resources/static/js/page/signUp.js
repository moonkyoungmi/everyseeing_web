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
			// 입력값 유효성 확인
			let id_v = $("#id").val();
			let pw_v = $("#pw").val();
			let pw_chk_v = $("#pwChk").val();

			let url_v = "/member/signUp";
		
			let data_v = {
				email : id_v,
				password : pw_v
			};
			/*
			$.ajax({
				type : "POST",
				url : url_v,
				data : data_v,
				success : function() {
					
				}
			});
			*/
			comm.send(url_v, data_v, "POST", function() {
				alert("회원가입이 완료되었습니다.");
			});
		},
		
		// 이메일 인증번호 발송
		clickSendAuthNum: function() {
			let id_v = $("#id").val();
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