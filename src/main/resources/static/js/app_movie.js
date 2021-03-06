import { APIKEY, getUser } from "./app_header.js";

const queryString = window.location.search;
const urlParams = new URLSearchParams(queryString);
const movieId = urlParams.get('movie_id');

const IMGPATH = "https://image.tmdb.org/t/p/w1280";
const FINDAPI = `https://api.themoviedb.org/3/movie/${movieId}?api_key=${APIKEY}&language=pt-BR`;
const RECOMMENDAPI = "http://localhost:8080/movies/rate";
const VIDEOAPI = `https://api.themoviedb.org/3/movie/${movieId}/videos?api_key=${APIKEY}&language=en-US`;

async function getVideo() {
    let key = await getKey();
    return `https://www.youtube.com/embed/${key}`;
}

async function getKey() {
    let key = "";
    await fetch(VIDEOAPI).then(res => res.json())
    .then(function (data) {
        key = data.results[0].key;
    });
    return key;

}

let user;
showMovie(FINDAPI);
async function showMovie(url) {
    user = await getUser();
    fetch(url).then(res => res.json())
    .then(async function(data){
        const element = document.getElementById("poster");
        const title = document.getElementById("title");

        document.title = data.title;
        title.innerText  = data.title;
        
        const image = document.getElementsByTagName("img");
        image[0].src = IMGPATH + data.poster_path;

        const media = document.getElementById("med");
        media.innerText += " " + data.vote_average;

        const total = document.getElementById("tot");
        total.innerText += " " + data.vote_count;

        const tit = document.getElementById("tit");
        tit.innerText = data.original_title;
        
        let d = data.release_date.split("-");
        const dat = document.getElementById("data");
        dat.innerText += d[2] +"/"+d[1]+"/"+d[0];

        const gen = document.getElementById("gen");
        data.genres.forEach(element => {
            gen.innerText += ", " + element.name;
        });
        gen.innerText = gen.innerText.substring(2);

        const dur = document.getElementById("dur");
        dur.innerText = data.runtime + " minutos"

        const idi = document.getElementById("idi");
        idi.innerText = data.original_language.toUpperCase();

        const pop = document.getElementById("pop");
        pop.innerText = data.popularity;

        const overview = document.getElementById("overview");
        overview.innerText = data.overview;

        const prod = document.getElementById("prod");
        data.production_countries.forEach(element => {
            prod.innerText += ", " + element.name;
        });
        prod.innerText = prod.innerText.substring(2);

        try {
            const trailer = document.getElementById("trailer");
            trailer.src = await getVideo();
        } catch (error) {
            console.log("Video not found");
        }
        
        const rad = document.getElementsByName("rate");
        rad.forEach(elemnt => {
            elemnt.addEventListener("click", rating);
        });
        if(user != null) {
            initRating(RECOMMENDAPI + `?movie_id=${movieId}`);
        }
    });
}

function initRating(url) {
    fetch(url).then((res) => res.json())
    .then(function(data){
            let rating = 0;
            rating = data.rating;
            if (rating > 0) {
                const aval = document.getElementById("user_aval");
                user_aval.innerText = "Sua Avalia????o: " + rating;
                let rad = document.getElementById("star"+rating);
                rad.checked = true;
            }
    })
    .catch(function(error){
        console.log(error);
    });
}

function rating() {
    if(user != null) {
        const rad = document.getElementsByName("rate");
        const aval = document.getElementById("user_aval");
        rad.forEach(elemnt => {
            if (elemnt.checked) {
                user_aval.innerText = "Sua Avalia????o: " + elemnt.value;
                let userRating = {
                    "movieId": movieId,
                    "rating": elemnt.value
                }
                postRating(userRating);  
            }
        });
    } else {
        const rad = document.getElementsByName("rate");
        rad.forEach(element =>
        element.checked = false);
        window.alert("Fa??a login para poder avaliar os filmes");    
    }
}

async function postRating(userRating) {
    await fetch(RECOMMENDAPI, {
        method: "POST",
        body: JSON.stringify(userRating),
        headers: {
            "Content-type": "application/json; charset=UTF-8"
        }
    })
}