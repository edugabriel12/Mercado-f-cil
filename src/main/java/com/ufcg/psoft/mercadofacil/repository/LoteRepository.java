package com.ufcg.psoft.mercadofacil.repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ufcg.psoft.mercadofacil.model.Lote;

@Repository
public class LoteRepository {
	
	private Map<String, Lote> lotes = new HashMap<String, Lote>();
	
	public Collection<Lote> getAll() {
		return this.lotes.values();
	}
	
	public Lote getLote(String id) {
		return this.lotes.get(id);
	}
	
	public void delLote(String id) {
		this.lotes.remove(id);
	}
	
	public void editLote(Lote lote, String id) {
		this.lotes.replace(id, lote);
	}
	
	public String addLote(Lote lote) {
		this.lotes.put(lote.getId(), lote);
		return lote.getId();
	}

}
