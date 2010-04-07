package cn.orz.pascal.gae.framework

import scala.collection.mutable.HashMap
import scala.xml.Elem

object RoutingManager{
   import java.util.logging.Logger;
   val log = Logger.getLogger("cn.orz.pascal.muse");
 
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

   def count_matches(url1:String)(url2:String):Double = {
      val eq2 = (x:String, y:String) => 
            (x != "" && y != "" ) &&
            (x == y)
      
      val like = (x:String, y:String) => 
            (x != "" && y != "" ) &&
            (y.matches(""".*\$\{.+?\}.*"""))

 
      val xs = url1.split("/").zip(url2.split("/"))
      if(url1 == "/" && url2 == "/"){
         1
      }else{
         xs.foldLeft(0.0){(r:Double, x:(String, String)) => 
            r + (if (eq2(x._1, x._2)) 1.0 
                 else if(like(x._1, x._2)) 0.01
                 else 0.0)
         }
      }
   }

   def dispatch(table:HashMap[(Symbol, String), Environment => Elem], method:Symbol, url:String) = {
      val count = count_matches(url)_
      val routes = table.keys.toList.filter(x => x._1 == method).map(x => x._2)
log.info("routings = " + routes.toString)

      val route = routes.sort((x, y) => count(x) > count(y)).head 
      val params = mapping(route, url)

      (table(method, route), params)
   }
}

