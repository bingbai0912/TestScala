/**
  * Created by hadoop on 9/27/18.
  */
trait Animal {
  def call( c: String): Unit ={
    print(c)
  }

//  abstract def run
//  def run( i: Int)
}

class Dog extends Animal{

}
