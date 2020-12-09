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

function populateTable(xhr) {
	if(xhr.responseText) {
		var res = JSON.parse(xhr.responseText);
		console.log(res);
		if (res === undefined || res.length === 0) {

		} else {
            let table = document.getElementById("employee-table");
            table.innerHTML = "";
			let thead = table.createTHead();
			let row = thead.insertRow();
			console.log("type of res[0]" + typeof(res[0]) +  res[0]);

					
			let th = document.createElement("th");
			th.scope = "col";
			let text = document.createTextNode("User ID");
			th.appendChild(text);
			row.appendChild(th);

			th = document.createElement("th");
			th.scope = "col";
			text = document.createTextNode("Username");
			th.appendChild(text);
			row.appendChild(th);

			th = document.createElement("th");
			th.scope = "col";
			text = document.createTextNode("First Name");
			th.appendChild(text);
			row.appendChild(th);

			th = document.createElement("th");
			th.scope = "col";
			text = document.createTextNode("Last Name");
			th.appendChild(text);
			row.appendChild(th);

			th = document.createElement("th");
			th.scope = "col";
			text = document.createTextNode("Email");
			th.appendChild(text);
			row.appendChild(th);

			for (let element of res) {
				let row =  table.insertRow();
				row.scope = "row";
				for(key in element) {
                    if (key != "password" && key != "role") {
                        let cell = row.insertCell();
      				    let text = document.createTextNode(element[key]);
      				    cell.appendChild(text);
                    }
				}
				
			}

		}
	} 
}

function doPopulateTable() {
    sendAjaxGet(`http://localhost:8080/project-1/portal/users?id=0`, populateTable);
}

$(document).ready(function() {
    $("#nav-bar").load("/project-1/nav.html");
    doPopulateTable();
})