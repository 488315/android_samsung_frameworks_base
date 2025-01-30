package com.google.protobuf;

import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticOutline0;
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
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.security.AccessController;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import sun.misc.Unsafe;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.google.protobuf.MessageSchema$1 */
    public abstract /* synthetic */ class AbstractC45351 {
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
            unsafe = (Unsafe) AccessController.doPrivileged(new UnsafeUtil.C45421());
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
        switch (AbstractC45351.$SwitchMap$com$google$protobuf$WireFormat$FieldType[wireFormat$FieldType.ordinal()]) {
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

    public static List listAt(long j, Object obj) {
        return (List) UnsafeUtil.getObject(j, obj);
    }

    /* JADX WARN: Removed duplicated region for block: B:255:0x0535  */
    /* JADX WARN: Removed duplicated region for block: B:258:0x053d  */
    /* JADX WARN: Removed duplicated region for block: B:270:0x0579  */
    /* JADX WARN: Removed duplicated region for block: B:281:0x05ba  */
    /* JADX WARN: Removed duplicated region for block: B:285:0x05c2  */
    /* JADX WARN: Removed duplicated region for block: B:295:0x0591  */
    /* JADX WARN: Removed duplicated region for block: B:303:0x056e  */
    /* JADX WARN: Removed duplicated region for block: B:304:0x0573  */
    /* JADX WARN: Removed duplicated region for block: B:305:0x0540  */
    /* JADX WARN: Removed duplicated region for block: B:306:0x0538  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static MessageSchema newSchema(MessageInfo messageInfo, NewInstanceSchema newInstanceSchema, ListFieldSchema listFieldSchema, UnknownFieldSchema unknownFieldSchema, ExtensionSchema extensionSchema, MapFieldSchema mapFieldSchema) {
        int i;
        int i2;
        FieldInfo[] fieldInfoArr;
        int[] iArr;
        int[] iArr2;
        int m240id;
        int i3;
        long objectFieldOffset;
        int i4;
        int i5;
        StructuralMessageInfo structuralMessageInfo;
        int i6;
        int i7;
        int i8;
        int i9;
        Class<?> type;
        Object obj;
        FieldType fieldType;
        int i10;
        int i11;
        int i12;
        int charAt;
        int i13;
        int charAt2;
        int i14;
        int i15;
        int charAt3;
        int[] iArr3;
        int i16;
        int i17;
        int i18;
        int i19;
        char charAt4;
        int i20;
        char charAt5;
        int i21;
        char charAt6;
        int i22;
        char charAt7;
        int i23;
        char charAt8;
        int i24;
        char charAt9;
        int i25;
        char charAt10;
        int i26;
        char charAt11;
        int i27;
        int i28;
        int i29;
        int objectFieldOffset2;
        String str;
        int i30;
        int i31;
        int i32;
        int i33;
        int i34;
        Field reflectField;
        int i35;
        char charAt12;
        int i36;
        int i37;
        int i38;
        Field reflectField2;
        Field reflectField3;
        int i39;
        char charAt13;
        int i40;
        char charAt14;
        int i41;
        char charAt15;
        int i42;
        char charAt16;
        boolean z = messageInfo instanceof RawMessageInfo;
        int[] iArr4 = EMPTY_INT_ARRAY;
        int i43 = 0;
        if (!z) {
            StructuralMessageInfo structuralMessageInfo2 = (StructuralMessageInfo) messageInfo;
            boolean z2 = structuralMessageInfo2.syntax == ProtoSyntax.PROTO3;
            FieldInfo[] fieldInfoArr2 = structuralMessageInfo2.fields;
            if (fieldInfoArr2.length == 0) {
                i = 0;
                i2 = 0;
            } else {
                i = fieldInfoArr2[0].fieldNumber;
                i2 = fieldInfoArr2[fieldInfoArr2.length - 1].fieldNumber;
            }
            int length = fieldInfoArr2.length;
            int[] iArr5 = new int[length * 3];
            Object[] objArr = new Object[length * 2];
            int i44 = 0;
            int i45 = 0;
            for (FieldInfo fieldInfo : fieldInfoArr2) {
                FieldType fieldType2 = fieldInfo.type;
                if (fieldType2 == FieldType.MAP) {
                    i44++;
                } else if (fieldType2.m240id() >= 18 && fieldInfo.type.m240id() <= 49) {
                    i45++;
                }
            }
            int[] iArr6 = i44 > 0 ? new int[i44] : null;
            int[] iArr7 = i45 > 0 ? new int[i45] : null;
            int[] iArr8 = structuralMessageInfo2.checkInitialized;
            if (iArr8 == null) {
                iArr8 = iArr4;
            }
            int i46 = 0;
            int i47 = 0;
            int i48 = 0;
            int i49 = 0;
            int i50 = 0;
            while (i46 < fieldInfoArr2.length) {
                FieldInfo fieldInfo2 = fieldInfoArr2[i46];
                int i51 = fieldInfo2.fieldNumber;
                OneofInfo oneofInfo = fieldInfo2.oneof;
                if (oneofInfo != null) {
                    fieldInfoArr = fieldInfoArr2;
                    int m240id2 = fieldInfo2.type.m240id() + 51;
                    iArr = iArr4;
                    int objectFieldOffset3 = (int) UnsafeUtil.objectFieldOffset(oneofInfo.valueField);
                    objectFieldOffset = UnsafeUtil.objectFieldOffset(oneofInfo.caseField);
                    m240id = m240id2;
                    iArr2 = iArr6;
                    i3 = objectFieldOffset3;
                } else {
                    fieldInfoArr = fieldInfoArr2;
                    iArr = iArr4;
                    FieldType fieldType3 = fieldInfo2.type;
                    iArr2 = iArr6;
                    int objectFieldOffset4 = (int) UnsafeUtil.objectFieldOffset(fieldInfo2.field);
                    m240id = fieldType3.m240id();
                    if (fieldType3.isList() || fieldType3.isMap()) {
                        i3 = objectFieldOffset4;
                        Field field = fieldInfo2.cachedSizeField;
                        if (field == null) {
                            i4 = 0;
                            i5 = 0;
                        } else {
                            objectFieldOffset = UnsafeUtil.objectFieldOffset(field);
                        }
                    } else {
                        Field field2 = fieldInfo2.presenceField;
                        if (field2 == null) {
                            i3 = objectFieldOffset4;
                            i4 = 1048575;
                        } else {
                            i3 = objectFieldOffset4;
                            i4 = (int) UnsafeUtil.objectFieldOffset(field2);
                        }
                        i5 = Integer.numberOfTrailingZeros(fieldInfo2.presenceMask);
                    }
                    structuralMessageInfo = structuralMessageInfo2;
                    i6 = i5;
                    i7 = i3;
                    i8 = m240id;
                    iArr5[i47] = fieldInfo2.fieldNumber;
                    int i52 = i46;
                    int[] iArr9 = iArr7;
                    iArr5[i47 + 1] = i7 | (i8 << 20) | (!fieldInfo2.required ? QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE : 0) | (!fieldInfo2.enforceUtf8 ? QuickStepContract.SYSUI_STATE_KNOX_HARD_KEY_INTENT : 0);
                    iArr5[i47 + 2] = i4 | (i6 << 20);
                    i9 = FieldInfo.AbstractC45291.$SwitchMap$com$google$protobuf$FieldType[fieldInfo2.type.ordinal()];
                    if (i9 != 1 || i9 == 2) {
                        Field field3 = fieldInfo2.field;
                        type = field3 == null ? field3.getType() : fieldInfo2.oneofStoredType;
                    } else {
                        type = (i9 == 3 || i9 == 4) ? fieldInfo2.messageClass : null;
                    }
                    obj = fieldInfo2.mapDefaultEntry;
                    if (obj == null) {
                        int i53 = (i47 / 3) * 2;
                        objArr[i53] = obj;
                        if (type != null) {
                            objArr[i53 + 1] = type;
                        } else {
                            Internal.EnumVerifier enumVerifier = fieldInfo2.enumVerifier;
                            if (enumVerifier != null) {
                                objArr[i53 + 1] = enumVerifier;
                            }
                        }
                    } else if (type != null) {
                        objArr[((i47 / 3) * 2) + 1] = type;
                    } else {
                        Internal.EnumVerifier enumVerifier2 = fieldInfo2.enumVerifier;
                        if (enumVerifier2 != null) {
                            objArr[((i47 / 3) * 2) + 1] = enumVerifier2;
                        }
                    }
                    if (i49 < iArr8.length && iArr8[i49] == i51) {
                        iArr8[i49] = i47;
                        i49++;
                    }
                    fieldType = fieldInfo2.type;
                    if (fieldType != FieldType.MAP) {
                        iArr2[i50] = i47;
                        i50++;
                    } else if (fieldType.m240id() >= 18) {
                        if (fieldInfo2.type.m240id() <= 49) {
                            iArr9[i48] = (int) UnsafeUtil.objectFieldOffset(fieldInfo2.field);
                            i48++;
                        }
                        i46 = i52 + 1;
                        i47 += 3;
                        fieldInfoArr2 = fieldInfoArr;
                        iArr6 = iArr2;
                        iArr4 = iArr;
                        structuralMessageInfo2 = structuralMessageInfo;
                        iArr7 = iArr9;
                    }
                    i46 = i52 + 1;
                    i47 += 3;
                    fieldInfoArr2 = fieldInfoArr;
                    iArr6 = iArr2;
                    iArr4 = iArr;
                    structuralMessageInfo2 = structuralMessageInfo;
                    iArr7 = iArr9;
                }
                i4 = (int) objectFieldOffset;
                structuralMessageInfo = structuralMessageInfo2;
                i7 = i3;
                i8 = m240id;
                i6 = 0;
                iArr5[i47] = fieldInfo2.fieldNumber;
                int i522 = i46;
                int[] iArr92 = iArr7;
                if (!fieldInfo2.enforceUtf8) {
                }
                iArr5[i47 + 1] = i7 | (i8 << 20) | (!fieldInfo2.required ? QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE : 0) | (!fieldInfo2.enforceUtf8 ? QuickStepContract.SYSUI_STATE_KNOX_HARD_KEY_INTENT : 0);
                iArr5[i47 + 2] = i4 | (i6 << 20);
                i9 = FieldInfo.AbstractC45291.$SwitchMap$com$google$protobuf$FieldType[fieldInfo2.type.ordinal()];
                if (i9 != 1) {
                }
                Field field32 = fieldInfo2.field;
                if (field32 == null) {
                }
                obj = fieldInfo2.mapDefaultEntry;
                if (obj == null) {
                }
                if (i49 < iArr8.length) {
                    iArr8[i49] = i47;
                    i49++;
                }
                fieldType = fieldInfo2.type;
                if (fieldType != FieldType.MAP) {
                }
                i46 = i522 + 1;
                i47 += 3;
                fieldInfoArr2 = fieldInfoArr;
                iArr6 = iArr2;
                iArr4 = iArr;
                structuralMessageInfo2 = structuralMessageInfo;
                iArr7 = iArr92;
            }
            StructuralMessageInfo structuralMessageInfo3 = structuralMessageInfo2;
            int[] iArr10 = iArr4;
            int[] iArr11 = iArr6;
            int[] iArr12 = iArr7;
            int[] iArr13 = iArr11 == null ? iArr10 : iArr11;
            int[] iArr14 = iArr12 == null ? iArr10 : iArr12;
            int[] iArr15 = new int[iArr8.length + iArr13.length + iArr14.length];
            System.arraycopy(iArr8, 0, iArr15, 0, iArr8.length);
            System.arraycopy(iArr13, 0, iArr15, iArr8.length, iArr13.length);
            System.arraycopy(iArr14, 0, iArr15, iArr8.length + iArr13.length, iArr14.length);
            return new MessageSchema(iArr5, objArr, i, i2, structuralMessageInfo3.defaultInstance, z2, true, iArr15, iArr8.length, iArr8.length + iArr13.length, newInstanceSchema, listFieldSchema, unknownFieldSchema, extensionSchema, mapFieldSchema);
        }
        RawMessageInfo rawMessageInfo = (RawMessageInfo) messageInfo;
        boolean z3 = rawMessageInfo.getSyntax() == ProtoSyntax.PROTO3;
        String str2 = rawMessageInfo.info;
        int length2 = str2.length();
        char c = 55296;
        if (str2.charAt(0) >= 55296) {
            int i54 = 1;
            while (true) {
                i10 = i54 + 1;
                if (str2.charAt(i54) < 55296) {
                    break;
                }
                i54 = i10;
            }
        } else {
            i10 = 1;
        }
        int i55 = i10 + 1;
        int charAt17 = str2.charAt(i10);
        if (charAt17 >= 55296) {
            int i56 = charAt17 & 8191;
            int i57 = 13;
            while (true) {
                i42 = i55 + 1;
                charAt16 = str2.charAt(i55);
                if (charAt16 < 55296) {
                    break;
                }
                i56 |= (charAt16 & 8191) << i57;
                i57 += 13;
                i55 = i42;
            }
            charAt17 = i56 | (charAt16 << i57);
            i55 = i42;
        }
        if (charAt17 == 0) {
            iArr3 = iArr4;
            i18 = 0;
            i17 = 0;
            charAt = 0;
            charAt2 = 0;
            charAt3 = 0;
            i16 = 0;
        } else {
            int i58 = i55 + 1;
            int charAt18 = str2.charAt(i55);
            if (charAt18 >= 55296) {
                int i59 = charAt18 & 8191;
                int i60 = 13;
                while (true) {
                    i26 = i58 + 1;
                    charAt11 = str2.charAt(i58);
                    if (charAt11 < 55296) {
                        break;
                    }
                    i59 |= (charAt11 & 8191) << i60;
                    i60 += 13;
                    i58 = i26;
                }
                charAt18 = i59 | (charAt11 << i60);
                i58 = i26;
            }
            int i61 = i58 + 1;
            int charAt19 = str2.charAt(i58);
            if (charAt19 >= 55296) {
                int i62 = charAt19 & 8191;
                int i63 = 13;
                while (true) {
                    i25 = i61 + 1;
                    charAt10 = str2.charAt(i61);
                    if (charAt10 < 55296) {
                        break;
                    }
                    i62 |= (charAt10 & 8191) << i63;
                    i63 += 13;
                    i61 = i25;
                }
                charAt19 = i62 | (charAt10 << i63);
                i61 = i25;
            }
            int i64 = i61 + 1;
            int charAt20 = str2.charAt(i61);
            if (charAt20 >= 55296) {
                int i65 = charAt20 & 8191;
                int i66 = i64;
                int i67 = 13;
                while (true) {
                    i24 = i66 + 1;
                    charAt9 = str2.charAt(i66);
                    if (charAt9 < 55296) {
                        break;
                    }
                    i65 |= (charAt9 & 8191) << i67;
                    i67 += 13;
                    i66 = i24;
                }
                charAt20 = i65 | (charAt9 << i67);
                i11 = i24;
            } else {
                i11 = i64;
            }
            int i68 = i11 + 1;
            int charAt21 = str2.charAt(i11);
            if (charAt21 >= 55296) {
                int i69 = charAt21 & 8191;
                int i70 = i68;
                int i71 = 13;
                while (true) {
                    i23 = i70 + 1;
                    charAt8 = str2.charAt(i70);
                    if (charAt8 < 55296) {
                        break;
                    }
                    i69 |= (charAt8 & 8191) << i71;
                    i71 += 13;
                    i70 = i23;
                }
                charAt21 = i69 | (charAt8 << i71);
                i12 = i23;
            } else {
                i12 = i68;
            }
            int i72 = i12 + 1;
            charAt = str2.charAt(i12);
            if (charAt >= 55296) {
                int i73 = charAt & 8191;
                int i74 = i72;
                int i75 = 13;
                while (true) {
                    i22 = i74 + 1;
                    charAt7 = str2.charAt(i74);
                    if (charAt7 < 55296) {
                        break;
                    }
                    i73 |= (charAt7 & 8191) << i75;
                    i75 += 13;
                    i74 = i22;
                }
                charAt = i73 | (charAt7 << i75);
                i13 = i22;
            } else {
                i13 = i72;
            }
            int i76 = i13 + 1;
            charAt2 = str2.charAt(i13);
            if (charAt2 >= 55296) {
                int i77 = charAt2 & 8191;
                int i78 = i76;
                int i79 = 13;
                while (true) {
                    i21 = i78 + 1;
                    charAt6 = str2.charAt(i78);
                    if (charAt6 < 55296) {
                        break;
                    }
                    i77 |= (charAt6 & 8191) << i79;
                    i79 += 13;
                    i78 = i21;
                }
                charAt2 = i77 | (charAt6 << i79);
                i14 = i21;
            } else {
                i14 = i76;
            }
            int i80 = i14 + 1;
            int charAt22 = str2.charAt(i14);
            if (charAt22 >= 55296) {
                int i81 = charAt22 & 8191;
                int i82 = i80;
                int i83 = 13;
                while (true) {
                    i20 = i82 + 1;
                    charAt5 = str2.charAt(i82);
                    if (charAt5 < 55296) {
                        break;
                    }
                    i81 |= (charAt5 & 8191) << i83;
                    i83 += 13;
                    i82 = i20;
                }
                charAt22 = i81 | (charAt5 << i83);
                i15 = i20;
            } else {
                i15 = i80;
            }
            int i84 = i15 + 1;
            charAt3 = str2.charAt(i15);
            if (charAt3 >= 55296) {
                int i85 = charAt3 & 8191;
                int i86 = i84;
                int i87 = 13;
                while (true) {
                    i19 = i86 + 1;
                    charAt4 = str2.charAt(i86);
                    if (charAt4 < 55296) {
                        break;
                    }
                    i85 |= (charAt4 & 8191) << i87;
                    i87 += 13;
                    i86 = i19;
                }
                charAt3 = i85 | (charAt4 << i87);
                i84 = i19;
            }
            iArr3 = new int[charAt3 + charAt2 + charAt22];
            int i88 = charAt19 + (charAt18 * 2);
            i43 = charAt18;
            i55 = i84;
            i16 = charAt21;
            i17 = i88;
            i18 = charAt20;
        }
        Class<?> cls = rawMessageInfo.defaultInstance.getClass();
        int[] iArr16 = new int[charAt * 3];
        Object[] objArr2 = new Object[charAt * 2];
        int i89 = charAt2 + charAt3;
        int i90 = i89;
        int i91 = charAt3;
        int i92 = 0;
        int i93 = 0;
        while (i55 < length2) {
            int i94 = i55 + 1;
            int charAt23 = str2.charAt(i55);
            if (charAt23 >= c) {
                int i95 = charAt23 & 8191;
                int i96 = i94;
                int i97 = 13;
                while (true) {
                    i41 = i96 + 1;
                    charAt15 = str2.charAt(i96);
                    if (charAt15 < c) {
                        break;
                    }
                    i95 |= (charAt15 & 8191) << i97;
                    i97 += 13;
                    i96 = i41;
                }
                charAt23 = i95 | (charAt15 << i97);
                i27 = i41;
            } else {
                i27 = i94;
            }
            int i98 = i27 + 1;
            int charAt24 = str2.charAt(i27);
            if (charAt24 >= c) {
                int i99 = charAt24 & 8191;
                int i100 = i98;
                int i101 = 13;
                while (true) {
                    i40 = i100 + 1;
                    charAt14 = str2.charAt(i100);
                    if (charAt14 < c) {
                        break;
                    }
                    i99 |= (charAt14 & 8191) << i101;
                    i101 += 13;
                    i100 = i40;
                }
                charAt24 = i99 | (charAt14 << i101);
                i28 = i40;
            } else {
                i28 = i98;
            }
            int i102 = charAt24 & 255;
            int i103 = length2;
            if ((charAt24 & 1024) != 0) {
                iArr3[i93] = i92;
                i93++;
            }
            Unsafe unsafe = UNSAFE;
            int i104 = i89;
            Object[] objArr3 = rawMessageInfo.objects;
            int i105 = charAt3;
            if (i102 >= 51) {
                int i106 = i28 + 1;
                int charAt25 = str2.charAt(i28);
                if (charAt25 >= 55296) {
                    int i107 = charAt25 & 8191;
                    int i108 = i106;
                    int i109 = 13;
                    while (true) {
                        i39 = i108 + 1;
                        charAt13 = str2.charAt(i108);
                        i29 = i18;
                        if (charAt13 < 55296) {
                            break;
                        }
                        i107 |= (charAt13 & 8191) << i109;
                        i109 += 13;
                        i108 = i39;
                        i18 = i29;
                    }
                    charAt25 = i107 | (charAt13 << i109);
                    i37 = i39;
                } else {
                    i29 = i18;
                    i37 = i106;
                }
                int i110 = i102 - 51;
                int i111 = i37;
                if (i110 == 9 || i110 == 17) {
                    i38 = 2;
                    objArr2[((i92 / 3) * 2) + 1] = objArr3[i17];
                    i17++;
                } else {
                    if (i110 == 12 && !z3) {
                        objArr2[((i92 / 3) * 2) + 1] = objArr3[i17];
                        i17++;
                    }
                    i38 = 2;
                }
                int i112 = charAt25 * i38;
                Object obj2 = objArr3[i112];
                if (obj2 instanceof Field) {
                    reflectField2 = (Field) obj2;
                } else {
                    reflectField2 = reflectField(cls, (String) obj2);
                    objArr3[i112] = reflectField2;
                }
                int i113 = i17;
                int objectFieldOffset5 = (int) unsafe.objectFieldOffset(reflectField2);
                int i114 = i112 + 1;
                Object obj3 = objArr3[i114];
                if (obj3 instanceof Field) {
                    reflectField3 = (Field) obj3;
                } else {
                    reflectField3 = reflectField(cls, (String) obj3);
                    objArr3[i114] = reflectField3;
                }
                str = str2;
                i32 = (int) unsafe.objectFieldOffset(reflectField3);
                i17 = i113;
                i30 = i43;
                objectFieldOffset2 = objectFieldOffset5;
                i28 = i111;
                i34 = 0;
            } else {
                i29 = i18;
                int i115 = i17 + 1;
                Field reflectField4 = reflectField(cls, (String) objArr3[i17]);
                if (i102 == 9 || i102 == 17) {
                    objArr2[((i92 / 3) * 2) + 1] = reflectField4.getType();
                } else {
                    if (i102 == 27 || i102 == 49) {
                        i36 = i115 + 1;
                        objArr2[((i92 / 3) * 2) + 1] = objArr3[i115];
                    } else if (i102 == 12 || i102 == 30 || i102 == 44) {
                        if (!z3) {
                            i36 = i115 + 1;
                            objArr2[((i92 / 3) * 2) + 1] = objArr3[i115];
                        }
                    } else if (i102 == 50) {
                        int i116 = i91 + 1;
                        iArr3[i91] = i92;
                        int i117 = (i92 / 3) * 2;
                        int i118 = i115 + 1;
                        objArr2[i117] = objArr3[i115];
                        if ((charAt24 & 2048) != 0) {
                            i115 = i118 + 1;
                            objArr2[i117 + 1] = objArr3[i118];
                        } else {
                            i115 = i118;
                        }
                        i91 = i116;
                    }
                    i115 = i36;
                }
                int i119 = i115;
                objectFieldOffset2 = (int) unsafe.objectFieldOffset(reflectField4);
                if (!((charAt24 & 4096) == 4096) || i102 > 17) {
                    str = str2;
                    i30 = i43;
                    i31 = 0;
                    i32 = 1048575;
                    i33 = 18;
                } else {
                    int i120 = i28 + 1;
                    int charAt26 = str2.charAt(i28);
                    if (charAt26 >= 55296) {
                        int i121 = charAt26 & 8191;
                        int i122 = 13;
                        while (true) {
                            i35 = i120 + 1;
                            charAt12 = str2.charAt(i120);
                            if (charAt12 < 55296) {
                                break;
                            }
                            i121 |= (charAt12 & 8191) << i122;
                            i122 += 13;
                            i120 = i35;
                        }
                        charAt26 = i121 | (charAt12 << i122);
                        i120 = i35;
                    }
                    int i123 = (charAt26 / 32) + (i43 * 2);
                    Object obj4 = objArr3[i123];
                    str = str2;
                    if (obj4 instanceof Field) {
                        reflectField = (Field) obj4;
                    } else {
                        reflectField = reflectField(cls, (String) obj4);
                        objArr3[i123] = reflectField;
                    }
                    i30 = i43;
                    i32 = (int) unsafe.objectFieldOffset(reflectField);
                    int i124 = charAt26 % 32;
                    i33 = 18;
                    i28 = i120;
                    i31 = i124;
                }
                if (i102 >= i33 && i102 <= 49) {
                    iArr3[i90] = objectFieldOffset2;
                    i90++;
                }
                i34 = i31;
                i17 = i119;
            }
            int i125 = i92 + 1;
            iArr16[i92] = charAt23;
            int i126 = i125 + 1;
            iArr16[i125] = objectFieldOffset2 | ((charAt24 & 256) != 0 ? QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE : 0) | ((charAt24 & 512) != 0 ? QuickStepContract.SYSUI_STATE_KNOX_HARD_KEY_INTENT : 0) | (i102 << 20);
            i92 = i126 + 1;
            iArr16[i126] = (i34 << 20) | i32;
            i43 = i30;
            i55 = i28;
            length2 = i103;
            str2 = str;
            i89 = i104;
            charAt3 = i105;
            i18 = i29;
            c = 55296;
        }
        return new MessageSchema(iArr16, objArr2, i18, i16, rawMessageInfo.defaultInstance, z3, false, iArr3, charAt3, i89, newInstanceSchema, listFieldSchema, unknownFieldSchema, extensionSchema, mapFieldSchema);
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
            StringBuilder m4m = ActivityResultRegistry$$ExternalSyntheticOutline0.m4m("Field ", str, " for ");
            m4m.append(cls.getName());
            m4m.append(" not found. Known fields are ");
            m4m.append(Arrays.toString(declaredFields));
            throw new RuntimeException(m4m.toString());
        }
    }

    public static void writeString(int i, Object obj, CodedOutputStreamWriter codedOutputStreamWriter) {
        if (!(obj instanceof String)) {
            codedOutputStreamWriter.writeBytes(i, (ByteString) obj);
        } else {
            codedOutputStreamWriter.output.writeString(i, (String) obj);
        }
    }

    public final boolean arePresentForEquals(int i, Object obj, Object obj2) {
        return isFieldPresent(i, obj) == isFieldPresent(i, obj2);
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x003d, code lost:
    
        if (com.google.protobuf.SchemaUtil.safeEquals(com.google.protobuf.UnsafeUtil.getObject(r7, r11), com.google.protobuf.UnsafeUtil.getObject(r7, r12)) != false) goto L109;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x006f, code lost:
    
        if (com.google.protobuf.SchemaUtil.safeEquals(com.google.protobuf.UnsafeUtil.getObject(r7, r11), com.google.protobuf.UnsafeUtil.getObject(r7, r12)) != false) goto L109;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0083, code lost:
    
        if (com.google.protobuf.UnsafeUtil.getLong(r7, r11) == com.google.protobuf.UnsafeUtil.getLong(r7, r12)) goto L109;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0095, code lost:
    
        if (com.google.protobuf.UnsafeUtil.getInt(r7, r11) == com.google.protobuf.UnsafeUtil.getInt(r7, r12)) goto L109;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x00a9, code lost:
    
        if (com.google.protobuf.UnsafeUtil.getLong(r7, r11) == com.google.protobuf.UnsafeUtil.getLong(r7, r12)) goto L109;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x00bb, code lost:
    
        if (com.google.protobuf.UnsafeUtil.getInt(r7, r11) == com.google.protobuf.UnsafeUtil.getInt(r7, r12)) goto L109;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x00cd, code lost:
    
        if (com.google.protobuf.UnsafeUtil.getInt(r7, r11) == com.google.protobuf.UnsafeUtil.getInt(r7, r12)) goto L109;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x00df, code lost:
    
        if (com.google.protobuf.UnsafeUtil.getInt(r7, r11) == com.google.protobuf.UnsafeUtil.getInt(r7, r12)) goto L109;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x00f5, code lost:
    
        if (com.google.protobuf.SchemaUtil.safeEquals(com.google.protobuf.UnsafeUtil.getObject(r7, r11), com.google.protobuf.UnsafeUtil.getObject(r7, r12)) != false) goto L109;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x010b, code lost:
    
        if (com.google.protobuf.SchemaUtil.safeEquals(com.google.protobuf.UnsafeUtil.getObject(r7, r11), com.google.protobuf.UnsafeUtil.getObject(r7, r12)) != false) goto L109;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x0121, code lost:
    
        if (com.google.protobuf.SchemaUtil.safeEquals(com.google.protobuf.UnsafeUtil.getObject(r7, r11), com.google.protobuf.UnsafeUtil.getObject(r7, r12)) != false) goto L109;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x0133, code lost:
    
        if (com.google.protobuf.UnsafeUtil.getBoolean(r7, r11) == com.google.protobuf.UnsafeUtil.getBoolean(r7, r12)) goto L109;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x0145, code lost:
    
        if (com.google.protobuf.UnsafeUtil.getInt(r7, r11) == com.google.protobuf.UnsafeUtil.getInt(r7, r12)) goto L109;
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x0159, code lost:
    
        if (com.google.protobuf.UnsafeUtil.getLong(r7, r11) == com.google.protobuf.UnsafeUtil.getLong(r7, r12)) goto L109;
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x016b, code lost:
    
        if (com.google.protobuf.UnsafeUtil.getInt(r7, r11) == com.google.protobuf.UnsafeUtil.getInt(r7, r12)) goto L109;
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x017e, code lost:
    
        if (com.google.protobuf.UnsafeUtil.getLong(r7, r11) == com.google.protobuf.UnsafeUtil.getLong(r7, r12)) goto L109;
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x0191, code lost:
    
        if (com.google.protobuf.UnsafeUtil.getLong(r7, r11) == com.google.protobuf.UnsafeUtil.getLong(r7, r12)) goto L109;
     */
    /* JADX WARN: Code restructure failed: missing block: B:83:0x01aa, code lost:
    
        if (java.lang.Float.floatToIntBits(com.google.protobuf.UnsafeUtil.getFloat(r7, r11)) == java.lang.Float.floatToIntBits(com.google.protobuf.UnsafeUtil.getFloat(r7, r12))) goto L109;
     */
    /* JADX WARN: Code restructure failed: missing block: B:87:0x01c5, code lost:
    
        if (java.lang.Double.doubleToLongBits(com.google.protobuf.UnsafeUtil.getDouble(r7, r11)) == java.lang.Double.doubleToLongBits(com.google.protobuf.UnsafeUtil.getDouble(r7, r12))) goto L109;
     */
    @Override // com.google.protobuf.Schema
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean equals(Object obj, Object obj2) {
        int[] iArr = this.buffer;
        int length = iArr.length;
        int i = 0;
        while (true) {
            boolean z = true;
            if (i >= length) {
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
            int typeAndOffsetAt = typeAndOffsetAt(i);
            long j = typeAndOffsetAt & 1048575;
            switch ((typeAndOffsetAt & 267386880) >>> 20) {
                case 0:
                    if (arePresentForEquals(i, obj, obj2)) {
                        break;
                    }
                    z = false;
                    break;
                case 1:
                    if (arePresentForEquals(i, obj, obj2)) {
                        break;
                    }
                    z = false;
                    break;
                case 2:
                    if (arePresentForEquals(i, obj, obj2)) {
                        break;
                    }
                    z = false;
                    break;
                case 3:
                    if (arePresentForEquals(i, obj, obj2)) {
                        break;
                    }
                    z = false;
                    break;
                case 4:
                    if (arePresentForEquals(i, obj, obj2)) {
                        break;
                    }
                    z = false;
                    break;
                case 5:
                    if (arePresentForEquals(i, obj, obj2)) {
                        break;
                    }
                    z = false;
                    break;
                case 6:
                    if (arePresentForEquals(i, obj, obj2)) {
                        break;
                    }
                    z = false;
                    break;
                case 7:
                    if (arePresentForEquals(i, obj, obj2)) {
                        break;
                    }
                    z = false;
                    break;
                case 8:
                    if (arePresentForEquals(i, obj, obj2)) {
                        break;
                    }
                    z = false;
                    break;
                case 9:
                    if (arePresentForEquals(i, obj, obj2)) {
                        break;
                    }
                    z = false;
                    break;
                case 10:
                    if (arePresentForEquals(i, obj, obj2)) {
                        break;
                    }
                    z = false;
                    break;
                case 11:
                    if (arePresentForEquals(i, obj, obj2)) {
                        break;
                    }
                    z = false;
                    break;
                case 12:
                    if (arePresentForEquals(i, obj, obj2)) {
                        break;
                    }
                    z = false;
                    break;
                case 13:
                    if (arePresentForEquals(i, obj, obj2)) {
                        break;
                    }
                    z = false;
                    break;
                case 14:
                    if (arePresentForEquals(i, obj, obj2)) {
                        break;
                    }
                    z = false;
                    break;
                case 15:
                    if (arePresentForEquals(i, obj, obj2)) {
                        break;
                    }
                    z = false;
                    break;
                case 16:
                    if (arePresentForEquals(i, obj, obj2)) {
                        break;
                    }
                    z = false;
                    break;
                case 17:
                    if (arePresentForEquals(i, obj, obj2)) {
                        break;
                    }
                    z = false;
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
                    z = SchemaUtil.safeEquals(UnsafeUtil.getObject(j, obj), UnsafeUtil.getObject(j, obj2));
                    break;
                case 50:
                    z = SchemaUtil.safeEquals(UnsafeUtil.getObject(j, obj), UnsafeUtil.getObject(j, obj2));
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
                    if (UnsafeUtil.getInt(j2, obj) == UnsafeUtil.getInt(j2, obj2)) {
                        break;
                    }
                    z = false;
                    break;
            }
            if (!z) {
                return false;
            }
            i += 3;
        }
    }

    public final Object filterMapUnknownEnumValues(Object obj, int i, Object obj2, UnknownFieldSchema unknownFieldSchema, Object obj3) {
        int i2 = this.buffer[i];
        Object object = UnsafeUtil.getObject(typeAndOffsetAt(i) & 1048575, obj);
        if (object == null) {
            return obj2;
        }
        Internal.EnumVerifier enumFieldVerifier = getEnumFieldVerifier(i);
        if (enumFieldVerifier == null) {
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
    /* JADX WARN: Failed to find 'out' block for switch in B:303:0x05d2. Please report as an issue. */
    @Override // com.google.protobuf.Schema
    public final int getSerializedSize(Object obj) {
        int i;
        int i2;
        int i3;
        int i4;
        boolean z;
        int computeTagSize;
        int computeTagSize2;
        int computeUInt64SizeNoTag;
        int computeBytesSize;
        int computeTagSize3;
        int computeInt32SizeNoTag;
        int computeSizeFixed32List;
        int computeSizeFixed64ListNoTag;
        int computeTagSize4;
        int computeUInt32SizeNoTag;
        int computeTagSize5;
        int computeTagSize6;
        int computeUInt64SizeNoTag2;
        int computeTagSize7;
        int computeInt32SizeNoTag2;
        int computeTagSize8;
        int computeTagSize9;
        int computeTagSize10;
        int computeUInt64SizeNoTag3;
        int computeTagSize11;
        int computeInt32SizeNoTag3;
        int computeFixed64Size;
        int computeTagSize12;
        int computeTagSize13;
        int computeStringSizeNoTag;
        int computeBytesSize2;
        int computeSizeFixed64ListNoTag2;
        int computeTagSize14;
        int computeUInt32SizeNoTag2;
        int i5 = 267386880;
        boolean z2 = this.proto3;
        MapFieldSchema mapFieldSchema = this.mapFieldSchema;
        Unsafe unsafe = UNSAFE;
        boolean z3 = this.useCachedSizeField;
        UnknownFieldSchema unknownFieldSchema = this.unknownFieldSchema;
        int[] iArr = this.buffer;
        int i6 = 1048575;
        if (z2) {
            int i7 = 0;
            int i8 = 0;
            while (i7 < iArr.length) {
                int typeAndOffsetAt = typeAndOffsetAt(i7);
                int i9 = (typeAndOffsetAt & i5) >>> 20;
                int i10 = iArr[i7];
                long j = typeAndOffsetAt & i6;
                int i11 = (i9 < FieldType.DOUBLE_LIST_PACKED.m240id() || i9 > FieldType.SINT64_LIST_PACKED.m240id()) ? 0 : iArr[i7 + 2] & i6;
                switch (i9) {
                    case 0:
                        if (isFieldPresent(i7, obj)) {
                            computeTagSize8 = CodedOutputStream.computeTagSize(i10);
                            computeFixed64Size = computeTagSize8 + 8;
                            i8 += computeFixed64Size;
                            break;
                        } else {
                            break;
                        }
                    case 1:
                        if (isFieldPresent(i7, obj)) {
                            computeTagSize9 = CodedOutputStream.computeTagSize(i10);
                            computeFixed64Size = computeTagSize9 + 4;
                            i8 += computeFixed64Size;
                            break;
                        } else {
                            break;
                        }
                    case 2:
                        if (isFieldPresent(i7, obj)) {
                            long j2 = UnsafeUtil.getLong(j, obj);
                            computeTagSize10 = CodedOutputStream.computeTagSize(i10);
                            computeUInt64SizeNoTag3 = CodedOutputStream.computeUInt64SizeNoTag(j2);
                            computeFixed64Size = computeUInt64SizeNoTag3 + computeTagSize10;
                            i8 += computeFixed64Size;
                            break;
                        } else {
                            break;
                        }
                    case 3:
                        if (isFieldPresent(i7, obj)) {
                            long j3 = UnsafeUtil.getLong(j, obj);
                            computeTagSize10 = CodedOutputStream.computeTagSize(i10);
                            computeUInt64SizeNoTag3 = CodedOutputStream.computeUInt64SizeNoTag(j3);
                            computeFixed64Size = computeUInt64SizeNoTag3 + computeTagSize10;
                            i8 += computeFixed64Size;
                            break;
                        } else {
                            break;
                        }
                    case 4:
                        if (isFieldPresent(i7, obj)) {
                            int i12 = UnsafeUtil.getInt(j, obj);
                            computeTagSize11 = CodedOutputStream.computeTagSize(i10);
                            computeInt32SizeNoTag3 = CodedOutputStream.computeInt32SizeNoTag(i12);
                            computeFixed64Size = computeInt32SizeNoTag3 + computeTagSize11;
                            i8 += computeFixed64Size;
                            break;
                        } else {
                            break;
                        }
                    case 5:
                        if (isFieldPresent(i7, obj)) {
                            computeFixed64Size = CodedOutputStream.computeFixed64Size(i10);
                            i8 += computeFixed64Size;
                            break;
                        } else {
                            break;
                        }
                    case 6:
                        if (isFieldPresent(i7, obj)) {
                            computeFixed64Size = CodedOutputStream.computeFixed32Size(i10);
                            i8 += computeFixed64Size;
                            break;
                        } else {
                            break;
                        }
                    case 7:
                        if (isFieldPresent(i7, obj)) {
                            computeTagSize12 = CodedOutputStream.computeTagSize(i10);
                            computeFixed64Size = computeTagSize12 + 1;
                            i8 += computeFixed64Size;
                            break;
                        } else {
                            break;
                        }
                    case 8:
                        if (!isFieldPresent(i7, obj)) {
                            break;
                        } else {
                            Object object = UnsafeUtil.getObject(j, obj);
                            if (object instanceof ByteString) {
                                computeBytesSize2 = CodedOutputStream.computeBytesSize(i10, (ByteString) object);
                                i8 += computeBytesSize2;
                                break;
                            } else {
                                computeTagSize13 = CodedOutputStream.computeTagSize(i10);
                                computeStringSizeNoTag = CodedOutputStream.computeStringSizeNoTag((String) object);
                                computeBytesSize2 = computeStringSizeNoTag + computeTagSize13;
                                i8 += computeBytesSize2;
                            }
                        }
                    case 9:
                        if (isFieldPresent(i7, obj)) {
                            computeFixed64Size = SchemaUtil.computeSizeMessage(i10, getMessageFieldSchema(i7), UnsafeUtil.getObject(j, obj));
                            i8 += computeFixed64Size;
                            break;
                        } else {
                            break;
                        }
                    case 10:
                        if (isFieldPresent(i7, obj)) {
                            computeFixed64Size = CodedOutputStream.computeBytesSize(i10, (ByteString) UnsafeUtil.getObject(j, obj));
                            i8 += computeFixed64Size;
                            break;
                        } else {
                            break;
                        }
                    case 11:
                        if (isFieldPresent(i7, obj)) {
                            computeFixed64Size = CodedOutputStream.computeUInt32Size(i10, UnsafeUtil.getInt(j, obj));
                            i8 += computeFixed64Size;
                            break;
                        } else {
                            break;
                        }
                    case 12:
                        if (isFieldPresent(i7, obj)) {
                            int i13 = UnsafeUtil.getInt(j, obj);
                            computeTagSize11 = CodedOutputStream.computeTagSize(i10);
                            computeInt32SizeNoTag3 = CodedOutputStream.computeInt32SizeNoTag(i13);
                            computeFixed64Size = computeInt32SizeNoTag3 + computeTagSize11;
                            i8 += computeFixed64Size;
                            break;
                        } else {
                            break;
                        }
                    case 13:
                        if (isFieldPresent(i7, obj)) {
                            computeTagSize9 = CodedOutputStream.computeTagSize(i10);
                            computeFixed64Size = computeTagSize9 + 4;
                            i8 += computeFixed64Size;
                            break;
                        } else {
                            break;
                        }
                    case 14:
                        if (isFieldPresent(i7, obj)) {
                            computeTagSize8 = CodedOutputStream.computeTagSize(i10);
                            computeFixed64Size = computeTagSize8 + 8;
                            i8 += computeFixed64Size;
                            break;
                        } else {
                            break;
                        }
                    case 15:
                        if (isFieldPresent(i7, obj)) {
                            int i14 = UnsafeUtil.getInt(j, obj);
                            computeTagSize11 = CodedOutputStream.computeTagSize(i10);
                            computeInt32SizeNoTag3 = CodedOutputStream.computeUInt32SizeNoTag((i14 >> 31) ^ (i14 << 1));
                            computeFixed64Size = computeInt32SizeNoTag3 + computeTagSize11;
                            i8 += computeFixed64Size;
                            break;
                        } else {
                            break;
                        }
                    case 16:
                        if (isFieldPresent(i7, obj)) {
                            long j4 = UnsafeUtil.getLong(j, obj);
                            computeTagSize10 = CodedOutputStream.computeTagSize(i10);
                            computeUInt64SizeNoTag3 = CodedOutputStream.computeUInt64SizeNoTag((j4 >> 63) ^ (j4 << 1));
                            computeFixed64Size = computeUInt64SizeNoTag3 + computeTagSize10;
                            i8 += computeFixed64Size;
                            break;
                        } else {
                            break;
                        }
                    case 17:
                        if (isFieldPresent(i7, obj)) {
                            computeFixed64Size = CodedOutputStream.computeGroupSize(i10, (MessageLite) UnsafeUtil.getObject(j, obj), getMessageFieldSchema(i7));
                            i8 += computeFixed64Size;
                            break;
                        } else {
                            break;
                        }
                    case 18:
                        computeFixed64Size = SchemaUtil.computeSizeFixed64List(listAt(j, obj), i10);
                        i8 += computeFixed64Size;
                        break;
                    case 19:
                        computeFixed64Size = SchemaUtil.computeSizeFixed32List(listAt(j, obj), i10);
                        i8 += computeFixed64Size;
                        break;
                    case 20:
                        computeFixed64Size = SchemaUtil.computeSizeInt64List(listAt(j, obj), i10);
                        i8 += computeFixed64Size;
                        break;
                    case 21:
                        computeFixed64Size = SchemaUtil.computeSizeUInt64List(listAt(j, obj), i10);
                        i8 += computeFixed64Size;
                        break;
                    case 22:
                        computeFixed64Size = SchemaUtil.computeSizeInt32List(listAt(j, obj), i10);
                        i8 += computeFixed64Size;
                        break;
                    case 23:
                        computeFixed64Size = SchemaUtil.computeSizeFixed64List(listAt(j, obj), i10);
                        i8 += computeFixed64Size;
                        break;
                    case 24:
                        computeFixed64Size = SchemaUtil.computeSizeFixed32List(listAt(j, obj), i10);
                        i8 += computeFixed64Size;
                        break;
                    case 25:
                        List listAt = listAt(j, obj);
                        Class cls = SchemaUtil.GENERATED_MESSAGE_CLASS;
                        int size = listAt.size();
                        if (size != 0) {
                            computeFixed64Size = (CodedOutputStream.computeTagSize(i10) + 1) * size;
                            i8 += computeFixed64Size;
                            break;
                        }
                        computeFixed64Size = 0;
                        i8 += computeFixed64Size;
                    case 26:
                        computeFixed64Size = SchemaUtil.computeSizeStringList(i10, listAt(j, obj));
                        i8 += computeFixed64Size;
                        break;
                    case 27:
                        computeFixed64Size = SchemaUtil.computeSizeMessageList(i10, listAt(j, obj), getMessageFieldSchema(i7));
                        i8 += computeFixed64Size;
                        break;
                    case 28:
                        computeFixed64Size = SchemaUtil.computeSizeByteStringList(i10, listAt(j, obj));
                        i8 += computeFixed64Size;
                        break;
                    case 29:
                        computeFixed64Size = SchemaUtil.computeSizeUInt32List(listAt(j, obj), i10);
                        i8 += computeFixed64Size;
                        break;
                    case 30:
                        computeFixed64Size = SchemaUtil.computeSizeEnumList(listAt(j, obj), i10);
                        i8 += computeFixed64Size;
                        break;
                    case 31:
                        computeFixed64Size = SchemaUtil.computeSizeFixed32List(listAt(j, obj), i10);
                        i8 += computeFixed64Size;
                        break;
                    case 32:
                        computeFixed64Size = SchemaUtil.computeSizeFixed64List(listAt(j, obj), i10);
                        i8 += computeFixed64Size;
                        break;
                    case 33:
                        computeFixed64Size = SchemaUtil.computeSizeSInt32List(listAt(j, obj), i10);
                        i8 += computeFixed64Size;
                        break;
                    case 34:
                        computeFixed64Size = SchemaUtil.computeSizeSInt64List(listAt(j, obj), i10);
                        i8 += computeFixed64Size;
                        break;
                    case 35:
                        computeSizeFixed64ListNoTag2 = SchemaUtil.computeSizeFixed64ListNoTag((List) unsafe.getObject(obj, j));
                        if (computeSizeFixed64ListNoTag2 <= 0) {
                            break;
                        } else {
                            if (z3) {
                                unsafe.putInt(obj, i11, computeSizeFixed64ListNoTag2);
                            }
                            computeTagSize14 = CodedOutputStream.computeTagSize(i10);
                            computeUInt32SizeNoTag2 = CodedOutputStream.computeUInt32SizeNoTag(computeSizeFixed64ListNoTag2);
                            i8 += computeUInt32SizeNoTag2 + computeTagSize14 + computeSizeFixed64ListNoTag2;
                            break;
                        }
                    case 36:
                        computeSizeFixed64ListNoTag2 = SchemaUtil.computeSizeFixed32ListNoTag((List) unsafe.getObject(obj, j));
                        if (computeSizeFixed64ListNoTag2 <= 0) {
                            break;
                        } else {
                            if (z3) {
                                unsafe.putInt(obj, i11, computeSizeFixed64ListNoTag2);
                            }
                            computeTagSize14 = CodedOutputStream.computeTagSize(i10);
                            computeUInt32SizeNoTag2 = CodedOutputStream.computeUInt32SizeNoTag(computeSizeFixed64ListNoTag2);
                            i8 += computeUInt32SizeNoTag2 + computeTagSize14 + computeSizeFixed64ListNoTag2;
                            break;
                        }
                    case 37:
                        computeSizeFixed64ListNoTag2 = SchemaUtil.computeSizeInt64ListNoTag((List) unsafe.getObject(obj, j));
                        if (computeSizeFixed64ListNoTag2 <= 0) {
                            break;
                        } else {
                            if (z3) {
                                unsafe.putInt(obj, i11, computeSizeFixed64ListNoTag2);
                            }
                            computeTagSize14 = CodedOutputStream.computeTagSize(i10);
                            computeUInt32SizeNoTag2 = CodedOutputStream.computeUInt32SizeNoTag(computeSizeFixed64ListNoTag2);
                            i8 += computeUInt32SizeNoTag2 + computeTagSize14 + computeSizeFixed64ListNoTag2;
                            break;
                        }
                    case 38:
                        computeSizeFixed64ListNoTag2 = SchemaUtil.computeSizeUInt64ListNoTag((List) unsafe.getObject(obj, j));
                        if (computeSizeFixed64ListNoTag2 <= 0) {
                            break;
                        } else {
                            if (z3) {
                                unsafe.putInt(obj, i11, computeSizeFixed64ListNoTag2);
                            }
                            computeTagSize14 = CodedOutputStream.computeTagSize(i10);
                            computeUInt32SizeNoTag2 = CodedOutputStream.computeUInt32SizeNoTag(computeSizeFixed64ListNoTag2);
                            i8 += computeUInt32SizeNoTag2 + computeTagSize14 + computeSizeFixed64ListNoTag2;
                            break;
                        }
                    case 39:
                        computeSizeFixed64ListNoTag2 = SchemaUtil.computeSizeInt32ListNoTag((List) unsafe.getObject(obj, j));
                        if (computeSizeFixed64ListNoTag2 <= 0) {
                            break;
                        } else {
                            if (z3) {
                                unsafe.putInt(obj, i11, computeSizeFixed64ListNoTag2);
                            }
                            computeTagSize14 = CodedOutputStream.computeTagSize(i10);
                            computeUInt32SizeNoTag2 = CodedOutputStream.computeUInt32SizeNoTag(computeSizeFixed64ListNoTag2);
                            i8 += computeUInt32SizeNoTag2 + computeTagSize14 + computeSizeFixed64ListNoTag2;
                            break;
                        }
                    case 40:
                        computeSizeFixed64ListNoTag2 = SchemaUtil.computeSizeFixed64ListNoTag((List) unsafe.getObject(obj, j));
                        if (computeSizeFixed64ListNoTag2 <= 0) {
                            break;
                        } else {
                            if (z3) {
                                unsafe.putInt(obj, i11, computeSizeFixed64ListNoTag2);
                            }
                            computeTagSize14 = CodedOutputStream.computeTagSize(i10);
                            computeUInt32SizeNoTag2 = CodedOutputStream.computeUInt32SizeNoTag(computeSizeFixed64ListNoTag2);
                            i8 += computeUInt32SizeNoTag2 + computeTagSize14 + computeSizeFixed64ListNoTag2;
                            break;
                        }
                    case 41:
                        computeSizeFixed64ListNoTag2 = SchemaUtil.computeSizeFixed32ListNoTag((List) unsafe.getObject(obj, j));
                        if (computeSizeFixed64ListNoTag2 <= 0) {
                            break;
                        } else {
                            if (z3) {
                                unsafe.putInt(obj, i11, computeSizeFixed64ListNoTag2);
                            }
                            computeTagSize14 = CodedOutputStream.computeTagSize(i10);
                            computeUInt32SizeNoTag2 = CodedOutputStream.computeUInt32SizeNoTag(computeSizeFixed64ListNoTag2);
                            i8 += computeUInt32SizeNoTag2 + computeTagSize14 + computeSizeFixed64ListNoTag2;
                            break;
                        }
                    case 42:
                        List list = (List) unsafe.getObject(obj, j);
                        Class cls2 = SchemaUtil.GENERATED_MESSAGE_CLASS;
                        computeSizeFixed64ListNoTag2 = list.size();
                        if (computeSizeFixed64ListNoTag2 <= 0) {
                            break;
                        } else {
                            if (z3) {
                                unsafe.putInt(obj, i11, computeSizeFixed64ListNoTag2);
                            }
                            computeTagSize14 = CodedOutputStream.computeTagSize(i10);
                            computeUInt32SizeNoTag2 = CodedOutputStream.computeUInt32SizeNoTag(computeSizeFixed64ListNoTag2);
                            i8 += computeUInt32SizeNoTag2 + computeTagSize14 + computeSizeFixed64ListNoTag2;
                            break;
                        }
                    case 43:
                        computeSizeFixed64ListNoTag2 = SchemaUtil.computeSizeUInt32ListNoTag((List) unsafe.getObject(obj, j));
                        if (computeSizeFixed64ListNoTag2 <= 0) {
                            break;
                        } else {
                            if (z3) {
                                unsafe.putInt(obj, i11, computeSizeFixed64ListNoTag2);
                            }
                            computeTagSize14 = CodedOutputStream.computeTagSize(i10);
                            computeUInt32SizeNoTag2 = CodedOutputStream.computeUInt32SizeNoTag(computeSizeFixed64ListNoTag2);
                            i8 += computeUInt32SizeNoTag2 + computeTagSize14 + computeSizeFixed64ListNoTag2;
                            break;
                        }
                    case 44:
                        computeSizeFixed64ListNoTag2 = SchemaUtil.computeSizeEnumListNoTag((List) unsafe.getObject(obj, j));
                        if (computeSizeFixed64ListNoTag2 <= 0) {
                            break;
                        } else {
                            if (z3) {
                                unsafe.putInt(obj, i11, computeSizeFixed64ListNoTag2);
                            }
                            computeTagSize14 = CodedOutputStream.computeTagSize(i10);
                            computeUInt32SizeNoTag2 = CodedOutputStream.computeUInt32SizeNoTag(computeSizeFixed64ListNoTag2);
                            i8 += computeUInt32SizeNoTag2 + computeTagSize14 + computeSizeFixed64ListNoTag2;
                            break;
                        }
                    case 45:
                        computeSizeFixed64ListNoTag2 = SchemaUtil.computeSizeFixed32ListNoTag((List) unsafe.getObject(obj, j));
                        if (computeSizeFixed64ListNoTag2 <= 0) {
                            break;
                        } else {
                            if (z3) {
                                unsafe.putInt(obj, i11, computeSizeFixed64ListNoTag2);
                            }
                            computeTagSize14 = CodedOutputStream.computeTagSize(i10);
                            computeUInt32SizeNoTag2 = CodedOutputStream.computeUInt32SizeNoTag(computeSizeFixed64ListNoTag2);
                            i8 += computeUInt32SizeNoTag2 + computeTagSize14 + computeSizeFixed64ListNoTag2;
                            break;
                        }
                    case 46:
                        computeSizeFixed64ListNoTag2 = SchemaUtil.computeSizeFixed64ListNoTag((List) unsafe.getObject(obj, j));
                        if (computeSizeFixed64ListNoTag2 <= 0) {
                            break;
                        } else {
                            if (z3) {
                                unsafe.putInt(obj, i11, computeSizeFixed64ListNoTag2);
                            }
                            computeTagSize14 = CodedOutputStream.computeTagSize(i10);
                            computeUInt32SizeNoTag2 = CodedOutputStream.computeUInt32SizeNoTag(computeSizeFixed64ListNoTag2);
                            i8 += computeUInt32SizeNoTag2 + computeTagSize14 + computeSizeFixed64ListNoTag2;
                            break;
                        }
                    case 47:
                        computeSizeFixed64ListNoTag2 = SchemaUtil.computeSizeSInt32ListNoTag((List) unsafe.getObject(obj, j));
                        if (computeSizeFixed64ListNoTag2 <= 0) {
                            break;
                        } else {
                            if (z3) {
                                unsafe.putInt(obj, i11, computeSizeFixed64ListNoTag2);
                            }
                            computeTagSize14 = CodedOutputStream.computeTagSize(i10);
                            computeUInt32SizeNoTag2 = CodedOutputStream.computeUInt32SizeNoTag(computeSizeFixed64ListNoTag2);
                            i8 += computeUInt32SizeNoTag2 + computeTagSize14 + computeSizeFixed64ListNoTag2;
                            break;
                        }
                    case 48:
                        computeSizeFixed64ListNoTag2 = SchemaUtil.computeSizeSInt64ListNoTag((List) unsafe.getObject(obj, j));
                        if (computeSizeFixed64ListNoTag2 <= 0) {
                            break;
                        } else {
                            if (z3) {
                                unsafe.putInt(obj, i11, computeSizeFixed64ListNoTag2);
                            }
                            computeTagSize14 = CodedOutputStream.computeTagSize(i10);
                            computeUInt32SizeNoTag2 = CodedOutputStream.computeUInt32SizeNoTag(computeSizeFixed64ListNoTag2);
                            i8 += computeUInt32SizeNoTag2 + computeTagSize14 + computeSizeFixed64ListNoTag2;
                            break;
                        }
                    case 49:
                        List listAt2 = listAt(j, obj);
                        Schema messageFieldSchema = getMessageFieldSchema(i7);
                        Class cls3 = SchemaUtil.GENERATED_MESSAGE_CLASS;
                        int size2 = listAt2.size();
                        if (size2 != 0) {
                            int i15 = 0;
                            for (int i16 = 0; i16 < size2; i16++) {
                                i15 = CodedOutputStream.computeGroupSize(i10, (MessageLite) listAt2.get(i16), messageFieldSchema) + i15;
                            }
                            computeFixed64Size = i15;
                            i8 += computeFixed64Size;
                            break;
                        }
                        computeFixed64Size = 0;
                        i8 += computeFixed64Size;
                    case 50:
                        computeFixed64Size = ((MapFieldSchemaLite) mapFieldSchema).getSerializedSize(i10, UnsafeUtil.getObject(j, obj), getMapFieldDefaultEntry(i7));
                        i8 += computeFixed64Size;
                        break;
                    case 51:
                        if (isOneofPresent(i10, i7, obj)) {
                            computeTagSize8 = CodedOutputStream.computeTagSize(i10);
                            computeFixed64Size = computeTagSize8 + 8;
                            i8 += computeFixed64Size;
                            break;
                        } else {
                            break;
                        }
                    case 52:
                        if (isOneofPresent(i10, i7, obj)) {
                            computeTagSize9 = CodedOutputStream.computeTagSize(i10);
                            computeFixed64Size = computeTagSize9 + 4;
                            i8 += computeFixed64Size;
                            break;
                        } else {
                            break;
                        }
                    case 53:
                        if (isOneofPresent(i10, i7, obj)) {
                            long oneofLongAt = oneofLongAt(j, obj);
                            computeTagSize10 = CodedOutputStream.computeTagSize(i10);
                            computeUInt64SizeNoTag3 = CodedOutputStream.computeUInt64SizeNoTag(oneofLongAt);
                            computeFixed64Size = computeUInt64SizeNoTag3 + computeTagSize10;
                            i8 += computeFixed64Size;
                            break;
                        } else {
                            break;
                        }
                    case 54:
                        if (isOneofPresent(i10, i7, obj)) {
                            long oneofLongAt2 = oneofLongAt(j, obj);
                            computeTagSize10 = CodedOutputStream.computeTagSize(i10);
                            computeUInt64SizeNoTag3 = CodedOutputStream.computeUInt64SizeNoTag(oneofLongAt2);
                            computeFixed64Size = computeUInt64SizeNoTag3 + computeTagSize10;
                            i8 += computeFixed64Size;
                            break;
                        } else {
                            break;
                        }
                    case 55:
                        if (isOneofPresent(i10, i7, obj)) {
                            int oneofIntAt = oneofIntAt(j, obj);
                            computeTagSize11 = CodedOutputStream.computeTagSize(i10);
                            computeInt32SizeNoTag3 = CodedOutputStream.computeInt32SizeNoTag(oneofIntAt);
                            computeFixed64Size = computeInt32SizeNoTag3 + computeTagSize11;
                            i8 += computeFixed64Size;
                            break;
                        } else {
                            break;
                        }
                    case 56:
                        if (isOneofPresent(i10, i7, obj)) {
                            computeFixed64Size = CodedOutputStream.computeFixed64Size(i10);
                            i8 += computeFixed64Size;
                            break;
                        } else {
                            break;
                        }
                    case 57:
                        if (isOneofPresent(i10, i7, obj)) {
                            computeFixed64Size = CodedOutputStream.computeFixed32Size(i10);
                            i8 += computeFixed64Size;
                            break;
                        } else {
                            break;
                        }
                    case 58:
                        if (isOneofPresent(i10, i7, obj)) {
                            computeTagSize12 = CodedOutputStream.computeTagSize(i10);
                            computeFixed64Size = computeTagSize12 + 1;
                            i8 += computeFixed64Size;
                            break;
                        } else {
                            break;
                        }
                    case 59:
                        if (!isOneofPresent(i10, i7, obj)) {
                            break;
                        } else {
                            Object object2 = UnsafeUtil.getObject(j, obj);
                            if (object2 instanceof ByteString) {
                                computeBytesSize2 = CodedOutputStream.computeBytesSize(i10, (ByteString) object2);
                                i8 += computeBytesSize2;
                                break;
                            } else {
                                computeTagSize13 = CodedOutputStream.computeTagSize(i10);
                                computeStringSizeNoTag = CodedOutputStream.computeStringSizeNoTag((String) object2);
                                computeBytesSize2 = computeStringSizeNoTag + computeTagSize13;
                                i8 += computeBytesSize2;
                            }
                        }
                    case 60:
                        if (isOneofPresent(i10, i7, obj)) {
                            computeFixed64Size = SchemaUtil.computeSizeMessage(i10, getMessageFieldSchema(i7), UnsafeUtil.getObject(j, obj));
                            i8 += computeFixed64Size;
                            break;
                        } else {
                            break;
                        }
                    case 61:
                        if (isOneofPresent(i10, i7, obj)) {
                            computeFixed64Size = CodedOutputStream.computeBytesSize(i10, (ByteString) UnsafeUtil.getObject(j, obj));
                            i8 += computeFixed64Size;
                            break;
                        } else {
                            break;
                        }
                    case 62:
                        if (isOneofPresent(i10, i7, obj)) {
                            computeFixed64Size = CodedOutputStream.computeUInt32Size(i10, oneofIntAt(j, obj));
                            i8 += computeFixed64Size;
                            break;
                        } else {
                            break;
                        }
                    case 63:
                        if (isOneofPresent(i10, i7, obj)) {
                            int oneofIntAt2 = oneofIntAt(j, obj);
                            computeTagSize11 = CodedOutputStream.computeTagSize(i10);
                            computeInt32SizeNoTag3 = CodedOutputStream.computeInt32SizeNoTag(oneofIntAt2);
                            computeFixed64Size = computeInt32SizeNoTag3 + computeTagSize11;
                            i8 += computeFixed64Size;
                            break;
                        } else {
                            break;
                        }
                    case 64:
                        if (isOneofPresent(i10, i7, obj)) {
                            computeTagSize9 = CodedOutputStream.computeTagSize(i10);
                            computeFixed64Size = computeTagSize9 + 4;
                            i8 += computeFixed64Size;
                            break;
                        } else {
                            break;
                        }
                    case 65:
                        if (isOneofPresent(i10, i7, obj)) {
                            computeTagSize8 = CodedOutputStream.computeTagSize(i10);
                            computeFixed64Size = computeTagSize8 + 8;
                            i8 += computeFixed64Size;
                            break;
                        } else {
                            break;
                        }
                    case 66:
                        if (isOneofPresent(i10, i7, obj)) {
                            int oneofIntAt3 = oneofIntAt(j, obj);
                            computeTagSize11 = CodedOutputStream.computeTagSize(i10);
                            computeInt32SizeNoTag3 = CodedOutputStream.computeUInt32SizeNoTag((oneofIntAt3 >> 31) ^ (oneofIntAt3 << 1));
                            computeFixed64Size = computeInt32SizeNoTag3 + computeTagSize11;
                            i8 += computeFixed64Size;
                            break;
                        } else {
                            break;
                        }
                    case 67:
                        if (isOneofPresent(i10, i7, obj)) {
                            long oneofLongAt3 = oneofLongAt(j, obj);
                            computeTagSize10 = CodedOutputStream.computeTagSize(i10);
                            computeUInt64SizeNoTag3 = CodedOutputStream.computeUInt64SizeNoTag((oneofLongAt3 >> 63) ^ (oneofLongAt3 << 1));
                            computeFixed64Size = computeUInt64SizeNoTag3 + computeTagSize10;
                            i8 += computeFixed64Size;
                            break;
                        } else {
                            break;
                        }
                    case 68:
                        if (isOneofPresent(i10, i7, obj)) {
                            computeFixed64Size = CodedOutputStream.computeGroupSize(i10, (MessageLite) UnsafeUtil.getObject(j, obj), getMessageFieldSchema(i7));
                            i8 += computeFixed64Size;
                            break;
                        } else {
                            break;
                        }
                }
                i7 += 3;
                i5 = 267386880;
                i6 = 1048575;
            }
            return unknownFieldSchema.getSerializedSize(unknownFieldSchema.getFromMessage(obj)) + i8;
        }
        int i17 = 0;
        int i18 = 0;
        int i19 = 0;
        int i20 = 1048575;
        while (i17 < iArr.length) {
            int typeAndOffsetAt2 = typeAndOffsetAt(i17);
            int i21 = iArr[i17];
            int i22 = (typeAndOffsetAt2 & 267386880) >>> 20;
            if (i22 <= 17) {
                i3 = iArr[i17 + 2];
                int i23 = i3 & 1048575;
                i4 = 1 << (i3 >>> 20);
                if (i23 != i20) {
                    i = i18;
                    i19 = unsafe.getInt(obj, i23);
                    i20 = i23;
                } else {
                    i = i18;
                }
                i2 = 1048575;
            } else {
                i = i18;
                if (!z3 || i22 < FieldType.DOUBLE_LIST_PACKED.m240id() || i22 > FieldType.SINT64_LIST_PACKED.m240id()) {
                    i2 = 1048575;
                    i3 = 0;
                } else {
                    i2 = 1048575;
                    i3 = iArr[i17 + 2] & 1048575;
                }
                i4 = 0;
            }
            int i24 = typeAndOffsetAt2 & i2;
            int i25 = i20;
            long j5 = i24;
            switch (i22) {
                case 0:
                    z = true;
                    if ((i19 & i4) != 0) {
                        computeTagSize = CodedOutputStream.computeTagSize(i21) + 8;
                        i18 = i + computeTagSize;
                        break;
                    }
                    i18 = i;
                    break;
                case 1:
                    z = true;
                    if ((i19 & i4) != 0) {
                        computeTagSize = CodedOutputStream.computeTagSize(i21) + 4;
                        i18 = i + computeTagSize;
                        break;
                    }
                    i18 = i;
                    break;
                case 2:
                    z = true;
                    if ((i19 & i4) != 0) {
                        long j6 = unsafe.getLong(obj, j5);
                        computeTagSize2 = CodedOutputStream.computeTagSize(i21);
                        computeUInt64SizeNoTag = CodedOutputStream.computeUInt64SizeNoTag(j6);
                        computeTagSize = computeTagSize2 + computeUInt64SizeNoTag;
                        i18 = i + computeTagSize;
                        break;
                    }
                    i18 = i;
                    break;
                case 3:
                    z = true;
                    if ((i19 & i4) != 0) {
                        long j7 = unsafe.getLong(obj, j5);
                        computeTagSize2 = CodedOutputStream.computeTagSize(i21);
                        computeUInt64SizeNoTag = CodedOutputStream.computeUInt64SizeNoTag(j7);
                        computeTagSize = computeTagSize2 + computeUInt64SizeNoTag;
                        i18 = i + computeTagSize;
                        break;
                    }
                    i18 = i;
                    break;
                case 4:
                    z = true;
                    if ((i19 & i4) != 0) {
                        computeTagSize = CodedOutputStream.computeInt32SizeNoTag(unsafe.getInt(obj, j5)) + CodedOutputStream.computeTagSize(i21);
                        i18 = i + computeTagSize;
                        break;
                    }
                    i18 = i;
                    break;
                case 5:
                    z = true;
                    if ((i19 & i4) != 0) {
                        computeTagSize = CodedOutputStream.computeFixed64Size(i21);
                        i18 = i + computeTagSize;
                        break;
                    }
                    i18 = i;
                    break;
                case 6:
                    z = true;
                    if ((i19 & i4) != 0) {
                        computeTagSize = CodedOutputStream.computeFixed32Size(i21);
                        i18 = i + computeTagSize;
                        break;
                    }
                    i18 = i;
                    break;
                case 7:
                    if ((i19 & i4) != 0) {
                        z = true;
                        computeTagSize = CodedOutputStream.computeTagSize(i21) + 1;
                        i18 = i + computeTagSize;
                        break;
                    }
                    z = true;
                    i18 = i;
                    break;
                case 8:
                    if ((i19 & i4) != 0) {
                        Object object3 = unsafe.getObject(obj, j5);
                        computeBytesSize = object3 instanceof ByteString ? CodedOutputStream.computeBytesSize(i21, (ByteString) object3) : CodedOutputStream.computeStringSizeNoTag((String) object3) + CodedOutputStream.computeTagSize(i21);
                        i18 = i + computeBytesSize;
                        z = true;
                        break;
                    }
                    z = true;
                    i18 = i;
                    break;
                case 9:
                    if ((i19 & i4) != 0) {
                        computeTagSize = SchemaUtil.computeSizeMessage(i21, getMessageFieldSchema(i17), unsafe.getObject(obj, j5));
                        z = true;
                        i18 = i + computeTagSize;
                        break;
                    }
                    z = true;
                    i18 = i;
                    break;
                case 10:
                    if ((i19 & i4) != 0) {
                        computeTagSize = CodedOutputStream.computeBytesSize(i21, (ByteString) unsafe.getObject(obj, j5));
                        z = true;
                        i18 = i + computeTagSize;
                        break;
                    }
                    z = true;
                    i18 = i;
                    break;
                case 11:
                    if ((i19 & i4) != 0) {
                        computeTagSize = CodedOutputStream.computeUInt32Size(i21, unsafe.getInt(obj, j5));
                        z = true;
                        i18 = i + computeTagSize;
                        break;
                    }
                    z = true;
                    i18 = i;
                    break;
                case 12:
                    if ((i19 & i4) != 0) {
                        int i26 = unsafe.getInt(obj, j5);
                        computeTagSize3 = CodedOutputStream.computeTagSize(i21);
                        computeInt32SizeNoTag = CodedOutputStream.computeInt32SizeNoTag(i26);
                        computeTagSize = computeInt32SizeNoTag + computeTagSize3;
                        z = true;
                        i18 = i + computeTagSize;
                        break;
                    }
                    z = true;
                    i18 = i;
                    break;
                case 13:
                    if ((i19 & i4) != 0) {
                        computeBytesSize = CodedOutputStream.computeTagSize(i21) + 4;
                        i18 = i + computeBytesSize;
                        z = true;
                        break;
                    }
                    z = true;
                    i18 = i;
                    break;
                case 14:
                    if ((i19 & i4) != 0) {
                        computeTagSize = CodedOutputStream.computeTagSize(i21) + 8;
                        z = true;
                        i18 = i + computeTagSize;
                        break;
                    }
                    z = true;
                    i18 = i;
                    break;
                case 15:
                    if ((i19 & i4) != 0) {
                        int i27 = unsafe.getInt(obj, j5);
                        computeTagSize3 = CodedOutputStream.computeTagSize(i21);
                        computeInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag((i27 >> 31) ^ (i27 << 1));
                        computeTagSize = computeInt32SizeNoTag + computeTagSize3;
                        z = true;
                        i18 = i + computeTagSize;
                        break;
                    }
                    z = true;
                    i18 = i;
                    break;
                case 16:
                    if ((i19 & i4) != 0) {
                        long j8 = unsafe.getLong(obj, j5);
                        computeTagSize = CodedOutputStream.computeTagSize(i21) + CodedOutputStream.computeUInt64SizeNoTag((j8 >> 63) ^ (j8 << 1));
                        z = true;
                        i18 = i + computeTagSize;
                        break;
                    } else {
                        z = true;
                        i18 = i;
                        break;
                    }
                case 17:
                    if ((i19 & i4) != 0) {
                        computeTagSize = CodedOutputStream.computeGroupSize(i21, (MessageLite) unsafe.getObject(obj, j5), getMessageFieldSchema(i17));
                        z = true;
                        i18 = i + computeTagSize;
                        break;
                    }
                    z = true;
                    i18 = i;
                    break;
                case 18:
                    computeTagSize = SchemaUtil.computeSizeFixed64List((List) unsafe.getObject(obj, j5), i21);
                    z = true;
                    i18 = i + computeTagSize;
                    break;
                case 19:
                    computeSizeFixed32List = SchemaUtil.computeSizeFixed32List((List) unsafe.getObject(obj, j5), i21);
                    i18 = i + computeSizeFixed32List;
                    z = true;
                    break;
                case 20:
                    computeSizeFixed32List = SchemaUtil.computeSizeInt64List((List) unsafe.getObject(obj, j5), i21);
                    i18 = i + computeSizeFixed32List;
                    z = true;
                    break;
                case 21:
                    computeSizeFixed32List = SchemaUtil.computeSizeUInt64List((List) unsafe.getObject(obj, j5), i21);
                    i18 = i + computeSizeFixed32List;
                    z = true;
                    break;
                case 22:
                    computeSizeFixed32List = SchemaUtil.computeSizeInt32List((List) unsafe.getObject(obj, j5), i21);
                    i18 = i + computeSizeFixed32List;
                    z = true;
                    break;
                case 23:
                    computeSizeFixed32List = SchemaUtil.computeSizeFixed64List((List) unsafe.getObject(obj, j5), i21);
                    i18 = i + computeSizeFixed32List;
                    z = true;
                    break;
                case 24:
                    computeSizeFixed32List = SchemaUtil.computeSizeFixed32List((List) unsafe.getObject(obj, j5), i21);
                    i18 = i + computeSizeFixed32List;
                    z = true;
                    break;
                case 25:
                    List list2 = (List) unsafe.getObject(obj, j5);
                    Class cls4 = SchemaUtil.GENERATED_MESSAGE_CLASS;
                    int size3 = list2.size();
                    computeSizeFixed32List = size3 == 0 ? 0 : (CodedOutputStream.computeTagSize(i21) + 1) * size3;
                    i18 = i + computeSizeFixed32List;
                    z = true;
                    break;
                case 26:
                    computeTagSize = SchemaUtil.computeSizeStringList(i21, (List) unsafe.getObject(obj, j5));
                    z = true;
                    i18 = i + computeTagSize;
                    break;
                case 27:
                    computeTagSize = SchemaUtil.computeSizeMessageList(i21, (List) unsafe.getObject(obj, j5), getMessageFieldSchema(i17));
                    z = true;
                    i18 = i + computeTagSize;
                    break;
                case 28:
                    computeTagSize = SchemaUtil.computeSizeByteStringList(i21, (List) unsafe.getObject(obj, j5));
                    z = true;
                    i18 = i + computeTagSize;
                    break;
                case 29:
                    computeTagSize = SchemaUtil.computeSizeUInt32List((List) unsafe.getObject(obj, j5), i21);
                    z = true;
                    i18 = i + computeTagSize;
                    break;
                case 30:
                    computeSizeFixed32List = SchemaUtil.computeSizeEnumList((List) unsafe.getObject(obj, j5), i21);
                    i18 = i + computeSizeFixed32List;
                    z = true;
                    break;
                case 31:
                    computeSizeFixed32List = SchemaUtil.computeSizeFixed32List((List) unsafe.getObject(obj, j5), i21);
                    i18 = i + computeSizeFixed32List;
                    z = true;
                    break;
                case 32:
                    computeSizeFixed32List = SchemaUtil.computeSizeFixed64List((List) unsafe.getObject(obj, j5), i21);
                    i18 = i + computeSizeFixed32List;
                    z = true;
                    break;
                case 33:
                    computeSizeFixed32List = SchemaUtil.computeSizeSInt32List((List) unsafe.getObject(obj, j5), i21);
                    i18 = i + computeSizeFixed32List;
                    z = true;
                    break;
                case 34:
                    computeSizeFixed32List = SchemaUtil.computeSizeSInt64List((List) unsafe.getObject(obj, j5), i21);
                    i18 = i + computeSizeFixed32List;
                    z = true;
                    break;
                case 35:
                    computeSizeFixed64ListNoTag = SchemaUtil.computeSizeFixed64ListNoTag((List) unsafe.getObject(obj, j5));
                    if (computeSizeFixed64ListNoTag > 0) {
                        if (z3) {
                            unsafe.putInt(obj, i3, computeSizeFixed64ListNoTag);
                        }
                        computeTagSize4 = CodedOutputStream.computeTagSize(i21);
                        computeUInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag(computeSizeFixed64ListNoTag);
                        i18 = computeUInt32SizeNoTag + computeTagSize4 + computeSizeFixed64ListNoTag + i;
                        z = true;
                        break;
                    }
                    z = true;
                    i18 = i;
                    break;
                case 36:
                    computeSizeFixed64ListNoTag = SchemaUtil.computeSizeFixed32ListNoTag((List) unsafe.getObject(obj, j5));
                    if (computeSizeFixed64ListNoTag > 0) {
                        if (z3) {
                            unsafe.putInt(obj, i3, computeSizeFixed64ListNoTag);
                        }
                        computeTagSize4 = CodedOutputStream.computeTagSize(i21);
                        computeUInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag(computeSizeFixed64ListNoTag);
                        i18 = computeUInt32SizeNoTag + computeTagSize4 + computeSizeFixed64ListNoTag + i;
                        z = true;
                        break;
                    }
                    z = true;
                    i18 = i;
                    break;
                case 37:
                    computeSizeFixed64ListNoTag = SchemaUtil.computeSizeInt64ListNoTag((List) unsafe.getObject(obj, j5));
                    if (computeSizeFixed64ListNoTag > 0) {
                        if (z3) {
                            unsafe.putInt(obj, i3, computeSizeFixed64ListNoTag);
                        }
                        computeTagSize4 = CodedOutputStream.computeTagSize(i21);
                        computeUInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag(computeSizeFixed64ListNoTag);
                        i18 = computeUInt32SizeNoTag + computeTagSize4 + computeSizeFixed64ListNoTag + i;
                        z = true;
                        break;
                    }
                    z = true;
                    i18 = i;
                    break;
                case 38:
                    computeSizeFixed64ListNoTag = SchemaUtil.computeSizeUInt64ListNoTag((List) unsafe.getObject(obj, j5));
                    if (computeSizeFixed64ListNoTag > 0) {
                        if (z3) {
                            unsafe.putInt(obj, i3, computeSizeFixed64ListNoTag);
                        }
                        computeTagSize4 = CodedOutputStream.computeTagSize(i21);
                        computeUInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag(computeSizeFixed64ListNoTag);
                        i18 = computeUInt32SizeNoTag + computeTagSize4 + computeSizeFixed64ListNoTag + i;
                        z = true;
                        break;
                    }
                    z = true;
                    i18 = i;
                    break;
                case 39:
                    computeSizeFixed64ListNoTag = SchemaUtil.computeSizeInt32ListNoTag((List) unsafe.getObject(obj, j5));
                    if (computeSizeFixed64ListNoTag > 0) {
                        if (z3) {
                            unsafe.putInt(obj, i3, computeSizeFixed64ListNoTag);
                        }
                        computeTagSize4 = CodedOutputStream.computeTagSize(i21);
                        computeUInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag(computeSizeFixed64ListNoTag);
                        i18 = computeUInt32SizeNoTag + computeTagSize4 + computeSizeFixed64ListNoTag + i;
                        z = true;
                        break;
                    }
                    z = true;
                    i18 = i;
                    break;
                case 40:
                    computeSizeFixed64ListNoTag = SchemaUtil.computeSizeFixed64ListNoTag((List) unsafe.getObject(obj, j5));
                    if (computeSizeFixed64ListNoTag > 0) {
                        if (z3) {
                            unsafe.putInt(obj, i3, computeSizeFixed64ListNoTag);
                        }
                        computeTagSize4 = CodedOutputStream.computeTagSize(i21);
                        computeUInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag(computeSizeFixed64ListNoTag);
                        i18 = computeUInt32SizeNoTag + computeTagSize4 + computeSizeFixed64ListNoTag + i;
                        z = true;
                        break;
                    }
                    z = true;
                    i18 = i;
                    break;
                case 41:
                    computeSizeFixed64ListNoTag = SchemaUtil.computeSizeFixed32ListNoTag((List) unsafe.getObject(obj, j5));
                    if (computeSizeFixed64ListNoTag > 0) {
                        if (z3) {
                            unsafe.putInt(obj, i3, computeSizeFixed64ListNoTag);
                        }
                        computeTagSize4 = CodedOutputStream.computeTagSize(i21);
                        computeUInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag(computeSizeFixed64ListNoTag);
                        i18 = computeUInt32SizeNoTag + computeTagSize4 + computeSizeFixed64ListNoTag + i;
                        z = true;
                        break;
                    }
                    z = true;
                    i18 = i;
                    break;
                case 42:
                    List list3 = (List) unsafe.getObject(obj, j5);
                    Class cls5 = SchemaUtil.GENERATED_MESSAGE_CLASS;
                    computeSizeFixed64ListNoTag = list3.size();
                    if (computeSizeFixed64ListNoTag > 0) {
                        if (z3) {
                            unsafe.putInt(obj, i3, computeSizeFixed64ListNoTag);
                        }
                        computeTagSize4 = CodedOutputStream.computeTagSize(i21);
                        computeUInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag(computeSizeFixed64ListNoTag);
                        i18 = computeUInt32SizeNoTag + computeTagSize4 + computeSizeFixed64ListNoTag + i;
                        z = true;
                        break;
                    }
                    z = true;
                    i18 = i;
                    break;
                case 43:
                    computeSizeFixed64ListNoTag = SchemaUtil.computeSizeUInt32ListNoTag((List) unsafe.getObject(obj, j5));
                    if (computeSizeFixed64ListNoTag > 0) {
                        if (z3) {
                            unsafe.putInt(obj, i3, computeSizeFixed64ListNoTag);
                        }
                        computeTagSize4 = CodedOutputStream.computeTagSize(i21);
                        computeUInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag(computeSizeFixed64ListNoTag);
                        i18 = computeUInt32SizeNoTag + computeTagSize4 + computeSizeFixed64ListNoTag + i;
                        z = true;
                        break;
                    }
                    z = true;
                    i18 = i;
                    break;
                case 44:
                    computeSizeFixed64ListNoTag = SchemaUtil.computeSizeEnumListNoTag((List) unsafe.getObject(obj, j5));
                    if (computeSizeFixed64ListNoTag > 0) {
                        if (z3) {
                            unsafe.putInt(obj, i3, computeSizeFixed64ListNoTag);
                        }
                        computeTagSize4 = CodedOutputStream.computeTagSize(i21);
                        computeUInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag(computeSizeFixed64ListNoTag);
                        i18 = computeUInt32SizeNoTag + computeTagSize4 + computeSizeFixed64ListNoTag + i;
                        z = true;
                        break;
                    }
                    z = true;
                    i18 = i;
                    break;
                case 45:
                    computeSizeFixed64ListNoTag = SchemaUtil.computeSizeFixed32ListNoTag((List) unsafe.getObject(obj, j5));
                    if (computeSizeFixed64ListNoTag > 0) {
                        if (z3) {
                            unsafe.putInt(obj, i3, computeSizeFixed64ListNoTag);
                        }
                        computeTagSize4 = CodedOutputStream.computeTagSize(i21);
                        computeUInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag(computeSizeFixed64ListNoTag);
                        i18 = computeUInt32SizeNoTag + computeTagSize4 + computeSizeFixed64ListNoTag + i;
                        z = true;
                        break;
                    }
                    z = true;
                    i18 = i;
                    break;
                case 46:
                    computeSizeFixed64ListNoTag = SchemaUtil.computeSizeFixed64ListNoTag((List) unsafe.getObject(obj, j5));
                    if (computeSizeFixed64ListNoTag > 0) {
                        if (z3) {
                            unsafe.putInt(obj, i3, computeSizeFixed64ListNoTag);
                        }
                        computeTagSize4 = CodedOutputStream.computeTagSize(i21);
                        computeUInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag(computeSizeFixed64ListNoTag);
                        i18 = computeUInt32SizeNoTag + computeTagSize4 + computeSizeFixed64ListNoTag + i;
                        z = true;
                        break;
                    }
                    z = true;
                    i18 = i;
                    break;
                case 47:
                    computeSizeFixed64ListNoTag = SchemaUtil.computeSizeSInt32ListNoTag((List) unsafe.getObject(obj, j5));
                    if (computeSizeFixed64ListNoTag > 0) {
                        if (z3) {
                            unsafe.putInt(obj, i3, computeSizeFixed64ListNoTag);
                        }
                        computeTagSize4 = CodedOutputStream.computeTagSize(i21);
                        computeUInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag(computeSizeFixed64ListNoTag);
                        i18 = computeUInt32SizeNoTag + computeTagSize4 + computeSizeFixed64ListNoTag + i;
                        z = true;
                        break;
                    }
                    z = true;
                    i18 = i;
                    break;
                case 48:
                    computeSizeFixed64ListNoTag = SchemaUtil.computeSizeSInt64ListNoTag((List) unsafe.getObject(obj, j5));
                    if (computeSizeFixed64ListNoTag > 0) {
                        if (z3) {
                            unsafe.putInt(obj, i3, computeSizeFixed64ListNoTag);
                        }
                        computeTagSize4 = CodedOutputStream.computeTagSize(i21);
                        computeUInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag(computeSizeFixed64ListNoTag);
                        i18 = computeUInt32SizeNoTag + computeTagSize4 + computeSizeFixed64ListNoTag + i;
                        z = true;
                        break;
                    }
                    z = true;
                    i18 = i;
                    break;
                case 49:
                    List list4 = (List) unsafe.getObject(obj, j5);
                    Schema messageFieldSchema2 = getMessageFieldSchema(i17);
                    Class cls6 = SchemaUtil.GENERATED_MESSAGE_CLASS;
                    int size4 = list4.size();
                    if (size4 == 0) {
                        computeTagSize = 0;
                    } else {
                        int i28 = 0;
                        int i29 = 0;
                        while (i28 < size4) {
                            i29 += CodedOutputStream.computeGroupSize(i21, (MessageLite) list4.get(i28), messageFieldSchema2);
                            i28++;
                            list4 = list4;
                        }
                        computeTagSize = i29;
                    }
                    z = true;
                    i18 = i + computeTagSize;
                    break;
                case 50:
                    computeTagSize = ((MapFieldSchemaLite) mapFieldSchema).getSerializedSize(i21, unsafe.getObject(obj, j5), getMapFieldDefaultEntry(i17));
                    z = true;
                    i18 = i + computeTagSize;
                    break;
                case 51:
                    if (isOneofPresent(i21, i17, obj)) {
                        computeTagSize5 = CodedOutputStream.computeTagSize(i21);
                        computeTagSize = computeTagSize5 + 8;
                        z = true;
                        i18 = i + computeTagSize;
                        break;
                    }
                    z = true;
                    i18 = i;
                    break;
                case 52:
                    if (isOneofPresent(i21, i17, obj)) {
                        computeTagSize = CodedOutputStream.computeTagSize(i21) + 4;
                        z = true;
                        i18 = i + computeTagSize;
                        break;
                    }
                    z = true;
                    i18 = i;
                    break;
                case 53:
                    if (isOneofPresent(i21, i17, obj)) {
                        long oneofLongAt4 = oneofLongAt(j5, obj);
                        computeTagSize6 = CodedOutputStream.computeTagSize(i21);
                        computeUInt64SizeNoTag2 = CodedOutputStream.computeUInt64SizeNoTag(oneofLongAt4);
                        computeTagSize = computeTagSize6 + computeUInt64SizeNoTag2;
                        z = true;
                        i18 = i + computeTagSize;
                        break;
                    }
                    z = true;
                    i18 = i;
                    break;
                case 54:
                    if (isOneofPresent(i21, i17, obj)) {
                        long oneofLongAt5 = oneofLongAt(j5, obj);
                        computeTagSize6 = CodedOutputStream.computeTagSize(i21);
                        computeUInt64SizeNoTag2 = CodedOutputStream.computeUInt64SizeNoTag(oneofLongAt5);
                        computeTagSize = computeTagSize6 + computeUInt64SizeNoTag2;
                        z = true;
                        i18 = i + computeTagSize;
                        break;
                    }
                    z = true;
                    i18 = i;
                    break;
                case 55:
                    if (isOneofPresent(i21, i17, obj)) {
                        int oneofIntAt4 = oneofIntAt(j5, obj);
                        computeTagSize7 = CodedOutputStream.computeTagSize(i21);
                        computeInt32SizeNoTag2 = CodedOutputStream.computeInt32SizeNoTag(oneofIntAt4);
                        computeTagSize = computeInt32SizeNoTag2 + computeTagSize7;
                        z = true;
                        i18 = i + computeTagSize;
                        break;
                    }
                    z = true;
                    i18 = i;
                    break;
                case 56:
                    if (isOneofPresent(i21, i17, obj)) {
                        computeTagSize = CodedOutputStream.computeFixed64Size(i21);
                        z = true;
                        i18 = i + computeTagSize;
                        break;
                    }
                    z = true;
                    i18 = i;
                    break;
                case 57:
                    if (isOneofPresent(i21, i17, obj)) {
                        computeBytesSize = CodedOutputStream.computeFixed32Size(i21);
                        i18 = i + computeBytesSize;
                        z = true;
                        break;
                    }
                    z = true;
                    i18 = i;
                    break;
                case 58:
                    if (isOneofPresent(i21, i17, obj)) {
                        computeTagSize = CodedOutputStream.computeTagSize(i21) + 1;
                        z = true;
                        i18 = i + computeTagSize;
                        break;
                    }
                    z = true;
                    i18 = i;
                    break;
                case 59:
                    if (isOneofPresent(i21, i17, obj)) {
                        Object object4 = unsafe.getObject(obj, j5);
                        computeBytesSize = object4 instanceof ByteString ? CodedOutputStream.computeBytesSize(i21, (ByteString) object4) : CodedOutputStream.computeStringSizeNoTag((String) object4) + CodedOutputStream.computeTagSize(i21);
                        i18 = i + computeBytesSize;
                        z = true;
                        break;
                    }
                    z = true;
                    i18 = i;
                    break;
                case 60:
                    if (isOneofPresent(i21, i17, obj)) {
                        computeTagSize = SchemaUtil.computeSizeMessage(i21, getMessageFieldSchema(i17), unsafe.getObject(obj, j5));
                        z = true;
                        i18 = i + computeTagSize;
                        break;
                    }
                    z = true;
                    i18 = i;
                    break;
                case 61:
                    if (isOneofPresent(i21, i17, obj)) {
                        computeTagSize = CodedOutputStream.computeBytesSize(i21, (ByteString) unsafe.getObject(obj, j5));
                        z = true;
                        i18 = i + computeTagSize;
                        break;
                    }
                    z = true;
                    i18 = i;
                    break;
                case 62:
                    if (isOneofPresent(i21, i17, obj)) {
                        computeTagSize = CodedOutputStream.computeUInt32Size(i21, oneofIntAt(j5, obj));
                        z = true;
                        i18 = i + computeTagSize;
                        break;
                    }
                    z = true;
                    i18 = i;
                    break;
                case 63:
                    if (isOneofPresent(i21, i17, obj)) {
                        int oneofIntAt5 = oneofIntAt(j5, obj);
                        computeTagSize7 = CodedOutputStream.computeTagSize(i21);
                        computeInt32SizeNoTag2 = CodedOutputStream.computeInt32SizeNoTag(oneofIntAt5);
                        computeTagSize = computeInt32SizeNoTag2 + computeTagSize7;
                        z = true;
                        i18 = i + computeTagSize;
                        break;
                    }
                    z = true;
                    i18 = i;
                    break;
                case 64:
                    if (isOneofPresent(i21, i17, obj)) {
                        computeBytesSize = CodedOutputStream.computeTagSize(i21) + 4;
                        i18 = i + computeBytesSize;
                        z = true;
                        break;
                    }
                    z = true;
                    i18 = i;
                    break;
                case 65:
                    if (isOneofPresent(i21, i17, obj)) {
                        computeTagSize5 = CodedOutputStream.computeTagSize(i21);
                        computeTagSize = computeTagSize5 + 8;
                        z = true;
                        i18 = i + computeTagSize;
                        break;
                    }
                    z = true;
                    i18 = i;
                    break;
                case 66:
                    if (isOneofPresent(i21, i17, obj)) {
                        int oneofIntAt6 = oneofIntAt(j5, obj);
                        computeTagSize7 = CodedOutputStream.computeTagSize(i21);
                        computeInt32SizeNoTag2 = CodedOutputStream.computeUInt32SizeNoTag((oneofIntAt6 >> 31) ^ (oneofIntAt6 << 1));
                        computeTagSize = computeInt32SizeNoTag2 + computeTagSize7;
                        z = true;
                        i18 = i + computeTagSize;
                        break;
                    }
                    z = true;
                    i18 = i;
                    break;
                case 67:
                    if (isOneofPresent(i21, i17, obj)) {
                        long oneofLongAt6 = oneofLongAt(j5, obj);
                        computeTagSize6 = CodedOutputStream.computeTagSize(i21);
                        computeUInt64SizeNoTag2 = CodedOutputStream.computeUInt64SizeNoTag((oneofLongAt6 >> 63) ^ (oneofLongAt6 << 1));
                        computeTagSize = computeTagSize6 + computeUInt64SizeNoTag2;
                        z = true;
                        i18 = i + computeTagSize;
                        break;
                    }
                    z = true;
                    i18 = i;
                    break;
                case 68:
                    if (isOneofPresent(i21, i17, obj)) {
                        computeTagSize = CodedOutputStream.computeGroupSize(i21, (MessageLite) unsafe.getObject(obj, j5), getMessageFieldSchema(i17));
                        z = true;
                        i18 = i + computeTagSize;
                        break;
                    }
                    z = true;
                    i18 = i;
                    break;
                default:
                    z = true;
                    i18 = i;
                    break;
            }
            i17 += 3;
            i20 = i25;
        }
        int serializedSize = unknownFieldSchema.getSerializedSize(unknownFieldSchema.getFromMessage(obj)) + i18;
        if (this.hasExtensions) {
            FieldSet extensions = this.extensionSchema.getExtensions(obj);
            int i30 = 0;
            int i31 = 0;
            while (true) {
                SmallSortedMap smallSortedMap = extensions.fields;
                if (i30 < smallSortedMap.getNumArrayEntries()) {
                    Map.Entry arrayEntryAt = smallSortedMap.getArrayEntryAt(i30);
                    i31 = FieldSet.computeFieldSize((GeneratedMessageLite.ExtensionDescriptor) arrayEntryAt.getKey(), arrayEntryAt.getValue()) + i31;
                    i30++;
                } else {
                    for (Map.Entry entry : smallSortedMap.getOverflowEntries()) {
                        i31 = FieldSet.computeFieldSize((GeneratedMessageLite.ExtensionDescriptor) entry.getKey(), entry.getValue()) + i31;
                    }
                    serializedSize += i31;
                }
            }
        }
        return serializedSize;
    }

    /* JADX WARN: Code restructure failed: missing block: B:103:0x01f2, code lost:
    
        if (r4 != false) goto L85;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x00da, code lost:
    
        if (r4 != false) goto L85;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x01f5, code lost:
    
        r8 = 1237;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x01f6, code lost:
    
        r4 = r8;
     */
    @Override // com.google.protobuf.Schema
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int hashCode(Object obj) {
        int i;
        int hashLong;
        int hashCode;
        int[] iArr = this.buffer;
        int length = iArr.length;
        int i2 = 0;
        for (int i3 = 0; i3 < length; i3 += 3) {
            int typeAndOffsetAt = typeAndOffsetAt(i3);
            int i4 = iArr[i3];
            long j = 1048575 & typeAndOffsetAt;
            int i5 = 1231;
            switch ((typeAndOffsetAt & 267386880) >>> 20) {
                case 0:
                    i = i2 * 53;
                    hashLong = Internal.hashLong(Double.doubleToLongBits(UnsafeUtil.getDouble(j, obj)));
                    i2 = hashLong + i;
                    break;
                case 1:
                    i = i2 * 53;
                    hashLong = Float.floatToIntBits(UnsafeUtil.getFloat(j, obj));
                    i2 = hashLong + i;
                    break;
                case 2:
                    i = i2 * 53;
                    hashLong = Internal.hashLong(UnsafeUtil.getLong(j, obj));
                    i2 = hashLong + i;
                    break;
                case 3:
                    i = i2 * 53;
                    hashLong = Internal.hashLong(UnsafeUtil.getLong(j, obj));
                    i2 = hashLong + i;
                    break;
                case 4:
                    i = i2 * 53;
                    hashLong = UnsafeUtil.getInt(j, obj);
                    i2 = hashLong + i;
                    break;
                case 5:
                    i = i2 * 53;
                    hashLong = Internal.hashLong(UnsafeUtil.getLong(j, obj));
                    i2 = hashLong + i;
                    break;
                case 6:
                    i = i2 * 53;
                    hashLong = UnsafeUtil.getInt(j, obj);
                    i2 = hashLong + i;
                    break;
                case 7:
                    i = i2 * 53;
                    boolean z = UnsafeUtil.getBoolean(j, obj);
                    Charset charset = Internal.UTF_8;
                    break;
                case 8:
                    i = i2 * 53;
                    hashLong = ((String) UnsafeUtil.getObject(j, obj)).hashCode();
                    i2 = hashLong + i;
                    break;
                case 9:
                    Object object = UnsafeUtil.getObject(j, obj);
                    if (object != null) {
                        hashCode = object.hashCode();
                        i2 = (i2 * 53) + hashCode;
                        break;
                    }
                    hashCode = 37;
                    i2 = (i2 * 53) + hashCode;
                case 10:
                    i = i2 * 53;
                    hashLong = UnsafeUtil.getObject(j, obj).hashCode();
                    i2 = hashLong + i;
                    break;
                case 11:
                    i = i2 * 53;
                    hashLong = UnsafeUtil.getInt(j, obj);
                    i2 = hashLong + i;
                    break;
                case 12:
                    i = i2 * 53;
                    hashLong = UnsafeUtil.getInt(j, obj);
                    i2 = hashLong + i;
                    break;
                case 13:
                    i = i2 * 53;
                    hashLong = UnsafeUtil.getInt(j, obj);
                    i2 = hashLong + i;
                    break;
                case 14:
                    i = i2 * 53;
                    hashLong = Internal.hashLong(UnsafeUtil.getLong(j, obj));
                    i2 = hashLong + i;
                    break;
                case 15:
                    i = i2 * 53;
                    hashLong = UnsafeUtil.getInt(j, obj);
                    i2 = hashLong + i;
                    break;
                case 16:
                    i = i2 * 53;
                    hashLong = Internal.hashLong(UnsafeUtil.getLong(j, obj));
                    i2 = hashLong + i;
                    break;
                case 17:
                    Object object2 = UnsafeUtil.getObject(j, obj);
                    if (object2 != null) {
                        hashCode = object2.hashCode();
                        i2 = (i2 * 53) + hashCode;
                        break;
                    }
                    hashCode = 37;
                    i2 = (i2 * 53) + hashCode;
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
                    i = i2 * 53;
                    hashLong = UnsafeUtil.getObject(j, obj).hashCode();
                    i2 = hashLong + i;
                    break;
                case 50:
                    i = i2 * 53;
                    hashLong = UnsafeUtil.getObject(j, obj).hashCode();
                    i2 = hashLong + i;
                    break;
                case 51:
                    if (isOneofPresent(i4, i3, obj)) {
                        i = i2 * 53;
                        hashLong = Internal.hashLong(Double.doubleToLongBits(((Double) UnsafeUtil.getObject(j, obj)).doubleValue()));
                        i2 = hashLong + i;
                        break;
                    } else {
                        break;
                    }
                case 52:
                    if (isOneofPresent(i4, i3, obj)) {
                        i = i2 * 53;
                        hashLong = Float.floatToIntBits(((Float) UnsafeUtil.getObject(j, obj)).floatValue());
                        i2 = hashLong + i;
                        break;
                    } else {
                        break;
                    }
                case 53:
                    if (isOneofPresent(i4, i3, obj)) {
                        i = i2 * 53;
                        hashLong = Internal.hashLong(oneofLongAt(j, obj));
                        i2 = hashLong + i;
                        break;
                    } else {
                        break;
                    }
                case 54:
                    if (isOneofPresent(i4, i3, obj)) {
                        i = i2 * 53;
                        hashLong = Internal.hashLong(oneofLongAt(j, obj));
                        i2 = hashLong + i;
                        break;
                    } else {
                        break;
                    }
                case 55:
                    if (isOneofPresent(i4, i3, obj)) {
                        i = i2 * 53;
                        hashLong = oneofIntAt(j, obj);
                        i2 = hashLong + i;
                        break;
                    } else {
                        break;
                    }
                case 56:
                    if (isOneofPresent(i4, i3, obj)) {
                        i = i2 * 53;
                        hashLong = Internal.hashLong(oneofLongAt(j, obj));
                        i2 = hashLong + i;
                        break;
                    } else {
                        break;
                    }
                case 57:
                    if (isOneofPresent(i4, i3, obj)) {
                        i = i2 * 53;
                        hashLong = oneofIntAt(j, obj);
                        i2 = hashLong + i;
                        break;
                    } else {
                        break;
                    }
                case 58:
                    if (isOneofPresent(i4, i3, obj)) {
                        i = i2 * 53;
                        boolean booleanValue = ((Boolean) UnsafeUtil.getObject(j, obj)).booleanValue();
                        Charset charset2 = Internal.UTF_8;
                        break;
                    } else {
                        break;
                    }
                case 59:
                    if (isOneofPresent(i4, i3, obj)) {
                        i = i2 * 53;
                        hashLong = ((String) UnsafeUtil.getObject(j, obj)).hashCode();
                        i2 = hashLong + i;
                        break;
                    } else {
                        break;
                    }
                case 60:
                    if (isOneofPresent(i4, i3, obj)) {
                        i = i2 * 53;
                        hashLong = UnsafeUtil.getObject(j, obj).hashCode();
                        i2 = hashLong + i;
                        break;
                    } else {
                        break;
                    }
                case 61:
                    if (isOneofPresent(i4, i3, obj)) {
                        i = i2 * 53;
                        hashLong = UnsafeUtil.getObject(j, obj).hashCode();
                        i2 = hashLong + i;
                        break;
                    } else {
                        break;
                    }
                case 62:
                    if (isOneofPresent(i4, i3, obj)) {
                        i = i2 * 53;
                        hashLong = oneofIntAt(j, obj);
                        i2 = hashLong + i;
                        break;
                    } else {
                        break;
                    }
                case 63:
                    if (isOneofPresent(i4, i3, obj)) {
                        i = i2 * 53;
                        hashLong = oneofIntAt(j, obj);
                        i2 = hashLong + i;
                        break;
                    } else {
                        break;
                    }
                case 64:
                    if (isOneofPresent(i4, i3, obj)) {
                        i = i2 * 53;
                        hashLong = oneofIntAt(j, obj);
                        i2 = hashLong + i;
                        break;
                    } else {
                        break;
                    }
                case 65:
                    if (isOneofPresent(i4, i3, obj)) {
                        i = i2 * 53;
                        hashLong = Internal.hashLong(oneofLongAt(j, obj));
                        i2 = hashLong + i;
                        break;
                    } else {
                        break;
                    }
                case 66:
                    if (isOneofPresent(i4, i3, obj)) {
                        i = i2 * 53;
                        hashLong = oneofIntAt(j, obj);
                        i2 = hashLong + i;
                        break;
                    } else {
                        break;
                    }
                case 67:
                    if (isOneofPresent(i4, i3, obj)) {
                        i = i2 * 53;
                        hashLong = Internal.hashLong(oneofLongAt(j, obj));
                        i2 = hashLong + i;
                        break;
                    } else {
                        break;
                    }
                case 68:
                    if (isOneofPresent(i4, i3, obj)) {
                        i = i2 * 53;
                        hashLong = UnsafeUtil.getObject(j, obj).hashCode();
                        i2 = hashLong + i;
                        break;
                    } else {
                        break;
                    }
            }
        }
        int hashCode2 = this.unknownFieldSchema.getFromMessage(obj).hashCode() + (i2 * 53);
        return this.hasExtensions ? (hashCode2 * 53) + this.extensionSchema.getExtensions(obj).hashCode() : hashCode2;
    }

    public final boolean isFieldPresent(int i, Object obj) {
        boolean equals;
        int i2 = this.buffer[i + 2];
        long j = i2 & 1048575;
        if (j != 1048575) {
            return ((1 << (i2 >>> 20)) & UnsafeUtil.getInt(j, obj)) != 0;
        }
        int typeAndOffsetAt = typeAndOffsetAt(i);
        long j2 = typeAndOffsetAt & 1048575;
        switch ((typeAndOffsetAt & 267386880) >>> 20) {
            case 0:
                return Double.doubleToRawLongBits(UnsafeUtil.getDouble(j2, obj)) != 0;
            case 1:
                return Float.floatToRawIntBits(UnsafeUtil.getFloat(j2, obj)) != 0;
            case 2:
                return UnsafeUtil.getLong(j2, obj) != 0;
            case 3:
                return UnsafeUtil.getLong(j2, obj) != 0;
            case 4:
                return UnsafeUtil.getInt(j2, obj) != 0;
            case 5:
                return UnsafeUtil.getLong(j2, obj) != 0;
            case 6:
                return UnsafeUtil.getInt(j2, obj) != 0;
            case 7:
                return UnsafeUtil.getBoolean(j2, obj);
            case 8:
                Object object = UnsafeUtil.getObject(j2, obj);
                if (object instanceof String) {
                    equals = ((String) object).isEmpty();
                    break;
                } else {
                    if (!(object instanceof ByteString)) {
                        throw new IllegalArgumentException();
                    }
                    equals = ByteString.EMPTY.equals(object);
                    break;
                }
            case 9:
                return UnsafeUtil.getObject(j2, obj) != null;
            case 10:
                equals = ByteString.EMPTY.equals(UnsafeUtil.getObject(j2, obj));
                break;
            case 11:
                return UnsafeUtil.getInt(j2, obj) != 0;
            case 12:
                return UnsafeUtil.getInt(j2, obj) != 0;
            case 13:
                return UnsafeUtil.getInt(j2, obj) != 0;
            case 14:
                return UnsafeUtil.getLong(j2, obj) != 0;
            case 15:
                return UnsafeUtil.getInt(j2, obj) != 0;
            case 16:
                return UnsafeUtil.getLong(j2, obj) != 0;
            case 17:
                return UnsafeUtil.getObject(j2, obj) != null;
            default:
                throw new IllegalArgumentException();
        }
        return !equals;
    }

    @Override // com.google.protobuf.Schema
    public final boolean isInitialized(Object obj) {
        int i = 1048575;
        int i2 = 0;
        int i3 = 0;
        while (true) {
            boolean z = true;
            if (i2 >= this.checkInitializedCount) {
                return !this.hasExtensions || this.extensionSchema.getExtensions(obj).isInitialized();
            }
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
                    return false;
                }
            }
            int i9 = (267386880 & typeAndOffsetAt) >>> 20;
            if (i9 == 9 || i9 == 17) {
                if (i == 1048575) {
                    z = isFieldPresent(i4, obj);
                } else if ((i8 & i3) == 0) {
                    z = false;
                }
                if (z && !getMessageFieldSchema(i4).isInitialized(UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj))) {
                    return false;
                }
            } else {
                if (i9 != 27) {
                    if (i9 == 60 || i9 == 68) {
                        if (isOneofPresent(i5, i4, obj) && !getMessageFieldSchema(i4).isInitialized(UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj))) {
                            return false;
                        }
                    } else if (i9 != 49) {
                        if (i9 != 50) {
                            continue;
                        } else {
                            Object object = UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj);
                            ((MapFieldSchemaLite) this.mapFieldSchema).getClass();
                            MapFieldLite mapFieldLite = (MapFieldLite) object;
                            if (!mapFieldLite.isEmpty() && ((MapEntryLite) getMapFieldDefaultEntry(i4)).metadata.valueType.getJavaType() == WireFormat$JavaType.MESSAGE) {
                                Iterator it = mapFieldLite.values().iterator();
                                Schema schema = null;
                                while (true) {
                                    if (!it.hasNext()) {
                                        break;
                                    }
                                    Object next = it.next();
                                    if (schema == null) {
                                        schema = Protobuf.INSTANCE.schemaFor(next.getClass());
                                    }
                                    if (!schema.isInitialized(next)) {
                                        z = false;
                                        break;
                                    }
                                }
                            }
                            if (!z) {
                                return false;
                            }
                        }
                    }
                }
                List list = (List) UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj);
                if (!list.isEmpty()) {
                    Schema messageFieldSchema = getMessageFieldSchema(i4);
                    int i10 = 0;
                    while (true) {
                        if (i10 >= list.size()) {
                            break;
                        }
                        if (!messageFieldSchema.isInitialized(list.get(i10))) {
                            z = false;
                            break;
                        }
                        i10++;
                    }
                }
                if (!z) {
                    return false;
                }
            }
            i2++;
        }
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
                int i2 = (typeAndOffsetAt & 267386880) >>> 20;
                Unsafe unsafe = UNSAFE;
                if (i2 != 9) {
                    switch (i2) {
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
                    getMessageFieldSchema(i).makeImmutable(unsafe.getObject(obj, j));
                }
            }
            this.unknownFieldSchema.makeImmutable(obj);
            if (this.hasExtensions) {
                this.extensionSchema.makeImmutable(obj);
            }
        }
    }

    @Override // com.google.protobuf.Schema
    public final void mergeFrom(Object obj, Object obj2) {
        checkMutable(obj);
        obj2.getClass();
        int i = 0;
        while (true) {
            int[] iArr = this.buffer;
            if (i >= iArr.length) {
                Class cls = SchemaUtil.GENERATED_MESSAGE_CLASS;
                UnknownFieldSchema unknownFieldSchema = this.unknownFieldSchema;
                unknownFieldSchema.setToMessage(obj, unknownFieldSchema.merge(unknownFieldSchema.getFromMessage(obj), unknownFieldSchema.getFromMessage(obj2)));
                if (this.hasExtensions) {
                    SchemaUtil.mergeExtensions(this.extensionSchema, obj, obj2);
                    return;
                }
                return;
            }
            int typeAndOffsetAt = typeAndOffsetAt(i);
            long j = 1048575 & typeAndOffsetAt;
            int i2 = iArr[i];
            switch ((typeAndOffsetAt & 267386880) >>> 20) {
                case 0:
                    if (!isFieldPresent(i, obj2)) {
                        break;
                    } else {
                        UnsafeUtil.MEMORY_ACCESSOR.putDouble(obj, j, UnsafeUtil.getDouble(j, obj2));
                        setFieldPresent(i, obj);
                        break;
                    }
                case 1:
                    if (!isFieldPresent(i, obj2)) {
                        break;
                    } else {
                        UnsafeUtil.MEMORY_ACCESSOR.putFloat(obj, j, UnsafeUtil.getFloat(j, obj2));
                        setFieldPresent(i, obj);
                        break;
                    }
                case 2:
                    if (!isFieldPresent(i, obj2)) {
                        break;
                    } else {
                        UnsafeUtil.putLong(obj, j, UnsafeUtil.getLong(j, obj2));
                        setFieldPresent(i, obj);
                        break;
                    }
                case 3:
                    if (!isFieldPresent(i, obj2)) {
                        break;
                    } else {
                        UnsafeUtil.putLong(obj, j, UnsafeUtil.getLong(j, obj2));
                        setFieldPresent(i, obj);
                        break;
                    }
                case 4:
                    if (!isFieldPresent(i, obj2)) {
                        break;
                    } else {
                        UnsafeUtil.putInt(UnsafeUtil.getInt(j, obj2), j, obj);
                        setFieldPresent(i, obj);
                        break;
                    }
                case 5:
                    if (!isFieldPresent(i, obj2)) {
                        break;
                    } else {
                        UnsafeUtil.putLong(obj, j, UnsafeUtil.getLong(j, obj2));
                        setFieldPresent(i, obj);
                        break;
                    }
                case 6:
                    if (!isFieldPresent(i, obj2)) {
                        break;
                    } else {
                        UnsafeUtil.putInt(UnsafeUtil.getInt(j, obj2), j, obj);
                        setFieldPresent(i, obj);
                        break;
                    }
                case 7:
                    if (!isFieldPresent(i, obj2)) {
                        break;
                    } else {
                        UnsafeUtil.MEMORY_ACCESSOR.putBoolean(obj, j, UnsafeUtil.getBoolean(j, obj2));
                        setFieldPresent(i, obj);
                        break;
                    }
                case 8:
                    if (!isFieldPresent(i, obj2)) {
                        break;
                    } else {
                        UnsafeUtil.putObject(j, obj, UnsafeUtil.getObject(j, obj2));
                        setFieldPresent(i, obj);
                        break;
                    }
                case 9:
                    mergeMessage(i, obj, obj2);
                    break;
                case 10:
                    if (!isFieldPresent(i, obj2)) {
                        break;
                    } else {
                        UnsafeUtil.putObject(j, obj, UnsafeUtil.getObject(j, obj2));
                        setFieldPresent(i, obj);
                        break;
                    }
                case 11:
                    if (!isFieldPresent(i, obj2)) {
                        break;
                    } else {
                        UnsafeUtil.putInt(UnsafeUtil.getInt(j, obj2), j, obj);
                        setFieldPresent(i, obj);
                        break;
                    }
                case 12:
                    if (!isFieldPresent(i, obj2)) {
                        break;
                    } else {
                        UnsafeUtil.putInt(UnsafeUtil.getInt(j, obj2), j, obj);
                        setFieldPresent(i, obj);
                        break;
                    }
                case 13:
                    if (!isFieldPresent(i, obj2)) {
                        break;
                    } else {
                        UnsafeUtil.putInt(UnsafeUtil.getInt(j, obj2), j, obj);
                        setFieldPresent(i, obj);
                        break;
                    }
                case 14:
                    if (!isFieldPresent(i, obj2)) {
                        break;
                    } else {
                        UnsafeUtil.putLong(obj, j, UnsafeUtil.getLong(j, obj2));
                        setFieldPresent(i, obj);
                        break;
                    }
                case 15:
                    if (!isFieldPresent(i, obj2)) {
                        break;
                    } else {
                        UnsafeUtil.putInt(UnsafeUtil.getInt(j, obj2), j, obj);
                        setFieldPresent(i, obj);
                        break;
                    }
                case 16:
                    if (!isFieldPresent(i, obj2)) {
                        break;
                    } else {
                        UnsafeUtil.putLong(obj, j, UnsafeUtil.getLong(j, obj2));
                        setFieldPresent(i, obj);
                        break;
                    }
                case 17:
                    mergeMessage(i, obj, obj2);
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
                    break;
                case 50:
                    Class cls2 = SchemaUtil.GENERATED_MESSAGE_CLASS;
                    UnsafeUtil.putObject(j, obj, ((MapFieldSchemaLite) this.mapFieldSchema).mergeFrom(UnsafeUtil.getObject(j, obj), UnsafeUtil.getObject(j, obj2)));
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
                    if (!isOneofPresent(i2, i, obj2)) {
                        break;
                    } else {
                        UnsafeUtil.putObject(j, obj, UnsafeUtil.getObject(j, obj2));
                        setOneofPresent(i2, i, obj);
                        break;
                    }
                case 60:
                    mergeOneofMessage(i, obj, obj2);
                    break;
                case 61:
                case 62:
                case 63:
                case 64:
                case 65:
                case 66:
                case 67:
                    if (!isOneofPresent(i2, i, obj2)) {
                        break;
                    } else {
                        UnsafeUtil.putObject(j, obj, UnsafeUtil.getObject(j, obj2));
                        setOneofPresent(i2, i, obj);
                        break;
                    }
                case 68:
                    mergeOneofMessage(i, obj, obj2);
                    break;
            }
            i += 3;
        }
    }

    public final void mergeMap(Object obj, int i, Object obj2, ExtensionRegistryLite extensionRegistryLite, Reader reader) {
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
        CodedInputStreamReader codedInputStreamReader = (CodedInputStreamReader) reader;
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
            } finally {
                codedInputStream.popLimit(pushLimit);
            }
        }
        mapFieldLite.put(obj3, obj5);
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
        GeneratedMessageLite generatedMessageLite = (GeneratedMessageLite) this.defaultInstance;
        generatedMessageLite.getClass();
        return (GeneratedMessageLite) generatedMessageLite.dynamicMethod(GeneratedMessageLite.MethodToInvoke.NEW_MUTABLE_INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v5, types: [int] */
    public final int parseMapField(Object obj, byte[] bArr, int i, int i2, int i3, long j, ArrayDecoders.Registers registers) {
        Object mapFieldDefaultEntry = getMapFieldDefaultEntry(i3);
        Unsafe unsafe = UNSAFE;
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
            byte b = bArr[decodeVarint32];
            if (b < 0) {
                i6 = ArrayDecoders.decodeVarint32(b, bArr, i6, registers);
                b = registers.int1;
            }
            int i7 = b >>> 3;
            int i8 = b & 7;
            if (i7 != 1) {
                if (i7 == 2 && i8 == metadata.valueType.getWireType()) {
                    decodeVarint32 = decodeMapEntryValue(bArr, i6, i2, metadata.valueType, obj3.getClass(), registers);
                    obj5 = registers.object1;
                }
                decodeVarint32 = ArrayDecoders.skipField(b, bArr, i6, i2, registers);
            } else if (i8 == metadata.keyType.getWireType()) {
                decodeVarint32 = decodeMapEntryValue(bArr, i6, i2, metadata.keyType, null, registers);
                obj4 = registers.object1;
            } else {
                decodeVarint32 = ArrayDecoders.skipField(b, bArr, i6, i2, registers);
            }
        }
        if (decodeVarint32 != i5) {
            throw InvalidProtocolBufferException.parseFailure();
        }
        mapFieldLite.put(obj4, obj5);
        return i5;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public final int parseOneofField(Object obj, byte[] bArr, int i, int i2, int i3, int i4, int i5, int i6, int i7, long j, int i8, ArrayDecoders.Registers registers) {
        int mergeMessageField;
        long j2 = this.buffer[i8 + 2] & 1048575;
        Unsafe unsafe = UNSAFE;
        switch (i7) {
            case 51:
                if (i5 == 1) {
                    unsafe.putObject(obj, j, Double.valueOf(Double.longBitsToDouble(ArrayDecoders.decodeFixed64(i, bArr))));
                    int i9 = i + 8;
                    unsafe.putInt(obj, j2, i4);
                    return i9;
                }
                return i;
            case 52:
                if (i5 == 5) {
                    unsafe.putObject(obj, j, Float.valueOf(Float.intBitsToFloat(ArrayDecoders.decodeFixed32(i, bArr))));
                    int i10 = i + 4;
                    unsafe.putInt(obj, j2, i4);
                    return i10;
                }
                return i;
            case 53:
            case 54:
                if (i5 == 0) {
                    int decodeVarint64 = ArrayDecoders.decodeVarint64(bArr, i, registers);
                    unsafe.putObject(obj, j, Long.valueOf(registers.long1));
                    unsafe.putInt(obj, j2, i4);
                    return decodeVarint64;
                }
                return i;
            case 55:
            case 62:
                if (i5 == 0) {
                    int decodeVarint32 = ArrayDecoders.decodeVarint32(bArr, i, registers);
                    unsafe.putObject(obj, j, Integer.valueOf(registers.int1));
                    unsafe.putInt(obj, j2, i4);
                    return decodeVarint32;
                }
                return i;
            case 56:
            case 65:
                if (i5 == 1) {
                    unsafe.putObject(obj, j, Long.valueOf(ArrayDecoders.decodeFixed64(i, bArr)));
                    int i11 = i + 8;
                    unsafe.putInt(obj, j2, i4);
                    return i11;
                }
                return i;
            case 57:
            case 64:
                if (i5 == 5) {
                    unsafe.putObject(obj, j, Integer.valueOf(ArrayDecoders.decodeFixed32(i, bArr)));
                    int i12 = i + 4;
                    unsafe.putInt(obj, j2, i4);
                    return i12;
                }
                return i;
            case 58:
                if (i5 == 0) {
                    int decodeVarint642 = ArrayDecoders.decodeVarint64(bArr, i, registers);
                    unsafe.putObject(obj, j, Boolean.valueOf(registers.long1 != 0));
                    unsafe.putInt(obj, j2, i4);
                    return decodeVarint642;
                }
                return i;
            case 59:
                if (i5 == 2) {
                    int decodeVarint322 = ArrayDecoders.decodeVarint32(bArr, i, registers);
                    int i13 = registers.int1;
                    if (i13 == 0) {
                        unsafe.putObject(obj, j, "");
                    } else {
                        if ((i6 & QuickStepContract.SYSUI_STATE_KNOX_HARD_KEY_INTENT) != 0) {
                            if (!(Utf8.processor.partialIsValidUtf8(decodeVarint322, decodeVarint322 + i13, bArr) == 0)) {
                                throw InvalidProtocolBufferException.invalidUtf8();
                            }
                        }
                        unsafe.putObject(obj, j, new String(bArr, decodeVarint322, i13, Internal.UTF_8));
                        decodeVarint322 += i13;
                    }
                    unsafe.putInt(obj, j2, i4);
                    return decodeVarint322;
                }
                return i;
            case 60:
                if (i5 == 2) {
                    Object mutableOneofMessageFieldForMerge = mutableOneofMessageFieldForMerge(i4, i8, obj);
                    mergeMessageField = ArrayDecoders.mergeMessageField(mutableOneofMessageFieldForMerge, getMessageFieldSchema(i8), bArr, i, i2, registers);
                    storeOneofMessageField(i4, i8, obj, mutableOneofMessageFieldForMerge);
                    break;
                }
                return i;
            case 61:
                if (i5 == 2) {
                    int decodeBytes = ArrayDecoders.decodeBytes(bArr, i, registers);
                    unsafe.putObject(obj, j, registers.object1);
                    unsafe.putInt(obj, j2, i4);
                    return decodeBytes;
                }
                return i;
            case 63:
                if (i5 == 0) {
                    int decodeVarint323 = ArrayDecoders.decodeVarint32(bArr, i, registers);
                    int i14 = registers.int1;
                    Internal.EnumVerifier enumFieldVerifier = getEnumFieldVerifier(i8);
                    if (enumFieldVerifier == null || enumFieldVerifier.isInRange(i14)) {
                        unsafe.putObject(obj, j, Integer.valueOf(i14));
                        unsafe.putInt(obj, j2, i4);
                    } else {
                        getMutableUnknownFields(obj).storeField(i3, Long.valueOf(i14));
                    }
                    return decodeVarint323;
                }
                return i;
            case 66:
                if (i5 == 0) {
                    int decodeVarint324 = ArrayDecoders.decodeVarint32(bArr, i, registers);
                    unsafe.putObject(obj, j, Integer.valueOf(CodedInputStream.decodeZigZag32(registers.int1)));
                    unsafe.putInt(obj, j2, i4);
                    return decodeVarint324;
                }
                return i;
            case 67:
                if (i5 == 0) {
                    int decodeVarint643 = ArrayDecoders.decodeVarint64(bArr, i, registers);
                    unsafe.putObject(obj, j, Long.valueOf(CodedInputStream.decodeZigZag64(registers.long1)));
                    unsafe.putInt(obj, j2, i4);
                    return decodeVarint643;
                }
                return i;
            case 68:
                if (i5 == 3) {
                    Object mutableOneofMessageFieldForMerge2 = mutableOneofMessageFieldForMerge(i4, i8, obj);
                    mergeMessageField = ArrayDecoders.mergeGroupField(mutableOneofMessageFieldForMerge2, getMessageFieldSchema(i8), bArr, i, i2, (i3 & (-8)) | 4, registers);
                    storeOneofMessageField(i4, i8, obj, mutableOneofMessageFieldForMerge2);
                    break;
                }
                return i;
            default:
                return i;
        }
        return mergeMessageField;
    }

    /* JADX WARN: Code restructure failed: missing block: B:260:0x03a3, code lost:
    
        if (r0 != r19) goto L134;
     */
    /* JADX WARN: Code restructure failed: missing block: B:261:0x03ef, code lost:
    
        r14 = r35;
        r12 = r36;
        r3 = r37;
        r13 = r38;
        r1 = r39;
        r11 = r40;
        r4 = r20;
        r6 = r24;
        r2 = r28;
        r5 = r29;
     */
    /* JADX WARN: Code restructure failed: missing block: B:266:0x03d0, code lost:
    
        if (r0 != r14) goto L134;
     */
    /* JADX WARN: Code restructure failed: missing block: B:268:0x03ed, code lost:
    
        if (r0 != r14) goto L134;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x0746, code lost:
    
        if (r5 == r1) goto L228;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x0748, code lost:
    
        r32.putInt(r12, r5, r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x074e, code lost:
    
        r10 = r34.checkInitializedCount;
        r3 = r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x0754, code lost:
    
        if (r10 >= r34.repeatedFieldOffsetStart) goto L284;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x0756, code lost:
    
        r3 = (com.google.protobuf.UnknownFieldSetLite) filterMapUnknownEnumValues(r35, r34.intArray[r10], r3, r34.unknownFieldSchema, r35);
        r10 = r10 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x076c, code lost:
    
        if (r3 == null) goto L234;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x076e, code lost:
    
        r34.unknownFieldSchema.setBuilderToMessage(r12, r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x0773, code lost:
    
        if (r8 != 0) goto L239;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x0775, code lost:
    
        if (r7 != r6) goto L237;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x077c, code lost:
    
        throw com.google.protobuf.InvalidProtocolBufferException.parseFailure();
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x0781, code lost:
    
        return r7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x077d, code lost:
    
        if (r7 > r6) goto L242;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x077f, code lost:
    
        if (r9 != r8) goto L242;
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x0786, code lost:
    
        throw com.google.protobuf.InvalidProtocolBufferException.parseFailure();
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:108:0x06f6  */
    /* JADX WARN: Removed duplicated region for block: B:109:0x06fa  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int parseProto2Message(Object obj, byte[] bArr, int i, int i2, int i3, ArrayDecoders.Registers registers) {
        Unsafe unsafe;
        int i4;
        Object obj2;
        UnknownFieldSetLite unknownFieldSetLite;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        int i11;
        int i12;
        int i13;
        int i14;
        int i15;
        int i16;
        int i17;
        int i18;
        int i19;
        int i20;
        boolean z;
        int i21;
        Object obj3;
        int i22;
        int i23;
        ArrayDecoders.Registers registers2;
        int i24;
        byte[] bArr2;
        int i25;
        byte[] bArr3;
        int i26;
        Object valueOf;
        Object valueOf2;
        Object valueOf3;
        int i27;
        int i28;
        Integer num;
        int decodePackedDoubleList;
        int i29;
        int i30;
        int i31;
        int i32;
        boolean z2;
        boolean z3;
        long j;
        int i33;
        boolean z4;
        char c;
        int decodeVarint64;
        int i34;
        int i35;
        int i36;
        int i37;
        int i38;
        int i39;
        Object obj4 = obj;
        byte[] bArr4 = bArr;
        int i40 = i2;
        ArrayDecoders.Registers registers3 = registers;
        checkMutable(obj);
        Unsafe unsafe2 = UNSAFE;
        int i41 = i;
        int i42 = i3;
        int i43 = 0;
        int i44 = -1;
        int i45 = 0;
        int i46 = 1048575;
        int i47 = 0;
        while (true) {
            if (i41 < i40) {
                int i48 = i41 + 1;
                byte b = bArr4[i41];
                if (b < 0) {
                    i11 = ArrayDecoders.decodeVarint32(b, bArr4, i48, registers3);
                    i10 = registers3.int1;
                } else {
                    i10 = b;
                    i11 = i48;
                }
                int i49 = i10 >>> 3;
                int i50 = i10 & 7;
                int i51 = this.maxFieldNumber;
                int i52 = i11;
                int i53 = this.minFieldNumber;
                int i54 = i42;
                if (i49 > i44) {
                    i14 = (i49 < i53 || i49 > i51) ? -1 : slowPositionForFieldNumber(i49, i45 / 3);
                    i12 = 0;
                } else {
                    if (i49 < i53 || i49 > i51) {
                        i12 = 0;
                        i13 = -1;
                    } else {
                        i12 = 0;
                        i13 = slowPositionForFieldNumber(i49, 0);
                    }
                    i14 = i13;
                }
                if (i14 == -1) {
                    i15 = i46;
                    i16 = i47;
                    i17 = i49;
                    unsafe = unsafe2;
                    i18 = i12;
                    i19 = i52;
                    i20 = i54;
                    z = true;
                    i21 = i10;
                } else {
                    int[] iArr = this.buffer;
                    int i55 = iArr[i14 + 1];
                    int i56 = (i55 & 267386880) >>> 20;
                    int i57 = i10;
                    int i58 = i14;
                    long j2 = i55 & 1048575;
                    if (i56 <= 17) {
                        int i59 = iArr[i58 + 2];
                        int i60 = 1 << (i59 >>> 20);
                        int i61 = i59 & 1048575;
                        if (i61 != i46) {
                            j = j2;
                            if (i46 != 1048575) {
                                unsafe2.putInt(obj4, i46, i47);
                            }
                            i47 = unsafe2.getInt(obj4, i61);
                            i15 = i61;
                        } else {
                            j = j2;
                            i15 = i46;
                        }
                        i16 = i47;
                        switch (i56) {
                            case 0:
                                i33 = i57;
                                i19 = i52;
                                i12 = i58;
                                long j3 = j;
                                z4 = true;
                                c = 65535;
                                if (i50 != 1) {
                                    i20 = i3;
                                    z = z4;
                                    i17 = i49;
                                    unsafe = unsafe2;
                                    i21 = i33;
                                    i18 = 0;
                                    break;
                                } else {
                                    UnsafeUtil.MEMORY_ACCESSOR.putDouble(obj, j3, Double.longBitsToDouble(ArrayDecoders.decodeFixed64(i19, bArr4)));
                                    i41 = i19 + 8;
                                    decodeVarint64 = i41;
                                    i39 = i16 | i60;
                                    int i62 = decodeVarint64;
                                    i47 = i39;
                                    i41 = i62;
                                    i42 = i3;
                                    i44 = i49;
                                    i45 = i12;
                                    i43 = i33;
                                    i46 = i15;
                                    i40 = i2;
                                    break;
                                }
                            case 1:
                                i33 = i57;
                                i19 = i52;
                                i12 = i58;
                                long j4 = j;
                                c = 65535;
                                if (i50 != 5) {
                                    z4 = true;
                                    i20 = i3;
                                    z = z4;
                                    i17 = i49;
                                    unsafe = unsafe2;
                                    i21 = i33;
                                    i18 = 0;
                                    break;
                                } else {
                                    UnsafeUtil.MEMORY_ACCESSOR.putFloat(obj4, j4, Float.intBitsToFloat(ArrayDecoders.decodeFixed32(i19, bArr4)));
                                    i41 = i19 + 4;
                                    decodeVarint64 = i41;
                                    i39 = i16 | i60;
                                    int i622 = decodeVarint64;
                                    i47 = i39;
                                    i41 = i622;
                                    i42 = i3;
                                    i44 = i49;
                                    i45 = i12;
                                    i43 = i33;
                                    i46 = i15;
                                    i40 = i2;
                                    break;
                                }
                            case 2:
                            case 3:
                                i33 = i57;
                                i19 = i52;
                                i12 = i58;
                                long j5 = j;
                                c = 65535;
                                if (i50 != 0) {
                                    z4 = true;
                                    i20 = i3;
                                    z = z4;
                                    i17 = i49;
                                    unsafe = unsafe2;
                                    i21 = i33;
                                    i18 = 0;
                                    break;
                                } else {
                                    decodeVarint64 = ArrayDecoders.decodeVarint64(bArr4, i19, registers3);
                                    unsafe2.putLong(obj, j5, registers3.long1);
                                    i39 = i16 | i60;
                                    int i6222 = decodeVarint64;
                                    i47 = i39;
                                    i41 = i6222;
                                    i42 = i3;
                                    i44 = i49;
                                    i45 = i12;
                                    i43 = i33;
                                    i46 = i15;
                                    i40 = i2;
                                    break;
                                }
                            case 4:
                            case 11:
                                i33 = i57;
                                i19 = i52;
                                i12 = i58;
                                long j6 = j;
                                c = 65535;
                                if (i50 != 0) {
                                    z4 = true;
                                    i20 = i3;
                                    z = z4;
                                    i17 = i49;
                                    unsafe = unsafe2;
                                    i21 = i33;
                                    i18 = 0;
                                    break;
                                } else {
                                    i41 = ArrayDecoders.decodeVarint32(bArr4, i19, registers3);
                                    unsafe2.putInt(obj4, j6, registers3.int1);
                                    decodeVarint64 = i41;
                                    i39 = i16 | i60;
                                    int i62222 = decodeVarint64;
                                    i47 = i39;
                                    i41 = i62222;
                                    i42 = i3;
                                    i44 = i49;
                                    i45 = i12;
                                    i43 = i33;
                                    i46 = i15;
                                    i40 = i2;
                                    break;
                                }
                            case 5:
                            case 14:
                                i19 = i52;
                                long j7 = j;
                                c = 65535;
                                if (i50 != 1) {
                                    i12 = i58;
                                    i33 = i57;
                                    z4 = true;
                                    i20 = i3;
                                    z = z4;
                                    i17 = i49;
                                    unsafe = unsafe2;
                                    i21 = i33;
                                    i18 = 0;
                                    break;
                                } else {
                                    i12 = i58;
                                    i33 = i57;
                                    unsafe2.putLong(obj, j7, ArrayDecoders.decodeFixed64(i19, bArr4));
                                    i41 = i19 + 8;
                                    decodeVarint64 = i41;
                                    i39 = i16 | i60;
                                    int i622222 = decodeVarint64;
                                    i47 = i39;
                                    i41 = i622222;
                                    i42 = i3;
                                    i44 = i49;
                                    i45 = i12;
                                    i43 = i33;
                                    i46 = i15;
                                    i40 = i2;
                                    break;
                                }
                            case 6:
                            case 13:
                                i34 = i57;
                                i19 = i52;
                                i35 = i58;
                                long j8 = j;
                                c = 65535;
                                if (i50 != 5) {
                                    i12 = i35;
                                    i33 = i34;
                                    z4 = true;
                                    i20 = i3;
                                    z = z4;
                                    i17 = i49;
                                    unsafe = unsafe2;
                                    i21 = i33;
                                    i18 = 0;
                                    break;
                                } else {
                                    unsafe2.putInt(obj4, j8, ArrayDecoders.decodeFixed32(i19, bArr4));
                                    i12 = i35;
                                    i33 = i34;
                                    i41 = i19 + 4;
                                    decodeVarint64 = i41;
                                    i39 = i16 | i60;
                                    int i6222222 = decodeVarint64;
                                    i47 = i39;
                                    i41 = i6222222;
                                    i42 = i3;
                                    i44 = i49;
                                    i45 = i12;
                                    i43 = i33;
                                    i46 = i15;
                                    i40 = i2;
                                    break;
                                }
                            case 7:
                                i34 = i57;
                                i19 = i52;
                                i35 = i58;
                                long j9 = j;
                                c = 65535;
                                if (i50 != 0) {
                                    i12 = i35;
                                    i33 = i34;
                                    z4 = true;
                                    i20 = i3;
                                    z = z4;
                                    i17 = i49;
                                    unsafe = unsafe2;
                                    i21 = i33;
                                    i18 = 0;
                                    break;
                                } else {
                                    i41 = ArrayDecoders.decodeVarint64(bArr4, i19, registers3);
                                    UnsafeUtil.MEMORY_ACCESSOR.putBoolean(obj4, j9, registers3.long1 != 0);
                                    i12 = i35;
                                    i33 = i34;
                                    decodeVarint64 = i41;
                                    i39 = i16 | i60;
                                    int i62222222 = decodeVarint64;
                                    i47 = i39;
                                    i41 = i62222222;
                                    i42 = i3;
                                    i44 = i49;
                                    i45 = i12;
                                    i43 = i33;
                                    i46 = i15;
                                    i40 = i2;
                                    break;
                                }
                            case 8:
                                i34 = i57;
                                i19 = i52;
                                i35 = i58;
                                long j10 = j;
                                c = 65535;
                                if (i50 != 2) {
                                    i12 = i35;
                                    i33 = i34;
                                    z4 = true;
                                    i20 = i3;
                                    z = z4;
                                    i17 = i49;
                                    unsafe = unsafe2;
                                    i21 = i33;
                                    i18 = 0;
                                    break;
                                } else {
                                    i41 = (536870912 & i55) == 0 ? ArrayDecoders.decodeString(bArr4, i19, registers3) : ArrayDecoders.decodeStringRequireUtf8(bArr4, i19, registers3);
                                    unsafe2.putObject(obj4, j10, registers3.object1);
                                    i12 = i35;
                                    i33 = i34;
                                    decodeVarint64 = i41;
                                    i39 = i16 | i60;
                                    int i622222222 = decodeVarint64;
                                    i47 = i39;
                                    i41 = i622222222;
                                    i42 = i3;
                                    i44 = i49;
                                    i45 = i12;
                                    i43 = i33;
                                    i46 = i15;
                                    i40 = i2;
                                    break;
                                }
                            case 9:
                                i36 = i57;
                                i19 = i52;
                                i37 = i58;
                                c = 65535;
                                if (i50 != 2) {
                                    i33 = i36;
                                    i12 = i37;
                                    z4 = true;
                                    i20 = i3;
                                    z = z4;
                                    i17 = i49;
                                    unsafe = unsafe2;
                                    i21 = i33;
                                    i18 = 0;
                                    break;
                                } else {
                                    Object mutableMessageFieldForMerge = mutableMessageFieldForMerge(i37, obj4);
                                    i38 = i37;
                                    i41 = ArrayDecoders.mergeMessageField(mutableMessageFieldForMerge, getMessageFieldSchema(i37), bArr, i19, i2, registers);
                                    storeMessageField(i38, obj4, mutableMessageFieldForMerge);
                                    int i63 = i38;
                                    i33 = i36;
                                    i12 = i63;
                                    decodeVarint64 = i41;
                                    i39 = i16 | i60;
                                    int i6222222222 = decodeVarint64;
                                    i47 = i39;
                                    i41 = i6222222222;
                                    i42 = i3;
                                    i44 = i49;
                                    i45 = i12;
                                    i43 = i33;
                                    i46 = i15;
                                    i40 = i2;
                                    break;
                                }
                            case 10:
                                i36 = i57;
                                i19 = i52;
                                i37 = i58;
                                long j11 = j;
                                c = 65535;
                                if (i50 != 2) {
                                    i33 = i36;
                                    i12 = i37;
                                    z4 = true;
                                    i20 = i3;
                                    z = z4;
                                    i17 = i49;
                                    unsafe = unsafe2;
                                    i21 = i33;
                                    i18 = 0;
                                    break;
                                } else {
                                    i41 = ArrayDecoders.decodeBytes(bArr4, i19, registers3);
                                    unsafe2.putObject(obj4, j11, registers3.object1);
                                    i33 = i36;
                                    i12 = i37;
                                    decodeVarint64 = i41;
                                    i39 = i16 | i60;
                                    int i62222222222 = decodeVarint64;
                                    i47 = i39;
                                    i41 = i62222222222;
                                    i42 = i3;
                                    i44 = i49;
                                    i45 = i12;
                                    i43 = i33;
                                    i46 = i15;
                                    i40 = i2;
                                    break;
                                }
                            case 12:
                                i36 = i57;
                                i19 = i52;
                                i38 = i58;
                                long j12 = j;
                                c = 65535;
                                if (i50 != 0) {
                                    int i64 = i38;
                                    i33 = i36;
                                    i12 = i64;
                                    z4 = true;
                                    i20 = i3;
                                    z = z4;
                                    i17 = i49;
                                    unsafe = unsafe2;
                                    i21 = i33;
                                    i18 = 0;
                                    break;
                                } else {
                                    i41 = ArrayDecoders.decodeVarint32(bArr4, i19, registers3);
                                    int i65 = registers3.int1;
                                    Internal.EnumVerifier enumFieldVerifier = getEnumFieldVerifier(i38);
                                    if (enumFieldVerifier == null || enumFieldVerifier.isInRange(i65)) {
                                        unsafe2.putInt(obj4, j12, i65);
                                        int i632 = i38;
                                        i33 = i36;
                                        i12 = i632;
                                        decodeVarint64 = i41;
                                        i39 = i16 | i60;
                                        int i622222222222 = decodeVarint64;
                                        i47 = i39;
                                        i41 = i622222222222;
                                        i42 = i3;
                                        i44 = i49;
                                        i45 = i12;
                                        i43 = i33;
                                        i46 = i15;
                                        i40 = i2;
                                        break;
                                    } else {
                                        getMutableUnknownFields(obj).storeField(i36, Long.valueOf(i65));
                                        i47 = i16;
                                        i33 = i36;
                                        i12 = i38;
                                        i42 = i3;
                                        i44 = i49;
                                        i45 = i12;
                                        i43 = i33;
                                        i46 = i15;
                                        i40 = i2;
                                    }
                                }
                                break;
                            case 15:
                                i36 = i57;
                                i19 = i52;
                                i38 = i58;
                                c = 65535;
                                if (i50 != 0) {
                                    int i642 = i38;
                                    i33 = i36;
                                    i12 = i642;
                                    z4 = true;
                                    i20 = i3;
                                    z = z4;
                                    i17 = i49;
                                    unsafe = unsafe2;
                                    i21 = i33;
                                    i18 = 0;
                                    break;
                                } else {
                                    i41 = ArrayDecoders.decodeVarint32(bArr4, i19, registers3);
                                    unsafe2.putInt(obj4, j, CodedInputStream.decodeZigZag32(registers3.int1));
                                    int i6322 = i38;
                                    i33 = i36;
                                    i12 = i6322;
                                    decodeVarint64 = i41;
                                    i39 = i16 | i60;
                                    int i6222222222222 = decodeVarint64;
                                    i47 = i39;
                                    i41 = i6222222222222;
                                    i42 = i3;
                                    i44 = i49;
                                    i45 = i12;
                                    i43 = i33;
                                    i46 = i15;
                                    i40 = i2;
                                    break;
                                }
                            case 16:
                                i36 = i57;
                                i19 = i52;
                                i38 = i58;
                                c = 65535;
                                if (i50 != 0) {
                                    int i6422 = i38;
                                    i33 = i36;
                                    i12 = i6422;
                                    z4 = true;
                                    i20 = i3;
                                    z = z4;
                                    i17 = i49;
                                    unsafe = unsafe2;
                                    i21 = i33;
                                    i18 = 0;
                                    break;
                                } else {
                                    decodeVarint64 = ArrayDecoders.decodeVarint64(bArr4, i19, registers3);
                                    unsafe2.putLong(obj, j, CodedInputStream.decodeZigZag64(registers3.long1));
                                    i33 = i36;
                                    i12 = i38;
                                    i39 = i16 | i60;
                                    int i62222222222222 = decodeVarint64;
                                    i47 = i39;
                                    i41 = i62222222222222;
                                    i42 = i3;
                                    i44 = i49;
                                    i45 = i12;
                                    i43 = i33;
                                    i46 = i15;
                                    i40 = i2;
                                    break;
                                }
                            case 17:
                                if (i50 != 3) {
                                    i19 = i52;
                                    c = 65535;
                                    i33 = i57;
                                    i12 = i58;
                                    z4 = true;
                                    i20 = i3;
                                    z = z4;
                                    i17 = i49;
                                    unsafe = unsafe2;
                                    i21 = i33;
                                    i18 = 0;
                                    break;
                                } else {
                                    Object mutableMessageFieldForMerge2 = mutableMessageFieldForMerge(i58, obj4);
                                    i36 = i57;
                                    i38 = i58;
                                    i41 = ArrayDecoders.mergeGroupField(mutableMessageFieldForMerge2, getMessageFieldSchema(i58), bArr, i52, i2, (i49 << 3) | 4, registers);
                                    storeMessageField(i38, obj4, mutableMessageFieldForMerge2);
                                    int i63222 = i38;
                                    i33 = i36;
                                    i12 = i63222;
                                    decodeVarint64 = i41;
                                    i39 = i16 | i60;
                                    int i622222222222222 = decodeVarint64;
                                    i47 = i39;
                                    i41 = i622222222222222;
                                    i42 = i3;
                                    i44 = i49;
                                    i45 = i12;
                                    i43 = i33;
                                    i46 = i15;
                                    i40 = i2;
                                    break;
                                }
                            default:
                                i33 = i57;
                                i19 = i52;
                                i12 = i58;
                                z4 = true;
                                c = 65535;
                                i20 = i3;
                                z = z4;
                                i17 = i49;
                                unsafe = unsafe2;
                                i21 = i33;
                                i18 = 0;
                                break;
                        }
                    } else {
                        if (i56 != 27) {
                            i15 = i46;
                            i29 = i47;
                            i30 = i58;
                            if (i56 <= 49) {
                                z2 = true;
                                i31 = i57;
                                i17 = i49;
                                unsafe = unsafe2;
                                i18 = 0;
                                i41 = parseRepeatedField(obj, bArr, i52, i2, i57, i49, i50, i30, i55, i56, j2, registers);
                            } else {
                                i31 = i57;
                                i17 = i49;
                                unsafe = unsafe2;
                                i32 = i52;
                                z2 = true;
                                z2 = true;
                                z3 = true;
                                i18 = 0;
                                if (i56 != 50) {
                                    i41 = parseOneofField(obj, bArr, i32, i2, i31, i17, i50, i55, i56, j2, i30, registers);
                                } else if (i50 == 2) {
                                    i41 = parseMapField(obj, bArr, i32, i2, i30, j2, registers);
                                }
                            }
                        } else if (i50 == 2) {
                            Internal.ProtobufList protobufList = (Internal.ProtobufList) unsafe2.getObject(obj4, j2);
                            if (!((AbstractProtobufList) protobufList).isMutable) {
                                int size = protobufList.size();
                                protobufList = protobufList.mutableCopyWithCapacity(size == 0 ? 10 : size * 2);
                                unsafe2.putObject(obj4, j2, protobufList);
                            }
                            i15 = i46;
                            i41 = ArrayDecoders.decodeMessageList(getMessageFieldSchema(i58), i57, bArr, i52, i2, protobufList, registers);
                            i42 = i3;
                            i44 = i49;
                            i45 = i58;
                            i43 = i57;
                            i47 = i47;
                            i46 = i15;
                            i40 = i2;
                        } else {
                            i15 = i46;
                            i29 = i47;
                            i31 = i57;
                            i17 = i49;
                            unsafe = unsafe2;
                            i32 = i52;
                            i30 = i58;
                            z3 = true;
                            i18 = 0;
                        }
                        i41 = i32;
                        z2 = z3;
                        i20 = i3;
                        i19 = i41;
                        i12 = i30;
                        i16 = i29;
                        i21 = i31;
                        z = z2;
                    }
                }
                if (i21 != i20 || i20 == 0) {
                    if (this.hasExtensions) {
                        ExtensionRegistryLite emptyRegistry = ExtensionRegistryLite.getEmptyRegistry();
                        registers2 = registers;
                        ExtensionRegistryLite extensionRegistryLite = registers2.extensionRegistry;
                        if (extensionRegistryLite != emptyRegistry) {
                            UnknownFieldSchema unknownFieldSchema = this.unknownFieldSchema;
                            extensionRegistryLite.getClass();
                            i22 = i17;
                            GeneratedMessageLite.GeneratedExtension generatedExtension = (GeneratedMessageLite.GeneratedExtension) extensionRegistryLite.extensionsByNumber.get(new ExtensionRegistryLite.ObjectIntPair(this.defaultInstance, i22));
                            if (generatedExtension == null) {
                                i41 = ArrayDecoders.decodeUnknownField(i21, bArr, i19, i2, getMutableUnknownFields(obj), registers);
                                obj3 = obj;
                                i26 = i2;
                                i24 = i20;
                                bArr2 = bArr;
                            } else {
                                obj3 = obj;
                                GeneratedMessageLite.ExtendableMessage extendableMessage = (GeneratedMessageLite.ExtendableMessage) obj3;
                                FieldSet fieldSet = extendableMessage.extensions;
                                if (fieldSet.isImmutable) {
                                    extendableMessage.extensions = fieldSet.m2770clone();
                                }
                                FieldSet fieldSet2 = extendableMessage.extensions;
                                GeneratedMessageLite.ExtensionDescriptor extensionDescriptor = generatedExtension.descriptor;
                                if (extensionDescriptor.isRepeated && extensionDescriptor.isPacked) {
                                    switch (ArrayDecoders.AbstractC45221.$SwitchMap$com$google$protobuf$WireFormat$FieldType[extensionDescriptor.type.ordinal()]) {
                                        case 1:
                                            bArr3 = bArr;
                                            DoubleArrayList doubleArrayList = new DoubleArrayList();
                                            decodePackedDoubleList = ArrayDecoders.decodePackedDoubleList(bArr3, i19, doubleArrayList, registers2);
                                            fieldSet2.setField(extensionDescriptor, doubleArrayList);
                                            break;
                                        case 2:
                                            bArr3 = bArr;
                                            FloatArrayList floatArrayList = new FloatArrayList();
                                            decodePackedDoubleList = ArrayDecoders.decodePackedFloatList(bArr3, i19, floatArrayList, registers2);
                                            fieldSet2.setField(extensionDescriptor, floatArrayList);
                                            break;
                                        case 3:
                                        case 4:
                                            bArr3 = bArr;
                                            LongArrayList longArrayList = new LongArrayList();
                                            decodePackedDoubleList = ArrayDecoders.decodePackedVarint64List(bArr3, i19, longArrayList, registers2);
                                            fieldSet2.setField(extensionDescriptor, longArrayList);
                                            break;
                                        case 5:
                                        case 6:
                                            bArr3 = bArr;
                                            IntArrayList intArrayList = new IntArrayList();
                                            decodePackedDoubleList = ArrayDecoders.decodePackedVarint32List(bArr3, i19, intArrayList, registers2);
                                            fieldSet2.setField(extensionDescriptor, intArrayList);
                                            break;
                                        case 7:
                                        case 8:
                                            bArr3 = bArr;
                                            LongArrayList longArrayList2 = new LongArrayList();
                                            decodePackedDoubleList = ArrayDecoders.decodePackedFixed64List(bArr3, i19, longArrayList2, registers2);
                                            fieldSet2.setField(extensionDescriptor, longArrayList2);
                                            break;
                                        case 9:
                                        case 10:
                                            bArr3 = bArr;
                                            IntArrayList intArrayList2 = new IntArrayList();
                                            decodePackedDoubleList = ArrayDecoders.decodePackedFixed32List(bArr3, i19, intArrayList2, registers2);
                                            fieldSet2.setField(extensionDescriptor, intArrayList2);
                                            break;
                                        case 11:
                                            bArr3 = bArr;
                                            BooleanArrayList booleanArrayList = new BooleanArrayList();
                                            decodePackedDoubleList = ArrayDecoders.decodePackedBoolList(bArr3, i19, booleanArrayList, registers2);
                                            fieldSet2.setField(extensionDescriptor, booleanArrayList);
                                            break;
                                        case 12:
                                            bArr3 = bArr;
                                            IntArrayList intArrayList3 = new IntArrayList();
                                            decodePackedDoubleList = ArrayDecoders.decodePackedSInt32List(bArr3, i19, intArrayList3, registers2);
                                            fieldSet2.setField(extensionDescriptor, intArrayList3);
                                            break;
                                        case 13:
                                            bArr3 = bArr;
                                            LongArrayList longArrayList3 = new LongArrayList();
                                            decodePackedDoubleList = ArrayDecoders.decodePackedSInt64List(bArr3, i19, longArrayList3, registers2);
                                            fieldSet2.setField(extensionDescriptor, longArrayList3);
                                            break;
                                        case 14:
                                            IntArrayList intArrayList4 = new IntArrayList();
                                            bArr3 = bArr;
                                            decodePackedDoubleList = ArrayDecoders.decodePackedVarint32List(bArr3, i19, intArrayList4, registers2);
                                            SchemaUtil.filterUnknownEnumList(extendableMessage, i22, intArrayList4, extensionDescriptor.enumTypeMap, (Object) null, unknownFieldSchema);
                                            fieldSet2.setField(extensionDescriptor, intArrayList4);
                                            break;
                                        default:
                                            StringBuilder sb = new StringBuilder("Type cannot be packed: ");
                                            sb.append(extensionDescriptor.type);
                                            throw new IllegalStateException(sb.toString());
                                    }
                                    i41 = decodePackedDoubleList;
                                } else {
                                    bArr3 = bArr;
                                    WireFormat$FieldType wireFormat$FieldType = extensionDescriptor.type;
                                    if (wireFormat$FieldType != WireFormat$FieldType.ENUM) {
                                        int i66 = ArrayDecoders.AbstractC45221.$SwitchMap$com$google$protobuf$WireFormat$FieldType[wireFormat$FieldType.ordinal()];
                                        MessageLite messageLite = generatedExtension.messageDefaultInstance;
                                        switch (i66) {
                                            case 1:
                                                i24 = i20;
                                                bArr2 = bArr3;
                                                i26 = i2;
                                                valueOf = Double.valueOf(Double.longBitsToDouble(ArrayDecoders.decodeFixed64(i19, bArr2)));
                                                valueOf3 = valueOf;
                                                i41 = i19 + 8;
                                                if (!extensionDescriptor.isRepeated) {
                                                    fieldSet2.addRepeatedField(extensionDescriptor, valueOf3);
                                                    break;
                                                } else {
                                                    fieldSet2.setField(extensionDescriptor, valueOf3);
                                                    break;
                                                }
                                            case 2:
                                                i24 = i20;
                                                bArr2 = bArr3;
                                                i26 = i2;
                                                valueOf2 = Float.valueOf(Float.intBitsToFloat(ArrayDecoders.decodeFixed32(i19, bArr2)));
                                                valueOf3 = valueOf2;
                                                i41 = i19 + 4;
                                                if (!extensionDescriptor.isRepeated) {
                                                }
                                                break;
                                            case 3:
                                            case 4:
                                                i24 = i20;
                                                bArr2 = bArr3;
                                                i26 = i2;
                                                i41 = ArrayDecoders.decodeVarint64(bArr2, i19, registers2);
                                                valueOf3 = Long.valueOf(registers2.long1);
                                                if (!extensionDescriptor.isRepeated) {
                                                }
                                                break;
                                            case 5:
                                            case 6:
                                                i24 = i20;
                                                bArr2 = bArr3;
                                                i26 = i2;
                                                i41 = ArrayDecoders.decodeVarint32(bArr2, i19, registers2);
                                                valueOf3 = Integer.valueOf(registers2.int1);
                                                if (!extensionDescriptor.isRepeated) {
                                                }
                                                break;
                                            case 7:
                                            case 8:
                                                i24 = i20;
                                                bArr2 = bArr3;
                                                i26 = i2;
                                                valueOf = Long.valueOf(ArrayDecoders.decodeFixed64(i19, bArr2));
                                                valueOf3 = valueOf;
                                                i41 = i19 + 8;
                                                if (!extensionDescriptor.isRepeated) {
                                                }
                                                break;
                                            case 9:
                                            case 10:
                                                i24 = i20;
                                                bArr2 = bArr3;
                                                i26 = i2;
                                                valueOf2 = Integer.valueOf(ArrayDecoders.decodeFixed32(i19, bArr2));
                                                valueOf3 = valueOf2;
                                                i41 = i19 + 4;
                                                if (!extensionDescriptor.isRepeated) {
                                                }
                                                break;
                                            case 11:
                                                i24 = i20;
                                                bArr2 = bArr3;
                                                i26 = i2;
                                                i41 = ArrayDecoders.decodeVarint64(bArr2, i19, registers2);
                                                boolean z5 = z;
                                                if (registers2.long1 == 0) {
                                                    z5 = i18;
                                                }
                                                valueOf3 = Boolean.valueOf(z5);
                                                if (!extensionDescriptor.isRepeated) {
                                                }
                                                break;
                                            case 12:
                                                i24 = i20;
                                                bArr2 = bArr3;
                                                i26 = i2;
                                                i41 = ArrayDecoders.decodeVarint32(bArr2, i19, registers2);
                                                valueOf3 = Integer.valueOf(CodedInputStream.decodeZigZag32(registers2.int1));
                                                if (!extensionDescriptor.isRepeated) {
                                                }
                                                break;
                                            case 13:
                                                i24 = i20;
                                                bArr2 = bArr3;
                                                i26 = i2;
                                                i41 = ArrayDecoders.decodeVarint64(bArr2, i19, registers2);
                                                valueOf3 = Long.valueOf(CodedInputStream.decodeZigZag64(registers2.long1));
                                                if (!extensionDescriptor.isRepeated) {
                                                }
                                                break;
                                            case 14:
                                                throw new IllegalStateException("Shouldn't reach here.");
                                            case 15:
                                                i24 = i20;
                                                bArr2 = bArr3;
                                                i26 = i2;
                                                i41 = ArrayDecoders.decodeBytes(bArr2, i19, registers2);
                                                valueOf3 = registers2.object1;
                                                if (!extensionDescriptor.isRepeated) {
                                                }
                                                break;
                                            case 16:
                                                i24 = i20;
                                                bArr2 = bArr3;
                                                i26 = i2;
                                                i41 = ArrayDecoders.decodeString(bArr2, i19, registers2);
                                                valueOf3 = registers2.object1;
                                                if (!extensionDescriptor.isRepeated) {
                                                }
                                                break;
                                            case 17:
                                                int i67 = (i22 << 3) | 4;
                                                Schema schemaFor = Protobuf.INSTANCE.schemaFor(messageLite.getClass());
                                                if (extensionDescriptor.isRepeated) {
                                                    int i68 = i19;
                                                    i28 = i2;
                                                    i24 = i20;
                                                    bArr2 = bArr3;
                                                    i41 = ArrayDecoders.decodeGroupField(schemaFor, bArr, i68, i2, i67, registers);
                                                    fieldSet2.addRepeatedField(extensionDescriptor, registers2.object1);
                                                    i27 = i28;
                                                    i26 = i27;
                                                    break;
                                                } else {
                                                    i24 = i20;
                                                    bArr2 = bArr3;
                                                    Object field = fieldSet2.getField(extensionDescriptor);
                                                    if (field == null) {
                                                        field = schemaFor.newInstance();
                                                        fieldSet2.setField(extensionDescriptor, field);
                                                    }
                                                    i27 = i2;
                                                    i41 = ArrayDecoders.mergeGroupField(field, schemaFor, bArr, i19, i2, i67, registers);
                                                    i26 = i27;
                                                }
                                            case 18:
                                                Schema schemaFor2 = Protobuf.INSTANCE.schemaFor(messageLite.getClass());
                                                if (extensionDescriptor.isRepeated) {
                                                    i41 = ArrayDecoders.decodeMessageField(schemaFor2, bArr3, i19, i2, registers2);
                                                    fieldSet2.addRepeatedField(extensionDescriptor, registers2.object1);
                                                    i27 = i2;
                                                    i24 = i20;
                                                    bArr2 = bArr3;
                                                    i26 = i27;
                                                    break;
                                                } else {
                                                    Object field2 = fieldSet2.getField(extensionDescriptor);
                                                    if (field2 == null) {
                                                        field2 = schemaFor2.newInstance();
                                                        fieldSet2.setField(extensionDescriptor, field2);
                                                    }
                                                    int i69 = i19;
                                                    i28 = i2;
                                                    i41 = ArrayDecoders.mergeMessageField(field2, schemaFor2, bArr, i69, i2, registers);
                                                    i24 = i20;
                                                    bArr2 = bArr3;
                                                    i27 = i28;
                                                    i26 = i27;
                                                }
                                            default:
                                                i24 = i20;
                                                bArr2 = bArr3;
                                                i26 = i2;
                                                num = null;
                                                break;
                                        }
                                    } else {
                                        i19 = ArrayDecoders.decodeVarint32(bArr3, i19, registers2);
                                        if (extensionDescriptor.enumTypeMap.findValueByNumber(registers2.int1) == null) {
                                            SchemaUtil.storeUnknownEnum(extendableMessage, i22, registers2.int1, null, unknownFieldSchema);
                                            i41 = i19;
                                        } else {
                                            num = Integer.valueOf(registers2.int1);
                                            i24 = i20;
                                            bArr2 = bArr3;
                                            i26 = i2;
                                        }
                                    }
                                    valueOf3 = num;
                                    i41 = i19;
                                    if (!extensionDescriptor.isRepeated) {
                                    }
                                }
                                i24 = i20;
                                bArr2 = bArr3;
                                i26 = i2;
                            }
                            i25 = i26;
                            i40 = i25;
                            i43 = i21;
                            i45 = i12;
                            i44 = i22;
                            obj4 = obj3;
                            i42 = i24;
                            i46 = i15;
                            i47 = i16;
                            bArr4 = bArr2;
                            registers3 = registers2;
                            unsafe2 = unsafe;
                        } else {
                            obj3 = obj;
                            i22 = i17;
                            i23 = i2;
                        }
                    } else {
                        obj3 = obj;
                        i22 = i17;
                        i23 = i2;
                        registers2 = registers;
                    }
                    i24 = i20;
                    bArr2 = bArr;
                    int i70 = i19;
                    i25 = i23;
                    i41 = ArrayDecoders.decodeUnknownField(i21, bArr, i70, i2, getMutableUnknownFields(obj), registers);
                    i40 = i25;
                    i43 = i21;
                    i45 = i12;
                    i44 = i22;
                    obj4 = obj3;
                    i42 = i24;
                    i46 = i15;
                    i47 = i16;
                    bArr4 = bArr2;
                    registers3 = registers2;
                    unsafe2 = unsafe;
                } else {
                    obj2 = obj;
                    i6 = i21;
                    i46 = i15;
                    i8 = i16;
                    i9 = 1048575;
                    unknownFieldSetLite = null;
                    i7 = i20;
                    i5 = i19;
                    i4 = i2;
                }
            } else {
                int i71 = i47;
                unsafe = unsafe2;
                i4 = i40;
                obj2 = obj4;
                unknownFieldSetLite = null;
                i5 = i41;
                i6 = i43;
                i7 = i42;
                i8 = i71;
                i9 = 1048575;
            }
        }
    }

    public final int parseRepeatedField(Object obj, byte[] bArr, int i, int i2, int i3, int i4, int i5, int i6, long j, int i7, long j2, ArrayDecoders.Registers registers) {
        int decodeVarint32List;
        int i8 = i;
        Unsafe unsafe = UNSAFE;
        Internal.ProtobufList protobufList = (Internal.ProtobufList) unsafe.getObject(obj, j2);
        if (!((AbstractProtobufList) protobufList).isMutable) {
            int size = protobufList.size();
            protobufList = protobufList.mutableCopyWithCapacity(size == 0 ? 10 : size * 2);
            unsafe.putObject(obj, j2, protobufList);
        }
        switch (i7) {
            case 18:
            case 35:
                if (i5 == 2) {
                    return ArrayDecoders.decodePackedDoubleList(bArr, i8, protobufList, registers);
                }
                if (i5 == 1) {
                    DoubleArrayList doubleArrayList = (DoubleArrayList) protobufList;
                    doubleArrayList.addDouble(Double.longBitsToDouble(ArrayDecoders.decodeFixed64(i8, bArr)));
                    int i9 = i8 + 8;
                    while (i9 < i2) {
                        int decodeVarint32 = ArrayDecoders.decodeVarint32(bArr, i9, registers);
                        if (i3 != registers.int1) {
                            return i9;
                        }
                        doubleArrayList.addDouble(Double.longBitsToDouble(ArrayDecoders.decodeFixed64(decodeVarint32, bArr)));
                        i9 = decodeVarint32 + 8;
                    }
                    return i9;
                }
                break;
            case 19:
            case 36:
                if (i5 == 2) {
                    return ArrayDecoders.decodePackedFloatList(bArr, i8, protobufList, registers);
                }
                if (i5 == 5) {
                    FloatArrayList floatArrayList = (FloatArrayList) protobufList;
                    floatArrayList.addFloat(Float.intBitsToFloat(ArrayDecoders.decodeFixed32(i8, bArr)));
                    int i10 = i8 + 4;
                    while (i10 < i2) {
                        int decodeVarint322 = ArrayDecoders.decodeVarint32(bArr, i10, registers);
                        if (i3 != registers.int1) {
                            return i10;
                        }
                        floatArrayList.addFloat(Float.intBitsToFloat(ArrayDecoders.decodeFixed32(decodeVarint322, bArr)));
                        i10 = decodeVarint322 + 4;
                    }
                    return i10;
                }
                break;
            case 20:
            case 21:
            case 37:
            case 38:
                if (i5 == 2) {
                    return ArrayDecoders.decodePackedVarint64List(bArr, i8, protobufList, registers);
                }
                if (i5 == 0) {
                    LongArrayList longArrayList = (LongArrayList) protobufList;
                    int decodeVarint64 = ArrayDecoders.decodeVarint64(bArr, i8, registers);
                    longArrayList.addLong(registers.long1);
                    while (decodeVarint64 < i2) {
                        int decodeVarint323 = ArrayDecoders.decodeVarint32(bArr, decodeVarint64, registers);
                        if (i3 != registers.int1) {
                            return decodeVarint64;
                        }
                        decodeVarint64 = ArrayDecoders.decodeVarint64(bArr, decodeVarint323, registers);
                        longArrayList.addLong(registers.long1);
                    }
                    return decodeVarint64;
                }
                break;
            case 22:
            case 29:
            case 39:
            case 43:
                if (i5 == 2) {
                    return ArrayDecoders.decodePackedVarint32List(bArr, i8, protobufList, registers);
                }
                if (i5 == 0) {
                    return ArrayDecoders.decodeVarint32List(i3, bArr, i, i2, protobufList, registers);
                }
                break;
            case 23:
            case 32:
            case 40:
            case 46:
                if (i5 == 2) {
                    return ArrayDecoders.decodePackedFixed64List(bArr, i8, protobufList, registers);
                }
                if (i5 == 1) {
                    LongArrayList longArrayList2 = (LongArrayList) protobufList;
                    longArrayList2.addLong(ArrayDecoders.decodeFixed64(i8, bArr));
                    int i11 = i8 + 8;
                    while (i11 < i2) {
                        int decodeVarint324 = ArrayDecoders.decodeVarint32(bArr, i11, registers);
                        if (i3 != registers.int1) {
                            return i11;
                        }
                        longArrayList2.addLong(ArrayDecoders.decodeFixed64(decodeVarint324, bArr));
                        i11 = decodeVarint324 + 8;
                    }
                    return i11;
                }
                break;
            case 24:
            case 31:
            case 41:
            case 45:
                if (i5 == 2) {
                    return ArrayDecoders.decodePackedFixed32List(bArr, i8, protobufList, registers);
                }
                if (i5 == 5) {
                    IntArrayList intArrayList = (IntArrayList) protobufList;
                    intArrayList.addInt(ArrayDecoders.decodeFixed32(i8, bArr));
                    int i12 = i8 + 4;
                    while (i12 < i2) {
                        int decodeVarint325 = ArrayDecoders.decodeVarint32(bArr, i12, registers);
                        if (i3 != registers.int1) {
                            return i12;
                        }
                        intArrayList.addInt(ArrayDecoders.decodeFixed32(decodeVarint325, bArr));
                        i12 = decodeVarint325 + 4;
                    }
                    return i12;
                }
                break;
            case 25:
            case 42:
                if (i5 == 2) {
                    return ArrayDecoders.decodePackedBoolList(bArr, i8, protobufList, registers);
                }
                if (i5 == 0) {
                    BooleanArrayList booleanArrayList = (BooleanArrayList) protobufList;
                    int decodeVarint642 = ArrayDecoders.decodeVarint64(bArr, i8, registers);
                    booleanArrayList.addBoolean(registers.long1 != 0);
                    while (decodeVarint642 < i2) {
                        int decodeVarint326 = ArrayDecoders.decodeVarint32(bArr, decodeVarint642, registers);
                        if (i3 != registers.int1) {
                            return decodeVarint642;
                        }
                        decodeVarint642 = ArrayDecoders.decodeVarint64(bArr, decodeVarint326, registers);
                        booleanArrayList.addBoolean(registers.long1 != 0);
                    }
                    return decodeVarint642;
                }
                break;
            case 26:
                if (i5 == 2) {
                    if ((j & 536870912) == 0) {
                        int decodeVarint327 = ArrayDecoders.decodeVarint32(bArr, i8, registers);
                        int i13 = registers.int1;
                        if (i13 < 0) {
                            throw InvalidProtocolBufferException.negativeSize();
                        }
                        if (i13 == 0) {
                            protobufList.add("");
                        } else {
                            protobufList.add(new String(bArr, decodeVarint327, i13, Internal.UTF_8));
                            decodeVarint327 += i13;
                        }
                        while (decodeVarint327 < i2) {
                            int decodeVarint328 = ArrayDecoders.decodeVarint32(bArr, decodeVarint327, registers);
                            if (i3 != registers.int1) {
                                return decodeVarint327;
                            }
                            decodeVarint327 = ArrayDecoders.decodeVarint32(bArr, decodeVarint328, registers);
                            int i14 = registers.int1;
                            if (i14 < 0) {
                                throw InvalidProtocolBufferException.negativeSize();
                            }
                            if (i14 == 0) {
                                protobufList.add("");
                            } else {
                                protobufList.add(new String(bArr, decodeVarint327, i14, Internal.UTF_8));
                                decodeVarint327 += i14;
                            }
                        }
                        return decodeVarint327;
                    }
                    int decodeVarint329 = ArrayDecoders.decodeVarint32(bArr, i8, registers);
                    int i15 = registers.int1;
                    if (i15 < 0) {
                        throw InvalidProtocolBufferException.negativeSize();
                    }
                    if (i15 == 0) {
                        protobufList.add("");
                    } else {
                        int i16 = decodeVarint329 + i15;
                        if (!(Utf8.processor.partialIsValidUtf8(decodeVarint329, i16, bArr) == 0)) {
                            throw InvalidProtocolBufferException.invalidUtf8();
                        }
                        protobufList.add(new String(bArr, decodeVarint329, i15, Internal.UTF_8));
                        decodeVarint329 = i16;
                    }
                    while (decodeVarint329 < i2) {
                        int decodeVarint3210 = ArrayDecoders.decodeVarint32(bArr, decodeVarint329, registers);
                        if (i3 != registers.int1) {
                            return decodeVarint329;
                        }
                        decodeVarint329 = ArrayDecoders.decodeVarint32(bArr, decodeVarint3210, registers);
                        int i17 = registers.int1;
                        if (i17 < 0) {
                            throw InvalidProtocolBufferException.negativeSize();
                        }
                        if (i17 == 0) {
                            protobufList.add("");
                        } else {
                            int i18 = decodeVarint329 + i17;
                            if (!(Utf8.processor.partialIsValidUtf8(decodeVarint329, i18, bArr) == 0)) {
                                throw InvalidProtocolBufferException.invalidUtf8();
                            }
                            protobufList.add(new String(bArr, decodeVarint329, i17, Internal.UTF_8));
                            decodeVarint329 = i18;
                        }
                    }
                    return decodeVarint329;
                }
                break;
            case 27:
                if (i5 == 2) {
                    return ArrayDecoders.decodeMessageList(getMessageFieldSchema(i6), i3, bArr, i, i2, protobufList, registers);
                }
                break;
            case 28:
                if (i5 == 2) {
                    int decodeVarint3211 = ArrayDecoders.decodeVarint32(bArr, i8, registers);
                    int i19 = registers.int1;
                    if (i19 < 0) {
                        throw InvalidProtocolBufferException.negativeSize();
                    }
                    if (i19 > bArr.length - decodeVarint3211) {
                        throw InvalidProtocolBufferException.truncatedMessage();
                    }
                    if (i19 == 0) {
                        protobufList.add(ByteString.EMPTY);
                    } else {
                        protobufList.add(ByteString.copyFrom(bArr, decodeVarint3211, i19));
                        decodeVarint3211 += i19;
                    }
                    while (decodeVarint3211 < i2) {
                        int decodeVarint3212 = ArrayDecoders.decodeVarint32(bArr, decodeVarint3211, registers);
                        if (i3 != registers.int1) {
                            return decodeVarint3211;
                        }
                        decodeVarint3211 = ArrayDecoders.decodeVarint32(bArr, decodeVarint3212, registers);
                        int i20 = registers.int1;
                        if (i20 < 0) {
                            throw InvalidProtocolBufferException.negativeSize();
                        }
                        if (i20 > bArr.length - decodeVarint3211) {
                            throw InvalidProtocolBufferException.truncatedMessage();
                        }
                        if (i20 == 0) {
                            protobufList.add(ByteString.EMPTY);
                        } else {
                            protobufList.add(ByteString.copyFrom(bArr, decodeVarint3211, i20));
                            decodeVarint3211 += i20;
                        }
                    }
                    return decodeVarint3211;
                }
                break;
            case 30:
            case 44:
                if (i5 == 2) {
                    decodeVarint32List = ArrayDecoders.decodePackedVarint32List(bArr, i8, protobufList, registers);
                } else if (i5 == 0) {
                    decodeVarint32List = ArrayDecoders.decodeVarint32List(i3, bArr, i, i2, protobufList, registers);
                }
                SchemaUtil.filterUnknownEnumList(obj, i4, protobufList, getEnumFieldVerifier(i6), (Object) null, this.unknownFieldSchema);
                return decodeVarint32List;
            case 33:
            case 47:
                if (i5 == 2) {
                    return ArrayDecoders.decodePackedSInt32List(bArr, i8, protobufList, registers);
                }
                if (i5 == 0) {
                    IntArrayList intArrayList2 = (IntArrayList) protobufList;
                    int decodeVarint3213 = ArrayDecoders.decodeVarint32(bArr, i8, registers);
                    intArrayList2.addInt(CodedInputStream.decodeZigZag32(registers.int1));
                    while (decodeVarint3213 < i2) {
                        int decodeVarint3214 = ArrayDecoders.decodeVarint32(bArr, decodeVarint3213, registers);
                        if (i3 != registers.int1) {
                            return decodeVarint3213;
                        }
                        decodeVarint3213 = ArrayDecoders.decodeVarint32(bArr, decodeVarint3214, registers);
                        intArrayList2.addInt(CodedInputStream.decodeZigZag32(registers.int1));
                    }
                    return decodeVarint3213;
                }
                break;
            case 34:
            case 48:
                if (i5 == 2) {
                    return ArrayDecoders.decodePackedSInt64List(bArr, i8, protobufList, registers);
                }
                if (i5 == 0) {
                    LongArrayList longArrayList3 = (LongArrayList) protobufList;
                    int decodeVarint643 = ArrayDecoders.decodeVarint64(bArr, i8, registers);
                    longArrayList3.addLong(CodedInputStream.decodeZigZag64(registers.long1));
                    while (decodeVarint643 < i2) {
                        int decodeVarint3215 = ArrayDecoders.decodeVarint32(bArr, decodeVarint643, registers);
                        if (i3 != registers.int1) {
                            return decodeVarint643;
                        }
                        decodeVarint643 = ArrayDecoders.decodeVarint64(bArr, decodeVarint3215, registers);
                        longArrayList3.addLong(CodedInputStream.decodeZigZag64(registers.long1));
                    }
                    return decodeVarint643;
                }
                break;
            case 49:
                if (i5 == 3) {
                    Schema messageFieldSchema = getMessageFieldSchema(i6);
                    int i21 = (i3 & (-8)) | 4;
                    i8 = ArrayDecoders.decodeGroupField(messageFieldSchema, bArr, i, i2, i21, registers);
                    protobufList.add(registers.object1);
                    while (i8 < i2) {
                        int decodeVarint3216 = ArrayDecoders.decodeVarint32(bArr, i8, registers);
                        if (i3 != registers.int1) {
                            break;
                        } else {
                            i8 = ArrayDecoders.decodeGroupField(messageFieldSchema, bArr, decodeVarint3216, i2, i21, registers);
                            protobufList.add(registers.object1);
                        }
                    }
                    break;
                }
                break;
        }
        return i8;
    }

    public final void readGroupList(Object obj, long j, Reader reader, Schema schema, ExtensionRegistryLite extensionRegistryLite) {
        int readTag;
        List mutableListAt = this.listFieldSchema.mutableListAt(j, obj);
        CodedInputStreamReader codedInputStreamReader = (CodedInputStreamReader) reader;
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

    public final void readMessageList(Object obj, int i, Reader reader, Schema schema, ExtensionRegistryLite extensionRegistryLite) {
        int readTag;
        List mutableListAt = this.listFieldSchema.mutableListAt(i & 1048575, obj);
        CodedInputStreamReader codedInputStreamReader = (CodedInputStreamReader) reader;
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

    public final void readString(Object obj, int i, Reader reader) {
        if ((536870912 & i) != 0) {
            CodedInputStreamReader codedInputStreamReader = (CodedInputStreamReader) reader;
            codedInputStreamReader.requireWireType(2);
            UnsafeUtil.putObject(i & 1048575, obj, codedInputStreamReader.input.readStringRequireUtf8());
        } else {
            if (!this.lite) {
                UnsafeUtil.putObject(i & 1048575, obj, ((CodedInputStreamReader) reader).readBytes());
                return;
            }
            CodedInputStreamReader codedInputStreamReader2 = (CodedInputStreamReader) reader;
            codedInputStreamReader2.requireWireType(2);
            UnsafeUtil.putObject(i & 1048575, obj, codedInputStreamReader2.input.readString());
        }
    }

    public final void readStringList(Object obj, int i, Reader reader) {
        boolean z = (536870912 & i) != 0;
        ListFieldSchema listFieldSchema = this.listFieldSchema;
        if (z) {
            ((CodedInputStreamReader) reader).readStringListInternal(listFieldSchema.mutableListAt(i & 1048575, obj), true);
        } else {
            ((CodedInputStreamReader) reader).readStringListInternal(listFieldSchema.mutableListAt(i & 1048575, obj), false);
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

    /* JADX WARN: Removed duplicated region for block: B:239:0x0567  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x002c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void writeFieldsInAscendingOrderProto3(Object obj, CodedOutputStreamWriter codedOutputStreamWriter) {
        Iterator it;
        Map.Entry entry;
        int length;
        int i;
        Iterator it2;
        boolean z = this.hasExtensions;
        ExtensionSchema extensionSchema = this.extensionSchema;
        if (z) {
            FieldSet extensions = extensionSchema.getExtensions(obj);
            if (!extensions.fields.isEmpty()) {
                it = extensions.iterator();
                entry = (Map.Entry) it.next();
                int[] iArr = this.buffer;
                length = iArr.length;
                i = 0;
                while (i < length) {
                    int typeAndOffsetAt = typeAndOffsetAt(i);
                    int i2 = iArr[i];
                    while (entry != null && extensionSchema.extensionNumber(entry) <= i2) {
                        extensionSchema.serializeExtension(codedOutputStreamWriter, entry);
                        entry = it.hasNext() ? (Map.Entry) it.next() : null;
                    }
                    switch ((267386880 & typeAndOffsetAt) >>> 20) {
                        case 0:
                            it2 = it;
                            if (!isFieldPresent(i, obj)) {
                                break;
                            } else {
                                codedOutputStreamWriter.writeDouble(UnsafeUtil.getDouble(typeAndOffsetAt & 1048575, obj), i2);
                                continue;
                            }
                        case 1:
                            it2 = it;
                            if (isFieldPresent(i, obj)) {
                                codedOutputStreamWriter.writeFloat(UnsafeUtil.getFloat(typeAndOffsetAt & 1048575, obj), i2);
                                break;
                            } else {
                                continue;
                            }
                        case 2:
                            it2 = it;
                            if (isFieldPresent(i, obj)) {
                                codedOutputStreamWriter.writeInt64(i2, UnsafeUtil.getLong(typeAndOffsetAt & 1048575, obj));
                                break;
                            } else {
                                continue;
                            }
                        case 3:
                            it2 = it;
                            if (isFieldPresent(i, obj)) {
                                codedOutputStreamWriter.writeUInt64(i2, UnsafeUtil.getLong(typeAndOffsetAt & 1048575, obj));
                                break;
                            } else {
                                continue;
                            }
                        case 4:
                            it2 = it;
                            if (isFieldPresent(i, obj)) {
                                codedOutputStreamWriter.writeInt32(i2, UnsafeUtil.getInt(typeAndOffsetAt & 1048575, obj));
                                break;
                            } else {
                                continue;
                            }
                        case 5:
                            it2 = it;
                            if (isFieldPresent(i, obj)) {
                                codedOutputStreamWriter.writeFixed64(i2, UnsafeUtil.getLong(typeAndOffsetAt & 1048575, obj));
                                break;
                            } else {
                                continue;
                            }
                        case 6:
                            it2 = it;
                            if (isFieldPresent(i, obj)) {
                                codedOutputStreamWriter.writeFixed32(i2, UnsafeUtil.getInt(typeAndOffsetAt & 1048575, obj));
                                break;
                            } else {
                                continue;
                            }
                        case 7:
                            it2 = it;
                            if (isFieldPresent(i, obj)) {
                                codedOutputStreamWriter.writeBool(i2, UnsafeUtil.getBoolean(typeAndOffsetAt & 1048575, obj));
                                break;
                            } else {
                                continue;
                            }
                        case 8:
                            it2 = it;
                            if (isFieldPresent(i, obj)) {
                                writeString(i2, UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj), codedOutputStreamWriter);
                                break;
                            } else {
                                continue;
                            }
                        case 9:
                            it2 = it;
                            if (isFieldPresent(i, obj)) {
                                codedOutputStreamWriter.writeMessage(i2, getMessageFieldSchema(i), UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj));
                                break;
                            } else {
                                continue;
                            }
                        case 10:
                            it2 = it;
                            if (isFieldPresent(i, obj)) {
                                codedOutputStreamWriter.writeBytes(i2, (ByteString) UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj));
                                break;
                            } else {
                                continue;
                            }
                        case 11:
                            it2 = it;
                            if (isFieldPresent(i, obj)) {
                                codedOutputStreamWriter.writeUInt32(i2, UnsafeUtil.getInt(typeAndOffsetAt & 1048575, obj));
                                break;
                            } else {
                                continue;
                            }
                        case 12:
                            it2 = it;
                            if (isFieldPresent(i, obj)) {
                                codedOutputStreamWriter.writeEnum(i2, UnsafeUtil.getInt(typeAndOffsetAt & 1048575, obj));
                                break;
                            } else {
                                continue;
                            }
                        case 13:
                            it2 = it;
                            if (isFieldPresent(i, obj)) {
                                codedOutputStreamWriter.writeSFixed32(i2, UnsafeUtil.getInt(typeAndOffsetAt & 1048575, obj));
                                break;
                            } else {
                                continue;
                            }
                        case 14:
                            it2 = it;
                            if (isFieldPresent(i, obj)) {
                                codedOutputStreamWriter.writeSFixed64(i2, UnsafeUtil.getLong(typeAndOffsetAt & 1048575, obj));
                                break;
                            } else {
                                continue;
                            }
                        case 15:
                            it2 = it;
                            if (isFieldPresent(i, obj)) {
                                codedOutputStreamWriter.writeSInt32(i2, UnsafeUtil.getInt(typeAndOffsetAt & 1048575, obj));
                                break;
                            } else {
                                continue;
                            }
                        case 16:
                            it2 = it;
                            if (isFieldPresent(i, obj)) {
                                codedOutputStreamWriter.writeSInt64(i2, UnsafeUtil.getLong(typeAndOffsetAt & 1048575, obj));
                                break;
                            } else {
                                continue;
                            }
                        case 17:
                            it2 = it;
                            if (isFieldPresent(i, obj)) {
                                codedOutputStreamWriter.writeGroup(i2, getMessageFieldSchema(i), UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj));
                                break;
                            } else {
                                continue;
                            }
                        case 18:
                            it2 = it;
                            SchemaUtil.writeDoubleList(iArr[i], (List) UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj), codedOutputStreamWriter, false);
                            continue;
                        case 19:
                            it2 = it;
                            SchemaUtil.writeFloatList(iArr[i], (List) UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj), codedOutputStreamWriter, false);
                            continue;
                        case 20:
                            it2 = it;
                            SchemaUtil.writeInt64List(iArr[i], (List) UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj), codedOutputStreamWriter, false);
                            continue;
                        case 21:
                            it2 = it;
                            SchemaUtil.writeUInt64List(iArr[i], (List) UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj), codedOutputStreamWriter, false);
                            continue;
                        case 22:
                            it2 = it;
                            SchemaUtil.writeInt32List(iArr[i], (List) UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj), codedOutputStreamWriter, false);
                            continue;
                        case 23:
                            it2 = it;
                            SchemaUtil.writeFixed64List(iArr[i], (List) UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj), codedOutputStreamWriter, false);
                            continue;
                        case 24:
                            it2 = it;
                            SchemaUtil.writeFixed32List(iArr[i], (List) UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj), codedOutputStreamWriter, false);
                            continue;
                        case 25:
                            it2 = it;
                            SchemaUtil.writeBoolList(iArr[i], (List) UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj), codedOutputStreamWriter, false);
                            continue;
                        case 26:
                            it2 = it;
                            SchemaUtil.writeStringList(iArr[i], (List) UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj), codedOutputStreamWriter);
                            continue;
                        case 27:
                            it2 = it;
                            SchemaUtil.writeMessageList(iArr[i], (List) UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj), codedOutputStreamWriter, getMessageFieldSchema(i));
                            continue;
                        case 28:
                            it2 = it;
                            SchemaUtil.writeBytesList(iArr[i], (List) UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj), codedOutputStreamWriter);
                            continue;
                        case 29:
                            it2 = it;
                            SchemaUtil.writeUInt32List(iArr[i], (List) UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj), codedOutputStreamWriter, false);
                            continue;
                        case 30:
                            it2 = it;
                            SchemaUtil.writeEnumList(iArr[i], (List) UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj), codedOutputStreamWriter, false);
                            continue;
                        case 31:
                            it2 = it;
                            SchemaUtil.writeSFixed32List(iArr[i], (List) UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj), codedOutputStreamWriter, false);
                            continue;
                        case 32:
                            it2 = it;
                            SchemaUtil.writeSFixed64List(iArr[i], (List) UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj), codedOutputStreamWriter, false);
                            continue;
                        case 33:
                            it2 = it;
                            SchemaUtil.writeSInt32List(iArr[i], (List) UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj), codedOutputStreamWriter, false);
                            continue;
                        case 34:
                            it2 = it;
                            SchemaUtil.writeSInt64List(iArr[i], (List) UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj), codedOutputStreamWriter, false);
                            continue;
                        case 35:
                            it2 = it;
                            SchemaUtil.writeDoubleList(iArr[i], (List) UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj), codedOutputStreamWriter, true);
                            continue;
                        case 36:
                            it2 = it;
                            SchemaUtil.writeFloatList(iArr[i], (List) UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj), codedOutputStreamWriter, true);
                            continue;
                        case 37:
                            it2 = it;
                            SchemaUtil.writeInt64List(iArr[i], (List) UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj), codedOutputStreamWriter, true);
                            continue;
                        case 38:
                            it2 = it;
                            SchemaUtil.writeUInt64List(iArr[i], (List) UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj), codedOutputStreamWriter, true);
                            continue;
                        case 39:
                            it2 = it;
                            SchemaUtil.writeInt32List(iArr[i], (List) UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj), codedOutputStreamWriter, true);
                            continue;
                        case 40:
                            it2 = it;
                            SchemaUtil.writeFixed64List(iArr[i], (List) UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj), codedOutputStreamWriter, true);
                            continue;
                        case 41:
                            it2 = it;
                            SchemaUtil.writeFixed32List(iArr[i], (List) UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj), codedOutputStreamWriter, true);
                            continue;
                        case 42:
                            it2 = it;
                            SchemaUtil.writeBoolList(iArr[i], (List) UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj), codedOutputStreamWriter, true);
                            continue;
                        case 43:
                            it2 = it;
                            SchemaUtil.writeUInt32List(iArr[i], (List) UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj), codedOutputStreamWriter, true);
                            continue;
                        case 44:
                            it2 = it;
                            SchemaUtil.writeEnumList(iArr[i], (List) UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj), codedOutputStreamWriter, true);
                            continue;
                        case 45:
                            it2 = it;
                            SchemaUtil.writeSFixed32List(iArr[i], (List) UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj), codedOutputStreamWriter, true);
                            continue;
                        case 46:
                            it2 = it;
                            SchemaUtil.writeSFixed64List(iArr[i], (List) UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj), codedOutputStreamWriter, true);
                            continue;
                        case 47:
                            it2 = it;
                            SchemaUtil.writeSInt32List(iArr[i], (List) UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj), codedOutputStreamWriter, true);
                            continue;
                        case 48:
                            it2 = it;
                            SchemaUtil.writeSInt64List(iArr[i], (List) UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj), codedOutputStreamWriter, true);
                            continue;
                        case 49:
                            SchemaUtil.writeGroupList(iArr[i], (List) UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj), codedOutputStreamWriter, getMessageFieldSchema(i));
                            break;
                        case 50:
                            writeMapHelper(codedOutputStreamWriter, i2, UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj), i);
                            break;
                        case 51:
                            if (isOneofPresent(i2, i, obj)) {
                                codedOutputStreamWriter.writeDouble(((Double) UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj)).doubleValue(), i2);
                                break;
                            }
                            break;
                        case 52:
                            if (isOneofPresent(i2, i, obj)) {
                                codedOutputStreamWriter.writeFloat(((Float) UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj)).floatValue(), i2);
                                break;
                            }
                            break;
                        case 53:
                            if (isOneofPresent(i2, i, obj)) {
                                codedOutputStreamWriter.writeInt64(i2, oneofLongAt(typeAndOffsetAt & 1048575, obj));
                                break;
                            }
                            break;
                        case 54:
                            if (isOneofPresent(i2, i, obj)) {
                                codedOutputStreamWriter.writeUInt64(i2, oneofLongAt(typeAndOffsetAt & 1048575, obj));
                                break;
                            }
                            break;
                        case 55:
                            if (isOneofPresent(i2, i, obj)) {
                                codedOutputStreamWriter.writeInt32(i2, oneofIntAt(typeAndOffsetAt & 1048575, obj));
                                break;
                            }
                            break;
                        case 56:
                            if (isOneofPresent(i2, i, obj)) {
                                codedOutputStreamWriter.writeFixed64(i2, oneofLongAt(typeAndOffsetAt & 1048575, obj));
                                break;
                            }
                            break;
                        case 57:
                            if (isOneofPresent(i2, i, obj)) {
                                codedOutputStreamWriter.writeFixed32(i2, oneofIntAt(typeAndOffsetAt & 1048575, obj));
                                break;
                            }
                            break;
                        case 58:
                            if (isOneofPresent(i2, i, obj)) {
                                codedOutputStreamWriter.writeBool(i2, ((Boolean) UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj)).booleanValue());
                                break;
                            }
                            break;
                        case 59:
                            if (isOneofPresent(i2, i, obj)) {
                                writeString(i2, UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj), codedOutputStreamWriter);
                                break;
                            }
                            break;
                        case 60:
                            if (isOneofPresent(i2, i, obj)) {
                                codedOutputStreamWriter.writeMessage(i2, getMessageFieldSchema(i), UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj));
                                break;
                            }
                            break;
                        case 61:
                            if (isOneofPresent(i2, i, obj)) {
                                codedOutputStreamWriter.writeBytes(i2, (ByteString) UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj));
                                break;
                            }
                            break;
                        case 62:
                            if (isOneofPresent(i2, i, obj)) {
                                codedOutputStreamWriter.writeUInt32(i2, oneofIntAt(typeAndOffsetAt & 1048575, obj));
                                break;
                            }
                            break;
                        case 63:
                            if (isOneofPresent(i2, i, obj)) {
                                codedOutputStreamWriter.writeEnum(i2, oneofIntAt(typeAndOffsetAt & 1048575, obj));
                                break;
                            }
                            break;
                        case 64:
                            if (isOneofPresent(i2, i, obj)) {
                                codedOutputStreamWriter.writeSFixed32(i2, oneofIntAt(typeAndOffsetAt & 1048575, obj));
                                break;
                            }
                            break;
                        case 65:
                            if (isOneofPresent(i2, i, obj)) {
                                codedOutputStreamWriter.writeSFixed64(i2, oneofLongAt(typeAndOffsetAt & 1048575, obj));
                                break;
                            }
                            break;
                        case 66:
                            if (isOneofPresent(i2, i, obj)) {
                                codedOutputStreamWriter.writeSInt32(i2, oneofIntAt(typeAndOffsetAt & 1048575, obj));
                                break;
                            }
                            break;
                        case 67:
                            if (isOneofPresent(i2, i, obj)) {
                                codedOutputStreamWriter.writeSInt64(i2, oneofLongAt(typeAndOffsetAt & 1048575, obj));
                                break;
                            }
                            break;
                        case 68:
                            if (isOneofPresent(i2, i, obj)) {
                                codedOutputStreamWriter.writeGroup(i2, getMessageFieldSchema(i), UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj));
                                break;
                            }
                            break;
                    }
                    it2 = it;
                    i += 3;
                    it = it2;
                }
                Iterator it3 = it;
                while (entry != null) {
                    extensionSchema.serializeExtension(codedOutputStreamWriter, entry);
                    entry = it3.hasNext() ? (Map.Entry) it3.next() : null;
                }
                UnknownFieldSchema unknownFieldSchema = this.unknownFieldSchema;
                unknownFieldSchema.writeTo(unknownFieldSchema.getFromMessage(obj), codedOutputStreamWriter);
            }
        }
        it = null;
        entry = null;
        int[] iArr2 = this.buffer;
        length = iArr2.length;
        i = 0;
        while (i < length) {
        }
        Iterator it32 = it;
        while (entry != null) {
        }
        UnknownFieldSchema unknownFieldSchema2 = this.unknownFieldSchema;
        unknownFieldSchema2.writeTo(unknownFieldSchema2.getFromMessage(obj), codedOutputStreamWriter);
    }

    public final void writeMapHelper(CodedOutputStreamWriter codedOutputStreamWriter, int i, Object obj, int i2) {
        if (obj != null) {
            Object mapFieldDefaultEntry = getMapFieldDefaultEntry(i2);
            MapFieldSchema mapFieldSchema = this.mapFieldSchema;
            ((MapFieldSchemaLite) mapFieldSchema).getClass();
            MapEntryLite.Metadata metadata = ((MapEntryLite) mapFieldDefaultEntry).metadata;
            ((MapFieldSchemaLite) mapFieldSchema).getClass();
            CodedOutputStream codedOutputStream = codedOutputStreamWriter.output;
            codedOutputStream.getClass();
            for (Map.Entry entry : ((MapFieldLite) obj).entrySet()) {
                codedOutputStream.writeTag(i, 2);
                codedOutputStream.writeUInt32NoTag(MapEntryLite.computeSerializedSize(metadata, entry.getKey(), entry.getValue()));
                MapEntryLite.writeTo(codedOutputStream, metadata, entry.getKey(), entry.getValue());
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:16:0x006e  */
    /* JADX WARN: Removed duplicated region for block: B:283:0x055b  */
    /* JADX WARN: Removed duplicated region for block: B:310:0x059a  */
    /* JADX WARN: Removed duplicated region for block: B:533:0x0a0c  */
    @Override // com.google.protobuf.Schema
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void writeTo(Object obj, CodedOutputStreamWriter codedOutputStreamWriter) {
        Iterator it;
        Map.Entry entry;
        int length;
        int i;
        int i2;
        Map.Entry entry2;
        int i3;
        int i4;
        Map.Entry entry3;
        Iterator it2;
        int length2;
        codedOutputStreamWriter.getClass();
        Writer$FieldOrder writer$FieldOrder = Writer$FieldOrder.ASCENDING;
        Writer$FieldOrder writer$FieldOrder2 = Writer$FieldOrder.DESCENDING;
        int[] iArr = this.buffer;
        ExtensionSchema extensionSchema = this.extensionSchema;
        boolean z = this.hasExtensions;
        UnknownFieldSchema unknownFieldSchema = this.unknownFieldSchema;
        int i5 = 267386880;
        int i6 = 1048575;
        Map.Entry entry4 = null;
        if (writer$FieldOrder == writer$FieldOrder2) {
            unknownFieldSchema.writeTo(unknownFieldSchema.getFromMessage(obj), codedOutputStreamWriter);
            if (z) {
                FieldSet extensions = extensionSchema.getExtensions(obj);
                if (!extensions.fields.isEmpty()) {
                    boolean z2 = extensions.hasLazyField;
                    SmallSortedMap smallSortedMap = extensions.fields;
                    if (z2) {
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
                    entry3 = (Map.Entry) it2.next();
                    length2 = iArr.length - 3;
                    while (length2 >= 0) {
                        int typeAndOffsetAt = typeAndOffsetAt(length2);
                        int i7 = iArr[length2];
                        while (entry3 != null && extensionSchema.extensionNumber(entry3) > i7) {
                            extensionSchema.serializeExtension(codedOutputStreamWriter, entry3);
                            entry3 = it2.hasNext() ? (Map.Entry) it2.next() : entry4;
                        }
                        switch ((typeAndOffsetAt & i5) >>> 20) {
                            case 0:
                                if (!isFieldPresent(length2, obj)) {
                                    break;
                                } else {
                                    codedOutputStreamWriter.writeDouble(UnsafeUtil.getDouble(typeAndOffsetAt & i6, obj), i7);
                                    break;
                                }
                            case 1:
                                if (!isFieldPresent(length2, obj)) {
                                    break;
                                } else {
                                    codedOutputStreamWriter.writeFloat(UnsafeUtil.getFloat(typeAndOffsetAt & i6, obj), i7);
                                    break;
                                }
                            case 2:
                                if (!isFieldPresent(length2, obj)) {
                                    break;
                                } else {
                                    codedOutputStreamWriter.writeInt64(i7, UnsafeUtil.getLong(typeAndOffsetAt & i6, obj));
                                    break;
                                }
                            case 3:
                                if (!isFieldPresent(length2, obj)) {
                                    break;
                                } else {
                                    codedOutputStreamWriter.writeUInt64(i7, UnsafeUtil.getLong(typeAndOffsetAt & i6, obj));
                                    break;
                                }
                            case 4:
                                if (!isFieldPresent(length2, obj)) {
                                    break;
                                } else {
                                    codedOutputStreamWriter.writeInt32(i7, UnsafeUtil.getInt(typeAndOffsetAt & i6, obj));
                                    break;
                                }
                            case 5:
                                if (!isFieldPresent(length2, obj)) {
                                    break;
                                } else {
                                    codedOutputStreamWriter.writeFixed64(i7, UnsafeUtil.getLong(typeAndOffsetAt & i6, obj));
                                    break;
                                }
                            case 6:
                                if (!isFieldPresent(length2, obj)) {
                                    break;
                                } else {
                                    codedOutputStreamWriter.writeFixed32(i7, UnsafeUtil.getInt(typeAndOffsetAt & i6, obj));
                                    break;
                                }
                            case 7:
                                if (!isFieldPresent(length2, obj)) {
                                    break;
                                } else {
                                    codedOutputStreamWriter.writeBool(i7, UnsafeUtil.getBoolean(typeAndOffsetAt & i6, obj));
                                    break;
                                }
                            case 8:
                                if (!isFieldPresent(length2, obj)) {
                                    break;
                                } else {
                                    writeString(i7, UnsafeUtil.getObject(typeAndOffsetAt & i6, obj), codedOutputStreamWriter);
                                    break;
                                }
                            case 9:
                                if (!isFieldPresent(length2, obj)) {
                                    break;
                                } else {
                                    codedOutputStreamWriter.writeMessage(i7, getMessageFieldSchema(length2), UnsafeUtil.getObject(typeAndOffsetAt & i6, obj));
                                    break;
                                }
                            case 10:
                                if (!isFieldPresent(length2, obj)) {
                                    break;
                                } else {
                                    codedOutputStreamWriter.writeBytes(i7, (ByteString) UnsafeUtil.getObject(typeAndOffsetAt & i6, obj));
                                    break;
                                }
                            case 11:
                                if (!isFieldPresent(length2, obj)) {
                                    break;
                                } else {
                                    codedOutputStreamWriter.writeUInt32(i7, UnsafeUtil.getInt(typeAndOffsetAt & i6, obj));
                                    break;
                                }
                            case 12:
                                if (!isFieldPresent(length2, obj)) {
                                    break;
                                } else {
                                    codedOutputStreamWriter.writeEnum(i7, UnsafeUtil.getInt(typeAndOffsetAt & i6, obj));
                                    break;
                                }
                            case 13:
                                if (!isFieldPresent(length2, obj)) {
                                    break;
                                } else {
                                    codedOutputStreamWriter.writeSFixed32(i7, UnsafeUtil.getInt(typeAndOffsetAt & i6, obj));
                                    break;
                                }
                            case 14:
                                if (!isFieldPresent(length2, obj)) {
                                    break;
                                } else {
                                    codedOutputStreamWriter.writeSFixed64(i7, UnsafeUtil.getLong(typeAndOffsetAt & i6, obj));
                                    break;
                                }
                            case 15:
                                if (!isFieldPresent(length2, obj)) {
                                    break;
                                } else {
                                    codedOutputStreamWriter.writeSInt32(i7, UnsafeUtil.getInt(typeAndOffsetAt & i6, obj));
                                    break;
                                }
                            case 16:
                                if (!isFieldPresent(length2, obj)) {
                                    break;
                                } else {
                                    codedOutputStreamWriter.writeSInt64(i7, UnsafeUtil.getLong(typeAndOffsetAt & i6, obj));
                                    break;
                                }
                            case 17:
                                if (!isFieldPresent(length2, obj)) {
                                    break;
                                } else {
                                    codedOutputStreamWriter.writeGroup(i7, getMessageFieldSchema(length2), UnsafeUtil.getObject(typeAndOffsetAt & i6, obj));
                                    break;
                                }
                            case 18:
                                SchemaUtil.writeDoubleList(iArr[length2], (List) UnsafeUtil.getObject(typeAndOffsetAt & i6, obj), codedOutputStreamWriter, false);
                                break;
                            case 19:
                                SchemaUtil.writeFloatList(iArr[length2], (List) UnsafeUtil.getObject(typeAndOffsetAt & i6, obj), codedOutputStreamWriter, false);
                                break;
                            case 20:
                                SchemaUtil.writeInt64List(iArr[length2], (List) UnsafeUtil.getObject(typeAndOffsetAt & i6, obj), codedOutputStreamWriter, false);
                                break;
                            case 21:
                                SchemaUtil.writeUInt64List(iArr[length2], (List) UnsafeUtil.getObject(typeAndOffsetAt & i6, obj), codedOutputStreamWriter, false);
                                break;
                            case 22:
                                SchemaUtil.writeInt32List(iArr[length2], (List) UnsafeUtil.getObject(typeAndOffsetAt & i6, obj), codedOutputStreamWriter, false);
                                break;
                            case 23:
                                SchemaUtil.writeFixed64List(iArr[length2], (List) UnsafeUtil.getObject(typeAndOffsetAt & i6, obj), codedOutputStreamWriter, false);
                                break;
                            case 24:
                                SchemaUtil.writeFixed32List(iArr[length2], (List) UnsafeUtil.getObject(typeAndOffsetAt & i6, obj), codedOutputStreamWriter, false);
                                break;
                            case 25:
                                SchemaUtil.writeBoolList(iArr[length2], (List) UnsafeUtil.getObject(typeAndOffsetAt & i6, obj), codedOutputStreamWriter, false);
                                break;
                            case 26:
                                SchemaUtil.writeStringList(iArr[length2], (List) UnsafeUtil.getObject(typeAndOffsetAt & i6, obj), codedOutputStreamWriter);
                                break;
                            case 27:
                                SchemaUtil.writeMessageList(iArr[length2], (List) UnsafeUtil.getObject(typeAndOffsetAt & i6, obj), codedOutputStreamWriter, getMessageFieldSchema(length2));
                                break;
                            case 28:
                                SchemaUtil.writeBytesList(iArr[length2], (List) UnsafeUtil.getObject(typeAndOffsetAt & i6, obj), codedOutputStreamWriter);
                                break;
                            case 29:
                                SchemaUtil.writeUInt32List(iArr[length2], (List) UnsafeUtil.getObject(typeAndOffsetAt & i6, obj), codedOutputStreamWriter, false);
                                break;
                            case 30:
                                SchemaUtil.writeEnumList(iArr[length2], (List) UnsafeUtil.getObject(typeAndOffsetAt & i6, obj), codedOutputStreamWriter, false);
                                break;
                            case 31:
                                SchemaUtil.writeSFixed32List(iArr[length2], (List) UnsafeUtil.getObject(typeAndOffsetAt & i6, obj), codedOutputStreamWriter, false);
                                break;
                            case 32:
                                SchemaUtil.writeSFixed64List(iArr[length2], (List) UnsafeUtil.getObject(typeAndOffsetAt & i6, obj), codedOutputStreamWriter, false);
                                break;
                            case 33:
                                SchemaUtil.writeSInt32List(iArr[length2], (List) UnsafeUtil.getObject(typeAndOffsetAt & i6, obj), codedOutputStreamWriter, false);
                                break;
                            case 34:
                                SchemaUtil.writeSInt64List(iArr[length2], (List) UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj), codedOutputStreamWriter, false);
                                break;
                            case 35:
                                SchemaUtil.writeDoubleList(iArr[length2], (List) UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj), codedOutputStreamWriter, true);
                                break;
                            case 36:
                                SchemaUtil.writeFloatList(iArr[length2], (List) UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj), codedOutputStreamWriter, true);
                                break;
                            case 37:
                                SchemaUtil.writeInt64List(iArr[length2], (List) UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj), codedOutputStreamWriter, true);
                                break;
                            case 38:
                                SchemaUtil.writeUInt64List(iArr[length2], (List) UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj), codedOutputStreamWriter, true);
                                break;
                            case 39:
                                SchemaUtil.writeInt32List(iArr[length2], (List) UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj), codedOutputStreamWriter, true);
                                break;
                            case 40:
                                SchemaUtil.writeFixed64List(iArr[length2], (List) UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj), codedOutputStreamWriter, true);
                                break;
                            case 41:
                                SchemaUtil.writeFixed32List(iArr[length2], (List) UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj), codedOutputStreamWriter, true);
                                break;
                            case 42:
                                SchemaUtil.writeBoolList(iArr[length2], (List) UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj), codedOutputStreamWriter, true);
                                break;
                            case 43:
                                SchemaUtil.writeUInt32List(iArr[length2], (List) UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj), codedOutputStreamWriter, true);
                                break;
                            case 44:
                                SchemaUtil.writeEnumList(iArr[length2], (List) UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj), codedOutputStreamWriter, true);
                                break;
                            case 45:
                                SchemaUtil.writeSFixed32List(iArr[length2], (List) UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj), codedOutputStreamWriter, true);
                                break;
                            case 46:
                                SchemaUtil.writeSFixed64List(iArr[length2], (List) UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj), codedOutputStreamWriter, true);
                                break;
                            case 47:
                                SchemaUtil.writeSInt32List(iArr[length2], (List) UnsafeUtil.getObject(typeAndOffsetAt & i6, obj), codedOutputStreamWriter, true);
                                break;
                            case 48:
                                SchemaUtil.writeSInt64List(iArr[length2], (List) UnsafeUtil.getObject(typeAndOffsetAt & i6, obj), codedOutputStreamWriter, true);
                                break;
                            case 49:
                                SchemaUtil.writeGroupList(iArr[length2], (List) UnsafeUtil.getObject(typeAndOffsetAt & i6, obj), codedOutputStreamWriter, getMessageFieldSchema(length2));
                                break;
                            case 50:
                                writeMapHelper(codedOutputStreamWriter, i7, UnsafeUtil.getObject(typeAndOffsetAt & i6, obj), length2);
                                break;
                            case 51:
                                if (!isOneofPresent(i7, length2, obj)) {
                                    break;
                                } else {
                                    codedOutputStreamWriter.writeDouble(((Double) UnsafeUtil.getObject(typeAndOffsetAt & i6, obj)).doubleValue(), i7);
                                    break;
                                }
                            case 52:
                                if (!isOneofPresent(i7, length2, obj)) {
                                    break;
                                } else {
                                    codedOutputStreamWriter.writeFloat(((Float) UnsafeUtil.getObject(typeAndOffsetAt & i6, obj)).floatValue(), i7);
                                    break;
                                }
                            case 53:
                                if (!isOneofPresent(i7, length2, obj)) {
                                    break;
                                } else {
                                    codedOutputStreamWriter.writeInt64(i7, oneofLongAt(typeAndOffsetAt & i6, obj));
                                    break;
                                }
                            case 54:
                                if (!isOneofPresent(i7, length2, obj)) {
                                    break;
                                } else {
                                    codedOutputStreamWriter.writeUInt64(i7, oneofLongAt(typeAndOffsetAt & i6, obj));
                                    break;
                                }
                            case 55:
                                if (!isOneofPresent(i7, length2, obj)) {
                                    break;
                                } else {
                                    codedOutputStreamWriter.writeInt32(i7, oneofIntAt(typeAndOffsetAt & i6, obj));
                                    break;
                                }
                            case 56:
                                if (!isOneofPresent(i7, length2, obj)) {
                                    break;
                                } else {
                                    codedOutputStreamWriter.writeFixed64(i7, oneofLongAt(typeAndOffsetAt & i6, obj));
                                    break;
                                }
                            case 57:
                                if (!isOneofPresent(i7, length2, obj)) {
                                    break;
                                } else {
                                    codedOutputStreamWriter.writeFixed32(i7, oneofIntAt(typeAndOffsetAt & i6, obj));
                                    break;
                                }
                            case 58:
                                if (!isOneofPresent(i7, length2, obj)) {
                                    break;
                                } else {
                                    codedOutputStreamWriter.writeBool(i7, ((Boolean) UnsafeUtil.getObject(typeAndOffsetAt & i6, obj)).booleanValue());
                                    break;
                                }
                            case 59:
                                if (!isOneofPresent(i7, length2, obj)) {
                                    break;
                                } else {
                                    writeString(i7, UnsafeUtil.getObject(typeAndOffsetAt & i6, obj), codedOutputStreamWriter);
                                    break;
                                }
                            case 60:
                                if (!isOneofPresent(i7, length2, obj)) {
                                    break;
                                } else {
                                    codedOutputStreamWriter.writeMessage(i7, getMessageFieldSchema(length2), UnsafeUtil.getObject(typeAndOffsetAt & i6, obj));
                                    break;
                                }
                            case 61:
                                if (!isOneofPresent(i7, length2, obj)) {
                                    break;
                                } else {
                                    codedOutputStreamWriter.writeBytes(i7, (ByteString) UnsafeUtil.getObject(typeAndOffsetAt & i6, obj));
                                    break;
                                }
                            case 62:
                                if (!isOneofPresent(i7, length2, obj)) {
                                    break;
                                } else {
                                    codedOutputStreamWriter.writeUInt32(i7, oneofIntAt(typeAndOffsetAt & i6, obj));
                                    break;
                                }
                            case 63:
                                if (!isOneofPresent(i7, length2, obj)) {
                                    break;
                                } else {
                                    codedOutputStreamWriter.writeEnum(i7, oneofIntAt(typeAndOffsetAt & i6, obj));
                                    break;
                                }
                            case 64:
                                if (!isOneofPresent(i7, length2, obj)) {
                                    break;
                                } else {
                                    codedOutputStreamWriter.writeSFixed32(i7, oneofIntAt(typeAndOffsetAt & i6, obj));
                                    break;
                                }
                            case 65:
                                if (!isOneofPresent(i7, length2, obj)) {
                                    break;
                                } else {
                                    codedOutputStreamWriter.writeSFixed64(i7, oneofLongAt(typeAndOffsetAt & i6, obj));
                                    break;
                                }
                            case 66:
                                if (!isOneofPresent(i7, length2, obj)) {
                                    break;
                                } else {
                                    codedOutputStreamWriter.writeSInt32(i7, oneofIntAt(typeAndOffsetAt & i6, obj));
                                    break;
                                }
                            case 67:
                                if (!isOneofPresent(i7, length2, obj)) {
                                    break;
                                } else {
                                    codedOutputStreamWriter.writeSInt64(i7, oneofLongAt(typeAndOffsetAt & i6, obj));
                                    break;
                                }
                            case 68:
                                if (!isOneofPresent(i7, length2, obj)) {
                                    break;
                                } else {
                                    codedOutputStreamWriter.writeGroup(i7, getMessageFieldSchema(length2), UnsafeUtil.getObject(typeAndOffsetAt & i6, obj));
                                    break;
                                }
                        }
                        length2 -= 3;
                        i5 = 267386880;
                        i6 = 1048575;
                        entry4 = null;
                    }
                    while (entry3 != null) {
                        extensionSchema.serializeExtension(codedOutputStreamWriter, entry3);
                        entry3 = it2.hasNext() ? (Map.Entry) it2.next() : null;
                    }
                    return;
                }
            }
            entry3 = null;
            it2 = null;
            length2 = iArr.length - 3;
            while (length2 >= 0) {
            }
            while (entry3 != null) {
            }
            return;
        }
        if (this.proto3) {
            writeFieldsInAscendingOrderProto3(obj, codedOutputStreamWriter);
            return;
        }
        if (z) {
            FieldSet extensions2 = extensionSchema.getExtensions(obj);
            if (!extensions2.fields.isEmpty()) {
                it = extensions2.iterator();
                entry = (Map.Entry) it.next();
                length = iArr.length;
                int i8 = 0;
                int i9 = 1048575;
                i = 0;
                while (i < length) {
                    int typeAndOffsetAt2 = typeAndOffsetAt(i);
                    int i10 = iArr[i];
                    int i11 = (typeAndOffsetAt2 & 267386880) >>> 20;
                    Unsafe unsafe = UNSAFE;
                    Map.Entry entry5 = entry;
                    if (i11 <= 17) {
                        int i12 = iArr[i + 2];
                        i2 = length;
                        int i13 = i12 & 1048575;
                        if (i13 != i9) {
                            i8 = unsafe.getInt(obj, i13);
                            i9 = i13;
                        }
                        i3 = 1 << (i12 >>> 20);
                        entry2 = entry5;
                    } else {
                        i2 = length;
                        entry2 = entry5;
                        i3 = 0;
                    }
                    while (true) {
                        i4 = i9;
                        if (entry2 != null && extensionSchema.extensionNumber(entry2) <= i10) {
                            extensionSchema.serializeExtension(codedOutputStreamWriter, entry2);
                            if (it.hasNext()) {
                                entry2 = (Map.Entry) it.next();
                                i9 = i4;
                            } else {
                                i9 = i4;
                                entry2 = null;
                            }
                        }
                    }
                    Iterator it3 = it;
                    Map.Entry entry6 = entry2;
                    long j = typeAndOffsetAt2 & 1048575;
                    switch (i11) {
                        case 0:
                            if ((i3 & i8) == 0) {
                                break;
                            } else {
                                codedOutputStreamWriter.writeDouble(UnsafeUtil.getDouble(j, obj), i10);
                                break;
                            }
                        case 1:
                            if ((i3 & i8) == 0) {
                                break;
                            } else {
                                codedOutputStreamWriter.writeFloat(UnsafeUtil.getFloat(j, obj), i10);
                                break;
                            }
                        case 2:
                            if ((i3 & i8) == 0) {
                                break;
                            } else {
                                codedOutputStreamWriter.writeInt64(i10, unsafe.getLong(obj, j));
                                break;
                            }
                        case 3:
                            if ((i3 & i8) == 0) {
                                break;
                            } else {
                                codedOutputStreamWriter.writeUInt64(i10, unsafe.getLong(obj, j));
                                break;
                            }
                        case 4:
                            if ((i3 & i8) == 0) {
                                break;
                            } else {
                                codedOutputStreamWriter.writeInt32(i10, unsafe.getInt(obj, j));
                                break;
                            }
                        case 5:
                            if ((i3 & i8) == 0) {
                                break;
                            } else {
                                codedOutputStreamWriter.writeFixed64(i10, unsafe.getLong(obj, j));
                                break;
                            }
                        case 6:
                            if ((i3 & i8) == 0) {
                                break;
                            } else {
                                codedOutputStreamWriter.writeFixed32(i10, unsafe.getInt(obj, j));
                                break;
                            }
                        case 7:
                            if ((i3 & i8) == 0) {
                                break;
                            } else {
                                codedOutputStreamWriter.writeBool(i10, UnsafeUtil.getBoolean(j, obj));
                                break;
                            }
                        case 8:
                            if ((i3 & i8) == 0) {
                                break;
                            } else {
                                writeString(i10, unsafe.getObject(obj, j), codedOutputStreamWriter);
                                break;
                            }
                        case 9:
                            if ((i3 & i8) == 0) {
                                break;
                            } else {
                                codedOutputStreamWriter.writeMessage(i10, getMessageFieldSchema(i), unsafe.getObject(obj, j));
                                break;
                            }
                        case 10:
                            if ((i3 & i8) == 0) {
                                break;
                            } else {
                                codedOutputStreamWriter.writeBytes(i10, (ByteString) unsafe.getObject(obj, j));
                                break;
                            }
                        case 11:
                            if ((i3 & i8) == 0) {
                                break;
                            } else {
                                codedOutputStreamWriter.writeUInt32(i10, unsafe.getInt(obj, j));
                                break;
                            }
                        case 12:
                            if ((i3 & i8) == 0) {
                                break;
                            } else {
                                codedOutputStreamWriter.writeEnum(i10, unsafe.getInt(obj, j));
                                break;
                            }
                        case 13:
                            if ((i3 & i8) == 0) {
                                break;
                            } else {
                                codedOutputStreamWriter.writeSFixed32(i10, unsafe.getInt(obj, j));
                                break;
                            }
                        case 14:
                            if ((i3 & i8) == 0) {
                                break;
                            } else {
                                codedOutputStreamWriter.writeSFixed64(i10, unsafe.getLong(obj, j));
                                break;
                            }
                        case 15:
                            if ((i3 & i8) == 0) {
                                break;
                            } else {
                                codedOutputStreamWriter.writeSInt32(i10, unsafe.getInt(obj, j));
                                break;
                            }
                        case 16:
                            if ((i3 & i8) == 0) {
                                break;
                            } else {
                                codedOutputStreamWriter.writeSInt64(i10, unsafe.getLong(obj, j));
                                break;
                            }
                        case 17:
                            if ((i3 & i8) == 0) {
                                break;
                            } else {
                                codedOutputStreamWriter.writeGroup(i10, getMessageFieldSchema(i), unsafe.getObject(obj, j));
                                break;
                            }
                        case 18:
                            SchemaUtil.writeDoubleList(iArr[i], (List) unsafe.getObject(obj, j), codedOutputStreamWriter, false);
                            break;
                        case 19:
                            SchemaUtil.writeFloatList(iArr[i], (List) unsafe.getObject(obj, j), codedOutputStreamWriter, false);
                            break;
                        case 20:
                            SchemaUtil.writeInt64List(iArr[i], (List) unsafe.getObject(obj, j), codedOutputStreamWriter, false);
                            break;
                        case 21:
                            SchemaUtil.writeUInt64List(iArr[i], (List) unsafe.getObject(obj, j), codedOutputStreamWriter, false);
                            break;
                        case 22:
                            SchemaUtil.writeInt32List(iArr[i], (List) unsafe.getObject(obj, j), codedOutputStreamWriter, false);
                            break;
                        case 23:
                            SchemaUtil.writeFixed64List(iArr[i], (List) unsafe.getObject(obj, j), codedOutputStreamWriter, false);
                            break;
                        case 24:
                            SchemaUtil.writeFixed32List(iArr[i], (List) unsafe.getObject(obj, j), codedOutputStreamWriter, false);
                            break;
                        case 25:
                            SchemaUtil.writeBoolList(iArr[i], (List) unsafe.getObject(obj, j), codedOutputStreamWriter, false);
                            break;
                        case 26:
                            SchemaUtil.writeStringList(iArr[i], (List) unsafe.getObject(obj, j), codedOutputStreamWriter);
                            break;
                        case 27:
                            SchemaUtil.writeMessageList(iArr[i], (List) unsafe.getObject(obj, j), codedOutputStreamWriter, getMessageFieldSchema(i));
                            break;
                        case 28:
                            SchemaUtil.writeBytesList(iArr[i], (List) unsafe.getObject(obj, j), codedOutputStreamWriter);
                            break;
                        case 29:
                            SchemaUtil.writeUInt32List(iArr[i], (List) unsafe.getObject(obj, j), codedOutputStreamWriter, false);
                            break;
                        case 30:
                            SchemaUtil.writeEnumList(iArr[i], (List) unsafe.getObject(obj, j), codedOutputStreamWriter, false);
                            break;
                        case 31:
                            SchemaUtil.writeSFixed32List(iArr[i], (List) unsafe.getObject(obj, j), codedOutputStreamWriter, false);
                            break;
                        case 32:
                            SchemaUtil.writeSFixed64List(iArr[i], (List) unsafe.getObject(obj, j), codedOutputStreamWriter, false);
                            break;
                        case 33:
                            SchemaUtil.writeSInt32List(iArr[i], (List) unsafe.getObject(obj, j), codedOutputStreamWriter, false);
                            break;
                        case 34:
                            SchemaUtil.writeSInt64List(iArr[i], (List) unsafe.getObject(obj, j), codedOutputStreamWriter, false);
                            break;
                        case 35:
                            SchemaUtil.writeDoubleList(iArr[i], (List) unsafe.getObject(obj, j), codedOutputStreamWriter, true);
                            break;
                        case 36:
                            SchemaUtil.writeFloatList(iArr[i], (List) unsafe.getObject(obj, j), codedOutputStreamWriter, true);
                            break;
                        case 37:
                            SchemaUtil.writeInt64List(iArr[i], (List) unsafe.getObject(obj, j), codedOutputStreamWriter, true);
                            break;
                        case 38:
                            SchemaUtil.writeUInt64List(iArr[i], (List) unsafe.getObject(obj, j), codedOutputStreamWriter, true);
                            break;
                        case 39:
                            SchemaUtil.writeInt32List(iArr[i], (List) unsafe.getObject(obj, j), codedOutputStreamWriter, true);
                            break;
                        case 40:
                            SchemaUtil.writeFixed64List(iArr[i], (List) unsafe.getObject(obj, j), codedOutputStreamWriter, true);
                            break;
                        case 41:
                            SchemaUtil.writeFixed32List(iArr[i], (List) unsafe.getObject(obj, j), codedOutputStreamWriter, true);
                            break;
                        case 42:
                            SchemaUtil.writeBoolList(iArr[i], (List) unsafe.getObject(obj, j), codedOutputStreamWriter, true);
                            break;
                        case 43:
                            SchemaUtil.writeUInt32List(iArr[i], (List) unsafe.getObject(obj, j), codedOutputStreamWriter, true);
                            break;
                        case 44:
                            SchemaUtil.writeEnumList(iArr[i], (List) unsafe.getObject(obj, j), codedOutputStreamWriter, true);
                            break;
                        case 45:
                            SchemaUtil.writeSFixed32List(iArr[i], (List) unsafe.getObject(obj, j), codedOutputStreamWriter, true);
                            break;
                        case 46:
                            SchemaUtil.writeSFixed64List(iArr[i], (List) unsafe.getObject(obj, j), codedOutputStreamWriter, true);
                            break;
                        case 47:
                            SchemaUtil.writeSInt32List(iArr[i], (List) unsafe.getObject(obj, j), codedOutputStreamWriter, true);
                            break;
                        case 48:
                            SchemaUtil.writeSInt64List(iArr[i], (List) unsafe.getObject(obj, j), codedOutputStreamWriter, true);
                            break;
                        case 49:
                            SchemaUtil.writeGroupList(iArr[i], (List) unsafe.getObject(obj, j), codedOutputStreamWriter, getMessageFieldSchema(i));
                            break;
                        case 50:
                            writeMapHelper(codedOutputStreamWriter, i10, unsafe.getObject(obj, j), i);
                            break;
                        case 51:
                            if (isOneofPresent(i10, i, obj)) {
                                codedOutputStreamWriter.writeDouble(((Double) UnsafeUtil.getObject(j, obj)).doubleValue(), i10);
                            }
                            break;
                        case 52:
                            if (isOneofPresent(i10, i, obj)) {
                                codedOutputStreamWriter.writeFloat(((Float) UnsafeUtil.getObject(j, obj)).floatValue(), i10);
                            }
                            break;
                        case 53:
                            if (isOneofPresent(i10, i, obj)) {
                                codedOutputStreamWriter.writeInt64(i10, oneofLongAt(j, obj));
                            }
                            break;
                        case 54:
                            if (isOneofPresent(i10, i, obj)) {
                                codedOutputStreamWriter.writeUInt64(i10, oneofLongAt(j, obj));
                            }
                            break;
                        case 55:
                            if (isOneofPresent(i10, i, obj)) {
                                codedOutputStreamWriter.writeInt32(i10, oneofIntAt(j, obj));
                            }
                            break;
                        case 56:
                            if (isOneofPresent(i10, i, obj)) {
                                codedOutputStreamWriter.writeFixed64(i10, oneofLongAt(j, obj));
                            }
                            break;
                        case 57:
                            if (isOneofPresent(i10, i, obj)) {
                                codedOutputStreamWriter.writeFixed32(i10, oneofIntAt(j, obj));
                            }
                            break;
                        case 58:
                            if (isOneofPresent(i10, i, obj)) {
                                codedOutputStreamWriter.writeBool(i10, ((Boolean) UnsafeUtil.getObject(j, obj)).booleanValue());
                            }
                            break;
                        case 59:
                            if (isOneofPresent(i10, i, obj)) {
                                writeString(i10, unsafe.getObject(obj, j), codedOutputStreamWriter);
                            }
                            break;
                        case 60:
                            if (isOneofPresent(i10, i, obj)) {
                                codedOutputStreamWriter.writeMessage(i10, getMessageFieldSchema(i), unsafe.getObject(obj, j));
                            }
                            break;
                        case 61:
                            if (isOneofPresent(i10, i, obj)) {
                                codedOutputStreamWriter.writeBytes(i10, (ByteString) unsafe.getObject(obj, j));
                            }
                            break;
                        case 62:
                            if (isOneofPresent(i10, i, obj)) {
                                codedOutputStreamWriter.writeUInt32(i10, oneofIntAt(j, obj));
                            }
                            break;
                        case 63:
                            if (isOneofPresent(i10, i, obj)) {
                                codedOutputStreamWriter.writeEnum(i10, oneofIntAt(j, obj));
                            }
                            break;
                        case 64:
                            if (isOneofPresent(i10, i, obj)) {
                                codedOutputStreamWriter.writeSFixed32(i10, oneofIntAt(j, obj));
                            }
                            break;
                        case 65:
                            if (isOneofPresent(i10, i, obj)) {
                                codedOutputStreamWriter.writeSFixed64(i10, oneofLongAt(j, obj));
                            }
                            break;
                        case 66:
                            if (isOneofPresent(i10, i, obj)) {
                                codedOutputStreamWriter.writeSInt32(i10, oneofIntAt(j, obj));
                            }
                            break;
                        case 67:
                            if (isOneofPresent(i10, i, obj)) {
                                codedOutputStreamWriter.writeSInt64(i10, oneofLongAt(j, obj));
                            }
                            break;
                        case 68:
                            if (isOneofPresent(i10, i, obj)) {
                                codedOutputStreamWriter.writeGroup(i10, getMessageFieldSchema(i), unsafe.getObject(obj, j));
                            }
                            break;
                    }
                    i += 3;
                    it = it3;
                    i9 = i4;
                    length = i2;
                    entry = entry6;
                }
                Iterator it4 = it;
                while (entry != null) {
                    extensionSchema.serializeExtension(codedOutputStreamWriter, entry);
                    entry = it4.hasNext() ? (Map.Entry) it4.next() : null;
                }
                unknownFieldSchema.writeTo(unknownFieldSchema.getFromMessage(obj), codedOutputStreamWriter);
            }
        }
        it = null;
        entry = null;
        length = iArr.length;
        int i82 = 0;
        int i92 = 1048575;
        i = 0;
        while (i < length) {
        }
        Iterator it42 = it;
        while (entry != null) {
        }
        unknownFieldSchema.writeTo(unknownFieldSchema.getFromMessage(obj), codedOutputStreamWriter);
    }

    /*  JADX ERROR: Type inference failed
        jadx.core.utils.exceptions.JadxOverflowException: Type inference error: updates count limit reached
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:77)
        */
    @Override // com.google.protobuf.Schema
    public final void mergeFrom(java.lang.Object r25, com.google.protobuf.Reader r26, com.google.protobuf.ExtensionRegistryLite r27) {
        /*
            Method dump skipped, instructions count: 2046
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.MessageSchema.mergeFrom(java.lang.Object, com.google.protobuf.Reader, com.google.protobuf.ExtensionRegistryLite):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:137:0x02a9, code lost:
    
        if (r0 != r32) goto L120;
     */
    /* JADX WARN: Code restructure failed: missing block: B:138:0x02ad, code lost:
    
        r4 = r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:139:0x02ff, code lost:
    
        r2 = r19;
        r6 = r25;
     */
    /* JADX WARN: Code restructure failed: missing block: B:145:0x02dd, code lost:
    
        if (r0 != r15) goto L120;
     */
    /* JADX WARN: Code restructure failed: missing block: B:147:0x02fd, code lost:
    
        if (r0 != r15) goto L120;
     */
    /* JADX WARN: Failed to find 'out' block for switch in B:26:0x00a0. Please report as an issue. */
    @Override // com.google.protobuf.Schema
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void mergeFrom(Object obj, byte[] bArr, int i, int i2, ArrayDecoders.Registers registers) {
        int i3;
        int i4;
        int slowPositionForFieldNumber;
        int i5;
        int i6;
        int i7;
        Unsafe unsafe;
        int i8;
        int i9;
        int i10;
        int i11;
        int i12;
        int i13;
        int i14;
        int decodeVarint64;
        int decodeVarint32;
        int i15;
        int i16;
        MessageSchema messageSchema = this;
        Object obj2 = obj;
        byte[] bArr2 = bArr;
        int i17 = i2;
        ArrayDecoders.Registers registers2 = registers;
        if (messageSchema.proto3) {
            checkMutable(obj);
            Unsafe unsafe2 = UNSAFE;
            int i18 = 0;
            int i19 = i;
            int i20 = 0;
            int i21 = 0;
            int i22 = -1;
            int i23 = 1048575;
            while (i19 < i17) {
                int i24 = i19 + 1;
                byte b = bArr2[i19];
                if (b < 0) {
                    i4 = ArrayDecoders.decodeVarint32(b, bArr2, i24, registers2);
                    i3 = registers2.int1;
                } else {
                    i3 = b;
                    i4 = i24;
                }
                int i25 = i3 >>> 3;
                int i26 = i3 & 7;
                int i27 = messageSchema.maxFieldNumber;
                int i28 = messageSchema.minFieldNumber;
                if (i25 > i22) {
                    int i29 = i20 / 3;
                    if (i25 >= i28 && i25 <= i27) {
                        slowPositionForFieldNumber = messageSchema.slowPositionForFieldNumber(i25, i29);
                    }
                    slowPositionForFieldNumber = -1;
                } else {
                    if (i25 >= i28 && i25 <= i27) {
                        slowPositionForFieldNumber = messageSchema.slowPositionForFieldNumber(i25, i18);
                    }
                    slowPositionForFieldNumber = -1;
                }
                int i30 = slowPositionForFieldNumber;
                if (i30 == -1) {
                    i5 = i25;
                    i6 = i4;
                    i7 = i23;
                    unsafe = unsafe2;
                    i8 = i18;
                } else {
                    int[] iArr = messageSchema.buffer;
                    int i31 = iArr[i30 + 1];
                    int i32 = (i31 & 267386880) >>> 20;
                    int i33 = i4;
                    long j = i31 & 1048575;
                    if (i32 <= 17) {
                        int i34 = iArr[i30 + 2];
                        int i35 = 1 << (i34 >>> 20);
                        int i36 = 1048575;
                        int i37 = i34 & 1048575;
                        if (i37 != i23) {
                            if (i23 != 1048575) {
                                unsafe2.putInt(obj2, i23, i21);
                                i36 = 1048575;
                            }
                            if (i37 != i36) {
                                i21 = unsafe2.getInt(obj2, i37);
                            }
                            i12 = i21;
                            i13 = i37;
                        } else {
                            i12 = i21;
                            i13 = i23;
                        }
                        switch (i32) {
                            case 0:
                                i14 = i33;
                                i5 = i25;
                                if (i26 != 1) {
                                    i7 = i13;
                                    unsafe = unsafe2;
                                    i6 = i14;
                                    i21 = i12;
                                    i8 = 0;
                                    i18 = i30;
                                    break;
                                } else {
                                    UnsafeUtil.MEMORY_ACCESSOR.putDouble(obj, j, Double.longBitsToDouble(ArrayDecoders.decodeFixed64(i14, bArr2)));
                                    decodeVarint32 = i14 + 8;
                                    decodeVarint64 = decodeVarint32;
                                    i16 = i12 | i35;
                                    i23 = i13;
                                    i21 = i16;
                                    i19 = decodeVarint64;
                                    i17 = i2;
                                    registers2 = registers;
                                    i20 = i30;
                                    i22 = i5;
                                    i18 = 0;
                                    break;
                                }
                            case 1:
                                i14 = i33;
                                i5 = i25;
                                if (i26 != 5) {
                                    i7 = i13;
                                    unsafe = unsafe2;
                                    i6 = i14;
                                    i21 = i12;
                                    i8 = 0;
                                    i18 = i30;
                                    break;
                                } else {
                                    UnsafeUtil.MEMORY_ACCESSOR.putFloat(obj2, j, Float.intBitsToFloat(ArrayDecoders.decodeFixed32(i14, bArr2)));
                                    decodeVarint32 = i14 + 4;
                                    decodeVarint64 = decodeVarint32;
                                    i16 = i12 | i35;
                                    i23 = i13;
                                    i21 = i16;
                                    i19 = decodeVarint64;
                                    i17 = i2;
                                    registers2 = registers;
                                    i20 = i30;
                                    i22 = i5;
                                    i18 = 0;
                                    break;
                                }
                            case 2:
                            case 3:
                                i14 = i33;
                                i5 = i25;
                                if (i26 != 0) {
                                    i7 = i13;
                                    unsafe = unsafe2;
                                    i6 = i14;
                                    i21 = i12;
                                    i8 = 0;
                                    i18 = i30;
                                    break;
                                } else {
                                    decodeVarint64 = ArrayDecoders.decodeVarint64(bArr2, i14, registers);
                                    unsafe2.putLong(obj, j, registers.long1);
                                    i16 = i12 | i35;
                                    i23 = i13;
                                    i21 = i16;
                                    i19 = decodeVarint64;
                                    i17 = i2;
                                    registers2 = registers;
                                    i20 = i30;
                                    i22 = i5;
                                    i18 = 0;
                                    break;
                                }
                            case 4:
                            case 11:
                                i14 = i33;
                                i5 = i25;
                                if (i26 != 0) {
                                    i7 = i13;
                                    unsafe = unsafe2;
                                    i6 = i14;
                                    i21 = i12;
                                    i8 = 0;
                                    i18 = i30;
                                    break;
                                } else {
                                    decodeVarint32 = ArrayDecoders.decodeVarint32(bArr2, i14, registers);
                                    unsafe2.putInt(obj2, j, registers.int1);
                                    decodeVarint64 = decodeVarint32;
                                    i16 = i12 | i35;
                                    i23 = i13;
                                    i21 = i16;
                                    i19 = decodeVarint64;
                                    i17 = i2;
                                    registers2 = registers;
                                    i20 = i30;
                                    i22 = i5;
                                    i18 = 0;
                                    break;
                                }
                            case 5:
                            case 14:
                                i15 = i33;
                                i5 = i25;
                                if (i26 != 1) {
                                    i14 = i15;
                                    i7 = i13;
                                    unsafe = unsafe2;
                                    i6 = i14;
                                    i21 = i12;
                                    i8 = 0;
                                    i18 = i30;
                                    break;
                                } else {
                                    i14 = i15;
                                    unsafe2.putLong(obj, j, ArrayDecoders.decodeFixed64(i15, bArr2));
                                    decodeVarint32 = i14 + 8;
                                    decodeVarint64 = decodeVarint32;
                                    i16 = i12 | i35;
                                    i23 = i13;
                                    i21 = i16;
                                    i19 = decodeVarint64;
                                    i17 = i2;
                                    registers2 = registers;
                                    i20 = i30;
                                    i22 = i5;
                                    i18 = 0;
                                    break;
                                }
                            case 6:
                            case 13:
                                i15 = i33;
                                i5 = i25;
                                if (i26 != 5) {
                                    i14 = i15;
                                    i7 = i13;
                                    unsafe = unsafe2;
                                    i6 = i14;
                                    i21 = i12;
                                    i8 = 0;
                                    i18 = i30;
                                    break;
                                } else {
                                    unsafe2.putInt(obj2, j, ArrayDecoders.decodeFixed32(i15, bArr2));
                                    i14 = i15;
                                    decodeVarint32 = i14 + 4;
                                    decodeVarint64 = decodeVarint32;
                                    i16 = i12 | i35;
                                    i23 = i13;
                                    i21 = i16;
                                    i19 = decodeVarint64;
                                    i17 = i2;
                                    registers2 = registers;
                                    i20 = i30;
                                    i22 = i5;
                                    i18 = 0;
                                    break;
                                }
                            case 7:
                                i15 = i33;
                                i5 = i25;
                                if (i26 != 0) {
                                    i14 = i15;
                                    i7 = i13;
                                    unsafe = unsafe2;
                                    i6 = i14;
                                    i21 = i12;
                                    i8 = 0;
                                    i18 = i30;
                                    break;
                                } else {
                                    decodeVarint32 = ArrayDecoders.decodeVarint64(bArr2, i15, registers);
                                    UnsafeUtil.MEMORY_ACCESSOR.putBoolean(obj2, j, registers.long1 != 0);
                                    decodeVarint64 = decodeVarint32;
                                    i16 = i12 | i35;
                                    i23 = i13;
                                    i21 = i16;
                                    i19 = decodeVarint64;
                                    i17 = i2;
                                    registers2 = registers;
                                    i20 = i30;
                                    i22 = i5;
                                    i18 = 0;
                                    break;
                                }
                            case 8:
                                i15 = i33;
                                i5 = i25;
                                if (i26 != 2) {
                                    i14 = i15;
                                    i7 = i13;
                                    unsafe = unsafe2;
                                    i6 = i14;
                                    i21 = i12;
                                    i8 = 0;
                                    i18 = i30;
                                    break;
                                } else {
                                    if ((536870912 & i31) == 0) {
                                        decodeVarint32 = ArrayDecoders.decodeString(bArr2, i15, registers);
                                    } else {
                                        decodeVarint32 = ArrayDecoders.decodeStringRequireUtf8(bArr2, i15, registers);
                                    }
                                    unsafe2.putObject(obj2, j, registers.object1);
                                    decodeVarint64 = decodeVarint32;
                                    i16 = i12 | i35;
                                    i23 = i13;
                                    i21 = i16;
                                    i19 = decodeVarint64;
                                    i17 = i2;
                                    registers2 = registers;
                                    i20 = i30;
                                    i22 = i5;
                                    i18 = 0;
                                    break;
                                }
                            case 9:
                                i15 = i33;
                                i5 = i25;
                                if (i26 != 2) {
                                    i14 = i15;
                                    i7 = i13;
                                    unsafe = unsafe2;
                                    i6 = i14;
                                    i21 = i12;
                                    i8 = 0;
                                    i18 = i30;
                                    break;
                                } else {
                                    Object mutableMessageFieldForMerge = messageSchema.mutableMessageFieldForMerge(i30, obj2);
                                    decodeVarint32 = ArrayDecoders.mergeMessageField(mutableMessageFieldForMerge, messageSchema.getMessageFieldSchema(i30), bArr, i15, i2, registers);
                                    messageSchema.storeMessageField(i30, obj2, mutableMessageFieldForMerge);
                                    decodeVarint64 = decodeVarint32;
                                    i16 = i12 | i35;
                                    i23 = i13;
                                    i21 = i16;
                                    i19 = decodeVarint64;
                                    i17 = i2;
                                    registers2 = registers;
                                    i20 = i30;
                                    i22 = i5;
                                    i18 = 0;
                                    break;
                                }
                            case 10:
                                i15 = i33;
                                i5 = i25;
                                if (i26 != 2) {
                                    i14 = i15;
                                    i7 = i13;
                                    unsafe = unsafe2;
                                    i6 = i14;
                                    i21 = i12;
                                    i8 = 0;
                                    i18 = i30;
                                    break;
                                } else {
                                    decodeVarint32 = ArrayDecoders.decodeBytes(bArr2, i15, registers);
                                    unsafe2.putObject(obj2, j, registers.object1);
                                    decodeVarint64 = decodeVarint32;
                                    i16 = i12 | i35;
                                    i23 = i13;
                                    i21 = i16;
                                    i19 = decodeVarint64;
                                    i17 = i2;
                                    registers2 = registers;
                                    i20 = i30;
                                    i22 = i5;
                                    i18 = 0;
                                    break;
                                }
                            case 12:
                                i15 = i33;
                                i5 = i25;
                                if (i26 != 0) {
                                    i14 = i15;
                                    i7 = i13;
                                    unsafe = unsafe2;
                                    i6 = i14;
                                    i21 = i12;
                                    i8 = 0;
                                    i18 = i30;
                                    break;
                                } else {
                                    decodeVarint32 = ArrayDecoders.decodeVarint32(bArr2, i15, registers);
                                    unsafe2.putInt(obj2, j, registers.int1);
                                    decodeVarint64 = decodeVarint32;
                                    i16 = i12 | i35;
                                    i23 = i13;
                                    i21 = i16;
                                    i19 = decodeVarint64;
                                    i17 = i2;
                                    registers2 = registers;
                                    i20 = i30;
                                    i22 = i5;
                                    i18 = 0;
                                    break;
                                }
                            case 15:
                                i15 = i33;
                                i5 = i25;
                                if (i26 != 0) {
                                    i14 = i15;
                                    i7 = i13;
                                    unsafe = unsafe2;
                                    i6 = i14;
                                    i21 = i12;
                                    i8 = 0;
                                    i18 = i30;
                                    break;
                                } else {
                                    decodeVarint32 = ArrayDecoders.decodeVarint32(bArr2, i15, registers);
                                    unsafe2.putInt(obj2, j, CodedInputStream.decodeZigZag32(registers.int1));
                                    decodeVarint64 = decodeVarint32;
                                    i16 = i12 | i35;
                                    i23 = i13;
                                    i21 = i16;
                                    i19 = decodeVarint64;
                                    i17 = i2;
                                    registers2 = registers;
                                    i20 = i30;
                                    i22 = i5;
                                    i18 = 0;
                                    break;
                                }
                            case 16:
                                if (i26 != 0) {
                                    i5 = i25;
                                    i14 = i33;
                                    i7 = i13;
                                    unsafe = unsafe2;
                                    i6 = i14;
                                    i21 = i12;
                                    i8 = 0;
                                    i18 = i30;
                                    break;
                                } else {
                                    decodeVarint64 = ArrayDecoders.decodeVarint64(bArr2, i33, registers);
                                    i5 = i25;
                                    unsafe2.putLong(obj, j, CodedInputStream.decodeZigZag64(registers.long1));
                                    i16 = i12 | i35;
                                    i23 = i13;
                                    i21 = i16;
                                    i19 = decodeVarint64;
                                    i17 = i2;
                                    registers2 = registers;
                                    i20 = i30;
                                    i22 = i5;
                                    i18 = 0;
                                    break;
                                }
                            default:
                                i14 = i33;
                                i5 = i25;
                                i7 = i13;
                                unsafe = unsafe2;
                                i6 = i14;
                                i21 = i12;
                                i8 = 0;
                                i18 = i30;
                                break;
                        }
                    } else {
                        i5 = i25;
                        if (i32 != 27) {
                            int i38 = i23;
                            int i39 = i21;
                            if (i32 <= 49) {
                                i10 = i30;
                                i9 = i39;
                                unsafe = unsafe2;
                                i8 = 0;
                                i7 = i38;
                                i19 = parseRepeatedField(obj, bArr, i33, i2, i3, i5, i26, i30, i31, i32, j, registers);
                            } else {
                                i9 = i39;
                                i10 = i30;
                                unsafe = unsafe2;
                                i11 = i33;
                                i7 = i38;
                                i8 = 0;
                                if (i32 != 50) {
                                    i19 = parseOneofField(obj, bArr, i11, i2, i3, i5, i26, i31, i32, j, i10, registers);
                                } else if (i26 == 2) {
                                    i19 = parseMapField(obj, bArr, i11, i2, i10, j, registers);
                                }
                            }
                            i23 = i7;
                            messageSchema = this;
                            obj2 = obj;
                            bArr2 = bArr;
                            i17 = i2;
                            registers2 = registers;
                            i22 = i5;
                            unsafe2 = unsafe;
                            i18 = i8;
                        } else if (i26 == 2) {
                            Internal.ProtobufList protobufList = (Internal.ProtobufList) unsafe2.getObject(obj2, j);
                            if (!((AbstractProtobufList) protobufList).isMutable) {
                                int size = protobufList.size();
                                protobufList = protobufList.mutableCopyWithCapacity(size == 0 ? 10 : size * 2);
                                unsafe2.putObject(obj2, j, protobufList);
                            }
                            i19 = ArrayDecoders.decodeMessageList(messageSchema.getMessageFieldSchema(i30), i3, bArr, i33, i2, protobufList, registers);
                            i21 = i21;
                            i23 = i23;
                            i17 = i2;
                            registers2 = registers;
                            i20 = i30;
                            i22 = i5;
                            i18 = 0;
                        } else {
                            i7 = i23;
                            i9 = i21;
                            i10 = i30;
                            unsafe = unsafe2;
                            i11 = i33;
                            i8 = 0;
                        }
                        int i40 = i11;
                        i6 = i40;
                        i18 = i10;
                        i21 = i9;
                    }
                }
                i19 = ArrayDecoders.decodeUnknownField(i3, bArr, i6, i2, getMutableUnknownFields(obj), registers);
                i20 = i18;
                i23 = i7;
                messageSchema = this;
                obj2 = obj;
                bArr2 = bArr;
                i17 = i2;
                registers2 = registers;
                i22 = i5;
                unsafe2 = unsafe;
                i18 = i8;
            }
            int i41 = i21;
            Unsafe unsafe3 = unsafe2;
            if (i23 != 1048575) {
                unsafe3.putInt(obj, i23, i41);
            }
            if (i19 != i2) {
                throw InvalidProtocolBufferException.parseFailure();
            }
            return;
        }
        parseProto2Message(obj, bArr, i, i2, 0, registers);
    }
}
