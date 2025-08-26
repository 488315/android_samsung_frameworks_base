package kotlin.collections;

import androidx.compose.runtime.collection.MutableVectorKt$$ExternalSyntheticOutline0;

/* loaded from: classes4.dex */
public class ArraysKt__ArraysJVMKt {
    public static final void copyOfRangeToIndexCheck(int i, int i2) {
        if (i > i2) {
            throw new IndexOutOfBoundsException(MutableVectorKt$$ExternalSyntheticOutline0.m(i, i2, "toIndex (", ") is greater than size (", ")."));
        }
    }
}
