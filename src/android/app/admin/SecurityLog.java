package android.app.admin;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.util.EventLog;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/* loaded from: classes.dex */
public class SecurityLog {
    public static final int LEVEL_ERROR = 3;
    public static final int LEVEL_INFO = 1;
    public static final int LEVEL_WARNING = 2;
    private static final String PROPERTY_LOGGING_ENABLED = "persist.logd.security";
    public static final int TAG_ADB_SHELL_CMD = 210002;
    public static final int TAG_ADB_SHELL_INTERACTIVE = 210001;
    public static final int TAG_APP_PROCESS_START = 210005;
    public static final int TAG_BACKUP_SERVICE_TOGGLED = 210044;
    public static final int TAG_BLUETOOTH_CONNECTION = 210039;
    public static final int TAG_BLUETOOTH_DISCONNECTION = 210040;
    public static final int TAG_CAMERA_POLICY_SET = 210034;
    public static final int TAG_CERT_AUTHORITY_INSTALLED = 210029;
    public static final int TAG_CERT_AUTHORITY_REMOVED = 210030;
    public static final int TAG_CERT_VALIDATION_FAILURE = 210033;
    public static final int TAG_CRYPTO_SELF_TEST_COMPLETED = 210031;
    public static final int TAG_KEYGUARD_DISABLED_FEATURES_SET = 210021;
    public static final int TAG_KEYGUARD_DISMISSED = 210006;
    public static final int TAG_KEYGUARD_DISMISS_AUTH_ATTEMPT = 210007;
    public static final int TAG_KEYGUARD_SECURED = 210008;
    public static final int TAG_KEY_DESTRUCTION = 210026;
    public static final int TAG_KEY_GENERATED = 210024;
    public static final int TAG_KEY_IMPORT = 210025;
    public static final int TAG_KEY_INTEGRITY_VIOLATION = 210032;
    public static final int TAG_LOGGING_STARTED = 210011;
    public static final int TAG_LOGGING_STOPPED = 210012;
    public static final int TAG_LOG_BUFFER_SIZE_CRITICAL = 210015;
    public static final int TAG_MAX_PASSWORD_ATTEMPTS_SET = 210020;
    public static final int TAG_MAX_SCREEN_LOCK_TIMEOUT_SET = 210019;
    public static final int TAG_MEDIA_MOUNT = 210013;
    public static final int TAG_MEDIA_UNMOUNT = 210014;
    public static final int TAG_NFC_DISABLED = 210046;
    public static final int TAG_NFC_ENABLED = 210045;
    public static final int TAG_OS_SHUTDOWN = 210010;
    public static final int TAG_OS_STARTUP = 210009;
    public static final int TAG_PACKAGE_INSTALLED = 210041;
    public static final int TAG_PACKAGE_UNINSTALLED = 210043;
    public static final int TAG_PACKAGE_UPDATED = 210042;
    public static final int TAG_PASSWORD_CHANGED = 210036;
    public static final int TAG_PASSWORD_COMPLEXITY_REQUIRED = 210035;
    public static final int TAG_PASSWORD_COMPLEXITY_SET = 210017;
    public static final int TAG_PASSWORD_EXPIRATION_SET = 210016;
    public static final int TAG_PASSWORD_HISTORY_LENGTH_SET = 210018;
    public static final int TAG_REMOTE_LOCK = 210022;
    public static final int TAG_SYNC_RECV_FILE = 210003;
    public static final int TAG_SYNC_SEND_FILE = 210004;
    public static final int TAG_USER_RESTRICTION_ADDED = 210027;
    public static final int TAG_USER_RESTRICTION_REMOVED = 210028;
    public static final int TAG_WIFI_CONNECTION = 210037;
    public static final int TAG_WIFI_DISCONNECTION = 210038;
    public static final int TAG_WIPE_FAILURE = 210023;

    @Retention(RetentionPolicy.SOURCE)
    public @interface SecurityLogLevel {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SecurityLogTag {
    }

    public static native boolean isLoggingEnabled();

    public static native void readEvents(Collection<SecurityEvent> collection) throws IOException;

    public static native void readEventsOnWrapping(long j, Collection<SecurityEvent> collection) throws IOException;

    public static native void readEventsSince(long j, Collection<SecurityEvent> collection) throws IOException;

    public static native void readPreviousEvents(Collection<SecurityEvent> collection) throws IOException;

    @SystemApi
    public static native int writeEvent(int i, Object... objArr);

    public static void setLoggingEnabledProperty(boolean z) {
        SystemProperties.set(PROPERTY_LOGGING_ENABLED, z ? "true" : "false");
    }

    public static boolean getLoggingEnabledProperty() {
        return SystemProperties.getBoolean(PROPERTY_LOGGING_ENABLED, false);
    }

