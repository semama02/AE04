package es.florida.AE04;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;


public class AE04 {
	
	//	Métode: main
	//	Descripció: fem la conexió amb la base de dades i mentres que llegim el fitxer nem anyadint les dades a la base de dades.
	//	Parametres d'entrada: ningún
	//	Parametres d'eixida: ningún
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/biblioteca","root","");
			Statement stmt = con.createStatement();
			
			try {
				FileReader fr = new FileReader("AE04_T1_4_JDBC_Dades.csv");
				BufferedReader br = new BufferedReader(fr);
				String linea = br.readLine();
				linea = br.readLine();
				while (linea != null) {
					String[] lineas = linea.split(";");
					for (int i = 0; i < lineas.length; i++) {
						if (lineas[i].equals("")) {
							lineas[i]="N.C";
						}
					}
					PreparedStatement psInsertar = con.prepareStatement("INSERT INTO llibres (titol,autor,any_naixement,any_publicacio,editorial,num_pags) VALUES (?,?,?,?,?,?)");
					psInsertar.setString(1,lineas[0]);
					psInsertar.setString(2,lineas[1]);
					psInsertar.setString(3,lineas[2]);
					psInsertar.setString(4,lineas[3]);
					psInsertar.setString(5,lineas[4]);
					psInsertar.setString(6,lineas[5]);
					int resultadoInsertar = psInsertar.executeUpdate();
					linea = br.readLine();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			System.out.println("CONSULTA 1: (autors nascuts abans de 1950)");
			ResultSet consulta1 = stmt.executeQuery("SELECT titol,autor,any_publicacio FROM llibres WHERE any_naixement < 1950");
			while(consulta1.next()) {		
				System.out.println(consulta1.getString(1)+" "+consulta1.getString(2)+" "+consulta1.getString(3));
			}
			consulta1.close();
			
			System.out.println("");
			System.out.println("CONSULTA 2: (hagen publicat almenys un llibre en el segle XXI)");
			ResultSet consulta2 = stmt.executeQuery("SELECT editorial FROM llibres WHERE any_publicacio > 2001 ORDER BY editorial");
			while(consulta2.next()) {
				System.out.println(consulta2.getString(1));
			}
			consulta2.close();
			
			stmt.close();
			con.close();
			} catch (Exception e) {
			System.out.println(e);
			} 
	}

}
