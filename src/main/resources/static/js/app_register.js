import { getUser } from "./app_header.js";

const APIUSER = "http://localhost:8080/movies/user";

// Elementos que serao usados
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
let userlog;
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
    nameUser.value = userlog.name;
    email.value = userlog.email;
}

// Adicionando eventos as validacoes
submit.addEventListener("click", function(e){
    e.preventDefault();
    clearFieldsValidation();
    validateName(nameUser);
    validateEmail(email);

    let user = {
        "name": nameUser.value,
        "email": email.value,
        "password": password.value
    };
    if(userlog != null) {
        user["id"] = userlog.id
        if (hiddenItem) {
            user.password = userlog.password;
        } else {
            validatePassword(password, confirmPassword);
            user["oldPassword"] = oldPassword.value;
        }
        updateUserData(user);
    } else {
        validatePassword(password, confirmPassword);
        registerUserData(user);
    }
});

async function initUser() {
    try {
        userlog = await getUser();
        console.log(userlog);
        updateUser();
    } catch (error) {
        btnAlter.classList.add("hidden");
        registerUser();
    }
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

async function updateUserData(user) {
    await fetch(APIUSER, {
    method: "PUT",
    body: JSON.stringify(user),
    headers: {
        "Content-type": "application/json; charset=UTF-8"
    }
    })
    .then(response => {
        if (response.status == 200) {
            alert("Alteração realizada com sucesso!");
            window.location.reload();
        } else {
            alert("Email ou senha invalido!");
        }
    })
    .catch(function(error)  {
        console.log("Error: " + error);
    });
}

async function registerUserData(user) {
    await fetch(APIUSER, {
    method: "POST",
    body: JSON.stringify(user),
    headers: {
        "Content-type": "application/json; charset=UTF-8"
    }
    })
    .then(response => {
        if (response.status == 201) {
            alert("Cadastro realizado com sucesso!");
            window.location.reload();
        } else {
            response.json().then((data) => printErroMessage(email, data.message));
        }
    })
    .catch(function(error)  {
        console.log("Error: " + error);
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
    if(password.value != userlog.password) {
        printErroMessage(password, "Senha Inválida!");
        throw "Password invalid";
    }
}
