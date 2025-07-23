package com.samsung.android.sume.core.format;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Pair;
import com.samsung.android.sume.core.Def;
import com.samsung.android.sume.core.types.ShapeType;
import java.util.Arrays;

/* loaded from: classes6.dex */
class StapleMutableShape implements MutableShape {
    public static final Parcelable.Creator<StapleMutableShape> CREATOR = new Parcelable.Creator<StapleMutableShape>() { // from class: com.samsung.android.sume.core.format.StapleMutableShape.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public StapleMutableShape createFromParcel(Parcel parcel) {
            return new StapleMutableShape(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public StapleMutableShape[] newArray(int i) {
            return new StapleMutableShape[i];
        }
    };
    private int batch;
    private int channels;
    private int cols;
    private int rows;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // com.samsung.android.sume.core.format.Shape
    public <V extends MutableShape> V toMutableShape() {
        return this;
    }

    public StapleMutableShape() {
        this.batch = -1;
        this.rows = 0;
        this.cols = 0;
        this.channels = -1;
    }

    public StapleMutableShape(int i, int i2, int i3, int i4) {
        this.batch = i;
        this.rows = i2;
        this.cols = i3;
        this.channels = i4;
    }

    public StapleMutableShape(ShapeType shapeType, int[] iArr) {
        this.batch = iArr[0];
        if (shapeType == ShapeType.NHWC) {
            this.rows = iArr[1];
            this.cols = iArr[2];
        } else {
            this.cols = iArr[1];
            this.rows = iArr[2];
        }
        this.channels = iArr[3];
    }

    private StapleMutableShape(Parcel parcel) {
        this.batch = parcel.readInt();
        this.rows = parcel.readInt();
        this.cols = parcel.readInt();
        this.channels = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.batch);
        parcel.writeInt(this.rows);
        parcel.writeInt(this.cols);
        parcel.writeInt(this.channels);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.samsung.android.sume.core.format.Copyable
    public Shape copy() {
        try {
            return (Shape) clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            throw new IllegalStateException("fail to clone");
        }
    }

    @Override // com.samsung.android.sume.core.format.Copyable
    /* renamed from: deepCopy, reason: merged with bridge method [inline-methods] */
    public Shape deepCopy2() {
        return new StapleMutableShape(this.batch, this.rows, this.cols, this.channels);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof MutableShape)) {
            return false;
        }
        MutableShape mutableShape = (MutableShape) obj;
        return Arrays.equals(new int[]{this.batch, this.rows, this.cols, this.channels}, new int[]{mutableShape.getBatch(), mutableShape.getRows(), mutableShape.getCols(), mutableShape.getChannels()});
    }

    @Override // com.samsung.android.sume.core.format.Shape
    public int getBatch() {
        return this.batch;
    }

    @Override // com.samsung.android.sume.core.format.MutableShape
    public MutableShape setBatch(int i) {
        this.batch = i;
        return this;
    }

    @Override // com.samsung.android.sume.core.format.Shape
    public int getRows() {
        return this.rows;
    }

    @Override // com.samsung.android.sume.core.format.MutableShape
    public MutableShape setRows(int i) {
        this.rows = i;
        return this;
    }

    @Override // com.samsung.android.sume.core.format.Shape
    public int getCols() {
        return this.cols;
    }

    @Override // com.samsung.android.sume.core.format.MutableShape
    public MutableShape setCols(int i) {
        this.cols = i;
        return this;
    }

    @Override // com.samsung.android.sume.core.format.Shape
    public int getChannels() {
        return this.channels;
    }

    @Override // com.samsung.android.sume.core.format.MutableShape
    public MutableShape setChannels(int i) {
        this.channels = i;
        return this;
    }

    @Override // com.samsung.android.sume.core.format.MutableShape
    public <V extends Shape> V toShape() {
        return new StapleShape(this);
    }

    @Override // com.samsung.android.sume.core.format.Shape
    public int getDimension() {
        return this.cols * this.rows;
    }

    @Override // com.samsung.android.sume.core.format.Shape
    public int getTotal() {
        return this.batch * this.cols * this.rows * this.channels;
    }

    @Override // com.samsung.android.sume.core.format.Shape
    public int[] toArray(int i) {
        if (i == 2) {
            return new int[]{this.batch, this.cols, this.rows, this.channels};
        }
        return new int[]{this.batch, this.rows, this.cols, this.channels};
    }

    @Override // com.samsung.android.sume.core.format.MutableShape
    public MutableShape scale(Float f) {
        this.rows = (int) (this.rows * f.floatValue());
        this.cols = (int) (this.cols * f.floatValue());
        return this;
    }

    @Override // com.samsung.android.sume.core.format.MutableShape
    public MutableShape scale(Pair<Float, Float> pair) {
        this.rows = (int) (this.rows * pair.first.floatValue());
        this.cols = (int) (this.cols * pair.second.floatValue());
        return this;
    }

    public String toString() {
        return Def.tagOf(this) + Def.fmtstr("batch/rows/cols/channels=[%d %d %d %d]", Integer.valueOf(this.batch), Integer.valueOf(this.rows), Integer.valueOf(this.cols), Integer.valueOf(this.channels));
    }

    @Override // java.lang.Comparable
    public int compareTo(Shape shape) {
        return getTotal() - shape.getTotal();
    }
}
