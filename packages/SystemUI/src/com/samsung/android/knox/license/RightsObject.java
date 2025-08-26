package com.samsung.android.knox.license;

import android.os.Parcel;
import android.os.Parcelable;
import java.io.Serializable;
import java.util.List;

/* loaded from: classes4.dex */
public class RightsObject implements Parcelable, Serializable {
    public static final Parcelable.Creator<RightsObject> CREATOR = new Parcelable.Creator<RightsObject>() { // from class: com.samsung.android.knox.license.RightsObject.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RightsObject createFromParcel(Parcel parcel) {
            return new RightsObject(parcel, 0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RightsObject[] newArray(int i) {
            return new RightsObject[i];
        }
    };
    private static final long serialVersionUID = 1;
    private long expiryDate;
    private String instanceId;
    private long issueDate;
    private String licenseType;
    private List<String> permissions;
    private String serverUrl;
    private String state;
    private long uploadFrequencyTime;
    private long uploadTime;

    public /* synthetic */ RightsObject(Parcel parcel, int i) {
        this(parcel);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public long getExpiryDate() {
        return this.expiryDate;
    }

    public String getInstanceId() {
        return this.instanceId;
    }

    public long getIssueDate() {
        return this.issueDate;
    }

    public String getLicenseType() {
        return this.licenseType;
    }

    public List<String> getPermissions() {
        return this.permissions;
    }

    public String getServerUrl() {
        return this.serverUrl;
    }

    public String getState() {
        return this.state;
    }

    public long getUploadFrequencyTime() {
        return this.uploadFrequencyTime;
    }

    public long getUploadTime() {
        return this.uploadTime;
    }

    public void readFromParcel(Parcel parcel) {
        this.instanceId = parcel.readString();
        this.state = parcel.readString();
        this.issueDate = parcel.readLong();
        this.expiryDate = parcel.readLong();
        this.licenseType = parcel.readString();
        this.permissions = parcel.createStringArrayList();
        this.uploadFrequencyTime = parcel.readLong();
        this.uploadTime = parcel.readLong();
        this.serverUrl = parcel.readString();
    }

    public void setExpiryDate(long j) {
        this.expiryDate = j;
    }

    public void setInstanceId(String str) {
        this.instanceId = str;
    }

    public void setIssueDate(long j) {
        this.issueDate = j;
    }

    public void setLicenseType(String str) {
        this.licenseType = str;
    }

    public void setPermissions(List<String> list) {
        this.permissions = list;
    }

    public void setServerUrl(String str) {
        this.serverUrl = str;
    }

    public void setState(String str) {
        this.state = str;
    }

    public void setUploadFrequencyTime(long j) {
        this.uploadFrequencyTime = j;
    }

    public void setUploadTime(long j) {
        this.uploadTime = j;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.instanceId);
        parcel.writeString(this.state);
        parcel.writeLong(this.issueDate);
        parcel.writeLong(this.expiryDate);
        parcel.writeString(this.licenseType);
        parcel.writeStringList(this.permissions);
        parcel.writeLong(this.uploadFrequencyTime);
        parcel.writeLong(this.uploadTime);
        parcel.writeString(this.serverUrl);
    }

    private RightsObject(Parcel parcel) {
        readFromParcel(parcel);
    }

    public RightsObject() {
    }
}
