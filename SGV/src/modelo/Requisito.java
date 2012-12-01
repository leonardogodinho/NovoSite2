package modelo;

import javax.persistence.*;

@Entity
@Table(name = "requisito")
public class Requisito {
	
	@Id
	@Column(name="id_requisito")
	private int idRequisito;

	@Column(name="nm_requisito")
	private String nome;

	public int getIdRequisito() {
		return idRequisito;
	}

	public void setIdRequisito(int idRequisito) {
		this.idRequisito = idRequisito;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public boolean equals(Object o)
	{
		if(o!=null)
		{
			Requisito r = (Requisito)o;
			if(r.getIdRequisito() == this.getIdRequisito())
				return true;
		}
		return false;
	}
}
