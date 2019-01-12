package com.himanshu.demo.springdatacassandra.controller

import org.scalatest.{FlatSpec, GivenWhenThen}
import org.springframework.test.context.TestContextManager

class IntTestSpec extends FlatSpec with GivenWhenThen{
  new TestContextManager(this.getClass).prepareTestInstance(this)
}
