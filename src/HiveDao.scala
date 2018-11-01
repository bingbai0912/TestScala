//package com.jd.shml.jwriter.push
//
//import scala.collection.JavaConversions._
//
//import org.apache.spark.sql._
//
//case class Article(
//                    item_sku_id:String,
//                    item_id:String,
//                    sku_name:String,
//                    item_first_cate_id:String,
//                    item_first_cate_name:String,
//                    item_second_cate_id:String,
//                    item_second_cate_name:String,
//                    item_third_cate_name:String,
//                    title:String,
//                    head_image:String,
//                    sub_image1:String,
//                    sub_image2:String,
//                    content:String,
//                    title_model_id:String,
//                    image_model_id:String,
//                    content_model_id:String,
//                    ctime:String,
//                    title_score:Double,
//                    image_score:Double,
//                    content_score:Double,
//                    score:Double,
//                    var publish_time:String,
//                    var publish_status:Integer,
//                    var failure_reason:String,
//                    var fxhh_article_id:Integer,
//                    id:String,
//                    platform_des:String,
//                    var pin:String,
//                    title_md5:String,
//                    content_md5:String,
//                    pic_md5:String,
//                    dt:String,
//                    item_third_cate_id:String
//                  )
//
//case class PublishConfig(
//                          platform_des:String,
//                          cate_id_and_limit_published:String,
//                          pin:String
//                        )
//
//class HiveDao {
//  val colunms = """
//    item_sku_id,item_id,sku_name,item_first_cate_id,item_first_cate_name,item_second_cate_id,item_second_cate_name,item_third_cate_name,title,head_image,
//    sub_image1,sub_image2,content,title_model_id,image_model_id,content_model_id,ctime,title_score,image_score,content_score,score,
//    title_md5,content_md5,pic_md5,id,dt,item_third_cate_id
//                """
//
//  val publishedColunms = """
//    item_sku_id,item_id,sku_name,item_first_cate_id,item_first_cate_name,item_second_cate_id,item_second_cate_name,item_third_cate_name,title,head_image,
//    sub_image1,sub_image2,content,title_model_id,image_model_id,content_model_id,ctime,title_score,image_score,content_score,score,
//    publish_time,publish_status,failure_reason,fxhh_article_id,id,platform_des,pin,title_md5,content_md5,pic_md5
//                         """
//
//  def fetchArticles(dt : String, cid3 : String, limit : String, platform_des_in : String) : java.util.List[Article] = {
//    var sqlStr = """
//          select a.*,if (d.sku is null, 0, d.sku_count) as sku_count_score from
//            (select %s from app.app_srd_jwriter_fxhh_article_to_be_published_da where dt='%s' and item_third_cate_id = '%s' and item_sku_id in
//            (select item_sku_id from app.app_srd_jwriter_sku_da where dt='%s' and item_third_cate_cd = '%s' and source = '%s')) a
//          left outer join
//            (select id from (select id,if(platform_des is null, 'fxhh', platform_des) as platform_des_m from app.app_srd_jwriter_fxhh_article_published_di
//            where publish_status in (0,1)) c where c.platform_des_m = '%s') b
//          on a.id = b.id
//          left outer join
//          (select sku, count(*) as sku_count from fdm.fdm_mobile_sh_promotion_discovery_cms_discovery_nicegoods_single_recommend_1 where status = 3 group by sku) d
//          on d.sku = a.item_sku_id
//          where b.id is null and a.content_model_id in('template_manualPauto_v0.2','template_auto_v0.2','template_manual_v0.2','avg_score_v0.1')
//              and a.item_sku_id not in (
//                  select ac.item_sku_id from
//                      app.app_srd_jwriter_fxhh_article_to_be_published_da ac
//                      inner join
//                      (
//                          select * from
//                          (
//                              select ak.id, ak.item_sku_id, ak.content_model_id, ak.title_model_id, ak.image_model_id,
//                              al.status, al.off_reason, Row_Number() OVER (partition by al.sku ORDER BY al.modify desc, ak.publish_time desc) rank
//                              from  app.app_srd_jwriter_fxhh_article_published_di ak,  fdm.fdm_mobile_sh_promotion_discovery_cms_discovery_nicegoods_single_recommend_1 al
//                              where ak.fxhh_article_id = al.id
//                              and al.recommender_id in
//                              (
//                                  select author_id
//                                  from app.app_celebrity_article_stats_info_di aa
//                                  left semi join(
//                                      select  explode(split(pin, ',')) as author_pin
//                                      from  app.app_srd_jwriter_fxhh_article_published_config) ab
//                                      on aa.author_pin = ab.author_pin
//                                      group by author_id
//                              )
//                              and ak.publish_status = 0
//                          ) ac
//                          where rank = 1
//                      ) ad
//                      on ad.item_sku_id = ac.item_sku_id
//                      where ad.status in (2,3) or off_reason like '%%运营调控%%' or off_reason like '%%商品不符合要求%%'
//                          or (off_reason like '%%图片不符合要求%%' and ac.image_model_id = ad.image_model_id)
//                          or (off_reason like '%%标题或推荐语不符合要求%%' and (ac.content_model_id = ad.content_model_id or ac.title_model_id = ad.title_model_id))
//                      group by ac.item_sku_id
//              )
//          order by sku_count_score asc, score desc, content_score desc, title_score desc, image_score desc limit %s
//                 """
//    val spark = SparkSession.builder().appName("HiveDao").enableHiveSupport().getOrCreate();
//    sqlStr = String.format(sqlStr, colunms, dt, cid3, dt, cid3, platform_des_in, platform_des_in, limit);
//    println("sql is : " + sqlStr);
//    val df = spark.sql(sqlStr);
//
//    val list = df.collectAsList();
//    val retList = new java.util.ArrayList[Article]();
//    list.toList.foreach { x => {
//      //println("row begin")
//      //println(x)
//      val item_sku_id = x.getAs[String]("item_sku_id");
//      val item_id = x.getAs[String]("item_id")
//      val sku_name = x.getAs[String]("sku_name")
//      val item_first_cate_id = x.getAs[String]("item_first_cate_id")
//      val item_first_cate_name = x.getAs[String]("item_first_cate_name")
//      val item_second_cate_id = x.getAs[String]("item_second_cate_id")
//      val item_second_cate_name = x.getAs[String]("item_second_cate_name")
//      val item_third_cate_name = x.getAs[String]("item_third_cate_name")
//      val title = x.getAs[String]("title")
//      val head_image = x.getAs[String]("head_image")
//      val sub_image1 = x.getAs[String]("sub_image1")
//      val sub_image2 = x.getAs[String]("sub_image2")
//      val content = x.getAs[String]("content")
//      val title_model_id = x.getAs[String]("title_model_id")
//      val image_model_id = x.getAs[String]("image_model_id")
//      val content_model_id = x.getAs[String]("content_model_id")
//      val ctime = x.getAs[String]("ctime")
//      val title_score = x.getAs[Double]("title_score")
//      val image_score = x.getAs[Double]("image_score")
//      val content_score = x.getAs[Double]("content_score")
//      val score = x.getAs[Double]("score")
//      val publish_time = null
//      val publish_status = null
//      val failure_reason = null
//      val fxhh_article_id = 0
//      val id=x.getAs[String]("id")
//      val platform_des=platform_des_in
//      val pin = null
//      val title_md5 = x.getAs[String]("title_md5")
//      val content_md5 = x.getAs[String]("content_md5")
//      val pic_md5 = x.getAs[String]("pic_md5")
//      val dt = x.getAs[String]("dt")
//      val item_third_cate_id = x.getAs[String]("item_third_cate_id")
//
//
//      var article = new Article(
//        item_sku_id:String,
//        item_id:String,
//        sku_name:String,
//        item_first_cate_id:String,
//        item_first_cate_name:String,
//        item_second_cate_id:String,
//        item_second_cate_name:String,
//        item_third_cate_name:String,
//        title:String,
//        head_image:String,
//        sub_image1:String,
//        sub_image2:String,
//        content:String,
//        title_model_id:String,
//        image_model_id:String,
//        content_model_id:String,
//        ctime:String,
//        title_score:Double,
//        image_score:Double,
//        content_score:Double,
//        score:Double,
//        publish_time:String,
//        publish_status:Integer,
//        failure_reason:String,
//        fxhh_article_id:Integer,
//        id:String,
//        platform_des:String,
//        pin:String,
//        title_md5:String,
//        content_md5:String,
//        pic_md5:String,
//        dt:String,
//        item_third_cate_id:String
//      )
//      retList.add(article)
//    } }
//    spark.stop()
//    return retList;
//  }
//
//  def fetchPublishConfig() : java.util.List[PublishConfig] = {
//    var sqlStr = """
//          select * from app.app_srd_jwriter_fxhh_article_published_config
//                 """
//    val spark = SparkSession.builder().appName("HiveDao").enableHiveSupport().getOrCreate();
//    println("sql is : " + sqlStr);
//    val df = spark.sql(sqlStr);
//
//    val list = df.collectAsList();
//    val retList = new java.util.ArrayList[PublishConfig]();
//    list.toList.foreach { x => {
//      //println("row begin")
//      //println(x)
//      val platform_des = x.getAs[String]("platform_des");
//      val cate_id_and_limit_published = x.getAs[String]("cate_id_and_limit_published");
//      val pin = x.getAs[String]("pin");
//
//
//      var publishConfig = new PublishConfig(
//        platform_des:String,
//        cate_id_and_limit_published:String,
//        pin:String
//      )
//      retList.add(publishConfig)
//    } }
//    spark.stop()
//    return retList;
//  }
//
//  def storePublishedArticles(articles : java.util.List[Article], dt : String, cid3 : String) : Unit = {
//    //println("articles is : " + articles);
//    val spark = SparkSession.builder().appName("HiveDao").enableHiveSupport().getOrCreate();
//    import spark.implicits._;
//    val df = spark.createDataset[Article](articles).toDF()
//    //println("df is : " + df);
//    val tmp = "tmpview_app_srd_jwriter_fxhh_article_published_di";
//    df.createOrReplaceTempView(tmp);
//    //println("df is : " + df);
//    var sqlStr = """
//        insert into table app.app_srd_jwriter_fxhh_article_published_di partition(dt='%s',item_third_cate_id='%s')
//        select %s  from
//                 """
//    sqlStr = String.format(sqlStr, dt, cid3, publishedColunms)
//    sqlStr += tmp
//    println("sql is : " + sqlStr);
//    spark.sql(sqlStr)
//    spark.stop()
//  }
//
//  /* def storeNiceGoodsImages(images : java.util.List[SkuArticle], dt : String) : Unit = {
//     val spark = SparkSession.builder().appName("HiveDao").enableHiveSupport().getOrCreate();
//     import spark.implicits._;
//     val df = spark.createDataset[SkuArticle](images).toDF()
//     val tmp = "tmpview_app_srd_jwritter_fxhh_sku_image_da";
//     df.createOrReplaceTempView(tmp);
//     var sqlStr = """
//         insert overwrite table app.app_srd_jwritter_fxhh_sku_image_da partition(dt='%s')
//         select sku_id,main_image_path,sub_image_path1,sub_image_path2,score,model_id,created from
//     """
//     sqlStr = String.format(sqlStr, dt)
//     sqlStr += tmp
//     println("sql is : " + sqlStr);
//     spark.sql(sqlStr)
//     spark.stop()
//   }*/
//  /*
//  def storeArticles(articles : java.util.List[SkuArticle], dt : String) : Unit = {
//    val spark = SparkSession.builder().appName("HiveDao").enableHiveSupport().getOrCreate();
//    import spark.implicits._;
//    //spark.sql("use tmp")
//    spark.createDataset(articles).toDF();
//    //val newdf = spark.createDataset(articles).toDF();
//  //  newdf.createOrReplaceTempView("tmp_szd_table1");
//  //  newdf.show()
//    spark.sql("insert into tmp.tmp_szd_sku_image partition(dt='2017-11-02') select skuid, img1, img2, img3 from tmp_szd_table1")
//  }
//  */
//  /*def sayHello(x: String): Unit = {
//    println("hello," + x);
//  }*/
//
//  /*def loadData(): Unit = {
//	val sc = new org.apache.spark.SparkContext
//
//    val hiveContext = new org.apache.spark.sql.hive.HiveContext(sc)
//    import hiveContext.implicits._
//    hiveContext.sql("use tmp")
//    val data = sc.textFile("shezhidong/image_check_result_2017-11-01").map(x=>x.split("\\t")).map(x=>Image(x(0),x(1)))
//    data.toDF().registerTempTable("table1")
//    hiveContext.sql("insert into tmp_szd_skuimage partition(dt='2017-11-01') select skuid, imageList from table1")
//  }
//
//  def loadData1() : Unit = {
//    val spark = SparkSession.builder().appName("spark test").enableHiveSupport().getOrCreate();
//    import spark.implicits._;
//    spark.sql("use tmp")
//    val df = spark.sql("select * from tmp_szd_skuimage where dt='2017-11-01'");
//    df.show();
//    val list = df.collectAsList();
//    val retList = new java.util.ArrayList[SkuImage1]();
//    list.toList.foreach { x => {
//      println("row begin")
//      println(x)
//      val skuid = x.getAs[String]("skuid");
//      val imgs = x.getAs[String]("imagelist")
//
//      val arr = imgs.split(";")
//      var img1 = arr.toList.get(0)
//      var img2 = img1
//      if (arr.length > 1) {
//        img2 = arr.toList.get(1);
//      }
//      var img3 = img1
//      if (arr.length > 2) {
//        img3 = arr.toList.get(2);
//      }
//      val skuImage = new SkuImage1(skuid,img1,img2,img3)
//      retList.add(skuImage)
//      println("row end")
//    } }
//    val newdf = spark.createDataset[SkuImage1](retList).toDF();
//    newdf.createOrReplaceTempView("tmp_szd_table1");
//    newdf.show()
//    spark.sql("insert into tmp.tmp_szd_sku_image partition(dt='2017-11-02') select skuid, img1, img2, img3 from tmp_szd_table1")
//
//  }*/
//
//
//
////def main(args:Array[String]):Unit={
////    val sc = new org.apache.spark.SparkContext
////    val hiveContext = new org.apache.spark.sql.hive.HiveContext(sc)
////    import hiveContext.implicits._
////    hiveContext.sql("use DataBaseName")
////    val data = sc.textFile("path").map(x=>x.split("\\s+")).map(x=>Person(x(0),x(1).toInt,x(2)))
////    data.toDF().registerTempTable("table1")
////    hiveContext.sql("insert into table2 partition(date='2015-04-02') select name,col1,col2 from table1")
////}
