package com.google.protobuf;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
