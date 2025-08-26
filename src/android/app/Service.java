package android.app;

import android.content.ComponentCallbacks2;
import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.Trace;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.view.contentcapture.ContentCaptureManager;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes.dex */
public abstract class Service extends ContextWrapper implements ComponentCallbacks2, ContentCaptureManager.ContentCaptureClient {
    public static final int START_CONTINUATION_MASK = 15;
    public static final int START_FLAG_REDELIVERY = 1;
    public static final int START_FLAG_RETRY = 2;
    public static final int START_NOT_STICKY = 2;
    public static final int START_REDELIVER_INTENT = 3;
    public static final int START_STICKY = 1;
    public static final int START_STICKY_COMPATIBILITY = 0;
    public static final int START_TASK_REMOVED_COMPLETE = 1000;
    public static final int STOP_FOREGROUND_DETACH = 2;

    @Deprecated
    public static final int STOP_FOREGROUND_LEGACY = 0;
    public static final int STOP_FOREGROUND_REMOVE = 1;
    private static final String TAG = "Service";
    private static final String TRACE_TRACK_NAME_FOREGROUND_SERVICE = "FGS";
    private static final ArrayMap<String, StackTrace> sStartForegroundServiceStackTraces = new ArrayMap<>();
    private IActivityManager mActivityManager;
    private Application mApplication;
    private String mClassName;
    private String mForegroundServiceTraceTitle;
    private final Object mForegroundServiceTraceTitleLock;
    private boolean mStartCompatibility;
    private ActivityThread mThread;
    private IBinder mToken;

    @Retention(RetentionPolicy.SOURCE)
    public @interface StartArgFlags {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface StartResult {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface StopForegroundSelector {
    }

    @Override // android.content.Context
    public final ContentCaptureManager.ContentCaptureClient getContentCaptureClient() {
        return this;
    }

    public abstract IBinder onBind(Intent intent);

    @Override // android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
    }

    public void onCreate() {
    }

    public void onDestroy() {
    }

    @Override // android.content.ComponentCallbacks
    public void onLowMemory() {
    }

    public void onRebind(Intent intent) {
    }

    @Deprecated
    public void onStart(Intent intent, int i) {
    }

    public void onTaskRemoved(Intent intent) {
    }

    public void onTimeout(int i) {
    }

    public void onTimeout(int i, int i2) {
    }

    @Override // android.content.ComponentCallbacks2
    public void onTrimMemory(int i) {
    }

    public boolean onUnbind(Intent intent) {
        return false;
    }

    public Service() {
        super(null);
        this.mThread = null;
        this.mClassName = null;
        this.mToken = null;
        this.mApplication = null;
        this.mActivityManager = null;
        this.mStartCompatibility = false;
        this.mForegroundServiceTraceTitle = null;
        this.mForegroundServiceTraceTitleLock = new Object();
    }

    public final Application getApplication() {
        return this.mApplication;
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        onStart(intent, i2);
        return !this.mStartCompatibility ? 1 : 0;
    }

    public final void stopSelf() {
        stopSelf(-1);
    }

    public final void stopSelf(int i) {
        IActivityManager iActivityManager = this.mActivityManager;
        if (iActivityManager == null) {
            return;
        }
        try {
            iActivityManager.stopServiceToken(new ComponentName(this, this.mClassName), this.mToken, i);
        } catch (RemoteException unused) {
        }
    }

    public final boolean stopSelfResult(int i) {
        IActivityManager iActivityManager = this.mActivityManager;
        if (iActivityManager == null) {
            return false;
        }
        try {
            return iActivityManager.stopServiceToken(new ComponentName(this, this.mClassName), this.mToken, i);
        } catch (RemoteException unused) {
            return false;
        }
    }

    @Deprecated
    public final void setForeground(boolean z) {
        Log.w(TAG, "setForeground: ignoring old API call on " + getClass().getName());
    }

    public final void startForeground(int i, Notification notification) {
        try {
            ComponentName componentName = new ComponentName(this, this.mClassName);
            this.mActivityManager.setServiceForeground(componentName, this.mToken, i, notification, 0, -1);
            clearStartForegroundServiceStackTrace();
            logForegroundServiceStart(componentName, -1);
        } catch (RemoteException unused) {
        }
    }

    public final void startForeground(int i, Notification notification, int i2) {
        try {
            ComponentName componentName = new ComponentName(this, this.mClassName);
            this.mActivityManager.setServiceForeground(componentName, this.mToken, i, notification, 0, i2);
            clearStartForegroundServiceStackTrace();
            logForegroundServiceStart(componentName, i2);
        } catch (RemoteException unused) {
        }
    }

    @Deprecated
    public final void stopForeground(boolean z) {
        stopForeground(z ? 1 : 0);
    }

