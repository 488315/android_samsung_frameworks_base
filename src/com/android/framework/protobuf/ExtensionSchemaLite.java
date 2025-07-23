package com.android.framework.protobuf;

import com.android.framework.protobuf.GeneratedMessageLite;
import com.android.framework.protobuf.MessageLite;
import com.android.framework.protobuf.WireFormat;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@CheckReturnValue
/* loaded from: classes5.dex */
final class ExtensionSchemaLite extends ExtensionSchema<GeneratedMessageLite.ExtensionDescriptor> {
    ExtensionSchemaLite() {
    }

    @Override // com.android.framework.protobuf.ExtensionSchema
    boolean hasExtensions(MessageLite messageLite) {
        return messageLite instanceof GeneratedMessageLite.ExtendableMessage;
    }

    @Override // com.android.framework.protobuf.ExtensionSchema
    FieldSet<GeneratedMessageLite.ExtensionDescriptor> getExtensions(Object obj) {
        return ((GeneratedMessageLite.ExtendableMessage) obj).extensions;
    }

    @Override // com.android.framework.protobuf.ExtensionSchema
    void setExtensions(Object obj, FieldSet<GeneratedMessageLite.ExtensionDescriptor> fieldSet) {
        ((GeneratedMessageLite.ExtendableMessage) obj).extensions = fieldSet;
    }

    @Override // com.android.framework.protobuf.ExtensionSchema
    FieldSet<GeneratedMessageLite.ExtensionDescriptor> getMutableExtensions(Object obj) {
        return ((GeneratedMessageLite.ExtendableMessage) obj).ensureExtensionsAreMutable();
    }

    @Override // com.android.framework.protobuf.ExtensionSchema
    void makeImmutable(Object obj) {
        getExtensions(obj).makeImmutable();
    }

