package com.samsung.android.knox.license;

import android.os.Parcel;
import android.os.Parcelable;
import java.io.Serializable;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class RightsObject implements Parcelable, Serializable {
    public static final Parcelable.Creator<RightsObject> CREATOR = new Parcelable.Creator<RightsObject>() { // from class: com.samsung.android.knox.license.RightsObject.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final RightsObject[] newArray(int i) {
            return new RightsObject[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final RightsObject createFromParcel(Parcel parcel) {
            return new RightsObject(parcel, 0);
        }

        @Override // android.os.Parcelable.Creator
        public final RightsObject[] newArray(int i) {
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
    public final int describeContents() {
        return 0;
    }

    public final long getExpiryDate() {
        return this.expiryDate;
    }

    public final String getInstanceId() {
        return this.instanceId;
    }

    public final long getIssueDate() {
        return this.issueDate;
    }

    public final String getLicenseType() {
        return this.licenseType;
    }

    public final List<String> getPermissions() {
        return this.permissions;
    }

    public final String getServerUrl() {
        return this.serverUrl;
    }

    public final String getState() {
        return this.state;
    }

    public final long getUploadFrequencyTime() {
        return this.uploadFrequencyTime;
    }

    public final long getUploadTime() {
        return this.uploadTime;
    }

    public final void readFromParcel(Parcel parcel) {
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

    public final void setExpiryDate(long j) {
        this.expiryDate = j;
    }

    public final void setInstanceId(String str) {
        this.instanceId = str;
    }

    public final void setIssueDate(long j) {
        this.issueDate = j;
    }

    public final void setLicenseType(String str) {
        this.licenseType = str;
    }

    public final void setPermissions(List<String> list) {
        this.permissions = list;
    }

    public final void setServerUrl(String str) {
        this.serverUrl = str;
    }

    public final void setState(String str) {
        this.state = str;
    }

    public final void setUploadFrequencyTime(long j) {
        this.uploadFrequencyTime = j;
    }

    public final void setUploadTime(long j) {
        this.uploadTime = j;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
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
