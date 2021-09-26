import { APIKEY } from "./app_header.js";
import showMovies from "./show_movie.js";

const queryString = window.location.search;
const urlParams = new URLSearchParams(queryString);
const genreId = urlParams.get('genre_id');

function getUrlDiscoverApi(p){
    return `https://api.themoviedb.org/3/discover/movie?api_key=${APIKEY}&with_genres=${genreId}&page=${p}`;
}

const load = document.getElementById("load");
load.addEventListener("click", nextPage);

var page = 1;
nextPage();
function nextPage() {
    showMovies(getUrlDiscoverApi(page), page++);
}