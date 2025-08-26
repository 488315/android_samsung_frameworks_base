package android.hardware.location;

import android.content.Context;
import android.hardware.location.IActivityRecognitionHardware;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import java.lang.reflect.Array;

/* loaded from: classes2.dex */
public class ActivityRecognitionHardware extends IActivityRecognitionHardware.Stub {
    private static final String ENFORCE_HW_PERMISSION_MESSAGE = "Permission 'android.permission.LOCATION_HARDWARE' not granted to access ActivityRecognitionHardware";
    private static final int EVENT_TYPE_COUNT = 3;
    private static final int EVENT_TYPE_DISABLED = 0;
    private static final int EVENT_TYPE_ENABLED = 1;
    private static final String HARDWARE_PERMISSION = "android.permission.LOCATION_HARDWARE";
    private static final int INVALID_ACTIVITY_TYPE = -1;
    private static final int NATIVE_SUCCESS_RESULT = 0;
    private static ActivityRecognitionHardware sSingletonInstance;
    private final Context mContext;
    private final SinkList mSinks = new SinkList();
    private final String[] mSupportedActivities;
    private final int mSupportedActivitiesCount;
    private final int[][] mSupportedActivitiesEnabledEvents;
    private static final String TAG = "ActivityRecognitionHW";
    private static final boolean DEBUG = Log.isLoggable(TAG, 3);
    private static final Object sSingletonInstanceLock = new Object();

    private static native void nativeClassInit();

    /* JADX INFO: Access modifiers changed from: private */
    public native int nativeDisableActivityEvent(int i, int i2);

    private native int nativeEnableActivityEvent(int i, int i2, long j);

    private native int nativeFlush();

    private native String[] nativeGetSupportedActivities();

    private native void nativeInitialize();

    private static native boolean nativeIsSupported();

    private native void nativeRelease();

    static {
        nativeClassInit();
    }

    private static class Event {
        public int activity;
        public long timestamp;
        public int type;

        private Event() {
        }
    }

    private ActivityRecognitionHardware(Context context) {
        nativeInitialize();
        this.mContext = context;
        String[] strArrFetchSupportedActivities = fetchSupportedActivities();
        this.mSupportedActivities = strArrFetchSupportedActivities;
        int length = strArrFetchSupportedActivities.length;
        this.mSupportedActivitiesCount = length;
        this.mSupportedActivitiesEnabledEvents = (int[][]) Array.newInstance((Class<?>) Integer.TYPE, length, 3);
    }

    public static ActivityRecognitionHardware getInstance(Context context) {
        ActivityRecognitionHardware activityRecognitionHardware;
        synchronized (sSingletonInstanceLock) {
            if (sSingletonInstance == null) {
                sSingletonInstance = new ActivityRecognitionHardware(context);
            }
            activityRecognitionHardware = sSingletonInstance;
        }
        return activityRecognitionHardware;
    }

    public static boolean isSupported() {
        return nativeIsSupported();
    }

    @Override // android.hardware.location.IActivityRecognitionHardware
    public String[] getSupportedActivities() throws SecurityException {
        super.getSupportedActivities_enforcePermission();
        return this.mSupportedActivities;
    }

    @Override // android.hardware.location.IActivityRecognitionHardware
    public boolean isActivitySupported(String str) throws SecurityException {
        super.isActivitySupported_enforcePermission();
        return getActivityType(str) != -1;
    }

    @Override // android.hardware.location.IActivityRecognitionHardware
    public boolean registerSink(IActivityRecognitionHardwareSink iActivityRecognitionHardwareSink) throws SecurityException {
        super.registerSink_enforcePermission();
        return this.mSinks.register(iActivityRecognitionHardwareSink);
    }

    @Override // android.hardware.location.IActivityRecognitionHardware
    public boolean unregisterSink(IActivityRecognitionHardwareSink iActivityRecognitionHardwareSink) throws SecurityException {
        super.unregisterSink_enforcePermission();
        return this.mSinks.unregister(iActivityRecognitionHardwareSink);
    }

    @Override // android.hardware.location.IActivityRecognitionHardware
    public boolean enableActivityEvent(String str, int i, long j) throws SecurityException {
        super.enableActivityEvent_enforcePermission();
        int activityType = getActivityType(str);
        if (activityType == -1 || nativeEnableActivityEvent(activityType, i, j) != 0) {
            return false;
        }
        this.mSupportedActivitiesEnabledEvents[activityType][i] = 1;
        return true;
    }

