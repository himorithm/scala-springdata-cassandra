package springdatacassandra.controller

import org.scalatest.FlatSpec
import springdatacassandra.model.{Person, PersonKey}


class PersonTest extends FlatSpec {

  it should "test Person" in {
    var key = new PersonKey()
    key.name = "wow"
    key.number = 12

    var person = new Person()
    person.personKey = key
    person.address = "asd"
    person.email = "a@b"
    println(person)
  }

}


