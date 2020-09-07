package br.com.otto.play.service;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

/**
 * Classe de serviço responsável por extrair o texto de uma determinada página
 * de um livro, para passá-lo como parâmetro ao serviço que irá realizar a
 * comunicação, via rest, com a api 'text-to-speech' da microsoft, para que este
 * texto seja sintetizado.
 *
 * @author time21
 *
 */
public class LivroService {

	private String diretorioLivro;
	private PDDocument livro;
	private Integer totalPaginasLivro;

	public LivroService(final String diretorioLivro) {
		this.diretorioLivro = diretorioLivro;

		try {

			this.inicializarPDDocument();

			if (this.livro != null) {

				this.totalPaginasLivro = this.livro.getNumberOfPages();
			}

		} catch (final IOException e) {
			System.err.println("Erro ao inicializar LivroService para o livro " + diretorioLivro);
			System.err.println(e);
		}
	}

	/**
	 * Método responsável por inicializar o atributo livro (PDDocument).
	 * 
	 * @throws IOException
	 */
	private void inicializarPDDocument() throws IOException {

		if (this.diretorioLivro == null || this.diretorioLivro.isEmpty()) {
			System.out.println("Informe o diretório do livro.");

		} else {

			final File file = new File(this.diretorioLivro);

			if (file.isFile()) {
				this.livro = PDDocument.load(file);
			}
		}

	}

	/**
	 * Método responsável por 'ler' a pagina informada. Será feita a extração do
	 * texto desta página e, em seguida, a sintetização do mesmo em voz.
	 *
	 * @param pagina
	 *            Número da página de um livro
	 */
	public void lerPagina(final int pagina) {

		try {

			if (this.livro != null && this.totalPaginasLivro > 0) {

				final String[] linhas = this.getTextoPagina(this.livro, pagina);
				
				//sintetização do texto
				TTSService.textToSpeech(linhas);

				this.livro.close();
			}

		} catch (final Exception e) {
			System.err.println("Erro durante leitura da página " + pagina + " do livro: " + this.diretorioLivro);
			System.err.println(e);
		}
	}

	/**
	 * Método responsável por extrair o texto de uma determinada página de um
	 * livro.
	 *
	 * @param livro
	 *            Livro/ pdf
	 * @param pagina
	 *            Número da página do doc
	 * @throws IOException
	 */
	private String[] getTextoPagina(final PDDocument livro, final int pagina) throws IOException {

		final PDFTextStripper stripper = new PDFTextStripper();
		stripper.setLineSeparator("\n");
		stripper.setStartPage(pagina);
		stripper.setEndPage(pagina);

		final String textoPagina = stripper.getText(livro);

		return textoPagina.split("\\r?\\n");
	}

	public String getDiretorioLivro() {
		return this.diretorioLivro;
	}

	public void setDiretorioLivro(final String diretorioLivro) {
		this.diretorioLivro = diretorioLivro;
	}

	public PDDocument getLivro() {
		return this.livro;
	}

	public void setLivro(final PDDocument livro) {
		this.livro = livro;
	}

	public Integer getTotalPaginas() {
		return this.totalPaginasLivro;
	}

	public void setTotalPaginas(final Integer totalPaginas) {
		this.totalPaginasLivro = totalPaginas;
	}

}
