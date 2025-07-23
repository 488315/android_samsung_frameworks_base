package android.app;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.hardware.input.InputManagerGlobal;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.os.IBinder;
import android.os.Looper;
import android.os.MessageQueue;
import android.os.PerformanceCollector;
import android.os.PersistableBundle;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.TestLooperManager;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.AndroidRuntimeException;
import android.util.Log;
import android.view.IWindowManager;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.WindowManagerGlobal;
import com.android.internal.R;
import com.android.internal.content.ReferrerIntent;
import com.samsung.android.rune.CoreRune;
import java.io.File;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.concurrent.TimeoutException;

/* loaded from: classes.dex */
public class Instrumentation {
    private static final long CONNECT_TIMEOUT_MILLIS = 60000;
    static final boolean DEBUG_FINISH_ACTIVITY;
    public static final boolean DEBUG_START_ACTIVITY;
    public static final String REPORT_KEY_IDENTIFIER = "id";
    public static final String REPORT_KEY_STREAMRESULT = "stream";
    public static final String TAG = "Instrumentation";
    private static final boolean VERBOSE = Log.isLoggable(TAG, 2);
    private List<ActivityMonitor> mActivityMonitors;
    private Context mAppContext;
    private ComponentName mComponent;
    private Context mInstrContext;
    private PerformanceCollector mPerformanceCollector;
    private Thread mRunner;
    private UiAutomation mUiAutomation;
    private IUiAutomationConnection mUiAutomationConnection;
    private List<ActivityWaiter> mWaitingActivities;
    private IInstrumentationWatcher mWatcher;
    private final Object mSync = new Object();
    private ActivityThread mThread = null;
    private MessageQueue mMessageQueue = null;
    private boolean mAutomaticPerformanceSnapshots = false;
    private Bundle mPerfMetrics = new Bundle();

    @Retention(RetentionPolicy.SOURCE)
    public @interface UiAutomationFlags {
    }

    public void onCreate(Bundle bundle) {
    }

    public void onDestroy() {
    }

    public boolean onException(Object obj, Throwable th) {
        return false;
    }

    public void onStart() {
    }

    static {
        DEBUG_START_ACTIVITY = Build.IS_DEBUGGABLE && SystemProperties.getBoolean("persist.wm.debug.start_activity", false);
        DEBUG_FINISH_ACTIVITY = Build.IS_DEBUGGABLE && SystemProperties.getBoolean("persist.wm.debug.finish_activity", false);
    }

    private void checkInstrumenting(String str) {
        if (this.mInstrContext != null) {
            return;
        }
        throw new RuntimeException(str + " cannot be called outside of instrumented processes");
    }

    public boolean isInstrumenting() {
        return this.mInstrContext != null;
    }

    public void start() {
        if (this.mRunner != null) {
            throw new RuntimeException("Instrumentation already started");
        }
        InstrumentationThread instrumentationThread = new InstrumentationThread("Instr: " + getClass().getName());
        this.mRunner = instrumentationThread;
        instrumentationThread.start();
    }

    public void sendStatus(int i, Bundle bundle) {
        IInstrumentationWatcher iInstrumentationWatcher = this.mWatcher;
        if (iInstrumentationWatcher != null) {
            try {
                iInstrumentationWatcher.instrumentationStatus(this.mComponent, i, bundle);
            } catch (RemoteException unused) {
                this.mWatcher = null;
            }
        }
    }

