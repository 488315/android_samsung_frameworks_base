package com.android.server.am;

import android.R;
import android.app.ActivityManager;
import android.app.ActivityManagerInternal;
import android.app.ActivityTaskManager;
import android.app.AppGlobals;
import android.app.BroadcastOptions;
import android.app.ForegroundServiceDelegationOptions;
import android.app.IActivityController;
import android.app.IActivityManager;
import android.app.IApplicationThread;
import android.app.IProcessObserver;
import android.app.IStopUserCallback;
import android.app.IUserSwitchObserver;
import android.app.KeyguardManager;
import android.app.ProfilerInfo;
import android.app.UidObserver;
import android.app.UserSwitchObserver;
import android.app.usage.AppStandbyInfo;
import android.app.usage.ConfigurationStats;
import android.app.usage.IUsageStatsManager;
import android.content.ComponentName;
import android.content.IIntentReceiver;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ConfigurationInfo;
import android.content.pm.FeatureInfo;
import android.content.pm.IPackageManager;
import android.content.pm.PackageManager;
import android.content.pm.ParceledListSlice;
import android.content.pm.SharedLibraryInfo;
import android.content.pm.UserInfo;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.graphics.Rect;
import android.hardware.biometrics.face.V1_0.OptionalBool$$ExternalSyntheticOutline0;
import android.hardware.display.DisplayManager;
import android.net.INetd;
import android.opengl.GLES10;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.IProgressListener;
import android.os.ParcelFileDescriptor;
import android.os.RemoteCallback;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.ShellCommand;
import android.os.StrictMode;
import android.os.SystemClock;
import android.os.Trace;
import android.os.UserHandle;
import android.os.UserManager;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.DebugUtils;
import android.util.DisplayMetrics;
import android.util.Slog;
import android.util.SparseArray;
import android.util.TeeWriter;
import android.util.proto.ProtoOutputStream;
import android.view.Choreographer;
import android.view.Display;
import com.android.internal.util.MemInfoReader;
import com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver;
import com.android.server.LocalServices;
import com.android.server.UiModeManagerService$13$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.ProxyManager$$ExternalSyntheticOutline0;
import com.android.server.accounts.AccountManagerService$$ExternalSyntheticOutline0;
import com.android.server.accounts.AccountManagerServiceShellCommand$$ExternalSyntheticOutline0;
import com.android.server.am.ActivityManagerService;
import com.android.server.am.ActivityManagerShellCommand;
import com.android.server.am.AppBatteryTracker;
import com.android.server.am.AppStartInfoTracker;
import com.android.server.am.CachedAppOptimizer;
import com.android.server.am.nano.Capabilities;
import com.android.server.am.nano.Capability;
import com.android.server.am.nano.FrameworkCapability;
import com.android.server.am.nano.VMCapability;
import com.android.server.am.nano.VMInfo;
import com.android.server.os.NativeTombstoneManager;
import com.android.server.os.NativeTombstoneManager$$ExternalSyntheticLambda2;
import com.android.server.pm.PackageManagerShellCommandDataLoader;
import com.android.server.pm.UserManagerInternal;
import com.android.server.utils.Slogf;
import com.android.server.wm.ActivityTaskManagerService;
import com.android.server.wm.PersisterQueue;
import com.android.server.wm.RecentTasks;
import com.android.server.wm.WindowManagerGlobalLock;
import com.android.server.wm.WindowManagerService;
import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.rune.CoreRune;
import dalvik.annotation.optimization.NeverCompile;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.URISyntaxException;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.function.BooleanSupplier;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;

public final class ActivityManagerShellCommand extends ShellCommand {
    public int mActivityType;
    public String mAgent;
    public boolean mAsync;
    public boolean mAttachAgentDuringBind;
    public boolean mAutoStop;
    public BroadcastOptions mBroadcastOptions;
    public int mClockType;
    public boolean mDismissKeyguardIfInsecure;
    public int mDisplayId;
    public final boolean mDumping;
    public boolean mForceLaunchTaskOnHome;
    public int mForceWindowingMode;
    public final ActivityManagerService mInterface;
    public final ActivityManagerService mInternal;
    public boolean mIsLockTask;
    public boolean mIsResumeAffordanceRequested;
    public boolean mIsTaskOverlay;
    public String mProfileFile;
    public int mProfilerOutputVersion;
    public String mReceiverPermission;
    public int mSamplingInterval;
    public boolean mShowSplashScreen;
    public boolean mStreaming;
    public int mTaskDisplayAreaFeatureId;
    public int mTaskId;
    public final ActivityTaskManagerService mTaskInterface;
    public int mUserId;
    public int mWindowingMode;
    public static final DateTimeFormatter LOG_NAME_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss", Locale.ROOT);
    public static final String[] CAPABILITIES = {"start.suspend"};
    public int mStartFlags = 0;
    public boolean mWaitOption = false;
    public boolean mStopOption = false;
    public int mRepeat = 0;
    public final IPackageManager mPm = AppGlobals.getPackageManager();

    public final class IntentReceiver extends IIntentReceiver.Stub {
        public boolean mFinished = false;
        public final PrintWriter mPw;

        public IntentReceiver(PrintWriter printWriter) {
            this.mPw = printWriter;
        }

        public final void performReceive(Intent intent, int i, String str, Bundle bundle, boolean z, boolean z2, int i2) {
            String m = VibrationParam$1$$ExternalSyntheticOutline0.m(i, "Broadcast completed: result=");
            if (str != null) {
                m = m + ", data=\"" + str + "\"";
            }
            if (bundle != null) {
                m = m + ", extras: " + bundle;
            }
            this.mPw.println(m);
            this.mPw.flush();
            synchronized (this) {
                this.mFinished = true;
                notifyAll();
            }
        }
    }

    public final class MyActivityController extends IActivityController.Stub {
        public final boolean mAlwaysContinue;
        public final boolean mAlwaysKill;
        public final String mGdbPort;
        public Process mGdbProcess;
        public AnonymousClass1 mGdbThread;
        public final InputStream mInput;
        public final IActivityManager mInterface;
        public final boolean mMonkey;
        public final PrintWriter mPw;
        public int mResult;
        public final boolean mSimpleMode;
        public int mState;
        public final String mTarget;

        public MyActivityController(IActivityManager iActivityManager, PrintWriter printWriter, InputStream inputStream, String str, boolean z, boolean z2, String str2, boolean z3, boolean z4) {
            this.mInterface = iActivityManager;
            this.mPw = printWriter;
            this.mInput = inputStream;
            this.mGdbPort = str;
            this.mMonkey = z;
            this.mSimpleMode = z2;
            this.mTarget = str2;
            this.mAlwaysContinue = z3;
            this.mAlwaysKill = z4;
        }

        public final boolean activityResuming(String str) {
            String str2 = this.mTarget;
            if (!(str2 == null ? true : str2.equals(str))) {
                return true;
            }
            synchronized (this) {
                this.mPw.println("** Activity resuming: " + str);
                this.mPw.flush();
            }
            return true;
        }

        public final boolean activityStarting(Intent intent, String str) {
            String str2 = this.mTarget;
            if (!(str2 == null ? true : str2.equals(str))) {
                return true;
            }
            synchronized (this) {
                this.mPw.println("** Activity starting: " + str);
                this.mPw.flush();
            }
            return true;
        }

