package android.hardware;

import android.annotation.SystemApi;
import android.content.Context;
import android.hardware.ISensorPrivacyListener;
import android.hardware.ISensorPrivacyManager;
import android.hardware.SensorPrivacyManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Pair;
import com.android.internal.camera.flags.Flags;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;

/* loaded from: classes2.dex */
public final class SensorPrivacyManager {
    private static final String LOG_TAG = "SensorPrivacyManager";
    public static final int TOGGLE_TYPE_HARDWARE = 2;
    public static final int TOGGLE_TYPE_SOFTWARE = 1;
    private static SensorPrivacyManager sInstance;
    private final Context mContext;
    private final ISensorPrivacyManager mService;
    public static final String EXTRA_SENSOR = SensorPrivacyManager.class.getName() + ".extra.sensor";
    public static final String EXTRA_NOTIFICATION_ID = SensorPrivacyManager.class.getName() + ".extra.notification_id";
    public static final String EXTRA_ALL_SENSORS = SensorPrivacyManager.class.getName() + ".extra.all_sensors";
    public static final String EXTRA_TOGGLE_TYPE = SensorPrivacyManager.class.getName() + ".extra.toggle_type";
    private static final Object sInstanceLock = new Object();
    private IBinder token = new Binder();
    private final Object mLock = new Object();
    private final ArrayMap<Pair<Integer, Integer>, Boolean> mToggleSupportCache = new ArrayMap<>();
    private final ArrayMap<OnSensorPrivacyChangedListener, Executor> mToggleListeners = new ArrayMap<>();
    private final ArrayMap<Pair<Integer, OnSensorPrivacyChangedListener>, OnSensorPrivacyChangedListener> mLegacyToggleListeners = new ArrayMap<>();
    private final ISensorPrivacyListener mIToggleListener = new AnonymousClass1();
    private boolean mToggleListenerRegistered = false;
    private Boolean mRequiresAuthentication = null;
    private final ArrayMap<OnAllSensorPrivacyChangedListener, ISensorPrivacyListener> mListeners = new ArrayMap<>();

    public interface OnAllSensorPrivacyChangedListener {
        void onAllSensorPrivacyChanged(boolean z);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ToggleType {
    }

    public static class Sensors {
        public static final int CAMERA = 2;
        public static final int MICROPHONE = 1;

        @Retention(RetentionPolicy.SOURCE)
        public @interface Sensor {
        }

        private Sensors() {
        }
    }

    public static class Sources {
        public static final int DIALOG = 3;
        public static final int OTHER = 5;
        public static final int QS_TILE = 1;
        public static final int SAFETY_CENTER = 6;
        public static final int SETTINGS = 2;
        public static final int SHELL = 4;

        @Retention(RetentionPolicy.SOURCE)
        public @interface Source {
        }

        private Sources() {
        }
    }

    @SystemApi
    public static class StateTypes {
        public static final int DISABLED = 2;
        public static final int ENABLED = 1;
        public static final int ENABLED_EXCEPT_ALLOWLISTED_APPS = 3;

        @Retention(RetentionPolicy.SOURCE)
        public @interface StateType {
        }

        private StateTypes() {
        }
    }

    @SystemApi
    public interface OnSensorPrivacyChangedListener {
        @Deprecated
        void onSensorPrivacyChanged(int i, boolean z);

        default void onSensorPrivacyChanged(SensorPrivacyChangedParams sensorPrivacyChangedParams) {
            onSensorPrivacyChanged(sensorPrivacyChangedParams.mSensor, sensorPrivacyChangedParams.mEnabled);
        }

        public static class SensorPrivacyChangedParams {
            private boolean mEnabled;
            private int mSensor;
            private int mState;
            private int mToggleType;

            private SensorPrivacyChangedParams(int i, int i2, int i3) {
                this.mToggleType = i;
                this.mSensor = i2;
                this.mState = i3;
                if (i3 == 1) {
                    this.mEnabled = true;
                } else {
                    this.mEnabled = false;
                }
            }

            private SensorPrivacyChangedParams(int i, int i2, boolean z) {
                this.mToggleType = i;
                this.mSensor = i2;
                this.mEnabled = z;
            }

            public int getToggleType() {
                return this.mToggleType;
            }

            public int getSensor() {
                return this.mSensor;
            }

            public boolean isEnabled() {
                return this.mEnabled;
            }

            public int getState() {
                return this.mState;
            }
        }
    }

    /* renamed from: android.hardware.SensorPrivacyManager$1, reason: invalid class name */
    class AnonymousClass1 extends ISensorPrivacyListener.Stub {
        AnonymousClass1() {
        }

