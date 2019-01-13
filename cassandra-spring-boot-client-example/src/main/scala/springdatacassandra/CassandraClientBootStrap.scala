package springdatacassandra

import java.util

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.{EnableAutoConfiguration, SpringBootApplication}
import org.springframework.context.annotation.{ComponentScan, Configuration}
import org.springframework.data.cassandra.config.{AbstractCassandraConfiguration, CassandraClusterFactoryBean, SchemaAction}
import org.springframework.data.cassandra.core.cql.keyspace.{CreateKeyspaceSpecification, KeyspaceOption}
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories

@EnableAutoConfiguration
@ComponentScan
@EnableCassandraRepositories
@Configuration
@SpringBootApplication
class CassandraClientBootStrap extends AbstractCassandraConfiguration {

  @Value("${spring.data.cassandra.keyspace-name}")
  var keySpaceName: String = _

  /** optional settings below **/
  override def cluster(): CassandraClusterFactoryBean = {
    val factory = super.cluster()
    factory.setJmxReportingEnabled(false) // avoid jmx spring boot cassandra issues.
    factory
  }

  override def getKeyspaceCreations: util.List[CreateKeyspaceSpecification] = {
    util.Arrays.asList(CreateKeyspaceSpecification.createKeyspace(getKeyspaceName)
      .ifNotExists()
      .withSimpleReplication(3L)
      .`with`(KeyspaceOption.DURABLE_WRITES, true))
  }

  override def getKeyspaceName: String = keySpaceName

  override def getSchemaAction = SchemaAction.RECREATE_DROP_UNUSED
}

object CassandraClientBootStrap extends App {
  SpringApplication.run(classOf[CassandraClientBootStrap], args: _*)
}
