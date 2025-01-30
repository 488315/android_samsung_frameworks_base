package kotlin.collections;

import android.support.v4.media.AbstractC0000x2c234b15;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import kotlin.collections.AbstractList;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class ArrayDeque extends AbstractMutableList {
    public static final Companion Companion = new Companion(null);
    public static final Object[] emptyElementData = new Object[0];
    public Object[] elementData;
    public int head;
    public int size;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public ArrayDeque(int i) {
        Object[] objArr;
        if (i == 0) {
            objArr = emptyElementData;
        } else {
            if (i <= 0) {
                throw new IllegalArgumentException(AbstractC0000x2c234b15.m0m("Illegal Capacity: ", i));
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
        ensureCapacity(collection.size() + getSize());
        copyCollectionElements(positiveMod(getSize() + this.head), collection);
        return true;
    }

    public final void addLast(Object obj) {
        ensureCapacity(getSize() + 1);
        this.elementData[positiveMod(getSize() + this.head)] = obj;
        this.size = getSize() + 1;
    }

    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final void clear() {
        int positiveMod = positiveMod(getSize() + this.head);
        int i = this.head;
        if (i < positiveMod) {
            Arrays.fill(this.elementData, i, positiveMod, (Object) null);
        } else if (!isEmpty()) {
            Object[] objArr = this.elementData;
            Arrays.fill(objArr, this.head, objArr.length, (Object) null);
            Arrays.fill(this.elementData, 0, positiveMod, (Object) null);
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
        this.size = collection.size() + getSize();
    }

    public final void ensureCapacity(int i) {
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
        Companion companion = Companion;
        int length = objArr.length;
        companion.getClass();
        int i2 = length + (length >> 1);
        if (i2 - i < 0) {
            i2 = i;
        }
        if (i2 - 2147483639 > 0) {
            i2 = i > 2147483639 ? Integer.MAX_VALUE : 2147483639;
        }
        Object[] objArr2 = new Object[i2];
        Object[] objArr3 = this.elementData;
        int i3 = this.head;
        System.arraycopy(objArr3, i3, objArr2, 0, objArr3.length - i3);
        Object[] objArr4 = this.elementData;
        int length2 = objArr4.length;
        int i4 = this.head;
        System.arraycopy(objArr4, 0, objArr2, length2 - i4, i4 - 0);
        this.head = 0;
        this.elementData = objArr2;
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

    public final int positiveMod(int i) {
        Object[] objArr = this.elementData;
        return i >= objArr.length ? i - objArr.length : i;
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
        if (!isEmpty()) {
            if (!(this.elementData.length == 0)) {
                int positiveMod2 = positiveMod(this.size + this.head);
                int i = this.head;
                if (i < positiveMod2) {
                    positiveMod = i;
                    while (i < positiveMod2) {
                        Object obj = this.elementData[i];
                        if (!collection.contains(obj)) {
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
                        if (!collection.contains(obj2)) {
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
                        if (!collection.contains(obj3)) {
                            Object[] objArr3 = this.elementData;
                            objArr3[positiveMod] = obj3;
                            positiveMod = positiveMod == objArr3.length + (-1) ? 0 : positiveMod + 1;
                        } else {
                            z2 = true;
                        }
                    }
                    z = z2;
                }
                if (z) {
                    int i4 = positiveMod - this.head;
                    if (i4 < 0) {
                        i4 += this.elementData.length;
                    }
                    this.size = i4;
                }
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
        if (i == CollectionsKt__CollectionsKt.getLastIndex(this)) {
            if (isEmpty()) {
                throw new NoSuchElementException("ArrayDeque is empty.");
            }
            int positiveMod = positiveMod(CollectionsKt__CollectionsKt.getLastIndex(this) + this.head);
            Object[] objArr = this.elementData;
            Object obj = objArr[positiveMod];
            objArr[positiveMod] = null;
            this.size--;
            return obj;
        }
        if (i == 0) {
            return removeFirst();
        }
        int positiveMod2 = positiveMod(this.head + i);
        Object[] objArr2 = this.elementData;
        Object obj2 = objArr2[positiveMod2];
        if (i < (this.size >> 1)) {
            int i3 = this.head;
            if (positiveMod2 >= i3) {
                System.arraycopy(objArr2, i3, objArr2, i3 + 1, positiveMod2 - i3);
            } else {
                System.arraycopy(objArr2, 0, objArr2, 1, positiveMod2 - 0);
                Object[] objArr3 = this.elementData;
                objArr3[0] = objArr3[objArr3.length - 1];
                int i4 = this.head;
                System.arraycopy(objArr3, i4, objArr3, i4 + 1, (objArr3.length - 1) - i4);
            }
            Object[] objArr4 = this.elementData;
            int i5 = this.head;
            objArr4[i5] = null;
            this.head = i5 != objArr4.length + (-1) ? i5 + 1 : 0;
        } else {
            int positiveMod3 = positiveMod(CollectionsKt__CollectionsKt.getLastIndex(this) + this.head);
            if (positiveMod2 <= positiveMod3) {
                Object[] objArr5 = this.elementData;
                int i6 = positiveMod2 + 1;
                System.arraycopy(objArr5, i6, objArr5, positiveMod2, (positiveMod3 + 1) - i6);
            } else {
                Object[] objArr6 = this.elementData;
                int i7 = positiveMod2 + 1;
                System.arraycopy(objArr6, i7, objArr6, positiveMod2, objArr6.length - i7);
                Object[] objArr7 = this.elementData;
                objArr7[objArr7.length - 1] = objArr7[0];
                System.arraycopy(objArr7, 1, objArr7, 0, (positiveMod3 + 1) - 1);
            }
            this.elementData[positiveMod3] = null;
        }
        this.size--;
        return obj2;
    }

    public final Object removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("ArrayDeque is empty.");
        }
        Object[] objArr = this.elementData;
        int i = this.head;
        Object obj = objArr[i];
        objArr[i] = null;
        this.head = i == objArr.length + (-1) ? 0 : i + 1;
        this.size--;
        return obj;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean retainAll(Collection collection) {
        int positiveMod;
        boolean z = false;
        if (!isEmpty()) {
            if (!(this.elementData.length == 0)) {
                int positiveMod2 = positiveMod(this.size + this.head);
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
                            Object[] objArr3 = this.elementData;
                            objArr3[positiveMod] = obj3;
                            positiveMod = positiveMod == objArr3.length + (-1) ? 0 : positiveMod + 1;
                        } else {
                            z2 = true;
                        }
                    }
                    z = z2;
                }
                if (z) {
                    int i4 = positiveMod - this.head;
                    if (i4 < 0) {
                        i4 += this.elementData.length;
                    }
                    this.size = i4;
                }
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
        int i3 = this.size;
        if (i == i3) {
            addLast(obj);
            return;
        }
        if (i == 0) {
            ensureCapacity(i3 + 1);
            int i4 = this.head;
            if (i4 == 0) {
                i4 = this.elementData.length;
            }
            int i5 = i4 - 1;
            this.head = i5;
            this.elementData[i5] = obj;
            this.size++;
            return;
        }
        ensureCapacity(i3 + 1);
        int positiveMod = positiveMod(this.head + i);
        int i6 = this.size;
        if (i < ((i6 + 1) >> 1)) {
            int length = positiveMod == 0 ? this.elementData.length - 1 : positiveMod - 1;
            int i7 = this.head;
            int length2 = i7 == 0 ? this.elementData.length - 1 : i7 - 1;
            if (length >= i7) {
                Object[] objArr = this.elementData;
                objArr[length2] = objArr[i7];
                int i8 = i7 + 1;
                System.arraycopy(objArr, i8, objArr, i7, (length + 1) - i8);
            } else {
                Object[] objArr2 = this.elementData;
                System.arraycopy(objArr2, i7, objArr2, i7 - 1, objArr2.length - i7);
                Object[] objArr3 = this.elementData;
                objArr3[objArr3.length - 1] = objArr3[0];
                System.arraycopy(objArr3, 1, objArr3, 0, (length + 1) - 1);
            }
            this.elementData[length] = obj;
            this.head = length2;
        } else {
            int positiveMod2 = positiveMod(i6 + this.head);
            if (positiveMod < positiveMod2) {
                Object[] objArr4 = this.elementData;
                System.arraycopy(objArr4, positiveMod, objArr4, positiveMod + 1, positiveMod2 - positiveMod);
            } else {
                Object[] objArr5 = this.elementData;
                System.arraycopy(objArr5, 0, objArr5, 1, positiveMod2 - 0);
                Object[] objArr6 = this.elementData;
                objArr6[0] = objArr6[objArr6.length - 1];
                System.arraycopy(objArr6, positiveMod, objArr6, positiveMod + 1, (objArr6.length - 1) - positiveMod);
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
            ArraysKt___ArraysJvmKt.copyInto$default(this.elementData, objArr, 0, i2, positiveMod, 2);
        } else if (!isEmpty()) {
            Object[] objArr2 = this.elementData;
            int i3 = this.head;
            System.arraycopy(objArr2, i3, objArr, 0, objArr2.length - i3);
            Object[] objArr3 = this.elementData;
            System.arraycopy(objArr3, 0, objArr, objArr3.length - this.head, positiveMod - 0);
        }
        int length2 = objArr.length;
        int i4 = this.size;
        if (length2 > i4) {
            objArr[i4] = null;
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
        int i3 = this.size;
        if (i == i3) {
            return addAll(collection);
        }
        ensureCapacity(collection.size() + i3);
        int positiveMod = positiveMod(this.size + this.head);
        int positiveMod2 = positiveMod(this.head + i);
        int size = collection.size();
        if (i < ((this.size + 1) >> 1)) {
            int i4 = this.head;
            int i5 = i4 - size;
            if (positiveMod2 < i4) {
                Object[] objArr = this.elementData;
                System.arraycopy(objArr, i4, objArr, i5, objArr.length - i4);
                if (size >= positiveMod2) {
                    Object[] objArr2 = this.elementData;
                    System.arraycopy(objArr2, 0, objArr2, objArr2.length - size, positiveMod2 + 0);
                } else {
                    Object[] objArr3 = this.elementData;
                    System.arraycopy(objArr3, 0, objArr3, objArr3.length - size, size + 0);
                    Object[] objArr4 = this.elementData;
                    System.arraycopy(objArr4, size, objArr4, 0, positiveMod2 - size);
                }
            } else if (i5 >= 0) {
                Object[] objArr5 = this.elementData;
                System.arraycopy(objArr5, i4, objArr5, i5, positiveMod2 - i4);
            } else {
                Object[] objArr6 = this.elementData;
                i5 += objArr6.length;
                int i6 = positiveMod2 - i4;
                int length = objArr6.length - i5;
                if (length >= i6) {
                    System.arraycopy(objArr6, i4, objArr6, i5, i6);
                } else {
                    System.arraycopy(objArr6, i4, objArr6, i5, (i4 + length) - i4);
                    Object[] objArr7 = this.elementData;
                    int i7 = this.head + length;
                    System.arraycopy(objArr7, i7, objArr7, 0, positiveMod2 - i7);
                }
            }
            this.head = i5;
            int i8 = positiveMod2 - size;
            if (i8 < 0) {
                i8 += this.elementData.length;
            }
            copyCollectionElements(i8, collection);
        } else {
            int i9 = positiveMod2 + size;
            if (positiveMod2 < positiveMod) {
                int i10 = size + positiveMod;
                Object[] objArr8 = this.elementData;
                if (i10 <= objArr8.length) {
                    System.arraycopy(objArr8, positiveMod2, objArr8, i9, positiveMod - positiveMod2);
                } else if (i9 >= objArr8.length) {
                    System.arraycopy(objArr8, positiveMod2, objArr8, i9 - objArr8.length, positiveMod - positiveMod2);
                } else {
                    int length2 = positiveMod - (i10 - objArr8.length);
                    System.arraycopy(objArr8, length2, objArr8, 0, positiveMod - length2);
                    Object[] objArr9 = this.elementData;
                    System.arraycopy(objArr9, positiveMod2, objArr9, i9, length2 - positiveMod2);
                }
            } else {
                Object[] objArr10 = this.elementData;
                System.arraycopy(objArr10, 0, objArr10, size, positiveMod - 0);
                Object[] objArr11 = this.elementData;
                if (i9 >= objArr11.length) {
                    System.arraycopy(objArr11, positiveMod2, objArr11, i9 - objArr11.length, objArr11.length - positiveMod2);
                } else {
                    int length3 = objArr11.length - size;
                    System.arraycopy(objArr11, length3, objArr11, 0, objArr11.length - length3);
                    Object[] objArr12 = this.elementData;
                    System.arraycopy(objArr12, positiveMod2, objArr12, i9, (objArr12.length - size) - positiveMod2);
                }
            }
            copyCollectionElements(positiveMod2, collection);
        }
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
