package android.app;

import android.content.ComponentName;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import com.samsung.android.media.AudioTag;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes.dex */
public class ForegroundServiceDelegationOptions {
    public static final int DELEGATION_SERVICE_CAMERA = 7;
    public static final int DELEGATION_SERVICE_CONNECTED_DEVICE = 5;
    public static final int DELEGATION_SERVICE_DATA_SYNC = 1;
    public static final int DELEGATION_SERVICE_DEFAULT = 0;
    public static final int DELEGATION_SERVICE_HEALTH = 9;
    public static final int DELEGATION_SERVICE_LOCATION = 4;
    public static final int DELEGATION_SERVICE_MEDIA_PLAYBACK = 2;
    public static final int DELEGATION_SERVICE_MEDIA_PROJECTION = 6;
    public static final int DELEGATION_SERVICE_MICROPHONE = 8;
    public static final int DELEGATION_SERVICE_PHONE_CALL = 3;
    public static final int DELEGATION_SERVICE_REMOTE_MESSAGING = 10;
    public static final int DELEGATION_SERVICE_SPECIAL_USE = 12;
    public static final int DELEGATION_SERVICE_SYSTEM_EXEMPTED = 11;
    public final IApplicationThread mClientAppThread;
    public String mClientInstanceName;
    public final Notification mClientNotification;
    public final int mClientNotificationId;
    public final String mClientPackageName;
    public final int mClientPid;
    public final int mClientUid;
    public final int mDelegationService;
    public final int mForegroundServiceTypes;
    public final boolean mSticky;

    @Retention(RetentionPolicy.SOURCE)
    public @interface DelegationService {
    }

    public ForegroundServiceDelegationOptions(int i, int i2, String str, IApplicationThread iApplicationThread, boolean z, String str2, int i3, int i4) {
        this(i, i2, str, iApplicationThread, z, str2, i3, i4, 0, null);
    }

    public ForegroundServiceDelegationOptions(int i, int i2, String str, IApplicationThread iApplicationThread, boolean z, String str2, int i3, int i4, int i5, Notification notification) {
        this.mClientPid = i;
        this.mClientUid = i2;
        this.mClientPackageName = str;
        this.mClientAppThread = iApplicationThread;
        this.mSticky = z;
        this.mClientInstanceName = str2;
        this.mForegroundServiceTypes = i3;
        this.mDelegationService = i4;
        this.mClientNotificationId = i5;
        this.mClientNotification = notification;
    }

    public boolean isSameDelegate(ForegroundServiceDelegationOptions foregroundServiceDelegationOptions) {
        return this.mDelegationService == foregroundServiceDelegationOptions.mDelegationService && this.mClientUid == foregroundServiceDelegationOptions.mClientUid && this.mClientPid == foregroundServiceDelegationOptions.mClientPid && this.mClientInstanceName.equals(foregroundServiceDelegationOptions.mClientInstanceName);
    }

    public ComponentName getComponentName() {
        return new ComponentName(this.mClientPackageName, serviceCodeToString(this.mDelegationService) + ":" + this.mClientInstanceName);
    }

    public String getDescription() {
        StringBuilder sb = new StringBuilder(128);
        sb.append("ForegroundServiceDelegate{package:");
        sb.append(this.mClientPackageName);
        sb.append(",service:");
        sb.append(serviceCodeToString(this.mDelegationService));
        sb.append(",uid:");
        sb.append(this.mClientUid);
        sb.append(",pid:");
        sb.append(this.mClientPid);
        sb.append(",instance:");
        sb.append(this.mClientInstanceName);
        sb.append("}");
        return sb.toString();
    }

    public static String serviceCodeToString(int i) {
        switch (i) {
            case 0:
                return "DEFAULT";
            case 1:
                return "DATA_SYNC";
            case 2:
                return "MEDIA_PLAYBACK";
            case 3:
                return "PHONE_CALL";
            case 4:
                return "LOCATION";
            case 5:
                return "CONNECTED_DEVICE";
            case 6:
                return "MEDIA_PROJECTION";
            case 7:
                return AudioTag.TAG_CAMERA;
            case 8:
                return "MICROPHONE";
            case 9:
                return "HEALTH";
            case 10:
                return "REMOTE_MESSAGING";
            case 11:
                return "SYSTEM_EXEMPTED";
            case 12:
                return "SPECIAL_USE";
            default:
                return "(unknown:" + i + NavigationBarInflaterView.KEY_CODE_END;
        }
    }

    public static class Builder {
        IApplicationThread mClientAppThread;
        String mClientInstanceName;
        Notification mClientNotification;
        int mClientNotificationId;
        String mClientPackageName;
        int mClientPid;
        int mClientUid;
        int mDelegationService;
        int mForegroundServiceTypes;
        boolean mSticky;

        public Builder setClientPid(int i) {
            this.mClientPid = i;
            return this;
        }

        public Builder setClientUid(int i) {
            this.mClientUid = i;
            return this;
        }

        public Builder setClientPackageName(String str) {
            this.mClientPackageName = str;
            return this;
        }

        public Builder setClientNotification(int i, Notification notification) {
            this.mClientNotificationId = i;
            this.mClientNotification = notification;
            return this;
        }

        public Builder setClientAppThread(IApplicationThread iApplicationThread) {
            this.mClientAppThread = iApplicationThread;
            return this;
        }

        public Builder setClientInstanceName(String str) {
            this.mClientInstanceName = str;
            return this;
        }

        public Builder setSticky(boolean z) {
            this.mSticky = z;
            return this;
        }

        public Builder setForegroundServiceTypes(int i) {
            this.mForegroundServiceTypes = i;
            return this;
        }

        public Builder setDelegationService(int i) {
            this.mDelegationService = i;
            return this;
        }

        public ForegroundServiceDelegationOptions build() {
            return new ForegroundServiceDelegationOptions(this.mClientPid, this.mClientUid, this.mClientPackageName, this.mClientAppThread, this.mSticky, this.mClientInstanceName, this.mForegroundServiceTypes, this.mDelegationService, this.mClientNotificationId, this.mClientNotification);
        }
    }
}