    @Override // com.android.framework.protobuf.ExtensionSchema
    <UT, UB> UB parseExtension(Object obj, Reader reader, Object obj2, ExtensionRegistryLite extensionRegistryLite, FieldSet<GeneratedMessageLite.ExtensionDescriptor> fieldSet, UB ub, UnknownFieldSchema<UT, UB> unknownFieldSchema) throws IOException {
        Object valueOf;
        Object field;
        ArrayList arrayList;
        GeneratedMessageLite.GeneratedExtension generatedExtension = (GeneratedMessageLite.GeneratedExtension) obj2;
        int number = generatedExtension.getNumber();
        if (generatedExtension.descriptor.isRepeated() && generatedExtension.descriptor.isPacked()) {
            switch (AnonymousClass1.$SwitchMap$com$google$protobuf$WireFormat$FieldType[generatedExtension.getLiteType().ordinal()]) {
                case 1:
                    arrayList = new ArrayList();
                    reader.readDoubleList(arrayList);
                    break;
                case 2:
                    arrayList = new ArrayList();
                    reader.readFloatList(arrayList);
                    break;
                case 3:
                    arrayList = new ArrayList();
                    reader.readInt64List(arrayList);
                    break;
                case 4:
                    arrayList = new ArrayList();
                    reader.readUInt64List(arrayList);
                    break;
                case 5:
                    arrayList = new ArrayList();
                    reader.readInt32List(arrayList);
                    break;
                case 6:
                    arrayList = new ArrayList();
                    reader.readFixed64List(arrayList);
                    break;
                case 7:
                    arrayList = new ArrayList();
                    reader.readFixed32List(arrayList);
                    break;
                case 8:
                    arrayList = new ArrayList();
                    reader.readBoolList(arrayList);
                    break;
                case 9:
                    arrayList = new ArrayList();
                    reader.readUInt32List(arrayList);
                    break;
                case 10:
                    arrayList = new ArrayList();
                    reader.readSFixed32List(arrayList);
                    break;
                case 11:
                    arrayList = new ArrayList();
                    reader.readSFixed64List(arrayList);
                    break;
                case 12:
                    arrayList = new ArrayList();
                    reader.readSInt32List(arrayList);
                    break;
                case 13:
                    arrayList = new ArrayList();
                    reader.readSInt64List(arrayList);
                    break;
                case 14:
                    arrayList = new ArrayList();
                    reader.readEnumList(arrayList);
                    ub = (UB) SchemaUtil.filterUnknownEnumList(obj, number, arrayList, generatedExtension.descriptor.getEnumType(), ub, unknownFieldSchema);
                    break;
                default:
                    throw new IllegalStateException("Type cannot be packed: " + generatedExtension.descriptor.getLiteType());
            }
            fieldSet.setField(generatedExtension.descriptor, arrayList);
            return ub;
        }
        if (generatedExtension.getLiteType() == WireFormat.FieldType.ENUM) {
            int readInt32 = reader.readInt32();
            if (generatedExtension.descriptor.getEnumType().findValueByNumber(readInt32) == null) {
                return (UB) SchemaUtil.storeUnknownEnum(obj, number, readInt32, ub, unknownFieldSchema);
            }
            valueOf = Integer.valueOf(readInt32);
        } else {
            switch (AnonymousClass1.$SwitchMap$com$google$protobuf$WireFormat$FieldType[generatedExtension.getLiteType().ordinal()]) {
                case 1:
                    valueOf = Double.valueOf(reader.readDouble());
                    break;
                case 2:
                    valueOf = Float.valueOf(reader.readFloat());
                    break;
                case 3:
                    valueOf = Long.valueOf(reader.readInt64());
                    break;
                case 4:
                    valueOf = Long.valueOf(reader.readUInt64());
                    break;
                case 5:
                    valueOf = Integer.valueOf(reader.readInt32());
                    break;
                case 6:
                    valueOf = Long.valueOf(reader.readFixed64());
                    break;
                case 7:
                    valueOf = Integer.valueOf(reader.readFixed32());
                    break;
                case 8:
                    valueOf = Boolean.valueOf(reader.readBool());
                    break;
                case 9:
                    valueOf = Integer.valueOf(reader.readUInt32());
                    break;
                case 10:
                    valueOf = Integer.valueOf(reader.readSFixed32());
                    break;
                case 11:
                    valueOf = Long.valueOf(reader.readSFixed64());
                    break;
                case 12:
                    valueOf = Integer.valueOf(reader.readSInt32());
                    break;
                case 13:
                    valueOf = Long.valueOf(reader.readSInt64());
                    break;
                case 14:
                    throw new IllegalStateException("Shouldn't reach here.");
                case 15:
                    valueOf = reader.readBytes();
                    break;
                case 16:
                    valueOf = reader.readString();
                    break;
                case 17:
                    if (!generatedExtension.isRepeated()) {
                        Object field2 = fieldSet.getField(generatedExtension.descriptor);
                        if (field2 instanceof GeneratedMessageLite) {
                            Schema schemaFor = Protobuf.getInstance().schemaFor((Protobuf) field2);
                            if (!((GeneratedMessageLite) field2).isMutable()) {
                                Object newInstance = schemaFor.newInstance();
                                schemaFor.mergeFrom(newInstance, field2);
                                fieldSet.setField(generatedExtension.descriptor, newInstance);
                                field2 = newInstance;
                            }
                            reader.mergeGroupField(field2, schemaFor, extensionRegistryLite);
                            return ub;
                        }
                    }
                    valueOf = reader.readGroup(generatedExtension.getMessageDefaultInstance().getClass(), extensionRegistryLite);
                    break;
                case 18:
                    if (!generatedExtension.isRepeated()) {
                        Object field3 = fieldSet.getField(generatedExtension.descriptor);
                        if (field3 instanceof GeneratedMessageLite) {
                            Schema schemaFor2 = Protobuf.getInstance().schemaFor((Protobuf) field3);
                            if (!((GeneratedMessageLite) field3).isMutable()) {
                                Object newInstance2 = schemaFor2.newInstance();
                                schemaFor2.mergeFrom(newInstance2, field3);
                                fieldSet.setField(generatedExtension.descriptor, newInstance2);
                                field3 = newInstance2;
                            }
                            reader.mergeMessageField(field3, schemaFor2, extensionRegistryLite);
                            return ub;
                        }
                    }
                    valueOf = reader.readMessage(generatedExtension.getMessageDefaultInstance().getClass(), extensionRegistryLite);
                    break;
                default:
                    valueOf = null;
                    break;
            }
        }
        if (generatedExtension.isRepeated()) {
            fieldSet.addRepeatedField(generatedExtension.descriptor, valueOf);
            return ub;
        }
        int i = AnonymousClass1.$SwitchMap$com$google$protobuf$WireFormat$FieldType[generatedExtension.getLiteType().ordinal()];
        if ((i == 17 || i == 18) && (field = fieldSet.getField(generatedExtension.descriptor)) != null) {
            valueOf = Internal.mergeMessage(field, valueOf);
        }
        fieldSet.setField(generatedExtension.descriptor, valueOf);
        return ub;
    }

