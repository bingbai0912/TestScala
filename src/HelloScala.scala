import java.io._
import java.lang.Long
import java.lang.reflect.{Constructor, Method}
import java.net.InetAddress
import java.text.SimpleDateFormat
import java.util
import java.util.concurrent.ConcurrentHashMap
import java.util.{Calendar, Date}
import java.util.regex.Pattern

import org.apache.commons.lang.text.StrSubstitutor
import org.apache.hadoop.conf.Configuration
import org.apache.spark.sql.catalyst.util.DateTimeUtils
import org.apache.spark.unsafe.types.UTF8String
import org.dom4j.{DocumentHelper, Element}

import scala.io.Source
import sun.misc.BASE64Decoder

import scala.collection.mutable
import scala.collection.mutable.PriorityQueue
import scala.xml.XML

/**
  * Created by hadoop on 3/22/17.
  */
class HelloScala {

}

class A{}

class B extends A{}

object A
object B


object HelloScala{
  def main(args: Array[String]) {
//    val data = decrypt("PJKJGIJJKJKIGICMPICMGMBMFMBMEMEMHMDMLMNNBMPIAIJIHJCJHICJPICMEMBMNNCMKMNNCMGMBMNNFMFM", "MjQz")
//    val data = decrypt("MEEFLFCFNEMFAEHFMFLFCFJEEABAMAMABAHABANAGAGALBGAMAJEFEMFBFJFKFCFJEEACAHALBHAFALBEAHAHALBDAGA", "CHEHBGAHKHLHAH")
//    println(data)
//    val ip = getEth0IP
//    println(ip)
//    val s = "NetworkWordCount.driver.[pid_log,172.16.170.130,CLI,no,no]_NetworkWordCount.StreamingMetrics.streaming.waitingBatches"
//    val s1 = s.replaceAll("^\\[&\\]","")
//
//    val s2 = "[pid_log,172.16.170.130,CLI,no,no]"
//    val s3 = s2.matches("[.*,.*,.*,.*,.*]")
//    println(s3)

//    val hm = new java.util.HashMap[String, String]()
//    hm.put("name","111")
//    hm.put("name","222")
//    hm.put("name","333")
//    hm.put("name","4444")
//    println(hm.get("name"))
//    test(123)
//    testInputStream
//    testParseXML()
//    test()
//    test
//    println(" " + System.getenv("GPU_CARD"))
//     try {
//       Array(1)(5)
//     } catch {
//       case e :ArrayIndexOutOfBoundsException => e.printStackTrace()
//     }
//     val appName = "[wangquanquan1,172.19.153.36,BUFFALO,306766,348672279]_pop_vender_similarity_summary"
//     //    val appName = "[wangquanquan1,172.19.153.35,BUFFALO,306761,348671703]_caculate volumn similarity"
//     var erp = "unknown"
//     var source = "unknown"
//     var bufflo_id = "unknown"
//     val pattern: String = "\\[\\S*,\\S*,\\S*,\\S*,\\S*\\]_.*"
//     if(appName!=null && Pattern.matches(pattern, appName)){
//       println("----------------")
//       val tmp = appName.split("]_")(0).replace("[","").split(",")
//       if(tmp.length==5){
//         erp = tmp(0)
//         source = tmp(2)
//         bufflo_id = tmp(3)
//       }
//     }
//     println(erp)
//     println(source)
//     println(bufflo_id)
//    print(returnInt(Array(1,3,5)))
//    testInvoke
//    testOneSecond
//    testCase(1)

//    testWhile()
//    println("123")
//    println("123\n".trim.toLong)
//    println(DateTimeUtils.stringToTimestamp(UTF8String.fromString("123")))
//    testIterator
//    testCopy()
//    testSystem()
//    testEnv("wher is ${JAVA_HOME}bin")
//    testEnv(null)
//    println("/".split("/").last)
//    testThrow
//    testClassLoader
//    testFunc(age = 20)
//    testReturn("org.apache.hadoop.hive.ql.udf.generic.GenericUDAFFirstValue")
//    testList()
//    testArray
//    val dog = new Dog()
//    dog.call("wang wang")
//    testReg
//    testPriorityQueue
    testMap()
  }

