package androidx.compose.runtime.external.kotlinx.collections.immutable.implementations.immutableList;

import androidx.compose.runtime.PreconditionsKt;
import androidx.compose.runtime.external.kotlinx.collections.immutable.PersistentList;
import androidx.compose.runtime.external.kotlinx.collections.immutable.internal.ListImplementation;
import androidx.compose.runtime.external.kotlinx.collections.immutable.internal.MutabilityOwnership;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import kotlin.collections.AbstractMutableList;
import kotlin.collections.ArraysKt___ArraysJvmKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.ArrayIterator;
import kotlin.jvm.internal.markers.KMutableCollection;

/* loaded from: classes.dex */
public final class PersistentVectorBuilder<E> extends AbstractMutableList implements List, Collection, KMutableCollection {
    public MutabilityOwnership ownership = new MutabilityOwnership();
    public Object[] root;
    public int rootShift;
    public int size;
    public Object[] tail;
    public PersistentList vector;
    public Object[] vectorRoot;
    public Object[] vectorTail;

    public PersistentVectorBuilder(PersistentList<? extends E> persistentList, Object[] objArr, Object[] objArr2, int i) {
        this.vector = persistentList;
        this.vectorRoot = objArr;
        this.vectorTail = objArr2;
        this.rootShift = i;
        this.root = this.vectorRoot;
        this.tail = this.vectorTail;
        this.size = this.vector.size();
    }

    public static void copyToBuffer(Object[] objArr, int i, Iterator it) {
        while (i < 32 && it.hasNext()) {
            objArr[i] = it.next();
            i++;
        }
    }

    @Override // java.util.AbstractList, java.util.List
    public final void add(int i, Object obj) {
        ListImplementation.checkPositionIndex$runtime_release(i, getSize());
        if (i == getSize()) {
            add(obj);
            return;
        }
        ((AbstractList) this).modCount++;
        int iRootSize$1 = rootSize$1();
        if (i >= iRootSize$1) {
            insertIntoTail(obj, this.root, i - iRootSize$1);
            return;
        }
        ObjectRef objectRef = new ObjectRef(null);
        Object[] objArr = this.root;
        objArr.getClass();
        insertIntoTail(objectRef.value, insertIntoRoot$1(objArr, this.rootShift, i, obj, objectRef), 0);
    }

    @Override // java.util.AbstractList, java.util.List
    public final boolean addAll(int i, Collection collection) {
        Collection collection2;
        Object[] objArrMutableBuffer;
        ListImplementation.checkPositionIndex$runtime_release(i, this.size);
        if (i == this.size) {
            return addAll(collection);
        }
        if (collection.isEmpty()) {
            return false;
        }
        ((AbstractList) this).modCount++;
        int i2 = (i >> 5) << 5;
        int size = ((collection.size() + (this.size - i2)) - 1) / 32;
        if (size == 0) {
            int i3 = i & 31;
            int size2 = ((collection.size() + i) - 1) & 31;
            Object[] objArr = this.tail;
            Object[] objArrMakeMutable = makeMutable(objArr);
            System.arraycopy(objArr, i3, objArrMakeMutable, size2 + 1, tailSize() - i3);
            copyToBuffer(objArrMakeMutable, i3, collection.iterator());
            this.tail = objArrMakeMutable;
            this.size = collection.size() + this.size;
            return true;
        }
        Object[][] objArr2 = new Object[size][];
        int iTailSize = tailSize();
        int size3 = collection.size() + this.size;
        if (size3 > 32) {
            size3 -= (size3 - 1) & (-32);
        }
        if (i >= rootSize$1()) {
            objArrMutableBuffer = mutableBuffer();
            collection2 = collection;
            splitToBuffers(collection2, i, this.tail, iTailSize, objArr2, size, objArrMutableBuffer);
            objArr2 = objArr2;
        } else {
            collection2 = collection;
            if (size3 > iTailSize) {
                int i4 = size3 - iTailSize;
                Object[] objArrMakeMutableShiftingRight = makeMutableShiftingRight(i4, this.tail);
                insertIntoRoot(collection2, i, i4, objArr2, size, objArrMakeMutableShiftingRight);
                objArr2 = objArr2;
                objArrMutableBuffer = objArrMakeMutableShiftingRight;
            } else {
                Object[] objArr3 = this.tail;
                objArrMutableBuffer = mutableBuffer();
                int i5 = iTailSize - size3;
                System.arraycopy(objArr3, i5, objArrMutableBuffer, 0, iTailSize - i5);
                int i6 = 32 - i5;
                Object[] objArrMakeMutableShiftingRight2 = makeMutableShiftingRight(i6, this.tail);
                int i7 = size - 1;
                objArr2[i7] = objArrMakeMutableShiftingRight2;
                insertIntoRoot(collection2, i, i6, objArr2, i7, objArrMakeMutableShiftingRight2);
                collection2 = collection2;
            }
        }
        this.root = pushBuffersIncreasingHeightIfNeeded(this.root, i2, objArr2);
        this.tail = objArrMutableBuffer;
        this.size = collection2.size() + this.size;
        return true;
    }

