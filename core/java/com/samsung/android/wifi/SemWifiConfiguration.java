package com.samsung.android.wifi;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes6.dex */
public class SemWifiConfiguration implements Parcelable {
    public static final int DISABLED_ASSOCIATION_REJECTED = 3;
    public static final int DISABLED_AUTHENTICATION_CA_CERTIFICATION_ERROR = 11;
    public static final int DISABLED_AUTHENTICATION_FAILURE = 2;
    public static final int DISABLED_CAPTIVE_PORTAL = 5;
    public static final int DISABLED_DHCP_FAILED = 4;
    public static final int DISABLED_NONE = 0;
    public static final int DISABLED_PERMANENTLY_NO_INTERNET = 7;
    public static final int DISABLED_PERMANENTLY_NO_INTERNET_INITIAL = 8;
    public static final int DISABLED_REASON_MAX = 12;
    public static final int DISABLED_SUSPICIOUS_NETWORK = 1;
    public static final int DISABLED_TEMPORARY_ELE_DETECTION = 9;
    public static final int DISABLED_TEMPORARY_NO_INTERNET = 6;
    public static final int DISABLED_TEMPORARY_SILENT_ROAMING = 10;
    public static final int PERSONALIZED_CONN_BTM_SHIFT_VALUE = 0;
    public static final int PERSONALIZED_CONN_OPTION_BITMASK = 15;
    public static final int PERSONALIZED_CONN_OPTION_DEFAULT = 0;
    public static final int PERSONALIZED_CONN_OPTION_DETECTED_BIGDATA = 1;
    public static final int PERSONALIZED_CONN_OPTION_ENABLED = 3;
    public static final int PERSONALIZED_CONN_OPTION_ENABLED_BIGDATA = 2;
    public static final int PERSONALIZED_CONN_OPTION_USER_DISABLED = 4;
    public int altNetworkTargetRssi;
    public String configKey;
    public long creationTime;
    public long disableTimeByEle;
    public long disableTimeByWcm;
    public boolean isCaptivePortal;
    public boolean isLockDown;
    public boolean isNoInternetAccessExpected;
    public int networkDisableReason;
    public int networkScore;
    public int personalizedConnectionOption;
    public static final String[] networkDisableReasonStrings = {"DISABLED_NONE", "DISABLED_SUSPICIOUS_NETWORK", "DISABLED_AUTHENTICATION_FAILURE", "DISABLED_ASSOCIATION_REJECTED", "DISABLED_DHCP_FAILED", "DISABLED_CAPTIVE_PORTAL", "DISABLED_TEMPORARY_NO_INTERNET", "DISABLED_PERMANENTLY_NO_INTERNET", "DISABLED_PERMANENTLY_NO_INTERNET_INITIAL", "DISABLED_TEMPORARY_ELE_DETECTION", "DISABLED_TEMPORARY_SILENT_ROAMING", "DISABLED_AUTHENTICATION_CA_CERTIFICATION_ERROR"};
    public static final Parcelable.Creator<SemWifiConfiguration> CREATOR = new Parcelable.Creator<SemWifiConfiguration>() { // from class: com.samsung.android.wifi.SemWifiConfiguration.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemWifiConfiguration createFromParcel(Parcel in) {
            SemWifiConfiguration config = new SemWifiConfiguration();
            config.configKey = in.readString();
            config.networkScore = in.readInt();
            config.isCaptivePortal = in.readBoolean();
            config.isLockDown = in.readBoolean();
            config.isNoInternetAccessExpected = in.readBoolean();
            config.disableTimeByWcm = in.readLong();
            config.disableTimeByEle = in.readLong();
            config.altNetworkTargetRssi = in.readInt();
            config.networkDisableReason = in.readInt();
            config.creationTime = in.readLong();
            config.personalizedConnectionOption = in.readInt();
            return config;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemWifiConfiguration[] newArray(int size) {
            return new SemWifiConfiguration[size];
        }
    };

    @Retention(RetentionPolicy.SOURCE)
    public @interface SemNetworkDisableReason {
    }

    SemWifiConfiguration() {
        this.configKey = "";
    }

