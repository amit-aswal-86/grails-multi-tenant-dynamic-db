package multi.tenant.dynamic.db

import grails.plugin.springsecurity.SpringSecurityService
import multi.tenant.dynamic.db.auth.Requestmap
import multi.tenant.dynamic.db.auth.SecRole
import multi.tenant.dynamic.db.auth.SecUser
import multi.tenant.dynamic.db.auth.SecUserSecRole
import org.grails.orm.hibernate.HibernateDatastore

class BootStrap {
    HibernateDatastore hibernateDatastore
    DatabaseProvisioningService databaseProvisioningService

    SpringSecurityService springSecurityService;

    def init = { servletContext ->
        setSpringSecurityStuff();

        for (String tenantCode in ['multi-tenant-1', 'multi-tenant-2']) {
            Tenant tenant = Tenant.findByTenantCode(tenantCode);
            if (tenant) { continue; }
            new Tenant(tenantName: 'Test tenant 1', tenantCode: tenantCode).save(failOnError: true);
        }

        for (DatabaseConfiguration databaseConfiguration : databaseProvisioningService.findAllDatabaseConfiguration()) {
            println ("DataSource >>> ${databaseConfiguration.dataSourceName}");
            println ("Configuration >>> ${databaseConfiguration.configuration}");
            hibernateDatastore.getConnectionSources().addConnectionSource(databaseConfiguration.dataSourceName, databaseConfiguration.configuration)
        }
    }
    def destroy = {
    }

    private void setSpringSecurityStuff() {
        for (String url in [
                '/', '/error', '/index', '/index.gsp', '/**/favicon.ico', '/shutdown',
                '/**/js/**', '/**/css/**', '/**/images/**',
                '/login', '/login.*', '/login/*',
                '/logout', '/logout.*', '/logout/*']) {
            Requestmap requestmap = Requestmap.findByUrl(url);
            if (!requestmap) {
                new Requestmap(url: url, configAttribute: 'permitAll').save();
            }
        }

        String url = "/employee/index";
        Requestmap requestmap = Requestmap.findByUrl(url);
        if (!requestmap) {
            new Requestmap(url: url, configAttribute: 'ROLE_USER').save();
        }

        url = "/employee/create";
        requestmap = Requestmap.findByUrl(url);
        if (!requestmap) {
            new Requestmap(url: url, configAttribute: 'ROLE_ADMIN').save();
        }

        url = "/employee/save";
        requestmap = Requestmap.findByUrl(url);
        if (!requestmap) {
            new Requestmap(url: url, configAttribute: 'ROLE_ADMIN').save();
        }

        url = "/employee/delete";
        requestmap = Requestmap.findByUrl(url);
        if (!requestmap) {
            new Requestmap(url: url, configAttribute: 'ROLE_ADMIN').save();
        }

        url = "/employee/edit";
        requestmap = Requestmap.findByUrl(url);
        if (!requestmap) {
            new Requestmap(url: url, configAttribute: 'ROLE_ADMIN').save();
        }

        url = "/employee/update";
        requestmap = Requestmap.findByUrl(url);
        if (!requestmap) {
            new Requestmap(url: url, configAttribute: 'ROLE_ADMIN').save();
        }

        springSecurityService.clearCachedRequestmaps();

        SecRole userRole = SecRole.findByAuthority("ROLE_USER");
        if (!userRole) {
            userRole = new SecRole(authority: 'ROLE_USER').save();
        }

        SecRole adminRole = SecRole.findByAuthority("ROLE_ADMIN");
        if (!adminRole) {
            adminRole = new SecRole(authority: 'ROLE_ADMIN').save();
        }

        SecUser secUser = SecUser.findByUsername('amit');
        if (!secUser) {
            secUser = new SecUser(username: 'amit', password: 'aswal', tenantId: 1).save(failOnError: true);
        }

        if (!secUser.authorities.contains(adminRole)) {
            new SecUserSecRole(secUser: secUser, secRole: adminRole).save();
        }

        if (!secUser.authorities.contains(userRole)) {
            new SecUserSecRole(secUser: secUser, secRole: userRole).save();
        }
    }
}
