package com.himanshu.demo.springdatacassandra.model

import org.springframework.data.cassandra.core.cql.PrimaryKeyType
import org.springframework.data.cassandra.core.mapping.{PrimaryKey, PrimaryKeyClass, PrimaryKeyColumn, Table}
import org.springframework.data.cassandra.repository.CassandraRepository
import org.springframework.stereotype.Repository

@Table
class Person extends Serializable {
  @PrimaryKey
  var personKey : PersonKey = _
  var address : String = _
  var email : String = _
}

@PrimaryKeyClass
class PersonKey extends Serializable{

  @PrimaryKeyColumn(ordinal = 0, `type` = PrimaryKeyType.PARTITIONED)
  var name: String = _

  @PrimaryKeyColumn(ordinal = 1, `type` = PrimaryKeyType.CLUSTERED)
  var number: Long = _
}

@Repository
sealed trait PersonRepository extends CassandraRepository[Person, PersonKey]
