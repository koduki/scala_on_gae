package cn.orz.pascal.muse3

import cn.orz.pascal.gae.persist.DataStore._
import cn.orz.pascal.gae.framework.helper.HtmlHelper._
import cn.orz.pascal.gae.framework.helper.GAEHelper._

object Show {
   def apply(id:String, entry:EntityWrapper, comments:List[EntityWrapper]):scala.xml.Elem = {
      val size = 10

      Template(id, entry('title)){
    <section id="content">
      <nav class="pager">

      </nav>	
      <article>
        <header>
          <h2>{$("<a href='/muse/pascal/" + entry.key.getId +  ".html'>" + entry('title) + "</a>")}</h2>
          <p>
            <span>created at: </span>{timestamp(entry, "created_at")}
            <span>updated at: </span>{timestamp(entry, "updated_at")}
          </p>
          {if (isAdmin()) {
          <span>{$("<a href='/muse/pascal/delete.html?id=" + id + "'>Delete</a>")}</span>
          }else ""}
        </header>
        <section class="body">
          {html(entry('contents).toString)}
        </section>
        <footer> 
           <section class="comments">
              <details>
                 <summary>コメント({comments.size})</summary>
                 <ul>
                 {for(comment <- comments) yield {
                   <li>
                     <article>
                       <span class="name">{comment('name)}</span>:
                       <p class="body">{comment('body)}</p>
                       ({timestamp(comment, "created_at")})
                     </article>
                   </li>
                 }}
                 </ul>
                 <form method="post" action="/muse/pascal/comment/create.do">
                   <fieldset>
                     <legend>コメントする</legend>
                     {$("<input type='hidden' name='parent' value='" + id + "' />")}
                     <input type="text" class="name" name="name" title="Enter name" />
                     <input type="text" class="comment" name="body" title="Enter comment" />
                     <input type="submit" class="submit" value="OK" />
                   </fieldset>
                 </form>
              </details>
           </section>
        </footer>
      </article>
      <nav class="pager">

      </nav>
    </section>
      }
   }
}
