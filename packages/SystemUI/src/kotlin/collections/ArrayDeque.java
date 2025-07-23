package kotlin.collections;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import kotlin.collections.AbstractList;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class ArrayDeque extends AbstractMutableList {
    public static final Object[] emptyElementData;
    public Object[] elementData;
    public int head;
    public int size;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
        emptyElementData = new Object[0];
    }

    public ArrayDeque(int i) {
        Object[] objArr;
        if (i == 0) {
            objArr = emptyElementData;
        } else {
            if (i <= 0) {
                throw new IllegalArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "Illegal Capacity: "));
            }
            objArr = new Object[i];
        }
        this.elementData = objArr;
    }

    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean add(Object obj) {
        addLast(obj);
        return true;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean addAll(Collection collection) {
        if (collection.isEmpty()) {
            return false;
        }
        registerModification();
        ensureCapacity$1(collection.size() + getSize());
        copyCollectionElements(positiveMod(getSize() + this.head), collection);
        return true;
    }

    public final void addFirst(Object obj) {
        registerModification();
        ensureCapacity$1(this.size + 1);
        int i = this.head;
        if (i == 0) {
            i = this.elementData.length;
        }
        int i2 = i - 1;
        this.head = i2;
        this.elementData[i2] = obj;
        this.size++;
    }

    public final void addLast(Object obj) {
        registerModification();
        ensureCapacity$1(getSize() + 1);
        this.elementData[positiveMod(getSize() + this.head)] = obj;
        this.size = getSize() + 1;
    }

    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final void clear() {
        if (!isEmpty()) {
            registerModification();
            nullifyNonEmpty(this.head, positiveMod(getSize() + this.head));
        }
        this.head = 0;
        this.size = 0;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean contains(Object obj) {
        return indexOf(obj) != -1;
    }

    public final void copyCollectionElements(int i, Collection collection) {
        Iterator it = collection.iterator();
        int length = this.elementData.length;
        while (i < length && it.hasNext()) {
            this.elementData[i] = it.next();
            i++;
        }
        int i2 = this.head;
        for (int i3 = 0; i3 < i2 && it.hasNext(); i3++) {
            this.elementData[i3] = it.next();
        }
        this.size = collection.size() + this.size;
    }

    public final void ensureCapacity$1(int i) {
        if (i < 0) {
            throw new IllegalStateException("Deque is too big.");
        }
        Object[] objArr = this.elementData;
        if (i <= objArr.length) {
            return;
        }
        if (objArr == emptyElementData) {
            if (i < 10) {
                i = 10;
            }
            this.elementData = new Object[i];
            return;
        }
        AbstractList.Companion companion = AbstractList.Companion;
        int length = objArr.length;
        companion.getClass();
        Object[] objArr2 = new Object[AbstractList.Companion.newCapacity$kotlin_stdlib(length, i)];
        Object[] objArr3 = this.elementData;
        ArraysKt___ArraysJvmKt.copyInto(objArr3, objArr2, 0, this.head, objArr3.length);
        Object[] objArr4 = this.elementData;
        int length2 = objArr4.length;
        int i2 = this.head;
        ArraysKt___ArraysJvmKt.copyInto(objArr4, objArr2, length2 - i2, 0, i2);
        this.head = 0;
        this.elementData = objArr2;
    }

    public final Object first() {
        if (isEmpty()) {
            throw new NoSuchElementException("ArrayDeque is empty.");
        }
        return this.elementData[this.head];
    }

    public final Object firstOrNull() {
        if (isEmpty()) {
            return null;
        }
        return this.elementData[this.head];
    }

    @Override // java.util.AbstractList, java.util.List
    public final Object get(int i) {
        AbstractList.Companion companion = AbstractList.Companion;
        int i2 = this.size;
        companion.getClass();
        AbstractList.Companion.checkElementIndex$kotlin_stdlib(i, i2);
        return this.elementData[positiveMod(this.head + i)];
    }

    @Override // kotlin.collections.AbstractMutableList
    public final int getSize() {
        return this.size;
    }

    public final int incremented(int i) {
        if (i == this.elementData.length - 1) {
            return 0;
        }
        return i + 1;
    }

    @Override // java.util.AbstractList, java.util.List
    public final int indexOf(Object obj) {
        int i;
        int positiveMod = positiveMod(getSize() + this.head);
        int i2 = this.head;
        if (i2 < positiveMod) {
            while (i2 < positiveMod) {
                if (Intrinsics.areEqual(obj, this.elementData[i2])) {
                    i = this.head;
                } else {
                    i2++;
                }
            }
            return -1;
        }
        if (i2 < positiveMod) {
            return -1;
        }
        int length = this.elementData.length;
        while (true) {
            if (i2 >= length) {
                for (int i3 = 0; i3 < positiveMod; i3++) {
                    if (Intrinsics.areEqual(obj, this.elementData[i3])) {
                        i2 = i3 + this.elementData.length;
                        i = this.head;
                    }
                }
                return -1;
            }
            if (Intrinsics.areEqual(obj, this.elementData[i2])) {
                i = this.head;
                break;
            }
            i2++;
        }
        return i2 - i;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean isEmpty() {
        return getSize() == 0;
    }

    public final Object last() {
        if (isEmpty()) {
            throw new NoSuchElementException("ArrayDeque is empty.");
        }
        return this.elementData[positiveMod((size() - 1) + this.head)];
    }

    @Override // java.util.AbstractList, java.util.List
    public final int lastIndexOf(Object obj) {
        int length;
        int i;
        int positiveMod = positiveMod(getSize() + this.head);
        int i2 = this.head;
        if (i2 < positiveMod) {
            length = positiveMod - 1;
            if (i2 <= length) {
                while (!Intrinsics.areEqual(obj, this.elementData[length])) {
                    if (length != i2) {
                        length--;
                    }
                }
                i = this.head;
                return length - i;
            }
            return -1;
        }
        if (i2 > positiveMod) {
            int i3 = positiveMod - 1;
            while (true) {
                if (-1 >= i3) {
                    length = this.elementData.length - 1;
                    int i4 = this.head;
                    if (i4 <= length) {
                        while (!Intrinsics.areEqual(obj, this.elementData[length])) {
                            if (length != i4) {
                                length--;
                            }
                        }
                        i = this.head;
                    }
                } else {
                    if (Intrinsics.areEqual(obj, this.elementData[i3])) {
                        length = i3 + this.elementData.length;
                        i = this.head;
                        break;
                    }
                    i3--;
                }
            }
        }
        return -1;
    }

    public final Object lastOrNull() {
        if (isEmpty()) {
            return null;
        }
        return this.elementData[positiveMod((size() - 1) + this.head)];
    }

    public final int negativeMod(int i) {
        return i < 0 ? i + this.elementData.length : i;
    }

    public final void nullifyNonEmpty(int i, int i2) {
        if (i < i2) {
            Arrays.fill(this.elementData, i, i2, (Object) null);
            return;
        }
        Object[] objArr = this.elementData;
        Arrays.fill(objArr, i, objArr.length, (Object) null);
        Arrays.fill(this.elementData, 0, i2, (Object) null);
    }

    public final int positiveMod(int i) {
        Object[] objArr = this.elementData;
        return i >= objArr.length ? i - objArr.length : i;
    }

    public final void registerModification() {
        ((java.util.AbstractList) this).modCount++;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean remove(Object obj) {
        int indexOf = indexOf(obj);
        if (indexOf == -1) {
            return false;
        }
        removeAt(indexOf);
        return true;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean removeAll(Collection collection) {
        int positiveMod;
        boolean z = false;
        z = false;
        z = false;
        if (!isEmpty() && this.elementData.length != 0) {
            int positiveMod2 = positiveMod(getSize() + this.head);
            int i = this.head;
            if (i < positiveMod2) {
                positiveMod = i;
                while (i < positiveMod2) {
                    Object obj = this.elementData[i];
                    if (collection.contains(obj)) {
                        z = true;
                    } else {
                        this.elementData[positiveMod] = obj;
                        positiveMod++;
                    }
                    i++;
                }
                Arrays.fill(this.elementData, positiveMod, positiveMod2, (Object) null);
            } else {
                int length = this.elementData.length;
                boolean z2 = false;
                int i2 = i;
                while (i < length) {
                    Object[] objArr = this.elementData;
                    Object obj2 = objArr[i];
                    objArr[i] = null;
                    if (collection.contains(obj2)) {
                        z2 = true;
                    } else {
                        this.elementData[i2] = obj2;
                        i2++;
                    }
                    i++;
                }
                positiveMod = positiveMod(i2);
                for (int i3 = 0; i3 < positiveMod2; i3++) {
                    Object[] objArr2 = this.elementData;
                    Object obj3 = objArr2[i3];
                    objArr2[i3] = null;
                    if (collection.contains(obj3)) {
                        z2 = true;
                    } else {
                        this.elementData[positiveMod] = obj3;
                        positiveMod = incremented(positiveMod);
                    }
                }
                z = z2;
            }
            if (z) {
                registerModification();
                this.size = negativeMod(positiveMod - this.head);
            }
        }
        return z;
    }

    @Override // kotlin.collections.AbstractMutableList
    public final Object removeAt(int i) {
        AbstractList.Companion companion = AbstractList.Companion;
        int i2 = this.size;
        companion.getClass();
        AbstractList.Companion.checkElementIndex$kotlin_stdlib(i, i2);
        if (i == getSize() - 1) {
            return removeLast();
        }
        if (i == 0) {
            return removeFirst();
        }
        registerModification();
        int positiveMod = positiveMod(this.head + i);
        Object[] objArr = this.elementData;
        Object obj = objArr[positiveMod];
        if (i < (this.size >> 1)) {
            int i3 = this.head;
            if (positiveMod >= i3) {
                ArraysKt___ArraysJvmKt.copyInto(objArr, objArr, i3 + 1, i3, positiveMod);
            } else {
                ArraysKt___ArraysJvmKt.copyInto(objArr, objArr, 1, 0, positiveMod);
                Object[] objArr2 = this.elementData;
                objArr2[0] = objArr2[objArr2.length - 1];
                int i4 = this.head;
                ArraysKt___ArraysJvmKt.copyInto(objArr2, objArr2, i4 + 1, i4, objArr2.length - 1);
            }
            Object[] objArr3 = this.elementData;
            int i5 = this.head;
            objArr3[i5] = null;
            this.head = incremented(i5);
        } else {
            int positiveMod2 = positiveMod((getSize() - 1) + this.head);
            if (positiveMod <= positiveMod2) {
                Object[] objArr4 = this.elementData;
                ArraysKt___ArraysJvmKt.copyInto(objArr4, objArr4, positiveMod, positiveMod + 1, positiveMod2 + 1);
            } else {
                Object[] objArr5 = this.elementData;
                ArraysKt___ArraysJvmKt.copyInto(objArr5, objArr5, positiveMod, positiveMod + 1, objArr5.length);
                Object[] objArr6 = this.elementData;
                objArr6[objArr6.length - 1] = objArr6[0];
                ArraysKt___ArraysJvmKt.copyInto(objArr6, objArr6, 0, 1, positiveMod2 + 1);
            }
            this.elementData[positiveMod2] = null;
        }
        this.size--;
        return obj;
    }

    public final Object removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("ArrayDeque is empty.");
        }
        registerModification();
        Object[] objArr = this.elementData;
        int i = this.head;
        Object obj = objArr[i];
        objArr[i] = null;
        this.head = incremented(i);
        this.size = getSize() - 1;
        return obj;
    }

    public final Object removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("ArrayDeque is empty.");
        }
        registerModification();
        int positiveMod = positiveMod((size() - 1) + this.head);
        Object[] objArr = this.elementData;
        Object obj = objArr[positiveMod];
        objArr[positiveMod] = null;
        this.size = getSize() - 1;
        return obj;
    }

    @Override // java.util.AbstractList
    public final void removeRange(int i, int i2) {
        AbstractList.Companion companion = AbstractList.Companion;
        int i3 = this.size;
        companion.getClass();
        AbstractList.Companion.checkRangeIndexes$kotlin_stdlib(i, i2, i3);
        int i4 = i2 - i;
        if (i4 == 0) {
            return;
        }
        if (i4 == this.size) {
            clear();
            return;
        }
        if (i4 == 1) {
            removeAt(i);
            return;
        }
        registerModification();
        if (i < this.size - i2) {
            int positiveMod = positiveMod((i - 1) + this.head);
            int positiveMod2 = positiveMod((i2 - 1) + this.head);
            while (i > 0) {
                int i5 = positiveMod + 1;
                int min = Math.min(i, Math.min(i5, positiveMod2 + 1));
                Object[] objArr = this.elementData;
                int i6 = positiveMod2 - min;
                int i7 = positiveMod - min;
                ArraysKt___ArraysJvmKt.copyInto(objArr, objArr, i6 + 1, i7 + 1, i5);
                positiveMod = negativeMod(i7);
                positiveMod2 = negativeMod(i6);
                i -= min;
            }
            int positiveMod3 = positiveMod(this.head + i4);
            nullifyNonEmpty(this.head, positiveMod3);
            this.head = positiveMod3;
        } else {
            int positiveMod4 = positiveMod(this.head + i2);
            int positiveMod5 = positiveMod(this.head + i);
            int i8 = this.size;
            while (true) {
                i8 -= i2;
                if (i8 <= 0) {
                    break;
                }
                Object[] objArr2 = this.elementData;
                i2 = Math.min(i8, Math.min(objArr2.length - positiveMod4, objArr2.length - positiveMod5));
                Object[] objArr3 = this.elementData;
                int i9 = positiveMod4 + i2;
                ArraysKt___ArraysJvmKt.copyInto(objArr3, objArr3, positiveMod5, positiveMod4, i9);
                positiveMod4 = positiveMod(i9);
                positiveMod5 = positiveMod(positiveMod5 + i2);
            }
            int positiveMod6 = positiveMod(this.size + this.head);
            nullifyNonEmpty(negativeMod(positiveMod6 - i4), positiveMod6);
        }
        this.size -= i4;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean retainAll(Collection collection) {
        int positiveMod;
        boolean z = false;
        z = false;
        z = false;
        if (!isEmpty() && this.elementData.length != 0) {
            int positiveMod2 = positiveMod(getSize() + this.head);
            int i = this.head;
            if (i < positiveMod2) {
                positiveMod = i;
                while (i < positiveMod2) {
                    Object obj = this.elementData[i];
                    if (collection.contains(obj)) {
                        this.elementData[positiveMod] = obj;
                        positiveMod++;
                    } else {
                        z = true;
                    }
                    i++;
                }
                Arrays.fill(this.elementData, positiveMod, positiveMod2, (Object) null);
            } else {
                int length = this.elementData.length;
                boolean z2 = false;
                int i2 = i;
                while (i < length) {
                    Object[] objArr = this.elementData;
                    Object obj2 = objArr[i];
                    objArr[i] = null;
                    if (collection.contains(obj2)) {
                        this.elementData[i2] = obj2;
                        i2++;
                    } else {
                        z2 = true;
                    }
                    i++;
                }
                positiveMod = positiveMod(i2);
                for (int i3 = 0; i3 < positiveMod2; i3++) {
                    Object[] objArr2 = this.elementData;
                    Object obj3 = objArr2[i3];
                    objArr2[i3] = null;
                    if (collection.contains(obj3)) {
                        this.elementData[positiveMod] = obj3;
                        positiveMod = incremented(positiveMod);
                    } else {
                        z2 = true;
                    }
                }
                z = z2;
            }
            if (z) {
                registerModification();
                this.size = negativeMod(positiveMod - this.head);
            }
        }
        return z;
    }

    @Override // java.util.AbstractList, java.util.List
    public final Object set(int i, Object obj) {
        AbstractList.Companion companion = AbstractList.Companion;
        int i2 = this.size;
        companion.getClass();
        AbstractList.Companion.checkElementIndex$kotlin_stdlib(i, i2);
        int positiveMod = positiveMod(this.head + i);
        Object[] objArr = this.elementData;
        Object obj2 = objArr[positiveMod];
        objArr[positiveMod] = obj;
        return obj2;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final Object[] toArray() {
        return toArray(new Object[getSize()]);
    }

    @Override // java.util.AbstractList, java.util.List
    public final void add(int i, Object obj) {
        AbstractList.Companion companion = AbstractList.Companion;
        int i2 = this.size;
        companion.getClass();
        AbstractList.Companion.checkPositionIndex$kotlin_stdlib(i, i2);
        if (i == this.size) {
            addLast(obj);
            return;
        }
        if (i == 0) {
            addFirst(obj);
            return;
        }
        registerModification();
        ensureCapacity$1(this.size + 1);
        int positiveMod = positiveMod(this.head + i);
        int i3 = this.size;
        if (i < ((i3 + 1) >> 1)) {
            int length = positiveMod == 0 ? this.elementData.length - 1 : positiveMod - 1;
            int i4 = this.head;
            int length2 = i4 == 0 ? this.elementData.length - 1 : i4 - 1;
            if (length >= i4) {
                Object[] objArr = this.elementData;
                objArr[length2] = objArr[i4];
                ArraysKt___ArraysJvmKt.copyInto(objArr, objArr, i4, i4 + 1, length + 1);
            } else {
                Object[] objArr2 = this.elementData;
                ArraysKt___ArraysJvmKt.copyInto(objArr2, objArr2, i4 - 1, i4, objArr2.length);
                Object[] objArr3 = this.elementData;
                objArr3[objArr3.length - 1] = objArr3[0];
                ArraysKt___ArraysJvmKt.copyInto(objArr3, objArr3, 0, 1, length + 1);
            }
            this.elementData[length] = obj;
            this.head = length2;
        } else {
            int positiveMod2 = positiveMod(i3 + this.head);
            if (positiveMod < positiveMod2) {
                Object[] objArr4 = this.elementData;
                ArraysKt___ArraysJvmKt.copyInto(objArr4, objArr4, positiveMod + 1, positiveMod, positiveMod2);
            } else {
                Object[] objArr5 = this.elementData;
                ArraysKt___ArraysJvmKt.copyInto(objArr5, objArr5, 1, 0, positiveMod2);
                Object[] objArr6 = this.elementData;
                objArr6[0] = objArr6[objArr6.length - 1];
                ArraysKt___ArraysJvmKt.copyInto(objArr6, objArr6, positiveMod + 1, positiveMod, objArr6.length - 1);
            }
            this.elementData[positiveMod] = obj;
        }
        this.size++;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final Object[] toArray(Object[] objArr) {
        int length = objArr.length;
        int i = this.size;
        if (length < i) {
            objArr = (Object[]) Array.newInstance(objArr.getClass().getComponentType(), i);
        }
        int positiveMod = positiveMod(this.size + this.head);
        int i2 = this.head;
        if (i2 < positiveMod) {
            ArraysKt___ArraysJvmKt.copyInto$default(this.elementData, objArr, i2, positiveMod, 2);
        } else if (!isEmpty()) {
            Object[] objArr2 = this.elementData;
            ArraysKt___ArraysJvmKt.copyInto(objArr2, objArr, 0, this.head, objArr2.length);
            Object[] objArr3 = this.elementData;
            ArraysKt___ArraysJvmKt.copyInto(objArr3, objArr, objArr3.length - this.head, 0, positiveMod);
        }
        int i3 = this.size;
        if (i3 < objArr.length) {
            objArr[i3] = null;
        }
        return objArr;
    }

    @Override // java.util.AbstractList, java.util.List
    public final boolean addAll(int i, Collection collection) {
        AbstractList.Companion companion = AbstractList.Companion;
        int i2 = this.size;
        companion.getClass();
        AbstractList.Companion.checkPositionIndex$kotlin_stdlib(i, i2);
        if (collection.isEmpty()) {
            return false;
        }
        if (i == this.size) {
            return addAll(collection);
        }
        registerModification();
        ensureCapacity$1(collection.size() + this.size);
        int positiveMod = positiveMod(this.size + this.head);
        int positiveMod2 = positiveMod(this.head + i);
        int size = collection.size();
        if (i < ((this.size + 1) >> 1)) {
            int i3 = this.head;
            int i4 = i3 - size;
            if (positiveMod2 < i3) {
                Object[] objArr = this.elementData;
                ArraysKt___ArraysJvmKt.copyInto(objArr, objArr, i4, i3, objArr.length);
                if (size >= positiveMod2) {
                    Object[] objArr2 = this.elementData;
                    ArraysKt___ArraysJvmKt.copyInto(objArr2, objArr2, objArr2.length - size, 0, positiveMod2);
                } else {
                    Object[] objArr3 = this.elementData;
                    ArraysKt___ArraysJvmKt.copyInto(objArr3, objArr3, objArr3.length - size, 0, size);
                    Object[] objArr4 = this.elementData;
                    ArraysKt___ArraysJvmKt.copyInto(objArr4, objArr4, 0, size, positiveMod2);
                }
            } else if (i4 >= 0) {
                Object[] objArr5 = this.elementData;
                ArraysKt___ArraysJvmKt.copyInto(objArr5, objArr5, i4, i3, positiveMod2);
            } else {
                Object[] objArr6 = this.elementData;
                i4 += objArr6.length;
                int i5 = positiveMod2 - i3;
                int length = objArr6.length - i4;
                if (length >= i5) {
                    ArraysKt___ArraysJvmKt.copyInto(objArr6, objArr6, i4, i3, positiveMod2);
                } else {
                    ArraysKt___ArraysJvmKt.copyInto(objArr6, objArr6, i4, i3, i3 + length);
                    Object[] objArr7 = this.elementData;
                    ArraysKt___ArraysJvmKt.copyInto(objArr7, objArr7, 0, this.head + length, positiveMod2);
                }
            }
            this.head = i4;
            copyCollectionElements(negativeMod(positiveMod2 - size), collection);
            return true;
        }
        int i6 = positiveMod2 + size;
        if (positiveMod2 < positiveMod) {
            int i7 = size + positiveMod;
            Object[] objArr8 = this.elementData;
            if (i7 <= objArr8.length) {
                ArraysKt___ArraysJvmKt.copyInto(objArr8, objArr8, i6, positiveMod2, positiveMod);
            } else if (i6 >= objArr8.length) {
                ArraysKt___ArraysJvmKt.copyInto(objArr8, objArr8, i6 - objArr8.length, positiveMod2, positiveMod);
            } else {
                int length2 = positiveMod - (i7 - objArr8.length);
                ArraysKt___ArraysJvmKt.copyInto(objArr8, objArr8, 0, length2, positiveMod);
                Object[] objArr9 = this.elementData;
                ArraysKt___ArraysJvmKt.copyInto(objArr9, objArr9, i6, positiveMod2, length2);
            }
        } else {
            Object[] objArr10 = this.elementData;
            ArraysKt___ArraysJvmKt.copyInto(objArr10, objArr10, size, 0, positiveMod);
            Object[] objArr11 = this.elementData;
            if (i6 >= objArr11.length) {
                ArraysKt___ArraysJvmKt.copyInto(objArr11, objArr11, i6 - objArr11.length, positiveMod2, objArr11.length);
            } else {
                ArraysKt___ArraysJvmKt.copyInto(objArr11, objArr11, 0, objArr11.length - size, objArr11.length);
                Object[] objArr12 = this.elementData;
                ArraysKt___ArraysJvmKt.copyInto(objArr12, objArr12, i6, positiveMod2, objArr12.length - size);
            }
        }
        copyCollectionElements(positiveMod2, collection);
        return true;
    }

    public ArrayDeque() {
        this.elementData = emptyElementData;
    }

    public ArrayDeque(Collection<Object> collection) {
        Object[] array = collection.toArray(new Object[0]);
        this.elementData = array;
        this.size = array.length;
        if (array.length == 0) {
            this.elementData = emptyElementData;
        }
    }
}
