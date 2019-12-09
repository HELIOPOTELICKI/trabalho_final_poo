/**
 * Trabalho Final - POO
 * Equipe: Hélio Potelicki & Luis Felipe Zaguini 
 */

import com.opencsv.bean.CsvBindByPosition;

public class DBRow {

	@CsvBindByPosition(position = 0)
	private String tipoMaterial;

	@CsvBindByPosition(position = 1)
	private String meioDivulgacao;

	@CsvBindByPosition(position = 2)
	private String entidade;

	@CsvBindByPosition(position = 3)
	private String tipoOrganizacao;

	@CsvBindByPosition(position = 4)
	private String autor;

	@CsvBindByPosition(position = 5)
	private String titulo;

	@CsvBindByPosition(position = 6)
	private String palavrasChaveDescritores;

	@CsvBindByPosition(position = 7)
	private String anoProducao;

	@CsvBindByPosition(position = 8)
	private String anoPublicacao;

	@CsvBindByPosition(position = 9)
	private String edicao;

	@CsvBindByPosition(position = 10)
	private String localPublicacao;

	@CsvBindByPosition(position = 11)
	private String editora;

	@CsvBindByPosition(position = 12)
	private String paginasMinutos;

	@CsvBindByPosition(position = 13)
	private String disponivelEm;

	@CsvBindByPosition(position = 14)
	private String ISBN;

	@CsvBindByPosition(position = 15)
	private String ISSN;

// Getters AND Setters
	public String getTipoMaterial() {
		return tipoMaterial;
	}

	public void setTipoMaterial(String tipoMaterial) {
		this.tipoMaterial = tipoMaterial;
	}

	public String getMeioDivulgacao() {
		return meioDivulgacao;
	}

	public void setMeioDivulgacao(String meioDivulgacao) {
		this.meioDivulgacao = meioDivulgacao;
	}

	public String getEntidade() {
		return entidade;
	}

	public void setEntidade(String entidade) {
		this.entidade = entidade;
	}

	public String getTipoOrganizacao() {
		return tipoOrganizacao;
	}

	public void setTipoOrganizacao(String tipoOrganizacao) {
		this.tipoOrganizacao = tipoOrganizacao;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getPalavrasChaveDescritores() {
		return palavrasChaveDescritores;
	}

	public void setPalavrasChaveDescritores(String palavrasChaveDescritores) {
		this.palavrasChaveDescritores = palavrasChaveDescritores;
	}

	public String getAnoProducao() {
		return anoProducao;
	}

	public void setAnoProducao(String anoProducao) {
		this.anoProducao = anoProducao;
	}

	public String getAnoPublicacao() {
		return anoPublicacao;
	}

	public void setAnoPublicacao(String anoPublicacao) {
		this.anoPublicacao = anoPublicacao;
	}

	public String getEdicao() {
		return edicao;
	}

	public void setEdicao(String edicao) {
		this.edicao = edicao;
	}

	public String getLocalPublicacao() {
		return localPublicacao;
	}

	public void setLocalPublicacao(String localPublicacao) {
		this.localPublicacao = localPublicacao;
	}

	public String getEditora() {
		return editora;
	}

	public void setEditora(String editora) {
		this.editora = editora;
	}

	public String getPaginasMinutos() {
		return paginasMinutos;
	}

	public void setPaginasMinutos(String paginasMinutos) {
		this.paginasMinutos = paginasMinutos;
	}

	public String getDisponivelEm() {
		return disponivelEm;
	}

	public void setDisponivelEm(String disponivelEm) {
		this.disponivelEm = disponivelEm;
	}

	public String getISBN() {
		return ISBN;
	}

	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}

	public String getISSN() {
		return ISSN;
	}

	public void setISSN(String iSSN) {
		ISSN = iSSN;
	}
}
