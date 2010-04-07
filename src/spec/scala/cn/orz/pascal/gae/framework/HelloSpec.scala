package cn.orz.pascal.gae.framework
import org.specs._

object helloWorld extends Specification {
   "'hello world' has 11 characters" in {
        "hello world".size must_== 11
    }
    "'hello world' matches 'h.* w.*'" in {
      "hello world" must be matching("h.* w.*")
    }

}
