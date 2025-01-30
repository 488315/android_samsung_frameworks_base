package com.samsung.android.sume.core.evaluate;

import android.p009os.Parcel;
import android.p009os.Parcelable;
import java.lang.Comparable;
import java.util.stream.Stream;

/* loaded from: classes4.dex */
public class Equal<T extends Comparable<T>> extends GenericEvaluator<T> {
    public static final Parcelable.Creator<Equal<?>> CREATOR = new Parcelable.Creator<Equal<?>>() { // from class: com.samsung.android.sume.core.evaluate.Equal.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Equal<?> createFromParcel(Parcel in) {
            return new Equal<>(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Equal<?>[] newArray(int size) {
            return new Equal[size];
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

    @Override // com.samsung.android.sume.core.evaluate.GenericEvaluator, android.p009os.Parcelable
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
    /* renamed from: or */
    public /* bridge */ /* synthetic */ Evaluator mo360or(Evaluator evaluator) {
        return super.mo360or(evaluator);
    }

    @Override // com.samsung.android.sume.core.evaluate.GenericEvaluator, com.samsung.android.sume.core.evaluate.Evaluator
    public /* bridge */ /* synthetic */ Stream stream() {
        return super.stream();
    }

    @Override // com.samsung.android.sume.core.evaluate.GenericEvaluator
    public /* bridge */ /* synthetic */ String toString() {
        return super.toString();
    }

    @Override // com.samsung.android.sume.core.evaluate.GenericEvaluator, android.p009os.Parcelable
    public /* bridge */ /* synthetic */ void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
    }

    Equal(T value) {
        super(value);
    }

    @Override // com.samsung.android.sume.core.evaluate.Evaluator
    public <V> boolean evaluate(V value) {
        return getValue() == value;
    }

    Equal(Parcel in) {
        super(in);
    }
}
