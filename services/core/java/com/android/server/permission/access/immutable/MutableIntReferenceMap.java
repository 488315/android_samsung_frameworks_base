package com.android.server.permission.access.immutable;

import android.util.SparseArray;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class MutableIntReferenceMap implements Immutable {
    public final SparseArray array;

    public /* synthetic */ MutableIntReferenceMap() {
        this(new SparseArray());
    }

    public MutableIntReferenceMap(SparseArray sparseArray) {
        this.array = sparseArray;
    }

    public final Immutable get(int i) {
        MutableReference mutableReference = (MutableReference) this.array.get(i);
        if (mutableReference != null) {
            return mutableReference.immutable;
        }
        return null;
    }

    public final int keyAt(int i) {
        return this.array.keyAt(i);
    }

    public final Immutable mutate(int i) {
        MutableReference mutableReference = (MutableReference) this.array.get(i);
        if (mutableReference != null) {
            return mutableReference.mutate();
        }
        return null;
    }

    public final void put(int i, Immutable immutable) {
        Object obj;
        SparseArray sparseArray = this.array;
        MutableReference mutableReference = new MutableReference(immutable, immutable);
        int indexOfKey = sparseArray.indexOfKey(i);
        if (indexOfKey >= 0) {
            obj = sparseArray.valueAt(indexOfKey);
            sparseArray.setValueAt(indexOfKey, mutableReference);
        } else {
            sparseArray.put(i, mutableReference);
            obj = null;
        }
    }

    public final void removeAt$1(int i) {
        SparseArray sparseArray = this.array;
        Object valueAt = sparseArray.valueAt(i);
        sparseArray.removeAt(i);
        this.array.size();
        Immutable immutable = ((MutableReference) valueAt).immutable;
    }

    @Override // com.android.server.permission.access.immutable.Immutable
    public final Object toMutable() {
        SparseArray clone = this.array.clone();
        int size = clone.size();
        for (int i = 0; i < size; i++) {
            clone.setValueAt(i, ((MutableReference) clone.valueAt(i)).toImmutable());
        }
        return new MutableIntReferenceMap(clone);
    }

    public final String toString() {
        return this.array.toString();
    }

    public final Immutable valueAt(int i) {
        return ((MutableReference) this.array.valueAt(i)).immutable;
    }
}
