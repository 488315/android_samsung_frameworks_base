package com.google.protobuf;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ExtensionSchemas {
    public static final ExtensionSchema FULL_SCHEMA;
    public static final ExtensionSchemaLite LITE_SCHEMA = new ExtensionSchemaLite();

    static {
        ExtensionSchema extensionSchema;
        try {
            extensionSchema = (ExtensionSchema) Class.forName("com.google.protobuf.ExtensionSchemaFull").getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception unused) {
            extensionSchema = null;
        }
        FULL_SCHEMA = extensionSchema;
    }
}