    public final PersistentList build() {
        PersistentList persistentVector;
        if (this.root == this.vectorRoot && this.tail == this.vectorTail) {
            persistentVector = this.vector;
        } else {
            this.ownership = new MutabilityOwnership();
            Object[] objArr = this.root;
            this.vectorRoot = objArr;
            Object[] objArr2 = this.tail;
            this.vectorTail = objArr2;
            if (objArr != null) {
                Object[] objArr3 = this.root;
                objArr3.getClass();
                persistentVector = new PersistentVector(objArr3, this.tail, this.size, this.rootShift);
            } else if (objArr2.length == 0) {
                SmallPersistentVector.Companion.getClass();
                persistentVector = SmallPersistentVector.EMPTY;
            } else {
                persistentVector = new SmallPersistentVector(Arrays.copyOf(this.tail, this.size));
            }
        }
        this.vector = persistentVector;
        return persistentVector;
    }

    @Override // java.util.AbstractList, java.util.List
    public final Object get(int i) {
        Object[] objArr;
        ListImplementation.checkElementIndex$runtime_release(i, getSize());
        if (rootSize$1() <= i) {
            objArr = this.tail;
        } else {
            Object[] objArr2 = this.root;
            objArr2.getClass();
            for (int i2 = this.rootShift; i2 > 0; i2 -= 5) {
                objArr2 = objArr2[UtilsKt.indexSegment(i, i2)];
            }
            objArr = objArr2;
        }
        return objArr[i & 31];
    }

    public final int getModCount$runtime_release() {
        return ((AbstractList) this).modCount;
    }

    @Override // kotlin.collections.AbstractMutableList
    public final int getSize() {
        return this.size;
    }

    public final void insertIntoRoot(Collection collection, int i, int i2, Object[][] objArr, int i3, Object[] objArr2) {
        if (this.root == null) {
            throw new IllegalStateException("root is null");
        }
        int i4 = i >> 5;
        AbstractListIterator abstractListIteratorLeafBufferIterator = leafBufferIterator(rootSize$1() >> 5);
        int i5 = i3;
        Object[] objArrMakeMutableShiftingRight = objArr2;
        while (abstractListIteratorLeafBufferIterator.previousIndex() != i4) {
            Object[] objArr3 = (Object[]) abstractListIteratorLeafBufferIterator.previous();
            ArraysKt___ArraysJvmKt.copyInto(objArr3, objArrMakeMutableShiftingRight, 0, 32 - i2, 32);
            objArrMakeMutableShiftingRight = makeMutableShiftingRight(i2, objArr3);
            i5--;
            objArr[i5] = objArrMakeMutableShiftingRight;
        }
        Object[] objArr4 = (Object[]) abstractListIteratorLeafBufferIterator.previous();
        int iRootSize$1 = i3 - (((rootSize$1() >> 5) - 1) - i4);
        if (iRootSize$1 < i3) {
            objArr2 = objArr[iRootSize$1];
            objArr2.getClass();
        }
        splitToBuffers(collection, i, objArr4, 32, objArr, iRootSize$1, objArr2);
    }

