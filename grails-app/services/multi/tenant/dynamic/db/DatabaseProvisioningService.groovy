package multi.tenant.dynamic.db


import groovy.transform.CompileStatic

@CompileStatic
class DatabaseProvisioningService {

    List<DatabaseConfiguration> findAllDatabaseConfiguration() {
        def tenantList = Tenant.list();
        tenantList.collect { findDatabaseConfigurationByTenantCode(it.tenantCode.toLowerCase()); };
    }

    DatabaseConfiguration findDatabaseConfigurationByTenantCode(String tenantCode) {
        new DatabaseConfiguration(dataSourceName: tenantCode, configuration: configurationByTenantCode(tenantCode))
    }

    Map<String, Object> configurationByTenantCode(String tenantCode) {
        [
                'hibernate.hbm2ddl.auto':'none',
                'username': 'root',
                'password': 'Root_Mysql_5.8',
                'url':"jdbc:mysql://localhost:3306/$tenantCode"
        ] as Map<String, Object>
    }
}

