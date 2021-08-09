import {createCookie, getCookie, alterCookiesUser} from "./cookies.js";

const APIUSER = "http://localhost:8080/movies/user";

// Elementos que serao usados
const form = document.getElementById("form-register");
const btnAlter = document.getElementById("btn-alter");
const submit = document.getElementById("button");
const nameUser = document.getElementById("name");
const email = document.getElementById("email");
const oldPassword = document.getElementById("password-old");
const password = document.getElementById("password-new");
const confirmPassword = document.getElementById("password-confirm");
const divOldPassword = document.getElementById("div-old-password");
const divNewPassword = document.getElementById("div-new-password");
const divConfirmPassword = document.getElementById("div-password");

var hiddenItem = true;
var userItem = "";
initUser();

function registerUser() {
    let title = document.getElementById("h1-title");
    title.innerText = "Cadastro de Usuário";
}

function updateUser() {
    divNewPassword.classList.add("hidden");
    divConfirmPassword.classList.add("hidden");

    let senha = document.getElementById("lb-password");
    senha.innerText = "Nova Senha";

    let title = document.getElementById("h1-title");
    title.innerText = "Atualizar seus dados";
    nameUser.value = userItem.name;
    email.value = userItem.email;
}

// Adicionando eventos as validacoes
submit.addEventListener("click", function(e){
    e.preventDefault();
    clearFieldsValidation();
    validateName(nameUser);
    validateEmail(email);

    let user = {
        "id": userItem.id, //
        "name": nameUser.value,
        "email": email.value,
        "password": password.value
    };
    if(userItem != "") {
        if (hiddenItem) {
            user.password = userItem.password;
        } else {
            validateOldPassword(oldPassword);
            validatePassword(password, confirmPassword);
        }
        updateUserData(user);
    } else {
        validatePassword(password, confirmPassword);
        register(user);
    }
});

async function register(user) {
    await sendUser(user, "POST");
    window.location.reload();
}

async function updateUserData(user) {
    await sendUser(user, "PUT");
    window.location.reload();
}

function initUser(id = "") {
    let idUser = "";
    idUser = getCookie("idUser") != "" ? getCookie("idUser") : id;
    if (idUser != "") {
        getUser(idUser);
    } else {
        btnAlter.classList.add("hidden");
        createCookie();
        registerUser();
    }
}

async function getUser(id) {
    await fetch(APIUSER + "/" + id)
    .then((res) => res.json())
    .then((data) => {
        alterCookiesUser(data)
        userItem = data;
    });
    updateUser();
}

btnAlter.addEventListener("click", updatePage);
// Atualiza a tela de cadastro, para ter ou nao a alteracao da senha
function updatePage() {
    if (hiddenItem) {
        divOldPassword.classList.remove("hidden");
        divNewPassword.classList.remove("hidden");
        divConfirmPassword.classList.remove("hidden");
    } else {
        divOldPassword.classList.add("hidden");
        divNewPassword.classList.add("hidden");
        divConfirmPassword.classList.add("hidden");
    }
    hiddenItem = !hiddenItem;
}

async function sendUser(user, method) {
    await fetch(APIUSER, {
    method: method,
    body: JSON.stringify(user),
    headers: {
        "Content-type": "application/json; charset=UTF-8"
    }
    })
    .then(response => response.json())
    .then((data) => {
        alterCookiesUser(data);
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

function validateName(name) {
    if (name.value.length < 4) {
        printErroMessage(name, "Nome do usuário precisa ter pelo menos 4 caracteres");
        throw "User name invalid";
    };
}

function validateEmail(email) {
    const re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    if (!re.test(email.value)) {
        printErroMessage(email, "Email Inválido!")
        throw "Email invalid";
    }
}

function validatePassword(password, confirmPassword) {
    if (password.value != confirmPassword.value || password.value.length < 6) {
        printErroMessage(confirmPassword, "Senha Inválida! As senhas devem ser iguais e possuir pelo menos 6 caracteres");
        throw "New Password invalid";
    }
}

function validateOldPassword(password) {
    if(password.value != userItem.password) {
        printErroMessage(password, "Senha Inválida!");
        throw "Password invalid";
    }
}