    public final Object[] insertIntoRoot$1(Object[] objArr, int i, int i2, Object obj, ObjectRef objectRef) {
        Object obj2;
        int iIndexSegment = UtilsKt.indexSegment(i2, i);
        if (i == 0) {
            objectRef.value = objArr[31];
            Object[] objArrMakeMutable = makeMutable(objArr);
            System.arraycopy(objArr, iIndexSegment, objArrMakeMutable, iIndexSegment + 1, 31 - iIndexSegment);
            objArrMakeMutable[iIndexSegment] = obj;
            return objArrMakeMutable;
        }
        Object[] objArrMakeMutable2 = makeMutable(objArr);
        int i3 = i - 5;
        objArrMakeMutable2[iIndexSegment] = insertIntoRoot$1((Object[]) objArrMakeMutable2[iIndexSegment], i3, i2, obj, objectRef);
        while (true) {
            iIndexSegment++;
            if (iIndexSegment >= 32 || (obj2 = objArrMakeMutable2[iIndexSegment]) == null) {
                break;
            }
            objArrMakeMutable2[iIndexSegment] = insertIntoRoot$1((Object[]) obj2, i3, 0, objectRef.value, objectRef);
        }
        return objArrMakeMutable2;
    }

    public final void insertIntoTail(Object obj, Object[] objArr, int i) {
        int iTailSize = tailSize();
        Object[] objArrMakeMutable = makeMutable(this.tail);
        if (iTailSize < 32) {
            ArraysKt___ArraysJvmKt.copyInto(this.tail, objArrMakeMutable, i + 1, i, iTailSize);
            objArrMakeMutable[i] = obj;
            this.root = objArr;
            this.tail = objArrMakeMutable;
            this.size++;
            return;
        }
        Object[] objArr2 = this.tail;
        Object obj2 = objArr2[31];
        ArraysKt___ArraysJvmKt.copyInto(objArr2, objArrMakeMutable, i + 1, i, 31);
        objArrMakeMutable[i] = obj;
        pushFilledTail(objArr, objArrMakeMutable, mutableBufferWith(obj2));
    }

    public final boolean isMutable(Object[] objArr) {
        return objArr.length == 33 && objArr[32] == this.ownership;
    }

    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.List
    public final Iterator iterator() {
        return listIterator(0);
    }

    public final AbstractListIterator leafBufferIterator(int i) {
        Object[] objArr = this.root;
        if (objArr == null) {
            throw new IllegalStateException("Invalid root");
        }
        int iRootSize$1 = rootSize$1() >> 5;
        ListImplementation.checkPositionIndex$runtime_release(i, iRootSize$1);
        int i2 = this.rootShift;
        return i2 == 0 ? new SingleElementListIterator(objArr, i) : new TrieIterator(objArr, i, iRootSize$1, i2 / 5);
    }

    @Override // java.util.AbstractList, java.util.List
    public final ListIterator listIterator(int i) {
        ListImplementation.checkPositionIndex$runtime_release(i, this.size);
        return new PersistentVectorMutableIterator(this, i);
    }

    public final Object[] makeMutable(Object[] objArr) {
        if (objArr == null) {
            return mutableBuffer();
        }
        if (isMutable(objArr)) {
            return objArr;
        }
        Object[] objArrMutableBuffer = mutableBuffer();
        int length = objArr.length;
        if (length > 32) {
            length = 32;
        }
        ArraysKt___ArraysJvmKt.copyInto$default(objArr, objArrMutableBuffer, 0, length, 6);
        return objArrMutableBuffer;
    }

    public final Object[] makeMutableShiftingRight(int i, Object[] objArr) {
        if (isMutable(objArr)) {
            System.arraycopy(objArr, 0, objArr, i, 32 - i);
            return objArr;
        }
        Object[] objArrMutableBuffer = mutableBuffer();
        System.arraycopy(objArr, 0, objArrMutableBuffer, i, 32 - i);
        return objArrMutableBuffer;
    }

    public final Object[] mutableBuffer() {
        Object[] objArr = new Object[33];
        objArr[32] = this.ownership;
        return objArr;
    }

    public final Object[] mutableBufferWith(Object obj) {
        Object[] objArr = new Object[33];
        objArr[0] = obj;
        objArr[32] = this.ownership;
        return objArr;
    }

