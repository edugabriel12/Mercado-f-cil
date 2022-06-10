package com.ufcg.psoft.mercadofacil.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.mercadofacil.dto.ProdutoDTO;
import com.ufcg.psoft.mercadofacil.exception.ProductNotFoundException;
import com.ufcg.psoft.mercadofacil.model.Lote;
import com.ufcg.psoft.mercadofacil.model.Produto;
import com.ufcg.psoft.mercadofacil.repository.LoteRepository;
import com.ufcg.psoft.mercadofacil.repository.ProdutoRepository;

@Service
public class ProdutoService {
	
	@Autowired
	private LoteRepository loteRep;
	
	@Autowired
	private ProdutoRepository prodRep;

	public List<Produto> listarProdutos() {
		return new ArrayList<Produto>(prodRep.getAll());
	}
	
	public List<Produto> listarProdsWithLote() {
		return new ArrayList<Produto>(getProdsWithLote());
	}
	
	public List<Produto> listarProdsLoteByName(String nome) {
		List<Produto> prods = getProdsWithLote();
		return getProdsByName(nome, prods);
	}

	public List<Produto> listarProdsByName(String nome) {
		return getProdsByName(nome, this.prodRep.getAll());
	}

	private List<Produto> getProdsByName(String nome, Collection<Produto> prods) {
		List<Produto> prodsResult = new ArrayList<Produto>();
		for (Produto produto : prods) {
			if(produto.getNome().toLowerCase().contains(nome.toLowerCase())) {
				prodsResult.add(produto);
			}
		}	
		return(prodsResult);
	}
	
	private List<Produto> getProdsWithLote() {
		List<Produto> prods = new ArrayList<Produto>();
		for (Lote lote : this.loteRep.getAll()) {
			prods.add(lote.getProduto());
		}
		return(prods);
	}

	public String addProduto(ProdutoDTO prodDTO) {
		Produto produto = new Produto(prodDTO.getNome(), prodDTO.getFabricante(), prodDTO.getPreco());
		
		this.prodRep.addProduto(produto);
		
		return produto.getId();
	}
	
	public Produto getProdutoById(String id) throws ProductNotFoundException {
		Produto prod = this.prodRep.getProd(id);
		if(prod == null) throw new ProductNotFoundException("Produto: " + id + " não encontrado");
		
		return(prod);
	}
	
	public void editProd(ProdutoDTO prodDTO, String id) throws ProductNotFoundException {
		Produto prod = this.prodRep.getProd(id);
		if(prod == null) throw new ProductNotFoundException("Produto: " + id + " não encontrado");
		Produto produto = new Produto(id, prodDTO.getNome(), prodDTO.getFabricante(), prodDTO.getPreco());
		this.prodRep.editProd(produto, id);
	}
	
	public void delProd(String id) {
		this.prodRep.delProd(id);
	}
}