    public void addResults(Bundle bundle) {
        try {
            ActivityManager.getService().addInstrumentationResults(this.mThread.getApplicationThread(), bundle);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void finish(int i, Bundle bundle) {
        if (this.mAutomaticPerformanceSnapshots) {
            endPerformanceSnapshot();
        }
        if (this.mPerfMetrics != null) {
            if (bundle == null) {
                bundle = new Bundle();
            }
            bundle.putAll(this.mPerfMetrics);
        }
        UiAutomation uiAutomation = this.mUiAutomation;
        if (uiAutomation != null && !uiAutomation.isDestroyed()) {
            this.mUiAutomation.disconnect();
            this.mUiAutomation = null;
        }
        this.mThread.finishInstrumentation(i, bundle);
    }

    public void setAutomaticPerformanceSnapshots() {
        this.mAutomaticPerformanceSnapshots = true;
        this.mPerformanceCollector = new PerformanceCollector();
    }

    public void startPerformanceSnapshot() {
        if (isProfiling()) {
            return;
        }
        this.mPerformanceCollector.beginSnapshot(null);
    }

    public void endPerformanceSnapshot() {
        if (isProfiling()) {
            return;
        }
        this.mPerfMetrics = this.mPerformanceCollector.endSnapshot();
    }

    public Context getContext() {
        return this.mInstrContext;
    }

    public ComponentName getComponentName() {
        return this.mComponent;
    }

    public Context getTargetContext() {
        return this.mAppContext;
    }

    public String getProcessName() {
        return this.mThread.getProcessName();
    }

    public boolean isProfiling() {
        return this.mThread.isProfiling();
    }

    public void startProfiling() {
        if (this.mThread.isProfiling()) {
            File file = new File(this.mThread.getProfileFilePath());
            file.getParentFile().mkdirs();
            Debug.startMethodTracing(file.toString(), 8388608);
        }
    }

    public void stopProfiling() {
        if (this.mThread.isProfiling()) {
            Debug.stopMethodTracing();
        }
    }

    public void setInTouchMode(boolean z) {
        try {
            IWindowManager.Stub.asInterface(ServiceManager.getService(Context.WINDOW_SERVICE)).setInTouchModeOnAllDisplays(z);
        } catch (RemoteException unused) {
        }
    }

    public void resetInTouchMode() {
        setInTouchMode(getContext().getResources().getBoolean(R.bool.config_defaultInTouchMode));
    }

    public void waitForIdle(Runnable runnable) {
        this.mMessageQueue.addIdleHandler(new Idler(runnable));
        this.mThread.getHandler().post(new EmptyRunnable());
    }

    public void waitForIdleSync() {
        validateNotAppThread();
        Idler idler = new Idler(null);
        this.mMessageQueue.addIdleHandler(idler);
        this.mThread.getHandler().post(new EmptyRunnable());
        idler.waitForIdle();
    }

    public void runOnMainSync(Runnable runnable) {
        validateNotAppThread();
        SyncRunnable syncRunnable = new SyncRunnable(runnable);
        this.mThread.getHandler().post(syncRunnable);
        syncRunnable.waitForComplete();
    }

    private void runOnMainSync$ravenwood(Runnable runnable) {
        validateNotAppThread();
        SyncRunnable syncRunnable = new SyncRunnable(runnable);
        this.mInstrContext.getMainExecutor().execute(syncRunnable);
        syncRunnable.waitForComplete();
    }

    boolean isSdkSandboxAllowedToStartActivities() {
        ActivityThread activityThread;
        return Process.isSdkSandbox() && (activityThread = this.mThread) != null && activityThread.mBoundApplication != null && this.mThread.mBoundApplication.isSdkInSandbox && getContext() != null && getContext().checkSelfPermission(Manifest.permission.START_ACTIVITIES_FROM_SDK_SANDBOX) == 0;
    }

    private void adjustIntentForCtsInSdkSandboxInstrumentation(Intent intent) {
        if (this.mComponent != null && intent.getComponent() != null && getContext().getPackageManager().getSdkSandboxPackageName().equals(intent.getComponent().getPackageName())) {
            intent.setComponent(new ComponentName(this.mComponent.getPackageName(), intent.getComponent().getClassName()));
        }
        intent.setIdentifier(this.mComponent.getPackageName());
    }

    private ActivityInfo resolveActivityInfoForCtsInSandbox(Intent intent) {
        adjustIntentForCtsInSdkSandboxInstrumentation(intent);
        ActivityInfo resolveActivityInfo = intent.resolveActivityInfo(getTargetContext().getPackageManager(), 0);
        if (resolveActivityInfo != null) {
            resolveActivityInfo.processName = this.mThread.getProcessName();
        }
        return resolveActivityInfo;
    }

    public Activity startActivitySync(Intent intent) {
        return startActivitySync(intent, null);
    }

    public Activity startActivitySync(Intent intent, Bundle bundle) {
        ActivityInfo resolveActivityInfo;
        Activity activity;
        if (DEBUG_START_ACTIVITY) {
            Log.d(TAG, "startActivity: intent=" + intent + " options=" + bundle, new Throwable());
        }
        validateNotAppThread();
        synchronized (this.mSync) {
            Intent intent2 = new Intent(intent);
            if (isSdkSandboxAllowedToStartActivities()) {
                resolveActivityInfo = resolveActivityInfoForCtsInSandbox(intent2);
            } else {
                resolveActivityInfo = intent2.resolveActivityInfo(getTargetContext().getPackageManager(), 0);
            }
            if (resolveActivityInfo == null) {
                throw new RuntimeException("Unable to resolve activity for: " + intent2);
            }
            String processName = this.mThread.getProcessName();
            if (!resolveActivityInfo.processName.equals(processName)) {
                throw new RuntimeException("Intent in process " + processName + " resolved to different process " + resolveActivityInfo.processName + ": " + intent2);
            }
            intent2.setComponent(new ComponentName(resolveActivityInfo.applicationInfo.packageName, resolveActivityInfo.name));
            ActivityWaiter activityWaiter = new ActivityWaiter(intent2);
            if (this.mWaitingActivities == null) {
                this.mWaitingActivities = new ArrayList();
            }
            this.mWaitingActivities.add(activityWaiter);
            getTargetContext().startActivity(intent2, bundle);
            do {
                try {
                    this.mSync.wait();
                } catch (InterruptedException unused) {
                }
            } while (this.mWaitingActivities.contains(activityWaiter));
            activity = activityWaiter.activity;
        }
        try {
            WindowManagerGlobal.getWindowManagerService().syncInputTransactions(true);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
        return activity;
    }

    public static class ActivityMonitor {
        private final boolean mBlock;
        private final String mClass;
        int mHits;
        private final boolean mIgnoreMatchingSpecificIntents;
        Activity mLastActivity;
        private final ActivityResult mResult;
        private final IntentFilter mWhich;

        public ActivityResult onStartActivity(Intent intent) {
            return null;
        }

        public void onStartActivityResult(int i, Bundle bundle) {
        }

        public ActivityMonitor(IntentFilter intentFilter, ActivityResult activityResult, boolean z) {
            this.mHits = 0;
            this.mLastActivity = null;
            this.mWhich = intentFilter;
            this.mClass = null;
            this.mResult = activityResult;
            this.mBlock = z;
            this.mIgnoreMatchingSpecificIntents = false;
        }

        public ActivityMonitor(String str, ActivityResult activityResult, boolean z) {
            this.mHits = 0;
            this.mLastActivity = null;
            this.mWhich = null;
            this.mClass = str;
            this.mResult = activityResult;
            this.mBlock = z;
            this.mIgnoreMatchingSpecificIntents = false;
        }

        public ActivityMonitor() {
            this.mHits = 0;
            this.mLastActivity = null;
            this.mWhich = null;
            this.mClass = null;
            this.mResult = null;
            this.mBlock = false;
            this.mIgnoreMatchingSpecificIntents = true;
        }

        final boolean ignoreMatchingSpecificIntents() {
            return this.mIgnoreMatchingSpecificIntents;
        }

        public final IntentFilter getFilter() {
            return this.mWhich;
        }

        public final ActivityResult getResult() {
            return this.mResult;
        }

        public final boolean isBlocking() {
            return this.mBlock;
        }

        public final int getHits() {
            return this.mHits;
        }

        public final Activity getLastActivity() {
            return this.mLastActivity;
        }

        public final Activity waitForActivity() {
            Activity activity;
            synchronized (this) {
                while (true) {
                    activity = this.mLastActivity;
                    if (activity == null) {
                        try {
                            wait();
                        } catch (InterruptedException unused) {
                        }
                    } else {
                        this.mLastActivity = null;
                    }
                }
            }
            return activity;
        }

        public final Activity waitForActivityWithTimeout(long j) {
            synchronized (this) {
                if (this.mLastActivity == null) {
                    try {
                        wait(j);
                    } catch (InterruptedException unused) {
                    }
                }
                Activity activity = this.mLastActivity;
                if (activity == null) {
                    return null;
                }
                this.mLastActivity = null;
                return activity;
            }
        }

        public ActivityResult onStartActivity(Context context, Intent intent, Bundle bundle) {
            return onStartActivity(intent);
        }

        final boolean match(Context context, Activity activity, Intent intent) {
            String className;
            if (this.mIgnoreMatchingSpecificIntents) {
                return false;
            }
            synchronized (this) {
                IntentFilter intentFilter = this.mWhich;
                if (intentFilter != null && intentFilter.match(context.getContentResolver(), intent, true, Instrumentation.TAG) < 0) {
                    return false;
                }
                if (this.mClass != null) {
                    if (activity != null) {
                        className = activity.getClass().getName();
                    } else {
                        className = intent.getComponent() != null ? intent.getComponent().getClassName() : null;
                    }
                    if (className == null || !this.mClass.equals(className)) {
                        return false;
                    }
                }
                if (activity != null) {
                    this.mLastActivity = activity;
                    notifyAll();
                }
                return true;
            }
        }
    }

    public void addMonitor(ActivityMonitor activityMonitor) {
        synchronized (this.mSync) {
            if (this.mActivityMonitors == null) {
                this.mActivityMonitors = new ArrayList();
            }
            this.mActivityMonitors.add(activityMonitor);
        }
    }

    public ActivityMonitor addMonitor(IntentFilter intentFilter, ActivityResult activityResult, boolean z) {
        ActivityMonitor activityMonitor = new ActivityMonitor(intentFilter, activityResult, z);
        addMonitor(activityMonitor);
        return activityMonitor;
    }

    public ActivityMonitor addMonitor(String str, ActivityResult activityResult, boolean z) {
        ActivityMonitor activityMonitor = new ActivityMonitor(str, activityResult, z);
        addMonitor(activityMonitor);
        return activityMonitor;
    }

    public boolean checkMonitorHit(ActivityMonitor activityMonitor, int i) {
        waitForIdleSync();
        synchronized (this.mSync) {
            if (activityMonitor.getHits() < i) {
                return false;
            }
            this.mActivityMonitors.remove(activityMonitor);
            return true;
        }
    }

    public Activity waitForMonitor(ActivityMonitor activityMonitor) {
        Activity waitForActivity = activityMonitor.waitForActivity();
        synchronized (this.mSync) {
            this.mActivityMonitors.remove(activityMonitor);
        }
        return waitForActivity;
    }

    public Activity waitForMonitorWithTimeout(ActivityMonitor activityMonitor, long j) {
        Activity waitForActivityWithTimeout = activityMonitor.waitForActivityWithTimeout(j);
        synchronized (this.mSync) {
            this.mActivityMonitors.remove(activityMonitor);
        }
        return waitForActivityWithTimeout;
    }

    public void removeMonitor(ActivityMonitor activityMonitor) {
        synchronized (this.mSync) {
            this.mActivityMonitors.remove(activityMonitor);
        }
    }

    /* renamed from: android.app.Instrumentation$1MenuRunnable, reason: invalid class name */
    class C1MenuRunnable implements Runnable {
        private final Activity activity;
        private final int flags;
        private final int identifier;
        boolean returnValue;

        public C1MenuRunnable(Instrumentation instrumentation, Activity activity, int i, int i2) {
            this.activity = activity;
            this.identifier = i;
            this.flags = i2;
        }

        @Override // java.lang.Runnable
        public void run() {
            this.returnValue = this.activity.getWindow().performPanelIdentifierAction(0, this.identifier, this.flags);
        }
    }

    public boolean invokeMenuActionSync(Activity activity, int i, int i2) {
        C1MenuRunnable c1MenuRunnable = new C1MenuRunnable(this, activity, i, i2);
        runOnMainSync(c1MenuRunnable);
        return c1MenuRunnable.returnValue;
    }

    public boolean invokeContextMenuAction(Activity activity, int i, int i2) {
        validateNotAppThread();
        sendKeySync(new KeyEvent(0, 23));
        waitForIdleSync();
        try {
            Thread.sleep(ViewConfiguration.getLongPressTimeout());
            sendKeySync(new KeyEvent(1, 23));
            waitForIdleSync();
            C1ContextMenuRunnable c1ContextMenuRunnable = new C1ContextMenuRunnable(this, activity, i, i2);
            runOnMainSync(c1ContextMenuRunnable);
            return c1ContextMenuRunnable.returnValue;
        } catch (InterruptedException e) {
            Log.e(TAG, "Could not sleep for long press timeout", e);
            return false;
        }
    }

    /* renamed from: android.app.Instrumentation$1ContextMenuRunnable, reason: invalid class name */
    class C1ContextMenuRunnable implements Runnable {
        private final Activity activity;
        private final int flags;
        private final int identifier;
        boolean returnValue;

        public C1ContextMenuRunnable(Instrumentation instrumentation, Activity activity, int i, int i2) {
            this.activity = activity;
            this.identifier = i;
            this.flags = i2;
        }

        @Override // java.lang.Runnable
        public void run() {
            this.returnValue = this.activity.getWindow().performContextMenuIdentifierAction(this.identifier, this.flags);
        }
    }

    public void sendStringSync(String str) {
        KeyEvent[] events;
        if (str == null || (events = KeyCharacterMap.load(-1).getEvents(str.toCharArray())) == null) {
            return;
        }
        for (KeyEvent keyEvent : events) {
            sendKeySync(KeyEvent.changeTimeRepeat(keyEvent, SystemClock.uptimeMillis(), 0));
        }
    }

    public void sendKeySync(KeyEvent keyEvent) {
        validateNotAppThread();
        long downTime = keyEvent.getDownTime();
        long eventTime = keyEvent.getEventTime();
        int source = keyEvent.getSource();
        if (source == 0) {
            source = 257;
        }
        if (eventTime == 0) {
            eventTime = SystemClock.uptimeMillis();
        }
        if (downTime == 0) {
            downTime = eventTime;
        }
        KeyEvent keyEvent2 = new KeyEvent(keyEvent);
        keyEvent2.setTime(downTime, eventTime);
        keyEvent2.setSource(source);
        keyEvent2.setFlags(keyEvent.getFlags() | 8);
        setDisplayIfNeeded(keyEvent2);
        InputManagerGlobal.getInstance().injectInputEvent(keyEvent2, 2);
    }

    private void setDisplayIfNeeded(KeyEvent keyEvent) {
        if (UserManager.isVisibleBackgroundUsersEnabled()) {
            int displayId = keyEvent.getDisplayId();
            if (displayId != -1) {
                if (VERBOSE) {
                    Log.v(TAG, "setDisplayIfNeeded(" + keyEvent + "): not changing display id as it's explicitly set to " + displayId);
                    return;
                }
                return;
            }
            int mainDisplayIdAssignedToUser = ((UserManager) this.mInstrContext.getSystemService(UserManager.class)).getMainDisplayIdAssignedToUser();
            if (VERBOSE) {
                Log.v(TAG, "setDisplayIfNeeded(" + keyEvent + "): eventDisplayId=" + displayId + ", user=" + this.mInstrContext.getUser() + ", userDisplayId=" + mainDisplayIdAssignedToUser);
            }
            if (mainDisplayIdAssignedToUser == -1) {
                Log.e(TAG, "setDisplayIfNeeded(" + keyEvent + "): UserManager returned INVALID_DISPLAY as display assigned to user " + this.mInstrContext.getUser());
                return;
            }
            keyEvent.setDisplayId(mainDisplayIdAssignedToUser);
        }
    }

    public void sendKeyDownUpSync(int i) {
        sendKeySync(new KeyEvent(0, i));
        sendKeySync(new KeyEvent(1, i));
    }

    public void sendCharacterSync(int i) {
        sendKeyDownUpSync(i);
    }

    public void sendPointerSync(MotionEvent motionEvent) {
        validateNotAppThread();
        if ((motionEvent.getSource() & 2) == 0) {
            motionEvent.setSource(4098);
        }
        syncInputTransactionsAndInjectEventIntoSelf(motionEvent);
    }

    private void syncInputTransactionsAndInjectEventIntoSelf(MotionEvent motionEvent) {
        boolean z = motionEvent.getAction() == 0 || motionEvent.isFromSource(8194);
        boolean z2 = motionEvent.getAction() == 1;
        if (z) {
            try {
                WindowManagerGlobal.getWindowManagerService().syncInputTransactions(true);
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
                return;
            }
        }
        InputManagerGlobal.getInstance().injectInputEvent(motionEvent, 2, Process.myUid());
        if (z2) {
            WindowManagerGlobal.getWindowManagerService().syncInputTransactions(true);
        }
    }

    public void sendTrackballEventSync(MotionEvent motionEvent) {
        validateNotAppThread();
        if (!motionEvent.isFromSource(4)) {
            motionEvent.setSource(65540);
        }
        InputManagerGlobal.getInstance().injectInputEvent(motionEvent, 2);
    }

    public Application newApplication(ClassLoader classLoader, String str, Context context) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        Application instantiateApplication = getFactory(context.getPackageName()).instantiateApplication(classLoader, str);
        instantiateApplication.attach(context);
        return instantiateApplication;
    }

    public static Application newApplication(Class<?> cls, Context context) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        Application application = (Application) cls.newInstance();
        application.attach(context);
        return application;
    }

    public void callApplicationOnCreate(Application application) {
        application.onCreate();
    }

    public Activity newActivity(Class<?> cls, Context context, IBinder iBinder, Application application, Intent intent, ActivityInfo activityInfo, CharSequence charSequence, Activity activity, String str, Object obj) throws InstantiationException, IllegalAccessException {
        Activity activity2 = (Activity) cls.newInstance();
        activity2.attach(context, null, this, iBinder, 0, application == null ? new Application() : application, intent, activityInfo, charSequence, activity, str, (Activity.NonConfigurationInstances) obj, new Configuration(), null, null, null, null, null, null, null);
        return activity2;
    }

    public Activity newActivity(ClassLoader classLoader, String str, Intent intent) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        return getFactory((intent == null || intent.getComponent() == null) ? null : intent.getComponent().getPackageName()).instantiateActivity(classLoader, str, intent);
    }

