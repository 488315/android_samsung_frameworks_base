package com.samsung.android.sume.core.types;

import android.security.keystore.KeyProperties;
import com.samsung.android.sume.core.Def;
import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.function.Predicate;
import java.util.function.Supplier;

/* JADX WARN: Enum visitor error
jadx.core.utils.exceptions.JadxRuntimeException: Init of enum field 'U8C1' uses external variables
	at jadx.core.dex.visitors.EnumVisitor.createEnumFieldByConstructor(EnumVisitor.java:451)
	at jadx.core.dex.visitors.EnumVisitor.processEnumFieldByField(EnumVisitor.java:372)
	at jadx.core.dex.visitors.EnumVisitor.processEnumFieldByWrappedInsn(EnumVisitor.java:337)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromFilledArray(EnumVisitor.java:322)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInsn(EnumVisitor.java:262)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInvoke(EnumVisitor.java:293)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInsn(EnumVisitor.java:266)
	at jadx.core.dex.visitors.EnumVisitor.convertToEnum(EnumVisitor.java:151)
	at jadx.core.dex.visitors.EnumVisitor.visit(EnumVisitor.java:100)
 */
/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* loaded from: classes6.dex */
public final class DataType implements NumericEnum {
    private static final /* synthetic */ DataType[] $VALUES;
    static final int DT_CN_SHIFT = 4;
    static final int DT_SET_MASK = 15;
    private static final int DT_SET_MAX = 16;
    public static final DataType F16;
    public static final DataType F16C1;
    public static final DataType F16C2;
    public static final DataType F16C3;
    public static final DataType F16C4;
    public static final DataType F32;
    public static final DataType F32C1;
    public static final DataType F32C2;
    public static final DataType F32C3;
    public static final DataType F32C4;
    public static final DataType F64;
    public static final DataType F64C1;
    public static final DataType F64C2;
    public static final DataType F64C3;
    public static final DataType F64C4;
    public static final DataType MAX_DEPTH;
    public static final DataType NONE = new DataType(KeyProperties.DIGEST_NONE, 0, -1);
    public static final DataType S16;
    public static final DataType S16C1;
    public static final DataType S16C2;
    public static final DataType S16C3;
    public static final DataType S16C4;
    public static final DataType S32;
    public static final DataType S32C1;
    public static final DataType S32C2;
    public static final DataType S32C3;
    public static final DataType S32C4;
    public static final DataType S64;
    public static final DataType S64C1;
    public static final DataType S64C2;
    public static final DataType S64C3;
    public static final DataType S64C4;
    public static final DataType S8;
    public static final DataType S8C1;
    public static final DataType S8C2;
    public static final DataType S8C3;
    public static final DataType S8C4;
    public static final DataType U16;
    public static final DataType U16C1;
    public static final DataType U16C2;
    public static final DataType U16C3;
    public static final DataType U16C4;
    public static final DataType U32;
    public static final DataType U32C1;
    public static final DataType U32C2;
    public static final DataType U32C3;
    public static final DataType U32C4;
    public static final DataType U64;
    public static final DataType U64C1;
    public static final DataType U64C2;
    public static final DataType U64C3;
    public static final DataType U64C4;
    public static final DataType U8;
    public static final DataType U8C1;
    public static final DataType U8C2;
    public static final DataType U8C3;
    public static final DataType U8C4;
    private final int value;

    private static /* synthetic */ DataType[] $values() {
        return new DataType[]{NONE, U8, S8, U16, S16, U32, S32, U64, S64, F16, F32, F64, MAX_DEPTH, U8C1, U8C2, U8C3, U8C4, S8C1, S8C2, S8C3, S8C4, U16C1, U16C2, U16C3, U16C4, S16C1, S16C2, S16C3, S16C4, U32C1, U32C2, U32C3, U32C4, S32C1, S32C2, S32C3, S32C4, U64C1, U64C2, U64C3, U64C4, S64C1, S64C2, S64C3, S64C4, F16C1, F16C2, F16C3, F16C4, F32C1, F32C2, F32C3, F32C4, F64C1, F64C2, F64C3, F64C4};
    }

    public static DataType valueOf(String str) {
        return (DataType) Enum.valueOf(DataType.class, str);
    }

