	function sem_numero(e, args) {
		if (document.all) {
			var evt = event.keyCode;
				} else {
					var evt = e.charCode;
				}
				var valid_chars = 'abcdefghijlmnopqrstuvxzwykABCDEFGHIJLMNOPQRSTUVXZWYK áéíóúâêôçãõü'
						+ args;
				var chr = String.fromCharCode(evt);
				if (valid_chars.indexOf(chr) > -1) {
					return true;
				}
				return false;
			}
