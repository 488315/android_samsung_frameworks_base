package com.google.protobuf;

import androidx.activity.result.ActivityResultRegistry$register$3$$ExternalSyntheticOutline0;
import com.google.protobuf.ArrayDecoders;
import com.google.protobuf.ByteString;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.Internal;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.MapEntryLite;
import com.google.protobuf.UnsafeUtil;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import java.io.IOException;
import java.lang.reflect.Field;
import java.security.AccessController;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import sun.misc.Unsafe;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class MessageSchema implements Schema {
    public static final int[] EMPTY_INT_ARRAY = new int[0];
    public static final Unsafe UNSAFE;
    public final int[] buffer;
    public final int checkInitializedCount;
    public final MessageLite defaultInstance;
    public final ExtensionSchema extensionSchema;
    public final boolean hasExtensions;
    public final int[] intArray;
    public final ListFieldSchema listFieldSchema;
    public final boolean lite;
    public final MapFieldSchema mapFieldSchema;
    public final int maxFieldNumber;
    public final int minFieldNumber;
    public final NewInstanceSchema newInstanceSchema;
    public final Object[] objects;
    public final boolean proto3;
    public final int repeatedFieldOffsetStart;
    public final UnknownFieldSchema unknownFieldSchema;
    public final boolean useCachedSizeField;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.google.protobuf.MessageSchema$1, reason: invalid class name */
    public abstract /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$com$google$protobuf$WireFormat$FieldType;

        static {
            int[] iArr = new int[WireFormat$FieldType.values().length];
            $SwitchMap$com$google$protobuf$WireFormat$FieldType = iArr;
            try {
                iArr[WireFormat$FieldType.BOOL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat$FieldType.BYTES.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat$FieldType.DOUBLE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat$FieldType.FIXED32.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat$FieldType.SFIXED32.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat$FieldType.FIXED64.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat$FieldType.SFIXED64.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat$FieldType.FLOAT.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat$FieldType.ENUM.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat$FieldType.INT32.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat$FieldType.UINT32.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat$FieldType.INT64.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat$FieldType.UINT64.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat$FieldType.MESSAGE.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat$FieldType.SINT32.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat$FieldType.SINT64.ordinal()] = 16;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat$FieldType.STRING.ordinal()] = 17;
            } catch (NoSuchFieldError unused17) {
            }
        }
    }

    static {
        Unsafe unsafe;
        try {
            unsafe = (Unsafe) AccessController.doPrivileged(new UnsafeUtil.AnonymousClass1());
        } catch (Throwable unused) {
            unsafe = null;
        }
        UNSAFE = unsafe;
    }

    private MessageSchema(int[] iArr, Object[] objArr, int i, int i2, MessageLite messageLite, boolean z, boolean z2, int[] iArr2, int i3, int i4, NewInstanceSchema newInstanceSchema, ListFieldSchema listFieldSchema, UnknownFieldSchema unknownFieldSchema, ExtensionSchema extensionSchema, MapFieldSchema mapFieldSchema) {
        this.buffer = iArr;
        this.objects = objArr;
        this.minFieldNumber = i;
        this.maxFieldNumber = i2;
        this.lite = messageLite instanceof GeneratedMessageLite;
        this.proto3 = z;
        this.hasExtensions = extensionSchema != null && extensionSchema.hasExtensions(messageLite);
        this.useCachedSizeField = z2;
        this.intArray = iArr2;
        this.checkInitializedCount = i3;
        this.repeatedFieldOffsetStart = i4;
        this.newInstanceSchema = newInstanceSchema;
        this.listFieldSchema = listFieldSchema;
        this.unknownFieldSchema = unknownFieldSchema;
        this.extensionSchema = extensionSchema;
        this.defaultInstance = messageLite;
        this.mapFieldSchema = mapFieldSchema;
    }

    public static void checkMutable(Object obj) {
        if (isMutable(obj)) {
            return;
        }
        throw new IllegalArgumentException("Mutating immutable message: " + obj);
    }

    public static int decodeMapEntryValue(byte[] bArr, int i, int i2, WireFormat$FieldType wireFormat$FieldType, Class cls, ArrayDecoders.Registers registers) {
        switch (AnonymousClass1.$SwitchMap$com$google$protobuf$WireFormat$FieldType[wireFormat$FieldType.ordinal()]) {
            case 1:
                int decodeVarint64 = ArrayDecoders.decodeVarint64(bArr, i, registers);
                registers.object1 = Boolean.valueOf(registers.long1 != 0);
                return decodeVarint64;
            case 2:
                return ArrayDecoders.decodeBytes(bArr, i, registers);
            case 3:
                registers.object1 = Double.valueOf(Double.longBitsToDouble(ArrayDecoders.decodeFixed64(i, bArr)));
                return i + 8;
            case 4:
            case 5:
                registers.object1 = Integer.valueOf(ArrayDecoders.decodeFixed32(i, bArr));
                return i + 4;
            case 6:
            case 7:
                registers.object1 = Long.valueOf(ArrayDecoders.decodeFixed64(i, bArr));
                return i + 8;
            case 8:
                registers.object1 = Float.valueOf(Float.intBitsToFloat(ArrayDecoders.decodeFixed32(i, bArr)));
                return i + 4;
            case 9:
            case 10:
            case 11:
                int decodeVarint32 = ArrayDecoders.decodeVarint32(bArr, i, registers);
                registers.object1 = Integer.valueOf(registers.int1);
                return decodeVarint32;
            case 12:
            case 13:
                int decodeVarint642 = ArrayDecoders.decodeVarint64(bArr, i, registers);
                registers.object1 = Long.valueOf(registers.long1);
                return decodeVarint642;
            case 14:
                return ArrayDecoders.decodeMessageField(Protobuf.INSTANCE.schemaFor(cls), bArr, i, i2, registers);
            case 15:
                int decodeVarint322 = ArrayDecoders.decodeVarint32(bArr, i, registers);
                registers.object1 = Integer.valueOf(CodedInputStream.decodeZigZag32(registers.int1));
                return decodeVarint322;
            case 16:
                int decodeVarint643 = ArrayDecoders.decodeVarint64(bArr, i, registers);
                registers.object1 = Long.valueOf(CodedInputStream.decodeZigZag64(registers.long1));
                return decodeVarint643;
            case 17:
                return ArrayDecoders.decodeStringRequireUtf8(bArr, i, registers);
            default:
                throw new RuntimeException("unsupported field type.");
        }
    }

    public static UnknownFieldSetLite getMutableUnknownFields(Object obj) {
        GeneratedMessageLite generatedMessageLite = (GeneratedMessageLite) obj;
        UnknownFieldSetLite unknownFieldSetLite = generatedMessageLite.unknownFields;
        if (unknownFieldSetLite != UnknownFieldSetLite.DEFAULT_INSTANCE) {
            return unknownFieldSetLite;
        }
        UnknownFieldSetLite newInstance = UnknownFieldSetLite.newInstance();
        generatedMessageLite.unknownFields = newInstance;
        return newInstance;
    }

    public static boolean isMutable(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof GeneratedMessageLite) {
            return ((GeneratedMessageLite) obj).isMutable();
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:255:0x054a  */
    /* JADX WARN: Removed duplicated region for block: B:258:0x0553  */
    /* JADX WARN: Removed duplicated region for block: B:270:0x0595  */
    /* JADX WARN: Removed duplicated region for block: B:281:0x05e2  */
    /* JADX WARN: Removed duplicated region for block: B:284:0x05e8  */
    /* JADX WARN: Removed duplicated region for block: B:295:0x05b1  */
    /* JADX WARN: Removed duplicated region for block: B:303:0x058a  */
    /* JADX WARN: Removed duplicated region for block: B:304:0x058f  */
    /* JADX WARN: Removed duplicated region for block: B:305:0x0556  */
    /* JADX WARN: Removed duplicated region for block: B:306:0x054d  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x02ad  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x02ca  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x02cd  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x02b4  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.google.protobuf.MessageSchema newSchema(com.google.protobuf.MessageInfo r41, com.google.protobuf.NewInstanceSchema r42, com.google.protobuf.ListFieldSchema r43, com.google.protobuf.UnknownFieldSchema r44, com.google.protobuf.ExtensionSchema r45, com.google.protobuf.MapFieldSchema r46) {
        /*
            Method dump skipped, instructions count: 1628
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.MessageSchema.newSchema(com.google.protobuf.MessageInfo, com.google.protobuf.NewInstanceSchema, com.google.protobuf.ListFieldSchema, com.google.protobuf.UnknownFieldSchema, com.google.protobuf.ExtensionSchema, com.google.protobuf.MapFieldSchema):com.google.protobuf.MessageSchema");
    }

    public static long offset(int i) {
        return i & 1048575;
    }

    public static int oneofIntAt(long j, Object obj) {
        return ((Integer) UnsafeUtil.getObject(j, obj)).intValue();
    }

    public static long oneofLongAt(long j, Object obj) {
        return ((Long) UnsafeUtil.getObject(j, obj)).longValue();
    }

    public static Field reflectField(Class cls, String str) {
        try {
            return cls.getDeclaredField(str);
        } catch (NoSuchFieldException unused) {
            Field[] declaredFields = cls.getDeclaredFields();
            for (Field field : declaredFields) {
                if (str.equals(field.getName())) {
                    return field;
                }
            }
            StringBuilder m = ActivityResultRegistry$register$3$$ExternalSyntheticOutline0.m("Field ", str, " for ");
            m.append(cls.getName());
            m.append(" not found. Known fields are ");
            m.append(Arrays.toString(declaredFields));
            throw new RuntimeException(m.toString());
        }
    }

    public static int type(int i) {
        return (i & 267386880) >>> 20;
    }

    public static void writeString(int i, Object obj, CodedOutputStreamWriter codedOutputStreamWriter) {
        if (!(obj instanceof String)) {
            codedOutputStreamWriter.writeBytes(i, (ByteString) obj);
        } else {
            codedOutputStreamWriter.output.writeString(i, (String) obj);
        }
    }

    public final boolean arePresentForEquals(GeneratedMessageLite generatedMessageLite, GeneratedMessageLite generatedMessageLite2, int i) {
        return isFieldPresent(i, generatedMessageLite) == isFieldPresent(i, generatedMessageLite2);
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x006c, code lost:
    
        if (com.google.protobuf.SchemaUtil.safeEquals(com.google.protobuf.UnsafeUtil.getObject(r7, r12), com.google.protobuf.UnsafeUtil.getObject(r7, r13)) != false) goto L105;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0080, code lost:
    
        if (com.google.protobuf.UnsafeUtil.getLong(r7, r12) == com.google.protobuf.UnsafeUtil.getLong(r7, r13)) goto L105;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0092, code lost:
    
        if (com.google.protobuf.UnsafeUtil.getInt(r7, r12) == com.google.protobuf.UnsafeUtil.getInt(r7, r13)) goto L105;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x00a6, code lost:
    
        if (com.google.protobuf.UnsafeUtil.getLong(r7, r12) == com.google.protobuf.UnsafeUtil.getLong(r7, r13)) goto L105;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x00b8, code lost:
    
        if (com.google.protobuf.UnsafeUtil.getInt(r7, r12) == com.google.protobuf.UnsafeUtil.getInt(r7, r13)) goto L105;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x00ca, code lost:
    
        if (com.google.protobuf.UnsafeUtil.getInt(r7, r12) == com.google.protobuf.UnsafeUtil.getInt(r7, r13)) goto L105;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x00dc, code lost:
    
        if (com.google.protobuf.UnsafeUtil.getInt(r7, r12) == com.google.protobuf.UnsafeUtil.getInt(r7, r13)) goto L105;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x00f2, code lost:
    
        if (com.google.protobuf.SchemaUtil.safeEquals(com.google.protobuf.UnsafeUtil.getObject(r7, r12), com.google.protobuf.UnsafeUtil.getObject(r7, r13)) != false) goto L105;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x0108, code lost:
    
        if (com.google.protobuf.SchemaUtil.safeEquals(com.google.protobuf.UnsafeUtil.getObject(r7, r12), com.google.protobuf.UnsafeUtil.getObject(r7, r13)) != false) goto L105;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x011e, code lost:
    
        if (com.google.protobuf.SchemaUtil.safeEquals(com.google.protobuf.UnsafeUtil.getObject(r7, r12), com.google.protobuf.UnsafeUtil.getObject(r7, r13)) != false) goto L105;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x0132, code lost:
    
        if (r5.getBoolean(r7, r12) == r5.getBoolean(r7, r13)) goto L105;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x0144, code lost:
    
        if (com.google.protobuf.UnsafeUtil.getInt(r7, r12) == com.google.protobuf.UnsafeUtil.getInt(r7, r13)) goto L105;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x0158, code lost:
    
        if (com.google.protobuf.UnsafeUtil.getLong(r7, r12) == com.google.protobuf.UnsafeUtil.getLong(r7, r13)) goto L105;
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x016a, code lost:
    
        if (com.google.protobuf.UnsafeUtil.getInt(r7, r12) == com.google.protobuf.UnsafeUtil.getInt(r7, r13)) goto L105;
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x017d, code lost:
    
        if (com.google.protobuf.UnsafeUtil.getLong(r7, r12) == com.google.protobuf.UnsafeUtil.getLong(r7, r13)) goto L105;
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x0190, code lost:
    
        if (com.google.protobuf.UnsafeUtil.getLong(r7, r12) == com.google.protobuf.UnsafeUtil.getLong(r7, r13)) goto L105;
     */
    /* JADX WARN: Code restructure failed: missing block: B:80:0x01ab, code lost:
    
        if (java.lang.Float.floatToIntBits(r5.getFloat(r7, r12)) == java.lang.Float.floatToIntBits(r5.getFloat(r7, r13))) goto L105;
     */
    /* JADX WARN: Code restructure failed: missing block: B:84:0x01c8, code lost:
    
        if (java.lang.Double.doubleToLongBits(r5.getDouble(r7, r12)) == java.lang.Double.doubleToLongBits(r5.getDouble(r7, r13))) goto L105;
     */
    /* JADX WARN: Code restructure failed: missing block: B:9:0x0037, code lost:
    
        if (com.google.protobuf.SchemaUtil.safeEquals(com.google.protobuf.UnsafeUtil.getObject(r7, r12), com.google.protobuf.UnsafeUtil.getObject(r7, r13)) != false) goto L105;
     */
    @Override // com.google.protobuf.Schema
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean equals(com.google.protobuf.GeneratedMessageLite r12, com.google.protobuf.GeneratedMessageLite r13) {
        /*
            Method dump skipped, instructions count: 644
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.MessageSchema.equals(com.google.protobuf.GeneratedMessageLite, com.google.protobuf.GeneratedMessageLite):boolean");
    }

    public final Object filterMapUnknownEnumValues(Object obj, int i, Object obj2, UnknownFieldSchema unknownFieldSchema, Object obj3) {
        Internal.EnumVerifier enumFieldVerifier;
        int i2 = this.buffer[i];
        Object object = UnsafeUtil.getObject(typeAndOffsetAt(i) & 1048575, obj);
        if (object == null || (enumFieldVerifier = getEnumFieldVerifier(i)) == null) {
            return obj2;
        }
        MapFieldSchemaLite mapFieldSchemaLite = (MapFieldSchemaLite) this.mapFieldSchema;
        mapFieldSchemaLite.getClass();
        Object mapFieldDefaultEntry = getMapFieldDefaultEntry(i);
        mapFieldSchemaLite.getClass();
        MapEntryLite.Metadata metadata = ((MapEntryLite) mapFieldDefaultEntry).metadata;
        Iterator it = ((MapFieldLite) object).entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            if (!enumFieldVerifier.isInRange(((Integer) entry.getValue()).intValue())) {
                if (obj2 == null) {
                    obj2 = unknownFieldSchema.getBuilderFromMessage(obj3);
                }
                ByteString.CodedBuilder codedBuilder = new ByteString.CodedBuilder(MapEntryLite.computeSerializedSize(metadata, entry.getKey(), entry.getValue()), null);
                CodedOutputStream.ArrayEncoder arrayEncoder = codedBuilder.output;
                try {
                    MapEntryLite.writeTo(arrayEncoder, metadata, entry.getKey(), entry.getValue());
                    if (arrayEncoder.spaceLeft() != 0) {
                        throw new IllegalStateException("Did not write as much data as expected.");
                    }
                    unknownFieldSchema.addLengthDelimited(obj2, i2, new ByteString.LiteralByteString(codedBuilder.buffer));
                    it.remove();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return obj2;
    }

    public final Internal.EnumVerifier getEnumFieldVerifier(int i) {
        return (Internal.EnumVerifier) this.objects[((i / 3) * 2) + 1];
    }

    public final Object getMapFieldDefaultEntry(int i) {
        return this.objects[(i / 3) * 2];
    }

    public final Schema getMessageFieldSchema(int i) {
        int i2 = (i / 3) * 2;
        Object[] objArr = this.objects;
        Schema schema = (Schema) objArr[i2];
        if (schema != null) {
            return schema;
        }
        Schema schemaFor = Protobuf.INSTANCE.schemaFor((Class) objArr[i2 + 1]);
        objArr[i2] = schemaFor;
        return schemaFor;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.google.protobuf.Schema
    public final int getSerializedSize(AbstractMessageLite abstractMessageLite) {
        MapFieldSchema mapFieldSchema;
        boolean z;
        int i;
        int i2;
        char c;
        int computeTagSize;
        int computeUInt64SizeNoTag;
        int computeTagSize2;
        int computeInt32SizeNoTag;
        int computeFixed64Size;
        int computeBytesSize;
        char c2;
        int computeTagSize3;
        int i3;
        int computeTagSize4;
        int computeUInt64SizeNoTag2;
        int computeTagSize5;
        int computeInt32SizeNoTag2;
        int computeFixed64Size2;
        int computeBytesSize2;
        int i4;
        int computeTagSize6;
        int computeUInt64SizeNoTag3;
        int i5 = 4;
        boolean z2 = this.proto3;
        UnknownFieldSchema unknownFieldSchema = this.unknownFieldSchema;
        MapFieldSchema mapFieldSchema2 = this.mapFieldSchema;
        boolean z3 = this.useCachedSizeField;
        int[] iArr = this.buffer;
        if (z2) {
            Unsafe unsafe = UNSAFE;
            int i6 = 0;
            int i7 = 0;
            while (i6 < iArr.length) {
                int typeAndOffsetAt = typeAndOffsetAt(i6);
                int type = type(typeAndOffsetAt);
                int i8 = iArr[i6];
                long j = typeAndOffsetAt & 1048575;
                int i9 = (type < FieldType.DOUBLE_LIST_PACKED.id() || type > FieldType.SINT64_LIST_PACKED.id()) ? 0 : iArr[i6 + 2] & 1048575;
                switch (type) {
                    case 0:
                        if (isFieldPresent(i6, abstractMessageLite)) {
                            i7 = MessageSchema$$ExternalSyntheticOutline0.m(i8, 8, i7);
                            break;
                        } else {
                            break;
                        }
                    case 1:
                        if (isFieldPresent(i6, abstractMessageLite)) {
                            i7 = MessageSchema$$ExternalSyntheticOutline0.m(i8, 4, i7);
                            break;
                        } else {
                            break;
                        }
                    case 2:
                        if (isFieldPresent(i6, abstractMessageLite)) {
                            long j2 = UnsafeUtil.getLong(j, abstractMessageLite);
                            computeTagSize4 = CodedOutputStream.computeTagSize(i8);
                            computeUInt64SizeNoTag2 = CodedOutputStream.computeUInt64SizeNoTag(j2);
                            computeFixed64Size2 = computeUInt64SizeNoTag2 + computeTagSize4;
                            i7 += computeFixed64Size2;
                            break;
                        } else {
                            break;
                        }
                    case 3:
                        if (isFieldPresent(i6, abstractMessageLite)) {
                            long j3 = UnsafeUtil.getLong(j, abstractMessageLite);
                            computeTagSize4 = CodedOutputStream.computeTagSize(i8);
                            computeUInt64SizeNoTag2 = CodedOutputStream.computeUInt64SizeNoTag(j3);
                            computeFixed64Size2 = computeUInt64SizeNoTag2 + computeTagSize4;
                            i7 += computeFixed64Size2;
                            break;
                        } else {
                            break;
                        }
                    case 4:
                        if (isFieldPresent(i6, abstractMessageLite)) {
                            int i10 = UnsafeUtil.getInt(j, abstractMessageLite);
                            computeTagSize5 = CodedOutputStream.computeTagSize(i8);
                            computeInt32SizeNoTag2 = CodedOutputStream.computeInt32SizeNoTag(i10);
                            computeFixed64Size2 = computeInt32SizeNoTag2 + computeTagSize5;
                            i7 += computeFixed64Size2;
                            break;
                        } else {
                            break;
                        }
                    case 5:
                        if (isFieldPresent(i6, abstractMessageLite)) {
                            computeFixed64Size2 = CodedOutputStream.computeFixed64Size(i8);
                            i7 += computeFixed64Size2;
                            break;
                        } else {
                            break;
                        }
                    case 6:
                        if (isFieldPresent(i6, abstractMessageLite)) {
                            computeFixed64Size2 = CodedOutputStream.computeFixed32Size(i8);
                            i7 += computeFixed64Size2;
                            break;
                        } else {
                            break;
                        }
                    case 7:
                        if (isFieldPresent(i6, abstractMessageLite)) {
                            i7 = MessageSchema$$ExternalSyntheticOutline0.m(i8, 1, i7);
                            break;
                        } else {
                            break;
                        }
                    case 8:
                        if (isFieldPresent(i6, abstractMessageLite)) {
                            Object object = UnsafeUtil.getObject(j, abstractMessageLite);
                            computeBytesSize2 = object instanceof ByteString ? CodedOutputStream.computeBytesSize(i8, (ByteString) object) : CodedOutputStream.computeStringSizeNoTag((String) object) + CodedOutputStream.computeTagSize(i8);
                            i7 = computeBytesSize2 + i7;
                            break;
                        } else {
                            break;
                        }
                    case 9:
                        if (isFieldPresent(i6, abstractMessageLite)) {
                            computeFixed64Size2 = SchemaUtil.computeSizeMessage(i8, UnsafeUtil.getObject(j, abstractMessageLite), getMessageFieldSchema(i6));
                            i7 += computeFixed64Size2;
                            break;
                        } else {
                            break;
                        }
                    case 10:
                        if (isFieldPresent(i6, abstractMessageLite)) {
                            computeFixed64Size2 = CodedOutputStream.computeBytesSize(i8, (ByteString) UnsafeUtil.getObject(j, abstractMessageLite));
                            i7 += computeFixed64Size2;
                            break;
                        } else {
                            break;
                        }
                    case 11:
                        if (isFieldPresent(i6, abstractMessageLite)) {
                            computeFixed64Size2 = CodedOutputStream.computeUInt32Size(i8, UnsafeUtil.getInt(j, abstractMessageLite));
                            i7 += computeFixed64Size2;
                            break;
                        } else {
                            break;
                        }
                    case 12:
                        if (isFieldPresent(i6, abstractMessageLite)) {
                            int i11 = UnsafeUtil.getInt(j, abstractMessageLite);
                            computeTagSize5 = CodedOutputStream.computeTagSize(i8);
                            computeInt32SizeNoTag2 = CodedOutputStream.computeInt32SizeNoTag(i11);
                            computeFixed64Size2 = computeInt32SizeNoTag2 + computeTagSize5;
                            i7 += computeFixed64Size2;
                            break;
                        } else {
                            break;
                        }
                    case 13:
                        if (isFieldPresent(i6, abstractMessageLite)) {
                            i7 = MessageSchema$$ExternalSyntheticOutline0.m(i8, 4, i7);
                            break;
                        } else {
                            break;
                        }
                    case 14:
                        if (isFieldPresent(i6, abstractMessageLite)) {
                            i7 = MessageSchema$$ExternalSyntheticOutline0.m(i8, 8, i7);
                            break;
                        } else {
                            break;
                        }
                    case 15:
                        if (isFieldPresent(i6, abstractMessageLite)) {
                            int i12 = UnsafeUtil.getInt(j, abstractMessageLite);
                            computeTagSize5 = CodedOutputStream.computeTagSize(i8);
                            computeInt32SizeNoTag2 = CodedOutputStream.computeUInt32SizeNoTag((i12 >> 31) ^ (i12 << 1));
                            computeFixed64Size2 = computeInt32SizeNoTag2 + computeTagSize5;
                            i7 += computeFixed64Size2;
                            break;
                        } else {
                            break;
                        }
                    case 16:
                        if (isFieldPresent(i6, abstractMessageLite)) {
                            long j4 = UnsafeUtil.getLong(j, abstractMessageLite);
                            computeTagSize4 = CodedOutputStream.computeTagSize(i8);
                            computeUInt64SizeNoTag2 = CodedOutputStream.computeUInt64SizeNoTag((j4 >> 63) ^ (j4 << 1));
                            computeFixed64Size2 = computeUInt64SizeNoTag2 + computeTagSize4;
                            i7 += computeFixed64Size2;
                            break;
                        } else {
                            break;
                        }
                    case 17:
                        if (isFieldPresent(i6, abstractMessageLite)) {
                            computeFixed64Size2 = CodedOutputStream.computeGroupSize(i8, (MessageLite) UnsafeUtil.getObject(j, abstractMessageLite), getMessageFieldSchema(i6));
                            i7 += computeFixed64Size2;
                            break;
                        } else {
                            break;
                        }
                    case 18:
                        computeFixed64Size2 = SchemaUtil.computeSizeFixed64List(i8, (List) UnsafeUtil.getObject(j, abstractMessageLite));
                        i7 += computeFixed64Size2;
                        break;
                    case 19:
                        computeFixed64Size2 = SchemaUtil.computeSizeFixed32List(i8, (List) UnsafeUtil.getObject(j, abstractMessageLite));
                        i7 += computeFixed64Size2;
                        break;
                    case 20:
                        computeFixed64Size2 = SchemaUtil.computeSizeInt64List(i8, (List) UnsafeUtil.getObject(j, abstractMessageLite));
                        i7 += computeFixed64Size2;
                        break;
                    case 21:
                        computeFixed64Size2 = SchemaUtil.computeSizeUInt64List(i8, (List) UnsafeUtil.getObject(j, abstractMessageLite));
                        i7 += computeFixed64Size2;
                        break;
                    case 22:
                        computeFixed64Size2 = SchemaUtil.computeSizeInt32List(i8, (List) UnsafeUtil.getObject(j, abstractMessageLite));
                        i7 += computeFixed64Size2;
                        break;
                    case 23:
                        computeFixed64Size2 = SchemaUtil.computeSizeFixed64List(i8, (List) UnsafeUtil.getObject(j, abstractMessageLite));
                        i7 += computeFixed64Size2;
                        break;
                    case 24:
                        computeFixed64Size2 = SchemaUtil.computeSizeFixed32List(i8, (List) UnsafeUtil.getObject(j, abstractMessageLite));
                        i7 += computeFixed64Size2;
                        break;
                    case 25:
                        List list = (List) UnsafeUtil.getObject(j, abstractMessageLite);
                        Class cls = SchemaUtil.GENERATED_MESSAGE_CLASS;
                        int size = list.size();
                        i7 += size == 0 ? 0 : (CodedOutputStream.computeTagSize(i8) + 1) * size;
                        break;
                    case 26:
                        computeFixed64Size2 = SchemaUtil.computeSizeStringList(i8, (List) UnsafeUtil.getObject(j, abstractMessageLite));
                        i7 += computeFixed64Size2;
                        break;
                    case 27:
                        computeFixed64Size2 = SchemaUtil.computeSizeMessageList(i8, (List) UnsafeUtil.getObject(j, abstractMessageLite), getMessageFieldSchema(i6));
                        i7 += computeFixed64Size2;
                        break;
                    case 28:
                        computeFixed64Size2 = SchemaUtil.computeSizeByteStringList(i8, (List) UnsafeUtil.getObject(j, abstractMessageLite));
                        i7 += computeFixed64Size2;
                        break;
                    case 29:
                        computeFixed64Size2 = SchemaUtil.computeSizeUInt32List(i8, (List) UnsafeUtil.getObject(j, abstractMessageLite));
                        i7 += computeFixed64Size2;
                        break;
                    case 30:
                        computeFixed64Size2 = SchemaUtil.computeSizeEnumList(i8, (List) UnsafeUtil.getObject(j, abstractMessageLite));
                        i7 += computeFixed64Size2;
                        break;
                    case 31:
                        computeFixed64Size2 = SchemaUtil.computeSizeFixed32List(i8, (List) UnsafeUtil.getObject(j, abstractMessageLite));
                        i7 += computeFixed64Size2;
                        break;
                    case 32:
                        computeFixed64Size2 = SchemaUtil.computeSizeFixed64List(i8, (List) UnsafeUtil.getObject(j, abstractMessageLite));
                        i7 += computeFixed64Size2;
                        break;
                    case 33:
                        computeFixed64Size2 = SchemaUtil.computeSizeSInt32List(i8, (List) UnsafeUtil.getObject(j, abstractMessageLite));
                        i7 += computeFixed64Size2;
                        break;
                    case 34:
                        computeFixed64Size2 = SchemaUtil.computeSizeSInt64List(i8, (List) UnsafeUtil.getObject(j, abstractMessageLite));
                        i7 += computeFixed64Size2;
                        break;
                    case 35:
                        int computeSizeFixed64ListNoTag = SchemaUtil.computeSizeFixed64ListNoTag((List) unsafe.getObject(abstractMessageLite, j));
                        if (computeSizeFixed64ListNoTag <= 0) {
                            break;
                        } else {
                            if (z3) {
                                unsafe.putInt(abstractMessageLite, i9, computeSizeFixed64ListNoTag);
                            }
                            i7 = FieldSet$$ExternalSyntheticOutline0.m(computeSizeFixed64ListNoTag, CodedOutputStream.computeTagSize(i8), computeSizeFixed64ListNoTag, i7);
                            break;
                        }
                    case 36:
                        int computeSizeFixed32ListNoTag = SchemaUtil.computeSizeFixed32ListNoTag((List) unsafe.getObject(abstractMessageLite, j));
                        if (computeSizeFixed32ListNoTag <= 0) {
                            break;
                        } else {
                            if (z3) {
                                unsafe.putInt(abstractMessageLite, i9, computeSizeFixed32ListNoTag);
                            }
                            i7 = FieldSet$$ExternalSyntheticOutline0.m(computeSizeFixed32ListNoTag, CodedOutputStream.computeTagSize(i8), computeSizeFixed32ListNoTag, i7);
                            break;
                        }
                    case 37:
                        int computeSizeInt64ListNoTag = SchemaUtil.computeSizeInt64ListNoTag((List) unsafe.getObject(abstractMessageLite, j));
                        if (computeSizeInt64ListNoTag <= 0) {
                            break;
                        } else {
                            if (z3) {
                                unsafe.putInt(abstractMessageLite, i9, computeSizeInt64ListNoTag);
                            }
                            i7 = FieldSet$$ExternalSyntheticOutline0.m(computeSizeInt64ListNoTag, CodedOutputStream.computeTagSize(i8), computeSizeInt64ListNoTag, i7);
                            break;
                        }
                    case 38:
                        int computeSizeUInt64ListNoTag = SchemaUtil.computeSizeUInt64ListNoTag((List) unsafe.getObject(abstractMessageLite, j));
                        if (computeSizeUInt64ListNoTag <= 0) {
                            break;
                        } else {
                            if (z3) {
                                unsafe.putInt(abstractMessageLite, i9, computeSizeUInt64ListNoTag);
                            }
                            i7 = FieldSet$$ExternalSyntheticOutline0.m(computeSizeUInt64ListNoTag, CodedOutputStream.computeTagSize(i8), computeSizeUInt64ListNoTag, i7);
                            break;
                        }
                    case 39:
                        int computeSizeInt32ListNoTag = SchemaUtil.computeSizeInt32ListNoTag((List) unsafe.getObject(abstractMessageLite, j));
                        if (computeSizeInt32ListNoTag <= 0) {
                            break;
                        } else {
                            if (z3) {
                                unsafe.putInt(abstractMessageLite, i9, computeSizeInt32ListNoTag);
                            }
                            i7 = FieldSet$$ExternalSyntheticOutline0.m(computeSizeInt32ListNoTag, CodedOutputStream.computeTagSize(i8), computeSizeInt32ListNoTag, i7);
                            break;
                        }
                    case 40:
                        int computeSizeFixed64ListNoTag2 = SchemaUtil.computeSizeFixed64ListNoTag((List) unsafe.getObject(abstractMessageLite, j));
                        if (computeSizeFixed64ListNoTag2 <= 0) {
                            break;
                        } else {
                            if (z3) {
                                unsafe.putInt(abstractMessageLite, i9, computeSizeFixed64ListNoTag2);
                            }
                            i7 = FieldSet$$ExternalSyntheticOutline0.m(computeSizeFixed64ListNoTag2, CodedOutputStream.computeTagSize(i8), computeSizeFixed64ListNoTag2, i7);
                            break;
                        }
                    case 41:
                        int computeSizeFixed32ListNoTag2 = SchemaUtil.computeSizeFixed32ListNoTag((List) unsafe.getObject(abstractMessageLite, j));
                        if (computeSizeFixed32ListNoTag2 <= 0) {
                            break;
                        } else {
                            if (z3) {
                                unsafe.putInt(abstractMessageLite, i9, computeSizeFixed32ListNoTag2);
                            }
                            i7 = FieldSet$$ExternalSyntheticOutline0.m(computeSizeFixed32ListNoTag2, CodedOutputStream.computeTagSize(i8), computeSizeFixed32ListNoTag2, i7);
                            break;
                        }
                    case 42:
                        List list2 = (List) unsafe.getObject(abstractMessageLite, j);
                        Class cls2 = SchemaUtil.GENERATED_MESSAGE_CLASS;
                        int size2 = list2.size();
                        if (size2 <= 0) {
                            break;
                        } else {
                            if (z3) {
                                unsafe.putInt(abstractMessageLite, i9, size2);
                            }
                            i7 = FieldSet$$ExternalSyntheticOutline0.m(size2, CodedOutputStream.computeTagSize(i8), size2, i7);
                            break;
                        }
                    case 43:
                        int computeSizeUInt32ListNoTag = SchemaUtil.computeSizeUInt32ListNoTag((List) unsafe.getObject(abstractMessageLite, j));
                        if (computeSizeUInt32ListNoTag <= 0) {
                            break;
                        } else {
                            if (z3) {
                                unsafe.putInt(abstractMessageLite, i9, computeSizeUInt32ListNoTag);
                            }
                            i7 = FieldSet$$ExternalSyntheticOutline0.m(computeSizeUInt32ListNoTag, CodedOutputStream.computeTagSize(i8), computeSizeUInt32ListNoTag, i7);
                            break;
                        }
                    case 44:
                        int computeSizeEnumListNoTag = SchemaUtil.computeSizeEnumListNoTag((List) unsafe.getObject(abstractMessageLite, j));
                        if (computeSizeEnumListNoTag <= 0) {
                            break;
                        } else {
                            if (z3) {
                                unsafe.putInt(abstractMessageLite, i9, computeSizeEnumListNoTag);
                            }
                            i7 = FieldSet$$ExternalSyntheticOutline0.m(computeSizeEnumListNoTag, CodedOutputStream.computeTagSize(i8), computeSizeEnumListNoTag, i7);
                            break;
                        }
                    case 45:
                        int computeSizeFixed32ListNoTag3 = SchemaUtil.computeSizeFixed32ListNoTag((List) unsafe.getObject(abstractMessageLite, j));
                        if (computeSizeFixed32ListNoTag3 <= 0) {
                            break;
                        } else {
                            if (z3) {
                                unsafe.putInt(abstractMessageLite, i9, computeSizeFixed32ListNoTag3);
                            }
                            i7 = FieldSet$$ExternalSyntheticOutline0.m(computeSizeFixed32ListNoTag3, CodedOutputStream.computeTagSize(i8), computeSizeFixed32ListNoTag3, i7);
                            break;
                        }
                    case 46:
                        int computeSizeFixed64ListNoTag3 = SchemaUtil.computeSizeFixed64ListNoTag((List) unsafe.getObject(abstractMessageLite, j));
                        if (computeSizeFixed64ListNoTag3 <= 0) {
                            break;
                        } else {
                            if (z3) {
                                unsafe.putInt(abstractMessageLite, i9, computeSizeFixed64ListNoTag3);
                            }
                            i7 = FieldSet$$ExternalSyntheticOutline0.m(computeSizeFixed64ListNoTag3, CodedOutputStream.computeTagSize(i8), computeSizeFixed64ListNoTag3, i7);
                            break;
                        }
                    case 47:
                        int computeSizeSInt32ListNoTag = SchemaUtil.computeSizeSInt32ListNoTag((List) unsafe.getObject(abstractMessageLite, j));
                        if (computeSizeSInt32ListNoTag <= 0) {
                            break;
                        } else {
                            if (z3) {
                                unsafe.putInt(abstractMessageLite, i9, computeSizeSInt32ListNoTag);
                            }
                            i7 = FieldSet$$ExternalSyntheticOutline0.m(computeSizeSInt32ListNoTag, CodedOutputStream.computeTagSize(i8), computeSizeSInt32ListNoTag, i7);
                            break;
                        }
                    case 48:
                        int computeSizeSInt64ListNoTag = SchemaUtil.computeSizeSInt64ListNoTag((List) unsafe.getObject(abstractMessageLite, j));
                        if (computeSizeSInt64ListNoTag <= 0) {
                            break;
                        } else {
                            if (z3) {
                                unsafe.putInt(abstractMessageLite, i9, computeSizeSInt64ListNoTag);
                            }
                            i7 = FieldSet$$ExternalSyntheticOutline0.m(computeSizeSInt64ListNoTag, CodedOutputStream.computeTagSize(i8), computeSizeSInt64ListNoTag, i7);
                            break;
                        }
                    case 49:
                        List list3 = (List) UnsafeUtil.getObject(j, abstractMessageLite);
                        Schema messageFieldSchema = getMessageFieldSchema(i6);
                        Class cls3 = SchemaUtil.GENERATED_MESSAGE_CLASS;
                        int size3 = list3.size();
                        if (size3 == 0) {
                            i4 = 0;
                        } else {
                            i4 = 0;
                            for (int i13 = 0; i13 < size3; i13++) {
                                i4 = CodedOutputStream.computeGroupSize(i8, (MessageLite) list3.get(i13), messageFieldSchema) + i4;
                            }
                        }
                        i7 = i4 + i7;
                        break;
                    case 50:
                        computeFixed64Size2 = ((MapFieldSchemaLite) mapFieldSchema2).getSerializedSize(i8, UnsafeUtil.getObject(j, abstractMessageLite), getMapFieldDefaultEntry(i6));
                        i7 += computeFixed64Size2;
                        break;
                    case 51:
                        if (isOneofPresent(i8, i6, abstractMessageLite)) {
                            i7 = MessageSchema$$ExternalSyntheticOutline0.m(i8, 8, i7);
                            break;
                        } else {
                            break;
                        }
                    case 52:
                        if (isOneofPresent(i8, i6, abstractMessageLite)) {
                            i7 = MessageSchema$$ExternalSyntheticOutline0.m(i8, i5, i7);
                            break;
                        } else {
                            break;
                        }
                    case 53:
                        if (isOneofPresent(i8, i6, abstractMessageLite)) {
                            long oneofLongAt = oneofLongAt(j, abstractMessageLite);
                            computeTagSize6 = CodedOutputStream.computeTagSize(i8);
                            computeUInt64SizeNoTag3 = CodedOutputStream.computeUInt64SizeNoTag(oneofLongAt);
                            i7 += computeUInt64SizeNoTag3 + computeTagSize6;
                            break;
                        } else {
                            break;
                        }
                    case 54:
                        if (isOneofPresent(i8, i6, abstractMessageLite)) {
                            long oneofLongAt2 = oneofLongAt(j, abstractMessageLite);
                            computeTagSize6 = CodedOutputStream.computeTagSize(i8);
                            computeUInt64SizeNoTag3 = CodedOutputStream.computeUInt64SizeNoTag(oneofLongAt2);
                            i7 += computeUInt64SizeNoTag3 + computeTagSize6;
                            break;
                        } else {
                            break;
                        }
                    case 55:
                        if (isOneofPresent(i8, i6, abstractMessageLite)) {
                            int oneofIntAt = oneofIntAt(j, abstractMessageLite);
                            computeTagSize4 = CodedOutputStream.computeTagSize(i8);
                            computeUInt64SizeNoTag2 = CodedOutputStream.computeInt32SizeNoTag(oneofIntAt);
                            computeFixed64Size2 = computeUInt64SizeNoTag2 + computeTagSize4;
                            i7 += computeFixed64Size2;
                            break;
                        } else {
                            break;
                        }
                    case 56:
                        if (isOneofPresent(i8, i6, abstractMessageLite)) {
                            computeFixed64Size2 = CodedOutputStream.computeFixed64Size(i8);
                            i7 += computeFixed64Size2;
                            break;
                        } else {
                            break;
                        }
                    case 57:
                        if (isOneofPresent(i8, i6, abstractMessageLite)) {
                            computeFixed64Size2 = CodedOutputStream.computeFixed32Size(i8);
                            i7 += computeFixed64Size2;
                            break;
                        } else {
                            break;
                        }
                    case 58:
                        if (isOneofPresent(i8, i6, abstractMessageLite)) {
                            i7 = MessageSchema$$ExternalSyntheticOutline0.m(i8, 1, i7);
                            break;
                        } else {
                            break;
                        }
                    case 59:
                        if (isOneofPresent(i8, i6, abstractMessageLite)) {
                            Object object2 = UnsafeUtil.getObject(j, abstractMessageLite);
                            computeBytesSize2 = object2 instanceof ByteString ? CodedOutputStream.computeBytesSize(i8, (ByteString) object2) : CodedOutputStream.computeStringSizeNoTag((String) object2) + CodedOutputStream.computeTagSize(i8);
                            i7 = computeBytesSize2 + i7;
                            break;
                        } else {
                            break;
                        }
                    case 60:
                        if (isOneofPresent(i8, i6, abstractMessageLite)) {
                            computeFixed64Size2 = SchemaUtil.computeSizeMessage(i8, UnsafeUtil.getObject(j, abstractMessageLite), getMessageFieldSchema(i6));
                            i7 += computeFixed64Size2;
                            break;
                        } else {
                            break;
                        }
                    case 61:
                        if (isOneofPresent(i8, i6, abstractMessageLite)) {
                            computeFixed64Size2 = CodedOutputStream.computeBytesSize(i8, (ByteString) UnsafeUtil.getObject(j, abstractMessageLite));
                            i7 += computeFixed64Size2;
                            break;
                        } else {
                            break;
                        }
                    case 62:
                        if (isOneofPresent(i8, i6, abstractMessageLite)) {
                            computeFixed64Size2 = CodedOutputStream.computeUInt32Size(i8, oneofIntAt(j, abstractMessageLite));
                            i7 += computeFixed64Size2;
                            break;
                        } else {
                            break;
                        }
                    case 63:
                        if (isOneofPresent(i8, i6, abstractMessageLite)) {
                            int oneofIntAt2 = oneofIntAt(j, abstractMessageLite);
                            computeTagSize4 = CodedOutputStream.computeTagSize(i8);
                            computeUInt64SizeNoTag2 = CodedOutputStream.computeInt32SizeNoTag(oneofIntAt2);
                            computeFixed64Size2 = computeUInt64SizeNoTag2 + computeTagSize4;
                            i7 += computeFixed64Size2;
                            break;
                        } else {
                            break;
                        }
                    case 64:
                        if (isOneofPresent(i8, i6, abstractMessageLite)) {
                            i7 = MessageSchema$$ExternalSyntheticOutline0.m(i8, i5, i7);
                            break;
                        } else {
                            break;
                        }
                    case 65:
                        if (isOneofPresent(i8, i6, abstractMessageLite)) {
                            i7 = MessageSchema$$ExternalSyntheticOutline0.m(i8, 8, i7);
                            break;
                        } else {
                            break;
                        }
                    case 66:
                        if (isOneofPresent(i8, i6, abstractMessageLite)) {
                            int oneofIntAt3 = oneofIntAt(j, abstractMessageLite);
                            computeTagSize4 = CodedOutputStream.computeTagSize(i8);
                            computeUInt64SizeNoTag2 = CodedOutputStream.computeUInt32SizeNoTag((oneofIntAt3 >> 31) ^ (oneofIntAt3 << 1));
                            computeFixed64Size2 = computeUInt64SizeNoTag2 + computeTagSize4;
                            i7 += computeFixed64Size2;
                            break;
                        } else {
                            break;
                        }
                    case 67:
                        if (isOneofPresent(i8, i6, abstractMessageLite)) {
                            long oneofLongAt3 = oneofLongAt(j, abstractMessageLite);
                            computeTagSize6 = CodedOutputStream.computeTagSize(i8);
                            computeUInt64SizeNoTag3 = CodedOutputStream.computeUInt64SizeNoTag((oneofLongAt3 << 1) ^ (oneofLongAt3 >> 63));
                            i7 += computeUInt64SizeNoTag3 + computeTagSize6;
                            break;
                        } else {
                            break;
                        }
                    case 68:
                        if (isOneofPresent(i8, i6, abstractMessageLite)) {
                            computeFixed64Size2 = CodedOutputStream.computeGroupSize(i8, (MessageLite) UnsafeUtil.getObject(j, abstractMessageLite), getMessageFieldSchema(i6));
                            i7 += computeFixed64Size2;
                            break;
                        } else {
                            break;
                        }
                }
                i6 += 3;
                i5 = 4;
            }
            return unknownFieldSchema.getSerializedSize(unknownFieldSchema.getFromMessage(abstractMessageLite)) + i7;
        }
        Unsafe unsafe2 = UNSAFE;
        int i14 = 1048575;
        int i15 = 0;
        int i16 = 0;
        int i17 = 0;
        while (i15 < iArr.length) {
            int typeAndOffsetAt2 = typeAndOffsetAt(i15);
            int i18 = iArr[i15];
            int type2 = type(typeAndOffsetAt2);
            if (type2 <= 17) {
                i = iArr[i15 + 2];
                int i19 = i & 1048575;
                i2 = 1 << (i >>> 20);
                mapFieldSchema = mapFieldSchema2;
                z = z3;
                if (i19 != i14) {
                    i17 = unsafe2.getInt(abstractMessageLite, i19);
                    i14 = i19;
                }
            } else {
                mapFieldSchema = mapFieldSchema2;
                z = z3;
                i = (!z || type2 < FieldType.DOUBLE_LIST_PACKED.id() || type2 > FieldType.SINT64_LIST_PACKED.id()) ? 0 : iArr[i15 + 2] & 1048575;
                i2 = 0;
            }
            long j5 = typeAndOffsetAt2 & 1048575;
            switch (type2) {
                case 0:
                    if ((i17 & i2) != 0) {
                        c = '\b';
                        i16 = MessageSchema$$ExternalSyntheticOutline0.m(i18, 8, i16);
                        break;
                    }
                    c = '\b';
                    break;
                case 1:
                    if ((i17 & i2) != 0) {
                        i16 = MessageSchema$$ExternalSyntheticOutline0.m(i18, 4, i16);
                        c = '\b';
                        break;
                    }
                    c = '\b';
                case 2:
                    if ((i17 & i2) != 0) {
                        long j6 = unsafe2.getLong(abstractMessageLite, j5);
                        computeTagSize = CodedOutputStream.computeTagSize(i18);
                        computeUInt64SizeNoTag = CodedOutputStream.computeUInt64SizeNoTag(j6);
                        computeTagSize3 = computeUInt64SizeNoTag + computeTagSize;
                        i16 += computeTagSize3;
                    }
                    c = '\b';
                    break;
                case 3:
                    if ((i17 & i2) != 0) {
                        long j7 = unsafe2.getLong(abstractMessageLite, j5);
                        computeTagSize = CodedOutputStream.computeTagSize(i18);
                        computeUInt64SizeNoTag = CodedOutputStream.computeUInt64SizeNoTag(j7);
                        computeTagSize3 = computeUInt64SizeNoTag + computeTagSize;
                        i16 += computeTagSize3;
                    }
                    c = '\b';
                    break;
                case 4:
                    if ((i17 & i2) != 0) {
                        int i20 = unsafe2.getInt(abstractMessageLite, j5);
                        computeTagSize2 = CodedOutputStream.computeTagSize(i18);
                        computeInt32SizeNoTag = CodedOutputStream.computeInt32SizeNoTag(i20);
                        computeFixed64Size = computeInt32SizeNoTag + computeTagSize2;
                        i16 += computeFixed64Size;
                    }
                    c = '\b';
                    break;
                case 5:
                    if ((i17 & i2) != 0) {
                        computeFixed64Size = CodedOutputStream.computeFixed64Size(i18);
                        i16 += computeFixed64Size;
                    }
                    c = '\b';
                    break;
                case 6:
                    if ((i17 & i2) != 0) {
                        computeFixed64Size = CodedOutputStream.computeFixed32Size(i18);
                        i16 += computeFixed64Size;
                    }
                    c = '\b';
                    break;
                case 7:
                    if ((i17 & i2) != 0) {
                        i16 = MessageSchema$$ExternalSyntheticOutline0.m(i18, 1, i16);
                    }
                    c = '\b';
                    break;
                case 8:
                    if ((i17 & i2) != 0) {
                        Object object3 = unsafe2.getObject(abstractMessageLite, j5);
                        computeBytesSize = object3 instanceof ByteString ? CodedOutputStream.computeBytesSize(i18, (ByteString) object3) : CodedOutputStream.computeStringSizeNoTag((String) object3) + CodedOutputStream.computeTagSize(i18);
                        i16 = computeBytesSize + i16;
                    }
                    c = '\b';
                    break;
                case 9:
                    if ((i17 & i2) != 0) {
                        computeFixed64Size = SchemaUtil.computeSizeMessage(i18, unsafe2.getObject(abstractMessageLite, j5), getMessageFieldSchema(i15));
                        i16 += computeFixed64Size;
                    }
                    c = '\b';
                    break;
                case 10:
                    if ((i17 & i2) != 0) {
                        computeFixed64Size = CodedOutputStream.computeBytesSize(i18, (ByteString) unsafe2.getObject(abstractMessageLite, j5));
                        i16 += computeFixed64Size;
                    }
                    c = '\b';
                    break;
                case 11:
                    if ((i17 & i2) != 0) {
                        computeFixed64Size = CodedOutputStream.computeUInt32Size(i18, unsafe2.getInt(abstractMessageLite, j5));
                        i16 += computeFixed64Size;
                    }
                    c = '\b';
                    break;
                case 12:
                    if ((i17 & i2) != 0) {
                        int i21 = unsafe2.getInt(abstractMessageLite, j5);
                        computeTagSize2 = CodedOutputStream.computeTagSize(i18);
                        computeInt32SizeNoTag = CodedOutputStream.computeInt32SizeNoTag(i21);
                        computeFixed64Size = computeInt32SizeNoTag + computeTagSize2;
                        i16 += computeFixed64Size;
                    }
                    c = '\b';
                    break;
                case 13:
                    if ((i17 & i2) != 0) {
                        i16 = MessageSchema$$ExternalSyntheticOutline0.m(i18, 4, i16);
                        c = '\b';
                        break;
                    }
                    c = '\b';
                case 14:
                    if ((i17 & i2) != 0) {
                        c2 = '\b';
                        i16 = MessageSchema$$ExternalSyntheticOutline0.m(i18, 8, i16);
                        c = c2;
                        break;
                    }
                    c = '\b';
                    break;
                case 15:
                    if ((i17 & i2) != 0) {
                        int i22 = unsafe2.getInt(abstractMessageLite, j5);
                        computeTagSize2 = CodedOutputStream.computeTagSize(i18);
                        computeInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag((i22 >> 31) ^ (i22 << 1));
                        computeFixed64Size = computeInt32SizeNoTag + computeTagSize2;
                        i16 += computeFixed64Size;
                    }
                    c = '\b';
                    break;
                case 16:
                    if ((i17 & i2) != 0) {
                        long j8 = unsafe2.getLong(abstractMessageLite, j5);
                        computeTagSize = CodedOutputStream.computeTagSize(i18);
                        computeUInt64SizeNoTag = CodedOutputStream.computeUInt64SizeNoTag((j8 >> 63) ^ (j8 << 1));
                        computeTagSize3 = computeUInt64SizeNoTag + computeTagSize;
                        i16 += computeTagSize3;
                    }
                    c = '\b';
                    break;
                case 17:
                    if ((i17 & i2) != 0) {
                        computeFixed64Size = CodedOutputStream.computeGroupSize(i18, (MessageLite) unsafe2.getObject(abstractMessageLite, j5), getMessageFieldSchema(i15));
                        i16 += computeFixed64Size;
                    }
                    c = '\b';
                    break;
                case 18:
                    computeFixed64Size = SchemaUtil.computeSizeFixed64List(i18, (List) unsafe2.getObject(abstractMessageLite, j5));
                    i16 += computeFixed64Size;
                    c = '\b';
                    break;
                case 19:
                    computeFixed64Size = SchemaUtil.computeSizeFixed32List(i18, (List) unsafe2.getObject(abstractMessageLite, j5));
                    i16 += computeFixed64Size;
                    c = '\b';
                    break;
                case 20:
                    computeFixed64Size = SchemaUtil.computeSizeInt64List(i18, (List) unsafe2.getObject(abstractMessageLite, j5));
                    i16 += computeFixed64Size;
                    c = '\b';
                    break;
                case 21:
                    computeFixed64Size = SchemaUtil.computeSizeUInt64List(i18, (List) unsafe2.getObject(abstractMessageLite, j5));
                    i16 += computeFixed64Size;
                    c = '\b';
                    break;
                case 22:
                    computeFixed64Size = SchemaUtil.computeSizeInt32List(i18, (List) unsafe2.getObject(abstractMessageLite, j5));
                    i16 += computeFixed64Size;
                    c = '\b';
                    break;
                case 23:
                    computeFixed64Size = SchemaUtil.computeSizeFixed64List(i18, (List) unsafe2.getObject(abstractMessageLite, j5));
                    i16 += computeFixed64Size;
                    c = '\b';
                    break;
                case 24:
                    computeFixed64Size = SchemaUtil.computeSizeFixed32List(i18, (List) unsafe2.getObject(abstractMessageLite, j5));
                    i16 += computeFixed64Size;
                    c = '\b';
                    break;
                case 25:
                    List list4 = (List) unsafe2.getObject(abstractMessageLite, j5);
                    Class cls4 = SchemaUtil.GENERATED_MESSAGE_CLASS;
                    int size4 = list4.size();
                    computeTagSize3 = size4 == 0 ? 0 : (CodedOutputStream.computeTagSize(i18) + 1) * size4;
                    i16 += computeTagSize3;
                    c = '\b';
                    break;
                case 26:
                    computeFixed64Size = SchemaUtil.computeSizeStringList(i18, (List) unsafe2.getObject(abstractMessageLite, j5));
                    i16 += computeFixed64Size;
                    c = '\b';
                    break;
                case 27:
                    computeFixed64Size = SchemaUtil.computeSizeMessageList(i18, (List) unsafe2.getObject(abstractMessageLite, j5), getMessageFieldSchema(i15));
                    i16 += computeFixed64Size;
                    c = '\b';
                    break;
                case 28:
                    computeFixed64Size = SchemaUtil.computeSizeByteStringList(i18, (List) unsafe2.getObject(abstractMessageLite, j5));
                    i16 += computeFixed64Size;
                    c = '\b';
                    break;
                case 29:
                    computeFixed64Size = SchemaUtil.computeSizeUInt32List(i18, (List) unsafe2.getObject(abstractMessageLite, j5));
                    i16 += computeFixed64Size;
                    c = '\b';
                    break;
                case 30:
                    computeFixed64Size = SchemaUtil.computeSizeEnumList(i18, (List) unsafe2.getObject(abstractMessageLite, j5));
                    i16 += computeFixed64Size;
                    c = '\b';
                    break;
                case 31:
                    computeFixed64Size = SchemaUtil.computeSizeFixed32List(i18, (List) unsafe2.getObject(abstractMessageLite, j5));
                    i16 += computeFixed64Size;
                    c = '\b';
                    break;
                case 32:
                    computeFixed64Size = SchemaUtil.computeSizeFixed64List(i18, (List) unsafe2.getObject(abstractMessageLite, j5));
                    i16 += computeFixed64Size;
                    c = '\b';
                    break;
                case 33:
                    computeFixed64Size = SchemaUtil.computeSizeSInt32List(i18, (List) unsafe2.getObject(abstractMessageLite, j5));
                    i16 += computeFixed64Size;
                    c = '\b';
                    break;
                case 34:
                    computeFixed64Size = SchemaUtil.computeSizeSInt64List(i18, (List) unsafe2.getObject(abstractMessageLite, j5));
                    i16 += computeFixed64Size;
                    c = '\b';
                    break;
                case 35:
                    int computeSizeFixed64ListNoTag4 = SchemaUtil.computeSizeFixed64ListNoTag((List) unsafe2.getObject(abstractMessageLite, j5));
                    if (computeSizeFixed64ListNoTag4 > 0) {
                        if (z) {
                            unsafe2.putInt(abstractMessageLite, i, computeSizeFixed64ListNoTag4);
                        }
                        i16 = FieldSet$$ExternalSyntheticOutline0.m(computeSizeFixed64ListNoTag4, CodedOutputStream.computeTagSize(i18), computeSizeFixed64ListNoTag4, i16);
                    }
                    c = '\b';
                    break;
                case 36:
                    int computeSizeFixed32ListNoTag4 = SchemaUtil.computeSizeFixed32ListNoTag((List) unsafe2.getObject(abstractMessageLite, j5));
                    if (computeSizeFixed32ListNoTag4 > 0) {
                        if (z) {
                            unsafe2.putInt(abstractMessageLite, i, computeSizeFixed32ListNoTag4);
                        }
                        i16 = FieldSet$$ExternalSyntheticOutline0.m(computeSizeFixed32ListNoTag4, CodedOutputStream.computeTagSize(i18), computeSizeFixed32ListNoTag4, i16);
                    }
                    c = '\b';
                    break;
                case 37:
                    int computeSizeInt64ListNoTag2 = SchemaUtil.computeSizeInt64ListNoTag((List) unsafe2.getObject(abstractMessageLite, j5));
                    if (computeSizeInt64ListNoTag2 > 0) {
                        if (z) {
                            unsafe2.putInt(abstractMessageLite, i, computeSizeInt64ListNoTag2);
                        }
                        i16 = FieldSet$$ExternalSyntheticOutline0.m(computeSizeInt64ListNoTag2, CodedOutputStream.computeTagSize(i18), computeSizeInt64ListNoTag2, i16);
                    }
                    c = '\b';
                    break;
                case 38:
                    int computeSizeUInt64ListNoTag2 = SchemaUtil.computeSizeUInt64ListNoTag((List) unsafe2.getObject(abstractMessageLite, j5));
                    if (computeSizeUInt64ListNoTag2 > 0) {
                        if (z) {
                            unsafe2.putInt(abstractMessageLite, i, computeSizeUInt64ListNoTag2);
                        }
                        i16 = FieldSet$$ExternalSyntheticOutline0.m(computeSizeUInt64ListNoTag2, CodedOutputStream.computeTagSize(i18), computeSizeUInt64ListNoTag2, i16);
                    }
                    c = '\b';
                    break;
                case 39:
                    int computeSizeInt32ListNoTag2 = SchemaUtil.computeSizeInt32ListNoTag((List) unsafe2.getObject(abstractMessageLite, j5));
                    if (computeSizeInt32ListNoTag2 > 0) {
                        if (z) {
                            unsafe2.putInt(abstractMessageLite, i, computeSizeInt32ListNoTag2);
                        }
                        i16 = FieldSet$$ExternalSyntheticOutline0.m(computeSizeInt32ListNoTag2, CodedOutputStream.computeTagSize(i18), computeSizeInt32ListNoTag2, i16);
                    }
                    c = '\b';
                    break;
                case 40:
                    int computeSizeFixed64ListNoTag5 = SchemaUtil.computeSizeFixed64ListNoTag((List) unsafe2.getObject(abstractMessageLite, j5));
                    if (computeSizeFixed64ListNoTag5 > 0) {
                        if (z) {
                            unsafe2.putInt(abstractMessageLite, i, computeSizeFixed64ListNoTag5);
                        }
                        i16 = FieldSet$$ExternalSyntheticOutline0.m(computeSizeFixed64ListNoTag5, CodedOutputStream.computeTagSize(i18), computeSizeFixed64ListNoTag5, i16);
                    }
                    c = '\b';
                    break;
                case 41:
                    int computeSizeFixed32ListNoTag5 = SchemaUtil.computeSizeFixed32ListNoTag((List) unsafe2.getObject(abstractMessageLite, j5));
                    if (computeSizeFixed32ListNoTag5 > 0) {
                        if (z) {
                            unsafe2.putInt(abstractMessageLite, i, computeSizeFixed32ListNoTag5);
                        }
                        i16 = FieldSet$$ExternalSyntheticOutline0.m(computeSizeFixed32ListNoTag5, CodedOutputStream.computeTagSize(i18), computeSizeFixed32ListNoTag5, i16);
                    }
                    c = '\b';
                    break;
                case 42:
                    List list5 = (List) unsafe2.getObject(abstractMessageLite, j5);
                    Class cls5 = SchemaUtil.GENERATED_MESSAGE_CLASS;
                    int size5 = list5.size();
                    if (size5 > 0) {
                        if (z) {
                            unsafe2.putInt(abstractMessageLite, i, size5);
                        }
                        i16 = FieldSet$$ExternalSyntheticOutline0.m(size5, CodedOutputStream.computeTagSize(i18), size5, i16);
                    }
                    c = '\b';
                    break;
                case 43:
                    int computeSizeUInt32ListNoTag2 = SchemaUtil.computeSizeUInt32ListNoTag((List) unsafe2.getObject(abstractMessageLite, j5));
                    if (computeSizeUInt32ListNoTag2 > 0) {
                        if (z) {
                            unsafe2.putInt(abstractMessageLite, i, computeSizeUInt32ListNoTag2);
                        }
                        i16 = FieldSet$$ExternalSyntheticOutline0.m(computeSizeUInt32ListNoTag2, CodedOutputStream.computeTagSize(i18), computeSizeUInt32ListNoTag2, i16);
                    }
                    c = '\b';
                    break;
                case 44:
                    int computeSizeEnumListNoTag2 = SchemaUtil.computeSizeEnumListNoTag((List) unsafe2.getObject(abstractMessageLite, j5));
                    if (computeSizeEnumListNoTag2 > 0) {
                        if (z) {
                            unsafe2.putInt(abstractMessageLite, i, computeSizeEnumListNoTag2);
                        }
                        i16 = FieldSet$$ExternalSyntheticOutline0.m(computeSizeEnumListNoTag2, CodedOutputStream.computeTagSize(i18), computeSizeEnumListNoTag2, i16);
                    }
                    c = '\b';
                    break;
                case 45:
                    int computeSizeFixed32ListNoTag6 = SchemaUtil.computeSizeFixed32ListNoTag((List) unsafe2.getObject(abstractMessageLite, j5));
                    if (computeSizeFixed32ListNoTag6 > 0) {
                        if (z) {
                            unsafe2.putInt(abstractMessageLite, i, computeSizeFixed32ListNoTag6);
                        }
                        i16 = FieldSet$$ExternalSyntheticOutline0.m(computeSizeFixed32ListNoTag6, CodedOutputStream.computeTagSize(i18), computeSizeFixed32ListNoTag6, i16);
                    }
                    c = '\b';
                    break;
                case 46:
                    int computeSizeFixed64ListNoTag6 = SchemaUtil.computeSizeFixed64ListNoTag((List) unsafe2.getObject(abstractMessageLite, j5));
                    if (computeSizeFixed64ListNoTag6 > 0) {
                        if (z) {
                            unsafe2.putInt(abstractMessageLite, i, computeSizeFixed64ListNoTag6);
                        }
                        i16 = FieldSet$$ExternalSyntheticOutline0.m(computeSizeFixed64ListNoTag6, CodedOutputStream.computeTagSize(i18), computeSizeFixed64ListNoTag6, i16);
                    }
                    c = '\b';
                    break;
                case 47:
                    int computeSizeSInt32ListNoTag2 = SchemaUtil.computeSizeSInt32ListNoTag((List) unsafe2.getObject(abstractMessageLite, j5));
                    if (computeSizeSInt32ListNoTag2 > 0) {
                        if (z) {
                            unsafe2.putInt(abstractMessageLite, i, computeSizeSInt32ListNoTag2);
                        }
                        i16 = FieldSet$$ExternalSyntheticOutline0.m(computeSizeSInt32ListNoTag2, CodedOutputStream.computeTagSize(i18), computeSizeSInt32ListNoTag2, i16);
                    }
                    c = '\b';
                    break;
                case 48:
                    int computeSizeSInt64ListNoTag2 = SchemaUtil.computeSizeSInt64ListNoTag((List) unsafe2.getObject(abstractMessageLite, j5));
                    if (computeSizeSInt64ListNoTag2 > 0) {
                        if (z) {
                            unsafe2.putInt(abstractMessageLite, i, computeSizeSInt64ListNoTag2);
                        }
                        i16 = FieldSet$$ExternalSyntheticOutline0.m(computeSizeSInt64ListNoTag2, CodedOutputStream.computeTagSize(i18), computeSizeSInt64ListNoTag2, i16);
                    }
                    c = '\b';
                    break;
                case 49:
                    List list6 = (List) unsafe2.getObject(abstractMessageLite, j5);
                    Schema messageFieldSchema2 = getMessageFieldSchema(i15);
                    Class cls6 = SchemaUtil.GENERATED_MESSAGE_CLASS;
                    int size6 = list6.size();
                    if (size6 == 0) {
                        i3 = 0;
                    } else {
                        i3 = 0;
                        for (int i23 = 0; i23 < size6; i23++) {
                            i3 += CodedOutputStream.computeGroupSize(i18, (MessageLite) list6.get(i23), messageFieldSchema2);
                        }
                    }
                    i16 += i3;
                    c = '\b';
                    break;
                case 50:
                    computeFixed64Size = ((MapFieldSchemaLite) mapFieldSchema).getSerializedSize(i18, unsafe2.getObject(abstractMessageLite, j5), getMapFieldDefaultEntry(i15));
                    i16 += computeFixed64Size;
                    c = '\b';
                    break;
                case 51:
                    if (isOneofPresent(i18, i15, abstractMessageLite)) {
                        c2 = '\b';
                        i16 = MessageSchema$$ExternalSyntheticOutline0.m(i18, 8, i16);
                        c = c2;
                        break;
                    }
                    c = '\b';
                    break;
                case 52:
                    if (isOneofPresent(i18, i15, abstractMessageLite)) {
                        i16 = MessageSchema$$ExternalSyntheticOutline0.m(i18, 4, i16);
                        c = '\b';
                        break;
                    }
                    c = '\b';
                case 53:
                    if (isOneofPresent(i18, i15, abstractMessageLite)) {
                        long oneofLongAt4 = oneofLongAt(j5, abstractMessageLite);
                        computeTagSize = CodedOutputStream.computeTagSize(i18);
                        computeUInt64SizeNoTag = CodedOutputStream.computeUInt64SizeNoTag(oneofLongAt4);
                        computeTagSize3 = computeUInt64SizeNoTag + computeTagSize;
                        i16 += computeTagSize3;
                    }
                    c = '\b';
                    break;
                case 54:
                    if (isOneofPresent(i18, i15, abstractMessageLite)) {
                        long oneofLongAt5 = oneofLongAt(j5, abstractMessageLite);
                        computeTagSize = CodedOutputStream.computeTagSize(i18);
                        computeUInt64SizeNoTag = CodedOutputStream.computeUInt64SizeNoTag(oneofLongAt5);
                        computeTagSize3 = computeUInt64SizeNoTag + computeTagSize;
                        i16 += computeTagSize3;
                    }
                    c = '\b';
                    break;
                case 55:
                    if (isOneofPresent(i18, i15, abstractMessageLite)) {
                        int oneofIntAt4 = oneofIntAt(j5, abstractMessageLite);
                        computeTagSize2 = CodedOutputStream.computeTagSize(i18);
                        computeInt32SizeNoTag = CodedOutputStream.computeInt32SizeNoTag(oneofIntAt4);
                        computeFixed64Size = computeInt32SizeNoTag + computeTagSize2;
                        i16 += computeFixed64Size;
                    }
                    c = '\b';
                    break;
                case 56:
                    if (isOneofPresent(i18, i15, abstractMessageLite)) {
                        computeFixed64Size = CodedOutputStream.computeFixed64Size(i18);
                        i16 += computeFixed64Size;
                    }
                    c = '\b';
                    break;
                case 57:
                    if (isOneofPresent(i18, i15, abstractMessageLite)) {
                        computeFixed64Size = CodedOutputStream.computeFixed32Size(i18);
                        i16 += computeFixed64Size;
                    }
                    c = '\b';
                    break;
                case 58:
                    if (isOneofPresent(i18, i15, abstractMessageLite)) {
                        i16 = MessageSchema$$ExternalSyntheticOutline0.m(i18, 1, i16);
                    }
                    c = '\b';
                    break;
                case 59:
                    if (isOneofPresent(i18, i15, abstractMessageLite)) {
                        Object object4 = unsafe2.getObject(abstractMessageLite, j5);
                        computeBytesSize = object4 instanceof ByteString ? CodedOutputStream.computeBytesSize(i18, (ByteString) object4) : CodedOutputStream.computeStringSizeNoTag((String) object4) + CodedOutputStream.computeTagSize(i18);
                        i16 = computeBytesSize + i16;
                    }
                    c = '\b';
                    break;
                case 60:
                    if (isOneofPresent(i18, i15, abstractMessageLite)) {
                        computeFixed64Size = SchemaUtil.computeSizeMessage(i18, unsafe2.getObject(abstractMessageLite, j5), getMessageFieldSchema(i15));
                        i16 += computeFixed64Size;
                    }
                    c = '\b';
                    break;
                case 61:
                    if (isOneofPresent(i18, i15, abstractMessageLite)) {
                        computeFixed64Size = CodedOutputStream.computeBytesSize(i18, (ByteString) unsafe2.getObject(abstractMessageLite, j5));
                        i16 += computeFixed64Size;
                    }
                    c = '\b';
                    break;
                case 62:
                    if (isOneofPresent(i18, i15, abstractMessageLite)) {
                        computeFixed64Size = CodedOutputStream.computeUInt32Size(i18, oneofIntAt(j5, abstractMessageLite));
                        i16 += computeFixed64Size;
                    }
                    c = '\b';
                    break;
                case 63:
                    if (isOneofPresent(i18, i15, abstractMessageLite)) {
                        int oneofIntAt5 = oneofIntAt(j5, abstractMessageLite);
                        computeTagSize2 = CodedOutputStream.computeTagSize(i18);
                        computeInt32SizeNoTag = CodedOutputStream.computeInt32SizeNoTag(oneofIntAt5);
                        computeFixed64Size = computeInt32SizeNoTag + computeTagSize2;
                        i16 += computeFixed64Size;
                    }
                    c = '\b';
                    break;
                case 64:
                    if (isOneofPresent(i18, i15, abstractMessageLite)) {
                        i16 = MessageSchema$$ExternalSyntheticOutline0.m(i18, 4, i16);
                        c = '\b';
                        break;
                    }
                    c = '\b';
                case 65:
                    if (isOneofPresent(i18, i15, abstractMessageLite)) {
                        c2 = '\b';
                        i16 = MessageSchema$$ExternalSyntheticOutline0.m(i18, 8, i16);
                        c = c2;
                        break;
                    }
                    c = '\b';
                    break;
                case 66:
                    if (isOneofPresent(i18, i15, abstractMessageLite)) {
                        int oneofIntAt6 = oneofIntAt(j5, abstractMessageLite);
                        computeTagSize2 = CodedOutputStream.computeTagSize(i18);
                        computeInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag((oneofIntAt6 >> 31) ^ (oneofIntAt6 << 1));
                        computeFixed64Size = computeInt32SizeNoTag + computeTagSize2;
                        i16 += computeFixed64Size;
                    }
                    c = '\b';
                    break;
                case 67:
                    if (isOneofPresent(i18, i15, abstractMessageLite)) {
                        long oneofLongAt6 = oneofLongAt(j5, abstractMessageLite);
                        computeTagSize = CodedOutputStream.computeTagSize(i18);
                        computeUInt64SizeNoTag = CodedOutputStream.computeUInt64SizeNoTag((oneofLongAt6 >> 63) ^ (oneofLongAt6 << 1));
                        computeTagSize3 = computeUInt64SizeNoTag + computeTagSize;
                        i16 += computeTagSize3;
                    }
                    c = '\b';
                    break;
                case 68:
                    if (isOneofPresent(i18, i15, abstractMessageLite)) {
                        computeFixed64Size = CodedOutputStream.computeGroupSize(i18, (MessageLite) unsafe2.getObject(abstractMessageLite, j5), getMessageFieldSchema(i15));
                        i16 += computeFixed64Size;
                    }
                    c = '\b';
                    break;
                default:
                    c = '\b';
                    break;
            }
            i15 += 3;
            mapFieldSchema2 = mapFieldSchema;
            z3 = z;
        }
        int serializedSize = unknownFieldSchema.getSerializedSize(unknownFieldSchema.getFromMessage(abstractMessageLite)) + i16;
        if (!this.hasExtensions) {
            return serializedSize;
        }
        FieldSet extensions = this.extensionSchema.getExtensions(abstractMessageLite);
        int i24 = 0;
        int i25 = 0;
        while (true) {
            SmallSortedMap smallSortedMap = extensions.fields;
            if (i24 >= smallSortedMap.entryList.size()) {
                for (Map.Entry entry : smallSortedMap.getOverflowEntries()) {
                    i25 = FieldSet.computeFieldSize((GeneratedMessageLite.ExtensionDescriptor) entry.getKey(), entry.getValue()) + i25;
                }
                return serializedSize + i25;
            }
            Map.Entry arrayEntryAt = smallSortedMap.getArrayEntryAt(i24);
            i25 = FieldSet.computeFieldSize((GeneratedMessageLite.ExtensionDescriptor) arrayEntryAt.getKey(), arrayEntryAt.getValue()) + i25;
            i24++;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:103:0x01f0, code lost:
    
        if (r4 != false) goto L42;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x00d5, code lost:
    
        if (r4 != false) goto L42;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x00d7, code lost:
    
        r8 = 1231;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x00d8, code lost:
    
        r3 = r8 + r3;
     */
    @Override // com.google.protobuf.Schema
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int hashCode(com.google.protobuf.GeneratedMessageLite r12) {
        /*
            Method dump skipped, instructions count: 764
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.MessageSchema.hashCode(com.google.protobuf.GeneratedMessageLite):int");
    }

    public final boolean isFieldPresent(int i, Object obj) {
        int i2 = this.buffer[i + 2];
        long j = i2 & 1048575;
        if (j == 1048575) {
            int typeAndOffsetAt = typeAndOffsetAt(i);
            long j2 = typeAndOffsetAt & 1048575;
            switch (type(typeAndOffsetAt)) {
                case 0:
                    if (Double.doubleToRawLongBits(UnsafeUtil.MEMORY_ACCESSOR.getDouble(j2, obj)) == 0) {
                        return false;
                    }
                    break;
                case 1:
                    if (Float.floatToRawIntBits(UnsafeUtil.MEMORY_ACCESSOR.getFloat(j2, obj)) == 0) {
                        return false;
                    }
                    break;
                case 2:
                    if (UnsafeUtil.getLong(j2, obj) == 0) {
                        return false;
                    }
                    break;
                case 3:
                    if (UnsafeUtil.getLong(j2, obj) == 0) {
                        return false;
                    }
                    break;
                case 4:
                    if (UnsafeUtil.getInt(j2, obj) == 0) {
                        return false;
                    }
                    break;
                case 5:
                    if (UnsafeUtil.getLong(j2, obj) == 0) {
                        return false;
                    }
                    break;
                case 6:
                    if (UnsafeUtil.getInt(j2, obj) == 0) {
                        return false;
                    }
                    break;
                case 7:
                    return UnsafeUtil.MEMORY_ACCESSOR.getBoolean(j2, obj);
                case 8:
                    Object object = UnsafeUtil.getObject(j2, obj);
                    if (object instanceof String) {
                        return !((String) object).isEmpty();
                    }
                    if (object instanceof ByteString) {
                        return !ByteString.EMPTY.equals(object);
                    }
                    throw new IllegalArgumentException();
                case 9:
                    if (UnsafeUtil.getObject(j2, obj) == null) {
                        return false;
                    }
                    break;
                case 10:
                    return !ByteString.EMPTY.equals(UnsafeUtil.getObject(j2, obj));
                case 11:
                    if (UnsafeUtil.getInt(j2, obj) == 0) {
                        return false;
                    }
                    break;
                case 12:
                    if (UnsafeUtil.getInt(j2, obj) == 0) {
                        return false;
                    }
                    break;
                case 13:
                    if (UnsafeUtil.getInt(j2, obj) == 0) {
                        return false;
                    }
                    break;
                case 14:
                    if (UnsafeUtil.getLong(j2, obj) == 0) {
                        return false;
                    }
                    break;
                case 15:
                    if (UnsafeUtil.getInt(j2, obj) == 0) {
                        return false;
                    }
                    break;
                case 16:
                    if (UnsafeUtil.getLong(j2, obj) == 0) {
                        return false;
                    }
                    break;
                case 17:
                    if (UnsafeUtil.getObject(j2, obj) == null) {
                        return false;
                    }
                    break;
                default:
                    throw new IllegalArgumentException();
            }
        } else if (((1 << (i2 >>> 20)) & UnsafeUtil.getInt(j, obj)) == 0) {
            return false;
        }
        return true;
    }

    @Override // com.google.protobuf.Schema
    public final boolean isInitialized(Object obj) {
        int i = 1048575;
        int i2 = 0;
        int i3 = 0;
        loop0: while (true) {
            boolean z = true;
            if (i2 < this.checkInitializedCount) {
                int i4 = this.intArray[i2];
                int[] iArr = this.buffer;
                int i5 = iArr[i4];
                int typeAndOffsetAt = typeAndOffsetAt(i4);
                int i6 = iArr[i4 + 2];
                int i7 = i6 & 1048575;
                int i8 = 1 << (i6 >>> 20);
                if (i7 != i) {
                    if (i7 != 1048575) {
                        i3 = UNSAFE.getInt(obj, i7);
                    }
                    i = i7;
                }
                if ((268435456 & typeAndOffsetAt) != 0) {
                    if (!(i == 1048575 ? isFieldPresent(i4, obj) : (i3 & i8) != 0)) {
                        break;
                    }
                }
                int type = type(typeAndOffsetAt);
                if (type == 9 || type == 17) {
                    if (i == 1048575) {
                        z = isFieldPresent(i4, obj);
                    } else if ((i8 & i3) == 0) {
                        z = false;
                    }
                    if (z && !getMessageFieldSchema(i4).isInitialized(UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj))) {
                        break;
                    }
                    i2++;
                } else {
                    if (type != 27) {
                        if (type == 60 || type == 68) {
                            if (isOneofPresent(i5, i4, obj) && !getMessageFieldSchema(i4).isInitialized(UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj))) {
                                break;
                            }
                            i2++;
                        } else if (type != 49) {
                            if (type == 50) {
                                Object object = UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj);
                                ((MapFieldSchemaLite) this.mapFieldSchema).getClass();
                                MapFieldLite mapFieldLite = (MapFieldLite) object;
                                if (!mapFieldLite.isEmpty() && ((MapEntryLite) getMapFieldDefaultEntry(i4)).metadata.valueType.getJavaType() == WireFormat$JavaType.MESSAGE) {
                                    Schema schema = null;
                                    for (Object obj2 : mapFieldLite.values()) {
                                        if (schema == null) {
                                            schema = Protobuf.INSTANCE.schemaFor((Class) obj2.getClass());
                                        }
                                        if (!schema.isInitialized(obj2)) {
                                            break loop0;
                                        }
                                    }
                                }
                            } else {
                                continue;
                            }
                            i2++;
                        }
                    }
                    List list = (List) UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj);
                    if (list.isEmpty()) {
                        continue;
                    } else {
                        Schema messageFieldSchema = getMessageFieldSchema(i4);
                        for (int i9 = 0; i9 < list.size(); i9++) {
                            if (!messageFieldSchema.isInitialized(list.get(i9))) {
                                break loop0;
                            }
                        }
                    }
                    i2++;
                }
            } else if (!this.hasExtensions || this.extensionSchema.getExtensions(obj).isInitialized()) {
                return true;
            }
        }
        return false;
    }

    public final boolean isOneofPresent(int i, int i2, Object obj) {
        return UnsafeUtil.getInt((long) (this.buffer[i2 + 2] & 1048575), obj) == i;
    }

    @Override // com.google.protobuf.Schema
    public final void makeImmutable(Object obj) {
        if (isMutable(obj)) {
            if (obj instanceof GeneratedMessageLite) {
                GeneratedMessageLite generatedMessageLite = (GeneratedMessageLite) obj;
                generatedMessageLite.setMemoizedSerializedSize(Integer.MAX_VALUE);
                generatedMessageLite.memoizedHashCode = 0;
                generatedMessageLite.markImmutable();
            }
            int length = this.buffer.length;
            for (int i = 0; i < length; i += 3) {
                int typeAndOffsetAt = typeAndOffsetAt(i);
                long j = 1048575 & typeAndOffsetAt;
                int type = type(typeAndOffsetAt);
                if (type != 9) {
                    switch (type) {
                        case 18:
                        case 19:
                        case 20:
                        case 21:
                        case 22:
                        case 23:
                        case 24:
                        case 25:
                        case 26:
                        case 27:
                        case 28:
                        case 29:
                        case 30:
                        case 31:
                        case 32:
                        case 33:
                        case 34:
                        case 35:
                        case 36:
                        case 37:
                        case 38:
                        case 39:
                        case 40:
                        case 41:
                        case 42:
                        case 43:
                        case 44:
                        case 45:
                        case 46:
                        case 47:
                        case 48:
                        case 49:
                            this.listFieldSchema.makeImmutableListAt(j, obj);
                            break;
                        case 50:
                            Unsafe unsafe = UNSAFE;
                            Object object = unsafe.getObject(obj, j);
                            if (object != null) {
                                ((MapFieldSchemaLite) this.mapFieldSchema).getClass();
                                ((MapFieldLite) object).makeImmutable();
                                unsafe.putObject(obj, j, object);
                                break;
                            } else {
                                break;
                            }
                    }
                }
                if (isFieldPresent(i, obj)) {
                    getMessageFieldSchema(i).makeImmutable(UNSAFE.getObject(obj, j));
                }
            }
            this.unknownFieldSchema.makeImmutable(obj);
            if (this.hasExtensions) {
                this.extensionSchema.makeImmutable(obj);
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.google.protobuf.Schema
    public final void mergeFrom(Object obj, Object obj2) {
        Object obj3;
        checkMutable(obj);
        obj2.getClass();
        int i = 0;
        while (true) {
            int[] iArr = this.buffer;
            if (i >= iArr.length) {
                Object obj4 = obj;
                Class cls = SchemaUtil.GENERATED_MESSAGE_CLASS;
                UnknownFieldSchema unknownFieldSchema = this.unknownFieldSchema;
                unknownFieldSchema.setToMessage(obj4, unknownFieldSchema.merge(unknownFieldSchema.getFromMessage(obj4), unknownFieldSchema.getFromMessage(obj2)));
                if (this.hasExtensions) {
                    SchemaUtil.mergeExtensions(this.extensionSchema, obj4, obj2);
                    return;
                }
                return;
            }
            int typeAndOffsetAt = typeAndOffsetAt(i);
            long j = 1048575 & typeAndOffsetAt;
            int i2 = iArr[i];
            switch (type(typeAndOffsetAt)) {
                case 0:
                    if (isFieldPresent(i, obj2)) {
                        UnsafeUtil.MemoryAccessor memoryAccessor = UnsafeUtil.MEMORY_ACCESSOR;
                        obj3 = obj;
                        memoryAccessor.putDouble(obj3, j, memoryAccessor.getDouble(j, obj2));
                        setFieldPresent(i, obj3);
                        break;
                    }
                    obj3 = obj;
                    break;
                case 1:
                    if (isFieldPresent(i, obj2)) {
                        UnsafeUtil.MemoryAccessor memoryAccessor2 = UnsafeUtil.MEMORY_ACCESSOR;
                        memoryAccessor2.putFloat(obj, j, memoryAccessor2.getFloat(j, obj2));
                        setFieldPresent(i, obj);
                    }
                    obj3 = obj;
                    break;
                case 2:
                    if (isFieldPresent(i, obj2)) {
                        UnsafeUtil.putLong(obj, j, UnsafeUtil.getLong(j, obj2));
                        setFieldPresent(i, obj);
                    }
                    obj3 = obj;
                    break;
                case 3:
                    if (isFieldPresent(i, obj2)) {
                        UnsafeUtil.putLong(obj, j, UnsafeUtil.getLong(j, obj2));
                        setFieldPresent(i, obj);
                    }
                    obj3 = obj;
                    break;
                case 4:
                    if (isFieldPresent(i, obj2)) {
                        UnsafeUtil.putInt(UnsafeUtil.getInt(j, obj2), j, obj);
                        setFieldPresent(i, obj);
                    }
                    obj3 = obj;
                    break;
                case 5:
                    if (isFieldPresent(i, obj2)) {
                        UnsafeUtil.putLong(obj, j, UnsafeUtil.getLong(j, obj2));
                        setFieldPresent(i, obj);
                    }
                    obj3 = obj;
                    break;
                case 6:
                    if (isFieldPresent(i, obj2)) {
                        UnsafeUtil.putInt(UnsafeUtil.getInt(j, obj2), j, obj);
                        setFieldPresent(i, obj);
                    }
                    obj3 = obj;
                    break;
                case 7:
                    if (isFieldPresent(i, obj2)) {
                        UnsafeUtil.MemoryAccessor memoryAccessor3 = UnsafeUtil.MEMORY_ACCESSOR;
                        memoryAccessor3.putBoolean(obj, j, memoryAccessor3.getBoolean(j, obj2));
                        setFieldPresent(i, obj);
                    }
                    obj3 = obj;
                    break;
                case 8:
                    if (isFieldPresent(i, obj2)) {
                        UnsafeUtil.putObject(j, obj, UnsafeUtil.getObject(j, obj2));
                        setFieldPresent(i, obj);
                    }
                    obj3 = obj;
                    break;
                case 9:
                    mergeMessage(i, obj, obj2);
                    obj3 = obj;
                    break;
                case 10:
                    if (isFieldPresent(i, obj2)) {
                        UnsafeUtil.putObject(j, obj, UnsafeUtil.getObject(j, obj2));
                        setFieldPresent(i, obj);
                    }
                    obj3 = obj;
                    break;
                case 11:
                    if (isFieldPresent(i, obj2)) {
                        UnsafeUtil.putInt(UnsafeUtil.getInt(j, obj2), j, obj);
                        setFieldPresent(i, obj);
                    }
                    obj3 = obj;
                    break;
                case 12:
                    if (isFieldPresent(i, obj2)) {
                        UnsafeUtil.putInt(UnsafeUtil.getInt(j, obj2), j, obj);
                        setFieldPresent(i, obj);
                    }
                    obj3 = obj;
                    break;
                case 13:
                    if (isFieldPresent(i, obj2)) {
                        UnsafeUtil.putInt(UnsafeUtil.getInt(j, obj2), j, obj);
                        setFieldPresent(i, obj);
                    }
                    obj3 = obj;
                    break;
                case 14:
                    if (isFieldPresent(i, obj2)) {
                        UnsafeUtil.putLong(obj, j, UnsafeUtil.getLong(j, obj2));
                        setFieldPresent(i, obj);
                    }
                    obj3 = obj;
                    break;
                case 15:
                    if (isFieldPresent(i, obj2)) {
                        UnsafeUtil.putInt(UnsafeUtil.getInt(j, obj2), j, obj);
                        setFieldPresent(i, obj);
                    }
                    obj3 = obj;
                    break;
                case 16:
                    if (isFieldPresent(i, obj2)) {
                        UnsafeUtil.putLong(obj, j, UnsafeUtil.getLong(j, obj2));
                        setFieldPresent(i, obj);
                    }
                    obj3 = obj;
                    break;
                case 17:
                    mergeMessage(i, obj, obj2);
                    obj3 = obj;
                    break;
                case 18:
                case 19:
                case 20:
                case 21:
                case 22:
                case 23:
                case 24:
                case 25:
                case 26:
                case 27:
                case 28:
                case 29:
                case 30:
                case 31:
                case 32:
                case 33:
                case 34:
                case 35:
                case 36:
                case 37:
                case 38:
                case 39:
                case 40:
                case 41:
                case 42:
                case 43:
                case 44:
                case 45:
                case 46:
                case 47:
                case 48:
                case 49:
                    this.listFieldSchema.mergeListsAt(j, obj, obj2);
                    obj3 = obj;
                    break;
                case 50:
                    Class cls2 = SchemaUtil.GENERATED_MESSAGE_CLASS;
                    UnsafeUtil.putObject(j, obj, ((MapFieldSchemaLite) this.mapFieldSchema).mergeFrom(UnsafeUtil.getObject(j, obj), UnsafeUtil.getObject(j, obj2)));
                    obj3 = obj;
                    break;
                case 51:
                case 52:
                case 53:
                case 54:
                case 55:
                case 56:
                case 57:
                case 58:
                case 59:
                    if (isOneofPresent(i2, i, obj2)) {
                        UnsafeUtil.putObject(j, obj, UnsafeUtil.getObject(j, obj2));
                        setOneofPresent(i2, i, obj);
                    }
                    obj3 = obj;
                    break;
                case 60:
                    mergeOneofMessage(i, obj, obj2);
                    obj3 = obj;
                    break;
                case 61:
                case 62:
                case 63:
                case 64:
                case 65:
                case 66:
                case 67:
                    if (isOneofPresent(i2, i, obj2)) {
                        UnsafeUtil.putObject(j, obj, UnsafeUtil.getObject(j, obj2));
                        setOneofPresent(i2, i, obj);
                    }
                    obj3 = obj;
                    break;
                case 68:
                    mergeOneofMessage(i, obj, obj2);
                    obj3 = obj;
                    break;
                default:
                    obj3 = obj;
                    break;
            }
            i += 3;
            obj = obj3;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:48:0x08c6 A[LOOP:2: B:47:0x08c4->B:48:0x08c6, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:52:0x08d9  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x0883 A[Catch: all -> 0x0889, TryCatch #32 {all -> 0x0889, blocks: (B:61:0x087e, B:63:0x0883, B:64:0x088c), top: B:60:0x087e }] */
    /* JADX WARN: Removed duplicated region for block: B:66:0x0892 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void mergeFromHelper(com.google.protobuf.UnknownFieldSchema r20, com.google.protobuf.ExtensionSchema r21, java.lang.Object r22, com.google.protobuf.CodedInputStreamReader r23, com.google.protobuf.ExtensionRegistryLite r24) {
        /*
            Method dump skipped, instructions count: 2412
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.MessageSchema.mergeFromHelper(com.google.protobuf.UnknownFieldSchema, com.google.protobuf.ExtensionSchema, java.lang.Object, com.google.protobuf.CodedInputStreamReader, com.google.protobuf.ExtensionRegistryLite):void");
    }

    public final void mergeMap(Object obj, int i, Object obj2, ExtensionRegistryLite extensionRegistryLite, CodedInputStreamReader codedInputStreamReader) {
        long typeAndOffsetAt = typeAndOffsetAt(i) & 1048575;
        Object object = UnsafeUtil.getObject(typeAndOffsetAt, obj);
        MapFieldSchema mapFieldSchema = this.mapFieldSchema;
        if (object == null) {
            ((MapFieldSchemaLite) mapFieldSchema).getClass();
            object = MapFieldLite.EMPTY_MAP_FIELD.mutableCopy();
            UnsafeUtil.putObject(typeAndOffsetAt, obj, object);
        } else {
            MapFieldSchemaLite mapFieldSchemaLite = (MapFieldSchemaLite) mapFieldSchema;
            mapFieldSchemaLite.getClass();
            if (!((MapFieldLite) object).isMutable()) {
                mapFieldSchemaLite.getClass();
                MapFieldLite mutableCopy = MapFieldLite.EMPTY_MAP_FIELD.mutableCopy();
                mapFieldSchemaLite.mergeFrom(mutableCopy, object);
                UnsafeUtil.putObject(typeAndOffsetAt, obj, mutableCopy);
                object = mutableCopy;
            }
        }
        MapFieldSchemaLite mapFieldSchemaLite2 = (MapFieldSchemaLite) mapFieldSchema;
        mapFieldSchemaLite2.getClass();
        MapFieldLite mapFieldLite = (MapFieldLite) object;
        mapFieldSchemaLite2.getClass();
        MapEntryLite.Metadata metadata = ((MapEntryLite) obj2).metadata;
        codedInputStreamReader.requireWireType(2);
        CodedInputStream codedInputStream = codedInputStreamReader.input;
        int pushLimit = codedInputStream.pushLimit(codedInputStream.readUInt32());
        Object obj3 = metadata.defaultKey;
        Object obj4 = metadata.defaultValue;
        Object obj5 = obj4;
        while (true) {
            try {
                int fieldNumber = codedInputStreamReader.getFieldNumber();
                if (fieldNumber == Integer.MAX_VALUE || codedInputStream.isAtEnd()) {
                    break;
                }
                if (fieldNumber == 1) {
                    obj3 = codedInputStreamReader.readField(metadata.keyType, null, null);
                } else if (fieldNumber != 2) {
                    try {
                        if (!codedInputStreamReader.skipField()) {
                            throw new InvalidProtocolBufferException("Unable to parse map entry.");
                        }
                    } catch (InvalidProtocolBufferException.InvalidWireTypeException unused) {
                        if (!codedInputStreamReader.skipField()) {
                            throw new InvalidProtocolBufferException("Unable to parse map entry.");
                        }
                    }
                } else {
                    obj5 = codedInputStreamReader.readField(metadata.valueType, obj4.getClass(), extensionRegistryLite);
                }
            } catch (Throwable th) {
                codedInputStream.popLimit(pushLimit);
                throw th;
            }
        }
        mapFieldLite.put(obj3, obj5);
        codedInputStream.popLimit(pushLimit);
    }

    public final void mergeMessage(int i, Object obj, Object obj2) {
        if (isFieldPresent(i, obj2)) {
            long typeAndOffsetAt = typeAndOffsetAt(i) & 1048575;
            Unsafe unsafe = UNSAFE;
            Object object = unsafe.getObject(obj2, typeAndOffsetAt);
            if (object == null) {
                throw new IllegalStateException("Source subfield " + this.buffer[i] + " is present but null: " + obj2);
            }
            Schema messageFieldSchema = getMessageFieldSchema(i);
            if (!isFieldPresent(i, obj)) {
                if (isMutable(object)) {
                    GeneratedMessageLite newInstance = messageFieldSchema.newInstance();
                    messageFieldSchema.mergeFrom(newInstance, object);
                    unsafe.putObject(obj, typeAndOffsetAt, newInstance);
                } else {
                    unsafe.putObject(obj, typeAndOffsetAt, object);
                }
                setFieldPresent(i, obj);
                return;
            }
            Object object2 = unsafe.getObject(obj, typeAndOffsetAt);
            if (!isMutable(object2)) {
                GeneratedMessageLite newInstance2 = messageFieldSchema.newInstance();
                messageFieldSchema.mergeFrom(newInstance2, object2);
                unsafe.putObject(obj, typeAndOffsetAt, newInstance2);
                object2 = newInstance2;
            }
            messageFieldSchema.mergeFrom(object2, object);
        }
    }

    public final void mergeOneofMessage(int i, Object obj, Object obj2) {
        int[] iArr = this.buffer;
        int i2 = iArr[i];
        if (isOneofPresent(i2, i, obj2)) {
            long typeAndOffsetAt = typeAndOffsetAt(i) & 1048575;
            Unsafe unsafe = UNSAFE;
            Object object = unsafe.getObject(obj2, typeAndOffsetAt);
            if (object == null) {
                throw new IllegalStateException("Source subfield " + iArr[i] + " is present but null: " + obj2);
            }
            Schema messageFieldSchema = getMessageFieldSchema(i);
            if (!isOneofPresent(i2, i, obj)) {
                if (isMutable(object)) {
                    GeneratedMessageLite newInstance = messageFieldSchema.newInstance();
                    messageFieldSchema.mergeFrom(newInstance, object);
                    unsafe.putObject(obj, typeAndOffsetAt, newInstance);
                } else {
                    unsafe.putObject(obj, typeAndOffsetAt, object);
                }
                setOneofPresent(i2, i, obj);
                return;
            }
            Object object2 = unsafe.getObject(obj, typeAndOffsetAt);
            if (!isMutable(object2)) {
                GeneratedMessageLite newInstance2 = messageFieldSchema.newInstance();
                messageFieldSchema.mergeFrom(newInstance2, object2);
                unsafe.putObject(obj, typeAndOffsetAt, newInstance2);
                object2 = newInstance2;
            }
            messageFieldSchema.mergeFrom(object2, object);
        }
    }

    public final Object mutableMessageFieldForMerge(int i, Object obj) {
        Schema messageFieldSchema = getMessageFieldSchema(i);
        long typeAndOffsetAt = typeAndOffsetAt(i) & 1048575;
        if (!isFieldPresent(i, obj)) {
            return messageFieldSchema.newInstance();
        }
        Object object = UNSAFE.getObject(obj, typeAndOffsetAt);
        if (isMutable(object)) {
            return object;
        }
        GeneratedMessageLite newInstance = messageFieldSchema.newInstance();
        if (object != null) {
            messageFieldSchema.mergeFrom(newInstance, object);
        }
        return newInstance;
    }

    public final Object mutableOneofMessageFieldForMerge(int i, int i2, Object obj) {
        Schema messageFieldSchema = getMessageFieldSchema(i2);
        if (!isOneofPresent(i, i2, obj)) {
            return messageFieldSchema.newInstance();
        }
        Object object = UNSAFE.getObject(obj, typeAndOffsetAt(i2) & 1048575);
        if (isMutable(object)) {
            return object;
        }
        GeneratedMessageLite newInstance = messageFieldSchema.newInstance();
        if (object != null) {
            messageFieldSchema.mergeFrom(newInstance, object);
        }
        return newInstance;
    }

    @Override // com.google.protobuf.Schema
    public final GeneratedMessageLite newInstance() {
        ((NewInstanceSchemaLite) this.newInstanceSchema).getClass();
        return ((GeneratedMessageLite) this.defaultInstance).newMutableInstance$1();
    }

    public final int parseMapField(Object obj, byte[] bArr, int i, int i2, int i3, long j, ArrayDecoders.Registers registers) {
        int decodeMapEntryValue;
        Unsafe unsafe = UNSAFE;
        Object mapFieldDefaultEntry = getMapFieldDefaultEntry(i3);
        Object object = unsafe.getObject(obj, j);
        MapFieldSchemaLite mapFieldSchemaLite = (MapFieldSchemaLite) this.mapFieldSchema;
        mapFieldSchemaLite.getClass();
        if (!((MapFieldLite) object).isMutable()) {
            mapFieldSchemaLite.getClass();
            MapFieldLite mutableCopy = MapFieldLite.EMPTY_MAP_FIELD.mutableCopy();
            mapFieldSchemaLite.mergeFrom(mutableCopy, object);
            unsafe.putObject(obj, j, mutableCopy);
            object = mutableCopy;
        }
        mapFieldSchemaLite.getClass();
        MapEntryLite.Metadata metadata = ((MapEntryLite) mapFieldDefaultEntry).metadata;
        mapFieldSchemaLite.getClass();
        MapFieldLite mapFieldLite = (MapFieldLite) object;
        int decodeVarint32 = ArrayDecoders.decodeVarint32(bArr, i, registers);
        int i4 = registers.int1;
        if (i4 < 0 || i4 > i2 - decodeVarint32) {
            throw InvalidProtocolBufferException.truncatedMessage();
        }
        int i5 = decodeVarint32 + i4;
        Object obj2 = metadata.defaultKey;
        Object obj3 = metadata.defaultValue;
        Object obj4 = obj2;
        Object obj5 = obj3;
        while (decodeVarint32 < i5) {
            int i6 = decodeVarint32 + 1;
            int i7 = bArr[decodeVarint32];
            if (i7 < 0) {
                i6 = ArrayDecoders.decodeVarint32(i7, bArr, i6, registers);
                i7 = registers.int1;
            }
            int i8 = i7 >>> 3;
            int i9 = i7 & 7;
            if (i8 != 1) {
                if (i8 == 2 && i9 == metadata.valueType.getWireType()) {
                    decodeMapEntryValue = decodeMapEntryValue(bArr, i6, i2, metadata.valueType, obj3.getClass(), registers);
                    obj5 = registers.object1;
                    decodeVarint32 = decodeMapEntryValue;
                }
                decodeVarint32 = ArrayDecoders.skipField(i7, bArr, i6, i2, registers);
            } else if (i9 == metadata.keyType.getWireType()) {
                decodeMapEntryValue = decodeMapEntryValue(bArr, i6, i2, metadata.keyType, null, registers);
                obj4 = registers.object1;
                decodeVarint32 = decodeMapEntryValue;
            } else {
                decodeVarint32 = ArrayDecoders.skipField(i7, bArr, i6, i2, registers);
            }
        }
        if (decodeVarint32 != i5) {
            throw InvalidProtocolBufferException.parseFailure();
        }
        mapFieldLite.put(obj4, obj5);
        return i5;
    }

    public final int parseOneofField(Object obj, byte[] bArr, int i, int i2, int i3, int i4, int i5, int i6, int i7, long j, int i8, ArrayDecoders.Registers registers) {
        int i9;
        Unsafe unsafe = UNSAFE;
        long j2 = this.buffer[i8 + 2] & 1048575;
        switch (i7) {
            case 51:
                if (i5 != 1) {
                    return i;
                }
                unsafe.putObject(obj, j, Double.valueOf(Double.longBitsToDouble(ArrayDecoders.decodeFixed64(i, bArr))));
                int i10 = i + 8;
                unsafe.putInt(obj, j2, i4);
                return i10;
            case 52:
                if (i5 != 5) {
                    return i;
                }
                unsafe.putObject(obj, j, Float.valueOf(Float.intBitsToFloat(ArrayDecoders.decodeFixed32(i, bArr))));
                int i11 = i + 4;
                unsafe.putInt(obj, j2, i4);
                return i11;
            case 53:
            case 54:
                if (i5 != 0) {
                    return i;
                }
                int decodeVarint64 = ArrayDecoders.decodeVarint64(bArr, i, registers);
                unsafe.putObject(obj, j, Long.valueOf(registers.long1));
                unsafe.putInt(obj, j2, i4);
                return decodeVarint64;
            case 55:
            case 62:
                if (i5 != 0) {
                    return i;
                }
                int decodeVarint32 = ArrayDecoders.decodeVarint32(bArr, i, registers);
                unsafe.putObject(obj, j, Integer.valueOf(registers.int1));
                unsafe.putInt(obj, j2, i4);
                return decodeVarint32;
            case 56:
            case 65:
                if (i5 != 1) {
                    return i;
                }
                unsafe.putObject(obj, j, Long.valueOf(ArrayDecoders.decodeFixed64(i, bArr)));
                int i12 = i + 8;
                unsafe.putInt(obj, j2, i4);
                return i12;
            case 57:
            case 64:
                if (i5 != 5) {
                    return i;
                }
                unsafe.putObject(obj, j, Integer.valueOf(ArrayDecoders.decodeFixed32(i, bArr)));
                int i13 = i + 4;
                unsafe.putInt(obj, j2, i4);
                return i13;
            case 58:
                if (i5 != 0) {
                    return i;
                }
                int decodeVarint642 = ArrayDecoders.decodeVarint64(bArr, i, registers);
                unsafe.putObject(obj, j, Boolean.valueOf(registers.long1 != 0));
                unsafe.putInt(obj, j2, i4);
                return decodeVarint642;
            case 59:
                if (i5 != 2) {
                    return i;
                }
                int decodeVarint322 = ArrayDecoders.decodeVarint32(bArr, i, registers);
                int i14 = registers.int1;
                if (i14 == 0) {
                    unsafe.putObject(obj, j, "");
                } else {
                    if ((i6 & VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS) != 0) {
                        if (!Utf8.processor.isValidUtf8(decodeVarint322, decodeVarint322 + i14, bArr)) {
                            throw InvalidProtocolBufferException.invalidUtf8();
                        }
                    }
                    unsafe.putObject(obj, j, new String(bArr, decodeVarint322, i14, Internal.UTF_8));
                    decodeVarint322 += i14;
                }
                unsafe.putInt(obj, j2, i4);
                return decodeVarint322;
            case 60:
                i9 = i;
                if (i5 == 2) {
                    Object mutableOneofMessageFieldForMerge = mutableOneofMessageFieldForMerge(i4, i8, obj);
                    int mergeMessageField = ArrayDecoders.mergeMessageField(mutableOneofMessageFieldForMerge, getMessageFieldSchema(i8), bArr, i9, i2, registers);
                    storeOneofMessageField(i4, i8, obj, mutableOneofMessageFieldForMerge);
                    return mergeMessageField;
                }
                return i9;
            case 61:
                i9 = i;
                if (i5 == 2) {
                    int decodeBytes = ArrayDecoders.decodeBytes(bArr, i9, registers);
                    unsafe.putObject(obj, j, registers.object1);
                    unsafe.putInt(obj, j2, i4);
                    return decodeBytes;
                }
                return i9;
            case 63:
                i9 = i;
                if (i5 == 0) {
                    int decodeVarint323 = ArrayDecoders.decodeVarint32(bArr, i9, registers);
                    int i15 = registers.int1;
                    Internal.EnumVerifier enumFieldVerifier = getEnumFieldVerifier(i8);
                    if (enumFieldVerifier != null && !enumFieldVerifier.isInRange(i15)) {
                        getMutableUnknownFields(obj).storeField(i3, Long.valueOf(i15));
                        return decodeVarint323;
                    }
                    unsafe.putObject(obj, j, Integer.valueOf(i15));
                    unsafe.putInt(obj, j2, i4);
                    return decodeVarint323;
                }
                return i9;
            case 66:
                i9 = i;
                if (i5 == 0) {
                    int decodeVarint324 = ArrayDecoders.decodeVarint32(bArr, i9, registers);
                    unsafe.putObject(obj, j, Integer.valueOf(CodedInputStream.decodeZigZag32(registers.int1)));
                    unsafe.putInt(obj, j2, i4);
                    return decodeVarint324;
                }
                return i9;
            case 67:
                i9 = i;
                if (i5 == 0) {
                    int decodeVarint643 = ArrayDecoders.decodeVarint64(bArr, i9, registers);
                    unsafe.putObject(obj, j, Long.valueOf(CodedInputStream.decodeZigZag64(registers.long1)));
                    unsafe.putInt(obj, j2, i4);
                    return decodeVarint643;
                }
                return i9;
            case 68:
                if (i5 == 3) {
                    Object mutableOneofMessageFieldForMerge2 = mutableOneofMessageFieldForMerge(i4, i8, obj);
                    int parseProto2Message = ((MessageSchema) getMessageFieldSchema(i8)).parseProto2Message(mutableOneofMessageFieldForMerge2, bArr, i, i2, (i3 & (-8)) | 4, registers);
                    registers.object1 = mutableOneofMessageFieldForMerge2;
                    storeOneofMessageField(i4, i8, obj, mutableOneofMessageFieldForMerge2);
                    return parseProto2Message;
                }
            default:
                return i;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:41:0x044f, code lost:
    
        r10 = r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x07ab, code lost:
    
        if (r0 == r1) goto L246;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x07ad, code lost:
    
        r28.putInt(r9, r0, r13);
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x07b3, code lost:
    
        r3 = null;
        r7 = r8.checkInitializedCount;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x07b9, code lost:
    
        if (r7 >= r8.repeatedFieldOffsetStart) goto L314;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x07bb, code lost:
    
        r3 = (com.google.protobuf.UnknownFieldSetLite) r8.filterMapUnknownEnumValues(r9, r8.intArray[r7], r3, r8.unknownFieldSchema, r31);
        r7 = r7 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x07cf, code lost:
    
        r0 = r8;
        r1 = r9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x07d1, code lost:
    
        if (r3 == null) goto L253;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x07d3, code lost:
    
        r0.unknownFieldSchema.setBuilderToMessage(r1, r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x07d8, code lost:
    
        if (r35 != 0) goto L258;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x07da, code lost:
    
        if (r10 != r6) goto L256;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x07e1, code lost:
    
        throw com.google.protobuf.InvalidProtocolBufferException.parseFailure();
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x07e6, code lost:
    
        return r10;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x07e2, code lost:
    
        if (r10 > r6) goto L261;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x07e4, code lost:
    
        if (r14 != r35) goto L261;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x07eb, code lost:
    
        throw com.google.protobuf.InvalidProtocolBufferException.parseFailure();
     */
    /* JADX WARN: Removed duplicated region for block: B:121:0x0764  */
    /* JADX WARN: Removed duplicated region for block: B:123:0x0769  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int parseProto2Message(java.lang.Object r31, byte[] r32, int r33, int r34, int r35, com.google.protobuf.ArrayDecoders.Registers r36) {
        /*
            Method dump skipped, instructions count: 2140
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.MessageSchema.parseProto2Message(java.lang.Object, byte[], int, int, int, com.google.protobuf.ArrayDecoders$Registers):int");
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public final int parseRepeatedField(Object obj, byte[] bArr, int i, int i2, int i3, int i4, int i5, int i6, long j, int i7, long j2, ArrayDecoders.Registers registers) {
        int decodeVarint32List;
        Unsafe unsafe = UNSAFE;
        Internal.ProtobufList protobufList = (Internal.ProtobufList) unsafe.getObject(obj, j2);
        if (!((AbstractProtobufList) protobufList).isMutable) {
            int size = protobufList.size();
            protobufList = protobufList.mutableCopyWithCapacity(size == 0 ? 10 : size * 2);
            unsafe.putObject(obj, j2, protobufList);
        }
        Internal.ProtobufList protobufList2 = protobufList;
        switch (i7) {
            case 18:
            case 35:
                if (i5 == 2) {
                    return ArrayDecoders.decodePackedDoubleList(bArr, i, protobufList2, registers);
                }
                if (i5 == 1) {
                    DoubleArrayList doubleArrayList = (DoubleArrayList) protobufList2;
                    doubleArrayList.addDouble(Double.longBitsToDouble(ArrayDecoders.decodeFixed64(i, bArr)));
                    int i8 = i + 8;
                    while (i8 < i2) {
                        int decodeVarint32 = ArrayDecoders.decodeVarint32(bArr, i8, registers);
                        if (i3 != registers.int1) {
                            return i8;
                        }
                        doubleArrayList.addDouble(Double.longBitsToDouble(ArrayDecoders.decodeFixed64(decodeVarint32, bArr)));
                        i8 = decodeVarint32 + 8;
                    }
                    return i8;
                }
                return i;
            case 19:
            case 36:
                if (i5 == 2) {
                    return ArrayDecoders.decodePackedFloatList(bArr, i, protobufList2, registers);
                }
                if (i5 == 5) {
                    FloatArrayList floatArrayList = (FloatArrayList) protobufList2;
                    floatArrayList.addFloat(Float.intBitsToFloat(ArrayDecoders.decodeFixed32(i, bArr)));
                    int i9 = i + 4;
                    while (i9 < i2) {
                        int decodeVarint322 = ArrayDecoders.decodeVarint32(bArr, i9, registers);
                        if (i3 != registers.int1) {
                            return i9;
                        }
                        floatArrayList.addFloat(Float.intBitsToFloat(ArrayDecoders.decodeFixed32(decodeVarint322, bArr)));
                        i9 = decodeVarint322 + 4;
                    }
                    return i9;
                }
                return i;
            case 20:
            case 21:
            case 37:
            case 38:
                if (i5 == 2) {
                    LongArrayList longArrayList = (LongArrayList) protobufList2;
                    int decodeVarint323 = ArrayDecoders.decodeVarint32(bArr, i, registers);
                    int i10 = registers.int1 + decodeVarint323;
                    while (decodeVarint323 < i10) {
                        decodeVarint323 = ArrayDecoders.decodeVarint64(bArr, decodeVarint323, registers);
                        longArrayList.addLong(registers.long1);
                    }
                    if (decodeVarint323 == i10) {
                        return decodeVarint323;
                    }
                    throw InvalidProtocolBufferException.truncatedMessage();
                }
                if (i5 == 0) {
                    LongArrayList longArrayList2 = (LongArrayList) protobufList2;
                    int decodeVarint64 = ArrayDecoders.decodeVarint64(bArr, i, registers);
                    longArrayList2.addLong(registers.long1);
                    while (decodeVarint64 < i2) {
                        int decodeVarint324 = ArrayDecoders.decodeVarint32(bArr, decodeVarint64, registers);
                        if (i3 != registers.int1) {
                            return decodeVarint64;
                        }
                        decodeVarint64 = ArrayDecoders.decodeVarint64(bArr, decodeVarint324, registers);
                        longArrayList2.addLong(registers.long1);
                    }
                    return decodeVarint64;
                }
                return i;
            case 22:
            case 29:
            case 39:
            case 43:
                if (i5 == 2) {
                    return ArrayDecoders.decodePackedVarint32List(bArr, i, protobufList2, registers);
                }
                if (i5 == 0) {
                    return ArrayDecoders.decodeVarint32List(i3, bArr, i, i2, protobufList2, registers);
                }
                return i;
            case 23:
            case 32:
            case 40:
            case 46:
                if (i5 == 2) {
                    return ArrayDecoders.decodePackedFixed64List(bArr, i, protobufList2, registers);
                }
                if (i5 == 1) {
                    LongArrayList longArrayList3 = (LongArrayList) protobufList2;
                    longArrayList3.addLong(ArrayDecoders.decodeFixed64(i, bArr));
                    int i11 = i + 8;
                    while (i11 < i2) {
                        int decodeVarint325 = ArrayDecoders.decodeVarint32(bArr, i11, registers);
                        if (i3 != registers.int1) {
                            return i11;
                        }
                        longArrayList3.addLong(ArrayDecoders.decodeFixed64(decodeVarint325, bArr));
                        i11 = decodeVarint325 + 8;
                    }
                    return i11;
                }
                return i;
            case 24:
            case 31:
            case 41:
            case 45:
                if (i5 == 2) {
                    return ArrayDecoders.decodePackedFixed32List(bArr, i, protobufList2, registers);
                }
                if (i5 == 5) {
                    IntArrayList intArrayList = (IntArrayList) protobufList2;
                    intArrayList.addInt(ArrayDecoders.decodeFixed32(i, bArr));
                    int i12 = i + 4;
                    while (i12 < i2) {
                        int decodeVarint326 = ArrayDecoders.decodeVarint32(bArr, i12, registers);
                        if (i3 != registers.int1) {
                            return i12;
                        }
                        intArrayList.addInt(ArrayDecoders.decodeFixed32(decodeVarint326, bArr));
                        i12 = decodeVarint326 + 4;
                    }
                    return i12;
                }
                return i;
            case 25:
            case 42:
                if (i5 == 2) {
                    return ArrayDecoders.decodePackedBoolList(bArr, i, protobufList2, registers);
                }
                if (i5 == 0) {
                    BooleanArrayList booleanArrayList = (BooleanArrayList) protobufList2;
                    int decodeVarint642 = ArrayDecoders.decodeVarint64(bArr, i, registers);
                    booleanArrayList.addBoolean(registers.long1 != 0);
                    while (decodeVarint642 < i2) {
                        int decodeVarint327 = ArrayDecoders.decodeVarint32(bArr, decodeVarint642, registers);
                        if (i3 != registers.int1) {
                            return decodeVarint642;
                        }
                        decodeVarint642 = ArrayDecoders.decodeVarint64(bArr, decodeVarint327, registers);
                        booleanArrayList.addBoolean(registers.long1 != 0);
                    }
                    return decodeVarint642;
                }
                return i;
            case 26:
                if (i5 == 2) {
                    if ((j & 536870912) == 0) {
                        int decodeVarint328 = ArrayDecoders.decodeVarint32(bArr, i, registers);
                        int i13 = registers.int1;
                        if (i13 < 0) {
                            throw InvalidProtocolBufferException.negativeSize();
                        }
                        if (i13 == 0) {
                            protobufList2.add("");
                        } else {
                            protobufList2.add(new String(bArr, decodeVarint328, i13, Internal.UTF_8));
                            decodeVarint328 += i13;
                        }
                        while (decodeVarint328 < i2) {
                            int decodeVarint329 = ArrayDecoders.decodeVarint32(bArr, decodeVarint328, registers);
                            if (i3 != registers.int1) {
                                return decodeVarint328;
                            }
                            decodeVarint328 = ArrayDecoders.decodeVarint32(bArr, decodeVarint329, registers);
                            int i14 = registers.int1;
                            if (i14 < 0) {
                                throw InvalidProtocolBufferException.negativeSize();
                            }
                            if (i14 == 0) {
                                protobufList2.add("");
                            } else {
                                protobufList2.add(new String(bArr, decodeVarint328, i14, Internal.UTF_8));
                                decodeVarint328 += i14;
                            }
                        }
                        return decodeVarint328;
                    }
                    int decodeVarint3210 = ArrayDecoders.decodeVarint32(bArr, i, registers);
                    int i15 = registers.int1;
                    if (i15 < 0) {
                        throw InvalidProtocolBufferException.negativeSize();
                    }
                    if (i15 == 0) {
                        protobufList2.add("");
                    } else {
                        int i16 = decodeVarint3210 + i15;
                        if (!Utf8.processor.isValidUtf8(decodeVarint3210, i16, bArr)) {
                            throw InvalidProtocolBufferException.invalidUtf8();
                        }
                        protobufList2.add(new String(bArr, decodeVarint3210, i15, Internal.UTF_8));
                        decodeVarint3210 = i16;
                    }
                    while (decodeVarint3210 < i2) {
                        int decodeVarint3211 = ArrayDecoders.decodeVarint32(bArr, decodeVarint3210, registers);
                        if (i3 != registers.int1) {
                            return decodeVarint3210;
                        }
                        decodeVarint3210 = ArrayDecoders.decodeVarint32(bArr, decodeVarint3211, registers);
                        int i17 = registers.int1;
                        if (i17 < 0) {
                            throw InvalidProtocolBufferException.negativeSize();
                        }
                        if (i17 == 0) {
                            protobufList2.add("");
                        } else {
                            int i18 = decodeVarint3210 + i17;
                            if (!Utf8.processor.isValidUtf8(decodeVarint3210, i18, bArr)) {
                                throw InvalidProtocolBufferException.invalidUtf8();
                            }
                            protobufList2.add(new String(bArr, decodeVarint3210, i17, Internal.UTF_8));
                            decodeVarint3210 = i18;
                        }
                    }
                    return decodeVarint3210;
                }
                return i;
            case 27:
                if (i5 == 2) {
                    return ArrayDecoders.decodeMessageList(getMessageFieldSchema(i6), i3, bArr, i, i2, protobufList2, registers);
                }
                return i;
            case 28:
                if (i5 == 2) {
                    int decodeVarint3212 = ArrayDecoders.decodeVarint32(bArr, i, registers);
                    int i19 = registers.int1;
                    if (i19 < 0) {
                        throw InvalidProtocolBufferException.negativeSize();
                    }
                    if (i19 > bArr.length - decodeVarint3212) {
                        throw InvalidProtocolBufferException.truncatedMessage();
                    }
                    if (i19 == 0) {
                        protobufList2.add(ByteString.EMPTY);
                    } else {
                        protobufList2.add(ByteString.copyFrom(decodeVarint3212, i19, bArr));
                        decodeVarint3212 += i19;
                    }
                    while (decodeVarint3212 < i2) {
                        int decodeVarint3213 = ArrayDecoders.decodeVarint32(bArr, decodeVarint3212, registers);
                        if (i3 != registers.int1) {
                            return decodeVarint3212;
                        }
                        decodeVarint3212 = ArrayDecoders.decodeVarint32(bArr, decodeVarint3213, registers);
                        int i20 = registers.int1;
                        if (i20 < 0) {
                            throw InvalidProtocolBufferException.negativeSize();
                        }
                        if (i20 > bArr.length - decodeVarint3212) {
                            throw InvalidProtocolBufferException.truncatedMessage();
                        }
                        if (i20 == 0) {
                            protobufList2.add(ByteString.EMPTY);
                        } else {
                            protobufList2.add(ByteString.copyFrom(decodeVarint3212, i20, bArr));
                            decodeVarint3212 += i20;
                        }
                    }
                    return decodeVarint3212;
                }
                return i;
            case 30:
            case 44:
                if (i5 != 2) {
                    if (i5 == 0) {
                        decodeVarint32List = ArrayDecoders.decodeVarint32List(i3, bArr, i, i2, protobufList2, registers);
                    }
                    return i;
                }
                decodeVarint32List = ArrayDecoders.decodePackedVarint32List(bArr, i, protobufList2, registers);
                SchemaUtil.filterUnknownEnumList(obj, i4, protobufList2, getEnumFieldVerifier(i6), (Object) null, this.unknownFieldSchema);
                return decodeVarint32List;
            case 33:
            case 47:
                if (i5 == 2) {
                    return ArrayDecoders.decodePackedSInt32List(bArr, i, protobufList2, registers);
                }
                if (i5 == 0) {
                    IntArrayList intArrayList2 = (IntArrayList) protobufList2;
                    int decodeVarint3214 = ArrayDecoders.decodeVarint32(bArr, i, registers);
                    intArrayList2.addInt(CodedInputStream.decodeZigZag32(registers.int1));
                    while (decodeVarint3214 < i2) {
                        int decodeVarint3215 = ArrayDecoders.decodeVarint32(bArr, decodeVarint3214, registers);
                        if (i3 != registers.int1) {
                            return decodeVarint3214;
                        }
                        decodeVarint3214 = ArrayDecoders.decodeVarint32(bArr, decodeVarint3215, registers);
                        intArrayList2.addInt(CodedInputStream.decodeZigZag32(registers.int1));
                    }
                    return decodeVarint3214;
                }
                return i;
            case 34:
            case 48:
                if (i5 == 2) {
                    return ArrayDecoders.decodePackedSInt64List(bArr, i, protobufList2, registers);
                }
                if (i5 == 0) {
                    LongArrayList longArrayList4 = (LongArrayList) protobufList2;
                    int decodeVarint643 = ArrayDecoders.decodeVarint64(bArr, i, registers);
                    longArrayList4.addLong(CodedInputStream.decodeZigZag64(registers.long1));
                    while (decodeVarint643 < i2) {
                        int decodeVarint3216 = ArrayDecoders.decodeVarint32(bArr, decodeVarint643, registers);
                        if (i3 != registers.int1) {
                            return decodeVarint643;
                        }
                        decodeVarint643 = ArrayDecoders.decodeVarint64(bArr, decodeVarint3216, registers);
                        longArrayList4.addLong(CodedInputStream.decodeZigZag64(registers.long1));
                    }
                    return decodeVarint643;
                }
                return i;
            case 49:
                if (i5 == 3) {
                    Schema messageFieldSchema = getMessageFieldSchema(i6);
                    int i21 = (i3 & (-8)) | 4;
                    GeneratedMessageLite newInstance = messageFieldSchema.newInstance();
                    MessageSchema messageSchema = (MessageSchema) messageFieldSchema;
                    int parseProto2Message = messageSchema.parseProto2Message(newInstance, bArr, i, i2, i21, registers);
                    MessageSchema messageSchema2 = messageSchema;
                    int i22 = i2;
                    int i23 = i21;
                    ArrayDecoders.Registers registers2 = registers;
                    registers2.object1 = newInstance;
                    messageFieldSchema.makeImmutable(newInstance);
                    registers2.object1 = newInstance;
                    protobufList2.add(newInstance);
                    while (parseProto2Message < i22) {
                        int decodeVarint3217 = ArrayDecoders.decodeVarint32(bArr, parseProto2Message, registers2);
                        if (i3 != registers2.int1) {
                            return parseProto2Message;
                        }
                        GeneratedMessageLite newInstance2 = messageFieldSchema.newInstance();
                        int i24 = i23;
                        int i25 = i22;
                        ArrayDecoders.Registers registers3 = registers2;
                        MessageSchema messageSchema3 = messageSchema2;
                        parseProto2Message = messageSchema3.parseProto2Message(newInstance2, bArr, decodeVarint3217, i25, i24, registers3);
                        messageSchema2 = messageSchema3;
                        i22 = i25;
                        registers2 = registers3;
                        registers2.object1 = newInstance2;
                        messageFieldSchema.makeImmutable(newInstance2);
                        registers2.object1 = newInstance2;
                        protobufList2.add(newInstance2);
                        i23 = i24;
                    }
                    return parseProto2Message;
                }
                return i;
            default:
                return i;
        }
    }

    public final void readGroupList(Object obj, long j, CodedInputStreamReader codedInputStreamReader, Schema schema, ExtensionRegistryLite extensionRegistryLite) {
        int readTag;
        List mutableListAt = this.listFieldSchema.mutableListAt(j, obj);
        int i = codedInputStreamReader.tag;
        if ((i & 7) != 3) {
            throw InvalidProtocolBufferException.invalidWireType();
        }
        do {
            GeneratedMessageLite newInstance = schema.newInstance();
            codedInputStreamReader.mergeGroupFieldInternal(newInstance, schema, extensionRegistryLite);
            schema.makeImmutable(newInstance);
            mutableListAt.add(newInstance);
            CodedInputStream codedInputStream = codedInputStreamReader.input;
            if (codedInputStream.isAtEnd() || codedInputStreamReader.nextTag != 0) {
                return;
            } else {
                readTag = codedInputStream.readTag();
            }
        } while (readTag == i);
        codedInputStreamReader.nextTag = readTag;
    }

    public final void readMessageList(Object obj, int i, CodedInputStreamReader codedInputStreamReader, Schema schema, ExtensionRegistryLite extensionRegistryLite) {
        int readTag;
        List mutableListAt = this.listFieldSchema.mutableListAt(i & 1048575, obj);
        int i2 = codedInputStreamReader.tag;
        if ((i2 & 7) != 2) {
            throw InvalidProtocolBufferException.invalidWireType();
        }
        do {
            GeneratedMessageLite newInstance = schema.newInstance();
            codedInputStreamReader.mergeMessageFieldInternal(newInstance, schema, extensionRegistryLite);
            schema.makeImmutable(newInstance);
            mutableListAt.add(newInstance);
            CodedInputStream codedInputStream = codedInputStreamReader.input;
            if (codedInputStream.isAtEnd() || codedInputStreamReader.nextTag != 0) {
                return;
            } else {
                readTag = codedInputStream.readTag();
            }
        } while (readTag == i2);
        codedInputStreamReader.nextTag = readTag;
    }

    public final void readString(Object obj, int i, CodedInputStreamReader codedInputStreamReader) {
        if ((536870912 & i) != 0) {
            codedInputStreamReader.requireWireType(2);
            UnsafeUtil.putObject(i & 1048575, obj, codedInputStreamReader.input.readStringRequireUtf8());
        } else if (!this.lite) {
            UnsafeUtil.putObject(i & 1048575, obj, codedInputStreamReader.readBytes());
        } else {
            codedInputStreamReader.requireWireType(2);
            UnsafeUtil.putObject(i & 1048575, obj, codedInputStreamReader.input.readString());
        }
    }

    public final void readStringList(Object obj, int i, CodedInputStreamReader codedInputStreamReader) {
        boolean z = (536870912 & i) != 0;
        ListFieldSchema listFieldSchema = this.listFieldSchema;
        if (z) {
            codedInputStreamReader.readStringListInternal(listFieldSchema.mutableListAt(i & 1048575, obj), true);
        } else {
            codedInputStreamReader.readStringListInternal(listFieldSchema.mutableListAt(i & 1048575, obj), false);
        }
    }

    public final void setFieldPresent(int i, Object obj) {
        int i2 = this.buffer[i + 2];
        long j = 1048575 & i2;
        if (j == 1048575) {
            return;
        }
        UnsafeUtil.putInt((1 << (i2 >>> 20)) | UnsafeUtil.getInt(j, obj), j, obj);
    }

    public final void setOneofPresent(int i, int i2, Object obj) {
        UnsafeUtil.putInt(i, this.buffer[i2 + 2] & 1048575, obj);
    }

    public final int slowPositionForFieldNumber(int i, int i2) {
        int[] iArr = this.buffer;
        int length = (iArr.length / 3) - 1;
        while (i2 <= length) {
            int i3 = (length + i2) >>> 1;
            int i4 = i3 * 3;
            int i5 = iArr[i4];
            if (i == i5) {
                return i4;
            }
            if (i < i5) {
                length = i3 - 1;
            } else {
                i2 = i3 + 1;
            }
        }
        return -1;
    }

    public final void storeMessageField(int i, Object obj, Object obj2) {
        UNSAFE.putObject(obj, typeAndOffsetAt(i) & 1048575, obj2);
        setFieldPresent(i, obj);
    }

    public final void storeOneofMessageField(int i, int i2, Object obj, Object obj2) {
        UNSAFE.putObject(obj, typeAndOffsetAt(i2) & 1048575, obj2);
        setOneofPresent(i, i2, obj);
    }

    public final int typeAndOffsetAt(int i) {
        return this.buffer[i + 1];
    }

    /* JADX WARN: Removed duplicated region for block: B:229:0x047c  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0031  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void writeFieldsInAscendingOrderProto2(java.lang.Object r21, com.google.protobuf.CodedOutputStreamWriter r22) {
        /*
            Method dump skipped, instructions count: 1320
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.MessageSchema.writeFieldsInAscendingOrderProto2(java.lang.Object, com.google.protobuf.CodedOutputStreamWriter):void");
    }

    public final void writeMapHelper(CodedOutputStreamWriter codedOutputStreamWriter, int i, Object obj, int i2) {
        if (obj != null) {
            Object mapFieldDefaultEntry = getMapFieldDefaultEntry(i2);
            MapFieldSchemaLite mapFieldSchemaLite = (MapFieldSchemaLite) this.mapFieldSchema;
            mapFieldSchemaLite.getClass();
            MapEntryLite.Metadata metadata = ((MapEntryLite) mapFieldDefaultEntry).metadata;
            mapFieldSchemaLite.getClass();
            CodedOutputStream codedOutputStream = codedOutputStreamWriter.output;
            codedOutputStream.getClass();
            for (Map.Entry entry : ((MapFieldLite) obj).entrySet()) {
                codedOutputStream.writeTag(i, 2);
                codedOutputStream.writeUInt32NoTag(MapEntryLite.computeSerializedSize(metadata, entry.getKey(), entry.getValue()));
                MapEntryLite.writeTo(codedOutputStream, metadata, entry.getKey(), entry.getValue());
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x006e  */
    /* JADX WARN: Removed duplicated region for block: B:283:0x0518  */
    /* JADX WARN: Removed duplicated region for block: B:308:0x054f  */
    /* JADX WARN: Removed duplicated region for block: B:538:0x0acb  */
    @Override // com.google.protobuf.Schema
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void writeTo(java.lang.Object r18, com.google.protobuf.CodedOutputStreamWriter r19) {
        /*
            Method dump skipped, instructions count: 3078
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.MessageSchema.writeTo(java.lang.Object, com.google.protobuf.CodedOutputStreamWriter):void");
    }

    @Override // com.google.protobuf.Schema
    public final void mergeFrom(Object obj, CodedInputStreamReader codedInputStreamReader, ExtensionRegistryLite extensionRegistryLite) {
        extensionRegistryLite.getClass();
        checkMutable(obj);
        mergeFromHelper(this.unknownFieldSchema, this.extensionSchema, obj, codedInputStreamReader, extensionRegistryLite);
    }

    /* JADX WARN: Failed to find 'out' block for switch in B:27:0x0097. Please report as an issue. */
    @Override // com.google.protobuf.Schema
    public final void mergeFrom(Object obj, byte[] bArr, int i, int i2, ArrayDecoders.Registers registers) {
        int i3;
        int i4;
        Object obj2;
        Unsafe unsafe;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        int i11;
        int i12;
        int i13;
        Object obj3;
        ArrayDecoders.Registers registers2;
        byte[] bArr2;
        int i14;
        Unsafe unsafe2;
        int i15;
        byte[] bArr3;
        int decodeStringRequireUtf8;
        Unsafe unsafe3;
        Unsafe unsafe4;
        byte[] bArr4;
        ArrayDecoders.Registers registers3;
        MessageSchema messageSchema = this;
        Object obj4 = obj;
        byte[] bArr5 = bArr;
        int i16 = i2;
        ArrayDecoders.Registers registers4 = registers;
        if (messageSchema.proto3) {
            checkMutable(obj4);
            Unsafe unsafe5 = UNSAFE;
            int i17 = i;
            int i18 = -1;
            int i19 = 0;
            int i20 = 1048575;
            int i21 = 0;
            while (i17 < i16) {
                int i22 = i17 + 1;
                int i23 = bArr5[i17];
                if (i23 < 0) {
                    i22 = ArrayDecoders.decodeVarint32(i23, bArr5, i22, registers4);
                    i23 = registers4.int1;
                }
                int i24 = i23 >>> 3;
                int i25 = i23 & 7;
                int i26 = messageSchema.maxFieldNumber;
                int i27 = messageSchema.minFieldNumber;
                if (i24 > i18) {
                    i4 = (i24 < i27 || i24 > i26) ? -1 : messageSchema.slowPositionForFieldNumber(i24, i19 / 3);
                    i3 = 0;
                } else if (i24 < i27 || i24 > i26) {
                    i3 = 0;
                    i4 = -1;
                } else {
                    i3 = 0;
                    i4 = messageSchema.slowPositionForFieldNumber(i24, 0);
                }
                int i28 = i4;
                if (i28 == -1) {
                    obj2 = obj4;
                    unsafe = unsafe5;
                    i5 = i23;
                    i6 = i22;
                    i7 = i24;
                    i8 = i3;
                } else {
                    int[] iArr = messageSchema.buffer;
                    int i29 = iArr[i28 + 1];
                    int type = type(i29);
                    int i30 = i23;
                    long j = i29 & 1048575;
                    if (type <= 17) {
                        int i31 = iArr[i28 + 2];
                        int i32 = 1 << (i31 >>> 20);
                        int i33 = i31 & 1048575;
                        if (i33 != i20) {
                            if (i20 != 1048575) {
                                unsafe5.putInt(obj4, i20, i21);
                            }
                            if (i33 != 1048575) {
                                i21 = unsafe5.getInt(obj4, i33);
                            }
                            i20 = i33;
                        }
                        switch (type) {
                            case 0:
                                ArrayDecoders.Registers registers5 = registers4;
                                bArr4 = bArr5;
                                registers3 = registers5;
                                i15 = i22;
                                i14 = i28;
                                unsafe3 = unsafe5;
                                if (i25 != 1) {
                                    i5 = i30;
                                    i6 = i15;
                                    unsafe = unsafe3;
                                    i7 = i24;
                                    i8 = i14;
                                    obj2 = obj4;
                                    break;
                                } else {
                                    UnsafeUtil.MEMORY_ACCESSOR.putDouble(obj, j, Double.longBitsToDouble(ArrayDecoders.decodeFixed64(i15, bArr4)));
                                    obj4 = obj;
                                    i17 = i15 + 8;
                                    i21 |= i32;
                                    byte[] bArr6 = bArr4;
                                    registers4 = registers3;
                                    bArr5 = bArr6;
                                    i16 = i2;
                                    unsafe5 = unsafe3;
                                    i18 = i24;
                                    i19 = i14;
                                    break;
                                }
                            case 1:
                                ArrayDecoders.Registers registers6 = registers4;
                                bArr4 = bArr5;
                                registers3 = registers6;
                                i15 = i22;
                                i14 = i28;
                                unsafe3 = unsafe5;
                                if (i25 != 5) {
                                    i5 = i30;
                                    i6 = i15;
                                    unsafe = unsafe3;
                                    i7 = i24;
                                    i8 = i14;
                                    obj2 = obj4;
                                    break;
                                } else {
                                    UnsafeUtil.MEMORY_ACCESSOR.putFloat(obj4, j, Float.intBitsToFloat(ArrayDecoders.decodeFixed32(i15, bArr4)));
                                    i17 = i15 + 4;
                                    i21 |= i32;
                                    byte[] bArr62 = bArr4;
                                    registers4 = registers3;
                                    bArr5 = bArr62;
                                    i16 = i2;
                                    unsafe5 = unsafe3;
                                    i18 = i24;
                                    i19 = i14;
                                    break;
                                }
                            case 2:
                            case 3:
                                ArrayDecoders.Registers registers7 = registers4;
                                byte[] bArr7 = bArr5;
                                i15 = i22;
                                i14 = i28;
                                if (i25 != 0) {
                                    unsafe3 = unsafe5;
                                    i5 = i30;
                                    i6 = i15;
                                    unsafe = unsafe3;
                                    i7 = i24;
                                    i8 = i14;
                                    obj2 = obj4;
                                    break;
                                } else {
                                    int decodeVarint64 = ArrayDecoders.decodeVarint64(bArr7, i15, registers7);
                                    Unsafe unsafe6 = unsafe5;
                                    Object obj5 = obj4;
                                    unsafe6.putLong(obj5, j, registers7.long1);
                                    obj4 = obj5;
                                    i21 |= i32;
                                    registers4 = registers7;
                                    bArr5 = bArr7;
                                    i17 = decodeVarint64;
                                    unsafe5 = unsafe6;
                                    i18 = i24;
                                    i19 = i14;
                                    i16 = i2;
                                    break;
                                }
                            case 4:
                            case 11:
                                ArrayDecoders.Registers registers8 = registers4;
                                byte[] bArr8 = bArr5;
                                i15 = i22;
                                i14 = i28;
                                if (i25 != 0) {
                                    unsafe3 = unsafe5;
                                    i5 = i30;
                                    i6 = i15;
                                    unsafe = unsafe3;
                                    i7 = i24;
                                    i8 = i14;
                                    obj2 = obj4;
                                    break;
                                } else {
                                    int decodeVarint32 = ArrayDecoders.decodeVarint32(bArr8, i15, registers8);
                                    unsafe5.putInt(obj4, j, registers8.int1);
                                    i21 |= i32;
                                    registers4 = registers8;
                                    bArr5 = bArr8;
                                    i16 = i2;
                                    i17 = decodeVarint32;
                                    i18 = i24;
                                    i19 = i14;
                                    break;
                                }
                            case 5:
                            case 14:
                                obj3 = obj4;
                                ArrayDecoders.Registers registers9 = registers4;
                                byte[] bArr9 = bArr5;
                                i14 = i28;
                                Unsafe unsafe7 = unsafe5;
                                if (i25 != 1) {
                                    i15 = i22;
                                    unsafe3 = unsafe7;
                                    obj4 = obj3;
                                    i5 = i30;
                                    i6 = i15;
                                    unsafe = unsafe3;
                                    i7 = i24;
                                    i8 = i14;
                                    obj2 = obj4;
                                    break;
                                } else {
                                    int i34 = i22;
                                    unsafe7.putLong(obj3, j, ArrayDecoders.decodeFixed64(i34, bArr9));
                                    unsafe5 = unsafe7;
                                    obj4 = obj3;
                                    i17 = i34 + 8;
                                    i21 |= i32;
                                    registers4 = registers9;
                                    bArr5 = bArr9;
                                    i16 = i2;
                                    i18 = i24;
                                    i19 = i14;
                                    break;
                                }
                            case 6:
                            case 13:
                                obj3 = obj4;
                                registers2 = registers4;
                                bArr2 = bArr5;
                                i14 = i28;
                                unsafe2 = unsafe5;
                                if (i25 != 5) {
                                    i15 = i22;
                                    unsafe3 = unsafe2;
                                    obj4 = obj3;
                                    i5 = i30;
                                    i6 = i15;
                                    unsafe = unsafe3;
                                    i7 = i24;
                                    i8 = i14;
                                    obj2 = obj4;
                                    break;
                                } else {
                                    unsafe2.putInt(obj3, j, ArrayDecoders.decodeFixed32(i22, bArr2));
                                    i17 = i22 + 4;
                                    i21 |= i32;
                                    i16 = i2;
                                    unsafe5 = unsafe2;
                                    bArr5 = bArr2;
                                    i18 = i24;
                                    i19 = i14;
                                    registers4 = registers2;
                                    obj4 = obj3;
                                    break;
                                }
                            case 7:
                                obj3 = obj4;
                                registers2 = registers4;
                                bArr2 = bArr5;
                                unsafe2 = unsafe5;
                                if (i25 != 0) {
                                    i14 = i28;
                                    i15 = i22;
                                    unsafe3 = unsafe2;
                                    obj4 = obj3;
                                    i5 = i30;
                                    i6 = i15;
                                    unsafe = unsafe3;
                                    i7 = i24;
                                    i8 = i14;
                                    obj2 = obj4;
                                    break;
                                } else {
                                    i17 = ArrayDecoders.decodeVarint64(bArr2, i22, registers2);
                                    i14 = i28;
                                    UnsafeUtil.MEMORY_ACCESSOR.putBoolean(obj3, j, registers2.long1 != 0);
                                    i21 |= i32;
                                    i16 = i2;
                                    unsafe5 = unsafe2;
                                    bArr5 = bArr2;
                                    i18 = i24;
                                    i19 = i14;
                                    registers4 = registers2;
                                    obj4 = obj3;
                                    break;
                                }
                            case 8:
                                obj3 = obj4;
                                registers2 = registers4;
                                bArr3 = bArr5;
                                unsafe2 = unsafe5;
                                if (i25 != 2) {
                                    i15 = i22;
                                    i14 = i28;
                                    unsafe3 = unsafe2;
                                    obj4 = obj3;
                                    i5 = i30;
                                    i6 = i15;
                                    unsafe = unsafe3;
                                    i7 = i24;
                                    i8 = i14;
                                    obj2 = obj4;
                                    break;
                                } else {
                                    if ((i29 & VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS) == 0) {
                                        decodeStringRequireUtf8 = ArrayDecoders.decodeString(bArr3, i22, registers2);
                                    } else {
                                        decodeStringRequireUtf8 = ArrayDecoders.decodeStringRequireUtf8(bArr3, i22, registers2);
                                    }
                                    i17 = decodeStringRequireUtf8;
                                    unsafe2.putObject(obj3, j, registers2.object1);
                                    i21 |= i32;
                                    i16 = i2;
                                    unsafe5 = unsafe2;
                                    i19 = i28;
                                    bArr5 = bArr3;
                                    i18 = i24;
                                    registers4 = registers2;
                                    obj4 = obj3;
                                    break;
                                }
                            case 9:
                                obj3 = obj4;
                                Unsafe unsafe8 = unsafe5;
                                if (i25 != 2) {
                                    unsafe2 = unsafe8;
                                    registers2 = registers4;
                                    i15 = i22;
                                    i14 = i28;
                                    unsafe3 = unsafe2;
                                    obj4 = obj3;
                                    i5 = i30;
                                    i6 = i15;
                                    unsafe = unsafe3;
                                    i7 = i24;
                                    i8 = i14;
                                    obj2 = obj4;
                                    break;
                                } else {
                                    Object mutableMessageFieldForMerge = messageSchema.mutableMessageFieldForMerge(i28, obj3);
                                    byte[] bArr10 = bArr5;
                                    unsafe2 = unsafe8;
                                    int i35 = i22;
                                    ArrayDecoders.Registers registers10 = registers4;
                                    int mergeMessageField = ArrayDecoders.mergeMessageField(mutableMessageFieldForMerge, messageSchema.getMessageFieldSchema(i28), bArr10, i35, i2, registers10);
                                    bArr3 = bArr10;
                                    registers2 = registers10;
                                    messageSchema.storeMessageField(i28, obj3, mutableMessageFieldForMerge);
                                    i21 |= i32;
                                    i16 = i2;
                                    i17 = mergeMessageField;
                                    unsafe5 = unsafe2;
                                    i19 = i28;
                                    bArr5 = bArr3;
                                    i18 = i24;
                                    registers4 = registers2;
                                    obj4 = obj3;
                                    break;
                                }
                            case 10:
                                obj3 = obj4;
                                unsafe4 = unsafe5;
                                if (i25 != 2) {
                                    i15 = i22;
                                    i14 = i28;
                                    unsafe3 = unsafe4;
                                    obj4 = obj3;
                                    i5 = i30;
                                    i6 = i15;
                                    unsafe = unsafe3;
                                    i7 = i24;
                                    i8 = i14;
                                    obj2 = obj4;
                                    break;
                                } else {
                                    i17 = ArrayDecoders.decodeBytes(bArr5, i22, registers4);
                                    unsafe4.putObject(obj3, j, registers4.object1);
                                    i21 |= i32;
                                    i16 = i2;
                                    unsafe5 = unsafe4;
                                    i19 = i28;
                                    i18 = i24;
                                    obj4 = obj3;
                                    break;
                                }
                            case 12:
                                obj3 = obj4;
                                unsafe4 = unsafe5;
                                if (i25 != 0) {
                                    i15 = i22;
                                    i14 = i28;
                                    unsafe3 = unsafe4;
                                    obj4 = obj3;
                                    i5 = i30;
                                    i6 = i15;
                                    unsafe = unsafe3;
                                    i7 = i24;
                                    i8 = i14;
                                    obj2 = obj4;
                                    break;
                                } else {
                                    i17 = ArrayDecoders.decodeVarint32(bArr5, i22, registers4);
                                    unsafe4.putInt(obj3, j, registers4.int1);
                                    i21 |= i32;
                                    i16 = i2;
                                    unsafe5 = unsafe4;
                                    i19 = i28;
                                    i18 = i24;
                                    obj4 = obj3;
                                    break;
                                }
                            case 15:
                                obj3 = obj4;
                                unsafe4 = unsafe5;
                                if (i25 != 0) {
                                    i15 = i22;
                                    i14 = i28;
                                    unsafe3 = unsafe4;
                                    obj4 = obj3;
                                    i5 = i30;
                                    i6 = i15;
                                    unsafe = unsafe3;
                                    i7 = i24;
                                    i8 = i14;
                                    obj2 = obj4;
                                    break;
                                } else {
                                    i17 = ArrayDecoders.decodeVarint32(bArr5, i22, registers4);
                                    unsafe4.putInt(obj3, j, CodedInputStream.decodeZigZag32(registers4.int1));
                                    i21 |= i32;
                                    i16 = i2;
                                    unsafe5 = unsafe4;
                                    i19 = i28;
                                    i18 = i24;
                                    obj4 = obj3;
                                    break;
                                }
                            case 16:
                                if (i25 != 0) {
                                    i15 = i22;
                                    i14 = i28;
                                    unsafe3 = unsafe5;
                                    i5 = i30;
                                    i6 = i15;
                                    unsafe = unsafe3;
                                    i7 = i24;
                                    i8 = i14;
                                    obj2 = obj4;
                                    break;
                                } else {
                                    int decodeVarint642 = ArrayDecoders.decodeVarint64(bArr5, i22, registers4);
                                    Unsafe unsafe9 = unsafe5;
                                    Object obj6 = obj4;
                                    unsafe9.putLong(obj6, j, CodedInputStream.decodeZigZag64(registers4.long1));
                                    obj3 = obj6;
                                    i21 |= i32;
                                    i16 = i2;
                                    unsafe5 = unsafe9;
                                    i19 = i28;
                                    i17 = decodeVarint642;
                                    i18 = i24;
                                    obj4 = obj3;
                                    break;
                                }
                            default:
                                i15 = i22;
                                i14 = i28;
                                unsafe3 = unsafe5;
                                i5 = i30;
                                i6 = i15;
                                unsafe = unsafe3;
                                i7 = i24;
                                i8 = i14;
                                obj2 = obj4;
                                break;
                        }
                    } else {
                        ArrayDecoders.Registers registers11 = registers4;
                        byte[] bArr11 = bArr5;
                        int i36 = i22;
                        Unsafe unsafe10 = unsafe5;
                        if (type != 27) {
                            i8 = i28;
                            if (type <= 49) {
                                i9 = i20;
                                unsafe = unsafe10;
                                i10 = i21;
                                int parseRepeatedField = messageSchema.parseRepeatedField(obj, bArr, i36, i2, i30, i24, i25, i8, i29, type, j, registers);
                                i12 = i30;
                                i11 = i24;
                                if (parseRepeatedField != i36) {
                                    messageSchema = this;
                                    obj4 = obj;
                                    registers4 = registers;
                                    i17 = parseRepeatedField;
                                    i19 = i8;
                                    i18 = i11;
                                    i20 = i9;
                                    i21 = i10;
                                    unsafe5 = unsafe;
                                    bArr5 = bArr;
                                    i16 = i2;
                                } else {
                                    i6 = parseRepeatedField;
                                    i7 = i11;
                                    i5 = i12;
                                    i20 = i9;
                                    i21 = i10;
                                    obj2 = obj;
                                }
                            } else {
                                i9 = i20;
                                unsafe = unsafe10;
                                i10 = i21;
                                i11 = i24;
                                i12 = i30;
                                i13 = i36;
                                if (type != 50) {
                                    i7 = i11;
                                    i5 = i12;
                                    int parseOneofField = parseOneofField(obj, bArr, i13, i2, i5, i7, i25, i29, type, j, i8, registers);
                                    obj2 = obj;
                                    i8 = i8;
                                    if (parseOneofField != i13) {
                                        messageSchema = this;
                                        registers4 = registers;
                                        i18 = i7;
                                        i17 = parseOneofField;
                                        i19 = i8;
                                        obj4 = obj2;
                                        i20 = i9;
                                        i21 = i10;
                                        unsafe5 = unsafe;
                                        bArr5 = bArr;
                                        i16 = i2;
                                    } else {
                                        i6 = parseOneofField;
                                        i20 = i9;
                                        i21 = i10;
                                    }
                                } else if (i25 == 2) {
                                    int parseMapField = parseMapField(obj, bArr, i13, i2, i8, j, registers);
                                    i8 = i8;
                                    if (parseMapField != i13) {
                                        messageSchema = this;
                                        obj4 = obj;
                                        bArr5 = bArr;
                                        registers4 = registers;
                                        i17 = parseMapField;
                                        i19 = i8;
                                        i18 = i11;
                                        i20 = i9;
                                        i21 = i10;
                                        unsafe5 = unsafe;
                                        i16 = i2;
                                    } else {
                                        i6 = parseMapField;
                                        i7 = i11;
                                        i5 = i12;
                                        i20 = i9;
                                        i21 = i10;
                                        obj2 = obj;
                                    }
                                } else {
                                    i6 = i13;
                                    i7 = i11;
                                    i5 = i12;
                                    i20 = i9;
                                    i21 = i10;
                                    obj2 = obj;
                                }
                            }
                        } else if (i25 == 2) {
                            Internal.ProtobufList protobufList = (Internal.ProtobufList) unsafe10.getObject(obj4, j);
                            if (!((AbstractProtobufList) protobufList).isMutable) {
                                int size = protobufList.size();
                                protobufList = protobufList.mutableCopyWithCapacity(size == 0 ? 10 : size * 2);
                                unsafe10.putObject(obj4, j, protobufList);
                            }
                            int decodeMessageList = ArrayDecoders.decodeMessageList(messageSchema.getMessageFieldSchema(i28), i30, bArr11, i36, i2, protobufList, registers11);
                            bArr5 = bArr;
                            registers4 = registers;
                            i17 = decodeMessageList;
                            i19 = i28;
                            unsafe5 = unsafe10;
                            i18 = i24;
                            obj4 = obj;
                            i16 = i2;
                        } else {
                            i12 = i30;
                            i13 = i36;
                            unsafe = unsafe10;
                            i9 = i20;
                            i10 = i21;
                            i11 = i24;
                            i8 = i28;
                            i6 = i13;
                            i7 = i11;
                            i5 = i12;
                            i20 = i9;
                            i21 = i10;
                            obj2 = obj;
                        }
                    }
                }
                int decodeUnknownField = ArrayDecoders.decodeUnknownField(i5, bArr, i6, i2, getMutableUnknownFields(obj2), registers);
                bArr5 = bArr;
                registers4 = registers;
                i18 = i7;
                i19 = i8;
                obj4 = obj2;
                unsafe5 = unsafe;
                i16 = i2;
                i17 = decodeUnknownField;
                messageSchema = this;
            }
            Object obj7 = obj4;
            Unsafe unsafe11 = unsafe5;
            int i37 = i16;
            int i38 = i20;
            int i39 = i21;
            if (i38 != 1048575) {
                unsafe11.putInt(obj7, i38, i39);
            }
            if (i17 != i37) {
                throw InvalidProtocolBufferException.parseFailure();
            }
            return;
        }
        parseProto2Message(obj4, bArr, i, i16, 0, registers);
    }
}
