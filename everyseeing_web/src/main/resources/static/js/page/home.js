const home = (function() {
	
	function init() {
		_settingGenre();
		_settingContent(0);
		_eventInit();
	};
	
	// 이벤트 초기화 
	function _eventInit() {
		let evo = $("[data-src='home'][data-act]").off();
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
			if(action == "clickMoreView") {
				_event.clickMoreView();
			} else if(action == "clickBookmark") {
				_event.clickBookmark(evo);
			}
		} else if(type == "change") {
			if(action == "changeGenre") {
				_event.changeGenre();
			}
		}
	};
	
	// 이벤트 실행
	let _event = {
		// 장르 드롭다운 변경
		changeGenre: function() {
			_settingContent(0);
		},
		
		// 더보기 클릭
		clickMoreView: function() {
			let cnt = $(".card").length;
			_settingContent(cnt);
		},
		
		// 찜 클릭
		clickBookmark: function(e) {
			let url_v = "/content/bookmark";
			
			let bookmark_yn = e.attr("data-yn");
			
			let data_v = {
				"idx_content": e.closest(".card").attr("data-idx-content"),
				"bookmark_yn": bookmark_yn
			}
			
			comm.send(url_v, data_v, "POST", function() {
				if(bookmark_yn == "Y") {
					e.attr("src", "/assets/imgs/heart.png");
				} else {
					e.attr("src", "/assets/imgs/heart_fill.png");
				}
			});
		}
	}
	
	// 장르 드롭다운 세팅
	function _settingGenre() {
		let url_v = "/content/genre/list";
		
		let data_v = {};
		
		comm.send(url_v, data_v, "POST", function(resp) {
			let list = resp.body.list;
			
			let ul_o = $("#genreList").empty();
			ul_o.append($("<option>").attr({
				"selected": true,
				"value": "all"
			}).html("전체"));
			for(let genre of list) {
				let option_o = $("<option>").addClass("dropdown-item").attr({
					"value": genre.code,
				}).html(genre.description);
				ul_o.append(option_o);
			}
		});
	}
	
	// 콘텐츠 세팅
	function _settingContent(offset) {
		let menu = sessionStorage.getItem("menu");
		
		if(menu == "B") {
			$("#genreList").hide();
		} else {
			$("#genreList").show();
		}
		
		let url_v = "/content/list";
		
		let data_v = {
			genre: $("#genreList option:selected").val(),
			menu: menu,
			limit: 15,
			offset: offset
		}

		comm.send(url_v, data_v, "POST", function(resp) {
			let list = resp.body.list;
			let total = resp.body.total;

			let list_o = $("#contentList");
			if(offset == 0) {
				list_o.empty();
			}
		
			for(let content_list of list) {
				let line_o = $("<div>").addClass("card-list");
				list_o.append(line_o);
				
				for(let content of content_list) {
					let card_o = $("<div>").addClass("card").attr({
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
	
	return {
		init,
	};
})();