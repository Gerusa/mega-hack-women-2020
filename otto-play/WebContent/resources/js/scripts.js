var isPlay = false;
var totalPagina = 0;

var pgParaDemo = 6; // apenas para demo - alterar para 0 
var paginaAtual = pgParaDemo; 

function pauseHide() {

	isPlay = false;
	var iconPause = document.getElementById('icon-pause')
	var iconPlay = document.getElementById('icon-play')
	var tooltip = document.getElementById('tooltip')

	iconPause.className = 'play-pause-hide'
	iconPlay.className = 'play-pause-visible'
	tooltip.innerHTML = 'Ouvir o Otto lendo a página.'
		
	inicializarVariaveisPagina();
}

function inicializarVariaveisPagina(){
	var leuTodasPaginas = paginaAtual > totalPagina;

	if(leuTodasPaginas){
		console.log("Leu todas as páginas. Inicializando paginaAtual para " + pgParaDemo)
		paginaAtual = pgParaDemo;
	}
}

function playHide() {

	isPlay = true;
	var iconPause = document.getElementById('icon-pause')
	var iconPlay = document.getElementById('icon-play')
	var tooltip = document.getElementById('tooltip')

	iconPlay.className = 'play-pause-hide'
	iconPause.className = 'play-pause-visible'
	tooltip.innerHTML = 'Pausar o Otto lendo a página.'

	startPlay();
}

function startPlay() {

	// obtem o nome do livro que está sendo apresentado no momento
	var nomeLivro = $("#book").attr('name');

	// primeira requisicao
	if (totalPagina == 0) {

		//consulta o total de páginas do livro
		return new Promise(function(resolve, reject) {
			$.ajax({
				url : '/otto-play/pagina?livro=' + nomeLivro,
				type : 'GET',
				success : function(response) {
					console.log("Total de páginas: " + response);
					totalPagina = response;

					getAudio(nomeLivro);
				},
				error : function(err) {
					console.log("Erro " + err);
				}
			});
		});

	} else {
		getAudio(nomeLivro);
	}
}

//áudio de todas as páginas do livro
function getAudio(nomeLivro) {
	
	if (isPlay && totalPagina > 0 && paginaAtual <= totalPagina) {

		getAudioDaPagina(nomeLivro);

	} else {
		console.log("Áudio pausado na página " + paginaAtual + "/" + totalPagina);
	}
}

//áudio da página atual do livro
function getAudioDaPagina(nomeLivro) {
	console.log("Obtendo áudio da página " + paginaAtual + " do livro " + nomeLivro);
	
	return new Promise(function(resolve, reject) {
		$.ajax({
			url : '/otto-play/audio?livro=' + nomeLivro + "&pg=" + paginaAtual,
			type : 'GET',
			success : function(response) {
				// alterar para retornar audio
				
				
				//mantém a sincronicidade
				paginaAtual++;
				getAudio(nomeLivro);
			},
			error : function(err) {
				console.log("erro");
			}
		});
	});
}
