package cn.orz.pascal.gae.framework

import javax.servlet.http.{HttpServlet, HttpServletRequest, HttpServletResponse}

import cn.orz.pascal.gae.framework.wrapper._
import cn.orz.pascal.gae.framework.RouteTable.routes
import cn.orz.pascal.gae.framework.Global

class RoutingServlet extends HttpServlet {
   override def service(req : HttpServletRequest, res : HttpServletResponse) = {
      req.setCharacterEncoding("UTF-8")
      
      val output = req.getMethod.toUpperCase match{
         case "GET" => routes(('GET, req.getRequestURI))( new Request(req), new Response(res), new Global(req, res))
         case "POST" => routes(('POST, req.getRequestURI))( new Request(req), new Response(res), new Global(req, res))
         case "PUT" => routes(('PUT, req.getRequestURI))( new Request(req), new Response(res), new Global(req, res))
         case "DELETE" => routes(('DELETE, req.getRequestURI))( new Request(req), new Response(res), new Global(req, res))
      }
      
      res.setCharacterEncoding("UTF-8");
      res.setContentType("text/html; charset=UTF-8");

      res.getWriter().println( req.getRequestURI )
      if(output != null) { res.getWriter().println( output ) }
   }
   
}
