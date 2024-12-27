package com.samsung.android.sume.core.evaluate;

import android.os.Parcel;
import android.os.Parcelable;

import com.samsung.android.sume.core.Def;

import java.util.stream.Stream;

abstract class GenericEvaluator<T extends Comparable<T>> implements Evaluator, Parcelable {
    private static final String TAG = Def.tagOf((Class<?>) GenericEvaluator.class);
    T value;

    GenericEvaluator(T value) {
        this.value = value;
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
    public int compareTo(Evaluator other) {
        if (other instanceof GenericEvaluator) {
            return getValue().compareTo((Comparable) other.getValue());
        }
        ((EvaluatorGroup) other).sort();
        return getValue().compareTo(((EvaluatorGroup) other).front());
    }

    @Override // com.samsung.android.sume.core.evaluate.Evaluator
    public Stream<Evaluator> stream() {
        return Stream.of(this);
    }

    @Override // com.samsung.android.sume.core.evaluate.Evaluator
    public Evaluator and(Evaluator other) {
        if (other instanceof AndEvaluatorGroup) {
            return ((AndEvaluatorGroup) other).add(this);
        }
        return new AndEvaluatorGroup(this, other);
    }

    @Override // com.samsung.android.sume.core.evaluate.Evaluator
    public Evaluator or(Evaluator other) {
        if (other instanceof OrEvaluatorGroup) {
            return ((OrEvaluatorGroup) other).add(this);
        }
        return new OrEvaluatorGroup(this, other);
    }

    public int describeContents() {
        return 0;
    }

    protected GenericEvaluator(Parcel in) {
        try {
            String className = in.readString();
            this.value = (T) in.readValue(Class.forName(className).getClassLoader());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            this.value = null;
            throw new IllegalArgumentException();
        }
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.value.getClass().getName());
        dest.writeValue(this.value);
    }

    public String toString() {
        return getClass().getSimpleName() + " : " + this.value;
    }
}
