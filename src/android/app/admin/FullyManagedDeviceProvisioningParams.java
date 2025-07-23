package android.app.admin;

import android.annotation.SystemApi;
import android.content.ComponentName;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.PersistableBundle;
import com.android.internal.protolog.PerfettoProtoLogImpl;
import java.util.Locale;
import java.util.Objects;

@SystemApi
/* loaded from: classes.dex */
public final class FullyManagedDeviceProvisioningParams implements Parcelable {
    private static final String CAN_DEVICE_OWNER_GRANT_SENSOR_PERMISSIONS_PARAM = "CAN_DEVICE_OWNER_GRANT_SENSOR_PERMISSIONS";
    public static final Parcelable.Creator<FullyManagedDeviceProvisioningParams> CREATOR = new Parcelable.Creator<FullyManagedDeviceProvisioningParams>() { // from class: android.app.admin.FullyManagedDeviceProvisioningParams.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FullyManagedDeviceProvisioningParams createFromParcel(Parcel parcel) {
            return new FullyManagedDeviceProvisioningParams((ComponentName) parcel.readTypedObject(ComponentName.CREATOR), parcel.readString(), parcel.readBoolean(), parcel.readString(), parcel.readLong(), parcel.readString(), parcel.readBoolean(), parcel.readPersistableBundle(), parcel.readBoolean());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FullyManagedDeviceProvisioningParams[] newArray(int i) {
            return new FullyManagedDeviceProvisioningParams[i];
        }
    };
    private static final String DEMO_DEVICE = "DEMO_DEVICE";
    private static final String LEAVE_ALL_SYSTEM_APPS_ENABLED_PARAM = "LEAVE_ALL_SYSTEM_APPS_ENABLED";
    private static final String LOCALE_PROVIDED_PARAM = "LOCALE_PROVIDED";
    private static final String TIME_ZONE_PROVIDED_PARAM = "TIME_ZONE_PROVIDED";
    private final PersistableBundle mAdminExtras;
    private final boolean mDemoDevice;
    private final ComponentName mDeviceAdminComponentName;
    private final boolean mDeviceOwnerCanGrantSensorsPermissions;
    private final boolean mLeaveAllSystemAppsEnabled;
    private final long mLocalTime;
    private final Locale mLocale;
    private final String mOwnerName;
    private final String mTimeZone;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private FullyManagedDeviceProvisioningParams(ComponentName componentName, String str, boolean z, String str2, long j, Locale locale, boolean z2, PersistableBundle persistableBundle, boolean z3) {
        this.mDeviceAdminComponentName = (ComponentName) Objects.requireNonNull(componentName);
        this.mOwnerName = (String) Objects.requireNonNull(str);
        this.mLeaveAllSystemAppsEnabled = z;
        this.mTimeZone = str2;
        this.mLocalTime = j;
        this.mLocale = locale;
        this.mDeviceOwnerCanGrantSensorsPermissions = z2;
        this.mAdminExtras = persistableBundle;
        this.mDemoDevice = z3;
    }

    private FullyManagedDeviceProvisioningParams(ComponentName componentName, String str, boolean z, String str2, long j, String str3, boolean z2, PersistableBundle persistableBundle, boolean z3) {
        this(componentName, str, z, str2, j, getLocale(str3), z2, persistableBundle, z3);
    }

    private static Locale getLocale(String str) {
        if (str == null) {
            return null;
        }
        return Locale.forLanguageTag(str);
    }

    public ComponentName getDeviceAdminComponentName() {
        return this.mDeviceAdminComponentName;
    }

    public String getOwnerName() {
        return this.mOwnerName;
    }

    public boolean isLeaveAllSystemAppsEnabled() {
        return this.mLeaveAllSystemAppsEnabled;
    }

    public String getTimeZone() {
        return this.mTimeZone;
    }

    public long getLocalTime() {
        return this.mLocalTime;
    }

    public Locale getLocale() {
        return this.mLocale;
    }

    public boolean canDeviceOwnerGrantSensorsPermissions() {
        return this.mDeviceOwnerCanGrantSensorsPermissions;
    }

    public PersistableBundle getAdminExtras() {
        return new PersistableBundle(this.mAdminExtras);
    }

    public boolean isDemoDevice() {
        return this.mDemoDevice;
    }

    public void logParams(String str) {
        Objects.requireNonNull(str);
        logParam(str, LEAVE_ALL_SYSTEM_APPS_ENABLED_PARAM, this.mLeaveAllSystemAppsEnabled);
        logParam(str, CAN_DEVICE_OWNER_GRANT_SENSOR_PERMISSIONS_PARAM, this.mDeviceOwnerCanGrantSensorsPermissions);
        logParam(str, TIME_ZONE_PROVIDED_PARAM, this.mTimeZone != null);
        logParam(str, LOCALE_PROVIDED_PARAM, this.mLocale != null);
        logParam(str, DEMO_DEVICE, this.mDemoDevice);
    }

