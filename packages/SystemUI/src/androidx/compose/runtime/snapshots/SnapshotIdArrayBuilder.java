package androidx.compose.runtime.snapshots;

import androidx.collection.MutableLongList;
import androidx.collection.internal.RuntimeHelpersKt;
import java.util.Arrays;
import kotlin.collections.ArraysKt___ArraysJvmKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SnapshotIdArrayBuilder {
    public final MutableLongList list;

    public SnapshotIdArrayBuilder(long[] jArr) {
        MutableLongList mutableLongList;
        if (jArr != null) {
            long[] copyOf = Arrays.copyOf(jArr, jArr.length);
            mutableLongList = new MutableLongList(copyOf.length);
            int i = mutableLongList._size;
            if (i < 0) {
                RuntimeHelpersKt.throwIndexOutOfBoundsException("");
                throw null;
            }
            if (copyOf.length != 0) {
                int length = copyOf.length + i;
                long[] jArr2 = mutableLongList.content;
                if (jArr2.length < length) {
                    mutableLongList.content = Arrays.copyOf(jArr2, Math.max(length, (jArr2.length * 3) / 2));
                }
                long[] jArr3 = mutableLongList.content;
                int i2 = mutableLongList._size;
                if (i != i2) {
                    ArraysKt___ArraysJvmKt.copyInto(jArr3, jArr3, copyOf.length + i, i, i2);
                }
                System.arraycopy(copyOf, 0, jArr3, i, copyOf.length);
                mutableLongList._size += copyOf.length;
            }
        } else {
            mutableLongList = new MutableLongList(0, 1, null);
        }
        this.list = mutableLongList;
    }
}
