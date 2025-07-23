package com.samsung.android.sume.core.format;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
class StapleShape implements Shape {
    public static final Parcelable.Creator<StapleShape> CREATOR = new Parcelable.Creator<StapleShape>() { // from class: com.samsung.android.sume.core.format.StapleShape.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public StapleShape createFromParcel(Parcel parcel) {
            return new StapleShape(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public StapleShape[] newArray(int i) {
            return new StapleShape[i];
        }
    };
    public static final int NONE = -1;
    private final MutableShape impl;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    StapleShape(MutableShape mutableShape) {
        this.impl = mutableShape;
    }

    protected StapleShape(Parcel parcel) {
        this.impl = (MutableShape) parcel.readParcelable(StapleMutableShape.class.getClassLoader());
    }

    @Override // com.samsung.android.sume.core.format.Shape
    public int getBatch() {
        return this.impl.getBatch();
    }

    @Override // com.samsung.android.sume.core.format.Shape
    public int getRows() {
        return this.impl.getRows();
    }

    @Override // com.samsung.android.sume.core.format.Shape
    public int getCols() {
        return this.impl.getCols();
    }

    @Override // com.samsung.android.sume.core.format.Shape
    public int getChannels() {
        return this.impl.getChannels();
    }

    @Override // com.samsung.android.sume.core.format.Shape
    public int getDimension() {
        return this.impl.getDimension();
    }

    @Override // com.samsung.android.sume.core.format.Shape
    public int getTotal() {
        return this.impl.getTotal();
    }

    @Override // com.samsung.android.sume.core.format.Shape
    public int[] toArray(int i) {
        return this.impl.toArray(i);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.impl, i);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.samsung.android.sume.core.format.Copyable
    public Shape copy() {
        return new StapleShape((MutableShape) this.impl.copy());
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.samsung.android.sume.core.format.Copyable
    /* renamed from: deepCopy */
    public Shape deepCopy2() {
        return new StapleShape((MutableShape) this.impl.deepCopy2());
    }

    @Override // com.samsung.android.sume.core.format.Shape
    public <V extends MutableShape> V toMutableShape() {
        return (V) this.impl.deepCopy2();
    }

    @Override // java.lang.Comparable
    public int compareTo(Shape shape) {
        return this.impl.compareTo(shape);
    }

    MutableShape asMutable() {
        return this.impl;
    }

    public String toString() {
        return this.impl.toString();
    }
}
