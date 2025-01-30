package com.google.protobuf;

import com.google.protobuf.ArrayDecoders;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.LazyField;
import java.util.Iterator;
import java.util.Map;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
    public final boolean equals(Object obj, Object obj2) {
        UnknownFieldSchema unknownFieldSchema = this.unknownFieldSchema;
        if (!unknownFieldSchema.getFromMessage(obj).equals(unknownFieldSchema.getFromMessage(obj2))) {
            return false;
        }
        if (!this.hasExtensions) {
            return true;
        }
        ExtensionSchema extensionSchema = this.extensionSchema;
        return extensionSchema.getExtensions(obj).equals(extensionSchema.getExtensions(obj2));
    }

    @Override // com.google.protobuf.Schema
    public final int getSerializedSize(Object obj) {
        SmallSortedMap smallSortedMap;
        UnknownFieldSchema unknownFieldSchema = this.unknownFieldSchema;
        int i = 0;
        int serializedSizeAsMessageSet = unknownFieldSchema.getSerializedSizeAsMessageSet(unknownFieldSchema.getFromMessage(obj)) + 0;
        if (!this.hasExtensions) {
            return serializedSizeAsMessageSet;
        }
        FieldSet extensions = this.extensionSchema.getExtensions(obj);
        int i2 = 0;
        while (true) {
            smallSortedMap = extensions.fields;
            if (i >= smallSortedMap.getNumArrayEntries()) {
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
    public final int hashCode(Object obj) {
        int hashCode = this.unknownFieldSchema.getFromMessage(obj).hashCode();
        return this.hasExtensions ? (hashCode * 53) + this.extensionSchema.getExtensions(obj).hashCode() : hashCode;
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
            GeneratedMessageLite generatedMessageLite = (GeneratedMessageLite) messageLite;
            generatedMessageLite.getClass();
            return (GeneratedMessageLite) generatedMessageLite.dynamicMethod(GeneratedMessageLite.MethodToInvoke.NEW_MUTABLE_INSTANCE);
        }
        GeneratedMessageLite generatedMessageLite2 = (GeneratedMessageLite) messageLite;
        generatedMessageLite2.getClass();
        return ((GeneratedMessageLite.Builder) generatedMessageLite2.dynamicMethod(GeneratedMessageLite.MethodToInvoke.NEW_BUILDER)).buildPartial();
    }

    public final boolean parseMessageSetItemOrUnknownField(Reader reader, ExtensionRegistryLite extensionRegistryLite, ExtensionSchema extensionSchema, FieldSet fieldSet, UnknownFieldSchema unknownFieldSchema, Object obj) {
        CodedInputStreamReader codedInputStreamReader = (CodedInputStreamReader) reader;
        int i = codedInputStreamReader.tag;
        MessageLite messageLite = this.defaultInstance;
        if (i != 11) {
            if ((i & 7) != 2) {
                return codedInputStreamReader.skipField();
            }
            GeneratedMessageLite.GeneratedExtension findExtensionByNumber = extensionSchema.findExtensionByNumber(extensionRegistryLite, messageLite, i >>> 3);
            if (findExtensionByNumber == null) {
                return unknownFieldSchema.mergeOneFieldFrom(obj, reader);
            }
            extensionSchema.parseLengthPrefixedMessageSetItem(reader, findExtensionByNumber, extensionRegistryLite, fieldSet);
            return true;
        }
        GeneratedMessageLite.GeneratedExtension generatedExtension = null;
        int i2 = 0;
        ByteString byteString = null;
        while (codedInputStreamReader.getFieldNumber() != Integer.MAX_VALUE) {
            int i3 = codedInputStreamReader.tag;
            if (i3 == 16) {
                i2 = codedInputStreamReader.readUInt32();
                generatedExtension = extensionSchema.findExtensionByNumber(extensionRegistryLite, messageLite, i2);
            } else if (i3 == 26) {
                if (generatedExtension != null) {
                    extensionSchema.parseLengthPrefixedMessageSetItem(reader, generatedExtension, extensionRegistryLite, fieldSet);
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
            } else {
                unknownFieldSchema.addLengthDelimited(obj, i2, byteString);
            }
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

    /* JADX WARN: Removed duplicated region for block: B:27:0x00c5  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x00ce A[EDGE_INSN: B:28:0x00ce->B:29:0x00ce BREAK  A[LOOP:1: B:13:0x0079->B:21:0x00ca], SYNTHETIC] */
    @Override // com.google.protobuf.Schema
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void mergeFrom(Object obj, byte[] bArr, int i, int i2, ArrayDecoders.Registers registers) {
        int decodeVarint32;
        MessageSetSchema messageSetSchema = this;
        GeneratedMessageLite generatedMessageLite = (GeneratedMessageLite) obj;
        UnknownFieldSetLite unknownFieldSetLite = generatedMessageLite.unknownFields;
        if (unknownFieldSetLite == UnknownFieldSetLite.DEFAULT_INSTANCE) {
            unknownFieldSetLite = UnknownFieldSetLite.newInstance();
            generatedMessageLite.unknownFields = unknownFieldSetLite;
        }
        UnknownFieldSetLite unknownFieldSetLite2 = unknownFieldSetLite;
        GeneratedMessageLite.ExtendableMessage extendableMessage = (GeneratedMessageLite.ExtendableMessage) obj;
        FieldSet fieldSet = extendableMessage.extensions;
        if (fieldSet.isImmutable) {
            extendableMessage.extensions = fieldSet.m2770clone();
        }
        FieldSet fieldSet2 = extendableMessage.extensions;
        int i3 = i;
        GeneratedMessageLite.GeneratedExtension generatedExtension = null;
        while (i3 < i2) {
            int decodeVarint322 = ArrayDecoders.decodeVarint32(bArr, i3, registers);
            int i4 = registers.int1;
            int i5 = 2;
            MessageLite messageLite = messageSetSchema.defaultInstance;
            ExtensionSchema extensionSchema = messageSetSchema.extensionSchema;
            ExtensionRegistryLite extensionRegistryLite = registers.extensionRegistry;
            if (i4 == 11) {
                int i6 = 0;
                ByteString byteString = null;
                while (decodeVarint322 < i2) {
                    decodeVarint322 = ArrayDecoders.decodeVarint32(bArr, decodeVarint322, registers);
                    int i7 = registers.int1;
                    int i8 = i7 >>> 3;
                    int i9 = i7 & 7;
                    if (i8 != i5) {
                        if (i8 == 3) {
                            if (generatedExtension != null) {
                                decodeVarint32 = ArrayDecoders.decodeMessageField(Protobuf.INSTANCE.schemaFor(generatedExtension.messageDefaultInstance.getClass()), bArr, decodeVarint322, i2, registers);
                                fieldSet2.setField(generatedExtension.descriptor, registers.object1);
                            } else if (i9 == 2) {
                                decodeVarint32 = ArrayDecoders.decodeBytes(bArr, decodeVarint322, registers);
                                byteString = (ByteString) registers.object1;
                            }
                            decodeVarint322 = decodeVarint32;
                            i5 = 2;
                        }
                        if (i7 != 12) {
                            break;
                        }
                        decodeVarint32 = ArrayDecoders.skipField(i7, bArr, decodeVarint322, i2, registers);
                        decodeVarint322 = decodeVarint32;
                        i5 = 2;
                    } else {
                        if (i9 == 0) {
                            decodeVarint32 = ArrayDecoders.decodeVarint32(bArr, decodeVarint322, registers);
                            i6 = registers.int1;
                            generatedExtension = extensionSchema.findExtensionByNumber(extensionRegistryLite, messageLite, i6);
                            decodeVarint322 = decodeVarint32;
                            i5 = 2;
                        }
                        if (i7 != 12) {
                        }
                    }
                }
                if (byteString != null) {
                    unknownFieldSetLite2.storeField((i6 << 3) | 2, byteString);
                }
                messageSetSchema = this;
                i3 = decodeVarint322;
            } else if ((i4 & 7) == 2) {
                GeneratedMessageLite.GeneratedExtension findExtensionByNumber = extensionSchema.findExtensionByNumber(extensionRegistryLite, messageLite, i4 >>> 3);
                if (findExtensionByNumber != null) {
                    i3 = ArrayDecoders.decodeMessageField(Protobuf.INSTANCE.schemaFor(findExtensionByNumber.messageDefaultInstance.getClass()), bArr, decodeVarint322, i2, registers);
                    fieldSet2.setField(findExtensionByNumber.descriptor, registers.object1);
                } else {
                    i3 = ArrayDecoders.decodeUnknownField(i4, bArr, decodeVarint322, i2, unknownFieldSetLite2, registers);
                }
                generatedExtension = findExtensionByNumber;
            } else {
                i3 = ArrayDecoders.skipField(i4, bArr, decodeVarint322, i2, registers);
            }
        }
        if (i3 != i2) {
            throw InvalidProtocolBufferException.parseFailure();
        }
    }

    @Override // com.google.protobuf.Schema
    public final void mergeFrom(Object obj, Reader reader, ExtensionRegistryLite extensionRegistryLite) {
        CodedInputStreamReader codedInputStreamReader;
        UnknownFieldSchema unknownFieldSchema = this.unknownFieldSchema;
        UnknownFieldSetLite builderFromMessage = unknownFieldSchema.getBuilderFromMessage(obj);
        ExtensionSchema extensionSchema = this.extensionSchema;
        FieldSet mutableExtensions = extensionSchema.getMutableExtensions(obj);
        do {
            try {
                codedInputStreamReader = (CodedInputStreamReader) reader;
                if (codedInputStreamReader.getFieldNumber() == Integer.MAX_VALUE) {
                    break;
                }
            } finally {
                unknownFieldSchema.setBuilderToMessage(obj, builderFromMessage);
            }
        } while (parseMessageSetItemOrUnknownField(codedInputStreamReader, extensionRegistryLite, extensionSchema, mutableExtensions, unknownFieldSchema, builderFromMessage));
    }
}
