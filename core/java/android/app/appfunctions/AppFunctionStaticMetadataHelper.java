package android.app.appfunctions;

import android.app.appsearch.util.DocumentIdUtil;
import java.util.Objects;

/* loaded from: classes.dex */
public class AppFunctionStaticMetadataHelper {
    public static final String APP_FUNCTION_INDEXER_PACKAGE = "android";
    public static final String APP_FUNCTION_STATIC_METADATA_DB = "apps-db";
    public static final String APP_FUNCTION_STATIC_NAMESPACE = "app_functions";
    public static final String PROPERTY_FUNCTION_ID = "functionId";
    public static final String PROPERTY_PACKAGE_NAME = "packageName";
    public static final String STATIC_PROPERTY_ENABLED_BY_DEFAULT = "enabledByDefault";
    public static final String STATIC_PROPERTY_RESTRICT_CALLERS_WITH_EXECUTE_APP_FUNCTIONS = "restrictCallersWithExecuteAppFunctions";
    public static final String STATIC_SCHEMA_TYPE = "AppFunctionStaticMetadata";

    public static String getStaticSchemaNameForPackage(String pkg) {
        return "AppFunctionStaticMetadata-" + ((String) Objects.requireNonNull(pkg));
    }

    public static String getDocumentIdForAppFunction(String pkg, String functionId) {
        return pkg + "/" + functionId;
    }

    public static String getStaticMetadataQualifiedId(String packageName, String functionId) {
        return DocumentIdUtil.createQualifiedId("android", APP_FUNCTION_STATIC_METADATA_DB, APP_FUNCTION_STATIC_NAMESPACE, getDocumentIdForAppFunction(packageName, functionId));
    }
}