    public final void stopForeground(int i) {
        try {
            this.mActivityManager.setServiceForeground(new ComponentName(this, this.mClassName), this.mToken, 0, null, i, 0);
            logForegroundServiceStopIfNecessary();
        } catch (RemoteException unused) {
        }
    }

    public final int getForegroundServiceType() {
        try {
            return this.mActivityManager.getForegroundServiceType(new ComponentName(this, this.mClassName), this.mToken);
        } catch (RemoteException unused) {
            return 0;
        }
    }

    protected void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.println("nothing to dump");
    }

    @Override // android.content.ContextWrapper
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        if (context != null) {
            context.setContentCaptureOptions(getContentCaptureOptions());
        }
    }

    public final void attach(Context context, ActivityThread activityThread, String str, IBinder iBinder, Application application, Object obj) {
        attachBaseContext(context);
        this.mThread = activityThread;
        this.mClassName = str;
        this.mToken = iBinder;
        this.mApplication = application;
        this.mActivityManager = (IActivityManager) obj;
        this.mStartCompatibility = getApplicationInfo().targetSdkVersion < 5;
        setContentCaptureOptions(application.getContentCaptureOptions());
    }

    public Context createServiceBaseContext(ActivityThread activityThread, LoadedApk loadedApk) {
        return ContextImpl.createAppContext(activityThread, loadedApk);
    }

    public final void detachAndCleanUp() {
        this.mToken = null;
        logForegroundServiceStopIfNecessary();
    }

    final String getClassName() {
        return this.mClassName;
    }

    @Override // android.view.contentcapture.ContentCaptureManager.ContentCaptureClient
    public final ComponentName contentCaptureClientGetComponentName() {
        return new ComponentName(this, this.mClassName);
    }

    private void logForegroundServiceStart(ComponentName componentName, int i) {
        synchronized (this.mForegroundServiceTraceTitleLock) {
            String str = this.mForegroundServiceTraceTitle;
            if (str == null) {
                String simple = TextUtils.formatSimple("comp=%s type=%s", componentName.toShortString(), Integer.toHexString(i));
                this.mForegroundServiceTraceTitle = simple;
                Trace.asyncTraceForTrackBegin(64L, TRACE_TRACK_NAME_FOREGROUND_SERVICE, simple, System.identityHashCode(this));
            } else {
                Trace.instantForTrack(64L, TRACE_TRACK_NAME_FOREGROUND_SERVICE, str);
            }
        }
    }

    private void logForegroundServiceStopIfNecessary() {
        synchronized (this.mForegroundServiceTraceTitleLock) {
            if (this.mForegroundServiceTraceTitle != null) {
                Trace.asyncTraceForTrackEnd(64L, TRACE_TRACK_NAME_FOREGROUND_SERVICE, System.identityHashCode(this));
                this.mForegroundServiceTraceTitle = null;
            }
        }
    }

    public static void setStartForegroundServiceStackTrace(String str, StackTrace stackTrace) {
        ArrayMap<String, StackTrace> arrayMap = sStartForegroundServiceStackTraces;
        synchronized (arrayMap) {
            arrayMap.put(str, stackTrace);
        }
    }

    private void clearStartForegroundServiceStackTrace() {
        ArrayMap<String, StackTrace> arrayMap = sStartForegroundServiceStackTraces;
        synchronized (arrayMap) {
            arrayMap.remove(getClassName());
        }
    }

    public static StackTrace getStartForegroundServiceStackTrace(String str) {
        StackTrace stackTrace;
        ArrayMap<String, StackTrace> arrayMap = sStartForegroundServiceStackTraces;
        synchronized (arrayMap) {
            stackTrace = arrayMap.get(str);
        }
        return stackTrace;
    }

    public final void callOnTimeout(int i) {
        if (this.mToken == null) {
            Log.w(TAG, "Service already destroyed, skipping onTimeout()");
            return;
        }
        try {
            if (!this.mActivityManager.shouldServiceTimeOut(new ComponentName(this, this.mClassName), this.mToken)) {
                Log.w(TAG, "Service no longer relevant, skipping onTimeout()");
                return;
            }
        } catch (RemoteException unused) {
        }
        onTimeout(i);
        if (Flags.introduceNewServiceOntimeoutCallback()) {
            onTimeout(i, 2048);
        }
    }

    public final void callOnTimeLimitExceeded(int i, int i2) {
        if (this.mToken == null) {
            Log.w(TAG, "Service already destroyed, skipping onTimeLimitExceeded()");
            return;
        }
        try {
            if (!this.mActivityManager.hasServiceTimeLimitExceeded(new ComponentName(this, this.mClassName), this.mToken)) {
                Log.w(TAG, "Service no longer relevant, skipping onTimeLimitExceeded()");
                return;
            }
        } catch (RemoteException unused) {
        }
        if (Flags.introduceNewServiceOntimeoutCallback()) {
            onTimeout(i, i2);
        }
    }
}
