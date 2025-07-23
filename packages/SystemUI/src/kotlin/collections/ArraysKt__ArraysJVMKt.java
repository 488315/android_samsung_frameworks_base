package kotlin.collections;

import androidx.compose.runtime.collection.MutableVectorKt$$ExternalSyntheticOutline0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class ArraysKt__ArraysJVMKt {
    public static final void copyOfRangeToIndexCheck(int i, int i2) {
        if (i > i2) {
            throw new IndexOutOfBoundsException(MutableVectorKt$$ExternalSyntheticOutline0.m(i, i2, "toIndex (", ") is greater than size (", ")."));
        }
    }
}
