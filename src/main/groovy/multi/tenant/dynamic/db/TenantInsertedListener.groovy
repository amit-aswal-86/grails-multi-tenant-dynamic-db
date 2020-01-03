package multi.tenant.dynamic.db

import grails.events.annotation.gorm.Listener
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.grails.datastore.mapping.engine.event.PostInsertEvent
import org.grails.orm.hibernate.HibernateDatastore
import org.springframework.beans.factory.annotation.Autowired

@CompileStatic
@Slf4j
class TenantInsertedListener {

    @Autowired
    HibernateDatastore hibernateDatastore;

    @Autowired
    DatabaseProvisioningService databaseProvisioningService;

    // @Autowired
    // TenantDatasourceUtilService tenantDatasourceUtilService

    @Listener(Tenant)
    void onUserPostInsertEvent(PostInsertEvent event) {
        /*
        String username = event.getEntityAccess().getPropertyValue("username");
        SecUser userInstance = tenantDatasourceUtilService.findUserByUsername(username);
        def tenantId = userInstance.tenantId;
        Tenant tenant = tenantDatasourceUtilService.findTenantById(tenantId);
        DatabaseConfiguration databaseConfiguration = databaseProvisioningService.findDatabaseConfigurationByTenantCode(tenant.tenantCode);
        hibernateDatastore.getConnectionSources().addConnectionSource(databaseConfiguration.dataSourceName, databaseConfiguration.configuration);
        */

        String tenantCode = event.getEntityAccess().getPropertyValue("tenantCode");
        DatabaseConfiguration databaseConfiguration = databaseProvisioningService.findDatabaseConfigurationByTenantCode(tenantCode);
        hibernateDatastore.getConnectionSources().addConnectionSource(databaseConfiguration.dataSourceName, databaseConfiguration.configuration);
    }
}