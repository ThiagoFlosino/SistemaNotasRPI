package Servidor;

import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import Banco.Banco;

public class SistemaNotasServer {
	

	void SistemaNotasImple(){

	}

	public static void main(String[] args) {
		try {
			Registry registry = LocateRegistry.createRegistry(1099);
			SistemaNotas sn = new SistemaNotasImple();
			registry.rebind("SistemaNotas", sn);
			System.out.println("Server Rodando");
			Banco.iniciaBanco();
			System.out.println("Banco Iniciado");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
