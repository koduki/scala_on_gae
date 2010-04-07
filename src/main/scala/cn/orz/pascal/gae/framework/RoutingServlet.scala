package cn.orz.pascal.gae.framework

import javax.servlet.http.{HttpServlet, HttpServletRequest, HttpServletResponse}

import cn.orz.pascal.gae.framework.wrapper._
import cn.orz.pascal.gae.framework.RoutingManager._
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
}
