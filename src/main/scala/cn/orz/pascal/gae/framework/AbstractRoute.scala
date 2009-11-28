package cn.orz.pascal.gae.framework

import cn.orz.pascal.gae.framework.wrapper.{Request, Response}
import cn.orz.pascal.gae.framework.RouteTable.routes
import cn.orz.pascal.gae.framework.Global

abstract class AbstractRoute {
   val DataStore = cn.orz.pascal.gae.persist.DataStore
   type Response = cn.orz.pascal.gae.framework.wrapper.Response
   type Request = cn.orz.pascal.gae.framework.wrapper.Request

   import scala.xml.XML
   def $(xml:String) = XML.loadString(xml)
  
   def get(url:String)(body:(Request, Response, Global) => scala.xml.Elem) = routes.put(('GET, url), body)
   def post(url:String)(body:(Request, Response, Global) => scala.xml.Elem) = routes.put(('POST, url), body)
   def put(url:String)(body:(Request, Response, Global) => scala.xml.Elem) = routes.put(('PUT, url), body)
   def delete(url:String)(body:(Request, Response, Global) => scala.xml.Elem) = routes.put(('DELETE, url), body)
}
