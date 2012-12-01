package modelo;

import javax.persistence.*;

@Entity
@Table(name="colaborador")
public class Colaborador extends Usuario {

	@Column(name="nr_quantidade_candidaturas")
	private int qtdCandidaturas;

	public int getQtdCandidaturas() {
		return qtdCandidaturas;
	}

	public void setQtdCandidaturas(int qtdCandidaturas) {
		this.qtdCandidaturas = qtdCandidaturas;
	}

	
}
