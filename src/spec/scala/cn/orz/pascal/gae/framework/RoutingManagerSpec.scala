package cn.orz.pascal.gae.framework
import org.specs._
import cn.orz.pascal.gae.framework.RoutingManager._

object RoutingManagerSpec extends Specification {
  "count_matches" should {
      "(/index)(/${id}.html)なら0を返す." in {
         count_matches("/index")("/${id}.html") must_== 0
      }

     "(/100.html)(/${id}.html)なら1を返す." in {
         count_matches("/100.html")("/${id}.html") must_== 1
      }

      "(/index)(/index)なら100を返す." in {
         count_matches("/index")("/index") must_== 100
      }

      "(/koduki/create)(/koduki/create)なら200を返す." in {
         count_matches("/koduki/create")("/koduki/create") must_== 200
      }

      "(/koduki/create)(/${user}/create)なら101を返す." in {
         count_matches("/koduki/create")("/${user}/create") must_== 101
      }

      "(/koduki/delete)(/${user}/create)なら0を返す." in {
         count_matches("/koduki/delete")("/${user}/create") must_== 0
      }

      "(/)(/koduki/create)なら0を返す." in {
         count_matches("/")("/koduki/create") must_== 0
      }
  }
}
