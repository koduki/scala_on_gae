package cn.orz.pascal.gae.framework

import cn.orz.pascal.gae.framework.wrapper._
import scala.collection.jcl.Conversions.convertMap

class Environment(_req:Request, _res:Response, 
                  _params:Map[String, String]){
   val req = _req.src
   val res = _res.src
   val pms = _params ++ convertMap(_req.params.src).foldLeft(Map[String, String]())(
                                    (r, x) => r + ((x._1.toString, x._2.asInstanceOf[Array[String]](0).toString)))

   def request() = _req
   def response() = _res
   def params = pms

   def flash(name:String, value:Any):Unit = {
      _req.session.setAttribute(name, value)
   }
   def flash(name:String):Any = {
      val result = _req.session.getAttribute(name)
      _req.session.removeAttribute(name)
      result
   }

   def forward(location:String) = {
      req.getRequestDispatcher(location).forward(req, res)
      <forward />
   }  
   def redirect(location:String) = {
     res.sendRedirect(location); 
     <redirect /> 
   }
}
