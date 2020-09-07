package br.com.otto.play.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.otto.play.service.LivroService;
import br.com.otto.play.util.ConstantUtil;

/**
 * Servlet implementation class PaginaLivroServlet
 * @author time21 
 */
@WebServlet(description = "Servlet responsável por consultar o total de páginas de um determinado livro.", urlPatterns = {"/pagina" })
public class PaginaLivroServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PaginaLivroServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {

		String totalPaginas = "0";
		final String nomeLivro = request.getParameter("livro");

		if (this.isValorValido(nomeLivro)) {
			
			final String diretorioLivro = request.getSession().getServletContext().getRealPath(ConstantUtil.RESOURCES_LIVRO) + nomeLivro;
			final LivroService ls = new LivroService(diretorioLivro);

			totalPaginas = ls.getTotalPaginas() != null ? ls.getTotalPaginas().toString() : "0";
		}

		 response.getWriter().append(totalPaginas);
	}

	/**
	 * @param valor
	 * @return
	 */
	private boolean isValorValido(final String valor) {
		return valor != null && !valor.isEmpty();
	}

}