    public SemWifiConfiguration(String configKey) {
        this.configKey = configKey;
        this.networkScore = 0;
        this.isCaptivePortal = false;
        this.isLockDown = false;
        this.isNoInternetAccessExpected = false;
        this.disableTimeByWcm = 0L;
        this.disableTimeByEle = 0L;
        this.altNetworkTargetRssi = 0;
        this.networkDisableReason = 0;
        this.creationTime = 0L;
        this.personalizedConnectionOption = 0;
    }

    public int getNetworkScore() {
        return this.networkScore;
    }

    public boolean isCaptivePortal() {
        return this.isCaptivePortal;
    }

    public boolean isNoInternetAccessExpected() {
        return this.isNoInternetAccessExpected;
    }

    public boolean isLockDown() {
        return this.isLockDown;
    }

    public boolean matches(SemWifiConfiguration other) {
        if (other == null) {
            return false;
        }
        return TextUtils.equals(this.configKey, other.configKey);
    }

    public String toString() {
        StringBuilder sbuf = new StringBuilder();
        sbuf.append(this.configKey).append("\n");
        if (this.isLockDown) {
            sbuf.append(" locked");
        }
        if (this.isCaptivePortal) {
            sbuf.append(" captivePortal").append("\n");
        }
        if (this.isNoInternetAccessExpected) {
            sbuf.append(" NoInternetAccessExpected").append("\n");
        }
        if (this.networkDisableReason >= 0 && this.networkDisableReason < 12) {
            sbuf.append(" disableReason: ").append(networkDisableReasonStrings[this.networkDisableReason]);
            if (this.disableTimeByWcm != 0) {
                sbuf.append(" disableTimeByWcm: ").append(this.disableTimeByWcm);
            }
            if (this.disableTimeByEle != 0) {
                sbuf.append(" disableTimeByEle: ").append(this.disableTimeByEle);
            }
            if (this.altNetworkTargetRssi != 0) {
                sbuf.append(" altNetworkTargetRssi: ").append(this.altNetworkTargetRssi);
            }
        }
        sbuf.append(" networkScore: ").append(this.networkScore).append("\n");
        sbuf.append(" cTime: ").append(this.creationTime).append("\n");
        sbuf.append(" personalizedConnectionOption: ").append(Integer.toHexString(this.personalizedConnectionOption)).append("\n");
        return sbuf.toString();
    }

    public int hashCode() {
        return this.configKey.hashCode();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof SemWifiConfiguration)) {
            return false;
        }
        SemWifiConfiguration other = (SemWifiConfiguration) obj;
        return matches(other) && this.networkScore == other.networkScore && this.isCaptivePortal == other.isCaptivePortal && this.isNoInternetAccessExpected == other.isNoInternetAccessExpected && this.networkDisableReason == other.networkDisableReason && this.isLockDown == other.isLockDown;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SemWifiConfiguration(SemWifiConfiguration source) {
        this.configKey = source.configKey;
        this.networkScore = source.networkScore;
        this.isCaptivePortal = source.isCaptivePortal;
        this.isLockDown = source.isLockDown;
        this.isNoInternetAccessExpected = source.isNoInternetAccessExpected;
        this.disableTimeByWcm = source.disableTimeByWcm;
        this.disableTimeByEle = source.disableTimeByEle;
        this.altNetworkTargetRssi = source.altNetworkTargetRssi;
        this.networkDisableReason = source.networkDisableReason;
        this.creationTime = source.creationTime;
        this.personalizedConnectionOption = source.personalizedConnectionOption;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.configKey);
        dest.writeInt(this.networkScore);
        dest.writeBoolean(this.isCaptivePortal);
        dest.writeBoolean(this.isLockDown);
        dest.writeBoolean(this.isNoInternetAccessExpected);
        dest.writeLong(this.disableTimeByWcm);
        dest.writeLong(this.disableTimeByEle);
        dest.writeInt(this.altNetworkTargetRssi);
        dest.writeInt(this.networkDisableReason);
        dest.writeLong(this.creationTime);
        dest.writeInt(this.personalizedConnectionOption);
    }
}
