import java.io.IOException
import java.net.{URL, URLDecoder}
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.concurrent.ConcurrentHashMap

import scala.actors.Actor

/**
  * Created by hadoop on 10/30/17.
  */
class Sun extends Parent{
  override def printName: Unit ={
    println("Sun")
//    Seq(Array.fill(50))
  }
}

object Sun{
  def main(args: Array[String]) {
//    val sun = new Sun()
//    sun.myprint
//    Actor.getClass().getResource("/").getFile
//    println(System.getProperty("java.class.path"))
//    find
//    testHashMap
    testDate
  }

  def testDate():Unit = {
    val cal = Calendar.getInstance()
    cal.add(Calendar.DATE, -1)
    val yesterday = new SimpleDateFormat("yyyy/MM/dd").format(cal.getTime)
    println(yesterday)
  }

  def testHashMap(): Unit ={
    val map = new collection.mutable.HashMap[String,String]
    map.put("name","baibing")
    map.put("name","baifang")
    println(map.get("name"))
//    ConcurrentHashMap
  }


  def find(obj: Object): Unit = {
//    logInfo("========================bai LOG===========================")
    val clazz = obj.getClass
    val loader = clazz.getClassLoader()
    val classFile = clazz.getName().replaceAll("\\.", "/") + ".class"
    try {
      val e: java.util.Enumeration[URL] = loader.getResources(classFile)
      var url : URL = null
      do {
        if (!e.hasMoreElements()) {
//          logInfo("not found!")
        }
        url = e.nextElement()
      } while (!"jar".equals(url.getProtocol()))
      var toReturn = url.getPath()
      if(toReturn.startsWith("file:")) {
        toReturn = toReturn.substring("file:".length())
      }
      toReturn = URLDecoder.decode(toReturn, "UTF-8")
      val jarName = toReturn.replaceAll("!.*$", "")
//      logError("jarName == " + jarName)
    } catch {
      case var6: IOException => new RuntimeException(var6)
    }
  }
}
