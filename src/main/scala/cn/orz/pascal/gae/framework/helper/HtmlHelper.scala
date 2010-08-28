// vim: set ts=4 sw=4 et:
package cn.orz.pascal.gae.framework.helper

import scala.xml.XML
import cn.orz.pascal.gae.persist.DataStore._
import cn.orz.pascal.gae.framework.util.DateUtils
object HtmlHelper{
   def $(xml:String) = XML.loadString(xml)
   def html(text:String):scala.xml.Elem = {
        try{
            $(
            "<div>" +
                text.replaceAll("&nbsp;", " ")
                +
            "</div>"
            )
        }catch{
            case e: java.util.NoSuchElementException => {
                $("<div></div>") 
            } 
        }
   }
   def df(date:String) = {
      val d = DateUtils.parse(date) 
      DateUtils.format(d, "yyyy-MM-dd HH:mm") 
   }
   def timestamp(entry:EntityWrapper, name:String) = {
     $("<time datetime='" + entry(Symbol(name)) + "' class='"+ name  +"'>" + df(entry(Symbol(name)))  + "</time>")
   }
   def url_for(url:String) = {
      "/muse/pascal" + url
   }
}
