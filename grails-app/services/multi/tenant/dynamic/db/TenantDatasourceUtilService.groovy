package multi.tenant.dynamic.db

import grails.gorm.transactions.Transactional
import multi.tenant.dynamic.db.auth.SecUser

@Transactional
class TenantDatasourceUtilService {

    SecUser findUserByUsername(String username) {
        return SecUser.findByUsername(username);
    }

    Tenant findTenantById(def tenantId) {
        return Tenant.get(tenantId);
    }
}
