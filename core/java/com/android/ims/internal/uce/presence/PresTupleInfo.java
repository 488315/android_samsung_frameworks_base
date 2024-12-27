package com.android.ims.internal.uce.presence;

import android.os.Parcel;
import android.os.Parcelable;

public class PresTupleInfo implements Parcelable {
    public static final Parcelable.Creator<PresTupleInfo> CREATOR =
            new Parcelable.Creator<
                    PresTupleInfo>() { // from class:
                                       // com.android.ims.internal.uce.presence.PresTupleInfo.1
                @Override // android.os.Parcelable.Creator
                public PresTupleInfo createFromParcel(Parcel source) {
                    return new PresTupleInfo(source);
                }

                @Override // android.os.Parcelable.Creator
                public PresTupleInfo[] newArray(int size) {
                    return new PresTupleInfo[size];
                }
            };
    private String mContactUri;
    private String mFeatureTag;
    private String mTimestamp;
    private String mVersion;

    public String getFeatureTag() {
        return this.mFeatureTag;
    }

    public void setFeatureTag(String featureTag) {
        this.mFeatureTag = featureTag;
    }

    public String getContactUri() {
        return this.mContactUri;
    }

    public void setContactUri(String contactUri) {
        this.mContactUri = contactUri;
    }

    public String getTimestamp() {
        return this.mTimestamp;
    }

    public void setTimestamp(String timestamp) {
        this.mTimestamp = timestamp;
    }

    public String getVersion() {
        return this.mVersion;
    }

    public void setVersion(String version) {
        this.mVersion = version;
    }

    public PresTupleInfo() {
        this.mFeatureTag = "";
        this.mContactUri = "";
        this.mTimestamp = "";
        this.mVersion = "";
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mFeatureTag);
        dest.writeString(this.mContactUri);
        dest.writeString(this.mTimestamp);
        dest.writeString(this.mVersion);
    }

    private PresTupleInfo(Parcel source) {
        this.mFeatureTag = "";
        this.mContactUri = "";
        this.mTimestamp = "";
        this.mVersion = "";
        readFromParcel(source);
    }

    public void readFromParcel(Parcel source) {
        this.mFeatureTag = source.readString();
        this.mContactUri = source.readString();
        this.mTimestamp = source.readString();
        this.mVersion = source.readString();
    }
}
