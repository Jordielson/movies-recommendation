function getCookie(params) {
    let name = params + "=";
    let decodedCookie = decodeURIComponent(document.cookie);
    let ca = decodedCookie.split(';');
    for(let i = 0; i <ca.length; i++) {
        let c = ca[i];
        while (c.charAt(0) == ' ') {
        c = c.substring(1);
        }
        if (c.indexOf(name) == 0) {
        return c.substring(name.length, c.length);
        }
    }
    return ""
}

function alterCookiesUser(user) {
    setCookie("idUser", user.id);
    setCookie("nameUser", user.name);
    setCookie("emailUser", user.email);
}

function setCookie(cname, cvalue) {
    document.cookie = cname + "=" + cvalue;
}

function createCookie() {
    document.cookie = "idUser=";
    document.cookie = "nameUser=";
    document.cookie = "emailUser=";
}

export {
    createCookie,
    getCookie,
    alterCookiesUser,
    setCookie
}