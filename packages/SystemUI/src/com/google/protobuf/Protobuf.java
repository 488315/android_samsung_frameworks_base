package com.google.protobuf;

import com.google.protobuf.ListFieldSchema;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/* loaded from: classes4.dex */
public final class Protobuf {
    public static final Protobuf INSTANCE = new Protobuf();
    public final ConcurrentMap schemaCache = new ConcurrentHashMap();
    public final ManifestSchemaFactory schemaFactory = new ManifestSchemaFactory();

    private Protobuf() {
    }

    public final Schema schemaFor(Class cls) {
        Class cls2;
        Internal.checkNotNull(cls, "messageType");
        Schema schemaNewSchema = (Schema) ((ConcurrentHashMap) this.schemaCache).get(cls);
        if (schemaNewSchema == null) {
            ManifestSchemaFactory manifestSchemaFactory = this.schemaFactory;
            manifestSchemaFactory.getClass();
            Class cls3 = SchemaUtil.GENERATED_MESSAGE_CLASS;
            if (!GeneratedMessageLite.class.isAssignableFrom(cls) && (cls2 = SchemaUtil.GENERATED_MESSAGE_CLASS) != null && !cls2.isAssignableFrom(cls)) {
                throw new IllegalArgumentException("Message classes must extend GeneratedMessageV3 or GeneratedMessageLite");
            }
            MessageInfo messageInfoMessageInfoFor = manifestSchemaFactory.messageInfoFactory.messageInfoFor(cls);
            if (messageInfoMessageInfoFor.isMessageSetWireFormat()) {
                if (GeneratedMessageLite.class.isAssignableFrom(cls)) {
                    schemaNewSchema = MessageSetSchema.newSchema(SchemaUtil.UNKNOWN_FIELD_SET_LITE_SCHEMA, ExtensionSchemas.LITE_SCHEMA, messageInfoMessageInfoFor.getDefaultInstance());
                } else {
                    UnknownFieldSchema unknownFieldSchema = SchemaUtil.PROTO2_UNKNOWN_FIELD_SET_SCHEMA;
                    ExtensionSchema extensionSchema = ExtensionSchemas.FULL_SCHEMA;
                    if (extensionSchema == null) {
                        throw new IllegalStateException("Protobuf runtime is not correctly loaded.");
                    }
                    schemaNewSchema = MessageSetSchema.newSchema(unknownFieldSchema, extensionSchema, messageInfoMessageInfoFor.getDefaultInstance());
                }
            } else if (GeneratedMessageLite.class.isAssignableFrom(cls)) {
                schemaNewSchema = messageInfoMessageInfoFor.getSyntax() == ProtoSyntax.PROTO2 ? MessageSchema.newSchema(messageInfoMessageInfoFor, NewInstanceSchemas.LITE_SCHEMA, ListFieldSchema.LITE_INSTANCE, SchemaUtil.UNKNOWN_FIELD_SET_LITE_SCHEMA, ExtensionSchemas.LITE_SCHEMA, MapFieldSchemas.LITE_SCHEMA) : MessageSchema.newSchema(messageInfoMessageInfoFor, NewInstanceSchemas.LITE_SCHEMA, ListFieldSchema.LITE_INSTANCE, SchemaUtil.UNKNOWN_FIELD_SET_LITE_SCHEMA, null, MapFieldSchemas.LITE_SCHEMA);
            } else if (messageInfoMessageInfoFor.getSyntax() == ProtoSyntax.PROTO2) {
                NewInstanceSchema newInstanceSchema = NewInstanceSchemas.FULL_SCHEMA;
                ListFieldSchema.ListFieldSchemaFull listFieldSchemaFull = ListFieldSchema.FULL_INSTANCE;
                UnknownFieldSchema unknownFieldSchema2 = SchemaUtil.PROTO2_UNKNOWN_FIELD_SET_SCHEMA;
                ExtensionSchema extensionSchema2 = ExtensionSchemas.FULL_SCHEMA;
                if (extensionSchema2 == null) {
                    throw new IllegalStateException("Protobuf runtime is not correctly loaded.");
                }
                schemaNewSchema = MessageSchema.newSchema(messageInfoMessageInfoFor, newInstanceSchema, listFieldSchemaFull, unknownFieldSchema2, extensionSchema2, MapFieldSchemas.FULL_SCHEMA);
            } else {
                schemaNewSchema = MessageSchema.newSchema(messageInfoMessageInfoFor, NewInstanceSchemas.FULL_SCHEMA, ListFieldSchema.FULL_INSTANCE, SchemaUtil.PROTO3_UNKNOWN_FIELD_SET_SCHEMA, null, MapFieldSchemas.FULL_SCHEMA);
            }
            Schema schema = (Schema) ((ConcurrentHashMap) this.schemaCache).putIfAbsent(cls, schemaNewSchema);
            if (schema != null) {
                return schema;
            }
        }
        return schemaNewSchema;
    }

    public final Schema schemaFor(Object obj) {
        return schemaFor((Class) obj.getClass());
    }
}
