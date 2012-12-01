package modelo;

import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name="avaliacao")
@SequenceGenerator(name="seq_id_avaliacao", sequenceName="seq_id_avaliacao")
public class Avaliacao {
	
	@Id
	@Column(name="id_avaliacao")
	@GeneratedValue(strategy=GenerationType.AUTO,generator="seq_id_avaliacao")
	private int idavaliacao;	
	
	@ManyToOne(fetch = FetchType.EAGER,cascade=CascadeType.ALL)
	private Candidatura c;
	
	@ManyToMany(fetch = FetchType.EAGER,cascade=CascadeType.ALL)
	@JoinTable(name="QuestaoProva"
			, joinColumns={@JoinColumn(name = "id_avaliacao")}
			, inverseJoinColumns={@JoinColumn(name="id_questao")})
	private Set<Questao> questoes;

	public int getIdavaliacao() {
		return idavaliacao;
	}

	public void setIdavaliacao(int idavaliacao) {
		this.idavaliacao = idavaliacao;
	}

	public Candidatura getC() {
		return c;
	}

	public void setC(Candidatura c) {
		this.c = c;
	}

	public Set<Questao> getQuestoes() {
		return questoes;
	}

	public void setQuestoes(Set<Questao> questoes) {
		this.questoes = questoes;
	}
}
