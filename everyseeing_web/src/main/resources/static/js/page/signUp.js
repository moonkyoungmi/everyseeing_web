const signUp = (function() {
	
	function init() {
		_headerBtnChange();
		_eventInit();
	};
	
	// 이벤트 초기화 
	function _eventInit() {
		let evo = $("[data-src='signUp'][data-act]").off();
		evo.on("click blur", function(e) {
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
			} else if(action == "clickSendAuthNum") {
				_event.clickSendAuthNum();
			} else if(action == "clickCheckAuthNum") {
				_event.clickCheckAuthNum();
			}
		} else if(type == "blur") {
			if(action == "changePw") {
				_event.changePw();
			} else if(action == "changePwChk") {
				_event.changePwChk();
			}
		};
	};
	
	// 이벤트 실행
	let _event = {
		clickLogin: function() {
			location.href = "/";
		},
		
		// 회원가입
		clickSignUp: function() {
			// 입력값 유효성 확인
			let email_v = $("#email").val();
			let auth_v = $("#authNum").val();
			let pw_v = $("#pw").val();
			let pw_chk_v = $("#pwChk").val();
			
			// null
			if(comm.isNull(email_v)|| comm.isNull(auth_v) || comm.isNull(pw_v) || comm.isNull(pw_chk_v)) {
				modal.alert({
					content: "입력하지 않은 값이 있습니다."
				});
				return;
			}

			// 인증번호
			let auth_yn = $("#authNum").attr("chk-auth");
			if(auth_yn != "1") {
				modal.alert({
					content: "이메일 인증을 해주세요."
				});
				return;
			}

			// 비밀번호 확인
			if(pw_v != pw_chk_v) {
				modal.alert({
					content: "비밀번호가 다릅니다."
				});
				return;
			}
			
			let pw_yn = $("#pwNotice").attr("chk-pw");
			let pw_chk_yn = $("#pwChkNotice").attr("chk-pw");
			if(pw_yn != "1" && pw_chk_yn != "1") {
				modal.alert({
					content: "올바르지 않은 비밀번호입니다. 다시 확인해 주세요."
				});
				return;
			}
	
			let url_v = "/member/signUp";
		
			let data_v = {
				email : email_v,
				auth_num: auth_v,
				password : pw_v,
			};

			comm.send(url_v, data_v, "POST", function(resp) {
				let code = resp.body.code;
				if(code == "1000") {
					modal.alert({
						content: "이미 사용 중인 이메일입니다.",
					});
				} else if(code == "1001") {
					modal.alert({
						content: "이메일 인증을 해주세요.",
					});
				} else {
					modal.alert({
						content: "회원가입이 완료되었습니다.",
						confirmCallback: function() {
							location.href="/";
						}
					});
				}
			});
		},
		
		// 이메일 인증번호 발송
		clickSendAuthNum: function() {
			let email_regex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/i;
			let email_v = $("#email").val();
			
			if(email_regex.test(email_v)) {
				let url_v = "/member/send/mail";
				
				let data_v = {
					"email": email_v
				}
				
				comm.send(url_v, data_v, "POST", function(resp) {
					if(resp.body.code == "1000") {
						modal.alert({
							content: "이미 사용 중인 이메일입니다.",
						});
						return;
					} else {
						modal.alert({
							content: "이메일이 발송되었습니다.",
						});
					}
				});
			} else {
				modal.alert({
					content: "올바른 이메일을 입력해 주세요."
				});
				return;
			}
		},
		
		// 인증번호 확인
		clickCheckAuthNum: function() {
			let auth = $("#authNum").val();

			let url_v = "/member/check/auth";
			
			let data_v = {
				"email": $("#email").val(),
				"auth_num": auth
			}
			
			comm.send(url_v, data_v, "POST", function(resp) {
				let content = "";

				if(resp.header.code == 200) {
					if(resp.body.code == 1001) {
						content = "인증번호가 올바르지 않습니다. 다시 확인해 주세요.";
						$("#authNum").attr("chk-auth", "0");
					} else {
						let saved_auth = resp.body.data.auth_number;
						if(saved_auth == auth) {
							content = "확인되었습니다.";
							$("#authNum").attr("chk-auth", "1");
						}
					}
				} else {
					content = "일시적인 오류가 발생했습니다.";
				}

				modal.alert({
					content: content,
				});
				
			});
		},
		
		// 비밀번호 입력
		changePw: function() {
			let pw_regex = /^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*?_]).{8,16}$/;
			let pw_v = $("#pw").val();
			
			if(pw_regex.test(pw_v)) {
				$("#pwNotice").css({
					"display": "none"
				}).attr("chk-pw", "1");
			} else {
				$("#pwNotice").css({
					"display": "block"
				}).attr("chk-pw", "0");
			}
		},
		
		// 비밀번호 확인 입력
		changePwChk: function() {
			if($("#pw").val() == $("#pwChk").val()) {
				$("#pwChkNotice").css({
					"display": "none"
				}).attr("chk-pw", "1");
			} else {
				$("#pwChkNotice").css({
					"display": "block"
				}).attr("chk-pw", "0");
			}
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