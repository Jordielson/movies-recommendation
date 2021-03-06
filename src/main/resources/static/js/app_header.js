export const APIKEY = "YOUR API KEY";


// Informacoes da API que sera usada pela parte superior das paginas
const GENREAPI = `https://api.themoviedb.org/3/genre/movie/list?api_key=${APIKEY}&language=pt-BR`;
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
        a.href = "/movies-recommendation/genre?genre_id=" + element.id;
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
        document.location.href = "/movies-recommendation/search?search_term=" + searchTerm;
    }
}

const GETUSER = "http://localhost:8080/movies/user";

getUser();
export async function getUser() {
    return await fetch(GETUSER).then((res) => res.json())
    .then(function(data){
        updateUser(data);
        return data;
    })
    .catch((error) => {
        console.log("Error: " + error);
    })
} 

function updateUser(data) {
    if (data != null) {
        let login = document.getElementById("id-get-user-login");
        login.addEventListener("click", function (e) {
            fetch("http://localhost:8080/movies-recommendation/logout", {
                method: "POST"
            })
        });
        login.innerText = "Sair"
        login.href = "/movies-recommendation/home";
    
        let regis = document.getElementById("id-get-user-register");
        regis.innerText = "Alterar meus dados";
    }
}