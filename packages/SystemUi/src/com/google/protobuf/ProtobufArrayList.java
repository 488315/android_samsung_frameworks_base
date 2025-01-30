package com.google.protobuf;

import android.support.v4.media.AbstractC0000x2c234b15;
import com.google.protobuf.Internal;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.RandomAccess;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ProtobufArrayList extends AbstractProtobufList implements RandomAccess {
    public static final ProtobufArrayList EMPTY_LIST;
    public Object[] array;
    public int size;

    static {
        ProtobufArrayList protobufArrayList = new ProtobufArrayList(new Object[0], 0);
        EMPTY_LIST = protobufArrayList;
        protobufArrayList.isMutable = false;
    }

    public ProtobufArrayList() {
        this(new Object[10], 0);
    }

    @Override // com.google.protobuf.AbstractProtobufList, java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean add(Object obj) {
        ensureIsMutable();
        int i = this.size;
        Object[] objArr = this.array;
        if (i == objArr.length) {
            this.array = Arrays.copyOf(objArr, ((i * 3) / 2) + 1);
        }
        Object[] objArr2 = this.array;
        int i2 = this.size;
        this.size = i2 + 1;
        objArr2[i2] = obj;
        ((AbstractList) this).modCount++;
        return true;
    }

    public final void ensureIndexInRange(int i) {
        if (i < 0 || i >= this.size) {
            StringBuilder m1m = AbstractC0000x2c234b15.m1m("Index:", i, ", Size:");
            m1m.append(this.size);
            throw new IndexOutOfBoundsException(m1m.toString());
        }
    }

    @Override // java.util.AbstractList, java.util.List
    public final Object get(int i) {
        ensureIndexInRange(i);
        return this.array[i];
    }

    @Override // com.google.protobuf.Internal.ProtobufList
    public final Internal.ProtobufList mutableCopyWithCapacity(int i) {
        if (i >= this.size) {
            return new ProtobufArrayList(Arrays.copyOf(this.array, i), this.size);
        }
        throw new IllegalArgumentException();
    }

    @Override // com.google.protobuf.AbstractProtobufList, java.util.AbstractList, java.util.List
    public final Object remove(int i) {
        ensureIsMutable();
        ensureIndexInRange(i);
        Object[] objArr = this.array;
        Object obj = objArr[i];
        if (i < this.size - 1) {
            System.arraycopy(objArr, i + 1, objArr, i, (r2 - i) - 1);
        }
        this.size--;
        ((AbstractList) this).modCount++;
        return obj;
    }

    @Override // com.google.protobuf.AbstractProtobufList, java.util.AbstractList, java.util.List
    public final Object set(int i, Object obj) {
        ensureIsMutable();
        ensureIndexInRange(i);
        Object[] objArr = this.array;
        Object obj2 = objArr[i];
        objArr[i] = obj;
        ((AbstractList) this).modCount++;
        return obj2;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return this.size;
    }

    private ProtobufArrayList(Object[] objArr, int i) {
        this.array = objArr;
        this.size = i;
    }

    @Override // com.google.protobuf.AbstractProtobufList, java.util.AbstractList, java.util.List
    public final void add(int i, Object obj) {
        int i2;
        ensureIsMutable();
        if (i >= 0 && i <= (i2 = this.size)) {
            Object[] objArr = this.array;
            if (i2 < objArr.length) {
                System.arraycopy(objArr, i, objArr, i + 1, i2 - i);
            } else {
                Object[] objArr2 = new Object[((i2 * 3) / 2) + 1];
                System.arraycopy(objArr, 0, objArr2, 0, i);
                System.arraycopy(this.array, i, objArr2, i + 1, this.size - i);
                this.array = objArr2;
            }
            this.array[i] = obj;
            this.size++;
            ((AbstractList) this).modCount++;
            return;
        }
        StringBuilder m1m = AbstractC0000x2c234b15.m1m("Index:", i, ", Size:");
        m1m.append(this.size);
        throw new IndexOutOfBoundsException(m1m.toString());
    }
}
