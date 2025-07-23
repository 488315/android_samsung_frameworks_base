package com.samsung.android.wifi;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.samsung.android.graphics.imagefilter.ShaderAssembler;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes6.dex */
public class SemWifiConfiguration implements Parcelable {
    public static final int DISABLED_ASSOCIATION_REJECTED = 3;
    public static final int DISABLED_AUTHENTICATION_CA_CERTIFICATION_ERROR = 11;
    public static final int DISABLED_AUTHENTICATION_FAILURE = 2;
    public static final int DISABLED_AUTHENTICATION_UMTS_AUTN_ERROR = 12;
    public static final int DISABLED_CAPTIVE_PORTAL = 5;
    public static final int DISABLED_DHCP_FAILED = 4;
    public static final int DISABLED_NONE = 0;
    public static final int DISABLED_PERMANENTLY_NO_INTERNET = 7;
    public static final int DISABLED_PERMANENTLY_NO_INTERNET_INITIAL = 8;
    public static final int DISABLED_REASON_MAX = 13;
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
    public static final String[] networkDisableReasonStrings = {"DISABLED_NONE", "DISABLED_SUSPICIOUS_NETWORK", "DISABLED_AUTHENTICATION_FAILURE", "DISABLED_ASSOCIATION_REJECTED", "DISABLED_DHCP_FAILED", "DISABLED_CAPTIVE_PORTAL", "DISABLED_TEMPORARY_NO_INTERNET", "DISABLED_PERMANENTLY_NO_INTERNET", "DISABLED_PERMANENTLY_NO_INTERNET_INITIAL", "DISABLED_TEMPORARY_ELE_DETECTION", "DISABLED_TEMPORARY_SILENT_ROAMING", "DISABLED_AUTHENTICATION_CA_CERTIFICATION_ERROR", "DISABLED_AUTHENTICATION_UMTS_AUTN_ERROR"};
    public static final Parcelable.Creator<SemWifiConfiguration> CREATOR = new Parcelable.Creator<SemWifiConfiguration>() { // from class: com.samsung.android.wifi.SemWifiConfiguration.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemWifiConfiguration createFromParcel(Parcel parcel) {
            SemWifiConfiguration semWifiConfiguration = new SemWifiConfiguration();
            semWifiConfiguration.configKey = parcel.readString();
            semWifiConfiguration.networkScore = parcel.readInt();
            semWifiConfiguration.isCaptivePortal = parcel.readBoolean();
            semWifiConfiguration.isLockDown = parcel.readBoolean();
            semWifiConfiguration.isNoInternetAccessExpected = parcel.readBoolean();
            semWifiConfiguration.disableTimeByWcm = parcel.readLong();
            semWifiConfiguration.disableTimeByEle = parcel.readLong();
            semWifiConfiguration.altNetworkTargetRssi = parcel.readInt();
            semWifiConfiguration.networkDisableReason = parcel.readInt();
            semWifiConfiguration.creationTime = parcel.readLong();
            semWifiConfiguration.personalizedConnectionOption = parcel.readInt();
            return semWifiConfiguration;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemWifiConfiguration[] newArray(int i) {
            return new SemWifiConfiguration[i];
        }
    };

    @Retention(RetentionPolicy.SOURCE)
    public @interface SemNetworkDisableReason {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    SemWifiConfiguration() {
        this.configKey = "";
    }

    public SemWifiConfiguration(String str) {
        this.configKey = str;
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

    public boolean matches(SemWifiConfiguration semWifiConfiguration) {
        if (semWifiConfiguration == null) {
            return false;
        }
        return TextUtils.equals(this.configKey, semWifiConfiguration.configKey);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.configKey);
        sb.append(ShaderAssembler.NEWLINE);
        if (this.isLockDown) {
            sb.append(" locked");
        }
        if (this.isCaptivePortal) {
            sb.append(" captivePortal\n");
        }
        if (this.isNoInternetAccessExpected) {
            sb.append(" NoInternetAccessExpected\n");
        }
        int i = this.networkDisableReason;
        if (i >= 0 && i < 13) {
            sb.append(" disableReason: ");
            sb.append(networkDisableReasonStrings[this.networkDisableReason]);
            if (this.disableTimeByWcm != 0) {
                sb.append(" disableTimeByWcm: ");
                sb.append(this.disableTimeByWcm);
            }
            if (this.disableTimeByEle != 0) {
                sb.append(" disableTimeByEle: ");
                sb.append(this.disableTimeByEle);
            }
            if (this.altNetworkTargetRssi != 0) {
                sb.append(" altNetworkTargetRssi: ");
                sb.append(this.altNetworkTargetRssi);
            }
        }
        sb.append(" networkScore: ");
        sb.append(this.networkScore);
        sb.append("\n cTime: ");
        sb.append(this.creationTime);
        sb.append("\n personalizedConnectionOption: ");
        sb.append(Integer.toHexString(this.personalizedConnectionOption));
        sb.append(ShaderAssembler.NEWLINE);
        return sb.toString();
    }

    public int hashCode() {
        return this.configKey.hashCode();
    }

    public boolean equals(Object obj) {
        if (obj instanceof SemWifiConfiguration) {
            SemWifiConfiguration semWifiConfiguration = (SemWifiConfiguration) obj;
            if (matches(semWifiConfiguration) && this.networkScore == semWifiConfiguration.networkScore && this.isCaptivePortal == semWifiConfiguration.isCaptivePortal && this.isNoInternetAccessExpected == semWifiConfiguration.isNoInternetAccessExpected && this.networkDisableReason == semWifiConfiguration.networkDisableReason && this.isLockDown == semWifiConfiguration.isLockDown) {
                return true;
            }
        }
        return false;
    }

    public SemWifiConfiguration(SemWifiConfiguration semWifiConfiguration) {
        this.configKey = semWifiConfiguration.configKey;
        this.networkScore = semWifiConfiguration.networkScore;
        this.isCaptivePortal = semWifiConfiguration.isCaptivePortal;
        this.isLockDown = semWifiConfiguration.isLockDown;
        this.isNoInternetAccessExpected = semWifiConfiguration.isNoInternetAccessExpected;
        this.disableTimeByWcm = semWifiConfiguration.disableTimeByWcm;
        this.disableTimeByEle = semWifiConfiguration.disableTimeByEle;
        this.altNetworkTargetRssi = semWifiConfiguration.altNetworkTargetRssi;
        this.networkDisableReason = semWifiConfiguration.networkDisableReason;
        this.creationTime = semWifiConfiguration.creationTime;
        this.personalizedConnectionOption = semWifiConfiguration.personalizedConnectionOption;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.configKey);
        parcel.writeInt(this.networkScore);
        parcel.writeBoolean(this.isCaptivePortal);
        parcel.writeBoolean(this.isLockDown);
        parcel.writeBoolean(this.isNoInternetAccessExpected);
        parcel.writeLong(this.disableTimeByWcm);
        parcel.writeLong(this.disableTimeByEle);
        parcel.writeInt(this.altNetworkTargetRssi);
        parcel.writeInt(this.networkDisableReason);
        parcel.writeLong(this.creationTime);
        parcel.writeInt(this.personalizedConnectionOption);
    }
}
