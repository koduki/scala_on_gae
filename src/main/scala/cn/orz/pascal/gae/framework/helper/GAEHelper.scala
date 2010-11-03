package cn.orz.pascal.gae.framework.helper
import com.google.appengine.api.users.UserServiceFactory

object GAEHelper {
   def isAdmin():Boolean = {
     val us = UserServiceFactory.getUserService()
     us.isUserLoggedIn() && us.isUserAdmin() 
   }
}
