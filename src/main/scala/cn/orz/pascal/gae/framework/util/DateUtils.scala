// vim: set ts=4 sw=4 et:
package cn.orz.pascal.gae.framework.util
import java.util.Date
import java.text.SimpleDateFormat
import java.util.TimeZone

object DateUtils{
  val HTML5_FORMAT="yyyy-MM-dd'T'HH:mm:ssZ"

  def format(date:Date):String = format(date, HTML5_FORMAT)
  def format(date:Date, format:String):String = {
     val df = new SimpleDateFormat(format)
     df.setTimeZone(TimeZone.getTimeZone("JST"))
     df.format(date)                                 
  }

  def parse(text:String):Date = parse(text, HTML5_FORMAT)
  def parse(text:String, format:String):Date = {
     val df = new SimpleDateFormat(format)
     df.setTimeZone(TimeZone.getTimeZone("JST"))
     df.parse(text)       
  }
}