    public static DataType[] values() {
        return (DataType[]) $VALUES.clone();
    }

    static {
        DataType dataType = new DataType("U8", 1, 0);
        U8 = dataType;
        DataType dataType2 = new DataType("S8", 2, 1);
        S8 = dataType2;
        DataType dataType3 = new DataType("U16", 3, 2);
        U16 = dataType3;
        DataType dataType4 = new DataType("S16", 4, 3);
        S16 = dataType4;
        DataType dataType5 = new DataType("U32", 5, 4);
        U32 = dataType5;
        DataType dataType6 = new DataType("S32", 6, 5);
        S32 = dataType6;
        DataType dataType7 = new DataType("U64", 7, 6);
        U64 = dataType7;
        DataType dataType8 = new DataType("S64", 8, 7);
        S64 = dataType8;
        DataType dataType9 = new DataType("F16", 9, 8);
        F16 = dataType9;
        DataType dataType10 = new DataType("F32", 10, 9);
        F32 = dataType10;
        DataType dataType11 = new DataType("F64", 11, 10);
        F64 = dataType11;
        MAX_DEPTH = new DataType("MAX_DEPTH", 12, 15);
        U8C1 = new DataType("U8C1", 13, makeType(dataType, 1));
        U8C2 = new DataType("U8C2", 14, makeType(dataType, 2));
        U8C3 = new DataType("U8C3", 15, makeType(dataType, 3));
        U8C4 = new DataType("U8C4", 16, makeType(dataType, 4));
        S8C1 = new DataType("S8C1", 17, makeType(dataType2, 1));
        S8C2 = new DataType("S8C2", 18, makeType(dataType2, 2));
        S8C3 = new DataType("S8C3", 19, makeType(dataType2, 3));
        S8C4 = new DataType("S8C4", 20, makeType(dataType2, 4));
        U16C1 = new DataType("U16C1", 21, makeType(dataType3, 1));
        U16C2 = new DataType("U16C2", 22, makeType(dataType3, 2));
        U16C3 = new DataType("U16C3", 23, makeType(dataType3, 3));
        U16C4 = new DataType("U16C4", 24, makeType(dataType3, 4));
        S16C1 = new DataType("S16C1", 25, makeType(dataType4, 1));
        S16C2 = new DataType("S16C2", 26, makeType(dataType4, 2));
        S16C3 = new DataType("S16C3", 27, makeType(dataType4, 3));
        S16C4 = new DataType("S16C4", 28, makeType(dataType4, 4));
        U32C1 = new DataType("U32C1", 29, makeType(dataType5, 1));
        U32C2 = new DataType("U32C2", 30, makeType(dataType5, 2));
        U32C3 = new DataType("U32C3", 31, makeType(dataType5, 3));
        U32C4 = new DataType("U32C4", 32, makeType(dataType5, 4));
        S32C1 = new DataType("S32C1", 33, makeType(dataType6, 1));
        S32C2 = new DataType("S32C2", 34, makeType(dataType6, 2));
        S32C3 = new DataType("S32C3", 35, makeType(dataType6, 3));
        S32C4 = new DataType("S32C4", 36, makeType(dataType6, 4));
        U64C1 = new DataType("U64C1", 37, makeType(dataType7, 1));
        U64C2 = new DataType("U64C2", 38, makeType(dataType7, 2));
        U64C3 = new DataType("U64C3", 39, makeType(dataType7, 3));
        U64C4 = new DataType("U64C4", 40, makeType(dataType7, 4));
        S64C1 = new DataType("S64C1", 41, makeType(dataType8, 1));
        S64C2 = new DataType("S64C2", 42, makeType(dataType8, 2));
        S64C3 = new DataType("S64C3", 43, makeType(dataType8, 3));
        S64C4 = new DataType("S64C4", 44, makeType(dataType8, 4));
        F16C1 = new DataType("F16C1", 45, makeType(dataType9, 1));
        F16C2 = new DataType("F16C2", 46, makeType(dataType9, 2));
        F16C3 = new DataType("F16C3", 47, makeType(dataType9, 3));
        F16C4 = new DataType("F16C4", 48, makeType(dataType9, 4));
        F32C1 = new DataType("F32C1", 49, makeType(dataType10, 1));
        F32C2 = new DataType("F32C2", 50, makeType(dataType10, 2));
        F32C3 = new DataType("F32C3", 51, makeType(dataType10, 3));
        F32C4 = new DataType("F32C4", 52, makeType(dataType10, 4));
        F64C1 = new DataType("F64C1", 53, makeType(dataType11, 1));
        F64C2 = new DataType("F64C2", 54, makeType(dataType11, 2));
        F64C3 = new DataType("F64C3", 55, makeType(dataType11, 3));
        F64C4 = new DataType("F64C4", 56, makeType(dataType11, 4));
        $VALUES = $values();
    }

