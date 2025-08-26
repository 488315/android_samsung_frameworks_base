package com.google.protobuf;

/* loaded from: classes4.dex */
public final class MapFieldSchemas {
    public static final MapFieldSchema FULL_SCHEMA;
    public static final MapFieldSchemaLite LITE_SCHEMA;

    static {
        MapFieldSchema mapFieldSchema = null;
        try {
            Class[] clsArr = new Class[0];
            mapFieldSchema = (MapFieldSchema) Class.forName("com.google.protobuf.MapFieldSchemaFull").getDeclaredConstructor(null).newInstance(null);
        } catch (Exception unused) {
        }
        FULL_SCHEMA = mapFieldSchema;
        LITE_SCHEMA = new MapFieldSchemaLite();
    }
}
