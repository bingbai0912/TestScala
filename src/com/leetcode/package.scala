package com

/**
  * Created by hadoop on 9/12/17.
  */
package object leetcode {
  def main(args: Array[String]) {
    //[ [1,2], [2,3], [3,4], [1,3] ]
//    val input = Array(new Interval(1,2),new Interval(2,3),new Interval(3,4),new Interval(1,3))
        val input = Array(new Interval(1,2),new Interval(1,2),new Interval(1,2))
    val result = Solution.eraseOverlapIntervals(input)
    println(result)
  }
}
