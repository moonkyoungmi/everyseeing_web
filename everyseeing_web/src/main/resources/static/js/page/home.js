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
					let card_o = $("<div>").addClass("card");
					line_o.append(card_o);

					{
						let img_o = $("<img>").addClass("card-img-top").attr({
							"src": content.thumbnail
						});
						card_o.append(img_o);
					}
					{
						let div_o = $("<div>").addClass("card-body");
						let span_o = $("<span>").html(content.title);
						div_o.append(span_o);
						card_o.append(div_o);
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
		});
	}
	
	return {
		init,
	};
})();