    public final Object[] nullifyAfter(int i, int i2, Object[] objArr) {
        if (!(i2 >= 0)) {
            PreconditionsKt.throwIllegalArgumentException("shift should be positive");
        }
        if (i2 == 0) {
            return objArr;
        }
        int iIndexSegment = UtilsKt.indexSegment(i, i2);
        Object objNullifyAfter = nullifyAfter(i, i2 - 5, (Object[]) objArr[iIndexSegment]);
        if (iIndexSegment < 31) {
            int i3 = iIndexSegment + 1;
            if (objArr[i3] != null) {
                if (isMutable(objArr)) {
                    Arrays.fill(objArr, i3, 32, (Object) null);
                }
                Object[] objArrMutableBuffer = mutableBuffer();
                System.arraycopy(objArr, 0, objArrMutableBuffer, 0, i3);
                objArr = objArrMutableBuffer;
            }
        }
        if (objNullifyAfter == objArr[iIndexSegment]) {
            return objArr;
        }
        Object[] objArrMakeMutable = makeMutable(objArr);
        objArrMakeMutable[iIndexSegment] = objNullifyAfter;
        return objArrMakeMutable;
    }

    public final Object[] pullLastBuffer$1(Object[] objArr, int i, int i2, ObjectRef objectRef) {
        Object[] objArrPullLastBuffer$1;
        int iIndexSegment = UtilsKt.indexSegment(i2 - 1, i);
        if (i == 5) {
            objectRef.value = objArr[iIndexSegment];
            objArrPullLastBuffer$1 = null;
        } else {
            objArrPullLastBuffer$1 = pullLastBuffer$1((Object[]) objArr[iIndexSegment], i - 5, i2, objectRef);
        }
        if (objArrPullLastBuffer$1 == null && iIndexSegment == 0) {
            return null;
        }
        Object[] objArrMakeMutable = makeMutable(objArr);
        objArrMakeMutable[iIndexSegment] = objArrPullLastBuffer$1;
        return objArrMakeMutable;
    }

    public final void pullLastBufferFromRoot(int i, int i2, Object[] objArr) {
        if (i2 == 0) {
            this.root = null;
            if (objArr == null) {
                objArr = new Object[0];
            }
            this.tail = objArr;
            this.size = i;
            this.rootShift = i2;
            return;
        }
        ObjectRef objectRef = new ObjectRef(null);
        objArr.getClass();
        Object[] objArrPullLastBuffer$1 = pullLastBuffer$1(objArr, i2, i, objectRef);
        objArrPullLastBuffer$1.getClass();
        this.tail = (Object[]) objectRef.value;
        this.size = i;
        if (objArrPullLastBuffer$1[1] == null) {
            this.root = (Object[]) objArrPullLastBuffer$1[0];
            this.rootShift = i2 - 5;
        } else {
            this.root = objArrPullLastBuffer$1;
            this.rootShift = i2;
        }
    }

    public final Object[] pushBuffers(Object[] objArr, int i, int i2, Iterator it) {
        if (!it.hasNext()) {
            PreconditionsKt.throwIllegalArgumentException("invalid buffersIterator");
        }
        if (!(i2 >= 0)) {
            PreconditionsKt.throwIllegalArgumentException("negative shift");
        }
        if (i2 == 0) {
            return (Object[]) it.next();
        }
        Object[] objArrMakeMutable = makeMutable(objArr);
        int iIndexSegment = UtilsKt.indexSegment(i, i2);
        int i3 = i2 - 5;
        objArrMakeMutable[iIndexSegment] = pushBuffers((Object[]) objArrMakeMutable[iIndexSegment], i, i3, it);
        while (true) {
            iIndexSegment++;
            if (iIndexSegment >= 32 || !it.hasNext()) {
                break;
            }
            objArrMakeMutable[iIndexSegment] = pushBuffers((Object[]) objArrMakeMutable[iIndexSegment], 0, i3, it);
        }
        return objArrMakeMutable;
    }

    public final Object[] pushBuffersIncreasingHeightIfNeeded(Object[] objArr, int i, Object[][] objArr2) {
        ArrayIterator arrayIterator = new ArrayIterator(objArr2);
        int i2 = i >> 5;
        int i3 = this.rootShift;
        Object[] objArrPushBuffers = i2 < (1 << i3) ? pushBuffers(objArr, i, i3, arrayIterator) : makeMutable(objArr);
        while (arrayIterator.hasNext()) {
            this.rootShift += 5;
            objArrPushBuffers = mutableBufferWith(objArrPushBuffers);
            int i4 = this.rootShift;
            pushBuffers(objArrPushBuffers, 1 << i4, i4, arrayIterator);
        }
        return objArrPushBuffers;
    }

