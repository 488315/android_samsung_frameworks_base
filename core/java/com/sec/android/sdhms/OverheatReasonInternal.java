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
        public OverheatReasonInternal createFromParcel(Parcel in) {
            return new OverheatReasonInternal(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public OverheatReasonInternal[] newArray(int size) {
            return new OverheatReasonInternal[size];
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

    public OverheatReasonInternal(long begin, long end, int charger, String camera, String game, String navigation, String network, String process, int environment) {
        this.beginTime = begin;
        this.endTime = end;
        this.chargerType = charger;
        this.cameraApp = camera;
        this.gameApp = game;
        this.navigationApp = navigation;
        this.networkApp = network;
        this.processApp = process;
        this.environmentType = environment;
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

    protected OverheatReasonInternal(Parcel in) {
        this.beginTime = in.readLong();
        this.endTime = in.readLong();
        this.chargerType = in.readInt();
        this.cameraApp = in.readString16NoHelper();
        this.gameApp = in.readString16NoHelper();
        this.navigationApp = in.readString16NoHelper();
        this.networkApp = in.readString16NoHelper();
        this.processApp = in.readString16NoHelper();
        this.environmentType = in.readInt();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.beginTime);
        dest.writeLong(this.endTime);
        dest.writeInt(this.chargerType);
        dest.writeString16NoHelper(this.cameraApp);
        dest.writeString16NoHelper(this.gameApp);
        dest.writeString16NoHelper(this.navigationApp);
        dest.writeString16NoHelper(this.networkApp);
        dest.writeString16NoHelper(this.processApp);
        dest.writeInt(this.environmentType);
    }
}
