package modelo;

import javax.persistence.*;

@Table
@Entity(name="relColaborador")
public class RelColaborador {
	
	@Id
	@GeneratedValue
	private int id;
	
	@Column(name="nr_cpf")
	private String nr_cpf;
	
	@Column(name="nm_usuario")
	private String nm_usuario;
	
	@Column(name="nm_departamento")
	private String nm_departamento;
	
	@Column(name="nm_oportunidade")
	private String nm_oportunidade;
	
	@Column(name="nm_status")
	private String nm_status;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNr_cpf() {
		return nr_cpf;
	}

	public void setNr_cpf(String nr_cpf) {
		this.nr_cpf = nr_cpf;
	}

	public String getNm_usuario() {
		return nm_usuario;
	}

	public void setNm_usuario(String nm_usuario) {
		this.nm_usuario = nm_usuario;
	}

	public String getNm_departamento() {
		return nm_departamento;
	}

	public void setNm_departamento(String nm_departamento) {
		this.nm_departamento = nm_departamento;
	}

	public String getNm_oportunidade() {
		return nm_oportunidade;
	}

	public void setNm_oportunidade(String nm_oportunidade) {
		this.nm_oportunidade = nm_oportunidade;
	}

	public String getNm_status() {
		return nm_status;
	}

	public void setNm_status(String nm_status) {
		this.nm_status = nm_status;
	}
}
