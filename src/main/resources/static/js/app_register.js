// Elementos que serao usados
const form = document.getElementById("form-register");
const submit = document.getElementById("button");

// Adicionando eventos as validacoes
submit.addEventListener("click", function(e) {
    e.preventDefault();
    let name = document.getElementById("name");
    let email = document.getElementById("email");
    let senha = document.getElementById("password");

});

// Adiciona o evento do click no botao de atualizar/cadastrar
const alter = document.getElementById("btn-alter");
alter.addEventListener("click", update);
let hiddenItem = true;
// Atualiza a tela de cadastro, se sera para cadastrar ou atualizar os dados
function update() {
    let divPassword = document.getElementById("div-old-password");
    let senha = document.getElementById("lb-password");
    hiddenItem ? divPassword.classList.remove("hidden") : divPassword.classList.add("hidden");
    hiddenItem ? senha.innerText = "Nova Senha" : senha.innerText = "Senha";
    hiddenItem = !hiddenItem;
}