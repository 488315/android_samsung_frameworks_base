package androidx.compose.runtime.external.kotlinx.collections.immutable.implementations.immutableList;

import androidx.compose.runtime.PreconditionsKt;
import androidx.compose.runtime.external.kotlinx.collections.immutable.PersistentList;
import androidx.compose.runtime.external.kotlinx.collections.immutable.internal.ListImplementation;
import java.util.Arrays;
import java.util.ListIterator;
import kotlin.collections.ArraysKt___ArraysJvmKt;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class PersistentVector<E> extends AbstractPersistentList<E> implements PersistentList<E> {
    public final Object[] root;
    public final int rootShift;
    public final int size;
    public final Object[] tail;

    public PersistentVector(Object[] objArr, Object[] objArr2, int i, int i2) {
        this.root = objArr;
        this.tail = objArr2;
        this.size = i;
        this.rootShift = i2;
        if (!(getSize() > 32)) {
            PreconditionsKt.throwIllegalArgumentException("Trie-based persistent vector should have at least 33 elements, got " + getSize());
        }
        int length = objArr2.length;
    }

    public static Object[] insertIntoRoot(Object[] objArr, int i, int i2, Object obj, ObjectRef objectRef) {
        int indexSegment = UtilsKt.indexSegment(i2, i);
        if (i == 0) {
            Object[] copyOf = indexSegment == 0 ? new Object[32] : Arrays.copyOf(objArr, 32);
            ArraysKt___ArraysJvmKt.copyInto(objArr, copyOf, indexSegment + 1, indexSegment, 31);
            objectRef.value = objArr[31];
            copyOf[indexSegment] = obj;
            return copyOf;
        }
        Object[] copyOf2 = Arrays.copyOf(objArr, 32);
        int i3 = i - 5;
        copyOf2[indexSegment] = insertIntoRoot((Object[]) objArr[indexSegment], i3, i2, obj, objectRef);
        while (true) {
            indexSegment++;
            if (indexSegment >= 32 || copyOf2[indexSegment] == null) {
                break;
            }
            copyOf2[indexSegment] = insertIntoRoot((Object[]) objArr[indexSegment], i3, 0, objectRef.value, objectRef);
        }
        return copyOf2;
    }

    public static Object[] pullLastBuffer(Object[] objArr, int i, int i2, ObjectRef objectRef) {
        Object[] pullLastBuffer;
        int indexSegment = UtilsKt.indexSegment(i2, i);
        if (i == 5) {
            objectRef.value = objArr[indexSegment];
            pullLastBuffer = null;
        } else {
            pullLastBuffer = pullLastBuffer((Object[]) objArr[indexSegment], i - 5, i2, objectRef);
        }
        if (pullLastBuffer == null && indexSegment == 0) {
            return null;
        }
        Object[] copyOf = Arrays.copyOf(objArr, 32);
        copyOf[indexSegment] = pullLastBuffer;
        return copyOf;
    }

    public static Object[] setInRoot(Object[] objArr, int i, int i2, Object obj) {
        int indexSegment = UtilsKt.indexSegment(i2, i);
        Object[] copyOf = Arrays.copyOf(objArr, 32);
        if (i == 0) {
            copyOf[indexSegment] = obj;
            return copyOf;
        }
        copyOf[indexSegment] = setInRoot((Object[]) copyOf[indexSegment], i - 5, i2, obj);
        return copyOf;
    }

    @Override // java.util.List, androidx.compose.runtime.external.kotlinx.collections.immutable.PersistentList
    public final PersistentList add(int i, Object obj) {
        ListImplementation.checkPositionIndex$runtime_release(i, this.size);
        if (i == this.size) {
            return add(obj);
        }
        int rootSize = rootSize();
        if (i >= rootSize) {
            return insertIntoTail(obj, this.root, i - rootSize);
        }
        ObjectRef objectRef = new ObjectRef(null);
        return insertIntoTail(objectRef.value, insertIntoRoot(this.root, this.rootShift, i, obj, objectRef), 0);
    }

    @Override // androidx.compose.runtime.external.kotlinx.collections.immutable.PersistentList
    /* renamed from: builder$1, reason: merged with bridge method [inline-methods] */
    public final PersistentVectorBuilder builder() {
        return new PersistentVectorBuilder(this, this.root, this.tail, this.rootShift);
    }

    @Override // java.util.List
    public final Object get(int i) {
        Object[] objArr;
        ListImplementation.checkElementIndex$runtime_release(i, getSize());
        if (rootSize() <= i) {
            objArr = this.tail;
        } else {
            Object[] objArr2 = this.root;
            for (int i2 = this.rootShift; i2 > 0; i2 -= 5) {
                objArr2 = objArr2[UtilsKt.indexSegment(i, i2)];
            }
            objArr = objArr2;
        }
        return objArr[i & 31];
    }

    @Override // kotlin.collections.AbstractCollection
    public final int getSize() {
        return this.size;
    }

    public final PersistentVector insertIntoTail(Object obj, Object[] objArr, int i) {
        int rootSize = this.size - rootSize();
        Object[] copyOf = Arrays.copyOf(this.tail, 32);
        if (rootSize < 32) {
            ArraysKt___ArraysJvmKt.copyInto(this.tail, copyOf, i + 1, i, rootSize);
            copyOf[i] = obj;
            return new PersistentVector(objArr, copyOf, this.size + 1, this.rootShift);
        }
        Object[] objArr2 = this.tail;
        Object obj2 = objArr2[31];
        ArraysKt___ArraysJvmKt.copyInto(objArr2, copyOf, i + 1, i, rootSize - 1);
        copyOf[i] = obj;
        Object[] objArr3 = new Object[32];
        objArr3[0] = obj2;
        return pushFilledTail(objArr, copyOf, objArr3);
    }

    @Override // kotlin.collections.AbstractList, java.util.List
    public final ListIterator listIterator(int i) {
        ListImplementation.checkPositionIndex$runtime_release(i, this.size);
        return new PersistentVectorIterator(this.root, this.tail, i, this.size, (this.rootShift / 5) + 1);
    }

    public final PersistentVector pushFilledTail(Object[] objArr, Object[] objArr2, Object[] objArr3) {
        int i = this.size >> 5;
        int i2 = this.rootShift;
        if (i <= (1 << i2)) {
            return new PersistentVector(pushTail(i2, objArr, objArr2), objArr3, this.size + 1, this.rootShift);
        }
        Object[] objArr4 = new Object[32];
        objArr4[0] = objArr;
        int i3 = i2 + 5;
        return new PersistentVector(pushTail(i3, objArr4, objArr2), objArr3, this.size + 1, i3);
    }

    public final Object[] pushTail(int i, Object[] objArr, Object[] objArr2) {
        Object[] objArr3;
        int indexSegment = UtilsKt.indexSegment(getSize() - 1, i);
        if (objArr == null || (objArr3 = Arrays.copyOf(objArr, 32)) == null) {
            objArr3 = new Object[32];
        }
        if (i == 5) {
            objArr3[indexSegment] = objArr2;
            return objArr3;
        }
        objArr3[indexSegment] = pushTail(i - 5, (Object[]) objArr3[indexSegment], objArr2);
        return objArr3;
    }

    @Override // androidx.compose.runtime.external.kotlinx.collections.immutable.PersistentList
    public final PersistentList removeAll(Function1 function1) {
        PersistentVectorBuilder builder = builder();
        builder.removeAllWithPredicate(function1);
        return builder.build();
    }

    @Override // androidx.compose.runtime.external.kotlinx.collections.immutable.PersistentList
    public final PersistentList removeAt(int i) {
        ListImplementation.checkElementIndex$runtime_release(i, this.size);
        int rootSize = rootSize();
        return i >= rootSize ? removeFromTailAt(this.root, rootSize, this.rootShift, i - rootSize) : removeFromTailAt(removeFromRootAt(this.root, this.rootShift, i, new ObjectRef(this.tail[0])), rootSize, this.rootShift, 0);
    }

    public final Object[] removeFromRootAt(Object[] objArr, int i, int i2, ObjectRef objectRef) {
        int indexSegment = UtilsKt.indexSegment(i2, i);
        if (i == 0) {
            Object[] copyOf = indexSegment == 0 ? new Object[32] : Arrays.copyOf(objArr, 32);
            ArraysKt___ArraysJvmKt.copyInto(objArr, copyOf, indexSegment, indexSegment + 1, 32);
            copyOf[31] = objectRef.value;
            objectRef.value = objArr[indexSegment];
            return copyOf;
        }
        int indexSegment2 = objArr[31] == null ? UtilsKt.indexSegment(rootSize() - 1, i) : 31;
        Object[] copyOf2 = Arrays.copyOf(objArr, 32);
        int i3 = i - 5;
        int i4 = indexSegment + 1;
        if (i4 <= indexSegment2) {
            while (true) {
                copyOf2[indexSegment2] = removeFromRootAt((Object[]) copyOf2[indexSegment2], i3, 0, objectRef);
                if (indexSegment2 == i4) {
                    break;
                }
                indexSegment2--;
            }
        }
        copyOf2[indexSegment] = removeFromRootAt((Object[]) copyOf2[indexSegment], i3, i2, objectRef);
        return copyOf2;
    }

    public final AbstractPersistentList removeFromTailAt(Object[] objArr, int i, int i2, int i3) {
        int i4 = this.size - i;
        if (i4 != 1) {
            Object[] copyOf = Arrays.copyOf(this.tail, 32);
            int i5 = i4 - 1;
            if (i3 < i5) {
                ArraysKt___ArraysJvmKt.copyInto(this.tail, copyOf, i3, i3 + 1, i4);
            }
            copyOf[i5] = null;
            return new PersistentVector(objArr, copyOf, (i + i4) - 1, i2);
        }
        if (i2 == 0) {
            if (objArr.length == 33) {
                objArr = Arrays.copyOf(objArr, 32);
            }
            return new SmallPersistentVector(objArr);
        }
        ObjectRef objectRef = new ObjectRef(null);
        Object[] pullLastBuffer = pullLastBuffer(objArr, i2, i - 1, objectRef);
        pullLastBuffer.getClass();
        Object[] objArr2 = (Object[]) objectRef.value;
        return pullLastBuffer[1] == null ? new PersistentVector((Object[]) pullLastBuffer[0], objArr2, i, i2 - 5) : new PersistentVector(pullLastBuffer, objArr2, i, i2);
    }

    public final int rootSize() {
        return (this.size - 1) & (-32);
    }

    @Override // kotlin.collections.AbstractList, java.util.List, androidx.compose.runtime.external.kotlinx.collections.immutable.PersistentList
    public final PersistentList set(int i, Object obj) {
        ListImplementation.checkElementIndex$runtime_release(i, this.size);
        if (rootSize() > i) {
            return new PersistentVector(setInRoot(this.root, this.rootShift, i, obj), this.tail, this.size, this.rootShift);
        }
        Object[] copyOf = Arrays.copyOf(this.tail, 32);
        copyOf[i & 31] = obj;
        return new PersistentVector(this.root, copyOf, this.size, this.rootShift);
    }

    @Override // java.util.Collection, java.util.List, androidx.compose.runtime.external.kotlinx.collections.immutable.PersistentList
    public final PersistentList add(Object obj) {
        int rootSize = this.size - rootSize();
        if (rootSize < 32) {
            Object[] copyOf = Arrays.copyOf(this.tail, 32);
            copyOf[rootSize] = obj;
            return new PersistentVector(this.root, copyOf, this.size + 1, this.rootShift);
        }
        Object[] objArr = new Object[32];
        objArr[0] = obj;
        return pushFilledTail(this.root, this.tail, objArr);
    }
}
