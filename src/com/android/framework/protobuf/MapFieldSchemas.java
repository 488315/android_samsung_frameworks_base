package com.android.framework.protobuf;

@CheckReturnValue
/* loaded from: classes5.dex */
final class MapFieldSchemas {
    private static final MapFieldSchema FULL_SCHEMA = loadSchemaForFullRuntime();
    private static final MapFieldSchema LITE_SCHEMA = new MapFieldSchemaLite();

    MapFieldSchemas() {
    }

    static MapFieldSchema full() {
        return FULL_SCHEMA;
    }

    static MapFieldSchema lite() {
        return LITE_SCHEMA;
    }

    private static MapFieldSchema loadSchemaForFullRuntime() {
        try {
            Class[] clsArr = new Class[0];
            return (MapFieldSchema) Class.forName("com.android.framework.protobuf.MapFieldSchemaFull").getDeclaredConstructor(null).newInstance(null);
        } catch (Exception unused) {
            return null;
        }
    }
}
