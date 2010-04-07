package cn.orz.pascal.muse3

import cn.orz.pascal.gae.persist.DataStore._
import cn.orz.pascal.gae.persist._
import cn.orz.pascal.gae.framework.{AbstractRoute, Environment}
import cn.orz.pascal.gae.framework.helper.HtmlHelper._

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

   get("/favicon.ico"){env =>  <icon />}
   get("/"){(env) =>  env.forward( "/index.html" )}
   get("/index.html"){env =>
      import env._
      val offset = if (params.contains("offset")) params("offset").toInt else 0
      val entries = DataStore from ('entry) sortDesc('created_at) limit(offset, 20) asIterator

      template(
         <h1>ブログなんだよもん</h1>
         <p>
            <a href="edit.html">投稿</a>
         </p>
         <p>
           
           </p>
         <div>
            {for(entry <- entries) yield {
            <div>
               <h2>{$("<a href='/" + entry.key.getId +  ".html'>" + entry('title) + "</a>")}</h2>
               <p>{entry('date)}</p>
               <p>{html(entry('contents).toString)}</p>
            </div> 
            }}
         </div>
         <p>
           {$("<a href='index.html?offset=" + (if (offset > 10) (offset - 10) else 0)  +"'>前へ</a>")}
           {$("<a href='index.html?offset=" + (offset + 10)  +"'>次へ</a>")}
         </p>
      )
   }

   get("/${id}.html"){env =>
      import env._
      val entry = DataStore get('entry, params("id"))
      val comments = DataStore from ('comment, entry.key) asList()
      template(
         <h1>ブログなんだよもん</h1>
         <p>
         </p>
         <p>id :{params("id")}</p>
         <p>{entry('title)}</p>
         <p>{entry('contents)}</p>
         <p>{entry('date)}</p>
         <div>
            <ul>
            {for(comment <- comments) yield {
              <li><span>{comment('name)}</span> : <span>{comment('body)}</span></li>
            }}
            </ul>
         </div> 
 
         <span>{$("<a href='/edit.html?id=" + params("id") + "'>編集</a>")}</span>
         <form method="post" action="/comment/create.do">
           {$("<input type='hidden' name='parent' value='" + params("id") + "' />")}
           <input name="name"/> : <input name="body"/> <input type="submit"/>
         </form>
      )
   }

   get("/edit.html"){env =>
      import env._
      val entry = if(params.contains("id")){
                     val id = params("id")
                     request.session.setAttribute("id", id)

                     DataStore get('entry, id)
                  }else{
                     Entity('entry ,('title, ""),
                                    ('contents, ""),
                                    ('date, (new java.util.Date()).toString))
                  }
      template(
          <h1>ブログなんだよもん - 編集</h1>
          <form method="post" action="/entry/create.do">
            <fieldset>
               <legend>ここに入力</legend>
               <p>{$("<input name='title' value='" +  entry('title) + "' />")}</p>
               <div>内容:</div>
               <textarea id="contents" name="contents">{entry('contents)}</textarea>
            </fieldset>
            <input type="submit" value="書き込む"/>
         </form>
       )
   }

   get("/logout"){(env) =>
      import com.google.appengine.api.users.UserServiceFactory
      val userService = UserServiceFactory.getUserService
      template(
         <h2>logout</h2>
         <span>{$("<a href='" +  userService.createLogoutURL("/") + "'>ログアウト</a>")}</span>
      )
   }

   post("/entry/create.do"){(env) =>
      import env._
      println("iddd;" + request.attribute("id") )
      val entry = if(request.session.getAttribute("id") != null){
                     val id = request.session.getAttribute("id").toString
                     DataStore get('entry, id)
                  }else{
                     Entity('entry,('title, ""),
                                   ('contents, ""),
                                   ('created_at, (new java.util.Date()).toString))
                  }

      entry.put('title, request.params("title"))
      entry.put('contents, request.params("contents"))

      DataStore.put(entry)
      redirect( "/index.html" )
   }

   post("/comment/create.do"){(env) =>
      import env._
      val parent = Key('entry, request.params("parent"))
      val comment = Entity('comment ,parent)  += (
                              ('name, request.params("name")), 
                              ('body, request.params("body")), 
                              ('created_at, (new java.util.Date()).toString))
      DataStore.put(comment)
      redirect( "/" + request.params("parent") + ".html" )
   }
}
