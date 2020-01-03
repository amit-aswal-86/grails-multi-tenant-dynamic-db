package multi.tenant.dynamic.db

import grails.converters.JSON

class EmployeeController {

    // static allowedMethods = [];

    EmployeeService employeeService;

    def index() {
        render employeeService.findAll() as JSON;
    }
}