    private AppComponentFactory getFactory(String str) {
        if (str == null) {
            Log.e(TAG, "No pkg specified, disabling AppComponentFactory");
            return AppComponentFactory.DEFAULT;
        }
        ActivityThread activityThread = this.mThread;
        if (activityThread == null) {
            Log.e(TAG, "Uninitialized ActivityThread, likely app-created Instrumentation, disabling AppComponentFactory", new Throwable());
            return AppComponentFactory.DEFAULT;
        }
        LoadedApk peekPackageInfo = activityThread.peekPackageInfo(str, true);
        if (peekPackageInfo == null) {
            peekPackageInfo = this.mThread.getSystemContext().mPackageInfo;
        }
        return peekPackageInfo.getAppFactory();
    }

    private void notifyStartActivityResult(int i, Bundle bundle) {
        if (this.mActivityMonitors == null) {
            return;
        }
        synchronized (this.mSync) {
            int size = this.mActivityMonitors.size();
            for (int i2 = 0; i2 < size; i2++) {
                ActivityMonitor activityMonitor = this.mActivityMonitors.get(i2);
                if (activityMonitor.ignoreMatchingSpecificIntents()) {
                    if (bundle == null) {
                        bundle = ActivityOptions.makeBasic().toBundle();
                    }
                    activityMonitor.onStartActivityResult(i, bundle);
                }
            }
        }
    }

