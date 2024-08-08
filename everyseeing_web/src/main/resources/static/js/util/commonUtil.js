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
			data_v = "d=" + encodeURIComponent(aes.enc256(this.getSecKey(), json));
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
	
	// ajax 파일 통신
	sendFile: function(url_v, data_v, method, successCallback, errorCallback) {
		// url
		url_v = this.prefix + url_v;
		
		let fData = new FormData();
		if(data_v instanceof FormData) {
			let entries = data_v.entries();
			let obj = {};
			for(let pair of entries) {
				let key = pair[0];
				let value = pair[1];
				
				if(value instanceof File) {
					fData.append(key, value);
				} else {
					obj[key] = value;
				}
			}
			let json = JSON.stringify(obj);
			fData.append("d", aes.enc256(this.getSecKey(), json));
		}
		
		$.ajax({
			type: method,
			url: url_v,
			data: fData,
			contentType : false,
		    processData: false,
		    enctype: "multipart/form-data",
		    xhrFields: {
		          withCredentials: true
		    },
		    cache: false,
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
	
	// null 체크
	isNull: function(data) {
		if(data == null || data == "") {
			return true;
		} else {
			return false;
		}
	},
	
	// 폼데이터로 변경
	changeFormData: function(data) {
        let formData = new FormData();
        let keys = Object.keys(data);

        for(let key of keys) {
            let value = data[key];
			if(value instanceof FileList) {
				for(let file of value) {
					formData.append(key, file);
				}
			} else {
				formData.append(key, value);
			} 
        }

        return formData;
    },
}