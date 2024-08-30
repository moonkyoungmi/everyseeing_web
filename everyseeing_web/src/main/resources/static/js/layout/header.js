const header = (function() {
	
	function init() {
		_profileSetting();
		_eventInit();
	};
	
	// 이벤트 초기화 
	function _eventInit() {
		let evo = $("[data-src='header'][data-act]").off();
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
			if(action == "clickLogo") {
				_event.clickLogo();
			} else if(action == "clickModifyProfile") {
				_event.clickModifyProfile();
			} else if(action == "clickProfileSave") {
				_event.clickProfileSave();				
			} else if(action == "clickModifyImg") {
				_event.clickModifyImg();
			} else if(action == "clickSetting") {
				_event.clickSetting();
			} else if(action == "clickLogout") {
				_event.clickLogout();
			} else if(action == "clickMenu") {
				_event.clickMenu(evo);
			}
		} else if(type == "change") {
			if(action == "changeFile") {
				_event.changeFile(evo);
			}
		}
	};
	
	// 이벤트 실행
	let _event = {
		clickLogo: function() {
			sessionStorage.removeItem("menu");
			location.href = "/";
		},
		
		// 프로필 수정 클릭
		clickModifyProfile: function() {
			_profileSetting();
			$("#fileInput").val("");
			$("#modifyProfileModal").modal("show");
		},
		
		// 프로필 수정 저장
		clickProfileSave: function() {
			let url_v = "/member/profile/modify";
			
			let data_v = {
				nickname: $("#modifyNickname").val(),
				profile_file: $("#fileInput")[0].files[0]
			};
			
			let f_data = comm.changeFormData(data_v);
			
			comm.sendFile(url_v, f_data, "POST", function(resp) {
				let code = resp.body.code;
				if(code == 1002) {
					modal.alert({
						content: "존재하지 않는 회원입니다."
					});
					return;
				}
				
				$("#modifyProfileModal").modal("hide");
				_profileSetting();
			});
		},
		
		// 설정 클릭
		clickSetting: function() {
			location.href = "/setting";
		},
		
		// 로그아웃 클릭
		clickLogout: function() {
			let url_v = "/logout";
			
			let data_v = {};
			
			comm.send(url_v, data_v, "GET", function() {
				sessionStorage.clear();
				location.href = "/login";
			});
		},
		
		// 프로필 수정 - 프로필 이미지 변경
		clickModifyImg: function() {
			$("#fileInput").trigger("click");
		},
		
		// 프로필 사진 변경 미리보기
		changeFile: function(evo) {
			let file = $(evo)[0].files[0];
			let reader = new FileReader();
			
		    reader.onload = function(e) {
		    	$("#imgPreview").attr("src", e.target.result);
		    };
		    reader.readAsDataURL(file);
		},
		
		// 메뉴 클릭
		clickMenu: function(evo) {
			let menu = evo.attr("data-menu");
			sessionStorage.setItem("menu", menu);
			location.reload();
		}
	};
	
	function _profileSetting() {
		let path = window.location.pathname;
		if(path != "/login" && path != "/signUp" && path != "/profile") {
			let url_v = "/member/profile/info";
			
			let data_v = {};
			
			comm.send(url_v, data_v, "POST", function(resp) {
				let data = resp.body.data;
				$("#profileImg").attr("src", data.profile_file);
				$("#imgPreview").attr("src", data.profile_file); // 프로필 수정 미리보기 사진
				$("#modifyNickname").val(data.nickname);
				$("#nickname").html(data.nickname);
			});
		}
	};
	
	return {
		init,
	};
})();