    private void prePerformCreate(Activity activity) {
        if (this.mWaitingActivities != null) {
            synchronized (this.mSync) {
                int size = this.mWaitingActivities.size();
                for (int i = 0; i < size; i++) {
                    ActivityWaiter activityWaiter = this.mWaitingActivities.get(i);
                    if (activityWaiter.intent.filterEquals(activity.getIntent())) {
                        activityWaiter.activity = activity;
                        this.mMessageQueue.addIdleHandler(new ActivityGoing(activityWaiter));
                    }
                }
            }
        }
    }

    private void postPerformCreate(Activity activity) {
        if (this.mActivityMonitors != null) {
            synchronized (this.mSync) {
                int size = this.mActivityMonitors.size();
                for (int i = 0; i < size; i++) {
                    this.mActivityMonitors.get(i).match(activity, activity, activity.getIntent());
                }
            }
        }
    }

    public void callActivityOnCreate(Activity activity, Bundle bundle) {
        prePerformCreate(activity);
        activity.performCreate(bundle);
        postPerformCreate(activity);
    }

    public void callActivityOnCreate(Activity activity, Bundle bundle, PersistableBundle persistableBundle) {
        prePerformCreate(activity);
        activity.performCreate(bundle, persistableBundle);
        postPerformCreate(activity);
    }

    public void callActivityOnDestroy(Activity activity) {
        activity.performDestroy();
    }

    public void callActivityOnRestoreInstanceState(Activity activity, Bundle bundle) {
        activity.performRestoreInstanceState(bundle);
    }

    public void callActivityOnRestoreInstanceState(Activity activity, Bundle bundle, PersistableBundle persistableBundle) {
        activity.performRestoreInstanceState(bundle, persistableBundle);
    }

    public void callActivityOnPostCreate(Activity activity, Bundle bundle) {
        activity.onPostCreate(bundle);
    }

    public void callActivityOnPostCreate(Activity activity, Bundle bundle, PersistableBundle persistableBundle) {
        activity.onPostCreate(bundle, persistableBundle);
    }

    public void callActivityOnNewIntent(Activity activity, Intent intent) {
        if (com.android.internal.hidden_from_bootclasspath.android.security.Flags.contentUriPermissionApis()) {
            activity.performNewIntent(intent, new ComponentCaller(activity.getActivityToken(), null));
        } else {
            activity.performNewIntent(intent);
        }
    }

    public void callActivityOnNewIntent(Activity activity, Intent intent, ComponentCaller componentCaller) {
        activity.performNewIntent(intent, componentCaller);
    }

    public void callActivityOnNewIntent(Activity activity, ReferrerIntent referrerIntent, ComponentCaller componentCaller) {
        internalCallActivityOnNewIntent(activity, referrerIntent, componentCaller);
    }

    private void internalCallActivityOnNewIntent(Activity activity, ReferrerIntent referrerIntent, ComponentCaller componentCaller) {
        String str = activity.mReferrer;
        if (referrerIntent != null) {
            try {
                activity.mReferrer = referrerIntent.mReferrer;
            } catch (Throwable th) {
                activity.mReferrer = str;
                throw th;
            }
        }
        callActivityOnNewIntent(activity, referrerIntent != null ? new Intent(referrerIntent) : null, componentCaller);
        activity.mReferrer = str;
    }

    public void callActivityOnNewIntent(Activity activity, ReferrerIntent referrerIntent) {
        if (com.android.internal.hidden_from_bootclasspath.android.security.Flags.contentUriPermissionApis()) {
            internalCallActivityOnNewIntent(activity, referrerIntent, new ComponentCaller(activity.getActivityToken(), null));
            return;
        }
        String str = activity.mReferrer;
        if (referrerIntent != null) {
            try {
                activity.mReferrer = referrerIntent.mReferrer;
            } catch (Throwable th) {
                activity.mReferrer = str;
                throw th;
            }
        }
        callActivityOnNewIntent(activity, referrerIntent != null ? new Intent(referrerIntent) : null);
        activity.mReferrer = str;
    }

    public void callActivityOnStart(Activity activity) {
        activity.onStart();
    }

    public void callActivityOnRestart(Activity activity) {
        activity.onRestart();
    }

    public void callActivityOnResume(Activity activity) {
        activity.mResumed = true;
        activity.onResume();
        if (this.mActivityMonitors != null) {
            synchronized (this.mSync) {
                int size = this.mActivityMonitors.size();
                for (int i = 0; i < size; i++) {
                    this.mActivityMonitors.get(i).match(activity, activity, activity.getIntent());
                }
            }
        }
    }

    public void callActivityOnStop(Activity activity) {
        activity.onStop();
    }

    public void callActivityOnSaveInstanceState(Activity activity, Bundle bundle) {
        activity.performSaveInstanceState(bundle);
    }

    public void callActivityOnSaveInstanceState(Activity activity, Bundle bundle, PersistableBundle persistableBundle) {
        activity.performSaveInstanceState(bundle, persistableBundle);
    }

    public void callActivityOnPause(Activity activity) {
        activity.performPause();
    }

    public void callActivityOnUserLeaving(Activity activity) {
        activity.performUserLeaving();
    }

    public void callActivityOnPictureInPictureRequested(Activity activity) {
        activity.onPictureInPictureRequested();
    }

