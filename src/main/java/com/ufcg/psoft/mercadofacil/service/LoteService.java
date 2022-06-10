package com.ufcg.psoft.mercadofacil.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.mercadofacil.dto.LoteDTO;
import com.ufcg.psoft.mercadofacil.exception.LoteNotFoundException;
import com.ufcg.psoft.mercadofacil.exception.ProductNotFoundException;
import com.ufcg.psoft.mercadofacil.model.Lote;
import com.ufcg.psoft.mercadofacil.model.Produto;
import com.ufcg.psoft.mercadofacil.repository.LoteRepository;
import com.ufcg.psoft.mercadofacil.repository.ProdutoRepository;

@Service
public class LoteService {

	@Autowired
	private LoteRepository loteRep;
	
	@Autowired
	private ProdutoRepository produtoRep;
	
	public String addLote(LoteDTO loteDTO) throws ProductNotFoundException {
		Produto prod = this.produtoRep.getProd(loteDTO.getIdProduto());
		
		if(prod == null) throw new ProductNotFoundException("Produto: " + loteDTO.getIdProduto() + " não encontrado");
		Lote lote = new Lote(prod, loteDTO.getQuantidade());
		this.loteRep.addLote(lote);

		return lote.getId();
	}
	
	public Lote getLoteById(String id) throws LoteNotFoundException {
		
		Lote lote = this.loteRep.getLote(id);
		if(lote == null) throw new LoteNotFoundException("Lote: " + id + " não encontrado");
		
		return (lote);
	}

	public void editLote(LoteDTO loteDTO, String id) throws ProductNotFoundException, LoteNotFoundException {
		
		Produto prod = this.produtoRep.getProd(loteDTO.getIdProduto());
		Lote lote = this.loteRep.getLote(id);
		if(lote == null) throw new LoteNotFoundException("Lote: " + id + " não encontrado");
		
		if(prod == null) throw new ProductNotFoundException("Produto: " + loteDTO.getIdProduto() + " não encontrado");
		lote = new Lote(id, prod, loteDTO.getQuantidade());
		this.loteRep.editLote(lote, id);		
	}

	public void delLote(String id) {
		this.loteRep.delLote(id);
		
	}

	public List<Lote> listarLotes() {
		return new ArrayList<Lote>(loteRep.getAll());
	}
}
