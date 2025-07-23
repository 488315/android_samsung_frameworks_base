package com.android.framework.protobuf;

@CheckReturnValue
/* loaded from: classes5.dex */
final class NewInstanceSchemas {
    private static final NewInstanceSchema FULL_SCHEMA = loadSchemaForFullRuntime();
    private static final NewInstanceSchema LITE_SCHEMA = new NewInstanceSchemaLite();

    NewInstanceSchemas() {
    }

    static NewInstanceSchema full() {
        return FULL_SCHEMA;
    }

    static NewInstanceSchema lite() {
        return LITE_SCHEMA;
    }

    private static NewInstanceSchema loadSchemaForFullRuntime() {
        try {
            Class[] clsArr = new Class[0];
            return (NewInstanceSchema) Class.forName("com.android.framework.protobuf.NewInstanceSchemaFull").getDeclaredConstructor(null).newInstance(null);
        } catch (Exception unused) {
            return null;
        }
    }
}