        @Override // android.hardware.ISensorPrivacyListener
        public void onSensorPrivacyChanged(final int i, final int i2, final boolean z) {
            synchronized (SensorPrivacyManager.this.mLock) {
                for (int i3 = 0; i3 < SensorPrivacyManager.this.mToggleListeners.size(); i3++) {
                    final OnSensorPrivacyChangedListener onSensorPrivacyChangedListener = (OnSensorPrivacyChangedListener) SensorPrivacyManager.this.mToggleListeners.keyAt(i3);
                    if (Flags.cameraPrivacyAllowlist()) {
                        final int i4 = z ? 1 : 2;
                        ((Executor) SensorPrivacyManager.this.mToggleListeners.valueAt(i3)).execute(new Runnable() { // from class: android.hardware.SensorPrivacyManager$1$$ExternalSyntheticLambda1
                            @Override // java.lang.Runnable
                            public final void run() {
                                onSensorPrivacyChangedListener.onSensorPrivacyChanged(new SensorPrivacyManager.OnSensorPrivacyChangedListener.SensorPrivacyChangedParams(i, i2, i4));
                            }
                        });
                    } else {
                        ((Executor) SensorPrivacyManager.this.mToggleListeners.valueAt(i3)).execute(new Runnable() { // from class: android.hardware.SensorPrivacyManager$1$$ExternalSyntheticLambda2
                            @Override // java.lang.Runnable
                            public final void run() {
                                onSensorPrivacyChangedListener.onSensorPrivacyChanged(new SensorPrivacyManager.OnSensorPrivacyChangedListener.SensorPrivacyChangedParams(i, i2, z));
                            }
                        });
                    }
                }
            }
        }

        @Override // android.hardware.ISensorPrivacyListener
        public void onSensorPrivacyStateChanged(final int i, final int i2, final int i3) {
            synchronized (SensorPrivacyManager.this.mLock) {
                for (int i4 = 0; i4 < SensorPrivacyManager.this.mToggleListeners.size(); i4++) {
                    final OnSensorPrivacyChangedListener onSensorPrivacyChangedListener = (OnSensorPrivacyChangedListener) SensorPrivacyManager.this.mToggleListeners.keyAt(i4);
                    ((Executor) SensorPrivacyManager.this.mToggleListeners.valueAt(i4)).execute(new Runnable() { // from class: android.hardware.SensorPrivacyManager$1$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            onSensorPrivacyChangedListener.onSensorPrivacyChanged(new SensorPrivacyManager.OnSensorPrivacyChangedListener.SensorPrivacyChangedParams(i, i2, i3));
                        }
                    });
                }
            }
        }
    }

    private SensorPrivacyManager(Context context, ISensorPrivacyManager iSensorPrivacyManager) {
        this.mContext = context;
        this.mService = iSensorPrivacyManager;
    }

    public static SensorPrivacyManager getInstance(Context context) {
        SensorPrivacyManager sensorPrivacyManager;
        synchronized (sInstanceLock) {
            if (sInstance == null) {
                try {
                    sInstance = new SensorPrivacyManager(context, ISensorPrivacyManager.Stub.asInterface(ServiceManager.getServiceOrThrow(Context.SENSOR_PRIVACY_SERVICE)));
                } catch (ServiceManager.ServiceNotFoundException e) {
                    throw new IllegalStateException(e);
                }
            }
            sensorPrivacyManager = sInstance;
        }
        return sensorPrivacyManager;
    }

    public static SensorPrivacyManager getInstance(Context context, ISensorPrivacyManager iSensorPrivacyManager) {
        SensorPrivacyManager sensorPrivacyManager;
        synchronized (sInstanceLock) {
            sensorPrivacyManager = new SensorPrivacyManager(context, iSensorPrivacyManager);
            sInstance = sensorPrivacyManager;
        }
        return sensorPrivacyManager;
    }

    public boolean supportsSensorToggle(int i) {
        return supportsSensorToggle(1, i);
    }

