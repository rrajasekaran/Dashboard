$(document).ready(function() { 
	    $('.waitScript').click(function() { 
	        $.blockUI({ css: { 
	            border: 'none', 
	            padding: '15px', 
	            backgroundColor: '#000', 
	            '-webkit-border-radius': '10px', 
	            '-moz-border-radius': '10px', 
	            opacity: .5, 
	            color: '#fff' 
	        } }); 
	 
	        setTimeout($.unblockUI, 1000000000); 
	    }); 
	}); 
