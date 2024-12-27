package com.samsung.android.infoextraction;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
public class HermesObject implements Parcelable {
    public static final Parcelable.Creator<HermesObject> CREATOR = new Parcelable.Creator<HermesObject>() { // from class: com.samsung.android.infoextraction.HermesObject.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public HermesObject createFromParcel(Parcel in) {
            HermesObject data = new HermesObject();
            data.readFromParcel(in);
            return data;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public HermesObject[] newArray(int size) {
            return new HermesObject[size];
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
    public void writeToParcel(Parcel out, int flags) {
        out.writeParcelable((HermesObject) this.obj, flags);
    }

    public void readFromParcel(Parcel in) {
        this.obj = in.readParcelable(HermesObject.class.getClassLoader());
    }
}
