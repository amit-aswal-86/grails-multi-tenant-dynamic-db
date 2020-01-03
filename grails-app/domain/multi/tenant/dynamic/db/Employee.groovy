package multi.tenant.dynamic.db

import grails.gorm.MultiTenant

class Employee implements MultiTenant<Employee> {

    static constraints = {
        empCode(nullable: false, unique: true);
        middleName(nullable: true);
    }

    static mapping = {
        version false
    }

    String empCode;
    String firstName;
    String middleName;
    String lastName;

    String gender;

    java.sql.Date dateOfBirth;

    Date dateCreated;
    Date lastUpdated;
}
