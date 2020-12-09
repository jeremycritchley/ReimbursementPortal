$(document).ready(function() {
	$("#loginForm").load("./login-form.html");
});

function sendLogin() {
    let username = document.getElementById("username").value;
    let password = document.getElementById("password").value;

    let loginHelper = {
		username: username,
		password: password
	}
	
	// This begin AJAX workflow
	let xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		if (this.readyState === 4 && this.status === 200) {
			//sessionStorage.setItem('currentUser',this.responseText);
			window.location = "http://localhost:8080/project-1/portal";
		} 
		
		if (this.readyState === 4 && this.status === 204) {
			console.log("Failed")
            //alert("Failed to log in! Username or password is incorrect")
            let childDiv= document.getElementById("warningText");
            childDiv.textContent = "Failed to log in! Username or Password is incorrect";
		}
		
		console.log("Processing");
		
	}
	
	xhr.open("POST", "http://localhost:8080/project-1/login");
		
	xhr.send(JSON.stringify(loginHelper));
}