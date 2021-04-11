package net.lulli.shape.data.api;

public enum Dialect {
	STANDARD("standard"), 
	ORACLE("oracle"), 
	MYSQL("mysql"), 
	POSTGRES("postgres"), 
	SYBASE("sybase"), 
	MSSQL("mssql"),
	SQLITE("sqlite"), 
	CASSANDRA("cassandra");

	private String dialectString;

	Dialect(String dialect) {
		this.dialectString = dialect;
	}

	String value() {
		return dialectString;
	}
}
