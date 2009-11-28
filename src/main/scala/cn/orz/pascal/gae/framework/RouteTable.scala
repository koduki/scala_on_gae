package cn.orz.pascal.gae.framework

import cn.orz.pascal.gae.framework.wrapper._
import cn.orz.pascal.gae.framework.Global
import scala.collection.mutable.HashMap

object RouteTable {
	val routes = new HashMap[(Symbol, String), (Request, Response, Global) => scala.xml.Elem]	
}
