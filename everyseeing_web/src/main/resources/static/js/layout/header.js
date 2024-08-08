const header = (function() {
	
	function init() {
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
			}
		}
	};
	
	// 이벤트 실행
	let _event = {
		clickLogo: function() {
			location.href = "/";
		},
		
	};
	
	return {
		init,
	};
})();