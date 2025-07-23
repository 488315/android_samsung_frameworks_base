package com.samsung.android.infoextraction;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes6.dex */
public class KerykeionRequest implements Parcelable {
    public static final Parcelable.Creator<KerykeionRequest> CREATOR = new Parcelable.Creator<KerykeionRequest>() { // from class: com.samsung.android.infoextraction.KerykeionRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public KerykeionRequest createFromParcel(Parcel parcel) {
            KerykeionRequest kerykeionRequest = new KerykeionRequest();
            kerykeionRequest.readFromParcel(parcel);
            return kerykeionRequest;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public KerykeionRequest[] newArray(int i) {
            return new KerykeionRequest[i];
        }
    };
    private List<Object> mPrimitive;
    private int nType;
    private int nPatternType = 0;
    private HermesObject mHermesObject = null;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public KerykeionRequest() {
        this.mPrimitive = null;
        this.mPrimitive = new ArrayList();
    }

    public void setRequestData(int i, List<Object> list, int i2) {
        this.nType = i;
        this.nPatternType = i2;
        for (Object obj : list) {
            if ((obj instanceof String) | (obj instanceof Uri)) {
                this.mPrimitive.add(obj);
            }
        }
    }

    public void setRequestData(int i, List<Object> list, int i2, HermesObject hermesObject) {
        this.nType = i;
        this.nPatternType = i2;
        for (Object obj : list) {
            if ((obj instanceof String) | (obj instanceof Uri)) {
                this.mPrimitive.add(obj);
            }
        }
        if (hermesObject != null) {
            this.mHermesObject = hermesObject;
        }
    }

    public int getType() {
        return this.nType;
    }

    public int getPatternType() {
        return this.nPatternType;
    }

    public List<Object> getSourceData() {
        return this.mPrimitive;
    }

    public HermesObject getHermesObject() {
        return this.mHermesObject;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.nType);
        parcel.writeList(this.mPrimitive);
        parcel.writeInt(this.nPatternType);
        parcel.writeParcelable(this.mHermesObject, i);
    }

    public void readFromParcel(Parcel parcel) {
        this.nType = parcel.readInt();
        this.mPrimitive = parcel.readArrayList(Object.class.getClassLoader());
        this.nPatternType = parcel.readInt();
        this.mHermesObject = (HermesObject) parcel.readParcelable(HermesObject.class.getClassLoader());
    }
}
