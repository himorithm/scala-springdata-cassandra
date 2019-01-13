package springdatacassandra

import com.typesafe.scalalogging.Logger
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class CassandraServerBootStrap

object CassandraServerBootStrap extends App {
  lazy val logger = Logger(getClass)
  logger.info("Starting CassandraServerBootStrap")
  SpringApplication.run(classOf[CassandraServerBootStrap], args: _*)
}
