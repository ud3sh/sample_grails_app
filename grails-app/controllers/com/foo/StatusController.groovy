package com.foo

class StatusController {

    def springSecurityService //injected by spring as there is a bean by the same name
    
    def index() { }
    
    def updateStatus () { 
        def status = new Status (message: params.message)//params is a map of all request parameters
        status.author = lookupUser() //lookups the ucrrent user
        status.save() //saves the object to the database
        def messages = currentUserTimeline()
        render template : 'messages', collection: messages, var: 'message'
    }
    
    
    private currentUserTimeline(){
        def usr = lookupUser()
        Status.withCriteria {
            or {
                eq 'username', usr.username
            }       
        }
        maxResults 10
        order 'dateCreated', 'desc'
    }
    
    private lookupUser(){
        if (springSecurityService.isLoggedIn()){
        //springSecurityService.pricipal contains information about the currently logged in user
            User.get(springSecurityService.principal.id)            
        }
        
    }
}
