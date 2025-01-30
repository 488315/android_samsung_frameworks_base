package android.location;

import android.p009os.Parcel;
import android.p009os.Parcelable;

/* loaded from: classes2.dex */
public class LocationConstants {
    public static final int MAX_LISTENERID_THRESHOLD = 30;
    public static final int MSG_GMS_LOCATION = 200;
    public static final int MSG_LOCATION_ICON_CHANGED = 204;
    public static final int MSG_SETTING_CHANGED = 203;
    public static final int MSG_SLOCATION_REMOVE = 202;
    public static final int MSG_SLOCATION_REQUEST = 201;

    public enum STATE_TYPE implements Parcelable {
        LOCATION_REQUEST,
        LOCATION_REMOVE,
        LOCATION_UPDATE,
        FOREGROUND_CHANGED,
        OP_CHANGED,
        PERMISSIONS_CHANGED,
        FREEZE_STATE_CHANGED,
        ADD_DATA_LISTENER,
        REMOVE_DATA_LISTENER,
        UPDATE_DATA_LISTENER,
        MONITOR_SERVICE_CONNECTED,
        NOTIFICATION_LISTENER_CONNECTED,
        NOTIFICATION_LISTENER_DISCONNECTED,
        NOTIFICATION_POSTED,
        NOTIFICATION_REMOVED,
        ACTIVE_REQUEST_SYNC_FROM_LMS,
        SUPL_ADDRESS,
        UPDATE_GNSS_INTERVAL,
        NAVIGATING,
        REPORT_NFW_NOTIFICATION,
        XTRA,
        PROXY_SERVICE_CONNECTED,
        PROXY_SERVICE_DISCONNECTED,
        PROXY_SERVICE_RECONNECTION_TIMEOUT,
        SET_LOCATION_ENABLED,
        SERVICE_STATE_CHANGED,
        SIM_STATE_CHANGED,
        MOCK_PROVIDER_CHANGED,
        SEND_EXTRA_COMMAND,
        AVAILABLE_MOTION_STOP,
        MOTION_STATE_CHANGED,
        MOTION_POWER_DISABLE,
        SUPPORT_ALGORITHM_TYPE,
        DEVICE_ACTIVITY_ERROR,
        SETTINGS_IGNORED_STATE_CHANGED,
        CP_CRASH,
        LOCATION_POWER_SAVE_CHANGED,
        DEVICE_STATIONARY_CHANGED,
        DEVICE_IDLE_CHANGED;

        public static final Parcelable.Creator<STATE_TYPE> CREATOR = new Parcelable.Creator<STATE_TYPE>() { // from class: android.location.LocationConstants.STATE_TYPE.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public STATE_TYPE createFromParcel(Parcel source) {
                return STATE_TYPE.valueOf(source.readString());
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public STATE_TYPE[] newArray(int size) {
                return new STATE_TYPE[size];
            }
        };

        @Override // android.p009os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.p009os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(name());
        }
    }

    public enum PAUSED_BY implements Parcelable {
        APP_OPS,
        FREEZE,
        PERMISSION_NONE,
        PERMISSION_CHECK,
        FOREGROUND,
        BACKGROUND,
        UNKNOWN;

        public static final Parcelable.Creator<PAUSED_BY> CREATOR = new Parcelable.Creator<PAUSED_BY>() { // from class: android.location.LocationConstants.PAUSED_BY.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public PAUSED_BY createFromParcel(Parcel source) {
                return PAUSED_BY.valueOf(source.readString());
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public PAUSED_BY[] newArray(int size) {
                return new PAUSED_BY[size];
            }
        };

        @Override // android.p009os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.p009os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(name());
        }
    }

    public enum LISTENER_TYPE implements Parcelable {
        LOCATION("LISTENER", false),
        NMEA("LISTENER_NMEA", true),
        GNSS_STATUS("LISTENER_STATUS", true),
        GNSS_ANTENNA_INFO("LISTENER_ANTENNA_INFO", false),
        GNSS_MEASUREMENT("LISTENER_MEASUREMENT", false),
        GNSS_NAVIGATION_MESSAGE("LISTENER_NAVIGATION", false);

        public static final Parcelable.Creator<LISTENER_TYPE> CREATOR = new Parcelable.Creator<LISTENER_TYPE>() { // from class: android.location.LocationConstants.LISTENER_TYPE.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public LISTENER_TYPE createFromParcel(Parcel source) {
                return LISTENER_TYPE.valueOf(source.readString());
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public LISTENER_TYPE[] newArray(int size) {
                return new LISTENER_TYPE[size];
            }
        };
        private final String dbCategoryAllowlist;
        private final boolean isPassiveType;
        private int maxListenerIdThreshold = 30;

        LISTENER_TYPE(String dbCategoryAllowlist, boolean isPassiveType) {
            this.dbCategoryAllowlist = dbCategoryAllowlist;
            this.isPassiveType = isPassiveType;
        }

        public boolean isPassiveType() {
            return this.isPassiveType;
        }

        public String getDbCategoryAllowlist() {
            return this.dbCategoryAllowlist;
        }

        public int getMaxListenerIdThreshold() {
            return this.maxListenerIdThreshold;
        }

        public void setMaxListenerIdThreshold(int maxListenerIdThreshold) {
            this.maxListenerIdThreshold = maxListenerIdThreshold;
        }

        @Override // android.p009os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.p009os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(name());
        }
    }
}
