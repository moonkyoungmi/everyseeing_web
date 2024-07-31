const comm = {
	prefix: "/api",
	
	// ajax 기본 통신
	send: function(url_v, data_v, method, successCallback, errorCallback) {
		// url
		if(url_v != "/login" && url_v != "/logout") {
			url_v = this.prefix + url_v;
		}
		
		// 데이터 암호화
		if(data_v != null) {
			let json = JSON.stringify(data_v);
			data_v = "d=" + aes.enc256(this.getSecKey(), json);
		}
		
		$.ajax({
			type: method,
			url: url_v,
			data: data_v,
			contentType: "application/x-www-form-urlencoded; charset=UTF-8",
			success: successCallback,
			error: errorCallback
		});
	},
	
	// 암호키 생성, 세션에 저장
	saveSecKey: function() {
		let cookie = document.cookie;
		secret_key = cookie.replace("secret_key=", "");
		sessionStorage.setItem("secret_key", secret_key);
	},
	
	// 암호키 가져오기
	getSecKey: function() {
		let key = sessionStorage.getItem("secret_key");
		
		return key;
	},
}