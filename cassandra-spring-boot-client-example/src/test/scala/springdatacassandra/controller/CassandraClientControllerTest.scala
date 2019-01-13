package springdatacassandra.controller

import org.junit.runner.RunWith
import org.mockito.{ArgumentCaptor, Mockito}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.data.cassandra.CassandraDataAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.context.annotation.{ComponentScan, Configuration}
import org.springframework.test.context.junit4.SpringRunner
import springdatacassandra.model.{Person, PersonRepository}

@RunWith(classOf[SpringRunner])
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = Array(classOf[TestConfig]))
class CassandraClientControllerTest extends IntTestSpec {

  @MockBean var personRepository: PersonRepository = _
  @Autowired var controller: CassandraClientController = _
  @Autowired var testRestTemplate: TestRestTemplate = _
  @LocalServerPort var randomServerPort: Int = _
  var baseUrl = s"http://localhost:$randomServerPort"

  it should "save given person" in {
    Given("a Person instance")
    val person = new Person
    val url = "/person/save"
    When(s"a request sent to $url")
    Mockito.when(personRepository.save(ArgumentCaptor.forClass(classOf[Person]).capture()))
      .thenReturn(new Person)
    val returnPerson = testRestTemplate.postForObject(s"$baseUrl$url", person, classOf[Person])

    Then("Returned Person should not be null")
    assert(returnPerson != null)
  }

}

@EnableAutoConfiguration(exclude = Array(classOf[CassandraDataAutoConfiguration]))
@ComponentScan
@Configuration
class TestConfig

