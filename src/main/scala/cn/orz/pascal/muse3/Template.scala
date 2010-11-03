package cn.orz.pascal.muse3

import scala.xml.Elem
import cn.orz.pascal.gae.framework.helper.HtmlHelper._
import cn.orz.pascal.gae.framework.helper.GAEHelper._

object Template {
   def apply(title:String)(body:Elem):Elem = apply("", title)(body)
   def apply(id:String, title:String)(body:Elem):Elem = {
<html xmlns="http://www.w3.org/1999/xhtml" lang="ja">
  <head>
    <meta charset="utf-8" />
    <link rel="stylesheet" type="text/css" href="http://blognandayomon.appspot.com/css/style.css" media="screen" />
    <script type="text/javascript" src="http://blognandayomon.appspot.com/javascripts/ckeditor/ckeditor.js"></script>
    <title>ブログなんだよもん - {title}</title>
  </head>
  <body>
    <header class="global">
      <h1>ブログなんだよもん</h1>
      <nav>
        <ul>
          <li><a href="/muse/pascal/">Top</a></li>
          {if (isAdmin()) {
          <li><a href='/muse/pascal/edit.html'>Create</a></li>
          <li>{$("<a href='/muse/pascal/edit.html?id=" + id + "'>Edit</a>")}</li>
          <li><a href='/muse/pascal/logout.html'>Logout</a></li>
          } else ""}
        </ul>
      </nav>
      <!--
      <form action="../.">
        <fieldset>
          <legend>Search</legend>
          <input type="search" class="text" name="search" title="Enter search word or phrase" />
          <input type="submit" class="submit" value="Search" />
        </fieldset>
      </form>
      -->
    </header>

    <div id="body">
       {body}
       <aside id="sidebar">
          <div>
             <h2>Profile</h2>
             <p><strong>名前:</strong>紅月
             <img src="http://blognandayomon.appspot.com/images/icon.jpg"/><br/>
             <strong>自己紹介:</strong>GentooやScala/JavaやRubyが好きなどこにでも居る普通の社会人。最近PHPに対してはツンデレ気味。 </p>
          </div>
          <nav>
             <h2>Links</h2>
             <ul>
                <li><a href="http://twitter.com/koduki">Twitter</a></li>
                <li><a href="http://github.com/koduki">GitHub</a></li>
                <li><a href="http://pascalmk.clipp.in/">clipp</a></li>
             </ul>
         </nav>
       </aside>
    </div>

    <footer class="global">
      <p>Powered by koduki factory</p>
    </footer>
  </body>
</html>
   }

}
