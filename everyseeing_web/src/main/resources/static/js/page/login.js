const login = (function() {
	
	function init() {
		_headerBtnChange();
		_eventInit();
	};
	
	// 이벤트 초기화 
	function _eventInit() {
		let evo = $("[data-src='login'][data-act]").off();
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
			if(action == "clickSignUp") {
				_event.clickSignUp();
			}
		};
	};
	
	// 이벤트 실행
	let _event = {
		clickSignUp: function() {
			location.href = "signUp";
		}
	};
	
	// 헤더 버튼 수정
	function _headerBtnChange() {
		let btn = $("#headerBtn");
		btn.html("회원가입");
		btn.attr("data-src", "login");
		btn.attr("data-act", "clickSignUp");
	};
	
	return {
		init,
	};
})();