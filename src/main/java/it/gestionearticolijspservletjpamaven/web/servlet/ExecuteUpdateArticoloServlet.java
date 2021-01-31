package it.gestionearticolijspservletjpamaven.web.servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.gestionearticolijspservletjpamaven.model.Articolo;
import it.gestionearticolijspservletjpamaven.service.ArticoloService;
import it.gestionearticolijspservletjpamaven.service.MyServiceFactory;
import it.gestionearticolijspservletjpamaven.utility.ArticoloUtility;


@WebServlet("/ExecuteUpdateArticoloServlet")
public class ExecuteUpdateArticoloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public ExecuteUpdateArticoloServlet() {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String codiceInputParam = request.getParameter("codice");
		String descrizioneInputParam = request.getParameter("descrizione");
		String prezzoInputStringParam = request.getParameter("prezzo");
		String dataArrivoStringParam = request.getParameter("dataArrivo");
		Long idArticolo = Long.parseLong(request.getParameter("idArticolo"));

		Date dataArrivoParsed = ArticoloUtility.parseDateArrivoFromString(dataArrivoStringParam);
		
		try {
			ArticoloService articoloSevice = MyServiceFactory.getArticoloServiceInstance();
			Articolo articolo = articoloSevice.caricaSingoloElemento(idArticolo);
			
			if (!ArticoloUtility.validateInput(codiceInputParam, descrizioneInputParam, prezzoInputStringParam, dataArrivoStringParam)
					|| dataArrivoParsed == null) {
				request.setAttribute("errorMessage", "Attenzione sono presenti errori di validazione");
				request.setAttribute("articoloDaModificare", articolo);
				request.getRequestDispatcher("/articolo/update.jsp").forward(request, response);
				return;
			}
			articolo.setCodice(codiceInputParam);
			articolo.setDataArrivo(dataArrivoParsed);
			articolo.setDescrizione(descrizioneInputParam);
			articolo.setPrezzo(Integer.parseInt(prezzoInputStringParam));
			articoloSevice.aggiorna(articolo);
			request.setAttribute("listaArticoliAttribute", MyServiceFactory.getArticoloServiceInstance().listAll());
			request.setAttribute("successMessage", "Aggiornamento effettuato con successo");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "Attenzione si è verificato un errore.");
			request.getRequestDispatcher("/index.jsp").forward(request, response);
			return;
		}
		
		request.getRequestDispatcher("/articolo/results.jsp").forward(request, response);
	}
	
}
