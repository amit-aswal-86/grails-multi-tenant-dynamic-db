package multi.tenant.dynamic.db

import groovy.transform.CompileStatic

@CompileStatic
class DatabaseConfiguration {
    String dataSourceName
    Map configuration
}