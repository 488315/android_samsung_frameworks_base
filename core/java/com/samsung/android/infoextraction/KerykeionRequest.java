package com.samsung.android.infoextraction;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class KerykeionRequest implements Parcelable {
    public static final Parcelable.Creator<KerykeionRequest> CREATOR =
            new Parcelable.Creator<
                    KerykeionRequest>() { // from class:
                                          // com.samsung.android.infoextraction.KerykeionRequest.1
                @Override // android.os.Parcelable.Creator
                public KerykeionRequest createFromParcel(Parcel in) {
                    KerykeionRequest data = new KerykeionRequest();
                    data.readFromParcel(in);
                    return data;
                }

                @Override // android.os.Parcelable.Creator
                public KerykeionRequest[] newArray(int size) {
                    return new KerykeionRequest[size];
                }
            };
    private List<Object> mPrimitive;
    private int nType;
    private int nPatternType = 0;
    private HermesObject mHermesObject = null;

    public KerykeionRequest() {
        this.mPrimitive = null;
        this.mPrimitive = new ArrayList();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public void setRequestData(int type, List<Object> origin, int patternType) {
        this.nType = type;
        this.nPatternType = patternType;
        for (Object obj : origin) {
            if ((obj instanceof String) | (obj instanceof Uri)) {
                this.mPrimitive.add(obj);
            }
        }
    }

    public void setRequestData(int type, List<Object> origin, int patternType, HermesObject hObj) {
        this.nType = type;
        this.nPatternType = patternType;
        for (Object obj : origin) {
            if ((obj instanceof String) | (obj instanceof Uri)) {
                this.mPrimitive.add(obj);
            }
        }
        if (hObj != null) {
            this.mHermesObject = hObj;
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
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(this.nType);
        out.writeList(this.mPrimitive);
        out.writeInt(this.nPatternType);
        out.writeParcelable(this.mHermesObject, flags);
    }

    public void readFromParcel(Parcel in) {
        this.nType = in.readInt();
        this.mPrimitive = in.readArrayList(Object.class.getClassLoader());
        this.nPatternType = in.readInt();
        this.mHermesObject = (HermesObject) in.readParcelable(HermesObject.class.getClassLoader());
    }
}
