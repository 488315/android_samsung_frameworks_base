package android.os;

import android.os.Parcelable;

/* loaded from: classes3.dex */
public class HWParamParcel implements Parcelable {
    public static final Parcelable.Creator<HWParamParcel> CREATOR = new Parcelable.Creator<HWParamParcel>() { // from class: android.os.HWParamParcel.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public HWParamParcel createFromParcel(Parcel in) {
            return new HWParamParcel(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public HWParamParcel[] newArray(int size) {
            return new HWParamParcel[size];
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

    private HWParamParcel(Parcel in) {
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
        readFromParcel(in);
    }

    public void writeToParcel(Parcel out) {
        out.writeInt(this.type);
        out.writeString(this.compID);
        out.writeString(this.feature);
        out.writeString(this.hitType);
        out.writeString(this.compVer);
        out.writeString(this.compManufacture);
        out.writeString(this.developMap);
        out.writeString(this.customMap);
        out.writeString(this.privateMap);
        out.writeString(this.appID);
    }

    public void readFromParcel(Parcel in) {
        this.type = in.readInt();
        this.compID = in.readString();
        this.feature = in.readString();
        this.hitType = in.readString();
        this.compVer = in.readString();
        this.compManufacture = in.readString();
        this.developMap = in.readString();
        this.customMap = in.readString();
        this.privateMap = in.readString();
        this.appID = in.readString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flag) {
        writeToParcel(out);
    }

    public void setData(int type, String compID, String feature, String hitType, String compVer, String compManufacture, String developMap, String customMap, String privateMap) {
        this.type = type;
        this.compID = compID;
        this.feature = feature;
        this.hitType = hitType;
        this.compVer = compVer;
        this.compManufacture = compManufacture;
        this.developMap = developMap;
        this.customMap = customMap;
        this.privateMap = privateMap;
        this.appID = "";
    }

    public void setData(int type, String compID, String feature, String hitType, String compVer, String compManufacture, String developMap, String customMap, String privateMap, String appID) {
        this.type = type;
        this.compID = compID;
        this.feature = feature;
        this.hitType = hitType;
        this.compVer = compVer;
        this.compManufacture = compManufacture;
        this.developMap = developMap;
        this.customMap = customMap;
        this.privateMap = privateMap;
        this.appID = appID;
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
