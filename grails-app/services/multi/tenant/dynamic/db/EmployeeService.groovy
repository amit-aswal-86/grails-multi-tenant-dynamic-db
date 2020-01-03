package multi.tenant.dynamic.db

import grails.gorm.multitenancy.CurrentTenant
import grails.gorm.services.Service
import groovy.transform.CompileStatic

@Service(Employee)
@CurrentTenant
@CompileStatic
interface EmployeeService {
    List<Employee> findAll();
}
