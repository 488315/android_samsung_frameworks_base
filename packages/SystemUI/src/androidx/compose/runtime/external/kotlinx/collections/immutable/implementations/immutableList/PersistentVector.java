package androidx.compose.runtime.external.kotlinx.collections.immutable.implementations.immutableList;

import androidx.compose.runtime.PreconditionsKt;
import androidx.compose.runtime.external.kotlinx.collections.immutable.PersistentList;
import androidx.compose.runtime.external.kotlinx.collections.immutable.internal.ListImplementation;
import java.util.Arrays;
import java.util.ListIterator;
import kotlin.collections.ArraysKt___ArraysJvmKt;
import kotlin.jvm.functions.Function1;

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
        int iIndexSegment = UtilsKt.indexSegment(i2, i);
        if (i == 0) {
            Object[] objArrCopyOf = iIndexSegment == 0 ? new Object[32] : Arrays.copyOf(objArr, 32);
            ArraysKt___ArraysJvmKt.copyInto(objArr, objArrCopyOf, iIndexSegment + 1, iIndexSegment, 31);
            objectRef.value = objArr[31];
            objArrCopyOf[iIndexSegment] = obj;
            return objArrCopyOf;
        }
        Object[] objArrCopyOf2 = Arrays.copyOf(objArr, 32);
        int i3 = i - 5;
        objArrCopyOf2[iIndexSegment] = insertIntoRoot((Object[]) objArr[iIndexSegment], i3, i2, obj, objectRef);
        while (true) {
            iIndexSegment++;
            if (iIndexSegment >= 32 || objArrCopyOf2[iIndexSegment] == null) {
                break;
            }
            objArrCopyOf2[iIndexSegment] = insertIntoRoot((Object[]) objArr[iIndexSegment], i3, 0, objectRef.value, objectRef);
        }
        return objArrCopyOf2;
    }

    public static Object[] pullLastBuffer(Object[] objArr, int i, int i2, ObjectRef objectRef) {
        Object[] objArrPullLastBuffer;
        int iIndexSegment = UtilsKt.indexSegment(i2, i);
        if (i == 5) {
            objectRef.value = objArr[iIndexSegment];
            objArrPullLastBuffer = null;
        } else {
            objArrPullLastBuffer = pullLastBuffer((Object[]) objArr[iIndexSegment], i - 5, i2, objectRef);
        }
        if (objArrPullLastBuffer == null && iIndexSegment == 0) {
            return null;
        }
        Object[] objArrCopyOf = Arrays.copyOf(objArr, 32);
        objArrCopyOf[iIndexSegment] = objArrPullLastBuffer;
        return objArrCopyOf;
    }

    public static Object[] setInRoot(Object[] objArr, int i, int i2, Object obj) {
        int iIndexSegment = UtilsKt.indexSegment(i2, i);
        Object[] objArrCopyOf = Arrays.copyOf(objArr, 32);
        if (i == 0) {
            objArrCopyOf[iIndexSegment] = obj;
            return objArrCopyOf;
        }
        objArrCopyOf[iIndexSegment] = setInRoot((Object[]) objArrCopyOf[iIndexSegment], i - 5, i2, obj);
        return objArrCopyOf;
    }

    @Override // java.util.List, androidx.compose.runtime.external.kotlinx.collections.immutable.PersistentList
    public final PersistentList add(int i, Object obj) {
        ListImplementation.checkPositionIndex$runtime_release(i, this.size);
        if (i == this.size) {
            return add(obj);
        }
        int iRootSize = rootSize();
        if (i >= iRootSize) {
            return insertIntoTail(obj, this.root, i - iRootSize);
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
        int iRootSize = this.size - rootSize();
        Object[] objArrCopyOf = Arrays.copyOf(this.tail, 32);
        if (iRootSize < 32) {
            ArraysKt___ArraysJvmKt.copyInto(this.tail, objArrCopyOf, i + 1, i, iRootSize);
            objArrCopyOf[i] = obj;
            return new PersistentVector(objArr, objArrCopyOf, this.size + 1, this.rootShift);
        }
        Object[] objArr2 = this.tail;
        Object obj2 = objArr2[31];
        ArraysKt___ArraysJvmKt.copyInto(objArr2, objArrCopyOf, i + 1, i, iRootSize - 1);
        objArrCopyOf[i] = obj;
        Object[] objArr3 = new Object[32];
        objArr3[0] = obj2;
        return pushFilledTail(objArr, objArrCopyOf, objArr3);
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
        Object[] objArrCopyOf;
        int iIndexSegment = UtilsKt.indexSegment(getSize() - 1, i);
        if (objArr == null || (objArrCopyOf = Arrays.copyOf(objArr, 32)) == null) {
            objArrCopyOf = new Object[32];
        }
        if (i == 5) {
            objArrCopyOf[iIndexSegment] = objArr2;
            return objArrCopyOf;
        }
        objArrCopyOf[iIndexSegment] = pushTail(i - 5, (Object[]) objArrCopyOf[iIndexSegment], objArr2);
        return objArrCopyOf;
    }

    @Override // androidx.compose.runtime.external.kotlinx.collections.immutable.PersistentList
    public final PersistentList removeAll(Function1 function1) {
        PersistentVectorBuilder persistentVectorBuilderBuilder = builder();
        persistentVectorBuilderBuilder.removeAllWithPredicate(function1);
        return persistentVectorBuilderBuilder.build();
    }

    @Override // androidx.compose.runtime.external.kotlinx.collections.immutable.PersistentList
    public final PersistentList removeAt(int i) {
        ListImplementation.checkElementIndex$runtime_release(i, this.size);
        int iRootSize = rootSize();
        return i >= iRootSize ? removeFromTailAt(this.root, iRootSize, this.rootShift, i - iRootSize) : removeFromTailAt(removeFromRootAt(this.root, this.rootShift, i, new ObjectRef(this.tail[0])), iRootSize, this.rootShift, 0);
    }

    public final Object[] removeFromRootAt(Object[] objArr, int i, int i2, ObjectRef objectRef) {
        int iIndexSegment = UtilsKt.indexSegment(i2, i);
        if (i == 0) {
            Object[] objArrCopyOf = iIndexSegment == 0 ? new Object[32] : Arrays.copyOf(objArr, 32);
            ArraysKt___ArraysJvmKt.copyInto(objArr, objArrCopyOf, iIndexSegment, iIndexSegment + 1, 32);
            objArrCopyOf[31] = objectRef.value;
            objectRef.value = objArr[iIndexSegment];
            return objArrCopyOf;
        }
        int iIndexSegment2 = objArr[31] == null ? UtilsKt.indexSegment(rootSize() - 1, i) : 31;
        Object[] objArrCopyOf2 = Arrays.copyOf(objArr, 32);
        int i3 = i - 5;
        int i4 = iIndexSegment + 1;
        if (i4 <= iIndexSegment2) {
            while (true) {
                objArrCopyOf2[iIndexSegment2] = removeFromRootAt((Object[]) objArrCopyOf2[iIndexSegment2], i3, 0, objectRef);
                if (iIndexSegment2 == i4) {
                    break;
                }
                iIndexSegment2--;
            }
        }
        objArrCopyOf2[iIndexSegment] = removeFromRootAt((Object[]) objArrCopyOf2[iIndexSegment], i3, i2, objectRef);
        return objArrCopyOf2;
    }

    public final AbstractPersistentList removeFromTailAt(Object[] objArr, int i, int i2, int i3) {
        int i4 = this.size - i;
        if (i4 != 1) {
            Object[] objArrCopyOf = Arrays.copyOf(this.tail, 32);
            int i5 = i4 - 1;
            if (i3 < i5) {
                ArraysKt___ArraysJvmKt.copyInto(this.tail, objArrCopyOf, i3, i3 + 1, i4);
            }
            objArrCopyOf[i5] = null;
            return new PersistentVector(objArr, objArrCopyOf, (i + i4) - 1, i2);
        }
        if (i2 == 0) {
            if (objArr.length == 33) {
                objArr = Arrays.copyOf(objArr, 32);
            }
            return new SmallPersistentVector(objArr);
        }
        ObjectRef objectRef = new ObjectRef(null);
        Object[] objArrPullLastBuffer = pullLastBuffer(objArr, i2, i - 1, objectRef);
        objArrPullLastBuffer.getClass();
        Object[] objArr2 = (Object[]) objectRef.value;
        return objArrPullLastBuffer[1] == null ? new PersistentVector((Object[]) objArrPullLastBuffer[0], objArr2, i, i2 - 5) : new PersistentVector(objArrPullLastBuffer, objArr2, i, i2);
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
        Object[] objArrCopyOf = Arrays.copyOf(this.tail, 32);
        objArrCopyOf[i & 31] = obj;
        return new PersistentVector(this.root, objArrCopyOf, this.size, this.rootShift);
    }

    @Override // java.util.Collection, java.util.List, androidx.compose.runtime.external.kotlinx.collections.immutable.PersistentList
    public final PersistentList add(Object obj) {
        int iRootSize = this.size - rootSize();
        if (iRootSize < 32) {
            Object[] objArrCopyOf = Arrays.copyOf(this.tail, 32);
            objArrCopyOf[iRootSize] = obj;
            return new PersistentVector(this.root, objArrCopyOf, this.size + 1, this.rootShift);
        }
        Object[] objArr = new Object[32];
        objArr[0] = obj;
        return pushFilledTail(this.root, this.tail, objArr);
    }
}