  def testMap(): Unit ={
    val taskid2starttime:mutable.Map[String,String] = new mutable.HashMap[String,String]
    val taskid2endtime:mutable.Map[String,String] = new mutable.HashMap[String,String]

    taskid2starttime.put("1","111")
    taskid2starttime.put("2","222")
    taskid2starttime.put("3","333")
    taskid2starttime.put("4","444")
    taskid2endtime.put("1","aaa")
    taskid2endtime.put("2","bbb")
    taskid2endtime.put("3","ccc")
    taskid2endtime.put("4","ddd")

    val taskStartEnd = taskid2starttime.map { kv =>
      (kv._1,kv._2.toLong,taskid2endtime.get(kv._1).get.toLong)
    }

  }

  def testAvro(): Unit ={

  }

  def testPriorityQueue: Unit ={
    val ord = Ordering[Int]
    val q1 = PriorityQueue.empty[Int](ord)
    val q2 = PriorityQueue.empty[Int](ord.reverse)

    q1.enqueue(1)
    q1.enqueue(3)
    q1.enqueue(5)
    q1.enqueue(2)

    q2.enqueue(1)
    q2.enqueue(2)
    q2.enqueue(5)
    q2.enqueue(3)

    while(q1.nonEmpty){
      print(q1.dequeue())
    }
    println
    while(q2.nonEmpty){
      print(q2.dequeue())
    }
  }

  def testArray: Unit ={
    val array = Array(1)
    val result = array.map{ i => i*2 }.reduce(_ + _)
    println(result)
  }

  def testReg():Unit = {

    val reg = s"(?i)secret|password".r

    println(reg.findFirstIn("hdfs://ns2/useruser/spark/lib/spark-2.1.0-hadoop-2.7.1.zip"))
    println(reg.findFirstIn("spark.yarn.archive"))
  }

  def testList(): Unit = {
//    val inputs: mutable.Set[String] = mutable.Set[String]()
//    inputs.add("123")
//    inputs.add("123")
//    inputs.add("456")
//    inputs.foreach(println)

    val inputs2: mutable.Map[Int,String] = mutable.Map[Int,String]()
    inputs2 += ((1,"123"))
    inputs2 += ((2,"123"))
    inputs2 += ((3,"456"))
    println(inputs2.size)
    inputs2.values.foreach(println)
      println(inputs2.values.mkString(","))
      println(inputs2.values.mkString(","))


  }

  def testReturn(getClassName: String): Unit ={
    import scala.collection.JavaConversions._
    import scala.collection.JavaConverters._
    val list = new util.ArrayList[String](0)
    println(list.size())
    val shouldUseSparkUDFS = List("GenericUDAFFirstValue")
    for (shouldUseSparkUDF <- shouldUseSparkUDFS) {
      if (getClassName.contains(shouldUseSparkUDF)) {
        println("niu bi!")
        return
      }
    }
    println("wo cao??")
  }

  def testFunc(name:String = "baibing", age:Int = 18){
//    println(s"user name $name, age:$age")
    println("spark-2.1.0-hadoop-2.7.1.zip".replaceAll("-\\d\\.\\d\\.\\d-", "-2.3.0-"))
//    Array[Int]().filter(_>1)
  }

  def testClassLoader()={
    val loader = Thread.currentThread().getContextClassLoader
    val url =  loader.getResource("hive-site.xm")
    println(url)
  }

  def testThrow():Unit = {
    try{
      try{
        Array(1)(2)
      }catch {
        case e: Exception => {
          println("aaaa")
          throw e
        }
      }
    } catch {
      case e: ArrayIndexOutOfBoundsException => println("ArrayIndexOutOfBoundsException")
    }
    println(2)
  }

  def testEnv(str :String): Unit = {
    println(s"Source str is:$str")
    if(str!=null && !str.contains("$")){
      println(s"Result str is:$str")
    }else{
      println(StrSubstitutor.replace(str, System.getenv()))
    }
  }
  def testSystem(): Unit ={
    if (System.getProperty("wo cao") != null)
      println(123)
    println(456)
  }

