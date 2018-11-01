/**
  * Created by hadoop on 10/30/17.
  */
class Parent {
  def printName: Unit ={
    println("Father")
  }

  def myprint: Unit ={
    printName
    this.printName
  }
}


