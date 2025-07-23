package android.content.pm;

import android.content.Context;
import android.os.BatteryManager;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Printer;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes.dex */
public class ServiceInfo extends ComponentInfo implements Parcelable {
    public static final Parcelable.Creator<ServiceInfo> CREATOR = new Parcelable.Creator<ServiceInfo>() { // from class: android.content.pm.ServiceInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ServiceInfo createFromParcel(Parcel parcel) {
            return new ServiceInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ServiceInfo[] newArray(int i) {
            return new ServiceInfo[i];
        }
    };
    public static final int FLAG_ALLOW_SHARED_ISOLATED_PROCESS = 16;
    public static final int FLAG_EXTERNAL_SERVICE = 4;
    public static final int FLAG_ISOLATED_PROCESS = 2;
    public static final int FLAG_SINGLE_USER = 1073741824;
    public static final int FLAG_STOP_WITH_TASK = 1;
    public static final int FLAG_SYSTEM_USER_ONLY = 536870912;
    public static final int FLAG_USE_APP_ZYGOTE = 8;
    public static final int FLAG_VISIBLE_TO_INSTANT_APP = 1048576;
    public static final int FOREGROUND_SERVICE_TYPES_MAX_INDEX = 30;
    public static final int FOREGROUND_SERVICE_TYPE_CAMERA = 64;
    public static final int FOREGROUND_SERVICE_TYPE_CONNECTED_DEVICE = 16;
    public static final int FOREGROUND_SERVICE_TYPE_DATA_SYNC = 1;
    public static final int FOREGROUND_SERVICE_TYPE_FILE_MANAGEMENT = 4096;
    public static final int FOREGROUND_SERVICE_TYPE_HEALTH = 256;
    public static final int FOREGROUND_SERVICE_TYPE_LOCATION = 8;
    public static final int FOREGROUND_SERVICE_TYPE_MANIFEST = -1;
    public static final int FOREGROUND_SERVICE_TYPE_MEDIA_PLAYBACK = 2;
    public static final int FOREGROUND_SERVICE_TYPE_MEDIA_PROCESSING = 8192;
    public static final int FOREGROUND_SERVICE_TYPE_MEDIA_PROJECTION = 32;
    public static final int FOREGROUND_SERVICE_TYPE_MICROPHONE = 128;

    @Deprecated
    public static final int FOREGROUND_SERVICE_TYPE_NONE = 0;
    public static final int FOREGROUND_SERVICE_TYPE_PHONE_CALL = 4;
    public static final int FOREGROUND_SERVICE_TYPE_REMOTE_MESSAGING = 512;
    public static final int FOREGROUND_SERVICE_TYPE_SHORT_SERVICE = 2048;
    public static final int FOREGROUND_SERVICE_TYPE_SPECIAL_USE = 1073741824;
    public static final int FOREGROUND_SERVICE_TYPE_SYSTEM_EXEMPTED = 1024;
    public int flags;
    public int mForegroundServiceType;
    public String permission;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ForegroundServiceType {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ServiceInfo() {
        this.mForegroundServiceType = 0;
    }

    public ServiceInfo(ServiceInfo serviceInfo) {
        super(serviceInfo);
        this.mForegroundServiceType = 0;
        this.permission = serviceInfo.permission;
        this.flags = serviceInfo.flags;
        this.mForegroundServiceType = serviceInfo.mForegroundServiceType;
    }

    public int getForegroundServiceType() {
        return this.mForegroundServiceType;
    }

    public void dump(Printer printer, String str) {
        dump(printer, str, 3);
    }

    void dump(Printer printer, String str, int i) {
        super.dumpFront(printer, str);
        printer.println(str + "permission=" + this.permission);
        printer.println(str + "flags=0x" + Integer.toHexString(this.flags));
        super.dumpBack(printer, str, i);
    }

    public String toString() {
        return "ServiceInfo{" + Integer.toHexString(System.identityHashCode(this)) + " " + this.name + "}";
    }

    public static String foregroundServiceTypeToLabel(int i) {
        if (i == -1) {
            return "manifest";
        }
        if (i == 0) {
            return "none";
        }
        if (i == 1) {
            return "dataSync";
        }
        if (i == 2) {
            return "mediaPlayback";
        }
        switch (i) {
            case 4:
                return "phoneCall";
            case 8:
                return "location";
            case 16:
                return "connectedDevice";
            case 32:
                return "mediaProjection";
            case 64:
                return Context.CAMERA_SERVICE;
            case 128:
                return "microphone";
            case 256:
                return BatteryManager.EXTRA_HEALTH;
            case 512:
                return "remoteMessaging";
            case 1024:
                return "systemExempted";
            case 2048:
                return "shortService";
            case 4096:
                return "fileManagement";
            case 8192:
                return "mediaProcessing";
            case 1073741824:
                return "specialUse";
            default:
                return "unknown";
        }
    }

    @Override // android.content.pm.ComponentInfo, android.content.pm.PackageItemInfo, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString8(this.permission);
        parcel.writeInt(this.flags);
        parcel.writeInt(this.mForegroundServiceType);
    }

    private ServiceInfo(Parcel parcel) {
        super(parcel);
        this.mForegroundServiceType = 0;
        this.permission = parcel.readString8();
        this.flags = parcel.readInt();
        this.mForegroundServiceType = parcel.readInt();
    }
}