        public final boolean appCrashed(String str, int i, String str2, String str3, long j, String str4) {
            String str5 = this.mTarget;
            if (!(str5 == null ? true : str5.equals(str))) {
                return true;
            }
            synchronized (this) {
                try {
                    if (this.mSimpleMode) {
                        this.mPw.println("** PROCESS CRASHED: " + str);
                    } else {
                        this.mPw.println("** ERROR: PROCESS CRASHED");
                        this.mPw.println("processName: " + str);
                        this.mPw.println("processPid: " + i);
                        this.mPw.println("shortMsg: " + str2);
                        this.mPw.println("longMsg: " + str3);
                        this.mPw.println("timeMillis: " + j);
                        this.mPw.println("uptime: " + SystemClock.uptimeMillis());
                        this.mPw.println("stack:");
                        this.mPw.print(str4);
                        this.mPw.println("#");
                    }
                    this.mPw.flush();
                    if (this.mAlwaysContinue) {
                        return true;
                    }
                    if (this.mAlwaysKill) {
                        return false;
                    }
                    return waitControllerLocked(i, 1) != 1;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final int appEarlyNotResponding(String str, int i, String str2) {
            String str3 = this.mTarget;
            if (!(str3 == null ? true : str3.equals(str))) {
                return 0;
            }
            synchronized (this) {
                try {
                    if (this.mSimpleMode) {
                        this.mPw.println("** EARLY PROCESS NOT RESPONDING: " + str);
                    } else {
                        this.mPw.println("** ERROR: EARLY PROCESS NOT RESPONDING");
                        this.mPw.println("processName: " + str);
                        this.mPw.println("processPid: " + i);
                        this.mPw.println("annotation: " + str2);
                        this.mPw.println("uptime: " + SystemClock.uptimeMillis());
                    }
                    this.mPw.flush();
                    if (this.mAlwaysContinue) {
                        return 0;
                    }
                    if (this.mAlwaysKill) {
                        return -1;
                    }
                    return waitControllerLocked(i, 2) == 1 ? -1 : 0;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final int appNotResponding(String str, int i, String str2) {
            String str3 = this.mTarget;
            if (!(str3 == null ? true : str3.equals(str))) {
                return 0;
            }
            synchronized (this) {
                try {
                    if (this.mSimpleMode) {
                        this.mPw.println("** PROCESS NOT RESPONDING: " + str);
                    } else {
                        this.mPw.println("** ERROR: PROCESS NOT RESPONDING");
                        this.mPw.println("processName: " + str);
                        this.mPw.println("processPid: " + i);
                        this.mPw.println("uptime: " + SystemClock.uptimeMillis());
                        this.mPw.println("processStats:");
                        this.mPw.print(str2);
                        this.mPw.println("#");
                    }
                    this.mPw.flush();
                    if (this.mAlwaysContinue) {
                        return 0;
                    }
                    if (this.mAlwaysKill) {
                        return -1;
                    }
                    int waitControllerLocked = waitControllerLocked(i, 3);
                    if (waitControllerLocked == 1) {
                        return -1;
                    }
                    return waitControllerLocked == 2 ? 1 : 0;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void killGdbLocked() {
            if (this.mGdbProcess != null) {
                this.mPw.println("Stopping gdbserver");
                this.mPw.flush();
                this.mGdbProcess.destroy();
                this.mGdbProcess = null;
            }
            AnonymousClass1 anonymousClass1 = this.mGdbThread;
            if (anonymousClass1 != null) {
                anonymousClass1.interrupt();
                this.mGdbThread = null;
            }
        }

        public final void printMessageForState() {
            if ((this.mAlwaysContinue || this.mAlwaysKill) && this.mSimpleMode) {
                return;
            }
            int i = this.mState;
            if (i == 0) {
                this.mPw.println("Monitoring activity manager...  available commands:");
            } else if (i == 1) {
                this.mPw.println("Waiting after crash...  available commands:");
                this.mPw.println("(c)ontinue: show crash dialog");
                this.mPw.println("(k)ill: immediately kill app");
            } else if (i == 2) {
                this.mPw.println("Waiting after early ANR...  available commands:");
                this.mPw.println("(c)ontinue: standard ANR processing");
                this.mPw.println("(k)ill: immediately kill app");
            } else if (i == 3) {
                this.mPw.println("Waiting after ANR...  available commands:");
                this.mPw.println("(c)ontinue: show ANR dialog");
                this.mPw.println("(k)ill: immediately kill app");
                this.mPw.println("(w)ait: wait some more");
            }
            this.mPw.println("(q)uit: finish monitoring");
        }

        public final void resumeController(int i) {
            synchronized (this) {
                this.mState = 0;
                this.mResult = i;
                notifyAll();
            }
        }

        public final int systemNotResponding(String str) {
            if (this.mTarget != null) {
                return -1;
            }
            synchronized (this) {
                try {
                    this.mPw.println("** ERROR: PROCESS NOT RESPONDING");
                    if (!this.mSimpleMode) {
                        this.mPw.println("message: " + str);
                        this.mPw.println("#");
                        this.mPw.println("Allowing system to die.");
                    }
                    this.mPw.flush();
                } catch (Throwable th) {
                    throw th;
                }
            }
            return -1;
        }

        /* JADX WARN: Type inference failed for: r0v6, types: [com.android.server.am.ActivityManagerShellCommand$MyActivityController$1, java.lang.Thread] */
        public final int waitControllerLocked(int i, int i2) {
            if (this.mGdbPort != null) {
                killGdbLocked();
                try {
                    this.mPw.println("Starting gdbserver on port " + this.mGdbPort);
                    this.mPw.println("Do the following:");
                    this.mPw.println("  adb forward tcp:" + this.mGdbPort + " tcp:" + this.mGdbPort);
                    PrintWriter printWriter = this.mPw;
                    StringBuilder sb = new StringBuilder("  gdbclient app_process :");
                    sb.append(this.mGdbPort);
                    printWriter.println(sb.toString());
                    this.mPw.flush();
                    this.mGdbProcess = Runtime.getRuntime().exec(new String[]{"gdbserver", ":" + this.mGdbPort, "--attach", Integer.toString(i)});
                    final InputStreamReader inputStreamReader = new InputStreamReader(this.mGdbProcess.getInputStream());
                    ?? r0 = new Thread() { // from class: com.android.server.am.ActivityManagerShellCommand.MyActivityController.1
                        @Override // java.lang.Thread, java.lang.Runnable
                        public final void run() {
                            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                            int i3 = 0;
                            while (true) {
                                synchronized (MyActivityController.this) {
                                    try {
                                        MyActivityController myActivityController = MyActivityController.this;
                                        if (myActivityController.mGdbThread == null) {
                                            return;
                                        }
                                        if (i3 == 2) {
                                            myActivityController.notifyAll();
                                        }
                                        try {
                                            String readLine = bufferedReader.readLine();
                                            if (readLine == null) {
                                                return;
                                            }
                                            MyActivityController.this.mPw.println("GDB: " + readLine);
                                            MyActivityController.this.mPw.flush();
                                            i3++;
                                        } catch (IOException unused) {
                                            return;
                                        }
                                    } catch (Throwable th) {
                                        throw th;
                                    }
                                }
                            }
                        }
                    };
                    this.mGdbThread = r0;
                    r0.start();
                    try {
                        wait(500L);
                    } catch (InterruptedException unused) {
                    }
                } catch (IOException e) {
                    this.mPw.println("Failure starting gdbserver: " + e);
                    this.mPw.flush();
                    killGdbLocked();
                }
            }
            this.mState = i2;
            this.mPw.println("");
            printMessageForState();
            this.mPw.flush();
            while (this.mState != 0) {
                try {
                    wait();
                } catch (InterruptedException unused2) {
                }
            }
            killGdbLocked();
            return this.mResult;
        }
    }

    public final class MyUidObserver extends UidObserver implements ActivityManagerService.OomAdjObserver {
        public final InputStream mInput;
        public final ActivityManagerService mInterface;
        public final ActivityManagerService mInternal;
        public final int mMask;
        public final PrintWriter mPw;
        public final int mUid;

        public MyUidObserver(ActivityManagerService activityManagerService, PrintWriter printWriter, InputStream inputStream, int i, int i2) {
            this.mInterface = activityManagerService;
            this.mInternal = activityManagerService;
            this.mPw = printWriter;
            this.mInput = inputStream;
            this.mUid = i;
            this.mMask = i2;
        }

        public final void onUidActive(int i) {
            synchronized (this) {
                try {
                    StrictMode.ThreadPolicy allowThreadDiskWrites = StrictMode.allowThreadDiskWrites();
                    try {
                        this.mPw.print(i);
                        this.mPw.println(" active");
                        this.mPw.flush();
                    } finally {
                        StrictMode.setThreadPolicy(allowThreadDiskWrites);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void onUidCachedChanged(int i, boolean z) {
            synchronized (this) {
                try {
                    StrictMode.ThreadPolicy allowThreadDiskWrites = StrictMode.allowThreadDiskWrites();
                    try {
                        this.mPw.print(i);
                        this.mPw.println(z ? " cached" : " uncached");
                        this.mPw.flush();
                        StrictMode.setThreadPolicy(allowThreadDiskWrites);
                    } catch (Throwable th) {
                        StrictMode.setThreadPolicy(allowThreadDiskWrites);
                        throw th;
                    }
                } catch (Throwable th2) {
                    throw th2;
                }
            }
        }

        public final void onUidGone(int i, boolean z) {
            synchronized (this) {
                try {
                    StrictMode.ThreadPolicy allowThreadDiskWrites = StrictMode.allowThreadDiskWrites();
                    try {
                        this.mPw.print(i);
                        this.mPw.print(" gone");
                        if (z) {
                            this.mPw.print(" disabled");
                        }
                        this.mPw.println();
                        this.mPw.flush();
                        StrictMode.setThreadPolicy(allowThreadDiskWrites);
                    } catch (Throwable th) {
                        StrictMode.setThreadPolicy(allowThreadDiskWrites);
                        throw th;
                    }
                } catch (Throwable th2) {
                    throw th2;
                }
            }
        }

        public final void onUidIdle(int i, boolean z) {
            synchronized (this) {
                try {
                    StrictMode.ThreadPolicy allowThreadDiskWrites = StrictMode.allowThreadDiskWrites();
                    try {
                        this.mPw.print(i);
                        this.mPw.print(" idle");
                        if (z) {
                            this.mPw.print(" disabled");
                        }
                        this.mPw.println();
                        this.mPw.flush();
                        StrictMode.setThreadPolicy(allowThreadDiskWrites);
                    } catch (Throwable th) {
                        StrictMode.setThreadPolicy(allowThreadDiskWrites);
                        throw th;
                    }
                } catch (Throwable th2) {
                    throw th2;
                }
            }
        }

        public final void onUidStateChanged(int i, int i2, long j, int i3) {
            synchronized (this) {
                try {
                    StrictMode.ThreadPolicy allowThreadDiskWrites = StrictMode.allowThreadDiskWrites();
                    try {
                        this.mPw.print(i);
                        this.mPw.print(" procstate ");
                        PrintWriter printWriter = this.mPw;
                        int i4 = ProcessList.PAGE_SIZE;
                        printWriter.print(ActivityManager.procStateToString(i2));
                        this.mPw.print(" seq ");
                        this.mPw.print(j);
                        this.mPw.print(" capability ");
                        this.mPw.println(this.mMask & i3);
                        this.mPw.flush();
                    } finally {
                        StrictMode.setThreadPolicy(allowThreadDiskWrites);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public final class ProcessObserver extends IProcessObserver.Stub {
        public IActivityManager mIam;
        public PrintWriter mPw;

        public final void onForegroundActivitiesChanged(int i, int i2, boolean z) {
            if (z) {
                try {
                    if (this.mIam.getUidProcessState(i2, "android") == 2) {
                        this.mPw.println("New foreground process: " + i);
                    } else {
                        this.mPw.println("No top app found");
                    }
                    this.mPw.flush();
                } catch (RemoteException unused) {
                    this.mPw.println("Error occurred in binder call");
                    this.mPw.flush();
                }
            }
        }

        public final void onForegroundServicesChanged(int i, int i2, int i3) {
        }

        public final void onProcessDied(int i, int i2) {
        }

        public final void onProcessStarted(int i, int i2, int i3, String str, String str2) {
        }
    }

    public final class ProgressWaiter extends IProgressListener.Stub {
        public final CountDownLatch mFinishedLatch = new CountDownLatch(1);
        public final int mUserId;

        public ProgressWaiter(int i) {
            this.mUserId = i;
        }

        public final void onFinished(int i, Bundle bundle) {
            Slogf.d("ActivityManager", "ProgressWaiter[user=%d]: onFinished(%d)", Integer.valueOf(this.mUserId), Integer.valueOf(i));
            this.mFinishedLatch.countDown();
        }

        public final void onProgress(int i, int i2, Bundle bundle) {
            Slogf.d("ActivityManager", "ProgressWaiter[user=%d]: onProgress(%d, %d)", Integer.valueOf(this.mUserId), Integer.valueOf(i), Integer.valueOf(i2));
        }

        public final void onStarted(int i, Bundle bundle) {
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("ProgressWaiter[userId=");
            sb.append(this.mUserId);
            sb.append(", finished=");
            return OptionalBool$$ExternalSyntheticOutline0.m("]", sb, this.mFinishedLatch.getCount() == 0);
        }
    }

    public final class StopUserCallback extends IStopUserCallback.Stub {
        public boolean mFinished = false;
        public final int mUserId;

        public StopUserCallback(int i) {
            this.mUserId = i;
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("ProgressWaiter[userId=");
            sb.append(this.mUserId);
            sb.append(", finished=");
            return OptionalBool$$ExternalSyntheticOutline0.m("]", sb, this.mFinished);
        }

        public final synchronized void userStopAborted(int i) {
            Slogf.d("ActivityManager", "StopUserCallback: userStopAborted(%d)", Integer.valueOf(i));
            this.mFinished = true;
            notifyAll();
        }

        public final synchronized void userStopped(int i) {
            Slogf.d("ActivityManager", "StopUserCallback: userStopped(%d)", Integer.valueOf(i));
            this.mFinished = true;
            notifyAll();
        }

        public final synchronized void waitForFinish() {
            while (!this.mFinished) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    throw new IllegalStateException(e);
                }
            }
            Slogf.d("ActivityManager", "user %d finished stopping", Integer.valueOf(this.mUserId));
        }
    }

    public ActivityManagerShellCommand(ActivityManagerService activityManagerService, boolean z) {
        this.mInterface = activityManagerService;
        this.mTaskInterface = activityManagerService.mActivityTaskManager;
        this.mInternal = activityManagerService;
        this.mDumping = z;
    }

    public static void addExtensionsForConfig(EGL10 egl10, EGLDisplay eGLDisplay, EGLConfig eGLConfig, int[] iArr, int[] iArr2, Set set) {
        EGLContext eGLContext = EGL10.EGL_NO_CONTEXT;
        EGLContext eglCreateContext = egl10.eglCreateContext(eGLDisplay, eGLConfig, eGLContext, iArr2);
        if (eglCreateContext == eGLContext) {
            return;
        }
        EGLSurface eglCreatePbufferSurface = egl10.eglCreatePbufferSurface(eGLDisplay, eGLConfig, iArr);
        if (eglCreatePbufferSurface == EGL10.EGL_NO_SURFACE) {
            egl10.eglDestroyContext(eGLDisplay, eglCreateContext);
            return;
        }
        egl10.eglMakeCurrent(eGLDisplay, eglCreatePbufferSurface, eglCreatePbufferSurface, eglCreateContext);
        String glGetString = GLES10.glGetString(7939);
        if (!TextUtils.isEmpty(glGetString)) {
            for (String str : glGetString.split(" ")) {
                ((HashSet) set).add(str);
            }
        }
        EGLSurface eGLSurface = EGL10.EGL_NO_SURFACE;
        egl10.eglMakeCurrent(eGLDisplay, eGLSurface, eGLSurface, EGL10.EGL_NO_CONTEXT);
        egl10.eglDestroySurface(eGLDisplay, eglCreatePbufferSurface);
        egl10.eglDestroyContext(eGLDisplay, eglCreateContext);
    }

    @NeverCompile
    public static void dumpHelp(PrintWriter printWriter, boolean z) {
        if (z) {
            BatteryService$$ExternalSyntheticOutline0.m(printWriter, "Activity manager dump options:", "  [-a] [-c] [-p PACKAGE] [-h] [WHAT] ...", "  WHAT may be one of:", "    a[ctivities]: activity stack state");
            BatteryService$$ExternalSyntheticOutline0.m(printWriter, "    r[recents]: recent activities state", "    b[roadcasts] [PACKAGE_NAME] [history [-s]]: broadcast state", "    broadcast-stats [PACKAGE_NAME]: aggregated broadcast statistics", "    i[ntents] [PACKAGE_NAME]: pending intent state");
            BatteryService$$ExternalSyntheticOutline0.m(printWriter, "    p[rocesses] [PACKAGE_NAME]: process state", "    o[om]: out of memory management", "    perm[issions]: URI permission grant state", "    prov[iders] [COMP_SPEC ...]: content provider state");
            BatteryService$$ExternalSyntheticOutline0.m(printWriter, "    provider [COMP_SPEC]: provider client-side state", "    s[ervices] [COMP_SPEC ...]: service state", "    allowed-associations: current package association restrictions", "    as[sociations]: tracked app associations");
            BatteryService$$ExternalSyntheticOutline0.m(printWriter, "    start-info [PACKAGE_NAME]: historical process start information", "    exit-info [PACKAGE_NAME]: historical process exit information", "    lmk: stats on low memory killer", "    lru: raw LRU process list");
            BatteryService$$ExternalSyntheticOutline0.m(printWriter, "    binder-proxies: stats on binder objects and IPCs", "    settings: currently applied config settings", "    timers: the current ANR timer state", "    service [COMP_SPEC]: service client-side state");
            BatteryService$$ExternalSyntheticOutline0.m(printWriter, "    package [PACKAGE_NAME]: all state related to given package", "    all: dump all activities", "    top: dump the top activity", "    users: user state");
            BatteryService$$ExternalSyntheticOutline0.m(printWriter, "  WHAT may also be a COMP_SPEC to dump activities.", "  COMP_SPEC may be a component name (com.foo/.myApp),", "    a partial substring in a component name, a", "    hex object identifier.");
            BatteryService$$ExternalSyntheticOutline0.m(printWriter, "  -a: include all available server state.", "  -c: include client state.", "  -p: limit output to given package.", "  -d: limit output to given display.");
            BatteryService$$ExternalSyntheticOutline0.m(printWriter, "  --checkin: output checkin format, resetting data.", "  --C: output checkin format, not resetting data.", "  --proto: output dump in protocol buffer format.");
            printWriter.printf("  %s: dump just the DUMPABLE-related state of an activity. Use the %s option to list the supported DUMPABLEs\n", "--dump-dumpable", "--list-dumpables");
            printWriter.printf("  %s: show the available dumpables in an activity\n", "--list-dumpables");
            return;
        }
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "Activity manager (activity) commands:", "  help", "      Print this help text.", "  start-activity [-D] [-N] [-W] [-P <FILE>] [--start-profiler <FILE>]");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "          [--sampling INTERVAL] [--clock-type <TYPE>] [--streaming]", "          [--profiler-output-version NUMBER]", "          [-R COUNT] [-S] [--track-allocation]", "          [--user <USER_ID> | current] [--suspend] <INTENT>");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "      Start an Activity.  Options are:", "      -D: enable debugging", "      --suspend: debugged app suspend threads at startup (only with -D)", "      -N: enable native debugging");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "      -W: wait for launch to complete (initial display)", "      --start-profiler <FILE>: start profiler and send results to <FILE>", "      --sampling INTERVAL: use sample profiling with INTERVAL microseconds", "          between samples (use with --start-profiler)");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "      --clock-type <TYPE>: type can be wall / thread-cpu / dual. Specify", "          the clock that is used to report the timestamps when profiling", "          The default value is dual. (use with --start-profiler)", "      --streaming: stream the profiling output to the specified file");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "          (use with --start-profiler)", "      --profiler-output-version Specify the version of the", "          profiling output (use with --start-profiler)", "      -P <FILE>: like above, but profiling stops when app goes idle");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "      --attach-agent <agent>: attach the given agent before binding", "      --attach-agent-bind <agent>: attach the given agent during binding", "      -R: repeat the activity launch <COUNT> times.  Prior to each repeat,", "          the top activity will be finished.");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "      -S: force stop the target app before starting the activity", "      --track-allocation: enable tracking of object allocations", "      --user <USER_ID> | current: Specify which user to run as; if not", "          specified then run as the current user.");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "      --windowingMode <WINDOWING_MODE>: The windowing mode to launch the activity into.", "      --activityType <ACTIVITY_TYPE>: The activity type to launch the activity as.", "      --display <DISPLAY_ID>: The display to launch the activity into.", "      --splashscreen-icon: Show the splash screen icon on launch.");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "  start-in-vsync", "      Start an Activity with vsync aligned. See `start-activity` for the", "      possible options.", "  start-service [--user <USER_ID> | current] <INTENT>");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "      Start a Service.  Options are:", "      --user <USER_ID> | current: Specify which user to run as; if not", "          specified then run as the current user.", "  start-foreground-service [--user <USER_ID> | current] <INTENT>");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "      Start a foreground Service.  Options are:", "      --user <USER_ID> | current: Specify which user to run as; if not", "          specified then run as the current user.", "  stop-service [--user <USER_ID> | current] <INTENT>");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "      Stop a Service.  Options are:", "      --user <USER_ID> | current: Specify which user to run as; if not", "          specified then run as the current user.", "  broadcast [--user <USER_ID> | all | current]");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "          [--receiver-permission <PERMISSION>]", "          [--allow-background-activity-starts]", "          [--async] <INTENT>", "      Send a broadcast Intent.  Options are:");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "      --user <USER_ID> | all | current: Specify which user to send to; if not", "          specified then send to all users.", "      --receiver-permission <PERMISSION>: Require receiver to hold permission.", "      --allow-background-activity-starts: The receiver may start activities");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "          even if in the background.", "      --async: Send without waiting for the completion of the receiver.", "  compact {some|full} <PROCESS>", "      Perform a single process compaction. The given <PROCESS> argument");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "          may be either a process name or pid.", "      some: execute file compaction.", "      full: execute anon + file compaction.", "  compact system");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "      Perform a full system compaction.", "  compact native {some|full} <pid>", "      Perform a native compaction for process with <pid>.", "      some: execute file compaction.");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "      full: execute anon + file compaction.", "  freeze [--sticky] <PROCESS>", "      Freeze a process. The given <PROCESS> argument", "          may be either a process name or pid.  Options are:");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "      --sticky: persists the frozen state for the process lifetime or", "                  until an unfreeze is triggered via shell", "  unfreeze [--sticky] <PROCESS>", "      Unfreeze a process. The given <PROCESS> argument");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "          may be either a process name or pid.  Options are:", "      --sticky: persists the unfrozen state for the process lifetime or", "                  until a freeze is triggered via shell", "  instrument [-r] [-e <NAME> <VALUE>] [-p <FILE>] [-w]");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "          [--user <USER_ID> | current]", "          [--no-hidden-api-checks [--no-test-api-access]]", "          [--no-isolated-storage]", "          [--no-window-animation] [--abi <ABI>] <COMPONENT>");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "      Start an Instrumentation.  Typically this target <COMPONENT> is in the", "      form <TEST_PACKAGE>/<RUNNER_CLASS> or only <TEST_PACKAGE> if there", "      is only one instrumentation.  Options are:", "      -r: print raw results (otherwise decode REPORT_KEY_STREAMRESULT).  Use with");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "          [-e perf true] to generate raw output for performance measurements.", "      -e <NAME> <VALUE>: set argument <NAME> to <VALUE>.  For test runners a", "          common form is [-e <testrunner_flag> <value>[,<value>...]].", "      -p <FILE>: write profiling data to <FILE>");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "      -m: Write output as protobuf to stdout (machine readable)", "      -f <Optional PATH/TO/FILE>: Write output as protobuf to a file (machine", "          readable). If path is not specified, default directory and file name will", "          be used: /sdcard/instrument-logs/log-yyyyMMdd-hhmmss-SSS.instrumentation_data_proto");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "      -w: wait for instrumentation to finish before returning.  Required for", "          test runners.", "      --user <USER_ID> | current: Specify user instrumentation runs in;", "          current user if not specified.");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "      --no-hidden-api-checks: disable restrictions on use of hidden API.", "      --no-test-api-access: do not allow access to test APIs, if hidden", "          API checks are enabled.", "      --no-isolated-storage: don't use isolated storage sandbox and ");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "          mount full external storage", "      --no-window-animation: turn off window animations while running.", "      --abi <ABI>: Launch the instrumented process with the selected ABI.", "          This assumes that the process supports the selected ABI.");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "  trace-ipc [start|stop] [--dump-file <FILE>]", "      Trace IPC transactions.", "      start: start tracing IPC transactions.", "      stop: stop tracing IPC transactions and dump the results to file.");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "      --dump-file <FILE>: Specify the file the trace should be dumped to.", "  profile start [--user <USER_ID> current]", "          [--clock-type <TYPE>]", "          [--profiler-output-version VERSION]");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "          [--sampling INTERVAL | --streaming] <PROCESS> <FILE>", "      Start profiler on a process.  The given <PROCESS> argument", "        may be either a process name or pid.  Options are:", "      --user <USER_ID> | current: When supplying a process name,");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "          specify user of process to profile; uses current user if not", "          specified.", "      --clock-type <TYPE>: use the specified clock to report timestamps.", "          The type can be one of wall | thread-cpu | dual. The default");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "          value is dual.", "      --profiler-output-versionVERSION: specifies the output", "          format version", "      --sampling INTERVAL: use sample profiling with INTERVAL microseconds");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "          between samples.", "      --streaming: stream the profiling output to the specified file.", "  profile stop [--user <USER_ID> current] <PROCESS>", "      Stop profiler on a process.  The given <PROCESS> argument");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "        may be either a process name or pid.  Options are:", "      --user <USER_ID> | current: When supplying a process name,", "          specify user of process to profile; uses current user if not", "          specified.");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "  dumpheap [--user <USER_ID> current] [-n] [-g] [-b <format>] ", "           <PROCESS> <FILE>", "      Dump the heap of a process.  The given <PROCESS> argument may", "        be either a process name or pid.  Options are:");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "      -n: dump native heap instead of managed heap", "      -g: force GC before dumping the heap", "      -b <format>: dump contents of bitmaps in the format specified,", "         which can be \"png\", \"jpg\" or \"webp\".");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "      --user <USER_ID> | current: When supplying a process name,", "          specify user of process to dump; uses current user if not specified.", "  set-debug-app [-w] [--persistent] <PACKAGE>", "      Set application <PACKAGE> to debug.  Options are:");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "      -w: wait for debugger when application starts", "      --persistent: retain this value", "  clear-debug-app", "      Clear the previously set-debug-app.");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "  set-watch-heap <PROCESS> <MEM-LIMIT>", "      Start monitoring pss size of <PROCESS>, if it is at or", "      above <HEAP-LIMIT> then a heap dump is collected for the user to report.", "  clear-watch-heap");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "      Clear the previously set-watch-heap.", "  clear-start-info [--user <USER_ID> | all | current] <PACKAGE>", "      Clear process start-info for the given package.", "      Clear start-info for all packages if no package is provided.");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "  start-info-detailed-monitoring [--user <USER_ID> | all | current] <PACKAGE>", "      Enable application start info detailed monitoring for the given package.", "      Disable if no package is supplied.", "  clear-exit-info [--user <USER_ID> | all | current] <PACKAGE>");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "      Clear process exit-info for the given package.", "      Clear exit-info for all packages if no package is provided.", "  bug-report [--progress | --telephony]", "      Request bug report generation; will launch a notification");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "        when done to select where it should be delivered. Options are:", "     --progress: will launch a notification right away to show its progress.", "     --telephony: will dump only telephony sections.", "  fgs-notification-rate-limit {enable | disable}");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "     Enable/disable rate limit on FGS notification deferral policy.", "  force-stop [--user <USER_ID> | all | current] <PACKAGE>", "      Completely stop the given application package.", "  stop-app [--user <USER_ID> | all | current] <PACKAGE>");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "      Stop an app and all of its services.  Unlike `force-stop` this does", "      not cancel the app's scheduled alarms and jobs.", "  crash [--user <USER_ID>] <PACKAGE|PID>", "      Induce a VM crash in the specified package or process");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "  kill [--user <USER_ID> | all | current] <PACKAGE>", "      Kill all background processes associated with the given application.", "  kill-all", "      Kill all processes that are safe to kill (cached, etc).");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "  make-uid-idle [--user <USER_ID> | all | current] <PACKAGE>", "      If the given application's uid is in the background and waiting to", "      become idle (not allowing background services), do that now.", "  set-deterministic-uid-idle [--user <USER_ID> | all | current] <true|false>");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "      If true, sets the timing of making UIDs idle consistent and", "      deterministic. If false, the timing will be variable depending on", "      other activity on the device. The default is false.", "  monitor [--gdb <port>] [-p <TARGET>] [-s] [-c] [-k]");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "      Start monitoring for crashes or ANRs.", "      --gdb: start gdbserv on the given port at crash/ANR", "      -p: only show events related to a specific process / package", "      -s: simple mode, only show a summary line for each event");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "      -c: assume the input is always [c]ontinue", "      -k: assume the input is always [k]ill", "         -c and -k are mutually exclusive.", "  watch-uids [--oom <uid>] [--mask <capabilities integer>]");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "      Start watching for and reporting uid state changes.", "      --oom: specify a uid for which to report detailed change messages.", "      --mask: Specify PROCESS_CAPABILITY_XXX mask to report. ", "              By default, it only reports FOREGROUND_LOCATION (1)");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "              FOREGROUND_CAMERA (2), FOREGROUND_MICROPHONE (4)", "              and NETWORK (8). New capabilities added on or after", "              Android UDC will not be reported by default.", "  hang [--allow-restart]");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "      Hang the system.", "      --allow-restart: allow watchdog to perform normal system restart", "  restart", "      Restart the user-space system.");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "  idle-maintenance", "      Perform idle maintenance now.", "  screen-compat [on|off] <PACKAGE>", "      Control screen compatibility mode of <PACKAGE>.");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "  package-importance <PACKAGE>", "      Print current importance of <PACKAGE>.", "  to-uri [INTENT]", "      Print the given Intent specification as a URI.");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "  to-intent-uri [INTENT]", "      Print the given Intent specification as an intent: URI.", "  to-app-uri [INTENT]", "      Print the given Intent specification as an android-app: URI.");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "  switch-user <USER_ID>", "      Switch to put USER_ID in the foreground, starting", "      execution of that user if it is currently stopped.", "  get-current-user");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "      Returns id of the current foreground user.", "  start-user [-w] [--display DISPLAY_ID] <USER_ID>", "      Start USER_ID in background if it is currently stopped;", "      use switch-user if you want to start the user in foreground.");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "      -w: wait for start-user to complete and the user to be unlocked.", "      --display <DISPLAY_ID>: starts the user visible in that display, which allows the user to launch activities on it.", "        (not supported on all devices; typically only on automotive builds where the vehicle has passenger displays)", "  unlock-user <USER_ID>");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "      Unlock the given user.  This will only work if the user doesn't", "      have an LSKF (PIN/pattern/password).", "  stop-user [-w] [-f] <USER_ID>", "      Stop execution of USER_ID, not allowing it to run any");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "      code until a later explicit start or switch to it.", "      -w: wait for stop-user to complete.", "      -f: force stop, even if user has an unstoppable parent.", "  is-user-stopped <USER_ID>");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "      Returns whether <USER_ID> has been stopped or not.", "  get-started-user-state <USER_ID>", "      Gets the current state of the given started user.", "  track-associations");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "      Enable association tracking.", "  untrack-associations", "      Disable and clear association tracking.", "  get-uid-state <UID>");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "      Gets the process state of an app given its <UID>.", "  attach-agent <PROCESS> <FILE>", "    Attach an agent to the specified <PROCESS>, which may be either a process name or a PID.", "  get-config [--days N] [--device] [--proto] [--display <DISPLAY_ID>]");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "      Retrieve the configuration and any recent configurations of the device.", "      --days: also return last N days of configurations that have been seen.", "      --device: also output global device configuration info.", "      --proto: return result as a proto; does not include --days info.");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "      --display: Specify for which display to run the command; if not ", "          specified then run for the default display.", "  supports-multiwindow", "      Returns true if the device supports multiwindow.");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "  supports-split-screen-multi-window", "      Returns true if the device supports split screen multiwindow.", "  suppress-resize-config-changes <true|false>", "      Suppresses configuration changes due to user resizing an activity/task.");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "  set-inactive [--user <USER_ID>] <PACKAGE> true|false", "      Sets the inactive state of an app.", "  get-inactive [--user <USER_ID>] <PACKAGE>", "      Returns the inactive state of an app.");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "  set-standby-bucket [--user <USER_ID>] <PACKAGE> active|working_set|frequent|rare|restricted", "      Puts an app in the standby bucket.", "  get-standby-bucket [--user <USER_ID>] <PACKAGE>", "      Returns the standby bucket of an app.");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "  send-trim-memory [--user <USER_ID>] <PROCESS>", "          [HIDDEN|RUNNING_MODERATE|BACKGROUND|RUNNING_LOW|MODERATE|RUNNING_CRITICAL|COMPLETE]", "      Send a memory trim event to a <PROCESS>.  May also supply a raw trim int level.", "  display [COMMAND] [...]: sub-commands for operating on displays.");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "       move-stack <STACK_ID> <DISPLAY_ID>", "           Move <STACK_ID> from its current display to <DISPLAY_ID>.", "  stack [COMMAND] [...]: sub-commands for operating on activity stacks.", "       move-task <TASK_ID> <STACK_ID> [true|false]");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "           Move <TASK_ID> from its current stack to the top (true) or", "           bottom (false) of <STACK_ID>.", "       list", "           List all of the activity stacks and their sizes.");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "       info <WINDOWING_MODE> <ACTIVITY_TYPE>", "           Display the information about activity stack in <WINDOWING_MODE> and <ACTIVITY_TYPE>.", "       remove <STACK_ID>", "           Remove stack <STACK_ID>.");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "  task [COMMAND] [...]: sub-commands for operating on activity tasks.", "       lock <TASK_ID>", "           Bring <TASK_ID> to the front and don't allow other tasks to run.", "       lock stop");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "           End the current task lock.", "       resizeable <TASK_ID> [0|1|2|3]", "           Change resizeable mode of <TASK_ID> to one of the following:", "           0: unresizeable");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "           1: crop_windows", "           2: resizeable", "           3: resizeable_and_pipable", "       resize <TASK_ID> <LEFT> <TOP> <RIGHT> <BOTTOM>");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "           The task is resized only if it is in multi-window windowing", "           mode or freeform windowing mode.", "  update-appinfo <USER_ID> <PACKAGE_NAME> [<PACKAGE_NAME>...]", "      Update the ApplicationInfo objects of the listed packages for <USER_ID>");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "      without restarting any processes.", "  write", "      Write all pending state to storage.", "  compat [COMMAND] [...]: sub-commands for toggling app-compat changes.");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "         enable|disable [--no-kill] <CHANGE_ID|CHANGE_NAME> <PACKAGE_NAME>", "            Toggles a change either by id or by name for <PACKAGE_NAME>.", "            It kills <PACKAGE_NAME> (to allow the toggle to take effect) unless --no-kill is provided.", "         reset <CHANGE_ID|CHANGE_NAME> <PACKAGE_NAME>");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "            Toggles a change either by id or by name for <PACKAGE_NAME>.", "            It kills <PACKAGE_NAME> (to allow the toggle to take effect).", "         enable-all|disable-all <targetSdkVersion> <PACKAGE_NAME>", "            Toggles all changes that are gated by <targetSdkVersion>.");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "         reset-all [--no-kill] <PACKAGE_NAME>", "            Removes all existing overrides for all changes for ", "            <PACKAGE_NAME> (back to default behaviour).", "            It kills <PACKAGE_NAME> (to allow the toggle to take effect) unless --no-kill is provided.");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "  memory-factor [command] [...]: sub-commands for overriding memory pressure factor", "         set <NORMAL|MODERATE|LOW|CRITICAL>", "            Overrides memory pressure factor. May also supply a raw int level", "         show");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "            Shows the existing memory pressure factor", "         reset", "            Removes existing override for memory pressure factor", "  service-restart-backoff <COMMAND> [...]: sub-commands to toggle service restart backoff policy.");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "         enable|disable <PACKAGE_NAME>", "            Toggles the restart backoff policy on/off for <PACKAGE_NAME>.", "         show <PACKAGE_NAME>", "            Shows the restart backoff policy state for <PACKAGE_NAME>.");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "  get-isolated-pids <UID>", "         Get the PIDs of isolated processes with packages in this <UID>", "  set-stop-user-on-switch [true|false]", "         Sets whether the current user (and its profiles) should be stopped when switching to a different user.");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "         Without arguments, it resets to the value defined by platform.", "  set-bg-abusive-uids [uid=percentage][,uid=percentage...]", "         Force setting the battery usage of the given UID.", "  set-bg-restriction-level [--user <USER_ID>] <PACKAGE> unrestricted|exempted|adaptive_bucket|restricted_bucket|background_restricted|hibernation");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "         Set an app's background restriction level which in turn map to a app standby bucket.", "  get-bg-restriction-level [--user <USER_ID>] <PACKAGE>", "         Get an app's background restriction level.", "  list-displays-for-starting-users");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "         Lists the id of displays that can be used to start users on background.", "  set-foreground-service-delegate [--user <USER_ID>] <PACKAGE> start|stop", "         Start/stop an app's foreground service delegate.", "  set-ignore-delivery-group-policy <ACTION>");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "         Start ignoring delivery group policy set for a broadcast action", "  clear-ignore-delivery-group-policy <ACTION>", "         Stop ignoring delivery group policy set for a broadcast action", "  capabilities [--protobuf]");
        printWriter.println("         Output am supported features (text format). Options are:");
        printWriter.println("         --protobuf: format output using protobuffer");
        Intent.printIntentArgsHelp(printWriter, "");
    }

    public final int bucketNameToBucketValue(String str) {
        String lowerCase = str.toLowerCase();
        if (lowerCase.startsWith("ac")) {
            return 10;
        }
        if (lowerCase.startsWith("wo")) {
            return 20;
        }
        if (lowerCase.startsWith("fr")) {
            return 30;
        }
        if (lowerCase.startsWith("ra")) {
            return 40;
        }
        if (lowerCase.startsWith("re")) {
            return 45;
        }
        if (lowerCase.startsWith("ne")) {
            return 50;
        }
        try {
            return Integer.parseInt(lowerCase);
        } catch (NumberFormatException unused) {
            this.getErrPrintWriter().println("Error: Unknown bucket: ".concat(str));
            return -1;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v0 */
    /* JADX WARN: Type inference failed for: r2v1 */
    /* JADX WARN: Type inference failed for: r2v11, types: [com.android.server.am.ProcessRecord] */
    /* JADX WARN: Type inference failed for: r2v12, types: [int] */
    /* JADX WARN: Type inference failed for: r2v13 */
    /* JADX WARN: Type inference failed for: r2v14 */
    /* JADX WARN: Type inference failed for: r2v15 */
    /* JADX WARN: Type inference failed for: r2v16, types: [com.android.server.am.ProcessRecord] */
    /* JADX WARN: Type inference failed for: r2v17 */
    /* JADX WARN: Type inference failed for: r2v2, types: [com.android.server.am.ProcessRecord] */
    /* JADX WARN: Type inference failed for: r4v5, types: [com.android.server.am.ActivityManagerService$PidMap] */
    @NeverCompile
    public final ProcessRecord getProcessFromShell() throws RemoteException {
        ?? r2;
        String nextArgRequired = getNextArgRequired();
        try {
            r2 = Integer.parseInt(nextArgRequired);
        } catch (NumberFormatException unused) {
            r2 = 0;
        }
        try {
            synchronized (this.mInternal.mPidsSelfLocked) {
                try {
                    r2 = this.mInternal.mPidsSelfLocked.get(r2);
                    if (r2 == 0) {
                        ActivityManagerProcLock activityManagerProcLock = this.mInternal.mProcLock;
                        ActivityManagerService.boostPriorityForProcLockedSection();
                        synchronized (activityManagerProcLock) {
                            try {
                                SparseArray sparseArray = (SparseArray) this.mInternal.mProcessList.mProcessNames.getMap().get(nextArgRequired);
                                if (sparseArray != null && sparseArray.size() != 0) {
                                    if (sparseArray.size() > 1) {
                                        getErrPrintWriter().println("Error: more than one processes found");
                                        ActivityManagerService.resetPriorityAfterProcLockedSection();
                                        return null;
                                    }
                                    r2 = (ProcessRecord) sparseArray.valueAt(0);
                                    ActivityManagerService.resetPriorityAfterProcLockedSection();
                                }
                                getErrPrintWriter().println("Error: could not find process");
                                ActivityManagerService.resetPriorityAfterProcLockedSection();
                                return null;
                            } catch (Throwable th) {
                                ActivityManagerService.resetPriorityAfterProcLockedSection();
                                throw th;
                            }
                        }
                    }
                    return r2;
                } catch (Throwable th2) {
                    th = th2;
                    r2 = 0;
                    try {
                        throw th;
                    } catch (NumberFormatException unused2) {
                    }
                }
            }
        } catch (Throwable th3) {
            th = th3;
            r2 = r2;
        }
    }

    public final Resources getResources(PrintWriter printWriter) {
        Configuration configuration = this.mInterface.mActivityTaskManager.getConfiguration();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        displayMetrics.setToDefaults();
        return new Resources(AssetManager.getSystem(), displayMetrics, configuration);
    }

    public final void getUidState(PrintWriter printWriter) {
        this.mInternal.enforceCallingPermission("android.permission.DUMP", "getUidState()");
        int uidState = this.mInternal.getUidState(Integer.parseInt(getNextArgRequired()));
        printWriter.print(uidState);
        printWriter.print(" (");
        printWriter.printf(DebugUtils.valueToString(ActivityManager.class, "PROCESS_STATE_", uidState), new Object[0]);
        printWriter.println(")");
    }

    public final Intent makeIntent() {
        this.mStartFlags = 0;
        this.mWaitOption = false;
        this.mStopOption = false;
        this.mRepeat = 0;
        this.mProfileFile = null;
        this.mSamplingInterval = 0;
        this.mAutoStop = false;
        this.mStreaming = false;
        this.mUserId = -2;
        this.mDisplayId = -1;
        this.mTaskDisplayAreaFeatureId = -1;
        this.mWindowingMode = 0;
        this.mForceWindowingMode = 0;
        this.mActivityType = 0;
        this.mTaskId = -1;
        this.mIsTaskOverlay = false;
        this.mIsLockTask = false;
        this.mAsync = false;
        this.mBroadcastOptions = null;
        return Intent.parseCommandArgs(this, new Intent.CommandOptionHandler() { // from class: com.android.server.am.ActivityManagerShellCommand.1
            public final boolean handleOption(String str, ShellCommand shellCommand) {
                if (str.equals("-D")) {
                    ActivityManagerShellCommand.this.mStartFlags |= 2;
                } else if (str.equals("--suspend")) {
                    ActivityManagerShellCommand.this.mStartFlags |= 16;
                } else if (str.equals("-N")) {
                    ActivityManagerShellCommand.this.mStartFlags |= 8;
                } else if (str.equals("-W")) {
                    ActivityManagerShellCommand.this.mWaitOption = true;
                } else if (str.equals("-P")) {
                    ActivityManagerShellCommand activityManagerShellCommand = ActivityManagerShellCommand.this;
                    activityManagerShellCommand.mProfileFile = activityManagerShellCommand.getNextArgRequired();
                    ActivityManagerShellCommand.this.mAutoStop = true;
                } else if (str.equals("--start-profiler")) {
                    ActivityManagerShellCommand activityManagerShellCommand2 = ActivityManagerShellCommand.this;
                    activityManagerShellCommand2.mProfileFile = activityManagerShellCommand2.getNextArgRequired();
                    ActivityManagerShellCommand.this.mAutoStop = false;
                } else if (str.equals("--sampling")) {
                    ActivityManagerShellCommand activityManagerShellCommand3 = ActivityManagerShellCommand.this;
                    activityManagerShellCommand3.mSamplingInterval = Integer.parseInt(activityManagerShellCommand3.getNextArgRequired());
                } else if (str.equals("--clock-type")) {
                    ActivityManagerShellCommand.this.mClockType = ProfilerInfo.getClockTypeFromString(ActivityManagerShellCommand.this.getNextArgRequired());
                } else if (str.equals("--profiler-output-version")) {
                    ActivityManagerShellCommand activityManagerShellCommand4 = ActivityManagerShellCommand.this;
                    activityManagerShellCommand4.mProfilerOutputVersion = Integer.parseInt(activityManagerShellCommand4.getNextArgRequired());
                } else if (str.equals("--streaming")) {
                    ActivityManagerShellCommand.this.mStreaming = true;
                } else if (str.equals("--attach-agent")) {
                    ActivityManagerShellCommand activityManagerShellCommand5 = ActivityManagerShellCommand.this;
                    if (activityManagerShellCommand5.mAgent != null) {
                        shellCommand.getErrPrintWriter().println("Multiple --attach-agent(-bind) not supported");
                        return false;
                    }
                    activityManagerShellCommand5.mAgent = activityManagerShellCommand5.getNextArgRequired();
                    ActivityManagerShellCommand.this.mAttachAgentDuringBind = false;
                } else if (str.equals("--attach-agent-bind")) {
                    ActivityManagerShellCommand activityManagerShellCommand6 = ActivityManagerShellCommand.this;
                    if (activityManagerShellCommand6.mAgent != null) {
                        shellCommand.getErrPrintWriter().println("Multiple --attach-agent(-bind) not supported");
                        return false;
                    }
                    activityManagerShellCommand6.mAgent = activityManagerShellCommand6.getNextArgRequired();
                    ActivityManagerShellCommand.this.mAttachAgentDuringBind = true;
                } else if (str.equals("-R")) {
                    ActivityManagerShellCommand activityManagerShellCommand7 = ActivityManagerShellCommand.this;
                    activityManagerShellCommand7.mRepeat = Integer.parseInt(activityManagerShellCommand7.getNextArgRequired());
                } else if (str.equals("-S")) {
                    ActivityManagerShellCommand.this.mStopOption = true;
                } else if (str.equals("--track-allocation")) {
                    ActivityManagerShellCommand.this.mStartFlags |= 4;
                } else if (str.equals("--user")) {
                    ActivityManagerShellCommand activityManagerShellCommand8 = ActivityManagerShellCommand.this;
                    activityManagerShellCommand8.mUserId = UserHandle.parseUserArg(activityManagerShellCommand8.getNextArgRequired());
                } else if (str.equals("--receiver-permission")) {
                    ActivityManagerShellCommand activityManagerShellCommand9 = ActivityManagerShellCommand.this;
                    activityManagerShellCommand9.mReceiverPermission = activityManagerShellCommand9.getNextArgRequired();
                } else if (str.equals("--display")) {
                    ActivityManagerShellCommand activityManagerShellCommand10 = ActivityManagerShellCommand.this;
                    activityManagerShellCommand10.mDisplayId = Integer.parseInt(activityManagerShellCommand10.getNextArgRequired());
                } else if (str.equals("--task-display-area-feature-id")) {
                    ActivityManagerShellCommand activityManagerShellCommand11 = ActivityManagerShellCommand.this;
                    activityManagerShellCommand11.mTaskDisplayAreaFeatureId = Integer.parseInt(activityManagerShellCommand11.getNextArgRequired());
                } else if (str.equals("--windowingMode")) {
                    ActivityManagerShellCommand activityManagerShellCommand12 = ActivityManagerShellCommand.this;
                    activityManagerShellCommand12.mWindowingMode = Integer.parseInt(activityManagerShellCommand12.getNextArgRequired());
                } else if (str.equals("--forceWindowingMode")) {
                    ActivityManagerShellCommand activityManagerShellCommand13 = ActivityManagerShellCommand.this;
                    activityManagerShellCommand13.mForceWindowingMode = Integer.parseInt(activityManagerShellCommand13.getNextArgRequired());
                } else if (CoreRune.MT_NEW_DEX_LAUNCH_POLICY && str.equals("--forceLaunchTaskOnHome")) {
                    ActivityManagerShellCommand.this.mForceLaunchTaskOnHome = true;
                } else if (str.equals("--activityType")) {
                    ActivityManagerShellCommand activityManagerShellCommand14 = ActivityManagerShellCommand.this;
                    activityManagerShellCommand14.mActivityType = Integer.parseInt(activityManagerShellCommand14.getNextArgRequired());
                } else if (str.equals("--task")) {
                    ActivityManagerShellCommand activityManagerShellCommand15 = ActivityManagerShellCommand.this;
                    activityManagerShellCommand15.mTaskId = Integer.parseInt(activityManagerShellCommand15.getNextArgRequired());
                } else if (str.equals("--task-overlay")) {
                    ActivityManagerShellCommand.this.mIsTaskOverlay = true;
                } else if (str.equals("--lock-task")) {
                    ActivityManagerShellCommand.this.mIsLockTask = true;
                } else if (str.equals("--allow-background-activity-starts")) {
                    ActivityManagerShellCommand activityManagerShellCommand16 = ActivityManagerShellCommand.this;
                    if (activityManagerShellCommand16.mBroadcastOptions == null) {
                        activityManagerShellCommand16.mBroadcastOptions = BroadcastOptions.makeBasic();
                    }
                    ActivityManagerShellCommand.this.mBroadcastOptions.setBackgroundActivityStartsAllowed(true);
                } else if (str.equals("--async")) {
                    ActivityManagerShellCommand.this.mAsync = true;
                } else if (str.equals("--splashscreen-show-icon")) {
                    ActivityManagerShellCommand.this.mShowSplashScreen = true;
                } else if (str.equals("--dismiss-keyguard-if-insecure") || str.equals("--dismiss-keyguard")) {
                    ActivityManagerShellCommand.this.mDismissKeyguardIfInsecure = true;
                } else if (str.equals("--allow-fgs-start-reason")) {
                    int parseInt = Integer.parseInt(ActivityManagerShellCommand.this.getNextArgRequired());
                    ActivityManagerShellCommand.this.mBroadcastOptions = BroadcastOptions.makeBasic();
                    ActivityManagerShellCommand.this.mBroadcastOptions.setTemporaryAppAllowlist(10000L, 0, parseInt, "");
                } else {
                    if (!CoreRune.FW_SHELL_TRANSITION_RESUMED_AFFORDANCE || !str.equals("--resume-affordance")) {
                        return false;
                    }
                    ActivityManagerShellCommand.this.mIsResumeAffordanceRequested = true;
                }
                return true;
            }
        });
    }

    public final int onCommand(String str) {
        char c;
        String str2;
        if (str == null) {
            return handleDefaultCommands(str);
        }
        final PrintWriter outPrintWriter = getOutPrintWriter();
        try {
            switch (str.hashCode()) {
                case -2121667104:
                    if (str.equals("dumpheap")) {
                        c = 19;
                        break;
                    }
                    c = 65535;
                    break;
                case -1969672196:
                    if (str.equals("set-debug-app")) {
                        c = 20;
                        break;
                    }
                    c = 65535;
                    break;
                case -1864717225:
                    if (str.equals("set-deterministic-uid-idle")) {
                        c = '%';
                        break;
                    }
                    c = 65535;
                    break;
                case -1860393403:
                    if (str.equals("get-isolated-pids")) {
                        c = 'T';
                        break;
                    }
                    c = 65535;
                    break;
                case -1719979774:
                    if (str.equals("get-inactive")) {
                        c = '=';
                        break;
                    }
                    c = 65535;
                    break;
                case -1710503333:
                    if (str.equals("package-importance")) {
                        c = ',';
                        break;
                    }
                    c = 65535;
                    break;
                case -1667670943:
                    if (str.equals("get-standby-bucket")) {
                        c = '?';
                        break;
                    }
                    c = 65535;
                    break;
                case -1619282346:
                    if (str.equals("start-user")) {
                        c = '2';
                        break;
                    }
                    c = 65535;
                    break;
                case -1618876223:
                    if (str.equals(INetd.IF_FLAG_BROADCAST)) {
                        c = '\f';
                        break;
                    }
                    c = 65535;
                    break;
                case -1514943892:
                    if (str.equals("list-displays-for-starting-users")) {
                        c = '\\';
                        break;
                    }
                    c = 65535;
                    break;
                case -1499335439:
                    if (str.equals("start-info-detailed-monitoring")) {
                        c = 26;
                        break;
                    }
                    c = 65535;
                    break;
                case -1487597642:
                    if (str.equals("capabilities")) {
                        c = '^';
                        break;
                    }
                    c = 65535;
                    break;
                case -1470725846:
                    if (str.equals("reset-dropbox-rate-limiter")) {
                        c = '[';
                        break;
                    }
                    c = 65535;
                    break;
                case -1354812542:
                    if (str.equals("compat")) {
                        c = 'P';
                        break;
                    }
                    c = 65535;
                    break;
                case -1324660647:
                    if (str.equals("suppress-resize-config-changes")) {
                        c = ';';
                        break;
                    }
                    c = 65535;
                    break;
                case -1303445945:
                    if (str.equals("send-trim-memory")) {
                        c = '@';
                        break;
                    }
                    c = 65535;
                    break;
                case -1275145137:
                    if (str.equals("wait-for-broadcast-barrier")) {
                        c = 'K';
                        break;
                    }
                    c = 65535;
                    break;
                case -1266402665:
                    if (str.equals("freeze")) {
                        c = 14;
                        break;
                    }
                    c = 65535;
                    break;
                case -1182154244:
                    if (str.equals("set-foreground-service-delegate")) {
                        c = ']';
                        break;
                    }
                    c = 65535;
                    break;
                case -1131287478:
                    if (str.equals("start-service")) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                case -1002578147:
                    if (str.equals("get-uid-state")) {
                        c = '9';
                        break;
                    }
                    c = 65535;
                    break;
                case -965273485:
                    if (str.equals("stopservice")) {
                        c = '\n';
                        break;
                    }
                    c = 65535;
                    break;
                case -930080590:
                    if (str.equals("startfgservice")) {
                        c = 7;
                        break;
                    }
                    c = 65535;
                    break;
                case -907667276:
                    if (str.equals("unlock-user")) {
                        c = '3';
                        break;
                    }
                    c = 65535;
                    break;
                case -892396682:
                    if (str.equals("start-foreground-service")) {
                        c = '\b';
                        break;
                    }
                    c = 65535;
                    break;
                case -878494906:
                    if (str.equals("set-bg-restriction-level")) {
                        c = 'X';
                        break;
                    }
                    c = 65535;
                    break;
                case -870018278:
                    if (str.equals("to-uri")) {
                        c = '-';
                        break;
                    }
                    c = 65535;
                    break;
                case -812219210:
                    if (str.equals("get-current-user")) {
                        c = '1';
                        break;
                    }
                    c = 65535;
                    break;
                case -747637291:
                    if (str.equals("set-standby-bucket")) {
                        c = '>';
                        break;
                    }
                    c = 65535;
                    break;
                case -699625063:
                    if (str.equals("get-config")) {
                        c = ':';
                        break;
                    }
                    c = 65535;
                    break;
                case -656088391:
                    if (str.equals("clear-start-info")) {
                        c = 25;
                        break;
                    }
                    c = 65535;
                    break;
                case -606123342:
                    if (str.equals("kill-all")) {
                        c = '#';
                        break;
                    }
                    c = 65535;
                    break;
                case -548621938:
                    if (str.equals("is-user-stopped")) {
                        c = '5';
                        break;
                    }
                    c = 65535;
                    break;
                case -541939658:
                    if (str.equals("observe-foreground-process")) {
                        c = 'Z';
                        break;
                    }
                    c = 65535;
                    break;
                case -443938379:
                    if (str.equals("fgs-notification-rate-limit")) {
                        c = ' ';
                        break;
                    }
                    c = 65535;
                    break;
                case -387147436:
                    if (str.equals("track-associations")) {
                        c = '7';
                        break;
                    }
                    c = 65535;
                    break;
                case -379899280:
                    if (str.equals("unfreeze")) {
                        c = 15;
                        break;
                    }
                    c = 65535;
                    break;
                case -354890749:
                    if (str.equals("screen-compat")) {
                        c = '+';
                        break;
                    }
                    c = 65535;
                    break;
                case -309425751:
                    if (str.equals("profile")) {
                        c = 18;
                        break;
                    }
                    c = 65535;
                    break;
                case -225973678:
                    if (str.equals("service-restart-backoff")) {
                        c = 'S';
                        break;
                    }
                    c = 65535;
                    break;
                case -170987146:
                    if (str.equals("set-inactive")) {
                        c = '<';
                        break;
                    }
                    c = 65535;
                    break;
                case -149941524:
                    if (str.equals("list-bg-exemptions-config")) {
                        c = 'W';
                        break;
                    }
                    c = 65535;
                    break;
                case -146027423:
                    if (str.equals("watch-uids")) {
                        c = '\'';
                        break;
                    }
                    c = 65535;
                    break;
                case -138040195:
                    if (str.equals("clear-exit-info")) {
                        c = 27;
                        break;
                    }
                    c = 65535;
                    break;
                case -100644880:
                    if (str.equals("startforegroundservice")) {
                        c = 6;
                        break;
                    }
                    c = 65535;
                    break;
                case -74413870:
                    if (str.equals("get-bg-restriction-level")) {
                        c = 'Y';
                        break;
                    }
                    c = 65535;
                    break;
                case -27715536:
                    if (str.equals("make-uid-idle")) {
                        c = '$';
                        break;
                    }
                    c = 65535;
                    break;
                case 3194994:
                    if (str.equals("hang")) {
                        c = '(';
                        break;
                    }
                    c = 65535;
                    break;
                case 3198785:
                    if (str.equals("help")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 3291998:
                    if (str.equals("kill")) {
                        c = '\"';
                        break;
                    }
                    c = 65535;
                    break;
                case 3552645:
                    if (str.equals("task")) {
                        c = 'C';
                        break;
                    }
                    c = 65535;
                    break;
                case 88586660:
                    if (str.equals("force-stop")) {
                        c = 29;
                        break;
                    }
                    c = 65535;
                    break;
                case 94921639:
                    if (str.equals("crash")) {
                        c = '!';
                        break;
                    }
                    c = 65535;
                    break;
                case 109757064:
                    if (str.equals("stack")) {
                        c = 'B';
                        break;
                    }
                    c = 65535;
                    break;
                case 109757538:
                    if (str.equals("start")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 113399775:
                    if (str.equals("write")) {
                        c = 'D';
                        break;
                    }
                    c = 65535;
                    break;
                case 135017371:
                    if (str.equals("memory-factor")) {
                        c = 'R';
                        break;
                    }
                    c = 65535;
                    break;
                case 185053203:
                    if (str.equals("startservice")) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case 237240942:
                    if (str.equals("to-app-uri")) {
                        c = '/';
                        break;
                    }
                    c = 65535;
                    break;
                case 549617690:
                    if (str.equals("start-activity")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 622433197:
                    if (str.equals("untrack-associations")) {
                        c = '8';
                        break;
                    }
                    c = 65535;
                    break;
                case 661133534:
                    if (str.equals("wait-for-application-barrier")) {
                        c = 'L';
                        break;
                    }
                    c = 65535;
                    break;
                case 667014829:
                    if (str.equals("bug-report")) {
                        c = 28;
                        break;
                    }
                    c = 65535;
                    break;
                case 680834441:
                    if (str.equals("supports-split-screen-multi-window")) {
                        c = 'G';
                        break;
                    }
                    c = 65535;
                    break;
                case 723112852:
                    if (str.equals("trace-ipc")) {
                        c = 17;
                        break;
                    }
                    c = 65535;
                    break;
                case 759066228:
                    if (str.equals("start-in-vsync")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case 764545184:
                    if (str.equals("supports-multiwindow")) {
                        c = 'F';
                        break;
                    }
                    c = 65535;
                    break;
                case 782722708:
                    if (str.equals("set-bg-abusive-uids")) {
                        c = 'V';
                        break;
                    }
                    c = 65535;
                    break;
                case 808179021:
                    if (str.equals("to-intent-uri")) {
                        c = '.';
                        break;
                    }
                    c = 65535;
                    break;
                case 810242677:
                    if (str.equals("set-watch-heap")) {
                        c = 23;
                        break;
                    }
                    c = 65535;
                    break;
                case 817137578:
                    if (str.equals("clear-watch-heap")) {
                        c = 24;
                        break;
                    }
                    c = 65535;
                    break;
                case 822490030:
                    if (str.equals("set-agent-app")) {
                        c = 21;
                        break;
                    }
                    c = 65535;
                    break;
                case 847202110:
                    if (str.equals("clear-ignore-delivery-group-policy")) {
                        c = 'O';
                        break;
                    }
                    c = 65535;
                    break;
                case 900455412:
                    if (str.equals("start-fg-service")) {
                        c = '\t';
                        break;
                    }
                    c = 65535;
                    break;
                case 950483747:
                    if (str.equals("compact")) {
                        c = '\r';
                        break;
                    }
                    c = 65535;
                    break;
                case 1024703869:
                    if (str.equals("attach-agent")) {
                        c = 'E';
                        break;
                    }
                    c = 65535;
                    break;
                case 1070798153:
                    if (str.equals("set-ignore-delivery-group-policy")) {
                        c = 'N';
                        break;
                    }
                    c = 65535;
                    break;
                case 1078591527:
                    if (str.equals("clear-debug-app")) {
                        c = 22;
                        break;
                    }
                    c = 65535;
                    break;
                case 1097506319:
                    if (str.equals("restart")) {
                        c = ')';
                        break;
                    }
                    c = 65535;
                    break;
                case 1129261387:
                    if (str.equals("update-appinfo")) {
                        c = 'H';
                        break;
                    }
                    c = 65535;
                    break;
                case 1147479778:
                    if (str.equals("wait-for-broadcast-dispatch")) {
                        c = 'M';
                        break;
                    }
                    c = 65535;
                    break;
                case 1180451466:
                    if (str.equals("refresh-settings-cache")) {
                        c = 'Q';
                        break;
                    }
                    c = 65535;
                    break;
                case 1219773618:
                    if (str.equals("get-started-user-state")) {
                        c = '6';
                        break;
                    }
                    c = 65535;
                    break;
                case 1236319578:
                    if (str.equals("monitor")) {
                        c = PackageManagerShellCommandDataLoader.ARGS_DELIM;
                        break;
                    }
                    c = 65535;
                    break;
                case 1395483623:
                    if (str.equals("instrument")) {
                        c = 16;
                        break;
                    }
                    c = 65535;
                    break;
                case 1583986358:
                    if (str.equals("stop-user")) {
                        c = '4';
                        break;
                    }
                    c = 65535;
                    break;
                case 1618908732:
                    if (str.equals("wait-for-broadcast-idle")) {
                        c = 'J';
                        break;
                    }
                    c = 65535;
                    break;
                case 1671764162:
                    if (str.equals("display")) {
                        c = 'A';
                        break;
                    }
                    c = 65535;
                    break;
                case 1713645014:
                    if (str.equals("stop-app")) {
                        c = 30;
                        break;
                    }
                    c = 65535;
                    break;
                case 1768693408:
                    if (str.equals("set-stop-user-on-switch")) {
                        c = 'U';
                        break;
                    }
                    c = 65535;
                    break;
                case 1852789518:
                    if (str.equals("no-home-screen")) {
                        c = 'I';
                        break;
                    }
                    c = 65535;
                    break;
                case 1861559962:
                    if (str.equals("idle-maintenance")) {
                        c = '*';
                        break;
                    }
                    c = 65535;
                    break;
                case 1863290858:
                    if (str.equals("stop-service")) {
                        c = 11;
                        break;
                    }
                    c = 65535;
                    break;
                case 2030969636:
                    if (str.equals("clear-recent-apps")) {
                        c = 31;
                        break;
                    }
                    c = 65535;
                    break;
                case 2083239620:
                    if (str.equals("switch-user")) {
                        c = '0';
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                    onHelp();
                    return 0;
                case 1:
                case 2:
                    return CoreRune.BAIDU_CARLIFE ? runStartActivityForCarlife(outPrintWriter) : runStartActivity(outPrintWriter);
                case 3:
                    final ProgressWaiter progressWaiter = new ProgressWaiter(0);
                    final int[] iArr = {-1};
                    this.mInternal.mUiHandler.runWithScissors(new Runnable() { // from class: com.android.server.am.ActivityManagerShellCommand$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            final ActivityManagerShellCommand activityManagerShellCommand = ActivityManagerShellCommand.this;
                            final int[] iArr2 = iArr;
                            final PrintWriter printWriter = outPrintWriter;
                            final ActivityManagerShellCommand.ProgressWaiter progressWaiter2 = progressWaiter;
                            activityManagerShellCommand.getClass();
                            Choreographer.getInstance().postFrameCallback(new Choreographer.FrameCallback() { // from class: com.android.server.am.ActivityManagerShellCommand$$ExternalSyntheticLambda3
                                @Override // android.view.Choreographer.FrameCallback
                                public final void doFrame(long j) {
                                    ActivityManagerShellCommand activityManagerShellCommand2 = ActivityManagerShellCommand.this;
                                    int[] iArr3 = iArr2;
                                    PrintWriter printWriter2 = printWriter;
                                    ActivityManagerShellCommand.ProgressWaiter progressWaiter3 = progressWaiter2;
                                    activityManagerShellCommand2.getClass();
                                    try {
                                        iArr3[0] = activityManagerShellCommand2.runStartActivity(printWriter2);
                                        progressWaiter3.onFinished(0, null);
                                    } catch (Exception e) {
                                        activityManagerShellCommand2.getErrPrintWriter().println("Error: unable to start activity, " + e);
                                    }
                                }
                            });
                        }
                    }, 60000L);
                    try {
                        progressWaiter.mFinishedLatch.await(120000L, TimeUnit.MILLISECONDS);
                    } catch (InterruptedException unused) {
                        System.err.println("Thread interrupted unexpectedly.");
                    }
                    return iArr[0];
                case 4:
                case 5:
                    return runStartService(outPrintWriter, false);
                case 6:
                case 7:
                case '\b':
                case '\t':
                    return runStartService(outPrintWriter, true);
                case '\n':
                case 11:
                    return runStopService(outPrintWriter);
                case '\f':
                    runSendBroadcast(outPrintWriter);
                    return 0;
                case '\r':
                    return runCompact(outPrintWriter);
                case 14:
                    return runFreeze(outPrintWriter, true);
                case 15:
                    return runFreeze(outPrintWriter, false);
                case 16:
                    getOutPrintWriter().println("Error: must be invoked through 'am instrument'.");
                    return -1;
                case 17:
                    return runTraceIpc(outPrintWriter);
                case 18:
                    return runProfile();
                case 19:
                    return runDumpHeap(outPrintWriter);
                case 20:
                    return runSetDebugApp();
                case 21:
                    runSetAgentApp();
                    return 0;
                case 22:
                    runClearDebugApp();
                    return 0;
                case 23:
                    runSetWatchHeap();
                    return 0;
                case 24:
                    runClearWatchHeap();
                    return 0;
                case 25:
                    return runClearStartInfo();
                case 26:
                    return runStartInfoDetailedMonitoring(outPrintWriter);
                case 27:
                    return runClearExitInfo();
                case 28:
                    return runBugReport(outPrintWriter);
                case 29:
                    return runForceStop();
                case 30:
                    return runStopApp();
                case 31:
                    runClearRecentApps();
                    return 0;
                case ' ':
                    runFgsNotificationRateLimit();
                    return 0;
                case '!':
                    return runCrash();
                case '\"':
                    return runKill();
                case '#':
                    this.mInterface.killAllBackgroundProcesses();
                    return 0;
                case '$':
                    return runMakeIdle();
                case '%':
                    return runSetDeterministicUidIdle();
                case '&':
                    return runMonitor(outPrintWriter);
                case '\'':
                    return runWatchUids(outPrintWriter);
                case '(':
                    return runHang(outPrintWriter);
                case ')':
                    return runRestart(outPrintWriter);
                case '*':
                    return runIdleMaintenance(outPrintWriter);
                case '+':
                    return runScreenCompat();
                case ',':
                    runPackageImportance(outPrintWriter);
                    return 0;
                case '-':
                    runToUri(0, outPrintWriter);
                    return 0;
                case '.':
                    runToUri(1, outPrintWriter);
                    return 0;
                case '/':
                    runToUri(2, outPrintWriter);
                    return 0;
                case '0':
                    return runSwitchUser(outPrintWriter);
                case '1':
                    runGetCurrentUser(outPrintWriter);
                    return 0;
                case '2':
                    return runStartUser(outPrintWriter);
                case '3':
                    return runUnlockUser(outPrintWriter);
                case '4':
                    return runStopUser();
                case '5':
                    runIsUserStopped(outPrintWriter);
                    return 0;
                case '6':
                    runGetStartedUserState(outPrintWriter);
                    return 0;
                case '7':
                    runTrackAssociations(outPrintWriter);
                    return 0;
                case '8':
                    runUntrackAssociations(outPrintWriter);
                    return 0;
                case '9':
                    getUidState(outPrintWriter);
                    return 0;
                case ':':
                    return runGetConfig(outPrintWriter);
                case ';':
                    this.mTaskInterface.suppressResizeConfigChanges(Boolean.valueOf(getNextArgRequired()).booleanValue());
                    return 0;
                case '<':
                    return runSetInactive();
                case '=':
                    return runGetInactive(outPrintWriter);
                case '>':
                    return runSetStandbyBucket();
                case '?':
                    return runGetStandbyBucket(outPrintWriter);
                case '@':
                    return runSendTrimMemory();
                case 'A':
                    return runDisplay();
                case 'B':
                    return runStack(outPrintWriter);
                case 'C':
                    return runTask(outPrintWriter);
                case 'D':
                    runWrite(outPrintWriter);
                    return 0;
                case 'E':
                    return runAttachAgent(outPrintWriter);
                case 'F':
                    return runSupportsMultiwindow(outPrintWriter);
                case 'G':
                    if (getResources(outPrintWriter) == null) {
                        return -1;
                    }
                    outPrintWriter.println(ActivityTaskManager.supportsSplitScreenMultiWindow(this.mInternal.mContext));
                    return 0;
                case 'H':
                    runUpdateApplicationInfo(outPrintWriter);
                    return 0;
                case 'I':
                    Resources resources = getResources(outPrintWriter);
                    if (resources == null) {
                        return -1;
                    }
                    outPrintWriter.println(resources.getBoolean(R.bool.config_pinnerAssistantApp));
                    return 0;
                case 'J':
                    return runWaitForBroadcastIdle(outPrintWriter);
                case 'K':
                    return runWaitForBroadcastBarrier(outPrintWriter);
                case 'L':
                    this.mInternal.waitForApplicationBarrier(new PrintWriter((Writer) new TeeWriter(new Writer[]{ActivityManagerDebugConfig.LOG_WRITER_INFO, outPrintWriter})));
                    return 0;
                case 'M':
                    runWaitForBroadcastDispatch(outPrintWriter);
                    return 0;
                case 'N':
                    String nextArgRequired = getNextArgRequired();
                    ActivityManagerService activityManagerService = this.mInternal;
                    activityManagerService.getClass();
                    Objects.requireNonNull(nextArgRequired);
                    activityManagerService.enforceCallingPermission("android.permission.DUMP", "waitForBroadcastBarrier()");
                    synchronized (activityManagerService.mDeliveryGroupPolicyIgnoredActions) {
                        activityManagerService.mDeliveryGroupPolicyIgnoredActions.add(nextArgRequired);
                    }
                    return 0;
                case 'O':
                    String nextArgRequired2 = getNextArgRequired();
                    ActivityManagerService activityManagerService2 = this.mInternal;
                    activityManagerService2.getClass();
                    Objects.requireNonNull(nextArgRequired2);
                    activityManagerService2.enforceCallingPermission("android.permission.DUMP", "waitForBroadcastBarrier()");
                    synchronized (activityManagerService2.mDeliveryGroupPolicyIgnoredActions) {
                        activityManagerService2.mDeliveryGroupPolicyIgnoredActions.remove(nextArgRequired2);
                    }
                    return 0;
                case 'P':
                    return runCompat(outPrintWriter);
                case 'Q':
                    this.mInternal.mCoreSettingsObserver.onChange(true);
                    return 0;
                case 'R':
                    return runMemoryFactor(outPrintWriter);
                case 'S':
                    return runServiceRestartBackoff(outPrintWriter);
                case 'T':
                    runGetIsolatedProcesses(outPrintWriter);
                    return 0;
                case 'U':
                    runSetStopUserOnSwitch(outPrintWriter);
                    return 0;
                case 'V':
                    return runSetBgAbusiveUids();
                case 'W':
                    ArraySet arraySet = this.mInternal.mAppRestrictionController.mBgRestrictionExemptioFromSysConfig;
                    if (arraySet != null) {
                        int size = arraySet.size();
                        for (int i = 0; i < size; i++) {
                            outPrintWriter.print((String) arraySet.valueAt(i));
                            outPrintWriter.print(' ');
                        }
                        outPrintWriter.println();
                    }
                    return 0;
                case 'X':
                    return runSetBgRestrictionLevel(outPrintWriter);
                case 'Y':
                    return runGetBgRestrictionLevel(outPrintWriter);
                case 'Z':
                    runGetCurrentForegroundProcess(outPrintWriter, this.mInternal);
                    return 0;
                case '[':
                    this.mInternal.mDropboxRateLimiter.reset();
                    BootReceiver.resetDropboxRateLimiter();
                    return 0;
                case '\\':
                    int[] displayIdsForStartingVisibleBackgroundUsers = this.mInterface.getDisplayIdsForStartingVisibleBackgroundUsers();
                    if (displayIdsForStartingVisibleBackgroundUsers != null && displayIdsForStartingVisibleBackgroundUsers.length != 0) {
                        str2 = Arrays.toString(displayIdsForStartingVisibleBackgroundUsers);
                        outPrintWriter.println(str2);
                        return 0;
                    }
                    str2 = "none";
                    outPrintWriter.println(str2);
                    return 0;
                case ']':
                    return runSetForegroundServiceDelegate(outPrintWriter);
                case '^':
                    return runCapabilities(outPrintWriter);
                default:
                    return handleDefaultCommands(str);
            }
        } catch (RemoteException e) {
            UiModeManagerService$13$$ExternalSyntheticOutline0.m("Remote exception: ", e, outPrintWriter);
            return -1;
        }
        UiModeManagerService$13$$ExternalSyntheticOutline0.m("Remote exception: ", e, outPrintWriter);
        return -1;
    }

    public final void onHelp() {
        dumpHelp(getOutPrintWriter(), this.mDumping);
    }

    public final int runAttachAgent(PrintWriter printWriter) {
        IApplicationThread iApplicationThread;
        this.mInternal.enforceCallingPermission("android.permission.SET_ACTIVITY_WATCHER", "attach-agent");
        String nextArgRequired = getNextArgRequired();
        String nextArgRequired2 = getNextArgRequired();
        String nextArg = getNextArg();
        if (nextArg != null) {
            printWriter.println("Error: Unknown option: ".concat(nextArg));
            return -1;
        }
        ActivityManagerService activityManagerService = this.mInternal;
        activityManagerService.getClass();
        try {
            ActivityManagerProcLock activityManagerProcLock = activityManagerService.mProcLock;
            ActivityManagerService.boostPriorityForProcLockedSection();
            synchronized (activityManagerProcLock) {
                try {
                    ProcessRecord findProcessLOSP = activityManagerService.findProcessLOSP(0, nextArgRequired, "attachAgent");
                    if (findProcessLOSP == null || (iApplicationThread = findProcessLOSP.mThread) == null) {
                        throw new IllegalArgumentException("Unknown process: " + nextArgRequired);
                    }
                    activityManagerService.enforceDebuggable(findProcessLOSP);
                    iApplicationThread.attachAgent(nextArgRequired2);
                } catch (Throwable th) {
                    ActivityManagerService.resetPriorityAfterProcLockedSection();
                    throw th;
                }
            }
            ActivityManagerService.resetPriorityAfterProcLockedSection();
            return 0;
        } catch (RemoteException unused) {
            throw new IllegalStateException("Process disappeared");
        }
    }

    public final int runBugReport(PrintWriter printWriter) {
        boolean z = true;
        while (true) {
            String nextOption = getNextOption();
            if (nextOption == null) {
                if (z) {
                    this.mInterface.requestFullBugReport();
                }
                printWriter.println("Your lovely bug report is being created; please be patient.");
                return 0;
            }
            if (nextOption.equals("--progress")) {
                this.mInterface.requestInteractiveBugReport();
            } else {
                if (!nextOption.equals("--telephony")) {
                    getErrPrintWriter().println("Error: Unknown option: ".concat(nextOption));
                    return -1;
                }
                this.mInterface.requestBugReportWithDescription("", "", 4);
            }
            z = false;
        }
    }

    public final int runCapabilities(PrintWriter printWriter) {
        PrintWriter errPrintWriter = getErrPrintWriter();
        boolean z = false;
        while (true) {
            String nextOption = getNextOption();
            if (nextOption == null) {
                String property = System.getProperty("java.vm.name", "?");
                String property2 = System.getProperty("java.vm.version", "?");
                if (z) {
                    Capabilities capabilities = new Capabilities();
                    capabilities.values = new Capability[CAPABILITIES.length];
                    int i = 0;
                    while (true) {
                        String[] strArr = CAPABILITIES;
                        if (i >= strArr.length) {
                            break;
                        }
                        Capability capability = new Capability();
                        capability.name = strArr[i];
                        capabilities.values[i] = capability;
                        i++;
                    }
                    String[] vmFeatureList = Debug.getVmFeatureList();
                    capabilities.vmCapabilities = new VMCapability[vmFeatureList.length];
                    for (int i2 = 0; i2 < vmFeatureList.length; i2++) {
                        VMCapability vMCapability = new VMCapability();
                        vMCapability.name = vmFeatureList[i2];
                        capabilities.vmCapabilities[i2] = vMCapability;
                    }
                    String[] featureList = Debug.getFeatureList();
                    capabilities.frameworkCapabilities = new FrameworkCapability[featureList.length];
                    for (int i3 = 0; i3 < featureList.length; i3++) {
                        FrameworkCapability frameworkCapability = new FrameworkCapability();
                        frameworkCapability.name = featureList[i3];
                        capabilities.frameworkCapabilities[i3] = frameworkCapability;
                    }
                    VMInfo vMInfo = new VMInfo();
                    vMInfo.name = property;
                    vMInfo.version = property2;
                    capabilities.vmInfo = vMInfo;
                    try {
                        getRawOutputStream().write(Capabilities.toByteArray(capabilities));
                    } catch (IOException unused) {
                        printWriter.println("Error while serializing capabilities protobuffer");
                    }
                } else {
                    printWriter.println("Format: 2");
                    for (String str : CAPABILITIES) {
                        printWriter.println(str);
                    }
                    for (String str2 : Debug.getVmFeatureList()) {
                        printWriter.println("vm:" + str2);
                    }
                    for (String str3 : Debug.getFeatureList()) {
                        printWriter.println("framework:" + str3);
                    }
                    printWriter.println("vm_name:" + property);
                    printWriter.println("vm_version:" + property2);
                }
                return 0;
            }
            if (!nextOption.equals("--protobuf")) {
                errPrintWriter.println("Error: Unknown option: ".concat(nextOption));
                return -1;
            }
            z = true;
        }
    }

    public final void runClearDebugApp() {
        this.mInterface.setDebugApp(null, false, true, false);
    }

    public final int runClearExitInfo() {
        this.mInternal.enforceCallingPermission("android.permission.WRITE_SECURE_SETTINGS", "runClearExitInfo()");
        int i = -2;
        while (true) {
            String nextOption = getNextOption();
            if (nextOption == null) {
                if (i == -2) {
                    UserInfo currentUser = this.mInterface.mUserController.getCurrentUser();
                    if (currentUser == null) {
                        return -1;
                    }
                    i = currentUser.id;
                }
                AppExitInfoTracker appExitInfoTracker = this.mInternal.mProcessList.mAppExitInfoTracker;
                String nextArg = getNextArg();
                appExitInfoTracker.getClass();
                NativeTombstoneManager nativeTombstoneManager = (NativeTombstoneManager) LocalServices.getService(NativeTombstoneManager.class);
                Optional empty = Optional.empty();
                if (TextUtils.isEmpty(nextArg)) {
                    synchronized (appExitInfoTracker.mLock) {
                        appExitInfoTracker.removeByUserIdLocked(i);
                    }
                } else {
                    int packageUid = appExitInfoTracker.mService.mPackageManagerInt.getPackageUid(nextArg, 131072L, i);
                    Optional of = Optional.of(Integer.valueOf(UserHandle.getAppId(packageUid)));
                    synchronized (appExitInfoTracker.mLock) {
                        appExitInfoTracker.removePackageLocked(packageUid, i, nextArg, true);
                    }
                    empty = of;
                }
                Optional of2 = Optional.of(Integer.valueOf(i));
                nativeTombstoneManager.getClass();
                nativeTombstoneManager.mHandler.post(new NativeTombstoneManager$$ExternalSyntheticLambda2(nativeTombstoneManager, of2, empty));
                appExitInfoTracker.schedulePersistProcessExitInfo(true);
                return 0;
            }
            if (!nextOption.equals("--user")) {
                getErrPrintWriter().println("Error: Unknown option: ".concat(nextOption));
                return -1;
            }
            i = UserHandle.parseUserArg(getNextArgRequired());
        }
    }

    public final void runClearRecentApps() {
        this.mTaskInterface.removeAllVisibleRecentTasks();
    }

    public final int runClearStartInfo() {
        this.mInternal.enforceCallingPermission("android.permission.WRITE_SECURE_SETTINGS", "runClearStartInfo()");
        int i = -2;
        while (true) {
            String nextOption = getNextOption();
            if (nextOption == null) {
                if (i == -2) {
                    UserInfo currentUser = this.mInterface.mUserController.getCurrentUser();
                    if (currentUser == null) {
                        return -1;
                    }
                    i = currentUser.id;
                }
                AppStartInfoTracker appStartInfoTracker = this.mInternal.mProcessList.mAppStartInfoTracker;
                String nextArg = getNextArg();
                if (!appStartInfoTracker.mEnabled) {
                    return 0;
                }
                Optional.empty();
                if (TextUtils.isEmpty(nextArg)) {
                    synchronized (appStartInfoTracker.mLock) {
                        if (i == -1) {
                            appStartInfoTracker.mData.getMap().clear();
                        } else {
                            appStartInfoTracker.forEachPackageLocked(new AppStartInfoTracker$$ExternalSyntheticLambda3(i));
                        }
                    }
                } else {
                    Optional.of(Integer.valueOf(UserHandle.getAppId(appStartInfoTracker.mService.mPackageManagerInt.getPackageUid(nextArg, 131072L, i))));
                    synchronized (appStartInfoTracker.mLock) {
                        appStartInfoTracker.removePackageLocked(i, nextArg);
                    }
                }
                appStartInfoTracker.schedulePersistProcessStartInfo(true);
                return 0;
            }
            if (!nextOption.equals("--user")) {
                getErrPrintWriter().println("Error: Unknown option: ".concat(nextOption));
                return -1;
            }
            i = UserHandle.parseUserArg(getNextArgRequired());
        }
    }

    public final void runClearWatchHeap() {
        this.mInterface.setDumpHeapDebugLimit(getNextArgRequired(), 0, -1L, null);
    }

    @NeverCompile
    public final int runCompact(PrintWriter printWriter) throws RemoteException {
        String nextArgRequired = getNextArgRequired();
        boolean equals = nextArgRequired.equals("full");
        boolean equals2 = nextArgRequired.equals("some");
        if (equals || equals2) {
            ProcessRecord processFromShell = getProcessFromShell();
            if (processFromShell == null) {
                getErrPrintWriter().println("Error: could not find process");
                return -1;
            }
            AccessibilityManagerService$$ExternalSyntheticOutline0.m(new StringBuilder("Process record found pid: "), processFromShell.mPid, printWriter);
            if (equals) {
                AccessibilityManagerService$$ExternalSyntheticOutline0.m(new StringBuilder("Executing full compaction for "), processFromShell.mPid, printWriter);
                ActivityManagerProcLock activityManagerProcLock = this.mInternal.mProcLock;
                ActivityManagerService.boostPriorityForProcLockedSection();
                synchronized (activityManagerProcLock) {
                    try {
                        this.mInternal.mOomAdjuster.mCachedAppOptimizer.compactApp(processFromShell, CachedAppOptimizer.CompactProfile.FULL, CachedAppOptimizer.CompactSource.SHELL, true);
                    } finally {
                    }
                }
                ActivityManagerService.resetPriorityAfterProcLockedSection();
                AccessibilityManagerService$$ExternalSyntheticOutline0.m(new StringBuilder("Finished full compaction for "), processFromShell.mPid, printWriter);
                return 0;
            }
            if (!equals2) {
                return 0;
            }
            AccessibilityManagerService$$ExternalSyntheticOutline0.m(new StringBuilder("Executing some compaction for "), processFromShell.mPid, printWriter);
            ActivityManagerProcLock activityManagerProcLock2 = this.mInternal.mProcLock;
            ActivityManagerService.boostPriorityForProcLockedSection();
            synchronized (activityManagerProcLock2) {
                try {
                    this.mInternal.mOomAdjuster.mCachedAppOptimizer.compactApp(processFromShell, CachedAppOptimizer.CompactProfile.SOME, CachedAppOptimizer.CompactSource.SHELL, true);
                } finally {
                    ActivityManagerService.resetPriorityAfterProcLockedSection();
                }
            }
            ActivityManagerService.resetPriorityAfterProcLockedSection();
            AccessibilityManagerService$$ExternalSyntheticOutline0.m(new StringBuilder("Finished some compaction for "), processFromShell.mPid, printWriter);
            return 0;
        }
        if (nextArgRequired.equals("system")) {
            printWriter.println("Executing system compaction");
            ActivityManagerProcLock activityManagerProcLock3 = this.mInternal.mProcLock;
            ActivityManagerService.boostPriorityForProcLockedSection();
            synchronized (activityManagerProcLock3) {
                try {
                    CachedAppOptimizer cachedAppOptimizer = this.mInternal.mOomAdjuster.mCachedAppOptimizer;
                    if (cachedAppOptimizer.useCompaction()) {
                        Trace.instantForTrack(64L, "Compaction", "compactAllSystem");
                        Handler handler = cachedAppOptimizer.mCompactionHandler;
                        handler.sendMessage(handler.obtainMessage(2));
                    }
                } finally {
                }
            }
            ActivityManagerService.resetPriorityAfterProcLockedSection();
            printWriter.println("Finished system compaction");
            return 0;
        }
        if (!nextArgRequired.equals("native")) {
            BinaryTransparencyService$$ExternalSyntheticOutline0.m(getErrPrintWriter(), "Error: unknown compact command '", nextArgRequired, "'");
            return -1;
        }
        String nextArgRequired2 = getNextArgRequired();
        boolean equals3 = nextArgRequired2.equals("full");
        boolean equals4 = nextArgRequired2.equals("some");
        String nextArgRequired3 = getNextArgRequired();
        try {
            int parseInt = Integer.parseInt(nextArgRequired3);
            if (equals3) {
                Handler handler2 = this.mInternal.mOomAdjuster.mCachedAppOptimizer.mCompactionHandler;
                handler2.sendMessage(handler2.obtainMessage(5, parseInt, 3));
                return 0;
            }
            if (!equals4) {
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(getErrPrintWriter(), "Error: unknown compaction type '", nextArgRequired2, "'");
                return -1;
            }
            Handler handler3 = this.mInternal.mOomAdjuster.mCachedAppOptimizer.mCompactionHandler;
            handler3.sendMessage(handler3.obtainMessage(5, parseInt, 1));
            return 0;
        } catch (Exception unused) {
            BinaryTransparencyService$$ExternalSyntheticOutline0.m(getErrPrintWriter(), "Error: failed to parse '", nextArgRequired3, "' as a PID");
            return -1;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x00fd  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x01f9  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int runCompat(java.io.PrintWriter r18) {
        /*
            Method dump skipped, instructions count: 625
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.am.ActivityManagerShellCommand.runCompat(java.io.PrintWriter):int");
    }

    public final int runCrash() {
        String str;
        int i;
        int i2 = -1;
        while (true) {
            String nextOption = getNextOption();
            if (nextOption == null) {
                String nextArgRequired = getNextArgRequired();
                try {
                    i = Integer.parseInt(nextArgRequired);
                    str = null;
                } catch (NumberFormatException unused) {
                    str = nextArgRequired;
                    i = -1;
                }
                for (int i3 : i2 == -1 ? this.mInternal.mUserController.mInjector.getUserManager().getUserIds() : new int[]{i2}) {
                    if (this.mInternal.mUserController.hasUserRestriction("no_debugging_features", i3)) {
                        AccountManagerServiceShellCommand$$ExternalSyntheticOutline0.m(getOutPrintWriter(), "Shell does not have permission to crash packages for user ", i3);
                    } else {
                        this.mInterface.crashApplicationWithTypeWithExtras(-1, i, str, i3, "shell-induced crash", false, 5, null);
                    }
                }
                return 0;
            }
            if (!nextOption.equals("--user")) {
                getErrPrintWriter().println("Error: Unknown option: ".concat(nextOption));
                return -1;
            }
            i2 = UserHandle.parseUserArg(getNextArgRequired());
        }
    }

    public final int runDisplay() {
        String nextArgRequired = getNextArgRequired();
        nextArgRequired.getClass();
        if (!nextArgRequired.equals("move-stack")) {
            BinaryTransparencyService$$ExternalSyntheticOutline0.m(getErrPrintWriter(), "Error: unknown command '", nextArgRequired, "'");
            return -1;
        }
        this.mTaskInterface.moveRootTaskToDisplay(Integer.parseInt(getNextArgRequired()), Integer.parseInt(getNextArgRequired()));
        return 0;
    }

    public final int runDumpHeap(PrintWriter printWriter) {
        PrintWriter errPrintWriter = getErrPrintWriter();
        boolean z = true;
        boolean z2 = false;
        boolean z3 = false;
        String str = null;
        int i = -2;
        while (true) {
            String nextOption = getNextOption();
            if (nextOption == null) {
                String nextArgRequired = getNextArgRequired();
                String nextArg = getNextArg();
                if (nextArg == null) {
                    nextArg = XmlUtils$$ExternalSyntheticOutline0.m("/data/local/tmp/heapdump-", LOG_NAME_TIME_FORMATTER.format(LocalDateTime.now(Clock.systemDefaultZone())), ".prof");
                }
                String str2 = nextArg;
                ParcelFileDescriptor openFileForSystem = openFileForSystem(str2, "w");
                if (openFileForSystem == null) {
                    return -1;
                }
                printWriter.println("File: " + str2);
                printWriter.flush();
                final CountDownLatch countDownLatch = new CountDownLatch(1);
                this.mInterface.dumpHeap(nextArgRequired, i, z, z2, z3, str, str2, openFileForSystem, new RemoteCallback(new RemoteCallback.OnResultListener() { // from class: com.android.server.am.ActivityManagerShellCommand.2
                    public final void onResult(Bundle bundle) {
                        countDownLatch.countDown();
                    }
                }, (Handler) null));
                printWriter.println("Waiting for dump to finish...");
                printWriter.flush();
                try {
                    countDownLatch.await();
                } catch (InterruptedException unused) {
                    errPrintWriter.println("Caught InterruptedException");
                }
                return 0;
            }
            if (nextOption.equals("--user")) {
                i = UserHandle.parseUserArg(getNextArgRequired());
                if (i == -1) {
                    errPrintWriter.println("Error: Can't dump heap with user 'all'");
                    return -1;
                }
            } else {
                if (!nextOption.equals("-n")) {
                    if (nextOption.equals("-g")) {
                        z3 = true;
                    } else if (nextOption.equals("-m")) {
                        z2 = true;
                    } else {
                        if (!nextOption.equals("-b")) {
                            errPrintWriter.println("Error: Unknown option: ".concat(nextOption));
                            return -1;
                        }
                        str = getNextArg();
                        if (str == null) {
                            str = "png";
                        }
                    }
                }
                z = false;
            }
        }
    }

    public final void runFgsNotificationRateLimit() {
        boolean z;
        String nextArgRequired = getNextArgRequired();
        nextArgRequired.getClass();
        if (nextArgRequired.equals("enable")) {
            z = true;
        } else {
            if (!nextArgRequired.equals("disable")) {
                throw new IllegalArgumentException("Argument must be either 'enable' or 'disable'");
            }
            z = false;
        }
        this.mInterface.enableFgsNotificationRateLimit(z);
    }

    public final int runForceStop() {
        int i = -1;
        while (true) {
            String nextOption = getNextOption();
            if (nextOption == null) {
                String nextArgRequired = getNextArgRequired();
                String replaceAll = SemCscFeature.getInstance().getString("CscFeature_Framework_ConfigForbiddenADBKillForceStopApps", "").replaceAll(" ", "");
                if (!replaceAll.isEmpty()) {
                    for (String str : replaceAll.split(",")) {
                        if (nextArgRequired.equals(str)) {
                            getErrPrintWriter().println("Error: cannot force-stop app - package: ".concat(nextArgRequired));
                            return -1;
                        }
                    }
                }
                this.mInterface.forceStopPackage(nextArgRequired, i);
                return 0;
            }
            if (!nextOption.equals("--user")) {
                getErrPrintWriter().println("Error: Unknown option: ".concat(nextOption));
                return -1;
            }
            i = UserHandle.parseUserArg(getNextArgRequired());
        }
    }

    @NeverCompile
    public final int runFreeze(PrintWriter printWriter, boolean z) throws RemoteException {
        String nextOption = getNextOption();
        boolean equals = nextOption != null ? nextOption.equals("--sticky") : false;
        ProcessRecord processFromShell = getProcessFromShell();
        if (processFromShell == null) {
            return -1;
        }
        StringBuilder m = BinaryTransparencyService$$ExternalSyntheticOutline0.m(printWriter, z ? "Freezing" : "Unfreezing", " process ");
        m.append(processFromShell.processName);
        printWriter.print(m.toString());
        printWriter.println(" (" + processFromShell.mPid + ") sticky=" + equals);
        ActivityManagerService activityManagerService = this.mInternal;
        ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                ActivityManagerProcLock activityManagerProcLock = this.mInternal.mProcLock;
                ActivityManagerService.boostPriorityForProcLockedSection();
                synchronized (activityManagerProcLock) {
                    try {
                        processFromShell.mOptRecord.mFreezeSticky = equals;
                        if (z) {
                            this.mInternal.mOomAdjuster.mCachedAppOptimizer.freezeAppAsyncInternalLSP(0L, processFromShell, true);
                        } else {
                            this.mInternal.mOomAdjuster.mCachedAppOptimizer.unfreezeAppInternalLSP(processFromShell, 0, true);
                        }
                    } catch (Throwable th) {
                        ActivityManagerService.resetPriorityAfterProcLockedSection();
                        throw th;
                    }
                }
                ActivityManagerService.resetPriorityAfterProcLockedSection();
            } catch (Throwable th2) {
                ActivityManagerService.resetPriorityAfterLockedSection();
                throw th2;
            }
        }
        ActivityManagerService.resetPriorityAfterLockedSection();
        return 0;
    }

    public final int runGetBgRestrictionLevel(PrintWriter printWriter) {
        int i = -2;
        while (true) {
            String nextOption = getNextOption();
            if (nextOption == null) {
                String nextArgRequired = getNextArgRequired();
                ActivityManagerService activityManagerService = this.mInternal;
                activityManagerService.getClass();
                int callingUid = Binder.getCallingUid();
                if (callingUid != 1000 && callingUid != 0 && callingUid != 2000) {
                    throw new SecurityException("Don't have permission to query app background restriction level");
                }
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    int restrictionLevel = activityManagerService.mInternal.getRestrictionLevel(nextArgRequired, i);
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    printWriter.println(ActivityManager.restrictionLevelToName(restrictionLevel));
                    return 0;
                } catch (Throwable th) {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    throw th;
                }
            }
            if (!nextOption.equals("--user")) {
                getErrPrintWriter().println("Error: Unknown option: ".concat(nextOption));
                return -1;
            }
            i = UserHandle.parseUserArg(getNextArgRequired());
        }
    }

    public final int runGetConfig(PrintWriter printWriter) {
        List emptyList;
        int i = -1;
        int i2 = 0;
        boolean z = false;
        boolean z2 = false;
        while (true) {
            String nextOption = getNextOption();
            if (nextOption == null) {
                Configuration configuration = this.mInterface.mActivityTaskManager.getConfiguration();
                Display display = ((DisplayManager) this.mInternal.mContext.getSystemService(DisplayManager.class)).getDisplay(i2);
                if (display == null) {
                    AccountManagerServiceShellCommand$$ExternalSyntheticOutline0.m(getErrPrintWriter(), "Error: Display does not exist: ", i2);
                    return -1;
                }
                DisplayMetrics displayMetrics = new DisplayMetrics();
                display.getMetrics(displayMetrics);
                if (z) {
                    ProtoOutputStream protoOutputStream = new ProtoOutputStream(getOutFileDescriptor());
                    configuration.writeResConfigToProto(protoOutputStream, 1146756268033L, displayMetrics);
                    if (z2) {
                        writeDeviceConfig(protoOutputStream, 1146756268034L, null, displayMetrics);
                    }
                    protoOutputStream.flush();
                } else {
                    printWriter.println("config: " + Configuration.resourceQualifierString(configuration, displayMetrics));
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(new StringBuilder("abi: "), TextUtils.join(",", Build.SUPPORTED_ABIS), printWriter);
                    if (z2) {
                        writeDeviceConfig(null, -1L, printWriter, displayMetrics);
                    }
                    if (i >= 0) {
                        IUsageStatsManager asInterface = IUsageStatsManager.Stub.asInterface(ServiceManager.getService("usagestats"));
                        long currentTimeMillis = System.currentTimeMillis();
                        try {
                            ParceledListSlice queryConfigurationStats = asInterface.queryConfigurationStats(4, currentTimeMillis - (i * 86400000), currentTimeMillis, "com.android.shell");
                            if (queryConfigurationStats == null) {
                                emptyList = Collections.emptyList();
                            } else {
                                final ArrayMap arrayMap = new ArrayMap();
                                List list = queryConfigurationStats.getList();
                                int size = list.size();
                                for (int i3 = 0; i3 < size; i3++) {
                                    ConfigurationStats configurationStats = (ConfigurationStats) list.get(i3);
                                    int indexOfKey = arrayMap.indexOfKey(configurationStats.getConfiguration());
                                    if (indexOfKey < 0) {
                                        arrayMap.put(configurationStats.getConfiguration(), Integer.valueOf(configurationStats.getActivationCount()));
                                    } else {
                                        arrayMap.setValueAt(indexOfKey, Integer.valueOf(((Integer) arrayMap.valueAt(indexOfKey)).intValue() + configurationStats.getActivationCount()));
                                    }
                                }
                                Comparator comparator = new Comparator() { // from class: com.android.server.am.ActivityManagerShellCommand.4
                                    @Override // java.util.Comparator
                                    public final int compare(Object obj, Object obj2) {
                                        return ((Integer) arrayMap.get((Configuration) obj2)).compareTo((Integer) arrayMap.get((Configuration) obj));
                                    }
                                };
                                ArrayList arrayList = new ArrayList(arrayMap.size());
                                arrayList.addAll(arrayMap.keySet());
                                Collections.sort(arrayList, comparator);
                                emptyList = arrayList;
                            }
                        } catch (RemoteException unused) {
                            emptyList = Collections.emptyList();
                        }
                        int size2 = emptyList.size();
                        if (size2 > 0) {
                            printWriter.println("recentConfigs:");
                            for (int i4 = 0; i4 < size2; i4++) {
                                printWriter.println("  config: " + Configuration.resourceQualifierString((Configuration) emptyList.get(i4)));
                            }
                        }
                    }
                }
                return 0;
            }
            if (nextOption.equals("--days")) {
                i = Integer.parseInt(getNextArgRequired());
                if (i <= 0) {
                    throw new IllegalArgumentException("--days must be a positive integer");
                }
            } else if (nextOption.equals("--proto")) {
                z = true;
            } else if (nextOption.equals("--device")) {
                z2 = true;
            } else {
                if (!nextOption.equals("--display")) {
                    getErrPrintWriter().println("Error: Unknown option: ".concat(nextOption));
                    return -1;
                }
                i2 = Integer.parseInt(getNextArgRequired());
                if (i2 < 0) {
                    throw new IllegalArgumentException("--display must be a non-negative integer");
                }
            }
        }
    }

    public final void runGetCurrentForegroundProcess(PrintWriter printWriter, IActivityManager iActivityManager) {
        ProcessObserver processObserver = new ProcessObserver();
        processObserver.mPw = printWriter;
        processObserver.mIam = iActivityManager;
        iActivityManager.registerProcessObserver(processObserver);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getRawInputStream()));
        while (true) {
            try {
                try {
                    String readLine = bufferedReader.readLine();
                    if (readLine == null) {
                        break;
                    }
                    if (readLine.length() > 0) {
                        if ("q".equals(readLine) || "quit".equals(readLine)) {
                            break;
                        }
                        printWriter.println("Invalid command: " + readLine);
                        printWriter.println("");
                    }
                    printWriter.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                    printWriter.flush();
                }
            } finally {
                iActivityManager.unregisterProcessObserver(processObserver);
            }
        }
    }

    public final void runGetCurrentUser(PrintWriter printWriter) {
        int currentUserId = this.mInterface.getCurrentUserId();
        if (currentUserId == -10000) {
            throw new IllegalStateException("Current user not set");
        }
        printWriter.println(currentUserId);
    }

    public final int runGetInactive(PrintWriter printWriter) {
        int i = -2;
        while (true) {
            String nextOption = getNextOption();
            if (nextOption == null) {
                printWriter.println("Idle=" + IUsageStatsManager.Stub.asInterface(ServiceManager.getService("usagestats")).isAppInactive(getNextArgRequired(), i, "com.android.shell"));
                return 0;
            }
            if (!nextOption.equals("--user")) {
                getErrPrintWriter().println("Error: Unknown option: ".concat(nextOption));
                return -1;
            }
            i = UserHandle.parseUserArg(getNextArgRequired());
        }
    }

    public final void runGetIsolatedProcesses(PrintWriter printWriter) {
        this.mInternal.enforceCallingPermission("android.permission.DUMP", "getIsolatedProcesses()");
        List isolatedProcesses = this.mInternal.mInternal.getIsolatedProcesses(Integer.parseInt(getNextArgRequired()));
        printWriter.print("[");
        if (isolatedProcesses != null) {
            int size = isolatedProcesses.size();
            for (int i = 0; i < size; i++) {
                if (i > 0) {
                    printWriter.print(", ");
                }
                printWriter.print(isolatedProcesses.get(i));
            }
        }
        printWriter.println("]");
    }

    public final int runGetStandbyBucket(PrintWriter printWriter) {
        int i = -2;
        while (true) {
            String nextOption = getNextOption();
            if (nextOption == null) {
                String nextArg = getNextArg();
                IUsageStatsManager asInterface = IUsageStatsManager.Stub.asInterface(ServiceManager.getService("usagestats"));
                if (nextArg != null) {
                    printWriter.println(asInterface.getAppStandbyBucket(nextArg, (String) null, i));
                    return 0;
                }
                for (AppStandbyInfo appStandbyInfo : asInterface.getAppStandbyBuckets("com.android.shell", i).getList()) {
                    printWriter.print(appStandbyInfo.mPackageName);
                    printWriter.print(": ");
                    printWriter.println(appStandbyInfo.mStandbyBucket);
                }
                return 0;
            }
            if (!nextOption.equals("--user")) {
                getErrPrintWriter().println("Error: Unknown option: ".concat(nextOption));
                return -1;
            }
            i = UserHandle.parseUserArg(getNextArgRequired());
        }
    }

    public final void runGetStartedUserState(PrintWriter printWriter) {
        this.mInternal.enforceCallingPermission("android.permission.DUMP", "runGetStartedUserState()");
        int parseInt = Integer.parseInt(getNextArgRequired());
        try {
            printWriter.println(UserState.stateToString(this.mInternal.mUserController.getStartedUserState(parseInt).state));
        } catch (NullPointerException unused) {
            AccountManagerServiceShellCommand$$ExternalSyntheticOutline0.m(printWriter, "User is not started: ", parseInt);
        }
    }

    public final int runHang(PrintWriter printWriter) {
        boolean z = false;
        while (true) {
            String nextOption = getNextOption();
            if (nextOption == null) {
                printWriter.println("Hanging the system...");
                printWriter.flush();
                try {
                    this.mInterface.hang(getShellCallback().getShellCallbackBinder(), z);
                    return 0;
                } catch (NullPointerException unused) {
                    printWriter.println("Hanging failed, since caller " + Binder.getCallingPid() + " did not provide a ShellCallback!");
                    printWriter.flush();
                    return 1;
                }
            }
            if (!nextOption.equals("--allow-restart")) {
                getErrPrintWriter().println("Error: Unknown option: ".concat(nextOption));
                return -1;
            }
            z = true;
        }
    }

    public final int runIdleMaintenance(PrintWriter printWriter) {
        String nextOption = getNextOption();
        if (nextOption != null) {
            getErrPrintWriter().println("Error: Unknown option: ".concat(nextOption));
            return -1;
        }
        printWriter.println("Performing idle maintenance...");
        this.mInterface.sendIdleJobTrigger();
        this.mInternal.performIdleMaintenance();
        return 0;
    }

    public final void runIsUserStopped(PrintWriter printWriter) {
        printWriter.println(this.mInternal.mUserController.getStartedUserState(UserHandle.parseUserArg(getNextArgRequired())) == null);
    }

    public final int runKill() {
        int i = -1;
        while (true) {
            String nextOption = getNextOption();
            if (nextOption == null) {
                String nextArgRequired = getNextArgRequired();
                String replaceAll = SemCscFeature.getInstance().getString("CscFeature_Framework_ConfigForbiddenADBKillForceStopApps", "").replaceAll(" ", "");
                if (!replaceAll.isEmpty()) {
                    for (String str : replaceAll.split(",")) {
                        if (nextArgRequired.equals(str)) {
                            getErrPrintWriter().println("Error: cannot kill app - package: ".concat(nextArgRequired));
                            return -1;
                        }
                    }
                }
                this.mInterface.killBackgroundProcesses(nextArgRequired, i);
                return 0;
            }
            if (!nextOption.equals("--user")) {
                getErrPrintWriter().println("Error: Unknown option: ".concat(nextOption));
                return -1;
            }
            i = UserHandle.parseUserArg(getNextArgRequired());
        }
    }

    public final int runMakeIdle() {
        int i = -1;
        while (true) {
            String nextOption = getNextOption();
            if (nextOption == null) {
                this.mInterface.makePackageIdle(getNextArgRequired(), i);
                return 0;
            }
            if (!nextOption.equals("--user")) {
                getErrPrintWriter().println("Error: Unknown option: ".concat(nextOption));
                return -1;
            }
            i = UserHandle.parseUserArg(getNextArgRequired());
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public final int runMemoryFactor(PrintWriter printWriter) {
        boolean z;
        boolean z2;
        int i;
        this.mInternal.enforceCallingPermission("android.permission.WRITE_SECURE_SETTINGS", "runMemoryFactor()");
        String nextArgRequired = getNextArgRequired();
        nextArgRequired.getClass();
        int i2 = 3;
        switch (nextArgRequired.hashCode()) {
            case 113762:
                if (nextArgRequired.equals("set")) {
                    z = false;
                    break;
                }
                z = -1;
                break;
            case 3529469:
                if (nextArgRequired.equals(KnoxCustomManagerService.SHOW)) {
                    z = true;
                    break;
                }
                z = -1;
                break;
            case 108404047:
                if (nextArgRequired.equals("reset")) {
                    z = 2;
                    break;
                }
                z = -1;
                break;
            default:
                z = -1;
                break;
        }
        switch (z) {
            case false:
                String nextArgRequired2 = getNextArgRequired();
                nextArgRequired2.getClass();
                switch (nextArgRequired2.hashCode()) {
                    case -1986416409:
                        if (nextArgRequired2.equals("NORMAL")) {
                            z2 = false;
                            break;
                        }
                        z2 = -1;
                        break;
                    case -1560189025:
                        if (nextArgRequired2.equals("CRITICAL")) {
                            z2 = true;
                            break;
                        }
                        z2 = -1;
                        break;
                    case 75572:
                        if (nextArgRequired2.equals("LOW")) {
                            z2 = 2;
                            break;
                        }
                        z2 = -1;
                        break;
                    case 163769603:
                        if (nextArgRequired2.equals("MODERATE")) {
                            z2 = 3;
                            break;
                        }
                        z2 = -1;
                        break;
                    default:
                        z2 = -1;
                        break;
                }
                switch (z2) {
                    case false:
                        i2 = 0;
                        break;
                    case true:
                        break;
                    case true:
                        i2 = 2;
                        break;
                    case true:
                        i2 = 1;
                        break;
                    default:
                        try {
                            i = Integer.parseInt(nextArgRequired2);
                        } catch (NumberFormatException unused) {
                            i = -1;
                        }
                        if (i >= 0 && i <= 3) {
                            i2 = i;
                            break;
                        } else {
                            getErrPrintWriter().println("Error: Unknown level option: ".concat(nextArgRequired2));
                            return -1;
                        }
                }
                this.mInternal.setMemFactorOverride(i2);
                return 0;
            case true:
                int memoryTrimLevel = this.mInternal.getMemoryTrimLevel();
                if (memoryTrimLevel == -1) {
                    printWriter.println("<UNKNOWN>");
                } else if (memoryTrimLevel == 0) {
                    printWriter.println("NORMAL");
                } else if (memoryTrimLevel == 1) {
                    printWriter.println("MODERATE");
                } else if (memoryTrimLevel == 2) {
                    printWriter.println("LOW");
                } else if (memoryTrimLevel == 3) {
                    printWriter.println("CRITICAL");
                }
                printWriter.flush();
                return 0;
            case true:
                this.mInternal.setMemFactorOverride(-1);
                return 0;
            default:
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(getErrPrintWriter(), "Error: unknown command '", nextArgRequired, "'");
                return -1;
        }
    }

    public final int runMonitor(PrintWriter printWriter) {
        boolean z;
        String str = null;
        String str2 = null;
        boolean z2 = false;
        boolean z3 = false;
        boolean z4 = false;
        boolean z5 = false;
        while (true) {
            String nextOption = getNextOption();
            if (nextOption == null) {
                if (z4 && z5) {
                    getErrPrintWriter().println("Error: -k and -c options can't be used together.");
                    return -1;
                }
                MyActivityController myActivityController = new MyActivityController(this.mInterface, printWriter, getRawInputStream(), str, z2, z3, str2, z4, z5);
                try {
                    try {
                        myActivityController.printMessageForState();
                        myActivityController.mPw.flush();
                        myActivityController.mInterface.setActivityController(myActivityController, myActivityController.mMonkey);
                        myActivityController.mState = 0;
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(myActivityController.mInput));
                        while (true) {
                            String readLine = bufferedReader.readLine();
                            if (readLine == null) {
                                break;
                            }
                            if (readLine.length() > 0) {
                                if ("q".equals(readLine) || "quit".equals(readLine)) {
                                    break;
                                }
                                int i = myActivityController.mState;
                                if (i == 1) {
                                    if (!"c".equals(readLine) && !"continue".equals(readLine)) {
                                        if (!"k".equals(readLine) && !"kill".equals(readLine)) {
                                            myActivityController.mPw.println("Invalid command: " + readLine);
                                        }
                                        myActivityController.resumeController(1);
                                    }
                                    myActivityController.resumeController(0);
                                } else if (i == 3) {
                                    if (!"c".equals(readLine) && !"continue".equals(readLine)) {
                                        if (!"k".equals(readLine) && !"kill".equals(readLine)) {
                                            if (!"w".equals(readLine) && !"wait".equals(readLine)) {
                                                myActivityController.mPw.println("Invalid command: " + readLine);
                                            }
                                            myActivityController.resumeController(2);
                                        }
                                        myActivityController.resumeController(1);
                                    }
                                    myActivityController.resumeController(0);
                                } else if (i == 2) {
                                    if (!"c".equals(readLine) && !"continue".equals(readLine)) {
                                        if (!"k".equals(readLine) && !"kill".equals(readLine)) {
                                            myActivityController.mPw.println("Invalid command: " + readLine);
                                        }
                                        myActivityController.resumeController(1);
                                    }
                                    myActivityController.resumeController(0);
                                } else {
                                    myActivityController.mPw.println("Invalid command: " + readLine);
                                }
                                z = true;
                            } else {
                                z = false;
                            }
                            synchronized (myActivityController) {
                                if (z) {
                                    try {
                                        myActivityController.mPw.println("");
                                    } finally {
                                    }
                                }
                                myActivityController.printMessageForState();
                                myActivityController.mPw.flush();
                            }
                        }
                        myActivityController.resumeController(0);
                    } catch (IOException e) {
                        e.printStackTrace(myActivityController.mPw);
                        myActivityController.mPw.flush();
                    }
                    myActivityController.mInterface.setActivityController((IActivityController) null, myActivityController.mMonkey);
                    return 0;
                } catch (Throwable th) {
                    myActivityController.mInterface.setActivityController((IActivityController) null, myActivityController.mMonkey);
                    throw th;
                }
            }
            if (nextOption.equals("--gdb")) {
                str = getNextArgRequired();
            } else if (nextOption.equals("-p")) {
                str2 = getNextArgRequired();
            } else if (nextOption.equals("-m")) {
                z2 = true;
            } else if (nextOption.equals("-s")) {
                z3 = true;
            } else if (nextOption.equals("-c")) {
                z4 = true;
            } else {
                if (!nextOption.equals("-k")) {
                    getErrPrintWriter().println("Error: Unknown option: ".concat(nextOption));
                    return -1;
                }
                z5 = true;
            }
        }
    }

    public final void runPackageImportance(PrintWriter printWriter) {
        printWriter.println(ActivityManager.RunningAppProcessInfo.procStateToImportance(this.mInterface.getPackageProcessState(getNextArgRequired(), "com.android.shell")));
    }

    /* JADX WARN: Removed duplicated region for block: B:43:0x00cc  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00d2  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int runProfile() {
        /*
            Method dump skipped, instructions count: 281
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.am.ActivityManagerShellCommand.runProfile():int");
    }

    public final int runRestart(PrintWriter printWriter) {
        String nextOption = getNextOption();
        if (nextOption != null) {
            getErrPrintWriter().println("Error: Unknown option: ".concat(nextOption));
            return -1;
        }
        printWriter.println("Restart the system...");
        printWriter.flush();
        this.mInterface.restart();
        return 0;
    }

    public final int runScreenCompat() {
        int i;
        String nextArgRequired = getNextArgRequired();
        if ("on".equals(nextArgRequired)) {
            i = 1;
        } else {
            if (!"off".equals(nextArgRequired)) {
                BinaryTransparencyService$$ExternalSyntheticOutline0.m50m(getErrPrintWriter(), "Error: enabled mode must be 'on' or 'off' at ", nextArgRequired);
                return -1;
            }
            i = 0;
        }
        String nextArgRequired2 = getNextArgRequired();
        do {
            try {
                this.mInterface.setPackageScreenCompatMode(nextArgRequired2, i);
            } catch (RemoteException unused) {
            }
            nextArgRequired2 = getNextArg();
        } while (nextArgRequired2 != null);
        return 0;
    }

    public final void runSendBroadcast(PrintWriter printWriter) {
        PrintWriter printWriter2 = new PrintWriter((Writer) new TeeWriter(new Writer[]{ActivityManagerDebugConfig.LOG_WRITER_INFO, printWriter}));
        try {
            Intent makeIntent = makeIntent();
            makeIntent.addFlags(4194304);
            IIntentReceiver intentReceiver = new IntentReceiver(printWriter2);
            String str = this.mReceiverPermission;
            String[] strArr = str == null ? null : new String[]{str};
            printWriter2.println("Broadcasting: " + makeIntent);
            printWriter2.flush();
            BroadcastOptions broadcastOptions = this.mBroadcastOptions;
            int broadcastIntentWithFeature = this.mInterface.broadcastIntentWithFeature(null, null, makeIntent, null, intentReceiver, 0, null, null, strArr, null, null, -1, broadcastOptions == null ? null : broadcastOptions.toBundle(), true, false, this.mUserId);
            Slogf.i("ActivityManager", VibrationParam$1$$ExternalSyntheticOutline0.m(broadcastIntentWithFeature, "Enqueued broadcast %s: "), makeIntent);
            if (broadcastIntentWithFeature != 0 || this.mAsync) {
                return;
            }
            synchronized (intentReceiver) {
                while (!intentReceiver.mFinished) {
                    try {
                        intentReceiver.wait();
                    } catch (InterruptedException e) {
                        throw new IllegalStateException(e);
                    }
                }
            }
        } catch (URISyntaxException e2) {
            throw new RuntimeException(e2.getMessage(), e2);
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public final int runSendTrimMemory() {
        char c;
        int i = 5;
        int i2 = -2;
        do {
            String nextOption = getNextOption();
            if (nextOption == null) {
                String nextArgRequired = getNextArgRequired();
                String nextArgRequired2 = getNextArgRequired();
                nextArgRequired2.getClass();
                switch (nextArgRequired2.hashCode()) {
                    case -1943119297:
                        if (nextArgRequired2.equals("RUNNING_CRITICAL")) {
                            c = 0;
                            break;
                        }
                        c = 65535;
                        break;
                    case -847101650:
                        if (nextArgRequired2.equals("BACKGROUND")) {
                            c = 1;
                            break;
                        }
                        c = 65535;
                        break;
                    case -219160669:
                        if (nextArgRequired2.equals("RUNNING_MODERATE")) {
                            c = 2;
                            break;
                        }
                        c = 65535;
                        break;
                    case 163769603:
                        if (nextArgRequired2.equals("MODERATE")) {
                            c = 3;
                            break;
                        }
                        c = 65535;
                        break;
                    case 183181625:
                        if (nextArgRequired2.equals("COMPLETE")) {
                            c = 4;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1072631956:
                        if (nextArgRequired2.equals("RUNNING_LOW")) {
                            c = 5;
                            break;
                        }
                        c = 65535;
                        break;
                    case 2130809258:
                        if (nextArgRequired2.equals("HIDDEN")) {
                            c = 6;
                            break;
                        }
                        c = 65535;
                        break;
                    default:
                        c = 65535;
                        break;
                }
                switch (c) {
                    case 0:
                        i = 15;
                        break;
                    case 1:
                        i = 40;
                        break;
                    case 2:
                        break;
                    case 3:
                        i = 60;
                        break;
                    case 4:
                        i = 80;
                        break;
                    case 5:
                        i = 10;
                        break;
                    case 6:
                        i = 20;
                        break;
                    default:
                        try {
                            i = Integer.parseInt(nextArgRequired2);
                            break;
                        } catch (NumberFormatException unused) {
                            getErrPrintWriter().println("Error: Unknown level option: ".concat(nextArgRequired2));
                            return -1;
                        }
                }
                this.mInterface.setProcessMemoryTrimLevel(nextArgRequired, i2, i);
                return 0;
            }
            if (!nextOption.equals("--user")) {
                getErrPrintWriter().println("Error: Unknown option: ".concat(nextOption));
                return -1;
            }
            i2 = UserHandle.parseUserArg(getNextArgRequired());
        } while (i2 != -1);
        getErrPrintWriter().println("Error: Can't use user 'all'");
        return -1;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public final int runServiceRestartBackoff(PrintWriter printWriter) {
        char c;
        boolean z;
        this.mInternal.enforceCallingPermission("android.permission.SET_PROCESS_LIMIT", "runServiceRestartBackoff()");
        String nextArgRequired = getNextArgRequired();
        nextArgRequired.getClass();
        switch (nextArgRequired.hashCode()) {
            case -1298848381:
                if (nextArgRequired.equals("enable")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 3529469:
                if (nextArgRequired.equals(KnoxCustomManagerService.SHOW)) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 1671308008:
                if (nextArgRequired.equals("disable")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                ActivityManagerService activityManagerService = this.mInternal;
                String nextArgRequired2 = getNextArgRequired();
                activityManagerService.getClass();
                ActivityManagerService.boostPriorityForLockedSection();
                synchronized (activityManagerService) {
                    try {
                        activityManagerService.mServices.setServiceRestartBackoffEnabledLocked(nextArgRequired2, true);
                    } finally {
                        ActivityManagerService.resetPriorityAfterLockedSection();
                    }
                }
                ActivityManagerService.resetPriorityAfterLockedSection();
                return 0;
            case 1:
                ActivityManagerService activityManagerService2 = this.mInternal;
                String nextArgRequired3 = getNextArgRequired();
                activityManagerService2.getClass();
                ActivityManagerService.boostPriorityForLockedSection();
                synchronized (activityManagerService2) {
                    try {
                        z = !activityManagerService2.mServices.mRestartBackoffDisabledPackages.contains(nextArgRequired3);
                    } finally {
                    }
                }
                ActivityManagerService.resetPriorityAfterLockedSection();
                printWriter.println(z ? "enabled" : "disabled");
                return 0;
            case 2:
                ActivityManagerService activityManagerService3 = this.mInternal;
                String nextArgRequired4 = getNextArgRequired();
                activityManagerService3.getClass();
                ActivityManagerService.boostPriorityForLockedSection();
                synchronized (activityManagerService3) {
                    try {
                        activityManagerService3.mServices.setServiceRestartBackoffEnabledLocked(nextArgRequired4, false);
                    } finally {
                    }
                }
                ActivityManagerService.resetPriorityAfterLockedSection();
                return 0;
            default:
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(getErrPrintWriter(), "Error: unknown command '", nextArgRequired, "'");
                return -1;
        }
    }

    public final void runSetAgentApp() {
        this.mInterface.setAgentApp(getNextArgRequired(), getNextArg());
    }

    public final int runSetBgAbusiveUids() {
        BaseAppStateTracker baseAppStateTracker;
        String nextArg = getNextArg();
        Iterator it = this.mInternal.mAppRestrictionController.mAppStateTrackers.iterator();
        while (true) {
            if (!it.hasNext()) {
                baseAppStateTracker = null;
                break;
            }
            baseAppStateTracker = (BaseAppStateTracker) it.next();
            if (AppBatteryTracker.class.isAssignableFrom(baseAppStateTracker.getClass())) {
                break;
            }
        }
        AppBatteryTracker appBatteryTracker = (AppBatteryTracker) baseAppStateTracker;
        if (appBatteryTracker == null) {
            getErrPrintWriter().println("Unable to get bg battery tracker");
            return -1;
        }
        if (nextArg == null) {
            appBatteryTracker.mDebugUidPercentages.clear();
            appBatteryTracker.scheduleBgBatteryUsageStatsCheck();
            return 0;
        }
        String[] split = nextArg.split(",");
        int length = split.length;
        int[] iArr = new int[length];
        double[][] dArr = new double[split.length][];
        for (int i = 0; i < split.length; i++) {
            try {
                String[] split2 = split[i].split("=");
                if (split2.length != 2) {
                    getErrPrintWriter().println("Malformed input");
                    return -1;
                }
                iArr[i] = Integer.parseInt(split2[0]);
                String[] split3 = split2[1].split(":");
                if (split3.length != 5) {
                    getErrPrintWriter().println("Malformed input");
                    return -1;
                }
                dArr[i] = new double[split3.length];
                for (int i2 = 0; i2 < split3.length; i2++) {
                    dArr[i][i2] = Double.parseDouble(split3[i2]);
                }
            } catch (NumberFormatException unused) {
                getErrPrintWriter().println("Malformed input");
                return -1;
            }
        }
        appBatteryTracker.mDebugUidPercentages.clear();
        for (int i3 = 0; i3 < length; i3++) {
            SparseArray sparseArray = appBatteryTracker.mDebugUidPercentages;
            int i4 = iArr[i3];
            AppBatteryTracker.BatteryUsage batteryUsage = new AppBatteryTracker.BatteryUsage();
            batteryUsage.mPercentage = dArr[i3];
            sparseArray.put(i4, new AppBatteryTracker.ImmutableBatteryUsage(batteryUsage));
        }
        appBatteryTracker.scheduleBgBatteryUsageStatsCheck();
        return 0;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public final int runSetBgRestrictionLevel(PrintWriter printWriter) {
        char c;
        int i;
        int i2;
        int i3 = -2;
        while (true) {
            String nextOption = getNextOption();
            if (nextOption == null) {
                String nextArgRequired = getNextArgRequired();
                String lowerCase = getNextArgRequired().toLowerCase();
                lowerCase.getClass();
                switch (lowerCase.hashCode()) {
                    case -1790443964:
                        if (lowerCase.equals("user_launch_only")) {
                            c = 0;
                            break;
                        }
                        c = 65535;
                        break;
                    case -1502662066:
                        if (lowerCase.equals("restricted_bucket")) {
                            c = 1;
                            break;
                        }
                        c = 65535;
                        break;
                    case -1349088399:
                        if (lowerCase.equals("custom")) {
                            c = 2;
                            break;
                        }
                        c = 65535;
                        break;
                    case -1078417287:
                        if (lowerCase.equals("force_stopped")) {
                            c = 3;
                            break;
                        }
                        c = 65535;
                        break;
                    case -775446516:
                        if (lowerCase.equals("background_restricted")) {
                            c = 4;
                            break;
                        }
                        c = 65535;
                        break;
                    case 824339380:
                        if (lowerCase.equals("unrestricted")) {
                            c = 5;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1351638995:
                        if (lowerCase.equals("adaptive_bucket")) {
                            c = 6;
                            break;
                        }
                        c = 65535;
                        break;
                    case 2052103358:
                        if (lowerCase.equals("exempted")) {
                            c = 7;
                            break;
                        }
                        c = 65535;
                        break;
                    default:
                        c = 65535;
                        break;
                }
                switch (c) {
                    case 0:
                        i = 70;
                        i2 = i;
                        break;
                    case 1:
                        i = 40;
                        i2 = i;
                        break;
                    case 2:
                        i = 90;
                        i2 = i;
                        break;
                    case 3:
                        i = 60;
                        i2 = i;
                        break;
                    case 4:
                        i = 50;
                        i2 = i;
                        break;
                    case 5:
                        i = 10;
                        i2 = i;
                        break;
                    case 6:
                        i = 30;
                        i2 = i;
                        break;
                    case 7:
                        i = 20;
                        i2 = i;
                        break;
                    default:
                        i2 = 0;
                        break;
                }
                if (i2 == 0) {
                    printWriter.println("Error: invalid restriction level");
                    return -1;
                }
                try {
                    int packageUidAsUser = this.mInternal.mContext.getPackageManager().getPackageUidAsUser(nextArgRequired, PackageManager.PackageInfoFlags.of(4194304L), i3);
                    ActivityManagerService activityManagerService = this.mInternal;
                    activityManagerService.getClass();
                    int callingUid = Binder.getCallingUid();
                    if (callingUid != 1000 && callingUid != 0 && callingUid != 2000) {
                        throw new SecurityException("No permission to change app restriction level");
                    }
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        activityManagerService.mAppRestrictionController.applyRestrictionLevel(nextArgRequired, packageUidAsUser, i2, null, activityManagerService.mUsageStatsService.getAppStandbyBucket(i3, nextArgRequired, SystemClock.elapsedRealtime()), true, 1024, 0);
                        return 0;
                    } finally {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } catch (PackageManager.NameNotFoundException unused) {
                    printWriter.println(AccountManagerService$$ExternalSyntheticOutline0.m(i3, "Error: userId:", " package:", nextArgRequired, " is not found"));
                    return -1;
                }
            }
            if (!nextOption.equals("--user")) {
                getErrPrintWriter().println("Error: Unknown option: ".concat(nextOption));
                return -1;
            }
            i3 = UserHandle.parseUserArg(getNextArgRequired());
        }
    }

    public final int runSetDebugApp() {
        boolean z = false;
        boolean z2 = false;
        while (true) {
            String nextOption = getNextOption();
            if (nextOption == null) {
                this.mInterface.setDebugApp(getNextArgRequired(), z, z2, false);
                return 0;
            }
            if (nextOption.equals("-w")) {
                z = true;
            } else {
                if (!nextOption.equals("--persistent")) {
                    getErrPrintWriter().println("Error: Unknown option: ".concat(nextOption));
                    return -1;
                }
                z2 = true;
            }
        }
    }

    public final int runSetDeterministicUidIdle() {
        while (true) {
            String nextOption = getNextOption();
            if (nextOption == null) {
                this.mInterface.setDeterministicUidIdle(Boolean.parseBoolean(getNextArgRequired()));
                return 0;
            }
            if (!nextOption.equals("--user")) {
                getErrPrintWriter().println("Error: Unknown option: ".concat(nextOption));
                return -1;
            }
            UserHandle.parseUserArg(getNextArgRequired());
        }
    }

    public final int runSetForegroundServiceDelegate(PrintWriter printWriter) {
        boolean z;
        boolean z2;
        ActivityManagerService.PidMap pidMap;
        int i;
        int i2 = -2;
        while (true) {
            String nextOption = getNextOption();
            if (nextOption == null) {
                String nextArgRequired = getNextArgRequired();
                String nextArgRequired2 = getNextArgRequired();
                if ("start".equals(nextArgRequired2)) {
                    z = true;
                } else {
                    if (!"stop".equals(nextArgRequired2)) {
                        printWriter.println("Error: action is either start or stop");
                        return -1;
                    }
                    z = false;
                }
                try {
                    int packageUidAsUser = this.mInternal.mContext.getPackageManager().getPackageUidAsUser(nextArgRequired, PackageManager.PackageInfoFlags.of(4194304L), i2);
                    ActivityManagerService activityManagerService = this.mInternal;
                    activityManagerService.getClass();
                    int callingUid = Binder.getCallingUid();
                    if (callingUid != 1000 && callingUid != 0 && callingUid != 2000) {
                        throw new SecurityException("No permission to start/stop foreground service delegate");
                    }
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        ActivityManagerService.boostPriorityForLockedSection();
                        synchronized (activityManagerService) {
                            try {
                                ArrayList arrayList = new ArrayList();
                                ActivityManagerService.PidMap pidMap2 = activityManagerService.mPidsSelfLocked;
                                synchronized (pidMap2) {
                                    z2 = false;
                                    int i3 = 0;
                                    while (i3 < ((SparseArray) activityManagerService.mPidsSelfLocked.mPidMap).size()) {
                                        try {
                                            ProcessRecord valueAt = activityManagerService.mPidsSelfLocked.valueAt(i3);
                                            IApplicationThread iApplicationThread = valueAt.mThread;
                                            if (valueAt.uid != packageUidAsUser || iApplicationThread == null) {
                                                i = i3;
                                                pidMap = pidMap2;
                                            } else {
                                                i = i3;
                                                pidMap = pidMap2;
                                                try {
                                                    arrayList.add(new ForegroundServiceDelegationOptions(((SparseArray) activityManagerService.mPidsSelfLocked.mPidMap).keyAt(i3), packageUidAsUser, nextArgRequired, (IApplicationThread) null, false, "FgsDelegate", 0, 12));
                                                    z2 = true;
                                                } catch (Throwable th) {
                                                    th = th;
                                                    throw th;
                                                }
                                            }
                                            i3 = i + 1;
                                            pidMap2 = pidMap;
                                        } catch (Throwable th2) {
                                            th = th2;
                                            pidMap = pidMap2;
                                        }
                                    }
                                    for (int size = arrayList.size() - 1; size >= 0; size--) {
                                        ForegroundServiceDelegationOptions foregroundServiceDelegationOptions = (ForegroundServiceDelegationOptions) arrayList.get(size);
                                        if (z) {
                                            activityManagerService.mInternal.startForegroundServiceDelegate(foregroundServiceDelegationOptions, (ServiceConnection) null);
                                        } else {
                                            activityManagerService.mInternal.stopForegroundServiceDelegate(foregroundServiceDelegationOptions);
                                        }
                                    }
                                }
                            } finally {
                                ActivityManagerService.resetPriorityAfterLockedSection();
                            }
                        }
                        ActivityManagerService.resetPriorityAfterLockedSection();
                        if (!z2) {
                            Slog.e("ActivityManager", "setForegroundServiceDelegate can not find process for packageName:" + nextArgRequired + " uid:" + packageUidAsUser);
                        }
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        return 0;
                    } catch (Throwable th3) {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        throw th3;
                    }
                } catch (PackageManager.NameNotFoundException unused) {
                    printWriter.println(AccountManagerService$$ExternalSyntheticOutline0.m(i2, "Error: userId:", " package:", nextArgRequired, " is not found"));
                    return -1;
                }
            }
            if (!nextOption.equals("--user")) {
                getErrPrintWriter().println("Error: Unknown option: ".concat(nextOption));
                return -1;
            }
            i2 = UserHandle.parseUserArg(getNextArgRequired());
        }
    }

    public final int runSetInactive() {
        int i = -2;
        while (true) {
            String nextOption = getNextOption();
            if (nextOption == null) {
                IUsageStatsManager.Stub.asInterface(ServiceManager.getService("usagestats")).setAppInactive(getNextArgRequired(), Boolean.parseBoolean(getNextArgRequired()), i);
                return 0;
            }
            if (!nextOption.equals("--user")) {
                getErrPrintWriter().println("Error: Unknown option: ".concat(nextOption));
                return -1;
            }
            i = UserHandle.parseUserArg(getNextArgRequired());
        }
    }

    public final int runSetStandbyBucket() {
        int i = -2;
        while (true) {
            String nextOption = getNextOption();
            if (nextOption == null) {
                String nextArgRequired = getNextArgRequired();
                String nextArgRequired2 = getNextArgRequired();
                int bucketNameToBucketValue = bucketNameToBucketValue(nextArgRequired2);
                if (bucketNameToBucketValue < 0) {
                    return -1;
                }
                boolean z = peekNextArg() != null;
                IUsageStatsManager asInterface = IUsageStatsManager.Stub.asInterface(ServiceManager.getService("usagestats"));
                if (z) {
                    ArrayList arrayList = new ArrayList();
                    arrayList.add(new AppStandbyInfo(nextArgRequired, bucketNameToBucketValue));
                    while (true) {
                        String nextArg = getNextArg();
                        if (nextArg == null) {
                            break;
                        }
                        int bucketNameToBucketValue2 = bucketNameToBucketValue(getNextArgRequired());
                        if (bucketNameToBucketValue2 >= 0) {
                            arrayList.add(new AppStandbyInfo(nextArg, bucketNameToBucketValue2));
                        }
                    }
                    asInterface.setAppStandbyBuckets(new ParceledListSlice(arrayList), i);
                } else {
                    asInterface.setAppStandbyBucket(nextArgRequired, bucketNameToBucketValue(nextArgRequired2), i);
                }
                return 0;
            }
            if (!nextOption.equals("--user")) {
                getErrPrintWriter().println("Error: Unknown option: ".concat(nextOption));
                return -1;
            }
            i = UserHandle.parseUserArg(getNextArgRequired());
        }
    }

    public final void runSetStopUserOnSwitch(PrintWriter printWriter) {
        this.mInternal.enforceCallingPermission("android.permission.INTERACT_ACROSS_USERS_FULL", "setStopUserOnSwitch()");
        String nextArg = getNextArg();
        if (nextArg == null) {
            Slogf.i("ActivityManager", "setStopUserOnSwitch(): resetting to default value");
            this.mInternal.setStopUserOnSwitch(-1);
            printWriter.println("Reset to default value");
        } else {
            boolean parseBoolean = Boolean.parseBoolean(nextArg);
            Slogf.i("ActivityManager", "runSetStopUserOnSwitch(): setting to %d (%b)", Integer.valueOf(parseBoolean ? 1 : 0), Boolean.valueOf(parseBoolean));
            this.mInternal.setStopUserOnSwitch(parseBoolean ? 1 : 0);
            printWriter.println("Set to " + parseBoolean);
        }
    }

    public final void runSetWatchHeap() {
        this.mInterface.setDumpHeapDebugLimit(getNextArgRequired(), 0, Long.parseLong(getNextArgRequired()), null);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public final int runStack(PrintWriter printWriter) {
        char c;
        boolean z = true;
        String nextArgRequired = getNextArgRequired();
        nextArgRequired.getClass();
        switch (nextArgRequired.hashCode()) {
            case -934610812:
                if (nextArgRequired.equals("remove")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 3237038:
                if (nextArgRequired.equals("info")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 3322014:
                if (nextArgRequired.equals("list")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 1022285313:
                if (nextArgRequired.equals("move-task")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                this.mTaskInterface.removeTask(Integer.parseInt(getNextArgRequired()));
                break;
            case 1:
                printWriter.println(this.mTaskInterface.getRootTaskInfo(Integer.parseInt(getNextArgRequired()), Integer.parseInt(getNextArgRequired())));
                break;
            case 2:
                Iterator it = ((ArrayList) this.mTaskInterface.getAllRootTaskInfos()).iterator();
                while (it.hasNext()) {
                    printWriter.println((ActivityTaskManager.RootTaskInfo) it.next());
                }
                break;
            case 3:
                int parseInt = Integer.parseInt(getNextArgRequired());
                int parseInt2 = Integer.parseInt(getNextArgRequired());
                String nextArgRequired2 = getNextArgRequired();
                if (!"true".equals(nextArgRequired2)) {
                    if (!"false".equals(nextArgRequired2)) {
                        BinaryTransparencyService$$ExternalSyntheticOutline0.m50m(getErrPrintWriter(), "Error: bad toTop arg: ", nextArgRequired2);
                        break;
                    } else {
                        z = false;
                    }
                }
                this.mTaskInterface.moveTaskToRootTask(parseInt, parseInt2, z);
                break;
            default:
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(getErrPrintWriter(), "Error: unknown command '", nextArgRequired, "'");
                break;
        }
        return -1;
    }

    /* JADX WARN: Code restructure failed: missing block: B:31:0x00aa, code lost:
    
        getErrPrintWriter().println("Error: Intent does not match any activities: " + r15);
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x00bf, code lost:
    
        return r13;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r13v0 */
    /* JADX WARN: Type inference failed for: r13v1, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r13v5 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int runStartActivity(java.io.PrintWriter r29) {
        /*
            Method dump skipped, instructions count: 928
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.am.ActivityManagerShellCommand.runStartActivity(java.io.PrintWriter):int");
    }

    /* JADX WARN: Code restructure failed: missing block: B:40:0x00f8, code lost:
    
        getErrPrintWriter().println("Error: Intent does not match any activities: " + r15);
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x010d, code lost:
    
        return r13;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:101:0x021c  */
    /* JADX WARN: Removed duplicated region for block: B:106:0x0229  */
    /* JADX WARN: Removed duplicated region for block: B:111:0x0236  */
    /* JADX WARN: Removed duplicated region for block: B:116:0x0243  */
    /* JADX WARN: Removed duplicated region for block: B:122:0x02b2  */
    /* JADX WARN: Removed duplicated region for block: B:133:0x03fa  */
    /* JADX WARN: Removed duplicated region for block: B:136:0x0404 A[LOOP:0: B:19:0x006e->B:136:0x0404, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:137:0x0403 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:141:0x02e9  */
    /* JADX WARN: Removed duplicated region for block: B:188:0x041d A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:193:0x02b4  */
    /* JADX WARN: Removed duplicated region for block: B:194:0x027f  */
    /* JADX WARN: Removed duplicated region for block: B:199:0x01a5  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x019b  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x01ab  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x01ba  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x01c9  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x01d9  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x01e6  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x01f7  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x0206  */
    /* JADX WARN: Type inference failed for: r13v0 */
    /* JADX WARN: Type inference failed for: r13v1, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r13v5 */
    /* JADX WARN: Type inference failed for: r23v1 */
    /* JADX WARN: Type inference failed for: r23v2, types: [int] */
    /* JADX WARN: Type inference failed for: r23v4 */
    /* JADX WARN: Type inference failed for: r2v109 */
    /* JADX WARN: Type inference failed for: r2v110 */
    /* JADX WARN: Type inference failed for: r2v122 */
    /* JADX WARN: Type inference failed for: r2v123 */
    /* JADX WARN: Type inference failed for: r2v127 */
    /* JADX WARN: Type inference failed for: r2v128 */
    /* JADX WARN: Type inference failed for: r2v131 */
    /* JADX WARN: Type inference failed for: r2v132 */
    /* JADX WARN: Type inference failed for: r2v48, types: [android.app.ActivityOptions] */
    /* JADX WARN: Type inference failed for: r2v50, types: [android.app.ActivityOptions] */
    /* JADX WARN: Type inference failed for: r2v52, types: [android.app.ActivityOptions] */
    /* JADX WARN: Type inference failed for: r2v58, types: [android.app.ActivityOptions] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int runStartActivityForCarlife(java.io.PrintWriter r30) {
        /*
            Method dump skipped, instructions count: 1084
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.am.ActivityManagerShellCommand.runStartActivityForCarlife(java.io.PrintWriter):int");
    }

    public final int runStartInfoDetailedMonitoring(PrintWriter printWriter) {
        int i = -2;
        while (true) {
            String nextOption = getNextOption();
            if (nextOption == null) {
                if (i == -2) {
                    UserInfo currentUser = this.mInterface.mUserController.getCurrentUser();
                    if (currentUser == null) {
                        return -1;
                    }
                    i = currentUser.id;
                }
                AppStartInfoTracker appStartInfoTracker = this.mInternal.mProcessList.mAppStartInfoTracker;
                String nextArg = getNextArg();
                synchronized (appStartInfoTracker.mLock) {
                    try {
                        if (appStartInfoTracker.mEnabled) {
                            appStartInfoTracker.forEachPackageLocked(new AppStartInfoTracker$$ExternalSyntheticLambda5());
                            if (TextUtils.isEmpty(nextArg)) {
                                printWriter.println("ActivityManager AppStartInfo detailed monitoring disabled");
                            } else {
                                SparseArray sparseArray = (SparseArray) appStartInfoTracker.mData.getMap().get(nextArg);
                                if (sparseArray != null) {
                                    for (int i2 = 0; i2 < sparseArray.size(); i2++) {
                                        AppStartInfoTracker.AppStartInfoContainer appStartInfoContainer = (AppStartInfoTracker.AppStartInfoContainer) sparseArray.valueAt(i2);
                                        if (UserHandle.getUserId(appStartInfoContainer.mUid) == i) {
                                            appStartInfoContainer.mMonitoringModeEnabled = true;
                                        }
                                    }
                                    printWriter.println("ActivityManager AppStartInfo detailed monitoring enabled for " + nextArg);
                                } else {
                                    printWriter.println("Package " + nextArg + " not found");
                                }
                            }
                        }
                    } finally {
                    }
                }
                return 0;
            }
            if (!nextOption.equals("--user")) {
                getErrPrintWriter().println("Error: Unknown option: ".concat(nextOption));
                return -1;
            }
            i = UserHandle.parseUserArg(getNextArgRequired());
        }
    }

    public final int runStartService(PrintWriter printWriter, boolean z) {
        PrintWriter errPrintWriter = getErrPrintWriter();
        try {
            Intent makeIntent = makeIntent();
            if (this.mUserId == -1) {
                errPrintWriter.println("Error: Can't start activity with user 'all'");
                return -1;
            }
            printWriter.println("Starting service: " + makeIntent);
            printWriter.flush();
            ComponentName startService = this.mInterface.startService(null, makeIntent, makeIntent.getType(), z, "com.android.shell", null, this.mUserId);
            if (startService == null) {
                errPrintWriter.println("Error: Not found; no service started.");
                return -1;
            }
            if (startService.getPackageName().equals("!")) {
                errPrintWriter.println("Error: Requires permission " + startService.getClassName());
                return -1;
            }
            if (startService.getPackageName().equals("!!")) {
                errPrintWriter.println("Error: " + startService.getClassName());
                return -1;
            }
            if (!startService.getPackageName().equals("?")) {
                return 0;
            }
            errPrintWriter.println("Error: " + startService.getClassName());
            return -1;
        } catch (URISyntaxException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public final int runStartUser(PrintWriter printWriter) {
        boolean startUserInBackgroundVisibleOnDisplay;
        boolean z = false;
        int i = -1;
        while (true) {
            String nextOption = getNextOption();
            if (nextOption == null) {
                int parseInt = Integer.parseInt(getNextArgRequired());
                ProgressWaiter progressWaiter = z ? new ProgressWaiter(parseInt) : null;
                UserManagerInternal userManagerInternal = (UserManagerInternal) LocalServices.getService(UserManagerInternal.class);
                ActivityManagerInternal activityManagerInternal = (ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class);
                int profileParentId = userManagerInternal.getProfileParentId(parseInt);
                int currentUserId = activityManagerInternal.getCurrentUserId();
                boolean z2 = profileParentId != parseInt;
                boolean z3 = z2 && profileParentId == currentUserId;
                Slogf.d("ActivityManager", "runStartUser(): userId=%d, parentUserId=%d, currentUserId=%d, isProfile=%b, isVisibleProfile=%b, display=%d, waiter=%s", Integer.valueOf(parseInt), Integer.valueOf(profileParentId), Integer.valueOf(currentUserId), Boolean.valueOf(z2), Boolean.valueOf(z3), Integer.valueOf(i), progressWaiter);
                Trace.traceBegin(64L, "shell_runStartUser" + parseInt);
                String str = "";
                try {
                    if (z3) {
                        Slogf.d("ActivityManager", "calling startProfileWithListener(%d, %s)", Integer.valueOf(parseInt), progressWaiter);
                        startUserInBackgroundVisibleOnDisplay = this.mInterface.startProfileWithListener(parseInt, progressWaiter);
                    } else if (i == -1) {
                        Slogf.d("ActivityManager", "calling startUserInBackgroundWithListener(%d)", Integer.valueOf(parseInt));
                        startUserInBackgroundVisibleOnDisplay = this.mInterface.startUserInBackgroundWithListener(parseInt, progressWaiter);
                    } else {
                        if (!UserManager.isVisibleBackgroundUsersEnabled()) {
                            printWriter.println("Not supported");
                            Trace.traceEnd(64L);
                            return -1;
                        }
                        Slogf.d("ActivityManager", "calling startUserInBackgroundVisibleOnDisplay(%d, %d, %s)", Integer.valueOf(parseInt), Integer.valueOf(i), progressWaiter);
                        startUserInBackgroundVisibleOnDisplay = this.mInterface.startUserInBackgroundVisibleOnDisplay(parseInt, i, progressWaiter);
                        str = " on display " + i;
                    }
                    if (z && startUserInBackgroundVisibleOnDisplay) {
                        Slogf.d("ActivityManager", "waiting %d ms", 120000);
                        progressWaiter.getClass();
                        try {
                            startUserInBackgroundVisibleOnDisplay = progressWaiter.mFinishedLatch.await(120000L, TimeUnit.MILLISECONDS);
                        } catch (InterruptedException unused) {
                            System.err.println("Thread interrupted unexpectedly.");
                            startUserInBackgroundVisibleOnDisplay = false;
                        }
                    }
                    Trace.traceEnd(64L);
                    if (startUserInBackgroundVisibleOnDisplay) {
                        BinaryTransparencyService$$ExternalSyntheticOutline0.m50m(printWriter, "Success: user started", str);
                    } else {
                        BinaryTransparencyService$$ExternalSyntheticOutline0.m50m(getErrPrintWriter(), "Error: could not start user", str);
                    }
                    return 0;
                } catch (Throwable th) {
                    Trace.traceEnd(64L);
                    throw th;
                }
            }
            if (nextOption.equals("--display")) {
                i = Integer.parseInt(getNextArgRequired());
                if (i < 0) {
                    throw new IllegalArgumentException("--display must be a non-negative integer");
                }
            } else {
                if (!nextOption.equals("-w")) {
                    getErrPrintWriter().println("Error: unknown option: ".concat(nextOption));
                    return -1;
                }
                z = true;
            }
        }
    }

    public final int runStopApp() {
        int i = 0;
        while (true) {
            String nextOption = getNextOption();
            if (nextOption == null) {
                this.mInterface.stopAppForUser(getNextArgRequired(), i);
                return 0;
            }
            if (!nextOption.equals("--user")) {
                getErrPrintWriter().println("Error: Unknown option: ".concat(nextOption));
                return -1;
            }
            i = UserHandle.parseUserArg(getNextArgRequired());
        }
    }

    public final int runStopService(PrintWriter printWriter) {
        PrintWriter errPrintWriter = getErrPrintWriter();
        try {
            Intent makeIntent = makeIntent();
            if (this.mUserId == -1) {
                errPrintWriter.println("Error: Can't stop activity with user 'all'");
                return -1;
            }
            printWriter.println("Stopping service: " + makeIntent);
            printWriter.flush();
            int stopService = this.mInterface.stopService(null, makeIntent, makeIntent.getType(), this.mUserId);
            if (stopService == 0) {
                errPrintWriter.println("Service not stopped: was not running.");
                return -1;
            }
            if (stopService == 1) {
                errPrintWriter.println("Service stopped");
                return -1;
            }
            if (stopService != -1) {
                return 0;
            }
            errPrintWriter.println("Error stopping service");
            return -1;
        } catch (URISyntaxException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public final int runStopUser() {
        boolean z = false;
        boolean z2 = false;
        while (true) {
            String nextOption = getNextOption();
            if (nextOption == null) {
                int parseInt = Integer.parseInt(getNextArgRequired());
                StopUserCallback stopUserCallback = z ? new StopUserCallback(parseInt) : null;
                Slogf.d("ActivityManager", "Calling stopUser(%d, %b, %s)", Integer.valueOf(parseInt), Boolean.valueOf(z2), stopUserCallback);
                Trace.traceBegin(64L, "shell_runStopUser-" + parseInt + "-[stopUser]");
                try {
                    int stopUserExceptCertainProfiles = this.mInterface.stopUserExceptCertainProfiles(parseInt, z2, stopUserCallback);
                    if (stopUserExceptCertainProfiles == 0) {
                        if (stopUserCallback != null) {
                            stopUserCallback.waitForFinish();
                        }
                        Trace.traceEnd(64L);
                        return 0;
                    }
                    String str = "";
                    if (stopUserExceptCertainProfiles == -4) {
                        str = " (Can't stop user " + parseInt + " - one of its related users can't be stopped)";
                    } else if (stopUserExceptCertainProfiles == -3) {
                        str = " (System user cannot be stopped)";
                    } else if (stopUserExceptCertainProfiles == -2) {
                        str = " (Can't stop current user)";
                    } else if (stopUserExceptCertainProfiles == -1) {
                        str = " (Unknown user " + parseInt + ")";
                    }
                    getErrPrintWriter().println("Switch failed: " + stopUserExceptCertainProfiles + str);
                    Trace.traceEnd(64L);
                    return -1;
                } catch (Throwable th) {
                    Trace.traceEnd(64L);
                    throw th;
                }
            }
            if ("-w".equals(nextOption)) {
                z = true;
            } else {
                if (!"-f".equals(nextOption)) {
                    getErrPrintWriter().println("Error: unknown option: ".concat(nextOption));
                    return -1;
                }
                z2 = true;
            }
        }
    }

    public final int runSupportsMultiwindow(PrintWriter printWriter) {
        if (getResources(printWriter) == null) {
            return -1;
        }
        printWriter.println(ActivityTaskManager.supportsMultiWindow(this.mInternal.mContext));
        return 0;
    }

    public final int runSwitchUser(PrintWriter printWriter) {
        boolean z = false;
        while (true) {
            String nextOption = getNextOption();
            if (nextOption == null) {
                int parseInt = Integer.parseInt(getNextArgRequired());
                int userSwitchability = ((UserManager) this.mInternal.mContext.getSystemService(UserManager.class)).getUserSwitchability(UserHandle.of(parseInt));
                if (userSwitchability != 0) {
                    AccountManagerServiceShellCommand$$ExternalSyntheticOutline0.m(getErrPrintWriter(), "Error: UserSwitchabilityResult=", userSwitchability);
                    return -1;
                }
                Trace.traceBegin(64L, "shell_runSwitchUser");
                try {
                    if (z ? switchUserAndWaitForComplete(parseInt) : this.mInterface.switchUser(parseInt)) {
                        Trace.traceEnd(64L);
                        return 0;
                    }
                    printWriter.printf("Error: Failed to switch to user %d\n", Integer.valueOf(parseInt));
                    Trace.traceEnd(64L);
                    return 1;
                } catch (Throwable th) {
                    Trace.traceEnd(64L);
                    throw th;
                }
            }
            if (!"-w".equals(nextOption)) {
                getErrPrintWriter().println("Error: unknown option: ".concat(nextOption));
                return -1;
            }
            z = true;
        }
    }

    public final int runTask(PrintWriter printWriter) {
        String nextArgRequired = getNextArgRequired();
        if (nextArgRequired.equals("lock")) {
            String nextArgRequired2 = getNextArgRequired();
            if (nextArgRequired2.equals("stop")) {
                this.mTaskInterface.stopSystemLockTaskMode();
            } else {
                this.mTaskInterface.startSystemLockTaskMode(Integer.parseInt(nextArgRequired2));
            }
            ProxyManager$$ExternalSyntheticOutline0.m(printWriter, this.mTaskInterface.isInLockTaskMode() ? "" : "not ", "in lockTaskMode", new StringBuilder("Activity manager is "));
            return 0;
        }
        if (nextArgRequired.equals("resizeable")) {
            this.mTaskInterface.setTaskResizeable(Integer.parseInt(getNextArgRequired()), Integer.parseInt(getNextArgRequired()));
            return 0;
        }
        if (!nextArgRequired.equals("resize")) {
            if (!nextArgRequired.equals("focus")) {
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(getErrPrintWriter(), "Error: unknown command '", nextArgRequired, "'");
                return -1;
            }
            int parseInt = Integer.parseInt(getNextArgRequired());
            AccountManagerServiceShellCommand$$ExternalSyntheticOutline0.m(printWriter, "Setting focus to task ", parseInt);
            this.mTaskInterface.setFocusedTask(parseInt);
            return 0;
        }
        int parseInt2 = Integer.parseInt(getNextArgRequired());
        String nextArgRequired3 = getNextArgRequired();
        int parseInt3 = Integer.parseInt(nextArgRequired3);
        String nextArgRequired4 = getNextArgRequired();
        int parseInt4 = Integer.parseInt(nextArgRequired4);
        String nextArgRequired5 = getNextArgRequired();
        int parseInt5 = Integer.parseInt(nextArgRequired5);
        String nextArgRequired6 = getNextArgRequired();
        int parseInt6 = Integer.parseInt(nextArgRequired6);
        Rect rect = null;
        if (parseInt3 < 0) {
            BinaryTransparencyService$$ExternalSyntheticOutline0.m50m(getErrPrintWriter(), "Error: bad left arg: ", nextArgRequired3);
        } else if (parseInt4 < 0) {
            BinaryTransparencyService$$ExternalSyntheticOutline0.m50m(getErrPrintWriter(), "Error: bad top arg: ", nextArgRequired4);
        } else if (parseInt5 <= 0) {
            BinaryTransparencyService$$ExternalSyntheticOutline0.m50m(getErrPrintWriter(), "Error: bad right arg: ", nextArgRequired5);
        } else if (parseInt6 <= 0) {
            BinaryTransparencyService$$ExternalSyntheticOutline0.m50m(getErrPrintWriter(), "Error: bad bottom arg: ", nextArgRequired6);
        } else {
            rect = new Rect(parseInt3, parseInt4, parseInt5, parseInt6);
        }
        if (rect == null) {
            getErrPrintWriter().println("Error: invalid input bounds");
            return -1;
        }
        this.mTaskInterface.resizeTask(parseInt2, rect, 0);
        try {
            Thread.sleep(0);
            return 0;
        } catch (InterruptedException unused) {
            return 0;
        }
    }

    public final void runToUri(int i, PrintWriter printWriter) {
        try {
            printWriter.println(makeIntent().toUri(i));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public final int runTraceIpc(PrintWriter printWriter) {
        String nextArgRequired = getNextArgRequired();
        if (nextArgRequired.equals("start")) {
            printWriter.println("Starting IPC tracing.");
            printWriter.flush();
            this.mInterface.startBinderTracking();
            return 0;
        }
        if (!nextArgRequired.equals("stop")) {
            BinaryTransparencyService$$ExternalSyntheticOutline0.m(getErrPrintWriter(), "Error: unknown trace ipc command '", nextArgRequired, "'");
            return -1;
        }
        PrintWriter errPrintWriter = getErrPrintWriter();
        String str = null;
        while (true) {
            String nextOption = getNextOption();
            if (nextOption != null) {
                if (!nextOption.equals("--dump-file")) {
                    errPrintWriter.println("Error: Unknown option: ".concat(nextOption));
                    break;
                }
                str = getNextArgRequired();
            } else if (str == null) {
                errPrintWriter.println("Error: Specify filename to dump logs to.");
            } else {
                ParcelFileDescriptor openFileForSystem = openFileForSystem(str, "w");
                if (openFileForSystem != null) {
                    this.mInterface.stopBinderTrackingAndDump(openFileForSystem);
                    printWriter.println("Stopped IPC tracing. Dumping logs to: ".concat(str));
                    return 0;
                }
            }
        }
        return -1;
    }

    public final void runTrackAssociations(PrintWriter printWriter) {
        this.mInternal.enforceCallingPermission("android.permission.SET_ACTIVITY_WATCHER", "runTrackAssociations()");
        ActivityManagerService activityManagerService = this.mInternal;
        ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                ActivityManagerService activityManagerService2 = this.mInternal;
                if (activityManagerService2.mTrackingAssociations) {
                    printWriter.println("Association tracking already enabled.");
                } else {
                    activityManagerService2.mTrackingAssociations = true;
                    printWriter.println("Association tracking started.");
                }
            } catch (Throwable th) {
                ActivityManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        ActivityManagerService.resetPriorityAfterLockedSection();
    }

    public final int runUnlockUser(PrintWriter printWriter) {
        int parseInt = Integer.parseInt(getNextArgRequired());
        String nextArg = getNextArg();
        if (!TextUtils.isEmpty(nextArg) && !"!".equals(nextArg)) {
            getErrPrintWriter().println("Error: token parameter not supported");
            return -1;
        }
        String nextArg2 = getNextArg();
        if (!TextUtils.isEmpty(nextArg2) && !"!".equals(nextArg2)) {
            getErrPrintWriter().println("Error: secret parameter not supported");
            return -1;
        }
        if (this.mInterface.unlockUser2(parseInt, null)) {
            printWriter.println("Success: user unlocked");
            return 0;
        }
        getErrPrintWriter().println("Error: could not unlock user");
        return 0;
    }

    public final void runUntrackAssociations(PrintWriter printWriter) {
        this.mInternal.enforceCallingPermission("android.permission.SET_ACTIVITY_WATCHER", "runUntrackAssociations()");
        ActivityManagerService activityManagerService = this.mInternal;
        ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                ActivityManagerService activityManagerService2 = this.mInternal;
                if (activityManagerService2.mTrackingAssociations) {
                    activityManagerService2.mTrackingAssociations = false;
                    activityManagerService2.mAssociations.clear();
                    printWriter.println("Association tracking stopped.");
                } else {
                    printWriter.println("Association tracking not running.");
                }
            } catch (Throwable th) {
                ActivityManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        ActivityManagerService.resetPriorityAfterLockedSection();
    }

    public final void runUpdateApplicationInfo(PrintWriter printWriter) {
        int parseUserArg = UserHandle.parseUserArg(getNextArgRequired());
        ArrayList arrayList = new ArrayList();
        arrayList.add(getNextArgRequired());
        while (true) {
            String nextArg = getNextArg();
            if (nextArg == null) {
                this.mInternal.scheduleApplicationInfoChanged(arrayList, parseUserArg);
                printWriter.println("Packages updated with most recent ApplicationInfos.");
                return;
            }
            arrayList.add(nextArg);
        }
    }

    public final int runWaitForBroadcastBarrier(PrintWriter printWriter) {
        PrintWriter printWriter2 = new PrintWriter((Writer) new TeeWriter(new Writer[]{ActivityManagerDebugConfig.LOG_WRITER_INFO, printWriter}));
        boolean z = false;
        boolean z2 = false;
        while (true) {
            String nextOption = getNextOption();
            if (nextOption == null) {
                this.mInternal.waitForBroadcastBarrier(printWriter2, z, z2);
                return 0;
            }
            if (nextOption.equals("--flush-broadcast-loopers")) {
                z = true;
            } else {
                if (!nextOption.equals("--flush-application-threads")) {
                    getErrPrintWriter().println("Error: Unknown option: ".concat(nextOption));
                    return -1;
                }
                z2 = true;
            }
        }
    }

    public final void runWaitForBroadcastDispatch(PrintWriter printWriter) {
        final PrintWriter printWriter2 = new PrintWriter((Writer) new TeeWriter(new Writer[]{ActivityManagerDebugConfig.LOG_WRITER_INFO, printWriter}));
        try {
            final Intent makeIntent = makeIntent();
            ActivityManagerService activityManagerService = this.mInternal;
            activityManagerService.enforceCallingPermission("android.permission.DUMP", "waitForBroadcastDispatch");
            final BroadcastQueueModernImpl broadcastQueueModernImpl = activityManagerService.mBroadcastQueue;
            broadcastQueueModernImpl.getClass();
            broadcastQueueModernImpl.waitFor(new BooleanSupplier() { // from class: com.android.server.am.BroadcastQueueModernImpl$$ExternalSyntheticLambda9
                @Override // java.util.function.BooleanSupplier
                public final boolean getAsBoolean() {
                    BroadcastQueueModernImpl broadcastQueueModernImpl2 = BroadcastQueueModernImpl.this;
                    Intent intent = makeIntent;
                    PrintWriter printWriter3 = printWriter2;
                    broadcastQueueModernImpl2.getClass();
                    return broadcastQueueModernImpl2.testAllProcessQueues(new BroadcastQueueModernImpl$$ExternalSyntheticLambda10(1, intent), "dispatch of " + intent, printWriter3);
                }
            });
        } catch (URISyntaxException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public final int runWaitForBroadcastIdle(PrintWriter printWriter) {
        PrintWriter printWriter2 = new PrintWriter((Writer) new TeeWriter(new Writer[]{ActivityManagerDebugConfig.LOG_WRITER_INFO, printWriter}));
        boolean z = false;
        while (true) {
            String nextOption = getNextOption();
            if (nextOption == null) {
                this.mInternal.waitForBroadcastIdle(printWriter2, z);
                return 0;
            }
            if (!nextOption.equals("--flush-broadcast-loopers")) {
                getErrPrintWriter().println("Error: Unknown option: ".concat(nextOption));
                return -1;
            }
            z = true;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:63:0x0104, code lost:
    
        return 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x0101, code lost:
    
        if (r10.mUid < 0) goto L53;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int runWatchUids(java.io.PrintWriter r12) {
        /*
            Method dump skipped, instructions count: 276
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.am.ActivityManagerShellCommand.runWatchUids(java.io.PrintWriter):int");
    }

    public final void runWrite(PrintWriter printWriter) {
        this.mInternal.enforceCallingPermission("android.permission.SET_ACTIVITY_WATCHER", "registerUidObserver()");
        RecentTasks recentTasks = ActivityTaskManagerService.this.mRecentTasks;
        WindowManagerGlobalLock windowManagerGlobalLock = recentTasks.mService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                recentTasks.syncPersistentTaskIdsLocked();
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
        PersisterQueue persisterQueue = recentTasks.mTaskPersister.mPersisterQueue;
        synchronized (persisterQueue) {
            persisterQueue.mNextWriteTime = -1L;
            persisterQueue.notifyAll();
            do {
                try {
                    persisterQueue.wait();
                } catch (InterruptedException unused) {
                }
            } while (persisterQueue.mNextWriteTime == -1);
        }
        printWriter.println("All tasks persisted.");
    }

    public final boolean switchUserAndWaitForComplete(final int i) {
        UserInfo currentUser = this.mInterface.mUserController.getCurrentUser();
        if (currentUser != null && i == currentUser.id) {
            return true;
        }
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        IUserSwitchObserver iUserSwitchObserver = new UserSwitchObserver() { // from class: com.android.server.am.ActivityManagerShellCommand.3
            public final void onUserSwitchComplete(int i2) {
                if (i == i2) {
                    countDownLatch.countDown();
                }
            }
        };
        try {
            this.mInterface.registerUserSwitchObserver(iUserSwitchObserver, ActivityManagerShellCommand.class.getName());
            boolean switchUser = this.mInterface.switchUser(i);
            if (!switchUser) {
                this.mInterface.unregisterUserSwitchObserver(iUserSwitchObserver);
                return false;
            }
            try {
                switchUser = countDownLatch.await(120000L, TimeUnit.MILLISECONDS);
            } catch (InterruptedException unused) {
                getErrPrintWriter().println("Error: Thread interrupted unexpectedly.");
            }
            return switchUser;
        } finally {
            this.mInterface.unregisterUserSwitchObserver(iUserSwitchObserver);
        }
    }

    public final void writeDeviceConfig(ProtoOutputStream protoOutputStream, long j, PrintWriter printWriter, DisplayMetrics displayMetrics) {
        long j2;
        int i;
        int[] iArr;
        EGLConfig[] eGLConfigArr;
        int[] iArr2;
        if (protoOutputStream != null) {
            j2 = protoOutputStream.start(j);
            protoOutputStream.write(1155346202625L, displayMetrics.widthPixels);
            protoOutputStream.write(1155346202626L, displayMetrics.heightPixels);
            protoOutputStream.write(1155346202627L, DisplayMetrics.DENSITY_DEVICE_STABLE);
        } else {
            j2 = -1;
        }
        if (printWriter != null) {
            printWriter.print("stable-width-px: ");
            printWriter.println(displayMetrics.widthPixels);
            printWriter.print("stable-height-px: ");
            printWriter.println(displayMetrics.heightPixels);
            printWriter.print("stable-density-dpi: ");
            printWriter.println(DisplayMetrics.DENSITY_DEVICE_STABLE);
        }
        MemInfoReader memInfoReader = new MemInfoReader();
        memInfoReader.readMemInfo();
        KeyguardManager keyguardManager = (KeyguardManager) this.mInternal.mContext.getSystemService(KeyguardManager.class);
        if (protoOutputStream != null) {
            protoOutputStream.write(1116691496964L, memInfoReader.getTotalSize());
            protoOutputStream.write(1133871366149L, ActivityManager.isLowRamDeviceStatic());
            protoOutputStream.write(1155346202630L, Runtime.getRuntime().availableProcessors());
            protoOutputStream.write(1133871366151L, keyguardManager.isDeviceSecure());
        }
        if (printWriter != null) {
            printWriter.print("total-ram: ");
            printWriter.println(memInfoReader.getTotalSize());
            printWriter.print("low-ram: ");
            printWriter.println(ActivityManager.isLowRamDeviceStatic());
            printWriter.print("max-cores: ");
            printWriter.println(Runtime.getRuntime().availableProcessors());
            printWriter.print("has-secure-screen-lock: ");
            printWriter.println(keyguardManager.isDeviceSecure());
        }
        try {
            ConfigurationInfo deviceConfigurationInfo = this.mTaskInterface.getDeviceConfigurationInfo();
            int i2 = deviceConfigurationInfo.reqGlEsVersion;
            if (i2 != 0) {
                if (protoOutputStream != null) {
                    protoOutputStream.write(1155346202632L, i2);
                }
                if (printWriter != null) {
                    printWriter.print("opengl-version: 0x");
                    printWriter.println(Integer.toHexString(deviceConfigurationInfo.reqGlEsVersion));
                }
            }
            HashSet hashSet = new HashSet();
            EGL10 egl10 = (EGL10) EGLContext.getEGL();
            if (egl10 == null) {
                getErrPrintWriter().println("Warning: couldn't get EGL");
            } else {
                EGLDisplay eglGetDisplay = egl10.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
                egl10.eglInitialize(eglGetDisplay, new int[2]);
                int[] iArr3 = new int[1];
                if (egl10.eglGetConfigs(eglGetDisplay, null, 0, iArr3)) {
                    int i3 = iArr3[0];
                    EGLConfig[] eGLConfigArr2 = new EGLConfig[i3];
                    if (egl10.eglGetConfigs(eglGetDisplay, eGLConfigArr2, i3, iArr3)) {
                        int[] iArr4 = {12375, 1, 12374, 1, 12344};
                        int[] iArr5 = {12440, 2, 12344};
                        int[] iArr6 = new int[1];
                        int i4 = 0;
                        while (i4 < iArr3[0]) {
                            egl10.eglGetConfigAttrib(eglGetDisplay, eGLConfigArr2[i4], 12327, iArr6);
                            if (iArr6[0] != 12368) {
                                egl10.eglGetConfigAttrib(eglGetDisplay, eGLConfigArr2[i4], 12339, iArr6);
                                if ((iArr6[0] & 1) != 0) {
                                    egl10.eglGetConfigAttrib(eglGetDisplay, eGLConfigArr2[i4], 12352, iArr6);
                                    if ((iArr6[0] & 1) != 0) {
                                        i = i4;
                                        iArr = iArr6;
                                        eGLConfigArr = eGLConfigArr2;
                                        iArr2 = iArr3;
                                        addExtensionsForConfig(egl10, eglGetDisplay, eGLConfigArr2[i4], iArr4, null, hashSet);
                                    } else {
                                        i = i4;
                                        iArr = iArr6;
                                        eGLConfigArr = eGLConfigArr2;
                                        iArr2 = iArr3;
                                    }
                                    if ((iArr[0] & 4) != 0) {
                                        addExtensionsForConfig(egl10, eglGetDisplay, eGLConfigArr[i], iArr4, iArr5, hashSet);
                                    }
                                    i4 = i + 1;
                                    iArr6 = iArr;
                                    iArr3 = iArr2;
                                    eGLConfigArr2 = eGLConfigArr;
                                }
                            }
                            i = i4;
                            iArr = iArr6;
                            eGLConfigArr = eGLConfigArr2;
                            iArr2 = iArr3;
                            i4 = i + 1;
                            iArr6 = iArr;
                            iArr3 = iArr2;
                            eGLConfigArr2 = eGLConfigArr;
                        }
                        egl10.eglTerminate(eglGetDisplay);
                    } else {
                        getErrPrintWriter().println("Warning: couldn't get EGL configs");
                    }
                } else {
                    getErrPrintWriter().println("Warning: couldn't get EGL config count");
                }
            }
            String[] strArr = (String[]) hashSet.toArray(new String[hashSet.size()]);
            Arrays.sort(strArr);
            for (int i5 = 0; i5 < strArr.length; i5++) {
                if (protoOutputStream != null) {
                    protoOutputStream.write(2237677961225L, strArr[i5]);
                }
                if (printWriter != null) {
                    printWriter.print("opengl-extensions: ");
                    printWriter.println(strArr[i5]);
                }
            }
            PackageManager packageManager = this.mInternal.mContext.getPackageManager();
            List<SharedLibraryInfo> sharedLibraries = packageManager.getSharedLibraries(0);
            Collections.sort(sharedLibraries, Comparator.comparing(new ActivityManagerShellCommand$$ExternalSyntheticLambda1()));
            for (int i6 = 0; i6 < sharedLibraries.size(); i6++) {
                if (protoOutputStream != null) {
                    protoOutputStream.write(2237677961226L, sharedLibraries.get(i6).getName());
                }
                if (printWriter != null) {
                    printWriter.print("shared-libraries: ");
                    printWriter.println(sharedLibraries.get(i6).getName());
                }
            }
            FeatureInfo[] systemAvailableFeatures = packageManager.getSystemAvailableFeatures();
            Arrays.sort(systemAvailableFeatures, new ActivityManagerShellCommand$$ExternalSyntheticLambda2());
            for (int i7 = 0; i7 < systemAvailableFeatures.length; i7++) {
                String str = systemAvailableFeatures[i7].name;
                if (str != null) {
                    if (protoOutputStream != null) {
                        protoOutputStream.write(2237677961227L, str);
                    }
                    if (printWriter != null) {
                        printWriter.print("features: ");
                        printWriter.println(systemAvailableFeatures[i7].name);
                    }
                }
            }
            if (protoOutputStream != null) {
                protoOutputStream.end(j2);
            }
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
