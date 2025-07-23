package com.samsung.android.sume.core.evaluate;

import android.os.Parcel;
import android.os.Parcelable;
import java.lang.Comparable;
import java.util.stream.Stream;

/* loaded from: classes6.dex */
public class GreaterEqual<T extends Comparable<T>> extends GenericEvaluator<T> {
    public static final Parcelable.Creator<GreaterEqual<?>> CREATOR = new Parcelable.Creator<GreaterEqual<?>>() { // from class: com.samsung.android.sume.core.evaluate.GreaterEqual.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public GreaterEqual<?> createFromParcel(Parcel parcel) {
            return new GreaterEqual<>(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public GreaterEqual<?>[] newArray(int i) {
            return new GreaterEqual[i];
        }
    };

    @Override // com.samsung.android.sume.core.evaluate.GenericEvaluator, com.samsung.android.sume.core.evaluate.Evaluator
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

    @Override // com.samsung.android.sume.core.evaluate.GenericEvaluator, com.samsung.android.sume.core.evaluate.Evaluator
    public /* bridge */ /* synthetic */ Comparable getValue() {
        return super.getValue();
    }

    @Override // com.samsung.android.sume.core.evaluate.GenericEvaluator, com.samsung.android.sume.core.evaluate.Evaluator
    public /* bridge */ /* synthetic */ Class getValueType() {
        return super.getValueType();
    }

    @Override // com.samsung.android.sume.core.evaluate.GenericEvaluator, com.samsung.android.sume.core.evaluate.Evaluator
    public /* bridge */ /* synthetic */ Evaluator or(Evaluator evaluator) {
        return super.or(evaluator);
    }

    @Override // com.samsung.android.sume.core.evaluate.GenericEvaluator, com.samsung.android.sume.core.evaluate.Evaluator
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

    GreaterEqual(T t) {
        super(t);
    }

    GreaterEqual(Parcel parcel) {
        super(parcel);
    }

    @Override // com.samsung.android.sume.core.evaluate.Evaluator
    public <V> boolean evaluate(V v) {
        return ((Comparable) v).compareTo(getValue()) >= 0;
    }
}
