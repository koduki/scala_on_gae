package cn.orz.pascal.gae.persist
import com.google.appengine.api.datastore._

object DataStore {
	def Entity(kind:Symbol, properties:(Symbol, Any)*) = {
     val result = new EntityWrapper(new Entity(kind.toString))
     properties.foreach(x => result += x)
     result
   }
	def Entity(kind:Symbol) = new EntityWrapper(new Entity(kind.toString))
	def Entity(kind:String) = new EntityWrapper(new Entity(kind))
	def Entity(kind:Symbol, parent:Key) = new EntityWrapper(new Entity(kind.toString, parent))  
	def Entity(kind:String, parent:Key) = new EntityWrapper(new Entity(kind, parent))  
	class EntityWrapper(val src:Entity){
		def key() = src.getKey
		def parent() = src.getParent
		def put(keyName:Symbol, value:Any):EntityWrapper = {src.setProperty(keyName.toString, value);this}
	   def +=(keyName:Symbol, value:Any):EntityWrapper = put(keyName, value)
	   def +=(args:(Symbol, Any)*):EntityWrapper = {args.foreach(x => this.put(x._1, x._2)); this}
	   def apply(keyName:Symbol) =  src.getProperty(keyName.toString) match{
			case x:Text => x.getValue
			case x:String => x
			case _ => ""
	    }
	} 

	implicit def wrapIterator(iterable:java.lang.Iterable[Entity]) = new Iterator[EntityWrapper]{
		val iterator = iterable.iterator()
		def hasNext:Boolean = iterator.hasNext()
		def next:EntityWrapper = new EntityWrapper(iterator.next())
	}
	    
	implicit def toFilter1(property:Symbol) = new { 
		def ===(value:Any):Query => Query = 
			query => query.addFilter(property.toString, Query.FilterOperator.EQUAL, value) 
		def <=(value:Any):Query => Query = 
			query => query.addFilter(property.toString, Query.FilterOperator.LESS_THAN_OR_EQUAL, value) 
		def >=(value:Any):Query => Query = 
			query => query.addFilter(property.toString, Query.FilterOperator.GREATER_THAN_OR_EQUAL, value)
		def <(value:Any):Query => Query = 
			query => query.addFilter(property.toString, Query.FilterOperator.LESS_THAN, value) 
		def >(value:Any):Query => Query = 
			query => query.addFilter(property.toString, Query.FilterOperator.GREATER_THAN, value) 
	}

    val ds = DatastoreServiceFactory.getDatastoreService()
    class Filter(ds:DatastoreService, query:Query){ 
      def asIterator():Iterator[EntityWrapper] = ds.prepare(query).asIterable()
      def asList():List[EntityWrapper] = asIterator.toList      
      def where(predicate:Query => Query) = new FetchOption(ds, predicate(query))
    }
    
	class FetchOption(ds:DatastoreService, query:Query){ 
    	def asIterator():Iterator[EntityWrapper] = ds.prepare(query).asIterable()
    	def asList():List[EntityWrapper] = ds.prepare(query).asIterable.toList
    	def and(predicate:Query => Query) = new FetchOption(ds, predicate(query))
    	def limit(offset:Int, limit:Int) = new {
    	  def asIterator():Iterator[EntityWrapper] = ds.prepare(query).asIterable(FetchOptions.Builder.withLimit(limit).offset(offset)) 
    	} 
	}
	def from(kind:Symbol) = new Filter(ds, new Query(kind.toString))
	def from(ancestor:Key) = new Filter(ds, new Query(ancestor))
 	def from(kind:Symbol, ancestor:Key) = new Filter(ds, new Query(kind.toString, ancestor))    

	def put(x:EntityWrapper) = DatastoreServiceFactory.getDatastoreService.put(x.src)
	def get(key:Key) = new EntityWrapper(DatastoreServiceFactory.getDatastoreService.get(key))
	def get(kind:Symbol, key:Symbol, value:Any) = (DataStore from(kind) where(key === value) asList())(0)
	def exist(kind:Symbol, key:Symbol, value:Any) = !(DataStore from(kind) where(key === value) asList() isEmpty)
}
