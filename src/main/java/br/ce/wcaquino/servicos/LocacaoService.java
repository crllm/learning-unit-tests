package br.ce.wcaquino.servicos;

import static br.ce.wcaquino.utils.DataUtils.adicionarDias;

import java.util.Date;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.utils.DataUtils;

public class LocacaoService {
	
	public Locacao alugarFilme(Usuario usuario, Filme filme) {
		Locacao locacao = new Locacao();
		locacao.setFilme(filme);
		locacao.setUsuario(usuario);
		locacao.setDataLocacao(new Date());
		locacao.setValor(filme.getPrecoLocacao());

		//Entrega no dia seguinte
		Date dataEntrega = new Date();
		dataEntrega = adicionarDias(dataEntrega, 1);
		locacao.setDataRetorno(dataEntrega);
		
		//Salvando a locacao...	
		//TODO adicionar método para salvar
		
		return locacao;
	}
	
	public static void main(String[] args) {
		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Carol");
		Filme filme = new Filme("O Brilho eterno de uma mente sem lembraças", 3, 15.62);

		Locacao locacao = service.alugarFilme(usuario, filme);
		System.out.println(locacao.getFilme().getNome().equals("O Brilho eterno de uma mente sem lembraças"));
		System.out.println(locacao.getUsuario().getNome().equals("Carol"));
		System.out.println(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()));
		System.out.println(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)));
		System.out.println(locacao.getValor() == 15.62);

	}
}