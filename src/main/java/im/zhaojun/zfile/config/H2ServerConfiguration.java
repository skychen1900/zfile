package im.zhaojun.zfile.config;

import java.sql.SQLException;

import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.java.Log;

/**
 * h2db tcp server start/stop.
 */
@Configuration
@Log
public class H2ServerConfiguration {

	@Value("${zfile.db.port}")
	private String h2TcpPort;

	/**
	 * TCP connection to connect with SQL clients to the embedded h2 database.
	 *
	 * @see Server
	 * @throws SQLException if something went wrong during startup the server.
	 * @return h2 db Server
	 */
	@Bean
	public Server server() {

		try {
			Server h2Server = Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", h2TcpPort).start();
			if (h2Server.isRunning(true)) {
				log.info("H2 server was started and is running.");
			} else {
				throw new RuntimeException("Could not start H2 server.");
			}
			return h2Server;

		} catch (SQLException e) {
			throw new RuntimeException("Failed to start H2 server: ", e);
		}

	}

	/**
	 * @return FlywayMigrationStrategy the strategy for migration.
	 */
//	@Bean
//	@DependsOn("server")
//	public FlywayMigrationStrategy flywayMigrationStrategy() {
//		return Flyway::migrate;
//	}
}