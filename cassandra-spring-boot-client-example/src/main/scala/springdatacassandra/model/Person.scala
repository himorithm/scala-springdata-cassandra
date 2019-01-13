package springdatacassandra.model

import org.springframework.data.cassandra.core.cql.PrimaryKeyType
import org.springframework.data.cassandra.core.mapping.{PrimaryKey, PrimaryKeyClass, PrimaryKeyColumn, Table}
import org.springframework.data.cassandra.repository.CassandraRepository
import org.springframework.stereotype.Repository

@Table
class Person extends Serializable {
  @PrimaryKey
  var personKey: PersonKey = _
  var address: String = _
  var email: String = _


  override def toString = s"Person(personKey=$personKey, address=$address, email=$email)"
}

@PrimaryKeyClass
class PersonKey extends Serializable {

  @PrimaryKeyColumn(ordinal = 0, `type` = PrimaryKeyType.PARTITIONED)
  var name: String = _

  @PrimaryKeyColumn(ordinal = 1, `type` = PrimaryKeyType.CLUSTERED)
  var number: Long = _

  override def equals(other: Any): Boolean = other match {
    case that: PersonKey =>
      (that canEqual this) &&
        name == that.name &&
        number == that.number
    case _ => false
  }

  def canEqual(other: Any): Boolean = other.isInstanceOf[PersonKey]

  override def hashCode(): Int = {
    val state = Seq(name, number)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }


  override def toString = s"PersonKey(name=$name, number=$number)"
}

@Repository
sealed trait PersonRepository extends CassandraRepository[Person, PersonKey]
