package cn.orz.pascal.gae.framework
import org.specs._
import cn.orz.pascal.gae.framework.RoutingManager._

object RoutingManagerSpec extends Specification {
  "count_matches" should {
      "(/index)(/${id}.html)$B$J$i(B0$B$rJV$9(B." in {
         count_matches("/index")("/${id}.html") must_== 0
      }

     "(/100.html)(/${id}.html)$B$J$i(B1$B$rJV$9(B." in {
         count_matches("/100.html")("/${id}.html") must_== 1
      }

      "(/index)(/index)$B$J$i(B100$B$rJV$9(B." in {
         count_matches("/index")("/index") must_== 100
      }

      "(/koduki/create)(/koduki/create)$B$J$i(B200$B$rJV$9(B." in {
         count_matches("/koduki/create")("/koduki/create") must_== 200
      }

      "(/koduki/create)(/${user}/create)$B$J$i(B101$B$rJV$9(B." in {
         count_matches("/koduki/create")("/${user}/create") must_== 101
      }

      "(/koduki/delete)(/${user}/create)$B$J$i(B0$B$rJV$9(B." in {
         count_matches("/koduki/delete")("/${user}/create") must_== 0
      }

      "(/)(/koduki/create)$B$J$i(B0$B$rJV$9(B." in {
         count_matches("/")("/koduki/create") must_== 0
      }
  }
}
