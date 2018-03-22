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