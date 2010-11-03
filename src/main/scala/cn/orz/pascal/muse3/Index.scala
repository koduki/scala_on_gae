package cn.orz.pascal.muse3

import cn.orz.pascal.gae.persist.DataStore._
import cn.orz.pascal.gae.framework.helper.HtmlHelper._
import com.google.appengine.api.datastore.Text

object Index {
   def apply(offset:Int, entries:Iterator[EntityWrapper]):scala.xml.Elem = {
      val size = 10
      Template("top"){
    <section id="content">
      <nav class="pager">
        {$("<a href='/muse/pascal/index.html?offset=" + (if (offset > size) (offset - size) else 0)  +"'>&lt;前の10件</a>")} | 
        {$("<a href='/muse/pascal/index.html?offset=" + (offset + size)  +"'>次の10件&gt;</a>")}
      </nav>	
      {for(entry <- entries) yield {
      <article>
        <header>

          <h2>{$("<a href='/muse/pascal/" + entry.key.getId +  ".html'>" + entry('title) + "</a>")}</h2>
          <p>
            <span>created at: </span>{timestamp(entry, "created_at")}
            <span>updated at: </span>{timestamp(entry, "updated_at")}
          </p>
        </header>
        <section class="body">
          {html(entry('contents))}
        </section>
        <footer />
      </article>
      }}
      <nav class="pager">
        {$("<a href='/muse/pascal/index.html?offset=" + (if (offset > size) (offset - size) else 0)  +"'>&lt;前の10件</a>")} | 
        {$("<a href='/muse/pascal/index.html?offset=" + (offset + size)  +"'>次の10件&gt;</a>")}
      </nav>
    </section>
      }
   }
}
