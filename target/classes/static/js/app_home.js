import { APIKEY } from "./app_header.js";
import showMovies from "./show_movie.js";

// Informacoes da API.
function getUrlApi(page) {
    return `https://api.themoviedb.org/3/movie/top_rated?api_key=${APIKEY}&page=${page}&language=pt-BR`;
}

const load = document.getElementById("load");
load.addEventListener("click", nextPage);

var page = 1;
nextPage();
function nextPage() {
    showMovies(getUrlApi(page), page++);
}
