package kotlin.collections;

import java.util.Arrays;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public final class ArraysKt extends ArraysKt___ArraysKt {
    public static void copyInto(int i, int i2, int i3, int[] iArr, int[] destination) {
        Intrinsics.checkNotNullParameter(iArr, "<this>");
        Intrinsics.checkNotNullParameter(destination, "destination");
        System.arraycopy(iArr, i2, destination, i, i3 - i2);
    }

    public static /* synthetic */ void copyInto$default(Object[] objArr, Object[] objArr2, int i, int i2, int i3) {
        if ((i3 & 4) != 0) {
            i = 0;
        }
        if ((i3 & 8) != 0) {
            i2 = objArr.length;
        }
        copyInto(objArr, objArr2, 0, i, i2);
    }

    public static Object[] copyOfRange(int i, Object[] objArr) {
        Intrinsics.checkNotNullParameter(objArr, "<this>");
        int length = objArr.length;
        if (i <= length) {
            Object[] copyOfRange = Arrays.copyOfRange(objArr, 0, i);
            Intrinsics.checkNotNullExpressionValue(copyOfRange, "copyOfRange(this, fromIndex, toIndex)");
            return copyOfRange;
        }
        throw new IndexOutOfBoundsException("toIndex (" + i + ") is greater than size (" + length + ").");
    }

    public static void copyInto(Object[] objArr, Object[] destination, int i, int i2, int i3) {
        Intrinsics.checkNotNullParameter(objArr, "<this>");
        Intrinsics.checkNotNullParameter(destination, "destination");
        System.arraycopy(objArr, i2, destination, i, i3 - i2);
    }
}