  case class ObjTest(name :String, age :Int)
  def testCopy(): Unit = {
    val map = new mutable.HashMap[ObjTest,String]
    val obj1 = ObjTest("baibing", 20)
    map.put(obj1,"1")
    val obj2 = obj1.copy(name = "baibing")
    println(obj1.hashCode())
    println(obj2.hashCode())
    println(obj1.equals(obj2))
    println(map.get(obj2))
  }




  def testIterator():Unit ={
    val it = Array(1,2).iterator
//    println(it.size)
    println(it.hasNext)
    println(it.hasNext)
    println(it.hasNext)
    println(it.next())
//    println(timestamp.asInstanceOf[scala.Long])
  }

  def testWhile():Unit = {
    println(Thread.currentThread().getId)
    while(true){
      Thread.sleep(500L)
    }
    System.getenv()
  }

  def testCase(i: Any):Unit = {
    i match {
      case Int => println("123")
//      case string: Boolean => println("boolean")
//      case _ => println("unknown")
    }
  }

  def testOneSecond():Unit = {
    val t1 = System.currentTimeMillis()
    Thread.sleep(10000L)
    println(System.currentTimeMillis()-t1)
  }

  def testInvoke(): Unit = {
    try {
      val clazz: Class[_] = Class.forName("com.jd.bdp.monitor.RWMonito213r")
      val ctor: Constructor[_] = clazz.getConstructor(classOf[Configuration])
      ctor.setAccessible(true)
      val instance: Any = ctor.newInstance(new Configuration())
      val doMonitorMethod: Method = clazz.getDeclaredMethod("doRunningMonitor",
        classOf[java.lang.Long], classOf[java.lang.Long], classOf[java.lang.Long],
        classOf[java.lang.Long], classOf[String], classOf[String], classOf[String])
      val isKillJob: Any = doMonitorMethod.invoke(instance, new Long(1l), new Long(1l)
        , new Long(1l), new Long(1l), "appid", "username", "SPARK")
      //    val doMonitorMethod: Method = clazz.getMethod("doRunningMonitor")
      //    val isKillJob: Any = doMonitorMethod.invoke(instance, "")
      println(isKillJob.toString)
    } catch {
      case e: Throwable => println(e.toString)
    }
  }

  def returnInt(ars: Array[Int]): Int ={
    ars.map{ x=>
      return x
    }
    0
  }

  class MyClass(a:Int, b:Int, c:Int)

