package springdatacassandra.controller

import java.time.LocalDate
import java.util.concurrent.Executors

import com.typesafe.scalalogging.Logger
import org.apache.cassandra.service.CassandraDaemon
import org.springframework.web.bind.annotation.{GetMapping, RestController}

@RestController
class CassandraServerController {

  lazy val logger = Logger(getClass)
  private val singleThreadPool = Executors.newSingleThreadExecutor()
  private val cassandraDemonInstance = new CassandraDaemon(true)

  @GetMapping(path = Array("/node/activate"))
  def activateCassandra(): String = {
    System.setProperty("cassandra.storagedir", "C://data")
    logger.info("Running Cassandra Node in StandAlone Mode")
    singleThreadPool.submit((() => {
      cassandraDemonInstance.activate()
    }): Runnable)
    "Starting Cassandra Node"
  }

  //On Windows whole application will be shut down.
  @GetMapping(path = Array("/node/deactivate"))
  def deActivateCassandra(): String = {
    logger.info("Stopping Cassandra Node")
    cassandraDemonInstance.deactivate()
    "Cassandra Node Deactivated"
  }

  @GetMapping(path = Array("/"))
  def welcome(): String = {
    "Spring Boot Running: " + LocalDate.now()
  }
}
