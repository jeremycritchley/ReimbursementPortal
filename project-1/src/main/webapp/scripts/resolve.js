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

function sendAjaxPost(url, func, ReimDTO, id, status) {
    console.log("in ajax post");
    console.log(id);
    console.log(ReimDTO)
	var xhr = new XMLHttpRequest()
			|| new ActiveXObject("Microsoft.HTTPRequest");
	xhr.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			func(this, status, id);
        } else if (this.readyState == 4) {

        }

	};
	xhr.open("POST", url, true);
	xhr.send(JSON.stringify(ReimDTO));
};



function populateTable(xhr) {
	if(xhr.responseText) {
		var res = JSON.parse(xhr.responseText);
		console.log(res);
		if (res === undefined || res.length === 0) {
            document.getElementById("empty-array").innerHTML = "No Pending Requests At This Time";
            document.getElementById("reim-table").innerHTML = "";
		} else {
            document.getElementById("empty-array").innerHTML = "";
            let table = document.getElementById("reim-table");
            table.innerHTML = "";
			let thead = table.createTHead();
			let row = thead.insertRow();
			console.log("type of res[0]" + typeof(res[0]) +  res[0]);
			
			let th = document.createElement("th");
			th.scope = "col";
			let text = document.createTextNode("#");
			th.appendChild(text);
			row.appendChild(th);

			th = document.createElement("th");
			th.scope = "col";
			text = document.createTextNode("Amount");
			th.appendChild(text);
			row.appendChild(th);

			th = document.createElement("th");
			th.scope = "col";
			text = document.createTextNode("Submitted Time");
			th.appendChild(text);
			row.appendChild(th);

			th = document.createElement("th");
			th.scope = "col";
			text = document.createTextNode("Resolved Time");
			th.appendChild(text);
			row.appendChild(th);

			th = document.createElement("th");
			th.scope = "col";
			text = document.createTextNode("Description");
			th.appendChild(text);
			row.appendChild(th);

			th = document.createElement("th");
			th.scope = "col";
			text = document.createTextNode("Author");
			th.appendChild(text);
			row.appendChild(th);

			th = document.createElement("th");
			th.scope = "col";
			text = document.createTextNode("Resolver");
			th.appendChild(text);
			row.appendChild(th);

			th = document.createElement("th");
			th.scope = "col";
			text = document.createTextNode("Status");
			th.appendChild(text);
			row.appendChild(th);

			th = document.createElement("th");
			th.scope = "col";
			text = document.createTextNode("Type");
			th.appendChild(text);
			row.appendChild(th);
            let app = document.createElement("th");
            let apptext = document.createTextNode("APPROVE");
			app.appendChild(apptext);
            row.appendChild(app);

            let deny = document.createElement("th");
            let denytext = document.createTextNode("DENY");
			deny.appendChild(denytext);
            row.appendChild(deny);
            


            let i = 0;
			for (let element of res) {
                let row =  table.insertRow();
                row.id = i;
				for(key in element) {
                    let cell = document.createElement("td");
                    row.appendChild(cell);
                    let text = document.createTextNode(element[key]);
                    cell.appendChild(text);
                     
                }
                let cell1 = row.insertCell();
                let appButton = document.createElement("button");
                //appButton.style = "background-color: #31c6e8;";
                appButton.style = "background-color: #ccffe6; width: 100%; height: 100%";
                appButton.innerHTML = "APPROVE"
                appButton.name = i;
                appButton.class = "btn btn-primary";
                appButton.id = "approve";
                appButton.addEventListener("click", function() {
                    let elems = document.getElementById(`${this.name}`);
                    updateReim(elems, "approve");
                    console.log(`${this.name}`);
                    console.log(this.name);
                })
                cell1.appendChild(appButton);
                

                let cell2 = row.insertCell();
                let denyButton = document.createElement("button");
                denyButton.style = "background-color: #ffb3b3; width: 100%; height: 100%";
                denyButton.innerHTML = "DENY"
                deny
                denyButton.name = i;
                denyButton.class = "btn btn-primary";
                denyButton.id = "deny";
                denyButton.addEventListener("click", function() {
                    let elems = document.getElementById(`${this.name}`);
                    updateReim(elems, "deny");
                    console.log(`${this.name}`);
                })
                cell2.appendChild(denyButton);

				i++;
			}

		}
	} 
}

function updateReim(elems, status) {
    console.log(elems);
    for (let i = 0; i < 9; i++) {
        console.log(elems.childNodes[i].innerHTML);
    }

    let ReimDTO = {
        reimId: elems.childNodes[0].innerHTML,
        amount: elems.childNodes[1].innerHTML,
        submittedTime: elems.childNodes[2].innerHTML,
        resolvedTime: "N/A",
        description: elems.childNodes[4].innerHTML,
        author: undefined,
        resolver: "N/A",
        status: "PENDING",
        type: elems.childNodes[8].innerHTML
    }

    elems.childNodes[9].style.display = 'none';
    elems.childNodes[10].style.display = 'none';

    sendAjaxPost(`http://localhost:8080/project-1/portal/reims/${status}`, editTable, ReimDTO, elems.id, status);
}

function editTable(xhr, status, id) {
    let row = document.getElementById(id);
    if (status === "deny") {
        row.style.backgroundColor = "#ffb3b3";
    } else if (status === "approve") {
        row.style.backgroundColor = "#ccffe6";
    }
}

function doPopulateTable() {
    sendAjaxGet(`http://localhost:8080/project-1/portal/reims?status=pending`, populateTable);
}

$(document).ready(function() {
    $("#nav-bar").load("/project-1/nav.html");
    doPopulateTable();
})