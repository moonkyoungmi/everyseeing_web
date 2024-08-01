const modal = (function() {
	/**
	 * alert
	 */
	function alert(option) {
		let modal_o = $("<div>").addClass("modal fade").attr({
			"data-bs-backdrop": "static",
			"data-bs-keyboard": false,
			"tabindex": -1,
			"aria-hidden": true,
		});
		{
			let dialog_o = $("<div>").addClass("modal-dialog modal-dialog-centered");
			modal_o.append(dialog_o);
			
			let content_o = $("<div>").addClass("modal-content").css({
				"text-align": "left"
			});
			dialog_o.append(content_o);
			
			{
				let header_o = $("<div>").addClass("modal-header");
				content_o.append(header_o);
				
				let title_o = $("<h4>").addClass("modal-title");
				header_o.append(title_o);
				if(option.title != null) {
					title_o.html(option.title);
				} else {
					title_o.html("알림");
				}
				
				let close_o = $("<button>").addClass("btn-close").attr({
					"type": "button",
					"data-bs-dismiss": "modal",
					"aria-label": "Close",
				});
				header_o.append(close_o);
			}
			{
				let body_o = $("<div>").addClass("modal-body");
				content_o.append(body_o);
				
				if(option.content != null) {
					body_o.append("<p>" + option.content + "</p>");
				}
			}
			{
				let footer_o = $("<div>").addClass("modal-footer");
				content_o.append(footer_o);
				
				let confirm_o = $("<button>").css({
					"width": "100px",
				    "background-color": "#22223B",
				    "color": "#F2E9E4",
				    "border": "none",
				    "border-radius": "5px"
				}).html("확인");
				footer_o.append(confirm_o);
				
				
				if(option.confirmText != null) {
					confirm_o.html(option.confirmText);
				}			
				
				confirm_o.off("click");
				confirm_o.on("click", function(){
					$(modal_o).modal("hide");
					if(typeof(option.confirmCallback) == "function") {
						option.confirmCallback();
					}
				});
			}
		}
		
		$(document.body).append(modal_o);
		
		$(modal_o).modal("show");
		
		$(modal_o).off("hidden.bs.modal");
		$(modal_o).on("hidden.bs.modal", function(){
			$(modal_o).remove();
		});
	}
	
	return {
		alert,
	}
})();