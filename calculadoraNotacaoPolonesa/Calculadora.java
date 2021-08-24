// Criado por Andréia Valentina Figueiredo Valentim - Aluna COTUCA 2021

import java.util.Scanner;
import java.io.*;
import java.util.*;
import java.net.*;


class Calculadora{
	public static void main(String args[]) throws Exception{

		try{
			String conta = "";
			int numero = -1; // Numero começa em -1 porque eh uma calculadora de numero inteiros, ou seja, positivos 
			Fila filaNumerica = new Fila();
			char[] Operadores = new char[20];
			char digito;
				
			do
			{
				do {
					System.out.println("Digite o numero da expressao: ");

					try 
					{
						numero = Teclado.getUmInt();
					} catch (Exception e)
					  {
						System.out.println("Numero Invalido!");
					  }


				} while (numero < 0);

				filaNumerica.enfileirar(numero); // coloca na fila quando o numero for valido 

				do 
				{

					System.out.println("Digite um operador, suas opcoes sao +, -, *, /, ^, e = para resultado: ");                                      
					digito = Teclado.getUmChar(); 

					if ("+-*^=/".indexOf(digito) < 0)
					{
						System.out.println("Digite um operador valido!");                   
					}

				} while ("+-*^=/".indexOf(digito) < 0);

				if (digito != '=')
					Operadores[filaNumerica.qtosElementos() - 1] = digito; // se pegou um numero, obrigatoriamente tera que pegar um operador, então utiliza essa logica para colocar o digito no local certo do vetor CHar 
					numero = -1; // resetar a variavel

				// 	System.out.println(conta); -- ver se tava pegando a expressão

			} while (digito != '=');

			System.out.print("Resultado: ");
			System.out.print(Expressao.resolveExpressao(Operadores, filaNumerica) + "");

			//TESTE
			//String teste = "";
			//System.out.println(Expressao.transformaLetras(Operadores, filaNumerica.qtosElementos()));
			//teste =  Expressao.transformaLetras(Operadores, filaNumerica.qtosElementos());
			//String polonesa = Expressao.montaPolonesa(teste);
			//System.out.println(polonesa);
			// System.out.println(Expressao.resolvePolonesa( filaNumerica, polonesa));


		} catch (Exception e){
			System.out.println("Erro de Execucao: " + e.getMessage());
		}
	}
}
