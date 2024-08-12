const header = (function() {
	
	function init() {
		_profileSetting();
		_eventInit();
	};
	
	// 이벤트 초기화 
	function _eventInit() {
		let evo = $("[data-src='header'][data-act]").off();
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
			if(action == "clickLogo") {
				_event.clickLogo();
			} else if(action == "clickModifyProfile") {
				_event.clickModifyProfile();
			} else if(action == "clickSetting") {
				_event.clickSetting();
			} else if(action == "clickLogout") {
				_event.clickLogout();
			}
		}
	};
	
	// 이벤트 실행
	let _event = {
		clickLogo: function() {
			location.href = "/";
		},
		
		// 프로필 수정 클릭
		clickModifyProfile: function() {
			
		},
		
		// 설정 클릭
		clickSetting: function() {
			
		},
		
		// 로그아웃 클릭
		clickLogout: function() {
			let url_v = "/logout";
			
			let data_v = {};
			
			comm.send(url_v, data_v, "GET", function() {
				location.href = "/login";
			});
		},
	};
	
	function _profileSetting() {
		let path = window.location.pathname;
		if(path != "/login" && path != "/signUp" && path != "/profile") {
			let url_v = "/member/profile/info";
			
			let data_v = {};
			
			comm.send(url_v, data_v, "POST", function(resp) {
				let data = resp.body.data;
				$("#profileImg").attr("src", data.profile_file);
				$("#nickname").html(data.nickname);
			});
		}
	};
	
	return {
		init,
	};
})();