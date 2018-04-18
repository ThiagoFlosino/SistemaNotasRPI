package Cliente;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Scanner;

import Servidor.SistemaNotas;

public class Cliente {
	public static void main(String[] args) throws RemoteException, NotBoundException {
			Registry registry = LocateRegistry.getRegistry("localhost", 1099);
			SistemaNotas s = (SistemaNotas) registry.lookup("SistemaNotas");

			Scanner input = new Scanner(System.in);
			
			String exit = "";
			
			while(!exit.equalsIgnoreCase("Exit")) {
				System.out.println("Informe que Operação Deseja Realizar:");
				System.out.println("1 - Listar Alunos");
				System.out.println("2 - Listar Disciplinas");
				System.out.println("3 - Cadastar Alunos");
				System.out.println("4 - Cadastar Disciplinas");
				System.out.println("5 - Cadastar Nota");
				System.out.println("6 - Consultar Notas por Disciplina");
				System.out.println("7 - Consultar Nota");
				System.out.println("8 - Consultar CR");
				System.out.println("9 - Exit");
				
				switch(Integer.parseInt(input.nextLine())) {
					case 1:
						List<String> alunos = s.listarAlunos();
						for(int i = 0; i < alunos.size(); i++) {
							System.out.println(alunos.get(i));
						}
						System.out.println("\n---------------------------------------");
						break;
					case 2:
						List<String> disciplinas = s.listarDisciplinas();
						for(int i = 0; i < disciplinas.size(); i++) {
							System.out.println(disciplinas.get(i));
						}
						System.out.println("\n---------------------------------------");
						break;
					case 3:
						System.out.println("Informe a matricula do Aluno, e o nome");
						Integer matriculaAluno =Integer.parseInt(input.nextLine());
						String nome = input.nextLine();
						System.out.println(s.cadastrarAluno(matriculaAluno, nome));
						System.out.println("\n---------------------------------------");
						break;
					case 4:
						System.out.println("Informe o Código e o nome da Disciplina");
						Integer cod_Disciplina =Integer.parseInt(input.nextLine());
						String nomeDisciplina = input.nextLine();
						System.out.println(s.cadastrarDisciplina(cod_Disciplina, nomeDisciplina));
						System.out.println("\n---------------------------------------");
						break;
					case 5:
						System.out.println("Informe a matricula do Aluno, o Código da Disciplina e a Nota");
						Integer matricula =Integer.parseInt(input.nextLine());
						Integer disciplina = Integer.parseInt(input.nextLine());
						Float nota = Float.valueOf(input.nextLine());
						System.out.println(s.cadastrarNota(matricula, disciplina, nota));
						System.out.println("\n---------------------------------------");
						break;
					case 6:
						System.out.println("Informe o Código da Disciplina");
						Integer codDisciplina =Integer.parseInt(input.nextLine());
						List<String> notasDisciplina= s.listarNotasPorDisciplinas(codDisciplina);
						for(int i = 0; i < notasDisciplina.size(); i++) {
							System.out.println(notasDisciplina.get(i));
						}
						System.out.println("\n---------------------------------------");
						break;
					case 7:
						System.out.println("Informe a Matricula do Aluno");
						Integer matAluno =Integer.parseInt(input.nextLine());
						List<String> notasAluno= s.listarNotasPorMatricula(matAluno);
						for(int i = 0; i < notasAluno.size(); i++) {
							System.out.println(notasAluno.get(i));
						}
						System.out.println("\n---------------------------------------");
						break;
					case 8:
						System.out.println("Informe a Matricula do Aluno");
						Integer mtAluno =Integer.parseInt(input.nextLine());
						System.out.println(s.consultarCR(mtAluno));
						System.out.println("\n---------------------------------------");
						break;
					case 9:
						exit = "Exit";
						break;
				}
				
			}

	}
}
