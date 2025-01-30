package com.samsung.android.sume.core.format;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Pair;
import com.samsung.android.sume.core.Def;
import com.samsung.android.sume.core.types.ShapeType;
import java.util.Arrays;

/* loaded from: classes4.dex */
class StapleMutableShape implements MutableShape {
  public static final Parcelable.Creator<StapleMutableShape> CREATOR =
      new Parcelable.Creator<
          StapleMutableShape>() { // from class:
                                  // com.samsung.android.sume.core.format.StapleMutableShape.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public StapleMutableShape createFromParcel(Parcel in) {
          return new StapleMutableShape(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public StapleMutableShape[] newArray(int size) {
          return new StapleMutableShape[size];
        }
      };
  private int batch;
  private int channels;
  private int cols;
  private int rows;

  public StapleMutableShape() {
    this.batch = -1;
    this.rows = 0;
    this.cols = 0;
    this.channels = -1;
  }

  public StapleMutableShape(int batch, int rows, int cols, int channels) {
    this.batch = batch;
    this.rows = rows;
    this.cols = cols;
    this.channels = channels;
  }

  public StapleMutableShape(ShapeType shapeType, int[] shape) {
    this.batch = shape[0];
    if (shapeType == ShapeType.NHWC) {
      this.rows = shape[1];
      this.cols = shape[2];
    } else {
      this.cols = shape[1];
      this.rows = shape[2];
    }
    this.channels = shape[3];
  }

  private StapleMutableShape(Parcel in) {
    this.batch = in.readInt();
    this.rows = in.readInt();
    this.cols = in.readInt();
    this.channels = in.readInt();
  }

  @Override // android.os.Parcelable
  public int describeContents() {
    return 0;
  }

  @Override // android.os.Parcelable
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(this.batch);
    dest.writeInt(this.rows);
    dest.writeInt(this.cols);
    dest.writeInt(this.channels);
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
    MutableShape other = (MutableShape) obj;
    int[] arr1 = {this.batch, this.rows, this.cols, this.channels};
    int[] arr2 = {other.getBatch(), other.getRows(), other.getCols(), other.getChannels()};
    return Arrays.equals(arr1, arr2);
  }

  @Override // com.samsung.android.sume.core.format.Shape
  public int getBatch() {
    return this.batch;
  }

  @Override // com.samsung.android.sume.core.format.MutableShape
  public MutableShape setBatch(int batch) {
    this.batch = batch;
    return this;
  }

  @Override // com.samsung.android.sume.core.format.Shape
  public int getRows() {
    return this.rows;
  }

  @Override // com.samsung.android.sume.core.format.MutableShape
  public MutableShape setRows(int rows) {
    this.rows = rows;
    return this;
  }

  @Override // com.samsung.android.sume.core.format.Shape
  public int getCols() {
    return this.cols;
  }

  @Override // com.samsung.android.sume.core.format.MutableShape
  public MutableShape setCols(int cols) {
    this.cols = cols;
    return this;
  }

  @Override // com.samsung.android.sume.core.format.Shape
  public int getChannels() {
    return this.channels;
  }

  @Override // com.samsung.android.sume.core.format.MutableShape
  public MutableShape setChannels(int channels) {
    this.channels = channels;
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
  public <V extends MutableShape> V toMutableShape() {
    return this;
  }

  @Override // com.samsung.android.sume.core.format.Shape
  public int[] toArray(int type) {
    if (type == 2) {
      return new int[] {this.batch, this.cols, this.rows, this.channels};
    }
    return new int[] {this.batch, this.rows, this.cols, this.channels};
  }

  @Override // com.samsung.android.sume.core.format.MutableShape
  public MutableShape scale(Float scale) {
    this.rows = (int) (this.rows * scale.floatValue());
    this.cols = (int) (this.cols * scale.floatValue());
    return this;
  }

  @Override // com.samsung.android.sume.core.format.MutableShape
  public MutableShape scale(Pair<Float, Float> scale) {
    this.rows = (int) (this.rows * scale.first.floatValue());
    this.cols = (int) (this.cols * scale.second.floatValue());
    return this;
  }

  public String toString() {
    return Def.tagOf(this)
        + Def.fmtstr(
            "batch/rows/cols/channels=[%d %d %d %d]",
            Integer.valueOf(this.batch),
            Integer.valueOf(this.rows),
            Integer.valueOf(this.cols),
            Integer.valueOf(this.channels));
  }

  @Override // java.lang.Comparable
  public int compareTo(Shape o) {
    return getTotal() - o.getTotal();
  }
}