    @Deprecated
    public void startAllocCounting() {
        Runtime.getRuntime().gc();
        Runtime.getRuntime().runFinalization();
        Runtime.getRuntime().gc();
        Debug.resetAllCounts();
        Debug.startAllocCounting();
    }

    @Deprecated
    public void stopAllocCounting() {
        Runtime.getRuntime().gc();
        Runtime.getRuntime().runFinalization();
        Runtime.getRuntime().gc();
        Debug.stopAllocCounting();
    }

    private void addValue(String str, int i, Bundle bundle) {
        if (bundle.containsKey(str)) {
            ArrayList<Integer> integerArrayList = bundle.getIntegerArrayList(str);
            if (integerArrayList != null) {
                integerArrayList.add(Integer.valueOf(i));
                return;
            }
            return;
        }
        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(Integer.valueOf(i));
        bundle.putIntegerArrayList(str, arrayList);
    }

    public Bundle getAllocCounts() {
        Bundle bundle = new Bundle();
        bundle.putLong(PerformanceCollector.METRIC_KEY_GLOBAL_ALLOC_COUNT, Debug.getGlobalAllocCount());
        bundle.putLong(PerformanceCollector.METRIC_KEY_GLOBAL_ALLOC_SIZE, Debug.getGlobalAllocSize());
        bundle.putLong(PerformanceCollector.METRIC_KEY_GLOBAL_FREED_COUNT, Debug.getGlobalFreedCount());
        bundle.putLong(PerformanceCollector.METRIC_KEY_GLOBAL_FREED_SIZE, Debug.getGlobalFreedSize());
        bundle.putLong(PerformanceCollector.METRIC_KEY_GC_INVOCATION_COUNT, Debug.getGlobalGcInvocationCount());
        return bundle;
    }

    public Bundle getBinderCounts() {
        Bundle bundle = new Bundle();
        bundle.putLong(PerformanceCollector.METRIC_KEY_SENT_TRANSACTIONS, Debug.getBinderSentTransactions());
        bundle.putLong(PerformanceCollector.METRIC_KEY_RECEIVED_TRANSACTIONS, Debug.getBinderReceivedTransactions());
        return bundle;
    }

    public static final class ActivityResult {
        private final int mResultCode;
        private final Intent mResultData;

        public ActivityResult(int i, Intent intent) {
            this.mResultCode = i;
            this.mResultData = intent;
        }

        public int getResultCode() {
            return this.mResultCode;
        }

        public Intent getResultData() {
            return this.mResultData;
        }
    }

    public ActivityResult execStartActivity(Context context, IBinder iBinder, IBinder iBinder2, Activity activity, Intent intent, int i, Bundle bundle) {
        Bundle bundle2;
        ActivityResult activityResult;
        if (DEBUG_START_ACTIVITY) {
            StringBuilder sb = new StringBuilder("startActivity: who=");
            sb.append(context);
            sb.append(" source=");
            sb.append(activity);
            sb.append(" intent=");
            sb.append(intent);
            sb.append(" requestCode=");
            sb.append(i);
            sb.append(" options=");
            bundle2 = bundle;
            sb.append(bundle2);
            Log.d(TAG, sb.toString(), new Throwable());
        } else {
            bundle2 = bundle;
        }
        Objects.requireNonNull(intent);
        IApplicationThread iApplicationThread = (IApplicationThread) iBinder;
        Uri onProvideReferrer = activity != null ? activity.onProvideReferrer() : null;
        if (onProvideReferrer != null) {
            intent.putExtra(Intent.EXTRA_REFERRER, onProvideReferrer);
        }
        if (isSdkSandboxAllowedToStartActivities()) {
            adjustIntentForCtsInSdkSandboxInstrumentation(intent);
        }
        if (this.mActivityMonitors != null) {
            synchronized (this.mSync) {
                int size = this.mActivityMonitors.size();
                int i2 = 0;
                while (true) {
                    if (i2 >= size) {
                        break;
                    }
                    ActivityMonitor activityMonitor = this.mActivityMonitors.get(i2);
                    if (activityMonitor.ignoreMatchingSpecificIntents()) {
                        if (bundle2 == null) {
                            bundle2 = ActivityOptions.makeBasic().toBundle();
                        }
                        activityResult = activityMonitor.onStartActivity(context, intent, bundle2);
                    } else {
                        activityResult = null;
                    }
                    if (activityResult != null) {
                        activityMonitor.mHits++;
                        return activityResult;
                    }
                    if (activityMonitor.match(context, null, intent)) {
                        activityMonitor.mHits++;
                        if (activityMonitor.isBlocking()) {
                            return i >= 0 ? activityMonitor.getResult() : null;
                        }
                    } else {
                        i2++;
                    }
                }
            }
        }
        Bundle bundle3 = bundle2;
        try {
            if (CoreRune.FW_FLIP_FULL_COVER_SCREEN_APPS_CUTOUT && activity != null && activity.mActivityInfo != null && activity.mActivityInfo.isLaunchedFromAppsCoverLauncher) {
                intent.putExtra(Intent.EXTRA_IS_LAUNCHED_FROM_APPS_COVER_LAUNCHER, true);
            }
            if (CoreRune.FW_FLIP_FULL_COVER_SCREEN_APP_COMPAT && activity != null && activity.mActivityInfo != null && activity.mActivityInfo.isLaunchedFromMultistarCoverLauncher) {
                intent.putExtra(Intent.EXTRA_IS_LAUNCHED_FROM_MULTISTAR_COVER_LAUNCHER, true);
            }
            intent.migrateExtraStreamToClipData(context);
            intent.prepareToLeaveProcess(context);
            int startActivity = ActivityTaskManager.getService().startActivity(iApplicationThread, context.getOpPackageName(), context.getAttributionTag(), intent, intent.resolveTypeIfNeeded(context.getContentResolver()), iBinder2, activity != null ? activity.mEmbeddedID : null, i, 0, null, bundle3);
            notifyStartActivityResult(startActivity, bundle3);
            checkStartActivityResult(startActivity, intent);
            return null;
        } catch (RemoteException e) {
            throw new RuntimeException("Failure from system", e);
        }
    }

    public void execStartActivities(Context context, IBinder iBinder, IBinder iBinder2, Activity activity, Intent[] intentArr, Bundle bundle) {
        execStartActivitiesAsUser(context, iBinder, iBinder2, activity, intentArr, bundle, context.getUserId());
    }

