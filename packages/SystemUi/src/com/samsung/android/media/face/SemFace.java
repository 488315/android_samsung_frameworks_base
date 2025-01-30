package com.samsung.android.media.face;

import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class SemFace implements Parcelable {
    public static final Parcelable.Creator<SemFace> CREATOR = new Parcelable.Creator() { // from class: com.samsung.android.media.face.SemFace.1
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            return new SemFace(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new SemFace[i];
        }
    };
    public int pose;
    public Rect rect;

    public SemFace() {
        this.rect = new Rect(0, 0, 0, 0);
        this.pose = 0;
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        if (parcel == null) {
            throw new IllegalArgumentException("Cannot write to Parcel because it is null");
        }
        parcel.writeParcelable(this.rect, i);
        parcel.writeInt(this.pose);
    }

    public SemFace(int i, int i2, int i3, int i4) {
        this.rect = new Rect(i, i2, i3, i4);
        this.pose = 0;
    }

    public SemFace(int i, int i2, int i3, int i4, int i5) {
        this.rect = new Rect(i, i2, i3, i4);
        this.pose = i5;
    }

    public SemFace(Rect rect) {
        this.rect = rect;
        this.pose = 0;
    }

    public SemFace(Rect rect, int i) {
        this.rect = rect;
        this.pose = i;
    }

    public SemFace(Parcel parcel) {
        if (parcel != null) {
            this.rect = (Rect) parcel.readParcelable(Rect.class.getClassLoader());
            this.pose = parcel.readInt();
            return;
        }
        throw new IllegalArgumentException("Cannot create object when input Parcel is null");
    }
}
