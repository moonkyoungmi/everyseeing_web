/**
 * 공통 layout 초기화
 */

const layout = (function() {
	
	let _callback = null;
	
	function init() {
		if(_callback != null && typeof(_callback) == "function") {
			comm.saveSecKey();
			_callback();
		}
	}
	
	return {
		init,
		setCallback: function(callback) {
			_callback = callback;
		}
	}
})();