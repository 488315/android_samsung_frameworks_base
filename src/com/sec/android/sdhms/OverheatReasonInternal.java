package com.sec.android.sdhms;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
public class OverheatReasonInternal implements Parcelable {
    public static final int CHARGER_TYPE_AC = 1;
    public static final int CHARGER_TYPE_AFC = 8;
    public static final int CHARGER_TYPE_USB = 2;
    public static final int CHARGER_TYPE_WIRELESS = 4;
    public static final Parcelable.Creator<OverheatReasonInternal> CREATOR = new Parcelable.Creator<OverheatReasonInternal>() { // from class: com.sec.android.sdhms.OverheatReasonInternal.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public OverheatReasonInternal createFromParcel(Parcel parcel) {
            return new OverheatReasonInternal(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public OverheatReasonInternal[] newArray(int i) {
            return new OverheatReasonInternal[i];
        }
    };
    public static final int ENVIRONMENT_TYPE_AMBIENT = 4;
    public static final int ENVIRONMENT_TYPE_BLANKET = 2;
    public static final int ENVIRONMENT_TYPE_SUNLIGHT = 1;
    public static final int REASON_CHARGING = 1;
    public static final int REASON_ENVIRONMENT = 8;
    public static final int REASON_HIGH_NETWORK_USAGE = 2;
    public static final int REASON_HIGH_PROCESS_USAGE = 4;
    public static final int REASON_SCENARIO_CAMERA = 65536;
    public static final int REASON_SCENARIO_GAME = 131072;
    public static final int REASON_SCENARIO_NAVIGATION = 262144;
    private long beginTime;
    private String cameraApp;
    private int chargerType;
    private long endTime;
    private int environmentType;
    private String gameApp;
    private String navigationApp;
    private String networkApp;
    private String processApp;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public OverheatReasonInternal(long j, long j2, int i, String str, String str2, String str3, String str4, String str5, int i2) {
        this.beginTime = j;
        this.endTime = j2;
        this.chargerType = i;
        this.cameraApp = str;
        this.gameApp = str2;
        this.navigationApp = str3;
        this.networkApp = str4;
        this.processApp = str5;
        this.environmentType = i2;
    }

    public long getBeginTime() {
        return this.beginTime;
    }

    public long getEndTime() {
        return this.endTime;
    }

    public int getChargerType() {
        return this.chargerType;
    }

    public String getCameraApp() {
        return this.cameraApp;
    }

    public String getGameApp() {
        return this.gameApp;
    }

    public String getNavigationApp() {
        return this.navigationApp;
    }

    public String getNetworkApp() {
        return this.networkApp;
    }

    public String getProcessApp() {
        return this.processApp;
    }

    public int getEnvironmentType() {
        return this.environmentType;
    }

    protected OverheatReasonInternal(Parcel parcel) {
        this.beginTime = parcel.readLong();
        this.endTime = parcel.readLong();
        this.chargerType = parcel.readInt();
        this.cameraApp = parcel.readString16NoHelper();
        this.gameApp = parcel.readString16NoHelper();
        this.navigationApp = parcel.readString16NoHelper();
        this.networkApp = parcel.readString16NoHelper();
        this.processApp = parcel.readString16NoHelper();
        this.environmentType = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.beginTime);
        parcel.writeLong(this.endTime);
        parcel.writeInt(this.chargerType);
        parcel.writeString16NoHelper(this.cameraApp);
        parcel.writeString16NoHelper(this.gameApp);
        parcel.writeString16NoHelper(this.navigationApp);
        parcel.writeString16NoHelper(this.networkApp);
        parcel.writeString16NoHelper(this.processApp);
        parcel.writeInt(this.environmentType);
    }
}
