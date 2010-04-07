package cn.orz.pascal.gae.framework
import org.specs._
import cn.orz.pascal.gae.framework.RoutingManager._

object RoutingManagerSpec extends Specification {
  "count_matches" should {
      "(/)(/)なら1を返す." in {
         count_matches("/")("/") must_== 1.0
      }

      "(/index)(/index)なら1を返す." in {
         count_matches("/index")("/index") must_== 1.0
      }

      "(/koduki/create)(/koduki/create)なら2を返す." in {
         count_matches("/koduki/create")("/koduki/create") must_== 2.0
      }

      "(/koduki/create)(/${user}/create)なら1.01を返す." in {
         count_matches("/koduki/create")("/${user}/create") must_== 1.01
      }

      "(/)(/koduki/create)なら0を返す." in {
         count_matches("/")("/koduki/create") must_== 0
      }
  }
}
