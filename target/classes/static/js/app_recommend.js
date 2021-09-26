import { APIKEY } from "./app_header.js";
import { showMovie, removeLoad } from "./show_movie.js";
import { getCookie } from "./cookies.js";

var userId = getCookie("idUser");
const APIRECOMMEND = "http://localhost:8080/movies/rate/recommend";

function getUrlApi(movieId) {
    return `https://api.themoviedb.org/3/movie/${movieId}?api_key=${APIKEY}&language=pt-BR`;
}
function getUrlApiRecommend(page) {
    return `${APIRECOMMEND}/${userId}?page=${page}`;
}

var page = 0;
const load = document.getElementById("load");
load.addEventListener("click", nextPage);
function nextPage() {
    showRecommends(++page);
}

showRecommends();
async function showRecommends(page = 0) {
    try {
        await getRecommends(getUrlApiRecommend(page));
    } catch (error) {
        getRecommends(APIRECOMMEND + `?page=${page}`);                
    }
}

async function getRecommends(url) {
    await fetch(url)
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
}