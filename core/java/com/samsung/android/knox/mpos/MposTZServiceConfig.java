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
        public MposTZServiceConfig createFromParcel(Parcel in) {
            return new MposTZServiceConfig(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MposTZServiceConfig[] newArray(int size) {
            return new MposTZServiceConfig[size];
        }
    };

    public MposTZServiceConfig(int maxSendCmdSize, int maxRecvRespSize, String taTechnology, String rootName, String processName) {
        this.maxSendCmdSize = maxSendCmdSize;
        this.maxRecvRespSize = maxRecvRespSize;
        this.taTechnology = taTechnology;
        this.rootName = rootName;
        this.processName = processName;
    }

    private MposTZServiceConfig(Parcel in) {
        readFromParcel(in);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flag) {
        out.writeInt(this.maxSendCmdSize);
        out.writeInt(this.maxRecvRespSize);
        out.writeString(this.taTechnology);
        out.writeString(this.rootName);
        out.writeString(this.processName);
    }

    public void readFromParcel(Parcel in) {
        this.maxSendCmdSize = in.readInt();
        this.maxRecvRespSize = in.readInt();
        this.taTechnology = in.readString();
        this.rootName = in.readString();
        this.processName = in.readString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
