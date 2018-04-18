package Servidor;


import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface SistemaNotas extends Remote {
	
	public String cadastrarNota(Integer matricula, Integer cod_disciplina, Float nota) throws RemoteException;
	public List<String> listarAlunos() throws RemoteException;
	public List<String> listarDisciplinas() throws RemoteException;
	public String cadastrarAluno(Integer matricula, String nome) throws RemoteException;
	public String cadastrarDisciplina(Integer codigo, String nome) throws RemoteException;
	public List<String> listarNotasPorDisciplinas(Integer codDisciplina) throws RemoteException;
	public List<String> listarNotasPorMatricula(Integer matricula) throws RemoteException;
	public List<String> consultarCR(Integer matricula) throws RemoteException;

}
