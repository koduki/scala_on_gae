package cn.orz.pascal.gae.framework

import cn.orz.pascal.gae.framework.wrapper._
import scala.collection.mutable.HashMap

object RouteTable {
   val routes = new HashMap[(Symbol, String), Environment => scala.xml.Elem] 
}
