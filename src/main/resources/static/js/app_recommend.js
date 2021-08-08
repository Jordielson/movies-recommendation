import { APIKEY } from "./app_header.js";
import { showMovie } from "./show_movie.js";

function getUrlApi(movieId) {
    return `https://api.themoviedb.org/3/movie/${movieId}?api_key=${APIKEY}&language=pt-BR`;
}
function getUrlApiRecommend(userId) {
    return `http://localhost:8080/movies/rate/recommend/${userId}`;
}

showRecommends(2);
function showRecommends(userId) {
    fetch(getUrlApiRecommend(userId))
    .then(res => res.json())
    .then(function(data){
        data.forEach(element => {
            const url = getUrlApi(element);
            fetch(url).then(res => res.json())
            .then(function(elemnt){
                showMovie(elemnt);
            });
        });
    });
}