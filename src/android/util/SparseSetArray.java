package android.util;

/* loaded from: classes4.dex */
public class SparseSetArray<T> {
    private final SparseArray<ArraySet<T>> mData;

    public SparseSetArray() {
        this.mData = new SparseArray<>();
    }

    public SparseSetArray(SparseSetArray<T> sparseSetArray) {
        int size = sparseSetArray.size();
        this.mData = new SparseArray<>(size);
        for (int i = 0; i < size; i++) {
            int keyAt = sparseSetArray.keyAt(i);
            addAll(keyAt, sparseSetArray.get(keyAt));
        }
    }

    public boolean add(int i, T t) {
        ArraySet<T> arraySet = this.mData.get(i);
        if (arraySet == null) {
            arraySet = new ArraySet<>();
            this.mData.put(i, arraySet);
        }
        if (arraySet.contains(t)) {
            return false;
        }
        arraySet.add(t);
        return true;
    }

    public void addAll(int i, ArraySet<T> arraySet) {
        ArraySet<T> arraySet2 = this.mData.get(i);
        if (arraySet2 == null) {
            this.mData.put(i, new ArraySet<>((ArraySet) arraySet));
        } else {
            arraySet2.addAll((ArraySet<? extends T>) arraySet);
        }
    }

    public void clear() {
        this.mData.clear();
    }

    public boolean contains(int i, T t) {
        ArraySet<T> arraySet = this.mData.get(i);
        if (arraySet == null) {
            return false;
        }
        return arraySet.contains(t);
    }

    public ArraySet<T> get(int i) {
        return this.mData.get(i);
    }

    public boolean remove(int i, T t) {
        ArraySet<T> arraySet = this.mData.get(i);
        if (arraySet == null) {
            return false;
        }
        boolean remove = arraySet.remove(t);
        if (arraySet.size() == 0) {
            this.mData.remove(i);
        }
        return remove;
    }

    public void remove(int i) {
        this.mData.remove(i);
    }

    public int size() {
        return this.mData.size();
    }

    public int keyAt(int i) {
        return this.mData.keyAt(i);
    }

    public int sizeAt(int i) {
        ArraySet<T> valueAt = this.mData.valueAt(i);
        if (valueAt == null) {
            return 0;
        }
        return valueAt.size();
    }

    public T valueAt(int i, int i2) {
        return this.mData.valueAt(i).valueAt(i2);
    }

    public ArraySet<T> valuesAt(int i) {
        return this.mData.valueAt(i);
    }
}
