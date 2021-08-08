import { APIKEY } from "./app_header.js";
import showMovies from "./show_movie.js";

const queryString = window.location.search;
const urlParams = new URLSearchParams(queryString);
const searchTerm = urlParams.get('search_term');

function getSearchApi(p, search) {
    return `https://api.themoviedb.org/3/search/movie?&api_key=${APIKEY}&query=${search}&page=${p}`;
}

const load = document.getElementById("load");
load.addEventListener("click", nextPage);

var page = 1;
nextPage();
function nextPage() {
    showMovies(getSearchApi(page, searchTerm), page++);
}