package cn.orz.pascal.gae.framework
import org.specs._
import cn.orz.pascal.gae.framework.RoutingManager._

object RoutingManagerSpec extends Specification {
  "count_matches" should {
      "(/)(/)$B$J$i(B1$B$rJV$9(B." in {
         count_matches("/")("/") must_== 1.0
      }

      "(/index)(/index)$B$J$i(B1$B$rJV$9(B." in {
         count_matches("/index")("/index") must_== 1.0
      }

      "(/koduki/create)(/koduki/create)$B$J$i(B2$B$rJV$9(B." in {
         count_matches("/koduki/create")("/koduki/create") must_== 2.0
      }

      "(/koduki/create)(/${user}/create)$B$J$i(B1.01$B$rJV$9(B." in {
         count_matches("/koduki/create")("/${user}/create") must_== 1.01
      }

      "(/)(/koduki/create)$B$J$i(B0$B$rJV$9(B." in {
         count_matches("/")("/koduki/create") must_== 0
      }
  }
}
