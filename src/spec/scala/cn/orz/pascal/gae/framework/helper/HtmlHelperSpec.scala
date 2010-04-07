package cn.orz.pascal.gae.framework.helper
import org.specs._
import cn.orz.pascal.gae.framework.helper.HtmlHelper._

object HtmlHelperSpec extends Specification {
  "html" should {
      "(Hello World)なら(Hello World)を返す." in {
         html("Hello World") must_== "Hello World"
      }
      """(Hello\n World)なら(Hello<br />\n World)を返す.""" in {
        html("Hello\n World") must_== "Hello<br />\n World"
      }
      """(Hello<br /> World)なら(Hello<br /> World)を返す.""" in {
        html("Hello<br /> World") must_== "Hello<br /> World"
      }
  }
}
