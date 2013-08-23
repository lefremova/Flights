function LoginPasswordIsEmpty (form) {
    var login = form.uname.value;
    var password = form.upass.value;
    if (login == '' || password == ''){
    	alert("It is necessary to fill all fields");
        return false
    } else {
        return true
    }
}