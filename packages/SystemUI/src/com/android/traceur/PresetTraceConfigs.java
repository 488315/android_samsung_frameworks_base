package com.android.traceur;

import android.os.Build;
import android.util.ArraySet;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.sec.ims.settings.ImsProfile;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class PresetTraceConfigs {
    public static final TraceOptions BATTERY_TRACE_OPTIONS;
    public static final List BATTERY_TRACE_TAGS;
    public static final TraceOptions DEFAULT_TRACE_OPTIONS;
    public static final List DEFAULT_TRACE_TAGS;
    public static final TraceOptions PERFORMANCE_TRACE_OPTIONS;
    public static final List PERFORMANCE_TRACE_TAGS;
    public static final TraceOptions THERMAL_TRACE_OPTIONS;
    public static final List THERMAL_TRACE_TAGS;
    public static final TraceOptions UI_TRACE_OPTIONS;
    public static final List UI_TRACE_TAGS;
    public static final List USER_BUILD_DISABLED_TRACE_TAGS;
    public static Set mBatteryTagList;
    public static Set mDefaultTagList;
    public static Set mPerformanceTagList;
    public static Set mThermalTagList;
    public static Set mUiTagList;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class TraceOptions {
        public final boolean apps;
        public final boolean attachToBugreport;
        public final int bufferSizeKb;
        public final boolean longTrace;
        public final int maxLongTraceDurationMinutes;
        public final int maxLongTraceSizeMb;
        public final boolean winscope;

        public TraceOptions(int i, boolean z, boolean z2, boolean z3, boolean z4, int i2, int i3) {
            this.bufferSizeKb = i;
            this.winscope = z;
            this.apps = z2;
            this.longTrace = z3;
            this.attachToBugreport = z4;
            this.maxLongTraceSizeMb = i2;
            this.maxLongTraceDurationMinutes = i3;
        }
    }

    static {
        List asList = Arrays.asList("aidl", "am", "binder_driver", "camera", "cpm", "dalvik", "disk", "freq", "gfx", "hal", "idle", "input", "memory", "memreclaim", "network", "power", "res", "sched", ImsProfile.SERVICE_SS, "sync", "thermal", "view", "webview", "wm", "workq");
        DEFAULT_TRACE_TAGS = asList;
        PERFORMANCE_TRACE_TAGS = asList;
        UI_TRACE_TAGS = asList;
        THERMAL_TRACE_TAGS = Arrays.asList("aidl", "am", "binder_driver", "camera", "dalvik", "disk", "freq", "gfx", "hal", "idle", "input", "memory", "memreclaim", "network", "power", "res", "sched", ImsProfile.SERVICE_SS, "sync", "thermal", "thermal_tj", "view", "webview", "wm", "workq");
        BATTERY_TRACE_TAGS = Arrays.asList("aidl", "am", "binder_driver", "network", "nnapi", "pm", "power", ImsProfile.SERVICE_SS, "thermal", "wm");
        USER_BUILD_DISABLED_TRACE_TAGS = Arrays.asList("workq", "sync");
        mDefaultTagList = null;
        mPerformanceTagList = null;
        mBatteryTagList = null;
        mThermalTagList = null;
        mUiTagList = null;
        DEFAULT_TRACE_OPTIONS = new TraceOptions(NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT, false, true, false, true, 10240, 30);
        PERFORMANCE_TRACE_OPTIONS = new TraceOptions(NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT, false, true, false, true, 10240, 30);
        BATTERY_TRACE_OPTIONS = new TraceOptions(NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT, false, false, true, true, 10240, 30);
        THERMAL_TRACE_OPTIONS = new TraceOptions(NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT, false, true, true, true, 10240, 30);
        UI_TRACE_OPTIONS = new TraceOptions(NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT, true, true, true, true, 10240, 30);
    }

    public static TraceConfig getDefaultConfig() {
        if (mDefaultTagList == null) {
            ArraySet arraySet = new ArraySet(DEFAULT_TRACE_TAGS);
            mDefaultTagList = arraySet;
            updateTagsIfUserBuild(arraySet);
        }
        return new TraceConfig(DEFAULT_TRACE_OPTIONS, mDefaultTagList);
    }

    public static void updateTagsIfUserBuild(Collection collection) {
        if (Build.TYPE.equals("user")) {
            ((ArraySet) collection).removeAll(USER_BUILD_DISABLED_TRACE_TAGS);
        }
    }
}
