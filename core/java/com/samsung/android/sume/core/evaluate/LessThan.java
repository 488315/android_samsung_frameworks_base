package com.samsung.android.sume.core.evaluate;

import android.os.Parcel;
import android.os.Parcelable;

import com.samsung.android.sume.core.Def;

import java.util.stream.Stream;

public class LessThan<T extends Comparable<T>> extends GenericEvaluator<T> {
    private static final String TAG = Def.tagOf((Class<?>) LessThan.class);
    public static final Parcelable.Creator<LessThan<?>> CREATOR =
            new Parcelable.Creator<
                    LessThan<
                            ?>>() { // from class: com.samsung.android.sume.core.evaluate.LessThan.1
                @Override // android.os.Parcelable.Creator
                public LessThan<?> createFromParcel(Parcel in) {
                    return new LessThan<>(in);
                }

                @Override // android.os.Parcelable.Creator
                public LessThan<?>[] newArray(int size) {
                    return new LessThan[size];
                }
            };

    @Override // com.samsung.android.sume.core.evaluate.GenericEvaluator,
              // com.samsung.android.sume.core.evaluate.Evaluator
    public /* bridge */ /* synthetic */ Evaluator and(Evaluator evaluator) {
        return super.and(evaluator);
    }

    @Override // com.samsung.android.sume.core.evaluate.GenericEvaluator
    public /* bridge */ /* synthetic */ int compareTo(Evaluator evaluator) {
        return super.compareTo(evaluator);
    }

    @Override // com.samsung.android.sume.core.evaluate.GenericEvaluator, android.os.Parcelable
    public /* bridge */ /* synthetic */ int describeContents() {
        return super.describeContents();
    }

    @Override // com.samsung.android.sume.core.evaluate.GenericEvaluator,
              // com.samsung.android.sume.core.evaluate.Evaluator
    public /* bridge */ /* synthetic */ Comparable getValue() {
        return super.getValue();
    }

    @Override // com.samsung.android.sume.core.evaluate.GenericEvaluator,
              // com.samsung.android.sume.core.evaluate.Evaluator
    public /* bridge */ /* synthetic */ Class getValueType() {
        return super.getValueType();
    }

    @Override // com.samsung.android.sume.core.evaluate.GenericEvaluator,
              // com.samsung.android.sume.core.evaluate.Evaluator
    public /* bridge */ /* synthetic */ Evaluator or(Evaluator evaluator) {
        return super.or(evaluator);
    }

    @Override // com.samsung.android.sume.core.evaluate.GenericEvaluator,
              // com.samsung.android.sume.core.evaluate.Evaluator
    public /* bridge */ /* synthetic */ Stream stream() {
        return super.stream();
    }

    @Override // com.samsung.android.sume.core.evaluate.GenericEvaluator
    public /* bridge */ /* synthetic */ String toString() {
        return super.toString();
    }

    @Override // com.samsung.android.sume.core.evaluate.GenericEvaluator, android.os.Parcelable
    public /* bridge */ /* synthetic */ void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
    }

    LessThan(T value) {
        super(value);
    }

    @Override // com.samsung.android.sume.core.evaluate.Evaluator
    public <V> boolean evaluate(V value) {
        return ((Comparable) value).compareTo(getValue(value.getClass())) < 0;
    }

    LessThan(Parcel in) {
        super(in);
    }
}
