package android.app;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.hardware.input.InputManagerGlobal;
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
import android.os.UserManager;
import android.util.AndroidRuntimeException;
import android.util.Log;
import android.view.IWindowManager;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceControl;
import android.view.ViewConfiguration;
import android.view.Window;
import android.view.WindowManagerGlobal;

import com.android.internal.R;
import com.android.internal.content.ReferrerIntent;

import com.samsung.android.core.AppJumpBlockTool;
import com.samsung.android.rune.CoreRune;

import java.io.File;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.concurrent.TimeoutException;

public class Instrumentation {
    private static final long CONNECT_TIMEOUT_MILLIS = 60000;
    static final boolean DEBUG_START_ACTIVITY;
    public static final String REPORT_KEY_IDENTIFIER = "id";
    public static final String REPORT_KEY_STREAMRESULT = "stream";
    private static final String TAG = "Instrumentation";
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
    private final Object mAnimationCompleteLock = new Object();

    @Retention(RetentionPolicy.SOURCE)
    public @interface UiAutomationFlags {}

    static {
        boolean z = false;
        if (Build.IS_DEBUGGABLE
                && SystemProperties.getBoolean("persist.wm.debug.start_activity", false)) {
            z = true;
        }
        DEBUG_START_ACTIVITY = z;
    }

    private void checkInstrumenting(String method) {
        if (this.mInstrContext == null) {
            throw new RuntimeException(
                    method + " cannot be called outside of instrumented processes");
        }
    }

    public boolean isInstrumenting() {
        if (this.mInstrContext == null) {
            return false;
        }
        return true;
    }

    public void onCreate(Bundle arguments) {}

    public void start() {
        if (this.mRunner != null) {
            throw new RuntimeException("Instrumentation already started");
        }
        this.mRunner = new InstrumentationThread("Instr: " + getClass().getName());
        this.mRunner.start();
    }

    public void onStart() {}

    public boolean onException(Object obj, Throwable e) {
        return false;
    }

    public void sendStatus(int resultCode, Bundle results) {
        if (this.mWatcher != null) {
            try {
                this.mWatcher.instrumentationStatus(this.mComponent, resultCode, results);
            } catch (RemoteException e) {
                this.mWatcher = null;
            }
        }
    }

    public void addResults(Bundle results) {
        IActivityManager am = ActivityManager.getService();
        try {
            am.addInstrumentationResults(this.mThread.getApplicationThread(), results);
        } catch (RemoteException ex) {
            throw ex.rethrowFromSystemServer();
        }
    }

    public void finish(int resultCode, Bundle results) {
        if (this.mAutomaticPerformanceSnapshots) {
            endPerformanceSnapshot();
        }
        if (this.mPerfMetrics != null) {
            if (results == null) {
                results = new Bundle();
            }
            results.putAll(this.mPerfMetrics);
        }
        if (this.mUiAutomation != null && !this.mUiAutomation.isDestroyed()) {
            this.mUiAutomation.disconnect();
            this.mUiAutomation = null;
        }
        this.mThread.finishInstrumentation(resultCode, results);
    }

    public void setAutomaticPerformanceSnapshots() {
        this.mAutomaticPerformanceSnapshots = true;
        this.mPerformanceCollector = new PerformanceCollector();
    }

    public void startPerformanceSnapshot() {
        if (!isProfiling()) {
            this.mPerformanceCollector.beginSnapshot(null);
        }
    }

    public void endPerformanceSnapshot() {
        if (!isProfiling()) {
            this.mPerfMetrics = this.mPerformanceCollector.endSnapshot();
        }
    }

    public void onDestroy() {}

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

    public void setInTouchMode(boolean inTouch) {
        try {
            IWindowManager.Stub.asInterface(ServiceManager.getService(Context.WINDOW_SERVICE))
                    .setInTouchModeOnAllDisplays(inTouch);
        } catch (RemoteException e) {
        }
    }

    public void resetInTouchMode() {
        boolean defaultInTouchMode =
                getContext().getResources().getBoolean(R.bool.config_defaultInTouchMode);
        setInTouchMode(defaultInTouchMode);
    }

    public void waitForIdle(Runnable recipient) {
        this.mMessageQueue.addIdleHandler(new Idler(recipient));
        this.mThread.getHandler().post(new EmptyRunnable());
    }

    public void waitForIdleSync() {
        validateNotAppThread();
        Idler idler = new Idler(null);
        this.mMessageQueue.addIdleHandler(idler);
        this.mThread.getHandler().post(new EmptyRunnable());
        idler.waitForIdle();
    }

    private void waitForEnterAnimationComplete(Activity activity) {
        synchronized (this.mAnimationCompleteLock) {
            long timeout = 5000;
            while (timeout > 0) {
                try {
                    if (activity.mEnterAnimationComplete) {
                        break;
                    }
                    long startTime = System.currentTimeMillis();
                    this.mAnimationCompleteLock.wait(timeout);
                    long totalTime = System.currentTimeMillis() - startTime;
                    timeout -= totalTime;
                } catch (InterruptedException e) {
                }
            }
        }
    }

    public void onEnterAnimationComplete() {
        synchronized (this.mAnimationCompleteLock) {
            this.mAnimationCompleteLock.notifyAll();
        }
    }

    public void runOnMainSync(Runnable runner) {
        validateNotAppThread();
        SyncRunnable sr = new SyncRunnable(runner);
        this.mThread.getHandler().post(sr);
        sr.waitForComplete();
    }

