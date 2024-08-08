const profile = (function() {
	
	function init() {
		_headerBtnChange();
		_profileSetting();
		_eventInit();
	};
	
	// 이벤트 초기화 
	function _eventInit() {
		let evo = $("[data-src='profile'][data-act]").off();
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
			if(action == "clickProfile") {
				_event.clickProfile(evo);
			} else if(action == "clickProfileAddBtn") {
				_event.clickProfileAddBtn();
			} else if(action == "clickProfileAdd") {
				_event.clickProfileAdd();
			} else if(action == "clickModifyImg") {
				_event.clickModifyImg();
			}
		} else if(type == "change") {
			if(action == "changeFile") {
				_event.changeFile(evo);
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
			$("#nickname").val("");
			$("#imgPreview").attr("src", "/assets/imgs/basic_profile.png");
			$("#fileInput").val("");
			$("#addProfileModal").modal("show");
		},
		
		// 프로필 추가 실행
		clickProfileAdd: function() {
			let file = $("#fileInput")[0].files[0];
			if(comm.isNull(file)) {
				file = "/assets/imgs/basic_profile.png";
			}
			
			let url_v = "/member/profile/add";
			
			let data_v = {
				nickname: $("#nickname").val(),
				profile_file: file
			};
			
			let f_data = comm.changeFormData(data_v);
			
			comm.sendFile(url_v, f_data, "POST", function(resp) {
				let code = resp.body.code;
				if(code == 1002) {
					modal.alert({
						content: "존재하지 않는 회원입니다."
					});
					return;
				} else if(code == 1004) {
					modal.alert({
						content: "더이상 프로필을 생성할 수 없습니다."
					});
					return;
				}
				
				$("#addProfileModal").modal("hide");
				_profileSetting();
			});
		},
		
		// 프로필 추가 - 프로필 이미지 변경
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
		}
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