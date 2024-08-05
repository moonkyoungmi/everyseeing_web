const profile = (function() {
	
	function init() {
		_headerBtnChange();
		_eventInit();
	};
	
	// 이벤트 초기화 
	function _eventInit() {
		let evo = $("[data-src='login'][data-act]").off();
		evo.on("click keyup", function(e) {
			_eventAction(e);
		});
	};
	
	// 이벤트 분기
	function _eventAction(e) {
		let evo = $(e.currentTarget);
		
		let action = evo.attr("data-act");
		
		let type = e.type;
		
		if(type == "click") {
			if(action == "clickSignUp") {
				_event.clickSignUp();
			} else if(action == "clickLogin") {
				_event.clickLogin();
			}
		} else if(type == "keyup") {
			if(action == "loginId" || action == "loginPw") {
				if(e.keyCode == 13) {
					_event.clickLogin();
				}
			}
		}
	};
	
	// 이벤트 실행
	let _event = {
		clickSignUp: function() {
			location.href = "signUp";
		},
		
		clickLogin: function() {
			let email_v = $("#email").val();
			let password_v = $("#pw").val();
			
			if(comm.isNull(email_v) || comm.isNull(password_v)) {
				modal.alert({
					content: "입력되지 않은 값이 있습니다."
				});
				return;
			}
			
			let url_v = "/login";
			
			let data_v = {
				email: email_v,
				password: password_v
			}
			
			comm.send(url_v, data_v, "POST", function(resp) {
				let code = resp.body.code;
				
				if(code == 1002 || code == 1003) {
					modal.alert({
						content: "아이디 혹은 비밀번호 오류입니다."
					});
				} else {
					location.href = "/";
				}
			});
		},
	};
	
	// 헤더 버튼 수정
	function _headerBtnChange() {
		let btn = $(".nav-btn");
		btn.remove();
	};
	
	return {
		init,
	};
})();