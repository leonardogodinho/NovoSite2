package modelo;

import javax.persistence.*;

@Table
@Entity(name="itens")
public class Itens {

	@Id
	@GeneratedValue
	private int id;
	
	@Column(name="nm_requisito")
	private String nm_requisito;
	
	@Column(name="nr_quantidade")
	private int nr_quantidade;

	public int getNr_quantidade() {
		return nr_quantidade;
	}

	public void setNr_quantidade(int nr_quantidade) {
		this.nr_quantidade = nr_quantidade;
	}

	public String getNm_requisito() {
		return nm_requisito;
	}

	public void setNm_requisito(String nm_requisito) {
		this.nm_requisito = nm_requisito;
	}

}
