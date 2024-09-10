const header = (function() {
	
	function init() {
		_profileSetting();
		_eventInit();
	};
	
	// 이벤트 초기화 
	function _eventInit() {
		let evo = $("[data-src='header'][data-act]").off();
		evo.on("click change keyup", function(e) {
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
			} else if(action == "clickChangeProfile") {
				_event.clickChangeProfile();
			} else if(action == "clickContentSearch") {
				_event.contentSearch();
			}
		} else if(type == "change") {
			if(action == "changeFile") {
				_event.changeFile(evo);
			}
		} else if(type == "keyup") {
			if(action == "contentSearch") {
				if(e.keyCode == 13) {
					_event.contentSearch();
				}
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
			location.href = "/";
		},
		
		// 프로필 변경 클릭
		clickChangeProfile: function() {
			location.href = "/profile";
		},
		
		// 검색
		contentSearch: function() {
			let url_v = "/content/list";
			
			let data_v = {
				"search_text": $("#searchInput").val()
			}
			
			comm.send(url_v, data_v, "POST", function(resp) {
				let total = resp.body.total;
				let list = resp.body.list;
				
				let list_o = $("#contentList");
				list_o.empty();
				
				if(total <= 0) {
					let div_o = $("<div>").addClass("content-empty");
					list_o.append(div_o);
					
					let p_o	= $("<p>").html("결과가 존재하지 않습니다.");
					div_o.append(p_o);
				}
				
				for(let content_list of list) {
					let line_o = $("<div>").addClass("card-list");
					list_o.append(line_o);
					
					for(let content of content_list) {
						let card_o = $("<div>").addClass("card").attr({
							"data-src": "home",
							"data-act": "clickContent",
							"data-idx-content": content.idx_content
						});
						line_o.append(card_o);
	
						{
							let img_o = $("<img>").addClass("card-img-top").attr({
								"src": content.thumbnail
							});
							card_o.append(img_o);
						}
						{
							let div_o = $("<div>").addClass("card-body");
							card_o.append(div_o);
							
							let row_o = $("<div>").addClass("row");
							div_o.append(row_o);
							
							let col1_o = $("<div>").addClass("col-9");
							row_o.append(col1_o);
	
							let col2_o = $("<div>").addClass("col-3");
							row_o.append(col2_o);
	
							let span_o = $("<span>").html(content.title);
							col1_o.append(span_o);
							
							let src_v = "/assets/imgs/heart.png";
							if(content.bookmark_yn == "Y") {
								src_v = "/assets/imgs/heart_fill.png";
							}
							let img_o = $("<img>").attr({
								"src": src_v,
								"data-src": "home",
								"data-act": "clickBookmark",
								"data-yn": content.bookmark_yn,
							}).addClass("heart");
							col2_o.append(img_o);
						}
					}
				}
				
				// 콘텐츠 개수
				let cnt = $(".card").length;
				if(total == cnt) {
					$("#moreView").hide();
				} else {
					$("#moreView").show();
				}
				
				_eventInit();
			});
		}
	};
	
	function _profileSetting() {
		let path = window.location.pathname;
		if(path != "/login" && path != "/signUp" && path != "/profile") {
			let url_v = "/member/profile/info";
			
			let data_v = {};
			
			comm.send(url_v, data_v, "POST", function(resp) {
				let data = resp.body.data;
				if(data.profile_file == null) {
					data.profile_file = "/assets/imgs/basic_profile.png";	
				}
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