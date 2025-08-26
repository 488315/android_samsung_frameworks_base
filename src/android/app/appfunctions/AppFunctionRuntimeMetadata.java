package android.app.appfunctions;

import android.app.appsearch.AppSearchSchema;
import android.app.appsearch.GenericDocument;
import java.util.Objects;

/* loaded from: classes.dex */
public class AppFunctionRuntimeMetadata extends GenericDocument {
    public static final String APP_FUNCTION_INDEXER_PACKAGE = "android";
    public static final String APP_FUNCTION_RUNTIME_METADATA_DB = "appfunctions-db";
    public static final String APP_FUNCTION_RUNTIME_NAMESPACE = "app_functions_runtime";
    public static final String PROPERTY_APP_FUNCTION_STATIC_METADATA_QUALIFIED_ID = "appFunctionStaticMetadataQualifiedId";
    public static final String PROPERTY_ENABLED = "enabled";
    public static final String PROPERTY_FUNCTION_ID = "functionId";
    public static final String PROPERTY_PACKAGE_NAME = "packageName";
    public static final String RUNTIME_SCHEMA_TYPE = "AppFunctionRuntimeMetadata";
    private static final String RUNTIME_SCHEMA_TYPE_SEPARATOR = "-";
    private static final String TAG = "AppSearchAppFunction";

    public AppFunctionRuntimeMetadata(GenericDocument genericDocument) {
        super(genericDocument);
    }

    public static String getRuntimeSchemaNameForPackage(String str) {
        return "AppFunctionRuntimeMetadata-" + ((String) Objects.requireNonNull(str));
    }

    public static String getPackageNameFromSchema(String str) {
        String[] strArrSplit = str.split("-");
        if (strArrSplit.length > 2) {
            throw new IllegalArgumentException("Invalid schema type: " + str + " for app function runtime");
        }
        if (strArrSplit.length < 2) {
            return "android";
        }
        return strArrSplit[1];
    }

    public static String getDocumentIdForAppFunction(String str, String str2) {
        return str + "/" + str2;
    }

    public static AppSearchSchema createAppFunctionRuntimeSchema(String str) {
        return getAppFunctionRuntimeSchemaBuilder(getRuntimeSchemaNameForPackage(str)).addParentType(RUNTIME_SCHEMA_TYPE).build();
    }

    public static AppSearchSchema createParentAppFunctionRuntimeSchema() {
        return getAppFunctionRuntimeSchemaBuilder(RUNTIME_SCHEMA_TYPE).build();
    }

    private static AppSearchSchema.Builder getAppFunctionRuntimeSchemaBuilder(String str) {
        return new AppSearchSchema.Builder(str).addProperty(new AppSearchSchema.StringPropertyConfig.Builder("functionId").setCardinality(2).setIndexingType(1).setTokenizerType(2).build()).addProperty(new AppSearchSchema.StringPropertyConfig.Builder("packageName").setCardinality(2).setIndexingType(1).setTokenizerType(2).build()).addProperty(new AppSearchSchema.LongPropertyConfig.Builder("enabled").setCardinality(2).setIndexingType(1).build()).addProperty(new AppSearchSchema.StringPropertyConfig.Builder(PROPERTY_APP_FUNCTION_STATIC_METADATA_QUALIFIED_ID).setCardinality(2).setJoinableValueType(1).build());
    }

    public String getFunctionId() {
        return (String) Objects.requireNonNull(getPropertyString("functionId"));
    }

    public String getPackageName() {
        return (String) Objects.requireNonNull(getPropertyString("packageName"));
    }

    public int getEnabled() {
        return (int) getPropertyLong("enabled");
    }

    public String getAppFunctionStaticMetadataQualifiedId() {
        return getPropertyString(PROPERTY_APP_FUNCTION_STATIC_METADATA_QUALIFIED_ID);
    }

    public static final class Builder extends GenericDocument.Builder<Builder> {
        public Builder(String str, String str2) {
            super(AppFunctionRuntimeMetadata.APP_FUNCTION_RUNTIME_NAMESPACE, AppFunctionRuntimeMetadata.getDocumentIdForAppFunction((String) Objects.requireNonNull(str), (String) Objects.requireNonNull(str2)), AppFunctionRuntimeMetadata.getRuntimeSchemaNameForPackage(str));
            setPropertyString("packageName", str);
            setPropertyString("functionId", str2);
            setPropertyString(AppFunctionRuntimeMetadata.PROPERTY_APP_FUNCTION_STATIC_METADATA_QUALIFIED_ID, AppFunctionStaticMetadataHelper.getStaticMetadataQualifiedId(str, str2));
        }

        public Builder(AppFunctionRuntimeMetadata appFunctionRuntimeMetadata) {
            this(appFunctionRuntimeMetadata.getPackageName(), appFunctionRuntimeMetadata.getFunctionId());
            setEnabled(appFunctionRuntimeMetadata.getEnabled());
        }

        public Builder setEnabled(int i) {
            if (i != 0 && i != 1 && i != 2) {
                throw new IllegalArgumentException("Value of EnabledState is unsupported.");
            }
            setPropertyLong("enabled", i);
            return this;
        }

        @Override // android.app.appsearch.GenericDocument.Builder
        public AppFunctionRuntimeMetadata build() {
            return new AppFunctionRuntimeMetadata(super.build());
        }
    }
}
