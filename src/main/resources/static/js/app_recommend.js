import { APIKEY } from "./app_header.js";
import showMovies, { showMovie, removeLoad } from "./show_movie.js";
import { getCookie } from "./cookies.js";

var userId = getCookie("idUser");

function getUrlApi(movieId) {
    return `https://api.themoviedb.org/3/movie/${movieId}?api_key=${APIKEY}&language=pt-BR`;
}
function getUrlApiRecommend(page) {
    return `http://localhost:8080/movies/rate/recommend/${userId}?page=${page}`;
}

var page = 0;
const load = document.getElementById("load");
load.addEventListener("click", nextPage);
function nextPage() {
    showRecommends(++page);
}

showRecommends();
function showRecommends(page = 0) {
    if(userId != "") {
        fetch(getUrlApiRecommend(page))
        .then(res => res.json())
        .then(function(data){
            removeLoad(data.totalPages-1, page);
            data.content.forEach(element => {
                const url = getUrlApi(element);
                fetch(url).then(res => res.json())
                .then(function(elemnt){
                    showMovie(elemnt);
                });
            });
        });
    } else {
        showMovies(`https://api.themoviedb.org/3/movie/upcoming?api_key=${APIKEY}&language=pt-BR&page=${page+1}`);
    }
}