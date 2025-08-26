package androidx.compose.runtime.snapshots;

import androidx.collection.MutableLongList;
import androidx.collection.internal.RuntimeHelpersKt;
import java.util.Arrays;
import kotlin.collections.ArraysKt___ArraysJvmKt;

/* loaded from: classes.dex */
public final class SnapshotIdArrayBuilder {
    public final MutableLongList list;

    public SnapshotIdArrayBuilder(long[] jArr) {
        MutableLongList mutableLongList;
        if (jArr != null) {
            long[] jArrCopyOf = Arrays.copyOf(jArr, jArr.length);
            mutableLongList = new MutableLongList(jArrCopyOf.length);
            int i = mutableLongList._size;
            if (i < 0) {
                RuntimeHelpersKt.throwIndexOutOfBoundsException("");
                throw null;
            }
            if (jArrCopyOf.length != 0) {
                int length = jArrCopyOf.length + i;
                long[] jArr2 = mutableLongList.content;
                if (jArr2.length < length) {
                    mutableLongList.content = Arrays.copyOf(jArr2, Math.max(length, (jArr2.length * 3) / 2));
                }
                long[] jArr3 = mutableLongList.content;
                int i2 = mutableLongList._size;
                if (i != i2) {
                    ArraysKt___ArraysJvmKt.copyInto(jArr3, jArr3, jArrCopyOf.length + i, i, i2);
                }
                System.arraycopy(jArrCopyOf, 0, jArr3, i, jArrCopyOf.length);
                mutableLongList._size += jArrCopyOf.length;
            }
        } else {
            mutableLongList = new MutableLongList(0, 1, null);
        }
        this.list = mutableLongList;
    }
}