    public int execStartActivitiesAsUser(Context context, IBinder iBinder, IBinder iBinder2, Activity activity, Intent[] intentArr, Bundle bundle, int i) {
        Bundle bundle2;
        int i2;
        ActivityResult activityResult;
        if (DEBUG_START_ACTIVITY) {
            StringJoiner stringJoiner = new StringJoiner(", ");
            for (Intent intent : intentArr) {
                stringJoiner.add(intent.toString());
            }
            StringBuilder sb = new StringBuilder("startActivities: who=");
            sb.append(context);
            sb.append(" source=");
            sb.append(activity);
            sb.append(" userId=");
            i2 = i;
            sb.append(i2);
            sb.append(" intents=[");
            sb.append(stringJoiner);
            sb.append("] options=");
            bundle2 = bundle;
            sb.append(bundle2);
            Log.d(TAG, sb.toString(), new Throwable());
        } else {
            bundle2 = bundle;
            i2 = i;
        }
        Objects.requireNonNull(intentArr);
        for (int length = intentArr.length - 1; length >= 0; length--) {
            Objects.requireNonNull(intentArr[length]);
        }
        IApplicationThread iApplicationThread = (IApplicationThread) iBinder;
        if (isSdkSandboxAllowedToStartActivities()) {
            for (Intent intent2 : intentArr) {
                adjustIntentForCtsInSdkSandboxInstrumentation(intent2);
            }
        }
        if (this.mActivityMonitors != null) {
            synchronized (this.mSync) {
                int size = this.mActivityMonitors.size();
                int i3 = 0;
                while (true) {
                    if (i3 >= size) {
                        break;
                    }
                    ActivityMonitor activityMonitor = this.mActivityMonitors.get(i3);
                    if (activityMonitor.ignoreMatchingSpecificIntents()) {
                        if (bundle2 == null) {
                            bundle2 = ActivityOptions.makeBasic().toBundle();
                        }
                        activityResult = activityMonitor.onStartActivity(context, intentArr[0], bundle2);
                    } else {
                        activityResult = null;
                    }
                    if (activityResult != null) {
                        activityMonitor.mHits++;
                        return -96;
                    }
                    if (activityMonitor.match(context, null, intentArr[0])) {
                        activityMonitor.mHits++;
                        if (activityMonitor.isBlocking()) {
                            return -96;
                        }
                    } else {
                        i3++;
                    }
                }
            }
        }
        Bundle bundle3 = bundle2;
        try {
            if (CoreRune.FW_FLIP_FULL_COVER_SCREEN_APPS_CUTOUT && activity != null && activity.mActivityInfo != null && activity.mActivityInfo.isLaunchedFromAppsCoverLauncher) {
                for (Intent intent3 : intentArr) {
                    intent3.putExtra(Intent.EXTRA_IS_LAUNCHED_FROM_APPS_COVER_LAUNCHER, true);
                }
            }
            if (CoreRune.FW_FLIP_FULL_COVER_SCREEN_APP_COMPAT && activity != null && activity.mActivityInfo != null && activity.mActivityInfo.isLaunchedFromMultistarCoverLauncher) {
                for (Intent intent4 : intentArr) {
                    intent4.putExtra(Intent.EXTRA_IS_LAUNCHED_FROM_MULTISTAR_COVER_LAUNCHER, true);
                }
            }
            String[] strArr = new String[intentArr.length];
            for (int i4 = 0; i4 < intentArr.length; i4++) {
                intentArr[i4].migrateExtraStreamToClipData(context);
                intentArr[i4].prepareToLeaveProcess(context);
                strArr[i4] = intentArr[i4].resolveTypeIfNeeded(context.getContentResolver());
            }
            int startActivities = ActivityTaskManager.getService().startActivities(iApplicationThread, context.getOpPackageName(), context.getAttributionTag(), intentArr, strArr, iBinder2, bundle3, i2);
            notifyStartActivityResult(startActivities, bundle3);
            checkStartActivityResult(startActivities, intentArr[0]);
            return startActivities;
        } catch (RemoteException e) {
            throw new RuntimeException("Failure from system", e);
        }
    }

    public ActivityResult execStartActivity(Context context, IBinder iBinder, IBinder iBinder2, String str, Intent intent, int i, Bundle bundle) {
        String str2;
        Bundle bundle2;
        ActivityResult activityResult;
        if (DEBUG_START_ACTIVITY) {
            StringBuilder sb = new StringBuilder("startActivity: who=");
            sb.append(context);
            sb.append(" target=");
            str2 = str;
            sb.append(str2);
            sb.append(" intent=");
            sb.append(intent);
            sb.append(" requestCode=");
            sb.append(i);
            sb.append(" options=");
            bundle2 = bundle;
            sb.append(bundle2);
            Log.d(TAG, sb.toString(), new Throwable());
        } else {
            str2 = str;
            bundle2 = bundle;
        }
        Objects.requireNonNull(intent);
        IApplicationThread iApplicationThread = (IApplicationThread) iBinder;
        if (isSdkSandboxAllowedToStartActivities()) {
            adjustIntentForCtsInSdkSandboxInstrumentation(intent);
        }
        if (this.mActivityMonitors != null) {
            synchronized (this.mSync) {
                int size = this.mActivityMonitors.size();
                int i2 = 0;
                while (true) {
                    if (i2 >= size) {
                        break;
                    }
                    ActivityMonitor activityMonitor = this.mActivityMonitors.get(i2);
                    if (activityMonitor.ignoreMatchingSpecificIntents()) {
                        if (bundle2 == null) {
                            bundle2 = ActivityOptions.makeBasic().toBundle();
                        }
                        activityResult = activityMonitor.onStartActivity(context, intent, bundle2);
                    } else {
                        activityResult = null;
                    }
                    if (activityResult != null) {
                        activityMonitor.mHits++;
                        return activityResult;
                    }
                    if (activityMonitor.match(context, null, intent)) {
                        activityMonitor.mHits++;
                        if (activityMonitor.isBlocking()) {
                            return i >= 0 ? activityMonitor.getResult() : null;
                        }
                    } else {
                        i2++;
                    }
                }
            }
        }
        Bundle bundle3 = bundle2;
        try {
            intent.migrateExtraStreamToClipData(context);
            intent.prepareToLeaveProcess(context);
            int startActivity = ActivityTaskManager.getService().startActivity(iApplicationThread, context.getOpPackageName(), context.getAttributionTag(), intent, intent.resolveTypeIfNeeded(context.getContentResolver()), iBinder2, str2, i, 0, null, bundle3);
            notifyStartActivityResult(startActivity, bundle3);
            checkStartActivityResult(startActivity, intent);
            return null;
        } catch (RemoteException e) {
            throw new RuntimeException("Failure from system", e);
        }
    }

    public ActivityResult execStartActivity(Context context, IBinder iBinder, IBinder iBinder2, String str, Intent intent, int i, Bundle bundle, UserHandle userHandle) {
        String str2;
        Bundle bundle2;
        ActivityResult activityResult;
        if (DEBUG_START_ACTIVITY) {
            StringBuilder sb = new StringBuilder("startActivity: who=");
            sb.append(context);
            sb.append(" user=");
            sb.append(userHandle);
            sb.append(" intent=");
            sb.append(intent);
            sb.append(" requestCode=");
            sb.append(i);
            sb.append(" resultWho=");
            str2 = str;
            sb.append(str2);
            sb.append(" options=");
            bundle2 = bundle;
            sb.append(bundle2);
            Log.d(TAG, sb.toString(), new Throwable());
        } else {
            str2 = str;
            bundle2 = bundle;
        }
        Objects.requireNonNull(intent);
        IApplicationThread iApplicationThread = (IApplicationThread) iBinder;
        if (isSdkSandboxAllowedToStartActivities()) {
            adjustIntentForCtsInSdkSandboxInstrumentation(intent);
        }
        if (this.mActivityMonitors != null) {
            synchronized (this.mSync) {
                int size = this.mActivityMonitors.size();
                int i2 = 0;
                while (true) {
                    if (i2 >= size) {
                        break;
                    }
                    ActivityMonitor activityMonitor = this.mActivityMonitors.get(i2);
                    if (activityMonitor.ignoreMatchingSpecificIntents()) {
                        if (bundle2 == null) {
                            bundle2 = ActivityOptions.makeBasic().toBundle();
                        }
                        activityResult = activityMonitor.onStartActivity(context, intent, bundle2);
                    } else {
                        activityResult = null;
                    }
                    if (activityResult != null) {
                        activityMonitor.mHits++;
                        return activityResult;
                    }
                    if (activityMonitor.match(context, null, intent)) {
                        activityMonitor.mHits++;
                        if (activityMonitor.isBlocking()) {
                            return i >= 0 ? activityMonitor.getResult() : null;
                        }
                    } else {
                        i2++;
                    }
                }
            }
        }
        Bundle bundle3 = bundle2;
        try {
            intent.migrateExtraStreamToClipData(context);
            intent.prepareToLeaveProcess(context);
            int startActivityAsUser = ActivityTaskManager.getService().startActivityAsUser(iApplicationThread, context.getOpPackageName(), context.getAttributionTag(), intent, intent.resolveTypeIfNeeded(context.getContentResolver()), iBinder2, str2, i, 0, null, bundle3, userHandle.getIdentifier());
            notifyStartActivityResult(startActivityAsUser, bundle3);
            checkStartActivityResult(startActivityAsUser, intent);
            return null;
        } catch (RemoteException e) {
            throw new RuntimeException("Failure from system", e);
        }
    }

