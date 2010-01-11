package cn.orz.pascal.gae.framework.wrapper
import javax.servlet.http.HttpServletRequest
import scala.collection.mutable.Map
import scala.collection.jcl.Conversions._
import com.google.appengine.api.datastore._

class Request(req:HttpServletRequest){
   def src:HttpServletRequest = req

   // ServletRequest
   def attribute(name:String) = req.getAttribute(name)
   def attributeNames() = req.getAttributeNames
   def characterEncoding = req.getCharacterEncoding()
   def contentLength = req.getContentLength()
   def contentType = req.getContentType()
   def inputStream = req.getInputStream()
   def locale = req.getLocale()
   def locales = req.getLocales()
   def parameter(name:String) = req.getParameter(name)
   def params = new {
      val src = req.getParameterMap
      def apply(key:String):String = if (src.containsKey(key))   (src.get(key)).asInstanceOf[Array[String]](0)   else ""
   }
   def protocol = req.getProtocol()
   def remoteAddr = req.getRemoteAddr()
   def remoteHost = req.getRemoteHost()
   
   def scheme = req.getScheme()
   def serverName = req.getServerName()
   def serverPort = req.getServerPort()
   def secure = req.isSecure()
   
   // HttpServletRequest
   def authType = req.getAuthType()
   def contextPath = req.getContextPath()
   def cookies = req.getCookies()
   
   def header(name:String) = req.getHeader(name)
   def headerNames() = req.getHeaderNames()
   def dateHeader(name: String) = req.getDateHeader(name)
   def intHeader(name:String) = req.getIntHeader(name)

   def method = req.getMethod.toUpperCase()
   def uri = req.getRequestURI()
   def session = req.getSession()
   
   def pathInfo = req.getPathInfo()
   def pathTranslated = req.getPathTranslated()
   def queryString = req.getQueryString()
   def remoteUser = req.getRemoteUser()
   def sessionId = req.getRequestedSessionId()

   def servletPath = req.getServletPath()
   def userPrincipal = req.getUserPrincipal()
   def userInRole(role:String) = req.isUserInRole(role)
      
   def sessionIdValid = req.isRequestedSessionIdValid()
   def sessionIdFromCookie = req.isRequestedSessionIdFromCookie()
   def sessionIdFromURL = req.isRequestedSessionIdFromURL()
}