  def test(): Unit ={
    val cal = new MyClass(1,2,3)
//    val str1 = Some("123")
//    val str2 = None
//    println(str1.isDefined)
//    println(str2.isDefined)
//    val str = 123
//    str match {
//      case 12 => println(12)
//    }
//    val array = Array("1","2","3")
//    println(array.mkString(","))
//    val current = 30
//    val count = 96
//    val successRatio = f"${current.toDouble/count.toDouble*100}%2.2f"
//    println(successRatio)

//    val str = "explain insert overwrite table app_shlx_ord_all_di partition (dt='2017-11-09',tp='WEIXIU' )       select             s.op_time      ,             s.order_id     ,             s.vir_order_id ,             s.sale_ord_id  ,             s.source_plat  ,             s.source_client,             s.client_type  ,             s.b_ord_flag   ,             s.buss_line    ,             s.order_type   ,             s.sys_source   ,             s.vender_id    ,             s.customer_id  ,             s.create_time  ,             s.pay_time     ,             s.vir_bus_time ,             s.supplier     ,             s.quantity     ,             s.sale_amount  ,             s.gross_profit ,             s.pay_type     ,             s.pay_jingdou  ,             s.pay_coupon   ,             s.pay_other    ,             s.work_post_cd                  from             (                     select                             substr(ord_complete_tm, 1, 10) op_time, --日期                             sale_ord_id order_id         , --订单号                             sale_ord_id vir_order_id     , --源系统订单号                             sale_ord_id sale_ord_id      , --混合订单号                             'WEIXIU' source_plat --来源平台                             ,case                             when mc_ord_flag=1 then '11'                              else '12' end  source_client --来源客户端                             ,'' client_type --客户端类型                             ,0 b_ord_flag --B订单标识                             ,'WEIXIU' buss_line --业务线                             ,sale_ord_type_cd order_type --订单类型                             ,'WEIXIU' sys_source --生产系统                             ,pop_vender_id vender_id --   商家编号                             ,user_log_acct customer_id --用户登录账号                             ,sale_ord_tm create_time --下单时间                             ,pay_tm pay_time --  支付时间                             ,substr(ord_complete_tm, 1, 10) vir_bus_time --业务时间                             ,'' supplier --供应商                             ,sale_qtty quantity --销量                             , after_prefr_amount                                          + sku_freight_amount                             -pop_shop_dq_pay_amount                                      -pop_shop_jq_pay_amount                                      -pop_shop_lim_sku_jq_pay_amount                              -pop_shop_lim_sku_dq_pay_amount                              -dq_and_jq_pay_amount                                              sale_amount --销售额                             ,after_prefr_amount                                          + sku_freight_amount                             -pop_shop_dq_pay_amount                                      -pop_shop_jq_pay_amount                                      -pop_shop_lim_sku_jq_pay_amount                              -pop_shop_lim_sku_dq_pay_amount                              -dq_and_jq_pay_amount                              -(sku_stk_prc * sale_qtty)  gross_profit --毛利                             ,pay_mode_cd pay_type --支付方式                             ,coalesce(jbean_pay_amount, 0.0) pay_jingdou --京豆支付金额                             ,coalesce( dq_and_jq_pay_amount , 0.0 ) pay_coupon --优惠券支付金额                             ,0.0 pay_other --其他支付金额                             ,work_post_cd                     from                             gdm.gdm_m04_ord_det_sum                     where                             to_date(ord_complete_tm) =  '2017-11-09'                                                          and dt                   >= '2017-11-09'                             and sale_ord_type_cd      = '108'                                                         and sale_ord_valid_flag   = 1                                                                                                                                )             s"
//    println(str)
//    println(str.replaceAll("--\\s*\\S*\\s*",""))

//    println(true ^ true)
//    println(false ^ false)
//    println(true ^ false)
//    println(false ^ true)
//    B.getClass.getGenericInterfaces.foreach( c => println("1==>"+c.getClass.getCanonicalName))
//    println("2==>"+B.getClass.getGenericSuperclass)
//    val tag = "@_"
//    val str = "@_row_number"
//    if (str.startsWith(tag)) {
//      println(str.substring(tag.size))
//    } else {
//      println(str)
//    }

//    println(scala.util.Try { Class.forName("abcde") }.isSuccess)
//    println(System.getenv("USER"))
    val m = Map(("dt","20180112"),("dp","active"))
    println(m.toSeq.mkString("="))
    println(m.toList.mkString("="))
    println(File.separator+m.toArray.map(s => s._1 + "=" + s._2).mkString(File.separator))

    Thread.sleep(10)

  }


  def getNowDate():String={
    val now:Date = new Date()
    val dateFormat:SimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd")
    val hehe = dateFormat.format( now )
    hehe
  }

  def getYesterday():String= {
    val dateFormat: SimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd")
    val cal: Calendar = Calendar.getInstance()
    cal.add(Calendar.DATE, -1)
    val yesterday = dateFormat.format(cal.getTime())
    yesterday
  }


    def testOBJ = {
      println(B.getClass.isAssignableFrom(Class.forName("A")))
      println(A.getClass.isAssignableFrom(Class.forName("B")))
      println(Class.forName("A").isAssignableFrom(B.getClass))
      println(Class.forName("B").isAssignableFrom(A.getClass))
      println(Class.forName("A").isAssignableFrom(Class.forName("B")))
      println(Class.forName("A").isAssignableFrom(classOf[B]))
      println(Class.forName("B").isAssignableFrom(Class.forName("A")))
      println(A.getClass.isAssignableFrom(B.getClass))
      println(B.getClass.isAssignableFrom(A.getClass))
    }

