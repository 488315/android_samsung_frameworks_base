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

    public static String getRuntimeSchemaNameForPackage(String pkg) {
        return "AppFunctionRuntimeMetadata-" + ((String) Objects.requireNonNull(pkg));
    }

    public static String getPackageNameFromSchema(String metadataSchemaType) {
        String[] split = metadataSchemaType.split("-");
        if (split.length > 2) {
            throw new IllegalArgumentException("Invalid schema type: " + metadataSchemaType + " for app function runtime");
        }
        if (split.length < 2) {
            return "android";
        }
        return split[1];
    }

    public static String getDocumentIdForAppFunction(String pkg, String functionId) {
        return pkg + "/" + functionId;
    }

    public static AppSearchSchema createAppFunctionRuntimeSchema(String packageName) {
        return getAppFunctionRuntimeSchemaBuilder(getRuntimeSchemaNameForPackage(packageName)).addParentType(RUNTIME_SCHEMA_TYPE).build();
    }

    public static AppSearchSchema createParentAppFunctionRuntimeSchema() {
        return getAppFunctionRuntimeSchemaBuilder(RUNTIME_SCHEMA_TYPE).build();
    }

    private static AppSearchSchema.Builder getAppFunctionRuntimeSchemaBuilder(String schemaType) {
        return new AppSearchSchema.Builder(schemaType).addProperty(new AppSearchSchema.StringPropertyConfig.Builder("functionId").setCardinality(2).setIndexingType(1).setTokenizerType(2).build()).addProperty(new AppSearchSchema.StringPropertyConfig.Builder("packageName").setCardinality(2).setIndexingType(1).setTokenizerType(2).build()).addProperty(new AppSearchSchema.LongPropertyConfig.Builder("enabled").setCardinality(2).setIndexingType(1).build()).addProperty(new AppSearchSchema.StringPropertyConfig.Builder(PROPERTY_APP_FUNCTION_STATIC_METADATA_QUALIFIED_ID).setCardinality(2).setJoinableValueType(1).build());
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
        public Builder(String packageName, String functionId) {
            super(AppFunctionRuntimeMetadata.APP_FUNCTION_RUNTIME_NAMESPACE, AppFunctionRuntimeMetadata.getDocumentIdForAppFunction((String) Objects.requireNonNull(packageName), (String) Objects.requireNonNull(functionId)), AppFunctionRuntimeMetadata.getRuntimeSchemaNameForPackage(packageName));
            setPropertyString("packageName", packageName);
            setPropertyString("functionId", functionId);
            setPropertyString(AppFunctionRuntimeMetadata.PROPERTY_APP_FUNCTION_STATIC_METADATA_QUALIFIED_ID, AppFunctionStaticMetadataHelper.getStaticMetadataQualifiedId(packageName, functionId));
        }

        public Builder(AppFunctionRuntimeMetadata original) {
            this(original.getPackageName(), original.getFunctionId());
            setEnabled(original.getEnabled());
        }

        public Builder setEnabled(int enabledState) {
            if (enabledState == 0 || enabledState == 1 || enabledState == 2) {
                setPropertyLong("enabled", enabledState);
                return this;
            }
            throw new IllegalArgumentException("Value of EnabledState is unsupported.");
        }

        @Override // android.app.appsearch.GenericDocument.Builder
        public AppFunctionRuntimeMetadata build() {
            return new AppFunctionRuntimeMetadata(super.build());
        }
    }
}