    /* renamed from: com.android.framework.protobuf.ExtensionSchemaLite$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$google$protobuf$WireFormat$FieldType;

        static {
            int[] iArr = new int[WireFormat.FieldType.values().length];
            $SwitchMap$com$google$protobuf$WireFormat$FieldType = iArr;
            try {
                iArr[WireFormat.FieldType.DOUBLE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat.FieldType.FLOAT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat.FieldType.INT64.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat.FieldType.UINT64.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat.FieldType.INT32.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat.FieldType.FIXED64.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat.FieldType.FIXED32.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat.FieldType.BOOL.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat.FieldType.UINT32.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat.FieldType.SFIXED32.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat.FieldType.SFIXED64.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat.FieldType.SINT32.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat.FieldType.SINT64.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat.FieldType.ENUM.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat.FieldType.BYTES.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat.FieldType.STRING.ordinal()] = 16;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat.FieldType.GROUP.ordinal()] = 17;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat.FieldType.MESSAGE.ordinal()] = 18;
            } catch (NoSuchFieldError unused18) {
            }
        }
    }

    @Override // com.android.framework.protobuf.ExtensionSchema
    int extensionNumber(Map.Entry<?, ?> entry) {
        return ((GeneratedMessageLite.ExtensionDescriptor) entry.getKey()).getNumber();
    }

    @Override // com.android.framework.protobuf.ExtensionSchema
    void serializeExtension(Writer writer, Map.Entry<?, ?> entry) throws IOException {
        GeneratedMessageLite.ExtensionDescriptor extensionDescriptor = (GeneratedMessageLite.ExtensionDescriptor) entry.getKey();
        if (extensionDescriptor.isRepeated()) {
            switch (AnonymousClass1.$SwitchMap$com$google$protobuf$WireFormat$FieldType[extensionDescriptor.getLiteType().ordinal()]) {
                case 1:
                    SchemaUtil.writeDoubleList(extensionDescriptor.getNumber(), (List) entry.getValue(), writer, extensionDescriptor.isPacked());
                    break;
                case 2:
                    SchemaUtil.writeFloatList(extensionDescriptor.getNumber(), (List) entry.getValue(), writer, extensionDescriptor.isPacked());
                    break;
                case 3:
                    SchemaUtil.writeInt64List(extensionDescriptor.getNumber(), (List) entry.getValue(), writer, extensionDescriptor.isPacked());
                    break;
                case 4:
                    SchemaUtil.writeUInt64List(extensionDescriptor.getNumber(), (List) entry.getValue(), writer, extensionDescriptor.isPacked());
                    break;
                case 5:
                    SchemaUtil.writeInt32List(extensionDescriptor.getNumber(), (List) entry.getValue(), writer, extensionDescriptor.isPacked());
                    break;
                case 6:
                    SchemaUtil.writeFixed64List(extensionDescriptor.getNumber(), (List) entry.getValue(), writer, extensionDescriptor.isPacked());
                    break;
                case 7:
                    SchemaUtil.writeFixed32List(extensionDescriptor.getNumber(), (List) entry.getValue(), writer, extensionDescriptor.isPacked());
                    break;
                case 8:
                    SchemaUtil.writeBoolList(extensionDescriptor.getNumber(), (List) entry.getValue(), writer, extensionDescriptor.isPacked());
                    break;
                case 9:
                    SchemaUtil.writeUInt32List(extensionDescriptor.getNumber(), (List) entry.getValue(), writer, extensionDescriptor.isPacked());
                    break;
                case 10:
                    SchemaUtil.writeSFixed32List(extensionDescriptor.getNumber(), (List) entry.getValue(), writer, extensionDescriptor.isPacked());
                    break;
                case 11:
                    SchemaUtil.writeSFixed64List(extensionDescriptor.getNumber(), (List) entry.getValue(), writer, extensionDescriptor.isPacked());
                    break;
                case 12:
                    SchemaUtil.writeSInt32List(extensionDescriptor.getNumber(), (List) entry.getValue(), writer, extensionDescriptor.isPacked());
                    break;
                case 13:
                    SchemaUtil.writeSInt64List(extensionDescriptor.getNumber(), (List) entry.getValue(), writer, extensionDescriptor.isPacked());
                    break;
                case 14:
                    SchemaUtil.writeInt32List(extensionDescriptor.getNumber(), (List) entry.getValue(), writer, extensionDescriptor.isPacked());
                    break;
                case 15:
                    SchemaUtil.writeBytesList(extensionDescriptor.getNumber(), (List) entry.getValue(), writer);
                    break;
                case 16:
                    SchemaUtil.writeStringList(extensionDescriptor.getNumber(), (List) entry.getValue(), writer);
                    break;
                case 17:
                    List list = (List) entry.getValue();
                    if (list != null && !list.isEmpty()) {
                        SchemaUtil.writeGroupList(extensionDescriptor.getNumber(), (List) entry.getValue(), writer, Protobuf.getInstance().schemaFor((Class) list.get(0).getClass()));
                        break;
                    }
                    break;
                case 18:
                    List list2 = (List) entry.getValue();
                    if (list2 != null && !list2.isEmpty()) {
                        SchemaUtil.writeMessageList(extensionDescriptor.getNumber(), (List) entry.getValue(), writer, Protobuf.getInstance().schemaFor((Class) list2.get(0).getClass()));
                        break;
                    }
                    break;
            }
        }
        switch (AnonymousClass1.$SwitchMap$com$google$protobuf$WireFormat$FieldType[extensionDescriptor.getLiteType().ordinal()]) {
            case 1:
                writer.writeDouble(extensionDescriptor.getNumber(), ((Double) entry.getValue()).doubleValue());
                break;
            case 2:
                writer.writeFloat(extensionDescriptor.getNumber(), ((Float) entry.getValue()).floatValue());
                break;
            case 3:
                writer.writeInt64(extensionDescriptor.getNumber(), ((Long) entry.getValue()).longValue());
                break;
            case 4:
                writer.writeUInt64(extensionDescriptor.getNumber(), ((Long) entry.getValue()).longValue());
                break;
            case 5:
                writer.writeInt32(extensionDescriptor.getNumber(), ((Integer) entry.getValue()).intValue());
                break;
            case 6:
                writer.writeFixed64(extensionDescriptor.getNumber(), ((Long) entry.getValue()).longValue());
                break;
            case 7:
                writer.writeFixed32(extensionDescriptor.getNumber(), ((Integer) entry.getValue()).intValue());
                break;
            case 8:
                writer.writeBool(extensionDescriptor.getNumber(), ((Boolean) entry.getValue()).booleanValue());
                break;
            case 9:
                writer.writeUInt32(extensionDescriptor.getNumber(), ((Integer) entry.getValue()).intValue());
                break;
            case 10:
                writer.writeSFixed32(extensionDescriptor.getNumber(), ((Integer) entry.getValue()).intValue());
                break;
            case 11:
                writer.writeSFixed64(extensionDescriptor.getNumber(), ((Long) entry.getValue()).longValue());
                break;
            case 12:
                writer.writeSInt32(extensionDescriptor.getNumber(), ((Integer) entry.getValue()).intValue());
                break;
            case 13:
                writer.writeSInt64(extensionDescriptor.getNumber(), ((Long) entry.getValue()).longValue());
                break;
            case 14:
                writer.writeInt32(extensionDescriptor.getNumber(), ((Integer) entry.getValue()).intValue());
                break;
            case 15:
                writer.writeBytes(extensionDescriptor.getNumber(), (ByteString) entry.getValue());
                break;
            case 16:
                writer.writeString(extensionDescriptor.getNumber(), (String) entry.getValue());
                break;
            case 17:
                writer.writeGroup(extensionDescriptor.getNumber(), entry.getValue(), Protobuf.getInstance().schemaFor((Class) entry.getValue().getClass()));
                break;
            case 18:
                writer.writeMessage(extensionDescriptor.getNumber(), entry.getValue(), Protobuf.getInstance().schemaFor((Class) entry.getValue().getClass()));
                break;
        }
    }

    @Override // com.android.framework.protobuf.ExtensionSchema
    Object findExtensionByNumber(ExtensionRegistryLite extensionRegistryLite, MessageLite messageLite, int i) {
        return extensionRegistryLite.findLiteExtensionByNumber(messageLite, i);
    }

    @Override // com.android.framework.protobuf.ExtensionSchema
    void parseLengthPrefixedMessageSetItem(Reader reader, Object obj, ExtensionRegistryLite extensionRegistryLite, FieldSet<GeneratedMessageLite.ExtensionDescriptor> fieldSet) throws IOException {
        GeneratedMessageLite.GeneratedExtension generatedExtension = (GeneratedMessageLite.GeneratedExtension) obj;
        fieldSet.setField(generatedExtension.descriptor, reader.readMessage(generatedExtension.getMessageDefaultInstance().getClass(), extensionRegistryLite));
    }

    @Override // com.android.framework.protobuf.ExtensionSchema
    void parseMessageSetItem(ByteString byteString, Object obj, ExtensionRegistryLite extensionRegistryLite, FieldSet<GeneratedMessageLite.ExtensionDescriptor> fieldSet) throws IOException {
        GeneratedMessageLite.GeneratedExtension generatedExtension = (GeneratedMessageLite.GeneratedExtension) obj;
        MessageLite.Builder newBuilderForType = generatedExtension.getMessageDefaultInstance().newBuilderForType();
        CodedInputStream newCodedInput = byteString.newCodedInput();
        newBuilderForType.mergeFrom(newCodedInput, extensionRegistryLite);
        fieldSet.setField(generatedExtension.descriptor, newBuilderForType.buildPartial());
        newCodedInput.checkLastTagWas(0);
    }
}