    public static final class SecurityEvent implements Parcelable {
        public static final Parcelable.Creator<SecurityEvent> CREATOR = new Parcelable.Creator<SecurityEvent>() { // from class: android.app.admin.SecurityLog.SecurityEvent.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SecurityEvent createFromParcel(Parcel parcel) {
                return new SecurityEvent(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SecurityEvent[] newArray(int i) {
                return new SecurityEvent[i];
            }
        };
        private EventLog.Event mEvent;
        private long mId;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        SecurityEvent(byte[] bArr) {
            this(0L, bArr);
        }

        SecurityEvent(Parcel parcel) {
            this(parcel.readLong(), parcel.createByteArray());
        }

        public SecurityEvent(long j, byte[] bArr) {
            this.mId = j;
            this.mEvent = EventLog.Event.fromBytes(bArr);
        }

        public long getTimeNanos() {
            return this.mEvent.getTimeNanos();
        }

        public int getTag() {
            return this.mEvent.getTag();
        }

        public Object getData() {
            return this.mEvent.getData();
        }

        public int getIntegerData(int i) {
            return ((Integer) ((Object[]) this.mEvent.getData())[i]).intValue();
        }

        public String getStringData(int i) {
            return (String) ((Object[]) this.mEvent.getData())[i];
        }

        public void setId(long j) {
            this.mId = j;
        }

        public long getId() {
            return this.mId;
        }

        public int getLogLevel() {
            int tag = getTag();
            if (tag != 210007) {
                if (tag != 210015) {
                    switch (tag) {
                        case 210023:
                            break;
                        case 210024:
                        case 210025:
                        case 210026:
                            break;
                        default:
                            switch (tag) {
                                case 210029:
                                    break;
                                case 210030:
                                case 210031:
                                    if (getSuccess()) {
                                        return 1;
                                    }
                                case 210032:
                                    return 3;
                                case 210033:
                                    return 2;
                                default:
                                    return 1;
                            }
                            break;
                    }
                }
                return 3;
            }
            return getSuccess() ? 1 : 2;
        }

        private boolean getSuccess() {
            Object data = getData();
            if (data != null && (data instanceof Object[])) {
                Object[] objArr = (Object[]) data;
                if (objArr.length >= 1) {
                    Object obj = objArr[0];
                    if ((obj instanceof Integer) && ((Integer) obj).intValue() != 0) {
                        return true;
                    }
                }
            }
            return false;
        }

        public SecurityEvent redact(int i) {
            int integerData;
            switch (getTag()) {
                case 210002:
                    return new SecurityEvent(getId(), this.mEvent.withNewData("").getBytes());
                case 210005:
                    try {
                        integerData = UserHandle.getUserId(getIntegerData(2));
                        break;
                    } catch (Exception unused) {
                        return null;
                    }
                case 210013:
                case 210014:
                    try {
                        return new SecurityEvent(getId(), this.mEvent.withNewData(new Object[]{getStringData(0), ""}).getBytes());
                    } catch (Exception unused2) {
                        return null;
                    }
                case 210024:
                case 210025:
                case 210026:
                    try {
                        integerData = UserHandle.getUserId(getIntegerData(2));
                        break;
                    } catch (Exception unused3) {
                        return null;
                    }
                case 210029:
                case 210030:
                case 210041:
                case 210042:
                case 210043:
                    try {
                        integerData = getIntegerData(2);
                        break;
                    } catch (Exception unused4) {
                        return null;
                    }
                case 210032:
                    try {
                        integerData = UserHandle.getUserId(getIntegerData(1));
                        break;
                    } catch (Exception unused5) {
                        return null;
                    }
                case 210036:
                    try {
                        integerData = getIntegerData(1);
                        break;
                    } catch (Exception unused6) {
                        return null;
                    }
                default:
                    integerData = -10000;
                    break;
            }
            if (integerData == -10000 || i == integerData) {
                return this;
            }
            return null;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeLong(this.mId);
            parcel.writeByteArray(this.mEvent.getBytes());
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj != null && getClass() == obj.getClass()) {
                SecurityEvent securityEvent = (SecurityEvent) obj;
                if (this.mEvent.equals(securityEvent.mEvent) && this.mId == securityEvent.mId) {
                    return true;
                }
            }
            return false;
        }

        public int hashCode() {
            return Objects.hash(this.mEvent, Long.valueOf(this.mId));
        }

        public boolean eventEquals(SecurityEvent securityEvent) {
            return securityEvent != null && this.mEvent.equals(securityEvent.mEvent);
        }
    }

    public static void redactEvents(ArrayList<SecurityEvent> arrayList, int i) {
        if (i == -1) {
            return;
        }
        int i2 = 0;
        for (int i3 = 0; i3 < arrayList.size(); i3++) {
            SecurityEvent redact = arrayList.get(i3).redact(i);
            if (redact != null) {
                arrayList.set(i2, redact);
                i2++;
            }
        }
        for (int size = arrayList.size() - 1; size >= i2; size--) {
            arrayList.remove(size);
        }
    }
}
