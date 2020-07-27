package net.lulli.shape.data.helper;

public class DbConnectionParameters {
  public final String jdbcUrl;
  public final String driverClassName;
  public final String user;
  public final String password;
  public final String dialect;
  public final String schema;
  public int poolSize = 1;

  private DbConnectionParameters(Builder builder) {
    this.jdbcUrl = builder.jdbcUrl;
    this.driverClassName = builder.driverClassName;
    this.user = builder.user;
    this.password = builder.password;
    this.dialect = builder.dialect;
    this.poolSize = builder.poolSize;
    this.schema = builder.schema;
  }

  public static class Builder {
    private String jdbcUrl;
    private String driverClassName;
    private String user;
    private String password;
    private String dialect;
    private String schema;
    private int poolSize;

    public String getJdbcUrl() {
      return jdbcUrl;
    }

    public String getDialect() {
      return dialect;
    }

    public Builder setDialect(String dialect) {
      StrictCheck.isNotNull(dialect);

      this.dialect = dialect;
      return this;
    }

    public Builder setJdbcUrl(String jdbcUrl) {
      StrictCheck.isNotNull(jdbcUrl);

      this.jdbcUrl = jdbcUrl;
      return this;
    }

    public Builder setDriverClassName(String driverClassName) {
      StrictCheck.isNotNull(driverClassName);

      this.driverClassName = driverClassName;
      return this;
    }

    public Builder setUser(String user) {
      StrictCheck.isNotNull(user);

      this.user = user;
      return this;
    }

    public Builder setPassword(String password) {
      StrictCheck.isNotNull(password);

      this.password = password;
      return this;
    }

    public Builder setSchema(String schema) {

      this.schema = schema;
      return this;
    }

    public DbConnectionParameters build() {
      return new DbConnectionParameters(this);
    }

    public void setPoolSize(int poolSize) {
      this.poolSize = poolSize;
    }
  }
}
