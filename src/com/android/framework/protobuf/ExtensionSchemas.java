package com.android.framework.protobuf;

@CheckReturnValue
/* loaded from: classes5.dex */
final class ExtensionSchemas {
    private static final ExtensionSchema<?> LITE_SCHEMA = new ExtensionSchemaLite();
    private static final ExtensionSchema<?> FULL_SCHEMA = loadSchemaForFullRuntime();

    ExtensionSchemas() {
    }

    private static ExtensionSchema<?> loadSchemaForFullRuntime() {
        try {
            Class[] clsArr = new Class[0];
            return (ExtensionSchema) Class.forName("com.android.framework.protobuf.ExtensionSchemaFull").getDeclaredConstructor(null).newInstance(null);
        } catch (Exception unused) {
            return null;
        }
    }

    static ExtensionSchema<?> lite() {
        return LITE_SCHEMA;
    }

    static ExtensionSchema<?> full() {
        ExtensionSchema<?> extensionSchema = FULL_SCHEMA;
        if (extensionSchema != null) {
            return extensionSchema;
        }
        throw new IllegalStateException("Protobuf runtime is not correctly loaded.");
    }
}
