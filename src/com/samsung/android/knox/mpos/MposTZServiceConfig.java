package com.samsung.android.knox.mpos;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
public class MposTZServiceConfig implements Parcelable {
    private static final String QSEE_UNKNOWN_PROCESS = "unknown";
    private static final String QSEE_UNKNOWN_ROOT = "unknown";
    private static final String TBASE_UNKNOWN_PROCESS = "ffffffff000000000000000000000000";
    private static final String TBASE_UNKNOWN_ROOT = "0";
    private static final String UNKNOWN_TA_TECHNOLOGY = "unknown";
    public int maxRecvRespSize;
    public int maxSendCmdSize;
    public String processName;
    public String rootName;
    public String taTechnology;
    private static final boolean bQC = Build.BOARD.matches("(?i)(msm[a-z0-9]*)|(sdm[a-z0-9]*)");
    public static final Parcelable.Creator<MposTZServiceConfig> CREATOR = new Parcelable.Creator<MposTZServiceConfig>() { // from class: com.samsung.android.knox.mpos.MposTZServiceConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MposTZServiceConfig createFromParcel(Parcel parcel) {
            return new MposTZServiceConfig(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MposTZServiceConfig[] newArray(int i) {
            return new MposTZServiceConfig[i];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public MposTZServiceConfig(int i, int i2, String str, String str2, String str3) {
        this.maxSendCmdSize = i;
        this.maxRecvRespSize = i2;
        this.taTechnology = str;
        this.rootName = str2;
        this.processName = str3;
    }

    private MposTZServiceConfig(Parcel parcel) {
        readFromParcel(parcel);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.maxSendCmdSize);
        parcel.writeInt(this.maxRecvRespSize);
        parcel.writeString(this.taTechnology);
        parcel.writeString(this.rootName);
        parcel.writeString(this.processName);
    }

    public void readFromParcel(Parcel parcel) {
        this.maxSendCmdSize = parcel.readInt();
        this.maxRecvRespSize = parcel.readInt();
        this.taTechnology = parcel.readString();
        this.rootName = parcel.readString();
        this.processName = parcel.readString();
    }
}
