package com.google.protobuf;

import com.google.protobuf.ArrayDecoders;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.LazyField;
import java.util.Iterator;
import java.util.Map;

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
        int messageSetSerializedSize = 0;
        while (true) {
            smallSortedMap = extensions.fields;
            if (i >= smallSortedMap.entryList.size()) {
                break;
            }
            messageSetSerializedSize += FieldSet.getMessageSetSerializedSize(smallSortedMap.getArrayEntryAt(i));
            i++;
        }
        Iterator it = smallSortedMap.getOverflowEntries().iterator();
        while (it.hasNext()) {
            messageSetSerializedSize += FieldSet.getMessageSetSerializedSize((Map.Entry) it.next());
        }
        return serializedSizeAsMessageSet + messageSetSerializedSize;
    }

    @Override // com.google.protobuf.Schema
    public final int hashCode(GeneratedMessageLite generatedMessageLite) {
        int iHashCode = this.unknownFieldSchema.getFromMessage(generatedMessageLite).hashCode();
        if (!this.hasExtensions) {
            return iHashCode;
        }
        return this.extensionSchema.getExtensions(generatedMessageLite).fields.hashCode() + (iHashCode * 53);
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

    public final boolean parseMessageSetItemOrUnknownField(CodedInputStreamReader codedInputStreamReader, ExtensionRegistryLite extensionRegistryLite, ExtensionSchema extensionSchema, FieldSet fieldSet, UnknownFieldSchema unknownFieldSchema, Object obj) throws InvalidProtocolBufferException {
        int i = codedInputStreamReader.tag;
        MessageLite messageLite = this.defaultInstance;
        if (i != 11) {
            if ((i & 7) != 2) {
                return codedInputStreamReader.skipField();
            }
            GeneratedMessageLite.GeneratedExtension generatedExtensionFindExtensionByNumber = extensionSchema.findExtensionByNumber(extensionRegistryLite, messageLite, i >>> 3);
            if (generatedExtensionFindExtensionByNumber == null) {
                return unknownFieldSchema.mergeOneFieldFrom(obj, codedInputStreamReader);
            }
            extensionSchema.parseLengthPrefixedMessageSetItem(codedInputStreamReader, generatedExtensionFindExtensionByNumber, extensionRegistryLite, fieldSet);
            return true;
        }
        GeneratedMessageLite.GeneratedExtension generatedExtensionFindExtensionByNumber2 = null;
        int uInt32 = 0;
        ByteString bytes = null;
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

    /* JADX WARN: Removed duplicated region for block: B:35:0x00bc  */
    @Override // com.google.protobuf.Schema
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void mergeFrom(Object obj, byte[] bArr, int i, int i2, ArrayDecoders.Registers registers) throws InvalidProtocolBufferException {
        MessageSetSchema messageSetSchema = this;
        GeneratedMessageLite generatedMessageLite = (GeneratedMessageLite) obj;
        UnknownFieldSetLite unknownFieldSetLiteNewInstance = generatedMessageLite.unknownFields;
        if (unknownFieldSetLiteNewInstance == UnknownFieldSetLite.DEFAULT_INSTANCE) {
            unknownFieldSetLiteNewInstance = UnknownFieldSetLite.newInstance();
            generatedMessageLite.unknownFields = unknownFieldSetLiteNewInstance;
        }
        UnknownFieldSetLite unknownFieldSetLite = unknownFieldSetLiteNewInstance;
        GeneratedMessageLite.ExtendableMessage extendableMessage = (GeneratedMessageLite.ExtendableMessage) obj;
        FieldSet fieldSet = extendableMessage.extensions;
        if (fieldSet.isImmutable) {
            extendableMessage.extensions = fieldSet.m3287clone();
        }
        FieldSet fieldSet2 = extendableMessage.extensions;
        int iSkipField = i;
        GeneratedMessageLite.GeneratedExtension generatedExtension = null;
        while (iSkipField < i2) {
            GeneratedMessageLite.GeneratedExtension generatedExtensionFindExtensionByNumber = generatedExtension;
            int iDecodeVarint32 = ArrayDecoders.decodeVarint32(bArr, iSkipField, registers);
            int i3 = registers.int1;
            MessageLite messageLite = messageSetSchema.defaultInstance;
            ExtensionSchema extensionSchema = messageSetSchema.extensionSchema;
            int i4 = 2;
            ExtensionRegistryLite extensionRegistryLite = registers.extensionRegistry;
            if (i3 == 11) {
                int i5 = 0;
                ByteString byteString = null;
                while (iDecodeVarint32 < i2) {
                    iDecodeVarint32 = ArrayDecoders.decodeVarint32(bArr, iDecodeVarint32, registers);
                    int i6 = registers.int1;
                    int i7 = i6 >>> 3;
                    int i8 = i6 & 7;
                    if (i7 == i4) {
                        if (i8 == 0) {
                            iDecodeVarint32 = ArrayDecoders.decodeVarint32(bArr, iDecodeVarint32, registers);
                            i5 = registers.int1;
                            generatedExtensionFindExtensionByNumber = extensionSchema.findExtensionByNumber(extensionRegistryLite, messageLite, i5);
                        }
                        i4 = 2;
                    } else if (i7 == 3) {
                        if (generatedExtensionFindExtensionByNumber != null) {
                            iDecodeVarint32 = ArrayDecoders.decodeMessageField(Protobuf.INSTANCE.schemaFor((Class) generatedExtensionFindExtensionByNumber.messageDefaultInstance.getClass()), bArr, iDecodeVarint32, i2, registers);
                            fieldSet2.setField(generatedExtensionFindExtensionByNumber.descriptor, registers.object1);
                        } else if (i8 == 2) {
                            iDecodeVarint32 = ArrayDecoders.decodeBytes(bArr, iDecodeVarint32, registers);
                            byteString = (ByteString) registers.object1;
                        } else if (i6 == 12) {
                            break;
                        } else {
                            iDecodeVarint32 = ArrayDecoders.skipField(i6, bArr, iDecodeVarint32, i2, registers);
                        }
                        i4 = 2;
                    }
                }
                if (byteString != null) {
                    unknownFieldSetLite.storeField((i5 << 3) | 2, byteString);
                }
                messageSetSchema = this;
                iSkipField = iDecodeVarint32;
            } else if ((i3 & 7) == 2) {
                generatedExtensionFindExtensionByNumber = extensionSchema.findExtensionByNumber(extensionRegistryLite, messageLite, i3 >>> 3);
                if (generatedExtensionFindExtensionByNumber != null) {
                    iSkipField = ArrayDecoders.decodeMessageField(Protobuf.INSTANCE.schemaFor((Class) generatedExtensionFindExtensionByNumber.messageDefaultInstance.getClass()), bArr, iDecodeVarint32, i2, registers);
                    fieldSet2.setField(generatedExtensionFindExtensionByNumber.descriptor, registers.object1);
                } else {
                    iSkipField = ArrayDecoders.decodeUnknownField(i3, bArr, iDecodeVarint32, i2, unknownFieldSetLite, registers);
                }
            } else {
                iSkipField = ArrayDecoders.skipField(i3, bArr, iDecodeVarint32, i2, registers);
            }
            generatedExtension = generatedExtensionFindExtensionByNumber;
        }
        if (iSkipField != i2) {
            throw InvalidProtocolBufferException.parseFailure();
        }
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
