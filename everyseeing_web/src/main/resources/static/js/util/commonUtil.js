const comm = {
	prefix: "/api",
	
	// ajax 기본 통신
	send: function(url_v, data_v, method, successCallback, errorCallback) {
		// url
		url_v = prefix + url_v;
		
		// 데이터 암호화
		if(data_v != null) {
			let json = JSON.stringify(data_v);
			data_v = "d=" + ""
		}
	},
	
	// 암호키 생성, 세션에 저장
	getSecKey: function() {
		let cookie = document.cookie;
		secret_key = cookie.replace("secret_key=", "");
		sessionStorage.setItem("secret_key", secret_key);
	},
}