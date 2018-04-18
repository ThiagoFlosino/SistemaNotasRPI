package Servidor;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Banco.Banco;

public class SistemaNotasImple extends UnicastRemoteObject implements SistemaNotas{

	private static final long serialVersionUID = 1L;

	public SistemaNotasImple() throws RemoteException {
		super();
	}
	

	@Override
	public String cadastrarNota(Integer matricula, Integer cod_disciplina, Float nota) {
		if(matricula == null|| cod_disciplina == null|| nota == null) {
			return "Os Parametros não podem ser nulos";
		}else {
			try {
				Connection conn = Banco.getConnection();
				String sqlVerifica = "SELECT * FROM NOTAS WHERE COD_DISCIPLINA = ? AND MAT_ALUNO = ?";
				PreparedStatement st = conn.prepareStatement(sqlVerifica);
				st.setInt(1, cod_disciplina);
				st.setInt(2, matricula);
				ResultSet rs = st.executeQuery();
				
				if(rs.next()) {
					String sql = "UPDATE NOTAS SET NOTA = ? WHERE COD_DISCIPLINA = ? AND MAT_ALUNO = ?";	
					st = conn.prepareStatement(sql);
					st.setInt(2, cod_disciplina);
					st.setInt(3, matricula);
					st.setFloat(1, nota);
					st.executeUpdate();

				}else {
					String sql = "INSERT INTO NOTAS VALUES(?,?,?)";
					st = conn.prepareStatement(sql);
					st.setInt(1, cod_disciplina);
					st.setInt(2, matricula);
					st.setFloat(3, nota);
					st.execute();
				}
				conn.close();
				return "Inserido com sucesso";
			} catch (SQLException e) {
				e.printStackTrace();
				return "Erro ao inserir dados";
			}

		}
		
	}


	@Override
	public List<String> listarAlunos() throws RemoteException {
		ResultSet rs = null;
		List<String> resultado = new ArrayList<String>();
		try {
			Connection conn = Banco.getConnection();
			Statement st;
			st = conn.createStatement();
			String sql = "Select * from Aluno;";
			rs = st.executeQuery(sql);
			while (rs.next()) {
				resultado.add("Matricula: " + rs.getInt("matricula") + ", Nome:" + rs.getString("nome"));
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return resultado;
	}
	
	public List<String> listarDisciplinas() throws RemoteException {
		ResultSet rs = null;
		List<String> resultado = new ArrayList<String>();
		try {
			Connection conn = Banco.getConnection();
			Statement st;
			st = conn.createStatement();
			String sql = "Select * from disciplina;";
			rs = st.executeQuery(sql);
			while (rs.next()) {
				resultado.add("Código: " + rs.getInt("codigo") + ", Disciplina:" + rs.getString("nome"));
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return resultado;
	}


	@Override
	public String cadastrarAluno(Integer matricula, String nome) throws RemoteException {
		try {
			Connection conn = Banco.getConnection();
			Statement st;
			st = conn.createStatement();
			String sql = "INSERT INTO ALUNO VALUES('"+nome+"',"+matricula+");";
			st.execute(sql);
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "Aluno de matricula: " + matricula + " e nome: "+ nome +". Inserido com sucesso.";
	}
	
	public String cadastrarDisciplina(Integer codigo, String nome) throws RemoteException {
		try {
			Connection conn = Banco.getConnection();
			Statement st;
			st = conn.createStatement();
			String sql = "INSERT INTO DISCIPLINA VALUES("+codigo+",'"+nome+"');";
			st.execute(sql);
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "Código: " + codigo + " e Disciplina: "+ nome +". Inserido com sucesso.";
	}
	
	public List<String> listarNotasPorDisciplinas(Integer codDisciplina) throws RemoteException {
		ResultSet rs = null;
		List<String> resultado = new ArrayList<String>();
		try {
			Connection conn = Banco.getConnection();
			Statement st;
			st = conn.createStatement();
			String sql = "Select d.nome as nomeDisciplina, mat_aluno, nota from notas join disciplina d on(notas.cod_disciplina = d.codigo) where cod_disciplina ="+codDisciplina+ ";";
			rs = st.executeQuery(sql);
			while (rs.next()) {
				resultado.add("Disciplina: " + rs.getString("nomeDisciplina") + ", Matricula: " + rs.getInt("mat_aluno") + ", Nota: "+ rs.getDouble("nota"));
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(resultado.isEmpty()) {
			resultado.add("Nenhuma nota cadastrada para essa Disciplina");
		}
		return resultado;
	}
	
	public List<String> listarNotasPorMatricula(Integer matricula) throws RemoteException {
		ResultSet rs = null;
		List<String> resultado = new ArrayList<String>();
		try {
			Connection conn = Banco.getConnection();
			String sql = "Select cod_disciplina, nota from notas where mat_aluno = ?";
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, matricula.intValue());
			rs = st.executeQuery();
			while (rs.next()) {
				System.out.println("entrou no adicionar Disciplina");
				resultado.add("Disciplina: " + rs.getInt("cod_disciplina") + ", Nota: "+ rs.getDouble("nota"));
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(resultado.isEmpty()) {
			resultado.add("Nenhuma nota cadastrada para esse Aluno");
		}
		return resultado;
	}
	public List<String> consultarCR(Integer matricula) throws RemoteException {
		ResultSet rs = null;
		List<String> resultado = new ArrayList<String>();
		try {
			Connection conn = Banco.getConnection();
			Statement st;
			st = conn.createStatement();
			String sql = "Select avg(nota) as cr from notas where mat_aluno ="+matricula+ ";";
			rs = st.executeQuery(sql);
			while (rs.next()) {
				resultado.add("CR: " +  rs.getDouble("cr"));
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(resultado.isEmpty()) {
			resultado.add("Nenhuma nota cadastrada para esse Aluno");
		}
		return resultado;
	}

}