    public static DataType of(DataType dataType, int i) {
        return from(makeType(dataType, i));
    }

    private static int makeType(DataType dataType, int i) {
        Def.require(dataType != NONE && dataType.getValue() <= MAX_DEPTH.getValue(), "1st argument is not depth", new Object[0]);
        return (dataType.getValue() & 15) + (i << 4);
    }

    @Override // com.samsung.android.sume.core.types.NumericEnum
    public int getValue() {
        return this.value;
    }

    @Override // com.samsung.android.sume.core.types.NumericEnum
    public String stringfy() {
        return name() + ":" + this.value;
    }

    private DataType(String str, int i, int i2) {
        this.value = i2;
    }

    public DataType depth() {
        return from(this.value & 15);
    }

    public int channels() {
        return this.value >> 4;
    }

    /* renamed from: com.samsung.android.sume.core.types.DataType$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$samsung$android$sume$core$types$DataType;

        static {
            int[] iArr = new int[DataType.values().length];
            $SwitchMap$com$samsung$android$sume$core$types$DataType = iArr;
            try {
                iArr[DataType.U8.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$samsung$android$sume$core$types$DataType[DataType.S8.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$samsung$android$sume$core$types$DataType[DataType.U16.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$samsung$android$sume$core$types$DataType[DataType.S16.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$samsung$android$sume$core$types$DataType[DataType.U32.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$samsung$android$sume$core$types$DataType[DataType.S32.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$samsung$android$sume$core$types$DataType[DataType.F16.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$samsung$android$sume$core$types$DataType[DataType.F32.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$samsung$android$sume$core$types$DataType[DataType.U64.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$samsung$android$sume$core$types$DataType[DataType.S64.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$samsung$android$sume$core$types$DataType[DataType.F64.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
        }
    }

    public float size() {
        switch (AnonymousClass1.$SwitchMap$com$samsung$android$sume$core$types$DataType[depth().ordinal()]) {
            case 1:
            case 2:
                return 1.0f;
            case 3:
            case 4:
                return 2.0f;
            case 5:
            case 6:
            case 7:
            case 8:
                return 4.0f;
            case 9:
            case 10:
            case 11:
                return 8.0f;
            default:
                return 0.0f;
        }
    }

    public boolean isByte() {
        return depth() == U8 || depth() == S8;
    }

    public boolean isShort() {
        return depth() == U16 || depth() == S16;
    }

    public boolean isInt() {
        return depth() == U32 || depth() == S32;
    }

    public boolean isLong() {
        return depth() == U64 || depth() == S64;
    }

    public boolean isFloat() {
        return depth() == F16 || depth() == F32 || depth() == F64;
    }

    public static float size(DataType dataType) {
        return dataType.size();
    }

    private static DataType from(final int i) {
        return (DataType) Arrays.stream(values()).filter(new Predicate() { // from class: com.samsung.android.sume.core.types.DataType$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return DataType.lambda$from$0(i, (DataType) obj);
            }
        }).findFirst().orElseThrow(new Supplier() { // from class: com.samsung.android.sume.core.types.DataType$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final Object get() {
                return DataType.lambda$from$1(i);
            }
        });
    }

    static /* synthetic */ boolean lambda$from$0(int i, DataType dataType) {
        return dataType.getValue() == i;
    }

    static /* synthetic */ InvalidParameterException lambda$from$1(int i) {
        return new InvalidParameterException("invalid DataType value: " + i);
    }
}
