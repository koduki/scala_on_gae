package cn.orz.pascal.gae.framework

import javax.servlet.http.{HttpServlet, HttpServletRequest, HttpServletResponse}

import cn.orz.pascal.gae.framework.wrapper._
import cn.orz.pascal.gae.framework.RoutingManager._
import cn.orz.pascal.gae.framework.RouteTable.routes
import java.util.logging.Logger;
 
class RoutingServlet extends HttpServlet {
   val log = Logger.getLogger("cn.orz.pascal.muse");

   override def service(req : HttpServletRequest, res : HttpServletResponse) = {
      req.setCharacterEncoding("UTF-8")
      
      val uri = req.getRequestURI
      val method = req.getMethod.toUpperCase match{
         case "GET" => 'GET
         case "POST"=>'POST
         case "PUT" => 'PUT
         case "DELETE" => 'DELETE
      }

      try{
        val (result, params) = dispatch(routes, method, uri)
        val output = result(new Environment(new Request(req), new Response(res), params))

        res.setCharacterEncoding("UTF-8");
        res.setContentType("text/html; charset=UTF-8");

        log.info( req.getRequestURI )
        if(output != null) { 
          res.getWriter().println(
            output .toString
                   .replaceAll("<br></br>", "<br />") ) 
        }
      }catch{
        case e: java.util.NoSuchElementException => {
          log.info(e.getMessage())
          res.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
      }
   }
}
