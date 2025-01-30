package com.samsung.android.knox.net;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class ProxyProperties implements Parcelable {
    public static final Parcelable.Creator<ProxyProperties> CREATOR = new Parcelable.Creator<ProxyProperties>() { // from class: com.samsung.android.knox.net.ProxyProperties.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final ProxyProperties[] newArray(int i) {
            return new ProxyProperties[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final ProxyProperties createFromParcel(Parcel parcel) {
            return new ProxyProperties(parcel, 0);
        }

        @Override // android.os.Parcelable.Creator
        public final ProxyProperties[] newArray(int i) {
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
    public final int describeContents() {
        return 0;
    }

    public final List<AuthConfig> getAuthConfigList() {
        return this.mAuthConfigList;
    }

    public final List<String> getExclusionList() {
        return this.mExclusionList;
    }

    public final String getHostname() {
        return this.mHostname;
    }

    public final String getPacFileUrl() {
        return this.mPacFileUrl;
    }

    public final int getPortNumber() {
        return this.mPortNumber;
    }

    public final boolean isAuthenticationConfigured() {
        List<AuthConfig> list = this.mAuthConfigList;
        return (list == null || list.isEmpty()) ? false : true;
    }

    public final boolean isValid() {
        boolean z = !TextUtils.isEmpty(this.mPacFileUrl);
        boolean z2 = !TextUtils.isEmpty(this.mHostname);
        if (z && z2) {
            return false;
        }
        if (isAuthenticationConfigured()) {
            for (AuthConfig authConfig : this.mAuthConfigList) {
                if (authConfig == null || !authConfig.isValid()) {
                    return false;
                }
            }
        }
        return true;
    }

    public final void readFromParcel(Parcel parcel) {
        this.mHostname = parcel.readString();
        this.mPortNumber = parcel.readInt();
        parcel.readStringList(this.mExclusionList);
        this.mPacFileUrl = parcel.readString();
        parcel.readList(this.mAuthConfigList, AuthConfig.class.getClassLoader());
    }

    public final void setAuthConfigList(List<AuthConfig> list) {
        this.mAuthConfigList = list;
    }

    public final void setExclusionList(List<String> list) {
        this.mExclusionList = list;
    }

    public final void setHostname(String str) {
        this.mHostname = str;
    }

    public final void setPacFileUrl(String str) {
        this.mPacFileUrl = str;
    }

    public final void setPortNumber(int i) {
        this.mPortNumber = i;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
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
