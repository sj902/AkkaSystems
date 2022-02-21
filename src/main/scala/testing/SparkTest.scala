package testing

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

object SparkTest extends App {
  val spark = SparkSession.builder
    .master("local[*]")
    .appName("Spark Word Count")
    .getOrCreate()
  
  val lines = spark.sparkContext.parallelize(
    Seq("Spark Intellij Idea Scala test one",
      "Spark Intellij Idea Scala test two",
      "Spark Intellij Idea Scala test three"))
  
  def counts: RDD[(String, Int)] = {
    lines
    .flatMap(line => line.split(" "))
    .map(word => (word, 1))
    .reduceByKey(_ + _)
  }
  
  val a = counts
  
  val b = counts
  
  a.foreach(println)
}
