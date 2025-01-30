package com.google.protobuf;

import com.google.protobuf.ListFieldSchema;
import java.nio.charset.Charset;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class Protobuf {
    public static final Protobuf INSTANCE = new Protobuf();
    public final ConcurrentMap schemaCache = new ConcurrentHashMap();
    public final ManifestSchemaFactory schemaFactory = new ManifestSchemaFactory();

    private Protobuf() {
    }

    public final Schema schemaFor(Class cls) {
        Schema newSchema;
        Class cls2;
        Charset charset = Internal.UTF_8;
        if (cls == null) {
            throw new NullPointerException("messageType");
        }
        ConcurrentHashMap concurrentHashMap = (ConcurrentHashMap) this.schemaCache;
        Schema schema = (Schema) concurrentHashMap.get(cls);
        if (schema != null) {
            return schema;
        }
        ManifestSchemaFactory manifestSchemaFactory = this.schemaFactory;
        manifestSchemaFactory.getClass();
        Class cls3 = SchemaUtil.GENERATED_MESSAGE_CLASS;
        if (!GeneratedMessageLite.class.isAssignableFrom(cls) && (cls2 = SchemaUtil.GENERATED_MESSAGE_CLASS) != null && !cls2.isAssignableFrom(cls)) {
            throw new IllegalArgumentException("Message classes must extend GeneratedMessageV3 or GeneratedMessageLite");
        }
        MessageInfo messageInfoFor = manifestSchemaFactory.messageInfoFactory.messageInfoFor(cls);
        if (!messageInfoFor.isMessageSetWireFormat()) {
            if (GeneratedMessageLite.class.isAssignableFrom(cls)) {
                newSchema = messageInfoFor.getSyntax() == ProtoSyntax.PROTO2 ? MessageSchema.newSchema(messageInfoFor, NewInstanceSchemas.LITE_SCHEMA, ListFieldSchema.LITE_INSTANCE, SchemaUtil.UNKNOWN_FIELD_SET_LITE_SCHEMA, ExtensionSchemas.LITE_SCHEMA, MapFieldSchemas.LITE_SCHEMA) : MessageSchema.newSchema(messageInfoFor, NewInstanceSchemas.LITE_SCHEMA, ListFieldSchema.LITE_INSTANCE, SchemaUtil.UNKNOWN_FIELD_SET_LITE_SCHEMA, null, MapFieldSchemas.LITE_SCHEMA);
            } else {
                if (messageInfoFor.getSyntax() == ProtoSyntax.PROTO2) {
                    NewInstanceSchema newInstanceSchema = NewInstanceSchemas.FULL_SCHEMA;
                    ListFieldSchema.ListFieldSchemaFull listFieldSchemaFull = ListFieldSchema.FULL_INSTANCE;
                    UnknownFieldSchema unknownFieldSchema = SchemaUtil.PROTO2_UNKNOWN_FIELD_SET_SCHEMA;
                    ExtensionSchema extensionSchema = ExtensionSchemas.FULL_SCHEMA;
                    if (extensionSchema == null) {
                        throw new IllegalStateException("Protobuf runtime is not correctly loaded.");
                    }
                    newSchema = MessageSchema.newSchema(messageInfoFor, newInstanceSchema, listFieldSchemaFull, unknownFieldSchema, extensionSchema, MapFieldSchemas.FULL_SCHEMA);
                } else {
                    newSchema = MessageSchema.newSchema(messageInfoFor, NewInstanceSchemas.FULL_SCHEMA, ListFieldSchema.FULL_INSTANCE, SchemaUtil.PROTO3_UNKNOWN_FIELD_SET_SCHEMA, null, MapFieldSchemas.FULL_SCHEMA);
                }
            }
        } else if (GeneratedMessageLite.class.isAssignableFrom(cls)) {
            newSchema = MessageSetSchema.newSchema(SchemaUtil.UNKNOWN_FIELD_SET_LITE_SCHEMA, ExtensionSchemas.LITE_SCHEMA, messageInfoFor.getDefaultInstance());
        } else {
            UnknownFieldSchema unknownFieldSchema2 = SchemaUtil.PROTO2_UNKNOWN_FIELD_SET_SCHEMA;
            ExtensionSchema extensionSchema2 = ExtensionSchemas.FULL_SCHEMA;
            if (extensionSchema2 == null) {
                throw new IllegalStateException("Protobuf runtime is not correctly loaded.");
            }
            newSchema = MessageSetSchema.newSchema(unknownFieldSchema2, extensionSchema2, messageInfoFor.getDefaultInstance());
        }
        Schema schema2 = newSchema;
        Schema schema3 = (Schema) concurrentHashMap.putIfAbsent(cls, schema2);
        return schema3 != null ? schema3 : schema2;
    }
}
