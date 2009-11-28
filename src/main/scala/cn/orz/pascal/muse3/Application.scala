package cn.orz.pascal.muse3

import cn.orz.pascal.gae.persist.DataStore._
import cn.orz.pascal.gae.persist._
import cn.orz.pascal.gae.framework.AbstractRoute
import cn.orz.pascal.gae.framework.Global

object Application extends AbstractRoute{
   case class Entry(body:String)
   def template(body:scala.xml.NodeBuffer) = {
      <html lang="ja">
          <head>
             <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
             <title>ブログなんだよもん</title>
          </head>
          <body>{body}</body>
      </html>
   
   }

   get("/"){(req, res, $) =>  $.forward( "/index.html" )}
   get("/favicon.ico"){(req, res, $) =>  <icon />}

   get("/index.html"){ (req, res, $) =>
      val params = req.params
      val entries = DataStore from ('entry) asList()
      template(
         <h1>ブログなんだよもん</h1>
         <p>
            <a href="new.html">投稿</a>
         </p>

         <div>
            {for(entry <- entries) yield {
            <div>
               <h2>{entry('title)}</h2>
               <p>{entry('date)}</p>
               <p>{entry('contents)}</p>
            </div> 
            }}
         </div>
      )
    
   }

   get("/new.html"){(req, res, $) =>
      template(
          <h1>ブログなんだよもん - 投稿</h1>
          <form method="post" action="entry">
            <fieldset>
               <legend>ここに入力</legend>
               <p>
                  <input name="year" size="5" value="2009" tabindex="1"/>-
                  <input name="month" size="3" value="9" tabindex="2"/>-
                  <input name="day" size="3" value="9" tabindex="3"/>
                  <input name="title" size="40" value="a" tabindex="4"/>
               </p>
               <div>タグ:</div>
               <input id="tags" name="tags" type="text" />
               <div>
                  閲覧範囲:
                  <select name="scope">
                     <option value="private" selected="true">非公開</option>
                     <option value="public">公開</option>
                  </select>
                  |<a href='/muse/pascal/upload.html'>アップローダ</a>|
                  |アップローダ|<a href='/muse/help.htm'>カスタムタグ一覧</a>|
               </div>
               <input type="hidden" name="user" value="pascal"/>
               <div>内容:</div>
               <textarea id="contents" name="contents" cols="100" rows="25"></textarea>
            </fieldset>
            <input type="submit" value="書き込む"/>
         </form>
       )
   }

   post("/entry"){(req, res, $) =>
      val params = req.params
      DataStore.put(Entity('entry ,('title, params("title")),
                                  ('contents, params("contents")),
                                  ('date, (new java.util.Date()).toString)))
      $.redirect( "/index.html" )
   }
 
}