    @Override // android.hardware.location.IActivityRecognitionHardware
    public boolean disableActivityEvent(String str, int i) throws SecurityException {
        super.disableActivityEvent_enforcePermission();
        int activityType = getActivityType(str);
        if (activityType == -1 || nativeDisableActivityEvent(activityType, i) != 0) {
            return false;
        }
        this.mSupportedActivitiesEnabledEvents[activityType][i] = 0;
        return true;
    }

    @Override // android.hardware.location.IActivityRecognitionHardware
    public boolean flush() throws SecurityException {
        super.flush_enforcePermission();
        return nativeFlush() == 0;
    }

    private void onActivityChanged(Event[] eventArr) {
        if (eventArr == null || eventArr.length == 0) {
            if (DEBUG) {
                Log.d(TAG, "No events to broadcast for onActivityChanged.");
                return;
            }
            return;
        }
        int length = eventArr.length;
        ActivityRecognitionEvent[] activityRecognitionEventArr = new ActivityRecognitionEvent[length];
        for (int i = 0; i < length; i++) {
            Event event = eventArr[i];
            activityRecognitionEventArr[i] = new ActivityRecognitionEvent(getActivityName(event.activity), event.type, event.timestamp);
        }
        ActivityChangedEvent activityChangedEvent = new ActivityChangedEvent(activityRecognitionEventArr);
        int iBeginBroadcast = this.mSinks.beginBroadcast();
        for (int i2 = 0; i2 < iBeginBroadcast; i2++) {
            try {
                this.mSinks.getBroadcastItem(i2).onActivityChanged(activityChangedEvent);
            } catch (RemoteException e) {
                Log.e(TAG, "Error delivering activity changed event.", e);
            }
        }
        this.mSinks.finishBroadcast();
    }

    private String getActivityName(int i) {
        if (i >= 0) {
            String[] strArr = this.mSupportedActivities;
            if (i < strArr.length) {
                return strArr[i];
            }
        }
        Log.e(TAG, String.format("Invalid ActivityType: %d, SupportedActivities: %d", Integer.valueOf(i), Integer.valueOf(this.mSupportedActivities.length)));
        return null;
    }

    private int getActivityType(String str) {
        if (TextUtils.isEmpty(str)) {
            return -1;
        }
        int length = this.mSupportedActivities.length;
        for (int i = 0; i < length; i++) {
            if (str.equals(this.mSupportedActivities[i])) {
                return i;
            }
        }
        return -1;
    }

    private void checkPermissions() {
        this.mContext.enforceCallingPermission("android.permission.LOCATION_HARDWARE", ENFORCE_HW_PERMISSION_MESSAGE);
    }

    private String[] fetchSupportedActivities() {
        String[] strArrNativeGetSupportedActivities = nativeGetSupportedActivities();
        return strArrNativeGetSupportedActivities != null ? strArrNativeGetSupportedActivities : new String[0];
    }

    private class SinkList extends RemoteCallbackList<IActivityRecognitionHardwareSink> {
        private SinkList() {
        }

        @Override // android.os.RemoteCallbackList
        public void onCallbackDied(IActivityRecognitionHardwareSink iActivityRecognitionHardwareSink) {
            int registeredCallbackCount = ActivityRecognitionHardware.this.mSinks.getRegisteredCallbackCount();
            if (ActivityRecognitionHardware.DEBUG) {
                Log.d(ActivityRecognitionHardware.TAG, "RegisteredCallbackCount: " + registeredCallbackCount);
            }
            if (registeredCallbackCount != 0) {
                return;
            }
            for (int i = 0; i < ActivityRecognitionHardware.this.mSupportedActivitiesCount; i++) {
                for (int i2 = 0; i2 < 3; i2++) {
                    disableActivityEventIfEnabled(i, i2);
                }
            }
        }

        private void disableActivityEventIfEnabled(int i, int i2) {
            if (ActivityRecognitionHardware.this.mSupportedActivitiesEnabledEvents[i][i2] != 1) {
                return;
            }
            int iNativeDisableActivityEvent = ActivityRecognitionHardware.this.nativeDisableActivityEvent(i, i2);
            ActivityRecognitionHardware.this.mSupportedActivitiesEnabledEvents[i][i2] = 0;
            Log.e(ActivityRecognitionHardware.TAG, String.format("DisableActivityEvent: activityType=%d, eventType=%d, result=%d", Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(iNativeDisableActivityEvent)));
        }
    }
}