    public ActivityResult execStartActivityAsCaller(Context context, IBinder iBinder, IBinder iBinder2, Activity activity, Intent intent, int i, Bundle bundle, boolean z, int i2) {
        Bundle bundle2;
        boolean z2;
        int i3;
        ActivityResult activityResult;
        if (DEBUG_START_ACTIVITY) {
            StringBuilder sb = new StringBuilder("startActivity: who=");
            sb.append(context);
            sb.append(" source=");
            sb.append(activity);
            sb.append(" userId=");
            i3 = i2;
            sb.append(i3);
            sb.append(" intent=");
            sb.append(intent);
            sb.append(" requestCode=");
            sb.append(i);
            sb.append(" ignoreTargetSecurity=");
            z2 = z;
            sb.append(z2);
            sb.append(" options=");
            bundle2 = bundle;
            sb.append(bundle2);
            Log.d(TAG, sb.toString(), new Throwable());
        } else {
            bundle2 = bundle;
            z2 = z;
            i3 = i2;
        }
        Objects.requireNonNull(intent);
        IApplicationThread iApplicationThread = (IApplicationThread) iBinder;
        if (isSdkSandboxAllowedToStartActivities()) {
            adjustIntentForCtsInSdkSandboxInstrumentation(intent);
        }
        if (this.mActivityMonitors != null) {
            synchronized (this.mSync) {
                int size = this.mActivityMonitors.size();
                int i4 = 0;
                while (true) {
                    if (i4 >= size) {
                        break;
                    }
                    ActivityMonitor activityMonitor = this.mActivityMonitors.get(i4);
                    if (activityMonitor.ignoreMatchingSpecificIntents()) {
                        if (bundle2 == null) {
                            bundle2 = ActivityOptions.makeBasic().toBundle();
                        }
                        activityResult = activityMonitor.onStartActivity(context, intent, bundle2);
                    } else {
                        activityResult = null;
                    }
                    if (activityResult != null) {
                        activityMonitor.mHits++;
                        return activityResult;
                    }
                    if (activityMonitor.match(context, null, intent)) {
                        activityMonitor.mHits++;
                        if (activityMonitor.isBlocking()) {
                            return i >= 0 ? activityMonitor.getResult() : null;
                        }
                    } else {
                        i4++;
                    }
                }
            }
        }
        Bundle bundle3 = bundle2;
        try {
            if (CoreRune.FW_FLIP_FULL_COVER_SCREEN_APPS_CUTOUT && activity != null && activity.mActivityInfo != null && activity.mActivityInfo.isLaunchedFromAppsCoverLauncher) {
                intent.putExtra(Intent.EXTRA_IS_LAUNCHED_FROM_APPS_COVER_LAUNCHER, true);
            }
            if (CoreRune.FW_FLIP_FULL_COVER_SCREEN_APP_COMPAT && activity != null && activity.mActivityInfo != null && activity.mActivityInfo.isLaunchedFromMultistarCoverLauncher) {
                intent.putExtra(Intent.EXTRA_IS_LAUNCHED_FROM_MULTISTAR_COVER_LAUNCHER, true);
            }
            intent.migrateExtraStreamToClipData(context);
            intent.prepareToLeaveProcess(context);
            int startActivityAsCaller = ActivityTaskManager.getService().startActivityAsCaller(iApplicationThread, context.getOpPackageName(), intent, intent.resolveTypeIfNeeded(context.getContentResolver()), iBinder2, activity != null ? activity.mEmbeddedID : null, i, 0, null, bundle3, z2, i3);
            notifyStartActivityResult(startActivityAsCaller, bundle3);
            checkStartActivityResult(startActivityAsCaller, intent);
            return null;
        } catch (RemoteException e) {
            throw new RuntimeException("Failure from system", e);
        }
    }

    public void execStartActivityFromAppTask(Context context, IBinder iBinder, IAppTask iAppTask, Intent intent, Bundle bundle) {
        ActivityResult activityResult;
        if (DEBUG_START_ACTIVITY) {
            Log.d(TAG, "startActivity: who=" + context + " intent=" + intent + " options=" + bundle, new Throwable());
        }
        Objects.requireNonNull(intent);
        IApplicationThread iApplicationThread = (IApplicationThread) iBinder;
        if (isSdkSandboxAllowedToStartActivities()) {
            adjustIntentForCtsInSdkSandboxInstrumentation(intent);
        }
        if (this.mActivityMonitors != null) {
            synchronized (this.mSync) {
                int size = this.mActivityMonitors.size();
                int i = 0;
                while (true) {
                    if (i >= size) {
                        break;
                    }
                    ActivityMonitor activityMonitor = this.mActivityMonitors.get(i);
                    if (activityMonitor.ignoreMatchingSpecificIntents()) {
                        if (bundle == null) {
                            bundle = ActivityOptions.makeBasic().toBundle();
                        }
                        activityResult = activityMonitor.onStartActivity(context, intent, bundle);
                    } else {
                        activityResult = null;
                    }
                    if (activityResult != null) {
                        activityMonitor.mHits++;
                        return;
                    } else if (activityMonitor.match(context, null, intent)) {
                        activityMonitor.mHits++;
                        if (activityMonitor.isBlocking()) {
                            return;
                        }
                    } else {
                        i++;
                    }
                }
            }
        }
        Bundle bundle2 = bundle;
        try {
            intent.migrateExtraStreamToClipData(context);
            intent.prepareToLeaveProcess(context);
            int startActivity = iAppTask.startActivity(iApplicationThread.asBinder(), context.getOpPackageName(), context.getAttributionTag(), intent, intent.resolveTypeIfNeeded(context.getContentResolver()), bundle2);
            notifyStartActivityResult(startActivity, bundle2);
            checkStartActivityResult(startActivity, intent);
        } catch (RemoteException e) {
            throw new RuntimeException("Failure from system", e);
        }
    }

