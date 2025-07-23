package androidx.datastore.preferences.protobuf;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import androidx.datastore.preferences.protobuf.Internal;
import java.nio.charset.Charset;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.Collection;
import java.util.RandomAccess;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class IntArrayList extends AbstractProtobufList implements RandomAccess, PrimitiveNonBoxingCollection {
    public int[] array;
    public int size;

    static {
        new IntArrayList(new int[0], 0, false);
    }

    public IntArrayList() {
        this(new int[10], 0, true);
    }

    @Override // androidx.datastore.preferences.protobuf.AbstractProtobufList, java.util.AbstractList, java.util.List
    public final void add(int i, Object obj) {
        int i2;
        int intValue = ((Integer) obj).intValue();
        ensureIsMutable();
        if (i < 0 || i > (i2 = this.size)) {
            StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "Index:", ", Size:");
            m.append(this.size);
            throw new IndexOutOfBoundsException(m.toString());
        }
        int[] iArr = this.array;
        if (i2 < iArr.length) {
            System.arraycopy(iArr, i, iArr, i + 1, i2 - i);
        } else {
            int[] iArr2 = new int[BooleanArrayList$$ExternalSyntheticOutline0.m(i2, 3, 2, 1)];
            System.arraycopy(iArr, 0, iArr2, 0, i);
            System.arraycopy(this.array, i, iArr2, i + 1, this.size - i);
            this.array = iArr2;
        }
        this.array[i] = intValue;
        this.size++;
        ((AbstractList) this).modCount++;
    }

    @Override // androidx.datastore.preferences.protobuf.AbstractProtobufList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean addAll(Collection collection) {
        ensureIsMutable();
        Charset charset = Internal.UTF_8;
        collection.getClass();
        if (!(collection instanceof IntArrayList)) {
            return super.addAll(collection);
        }
        IntArrayList intArrayList = (IntArrayList) collection;
        int i = intArrayList.size;
        if (i == 0) {
            return false;
        }
        int i2 = this.size;
        if (Integer.MAX_VALUE - i2 < i) {
            throw new OutOfMemoryError();
        }
        int i3 = i2 + i;
        int[] iArr = this.array;
        if (i3 > iArr.length) {
            this.array = Arrays.copyOf(iArr, i3);
        }
        System.arraycopy(intArrayList.array, 0, this.array, this.size, intArrayList.size);
        this.size = i3;
        ((AbstractList) this).modCount++;
        return true;
    }

    public final void addInt(int i) {
        ensureIsMutable();
        int i2 = this.size;
        int[] iArr = this.array;
        if (i2 == iArr.length) {
            int[] iArr2 = new int[BooleanArrayList$$ExternalSyntheticOutline0.m(i2, 3, 2, 1)];
            System.arraycopy(iArr, 0, iArr2, 0, i2);
            this.array = iArr2;
        }
        int[] iArr3 = this.array;
        int i3 = this.size;
        this.size = i3 + 1;
        iArr3[i3] = i;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean contains(Object obj) {
        return indexOf(obj) != -1;
    }

    public final void ensureIndexInRange$3(int i) {
        if (i < 0 || i >= this.size) {
            StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "Index:", ", Size:");
            m.append(this.size);
            throw new IndexOutOfBoundsException(m.toString());
        }
    }

    @Override // androidx.datastore.preferences.protobuf.AbstractProtobufList, java.util.AbstractList, java.util.Collection, java.util.List
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof IntArrayList)) {
            return super.equals(obj);
        }
        IntArrayList intArrayList = (IntArrayList) obj;
        if (this.size != intArrayList.size) {
            return false;
        }
        int[] iArr = intArrayList.array;
        for (int i = 0; i < this.size; i++) {
            if (this.array[i] != iArr[i]) {
                return false;
            }
        }
        return true;
    }

    @Override // java.util.AbstractList, java.util.List
    public final Object get(int i) {
        return Integer.valueOf(getInt(i));
    }

    public final int getInt(int i) {
        ensureIndexInRange$3(i);
        return this.array[i];
    }

    @Override // androidx.datastore.preferences.protobuf.AbstractProtobufList, java.util.AbstractList, java.util.Collection, java.util.List
    public final int hashCode() {
        int i = 1;
        for (int i2 = 0; i2 < this.size; i2++) {
            i = (i * 31) + this.array[i2];
        }
        return i;
    }

    @Override // java.util.AbstractList, java.util.List
    public final int indexOf(Object obj) {
        if (!(obj instanceof Integer)) {
            return -1;
        }
        int intValue = ((Integer) obj).intValue();
        int i = this.size;
        for (int i2 = 0; i2 < i; i2++) {
            if (this.array[i2] == intValue) {
                return i2;
            }
        }
        return -1;
    }

    @Override // androidx.datastore.preferences.protobuf.Internal.ProtobufList
    public final Internal.ProtobufList mutableCopyWithCapacity(int i) {
        if (i >= this.size) {
            return new IntArrayList(Arrays.copyOf(this.array, i), this.size, true);
        }
        throw new IllegalArgumentException();
    }

    @Override // androidx.datastore.preferences.protobuf.AbstractProtobufList, java.util.AbstractList, java.util.List
    public final Object remove(int i) {
        ensureIsMutable();
        ensureIndexInRange$3(i);
        int[] iArr = this.array;
        int i2 = iArr[i];
        if (i < this.size - 1) {
            System.arraycopy(iArr, i + 1, iArr, i, (r2 - i) - 1);
        }
        this.size--;
        ((AbstractList) this).modCount++;
        return Integer.valueOf(i2);
    }

    @Override // java.util.AbstractList
    public final void removeRange(int i, int i2) {
        ensureIsMutable();
        if (i2 < i) {
            throw new IndexOutOfBoundsException("toIndex < fromIndex");
        }
        int[] iArr = this.array;
        System.arraycopy(iArr, i2, iArr, i, this.size - i2);
        this.size -= i2 - i;
        ((AbstractList) this).modCount++;
    }

    @Override // androidx.datastore.preferences.protobuf.AbstractProtobufList, java.util.AbstractList, java.util.List
    public final Object set(int i, Object obj) {
        int intValue = ((Integer) obj).intValue();
        ensureIsMutable();
        ensureIndexInRange$3(i);
        int[] iArr = this.array;
        int i2 = iArr[i];
        iArr[i] = intValue;
        return Integer.valueOf(i2);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return this.size;
    }

    private IntArrayList(int[] iArr, int i, boolean z) {
        super(z);
        this.array = iArr;
        this.size = i;
    }

    @Override // androidx.datastore.preferences.protobuf.AbstractProtobufList, java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean add(Object obj) {
        addInt(((Integer) obj).intValue());
        return true;
    }
}
