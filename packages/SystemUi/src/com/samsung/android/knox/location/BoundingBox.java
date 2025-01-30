package com.samsung.android.knox.location;

import android.os.Parcel;
import android.os.Parcelable;
import java.io.Serializable;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class BoundingBox implements Serializable, Parcelable {
    public static final Parcelable.Creator<BoundingBox> CREATOR = new Parcelable.Creator<BoundingBox>() { // from class: com.samsung.android.knox.location.BoundingBox.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final BoundingBox[] newArray(int i) {
            return new BoundingBox[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final BoundingBox createFromParcel(Parcel parcel) {
            return new BoundingBox(parcel, 0);
        }

        @Override // android.os.Parcelable.Creator
        public final BoundingBox[] newArray(int i) {
            return new BoundingBox[i];
        }
    };
    private static final long serialVersionUID = 1;
    public LatLongPoint bottom;
    public LatLongPoint left;
    public LatLongPoint right;
    public LatLongPoint top;

    public /* synthetic */ BoundingBox(Parcel parcel, int i) {
        this(parcel);
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    public final void readFromParcel(Parcel parcel) {
        Parcelable.Creator<LatLongPoint> creator = LatLongPoint.CREATOR;
        this.left = creator.createFromParcel(parcel);
        this.right = creator.createFromParcel(parcel);
        this.top = creator.createFromParcel(parcel);
        this.bottom = creator.createFromParcel(parcel);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        this.left.writeToParcel(parcel, i);
        this.right.writeToParcel(parcel, i);
        this.top.writeToParcel(parcel, i);
        this.bottom.writeToParcel(parcel, i);
    }

    public BoundingBox() {
        this.left = new LatLongPoint(0.0d, 0.0d);
        this.right = new LatLongPoint(0.0d, 0.0d);
        this.top = new LatLongPoint(0.0d, 0.0d);
        this.bottom = new LatLongPoint(0.0d, 0.0d);
    }

    public BoundingBox(LatLongPoint latLongPoint, LatLongPoint latLongPoint2, LatLongPoint latLongPoint3, LatLongPoint latLongPoint4) {
        this.left = latLongPoint;
        this.right = latLongPoint2;
        this.top = latLongPoint3;
        this.bottom = latLongPoint4;
    }

    private BoundingBox(Parcel parcel) {
        readFromParcel(parcel);
    }
}
