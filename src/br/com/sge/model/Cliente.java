package br.com.sge.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.topsys.database.hibernate.TSActiveRecordAb;
import br.com.topsys.util.TSUtil;

@Entity
@Table(name = "cliente")
public class Cliente extends TSActiveRecordAb<Cliente> {

	@Id
	@SequenceGenerator(name = "CLIENTE_ID_SEQ", sequenceName = "cliente_id_seq", allocationSize = 1)
	@GeneratedValue(generator = "CLIENTE_ID_SEQ", strategy = GenerationType.SEQUENCE)
	private Long id;

	private String nome;

	@ManyToOne
	@JoinColumn(name = "tipo_identificador_id")
	private TipoIdentificador tipoIdentificador;

	private String identificador;

	private String telefone;

	private String endereco;

	@Column(name = "flag_ativo")
	private Boolean flagAtivo;
	
	@OneToMany(mappedBy = "cliente", cascade=CascadeType.ALL, orphanRemoval=true, fetch = FetchType.EAGER)	
	private List<Contrato> contratos;

	public Cliente() {

	}

	public Cliente(Boolean flagAtivo) {
		this.flagAtivo = flagAtivo;
	}

	public Long getId() {
		return TSUtil.tratarLong(id);
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getFlagAtivo() {
		return flagAtivo;
	}

	public void setFlagAtivo(Boolean flagAtivo) {
		this.flagAtivo = flagAtivo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSituacao() {
		return (flagAtivo.equals(Boolean.TRUE) ? "ATIVO" : "INATIVO");
	}

	public TipoIdentificador getTipoIdentificador() {
		return tipoIdentificador;
	}

	public void setTipoIdentificador(TipoIdentificador tipoIdentificador) {
		this.tipoIdentificador = tipoIdentificador;
	}

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public List<Contrato> getContratos() {
		return contratos;
	}

	public void setContratos(List<Contrato> contratos) {
		this.contratos = contratos;
	}

}
