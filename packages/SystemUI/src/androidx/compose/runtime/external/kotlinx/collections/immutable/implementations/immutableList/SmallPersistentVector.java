package androidx.compose.runtime.external.kotlinx.collections.immutable.implementations.immutableList;

import androidx.compose.runtime.external.kotlinx.collections.immutable.ImmutableList;
import androidx.compose.runtime.external.kotlinx.collections.immutable.PersistentList;
import androidx.compose.runtime.external.kotlinx.collections.immutable.implementations.immutableList.AbstractPersistentList;
import androidx.compose.runtime.external.kotlinx.collections.immutable.internal.ListImplementation;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;
import kotlin.collections.ArraysKt__ArraysJVMKt;
import kotlin.collections.ArraysKt___ArraysJvmKt;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class SmallPersistentVector<E> extends AbstractPersistentList<E> implements ImmutableList<E> {
    public static final Companion Companion = new Companion(null);
    public static final SmallPersistentVector EMPTY = new SmallPersistentVector(new Object[0]);
    public final Object[] buffer;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public SmallPersistentVector(Object[] objArr) {
        this.buffer = objArr;
        int length = objArr.length;
    }

    @Override // java.util.List, androidx.compose.runtime.external.kotlinx.collections.immutable.PersistentList
    public final PersistentList add(int i, Object obj) {
        ListImplementation.checkPositionIndex$runtime_release(i, this.buffer.length);
        Object[] objArr = this.buffer;
        if (i == objArr.length) {
            return add(obj);
        }
        if (objArr.length < 32) {
            Object[] objArr2 = new Object[objArr.length + 1];
            ArraysKt___ArraysJvmKt.copyInto$default(objArr, objArr2, 0, i, 6);
            Object[] objArr3 = this.buffer;
            ArraysKt___ArraysJvmKt.copyInto(objArr3, objArr2, i + 1, i, objArr3.length);
            objArr2[i] = obj;
            return new SmallPersistentVector(objArr2);
        }
        Object[] objArrCopyOf = Arrays.copyOf(objArr, objArr.length);
        ArraysKt___ArraysJvmKt.copyInto(this.buffer, objArrCopyOf, i + 1, i, r1.length - 1);
        objArrCopyOf[i] = obj;
        Object[] objArr4 = this.buffer;
        Object[] objArr5 = new Object[32];
        objArr5[0] = objArr4[31];
        return new PersistentVector(objArrCopyOf, objArr5, objArr4.length + 1, 0);
    }

    @Override // androidx.compose.runtime.external.kotlinx.collections.immutable.implementations.immutableList.AbstractPersistentList, java.util.Collection, java.util.List, androidx.compose.runtime.external.kotlinx.collections.immutable.PersistentList
    public final PersistentList addAll(Collection collection) {
        if (collection.size() + this.buffer.length > 32) {
            PersistentVectorBuilder persistentVectorBuilderBuilder = builder();
            persistentVectorBuilderBuilder.addAll(collection);
            return persistentVectorBuilderBuilder.build();
        }
        Object[] objArr = this.buffer;
        Object[] objArrCopyOf = Arrays.copyOf(objArr, collection.size() + objArr.length);
        int length = this.buffer.length;
        Iterator<E> it = collection.iterator();
        while (it.hasNext()) {
            objArrCopyOf[length] = it.next();
            length++;
        }
        return new SmallPersistentVector(objArrCopyOf);
    }

    @Override // androidx.compose.runtime.external.kotlinx.collections.immutable.PersistentList
    public final PersistentVectorBuilder builder() {
        return new PersistentVectorBuilder(this, null, this.buffer, 0);
    }

    @Override // java.util.List
    public final Object get(int i) {
        ListImplementation.checkElementIndex$runtime_release(i, getSize());
        return this.buffer[i];
    }

    @Override // kotlin.collections.AbstractCollection
    public final int getSize() {
        return this.buffer.length;
    }

    @Override // kotlin.collections.AbstractList, java.util.List
    public final int indexOf(Object obj) {
        return ArraysKt___ArraysKt.indexOf(this.buffer, obj);
    }

    @Override // kotlin.collections.AbstractList, java.util.List
    public final int lastIndexOf(Object obj) {
        Object[] objArr = this.buffer;
        if (obj == null) {
            int length = objArr.length - 1;
            if (length >= 0) {
                while (true) {
                    int i = length - 1;
                    if (objArr[length] == null) {
                        return length;
                    }
                    if (i < 0) {
                        break;
                    }
                    length = i;
                }
            }
        } else {
            int length2 = objArr.length - 1;
            if (length2 >= 0) {
                while (true) {
                    int i2 = length2 - 1;
                    if (obj.equals(objArr[length2])) {
                        return length2;
                    }
                    if (i2 < 0) {
                        break;
                    }
                    length2 = i2;
                }
            }
        }
        return -1;
    }

    @Override // kotlin.collections.AbstractList, java.util.List
    public final ListIterator listIterator(int i) {
        ListImplementation.checkPositionIndex$runtime_release(i, this.buffer.length);
        Object[] objArr = this.buffer;
        return new BufferIterator(objArr, i, objArr.length);
    }

    @Override // androidx.compose.runtime.external.kotlinx.collections.immutable.PersistentList
    public final PersistentList removeAll(Function1 function1) {
        Object[] objArrCopyOf = this.buffer;
        int length = objArrCopyOf.length;
        int length2 = objArrCopyOf.length;
        boolean z = false;
        for (int i = 0; i < length2; i++) {
            Object obj = this.buffer[i];
            if (((Boolean) ((AbstractPersistentList.AnonymousClass1) function1).mo781invoke(obj)).booleanValue()) {
                if (!z) {
                    Object[] objArr = this.buffer;
                    objArrCopyOf = Arrays.copyOf(objArr, objArr.length);
                    z = true;
                    length = i;
                }
            } else if (z) {
                objArrCopyOf[length] = obj;
                length++;
            }
        }
        if (length == this.buffer.length) {
            return this;
        }
        if (length == 0) {
            return EMPTY;
        }
        ArraysKt__ArraysJVMKt.copyOfRangeToIndexCheck(length, objArrCopyOf.length);
        return new SmallPersistentVector(Arrays.copyOfRange(objArrCopyOf, 0, length));
    }

    @Override // androidx.compose.runtime.external.kotlinx.collections.immutable.PersistentList
    public final PersistentList removeAt(int i) {
        ListImplementation.checkElementIndex$runtime_release(i, this.buffer.length);
        Object[] objArr = this.buffer;
        if (objArr.length == 1) {
            return EMPTY;
        }
        Object[] objArrCopyOf = Arrays.copyOf(objArr, objArr.length - 1);
        Object[] objArr2 = this.buffer;
        ArraysKt___ArraysJvmKt.copyInto(objArr2, objArrCopyOf, i, i + 1, objArr2.length);
        return new SmallPersistentVector(objArrCopyOf);
    }

    @Override // kotlin.collections.AbstractList, java.util.List, androidx.compose.runtime.external.kotlinx.collections.immutable.PersistentList
    public final PersistentList set(int i, Object obj) {
        ListImplementation.checkElementIndex$runtime_release(i, getSize());
        Object[] objArr = this.buffer;
        Object[] objArrCopyOf = Arrays.copyOf(objArr, objArr.length);
        objArrCopyOf[i] = obj;
        return new SmallPersistentVector(objArrCopyOf);
    }

    @Override // java.util.Collection, java.util.List, androidx.compose.runtime.external.kotlinx.collections.immutable.PersistentList
    public final PersistentList add(Object obj) {
        Object[] objArr = this.buffer;
        if (objArr.length < 32) {
            Object[] objArrCopyOf = Arrays.copyOf(objArr, objArr.length + 1);
            objArrCopyOf[this.buffer.length] = obj;
            return new SmallPersistentVector(objArrCopyOf);
        }
        Object[] objArr2 = new Object[32];
        objArr2[0] = obj;
        return new PersistentVector(objArr, objArr2, objArr.length + 1, 0);
    }
}
