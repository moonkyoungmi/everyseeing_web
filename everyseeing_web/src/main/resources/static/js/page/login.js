const login = (function() {
	
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
			} else if(action == "clickFindPw") {
				_event.clickFindPw();
			} else if(action == "clickSendPw") {
				_event.clickSendPw();
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
					location.href = "/profile";
				}
			});
		},
		
		// 비밀번호 찾기 모달
		clickFindPw: function() {
			$("#findPwEmail").val("");
			$("#findPwModal").modal("show");
		},
		
		// 비밀번호 발송
		clickSendPw: function() {
			// 이메일 유효성 체크
			let email_regex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/i;
			let email = $("#findPwEmail").val();
			
			if(email_regex.test(email)) {
				let url_v = "/member/find/pw";
				
				let data_v = {
					email: email
				}
				
				comm.send(url_v, data_v, "POST", function(resp) {
					let code = resp.body.code;
					
					if(code == 1002) {
						modal.alert({
							content: "존재하지 않는 회원입니다.",
						});
					} else {
						modal.alert({
							content: "입력하신 이메일로 임시 비밀번호를 발송하였습니다.<br>로그인 후 반드시 비밀번호 변경을 해주세요.",
							confirmCallback: function() {
								$("#findPwModal").modal("hide");
							}
						});
					}
				});
			} else {
				modal.alert({
					content: "잘못된 이메일 형식입니다.",
				});
			}
		},
	};
	
	// 헤더 버튼 수정
	function _headerBtnChange() {
		let btn = $("#headerBtn");
		btn.html("회원가입");
		btn.attr("data-src", "login");
		btn.attr("data-act", "clickSignUp");
	};
	
	return {
		init,
	};
})();