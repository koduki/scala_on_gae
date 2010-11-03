package cn.orz.pascal.muse3

import cn.orz.pascal.gae.persist.DataStore._
import cn.orz.pascal.gae.persist._
import cn.orz.pascal.gae.framework.{AbstractApplication, Environment}
import cn.orz.pascal.gae.framework.helper.HtmlHelper._
import cn.orz.pascal.gae.framework.util.DateUtils
import com.google.appengine.api.datastore.Text
import java.util.Date

object Application extends AbstractApplication{
   get("/favicon.ico"){env =>  <icon />}
   get("/"){(env) =>  env.redirect( "/muse/pascal/" )}
   get("/index.html"){(env) =>  env.redirect( "/muse/pascal/" )}
   get("/muse/pascal/"){(env) =>  env.forward( "/muse/pascal/index.html" )}
   get("/muse/pascal/view.html"){(env) =>  env.redirect( "/muse/pascal/" )}
   get("/muse/pascal/index.html"){env =>
      import env._
      val offset = if (params.contains("offset")) params("offset").toInt else 0
      val entries = DataStore from ('entry) sortDesc('created_at) limit(offset, 10) asIterator

      Index(offset, entries)
   }

   get("/muse/pascal/${id}.html"){env =>
      import env._
      val id = params("id")
      val entry = DataStore get('entry, id)
      val comments = DataStore from ('comment, entry.key) asList()

      Show(id, entry, comments)
   }
　
   get("/edit.html"){env =>
      Template("edit"){$
          <form method="post" action="/create.do">$
            <fieldset>$
               <legend>ここに入力</legend>$
               <p>{$("<input name='title' value='" +  entry('title) + "' />")}</p>$
               <div>内容:</div>$
               <textarea class="ckeditor" name="contents">{entry('contents)}</textarea>$
            </fieldset>$
            <input type="submit" value="書き込む"/>$
         </form>$
      }$
   }

   get("/muse/pascal/edit.html"){env =>
      import env._
      val entry = if(params.contains("id")){
                     val id = params("id")
                     flash("id", id)

                     DataStore get('entry, id)
                  }else{
                     Entity('entry ,('title, ""),
                                    ('contents, ""),
                                    ('date, DateUtils.format(new Date())))
                  }
      Template("edit"){
          <form method="post" action="/muse/pascal/entry/create.do">
            <fieldset>
               <legend>ここに入力</legend>
               <p>{$("<input name='title' value='" +  entry('title) + "' />")}</p>
               <div>内容:</div>
               <textarea class="ckeditor" name="contents">{entry('contents)}</textarea>
            </fieldset>
            <input type="submit" value="書き込む"/>
         </form>
      }
   }

   get("/muse/pascal/delete.html"){env =>
      import env._
      val id = params("id")
      flash("id", id)

      Template("delete"){
         <p> 
            <h2>本当に削除しますか？</h2>
            <form method="post" action="/muse/pascal/entry/delete.do">
               <input type="submit" value="OK"/>
            </form>
         </p>
      }
   }

   get("/muse/pascal/logout.html"){(env) =>
      import com.google.appengine.api.users.UserServiceFactory
      val userService = UserServiceFactory.getUserService
      Template("logout")(
         <p>
           <h2>logout</h2>
           <span>{$("<a href='" +  userService.createLogoutURL("/") + "'>ログアウト</a>")}</span>
         </p>
      )
   }

   post("/muse/pascal/entry/create.do"){(env) =>
      import env._
      val id = flash("id")
      val entry = if(id != null){
                     DataStore get('entry, id.toString)
                  }else{
                     Entity('entry,('title, ""),
                                   ('contents, ""),
                                   ('created_at, DateUtils.format(new Date())))
                  }
      entry.put('title, request.params("title"))
      entry.put('contents, new Text(request.params("contents")))
      entry.put('updated_at, DateUtils.format(new Date()))

      DataStore.put(entry)
      redirect( "/muse/pascal/index.html" )
   }

   post("/muse/pascal/entry/delete.do"){(env) =>
      import env._
      val id = flash("id").toString
      DataStore.delete('entry, id)

      redirect( "/muse/pascal/index.html" )
   }


   post("/muse/pascal/comment/create.do"){(env) =>
      import env._
      val parent = Key('entry, request.params("parent"))
      val comment = Entity('comment ,parent)  += (
                              ('name, request.params("name")), 
                              ('body, request.params("body")), 
                              ('created_at, DateUtils.format(new Date())))
      DataStore.put(comment)
      redirect( "/muse/pascal/" + request.params("parent") + ".html" )
   }
}
