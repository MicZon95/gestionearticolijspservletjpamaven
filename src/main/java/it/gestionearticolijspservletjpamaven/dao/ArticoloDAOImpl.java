package it.gestionearticolijspservletjpamaven.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import it.gestionearticolijspservletjpamaven.model.Articolo;

public class ArticoloDAOImpl implements ArticoloDAO {

	private EntityManager entityManager;

	@Override
	public List<Articolo> list() throws Exception {
		return entityManager.createQuery("from Articolo", Articolo.class).getResultList();
	}

	@Override
	public Articolo findOne(Long id) throws Exception {
		return entityManager.find(Articolo.class, id);
	}

	@Override
	public void update(Articolo input) throws Exception {
		 if (input == null) {
	         throw new Exception("Problema valore in input");
	     }
	     input = entityManager.merge(input);
	}

	@Override
	public void insert(Articolo input) throws Exception {
		if (input == null) {
			throw new Exception("Problema valore in input");
		}
		entityManager.persist(input);
	}

	@Override
	public void delete(Articolo input) throws Exception {
		if (input == null) {
            throw new Exception("Problema valore in input");
        }
        entityManager.remove(entityManager.merge(input));
	}

	@Override
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public List<Articolo> findByExample(Articolo input) throws Exception {
		if (input == null) {
            throw new Exception("Problema valore in input");
        }
		
		TypedQuery<Articolo> queryDaFare = null;
		
		String query = "select a FROM Articolo a where 1=1 ";
		if (input.getCodice() != null && !input.getCodice().equals("")) {
			query += " and a.codice='" + input.getCodice() + "' ";
		}
		if (input.getDescrizione() != null && !input.getDescrizione().equals("")) {
			query += " and a.descrizione='" + input.getDescrizione() + "' ";
		}

		if (input.getPrezzo() != null && !input.getPrezzo().equals("")) {
			query += " and a.prezzo='" + input.getPrezzo() + "' ";
			queryDaFare.setParameter("prezzo", input.getPrezzo());
		}

		if (input.getDataArrivo() != null && !input.getDataArrivo().equals("")) {
			query += " and a.dataarrivo='" + input.getDataArrivo() + "' ";
			queryDaFare.setParameter("dataarrivo", input.getDataArrivo());
		}
		queryDaFare = entityManager.createQuery(query + "group by a",Articolo.class);
        return queryDaFare.getResultList();
	}

}
