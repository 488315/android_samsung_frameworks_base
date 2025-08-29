package com.google.protobuf;

import androidx.activity.result.ActivityResultRegistry$register$3$$ExternalSyntheticOutline0;
import com.google.protobuf.ArrayDecoders;
import com.google.protobuf.ByteString;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.FieldInfo;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.Internal;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.LazyField;
import com.google.protobuf.MapEntryLite;
import com.google.protobuf.SmallSortedMap;
import com.google.protobuf.UnsafeUtil;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.security.AccessController;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import sun.misc.Unsafe;

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
                int iDecodeVarint64 = ArrayDecoders.decodeVarint64(bArr, i, registers);
                registers.object1 = Boolean.valueOf(registers.long1 != 0);
                return iDecodeVarint64;
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
                int iDecodeVarint32 = ArrayDecoders.decodeVarint32(bArr, i, registers);
                registers.object1 = Integer.valueOf(registers.int1);
                return iDecodeVarint32;
            case 12:
            case 13:
                int iDecodeVarint642 = ArrayDecoders.decodeVarint64(bArr, i, registers);
                registers.object1 = Long.valueOf(registers.long1);
                return iDecodeVarint642;
            case 14:
                return ArrayDecoders.decodeMessageField(Protobuf.INSTANCE.schemaFor(cls), bArr, i, i2, registers);
            case 15:
                int iDecodeVarint322 = ArrayDecoders.decodeVarint32(bArr, i, registers);
                registers.object1 = Integer.valueOf(CodedInputStream.decodeZigZag32(registers.int1));
                return iDecodeVarint322;
            case 16:
                int iDecodeVarint643 = ArrayDecoders.decodeVarint64(bArr, i, registers);
                registers.object1 = Long.valueOf(CodedInputStream.decodeZigZag64(registers.long1));
                return iDecodeVarint643;
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
        UnknownFieldSetLite unknownFieldSetLiteNewInstance = UnknownFieldSetLite.newInstance();
        generatedMessageLite.unknownFields = unknownFieldSetLiteNewInstance;
        return unknownFieldSetLiteNewInstance;
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

    /* JADX WARN: Removed duplicated region for block: B:131:0x02ad  */
    /* JADX WARN: Removed duplicated region for block: B:133:0x02b4  */
    /* JADX WARN: Removed duplicated region for block: B:136:0x02ca  */
    /* JADX WARN: Removed duplicated region for block: B:137:0x02cd  */
    /* JADX WARN: Removed duplicated region for block: B:254:0x054a  */
    /* JADX WARN: Removed duplicated region for block: B:255:0x054d  */
    /* JADX WARN: Removed duplicated region for block: B:258:0x0553  */
    /* JADX WARN: Removed duplicated region for block: B:259:0x0556  */
    /* JADX WARN: Removed duplicated region for block: B:270:0x0586  */
    /* JADX WARN: Removed duplicated region for block: B:272:0x058a  */
    /* JADX WARN: Removed duplicated region for block: B:273:0x058f  */
    /* JADX WARN: Removed duplicated region for block: B:276:0x0595  */
    /* JADX WARN: Removed duplicated region for block: B:283:0x05b1  */
    /* JADX WARN: Removed duplicated region for block: B:295:0x05e2  */
    /* JADX WARN: Removed duplicated region for block: B:296:0x05e8  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static MessageSchema newSchema(MessageInfo messageInfo, NewInstanceSchema newInstanceSchema, ListFieldSchema listFieldSchema, UnknownFieldSchema unknownFieldSchema, ExtensionSchema extensionSchema, MapFieldSchema mapFieldSchema) {
        int i;
        int i2;
        FieldInfo[] fieldInfoArr;
        Object[] objArr;
        int i3;
        int iObjectFieldOffset;
        int i4;
        int iNumberOfTrailingZeros;
        int iObjectFieldOffset2;
        int i5;
        Class<?> type;
        Object obj;
        FieldType fieldType;
        int i6;
        int iCharAt;
        int i7;
        int i8;
        int iCharAt2;
        int i9;
        int iCharAt3;
        int i10;
        int i11;
        int i12;
        int iCharAt4;
        int i13;
        int i14;
        int i15;
        char cCharAt;
        int i16;
        char cCharAt2;
        int i17;
        char cCharAt3;
        int i18;
        char cCharAt4;
        int i19;
        char cCharAt5;
        int i20;
        char cCharAt6;
        int i21;
        char cCharAt7;
        int i22;
        char cCharAt8;
        int i23;
        int[] iArr;
        int i24;
        int i25;
        int[] iArr2;
        String str;
        int i26;
        int i27;
        int iObjectFieldOffset3;
        int iObjectFieldOffset4;
        int i28;
        int iObjectFieldOffset5;
        int i29;
        Field fieldReflectField;
        char cCharAt9;
        int i30;
        int i31;
        int i32;
        Object obj2;
        Field fieldReflectField2;
        Object obj3;
        Field fieldReflectField3;
        int i33;
        char cCharAt10;
        int i34;
        int i35;
        char cCharAt11;
        int i36;
        char cCharAt12;
        int i37;
        char cCharAt13;
        boolean z = messageInfo instanceof RawMessageInfo;
        int[] iArr3 = EMPTY_INT_ARRAY;
        int i38 = 0;
        if (!z) {
            int i39 = 2;
            int i40 = 1;
            StructuralMessageInfo structuralMessageInfo = (StructuralMessageInfo) messageInfo;
            boolean z2 = structuralMessageInfo.syntax == ProtoSyntax.PROTO3;
            FieldInfo[] fieldInfoArr2 = structuralMessageInfo.fields;
            if (fieldInfoArr2.length == 0) {
                i = 0;
                i2 = 0;
            } else {
                int i41 = fieldInfoArr2[0].fieldNumber;
                i = fieldInfoArr2[fieldInfoArr2.length - 1].fieldNumber;
                i2 = i41;
            }
            int length = fieldInfoArr2.length;
            int[] iArr4 = new int[length * 3];
            Object[] objArr2 = new Object[length * 2];
            int i42 = 0;
            int i43 = 0;
            for (FieldInfo fieldInfo : fieldInfoArr2) {
                FieldType fieldType2 = fieldInfo.type;
                if (fieldType2 == FieldType.MAP) {
                    i42++;
                } else if (fieldType2.id() >= 18 && fieldInfo.type.id() <= 49) {
                    i43++;
                }
            }
            int[] iArr5 = i42 > 0 ? new int[i42] : null;
            int[] iArr6 = i43 > 0 ? new int[i43] : null;
            int[] iArr7 = structuralMessageInfo.checkInitialized;
            if (iArr7 == null) {
                iArr7 = iArr3;
            }
            int i44 = 0;
            int i45 = 0;
            int i46 = 0;
            int i47 = 0;
            int i48 = 0;
            while (i44 < fieldInfoArr2.length) {
                FieldInfo fieldInfo2 = fieldInfoArr2[i44];
                int i49 = fieldInfo2.fieldNumber;
                int[] iArr8 = iArr3;
                OneofInfo oneofInfo = fieldInfo2.oneof;
                if (oneofInfo != null) {
                    fieldInfoArr = fieldInfoArr2;
                    int iId = fieldInfo2.type.id() + 51;
                    objArr = objArr2;
                    int iObjectFieldOffset6 = (int) UnsafeUtil.objectFieldOffset(oneofInfo.valueField);
                    iObjectFieldOffset = (int) UnsafeUtil.objectFieldOffset(oneofInfo.caseField);
                    i4 = iId;
                    i3 = iObjectFieldOffset6;
                } else {
                    fieldInfoArr = fieldInfoArr2;
                    objArr = objArr2;
                    FieldType fieldType3 = fieldInfo2.type;
                    int iObjectFieldOffset7 = (int) UnsafeUtil.objectFieldOffset(fieldInfo2.field);
                    int iId2 = fieldType3.id();
                    if (fieldType3.isList() || fieldType3.isMap()) {
                        i3 = iObjectFieldOffset7;
                        Field field = fieldInfo2.cachedSizeField;
                        if (field == null) {
                            i4 = iId2;
                            iNumberOfTrailingZeros = 0;
                            iObjectFieldOffset = 0;
                        } else {
                            iObjectFieldOffset = (int) UnsafeUtil.objectFieldOffset(field);
                            i4 = iId2;
                        }
                    } else {
                        Field field2 = fieldInfo2.presenceField;
                        if (field2 == null) {
                            i3 = iObjectFieldOffset7;
                            iObjectFieldOffset2 = 1048575;
                        } else {
                            i3 = iObjectFieldOffset7;
                            iObjectFieldOffset2 = (int) UnsafeUtil.objectFieldOffset(field2);
                        }
                        iNumberOfTrailingZeros = Integer.numberOfTrailingZeros(fieldInfo2.presenceMask);
                        i4 = iId2;
                        iObjectFieldOffset = iObjectFieldOffset2;
                    }
                    iArr4[i45] = fieldInfo2.fieldNumber;
                    iArr4[i45 + 1] = (!fieldInfo2.enforceUtf8 ? VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS : 0) | (!fieldInfo2.required ? 268435456 : 0) | (i4 << 20) | i3;
                    iArr4[i45 + 2] = (iNumberOfTrailingZeros << 20) | iObjectFieldOffset;
                    i5 = FieldInfo.AnonymousClass1.$SwitchMap$com$google$protobuf$FieldType[fieldInfo2.type.ordinal()];
                    if (i5 != i40 || i5 == i39) {
                        Field field3 = fieldInfo2.field;
                        type = field3 == null ? field3.getType() : fieldInfo2.oneofStoredType;
                    } else {
                        type = (i5 == 3 || i5 == 4) ? fieldInfo2.messageClass : null;
                    }
                    obj = fieldInfo2.mapDefaultEntry;
                    if (obj == null) {
                        int i50 = (i45 / 3) * 2;
                        objArr[i50] = obj;
                        if (type != null) {
                            objArr[i50 + 1] = type;
                        } else {
                            Internal.EnumVerifier enumVerifier = fieldInfo2.enumVerifier;
                            if (enumVerifier != null) {
                                objArr[i50 + 1] = enumVerifier;
                            }
                        }
                        i39 = 2;
                        i40 = 1;
                    } else if (type != null) {
                        i39 = 2;
                        i40 = 1;
                        objArr[((i45 / 3) * 2) + 1] = type;
                    } else {
                        i39 = 2;
                        i40 = 1;
                        Internal.EnumVerifier enumVerifier2 = fieldInfo2.enumVerifier;
                        if (enumVerifier2 != null) {
                            objArr[((i45 / 3) * 2) + 1] = enumVerifier2;
                        }
                    }
                    if (i46 < iArr7.length && iArr7[i46] == i49) {
                        iArr7[i46] = i45;
                        i46++;
                    }
                    fieldType = fieldInfo2.type;
                    if (fieldType != FieldType.MAP) {
                        iArr5[i47] = i45;
                        i47++;
                    } else if (fieldType.id() >= 18 && fieldInfo2.type.id() <= 49) {
                        iArr6[i48] = (int) UnsafeUtil.objectFieldOffset(fieldInfo2.field);
                        i48++;
                    }
                    i44++;
                    i45 += 3;
                    iArr3 = iArr8;
                    fieldInfoArr2 = fieldInfoArr;
                    objArr2 = objArr;
                }
                iNumberOfTrailingZeros = 0;
                iArr4[i45] = fieldInfo2.fieldNumber;
                iArr4[i45 + 1] = (!fieldInfo2.enforceUtf8 ? VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS : 0) | (!fieldInfo2.required ? 268435456 : 0) | (i4 << 20) | i3;
                iArr4[i45 + 2] = (iNumberOfTrailingZeros << 20) | iObjectFieldOffset;
                i5 = FieldInfo.AnonymousClass1.$SwitchMap$com$google$protobuf$FieldType[fieldInfo2.type.ordinal()];
                if (i5 != i40) {
                    Field field32 = fieldInfo2.field;
                    if (field32 == null) {
                    }
                }
                obj = fieldInfo2.mapDefaultEntry;
                if (obj == null) {
                }
                if (i46 < iArr7.length) {
                    iArr7[i46] = i45;
                    i46++;
                }
                fieldType = fieldInfo2.type;
                if (fieldType != FieldType.MAP) {
                }
                i44++;
                i45 += 3;
                iArr3 = iArr8;
                fieldInfoArr2 = fieldInfoArr;
                objArr2 = objArr;
            }
            int[] iArr9 = iArr3;
            Object[] objArr3 = objArr2;
            if (iArr5 == null) {
                iArr5 = iArr9;
            }
            int[] iArr10 = iArr6 == null ? iArr9 : iArr6;
            int[] iArr11 = new int[iArr7.length + iArr5.length + iArr10.length];
            System.arraycopy(iArr7, 0, iArr11, 0, iArr7.length);
            System.arraycopy(iArr5, 0, iArr11, iArr7.length, iArr5.length);
            System.arraycopy(iArr10, 0, iArr11, iArr7.length + iArr5.length, iArr10.length);
            return new MessageSchema(iArr4, objArr3, i2, i, structuralMessageInfo.defaultInstance, z2, true, iArr11, iArr7.length, iArr7.length + iArr5.length, newInstanceSchema, listFieldSchema, unknownFieldSchema, extensionSchema, mapFieldSchema);
        }
        RawMessageInfo rawMessageInfo = (RawMessageInfo) messageInfo;
        boolean z3 = rawMessageInfo.getSyntax() == ProtoSyntax.PROTO3;
        String str2 = rawMessageInfo.info;
        int length2 = str2.length();
        char c = 55296;
        if (str2.charAt(0) >= 55296) {
            int i51 = 1;
            while (true) {
                i6 = i51 + 1;
                if (str2.charAt(i51) < 55296) {
                    break;
                }
                i51 = i6;
            }
        } else {
            i6 = 1;
        }
        int i52 = i6 + 1;
        int iCharAt5 = str2.charAt(i6);
        if (iCharAt5 >= 55296) {
            int i53 = iCharAt5 & 8191;
            int i54 = 13;
            while (true) {
                i37 = i52 + 1;
                cCharAt13 = str2.charAt(i52);
                if (cCharAt13 < 55296) {
                    break;
                }
                i53 |= (cCharAt13 & 8191) << i54;
                i54 += 13;
                i52 = i37;
            }
            iCharAt5 = i53 | (cCharAt13 << i54);
            i52 = i37;
        }
        if (iCharAt5 == 0) {
            iCharAt2 = 0;
            iCharAt3 = 0;
            iCharAt4 = 0;
            i13 = 0;
            iCharAt = 0;
            i14 = 0;
            i10 = 0;
        } else {
            int i55 = i52 + 1;
            int iCharAt6 = str2.charAt(i52);
            if (iCharAt6 >= 55296) {
                int i56 = iCharAt6 & 8191;
                int i57 = 13;
                while (true) {
                    i22 = i55 + 1;
                    cCharAt8 = str2.charAt(i55);
                    if (cCharAt8 < 55296) {
                        break;
                    }
                    i56 |= (cCharAt8 & 8191) << i57;
                    i57 += 13;
                    i55 = i22;
                }
                iCharAt6 = i56 | (cCharAt8 << i57);
                i55 = i22;
            }
            int i58 = i55 + 1;
            int iCharAt7 = str2.charAt(i55);
            if (iCharAt7 >= 55296) {
                int i59 = iCharAt7 & 8191;
                int i60 = 13;
                while (true) {
                    i21 = i58 + 1;
                    cCharAt7 = str2.charAt(i58);
                    if (cCharAt7 < 55296) {
                        break;
                    }
                    i59 |= (cCharAt7 & 8191) << i60;
                    i60 += 13;
                    i58 = i21;
                }
                iCharAt7 = i59 | (cCharAt7 << i60);
                i58 = i21;
            }
            int i61 = i58 + 1;
            iCharAt = str2.charAt(i58);
            if (iCharAt >= 55296) {
                int i62 = iCharAt & 8191;
                int i63 = i61;
                int i64 = 13;
                while (true) {
                    i20 = i63 + 1;
                    cCharAt6 = str2.charAt(i63);
                    if (cCharAt6 < 55296) {
                        break;
                    }
                    i62 |= (cCharAt6 & 8191) << i64;
                    i64 += 13;
                    i63 = i20;
                }
                iCharAt = i62 | (cCharAt6 << i64);
                i7 = i20;
            } else {
                i7 = i61;
            }
            int i65 = i7 + 1;
            int iCharAt8 = str2.charAt(i7);
            if (iCharAt8 >= 55296) {
                int i66 = iCharAt8 & 8191;
                int i67 = i65;
                int i68 = 13;
                while (true) {
                    i19 = i67 + 1;
                    cCharAt5 = str2.charAt(i67);
                    if (cCharAt5 < 55296) {
                        break;
                    }
                    i66 |= (cCharAt5 & 8191) << i68;
                    i68 += 13;
                    i67 = i19;
                }
                iCharAt8 = i66 | (cCharAt5 << i68);
                i8 = i19;
            } else {
                i8 = i65;
            }
            int i69 = i8 + 1;
            iCharAt2 = str2.charAt(i8);
            if (iCharAt2 >= 55296) {
                int i70 = iCharAt2 & 8191;
                int i71 = i69;
                int i72 = 13;
                while (true) {
                    i18 = i71 + 1;
                    cCharAt4 = str2.charAt(i71);
                    if (cCharAt4 < 55296) {
                        break;
                    }
                    i70 |= (cCharAt4 & 8191) << i72;
                    i72 += 13;
                    i71 = i18;
                }
                iCharAt2 = i70 | (cCharAt4 << i72);
                i9 = i18;
            } else {
                i9 = i69;
            }
            int i73 = i9 + 1;
            iCharAt3 = str2.charAt(i9);
            if (iCharAt3 >= 55296) {
                int i74 = iCharAt3 & 8191;
                i10 = 0;
                int i75 = i73;
                int i76 = 13;
                while (true) {
                    i17 = i75 + 1;
                    cCharAt3 = str2.charAt(i75);
                    if (cCharAt3 < 55296) {
                        break;
                    }
                    i74 |= (cCharAt3 & 8191) << i76;
                    i76 += 13;
                    i75 = i17;
                }
                iCharAt3 = i74 | (cCharAt3 << i76);
                i11 = i17;
            } else {
                i10 = 0;
                i11 = i73;
            }
            int i77 = i11 + 1;
            int iCharAt9 = str2.charAt(i11);
            if (iCharAt9 >= 55296) {
                int i78 = iCharAt9 & 8191;
                int i79 = i77;
                int i80 = 13;
                while (true) {
                    i16 = i79 + 1;
                    cCharAt2 = str2.charAt(i79);
                    if (cCharAt2 < 55296) {
                        break;
                    }
                    i78 |= (cCharAt2 & 8191) << i80;
                    i80 += 13;
                    i79 = i16;
                }
                iCharAt9 = i78 | (cCharAt2 << i80);
                i12 = i16;
            } else {
                i12 = i77;
            }
            int i81 = i12 + 1;
            iCharAt4 = str2.charAt(i12);
            if (iCharAt4 >= 55296) {
                int i82 = iCharAt4 & 8191;
                int i83 = i81;
                int i84 = 13;
                while (true) {
                    i15 = i83 + 1;
                    cCharAt = str2.charAt(i83);
                    if (cCharAt < 55296) {
                        break;
                    }
                    i82 |= (cCharAt & 8191) << i84;
                    i84 += 13;
                    i83 = i15;
                }
                iCharAt4 = i82 | (cCharAt << i84);
                i81 = i15;
            }
            i13 = (iCharAt6 * 2) + iCharAt7;
            iArr3 = new int[iCharAt4 + iCharAt3 + iCharAt9];
            i38 = iCharAt6;
            i52 = i81;
            i14 = iCharAt8;
        }
        Unsafe unsafe = UNSAFE;
        Class<?> cls = rawMessageInfo.defaultInstance.getClass();
        int[] iArr12 = new int[iCharAt2 * 3];
        Object[] objArr4 = new Object[iCharAt2 * 2];
        int i85 = iCharAt3 + iCharAt4;
        int i86 = i85;
        int i87 = iCharAt4;
        int i88 = i10;
        int i89 = i88;
        while (i52 < length2) {
            int i90 = i52 + 1;
            int iCharAt10 = str2.charAt(i52);
            if (iCharAt10 >= c) {
                int i91 = iCharAt10 & 8191;
                int i92 = i90;
                int i93 = 13;
                while (true) {
                    i36 = i92 + 1;
                    cCharAt12 = str2.charAt(i92);
                    if (cCharAt12 < c) {
                        break;
                    }
                    i91 |= (cCharAt12 & 8191) << i93;
                    i93 += 13;
                    i92 = i36;
                }
                iCharAt10 = i91 | (cCharAt12 << i93);
                i23 = i36;
            } else {
                i23 = i90;
            }
            int i94 = i23 + 1;
            int iCharAt11 = str2.charAt(i23);
            if (iCharAt11 >= c) {
                int i95 = iCharAt11 & 8191;
                int i96 = i94;
                int i97 = 13;
                while (true) {
                    i35 = i96 + 1;
                    cCharAt11 = str2.charAt(i96);
                    iArr = iArr3;
                    if (cCharAt11 < 55296) {
                        break;
                    }
                    i95 |= (cCharAt11 & 8191) << i97;
                    i97 += 13;
                    i96 = i35;
                    iArr3 = iArr;
                }
                iCharAt11 = i95 | (cCharAt11 << i97);
                i24 = i35;
            } else {
                iArr = iArr3;
                i24 = i94;
            }
            int i98 = iCharAt11 & 255;
            Object[] objArr5 = objArr4;
            if ((iCharAt11 & 1024) != 0) {
                iArr[i88] = i89;
                i88++;
            }
            Object[] objArr6 = rawMessageInfo.objects;
            if (i98 >= 51) {
                int i99 = i24 + 1;
                int iCharAt12 = str2.charAt(i24);
                if (iCharAt12 >= 55296) {
                    int i100 = iCharAt12 & 8191;
                    int i101 = i99;
                    int i102 = 13;
                    while (true) {
                        i33 = i101 + 1;
                        cCharAt10 = str2.charAt(i101);
                        i34 = i100;
                        if (cCharAt10 < 55296) {
                            break;
                        }
                        i100 = i34 | ((cCharAt10 & 8191) << i102);
                        i102 += 13;
                        i101 = i33;
                    }
                    iCharAt12 = i34 | (cCharAt10 << i102);
                    i31 = i33;
                } else {
                    i31 = i99;
                }
                int i103 = iCharAt12;
                int i104 = i98 - 51;
                int i105 = i31;
                if (i104 == 9 || i104 == 17) {
                    i32 = i13 + 1;
                    objArr5[((i89 / 3) * 2) + 1] = objArr6[i13];
                } else {
                    if (i104 == 12 && !z3) {
                        i32 = i13 + 1;
                        objArr5[((i89 / 3) * 2) + 1] = objArr6[i13];
                    }
                    int i106 = i103 * 2;
                    obj2 = objArr6[i106];
                    if (obj2 instanceof Field) {
                        fieldReflectField2 = reflectField(cls, (String) obj2);
                        objArr6[i106] = fieldReflectField2;
                    } else {
                        fieldReflectField2 = (Field) obj2;
                    }
                    i25 = i85;
                    iArr2 = iArr12;
                    iObjectFieldOffset5 = (int) unsafe.objectFieldOffset(fieldReflectField2);
                    int i107 = i106 + 1;
                    obj3 = objArr6[i107];
                    if (obj3 instanceof Field) {
                        fieldReflectField3 = reflectField(cls, (String) obj3);
                        objArr6[i107] = fieldReflectField3;
                    } else {
                        fieldReflectField3 = (Field) obj3;
                    }
                    iObjectFieldOffset4 = (int) unsafe.objectFieldOffset(fieldReflectField3);
                    i29 = i13;
                    i26 = i105;
                    str = str2;
                    i28 = i10;
                }
                i13 = i32;
                int i1062 = i103 * 2;
                obj2 = objArr6[i1062];
                if (obj2 instanceof Field) {
                }
                i25 = i85;
                iArr2 = iArr12;
                iObjectFieldOffset5 = (int) unsafe.objectFieldOffset(fieldReflectField2);
                int i1072 = i1062 + 1;
                obj3 = objArr6[i1072];
                if (obj3 instanceof Field) {
                }
                iObjectFieldOffset4 = (int) unsafe.objectFieldOffset(fieldReflectField3);
                i29 = i13;
                i26 = i105;
                str = str2;
                i28 = i10;
            } else {
                i25 = i85;
                iArr2 = iArr12;
                int i108 = i13 + 1;
                Field fieldReflectField4 = reflectField(cls, (String) objArr6[i13]);
                if (i98 == 9 || i98 == 17) {
                    objArr5[((i89 / 3) * 2) + 1] = fieldReflectField4.getType();
                } else {
                    if (i98 == 27 || i98 == 49) {
                        i30 = i13 + 2;
                        objArr5[((i89 / 3) * 2) + 1] = objArr6[i108];
                    } else if (i98 == 12 || i98 == 30 || i98 == 44) {
                        if (!z3) {
                            i30 = i13 + 2;
                            objArr5[((i89 / 3) * 2) + 1] = objArr6[i108];
                        }
                    } else if (i98 == 50) {
                        int i109 = i87 + 1;
                        iArr[i87] = i89;
                        int i110 = (i89 / 3) * 2;
                        int i111 = i13 + 2;
                        objArr5[i110] = objArr6[i108];
                        if ((iCharAt11 & 2048) != 0) {
                            i108 = i13 + 3;
                            objArr5[i110 + 1] = objArr6[i111];
                            i87 = i109;
                        } else {
                            i87 = i109;
                            i108 = i111;
                        }
                    }
                    i108 = i30;
                }
                int iObjectFieldOffset8 = (int) unsafe.objectFieldOffset(fieldReflectField4);
                if ((iCharAt11 & 4096) != 4096 || i98 > 17) {
                    str = str2;
                    i26 = i24;
                    i27 = i10;
                    iObjectFieldOffset3 = 1048575;
                } else {
                    int i112 = i24 + 1;
                    int iCharAt13 = str2.charAt(i24);
                    if (iCharAt13 >= 55296) {
                        int i113 = iCharAt13 & 8191;
                        int i114 = 13;
                        while (true) {
                            i26 = i112 + 1;
                            cCharAt9 = str2.charAt(i112);
                            if (cCharAt9 < 55296) {
                                break;
                            }
                            i113 |= (cCharAt9 & 8191) << i114;
                            i114 += 13;
                            i112 = i26;
                        }
                        iCharAt13 = i113 | (cCharAt9 << i114);
                    } else {
                        i26 = i112;
                    }
                    int i115 = (iCharAt13 / 32) + (i38 * 2);
                    Object obj4 = objArr6[i115];
                    if (obj4 instanceof Field) {
                        fieldReflectField = (Field) obj4;
                    } else {
                        fieldReflectField = reflectField(cls, (String) obj4);
                        objArr6[i115] = fieldReflectField;
                    }
                    str = str2;
                    int i116 = iCharAt13;
                    iObjectFieldOffset3 = (int) unsafe.objectFieldOffset(fieldReflectField);
                    i27 = i116 % 32;
                }
                if (i98 < 18 || i98 > 49) {
                    int i117 = i108;
                    iObjectFieldOffset4 = iObjectFieldOffset3;
                    i28 = i27;
                    iObjectFieldOffset5 = iObjectFieldOffset8;
                    i29 = i117;
                } else {
                    int i118 = i86 + 1;
                    iArr[i86] = iObjectFieldOffset8;
                    int i119 = i108;
                    iObjectFieldOffset4 = iObjectFieldOffset3;
                    i28 = i27;
                    iObjectFieldOffset5 = iObjectFieldOffset8;
                    i29 = i119;
                    i86 = i118;
                }
            }
            int i120 = i89 + 1;
            iArr2[i89] = iCharAt10;
            int i121 = i89 + 2;
            int i122 = i28;
            iArr2[i120] = ((iCharAt11 & 512) != 0 ? VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS : i10) | ((iCharAt11 & 256) != 0 ? 268435456 : i10) | (i98 << 20) | iObjectFieldOffset5;
            i89 += 3;
            iArr2[i121] = (i122 << 20) | iObjectFieldOffset4;
            str2 = str;
            objArr4 = objArr5;
            iArr3 = iArr;
            i52 = i26;
            iArr12 = iArr2;
            c = 55296;
            i13 = i29;
            i85 = i25;
        }
        return new MessageSchema(iArr12, objArr4, iCharAt, i14, rawMessageInfo.defaultInstance, z3, false, iArr3, iCharAt4, i85, newInstanceSchema, listFieldSchema, unknownFieldSchema, extensionSchema, mapFieldSchema);
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
            StringBuilder sbM = ActivityResultRegistry$register$3$$ExternalSyntheticOutline0.m("Field ", str, " for ");
            sbM.append(cls.getName());
            sbM.append(" not found. Known fields are ");
            sbM.append(Arrays.toString(declaredFields));
            throw new RuntimeException(sbM.toString());
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

    /* JADX WARN: Removed duplicated region for block: B:13:0x003b  */
    @Override // com.google.protobuf.Schema
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean equals(GeneratedMessageLite generatedMessageLite, GeneratedMessageLite generatedMessageLite2) {
        int[] iArr = this.buffer;
        int length = iArr.length;
        int i = 0;
        while (true) {
            boolean zSafeEquals = true;
            if (i < length) {
                int iTypeAndOffsetAt = typeAndOffsetAt(i);
                long j = iTypeAndOffsetAt & 1048575;
                switch (type(iTypeAndOffsetAt)) {
                    case 0:
                        if (arePresentForEquals(generatedMessageLite, generatedMessageLite2, i)) {
                            UnsafeUtil.MemoryAccessor memoryAccessor = UnsafeUtil.MEMORY_ACCESSOR;
                            if (Double.doubleToLongBits(memoryAccessor.getDouble(j, generatedMessageLite)) != Double.doubleToLongBits(memoryAccessor.getDouble(j, generatedMessageLite2))) {
                                zSafeEquals = false;
                                break;
                            }
                        }
                        break;
                    case 1:
                        if (arePresentForEquals(generatedMessageLite, generatedMessageLite2, i)) {
                            UnsafeUtil.MemoryAccessor memoryAccessor2 = UnsafeUtil.MEMORY_ACCESSOR;
                            if (Float.floatToIntBits(memoryAccessor2.getFloat(j, generatedMessageLite)) != Float.floatToIntBits(memoryAccessor2.getFloat(j, generatedMessageLite2))) {
                            }
                        }
                        break;
                    case 2:
                        if (!arePresentForEquals(generatedMessageLite, generatedMessageLite2, i) || UnsafeUtil.getLong(j, generatedMessageLite) != UnsafeUtil.getLong(j, generatedMessageLite2)) {
                        }
                        break;
                    case 3:
                        if (!arePresentForEquals(generatedMessageLite, generatedMessageLite2, i) || UnsafeUtil.getLong(j, generatedMessageLite) != UnsafeUtil.getLong(j, generatedMessageLite2)) {
                        }
                        break;
                    case 4:
                        if (!arePresentForEquals(generatedMessageLite, generatedMessageLite2, i) || UnsafeUtil.getInt(j, generatedMessageLite) != UnsafeUtil.getInt(j, generatedMessageLite2)) {
                        }
                        break;
                    case 5:
                        if (!arePresentForEquals(generatedMessageLite, generatedMessageLite2, i) || UnsafeUtil.getLong(j, generatedMessageLite) != UnsafeUtil.getLong(j, generatedMessageLite2)) {
                        }
                        break;
                    case 6:
                        if (!arePresentForEquals(generatedMessageLite, generatedMessageLite2, i) || UnsafeUtil.getInt(j, generatedMessageLite) != UnsafeUtil.getInt(j, generatedMessageLite2)) {
                        }
                        break;
                    case 7:
                        if (arePresentForEquals(generatedMessageLite, generatedMessageLite2, i)) {
                            UnsafeUtil.MemoryAccessor memoryAccessor3 = UnsafeUtil.MEMORY_ACCESSOR;
                            if (memoryAccessor3.getBoolean(j, generatedMessageLite) != memoryAccessor3.getBoolean(j, generatedMessageLite2)) {
                            }
                        }
                        break;
                    case 8:
                        if (!arePresentForEquals(generatedMessageLite, generatedMessageLite2, i) || !SchemaUtil.safeEquals(UnsafeUtil.getObject(j, generatedMessageLite), UnsafeUtil.getObject(j, generatedMessageLite2))) {
                        }
                        break;
                    case 9:
                        if (!arePresentForEquals(generatedMessageLite, generatedMessageLite2, i) || !SchemaUtil.safeEquals(UnsafeUtil.getObject(j, generatedMessageLite), UnsafeUtil.getObject(j, generatedMessageLite2))) {
                        }
                        break;
                    case 10:
                        if (!arePresentForEquals(generatedMessageLite, generatedMessageLite2, i) || !SchemaUtil.safeEquals(UnsafeUtil.getObject(j, generatedMessageLite), UnsafeUtil.getObject(j, generatedMessageLite2))) {
                        }
                        break;
                    case 11:
                        if (!arePresentForEquals(generatedMessageLite, generatedMessageLite2, i) || UnsafeUtil.getInt(j, generatedMessageLite) != UnsafeUtil.getInt(j, generatedMessageLite2)) {
                        }
                        break;
                    case 12:
                        if (!arePresentForEquals(generatedMessageLite, generatedMessageLite2, i) || UnsafeUtil.getInt(j, generatedMessageLite) != UnsafeUtil.getInt(j, generatedMessageLite2)) {
                        }
                        break;
                    case 13:
                        if (!arePresentForEquals(generatedMessageLite, generatedMessageLite2, i) || UnsafeUtil.getInt(j, generatedMessageLite) != UnsafeUtil.getInt(j, generatedMessageLite2)) {
                        }
                        break;
                    case 14:
                        if (!arePresentForEquals(generatedMessageLite, generatedMessageLite2, i) || UnsafeUtil.getLong(j, generatedMessageLite) != UnsafeUtil.getLong(j, generatedMessageLite2)) {
                        }
                        break;
                    case 15:
                        if (!arePresentForEquals(generatedMessageLite, generatedMessageLite2, i) || UnsafeUtil.getInt(j, generatedMessageLite) != UnsafeUtil.getInt(j, generatedMessageLite2)) {
                        }
                        break;
                    case 16:
                        if (!arePresentForEquals(generatedMessageLite, generatedMessageLite2, i) || UnsafeUtil.getLong(j, generatedMessageLite) != UnsafeUtil.getLong(j, generatedMessageLite2)) {
                        }
                        break;
                    case 17:
                        if (!arePresentForEquals(generatedMessageLite, generatedMessageLite2, i) || !SchemaUtil.safeEquals(UnsafeUtil.getObject(j, generatedMessageLite), UnsafeUtil.getObject(j, generatedMessageLite2))) {
                        }
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
                        zSafeEquals = SchemaUtil.safeEquals(UnsafeUtil.getObject(j, generatedMessageLite), UnsafeUtil.getObject(j, generatedMessageLite2));
                        break;
                    case 50:
                        zSafeEquals = SchemaUtil.safeEquals(UnsafeUtil.getObject(j, generatedMessageLite), UnsafeUtil.getObject(j, generatedMessageLite2));
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
                    case 60:
                    case 61:
                    case 62:
                    case 63:
                    case 64:
                    case 65:
                    case 66:
                    case 67:
                    case 68:
                        long j2 = iArr[i + 2] & 1048575;
                        if (UnsafeUtil.getInt(j2, generatedMessageLite) != UnsafeUtil.getInt(j2, generatedMessageLite2) || !SchemaUtil.safeEquals(UnsafeUtil.getObject(j, generatedMessageLite), UnsafeUtil.getObject(j, generatedMessageLite2))) {
                        }
                        break;
                }
                if (zSafeEquals) {
                    i += 3;
                }
            } else {
                UnknownFieldSchema unknownFieldSchema = this.unknownFieldSchema;
                if (unknownFieldSchema.getFromMessage(generatedMessageLite).equals(unknownFieldSchema.getFromMessage(generatedMessageLite2))) {
                    if (!this.hasExtensions) {
                        return true;
                    }
                    ExtensionSchema extensionSchema = this.extensionSchema;
                    return extensionSchema.getExtensions(generatedMessageLite).equals(extensionSchema.getExtensions(generatedMessageLite2));
                }
            }
        }
        return false;
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
        Schema schemaSchemaFor = Protobuf.INSTANCE.schemaFor((Class) objArr[i2 + 1]);
        objArr[i2] = schemaSchemaFor;
        return schemaSchemaFor;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:260:0x060c A[PHI: r5
      0x060c: PHI (r5v3 int) = 
      (r5v2 int)
      (r5v2 int)
      (r5v2 int)
      (r5v2 int)
      (r5v2 int)
      (r5v2 int)
      (r5v2 int)
      (r5v2 int)
      (r5v6 int)
      (r5v2 int)
      (r5v2 int)
      (r5v2 int)
      (r5v2 int)
      (r5v2 int)
      (r5v2 int)
      (r5v2 int)
      (r5v2 int)
      (r5v2 int)
      (r5v2 int)
      (r5v2 int)
      (r5v9 int)
      (r5v2 int)
      (r5v10 int)
      (r5v2 int)
      (r5v11 int)
      (r5v2 int)
      (r5v12 int)
      (r5v2 int)
      (r5v13 int)
      (r5v2 int)
      (r5v14 int)
      (r5v2 int)
      (r5v15 int)
      (r5v2 int)
      (r5v16 int)
      (r5v2 int)
      (r5v17 int)
      (r5v2 int)
      (r5v18 int)
      (r5v2 int)
      (r5v19 int)
      (r5v2 int)
      (r5v20 int)
      (r5v2 int)
      (r5v21 int)
      (r5v2 int)
      (r5v22 int)
      (r5v23 int)
      (r5v2 int)
      (r5v2 int)
      (r5v2 int)
      (r5v2 int)
      (r5v2 int)
      (r5v2 int)
      (r5v2 int)
      (r5v2 int)
      (r5v26 int)
      (r5v27 int)
      (r5v2 int)
      (r5v2 int)
      (r5v2 int)
      (r5v2 int)
      (r5v2 int)
      (r5v2 int)
      (r5v2 int)
      (r5v2 int)
      (r5v33 int)
      (r5v2 int)
      (r5v34 int)
      (r5v2 int)
     binds: [B:254:0x05f3, B:471:0x0b0a, B:468:0x0af8, B:465:0x0ae6, B:462:0x0ad4, B:459:0x0aca, B:456:0x0ac0, B:453:0x0ab5, B:454:0x0ab7, B:447:0x0a94, B:444:0x0a82, B:441:0x0a72, B:438:0x0a64, B:435:0x0a52, B:432:0x0a47, B:429:0x0a3b, B:426:0x0a22, B:423:0x0a09, B:420:0x09f5, B:395:0x0901, B:398:0x0909, B:390:0x08e5, B:393:0x08ed, B:385:0x08c9, B:388:0x08d1, B:380:0x08ad, B:383:0x08b5, B:375:0x0891, B:378:0x0899, B:370:0x0875, B:373:0x087d, B:365:0x0859, B:368:0x0861, B:360:0x083d, B:363:0x0845, B:355:0x081f, B:358:0x0827, B:350:0x0803, B:353:0x080b, B:345:0x07e7, B:348:0x07ef, B:340:0x07cb, B:343:0x07d3, B:335:0x07af, B:338:0x07b7, B:330:0x0793, B:333:0x079b, B:328:0x0786, B:319:0x0742, B:316:0x0735, B:313:0x0721, B:310:0x070d, B:307:0x06f9, B:304:0x06ed, B:301:0x06e1, B:298:0x06d4, B:299:0x06d6, B:295:0x06c0, B:291:0x06b0, B:288:0x069c, B:285:0x068a, B:282:0x067b, B:279:0x0668, B:276:0x065c, B:272:0x064c, B:268:0x0631, B:266:0x062b, B:263:0x0615, B:259:0x060b, B:257:0x05fb] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // com.google.protobuf.Schema
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int getSerializedSize(AbstractMessageLite abstractMessageLite) {
        MapFieldSchema mapFieldSchema;
        boolean z;
        int i;
        int i2;
        char c;
        int iComputeTagSize;
        int iComputeUInt64SizeNoTag;
        int iComputeTagSize2;
        int iComputeInt32SizeNoTag;
        int iComputeFixed64Size;
        int iComputeBytesSize;
        char c2;
        int iComputeTagSize3;
        int iComputeGroupSize;
        int iComputeTagSize4;
        int iComputeUInt64SizeNoTag2;
        int iComputeTagSize5;
        int iComputeInt32SizeNoTag2;
        int iComputeFixed64Size2;
        int iComputeBytesSize2;
        int iComputeGroupSize2;
        int iComputeTagSize6;
        int iComputeUInt64SizeNoTag3;
        int i3 = 4;
        boolean z2 = this.proto3;
        UnknownFieldSchema unknownFieldSchema = this.unknownFieldSchema;
        MapFieldSchema mapFieldSchema2 = this.mapFieldSchema;
        boolean z3 = this.useCachedSizeField;
        int[] iArr = this.buffer;
        if (z2) {
            Unsafe unsafe = UNSAFE;
            int i4 = 0;
            int iM = 0;
            while (i4 < iArr.length) {
                int iTypeAndOffsetAt = typeAndOffsetAt(i4);
                int iType = type(iTypeAndOffsetAt);
                int i5 = iArr[i4];
                long j = iTypeAndOffsetAt & 1048575;
                int i6 = (iType < FieldType.DOUBLE_LIST_PACKED.id() || iType > FieldType.SINT64_LIST_PACKED.id()) ? 0 : iArr[i4 + 2] & 1048575;
                switch (iType) {
                    case 0:
                        if (isFieldPresent(i4, abstractMessageLite)) {
                            iM = MessageSchema$$ExternalSyntheticOutline0.m(i5, 8, iM);
                            break;
                        } else {
                            break;
                        }
                    case 1:
                        if (isFieldPresent(i4, abstractMessageLite)) {
                            iM = MessageSchema$$ExternalSyntheticOutline0.m(i5, 4, iM);
                            break;
                        } else {
                            break;
                        }
                    case 2:
                        if (isFieldPresent(i4, abstractMessageLite)) {
                            long j2 = UnsafeUtil.getLong(j, abstractMessageLite);
                            iComputeTagSize4 = CodedOutputStream.computeTagSize(i5);
                            iComputeUInt64SizeNoTag2 = CodedOutputStream.computeUInt64SizeNoTag(j2);
                            iComputeFixed64Size2 = iComputeUInt64SizeNoTag2 + iComputeTagSize4;
                            iM += iComputeFixed64Size2;
                            break;
                        } else {
                            break;
                        }
                    case 3:
                        if (isFieldPresent(i4, abstractMessageLite)) {
                            long j3 = UnsafeUtil.getLong(j, abstractMessageLite);
                            iComputeTagSize4 = CodedOutputStream.computeTagSize(i5);
                            iComputeUInt64SizeNoTag2 = CodedOutputStream.computeUInt64SizeNoTag(j3);
                            iComputeFixed64Size2 = iComputeUInt64SizeNoTag2 + iComputeTagSize4;
                            iM += iComputeFixed64Size2;
                            break;
                        } else {
                            break;
                        }
                    case 4:
                        if (isFieldPresent(i4, abstractMessageLite)) {
                            int i7 = UnsafeUtil.getInt(j, abstractMessageLite);
                            iComputeTagSize5 = CodedOutputStream.computeTagSize(i5);
                            iComputeInt32SizeNoTag2 = CodedOutputStream.computeInt32SizeNoTag(i7);
                            iComputeFixed64Size2 = iComputeInt32SizeNoTag2 + iComputeTagSize5;
                            iM += iComputeFixed64Size2;
                            break;
                        } else {
                            break;
                        }
                    case 5:
                        if (isFieldPresent(i4, abstractMessageLite)) {
                            iComputeFixed64Size2 = CodedOutputStream.computeFixed64Size(i5);
                            iM += iComputeFixed64Size2;
                            break;
                        } else {
                            break;
                        }
                    case 6:
                        if (isFieldPresent(i4, abstractMessageLite)) {
                            iComputeFixed64Size2 = CodedOutputStream.computeFixed32Size(i5);
                            iM += iComputeFixed64Size2;
                            break;
                        } else {
                            break;
                        }
                    case 7:
                        if (isFieldPresent(i4, abstractMessageLite)) {
                            iM = MessageSchema$$ExternalSyntheticOutline0.m(i5, 1, iM);
                            break;
                        } else {
                            break;
                        }
                    case 8:
                        if (isFieldPresent(i4, abstractMessageLite)) {
                            Object object = UnsafeUtil.getObject(j, abstractMessageLite);
                            iComputeBytesSize2 = object instanceof ByteString ? CodedOutputStream.computeBytesSize(i5, (ByteString) object) : CodedOutputStream.computeStringSizeNoTag((String) object) + CodedOutputStream.computeTagSize(i5);
                            iM = iComputeBytesSize2 + iM;
                            break;
                        } else {
                            break;
                        }
                    case 9:
                        if (isFieldPresent(i4, abstractMessageLite)) {
                            iComputeFixed64Size2 = SchemaUtil.computeSizeMessage(i5, UnsafeUtil.getObject(j, abstractMessageLite), getMessageFieldSchema(i4));
                            iM += iComputeFixed64Size2;
                            break;
                        } else {
                            break;
                        }
                    case 10:
                        if (isFieldPresent(i4, abstractMessageLite)) {
                            iComputeFixed64Size2 = CodedOutputStream.computeBytesSize(i5, (ByteString) UnsafeUtil.getObject(j, abstractMessageLite));
                            iM += iComputeFixed64Size2;
                            break;
                        } else {
                            break;
                        }
                    case 11:
                        if (isFieldPresent(i4, abstractMessageLite)) {
                            iComputeFixed64Size2 = CodedOutputStream.computeUInt32Size(i5, UnsafeUtil.getInt(j, abstractMessageLite));
                            iM += iComputeFixed64Size2;
                            break;
                        } else {
                            break;
                        }
                    case 12:
                        if (isFieldPresent(i4, abstractMessageLite)) {
                            int i8 = UnsafeUtil.getInt(j, abstractMessageLite);
                            iComputeTagSize5 = CodedOutputStream.computeTagSize(i5);
                            iComputeInt32SizeNoTag2 = CodedOutputStream.computeInt32SizeNoTag(i8);
                            iComputeFixed64Size2 = iComputeInt32SizeNoTag2 + iComputeTagSize5;
                            iM += iComputeFixed64Size2;
                            break;
                        } else {
                            break;
                        }
                    case 13:
                        if (isFieldPresent(i4, abstractMessageLite)) {
                            iM = MessageSchema$$ExternalSyntheticOutline0.m(i5, 4, iM);
                            break;
                        } else {
                            break;
                        }
                    case 14:
                        if (isFieldPresent(i4, abstractMessageLite)) {
                            iM = MessageSchema$$ExternalSyntheticOutline0.m(i5, 8, iM);
                            break;
                        } else {
                            break;
                        }
                    case 15:
                        if (isFieldPresent(i4, abstractMessageLite)) {
                            int i9 = UnsafeUtil.getInt(j, abstractMessageLite);
                            iComputeTagSize5 = CodedOutputStream.computeTagSize(i5);
                            iComputeInt32SizeNoTag2 = CodedOutputStream.computeUInt32SizeNoTag((i9 >> 31) ^ (i9 << 1));
                            iComputeFixed64Size2 = iComputeInt32SizeNoTag2 + iComputeTagSize5;
                            iM += iComputeFixed64Size2;
                            break;
                        } else {
                            break;
                        }
                    case 16:
                        if (isFieldPresent(i4, abstractMessageLite)) {
                            long j4 = UnsafeUtil.getLong(j, abstractMessageLite);
                            iComputeTagSize4 = CodedOutputStream.computeTagSize(i5);
                            iComputeUInt64SizeNoTag2 = CodedOutputStream.computeUInt64SizeNoTag((j4 >> 63) ^ (j4 << 1));
                            iComputeFixed64Size2 = iComputeUInt64SizeNoTag2 + iComputeTagSize4;
                            iM += iComputeFixed64Size2;
                            break;
                        } else {
                            break;
                        }
                    case 17:
                        if (isFieldPresent(i4, abstractMessageLite)) {
                            iComputeFixed64Size2 = CodedOutputStream.computeGroupSize(i5, (MessageLite) UnsafeUtil.getObject(j, abstractMessageLite), getMessageFieldSchema(i4));
                            iM += iComputeFixed64Size2;
                            break;
                        } else {
                            break;
                        }
                    case 18:
                        iComputeFixed64Size2 = SchemaUtil.computeSizeFixed64List(i5, (List) UnsafeUtil.getObject(j, abstractMessageLite));
                        iM += iComputeFixed64Size2;
                        break;
                    case 19:
                        iComputeFixed64Size2 = SchemaUtil.computeSizeFixed32List(i5, (List) UnsafeUtil.getObject(j, abstractMessageLite));
                        iM += iComputeFixed64Size2;
                        break;
                    case 20:
                        iComputeFixed64Size2 = SchemaUtil.computeSizeInt64List(i5, (List) UnsafeUtil.getObject(j, abstractMessageLite));
                        iM += iComputeFixed64Size2;
                        break;
                    case 21:
                        iComputeFixed64Size2 = SchemaUtil.computeSizeUInt64List(i5, (List) UnsafeUtil.getObject(j, abstractMessageLite));
                        iM += iComputeFixed64Size2;
                        break;
                    case 22:
                        iComputeFixed64Size2 = SchemaUtil.computeSizeInt32List(i5, (List) UnsafeUtil.getObject(j, abstractMessageLite));
                        iM += iComputeFixed64Size2;
                        break;
                    case 23:
                        iComputeFixed64Size2 = SchemaUtil.computeSizeFixed64List(i5, (List) UnsafeUtil.getObject(j, abstractMessageLite));
                        iM += iComputeFixed64Size2;
                        break;
                    case 24:
                        iComputeFixed64Size2 = SchemaUtil.computeSizeFixed32List(i5, (List) UnsafeUtil.getObject(j, abstractMessageLite));
                        iM += iComputeFixed64Size2;
                        break;
                    case 25:
                        List list = (List) UnsafeUtil.getObject(j, abstractMessageLite);
                        Class cls = SchemaUtil.GENERATED_MESSAGE_CLASS;
                        int size = list.size();
                        iM += size == 0 ? 0 : (CodedOutputStream.computeTagSize(i5) + 1) * size;
                        break;
                    case 26:
                        iComputeFixed64Size2 = SchemaUtil.computeSizeStringList(i5, (List) UnsafeUtil.getObject(j, abstractMessageLite));
                        iM += iComputeFixed64Size2;
                        break;
                    case 27:
                        iComputeFixed64Size2 = SchemaUtil.computeSizeMessageList(i5, (List) UnsafeUtil.getObject(j, abstractMessageLite), getMessageFieldSchema(i4));
                        iM += iComputeFixed64Size2;
                        break;
                    case 28:
                        iComputeFixed64Size2 = SchemaUtil.computeSizeByteStringList(i5, (List) UnsafeUtil.getObject(j, abstractMessageLite));
                        iM += iComputeFixed64Size2;
                        break;
                    case 29:
                        iComputeFixed64Size2 = SchemaUtil.computeSizeUInt32List(i5, (List) UnsafeUtil.getObject(j, abstractMessageLite));
                        iM += iComputeFixed64Size2;
                        break;
                    case 30:
                        iComputeFixed64Size2 = SchemaUtil.computeSizeEnumList(i5, (List) UnsafeUtil.getObject(j, abstractMessageLite));
                        iM += iComputeFixed64Size2;
                        break;
                    case 31:
                        iComputeFixed64Size2 = SchemaUtil.computeSizeFixed32List(i5, (List) UnsafeUtil.getObject(j, abstractMessageLite));
                        iM += iComputeFixed64Size2;
                        break;
                    case 32:
                        iComputeFixed64Size2 = SchemaUtil.computeSizeFixed64List(i5, (List) UnsafeUtil.getObject(j, abstractMessageLite));
                        iM += iComputeFixed64Size2;
                        break;
                    case 33:
                        iComputeFixed64Size2 = SchemaUtil.computeSizeSInt32List(i5, (List) UnsafeUtil.getObject(j, abstractMessageLite));
                        iM += iComputeFixed64Size2;
                        break;
                    case 34:
                        iComputeFixed64Size2 = SchemaUtil.computeSizeSInt64List(i5, (List) UnsafeUtil.getObject(j, abstractMessageLite));
                        iM += iComputeFixed64Size2;
                        break;
                    case 35:
                        int iComputeSizeFixed64ListNoTag = SchemaUtil.computeSizeFixed64ListNoTag((List) unsafe.getObject(abstractMessageLite, j));
                        if (iComputeSizeFixed64ListNoTag <= 0) {
                            break;
                        } else {
                            if (z3) {
                                unsafe.putInt(abstractMessageLite, i6, iComputeSizeFixed64ListNoTag);
                            }
                            iM = FieldSet$$ExternalSyntheticOutline0.m(iComputeSizeFixed64ListNoTag, CodedOutputStream.computeTagSize(i5), iComputeSizeFixed64ListNoTag, iM);
                            break;
                        }
                    case 36:
                        int iComputeSizeFixed32ListNoTag = SchemaUtil.computeSizeFixed32ListNoTag((List) unsafe.getObject(abstractMessageLite, j));
                        if (iComputeSizeFixed32ListNoTag <= 0) {
                            break;
                        } else {
                            if (z3) {
                                unsafe.putInt(abstractMessageLite, i6, iComputeSizeFixed32ListNoTag);
                            }
                            iM = FieldSet$$ExternalSyntheticOutline0.m(iComputeSizeFixed32ListNoTag, CodedOutputStream.computeTagSize(i5), iComputeSizeFixed32ListNoTag, iM);
                            break;
                        }
                    case 37:
                        int iComputeSizeInt64ListNoTag = SchemaUtil.computeSizeInt64ListNoTag((List) unsafe.getObject(abstractMessageLite, j));
                        if (iComputeSizeInt64ListNoTag <= 0) {
                            break;
                        } else {
                            if (z3) {
                                unsafe.putInt(abstractMessageLite, i6, iComputeSizeInt64ListNoTag);
                            }
                            iM = FieldSet$$ExternalSyntheticOutline0.m(iComputeSizeInt64ListNoTag, CodedOutputStream.computeTagSize(i5), iComputeSizeInt64ListNoTag, iM);
                            break;
                        }
                    case 38:
                        int iComputeSizeUInt64ListNoTag = SchemaUtil.computeSizeUInt64ListNoTag((List) unsafe.getObject(abstractMessageLite, j));
                        if (iComputeSizeUInt64ListNoTag <= 0) {
                            break;
                        } else {
                            if (z3) {
                                unsafe.putInt(abstractMessageLite, i6, iComputeSizeUInt64ListNoTag);
                            }
                            iM = FieldSet$$ExternalSyntheticOutline0.m(iComputeSizeUInt64ListNoTag, CodedOutputStream.computeTagSize(i5), iComputeSizeUInt64ListNoTag, iM);
                            break;
                        }
                    case 39:
                        int iComputeSizeInt32ListNoTag = SchemaUtil.computeSizeInt32ListNoTag((List) unsafe.getObject(abstractMessageLite, j));
                        if (iComputeSizeInt32ListNoTag <= 0) {
                            break;
                        } else {
                            if (z3) {
                                unsafe.putInt(abstractMessageLite, i6, iComputeSizeInt32ListNoTag);
                            }
                            iM = FieldSet$$ExternalSyntheticOutline0.m(iComputeSizeInt32ListNoTag, CodedOutputStream.computeTagSize(i5), iComputeSizeInt32ListNoTag, iM);
                            break;
                        }
                    case 40:
                        int iComputeSizeFixed64ListNoTag2 = SchemaUtil.computeSizeFixed64ListNoTag((List) unsafe.getObject(abstractMessageLite, j));
                        if (iComputeSizeFixed64ListNoTag2 <= 0) {
                            break;
                        } else {
                            if (z3) {
                                unsafe.putInt(abstractMessageLite, i6, iComputeSizeFixed64ListNoTag2);
                            }
                            iM = FieldSet$$ExternalSyntheticOutline0.m(iComputeSizeFixed64ListNoTag2, CodedOutputStream.computeTagSize(i5), iComputeSizeFixed64ListNoTag2, iM);
                            break;
                        }
                    case 41:
                        int iComputeSizeFixed32ListNoTag2 = SchemaUtil.computeSizeFixed32ListNoTag((List) unsafe.getObject(abstractMessageLite, j));
                        if (iComputeSizeFixed32ListNoTag2 <= 0) {
                            break;
                        } else {
                            if (z3) {
                                unsafe.putInt(abstractMessageLite, i6, iComputeSizeFixed32ListNoTag2);
                            }
                            iM = FieldSet$$ExternalSyntheticOutline0.m(iComputeSizeFixed32ListNoTag2, CodedOutputStream.computeTagSize(i5), iComputeSizeFixed32ListNoTag2, iM);
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
                                unsafe.putInt(abstractMessageLite, i6, size2);
                            }
                            iM = FieldSet$$ExternalSyntheticOutline0.m(size2, CodedOutputStream.computeTagSize(i5), size2, iM);
                            break;
                        }
                    case 43:
                        int iComputeSizeUInt32ListNoTag = SchemaUtil.computeSizeUInt32ListNoTag((List) unsafe.getObject(abstractMessageLite, j));
                        if (iComputeSizeUInt32ListNoTag <= 0) {
                            break;
                        } else {
                            if (z3) {
                                unsafe.putInt(abstractMessageLite, i6, iComputeSizeUInt32ListNoTag);
                            }
                            iM = FieldSet$$ExternalSyntheticOutline0.m(iComputeSizeUInt32ListNoTag, CodedOutputStream.computeTagSize(i5), iComputeSizeUInt32ListNoTag, iM);
                            break;
                        }
                    case 44:
                        int iComputeSizeEnumListNoTag = SchemaUtil.computeSizeEnumListNoTag((List) unsafe.getObject(abstractMessageLite, j));
                        if (iComputeSizeEnumListNoTag <= 0) {
                            break;
                        } else {
                            if (z3) {
                                unsafe.putInt(abstractMessageLite, i6, iComputeSizeEnumListNoTag);
                            }
                            iM = FieldSet$$ExternalSyntheticOutline0.m(iComputeSizeEnumListNoTag, CodedOutputStream.computeTagSize(i5), iComputeSizeEnumListNoTag, iM);
                            break;
                        }
                    case 45:
                        int iComputeSizeFixed32ListNoTag3 = SchemaUtil.computeSizeFixed32ListNoTag((List) unsafe.getObject(abstractMessageLite, j));
                        if (iComputeSizeFixed32ListNoTag3 <= 0) {
                            break;
                        } else {
                            if (z3) {
                                unsafe.putInt(abstractMessageLite, i6, iComputeSizeFixed32ListNoTag3);
                            }
                            iM = FieldSet$$ExternalSyntheticOutline0.m(iComputeSizeFixed32ListNoTag3, CodedOutputStream.computeTagSize(i5), iComputeSizeFixed32ListNoTag3, iM);
                            break;
                        }
                    case 46:
                        int iComputeSizeFixed64ListNoTag3 = SchemaUtil.computeSizeFixed64ListNoTag((List) unsafe.getObject(abstractMessageLite, j));
                        if (iComputeSizeFixed64ListNoTag3 <= 0) {
                            break;
                        } else {
                            if (z3) {
                                unsafe.putInt(abstractMessageLite, i6, iComputeSizeFixed64ListNoTag3);
                            }
                            iM = FieldSet$$ExternalSyntheticOutline0.m(iComputeSizeFixed64ListNoTag3, CodedOutputStream.computeTagSize(i5), iComputeSizeFixed64ListNoTag3, iM);
                            break;
                        }
                    case 47:
                        int iComputeSizeSInt32ListNoTag = SchemaUtil.computeSizeSInt32ListNoTag((List) unsafe.getObject(abstractMessageLite, j));
                        if (iComputeSizeSInt32ListNoTag <= 0) {
                            break;
                        } else {
                            if (z3) {
                                unsafe.putInt(abstractMessageLite, i6, iComputeSizeSInt32ListNoTag);
                            }
                            iM = FieldSet$$ExternalSyntheticOutline0.m(iComputeSizeSInt32ListNoTag, CodedOutputStream.computeTagSize(i5), iComputeSizeSInt32ListNoTag, iM);
                            break;
                        }
                    case 48:
                        int iComputeSizeSInt64ListNoTag = SchemaUtil.computeSizeSInt64ListNoTag((List) unsafe.getObject(abstractMessageLite, j));
                        if (iComputeSizeSInt64ListNoTag <= 0) {
                            break;
                        } else {
                            if (z3) {
                                unsafe.putInt(abstractMessageLite, i6, iComputeSizeSInt64ListNoTag);
                            }
                            iM = FieldSet$$ExternalSyntheticOutline0.m(iComputeSizeSInt64ListNoTag, CodedOutputStream.computeTagSize(i5), iComputeSizeSInt64ListNoTag, iM);
                            break;
                        }
                    case 49:
                        List list3 = (List) UnsafeUtil.getObject(j, abstractMessageLite);
                        Schema messageFieldSchema = getMessageFieldSchema(i4);
                        Class cls3 = SchemaUtil.GENERATED_MESSAGE_CLASS;
                        int size3 = list3.size();
                        if (size3 == 0) {
                            iComputeGroupSize2 = 0;
                        } else {
                            iComputeGroupSize2 = 0;
                            for (int i10 = 0; i10 < size3; i10++) {
                                iComputeGroupSize2 = CodedOutputStream.computeGroupSize(i5, (MessageLite) list3.get(i10), messageFieldSchema) + iComputeGroupSize2;
                            }
                        }
                        iM = iComputeGroupSize2 + iM;
                        break;
                    case 50:
                        iComputeFixed64Size2 = ((MapFieldSchemaLite) mapFieldSchema2).getSerializedSize(i5, UnsafeUtil.getObject(j, abstractMessageLite), getMapFieldDefaultEntry(i4));
                        iM += iComputeFixed64Size2;
                        break;
                    case 51:
                        if (isOneofPresent(i5, i4, abstractMessageLite)) {
                            iM = MessageSchema$$ExternalSyntheticOutline0.m(i5, 8, iM);
                            break;
                        } else {
                            break;
                        }
                    case 52:
                        if (isOneofPresent(i5, i4, abstractMessageLite)) {
                            iM = MessageSchema$$ExternalSyntheticOutline0.m(i5, i3, iM);
                            break;
                        } else {
                            break;
                        }
                    case 53:
                        if (isOneofPresent(i5, i4, abstractMessageLite)) {
                            long jOneofLongAt = oneofLongAt(j, abstractMessageLite);
                            iComputeTagSize6 = CodedOutputStream.computeTagSize(i5);
                            iComputeUInt64SizeNoTag3 = CodedOutputStream.computeUInt64SizeNoTag(jOneofLongAt);
                            iM += iComputeUInt64SizeNoTag3 + iComputeTagSize6;
                            break;
                        } else {
                            break;
                        }
                    case 54:
                        if (isOneofPresent(i5, i4, abstractMessageLite)) {
                            long jOneofLongAt2 = oneofLongAt(j, abstractMessageLite);
                            iComputeTagSize6 = CodedOutputStream.computeTagSize(i5);
                            iComputeUInt64SizeNoTag3 = CodedOutputStream.computeUInt64SizeNoTag(jOneofLongAt2);
                            iM += iComputeUInt64SizeNoTag3 + iComputeTagSize6;
                            break;
                        } else {
                            break;
                        }
                    case 55:
                        if (isOneofPresent(i5, i4, abstractMessageLite)) {
                            int iOneofIntAt = oneofIntAt(j, abstractMessageLite);
                            iComputeTagSize4 = CodedOutputStream.computeTagSize(i5);
                            iComputeUInt64SizeNoTag2 = CodedOutputStream.computeInt32SizeNoTag(iOneofIntAt);
                            iComputeFixed64Size2 = iComputeUInt64SizeNoTag2 + iComputeTagSize4;
                            iM += iComputeFixed64Size2;
                            break;
                        } else {
                            break;
                        }
                    case 56:
                        if (isOneofPresent(i5, i4, abstractMessageLite)) {
                            iComputeFixed64Size2 = CodedOutputStream.computeFixed64Size(i5);
                            iM += iComputeFixed64Size2;
                            break;
                        } else {
                            break;
                        }
                    case 57:
                        if (isOneofPresent(i5, i4, abstractMessageLite)) {
                            iComputeFixed64Size2 = CodedOutputStream.computeFixed32Size(i5);
                            iM += iComputeFixed64Size2;
                            break;
                        } else {
                            break;
                        }
                    case 58:
                        if (isOneofPresent(i5, i4, abstractMessageLite)) {
                            iM = MessageSchema$$ExternalSyntheticOutline0.m(i5, 1, iM);
                            break;
                        } else {
                            break;
                        }
                    case 59:
                        if (isOneofPresent(i5, i4, abstractMessageLite)) {
                            Object object2 = UnsafeUtil.getObject(j, abstractMessageLite);
                            iComputeBytesSize2 = object2 instanceof ByteString ? CodedOutputStream.computeBytesSize(i5, (ByteString) object2) : CodedOutputStream.computeStringSizeNoTag((String) object2) + CodedOutputStream.computeTagSize(i5);
                            iM = iComputeBytesSize2 + iM;
                            break;
                        } else {
                            break;
                        }
                    case 60:
                        if (isOneofPresent(i5, i4, abstractMessageLite)) {
                            iComputeFixed64Size2 = SchemaUtil.computeSizeMessage(i5, UnsafeUtil.getObject(j, abstractMessageLite), getMessageFieldSchema(i4));
                            iM += iComputeFixed64Size2;
                            break;
                        } else {
                            break;
                        }
                    case 61:
                        if (isOneofPresent(i5, i4, abstractMessageLite)) {
                            iComputeFixed64Size2 = CodedOutputStream.computeBytesSize(i5, (ByteString) UnsafeUtil.getObject(j, abstractMessageLite));
                            iM += iComputeFixed64Size2;
                            break;
                        } else {
                            break;
                        }
                    case 62:
                        if (isOneofPresent(i5, i4, abstractMessageLite)) {
                            iComputeFixed64Size2 = CodedOutputStream.computeUInt32Size(i5, oneofIntAt(j, abstractMessageLite));
                            iM += iComputeFixed64Size2;
                            break;
                        } else {
                            break;
                        }
                    case 63:
                        if (isOneofPresent(i5, i4, abstractMessageLite)) {
                            int iOneofIntAt2 = oneofIntAt(j, abstractMessageLite);
                            iComputeTagSize4 = CodedOutputStream.computeTagSize(i5);
                            iComputeUInt64SizeNoTag2 = CodedOutputStream.computeInt32SizeNoTag(iOneofIntAt2);
                            iComputeFixed64Size2 = iComputeUInt64SizeNoTag2 + iComputeTagSize4;
                            iM += iComputeFixed64Size2;
                            break;
                        } else {
                            break;
                        }
                    case 64:
                        if (isOneofPresent(i5, i4, abstractMessageLite)) {
                            iM = MessageSchema$$ExternalSyntheticOutline0.m(i5, i3, iM);
                            break;
                        } else {
                            break;
                        }
                    case 65:
                        if (isOneofPresent(i5, i4, abstractMessageLite)) {
                            iM = MessageSchema$$ExternalSyntheticOutline0.m(i5, 8, iM);
                            break;
                        } else {
                            break;
                        }
                    case 66:
                        if (isOneofPresent(i5, i4, abstractMessageLite)) {
                            int iOneofIntAt3 = oneofIntAt(j, abstractMessageLite);
                            iComputeTagSize4 = CodedOutputStream.computeTagSize(i5);
                            iComputeUInt64SizeNoTag2 = CodedOutputStream.computeUInt32SizeNoTag((iOneofIntAt3 >> 31) ^ (iOneofIntAt3 << 1));
                            iComputeFixed64Size2 = iComputeUInt64SizeNoTag2 + iComputeTagSize4;
                            iM += iComputeFixed64Size2;
                            break;
                        } else {
                            break;
                        }
                    case 67:
                        if (isOneofPresent(i5, i4, abstractMessageLite)) {
                            long jOneofLongAt3 = oneofLongAt(j, abstractMessageLite);
                            iComputeTagSize6 = CodedOutputStream.computeTagSize(i5);
                            iComputeUInt64SizeNoTag3 = CodedOutputStream.computeUInt64SizeNoTag((jOneofLongAt3 << 1) ^ (jOneofLongAt3 >> 63));
                            iM += iComputeUInt64SizeNoTag3 + iComputeTagSize6;
                            break;
                        } else {
                            break;
                        }
                    case 68:
                        if (isOneofPresent(i5, i4, abstractMessageLite)) {
                            iComputeFixed64Size2 = CodedOutputStream.computeGroupSize(i5, (MessageLite) UnsafeUtil.getObject(j, abstractMessageLite), getMessageFieldSchema(i4));
                            iM += iComputeFixed64Size2;
                            break;
                        } else {
                            break;
                        }
                }
                i4 += 3;
                i3 = 4;
            }
            return unknownFieldSchema.getSerializedSize(unknownFieldSchema.getFromMessage(abstractMessageLite)) + iM;
        }
        Unsafe unsafe2 = UNSAFE;
        int i11 = 1048575;
        int i12 = 0;
        int iM2 = 0;
        int i13 = 0;
        while (i12 < iArr.length) {
            int iTypeAndOffsetAt2 = typeAndOffsetAt(i12);
            int i14 = iArr[i12];
            int iType2 = type(iTypeAndOffsetAt2);
            if (iType2 <= 17) {
                i = iArr[i12 + 2];
                int i15 = i & 1048575;
                i2 = 1 << (i >>> 20);
                mapFieldSchema = mapFieldSchema2;
                z = z3;
                if (i15 != i11) {
                    i13 = unsafe2.getInt(abstractMessageLite, i15);
                    i11 = i15;
                }
            } else {
                mapFieldSchema = mapFieldSchema2;
                z = z3;
                i = (!z || iType2 < FieldType.DOUBLE_LIST_PACKED.id() || iType2 > FieldType.SINT64_LIST_PACKED.id()) ? 0 : iArr[i12 + 2] & 1048575;
                i2 = 0;
            }
            long j5 = iTypeAndOffsetAt2 & 1048575;
            switch (iType2) {
                case 0:
                    if ((i13 & i2) != 0) {
                        c = '\b';
                        iM2 = MessageSchema$$ExternalSyntheticOutline0.m(i14, 8, iM2);
                        break;
                    } else {
                        c = '\b';
                        break;
                    }
                case 1:
                    if ((i13 & i2) != 0) {
                        iM2 = MessageSchema$$ExternalSyntheticOutline0.m(i14, 4, iM2);
                    }
                    c = '\b';
                    break;
                case 2:
                    if ((i13 & i2) != 0) {
                        long j6 = unsafe2.getLong(abstractMessageLite, j5);
                        iComputeTagSize = CodedOutputStream.computeTagSize(i14);
                        iComputeUInt64SizeNoTag = CodedOutputStream.computeUInt64SizeNoTag(j6);
                        iComputeTagSize3 = iComputeUInt64SizeNoTag + iComputeTagSize;
                        iM2 += iComputeTagSize3;
                    }
                    c = '\b';
                    break;
                case 3:
                    if ((i13 & i2) != 0) {
                        long j7 = unsafe2.getLong(abstractMessageLite, j5);
                        iComputeTagSize = CodedOutputStream.computeTagSize(i14);
                        iComputeUInt64SizeNoTag = CodedOutputStream.computeUInt64SizeNoTag(j7);
                        iComputeTagSize3 = iComputeUInt64SizeNoTag + iComputeTagSize;
                        iM2 += iComputeTagSize3;
                    }
                    c = '\b';
                    break;
                case 4:
                    if ((i13 & i2) != 0) {
                        int i16 = unsafe2.getInt(abstractMessageLite, j5);
                        iComputeTagSize2 = CodedOutputStream.computeTagSize(i14);
                        iComputeInt32SizeNoTag = CodedOutputStream.computeInt32SizeNoTag(i16);
                        iComputeFixed64Size = iComputeInt32SizeNoTag + iComputeTagSize2;
                        iM2 += iComputeFixed64Size;
                    }
                    c = '\b';
                    break;
                case 5:
                    if ((i13 & i2) != 0) {
                        iComputeFixed64Size = CodedOutputStream.computeFixed64Size(i14);
                        iM2 += iComputeFixed64Size;
                    }
                    c = '\b';
                    break;
                case 6:
                    if ((i13 & i2) != 0) {
                        iComputeFixed64Size = CodedOutputStream.computeFixed32Size(i14);
                        iM2 += iComputeFixed64Size;
                    }
                    c = '\b';
                    break;
                case 7:
                    if ((i13 & i2) != 0) {
                        iM2 = MessageSchema$$ExternalSyntheticOutline0.m(i14, 1, iM2);
                    }
                    c = '\b';
                    break;
                case 8:
                    if ((i13 & i2) != 0) {
                        Object object3 = unsafe2.getObject(abstractMessageLite, j5);
                        iComputeBytesSize = object3 instanceof ByteString ? CodedOutputStream.computeBytesSize(i14, (ByteString) object3) : CodedOutputStream.computeStringSizeNoTag((String) object3) + CodedOutputStream.computeTagSize(i14);
                        iM2 = iComputeBytesSize + iM2;
                    }
                    c = '\b';
                    break;
                case 9:
                    if ((i13 & i2) != 0) {
                        iComputeFixed64Size = SchemaUtil.computeSizeMessage(i14, unsafe2.getObject(abstractMessageLite, j5), getMessageFieldSchema(i12));
                        iM2 += iComputeFixed64Size;
                    }
                    c = '\b';
                    break;
                case 10:
                    if ((i13 & i2) != 0) {
                        iComputeFixed64Size = CodedOutputStream.computeBytesSize(i14, (ByteString) unsafe2.getObject(abstractMessageLite, j5));
                        iM2 += iComputeFixed64Size;
                    }
                    c = '\b';
                    break;
                case 11:
                    if ((i13 & i2) != 0) {
                        iComputeFixed64Size = CodedOutputStream.computeUInt32Size(i14, unsafe2.getInt(abstractMessageLite, j5));
                        iM2 += iComputeFixed64Size;
                    }
                    c = '\b';
                    break;
                case 12:
                    if ((i13 & i2) != 0) {
                        int i17 = unsafe2.getInt(abstractMessageLite, j5);
                        iComputeTagSize2 = CodedOutputStream.computeTagSize(i14);
                        iComputeInt32SizeNoTag = CodedOutputStream.computeInt32SizeNoTag(i17);
                        iComputeFixed64Size = iComputeInt32SizeNoTag + iComputeTagSize2;
                        iM2 += iComputeFixed64Size;
                    }
                    c = '\b';
                    break;
                case 13:
                    if ((i13 & i2) != 0) {
                        iM2 = MessageSchema$$ExternalSyntheticOutline0.m(i14, 4, iM2);
                    }
                    c = '\b';
                    break;
                case 14:
                    if ((i13 & i2) != 0) {
                        c2 = '\b';
                        iM2 = MessageSchema$$ExternalSyntheticOutline0.m(i14, 8, iM2);
                        c = c2;
                        break;
                    }
                    c = '\b';
                    break;
                case 15:
                    if ((i13 & i2) != 0) {
                        int i18 = unsafe2.getInt(abstractMessageLite, j5);
                        iComputeTagSize2 = CodedOutputStream.computeTagSize(i14);
                        iComputeInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag((i18 >> 31) ^ (i18 << 1));
                        iComputeFixed64Size = iComputeInt32SizeNoTag + iComputeTagSize2;
                        iM2 += iComputeFixed64Size;
                    }
                    c = '\b';
                    break;
                case 16:
                    if ((i13 & i2) != 0) {
                        long j8 = unsafe2.getLong(abstractMessageLite, j5);
                        iComputeTagSize = CodedOutputStream.computeTagSize(i14);
                        iComputeUInt64SizeNoTag = CodedOutputStream.computeUInt64SizeNoTag((j8 >> 63) ^ (j8 << 1));
                        iComputeTagSize3 = iComputeUInt64SizeNoTag + iComputeTagSize;
                        iM2 += iComputeTagSize3;
                    }
                    c = '\b';
                    break;
                case 17:
                    if ((i13 & i2) != 0) {
                        iComputeFixed64Size = CodedOutputStream.computeGroupSize(i14, (MessageLite) unsafe2.getObject(abstractMessageLite, j5), getMessageFieldSchema(i12));
                        iM2 += iComputeFixed64Size;
                    }
                    c = '\b';
                    break;
                case 18:
                    iComputeFixed64Size = SchemaUtil.computeSizeFixed64List(i14, (List) unsafe2.getObject(abstractMessageLite, j5));
                    iM2 += iComputeFixed64Size;
                    c = '\b';
                    break;
                case 19:
                    iComputeFixed64Size = SchemaUtil.computeSizeFixed32List(i14, (List) unsafe2.getObject(abstractMessageLite, j5));
                    iM2 += iComputeFixed64Size;
                    c = '\b';
                    break;
                case 20:
                    iComputeFixed64Size = SchemaUtil.computeSizeInt64List(i14, (List) unsafe2.getObject(abstractMessageLite, j5));
                    iM2 += iComputeFixed64Size;
                    c = '\b';
                    break;
                case 21:
                    iComputeFixed64Size = SchemaUtil.computeSizeUInt64List(i14, (List) unsafe2.getObject(abstractMessageLite, j5));
                    iM2 += iComputeFixed64Size;
                    c = '\b';
                    break;
                case 22:
                    iComputeFixed64Size = SchemaUtil.computeSizeInt32List(i14, (List) unsafe2.getObject(abstractMessageLite, j5));
                    iM2 += iComputeFixed64Size;
                    c = '\b';
                    break;
                case 23:
                    iComputeFixed64Size = SchemaUtil.computeSizeFixed64List(i14, (List) unsafe2.getObject(abstractMessageLite, j5));
                    iM2 += iComputeFixed64Size;
                    c = '\b';
                    break;
                case 24:
                    iComputeFixed64Size = SchemaUtil.computeSizeFixed32List(i14, (List) unsafe2.getObject(abstractMessageLite, j5));
                    iM2 += iComputeFixed64Size;
                    c = '\b';
                    break;
                case 25:
                    List list4 = (List) unsafe2.getObject(abstractMessageLite, j5);
                    Class cls4 = SchemaUtil.GENERATED_MESSAGE_CLASS;
                    int size4 = list4.size();
                    iComputeTagSize3 = size4 == 0 ? 0 : (CodedOutputStream.computeTagSize(i14) + 1) * size4;
                    iM2 += iComputeTagSize3;
                    c = '\b';
                    break;
                case 26:
                    iComputeFixed64Size = SchemaUtil.computeSizeStringList(i14, (List) unsafe2.getObject(abstractMessageLite, j5));
                    iM2 += iComputeFixed64Size;
                    c = '\b';
                    break;
                case 27:
                    iComputeFixed64Size = SchemaUtil.computeSizeMessageList(i14, (List) unsafe2.getObject(abstractMessageLite, j5), getMessageFieldSchema(i12));
                    iM2 += iComputeFixed64Size;
                    c = '\b';
                    break;
                case 28:
                    iComputeFixed64Size = SchemaUtil.computeSizeByteStringList(i14, (List) unsafe2.getObject(abstractMessageLite, j5));
                    iM2 += iComputeFixed64Size;
                    c = '\b';
                    break;
                case 29:
                    iComputeFixed64Size = SchemaUtil.computeSizeUInt32List(i14, (List) unsafe2.getObject(abstractMessageLite, j5));
                    iM2 += iComputeFixed64Size;
                    c = '\b';
                    break;
                case 30:
                    iComputeFixed64Size = SchemaUtil.computeSizeEnumList(i14, (List) unsafe2.getObject(abstractMessageLite, j5));
                    iM2 += iComputeFixed64Size;
                    c = '\b';
                    break;
                case 31:
                    iComputeFixed64Size = SchemaUtil.computeSizeFixed32List(i14, (List) unsafe2.getObject(abstractMessageLite, j5));
                    iM2 += iComputeFixed64Size;
                    c = '\b';
                    break;
                case 32:
                    iComputeFixed64Size = SchemaUtil.computeSizeFixed64List(i14, (List) unsafe2.getObject(abstractMessageLite, j5));
                    iM2 += iComputeFixed64Size;
                    c = '\b';
                    break;
                case 33:
                    iComputeFixed64Size = SchemaUtil.computeSizeSInt32List(i14, (List) unsafe2.getObject(abstractMessageLite, j5));
                    iM2 += iComputeFixed64Size;
                    c = '\b';
                    break;
                case 34:
                    iComputeFixed64Size = SchemaUtil.computeSizeSInt64List(i14, (List) unsafe2.getObject(abstractMessageLite, j5));
                    iM2 += iComputeFixed64Size;
                    c = '\b';
                    break;
                case 35:
                    int iComputeSizeFixed64ListNoTag4 = SchemaUtil.computeSizeFixed64ListNoTag((List) unsafe2.getObject(abstractMessageLite, j5));
                    if (iComputeSizeFixed64ListNoTag4 > 0) {
                        if (z) {
                            unsafe2.putInt(abstractMessageLite, i, iComputeSizeFixed64ListNoTag4);
                        }
                        iM2 = FieldSet$$ExternalSyntheticOutline0.m(iComputeSizeFixed64ListNoTag4, CodedOutputStream.computeTagSize(i14), iComputeSizeFixed64ListNoTag4, iM2);
                    }
                    c = '\b';
                    break;
                case 36:
                    int iComputeSizeFixed32ListNoTag4 = SchemaUtil.computeSizeFixed32ListNoTag((List) unsafe2.getObject(abstractMessageLite, j5));
                    if (iComputeSizeFixed32ListNoTag4 > 0) {
                        if (z) {
                            unsafe2.putInt(abstractMessageLite, i, iComputeSizeFixed32ListNoTag4);
                        }
                        iM2 = FieldSet$$ExternalSyntheticOutline0.m(iComputeSizeFixed32ListNoTag4, CodedOutputStream.computeTagSize(i14), iComputeSizeFixed32ListNoTag4, iM2);
                    }
                    c = '\b';
                    break;
                case 37:
                    int iComputeSizeInt64ListNoTag2 = SchemaUtil.computeSizeInt64ListNoTag((List) unsafe2.getObject(abstractMessageLite, j5));
                    if (iComputeSizeInt64ListNoTag2 > 0) {
                        if (z) {
                            unsafe2.putInt(abstractMessageLite, i, iComputeSizeInt64ListNoTag2);
                        }
                        iM2 = FieldSet$$ExternalSyntheticOutline0.m(iComputeSizeInt64ListNoTag2, CodedOutputStream.computeTagSize(i14), iComputeSizeInt64ListNoTag2, iM2);
                    }
                    c = '\b';
                    break;
                case 38:
                    int iComputeSizeUInt64ListNoTag2 = SchemaUtil.computeSizeUInt64ListNoTag((List) unsafe2.getObject(abstractMessageLite, j5));
                    if (iComputeSizeUInt64ListNoTag2 > 0) {
                        if (z) {
                            unsafe2.putInt(abstractMessageLite, i, iComputeSizeUInt64ListNoTag2);
                        }
                        iM2 = FieldSet$$ExternalSyntheticOutline0.m(iComputeSizeUInt64ListNoTag2, CodedOutputStream.computeTagSize(i14), iComputeSizeUInt64ListNoTag2, iM2);
                    }
                    c = '\b';
                    break;
                case 39:
                    int iComputeSizeInt32ListNoTag2 = SchemaUtil.computeSizeInt32ListNoTag((List) unsafe2.getObject(abstractMessageLite, j5));
                    if (iComputeSizeInt32ListNoTag2 > 0) {
                        if (z) {
                            unsafe2.putInt(abstractMessageLite, i, iComputeSizeInt32ListNoTag2);
                        }
                        iM2 = FieldSet$$ExternalSyntheticOutline0.m(iComputeSizeInt32ListNoTag2, CodedOutputStream.computeTagSize(i14), iComputeSizeInt32ListNoTag2, iM2);
                    }
                    c = '\b';
                    break;
                case 40:
                    int iComputeSizeFixed64ListNoTag5 = SchemaUtil.computeSizeFixed64ListNoTag((List) unsafe2.getObject(abstractMessageLite, j5));
                    if (iComputeSizeFixed64ListNoTag5 > 0) {
                        if (z) {
                            unsafe2.putInt(abstractMessageLite, i, iComputeSizeFixed64ListNoTag5);
                        }
                        iM2 = FieldSet$$ExternalSyntheticOutline0.m(iComputeSizeFixed64ListNoTag5, CodedOutputStream.computeTagSize(i14), iComputeSizeFixed64ListNoTag5, iM2);
                    }
                    c = '\b';
                    break;
                case 41:
                    int iComputeSizeFixed32ListNoTag5 = SchemaUtil.computeSizeFixed32ListNoTag((List) unsafe2.getObject(abstractMessageLite, j5));
                    if (iComputeSizeFixed32ListNoTag5 > 0) {
                        if (z) {
                            unsafe2.putInt(abstractMessageLite, i, iComputeSizeFixed32ListNoTag5);
                        }
                        iM2 = FieldSet$$ExternalSyntheticOutline0.m(iComputeSizeFixed32ListNoTag5, CodedOutputStream.computeTagSize(i14), iComputeSizeFixed32ListNoTag5, iM2);
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
                        iM2 = FieldSet$$ExternalSyntheticOutline0.m(size5, CodedOutputStream.computeTagSize(i14), size5, iM2);
                    }
                    c = '\b';
                    break;
                case 43:
                    int iComputeSizeUInt32ListNoTag2 = SchemaUtil.computeSizeUInt32ListNoTag((List) unsafe2.getObject(abstractMessageLite, j5));
                    if (iComputeSizeUInt32ListNoTag2 > 0) {
                        if (z) {
                            unsafe2.putInt(abstractMessageLite, i, iComputeSizeUInt32ListNoTag2);
                        }
                        iM2 = FieldSet$$ExternalSyntheticOutline0.m(iComputeSizeUInt32ListNoTag2, CodedOutputStream.computeTagSize(i14), iComputeSizeUInt32ListNoTag2, iM2);
                    }
                    c = '\b';
                    break;
                case 44:
                    int iComputeSizeEnumListNoTag2 = SchemaUtil.computeSizeEnumListNoTag((List) unsafe2.getObject(abstractMessageLite, j5));
                    if (iComputeSizeEnumListNoTag2 > 0) {
                        if (z) {
                            unsafe2.putInt(abstractMessageLite, i, iComputeSizeEnumListNoTag2);
                        }
                        iM2 = FieldSet$$ExternalSyntheticOutline0.m(iComputeSizeEnumListNoTag2, CodedOutputStream.computeTagSize(i14), iComputeSizeEnumListNoTag2, iM2);
                    }
                    c = '\b';
                    break;
                case 45:
                    int iComputeSizeFixed32ListNoTag6 = SchemaUtil.computeSizeFixed32ListNoTag((List) unsafe2.getObject(abstractMessageLite, j5));
                    if (iComputeSizeFixed32ListNoTag6 > 0) {
                        if (z) {
                            unsafe2.putInt(abstractMessageLite, i, iComputeSizeFixed32ListNoTag6);
                        }
                        iM2 = FieldSet$$ExternalSyntheticOutline0.m(iComputeSizeFixed32ListNoTag6, CodedOutputStream.computeTagSize(i14), iComputeSizeFixed32ListNoTag6, iM2);
                    }
                    c = '\b';
                    break;
                case 46:
                    int iComputeSizeFixed64ListNoTag6 = SchemaUtil.computeSizeFixed64ListNoTag((List) unsafe2.getObject(abstractMessageLite, j5));
                    if (iComputeSizeFixed64ListNoTag6 > 0) {
                        if (z) {
                            unsafe2.putInt(abstractMessageLite, i, iComputeSizeFixed64ListNoTag6);
                        }
                        iM2 = FieldSet$$ExternalSyntheticOutline0.m(iComputeSizeFixed64ListNoTag6, CodedOutputStream.computeTagSize(i14), iComputeSizeFixed64ListNoTag6, iM2);
                    }
                    c = '\b';
                    break;
                case 47:
                    int iComputeSizeSInt32ListNoTag2 = SchemaUtil.computeSizeSInt32ListNoTag((List) unsafe2.getObject(abstractMessageLite, j5));
                    if (iComputeSizeSInt32ListNoTag2 > 0) {
                        if (z) {
                            unsafe2.putInt(abstractMessageLite, i, iComputeSizeSInt32ListNoTag2);
                        }
                        iM2 = FieldSet$$ExternalSyntheticOutline0.m(iComputeSizeSInt32ListNoTag2, CodedOutputStream.computeTagSize(i14), iComputeSizeSInt32ListNoTag2, iM2);
                    }
                    c = '\b';
                    break;
                case 48:
                    int iComputeSizeSInt64ListNoTag2 = SchemaUtil.computeSizeSInt64ListNoTag((List) unsafe2.getObject(abstractMessageLite, j5));
                    if (iComputeSizeSInt64ListNoTag2 > 0) {
                        if (z) {
                            unsafe2.putInt(abstractMessageLite, i, iComputeSizeSInt64ListNoTag2);
                        }
                        iM2 = FieldSet$$ExternalSyntheticOutline0.m(iComputeSizeSInt64ListNoTag2, CodedOutputStream.computeTagSize(i14), iComputeSizeSInt64ListNoTag2, iM2);
                    }
                    c = '\b';
                    break;
                case 49:
                    List list6 = (List) unsafe2.getObject(abstractMessageLite, j5);
                    Schema messageFieldSchema2 = getMessageFieldSchema(i12);
                    Class cls6 = SchemaUtil.GENERATED_MESSAGE_CLASS;
                    int size6 = list6.size();
                    if (size6 == 0) {
                        iComputeGroupSize = 0;
                    } else {
                        iComputeGroupSize = 0;
                        for (int i19 = 0; i19 < size6; i19++) {
                            iComputeGroupSize += CodedOutputStream.computeGroupSize(i14, (MessageLite) list6.get(i19), messageFieldSchema2);
                        }
                    }
                    iM2 += iComputeGroupSize;
                    c = '\b';
                    break;
                case 50:
                    iComputeFixed64Size = ((MapFieldSchemaLite) mapFieldSchema).getSerializedSize(i14, unsafe2.getObject(abstractMessageLite, j5), getMapFieldDefaultEntry(i12));
                    iM2 += iComputeFixed64Size;
                    c = '\b';
                    break;
                case 51:
                    if (isOneofPresent(i14, i12, abstractMessageLite)) {
                        c2 = '\b';
                        iM2 = MessageSchema$$ExternalSyntheticOutline0.m(i14, 8, iM2);
                        c = c2;
                        break;
                    }
                    c = '\b';
                    break;
                case 52:
                    if (isOneofPresent(i14, i12, abstractMessageLite)) {
                        iM2 = MessageSchema$$ExternalSyntheticOutline0.m(i14, 4, iM2);
                    }
                    c = '\b';
                    break;
                case 53:
                    if (isOneofPresent(i14, i12, abstractMessageLite)) {
                        long jOneofLongAt4 = oneofLongAt(j5, abstractMessageLite);
                        iComputeTagSize = CodedOutputStream.computeTagSize(i14);
                        iComputeUInt64SizeNoTag = CodedOutputStream.computeUInt64SizeNoTag(jOneofLongAt4);
                        iComputeTagSize3 = iComputeUInt64SizeNoTag + iComputeTagSize;
                        iM2 += iComputeTagSize3;
                    }
                    c = '\b';
                    break;
                case 54:
                    if (isOneofPresent(i14, i12, abstractMessageLite)) {
                        long jOneofLongAt5 = oneofLongAt(j5, abstractMessageLite);
                        iComputeTagSize = CodedOutputStream.computeTagSize(i14);
                        iComputeUInt64SizeNoTag = CodedOutputStream.computeUInt64SizeNoTag(jOneofLongAt5);
                        iComputeTagSize3 = iComputeUInt64SizeNoTag + iComputeTagSize;
                        iM2 += iComputeTagSize3;
                    }
                    c = '\b';
                    break;
                case 55:
                    if (isOneofPresent(i14, i12, abstractMessageLite)) {
                        int iOneofIntAt4 = oneofIntAt(j5, abstractMessageLite);
                        iComputeTagSize2 = CodedOutputStream.computeTagSize(i14);
                        iComputeInt32SizeNoTag = CodedOutputStream.computeInt32SizeNoTag(iOneofIntAt4);
                        iComputeFixed64Size = iComputeInt32SizeNoTag + iComputeTagSize2;
                        iM2 += iComputeFixed64Size;
                    }
                    c = '\b';
                    break;
                case 56:
                    if (isOneofPresent(i14, i12, abstractMessageLite)) {
                        iComputeFixed64Size = CodedOutputStream.computeFixed64Size(i14);
                        iM2 += iComputeFixed64Size;
                    }
                    c = '\b';
                    break;
                case 57:
                    if (isOneofPresent(i14, i12, abstractMessageLite)) {
                        iComputeFixed64Size = CodedOutputStream.computeFixed32Size(i14);
                        iM2 += iComputeFixed64Size;
                    }
                    c = '\b';
                    break;
                case 58:
                    if (isOneofPresent(i14, i12, abstractMessageLite)) {
                        iM2 = MessageSchema$$ExternalSyntheticOutline0.m(i14, 1, iM2);
                    }
                    c = '\b';
                    break;
                case 59:
                    if (isOneofPresent(i14, i12, abstractMessageLite)) {
                        Object object4 = unsafe2.getObject(abstractMessageLite, j5);
                        iComputeBytesSize = object4 instanceof ByteString ? CodedOutputStream.computeBytesSize(i14, (ByteString) object4) : CodedOutputStream.computeStringSizeNoTag((String) object4) + CodedOutputStream.computeTagSize(i14);
                        iM2 = iComputeBytesSize + iM2;
                    }
                    c = '\b';
                    break;
                case 60:
                    if (isOneofPresent(i14, i12, abstractMessageLite)) {
                        iComputeFixed64Size = SchemaUtil.computeSizeMessage(i14, unsafe2.getObject(abstractMessageLite, j5), getMessageFieldSchema(i12));
                        iM2 += iComputeFixed64Size;
                    }
                    c = '\b';
                    break;
                case 61:
                    if (isOneofPresent(i14, i12, abstractMessageLite)) {
                        iComputeFixed64Size = CodedOutputStream.computeBytesSize(i14, (ByteString) unsafe2.getObject(abstractMessageLite, j5));
                        iM2 += iComputeFixed64Size;
                    }
                    c = '\b';
                    break;
                case 62:
                    if (isOneofPresent(i14, i12, abstractMessageLite)) {
                        iComputeFixed64Size = CodedOutputStream.computeUInt32Size(i14, oneofIntAt(j5, abstractMessageLite));
                        iM2 += iComputeFixed64Size;
                    }
                    c = '\b';
                    break;
                case 63:
                    if (isOneofPresent(i14, i12, abstractMessageLite)) {
                        int iOneofIntAt5 = oneofIntAt(j5, abstractMessageLite);
                        iComputeTagSize2 = CodedOutputStream.computeTagSize(i14);
                        iComputeInt32SizeNoTag = CodedOutputStream.computeInt32SizeNoTag(iOneofIntAt5);
                        iComputeFixed64Size = iComputeInt32SizeNoTag + iComputeTagSize2;
                        iM2 += iComputeFixed64Size;
                    }
                    c = '\b';
                    break;
                case 64:
                    if (isOneofPresent(i14, i12, abstractMessageLite)) {
                        iM2 = MessageSchema$$ExternalSyntheticOutline0.m(i14, 4, iM2);
                    }
                    c = '\b';
                    break;
                case 65:
                    if (isOneofPresent(i14, i12, abstractMessageLite)) {
                        c2 = '\b';
                        iM2 = MessageSchema$$ExternalSyntheticOutline0.m(i14, 8, iM2);
                        c = c2;
                        break;
                    }
                    c = '\b';
                    break;
                case 66:
                    if (isOneofPresent(i14, i12, abstractMessageLite)) {
                        int iOneofIntAt6 = oneofIntAt(j5, abstractMessageLite);
                        iComputeTagSize2 = CodedOutputStream.computeTagSize(i14);
                        iComputeInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag((iOneofIntAt6 >> 31) ^ (iOneofIntAt6 << 1));
                        iComputeFixed64Size = iComputeInt32SizeNoTag + iComputeTagSize2;
                        iM2 += iComputeFixed64Size;
                    }
                    c = '\b';
                    break;
                case 67:
                    if (isOneofPresent(i14, i12, abstractMessageLite)) {
                        long jOneofLongAt6 = oneofLongAt(j5, abstractMessageLite);
                        iComputeTagSize = CodedOutputStream.computeTagSize(i14);
                        iComputeUInt64SizeNoTag = CodedOutputStream.computeUInt64SizeNoTag((jOneofLongAt6 >> 63) ^ (jOneofLongAt6 << 1));
                        iComputeTagSize3 = iComputeUInt64SizeNoTag + iComputeTagSize;
                        iM2 += iComputeTagSize3;
                    }
                    c = '\b';
                    break;
                case 68:
                    if (isOneofPresent(i14, i12, abstractMessageLite)) {
                        iComputeFixed64Size = CodedOutputStream.computeGroupSize(i14, (MessageLite) unsafe2.getObject(abstractMessageLite, j5), getMessageFieldSchema(i12));
                        iM2 += iComputeFixed64Size;
                    }
                    c = '\b';
                    break;
            }
            i12 += 3;
            mapFieldSchema2 = mapFieldSchema;
            z3 = z;
        }
        int serializedSize = unknownFieldSchema.getSerializedSize(unknownFieldSchema.getFromMessage(abstractMessageLite)) + iM2;
        if (!this.hasExtensions) {
            return serializedSize;
        }
        FieldSet extensions = this.extensionSchema.getExtensions(abstractMessageLite);
        int i20 = 0;
        int iComputeFieldSize = 0;
        while (true) {
            SmallSortedMap smallSortedMap = extensions.fields;
            if (i20 >= smallSortedMap.entryList.size()) {
                for (Map.Entry entry : smallSortedMap.getOverflowEntries()) {
                    iComputeFieldSize = FieldSet.computeFieldSize((GeneratedMessageLite.ExtensionDescriptor) entry.getKey(), entry.getValue()) + iComputeFieldSize;
                }
                return serializedSize + iComputeFieldSize;
            }
            Map.Entry arrayEntryAt = smallSortedMap.getArrayEntryAt(i20);
            iComputeFieldSize = FieldSet.computeFieldSize((GeneratedMessageLite.ExtensionDescriptor) arrayEntryAt.getKey(), arrayEntryAt.getValue()) + iComputeFieldSize;
            i20++;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:42:0x00d7 A[PHI: r3
      0x00d7: PHI (r3v32 int) = (r3v10 int), (r3v33 int) binds: [B:83:0x01f0, B:41:0x00d5] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // com.google.protobuf.Schema
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int hashCode(GeneratedMessageLite generatedMessageLite) {
        int i;
        int iHashLong;
        int i2;
        int[] iArr = this.buffer;
        int length = iArr.length;
        int i3 = 0;
        for (int i4 = 0; i4 < length; i4 += 3) {
            int iTypeAndOffsetAt = typeAndOffsetAt(i4);
            int i5 = iArr[i4];
            long j = 1048575 & iTypeAndOffsetAt;
            int i6 = 1237;
            int iHashCode = 37;
            switch (type(iTypeAndOffsetAt)) {
                case 0:
                    i = i3 * 53;
                    iHashLong = Internal.hashLong(Double.doubleToLongBits(UnsafeUtil.MEMORY_ACCESSOR.getDouble(j, generatedMessageLite)));
                    i3 = iHashLong + i;
                    break;
                case 1:
                    i = i3 * 53;
                    iHashLong = Float.floatToIntBits(UnsafeUtil.MEMORY_ACCESSOR.getFloat(j, generatedMessageLite));
                    i3 = iHashLong + i;
                    break;
                case 2:
                    i = i3 * 53;
                    iHashLong = Internal.hashLong(UnsafeUtil.getLong(j, generatedMessageLite));
                    i3 = iHashLong + i;
                    break;
                case 3:
                    i = i3 * 53;
                    iHashLong = Internal.hashLong(UnsafeUtil.getLong(j, generatedMessageLite));
                    i3 = iHashLong + i;
                    break;
                case 4:
                    i = i3 * 53;
                    iHashLong = UnsafeUtil.getInt(j, generatedMessageLite);
                    i3 = iHashLong + i;
                    break;
                case 5:
                    i = i3 * 53;
                    iHashLong = Internal.hashLong(UnsafeUtil.getLong(j, generatedMessageLite));
                    i3 = iHashLong + i;
                    break;
                case 6:
                    i = i3 * 53;
                    iHashLong = UnsafeUtil.getInt(j, generatedMessageLite);
                    i3 = iHashLong + i;
                    break;
                case 7:
                    i2 = i3 * 53;
                    boolean z = UnsafeUtil.MEMORY_ACCESSOR.getBoolean(j, generatedMessageLite);
                    Charset charset = Internal.UTF_8;
                    if (z) {
                        i6 = 1231;
                    }
                    i3 = i6 + i2;
                    break;
                case 8:
                    i = i3 * 53;
                    iHashLong = ((String) UnsafeUtil.getObject(j, generatedMessageLite)).hashCode();
                    i3 = iHashLong + i;
                    break;
                case 9:
                    Object object = UnsafeUtil.getObject(j, generatedMessageLite);
                    if (object != null) {
                        iHashCode = object.hashCode();
                    }
                    i3 = (i3 * 53) + iHashCode;
                    break;
                case 10:
                    i = i3 * 53;
                    iHashLong = UnsafeUtil.getObject(j, generatedMessageLite).hashCode();
                    i3 = iHashLong + i;
                    break;
                case 11:
                    i = i3 * 53;
                    iHashLong = UnsafeUtil.getInt(j, generatedMessageLite);
                    i3 = iHashLong + i;
                    break;
                case 12:
                    i = i3 * 53;
                    iHashLong = UnsafeUtil.getInt(j, generatedMessageLite);
                    i3 = iHashLong + i;
                    break;
                case 13:
                    i = i3 * 53;
                    iHashLong = UnsafeUtil.getInt(j, generatedMessageLite);
                    i3 = iHashLong + i;
                    break;
                case 14:
                    i = i3 * 53;
                    iHashLong = Internal.hashLong(UnsafeUtil.getLong(j, generatedMessageLite));
                    i3 = iHashLong + i;
                    break;
                case 15:
                    i = i3 * 53;
                    iHashLong = UnsafeUtil.getInt(j, generatedMessageLite);
                    i3 = iHashLong + i;
                    break;
                case 16:
                    i = i3 * 53;
                    iHashLong = Internal.hashLong(UnsafeUtil.getLong(j, generatedMessageLite));
                    i3 = iHashLong + i;
                    break;
                case 17:
                    Object object2 = UnsafeUtil.getObject(j, generatedMessageLite);
                    if (object2 != null) {
                        iHashCode = object2.hashCode();
                    }
                    i3 = (i3 * 53) + iHashCode;
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
                    i = i3 * 53;
                    iHashLong = UnsafeUtil.getObject(j, generatedMessageLite).hashCode();
                    i3 = iHashLong + i;
                    break;
                case 50:
                    i = i3 * 53;
                    iHashLong = UnsafeUtil.getObject(j, generatedMessageLite).hashCode();
                    i3 = iHashLong + i;
                    break;
                case 51:
                    if (isOneofPresent(i5, i4, generatedMessageLite)) {
                        i = i3 * 53;
                        iHashLong = Internal.hashLong(Double.doubleToLongBits(((Double) UnsafeUtil.getObject(j, generatedMessageLite)).doubleValue()));
                        i3 = iHashLong + i;
                        break;
                    } else {
                        break;
                    }
                case 52:
                    if (isOneofPresent(i5, i4, generatedMessageLite)) {
                        i = i3 * 53;
                        iHashLong = Float.floatToIntBits(((Float) UnsafeUtil.getObject(j, generatedMessageLite)).floatValue());
                        i3 = iHashLong + i;
                        break;
                    } else {
                        break;
                    }
                case 53:
                    if (isOneofPresent(i5, i4, generatedMessageLite)) {
                        i = i3 * 53;
                        iHashLong = Internal.hashLong(oneofLongAt(j, generatedMessageLite));
                        i3 = iHashLong + i;
                        break;
                    } else {
                        break;
                    }
                case 54:
                    if (isOneofPresent(i5, i4, generatedMessageLite)) {
                        i = i3 * 53;
                        iHashLong = Internal.hashLong(oneofLongAt(j, generatedMessageLite));
                        i3 = iHashLong + i;
                        break;
                    } else {
                        break;
                    }
                case 55:
                    if (isOneofPresent(i5, i4, generatedMessageLite)) {
                        i = i3 * 53;
                        iHashLong = oneofIntAt(j, generatedMessageLite);
                        i3 = iHashLong + i;
                        break;
                    } else {
                        break;
                    }
                case 56:
                    if (isOneofPresent(i5, i4, generatedMessageLite)) {
                        i = i3 * 53;
                        iHashLong = Internal.hashLong(oneofLongAt(j, generatedMessageLite));
                        i3 = iHashLong + i;
                        break;
                    } else {
                        break;
                    }
                case 57:
                    if (isOneofPresent(i5, i4, generatedMessageLite)) {
                        i = i3 * 53;
                        iHashLong = oneofIntAt(j, generatedMessageLite);
                        i3 = iHashLong + i;
                        break;
                    } else {
                        break;
                    }
                case 58:
                    if (isOneofPresent(i5, i4, generatedMessageLite)) {
                        i2 = i3 * 53;
                        boolean zBooleanValue = ((Boolean) UnsafeUtil.getObject(j, generatedMessageLite)).booleanValue();
                        Charset charset2 = Internal.UTF_8;
                        if (zBooleanValue) {
                        }
                        i3 = i6 + i2;
                        break;
                    } else {
                        break;
                    }
                    break;
                case 59:
                    if (isOneofPresent(i5, i4, generatedMessageLite)) {
                        i = i3 * 53;
                        iHashLong = ((String) UnsafeUtil.getObject(j, generatedMessageLite)).hashCode();
                        i3 = iHashLong + i;
                        break;
                    } else {
                        break;
                    }
                case 60:
                    if (isOneofPresent(i5, i4, generatedMessageLite)) {
                        i = i3 * 53;
                        iHashLong = UnsafeUtil.getObject(j, generatedMessageLite).hashCode();
                        i3 = iHashLong + i;
                        break;
                    } else {
                        break;
                    }
                case 61:
                    if (isOneofPresent(i5, i4, generatedMessageLite)) {
                        i = i3 * 53;
                        iHashLong = UnsafeUtil.getObject(j, generatedMessageLite).hashCode();
                        i3 = iHashLong + i;
                        break;
                    } else {
                        break;
                    }
                case 62:
                    if (isOneofPresent(i5, i4, generatedMessageLite)) {
                        i = i3 * 53;
                        iHashLong = oneofIntAt(j, generatedMessageLite);
                        i3 = iHashLong + i;
                        break;
                    } else {
                        break;
                    }
                case 63:
                    if (isOneofPresent(i5, i4, generatedMessageLite)) {
                        i = i3 * 53;
                        iHashLong = oneofIntAt(j, generatedMessageLite);
                        i3 = iHashLong + i;
                        break;
                    } else {
                        break;
                    }
                case 64:
                    if (isOneofPresent(i5, i4, generatedMessageLite)) {
                        i = i3 * 53;
                        iHashLong = oneofIntAt(j, generatedMessageLite);
                        i3 = iHashLong + i;
                        break;
                    } else {
                        break;
                    }
                case 65:
                    if (isOneofPresent(i5, i4, generatedMessageLite)) {
                        i = i3 * 53;
                        iHashLong = Internal.hashLong(oneofLongAt(j, generatedMessageLite));
                        i3 = iHashLong + i;
                        break;
                    } else {
                        break;
                    }
                case 66:
                    if (isOneofPresent(i5, i4, generatedMessageLite)) {
                        i = i3 * 53;
                        iHashLong = oneofIntAt(j, generatedMessageLite);
                        i3 = iHashLong + i;
                        break;
                    } else {
                        break;
                    }
                case 67:
                    if (isOneofPresent(i5, i4, generatedMessageLite)) {
                        i = i3 * 53;
                        iHashLong = Internal.hashLong(oneofLongAt(j, generatedMessageLite));
                        i3 = iHashLong + i;
                        break;
                    } else {
                        break;
                    }
                case 68:
                    if (isOneofPresent(i5, i4, generatedMessageLite)) {
                        i = i3 * 53;
                        iHashLong = UnsafeUtil.getObject(j, generatedMessageLite).hashCode();
                        i3 = iHashLong + i;
                        break;
                    } else {
                        break;
                    }
            }
        }
        int iHashCode2 = this.unknownFieldSchema.getFromMessage(generatedMessageLite).hashCode() + (i3 * 53);
        if (!this.hasExtensions) {
            return iHashCode2;
        }
        return this.extensionSchema.getExtensions(generatedMessageLite).fields.hashCode() + (iHashCode2 * 53);
    }

    public final boolean isFieldPresent(int i, Object obj) {
        int i2 = this.buffer[i + 2];
        long j = i2 & 1048575;
        if (j == 1048575) {
            int iTypeAndOffsetAt = typeAndOffsetAt(i);
            long j2 = iTypeAndOffsetAt & 1048575;
            switch (type(iTypeAndOffsetAt)) {
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

    /* JADX WARN: Removed duplicated region for block: B:20:0x0045  */
    @Override // com.google.protobuf.Schema
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean isInitialized(Object obj) {
        int i = 1048575;
        int i2 = 0;
        int i3 = 0;
        loop0: while (true) {
            boolean zIsFieldPresent = true;
            if (i2 < this.checkInitializedCount) {
                int i4 = this.intArray[i2];
                int[] iArr = this.buffer;
                int i5 = iArr[i4];
                int iTypeAndOffsetAt = typeAndOffsetAt(i4);
                int i6 = iArr[i4 + 2];
                int i7 = i6 & 1048575;
                int i8 = 1 << (i6 >>> 20);
                if (i7 != i) {
                    if (i7 != 1048575) {
                        i3 = UNSAFE.getInt(obj, i7);
                    }
                    i = i7;
                }
                if ((268435456 & iTypeAndOffsetAt) != 0) {
                    if (!(i == 1048575 ? isFieldPresent(i4, obj) : (i3 & i8) != 0)) {
                        break;
                    }
                } else {
                    int iType = type(iTypeAndOffsetAt);
                    if (iType == 9 || iType == 17) {
                        if (i == 1048575) {
                            zIsFieldPresent = isFieldPresent(i4, obj);
                        } else if ((i8 & i3) == 0) {
                            zIsFieldPresent = false;
                        }
                        if (zIsFieldPresent && !getMessageFieldSchema(i4).isInitialized(UnsafeUtil.getObject(iTypeAndOffsetAt & 1048575, obj))) {
                            break;
                        }
                        i2++;
                    } else {
                        if (iType != 27) {
                            if (iType == 60 || iType == 68) {
                                if (isOneofPresent(i5, i4, obj) && !getMessageFieldSchema(i4).isInitialized(UnsafeUtil.getObject(iTypeAndOffsetAt & 1048575, obj))) {
                                    break;
                                }
                                i2++;
                            } else if (iType != 49) {
                                if (iType != 50) {
                                    continue;
                                } else {
                                    Object object = UnsafeUtil.getObject(iTypeAndOffsetAt & 1048575, obj);
                                    ((MapFieldSchemaLite) this.mapFieldSchema).getClass();
                                    MapFieldLite mapFieldLite = (MapFieldLite) object;
                                    if (!mapFieldLite.isEmpty() && ((MapEntryLite) getMapFieldDefaultEntry(i4)).metadata.valueType.getJavaType() == WireFormat$JavaType.MESSAGE) {
                                        Schema schemaSchemaFor = null;
                                        for (Object obj2 : mapFieldLite.values()) {
                                            if (schemaSchemaFor == null) {
                                                schemaSchemaFor = Protobuf.INSTANCE.schemaFor((Class) obj2.getClass());
                                            }
                                            if (!schemaSchemaFor.isInitialized(obj2)) {
                                                break loop0;
                                            }
                                        }
                                    }
                                }
                                i2++;
                            }
                        }
                        List list = (List) UnsafeUtil.getObject(iTypeAndOffsetAt & 1048575, obj);
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

    /* JADX WARN: Removed duplicated region for block: B:18:0x0054  */
    @Override // com.google.protobuf.Schema
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
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
                int iTypeAndOffsetAt = typeAndOffsetAt(i);
                long j = 1048575 & iTypeAndOffsetAt;
                int iType = type(iTypeAndOffsetAt);
                if (iType != 9) {
                    switch (iType) {
                        case 17:
                            if (isFieldPresent(i, obj)) {
                                getMessageFieldSchema(i).makeImmutable(UNSAFE.getObject(obj, j));
                                break;
                            } else {
                                break;
                            }
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
            }
            this.unknownFieldSchema.makeImmutable(obj);
            if (this.hasExtensions) {
                this.extensionSchema.makeImmutable(obj);
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0022  */
    @Override // com.google.protobuf.Schema
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
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
            int iTypeAndOffsetAt = typeAndOffsetAt(i);
            long j = 1048575 & iTypeAndOffsetAt;
            int i2 = iArr[i];
            switch (type(iTypeAndOffsetAt)) {
                case 0:
                    if (!isFieldPresent(i, obj2)) {
                        obj3 = obj;
                        break;
                    } else {
                        UnsafeUtil.MemoryAccessor memoryAccessor = UnsafeUtil.MEMORY_ACCESSOR;
                        obj3 = obj;
                        memoryAccessor.putDouble(obj3, j, memoryAccessor.getDouble(j, obj2));
                        setFieldPresent(i, obj3);
                        break;
                    }
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
            }
            i += 3;
            obj = obj3;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:272:0x0883 A[Catch: all -> 0x0889, TryCatch #32 {all -> 0x0889, blocks: (B:270:0x087e, B:272:0x0883, B:275:0x088c), top: B:322:0x087e }] */
    /* JADX WARN: Removed duplicated region for block: B:291:0x08c6 A[LOOP:2: B:290:0x08c4->B:291:0x08c6, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:294:0x08d9  */
    /* JADX WARN: Removed duplicated region for block: B:362:0x00af A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:365:0x0892 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00aa  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void mergeFromHelper(UnknownFieldSchema unknownFieldSchema, ExtensionSchema extensionSchema, Object obj, CodedInputStreamReader codedInputStreamReader, ExtensionRegistryLite extensionRegistryLite) throws Throwable {
        UnknownFieldSchema unknownFieldSchema2;
        Object obj2;
        int i;
        Object objFilterMapUnknownEnumValues;
        int i2;
        int fieldNumber;
        Object builderFromMessage;
        int iSlowPositionForFieldNumber;
        Object obj3;
        ExtensionSchema extensionSchema2;
        GeneratedMessageLite.GeneratedExtension generatedExtensionFindExtensionByNumber;
        UnknownFieldSchema unknownFieldSchema3;
        MessageSchema messageSchema;
        UnknownFieldSchema unknownFieldSchema4;
        CodedInputStreamReader codedInputStreamReader2;
        ExtensionRegistryLite extensionRegistryLite2;
        int iType;
        CodedInputStream codedInputStream;
        ListFieldSchema listFieldSchema;
        Object obj4;
        CodedInputStreamReader codedInputStreamReader3;
        MessageSchema messageSchema2;
        Object obj5;
        MessageSchema messageSchema3 = this;
        ExtensionRegistryLite extensionRegistryLite3 = extensionRegistryLite;
        int[] iArr = messageSchema3.intArray;
        int i3 = messageSchema3.repeatedFieldOffsetStart;
        int i4 = messageSchema3.checkInitializedCount;
        Object extension = null;
        FieldSet mutableExtensions = null;
        while (true) {
            try {
                fieldNumber = codedInputStreamReader.getFieldNumber();
                try {
                    iSlowPositionForFieldNumber = (fieldNumber < messageSchema3.minFieldNumber || fieldNumber > messageSchema3.maxFieldNumber) ? -1 : messageSchema3.slowPositionForFieldNumber(fieldNumber, 0);
                } catch (Throwable th) {
                    th = th;
                    unknownFieldSchema2 = unknownFieldSchema;
                    obj2 = obj;
                    builderFromMessage = extension;
                }
            } catch (Throwable th2) {
                th = th2;
                unknownFieldSchema2 = unknownFieldSchema;
                obj2 = obj;
            }
            if (iSlowPositionForFieldNumber < 0) {
                if (fieldNumber == Integer.MAX_VALUE) {
                    Object objFilterMapUnknownEnumValues2 = extension;
                    while (i4 < i3) {
                        objFilterMapUnknownEnumValues2 = messageSchema3.filterMapUnknownEnumValues(obj, iArr[i4], objFilterMapUnknownEnumValues2, unknownFieldSchema, obj);
                        i4++;
                        messageSchema3 = messageSchema3;
                    }
                    if (objFilterMapUnknownEnumValues2 != null) {
                        unknownFieldSchema.setBuilderToMessage(obj, objFilterMapUnknownEnumValues2);
                        return;
                    }
                    return;
                }
                unknownFieldSchema2 = unknownFieldSchema;
                MessageSchema messageSchema4 = messageSchema3;
                try {
                    if (messageSchema4.hasExtensions) {
                        extensionSchema2 = extensionSchema;
                        generatedExtensionFindExtensionByNumber = extensionSchema2.findExtensionByNumber(extensionRegistryLite3, messageSchema4.defaultInstance, fieldNumber);
                    } else {
                        extensionSchema2 = extensionSchema;
                        generatedExtensionFindExtensionByNumber = null;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    obj3 = obj;
                    builderFromMessage = extension;
                }
                if (generatedExtensionFindExtensionByNumber == null) {
                    obj2 = obj;
                    builderFromMessage = extension;
                    unknownFieldSchema3 = unknownFieldSchema2;
                    ExtensionRegistryLite extensionRegistryLite4 = extensionRegistryLite3;
                    try {
                        unknownFieldSchema3.getClass();
                        if (builderFromMessage == null) {
                            try {
                                builderFromMessage = unknownFieldSchema3.getBuilderFromMessage(obj2);
                            } catch (Throwable th4) {
                                th = th4;
                                unknownFieldSchema2 = unknownFieldSchema3;
                                i = i4;
                                extension = builderFromMessage;
                                objFilterMapUnknownEnumValues = extension;
                                i2 = i;
                                while (i2 < i3) {
                                }
                                UnknownFieldSchema unknownFieldSchema5 = unknownFieldSchema2;
                                if (objFilterMapUnknownEnumValues != null) {
                                }
                                throw th;
                            }
                            try {
                                if (unknownFieldSchema3.mergeOneFieldFrom(builderFromMessage, codedInputStreamReader)) {
                                    Object objFilterMapUnknownEnumValues3 = builderFromMessage;
                                    while (i4 < i3) {
                                        objFilterMapUnknownEnumValues3 = messageSchema4.filterMapUnknownEnumValues(obj2, iArr[i4], objFilterMapUnknownEnumValues3, unknownFieldSchema3, obj);
                                        i4++;
                                    }
                                    if (objFilterMapUnknownEnumValues3 != null) {
                                        unknownFieldSchema3.setBuilderToMessage(obj2, objFilterMapUnknownEnumValues3);
                                        return;
                                    }
                                    return;
                                }
                                extensionRegistryLite3 = extensionRegistryLite4;
                                messageSchema3 = messageSchema4;
                                extension = builderFromMessage;
                            } catch (Throwable th5) {
                                th = th5;
                                unknownFieldSchema2 = unknownFieldSchema3;
                                i = i4;
                                extension = builderFromMessage;
                                objFilterMapUnknownEnumValues = extension;
                                i2 = i;
                                while (i2 < i3) {
                                }
                                UnknownFieldSchema unknownFieldSchema52 = unknownFieldSchema2;
                                if (objFilterMapUnknownEnumValues != null) {
                                }
                                throw th;
                            }
                        } else if (unknownFieldSchema3.mergeOneFieldFrom(builderFromMessage, codedInputStreamReader)) {
                        }
                    } catch (Throwable th6) {
                        th = th6;
                        obj3 = obj2;
                        unknownFieldSchema2 = unknownFieldSchema3;
                        obj2 = obj3;
                        i = i4;
                        extension = builderFromMessage;
                        objFilterMapUnknownEnumValues = extension;
                        i2 = i;
                        while (i2 < i3) {
                        }
                        UnknownFieldSchema unknownFieldSchema522 = unknownFieldSchema2;
                        if (objFilterMapUnknownEnumValues != null) {
                        }
                        throw th;
                    }
                    objFilterMapUnknownEnumValues = extension;
                    i2 = i;
                    while (i2 < i3) {
                        objFilterMapUnknownEnumValues = filterMapUnknownEnumValues(obj2, iArr[i2], objFilterMapUnknownEnumValues, unknownFieldSchema, obj);
                        i2++;
                        unknownFieldSchema2 = unknownFieldSchema;
                    }
                    UnknownFieldSchema unknownFieldSchema5222 = unknownFieldSchema2;
                    if (objFilterMapUnknownEnumValues != null) {
                        unknownFieldSchema5222.setBuilderToMessage(obj2, objFilterMapUnknownEnumValues);
                    }
                    throw th;
                }
                if (mutableExtensions == null) {
                    try {
                        mutableExtensions = extensionSchema.getMutableExtensions(obj);
                    } catch (Throwable th7) {
                        th = th7;
                        obj2 = obj;
                        i = i4;
                        objFilterMapUnknownEnumValues = extension;
                        i2 = i;
                        while (i2 < i3) {
                        }
                        UnknownFieldSchema unknownFieldSchema52222 = unknownFieldSchema2;
                        if (objFilterMapUnknownEnumValues != null) {
                        }
                        throw th;
                    }
                }
                Object obj6 = extension;
                FieldSet fieldSet = mutableExtensions;
                try {
                    mutableExtensions = fieldSet;
                    extension = extensionSchema2.parseExtension(obj, codedInputStreamReader, generatedExtensionFindExtensionByNumber, extensionRegistryLite3, fieldSet, obj6, unknownFieldSchema2);
                    extensionRegistryLite3 = extensionRegistryLite3;
                    messageSchema3 = messageSchema4;
                } catch (Throwable th8) {
                    th = th8;
                    obj2 = obj;
                    builderFromMessage = obj6;
                    unknownFieldSchema3 = unknownFieldSchema2;
                    unknownFieldSchema2 = unknownFieldSchema3;
                    i = i4;
                    extension = builderFromMessage;
                    objFilterMapUnknownEnumValues = extension;
                    i2 = i;
                    while (i2 < i3) {
                    }
                    UnknownFieldSchema unknownFieldSchema522222 = unknownFieldSchema2;
                    if (objFilterMapUnknownEnumValues != null) {
                    }
                    throw th;
                }
                i = i4;
                extension = builderFromMessage;
                objFilterMapUnknownEnumValues = extension;
                i2 = i;
                while (i2 < i3) {
                }
                UnknownFieldSchema unknownFieldSchema5222222 = unknownFieldSchema2;
                if (objFilterMapUnknownEnumValues != null) {
                }
                throw th;
            }
            obj3 = obj;
            ExtensionRegistryLite extensionRegistryLite5 = extensionRegistryLite3;
            builderFromMessage = extension;
            unknownFieldSchema3 = unknownFieldSchema;
            try {
                int iTypeAndOffsetAt = messageSchema3.typeAndOffsetAt(iSlowPositionForFieldNumber);
                try {
                    iType = type(iTypeAndOffsetAt);
                    codedInputStream = codedInputStreamReader.input;
                    listFieldSchema = messageSchema3.listFieldSchema;
                } catch (InvalidProtocolBufferException.InvalidWireTypeException unused) {
                    messageSchema = messageSchema3;
                    unknownFieldSchema4 = unknownFieldSchema3;
                    obj2 = obj3;
                    i = i4;
                } catch (Throwable th9) {
                    th = th9;
                }
                switch (iType) {
                    case 0:
                        unknownFieldSchema4 = unknownFieldSchema3;
                        i = i4;
                        codedInputStreamReader2 = codedInputStreamReader;
                        extensionRegistryLite2 = extensionRegistryLite5;
                        int i5 = iSlowPositionForFieldNumber;
                        messageSchema = messageSchema3;
                        long jOffset = offset(iTypeAndOffsetAt);
                        codedInputStreamReader2.requireWireType(1);
                        try {
                            obj4 = obj;
                        } catch (InvalidProtocolBufferException.InvalidWireTypeException unused2) {
                            obj2 = obj;
                        }
                        try {
                            UnsafeUtil.MEMORY_ACCESSOR.putDouble(obj4, jOffset, codedInputStream.readDouble());
                            messageSchema.setFieldPresent(i5, obj4);
                            extension = builderFromMessage;
                        } catch (InvalidProtocolBufferException.InvalidWireTypeException unused3) {
                            obj2 = obj4;
                            extension = builderFromMessage;
                            try {
                                unknownFieldSchema4.getClass();
                                if (extension == null) {
                                }
                                if (!unknownFieldSchema4.mergeOneFieldFrom(extension, codedInputStreamReader2)) {
                                }
                                messageSchema3 = this;
                                extensionRegistryLite3 = extensionRegistryLite2;
                                i4 = i;
                            } catch (Throwable th10) {
                                th = th10;
                                unknownFieldSchema2 = unknownFieldSchema4;
                                objFilterMapUnknownEnumValues = extension;
                                i2 = i;
                                while (i2 < i3) {
                                }
                                UnknownFieldSchema unknownFieldSchema52222222 = unknownFieldSchema2;
                                if (objFilterMapUnknownEnumValues != null) {
                                }
                                throw th;
                            }
                        } catch (Throwable th11) {
                            th = th11;
                            obj2 = obj4;
                            extension = builderFromMessage;
                            unknownFieldSchema2 = unknownFieldSchema4;
                            objFilterMapUnknownEnumValues = extension;
                            i2 = i;
                            while (i2 < i3) {
                            }
                            UnknownFieldSchema unknownFieldSchema522222222 = unknownFieldSchema2;
                            if (objFilterMapUnknownEnumValues != null) {
                            }
                            throw th;
                        }
                        messageSchema3 = this;
                        extensionRegistryLite3 = extensionRegistryLite2;
                        i4 = i;
                        break;
                    case 1:
                        unknownFieldSchema4 = unknownFieldSchema3;
                        i = i4;
                        extensionRegistryLite2 = extensionRegistryLite5;
                        int i6 = iSlowPositionForFieldNumber;
                        long jOffset2 = offset(iTypeAndOffsetAt);
                        codedInputStreamReader.requireWireType(5);
                        UnsafeUtil.MEMORY_ACCESSOR.putFloat(obj3, jOffset2, codedInputStream.readFloat());
                        messageSchema3.setFieldPresent(i6, obj3);
                        extension = builderFromMessage;
                        messageSchema3 = this;
                        extensionRegistryLite3 = extensionRegistryLite2;
                        i4 = i;
                    case 2:
                        unknownFieldSchema4 = unknownFieldSchema3;
                        i = i4;
                        extensionRegistryLite2 = extensionRegistryLite5;
                        int i7 = iSlowPositionForFieldNumber;
                        long jOffset3 = offset(iTypeAndOffsetAt);
                        codedInputStreamReader.requireWireType(0);
                        UnsafeUtil.putLong(obj3, jOffset3, codedInputStream.readInt64());
                        messageSchema3.setFieldPresent(i7, obj3);
                        extension = builderFromMessage;
                        messageSchema3 = this;
                        extensionRegistryLite3 = extensionRegistryLite2;
                        i4 = i;
                    case 3:
                        unknownFieldSchema4 = unknownFieldSchema3;
                        i = i4;
                        extensionRegistryLite2 = extensionRegistryLite5;
                        int i8 = iSlowPositionForFieldNumber;
                        long jOffset4 = offset(iTypeAndOffsetAt);
                        codedInputStreamReader.requireWireType(0);
                        UnsafeUtil.putLong(obj3, jOffset4, codedInputStream.readUInt64());
                        messageSchema3.setFieldPresent(i8, obj3);
                        extension = builderFromMessage;
                        messageSchema3 = this;
                        extensionRegistryLite3 = extensionRegistryLite2;
                        i4 = i;
                    case 4:
                        unknownFieldSchema4 = unknownFieldSchema3;
                        i = i4;
                        extensionRegistryLite2 = extensionRegistryLite5;
                        int i9 = iSlowPositionForFieldNumber;
                        long jOffset5 = offset(iTypeAndOffsetAt);
                        codedInputStreamReader.requireWireType(0);
                        UnsafeUtil.putInt(codedInputStream.readInt32(), jOffset5, obj3);
                        messageSchema3.setFieldPresent(i9, obj3);
                        extension = builderFromMessage;
                        messageSchema3 = this;
                        extensionRegistryLite3 = extensionRegistryLite2;
                        i4 = i;
                    case 5:
                        unknownFieldSchema4 = unknownFieldSchema3;
                        i = i4;
                        extensionRegistryLite2 = extensionRegistryLite5;
                        int i10 = iSlowPositionForFieldNumber;
                        long jOffset6 = offset(iTypeAndOffsetAt);
                        codedInputStreamReader.requireWireType(1);
                        UnsafeUtil.putLong(obj3, jOffset6, codedInputStream.readFixed64());
                        messageSchema3.setFieldPresent(i10, obj3);
                        extension = builderFromMessage;
                        messageSchema3 = this;
                        extensionRegistryLite3 = extensionRegistryLite2;
                        i4 = i;
                    case 6:
                        unknownFieldSchema4 = unknownFieldSchema3;
                        i = i4;
                        extensionRegistryLite2 = extensionRegistryLite5;
                        int i11 = iSlowPositionForFieldNumber;
                        long jOffset7 = offset(iTypeAndOffsetAt);
                        codedInputStreamReader.requireWireType(5);
                        UnsafeUtil.putInt(codedInputStream.readFixed32(), jOffset7, obj3);
                        messageSchema3.setFieldPresent(i11, obj3);
                        extension = builderFromMessage;
                        messageSchema3 = this;
                        extensionRegistryLite3 = extensionRegistryLite2;
                        i4 = i;
                    case 7:
                        unknownFieldSchema4 = unknownFieldSchema3;
                        i = i4;
                        extensionRegistryLite2 = extensionRegistryLite5;
                        int i12 = iSlowPositionForFieldNumber;
                        long jOffset8 = offset(iTypeAndOffsetAt);
                        codedInputStreamReader.requireWireType(0);
                        UnsafeUtil.MEMORY_ACCESSOR.putBoolean(obj3, jOffset8, codedInputStream.readBool());
                        messageSchema3.setFieldPresent(i12, obj3);
                        extension = builderFromMessage;
                        messageSchema3 = this;
                        extensionRegistryLite3 = extensionRegistryLite2;
                        i4 = i;
                    case 8:
                        unknownFieldSchema4 = unknownFieldSchema3;
                        i = i4;
                        extensionRegistryLite2 = extensionRegistryLite5;
                        MessageSchema messageSchema5 = messageSchema3;
                        messageSchema5.readString(obj3, iTypeAndOffsetAt, codedInputStreamReader);
                        messageSchema5.setFieldPresent(iSlowPositionForFieldNumber, obj3);
                        extension = builderFromMessage;
                        messageSchema3 = this;
                        extensionRegistryLite3 = extensionRegistryLite2;
                        i4 = i;
                    case 9:
                        MessageSchema messageSchema6 = messageSchema3;
                        unknownFieldSchema4 = unknownFieldSchema3;
                        i = i4;
                        extensionRegistryLite2 = extensionRegistryLite5;
                        int i13 = iSlowPositionForFieldNumber;
                        MessageLite messageLite = (MessageLite) messageSchema6.mutableMessageFieldForMerge(i13, obj3);
                        Schema messageFieldSchema = messageSchema6.getMessageFieldSchema(i13);
                        codedInputStreamReader.requireWireType(2);
                        codedInputStreamReader.mergeMessageFieldInternal(messageLite, messageFieldSchema, extensionRegistryLite2);
                        messageSchema6.storeMessageField(i13, obj3, messageLite);
                        extension = builderFromMessage;
                        messageSchema3 = this;
                        extensionRegistryLite3 = extensionRegistryLite2;
                        i4 = i;
                    case 10:
                        unknownFieldSchema4 = unknownFieldSchema3;
                        i = i4;
                        extensionRegistryLite2 = extensionRegistryLite5;
                        UnsafeUtil.putObject(offset(iTypeAndOffsetAt), obj3, codedInputStreamReader.readBytes());
                        messageSchema3.setFieldPresent(iSlowPositionForFieldNumber, obj3);
                        extension = builderFromMessage;
                        messageSchema3 = this;
                        extensionRegistryLite3 = extensionRegistryLite2;
                        i4 = i;
                    case 11:
                        unknownFieldSchema4 = unknownFieldSchema3;
                        i = i4;
                        extensionRegistryLite2 = extensionRegistryLite5;
                        int i14 = iSlowPositionForFieldNumber;
                        long jOffset9 = offset(iTypeAndOffsetAt);
                        codedInputStreamReader.requireWireType(0);
                        UnsafeUtil.putInt(codedInputStream.readUInt32(), jOffset9, obj3);
                        messageSchema3.setFieldPresent(i14, obj3);
                        extension = builderFromMessage;
                        messageSchema3 = this;
                        extensionRegistryLite3 = extensionRegistryLite2;
                        i4 = i;
                    case 12:
                        unknownFieldSchema4 = unknownFieldSchema3;
                        i = i4;
                        extensionRegistryLite2 = extensionRegistryLite5;
                        int i15 = iSlowPositionForFieldNumber;
                        MessageSchema messageSchema7 = messageSchema3;
                        codedInputStreamReader.requireWireType(0);
                        int i16 = codedInputStream.readEnum();
                        Internal.EnumVerifier enumFieldVerifier = messageSchema7.getEnumFieldVerifier(i15);
                        if (enumFieldVerifier != null && !enumFieldVerifier.isInRange(i16)) {
                            extension = SchemaUtil.storeUnknownEnum(obj3, fieldNumber, i16, builderFromMessage, unknownFieldSchema4);
                            messageSchema3 = this;
                            extensionRegistryLite3 = extensionRegistryLite2;
                            i4 = i;
                        }
                        UnsafeUtil.putInt(i16, offset(iTypeAndOffsetAt), obj3);
                        messageSchema7.setFieldPresent(i15, obj3);
                        extension = builderFromMessage;
                        messageSchema3 = this;
                        extensionRegistryLite3 = extensionRegistryLite2;
                        i4 = i;
                        break;
                    case 13:
                        unknownFieldSchema4 = unknownFieldSchema3;
                        i = i4;
                        extensionRegistryLite2 = extensionRegistryLite5;
                        int i17 = iSlowPositionForFieldNumber;
                        long jOffset10 = offset(iTypeAndOffsetAt);
                        codedInputStreamReader.requireWireType(5);
                        UnsafeUtil.putInt(codedInputStream.readSFixed32(), jOffset10, obj3);
                        messageSchema3.setFieldPresent(i17, obj3);
                        extension = builderFromMessage;
                        messageSchema3 = this;
                        extensionRegistryLite3 = extensionRegistryLite2;
                        i4 = i;
                    case 14:
                        unknownFieldSchema4 = unknownFieldSchema3;
                        i = i4;
                        extensionRegistryLite2 = extensionRegistryLite5;
                        int i18 = iSlowPositionForFieldNumber;
                        long jOffset11 = offset(iTypeAndOffsetAt);
                        codedInputStreamReader.requireWireType(1);
                        UnsafeUtil.putLong(obj3, jOffset11, codedInputStream.readSFixed64());
                        messageSchema3.setFieldPresent(i18, obj3);
                        extension = builderFromMessage;
                        messageSchema3 = this;
                        extensionRegistryLite3 = extensionRegistryLite2;
                        i4 = i;
                    case 15:
                        unknownFieldSchema4 = unknownFieldSchema3;
                        i = i4;
                        extensionRegistryLite2 = extensionRegistryLite5;
                        int i19 = iSlowPositionForFieldNumber;
                        long jOffset12 = offset(iTypeAndOffsetAt);
                        codedInputStreamReader.requireWireType(0);
                        UnsafeUtil.putInt(codedInputStream.readSInt32(), jOffset12, obj3);
                        messageSchema3.setFieldPresent(i19, obj3);
                        extension = builderFromMessage;
                        messageSchema3 = this;
                        extensionRegistryLite3 = extensionRegistryLite2;
                        i4 = i;
                    case 16:
                        unknownFieldSchema4 = unknownFieldSchema3;
                        i = i4;
                        extensionRegistryLite2 = extensionRegistryLite5;
                        int i20 = iSlowPositionForFieldNumber;
                        long jOffset13 = offset(iTypeAndOffsetAt);
                        codedInputStreamReader.requireWireType(0);
                        UnsafeUtil.putLong(obj3, jOffset13, codedInputStream.readSInt64());
                        messageSchema3.setFieldPresent(i20, obj3);
                        extension = builderFromMessage;
                        messageSchema3 = this;
                        extensionRegistryLite3 = extensionRegistryLite2;
                        i4 = i;
                    case 17:
                        MessageSchema messageSchema8 = messageSchema3;
                        unknownFieldSchema4 = unknownFieldSchema3;
                        i = i4;
                        extensionRegistryLite2 = extensionRegistryLite5;
                        int i21 = iSlowPositionForFieldNumber;
                        MessageLite messageLite2 = (MessageLite) messageSchema8.mutableMessageFieldForMerge(i21, obj3);
                        Schema messageFieldSchema2 = messageSchema8.getMessageFieldSchema(i21);
                        codedInputStreamReader.requireWireType(3);
                        codedInputStreamReader.mergeGroupFieldInternal(messageLite2, messageFieldSchema2, extensionRegistryLite2);
                        messageSchema8.storeMessageField(i21, obj3, messageLite2);
                        extension = builderFromMessage;
                        messageSchema3 = this;
                        extensionRegistryLite3 = extensionRegistryLite2;
                        i4 = i;
                    case 18:
                        unknownFieldSchema4 = unknownFieldSchema3;
                        i = i4;
                        extensionRegistryLite2 = extensionRegistryLite5;
                        codedInputStreamReader.readDoubleList(listFieldSchema.mutableListAt(offset(iTypeAndOffsetAt), obj3));
                        extension = builderFromMessage;
                        messageSchema3 = this;
                        extensionRegistryLite3 = extensionRegistryLite2;
                        i4 = i;
                    case 19:
                        unknownFieldSchema4 = unknownFieldSchema3;
                        i = i4;
                        extensionRegistryLite2 = extensionRegistryLite5;
                        codedInputStreamReader.readFloatList(listFieldSchema.mutableListAt(offset(iTypeAndOffsetAt), obj3));
                        extension = builderFromMessage;
                        messageSchema3 = this;
                        extensionRegistryLite3 = extensionRegistryLite2;
                        i4 = i;
                    case 20:
                        unknownFieldSchema4 = unknownFieldSchema3;
                        i = i4;
                        extensionRegistryLite2 = extensionRegistryLite5;
                        codedInputStreamReader.readInt64List(listFieldSchema.mutableListAt(offset(iTypeAndOffsetAt), obj3));
                        extension = builderFromMessage;
                        messageSchema3 = this;
                        extensionRegistryLite3 = extensionRegistryLite2;
                        i4 = i;
                    case 21:
                        unknownFieldSchema4 = unknownFieldSchema3;
                        i = i4;
                        extensionRegistryLite2 = extensionRegistryLite5;
                        codedInputStreamReader.readUInt64List(listFieldSchema.mutableListAt(offset(iTypeAndOffsetAt), obj3));
                        extension = builderFromMessage;
                        messageSchema3 = this;
                        extensionRegistryLite3 = extensionRegistryLite2;
                        i4 = i;
                    case 22:
                        unknownFieldSchema4 = unknownFieldSchema3;
                        i = i4;
                        extensionRegistryLite2 = extensionRegistryLite5;
                        codedInputStreamReader.readInt32List(listFieldSchema.mutableListAt(offset(iTypeAndOffsetAt), obj3));
                        extension = builderFromMessage;
                        messageSchema3 = this;
                        extensionRegistryLite3 = extensionRegistryLite2;
                        i4 = i;
                    case 23:
                        unknownFieldSchema4 = unknownFieldSchema3;
                        i = i4;
                        extensionRegistryLite2 = extensionRegistryLite5;
                        codedInputStreamReader.readFixed64List(listFieldSchema.mutableListAt(offset(iTypeAndOffsetAt), obj3));
                        extension = builderFromMessage;
                        messageSchema3 = this;
                        extensionRegistryLite3 = extensionRegistryLite2;
                        i4 = i;
                    case 24:
                        unknownFieldSchema4 = unknownFieldSchema3;
                        i = i4;
                        extensionRegistryLite2 = extensionRegistryLite5;
                        codedInputStreamReader.readFixed32List(listFieldSchema.mutableListAt(offset(iTypeAndOffsetAt), obj3));
                        extension = builderFromMessage;
                        messageSchema3 = this;
                        extensionRegistryLite3 = extensionRegistryLite2;
                        i4 = i;
                    case 25:
                        unknownFieldSchema4 = unknownFieldSchema3;
                        i = i4;
                        extensionRegistryLite2 = extensionRegistryLite5;
                        codedInputStreamReader.readBoolList(listFieldSchema.mutableListAt(offset(iTypeAndOffsetAt), obj3));
                        extension = builderFromMessage;
                        messageSchema3 = this;
                        extensionRegistryLite3 = extensionRegistryLite2;
                        i4 = i;
                    case 26:
                        unknownFieldSchema4 = unknownFieldSchema3;
                        obj2 = obj3;
                        i = i4;
                        messageSchema = messageSchema3;
                        codedInputStreamReader2 = codedInputStreamReader;
                        extensionRegistryLite2 = extensionRegistryLite5;
                        try {
                            messageSchema.readStringList(obj2, iTypeAndOffsetAt, codedInputStreamReader2);
                            extension = builderFromMessage;
                        } catch (InvalidProtocolBufferException.InvalidWireTypeException unused4) {
                            extension = builderFromMessage;
                            unknownFieldSchema4.getClass();
                            if (extension == null) {
                            }
                            if (!unknownFieldSchema4.mergeOneFieldFrom(extension, codedInputStreamReader2)) {
                            }
                            messageSchema3 = this;
                            extensionRegistryLite3 = extensionRegistryLite2;
                            i4 = i;
                        }
                        messageSchema3 = this;
                        extensionRegistryLite3 = extensionRegistryLite2;
                        i4 = i;
                        break;
                    case 27:
                        unknownFieldSchema4 = unknownFieldSchema3;
                        obj2 = obj3;
                        i = i4;
                        MessageSchema messageSchema9 = messageSchema3;
                        try {
                            extensionRegistryLite5 = extensionRegistryLite;
                            try {
                                messageSchema3.readMessageList(obj2, iTypeAndOffsetAt, codedInputStreamReader, messageSchema9.getMessageFieldSchema(iSlowPositionForFieldNumber), extensionRegistryLite5);
                                extensionRegistryLite2 = extensionRegistryLite5;
                                extension = builderFromMessage;
                            } catch (InvalidProtocolBufferException.InvalidWireTypeException unused5) {
                                messageSchema = messageSchema3;
                                codedInputStreamReader2 = codedInputStreamReader;
                                extensionRegistryLite2 = extensionRegistryLite5;
                                extension = builderFromMessage;
                                unknownFieldSchema4.getClass();
                                if (extension == null) {
                                }
                                if (!unknownFieldSchema4.mergeOneFieldFrom(extension, codedInputStreamReader2)) {
                                }
                                messageSchema3 = this;
                                extensionRegistryLite3 = extensionRegistryLite2;
                                i4 = i;
                            } catch (Throwable th12) {
                                th = th12;
                                extension = builderFromMessage;
                                unknownFieldSchema2 = unknownFieldSchema4;
                                objFilterMapUnknownEnumValues = extension;
                                i2 = i;
                                while (i2 < i3) {
                                }
                                UnknownFieldSchema unknownFieldSchema5222222222 = unknownFieldSchema2;
                                if (objFilterMapUnknownEnumValues != null) {
                                }
                                throw th;
                            }
                        } catch (InvalidProtocolBufferException.InvalidWireTypeException unused6) {
                            extensionRegistryLite2 = extensionRegistryLite;
                            messageSchema = messageSchema9;
                            codedInputStreamReader2 = codedInputStreamReader;
                            extension = builderFromMessage;
                            unknownFieldSchema4.getClass();
                            if (extension == null) {
                            }
                            if (!unknownFieldSchema4.mergeOneFieldFrom(extension, codedInputStreamReader2)) {
                            }
                            messageSchema3 = this;
                            extensionRegistryLite3 = extensionRegistryLite2;
                            i4 = i;
                        } catch (Throwable th13) {
                            th = th13;
                        }
                        messageSchema3 = this;
                        extensionRegistryLite3 = extensionRegistryLite2;
                        i4 = i;
                        break;
                    case 28:
                        codedInputStreamReader3 = codedInputStreamReader;
                        unknownFieldSchema4 = unknownFieldSchema3;
                        i = i4;
                        messageSchema2 = messageSchema3;
                        codedInputStreamReader3.readBytesList(listFieldSchema.mutableListAt(offset(iTypeAndOffsetAt), obj3));
                        extensionRegistryLite2 = extensionRegistryLite;
                        extension = builderFromMessage;
                        messageSchema3 = this;
                        extensionRegistryLite3 = extensionRegistryLite2;
                        i4 = i;
                    case 29:
                        codedInputStreamReader3 = codedInputStreamReader;
                        unknownFieldSchema4 = unknownFieldSchema3;
                        obj2 = obj3;
                        i = i4;
                        messageSchema2 = messageSchema3;
                        try {
                            try {
                                codedInputStreamReader3.readUInt32List(listFieldSchema.mutableListAt(offset(iTypeAndOffsetAt), obj2));
                                extensionRegistryLite2 = extensionRegistryLite;
                                extension = builderFromMessage;
                            } catch (InvalidProtocolBufferException.InvalidWireTypeException unused7) {
                                extensionRegistryLite2 = extensionRegistryLite;
                                messageSchema = messageSchema2;
                                extension = builderFromMessage;
                                codedInputStreamReader2 = codedInputStreamReader3;
                                unknownFieldSchema4.getClass();
                                if (extension == null) {
                                }
                                if (!unknownFieldSchema4.mergeOneFieldFrom(extension, codedInputStreamReader2)) {
                                }
                                messageSchema3 = this;
                                extensionRegistryLite3 = extensionRegistryLite2;
                                i4 = i;
                            }
                            messageSchema3 = this;
                            extensionRegistryLite3 = extensionRegistryLite2;
                            i4 = i;
                        } catch (Throwable th14) {
                            th = th14;
                            extension = builderFromMessage;
                            unknownFieldSchema2 = unknownFieldSchema4;
                            objFilterMapUnknownEnumValues = extension;
                            i2 = i;
                            while (i2 < i3) {
                            }
                            UnknownFieldSchema unknownFieldSchema52222222222 = unknownFieldSchema2;
                            if (objFilterMapUnknownEnumValues != null) {
                            }
                            throw th;
                        }
                        break;
                    case 30:
                        messageSchema2 = messageSchema3;
                        obj2 = obj3;
                        int i22 = iSlowPositionForFieldNumber;
                        codedInputStreamReader3 = codedInputStreamReader;
                        i = i4;
                        extension = builderFromMessage;
                        try {
                            List listMutableListAt = listFieldSchema.mutableListAt(offset(iTypeAndOffsetAt), obj2);
                            codedInputStreamReader3.readEnumList(listMutableListAt);
                            Internal.EnumVerifier enumFieldVerifier2 = messageSchema2.getEnumFieldVerifier(i22);
                            obj5 = obj2;
                            unknownFieldSchema2 = unknownFieldSchema;
                            try {
                                extension = SchemaUtil.filterUnknownEnumList(obj5, fieldNumber, listMutableListAt, enumFieldVerifier2, extension, unknownFieldSchema2);
                                unknownFieldSchema4 = unknownFieldSchema2;
                                extensionRegistryLite2 = extensionRegistryLite;
                            } catch (InvalidProtocolBufferException.InvalidWireTypeException unused8) {
                                unknownFieldSchema4 = unknownFieldSchema2;
                                extensionRegistryLite2 = extensionRegistryLite;
                                obj2 = obj5;
                                messageSchema = messageSchema2;
                                codedInputStreamReader2 = codedInputStreamReader3;
                                unknownFieldSchema4.getClass();
                                if (extension == null) {
                                }
                                if (!unknownFieldSchema4.mergeOneFieldFrom(extension, codedInputStreamReader2)) {
                                }
                                messageSchema3 = this;
                                extensionRegistryLite3 = extensionRegistryLite2;
                                i4 = i;
                            } catch (Throwable th15) {
                                th = th15;
                                obj2 = obj5;
                            }
                        } catch (InvalidProtocolBufferException.InvalidWireTypeException unused9) {
                            unknownFieldSchema4 = unknownFieldSchema;
                            extensionRegistryLite2 = extensionRegistryLite;
                            messageSchema = messageSchema2;
                            codedInputStreamReader2 = codedInputStreamReader3;
                            unknownFieldSchema4.getClass();
                            if (extension == null) {
                            }
                            if (!unknownFieldSchema4.mergeOneFieldFrom(extension, codedInputStreamReader2)) {
                            }
                            messageSchema3 = this;
                            extensionRegistryLite3 = extensionRegistryLite2;
                            i4 = i;
                        } catch (Throwable th16) {
                            th = th16;
                            unknownFieldSchema4 = unknownFieldSchema;
                            unknownFieldSchema2 = unknownFieldSchema4;
                            objFilterMapUnknownEnumValues = extension;
                            i2 = i;
                            while (i2 < i3) {
                            }
                            UnknownFieldSchema unknownFieldSchema522222222222 = unknownFieldSchema2;
                            if (objFilterMapUnknownEnumValues != null) {
                            }
                            throw th;
                        }
                        messageSchema3 = this;
                        extensionRegistryLite3 = extensionRegistryLite2;
                        i4 = i;
                        break;
                    case 31:
                        codedInputStreamReader3 = codedInputStreamReader;
                        i = i4;
                        extension = builderFromMessage;
                        messageSchema2 = messageSchema3;
                        codedInputStreamReader3.readSFixed32List(listFieldSchema.mutableListAt(offset(iTypeAndOffsetAt), obj3));
                        unknownFieldSchema4 = unknownFieldSchema;
                        extensionRegistryLite2 = extensionRegistryLite;
                        builderFromMessage = extension;
                        extension = builderFromMessage;
                        messageSchema3 = this;
                        extensionRegistryLite3 = extensionRegistryLite2;
                        i4 = i;
                    case 32:
                        codedInputStreamReader3 = codedInputStreamReader;
                        i = i4;
                        extension = builderFromMessage;
                        messageSchema2 = messageSchema3;
                        codedInputStreamReader3.readSFixed64List(listFieldSchema.mutableListAt(offset(iTypeAndOffsetAt), obj3));
                        unknownFieldSchema4 = unknownFieldSchema;
                        extensionRegistryLite2 = extensionRegistryLite;
                        builderFromMessage = extension;
                        extension = builderFromMessage;
                        messageSchema3 = this;
                        extensionRegistryLite3 = extensionRegistryLite2;
                        i4 = i;
                    case 33:
                        codedInputStreamReader3 = codedInputStreamReader;
                        i = i4;
                        extension = builderFromMessage;
                        messageSchema2 = messageSchema3;
                        codedInputStreamReader3.readSInt32List(listFieldSchema.mutableListAt(offset(iTypeAndOffsetAt), obj3));
                        unknownFieldSchema4 = unknownFieldSchema;
                        extensionRegistryLite2 = extensionRegistryLite;
                        builderFromMessage = extension;
                        extension = builderFromMessage;
                        messageSchema3 = this;
                        extensionRegistryLite3 = extensionRegistryLite2;
                        i4 = i;
                    case 34:
                        codedInputStreamReader3 = codedInputStreamReader;
                        i = i4;
                        extension = builderFromMessage;
                        messageSchema2 = messageSchema3;
                        codedInputStreamReader3.readSInt64List(listFieldSchema.mutableListAt(offset(iTypeAndOffsetAt), obj3));
                        unknownFieldSchema4 = unknownFieldSchema;
                        extensionRegistryLite2 = extensionRegistryLite;
                        builderFromMessage = extension;
                        extension = builderFromMessage;
                        messageSchema3 = this;
                        extensionRegistryLite3 = extensionRegistryLite2;
                        i4 = i;
                    case 35:
                        codedInputStreamReader3 = codedInputStreamReader;
                        i = i4;
                        extension = builderFromMessage;
                        messageSchema2 = messageSchema3;
                        codedInputStreamReader3.readDoubleList(listFieldSchema.mutableListAt(offset(iTypeAndOffsetAt), obj3));
                        unknownFieldSchema4 = unknownFieldSchema;
                        extensionRegistryLite2 = extensionRegistryLite;
                        builderFromMessage = extension;
                        extension = builderFromMessage;
                        messageSchema3 = this;
                        extensionRegistryLite3 = extensionRegistryLite2;
                        i4 = i;
                    case 36:
                        codedInputStreamReader3 = codedInputStreamReader;
                        i = i4;
                        extension = builderFromMessage;
                        messageSchema2 = messageSchema3;
                        codedInputStreamReader3.readFloatList(listFieldSchema.mutableListAt(offset(iTypeAndOffsetAt), obj3));
                        unknownFieldSchema4 = unknownFieldSchema;
                        extensionRegistryLite2 = extensionRegistryLite;
                        builderFromMessage = extension;
                        extension = builderFromMessage;
                        messageSchema3 = this;
                        extensionRegistryLite3 = extensionRegistryLite2;
                        i4 = i;
                    case 37:
                        codedInputStreamReader3 = codedInputStreamReader;
                        i = i4;
                        extension = builderFromMessage;
                        messageSchema2 = messageSchema3;
                        codedInputStreamReader3.readInt64List(listFieldSchema.mutableListAt(offset(iTypeAndOffsetAt), obj3));
                        unknownFieldSchema4 = unknownFieldSchema;
                        extensionRegistryLite2 = extensionRegistryLite;
                        builderFromMessage = extension;
                        extension = builderFromMessage;
                        messageSchema3 = this;
                        extensionRegistryLite3 = extensionRegistryLite2;
                        i4 = i;
                    case 38:
                        codedInputStreamReader3 = codedInputStreamReader;
                        i = i4;
                        extension = builderFromMessage;
                        messageSchema2 = messageSchema3;
                        codedInputStreamReader3.readUInt64List(listFieldSchema.mutableListAt(offset(iTypeAndOffsetAt), obj3));
                        unknownFieldSchema4 = unknownFieldSchema;
                        extensionRegistryLite2 = extensionRegistryLite;
                        builderFromMessage = extension;
                        extension = builderFromMessage;
                        messageSchema3 = this;
                        extensionRegistryLite3 = extensionRegistryLite2;
                        i4 = i;
                    case 39:
                        codedInputStreamReader3 = codedInputStreamReader;
                        i = i4;
                        extension = builderFromMessage;
                        messageSchema2 = messageSchema3;
                        codedInputStreamReader3.readInt32List(listFieldSchema.mutableListAt(offset(iTypeAndOffsetAt), obj3));
                        unknownFieldSchema4 = unknownFieldSchema;
                        extensionRegistryLite2 = extensionRegistryLite;
                        builderFromMessage = extension;
                        extension = builderFromMessage;
                        messageSchema3 = this;
                        extensionRegistryLite3 = extensionRegistryLite2;
                        i4 = i;
                    case 40:
                        codedInputStreamReader3 = codedInputStreamReader;
                        i = i4;
                        extension = builderFromMessage;
                        messageSchema2 = messageSchema3;
                        codedInputStreamReader3.readFixed64List(listFieldSchema.mutableListAt(offset(iTypeAndOffsetAt), obj3));
                        unknownFieldSchema4 = unknownFieldSchema;
                        extensionRegistryLite2 = extensionRegistryLite;
                        builderFromMessage = extension;
                        extension = builderFromMessage;
                        messageSchema3 = this;
                        extensionRegistryLite3 = extensionRegistryLite2;
                        i4 = i;
                    case 41:
                        codedInputStreamReader3 = codedInputStreamReader;
                        i = i4;
                        extension = builderFromMessage;
                        messageSchema2 = messageSchema3;
                        codedInputStreamReader3.readFixed32List(listFieldSchema.mutableListAt(offset(iTypeAndOffsetAt), obj3));
                        unknownFieldSchema4 = unknownFieldSchema;
                        extensionRegistryLite2 = extensionRegistryLite;
                        builderFromMessage = extension;
                        extension = builderFromMessage;
                        messageSchema3 = this;
                        extensionRegistryLite3 = extensionRegistryLite2;
                        i4 = i;
                    case 42:
                        codedInputStreamReader3 = codedInputStreamReader;
                        i = i4;
                        extension = builderFromMessage;
                        messageSchema2 = messageSchema3;
                        codedInputStreamReader3.readBoolList(listFieldSchema.mutableListAt(offset(iTypeAndOffsetAt), obj3));
                        unknownFieldSchema4 = unknownFieldSchema;
                        extensionRegistryLite2 = extensionRegistryLite;
                        builderFromMessage = extension;
                        extension = builderFromMessage;
                        messageSchema3 = this;
                        extensionRegistryLite3 = extensionRegistryLite2;
                        i4 = i;
                    case 43:
                        codedInputStreamReader3 = codedInputStreamReader;
                        obj2 = obj3;
                        i = i4;
                        extension = builderFromMessage;
                        messageSchema2 = messageSchema3;
                        try {
                            codedInputStreamReader3.readUInt32List(listFieldSchema.mutableListAt(offset(iTypeAndOffsetAt), obj2));
                            unknownFieldSchema4 = unknownFieldSchema;
                            extensionRegistryLite2 = extensionRegistryLite;
                            builderFromMessage = extension;
                            extension = builderFromMessage;
                        } catch (InvalidProtocolBufferException.InvalidWireTypeException unused10) {
                            unknownFieldSchema4 = unknownFieldSchema;
                            extensionRegistryLite2 = extensionRegistryLite;
                            messageSchema = messageSchema2;
                            codedInputStreamReader2 = codedInputStreamReader3;
                            unknownFieldSchema4.getClass();
                            if (extension == null) {
                            }
                            if (!unknownFieldSchema4.mergeOneFieldFrom(extension, codedInputStreamReader2)) {
                            }
                            messageSchema3 = this;
                            extensionRegistryLite3 = extensionRegistryLite2;
                            i4 = i;
                        } catch (Throwable th17) {
                            th = th17;
                            unknownFieldSchema2 = unknownFieldSchema;
                            objFilterMapUnknownEnumValues = extension;
                            i2 = i;
                            while (i2 < i3) {
                            }
                            UnknownFieldSchema unknownFieldSchema5222222222222 = unknownFieldSchema2;
                            if (objFilterMapUnknownEnumValues != null) {
                            }
                            throw th;
                        }
                        messageSchema3 = this;
                        extensionRegistryLite3 = extensionRegistryLite2;
                        i4 = i;
                        break;
                    case 44:
                        messageSchema2 = messageSchema3;
                        obj5 = obj3;
                        int i23 = iSlowPositionForFieldNumber;
                        codedInputStreamReader3 = codedInputStreamReader;
                        i = i4;
                        try {
                            List listMutableListAt2 = listFieldSchema.mutableListAt(offset(iTypeAndOffsetAt), obj5);
                            codedInputStreamReader3.readEnumList(listMutableListAt2);
                            extension = builderFromMessage;
                            try {
                                extension = SchemaUtil.filterUnknownEnumList(obj5, fieldNumber, listMutableListAt2, messageSchema2.getEnumFieldVerifier(i23), extension, unknownFieldSchema3);
                                unknownFieldSchema4 = unknownFieldSchema;
                                extensionRegistryLite2 = extensionRegistryLite;
                            } catch (InvalidProtocolBufferException.InvalidWireTypeException unused11) {
                                unknownFieldSchema4 = unknownFieldSchema;
                                extensionRegistryLite2 = extensionRegistryLite;
                                obj2 = obj5;
                                messageSchema = messageSchema2;
                                codedInputStreamReader2 = codedInputStreamReader3;
                                unknownFieldSchema4.getClass();
                                if (extension == null) {
                                }
                                if (!unknownFieldSchema4.mergeOneFieldFrom(extension, codedInputStreamReader2)) {
                                }
                                messageSchema3 = this;
                                extensionRegistryLite3 = extensionRegistryLite2;
                                i4 = i;
                            } catch (Throwable th18) {
                                th = th18;
                                obj2 = obj5;
                                unknownFieldSchema2 = unknownFieldSchema;
                                objFilterMapUnknownEnumValues = extension;
                                i2 = i;
                                while (i2 < i3) {
                                }
                                UnknownFieldSchema unknownFieldSchema52222222222222 = unknownFieldSchema2;
                                if (objFilterMapUnknownEnumValues != null) {
                                }
                                throw th;
                            }
                        } catch (InvalidProtocolBufferException.InvalidWireTypeException unused12) {
                            extension = builderFromMessage;
                        } catch (Throwable th19) {
                            th = th19;
                            obj2 = obj5;
                            extension = builderFromMessage;
                        }
                        messageSchema3 = this;
                        extensionRegistryLite3 = extensionRegistryLite2;
                        i4 = i;
                        break;
                    case 45:
                        messageSchema2 = messageSchema3;
                        obj4 = obj3;
                        codedInputStreamReader3 = codedInputStreamReader;
                        unknownFieldSchema4 = unknownFieldSchema3;
                        i = i4;
                        codedInputStreamReader3.readSFixed32List(listFieldSchema.mutableListAt(offset(iTypeAndOffsetAt), obj4));
                        extensionRegistryLite2 = extensionRegistryLite;
                        extension = builderFromMessage;
                        messageSchema3 = this;
                        extensionRegistryLite3 = extensionRegistryLite2;
                        i4 = i;
                    case 46:
                        messageSchema2 = messageSchema3;
                        obj4 = obj3;
                        codedInputStreamReader3 = codedInputStreamReader;
                        unknownFieldSchema4 = unknownFieldSchema3;
                        i = i4;
                        codedInputStreamReader3.readSFixed64List(listFieldSchema.mutableListAt(offset(iTypeAndOffsetAt), obj4));
                        extensionRegistryLite2 = extensionRegistryLite;
                        extension = builderFromMessage;
                        messageSchema3 = this;
                        extensionRegistryLite3 = extensionRegistryLite2;
                        i4 = i;
                    case 47:
                        messageSchema2 = messageSchema3;
                        obj4 = obj3;
                        codedInputStreamReader3 = codedInputStreamReader;
                        unknownFieldSchema4 = unknownFieldSchema3;
                        i = i4;
                        codedInputStreamReader3.readSInt32List(listFieldSchema.mutableListAt(offset(iTypeAndOffsetAt), obj4));
                        extensionRegistryLite2 = extensionRegistryLite;
                        extension = builderFromMessage;
                        messageSchema3 = this;
                        extensionRegistryLite3 = extensionRegistryLite2;
                        i4 = i;
                    case 48:
                        messageSchema2 = messageSchema3;
                        obj4 = obj3;
                        codedInputStreamReader3 = codedInputStreamReader;
                        unknownFieldSchema4 = unknownFieldSchema3;
                        i = i4;
                        try {
                            codedInputStreamReader3.readSInt64List(listFieldSchema.mutableListAt(offset(iTypeAndOffsetAt), obj4));
                            extensionRegistryLite2 = extensionRegistryLite;
                            extension = builderFromMessage;
                        } catch (InvalidProtocolBufferException.InvalidWireTypeException unused13) {
                            extensionRegistryLite2 = extensionRegistryLite;
                            obj2 = obj4;
                            messageSchema = messageSchema2;
                            extension = builderFromMessage;
                            codedInputStreamReader2 = codedInputStreamReader3;
                            unknownFieldSchema4.getClass();
                            if (extension == null) {
                            }
                            if (!unknownFieldSchema4.mergeOneFieldFrom(extension, codedInputStreamReader2)) {
                            }
                            messageSchema3 = this;
                            extensionRegistryLite3 = extensionRegistryLite2;
                            i4 = i;
                        } catch (Throwable th20) {
                            th = th20;
                            obj2 = obj4;
                            extension = builderFromMessage;
                            unknownFieldSchema2 = unknownFieldSchema4;
                            objFilterMapUnknownEnumValues = extension;
                            i2 = i;
                            while (i2 < i3) {
                            }
                            UnknownFieldSchema unknownFieldSchema522222222222222 = unknownFieldSchema2;
                            if (objFilterMapUnknownEnumValues != null) {
                            }
                            throw th;
                        }
                        messageSchema3 = this;
                        extensionRegistryLite3 = extensionRegistryLite2;
                        i4 = i;
                        break;
                    case 49:
                        unknownFieldSchema4 = unknownFieldSchema3;
                        i = i4;
                        try {
                            obj2 = obj;
                        } catch (Throwable th21) {
                            th = th21;
                            obj4 = obj;
                            obj2 = obj4;
                            extension = builderFromMessage;
                            unknownFieldSchema2 = unknownFieldSchema4;
                            objFilterMapUnknownEnumValues = extension;
                            i2 = i;
                            while (i2 < i3) {
                            }
                            UnknownFieldSchema unknownFieldSchema5222222222222222 = unknownFieldSchema2;
                            if (objFilterMapUnknownEnumValues != null) {
                            }
                            throw th;
                        }
                        try {
                            messageSchema3.readGroupList(obj2, offset(iTypeAndOffsetAt), codedInputStreamReader, messageSchema3.getMessageFieldSchema(iSlowPositionForFieldNumber), extensionRegistryLite);
                            messageSchema2 = messageSchema3;
                            obj4 = obj2;
                            codedInputStreamReader3 = codedInputStreamReader;
                            extensionRegistryLite2 = extensionRegistryLite;
                            extension = builderFromMessage;
                        } catch (InvalidProtocolBufferException.InvalidWireTypeException unused14) {
                            extensionRegistryLite2 = extensionRegistryLite;
                            messageSchema = messageSchema3;
                            codedInputStreamReader2 = codedInputStreamReader;
                            extension = builderFromMessage;
                            unknownFieldSchema4.getClass();
                            if (extension == null) {
                            }
                            if (!unknownFieldSchema4.mergeOneFieldFrom(extension, codedInputStreamReader2)) {
                            }
                            messageSchema3 = this;
                            extensionRegistryLite3 = extensionRegistryLite2;
                            i4 = i;
                        } catch (Throwable th22) {
                            th = th22;
                            extension = builderFromMessage;
                            unknownFieldSchema2 = unknownFieldSchema4;
                            objFilterMapUnknownEnumValues = extension;
                            i2 = i;
                            while (i2 < i3) {
                            }
                            UnknownFieldSchema unknownFieldSchema52222222222222222 = unknownFieldSchema2;
                            if (objFilterMapUnknownEnumValues != null) {
                            }
                            throw th;
                        }
                        messageSchema3 = this;
                        extensionRegistryLite3 = extensionRegistryLite2;
                        i4 = i;
                        break;
                    case 50:
                        unknownFieldSchema4 = unknownFieldSchema3;
                        i = i4;
                        try {
                            try {
                                messageSchema3.mergeMap(obj3, iSlowPositionForFieldNumber, messageSchema3.getMapFieldDefaultEntry(iSlowPositionForFieldNumber), extensionRegistryLite5, codedInputStreamReader);
                                extensionRegistryLite2 = extensionRegistryLite;
                                extension = builderFromMessage;
                            } catch (Throwable th23) {
                                th = th23;
                                obj2 = obj;
                                extension = builderFromMessage;
                                unknownFieldSchema2 = unknownFieldSchema4;
                                objFilterMapUnknownEnumValues = extension;
                                i2 = i;
                                while (i2 < i3) {
                                }
                                UnknownFieldSchema unknownFieldSchema522222222222222222 = unknownFieldSchema2;
                                if (objFilterMapUnknownEnumValues != null) {
                                }
                                throw th;
                            }
                        } catch (InvalidProtocolBufferException.InvalidWireTypeException unused15) {
                            obj2 = obj;
                            codedInputStreamReader2 = codedInputStreamReader;
                            extensionRegistryLite2 = extensionRegistryLite;
                            messageSchema = messageSchema3;
                            extension = builderFromMessage;
                            unknownFieldSchema4.getClass();
                            if (extension == null) {
                            }
                            if (!unknownFieldSchema4.mergeOneFieldFrom(extension, codedInputStreamReader2)) {
                            }
                            messageSchema3 = this;
                            extensionRegistryLite3 = extensionRegistryLite2;
                            i4 = i;
                        }
                        messageSchema3 = this;
                        extensionRegistryLite3 = extensionRegistryLite2;
                        i4 = i;
                        break;
                    case 51:
                        unknownFieldSchema4 = unknownFieldSchema3;
                        i = i4;
                        long jOffset14 = offset(iTypeAndOffsetAt);
                        codedInputStreamReader.requireWireType(1);
                        UnsafeUtil.putObject(jOffset14, obj3, Double.valueOf(codedInputStream.readDouble()));
                        messageSchema3.setOneofPresent(fieldNumber, iSlowPositionForFieldNumber, obj3);
                        extensionRegistryLite2 = extensionRegistryLite5;
                        extension = builderFromMessage;
                        messageSchema3 = this;
                        extensionRegistryLite3 = extensionRegistryLite2;
                        i4 = i;
                    case 52:
                        unknownFieldSchema4 = unknownFieldSchema3;
                        i = i4;
                        long jOffset15 = offset(iTypeAndOffsetAt);
                        codedInputStreamReader.requireWireType(5);
                        UnsafeUtil.putObject(jOffset15, obj3, Float.valueOf(codedInputStream.readFloat()));
                        messageSchema3.setOneofPresent(fieldNumber, iSlowPositionForFieldNumber, obj3);
                        extensionRegistryLite2 = extensionRegistryLite5;
                        extension = builderFromMessage;
                        messageSchema3 = this;
                        extensionRegistryLite3 = extensionRegistryLite2;
                        i4 = i;
                    case 53:
                        unknownFieldSchema4 = unknownFieldSchema3;
                        i = i4;
                        long jOffset16 = offset(iTypeAndOffsetAt);
                        codedInputStreamReader.requireWireType(0);
                        UnsafeUtil.putObject(jOffset16, obj3, Long.valueOf(codedInputStream.readInt64()));
                        messageSchema3.setOneofPresent(fieldNumber, iSlowPositionForFieldNumber, obj3);
                        extensionRegistryLite2 = extensionRegistryLite5;
                        extension = builderFromMessage;
                        messageSchema3 = this;
                        extensionRegistryLite3 = extensionRegistryLite2;
                        i4 = i;
                    case 54:
                        unknownFieldSchema4 = unknownFieldSchema3;
                        i = i4;
                        long jOffset17 = offset(iTypeAndOffsetAt);
                        codedInputStreamReader.requireWireType(0);
                        UnsafeUtil.putObject(jOffset17, obj3, Long.valueOf(codedInputStream.readUInt64()));
                        messageSchema3.setOneofPresent(fieldNumber, iSlowPositionForFieldNumber, obj3);
                        extensionRegistryLite2 = extensionRegistryLite5;
                        extension = builderFromMessage;
                        messageSchema3 = this;
                        extensionRegistryLite3 = extensionRegistryLite2;
                        i4 = i;
                    case 55:
                        unknownFieldSchema4 = unknownFieldSchema3;
                        i = i4;
                        long jOffset18 = offset(iTypeAndOffsetAt);
                        codedInputStreamReader.requireWireType(0);
                        UnsafeUtil.putObject(jOffset18, obj3, Integer.valueOf(codedInputStream.readInt32()));
                        messageSchema3.setOneofPresent(fieldNumber, iSlowPositionForFieldNumber, obj3);
                        extensionRegistryLite2 = extensionRegistryLite5;
                        extension = builderFromMessage;
                        messageSchema3 = this;
                        extensionRegistryLite3 = extensionRegistryLite2;
                        i4 = i;
                    case 56:
                        unknownFieldSchema4 = unknownFieldSchema3;
                        i = i4;
                        long jOffset19 = offset(iTypeAndOffsetAt);
                        codedInputStreamReader.requireWireType(1);
                        UnsafeUtil.putObject(jOffset19, obj3, Long.valueOf(codedInputStream.readFixed64()));
                        messageSchema3.setOneofPresent(fieldNumber, iSlowPositionForFieldNumber, obj3);
                        extensionRegistryLite2 = extensionRegistryLite5;
                        extension = builderFromMessage;
                        messageSchema3 = this;
                        extensionRegistryLite3 = extensionRegistryLite2;
                        i4 = i;
                    case 57:
                        unknownFieldSchema4 = unknownFieldSchema3;
                        i = i4;
                        long jOffset20 = offset(iTypeAndOffsetAt);
                        codedInputStreamReader.requireWireType(5);
                        UnsafeUtil.putObject(jOffset20, obj3, Integer.valueOf(codedInputStream.readFixed32()));
                        messageSchema3.setOneofPresent(fieldNumber, iSlowPositionForFieldNumber, obj3);
                        extensionRegistryLite2 = extensionRegistryLite5;
                        extension = builderFromMessage;
                        messageSchema3 = this;
                        extensionRegistryLite3 = extensionRegistryLite2;
                        i4 = i;
                    case 58:
                        unknownFieldSchema4 = unknownFieldSchema3;
                        i = i4;
                        long jOffset21 = offset(iTypeAndOffsetAt);
                        codedInputStreamReader.requireWireType(0);
                        UnsafeUtil.putObject(jOffset21, obj3, Boolean.valueOf(codedInputStream.readBool()));
                        messageSchema3.setOneofPresent(fieldNumber, iSlowPositionForFieldNumber, obj3);
                        extensionRegistryLite2 = extensionRegistryLite5;
                        extension = builderFromMessage;
                        messageSchema3 = this;
                        extensionRegistryLite3 = extensionRegistryLite2;
                        i4 = i;
                    case 59:
                        unknownFieldSchema4 = unknownFieldSchema3;
                        i = i4;
                        messageSchema3.readString(obj3, iTypeAndOffsetAt, codedInputStreamReader);
                        messageSchema3.setOneofPresent(fieldNumber, iSlowPositionForFieldNumber, obj3);
                        extensionRegistryLite2 = extensionRegistryLite5;
                        extension = builderFromMessage;
                        messageSchema3 = this;
                        extensionRegistryLite3 = extensionRegistryLite2;
                        i4 = i;
                    case 60:
                        unknownFieldSchema4 = unknownFieldSchema3;
                        i = i4;
                        MessageLite messageLite3 = (MessageLite) messageSchema3.mutableOneofMessageFieldForMerge(fieldNumber, iSlowPositionForFieldNumber, obj3);
                        Schema messageFieldSchema3 = messageSchema3.getMessageFieldSchema(iSlowPositionForFieldNumber);
                        codedInputStreamReader.requireWireType(2);
                        codedInputStreamReader.mergeMessageFieldInternal(messageLite3, messageFieldSchema3, extensionRegistryLite5);
                        messageSchema3.storeOneofMessageField(fieldNumber, iSlowPositionForFieldNumber, obj3, messageLite3);
                        extensionRegistryLite2 = extensionRegistryLite5;
                        extension = builderFromMessage;
                        messageSchema3 = this;
                        extensionRegistryLite3 = extensionRegistryLite2;
                        i4 = i;
                    case 61:
                        unknownFieldSchema4 = unknownFieldSchema3;
                        i = i4;
                        UnsafeUtil.putObject(offset(iTypeAndOffsetAt), obj3, codedInputStreamReader.readBytes());
                        messageSchema3.setOneofPresent(fieldNumber, iSlowPositionForFieldNumber, obj3);
                        extensionRegistryLite2 = extensionRegistryLite5;
                        extension = builderFromMessage;
                        messageSchema3 = this;
                        extensionRegistryLite3 = extensionRegistryLite2;
                        i4 = i;
                    case 62:
                        unknownFieldSchema4 = unknownFieldSchema3;
                        i = i4;
                        long jOffset22 = offset(iTypeAndOffsetAt);
                        codedInputStreamReader.requireWireType(0);
                        UnsafeUtil.putObject(jOffset22, obj3, Integer.valueOf(codedInputStream.readUInt32()));
                        messageSchema3.setOneofPresent(fieldNumber, iSlowPositionForFieldNumber, obj3);
                        extensionRegistryLite2 = extensionRegistryLite5;
                        extension = builderFromMessage;
                        messageSchema3 = this;
                        extensionRegistryLite3 = extensionRegistryLite2;
                        i4 = i;
                    case 63:
                        unknownFieldSchema4 = unknownFieldSchema3;
                        i = i4;
                        codedInputStreamReader.requireWireType(0);
                        int i24 = codedInputStream.readEnum();
                        Internal.EnumVerifier enumFieldVerifier3 = messageSchema3.getEnumFieldVerifier(iSlowPositionForFieldNumber);
                        if (enumFieldVerifier3 != null && !enumFieldVerifier3.isInRange(i24)) {
                            extension = SchemaUtil.storeUnknownEnum(obj3, fieldNumber, i24, builderFromMessage, unknownFieldSchema4);
                            extensionRegistryLite2 = extensionRegistryLite5;
                            messageSchema3 = this;
                            extensionRegistryLite3 = extensionRegistryLite2;
                            i4 = i;
                        }
                        UnsafeUtil.putObject(offset(iTypeAndOffsetAt), obj3, Integer.valueOf(i24));
                        messageSchema3.setOneofPresent(fieldNumber, iSlowPositionForFieldNumber, obj3);
                        extensionRegistryLite2 = extensionRegistryLite5;
                        extension = builderFromMessage;
                        messageSchema3 = this;
                        extensionRegistryLite3 = extensionRegistryLite2;
                        i4 = i;
                        break;
                    case 64:
                        unknownFieldSchema4 = unknownFieldSchema3;
                        i = i4;
                        long jOffset23 = offset(iTypeAndOffsetAt);
                        codedInputStreamReader.requireWireType(5);
                        UnsafeUtil.putObject(jOffset23, obj3, Integer.valueOf(codedInputStream.readSFixed32()));
                        messageSchema3.setOneofPresent(fieldNumber, iSlowPositionForFieldNumber, obj3);
                        extensionRegistryLite2 = extensionRegistryLite5;
                        extension = builderFromMessage;
                        messageSchema3 = this;
                        extensionRegistryLite3 = extensionRegistryLite2;
                        i4 = i;
                    case 65:
                        unknownFieldSchema4 = unknownFieldSchema3;
                        i = i4;
                        long jOffset24 = offset(iTypeAndOffsetAt);
                        codedInputStreamReader.requireWireType(1);
                        UnsafeUtil.putObject(jOffset24, obj3, Long.valueOf(codedInputStream.readSFixed64()));
                        messageSchema3.setOneofPresent(fieldNumber, iSlowPositionForFieldNumber, obj3);
                        extensionRegistryLite2 = extensionRegistryLite5;
                        extension = builderFromMessage;
                        messageSchema3 = this;
                        extensionRegistryLite3 = extensionRegistryLite2;
                        i4 = i;
                    case 66:
                        unknownFieldSchema4 = unknownFieldSchema3;
                        i = i4;
                        long jOffset25 = offset(iTypeAndOffsetAt);
                        codedInputStreamReader.requireWireType(0);
                        UnsafeUtil.putObject(jOffset25, obj3, Integer.valueOf(codedInputStream.readSInt32()));
                        messageSchema3.setOneofPresent(fieldNumber, iSlowPositionForFieldNumber, obj3);
                        extensionRegistryLite2 = extensionRegistryLite5;
                        extension = builderFromMessage;
                        messageSchema3 = this;
                        extensionRegistryLite3 = extensionRegistryLite2;
                        i4 = i;
                    case 67:
                        unknownFieldSchema4 = unknownFieldSchema3;
                        i = i4;
                        try {
                            long jOffset26 = offset(iTypeAndOffsetAt);
                            codedInputStreamReader.requireWireType(0);
                            UnsafeUtil.putObject(jOffset26, obj3, Long.valueOf(codedInputStream.readSInt64()));
                            messageSchema3.setOneofPresent(fieldNumber, iSlowPositionForFieldNumber, obj3);
                            extensionRegistryLite2 = extensionRegistryLite5;
                            extension = builderFromMessage;
                        } catch (InvalidProtocolBufferException.InvalidWireTypeException unused16) {
                            messageSchema = messageSchema3;
                            extensionRegistryLite2 = extensionRegistryLite5;
                            obj2 = obj3;
                            extension = builderFromMessage;
                            codedInputStreamReader2 = codedInputStreamReader;
                            unknownFieldSchema4.getClass();
                            if (extension == null) {
                            }
                            if (!unknownFieldSchema4.mergeOneFieldFrom(extension, codedInputStreamReader2)) {
                            }
                            messageSchema3 = this;
                            extensionRegistryLite3 = extensionRegistryLite2;
                            i4 = i;
                        } catch (Throwable th24) {
                            th = th24;
                            obj2 = obj3;
                            extension = builderFromMessage;
                            unknownFieldSchema2 = unknownFieldSchema4;
                            objFilterMapUnknownEnumValues = extension;
                            i2 = i;
                            while (i2 < i3) {
                            }
                            UnknownFieldSchema unknownFieldSchema5222222222222222222 = unknownFieldSchema2;
                            if (objFilterMapUnknownEnumValues != null) {
                            }
                            throw th;
                        }
                        messageSchema3 = this;
                        extensionRegistryLite3 = extensionRegistryLite2;
                        i4 = i;
                        break;
                    case 68:
                        unknownFieldSchema4 = unknownFieldSchema3;
                        try {
                            MessageLite messageLite4 = (MessageLite) messageSchema3.mutableOneofMessageFieldForMerge(fieldNumber, iSlowPositionForFieldNumber, obj3);
                            Schema messageFieldSchema4 = messageSchema3.getMessageFieldSchema(iSlowPositionForFieldNumber);
                            codedInputStreamReader.requireWireType(3);
                            codedInputStreamReader.mergeGroupFieldInternal(messageLite4, messageFieldSchema4, extensionRegistryLite5);
                            messageSchema3.storeOneofMessageField(fieldNumber, iSlowPositionForFieldNumber, obj3, messageLite4);
                            i = i4;
                            extensionRegistryLite2 = extensionRegistryLite5;
                            extension = builderFromMessage;
                        } catch (InvalidProtocolBufferException.InvalidWireTypeException unused17) {
                            messageSchema = messageSchema3;
                            obj2 = obj3;
                            i = i4;
                            extension = builderFromMessage;
                            codedInputStreamReader2 = codedInputStreamReader;
                            extensionRegistryLite2 = extensionRegistryLite5;
                            unknownFieldSchema4.getClass();
                            if (extension == null) {
                            }
                            if (!unknownFieldSchema4.mergeOneFieldFrom(extension, codedInputStreamReader2)) {
                            }
                            messageSchema3 = this;
                            extensionRegistryLite3 = extensionRegistryLite2;
                            i4 = i;
                        } catch (Throwable th25) {
                            th = th25;
                            obj2 = obj3;
                            i = i4;
                            extension = builderFromMessage;
                            unknownFieldSchema2 = unknownFieldSchema4;
                            objFilterMapUnknownEnumValues = extension;
                            i2 = i;
                            while (i2 < i3) {
                            }
                            UnknownFieldSchema unknownFieldSchema52222222222222222222 = unknownFieldSchema2;
                            if (objFilterMapUnknownEnumValues != null) {
                            }
                            throw th;
                        }
                        messageSchema3 = this;
                        extensionRegistryLite3 = extensionRegistryLite2;
                        i4 = i;
                        break;
                    default:
                        if (builderFromMessage == null) {
                            try {
                                builderFromMessage = unknownFieldSchema3.getBuilderFromMessage(obj3);
                            } catch (InvalidProtocolBufferException.InvalidWireTypeException unused18) {
                                messageSchema = messageSchema3;
                                unknownFieldSchema4 = unknownFieldSchema3;
                                obj2 = obj3;
                                i = i4;
                                extension = builderFromMessage;
                                codedInputStreamReader2 = codedInputStreamReader;
                                extensionRegistryLite2 = extensionRegistryLite5;
                                unknownFieldSchema4.getClass();
                                if (extension == null) {
                                    extension = unknownFieldSchema4.getBuilderFromMessage(obj2);
                                }
                                if (!unknownFieldSchema4.mergeOneFieldFrom(extension, codedInputStreamReader2)) {
                                    Object objFilterMapUnknownEnumValues4 = extension;
                                    int i25 = i;
                                    while (i25 < i3) {
                                        UnknownFieldSchema unknownFieldSchema6 = unknownFieldSchema4;
                                        objFilterMapUnknownEnumValues4 = messageSchema.filterMapUnknownEnumValues(obj2, iArr[i25], objFilterMapUnknownEnumValues4, unknownFieldSchema6, obj);
                                        i25++;
                                        messageSchema = this;
                                        unknownFieldSchema4 = unknownFieldSchema6;
                                    }
                                    UnknownFieldSchema unknownFieldSchema7 = unknownFieldSchema4;
                                    if (objFilterMapUnknownEnumValues4 != null) {
                                        unknownFieldSchema7.setBuilderToMessage(obj2, objFilterMapUnknownEnumValues4);
                                        return;
                                    }
                                    return;
                                }
                                messageSchema3 = this;
                                extensionRegistryLite3 = extensionRegistryLite2;
                                i4 = i;
                            }
                        }
                        try {
                            if (!unknownFieldSchema3.mergeOneFieldFrom(builderFromMessage, codedInputStreamReader)) {
                                Object objFilterMapUnknownEnumValues5 = builderFromMessage;
                                while (i4 < i3) {
                                    objFilterMapUnknownEnumValues5 = messageSchema3.filterMapUnknownEnumValues(obj3, iArr[i4], objFilterMapUnknownEnumValues5, unknownFieldSchema3, obj);
                                    i4++;
                                }
                                if (objFilterMapUnknownEnumValues5 != null) {
                                    unknownFieldSchema3.setBuilderToMessage(obj3, objFilterMapUnknownEnumValues5);
                                    return;
                                }
                                return;
                            }
                            unknownFieldSchema4 = unknownFieldSchema3;
                            i = i4;
                            extension = builderFromMessage;
                            extensionRegistryLite2 = extensionRegistryLite5;
                            messageSchema3 = this;
                            extensionRegistryLite3 = extensionRegistryLite2;
                            i4 = i;
                        } catch (Throwable th26) {
                            th = th26;
                            unknownFieldSchema4 = unknownFieldSchema3;
                            obj2 = obj3;
                            i = i4;
                            extension = builderFromMessage;
                            unknownFieldSchema2 = unknownFieldSchema4;
                            objFilterMapUnknownEnumValues = extension;
                            i2 = i;
                            while (i2 < i3) {
                            }
                            UnknownFieldSchema unknownFieldSchema522222222222222222222 = unknownFieldSchema2;
                            if (objFilterMapUnknownEnumValues != null) {
                            }
                            throw th;
                        }
                        break;
                }
            } catch (Throwable th27) {
                th = th27;
                unknownFieldSchema2 = unknownFieldSchema3;
                obj2 = obj3;
                i = i4;
                extension = builderFromMessage;
                objFilterMapUnknownEnumValues = extension;
                i2 = i;
                while (i2 < i3) {
                }
                UnknownFieldSchema unknownFieldSchema5222222222222222222222 = unknownFieldSchema2;
                if (objFilterMapUnknownEnumValues != null) {
                }
                throw th;
            }
            obj2 = obj3;
            i = i4;
            extension = builderFromMessage;
            objFilterMapUnknownEnumValues = extension;
            i2 = i;
            while (i2 < i3) {
            }
            UnknownFieldSchema unknownFieldSchema52222222222222222222222 = unknownFieldSchema2;
            if (objFilterMapUnknownEnumValues != null) {
            }
            throw th;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:32:0x00a7, code lost:
    
        r9.put(r1, r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x00aa, code lost:
    
        r10.popLimit(r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x00ad, code lost:
    
        return;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void mergeMap(Object obj, int i, Object obj2, ExtensionRegistryLite extensionRegistryLite, CodedInputStreamReader codedInputStreamReader) throws InvalidProtocolBufferException.InvalidWireTypeException {
        long jTypeAndOffsetAt = typeAndOffsetAt(i) & 1048575;
        Object object = UnsafeUtil.getObject(jTypeAndOffsetAt, obj);
        MapFieldSchema mapFieldSchema = this.mapFieldSchema;
        if (object == null) {
            ((MapFieldSchemaLite) mapFieldSchema).getClass();
            object = MapFieldLite.EMPTY_MAP_FIELD.mutableCopy();
            UnsafeUtil.putObject(jTypeAndOffsetAt, obj, object);
        } else {
            MapFieldSchemaLite mapFieldSchemaLite = (MapFieldSchemaLite) mapFieldSchema;
            mapFieldSchemaLite.getClass();
            if (!((MapFieldLite) object).isMutable()) {
                mapFieldSchemaLite.getClass();
                MapFieldLite mapFieldLiteMutableCopy = MapFieldLite.EMPTY_MAP_FIELD.mutableCopy();
                mapFieldSchemaLite.mergeFrom(mapFieldLiteMutableCopy, object);
                UnsafeUtil.putObject(jTypeAndOffsetAt, obj, mapFieldLiteMutableCopy);
                object = mapFieldLiteMutableCopy;
            }
        }
        MapFieldSchemaLite mapFieldSchemaLite2 = (MapFieldSchemaLite) mapFieldSchema;
        mapFieldSchemaLite2.getClass();
        MapFieldLite mapFieldLite = (MapFieldLite) object;
        mapFieldSchemaLite2.getClass();
        MapEntryLite.Metadata metadata = ((MapEntryLite) obj2).metadata;
        codedInputStreamReader.requireWireType(2);
        CodedInputStream codedInputStream = codedInputStreamReader.input;
        int iPushLimit = codedInputStream.pushLimit(codedInputStream.readUInt32());
        Object field = metadata.defaultKey;
        Object obj3 = metadata.defaultValue;
        Object field2 = obj3;
        while (true) {
            try {
                int fieldNumber = codedInputStreamReader.getFieldNumber();
                if (fieldNumber == Integer.MAX_VALUE || codedInputStream.isAtEnd()) {
                    break;
                }
                if (fieldNumber == 1) {
                    field = codedInputStreamReader.readField(metadata.keyType, null, null);
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
                    field2 = codedInputStreamReader.readField(metadata.valueType, obj3.getClass(), extensionRegistryLite);
                }
            } catch (Throwable th) {
                codedInputStream.popLimit(iPushLimit);
                throw th;
            }
        }
    }

    public final void mergeMessage(int i, Object obj, Object obj2) {
        if (isFieldPresent(i, obj2)) {
            long jTypeAndOffsetAt = typeAndOffsetAt(i) & 1048575;
            Unsafe unsafe = UNSAFE;
            Object object = unsafe.getObject(obj2, jTypeAndOffsetAt);
            if (object == null) {
                throw new IllegalStateException("Source subfield " + this.buffer[i] + " is present but null: " + obj2);
            }
            Schema messageFieldSchema = getMessageFieldSchema(i);
            if (!isFieldPresent(i, obj)) {
                if (isMutable(object)) {
                    GeneratedMessageLite generatedMessageLiteNewInstance = messageFieldSchema.newInstance();
                    messageFieldSchema.mergeFrom(generatedMessageLiteNewInstance, object);
                    unsafe.putObject(obj, jTypeAndOffsetAt, generatedMessageLiteNewInstance);
                } else {
                    unsafe.putObject(obj, jTypeAndOffsetAt, object);
                }
                setFieldPresent(i, obj);
                return;
            }
            Object object2 = unsafe.getObject(obj, jTypeAndOffsetAt);
            if (!isMutable(object2)) {
                GeneratedMessageLite generatedMessageLiteNewInstance2 = messageFieldSchema.newInstance();
                messageFieldSchema.mergeFrom(generatedMessageLiteNewInstance2, object2);
                unsafe.putObject(obj, jTypeAndOffsetAt, generatedMessageLiteNewInstance2);
                object2 = generatedMessageLiteNewInstance2;
            }
            messageFieldSchema.mergeFrom(object2, object);
        }
    }

    public final void mergeOneofMessage(int i, Object obj, Object obj2) {
        int[] iArr = this.buffer;
        int i2 = iArr[i];
        if (isOneofPresent(i2, i, obj2)) {
            long jTypeAndOffsetAt = typeAndOffsetAt(i) & 1048575;
            Unsafe unsafe = UNSAFE;
            Object object = unsafe.getObject(obj2, jTypeAndOffsetAt);
            if (object == null) {
                throw new IllegalStateException("Source subfield " + iArr[i] + " is present but null: " + obj2);
            }
            Schema messageFieldSchema = getMessageFieldSchema(i);
            if (!isOneofPresent(i2, i, obj)) {
                if (isMutable(object)) {
                    GeneratedMessageLite generatedMessageLiteNewInstance = messageFieldSchema.newInstance();
                    messageFieldSchema.mergeFrom(generatedMessageLiteNewInstance, object);
                    unsafe.putObject(obj, jTypeAndOffsetAt, generatedMessageLiteNewInstance);
                } else {
                    unsafe.putObject(obj, jTypeAndOffsetAt, object);
                }
                setOneofPresent(i2, i, obj);
                return;
            }
            Object object2 = unsafe.getObject(obj, jTypeAndOffsetAt);
            if (!isMutable(object2)) {
                GeneratedMessageLite generatedMessageLiteNewInstance2 = messageFieldSchema.newInstance();
                messageFieldSchema.mergeFrom(generatedMessageLiteNewInstance2, object2);
                unsafe.putObject(obj, jTypeAndOffsetAt, generatedMessageLiteNewInstance2);
                object2 = generatedMessageLiteNewInstance2;
            }
            messageFieldSchema.mergeFrom(object2, object);
        }
    }

    public final Object mutableMessageFieldForMerge(int i, Object obj) {
        Schema messageFieldSchema = getMessageFieldSchema(i);
        long jTypeAndOffsetAt = typeAndOffsetAt(i) & 1048575;
        if (!isFieldPresent(i, obj)) {
            return messageFieldSchema.newInstance();
        }
        Object object = UNSAFE.getObject(obj, jTypeAndOffsetAt);
        if (isMutable(object)) {
            return object;
        }
        GeneratedMessageLite generatedMessageLiteNewInstance = messageFieldSchema.newInstance();
        if (object != null) {
            messageFieldSchema.mergeFrom(generatedMessageLiteNewInstance, object);
        }
        return generatedMessageLiteNewInstance;
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
        GeneratedMessageLite generatedMessageLiteNewInstance = messageFieldSchema.newInstance();
        if (object != null) {
            messageFieldSchema.mergeFrom(generatedMessageLiteNewInstance, object);
        }
        return generatedMessageLiteNewInstance;
    }

    @Override // com.google.protobuf.Schema
    public final GeneratedMessageLite newInstance() {
        ((NewInstanceSchemaLite) this.newInstanceSchema).getClass();
        return ((GeneratedMessageLite) this.defaultInstance).newMutableInstance$1();
    }

    public final int parseMapField(Object obj, byte[] bArr, int i, int i2, int i3, long j, ArrayDecoders.Registers registers) throws InvalidProtocolBufferException {
        int iDecodeMapEntryValue;
        Unsafe unsafe = UNSAFE;
        Object mapFieldDefaultEntry = getMapFieldDefaultEntry(i3);
        Object object = unsafe.getObject(obj, j);
        MapFieldSchemaLite mapFieldSchemaLite = (MapFieldSchemaLite) this.mapFieldSchema;
        mapFieldSchemaLite.getClass();
        if (!((MapFieldLite) object).isMutable()) {
            mapFieldSchemaLite.getClass();
            MapFieldLite mapFieldLiteMutableCopy = MapFieldLite.EMPTY_MAP_FIELD.mutableCopy();
            mapFieldSchemaLite.mergeFrom(mapFieldLiteMutableCopy, object);
            unsafe.putObject(obj, j, mapFieldLiteMutableCopy);
            object = mapFieldLiteMutableCopy;
        }
        mapFieldSchemaLite.getClass();
        MapEntryLite.Metadata metadata = ((MapEntryLite) mapFieldDefaultEntry).metadata;
        mapFieldSchemaLite.getClass();
        MapFieldLite mapFieldLite = (MapFieldLite) object;
        int iDecodeVarint32 = ArrayDecoders.decodeVarint32(bArr, i, registers);
        int i4 = registers.int1;
        if (i4 < 0 || i4 > i2 - iDecodeVarint32) {
            throw InvalidProtocolBufferException.truncatedMessage();
        }
        int i5 = iDecodeVarint32 + i4;
        Object obj2 = metadata.defaultKey;
        Object obj3 = metadata.defaultValue;
        Object obj4 = obj2;
        Object obj5 = obj3;
        while (iDecodeVarint32 < i5) {
            int iDecodeVarint322 = iDecodeVarint32 + 1;
            int i6 = bArr[iDecodeVarint32];
            if (i6 < 0) {
                iDecodeVarint322 = ArrayDecoders.decodeVarint32(i6, bArr, iDecodeVarint322, registers);
                i6 = registers.int1;
            }
            int i7 = i6 >>> 3;
            int i8 = i6 & 7;
            if (i7 != 1) {
                if (i7 == 2 && i8 == metadata.valueType.getWireType()) {
                    iDecodeMapEntryValue = decodeMapEntryValue(bArr, iDecodeVarint322, i2, metadata.valueType, obj3.getClass(), registers);
                    obj5 = registers.object1;
                    iDecodeVarint32 = iDecodeMapEntryValue;
                } else {
                    iDecodeVarint32 = ArrayDecoders.skipField(i6, bArr, iDecodeVarint322, i2, registers);
                }
            } else if (i8 == metadata.keyType.getWireType()) {
                iDecodeMapEntryValue = decodeMapEntryValue(bArr, iDecodeVarint322, i2, metadata.keyType, null, registers);
                obj4 = registers.object1;
                iDecodeVarint32 = iDecodeMapEntryValue;
            } else {
                iDecodeVarint32 = ArrayDecoders.skipField(i6, bArr, iDecodeVarint322, i2, registers);
            }
        }
        if (iDecodeVarint32 != i5) {
            throw InvalidProtocolBufferException.parseFailure();
        }
        mapFieldLite.put(obj4, obj5);
        return i5;
    }

    public final int parseOneofField(Object obj, byte[] bArr, int i, int i2, int i3, int i4, int i5, int i6, int i7, long j, int i8, ArrayDecoders.Registers registers) throws InvalidProtocolBufferException {
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
                int iDecodeVarint64 = ArrayDecoders.decodeVarint64(bArr, i, registers);
                unsafe.putObject(obj, j, Long.valueOf(registers.long1));
                unsafe.putInt(obj, j2, i4);
                return iDecodeVarint64;
            case 55:
            case 62:
                if (i5 != 0) {
                    return i;
                }
                int iDecodeVarint32 = ArrayDecoders.decodeVarint32(bArr, i, registers);
                unsafe.putObject(obj, j, Integer.valueOf(registers.int1));
                unsafe.putInt(obj, j2, i4);
                return iDecodeVarint32;
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
                int iDecodeVarint642 = ArrayDecoders.decodeVarint64(bArr, i, registers);
                unsafe.putObject(obj, j, Boolean.valueOf(registers.long1 != 0));
                unsafe.putInt(obj, j2, i4);
                return iDecodeVarint642;
            case 59:
                if (i5 != 2) {
                    return i;
                }
                int iDecodeVarint322 = ArrayDecoders.decodeVarint32(bArr, i, registers);
                int i14 = registers.int1;
                if (i14 == 0) {
                    unsafe.putObject(obj, j, "");
                } else {
                    if ((i6 & VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS) != 0) {
                        if (!Utf8.processor.isValidUtf8(iDecodeVarint322, iDecodeVarint322 + i14, bArr)) {
                            throw InvalidProtocolBufferException.invalidUtf8();
                        }
                    }
                    unsafe.putObject(obj, j, new String(bArr, iDecodeVarint322, i14, Internal.UTF_8));
                    iDecodeVarint322 += i14;
                }
                unsafe.putInt(obj, j2, i4);
                return iDecodeVarint322;
            case 60:
                i9 = i;
                if (i5 == 2) {
                    Object objMutableOneofMessageFieldForMerge = mutableOneofMessageFieldForMerge(i4, i8, obj);
                    int iMergeMessageField = ArrayDecoders.mergeMessageField(objMutableOneofMessageFieldForMerge, getMessageFieldSchema(i8), bArr, i9, i2, registers);
                    storeOneofMessageField(i4, i8, obj, objMutableOneofMessageFieldForMerge);
                    return iMergeMessageField;
                }
                return i9;
            case 61:
                i9 = i;
                if (i5 == 2) {
                    int iDecodeBytes = ArrayDecoders.decodeBytes(bArr, i9, registers);
                    unsafe.putObject(obj, j, registers.object1);
                    unsafe.putInt(obj, j2, i4);
                    return iDecodeBytes;
                }
                return i9;
            case 63:
                i9 = i;
                if (i5 == 0) {
                    int iDecodeVarint323 = ArrayDecoders.decodeVarint32(bArr, i9, registers);
                    int i15 = registers.int1;
                    Internal.EnumVerifier enumFieldVerifier = getEnumFieldVerifier(i8);
                    if (enumFieldVerifier != null && !enumFieldVerifier.isInRange(i15)) {
                        getMutableUnknownFields(obj).storeField(i3, Long.valueOf(i15));
                        return iDecodeVarint323;
                    }
                    unsafe.putObject(obj, j, Integer.valueOf(i15));
                    unsafe.putInt(obj, j2, i4);
                    return iDecodeVarint323;
                }
                return i9;
            case 66:
                i9 = i;
                if (i5 == 0) {
                    int iDecodeVarint324 = ArrayDecoders.decodeVarint32(bArr, i9, registers);
                    unsafe.putObject(obj, j, Integer.valueOf(CodedInputStream.decodeZigZag32(registers.int1)));
                    unsafe.putInt(obj, j2, i4);
                    return iDecodeVarint324;
                }
                return i9;
            case 67:
                i9 = i;
                if (i5 == 0) {
                    int iDecodeVarint643 = ArrayDecoders.decodeVarint64(bArr, i9, registers);
                    unsafe.putObject(obj, j, Long.valueOf(CodedInputStream.decodeZigZag64(registers.long1)));
                    unsafe.putInt(obj, j2, i4);
                    return iDecodeVarint643;
                }
                return i9;
            case 68:
                if (i5 == 3) {
                    Object objMutableOneofMessageFieldForMerge2 = mutableOneofMessageFieldForMerge(i4, i8, obj);
                    int proto2Message = ((MessageSchema) getMessageFieldSchema(i8)).parseProto2Message(objMutableOneofMessageFieldForMerge2, bArr, i, i2, (i3 & (-8)) | 4, registers);
                    registers.object1 = objMutableOneofMessageFieldForMerge2;
                    storeOneofMessageField(i4, i8, obj, objMutableOneofMessageFieldForMerge2);
                    return proto2Message;
                }
            default:
                return i;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:149:0x044f, code lost:
    
        r10 = r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:244:0x07ab, code lost:
    
        if (r0 == r1) goto L246;
     */
    /* JADX WARN: Code restructure failed: missing block: B:245:0x07ad, code lost:
    
        r28.putInt(r9, r0, r13);
     */
    /* JADX WARN: Code restructure failed: missing block: B:246:0x07b3, code lost:
    
        r3 = null;
        r7 = r8.checkInitializedCount;
     */
    /* JADX WARN: Code restructure failed: missing block: B:248:0x07b9, code lost:
    
        if (r7 >= r8.repeatedFieldOffsetStart) goto L314;
     */
    /* JADX WARN: Code restructure failed: missing block: B:249:0x07bb, code lost:
    
        r3 = (com.google.protobuf.UnknownFieldSetLite) r8.filterMapUnknownEnumValues(r9, r8.intArray[r7], r3, r8.unknownFieldSchema, r31);
        r7 = r7 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:250:0x07cf, code lost:
    
        r0 = r8;
        r1 = r9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:251:0x07d1, code lost:
    
        if (r3 == null) goto L253;
     */
    /* JADX WARN: Code restructure failed: missing block: B:252:0x07d3, code lost:
    
        r0.unknownFieldSchema.setBuilderToMessage(r1, r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:253:0x07d8, code lost:
    
        if (r35 != 0) goto L258;
     */
    /* JADX WARN: Code restructure failed: missing block: B:254:0x07da, code lost:
    
        if (r10 != r6) goto L256;
     */
    /* JADX WARN: Code restructure failed: missing block: B:257:0x07e1, code lost:
    
        throw com.google.protobuf.InvalidProtocolBufferException.parseFailure();
     */
    /* JADX WARN: Code restructure failed: missing block: B:258:0x07e2, code lost:
    
        if (r10 > r6) goto L261;
     */
    /* JADX WARN: Code restructure failed: missing block: B:259:0x07e4, code lost:
    
        if (r14 != r35) goto L261;
     */
    /* JADX WARN: Code restructure failed: missing block: B:260:0x07e6, code lost:
    
        return r10;
     */
    /* JADX WARN: Code restructure failed: missing block: B:262:0x07eb, code lost:
    
        throw com.google.protobuf.InvalidProtocolBufferException.parseFailure();
     */
    /* JADX WARN: Removed duplicated region for block: B:237:0x0764  */
    /* JADX WARN: Removed duplicated region for block: B:239:0x0769  */
    /* JADX WARN: Removed duplicated region for block: B:241:0x0773  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int parseProto2Message(Object obj, byte[] bArr, int i, int i2, int i3, ArrayDecoders.Registers registers) throws InvalidProtocolBufferException {
        int i4;
        Unsafe unsafe;
        MessageSchema messageSchema;
        Object obj2;
        int i5;
        int i6;
        int iSlowPositionForFieldNumber;
        int i7;
        boolean z;
        boolean z2;
        int i8;
        int i9;
        int i10;
        int i11;
        int i12;
        int i13;
        int i14;
        int i15;
        int i16;
        Object objValueOf;
        int i17;
        int iDecodeVarint64;
        int proto2Message;
        int iDecodePackedDoubleList;
        int iDecodePackedVarint32List;
        int i18;
        byte[] bArr2;
        Unsafe unsafe2;
        int i19;
        boolean z3;
        int i20;
        ArrayDecoders.Registers registers2;
        int i21;
        Unsafe unsafe3;
        int i22;
        Object obj3;
        ArrayDecoders.Registers registers3;
        Unsafe unsafe4;
        byte[] bArr3;
        ArrayDecoders.Registers registers4;
        byte[] bArr4;
        Object obj4;
        ArrayDecoders.Registers registers5;
        int i23;
        int i24;
        MessageSchema messageSchema2 = this;
        Object obj5 = obj;
        byte[] bArr5 = bArr;
        int i25 = i2;
        ArrayDecoders.Registers registers6 = registers;
        checkMutable(obj5);
        Unsafe unsafe5 = UNSAFE;
        int iDecodeUnknownField = i;
        int i26 = -1;
        int i27 = 0;
        int i28 = 1048575;
        int i29 = 0;
        int i30 = 0;
        while (true) {
            int i31 = 1048575;
            while (iDecodeUnknownField < i25) {
                int iDecodeVarint32 = iDecodeUnknownField + 1;
                int i32 = bArr5[iDecodeUnknownField];
                if (i32 < 0) {
                    iDecodeVarint32 = ArrayDecoders.decodeVarint32(i32, bArr5, iDecodeVarint32, registers6);
                    i32 = registers6.int1;
                }
                int i33 = iDecodeVarint32;
                i30 = i32;
                iDecodeUnknownField = i33;
                int i34 = i30 >>> 3;
                int i35 = i27;
                int i36 = i30 & 7;
                int i37 = messageSchema2.maxFieldNumber;
                int i38 = messageSchema2.minFieldNumber;
                if (i34 > i26) {
                    iSlowPositionForFieldNumber = (i34 < i38 || i34 > i37) ? -1 : messageSchema2.slowPositionForFieldNumber(i34, i35 / 3);
                } else {
                    iSlowPositionForFieldNumber = (i34 < i38 || i34 > i37) ? -1 : messageSchema2.slowPositionForFieldNumber(i34, 0);
                }
                int i39 = iSlowPositionForFieldNumber;
                if (i39 == -1) {
                    unsafe = unsafe5;
                    i26 = i34;
                    i7 = 0;
                    z = false;
                    z2 = true;
                    obj2 = obj5;
                    i8 = i28;
                    messageSchema = messageSchema2;
                    i9 = i30;
                } else {
                    int[] iArr = messageSchema2.buffer;
                    int i40 = iArr[i39 + 1];
                    int iType = type(i40);
                    long j = i40 & i31;
                    if (iType <= 17) {
                        int i41 = iArr[i39 + 2];
                        int i42 = 1 << (i41 >>> 20);
                        int i43 = i41 & i31;
                        if (i43 != i28) {
                            int i44 = i31;
                            i18 = iType;
                            if (i28 != i44) {
                                unsafe5.putInt(obj5, i28, i29);
                            }
                            i8 = i43;
                            i29 = unsafe5.getInt(obj5, i43);
                        } else {
                            i18 = iType;
                            i8 = i28;
                        }
                        switch (i18) {
                            case 0:
                                bArr2 = bArr;
                                unsafe2 = unsafe5;
                                i19 = i30;
                                z3 = true;
                                i20 = iDecodeUnknownField;
                                registers2 = registers6;
                                i21 = i29;
                                if (i36 != 1) {
                                    z2 = z3;
                                    unsafe = unsafe2;
                                    iDecodeUnknownField = i20;
                                    i7 = i39;
                                    registers6 = registers2;
                                    i29 = i21;
                                    i26 = i34;
                                    z = false;
                                    messageSchema = messageSchema2;
                                    obj2 = obj5;
                                    i9 = i19;
                                    break;
                                } else {
                                    UnsafeUtil.MEMORY_ACCESSOR.putDouble(obj, j, Double.longBitsToDouble(ArrayDecoders.decodeFixed64(i20, bArr2)));
                                    obj5 = obj;
                                    iDecodeUnknownField = i20 + 8;
                                    i29 = i21 | i42;
                                    i25 = i2;
                                    unsafe5 = unsafe2;
                                    i28 = i8;
                                    bArr5 = bArr2;
                                    i27 = i39;
                                    registers6 = registers2;
                                    i30 = i19;
                                    i26 = i34;
                                }
                            case 1:
                                bArr2 = bArr;
                                unsafe2 = unsafe5;
                                i19 = i30;
                                i20 = iDecodeUnknownField;
                                registers2 = registers6;
                                i21 = i29;
                                if (i36 != 5) {
                                    z3 = true;
                                    z2 = z3;
                                    unsafe = unsafe2;
                                    iDecodeUnknownField = i20;
                                    i7 = i39;
                                    registers6 = registers2;
                                    i29 = i21;
                                    i26 = i34;
                                    z = false;
                                    messageSchema = messageSchema2;
                                    obj2 = obj5;
                                    i9 = i19;
                                    break;
                                } else {
                                    UnsafeUtil.MEMORY_ACCESSOR.putFloat(obj5, j, Float.intBitsToFloat(ArrayDecoders.decodeFixed32(i20, bArr2)));
                                    iDecodeUnknownField = i20 + 4;
                                    i29 = i21 | i42;
                                    i25 = i2;
                                    unsafe5 = unsafe2;
                                    i28 = i8;
                                    bArr5 = bArr2;
                                    i27 = i39;
                                    registers6 = registers2;
                                    i30 = i19;
                                    i26 = i34;
                                }
                            case 2:
                            case 3:
                                bArr2 = bArr;
                                unsafe3 = unsafe5;
                                i19 = i30;
                                i20 = iDecodeUnknownField;
                                registers2 = registers6;
                                i21 = i29;
                                if (i36 != 0) {
                                    unsafe2 = unsafe3;
                                    z3 = true;
                                    z2 = z3;
                                    unsafe = unsafe2;
                                    iDecodeUnknownField = i20;
                                    i7 = i39;
                                    registers6 = registers2;
                                    i29 = i21;
                                    i26 = i34;
                                    z = false;
                                    messageSchema = messageSchema2;
                                    obj2 = obj5;
                                    i9 = i19;
                                    break;
                                } else {
                                    int iDecodeVarint642 = ArrayDecoders.decodeVarint64(bArr2, i20, registers2);
                                    Object obj6 = obj5;
                                    unsafe3.putLong(obj6, j, registers2.long1);
                                    unsafe2 = unsafe3;
                                    obj5 = obj6;
                                    i29 = i21 | i42;
                                    i25 = i2;
                                    iDecodeUnknownField = iDecodeVarint642;
                                    unsafe5 = unsafe2;
                                    i28 = i8;
                                    bArr5 = bArr2;
                                    i27 = i39;
                                    registers6 = registers2;
                                    i30 = i19;
                                    i26 = i34;
                                }
                            case 4:
                            case 11:
                                bArr2 = bArr;
                                unsafe3 = unsafe5;
                                i19 = i30;
                                i20 = iDecodeUnknownField;
                                registers2 = registers6;
                                i21 = i29;
                                if (i36 != 0) {
                                    unsafe2 = unsafe3;
                                    z3 = true;
                                    z2 = z3;
                                    unsafe = unsafe2;
                                    iDecodeUnknownField = i20;
                                    i7 = i39;
                                    registers6 = registers2;
                                    i29 = i21;
                                    i26 = i34;
                                    z = false;
                                    messageSchema = messageSchema2;
                                    obj2 = obj5;
                                    i9 = i19;
                                    break;
                                } else {
                                    int iDecodeVarint322 = ArrayDecoders.decodeVarint32(bArr2, i20, registers2);
                                    unsafe3.putInt(obj5, j, registers2.int1);
                                    i29 = i21 | i42;
                                    i25 = i2;
                                    unsafe5 = unsafe3;
                                    iDecodeUnknownField = iDecodeVarint322;
                                    i28 = i8;
                                    bArr5 = bArr2;
                                    i27 = i39;
                                    registers6 = registers2;
                                    i30 = i19;
                                    i26 = i34;
                                }
                            case 5:
                            case 14:
                                bArr2 = bArr;
                                unsafe2 = unsafe5;
                                i19 = i30;
                                z3 = true;
                                i22 = iDecodeUnknownField;
                                obj3 = obj5;
                                registers3 = registers6;
                                i21 = i29;
                                if (i36 != 1) {
                                    registers2 = registers3;
                                    obj5 = obj3;
                                    i20 = i22;
                                    z2 = z3;
                                    unsafe = unsafe2;
                                    iDecodeUnknownField = i20;
                                    i7 = i39;
                                    registers6 = registers2;
                                    i29 = i21;
                                    i26 = i34;
                                    z = false;
                                    messageSchema = messageSchema2;
                                    obj2 = obj5;
                                    i9 = i19;
                                    break;
                                } else {
                                    registers2 = registers3;
                                    unsafe2.putLong(obj3, j, ArrayDecoders.decodeFixed64(i22, bArr2));
                                    obj5 = obj3;
                                    iDecodeUnknownField = i22 + 8;
                                    i29 = i21 | i42;
                                    i25 = i2;
                                    unsafe5 = unsafe2;
                                    i28 = i8;
                                    bArr5 = bArr2;
                                    i27 = i39;
                                    registers6 = registers2;
                                    i30 = i19;
                                    i26 = i34;
                                }
                            case 6:
                            case 13:
                                bArr4 = bArr;
                                unsafe2 = unsafe5;
                                i19 = i30;
                                i22 = iDecodeUnknownField;
                                obj4 = obj5;
                                registers5 = registers6;
                                i21 = i29;
                                if (i36 != 5) {
                                    registers2 = registers5;
                                    obj5 = obj4;
                                    z3 = true;
                                    i20 = i22;
                                    z2 = z3;
                                    unsafe = unsafe2;
                                    iDecodeUnknownField = i20;
                                    i7 = i39;
                                    registers6 = registers2;
                                    i29 = i21;
                                    i26 = i34;
                                    z = false;
                                    messageSchema = messageSchema2;
                                    obj2 = obj5;
                                    i9 = i19;
                                    break;
                                } else {
                                    unsafe2.putInt(obj4, j, ArrayDecoders.decodeFixed32(i22, bArr4));
                                    iDecodeUnknownField = i22 + 4;
                                    i29 = i21 | i42;
                                    i25 = i2;
                                    registers6 = registers5;
                                    obj5 = obj4;
                                    bArr5 = bArr4;
                                    i27 = i39;
                                    i30 = i19;
                                    i26 = i34;
                                    i31 = 1048575;
                                    unsafe5 = unsafe2;
                                    i28 = i8;
                                }
                            case 7:
                                bArr4 = bArr;
                                unsafe2 = unsafe5;
                                i19 = i30;
                                i22 = iDecodeUnknownField;
                                obj4 = obj5;
                                registers5 = registers6;
                                i21 = i29;
                                if (i36 != 0) {
                                    registers2 = registers5;
                                    obj5 = obj4;
                                    z3 = true;
                                    i20 = i22;
                                    z2 = z3;
                                    unsafe = unsafe2;
                                    iDecodeUnknownField = i20;
                                    i7 = i39;
                                    registers6 = registers2;
                                    i29 = i21;
                                    i26 = i34;
                                    z = false;
                                    messageSchema = messageSchema2;
                                    obj2 = obj5;
                                    i9 = i19;
                                    break;
                                } else {
                                    iDecodeUnknownField = ArrayDecoders.decodeVarint64(bArr4, i22, registers5);
                                    UnsafeUtil.MEMORY_ACCESSOR.putBoolean(obj4, j, registers5.long1 != 0);
                                    i29 = i21 | i42;
                                    i25 = i2;
                                    registers6 = registers5;
                                    obj5 = obj4;
                                    bArr5 = bArr4;
                                    i27 = i39;
                                    i30 = i19;
                                    i26 = i34;
                                    i31 = 1048575;
                                    unsafe5 = unsafe2;
                                    i28 = i8;
                                }
                            case 8:
                                bArr4 = bArr;
                                unsafe2 = unsafe5;
                                i19 = i30;
                                i22 = iDecodeUnknownField;
                                obj4 = obj5;
                                registers5 = registers6;
                                i21 = i29;
                                if (i36 != 2) {
                                    registers2 = registers5;
                                    obj5 = obj4;
                                    z3 = true;
                                    i20 = i22;
                                    z2 = z3;
                                    unsafe = unsafe2;
                                    iDecodeUnknownField = i20;
                                    i7 = i39;
                                    registers6 = registers2;
                                    i29 = i21;
                                    i26 = i34;
                                    z = false;
                                    messageSchema = messageSchema2;
                                    obj2 = obj5;
                                    i9 = i19;
                                    break;
                                } else {
                                    iDecodeUnknownField = (536870912 & i40) == 0 ? ArrayDecoders.decodeString(bArr4, i22, registers5) : ArrayDecoders.decodeStringRequireUtf8(bArr4, i22, registers5);
                                    unsafe2.putObject(obj4, j, registers5.object1);
                                    i29 = i21 | i42;
                                    i25 = i2;
                                    registers6 = registers5;
                                    obj5 = obj4;
                                    bArr5 = bArr4;
                                    i27 = i39;
                                    i30 = i19;
                                    i26 = i34;
                                    i31 = 1048575;
                                    unsafe5 = unsafe2;
                                    i28 = i8;
                                }
                            case 9:
                                Unsafe unsafe6 = unsafe5;
                                obj3 = obj5;
                                ArrayDecoders.Registers registers7 = registers6;
                                i22 = iDecodeUnknownField;
                                if (i36 != 2) {
                                    unsafe2 = unsafe6;
                                    registers3 = registers7;
                                    i21 = i29;
                                    i19 = i30;
                                    z3 = true;
                                    registers2 = registers3;
                                    obj5 = obj3;
                                    i20 = i22;
                                    z2 = z3;
                                    unsafe = unsafe2;
                                    iDecodeUnknownField = i20;
                                    i7 = i39;
                                    registers6 = registers2;
                                    i29 = i21;
                                    i26 = i34;
                                    z = false;
                                    messageSchema = messageSchema2;
                                    obj2 = obj5;
                                    i9 = i19;
                                    break;
                                } else {
                                    Object objMutableMessageFieldForMerge = messageSchema2.mutableMessageFieldForMerge(i39, obj3);
                                    unsafe2 = unsafe6;
                                    int iMergeMessageField = ArrayDecoders.mergeMessageField(objMutableMessageFieldForMerge, messageSchema2.getMessageFieldSchema(i39), bArr, i22, i2, registers7);
                                    messageSchema2.storeMessageField(i39, obj3, objMutableMessageFieldForMerge);
                                    i29 |= i42;
                                    i25 = i2;
                                    registers6 = registers7;
                                    iDecodeUnknownField = iMergeMessageField;
                                    obj5 = obj3;
                                    bArr5 = bArr;
                                    i27 = i39;
                                    i26 = i34;
                                    i31 = 1048575;
                                    unsafe5 = unsafe2;
                                    i28 = i8;
                                }
                            case 10:
                                Unsafe unsafe7 = unsafe5;
                                obj3 = obj5;
                                unsafe4 = unsafe7;
                                bArr3 = bArr;
                                registers4 = registers6;
                                i22 = iDecodeUnknownField;
                                if (i36 != 2) {
                                    i21 = i29;
                                    i19 = i30;
                                    z3 = true;
                                    registers2 = registers4;
                                    unsafe2 = unsafe4;
                                    obj5 = obj3;
                                    i20 = i22;
                                    z2 = z3;
                                    unsafe = unsafe2;
                                    iDecodeUnknownField = i20;
                                    i7 = i39;
                                    registers6 = registers2;
                                    i29 = i21;
                                    i26 = i34;
                                    z = false;
                                    messageSchema = messageSchema2;
                                    obj2 = obj5;
                                    i9 = i19;
                                    break;
                                } else {
                                    iDecodeUnknownField = ArrayDecoders.decodeBytes(bArr3, i22, registers4);
                                    unsafe4.putObject(obj3, j, registers4.object1);
                                    i29 |= i42;
                                    Object obj7 = obj3;
                                    unsafe5 = unsafe4;
                                    obj5 = obj7;
                                    i25 = i2;
                                    bArr5 = bArr3;
                                    i28 = i8;
                                    registers6 = registers4;
                                    i27 = i39;
                                    i26 = i34;
                                }
                            case 12:
                                Unsafe unsafe8 = unsafe5;
                                obj3 = obj5;
                                unsafe4 = unsafe8;
                                bArr3 = bArr;
                                registers4 = registers6;
                                i22 = iDecodeUnknownField;
                                if (i36 != 0) {
                                    i21 = i29;
                                    i19 = i30;
                                    z3 = true;
                                    registers2 = registers4;
                                    unsafe2 = unsafe4;
                                    obj5 = obj3;
                                    i20 = i22;
                                    z2 = z3;
                                    unsafe = unsafe2;
                                    iDecodeUnknownField = i20;
                                    i7 = i39;
                                    registers6 = registers2;
                                    i29 = i21;
                                    i26 = i34;
                                    z = false;
                                    messageSchema = messageSchema2;
                                    obj2 = obj5;
                                    i9 = i19;
                                    break;
                                } else {
                                    iDecodeUnknownField = ArrayDecoders.decodeVarint32(bArr3, i22, registers4);
                                    int i45 = registers4.int1;
                                    Internal.EnumVerifier enumFieldVerifier = messageSchema2.getEnumFieldVerifier(i39);
                                    if (enumFieldVerifier == null || enumFieldVerifier.isInRange(i45)) {
                                        unsafe4.putInt(obj3, j, i45);
                                        i29 |= i42;
                                        Object obj72 = obj3;
                                        unsafe5 = unsafe4;
                                        obj5 = obj72;
                                        i25 = i2;
                                        bArr5 = bArr3;
                                        i28 = i8;
                                        registers6 = registers4;
                                        i27 = i39;
                                        i26 = i34;
                                    } else {
                                        getMutableUnknownFields(obj3).storeField(i30, Long.valueOf(i45));
                                        Object obj722 = obj3;
                                        unsafe5 = unsafe4;
                                        obj5 = obj722;
                                        i25 = i2;
                                        bArr5 = bArr3;
                                        i28 = i8;
                                        registers6 = registers4;
                                        i27 = i39;
                                        i26 = i34;
                                    }
                                }
                                break;
                            case 15:
                                Unsafe unsafe9 = unsafe5;
                                obj3 = obj5;
                                unsafe4 = unsafe9;
                                bArr3 = bArr;
                                registers4 = registers6;
                                i22 = iDecodeUnknownField;
                                if (i36 != 0) {
                                    i21 = i29;
                                    i19 = i30;
                                    z3 = true;
                                    registers2 = registers4;
                                    unsafe2 = unsafe4;
                                    obj5 = obj3;
                                    i20 = i22;
                                    z2 = z3;
                                    unsafe = unsafe2;
                                    iDecodeUnknownField = i20;
                                    i7 = i39;
                                    registers6 = registers2;
                                    i29 = i21;
                                    i26 = i34;
                                    z = false;
                                    messageSchema = messageSchema2;
                                    obj2 = obj5;
                                    i9 = i19;
                                    break;
                                } else {
                                    iDecodeUnknownField = ArrayDecoders.decodeVarint32(bArr3, i22, registers4);
                                    unsafe4.putInt(obj3, j, CodedInputStream.decodeZigZag32(registers4.int1));
                                    i29 |= i42;
                                    Object obj7222 = obj3;
                                    unsafe5 = unsafe4;
                                    obj5 = obj7222;
                                    i25 = i2;
                                    bArr5 = bArr3;
                                    i28 = i8;
                                    registers6 = registers4;
                                    i27 = i39;
                                    i26 = i34;
                                }
                            case 16:
                                bArr3 = bArr;
                                registers4 = registers6;
                                i22 = iDecodeUnknownField;
                                if (i36 != 0) {
                                    Unsafe unsafe10 = unsafe5;
                                    obj3 = obj5;
                                    unsafe4 = unsafe10;
                                    i21 = i29;
                                    i19 = i30;
                                    z3 = true;
                                    registers2 = registers4;
                                    unsafe2 = unsafe4;
                                    obj5 = obj3;
                                    i20 = i22;
                                    z2 = z3;
                                    unsafe = unsafe2;
                                    iDecodeUnknownField = i20;
                                    i7 = i39;
                                    registers6 = registers2;
                                    i29 = i21;
                                    i26 = i34;
                                    z = false;
                                    messageSchema = messageSchema2;
                                    obj2 = obj5;
                                    i9 = i19;
                                    break;
                                } else {
                                    int iDecodeVarint643 = ArrayDecoders.decodeVarint64(bArr3, i22, registers4);
                                    Object obj8 = obj5;
                                    Unsafe unsafe11 = unsafe5;
                                    unsafe11.putLong(obj8, j, CodedInputStream.decodeZigZag64(registers4.long1));
                                    i29 |= i42;
                                    unsafe5 = unsafe11;
                                    obj5 = obj8;
                                    i25 = i2;
                                    iDecodeUnknownField = iDecodeVarint643;
                                    bArr5 = bArr3;
                                    i28 = i8;
                                    registers6 = registers4;
                                    i27 = i39;
                                    i26 = i34;
                                }
                            case 17:
                                if (i36 != 3) {
                                    unsafe2 = unsafe5;
                                    i19 = i30;
                                    z3 = true;
                                    i20 = iDecodeUnknownField;
                                    registers2 = registers6;
                                    i21 = i29;
                                    z2 = z3;
                                    unsafe = unsafe2;
                                    iDecodeUnknownField = i20;
                                    i7 = i39;
                                    registers6 = registers2;
                                    i29 = i21;
                                    i26 = i34;
                                    z = false;
                                    messageSchema = messageSchema2;
                                    obj2 = obj5;
                                    i9 = i19;
                                    break;
                                } else {
                                    Object objMutableMessageFieldForMerge2 = messageSchema2.mutableMessageFieldForMerge(i39, obj5);
                                    ArrayDecoders.Registers registers8 = registers6;
                                    int proto2Message2 = ((MessageSchema) messageSchema2.getMessageFieldSchema(i39)).parseProto2Message(objMutableMessageFieldForMerge2, bArr, iDecodeUnknownField, i25, (i34 << 3) | 4, registers8);
                                    registers4 = registers8;
                                    bArr3 = bArr;
                                    registers4.object1 = objMutableMessageFieldForMerge2;
                                    messageSchema2.storeMessageField(i39, obj5, objMutableMessageFieldForMerge2);
                                    i29 |= i42;
                                    i25 = i2;
                                    iDecodeUnknownField = proto2Message2;
                                    bArr5 = bArr3;
                                    i28 = i8;
                                    registers6 = registers4;
                                    i27 = i39;
                                    i26 = i34;
                                }
                            default:
                                unsafe2 = unsafe5;
                                i19 = i30;
                                z3 = true;
                                i20 = iDecodeUnknownField;
                                registers2 = registers6;
                                i21 = i29;
                                z2 = z3;
                                unsafe = unsafe2;
                                iDecodeUnknownField = i20;
                                i7 = i39;
                                registers6 = registers2;
                                i29 = i21;
                                i26 = i34;
                                z = false;
                                messageSchema = messageSchema2;
                                obj2 = obj5;
                                i9 = i19;
                                break;
                        }
                    } else {
                        i10 = i34;
                        ArrayDecoders.Registers registers9 = registers6;
                        Unsafe unsafe12 = unsafe5;
                        int i46 = i28;
                        if (iType == 27) {
                            if (i36 == 2) {
                                Internal.ProtobufList protobufListMutableCopyWithCapacity = (Internal.ProtobufList) unsafe12.getObject(obj5, j);
                                if (!((AbstractProtobufList) protobufListMutableCopyWithCapacity).isMutable) {
                                    int size = protobufListMutableCopyWithCapacity.size();
                                    protobufListMutableCopyWithCapacity = protobufListMutableCopyWithCapacity.mutableCopyWithCapacity(size == 0 ? 10 : size * 2);
                                    unsafe12.putObject(obj5, j, protobufListMutableCopyWithCapacity);
                                }
                                i25 = i2;
                                registers6 = registers;
                                iDecodeUnknownField = ArrayDecoders.decodeMessageList(messageSchema2.getMessageFieldSchema(i39), i30, bArr, iDecodeUnknownField, i2, protobufListMutableCopyWithCapacity, registers9);
                                i30 = i30;
                                unsafe5 = unsafe12;
                                i27 = i39;
                                i28 = i46;
                                i26 = i10;
                                i31 = 1048575;
                                obj5 = obj;
                                bArr5 = bArr;
                            } else {
                                i23 = iDecodeUnknownField;
                                unsafe = unsafe12;
                                i24 = i29;
                                i30 = i30;
                                z = false;
                                z2 = true;
                            }
                        } else if (iType <= 49) {
                            unsafe = unsafe12;
                            i24 = i29;
                            z = false;
                            z2 = true;
                            int repeatedField = messageSchema2.parseRepeatedField(obj, bArr, iDecodeUnknownField, i2, i30, i10, i36, i39, i40, iType, j, registers);
                            i30 = i30;
                            i39 = i39;
                            if (repeatedField != iDecodeUnknownField) {
                                messageSchema2 = this;
                                obj5 = obj;
                                bArr5 = bArr;
                                i25 = i2;
                                registers6 = registers;
                                iDecodeUnknownField = repeatedField;
                                i27 = i39;
                                i29 = i24;
                                i28 = i46;
                                i26 = i10;
                            } else {
                                messageSchema = this;
                                obj2 = obj;
                                registers6 = registers;
                                iDecodeUnknownField = repeatedField;
                                i7 = i39;
                                i9 = i30;
                                i29 = i24;
                                i8 = i46;
                                i26 = i10;
                            }
                        } else {
                            i30 = i30;
                            i23 = iDecodeUnknownField;
                            unsafe = unsafe12;
                            i24 = i29;
                            z = false;
                            z2 = true;
                            if (iType == 50) {
                                if (i36 == 2) {
                                    int mapField = parseMapField(obj, bArr, i23, i2, i39, j, registers);
                                    if (mapField != i23) {
                                        messageSchema2 = this;
                                        obj5 = obj;
                                        bArr5 = bArr;
                                        i25 = i2;
                                        registers6 = registers;
                                        iDecodeUnknownField = mapField;
                                        i27 = i39;
                                        i29 = i24;
                                        i28 = i46;
                                        i26 = i10;
                                    } else {
                                        messageSchema = this;
                                        obj2 = obj;
                                        registers6 = registers;
                                        iDecodeUnknownField = mapField;
                                    }
                                }
                                i7 = i39;
                                i9 = i30;
                                i29 = i24;
                                i8 = i46;
                                i26 = i10;
                            } else {
                                i26 = i10;
                                int oneofField = parseOneofField(obj, bArr, i23, i2, i30, i26, i36, i40, iType, j, i39, registers);
                                messageSchema = this;
                                obj2 = obj;
                                i9 = i30;
                                registers6 = registers;
                                if (oneofField != i23) {
                                    bArr5 = bArr;
                                    i25 = i2;
                                    i30 = i9;
                                    iDecodeUnknownField = oneofField;
                                    messageSchema2 = messageSchema;
                                    obj5 = obj2;
                                    i27 = i39;
                                    i29 = i24;
                                    i28 = i46;
                                } else {
                                    iDecodeUnknownField = oneofField;
                                    i7 = i39;
                                    i29 = i24;
                                    i8 = i46;
                                }
                            }
                        }
                        messageSchema = this;
                        obj2 = obj;
                        registers6 = registers;
                        iDecodeUnknownField = i23;
                        i7 = i39;
                        i9 = i30;
                        i29 = i24;
                        i8 = i46;
                        i26 = i10;
                    }
                    unsafe5 = unsafe;
                }
                if (i9 == i3 && i3 != 0) {
                    i4 = i2;
                    i30 = i9;
                    i6 = i8;
                    i5 = 1048575;
                } else if (messageSchema.hasExtensions) {
                    ExtensionRegistryLite emptyRegistry = ExtensionRegistryLite.getEmptyRegistry();
                    ExtensionRegistryLite extensionRegistryLite = registers6.extensionRegistry;
                    if (extensionRegistryLite != emptyRegistry) {
                        GeneratedMessageLite.GeneratedExtension generatedExtension = (GeneratedMessageLite.GeneratedExtension) extensionRegistryLite.extensionsByNumber.get(new ExtensionRegistryLite.ObjectIntPair(messageSchema.defaultInstance, i26));
                        if (generatedExtension == null) {
                            i15 = i9;
                            proto2Message = ArrayDecoders.decodeUnknownField(i9, bArr, iDecodeUnknownField, i2, getMutableUnknownFields(obj2), registers6);
                            i10 = i26;
                            i11 = i7;
                            i12 = i29;
                        } else {
                            int i47 = i9;
                            int i48 = iDecodeUnknownField;
                            GeneratedMessageLite.ExtendableMessage extendableMessage = (GeneratedMessageLite.ExtendableMessage) obj2;
                            FieldSet fieldSet = extendableMessage.extensions;
                            if (fieldSet.isImmutable) {
                                extendableMessage.extensions = fieldSet.m3287clone();
                            }
                            FieldSet fieldSet2 = extendableMessage.extensions;
                            GeneratedMessageLite.ExtensionDescriptor extensionDescriptor = generatedExtension.descriptor;
                            boolean z4 = extensionDescriptor.isRepeated;
                            UnknownFieldSchema unknownFieldSchema = messageSchema.unknownFieldSchema;
                            if (!z4 || !extensionDescriptor.isPacked) {
                                i12 = i29;
                                UnknownFieldSchema unknownFieldSchema2 = unknownFieldSchema;
                                i15 = i47;
                                WireFormat$FieldType wireFormat$FieldType = extensionDescriptor.type;
                                i11 = i7;
                                if (wireFormat$FieldType != WireFormat$FieldType.ENUM) {
                                    int i49 = ArrayDecoders.AnonymousClass1.$SwitchMap$com$google$protobuf$WireFormat$FieldType[wireFormat$FieldType.ordinal()];
                                    MessageLite messageLite = generatedExtension.messageDefaultInstance;
                                    switch (i49) {
                                        case 1:
                                            i16 = i48;
                                            i10 = i26;
                                            objValueOf = Double.valueOf(Double.longBitsToDouble(ArrayDecoders.decodeFixed64(i16, bArr)));
                                            iDecodeVarint64 = i16 + 8;
                                            if (extensionDescriptor.isRepeated) {
                                            }
                                            proto2Message = iDecodeVarint64;
                                            break;
                                        case 2:
                                            i17 = i48;
                                            i10 = i26;
                                            objValueOf = Float.valueOf(Float.intBitsToFloat(ArrayDecoders.decodeFixed32(i17, bArr)));
                                            iDecodeVarint64 = i17 + 4;
                                            if (extensionDescriptor.isRepeated) {
                                            }
                                            proto2Message = iDecodeVarint64;
                                            break;
                                        case 3:
                                        case 4:
                                            i10 = i26;
                                            iDecodeVarint64 = ArrayDecoders.decodeVarint64(bArr, i48, registers6);
                                            objValueOf = Long.valueOf(registers6.long1);
                                            if (extensionDescriptor.isRepeated) {
                                            }
                                            proto2Message = iDecodeVarint64;
                                            break;
                                        case 5:
                                        case 6:
                                            i10 = i26;
                                            iDecodeVarint64 = ArrayDecoders.decodeVarint32(bArr, i48, registers6);
                                            objValueOf = Integer.valueOf(registers6.int1);
                                            if (extensionDescriptor.isRepeated) {
                                            }
                                            proto2Message = iDecodeVarint64;
                                            break;
                                        case 7:
                                        case 8:
                                            i16 = i48;
                                            i10 = i26;
                                            objValueOf = Long.valueOf(ArrayDecoders.decodeFixed64(i16, bArr));
                                            iDecodeVarint64 = i16 + 8;
                                            if (extensionDescriptor.isRepeated) {
                                            }
                                            proto2Message = iDecodeVarint64;
                                            break;
                                        case 9:
                                        case 10:
                                            i17 = i48;
                                            i10 = i26;
                                            objValueOf = Integer.valueOf(ArrayDecoders.decodeFixed32(i17, bArr));
                                            iDecodeVarint64 = i17 + 4;
                                            if (extensionDescriptor.isRepeated) {
                                            }
                                            proto2Message = iDecodeVarint64;
                                            break;
                                        case 11:
                                            i10 = i26;
                                            iDecodeVarint64 = ArrayDecoders.decodeVarint64(bArr, i48, registers6);
                                            if (registers6.long1 == 0) {
                                                z2 = z;
                                            }
                                            objValueOf = Boolean.valueOf(z2);
                                            if (extensionDescriptor.isRepeated) {
                                            }
                                            proto2Message = iDecodeVarint64;
                                            break;
                                        case 12:
                                            i10 = i26;
                                            iDecodeVarint64 = ArrayDecoders.decodeVarint32(bArr, i48, registers6);
                                            objValueOf = Integer.valueOf(CodedInputStream.decodeZigZag32(registers6.int1));
                                            if (extensionDescriptor.isRepeated) {
                                            }
                                            proto2Message = iDecodeVarint64;
                                            break;
                                        case 13:
                                            i10 = i26;
                                            iDecodeVarint64 = ArrayDecoders.decodeVarint64(bArr, i48, registers6);
                                            objValueOf = Long.valueOf(CodedInputStream.decodeZigZag64(registers6.long1));
                                            if (extensionDescriptor.isRepeated) {
                                            }
                                            proto2Message = iDecodeVarint64;
                                            break;
                                        case 14:
                                            throw new IllegalStateException("Shouldn't reach here.");
                                        case 15:
                                            i10 = i26;
                                            iDecodeVarint64 = ArrayDecoders.decodeBytes(bArr, i48, registers6);
                                            objValueOf = registers6.object1;
                                            if (extensionDescriptor.isRepeated) {
                                            }
                                            proto2Message = iDecodeVarint64;
                                            break;
                                        case 16:
                                            i10 = i26;
                                            iDecodeVarint64 = ArrayDecoders.decodeString(bArr, i48, registers6);
                                            objValueOf = registers6.object1;
                                            if (extensionDescriptor.isRepeated) {
                                            }
                                            proto2Message = iDecodeVarint64;
                                            break;
                                        case 17:
                                            int i50 = (i26 << 3) | 4;
                                            Schema schemaSchemaFor = Protobuf.INSTANCE.schemaFor((Class) messageLite.getClass());
                                            if (!extensionDescriptor.isRepeated) {
                                                i10 = i26;
                                                Object field = fieldSet2.getField(extensionDescriptor);
                                                if (field == null) {
                                                    field = schemaSchemaFor.newInstance();
                                                    fieldSet2.setField(extensionDescriptor, field);
                                                }
                                                Object obj9 = field;
                                                proto2Message = ((MessageSchema) schemaSchemaFor).parseProto2Message(obj9, bArr, i48, i2, i50, registers);
                                                registers.object1 = obj9;
                                                break;
                                            } else {
                                                GeneratedMessageLite generatedMessageLiteNewInstance = schemaSchemaFor.newInstance();
                                                i10 = i26;
                                                proto2Message = ((MessageSchema) schemaSchemaFor).parseProto2Message(generatedMessageLiteNewInstance, bArr, i48, i2, i50, registers);
                                                registers.object1 = generatedMessageLiteNewInstance;
                                                schemaSchemaFor.makeImmutable(generatedMessageLiteNewInstance);
                                                registers.object1 = generatedMessageLiteNewInstance;
                                                fieldSet2.addRepeatedField(extensionDescriptor, generatedMessageLiteNewInstance);
                                                break;
                                            }
                                        case 18:
                                            Schema schemaSchemaFor2 = Protobuf.INSTANCE.schemaFor((Class) messageLite.getClass());
                                            if (extensionDescriptor.isRepeated) {
                                                int iDecodeMessageField = ArrayDecoders.decodeMessageField(schemaSchemaFor2, bArr, i48, i2, registers6);
                                                fieldSet2.addRepeatedField(extensionDescriptor, registers6.object1);
                                                proto2Message = iDecodeMessageField;
                                                i10 = i26;
                                                break;
                                            } else {
                                                Object field2 = fieldSet2.getField(extensionDescriptor);
                                                if (field2 == null) {
                                                    field2 = schemaSchemaFor2.newInstance();
                                                    fieldSet2.setField(extensionDescriptor, field2);
                                                }
                                                proto2Message = ArrayDecoders.mergeMessageField(field2, schemaSchemaFor2, bArr, i48, i2, registers6);
                                                i10 = i26;
                                            }
                                        default:
                                            iDecodeVarint64 = i48;
                                            i10 = i26;
                                            objValueOf = null;
                                            if (extensionDescriptor.isRepeated) {
                                            }
                                            proto2Message = iDecodeVarint64;
                                            break;
                                    }
                                } else {
                                    int iDecodeVarint323 = ArrayDecoders.decodeVarint32(bArr, i48, registers6);
                                    if (extensionDescriptor.enumTypeMap.findValueByNumber(registers6.int1) == null) {
                                        SchemaUtil.storeUnknownEnum(extendableMessage, i26, registers6.int1, null, unknownFieldSchema2);
                                        proto2Message = iDecodeVarint323;
                                        i10 = i26;
                                    } else {
                                        objValueOf = Integer.valueOf(registers6.int1);
                                        i10 = i26;
                                        iDecodeVarint64 = iDecodeVarint323;
                                        if (extensionDescriptor.isRepeated) {
                                            fieldSet2.addRepeatedField(extensionDescriptor, objValueOf);
                                        } else {
                                            fieldSet2.setField(extensionDescriptor, objValueOf);
                                        }
                                        proto2Message = iDecodeVarint64;
                                    }
                                }
                            } else {
                                switch (ArrayDecoders.AnonymousClass1.$SwitchMap$com$google$protobuf$WireFormat$FieldType[extensionDescriptor.type.ordinal()]) {
                                    case 1:
                                        i12 = i29;
                                        i15 = i47;
                                        DoubleArrayList doubleArrayList = new DoubleArrayList();
                                        iDecodePackedDoubleList = ArrayDecoders.decodePackedDoubleList(bArr, i48, doubleArrayList, registers6);
                                        fieldSet2.setField(extensionDescriptor, doubleArrayList);
                                        proto2Message = iDecodePackedDoubleList;
                                        i10 = i26;
                                        i11 = i7;
                                        break;
                                    case 2:
                                        i12 = i29;
                                        i15 = i47;
                                        FloatArrayList floatArrayList = new FloatArrayList();
                                        iDecodePackedDoubleList = ArrayDecoders.decodePackedFloatList(bArr, i48, floatArrayList, registers6);
                                        fieldSet2.setField(extensionDescriptor, floatArrayList);
                                        proto2Message = iDecodePackedDoubleList;
                                        i10 = i26;
                                        i11 = i7;
                                        break;
                                    case 3:
                                    case 4:
                                        LongArrayList longArrayList = new LongArrayList();
                                        int iDecodeVarint324 = ArrayDecoders.decodeVarint32(bArr, i48, registers6);
                                        int i51 = registers6.int1 + iDecodeVarint324;
                                        while (iDecodeVarint324 < i51) {
                                            iDecodeVarint324 = ArrayDecoders.decodeVarint64(bArr, iDecodeVarint324, registers6);
                                            longArrayList.addLong(registers6.long1);
                                            i29 = i29;
                                            i47 = i47;
                                        }
                                        i12 = i29;
                                        i15 = i47;
                                        if (iDecodeVarint324 != i51) {
                                            throw InvalidProtocolBufferException.truncatedMessage();
                                        }
                                        fieldSet2.setField(extensionDescriptor, longArrayList);
                                        proto2Message = iDecodeVarint324;
                                        i10 = i26;
                                        i11 = i7;
                                        break;
                                    case 5:
                                    case 6:
                                        IntArrayList intArrayList = new IntArrayList();
                                        iDecodePackedVarint32List = ArrayDecoders.decodePackedVarint32List(bArr, i48, intArrayList, registers6);
                                        fieldSet2.setField(extensionDescriptor, intArrayList);
                                        proto2Message = iDecodePackedVarint32List;
                                        i10 = i26;
                                        i11 = i7;
                                        i12 = i29;
                                        i15 = i47;
                                        break;
                                    case 7:
                                    case 8:
                                        LongArrayList longArrayList2 = new LongArrayList();
                                        iDecodePackedVarint32List = ArrayDecoders.decodePackedFixed64List(bArr, i48, longArrayList2, registers6);
                                        fieldSet2.setField(extensionDescriptor, longArrayList2);
                                        proto2Message = iDecodePackedVarint32List;
                                        i10 = i26;
                                        i11 = i7;
                                        i12 = i29;
                                        i15 = i47;
                                        break;
                                    case 9:
                                    case 10:
                                        IntArrayList intArrayList2 = new IntArrayList();
                                        iDecodePackedVarint32List = ArrayDecoders.decodePackedFixed32List(bArr, i48, intArrayList2, registers6);
                                        fieldSet2.setField(extensionDescriptor, intArrayList2);
                                        proto2Message = iDecodePackedVarint32List;
                                        i10 = i26;
                                        i11 = i7;
                                        i12 = i29;
                                        i15 = i47;
                                        break;
                                    case 11:
                                        BooleanArrayList booleanArrayList = new BooleanArrayList();
                                        iDecodePackedVarint32List = ArrayDecoders.decodePackedBoolList(bArr, i48, booleanArrayList, registers6);
                                        fieldSet2.setField(extensionDescriptor, booleanArrayList);
                                        proto2Message = iDecodePackedVarint32List;
                                        i10 = i26;
                                        i11 = i7;
                                        i12 = i29;
                                        i15 = i47;
                                        break;
                                    case 12:
                                        IntArrayList intArrayList3 = new IntArrayList();
                                        iDecodePackedVarint32List = ArrayDecoders.decodePackedSInt32List(bArr, i48, intArrayList3, registers6);
                                        fieldSet2.setField(extensionDescriptor, intArrayList3);
                                        proto2Message = iDecodePackedVarint32List;
                                        i10 = i26;
                                        i11 = i7;
                                        i12 = i29;
                                        i15 = i47;
                                        break;
                                    case 13:
                                        LongArrayList longArrayList3 = new LongArrayList();
                                        iDecodePackedVarint32List = ArrayDecoders.decodePackedSInt64List(bArr, i48, longArrayList3, registers6);
                                        fieldSet2.setField(extensionDescriptor, longArrayList3);
                                        proto2Message = iDecodePackedVarint32List;
                                        i10 = i26;
                                        i11 = i7;
                                        i12 = i29;
                                        i15 = i47;
                                        break;
                                    case 14:
                                        IntArrayList intArrayList4 = new IntArrayList();
                                        iDecodePackedVarint32List = ArrayDecoders.decodePackedVarint32List(bArr, i48, intArrayList4, registers6);
                                        SchemaUtil.filterUnknownEnumList(extendableMessage, i26, intArrayList4, extensionDescriptor.enumTypeMap, (Object) null, unknownFieldSchema);
                                        fieldSet2.setField(extensionDescriptor, intArrayList4);
                                        proto2Message = iDecodePackedVarint32List;
                                        i10 = i26;
                                        i11 = i7;
                                        i12 = i29;
                                        i15 = i47;
                                        break;
                                    default:
                                        throw new IllegalStateException("Type cannot be packed: " + extensionDescriptor.type);
                                }
                            }
                        }
                        i14 = i2;
                        iDecodeUnknownField = proto2Message;
                        i13 = i15;
                    } else {
                        i10 = i26;
                        i11 = i7;
                        i12 = i29;
                        i13 = i9;
                        i14 = i2;
                        iDecodeUnknownField = ArrayDecoders.decodeUnknownField(i13, bArr, iDecodeUnknownField, i2, getMutableUnknownFields(obj2), registers6);
                    }
                    bArr5 = bArr;
                    i29 = i12;
                    registers6 = registers;
                    i30 = i13;
                    i25 = i14;
                    messageSchema2 = messageSchema;
                    obj5 = obj2;
                    i28 = i8;
                    i27 = i11;
                    i26 = i10;
                    unsafe5 = unsafe;
                }
            }
            i4 = i25;
            int i52 = i28;
            unsafe = unsafe5;
            messageSchema = messageSchema2;
            obj2 = obj5;
            i5 = i31;
            i6 = i52;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public final int parseRepeatedField(Object obj, byte[] bArr, int i, int i2, int i3, int i4, int i5, int i6, long j, int i7, long j2, ArrayDecoders.Registers registers) throws InvalidProtocolBufferException {
        int iDecodeVarint32List;
        Unsafe unsafe = UNSAFE;
        Internal.ProtobufList protobufListMutableCopyWithCapacity = (Internal.ProtobufList) unsafe.getObject(obj, j2);
        if (!((AbstractProtobufList) protobufListMutableCopyWithCapacity).isMutable) {
            int size = protobufListMutableCopyWithCapacity.size();
            protobufListMutableCopyWithCapacity = protobufListMutableCopyWithCapacity.mutableCopyWithCapacity(size == 0 ? 10 : size * 2);
            unsafe.putObject(obj, j2, protobufListMutableCopyWithCapacity);
        }
        Internal.ProtobufList protobufList = protobufListMutableCopyWithCapacity;
        switch (i7) {
            case 18:
            case 35:
                if (i5 == 2) {
                    return ArrayDecoders.decodePackedDoubleList(bArr, i, protobufList, registers);
                }
                if (i5 == 1) {
                    DoubleArrayList doubleArrayList = (DoubleArrayList) protobufList;
                    doubleArrayList.addDouble(Double.longBitsToDouble(ArrayDecoders.decodeFixed64(i, bArr)));
                    int i8 = i + 8;
                    while (i8 < i2) {
                        int iDecodeVarint32 = ArrayDecoders.decodeVarint32(bArr, i8, registers);
                        if (i3 != registers.int1) {
                            return i8;
                        }
                        doubleArrayList.addDouble(Double.longBitsToDouble(ArrayDecoders.decodeFixed64(iDecodeVarint32, bArr)));
                        i8 = iDecodeVarint32 + 8;
                    }
                    return i8;
                }
                return i;
            case 19:
            case 36:
                if (i5 == 2) {
                    return ArrayDecoders.decodePackedFloatList(bArr, i, protobufList, registers);
                }
                if (i5 == 5) {
                    FloatArrayList floatArrayList = (FloatArrayList) protobufList;
                    floatArrayList.addFloat(Float.intBitsToFloat(ArrayDecoders.decodeFixed32(i, bArr)));
                    int i9 = i + 4;
                    while (i9 < i2) {
                        int iDecodeVarint322 = ArrayDecoders.decodeVarint32(bArr, i9, registers);
                        if (i3 != registers.int1) {
                            return i9;
                        }
                        floatArrayList.addFloat(Float.intBitsToFloat(ArrayDecoders.decodeFixed32(iDecodeVarint322, bArr)));
                        i9 = iDecodeVarint322 + 4;
                    }
                    return i9;
                }
                return i;
            case 20:
            case 21:
            case 37:
            case 38:
                if (i5 == 2) {
                    LongArrayList longArrayList = (LongArrayList) protobufList;
                    int iDecodeVarint323 = ArrayDecoders.decodeVarint32(bArr, i, registers);
                    int i10 = registers.int1 + iDecodeVarint323;
                    while (iDecodeVarint323 < i10) {
                        iDecodeVarint323 = ArrayDecoders.decodeVarint64(bArr, iDecodeVarint323, registers);
                        longArrayList.addLong(registers.long1);
                    }
                    if (iDecodeVarint323 == i10) {
                        return iDecodeVarint323;
                    }
                    throw InvalidProtocolBufferException.truncatedMessage();
                }
                if (i5 == 0) {
                    LongArrayList longArrayList2 = (LongArrayList) protobufList;
                    int iDecodeVarint64 = ArrayDecoders.decodeVarint64(bArr, i, registers);
                    longArrayList2.addLong(registers.long1);
                    while (iDecodeVarint64 < i2) {
                        int iDecodeVarint324 = ArrayDecoders.decodeVarint32(bArr, iDecodeVarint64, registers);
                        if (i3 != registers.int1) {
                            return iDecodeVarint64;
                        }
                        iDecodeVarint64 = ArrayDecoders.decodeVarint64(bArr, iDecodeVarint324, registers);
                        longArrayList2.addLong(registers.long1);
                    }
                    return iDecodeVarint64;
                }
                return i;
            case 22:
            case 29:
            case 39:
            case 43:
                if (i5 == 2) {
                    return ArrayDecoders.decodePackedVarint32List(bArr, i, protobufList, registers);
                }
                if (i5 == 0) {
                    return ArrayDecoders.decodeVarint32List(i3, bArr, i, i2, protobufList, registers);
                }
                return i;
            case 23:
            case 32:
            case 40:
            case 46:
                if (i5 == 2) {
                    return ArrayDecoders.decodePackedFixed64List(bArr, i, protobufList, registers);
                }
                if (i5 == 1) {
                    LongArrayList longArrayList3 = (LongArrayList) protobufList;
                    longArrayList3.addLong(ArrayDecoders.decodeFixed64(i, bArr));
                    int i11 = i + 8;
                    while (i11 < i2) {
                        int iDecodeVarint325 = ArrayDecoders.decodeVarint32(bArr, i11, registers);
                        if (i3 != registers.int1) {
                            return i11;
                        }
                        longArrayList3.addLong(ArrayDecoders.decodeFixed64(iDecodeVarint325, bArr));
                        i11 = iDecodeVarint325 + 8;
                    }
                    return i11;
                }
                return i;
            case 24:
            case 31:
            case 41:
            case 45:
                if (i5 == 2) {
                    return ArrayDecoders.decodePackedFixed32List(bArr, i, protobufList, registers);
                }
                if (i5 == 5) {
                    IntArrayList intArrayList = (IntArrayList) protobufList;
                    intArrayList.addInt(ArrayDecoders.decodeFixed32(i, bArr));
                    int i12 = i + 4;
                    while (i12 < i2) {
                        int iDecodeVarint326 = ArrayDecoders.decodeVarint32(bArr, i12, registers);
                        if (i3 != registers.int1) {
                            return i12;
                        }
                        intArrayList.addInt(ArrayDecoders.decodeFixed32(iDecodeVarint326, bArr));
                        i12 = iDecodeVarint326 + 4;
                    }
                    return i12;
                }
                return i;
            case 25:
            case 42:
                if (i5 == 2) {
                    return ArrayDecoders.decodePackedBoolList(bArr, i, protobufList, registers);
                }
                if (i5 == 0) {
                    BooleanArrayList booleanArrayList = (BooleanArrayList) protobufList;
                    int iDecodeVarint642 = ArrayDecoders.decodeVarint64(bArr, i, registers);
                    booleanArrayList.addBoolean(registers.long1 != 0);
                    while (iDecodeVarint642 < i2) {
                        int iDecodeVarint327 = ArrayDecoders.decodeVarint32(bArr, iDecodeVarint642, registers);
                        if (i3 != registers.int1) {
                            return iDecodeVarint642;
                        }
                        iDecodeVarint642 = ArrayDecoders.decodeVarint64(bArr, iDecodeVarint327, registers);
                        booleanArrayList.addBoolean(registers.long1 != 0);
                    }
                    return iDecodeVarint642;
                }
                return i;
            case 26:
                if (i5 == 2) {
                    if ((j & 536870912) == 0) {
                        int iDecodeVarint328 = ArrayDecoders.decodeVarint32(bArr, i, registers);
                        int i13 = registers.int1;
                        if (i13 < 0) {
                            throw InvalidProtocolBufferException.negativeSize();
                        }
                        if (i13 == 0) {
                            protobufList.add("");
                        } else {
                            protobufList.add(new String(bArr, iDecodeVarint328, i13, Internal.UTF_8));
                            iDecodeVarint328 += i13;
                        }
                        while (iDecodeVarint328 < i2) {
                            int iDecodeVarint329 = ArrayDecoders.decodeVarint32(bArr, iDecodeVarint328, registers);
                            if (i3 != registers.int1) {
                                return iDecodeVarint328;
                            }
                            iDecodeVarint328 = ArrayDecoders.decodeVarint32(bArr, iDecodeVarint329, registers);
                            int i14 = registers.int1;
                            if (i14 < 0) {
                                throw InvalidProtocolBufferException.negativeSize();
                            }
                            if (i14 == 0) {
                                protobufList.add("");
                            } else {
                                protobufList.add(new String(bArr, iDecodeVarint328, i14, Internal.UTF_8));
                                iDecodeVarint328 += i14;
                            }
                        }
                        return iDecodeVarint328;
                    }
                    int iDecodeVarint3210 = ArrayDecoders.decodeVarint32(bArr, i, registers);
                    int i15 = registers.int1;
                    if (i15 < 0) {
                        throw InvalidProtocolBufferException.negativeSize();
                    }
                    if (i15 == 0) {
                        protobufList.add("");
                    } else {
                        int i16 = iDecodeVarint3210 + i15;
                        if (!Utf8.processor.isValidUtf8(iDecodeVarint3210, i16, bArr)) {
                            throw InvalidProtocolBufferException.invalidUtf8();
                        }
                        protobufList.add(new String(bArr, iDecodeVarint3210, i15, Internal.UTF_8));
                        iDecodeVarint3210 = i16;
                    }
                    while (iDecodeVarint3210 < i2) {
                        int iDecodeVarint3211 = ArrayDecoders.decodeVarint32(bArr, iDecodeVarint3210, registers);
                        if (i3 != registers.int1) {
                            return iDecodeVarint3210;
                        }
                        iDecodeVarint3210 = ArrayDecoders.decodeVarint32(bArr, iDecodeVarint3211, registers);
                        int i17 = registers.int1;
                        if (i17 < 0) {
                            throw InvalidProtocolBufferException.negativeSize();
                        }
                        if (i17 == 0) {
                            protobufList.add("");
                        } else {
                            int i18 = iDecodeVarint3210 + i17;
                            if (!Utf8.processor.isValidUtf8(iDecodeVarint3210, i18, bArr)) {
                                throw InvalidProtocolBufferException.invalidUtf8();
                            }
                            protobufList.add(new String(bArr, iDecodeVarint3210, i17, Internal.UTF_8));
                            iDecodeVarint3210 = i18;
                        }
                    }
                    return iDecodeVarint3210;
                }
                return i;
            case 27:
                if (i5 == 2) {
                    return ArrayDecoders.decodeMessageList(getMessageFieldSchema(i6), i3, bArr, i, i2, protobufList, registers);
                }
                return i;
            case 28:
                if (i5 == 2) {
                    int iDecodeVarint3212 = ArrayDecoders.decodeVarint32(bArr, i, registers);
                    int i19 = registers.int1;
                    if (i19 < 0) {
                        throw InvalidProtocolBufferException.negativeSize();
                    }
                    if (i19 > bArr.length - iDecodeVarint3212) {
                        throw InvalidProtocolBufferException.truncatedMessage();
                    }
                    if (i19 == 0) {
                        protobufList.add(ByteString.EMPTY);
                    } else {
                        protobufList.add(ByteString.copyFrom(iDecodeVarint3212, i19, bArr));
                        iDecodeVarint3212 += i19;
                    }
                    while (iDecodeVarint3212 < i2) {
                        int iDecodeVarint3213 = ArrayDecoders.decodeVarint32(bArr, iDecodeVarint3212, registers);
                        if (i3 != registers.int1) {
                            return iDecodeVarint3212;
                        }
                        iDecodeVarint3212 = ArrayDecoders.decodeVarint32(bArr, iDecodeVarint3213, registers);
                        int i20 = registers.int1;
                        if (i20 < 0) {
                            throw InvalidProtocolBufferException.negativeSize();
                        }
                        if (i20 > bArr.length - iDecodeVarint3212) {
                            throw InvalidProtocolBufferException.truncatedMessage();
                        }
                        if (i20 == 0) {
                            protobufList.add(ByteString.EMPTY);
                        } else {
                            protobufList.add(ByteString.copyFrom(iDecodeVarint3212, i20, bArr));
                            iDecodeVarint3212 += i20;
                        }
                    }
                    return iDecodeVarint3212;
                }
                return i;
            case 30:
            case 44:
                if (i5 != 2) {
                    if (i5 == 0) {
                        iDecodeVarint32List = ArrayDecoders.decodeVarint32List(i3, bArr, i, i2, protobufList, registers);
                    }
                    return i;
                }
                iDecodeVarint32List = ArrayDecoders.decodePackedVarint32List(bArr, i, protobufList, registers);
                SchemaUtil.filterUnknownEnumList(obj, i4, protobufList, getEnumFieldVerifier(i6), (Object) null, this.unknownFieldSchema);
                return iDecodeVarint32List;
            case 33:
            case 47:
                if (i5 == 2) {
                    return ArrayDecoders.decodePackedSInt32List(bArr, i, protobufList, registers);
                }
                if (i5 == 0) {
                    IntArrayList intArrayList2 = (IntArrayList) protobufList;
                    int iDecodeVarint3214 = ArrayDecoders.decodeVarint32(bArr, i, registers);
                    intArrayList2.addInt(CodedInputStream.decodeZigZag32(registers.int1));
                    while (iDecodeVarint3214 < i2) {
                        int iDecodeVarint3215 = ArrayDecoders.decodeVarint32(bArr, iDecodeVarint3214, registers);
                        if (i3 != registers.int1) {
                            return iDecodeVarint3214;
                        }
                        iDecodeVarint3214 = ArrayDecoders.decodeVarint32(bArr, iDecodeVarint3215, registers);
                        intArrayList2.addInt(CodedInputStream.decodeZigZag32(registers.int1));
                    }
                    return iDecodeVarint3214;
                }
                return i;
            case 34:
            case 48:
                if (i5 == 2) {
                    return ArrayDecoders.decodePackedSInt64List(bArr, i, protobufList, registers);
                }
                if (i5 == 0) {
                    LongArrayList longArrayList4 = (LongArrayList) protobufList;
                    int iDecodeVarint643 = ArrayDecoders.decodeVarint64(bArr, i, registers);
                    longArrayList4.addLong(CodedInputStream.decodeZigZag64(registers.long1));
                    while (iDecodeVarint643 < i2) {
                        int iDecodeVarint3216 = ArrayDecoders.decodeVarint32(bArr, iDecodeVarint643, registers);
                        if (i3 != registers.int1) {
                            return iDecodeVarint643;
                        }
                        iDecodeVarint643 = ArrayDecoders.decodeVarint64(bArr, iDecodeVarint3216, registers);
                        longArrayList4.addLong(CodedInputStream.decodeZigZag64(registers.long1));
                    }
                    return iDecodeVarint643;
                }
                return i;
            case 49:
                if (i5 == 3) {
                    Schema messageFieldSchema = getMessageFieldSchema(i6);
                    int i21 = (i3 & (-8)) | 4;
                    GeneratedMessageLite generatedMessageLiteNewInstance = messageFieldSchema.newInstance();
                    MessageSchema messageSchema = (MessageSchema) messageFieldSchema;
                    int proto2Message = messageSchema.parseProto2Message(generatedMessageLiteNewInstance, bArr, i, i2, i21, registers);
                    MessageSchema messageSchema2 = messageSchema;
                    int i22 = i2;
                    int i23 = i21;
                    ArrayDecoders.Registers registers2 = registers;
                    registers2.object1 = generatedMessageLiteNewInstance;
                    messageFieldSchema.makeImmutable(generatedMessageLiteNewInstance);
                    registers2.object1 = generatedMessageLiteNewInstance;
                    protobufList.add(generatedMessageLiteNewInstance);
                    while (proto2Message < i22) {
                        int iDecodeVarint3217 = ArrayDecoders.decodeVarint32(bArr, proto2Message, registers2);
                        if (i3 != registers2.int1) {
                            return proto2Message;
                        }
                        GeneratedMessageLite generatedMessageLiteNewInstance2 = messageFieldSchema.newInstance();
                        int i24 = i23;
                        int i25 = i22;
                        ArrayDecoders.Registers registers3 = registers2;
                        MessageSchema messageSchema3 = messageSchema2;
                        proto2Message = messageSchema3.parseProto2Message(generatedMessageLiteNewInstance2, bArr, iDecodeVarint3217, i25, i24, registers3);
                        messageSchema2 = messageSchema3;
                        i22 = i25;
                        registers2 = registers3;
                        registers2.object1 = generatedMessageLiteNewInstance2;
                        messageFieldSchema.makeImmutable(generatedMessageLiteNewInstance2);
                        registers2.object1 = generatedMessageLiteNewInstance2;
                        protobufList.add(generatedMessageLiteNewInstance2);
                        i23 = i24;
                    }
                    return proto2Message;
                }
                return i;
            default:
                return i;
        }
    }

    public final void readGroupList(Object obj, long j, CodedInputStreamReader codedInputStreamReader, Schema schema, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException.InvalidWireTypeException {
        int tag;
        List listMutableListAt = this.listFieldSchema.mutableListAt(j, obj);
        int i = codedInputStreamReader.tag;
        if ((i & 7) != 3) {
            throw InvalidProtocolBufferException.invalidWireType();
        }
        do {
            GeneratedMessageLite generatedMessageLiteNewInstance = schema.newInstance();
            codedInputStreamReader.mergeGroupFieldInternal(generatedMessageLiteNewInstance, schema, extensionRegistryLite);
            schema.makeImmutable(generatedMessageLiteNewInstance);
            listMutableListAt.add(generatedMessageLiteNewInstance);
            CodedInputStream codedInputStream = codedInputStreamReader.input;
            if (codedInputStream.isAtEnd() || codedInputStreamReader.nextTag != 0) {
                return;
            } else {
                tag = codedInputStream.readTag();
            }
        } while (tag == i);
        codedInputStreamReader.nextTag = tag;
    }

    public final void readMessageList(Object obj, int i, CodedInputStreamReader codedInputStreamReader, Schema schema, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        int tag;
        List listMutableListAt = this.listFieldSchema.mutableListAt(i & 1048575, obj);
        int i2 = codedInputStreamReader.tag;
        if ((i2 & 7) != 2) {
            throw InvalidProtocolBufferException.invalidWireType();
        }
        do {
            GeneratedMessageLite generatedMessageLiteNewInstance = schema.newInstance();
            codedInputStreamReader.mergeMessageFieldInternal(generatedMessageLiteNewInstance, schema, extensionRegistryLite);
            schema.makeImmutable(generatedMessageLiteNewInstance);
            listMutableListAt.add(generatedMessageLiteNewInstance);
            CodedInputStream codedInputStream = codedInputStreamReader.input;
            if (codedInputStream.isAtEnd() || codedInputStreamReader.nextTag != 0) {
                return;
            } else {
                tag = codedInputStream.readTag();
            }
        } while (tag == i2);
        codedInputStreamReader.nextTag = tag;
    }

    public final void readString(Object obj, int i, CodedInputStreamReader codedInputStreamReader) throws InvalidProtocolBufferException.InvalidWireTypeException {
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

    public final void readStringList(Object obj, int i, CodedInputStreamReader codedInputStreamReader) throws InvalidProtocolBufferException.InvalidWireTypeException {
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

    /* JADX WARN: Removed duplicated region for block: B:7:0x0023  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void writeFieldsInAscendingOrderProto2(Object obj, CodedOutputStreamWriter codedOutputStreamWriter) {
        Iterator it;
        Map.Entry entry;
        boolean z;
        int i;
        boolean z2 = this.hasExtensions;
        ExtensionSchema extensionSchema = this.extensionSchema;
        if (z2) {
            FieldSet extensions = extensionSchema.getExtensions(obj);
            if (extensions.fields.isEmpty()) {
                it = null;
                entry = null;
            } else {
                it = extensions.iterator();
                entry = (Map.Entry) it.next();
            }
        }
        int[] iArr = this.buffer;
        int length = iArr.length;
        Unsafe unsafe = UNSAFE;
        int i2 = 0;
        int i3 = 1048575;
        int i4 = 0;
        while (i2 < length) {
            int iTypeAndOffsetAt = typeAndOffsetAt(i2);
            int i5 = iArr[i2];
            int iType = type(iTypeAndOffsetAt);
            Iterator it2 = it;
            if (iType <= 17) {
                int i6 = iArr[i2 + 2];
                z = true;
                int i7 = i6 & 1048575;
                if (i7 != i3) {
                    i4 = unsafe.getInt(obj, i7);
                    i3 = i7;
                }
                i = 1 << (i6 >>> 20);
            } else {
                z = true;
                i = 0;
            }
            while (entry != null && extensionSchema.extensionNumber(entry) <= i5) {
                extensionSchema.serializeExtension(codedOutputStreamWriter, entry);
                entry = it2.hasNext() ? (Map.Entry) it2.next() : null;
            }
            int i8 = iTypeAndOffsetAt & 1048575;
            Map.Entry entry2 = entry;
            int[] iArr2 = iArr;
            long j = i8;
            switch (iType) {
                case 0:
                    if ((i & i4) != 0) {
                        codedOutputStreamWriter.writeDouble(UnsafeUtil.MEMORY_ACCESSOR.getDouble(j, obj), i5);
                        continue;
                    }
                    i2 += 3;
                    iArr = iArr2;
                    it = it2;
                    entry = entry2;
                case 1:
                    if ((i & i4) != 0) {
                        codedOutputStreamWriter.writeFloat(UnsafeUtil.MEMORY_ACCESSOR.getFloat(j, obj), i5);
                    } else {
                        continue;
                    }
                    i2 += 3;
                    iArr = iArr2;
                    it = it2;
                    entry = entry2;
                case 2:
                    if ((i & i4) != 0) {
                        codedOutputStreamWriter.writeInt64(i5, unsafe.getLong(obj, j));
                    } else {
                        continue;
                    }
                    i2 += 3;
                    iArr = iArr2;
                    it = it2;
                    entry = entry2;
                case 3:
                    if ((i & i4) != 0) {
                        codedOutputStreamWriter.writeUInt64(i5, unsafe.getLong(obj, j));
                    } else {
                        continue;
                    }
                    i2 += 3;
                    iArr = iArr2;
                    it = it2;
                    entry = entry2;
                case 4:
                    if ((i & i4) != 0) {
                        codedOutputStreamWriter.writeInt32(i5, unsafe.getInt(obj, j));
                    } else {
                        continue;
                    }
                    i2 += 3;
                    iArr = iArr2;
                    it = it2;
                    entry = entry2;
                case 5:
                    if ((i & i4) != 0) {
                        codedOutputStreamWriter.writeFixed64(i5, unsafe.getLong(obj, j));
                    } else {
                        continue;
                    }
                    i2 += 3;
                    iArr = iArr2;
                    it = it2;
                    entry = entry2;
                case 6:
                    if ((i & i4) != 0) {
                        codedOutputStreamWriter.writeFixed32(i5, unsafe.getInt(obj, j));
                    } else {
                        continue;
                    }
                    i2 += 3;
                    iArr = iArr2;
                    it = it2;
                    entry = entry2;
                case 7:
                    if ((i & i4) != 0) {
                        codedOutputStreamWriter.writeBool(i5, UnsafeUtil.MEMORY_ACCESSOR.getBoolean(j, obj));
                    } else {
                        continue;
                    }
                    i2 += 3;
                    iArr = iArr2;
                    it = it2;
                    entry = entry2;
                case 8:
                    if ((i & i4) != 0) {
                        writeString(i5, unsafe.getObject(obj, j), codedOutputStreamWriter);
                    } else {
                        continue;
                    }
                    i2 += 3;
                    iArr = iArr2;
                    it = it2;
                    entry = entry2;
                case 9:
                    if ((i & i4) != 0) {
                        codedOutputStreamWriter.writeMessage(i5, unsafe.getObject(obj, j), getMessageFieldSchema(i2));
                    } else {
                        continue;
                    }
                    i2 += 3;
                    iArr = iArr2;
                    it = it2;
                    entry = entry2;
                case 10:
                    if ((i & i4) != 0) {
                        codedOutputStreamWriter.writeBytes(i5, (ByteString) unsafe.getObject(obj, j));
                    } else {
                        continue;
                    }
                    i2 += 3;
                    iArr = iArr2;
                    it = it2;
                    entry = entry2;
                case 11:
                    if ((i & i4) != 0) {
                        codedOutputStreamWriter.writeUInt32(i5, unsafe.getInt(obj, j));
                    } else {
                        continue;
                    }
                    i2 += 3;
                    iArr = iArr2;
                    it = it2;
                    entry = entry2;
                case 12:
                    if ((i & i4) != 0) {
                        codedOutputStreamWriter.writeEnum(i5, unsafe.getInt(obj, j));
                    } else {
                        continue;
                    }
                    i2 += 3;
                    iArr = iArr2;
                    it = it2;
                    entry = entry2;
                case 13:
                    if ((i & i4) != 0) {
                        codedOutputStreamWriter.writeSFixed32(i5, unsafe.getInt(obj, j));
                    } else {
                        continue;
                    }
                    i2 += 3;
                    iArr = iArr2;
                    it = it2;
                    entry = entry2;
                case 14:
                    if ((i & i4) != 0) {
                        codedOutputStreamWriter.writeSFixed64(i5, unsafe.getLong(obj, j));
                    } else {
                        continue;
                    }
                    i2 += 3;
                    iArr = iArr2;
                    it = it2;
                    entry = entry2;
                case 15:
                    if ((i & i4) != 0) {
                        codedOutputStreamWriter.writeSInt32(i5, unsafe.getInt(obj, j));
                    } else {
                        continue;
                    }
                    i2 += 3;
                    iArr = iArr2;
                    it = it2;
                    entry = entry2;
                case 16:
                    if ((i & i4) != 0) {
                        codedOutputStreamWriter.writeSInt64(i5, unsafe.getLong(obj, j));
                    } else {
                        continue;
                    }
                    i2 += 3;
                    iArr = iArr2;
                    it = it2;
                    entry = entry2;
                case 17:
                    if ((i & i4) != 0) {
                        codedOutputStreamWriter.writeGroup(i5, unsafe.getObject(obj, j), getMessageFieldSchema(i2));
                    } else {
                        continue;
                    }
                    i2 += 3;
                    iArr = iArr2;
                    it = it2;
                    entry = entry2;
                case 18:
                    SchemaUtil.writeDoubleList(iArr2[i2], (List) unsafe.getObject(obj, j), codedOutputStreamWriter, false);
                    continue;
                    i2 += 3;
                    iArr = iArr2;
                    it = it2;
                    entry = entry2;
                case 19:
                    SchemaUtil.writeFloatList(iArr2[i2], (List) unsafe.getObject(obj, j), codedOutputStreamWriter, false);
                    continue;
                    i2 += 3;
                    iArr = iArr2;
                    it = it2;
                    entry = entry2;
                case 20:
                    SchemaUtil.writeInt64List(iArr2[i2], (List) unsafe.getObject(obj, j), codedOutputStreamWriter, false);
                    continue;
                    i2 += 3;
                    iArr = iArr2;
                    it = it2;
                    entry = entry2;
                case 21:
                    SchemaUtil.writeUInt64List(iArr2[i2], (List) unsafe.getObject(obj, j), codedOutputStreamWriter, false);
                    continue;
                    i2 += 3;
                    iArr = iArr2;
                    it = it2;
                    entry = entry2;
                case 22:
                    SchemaUtil.writeInt32List(iArr2[i2], (List) unsafe.getObject(obj, j), codedOutputStreamWriter, false);
                    continue;
                    i2 += 3;
                    iArr = iArr2;
                    it = it2;
                    entry = entry2;
                case 23:
                    SchemaUtil.writeFixed64List(iArr2[i2], (List) unsafe.getObject(obj, j), codedOutputStreamWriter, false);
                    continue;
                    i2 += 3;
                    iArr = iArr2;
                    it = it2;
                    entry = entry2;
                case 24:
                    SchemaUtil.writeFixed32List(iArr2[i2], (List) unsafe.getObject(obj, j), codedOutputStreamWriter, false);
                    continue;
                    i2 += 3;
                    iArr = iArr2;
                    it = it2;
                    entry = entry2;
                case 25:
                    SchemaUtil.writeBoolList(iArr2[i2], (List) unsafe.getObject(obj, j), codedOutputStreamWriter, false);
                    continue;
                    i2 += 3;
                    iArr = iArr2;
                    it = it2;
                    entry = entry2;
                case 26:
                    SchemaUtil.writeStringList(iArr2[i2], (List) unsafe.getObject(obj, j), codedOutputStreamWriter);
                    break;
                case 27:
                    SchemaUtil.writeMessageList(iArr2[i2], (List) unsafe.getObject(obj, j), codedOutputStreamWriter, getMessageFieldSchema(i2));
                    break;
                case 28:
                    SchemaUtil.writeBytesList(iArr2[i2], (List) unsafe.getObject(obj, j), codedOutputStreamWriter);
                    break;
                case 29:
                    SchemaUtil.writeUInt32List(iArr2[i2], (List) unsafe.getObject(obj, j), codedOutputStreamWriter, false);
                    continue;
                    i2 += 3;
                    iArr = iArr2;
                    it = it2;
                    entry = entry2;
                case 30:
                    SchemaUtil.writeEnumList(iArr2[i2], (List) unsafe.getObject(obj, j), codedOutputStreamWriter, false);
                    continue;
                    i2 += 3;
                    iArr = iArr2;
                    it = it2;
                    entry = entry2;
                case 31:
                    SchemaUtil.writeSFixed32List(iArr2[i2], (List) unsafe.getObject(obj, j), codedOutputStreamWriter, false);
                    continue;
                    i2 += 3;
                    iArr = iArr2;
                    it = it2;
                    entry = entry2;
                case 32:
                    SchemaUtil.writeSFixed64List(iArr2[i2], (List) unsafe.getObject(obj, j), codedOutputStreamWriter, false);
                    continue;
                    i2 += 3;
                    iArr = iArr2;
                    it = it2;
                    entry = entry2;
                case 33:
                    SchemaUtil.writeSInt32List(iArr2[i2], (List) unsafe.getObject(obj, j), codedOutputStreamWriter, false);
                    continue;
                    i2 += 3;
                    iArr = iArr2;
                    it = it2;
                    entry = entry2;
                case 34:
                    SchemaUtil.writeSInt64List(iArr2[i2], (List) unsafe.getObject(obj, j), codedOutputStreamWriter, false);
                    continue;
                    i2 += 3;
                    iArr = iArr2;
                    it = it2;
                    entry = entry2;
                case 35:
                    SchemaUtil.writeDoubleList(iArr2[i2], (List) unsafe.getObject(obj, j), codedOutputStreamWriter, z);
                    break;
                case 36:
                    SchemaUtil.writeFloatList(iArr2[i2], (List) unsafe.getObject(obj, j), codedOutputStreamWriter, z);
                    break;
                case 37:
                    SchemaUtil.writeInt64List(iArr2[i2], (List) unsafe.getObject(obj, j), codedOutputStreamWriter, z);
                    break;
                case 38:
                    SchemaUtil.writeUInt64List(iArr2[i2], (List) unsafe.getObject(obj, j), codedOutputStreamWriter, z);
                    break;
                case 39:
                    SchemaUtil.writeInt32List(iArr2[i2], (List) unsafe.getObject(obj, j), codedOutputStreamWriter, z);
                    break;
                case 40:
                    SchemaUtil.writeFixed64List(iArr2[i2], (List) unsafe.getObject(obj, j), codedOutputStreamWriter, z);
                    break;
                case 41:
                    SchemaUtil.writeFixed32List(iArr2[i2], (List) unsafe.getObject(obj, j), codedOutputStreamWriter, z);
                    break;
                case 42:
                    SchemaUtil.writeBoolList(iArr2[i2], (List) unsafe.getObject(obj, j), codedOutputStreamWriter, z);
                    break;
                case 43:
                    SchemaUtil.writeUInt32List(iArr2[i2], (List) unsafe.getObject(obj, j), codedOutputStreamWriter, z);
                    break;
                case 44:
                    SchemaUtil.writeEnumList(iArr2[i2], (List) unsafe.getObject(obj, j), codedOutputStreamWriter, z);
                    break;
                case 45:
                    SchemaUtil.writeSFixed32List(iArr2[i2], (List) unsafe.getObject(obj, j), codedOutputStreamWriter, z);
                    break;
                case 46:
                    SchemaUtil.writeSFixed64List(iArr2[i2], (List) unsafe.getObject(obj, j), codedOutputStreamWriter, z);
                    break;
                case 47:
                    SchemaUtil.writeSInt32List(iArr2[i2], (List) unsafe.getObject(obj, j), codedOutputStreamWriter, z);
                    break;
                case 48:
                    SchemaUtil.writeSInt64List(iArr2[i2], (List) unsafe.getObject(obj, j), codedOutputStreamWriter, z);
                    break;
                case 49:
                    SchemaUtil.writeGroupList(iArr2[i2], (List) unsafe.getObject(obj, j), codedOutputStreamWriter, getMessageFieldSchema(i2));
                    break;
                case 50:
                    writeMapHelper(codedOutputStreamWriter, i5, unsafe.getObject(obj, j), i2);
                    break;
                case 51:
                    if (isOneofPresent(i5, i2, obj)) {
                        codedOutputStreamWriter.writeDouble(((Double) UnsafeUtil.getObject(j, obj)).doubleValue(), i5);
                        break;
                    }
                    break;
                case 52:
                    if (isOneofPresent(i5, i2, obj)) {
                        codedOutputStreamWriter.writeFloat(((Float) UnsafeUtil.getObject(j, obj)).floatValue(), i5);
                        break;
                    }
                    break;
                case 53:
                    if (isOneofPresent(i5, i2, obj)) {
                        codedOutputStreamWriter.writeInt64(i5, oneofLongAt(j, obj));
                        break;
                    }
                    break;
                case 54:
                    if (isOneofPresent(i5, i2, obj)) {
                        codedOutputStreamWriter.writeUInt64(i5, oneofLongAt(j, obj));
                        break;
                    }
                    break;
                case 55:
                    if (isOneofPresent(i5, i2, obj)) {
                        codedOutputStreamWriter.writeInt32(i5, oneofIntAt(j, obj));
                        break;
                    }
                    break;
                case 56:
                    if (isOneofPresent(i5, i2, obj)) {
                        codedOutputStreamWriter.writeFixed64(i5, oneofLongAt(j, obj));
                        break;
                    }
                    break;
                case 57:
                    if (isOneofPresent(i5, i2, obj)) {
                        codedOutputStreamWriter.writeFixed32(i5, oneofIntAt(j, obj));
                        break;
                    }
                    break;
                case 58:
                    if (isOneofPresent(i5, i2, obj)) {
                        codedOutputStreamWriter.writeBool(i5, ((Boolean) UnsafeUtil.getObject(j, obj)).booleanValue());
                        break;
                    }
                    break;
                case 59:
                    if (isOneofPresent(i5, i2, obj)) {
                        writeString(i5, unsafe.getObject(obj, j), codedOutputStreamWriter);
                        break;
                    }
                    break;
                case 60:
                    if (isOneofPresent(i5, i2, obj)) {
                        codedOutputStreamWriter.writeMessage(i5, unsafe.getObject(obj, j), getMessageFieldSchema(i2));
                        break;
                    }
                    break;
                case 61:
                    if (isOneofPresent(i5, i2, obj)) {
                        codedOutputStreamWriter.writeBytes(i5, (ByteString) unsafe.getObject(obj, j));
                        break;
                    }
                    break;
                case 62:
                    if (isOneofPresent(i5, i2, obj)) {
                        codedOutputStreamWriter.writeUInt32(i5, oneofIntAt(j, obj));
                        break;
                    }
                    break;
                case 63:
                    if (isOneofPresent(i5, i2, obj)) {
                        codedOutputStreamWriter.writeEnum(i5, oneofIntAt(j, obj));
                        break;
                    }
                    break;
                case 64:
                    if (isOneofPresent(i5, i2, obj)) {
                        codedOutputStreamWriter.writeSFixed32(i5, oneofIntAt(j, obj));
                        break;
                    }
                    break;
                case 65:
                    if (isOneofPresent(i5, i2, obj)) {
                        codedOutputStreamWriter.writeSFixed64(i5, oneofLongAt(j, obj));
                        break;
                    }
                    break;
                case 66:
                    if (isOneofPresent(i5, i2, obj)) {
                        codedOutputStreamWriter.writeSInt32(i5, oneofIntAt(j, obj));
                        break;
                    }
                    break;
                case 67:
                    if (isOneofPresent(i5, i2, obj)) {
                        codedOutputStreamWriter.writeSInt64(i5, oneofLongAt(j, obj));
                        break;
                    }
                    break;
                case 68:
                    if (isOneofPresent(i5, i2, obj)) {
                        codedOutputStreamWriter.writeGroup(i5, unsafe.getObject(obj, j), getMessageFieldSchema(i2));
                        break;
                    }
                    break;
            }
            i2 += 3;
            iArr = iArr2;
            it = it2;
            entry = entry2;
        }
        Iterator it3 = it;
        while (entry != null) {
            extensionSchema.serializeExtension(codedOutputStreamWriter, entry);
            entry = it3.hasNext() ? (Map.Entry) it3.next() : null;
        }
        UnknownFieldSchema unknownFieldSchema = this.unknownFieldSchema;
        unknownFieldSchema.writeTo(unknownFieldSchema.getFromMessage(obj), codedOutputStreamWriter);
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

    /* JADX WARN: Removed duplicated region for block: B:187:0x0549  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0067  */
    @Override // com.google.protobuf.Schema
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void writeTo(Object obj, CodedOutputStreamWriter codedOutputStreamWriter) {
        Iterator it;
        Map.Entry entry;
        int i;
        Map.Entry entry2;
        Iterator it2;
        codedOutputStreamWriter.getClass();
        Writer$FieldOrder writer$FieldOrder = Writer$FieldOrder.ASCENDING;
        Writer$FieldOrder writer$FieldOrder2 = Writer$FieldOrder.DESCENDING;
        int[] iArr = this.buffer;
        ExtensionSchema extensionSchema = this.extensionSchema;
        boolean z = this.hasExtensions;
        UnknownFieldSchema unknownFieldSchema = this.unknownFieldSchema;
        int i2 = 1048575;
        boolean z2 = true;
        if (writer$FieldOrder == writer$FieldOrder2) {
            unknownFieldSchema.writeTo(unknownFieldSchema.getFromMessage(obj), codedOutputStreamWriter);
            if (z) {
                FieldSet extensions = extensionSchema.getExtensions(obj);
                if (extensions.fields.isEmpty()) {
                    entry2 = null;
                    it2 = null;
                } else {
                    boolean z3 = extensions.hasLazyField;
                    SmallSortedMap smallSortedMap = extensions.fields;
                    if (z3) {
                        if (smallSortedMap.lazyDescendingEntrySet == null) {
                            smallSortedMap.lazyDescendingEntrySet = new SmallSortedMap.DescendingEntrySet();
                        }
                        it2 = new LazyField.LazyIterator(smallSortedMap.lazyDescendingEntrySet.iterator());
                    } else {
                        if (smallSortedMap.lazyDescendingEntrySet == null) {
                            smallSortedMap.lazyDescendingEntrySet = new SmallSortedMap.DescendingEntrySet();
                        }
                        it2 = smallSortedMap.lazyDescendingEntrySet.iterator();
                    }
                    entry2 = (Map.Entry) it2.next();
                }
            }
            for (int length = iArr.length - 3; length >= 0; length -= 3) {
                int iTypeAndOffsetAt = typeAndOffsetAt(length);
                int i3 = iArr[length];
                while (entry2 != null && extensionSchema.extensionNumber(entry2) > i3) {
                    extensionSchema.serializeExtension(codedOutputStreamWriter, entry2);
                    entry2 = it2.hasNext() ? (Map.Entry) it2.next() : null;
                }
                switch (type(iTypeAndOffsetAt)) {
                    case 0:
                        if (isFieldPresent(length, obj)) {
                            codedOutputStreamWriter.writeDouble(UnsafeUtil.MEMORY_ACCESSOR.getDouble(iTypeAndOffsetAt & 1048575, obj), i3);
                            break;
                        } else {
                            break;
                        }
                    case 1:
                        if (isFieldPresent(length, obj)) {
                            codedOutputStreamWriter.writeFloat(UnsafeUtil.MEMORY_ACCESSOR.getFloat(iTypeAndOffsetAt & 1048575, obj), i3);
                            break;
                        } else {
                            break;
                        }
                    case 2:
                        if (isFieldPresent(length, obj)) {
                            codedOutputStreamWriter.writeInt64(i3, UnsafeUtil.getLong(iTypeAndOffsetAt & 1048575, obj));
                            break;
                        } else {
                            break;
                        }
                    case 3:
                        if (isFieldPresent(length, obj)) {
                            codedOutputStreamWriter.writeUInt64(i3, UnsafeUtil.getLong(iTypeAndOffsetAt & 1048575, obj));
                            break;
                        } else {
                            break;
                        }
                    case 4:
                        if (isFieldPresent(length, obj)) {
                            codedOutputStreamWriter.writeInt32(i3, UnsafeUtil.getInt(iTypeAndOffsetAt & 1048575, obj));
                            break;
                        } else {
                            break;
                        }
                    case 5:
                        if (isFieldPresent(length, obj)) {
                            codedOutputStreamWriter.writeFixed64(i3, UnsafeUtil.getLong(iTypeAndOffsetAt & 1048575, obj));
                            break;
                        } else {
                            break;
                        }
                    case 6:
                        if (isFieldPresent(length, obj)) {
                            codedOutputStreamWriter.writeFixed32(i3, UnsafeUtil.getInt(iTypeAndOffsetAt & 1048575, obj));
                            break;
                        } else {
                            break;
                        }
                    case 7:
                        if (isFieldPresent(length, obj)) {
                            codedOutputStreamWriter.writeBool(i3, UnsafeUtil.MEMORY_ACCESSOR.getBoolean(iTypeAndOffsetAt & 1048575, obj));
                            break;
                        } else {
                            break;
                        }
                    case 8:
                        if (isFieldPresent(length, obj)) {
                            writeString(i3, UnsafeUtil.getObject(iTypeAndOffsetAt & 1048575, obj), codedOutputStreamWriter);
                            break;
                        } else {
                            break;
                        }
                    case 9:
                        if (isFieldPresent(length, obj)) {
                            codedOutputStreamWriter.writeMessage(i3, UnsafeUtil.getObject(iTypeAndOffsetAt & 1048575, obj), getMessageFieldSchema(length));
                            break;
                        } else {
                            break;
                        }
                    case 10:
                        if (isFieldPresent(length, obj)) {
                            codedOutputStreamWriter.writeBytes(i3, (ByteString) UnsafeUtil.getObject(iTypeAndOffsetAt & 1048575, obj));
                            break;
                        } else {
                            break;
                        }
                    case 11:
                        if (isFieldPresent(length, obj)) {
                            codedOutputStreamWriter.writeUInt32(i3, UnsafeUtil.getInt(iTypeAndOffsetAt & 1048575, obj));
                            break;
                        } else {
                            break;
                        }
                    case 12:
                        if (isFieldPresent(length, obj)) {
                            codedOutputStreamWriter.writeEnum(i3, UnsafeUtil.getInt(iTypeAndOffsetAt & 1048575, obj));
                            break;
                        } else {
                            break;
                        }
                    case 13:
                        if (isFieldPresent(length, obj)) {
                            codedOutputStreamWriter.writeSFixed32(i3, UnsafeUtil.getInt(iTypeAndOffsetAt & 1048575, obj));
                            break;
                        } else {
                            break;
                        }
                    case 14:
                        if (isFieldPresent(length, obj)) {
                            codedOutputStreamWriter.writeSFixed64(i3, UnsafeUtil.getLong(iTypeAndOffsetAt & 1048575, obj));
                            break;
                        } else {
                            break;
                        }
                    case 15:
                        if (isFieldPresent(length, obj)) {
                            codedOutputStreamWriter.writeSInt32(i3, UnsafeUtil.getInt(iTypeAndOffsetAt & 1048575, obj));
                            break;
                        } else {
                            break;
                        }
                    case 16:
                        if (isFieldPresent(length, obj)) {
                            codedOutputStreamWriter.writeSInt64(i3, UnsafeUtil.getLong(iTypeAndOffsetAt & 1048575, obj));
                            break;
                        } else {
                            break;
                        }
                    case 17:
                        if (isFieldPresent(length, obj)) {
                            codedOutputStreamWriter.writeGroup(i3, UnsafeUtil.getObject(iTypeAndOffsetAt & 1048575, obj), getMessageFieldSchema(length));
                            break;
                        } else {
                            break;
                        }
                    case 18:
                        SchemaUtil.writeDoubleList(iArr[length], (List) UnsafeUtil.getObject(iTypeAndOffsetAt & 1048575, obj), codedOutputStreamWriter, false);
                        break;
                    case 19:
                        SchemaUtil.writeFloatList(iArr[length], (List) UnsafeUtil.getObject(iTypeAndOffsetAt & 1048575, obj), codedOutputStreamWriter, false);
                        break;
                    case 20:
                        SchemaUtil.writeInt64List(iArr[length], (List) UnsafeUtil.getObject(iTypeAndOffsetAt & 1048575, obj), codedOutputStreamWriter, false);
                        break;
                    case 21:
                        SchemaUtil.writeUInt64List(iArr[length], (List) UnsafeUtil.getObject(iTypeAndOffsetAt & 1048575, obj), codedOutputStreamWriter, false);
                        break;
                    case 22:
                        SchemaUtil.writeInt32List(iArr[length], (List) UnsafeUtil.getObject(iTypeAndOffsetAt & 1048575, obj), codedOutputStreamWriter, false);
                        break;
                    case 23:
                        SchemaUtil.writeFixed64List(iArr[length], (List) UnsafeUtil.getObject(iTypeAndOffsetAt & 1048575, obj), codedOutputStreamWriter, false);
                        break;
                    case 24:
                        SchemaUtil.writeFixed32List(iArr[length], (List) UnsafeUtil.getObject(iTypeAndOffsetAt & 1048575, obj), codedOutputStreamWriter, false);
                        break;
                    case 25:
                        SchemaUtil.writeBoolList(iArr[length], (List) UnsafeUtil.getObject(iTypeAndOffsetAt & 1048575, obj), codedOutputStreamWriter, false);
                        break;
                    case 26:
                        SchemaUtil.writeStringList(iArr[length], (List) UnsafeUtil.getObject(iTypeAndOffsetAt & 1048575, obj), codedOutputStreamWriter);
                        break;
                    case 27:
                        SchemaUtil.writeMessageList(iArr[length], (List) UnsafeUtil.getObject(iTypeAndOffsetAt & 1048575, obj), codedOutputStreamWriter, getMessageFieldSchema(length));
                        break;
                    case 28:
                        SchemaUtil.writeBytesList(iArr[length], (List) UnsafeUtil.getObject(iTypeAndOffsetAt & 1048575, obj), codedOutputStreamWriter);
                        break;
                    case 29:
                        SchemaUtil.writeUInt32List(iArr[length], (List) UnsafeUtil.getObject(iTypeAndOffsetAt & 1048575, obj), codedOutputStreamWriter, false);
                        break;
                    case 30:
                        SchemaUtil.writeEnumList(iArr[length], (List) UnsafeUtil.getObject(iTypeAndOffsetAt & 1048575, obj), codedOutputStreamWriter, false);
                        break;
                    case 31:
                        SchemaUtil.writeSFixed32List(iArr[length], (List) UnsafeUtil.getObject(iTypeAndOffsetAt & 1048575, obj), codedOutputStreamWriter, false);
                        break;
                    case 32:
                        SchemaUtil.writeSFixed64List(iArr[length], (List) UnsafeUtil.getObject(iTypeAndOffsetAt & 1048575, obj), codedOutputStreamWriter, false);
                        break;
                    case 33:
                        SchemaUtil.writeSInt32List(iArr[length], (List) UnsafeUtil.getObject(iTypeAndOffsetAt & 1048575, obj), codedOutputStreamWriter, false);
                        break;
                    case 34:
                        SchemaUtil.writeSInt64List(iArr[length], (List) UnsafeUtil.getObject(iTypeAndOffsetAt & 1048575, obj), codedOutputStreamWriter, false);
                        break;
                    case 35:
                        SchemaUtil.writeDoubleList(iArr[length], (List) UnsafeUtil.getObject(iTypeAndOffsetAt & 1048575, obj), codedOutputStreamWriter, true);
                        break;
                    case 36:
                        SchemaUtil.writeFloatList(iArr[length], (List) UnsafeUtil.getObject(iTypeAndOffsetAt & 1048575, obj), codedOutputStreamWriter, true);
                        break;
                    case 37:
                        SchemaUtil.writeInt64List(iArr[length], (List) UnsafeUtil.getObject(iTypeAndOffsetAt & 1048575, obj), codedOutputStreamWriter, true);
                        break;
                    case 38:
                        SchemaUtil.writeUInt64List(iArr[length], (List) UnsafeUtil.getObject(iTypeAndOffsetAt & 1048575, obj), codedOutputStreamWriter, true);
                        break;
                    case 39:
                        SchemaUtil.writeInt32List(iArr[length], (List) UnsafeUtil.getObject(iTypeAndOffsetAt & 1048575, obj), codedOutputStreamWriter, true);
                        break;
                    case 40:
                        SchemaUtil.writeFixed64List(iArr[length], (List) UnsafeUtil.getObject(iTypeAndOffsetAt & 1048575, obj), codedOutputStreamWriter, true);
                        break;
                    case 41:
                        SchemaUtil.writeFixed32List(iArr[length], (List) UnsafeUtil.getObject(iTypeAndOffsetAt & 1048575, obj), codedOutputStreamWriter, true);
                        break;
                    case 42:
                        SchemaUtil.writeBoolList(iArr[length], (List) UnsafeUtil.getObject(iTypeAndOffsetAt & 1048575, obj), codedOutputStreamWriter, true);
                        break;
                    case 43:
                        SchemaUtil.writeUInt32List(iArr[length], (List) UnsafeUtil.getObject(iTypeAndOffsetAt & 1048575, obj), codedOutputStreamWriter, true);
                        break;
                    case 44:
                        SchemaUtil.writeEnumList(iArr[length], (List) UnsafeUtil.getObject(iTypeAndOffsetAt & 1048575, obj), codedOutputStreamWriter, true);
                        break;
                    case 45:
                        SchemaUtil.writeSFixed32List(iArr[length], (List) UnsafeUtil.getObject(iTypeAndOffsetAt & 1048575, obj), codedOutputStreamWriter, true);
                        break;
                    case 46:
                        SchemaUtil.writeSFixed64List(iArr[length], (List) UnsafeUtil.getObject(iTypeAndOffsetAt & 1048575, obj), codedOutputStreamWriter, true);
                        break;
                    case 47:
                        SchemaUtil.writeSInt32List(iArr[length], (List) UnsafeUtil.getObject(iTypeAndOffsetAt & 1048575, obj), codedOutputStreamWriter, true);
                        break;
                    case 48:
                        SchemaUtil.writeSInt64List(iArr[length], (List) UnsafeUtil.getObject(iTypeAndOffsetAt & 1048575, obj), codedOutputStreamWriter, true);
                        break;
                    case 49:
                        SchemaUtil.writeGroupList(iArr[length], (List) UnsafeUtil.getObject(iTypeAndOffsetAt & 1048575, obj), codedOutputStreamWriter, getMessageFieldSchema(length));
                        break;
                    case 50:
                        writeMapHelper(codedOutputStreamWriter, i3, UnsafeUtil.getObject(iTypeAndOffsetAt & 1048575, obj), length);
                        break;
                    case 51:
                        if (isOneofPresent(i3, length, obj)) {
                            codedOutputStreamWriter.writeDouble(((Double) UnsafeUtil.getObject(iTypeAndOffsetAt & 1048575, obj)).doubleValue(), i3);
                            break;
                        } else {
                            break;
                        }
                    case 52:
                        if (isOneofPresent(i3, length, obj)) {
                            codedOutputStreamWriter.writeFloat(((Float) UnsafeUtil.getObject(iTypeAndOffsetAt & 1048575, obj)).floatValue(), i3);
                            break;
                        } else {
                            break;
                        }
                    case 53:
                        if (isOneofPresent(i3, length, obj)) {
                            codedOutputStreamWriter.writeInt64(i3, oneofLongAt(iTypeAndOffsetAt & 1048575, obj));
                            break;
                        } else {
                            break;
                        }
                    case 54:
                        if (isOneofPresent(i3, length, obj)) {
                            codedOutputStreamWriter.writeUInt64(i3, oneofLongAt(iTypeAndOffsetAt & 1048575, obj));
                            break;
                        } else {
                            break;
                        }
                    case 55:
                        if (isOneofPresent(i3, length, obj)) {
                            codedOutputStreamWriter.writeInt32(i3, oneofIntAt(iTypeAndOffsetAt & 1048575, obj));
                            break;
                        } else {
                            break;
                        }
                    case 56:
                        if (isOneofPresent(i3, length, obj)) {
                            codedOutputStreamWriter.writeFixed64(i3, oneofLongAt(iTypeAndOffsetAt & 1048575, obj));
                            break;
                        } else {
                            break;
                        }
                    case 57:
                        if (isOneofPresent(i3, length, obj)) {
                            codedOutputStreamWriter.writeFixed32(i3, oneofIntAt(iTypeAndOffsetAt & 1048575, obj));
                            break;
                        } else {
                            break;
                        }
                    case 58:
                        if (isOneofPresent(i3, length, obj)) {
                            codedOutputStreamWriter.writeBool(i3, ((Boolean) UnsafeUtil.getObject(iTypeAndOffsetAt & 1048575, obj)).booleanValue());
                            break;
                        } else {
                            break;
                        }
                    case 59:
                        if (isOneofPresent(i3, length, obj)) {
                            writeString(i3, UnsafeUtil.getObject(iTypeAndOffsetAt & 1048575, obj), codedOutputStreamWriter);
                            break;
                        } else {
                            break;
                        }
                    case 60:
                        if (isOneofPresent(i3, length, obj)) {
                            codedOutputStreamWriter.writeMessage(i3, UnsafeUtil.getObject(iTypeAndOffsetAt & 1048575, obj), getMessageFieldSchema(length));
                            break;
                        } else {
                            break;
                        }
                    case 61:
                        if (isOneofPresent(i3, length, obj)) {
                            codedOutputStreamWriter.writeBytes(i3, (ByteString) UnsafeUtil.getObject(iTypeAndOffsetAt & 1048575, obj));
                            break;
                        } else {
                            break;
                        }
                    case 62:
                        if (isOneofPresent(i3, length, obj)) {
                            codedOutputStreamWriter.writeUInt32(i3, oneofIntAt(iTypeAndOffsetAt & 1048575, obj));
                            break;
                        } else {
                            break;
                        }
                    case 63:
                        if (isOneofPresent(i3, length, obj)) {
                            codedOutputStreamWriter.writeEnum(i3, oneofIntAt(iTypeAndOffsetAt & 1048575, obj));
                            break;
                        } else {
                            break;
                        }
                    case 64:
                        if (isOneofPresent(i3, length, obj)) {
                            codedOutputStreamWriter.writeSFixed32(i3, oneofIntAt(iTypeAndOffsetAt & 1048575, obj));
                            break;
                        } else {
                            break;
                        }
                    case 65:
                        if (isOneofPresent(i3, length, obj)) {
                            codedOutputStreamWriter.writeSFixed64(i3, oneofLongAt(iTypeAndOffsetAt & 1048575, obj));
                            break;
                        } else {
                            break;
                        }
                    case 66:
                        if (isOneofPresent(i3, length, obj)) {
                            codedOutputStreamWriter.writeSInt32(i3, oneofIntAt(iTypeAndOffsetAt & 1048575, obj));
                            break;
                        } else {
                            break;
                        }
                    case 67:
                        if (isOneofPresent(i3, length, obj)) {
                            codedOutputStreamWriter.writeSInt64(i3, oneofLongAt(iTypeAndOffsetAt & 1048575, obj));
                            break;
                        } else {
                            break;
                        }
                    case 68:
                        if (isOneofPresent(i3, length, obj)) {
                            codedOutputStreamWriter.writeGroup(i3, UnsafeUtil.getObject(iTypeAndOffsetAt & 1048575, obj), getMessageFieldSchema(length));
                            break;
                        } else {
                            break;
                        }
                }
            }
            while (entry2 != null) {
                extensionSchema.serializeExtension(codedOutputStreamWriter, entry2);
                entry2 = it2.hasNext() ? (Map.Entry) it2.next() : null;
            }
            return;
        }
        if (!this.proto3) {
            writeFieldsInAscendingOrderProto2(obj, codedOutputStreamWriter);
            return;
        }
        if (z) {
            FieldSet extensions2 = extensionSchema.getExtensions(obj);
            if (extensions2.fields.isEmpty()) {
                it = null;
                entry = null;
            } else {
                it = extensions2.iterator();
                entry = (Map.Entry) it.next();
            }
        }
        int length2 = iArr.length;
        int i4 = 0;
        while (i4 < length2) {
            int iTypeAndOffsetAt2 = typeAndOffsetAt(i4);
            int i5 = iArr[i4];
            while (entry != null && extensionSchema.extensionNumber(entry) <= i5) {
                extensionSchema.serializeExtension(codedOutputStreamWriter, entry);
                entry = it.hasNext() ? (Map.Entry) it.next() : null;
            }
            switch (type(iTypeAndOffsetAt2)) {
                case 0:
                    i = i2;
                    if (isFieldPresent(i4, obj)) {
                        codedOutputStreamWriter.writeDouble(UnsafeUtil.MEMORY_ACCESSOR.getDouble(iTypeAndOffsetAt2 & i, obj), i5);
                        break;
                    } else {
                        break;
                    }
                case 1:
                    i = i2;
                    if (isFieldPresent(i4, obj)) {
                        codedOutputStreamWriter.writeFloat(UnsafeUtil.MEMORY_ACCESSOR.getFloat(iTypeAndOffsetAt2 & i, obj), i5);
                        break;
                    } else {
                        break;
                    }
                case 2:
                    i = i2;
                    if (isFieldPresent(i4, obj)) {
                        codedOutputStreamWriter.writeInt64(i5, UnsafeUtil.getLong(iTypeAndOffsetAt2 & i, obj));
                        break;
                    } else {
                        break;
                    }
                case 3:
                    i = i2;
                    if (isFieldPresent(i4, obj)) {
                        codedOutputStreamWriter.writeUInt64(i5, UnsafeUtil.getLong(iTypeAndOffsetAt2 & i, obj));
                        break;
                    } else {
                        break;
                    }
                case 4:
                    i = i2;
                    if (isFieldPresent(i4, obj)) {
                        codedOutputStreamWriter.writeInt32(i5, UnsafeUtil.getInt(iTypeAndOffsetAt2 & i, obj));
                        break;
                    } else {
                        break;
                    }
                case 5:
                    i = i2;
                    if (isFieldPresent(i4, obj)) {
                        codedOutputStreamWriter.writeFixed64(i5, UnsafeUtil.getLong(iTypeAndOffsetAt2 & i, obj));
                        break;
                    } else {
                        break;
                    }
                case 6:
                    i = i2;
                    if (isFieldPresent(i4, obj)) {
                        codedOutputStreamWriter.writeFixed32(i5, UnsafeUtil.getInt(iTypeAndOffsetAt2 & i, obj));
                        break;
                    } else {
                        break;
                    }
                case 7:
                    i = i2;
                    if (isFieldPresent(i4, obj)) {
                        codedOutputStreamWriter.writeBool(i5, UnsafeUtil.MEMORY_ACCESSOR.getBoolean(iTypeAndOffsetAt2 & i, obj));
                        break;
                    } else {
                        break;
                    }
                case 8:
                    i = i2;
                    if (isFieldPresent(i4, obj)) {
                        writeString(i5, UnsafeUtil.getObject(iTypeAndOffsetAt2 & i, obj), codedOutputStreamWriter);
                        break;
                    } else {
                        break;
                    }
                case 9:
                    i = i2;
                    if (isFieldPresent(i4, obj)) {
                        codedOutputStreamWriter.writeMessage(i5, UnsafeUtil.getObject(iTypeAndOffsetAt2 & i, obj), getMessageFieldSchema(i4));
                        break;
                    } else {
                        break;
                    }
                case 10:
                    i = i2;
                    if (isFieldPresent(i4, obj)) {
                        codedOutputStreamWriter.writeBytes(i5, (ByteString) UnsafeUtil.getObject(iTypeAndOffsetAt2 & i, obj));
                        break;
                    } else {
                        break;
                    }
                case 11:
                    i = i2;
                    if (isFieldPresent(i4, obj)) {
                        codedOutputStreamWriter.writeUInt32(i5, UnsafeUtil.getInt(iTypeAndOffsetAt2 & i, obj));
                        break;
                    } else {
                        break;
                    }
                case 12:
                    i = i2;
                    if (isFieldPresent(i4, obj)) {
                        codedOutputStreamWriter.writeEnum(i5, UnsafeUtil.getInt(iTypeAndOffsetAt2 & i, obj));
                        break;
                    } else {
                        break;
                    }
                case 13:
                    i = i2;
                    if (isFieldPresent(i4, obj)) {
                        codedOutputStreamWriter.writeSFixed32(i5, UnsafeUtil.getInt(iTypeAndOffsetAt2 & i, obj));
                        break;
                    } else {
                        break;
                    }
                case 14:
                    i = i2;
                    if (isFieldPresent(i4, obj)) {
                        codedOutputStreamWriter.writeSFixed64(i5, UnsafeUtil.getLong(iTypeAndOffsetAt2 & i, obj));
                        break;
                    } else {
                        break;
                    }
                case 15:
                    i = i2;
                    if (isFieldPresent(i4, obj)) {
                        codedOutputStreamWriter.writeSInt32(i5, UnsafeUtil.getInt(iTypeAndOffsetAt2 & i, obj));
                        break;
                    } else {
                        break;
                    }
                case 16:
                    i = i2;
                    if (isFieldPresent(i4, obj)) {
                        codedOutputStreamWriter.writeSInt64(i5, UnsafeUtil.getLong(iTypeAndOffsetAt2 & i, obj));
                        break;
                    } else {
                        break;
                    }
                case 17:
                    i = i2;
                    if (isFieldPresent(i4, obj)) {
                        codedOutputStreamWriter.writeGroup(i5, UnsafeUtil.getObject(iTypeAndOffsetAt2 & i, obj), getMessageFieldSchema(i4));
                        break;
                    } else {
                        break;
                    }
                case 18:
                    i = i2;
                    SchemaUtil.writeDoubleList(iArr[i4], (List) UnsafeUtil.getObject(iTypeAndOffsetAt2 & i, obj), codedOutputStreamWriter, false);
                    break;
                case 19:
                    i = i2;
                    SchemaUtil.writeFloatList(iArr[i4], (List) UnsafeUtil.getObject(iTypeAndOffsetAt2 & i, obj), codedOutputStreamWriter, false);
                    break;
                case 20:
                    i = i2;
                    SchemaUtil.writeInt64List(iArr[i4], (List) UnsafeUtil.getObject(iTypeAndOffsetAt2 & i, obj), codedOutputStreamWriter, false);
                    break;
                case 21:
                    i = i2;
                    SchemaUtil.writeUInt64List(iArr[i4], (List) UnsafeUtil.getObject(iTypeAndOffsetAt2 & i, obj), codedOutputStreamWriter, false);
                    break;
                case 22:
                    i = i2;
                    SchemaUtil.writeInt32List(iArr[i4], (List) UnsafeUtil.getObject(iTypeAndOffsetAt2 & i, obj), codedOutputStreamWriter, false);
                    break;
                case 23:
                    i = i2;
                    SchemaUtil.writeFixed64List(iArr[i4], (List) UnsafeUtil.getObject(iTypeAndOffsetAt2 & i, obj), codedOutputStreamWriter, false);
                    break;
                case 24:
                    i = i2;
                    SchemaUtil.writeFixed32List(iArr[i4], (List) UnsafeUtil.getObject(iTypeAndOffsetAt2 & i, obj), codedOutputStreamWriter, false);
                    break;
                case 25:
                    i = i2;
                    SchemaUtil.writeBoolList(iArr[i4], (List) UnsafeUtil.getObject(iTypeAndOffsetAt2 & i, obj), codedOutputStreamWriter, false);
                    break;
                case 26:
                    i = i2;
                    SchemaUtil.writeStringList(iArr[i4], (List) UnsafeUtil.getObject(iTypeAndOffsetAt2 & i, obj), codedOutputStreamWriter);
                    break;
                case 27:
                    i = i2;
                    SchemaUtil.writeMessageList(iArr[i4], (List) UnsafeUtil.getObject(iTypeAndOffsetAt2 & i, obj), codedOutputStreamWriter, getMessageFieldSchema(i4));
                    break;
                case 28:
                    i = i2;
                    SchemaUtil.writeBytesList(iArr[i4], (List) UnsafeUtil.getObject(iTypeAndOffsetAt2 & i, obj), codedOutputStreamWriter);
                    break;
                case 29:
                    i = i2;
                    SchemaUtil.writeUInt32List(iArr[i4], (List) UnsafeUtil.getObject(iTypeAndOffsetAt2 & i, obj), codedOutputStreamWriter, false);
                    break;
                case 30:
                    i = i2;
                    SchemaUtil.writeEnumList(iArr[i4], (List) UnsafeUtil.getObject(iTypeAndOffsetAt2 & i, obj), codedOutputStreamWriter, false);
                    break;
                case 31:
                    i = i2;
                    SchemaUtil.writeSFixed32List(iArr[i4], (List) UnsafeUtil.getObject(iTypeAndOffsetAt2 & i, obj), codedOutputStreamWriter, false);
                    break;
                case 32:
                    i = i2;
                    SchemaUtil.writeSFixed64List(iArr[i4], (List) UnsafeUtil.getObject(iTypeAndOffsetAt2 & i, obj), codedOutputStreamWriter, false);
                    break;
                case 33:
                    i = i2;
                    SchemaUtil.writeSInt32List(iArr[i4], (List) UnsafeUtil.getObject(iTypeAndOffsetAt2 & i, obj), codedOutputStreamWriter, false);
                    break;
                case 34:
                    i = i2;
                    SchemaUtil.writeSInt64List(iArr[i4], (List) UnsafeUtil.getObject(iTypeAndOffsetAt2 & i, obj), codedOutputStreamWriter, false);
                    break;
                case 35:
                    i = i2;
                    SchemaUtil.writeDoubleList(iArr[i4], (List) UnsafeUtil.getObject(iTypeAndOffsetAt2 & i, obj), codedOutputStreamWriter, z2);
                    break;
                case 36:
                    i = i2;
                    SchemaUtil.writeFloatList(iArr[i4], (List) UnsafeUtil.getObject(iTypeAndOffsetAt2 & i, obj), codedOutputStreamWriter, z2);
                    break;
                case 37:
                    i = i2;
                    SchemaUtil.writeInt64List(iArr[i4], (List) UnsafeUtil.getObject(iTypeAndOffsetAt2 & i, obj), codedOutputStreamWriter, z2);
                    break;
                case 38:
                    i = i2;
                    SchemaUtil.writeUInt64List(iArr[i4], (List) UnsafeUtil.getObject(iTypeAndOffsetAt2 & i, obj), codedOutputStreamWriter, z2);
                    break;
                case 39:
                    i = i2;
                    SchemaUtil.writeInt32List(iArr[i4], (List) UnsafeUtil.getObject(iTypeAndOffsetAt2 & i, obj), codedOutputStreamWriter, z2);
                    break;
                case 40:
                    i = i2;
                    SchemaUtil.writeFixed64List(iArr[i4], (List) UnsafeUtil.getObject(iTypeAndOffsetAt2 & i, obj), codedOutputStreamWriter, z2);
                    break;
                case 41:
                    i = i2;
                    SchemaUtil.writeFixed32List(iArr[i4], (List) UnsafeUtil.getObject(iTypeAndOffsetAt2 & i, obj), codedOutputStreamWriter, z2);
                    break;
                case 42:
                    i = i2;
                    SchemaUtil.writeBoolList(iArr[i4], (List) UnsafeUtil.getObject(iTypeAndOffsetAt2 & i, obj), codedOutputStreamWriter, z2);
                    break;
                case 43:
                    i = i2;
                    SchemaUtil.writeUInt32List(iArr[i4], (List) UnsafeUtil.getObject(iTypeAndOffsetAt2 & i, obj), codedOutputStreamWriter, z2);
                    break;
                case 44:
                    i = i2;
                    SchemaUtil.writeEnumList(iArr[i4], (List) UnsafeUtil.getObject(iTypeAndOffsetAt2 & i, obj), codedOutputStreamWriter, z2);
                    break;
                case 45:
                    i = i2;
                    SchemaUtil.writeSFixed32List(iArr[i4], (List) UnsafeUtil.getObject(iTypeAndOffsetAt2 & i, obj), codedOutputStreamWriter, z2);
                    break;
                case 46:
                    i = i2;
                    SchemaUtil.writeSFixed64List(iArr[i4], (List) UnsafeUtil.getObject(iTypeAndOffsetAt2 & i, obj), codedOutputStreamWriter, z2);
                    break;
                case 47:
                    i = i2;
                    SchemaUtil.writeSInt32List(iArr[i4], (List) UnsafeUtil.getObject(iTypeAndOffsetAt2 & i, obj), codedOutputStreamWriter, z2);
                    break;
                case 48:
                    i = i2;
                    z2 = true;
                    SchemaUtil.writeSInt64List(iArr[i4], (List) UnsafeUtil.getObject(iTypeAndOffsetAt2 & i, obj), codedOutputStreamWriter, true);
                    break;
                case 49:
                    i = i2;
                    SchemaUtil.writeGroupList(iArr[i4], (List) UnsafeUtil.getObject(iTypeAndOffsetAt2 & i, obj), codedOutputStreamWriter, getMessageFieldSchema(i4));
                    z2 = true;
                    break;
                case 50:
                    i = i2;
                    writeMapHelper(codedOutputStreamWriter, i5, UnsafeUtil.getObject(iTypeAndOffsetAt2 & i, obj), i4);
                    z2 = true;
                    break;
                case 51:
                    i = i2;
                    if (isOneofPresent(i5, i4, obj)) {
                        codedOutputStreamWriter.writeDouble(((Double) UnsafeUtil.getObject(iTypeAndOffsetAt2 & i, obj)).doubleValue(), i5);
                    }
                    z2 = true;
                    break;
                case 52:
                    i = i2;
                    if (isOneofPresent(i5, i4, obj)) {
                        codedOutputStreamWriter.writeFloat(((Float) UnsafeUtil.getObject(iTypeAndOffsetAt2 & i, obj)).floatValue(), i5);
                    }
                    z2 = true;
                    break;
                case 53:
                    i = i2;
                    if (isOneofPresent(i5, i4, obj)) {
                        codedOutputStreamWriter.writeInt64(i5, oneofLongAt(iTypeAndOffsetAt2 & i, obj));
                    }
                    z2 = true;
                    break;
                case 54:
                    i = i2;
                    if (isOneofPresent(i5, i4, obj)) {
                        codedOutputStreamWriter.writeUInt64(i5, oneofLongAt(iTypeAndOffsetAt2 & i, obj));
                    }
                    z2 = true;
                    break;
                case 55:
                    i = i2;
                    if (isOneofPresent(i5, i4, obj)) {
                        codedOutputStreamWriter.writeInt32(i5, oneofIntAt(iTypeAndOffsetAt2 & i, obj));
                    }
                    z2 = true;
                    break;
                case 56:
                    i = i2;
                    if (isOneofPresent(i5, i4, obj)) {
                        codedOutputStreamWriter.writeFixed64(i5, oneofLongAt(iTypeAndOffsetAt2 & i, obj));
                    }
                    z2 = true;
                    break;
                case 57:
                    i = i2;
                    if (isOneofPresent(i5, i4, obj)) {
                        codedOutputStreamWriter.writeFixed32(i5, oneofIntAt(iTypeAndOffsetAt2 & i, obj));
                    }
                    z2 = true;
                    break;
                case 58:
                    i = i2;
                    if (isOneofPresent(i5, i4, obj)) {
                        codedOutputStreamWriter.writeBool(i5, ((Boolean) UnsafeUtil.getObject(iTypeAndOffsetAt2 & i, obj)).booleanValue());
                    }
                    z2 = true;
                    break;
                case 59:
                    i = i2;
                    if (isOneofPresent(i5, i4, obj)) {
                        writeString(i5, UnsafeUtil.getObject(iTypeAndOffsetAt2 & i, obj), codedOutputStreamWriter);
                    }
                    z2 = true;
                    break;
                case 60:
                    i = i2;
                    if (isOneofPresent(i5, i4, obj)) {
                        codedOutputStreamWriter.writeMessage(i5, UnsafeUtil.getObject(iTypeAndOffsetAt2 & i, obj), getMessageFieldSchema(i4));
                    }
                    z2 = true;
                    break;
                case 61:
                    i = i2;
                    if (isOneofPresent(i5, i4, obj)) {
                        codedOutputStreamWriter.writeBytes(i5, (ByteString) UnsafeUtil.getObject(iTypeAndOffsetAt2 & i, obj));
                    }
                    z2 = true;
                    break;
                case 62:
                    i = i2;
                    if (isOneofPresent(i5, i4, obj)) {
                        codedOutputStreamWriter.writeUInt32(i5, oneofIntAt(iTypeAndOffsetAt2 & i, obj));
                    }
                    z2 = true;
                    break;
                case 63:
                    i = i2;
                    if (isOneofPresent(i5, i4, obj)) {
                        codedOutputStreamWriter.writeEnum(i5, oneofIntAt(iTypeAndOffsetAt2 & i, obj));
                    }
                    z2 = true;
                    break;
                case 64:
                    i = i2;
                    if (isOneofPresent(i5, i4, obj)) {
                        codedOutputStreamWriter.writeSFixed32(i5, oneofIntAt(iTypeAndOffsetAt2 & i, obj));
                    }
                    z2 = true;
                    break;
                case 65:
                    i = i2;
                    if (isOneofPresent(i5, i4, obj)) {
                        codedOutputStreamWriter.writeSFixed64(i5, oneofLongAt(iTypeAndOffsetAt2 & i, obj));
                    }
                    z2 = true;
                    break;
                case 66:
                    i = i2;
                    if (isOneofPresent(i5, i4, obj)) {
                        codedOutputStreamWriter.writeSInt32(i5, oneofIntAt(iTypeAndOffsetAt2 & i, obj));
                    }
                    z2 = true;
                    break;
                case 67:
                    i = i2;
                    if (isOneofPresent(i5, i4, obj)) {
                        codedOutputStreamWriter.writeSInt64(i5, oneofLongAt(iTypeAndOffsetAt2 & i, obj));
                    }
                    z2 = true;
                    break;
                case 68:
                    if (isOneofPresent(i5, i4, obj)) {
                        i = i2;
                        codedOutputStreamWriter.writeGroup(i5, UnsafeUtil.getObject(iTypeAndOffsetAt2 & i2, obj), getMessageFieldSchema(i4));
                        z2 = true;
                        break;
                    }
                default:
                    i = i2;
                    break;
            }
            i4 += 3;
            i2 = i;
        }
        while (entry != null) {
            extensionSchema.serializeExtension(codedOutputStreamWriter, entry);
            entry = it.hasNext() ? (Map.Entry) it.next() : null;
        }
        unknownFieldSchema.writeTo(unknownFieldSchema.getFromMessage(obj), codedOutputStreamWriter);
    }

    @Override // com.google.protobuf.Schema
    public final void mergeFrom(Object obj, CodedInputStreamReader codedInputStreamReader, ExtensionRegistryLite extensionRegistryLite) throws Throwable {
        extensionRegistryLite.getClass();
        checkMutable(obj);
        mergeFromHelper(this.unknownFieldSchema, this.extensionSchema, obj, codedInputStreamReader, extensionRegistryLite);
    }

    /* JADX WARN: Failed to find 'out' block for switch in B:34:0x0097. Please report as an issue. */
    @Override // com.google.protobuf.Schema
    public final void mergeFrom(Object obj, byte[] bArr, int i, int i2, ArrayDecoders.Registers registers) throws InvalidProtocolBufferException {
        int i3;
        int iSlowPositionForFieldNumber;
        Object obj2;
        Unsafe unsafe;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        int i11;
        int i12;
        Object obj3;
        ArrayDecoders.Registers registers2;
        byte[] bArr2;
        int i13;
        Unsafe unsafe2;
        int i14;
        byte[] bArr3;
        int iDecodeStringRequireUtf8;
        Unsafe unsafe3;
        Unsafe unsafe4;
        byte[] bArr4;
        ArrayDecoders.Registers registers3;
        MessageSchema messageSchema = this;
        Object obj4 = obj;
        byte[] bArr5 = bArr;
        int i15 = i2;
        ArrayDecoders.Registers registers4 = registers;
        if (messageSchema.proto3) {
            checkMutable(obj4);
            Unsafe unsafe5 = UNSAFE;
            int iDecodeVarint64 = i;
            int i16 = -1;
            int i17 = 0;
            int i18 = 1048575;
            int i19 = 0;
            while (iDecodeVarint64 < i15) {
                int iDecodeVarint32 = iDecodeVarint64 + 1;
                int i20 = bArr5[iDecodeVarint64];
                if (i20 < 0) {
                    iDecodeVarint32 = ArrayDecoders.decodeVarint32(i20, bArr5, iDecodeVarint32, registers4);
                    i20 = registers4.int1;
                }
                int i21 = i20 >>> 3;
                int i22 = i20 & 7;
                int i23 = messageSchema.maxFieldNumber;
                int i24 = messageSchema.minFieldNumber;
                if (i21 > i16) {
                    iSlowPositionForFieldNumber = (i21 < i24 || i21 > i23) ? -1 : messageSchema.slowPositionForFieldNumber(i21, i17 / 3);
                    i3 = 0;
                } else if (i21 < i24 || i21 > i23) {
                    i3 = 0;
                    iSlowPositionForFieldNumber = -1;
                } else {
                    i3 = 0;
                    iSlowPositionForFieldNumber = messageSchema.slowPositionForFieldNumber(i21, 0);
                }
                int i25 = iSlowPositionForFieldNumber;
                if (i25 == -1) {
                    obj2 = obj4;
                    unsafe = unsafe5;
                    i4 = i20;
                    i5 = iDecodeVarint32;
                    i6 = i21;
                    i7 = i3;
                } else {
                    int[] iArr = messageSchema.buffer;
                    int i26 = iArr[i25 + 1];
                    int iType = type(i26);
                    int i27 = i20;
                    long j = i26 & 1048575;
                    if (iType <= 17) {
                        int i28 = iArr[i25 + 2];
                        int i29 = 1 << (i28 >>> 20);
                        int i30 = i28 & 1048575;
                        if (i30 != i18) {
                            if (i18 != 1048575) {
                                unsafe5.putInt(obj4, i18, i19);
                            }
                            if (i30 != 1048575) {
                                i19 = unsafe5.getInt(obj4, i30);
                            }
                            i18 = i30;
                        }
                        switch (iType) {
                            case 0:
                                ArrayDecoders.Registers registers5 = registers4;
                                bArr4 = bArr5;
                                registers3 = registers5;
                                i14 = iDecodeVarint32;
                                i13 = i25;
                                unsafe3 = unsafe5;
                                if (i22 != 1) {
                                    i4 = i27;
                                    i5 = i14;
                                    unsafe = unsafe3;
                                    i6 = i21;
                                    i7 = i13;
                                    obj2 = obj4;
                                    break;
                                } else {
                                    UnsafeUtil.MEMORY_ACCESSOR.putDouble(obj, j, Double.longBitsToDouble(ArrayDecoders.decodeFixed64(i14, bArr4)));
                                    obj4 = obj;
                                    iDecodeVarint64 = i14 + 8;
                                    i19 |= i29;
                                    byte[] bArr6 = bArr4;
                                    registers4 = registers3;
                                    bArr5 = bArr6;
                                    i15 = i2;
                                    unsafe5 = unsafe3;
                                    i16 = i21;
                                    i17 = i13;
                                    break;
                                }
                            case 1:
                                ArrayDecoders.Registers registers6 = registers4;
                                bArr4 = bArr5;
                                registers3 = registers6;
                                i14 = iDecodeVarint32;
                                i13 = i25;
                                unsafe3 = unsafe5;
                                if (i22 != 5) {
                                    i4 = i27;
                                    i5 = i14;
                                    unsafe = unsafe3;
                                    i6 = i21;
                                    i7 = i13;
                                    obj2 = obj4;
                                    break;
                                } else {
                                    UnsafeUtil.MEMORY_ACCESSOR.putFloat(obj4, j, Float.intBitsToFloat(ArrayDecoders.decodeFixed32(i14, bArr4)));
                                    iDecodeVarint64 = i14 + 4;
                                    i19 |= i29;
                                    byte[] bArr62 = bArr4;
                                    registers4 = registers3;
                                    bArr5 = bArr62;
                                    i15 = i2;
                                    unsafe5 = unsafe3;
                                    i16 = i21;
                                    i17 = i13;
                                    break;
                                }
                            case 2:
                            case 3:
                                ArrayDecoders.Registers registers7 = registers4;
                                byte[] bArr7 = bArr5;
                                i14 = iDecodeVarint32;
                                i13 = i25;
                                if (i22 != 0) {
                                    unsafe3 = unsafe5;
                                    i4 = i27;
                                    i5 = i14;
                                    unsafe = unsafe3;
                                    i6 = i21;
                                    i7 = i13;
                                    obj2 = obj4;
                                    break;
                                } else {
                                    int iDecodeVarint642 = ArrayDecoders.decodeVarint64(bArr7, i14, registers7);
                                    Unsafe unsafe6 = unsafe5;
                                    Object obj5 = obj4;
                                    unsafe6.putLong(obj5, j, registers7.long1);
                                    obj4 = obj5;
                                    i19 |= i29;
                                    registers4 = registers7;
                                    bArr5 = bArr7;
                                    iDecodeVarint64 = iDecodeVarint642;
                                    unsafe5 = unsafe6;
                                    i16 = i21;
                                    i17 = i13;
                                    i15 = i2;
                                    break;
                                }
                            case 4:
                            case 11:
                                ArrayDecoders.Registers registers8 = registers4;
                                byte[] bArr8 = bArr5;
                                i14 = iDecodeVarint32;
                                i13 = i25;
                                if (i22 != 0) {
                                    unsafe3 = unsafe5;
                                    i4 = i27;
                                    i5 = i14;
                                    unsafe = unsafe3;
                                    i6 = i21;
                                    i7 = i13;
                                    obj2 = obj4;
                                    break;
                                } else {
                                    int iDecodeVarint322 = ArrayDecoders.decodeVarint32(bArr8, i14, registers8);
                                    unsafe5.putInt(obj4, j, registers8.int1);
                                    i19 |= i29;
                                    registers4 = registers8;
                                    bArr5 = bArr8;
                                    i15 = i2;
                                    iDecodeVarint64 = iDecodeVarint322;
                                    i16 = i21;
                                    i17 = i13;
                                    break;
                                }
                            case 5:
                            case 14:
                                obj3 = obj4;
                                ArrayDecoders.Registers registers9 = registers4;
                                byte[] bArr9 = bArr5;
                                i13 = i25;
                                Unsafe unsafe7 = unsafe5;
                                if (i22 != 1) {
                                    i14 = iDecodeVarint32;
                                    unsafe3 = unsafe7;
                                    obj4 = obj3;
                                    i4 = i27;
                                    i5 = i14;
                                    unsafe = unsafe3;
                                    i6 = i21;
                                    i7 = i13;
                                    obj2 = obj4;
                                    break;
                                } else {
                                    int i31 = iDecodeVarint32;
                                    unsafe7.putLong(obj3, j, ArrayDecoders.decodeFixed64(i31, bArr9));
                                    unsafe5 = unsafe7;
                                    obj4 = obj3;
                                    iDecodeVarint64 = i31 + 8;
                                    i19 |= i29;
                                    registers4 = registers9;
                                    bArr5 = bArr9;
                                    i15 = i2;
                                    i16 = i21;
                                    i17 = i13;
                                    break;
                                }
                            case 6:
                            case 13:
                                obj3 = obj4;
                                registers2 = registers4;
                                bArr2 = bArr5;
                                i13 = i25;
                                unsafe2 = unsafe5;
                                if (i22 != 5) {
                                    i14 = iDecodeVarint32;
                                    unsafe3 = unsafe2;
                                    obj4 = obj3;
                                    i4 = i27;
                                    i5 = i14;
                                    unsafe = unsafe3;
                                    i6 = i21;
                                    i7 = i13;
                                    obj2 = obj4;
                                    break;
                                } else {
                                    unsafe2.putInt(obj3, j, ArrayDecoders.decodeFixed32(iDecodeVarint32, bArr2));
                                    iDecodeVarint64 = iDecodeVarint32 + 4;
                                    i19 |= i29;
                                    i15 = i2;
                                    unsafe5 = unsafe2;
                                    bArr5 = bArr2;
                                    i16 = i21;
                                    i17 = i13;
                                    registers4 = registers2;
                                    obj4 = obj3;
                                    break;
                                }
                            case 7:
                                obj3 = obj4;
                                registers2 = registers4;
                                bArr2 = bArr5;
                                unsafe2 = unsafe5;
                                if (i22 != 0) {
                                    i13 = i25;
                                    i14 = iDecodeVarint32;
                                    unsafe3 = unsafe2;
                                    obj4 = obj3;
                                    i4 = i27;
                                    i5 = i14;
                                    unsafe = unsafe3;
                                    i6 = i21;
                                    i7 = i13;
                                    obj2 = obj4;
                                    break;
                                } else {
                                    iDecodeVarint64 = ArrayDecoders.decodeVarint64(bArr2, iDecodeVarint32, registers2);
                                    i13 = i25;
                                    UnsafeUtil.MEMORY_ACCESSOR.putBoolean(obj3, j, registers2.long1 != 0);
                                    i19 |= i29;
                                    i15 = i2;
                                    unsafe5 = unsafe2;
                                    bArr5 = bArr2;
                                    i16 = i21;
                                    i17 = i13;
                                    registers4 = registers2;
                                    obj4 = obj3;
                                    break;
                                }
                            case 8:
                                obj3 = obj4;
                                registers2 = registers4;
                                bArr3 = bArr5;
                                unsafe2 = unsafe5;
                                if (i22 != 2) {
                                    i14 = iDecodeVarint32;
                                    i13 = i25;
                                    unsafe3 = unsafe2;
                                    obj4 = obj3;
                                    i4 = i27;
                                    i5 = i14;
                                    unsafe = unsafe3;
                                    i6 = i21;
                                    i7 = i13;
                                    obj2 = obj4;
                                    break;
                                } else {
                                    if ((i26 & VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS) == 0) {
                                        iDecodeStringRequireUtf8 = ArrayDecoders.decodeString(bArr3, iDecodeVarint32, registers2);
                                    } else {
                                        iDecodeStringRequireUtf8 = ArrayDecoders.decodeStringRequireUtf8(bArr3, iDecodeVarint32, registers2);
                                    }
                                    iDecodeVarint64 = iDecodeStringRequireUtf8;
                                    unsafe2.putObject(obj3, j, registers2.object1);
                                    i19 |= i29;
                                    i15 = i2;
                                    unsafe5 = unsafe2;
                                    i17 = i25;
                                    bArr5 = bArr3;
                                    i16 = i21;
                                    registers4 = registers2;
                                    obj4 = obj3;
                                    break;
                                }
                            case 9:
                                obj3 = obj4;
                                Unsafe unsafe8 = unsafe5;
                                if (i22 != 2) {
                                    unsafe2 = unsafe8;
                                    registers2 = registers4;
                                    i14 = iDecodeVarint32;
                                    i13 = i25;
                                    unsafe3 = unsafe2;
                                    obj4 = obj3;
                                    i4 = i27;
                                    i5 = i14;
                                    unsafe = unsafe3;
                                    i6 = i21;
                                    i7 = i13;
                                    obj2 = obj4;
                                    break;
                                } else {
                                    Object objMutableMessageFieldForMerge = messageSchema.mutableMessageFieldForMerge(i25, obj3);
                                    byte[] bArr10 = bArr5;
                                    unsafe2 = unsafe8;
                                    int i32 = iDecodeVarint32;
                                    ArrayDecoders.Registers registers10 = registers4;
                                    int iMergeMessageField = ArrayDecoders.mergeMessageField(objMutableMessageFieldForMerge, messageSchema.getMessageFieldSchema(i25), bArr10, i32, i2, registers10);
                                    bArr3 = bArr10;
                                    registers2 = registers10;
                                    messageSchema.storeMessageField(i25, obj3, objMutableMessageFieldForMerge);
                                    i19 |= i29;
                                    i15 = i2;
                                    iDecodeVarint64 = iMergeMessageField;
                                    unsafe5 = unsafe2;
                                    i17 = i25;
                                    bArr5 = bArr3;
                                    i16 = i21;
                                    registers4 = registers2;
                                    obj4 = obj3;
                                    break;
                                }
                            case 10:
                                obj3 = obj4;
                                unsafe4 = unsafe5;
                                if (i22 != 2) {
                                    i14 = iDecodeVarint32;
                                    i13 = i25;
                                    unsafe3 = unsafe4;
                                    obj4 = obj3;
                                    i4 = i27;
                                    i5 = i14;
                                    unsafe = unsafe3;
                                    i6 = i21;
                                    i7 = i13;
                                    obj2 = obj4;
                                    break;
                                } else {
                                    iDecodeVarint64 = ArrayDecoders.decodeBytes(bArr5, iDecodeVarint32, registers4);
                                    unsafe4.putObject(obj3, j, registers4.object1);
                                    i19 |= i29;
                                    i15 = i2;
                                    unsafe5 = unsafe4;
                                    i17 = i25;
                                    i16 = i21;
                                    obj4 = obj3;
                                    break;
                                }
                            case 12:
                                obj3 = obj4;
                                unsafe4 = unsafe5;
                                if (i22 != 0) {
                                    i14 = iDecodeVarint32;
                                    i13 = i25;
                                    unsafe3 = unsafe4;
                                    obj4 = obj3;
                                    i4 = i27;
                                    i5 = i14;
                                    unsafe = unsafe3;
                                    i6 = i21;
                                    i7 = i13;
                                    obj2 = obj4;
                                    break;
                                } else {
                                    iDecodeVarint64 = ArrayDecoders.decodeVarint32(bArr5, iDecodeVarint32, registers4);
                                    unsafe4.putInt(obj3, j, registers4.int1);
                                    i19 |= i29;
                                    i15 = i2;
                                    unsafe5 = unsafe4;
                                    i17 = i25;
                                    i16 = i21;
                                    obj4 = obj3;
                                    break;
                                }
                            case 15:
                                obj3 = obj4;
                                unsafe4 = unsafe5;
                                if (i22 != 0) {
                                    i14 = iDecodeVarint32;
                                    i13 = i25;
                                    unsafe3 = unsafe4;
                                    obj4 = obj3;
                                    i4 = i27;
                                    i5 = i14;
                                    unsafe = unsafe3;
                                    i6 = i21;
                                    i7 = i13;
                                    obj2 = obj4;
                                    break;
                                } else {
                                    iDecodeVarint64 = ArrayDecoders.decodeVarint32(bArr5, iDecodeVarint32, registers4);
                                    unsafe4.putInt(obj3, j, CodedInputStream.decodeZigZag32(registers4.int1));
                                    i19 |= i29;
                                    i15 = i2;
                                    unsafe5 = unsafe4;
                                    i17 = i25;
                                    i16 = i21;
                                    obj4 = obj3;
                                    break;
                                }
                            case 16:
                                if (i22 != 0) {
                                    i14 = iDecodeVarint32;
                                    i13 = i25;
                                    unsafe3 = unsafe5;
                                    i4 = i27;
                                    i5 = i14;
                                    unsafe = unsafe3;
                                    i6 = i21;
                                    i7 = i13;
                                    obj2 = obj4;
                                    break;
                                } else {
                                    int iDecodeVarint643 = ArrayDecoders.decodeVarint64(bArr5, iDecodeVarint32, registers4);
                                    Unsafe unsafe9 = unsafe5;
                                    Object obj6 = obj4;
                                    unsafe9.putLong(obj6, j, CodedInputStream.decodeZigZag64(registers4.long1));
                                    obj3 = obj6;
                                    i19 |= i29;
                                    i15 = i2;
                                    unsafe5 = unsafe9;
                                    i17 = i25;
                                    iDecodeVarint64 = iDecodeVarint643;
                                    i16 = i21;
                                    obj4 = obj3;
                                    break;
                                }
                            default:
                                i14 = iDecodeVarint32;
                                i13 = i25;
                                unsafe3 = unsafe5;
                                i4 = i27;
                                i5 = i14;
                                unsafe = unsafe3;
                                i6 = i21;
                                i7 = i13;
                                obj2 = obj4;
                                break;
                        }
                    } else {
                        ArrayDecoders.Registers registers11 = registers4;
                        byte[] bArr11 = bArr5;
                        int i33 = iDecodeVarint32;
                        Unsafe unsafe10 = unsafe5;
                        if (iType != 27) {
                            i7 = i25;
                            if (iType <= 49) {
                                i8 = i18;
                                unsafe = unsafe10;
                                i9 = i19;
                                int repeatedField = messageSchema.parseRepeatedField(obj, bArr, i33, i2, i27, i21, i22, i7, i26, iType, j, registers);
                                i11 = i27;
                                i10 = i21;
                                if (repeatedField != i33) {
                                    messageSchema = this;
                                    obj4 = obj;
                                    registers4 = registers;
                                    iDecodeVarint64 = repeatedField;
                                    i17 = i7;
                                    i16 = i10;
                                    i18 = i8;
                                    i19 = i9;
                                    unsafe5 = unsafe;
                                    bArr5 = bArr;
                                    i15 = i2;
                                } else {
                                    i5 = repeatedField;
                                    i6 = i10;
                                    i4 = i11;
                                    i18 = i8;
                                    i19 = i9;
                                    obj2 = obj;
                                }
                            } else {
                                i8 = i18;
                                unsafe = unsafe10;
                                i9 = i19;
                                i10 = i21;
                                i11 = i27;
                                i12 = i33;
                                if (iType != 50) {
                                    i6 = i10;
                                    i4 = i11;
                                    int oneofField = parseOneofField(obj, bArr, i12, i2, i4, i6, i22, i26, iType, j, i7, registers);
                                    obj2 = obj;
                                    i7 = i7;
                                    if (oneofField != i12) {
                                        messageSchema = this;
                                        registers4 = registers;
                                        i16 = i6;
                                        iDecodeVarint64 = oneofField;
                                        i17 = i7;
                                        obj4 = obj2;
                                        i18 = i8;
                                        i19 = i9;
                                        unsafe5 = unsafe;
                                        bArr5 = bArr;
                                        i15 = i2;
                                    } else {
                                        i5 = oneofField;
                                        i18 = i8;
                                        i19 = i9;
                                    }
                                } else if (i22 == 2) {
                                    int mapField = parseMapField(obj, bArr, i12, i2, i7, j, registers);
                                    i7 = i7;
                                    if (mapField != i12) {
                                        messageSchema = this;
                                        obj4 = obj;
                                        bArr5 = bArr;
                                        registers4 = registers;
                                        iDecodeVarint64 = mapField;
                                        i17 = i7;
                                        i16 = i10;
                                        i18 = i8;
                                        i19 = i9;
                                        unsafe5 = unsafe;
                                        i15 = i2;
                                    } else {
                                        i5 = mapField;
                                        i6 = i10;
                                        i4 = i11;
                                        i18 = i8;
                                        i19 = i9;
                                        obj2 = obj;
                                    }
                                } else {
                                    i5 = i12;
                                    i6 = i10;
                                    i4 = i11;
                                    i18 = i8;
                                    i19 = i9;
                                    obj2 = obj;
                                }
                            }
                        } else if (i22 == 2) {
                            Internal.ProtobufList protobufListMutableCopyWithCapacity = (Internal.ProtobufList) unsafe10.getObject(obj4, j);
                            if (!((AbstractProtobufList) protobufListMutableCopyWithCapacity).isMutable) {
                                int size = protobufListMutableCopyWithCapacity.size();
                                protobufListMutableCopyWithCapacity = protobufListMutableCopyWithCapacity.mutableCopyWithCapacity(size == 0 ? 10 : size * 2);
                                unsafe10.putObject(obj4, j, protobufListMutableCopyWithCapacity);
                            }
                            int iDecodeMessageList = ArrayDecoders.decodeMessageList(messageSchema.getMessageFieldSchema(i25), i27, bArr11, i33, i2, protobufListMutableCopyWithCapacity, registers11);
                            bArr5 = bArr;
                            registers4 = registers;
                            iDecodeVarint64 = iDecodeMessageList;
                            i17 = i25;
                            unsafe5 = unsafe10;
                            i16 = i21;
                            obj4 = obj;
                            i15 = i2;
                        } else {
                            i11 = i27;
                            i12 = i33;
                            unsafe = unsafe10;
                            i8 = i18;
                            i9 = i19;
                            i10 = i21;
                            i7 = i25;
                            i5 = i12;
                            i6 = i10;
                            i4 = i11;
                            i18 = i8;
                            i19 = i9;
                            obj2 = obj;
                        }
                    }
                }
                int iDecodeUnknownField = ArrayDecoders.decodeUnknownField(i4, bArr, i5, i2, getMutableUnknownFields(obj2), registers);
                bArr5 = bArr;
                registers4 = registers;
                i16 = i6;
                i17 = i7;
                obj4 = obj2;
                unsafe5 = unsafe;
                i15 = i2;
                iDecodeVarint64 = iDecodeUnknownField;
                messageSchema = this;
            }
            Object obj7 = obj4;
            Unsafe unsafe11 = unsafe5;
            int i34 = i15;
            int i35 = i18;
            int i36 = i19;
            if (i35 != 1048575) {
                unsafe11.putInt(obj7, i35, i36);
            }
            if (iDecodeVarint64 != i34) {
                throw InvalidProtocolBufferException.parseFailure();
            }
            return;
        }
        parseProto2Message(obj4, bArr, i, i15, 0, registers);
    }
}
