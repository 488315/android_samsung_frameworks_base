package com.samsung.android.knox.net;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class ProxyProperties implements Parcelable {
    public static final Parcelable.Creator<ProxyProperties> CREATOR = new Parcelable.Creator<ProxyProperties>() { // from class: com.samsung.android.knox.net.ProxyProperties.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ProxyProperties createFromParcel(Parcel parcel) {
            return new ProxyProperties(parcel, 0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ProxyProperties[] newArray(int i) {
            return new ProxyProperties[i];
        }
    };
    public List<AuthConfig> mAuthConfigList;
    public List<String> mExclusionList;
    public String mHostname;
    public String mPacFileUrl;
    public int mPortNumber;

    public /* synthetic */ ProxyProperties(Parcel parcel, int i) {
        this(parcel);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public List<AuthConfig> getAuthConfigList() {
        return this.mAuthConfigList;
    }

    public List<String> getExclusionList() {
        return this.mExclusionList;
    }

    public String getHostname() {
        return this.mHostname;
    }

    public String getPacFileUrl() {
        return this.mPacFileUrl;
    }

    public int getPortNumber() {
        return this.mPortNumber;
    }

    public boolean isAuthenticationConfigured() {
        List<AuthConfig> list = this.mAuthConfigList;
        return (list == null || list.isEmpty()) ? false : true;
    }

    public boolean isValid() {
        boolean isEmpty = TextUtils.isEmpty(this.mPacFileUrl);
        boolean isEmpty2 = TextUtils.isEmpty(this.mHostname);
        if (!isEmpty && !isEmpty2) {
            return false;
        }
        if (!isAuthenticationConfigured()) {
            return true;
        }
        for (AuthConfig authConfig : this.mAuthConfigList) {
            if (authConfig == null || !authConfig.isValid()) {
                return false;
            }
        }
        return true;
    }

    public void readFromParcel(Parcel parcel) {
        this.mHostname = parcel.readString();
        this.mPortNumber = parcel.readInt();
        parcel.readStringList(this.mExclusionList);
        this.mPacFileUrl = parcel.readString();
        parcel.readList(this.mAuthConfigList, AuthConfig.class.getClassLoader());
    }

    public void setAuthConfigList(List<AuthConfig> list) {
        this.mAuthConfigList = list;
    }

    public void setExclusionList(List<String> list) {
        this.mExclusionList = list;
    }

    public void setHostname(String str) {
        this.mHostname = str;
    }

    public void setPacFileUrl(String str) {
        this.mPacFileUrl = str;
    }

    public void setPortNumber(int i) {
        this.mPortNumber = i;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mHostname);
        parcel.writeInt(this.mPortNumber);
        parcel.writeStringList(this.mExclusionList);
        parcel.writeString(this.mPacFileUrl);
        parcel.writeList(this.mAuthConfigList);
    }

    public ProxyProperties() {
        this.mPortNumber = -1;
        this.mExclusionList = new ArrayList();
        this.mAuthConfigList = new ArrayList();
    }

    private ProxyProperties(Parcel parcel) {
        this.mPortNumber = -1;
        this.mExclusionList = new ArrayList();
        this.mAuthConfigList = new ArrayList();
        readFromParcel(parcel);
    }
}
