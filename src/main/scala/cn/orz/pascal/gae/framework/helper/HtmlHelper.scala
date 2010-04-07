// vim: set ts=4 sw=4 et:
package cn.orz.pascal.gae.framework.helper
import scala.xml.XML

object HtmlHelper{
   def html(text:String):scala.xml.Elem = {
        XML.loadString(
        "<p>" +
            text.replaceAll("\r", "")
                .replaceAll("\n", "<br />\n")  
              +
        "</p>"
            ) 
   }
}
