package com.google.protobuf;

import com.google.protobuf.CodedInputStream;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageLite;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ExtensionSchemaLite extends ExtensionSchema {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.google.protobuf.ExtensionSchemaLite$1 */
    public abstract /* synthetic */ class AbstractC45281 {
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
        extensionRegistryLite.getClass();
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
            extendableMessage.extensions = fieldSet.m2770clone();
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
    public final Object parseExtension(Object obj, Reader reader, Object obj2, ExtensionRegistryLite extensionRegistryLite, FieldSet fieldSet, Object obj3, UnknownFieldSchema unknownFieldSchema) {
        Object valueOf;
        Object newInstance;
        Object field;
        ArrayList arrayList;
        GeneratedMessageLite.GeneratedExtension generatedExtension = (GeneratedMessageLite.GeneratedExtension) obj2;
        GeneratedMessageLite.ExtensionDescriptor extensionDescriptor = generatedExtension.descriptor;
        int i = extensionDescriptor.number;
        if (extensionDescriptor.isRepeated && extensionDescriptor.isPacked) {
            switch (AbstractC45281.$SwitchMap$com$google$protobuf$WireFormat$FieldType[extensionDescriptor.type.ordinal()]) {
                case 1:
                    arrayList = new ArrayList();
                    ((CodedInputStreamReader) reader).readDoubleList(arrayList);
                    break;
                case 2:
                    arrayList = new ArrayList();
                    ((CodedInputStreamReader) reader).readFloatList(arrayList);
                    break;
                case 3:
                    arrayList = new ArrayList();
                    ((CodedInputStreamReader) reader).readInt64List(arrayList);
                    break;
                case 4:
                    arrayList = new ArrayList();
                    ((CodedInputStreamReader) reader).readUInt64List(arrayList);
                    break;
                case 5:
                    arrayList = new ArrayList();
                    ((CodedInputStreamReader) reader).readInt32List(arrayList);
                    break;
                case 6:
                    arrayList = new ArrayList();
                    ((CodedInputStreamReader) reader).readFixed64List(arrayList);
                    break;
                case 7:
                    arrayList = new ArrayList();
                    ((CodedInputStreamReader) reader).readFixed32List(arrayList);
                    break;
                case 8:
                    arrayList = new ArrayList();
                    ((CodedInputStreamReader) reader).readBoolList(arrayList);
                    break;
                case 9:
                    arrayList = new ArrayList();
                    ((CodedInputStreamReader) reader).readUInt32List(arrayList);
                    break;
                case 10:
                    arrayList = new ArrayList();
                    ((CodedInputStreamReader) reader).readSFixed32List(arrayList);
                    break;
                case 11:
                    arrayList = new ArrayList();
                    ((CodedInputStreamReader) reader).readSFixed64List(arrayList);
                    break;
                case 12:
                    arrayList = new ArrayList();
                    ((CodedInputStreamReader) reader).readSInt32List(arrayList);
                    break;
                case 13:
                    arrayList = new ArrayList();
                    ((CodedInputStreamReader) reader).readSInt64List(arrayList);
                    break;
                case 14:
                    arrayList = new ArrayList();
                    ((CodedInputStreamReader) reader).readEnumList(arrayList);
                    obj3 = SchemaUtil.filterUnknownEnumList(obj, i, arrayList, extensionDescriptor.enumTypeMap, obj3, unknownFieldSchema);
                    break;
                default:
                    StringBuilder sb = new StringBuilder("Type cannot be packed: ");
                    sb.append(extensionDescriptor.type);
                    throw new IllegalStateException(sb.toString());
            }
            fieldSet.setField(extensionDescriptor, arrayList);
        } else {
            WireFormat$FieldType wireFormat$FieldType = extensionDescriptor.type;
            if (wireFormat$FieldType != WireFormat$FieldType.ENUM) {
                int i2 = AbstractC45281.$SwitchMap$com$google$protobuf$WireFormat$FieldType[wireFormat$FieldType.ordinal()];
                MessageLite messageLite = generatedExtension.messageDefaultInstance;
                switch (i2) {
                    case 1:
                        CodedInputStreamReader codedInputStreamReader = (CodedInputStreamReader) reader;
                        codedInputStreamReader.requireWireType(1);
                        valueOf = Double.valueOf(codedInputStreamReader.input.readDouble());
                        break;
                    case 2:
                        CodedInputStreamReader codedInputStreamReader2 = (CodedInputStreamReader) reader;
                        codedInputStreamReader2.requireWireType(5);
                        valueOf = Float.valueOf(codedInputStreamReader2.input.readFloat());
                        break;
                    case 3:
                        valueOf = Long.valueOf(((CodedInputStreamReader) reader).readInt64());
                        break;
                    case 4:
                        CodedInputStreamReader codedInputStreamReader3 = (CodedInputStreamReader) reader;
                        codedInputStreamReader3.requireWireType(0);
                        valueOf = Long.valueOf(codedInputStreamReader3.input.readUInt64());
                        break;
                    case 5:
                        valueOf = Integer.valueOf(((CodedInputStreamReader) reader).readInt32());
                        break;
                    case 6:
                        valueOf = Long.valueOf(((CodedInputStreamReader) reader).readFixed64());
                        break;
                    case 7:
                        valueOf = Integer.valueOf(((CodedInputStreamReader) reader).readFixed32());
                        break;
                    case 8:
                        CodedInputStreamReader codedInputStreamReader4 = (CodedInputStreamReader) reader;
                        codedInputStreamReader4.requireWireType(0);
                        valueOf = Boolean.valueOf(codedInputStreamReader4.input.readBool());
                        break;
                    case 9:
                        valueOf = Integer.valueOf(((CodedInputStreamReader) reader).readUInt32());
                        break;
                    case 10:
                        CodedInputStreamReader codedInputStreamReader5 = (CodedInputStreamReader) reader;
                        codedInputStreamReader5.requireWireType(5);
                        valueOf = Integer.valueOf(codedInputStreamReader5.input.readSFixed32());
                        break;
                    case 11:
                        CodedInputStreamReader codedInputStreamReader6 = (CodedInputStreamReader) reader;
                        codedInputStreamReader6.requireWireType(1);
                        valueOf = Long.valueOf(codedInputStreamReader6.input.readSFixed64());
                        break;
                    case 12:
                        CodedInputStreamReader codedInputStreamReader7 = (CodedInputStreamReader) reader;
                        codedInputStreamReader7.requireWireType(0);
                        valueOf = Integer.valueOf(codedInputStreamReader7.input.readSInt32());
                        break;
                    case 13:
                        CodedInputStreamReader codedInputStreamReader8 = (CodedInputStreamReader) reader;
                        codedInputStreamReader8.requireWireType(0);
                        valueOf = Long.valueOf(codedInputStreamReader8.input.readSInt64());
                        break;
                    case 14:
                        throw new IllegalStateException("Shouldn't reach here.");
                    case 15:
                        valueOf = ((CodedInputStreamReader) reader).readBytes();
                        break;
                    case 16:
                        CodedInputStreamReader codedInputStreamReader9 = (CodedInputStreamReader) reader;
                        codedInputStreamReader9.requireWireType(2);
                        valueOf = codedInputStreamReader9.input.readString();
                        break;
                    case 17:
                        if (!extensionDescriptor.isRepeated) {
                            Object field2 = fieldSet.getField(extensionDescriptor);
                            if (field2 instanceof GeneratedMessageLite) {
                                Protobuf protobuf = Protobuf.INSTANCE;
                                protobuf.getClass();
                                Schema schemaFor = protobuf.schemaFor(field2.getClass());
                                if (!((GeneratedMessageLite) field2).isMutable()) {
                                    Object newInstance2 = schemaFor.newInstance();
                                    schemaFor.mergeFrom(newInstance2, field2);
                                    fieldSet.setField(extensionDescriptor, newInstance2);
                                    field2 = newInstance2;
                                }
                                CodedInputStreamReader codedInputStreamReader10 = (CodedInputStreamReader) reader;
                                codedInputStreamReader10.requireWireType(3);
                                codedInputStreamReader10.mergeGroupFieldInternal(field2, schemaFor, extensionRegistryLite);
                                return obj3;
                            }
                        }
                        Class<?> cls = messageLite.getClass();
                        CodedInputStreamReader codedInputStreamReader11 = (CodedInputStreamReader) reader;
                        codedInputStreamReader11.requireWireType(3);
                        Schema schemaFor2 = Protobuf.INSTANCE.schemaFor(cls);
                        newInstance = schemaFor2.newInstance();
                        codedInputStreamReader11.mergeGroupFieldInternal(newInstance, schemaFor2, extensionRegistryLite);
                        schemaFor2.makeImmutable(newInstance);
                        valueOf = newInstance;
                        break;
                    case 18:
                        if (!extensionDescriptor.isRepeated) {
                            Object field3 = fieldSet.getField(extensionDescriptor);
                            if (field3 instanceof GeneratedMessageLite) {
                                Protobuf protobuf2 = Protobuf.INSTANCE;
                                protobuf2.getClass();
                                Schema schemaFor3 = protobuf2.schemaFor(field3.getClass());
                                if (!((GeneratedMessageLite) field3).isMutable()) {
                                    Object newInstance3 = schemaFor3.newInstance();
                                    schemaFor3.mergeFrom(newInstance3, field3);
                                    fieldSet.setField(extensionDescriptor, newInstance3);
                                    field3 = newInstance3;
                                }
                                CodedInputStreamReader codedInputStreamReader12 = (CodedInputStreamReader) reader;
                                codedInputStreamReader12.requireWireType(2);
                                codedInputStreamReader12.mergeMessageFieldInternal(field3, schemaFor3, extensionRegistryLite);
                                return obj3;
                            }
                        }
                        Class<?> cls2 = messageLite.getClass();
                        CodedInputStreamReader codedInputStreamReader13 = (CodedInputStreamReader) reader;
                        codedInputStreamReader13.requireWireType(2);
                        Schema schemaFor4 = Protobuf.INSTANCE.schemaFor(cls2);
                        newInstance = schemaFor4.newInstance();
                        codedInputStreamReader13.mergeMessageFieldInternal(newInstance, schemaFor4, extensionRegistryLite);
                        schemaFor4.makeImmutable(newInstance);
                        valueOf = newInstance;
                        break;
                    default:
                        valueOf = null;
                        break;
                }
            } else {
                int readInt32 = ((CodedInputStreamReader) reader).readInt32();
                if (extensionDescriptor.enumTypeMap.findValueByNumber(readInt32) == null) {
                    return SchemaUtil.storeUnknownEnum(obj, i, readInt32, obj3, unknownFieldSchema);
                }
                valueOf = Integer.valueOf(readInt32);
            }
            if (extensionDescriptor.isRepeated) {
                fieldSet.addRepeatedField(extensionDescriptor, valueOf);
            } else {
                int i3 = AbstractC45281.$SwitchMap$com$google$protobuf$WireFormat$FieldType[extensionDescriptor.type.ordinal()];
                if ((i3 == 17 || i3 == 18) && (field = fieldSet.getField(extensionDescriptor)) != null) {
                    GeneratedMessageLite.Builder builder = ((GeneratedMessageLite) ((MessageLite) field)).toBuilder();
                    MessageLite messageLite2 = (MessageLite) valueOf;
                    if (!builder.defaultInstance.getClass().isInstance(messageLite2)) {
                        throw new IllegalArgumentException("mergeFrom(MessageLite) can only merge messages of the same type.");
                    }
                    builder.internalMergeFrom((AbstractMessageLite) messageLite2);
                    valueOf = builder.buildPartial();
                }
                fieldSet.setField(extensionDescriptor, valueOf);
            }
        }
        return obj3;
    }

    @Override // com.google.protobuf.ExtensionSchema
    public final void parseLengthPrefixedMessageSetItem(Reader reader, Object obj, ExtensionRegistryLite extensionRegistryLite, FieldSet fieldSet) {
        GeneratedMessageLite.GeneratedExtension generatedExtension = (GeneratedMessageLite.GeneratedExtension) obj;
        Class<?> cls = generatedExtension.messageDefaultInstance.getClass();
        CodedInputStreamReader codedInputStreamReader = (CodedInputStreamReader) reader;
        codedInputStreamReader.requireWireType(2);
        Schema schemaFor = Protobuf.INSTANCE.schemaFor(cls);
        GeneratedMessageLite newInstance = schemaFor.newInstance();
        codedInputStreamReader.mergeMessageFieldInternal(newInstance, schemaFor, extensionRegistryLite);
        schemaFor.makeImmutable(newInstance);
        fieldSet.setField(generatedExtension.descriptor, newInstance);
    }

    @Override // com.google.protobuf.ExtensionSchema
    public final void parseMessageSetItem(ByteString byteString, Object obj, ExtensionRegistryLite extensionRegistryLite, FieldSet fieldSet) {
        GeneratedMessageLite.GeneratedExtension generatedExtension = (GeneratedMessageLite.GeneratedExtension) obj;
        GeneratedMessageLite generatedMessageLite = (GeneratedMessageLite) generatedExtension.messageDefaultInstance;
        generatedMessageLite.getClass();
        GeneratedMessageLite.Builder builder = (GeneratedMessageLite.Builder) generatedMessageLite.dynamicMethod(GeneratedMessageLite.MethodToInvoke.NEW_BUILDER);
        CodedInputStream.ArrayDecoder newCodedInput = byteString.newCodedInput();
        builder.mergeFrom(newCodedInput, extensionRegistryLite);
        fieldSet.setField(generatedExtension.descriptor, builder.buildPartial());
        newCodedInput.checkLastTagWas(0);
    }

    @Override // com.google.protobuf.ExtensionSchema
    public final void serializeExtension(CodedOutputStreamWriter codedOutputStreamWriter, Map.Entry entry) {
        GeneratedMessageLite.ExtensionDescriptor extensionDescriptor = (GeneratedMessageLite.ExtensionDescriptor) entry.getKey();
        if (extensionDescriptor.isRepeated) {
            switch (AbstractC45281.$SwitchMap$com$google$protobuf$WireFormat$FieldType[extensionDescriptor.type.ordinal()]) {
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
                        SchemaUtil.writeGroupList(extensionDescriptor.number, (List) entry.getValue(), codedOutputStreamWriter, Protobuf.INSTANCE.schemaFor(list.get(0).getClass()));
                        break;
                    }
                    break;
                case 18:
                    List list2 = (List) entry.getValue();
                    if (list2 != null && !list2.isEmpty()) {
                        SchemaUtil.writeMessageList(extensionDescriptor.number, (List) entry.getValue(), codedOutputStreamWriter, Protobuf.INSTANCE.schemaFor(list2.get(0).getClass()));
                        break;
                    }
                    break;
            }
        }
        switch (AbstractC45281.$SwitchMap$com$google$protobuf$WireFormat$FieldType[extensionDescriptor.type.ordinal()]) {
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
                codedOutputStreamWriter.writeGroup(extensionDescriptor.number, Protobuf.INSTANCE.schemaFor(entry.getValue().getClass()), entry.getValue());
                break;
            case 18:
                codedOutputStreamWriter.writeMessage(extensionDescriptor.number, Protobuf.INSTANCE.schemaFor(entry.getValue().getClass()), entry.getValue());
                break;
        }
    }
}
