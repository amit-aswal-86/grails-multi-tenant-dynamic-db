package multi.tenant.dynamic.db

import grails.plugin.springsecurity.SpringSecurityService
import multi.tenant.dynamic.db.auth.SecUser
import org.grails.datastore.mapping.multitenancy.exceptions.TenantNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Lazy

class AppTenantResolver implements org.grails.datastore.mapping.multitenancy.TenantResolver {

    @Autowired
    @Lazy
    SpringSecurityService springSecurityService

    @Override
    Serializable resolveTenantIdentifier() throws TenantNotFoundException {
        Integer tenantId = getTenantFromLoggedInUser();
        if (tenantId) {
            Tenant tenantInstance = Tenant.get(new Long(tenantId.intValue()));
            if (!tenantInstance) {
                throw new TenantNotFoundException("Tenant not found for the Logged In User");
            }
            return tenantInstance.tenantCode.toLowerCase();
        }
        throw new TenantNotFoundException("Tenant could not be resolved for the Logged In User")
    }

    Integer getTenantFromLoggedInUser() {
        if (springSecurityService.isLoggedIn()) {
            def user = (SecUser) springSecurityService.getCurrentUser();
            if (user) {
                return user.tenantId
            }
        }
        null
    }
}
