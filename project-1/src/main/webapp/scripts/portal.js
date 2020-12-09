function sendAjaxGet(url, func) {
	var xhr = new XMLHttpRequest()
			|| new ActiveXObject("Microsoft.HTTPRequest");
	xhr.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			func(this);
		}
	};
	xhr.open("GET", url, true);
	xhr.send();
};
var res;
function populateUser(xhr) {
	if (xhr.responseText) {
		res = JSON.parse(xhr.responseText);
		console.log(res);
		if (res.username) {
			document.getElementById("welcome-user").innerText = "Welcome, "
					+ res.username;
        }
        
        if (res.role === "EMPLOYEE") {
            $("#links").load("./employee-links.html");
        } else if (res.role === "MANAGER") {
            $("#links").load("./manager-links.html")
        }
	} else {
		window.location = "http://localhost:8080/project-1/login";
	}
};

function doViewReims() {
    sendAjaxGet("http://localhost:8080/project-1/portal/reims", {});
}

function doViewNewReim() {
	sendAjaxGet("http://localhost:8080/project-1/portal/reims?status=new", {});
}

function doViewEmployees() {
	sendAjaxGet("http://localhost:8080/project-1/portal/users/all", {});
}

function doViewPersonal() {
	sendAjaxGet(`http://localhost:8080/project-1/portal/users`, {});
}

function doResolveReims() {
	sendAjaxGet(`http://localhost:8080/project-1/portal/reims/resolve`, {});
}

window.onload = function() {
	sendAjaxGet("http://localhost:8080/project-1/session", populateUser);
}

$(document).ready(function() {
    $("#nav-bar").load("./nav.html");
})