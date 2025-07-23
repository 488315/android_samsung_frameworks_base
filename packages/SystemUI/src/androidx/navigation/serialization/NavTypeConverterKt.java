package androidx.navigation.serialization;

import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsJVMKt;
import kotlinx.serialization.descriptors.SerialDescriptor;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class NavTypeConverterKt {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[InternalType.values().length];
            try {
                iArr[InternalType.INT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[InternalType.BOOL.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[InternalType.FLOAT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[InternalType.LONG.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[InternalType.STRING.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[InternalType.INT_ARRAY.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr[InternalType.BOOL_ARRAY.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr[InternalType.FLOAT_ARRAY.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                iArr[InternalType.LONG_ARRAY.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                iArr[InternalType.ARRAY.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                iArr[InternalType.LIST.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public static final InternalType toInternalType(SerialDescriptor serialDescriptor) {
        String replace$default = StringsKt__StringsJVMKt.replace$default(serialDescriptor.getSerialName(), "?", "");
        return Intrinsics.areEqual(replace$default, "kotlin.Int") ? InternalType.INT : Intrinsics.areEqual(replace$default, "kotlin.Boolean") ? InternalType.BOOL : Intrinsics.areEqual(replace$default, "kotlin.Float") ? InternalType.FLOAT : Intrinsics.areEqual(replace$default, "kotlin.Long") ? InternalType.LONG : Intrinsics.areEqual(replace$default, "kotlin.String") ? InternalType.STRING : Intrinsics.areEqual(replace$default, "kotlin.IntArray") ? InternalType.INT_ARRAY : Intrinsics.areEqual(replace$default, "kotlin.BooleanArray") ? InternalType.BOOL_ARRAY : Intrinsics.areEqual(replace$default, "kotlin.FloatArray") ? InternalType.FLOAT_ARRAY : Intrinsics.areEqual(replace$default, "kotlin.LongArray") ? InternalType.LONG_ARRAY : Intrinsics.areEqual(replace$default, "kotlin.Array") ? InternalType.ARRAY : replace$default.startsWith("kotlin.collections.ArrayList") ? InternalType.LIST : InternalType.UNKNOWN;
    }
}
