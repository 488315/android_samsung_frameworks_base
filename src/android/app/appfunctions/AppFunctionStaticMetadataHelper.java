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

    public static String getStaticSchemaNameForPackage(String str) {
        return "AppFunctionStaticMetadata-" + ((String) Objects.requireNonNull(str));
    }

    public static String getDocumentIdForAppFunction(String str, String str2) {
        return str + "/" + str2;
    }

    public static String getStaticMetadataQualifiedId(String str, String str2) {
        return DocumentIdUtil.createQualifiedId("android", APP_FUNCTION_STATIC_METADATA_DB, APP_FUNCTION_STATIC_NAMESPACE, getDocumentIdForAppFunction(str, str2));
    }
}
