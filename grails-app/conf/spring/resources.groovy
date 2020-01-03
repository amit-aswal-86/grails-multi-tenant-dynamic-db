import multi.tenant.dynamic.db.auth.SecUserPasswordEncoderListener
import multi.tenant.dynamic.db.TenantInsertedListener
import multi.tenant.dynamic.db.AppTenantResolver;

// Place your Spring DSL code here
beans = {
    secUserPasswordEncoderListener(SecUserPasswordEncoderListener);

    tenantInsertedListener(TenantInsertedListener);

    appTenantResolver(AppTenantResolver);
}
