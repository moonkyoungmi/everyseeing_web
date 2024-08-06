const profile = (function() {
	
	function init() {
		_headerBtnChange();
		_profileSetting();
		_eventInit();
	};
	
	// 이벤트 초기화 
	function _eventInit() {
		let evo = $("[data-src='profile'][data-act]").off();
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
			if(action == "clickProfile") {
				_event.clickProfile(evo);
			} else if(action == "clickProfileAddBtn") {
				_event.clickProfileAddBtn();
			}
		}
	};
	
	// 이벤트 실행
	let _event = {
		// 프로필 선택
		clickProfile: function(evo) {
			
		},
		
		// 프로필 추가 클릭
		clickProfileAddBtn: function() {
			$("#addProfileModal").modal("show");
		},
		
		// 프로필 추가 실행
		clickProfileAdd: function() {
			
		},
	};
	
	// 헤더 버튼 수정
	function _headerBtnChange() {
		let btn = $(".nav-btn");
		btn.remove();
	};
	
	// 계정에 따른 프로필 세팅
	function _profileSetting() {
		let url_v = "/member/profile/list";
		
		let data_v = {};
		
		comm.send(url_v, data_v, "POST", function(resp) {
			let list = resp.body.list;
			
			let box_o = $("#imgBox");
			for(let profile of list) {
				let img_o = $("<img>").addClass("profile-img");
				
				let file = profile.profile_file;
				if(comm.isNull(file)) {
					file = "/assets/imgs/basic_profile.png";						
				}
				img_o.attr("src", file);
				img_o.attr("data-src", "profile");
				img_o.attr("data-act", "clickProfile");
				
				box_o.append(img_o);
			}
		});
	};
	
	return {
		init,
	};
})();