package kotlin.collections;

import androidx.recyclerview.widget.SeslRecyclerViewFastScroller$$ExternalSyntheticOutline0;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class ArraysKt__ArraysJVMKt {
    public static final void copyOfRangeToIndexCheck(int i, int i2) {
        if (i > i2) {
            throw new IndexOutOfBoundsException(SeslRecyclerViewFastScroller$$ExternalSyntheticOutline0.m47m("toIndex (", i, ") is greater than size (", i2, ")."));
        }
    }
}
