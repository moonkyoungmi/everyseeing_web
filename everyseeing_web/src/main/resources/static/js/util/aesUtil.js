const aes = {
	// 암호화 함수
	encodeAES256: function(key, data) {
		let iv = CryptoJS.enc.Hex.parse("0000000000000000");

	    const cipher = CryptoJS.AES.encrypt(data, CryptoJS.enc.Base64.parse(key), {
	        iv: iv,
	        padding: CryptoJS.pad.Pkcs7,
	        mode: CryptoJS.mode.CBC
	    });
	    
	    return cipher.toString();
	},
	
	// 암호화 실행
	enc256: function(key, data) {
		key = this.getSecKey();
		
		return this.encodeAES256(key, data);
	},
	
	// 복호화 함수
	decodeAES256: function(key, data) {
		const cipher = CryptoJS.AES.decrypt(data, CryptoJS.enc.Utf8.parse(key), {
			iv: CryptoJS.enc.Utf8.parse(""),
		    padding: CryptoJS.pad.Pkcs7,
		    mode: CryptoJS.mode.CBC
		});
		    return cipher.toString(CryptoJS.enc.Utf8);
	},
	
	// 복호화 실행
	dec256: function(key, data) {
		key = this.getSecKey();
		
		return this.decodeAES256(key, data);
	},
	
	// 키 가져오기
	getSecKey: function() {
		let secret_key = sessionStorage.getItem("secret_key");
		
		return secret_key;
	}
}