    public final void pushFilledTail(Object[] objArr, Object[] objArr2, Object[] objArr3) {
        int i = this.size;
        int i2 = i >> 5;
        int i3 = this.rootShift;
        if (i2 > (1 << i3)) {
            this.root = pushTail(this.rootShift + 5, mutableBufferWith(objArr), objArr2);
            this.tail = objArr3;
            this.rootShift += 5;
            this.size++;
            return;
        }
        if (objArr == null) {
            this.root = objArr2;
            this.tail = objArr3;
            this.size = i + 1;
        } else {
            this.root = pushTail(i3, objArr, objArr2);
            this.tail = objArr3;
            this.size++;
        }
    }

    public final Object[] pushTail(int i, Object[] objArr, Object[] objArr2) {
        int iIndexSegment = UtilsKt.indexSegment(getSize() - 1, i);
        Object[] objArrMakeMutable = makeMutable(objArr);
        if (i == 5) {
            objArrMakeMutable[iIndexSegment] = objArr2;
            return objArrMakeMutable;
        }
        objArrMakeMutable[iIndexSegment] = pushTail(i - 5, (Object[]) objArrMakeMutable[iIndexSegment], objArr2);
        return objArrMakeMutable;
    }

    /*  JADX ERROR: NullPointerException in pass: LoopRegionVisitor
        java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.SSAVar.use(jadx.core.dex.instructions.args.RegisterArg)" because "ssaVar" is null
        	at jadx.core.dex.nodes.InsnNode.rebindArgs(InsnNode.java:493)
        	at jadx.core.dex.nodes.InsnNode.rebindArgs(InsnNode.java:496)
        */
    public final int recyclableRemoveAll(kotlin.jvm.functions.Function1 r7, java.lang.Object[] r8, int r9, int r10, androidx.compose.runtime.external.kotlinx.collections.immutable.implementations.immutableList.ObjectRef r11, java.util.List r12, java.util.List r13) {
        /*
            r6 = this;
            boolean r0 = r6.isMutable(r8)
            if (r0 == 0) goto Lc
            r0 = r12
            java.util.ArrayList r0 = (java.util.ArrayList) r0
            r0.add(r8)
        Lc:
            java.lang.Object r0 = r11.value
            java.lang.Object[] r0 = (java.lang.Object[]) r0
            r1 = 0
            r3 = r0
            r2 = r1
        L13:
            if (r2 >= r9) goto L4c
            r4 = r8[r2]
            java.lang.Object r5 = r7.mo781invoke(r4)
            java.lang.Boolean r5 = (java.lang.Boolean) r5
            boolean r5 = r5.booleanValue()
            if (r5 != 0) goto L49
            r5 = 32
            if (r10 != r5) goto L44
            boolean r10 = r12.isEmpty()
            if (r10 != 0) goto L3e
            r10 = r12
            java.util.ArrayList r10 = (java.util.ArrayList) r10
            int r3 = r10.size()
            int r3 = r3 + (-1)
            java.lang.Object r10 = r10.remove(r3)
            java.lang.Object[] r10 = (java.lang.Object[]) r10
        L3c:
            r3 = r10
            goto L43
        L3e:
            java.lang.Object[] r10 = r6.mutableBuffer()
            goto L3c
        L43:
            r10 = r1
        L44:
            int r5 = r10 + 1
            r3[r10] = r4
            r10 = r5
        L49:
            int r2 = r2 + 1
            goto L13
        L4c:
            r11.value = r3
            if (r0 == r3) goto L55
            java.util.ArrayList r13 = (java.util.ArrayList) r13
            r13.add(r0)
        L55:
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.runtime.external.kotlinx.collections.immutable.implementations.immutableList.PersistentVectorBuilder.recyclableRemoveAll(kotlin.jvm.functions.Function1, java.lang.Object[], int, int, androidx.compose.runtime.external.kotlinx.collections.immutable.implementations.immutableList.ObjectRef, java.util.List, java.util.List):int");
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean removeAll(final Collection collection) {
        return removeAllWithPredicate(new Function1() { // from class: androidx.compose.runtime.external.kotlinx.collections.immutable.implementations.immutableList.PersistentVectorBuilder.removeAll.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                return Boolean.valueOf(collection.contains(obj));
            }
        });
    }

    public final boolean removeAllWithPredicate(Function1 function1) {
        Object[] objArrPushBuffers;
        int i;
        Function1 function12 = function1;
        int iTailSize = tailSize();
        Object[] objArrNullifyAfter = null;
        ObjectRef objectRef = new ObjectRef(null);
        boolean z = false;
        if (this.root == null) {
            int iRemoveAll = removeAll(function12, this.tail, iTailSize, objectRef);
            if (iRemoveAll == iTailSize) {
                iRemoveAll = iTailSize;
            } else {
                Object[] objArr = (Object[]) objectRef.value;
                Arrays.fill(objArr, iRemoveAll, iTailSize, (Object) null);
                this.tail = objArr;
                this.size -= iTailSize - iRemoveAll;
            }
            if (iRemoveAll != iTailSize) {
                z = true;
            }
        } else {
            AbstractListIterator abstractListIteratorLeafBufferIterator = leafBufferIterator(0);
            int iRemoveAll2 = 32;
            while (iRemoveAll2 == 32 && abstractListIteratorLeafBufferIterator.hasNext()) {
                iRemoveAll2 = removeAll(function12, (Object[]) abstractListIteratorLeafBufferIterator.next(), 32, objectRef);
            }
            if (iRemoveAll2 == 32) {
                abstractListIteratorLeafBufferIterator.hasNext();
                int iRemoveAll3 = removeAll(function12, this.tail, iTailSize, objectRef);
                if (iRemoveAll3 == iTailSize) {
                    iRemoveAll3 = iTailSize;
                } else {
                    Object[] objArr2 = (Object[]) objectRef.value;
                    Arrays.fill(objArr2, iRemoveAll3, iTailSize, (Object) null);
                    this.tail = objArr2;
                    this.size -= iTailSize - iRemoveAll3;
                }
                if (iRemoveAll3 == 0) {
                    pullLastBufferFromRoot(this.size, this.rootShift, this.root);
                }
                if (iRemoveAll3 != iTailSize) {
                }
            } else {
                int iPreviousIndex = abstractListIteratorLeafBufferIterator.previousIndex() << 5;
                ArrayList arrayList = new ArrayList();
                ArrayList arrayList2 = new ArrayList();
                int iRecyclableRemoveAll = iRemoveAll2;
                while (abstractListIteratorLeafBufferIterator.hasNext()) {
                    iRecyclableRemoveAll = recyclableRemoveAll(function12, (Object[]) abstractListIteratorLeafBufferIterator.next(), 32, iRecyclableRemoveAll, objectRef, arrayList2, arrayList);
                    function12 = function1;
                }
                int iRecyclableRemoveAll2 = recyclableRemoveAll(function1, this.tail, iTailSize, iRecyclableRemoveAll, objectRef, arrayList2, arrayList);
                Object[] objArr3 = (Object[]) objectRef.value;
                Arrays.fill(objArr3, iRecyclableRemoveAll2, 32, (Object) null);
                if (arrayList.isEmpty()) {
                    objArrPushBuffers = this.root;
                    objArrPushBuffers.getClass();
                } else {
                    objArrPushBuffers = pushBuffers(this.root, iPreviousIndex, this.rootShift, arrayList.iterator());
                }
                int size = iPreviousIndex + (arrayList.size() << 5);
                if ((size & 31) != 0) {
                    PreconditionsKt.throwIllegalArgumentException("invalid size");
                }
                if (size == 0) {
                    this.rootShift = 0;
                } else {
                    int i2 = size - 1;
                    while (true) {
                        i = this.rootShift;
                        if ((i2 >> i) != 0) {
                            break;
                        }
                        this.rootShift = i - 5;
                        objArrPushBuffers = objArrPushBuffers[0];
                    }
                    objArrNullifyAfter = nullifyAfter(i2, i, objArrPushBuffers);
                }
                this.root = objArrNullifyAfter;
                this.tail = objArr3;
                this.size = size + iRecyclableRemoveAll2;
            }
            z = true;
        }
        if (z) {
            ((AbstractList) this).modCount++;
        }
        return z;
    }

    @Override // kotlin.collections.AbstractMutableList
    public final Object removeAt(int i) {
        ListImplementation.checkElementIndex$runtime_release(i, getSize());
        ((AbstractList) this).modCount++;
        int iRootSize$1 = rootSize$1();
        if (i >= iRootSize$1) {
            return removeFromTailAt(this.root, iRootSize$1, this.rootShift, i - iRootSize$1);
        }
        ObjectRef objectRef = new ObjectRef(this.tail[0]);
        Object[] objArr = this.root;
        objArr.getClass();
        removeFromTailAt(removeFromRootAt$1(objArr, this.rootShift, i, objectRef), iRootSize$1, this.rootShift, 0);
        return objectRef.value;
    }

    public final Object[] removeFromRootAt$1(Object[] objArr, int i, int i2, ObjectRef objectRef) {
        int iIndexSegment = UtilsKt.indexSegment(i2, i);
        if (i == 0) {
            Object obj = objArr[iIndexSegment];
            Object[] objArrMakeMutable = makeMutable(objArr);
            int i3 = iIndexSegment + 1;
            System.arraycopy(objArr, i3, objArrMakeMutable, iIndexSegment, 32 - i3);
            objArrMakeMutable[31] = objectRef.value;
            objectRef.value = obj;
            return objArrMakeMutable;
        }
        int iIndexSegment2 = objArr[31] == null ? UtilsKt.indexSegment(rootSize$1() - 1, i) : 31;
        Object[] objArrMakeMutable2 = makeMutable(objArr);
        int i4 = i - 5;
        int i5 = iIndexSegment + 1;
        if (i5 <= iIndexSegment2) {
            while (true) {
                objArrMakeMutable2[iIndexSegment2] = removeFromRootAt$1((Object[]) objArrMakeMutable2[iIndexSegment2], i4, 0, objectRef);
                if (iIndexSegment2 == i5) {
                    break;
                }
                iIndexSegment2--;
            }
        }
        objArrMakeMutable2[iIndexSegment] = removeFromRootAt$1((Object[]) objArrMakeMutable2[iIndexSegment], i4, i2, objectRef);
        return objArrMakeMutable2;
    }

    public final Object removeFromTailAt(Object[] objArr, int i, int i2, int i3) {
        int size = getSize() - i;
        if (size == 1) {
            Object obj = this.tail[0];
            pullLastBufferFromRoot(i, i2, objArr);
            return obj;
        }
        Object[] objArr2 = this.tail;
        Object obj2 = objArr2[i3];
        Object[] objArrMakeMutable = makeMutable(objArr2);
        int i4 = i3 + 1;
        System.arraycopy(objArr2, i4, objArrMakeMutable, i3, size - i4);
        objArrMakeMutable[size - 1] = null;
        this.root = objArr;
        this.tail = objArrMakeMutable;
        this.size = (i + size) - 1;
        this.rootShift = i2;
        return obj2;
    }

    public final int rootSize$1() {
        int i = this.size;
        if (i <= 32) {
            return 0;
        }
        return (i - 1) & (-32);
    }

    @Override // java.util.AbstractList, java.util.List
    public final Object set(int i, Object obj) {
        ListImplementation.checkElementIndex$runtime_release(i, getSize());
        if (rootSize$1() > i) {
            ObjectRef objectRef = new ObjectRef(null);
            Object[] objArr = this.root;
            objArr.getClass();
            this.root = setInRoot(objArr, this.rootShift, i, obj, objectRef);
            return objectRef.value;
        }
        Object[] objArrMakeMutable = makeMutable(this.tail);
        if (objArrMakeMutable != this.tail) {
            ((AbstractList) this).modCount++;
        }
        int i2 = i & 31;
        Object obj2 = objArrMakeMutable[i2];
        objArrMakeMutable[i2] = obj;
        this.tail = objArrMakeMutable;
        return obj2;
    }

    public final Object[] setInRoot(Object[] objArr, int i, int i2, Object obj, ObjectRef objectRef) {
        int iIndexSegment = UtilsKt.indexSegment(i2, i);
        Object[] objArrMakeMutable = makeMutable(objArr);
        if (i != 0) {
            objArrMakeMutable[iIndexSegment] = setInRoot((Object[]) objArrMakeMutable[iIndexSegment], i - 5, i2, obj, objectRef);
            return objArrMakeMutable;
        }
        if (objArrMakeMutable != objArr) {
            ((AbstractList) this).modCount++;
        }
        objectRef.value = objArrMakeMutable[iIndexSegment];
        objArrMakeMutable[iIndexSegment] = obj;
        return objArrMakeMutable;
    }

    public final void splitToBuffers(Collection collection, int i, Object[] objArr, int i2, Object[][] objArr2, int i3, Object[] objArr3) {
        Object[] objArrMutableBuffer;
        if (i3 < 1) {
            PreconditionsKt.throwIllegalArgumentException("requires at least one nullBuffer");
        }
        Object[] objArrMakeMutable = makeMutable(objArr);
        objArr2[0] = objArrMakeMutable;
        int i4 = i & 31;
        int size = ((collection.size() + i) - 1) & 31;
        int i5 = (i2 - i4) + size;
        if (i5 < 32) {
            ArraysKt___ArraysJvmKt.copyInto(objArrMakeMutable, objArr3, size + 1, i4, i2);
        } else {
            int i6 = i5 - 31;
            if (i3 == 1) {
                objArrMutableBuffer = objArrMakeMutable;
            } else {
                objArrMutableBuffer = mutableBuffer();
                i3--;
                objArr2[i3] = objArrMutableBuffer;
            }
            int i7 = i2 - i6;
            ArraysKt___ArraysJvmKt.copyInto(objArrMakeMutable, objArr3, 0, i7, i2);
            ArraysKt___ArraysJvmKt.copyInto(objArrMakeMutable, objArrMutableBuffer, size + 1, i4, i7);
            objArr3 = objArrMutableBuffer;
        }
        Iterator<E> it = collection.iterator();
        copyToBuffer(objArrMakeMutable, i4, it);
        for (int i8 = 1; i8 < i3; i8++) {
            Object[] objArrMutableBuffer2 = mutableBuffer();
            copyToBuffer(objArrMutableBuffer2, 0, it);
            objArr2[i8] = objArrMutableBuffer2;
        }
        copyToBuffer(objArr3, 0, it);
    }

    public final int tailSize() {
        int i = this.size;
        return i <= 32 ? i : i - ((i - 1) & (-32));
    }

    public final int removeAll(Function1 function1, Object[] objArr, int i, ObjectRef objectRef) {
        Object[] objArrMakeMutable = objArr;
        int i2 = i;
        boolean z = false;
        for (int i3 = 0; i3 < i; i3++) {
            Object obj = objArr[i3];
            if (((Boolean) function1.mo781invoke(obj)).booleanValue()) {
                if (!z) {
                    objArrMakeMutable = makeMutable(objArr);
                    z = true;
                    i2 = i3;
                }
            } else if (z) {
                objArrMakeMutable[i2] = obj;
                i2++;
            }
        }
        objectRef.value = objArrMakeMutable;
        return i2;
    }

    @Override // java.util.AbstractList, java.util.List
    public final ListIterator listIterator() {
        return listIterator(0);
    }

    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean add(Object obj) {
        ((AbstractList) this).modCount++;
        int iTailSize = tailSize();
        if (iTailSize < 32) {
            Object[] objArrMakeMutable = makeMutable(this.tail);
            objArrMakeMutable[iTailSize] = obj;
            this.tail = objArrMakeMutable;
            this.size = getSize() + 1;
        } else {
            pushFilledTail(this.root, this.tail, mutableBufferWith(obj));
        }
        return true;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean addAll(Collection collection) {
        if (collection.isEmpty()) {
            return false;
        }
        ((AbstractList) this).modCount++;
        int iTailSize = tailSize();
        Iterator<E> it = collection.iterator();
        if (32 - iTailSize >= collection.size()) {
            Object[] objArrMakeMutable = makeMutable(this.tail);
            copyToBuffer(objArrMakeMutable, iTailSize, it);
            this.tail = objArrMakeMutable;
            this.size = collection.size() + this.size;
            return true;
        }
        int size = ((collection.size() + iTailSize) - 1) / 32;
        Object[][] objArr = new Object[size][];
        Object[] objArrMakeMutable2 = makeMutable(this.tail);
        copyToBuffer(objArrMakeMutable2, iTailSize, it);
        objArr[0] = objArrMakeMutable2;
        for (int i = 1; i < size; i++) {
            Object[] objArrMutableBuffer = mutableBuffer();
            copyToBuffer(objArrMutableBuffer, 0, it);
            objArr[i] = objArrMutableBuffer;
        }
        this.root = pushBuffersIncreasingHeightIfNeeded(this.root, rootSize$1(), objArr);
        Object[] objArrMutableBuffer2 = mutableBuffer();
        copyToBuffer(objArrMutableBuffer2, 0, it);
        this.tail = objArrMutableBuffer2;
        this.size = collection.size() + this.size;
        return true;
    }
}
