package cn.orz.pascal.gae.framework.helper
import org.specs._
import cn.orz.pascal.gae.framework.helper.HtmlHelper._

object HtmlHelperSpec extends Specification {
  "html" should {
      "(Hello World)$B$J$i(B(Hello World)$B$rJV$9(B." in {
         html("Hello World") must_== "Hello World"
      }
      """(Hello\n World)$B$J$i(B(Hello<br />\n World)$B$rJV$9(B.""" in {
        html("Hello\n World") must_== "Hello<br />\n World"
      }
      """(Hello<br /> World)$B$J$i(B(Hello<br /> World)$B$rJV$9(B.""" in {
        html("Hello<br /> World") must_== "Hello<br /> World"
      }
  }
}
