package com.samsung.android.sume.core.evaluate;

import android.os.Parcel;
import android.os.Parcelable;
import com.samsung.android.sume.core.Def;
import java.lang.Comparable;
import java.util.stream.Stream;

/* loaded from: classes6.dex */
abstract class GenericEvaluator<T extends Comparable<T>> implements Evaluator, Parcelable {
    private static final String TAG = Def.tagOf((Class<?>) GenericEvaluator.class);
    T value;

    public int describeContents() {
        return 0;
    }

    GenericEvaluator(T t) {
        this.value = t;
    }

    @Override // com.samsung.android.sume.core.evaluate.Evaluator
    public T getValue() {
        return this.value;
    }

    <V> V getValue(Class<V> cls) {
        if (this.value instanceof Number) {
            if (Float.class.isAssignableFrom(cls)) {
                return cls.cast(Float.valueOf(((Number) this.value).floatValue()));
            }
            if (Integer.class.isAssignableFrom(cls)) {
                return cls.cast(Integer.valueOf(((Number) this.value).intValue()));
            }
            if (Long.class.isAssignableFrom(cls)) {
                return cls.cast(Long.valueOf(((Number) this.value).longValue()));
            }
            if (Double.class.isAssignableFrom(cls)) {
                return cls.cast(Double.valueOf(((Number) this.value).doubleValue()));
            }
            if (Byte.class.isAssignableFrom(cls)) {
                return cls.cast(Byte.valueOf(((Number) this.value).byteValue()));
            }
            if (Short.class.isAssignableFrom(cls)) {
                return cls.cast(Short.valueOf(((Number) this.value).shortValue()));
            }
            throw new IllegalStateException("unknown number type");
        }
        throw new UnsupportedOperationException("not implemented except number type");
    }

    @Override // com.samsung.android.sume.core.evaluate.Evaluator
    public Class<?> getValueType() {
        return this.value.getClass();
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // java.lang.Comparable
    public int compareTo(Evaluator evaluator) {
        if (evaluator instanceof GenericEvaluator) {
            return getValue().compareTo((Comparable) evaluator.getValue());
        }
        EvaluatorGroup evaluatorGroup = (EvaluatorGroup) evaluator;
        evaluatorGroup.sort();
        return getValue().compareTo(evaluatorGroup.front());
    }

    @Override // com.samsung.android.sume.core.evaluate.Evaluator
    public Stream<Evaluator> stream() {
        return Stream.of(this);
    }

    @Override // com.samsung.android.sume.core.evaluate.Evaluator
    public Evaluator and(Evaluator evaluator) {
        if (evaluator instanceof AndEvaluatorGroup) {
            return ((AndEvaluatorGroup) evaluator).add(this);
        }
        return new AndEvaluatorGroup(this, evaluator);
    }

    @Override // com.samsung.android.sume.core.evaluate.Evaluator
    public Evaluator or(Evaluator evaluator) {
        if (evaluator instanceof OrEvaluatorGroup) {
            return ((OrEvaluatorGroup) evaluator).add(this);
        }
        return new OrEvaluatorGroup(this, evaluator);
    }

    protected GenericEvaluator(Parcel parcel) {
        try {
            this.value = (T) parcel.readValue(Class.forName(parcel.readString()).getClassLoader());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            this.value = null;
            throw new IllegalArgumentException();
        }
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.value.getClass().getName());
        parcel.writeValue(this.value);
    }

    public String toString() {
        return getClass().getSimpleName() + " : " + this.value;
    }
}
