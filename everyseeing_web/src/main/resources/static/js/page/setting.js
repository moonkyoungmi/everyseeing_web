const setting = (function() {
	
	function init() {
		_settinginfo();
		_eventInit();
	};
	
	// 이벤트 초기화 
	function _eventInit() {
		let evo = $("[data-src='setting'][data-act]").off();
		evo.on("click change", function(e) {
			_eventAction(e);
		});
	};
	
	// 이벤트 분기
	function _eventAction(e) {
		let evo = $(e.currentTarget);
		
		let action = evo.attr("data-act");
		
		let type = e.type;
		
		if(type == "click") {
			if(action == "clickChangePw") {
				_event.clickChangePw();
			} else if(action == "clickLeave") {
				_event.clickLeave();
			}
		} else if(type == "change") {
			if(action == "changeNewPw") {
				_event.changeNewPw();
			} else if(action == "checkNewPw") {
				_event.checkNewPw();
			}
		}
	};
	
	// 이벤트 실행
	let _event = {
		// 비밀번호 변경 클릭
		clickChangePw: function() {
			let pre_pw_v = $("#checkPw").val();
			let new_pw_v = $("#newPw").val();
			let check_pw_v = $("#checkNewPw").val();
			
			// null
			if(comm.isNull(pre_pw_v)|| comm.isNull(new_pw_v) || comm.isNull(check_pw_v)) {
				modal.alert({
					content: "입력하지 않은 값이 있습니다."
				});
				return;
			}
			
			// 새 비밀번호 확인
			if(new_pw_v != check_pw_v) {
				modal.alert({
					content: "비밀번호가 다릅니다."
				});
				return;
			}
			
			let pw_yn = $("#pwNotice").attr("chk-pw");
			let pw_chk_yn = $("#pwChkNotice").attr("chk-pw");
			if(pw_yn != "1" || pw_chk_yn != "1") {
				modal.alert({
					content: "올바르지 않은 비밀번호입니다. 다시 확인해 주세요."
				});
				return;
			}
			
			let url_v = "/member/modify/pw";
			
			let data_v = {
				password: pre_pw_v,
				new_password: new_pw_v
			}
			
			comm.send(url_v, data_v, "POST", function(resp) {
				let code = resp.body.code;
				
				if(code == 1005) {
					modal.alert({
						content: "현재 비밀번호가 일치하지 않습니다.",
					});
				} else {
					modal.alert({
						content: "비밀번호가 변경되었습니다.",
						confirmCallback: function() {
							location.reload();
						}
					});
				}
			});
		},
		
		// 회원 탈퇴 클릭
		clickLeave: function() {
			modal.confirm({
				content: "회원탈퇴 시 프로필을 포함한 모든 계정 정보가 삭제됩니다.<br>정말 탈퇴하시겠습니까?",
				confirmCallback: function() {
					let url_v = "/member/leave";
					
					let data_v = {};
					
					comm.send(url_v, data_v, "POST", function() {
						modal.alert({
							content: "회원 탈퇴가 완료되었습니다.<br>이용해 주셔서 감사합니다.",
							confirmCallback: function() {
								sessionStorage.clear();
								location.href = "/login";
							}
						})
					}); 
				}
			});
		},
		
		// 새 비밀번호 
		changeNewPw: function() {
			let pw_regex = /^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*?_]).{8,16}$/;
			let pw_v = $("#newPw").val();
			
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
		
		// 새 비밀번호 확인
		checkNewPw: function() {
			if($("#checkNewPw").val() == $("#newPw").val()) {
				$("#pwChkNotice").css({
					"display": "none"
				}).attr("chk-pw", "1");
			} else {
				$("#pwChkNotice").css({
					"display": "block"
				}).attr("chk-pw", "0");
			}
		},
	}
	
	// 이메일 설정
	function _settinginfo() {
		let url_v = "/member/info";
		
		let data_v = {};
		
		comm.send(url_v, data_v, "POST", function(resp) {
			$("#emailInfo").html(resp.body.data.email);
		});
	}

	return {
		init,
	};
})();