package android.util;

import java.util.function.Consumer;

/* loaded from: classes4.dex */
public class SparseArrayMap<K, V> {
    private final SparseArray<ArrayMap<K, V>> mData = new SparseArray<>();

    public interface TriConsumer<K, V> {
        void accept(int i, K k, V v);
    }

    public V add(int i, K k, V v) {
        ArrayMap<K, V> arrayMap = this.mData.get(i);
        if (arrayMap == null) {
            arrayMap = new ArrayMap<>();
            this.mData.put(i, arrayMap);
        }
        return arrayMap.put(k, v);
    }

    public void clear() {
        for (int i = 0; i < this.mData.size(); i++) {
            this.mData.valueAt(i).clear();
        }
    }

    public boolean contains(int i, K k) {
        return this.mData.contains(i) && this.mData.get(i).containsKey(k);
    }

    public void delete(int i) {
        this.mData.delete(i);
    }

    public void deleteAt(int i) {
        this.mData.removeAt(i);
    }

    public V delete(int i, K k) {
        ArrayMap<K, V> arrayMap = this.mData.get(i);
        if (arrayMap != null) {
            return arrayMap.remove(k);
        }
        return null;
    }

    public void deleteAt(int i, int i2) {
        this.mData.valueAt(i).removeAt(i2);
    }

    public V get(int i, K k) {
        ArrayMap<K, V> arrayMap = this.mData.get(i);
        if (arrayMap != null) {
            return arrayMap.get(k);
        }
        return null;
    }

    public V getOrDefault(int i, K k, V v) {
        ArrayMap<K, V> arrayMap;
        return (this.mData.contains(i) && (arrayMap = this.mData.get(i)) != null && arrayMap.containsKey(k)) ? arrayMap.get(k) : v;
    }

    public int indexOfKey(int i) {
        return this.mData.indexOfKey(i);
    }

    public int indexOfKey(int i, K k) {
        ArrayMap<K, V> arrayMap = this.mData.get(i);
        if (arrayMap != null) {
            return arrayMap.indexOfKey(k);
        }
        return -1;
    }

    public int keyAt(int i) {
        return this.mData.keyAt(i);
    }

    public K keyAt(int i, int i2) {
        return this.mData.valueAt(i).keyAt(i2);
    }

    public int numMaps() {
        return this.mData.size();
    }

    public int numElementsForKey(int i) {
        ArrayMap<K, V> arrayMap = this.mData.get(i);
        if (arrayMap == null) {
            return 0;
        }
        return arrayMap.size();
    }

    public int numElementsForKeyAt(int i) {
        ArrayMap<K, V> valueAt = this.mData.valueAt(i);
        if (valueAt == null) {
            return 0;
        }
        return valueAt.size();
    }

    public V valueAt(int i, int i2) {
        return this.mData.valueAt(i).valueAt(i2);
    }

    public void forEach(Consumer<V> consumer) {
        for (int numMaps = numMaps() - 1; numMaps >= 0; numMaps--) {
            ArrayMap<K, V> valueAt = this.mData.valueAt(numMaps);
            for (int size = valueAt.size() - 1; size >= 0; size--) {
                consumer.accept(valueAt.valueAt(size));
            }
        }
    }

    public void forEach(TriConsumer<K, V> triConsumer) {
        for (int numMaps = numMaps() - 1; numMaps >= 0; numMaps--) {
            int keyAt = this.mData.keyAt(numMaps);
            ArrayMap<K, V> valueAt = this.mData.valueAt(numMaps);
            for (int size = valueAt.size() - 1; size >= 0; size--) {
                triConsumer.accept(keyAt, valueAt.keyAt(size), valueAt.valueAt(size));
            }
        }
    }
}
