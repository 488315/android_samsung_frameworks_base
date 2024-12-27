package android.app.admin;

import android.content.ComponentName;
import android.os.Bundle;
import android.stats.devicepolicy.nano.StringList;

import com.android.framework.protobuf.nano.MessageNano;
import com.android.internal.util.FrameworkStatsLog;

import com.samsung.android.knox.SemPersonaManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/* loaded from: classes.dex */
public class DevicePolicyEventLogger {
    private String mAdminPackageName;
    private boolean mBooleanValue;
    private final int mEventId;
    private int mIntValue;
    private Bundle mKnoxBundleValue = new Bundle();
    private String[] mStringArrayValue;
    private long mTimePeriodMs;

    private DevicePolicyEventLogger(int eventId) {
        this.mEventId = eventId;
    }

    public static DevicePolicyEventLogger createEvent(int eventId) {
        return new DevicePolicyEventLogger(eventId);
    }

    public int getEventId() {
        return this.mEventId;
    }

    public DevicePolicyEventLogger setInt(int value) {
        this.mIntValue = value;
        return this;
    }

    public int getInt() {
        return this.mIntValue;
    }

    public DevicePolicyEventLogger setBoolean(boolean value) {
        this.mBooleanValue = value;
        return this;
    }

    public boolean getBoolean() {
        return this.mBooleanValue;
    }

    public DevicePolicyEventLogger setTimePeriod(long timePeriodMillis) {
        this.mTimePeriodMs = timePeriodMillis;
        return this;
    }

    public long getTimePeriod() {
        return this.mTimePeriodMs;
    }

    public DevicePolicyEventLogger setStrings(String... values) {
        this.mStringArrayValue = values;
        return this;
    }

    public DevicePolicyEventLogger setStrings(String value, String[] values) {
        Objects.requireNonNull(values, "values parameter cannot be null");
        this.mStringArrayValue = new String[values.length + 1];
        this.mStringArrayValue[0] = value;
        System.arraycopy(values, 0, this.mStringArrayValue, 1, values.length);
        return this;
    }

    public DevicePolicyEventLogger setStrings(String value1, String value2, String[] values) {
        Objects.requireNonNull(values, "values parameter cannot be null");
        this.mStringArrayValue = new String[values.length + 2];
        this.mStringArrayValue[0] = value1;
        this.mStringArrayValue[1] = value2;
        System.arraycopy(values, 0, this.mStringArrayValue, 2, values.length);
        return this;
    }

    public String[] getStringArray() {
        if (this.mStringArrayValue == null) {
            return null;
        }
        return (String[]) Arrays.copyOf(this.mStringArrayValue, this.mStringArrayValue.length);
    }

    public DevicePolicyEventLogger setAdmin(String packageName) {
        this.mAdminPackageName = packageName;
        return this;
    }

    public DevicePolicyEventLogger setAdmin(ComponentName componentName) {
        this.mAdminPackageName = componentName != null ? componentName.getPackageName() : null;
        return this;
    }

    public String getAdminPackageName() {
        return this.mAdminPackageName;
    }

    public DevicePolicyEventLogger setKnoxBundleValue(Bundle value) {
        this.mKnoxBundleValue.putAll(value);
        return this;
    }

    public Bundle getKnoxBundleValue() {
        return this.mKnoxBundleValue;
    }

    public void write() {
        byte[] bytes = stringArrayValueToBytes(this.mStringArrayValue);
        FrameworkStatsLog.write(
                103,
                this.mEventId,
                this.mAdminPackageName,
                this.mIntValue,
                this.mBooleanValue,
                this.mTimePeriodMs,
                bytes);
        Bundle bundle = new Bundle();
        bundle.putInt("aN", this.mEventId);
        bundle.putInt("iV", this.mIntValue);
        bundle.putBoolean("bV", this.mBooleanValue);
        bundle.putLong("tpms", this.mTimePeriodMs);
        bundle.putBundle("kB", this.mKnoxBundleValue);
        if (this.mStringArrayValue != null && this.mStringArrayValue.length > 0) {
            bundle.putStringArrayList(
                    "saV", new ArrayList<>(Arrays.asList(this.mStringArrayValue)));
        }
        if (this.mKnoxBundleValue.containsKey("targetPackageName")) {
            bundle.getStringArrayList("saV")
                    .add(this.mKnoxBundleValue.getCharSequence("targetPackageName").toString());
        }
        if (this.mAdminPackageName != null && !this.mAdminPackageName.isEmpty()) {
            bundle.putString("apN", this.mAdminPackageName);
        }
        SemPersonaManager.logDpmsKA(bundle);
    }

    private static byte[] stringArrayValueToBytes(String[] array) {
        if (array == null) {
            return null;
        }
        StringList stringList = new StringList();
        stringList.stringValue = array;
        return MessageNano.toByteArray(stringList);
    }
}
