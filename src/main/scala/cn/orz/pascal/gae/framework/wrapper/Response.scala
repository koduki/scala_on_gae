package cn.orz.pascal.gae.framework.wrapper

import javax.servlet.http.{HttpServletResponse ,HttpServletRequest}
import javax.servlet.http.Cookie
import java.util.Locale

class Response(val res:HttpServletResponse){
  def response:HttpServletResponse = res
   
  // ServletResponse
  def characterEncoding = res.getCharacterEncoding()
  def characterEncoding_=(enc: String) = res.setCharacterEncoding(enc)

  def contentType_=(typ: String) = res.setContentType(typ)

  def outputStream = res.getOutputStream()
  def writer = res.getWriter()
  def contentLength_=(len: Int) = res.setContentLength(len)

  def committed = res.isCommitted()

  def locale = res.getLocale()
  def locale_=(locale: Locale) = res.setLocale(locale)
    
  def flush() = res.flushBuffer()
  def reset() = res.reset()
    
  // HttpServletResponse
  def cookie(cookie:Cookie) = res.addCookie(cookie)
  def addDateHeader(name:String, date:long) = res.addDateHeader(name, date)
  def dateHeader_=(name:String, date:Long) = res.setDateHeader(name, date)
  def addHeader(name:String, value:String) = res.addHeader(name, value)
  def header(name:String, value:String) = res.setHeader(name, value)
    
  def addInitHeader(name:String, value:Int) = res.addIntHeader(name, value)
  def containsHeader(name:String) = res.containsHeader(name)
  def encodeRedirectURL(url:String) = res.encodeRedirectURL(url)
  def encodeURL(url:String) = res.encodeURL(url)
  
  def sendError(sc:Int) = {res.sendError(sc); <error />}
  def sendError(sc:Int, msg:String) = {res.sendError(sc, msg); <error />}
  def redirect(location:String) = {res.sendRedirect(location); <redirect /> }
        
  def initHeader(name:String, value:Int) = res.setIntHeader(name, value)
  def status_=(sc:Int) = res.setStatus(sc)  
}
