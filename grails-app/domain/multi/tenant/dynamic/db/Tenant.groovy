package multi.tenant.dynamic.db

class Tenant {

    static constraints = {
        tenantCode(unique: true);
    }

    String tenantName;
    String tenantCode;

    Date dateCreated;
    Date lastUpdated;
}
