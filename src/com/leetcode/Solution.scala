package com.leetcode

/**
  * Created by hadoop on 9/12/17.
  */
object Solution {
  def eraseOverlapIntervals(intervals: Array[Interval]): Int = {
    var count = 1
    if (intervals.length==0) return 0
    val orderedIntervals = intervals.sortBy( interval=>interval.end )
    val first = orderedIntervals(0)
    var end = first.end
    for(i<-1 until orderedIntervals.length){
      val interval = orderedIntervals(i)
      if(interval.start>=end){
        end = interval.end
        count+=1
      }
    }
    orderedIntervals.length-count
  }
}

//Definition for an interval.
class Interval(var _start: Int = 0, var _end: Int = 0) {
  var start: Int = _start
  var end: Int = _end
}
