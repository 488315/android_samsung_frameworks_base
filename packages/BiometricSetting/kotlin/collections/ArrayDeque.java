package kotlin.collections;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ArrayDeque.kt */
/* loaded from: classes.dex */
public final class ArrayDeque<E> extends AbstractMutableList<E> {
    private static final Object[] emptyElementData = new Object[0];
    private Object[] elementData = emptyElementData;
    private int head;
    private int size;

    private final void copyCollectionElements(int i, Collection<? extends E> collection) {
        Iterator<? extends E> it = collection.iterator();
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

    private final void ensureCapacity(int i) {
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
        int length = objArr.length;
        int i2 = length + (length >> 1);
        if (i2 - i < 0) {
            i2 = i;
        }
        if (i2 - 2147483639 > 0) {
            i2 = i > 2147483639 ? Integer.MAX_VALUE : 2147483639;
        }
        Object[] objArr2 = new Object[i2];
        ArraysKt.copyInto(objArr, objArr2, 0, this.head, objArr.length);
        Object[] objArr3 = this.elementData;
        int length2 = objArr3.length;
        int i3 = this.head;
        ArraysKt.copyInto(objArr3, objArr2, length2 - i3, 0, i3);
        this.head = 0;
        this.elementData = objArr2;
    }

    private final int incremented(int i) {
        Intrinsics.checkNotNullParameter(this.elementData, "<this>");
        if (i == r1.length - 1) {
            return 0;
        }
        return i + 1;
    }

    private final int positiveMod(int i) {
        Object[] objArr = this.elementData;
        return i >= objArr.length ? i - objArr.length : i;
    }

    @Override // java.util.AbstractList, java.util.List
    public final void add(int i, E e) {
        int i2;
        int i3 = this.size;
        if (i < 0 || i > i3) {
            throw new IndexOutOfBoundsException("index: " + i + ", size: " + i3);
        }
        if (i == i3) {
            addLast(e);
            return;
        }
        if (i == 0) {
            ensureCapacity(i3 + 1);
            int i4 = this.head;
            if (i4 == 0) {
                Object[] objArr = this.elementData;
                Intrinsics.checkNotNullParameter(objArr, "<this>");
                i4 = objArr.length;
            }
            int i5 = i4 - 1;
            this.head = i5;
            this.elementData[i5] = e;
            this.size++;
            return;
        }
        ensureCapacity(i3 + 1);
        int positiveMod = positiveMod(this.head + i);
        int i6 = this.size;
        if (i < ((i6 + 1) >> 1)) {
            if (positiveMod == 0) {
                Object[] objArr2 = this.elementData;
                Intrinsics.checkNotNullParameter(objArr2, "<this>");
                i2 = objArr2.length - 1;
            } else {
                i2 = positiveMod - 1;
            }
            int i7 = this.head;
            if (i7 == 0) {
                Object[] objArr3 = this.elementData;
                Intrinsics.checkNotNullParameter(objArr3, "<this>");
                i7 = objArr3.length;
            }
            int i8 = i7 - 1;
            int i9 = this.head;
            if (i2 >= i9) {
                Object[] objArr4 = this.elementData;
                objArr4[i8] = objArr4[i9];
                ArraysKt.copyInto(objArr4, objArr4, i9, i9 + 1, i2 + 1);
            } else {
                Object[] objArr5 = this.elementData;
                ArraysKt.copyInto(objArr5, objArr5, i9 - 1, i9, objArr5.length);
                Object[] objArr6 = this.elementData;
                objArr6[objArr6.length - 1] = objArr6[0];
                ArraysKt.copyInto(objArr6, objArr6, 0, 1, i2 + 1);
            }
            this.elementData[i2] = e;
            this.head = i8;
        } else {
            int positiveMod2 = positiveMod(i6 + this.head);
            if (positiveMod < positiveMod2) {
                Object[] objArr7 = this.elementData;
                ArraysKt.copyInto(objArr7, objArr7, positiveMod + 1, positiveMod, positiveMod2);
            } else {
                Object[] objArr8 = this.elementData;
                ArraysKt.copyInto(objArr8, objArr8, 1, 0, positiveMod2);
                Object[] objArr9 = this.elementData;
                objArr9[0] = objArr9[objArr9.length - 1];
                ArraysKt.copyInto(objArr9, objArr9, positiveMod + 1, positiveMod, objArr9.length - 1);
            }
            this.elementData[positiveMod] = e;
        }
        this.size++;
    }

    @Override // java.util.AbstractList, java.util.List
    public final boolean addAll(int i, Collection<? extends E> elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        int i2 = this.size;
        if (i < 0 || i > i2) {
            throw new IndexOutOfBoundsException("index: " + i + ", size: " + i2);
        }
        if (elements.isEmpty()) {
            return false;
        }
        int i3 = this.size;
        if (i == i3) {
            return addAll(elements);
        }
        ensureCapacity(elements.size() + i3);
        int positiveMod = positiveMod(this.size + this.head);
        int positiveMod2 = positiveMod(this.head + i);
        int size = elements.size();
        if (i < ((this.size + 1) >> 1)) {
            int i4 = this.head;
            int i5 = i4 - size;
            if (positiveMod2 < i4) {
                Object[] objArr = this.elementData;
                ArraysKt.copyInto(objArr, objArr, i5, i4, objArr.length);
                if (size >= positiveMod2) {
                    Object[] objArr2 = this.elementData;
                    ArraysKt.copyInto(objArr2, objArr2, objArr2.length - size, 0, positiveMod2);
                } else {
                    Object[] objArr3 = this.elementData;
                    ArraysKt.copyInto(objArr3, objArr3, objArr3.length - size, 0, size);
                    Object[] objArr4 = this.elementData;
                    ArraysKt.copyInto(objArr4, objArr4, 0, size, positiveMod2);
                }
            } else if (i5 >= 0) {
                Object[] objArr5 = this.elementData;
                ArraysKt.copyInto(objArr5, objArr5, i5, i4, positiveMod2);
            } else {
                Object[] objArr6 = this.elementData;
                i5 += objArr6.length;
                int i6 = positiveMod2 - i4;
                int length = objArr6.length - i5;
                if (length >= i6) {
                    ArraysKt.copyInto(objArr6, objArr6, i5, i4, positiveMod2);
                } else {
                    ArraysKt.copyInto(objArr6, objArr6, i5, i4, i4 + length);
                    Object[] objArr7 = this.elementData;
                    ArraysKt.copyInto(objArr7, objArr7, 0, this.head + length, positiveMod2);
                }
            }
            this.head = i5;
            int i7 = positiveMod2 - size;
            if (i7 < 0) {
                i7 += this.elementData.length;
            }
            copyCollectionElements(i7, elements);
        } else {
            int i8 = positiveMod2 + size;
            if (positiveMod2 < positiveMod) {
                int i9 = size + positiveMod;
                Object[] objArr8 = this.elementData;
                if (i9 <= objArr8.length) {
                    ArraysKt.copyInto(objArr8, objArr8, i8, positiveMod2, positiveMod);
                } else if (i8 >= objArr8.length) {
                    ArraysKt.copyInto(objArr8, objArr8, i8 - objArr8.length, positiveMod2, positiveMod);
                } else {
                    int length2 = positiveMod - (i9 - objArr8.length);
                    ArraysKt.copyInto(objArr8, objArr8, 0, length2, positiveMod);
                    Object[] objArr9 = this.elementData;
                    ArraysKt.copyInto(objArr9, objArr9, i8, positiveMod2, length2);
                }
            } else {
                Object[] objArr10 = this.elementData;
                ArraysKt.copyInto(objArr10, objArr10, size, 0, positiveMod);
                Object[] objArr11 = this.elementData;
                if (i8 >= objArr11.length) {
                    ArraysKt.copyInto(objArr11, objArr11, i8 - objArr11.length, positiveMod2, objArr11.length);
                } else {
                    ArraysKt.copyInto(objArr11, objArr11, 0, objArr11.length - size, objArr11.length);
                    Object[] objArr12 = this.elementData;
                    ArraysKt.copyInto(objArr12, objArr12, i8, positiveMod2, objArr12.length - size);
                }
            }
            copyCollectionElements(positiveMod2, elements);
        }
        return true;
    }

    public final void addLast(E e) {
        ensureCapacity(getSize() + 1);
        this.elementData[positiveMod(getSize() + this.head)] = e;
        this.size = getSize() + 1;
    }

    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final void clear() {
        int positiveMod = positiveMod(this.size + this.head);
        int i = this.head;
        if (i < positiveMod) {
            Object[] objArr = this.elementData;
            Intrinsics.checkNotNullParameter(objArr, "<this>");
            Arrays.fill(objArr, i, positiveMod, (Object) null);
        } else if (!isEmpty()) {
            Object[] objArr2 = this.elementData;
            Arrays.fill(objArr2, this.head, objArr2.length, (Object) null);
            Object[] objArr3 = this.elementData;
            Intrinsics.checkNotNullParameter(objArr3, "<this>");
            Arrays.fill(objArr3, 0, positiveMod, (Object) null);
        }
        this.head = 0;
        this.size = 0;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean contains(Object obj) {
        return indexOf(obj) != -1;
    }

    @Override // java.util.AbstractList, java.util.List
    public final E get(int i) {
        int size = getSize();
        if (i >= 0 && i < size) {
            return (E) this.elementData[positiveMod(this.head + i)];
        }
        throw new IndexOutOfBoundsException("index: " + i + ", size: " + size);
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
        int positiveMod = positiveMod(this.size + this.head);
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
                    Object[] objArr = this.elementData;
                    Intrinsics.checkNotNullParameter(objArr, "<this>");
                    length = objArr.length - 1;
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

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean remove(Object obj) {
        int indexOf = indexOf(obj);
        if (indexOf == -1) {
            return false;
        }
        removeAt(indexOf);
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean removeAll(Collection<? extends Object> elements) {
        int positiveMod;
        Intrinsics.checkNotNullParameter(elements, "elements");
        boolean z = false;
        z = false;
        z = false;
        if (!isEmpty()) {
            if ((this.elementData.length == 0) == false) {
                int positiveMod2 = positiveMod(this.size + this.head);
                int i = this.head;
                if (i < positiveMod2) {
                    positiveMod = i;
                    while (i < positiveMod2) {
                        Object obj = this.elementData[i];
                        if (!elements.contains(obj)) {
                            this.elementData[positiveMod] = obj;
                            positiveMod++;
                        } else {
                            z = true;
                        }
                        i++;
                    }
                    Object[] objArr = this.elementData;
                    Intrinsics.checkNotNullParameter(objArr, "<this>");
                    Arrays.fill(objArr, positiveMod, positiveMod2, (Object) null);
                } else {
                    int length = this.elementData.length;
                    boolean z2 = false;
                    int i2 = i;
                    while (i < length) {
                        Object[] objArr2 = this.elementData;
                        Object obj2 = objArr2[i];
                        objArr2[i] = null;
                        if (!elements.contains(obj2)) {
                            this.elementData[i2] = obj2;
                            i2++;
                        } else {
                            z2 = true;
                        }
                        i++;
                    }
                    positiveMod = positiveMod(i2);
                    for (int i3 = 0; i3 < positiveMod2; i3++) {
                        Object[] objArr3 = this.elementData;
                        Object obj3 = objArr3[i3];
                        objArr3[i3] = null;
                        if (!elements.contains(obj3)) {
                            this.elementData[positiveMod] = obj3;
                            positiveMod = incremented(positiveMod);
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
    public final E removeAt(int i) {
        int i2 = this.size;
        if (i < 0 || i >= i2) {
            throw new IndexOutOfBoundsException("index: " + i + ", size: " + i2);
        }
        if (i == CollectionsKt.getLastIndex(this)) {
            if (isEmpty()) {
                throw new NoSuchElementException("ArrayDeque is empty.");
            }
            int positiveMod = positiveMod(CollectionsKt.getLastIndex(this) + this.head);
            Object[] objArr = this.elementData;
            E e = (E) objArr[positiveMod];
            objArr[positiveMod] = null;
            this.size--;
            return e;
        }
        if (i == 0) {
            if (isEmpty()) {
                throw new NoSuchElementException("ArrayDeque is empty.");
            }
            Object[] objArr2 = this.elementData;
            int i3 = this.head;
            E e2 = (E) objArr2[i3];
            objArr2[i3] = null;
            this.head = incremented(i3);
            this.size--;
            return e2;
        }
        int positiveMod2 = positiveMod(this.head + i);
        Object[] objArr3 = this.elementData;
        E e3 = (E) objArr3[positiveMod2];
        if (i < (this.size >> 1)) {
            int i4 = this.head;
            if (positiveMod2 >= i4) {
                ArraysKt.copyInto(objArr3, objArr3, i4 + 1, i4, positiveMod2);
            } else {
                ArraysKt.copyInto(objArr3, objArr3, 1, 0, positiveMod2);
                Object[] objArr4 = this.elementData;
                objArr4[0] = objArr4[objArr4.length - 1];
                int i5 = this.head;
                ArraysKt.copyInto(objArr4, objArr4, i5 + 1, i5, objArr4.length - 1);
            }
            Object[] objArr5 = this.elementData;
            int i6 = this.head;
            objArr5[i6] = null;
            this.head = incremented(i6);
        } else {
            int positiveMod3 = positiveMod(CollectionsKt.getLastIndex(this) + this.head);
            if (positiveMod2 <= positiveMod3) {
                Object[] objArr6 = this.elementData;
                ArraysKt.copyInto(objArr6, objArr6, positiveMod2, positiveMod2 + 1, positiveMod3 + 1);
            } else {
                Object[] objArr7 = this.elementData;
                ArraysKt.copyInto(objArr7, objArr7, positiveMod2, positiveMod2 + 1, objArr7.length);
                Object[] objArr8 = this.elementData;
                objArr8[objArr8.length - 1] = objArr8[0];
                ArraysKt.copyInto(objArr8, objArr8, 0, 1, positiveMod3 + 1);
            }
            this.elementData[positiveMod3] = null;
        }
        this.size--;
        return e3;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean retainAll(Collection<? extends Object> elements) {
        int positiveMod;
        Intrinsics.checkNotNullParameter(elements, "elements");
        boolean z = false;
        z = false;
        z = false;
        if (!isEmpty()) {
            if ((this.elementData.length == 0) == false) {
                int positiveMod2 = positiveMod(this.size + this.head);
                int i = this.head;
                if (i < positiveMod2) {
                    positiveMod = i;
                    while (i < positiveMod2) {
                        Object obj = this.elementData[i];
                        if (elements.contains(obj)) {
                            this.elementData[positiveMod] = obj;
                            positiveMod++;
                        } else {
                            z = true;
                        }
                        i++;
                    }
                    Object[] objArr = this.elementData;
                    Intrinsics.checkNotNullParameter(objArr, "<this>");
                    Arrays.fill(objArr, positiveMod, positiveMod2, (Object) null);
                } else {
                    int length = this.elementData.length;
                    boolean z2 = false;
                    int i2 = i;
                    while (i < length) {
                        Object[] objArr2 = this.elementData;
                        Object obj2 = objArr2[i];
                        objArr2[i] = null;
                        if (elements.contains(obj2)) {
                            this.elementData[i2] = obj2;
                            i2++;
                        } else {
                            z2 = true;
                        }
                        i++;
                    }
                    positiveMod = positiveMod(i2);
                    for (int i3 = 0; i3 < positiveMod2; i3++) {
                        Object[] objArr3 = this.elementData;
                        Object obj3 = objArr3[i3];
                        objArr3[i3] = null;
                        if (elements.contains(obj3)) {
                            this.elementData[positiveMod] = obj3;
                            positiveMod = incremented(positiveMod);
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
    public final E set(int i, E e) {
        int size = getSize();
        if (i < 0 || i >= size) {
            throw new IndexOutOfBoundsException("index: " + i + ", size: " + size);
        }
        int positiveMod = positiveMod(this.head + i);
        Object[] objArr = this.elementData;
        E e2 = (E) objArr[positiveMod];
        objArr[positiveMod] = e;
        return e2;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final Object[] toArray() {
        return toArray(new Object[getSize()]);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final <T> T[] toArray(T[] array) {
        Intrinsics.checkNotNullParameter(array, "array");
        int length = array.length;
        int i = this.size;
        if (length < i) {
            Object newInstance = Array.newInstance(array.getClass().getComponentType(), i);
            Intrinsics.checkNotNull(newInstance, "null cannot be cast to non-null type kotlin.Array<T of kotlin.collections.ArraysKt__ArraysJVMKt.arrayOfNulls>");
            array = (T[]) ((Object[]) newInstance);
        }
        int positiveMod = positiveMod(this.size + this.head);
        int i2 = this.head;
        if (i2 < positiveMod) {
            ArraysKt.copyInto$default(this.elementData, array, i2, positiveMod, 2);
        } else if (!isEmpty()) {
            Object[] objArr = this.elementData;
            ArraysKt.copyInto(objArr, array, 0, this.head, objArr.length);
            Object[] objArr2 = this.elementData;
            ArraysKt.copyInto(objArr2, array, objArr2.length - this.head, 0, positiveMod);
        }
        int length2 = array.length;
        int i3 = this.size;
        if (length2 > i3) {
            array[i3] = null;
        }
        return array;
    }

    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean add(E e) {
        addLast(e);
        return true;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean addAll(Collection<? extends E> elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        if (elements.isEmpty()) {
            return false;
        }
        ensureCapacity(elements.size() + getSize());
        copyCollectionElements(positiveMod(getSize() + this.head), elements);
        return true;
    }
}
