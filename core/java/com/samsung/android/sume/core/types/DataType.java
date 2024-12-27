package com.samsung.android.sume.core.types;

import com.samsung.android.sume.core.Def;
import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.function.Predicate;
import java.util.function.Supplier;

/* loaded from: classes6.dex */
public enum DataType implements NumericEnum {
    NONE(-1),
    U8(0),
    S8(1),
    U16(2),
    S16(3),
    U32(4),
    S32(5),
    U64(6),
    S64(7),
    F16(8),
    F32(9),
    F64(10),
    MAX_DEPTH(15),
    U8C1(makeType(U8, 1)),
    U8C2(makeType(U8, 2)),
    U8C3(makeType(U8, 3)),
    U8C4(makeType(U8, 4)),
    S8C1(makeType(S8, 1)),
    S8C2(makeType(S8, 2)),
    S8C3(makeType(S8, 3)),
    S8C4(makeType(S8, 4)),
    U16C1(makeType(U16, 1)),
    U16C2(makeType(U16, 2)),
    U16C3(makeType(U16, 3)),
    U16C4(makeType(U16, 4)),
    S16C1(makeType(S16, 1)),
    S16C2(makeType(S16, 2)),
    S16C3(makeType(S16, 3)),
    S16C4(makeType(S16, 4)),
    U32C1(makeType(U32, 1)),
    U32C2(makeType(U32, 2)),
    U32C3(makeType(U32, 3)),
    U32C4(makeType(U32, 4)),
    S32C1(makeType(S32, 1)),
    S32C2(makeType(S32, 2)),
    S32C3(makeType(S32, 3)),
    S32C4(makeType(S32, 4)),
    U64C1(makeType(U64, 1)),
    U64C2(makeType(U64, 2)),
    U64C3(makeType(U64, 3)),
    U64C4(makeType(U64, 4)),
    S64C1(makeType(S64, 1)),
    S64C2(makeType(S64, 2)),
    S64C3(makeType(S64, 3)),
    S64C4(makeType(S64, 4)),
    F16C1(makeType(F16, 1)),
    F16C2(makeType(F16, 2)),
    F16C3(makeType(F16, 3)),
    F16C4(makeType(F16, 4)),
    F32C1(makeType(F32, 1)),
    F32C2(makeType(F32, 2)),
    F32C3(makeType(F32, 3)),
    F32C4(makeType(F32, 4)),
    F64C1(makeType(F64, 1)),
    F64C2(makeType(F64, 2)),
    F64C3(makeType(F64, 3)),
    F64C4(makeType(F64, 4));

    static final int DT_CN_SHIFT = 4;
    static final int DT_SET_MASK = 15;
    private static final int DT_SET_MAX = 16;
    private final int value;

    public static DataType of(DataType depth, int cn) {
        return from(makeType(depth, cn));
    }

    private static int makeType(DataType depth, int cn) {
        Def.require(depth != NONE && depth.getValue() <= MAX_DEPTH.getValue(), "1st argument is not depth", new Object[0]);
        return (depth.getValue() & 15) + (cn << 4);
    }

    @Override // com.samsung.android.sume.core.types.NumericEnum
    public int getValue() {
        return this.value;
    }

    @Override // com.samsung.android.sume.core.types.NumericEnum
    public String stringfy() {
        return name() + ":" + this.value;
    }

    DataType(int value) {
        this.value = value;
    }

    public DataType depth() {
        return from(this.value & 15);
    }

    public int channels() {
        return this.value >> 4;
    }

    public float size() {
        switch (depth()) {
        }
        return 4.0f;
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

    private static DataType from(final int value) {
        return (DataType) Arrays.stream(values()).filter(new Predicate() { // from class: com.samsung.android.sume.core.types.DataType$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return DataType.lambda$from$0(value, (DataType) obj);
            }
        }).findFirst().orElseThrow(new Supplier() { // from class: com.samsung.android.sume.core.types.DataType$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final Object get() {
                return DataType.lambda$from$1(value);
            }
        });
    }

    static /* synthetic */ boolean lambda$from$0(int value, DataType e) {
        return e.getValue() == value;
    }

    static /* synthetic */ InvalidParameterException lambda$from$1(int value) {
        return new InvalidParameterException("invalid DataType value: " + value);
    }
}
