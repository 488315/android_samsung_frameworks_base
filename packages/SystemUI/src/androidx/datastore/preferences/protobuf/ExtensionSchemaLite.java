package androidx.datastore.preferences.protobuf;

import androidx.datastore.preferences.protobuf.CodedInputStream;
import androidx.datastore.preferences.protobuf.ExtensionRegistryLite;
import androidx.datastore.preferences.protobuf.GeneratedMessageLite;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ExtensionSchemaLite extends ExtensionSchema {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: androidx.datastore.preferences.protobuf.ExtensionSchemaLite$1, reason: invalid class name */
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

    @Override // androidx.datastore.preferences.protobuf.ExtensionSchema
    public final int extensionNumber(Map.Entry entry) {
        return ((GeneratedMessageLite.ExtensionDescriptor) entry.getKey()).number;
    }

    @Override // androidx.datastore.preferences.protobuf.ExtensionSchema
    public final GeneratedMessageLite.GeneratedExtension findExtensionByNumber(ExtensionRegistryLite extensionRegistryLite, MessageLite messageLite, int i) {
        return (GeneratedMessageLite.GeneratedExtension) extensionRegistryLite.extensionsByNumber.get(new ExtensionRegistryLite.ObjectIntPair(messageLite, i));
    }

    @Override // androidx.datastore.preferences.protobuf.ExtensionSchema
    public final FieldSet getExtensions(Object obj) {
        return ((GeneratedMessageLite.ExtendableMessage) obj).extensions;
    }

    @Override // androidx.datastore.preferences.protobuf.ExtensionSchema
    public final FieldSet getMutableExtensions(Object obj) {
        GeneratedMessageLite.ExtendableMessage extendableMessage = (GeneratedMessageLite.ExtendableMessage) obj;
        FieldSet fieldSet = extendableMessage.extensions;
        if (fieldSet.isImmutable) {
            extendableMessage.extensions = fieldSet.m897clone();
        }
        return extendableMessage.extensions;
    }

    @Override // androidx.datastore.preferences.protobuf.ExtensionSchema
    public final boolean hasExtensions(MessageLite messageLite) {
        return messageLite instanceof GeneratedMessageLite.ExtendableMessage;
    }

    @Override // androidx.datastore.preferences.protobuf.ExtensionSchema
    public final void makeImmutable(Object obj) {
        ((GeneratedMessageLite.ExtendableMessage) obj).extensions.makeImmutable();
    }

    @Override // androidx.datastore.preferences.protobuf.ExtensionSchema
    public final Object parseExtension(CodedInputStreamReader codedInputStreamReader, Object obj, ExtensionRegistryLite extensionRegistryLite, FieldSet fieldSet, Object obj2) {
        Object field;
        List list;
        ArrayList arrayList;
        GeneratedMessageLite.GeneratedExtension generatedExtension = (GeneratedMessageLite.GeneratedExtension) obj;
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
                    extensionDescriptor.getClass();
                    Class cls = SchemaUtil.GENERATED_MESSAGE_CLASS;
                    break;
                default:
                    throw new IllegalStateException("Type cannot be packed: " + extensionDescriptor.type);
            }
            fieldSet.setField(extensionDescriptor, arrayList);
            return obj2;
        }
        WireFormat$FieldType wireFormat$FieldType = extensionDescriptor.type;
        Object obj3 = null;
        if (wireFormat$FieldType == WireFormat$FieldType.ENUM) {
            codedInputStreamReader.requireWireType(0);
            codedInputStreamReader.input.readInt32();
            extensionDescriptor.getClass();
            throw null;
        }
        int[] iArr = AnonymousClass1.$SwitchMap$com$google$protobuf$WireFormat$FieldType;
        int i2 = iArr[wireFormat$FieldType.ordinal()];
        MessageLite messageLite = generatedExtension.messageDefaultInstance;
        switch (i2) {
            case 1:
                codedInputStreamReader.requireWireType(1);
                obj3 = Double.valueOf(codedInputStreamReader.input.readDouble());
                break;
            case 2:
                codedInputStreamReader.requireWireType(5);
                obj3 = Float.valueOf(codedInputStreamReader.input.readFloat());
                break;
            case 3:
                codedInputStreamReader.requireWireType(0);
                obj3 = Long.valueOf(codedInputStreamReader.input.readInt64());
                break;
            case 4:
                codedInputStreamReader.requireWireType(0);
                obj3 = Long.valueOf(codedInputStreamReader.input.readUInt64());
                break;
            case 5:
                codedInputStreamReader.requireWireType(0);
                obj3 = Integer.valueOf(codedInputStreamReader.input.readInt32());
                break;
            case 6:
                codedInputStreamReader.requireWireType(1);
                obj3 = Long.valueOf(codedInputStreamReader.input.readFixed64());
                break;
            case 7:
                codedInputStreamReader.requireWireType(5);
                obj3 = Integer.valueOf(codedInputStreamReader.input.readFixed32());
                break;
            case 8:
                codedInputStreamReader.requireWireType(0);
                obj3 = Boolean.valueOf(codedInputStreamReader.input.readBool());
                break;
            case 9:
                codedInputStreamReader.requireWireType(0);
                obj3 = Integer.valueOf(codedInputStreamReader.input.readUInt32());
                break;
            case 10:
                codedInputStreamReader.requireWireType(5);
                obj3 = Integer.valueOf(codedInputStreamReader.input.readSFixed32());
                break;
            case 11:
                codedInputStreamReader.requireWireType(1);
                obj3 = Long.valueOf(codedInputStreamReader.input.readSFixed64());
                break;
            case 12:
                codedInputStreamReader.requireWireType(0);
                obj3 = Integer.valueOf(codedInputStreamReader.input.readSInt32());
                break;
            case 13:
                codedInputStreamReader.requireWireType(0);
                obj3 = Long.valueOf(codedInputStreamReader.input.readSInt64());
                break;
            case 14:
                throw new IllegalStateException("Shouldn't reach here.");
            case 15:
                obj3 = codedInputStreamReader.readBytes();
                break;
            case 16:
                codedInputStreamReader.requireWireType(2);
                obj3 = codedInputStreamReader.input.readString();
                break;
            case 17:
                if (!extensionDescriptor.isRepeated) {
                    Object field2 = fieldSet.getField(extensionDescriptor);
                    if (field2 instanceof GeneratedMessageLite) {
                        Protobuf protobuf = Protobuf.INSTANCE;
                        protobuf.getClass();
                        Schema schemaFor = protobuf.schemaFor(field2.getClass());
                        if (!((GeneratedMessageLite) field2).isMutable()) {
                            Object newInstance = schemaFor.newInstance();
                            schemaFor.mergeFrom(newInstance, field2);
                            fieldSet.setField(extensionDescriptor, newInstance);
                            field2 = newInstance;
                        }
                        codedInputStreamReader.requireWireType(3);
                        codedInputStreamReader.mergeGroupFieldInternal(field2, schemaFor, extensionRegistryLite);
                        return obj2;
                    }
                }
                Class<?> cls2 = messageLite.getClass();
                codedInputStreamReader.requireWireType(3);
                Schema schemaFor2 = Protobuf.INSTANCE.schemaFor(cls2);
                obj3 = schemaFor2.newInstance();
                codedInputStreamReader.mergeGroupFieldInternal(obj3, schemaFor2, extensionRegistryLite);
                schemaFor2.makeImmutable(obj3);
                break;
            case 18:
                if (!extensionDescriptor.isRepeated) {
                    Object field3 = fieldSet.getField(extensionDescriptor);
                    if (field3 instanceof GeneratedMessageLite) {
                        Protobuf protobuf2 = Protobuf.INSTANCE;
                        protobuf2.getClass();
                        Schema schemaFor3 = protobuf2.schemaFor(field3.getClass());
                        if (!((GeneratedMessageLite) field3).isMutable()) {
                            Object newInstance2 = schemaFor3.newInstance();
                            schemaFor3.mergeFrom(newInstance2, field3);
                            fieldSet.setField(extensionDescriptor, newInstance2);
                            field3 = newInstance2;
                        }
                        codedInputStreamReader.requireWireType(2);
                        codedInputStreamReader.mergeMessageFieldInternal(field3, schemaFor3, extensionRegistryLite);
                        return obj2;
                    }
                }
                obj3 = codedInputStreamReader.readMessage(messageLite.getClass(), extensionRegistryLite);
                break;
        }
        if (extensionDescriptor.isRepeated) {
            fieldSet.getClass();
            if (!extensionDescriptor.isRepeated) {
                throw new IllegalArgumentException("addRepeatedField() can only be called on repeated fields.");
            }
            FieldSet.verifyType(extensionDescriptor, obj3);
            Object field4 = fieldSet.getField(extensionDescriptor);
            if (field4 == null) {
                list = new ArrayList();
                fieldSet.fields.put((Comparable) extensionDescriptor, (Object) list);
            } else {
                list = (List) field4;
            }
            list.add(obj3);
            return obj2;
        }
        int i3 = iArr[extensionDescriptor.type.ordinal()];
        if ((i3 == 17 || i3 == 18) && (field = fieldSet.getField(extensionDescriptor)) != null) {
            GeneratedMessageLite.Builder builder = ((MessageLite) field).toBuilder();
            MessageLite messageLite2 = (MessageLite) obj3;
            if (!builder.defaultInstance.getClass().isInstance(messageLite2)) {
                throw new IllegalArgumentException("mergeFrom(MessageLite) can only merge messages of the same type.");
            }
            builder.mergeFrom((GeneratedMessageLite) ((AbstractMessageLite) messageLite2));
            obj3 = builder.buildPartial$1();
        }
        fieldSet.setField(extensionDescriptor, obj3);
        return obj2;
    }

    @Override // androidx.datastore.preferences.protobuf.ExtensionSchema
    public final void parseLengthPrefixedMessageSetItem(CodedInputStreamReader codedInputStreamReader, Object obj, ExtensionRegistryLite extensionRegistryLite, FieldSet fieldSet) {
        GeneratedMessageLite.GeneratedExtension generatedExtension = (GeneratedMessageLite.GeneratedExtension) obj;
        fieldSet.setField(generatedExtension.descriptor, codedInputStreamReader.readMessage(generatedExtension.messageDefaultInstance.getClass(), extensionRegistryLite));
    }

    @Override // androidx.datastore.preferences.protobuf.ExtensionSchema
    public final void parseMessageSetItem(ByteString byteString, Object obj, ExtensionRegistryLite extensionRegistryLite, FieldSet fieldSet) {
        GeneratedMessageLite.GeneratedExtension generatedExtension = (GeneratedMessageLite.GeneratedExtension) obj;
        GeneratedMessageLite.Builder newBuilderForType = generatedExtension.messageDefaultInstance.newBuilderForType();
        CodedInputStream.ArrayDecoder newCodedInput = byteString.newCodedInput();
        newBuilderForType.copyOnWrite();
        try {
            Protobuf protobuf = Protobuf.INSTANCE;
            GeneratedMessageLite generatedMessageLite = newBuilderForType.instance;
            protobuf.getClass();
            protobuf.schemaFor(generatedMessageLite.getClass()).mergeFrom(newBuilderForType.instance, CodedInputStreamReader.forCodedInput(newCodedInput), extensionRegistryLite);
            fieldSet.setField(generatedExtension.descriptor, newBuilderForType.buildPartial$1());
            newCodedInput.checkLastTagWas(0);
        } catch (RuntimeException e) {
            if (!(e.getCause() instanceof IOException)) {
                throw e;
            }
            throw ((IOException) e.getCause());
        }
    }

    @Override // androidx.datastore.preferences.protobuf.ExtensionSchema
    public final void serializeExtension(CodedOutputStreamWriter codedOutputStreamWriter, Map.Entry entry) {
        GeneratedMessageLite.ExtensionDescriptor extensionDescriptor = (GeneratedMessageLite.ExtensionDescriptor) entry.getKey();
        if (extensionDescriptor.isRepeated) {
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
        switch (AnonymousClass1.$SwitchMap$com$google$protobuf$WireFormat$FieldType[extensionDescriptor.type.ordinal()]) {
            case 1:
                int i = extensionDescriptor.number;
                double doubleValue = ((Double) entry.getValue()).doubleValue();
                CodedOutputStream codedOutputStream = codedOutputStreamWriter.output;
                codedOutputStream.getClass();
                codedOutputStream.writeFixed64(i, Double.doubleToRawLongBits(doubleValue));
                break;
            case 2:
                int i2 = extensionDescriptor.number;
                float floatValue = ((Float) entry.getValue()).floatValue();
                CodedOutputStream codedOutputStream2 = codedOutputStreamWriter.output;
                codedOutputStream2.getClass();
                codedOutputStream2.writeFixed32(i2, Float.floatToRawIntBits(floatValue));
                break;
            case 3:
                codedOutputStreamWriter.writeInt64(extensionDescriptor.number, ((Long) entry.getValue()).longValue());
                break;
            case 4:
                codedOutputStreamWriter.output.writeUInt64(extensionDescriptor.number, ((Long) entry.getValue()).longValue());
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
                codedOutputStreamWriter.output.writeBool(extensionDescriptor.number, ((Boolean) entry.getValue()).booleanValue());
                break;
            case 9:
                codedOutputStreamWriter.output.writeUInt32(extensionDescriptor.number, ((Integer) entry.getValue()).intValue());
                break;
            case 10:
                codedOutputStreamWriter.output.writeFixed32(extensionDescriptor.number, ((Integer) entry.getValue()).intValue());
                break;
            case 11:
                codedOutputStreamWriter.output.writeFixed64(extensionDescriptor.number, ((Long) entry.getValue()).longValue());
                break;
            case 12:
                int i3 = extensionDescriptor.number;
                int intValue = ((Integer) entry.getValue()).intValue();
                CodedOutputStream codedOutputStream3 = codedOutputStreamWriter.output;
                codedOutputStream3.writeUInt32(i3, (intValue >> 31) ^ (intValue << 1));
                break;
            case 13:
                int i4 = extensionDescriptor.number;
                long longValue = ((Long) entry.getValue()).longValue();
                CodedOutputStream codedOutputStream4 = codedOutputStreamWriter.output;
                codedOutputStream4.writeUInt64(i4, (longValue >> 63) ^ (longValue << 1));
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
                codedOutputStreamWriter.writeGroup(extensionDescriptor.number, entry.getValue(), Protobuf.INSTANCE.schemaFor(entry.getValue().getClass()));
                break;
            case 18:
                codedOutputStreamWriter.writeMessage(extensionDescriptor.number, entry.getValue(), Protobuf.INSTANCE.schemaFor(entry.getValue().getClass()));
                break;
        }
    }
}
