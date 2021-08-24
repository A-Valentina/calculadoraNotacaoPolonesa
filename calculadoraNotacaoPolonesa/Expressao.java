class Expressao { 

	public static double resolveExpressao(char[] operadores, Fila numeros) 

	{
		//Fila numeros = extraiNumeros(expOriginal); --- Não precisei do metodo de extrair
		String expEmLetra = transformaLetras (operadores, numeros.qtosElementos());
		String expPolonesa = montaPolonesa(expEmLetra);
		return resolvePolonesa(numeros, expPolonesa);

	}

	private static String transformaLetras (char[] operadores, int qtd)
	{  
		char   letra = 'a';
		String retorno = "";

		for(int i=0; i < qtd - 1; i++) // Percorre o vetor de operadores para formar a expressão em letras, deve ser menor que o numero de operadores, para não cair em erro
		{
			String  op = operadores[i] + ""; // converter em string  
			retorno += letra + op; 
			letra++;
		} 

		retorno += letra; // porque existe uma letra a mais do que operador 

		return retorno;

	}



	private static String montaPolonesa (String expEmLetra) 
	{
		Pilha<Character>auxiliar = new Pilha<Character>();  // Crianção de pilha seguindo a logica da pilha generica, como quero uma pilha de char deve-se colocar o char ali, caso fosse de int, somente trocar char por int

		String ret = "";  
		char atual = ' ';
		char novo = ' ';
		String aux = "";
		char operadorFinal = ' '; 

			for (int i = 0; i < expEmLetra.length(); i++)
			{ 
				//Essa pilha precisa obrigatoriamente de um try catch para chamar os metodos
				try{			   
					atual  = auxiliar.recuperar();
					//System.out.println(atual);
				} catch(Exception ex){}

				novo = expEmLetra.charAt(i);

				if ("+-*=/^".indexOf(novo) < 0) // é uma letra 
				{
					ret += novo;
					ret += aux;
					aux = ""; // deixar o aux vazio depois de adiciona-lo no ret 
					continue;

				}

				else
				{


					if(comparar(novo, atual) == 1) // 1, se o retorno disso for 1, então o atual pode sair da pilha e ir esperar a proxima letra para dar o retorno
					{
						//System.out.println("ATUAL QUE NAO SAI E CONTINUA NO TOPO 1 " + atual);
						//System.out.println("NOVO Q CHEGOU E VAI DIRETO PRA FILA 1 " + novo); 
						aux += novo; 
					}

					if(comparar(novo, atual) < 0) // -1, se o retorno disso for -1, então o atual pode sair da pilha e ir para o retorno
					{
						//System.out.println("ATUAL ANTES DE SAIR  -1 " + atual);
						//System.out.println("NOVO Q CHEGOU  -1 " + novo);

						try {
							auxiliar.remover();  		
						} catch (Exception ex) {}		

						//System.out.println("ATUAL DPS Q SAIU  -1" + atual);
						//System.out.println("NOVO q quer ir pro topo da PILHA  -1" + novo);

						ret += atual;

						try{
							atual = auxiliar.recuperar();  //O atual agora vai ser o que tava na 
						} catch (Exception ex) {}		

						if (comparar(novo,atual) == -1)
						{
							do {
								try{
									//atual = auxiliar.recuperar();
									//System.out.println("ATUAL q ta no topo da pilha -1" + atual);
									auxiliar.remover(); 
									//System.out.println("ATUAL q ta no topo da pilha -1" + atual);

									ret += atual;

								} catch (Exception ex) {}		

							} while((auxiliar.getQtd() !=0) && comparar(novo,atual) == -1); 
							// Atualizar o atual e comparar dnv se da < 0  
						}

						try {
							auxiliar.empilhar(novo);
						//System.out.println("empilhou no cond -1: " + novo);
							continue; 

						} catch (Exception ex) {}	
					}


				if(comparar(novo, atual) == 0) // 0, se o retorno disso for 0, então o atual não pode sair da pilha e nem ir para o retorno
				{
					//System.out.println("ATUAL QUE NAO SAI 0 " + atual);
					//System.out.println("NOVO Q CHEGOU 0 " + novo);
					try{
						auxiliar.empilhar(novo);
						//System.out.println("empilhou: no cond 0" + novo);
					} catch (Exception ex){}
					continue;
					//System.out.println("ATUAL Q NAO SAI E AINDA TA NA PILHA 0 " + atual);
					//System.out.println("NOVO NO TOPO DA PILHA 0 " + novo);	
				}
			}    	
	}

// Um loop para pegar o que restou na pilha

		try{
			do {
				try{
					operadorFinal = auxiliar.recuperar();
					auxiliar.remover(); 
				} catch(Exception ex){}
					
				ret += operadorFinal;

			} while(auxiliar.getQtd() != 0);

		} catch(Exception ex){} 

		return ret;

	}



	private static int comparar(char novo, char atual)  // Metodo para comparar 
	{
		// Novo = Novo digito que saira da expressão e estara entrando na pilha
		// Atual = Digito que ja esta no topo da pilha e sera comparado com o que estara entrando, o "novo"
		// As classes de preferencia: +- < */ < ^ 

        // If que o atual podera sair da pilha e ir para o retorno
		if("+-".indexOf(novo)> -1 && "*/+-".indexOf(atual) > -1)  //se no novo for + ou - e o atual for */+- o atual tem preferencia ent ele retorna -1, que vai ter significado no outro metodo --> o atual sai da pilha e vai para a string de retorno e o novo fica na pilha
			return -1;

		if("*/".indexOf(novo)>-1 && "*/".indexOf(atual)>-1)   
			return -1;	

		// sempre que for ^ vai direto pra pilha pq ele tem preferencia sobre todos
		if("^".indexOf(novo)>-1 && "*/+-^".indexOf(atual)>-1)   
			return 1;
			

		// If que o atual não podera sair da pilha e ir para o retorno

		if("+-".indexOf(atual)>-1 && "*/".indexOf(novo)>-1)  //se o numero que ta na pilha tiver menos prioridade que o que ta chegando, o novo vai ficar no topo da pilha e o atual, que tava antes, vai continuar la, mas vai sair do topo 
			return 0;	

		return 0;  
	}




	private static double resolvePolonesa(Fila numeros, String expPolonesa) 
	{
		Pilha<Double>auxiliar = new Pilha<Double>();  // Crianção de pilha seguindo a logica da pilha generica, como quero uma pilha de char deve-se colocar o char ali, caso fosse de int, somente trocar char por int
		double ret = 0;
		char digito = ' '; 

		for (int i = 0;  i < expPolonesa.length(); i++) 
		{
			digito = expPolonesa.charAt(i);
			//System.out.println("DIGITO DA VEZ: " + digito);

			if ("+-*=/^".indexOf(digito) < 0) //é uma letra então vai direto pra pilha, mas oq vai pra pilha é o numero então adiciona o numero da fila
			{
				try{
				auxiliar.empilhar(Double.valueOf(numeros.desenfileirar()));

				//System.out.println("TOPO DA PILHA: " + auxiliar.recuperar());

				} catch (Exception ex){}
			}
			else // Onde calcula, digito = operador
			{
				try{
					double numero1 = Double.valueOf(auxiliar.remover());
					double numero2 = Double.valueOf(auxiliar.remover());

					//System.out.println("N1 e op: " + numero1 + " " + digito);
					//System.out.println("N2 e op: " + numero2 + " " + digito);

					char operador = digito;

					double resultado = calcula(numero1, numero2, operador);  // O resultado do calculado do metodo 
					//System.out.println("Resultado antes de empilhar: " + resultado);

					auxiliar.empilhar(resultado);

					//System.out.println("Resultado depois de empilhar: " + resultado);
				//try{
				//System.out.println("TOPO DA PILHA q devia ser o resultado: " + auxiliar.recuperar());
				//} catch (Exception ex){}





				} catch (Exception ex){}



			}
		}

		try{
		ret = auxiliar.recuperar();	
		} catch (Exception ex){}
		//System.out.println("Rett que devia ser o Resultado: " + ret);

		return ret;
	} 

	private static double calcula(double n1, double n2, char op)
	{

		double resultado = 0;

		switch(op)
		{
			case '+':
			resultado = n2+n1;
			break;

			case '-':
			resultado = n2-n1;
			break;


			case '*':
			resultado = n2*n1;
			break;


			case '/':
			resultado = n2/n1;
			break;

			case '^':
			resultado = Math.pow(n2,n1); 
			break;


		}	

		return resultado;

	}
}
