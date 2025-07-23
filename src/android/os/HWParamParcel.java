package android.os;

import android.os.Parcelable;

/* loaded from: classes3.dex */
public class HWParamParcel implements Parcelable {
    public static final Parcelable.Creator<HWParamParcel> CREATOR = new Parcelable.Creator<HWParamParcel>() { // from class: android.os.HWParamParcel.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public HWParamParcel createFromParcel(Parcel parcel) {
            return new HWParamParcel(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public HWParamParcel[] newArray(int i) {
            return new HWParamParcel[i];
        }
    };
    String appID;
    String compID;
    String compManufacture;
    String compVer;
    String customMap;
    String developMap;
    String feature;
    String hitType;
    String privateMap;
    int type;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public HWParamParcel() {
        this.type = 0;
        this.compID = "";
        this.feature = "";
        this.hitType = "";
        this.compVer = "";
        this.compManufacture = "";
        this.developMap = "";
        this.customMap = "";
        this.privateMap = "";
        this.appID = "";
    }

    private HWParamParcel(Parcel parcel) {
        this.type = 0;
        this.compID = "";
        this.feature = "";
        this.hitType = "";
        this.compVer = "";
        this.compManufacture = "";
        this.developMap = "";
        this.customMap = "";
        this.privateMap = "";
        this.appID = "";
        readFromParcel(parcel);
    }

    public void writeToParcel(Parcel parcel) {
        parcel.writeInt(this.type);
        parcel.writeString(this.compID);
        parcel.writeString(this.feature);
        parcel.writeString(this.hitType);
        parcel.writeString(this.compVer);
        parcel.writeString(this.compManufacture);
        parcel.writeString(this.developMap);
        parcel.writeString(this.customMap);
        parcel.writeString(this.privateMap);
        parcel.writeString(this.appID);
    }

    public void readFromParcel(Parcel parcel) {
        this.type = parcel.readInt();
        this.compID = parcel.readString();
        this.feature = parcel.readString();
        this.hitType = parcel.readString();
        this.compVer = parcel.readString();
        this.compManufacture = parcel.readString();
        this.developMap = parcel.readString();
        this.customMap = parcel.readString();
        this.privateMap = parcel.readString();
        this.appID = parcel.readString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        writeToParcel(parcel);
    }

    public void setData(int i, String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8) {
        this.type = i;
        this.compID = str;
        this.feature = str2;
        this.hitType = str3;
        this.compVer = str4;
        this.compManufacture = str5;
        this.developMap = str6;
        this.customMap = str7;
        this.privateMap = str8;
        this.appID = "";
    }

    public void setData(int i, String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9) {
        this.type = i;
        this.compID = str;
        this.feature = str2;
        this.hitType = str3;
        this.compVer = str4;
        this.compManufacture = str5;
        this.developMap = str6;
        this.customMap = str7;
        this.privateMap = str8;
        this.appID = str9;
    }

    public int getType() {
        return this.type;
    }

    public String getCompID() {
        return this.compID;
    }

    public String getCompVer() {
        return this.compVer;
    }

    public String getCompManufacture() {
        return this.compManufacture;
    }

    public String getHitType() {
        return this.hitType;
    }

    public String getFeature() {
        return this.feature;
    }

    public String getDevelopMap() {
        return this.developMap;
    }

    public String getCustomMap() {
        return this.customMap;
    }

    public String getPrivateMap() {
        return this.privateMap;
    }

    public String getAppId() {
        return this.appID;
    }
}
