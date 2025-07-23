package com.android.framework.protobuf;

import com.android.framework.protobuf.FieldSet;
import com.android.framework.protobuf.LazyField;
import com.android.framework.protobuf.WireFormat;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

@CheckReturnValue
/* loaded from: classes5.dex */
final class MessageSetSchema<T> implements Schema<T> {
    private final MessageLite defaultInstance;
    private final ExtensionSchema<?> extensionSchema;
    private final boolean hasExtensions;
    private final UnknownFieldSchema<?, ?> unknownFieldSchema;

    private MessageSetSchema(UnknownFieldSchema<?, ?> unknownFieldSchema, ExtensionSchema<?> extensionSchema, MessageLite messageLite) {
        this.unknownFieldSchema = unknownFieldSchema;
        this.hasExtensions = extensionSchema.hasExtensions(messageLite);
        this.extensionSchema = extensionSchema;
        this.defaultInstance = messageLite;
    }

    static <T> MessageSetSchema<T> newSchema(UnknownFieldSchema<?, ?> unknownFieldSchema, ExtensionSchema<?> extensionSchema, MessageLite messageLite) {
        return new MessageSetSchema<>(unknownFieldSchema, extensionSchema, messageLite);
    }

    @Override // com.android.framework.protobuf.Schema
    public T newInstance() {
        MessageLite messageLite = this.defaultInstance;
        if (messageLite instanceof GeneratedMessageLite) {
            return (T) ((GeneratedMessageLite) messageLite).newMutableInstance();
        }
        return (T) messageLite.newBuilderForType().buildPartial();
    }

    @Override // com.android.framework.protobuf.Schema
    public boolean equals(T t, T t2) {
        if (!this.unknownFieldSchema.getFromMessage(t).equals(this.unknownFieldSchema.getFromMessage(t2))) {
            return false;
        }
        if (this.hasExtensions) {
            return this.extensionSchema.getExtensions(t).equals(this.extensionSchema.getExtensions(t2));
        }
        return true;
    }

    @Override // com.android.framework.protobuf.Schema
    public int hashCode(T t) {
        int hashCode = this.unknownFieldSchema.getFromMessage(t).hashCode();
        return this.hasExtensions ? (hashCode * 53) + this.extensionSchema.getExtensions(t).hashCode() : hashCode;
    }

    @Override // com.android.framework.protobuf.Schema
    public void mergeFrom(T t, T t2) {
        SchemaUtil.mergeUnknownFields(this.unknownFieldSchema, t, t2);
        if (this.hasExtensions) {
            SchemaUtil.mergeExtensions(this.extensionSchema, t, t2);
        }
    }

    @Override // com.android.framework.protobuf.Schema
    public void writeTo(T t, Writer writer) throws IOException {
        Iterator<Map.Entry<?, Object>> it = this.extensionSchema.getExtensions(t).iterator();
        while (it.hasNext()) {
            Map.Entry<?, Object> next = it.next();
            FieldSet.FieldDescriptorLite fieldDescriptorLite = (FieldSet.FieldDescriptorLite) next.getKey();
            if (fieldDescriptorLite.getLiteJavaType() != WireFormat.JavaType.MESSAGE || fieldDescriptorLite.isRepeated() || fieldDescriptorLite.isPacked()) {
                throw new IllegalStateException("Found invalid MessageSet item.");
            }
            if (next instanceof LazyField.LazyEntry) {
                writer.writeMessageSetItem(fieldDescriptorLite.getNumber(), ((LazyField.LazyEntry) next).getField().toByteString());
            } else {
                writer.writeMessageSetItem(fieldDescriptorLite.getNumber(), next.getValue());
            }
        }
        writeUnknownFieldsHelper(this.unknownFieldSchema, t, writer);
    }

    private <UT, UB> void writeUnknownFieldsHelper(UnknownFieldSchema<UT, UB> unknownFieldSchema, T t, Writer writer) throws IOException {
        unknownFieldSchema.writeAsMessageSetTo(unknownFieldSchema.getFromMessage(t), writer);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:22:0x00c9  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x00cf A[EDGE_INSN: B:24:0x00cf->B:25:0x00cf BREAK  A[LOOP:1: B:10:0x006f->B:18:0x006f], SYNTHETIC] */
    @Override // com.android.framework.protobuf.Schema
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void mergeFrom(T r11, byte[] r12, int r13, int r14, com.android.framework.protobuf.ArrayDecoders.Registers r15) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 230
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.framework.protobuf.MessageSetSchema.mergeFrom(java.lang.Object, byte[], int, int, com.android.framework.protobuf.ArrayDecoders$Registers):void");
    }

    @Override // com.android.framework.protobuf.Schema
    public void mergeFrom(T t, Reader reader, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        mergeFromHelper(this.unknownFieldSchema, this.extensionSchema, t, reader, extensionRegistryLite);
    }

