const home = (function() {
	
	function init() {
		_settingGenre();
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
			if(action == "") {
			}
		} else if(type == "change") {
			if(action == "changeGenre") {
				_event.changeGenre(evo);
			}
		}
	};
	
	// 이벤트 실행
	let _event = {
		// 장르 드롭다운 변경
		changeGenre: function(e) {
			let genre_idx = e.val();
		}
	}
	
	// 장르 드롭다운 세팅
	function _settingGenre() {
		let url_v = "/content/genre/list";
		
		let data_v = {};
		
		comm.send(url_v, data_v, "POST", function(resp) {
			let list = resp.body.list;
			
			let ul_o = $("#genreList").empty();
			ul_o.append($("<option>").attr("selected", true).html("전체"));
			for(let genre of list) {
				let option_o = $("<option>").addClass("dropdown-item").attr({
					"value": genre.idx_code,
				}).html(genre.description);
				ul_o.append(option_o);
			}
		});
	}
	
	return {
		init,
	};
})();