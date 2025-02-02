package androidx.collection;

import androidx.appcompat.view.menu.SubMenuBuilder$$ExternalSyntheticOutline0;
import androidx.collection.internal.ContainerHelpersKt;
import java.util.Arrays;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: LongSparseArray.jvm.kt */
/* loaded from: classes.dex */
public final class LongSparseArray<E> implements Cloneable {
    public /* synthetic */ boolean garbage;
    public /* synthetic */ long[] keys;
    public /* synthetic */ int size;
    public /* synthetic */ Object[] values;

    public LongSparseArray(int i) {
        if (i == 0) {
            this.keys = ContainerHelpersKt.EMPTY_LONGS;
            this.values = ContainerHelpersKt.EMPTY_OBJECTS;
            return;
        }
        int i2 = i * 8;
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
        int i5 = i2 / 8;
        this.keys = new long[i5];
        this.values = new Object[i5];
    }

    public final void clear() {
        int i = this.size;
        Object[] objArr = this.values;
        for (int i2 = 0; i2 < i; i2++) {
            objArr[i2] = null;
        }
        this.size = 0;
        this.garbage = false;
    }

    public final E get(long j) {
        Object obj;
        int binarySearch = ContainerHelpersKt.binarySearch(this.keys, this.size, j);
        if (binarySearch >= 0) {
            Object obj2 = this.values[binarySearch];
            obj = LongSparseArrayKt.DELETED;
            if (obj2 != obj) {
                return (E) this.values[binarySearch];
            }
        }
        return null;
    }

    public final long keyAt(int i) {
        Object obj;
        if (!(i >= 0 && i < this.size)) {
            throw new IllegalArgumentException(SubMenuBuilder$$ExternalSyntheticOutline0.m0m("Expected index to be within 0..size()-1, but was ", i).toString());
        }
        if (this.garbage) {
            int i2 = this.size;
            long[] jArr = this.keys;
            Object[] objArr = this.values;
            int i3 = 0;
            for (int i4 = 0; i4 < i2; i4++) {
                Object obj2 = objArr[i4];
                obj = LongSparseArrayKt.DELETED;
                if (obj2 != obj) {
                    if (i4 != i3) {
                        jArr[i3] = jArr[i4];
                        objArr[i3] = obj2;
                        objArr[i4] = null;
                    }
                    i3++;
                }
            }
            this.garbage = false;
            this.size = i3;
        }
        return this.keys[i];
    }

    public final void put(long j, E e) {
        Object obj;
        Object obj2;
        int binarySearch = ContainerHelpersKt.binarySearch(this.keys, this.size, j);
        if (binarySearch >= 0) {
            this.values[binarySearch] = e;
            return;
        }
        int i = ~binarySearch;
        if (i < this.size) {
            Object obj3 = this.values[i];
            obj2 = LongSparseArrayKt.DELETED;
            if (obj3 == obj2) {
                this.keys[i] = j;
                this.values[i] = e;
                return;
            }
        }
        if (this.garbage) {
            int i2 = this.size;
            long[] jArr = this.keys;
            if (i2 >= jArr.length) {
                Object[] objArr = this.values;
                int i3 = 0;
                for (int i4 = 0; i4 < i2; i4++) {
                    Object obj4 = objArr[i4];
                    obj = LongSparseArrayKt.DELETED;
                    if (obj4 != obj) {
                        if (i4 != i3) {
                            jArr[i3] = jArr[i4];
                            objArr[i3] = obj4;
                            objArr[i4] = null;
                        }
                        i3++;
                    }
                }
                this.garbage = false;
                this.size = i3;
                i = ~ContainerHelpersKt.binarySearch(this.keys, i3, j);
            }
        }
        int i5 = this.size;
        if (i5 >= this.keys.length) {
            int i6 = (i5 + 1) * 8;
            int i7 = 4;
            while (true) {
                if (i7 >= 32) {
                    break;
                }
                int i8 = (1 << i7) - 12;
                if (i6 <= i8) {
                    i6 = i8;
                    break;
                }
                i7++;
            }
            int i9 = i6 / 8;
            long[] copyOf = Arrays.copyOf(this.keys, i9);
            Intrinsics.checkNotNullExpressionValue(copyOf, "copyOf(this, newSize)");
            this.keys = copyOf;
            Object[] copyOf2 = Arrays.copyOf(this.values, i9);
            Intrinsics.checkNotNullExpressionValue(copyOf2, "copyOf(this, newSize)");
            this.values = copyOf2;
        }
        int i10 = this.size - i;
        if (i10 != 0) {
            long[] jArr2 = this.keys;
            int i11 = i + 1;
            Intrinsics.checkNotNullParameter(jArr2, "<this>");
            System.arraycopy(jArr2, i, jArr2, i11, i10);
            Object[] objArr2 = this.values;
            ArraysKt.copyInto(objArr2, objArr2, i11, i, this.size);
        }
        this.keys[i] = j;
        this.values[i] = e;
        this.size++;
    }

    public final int size() {
        Object obj;
        if (this.garbage) {
            int i = this.size;
            long[] jArr = this.keys;
            Object[] objArr = this.values;
            int i2 = 0;
            for (int i3 = 0; i3 < i; i3++) {
                Object obj2 = objArr[i3];
                obj = LongSparseArrayKt.DELETED;
                if (obj2 != obj) {
                    if (i3 != i2) {
                        jArr[i2] = jArr[i3];
                        objArr[i2] = obj2;
                        objArr[i3] = null;
                    }
                    i2++;
                }
            }
            this.garbage = false;
            this.size = i2;
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
            sb.append(keyAt(i2));
            sb.append('=');
            E valueAt = valueAt(i2);
            if (valueAt != sb) {
                sb.append(valueAt);
            } else {
                sb.append("(this Map)");
            }
        }
        sb.append('}');
        String sb2 = sb.toString();
        Intrinsics.checkNotNullExpressionValue(sb2, "StringBuilder(capacity).…builderAction).toString()");
        return sb2;
    }

    public final E valueAt(int i) {
        Object obj;
        if (!(i >= 0 && i < this.size)) {
            throw new IllegalArgumentException(SubMenuBuilder$$ExternalSyntheticOutline0.m0m("Expected index to be within 0..size()-1, but was ", i).toString());
        }
        if (this.garbage) {
            int i2 = this.size;
            long[] jArr = this.keys;
            Object[] objArr = this.values;
            int i3 = 0;
            for (int i4 = 0; i4 < i2; i4++) {
                Object obj2 = objArr[i4];
                obj = LongSparseArrayKt.DELETED;
                if (obj2 != obj) {
                    if (i4 != i3) {
                        jArr[i3] = jArr[i4];
                        objArr[i3] = obj2;
                        objArr[i4] = null;
                    }
                    i3++;
                }
            }
            this.garbage = false;
            this.size = i3;
        }
        return (E) this.values[i];
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public final LongSparseArray<E> m13clone() {
        Object clone = super.clone();
        Intrinsics.checkNotNull(clone, "null cannot be cast to non-null type androidx.collection.LongSparseArray<E of androidx.collection.LongSparseArray>");
        LongSparseArray<E> longSparseArray = (LongSparseArray) clone;
        longSparseArray.keys = (long[]) this.keys.clone();
        longSparseArray.values = (Object[]) this.values.clone();
        return longSparseArray;
    }

    public LongSparseArray() {
        this(10);
    }
}
