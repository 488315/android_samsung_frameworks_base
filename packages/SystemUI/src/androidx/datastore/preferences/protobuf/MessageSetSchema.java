package androidx.datastore.preferences.protobuf;

import androidx.datastore.preferences.protobuf.GeneratedMessageLite;
import androidx.datastore.preferences.protobuf.LazyField;
import java.util.Iterator;
import java.util.Map;

/* loaded from: classes.dex */
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

    @Override // androidx.datastore.preferences.protobuf.Schema
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

    @Override // androidx.datastore.preferences.protobuf.Schema
    public final int getSerializedSize(AbstractMessageLite abstractMessageLite) {
        UnknownFieldSchema unknownFieldSchema = this.unknownFieldSchema;
        int serializedSizeAsMessageSet = unknownFieldSchema.getSerializedSizeAsMessageSet(unknownFieldSchema.getFromMessage(abstractMessageLite));
        if (!this.hasExtensions) {
            return serializedSizeAsMessageSet;
        }
        SmallSortedMap smallSortedMap = this.extensionSchema.getExtensions(abstractMessageLite).fields;
        int size = smallSortedMap.entryList.size();
        int messageSetSerializedSize = 0;
        for (int i = 0; i < size; i++) {
            messageSetSerializedSize += FieldSet.getMessageSetSerializedSize(smallSortedMap.getArrayEntryAt(i));
        }
        Iterator it = smallSortedMap.getOverflowEntries().iterator();
        while (it.hasNext()) {
            messageSetSerializedSize += FieldSet.getMessageSetSerializedSize((Map.Entry) it.next());
        }
        return serializedSizeAsMessageSet + messageSetSerializedSize;
    }

    @Override // androidx.datastore.preferences.protobuf.Schema
    public final int hashCode(GeneratedMessageLite generatedMessageLite) {
        int iHashCode = this.unknownFieldSchema.getFromMessage(generatedMessageLite).hashCode();
        if (!this.hasExtensions) {
            return iHashCode;
        }
        return this.extensionSchema.getExtensions(generatedMessageLite).fields.hashCode() + (iHashCode * 53);
    }

    @Override // androidx.datastore.preferences.protobuf.Schema
    public final boolean isInitialized(Object obj) {
        return this.extensionSchema.getExtensions(obj).isInitialized();
    }

    @Override // androidx.datastore.preferences.protobuf.Schema
    public final void makeImmutable(Object obj) {
        this.unknownFieldSchema.makeImmutable(obj);
        this.extensionSchema.makeImmutable(obj);
    }

    @Override // androidx.datastore.preferences.protobuf.Schema
    public final void mergeFrom(Object obj, Object obj2) {
        Class cls = SchemaUtil.GENERATED_MESSAGE_CLASS;
        UnknownFieldSchema unknownFieldSchema = this.unknownFieldSchema;
        unknownFieldSchema.setToMessage(obj, unknownFieldSchema.merge(unknownFieldSchema.getFromMessage(obj), unknownFieldSchema.getFromMessage(obj2)));
        if (this.hasExtensions) {
            SchemaUtil.mergeExtensions(this.extensionSchema, obj, obj2);
        }
    }

    @Override // androidx.datastore.preferences.protobuf.Schema
    public final Object newInstance() {
        MessageLite messageLite = this.defaultInstance;
        return messageLite instanceof GeneratedMessageLite ? ((GeneratedMessageLite) messageLite).newMutableInstance$1() : messageLite.newBuilderForType().buildPartial$1();
    }

    public final boolean parseMessageSetItemOrUnknownField(CodedInputStreamReader codedInputStreamReader, ExtensionRegistryLite extensionRegistryLite, ExtensionSchema extensionSchema, FieldSet fieldSet, UnknownFieldSchema unknownFieldSchema, Object obj) throws InvalidProtocolBufferException {
        int i = codedInputStreamReader.tag;
        MessageLite messageLite = this.defaultInstance;
        if (i != 11) {
            if ((i & 7) != 2) {
                return codedInputStreamReader.skipField();
            }
            GeneratedMessageLite.GeneratedExtension generatedExtensionFindExtensionByNumber = extensionSchema.findExtensionByNumber(extensionRegistryLite, messageLite, i >>> 3);
            if (generatedExtensionFindExtensionByNumber == null) {
                return unknownFieldSchema.mergeOneFieldFrom(0, codedInputStreamReader, obj);
            }
            extensionSchema.parseLengthPrefixedMessageSetItem(codedInputStreamReader, generatedExtensionFindExtensionByNumber, extensionRegistryLite, fieldSet);
            return true;
        }
        GeneratedMessageLite.GeneratedExtension generatedExtensionFindExtensionByNumber2 = null;
        ByteString bytes = null;
        int uInt32 = 0;
        while (codedInputStreamReader.getFieldNumber() != Integer.MAX_VALUE) {
            int i2 = codedInputStreamReader.tag;
            if (i2 == 16) {
                codedInputStreamReader.requireWireType(0);
                uInt32 = codedInputStreamReader.input.readUInt32();
                generatedExtensionFindExtensionByNumber2 = extensionSchema.findExtensionByNumber(extensionRegistryLite, messageLite, uInt32);
            } else if (i2 == 26) {
                if (generatedExtensionFindExtensionByNumber2 != null) {
                    extensionSchema.parseLengthPrefixedMessageSetItem(codedInputStreamReader, generatedExtensionFindExtensionByNumber2, extensionRegistryLite, fieldSet);
                } else {
                    bytes = codedInputStreamReader.readBytes();
                }
            } else if (!codedInputStreamReader.skipField()) {
                break;
            }
        }
        if (codedInputStreamReader.tag != 12) {
            throw new InvalidProtocolBufferException("Protocol message end-group tag did not match expected tag.");
        }
        if (bytes != null) {
            if (generatedExtensionFindExtensionByNumber2 != null) {
                extensionSchema.parseMessageSetItem(bytes, generatedExtensionFindExtensionByNumber2, extensionRegistryLite, fieldSet);
                return true;
            }
            unknownFieldSchema.addLengthDelimited(obj, uInt32, bytes);
        }
        return true;
    }

    @Override // androidx.datastore.preferences.protobuf.Schema
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

    @Override // androidx.datastore.preferences.protobuf.Schema
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
