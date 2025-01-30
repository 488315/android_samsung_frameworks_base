package androidx.collection;

import androidx.collection.internal.ContainerHelpersKt;
import java.util.Arrays;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SparseArrayCompat implements Cloneable {
    public /* synthetic */ boolean garbage;
    public /* synthetic */ int[] keys;
    public /* synthetic */ int size;
    public /* synthetic */ Object[] values;

    public SparseArrayCompat() {
        this(0, 1, null);
    }

    public final Object get(int i) {
        Object obj;
        Object obj2 = SparseArrayCompatKt.DELETED;
        int binarySearch = ContainerHelpersKt.binarySearch(this.size, i, this.keys);
        if (binarySearch < 0 || (obj = this.values[binarySearch]) == SparseArrayCompatKt.DELETED) {
            return null;
        }
        return obj;
    }

    public final void put(int i, Object obj) {
        int binarySearch = ContainerHelpersKt.binarySearch(this.size, i, this.keys);
        if (binarySearch >= 0) {
            this.values[binarySearch] = obj;
            return;
        }
        int i2 = ~binarySearch;
        int i3 = this.size;
        if (i2 < i3) {
            Object[] objArr = this.values;
            if (objArr[i2] == SparseArrayCompatKt.DELETED) {
                this.keys[i2] = i;
                objArr[i2] = obj;
                return;
            }
        }
        if (this.garbage && i3 >= this.keys.length) {
            SparseArrayCompatKt.access$gc(this);
            i2 = ~ContainerHelpersKt.binarySearch(this.size, i, this.keys);
        }
        int i4 = this.size;
        if (i4 >= this.keys.length) {
            int i5 = (i4 + 1) * 4;
            int i6 = 4;
            while (true) {
                if (i6 >= 32) {
                    break;
                }
                int i7 = (1 << i6) - 12;
                if (i5 <= i7) {
                    i5 = i7;
                    break;
                }
                i6++;
            }
            int i8 = i5 / 4;
            this.keys = Arrays.copyOf(this.keys, i8);
            this.values = Arrays.copyOf(this.values, i8);
        }
        int i9 = this.size - i2;
        if (i9 != 0) {
            int[] iArr = this.keys;
            int i10 = i2 + 1;
            System.arraycopy(iArr, i2, iArr, i10, i9);
            Object[] objArr2 = this.values;
            System.arraycopy(objArr2, i2, objArr2, i10, this.size - i2);
        }
        this.keys[i2] = i;
        this.values[i2] = obj;
        this.size++;
    }

    public final int size() {
        if (this.garbage) {
            SparseArrayCompatKt.access$gc(this);
        }
        return this.size;
    }

    public final String toString() {
        if (size() <= 0) {
            return "{}";
        }
        StringBuilder sb = new StringBuilder(this.size * 28);
        sb.append('{');
        int i = this.size;
        for (int i2 = 0; i2 < i; i2++) {
            if (i2 > 0) {
                sb.append(", ");
            }
            if (this.garbage) {
                SparseArrayCompatKt.access$gc(this);
            }
            sb.append(this.keys[i2]);
            sb.append('=');
            Object valueAt = valueAt(i2);
            if (valueAt != this) {
                sb.append(valueAt);
            } else {
                sb.append("(this Map)");
            }
        }
        sb.append('}');
        return sb.toString();
    }

    public final Object valueAt(int i) {
        if (this.garbage) {
            SparseArrayCompatKt.access$gc(this);
        }
        return this.values[i];
    }

    public SparseArrayCompat(int i) {
        if (i == 0) {
            this.keys = ContainerHelpersKt.EMPTY_INTS;
            this.values = ContainerHelpersKt.EMPTY_OBJECTS;
            return;
        }
        int i2 = i * 4;
        int i3 = 4;
        while (true) {
            if (i3 >= 32) {
                break;
            }
            int i4 = (1 << i3) - 12;
            if (i2 <= i4) {
                i2 = i4;
                break;
            }
            i3++;
        }
        int i5 = i2 / 4;
        this.keys = new int[i5];
        this.values = new Object[i5];
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public final SparseArrayCompat m302clone() {
        SparseArrayCompat sparseArrayCompat = (SparseArrayCompat) super.clone();
        sparseArrayCompat.keys = (int[]) this.keys.clone();
        sparseArrayCompat.values = (Object[]) this.values.clone();
        return sparseArrayCompat;
    }

    public /* synthetic */ SparseArrayCompat(int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? 10 : i);
    }
}
