package com.google.protobuf;

import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.LazyField;
import java.util.Iterator;
import java.util.Map;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class MessageSetSchema implements Schema {
    public final MessageLite defaultInstance;
    public final ExtensionSchema extensionSchema;
    public final boolean hasExtensions;
    public final UnknownFieldSchema unknownFieldSchema;

    private MessageSetSchema(UnknownFieldSchema unknownFieldSchema, ExtensionSchema extensionSchema, MessageLite messageLite) {
        this.unknownFieldSchema = unknownFieldSchema;
        this.hasExtensions = extensionSchema.hasExtensions(messageLite);
        this.extensionSchema = extensionSchema;
        this.defaultInstance = messageLite;
    }

    public static MessageSetSchema newSchema(UnknownFieldSchema unknownFieldSchema, ExtensionSchema extensionSchema, MessageLite messageLite) {
        return new MessageSetSchema(unknownFieldSchema, extensionSchema, messageLite);
    }

    @Override // com.google.protobuf.Schema
    public final boolean equals(GeneratedMessageLite generatedMessageLite, GeneratedMessageLite generatedMessageLite2) {
        UnknownFieldSchema unknownFieldSchema = this.unknownFieldSchema;
        if (!unknownFieldSchema.getFromMessage(generatedMessageLite).equals(unknownFieldSchema.getFromMessage(generatedMessageLite2))) {
            return false;
        }
        if (!this.hasExtensions) {
            return true;
        }
        ExtensionSchema extensionSchema = this.extensionSchema;
        return extensionSchema.getExtensions(generatedMessageLite).equals(extensionSchema.getExtensions(generatedMessageLite2));
    }

    @Override // com.google.protobuf.Schema
    public final int getSerializedSize(AbstractMessageLite abstractMessageLite) {
        SmallSortedMap smallSortedMap;
        UnknownFieldSchema unknownFieldSchema = this.unknownFieldSchema;
        int serializedSizeAsMessageSet = unknownFieldSchema.getSerializedSizeAsMessageSet(unknownFieldSchema.getFromMessage(abstractMessageLite));
        if (!this.hasExtensions) {
            return serializedSizeAsMessageSet;
        }
        FieldSet extensions = this.extensionSchema.getExtensions(abstractMessageLite);
        int i = 0;
        int i2 = 0;
        while (true) {
            smallSortedMap = extensions.fields;
            if (i >= smallSortedMap.entryList.size()) {
                break;
            }
            i2 += FieldSet.getMessageSetSerializedSize(smallSortedMap.getArrayEntryAt(i));
            i++;
        }
        Iterator it = smallSortedMap.getOverflowEntries().iterator();
        while (it.hasNext()) {
            i2 += FieldSet.getMessageSetSerializedSize((Map.Entry) it.next());
        }
        return serializedSizeAsMessageSet + i2;
    }

    @Override // com.google.protobuf.Schema
    public final int hashCode(GeneratedMessageLite generatedMessageLite) {
        int hashCode = this.unknownFieldSchema.getFromMessage(generatedMessageLite).hashCode();
        if (!this.hasExtensions) {
            return hashCode;
        }
        return this.extensionSchema.getExtensions(generatedMessageLite).fields.hashCode() + (hashCode * 53);
    }

    @Override // com.google.protobuf.Schema
    public final boolean isInitialized(Object obj) {
        return this.extensionSchema.getExtensions(obj).isInitialized();
    }

    @Override // com.google.protobuf.Schema
    public final void makeImmutable(Object obj) {
        this.unknownFieldSchema.makeImmutable(obj);
        this.extensionSchema.makeImmutable(obj);
    }

    @Override // com.google.protobuf.Schema
    public final void mergeFrom(Object obj, Object obj2) {
        Class cls = SchemaUtil.GENERATED_MESSAGE_CLASS;
        UnknownFieldSchema unknownFieldSchema = this.unknownFieldSchema;
        unknownFieldSchema.setToMessage(obj, unknownFieldSchema.merge(unknownFieldSchema.getFromMessage(obj), unknownFieldSchema.getFromMessage(obj2)));
        if (this.hasExtensions) {
            SchemaUtil.mergeExtensions(this.extensionSchema, obj, obj2);
        }
    }

    @Override // com.google.protobuf.Schema
    public final GeneratedMessageLite newInstance() {
        MessageLite messageLite = this.defaultInstance;
        if (messageLite instanceof GeneratedMessageLite) {
            return ((GeneratedMessageLite) messageLite).newMutableInstance$1();
        }
        GeneratedMessageLite generatedMessageLite = (GeneratedMessageLite) messageLite;
        generatedMessageLite.getClass();
        return ((GeneratedMessageLite.Builder) generatedMessageLite.dynamicMethod(GeneratedMessageLite.MethodToInvoke.NEW_BUILDER)).buildPartial();
    }

    public final boolean parseMessageSetItemOrUnknownField(CodedInputStreamReader codedInputStreamReader, ExtensionRegistryLite extensionRegistryLite, ExtensionSchema extensionSchema, FieldSet fieldSet, UnknownFieldSchema unknownFieldSchema, Object obj) {
        int i = codedInputStreamReader.tag;
        MessageLite messageLite = this.defaultInstance;
        if (i != 11) {
            if ((i & 7) != 2) {
                return codedInputStreamReader.skipField();
            }
            GeneratedMessageLite.GeneratedExtension findExtensionByNumber = extensionSchema.findExtensionByNumber(extensionRegistryLite, messageLite, i >>> 3);
            if (findExtensionByNumber == null) {
                return unknownFieldSchema.mergeOneFieldFrom(obj, codedInputStreamReader);
            }
            extensionSchema.parseLengthPrefixedMessageSetItem(codedInputStreamReader, findExtensionByNumber, extensionRegistryLite, fieldSet);
            return true;
        }
        GeneratedMessageLite.GeneratedExtension generatedExtension = null;
        int i2 = 0;
        ByteString byteString = null;
        while (codedInputStreamReader.getFieldNumber() != Integer.MAX_VALUE) {
            int i3 = codedInputStreamReader.tag;
            if (i3 == 16) {
                codedInputStreamReader.requireWireType(0);
                i2 = codedInputStreamReader.input.readUInt32();
                generatedExtension = extensionSchema.findExtensionByNumber(extensionRegistryLite, messageLite, i2);
            } else if (i3 == 26) {
                if (generatedExtension != null) {
                    extensionSchema.parseLengthPrefixedMessageSetItem(codedInputStreamReader, generatedExtension, extensionRegistryLite, fieldSet);
                } else {
                    byteString = codedInputStreamReader.readBytes();
                }
            } else if (!codedInputStreamReader.skipField()) {
                break;
            }
        }
        if (codedInputStreamReader.tag != 12) {
            throw new InvalidProtocolBufferException("Protocol message end-group tag did not match expected tag.");
        }
        if (byteString != null) {
            if (generatedExtension != null) {
                extensionSchema.parseMessageSetItem(byteString, generatedExtension, extensionRegistryLite, fieldSet);
                return true;
            }
            unknownFieldSchema.addLengthDelimited(obj, i2, byteString);
        }
        return true;
    }

    @Override // com.google.protobuf.Schema
    public final void writeTo(Object obj, CodedOutputStreamWriter codedOutputStreamWriter) {
        Iterator it = this.extensionSchema.getExtensions(obj).iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            GeneratedMessageLite.ExtensionDescriptor extensionDescriptor = (GeneratedMessageLite.ExtensionDescriptor) entry.getKey();
            if (extensionDescriptor.type.getJavaType() != WireFormat$JavaType.MESSAGE || extensionDescriptor.isRepeated || extensionDescriptor.isPacked) {
                throw new IllegalStateException("Found invalid MessageSet item.");
            }
            if (entry instanceof LazyField.LazyEntry) {
                codedOutputStreamWriter.writeMessageSetItem(extensionDescriptor.number, ((LazyField) ((LazyField.LazyEntry) entry).entry.getValue()).toByteString());
            } else {
                codedOutputStreamWriter.writeMessageSetItem(extensionDescriptor.number, entry.getValue());
            }
        }
        UnknownFieldSchema unknownFieldSchema = this.unknownFieldSchema;
        unknownFieldSchema.writeAsMessageSetTo(unknownFieldSchema.getFromMessage(obj), codedOutputStreamWriter);
    }

    /* JADX WARN: Removed duplicated region for block: B:38:0x00c1  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00c6 A[EDGE_INSN: B:40:0x00c6->B:41:0x00c6 BREAK  A[LOOP:1: B:23:0x0073->B:31:0x009e], SYNTHETIC] */
    @Override // com.google.protobuf.Schema
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void mergeFrom(java.lang.Object r17, byte[] r18, int r19, int r20, com.google.protobuf.ArrayDecoders.Registers r21) {
        /*
            Method dump skipped, instructions count: 219
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.MessageSetSchema.mergeFrom(java.lang.Object, byte[], int, int, com.google.protobuf.ArrayDecoders$Registers):void");
    }

    @Override // com.google.protobuf.Schema
    public final void mergeFrom(Object obj, CodedInputStreamReader codedInputStreamReader, ExtensionRegistryLite extensionRegistryLite) {
        UnknownFieldSchema unknownFieldSchema = this.unknownFieldSchema;
        UnknownFieldSetLite builderFromMessage = unknownFieldSchema.getBuilderFromMessage(obj);
        ExtensionSchema extensionSchema = this.extensionSchema;
        FieldSet mutableExtensions = extensionSchema.getMutableExtensions(obj);
        while (codedInputStreamReader.getFieldNumber() != Integer.MAX_VALUE) {
            try {
                MessageSetSchema messageSetSchema = this;
                CodedInputStreamReader codedInputStreamReader2 = codedInputStreamReader;
                ExtensionRegistryLite extensionRegistryLite2 = extensionRegistryLite;
                if (!messageSetSchema.parseMessageSetItemOrUnknownField(codedInputStreamReader2, extensionRegistryLite2, extensionSchema, mutableExtensions, unknownFieldSchema, builderFromMessage)) {
                    return;
                }
                this = messageSetSchema;
                codedInputStreamReader = codedInputStreamReader2;
                extensionRegistryLite = extensionRegistryLite2;
            } finally {
                unknownFieldSchema.setBuilderToMessage(obj, builderFromMessage);
            }
        }
    }
}
