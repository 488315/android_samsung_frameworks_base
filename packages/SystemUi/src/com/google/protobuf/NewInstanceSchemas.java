package com.google.protobuf;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class NewInstanceSchemas {
    public static final NewInstanceSchema FULL_SCHEMA;
    public static final NewInstanceSchemaLite LITE_SCHEMA;

    static {
        NewInstanceSchema newInstanceSchema;
        try {
            newInstanceSchema = (NewInstanceSchema) Class.forName("com.google.protobuf.NewInstanceSchemaFull").getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception unused) {
            newInstanceSchema = null;
        }
        FULL_SCHEMA = newInstanceSchema;
        LITE_SCHEMA = new NewInstanceSchemaLite();
    }
}
