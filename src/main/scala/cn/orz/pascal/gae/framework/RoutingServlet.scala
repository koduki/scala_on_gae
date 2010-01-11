package cn.orz.pascal.gae.framework

import javax.servlet.http.{HttpServlet, HttpServletRequest, HttpServletResponse}

import scala.collection.mutable.HashMap
import scala.xml.Elem

import cn.orz.pascal.gae.framework.wrapper._
import cn.orz.pascal.gae.framework.RouteTable.routes

class RoutingServlet extends HttpServlet {
   override def service(req : HttpServletRequest, res : HttpServletResponse) = {
      req.setCharacterEncoding("UTF-8")
      
      val uri = req.getRequestURI
      val method = req.getMethod.toUpperCase match{
         case "GET" => 'GET
         case "POST"=>'POST
         case "PUT" => 'PUT
         case "DELETE" => 'DELETE
      }

      val (result, params) = dispatch(routes, method, uri)
      val output = result(new Environment(new Request(req), new Response(res), params))

      res.setCharacterEncoding("UTF-8");
      res.setContentType("text/html; charset=UTF-8");

      res.getWriter().println( req.getRequestURI )
      if(output != null) { res.getWriter().println( output ) }
   }

   def mapping(route:String, url:String) = {
      val regx = """\$\{(.+?)\}"""
      def blank(n:Int) = (1 to n).map(x =>"").toList
      def extract(name:String) = (regx).r.
         findFirstMatchIn(name) match {
           case Some(s) => s.group(1)
           case None => "" 
         }

      def rlist(s1:String, s2:String) = { 
          def intDiff(x:Int, y:Int) = Math.abs(x - y)
          ((if (s1.size < s2.size) blank(intDiff(s1.size, s2.size)) 
            else blank(0)) ::: s1.toList.map((x) => x.toString )).
          reverse
      }

      def diff(s1:String, s2:String) = {
         val xs = rlist(s1, s2).zip(rlist(s2, s1)) 
         xs.filter(x => x._1 != x._2).reverse.
            foldLeft(""){(r, x) => r + x._1}
      }
 
      route.split("/").zip(url.split("/")).
         filter(x => x._1.matches(".*" + regx + ".*")).
         map(x => (extract(x._1), diff(x._2, x._1))).
         foldLeft(Map[String, String]()){(r, x) => r + x}
   }

   def count_matches(url1:String)(url2:String) = {
      val predicate = (x:String, y:String) => 
            x == y || y.matches(""".*\$\{.+?\}.*""")

      url1.split("/").zip(url2.split("/")).
         foldLeft(0){(r:Int, x:(String, String)) => 
            r + (if (predicate(x._1, x._2)) 1 else 0)
         }
   }

   def dispatch(table:HashMap[(Symbol, String), Environment => Elem], method:Symbol, url:String) = {
      val count = count_matches(url)_
      val routes = table.keys.toList.filter(x => x._1 == method).map(x => x._2)
      val route = routes.sort((x, y) => count(x) > count(y)).head 
      val params = mapping(route, url)

      (table(method, route), params)
   }
}
