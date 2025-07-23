package com.android.internal.util;

import android.util.SparseArray;
import android.util.SparseIntArray;
import com.android.internal.util.HeavyHitterSketch;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/* loaded from: classes4.dex */
public interface HeavyHitterSketch<T> {
    void add(T t);

    List<T> getCandidates(List<T> list);

    float getRequiredValidationInputRatio();

    List<T> getTopHeavyHitters(int i, List<T> list, List<Float> list2);

    void reset();

    void setConfig(int i, int i2);

    static <V> HeavyHitterSketch<V> newDefault() {
        return new HeavyHitterSketchImpl();
    }

    public static final class HeavyHitterSketchImpl<T> implements HeavyHitterSketch<T> {
        private int mCapacity;
        private boolean mConfigured;
        private int mNumInputs;
        private int mPassSize;
        private int mTotalSize;
        private final SparseArray<T> mObjects = new SparseArray<>();
        private final SparseIntArray mFrequencies = new SparseIntArray();

        @Override // com.android.internal.util.HeavyHitterSketch
        public float getRequiredValidationInputRatio() {
            return 0.5f;
        }

        @Override // com.android.internal.util.HeavyHitterSketch
        public void setConfig(int i, int i2) {
            if (i < i2 || i <= 1) {
                this.mConfigured = false;
                throw new IllegalArgumentException();
            }
            reset();
            this.mTotalSize = i;
            this.mPassSize = i >> 1;
            this.mCapacity = i2;
            this.mConfigured = true;
        }

        @Override // com.android.internal.util.HeavyHitterSketch
        public void add(T t) {
            if (!this.mConfigured) {
                throw new IllegalStateException();
            }
            int i = this.mNumInputs;
            if (i < this.mPassSize) {
                addToMGSummary(t);
            } else if (i < this.mTotalSize) {
                validate(t);
            }
        }

        private void addToMGSummary(T t) {
            int hashCode = t != null ? t.hashCode() : 0;
            int indexOfKey = this.mObjects.indexOfKey(hashCode);
            if (indexOfKey >= 0) {
                SparseIntArray sparseIntArray = this.mFrequencies;
                sparseIntArray.setValueAt(indexOfKey, sparseIntArray.valueAt(indexOfKey) + 1);
            } else if (this.mObjects.size() < this.mCapacity - 1) {
                this.mObjects.put(hashCode, t);
                this.mFrequencies.put(hashCode, 1);
            } else {
                for (int size = this.mFrequencies.size() - 1; size >= 0; size--) {
                    int valueAt = this.mFrequencies.valueAt(size) - 1;
                    if (valueAt == 0) {
                        this.mObjects.removeAt(size);
                        this.mFrequencies.removeAt(size);
                    } else {
                        this.mFrequencies.setValueAt(size, valueAt);
                    }
                }
            }
            int i = this.mNumInputs + 1;
            this.mNumInputs = i;
            if (i == this.mPassSize) {
                for (int size2 = this.mFrequencies.size() - 1; size2 >= 0; size2--) {
                    this.mFrequencies.setValueAt(size2, 0);
                }
            }
        }

        private void validate(T t) {
            int indexOfKey = this.mObjects.indexOfKey(t != null ? t.hashCode() : 0);
            if (indexOfKey >= 0) {
                SparseIntArray sparseIntArray = this.mFrequencies;
                sparseIntArray.setValueAt(indexOfKey, sparseIntArray.valueAt(indexOfKey) + 1);
            }
            int i = this.mNumInputs + 1;
            this.mNumInputs = i;
            if (i == this.mTotalSize) {
                int i2 = this.mPassSize / this.mCapacity;
                for (int size = this.mFrequencies.size() - 1; size >= 0; size--) {
                    if (this.mFrequencies.valueAt(size) < i2) {
                        this.mFrequencies.removeAt(size);
                        this.mObjects.removeAt(size);
                    }
                }
            }
        }

        @Override // com.android.internal.util.HeavyHitterSketch
        public List<T> getTopHeavyHitters(int i, List<T> list, List<Float> list2) {
            if (!this.mConfigured) {
                throw new IllegalStateException();
            }
            if (i >= this.mCapacity) {
                throw new IllegalArgumentException();
            }
            if (this.mNumInputs < this.mTotalSize) {
                throw new IllegalStateException();
            }
            ArrayList arrayList = null;
            for (int size = this.mFrequencies.size() - 1; size >= 0; size--) {
                if (this.mFrequencies.valueAt(size) > 0) {
                    if (arrayList == null) {
                        arrayList = new ArrayList();
                    }
                    arrayList.add(Integer.valueOf(size));
                }
            }
            if (arrayList == null) {
                return null;
            }
            Collections.sort(arrayList, new Comparator() { // from class: com.android.internal.util.HeavyHitterSketch$HeavyHitterSketchImpl$$ExternalSyntheticLambda0
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    int lambda$getTopHeavyHitters$0;
                    lambda$getTopHeavyHitters$0 = HeavyHitterSketch.HeavyHitterSketchImpl.this.lambda$getTopHeavyHitters$0((Integer) obj, (Integer) obj2);
                    return lambda$getTopHeavyHitters$0;
                }
            });
            if (list == null) {
                list = new ArrayList();
            }
            if (i == 0) {
                i = this.mCapacity - 1;
            }
            int min = Math.min(i, arrayList.size());
            for (int i2 = 0; i2 < min; i2++) {
                T valueAt = this.mObjects.valueAt(((Integer) arrayList.get(i2)).intValue());
                if (valueAt != null) {
                    list.add(valueAt);
                    if (list2 != null) {
                        list2.add(Float.valueOf(this.mFrequencies.valueAt(r1) / this.mPassSize));
                    }
                }
            }
            return list;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ int lambda$getTopHeavyHitters$0(Integer num, Integer num2) {
            return this.mFrequencies.valueAt(num2.intValue()) - this.mFrequencies.valueAt(num.intValue());
        }

        @Override // com.android.internal.util.HeavyHitterSketch
        public List<T> getCandidates(List<T> list) {
            if (!this.mConfigured) {
                throw new IllegalStateException();
            }
            if (this.mNumInputs < this.mPassSize) {
                return null;
            }
            if (list == null) {
                list = new ArrayList();
            }
            for (int size = this.mObjects.size() - 1; size >= 0; size--) {
                T valueAt = this.mObjects.valueAt(size);
                if (valueAt != null) {
                    list.add(valueAt);
                }
            }
            return list;
        }

        @Override // com.android.internal.util.HeavyHitterSketch
        public void reset() {
            this.mNumInputs = 0;
            this.mObjects.clear();
            this.mFrequencies.clear();
        }
    }
}
