package androidx.collection;

import androidx.collection.internal.ContainerHelpersKt;
import java.util.Arrays;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SparseArrayCompat.jvm.kt */
/* loaded from: classes.dex */
public final class SparseArrayCompat<E> implements Cloneable {
    public /* synthetic */ int[] keys;
    public /* synthetic */ int size;
    public /* synthetic */ Object[] values;

    public SparseArrayCompat() {
        int i;
        int i2 = 4;
        while (true) {
            i = 40;
            if (i2 >= 32) {
                break;
            }
            int i3 = (1 << i2) - 12;
            if (40 <= i3) {
                i = i3;
                break;
            }
            i2++;
        }
        int i4 = i / 4;
        this.keys = new int[i4];
        this.values = new Object[i4];
    }

    public final void append(int i, E e) {
        int i2 = this.size;
        if (i2 != 0 && i <= this.keys[i2 - 1]) {
            put(i, e);
            return;
        }
        if (i2 >= this.keys.length) {
            int i3 = (i2 + 1) * 4;
            int i4 = 4;
            while (true) {
                if (i4 >= 32) {
                    break;
                }
                int i5 = (1 << i4) - 12;
                if (i3 <= i5) {
                    i3 = i5;
                    break;
                }
                i4++;
            }
            int i6 = i3 / 4;
            int[] copyOf = Arrays.copyOf(this.keys, i6);
            Intrinsics.checkNotNullExpressionValue(copyOf, "copyOf(this, newSize)");
            this.keys = copyOf;
            Object[] copyOf2 = Arrays.copyOf(this.values, i6);
            Intrinsics.checkNotNullExpressionValue(copyOf2, "copyOf(this, newSize)");
            this.values = copyOf2;
        }
        this.keys[i2] = i;
        this.values[i2] = e;
        this.size = i2 + 1;
    }

    public final void put(int i, E e) {
        Object obj;
        int binarySearch = ContainerHelpersKt.binarySearch(this.size, i, this.keys);
        if (binarySearch >= 0) {
            this.values[binarySearch] = e;
            return;
        }
        int i2 = ~binarySearch;
        if (i2 < this.size) {
            Object obj2 = this.values[i2];
            obj = SparseArrayCompatKt.DELETED;
            if (obj2 == obj) {
                this.keys[i2] = i;
                this.values[i2] = e;
                return;
            }
        }
        int i3 = this.size;
        if (i3 >= this.keys.length) {
            int i4 = (i3 + 1) * 4;
            int i5 = 4;
            while (true) {
                if (i5 >= 32) {
                    break;
                }
                int i6 = (1 << i5) - 12;
                if (i4 <= i6) {
                    i4 = i6;
                    break;
                }
                i5++;
            }
            int i7 = i4 / 4;
            int[] copyOf = Arrays.copyOf(this.keys, i7);
            Intrinsics.checkNotNullExpressionValue(copyOf, "copyOf(this, newSize)");
            this.keys = copyOf;
            Object[] copyOf2 = Arrays.copyOf(this.values, i7);
            Intrinsics.checkNotNullExpressionValue(copyOf2, "copyOf(this, newSize)");
            this.values = copyOf2;
        }
        int i8 = this.size;
        if (i8 - i2 != 0) {
            int[] iArr = this.keys;
            int i9 = i2 + 1;
            ArraysKt.copyInto(i9, i2, i8, iArr, iArr);
            Object[] objArr = this.values;
            ArraysKt.copyInto(objArr, objArr, i9, i2, this.size);
        }
        this.keys[i2] = i;
        this.values[i2] = e;
        this.size++;
    }

    public final String toString() {
        int i = this.size;
        if (i <= 0) {
            return "{}";
        }
        StringBuilder sb = new StringBuilder(i * 28);
        sb.append('{');
        int i2 = this.size;
        for (int i3 = 0; i3 < i2; i3++) {
            if (i3 > 0) {
                sb.append(", ");
            }
            sb.append(this.keys[i3]);
            sb.append('=');
            Object obj = this.values[i3];
            if (obj != this) {
                sb.append(obj);
            } else {
                sb.append("(this Map)");
            }
        }
        sb.append('}');
        String sb2 = sb.toString();
        Intrinsics.checkNotNullExpressionValue(sb2, "buffer.toString()");
        return sb2;
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public final SparseArrayCompat<E> m14clone() {
        Object clone = super.clone();
        Intrinsics.checkNotNull(clone, "null cannot be cast to non-null type androidx.collection.SparseArrayCompat<E of androidx.collection.SparseArrayCompat>");
        SparseArrayCompat<E> sparseArrayCompat = (SparseArrayCompat) clone;
        sparseArrayCompat.keys = (int[]) this.keys.clone();
        sparseArrayCompat.values = (Object[]) this.values.clone();
        return sparseArrayCompat;
    }
}