    public boolean supportsSensorToggle(int i, int i2) {
        boolean zBooleanValue;
        try {
            Pair<Integer, Integer> pair = new Pair<>(Integer.valueOf(i), Integer.valueOf(i2));
            synchronized (this.mLock) {
                Boolean boolValueOf = this.mToggleSupportCache.get(pair);
                if (boolValueOf == null) {
                    boolValueOf = Boolean.valueOf(this.mService.supportsSensorToggle(i, i2));
                    this.mToggleSupportCache.put(pair, boolValueOf);
                }
                zBooleanValue = boolValueOf.booleanValue();
            }
            return zBooleanValue;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void addSensorPrivacyListener(int i, OnSensorPrivacyChangedListener onSensorPrivacyChangedListener) {
        addSensorPrivacyListener(i, this.mContext.getMainExecutor(), onSensorPrivacyChangedListener);
    }

    public void addSensorPrivacyListener(int i, int i2, OnSensorPrivacyChangedListener onSensorPrivacyChangedListener) {
        addSensorPrivacyListener(i, this.mContext.getMainExecutor(), onSensorPrivacyChangedListener);
    }

    @SystemApi
    public void addSensorPrivacyListener(final int i, Executor executor, final OnSensorPrivacyChangedListener onSensorPrivacyChangedListener) {
        Pair<Integer, OnSensorPrivacyChangedListener> pair = new Pair<>(Integer.valueOf(i), onSensorPrivacyChangedListener);
        OnSensorPrivacyChangedListener onSensorPrivacyChangedListener2 = new OnSensorPrivacyChangedListener(this) { // from class: android.hardware.SensorPrivacyManager.2
            @Override // android.hardware.SensorPrivacyManager.OnSensorPrivacyChangedListener
            public void onSensorPrivacyChanged(int i2, boolean z) {
            }

            @Override // android.hardware.SensorPrivacyManager.OnSensorPrivacyChangedListener
            public void onSensorPrivacyChanged(OnSensorPrivacyChangedListener.SensorPrivacyChangedParams sensorPrivacyChangedParams) {
                if (sensorPrivacyChangedParams.getSensor() == i) {
                    onSensorPrivacyChangedListener.onSensorPrivacyChanged(sensorPrivacyChangedParams);
                }
            }
        };
        synchronized (this.mLock) {
            this.mLegacyToggleListeners.put(pair, onSensorPrivacyChangedListener2);
            addSensorPrivacyListenerLocked(executor, onSensorPrivacyChangedListener2);
        }
    }

    @SystemApi
    public void addSensorPrivacyListener(OnSensorPrivacyChangedListener onSensorPrivacyChangedListener) {
        addSensorPrivacyListener(this.mContext.getMainExecutor(), onSensorPrivacyChangedListener);
    }

    @SystemApi
    public void addSensorPrivacyListener(Executor executor, OnSensorPrivacyChangedListener onSensorPrivacyChangedListener) {
        synchronized (this.mLock) {
            addSensorPrivacyListenerLocked(executor, onSensorPrivacyChangedListener);
        }
    }

    private void addSensorPrivacyListenerLocked(Executor executor, OnSensorPrivacyChangedListener onSensorPrivacyChangedListener) {
        if (!this.mToggleListenerRegistered) {
            try {
                this.mService.addToggleSensorPrivacyListener(this.mIToggleListener);
                this.mToggleListenerRegistered = true;
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
            }
        }
        if (this.mToggleListeners.containsKey(onSensorPrivacyChangedListener)) {
            throw new IllegalArgumentException("listener is already registered");
        }
        this.mToggleListeners.put(onSensorPrivacyChangedListener, executor);
    }

    @SystemApi
    public void removeSensorPrivacyListener(int i, OnSensorPrivacyChangedListener onSensorPrivacyChangedListener) {
        Pair pair = new Pair(Integer.valueOf(i), onSensorPrivacyChangedListener);
        synchronized (this.mLock) {
            OnSensorPrivacyChangedListener onSensorPrivacyChangedListenerRemove = this.mLegacyToggleListeners.remove(pair);
            if (onSensorPrivacyChangedListenerRemove != null) {
                removeSensorPrivacyListenerLocked(onSensorPrivacyChangedListenerRemove);
            }
        }
    }

    @SystemApi
    public void removeSensorPrivacyListener(OnSensorPrivacyChangedListener onSensorPrivacyChangedListener) {
        synchronized (this.mLock) {
            removeSensorPrivacyListenerLocked(onSensorPrivacyChangedListener);
        }
    }

    private void removeSensorPrivacyListenerLocked(OnSensorPrivacyChangedListener onSensorPrivacyChangedListener) {
        this.mToggleListeners.remove(onSensorPrivacyChangedListener);
        if (this.mToggleListeners.size() == 0) {
            try {
                this.mService.removeToggleSensorPrivacyListener(this.mIToggleListener);
                this.mToggleListenerRegistered = false;
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
            }
        }
    }

    @SystemApi
    @Deprecated
    public boolean isSensorPrivacyEnabled(int i) {
        return isSensorPrivacyEnabled(1, i);
    }

    @SystemApi
    public boolean isSensorPrivacyEnabled(int i, int i2) {
        try {
            return this.mService.isToggleSensorPrivacyEnabled(i, i2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public boolean areAnySensorPrivacyTogglesEnabled(int i) {
        try {
            return this.mService.isCombinedToggleSensorPrivacyEnabled(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public int getSensorPrivacyState(int i, int i2) {
        try {
            return this.mService.getToggleSensorPrivacyState(i, i2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public boolean isCameraPrivacyEnabled(String str) {
        try {
            return this.mService.isCameraPrivacyEnabled(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public List<String> getCameraPrivacyAllowlist() {
        List<String> cameraPrivacyAllowlist;
        synchronized (this.mLock) {
            try {
                try {
                    cameraPrivacyAllowlist = this.mService.getCameraPrivacyAllowlist();
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return cameraPrivacyAllowlist;
    }

    public void setCameraPrivacyAllowlist(List<String> list) {
        synchronized (this.mLock) {
            try {
                try {
                    this.mService.setCameraPrivacyAllowlist(list);
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @SystemApi
    public void setSensorPrivacy(int i, boolean z) {
        setSensorPrivacy(resolveSourceFromCurrentContext(), i, z, this.mContext.getUserId());
    }

    private int resolveSourceFromCurrentContext() {
        return Objects.equals(this.mContext.getOpPackageName(), this.mContext.getPackageManager().getPermissionControllerPackageName()) ? 6 : 5;
    }

    @SystemApi
    public void setSensorPrivacyState(int i, int i2) {
        setSensorPrivacyState(resolveSourceFromCurrentContext(), i, i2);
    }

    public void setSensorPrivacy(int i, int i2, boolean z) {
        setSensorPrivacy(i, i2, z, -2);
    }

    public void setSensorPrivacy(int i, int i2, boolean z, int i3) {
        try {
            this.mService.setToggleSensorPrivacy(i3, i, i2, z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setSensorPrivacyState(int i, int i2, int i3) {
        try {
            this.mService.setToggleSensorPrivacyState(this.mContext.getUserId(), i, i2, i3);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setSensorPrivacyForProfileGroup(int i, int i2, boolean z) {
        setSensorPrivacyForProfileGroup(i, i2, z, this.mContext.getUserId());
    }

    public void setSensorPrivacyForProfileGroup(int i, int i2, boolean z, int i3) {
        try {
            this.mService.setToggleSensorPrivacyForProfileGroup(i3, i, i2, z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setSensorPrivacyForProfileGroupWithConfirmPopup(int i, int i2, boolean z, int i3) {
        try {
            this.mService.setToggleSensorPrivacyForProfileGroupWithConfirmPopup(-2, i, i2, z, i3);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setSensorPrivacyStateForProfileGroup(int i, int i2, int i3) {
        try {
            this.mService.setToggleSensorPrivacyStateForProfileGroup(this.mContext.getUserId(), i, i2, i3);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void suppressSensorPrivacyReminders(int i, boolean z) {
        suppressSensorPrivacyReminders(i, z, this.mContext.getUserId());
    }

    public void suppressSensorPrivacyReminders(int i, boolean z, int i2) {
        try {
            this.mService.suppressToggleSensorPrivacyReminders(i2, i, this.token, z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean requiresAuthentication() {
        if (this.mRequiresAuthentication == null) {
            try {
                this.mRequiresAuthentication = Boolean.valueOf(this.mService.requiresAuthentication());
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return this.mRequiresAuthentication.booleanValue();
    }

    public void showSensorUseDialog(int i) {
        try {
            this.mService.showSensorUseDialog(i);
        } catch (RemoteException e) {
            Log.e(LOG_TAG, "Received exception while trying to show sensor use dialog", e);
        }
    }

    public void setAllSensorPrivacy(boolean z) {
        try {
            this.mService.setSensorPrivacy(z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void addAllSensorPrivacyListener(final OnAllSensorPrivacyChangedListener onAllSensorPrivacyChangedListener) {
        synchronized (this.mListeners) {
            ISensorPrivacyListener iSensorPrivacyListener = this.mListeners.get(onAllSensorPrivacyChangedListener);
            if (iSensorPrivacyListener == null) {
                iSensorPrivacyListener = new ISensorPrivacyListener.Stub(this) { // from class: android.hardware.SensorPrivacyManager.3
                    @Override // android.hardware.ISensorPrivacyListener
                    public void onSensorPrivacyStateChanged(int i, int i2, int i3) {
                    }

                    @Override // android.hardware.ISensorPrivacyListener
                    public void onSensorPrivacyChanged(int i, int i2, boolean z) {
                        onAllSensorPrivacyChangedListener.onAllSensorPrivacyChanged(z);
                    }
                };
                this.mListeners.put(onAllSensorPrivacyChangedListener, iSensorPrivacyListener);
            }
            try {
                this.mService.addSensorPrivacyListener(iSensorPrivacyListener);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void removeAllSensorPrivacyListener(OnAllSensorPrivacyChangedListener onAllSensorPrivacyChangedListener) {
        synchronized (this.mListeners) {
            ISensorPrivacyListener iSensorPrivacyListener = this.mListeners.get(onAllSensorPrivacyChangedListener);
            if (iSensorPrivacyListener != null) {
                this.mListeners.remove(iSensorPrivacyListener);
                try {
                    this.mService.removeSensorPrivacyListener(iSensorPrivacyListener);
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
        }
    }

    public boolean isAllSensorPrivacyEnabled() {
        try {
            return this.mService.isSensorPrivacyEnabled();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
