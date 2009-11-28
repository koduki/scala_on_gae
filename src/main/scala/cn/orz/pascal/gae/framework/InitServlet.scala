package cn.orz.pascal.gae.framework
import javax.servlet.http.HttpServlet
import cn.orz.pascal.muse3.Application

class InitServlet extends HttpServlet {
  override def init() { Application }
}
