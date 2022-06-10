package com.ufcg.psoft.mercadofacil.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import com.ufcg.psoft.mercadofacil.dto.ProdutoDTO;
import com.ufcg.psoft.mercadofacil.exception.ProductNotFoundException;
import com.ufcg.psoft.mercadofacil.model.Produto;
import com.ufcg.psoft.mercadofacil.service.ProdutoService;
import com.ufcg.psoft.mercadofacil.util.URL;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ProdutoController {
	
	@Autowired
	private ProdutoService produtoService;
	
	@RequestMapping(value = "/produto/", method = RequestMethod.POST)
	public ResponseEntity<?> criarProduto(@RequestBody ProdutoDTO produtoDTO, UriComponentsBuilder ucBuilder) {

		String prodID = produtoService.addProduto(produtoDTO);
		return new ResponseEntity<String>("Produto cadastrado com ID:" + prodID, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/produto/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> consultarProduto(@PathVariable("id") String id) {

		Produto produto;
		try {
			produto = produtoService.getProdutoById(id);
		} catch (ProductNotFoundException e) {
			return new ResponseEntity<String>("Produto não encontrado", HttpStatus.NO_CONTENT);
		}
			
		return new ResponseEntity<Produto>(produto, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/produtospornome", method = RequestMethod.GET)
	public ResponseEntity<?> listarProdutosPorNome(@RequestParam(value="nome", defaultValue="") String nome) {

		nome = URL.decodeParam(nome);
		
		List<Produto> produtos = produtoService.listarProdsByName(nome);
			
		return new ResponseEntity<List<Produto>>(produtos, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/produtoslotepornome", method = RequestMethod.GET)
	public ResponseEntity<?> listarProdutosComLotePorNome(@RequestParam(value="nome", defaultValue="") String nome) {

		nome = URL.decodeParam(nome);
		
		List<Produto> produtos = produtoService.listarProdsLoteByName(nome);
			
		return new ResponseEntity<List<Produto>>(produtos, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/produtos", method = RequestMethod.GET)
	public ResponseEntity<?> listarProdutos() {
		List<Produto> produtos = produtoService.listarProdutos();
		
		return new ResponseEntity<List<Produto>>(produtos, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/produtoscomlote", method = RequestMethod.GET)
	public ResponseEntity<?> listarProdutosComLote() {
		List<Produto> produtos = produtoService.listarProdsWithLote();
		
		return new ResponseEntity<List<Produto>>(produtos, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/produto/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> atualizaProduto(@RequestBody ProdutoDTO prodDTO, @PathVariable("id") String id) {
		
		@SuppressWarnings("unused")
		Produto produto;
		try {
			produto = produtoService.getProdutoById(id);
			this.produtoService.editProd(prodDTO, id);
		} catch (ProductNotFoundException e) {
			return new ResponseEntity<String>("Produto não encontrado", HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<String>("Produto atualizado com sucesso!",HttpStatus.NO_CONTENT);
	}
	
	@RequestMapping(value = "/produto/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> RemoveProduto(@PathVariable("id") String id) {
		
		@SuppressWarnings("unused")
		Produto produto;
		try {
			produto = produtoService.getProdutoById(id);
			this.produtoService.delProd(id);
		} catch (ProductNotFoundException e) {
			return new ResponseEntity<String>("Produto não encontrado", HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<String>("Produto removido com sucesso!",HttpStatus.NO_CONTENT);
	}
	
}