    def testParseXML(): Unit = {
      val xml: String = "<configuration> \n  <property>\n    <name>dfs.journalnode.rpc-address</name>\n    <value>0.0.0.0:8485</value>\n    <source>hdfs-default.xml</source>\n    <source>job.xml</source>\n  </property>\n</configuration> "
      val doc = DocumentHelper.parseText(xml)
      val rootElt = doc.getRootElement
      val iter = rootElt.elementIterator("property")
      while (iter.hasNext) {
        //      val itemEle :Element = iter.next()
        //      itemEle
        iter.next()
      }

      XML.loadString(xml)
    }

    def testInputStream(): Unit = {
      val in = new FileInputStream("/disk/workspace/Jdh-Doctor/doctor-common/pom.xml")
      val lines = Source.fromInputStream(in)
      for (line <- lines) {
        System.out.println(line)
      }
      in.close()
      System.out.println(in == null)
    }

    //  def test(v:Any): Unit ={
    //    v match {
    //      case Integer =>
    //      case Long =>
    //      case Float =>
    //      case String =>
    //      case java.sql.Date =>
    //    }
    //  }

    def decrypt(data: String, key: String): String = {
      if (isEmpty(data) || isEmpty(key)) return null
      val k: Int = new String(new BASE64Decoder().decodeBuffer(key)).toInt
      val bData: Array[Byte] = data.getBytes
      var n: Int = data.getBytes.length
      if (n % 2 != 0) {
        return ""
      }
      n = n / 2
      val buff: Array[Byte] = new Array[Byte](n)
      for (i <- buff.indices) {
        buff(i) = ((n >> (i << 3)) & 0xFF).toByte
      }
      var j: Int = 0
      for (i <- 0 until n) {
        var data1: Byte = bData(j)
        var data2: Byte = bData(j + 1)
        j += 2
        data1 = (data1 - 65).toByte
        data2 = (data2 - 65).toByte
        val b2: Byte = (data2 * 16 + data1).toByte
        buff(i) = (b2 ^ k).toByte
      }
      new String(buff)
    }

    def isEmpty(str: String): Boolean = {
      str == null || str.length == 0
    }

    def getEth0IP: String = {
      var ip: String = ""
      try {
        ip = InetAddress.getLocalHost.getHostAddress
        if (!(ip == null || ip.length == 0 || "127.0.0.1" == ip)) {
          println("InetAddress.getLocalHost.getHostAddress")
          return ip
        }
      }
      catch {
        case e: Exception => {
          println("获取本机eth1 IP失败")
          println(e.printStackTrace())
        }
      }
      try {
        val rt: Runtime = Runtime.getRuntime
        val proc: Process = rt.exec(Array[String]("/bin/sh", "-c", "hostname -i"), null, null)
        val stdin: InputStream = proc.getInputStream
        val isr: InputStreamReader = new InputStreamReader(stdin)
        val br: BufferedReader = new BufferedReader(isr)
        ip = br.readLine
        if (!(ip == null || ip.length == 0 || ip.startsWith("127.0.0.1"))) {
          println("hostname -i")
          return ip
        }
        br.close
        isr.close
        stdin.close
      }
      catch {
        case e: IOException => {
          println("获取本机eth1 IP失败")
          println(e.printStackTrace())
        }
      }
      if ("" == ip || ip.startsWith("127.0.0.1")) {
        try {
          val rt: Runtime = Runtime.getRuntime
          val proc: Process = rt.exec(Array[String]("/bin/sh", "-c", "ifconfig eth1 | grep 'inet addr' |" +
            " awk '{print $2}' | awk -F ':' '{print $2}'"), null, null)
          println("ifconfig eth1")
          val stdin: InputStream = proc.getInputStream
          val isr: InputStreamReader = new InputStreamReader(stdin)
          val br: BufferedReader = new BufferedReader(isr)
          ip = br.readLine
          br.close
          isr.close
          stdin.close
        }
        catch {
          case e: IOException => {
            println("获取本机eth1 IP失败")
            println(e.printStackTrace())
          }
        }
      }
      ip
    }
}