    boolean isSdkSandboxAllowedToStartActivities() {
        return Process.isSdkSandbox()
                && this.mThread != null
                && this.mThread.mBoundApplication != null
                && this.mThread.mBoundApplication.isSdkInSandbox
                && getContext() != null
                && getContext()
                                .checkSelfPermission(
                                        Manifest.permission.START_ACTIVITIES_FROM_SDK_SANDBOX)
                        == 0;
    }

    private void adjustIntentForCtsInSdkSandboxInstrumentation(Intent intent) {
        if (this.mComponent != null
                && intent.getComponent() != null
                && getContext()
                        .getPackageManager()
                        .getSdkSandboxPackageName()
                        .equals(intent.getComponent().getPackageName())) {
            intent.setComponent(
                    new ComponentName(
                            this.mComponent.getPackageName(),
                            intent.getComponent().getClassName()));
        }
        intent.setIdentifier(this.mComponent.getPackageName());
    }

    private ActivityInfo resolveActivityInfoForCtsInSandbox(Intent intent) {
        adjustIntentForCtsInSdkSandboxInstrumentation(intent);
        ActivityInfo ai = intent.resolveActivityInfo(getTargetContext().getPackageManager(), 0);
        if (ai != null) {
            ai.processName = this.mThread.getProcessName();
        }
        return ai;
    }

    public Activity startActivitySync(Intent intent) {
        return startActivitySync(intent, null);
    }

