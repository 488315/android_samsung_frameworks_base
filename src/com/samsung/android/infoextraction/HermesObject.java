package com.samsung.android.infoextraction;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
public class HermesObject implements Parcelable {
    public static final Parcelable.Creator<HermesObject> CREATOR = new Parcelable.Creator<HermesObject>() { // from class: com.samsung.android.infoextraction.HermesObject.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public HermesObject createFromParcel(Parcel parcel) {
            HermesObject hermesObject = new HermesObject();
            hermesObject.readFromParcel(parcel);
            return hermesObject;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public HermesObject[] newArray(int i) {
            return new HermesObject[i];
        }
    };
    private Object obj = null;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public void setObject(Object obj) {
        this.obj = obj;
    }

    public Object getObject() {
        return this.obj;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable((HermesObject) this.obj, i);
    }

    public void readFromParcel(Parcel parcel) {
        this.obj = parcel.readParcelable(HermesObject.class.getClassLoader());
    }
}
