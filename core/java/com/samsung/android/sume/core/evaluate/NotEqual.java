package com.samsung.android.sume.core.evaluate;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.stream.Stream;

public class NotEqual<T extends Comparable<T>> extends GenericEvaluator<T> {
    public static final Parcelable.Creator<NotEqual<?>> CREATOR =
            new Parcelable.Creator<
                    NotEqual<
                            ?>>() { // from class: com.samsung.android.sume.core.evaluate.NotEqual.1
                @Override // android.os.Parcelable.Creator
                public NotEqual<?> createFromParcel(Parcel in) {
                    return new NotEqual<>(in);
                }

                @Override // android.os.Parcelable.Creator
                public NotEqual<?>[] newArray(int size) {
                    return new NotEqual[size];
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

    NotEqual(T value) {
        super(value);
    }

    @Override // com.samsung.android.sume.core.evaluate.Evaluator
    public <V> boolean evaluate(V value) {
        return getValue() != value;
    }

    NotEqual(Parcel in) {
        super(in);
    }
}
