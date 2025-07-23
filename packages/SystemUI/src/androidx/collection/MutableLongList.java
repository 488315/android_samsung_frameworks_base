package androidx.collection;

import java.util.Arrays;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class MutableLongList extends LongList {
    public MutableLongList() {
        this(0, 1, null);
    }

    public final void add(long j) {
        int i = this._size + 1;
        long[] jArr = this.content;
        if (jArr.length < i) {
            this.content = Arrays.copyOf(jArr, Math.max(i, (jArr.length * 3) / 2));
        }
        long[] jArr2 = this.content;
        int i2 = this._size;
        jArr2[i2] = j;
        this._size = i2 + 1;
    }

    public MutableLongList(int i) {
        super(i, null);
    }

    public /* synthetic */ MutableLongList(int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? 16 : i);
    }
}
