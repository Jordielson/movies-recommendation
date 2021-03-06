
// Selecao dos elementos que serao usados
const IMGPATH = "https://image.tmdb.org/t/p/w1280";
const main = document.getElementById("main");  

/* showMovies faz a requisicao a API e cria um componente para cada filme, 
adicionando-os na tela home dentro da tag main. */
/**
 * @param {string} apiUrl api que sera usada para buscar as descricoes dos filmes
 */
export default function showMovies(url, page){
    fetch(url).then(res => res.json())
    .then(function(data){
        updateLoad(data.total_pages, page);
        data.results.forEach(element => {
            showMovie(element)
        }); 
    });
}

export function showMovie(element) {
    // Criando uma div para cada filme e adicionando na tag main. 
    const elemnt = document.createElement("div");
    const image = document.createElement("img");
    const text = document.createElement("h3");
    const a = document.createElement("a");
    a.href = "/movies-recommendation/movie?movie_id=" + element.id;
    if (element.title.length > 20) {
        text.className = "animeted";
    }
    text.innerHTML = element.title;
    image.src = IMGPATH + element.poster_path;
    a.appendChild(image);
    a.appendChild(text);
    elemnt.appendChild(a);
    main.appendChild(elemnt);
}

export function updateLoad(total_pages, page) {
    const load = document.getElementById("load");
    if(total_pages == page) {
        load.classList.add("hidden");
    } else if (total_pages > page) {
        load.classList.remove("hidden");
    }
}