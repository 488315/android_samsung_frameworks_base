package androidx.collection;

import androidx.collection.internal.RuntimeHelpersKt;
import java.util.Arrays;
import kotlin.collections.ArraysKt___ArraysJvmKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class MutableIntList extends IntList {
    public MutableIntList() {
        this(0, 1, null);
    }

    public final void add(int i) {
        ensureCapacity(this._size + 1);
        int[] iArr = this.content;
        int i2 = this._size;
        iArr[i2] = i;
        this._size = i2 + 1;
    }

    public final void ensureCapacity(int i) {
        int[] iArr = this.content;
        if (iArr.length < i) {
            this.content = Arrays.copyOf(iArr, Math.max(i, (iArr.length * 3) / 2));
        }
    }

    public final int removeAt(int i) {
        int i2;
        if (i < 0 || i >= (i2 = this._size)) {
            RuntimeHelpersKt.throwIndexOutOfBoundsException("Index must be between 0 and size");
            throw null;
        }
        int[] iArr = this.content;
        int i3 = iArr[i];
        if (i != i2 - 1) {
            ArraysKt___ArraysJvmKt.copyInto(i, i + 1, i2, iArr, iArr);
        }
        this._size--;
        return i3;
    }

    public final void set(int i, int i2) {
        if (i < 0 || i >= this._size) {
            RuntimeHelpersKt.throwIndexOutOfBoundsException("Index must be between 0 and size");
            throw null;
        }
        int[] iArr = this.content;
        int i3 = iArr[i];
        iArr[i] = i2;
    }

    public MutableIntList(int i) {
        super(i, null);
    }

    public /* synthetic */ MutableIntList(int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? 16 : i);
    }

    public final void add() {
        int i = this._size;
        if (i >= 0) {
            ensureCapacity(i + 1);
            int[] iArr = this.content;
            int i2 = this._size;
            if (i2 != 0) {
                ArraysKt___ArraysJvmKt.copyInto(1, 0, i2, iArr, iArr);
            }
            iArr[0] = 0;
            this._size++;
            return;
        }
        RuntimeHelpersKt.throwIndexOutOfBoundsException("Index must be between 0 and size");
        throw null;
    }
}
