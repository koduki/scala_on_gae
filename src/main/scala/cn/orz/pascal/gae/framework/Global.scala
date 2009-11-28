package cn.orz.pascal.gae.framework

import javax.servlet.http.{HttpServlet, HttpServletRequest, HttpServletResponse}

class Global(req:HttpServletRequest, res:HttpServletResponse){
	def forward(location:String) = {
		req.getRequestDispatcher(location).forward(req, res)
		<forward />
	}	

  	def redirect(location:String) = {res.sendRedirect(location); <redirect /> }
}