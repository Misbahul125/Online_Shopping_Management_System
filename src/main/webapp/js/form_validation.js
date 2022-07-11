function validatePassword() {
    let p1 = document.getElementById("newPassword").value;
    let p2 = document.getElementById("confirmPassword").value;
    console.log({p1: p1, p2: p2});
    if (p1.length < 8) {
        document.getElementById("message1").innerHTML = "**Password length must be of atleast 8 characters";
        document.getElementById("message2").innerHTML = "**Password length must be of atleast 8 characters";
        return false;
    }

    if (p1 !== p2) {
        document.getElementById("message1").outerHTML = "";
        document.getElementById("message2").innerHTML = "**The passwords you entered are not same";
        return false;
    } else {
        return true;
    }
}

function validateSignup() {
    let p1 = document.getElementById("password").value;
    console.log({p1: p1});
    if (p1.length < 8) {
        document.getElementById("message1").innerHTML = "**Password length must be of atleast 8 characters";
        return false;
    } else {
        return true;
    }
}