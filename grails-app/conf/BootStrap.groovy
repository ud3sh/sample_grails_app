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
        
        //create intial data
        def userRole = new Authority(authority: 'ROLE_USER').save()
        def adminRole = new Authority(authority: 'ADMIN_USER').save()


        String password = springSecurityService.encodePassword('password')

        [user1: 'User1', user2: 'User2'].each { userName, firstName ->
            def user = new User(username: userName, firstName: firstName, password: password, enabled: true).save()
            UserAuthority.create user, userRole, true
        }
    }
}