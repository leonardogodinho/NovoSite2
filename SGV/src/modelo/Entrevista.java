package modelo;

import java.util.Date;

import javax.persistence.*;

@Table
@Entity(name="entrevista")
@SequenceGenerator(name="seq_id_entrevista", sequenceName="seq_id_entrevista")
public class Entrevista {
	
	@Id
	@Column(name="id_entrevista")
	@GeneratedValue(strategy=GenerationType.AUTO,generator="seq_id_entrevista")
	private int idEntrevista;
	
	@Column(name="dt_entrevista")
	private Date data;
	
	@ManyToOne(fetch = FetchType.EAGER,cascade=CascadeType.ALL)
	private Candidatura can;
	
	@ManyToOne(fetch = FetchType.EAGER,cascade=CascadeType.ALL)
	private Usuario gerente;

	public int getIdEntrevista() {
		return idEntrevista;
	}

	public void setIdEntrevista(int idEntrevista) {
		this.idEntrevista = idEntrevista;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Candidatura getCan() {
		return can;
	}

	public void setCan(Candidatura can) {
		this.can = can;
	}

	public Usuario getGerente() {
		return gerente;
	}

	public void setGerente(Usuario gerente) {
		this.gerente = gerente;
	}
}
