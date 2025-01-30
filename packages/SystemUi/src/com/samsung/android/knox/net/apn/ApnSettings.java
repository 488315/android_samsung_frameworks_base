package com.samsung.android.knox.net.apn;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class ApnSettings implements Parcelable {
    public static final Parcelable.Creator<ApnSettings> CREATOR = new Parcelable.Creator<ApnSettings>() { // from class: com.samsung.android.knox.net.apn.ApnSettings.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final ApnSettings[] newArray(int i) {
            return new ApnSettings[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final ApnSettings createFromParcel(Parcel parcel) {
            return new ApnSettings(parcel, 0);
        }

        @Override // android.os.Parcelable.Creator
        public final ApnSettings[] newArray(int i) {
            return new ApnSettings[i];
        }
    };
    public static final String MVNO_GID = "gid";
    public static final String MVNO_IMSI = "imsi";
    public static final String MVNO_NONE = "";
    public static final String MVNO_SPN = "spn";
    public static final String PROTOCOL_IPV4 = "IP";
    public static final String PROTOCOL_IPV4_IPV6 = "IPV4V6";
    public static final String PROTOCOL_IPV6 = "IPV6";
    public String apn;
    public int authType;

    /* renamed from: id */
    public long f488id;
    public String mcc;
    public String mmsPort;
    public String mmsProxy;
    public String mmsc;
    public String mnc;
    public String mvno_type;
    public String mvno_value;
    public String name;
    public String password;
    public int port;
    public String protocol;
    public String proxy;
    public String roamingProtocol;
    public String server;
    public String type;
    public String user;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class Builder {
        public String mApnName;
        public int mAuthType;
        public long mId;
        public String mMcc;
        public String mMmsProxyAddress;
        public int mMmsProxyPort;
        public String mMmsc;
        public String mMnc;
        public String mMvnoType;
        public String mMvnoValue;
        public String mName;
        public String mPassword;
        public String mProtocol;
        public String mProxyAddress;
        public int mProxyPort;
        public String mRoamingProtocol;
        public String mServer;
        public String mType;
        public String mUser;

        public final ApnSettings build() {
            if (TextUtils.isEmpty(this.mApnName) || TextUtils.isEmpty(this.mName)) {
                return null;
            }
            return new ApnSettings(this, 0);
        }

        public final Builder setApnName(String str) {
            this.mApnName = str;
            return this;
        }

        public final Builder setAuthType(int i) {
            this.mAuthType = i;
            return this;
        }

        public final Builder setId(long j) {
            this.mId = j;
            return this;
        }

        public final Builder setMcc(String str) {
            this.mMcc = str;
            return this;
        }

        public final Builder setMmsProxyAddress(String str) {
            this.mMmsProxyAddress = str;
            return this;
        }

        public final Builder setMmsProxyPort(int i) {
            this.mMmsProxyPort = i;
            return this;
        }

        public final Builder setMmsc(String str) {
            this.mMmsc = str;
            return this;
        }

        public final Builder setMnc(String str) {
            this.mMnc = str;
            return this;
        }

        public final Builder setMvnoType(String str) {
            this.mMvnoType = str;
            return this;
        }

        public final Builder setMvnoValue(String str) {
            this.mMvnoValue = str;
            return this;
        }

        public final Builder setName(String str) {
            this.mName = str;
            return this;
        }

        public final Builder setPassword(String str) {
            this.mPassword = str;
            return this;
        }

        public final Builder setProtocol(String str) {
            this.mProtocol = str;
            return this;
        }

        public final Builder setProxyAddress(String str) {
            this.mProxyAddress = str;
            return this;
        }

        public final Builder setProxyPort(int i) {
            this.mProxyPort = i;
            return this;
        }

        public final Builder setRoamingProtocol(String str) {
            this.mRoamingProtocol = str;
            return this;
        }

        public final Builder setServer(String str) {
            this.mServer = str;
            return this;
        }

        public final Builder setType(String str) {
            this.mType = str;
            return this;
        }

        public final Builder setUser(String str) {
            this.mUser = str;
            return this;
        }
    }

    public /* synthetic */ ApnSettings(Parcel parcel, int i) {
        this(parcel);
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    public final String getApn() {
        return this.apn;
    }

    public final int getAuthType() {
        return this.authType;
    }

    public final long getId() {
        return this.f488id;
    }

    public final String getMcc() {
        return this.mcc;
    }

    public final String getMmsPort() {
        return this.mmsPort;
    }

    public final String getMmsProxy() {
        return this.mmsProxy;
    }

    public final String getMmsc() {
        return this.mmsc;
    }

    public final String getMnc() {
        return this.mnc;
    }

    public final String getName() {
        return this.name;
    }

    public final String getPassword() {
        return this.password;
    }

    public final int getPort() {
        return this.port;
    }

    public final String getProxy() {
        return this.proxy;
    }

    public final String getServer() {
        return this.server;
    }

    public final String getType() {
        return this.type;
    }

    public final String getUser() {
        return this.user;
    }

    public final void readFromParcel(Parcel parcel) {
        this.f488id = parcel.readLong();
        this.name = parcel.readString();
        this.apn = parcel.readString();
        this.mcc = parcel.readString();
        this.mnc = parcel.readString();
        this.user = parcel.readString();
        this.server = parcel.readString();
        this.password = parcel.readString();
        this.proxy = parcel.readString();
        this.port = parcel.readInt();
        this.mmsProxy = parcel.readString();
        this.mmsPort = parcel.readString();
        this.mmsc = parcel.readString();
        this.type = parcel.readString();
        this.authType = parcel.readInt();
        this.protocol = parcel.readString();
        this.roamingProtocol = parcel.readString();
        this.mvno_type = parcel.readString();
        this.mvno_value = parcel.readString();
    }

    public final void setApn(String str) {
        this.apn = str;
    }

    public final void setAuthType(int i) {
        this.authType = i;
    }

    public final void setId(long j) {
        this.f488id = j;
    }

    public final void setMcc(String str) {
        this.mcc = str;
    }

    public final void setMmsPort(String str) {
        this.mmsPort = str;
    }

    public final void setMmsProxy(String str) {
        this.mmsProxy = str;
    }

    public final void setMmsc(String str) {
        this.mmsc = str;
    }

    public final void setMnc(String str) {
        this.mnc = str;
    }

    public final void setName(String str) {
        this.name = str;
    }

    public final void setPassword(String str) {
        this.password = str;
    }

    public final void setPort(int i) {
        this.port = i;
    }

    public final void setProxy(String str) {
        this.proxy = str;
    }

    public final void setServer(String str) {
        this.server = str;
    }

    public final void setType(String str) {
        this.type = str;
    }

    public final void setUser(String str) {
        this.user = str;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.f488id);
        parcel.writeString(this.name);
        parcel.writeString(this.apn);
        parcel.writeString(this.mcc);
        parcel.writeString(this.mnc);
        parcel.writeString(this.user);
        parcel.writeString(this.server);
        parcel.writeString(this.password);
        parcel.writeString(this.proxy);
        parcel.writeInt(this.port);
        parcel.writeString(this.mmsProxy);
        parcel.writeString(this.mmsPort);
        parcel.writeString(this.mmsc);
        parcel.writeString(this.type);
        parcel.writeInt(this.authType);
        parcel.writeString(this.protocol);
        parcel.writeString(this.roamingProtocol);
        parcel.writeString(this.mvno_type);
        parcel.writeString(this.mvno_value);
    }

    public /* synthetic */ ApnSettings(Builder builder, int i) {
        this(builder);
    }

    private ApnSettings(Parcel parcel) {
        this.f488id = -1L;
        this.name = "";
        this.apn = "";
        this.mcc = "";
        this.mnc = "";
        this.user = "";
        this.server = "";
        this.password = "";
        this.proxy = "";
        this.port = -1;
        this.mmsProxy = "";
        this.mmsPort = "";
        this.mmsc = "";
        this.type = "";
        this.authType = -1;
        this.protocol = PROTOCOL_IPV4;
        this.roamingProtocol = PROTOCOL_IPV4;
        this.mvno_type = "";
        this.mvno_value = "";
        readFromParcel(parcel);
    }

    public ApnSettings() {
        this.f488id = -1L;
        this.name = "";
        this.apn = "";
        this.mcc = "";
        this.mnc = "";
        this.user = "";
        this.server = "";
        this.password = "";
        this.proxy = "";
        this.port = -1;
        this.mmsProxy = "";
        this.mmsPort = "";
        this.mmsc = "";
        this.type = "";
        this.authType = -1;
        this.protocol = PROTOCOL_IPV4;
        this.roamingProtocol = PROTOCOL_IPV4;
        this.mvno_type = "";
        this.mvno_value = "";
    }

    private ApnSettings(Builder builder) {
        this.f488id = -1L;
        this.name = "";
        this.apn = "";
        this.mcc = "";
        this.mnc = "";
        this.user = "";
        this.server = "";
        this.password = "";
        this.proxy = "";
        this.port = -1;
        this.mmsProxy = "";
        this.mmsPort = "";
        this.mmsc = "";
        this.type = "";
        this.authType = -1;
        this.protocol = PROTOCOL_IPV4;
        this.roamingProtocol = PROTOCOL_IPV4;
        this.mvno_type = "";
        this.mvno_value = "";
        this.f488id = builder.mId;
        this.name = builder.mName;
        this.apn = builder.mApnName;
        this.mcc = builder.mMcc;
        this.mnc = builder.mMnc;
        this.user = builder.mUser;
        this.password = builder.mPassword;
        this.server = builder.mServer;
        this.proxy = builder.mProxyAddress;
        this.port = builder.mProxyPort;
        this.mmsc = builder.mMmsc;
        this.mmsProxy = builder.mMmsProxyAddress;
        this.mmsPort = Integer.toString(builder.mMmsProxyPort);
        this.type = builder.mType;
        this.authType = builder.mAuthType;
        this.mvno_type = builder.mMvnoType;
        this.mvno_value = builder.mMvnoValue;
        this.protocol = builder.mProtocol;
        this.roamingProtocol = builder.mRoamingProtocol;
    }
}
