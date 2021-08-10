import {createCookie, getCookie} from "./cookies.js";

// Informacoes da API que sera usada pela parte superior das paginas
const GENREAPI = "https://api.themoviedb.org/3/genre/movie/list?api_key=04c35731a5ee918f014970082a0088b1&language=pt-BR";
// Selecao dos elementos que serao usados pelo header
const form = document.getElementById("form");
const search = document.getElementById("search");
const sideMenu = document.getElementById("side-menu");

showGenre(GENREAPI);
function showGenre(url){
    fetch(url).then(res => res.json())
    .then(function(data){
    data.genres.forEach(element => {
        const a = document.createElement("a");
        a.innerText = element.name;
        a.href = "/movies/genre?genre_id=" + element.id;
        sideMenu.appendChild(a);
        }); 
    });
}

const openSideMenu = document.getElementById("open-side-menu");
openSideMenu.addEventListener("click", openSlideMenu);
function openSlideMenu() {
    document.getElementById("side-menu").style.width = "250px";
}

const closeSideMenu = document.getElementById("close-side-menu");
closeSideMenu.addEventListener("click", closeSlideMenu);
function closeSlideMenu() {
    document.getElementById("side-menu").style.width = "0";
}

form.addEventListener("submit", searchMovies);
/**
    * 
    * @param {Event} e Evento da busca pelos filmes
    */
function searchMovies(e) {
    e.preventDefault();
    // main.innerHTML = '';
    const searchTerm = search.value;
    /* Adiciona o valor da pesquisa na url da API para fazer a busca. */
    if (searchTerm) {
        search.value = "";
        document.location.href = "/movies/search?search_term=" + searchTerm;
    }
}

let userId = getCookie("idUser");
if (userId != "") {
    let login = document.getElementById("id-get-user-login");
    login.addEventListener("click", function (e) {
        createCookie();
    });
    login.innerText = "Sair"
    login.href = "/movies/home";

    let regis = document.getElementById("id-get-user-register");
    regis.innerText = "Alterar meus dados";
}


export const APIKEY = "YOUR API KEY";