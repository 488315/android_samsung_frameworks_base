package com.google.protobuf;

import com.google.protobuf.CodedInputStream;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageLite;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
public final class ExtensionSchemaLite extends ExtensionSchema {

    /* renamed from: com.google.protobuf.ExtensionSchemaLite$1, reason: invalid class name */
    public abstract /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$com$google$protobuf$WireFormat$FieldType;

        static {
            int[] iArr = new int[WireFormat$FieldType.values().length];
            $SwitchMap$com$google$protobuf$WireFormat$FieldType = iArr;
            try {
                iArr[WireFormat$FieldType.DOUBLE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat$FieldType.FLOAT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat$FieldType.INT64.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat$FieldType.UINT64.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat$FieldType.INT32.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat$FieldType.FIXED64.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat$FieldType.FIXED32.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat$FieldType.BOOL.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat$FieldType.UINT32.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat$FieldType.SFIXED32.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat$FieldType.SFIXED64.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat$FieldType.SINT32.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat$FieldType.SINT64.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat$FieldType.ENUM.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat$FieldType.BYTES.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat$FieldType.STRING.ordinal()] = 16;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat$FieldType.GROUP.ordinal()] = 17;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat$FieldType.MESSAGE.ordinal()] = 18;
            } catch (NoSuchFieldError unused18) {
            }
        }
    }

    @Override // com.google.protobuf.ExtensionSchema
    public final int extensionNumber(Map.Entry entry) {
        return ((GeneratedMessageLite.ExtensionDescriptor) entry.getKey()).number;
    }

    @Override // com.google.protobuf.ExtensionSchema
    public final GeneratedMessageLite.GeneratedExtension findExtensionByNumber(ExtensionRegistryLite extensionRegistryLite, MessageLite messageLite, int i) {
        return (GeneratedMessageLite.GeneratedExtension) extensionRegistryLite.extensionsByNumber.get(new ExtensionRegistryLite.ObjectIntPair(messageLite, i));
    }

    @Override // com.google.protobuf.ExtensionSchema
    public final FieldSet getExtensions(Object obj) {
        return ((GeneratedMessageLite.ExtendableMessage) obj).extensions;
    }

    @Override // com.google.protobuf.ExtensionSchema
    public final FieldSet getMutableExtensions(Object obj) {
        GeneratedMessageLite.ExtendableMessage extendableMessage = (GeneratedMessageLite.ExtendableMessage) obj;
        FieldSet fieldSet = extendableMessage.extensions;
        if (fieldSet.isImmutable) {
            extendableMessage.extensions = fieldSet.m3287clone();
        }
        return extendableMessage.extensions;
    }

    @Override // com.google.protobuf.ExtensionSchema
    public final boolean hasExtensions(MessageLite messageLite) {
        return messageLite instanceof GeneratedMessageLite.ExtendableMessage;
    }

    @Override // com.google.protobuf.ExtensionSchema
    public final void makeImmutable(Object obj) {
        ((GeneratedMessageLite.ExtendableMessage) obj).extensions.makeImmutable();
    }

    @Override // com.google.protobuf.ExtensionSchema
    public final Object parseExtension(Object obj, CodedInputStreamReader codedInputStreamReader, Object obj2, ExtensionRegistryLite extensionRegistryLite, FieldSet fieldSet, Object obj3, UnknownFieldSchema unknownFieldSchema) throws InvalidProtocolBufferException {
        Object objValueOf;
        Object field;
        ArrayList arrayList;
        GeneratedMessageLite.GeneratedExtension generatedExtension = (GeneratedMessageLite.GeneratedExtension) obj2;
        GeneratedMessageLite.ExtensionDescriptor extensionDescriptor = generatedExtension.descriptor;
        int i = extensionDescriptor.number;
        if (extensionDescriptor.isRepeated && extensionDescriptor.isPacked) {
            switch (AnonymousClass1.$SwitchMap$com$google$protobuf$WireFormat$FieldType[extensionDescriptor.type.ordinal()]) {
                case 1:
                    arrayList = new ArrayList();
                    codedInputStreamReader.readDoubleList(arrayList);
                    break;
                case 2:
                    arrayList = new ArrayList();
                    codedInputStreamReader.readFloatList(arrayList);
                    break;
                case 3:
                    arrayList = new ArrayList();
                    codedInputStreamReader.readInt64List(arrayList);
                    break;
                case 4:
                    arrayList = new ArrayList();
                    codedInputStreamReader.readUInt64List(arrayList);
                    break;
                case 5:
                    arrayList = new ArrayList();
                    codedInputStreamReader.readInt32List(arrayList);
                    break;
                case 6:
                    arrayList = new ArrayList();
                    codedInputStreamReader.readFixed64List(arrayList);
                    break;
                case 7:
                    arrayList = new ArrayList();
                    codedInputStreamReader.readFixed32List(arrayList);
                    break;
                case 8:
                    arrayList = new ArrayList();
                    codedInputStreamReader.readBoolList(arrayList);
                    break;
                case 9:
                    arrayList = new ArrayList();
                    codedInputStreamReader.readUInt32List(arrayList);
                    break;
                case 10:
                    arrayList = new ArrayList();
                    codedInputStreamReader.readSFixed32List(arrayList);
                    break;
                case 11:
                    arrayList = new ArrayList();
                    codedInputStreamReader.readSFixed64List(arrayList);
                    break;
                case 12:
                    arrayList = new ArrayList();
                    codedInputStreamReader.readSInt32List(arrayList);
                    break;
                case 13:
                    arrayList = new ArrayList();
                    codedInputStreamReader.readSInt64List(arrayList);
                    break;
                case 14:
                    arrayList = new ArrayList();
                    codedInputStreamReader.readEnumList(arrayList);
                    obj3 = SchemaUtil.filterUnknownEnumList(obj, i, arrayList, extensionDescriptor.enumTypeMap, obj3, unknownFieldSchema);
                    break;
                default:
                    throw new IllegalStateException("Type cannot be packed: " + extensionDescriptor.type);
            }
            fieldSet.setField(extensionDescriptor, arrayList);
            return obj3;
        }
        WireFormat$FieldType wireFormat$FieldType = extensionDescriptor.type;
        if (wireFormat$FieldType != WireFormat$FieldType.ENUM) {
            int i2 = AnonymousClass1.$SwitchMap$com$google$protobuf$WireFormat$FieldType[wireFormat$FieldType.ordinal()];
            MessageLite messageLite = generatedExtension.messageDefaultInstance;
            switch (i2) {
                case 1:
                    codedInputStreamReader.requireWireType(1);
                    objValueOf = Double.valueOf(codedInputStreamReader.input.readDouble());
                    break;
                case 2:
                    codedInputStreamReader.requireWireType(5);
                    objValueOf = Float.valueOf(codedInputStreamReader.input.readFloat());
                    break;
                case 3:
                    codedInputStreamReader.requireWireType(0);
                    objValueOf = Long.valueOf(codedInputStreamReader.input.readInt64());
                    break;
                case 4:
                    codedInputStreamReader.requireWireType(0);
                    objValueOf = Long.valueOf(codedInputStreamReader.input.readUInt64());
                    break;
                case 5:
                    codedInputStreamReader.requireWireType(0);
                    objValueOf = Integer.valueOf(codedInputStreamReader.input.readInt32());
                    break;
                case 6:
                    codedInputStreamReader.requireWireType(1);
                    objValueOf = Long.valueOf(codedInputStreamReader.input.readFixed64());
                    break;
                case 7:
                    codedInputStreamReader.requireWireType(5);
                    objValueOf = Integer.valueOf(codedInputStreamReader.input.readFixed32());
                    break;
                case 8:
                    codedInputStreamReader.requireWireType(0);
                    objValueOf = Boolean.valueOf(codedInputStreamReader.input.readBool());
                    break;
                case 9:
                    codedInputStreamReader.requireWireType(0);
                    objValueOf = Integer.valueOf(codedInputStreamReader.input.readUInt32());
                    break;
                case 10:
                    codedInputStreamReader.requireWireType(5);
                    objValueOf = Integer.valueOf(codedInputStreamReader.input.readSFixed32());
                    break;
                case 11:
                    codedInputStreamReader.requireWireType(1);
                    objValueOf = Long.valueOf(codedInputStreamReader.input.readSFixed64());
                    break;
                case 12:
                    codedInputStreamReader.requireWireType(0);
                    objValueOf = Integer.valueOf(codedInputStreamReader.input.readSInt32());
                    break;
                case 13:
                    codedInputStreamReader.requireWireType(0);
                    objValueOf = Long.valueOf(codedInputStreamReader.input.readSInt64());
                    break;
                case 14:
                    throw new IllegalStateException("Shouldn't reach here.");
                case 15:
                    objValueOf = codedInputStreamReader.readBytes();
                    break;
                case 16:
                    codedInputStreamReader.requireWireType(2);
                    objValueOf = codedInputStreamReader.input.readString();
                    break;
                case 17:
                    if (!extensionDescriptor.isRepeated) {
                        Object field2 = fieldSet.getField(extensionDescriptor);
                        if (field2 instanceof GeneratedMessageLite) {
                            Schema schemaSchemaFor = Protobuf.INSTANCE.schemaFor(field2);
                            if (!((GeneratedMessageLite) field2).isMutable()) {
                                GeneratedMessageLite generatedMessageLiteNewInstance = schemaSchemaFor.newInstance();
                                schemaSchemaFor.mergeFrom(generatedMessageLiteNewInstance, field2);
                                fieldSet.setField(extensionDescriptor, generatedMessageLiteNewInstance);
                                field2 = generatedMessageLiteNewInstance;
                            }
                            codedInputStreamReader.requireWireType(3);
                            codedInputStreamReader.mergeGroupFieldInternal(field2, schemaSchemaFor, extensionRegistryLite);
                            return obj3;
                        }
                    }
                    Class<?> cls = messageLite.getClass();
                    codedInputStreamReader.requireWireType(3);
                    Schema schemaSchemaFor2 = Protobuf.INSTANCE.schemaFor((Class) cls);
                    GeneratedMessageLite generatedMessageLiteNewInstance2 = schemaSchemaFor2.newInstance();
                    codedInputStreamReader.mergeGroupFieldInternal(generatedMessageLiteNewInstance2, schemaSchemaFor2, extensionRegistryLite);
                    schemaSchemaFor2.makeImmutable(generatedMessageLiteNewInstance2);
                    objValueOf = generatedMessageLiteNewInstance2;
                    break;
                case 18:
                    if (!extensionDescriptor.isRepeated) {
                        Object field3 = fieldSet.getField(extensionDescriptor);
                        if (field3 instanceof GeneratedMessageLite) {
                            Schema schemaSchemaFor3 = Protobuf.INSTANCE.schemaFor(field3);
                            if (!((GeneratedMessageLite) field3).isMutable()) {
                                GeneratedMessageLite generatedMessageLiteNewInstance3 = schemaSchemaFor3.newInstance();
                                schemaSchemaFor3.mergeFrom(generatedMessageLiteNewInstance3, field3);
                                fieldSet.setField(extensionDescriptor, generatedMessageLiteNewInstance3);
                                field3 = generatedMessageLiteNewInstance3;
                            }
                            codedInputStreamReader.requireWireType(2);
                            codedInputStreamReader.mergeMessageFieldInternal(field3, schemaSchemaFor3, extensionRegistryLite);
                            return obj3;
                        }
                    }
                    objValueOf = codedInputStreamReader.readMessage(messageLite.getClass(), extensionRegistryLite);
                    break;
                default:
                    objValueOf = null;
                    break;
            }
        } else {
            codedInputStreamReader.requireWireType(0);
            int int32 = codedInputStreamReader.input.readInt32();
            if (extensionDescriptor.enumTypeMap.findValueByNumber(int32) == null) {
                return SchemaUtil.storeUnknownEnum(obj, i, int32, obj3, unknownFieldSchema);
            }
            objValueOf = Integer.valueOf(int32);
        }
        if (extensionDescriptor.isRepeated) {
            fieldSet.addRepeatedField(extensionDescriptor, objValueOf);
            return obj3;
        }
        int i3 = AnonymousClass1.$SwitchMap$com$google$protobuf$WireFormat$FieldType[extensionDescriptor.type.ordinal()];
        if ((i3 == 17 || i3 == 18) && (field = fieldSet.getField(extensionDescriptor)) != null) {
            GeneratedMessageLite.Builder builder = ((GeneratedMessageLite) ((MessageLite) field)).toBuilder();
            MessageLite messageLite2 = (MessageLite) objValueOf;
            if (!builder.defaultInstance.getClass().isInstance(messageLite2)) {
                throw new IllegalArgumentException("mergeFrom(MessageLite) can only merge messages of the same type.");
            }
            builder.mergeFrom((GeneratedMessageLite) ((AbstractMessageLite) messageLite2));
            objValueOf = builder.buildPartial();
        }
        fieldSet.setField(extensionDescriptor, objValueOf);
        return obj3;
    }

    @Override // com.google.protobuf.ExtensionSchema
    public final void parseLengthPrefixedMessageSetItem(CodedInputStreamReader codedInputStreamReader, Object obj, ExtensionRegistryLite extensionRegistryLite, FieldSet fieldSet) throws InvalidProtocolBufferException {
        GeneratedMessageLite.GeneratedExtension generatedExtension = (GeneratedMessageLite.GeneratedExtension) obj;
        fieldSet.setField(generatedExtension.descriptor, codedInputStreamReader.readMessage(generatedExtension.messageDefaultInstance.getClass(), extensionRegistryLite));
    }

    @Override // com.google.protobuf.ExtensionSchema
    public final void parseMessageSetItem(ByteString byteString, Object obj, ExtensionRegistryLite extensionRegistryLite, FieldSet fieldSet) throws IOException {
        GeneratedMessageLite.GeneratedExtension generatedExtension = (GeneratedMessageLite.GeneratedExtension) obj;
        GeneratedMessageLite generatedMessageLite = (GeneratedMessageLite) generatedExtension.messageDefaultInstance;
        generatedMessageLite.getClass();
        GeneratedMessageLite.Builder builder = (GeneratedMessageLite.Builder) generatedMessageLite.dynamicMethod(GeneratedMessageLite.MethodToInvoke.NEW_BUILDER);
        CodedInputStream.ArrayDecoder arrayDecoderNewCodedInput = byteString.newCodedInput();
        builder.copyOnWrite();
        try {
            Protobuf.INSTANCE.schemaFor(builder.instance).mergeFrom(builder.instance, CodedInputStreamReader.forCodedInput(arrayDecoderNewCodedInput), extensionRegistryLite);
            fieldSet.setField(generatedExtension.descriptor, builder.buildPartial());
            arrayDecoderNewCodedInput.checkLastTagWas(0);
        } catch (RuntimeException e) {
            if (!(e.getCause() instanceof IOException)) {
                throw e;
            }
            throw ((IOException) e.getCause());
        }
    }

    @Override // com.google.protobuf.ExtensionSchema
    public final void serializeExtension(CodedOutputStreamWriter codedOutputStreamWriter, Map.Entry entry) {
        GeneratedMessageLite.ExtensionDescriptor extensionDescriptor = (GeneratedMessageLite.ExtensionDescriptor) entry.getKey();
        if (!extensionDescriptor.isRepeated) {
            switch (AnonymousClass1.$SwitchMap$com$google$protobuf$WireFormat$FieldType[extensionDescriptor.type.ordinal()]) {
                case 1:
                    codedOutputStreamWriter.writeDouble(((Double) entry.getValue()).doubleValue(), extensionDescriptor.number);
                    break;
                case 2:
                    codedOutputStreamWriter.writeFloat(((Float) entry.getValue()).floatValue(), extensionDescriptor.number);
                    break;
                case 3:
                    codedOutputStreamWriter.writeInt64(extensionDescriptor.number, ((Long) entry.getValue()).longValue());
                    break;
                case 4:
                    codedOutputStreamWriter.writeUInt64(extensionDescriptor.number, ((Long) entry.getValue()).longValue());
                    break;
                case 5:
                    codedOutputStreamWriter.writeInt32(extensionDescriptor.number, ((Integer) entry.getValue()).intValue());
                    break;
                case 6:
                    codedOutputStreamWriter.writeFixed64(extensionDescriptor.number, ((Long) entry.getValue()).longValue());
                    break;
                case 7:
                    codedOutputStreamWriter.writeFixed32(extensionDescriptor.number, ((Integer) entry.getValue()).intValue());
                    break;
                case 8:
                    codedOutputStreamWriter.writeBool(extensionDescriptor.number, ((Boolean) entry.getValue()).booleanValue());
                    break;
                case 9:
                    codedOutputStreamWriter.writeUInt32(extensionDescriptor.number, ((Integer) entry.getValue()).intValue());
                    break;
                case 10:
                    codedOutputStreamWriter.writeSFixed32(extensionDescriptor.number, ((Integer) entry.getValue()).intValue());
                    break;
                case 11:
                    codedOutputStreamWriter.writeSFixed64(extensionDescriptor.number, ((Long) entry.getValue()).longValue());
                    break;
                case 12:
                    codedOutputStreamWriter.writeSInt32(extensionDescriptor.number, ((Integer) entry.getValue()).intValue());
                    break;
                case 13:
                    codedOutputStreamWriter.writeSInt64(extensionDescriptor.number, ((Long) entry.getValue()).longValue());
                    break;
                case 14:
                    codedOutputStreamWriter.writeInt32(extensionDescriptor.number, ((Integer) entry.getValue()).intValue());
                    break;
                case 15:
                    codedOutputStreamWriter.writeBytes(extensionDescriptor.number, (ByteString) entry.getValue());
                    break;
                case 16:
                    codedOutputStreamWriter.output.writeString(extensionDescriptor.number, (String) entry.getValue());
                    break;
                case 17:
                    codedOutputStreamWriter.writeGroup(extensionDescriptor.number, entry.getValue(), Protobuf.INSTANCE.schemaFor((Class) entry.getValue().getClass()));
                    break;
                case 18:
                    codedOutputStreamWriter.writeMessage(extensionDescriptor.number, entry.getValue(), Protobuf.INSTANCE.schemaFor((Class) entry.getValue().getClass()));
                    break;
            }
        }
        switch (AnonymousClass1.$SwitchMap$com$google$protobuf$WireFormat$FieldType[extensionDescriptor.type.ordinal()]) {
            case 1:
                SchemaUtil.writeDoubleList(extensionDescriptor.number, (List) entry.getValue(), codedOutputStreamWriter, extensionDescriptor.isPacked);
                break;
            case 2:
                SchemaUtil.writeFloatList(extensionDescriptor.number, (List) entry.getValue(), codedOutputStreamWriter, extensionDescriptor.isPacked);
                break;
            case 3:
                SchemaUtil.writeInt64List(extensionDescriptor.number, (List) entry.getValue(), codedOutputStreamWriter, extensionDescriptor.isPacked);
                break;
            case 4:
                SchemaUtil.writeUInt64List(extensionDescriptor.number, (List) entry.getValue(), codedOutputStreamWriter, extensionDescriptor.isPacked);
                break;
            case 5:
                SchemaUtil.writeInt32List(extensionDescriptor.number, (List) entry.getValue(), codedOutputStreamWriter, extensionDescriptor.isPacked);
                break;
            case 6:
                SchemaUtil.writeFixed64List(extensionDescriptor.number, (List) entry.getValue(), codedOutputStreamWriter, extensionDescriptor.isPacked);
                break;
            case 7:
                SchemaUtil.writeFixed32List(extensionDescriptor.number, (List) entry.getValue(), codedOutputStreamWriter, extensionDescriptor.isPacked);
                break;
            case 8:
                SchemaUtil.writeBoolList(extensionDescriptor.number, (List) entry.getValue(), codedOutputStreamWriter, extensionDescriptor.isPacked);
                break;
            case 9:
                SchemaUtil.writeUInt32List(extensionDescriptor.number, (List) entry.getValue(), codedOutputStreamWriter, extensionDescriptor.isPacked);
                break;
            case 10:
                SchemaUtil.writeSFixed32List(extensionDescriptor.number, (List) entry.getValue(), codedOutputStreamWriter, extensionDescriptor.isPacked);
                break;
            case 11:
                SchemaUtil.writeSFixed64List(extensionDescriptor.number, (List) entry.getValue(), codedOutputStreamWriter, extensionDescriptor.isPacked);
                break;
            case 12:
                SchemaUtil.writeSInt32List(extensionDescriptor.number, (List) entry.getValue(), codedOutputStreamWriter, extensionDescriptor.isPacked);
                break;
            case 13:
                SchemaUtil.writeSInt64List(extensionDescriptor.number, (List) entry.getValue(), codedOutputStreamWriter, extensionDescriptor.isPacked);
                break;
            case 14:
                SchemaUtil.writeInt32List(extensionDescriptor.number, (List) entry.getValue(), codedOutputStreamWriter, extensionDescriptor.isPacked);
                break;
            case 15:
                SchemaUtil.writeBytesList(extensionDescriptor.number, (List) entry.getValue(), codedOutputStreamWriter);
                break;
            case 16:
                SchemaUtil.writeStringList(extensionDescriptor.number, (List) entry.getValue(), codedOutputStreamWriter);
                break;
            case 17:
                List list = (List) entry.getValue();
                if (list != null && !list.isEmpty()) {
                    SchemaUtil.writeGroupList(extensionDescriptor.number, (List) entry.getValue(), codedOutputStreamWriter, Protobuf.INSTANCE.schemaFor((Class) list.get(0).getClass()));
                    break;
                }
                break;
            case 18:
                List list2 = (List) entry.getValue();
                if (list2 != null && !list2.isEmpty()) {
                    SchemaUtil.writeMessageList(extensionDescriptor.number, (List) entry.getValue(), codedOutputStreamWriter, Protobuf.INSTANCE.schemaFor((Class) list2.get(0).getClass()));
                    break;
                }
                break;
        }
    }
}
