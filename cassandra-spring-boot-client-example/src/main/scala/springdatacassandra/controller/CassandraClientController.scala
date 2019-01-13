package springdatacassandra.controller

import java.util
import java.util.Optional

import com.typesafe.scalalogging.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.{RequestBody, RequestMapping, RequestMethod, RestController}
import springdatacassandra.model.{Person, PersonKey, PersonRepository}

@RestController
case class CassandraClientController(@Autowired personRepository: PersonRepository) {
  val logger = Logger(getClass)

  @RequestMapping(path = Array("/person/get"), method = Array(RequestMethod.POST))
  def getPerson(@RequestBody personKey: PersonKey): Optional[Person] = {
    logger.info(s"Received getPerson request:$personKey")
    personRepository.findById(personKey)
  }

  @RequestMapping(path = Array("/person/save"), method = Array(RequestMethod.POST))
  def savePerson(@RequestBody person: Person): Person = {
    logger.info(s"Received savePerson request:${person.personKey}")
    personRepository.save(person)
  }

  @RequestMapping(path = Array("/person/findAll"), method = Array(RequestMethod.GET))
  def findAllPerson(): util.List[Person] = {
    logger.info(s"Received findAllPerson request")
    personRepository.findAll()
  }
}
