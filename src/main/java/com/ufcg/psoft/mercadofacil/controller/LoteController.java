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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.ufcg.psoft.mercadofacil.dto.LoteDTO;
import com.ufcg.psoft.mercadofacil.dto.ProdutoDTO;
import com.ufcg.psoft.mercadofacil.exception.LoteNotFoundException;
import com.ufcg.psoft.mercadofacil.exception.ProductNotFoundException;
import com.ufcg.psoft.mercadofacil.model.Lote;
import com.ufcg.psoft.mercadofacil.model.Produto;
import com.ufcg.psoft.mercadofacil.service.LoteService;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class LoteController {
	
	@Autowired
	private LoteService loteService;
	
	@RequestMapping(value = "/lote/", method = RequestMethod.POST)
	public ResponseEntity<?> criarProduto(@RequestBody LoteDTO loteDTO, UriComponentsBuilder ucBuilder) {

		String loteID;
		try {
			loteID = loteService.addLote(loteDTO);
		} catch (ProductNotFoundException e) {
			return new ResponseEntity<String>("Lote não encontrado", HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<String>("Lote cadastrado com ID:" + loteID, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/lotes", method = RequestMethod.GET)
	public ResponseEntity<?> listarLotes() {
		List<Lote> lotes = loteService.listarLotes();
		
		return new ResponseEntity<List<Lote>>(lotes, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/lote/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> consultarLote(@PathVariable("id") String id) {

		Lote lote;
		try {
			lote = loteService.getLoteById(id);
		} catch (LoteNotFoundException e) {
			return new ResponseEntity<String>("Lote não encontrado", HttpStatus.NO_CONTENT);
		}
			
		return new ResponseEntity<Lote>(lote, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/lote/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> atualizaLote(@RequestBody LoteDTO loteDTO, @PathVariable("id") String id) throws LoteNotFoundException {
		
		@SuppressWarnings("unused")
		Lote lote;
		try {
			lote = loteService.getLoteById(id);
			this.loteService.editLote(loteDTO, id);
		} catch (ProductNotFoundException e) {
			return new ResponseEntity<String>("Lote não encontrado", HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<String>("Lote atualizado com sucesso!",HttpStatus.NO_CONTENT);
	}
	
	@RequestMapping(value = "/lote/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> RemoveLote(@PathVariable("id") String id) {
		
		@SuppressWarnings("unused")
		Lote lote;
		try {
			lote = loteService.getLoteById(id);
			this.loteService.delLote(id);
		} catch (LoteNotFoundException e) {
			return new ResponseEntity<String>("Lote não encontrado", HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<String>("Lote removido com sucesso!",HttpStatus.NO_CONTENT);
	}

	
	
}
