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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((contratos == null) ? 0 : contratos.hashCode());
		result = prime * result + ((endereco == null) ? 0 : endereco.hashCode());
		result = prime * result + ((flagAtivo == null) ? 0 : flagAtivo.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((identificador == null) ? 0 : identificador.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((telefone == null) ? 0 : telefone.hashCode());
		result = prime * result + ((tipoIdentificador == null) ? 0 : tipoIdentificador.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		if (contratos == null) {
			if (other.contratos != null)
				return false;
		} else if (!contratos.equals(other.contratos))
			return false;
		if (endereco == null) {
			if (other.endereco != null)
				return false;
		} else if (!endereco.equals(other.endereco))
			return false;
		if (flagAtivo == null) {
			if (other.flagAtivo != null)
				return false;
		} else if (!flagAtivo.equals(other.flagAtivo))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (identificador == null) {
			if (other.identificador != null)
				return false;
		} else if (!identificador.equals(other.identificador))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (telefone == null) {
			if (other.telefone != null)
				return false;
		} else if (!telefone.equals(other.telefone))
			return false;
		if (tipoIdentificador == null) {
			if (other.tipoIdentificador != null)
				return false;
		} else if (!tipoIdentificador.equals(other.tipoIdentificador))
			return false;
		return true;
	}

}