    private void logParam(String str, String str2, boolean z) {
        DevicePolicyEventLogger.createEvent(197).setStrings(str).setAdmin(this.mDeviceAdminComponentName).setStrings(str2).setBoolean(z).write();
    }

    public static final class Builder {
        private PersistableBundle mAdminExtras;
        private final ComponentName mDeviceAdminComponentName;
        private boolean mLeaveAllSystemAppsEnabled;
        private long mLocalTime;
        private Locale mLocale;
        private final String mOwnerName;
        private String mTimeZone;
        boolean mDeviceOwnerCanGrantSensorsPermissions = true;
        boolean mDemoDevice = false;

        public Builder(ComponentName componentName, String str) {
            this.mDeviceAdminComponentName = (ComponentName) Objects.requireNonNull(componentName);
            this.mOwnerName = (String) Objects.requireNonNull(str);
        }

        public Builder setLeaveAllSystemAppsEnabled(boolean z) {
            this.mLeaveAllSystemAppsEnabled = z;
            return this;
        }

        public Builder setTimeZone(String str) {
            this.mTimeZone = str;
            return this;
        }

        public Builder setLocalTime(long j) {
            this.mLocalTime = j;
            return this;
        }

        public Builder setLocale(Locale locale) {
            this.mLocale = locale;
            return this;
        }

        public Builder setCanDeviceOwnerGrantSensorsPermissions(boolean z) {
            this.mDeviceOwnerCanGrantSensorsPermissions = z;
            return this;
        }

        public Builder setAdminExtras(PersistableBundle persistableBundle) {
            PersistableBundle persistableBundle2;
            if (persistableBundle != null) {
                persistableBundle2 = new PersistableBundle(persistableBundle);
            } else {
                persistableBundle2 = new PersistableBundle();
            }
            this.mAdminExtras = persistableBundle2;
            return this;
        }

        public Builder setDemoDevice(boolean z) {
            this.mDemoDevice = z;
            return this;
        }

        public FullyManagedDeviceProvisioningParams build() {
            ComponentName componentName = this.mDeviceAdminComponentName;
            String str = this.mOwnerName;
            boolean z = this.mLeaveAllSystemAppsEnabled;
            String str2 = this.mTimeZone;
            long j = this.mLocalTime;
            Locale locale = this.mLocale;
            boolean z2 = this.mDeviceOwnerCanGrantSensorsPermissions;
            PersistableBundle persistableBundle = this.mAdminExtras;
            if (persistableBundle == null) {
                persistableBundle = new PersistableBundle();
            }
            return new FullyManagedDeviceProvisioningParams(componentName, str, z, str2, j, locale, z2, persistableBundle, this.mDemoDevice);
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("FullyManagedDeviceProvisioningParams{mDeviceAdminComponentName=");
        sb.append(this.mDeviceAdminComponentName);
        sb.append(", mOwnerName=");
        sb.append(this.mOwnerName);
        sb.append(", mLeaveAllSystemAppsEnabled=");
        sb.append(this.mLeaveAllSystemAppsEnabled);
        sb.append(", mTimeZone=");
        String str = this.mTimeZone;
        Object obj = PerfettoProtoLogImpl.NULL_STRING;
        if (str == null) {
            str = PerfettoProtoLogImpl.NULL_STRING;
        }
        sb.append(str);
        sb.append(", mLocalTime=");
        sb.append(this.mLocalTime);
        sb.append(", mLocale=");
        Locale locale = this.mLocale;
        if (locale != null) {
            obj = locale;
        }
        sb.append(obj);
        sb.append(", mDeviceOwnerCanGrantSensorsPermissions=");
        sb.append(this.mDeviceOwnerCanGrantSensorsPermissions);
        sb.append(", mAdminExtras=");
        sb.append(this.mAdminExtras);
        sb.append(", mDemoDevice=");
        sb.append(this.mDemoDevice);
        sb.append('}');
        return sb.toString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedObject(this.mDeviceAdminComponentName, i);
        parcel.writeString(this.mOwnerName);
        parcel.writeBoolean(this.mLeaveAllSystemAppsEnabled);
        parcel.writeString(this.mTimeZone);
        parcel.writeLong(this.mLocalTime);
        Locale locale = this.mLocale;
        parcel.writeString(locale == null ? null : locale.toLanguageTag());
        parcel.writeBoolean(this.mDeviceOwnerCanGrantSensorsPermissions);
        parcel.writePersistableBundle(this.mAdminExtras);
        parcel.writeBoolean(this.mDemoDevice);
    }
}