    public Activity startActivitySync(Intent intent, Bundle options) {
        ActivityInfo ai;
        Activity activity;
        if (DEBUG_START_ACTIVITY) {
            Log.d(TAG, "startActivity: intent=" + intent + " options=" + options, new Throwable());
        }
        validateNotAppThread();
        synchronized (this.mSync) {
            Intent intent2 = new Intent(intent);
            if (isSdkSandboxAllowedToStartActivities()) {
                ai = resolveActivityInfoForCtsInSandbox(intent2);
            } else {
                ai = intent2.resolveActivityInfo(getTargetContext().getPackageManager(), 0);
            }
            if (ai == null) {
                throw new RuntimeException("Unable to resolve activity for: " + intent2);
            }
            String myProc = this.mThread.getProcessName();
            if (!ai.processName.equals(myProc)) {
                throw new RuntimeException(
                        "Intent in process "
                                + myProc
                                + " resolved to different process "
                                + ai.processName
                                + ": "
                                + intent2);
            }
            intent2.setComponent(new ComponentName(ai.applicationInfo.packageName, ai.name));
            ActivityWaiter aw = new ActivityWaiter(intent2);
            if (this.mWaitingActivities == null) {
                this.mWaitingActivities = new ArrayList();
            }
            this.mWaitingActivities.add(aw);
            getTargetContext().startActivity(intent2, options);
            do {
                try {
                    this.mSync.wait();
                } catch (InterruptedException e) {
                }
            } while (this.mWaitingActivities.contains(aw));
            activity = aw.activity;
        }
        waitForEnterAnimationComplete(activity);
        SurfaceControl.Transaction t = new SurfaceControl.Transaction();
        try {
            t.apply(true);
            t.close();
            return activity;
        } catch (Throwable th) {
            try {
                t.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    public static class ActivityMonitor {
        private final boolean mBlock;
        private final String mClass;
        int mHits;
        private final boolean mIgnoreMatchingSpecificIntents;
        Activity mLastActivity;
        private final ActivityResult mResult;
        private final IntentFilter mWhich;

        public ActivityMonitor(IntentFilter which, ActivityResult result, boolean block) {
            this.mHits = 0;
            this.mLastActivity = null;
            this.mWhich = which;
            this.mClass = null;
            this.mResult = result;
            this.mBlock = block;
            this.mIgnoreMatchingSpecificIntents = false;
        }

        public ActivityMonitor(String cls, ActivityResult result, boolean block) {
            this.mHits = 0;
            this.mLastActivity = null;
            this.mWhich = null;
            this.mClass = cls;
            this.mResult = result;
            this.mBlock = block;
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
            Activity res;
            synchronized (this) {
                while (this.mLastActivity == null) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                    }
                }
                res = this.mLastActivity;
                this.mLastActivity = null;
            }
            return res;
        }

        public final Activity waitForActivityWithTimeout(long timeOut) {
            synchronized (this) {
                if (this.mLastActivity == null) {
                    try {
                        wait(timeOut);
                    } catch (InterruptedException e) {
                    }
                }
                if (this.mLastActivity == null) {
                    return null;
                }
                Activity res = this.mLastActivity;
                this.mLastActivity = null;
                return res;
            }
        }

        public ActivityResult onStartActivity(Context who, Intent intent, Bundle options) {
            return onStartActivity(intent);
        }

        public ActivityResult onStartActivity(Intent intent) {
            return null;
        }

        public void onStartActivityResult(int result, Bundle bOptions) {}

        final boolean match(Context who, Activity activity, Intent intent) {
            if (this.mIgnoreMatchingSpecificIntents) {
                return false;
            }
            synchronized (this) {
                if (this.mWhich != null
                        && this.mWhich.match(
                                        who.getContentResolver(), intent, true, Instrumentation.TAG)
                                < 0) {
                    return false;
                }
                if (this.mClass != null) {
                    String cls = null;
                    if (activity != null) {
                        cls = activity.getClass().getName();
                    } else if (intent.getComponent() != null) {
                        cls = intent.getComponent().getClassName();
                    }
                    if (cls == null || !this.mClass.equals(cls)) {
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

    public void addMonitor(ActivityMonitor monitor) {
        synchronized (this.mSync) {
            if (this.mActivityMonitors == null) {
                this.mActivityMonitors = new ArrayList();
            }
            this.mActivityMonitors.add(monitor);
        }
    }

    public ActivityMonitor addMonitor(IntentFilter filter, ActivityResult result, boolean block) {
        ActivityMonitor am = new ActivityMonitor(filter, result, block);
        addMonitor(am);
        return am;
    }

    public ActivityMonitor addMonitor(String cls, ActivityResult result, boolean block) {
        ActivityMonitor am = new ActivityMonitor(cls, result, block);
        addMonitor(am);
        return am;
    }

    public boolean checkMonitorHit(ActivityMonitor monitor, int minHits) {
        waitForIdleSync();
        synchronized (this.mSync) {
            if (monitor.getHits() < minHits) {
                return false;
            }
            this.mActivityMonitors.remove(monitor);
            return true;
        }
    }

    public Activity waitForMonitor(ActivityMonitor monitor) {
        Activity activity = monitor.waitForActivity();
        synchronized (this.mSync) {
            this.mActivityMonitors.remove(monitor);
        }
        return activity;
    }

    public Activity waitForMonitorWithTimeout(ActivityMonitor monitor, long timeOut) {
        Activity activity = monitor.waitForActivityWithTimeout(timeOut);
        synchronized (this.mSync) {
            this.mActivityMonitors.remove(monitor);
        }
        return activity;
    }

    public void removeMonitor(ActivityMonitor monitor) {
        synchronized (this.mSync) {
            this.mActivityMonitors.remove(monitor);
        }
    }

    /* renamed from: android.app.Instrumentation$1MenuRunnable, reason: invalid class name */
    class C1MenuRunnable implements Runnable {
        private final Activity activity;
        private final int flags;
        private final int identifier;
        boolean returnValue;

        public C1MenuRunnable(Activity _activity, int _identifier, int _flags) {
            this.activity = _activity;
            this.identifier = _identifier;
            this.flags = _flags;
        }

        @Override // java.lang.Runnable
        public void run() {
            Window win = this.activity.getWindow();
            this.returnValue = win.performPanelIdentifierAction(0, this.identifier, this.flags);
        }
    }

    public boolean invokeMenuActionSync(Activity targetActivity, int id, int flag) {
        C1MenuRunnable mr = new C1MenuRunnable(targetActivity, id, flag);
        runOnMainSync(mr);
        return mr.returnValue;
    }

    public boolean invokeContextMenuAction(Activity targetActivity, int id, int flag) {
        validateNotAppThread();
        KeyEvent downEvent = new KeyEvent(0, 23);
        sendKeySync(downEvent);
        waitForIdleSync();
        try {
            Thread.sleep(ViewConfiguration.getLongPressTimeout());
            KeyEvent upEvent = new KeyEvent(1, 23);
            sendKeySync(upEvent);
            waitForIdleSync();
            C1ContextMenuRunnable cmr = new C1ContextMenuRunnable(targetActivity, id, flag);
            runOnMainSync(cmr);
            return cmr.returnValue;
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

        public C1ContextMenuRunnable(Activity _activity, int _identifier, int _flags) {
            this.activity = _activity;
            this.identifier = _identifier;
            this.flags = _flags;
        }

        @Override // java.lang.Runnable
        public void run() {
            Window win = this.activity.getWindow();
            this.returnValue = win.performContextMenuIdentifierAction(this.identifier, this.flags);
        }
    }

    public void sendStringSync(String text) {
        if (text == null) {
            return;
        }
        KeyCharacterMap keyCharacterMap = KeyCharacterMap.load(-1);
        KeyEvent[] events = keyCharacterMap.getEvents(text.toCharArray());
        if (events != null) {
            for (KeyEvent keyEvent : events) {
                sendKeySync(KeyEvent.changeTimeRepeat(keyEvent, SystemClock.uptimeMillis(), 0));
            }
        }
    }

    public void sendKeySync(KeyEvent event) {
        validateNotAppThread();
        long downTime = event.getDownTime();
        long eventTime = event.getEventTime();
        int source = event.getSource();
        if (source == 0) {
            source = 257;
        }
        if (eventTime == 0) {
            eventTime = SystemClock.uptimeMillis();
        }
        if (downTime == 0) {
            downTime = eventTime;
        }
        KeyEvent newEvent = new KeyEvent(event);
        newEvent.setTime(downTime, eventTime);
        newEvent.setSource(source);
        newEvent.setFlags(event.getFlags() | 8);
        setDisplayIfNeeded(newEvent);
        InputManagerGlobal.getInstance().injectInputEvent(newEvent, 2);
    }

    private void setDisplayIfNeeded(KeyEvent event) {
        if (!UserManager.isVisibleBackgroundUsersEnabled()) {
            return;
        }
        int eventDisplayId = event.getDisplayId();
        if (eventDisplayId != -1) {
            if (VERBOSE) {
                Log.v(
                        TAG,
                        "setDisplayIfNeeded("
                                + event
                                + "): not changing display id as it's explicitly set to "
                                + eventDisplayId);
                return;
            }
            return;
        }
        UserManager userManager =
                (UserManager) this.mInstrContext.getSystemService(UserManager.class);
        int userDisplayId = userManager.getMainDisplayIdAssignedToUser();
        if (VERBOSE) {
            Log.v(
                    TAG,
                    "setDisplayIfNeeded("
                            + event
                            + "): eventDisplayId="
                            + eventDisplayId
                            + ", user="
                            + this.mInstrContext.getUser()
                            + ", userDisplayId="
                            + userDisplayId);
        }
        if (userDisplayId == -1) {
            Log.e(
                    TAG,
                    "setDisplayIfNeeded("
                            + event
                            + "): UserManager returned INVALID_DISPLAY as display assigned to user "
                            + this.mInstrContext.getUser());
        } else {
            event.setDisplayId(userDisplayId);
        }
    }

    public void sendKeyDownUpSync(int keyCode) {
        sendKeySync(new KeyEvent(0, keyCode));
        sendKeySync(new KeyEvent(1, keyCode));
    }

    public void sendCharacterSync(int keyCode) {
        sendKeyDownUpSync(keyCode);
    }

    public void sendPointerSync(MotionEvent event) {
        validateNotAppThread();
        if ((event.getSource() & 2) == 0) {
            event.setSource(4098);
        }
        syncInputTransactionsAndInjectEventIntoSelf(event);
    }

    private void syncInputTransactionsAndInjectEventIntoSelf(MotionEvent event) {
        boolean syncBefore = event.getAction() == 0 || event.isFromSource(8194);
        boolean syncAfter = event.getAction() == 1;
        if (syncBefore) {
            try {
                WindowManagerGlobal.getWindowManagerService().syncInputTransactions(true);
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
                return;
            }
        }
        InputManagerGlobal.getInstance().injectInputEvent(event, 2, Process.myUid());
        if (syncAfter) {
            WindowManagerGlobal.getWindowManagerService().syncInputTransactions(true);
        }
    }

    public void sendTrackballEventSync(MotionEvent event) {
        validateNotAppThread();
        if (!event.isFromSource(4)) {
            event.setSource(65540);
        }
        InputManagerGlobal.getInstance().injectInputEvent(event, 2);
    }

    public Application newApplication(ClassLoader cl, String className, Context context)
            throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        Application app =
                getFactory(context.getPackageName()).instantiateApplication(cl, className);
        app.attach(context);
        return app;
    }

    public static Application newApplication(Class<?> clazz, Context context)
            throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        Application app = (Application) clazz.newInstance();
        app.attach(context);
        return app;
    }

    public void callApplicationOnCreate(Application app) {
        app.onCreate();
    }

    public Activity newActivity(
            Class<?> clazz,
            Context context,
            IBinder token,
            Application application,
            Intent intent,
            ActivityInfo info,
            CharSequence title,
            Activity parent,
            String id,
            Object lastNonConfigurationInstance)
            throws InstantiationException, IllegalAccessException {
        Application application2;
        Activity activity = (Activity) clazz.newInstance();
        if (application != null) {
            application2 = application;
        } else {
            application2 = new Application();
        }
        activity.attach(
                context,
                null,
                this,
                token,
                0,
                application2,
                intent,
                info,
                title,
                parent,
                id,
                (Activity.NonConfigurationInstances) lastNonConfigurationInstance,
                new Configuration(),
                null,
                null,
                null,
                null,
                null,
                null,
                null);
        return activity;
    }

    public Activity newActivity(ClassLoader cl, String className, Intent intent)
            throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        String pkg =
                (intent == null || intent.getComponent() == null)
                        ? null
                        : intent.getComponent().getPackageName();
        return getFactory(pkg).instantiateActivity(cl, className, intent);
    }

    private AppComponentFactory getFactory(String pkg) {
        if (pkg == null) {
            Log.e(TAG, "No pkg specified, disabling AppComponentFactory");
            return AppComponentFactory.DEFAULT;
        }
        if (this.mThread == null) {
            Log.e(
                    TAG,
                    "Uninitialized ActivityThread, likely app-created Instrumentation, disabling"
                            + " AppComponentFactory",
                    new Throwable());
            return AppComponentFactory.DEFAULT;
        }
        LoadedApk apk = this.mThread.peekPackageInfo(pkg, true);
        if (apk == null) {
            apk = this.mThread.getSystemContext().mPackageInfo;
        }
        return apk.getAppFactory();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyStartActivityResult(int result, Bundle options) {
        if (this.mActivityMonitors == null) {
            return;
        }
        synchronized (this.mSync) {
            int size = this.mActivityMonitors.size();
            for (int i = 0; i < size; i++) {
                ActivityMonitor am = this.mActivityMonitors.get(i);
                if (am.ignoreMatchingSpecificIntents()) {
                    if (options == null) {
                        options = ActivityOptions.makeBasic().toBundle();
                    }
                    am.onStartActivityResult(result, options);
                }
            }
        }
    }

    private void prePerformCreate(Activity activity) {
        if (this.mWaitingActivities != null) {
            synchronized (this.mSync) {
                int N = this.mWaitingActivities.size();
                for (int i = 0; i < N; i++) {
                    ActivityWaiter aw = this.mWaitingActivities.get(i);
                    Intent intent = aw.intent;
                    if (intent.filterEquals(activity.getIntent())) {
                        aw.activity = activity;
                        this.mMessageQueue.addIdleHandler(new ActivityGoing(aw));
                    }
                }
            }
        }
    }

    private void postPerformCreate(Activity activity) {
        if (this.mActivityMonitors != null) {
            synchronized (this.mSync) {
                int N = this.mActivityMonitors.size();
                for (int i = 0; i < N; i++) {
                    ActivityMonitor am = this.mActivityMonitors.get(i);
                    am.match(activity, activity, activity.getIntent());
                }
            }
        }
    }

    public void callActivityOnCreate(Activity activity, Bundle icicle) {
        prePerformCreate(activity);
        activity.performCreate(icicle);
        postPerformCreate(activity);
    }

    public void callActivityOnCreate(
            Activity activity, Bundle icicle, PersistableBundle persistentState) {
        prePerformCreate(activity);
        activity.performCreate(icicle, persistentState);
        postPerformCreate(activity);
    }

    public void callActivityOnDestroy(Activity activity) {
        activity.performDestroy();
    }

    public void callActivityOnRestoreInstanceState(Activity activity, Bundle savedInstanceState) {
        activity.performRestoreInstanceState(savedInstanceState);
    }

    public void callActivityOnRestoreInstanceState(
            Activity activity, Bundle savedInstanceState, PersistableBundle persistentState) {
        activity.performRestoreInstanceState(savedInstanceState, persistentState);
    }

    public void callActivityOnPostCreate(Activity activity, Bundle savedInstanceState) {
        activity.onPostCreate(savedInstanceState);
    }

    public void callActivityOnPostCreate(
            Activity activity, Bundle savedInstanceState, PersistableBundle persistentState) {
        activity.onPostCreate(savedInstanceState, persistentState);
    }

    public void callActivityOnNewIntent(Activity activity, Intent intent) {
        if (android.security.Flags.contentUriPermissionApis()) {
            activity.performNewIntent(
                    intent, new ComponentCaller(activity.getActivityToken(), null));
        } else {
            activity.performNewIntent(intent);
        }
    }

    public void callActivityOnNewIntent(Activity activity, Intent intent, ComponentCaller caller) {
        activity.performNewIntent(intent, caller);
    }

    public void callActivityOnNewIntent(
            Activity activity, ReferrerIntent intent, ComponentCaller caller) {
        internalCallActivityOnNewIntent(activity, intent, caller);
    }

    private void internalCallActivityOnNewIntent(
            Activity activity, ReferrerIntent intent, ComponentCaller caller) {
        String oldReferrer = activity.mReferrer;
        if (intent != null) {
            try {
                activity.mReferrer = intent.mReferrer;
            } catch (Throwable th) {
                activity.mReferrer = oldReferrer;
                throw th;
            }
        }
        Intent newIntent = intent != null ? new Intent(intent) : null;
        callActivityOnNewIntent(activity, newIntent, caller);
        activity.mReferrer = oldReferrer;
    }

    public void callActivityOnNewIntent(Activity activity, ReferrerIntent intent) {
        if (android.security.Flags.contentUriPermissionApis()) {
            internalCallActivityOnNewIntent(
                    activity, intent, new ComponentCaller(activity.getActivityToken(), null));
            return;
        }
        String oldReferrer = activity.mReferrer;
        if (intent != null) {
            try {
                activity.mReferrer = intent.mReferrer;
            } catch (Throwable th) {
                activity.mReferrer = oldReferrer;
                throw th;
            }
        }
        callActivityOnNewIntent(activity, intent != null ? new Intent(intent) : null);
        activity.mReferrer = oldReferrer;
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
                int N = this.mActivityMonitors.size();
                for (int i = 0; i < N; i++) {
                    ActivityMonitor am = this.mActivityMonitors.get(i);
                    am.match(activity, activity, activity.getIntent());
                }
            }
        }
    }

    public void callActivityOnStop(Activity activity) {
        activity.onStop();
    }

    public void callActivityOnSaveInstanceState(Activity activity, Bundle outState) {
        activity.performSaveInstanceState(outState);
    }

    public void callActivityOnSaveInstanceState(
            Activity activity, Bundle outState, PersistableBundle outPersistentState) {
        activity.performSaveInstanceState(outState, outPersistentState);
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

    private void addValue(String key, int value, Bundle results) {
        if (results.containsKey(key)) {
            List<Integer> list = results.getIntegerArrayList(key);
            if (list != null) {
                list.add(Integer.valueOf(value));
                return;
            }
            return;
        }
        ArrayList<Integer> list2 = new ArrayList<>();
        list2.add(Integer.valueOf(value));
        results.putIntegerArrayList(key, list2);
    }

    public Bundle getAllocCounts() {
        Bundle results = new Bundle();
        results.putLong(
                PerformanceCollector.METRIC_KEY_GLOBAL_ALLOC_COUNT, Debug.getGlobalAllocCount());
        results.putLong(
                PerformanceCollector.METRIC_KEY_GLOBAL_ALLOC_SIZE, Debug.getGlobalAllocSize());
        results.putLong(
                PerformanceCollector.METRIC_KEY_GLOBAL_FREED_COUNT, Debug.getGlobalFreedCount());
        results.putLong(
                PerformanceCollector.METRIC_KEY_GLOBAL_FREED_SIZE, Debug.getGlobalFreedSize());
        results.putLong(
                PerformanceCollector.METRIC_KEY_GC_INVOCATION_COUNT,
                Debug.getGlobalGcInvocationCount());
        return results;
    }

    public Bundle getBinderCounts() {
        Bundle results = new Bundle();
        results.putLong(
                PerformanceCollector.METRIC_KEY_SENT_TRANSACTIONS,
                Debug.getBinderSentTransactions());
        results.putLong(
                PerformanceCollector.METRIC_KEY_RECEIVED_TRANSACTIONS,
                Debug.getBinderReceivedTransactions());
        return results;
    }

    public static final class ActivityResult {
        private final int mResultCode;
        private final Intent mResultData;

        public ActivityResult(int resultCode, Intent resultData) {
            this.mResultCode = resultCode;
            this.mResultData = resultData;
        }

        public int getResultCode() {
            return this.mResultCode;
        }

        public Intent getResultData() {
            return this.mResultData;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:40:0x00d1, code lost:

       r12 = r2;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public android.app.Instrumentation.ActivityResult execStartActivity(
            final android.content.Context r25,
            android.os.IBinder r26,
            final android.os.IBinder r27,
            final android.app.Activity r28,
            final android.content.Intent r29,
            int r30,
            android.os.Bundle r31) {
        /*
            Method dump skipped, instructions count: 361
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                        + " android.app.Instrumentation.execStartActivity(android.content.Context,"
                        + " android.os.IBinder, android.os.IBinder, android.app.Activity,"
                        + " android.content.Intent, int,"
                        + " android.os.Bundle):android.app.Instrumentation$ActivityResult");
    }

    public void execStartActivities(
            Context who,
            IBinder contextThread,
            IBinder token,
            Activity target,
            Intent[] intents,
            Bundle options) {
        execStartActivitiesAsUser(
                who, contextThread, token, target, intents, options, who.getUserId());
    }

    public int execStartActivitiesAsUser(
            final Context who,
            IBinder contextThread,
            final IBinder token,
            Activity target,
            Intent[] intents,
            Bundle options,
            final int userId) {
        Bundle options2;
        Bundle options3;
        if (DEBUG_START_ACTIVITY) {
            StringJoiner joiner = new StringJoiner(", ");
            for (Intent i : intents) {
                joiner.add(i.toString());
            }
            options2 = options;
            Log.d(
                    TAG,
                    "startActivities: who="
                            + who
                            + " source="
                            + target
                            + " userId="
                            + userId
                            + " intents=["
                            + joiner
                            + "] options="
                            + options2,
                    new Throwable());
        } else {
            options2 = options;
        }
        Objects.requireNonNull(intents);
        for (int i2 = intents.length - 1; i2 >= 0; i2--) {
            Objects.requireNonNull(intents[i2]);
        }
        final IApplicationThread whoThread = (IApplicationThread) contextThread;
        if (isSdkSandboxAllowedToStartActivities()) {
            for (Intent intent : intents) {
                adjustIntentForCtsInSdkSandboxInstrumentation(intent);
            }
        }
        if (this.mActivityMonitors == null) {
            options3 = options2;
        } else {
            synchronized (this.mSync) {
                try {
                } catch (Throwable th) {
                    th = th;
                }
                try {
                    int N = this.mActivityMonitors.size();
                    int i3 = 0;
                    while (true) {
                        if (i3 >= N) {
                            break;
                        }
                        ActivityMonitor am = this.mActivityMonitors.get(i3);
                        ActivityResult result = null;
                        if (am.ignoreMatchingSpecificIntents()) {
                            if (options2 == null) {
                                options2 = ActivityOptions.makeBasic().toBundle();
                            }
                            result = am.onStartActivity(who, intents[0], options2);
                        }
                        if (result != null) {
                            am.mHits++;
                            return -96;
                        }
                        if (!am.match(who, null, intents[0])) {
                            i3++;
                        } else {
                            am.mHits++;
                            if (am.isBlocking()) {
                                return -96;
                            }
                        }
                    }
                    options3 = options2;
                } catch (Throwable th2) {
                    th = th2;
                    throw th;
                }
            }
        }
        try {
            final String[] resolvedTypes = new String[intents.length];
            for (int i4 = 0; i4 < intents.length; i4++) {
                try {
                    intents[i4].migrateExtraStreamToClipData(who);
                    intents[i4].prepareToLeaveProcess(who);
                    resolvedTypes[i4] = intents[i4].resolveTypeIfNeeded(who.getContentResolver());
                } catch (RemoteException e) {
                    e = e;
                    throw new RuntimeException("Failure from system", e);
                }
            }
            AppJumpBlockTool.BlockDialogReceiver blockDialogReceiver =
                    new AppJumpBlockTool
                            .BlockDialogReceiver() { // from class: android.app.Instrumentation.2
                        @Override // com.samsung.android.core.AppJumpBlockTool.BlockDialogReceiver,
                        // android.content.BroadcastReceiver
                        public void onReceive(Context context, Intent receiveIntent) {
                            super.onReceive(context, receiveIntent);
                            if (isResultAllow()) {
                                try {
                                    Intent[] jumpIntents = new Intent[getSourceIntents().size()];
                                    getSourceIntents().toArray(jumpIntents);
                                    int newResult =
                                            ActivityTaskManager.getService()
                                                    .startActivities(
                                                            whoThread,
                                                            who.getOpPackageName(),
                                                            who.getAttributionTag(),
                                                            jumpIntents,
                                                            resolvedTypes,
                                                            token,
                                                            getSourceOptions(),
                                                            userId);
                                    Instrumentation.this.notifyStartActivityResult(
                                            newResult, getSourceOptions());
                                    Instrumentation.checkStartActivityResult(
                                            newResult, jumpIntents[0]);
                                } catch (RemoteException e2) {
                                    throw new RuntimeException("Failure from system", e2);
                                }
                            }
                        }
                    };
            if (CoreRune.SUPPORT_APP_JUMP_BLOCK && intents.length > 0) {
                intents[0].putExtra(AppJumpBlockTool.CONTINUE_FLAG, false);
                intents[0].putExtra(AppJumpBlockTool.IDENTIFY, blockDialogReceiver.getIdentify());
            }
            Bundle options4 = options3;
            try {
                int result2 =
                        ActivityTaskManager.getService()
                                .startActivities(
                                        whoThread,
                                        who.getOpPackageName(),
                                        who.getAttributionTag(),
                                        intents,
                                        resolvedTypes,
                                        token,
                                        options4,
                                        userId);
                if (CoreRune.SUPPORT_APP_JUMP_BLOCK && result2 == -103) {
                    Log.e("AppJumpBlockTool", "Register Broadcast1");
                    AppJumpBlockTool.registerBroadcast(who, blockDialogReceiver);
                    return -103;
                }
                notifyStartActivityResult(result2, options4);
                checkStartActivityResult(result2, intents[0]);
                return result2;
            } catch (RemoteException e2) {
                e = e2;
                throw new RuntimeException("Failure from system", e);
            }
        } catch (RemoteException e3) {
            e = e3;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:34:0x00c3, code lost:

       r11 = r4;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public android.app.Instrumentation.ActivityResult execStartActivity(
            android.content.Context r20,
            android.os.IBinder r21,
            android.os.IBinder r22,
            java.lang.String r23,
            android.content.Intent r24,
            int r25,
            android.os.Bundle r26) {
        /*
            Method dump skipped, instructions count: 281
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                        + " android.app.Instrumentation.execStartActivity(android.content.Context,"
                        + " android.os.IBinder, android.os.IBinder, java.lang.String,"
                        + " android.content.Intent, int,"
                        + " android.os.Bundle):android.app.Instrumentation$ActivityResult");
    }

    /* JADX WARN: Code restructure failed: missing block: B:34:0x00d1, code lost:

       r10 = r4;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public android.app.Instrumentation.ActivityResult execStartActivity(
            android.content.Context r21,
            android.os.IBinder r22,
            android.os.IBinder r23,
            java.lang.String r24,
            android.content.Intent r25,
            int r26,
            android.os.Bundle r27,
            android.os.UserHandle r28) {
        /*
            Method dump skipped, instructions count: 304
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                        + " android.app.Instrumentation.execStartActivity(android.content.Context,"
                        + " android.os.IBinder, android.os.IBinder, java.lang.String,"
                        + " android.content.Intent, int, android.os.Bundle,"
                        + " android.os.UserHandle):android.app.Instrumentation$ActivityResult");
    }

    /* JADX WARN: Code restructure failed: missing block: B:33:0x00dd, code lost:

       r10 = r5;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public android.app.Instrumentation.ActivityResult execStartActivityAsCaller(
            android.content.Context r20,
            android.os.IBinder r21,
            android.os.IBinder r22,
            android.app.Activity r23,
            android.content.Intent r24,
            int r25,
            android.os.Bundle r26,
            boolean r27,
            int r28) {
        /*
            Method dump skipped, instructions count: 326
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " android.app.Instrumentation.execStartActivityAsCaller(android.content.Context,"
                    + " android.os.IBinder, android.os.IBinder, android.app.Activity,"
                    + " android.content.Intent, int, android.os.Bundle, boolean,"
                    + " int):android.app.Instrumentation$ActivityResult");
    }

    public void execStartActivityFromAppTask(
            Context who, IBinder contextThread, IAppTask appTask, Intent intent, Bundle options) {
        if (DEBUG_START_ACTIVITY) {
            Log.d(
                    TAG,
                    "startActivity: who=" + who + " intent=" + intent + " options=" + options,
                    new Throwable());
        }
        Objects.requireNonNull(intent);
        IApplicationThread whoThread = (IApplicationThread) contextThread;
        if (isSdkSandboxAllowedToStartActivities()) {
            adjustIntentForCtsInSdkSandboxInstrumentation(intent);
        }
        if (this.mActivityMonitors != null) {
            synchronized (this.mSync) {
                int N = this.mActivityMonitors.size();
                int i = 0;
                while (true) {
                    if (i >= N) {
                        break;
                    }
                    ActivityMonitor am = this.mActivityMonitors.get(i);
                    ActivityResult result = null;
                    if (am.ignoreMatchingSpecificIntents()) {
                        if (options == null) {
                            options = ActivityOptions.makeBasic().toBundle();
                        }
                        result = am.onStartActivity(who, intent, options);
                    }
                    if (result != null) {
                        am.mHits++;
                        return;
                    } else if (!am.match(who, null, intent)) {
                        i++;
                    } else {
                        am.mHits++;
                        if (am.isBlocking()) {
                            return;
                        }
                    }
                }
            }
        }
        try {
            intent.migrateExtraStreamToClipData(who);
            intent.prepareToLeaveProcess(who);
            int result2 =
                    appTask.startActivity(
                            whoThread.asBinder(),
                            who.getOpPackageName(),
                            who.getAttributionTag(),
                            intent,
                            intent.resolveTypeIfNeeded(who.getContentResolver()),
                            options);
            notifyStartActivityResult(result2, options);
            checkStartActivityResult(result2, intent);
        } catch (RemoteException e) {
            throw new RuntimeException("Failure from system", e);
        }
    }

    final void init(
            ActivityThread thread,
            Context instrContext,
            Context appContext,
            ComponentName component,
            IInstrumentationWatcher watcher,
            IUiAutomationConnection uiAutomationConnection) {
        this.mThread = thread;
        this.mThread.getLooper();
        this.mMessageQueue = Looper.myQueue();
        this.mInstrContext = instrContext;
        this.mAppContext = appContext;
        this.mComponent = component;
        this.mWatcher = watcher;
        this.mUiAutomationConnection = uiAutomationConnection;
    }

    final void basicInit(ActivityThread thread) {
        this.mThread = thread;
    }

    public final void basicInit(Context context) {
        this.mInstrContext = context;
        this.mAppContext = context;
    }

    public static void checkStartActivityResult(int res, Object intent) {
        if (!ActivityManager.isStartResultFatalError(res)) {
            return;
        }
        switch (res) {
            case -200:
            case -102:
                return;
            case -101:
                Log.d(
                        TAG,
                        "checkStartActivityResult() : mdm admin has blocked start activity "
                                + intent);
                return;
            case -100:
                throw new IllegalStateException("Cannot start voice activity on a hidden session");
            case ActivityManager.START_VOICE_NOT_ACTIVE_SESSION /* -99 */:
                throw new IllegalStateException(
                        "Session calling startVoiceActivity does not match active session");
            case ActivityManager.START_NOT_VOICE_COMPATIBLE /* -97 */:
                throw new SecurityException(
                        "Starting under voice control not allowed for: " + intent);
            case ActivityManager.START_CANCELED /* -96 */:
                throw new AndroidRuntimeException("Activity could not be started for " + intent);
            case ActivityManager.START_NOT_ACTIVITY /* -95 */:
                throw new IllegalArgumentException("PendingIntent is not an activity");
            case ActivityManager.START_PERMISSION_DENIED /* -94 */:
                throw new SecurityException("Not allowed to start activity " + intent);
            case ActivityManager.START_FORWARD_AND_REQUEST_CONFLICT /* -93 */:
                throw new AndroidRuntimeException(
                        "FORWARD_RESULT_FLAG used while also requesting a result");
            case -92:
            case -91:
                if ((intent instanceof Intent) && ((Intent) intent).getComponent() != null) {
                    throw new ActivityNotFoundException(
                            "Unable to find explicit activity class "
                                    + ((Intent) intent).getComponent().toShortString()
                                    + "; have you declared this activity in your"
                                    + " AndroidManifest.xml, or does your intent not match its"
                                    + " declared <intent-filter>?");
                }
                throw new ActivityNotFoundException("No Activity found to handle " + intent);
            case -90:
                throw new IllegalStateException(
                        "Cannot start assistant activity on a hidden session");
            case ActivityManager.START_ASSISTANT_NOT_ACTIVE_SESSION /* -89 */:
                throw new IllegalStateException(
                        "Session calling startAssistantActivity does not match active session");
            default:
                throw new AndroidRuntimeException(
                        "Unknown error code " + res + " when starting " + intent);
        }
    }

    private final void validateNotAppThread() {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw new RuntimeException(
                    "This method can not be called from the main application thread");
        }
    }

    public UiAutomation getUiAutomation() {
        return getUiAutomation(0);
    }

    public UiAutomation getUiAutomation(int flags) {
        boolean mustCreateNewAutomation =
                this.mUiAutomation == null || this.mUiAutomation.isDestroyed();
        if (this.mUiAutomationConnection != null) {
            if (!mustCreateNewAutomation && this.mUiAutomation.getFlags() == flags) {
                return this.mUiAutomation;
            }
            if (mustCreateNewAutomation) {
                this.mUiAutomation =
                        new UiAutomation(getTargetContext(), this.mUiAutomationConnection);
            } else {
                this.mUiAutomation.disconnect();
            }
            if (getTargetContext().getApplicationInfo().targetSdkVersion <= 30) {
                this.mUiAutomation.connect(flags);
                return this.mUiAutomation;
            }
            long startUptime = SystemClock.uptimeMillis();
            try {
                this.mUiAutomation.connectWithTimeout(flags, 60000L);
                return this.mUiAutomation;
            } catch (TimeoutException e) {
                long waited = SystemClock.uptimeMillis() - startUptime;
                Log.e(TAG, "Unable to connect to UiAutomation. Waited for " + waited + " ms", e);
                this.mUiAutomation.destroy();
                this.mUiAutomation = null;
            }
        }
        return null;
    }

    public TestLooperManager acquireLooperManager(Looper looper) {
        checkInstrumenting("acquireLooperManager");
        return new TestLooperManager(looper);
    }

    private final class InstrumentationThread extends Thread {
        public InstrumentationThread(String name) {
            super(name);
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            try {
                Process.setThreadPriority(-8);
            } catch (RuntimeException e) {
                Log.w(
                        Instrumentation.TAG,
                        "Exception setting priority of instrumentation thread " + Process.myTid(),
                        e);
            }
            if (Instrumentation.this.mAutomaticPerformanceSnapshots) {
                Instrumentation.this.startPerformanceSnapshot();
            }
            Instrumentation.this.onStart();
        }
    }

    private static final class EmptyRunnable implements Runnable {
        private EmptyRunnable() {}

        @Override // java.lang.Runnable
        public void run() {}
    }

    private static final class SyncRunnable implements Runnable {
        private boolean mComplete;
        private final Runnable mTarget;

        public SyncRunnable(Runnable target) {
            this.mTarget = target;
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
                    } catch (InterruptedException e) {
                    }
                }
            }
        }
    }

    private static final class ActivityWaiter {
        public Activity activity;
        public final Intent intent;

        public ActivityWaiter(Intent _intent) {
            this.intent = _intent;
        }
    }

    private final class ActivityGoing implements MessageQueue.IdleHandler {
        private final ActivityWaiter mWaiter;

        public ActivityGoing(ActivityWaiter waiter) {
            this.mWaiter = waiter;
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

        public Idler(Runnable callback) {
            this.mCallback = callback;
        }

        @Override // android.os.MessageQueue.IdleHandler
        public final boolean queueIdle() {
            if (this.mCallback != null) {
                this.mCallback.run();
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
                    } catch (InterruptedException e) {
                    }
                }
            }
        }
    }
}
