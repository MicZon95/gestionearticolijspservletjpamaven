package it.gestionearticolijspservletjpamaven.web.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.gestionearticolijspservletjpamaven.model.Articolo;
import it.gestionearticolijspservletjpamaven.service.ArticoloService;
import it.gestionearticolijspservletjpamaven.service.MyServiceFactory;


@WebServlet("/ExecuteDeleteArticoloServlet")
public class ExecuteDeleteArticoloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public ExecuteDeleteArticoloServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Long idArticolo = Long.parseLong(request.getParameter("idArticolo"));
			ArticoloService articoloSevice = MyServiceFactory.getArticoloServiceInstance();
			Articolo articolo = articoloSevice.caricaSingoloElemento(idArticolo);
			articoloSevice.rimuovi(articolo);
			request.setAttribute("listaArticoliAttribute", MyServiceFactory.getArticoloServiceInstance().listAll());
			request.setAttribute("successMessage", "Eliminazione effettuata con successo");
			request.getRequestDispatcher("/articolo/results.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "Attenzione si è verificato un errore.");
			request.getRequestDispatcher("/index.jsp").forward(request, response);
			return;
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
