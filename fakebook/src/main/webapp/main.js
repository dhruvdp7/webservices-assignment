window.addEventListener('load', init);

function init() {
	btn1 = document.querySelector('#addbutton');
	btn1.addEventListener('click', addBtn1Click);
	btn2 = document.querySelector('#getbutton');
	btn2.addEventListener('click', addBtn2Click);
	btn3 = document.querySelector('#getallbutton');
	btn3.addEventListener('click', addBtn3Click);
	// main =document.querySelector('#main');
}

function addBtn1Click() {

	$(".comment").remove();

	var comment_container = document.createElement('div');
	var form = document.createElement("form");
	var comment_box = document.createElement('div');
	var comment_area = document.createElement('textarea');
	var email_container = document.createElement('div');
	var email = document.createElement('input');
	var submit_container = document.createElement('div');
	var submit = document.createElement('input');

	form.setAttribute('action', 'webapi/comment/addcomment');
	form.setAttribute('method', 'post');
	comment_area.setAttribute('rows', '4');
	comment_area.setAttribute('cols', '50');
	comment_area.setAttribute('name', 'comment');
	comment_area.setAttribute('placeholder', 'Add your comment');
	email.setAttribute('type', 'email');
	email.setAttribute('name', 'email');
	email.setAttribute('placeholder', 'Enter Your Email');
	submit.setAttribute('type', 'submit');
	submit.setAttribute('value', 'ok');

	form.appendChild(comment_box);
	comment_box.appendChild(comment_area);
	form.appendChild(email_container);
	email_container.appendChild(email);
	form.appendChild(submit_container);
	submit_container.appendChild(submit);
	comment_container.appendChild(form);
	main.appendChild(comment_container);
}



function addBtn2Click() {
	$(".comment").remove();
	$("#main").append(" <div class='formcontainer'>" +
			"</div>");
	// $("#main").append("<iframe src='index.html' name='myframe' height='200px'
	// width='200px'>");
	
	$(".formcontainer").append(" <div class='emailcontainer'><input type='email' name='email' id='user-email' " +
			"placeholder='Enter Email..' required></div>");
	
	$(".formcontainer").append(" <div class='buttoncontainer'><input type='submit' id='btnsubmit' " +
			"value='Get Comments'></div>");

			$("#btnsubmit").click(()=>{
				let email = $("#user-email")[0].value
				appendComments(email);
			})
}


function appendComments(email){

		// $('#form1').submit();
		   $('.buttoncontainer').hide();
		  $('.emailcontainer').hide();
		$(".heading1").remove();
		$(".heading2").remove();
		 $("#main").append(" <div class='heading2'><h2>Comments</h2></div>");
			$('#main').append("<div class='emailname'><h3>By</h3>"+"<h3>"+email+"</h3></div>");	
		$("#main").append(" <div class='commentcontainer'></div>");
	
		getComment(email).then(data => {
			console.log(data);
	
			
		
			for (let i=0;Object.keys(data).length;i++){
				
				$('.commentcontainer').append("<div class='row'></div>");
				 $('.row:eq('+i+')').append("<div class='all-comment'></div>");
				 
				 
				 		
				 $('.all-comment:eq('+i+')').append(data.comments[i].comment);
				 // $('.all-email:eq('+i+')').append("<h5>Email:</h5>"+data.comments[i].email);
				 $('.commentcontainer').append("<hr>");
			}                                                                                                                                                                                                                                                
		}).catch(err => console.log('error occured'));	
		
}



function getComment(email){
	console.log(email)
	return $.ajax({
		   url: 'http://localhost:8080/fakebook/webapi/comment/getcomment?email='+email,
		   success: function (data) {
			   return data;
		   }
	   });
}


	


function getComments(){
	 return $.ajax({
	        url: 'http://localhost:8080/fakebook/webapi/comment/getallcomments',
	        dataType: 'json',
	        success: function (data) {
	            return data;
	        }
	    });
}


function addBtn3Click() {
	$(".comment").remove();
	$(".heading1").remove();
	$(".heading2").remove();
	$("#main").append(" <div class='heading2'><h1>All Comments</h1></div>");
	$("#main").append(" <div class='commentcontainer'></div>");
getComments().then(data => {
	console.log(data);	
	for (let i=0;Object.keys(data).length;i++){
	
		$('.commentcontainer').append("<div class='row'></div>");
		 $('.row:eq('+i+')').append("<div class='all-comment'></div> " +
		 		"<div class='all-email'></div>");	
		 $('.all-comment:eq('+i+')').append("<h5>Comment:</h5>"+data.comments[i].comment);
		 $('.all-email:eq('+i+')').append("<h5>Email:</h5>"+data.comments[i].email);
		 $('.commentcontainer').append("<hr>");
	}                                                                                                                                                                                                                                                
}).catch(err => console.log('error occured'));
}





