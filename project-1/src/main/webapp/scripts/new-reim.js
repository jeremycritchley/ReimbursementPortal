

function doSubmitReim() {
    let amount = document.getElementById("amount").value;
    let type = document.querySelector('input[name="type"]:checked').value;
    let description = document.getElementById("description").value;

    let ReimDTO = {
        reimId: undefined,
        amount: amount,
        submittedTime: undefined,
        resolvedTime: "N/A",
        description: description,
        author: undefined,
        resolver: "N/A",
        status: "PENDING",
        type: type
    }

    let xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		if (this.readyState === 4 && this.status === 200) {
			//sessionStorage.setItem('currentUser',this.responseText);
            //window.location = "http://localhost:8080/project-1/portal";
            let childDiv= document.getElementById("warningText");
            childDiv.innerHTML = "Reimbursement Successfully submitted";
            childDiv.style.backgroundColor = "#ccffe6";
		} 
		
		if (this.readyState === 4 && this.status === 204) {
			console.log("Failed")
            //alert("Failed to log in! Username or password is incorrect")
            let childDiv= document.getElementById("warningText");
            childDiv.innerHTML = "Failed to Submit Reimbursement";
            childDiv.style.backgroundColor = "#ff8787";
		}
		
		console.log("Processing");
    }

    xhr.open("POST", "http://localhost:8080/project-1/portal/reims");
		
	xhr.send(JSON.stringify(ReimDTO));
}

$(document).ready(function() {
    $("#nav-bar").load("../nav.html");
})