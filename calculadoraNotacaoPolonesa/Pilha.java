import java.lang.reflect.*;

public class Pilha <X extends Comparable<X>> implements Cloneable
{
	private Object[] pilha;
	private int qtd;
	private static final int TAMANHO_INICIAL = 10;

	public 	Pilha()
	{
		this.pilha = new Object[TAMANHO_INICIAL];
		this.qtd = 0;
	}


	private void redimensionar(float taxa)
	{
		Object[] novo = new Object[Math.round(this.pilha.length * taxa)];
		for(int i = 0; i<this.qtd; i++)
		{
			novo[i] = this.pilha[i];
		}
		this.pilha = novo;
	}

	public X remover()throws Exception  //Desempilhar
	{
		if(qtd <= 0)
			throw new Exception("pilha vazia");

		X ret = this.recuperar();
		
		qtd--;
		
		this.pilha[qtd] = null; // tem q fzr isso sim pra coleta de lixo do java
		if(this.pilha.length/2 >= this.qtd && this.qtd>= Pilha.TAMANHO_INICIAL)
			this.redimensionar(0.5F);
			
		return ret;	
	}

	public void empilhar(X valor)throws Exception
	{
		if(valor == null)
			throw new Exception("valor nulo");

		if(qtd == this.pilha.length)
			this.redimensionar(2.0F);

		this.pilha[qtd] = valor;

		qtd++;
	}

	public X recuperar() throws Exception   //verificar o topo 
	{
		if(this.qtd == 0)
			throw new Exception("pilha vazia");
		//return (X)this.meuCloneX(this.pilha[this.qtd-1]);
		return (X)this.pilha[this.qtd-1];
	}

	private X meuCloneX(X x)
	{
		X ret=null;

		try
		{
			Class<?> classe = x.getClass();
			Class<?>[] tiposDosParms = null; // null pq clone nao tem parametros
			Method metodo = classe.getMethod("clone",tiposDosParms);
			Object[] parms = null; // null pq clone nao tem parametros
			ret = (X)metodo.invoke(x,parms);
		}
		catch (Exception erro)
		{} // pq sei que estou chamando clone de um objeto que é Cloneable e, portanto, nao há risco do método não existir ou de ser chamado com parametros errado

	    return ret;
	}

	public String toString()
	{
		String ret = "";
		for(int i=0; i<this.qtd-1; i++)
		{
			ret+= this.pilha[i].toString();
		}
		return ret;
	}

	public int hashCode()
	{
		int ret = 5;
		for(int i=0; i<this.qtd-1; i++)
		{
			ret *= 5 + this.pilha[i].hashCode();
		}
		ret *= 5 + ((Integer)this.qtd).hashCode();

		return ret;
	}

	public Object clone()
	{
		Pilha ret = null;
		try
		{
			ret = new Pilha(this);
		}
		catch(Exception ex)
		{}
		return ret;
	}

	public Pilha(Pilha pi)throws Exception
	{
		if(pi == null)
			throw new Exception("Pilha nula");

		this.qtd = pi.qtd;
		Object[] novo = new Object[this.qtd];
		for(int i = 0; i<this.qtd; i++)
		{
			novo[i] = pi.pilha[i];
		}
		this.pilha = novo;
	}

	public boolean equals(Object obj)
	{
		if(obj == null)
			return false;
		if(obj == this)
			return true;
		if(obj.getClass() != this.getClass())
			return false;

		Pilha pi = (Pilha)obj;
		if(pi.qtd != this.qtd)
			return false;
		for(int i=0; i< qtd-1; i++)
		{
			if(this.pilha[i] != pi.pilha[i])
				return false;
		}

		return true;
	}

	public int compareTo(Pilha pi)
	{
		if(this.qtd > pi.qtd)
			return 1;
		if(this.qtd < pi.qtd)
			return -1;
		return 0;
	}

	public int getQtd()
	{
		return this.qtd;
	}
}
