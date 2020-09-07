package br.com.otto.play.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.com.otto.play.service.LivroService;
import br.com.otto.play.util.ConstantUtil;

/**
 * Servlet implementation class AudioLivroServlet
 * @author time21
 */
@WebServlet(description = "Servlet responsável por realizar a chamada de um serviço para que seja feita a sintetização do texto, de uma determinada página de um livro, em voz.", urlPatterns = {
		"/audio" })
public class AudioLivroServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AudioLivroServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		 
		synchronized(session){
			
			final String nomeLivro = request.getParameter("livro");
			final String nrPagina = request.getParameter("pg");
			System.out.println("Obtendo áudio do livro " + nomeLivro + " - página " + nrPagina);
			
			if (this.isValorValido(nomeLivro) && this.isValorValido(nrPagina)) {
				
				final String diretorioLivro = request.getSession().getServletContext().getRealPath(ConstantUtil.RESOURCES_LIVRO) + nomeLivro;
				
				final LivroService service = new LivroService(diretorioLivro);
				service.lerPagina(Integer.valueOf(nrPagina));
			}
		}
	}

	/**
	 * @param valor
	 * @return
	 */
	private boolean isValorValido(final String valor) {
		return valor != null && !valor.isEmpty();
	}

}
