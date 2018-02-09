;function getXMLHttpRequest() {
    var xmlHttpReq;
    if (window.XMLHttpRequest) {
        xmlHttpReq = new XMLHttpRequest();
    } else if (window.ActiveXObject) {
        try {
            xmlHttpReq = new ActiveXObject("Msxml2.XMLHTTP");
        } catch (exp1) {
            try {
                xmlHttpReq = new ActiveXObject("Microsoft.XMLHTTP");
            } catch (exp2) {
                alert("Exception in getXMLHttpRequest()!");
            }
        }
    }
    return xmlHttpReq;
}

function isEmptyField(str) {
    if(str===undefined){
        return true;
    }
    return str.trim() === '';
}

function post(path, params, method) {
    method = method || "post";

    var form = document.createElement("form");
    form.setAttribute("method", method);
    form.setAttribute("action", path);

    for(var key in params) {
        if(params.hasOwnProperty(key)) {
            var hiddenField = document.createElement("input");
            hiddenField.setAttribute("type", "hidden");
            hiddenField.setAttribute("name", key);
            hiddenField.setAttribute("value", params[key]);

            form.appendChild(hiddenField);
        }
    }

    document.body.appendChild(form);
    form.submit();
}

function loginSubmit() {
    var login = document.getElementById('login-login').value;
    var password = document.getElementById('login-password').value;
    if(isEmptyField(login) || isEmptyField(password)){
        alert("Please fill all fields");
        return false;
    }
    post('login',{'login':login, 'password':sha256(password)});
    return false;
}

function signupSubmit() {
    var login = document.getElementById('signup-login').value;
    var password = document.getElementById('signup-password').value;
    var password2 = document.getElementById('signup-password2').value;
    if(isEmptyField(login) || isEmptyField(password)){
        alert("Please fill all fields");
        return false;
    }
    if(password !== password2){
        alert("Passwords should be the same.");
        return false;
    }
    post('signup',{'login':login, 'password':sha256(password)});
    return false;
}