    private <UT, UB, ET extends FieldSet.FieldDescriptorLite<ET>> void mergeFromHelper(UnknownFieldSchema<UT, UB> unknownFieldSchema, ExtensionSchema<ET> extensionSchema, T t, Reader reader, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        UnknownFieldSchema<UT, UB> unknownFieldSchema2;
        MessageSetSchema<T> messageSetSchema;
        ExtensionSchema<ET> extensionSchema2;
        Reader reader2;
        ExtensionRegistryLite extensionRegistryLite2;
        UB builderFromMessage = unknownFieldSchema.getBuilderFromMessage(t);
        FieldSet<ET> mutableExtensions = extensionSchema.getMutableExtensions(t);
        while (reader.getFieldNumber() != Integer.MAX_VALUE) {
            try {
                messageSetSchema = this;
                unknownFieldSchema2 = unknownFieldSchema;
                extensionSchema2 = extensionSchema;
                reader2 = reader;
                extensionRegistryLite2 = extensionRegistryLite;
            } catch (Throwable th) {
                th = th;
                unknownFieldSchema2 = unknownFieldSchema;
            }
            try {
                if (!messageSetSchema.parseMessageSetItemOrUnknownField(reader2, extensionRegistryLite2, extensionSchema2, mutableExtensions, unknownFieldSchema2, builderFromMessage)) {
                    unknownFieldSchema2.setBuilderToMessage(t, builderFromMessage);
                    return;
                }
                this = messageSetSchema;
                reader = reader2;
                extensionRegistryLite = extensionRegistryLite2;
                extensionSchema = extensionSchema2;
                unknownFieldSchema = unknownFieldSchema2;
            } catch (Throwable th2) {
                th = th2;
                Throwable th3 = th;
                unknownFieldSchema2.setBuilderToMessage(t, builderFromMessage);
                throw th3;
            }
        }
        unknownFieldSchema.setBuilderToMessage(t, builderFromMessage);
    }

    @Override // com.android.framework.protobuf.Schema
    public void makeImmutable(T t) {
        this.unknownFieldSchema.makeImmutable(t);
        this.extensionSchema.makeImmutable(t);
    }

    private <UT, UB, ET extends FieldSet.FieldDescriptorLite<ET>> boolean parseMessageSetItemOrUnknownField(Reader reader, ExtensionRegistryLite extensionRegistryLite, ExtensionSchema<ET> extensionSchema, FieldSet<ET> fieldSet, UnknownFieldSchema<UT, UB> unknownFieldSchema, UB ub) throws IOException {
        int tag = reader.getTag();
        if (tag != WireFormat.MESSAGE_SET_ITEM_TAG) {
            if (WireFormat.getTagWireType(tag) == 2) {
                Object findExtensionByNumber = extensionSchema.findExtensionByNumber(extensionRegistryLite, this.defaultInstance, WireFormat.getTagFieldNumber(tag));
                if (findExtensionByNumber != null) {
                    extensionSchema.parseLengthPrefixedMessageSetItem(reader, findExtensionByNumber, extensionRegistryLite, fieldSet);
                    return true;
                }
                return unknownFieldSchema.mergeOneFieldFrom(ub, reader);
            }
            return reader.skipField();
        }
        Object obj = null;
        int i = 0;
        ByteString byteString = null;
        while (reader.getFieldNumber() != Integer.MAX_VALUE) {
            int tag2 = reader.getTag();
            if (tag2 == WireFormat.MESSAGE_SET_TYPE_ID_TAG) {
                i = reader.readUInt32();
                obj = extensionSchema.findExtensionByNumber(extensionRegistryLite, this.defaultInstance, i);
            } else if (tag2 == WireFormat.MESSAGE_SET_MESSAGE_TAG) {
                if (obj != null) {
                    extensionSchema.parseLengthPrefixedMessageSetItem(reader, obj, extensionRegistryLite, fieldSet);
                } else {
                    byteString = reader.readBytes();
                }
            } else if (!reader.skipField()) {
                break;
            }
        }
        if (reader.getTag() != WireFormat.MESSAGE_SET_ITEM_END_TAG) {
            throw InvalidProtocolBufferException.invalidEndTag();
        }
        if (byteString != null) {
            if (obj != null) {
                extensionSchema.parseMessageSetItem(byteString, obj, extensionRegistryLite, fieldSet);
            } else {
                unknownFieldSchema.addLengthDelimited(ub, i, byteString);
            }
        }
        return true;
    }

    @Override // com.android.framework.protobuf.Schema
    public final boolean isInitialized(T t) {
        return this.extensionSchema.getExtensions(t).isInitialized();
    }

    @Override // com.android.framework.protobuf.Schema
    public int getSerializedSize(T t) {
        int unknownFieldsSerializedSize = getUnknownFieldsSerializedSize(this.unknownFieldSchema, t);
        return this.hasExtensions ? unknownFieldsSerializedSize + this.extensionSchema.getExtensions(t).getMessageSetSerializedSize() : unknownFieldsSerializedSize;
    }

    private <UT, UB> int getUnknownFieldsSerializedSize(UnknownFieldSchema<UT, UB> unknownFieldSchema, T t) {
        return unknownFieldSchema.getSerializedSizeAsMessageSet(unknownFieldSchema.getFromMessage(t));
    }
}
