package com.samsung.android.sume.core.buffer;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.util.Pair;
import com.samsung.android.sume.core.Def;
import com.samsung.android.sume.core.format.Copyable;
import com.samsung.android.sume.core.format.MediaFormat;
import java.io.Serializable;

/* loaded from: classes4.dex */
public class Align implements Serializable, Parcelable, Copyable<Align>, Comparable<Align> {
  private int alignOfHeight;
  private int alignOfWidth;
  private int scanline;
  private int stride;
  private static final String TAG = Def.tagOf((Class<?>) Align.class);
  public static final Parcelable.Creator<Align> CREATOR =
      new Parcelable.Creator<Align>() { // from class: com.samsung.android.sume.core.buffer.Align.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Align createFromParcel(Parcel in) {
          return new Align(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Align[] newArray(int size) {
          return new Align[size];
        }
      };

  protected Align() {
    this.stride = 0;
    this.scanline = 0;
    this.alignOfWidth = 0;
    this.alignOfHeight = 0;
  }

  protected Align(int stride, int scanline) {
    this.stride = stride;
    this.scanline = scanline;
    this.alignOfWidth = 0;
    this.alignOfHeight = 0;
  }

  protected Align(int stride, int scanline, int alignOfWidth, int alignOfHeight) {
    this.stride = stride;
    this.scanline = scanline;
    this.alignOfWidth = alignOfWidth;
    this.alignOfHeight = alignOfHeight;
  }

  protected Align(Parcel in) {
    this.stride = in.readInt();
    this.scanline = in.readInt();
    this.alignOfWidth = in.readInt();
    this.alignOfHeight = in.readInt();
  }

  @Override // android.os.Parcelable
  public int describeContents() {
    return 0;
  }

  @Override // android.os.Parcelable
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(this.stride);
    dest.writeInt(this.scanline);
  }

  /* JADX WARN: Can't rename method to resolve collision */
  @Override // com.samsung.android.sume.core.format.Copyable
  public Align copy() {
    try {
      return (Align) clone();
    } catch (CloneNotSupportedException e) {
      e.printStackTrace();
      throw new IllegalStateException("fail to clone");
    }
  }

  /* JADX WARN: Can't rename method to resolve collision */
  @Override // com.samsung.android.sume.core.format.Copyable
  /* renamed from: deepCopy */
  public Align deepCopy2() {
    return new Align(this.stride, this.scanline, this.alignOfWidth, this.alignOfHeight);
  }

  @Override // java.lang.Comparable
  public int compareTo(Align o) {
    return getDimension() - o.getDimension();
  }

  public String contentToString() {
    return contentToString(this);
  }

  public String contentToString(Object obj) {
    return Def.taglnOf(obj)
        + Def.contentToString(
            "stride=" + this.stride,
            "scanline=" + this.scanline,
            "width-align=" + this.alignOfWidth,
            "height-align=" + this.alignOfHeight);
  }

  public void adjustAlign() {
    int i;
    int i2;
    int i3 = this.stride;
    if (i3 > 0 && (i2 = this.alignOfWidth) > 0 && i3 % i2 != 0) {
      this.stride = ((i3 + i2) - 1) & (~(i2 - 1));
    }
    int i4 = this.scanline;
    if (i4 > 0 && (i = this.alignOfHeight) > 0 && i4 % i != 0) {
      this.scanline = ((i4 + i) - 1) & (~(i - 1));
    }
    Log.m94d(
        TAG,
        "adjust align to ["
            + this.stride
            + " , "
            + this.scanline
            + NavigationBarInflaterView.SIZE_MOD_END);
  }

  public int getStride() {
    return this.stride;
  }

  public int getScanline() {
    return this.scanline;
  }

  public int getAlignOfWidth() {
    return this.alignOfWidth;
  }

  public int getAlignOfHeight() {
    return this.alignOfHeight;
  }

  public Pair getAlign() {
    return new Pair(Integer.valueOf(this.alignOfWidth), Integer.valueOf(this.alignOfHeight));
  }

  public int getDimension() {
    int i;
    int i2 = this.stride;
    if (i2 <= 0 || (i = this.scanline) <= 0) {
      return 0;
    }
    return i2 * i;
  }

  public Align setStride(int stride) {
    this.stride = stride;
    return this;
  }

  public Align setScanline(int scanline) {
    this.scanline = scanline;
    return this;
  }

  public Align setAlignOfWidth(int alignOfWidth) {
    this.alignOfWidth = alignOfWidth;
    adjustAlign();
    return this;
  }

  public Align setAlignOfHeight(int alignOfHeight) {
    this.alignOfHeight = alignOfHeight;
    adjustAlign();
    return this;
  }

  public Align setShape(int stride, int scanline) {
    this.stride = stride;
    this.scanline = scanline;
    return this;
  }

  public Align set(int alignOfWidth, int alignOfHeight) {
    this.alignOfWidth = alignOfWidth;
    this.alignOfHeight = alignOfHeight;
    adjustAlign();
    return this;
  }

  public static Align strideOf(int stride) {
    return new Align(stride, 0);
  }

  public static Align scanlineOf(int scanline) {
    return new Align(0, scanline);
  }

  public static Align shapeOf(int stride, int scanline) {
    return new Align(stride, scanline);
  }

  /* renamed from: of */
  public static Align m310of(int alignOfWidth) {
    return new Align(0, 0, alignOfWidth, 1);
  }

  /* renamed from: of */
  public static Align m311of(int alignOfWidth, int alignOfHeight) {
    return new Align(0, 0, alignOfWidth, alignOfHeight);
  }

  /* renamed from: of */
  public static Align m312of(int stride, int scanline, int alignOfWidth, int alignOfHeight) {
    return new Align(stride, scanline, alignOfWidth, alignOfHeight);
  }

  public static Align setByFormat(MediaFormat format) {
    Log.m94d(TAG, format.toString());
    if (format.getShape() != null && format.getCols() != 0 && format.getRows() != 0) {
      if (format.getChannels() > 0) {
        return shapeOf(format.getCols() * format.getChannels(), format.getRows());
      }
      return shapeOf(format.getCols(), format.getRows());
    }
    return new Align();
  }
}
