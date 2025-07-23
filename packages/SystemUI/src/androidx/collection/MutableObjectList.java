package androidx.collection;

import androidx.collection.internal.RuntimeHelpersKt;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import kotlin.collections.ArraysKt___ArraysJvmKt;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.internal.CollectionToArray;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;
import kotlin.jvm.internal.markers.KMutableList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class MutableObjectList extends ObjectList {
    public ObjectListMutableList list;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class MutableObjectListIterator implements ListIterator, KMappedMarker {
        public final List list;
        public int prevIndex;

        public MutableObjectListIterator(List<Object> list, int i) {
            this.list = list;
            this.prevIndex = i - 1;
        }

        @Override // java.util.ListIterator
        public final void add(Object obj) {
            List list = this.list;
            int i = this.prevIndex + 1;
            this.prevIndex = i;
            list.add(i, obj);
        }

        @Override // java.util.ListIterator, java.util.Iterator
        public final boolean hasNext() {
            return this.prevIndex < this.list.size() - 1;
        }

        @Override // java.util.ListIterator
        public final boolean hasPrevious() {
            return this.prevIndex >= 0;
        }

        @Override // java.util.ListIterator, java.util.Iterator
        public final Object next() {
            List list = this.list;
            int i = this.prevIndex + 1;
            this.prevIndex = i;
            return list.get(i);
        }

        @Override // java.util.ListIterator
        public final int nextIndex() {
            return this.prevIndex + 1;
        }

        @Override // java.util.ListIterator
        public final Object previous() {
            List list = this.list;
            int i = this.prevIndex;
            this.prevIndex = i - 1;
            return list.get(i);
        }

        @Override // java.util.ListIterator
        public final int previousIndex() {
            return this.prevIndex;
        }

        @Override // java.util.ListIterator, java.util.Iterator
        public final void remove() {
            this.list.remove(this.prevIndex);
            this.prevIndex--;
        }

        @Override // java.util.ListIterator
        public final void set(Object obj) {
            this.list.set(this.prevIndex, obj);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class ObjectListMutableList implements List, KMutableList {
        public final MutableObjectList objectList;

        public ObjectListMutableList(MutableObjectList mutableObjectList) {
            this.objectList = mutableObjectList;
        }

        @Override // java.util.List, java.util.Collection
        public final boolean add(Object obj) {
            this.objectList.add(obj);
            return true;
        }

        @Override // java.util.List
        public final boolean addAll(int i, Collection collection) {
            MutableObjectList mutableObjectList = this.objectList;
            if (i < 0 || i > mutableObjectList._size) {
                mutableObjectList.getClass();
                RuntimeHelpersKt.throwIndexOutOfBoundsException("Index " + i + " must be in 0.." + mutableObjectList._size);
                throw null;
            }
            int i2 = 0;
            if (collection.isEmpty()) {
                return false;
            }
            int size = collection.size() + mutableObjectList._size;
            Object[] objArr = mutableObjectList.content;
            if (objArr.length < size) {
                mutableObjectList.resizeStorage(size, objArr);
            }
            Object[] objArr2 = mutableObjectList.content;
            if (i != mutableObjectList._size) {
                ArraysKt___ArraysJvmKt.copyInto(objArr2, objArr2, collection.size() + i, i, mutableObjectList._size);
            }
            for (Object obj : collection) {
                int i3 = i2 + 1;
                if (i2 < 0) {
                    CollectionsKt__CollectionsKt.throwIndexOverflow();
                    throw null;
                }
                objArr2[i2 + i] = obj;
                i2 = i3;
            }
            mutableObjectList._size = collection.size() + mutableObjectList._size;
            return true;
        }

        @Override // java.util.List, java.util.Collection
        public final void clear() {
            this.objectList.clear();
        }

        @Override // java.util.List, java.util.Collection
        public final boolean contains(Object obj) {
            return this.objectList.indexOf(obj) >= 0;
        }

        @Override // java.util.List, java.util.Collection
        public final boolean containsAll(Collection collection) {
            MutableObjectList mutableObjectList = this.objectList;
            mutableObjectList.getClass();
            Iterator it = collection.iterator();
            while (it.hasNext()) {
                if (mutableObjectList.indexOf(it.next()) < 0) {
                    return false;
                }
            }
            return true;
        }

        @Override // java.util.List
        public final Object get(int i) {
            ObjectListKt.access$checkIndex(i, this);
            return this.objectList.get(i);
        }

        @Override // java.util.List
        public final int indexOf(Object obj) {
            return this.objectList.indexOf(obj);
        }

        @Override // java.util.List, java.util.Collection
        public final boolean isEmpty() {
            return this.objectList.isEmpty();
        }

        @Override // java.util.List, java.util.Collection, java.lang.Iterable
        public final Iterator iterator() {
            return new MutableObjectListIterator(this, 0);
        }

        @Override // java.util.List
        public final int lastIndexOf(Object obj) {
            MutableObjectList mutableObjectList = this.objectList;
            if (obj == null) {
                Object[] objArr = mutableObjectList.content;
                for (int i = mutableObjectList._size - 1; -1 < i; i--) {
                    if (objArr[i] == null) {
                        return i;
                    }
                }
            } else {
                Object[] objArr2 = mutableObjectList.content;
                for (int i2 = mutableObjectList._size - 1; -1 < i2; i2--) {
                    if (obj.equals(objArr2[i2])) {
                        return i2;
                    }
                }
            }
            return -1;
        }

        @Override // java.util.List
        public final ListIterator listIterator() {
            return new MutableObjectListIterator(this, 0);
        }

        @Override // java.util.List, java.util.Collection
        public final boolean remove(Object obj) {
            return this.objectList.remove(obj);
        }

        @Override // java.util.List, java.util.Collection
        public final boolean removeAll(Collection collection) {
            MutableObjectList mutableObjectList = this.objectList;
            int i = mutableObjectList._size;
            Iterator it = collection.iterator();
            while (it.hasNext()) {
                mutableObjectList.remove(it.next());
            }
            return i != mutableObjectList._size;
        }

        @Override // java.util.List, java.util.Collection
        public final boolean retainAll(Collection collection) {
            MutableObjectList mutableObjectList = this.objectList;
            int i = mutableObjectList._size;
            Object[] objArr = mutableObjectList.content;
            for (int i2 = i - 1; -1 < i2; i2--) {
                if (!collection.contains(objArr[i2])) {
                    mutableObjectList.removeAt(i2);
                }
            }
            return i != mutableObjectList._size;
        }

        @Override // java.util.List
        public final Object set(int i, Object obj) {
            ObjectListKt.access$checkIndex(i, this);
            MutableObjectList mutableObjectList = this.objectList;
            if (i < 0 || i >= mutableObjectList._size) {
                mutableObjectList.throwIndexOutOfBoundsExclusiveException$collection(i);
                throw null;
            }
            Object[] objArr = mutableObjectList.content;
            Object obj2 = objArr[i];
            objArr[i] = obj;
            return obj2;
        }

        @Override // java.util.List, java.util.Collection
        public final int size() {
            return this.objectList._size;
        }

        @Override // java.util.List
        public final List subList(int i, int i2) {
            ObjectListKt.access$checkSubIndex(this, i, i2);
            return new SubList(this, i, i2);
        }

        @Override // java.util.List, java.util.Collection
        public final Object[] toArray() {
            return CollectionToArray.toArray(this);
        }

        @Override // java.util.List
        public final void add(int i, Object obj) {
            int i2;
            MutableObjectList mutableObjectList = this.objectList;
            if (i < 0 || i > (i2 = mutableObjectList._size)) {
                mutableObjectList.getClass();
                RuntimeHelpersKt.throwIndexOutOfBoundsException("Index " + i + " must be in 0.." + mutableObjectList._size);
                throw null;
            }
            int i3 = i2 + 1;
            Object[] objArr = mutableObjectList.content;
            if (objArr.length < i3) {
                mutableObjectList.resizeStorage(i3, objArr);
            }
            Object[] objArr2 = mutableObjectList.content;
            int i4 = mutableObjectList._size;
            if (i != i4) {
                ArraysKt___ArraysJvmKt.copyInto(objArr2, objArr2, i + 1, i, i4);
            }
            objArr2[i] = obj;
            mutableObjectList._size++;
        }

        @Override // java.util.List
        public final ListIterator listIterator(int i) {
            return new MutableObjectListIterator(this, i);
        }

        @Override // java.util.List
        public final Object remove(int i) {
            ObjectListKt.access$checkIndex(i, this);
            return this.objectList.removeAt(i);
        }

        @Override // java.util.List, java.util.Collection
        public final Object[] toArray(Object[] objArr) {
            return CollectionToArray.toArray(this, objArr);
        }

        @Override // java.util.List, java.util.Collection
        public final boolean addAll(Collection collection) {
            MutableObjectList mutableObjectList = this.objectList;
            int i = mutableObjectList._size;
            Iterator it = collection.iterator();
            while (it.hasNext()) {
                mutableObjectList.add(it.next());
            }
            return i != mutableObjectList._size;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class SubList implements List, KMutableList {
        public int end;
        public final List list;
        public final int start;

        public SubList(List<Object> list, int i, int i2) {
            this.list = list;
            this.start = i;
            this.end = i2;
        }

        @Override // java.util.List, java.util.Collection
        public final boolean add(Object obj) {
            List list = this.list;
            int i = this.end;
            this.end = i + 1;
            list.add(i, obj);
            return true;
        }

        @Override // java.util.List
        public final boolean addAll(int i, Collection collection) {
            this.list.addAll(i + this.start, collection);
            this.end = collection.size() + this.end;
            return collection.size() > 0;
        }

        @Override // java.util.List, java.util.Collection
        public final void clear() {
            int i = this.end - 1;
            int i2 = this.start;
            if (i2 <= i) {
                while (true) {
                    this.list.remove(i);
                    if (i == i2) {
                        break;
                    } else {
                        i--;
                    }
                }
            }
            this.end = this.start;
        }

        @Override // java.util.List, java.util.Collection
        public final boolean contains(Object obj) {
            int i = this.end;
            for (int i2 = this.start; i2 < i; i2++) {
                if (Intrinsics.areEqual(this.list.get(i2), obj)) {
                    return true;
                }
            }
            return false;
        }

        @Override // java.util.List, java.util.Collection
        public final boolean containsAll(Collection collection) {
            Iterator it = collection.iterator();
            while (it.hasNext()) {
                if (!contains(it.next())) {
                    return false;
                }
            }
            return true;
        }

        @Override // java.util.List
        public final Object get(int i) {
            ObjectListKt.access$checkIndex(i, this);
            return this.list.get(i + this.start);
        }

        @Override // java.util.List
        public final int indexOf(Object obj) {
            int i = this.end;
            for (int i2 = this.start; i2 < i; i2++) {
                if (Intrinsics.areEqual(this.list.get(i2), obj)) {
                    return i2 - this.start;
                }
            }
            return -1;
        }

        @Override // java.util.List, java.util.Collection
        public final boolean isEmpty() {
            return this.end == this.start;
        }

        @Override // java.util.List, java.util.Collection, java.lang.Iterable
        public final Iterator iterator() {
            return new MutableObjectListIterator(this, 0);
        }

        @Override // java.util.List
        public final int lastIndexOf(Object obj) {
            int i = this.end - 1;
            int i2 = this.start;
            if (i2 > i) {
                return -1;
            }
            while (!Intrinsics.areEqual(this.list.get(i), obj)) {
                if (i == i2) {
                    return -1;
                }
                i--;
            }
            return i - this.start;
        }

        @Override // java.util.List
        public final ListIterator listIterator() {
            return new MutableObjectListIterator(this, 0);
        }

        @Override // java.util.List, java.util.Collection
        public final boolean remove(Object obj) {
            int i = this.end;
            for (int i2 = this.start; i2 < i; i2++) {
                if (Intrinsics.areEqual(this.list.get(i2), obj)) {
                    this.list.remove(i2);
                    this.end--;
                    return true;
                }
            }
            return false;
        }

        @Override // java.util.List, java.util.Collection
        public final boolean removeAll(Collection collection) {
            int i = this.end;
            Iterator it = collection.iterator();
            while (it.hasNext()) {
                remove(it.next());
            }
            return i != this.end;
        }

        @Override // java.util.List, java.util.Collection
        public final boolean retainAll(Collection collection) {
            int i = this.end;
            int i2 = i - 1;
            int i3 = this.start;
            if (i3 <= i2) {
                while (true) {
                    if (!collection.contains(this.list.get(i2))) {
                        this.list.remove(i2);
                        this.end--;
                    }
                    if (i2 == i3) {
                        break;
                    }
                    i2--;
                }
            }
            return i != this.end;
        }

        @Override // java.util.List
        public final Object set(int i, Object obj) {
            ObjectListKt.access$checkIndex(i, this);
            return this.list.set(i + this.start, obj);
        }

        @Override // java.util.List, java.util.Collection
        public final int size() {
            return this.end - this.start;
        }

        @Override // java.util.List
        public final List subList(int i, int i2) {
            ObjectListKt.access$checkSubIndex(this, i, i2);
            return new SubList(this, i, i2);
        }

        @Override // java.util.List, java.util.Collection
        public final Object[] toArray() {
            return CollectionToArray.toArray(this);
        }

        @Override // java.util.List
        public final void add(int i, Object obj) {
            this.list.add(i + this.start, obj);
            this.end++;
        }

        @Override // java.util.List
        public final ListIterator listIterator(int i) {
            return new MutableObjectListIterator(this, i);
        }

        @Override // java.util.List, java.util.Collection
        public final Object[] toArray(Object[] objArr) {
            return CollectionToArray.toArray(this, objArr);
        }

        @Override // java.util.List, java.util.Collection
        public final boolean addAll(Collection collection) {
            this.list.addAll(this.end, collection);
            this.end = collection.size() + this.end;
            return collection.size() > 0;
        }

        @Override // java.util.List
        public final Object remove(int i) {
            ObjectListKt.access$checkIndex(i, this);
            this.end--;
            return this.list.remove(i + this.start);
        }
    }

    public MutableObjectList() {
        this(0, 1, null);
    }

    public final void add(Object obj) {
        int i = this._size + 1;
        Object[] objArr = this.content;
        if (objArr.length < i) {
            resizeStorage(i, objArr);
        }
        Object[] objArr2 = this.content;
        int i2 = this._size;
        objArr2[i2] = obj;
        this._size = i2 + 1;
    }

    public final void clear() {
        Arrays.fill(this.content, 0, this._size, (Object) null);
        this._size = 0;
    }

    public final boolean remove(Object obj) {
        int indexOf = indexOf(obj);
        if (indexOf < 0) {
            return false;
        }
        removeAt(indexOf);
        return true;
    }

    public final Object removeAt(int i) {
        int i2;
        if (i < 0 || i >= (i2 = this._size)) {
            throwIndexOutOfBoundsExclusiveException$collection(i);
            throw null;
        }
        Object[] objArr = this.content;
        Object obj = objArr[i];
        if (i != i2 - 1) {
            ArraysKt___ArraysJvmKt.copyInto(objArr, objArr, i, i + 1, i2);
        }
        int i3 = this._size - 1;
        this._size = i3;
        objArr[i3] = null;
        return obj;
    }

    public final void removeRange(int i, int i2) {
        int i3;
        if (i < 0 || i > (i3 = this._size) || i2 < 0 || i2 > i3) {
            StringBuilder m = MutableObjectList$$ExternalSyntheticOutline0.m(i, i2, "Start (", ") and end (", ") must be in 0..");
            m.append(this._size);
            RuntimeHelpersKt.throwIndexOutOfBoundsException(m.toString());
            throw null;
        }
        if (i2 < i) {
            RuntimeHelpersKt.throwIllegalArgumentException("Start (" + i + ") is more than end (" + i2 + ')');
            throw null;
        }
        if (i2 != i) {
            if (i2 < i3) {
                Object[] objArr = this.content;
                ArraysKt___ArraysJvmKt.copyInto(objArr, objArr, i, i2, i3);
            }
            int i4 = this._size;
            int i5 = i4 - (i2 - i);
            Arrays.fill(this.content, i5, i4, (Object) null);
            this._size = i5;
        }
    }

    public final void resizeStorage(int i, Object[] objArr) {
        int length = objArr.length;
        Object[] objArr2 = new Object[Math.max(i, (length * 3) / 2)];
        System.arraycopy(objArr, 0, objArr2, 0, length);
        this.content = objArr2;
    }

    public MutableObjectList(int i) {
        super(i, null);
    }

    public /* synthetic */ MutableObjectList(int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? 16 : i);
    }
}