    final void init(ActivityThread activityThread, Context context, Context context2, ComponentName componentName, IInstrumentationWatcher iInstrumentationWatcher, IUiAutomationConnection iUiAutomationConnection) {
        this.mThread = activityThread;
        activityThread.getLooper();
        this.mMessageQueue = Looper.myQueue();
        this.mInstrContext = context;
        this.mAppContext = context2;
        this.mComponent = componentName;
        this.mWatcher = iInstrumentationWatcher;
        this.mUiAutomationConnection = iUiAutomationConnection;
    }

    final void basicInit(ActivityThread activityThread) {
        this.mThread = activityThread;
    }

    public final void basicInit(Context context, Context context2, UiAutomation uiAutomation) {
        this.mInstrContext = context;
        this.mAppContext = context2;
        this.mUiAutomation = uiAutomation;
    }

    public static void checkStartActivityResult(int i, Object obj) {
        if (ActivityManager.isStartResultFatalError(i) && i != -200) {
            switch (i) {
                case -102:
                    return;
                case -101:
                    Log.d(TAG, "checkStartActivityResult() : mdm admin has blocked start activity " + obj);
                    return;
                case -100:
                    throw new IllegalStateException("Cannot start voice activity on a hidden session");
                case ActivityManager.START_VOICE_NOT_ACTIVE_SESSION /* -99 */:
                    throw new IllegalStateException("Session calling startVoiceActivity does not match active session");
                default:
                    switch (i) {
                        case ActivityManager.START_NOT_VOICE_COMPATIBLE /* -97 */:
                            throw new SecurityException("Starting under voice control not allowed for: " + obj);
                        case ActivityManager.START_CANCELED /* -96 */:
                            throw new AndroidRuntimeException("Activity could not be started for " + obj);
                        case ActivityManager.START_NOT_ACTIVITY /* -95 */:
                            throw new IllegalArgumentException("PendingIntent is not an activity");
                        case ActivityManager.START_PERMISSION_DENIED /* -94 */:
                            throw new SecurityException("Not allowed to start activity " + obj);
                        case ActivityManager.START_FORWARD_AND_REQUEST_CONFLICT /* -93 */:
                            throw new AndroidRuntimeException("FORWARD_RESULT_FLAG used while also requesting a result");
                        case -92:
                        case -91:
                            if (obj instanceof Intent) {
                                Intent intent = (Intent) obj;
                                if (intent.getComponent() != null) {
                                    throw new ActivityNotFoundException("Unable to find explicit activity class " + intent.getComponent().toShortString() + "; have you declared this activity in your AndroidManifest.xml, or does your intent not match its declared <intent-filter>?");
                                }
                            }
                            throw new ActivityNotFoundException("No Activity found to handle " + obj);
                        case -90:
                            throw new IllegalStateException("Cannot start assistant activity on a hidden session");
                        case ActivityManager.START_ASSISTANT_NOT_ACTIVE_SESSION /* -89 */:
                            throw new IllegalStateException("Session calling startAssistantActivity does not match active session");
                        default:
                            throw new AndroidRuntimeException("Unknown error code " + i + " when starting " + obj);
                    }
            }
        }
    }

    private void validateNotAppThread() {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw new RuntimeException("This method can not be called from the main application thread");
        }
    }

    public UiAutomation getUiAutomation() {
        return getUiAutomation(0);
    }

    public UiAutomation getUiAutomation(int i) {
        UiAutomation uiAutomation = this.mUiAutomation;
        boolean z = uiAutomation == null || uiAutomation.isDestroyed();
        if (this.mUiAutomationConnection != null) {
            if (!z && this.mUiAutomation.getFlags() == i) {
                return this.mUiAutomation;
            }
            if (z) {
                this.mUiAutomation = new UiAutomation(getTargetContext(), this.mUiAutomationConnection);
            } else {
                this.mUiAutomation.disconnect();
            }
            if (getTargetContext().getApplicationInfo().targetSdkVersion <= 30) {
                this.mUiAutomation.connect(i);
                return this.mUiAutomation;
            }
            long uptimeMillis = SystemClock.uptimeMillis();
            try {
                this.mUiAutomation.connectWithTimeout(i, 60000L);
                return this.mUiAutomation;
            } catch (TimeoutException e) {
                Log.e(TAG, "Unable to connect to UiAutomation. Waited for " + (SystemClock.uptimeMillis() - uptimeMillis) + " ms", e);
                this.mUiAutomation.destroy();
                this.mUiAutomation = null;
            }
        }
        return null;
    }

    private UiAutomation getUiAutomation$ravenwood(int i) {
        return this.mUiAutomation;
    }

    public TestLooperManager acquireLooperManager(Looper looper) {
        checkInstrumenting("acquireLooperManager");
        return new TestLooperManager(looper);
    }

    private final class InstrumentationThread extends Thread {
        public InstrumentationThread(String str) {
            super(str);
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            try {
                Process.setThreadPriority(-8);
            } catch (RuntimeException e) {
                Log.w(Instrumentation.TAG, "Exception setting priority of instrumentation thread " + Process.myTid(), e);
            }
            if (Instrumentation.this.mAutomaticPerformanceSnapshots) {
                Instrumentation.this.startPerformanceSnapshot();
            }
            Instrumentation.this.onStart();
        }
    }

    private static final class EmptyRunnable implements Runnable {
        @Override // java.lang.Runnable
        public void run() {
        }

        private EmptyRunnable() {
        }
    }

    private static final class SyncRunnable implements Runnable {
        private boolean mComplete;
        private final Runnable mTarget;

        public SyncRunnable(Runnable runnable) {
            this.mTarget = runnable;
        }

        @Override // java.lang.Runnable
        public void run() {
            this.mTarget.run();
            synchronized (this) {
                this.mComplete = true;
                notifyAll();
            }
        }

        public void waitForComplete() {
            synchronized (this) {
                while (!this.mComplete) {
                    try {
                        wait();
                    } catch (InterruptedException unused) {
                    }
                }
            }
        }
    }

    private static final class ActivityWaiter {
        public Activity activity;
        public final Intent intent;

        public ActivityWaiter(Intent intent) {
            this.intent = intent;
        }
    }

    private final class ActivityGoing implements MessageQueue.IdleHandler {
        private final ActivityWaiter mWaiter;

        public ActivityGoing(ActivityWaiter activityWaiter) {
            this.mWaiter = activityWaiter;
        }

        @Override // android.os.MessageQueue.IdleHandler
        public final boolean queueIdle() {
            synchronized (Instrumentation.this.mSync) {
                Instrumentation.this.mWaitingActivities.remove(this.mWaiter);
                Instrumentation.this.mSync.notifyAll();
            }
            return false;
        }
    }

    private static final class Idler implements MessageQueue.IdleHandler {
        private final Runnable mCallback;
        private boolean mIdle = false;

        public Idler(Runnable runnable) {
            this.mCallback = runnable;
        }

        @Override // android.os.MessageQueue.IdleHandler
        public final boolean queueIdle() {
            Runnable runnable = this.mCallback;
            if (runnable != null) {
                runnable.run();
            }
            synchronized (this) {
                this.mIdle = true;
                notifyAll();
            }
            return false;
        }

        public void waitForIdle() {
            synchronized (this) {
                while (!this.mIdle) {
                    try {
                        wait();
                    } catch (InterruptedException unused) {
                    }
                }
            }
        }
    }
}
