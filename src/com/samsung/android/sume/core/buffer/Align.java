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

/* loaded from: classes6.dex */
public class Align implements Serializable, Parcelable, Copyable<Align>, Comparable<Align> {
    private int alignOfHeight;
    private int alignOfWidth;
    private int scanline;
    private int stride;
    private static final String TAG = Def.tagOf((Class<?>) Align.class);
    public static final Parcelable.Creator<Align> CREATOR = new Parcelable.Creator<Align>() { // from class: com.samsung.android.sume.core.buffer.Align.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Align createFromParcel(Parcel parcel) {
            return new Align(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Align[] newArray(int i) {
            return new Align[i];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    protected Align() {
        this.stride = 0;
        this.scanline = 0;
        this.alignOfWidth = 0;
        this.alignOfHeight = 0;
    }

    protected Align(int i, int i2) {
        this.stride = i;
        this.scanline = i2;
        this.alignOfWidth = 0;
        this.alignOfHeight = 0;
    }

    protected Align(int i, int i2, int i3, int i4) {
        this.stride = i;
        this.scanline = i2;
        this.alignOfWidth = i3;
        this.alignOfHeight = i4;
    }

    protected Align(Parcel parcel) {
        this.stride = parcel.readInt();
        this.scanline = parcel.readInt();
        this.alignOfWidth = parcel.readInt();
        this.alignOfHeight = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.stride);
        parcel.writeInt(this.scanline);
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
    public int compareTo(Align align) {
        return getDimension() - align.getDimension();
    }

    public String contentToString() {
        return contentToString(this);
    }

    public String contentToString(Object obj) {
        StringBuilder sb = new StringBuilder();
        sb.append(Def.taglnOf(obj));
        sb.append(Def.contentToString("stride=" + this.stride, "scanline=" + this.scanline, "width-align=" + this.alignOfWidth, "height-align=" + this.alignOfHeight));
        return sb.toString();
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
        Log.d(TAG, "adjust align to [" + this.stride + " , " + this.scanline + NavigationBarInflaterView.SIZE_MOD_END);
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

    public Align setStride(int i) {
        this.stride = i;
        return this;
    }

    public Align setScanline(int i) {
        this.scanline = i;
        return this;
    }

    public Align setAlignOfWidth(int i) {
        this.alignOfWidth = i;
        adjustAlign();
        return this;
    }

    public Align setAlignOfHeight(int i) {
        this.alignOfHeight = i;
        adjustAlign();
        return this;
    }

    public Align setShape(int i, int i2) {
        this.stride = i;
        this.scanline = i2;
        return this;
    }

    public Align set(int i, int i2) {
        this.alignOfWidth = i;
        this.alignOfHeight = i2;
        adjustAlign();
        return this;
    }

    public static Align strideOf(int i) {
        return new Align(i, 0);
    }

    public static Align scanlineOf(int i) {
        return new Align(0, i);
    }

    public static Align shapeOf(int i, int i2) {
        return new Align(i, i2);
    }

    public static Align of(int i) {
        return new Align(0, 0, i, 1);
    }

    public static Align of(int i, int i2) {
        return new Align(0, 0, i, i2);
    }

    public static Align of(int i, int i2, int i3, int i4) {
        return new Align(i, i2, i3, i4);
    }

    public static Align setByFormat(MediaFormat mediaFormat) {
        Log.d(TAG, mediaFormat.toString());
        if (mediaFormat.getShape() != null && mediaFormat.getCols() != 0 && mediaFormat.getRows() != 0) {
            if (mediaFormat.getChannels() > 0) {
                return shapeOf(mediaFormat.getCols() * mediaFormat.getChannels(), mediaFormat.getRows());
            }
            return shapeOf(mediaFormat.getCols(), mediaFormat.getRows());
        }
        return new Align();
    }
}
