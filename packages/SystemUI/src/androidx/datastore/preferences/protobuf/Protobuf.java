package androidx.datastore.preferences.protobuf;

import androidx.datastore.preferences.protobuf.ManifestSchemaFactory;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class Protobuf {
    public static final Protobuf INSTANCE = new Protobuf();
    public final ConcurrentMap schemaCache = new ConcurrentHashMap();
    public final ManifestSchemaFactory schemaFactory = new ManifestSchemaFactory();

    private Protobuf() {
    }

    public final Schema schemaFor(Class cls) {
        Class cls2;
        Internal.checkNotNull(cls, "messageType");
        Schema schema = (Schema) ((ConcurrentHashMap) this.schemaCache).get(cls);
        if (schema == null) {
            ManifestSchemaFactory manifestSchemaFactory = this.schemaFactory;
            manifestSchemaFactory.getClass();
            Class cls3 = SchemaUtil.GENERATED_MESSAGE_CLASS;
            if (!GeneratedMessageLite.class.isAssignableFrom(cls) && (cls2 = SchemaUtil.GENERATED_MESSAGE_CLASS) != null && !cls2.isAssignableFrom(cls)) {
                throw new IllegalArgumentException("Message classes must extend GeneratedMessage or GeneratedMessageLite");
            }
            MessageInfo messageInfoFor = manifestSchemaFactory.messageInfoFactory.messageInfoFor(cls);
            if (messageInfoFor.isMessageSetWireFormat()) {
                if (GeneratedMessageLite.class.isAssignableFrom(cls)) {
                    schema = MessageSetSchema.newSchema(SchemaUtil.UNKNOWN_FIELD_SET_LITE_SCHEMA, ExtensionSchemas.LITE_SCHEMA, messageInfoFor.getDefaultInstance());
                } else {
                    UnknownFieldSchema unknownFieldSchema = SchemaUtil.UNKNOWN_FIELD_SET_FULL_SCHEMA;
                    ExtensionSchema extensionSchema = ExtensionSchemas.FULL_SCHEMA;
                    if (extensionSchema == null) {
                        throw new IllegalStateException("Protobuf runtime is not correctly loaded.");
                    }
                    schema = MessageSetSchema.newSchema(unknownFieldSchema, extensionSchema, messageInfoFor.getDefaultInstance());
                }
            } else if (GeneratedMessageLite.class.isAssignableFrom(cls)) {
                NewInstanceSchemaLite newInstanceSchemaLite = NewInstanceSchemas.LITE_SCHEMA;
                ExtensionSchemaLite extensionSchemaLite = null;
                ListFieldSchemaLite listFieldSchemaLite = ListFieldSchemas.LITE_SCHEMA;
                UnknownFieldSetLiteSchema unknownFieldSetLiteSchema = SchemaUtil.UNKNOWN_FIELD_SET_LITE_SCHEMA;
                if (ManifestSchemaFactory.AnonymousClass2.$SwitchMap$com$google$protobuf$ProtoSyntax[messageInfoFor.getSyntax().ordinal()] != 1) {
                    extensionSchemaLite = ExtensionSchemas.LITE_SCHEMA;
                }
                schema = MessageSchema.newSchema(messageInfoFor, newInstanceSchemaLite, listFieldSchemaLite, unknownFieldSetLiteSchema, extensionSchemaLite, MapFieldSchemas.LITE_SCHEMA);
            } else {
                ExtensionSchema extensionSchema2 = null;
                NewInstanceSchema newInstanceSchema = NewInstanceSchemas.FULL_SCHEMA;
                ListFieldSchema listFieldSchema = ListFieldSchemas.FULL_SCHEMA;
                UnknownFieldSchema unknownFieldSchema2 = SchemaUtil.UNKNOWN_FIELD_SET_FULL_SCHEMA;
                if (ManifestSchemaFactory.AnonymousClass2.$SwitchMap$com$google$protobuf$ProtoSyntax[messageInfoFor.getSyntax().ordinal()] != 1 && (extensionSchema2 = ExtensionSchemas.FULL_SCHEMA) == null) {
                    throw new IllegalStateException("Protobuf runtime is not correctly loaded.");
                }
                schema = MessageSchema.newSchema(messageInfoFor, newInstanceSchema, listFieldSchema, unknownFieldSchema2, extensionSchema2, MapFieldSchemas.FULL_SCHEMA);
            }
            Schema schema2 = (Schema) ((ConcurrentHashMap) this.schemaCache).putIfAbsent(cls, schema);
            if (schema2 != null) {
                return schema2;
            }
        }
        return schema;
    }
}
