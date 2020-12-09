function sendAjaxGet(url, func) {
    var xhr = new XMLHttpRequest()
        || new ActiveXObject("Microsoft.HTTPRequest");
    xhr.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            func(this);
        }
    };
    console.log('URL : ' + url);
    xhr.open("GET", url, true);
    xhr.send();
};


function populateRes(xhr) {
    if (xhr.responseText) {
        let res = JSON.parse(xhr.responseText);
        if (res) {
            sendAjaxGet("http://localhost:8080/project-1/portal/users/" + res.user_id, populatePersonalInfo);
        }
    }
}

function populateResUpdate(xhr) {
    console.log("in populate Res UPDATE");
    if (xhr.responseText) {
        console.log("in populate Res UPDATE");
        let res = JSON.parse(xhr.responseText);
        if (res) {
            console.log("in populate Res UPDATE " + res.user_id);
            sendAjaxGet(`http://localhost:8080/project-1/portal/users/${res.user_id}`, updatePersonal);
        }
    }
}

function populatePersonalInfo(xhr) {
    if (xhr.responseText) {
        var personal = JSON.parse(xhr.responseText);
        console.log(personal);
        if (personal === undefined || personal.length === 0) {

        } else {
            let div = document.getElementById("personal-div");
            for (key in personal) {

                if (key == "userId") {
                    let text = document.createTextNode(personal[key]);
                    let p = document.getElementById("user_id");
                    p.innerHTML = "";
                    p.appendChild(text);
                } else if (key == "username") {
                    let text = document.createTextNode(personal[key]);
                    let p = document.getElementById("username");
                    p.innerHTML = "";
                    p.appendChild(text);
                } else if (key == "firstName") {
                    let text = document.createTextNode(personal[key]);
                    let p = document.getElementById("first_name");
                    p.innerHTML = "";
                    p.appendChild(text);
                } else if (key == "lastName") {
                    let text = document.createTextNode(personal[key]);
                    let p = document.getElementById("last_name");
                    p.innerHTML = "";
                    p.appendChild(text);
                } else if (key == "email") {
                    let text = document.createTextNode(personal[key]);
                    let p = document.getElementById("email");
                    p.innerHTML = "";
                    p.appendChild(text);
                } else if (key == "role") {
                    let text = document.createTextNode(personal[key]);
                    let p = document.getElementById("role");
                    p.innerHTML = "";
                    p.appendChild(text);
                }
            }
        }
    }
}

function doPopulatePersonalInfo() {
    sendAjaxGet("http://localhost:8080/project-1/session", populateRes);

}



function hideshow() {
    let b = document.getElementById('initial_button');
    b.style.display = 'none';
    let s = document.getElementById('secondary_button');
    s.style.display = 'block';


    let div = document.getElementById('username');
    let text = div.innerHTML;
    div.innerHTML = "";
    let input = document.createElement("input");
    input.value = text;
    input.id = 'username_form';
    div.appendChild(input);

    div = document.getElementById('first_name');
    text = div.innerHTML;
    div.innerHTML = "";
    input = document.createElement("input");
    input.value = text;
    input.id = 'fn_form';
    div.appendChild(input);

    div = document.getElementById('last_name');
    text = div.innerHTML;
    div.innerHTML = "";
    input = document.createElement("input");
    input.value = text;
    input.id = 'ln_form';
    div.appendChild(input);

    div = document.getElementById('email');
    text = div.innerHTML;
    div.innerHTML = "";
    input = document.createElement("input");
    input.value = text;
    input.id = 'email_form';
    div.appendChild(input);
}

$(document).ready(function () {
    $("#nav-bar").load("../nav.html");
    let b = document.getElementById('secondary_button');
    b.style.display = 'none';
    doPopulatePersonalInfo();
})


function updatePersonalInfo() {
    console.log("in UPDATE PERSONAL");
    
    let user_id = document.getElementById("user_id").innerHTML;
    let username = document.getElementById("username_form").value;
    let password = "";
    let last_name = document.getElementById("ln_form").value;
    let email = document.getElementById("email_form").value;
    let first_name = document.getElementById("fn_form").value;
    let role = document.getElementById("role").innerHTML;

    let udtoHelper = {
        userId: user_id,
        username: username,
        password: password,
        firstName: first_name,
        lastName: last_name,
        role: role,
        email: email
    }

            let xhr = new XMLHttpRequest();
            xhr.onreadystatechange = function () {
                if (this.readyState === 4 && this.status === 200) {
                    //sessionStorage.setItem('currentUser',this.responseText);
                    console.log("PUT SHOULD HAVE BEEN SUCCESSFUL")
                    //doPopulatePersonalInfo();
                    location.reload();
                }

                if (this.readyState === 4 && this.status === 204) {
                    console.log("Failed")
                    //alert("Failed to log in! Username or password is incorrect")
                    let childDiv = document.getElementById("warningText");
                    childDiv.textContent = "Failed to update Info";
                }
            }
            console.log("TRYING TO PUT");
            xhr.open("POST", "http://localhost:8080/project-1/portal/users/"+user_id, true);

            xhr.send(JSON.stringify(udtoHelper));
}
    
