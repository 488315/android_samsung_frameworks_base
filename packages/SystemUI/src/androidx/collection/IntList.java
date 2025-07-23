package androidx.collection;

import androidx.collection.internal.RuntimeHelpersKt;
import java.util.NoSuchElementException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt___RangesKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class IntList {
    public int _size;
    public int[] content;

    public /* synthetic */ IntList(int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(i);
    }

    public final boolean equals(Object obj) {
        if (obj instanceof IntList) {
            IntList intList = (IntList) obj;
            int i = intList._size;
            int i2 = this._size;
            if (i == i2) {
                int[] iArr = this.content;
                int[] iArr2 = intList.content;
                IntRange until = RangesKt___RangesKt.until(0, i2);
                int i3 = until.first;
                int i4 = until.last;
                if (i3 > i4) {
                    return true;
                }
                while (iArr[i3] == iArr2[i3]) {
                    if (i3 == i4) {
                        return true;
                    }
                    i3++;
                }
                return false;
            }
        }
        return false;
    }

    public final int get(int i) {
        if (i >= 0 && i < this._size) {
            return this.content[i];
        }
        RuntimeHelpersKt.throwIndexOutOfBoundsException("Index must be between 0 and size");
        throw null;
    }

    public final int hashCode() {
        int[] iArr = this.content;
        int i = this._size;
        int i2 = 0;
        for (int i3 = 0; i3 < i; i3++) {
            i2 += Integer.hashCode(iArr[i3]) * 31;
        }
        return i2;
    }

    public final int last() {
        int i = this._size;
        if (i != 0) {
            return this.content[i - 1];
        }
        throw new NoSuchElementException("IntList is empty.");
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append((CharSequence) "[");
        int[] iArr = this.content;
        int i = this._size;
        int i2 = 0;
        while (true) {
            if (i2 >= i) {
                sb.append((CharSequence) "]");
                break;
            }
            int i3 = iArr[i2];
            if (i2 == -1) {
                sb.append((CharSequence) "...");
                break;
            }
            if (i2 != 0) {
                sb.append((CharSequence) ", ");
            }
            sb.append(i3);
            i2++;
        }
        return sb.toString();
    }

    private IntList(int i) {
        this.content = i == 0 ? IntSetKt.EmptyIntArray : new int[i];
    }
}
