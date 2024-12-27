package com.samsung.android.sume.core.format;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
class StapleShape implements Shape {
    public static final Parcelable.Creator<StapleShape> CREATOR =
            new Parcelable.Creator<
                    StapleShape>() { // from class:
                                     // com.samsung.android.sume.core.format.StapleShape.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public StapleShape createFromParcel(Parcel in) {
                    return new StapleShape(in);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public StapleShape[] newArray(int size) {
                    return new StapleShape[size];
                }
            };
    public static final int NONE = -1;
    private final MutableShape impl;

    StapleShape(MutableShape impl) {
        this.impl = impl;
    }

    protected StapleShape(Parcel in) {
        this.impl = (MutableShape) in.readParcelable(StapleMutableShape.class.getClassLoader());
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
    public int[] toArray(int type) {
        return this.impl.toArray(type);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.impl, flags);
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
    public int compareTo(Shape o) {
        return this.impl.compareTo(o);
    }

    MutableShape asMutable() {
        return this.impl;
    }

    public String toString() {
        return this.impl.toString();
    }
}
