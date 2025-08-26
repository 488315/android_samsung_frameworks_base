package androidx.datastore.preferences.protobuf;

import androidx.activity.result.ActivityResultRegistry$register$3$$ExternalSyntheticOutline0;
import androidx.datastore.preferences.protobuf.FieldInfo;
import androidx.datastore.preferences.protobuf.GeneratedMessageLite;
import androidx.datastore.preferences.protobuf.Internal;
import androidx.datastore.preferences.protobuf.InvalidProtocolBufferException;
import androidx.datastore.preferences.protobuf.LazyField;
import androidx.datastore.preferences.protobuf.MapEntryLite;
import androidx.datastore.preferences.protobuf.SmallSortedMap;
import androidx.datastore.preferences.protobuf.UnsafeUtil;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.security.AccessController;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import sun.misc.Unsafe;

/* loaded from: classes.dex */
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
    public final int repeatedFieldOffsetStart;
    public final UnknownFieldSchema unknownFieldSchema;
    public final boolean useCachedSizeField;

    static {
        Unsafe unsafe;
        try {
            unsafe = (Unsafe) AccessController.doPrivileged(new UnsafeUtil.AnonymousClass1());
        } catch (Throwable unused) {
            unsafe = null;
        }
        UNSAFE = unsafe;
    }

    private MessageSchema(int[] iArr, Object[] objArr, int i, int i2, MessageLite messageLite, ProtoSyntax protoSyntax, boolean z, int[] iArr2, int i3, int i4, NewInstanceSchema newInstanceSchema, ListFieldSchema listFieldSchema, UnknownFieldSchema unknownFieldSchema, ExtensionSchema extensionSchema, MapFieldSchema mapFieldSchema) {
        this.buffer = iArr;
        this.objects = objArr;
        this.minFieldNumber = i;
        this.maxFieldNumber = i2;
        this.lite = messageLite instanceof GeneratedMessageLite;
        this.hasExtensions = extensionSchema != null && extensionSchema.hasExtensions(messageLite);
        this.useCachedSizeField = z;
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

    public static boolean isMutable(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof GeneratedMessageLite) {
            return ((GeneratedMessageLite) obj).isMutable();
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:130:0x02ab  */
    /* JADX WARN: Removed duplicated region for block: B:132:0x02b0  */
    /* JADX WARN: Removed duplicated region for block: B:135:0x02c8  */
    /* JADX WARN: Removed duplicated region for block: B:136:0x02cb  */
    /* JADX WARN: Removed duplicated region for block: B:173:0x0389  */
    /* JADX WARN: Removed duplicated region for block: B:189:0x03d5  */
    /* JADX WARN: Removed duplicated region for block: B:194:0x03ec  */
    /* JADX WARN: Removed duplicated region for block: B:256:0x054a  */
    /* JADX WARN: Removed duplicated region for block: B:257:0x054d  */
    /* JADX WARN: Removed duplicated region for block: B:260:0x0553  */
    /* JADX WARN: Removed duplicated region for block: B:261:0x0556  */
    /* JADX WARN: Removed duplicated region for block: B:272:0x0586  */
    /* JADX WARN: Removed duplicated region for block: B:274:0x058a  */
    /* JADX WARN: Removed duplicated region for block: B:275:0x058f  */
    /* JADX WARN: Removed duplicated region for block: B:278:0x0595  */
    /* JADX WARN: Removed duplicated region for block: B:282:0x05a8  */
    /* JADX WARN: Removed duplicated region for block: B:286:0x05b9  */
    /* JADX WARN: Removed duplicated region for block: B:291:0x05c8  */
    /* JADX WARN: Removed duplicated region for block: B:293:0x05d0  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static MessageSchema newSchema(MessageInfo messageInfo, NewInstanceSchema newInstanceSchema, ListFieldSchema listFieldSchema, UnknownFieldSchema unknownFieldSchema, ExtensionSchema extensionSchema, MapFieldSchema mapFieldSchema) {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        int iObjectFieldOffset;
        int i6;
        int iNumberOfTrailingZeros;
        int iObjectFieldOffset2;
        int i7;
        Class<?> type;
        Object obj;
        FieldType fieldType;
        int i8;
        int i9;
        int i10;
        int iCharAt;
        int i11;
        int iCharAt2;
        int i12;
        int i13;
        int i14;
        int i15;
        int i16;
        int[] iArr;
        int i17;
        int i18;
        int i19;
        char cCharAt;
        int i20;
        char cCharAt2;
        int i21;
        char cCharAt3;
        int i22;
        char cCharAt4;
        int i23;
        char cCharAt5;
        int i24;
        char cCharAt6;
        int i25;
        char cCharAt7;
        int i26;
        char cCharAt8;
        int i27;
        int i28;
        int i29;
        int i30;
        Object[] objArr;
        int i31;
        int iObjectFieldOffset3;
        int i32;
        int i33;
        int iObjectFieldOffset4;
        int i34;
        Field fieldReflectField;
        char cCharAt9;
        int i35;
        int i36;
        int i37;
        Object obj2;
        Field fieldReflectField2;
        Object obj3;
        Field fieldReflectField3;
        int i38;
        char cCharAt10;
        int i39;
        char cCharAt11;
        int i40;
        char cCharAt12;
        int i41;
        char cCharAt13;
        boolean z = messageInfo instanceof RawMessageInfo;
        int[] iArr2 = EMPTY_INT_ARRAY;
        int i42 = 0;
        if (!z) {
            int i43 = 2;
            int i44 = 1;
            StructuralMessageInfo structuralMessageInfo = (StructuralMessageInfo) messageInfo;
            FieldInfo[] fieldInfoArr = structuralMessageInfo.fields;
            if (fieldInfoArr.length == 0) {
                i = 0;
                i2 = 0;
            } else {
                i = fieldInfoArr[0].fieldNumber;
                i2 = fieldInfoArr[fieldInfoArr.length - 1].fieldNumber;
            }
            int length = fieldInfoArr.length;
            int[] iArr3 = new int[length * 3];
            Object[] objArr2 = new Object[length * 2];
            int i45 = 0;
            int i46 = 0;
            for (FieldInfo fieldInfo : fieldInfoArr) {
                FieldType fieldType2 = fieldInfo.type;
                if (fieldType2 == FieldType.MAP) {
                    i45++;
                } else if (fieldType2.id() >= 18 && fieldInfo.type.id() <= 49) {
                    i46++;
                }
            }
            int[] iArr4 = i45 > 0 ? new int[i45] : null;
            int[] iArr5 = i46 > 0 ? new int[i46] : null;
            int[] iArr6 = structuralMessageInfo.checkInitialized;
            if (iArr6 == null) {
                iArr6 = iArr2;
            }
            int i47 = 0;
            int i48 = 0;
            int i49 = 0;
            int i50 = 0;
            int i51 = 0;
            while (i47 < fieldInfoArr.length) {
                FieldInfo fieldInfo2 = fieldInfoArr[i47];
                FieldInfo[] fieldInfoArr2 = fieldInfoArr;
                int i52 = fieldInfo2.fieldNumber;
                int[] iArr7 = iArr2;
                OneofInfo oneofInfo = fieldInfo2.oneof;
                if (oneofInfo != null) {
                    i3 = i;
                    int iId = fieldInfo2.type.id() + 51;
                    i4 = i2;
                    int iObjectFieldOffset5 = (int) UnsafeUtil.objectFieldOffset(oneofInfo.valueField);
                    iObjectFieldOffset = (int) UnsafeUtil.objectFieldOffset(oneofInfo.caseField);
                    i6 = iId;
                    i5 = iObjectFieldOffset5;
                } else {
                    i3 = i;
                    i4 = i2;
                    FieldType fieldType3 = fieldInfo2.type;
                    int iObjectFieldOffset6 = (int) UnsafeUtil.objectFieldOffset(fieldInfo2.field);
                    int iId2 = fieldType3.id();
                    if (fieldType3.isList() || fieldType3.isMap()) {
                        i5 = iObjectFieldOffset6;
                        Field field = fieldInfo2.cachedSizeField;
                        if (field == null) {
                            i6 = iId2;
                            iNumberOfTrailingZeros = 0;
                            iObjectFieldOffset = 0;
                        } else {
                            iObjectFieldOffset = (int) UnsafeUtil.objectFieldOffset(field);
                            i6 = iId2;
                        }
                    } else {
                        Field field2 = fieldInfo2.presenceField;
                        if (field2 == null) {
                            i5 = iObjectFieldOffset6;
                            iObjectFieldOffset2 = 1048575;
                        } else {
                            i5 = iObjectFieldOffset6;
                            iObjectFieldOffset2 = (int) UnsafeUtil.objectFieldOffset(field2);
                        }
                        iNumberOfTrailingZeros = Integer.numberOfTrailingZeros(fieldInfo2.presenceMask);
                        i6 = iId2;
                        iObjectFieldOffset = iObjectFieldOffset2;
                    }
                    iArr3[i48] = fieldInfo2.fieldNumber;
                    iArr3[i48 + 1] = (!fieldInfo2.enforceUtf8 ? VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS : 0) | (!fieldInfo2.required ? 268435456 : 0) | (i6 << 20) | i5;
                    iArr3[i48 + 2] = (iNumberOfTrailingZeros << 20) | iObjectFieldOffset;
                    i7 = FieldInfo.AnonymousClass1.$SwitchMap$com$google$protobuf$FieldType[fieldInfo2.type.ordinal()];
                    if (i7 != i44 || i7 == i43) {
                        Field field3 = fieldInfo2.field;
                        type = field3 == null ? field3.getType() : fieldInfo2.oneofStoredType;
                    } else {
                        type = (i7 == 3 || i7 == 4) ? fieldInfo2.messageClass : null;
                    }
                    obj = fieldInfo2.mapDefaultEntry;
                    if (obj == null) {
                        int i53 = (i48 / 3) * 2;
                        objArr2[i53] = obj;
                        if (type != null) {
                            objArr2[i53 + 1] = type;
                        }
                    } else {
                        if (type != null) {
                            i43 = 2;
                            i44 = 1;
                            objArr2[((i48 / 3) * 2) + 1] = type;
                        }
                        if (i49 < iArr6.length && iArr6[i49] == i52) {
                            iArr6[i49] = i48;
                            i49++;
                        }
                        fieldType = fieldInfo2.type;
                        if (fieldType == FieldType.MAP) {
                            iArr4[i50] = i48;
                            i50++;
                        } else {
                            if (fieldType.id() >= 18) {
                                if (fieldInfo2.type.id() <= 49) {
                                    iArr5[i51] = (int) UnsafeUtil.objectFieldOffset(fieldInfo2.field);
                                    i51++;
                                }
                            }
                            i47++;
                            i48 += 3;
                            fieldInfoArr = fieldInfoArr2;
                            iArr2 = iArr7;
                            i = i3;
                            i2 = i4;
                        }
                        i47++;
                        i48 += 3;
                        fieldInfoArr = fieldInfoArr2;
                        iArr2 = iArr7;
                        i = i3;
                        i2 = i4;
                    }
                    i43 = 2;
                    i44 = 1;
                    if (i49 < iArr6.length) {
                        iArr6[i49] = i48;
                        i49++;
                    }
                    fieldType = fieldInfo2.type;
                    if (fieldType == FieldType.MAP) {
                    }
                    i47++;
                    i48 += 3;
                    fieldInfoArr = fieldInfoArr2;
                    iArr2 = iArr7;
                    i = i3;
                    i2 = i4;
                }
                iNumberOfTrailingZeros = 0;
                iArr3[i48] = fieldInfo2.fieldNumber;
                iArr3[i48 + 1] = (!fieldInfo2.enforceUtf8 ? VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS : 0) | (!fieldInfo2.required ? 268435456 : 0) | (i6 << 20) | i5;
                iArr3[i48 + 2] = (iNumberOfTrailingZeros << 20) | iObjectFieldOffset;
                i7 = FieldInfo.AnonymousClass1.$SwitchMap$com$google$protobuf$FieldType[fieldInfo2.type.ordinal()];
                if (i7 != i44) {
                    Field field32 = fieldInfo2.field;
                    if (field32 == null) {
                    }
                }
                obj = fieldInfo2.mapDefaultEntry;
                if (obj == null) {
                }
                i43 = 2;
                i44 = 1;
                if (i49 < iArr6.length) {
                }
                fieldType = fieldInfo2.type;
                if (fieldType == FieldType.MAP) {
                }
                i47++;
                i48 += 3;
                fieldInfoArr = fieldInfoArr2;
                iArr2 = iArr7;
                i = i3;
                i2 = i4;
            }
            int[] iArr8 = iArr2;
            int i54 = i;
            int i55 = i2;
            if (iArr4 == null) {
                iArr4 = iArr8;
            }
            int[] iArr9 = iArr5 == null ? iArr8 : iArr5;
            int[] iArr10 = new int[iArr6.length + iArr4.length + iArr9.length];
            System.arraycopy(iArr6, 0, iArr10, 0, iArr6.length);
            System.arraycopy(iArr4, 0, iArr10, iArr6.length, iArr4.length);
            System.arraycopy(iArr9, 0, iArr10, iArr6.length + iArr4.length, iArr9.length);
            return new MessageSchema(iArr3, objArr2, i54, i55, structuralMessageInfo.defaultInstance, structuralMessageInfo.syntax, true, iArr10, iArr6.length, iArr6.length + iArr4.length, newInstanceSchema, listFieldSchema, unknownFieldSchema, extensionSchema, mapFieldSchema);
        }
        RawMessageInfo rawMessageInfo = (RawMessageInfo) messageInfo;
        String str = rawMessageInfo.info;
        int length2 = str.length();
        char c = 55296;
        if (str.charAt(0) >= 55296) {
            int i56 = 1;
            while (true) {
                i8 = i56 + 1;
                if (str.charAt(i56) < 55296) {
                    break;
                }
                i56 = i8;
            }
        } else {
            i8 = 1;
        }
        int i57 = i8 + 1;
        int iCharAt3 = str.charAt(i8);
        if (iCharAt3 >= 55296) {
            int i58 = iCharAt3 & 8191;
            int i59 = 13;
            while (true) {
                i41 = i57 + 1;
                cCharAt13 = str.charAt(i57);
                if (cCharAt13 < 55296) {
                    break;
                }
                i58 |= (cCharAt13 & 8191) << i59;
                i59 += 13;
                i57 = i41;
            }
            iCharAt3 = i58 | (cCharAt13 << i59);
            i57 = i41;
        }
        if (iCharAt3 == 0) {
            iArr = iArr2;
            iCharAt = 0;
            iCharAt2 = 0;
            i15 = 0;
            i12 = 0;
            i18 = 0;
            i16 = 0;
            i17 = 0;
        } else {
            int i60 = i57 + 1;
            int iCharAt4 = str.charAt(i57);
            if (iCharAt4 >= 55296) {
                int i61 = iCharAt4 & 8191;
                int i62 = 13;
                while (true) {
                    i26 = i60 + 1;
                    cCharAt8 = str.charAt(i60);
                    if (cCharAt8 < 55296) {
                        break;
                    }
                    i61 |= (cCharAt8 & 8191) << i62;
                    i62 += 13;
                    i60 = i26;
                }
                iCharAt4 = i61 | (cCharAt8 << i62);
                i60 = i26;
            }
            int i63 = i60 + 1;
            int iCharAt5 = str.charAt(i60);
            if (iCharAt5 >= 55296) {
                int i64 = iCharAt5 & 8191;
                int i65 = 13;
                while (true) {
                    i25 = i63 + 1;
                    cCharAt7 = str.charAt(i63);
                    if (cCharAt7 < 55296) {
                        break;
                    }
                    i64 |= (cCharAt7 & 8191) << i65;
                    i65 += 13;
                    i63 = i25;
                }
                iCharAt5 = i64 | (cCharAt7 << i65);
                i63 = i25;
            }
            int i66 = i63 + 1;
            int iCharAt6 = str.charAt(i63);
            if (iCharAt6 >= 55296) {
                int i67 = iCharAt6 & 8191;
                int i68 = i66;
                int i69 = 13;
                while (true) {
                    i24 = i68 + 1;
                    cCharAt6 = str.charAt(i68);
                    if (cCharAt6 < 55296) {
                        break;
                    }
                    i67 |= (cCharAt6 & 8191) << i69;
                    i69 += 13;
                    i68 = i24;
                }
                iCharAt6 = i67 | (cCharAt6 << i69);
                i9 = i24;
            } else {
                i9 = i66;
            }
            int i70 = i9 + 1;
            int iCharAt7 = str.charAt(i9);
            if (iCharAt7 >= 55296) {
                int i71 = iCharAt7 & 8191;
                int i72 = i70;
                int i73 = 13;
                while (true) {
                    i23 = i72 + 1;
                    cCharAt5 = str.charAt(i72);
                    if (cCharAt5 < 55296) {
                        break;
                    }
                    i71 |= (cCharAt5 & 8191) << i73;
                    i73 += 13;
                    i72 = i23;
                }
                iCharAt7 = i71 | (cCharAt5 << i73);
                i10 = i23;
            } else {
                i10 = i70;
            }
            int i74 = i10 + 1;
            iCharAt = str.charAt(i10);
            if (iCharAt >= 55296) {
                int i75 = iCharAt & 8191;
                int i76 = i74;
                int i77 = 13;
                while (true) {
                    i22 = i76 + 1;
                    cCharAt4 = str.charAt(i76);
                    if (cCharAt4 < 55296) {
                        break;
                    }
                    i75 |= (cCharAt4 & 8191) << i77;
                    i77 += 13;
                    i76 = i22;
                }
                iCharAt = i75 | (cCharAt4 << i77);
                i11 = i22;
            } else {
                i11 = i74;
            }
            int i78 = i11 + 1;
            iCharAt2 = str.charAt(i11);
            if (iCharAt2 >= 55296) {
                int i79 = iCharAt2 & 8191;
                i12 = 0;
                int i80 = i78;
                int i81 = 13;
                while (true) {
                    i21 = i80 + 1;
                    cCharAt3 = str.charAt(i80);
                    if (cCharAt3 < 55296) {
                        break;
                    }
                    i79 |= (cCharAt3 & 8191) << i81;
                    i81 += 13;
                    i80 = i21;
                }
                iCharAt2 = i79 | (cCharAt3 << i81);
                i13 = i21;
            } else {
                i12 = 0;
                i13 = i78;
            }
            int i82 = i13 + 1;
            int iCharAt8 = str.charAt(i13);
            if (iCharAt8 >= 55296) {
                int i83 = iCharAt8 & 8191;
                int i84 = i82;
                int i85 = 13;
                while (true) {
                    i20 = i84 + 1;
                    cCharAt2 = str.charAt(i84);
                    if (cCharAt2 < 55296) {
                        break;
                    }
                    i83 |= (cCharAt2 & 8191) << i85;
                    i85 += 13;
                    i84 = i20;
                }
                iCharAt8 = i83 | (cCharAt2 << i85);
                i14 = i20;
            } else {
                i14 = i82;
            }
            int i86 = i14 + 1;
            int iCharAt9 = str.charAt(i14);
            if (iCharAt9 >= 55296) {
                int i87 = iCharAt9 & 8191;
                int i88 = i86;
                int i89 = 13;
                while (true) {
                    i19 = i88 + 1;
                    cCharAt = str.charAt(i88);
                    if (cCharAt < 55296) {
                        break;
                    }
                    i87 |= (cCharAt & 8191) << i89;
                    i89 += 13;
                    i88 = i19;
                }
                iCharAt9 = i87 | (cCharAt << i89);
                i86 = i19;
            }
            i15 = (iCharAt4 * 2) + iCharAt5;
            i16 = iCharAt7;
            iArr = new int[iCharAt9 + iCharAt2 + iCharAt8];
            i17 = iCharAt9;
            i42 = iCharAt4;
            i18 = iCharAt6;
            i57 = i86;
        }
        Unsafe unsafe = UNSAFE;
        Class<?> cls = rawMessageInfo.defaultInstance.getClass();
        int[] iArr11 = new int[iCharAt * 3];
        Object[] objArr3 = new Object[iCharAt * 2];
        int i90 = i17 + iCharAt2;
        int i91 = i12;
        int i92 = i91;
        int i93 = i17;
        int i94 = i90;
        while (i57 < length2) {
            int i95 = i57 + 1;
            int iCharAt10 = str.charAt(i57);
            if (iCharAt10 >= c) {
                int i96 = iCharAt10 & 8191;
                int i97 = i95;
                int i98 = 13;
                while (true) {
                    i40 = i97 + 1;
                    cCharAt12 = str.charAt(i97);
                    if (cCharAt12 < c) {
                        break;
                    }
                    i96 |= (cCharAt12 & 8191) << i98;
                    i98 += 13;
                    i97 = i40;
                }
                iCharAt10 = i96 | (cCharAt12 << i98);
                i27 = i40;
            } else {
                i27 = i95;
            }
            int i99 = i27 + 1;
            int iCharAt11 = str.charAt(i27);
            if (iCharAt11 >= c) {
                int i100 = iCharAt11 & 8191;
                int i101 = i99;
                int i102 = 13;
                while (true) {
                    i39 = i101 + 1;
                    cCharAt11 = str.charAt(i101);
                    if (cCharAt11 < c) {
                        break;
                    }
                    i100 |= (cCharAt11 & 8191) << i102;
                    i102 += 13;
                    i101 = i39;
                }
                iCharAt11 = i100 | (cCharAt11 << i102);
                i28 = i39;
            } else {
                i28 = i99;
            }
            int i103 = iCharAt11 & 255;
            if ((iCharAt11 & 1024) != 0) {
                iArr[i91] = i92;
                i91++;
            }
            Object[] objArr4 = rawMessageInfo.objects;
            Object[] objArr5 = objArr3;
            if (i103 >= 51) {
                int i104 = i28 + 1;
                int iCharAt12 = str.charAt(i28);
                char c2 = 55296;
                if (iCharAt12 >= 55296) {
                    int i105 = iCharAt12 & 8191;
                    int i106 = i104;
                    int i107 = 13;
                    while (true) {
                        i38 = i106 + 1;
                        cCharAt10 = str.charAt(i106);
                        if (cCharAt10 < c2) {
                            break;
                        }
                        i105 |= (cCharAt10 & 8191) << i107;
                        i107 += 13;
                        i106 = i38;
                        c2 = 55296;
                    }
                    iCharAt12 = i105 | (cCharAt10 << i107);
                    i36 = i38;
                } else {
                    i36 = i104;
                }
                int i108 = i36;
                int i109 = i103 - 51;
                i29 = i91;
                if (i109 == 9 || i109 == 17) {
                    i37 = i15 + 1;
                    objArr5[((i92 / 3) * 2) + 1] = objArr4[i15];
                } else {
                    if (i109 == 12 && (rawMessageInfo.getSyntax().equals(ProtoSyntax.PROTO2) || (iCharAt11 & 2048) != 0)) {
                        i37 = i15 + 1;
                        objArr5[((i92 / 3) * 2) + 1] = objArr4[i15];
                    }
                    int i110 = iCharAt12 * 2;
                    obj2 = objArr4[i110];
                    if (obj2 instanceof Field) {
                        fieldReflectField2 = reflectField(cls, (String) obj2);
                        objArr4[i110] = fieldReflectField2;
                    } else {
                        fieldReflectField2 = (Field) obj2;
                    }
                    int iObjectFieldOffset7 = (int) unsafe.objectFieldOffset(fieldReflectField2);
                    int i111 = i110 + 1;
                    obj3 = objArr4[i111];
                    if (obj3 instanceof Field) {
                        fieldReflectField3 = reflectField(cls, (String) obj3);
                        objArr4[i111] = fieldReflectField3;
                    } else {
                        fieldReflectField3 = (Field) obj3;
                    }
                    int i112 = i42;
                    iObjectFieldOffset4 = (int) unsafe.objectFieldOffset(fieldReflectField3);
                    i34 = iObjectFieldOffset7;
                    i30 = i112;
                    i33 = i12;
                    i32 = i108;
                }
                i15 = i37;
                int i1102 = iCharAt12 * 2;
                obj2 = objArr4[i1102];
                if (obj2 instanceof Field) {
                }
                int iObjectFieldOffset72 = (int) unsafe.objectFieldOffset(fieldReflectField2);
                int i1112 = i1102 + 1;
                obj3 = objArr4[i1112];
                if (obj3 instanceof Field) {
                }
                int i1122 = i42;
                iObjectFieldOffset4 = (int) unsafe.objectFieldOffset(fieldReflectField3);
                i34 = iObjectFieldOffset72;
                i30 = i1122;
                i33 = i12;
                i32 = i108;
            } else {
                i29 = i91;
                int i113 = i15 + 1;
                Field fieldReflectField4 = reflectField(cls, (String) objArr4[i15]);
                if (i103 == 9 || i103 == 17) {
                    i30 = i42;
                    objArr5[((i92 / 3) * 2) + 1] = fieldReflectField4.getType();
                } else {
                    if (i103 == 27 || i103 == 49) {
                        i30 = i42;
                        i35 = i15 + 2;
                        objArr5[((i92 / 3) * 2) + 1] = objArr4[i113];
                    } else if (i103 == 12 || i103 == 30 || i103 == 44) {
                        i30 = i42;
                        if (rawMessageInfo.getSyntax() == ProtoSyntax.PROTO2 || (iCharAt11 & 2048) != 0) {
                            i35 = i15 + 2;
                            objArr5[((i92 / 3) * 2) + 1] = objArr4[i113];
                        }
                    } else if (i103 == 50) {
                        int i114 = i93 + 1;
                        iArr[i93] = i92;
                        int i115 = (i92 / 3) * 2;
                        int i116 = i15 + 2;
                        objArr5[i115] = objArr4[i113];
                        if ((iCharAt11 & 2048) != 0) {
                            i31 = i15 + 3;
                            objArr5[i115 + 1] = objArr4[i116];
                            i30 = i42;
                            objArr = objArr4;
                            i93 = i114;
                        } else {
                            objArr = objArr4;
                            i31 = i116;
                            i93 = i114;
                            i30 = i42;
                        }
                        iObjectFieldOffset3 = (int) unsafe.objectFieldOffset(fieldReflectField4);
                        if ((iCharAt11 & 4096) == 0 || i103 > 17) {
                            i32 = i28;
                            i33 = i12;
                            iObjectFieldOffset4 = 1048575;
                        } else {
                            int i117 = i28 + 1;
                            int iCharAt13 = str.charAt(i28);
                            if (iCharAt13 >= 55296) {
                                int i118 = iCharAt13 & 8191;
                                int i119 = 13;
                                while (true) {
                                    i32 = i117 + 1;
                                    cCharAt9 = str.charAt(i117);
                                    if (cCharAt9 < 55296) {
                                        break;
                                    }
                                    i118 |= (cCharAt9 & 8191) << i119;
                                    i119 += 13;
                                    i117 = i32;
                                }
                                iCharAt13 = i118 | (cCharAt9 << i119);
                            } else {
                                i32 = i117;
                            }
                            int i120 = (iCharAt13 / 32) + (i30 * 2);
                            Object obj4 = objArr[i120];
                            if (obj4 instanceof Field) {
                                fieldReflectField = (Field) obj4;
                            } else {
                                fieldReflectField = reflectField(cls, (String) obj4);
                                objArr[i120] = fieldReflectField;
                            }
                            iObjectFieldOffset4 = (int) unsafe.objectFieldOffset(fieldReflectField);
                            i33 = iCharAt13 % 32;
                        }
                        if (i103 >= 18 || i103 > 49) {
                            i15 = i31;
                            i34 = iObjectFieldOffset3;
                        } else {
                            iArr[i94] = iObjectFieldOffset3;
                            i15 = i31;
                            i34 = iObjectFieldOffset3;
                            i94++;
                        }
                    } else {
                        i30 = i42;
                    }
                    i31 = i35;
                    objArr = objArr4;
                    iObjectFieldOffset3 = (int) unsafe.objectFieldOffset(fieldReflectField4);
                    if ((iCharAt11 & 4096) == 0) {
                        i32 = i28;
                        i33 = i12;
                        iObjectFieldOffset4 = 1048575;
                        if (i103 >= 18) {
                            i15 = i31;
                            i34 = iObjectFieldOffset3;
                        }
                    }
                }
                objArr = objArr4;
                i31 = i113;
                iObjectFieldOffset3 = (int) unsafe.objectFieldOffset(fieldReflectField4);
                if ((iCharAt11 & 4096) == 0) {
                }
            }
            int i121 = i92 + 1;
            iArr11[i92] = iCharAt10;
            int i122 = i92 + 2;
            String str2 = str;
            iArr11[i121] = ((iCharAt11 & 256) != 0 ? 268435456 : i12) | ((iCharAt11 & 512) != 0 ? VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS : i12) | ((iCharAt11 & 2048) != 0 ? Integer.MIN_VALUE : i12) | (i103 << 20) | i34;
            i92 += 3;
            iArr11[i122] = (i33 << 20) | iObjectFieldOffset4;
            i42 = i30;
            str = str2;
            i57 = i32;
            objArr3 = objArr5;
            i91 = i29;
            c = 55296;
        }
        return new MessageSchema(iArr11, objArr3, i18, i16, rawMessageInfo.defaultInstance, rawMessageInfo.getSyntax(), false, iArr, i17, i90, newInstanceSchema, listFieldSchema, unknownFieldSchema, extensionSchema, mapFieldSchema);
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
    @Override // androidx.datastore.preferences.protobuf.Schema
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

    public final void filterMapUnknownEnumValues(int i, Object obj, Object obj2) {
        int i2 = this.buffer[i];
        if (UnsafeUtil.getObject(typeAndOffsetAt(i) & 1048575, obj) == null) {
            return;
        }
        getEnumFieldVerifier(i);
    }

    public final void getEnumFieldVerifier(int i) {
        if (this.objects[((i / 3) * 2) + 1] != null) {
            throw new ClassCastException();
        }
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
    /* JADX WARN: Removed duplicated region for block: B:179:0x041f A[PHI: r16
      0x041f: PHI (r16v47 int) = 
      (r16v30 int)
      (r16v31 int)
      (r16v32 int)
      (r16v36 int)
      (r16v38 int)
      (r16v39 int)
      (r16v40 int)
      (r16v44 int)
      (r16v48 int)
     binds: [B:252:0x05dd, B:248:0x05c1, B:244:0x05a5, B:216:0x0514, B:199:0x04a6, B:195:0x048c, B:191:0x0472, B:184:0x043c, B:178:0x041d] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // androidx.datastore.preferences.protobuf.Schema
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int getSerializedSize(AbstractMessageLite abstractMessageLite) {
        int i;
        int i2;
        int iComputeTagSize;
        int iComputeTagSize2;
        int iComputeTagSize3;
        int iComputeUInt64SizeNoTag;
        int iComputeTagSize4;
        int iComputeUInt64SizeNoTag2;
        int iComputeTagSize5;
        int iComputeTagSize6;
        int iComputeBytesSize;
        int size;
        int iComputeSizeUInt64ListNoTag;
        int iComputeTagSize7;
        int iComputeTagSize8;
        int iComputeTagSize9;
        int size2;
        int iComputeTagSize10;
        int iComputeUInt32SizeNoTag;
        int serializedSize;
        int iComputeTagSize11;
        int iComputeTagSize12;
        int iComputeTagSize13;
        int iComputeUInt64SizeNoTag3;
        int iComputeTagSize14;
        int iComputeUInt64SizeNoTag4;
        int iComputeTagSize15;
        MessageSchema messageSchema = this;
        AbstractMessageLite abstractMessageLite2 = abstractMessageLite;
        int i3 = 1;
        Unsafe unsafe = UNSAFE;
        int i4 = 1048575;
        int i5 = 1048575;
        int i6 = 0;
        int i7 = 0;
        int iComputeBytesSize2 = 0;
        while (true) {
            int[] iArr = messageSchema.buffer;
            if (i6 >= iArr.length) {
                UnknownFieldSchema unknownFieldSchema = messageSchema.unknownFieldSchema;
                int serializedSize2 = unknownFieldSchema.getSerializedSize(unknownFieldSchema.getFromMessage(abstractMessageLite2)) + iComputeBytesSize2;
                if (!messageSchema.hasExtensions) {
                    return serializedSize2;
                }
                SmallSortedMap smallSortedMap = messageSchema.extensionSchema.getExtensions(abstractMessageLite2).fields;
                int size3 = smallSortedMap.entryList.size();
                int iComputeFieldSize = 0;
                for (int i8 = 0; i8 < size3; i8++) {
                    Map.Entry arrayEntryAt = smallSortedMap.getArrayEntryAt(i8);
                    iComputeFieldSize += FieldSet.computeFieldSize((GeneratedMessageLite.ExtensionDescriptor) arrayEntryAt.getKey(), arrayEntryAt.getValue());
                }
                for (Map.Entry entry : smallSortedMap.getOverflowEntries()) {
                    iComputeFieldSize += FieldSet.computeFieldSize((GeneratedMessageLite.ExtensionDescriptor) entry.getKey(), entry.getValue());
                }
                return serializedSize2 + iComputeFieldSize;
            }
            int iTypeAndOffsetAt = messageSchema.typeAndOffsetAt(i6);
            int iType = type(iTypeAndOffsetAt);
            int i9 = iArr[i6];
            int i10 = iArr[i6 + 2];
            int i11 = i10 & i4;
            if (iType <= 17) {
                if (i11 != i5) {
                    i7 = i11 == i4 ? 0 : unsafe.getInt(abstractMessageLite2, i11);
                    i5 = i11;
                }
                i = i3 << (i10 >>> 20);
            } else {
                i = 0;
            }
            long j = iTypeAndOffsetAt & i4;
            if (iType < FieldType.DOUBLE_LIST_PACKED.id() || iType > FieldType.SINT64_LIST_PACKED.id()) {
                i11 = 0;
            }
            boolean z = messageSchema.useCachedSizeField;
            switch (iType) {
                case 0:
                    i2 = i3;
                    if (messageSchema.isFieldPresent(i6, abstractMessageLite2, i5, i7, i)) {
                        iComputeTagSize = CodedOutputStream.computeTagSize(i9) + 8;
                        iComputeBytesSize2 += iComputeTagSize;
                        break;
                    } else {
                        break;
                    }
                case 1:
                    i2 = i3;
                    if (messageSchema.isFieldPresent(i6, abstractMessageLite2, i5, i7, i)) {
                        iComputeTagSize2 = CodedOutputStream.computeTagSize(i9);
                        iComputeTagSize6 = iComputeTagSize2 + 4;
                        iComputeBytesSize2 += iComputeTagSize6;
                    }
                    messageSchema = this;
                    abstractMessageLite2 = abstractMessageLite;
                    break;
                case 2:
                    i2 = i3;
                    if (messageSchema.isFieldPresent(i6, abstractMessageLite2, i5, i7, i)) {
                        long j2 = unsafe.getLong(abstractMessageLite2, j);
                        iComputeTagSize3 = CodedOutputStream.computeTagSize(i9);
                        iComputeUInt64SizeNoTag = CodedOutputStream.computeUInt64SizeNoTag(j2);
                        iComputeBytesSize2 += iComputeUInt64SizeNoTag + iComputeTagSize3;
                    }
                    messageSchema = this;
                    break;
                case 3:
                    i2 = i3;
                    if (messageSchema.isFieldPresent(i6, abstractMessageLite2, i5, i7, i)) {
                        long j3 = unsafe.getLong(abstractMessageLite2, j);
                        iComputeTagSize3 = CodedOutputStream.computeTagSize(i9);
                        iComputeUInt64SizeNoTag = CodedOutputStream.computeUInt64SizeNoTag(j3);
                        iComputeBytesSize2 += iComputeUInt64SizeNoTag + iComputeTagSize3;
                    }
                    messageSchema = this;
                    break;
                case 4:
                    i2 = i3;
                    if (messageSchema.isFieldPresent(i6, abstractMessageLite2, i5, i7, i)) {
                        int i12 = unsafe.getInt(abstractMessageLite2, j);
                        iComputeTagSize4 = CodedOutputStream.computeTagSize(i9);
                        iComputeUInt64SizeNoTag2 = CodedOutputStream.computeUInt64SizeNoTag(i12);
                        iComputeBytesSize = iComputeUInt64SizeNoTag2 + iComputeTagSize4;
                        iComputeBytesSize2 += iComputeBytesSize;
                    }
                    messageSchema = this;
                    break;
                case 5:
                    i2 = i3;
                    if (messageSchema.isFieldPresent(i6, abstractMessageLite2, i5, i7, i)) {
                        iComputeTagSize5 = CodedOutputStream.computeTagSize(i9);
                        iComputeTagSize6 = iComputeTagSize5 + 8;
                        iComputeBytesSize2 += iComputeTagSize6;
                    }
                    messageSchema = this;
                    abstractMessageLite2 = abstractMessageLite;
                    break;
                case 6:
                    i2 = i3;
                    if (messageSchema.isFieldPresent(i6, abstractMessageLite2, i5, i7, i)) {
                        iComputeTagSize2 = CodedOutputStream.computeTagSize(i9);
                        iComputeTagSize6 = iComputeTagSize2 + 4;
                        iComputeBytesSize2 += iComputeTagSize6;
                    }
                    messageSchema = this;
                    abstractMessageLite2 = abstractMessageLite;
                    break;
                case 7:
                    i2 = i3;
                    if (messageSchema.isFieldPresent(i6, abstractMessageLite2, i5, i7, i)) {
                        iComputeTagSize6 = CodedOutputStream.computeTagSize(i9) + 1;
                        iComputeBytesSize2 += iComputeTagSize6;
                    }
                    messageSchema = this;
                    abstractMessageLite2 = abstractMessageLite;
                    break;
                case 8:
                    i2 = i3;
                    if (messageSchema.isFieldPresent(i6, abstractMessageLite2, i5, i7, i)) {
                        Object object = unsafe.getObject(abstractMessageLite2, j);
                        iComputeBytesSize2 = (object instanceof ByteString ? CodedOutputStream.computeBytesSize(i9, (ByteString) object) : CodedOutputStream.computeStringSizeNoTag((String) object) + CodedOutputStream.computeTagSize(i9)) + iComputeBytesSize2;
                    }
                    messageSchema = this;
                    break;
                case 9:
                    i2 = i3;
                    if (messageSchema.isFieldPresent(i6, abstractMessageLite2, i5, i7, i)) {
                        Object object2 = unsafe.getObject(abstractMessageLite2, j);
                        Schema messageFieldSchema = messageSchema.getMessageFieldSchema(i6);
                        Class cls = SchemaUtil.GENERATED_MESSAGE_CLASS;
                        if (object2 instanceof LazyFieldLite) {
                            iComputeTagSize = CodedOutputStream.computeLazyFieldSizeNoTag((LazyFieldLite) object2) + CodedOutputStream.computeTagSize(i9);
                        } else {
                            int iComputeTagSize16 = CodedOutputStream.computeTagSize(i9);
                            int serializedSize3 = ((AbstractMessageLite) ((MessageLite) object2)).getSerializedSize(messageFieldSchema);
                            iComputeTagSize = CodedOutputStream.computeUInt32SizeNoTag(serializedSize3) + serializedSize3 + iComputeTagSize16;
                        }
                        iComputeBytesSize2 += iComputeTagSize;
                        break;
                    } else {
                        break;
                    }
                case 10:
                    i2 = i3;
                    if (messageSchema.isFieldPresent(i6, abstractMessageLite2, i5, i7, i)) {
                        iComputeBytesSize = CodedOutputStream.computeBytesSize(i9, (ByteString) unsafe.getObject(abstractMessageLite2, j));
                        iComputeBytesSize2 += iComputeBytesSize;
                    }
                    messageSchema = this;
                    break;
                case 11:
                    i2 = i3;
                    if (messageSchema.isFieldPresent(i6, abstractMessageLite2, i5, i7, i)) {
                        int i13 = unsafe.getInt(abstractMessageLite2, j);
                        iComputeTagSize4 = CodedOutputStream.computeTagSize(i9);
                        iComputeUInt64SizeNoTag2 = CodedOutputStream.computeUInt32SizeNoTag(i13);
                        iComputeBytesSize = iComputeUInt64SizeNoTag2 + iComputeTagSize4;
                        iComputeBytesSize2 += iComputeBytesSize;
                    }
                    messageSchema = this;
                    break;
                case 12:
                    i2 = i3;
                    if (messageSchema.isFieldPresent(i6, abstractMessageLite2, i5, i7, i)) {
                        int i14 = unsafe.getInt(abstractMessageLite2, j);
                        iComputeTagSize4 = CodedOutputStream.computeTagSize(i9);
                        iComputeUInt64SizeNoTag2 = CodedOutputStream.computeUInt64SizeNoTag(i14);
                        iComputeBytesSize = iComputeUInt64SizeNoTag2 + iComputeTagSize4;
                        iComputeBytesSize2 += iComputeBytesSize;
                    }
                    messageSchema = this;
                    break;
                case 13:
                    i2 = i3;
                    if (messageSchema.isFieldPresent(i6, abstractMessageLite2, i5, i7, i)) {
                        iComputeTagSize2 = CodedOutputStream.computeTagSize(i9);
                        iComputeTagSize6 = iComputeTagSize2 + 4;
                        iComputeBytesSize2 += iComputeTagSize6;
                    }
                    messageSchema = this;
                    abstractMessageLite2 = abstractMessageLite;
                    break;
                case 14:
                    i2 = i3;
                    if (messageSchema.isFieldPresent(i6, abstractMessageLite2, i5, i7, i)) {
                        iComputeTagSize5 = CodedOutputStream.computeTagSize(i9);
                        iComputeTagSize6 = iComputeTagSize5 + 8;
                        iComputeBytesSize2 += iComputeTagSize6;
                    }
                    messageSchema = this;
                    abstractMessageLite2 = abstractMessageLite;
                    break;
                case 15:
                    i2 = i3;
                    if (messageSchema.isFieldPresent(i6, abstractMessageLite2, i5, i7, i)) {
                        int i15 = unsafe.getInt(abstractMessageLite2, j);
                        iComputeTagSize4 = CodedOutputStream.computeTagSize(i9);
                        iComputeUInt64SizeNoTag2 = CodedOutputStream.computeSInt32SizeNoTag(i15);
                        iComputeBytesSize = iComputeUInt64SizeNoTag2 + iComputeTagSize4;
                        iComputeBytesSize2 += iComputeBytesSize;
                    }
                    messageSchema = this;
                    break;
                case 16:
                    i2 = i3;
                    if (messageSchema.isFieldPresent(i6, abstractMessageLite2, i5, i7, i)) {
                        long j4 = unsafe.getLong(abstractMessageLite2, j);
                        iComputeTagSize3 = CodedOutputStream.computeTagSize(i9);
                        iComputeUInt64SizeNoTag = CodedOutputStream.computeSInt64SizeNoTag(j4);
                        iComputeBytesSize2 += iComputeUInt64SizeNoTag + iComputeTagSize3;
                    }
                    messageSchema = this;
                    break;
                case 17:
                    i2 = i3;
                    if (messageSchema.isFieldPresent(i6, abstractMessageLite2, i5, i7, i)) {
                        iComputeTagSize = ((AbstractMessageLite) ((MessageLite) unsafe.getObject(abstractMessageLite2, j))).getSerializedSize(messageSchema.getMessageFieldSchema(i6)) + (CodedOutputStream.computeTagSize(i9) * 2);
                        iComputeBytesSize2 += iComputeTagSize;
                        break;
                    } else {
                        break;
                    }
                case 18:
                    i2 = i3;
                    iComputeTagSize = SchemaUtil.computeSizeFixed64List(i9, (List) unsafe.getObject(abstractMessageLite2, j));
                    iComputeBytesSize2 += iComputeTagSize;
                    break;
                case 19:
                    i2 = i3;
                    iComputeTagSize = SchemaUtil.computeSizeFixed32List(i9, (List) unsafe.getObject(abstractMessageLite2, j));
                    iComputeBytesSize2 += iComputeTagSize;
                    break;
                case 20:
                    i2 = i3;
                    List list = (List) unsafe.getObject(abstractMessageLite2, j);
                    Class cls2 = SchemaUtil.GENERATED_MESSAGE_CLASS;
                    iComputeTagSize8 = list.size() == 0 ? 0 : (CodedOutputStream.computeTagSize(i9) * list.size()) + SchemaUtil.computeSizeInt64ListNoTag(list);
                    iComputeBytesSize2 += iComputeTagSize8;
                    break;
                case 21:
                    i2 = i3;
                    List list2 = (List) unsafe.getObject(abstractMessageLite2, j);
                    Class cls3 = SchemaUtil.GENERATED_MESSAGE_CLASS;
                    size = list2.size();
                    if (size != 0) {
                        iComputeSizeUInt64ListNoTag = SchemaUtil.computeSizeUInt64ListNoTag(list2);
                        iComputeTagSize7 = CodedOutputStream.computeTagSize(i9);
                        iComputeTagSize8 = (iComputeTagSize7 * size) + iComputeSizeUInt64ListNoTag;
                        iComputeBytesSize2 += iComputeTagSize8;
                        break;
                    }
                    iComputeBytesSize2 += iComputeTagSize8;
                case 22:
                    i2 = i3;
                    List list3 = (List) unsafe.getObject(abstractMessageLite2, j);
                    Class cls4 = SchemaUtil.GENERATED_MESSAGE_CLASS;
                    size = list3.size();
                    if (size != 0) {
                        iComputeSizeUInt64ListNoTag = SchemaUtil.computeSizeInt32ListNoTag(list3);
                        iComputeTagSize7 = CodedOutputStream.computeTagSize(i9);
                        iComputeTagSize8 = (iComputeTagSize7 * size) + iComputeSizeUInt64ListNoTag;
                        iComputeBytesSize2 += iComputeTagSize8;
                        break;
                    }
                    iComputeBytesSize2 += iComputeTagSize8;
                case 23:
                    i2 = i3;
                    iComputeTagSize = SchemaUtil.computeSizeFixed64List(i9, (List) unsafe.getObject(abstractMessageLite2, j));
                    iComputeBytesSize2 += iComputeTagSize;
                    break;
                case 24:
                    i2 = i3;
                    iComputeTagSize = SchemaUtil.computeSizeFixed32List(i9, (List) unsafe.getObject(abstractMessageLite2, j));
                    iComputeBytesSize2 += iComputeTagSize;
                    break;
                case 25:
                    i2 = i3;
                    List list4 = (List) unsafe.getObject(abstractMessageLite2, j);
                    Class cls5 = SchemaUtil.GENERATED_MESSAGE_CLASS;
                    int size4 = list4.size();
                    iComputeBytesSize2 += size4 == 0 ? 0 : (CodedOutputStream.computeTagSize(i9) + 1) * size4;
                    break;
                case 26:
                    i2 = i3;
                    List list5 = (List) unsafe.getObject(abstractMessageLite2, j);
                    Class cls6 = SchemaUtil.GENERATED_MESSAGE_CLASS;
                    int size5 = list5.size();
                    if (size5 != 0) {
                        iComputeTagSize8 = CodedOutputStream.computeTagSize(i9) * size5;
                        if (list5 instanceof LazyStringList) {
                            LazyStringList lazyStringList = (LazyStringList) list5;
                            for (int i16 = 0; i16 < size5; i16++) {
                                Object raw = lazyStringList.getRaw();
                                iComputeTagSize8 = (raw instanceof ByteString ? CodedOutputStream.computeBytesSizeNoTag((ByteString) raw) : CodedOutputStream.computeStringSizeNoTag((String) raw)) + iComputeTagSize8;
                            }
                        } else {
                            for (int i17 = 0; i17 < size5; i17++) {
                                Object obj = list5.get(i17);
                                iComputeTagSize8 = (obj instanceof ByteString ? CodedOutputStream.computeBytesSizeNoTag((ByteString) obj) : CodedOutputStream.computeStringSizeNoTag((String) obj)) + iComputeTagSize8;
                            }
                        }
                    }
                    iComputeBytesSize2 += iComputeTagSize8;
                    break;
                case 27:
                    i2 = i3;
                    List list6 = (List) unsafe.getObject(abstractMessageLite2, j);
                    Schema messageFieldSchema2 = messageSchema.getMessageFieldSchema(i6);
                    Class cls7 = SchemaUtil.GENERATED_MESSAGE_CLASS;
                    int size6 = list6.size();
                    if (size6 == 0) {
                        iComputeTagSize9 = 0;
                    } else {
                        iComputeTagSize9 = CodedOutputStream.computeTagSize(i9) * size6;
                        for (int i18 = 0; i18 < size6; i18++) {
                            Object obj2 = list6.get(i18);
                            if (obj2 instanceof LazyFieldLite) {
                                iComputeTagSize9 = CodedOutputStream.computeLazyFieldSizeNoTag((LazyFieldLite) obj2) + iComputeTagSize9;
                            } else {
                                int serializedSize4 = ((AbstractMessageLite) ((MessageLite) obj2)).getSerializedSize(messageFieldSchema2);
                                iComputeTagSize9 = CodedOutputStream.computeUInt32SizeNoTag(serializedSize4) + serializedSize4 + iComputeTagSize9;
                            }
                        }
                    }
                    iComputeBytesSize2 += iComputeTagSize9;
                    break;
                case 28:
                    i2 = i3;
                    List list7 = (List) unsafe.getObject(abstractMessageLite2, j);
                    Class cls8 = SchemaUtil.GENERATED_MESSAGE_CLASS;
                    int size7 = list7.size();
                    if (size7 != 0) {
                        iComputeTagSize8 = CodedOutputStream.computeTagSize(i9) * size7;
                        for (int i19 = 0; i19 < list7.size(); i19++) {
                            iComputeTagSize8 += CodedOutputStream.computeBytesSizeNoTag((ByteString) list7.get(i19));
                        }
                    }
                    iComputeBytesSize2 += iComputeTagSize8;
                    break;
                case 29:
                    i2 = i3;
                    List list8 = (List) unsafe.getObject(abstractMessageLite2, j);
                    Class cls9 = SchemaUtil.GENERATED_MESSAGE_CLASS;
                    size = list8.size();
                    if (size != 0) {
                        iComputeSizeUInt64ListNoTag = SchemaUtil.computeSizeUInt32ListNoTag(list8);
                        iComputeTagSize7 = CodedOutputStream.computeTagSize(i9);
                        iComputeTagSize8 = (iComputeTagSize7 * size) + iComputeSizeUInt64ListNoTag;
                        iComputeBytesSize2 += iComputeTagSize8;
                        break;
                    }
                    iComputeBytesSize2 += iComputeTagSize8;
                case 30:
                    i2 = i3;
                    List list9 = (List) unsafe.getObject(abstractMessageLite2, j);
                    Class cls10 = SchemaUtil.GENERATED_MESSAGE_CLASS;
                    size = list9.size();
                    if (size != 0) {
                        iComputeSizeUInt64ListNoTag = SchemaUtil.computeSizeEnumListNoTag(list9);
                        iComputeTagSize7 = CodedOutputStream.computeTagSize(i9);
                        iComputeTagSize8 = (iComputeTagSize7 * size) + iComputeSizeUInt64ListNoTag;
                        iComputeBytesSize2 += iComputeTagSize8;
                        break;
                    }
                    iComputeBytesSize2 += iComputeTagSize8;
                case 31:
                    i2 = i3;
                    iComputeTagSize = SchemaUtil.computeSizeFixed32List(i9, (List) unsafe.getObject(abstractMessageLite2, j));
                    iComputeBytesSize2 += iComputeTagSize;
                    break;
                case 32:
                    i2 = i3;
                    iComputeTagSize = SchemaUtil.computeSizeFixed64List(i9, (List) unsafe.getObject(abstractMessageLite2, j));
                    iComputeBytesSize2 += iComputeTagSize;
                    break;
                case 33:
                    i2 = i3;
                    List list10 = (List) unsafe.getObject(abstractMessageLite2, j);
                    Class cls11 = SchemaUtil.GENERATED_MESSAGE_CLASS;
                    size = list10.size();
                    if (size != 0) {
                        iComputeSizeUInt64ListNoTag = SchemaUtil.computeSizeSInt32ListNoTag(list10);
                        iComputeTagSize7 = CodedOutputStream.computeTagSize(i9);
                        iComputeTagSize8 = (iComputeTagSize7 * size) + iComputeSizeUInt64ListNoTag;
                        iComputeBytesSize2 += iComputeTagSize8;
                        break;
                    }
                    iComputeBytesSize2 += iComputeTagSize8;
                case 34:
                    i2 = i3;
                    List list11 = (List) unsafe.getObject(abstractMessageLite2, j);
                    Class cls12 = SchemaUtil.GENERATED_MESSAGE_CLASS;
                    size = list11.size();
                    if (size != 0) {
                        iComputeSizeUInt64ListNoTag = SchemaUtil.computeSizeSInt64ListNoTag(list11);
                        iComputeTagSize7 = CodedOutputStream.computeTagSize(i9);
                        iComputeTagSize8 = (iComputeTagSize7 * size) + iComputeSizeUInt64ListNoTag;
                        iComputeBytesSize2 += iComputeTagSize8;
                        break;
                    }
                    iComputeBytesSize2 += iComputeTagSize8;
                case 35:
                    i2 = i3;
                    List list12 = (List) unsafe.getObject(abstractMessageLite2, j);
                    Class cls13 = SchemaUtil.GENERATED_MESSAGE_CLASS;
                    size2 = list12.size() * 8;
                    if (size2 <= 0) {
                        break;
                    } else {
                        if (z) {
                            unsafe.putInt(abstractMessageLite2, i11, size2);
                        }
                        iComputeTagSize10 = CodedOutputStream.computeTagSize(i9);
                        iComputeUInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag(size2);
                        iComputeBytesSize2 += iComputeUInt32SizeNoTag + iComputeTagSize10 + size2;
                        break;
                    }
                case 36:
                    i2 = i3;
                    List list13 = (List) unsafe.getObject(abstractMessageLite2, j);
                    Class cls14 = SchemaUtil.GENERATED_MESSAGE_CLASS;
                    size2 = list13.size() * 4;
                    if (size2 <= 0) {
                        break;
                    } else {
                        if (z) {
                            unsafe.putInt(abstractMessageLite2, i11, size2);
                        }
                        iComputeTagSize10 = CodedOutputStream.computeTagSize(i9);
                        iComputeUInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag(size2);
                        iComputeBytesSize2 += iComputeUInt32SizeNoTag + iComputeTagSize10 + size2;
                        break;
                    }
                case 37:
                    i2 = i3;
                    size2 = SchemaUtil.computeSizeInt64ListNoTag((List) unsafe.getObject(abstractMessageLite2, j));
                    if (size2 <= 0) {
                        break;
                    } else {
                        if (z) {
                            unsafe.putInt(abstractMessageLite2, i11, size2);
                        }
                        iComputeTagSize10 = CodedOutputStream.computeTagSize(i9);
                        iComputeUInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag(size2);
                        iComputeBytesSize2 += iComputeUInt32SizeNoTag + iComputeTagSize10 + size2;
                        break;
                    }
                case 38:
                    i2 = i3;
                    size2 = SchemaUtil.computeSizeUInt64ListNoTag((List) unsafe.getObject(abstractMessageLite2, j));
                    if (size2 <= 0) {
                        break;
                    } else {
                        if (z) {
                            unsafe.putInt(abstractMessageLite2, i11, size2);
                        }
                        iComputeTagSize10 = CodedOutputStream.computeTagSize(i9);
                        iComputeUInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag(size2);
                        iComputeBytesSize2 += iComputeUInt32SizeNoTag + iComputeTagSize10 + size2;
                        break;
                    }
                case 39:
                    i2 = i3;
                    size2 = SchemaUtil.computeSizeInt32ListNoTag((List) unsafe.getObject(abstractMessageLite2, j));
                    if (size2 <= 0) {
                        break;
                    } else {
                        if (z) {
                            unsafe.putInt(abstractMessageLite2, i11, size2);
                        }
                        iComputeTagSize10 = CodedOutputStream.computeTagSize(i9);
                        iComputeUInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag(size2);
                        iComputeBytesSize2 += iComputeUInt32SizeNoTag + iComputeTagSize10 + size2;
                        break;
                    }
                case 40:
                    i2 = i3;
                    List list14 = (List) unsafe.getObject(abstractMessageLite2, j);
                    Class cls15 = SchemaUtil.GENERATED_MESSAGE_CLASS;
                    size2 = list14.size() * 8;
                    if (size2 <= 0) {
                        break;
                    } else {
                        if (z) {
                            unsafe.putInt(abstractMessageLite2, i11, size2);
                        }
                        iComputeTagSize10 = CodedOutputStream.computeTagSize(i9);
                        iComputeUInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag(size2);
                        iComputeBytesSize2 += iComputeUInt32SizeNoTag + iComputeTagSize10 + size2;
                        break;
                    }
                case 41:
                    i2 = i3;
                    List list15 = (List) unsafe.getObject(abstractMessageLite2, j);
                    Class cls16 = SchemaUtil.GENERATED_MESSAGE_CLASS;
                    size2 = list15.size() * 4;
                    if (size2 <= 0) {
                        break;
                    } else {
                        if (z) {
                            unsafe.putInt(abstractMessageLite2, i11, size2);
                        }
                        iComputeTagSize10 = CodedOutputStream.computeTagSize(i9);
                        iComputeUInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag(size2);
                        iComputeBytesSize2 += iComputeUInt32SizeNoTag + iComputeTagSize10 + size2;
                        break;
                    }
                case 42:
                    i2 = i3;
                    List list16 = (List) unsafe.getObject(abstractMessageLite2, j);
                    Class cls17 = SchemaUtil.GENERATED_MESSAGE_CLASS;
                    size2 = list16.size();
                    if (size2 <= 0) {
                        break;
                    } else {
                        if (z) {
                            unsafe.putInt(abstractMessageLite2, i11, size2);
                        }
                        iComputeTagSize10 = CodedOutputStream.computeTagSize(i9);
                        iComputeUInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag(size2);
                        iComputeBytesSize2 += iComputeUInt32SizeNoTag + iComputeTagSize10 + size2;
                        break;
                    }
                case 43:
                    i2 = i3;
                    size2 = SchemaUtil.computeSizeUInt32ListNoTag((List) unsafe.getObject(abstractMessageLite2, j));
                    if (size2 <= 0) {
                        break;
                    } else {
                        if (z) {
                            unsafe.putInt(abstractMessageLite2, i11, size2);
                        }
                        iComputeTagSize10 = CodedOutputStream.computeTagSize(i9);
                        iComputeUInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag(size2);
                        iComputeBytesSize2 += iComputeUInt32SizeNoTag + iComputeTagSize10 + size2;
                        break;
                    }
                case 44:
                    i2 = i3;
                    size2 = SchemaUtil.computeSizeEnumListNoTag((List) unsafe.getObject(abstractMessageLite2, j));
                    if (size2 <= 0) {
                        break;
                    } else {
                        if (z) {
                            unsafe.putInt(abstractMessageLite2, i11, size2);
                        }
                        iComputeTagSize10 = CodedOutputStream.computeTagSize(i9);
                        iComputeUInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag(size2);
                        iComputeBytesSize2 += iComputeUInt32SizeNoTag + iComputeTagSize10 + size2;
                        break;
                    }
                case 45:
                    i2 = i3;
                    List list17 = (List) unsafe.getObject(abstractMessageLite2, j);
                    Class cls18 = SchemaUtil.GENERATED_MESSAGE_CLASS;
                    size2 = list17.size() * 4;
                    if (size2 <= 0) {
                        break;
                    } else {
                        if (z) {
                            unsafe.putInt(abstractMessageLite2, i11, size2);
                        }
                        iComputeTagSize10 = CodedOutputStream.computeTagSize(i9);
                        iComputeUInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag(size2);
                        iComputeBytesSize2 += iComputeUInt32SizeNoTag + iComputeTagSize10 + size2;
                        break;
                    }
                case 46:
                    i2 = i3;
                    List list18 = (List) unsafe.getObject(abstractMessageLite2, j);
                    Class cls19 = SchemaUtil.GENERATED_MESSAGE_CLASS;
                    size2 = list18.size() * 8;
                    if (size2 <= 0) {
                        break;
                    } else {
                        if (z) {
                            unsafe.putInt(abstractMessageLite2, i11, size2);
                        }
                        iComputeTagSize10 = CodedOutputStream.computeTagSize(i9);
                        iComputeUInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag(size2);
                        iComputeBytesSize2 += iComputeUInt32SizeNoTag + iComputeTagSize10 + size2;
                        break;
                    }
                case 47:
                    i2 = i3;
                    size2 = SchemaUtil.computeSizeSInt32ListNoTag((List) unsafe.getObject(abstractMessageLite2, j));
                    if (size2 <= 0) {
                        break;
                    } else {
                        if (z) {
                            unsafe.putInt(abstractMessageLite2, i11, size2);
                        }
                        iComputeTagSize10 = CodedOutputStream.computeTagSize(i9);
                        iComputeUInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag(size2);
                        iComputeBytesSize2 += iComputeUInt32SizeNoTag + iComputeTagSize10 + size2;
                        break;
                    }
                case 48:
                    i2 = i3;
                    size2 = SchemaUtil.computeSizeSInt64ListNoTag((List) unsafe.getObject(abstractMessageLite2, j));
                    if (size2 <= 0) {
                        break;
                    } else {
                        if (z) {
                            unsafe.putInt(abstractMessageLite2, i11, size2);
                        }
                        iComputeTagSize10 = CodedOutputStream.computeTagSize(i9);
                        iComputeUInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag(size2);
                        iComputeBytesSize2 += iComputeUInt32SizeNoTag + iComputeTagSize10 + size2;
                        break;
                    }
                case 49:
                    i2 = i3;
                    List list19 = (List) unsafe.getObject(abstractMessageLite2, j);
                    Schema messageFieldSchema3 = messageSchema.getMessageFieldSchema(i6);
                    Class cls20 = SchemaUtil.GENERATED_MESSAGE_CLASS;
                    int size8 = list19.size();
                    if (size8 == 0) {
                        serializedSize = 0;
                    } else {
                        serializedSize = 0;
                        for (int i20 = 0; i20 < size8; i20++) {
                            serializedSize += ((AbstractMessageLite) ((MessageLite) list19.get(i20))).getSerializedSize(messageFieldSchema3) + (CodedOutputStream.computeTagSize(i9) * 2);
                        }
                    }
                    iComputeBytesSize2 += serializedSize;
                    break;
                case 50:
                    Object object3 = unsafe.getObject(abstractMessageLite2, j);
                    Object obj3 = messageSchema.objects[(i6 / 3) * 2];
                    ((MapFieldSchemaLite) messageSchema.mapFieldSchema).getClass();
                    MapFieldLite mapFieldLite = (MapFieldLite) object3;
                    MapEntryLite mapEntryLite = (MapEntryLite) obj3;
                    if (mapFieldLite.isEmpty()) {
                        iComputeTagSize9 = 0;
                    } else {
                        iComputeTagSize9 = 0;
                        for (Map.Entry entry2 : mapFieldLite.entrySet()) {
                            Object key = entry2.getKey();
                            Object value = entry2.getValue();
                            mapEntryLite.getClass();
                            int iComputeTagSize17 = CodedOutputStream.computeTagSize(i9);
                            int i21 = i3;
                            int iComputeSerializedSize = MapEntryLite.computeSerializedSize(mapEntryLite.metadata, key, value);
                            iComputeTagSize9 += CodedOutputStream.computeUInt32SizeNoTag(iComputeSerializedSize) + iComputeSerializedSize + iComputeTagSize17;
                            i3 = i21;
                        }
                    }
                    i2 = i3;
                    iComputeBytesSize2 += iComputeTagSize9;
                    break;
                case 51:
                    if (messageSchema.isOneofPresent(i9, i6, abstractMessageLite2)) {
                        iComputeTagSize11 = CodedOutputStream.computeTagSize(i9);
                        iComputeTagSize15 = iComputeTagSize11 + 8;
                        iComputeBytesSize2 += iComputeTagSize15;
                    }
                    i2 = i3;
                    break;
                case 52:
                    if (messageSchema.isOneofPresent(i9, i6, abstractMessageLite2)) {
                        iComputeTagSize12 = CodedOutputStream.computeTagSize(i9);
                        iComputeTagSize15 = iComputeTagSize12 + 4;
                        iComputeBytesSize2 += iComputeTagSize15;
                    }
                    i2 = i3;
                    break;
                case 53:
                    if (messageSchema.isOneofPresent(i9, i6, abstractMessageLite2)) {
                        long jOneofLongAt = oneofLongAt(j, abstractMessageLite2);
                        iComputeTagSize13 = CodedOutputStream.computeTagSize(i9);
                        iComputeUInt64SizeNoTag3 = CodedOutputStream.computeUInt64SizeNoTag(jOneofLongAt);
                        iComputeBytesSize2 += iComputeUInt64SizeNoTag3 + iComputeTagSize13;
                    }
                    i2 = i3;
                    break;
                case 54:
                    if (messageSchema.isOneofPresent(i9, i6, abstractMessageLite2)) {
                        long jOneofLongAt2 = oneofLongAt(j, abstractMessageLite2);
                        iComputeTagSize13 = CodedOutputStream.computeTagSize(i9);
                        iComputeUInt64SizeNoTag3 = CodedOutputStream.computeUInt64SizeNoTag(jOneofLongAt2);
                        iComputeBytesSize2 += iComputeUInt64SizeNoTag3 + iComputeTagSize13;
                    }
                    i2 = i3;
                    break;
                case 55:
                    if (messageSchema.isOneofPresent(i9, i6, abstractMessageLite2)) {
                        int iOneofIntAt = oneofIntAt(j, abstractMessageLite2);
                        iComputeTagSize14 = CodedOutputStream.computeTagSize(i9);
                        iComputeUInt64SizeNoTag4 = CodedOutputStream.computeUInt64SizeNoTag(iOneofIntAt);
                        iComputeTagSize15 = iComputeUInt64SizeNoTag4 + iComputeTagSize14;
                        iComputeBytesSize2 += iComputeTagSize15;
                    }
                    i2 = i3;
                    break;
                case 56:
                    if (messageSchema.isOneofPresent(i9, i6, abstractMessageLite2)) {
                        iComputeTagSize11 = CodedOutputStream.computeTagSize(i9);
                        iComputeTagSize15 = iComputeTagSize11 + 8;
                        iComputeBytesSize2 += iComputeTagSize15;
                    }
                    i2 = i3;
                    break;
                case 57:
                    if (messageSchema.isOneofPresent(i9, i6, abstractMessageLite2)) {
                        iComputeTagSize12 = CodedOutputStream.computeTagSize(i9);
                        iComputeTagSize15 = iComputeTagSize12 + 4;
                        iComputeBytesSize2 += iComputeTagSize15;
                    }
                    i2 = i3;
                    break;
                case 58:
                    if (messageSchema.isOneofPresent(i9, i6, abstractMessageLite2)) {
                        iComputeTagSize15 = CodedOutputStream.computeTagSize(i9) + i3;
                        iComputeBytesSize2 += iComputeTagSize15;
                    }
                    i2 = i3;
                    break;
                case 59:
                    if (messageSchema.isOneofPresent(i9, i6, abstractMessageLite2)) {
                        Object object4 = unsafe.getObject(abstractMessageLite2, j);
                        iComputeBytesSize2 = (object4 instanceof ByteString ? CodedOutputStream.computeBytesSize(i9, (ByteString) object4) : CodedOutputStream.computeStringSizeNoTag((String) object4) + CodedOutputStream.computeTagSize(i9)) + iComputeBytesSize2;
                    }
                    i2 = i3;
                    break;
                case 60:
                    if (messageSchema.isOneofPresent(i9, i6, abstractMessageLite2)) {
                        Object object5 = unsafe.getObject(abstractMessageLite2, j);
                        Schema messageFieldSchema4 = messageSchema.getMessageFieldSchema(i6);
                        Class cls21 = SchemaUtil.GENERATED_MESSAGE_CLASS;
                        if (object5 instanceof LazyFieldLite) {
                            iComputeTagSize14 = CodedOutputStream.computeTagSize(i9);
                            iComputeUInt64SizeNoTag4 = CodedOutputStream.computeLazyFieldSizeNoTag((LazyFieldLite) object5);
                            iComputeTagSize15 = iComputeUInt64SizeNoTag4 + iComputeTagSize14;
                            iComputeBytesSize2 += iComputeTagSize15;
                        } else {
                            int iComputeTagSize18 = CodedOutputStream.computeTagSize(i9);
                            int serializedSize5 = ((AbstractMessageLite) ((MessageLite) object5)).getSerializedSize(messageFieldSchema4);
                            iComputeTagSize15 = CodedOutputStream.computeUInt32SizeNoTag(serializedSize5) + serializedSize5 + iComputeTagSize18;
                            iComputeBytesSize2 += iComputeTagSize15;
                        }
                    }
                    i2 = i3;
                    break;
                case 61:
                    if (messageSchema.isOneofPresent(i9, i6, abstractMessageLite2)) {
                        iComputeTagSize15 = CodedOutputStream.computeBytesSize(i9, (ByteString) unsafe.getObject(abstractMessageLite2, j));
                        iComputeBytesSize2 += iComputeTagSize15;
                    }
                    i2 = i3;
                    break;
                case 62:
                    if (messageSchema.isOneofPresent(i9, i6, abstractMessageLite2)) {
                        int iOneofIntAt2 = oneofIntAt(j, abstractMessageLite2);
                        iComputeTagSize14 = CodedOutputStream.computeTagSize(i9);
                        iComputeUInt64SizeNoTag4 = CodedOutputStream.computeUInt32SizeNoTag(iOneofIntAt2);
                        iComputeTagSize15 = iComputeUInt64SizeNoTag4 + iComputeTagSize14;
                        iComputeBytesSize2 += iComputeTagSize15;
                    }
                    i2 = i3;
                    break;
                case 63:
                    if (messageSchema.isOneofPresent(i9, i6, abstractMessageLite2)) {
                        int iOneofIntAt3 = oneofIntAt(j, abstractMessageLite2);
                        iComputeTagSize14 = CodedOutputStream.computeTagSize(i9);
                        iComputeUInt64SizeNoTag4 = CodedOutputStream.computeUInt64SizeNoTag(iOneofIntAt3);
                        iComputeTagSize15 = iComputeUInt64SizeNoTag4 + iComputeTagSize14;
                        iComputeBytesSize2 += iComputeTagSize15;
                    }
                    i2 = i3;
                    break;
                case 64:
                    if (messageSchema.isOneofPresent(i9, i6, abstractMessageLite2)) {
                        iComputeTagSize12 = CodedOutputStream.computeTagSize(i9);
                        iComputeTagSize15 = iComputeTagSize12 + 4;
                        iComputeBytesSize2 += iComputeTagSize15;
                    }
                    i2 = i3;
                    break;
                case 65:
                    if (messageSchema.isOneofPresent(i9, i6, abstractMessageLite2)) {
                        iComputeTagSize11 = CodedOutputStream.computeTagSize(i9);
                        iComputeTagSize15 = iComputeTagSize11 + 8;
                        iComputeBytesSize2 += iComputeTagSize15;
                    }
                    i2 = i3;
                    break;
                case 66:
                    if (messageSchema.isOneofPresent(i9, i6, abstractMessageLite2)) {
                        int iOneofIntAt4 = oneofIntAt(j, abstractMessageLite2);
                        iComputeTagSize14 = CodedOutputStream.computeTagSize(i9);
                        iComputeUInt64SizeNoTag4 = CodedOutputStream.computeSInt32SizeNoTag(iOneofIntAt4);
                        iComputeTagSize15 = iComputeUInt64SizeNoTag4 + iComputeTagSize14;
                        iComputeBytesSize2 += iComputeTagSize15;
                    }
                    i2 = i3;
                    break;
                case 67:
                    if (messageSchema.isOneofPresent(i9, i6, abstractMessageLite2)) {
                        long jOneofLongAt3 = oneofLongAt(j, abstractMessageLite2);
                        iComputeTagSize13 = CodedOutputStream.computeTagSize(i9);
                        iComputeUInt64SizeNoTag3 = CodedOutputStream.computeSInt64SizeNoTag(jOneofLongAt3);
                        iComputeBytesSize2 += iComputeUInt64SizeNoTag3 + iComputeTagSize13;
                    }
                    i2 = i3;
                    break;
                case 68:
                    if (messageSchema.isOneofPresent(i9, i6, abstractMessageLite2)) {
                        iComputeTagSize15 = ((AbstractMessageLite) ((MessageLite) unsafe.getObject(abstractMessageLite2, j))).getSerializedSize(messageSchema.getMessageFieldSchema(i6)) + (CodedOutputStream.computeTagSize(i9) * 2);
                        iComputeBytesSize2 += iComputeTagSize15;
                    }
                    i2 = i3;
                    break;
                default:
                    i2 = i3;
                    break;
            }
            i6 += 3;
            i3 = i2;
            i4 = 1048575;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:42:0x00d7 A[PHI: r3
      0x00d7: PHI (r3v32 int) = (r3v10 int), (r3v33 int) binds: [B:83:0x01f0, B:41:0x00d5] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // androidx.datastore.preferences.protobuf.Schema
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

    @Override // androidx.datastore.preferences.protobuf.Schema
    public final boolean isInitialized(Object obj) {
        int i;
        int i2;
        int i3;
        int i4 = 1048575;
        int i5 = 0;
        int i6 = 0;
        loop0: while (true) {
            if (i6 < this.checkInitializedCount) {
                int i7 = this.intArray[i6];
                int[] iArr = this.buffer;
                int i8 = iArr[i7];
                int iTypeAndOffsetAt = typeAndOffsetAt(i7);
                int i9 = iArr[i7 + 2];
                int i10 = i9 & 1048575;
                int i11 = 1 << (i9 >>> 20);
                if (i10 != i4) {
                    if (i10 != 1048575) {
                        i5 = UNSAFE.getInt(obj, i10);
                    }
                    i2 = i7;
                    i3 = i5;
                    i = i10;
                } else {
                    int i12 = i5;
                    i = i4;
                    i2 = i7;
                    i3 = i12;
                }
                if ((268435456 & iTypeAndOffsetAt) != 0 && !isFieldPresent(i2, obj, i, i3, i11)) {
                    break;
                }
                int iType = type(iTypeAndOffsetAt);
                if (iType == 9 || iType == 17) {
                    if (isFieldPresent(i2, obj, i, i3, i11) && !getMessageFieldSchema(i2).isInitialized(UnsafeUtil.getObject(iTypeAndOffsetAt & 1048575, obj))) {
                        break;
                    }
                    i6++;
                    i4 = i;
                    i5 = i3;
                } else {
                    if (iType != 27) {
                        if (iType == 60 || iType == 68) {
                            if (isOneofPresent(i8, i2, obj) && !getMessageFieldSchema(i2).isInitialized(UnsafeUtil.getObject(iTypeAndOffsetAt & 1048575, obj))) {
                                break;
                            }
                            i6++;
                            i4 = i;
                            i5 = i3;
                        } else if (iType != 49) {
                            if (iType == 50) {
                                Object object = UnsafeUtil.getObject(iTypeAndOffsetAt & 1048575, obj);
                                ((MapFieldSchemaLite) this.mapFieldSchema).getClass();
                                MapFieldLite mapFieldLite = (MapFieldLite) object;
                                if (!mapFieldLite.isEmpty()) {
                                    if (((MapEntryLite) this.objects[(i2 / 3) * 2]).metadata.valueType.getJavaType() == WireFormat$JavaType.MESSAGE) {
                                        Schema schemaSchemaFor = null;
                                        for (Object obj2 : mapFieldLite.values()) {
                                            if (schemaSchemaFor == null) {
                                                schemaSchemaFor = Protobuf.INSTANCE.schemaFor(obj2.getClass());
                                            }
                                            if (!schemaSchemaFor.isInitialized(obj2)) {
                                                break loop0;
                                            }
                                        }
                                    } else {
                                        continue;
                                    }
                                } else {
                                    continue;
                                }
                            } else {
                                continue;
                            }
                            i6++;
                            i4 = i;
                            i5 = i3;
                        }
                    }
                    List list = (List) UnsafeUtil.getObject(iTypeAndOffsetAt & 1048575, obj);
                    if (list.isEmpty()) {
                        continue;
                    } else {
                        Schema messageFieldSchema = getMessageFieldSchema(i2);
                        for (int i13 = 0; i13 < list.size(); i13++) {
                            if (!messageFieldSchema.isInitialized(list.get(i13))) {
                                break loop0;
                            }
                        }
                    }
                    i6++;
                    i4 = i;
                    i5 = i3;
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

    /* JADX WARN: Removed duplicated region for block: B:27:0x0083  */
    @Override // androidx.datastore.preferences.protobuf.Schema
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
            int[] iArr = this.buffer;
            int length = iArr.length;
            for (int i = 0; i < length; i += 3) {
                int iTypeAndOffsetAt = typeAndOffsetAt(i);
                long j = 1048575 & iTypeAndOffsetAt;
                int iType = type(iTypeAndOffsetAt);
                if (iType != 9) {
                    if (iType != 60 && iType != 68) {
                        switch (iType) {
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
                                ((ListFieldSchemaLite) this.listFieldSchema).getClass();
                                AbstractProtobufList abstractProtobufList = (AbstractProtobufList) ((Internal.ProtobufList) UnsafeUtil.getObject(j, obj));
                                if (abstractProtobufList.isMutable) {
                                    abstractProtobufList.isMutable = false;
                                    break;
                                } else {
                                    break;
                                }
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
                    } else if (isOneofPresent(iArr[i], i, obj)) {
                        getMessageFieldSchema(i).makeImmutable(UNSAFE.getObject(obj, j));
                    }
                } else if (isFieldPresent(i, obj)) {
                    getMessageFieldSchema(i).makeImmutable(UNSAFE.getObject(obj, j));
                }
            }
            this.unknownFieldSchema.makeImmutable(obj);
            if (this.hasExtensions) {
                this.extensionSchema.makeImmutable(obj);
            }
        }
    }

    @Override // androidx.datastore.preferences.protobuf.Schema
    public final void mergeFrom(Object obj, CodedInputStreamReader codedInputStreamReader, ExtensionRegistryLite extensionRegistryLite) {
        extensionRegistryLite.getClass();
        if (isMutable(obj)) {
            mergeFromHelper(this.unknownFieldSchema, this.extensionSchema, obj, codedInputStreamReader, extensionRegistryLite);
        } else {
            throw new IllegalArgumentException("Mutating immutable message: " + obj);
        }
    }

    /*  JADX ERROR: Type inference failed
        jadx.core.utils.exceptions.JadxOverflowException: Type inference error: updates count limit reached
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:77)
        */
    public final void mergeFromHelper(androidx.datastore.preferences.protobuf.UnknownFieldSchema r22, androidx.datastore.preferences.protobuf.ExtensionSchema r23, java.lang.Object r24, androidx.datastore.preferences.protobuf.CodedInputStreamReader r25, androidx.datastore.preferences.protobuf.ExtensionRegistryLite r26) {
        /*
            Method dump skipped, instructions count: 2230
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.preferences.protobuf.MessageSchema.mergeFromHelper(androidx.datastore.preferences.protobuf.UnknownFieldSchema, androidx.datastore.preferences.protobuf.ExtensionSchema, java.lang.Object, androidx.datastore.preferences.protobuf.CodedInputStreamReader, androidx.datastore.preferences.protobuf.ExtensionRegistryLite):void");
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
                    Object objNewInstance = messageFieldSchema.newInstance();
                    messageFieldSchema.mergeFrom(objNewInstance, object);
                    unsafe.putObject(obj, jTypeAndOffsetAt, objNewInstance);
                } else {
                    unsafe.putObject(obj, jTypeAndOffsetAt, object);
                }
                setFieldPresent(i, obj);
                return;
            }
            Object object2 = unsafe.getObject(obj, jTypeAndOffsetAt);
            if (!isMutable(object2)) {
                Object objNewInstance2 = messageFieldSchema.newInstance();
                messageFieldSchema.mergeFrom(objNewInstance2, object2);
                unsafe.putObject(obj, jTypeAndOffsetAt, objNewInstance2);
                object2 = objNewInstance2;
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
                    Object objNewInstance = messageFieldSchema.newInstance();
                    messageFieldSchema.mergeFrom(objNewInstance, object);
                    unsafe.putObject(obj, jTypeAndOffsetAt, objNewInstance);
                } else {
                    unsafe.putObject(obj, jTypeAndOffsetAt, object);
                }
                setOneofPresent(i2, i, obj);
                return;
            }
            Object object2 = unsafe.getObject(obj, jTypeAndOffsetAt);
            if (!isMutable(object2)) {
                Object objNewInstance2 = messageFieldSchema.newInstance();
                messageFieldSchema.mergeFrom(objNewInstance2, object2);
                unsafe.putObject(obj, jTypeAndOffsetAt, objNewInstance2);
                object2 = objNewInstance2;
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
        Object objNewInstance = messageFieldSchema.newInstance();
        if (object != null) {
            messageFieldSchema.mergeFrom(objNewInstance, object);
        }
        return objNewInstance;
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
        Object objNewInstance = messageFieldSchema.newInstance();
        if (object != null) {
            messageFieldSchema.mergeFrom(objNewInstance, object);
        }
        return objNewInstance;
    }

    @Override // androidx.datastore.preferences.protobuf.Schema
    public final Object newInstance() {
        ((NewInstanceSchemaLite) this.newInstanceSchema).getClass();
        return ((GeneratedMessageLite) this.defaultInstance).newMutableInstance$1();
    }

    public final int positionForFieldNumber(int i) {
        if (i >= this.minFieldNumber && i <= this.maxFieldNumber) {
            int[] iArr = this.buffer;
            int length = (iArr.length / 3) - 1;
            int i2 = 0;
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
        }
        return -1;
    }

    public final void readGroupList(Object obj, long j, CodedInputStreamReader codedInputStreamReader, Schema schema, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException.InvalidWireTypeException {
        int tag;
        Internal.ProtobufList protobufListMutableListAt = ((ListFieldSchemaLite) this.listFieldSchema).mutableListAt(j, obj);
        int i = codedInputStreamReader.tag;
        if ((i & 7) != 3) {
            throw InvalidProtocolBufferException.invalidWireType();
        }
        do {
            Object objNewInstance = schema.newInstance();
            codedInputStreamReader.mergeGroupFieldInternal(objNewInstance, schema, extensionRegistryLite);
            schema.makeImmutable(objNewInstance);
            protobufListMutableListAt.add(objNewInstance);
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
        Internal.ProtobufList protobufListMutableListAt = ((ListFieldSchemaLite) this.listFieldSchema).mutableListAt(i & 1048575, obj);
        int i2 = codedInputStreamReader.tag;
        if ((i2 & 7) != 2) {
            throw InvalidProtocolBufferException.invalidWireType();
        }
        do {
            Object objNewInstance = schema.newInstance();
            codedInputStreamReader.mergeMessageFieldInternal(objNewInstance, schema, extensionRegistryLite);
            schema.makeImmutable(objNewInstance);
            protobufListMutableListAt.add(objNewInstance);
            CodedInputStream codedInputStream = codedInputStreamReader.input;
            if (codedInputStream.isAtEnd() || codedInputStreamReader.nextTag != 0) {
                return;
            } else {
                tag = codedInputStream.readTag();
            }
        } while (tag == i2);
        codedInputStreamReader.nextTag = tag;
    }

    public final void readString(int i, CodedInputStreamReader codedInputStreamReader, Object obj) throws InvalidProtocolBufferException.InvalidWireTypeException {
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

    public final void readStringList(int i, CodedInputStreamReader codedInputStreamReader, Object obj) throws InvalidProtocolBufferException.InvalidWireTypeException {
        boolean z = (536870912 & i) != 0;
        ListFieldSchema listFieldSchema = this.listFieldSchema;
        if (z) {
            codedInputStreamReader.readStringListInternal(((ListFieldSchemaLite) listFieldSchema).mutableListAt(i & 1048575, obj), true);
        } else {
            codedInputStreamReader.readStringListInternal(((ListFieldSchemaLite) listFieldSchema).mutableListAt(i & 1048575, obj), false);
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

    public final int typeAndOffsetAt(int i) {
        return this.buffer[i + 1];
    }

    public final void writeMapHelper(CodedOutputStreamWriter codedOutputStreamWriter, int i, Object obj, int i2) {
        if (obj != null) {
            Object obj2 = this.objects[(i2 / 3) * 2];
            MapFieldSchemaLite mapFieldSchemaLite = (MapFieldSchemaLite) this.mapFieldSchema;
            mapFieldSchemaLite.getClass();
            MapEntryLite.Metadata metadata = ((MapEntryLite) obj2).metadata;
            mapFieldSchemaLite.getClass();
            CodedOutputStream codedOutputStream = codedOutputStreamWriter.output;
            codedOutputStream.getClass();
            for (Map.Entry entry : ((MapFieldLite) obj).entrySet()) {
                codedOutputStream.writeTag(i, 2);
                codedOutputStream.writeUInt32NoTag(MapEntryLite.computeSerializedSize(metadata, entry.getKey(), entry.getValue()));
                Object key = entry.getKey();
                Object value = entry.getValue();
                FieldSet.writeElement(codedOutputStream, metadata.keyType, 1, key);
                FieldSet.writeElement(codedOutputStream, metadata.valueType, 2, value);
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:189:0x063c  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0072  */
    @Override // androidx.datastore.preferences.protobuf.Schema
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void writeTo(Object obj, CodedOutputStreamWriter codedOutputStreamWriter) {
        Map.Entry entry;
        Iterator it;
        int i;
        int i2;
        int i3;
        Map.Entry entry2;
        int i4;
        Unsafe unsafe;
        int i5;
        int i6;
        int[] iArr;
        boolean z;
        boolean z2;
        boolean z3;
        Iterator it2;
        Map.Entry entry3;
        MessageSchema messageSchema = this;
        codedOutputStreamWriter.getClass();
        Writer$FieldOrder writer$FieldOrder = Writer$FieldOrder.ASCENDING;
        Writer$FieldOrder writer$FieldOrder2 = Writer$FieldOrder.DESCENDING;
        int[] iArr2 = messageSchema.buffer;
        ExtensionSchema extensionSchema = messageSchema.extensionSchema;
        boolean z4 = messageSchema.hasExtensions;
        UnknownFieldSchema unknownFieldSchema = messageSchema.unknownFieldSchema;
        Map.Entry entry4 = null;
        boolean z5 = true;
        int i7 = 1048575;
        if (writer$FieldOrder == writer$FieldOrder2) {
            unknownFieldSchema.writeTo(unknownFieldSchema.getFromMessage(obj), codedOutputStreamWriter);
            if (z4) {
                FieldSet extensions = extensionSchema.getExtensions(obj);
                if (extensions.fields.isEmpty()) {
                    it2 = null;
                    entry3 = null;
                } else {
                    SmallSortedMap smallSortedMap = extensions.fields;
                    if (smallSortedMap.isEmpty()) {
                        it2 = Collections.emptyIterator();
                    } else if (extensions.hasLazyField) {
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
                }
            }
            int length = iArr2.length - 3;
            while (length >= 0) {
                int iTypeAndOffsetAt = messageSchema.typeAndOffsetAt(length);
                int i8 = iArr2[length];
                while (entry3 != null && extensionSchema.extensionNumber(entry3) > i8) {
                    extensionSchema.serializeExtension(codedOutputStreamWriter, entry3);
                    entry3 = it2.hasNext() ? (Map.Entry) it2.next() : entry4;
                }
                switch (type(iTypeAndOffsetAt)) {
                    case 0:
                        if (messageSchema.isFieldPresent(length, obj)) {
                            double d = UnsafeUtil.MEMORY_ACCESSOR.getDouble(iTypeAndOffsetAt & 1048575, obj);
                            CodedOutputStream codedOutputStream = codedOutputStreamWriter.output;
                            codedOutputStream.getClass();
                            codedOutputStream.writeFixed64(i8, Double.doubleToRawLongBits(d));
                            continue;
                        }
                        length -= 3;
                        entry4 = null;
                    case 1:
                        if (messageSchema.isFieldPresent(length, obj)) {
                            float f = UnsafeUtil.MEMORY_ACCESSOR.getFloat(iTypeAndOffsetAt & 1048575, obj);
                            CodedOutputStream codedOutputStream2 = codedOutputStreamWriter.output;
                            codedOutputStream2.getClass();
                            codedOutputStream2.writeFixed32(i8, Float.floatToRawIntBits(f));
                        } else {
                            continue;
                        }
                        length -= 3;
                        entry4 = null;
                    case 2:
                        if (messageSchema.isFieldPresent(length, obj)) {
                            codedOutputStreamWriter.writeInt64(i8, UnsafeUtil.getLong(iTypeAndOffsetAt & 1048575, obj));
                        } else {
                            continue;
                        }
                        length -= 3;
                        entry4 = null;
                    case 3:
                        if (messageSchema.isFieldPresent(length, obj)) {
                            codedOutputStreamWriter.output.writeUInt64(i8, UnsafeUtil.getLong(iTypeAndOffsetAt & 1048575, obj));
                        } else {
                            continue;
                        }
                        length -= 3;
                        entry4 = null;
                    case 4:
                        if (messageSchema.isFieldPresent(length, obj)) {
                            codedOutputStreamWriter.writeInt32(i8, UnsafeUtil.getInt(iTypeAndOffsetAt & 1048575, obj));
                        } else {
                            continue;
                        }
                        length -= 3;
                        entry4 = null;
                    case 5:
                        if (messageSchema.isFieldPresent(length, obj)) {
                            codedOutputStreamWriter.writeFixed64(i8, UnsafeUtil.getLong(iTypeAndOffsetAt & 1048575, obj));
                        } else {
                            continue;
                        }
                        length -= 3;
                        entry4 = null;
                    case 6:
                        if (messageSchema.isFieldPresent(length, obj)) {
                            codedOutputStreamWriter.writeFixed32(i8, UnsafeUtil.getInt(iTypeAndOffsetAt & 1048575, obj));
                        } else {
                            continue;
                        }
                        length -= 3;
                        entry4 = null;
                    case 7:
                        if (messageSchema.isFieldPresent(length, obj)) {
                            codedOutputStreamWriter.output.writeBool(i8, UnsafeUtil.MEMORY_ACCESSOR.getBoolean(iTypeAndOffsetAt & 1048575, obj));
                        } else {
                            continue;
                        }
                        length -= 3;
                        entry4 = null;
                    case 8:
                        if (messageSchema.isFieldPresent(length, obj)) {
                            writeString(i8, UnsafeUtil.getObject(iTypeAndOffsetAt & 1048575, obj), codedOutputStreamWriter);
                        } else {
                            continue;
                        }
                        length -= 3;
                        entry4 = null;
                    case 9:
                        if (messageSchema.isFieldPresent(length, obj)) {
                            codedOutputStreamWriter.writeMessage(i8, UnsafeUtil.getObject(iTypeAndOffsetAt & 1048575, obj), messageSchema.getMessageFieldSchema(length));
                        } else {
                            continue;
                        }
                        length -= 3;
                        entry4 = null;
                    case 10:
                        if (messageSchema.isFieldPresent(length, obj)) {
                            codedOutputStreamWriter.writeBytes(i8, (ByteString) UnsafeUtil.getObject(iTypeAndOffsetAt & 1048575, obj));
                        } else {
                            continue;
                        }
                        length -= 3;
                        entry4 = null;
                    case 11:
                        if (messageSchema.isFieldPresent(length, obj)) {
                            codedOutputStreamWriter.output.writeUInt32(i8, UnsafeUtil.getInt(iTypeAndOffsetAt & 1048575, obj));
                        } else {
                            continue;
                        }
                        length -= 3;
                        entry4 = null;
                    case 12:
                        if (messageSchema.isFieldPresent(length, obj)) {
                            codedOutputStreamWriter.output.writeInt32(i8, UnsafeUtil.getInt(iTypeAndOffsetAt & 1048575, obj));
                        } else {
                            continue;
                        }
                        length -= 3;
                        entry4 = null;
                    case 13:
                        if (messageSchema.isFieldPresent(length, obj)) {
                            codedOutputStreamWriter.output.writeFixed32(i8, UnsafeUtil.getInt(iTypeAndOffsetAt & 1048575, obj));
                        } else {
                            continue;
                        }
                        length -= 3;
                        entry4 = null;
                    case 14:
                        if (messageSchema.isFieldPresent(length, obj)) {
                            codedOutputStreamWriter.output.writeFixed64(i8, UnsafeUtil.getLong(iTypeAndOffsetAt & 1048575, obj));
                        } else {
                            continue;
                        }
                        length -= 3;
                        entry4 = null;
                    case 15:
                        if (messageSchema.isFieldPresent(length, obj)) {
                            int i9 = UnsafeUtil.getInt(iTypeAndOffsetAt & 1048575, obj);
                            codedOutputStreamWriter.output.writeUInt32(i8, (i9 >> 31) ^ (i9 << 1));
                        } else {
                            continue;
                        }
                        length -= 3;
                        entry4 = null;
                    case 16:
                        if (messageSchema.isFieldPresent(length, obj)) {
                            long j = UnsafeUtil.getLong(iTypeAndOffsetAt & 1048575, obj);
                            codedOutputStreamWriter.output.writeUInt64(i8, (j << 1) ^ (j >> 63));
                        } else {
                            continue;
                        }
                        length -= 3;
                        entry4 = null;
                    case 17:
                        if (messageSchema.isFieldPresent(length, obj)) {
                            codedOutputStreamWriter.writeGroup(i8, UnsafeUtil.getObject(iTypeAndOffsetAt & 1048575, obj), messageSchema.getMessageFieldSchema(length));
                        } else {
                            continue;
                        }
                        length -= 3;
                        entry4 = null;
                    case 18:
                        SchemaUtil.writeDoubleList(iArr2[length], (List) UnsafeUtil.getObject(iTypeAndOffsetAt & 1048575, obj), codedOutputStreamWriter, false);
                        continue;
                        length -= 3;
                        entry4 = null;
                    case 19:
                        SchemaUtil.writeFloatList(iArr2[length], (List) UnsafeUtil.getObject(iTypeAndOffsetAt & 1048575, obj), codedOutputStreamWriter, false);
                        continue;
                        length -= 3;
                        entry4 = null;
                    case 20:
                        SchemaUtil.writeInt64List(iArr2[length], (List) UnsafeUtil.getObject(iTypeAndOffsetAt & 1048575, obj), codedOutputStreamWriter, false);
                        continue;
                        length -= 3;
                        entry4 = null;
                    case 21:
                        SchemaUtil.writeUInt64List(iArr2[length], (List) UnsafeUtil.getObject(iTypeAndOffsetAt & 1048575, obj), codedOutputStreamWriter, false);
                        continue;
                        length -= 3;
                        entry4 = null;
                    case 22:
                        SchemaUtil.writeInt32List(iArr2[length], (List) UnsafeUtil.getObject(iTypeAndOffsetAt & 1048575, obj), codedOutputStreamWriter, false);
                        continue;
                        length -= 3;
                        entry4 = null;
                    case 23:
                        SchemaUtil.writeFixed64List(iArr2[length], (List) UnsafeUtil.getObject(iTypeAndOffsetAt & 1048575, obj), codedOutputStreamWriter, false);
                        continue;
                        length -= 3;
                        entry4 = null;
                    case 24:
                        SchemaUtil.writeFixed32List(iArr2[length], (List) UnsafeUtil.getObject(iTypeAndOffsetAt & 1048575, obj), codedOutputStreamWriter, false);
                        continue;
                        length -= 3;
                        entry4 = null;
                    case 25:
                        SchemaUtil.writeBoolList(iArr2[length], (List) UnsafeUtil.getObject(iTypeAndOffsetAt & 1048575, obj), codedOutputStreamWriter, false);
                        continue;
                        length -= 3;
                        entry4 = null;
                    case 26:
                        SchemaUtil.writeStringList(iArr2[length], (List) UnsafeUtil.getObject(iTypeAndOffsetAt & 1048575, obj), codedOutputStreamWriter);
                        continue;
                        length -= 3;
                        entry4 = null;
                    case 27:
                        SchemaUtil.writeMessageList(iArr2[length], (List) UnsafeUtil.getObject(iTypeAndOffsetAt & 1048575, obj), codedOutputStreamWriter, messageSchema.getMessageFieldSchema(length));
                        continue;
                        length -= 3;
                        entry4 = null;
                    case 28:
                        SchemaUtil.writeBytesList(iArr2[length], (List) UnsafeUtil.getObject(iTypeAndOffsetAt & 1048575, obj), codedOutputStreamWriter);
                        continue;
                        length -= 3;
                        entry4 = null;
                    case 29:
                        SchemaUtil.writeUInt32List(iArr2[length], (List) UnsafeUtil.getObject(iTypeAndOffsetAt & 1048575, obj), codedOutputStreamWriter, false);
                        continue;
                        length -= 3;
                        entry4 = null;
                    case 30:
                        SchemaUtil.writeEnumList(iArr2[length], (List) UnsafeUtil.getObject(iTypeAndOffsetAt & 1048575, obj), codedOutputStreamWriter, false);
                        continue;
                        length -= 3;
                        entry4 = null;
                    case 31:
                        SchemaUtil.writeSFixed32List(iArr2[length], (List) UnsafeUtil.getObject(iTypeAndOffsetAt & 1048575, obj), codedOutputStreamWriter, false);
                        continue;
                        length -= 3;
                        entry4 = null;
                    case 32:
                        SchemaUtil.writeSFixed64List(iArr2[length], (List) UnsafeUtil.getObject(iTypeAndOffsetAt & 1048575, obj), codedOutputStreamWriter, false);
                        continue;
                        length -= 3;
                        entry4 = null;
                    case 33:
                        SchemaUtil.writeSInt32List(iArr2[length], (List) UnsafeUtil.getObject(iTypeAndOffsetAt & 1048575, obj), codedOutputStreamWriter, false);
                        continue;
                        length -= 3;
                        entry4 = null;
                    case 34:
                        SchemaUtil.writeSInt64List(iArr2[length], (List) UnsafeUtil.getObject(iTypeAndOffsetAt & 1048575, obj), codedOutputStreamWriter, false);
                        continue;
                        length -= 3;
                        entry4 = null;
                    case 35:
                        SchemaUtil.writeDoubleList(iArr2[length], (List) UnsafeUtil.getObject(iTypeAndOffsetAt & 1048575, obj), codedOutputStreamWriter, true);
                        continue;
                        length -= 3;
                        entry4 = null;
                    case 36:
                        SchemaUtil.writeFloatList(iArr2[length], (List) UnsafeUtil.getObject(iTypeAndOffsetAt & 1048575, obj), codedOutputStreamWriter, true);
                        continue;
                        length -= 3;
                        entry4 = null;
                    case 37:
                        SchemaUtil.writeInt64List(iArr2[length], (List) UnsafeUtil.getObject(iTypeAndOffsetAt & 1048575, obj), codedOutputStreamWriter, true);
                        continue;
                        length -= 3;
                        entry4 = null;
                    case 38:
                        SchemaUtil.writeUInt64List(iArr2[length], (List) UnsafeUtil.getObject(iTypeAndOffsetAt & 1048575, obj), codedOutputStreamWriter, true);
                        continue;
                        length -= 3;
                        entry4 = null;
                    case 39:
                        SchemaUtil.writeInt32List(iArr2[length], (List) UnsafeUtil.getObject(iTypeAndOffsetAt & 1048575, obj), codedOutputStreamWriter, true);
                        continue;
                        length -= 3;
                        entry4 = null;
                    case 40:
                        SchemaUtil.writeFixed64List(iArr2[length], (List) UnsafeUtil.getObject(iTypeAndOffsetAt & 1048575, obj), codedOutputStreamWriter, true);
                        continue;
                        length -= 3;
                        entry4 = null;
                    case 41:
                        SchemaUtil.writeFixed32List(iArr2[length], (List) UnsafeUtil.getObject(iTypeAndOffsetAt & 1048575, obj), codedOutputStreamWriter, true);
                        continue;
                        length -= 3;
                        entry4 = null;
                    case 42:
                        SchemaUtil.writeBoolList(iArr2[length], (List) UnsafeUtil.getObject(iTypeAndOffsetAt & 1048575, obj), codedOutputStreamWriter, true);
                        continue;
                        length -= 3;
                        entry4 = null;
                    case 43:
                        SchemaUtil.writeUInt32List(iArr2[length], (List) UnsafeUtil.getObject(iTypeAndOffsetAt & 1048575, obj), codedOutputStreamWriter, true);
                        continue;
                        length -= 3;
                        entry4 = null;
                    case 44:
                        SchemaUtil.writeEnumList(iArr2[length], (List) UnsafeUtil.getObject(iTypeAndOffsetAt & 1048575, obj), codedOutputStreamWriter, true);
                        continue;
                        length -= 3;
                        entry4 = null;
                    case 45:
                        SchemaUtil.writeSFixed32List(iArr2[length], (List) UnsafeUtil.getObject(iTypeAndOffsetAt & 1048575, obj), codedOutputStreamWriter, true);
                        continue;
                        length -= 3;
                        entry4 = null;
                    case 46:
                        SchemaUtil.writeSFixed64List(iArr2[length], (List) UnsafeUtil.getObject(iTypeAndOffsetAt & 1048575, obj), codedOutputStreamWriter, true);
                        continue;
                        length -= 3;
                        entry4 = null;
                    case 47:
                        SchemaUtil.writeSInt32List(iArr2[length], (List) UnsafeUtil.getObject(iTypeAndOffsetAt & 1048575, obj), codedOutputStreamWriter, true);
                        continue;
                        length -= 3;
                        entry4 = null;
                    case 48:
                        SchemaUtil.writeSInt64List(iArr2[length], (List) UnsafeUtil.getObject(iTypeAndOffsetAt & 1048575, obj), codedOutputStreamWriter, true);
                        continue;
                        length -= 3;
                        entry4 = null;
                    case 49:
                        SchemaUtil.writeGroupList(iArr2[length], (List) UnsafeUtil.getObject(iTypeAndOffsetAt & 1048575, obj), codedOutputStreamWriter, messageSchema.getMessageFieldSchema(length));
                        continue;
                        length -= 3;
                        entry4 = null;
                    case 50:
                        messageSchema.writeMapHelper(codedOutputStreamWriter, i8, UnsafeUtil.getObject(iTypeAndOffsetAt & 1048575, obj), length);
                        continue;
                        length -= 3;
                        entry4 = null;
                    case 51:
                        if (messageSchema.isOneofPresent(i8, length, obj)) {
                            double dDoubleValue = ((Double) UnsafeUtil.getObject(iTypeAndOffsetAt & 1048575, obj)).doubleValue();
                            CodedOutputStream codedOutputStream3 = codedOutputStreamWriter.output;
                            codedOutputStream3.getClass();
                            codedOutputStream3.writeFixed64(i8, Double.doubleToRawLongBits(dDoubleValue));
                        } else {
                            continue;
                        }
                        length -= 3;
                        entry4 = null;
                    case 52:
                        if (messageSchema.isOneofPresent(i8, length, obj)) {
                            float fFloatValue = ((Float) UnsafeUtil.getObject(iTypeAndOffsetAt & 1048575, obj)).floatValue();
                            CodedOutputStream codedOutputStream4 = codedOutputStreamWriter.output;
                            codedOutputStream4.getClass();
                            codedOutputStream4.writeFixed32(i8, Float.floatToRawIntBits(fFloatValue));
                        } else {
                            continue;
                        }
                        length -= 3;
                        entry4 = null;
                    case 53:
                        if (messageSchema.isOneofPresent(i8, length, obj)) {
                            codedOutputStreamWriter.writeInt64(i8, oneofLongAt(iTypeAndOffsetAt & 1048575, obj));
                        } else {
                            continue;
                        }
                        length -= 3;
                        entry4 = null;
                    case 54:
                        if (messageSchema.isOneofPresent(i8, length, obj)) {
                            codedOutputStreamWriter.output.writeUInt64(i8, oneofLongAt(iTypeAndOffsetAt & 1048575, obj));
                        } else {
                            continue;
                        }
                        length -= 3;
                        entry4 = null;
                    case 55:
                        if (messageSchema.isOneofPresent(i8, length, obj)) {
                            codedOutputStreamWriter.writeInt32(i8, oneofIntAt(iTypeAndOffsetAt & 1048575, obj));
                        } else {
                            continue;
                        }
                        length -= 3;
                        entry4 = null;
                    case 56:
                        if (messageSchema.isOneofPresent(i8, length, obj)) {
                            codedOutputStreamWriter.writeFixed64(i8, oneofLongAt(iTypeAndOffsetAt & 1048575, obj));
                        } else {
                            continue;
                        }
                        length -= 3;
                        entry4 = null;
                    case 57:
                        if (messageSchema.isOneofPresent(i8, length, obj)) {
                            codedOutputStreamWriter.writeFixed32(i8, oneofIntAt(iTypeAndOffsetAt & 1048575, obj));
                        } else {
                            continue;
                        }
                        length -= 3;
                        entry4 = null;
                    case 58:
                        if (messageSchema.isOneofPresent(i8, length, obj)) {
                            codedOutputStreamWriter.output.writeBool(i8, ((Boolean) UnsafeUtil.getObject(iTypeAndOffsetAt & 1048575, obj)).booleanValue());
                        } else {
                            continue;
                        }
                        length -= 3;
                        entry4 = null;
                    case 59:
                        if (messageSchema.isOneofPresent(i8, length, obj)) {
                            writeString(i8, UnsafeUtil.getObject(iTypeAndOffsetAt & 1048575, obj), codedOutputStreamWriter);
                        } else {
                            continue;
                        }
                        length -= 3;
                        entry4 = null;
                    case 60:
                        if (messageSchema.isOneofPresent(i8, length, obj)) {
                            codedOutputStreamWriter.writeMessage(i8, UnsafeUtil.getObject(iTypeAndOffsetAt & 1048575, obj), messageSchema.getMessageFieldSchema(length));
                        } else {
                            continue;
                        }
                        length -= 3;
                        entry4 = null;
                    case 61:
                        if (messageSchema.isOneofPresent(i8, length, obj)) {
                            codedOutputStreamWriter.writeBytes(i8, (ByteString) UnsafeUtil.getObject(iTypeAndOffsetAt & 1048575, obj));
                        } else {
                            continue;
                        }
                        length -= 3;
                        entry4 = null;
                    case 62:
                        if (messageSchema.isOneofPresent(i8, length, obj)) {
                            codedOutputStreamWriter.output.writeUInt32(i8, oneofIntAt(iTypeAndOffsetAt & 1048575, obj));
                        } else {
                            continue;
                        }
                        length -= 3;
                        entry4 = null;
                    case 63:
                        if (messageSchema.isOneofPresent(i8, length, obj)) {
                            codedOutputStreamWriter.output.writeInt32(i8, oneofIntAt(iTypeAndOffsetAt & 1048575, obj));
                        } else {
                            continue;
                        }
                        length -= 3;
                        entry4 = null;
                    case 64:
                        if (messageSchema.isOneofPresent(i8, length, obj)) {
                            codedOutputStreamWriter.output.writeFixed32(i8, oneofIntAt(iTypeAndOffsetAt & 1048575, obj));
                        } else {
                            continue;
                        }
                        length -= 3;
                        entry4 = null;
                    case 65:
                        if (messageSchema.isOneofPresent(i8, length, obj)) {
                            codedOutputStreamWriter.output.writeFixed64(i8, oneofLongAt(iTypeAndOffsetAt & 1048575, obj));
                        } else {
                            continue;
                        }
                        length -= 3;
                        entry4 = null;
                    case 66:
                        if (messageSchema.isOneofPresent(i8, length, obj)) {
                            int iOneofIntAt = oneofIntAt(iTypeAndOffsetAt & 1048575, obj);
                            codedOutputStreamWriter.output.writeUInt32(i8, (iOneofIntAt >> 31) ^ (iOneofIntAt << 1));
                        } else {
                            continue;
                        }
                        length -= 3;
                        entry4 = null;
                    case 67:
                        if (messageSchema.isOneofPresent(i8, length, obj)) {
                            long jOneofLongAt = oneofLongAt(iTypeAndOffsetAt & 1048575, obj);
                            codedOutputStreamWriter.output.writeUInt64(i8, (jOneofLongAt << 1) ^ (jOneofLongAt >> 63));
                        } else {
                            continue;
                        }
                        length -= 3;
                        entry4 = null;
                    case 68:
                        if (messageSchema.isOneofPresent(i8, length, obj)) {
                            codedOutputStreamWriter.writeGroup(i8, UnsafeUtil.getObject(iTypeAndOffsetAt & 1048575, obj), messageSchema.getMessageFieldSchema(length));
                        }
                        length -= 3;
                        entry4 = null;
                        break;
                }
                length -= 3;
                entry4 = null;
            }
            while (entry3 != null) {
                extensionSchema.serializeExtension(codedOutputStreamWriter, entry3);
                entry3 = it2.hasNext() ? (Map.Entry) it2.next() : null;
            }
            return;
        }
        if (z4) {
            FieldSet extensions2 = extensionSchema.getExtensions(obj);
            if (extensions2.fields.isEmpty()) {
                entry = null;
                it = null;
            } else {
                Iterator it3 = extensions2.iterator();
                entry = (Map.Entry) it3.next();
                it = it3;
            }
        }
        int length2 = iArr2.length;
        Unsafe unsafe2 = UNSAFE;
        int i10 = 1048575;
        int i11 = 0;
        int i12 = 0;
        while (i11 < length2) {
            int iTypeAndOffsetAt2 = messageSchema.typeAndOffsetAt(i11);
            int i13 = iArr2[i11];
            int iType = type(iTypeAndOffsetAt2);
            boolean z6 = z5;
            if (iType <= 17) {
                int i14 = iArr2[i11 + 2];
                Map.Entry entry5 = entry;
                int i15 = i14 & i7;
                if (i15 != i10) {
                    if (i15 == i7) {
                        i = i7;
                        i2 = iType;
                        i12 = 0;
                    } else {
                        i = i7;
                        i2 = iType;
                        i12 = unsafe2.getInt(obj, i15);
                    }
                    i10 = i15;
                } else {
                    i = i7;
                    i2 = iType;
                }
                int i16 = (z6 ? 1 : 0) << (i14 >>> 20);
                int i17 = i10;
                i4 = i16;
                i3 = i17;
                entry2 = entry5;
            } else {
                Map.Entry entry6 = entry;
                i = i7;
                i2 = iType;
                i3 = i10;
                entry2 = entry6;
                i4 = 0;
            }
            while (entry2 != null && extensionSchema.extensionNumber(entry2) <= i13) {
                extensionSchema.serializeExtension(codedOutputStreamWriter, entry2);
                entry2 = it.hasNext() ? (Map.Entry) it.next() : null;
            }
            long j2 = iTypeAndOffsetAt2 & i;
            switch (i2) {
                case 0:
                    int[] iArr3 = iArr2;
                    unsafe = unsafe2;
                    i5 = i11;
                    i6 = i12;
                    iArr = iArr3;
                    z = false;
                    if (!messageSchema.isFieldPresent(i5, obj, i3, i6, i4)) {
                        break;
                    } else {
                        double d2 = UnsafeUtil.MEMORY_ACCESSOR.getDouble(j2, obj);
                        CodedOutputStream codedOutputStream5 = codedOutputStreamWriter.output;
                        codedOutputStream5.getClass();
                        codedOutputStream5.writeFixed64(i13, Double.doubleToRawLongBits(d2));
                        break;
                    }
                case 1:
                    int[] iArr4 = iArr2;
                    unsafe = unsafe2;
                    i5 = i11;
                    i6 = i12;
                    iArr = iArr4;
                    z = false;
                    if (!messageSchema.isFieldPresent(i5, obj, i3, i6, i4)) {
                        break;
                    } else {
                        float f2 = UnsafeUtil.MEMORY_ACCESSOR.getFloat(j2, obj);
                        CodedOutputStream codedOutputStream6 = codedOutputStreamWriter.output;
                        codedOutputStream6.getClass();
                        codedOutputStream6.writeFixed32(i13, Float.floatToRawIntBits(f2));
                        break;
                    }
                case 2:
                    int[] iArr5 = iArr2;
                    unsafe = unsafe2;
                    i5 = i11;
                    i6 = i12;
                    iArr = iArr5;
                    z = false;
                    if (!messageSchema.isFieldPresent(i5, obj, i3, i6, i4)) {
                        break;
                    } else {
                        codedOutputStreamWriter.writeInt64(i13, unsafe.getLong(obj, j2));
                        break;
                    }
                case 3:
                    int[] iArr6 = iArr2;
                    unsafe = unsafe2;
                    i5 = i11;
                    i6 = i12;
                    iArr = iArr6;
                    z = false;
                    if (!messageSchema.isFieldPresent(i5, obj, i3, i6, i4)) {
                        break;
                    } else {
                        codedOutputStreamWriter.output.writeUInt64(i13, unsafe.getLong(obj, j2));
                        break;
                    }
                case 4:
                    int[] iArr7 = iArr2;
                    unsafe = unsafe2;
                    i5 = i11;
                    i6 = i12;
                    iArr = iArr7;
                    z = false;
                    if (!messageSchema.isFieldPresent(i5, obj, i3, i6, i4)) {
                        break;
                    } else {
                        codedOutputStreamWriter.writeInt32(i13, unsafe.getInt(obj, j2));
                        break;
                    }
                case 5:
                    int[] iArr8 = iArr2;
                    unsafe = unsafe2;
                    i5 = i11;
                    i6 = i12;
                    iArr = iArr8;
                    z = false;
                    if (!messageSchema.isFieldPresent(i5, obj, i3, i6, i4)) {
                        break;
                    } else {
                        codedOutputStreamWriter.writeFixed64(i13, unsafe.getLong(obj, j2));
                        break;
                    }
                case 6:
                    int[] iArr9 = iArr2;
                    unsafe = unsafe2;
                    i5 = i11;
                    i6 = i12;
                    iArr = iArr9;
                    z = false;
                    if (!messageSchema.isFieldPresent(i5, obj, i3, i6, i4)) {
                        break;
                    } else {
                        codedOutputStreamWriter.writeFixed32(i13, unsafe.getInt(obj, j2));
                        break;
                    }
                case 7:
                    int[] iArr10 = iArr2;
                    unsafe = unsafe2;
                    i5 = i11;
                    i6 = i12;
                    iArr = iArr10;
                    z = false;
                    if (!messageSchema.isFieldPresent(i5, obj, i3, i6, i4)) {
                        break;
                    } else {
                        codedOutputStreamWriter.output.writeBool(i13, UnsafeUtil.MEMORY_ACCESSOR.getBoolean(j2, obj));
                        break;
                    }
                case 8:
                    int[] iArr11 = iArr2;
                    unsafe = unsafe2;
                    i5 = i11;
                    i6 = i12;
                    iArr = iArr11;
                    z = false;
                    if (!messageSchema.isFieldPresent(i5, obj, i3, i6, i4)) {
                        break;
                    } else {
                        writeString(i13, unsafe.getObject(obj, j2), codedOutputStreamWriter);
                        break;
                    }
                case 9:
                    int[] iArr12 = iArr2;
                    unsafe = unsafe2;
                    i5 = i11;
                    i6 = i12;
                    iArr = iArr12;
                    z = false;
                    if (!messageSchema.isFieldPresent(i5, obj, i3, i6, i4)) {
                        break;
                    } else {
                        codedOutputStreamWriter.writeMessage(i13, unsafe.getObject(obj, j2), messageSchema.getMessageFieldSchema(i5));
                        break;
                    }
                case 10:
                    int[] iArr13 = iArr2;
                    unsafe = unsafe2;
                    i5 = i11;
                    i6 = i12;
                    iArr = iArr13;
                    z = false;
                    if (!messageSchema.isFieldPresent(i5, obj, i3, i6, i4)) {
                        break;
                    } else {
                        codedOutputStreamWriter.writeBytes(i13, (ByteString) unsafe.getObject(obj, j2));
                        break;
                    }
                case 11:
                    int[] iArr14 = iArr2;
                    unsafe = unsafe2;
                    i5 = i11;
                    i6 = i12;
                    iArr = iArr14;
                    z = false;
                    if (!messageSchema.isFieldPresent(i5, obj, i3, i6, i4)) {
                        break;
                    } else {
                        codedOutputStreamWriter.output.writeUInt32(i13, unsafe.getInt(obj, j2));
                        break;
                    }
                case 12:
                    int[] iArr15 = iArr2;
                    unsafe = unsafe2;
                    i5 = i11;
                    i6 = i12;
                    iArr = iArr15;
                    z = false;
                    if (!messageSchema.isFieldPresent(i5, obj, i3, i6, i4)) {
                        break;
                    } else {
                        codedOutputStreamWriter.output.writeInt32(i13, unsafe.getInt(obj, j2));
                        break;
                    }
                case 13:
                    int[] iArr16 = iArr2;
                    unsafe = unsafe2;
                    i5 = i11;
                    i6 = i12;
                    iArr = iArr16;
                    z = false;
                    if (!messageSchema.isFieldPresent(i5, obj, i3, i6, i4)) {
                        break;
                    } else {
                        codedOutputStreamWriter.output.writeFixed32(i13, unsafe.getInt(obj, j2));
                        break;
                    }
                case 14:
                    int[] iArr17 = iArr2;
                    unsafe = unsafe2;
                    i5 = i11;
                    i6 = i12;
                    iArr = iArr17;
                    z = false;
                    if (!messageSchema.isFieldPresent(i5, obj, i3, i6, i4)) {
                        break;
                    } else {
                        codedOutputStreamWriter.output.writeFixed64(i13, unsafe.getLong(obj, j2));
                        break;
                    }
                case 15:
                    int[] iArr18 = iArr2;
                    unsafe = unsafe2;
                    i5 = i11;
                    i6 = i12;
                    iArr = iArr18;
                    z = false;
                    if (!messageSchema.isFieldPresent(i5, obj, i3, i6, i4)) {
                        break;
                    } else {
                        int i18 = unsafe.getInt(obj, j2);
                        codedOutputStreamWriter.output.writeUInt32(i13, (i18 >> 31) ^ (i18 << 1));
                        break;
                    }
                case 16:
                    int[] iArr19 = iArr2;
                    unsafe = unsafe2;
                    i5 = i11;
                    i6 = i12;
                    iArr = iArr19;
                    z = false;
                    if (!messageSchema.isFieldPresent(i5, obj, i3, i6, i4)) {
                        z6 = true;
                        break;
                    } else {
                        long j3 = unsafe.getLong(obj, j2);
                        z6 = true;
                        codedOutputStreamWriter.output.writeUInt64(i13, (j3 << 1) ^ (j3 >> 63));
                        break;
                    }
                case 17:
                    int[] iArr20 = iArr2;
                    unsafe = unsafe2;
                    i5 = i11;
                    i6 = i12;
                    iArr = iArr20;
                    z = false;
                    if (messageSchema.isFieldPresent(i5, obj, i3, i6, i4)) {
                        codedOutputStreamWriter.writeGroup(i13, unsafe.getObject(obj, j2), messageSchema.getMessageFieldSchema(i5));
                    }
                    z6 = true;
                    break;
                case 18:
                    z2 = false;
                    SchemaUtil.writeDoubleList(iArr2[i11], (List) unsafe2.getObject(obj, j2), codedOutputStreamWriter, false);
                    int[] iArr21 = iArr2;
                    unsafe = unsafe2;
                    i5 = i11;
                    i6 = i12;
                    iArr = iArr21;
                    z = z2;
                    z6 = true;
                    break;
                case 19:
                    z2 = false;
                    SchemaUtil.writeFloatList(iArr2[i11], (List) unsafe2.getObject(obj, j2), codedOutputStreamWriter, false);
                    int[] iArr212 = iArr2;
                    unsafe = unsafe2;
                    i5 = i11;
                    i6 = i12;
                    iArr = iArr212;
                    z = z2;
                    z6 = true;
                    break;
                case 20:
                    z2 = false;
                    SchemaUtil.writeInt64List(iArr2[i11], (List) unsafe2.getObject(obj, j2), codedOutputStreamWriter, false);
                    int[] iArr2122 = iArr2;
                    unsafe = unsafe2;
                    i5 = i11;
                    i6 = i12;
                    iArr = iArr2122;
                    z = z2;
                    z6 = true;
                    break;
                case 21:
                    z2 = false;
                    SchemaUtil.writeUInt64List(iArr2[i11], (List) unsafe2.getObject(obj, j2), codedOutputStreamWriter, false);
                    int[] iArr21222 = iArr2;
                    unsafe = unsafe2;
                    i5 = i11;
                    i6 = i12;
                    iArr = iArr21222;
                    z = z2;
                    z6 = true;
                    break;
                case 22:
                    z2 = false;
                    SchemaUtil.writeInt32List(iArr2[i11], (List) unsafe2.getObject(obj, j2), codedOutputStreamWriter, false);
                    int[] iArr212222 = iArr2;
                    unsafe = unsafe2;
                    i5 = i11;
                    i6 = i12;
                    iArr = iArr212222;
                    z = z2;
                    z6 = true;
                    break;
                case 23:
                    z2 = false;
                    SchemaUtil.writeFixed64List(iArr2[i11], (List) unsafe2.getObject(obj, j2), codedOutputStreamWriter, false);
                    int[] iArr2122222 = iArr2;
                    unsafe = unsafe2;
                    i5 = i11;
                    i6 = i12;
                    iArr = iArr2122222;
                    z = z2;
                    z6 = true;
                    break;
                case 24:
                    z2 = false;
                    SchemaUtil.writeFixed32List(iArr2[i11], (List) unsafe2.getObject(obj, j2), codedOutputStreamWriter, false);
                    int[] iArr21222222 = iArr2;
                    unsafe = unsafe2;
                    i5 = i11;
                    i6 = i12;
                    iArr = iArr21222222;
                    z = z2;
                    z6 = true;
                    break;
                case 25:
                    z2 = false;
                    SchemaUtil.writeBoolList(iArr2[i11], (List) unsafe2.getObject(obj, j2), codedOutputStreamWriter, false);
                    int[] iArr212222222 = iArr2;
                    unsafe = unsafe2;
                    i5 = i11;
                    i6 = i12;
                    iArr = iArr212222222;
                    z = z2;
                    z6 = true;
                    break;
                case 26:
                    SchemaUtil.writeStringList(iArr2[i11], (List) unsafe2.getObject(obj, j2), codedOutputStreamWriter);
                    int[] iArr22 = iArr2;
                    unsafe = unsafe2;
                    i5 = i11;
                    i6 = i12;
                    iArr = iArr22;
                    z = false;
                    z6 = true;
                    break;
                case 27:
                    SchemaUtil.writeMessageList(iArr2[i11], (List) unsafe2.getObject(obj, j2), codedOutputStreamWriter, messageSchema.getMessageFieldSchema(i11));
                    int[] iArr222 = iArr2;
                    unsafe = unsafe2;
                    i5 = i11;
                    i6 = i12;
                    iArr = iArr222;
                    z = false;
                    z6 = true;
                    break;
                case 28:
                    SchemaUtil.writeBytesList(iArr2[i11], (List) unsafe2.getObject(obj, j2), codedOutputStreamWriter);
                    int[] iArr2222 = iArr2;
                    unsafe = unsafe2;
                    i5 = i11;
                    i6 = i12;
                    iArr = iArr2222;
                    z = false;
                    z6 = true;
                    break;
                case 29:
                    z2 = false;
                    SchemaUtil.writeUInt32List(iArr2[i11], (List) unsafe2.getObject(obj, j2), codedOutputStreamWriter, false);
                    int[] iArr2122222222 = iArr2;
                    unsafe = unsafe2;
                    i5 = i11;
                    i6 = i12;
                    iArr = iArr2122222222;
                    z = z2;
                    z6 = true;
                    break;
                case 30:
                    z2 = false;
                    SchemaUtil.writeEnumList(iArr2[i11], (List) unsafe2.getObject(obj, j2), codedOutputStreamWriter, false);
                    int[] iArr21222222222 = iArr2;
                    unsafe = unsafe2;
                    i5 = i11;
                    i6 = i12;
                    iArr = iArr21222222222;
                    z = z2;
                    z6 = true;
                    break;
                case 31:
                    z2 = false;
                    SchemaUtil.writeSFixed32List(iArr2[i11], (List) unsafe2.getObject(obj, j2), codedOutputStreamWriter, false);
                    int[] iArr212222222222 = iArr2;
                    unsafe = unsafe2;
                    i5 = i11;
                    i6 = i12;
                    iArr = iArr212222222222;
                    z = z2;
                    z6 = true;
                    break;
                case 32:
                    z2 = false;
                    SchemaUtil.writeSFixed64List(iArr2[i11], (List) unsafe2.getObject(obj, j2), codedOutputStreamWriter, false);
                    int[] iArr2122222222222 = iArr2;
                    unsafe = unsafe2;
                    i5 = i11;
                    i6 = i12;
                    iArr = iArr2122222222222;
                    z = z2;
                    z6 = true;
                    break;
                case 33:
                    z2 = false;
                    SchemaUtil.writeSInt32List(iArr2[i11], (List) unsafe2.getObject(obj, j2), codedOutputStreamWriter, false);
                    int[] iArr21222222222222 = iArr2;
                    unsafe = unsafe2;
                    i5 = i11;
                    i6 = i12;
                    iArr = iArr21222222222222;
                    z = z2;
                    z6 = true;
                    break;
                case 34:
                    z2 = false;
                    SchemaUtil.writeSInt64List(iArr2[i11], (List) unsafe2.getObject(obj, j2), codedOutputStreamWriter, false);
                    int[] iArr212222222222222 = iArr2;
                    unsafe = unsafe2;
                    i5 = i11;
                    i6 = i12;
                    iArr = iArr212222222222222;
                    z = z2;
                    z6 = true;
                    break;
                case 35:
                    z3 = z6 ? 1 : 0;
                    SchemaUtil.writeDoubleList(iArr2[i11], (List) unsafe2.getObject(obj, j2), codedOutputStreamWriter, z3);
                    int[] iArr23 = iArr2;
                    unsafe = unsafe2;
                    i5 = i11;
                    i6 = i12;
                    iArr = iArr23;
                    z6 = z3;
                    z = false;
                    break;
                case 36:
                    z3 = z6 ? 1 : 0;
                    SchemaUtil.writeFloatList(iArr2[i11], (List) unsafe2.getObject(obj, j2), codedOutputStreamWriter, z3);
                    int[] iArr232 = iArr2;
                    unsafe = unsafe2;
                    i5 = i11;
                    i6 = i12;
                    iArr = iArr232;
                    z6 = z3;
                    z = false;
                    break;
                case 37:
                    z3 = z6 ? 1 : 0;
                    SchemaUtil.writeInt64List(iArr2[i11], (List) unsafe2.getObject(obj, j2), codedOutputStreamWriter, z3);
                    int[] iArr2322 = iArr2;
                    unsafe = unsafe2;
                    i5 = i11;
                    i6 = i12;
                    iArr = iArr2322;
                    z6 = z3;
                    z = false;
                    break;
                case 38:
                    z3 = z6 ? 1 : 0;
                    SchemaUtil.writeUInt64List(iArr2[i11], (List) unsafe2.getObject(obj, j2), codedOutputStreamWriter, z3);
                    int[] iArr23222 = iArr2;
                    unsafe = unsafe2;
                    i5 = i11;
                    i6 = i12;
                    iArr = iArr23222;
                    z6 = z3;
                    z = false;
                    break;
                case 39:
                    z3 = z6 ? 1 : 0;
                    SchemaUtil.writeInt32List(iArr2[i11], (List) unsafe2.getObject(obj, j2), codedOutputStreamWriter, z3);
                    int[] iArr232222 = iArr2;
                    unsafe = unsafe2;
                    i5 = i11;
                    i6 = i12;
                    iArr = iArr232222;
                    z6 = z3;
                    z = false;
                    break;
                case 40:
                    z3 = z6 ? 1 : 0;
                    SchemaUtil.writeFixed64List(iArr2[i11], (List) unsafe2.getObject(obj, j2), codedOutputStreamWriter, z3);
                    int[] iArr2322222 = iArr2;
                    unsafe = unsafe2;
                    i5 = i11;
                    i6 = i12;
                    iArr = iArr2322222;
                    z6 = z3;
                    z = false;
                    break;
                case 41:
                    z3 = z6 ? 1 : 0;
                    SchemaUtil.writeFixed32List(iArr2[i11], (List) unsafe2.getObject(obj, j2), codedOutputStreamWriter, z3);
                    int[] iArr23222222 = iArr2;
                    unsafe = unsafe2;
                    i5 = i11;
                    i6 = i12;
                    iArr = iArr23222222;
                    z6 = z3;
                    z = false;
                    break;
                case 42:
                    z3 = z6 ? 1 : 0;
                    SchemaUtil.writeBoolList(iArr2[i11], (List) unsafe2.getObject(obj, j2), codedOutputStreamWriter, z3);
                    int[] iArr232222222 = iArr2;
                    unsafe = unsafe2;
                    i5 = i11;
                    i6 = i12;
                    iArr = iArr232222222;
                    z6 = z3;
                    z = false;
                    break;
                case 43:
                    z3 = z6 ? 1 : 0;
                    SchemaUtil.writeUInt32List(iArr2[i11], (List) unsafe2.getObject(obj, j2), codedOutputStreamWriter, z3);
                    int[] iArr2322222222 = iArr2;
                    unsafe = unsafe2;
                    i5 = i11;
                    i6 = i12;
                    iArr = iArr2322222222;
                    z6 = z3;
                    z = false;
                    break;
                case 44:
                    z3 = z6 ? 1 : 0;
                    SchemaUtil.writeEnumList(iArr2[i11], (List) unsafe2.getObject(obj, j2), codedOutputStreamWriter, z3);
                    int[] iArr23222222222 = iArr2;
                    unsafe = unsafe2;
                    i5 = i11;
                    i6 = i12;
                    iArr = iArr23222222222;
                    z6 = z3;
                    z = false;
                    break;
                case 45:
                    z3 = z6 ? 1 : 0;
                    SchemaUtil.writeSFixed32List(iArr2[i11], (List) unsafe2.getObject(obj, j2), codedOutputStreamWriter, z3);
                    int[] iArr232222222222 = iArr2;
                    unsafe = unsafe2;
                    i5 = i11;
                    i6 = i12;
                    iArr = iArr232222222222;
                    z6 = z3;
                    z = false;
                    break;
                case 46:
                    z3 = z6 ? 1 : 0;
                    SchemaUtil.writeSFixed64List(iArr2[i11], (List) unsafe2.getObject(obj, j2), codedOutputStreamWriter, z3);
                    int[] iArr2322222222222 = iArr2;
                    unsafe = unsafe2;
                    i5 = i11;
                    i6 = i12;
                    iArr = iArr2322222222222;
                    z6 = z3;
                    z = false;
                    break;
                case 47:
                    z3 = z6 ? 1 : 0;
                    SchemaUtil.writeSInt32List(iArr2[i11], (List) unsafe2.getObject(obj, j2), codedOutputStreamWriter, z3);
                    int[] iArr23222222222222 = iArr2;
                    unsafe = unsafe2;
                    i5 = i11;
                    i6 = i12;
                    iArr = iArr23222222222222;
                    z6 = z3;
                    z = false;
                    break;
                case 48:
                    SchemaUtil.writeSInt64List(iArr2[i11], (List) unsafe2.getObject(obj, j2), codedOutputStreamWriter, z6);
                    int[] iArr24 = iArr2;
                    unsafe = unsafe2;
                    i5 = i11;
                    i6 = i12;
                    iArr = iArr24;
                    z = false;
                    break;
                case 49:
                    SchemaUtil.writeGroupList(iArr2[i11], (List) unsafe2.getObject(obj, j2), codedOutputStreamWriter, messageSchema.getMessageFieldSchema(i11));
                    int[] iArr242 = iArr2;
                    unsafe = unsafe2;
                    i5 = i11;
                    i6 = i12;
                    iArr = iArr242;
                    z = false;
                    break;
                case 50:
                    messageSchema.writeMapHelper(codedOutputStreamWriter, i13, unsafe2.getObject(obj, j2), i11);
                    int[] iArr2422 = iArr2;
                    unsafe = unsafe2;
                    i5 = i11;
                    i6 = i12;
                    iArr = iArr2422;
                    z = false;
                    break;
                case 51:
                    if (messageSchema.isOneofPresent(i13, i11, obj)) {
                        double dDoubleValue2 = ((Double) UnsafeUtil.getObject(j2, obj)).doubleValue();
                        CodedOutputStream codedOutputStream7 = codedOutputStreamWriter.output;
                        codedOutputStream7.getClass();
                        codedOutputStream7.writeFixed64(i13, Double.doubleToRawLongBits(dDoubleValue2));
                    }
                    int[] iArr24222 = iArr2;
                    unsafe = unsafe2;
                    i5 = i11;
                    i6 = i12;
                    iArr = iArr24222;
                    z = false;
                    break;
                case 52:
                    if (messageSchema.isOneofPresent(i13, i11, obj)) {
                        float fFloatValue2 = ((Float) UnsafeUtil.getObject(j2, obj)).floatValue();
                        CodedOutputStream codedOutputStream8 = codedOutputStreamWriter.output;
                        codedOutputStream8.getClass();
                        codedOutputStream8.writeFixed32(i13, Float.floatToRawIntBits(fFloatValue2));
                    }
                    int[] iArr242222 = iArr2;
                    unsafe = unsafe2;
                    i5 = i11;
                    i6 = i12;
                    iArr = iArr242222;
                    z = false;
                    break;
                case 53:
                    if (messageSchema.isOneofPresent(i13, i11, obj)) {
                        codedOutputStreamWriter.writeInt64(i13, oneofLongAt(j2, obj));
                    }
                    int[] iArr2422222 = iArr2;
                    unsafe = unsafe2;
                    i5 = i11;
                    i6 = i12;
                    iArr = iArr2422222;
                    z = false;
                    break;
                case 54:
                    if (messageSchema.isOneofPresent(i13, i11, obj)) {
                        codedOutputStreamWriter.output.writeUInt64(i13, oneofLongAt(j2, obj));
                    }
                    int[] iArr24222222 = iArr2;
                    unsafe = unsafe2;
                    i5 = i11;
                    i6 = i12;
                    iArr = iArr24222222;
                    z = false;
                    break;
                case 55:
                    if (messageSchema.isOneofPresent(i13, i11, obj)) {
                        codedOutputStreamWriter.writeInt32(i13, oneofIntAt(j2, obj));
                    }
                    int[] iArr242222222 = iArr2;
                    unsafe = unsafe2;
                    i5 = i11;
                    i6 = i12;
                    iArr = iArr242222222;
                    z = false;
                    break;
                case 56:
                    if (messageSchema.isOneofPresent(i13, i11, obj)) {
                        codedOutputStreamWriter.writeFixed64(i13, oneofLongAt(j2, obj));
                    }
                    int[] iArr2422222222 = iArr2;
                    unsafe = unsafe2;
                    i5 = i11;
                    i6 = i12;
                    iArr = iArr2422222222;
                    z = false;
                    break;
                case 57:
                    if (messageSchema.isOneofPresent(i13, i11, obj)) {
                        codedOutputStreamWriter.writeFixed32(i13, oneofIntAt(j2, obj));
                    }
                    int[] iArr24222222222 = iArr2;
                    unsafe = unsafe2;
                    i5 = i11;
                    i6 = i12;
                    iArr = iArr24222222222;
                    z = false;
                    break;
                case 58:
                    if (messageSchema.isOneofPresent(i13, i11, obj)) {
                        codedOutputStreamWriter.output.writeBool(i13, ((Boolean) UnsafeUtil.getObject(j2, obj)).booleanValue());
                    }
                    int[] iArr242222222222 = iArr2;
                    unsafe = unsafe2;
                    i5 = i11;
                    i6 = i12;
                    iArr = iArr242222222222;
                    z = false;
                    break;
                case 59:
                    if (messageSchema.isOneofPresent(i13, i11, obj)) {
                        writeString(i13, unsafe2.getObject(obj, j2), codedOutputStreamWriter);
                    }
                    int[] iArr2422222222222 = iArr2;
                    unsafe = unsafe2;
                    i5 = i11;
                    i6 = i12;
                    iArr = iArr2422222222222;
                    z = false;
                    break;
                case 60:
                    if (messageSchema.isOneofPresent(i13, i11, obj)) {
                        codedOutputStreamWriter.writeMessage(i13, unsafe2.getObject(obj, j2), messageSchema.getMessageFieldSchema(i11));
                    }
                    int[] iArr24222222222222 = iArr2;
                    unsafe = unsafe2;
                    i5 = i11;
                    i6 = i12;
                    iArr = iArr24222222222222;
                    z = false;
                    break;
                case 61:
                    if (messageSchema.isOneofPresent(i13, i11, obj)) {
                        codedOutputStreamWriter.writeBytes(i13, (ByteString) unsafe2.getObject(obj, j2));
                    }
                    int[] iArr242222222222222 = iArr2;
                    unsafe = unsafe2;
                    i5 = i11;
                    i6 = i12;
                    iArr = iArr242222222222222;
                    z = false;
                    break;
                case 62:
                    if (messageSchema.isOneofPresent(i13, i11, obj)) {
                        codedOutputStreamWriter.output.writeUInt32(i13, oneofIntAt(j2, obj));
                    }
                    int[] iArr2422222222222222 = iArr2;
                    unsafe = unsafe2;
                    i5 = i11;
                    i6 = i12;
                    iArr = iArr2422222222222222;
                    z = false;
                    break;
                case 63:
                    if (messageSchema.isOneofPresent(i13, i11, obj)) {
                        codedOutputStreamWriter.output.writeInt32(i13, oneofIntAt(j2, obj));
                    }
                    int[] iArr24222222222222222 = iArr2;
                    unsafe = unsafe2;
                    i5 = i11;
                    i6 = i12;
                    iArr = iArr24222222222222222;
                    z = false;
                    break;
                case 64:
                    if (messageSchema.isOneofPresent(i13, i11, obj)) {
                        codedOutputStreamWriter.output.writeFixed32(i13, oneofIntAt(j2, obj));
                    }
                    int[] iArr242222222222222222 = iArr2;
                    unsafe = unsafe2;
                    i5 = i11;
                    i6 = i12;
                    iArr = iArr242222222222222222;
                    z = false;
                    break;
                case 65:
                    if (messageSchema.isOneofPresent(i13, i11, obj)) {
                        codedOutputStreamWriter.output.writeFixed64(i13, oneofLongAt(j2, obj));
                    }
                    int[] iArr2422222222222222222 = iArr2;
                    unsafe = unsafe2;
                    i5 = i11;
                    i6 = i12;
                    iArr = iArr2422222222222222222;
                    z = false;
                    break;
                case 66:
                    if (messageSchema.isOneofPresent(i13, i11, obj)) {
                        int iOneofIntAt2 = oneofIntAt(j2, obj);
                        codedOutputStreamWriter.output.writeUInt32(i13, (iOneofIntAt2 >> 31) ^ (iOneofIntAt2 << 1));
                    }
                    int[] iArr24222222222222222222 = iArr2;
                    unsafe = unsafe2;
                    i5 = i11;
                    i6 = i12;
                    iArr = iArr24222222222222222222;
                    z = false;
                    break;
                case 67:
                    if (messageSchema.isOneofPresent(i13, i11, obj)) {
                        long jOneofLongAt2 = oneofLongAt(j2, obj);
                        codedOutputStreamWriter.output.writeUInt64(i13, (jOneofLongAt2 << (z6 ? 1L : 0L)) ^ (jOneofLongAt2 >> 63));
                    }
                    int[] iArr242222222222222222222 = iArr2;
                    unsafe = unsafe2;
                    i5 = i11;
                    i6 = i12;
                    iArr = iArr242222222222222222222;
                    z = false;
                    break;
                case 68:
                    if (messageSchema.isOneofPresent(i13, i11, obj)) {
                        codedOutputStreamWriter.writeGroup(i13, unsafe2.getObject(obj, j2), messageSchema.getMessageFieldSchema(i11));
                    }
                    int[] iArr2422222222222222222222 = iArr2;
                    unsafe = unsafe2;
                    i5 = i11;
                    i6 = i12;
                    iArr = iArr2422222222222222222222;
                    z = false;
                    break;
                default:
                    int[] iArr24222222222222222222222 = iArr2;
                    unsafe = unsafe2;
                    i5 = i11;
                    i6 = i12;
                    iArr = iArr24222222222222222222222;
                    z = false;
                    break;
            }
            int i19 = i5 + 3;
            i10 = i3;
            unsafe2 = unsafe;
            entry = entry2;
            iArr2 = iArr;
            z5 = z6;
            i7 = i;
            i12 = i6;
            i11 = i19;
            messageSchema = this;
        }
        while (entry != null) {
            extensionSchema.serializeExtension(codedOutputStreamWriter, entry);
            entry = it.hasNext() ? (Map.Entry) it.next() : null;
        }
        unknownFieldSchema.writeTo(unknownFieldSchema.getFromMessage(obj), codedOutputStreamWriter);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:11:0x0025  */
    @Override // androidx.datastore.preferences.protobuf.Schema
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void mergeFrom(Object obj, Object obj2) {
        Object obj3;
        if (isMutable(obj)) {
            obj2.getClass();
            int i = 0;
            while (true) {
                int[] iArr = this.buffer;
                if (i < iArr.length) {
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
                            ((ListFieldSchemaLite) this.listFieldSchema).getClass();
                            Internal.ProtobufList protobufListMutableCopyWithCapacity = (Internal.ProtobufList) UnsafeUtil.getObject(j, obj);
                            Internal.ProtobufList protobufList = (Internal.ProtobufList) UnsafeUtil.getObject(j, obj2);
                            int size = protobufListMutableCopyWithCapacity.size();
                            int size2 = protobufList.size();
                            if (size > 0 && size2 > 0) {
                                if (!((AbstractProtobufList) protobufListMutableCopyWithCapacity).isMutable) {
                                    protobufListMutableCopyWithCapacity = protobufListMutableCopyWithCapacity.mutableCopyWithCapacity(size2 + size);
                                }
                                protobufListMutableCopyWithCapacity.addAll(protobufList);
                            }
                            if (size > 0) {
                                protobufList = protobufListMutableCopyWithCapacity;
                            }
                            UnsafeUtil.putObject(j, obj, protobufList);
                            obj3 = obj;
                            break;
                        case 50:
                            Class cls = SchemaUtil.GENERATED_MESSAGE_CLASS;
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
                } else {
                    Object obj4 = obj;
                    Class cls2 = SchemaUtil.GENERATED_MESSAGE_CLASS;
                    UnknownFieldSchema unknownFieldSchema = this.unknownFieldSchema;
                    unknownFieldSchema.setToMessage(obj4, unknownFieldSchema.merge(unknownFieldSchema.getFromMessage(obj4), unknownFieldSchema.getFromMessage(obj2)));
                    if (this.hasExtensions) {
                        SchemaUtil.mergeExtensions(this.extensionSchema, obj4, obj2);
                        return;
                    }
                    return;
                }
            }
        } else {
            throw new IllegalArgumentException("Mutating immutable message: " + obj);
        }
    }

    public final boolean isFieldPresent(int i, Object obj, int i2, int i3, int i4) {
        if (i2 == 1048575) {
            return isFieldPresent(i, obj);
        }
        return (i3 & i4) != 0;
    }
}
