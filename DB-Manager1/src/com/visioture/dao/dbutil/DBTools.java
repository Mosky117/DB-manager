package com.visioture.dao.dbutil;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Classe di utility per la gestione della connessione al database <i>MySql</i>
 * tramite connettore <i>JDBC</i>.
 * <p>
 * Questa classe e' pensata per essere usata dal <i>DAO</i> all'interno
 * dell'architettura di backend:
 * <p>
 * <img src="doc-files/Backend.png" width="500">
 * <p>
 * Per funzionare bisogna aggiungere il connettore <i>JDBC</i> al classpath del
 * progetto come file jar esterno.</br>
 * Il connettore e' in dotazione col
 * <a href="https://dev.mysql.com/downloads/installer/">pacchetto di
 * installazione di MySql</a>, ma e' possibile anche
 * <a href="https://dev.mysql.com/downloads/connector/j/">scaricare solo il
 * connettore</a>.</br>
 * <p>
 * Questa classe espone due metodi statici:
 * <ul>
 * <li><i>getConnection()</i> - restituisce un oggetto di tipo
 * <b>Connection</b></li>
 * <li><i>closeConnection(Connection)</i> - chiude la connessione</li>
 * </ul>
 * <p>
 * La classe contiene inoltre un campo statico privato di tipo <b>Properties</b>
 * che parametrizza la connessione al database caricando i seguenti parametri
 * dal file <a href="file:config.properties">config.properties</a> posto nello
 * stesso package:
 * 
 * <ul>
 * <li>endpoint</li>
 * <li>port</li>
 * <li>schema</li>
 * <li>user</li>
 * <li>password</li>
 * </ul>
 * 
 * 
 * @author Giulio Grimani
 * 
 * @see {@link java.util.Properties}
 * @see {@link #getConnection()}
 * @see {@link #closeConnection(Connection)}
 */
public class DBTools {

	private static Properties properties = getProperties();

	/**
	 * Effettua la connessione al Database.
	 * <p>
	 * Questo metodo dovrebbe essere invocato dalle classi del DAO, poiche'
	 * restituisce un'oggetto di tipo <b>Connection<b> col quale interrogare il
	 * database.
	 * 
	 * @return Un oggetto di tipo <b>Connection</b>
	 */
	public static Connection getConnection() {
		driverSettings();
		Connection connection = null;
		try {
			String endpoint = properties.getProperty("endpoint");
			String port = properties.getProperty("port");
			String schema = properties.getProperty("schema");
			String user = properties.getProperty("user");
			String password = properties.getProperty("password");

			connection = DriverManager.getConnection("jdbc:mysql://" + endpoint + ":" + port + "/" + schema, user,
					password);
			return connection;
		} catch (Exception e) {
			System.out.println("Connection Failed! Check output console");
			System.out.println(e.getMessage());
			return null;
		}
	}

	/**
	 * scrivere la javadoc
	 * 
	 * @param connection
	 */
	public static void closeConnection(Connection connection) {
		try {
			if (connection != null) {
				connection.close();
				System.out.println("Connection closed !!");
			} else {
				System.out.println("Connection was null!!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Memorizza i parametri definiti nel file <i>config.properties</i> all'interno
	 * del campo statico di tipo <b>Properties</b> della classe.
	 */
	private static Properties getProperties() {
		Properties prop = new Properties();
		InputStream input = null;
		try {
			input = DBTools.class.getResourceAsStream("config.properties");

			// load a properties file
			prop.load(input);
			input.close();
			return prop;

		} catch (IOException ex) {
			ex.printStackTrace();
			try {
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
	}

	/**
	 * Imposta i driver del connettore <i>JDBC</i> per la connessione al Database.
	 * 
	 * @deprecated Questo metodo non e' piu' necessario da Java 1.6 in poi: <a href=
	 *             "https://www.tutorialspoint.com/is-it-mandatory-to-register-the-driver-while-working-with-jdbc"/>fonte.</a>
	 */
	@Deprecated
	private static void driverSettings() {
		System.out.println("-------- MySQL JDBC Connection Demo ------------");
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("MySQL JDBC Driver not found !!");
			return;
		}
		System.out.println("MySQL JDBC Driver Registered!");
	}

}
