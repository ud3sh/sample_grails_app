import com.foo.Authority
import com.foo.User
import com.foo.UserAuthority

class BootStrap {

    def springSecurityService

    def init = { servletContext ->
        if (!User.count()) {
            createData()
        }
    }

    def destroy = {
    }

    private void createData() {
        
        //create intial data]
        
        def userRole = Authority.findByAuthority('ROLE_USER')?:new Authority(authority: 'ROLE_USER').save()
        def adminRole = Authority.findByAuthority('ADMIN_USER')?:new Authority(authority: 'ADMIN_USER').save()


        String password = "password"

        def user1 = new User(username: "firstuser", 
                             firstName: "First", 
                             lastName:"User", 
                             password: password, 
                             enabled: true).save()
        def user2 = new User(username: "seconduser", 
                             firstName: "Second", 
                             lastName:"User", 
                             password: password, 
                             enabled: true).save()

        UserAuthority.create user1, userRole, true
        UserAuthority.create user2, userRole, true
    }
}