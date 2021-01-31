package it.gestionearticolijspservletjpamaven.web.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.gestionearticolijspservletjpamaven.model.Articolo;
import it.gestionearticolijspservletjpamaven.service.ArticoloService;
import it.gestionearticolijspservletjpamaven.service.MyServiceFactory;


@WebServlet("/PrepareDeleteArticoloServlet")
public class PrepareDeleteArticoloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public PrepareDeleteArticoloServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArticoloService articoloService = MyServiceFactory.getArticoloServiceInstance();
		
		Long idArticoloScelto = Long.parseLong(request.getParameter("idArticolo"));
		
		try {
			Articolo articolo = articoloService.caricaSingoloElemento(idArticoloScelto);
			request.setAttribute("articoloDaCancellare", articolo);
			request.setAttribute("errorMessage", "Sei sicuro di voler cancellare l'articolo?");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/articolo/delete.jsp");
		requestDispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
