package com.foo
import com.foo.User

class StatusController {

    def springSecurityService //injected by spring as there is a bean by the same name
    
    def index() { }
    
    def updateStatus () { 
        def status = new Status (message: params.message)//params is a map of all request parameters
        status.author = lookupUser() //lookups the ucrrent user
        status.save() //saves the object to the database
        def messages = currentUserTimeline()
        render template: 'statusMessages', collection: messages, var: 'msg'
    }
    
    
    private currentUserTimeline(){
        def per = lookupUser()
        def query = Status.whereAny {
            author { 
                author { username == per.username }
            }
        }
        query.list(max: 10)
    }
    
    private lookupUser(){
        return User.get(springSecurityService.principal.id)        
    }
}
