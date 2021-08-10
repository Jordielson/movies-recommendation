import {alterCookiesUser} from "./cookies.js";

const APIUSER = "http://localhost:8080/movies/users";

// Elementos que serao usados
const btnAlter = document.getElementById("btn-alter");
const submit = document.getElementById("button");
const email = document.getElementById("email");
const password = document.getElementById("password");
const divPassword = document.getElementById("div-password");
var userItem = "";

submit.addEventListener("click", function (e) {
    e.preventDefault();
    let user = {
        "email": email.value
    }
    login(user);
    clearFieldsValidation();
});

async function login(user) {
    try {
        console.log(user.email);
        await getUser(user);
    } catch (error) {
        printErroMessage(email, "Email inválido!");
        throw "Email invalid";
    }
    console.log(userItem);
    if (userItem.password != password.value) {
        printErroMessage(password, "Senha inválida!");
        throw "Password invalid";
    } else {
        alterCookiesUser(userItem);
        window.location = "/movies/home";
    }
}

async function getUser(user) {
    await fetch(APIUSER + "/" + user.email)
    .then((res) => res.json())
    .then((data) => {
        userItem = data;
    }); 
}

function printErroMessage(input, msg) {
    let template = document.querySelector(".error-data").cloneNode(true);
    template.textContent = msg;
    
    let inputParant = input.parentNode;
    template.classList.remove('template');
    inputParant.appendChild(template);
}

function clearFieldsValidation() {
    let currentValidation = document.querySelectorAll("form .error-data");
    if(currentValidation.length > 0) {
        currentValidation.forEach(element => element.remove());
    }
}