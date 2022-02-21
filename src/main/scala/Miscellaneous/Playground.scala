package Miscellaneous
import java.util.Random
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.SparkContext._
import org.apache.spark.rdd.RDD

/**
 * Usage: GroupByTest [numMappers] [numKVPairs] [valSize] [numReducers]
 */
object Playground {
  def main(args: Array[String]) {
    val sparkConf = new SparkConf().setAppName("GroupBy Test")
    
    val sc = new SparkContext(sparkConf)
    
    val pairs1: RDD[(Int, Array[Byte])] = sc.parallelize(0 until 100, 100).flatMap { p =>
      val ranGen = new Random
      val arr1 = new Array[(Int, Array[Byte])](10000)
      for (i <- 0 until 10000) {
        val byteArr = new Array[Byte](1000)
        ranGen.nextBytes(byteArr)
        arr1(i) = (ranGen.nextInt(Int.MaxValue), byteArr)
      }
      arr1
    }.cache
    // Enforce that everything has been calculated and in cache
    pairs1.count
    
    println(pairs1.groupByKey(36).count)
    
    sc.stop()
  }
}