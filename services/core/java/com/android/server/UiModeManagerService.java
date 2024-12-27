package com.android.server;

import android.R;
import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.app.AlarmManager;
import android.app.Flags;
import android.app.IOnProjectionStateChangedListener;
import android.app.IUiModeManager;
import android.app.IUiModeManagerCallback;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.StatusBarManager;
import android.app.UiModeManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.net.shared.InitialConfiguration$$ExternalSyntheticOutline0;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.PermissionEnforcer;
import android.os.PowerManager;
import android.os.PowerManagerInternal;
import android.os.PowerSaveState;
import android.os.Process;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.ServiceManager;
import android.os.ShellCallback;
import android.os.ShellCommand;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.service.dreams.DreamManagerInternal;
import android.service.vr.IVrManager;
import android.service.vr.IVrStateCallbacks;
import android.util.ArraySet;
import android.util.Log;
import android.util.Slog;
import android.util.SparseArray;
import android.util.TimeUtils;
import android.view.ContextThemeWrapper;
import android.widget.Toast;

import com.android.internal.app.DisableCarModeActivity;
import com.android.internal.notification.SystemNotificationChannels;
import com.android.internal.util.DumpUtils;
import com.android.internal.util.FunctionalUtils;
import com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.pm.UserManagerService;
import com.android.server.twilight.TwilightListener;
import com.android.server.twilight.TwilightManager;
import com.android.server.twilight.TwilightService;
import com.android.server.twilight.TwilightState;
import com.android.server.wm.ActivityTaskManagerInternal;
import com.android.server.wm.ActivityTaskManagerService;
import com.android.server.wm.PackageConfigurationUpdaterImpl;
import com.android.server.wm.WindowManagerInternal;

import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.rune.CoreRune;
import com.samsung.android.server.packagefeature.PackageFeatureUserChange;
import com.samsung.android.server.packagefeature.PackageFeatureUserChangePersister;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class UiModeManagerService extends SystemService {
    public static final Set SUPPORTED_NIGHT_MODE_CUSTOM_TYPES = new ArraySet(new Integer[] {0, 1});
    public static final DateTimeFormatter sFormatter =
            DateTimeFormatter.ofPattern("MM-dd HH:mm:ss.SSS");
    public final LocalTime DEFAULT_CUSTOM_NIGHT_END_TIME;
    public final LocalTime DEFAULT_CUSTOM_NIGHT_START_TIME;
    public ActivityTaskManagerInternal mActivityTaskManager;
    public AlarmManager mAlarmManager;
    public int mAttentionModeThemeOverlay;
    public boolean mAutoModeChangeImmediately;
    public final AnonymousClass2 mBatteryReceiver;
    public boolean mCar;
    public int mCarModeEnableFlags;
    public boolean mCarModeEnabled;
    public boolean mCarModeKeepsScreenOn;
    public final Map mCarModePackagePriority;
    public boolean mCharging;
    public boolean mComputedNightMode;
    public final Configuration mConfiguration;
    public final AnonymousClass9 mContrastObserver;
    public final SparseArray mContrasts;
    public int mCurUiMode;
    public int mCurrentUser;
    public LocalTime mCustomAutoNightModeEndMilliseconds;
    public LocalTime mCustomAutoNightModeStartMilliseconds;
    public final UiModeManagerService$$ExternalSyntheticLambda2 mCustomTimeListener;
    public final AnonymousClass9 mDarkThemeObserver;
    public int mDefaultUiModeType;
    public boolean mDeskModeKeepsScreenOn;
    public boolean mDesktopModeEnabled;
    public final AnonymousClass2 mDeviceInactiveListener;
    public final AnonymousClass2 mDockModeReceiver;
    public int mDockState;
    public DreamManagerInternal mDreamManagerInternal;
    public boolean mDreamsDisabledByAmbientModeSuppression;
    public boolean mEnableCarDockLaunch;
    public final Handler mHandler;
    public boolean mHoldingConfiguration;
    public final Injector mInjector;
    public boolean mIsNightModeRegistered;
    public boolean mLastBedtimeRequestedNightMode;
    public int mLastBroadcastState;
    public PowerManagerInternal mLocalPowerManager;
    public final LocalService mLocalService;
    public final Object mLock;
    public boolean mNewDexModeEnabled;
    public final AnonymousClass1 mNightMode;
    public int mNightModeCustomType;
    public boolean mNightModeLocked;
    public final List mNightPriorityAllowedPackagesFromScpm;
    public final PackageFeatureUserChange mNightPriorityAppliedPackages;
    public NotificationManager mNotificationManager;
    public final AnonymousClass2 mOnPackageAdded;
    public final AnonymousClass2 mOnShutdown;
    public final AnonymousClass2 mOnTimeChangedHandler;
    public boolean mOverrideNightModeOff;
    public boolean mOverrideNightModeOn;
    public int mOverrideNightModeUser;
    public final PackageFeatureUserChange mPackagesNeedToShowDialog;
    public PowerManager mPowerManager;
    public boolean mPowerSave;
    public SparseArray mProjectionHolders;
    public SparseArray mProjectionListeners;
    public final AnonymousClass2 mResultReceiver;
    public final Stub mService;
    public int mSetUiMode;
    public final AnonymousClass2 mSettingsRestored;
    public final AnonymousClass9 mSetupWizardObserver;
    public boolean mShopDemo;
    public boolean mStartDreamImmediatelyOnDock;
    public StatusBarManager mStatusBarManager;
    public boolean mSystemReady;
    public boolean mTelevision;
    public boolean mToggleNewDexMode;
    public final AnonymousClass5 mTwilightListener;
    public TwilightManager mTwilightManager;
    public boolean mUiModeLocked;
    public final SparseArray mUiModeManagerCallbacks;
    public boolean mVrHeadset;
    public final AnonymousClass8 mVrStateCallbacks;
    public boolean mWaitForDeviceInactive;
    public PowerManager.WakeLock mWakeLock;
    public boolean mWatch;
    public WindowManagerInternal mWindowManager;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.UiModeManagerService$1, reason: invalid class name */
    public final class AnonymousClass1 {
        public int mNightModeValue;

        public final void set(int i) {
            this.mNightModeValue = i;
            if (Flags.enableNightModeBinderCache()) {
                UiModeManager.invalidateNightModeCache();
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.UiModeManagerService$16, reason: invalid class name */
    public final class AnonymousClass16 implements Runnable {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ UiModeManagerService this$0;

        public /* synthetic */ AnonymousClass16(UiModeManagerService uiModeManagerService, int i) {
            this.$r8$classId = i;
            this.this$0 = uiModeManagerService;
        }

        @Override // java.lang.Runnable
        public final void run() {
            switch (this.$r8$classId) {
                case 0:
                    this.this$0.mAutoModeChangeImmediately = false;
                    break;
                default:
                    Toast.makeText(
                                    new ContextThemeWrapper(
                                            this.this$0.getContext(),
                                            R.style.Theme.DeviceDefault.Light),
                                    this.this$0.getContext().getString(17042866),
                                    1)
                            .show();
                    break;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.UiModeManagerService$9, reason: invalid class name */
    public final class AnonymousClass9 extends ContentObserver {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ UiModeManagerService this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public /* synthetic */ AnonymousClass9(
                UiModeManagerService uiModeManagerService, Handler handler, int i) {
            super(handler);
            this.$r8$classId = i;
            this.this$0 = uiModeManagerService;
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri) {
            switch (this.$r8$classId) {
                case 0:
                    synchronized (this.this$0.mLock) {
                        try {
                            if (Settings.Secure.getIntForUser(
                                                    this.this$0.getContext().getContentResolver(),
                                                    "user_setup_complete",
                                                    0,
                                                    UserHandle.getCallingUserId())
                                            == 1
                                    && !z) {
                                UiModeManagerService uiModeManagerService = this.this$0;
                                uiModeManagerService.getClass();
                                uiModeManagerService
                                        .getContext()
                                        .getContentResolver()
                                        .unregisterContentObserver(
                                                this.this$0.mSetupWizardObserver);
                                Context context = this.this$0.getContext();
                                this.this$0.updateNightModeFromSettingsLocked(
                                        context,
                                        context.getResources(),
                                        UserHandle.getCallingUserId());
                                this.this$0.updateLocked(0, 0);
                            }
                        } finally {
                        }
                    }
                    return;
                case 1:
                    this.this$0.updateSystemProperties();
                    return;
                case 2:
                    synchronized (this.this$0.mLock) {
                        try {
                            if (this.this$0.updateContrastLocked()) {
                                UiModeManagerService uiModeManagerService2 = this.this$0;
                                if (!uiModeManagerService2.mContrasts.contains(
                                        uiModeManagerService2.mCurrentUser)) {
                                    uiModeManagerService2.updateContrastLocked();
                                }
                                final float floatValue =
                                        ((Float)
                                                        uiModeManagerService2.mContrasts.get(
                                                                uiModeManagerService2.mCurrentUser))
                                                .floatValue();
                                UiModeManagerService uiModeManagerService3 = this.this$0;
                                ((RemoteCallbackList)
                                                uiModeManagerService3.mUiModeManagerCallbacks.get(
                                                        uiModeManagerService3.mCurrentUser,
                                                        new RemoteCallbackList()))
                                        .broadcast(
                                                FunctionalUtils.ignoreRemoteException(
                                                        new FunctionalUtils
                                                                .RemoteExceptionIgnoringConsumer() { // from class: com.android.server.UiModeManagerService$11$$ExternalSyntheticLambda0
                                                            public final void acceptOrThrow(
                                                                    Object obj) {
                                                                ((IUiModeManagerCallback) obj)
                                                                        .notifyContrastChanged(
                                                                                floatValue);
                                                            }
                                                        }));
                            }
                        } finally {
                        }
                    }
                    return;
                default:
                    UiModeManagerService uiModeManagerService4 = this.this$0;
                    uiModeManagerService4.mShopDemo =
                            Settings.Secure.getInt(
                                            uiModeManagerService4.getContext().getContentResolver(),
                                            "shopdemo",
                                            0)
                                    == 1;
                    return;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public class Injector {}

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class LocalService {
        public LocalService() {}

        public final boolean isNightMode() {
            boolean z;
            synchronized (UiModeManagerService.this.mLock) {
                z = (UiModeManagerService.this.mConfiguration.uiMode & 32) != 0;
            }
            return z;
        }

        public final void setNewDexMode(boolean z) {
            synchronized (UiModeManagerService.this.mLock) {
                try {
                    UiModeManagerService uiModeManagerService = UiModeManagerService.this;
                    if (uiModeManagerService.mNewDexModeEnabled == z) {
                        return;
                    }
                    uiModeManagerService.mToggleNewDexMode = true;
                    uiModeManagerService.mNewDexModeEnabled = z;
                    uiModeManagerService.updateConfigurationLocked();
                    UiModeManagerService.this.applyConfigurationExternallyLocked();
                    UiModeManagerService.this.mToggleNewDexMode = false;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class LogWrapper {
        public static final Logger logger;
        public static final SimpleDateFormat formatter =
                new SimpleDateFormat("MM-dd HH:mm:ss.SSS: ", Locale.getDefault());
        public static final Date date = new Date();

        static {
            try {
                FileHandler fileHandler =
                        new FileHandler("/data/log/dark_mode_log%g.txt", 5120, 2, true);
                fileHandler.setFormatter(
                        new Formatter() { // from class:
                                          // com.android.server.UiModeManagerService.LogWrapper.1
                            @Override // java.util.logging.Formatter
                            public final String format(LogRecord logRecord) {
                                Date date2 = LogWrapper.date;
                                date2.setTime(System.currentTimeMillis());
                                StringBuilder sb = new StringBuilder(80);
                                sb.append(LogWrapper.formatter.format(date2));
                                sb.append(logRecord.getMessage());
                                return sb.toString();
                            }
                        });
                Logger logger2 = Logger.getLogger(LogWrapper.class.getName());
                logger = logger2;
                logger2.addHandler(fileHandler);
                logger2.setLevel(Level.ALL);
                logger2.setUseParentHandlers(false);
            } catch (Exception e) {
                Log.i("LogWrapper", "Can not use LogWrapper " + e.toString());
            }
        }

        public static StringBuilder getLogText() {
            File[] fileArr = {
                new File("/data/log/dark_mode_log0.txt"), new File("/data/log/dark_mode_log1.txt")
            };
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 2; i++) {
                File file = fileArr[i];
                if (file.exists()) {
                    try {
                        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                        while (true) {
                            try {
                                String readLine = bufferedReader.readLine();
                                if (readLine == null) {
                                    break;
                                }
                                sb.append(readLine);
                                sb.append('\n');
                            } finally {
                            }
                        }
                        bufferedReader.close();
                        sb.append('\n');
                    } catch (IOException e) {
                        Log.e("LogWrapper", "Can not use getLogText : " + e);
                        return null;
                    }
                }
            }
            return sb;
        }

        public static void i(String str) {
            Logger logger2 = logger;
            if (logger2 != null) {
                logger2.log(
                        Level.INFO,
                        String.format(
                                "V %s(%d): %s\n",
                                "UiModeManager", Integer.valueOf(Binder.getCallingPid()), str));
            }
            Log.i("UiModeManager", str);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ProjectionHolder implements IBinder.DeathRecipient {
        public final IBinder mBinder;
        public final String mPackageName;
        public final UiModeManagerService$Stub$$ExternalSyntheticLambda1 mProjectionReleaser;
        public final int mProjectionType;

        public ProjectionHolder(
                String str,
                int i,
                IBinder iBinder,
                UiModeManagerService$Stub$$ExternalSyntheticLambda1
                        uiModeManagerService$Stub$$ExternalSyntheticLambda1) {
            this.mPackageName = str;
            this.mProjectionType = i;
            this.mBinder = iBinder;
            this.mProjectionReleaser = uiModeManagerService$Stub$$ExternalSyntheticLambda1;
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            Set set = UiModeManagerService.SUPPORTED_NIGHT_MODE_CUSTOM_TYPES;
            StringBuilder sb = new StringBuilder("Projection holder ");
            sb.append(this.mPackageName);
            sb.append(" died. Releasing projection type ");
            UiModeManagerService$13$$ExternalSyntheticOutline0.m(
                    sb, this.mProjectionType, ".", "UiModeManager");
            UiModeManagerService$Stub$$ExternalSyntheticLambda1
                    uiModeManagerService$Stub$$ExternalSyntheticLambda1 = this.mProjectionReleaser;
            UiModeManagerService.m102$$Nest$mreleaseProjectionUnchecked(
                    uiModeManagerService$Stub$$ExternalSyntheticLambda1.f$0,
                    this.mProjectionType,
                    this.mPackageName);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Shell extends ShellCommand {
        public final IUiModeManager mInterface;

        public Shell(IUiModeManager iUiModeManager) {
            this.mInterface = iUiModeManager;
        }

        public static String nightModeToStr(int i, int i2) {
            return i != 0
                    ? i != 1
                            ? i != 2
                                    ? i != 3
                                            ? "unknown"
                                            : i2 == 0
                                                    ? "custom_schedule"
                                                    : i2 == 1 ? "custom_bedtime" : "unknown"
                                    : "yes"
                            : "no"
                    : "auto";
        }

        public final int handleCarMode() {
            PrintWriter errPrintWriter = getErrPrintWriter();
            String nextArg = getNextArg();
            if (nextArg == null) {
                printCurrentCarMode();
                return 0;
            }
            if (nextArg.equals("yes")) {
                this.mInterface.enableCarMode(0, 0, "");
                printCurrentCarMode();
                return 0;
            }
            if (!nextArg.equals("no")) {
                errPrintWriter.println("Error: mode must be 'yes', or 'no'");
                return -1;
            }
            this.mInterface.disableCarMode(0);
            printCurrentCarMode();
            return 0;
        }

        public final int handleCustomTime() {
            String nextArg = getNextArg();
            if (nextArg == null) {
                PrintWriter outPrintWriter = getOutPrintWriter();
                StringBuilder sb = new StringBuilder("start ");
                long customNightModeStart = this.mInterface.getCustomNightModeStart();
                Set set = UiModeManagerService.SUPPORTED_NIGHT_MODE_CUSTOM_TYPES;
                sb.append(LocalTime.ofNanoOfDay(customNightModeStart * 1000).toString());
                outPrintWriter.println(sb.toString());
                getOutPrintWriter()
                        .println(
                                "end "
                                        + LocalTime.ofNanoOfDay(
                                                        this.mInterface.getCustomNightModeEnd()
                                                                * 1000)
                                                .toString());
                return 0;
            }
            if (nextArg.equals("end")) {
                String nextArg2 = getNextArg();
                IUiModeManager iUiModeManager = this.mInterface;
                LocalTime parse = LocalTime.parse(nextArg2);
                Set set2 = UiModeManagerService.SUPPORTED_NIGHT_MODE_CUSTOM_TYPES;
                iUiModeManager.setCustomNightModeEnd(parse.toNanoOfDay() / 1000);
                return 0;
            }
            if (!nextArg.equals("start")) {
                getErrPrintWriter().println("command must be in [start|end]");
                return -1;
            }
            String nextArg3 = getNextArg();
            IUiModeManager iUiModeManager2 = this.mInterface;
            LocalTime parse2 = LocalTime.parse(nextArg3);
            Set set3 = UiModeManagerService.SUPPORTED_NIGHT_MODE_CUSTOM_TYPES;
            iUiModeManager2.setCustomNightModeStart(parse2.toNanoOfDay() / 1000);
            return 0;
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        /* JADX WARN: Removed duplicated region for block: B:16:0x004d  */
        /* JADX WARN: Removed duplicated region for block: B:24:0x0060 A[Catch: RemoteException -> 0x0031, TRY_LEAVE, TryCatch #0 {RemoteException -> 0x0031, blocks: (B:7:0x0012, B:18:0x0051, B:20:0x0056, B:22:0x005b, B:24:0x0060, B:27:0x006c, B:44:0x00e2, B:46:0x00ec, B:47:0x00f1, B:49:0x010d, B:67:0x0026, B:70:0x0034, B:73:0x003f), top: B:6:0x0012 }] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final int onCommand(java.lang.String r11) {
            /*
                Method dump skipped, instructions count: 322
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.UiModeManagerService.Shell.onCommand(java.lang.String):int");
        }

        public final void onHelp() {
            PrintWriter outPrintWriter = getOutPrintWriter();
            outPrintWriter.println("UiModeManager service (uimode) commands:");
            outPrintWriter.println("  help");
            outPrintWriter.println("    Print this help text.");
            outPrintWriter.println("  night [yes|no|auto|custom_schedule|custom_bedtime]");
            BatteryService$$ExternalSyntheticOutline0.m(
                    outPrintWriter,
                    "    Set or read night mode.",
                    "  car [yes|no]",
                    "    Set or read car mode.",
                    "  time [start|end] <ISO time>");
            outPrintWriter.println(
                    "    Set custom start/end schedule time (night mode must be set to custom to"
                        + " apply).");
        }

        public final void printCurrentCarMode() {
            getOutPrintWriter()
                    .println(
                            "Car mode: "
                                    .concat(
                                            this.mInterface.getCurrentModeType() == 3
                                                    ? "yes"
                                                    : "no"));
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Stub extends IUiModeManager.Stub {
        public Stub(Context context) {
            super(PermissionEnforcer.fromContext(context));
        }

        public final void addCallback(IUiModeManagerCallback iUiModeManagerCallback) {
            int callingUserId = UserHandle.getCallingUserId();
            synchronized (UiModeManagerService.this.mLock) {
                try {
                    if (!UiModeManagerService.this.mUiModeManagerCallbacks.contains(
                            callingUserId)) {
                        UiModeManagerService.this.mUiModeManagerCallbacks.put(
                                callingUserId, new RemoteCallbackList());
                    }
                    ((RemoteCallbackList)
                                    UiModeManagerService.this.mUiModeManagerCallbacks.get(
                                            callingUserId))
                            .register(iUiModeManagerCallback);
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void addNightPriorityAllowedPackageFromShell(String str) {}

        public final void addOnProjectionStateChangedListener(
                IOnProjectionStateChangedListener iOnProjectionStateChangedListener, int i) {
            addOnProjectionStateChangedListener_enforcePermission();
            if (i == 0) {
                return;
            }
            UiModeManagerService.m99$$Nest$menforceValidCallingUser(
                    UiModeManagerService.this, UserHandle.getCallingUserId());
            synchronized (UiModeManagerService.this.mLock) {
                try {
                    UiModeManagerService uiModeManagerService = UiModeManagerService.this;
                    if (uiModeManagerService.mProjectionListeners == null) {
                        uiModeManagerService.mProjectionListeners = new SparseArray(1);
                    }
                    if (!UiModeManagerService.this.mProjectionListeners.contains(i)) {
                        UiModeManagerService.this.mProjectionListeners.put(
                                i, new RemoteCallbackList());
                    }
                    if (((RemoteCallbackList) UiModeManagerService.this.mProjectionListeners.get(i))
                            .register(iOnProjectionStateChangedListener)) {
                        ArrayList arrayList = new ArrayList();
                        int populateWithRelevantActivePackageNames =
                                UiModeManagerService.this.populateWithRelevantActivePackageNames(
                                        i, arrayList);
                        if (!arrayList.isEmpty()) {
                            try {
                                iOnProjectionStateChangedListener.onProjectionStateChanged(
                                        populateWithRelevantActivePackageNames, arrayList);
                            } catch (RemoteException unused) {
                                Set set = UiModeManagerService.SUPPORTED_NIGHT_MODE_CUSTOM_TYPES;
                                Slog.w(
                                        "UiModeManager",
                                        "Failed a call to onProjectionStateChanged() during"
                                            + " listener registration.");
                            }
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void disableCarMode(int i) {
            disableCarModeByCallingPackage(i, null);
        }

        public final void disableCarModeByCallingPackage(int i, final String str) {
            if (isUiModeLocked()) {
                Set set = UiModeManagerService.SUPPORTED_NIGHT_MODE_CUSTOM_TYPES;
                Slog.e("UiModeManager", "disableCarMode while UI mode is locked");
                return;
            }
            UiModeManagerService.this.mInjector.getClass();
            int callingUid = Binder.getCallingUid();
            boolean z = callingUid == 1000;
            boolean z2 = callingUid == 2000;
            if (!z && !z2) {
                UiModeManagerService.m97$$Nest$massertLegit(UiModeManagerService.this, str);
            }
            int i2 = z ? i : i & (-3);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (UiModeManagerService.this.mLock) {
                    try {
                        UiModeManagerService.this.setCarModeLocked(
                                i2,
                                ((Integer)
                                                ((HashMap)
                                                                UiModeManagerService.this
                                                                        .mCarModePackagePriority)
                                                        .entrySet().stream()
                                                                .filter(
                                                                        new Predicate() { // from
                                                                                          // class:
                                                                                          // com.android.server.UiModeManagerService$Stub$$ExternalSyntheticLambda2
                                                                            @Override // java.util.function.Predicate
                                                                            public final boolean
                                                                                    test(
                                                                                            Object
                                                                                                    obj) {
                                                                                return ((String)
                                                                                                ((Map
                                                                                                                        .Entry)
                                                                                                                obj)
                                                                                                        .getValue())
                                                                                        .equals(
                                                                                                str);
                                                                            }
                                                                        })
                                                                .findFirst()
                                                                .map(
                                                                        new UiModeManagerService$Stub$$ExternalSyntheticLambda3())
                                                                .orElse(0))
                                        .intValue(),
                                str,
                                false);
                        UiModeManagerService uiModeManagerService = UiModeManagerService.this;
                        if (uiModeManagerService.mSystemReady) {
                            uiModeManagerService.updateLocked(0, i);
                        }
                    } finally {
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void dump(
                FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            Context context = UiModeManagerService.this.getContext();
            Set set = UiModeManagerService.SUPPORTED_NIGHT_MODE_CUSTOM_TYPES;
            if (DumpUtils.checkDumpPermission(context, "UiModeManager", printWriter)) {
                UiModeManagerService uiModeManagerService = UiModeManagerService.this;
                synchronized (uiModeManagerService.mLock) {
                    try {
                        printWriter.println("Current UI Mode Service state:");
                        printWriter.print("  mDockState=");
                        printWriter.print(uiModeManagerService.mDockState);
                        printWriter.print(" mLastBroadcastState=");
                        printWriter.println(uiModeManagerService.mLastBroadcastState);
                        printWriter.print(" mStartDreamImmediatelyOnDock=");
                        printWriter.print(uiModeManagerService.mStartDreamImmediatelyOnDock);
                        printWriter.print("  mNightMode=");
                        printWriter.print(uiModeManagerService.mNightMode.mNightModeValue);
                        printWriter.print(" (");
                        printWriter.print(
                                Shell.nightModeToStr(
                                        uiModeManagerService.mNightMode.mNightModeValue,
                                        uiModeManagerService.mNightModeCustomType));
                        printWriter.print(") ");
                        printWriter.print(" mOverrideOn/Off=");
                        printWriter.print(uiModeManagerService.mOverrideNightModeOn);
                        printWriter.print("/");
                        printWriter.print(uiModeManagerService.mOverrideNightModeOff);
                        printWriter.print("  mAttentionModeThemeOverlay=");
                        printWriter.print(uiModeManagerService.mAttentionModeThemeOverlay);
                        printWriter.print(" mNightModeLocked=");
                        printWriter.println(uiModeManagerService.mNightModeLocked);
                        printWriter.print("  mCarModeEnabled=");
                        printWriter.print(uiModeManagerService.mCarModeEnabled);
                        printWriter.print(" (carModeApps=");
                        for (Map.Entry entry :
                                ((HashMap) uiModeManagerService.mCarModePackagePriority)
                                        .entrySet()) {
                            printWriter.print(entry.getKey());
                            printWriter.print(":");
                            printWriter.print((String) entry.getValue());
                            printWriter.print(" ");
                        }
                        printWriter.println("");
                        printWriter.print(" mWaitForDeviceInactive=");
                        printWriter.print(uiModeManagerService.mWaitForDeviceInactive);
                        printWriter.print(" mComputedNightMode=");
                        printWriter.print(uiModeManagerService.mComputedNightMode);
                        printWriter.print(" customStart=");
                        printWriter.print(
                                uiModeManagerService.mCustomAutoNightModeStartMilliseconds);
                        printWriter.print(" customEnd");
                        printWriter.print(uiModeManagerService.mCustomAutoNightModeEndMilliseconds);
                        printWriter.print(" mCarModeEnableFlags=");
                        printWriter.print(uiModeManagerService.mCarModeEnableFlags);
                        printWriter.print(" mEnableCarDockLaunch=");
                        printWriter.println(uiModeManagerService.mEnableCarDockLaunch);
                        printWriter.print("  mCurUiMode=0x");
                        printWriter.print(Integer.toHexString(uiModeManagerService.mCurUiMode));
                        printWriter.print(" mUiModeLocked=");
                        printWriter.print(uiModeManagerService.mUiModeLocked);
                        printWriter.print(" mSetUiMode=0x");
                        printWriter.println(Integer.toHexString(uiModeManagerService.mSetUiMode));
                        printWriter.print("  mHoldingConfiguration=");
                        printWriter.print(uiModeManagerService.mHoldingConfiguration);
                        printWriter.print(" mSystemReady=");
                        printWriter.println(uiModeManagerService.mSystemReady);
                        if (uiModeManagerService.mTwilightManager != null) {
                            printWriter.print("  mTwilightService.getLastTwilightState()=");
                            printWriter.println(
                                    ((TwilightService.AnonymousClass1)
                                                    uiModeManagerService.mTwilightManager)
                                            .getLastTwilightState());
                        }
                        printWriter.println(UiModeManagerService.buildLogString());
                        printWriter.println(LogWrapper.getLogText());
                        printWriter.print("  mNightPriorityAllowedPackagesFromScpm=");
                        printWriter.println(
                                uiModeManagerService.mNightPriorityAllowedPackagesFromScpm);
                        uiModeManagerService.mNightPriorityAppliedPackages.dump(
                                printWriter, "Applied packages", "    ");
                        uiModeManagerService.mPackagesNeedToShowDialog.dump(
                                printWriter, "Show dialog packages", "    ");
                        printWriter.print("  mDesktopModeEnabled=");
                        printWriter.println(uiModeManagerService.mDesktopModeEnabled);
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
        }

        public final void enableCarMode(int i, int i2, String str) {
            if (isUiModeLocked()) {
                Set set = UiModeManagerService.SUPPORTED_NIGHT_MODE_CUSTOM_TYPES;
                Slog.e("UiModeManager", "enableCarMode while UI mode is locked");
                return;
            }
            if (i2 != 0
                    && UiModeManagerService.this
                                    .getContext()
                                    .checkCallingOrSelfPermission(
                                            "android.permission.ENTER_CAR_MODE_PRIORITIZED")
                            != 0) {
                throw new SecurityException(
                        "Enabling car mode with a priority requires permission"
                            + " ENTER_CAR_MODE_PRIORITIZED");
            }
            UiModeManagerService.this.mInjector.getClass();
            if (Binder.getCallingUid() != 2000) {
                UiModeManagerService.m97$$Nest$massertLegit(UiModeManagerService.this, str);
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (UiModeManagerService.this.mLock) {
                    try {
                        UiModeManagerService.this.setCarModeLocked(i, i2, str, true);
                        UiModeManagerService uiModeManagerService = UiModeManagerService.this;
                        if (uiModeManagerService.mSystemReady) {
                            uiModeManagerService.updateLocked(i, 0);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final int getActiveProjectionTypes() {
            int i;
            getActiveProjectionTypes_enforcePermission();
            synchronized (UiModeManagerService.this.mLock) {
                try {
                    i = 0;
                    if (UiModeManagerService.this.mProjectionHolders != null) {
                        int i2 = 0;
                        while (i < UiModeManagerService.this.mProjectionHolders.size()) {
                            if (!((List) UiModeManagerService.this.mProjectionHolders.valueAt(i))
                                    .isEmpty()) {
                                i2 |= UiModeManagerService.this.mProjectionHolders.keyAt(i);
                            }
                            i++;
                        }
                        i = i2;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            return i;
        }

        public final int getAttentionModeThemeOverlay() {
            int i;
            getAttentionModeThemeOverlay_enforcePermission();
            synchronized (UiModeManagerService.this.mLock) {
                i = UiModeManagerService.this.mAttentionModeThemeOverlay;
            }
            return i;
        }

        public final float getContrast() {
            float floatValue;
            synchronized (UiModeManagerService.this.mLock) {
                UiModeManagerService uiModeManagerService = UiModeManagerService.this;
                if (!uiModeManagerService.mContrasts.contains(uiModeManagerService.mCurrentUser)) {
                    uiModeManagerService.updateContrastLocked();
                }
                floatValue =
                        ((Float)
                                        uiModeManagerService.mContrasts.get(
                                                uiModeManagerService.mCurrentUser))
                                .floatValue();
            }
            return floatValue;
        }

        public final int getCurrentModeType() {
            int i;
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (UiModeManagerService.this.mLock) {
                    i = UiModeManagerService.this.mCurUiMode & 15;
                }
                return i;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final long getCustomNightModeEnd() {
            return UiModeManagerService.this.mCustomAutoNightModeEndMilliseconds.toNanoOfDay()
                    / 1000;
        }

        public final long getCustomNightModeStart() {
            return UiModeManagerService.this.mCustomAutoNightModeStartMilliseconds.toNanoOfDay()
                    / 1000;
        }

        public final int getNightMode() {
            int i;
            synchronized (UiModeManagerService.this.mLock) {
                i = UiModeManagerService.this.mNightMode.mNightModeValue;
            }
            return i;
        }

        public final int getNightModeCustomType() {
            int i;
            getNightModeCustomType_enforcePermission();
            synchronized (UiModeManagerService.this.mLock) {
                i = UiModeManagerService.this.mNightModeCustomType;
            }
            return i;
        }

        public final List getNightPriorityAllowedPackagesFromScpm() {
            List list;
            synchronized (UiModeManagerService.this.mNightPriorityAllowedPackagesFromScpm) {
                list = UiModeManagerService.this.mNightPriorityAllowedPackagesFromScpm;
            }
            return list;
        }

        public final int getPackageNightMode(String str, int i) {
            boolean z;
            if (!UiModeManagerService.this.isNightPriorityAllowedLocked(str)) {
                Set set = UiModeManagerService.SUPPORTED_NIGHT_MODE_CUSTOM_TYPES;
                BootReceiver$$ExternalSyntheticOutline0.m(
                        "getPackageNightMode is not allowed for ", str, "UiModeManager");
                return -1;
            }
            UiModeManagerService uiModeManagerService = UiModeManagerService.this;
            synchronized (uiModeManagerService.mNightPriorityAppliedPackages) {
                z = uiModeManagerService.mNightPriorityAppliedPackages.getValue(i, str) != null;
            }
            if (!z) {
                return 2;
            }
            ActivityTaskManagerInternal.PackageConfig findPackageConfiguration =
                    ActivityTaskManagerService.this.mPackageConfigPersister
                            .findPackageConfiguration(i, str);
            if (findPackageConfiguration != null) {
                return findPackageConfiguration.mNightMode.intValue();
            }
            BootReceiver$$ExternalSyntheticOutline0.m(
                    "getPackageNightMode cannot get config for ", str, "UiModeManager");
            return -1;
        }

        public final List getProjectingPackages(int i) {
            ArrayList arrayList;
            getProjectingPackages_enforcePermission();
            synchronized (UiModeManagerService.this.mLock) {
                arrayList = new ArrayList();
                UiModeManagerService.this.populateWithRelevantActivePackageNames(i, arrayList);
            }
            return arrayList;
        }

        public final boolean isNightModeLocked() {
            boolean z;
            synchronized (UiModeManagerService.this.mLock) {
                z = UiModeManagerService.this.mNightModeLocked;
            }
            return z;
        }

        public final boolean isUiModeLocked() {
            boolean z;
            synchronized (UiModeManagerService.this.mLock) {
                z = UiModeManagerService.this.mUiModeLocked;
            }
            return z;
        }

        public final void onShellCommand(
                FileDescriptor fileDescriptor,
                FileDescriptor fileDescriptor2,
                FileDescriptor fileDescriptor3,
                String[] strArr,
                ShellCallback shellCallback,
                ResultReceiver resultReceiver) {
            new Shell(UiModeManagerService.this.mService)
                    .exec(
                            UiModeManagerService.this.mService,
                            fileDescriptor,
                            fileDescriptor2,
                            fileDescriptor3,
                            strArr,
                            shellCallback,
                            resultReceiver);
        }

        public final boolean releaseProjection(int i, String str) {
            UiModeManagerService.m97$$Nest$massertLegit(UiModeManagerService.this, str);
            boolean z = ((i + (-1)) & i) == 0;
            if (i == 0 || !z) {
                throw new IllegalArgumentException("Must specify exactly one projection type.");
            }
            UiModeManagerService uiModeManagerService = UiModeManagerService.this;
            if ((i & 1) != 0) {
                uiModeManagerService
                        .getContext()
                        .enforceCallingPermission(
                                "android.permission.TOGGLE_AUTOMOTIVE_PROJECTION",
                                "toggleProjection");
            } else {
                uiModeManagerService.getClass();
            }
            UiModeManagerService.m99$$Nest$menforceValidCallingUser(
                    UiModeManagerService.this, UserHandle.getCallingUserId());
            return UiModeManagerService.m102$$Nest$mreleaseProjectionUnchecked(
                    UiModeManagerService.this, i, str);
        }

        public final void removeOnProjectionStateChangedListener(
                IOnProjectionStateChangedListener iOnProjectionStateChangedListener) {
            removeOnProjectionStateChangedListener_enforcePermission();
            synchronized (UiModeManagerService.this.mLock) {
                try {
                    if (UiModeManagerService.this.mProjectionListeners != null) {
                        for (int i = 0;
                                i < UiModeManagerService.this.mProjectionListeners.size();
                                i++) {
                            ((RemoteCallbackList)
                                            UiModeManagerService.this.mProjectionListeners.valueAt(
                                                    i))
                                    .unregister(iOnProjectionStateChangedListener);
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final boolean requestProjection(IBinder iBinder, int i, String str) {
            UiModeManagerService.m97$$Nest$massertLegit(UiModeManagerService.this, str);
            boolean z = ((i + (-1)) & i) == 0;
            if (i == 0 || !z) {
                throw new IllegalArgumentException("Must specify exactly one projection type.");
            }
            UiModeManagerService uiModeManagerService = UiModeManagerService.this;
            if ((i & 1) != 0) {
                uiModeManagerService
                        .getContext()
                        .enforceCallingPermission(
                                "android.permission.TOGGLE_AUTOMOTIVE_PROJECTION",
                                "toggleProjection");
            } else {
                uiModeManagerService.getClass();
            }
            UiModeManagerService.m99$$Nest$menforceValidCallingUser(
                    UiModeManagerService.this, UserHandle.getCallingUserId());
            synchronized (UiModeManagerService.this.mLock) {
                try {
                    UiModeManagerService uiModeManagerService2 = UiModeManagerService.this;
                    if (uiModeManagerService2.mProjectionHolders == null) {
                        uiModeManagerService2.mProjectionHolders = new SparseArray(1);
                    }
                    if (!UiModeManagerService.this.mProjectionHolders.contains(i)) {
                        UiModeManagerService.this.mProjectionHolders.put(i, new ArrayList(1));
                    }
                    List list = (List) UiModeManagerService.this.mProjectionHolders.get(i);
                    for (int i2 = 0; i2 < list.size(); i2++) {
                        if (str.equals(((ProjectionHolder) list.get(i2)).mPackageName)) {
                            return true;
                        }
                    }
                    if (i == 1 && !list.isEmpty()) {
                        return false;
                    }
                    ProjectionHolder projectionHolder =
                            new ProjectionHolder(
                                    str,
                                    i,
                                    iBinder,
                                    new UiModeManagerService$Stub$$ExternalSyntheticLambda1(
                                            UiModeManagerService.this));
                    try {
                        iBinder.linkToDeath(projectionHolder, 0);
                        list.add(projectionHolder);
                        Set set = UiModeManagerService.SUPPORTED_NIGHT_MODE_CUSTOM_TYPES;
                        Slog.d(
                                "UiModeManager",
                                "Package " + str + " set projection type " + i + ".");
                        UiModeManagerService.this.onProjectionStateChangedLocked(i);
                        return true;
                    } catch (RemoteException e) {
                        Set set2 = UiModeManagerService.SUPPORTED_NIGHT_MODE_CUSTOM_TYPES;
                        Slog.e(
                                "UiModeManager",
                                "linkToDeath failed for projection requester: "
                                        + projectionHolder.mPackageName
                                        + ".",
                                e);
                        return false;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void resetNightPriorityAppliedPackages(int i) {}

        public final void setApplicationNightMode(int i) {
            if (i != 0 && i != 1 && i != 2 && i != 3) {
                throw new IllegalArgumentException(
                        VibrationParam$1$$ExternalSyntheticOutline0.m(i, "Unknown mode: "));
            }
            int i2 = i != 1 ? i != 2 ? 0 : 32 : 16;
            ActivityTaskManagerService.LocalService localService =
                    (ActivityTaskManagerService.LocalService)
                            UiModeManagerService.this.mActivityTaskManager;
            localService.getClass();
            PackageConfigurationUpdaterImpl packageConfigurationUpdaterImpl =
                    new PackageConfigurationUpdaterImpl(
                            Binder.getCallingPid(), ActivityTaskManagerService.this);
            packageConfigurationUpdaterImpl.setNightMode(i2);
            packageConfigurationUpdaterImpl.commit();
        }

        public final void setAttentionModeThemeOverlay(int i) {
            setAttentionModeThemeOverlay_enforcePermission();
            UiModeManagerService.m99$$Nest$menforceValidCallingUser(
                    UiModeManagerService.this, UserHandle.getCallingUserId());
            synchronized (UiModeManagerService.this.mLock) {
                try {
                    UiModeManagerService uiModeManagerService = UiModeManagerService.this;
                    if (uiModeManagerService.mAttentionModeThemeOverlay != i) {
                        uiModeManagerService.mAttentionModeThemeOverlay = i;
                        Binder.withCleanCallingIdentity(
                                new FunctionalUtils
                                        .ThrowingRunnable() { // from class:
                                                              // com.android.server.UiModeManagerService$Stub$$ExternalSyntheticLambda0
                                    public final void runOrThrow() {
                                        UiModeManagerService.this.updateLocked(0, 0);
                                    }
                                });
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void setCustomNightModeEnd(long j) {
            LocalTime ofNanoOfDay;
            if (isNightModeLocked()
                    && UiModeManagerService.this
                                    .getContext()
                                    .checkCallingOrSelfPermission(
                                            "android.permission.MODIFY_DAY_NIGHT_MODE")
                            != 0) {
                Set set = UiModeManagerService.SUPPORTED_NIGHT_MODE_CUSTOM_TYPES;
                Slog.e(
                        "UiModeManager",
                        "Set custom time end, requires MODIFY_DAY_NIGHT_MODE permission");
                return;
            }
            int callingUserId = UserHandle.getCallingUserId();
            UiModeManagerService.m99$$Nest$menforceValidCallingUser(
                    UiModeManagerService.this, callingUserId);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                try {
                    ofNanoOfDay = LocalTime.ofNanoOfDay(j * 1000);
                } catch (DateTimeException unused) {
                    UiModeManagerService.this.unregisterDeviceInactiveListenerLocked();
                }
                if (ofNanoOfDay == null) {
                    return;
                }
                UiModeManagerService uiModeManagerService = UiModeManagerService.this;
                uiModeManagerService.mCustomAutoNightModeEndMilliseconds = ofNanoOfDay;
                UiModeManagerService.m101$$Nest$monCustomTimeUpdated(
                        uiModeManagerService, callingUserId);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void setCustomNightModeStart(long j) {
            LocalTime ofNanoOfDay;
            if (isNightModeLocked()
                    && UiModeManagerService.this
                                    .getContext()
                                    .checkCallingOrSelfPermission(
                                            "android.permission.MODIFY_DAY_NIGHT_MODE")
                            != 0) {
                Set set = UiModeManagerService.SUPPORTED_NIGHT_MODE_CUSTOM_TYPES;
                Slog.e(
                        "UiModeManager",
                        "Set custom time start, requires MODIFY_DAY_NIGHT_MODE permission");
                return;
            }
            int callingUserId = UserHandle.getCallingUserId();
            UiModeManagerService.m99$$Nest$menforceValidCallingUser(
                    UiModeManagerService.this, callingUserId);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                try {
                    ofNanoOfDay = LocalTime.ofNanoOfDay(j * 1000);
                } catch (DateTimeException unused) {
                    UiModeManagerService.this.unregisterDeviceInactiveListenerLocked();
                }
                if (ofNanoOfDay == null) {
                    return;
                }
                UiModeManagerService uiModeManagerService = UiModeManagerService.this;
                uiModeManagerService.mCustomAutoNightModeStartMilliseconds = ofNanoOfDay;
                uiModeManagerService.persistNightMode(callingUserId);
                UiModeManagerService.m101$$Nest$monCustomTimeUpdated(
                        UiModeManagerService.this, callingUserId);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void setDesktopMode(boolean z) {
            synchronized (UiModeManagerService.this.mLock) {
                UiModeManagerService uiModeManagerService = UiModeManagerService.this;
                uiModeManagerService.mDesktopModeEnabled = z;
                uiModeManagerService.updateConfigurationLocked();
            }
        }

        public final void setNightMode(int i) {
            setNightModeInternal(i, i == 3 ? 0 : -1);
        }

        public final boolean setNightModeActivated(boolean z) {
            return setNightModeActivatedForModeInternal(
                    UiModeManagerService.this.mNightModeCustomType, z);
        }

        public final boolean setNightModeActivatedForCustomMode(int i, boolean z) {
            return setNightModeActivatedForModeInternal(i, z);
        }

        public final boolean setNightModeActivatedForModeInternal(int i, boolean z) {
            if (UiModeManagerService.this
                            .getContext()
                            .checkCallingOrSelfPermission(
                                    "android.permission.MODIFY_DAY_NIGHT_MODE")
                    != 0) {
                Set set = UiModeManagerService.SUPPORTED_NIGHT_MODE_CUSTOM_TYPES;
                Slog.e(
                        "UiModeManager",
                        "Night mode locked, requires MODIFY_DAY_NIGHT_MODE permission");
                return false;
            }
            int identifier = Binder.getCallingUserHandle().getIdentifier();
            UiModeManagerService.m99$$Nest$menforceValidCallingUser(
                    UiModeManagerService.this, identifier);
            UiModeManagerService uiModeManagerService = UiModeManagerService.this;
            if (identifier != uiModeManagerService.mCurrentUser
                    && uiModeManagerService
                                    .getContext()
                                    .checkCallingOrSelfPermission(
                                            "android.permission.INTERACT_ACROSS_USERS")
                            != 0) {
                Slog.e(
                        "UiModeManager",
                        "Target user is not current user, INTERACT_ACROSS_USERS permission is"
                            + " required");
                return false;
            }
            if (i == 1) {
                UiModeManagerService.this.mLastBedtimeRequestedNightMode = z;
            }
            UiModeManagerService uiModeManagerService2 = UiModeManagerService.this;
            if (i != uiModeManagerService2.mNightModeCustomType) {
                return false;
            }
            if (!UiModeManagerService.m98$$Nest$mcanSetNightMode(uiModeManagerService2)) {
                StringBuilder m =
                        BatteryService$$ExternalSyntheticOutline0.m(
                                "Ignore setNightModeActivated : ", ", mNightMode ", z);
                m.append(UiModeManagerService.this.mNightMode);
                Log.i("UiModeManager", m.toString());
                return false;
            }
            String nameForUid =
                    UiModeManagerService.this
                            .getContext()
                            .getPackageManager()
                            .getNameForUid(Binder.getCallingUid());
            int callingPid = Binder.getCallingPid();
            String m100$$Nest$mgetPackageName =
                    UiModeManagerService.m100$$Nest$mgetPackageName(UiModeManagerService.this);
            synchronized (UiModeManagerService.this.mLock) {
                try {
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        UiModeManagerService uiModeManagerService3 = UiModeManagerService.this;
                        AnonymousClass1 anonymousClass1 = uiModeManagerService3.mNightMode;
                        int i2 = anonymousClass1.mNightModeValue;
                        if (i2 != 0 && i2 != 3) {
                            if (i2 == 1 && z) {
                                anonymousClass1.set(2);
                            } else if (i2 == 2 && !z) {
                                anonymousClass1.set(1);
                            }
                            Settings.System.putIntForUser(
                                    UiModeManagerService.this.getContext().getContentResolver(),
                                    "display_night_theme",
                                    z ? 1 : 0,
                                    UiModeManagerService.this.mCurrentUser);
                            LogWrapper.i(
                                    "setNightModeActivated : "
                                            + z
                                            + " by "
                                            + callingPid
                                            + ", "
                                            + nameForUid
                                            + ", "
                                            + m100$$Nest$mgetPackageName
                                            + ", mNightMode "
                                            + UiModeManagerService.this.mNightMode
                                            + ", mNightModeCustomType "
                                            + UiModeManagerService.this.mNightModeCustomType);
                            UiModeManagerService.this.updateConfigurationLocked();
                            UiModeManagerService.this.applyConfigurationExternallyLocked();
                            UiModeManagerService uiModeManagerService4 = UiModeManagerService.this;
                            uiModeManagerService4.persistNightMode(
                                    uiModeManagerService4.mCurrentUser);
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                        }
                        uiModeManagerService3.unregisterDeviceInactiveListenerLocked();
                        UiModeManagerService uiModeManagerService5 = UiModeManagerService.this;
                        uiModeManagerService5.mOverrideNightModeOff = !z;
                        uiModeManagerService5.mOverrideNightModeOn = z;
                        int i3 = uiModeManagerService5.mCurrentUser;
                        uiModeManagerService5.mOverrideNightModeUser = i3;
                        uiModeManagerService5.persistNightModeOverrides(i3);
                        UiModeManagerService uiModeManagerService6 = UiModeManagerService.this;
                        if (uiModeManagerService6.mComputedNightMode != z) {
                            uiModeManagerService6.mWaitForDeviceInactive = false;
                        }
                        uiModeManagerService6.mComputedNightMode = z;
                        Settings.System.putIntForUser(
                                UiModeManagerService.this.getContext().getContentResolver(),
                                "display_night_theme",
                                z ? 1 : 0,
                                UiModeManagerService.this.mCurrentUser);
                        LogWrapper.i(
                                "setNightModeActivated : "
                                        + z
                                        + " by "
                                        + callingPid
                                        + ", "
                                        + nameForUid
                                        + ", "
                                        + m100$$Nest$mgetPackageName
                                        + ", mNightMode "
                                        + UiModeManagerService.this.mNightMode
                                        + ", mNightModeCustomType "
                                        + UiModeManagerService.this.mNightModeCustomType);
                        UiModeManagerService.this.updateConfigurationLocked();
                        UiModeManagerService.this.applyConfigurationExternallyLocked();
                        UiModeManagerService uiModeManagerService42 = UiModeManagerService.this;
                        uiModeManagerService42.persistNightMode(
                                uiModeManagerService42.mCurrentUser);
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    } catch (Throwable th) {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        throw th;
                    }
                } catch (Throwable th2) {
                    throw th2;
                }
            }
            return true;
        }

        public final void setNightModeCustomType(int i) {
            setNightModeCustomType_enforcePermission();
            setNightModeInternal(3, i);
        }

        public final void setNightModeInternal(int i, int i2) {
            if (isNightModeLocked()
                    && UiModeManagerService.this
                                    .getContext()
                                    .checkCallingOrSelfPermission(
                                            "android.permission.MODIFY_DAY_NIGHT_MODE")
                            != 0) {
                Set set = UiModeManagerService.SUPPORTED_NIGHT_MODE_CUSTOM_TYPES;
                Slog.e(
                        "UiModeManager",
                        "Night mode locked, requires MODIFY_DAY_NIGHT_MODE permission");
                return;
            }
            if (i != 0 && i != 1 && i != 2) {
                if (i != 3) {
                    throw new IllegalArgumentException(
                            VibrationParam$1$$ExternalSyntheticOutline0.m(i, "Unknown mode: "));
                }
                if (!UiModeManagerService.SUPPORTED_NIGHT_MODE_CUSTOM_TYPES.contains(
                        Integer.valueOf(i2))) {
                    throw new IllegalArgumentException(
                            VibrationParam$1$$ExternalSyntheticOutline0.m(
                                    i2, "Can't set the custom type to "));
                }
            }
            if (i != 1
                    && !UiModeManagerService.m98$$Nest$mcanSetNightMode(
                            UiModeManagerService.this)) {
                DirEncryptService$$ExternalSyntheticOutline0.m(
                        i, "Ignore setNightMode : ", "UiModeManager");
                return;
            }
            UiModeManagerService.m99$$Nest$menforceValidCallingUser(UiModeManagerService.this, -2);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (UiModeManagerService.this.mLock) {
                    try {
                        UiModeManagerService uiModeManagerService = UiModeManagerService.this;
                        int i3 = uiModeManagerService.mNightMode.mNightModeValue;
                        if (i3 != i || uiModeManagerService.mNightModeCustomType != i2) {
                            if (i3 == 0 || i3 == 3) {
                                uiModeManagerService.unregisterDeviceInactiveListenerLocked();
                                UiModeManagerService uiModeManagerService2 =
                                        UiModeManagerService.this;
                                uiModeManagerService2.mAlarmManager.cancel(
                                        uiModeManagerService2.mCustomTimeListener);
                            }
                            UiModeManagerService uiModeManagerService3 = UiModeManagerService.this;
                            uiModeManagerService3.mNightModeCustomType = i == 3 ? i2 : -1;
                            uiModeManagerService3.mNightMode.set(i);
                            UiModeManagerService uiModeManagerService4 = UiModeManagerService.this;
                            uiModeManagerService4.mAttentionModeThemeOverlay = 1000;
                            uiModeManagerService4.resetNightModeOverrideLocked();
                            UiModeManagerService.this.persistNightMode(-2);
                            LogWrapper.i(
                                    "setNightMode : "
                                            + i
                                            + ", customModeType : "
                                            + i2
                                            + ", by "
                                            + Binder.getCallingPid()
                                            + ", "
                                            + UiModeManagerService.this
                                                    .getContext()
                                                    .getPackageManager()
                                                    .getNameForUid(Binder.getCallingUid())
                                            + ", "
                                            + UiModeManagerService.m100$$Nest$mgetPackageName(
                                                    UiModeManagerService.this));
                            UiModeManagerService.this.persistNightModeSettingDB(-2);
                            UiModeManagerService uiModeManagerService5 = UiModeManagerService.this;
                            int i4 = uiModeManagerService5.mNightMode.mNightModeValue;
                            if ((i4 == 0 || i4 == 3)
                                    && !uiModeManagerService5
                                            .shouldApplyAutomaticChangesImmediately()) {
                                UiModeManagerService uiModeManagerService6 =
                                        UiModeManagerService.this;
                                if (uiModeManagerService6.mNightMode.mNightModeValue == 3) {
                                    if (uiModeManagerService6.mComputedNightMode
                                            ^ TimeUtils.isTimeBetween(
                                                    LocalTime.now(),
                                                    uiModeManagerService6
                                                            .mCustomAutoNightModeStartMilliseconds,
                                                    uiModeManagerService6
                                                            .mCustomAutoNightModeEndMilliseconds)) {}
                                }
                                UiModeManagerService uiModeManagerService7 =
                                        UiModeManagerService.this;
                                if (!uiModeManagerService7.mAutoModeChangeImmediately) {
                                    uiModeManagerService7.registerDeviceInactiveListenerLocked();
                                }
                            }
                            UiModeManagerService.this.unregisterDeviceInactiveListenerLocked();
                            UiModeManagerService.this.updateLocked(0, 0);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void setNightPriorityAllowedPackagesFromScpm(List list) {
            if (UiModeManagerService.this
                            .getContext()
                            .checkCallingOrSelfPermission(
                                    "android.permission.INTERACT_ACROSS_USERS_FULL")
                    != 0) {
                Set set = UiModeManagerService.SUPPORTED_NIGHT_MODE_CUSTOM_TYPES;
                Slog.e(
                        "UiModeManager",
                        "setNightPriorityAllowedPackagesFromScpm requires"
                            + " INTERACT_ACROSS_USERS_FULL permission");
                return;
            }
            if (list == null) {
                Set set2 = UiModeManagerService.SUPPORTED_NIGHT_MODE_CUSTOM_TYPES;
                Slog.e(
                        "UiModeManager",
                        "setNightPriorityAllowedPackagesFromScpm received invalid list");
                return;
            }
            synchronized (UiModeManagerService.this.mNightPriorityAllowedPackagesFromScpm) {
                Set set3 = UiModeManagerService.SUPPORTED_NIGHT_MODE_CUSTOM_TYPES;
                Slog.d("UiModeManager", "setNightPriorityAllowedPackagesFromScpm " + list.size());
                ((ArrayList) UiModeManagerService.this.mNightPriorityAllowedPackagesFromScpm)
                        .clear();
                ((ArrayList) UiModeManagerService.this.mNightPriorityAllowedPackagesFromScpm)
                        .addAll(list);
            }
        }

        public final void setPackageNightMode(String str, int i, int i2) {
            if (UiModeManagerService.this
                            .getContext()
                            .checkCallingOrSelfPermission(
                                    "android.permission.MODIFY_DAY_NIGHT_MODE")
                    == 0) {
                UiModeManagerService.m103$$Nest$msetPackageNightModeInnerLocked(
                        UiModeManagerService.this, str, i, i2);
            } else {
                Set set = UiModeManagerService.SUPPORTED_NIGHT_MODE_CUSTOM_TYPES;
                Slog.e(
                        "UiModeManager",
                        "setPackageNightMode requires MODIFY_DAY_NIGHT_MODE permission");
            }
        }
    }

    /* renamed from: -$$Nest$massertLegit, reason: not valid java name */
    public static void m97$$Nest$massertLegit(
            UiModeManagerService uiModeManagerService, String str) {
        uiModeManagerService.mInjector.getClass();
        int callingUid = Binder.getCallingUid();
        int userId = UserHandle.getUserId(callingUid);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean z = false;
        try {
            if (uiModeManagerService
                            .getContext()
                            .getPackageManager()
                            .getPackageUidAsUser(str, userId)
                    == callingUid) {
                z = true;
            }
        } catch (PackageManager.NameNotFoundException unused) {
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        if (!z) {
            throw new SecurityException(
                    XmlUtils$$ExternalSyntheticOutline0.m(
                            "Caller claimed bogus packageName: ", str, "."));
        }
    }

    /* renamed from: -$$Nest$mcanSetNightMode, reason: not valid java name */
    public static boolean m98$$Nest$mcanSetNightMode(UiModeManagerService uiModeManagerService) {
        String string =
                Settings.System.getString(
                        uiModeManagerService.getContext().getContentResolver(),
                        "current_sec_active_themepackage");
        if (string != null && !string.isEmpty()) {
            r1 =
                    Settings.System.getInt(
                                    uiModeManagerService.getContext().getContentResolver(),
                                    "current_theme_support_night_mode",
                                    0)
                            == 1;
            if (!r1) {
                Log.i(
                        "UiModeManager",
                        "Cannot set night mode because current theme does not support night mode");
            }
        }
        return r1;
    }

    /* renamed from: -$$Nest$menforceValidCallingUser, reason: not valid java name */
    public static void m99$$Nest$menforceValidCallingUser(
            UiModeManagerService uiModeManagerService, int i) {
        uiModeManagerService.getClass();
        if (UserManager.isVisibleBackgroundUsersEnabled()) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            if (i != 0) {
                try {
                    if (i != uiModeManagerService.mCurrentUser
                            && !UserManagerService.getInstance()
                                    .isSameProfileGroup(i, uiModeManagerService.mCurrentUser)) {
                        throw new SecurityException(
                                "Calling user is not valid for level-1 compatibility in MUMD."
                                    + " callingUserId="
                                        + i
                                        + " currentUserId="
                                        + uiModeManagerService.mCurrentUser);
                    }
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }
    }

    /* renamed from: -$$Nest$mgetPackageName, reason: not valid java name */
    public static String m100$$Nest$mgetPackageName(UiModeManagerService uiModeManagerService) {
        uiModeManagerService.getClass();
        try {
            return ActivityManager.getService().getPackageFromAppProcesses(Binder.getCallingPid());
        } catch (Exception e) {
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                    e, "Exception in getPackageNameFromAppProcesses : ", "UiModeManager");
            return "none";
        }
    }

    /* renamed from: -$$Nest$monCustomTimeUpdated, reason: not valid java name */
    public static void m101$$Nest$monCustomTimeUpdated(
            UiModeManagerService uiModeManagerService, int i) {
        uiModeManagerService.persistNightMode(i);
        AnonymousClass1 anonymousClass1 = uiModeManagerService.mNightMode;
        if (anonymousClass1.mNightModeValue != 3) {
            return;
        }
        if (!uiModeManagerService.shouldApplyAutomaticChangesImmediately()
                && (anonymousClass1.mNightModeValue != 3
                        || !(TimeUtils.isTimeBetween(
                                        LocalTime.now(),
                                        uiModeManagerService.mCustomAutoNightModeStartMilliseconds,
                                        uiModeManagerService.mCustomAutoNightModeEndMilliseconds)
                                ^ uiModeManagerService.mComputedNightMode))) {
            uiModeManagerService.registerDeviceInactiveListenerLocked();
        } else {
            uiModeManagerService.unregisterDeviceInactiveListenerLocked();
            uiModeManagerService.updateLocked(0, 0);
        }
    }

    /* renamed from: -$$Nest$mreleaseProjectionUnchecked, reason: not valid java name */
    public static boolean m102$$Nest$mreleaseProjectionUnchecked(
            UiModeManagerService uiModeManagerService, int i, String str) {
        boolean z;
        List list;
        synchronized (uiModeManagerService.mLock) {
            try {
                SparseArray sparseArray = uiModeManagerService.mProjectionHolders;
                z = false;
                if (sparseArray != null && (list = (List) sparseArray.get(i)) != null) {
                    boolean z2 = false;
                    for (int size = list.size() - 1; size >= 0; size--) {
                        ProjectionHolder projectionHolder = (ProjectionHolder) list.get(size);
                        if (str.equals(projectionHolder.mPackageName)) {
                            projectionHolder.mBinder.unlinkToDeath(projectionHolder, 0);
                            Slog.d(
                                    "UiModeManager",
                                    "Projection type " + i + " released by " + str + ".");
                            list.remove(size);
                            z2 = true;
                        }
                    }
                    z = z2;
                }
                if (z) {
                    uiModeManagerService.onProjectionStateChangedLocked(i);
                } else {
                    Slog.w(
                            "UiModeManager",
                            str
                                    + " tried to release projection type "
                                    + i
                                    + " but was not set by that package.");
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return z;
    }

    /* renamed from: -$$Nest$msetPackageNightModeInnerLocked, reason: not valid java name */
    public static boolean m103$$Nest$msetPackageNightModeInnerLocked(
            UiModeManagerService uiModeManagerService, String str, int i, int i2) {
        uiModeManagerService.getClass();
        if (i2 != 0 && i2 != 2) {
            NandswapManager$$ExternalSyntheticOutline0.m(
                    i2,
                    "setPackageNightModeInnerLocked received unsupported mode: ",
                    "UiModeManager");
            return false;
        }
        if (!uiModeManagerService.isNightPriorityAllowedLocked(str)) {
            BootReceiver$$ExternalSyntheticOutline0.m(
                    "setPackageNightModeInnerLocked is not allowed for ", str, "UiModeManager");
            return false;
        }
        synchronized (uiModeManagerService.mNightPriorityAppliedPackages) {
            Slog.d(
                    "UiModeManager",
                    "setPackageNightModeInnerLocked m="
                            + i2
                            + " p="
                            + str
                            + " u="
                            + i
                            + " by "
                            + Binder.getCallingUid());
            PackageFeatureUserChange packageFeatureUserChange =
                    uiModeManagerService.mNightPriorityAppliedPackages;
            StringBuilder sb = new StringBuilder();
            sb.append(i2);
            sb.append("/");
            sb.append(Binder.getCallingUid());
            packageFeatureUserChange.putValue(str, i, sb.toString());
        }
        PackageConfigurationUpdaterImpl packageConfigurationUpdaterImpl =
                new PackageConfigurationUpdaterImpl(i, ActivityTaskManagerService.this, str);
        packageConfigurationUpdaterImpl.setNightMode(i2 == 2 ? 32 : 0);
        packageConfigurationUpdaterImpl.commit();
        int secureFolderId = SemPersonaManager.getSecureFolderId(uiModeManagerService.getContext());
        if (secureFolderId > 0 && i != secureFolderId && i == 0) {
            PackageConfigurationUpdaterImpl packageConfigurationUpdaterImpl2 =
                    new PackageConfigurationUpdaterImpl(
                            secureFolderId, ActivityTaskManagerService.this, str);
            packageConfigurationUpdaterImpl2.setNightMode(i2 == 2 ? 32 : 0);
            packageConfigurationUpdaterImpl2.commit();
        }
        return true;
    }

    public UiModeManagerService(Context context) {
        this(context, false, null, new Injector());
    }

    /* JADX WARN: Type inference failed for: r6v10, types: [com.android.server.UiModeManagerService$8] */
    /* JADX WARN: Type inference failed for: r6v14, types: [com.android.server.UiModeManagerService$2] */
    /* JADX WARN: Type inference failed for: r6v15, types: [com.android.server.UiModeManagerService$2] */
    /* JADX WARN: Type inference failed for: r6v16, types: [com.android.server.UiModeManagerService$2] */
    /* JADX WARN: Type inference failed for: r6v3, types: [com.android.server.UiModeManagerService$2] */
    /* JADX WARN: Type inference failed for: r6v4, types: [com.android.server.UiModeManagerService$2] */
    /* JADX WARN: Type inference failed for: r6v5, types: [com.android.server.UiModeManagerService$2] */
    /* JADX WARN: Type inference failed for: r6v6, types: [com.android.server.UiModeManagerService$5] */
    /* JADX WARN: Type inference failed for: r6v7, types: [com.android.server.UiModeManagerService$2] */
    /* JADX WARN: Type inference failed for: r6v8, types: [com.android.server.UiModeManagerService$2] */
    /* JADX WARN: Type inference failed for: r6v9, types: [com.android.server.UiModeManagerService$$ExternalSyntheticLambda2] */
    public UiModeManagerService(
            Context context, boolean z, TwilightManager twilightManager, Injector injector) {
        super(context);
        this.mLock = new Object();
        this.mDockState = 0;
        this.mLastBroadcastState = 0;
        AnonymousClass1 anonymousClass1 = new AnonymousClass1();
        anonymousClass1.mNightModeValue = 1;
        this.mNightMode = anonymousClass1;
        this.mNightModeCustomType = -1;
        this.mAttentionModeThemeOverlay = 1000;
        LocalTime of = LocalTime.of(19, 0);
        this.DEFAULT_CUSTOM_NIGHT_START_TIME = of;
        LocalTime of2 = LocalTime.of(7, 0);
        this.DEFAULT_CUSTOM_NIGHT_END_TIME = of2;
        this.mCustomAutoNightModeStartMilliseconds = of;
        this.mCustomAutoNightModeEndMilliseconds = of2;
        this.mCarModePackagePriority = new HashMap();
        this.mCarModeEnabled = false;
        this.mCharging = false;
        this.mPowerSave = false;
        this.mWaitForDeviceInactive = false;
        this.mLastBedtimeRequestedNightMode = false;
        this.mStartDreamImmediatelyOnDock = true;
        this.mDreamsDisabledByAmbientModeSuppression = false;
        this.mEnableCarDockLaunch = true;
        this.mUiModeLocked = false;
        this.mNightModeLocked = false;
        this.mCurUiMode = 0;
        this.mSetUiMode = 0;
        this.mHoldingConfiguration = false;
        Configuration configuration = new Configuration();
        this.mConfiguration = configuration;
        Handler handler = new Handler();
        this.mHandler = handler;
        this.mOverrideNightModeUser = 0;
        this.mLocalService = new LocalService();
        this.mUiModeManagerCallbacks = new SparseArray();
        this.mContrasts = new SparseArray();
        this.mAutoModeChangeImmediately = false;
        this.mIsNightModeRegistered = false;
        this.mDesktopModeEnabled = false;
        this.mNewDexModeEnabled = false;
        this.mToggleNewDexMode = false;
        final int i = 0;
        this.mResultReceiver =
                new BroadcastReceiver(
                        this) { // from class: com.android.server.UiModeManagerService.2
                    public final /* synthetic */ UiModeManagerService this$0;

                    {
                        this.this$0 = this;
                    }

                    private final void onReceive$com$android$server$UiModeManagerService$3(
                            Context context2, Intent intent) {
                        int intExtra = intent.getIntExtra("android.intent.extra.DOCK_STATE", 0);
                        UiModeManagerService uiModeManagerService = this.this$0;
                        synchronized (uiModeManagerService.mLock) {
                            try {
                                if (intExtra != uiModeManagerService.mDockState) {
                                    uiModeManagerService.mDockState = intExtra;
                                    uiModeManagerService.setCarModeLocked(0, 0, "", intExtra == 2);
                                    if (uiModeManagerService.mSystemReady) {
                                        uiModeManagerService.updateLocked(1, 0);
                                    }
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }

                    private final void onReceive$com$android$server$UiModeManagerService$4(
                            Context context2, Intent intent) {
                        String action = intent.getAction();
                        action.getClass();
                        if (action.equals("android.intent.action.BATTERY_CHANGED")) {
                            this.this$0.mCharging = intent.getIntExtra("plugged", 0) != 0;
                        }
                        synchronized (this.this$0.mLock) {
                            try {
                                UiModeManagerService uiModeManagerService = this.this$0;
                                if (uiModeManagerService.mSystemReady
                                        && (uiModeManagerService.mCarModeEnabled
                                                || UiModeManagerService.isDeskDockState(
                                                        uiModeManagerService.mDockState))) {
                                    this.this$0.updateLocked(0, 0);
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }

                    private final void onReceive$com$android$server$UiModeManagerService$6(
                            Context context2, Intent intent) {
                        synchronized (this.this$0.mLock) {
                            this.this$0.unregisterDeviceInactiveListenerLocked();
                            this.this$0.updateLocked(0, 0);
                        }
                    }

                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        int intExtra;
                        switch (i) {
                            case 0:
                                if (getResultCode() != -1) {
                                    return;
                                }
                                int intExtra2 = intent.getIntExtra("enableFlags", 0);
                                int intExtra3 = intent.getIntExtra("disableFlags", 0);
                                synchronized (this.this$0.mLock) {
                                    UiModeManagerService uiModeManagerService = this.this$0;
                                    String action = intent.getAction();
                                    uiModeManagerService.getClass();
                                    if (UiModeManager.ACTION_ENTER_CAR_MODE.equals(action)) {
                                        if (uiModeManagerService.mEnableCarDockLaunch
                                                && (intExtra2 & 1) != 0) {
                                            r0 = "android.intent.category.CAR_DOCK";
                                        }
                                    } else if (UiModeManager.ACTION_ENTER_DESK_MODE.equals(
                                            action)) {
                                        if ((intExtra2 & 1) != 0) {
                                            r0 = "android.intent.category.DESK_DOCK";
                                        }
                                    } else if ((intExtra3 & 1) != 0) {
                                        r0 = "android.intent.category.HOME";
                                    }
                                    uiModeManagerService
                                            .sendConfigurationAndStartDreamOrDockAppLocked(r0);
                                }
                                return;
                            case 1:
                                Uri data = intent.getData();
                                r0 = data != null ? data.getSchemeSpecificPart() : null;
                                if (r0 == null
                                        || (intExtra =
                                                        intent.getIntExtra(
                                                                "android.intent.extra.user_handle",
                                                                -10000))
                                                == -10000) {
                                    return;
                                }
                                String action2 = intent.getAction();
                                if (!("android.intent.action.PACKAGE_ADDED".equals(action2)
                                                && intent.getBooleanExtra(
                                                        "android.intent.extra.REPLACING", false))
                                        && this.this$0.isNightPriorityAllowedLocked(r0)) {
                                    Set set =
                                            UiModeManagerService.SUPPORTED_NIGHT_MODE_CUSTOM_TYPES;
                                    UiModeManagerService$13$$ExternalSyntheticOutline0.m(
                                            InitialConfiguration$$ExternalSyntheticOutline0.m(
                                                    "onReceive : ",
                                                    action2,
                                                    ", package : ",
                                                    r0,
                                                    ", "),
                                            intExtra,
                                            "UiModeManager");
                                    UiModeManagerService.m103$$Nest$msetPackageNightModeInnerLocked(
                                            this.this$0, r0, intExtra, 2);
                                    synchronized (this.this$0.mPackagesNeedToShowDialog) {
                                        this.this$0.mPackagesNeedToShowDialog.putValue(
                                                r0, intExtra, Boolean.TRUE);
                                    }
                                    return;
                                }
                                return;
                            case 2:
                                UiModeManagerService uiModeManagerService2 = this.this$0;
                                if (uiModeManagerService2.mNightMode.mNightModeValue == 0) {
                                    Settings.Secure.putIntForUser(
                                            uiModeManagerService2.getContext().getContentResolver(),
                                            "ui_night_mode_last_computed",
                                            uiModeManagerService2.mComputedNightMode ? 1 : 0,
                                            uiModeManagerService2.mCurrentUser);
                                    return;
                                }
                                return;
                            case 3:
                                if (Arrays.asList(
                                                "ui_night_mode",
                                                "dark_theme_custom_start_time",
                                                "dark_theme_custom_end_time")
                                        .contains(
                                                intent.getExtras()
                                                        .getCharSequence("setting_name"))) {
                                    synchronized (this.this$0.mLock) {
                                        this.this$0.updateNightModeFromSettingsLocked(
                                                context2,
                                                context2.getResources(),
                                                UserHandle.getCallingUserId());
                                        this.this$0.updateConfigurationLocked();
                                        this.this$0.applyConfigurationExternallyLocked();
                                    }
                                    return;
                                }
                                return;
                            case 4:
                                onReceive$com$android$server$UiModeManagerService$3(
                                        context2, intent);
                                return;
                            case 5:
                                onReceive$com$android$server$UiModeManagerService$4(
                                        context2, intent);
                                return;
                            case 6:
                                onReceive$com$android$server$UiModeManagerService$6(
                                        context2, intent);
                                return;
                            default:
                                synchronized (this.this$0.mLock) {
                                    UiModeManagerService uiModeManagerService3 = this.this$0;
                                    if (uiModeManagerService3.mNightMode.mNightModeValue == 3) {
                                        if (uiModeManagerService3
                                                .shouldApplyAutomaticChangesImmediately()) {
                                            uiModeManagerService3.updateLocked(0, 0);
                                        } else {
                                            uiModeManagerService3
                                                    .registerDeviceInactiveListenerLocked();
                                        }
                                        uiModeManagerService3.scheduleNextCustomTimeListener();
                                    }
                                }
                                return;
                        }
                    }
                };
        final int i2 = 4;
        this.mDockModeReceiver =
                new BroadcastReceiver(
                        this) { // from class: com.android.server.UiModeManagerService.2
                    public final /* synthetic */ UiModeManagerService this$0;

                    {
                        this.this$0 = this;
                    }

                    private final void onReceive$com$android$server$UiModeManagerService$3(
                            Context context2, Intent intent) {
                        int intExtra = intent.getIntExtra("android.intent.extra.DOCK_STATE", 0);
                        UiModeManagerService uiModeManagerService = this.this$0;
                        synchronized (uiModeManagerService.mLock) {
                            try {
                                if (intExtra != uiModeManagerService.mDockState) {
                                    uiModeManagerService.mDockState = intExtra;
                                    uiModeManagerService.setCarModeLocked(0, 0, "", intExtra == 2);
                                    if (uiModeManagerService.mSystemReady) {
                                        uiModeManagerService.updateLocked(1, 0);
                                    }
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }

                    private final void onReceive$com$android$server$UiModeManagerService$4(
                            Context context2, Intent intent) {
                        String action = intent.getAction();
                        action.getClass();
                        if (action.equals("android.intent.action.BATTERY_CHANGED")) {
                            this.this$0.mCharging = intent.getIntExtra("plugged", 0) != 0;
                        }
                        synchronized (this.this$0.mLock) {
                            try {
                                UiModeManagerService uiModeManagerService = this.this$0;
                                if (uiModeManagerService.mSystemReady
                                        && (uiModeManagerService.mCarModeEnabled
                                                || UiModeManagerService.isDeskDockState(
                                                        uiModeManagerService.mDockState))) {
                                    this.this$0.updateLocked(0, 0);
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }

                    private final void onReceive$com$android$server$UiModeManagerService$6(
                            Context context2, Intent intent) {
                        synchronized (this.this$0.mLock) {
                            this.this$0.unregisterDeviceInactiveListenerLocked();
                            this.this$0.updateLocked(0, 0);
                        }
                    }

                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        int intExtra;
                        switch (i2) {
                            case 0:
                                if (getResultCode() != -1) {
                                    return;
                                }
                                int intExtra2 = intent.getIntExtra("enableFlags", 0);
                                int intExtra3 = intent.getIntExtra("disableFlags", 0);
                                synchronized (this.this$0.mLock) {
                                    UiModeManagerService uiModeManagerService = this.this$0;
                                    String action = intent.getAction();
                                    uiModeManagerService.getClass();
                                    if (UiModeManager.ACTION_ENTER_CAR_MODE.equals(action)) {
                                        if (uiModeManagerService.mEnableCarDockLaunch
                                                && (intExtra2 & 1) != 0) {
                                            r0 = "android.intent.category.CAR_DOCK";
                                        }
                                    } else if (UiModeManager.ACTION_ENTER_DESK_MODE.equals(
                                            action)) {
                                        if ((intExtra2 & 1) != 0) {
                                            r0 = "android.intent.category.DESK_DOCK";
                                        }
                                    } else if ((intExtra3 & 1) != 0) {
                                        r0 = "android.intent.category.HOME";
                                    }
                                    uiModeManagerService
                                            .sendConfigurationAndStartDreamOrDockAppLocked(r0);
                                }
                                return;
                            case 1:
                                Uri data = intent.getData();
                                r0 = data != null ? data.getSchemeSpecificPart() : null;
                                if (r0 == null
                                        || (intExtra =
                                                        intent.getIntExtra(
                                                                "android.intent.extra.user_handle",
                                                                -10000))
                                                == -10000) {
                                    return;
                                }
                                String action2 = intent.getAction();
                                if (!("android.intent.action.PACKAGE_ADDED".equals(action2)
                                                && intent.getBooleanExtra(
                                                        "android.intent.extra.REPLACING", false))
                                        && this.this$0.isNightPriorityAllowedLocked(r0)) {
                                    Set set =
                                            UiModeManagerService.SUPPORTED_NIGHT_MODE_CUSTOM_TYPES;
                                    UiModeManagerService$13$$ExternalSyntheticOutline0.m(
                                            InitialConfiguration$$ExternalSyntheticOutline0.m(
                                                    "onReceive : ",
                                                    action2,
                                                    ", package : ",
                                                    r0,
                                                    ", "),
                                            intExtra,
                                            "UiModeManager");
                                    UiModeManagerService.m103$$Nest$msetPackageNightModeInnerLocked(
                                            this.this$0, r0, intExtra, 2);
                                    synchronized (this.this$0.mPackagesNeedToShowDialog) {
                                        this.this$0.mPackagesNeedToShowDialog.putValue(
                                                r0, intExtra, Boolean.TRUE);
                                    }
                                    return;
                                }
                                return;
                            case 2:
                                UiModeManagerService uiModeManagerService2 = this.this$0;
                                if (uiModeManagerService2.mNightMode.mNightModeValue == 0) {
                                    Settings.Secure.putIntForUser(
                                            uiModeManagerService2.getContext().getContentResolver(),
                                            "ui_night_mode_last_computed",
                                            uiModeManagerService2.mComputedNightMode ? 1 : 0,
                                            uiModeManagerService2.mCurrentUser);
                                    return;
                                }
                                return;
                            case 3:
                                if (Arrays.asList(
                                                "ui_night_mode",
                                                "dark_theme_custom_start_time",
                                                "dark_theme_custom_end_time")
                                        .contains(
                                                intent.getExtras()
                                                        .getCharSequence("setting_name"))) {
                                    synchronized (this.this$0.mLock) {
                                        this.this$0.updateNightModeFromSettingsLocked(
                                                context2,
                                                context2.getResources(),
                                                UserHandle.getCallingUserId());
                                        this.this$0.updateConfigurationLocked();
                                        this.this$0.applyConfigurationExternallyLocked();
                                    }
                                    return;
                                }
                                return;
                            case 4:
                                onReceive$com$android$server$UiModeManagerService$3(
                                        context2, intent);
                                return;
                            case 5:
                                onReceive$com$android$server$UiModeManagerService$4(
                                        context2, intent);
                                return;
                            case 6:
                                onReceive$com$android$server$UiModeManagerService$6(
                                        context2, intent);
                                return;
                            default:
                                synchronized (this.this$0.mLock) {
                                    UiModeManagerService uiModeManagerService3 = this.this$0;
                                    if (uiModeManagerService3.mNightMode.mNightModeValue == 3) {
                                        if (uiModeManagerService3
                                                .shouldApplyAutomaticChangesImmediately()) {
                                            uiModeManagerService3.updateLocked(0, 0);
                                        } else {
                                            uiModeManagerService3
                                                    .registerDeviceInactiveListenerLocked();
                                        }
                                        uiModeManagerService3.scheduleNextCustomTimeListener();
                                    }
                                }
                                return;
                        }
                    }
                };
        final int i3 = 5;
        this.mBatteryReceiver =
                new BroadcastReceiver(
                        this) { // from class: com.android.server.UiModeManagerService.2
                    public final /* synthetic */ UiModeManagerService this$0;

                    {
                        this.this$0 = this;
                    }

                    private final void onReceive$com$android$server$UiModeManagerService$3(
                            Context context2, Intent intent) {
                        int intExtra = intent.getIntExtra("android.intent.extra.DOCK_STATE", 0);
                        UiModeManagerService uiModeManagerService = this.this$0;
                        synchronized (uiModeManagerService.mLock) {
                            try {
                                if (intExtra != uiModeManagerService.mDockState) {
                                    uiModeManagerService.mDockState = intExtra;
                                    uiModeManagerService.setCarModeLocked(0, 0, "", intExtra == 2);
                                    if (uiModeManagerService.mSystemReady) {
                                        uiModeManagerService.updateLocked(1, 0);
                                    }
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }

                    private final void onReceive$com$android$server$UiModeManagerService$4(
                            Context context2, Intent intent) {
                        String action = intent.getAction();
                        action.getClass();
                        if (action.equals("android.intent.action.BATTERY_CHANGED")) {
                            this.this$0.mCharging = intent.getIntExtra("plugged", 0) != 0;
                        }
                        synchronized (this.this$0.mLock) {
                            try {
                                UiModeManagerService uiModeManagerService = this.this$0;
                                if (uiModeManagerService.mSystemReady
                                        && (uiModeManagerService.mCarModeEnabled
                                                || UiModeManagerService.isDeskDockState(
                                                        uiModeManagerService.mDockState))) {
                                    this.this$0.updateLocked(0, 0);
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }

                    private final void onReceive$com$android$server$UiModeManagerService$6(
                            Context context2, Intent intent) {
                        synchronized (this.this$0.mLock) {
                            this.this$0.unregisterDeviceInactiveListenerLocked();
                            this.this$0.updateLocked(0, 0);
                        }
                    }

                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        int intExtra;
                        switch (i3) {
                            case 0:
                                if (getResultCode() != -1) {
                                    return;
                                }
                                int intExtra2 = intent.getIntExtra("enableFlags", 0);
                                int intExtra3 = intent.getIntExtra("disableFlags", 0);
                                synchronized (this.this$0.mLock) {
                                    UiModeManagerService uiModeManagerService = this.this$0;
                                    String action = intent.getAction();
                                    uiModeManagerService.getClass();
                                    if (UiModeManager.ACTION_ENTER_CAR_MODE.equals(action)) {
                                        if (uiModeManagerService.mEnableCarDockLaunch
                                                && (intExtra2 & 1) != 0) {
                                            r0 = "android.intent.category.CAR_DOCK";
                                        }
                                    } else if (UiModeManager.ACTION_ENTER_DESK_MODE.equals(
                                            action)) {
                                        if ((intExtra2 & 1) != 0) {
                                            r0 = "android.intent.category.DESK_DOCK";
                                        }
                                    } else if ((intExtra3 & 1) != 0) {
                                        r0 = "android.intent.category.HOME";
                                    }
                                    uiModeManagerService
                                            .sendConfigurationAndStartDreamOrDockAppLocked(r0);
                                }
                                return;
                            case 1:
                                Uri data = intent.getData();
                                r0 = data != null ? data.getSchemeSpecificPart() : null;
                                if (r0 == null
                                        || (intExtra =
                                                        intent.getIntExtra(
                                                                "android.intent.extra.user_handle",
                                                                -10000))
                                                == -10000) {
                                    return;
                                }
                                String action2 = intent.getAction();
                                if (!("android.intent.action.PACKAGE_ADDED".equals(action2)
                                                && intent.getBooleanExtra(
                                                        "android.intent.extra.REPLACING", false))
                                        && this.this$0.isNightPriorityAllowedLocked(r0)) {
                                    Set set =
                                            UiModeManagerService.SUPPORTED_NIGHT_MODE_CUSTOM_TYPES;
                                    UiModeManagerService$13$$ExternalSyntheticOutline0.m(
                                            InitialConfiguration$$ExternalSyntheticOutline0.m(
                                                    "onReceive : ",
                                                    action2,
                                                    ", package : ",
                                                    r0,
                                                    ", "),
                                            intExtra,
                                            "UiModeManager");
                                    UiModeManagerService.m103$$Nest$msetPackageNightModeInnerLocked(
                                            this.this$0, r0, intExtra, 2);
                                    synchronized (this.this$0.mPackagesNeedToShowDialog) {
                                        this.this$0.mPackagesNeedToShowDialog.putValue(
                                                r0, intExtra, Boolean.TRUE);
                                    }
                                    return;
                                }
                                return;
                            case 2:
                                UiModeManagerService uiModeManagerService2 = this.this$0;
                                if (uiModeManagerService2.mNightMode.mNightModeValue == 0) {
                                    Settings.Secure.putIntForUser(
                                            uiModeManagerService2.getContext().getContentResolver(),
                                            "ui_night_mode_last_computed",
                                            uiModeManagerService2.mComputedNightMode ? 1 : 0,
                                            uiModeManagerService2.mCurrentUser);
                                    return;
                                }
                                return;
                            case 3:
                                if (Arrays.asList(
                                                "ui_night_mode",
                                                "dark_theme_custom_start_time",
                                                "dark_theme_custom_end_time")
                                        .contains(
                                                intent.getExtras()
                                                        .getCharSequence("setting_name"))) {
                                    synchronized (this.this$0.mLock) {
                                        this.this$0.updateNightModeFromSettingsLocked(
                                                context2,
                                                context2.getResources(),
                                                UserHandle.getCallingUserId());
                                        this.this$0.updateConfigurationLocked();
                                        this.this$0.applyConfigurationExternallyLocked();
                                    }
                                    return;
                                }
                                return;
                            case 4:
                                onReceive$com$android$server$UiModeManagerService$3(
                                        context2, intent);
                                return;
                            case 5:
                                onReceive$com$android$server$UiModeManagerService$4(
                                        context2, intent);
                                return;
                            case 6:
                                onReceive$com$android$server$UiModeManagerService$6(
                                        context2, intent);
                                return;
                            default:
                                synchronized (this.this$0.mLock) {
                                    UiModeManagerService uiModeManagerService3 = this.this$0;
                                    if (uiModeManagerService3.mNightMode.mNightModeValue == 3) {
                                        if (uiModeManagerService3
                                                .shouldApplyAutomaticChangesImmediately()) {
                                            uiModeManagerService3.updateLocked(0, 0);
                                        } else {
                                            uiModeManagerService3
                                                    .registerDeviceInactiveListenerLocked();
                                        }
                                        uiModeManagerService3.scheduleNextCustomTimeListener();
                                    }
                                }
                                return;
                        }
                    }
                };
        this.mTwilightListener =
                new TwilightListener() { // from class: com.android.server.UiModeManagerService.5
                    @Override // com.android.server.twilight.TwilightListener
                    public final void onTwilightStateChanged(TwilightState twilightState) {
                        synchronized (UiModeManagerService.this.mLock) {
                            try {
                                UiModeManagerService uiModeManagerService =
                                        UiModeManagerService.this;
                                if (uiModeManagerService.mNightMode.mNightModeValue == 0
                                        && uiModeManagerService.mSystemReady) {
                                    if (!uiModeManagerService
                                            .shouldApplyAutomaticChangesImmediately()) {
                                        UiModeManagerService uiModeManagerService2 =
                                                UiModeManagerService.this;
                                        if (!uiModeManagerService2.mAutoModeChangeImmediately) {
                                            uiModeManagerService2
                                                    .registerDeviceInactiveListenerLocked();
                                        }
                                    }
                                    if (UiModeManagerService.this.mAutoModeChangeImmediately) {
                                        Set set =
                                                UiModeManagerService
                                                        .SUPPORTED_NIGHT_MODE_CUSTOM_TYPES;
                                        LogWrapper.i(
                                                "Twilight state is changed immediately after"
                                                    + " MODE_NIGHT_AUTO setting");
                                    }
                                    UiModeManagerService.this.updateLocked(0, 0);
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }
                };
        final int i4 = 6;
        this.mDeviceInactiveListener =
                new BroadcastReceiver(
                        this) { // from class: com.android.server.UiModeManagerService.2
                    public final /* synthetic */ UiModeManagerService this$0;

                    {
                        this.this$0 = this;
                    }

                    private final void onReceive$com$android$server$UiModeManagerService$3(
                            Context context2, Intent intent) {
                        int intExtra = intent.getIntExtra("android.intent.extra.DOCK_STATE", 0);
                        UiModeManagerService uiModeManagerService = this.this$0;
                        synchronized (uiModeManagerService.mLock) {
                            try {
                                if (intExtra != uiModeManagerService.mDockState) {
                                    uiModeManagerService.mDockState = intExtra;
                                    uiModeManagerService.setCarModeLocked(0, 0, "", intExtra == 2);
                                    if (uiModeManagerService.mSystemReady) {
                                        uiModeManagerService.updateLocked(1, 0);
                                    }
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }

                    private final void onReceive$com$android$server$UiModeManagerService$4(
                            Context context2, Intent intent) {
                        String action = intent.getAction();
                        action.getClass();
                        if (action.equals("android.intent.action.BATTERY_CHANGED")) {
                            this.this$0.mCharging = intent.getIntExtra("plugged", 0) != 0;
                        }
                        synchronized (this.this$0.mLock) {
                            try {
                                UiModeManagerService uiModeManagerService = this.this$0;
                                if (uiModeManagerService.mSystemReady
                                        && (uiModeManagerService.mCarModeEnabled
                                                || UiModeManagerService.isDeskDockState(
                                                        uiModeManagerService.mDockState))) {
                                    this.this$0.updateLocked(0, 0);
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }

                    private final void onReceive$com$android$server$UiModeManagerService$6(
                            Context context2, Intent intent) {
                        synchronized (this.this$0.mLock) {
                            this.this$0.unregisterDeviceInactiveListenerLocked();
                            this.this$0.updateLocked(0, 0);
                        }
                    }

                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        int intExtra;
                        switch (i4) {
                            case 0:
                                if (getResultCode() != -1) {
                                    return;
                                }
                                int intExtra2 = intent.getIntExtra("enableFlags", 0);
                                int intExtra3 = intent.getIntExtra("disableFlags", 0);
                                synchronized (this.this$0.mLock) {
                                    UiModeManagerService uiModeManagerService = this.this$0;
                                    String action = intent.getAction();
                                    uiModeManagerService.getClass();
                                    if (UiModeManager.ACTION_ENTER_CAR_MODE.equals(action)) {
                                        if (uiModeManagerService.mEnableCarDockLaunch
                                                && (intExtra2 & 1) != 0) {
                                            r0 = "android.intent.category.CAR_DOCK";
                                        }
                                    } else if (UiModeManager.ACTION_ENTER_DESK_MODE.equals(
                                            action)) {
                                        if ((intExtra2 & 1) != 0) {
                                            r0 = "android.intent.category.DESK_DOCK";
                                        }
                                    } else if ((intExtra3 & 1) != 0) {
                                        r0 = "android.intent.category.HOME";
                                    }
                                    uiModeManagerService
                                            .sendConfigurationAndStartDreamOrDockAppLocked(r0);
                                }
                                return;
                            case 1:
                                Uri data = intent.getData();
                                r0 = data != null ? data.getSchemeSpecificPart() : null;
                                if (r0 == null
                                        || (intExtra =
                                                        intent.getIntExtra(
                                                                "android.intent.extra.user_handle",
                                                                -10000))
                                                == -10000) {
                                    return;
                                }
                                String action2 = intent.getAction();
                                if (!("android.intent.action.PACKAGE_ADDED".equals(action2)
                                                && intent.getBooleanExtra(
                                                        "android.intent.extra.REPLACING", false))
                                        && this.this$0.isNightPriorityAllowedLocked(r0)) {
                                    Set set =
                                            UiModeManagerService.SUPPORTED_NIGHT_MODE_CUSTOM_TYPES;
                                    UiModeManagerService$13$$ExternalSyntheticOutline0.m(
                                            InitialConfiguration$$ExternalSyntheticOutline0.m(
                                                    "onReceive : ",
                                                    action2,
                                                    ", package : ",
                                                    r0,
                                                    ", "),
                                            intExtra,
                                            "UiModeManager");
                                    UiModeManagerService.m103$$Nest$msetPackageNightModeInnerLocked(
                                            this.this$0, r0, intExtra, 2);
                                    synchronized (this.this$0.mPackagesNeedToShowDialog) {
                                        this.this$0.mPackagesNeedToShowDialog.putValue(
                                                r0, intExtra, Boolean.TRUE);
                                    }
                                    return;
                                }
                                return;
                            case 2:
                                UiModeManagerService uiModeManagerService2 = this.this$0;
                                if (uiModeManagerService2.mNightMode.mNightModeValue == 0) {
                                    Settings.Secure.putIntForUser(
                                            uiModeManagerService2.getContext().getContentResolver(),
                                            "ui_night_mode_last_computed",
                                            uiModeManagerService2.mComputedNightMode ? 1 : 0,
                                            uiModeManagerService2.mCurrentUser);
                                    return;
                                }
                                return;
                            case 3:
                                if (Arrays.asList(
                                                "ui_night_mode",
                                                "dark_theme_custom_start_time",
                                                "dark_theme_custom_end_time")
                                        .contains(
                                                intent.getExtras()
                                                        .getCharSequence("setting_name"))) {
                                    synchronized (this.this$0.mLock) {
                                        this.this$0.updateNightModeFromSettingsLocked(
                                                context2,
                                                context2.getResources(),
                                                UserHandle.getCallingUserId());
                                        this.this$0.updateConfigurationLocked();
                                        this.this$0.applyConfigurationExternallyLocked();
                                    }
                                    return;
                                }
                                return;
                            case 4:
                                onReceive$com$android$server$UiModeManagerService$3(
                                        context2, intent);
                                return;
                            case 5:
                                onReceive$com$android$server$UiModeManagerService$4(
                                        context2, intent);
                                return;
                            case 6:
                                onReceive$com$android$server$UiModeManagerService$6(
                                        context2, intent);
                                return;
                            default:
                                synchronized (this.this$0.mLock) {
                                    UiModeManagerService uiModeManagerService3 = this.this$0;
                                    if (uiModeManagerService3.mNightMode.mNightModeValue == 3) {
                                        if (uiModeManagerService3
                                                .shouldApplyAutomaticChangesImmediately()) {
                                            uiModeManagerService3.updateLocked(0, 0);
                                        } else {
                                            uiModeManagerService3
                                                    .registerDeviceInactiveListenerLocked();
                                        }
                                        uiModeManagerService3.scheduleNextCustomTimeListener();
                                    }
                                }
                                return;
                        }
                    }
                };
        final int i5 = 7;
        this.mOnTimeChangedHandler =
                new BroadcastReceiver(
                        this) { // from class: com.android.server.UiModeManagerService.2
                    public final /* synthetic */ UiModeManagerService this$0;

                    {
                        this.this$0 = this;
                    }

                    private final void onReceive$com$android$server$UiModeManagerService$3(
                            Context context2, Intent intent) {
                        int intExtra = intent.getIntExtra("android.intent.extra.DOCK_STATE", 0);
                        UiModeManagerService uiModeManagerService = this.this$0;
                        synchronized (uiModeManagerService.mLock) {
                            try {
                                if (intExtra != uiModeManagerService.mDockState) {
                                    uiModeManagerService.mDockState = intExtra;
                                    uiModeManagerService.setCarModeLocked(0, 0, "", intExtra == 2);
                                    if (uiModeManagerService.mSystemReady) {
                                        uiModeManagerService.updateLocked(1, 0);
                                    }
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }

                    private final void onReceive$com$android$server$UiModeManagerService$4(
                            Context context2, Intent intent) {
                        String action = intent.getAction();
                        action.getClass();
                        if (action.equals("android.intent.action.BATTERY_CHANGED")) {
                            this.this$0.mCharging = intent.getIntExtra("plugged", 0) != 0;
                        }
                        synchronized (this.this$0.mLock) {
                            try {
                                UiModeManagerService uiModeManagerService = this.this$0;
                                if (uiModeManagerService.mSystemReady
                                        && (uiModeManagerService.mCarModeEnabled
                                                || UiModeManagerService.isDeskDockState(
                                                        uiModeManagerService.mDockState))) {
                                    this.this$0.updateLocked(0, 0);
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }

                    private final void onReceive$com$android$server$UiModeManagerService$6(
                            Context context2, Intent intent) {
                        synchronized (this.this$0.mLock) {
                            this.this$0.unregisterDeviceInactiveListenerLocked();
                            this.this$0.updateLocked(0, 0);
                        }
                    }

                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        int intExtra;
                        switch (i5) {
                            case 0:
                                if (getResultCode() != -1) {
                                    return;
                                }
                                int intExtra2 = intent.getIntExtra("enableFlags", 0);
                                int intExtra3 = intent.getIntExtra("disableFlags", 0);
                                synchronized (this.this$0.mLock) {
                                    UiModeManagerService uiModeManagerService = this.this$0;
                                    String action = intent.getAction();
                                    uiModeManagerService.getClass();
                                    if (UiModeManager.ACTION_ENTER_CAR_MODE.equals(action)) {
                                        if (uiModeManagerService.mEnableCarDockLaunch
                                                && (intExtra2 & 1) != 0) {
                                            r0 = "android.intent.category.CAR_DOCK";
                                        }
                                    } else if (UiModeManager.ACTION_ENTER_DESK_MODE.equals(
                                            action)) {
                                        if ((intExtra2 & 1) != 0) {
                                            r0 = "android.intent.category.DESK_DOCK";
                                        }
                                    } else if ((intExtra3 & 1) != 0) {
                                        r0 = "android.intent.category.HOME";
                                    }
                                    uiModeManagerService
                                            .sendConfigurationAndStartDreamOrDockAppLocked(r0);
                                }
                                return;
                            case 1:
                                Uri data = intent.getData();
                                r0 = data != null ? data.getSchemeSpecificPart() : null;
                                if (r0 == null
                                        || (intExtra =
                                                        intent.getIntExtra(
                                                                "android.intent.extra.user_handle",
                                                                -10000))
                                                == -10000) {
                                    return;
                                }
                                String action2 = intent.getAction();
                                if (!("android.intent.action.PACKAGE_ADDED".equals(action2)
                                                && intent.getBooleanExtra(
                                                        "android.intent.extra.REPLACING", false))
                                        && this.this$0.isNightPriorityAllowedLocked(r0)) {
                                    Set set =
                                            UiModeManagerService.SUPPORTED_NIGHT_MODE_CUSTOM_TYPES;
                                    UiModeManagerService$13$$ExternalSyntheticOutline0.m(
                                            InitialConfiguration$$ExternalSyntheticOutline0.m(
                                                    "onReceive : ",
                                                    action2,
                                                    ", package : ",
                                                    r0,
                                                    ", "),
                                            intExtra,
                                            "UiModeManager");
                                    UiModeManagerService.m103$$Nest$msetPackageNightModeInnerLocked(
                                            this.this$0, r0, intExtra, 2);
                                    synchronized (this.this$0.mPackagesNeedToShowDialog) {
                                        this.this$0.mPackagesNeedToShowDialog.putValue(
                                                r0, intExtra, Boolean.TRUE);
                                    }
                                    return;
                                }
                                return;
                            case 2:
                                UiModeManagerService uiModeManagerService2 = this.this$0;
                                if (uiModeManagerService2.mNightMode.mNightModeValue == 0) {
                                    Settings.Secure.putIntForUser(
                                            uiModeManagerService2.getContext().getContentResolver(),
                                            "ui_night_mode_last_computed",
                                            uiModeManagerService2.mComputedNightMode ? 1 : 0,
                                            uiModeManagerService2.mCurrentUser);
                                    return;
                                }
                                return;
                            case 3:
                                if (Arrays.asList(
                                                "ui_night_mode",
                                                "dark_theme_custom_start_time",
                                                "dark_theme_custom_end_time")
                                        .contains(
                                                intent.getExtras()
                                                        .getCharSequence("setting_name"))) {
                                    synchronized (this.this$0.mLock) {
                                        this.this$0.updateNightModeFromSettingsLocked(
                                                context2,
                                                context2.getResources(),
                                                UserHandle.getCallingUserId());
                                        this.this$0.updateConfigurationLocked();
                                        this.this$0.applyConfigurationExternallyLocked();
                                    }
                                    return;
                                }
                                return;
                            case 4:
                                onReceive$com$android$server$UiModeManagerService$3(
                                        context2, intent);
                                return;
                            case 5:
                                onReceive$com$android$server$UiModeManagerService$4(
                                        context2, intent);
                                return;
                            case 6:
                                onReceive$com$android$server$UiModeManagerService$6(
                                        context2, intent);
                                return;
                            default:
                                synchronized (this.this$0.mLock) {
                                    UiModeManagerService uiModeManagerService3 = this.this$0;
                                    if (uiModeManagerService3.mNightMode.mNightModeValue == 3) {
                                        if (uiModeManagerService3
                                                .shouldApplyAutomaticChangesImmediately()) {
                                            uiModeManagerService3.updateLocked(0, 0);
                                        } else {
                                            uiModeManagerService3
                                                    .registerDeviceInactiveListenerLocked();
                                        }
                                        uiModeManagerService3.scheduleNextCustomTimeListener();
                                    }
                                }
                                return;
                        }
                    }
                };
        this.mCustomTimeListener =
                new AlarmManager
                        .OnAlarmListener() { // from class:
                                             // com.android.server.UiModeManagerService$$ExternalSyntheticLambda2
                    @Override // android.app.AlarmManager.OnAlarmListener
                    public final void onAlarm() {
                        UiModeManagerService uiModeManagerService = UiModeManagerService.this;
                        synchronized (uiModeManagerService.mLock) {
                            if (uiModeManagerService.mNightMode.mNightModeValue == 3) {
                                if (uiModeManagerService.shouldApplyAutomaticChangesImmediately()) {
                                    uiModeManagerService.updateLocked(0, 0);
                                } else {
                                    uiModeManagerService.registerDeviceInactiveListenerLocked();
                                }
                                uiModeManagerService.scheduleNextCustomTimeListener();
                            }
                        }
                    }
                };
        this.mVrStateCallbacks =
                new IVrStateCallbacks
                        .Stub() { // from class: com.android.server.UiModeManagerService.8
                    public final void onVrStateChanged(boolean z2) {
                        synchronized (UiModeManagerService.this.mLock) {
                            try {
                                UiModeManagerService uiModeManagerService =
                                        UiModeManagerService.this;
                                uiModeManagerService.mVrHeadset = z2;
                                if (uiModeManagerService.mSystemReady) {
                                    uiModeManagerService.updateLocked(0, 0);
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }
                };
        this.mSetupWizardObserver = new AnonymousClass9(this, handler, 0);
        this.mDarkThemeObserver = new AnonymousClass9(this, handler, 1);
        this.mContrastObserver = new AnonymousClass9(this, handler, 2);
        final int i6 = 1;
        this.mOnPackageAdded =
                new BroadcastReceiver(
                        this) { // from class: com.android.server.UiModeManagerService.2
                    public final /* synthetic */ UiModeManagerService this$0;

                    {
                        this.this$0 = this;
                    }

                    private final void onReceive$com$android$server$UiModeManagerService$3(
                            Context context2, Intent intent) {
                        int intExtra = intent.getIntExtra("android.intent.extra.DOCK_STATE", 0);
                        UiModeManagerService uiModeManagerService = this.this$0;
                        synchronized (uiModeManagerService.mLock) {
                            try {
                                if (intExtra != uiModeManagerService.mDockState) {
                                    uiModeManagerService.mDockState = intExtra;
                                    uiModeManagerService.setCarModeLocked(0, 0, "", intExtra == 2);
                                    if (uiModeManagerService.mSystemReady) {
                                        uiModeManagerService.updateLocked(1, 0);
                                    }
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }

                    private final void onReceive$com$android$server$UiModeManagerService$4(
                            Context context2, Intent intent) {
                        String action = intent.getAction();
                        action.getClass();
                        if (action.equals("android.intent.action.BATTERY_CHANGED")) {
                            this.this$0.mCharging = intent.getIntExtra("plugged", 0) != 0;
                        }
                        synchronized (this.this$0.mLock) {
                            try {
                                UiModeManagerService uiModeManagerService = this.this$0;
                                if (uiModeManagerService.mSystemReady
                                        && (uiModeManagerService.mCarModeEnabled
                                                || UiModeManagerService.isDeskDockState(
                                                        uiModeManagerService.mDockState))) {
                                    this.this$0.updateLocked(0, 0);
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }

                    private final void onReceive$com$android$server$UiModeManagerService$6(
                            Context context2, Intent intent) {
                        synchronized (this.this$0.mLock) {
                            this.this$0.unregisterDeviceInactiveListenerLocked();
                            this.this$0.updateLocked(0, 0);
                        }
                    }

                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        int intExtra;
                        switch (i6) {
                            case 0:
                                if (getResultCode() != -1) {
                                    return;
                                }
                                int intExtra2 = intent.getIntExtra("enableFlags", 0);
                                int intExtra3 = intent.getIntExtra("disableFlags", 0);
                                synchronized (this.this$0.mLock) {
                                    UiModeManagerService uiModeManagerService = this.this$0;
                                    String action = intent.getAction();
                                    uiModeManagerService.getClass();
                                    if (UiModeManager.ACTION_ENTER_CAR_MODE.equals(action)) {
                                        if (uiModeManagerService.mEnableCarDockLaunch
                                                && (intExtra2 & 1) != 0) {
                                            r0 = "android.intent.category.CAR_DOCK";
                                        }
                                    } else if (UiModeManager.ACTION_ENTER_DESK_MODE.equals(
                                            action)) {
                                        if ((intExtra2 & 1) != 0) {
                                            r0 = "android.intent.category.DESK_DOCK";
                                        }
                                    } else if ((intExtra3 & 1) != 0) {
                                        r0 = "android.intent.category.HOME";
                                    }
                                    uiModeManagerService
                                            .sendConfigurationAndStartDreamOrDockAppLocked(r0);
                                }
                                return;
                            case 1:
                                Uri data = intent.getData();
                                r0 = data != null ? data.getSchemeSpecificPart() : null;
                                if (r0 == null
                                        || (intExtra =
                                                        intent.getIntExtra(
                                                                "android.intent.extra.user_handle",
                                                                -10000))
                                                == -10000) {
                                    return;
                                }
                                String action2 = intent.getAction();
                                if (!("android.intent.action.PACKAGE_ADDED".equals(action2)
                                                && intent.getBooleanExtra(
                                                        "android.intent.extra.REPLACING", false))
                                        && this.this$0.isNightPriorityAllowedLocked(r0)) {
                                    Set set =
                                            UiModeManagerService.SUPPORTED_NIGHT_MODE_CUSTOM_TYPES;
                                    UiModeManagerService$13$$ExternalSyntheticOutline0.m(
                                            InitialConfiguration$$ExternalSyntheticOutline0.m(
                                                    "onReceive : ",
                                                    action2,
                                                    ", package : ",
                                                    r0,
                                                    ", "),
                                            intExtra,
                                            "UiModeManager");
                                    UiModeManagerService.m103$$Nest$msetPackageNightModeInnerLocked(
                                            this.this$0, r0, intExtra, 2);
                                    synchronized (this.this$0.mPackagesNeedToShowDialog) {
                                        this.this$0.mPackagesNeedToShowDialog.putValue(
                                                r0, intExtra, Boolean.TRUE);
                                    }
                                    return;
                                }
                                return;
                            case 2:
                                UiModeManagerService uiModeManagerService2 = this.this$0;
                                if (uiModeManagerService2.mNightMode.mNightModeValue == 0) {
                                    Settings.Secure.putIntForUser(
                                            uiModeManagerService2.getContext().getContentResolver(),
                                            "ui_night_mode_last_computed",
                                            uiModeManagerService2.mComputedNightMode ? 1 : 0,
                                            uiModeManagerService2.mCurrentUser);
                                    return;
                                }
                                return;
                            case 3:
                                if (Arrays.asList(
                                                "ui_night_mode",
                                                "dark_theme_custom_start_time",
                                                "dark_theme_custom_end_time")
                                        .contains(
                                                intent.getExtras()
                                                        .getCharSequence("setting_name"))) {
                                    synchronized (this.this$0.mLock) {
                                        this.this$0.updateNightModeFromSettingsLocked(
                                                context2,
                                                context2.getResources(),
                                                UserHandle.getCallingUserId());
                                        this.this$0.updateConfigurationLocked();
                                        this.this$0.applyConfigurationExternallyLocked();
                                    }
                                    return;
                                }
                                return;
                            case 4:
                                onReceive$com$android$server$UiModeManagerService$3(
                                        context2, intent);
                                return;
                            case 5:
                                onReceive$com$android$server$UiModeManagerService$4(
                                        context2, intent);
                                return;
                            case 6:
                                onReceive$com$android$server$UiModeManagerService$6(
                                        context2, intent);
                                return;
                            default:
                                synchronized (this.this$0.mLock) {
                                    UiModeManagerService uiModeManagerService3 = this.this$0;
                                    if (uiModeManagerService3.mNightMode.mNightModeValue == 3) {
                                        if (uiModeManagerService3
                                                .shouldApplyAutomaticChangesImmediately()) {
                                            uiModeManagerService3.updateLocked(0, 0);
                                        } else {
                                            uiModeManagerService3
                                                    .registerDeviceInactiveListenerLocked();
                                        }
                                        uiModeManagerService3.scheduleNextCustomTimeListener();
                                    }
                                }
                                return;
                        }
                    }
                };
        final int i7 = 2;
        this.mOnShutdown =
                new BroadcastReceiver(
                        this) { // from class: com.android.server.UiModeManagerService.2
                    public final /* synthetic */ UiModeManagerService this$0;

                    {
                        this.this$0 = this;
                    }

                    private final void onReceive$com$android$server$UiModeManagerService$3(
                            Context context2, Intent intent) {
                        int intExtra = intent.getIntExtra("android.intent.extra.DOCK_STATE", 0);
                        UiModeManagerService uiModeManagerService = this.this$0;
                        synchronized (uiModeManagerService.mLock) {
                            try {
                                if (intExtra != uiModeManagerService.mDockState) {
                                    uiModeManagerService.mDockState = intExtra;
                                    uiModeManagerService.setCarModeLocked(0, 0, "", intExtra == 2);
                                    if (uiModeManagerService.mSystemReady) {
                                        uiModeManagerService.updateLocked(1, 0);
                                    }
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }

                    private final void onReceive$com$android$server$UiModeManagerService$4(
                            Context context2, Intent intent) {
                        String action = intent.getAction();
                        action.getClass();
                        if (action.equals("android.intent.action.BATTERY_CHANGED")) {
                            this.this$0.mCharging = intent.getIntExtra("plugged", 0) != 0;
                        }
                        synchronized (this.this$0.mLock) {
                            try {
                                UiModeManagerService uiModeManagerService = this.this$0;
                                if (uiModeManagerService.mSystemReady
                                        && (uiModeManagerService.mCarModeEnabled
                                                || UiModeManagerService.isDeskDockState(
                                                        uiModeManagerService.mDockState))) {
                                    this.this$0.updateLocked(0, 0);
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }

                    private final void onReceive$com$android$server$UiModeManagerService$6(
                            Context context2, Intent intent) {
                        synchronized (this.this$0.mLock) {
                            this.this$0.unregisterDeviceInactiveListenerLocked();
                            this.this$0.updateLocked(0, 0);
                        }
                    }

                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        int intExtra;
                        switch (i7) {
                            case 0:
                                if (getResultCode() != -1) {
                                    return;
                                }
                                int intExtra2 = intent.getIntExtra("enableFlags", 0);
                                int intExtra3 = intent.getIntExtra("disableFlags", 0);
                                synchronized (this.this$0.mLock) {
                                    UiModeManagerService uiModeManagerService = this.this$0;
                                    String action = intent.getAction();
                                    uiModeManagerService.getClass();
                                    if (UiModeManager.ACTION_ENTER_CAR_MODE.equals(action)) {
                                        if (uiModeManagerService.mEnableCarDockLaunch
                                                && (intExtra2 & 1) != 0) {
                                            r0 = "android.intent.category.CAR_DOCK";
                                        }
                                    } else if (UiModeManager.ACTION_ENTER_DESK_MODE.equals(
                                            action)) {
                                        if ((intExtra2 & 1) != 0) {
                                            r0 = "android.intent.category.DESK_DOCK";
                                        }
                                    } else if ((intExtra3 & 1) != 0) {
                                        r0 = "android.intent.category.HOME";
                                    }
                                    uiModeManagerService
                                            .sendConfigurationAndStartDreamOrDockAppLocked(r0);
                                }
                                return;
                            case 1:
                                Uri data = intent.getData();
                                r0 = data != null ? data.getSchemeSpecificPart() : null;
                                if (r0 == null
                                        || (intExtra =
                                                        intent.getIntExtra(
                                                                "android.intent.extra.user_handle",
                                                                -10000))
                                                == -10000) {
                                    return;
                                }
                                String action2 = intent.getAction();
                                if (!("android.intent.action.PACKAGE_ADDED".equals(action2)
                                                && intent.getBooleanExtra(
                                                        "android.intent.extra.REPLACING", false))
                                        && this.this$0.isNightPriorityAllowedLocked(r0)) {
                                    Set set =
                                            UiModeManagerService.SUPPORTED_NIGHT_MODE_CUSTOM_TYPES;
                                    UiModeManagerService$13$$ExternalSyntheticOutline0.m(
                                            InitialConfiguration$$ExternalSyntheticOutline0.m(
                                                    "onReceive : ",
                                                    action2,
                                                    ", package : ",
                                                    r0,
                                                    ", "),
                                            intExtra,
                                            "UiModeManager");
                                    UiModeManagerService.m103$$Nest$msetPackageNightModeInnerLocked(
                                            this.this$0, r0, intExtra, 2);
                                    synchronized (this.this$0.mPackagesNeedToShowDialog) {
                                        this.this$0.mPackagesNeedToShowDialog.putValue(
                                                r0, intExtra, Boolean.TRUE);
                                    }
                                    return;
                                }
                                return;
                            case 2:
                                UiModeManagerService uiModeManagerService2 = this.this$0;
                                if (uiModeManagerService2.mNightMode.mNightModeValue == 0) {
                                    Settings.Secure.putIntForUser(
                                            uiModeManagerService2.getContext().getContentResolver(),
                                            "ui_night_mode_last_computed",
                                            uiModeManagerService2.mComputedNightMode ? 1 : 0,
                                            uiModeManagerService2.mCurrentUser);
                                    return;
                                }
                                return;
                            case 3:
                                if (Arrays.asList(
                                                "ui_night_mode",
                                                "dark_theme_custom_start_time",
                                                "dark_theme_custom_end_time")
                                        .contains(
                                                intent.getExtras()
                                                        .getCharSequence("setting_name"))) {
                                    synchronized (this.this$0.mLock) {
                                        this.this$0.updateNightModeFromSettingsLocked(
                                                context2,
                                                context2.getResources(),
                                                UserHandle.getCallingUserId());
                                        this.this$0.updateConfigurationLocked();
                                        this.this$0.applyConfigurationExternallyLocked();
                                    }
                                    return;
                                }
                                return;
                            case 4:
                                onReceive$com$android$server$UiModeManagerService$3(
                                        context2, intent);
                                return;
                            case 5:
                                onReceive$com$android$server$UiModeManagerService$4(
                                        context2, intent);
                                return;
                            case 6:
                                onReceive$com$android$server$UiModeManagerService$6(
                                        context2, intent);
                                return;
                            default:
                                synchronized (this.this$0.mLock) {
                                    UiModeManagerService uiModeManagerService3 = this.this$0;
                                    if (uiModeManagerService3.mNightMode.mNightModeValue == 3) {
                                        if (uiModeManagerService3
                                                .shouldApplyAutomaticChangesImmediately()) {
                                            uiModeManagerService3.updateLocked(0, 0);
                                        } else {
                                            uiModeManagerService3
                                                    .registerDeviceInactiveListenerLocked();
                                        }
                                        uiModeManagerService3.scheduleNextCustomTimeListener();
                                    }
                                }
                                return;
                        }
                    }
                };
        final int i8 = 3;
        this.mSettingsRestored =
                new BroadcastReceiver(
                        this) { // from class: com.android.server.UiModeManagerService.2
                    public final /* synthetic */ UiModeManagerService this$0;

                    {
                        this.this$0 = this;
                    }

                    private final void onReceive$com$android$server$UiModeManagerService$3(
                            Context context2, Intent intent) {
                        int intExtra = intent.getIntExtra("android.intent.extra.DOCK_STATE", 0);
                        UiModeManagerService uiModeManagerService = this.this$0;
                        synchronized (uiModeManagerService.mLock) {
                            try {
                                if (intExtra != uiModeManagerService.mDockState) {
                                    uiModeManagerService.mDockState = intExtra;
                                    uiModeManagerService.setCarModeLocked(0, 0, "", intExtra == 2);
                                    if (uiModeManagerService.mSystemReady) {
                                        uiModeManagerService.updateLocked(1, 0);
                                    }
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }

                    private final void onReceive$com$android$server$UiModeManagerService$4(
                            Context context2, Intent intent) {
                        String action = intent.getAction();
                        action.getClass();
                        if (action.equals("android.intent.action.BATTERY_CHANGED")) {
                            this.this$0.mCharging = intent.getIntExtra("plugged", 0) != 0;
                        }
                        synchronized (this.this$0.mLock) {
                            try {
                                UiModeManagerService uiModeManagerService = this.this$0;
                                if (uiModeManagerService.mSystemReady
                                        && (uiModeManagerService.mCarModeEnabled
                                                || UiModeManagerService.isDeskDockState(
                                                        uiModeManagerService.mDockState))) {
                                    this.this$0.updateLocked(0, 0);
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }

                    private final void onReceive$com$android$server$UiModeManagerService$6(
                            Context context2, Intent intent) {
                        synchronized (this.this$0.mLock) {
                            this.this$0.unregisterDeviceInactiveListenerLocked();
                            this.this$0.updateLocked(0, 0);
                        }
                    }

                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        int intExtra;
                        switch (i8) {
                            case 0:
                                if (getResultCode() != -1) {
                                    return;
                                }
                                int intExtra2 = intent.getIntExtra("enableFlags", 0);
                                int intExtra3 = intent.getIntExtra("disableFlags", 0);
                                synchronized (this.this$0.mLock) {
                                    UiModeManagerService uiModeManagerService = this.this$0;
                                    String action = intent.getAction();
                                    uiModeManagerService.getClass();
                                    if (UiModeManager.ACTION_ENTER_CAR_MODE.equals(action)) {
                                        if (uiModeManagerService.mEnableCarDockLaunch
                                                && (intExtra2 & 1) != 0) {
                                            r0 = "android.intent.category.CAR_DOCK";
                                        }
                                    } else if (UiModeManager.ACTION_ENTER_DESK_MODE.equals(
                                            action)) {
                                        if ((intExtra2 & 1) != 0) {
                                            r0 = "android.intent.category.DESK_DOCK";
                                        }
                                    } else if ((intExtra3 & 1) != 0) {
                                        r0 = "android.intent.category.HOME";
                                    }
                                    uiModeManagerService
                                            .sendConfigurationAndStartDreamOrDockAppLocked(r0);
                                }
                                return;
                            case 1:
                                Uri data = intent.getData();
                                r0 = data != null ? data.getSchemeSpecificPart() : null;
                                if (r0 == null
                                        || (intExtra =
                                                        intent.getIntExtra(
                                                                "android.intent.extra.user_handle",
                                                                -10000))
                                                == -10000) {
                                    return;
                                }
                                String action2 = intent.getAction();
                                if (!("android.intent.action.PACKAGE_ADDED".equals(action2)
                                                && intent.getBooleanExtra(
                                                        "android.intent.extra.REPLACING", false))
                                        && this.this$0.isNightPriorityAllowedLocked(r0)) {
                                    Set set =
                                            UiModeManagerService.SUPPORTED_NIGHT_MODE_CUSTOM_TYPES;
                                    UiModeManagerService$13$$ExternalSyntheticOutline0.m(
                                            InitialConfiguration$$ExternalSyntheticOutline0.m(
                                                    "onReceive : ",
                                                    action2,
                                                    ", package : ",
                                                    r0,
                                                    ", "),
                                            intExtra,
                                            "UiModeManager");
                                    UiModeManagerService.m103$$Nest$msetPackageNightModeInnerLocked(
                                            this.this$0, r0, intExtra, 2);
                                    synchronized (this.this$0.mPackagesNeedToShowDialog) {
                                        this.this$0.mPackagesNeedToShowDialog.putValue(
                                                r0, intExtra, Boolean.TRUE);
                                    }
                                    return;
                                }
                                return;
                            case 2:
                                UiModeManagerService uiModeManagerService2 = this.this$0;
                                if (uiModeManagerService2.mNightMode.mNightModeValue == 0) {
                                    Settings.Secure.putIntForUser(
                                            uiModeManagerService2.getContext().getContentResolver(),
                                            "ui_night_mode_last_computed",
                                            uiModeManagerService2.mComputedNightMode ? 1 : 0,
                                            uiModeManagerService2.mCurrentUser);
                                    return;
                                }
                                return;
                            case 3:
                                if (Arrays.asList(
                                                "ui_night_mode",
                                                "dark_theme_custom_start_time",
                                                "dark_theme_custom_end_time")
                                        .contains(
                                                intent.getExtras()
                                                        .getCharSequence("setting_name"))) {
                                    synchronized (this.this$0.mLock) {
                                        this.this$0.updateNightModeFromSettingsLocked(
                                                context2,
                                                context2.getResources(),
                                                UserHandle.getCallingUserId());
                                        this.this$0.updateConfigurationLocked();
                                        this.this$0.applyConfigurationExternallyLocked();
                                    }
                                    return;
                                }
                                return;
                            case 4:
                                onReceive$com$android$server$UiModeManagerService$3(
                                        context2, intent);
                                return;
                            case 5:
                                onReceive$com$android$server$UiModeManagerService$4(
                                        context2, intent);
                                return;
                            case 6:
                                onReceive$com$android$server$UiModeManagerService$6(
                                        context2, intent);
                                return;
                            default:
                                synchronized (this.this$0.mLock) {
                                    UiModeManagerService uiModeManagerService3 = this.this$0;
                                    if (uiModeManagerService3.mNightMode.mNightModeValue == 3) {
                                        if (uiModeManagerService3
                                                .shouldApplyAutomaticChangesImmediately()) {
                                            uiModeManagerService3.updateLocked(0, 0);
                                        } else {
                                            uiModeManagerService3
                                                    .registerDeviceInactiveListenerLocked();
                                        }
                                        uiModeManagerService3.scheduleNextCustomTimeListener();
                                    }
                                }
                                return;
                        }
                    }
                };
        this.mNightPriorityAllowedPackagesFromScpm = new ArrayList();
        String str = PackageFeatureUserChangePersister.PACKAGE_SETTINGS_DIRECTORY;
        this.mNightPriorityAppliedPackages =
                new PackageFeatureUserChange(4096, str, "NightModePriorityAppliedPackages");
        this.mPackagesNeedToShowDialog =
                new PackageFeatureUserChange(8192, str, "NightModeShowDialogPackages");
        this.mService = new Stub(context);
        configuration.setToDefaults();
        this.mTwilightManager = twilightManager;
        this.mInjector = injector;
    }

    public static String adjustTab(int i) {
        String valueOf = String.valueOf(i);
        return valueOf.length() > 4 ? valueOf : " ".concat(valueOf);
    }

    public static String buildLogString() {
        return LocalDateTime.now().format(sFormatter)
                + " "
                + adjustTab(Process.myUid())
                + " "
                + adjustTab(Process.myPid())
                + " "
                + adjustTab(Process.myTid())
                + " V UiModeManager: SavedLogsStart";
    }

    public static long getCustomTimeToMinute(LocalTime localTime) {
        return localTime.getMinute() + (localTime.getHour() * 60);
    }

    public static boolean isDeskDockState(int i) {
        return i == 1 || i == 3 || i == 4;
    }

    public final void adjustStatusBarCarModeLocked() {
        Context context = getContext();
        if (this.mStatusBarManager == null) {
            this.mStatusBarManager = (StatusBarManager) context.getSystemService("statusbar");
        }
        StatusBarManager statusBarManager = this.mStatusBarManager;
        if (statusBarManager != null) {
            statusBarManager.disable(this.mCarModeEnabled ? 524288 : 0);
        }
        if (this.mNotificationManager == null) {
            this.mNotificationManager =
                    (NotificationManager) context.getSystemService("notification");
        }
        NotificationManager notificationManager = this.mNotificationManager;
        if (notificationManager != null) {
            if (this.mCarModeEnabled) {
                this.mNotificationManager.notifyAsUser(
                        null,
                        10,
                        new Notification.Builder(context, SystemNotificationChannels.CAR_MODE)
                                .setSmallIcon(17304430)
                                .setDefaults(4)
                                .setOngoing(true)
                                .setWhen(0L)
                                .setColor(
                                        context.getColor(R.color.system_notification_accent_color))
                                .setContentTitle(
                                        context.getString(R.string.config_packagedKeyboardName))
                                .setContentText(
                                        context.getString(
                                                R.string.config_overrideComponentUiPackage))
                                .setContentIntent(
                                        PendingIntent.getActivityAsUser(
                                                context,
                                                0,
                                                new Intent(
                                                        context,
                                                        (Class<?>) DisableCarModeActivity.class),
                                                33554432,
                                                null,
                                                UserHandle.CURRENT))
                                .build(),
                        UserHandle.ALL);
            } else {
                notificationManager.cancelAsUser(null, 10, UserHandle.ALL);
            }
        }
    }

    public final void applyConfigurationExternallyLocked() {
        int i = this.mSetUiMode;
        int i2 = this.mConfiguration.uiMode;
        if (i != i2) {
            this.mSetUiMode = i2;
            this.mWindowManager.clearSnapshotCache();
            try {
                ActivityTaskManager.getService().updateConfiguration(this.mConfiguration);
            } catch (RemoteException e) {
                Slog.w("UiModeManager", "Failure communicating with activity manager", e);
            } catch (SecurityException e2) {
                Slog.e("UiModeManager", "Activity does not have the ", e2);
            }
        }
    }

    public Configuration getConfiguration() {
        return this.mConfiguration;
    }

    public IUiModeManager getService() {
        return this.mService;
    }

    public final boolean isNightPriorityAllowedLocked(String str) {
        return ((ArrayList) this.mNightPriorityAllowedPackagesFromScpm).contains(str);
    }

    @Override // com.android.server.SystemService
    public final void onBootPhase(int i) {
        if (i == 500) {
            synchronized (this.mLock) {
                try {
                    Context context = getContext();
                    boolean z = true;
                    this.mSystemReady = true;
                    PowerManager powerManager = (PowerManager) context.getSystemService("power");
                    this.mPowerManager = powerManager;
                    this.mWakeLock = powerManager.newWakeLock(26, "UiModeManager");
                    this.mWindowManager =
                            (WindowManagerInternal)
                                    LocalServices.getService(WindowManagerInternal.class);
                    this.mActivityTaskManager =
                            (ActivityTaskManagerInternal)
                                    LocalServices.getService(ActivityTaskManagerInternal.class);
                    this.mAlarmManager = (AlarmManager) getContext().getSystemService("alarm");
                    TwilightManager twilightManager =
                            (TwilightManager) getLocalService(TwilightManager.class);
                    if (twilightManager != null) {
                        this.mTwilightManager = twilightManager;
                    }
                    this.mLocalPowerManager =
                            (PowerManagerInternal)
                                    LocalServices.getService(PowerManagerInternal.class);
                    this.mDreamManagerInternal =
                            (DreamManagerInternal)
                                    LocalServices.getService(DreamManagerInternal.class);
                    this.mPowerSave =
                            this.mLocalPowerManager.getLowPowerState(16).batterySaverEnabled;
                    this.mLocalPowerManager.registerLowPowerModeObserver(
                            16,
                            new Consumer() { // from class:
                                             // com.android.server.UiModeManagerService$$ExternalSyntheticLambda1
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj) {
                                    UiModeManagerService uiModeManagerService =
                                            UiModeManagerService.this;
                                    PowerSaveState powerSaveState = (PowerSaveState) obj;
                                    synchronized (uiModeManagerService.mLock) {
                                        try {
                                            boolean z2 = uiModeManagerService.mPowerSave;
                                            boolean z3 = powerSaveState.batterySaverEnabled;
                                            if (z2 == z3) {
                                                return;
                                            }
                                            uiModeManagerService.mPowerSave = z3;
                                            if (uiModeManagerService.mSystemReady) {
                                                uiModeManagerService.updateLocked(0, 0);
                                            }
                                        } finally {
                                        }
                                    }
                                }
                            });
                    this.mCarModeEnabled = this.mDockState == 2;
                    IVrManager asInterface =
                            IVrManager.Stub.asInterface(ServiceManager.getService("vrmanager"));
                    if (asInterface != null) {
                        try {
                            asInterface.registerListener(this.mVrStateCallbacks);
                        } catch (RemoteException e) {
                            Slog.e(
                                    "UiModeManager",
                                    "Failed to register VR mode state listener: " + e);
                        }
                    }
                    context.getContentResolver()
                            .registerContentObserver(
                                    Settings.Secure.getUriFor("ui_night_mode"),
                                    false,
                                    this.mDarkThemeObserver,
                                    0);
                    context.getContentResolver()
                            .registerContentObserver(
                                    Settings.Secure.getUriFor("contrast_level"),
                                    false,
                                    this.mContrastObserver,
                                    -1);
                    context.registerReceiver(
                            this.mDockModeReceiver,
                            new IntentFilter("android.intent.action.DOCK_EVENT"));
                    context.registerReceiver(
                            this.mBatteryReceiver,
                            new IntentFilter("android.intent.action.BATTERY_CHANGED"));
                    context.registerReceiver(
                            this.mSettingsRestored,
                            new IntentFilter("android.os.action.SETTING_RESTORED"));
                    context.registerReceiver(
                            this.mOnShutdown,
                            new IntentFilter("android.intent.action.ACTION_SHUTDOWN"));
                    IntentFilter intentFilter =
                            new IntentFilter("android.intent.action.PACKAGE_ADDED");
                    intentFilter.addAction("android.intent.action.PACKAGE_DATA_CLEARED");
                    intentFilter.addDataScheme("package");
                    context.registerReceiver(this.mOnPackageAdded, intentFilter);
                    context.getContentResolver()
                            .registerContentObserver(
                                    Settings.Secure.getUriFor("shopdemo"),
                                    false,
                                    new AnonymousClass9(this, this.mHandler, 3),
                                    0);
                    if (Settings.Secure.getInt(getContext().getContentResolver(), "shopdemo", 0)
                            != 1) {
                        z = false;
                    }
                    this.mShopDemo = z;
                    updateConfigurationLocked();
                    applyConfigurationExternallyLocked();
                } finally {
                }
            }
        }
    }

    public final void onProjectionStateChangedLocked(int i) {
        if (this.mProjectionListeners == null) {
            return;
        }
        for (int i2 = 0; i2 < this.mProjectionListeners.size(); i2++) {
            int keyAt = this.mProjectionListeners.keyAt(i2);
            if ((i & keyAt) != 0) {
                RemoteCallbackList remoteCallbackList =
                        (RemoteCallbackList) this.mProjectionListeners.valueAt(i2);
                ArrayList arrayList = new ArrayList();
                int populateWithRelevantActivePackageNames =
                        populateWithRelevantActivePackageNames(keyAt, arrayList);
                int beginBroadcast = remoteCallbackList.beginBroadcast();
                for (int i3 = 0; i3 < beginBroadcast; i3++) {
                    try {
                        remoteCallbackList
                                .getBroadcastItem(i3)
                                .onProjectionStateChanged(
                                        populateWithRelevantActivePackageNames, arrayList);
                    } catch (RemoteException unused) {
                        Slog.w("UiModeManager", "Failed a call to onProjectionStateChanged().");
                    }
                }
                remoteCallbackList.finishBroadcast();
            }
        }
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
        final Context context = getContext();
        verifySetupWizardCompleted();
        final Resources resources = context.getResources();
        this.mNightMode.set(
                resources.getInteger(R.integer.config_displayWhiteBalanceTransitionTimeDecrease));
        this.mStartDreamImmediatelyOnDock = resources.getBoolean(R.bool.config_supportsCamToggle);
        this.mDreamsDisabledByAmbientModeSuppression =
                resources.getBoolean(R.bool.config_duplicate_port_omadm_wappush);
        this.mDefaultUiModeType =
                resources.getInteger(R.integer.config_dreamCloseAnimationDuration);
        this.mCarModeKeepsScreenOn =
                resources.getInteger(R.integer.config_defaultPeakRefreshRate) == 1;
        this.mDeskModeKeepsScreenOn =
                resources.getInteger(R.integer.config_dreamsBatteryLevelMinimumWhenPowered) == 1;
        this.mEnableCarDockLaunch = resources.getBoolean(R.bool.config_enableGeofenceOverlay);
        this.mUiModeLocked = resources.getBoolean(R.bool.config_multiuserDelayUserDataLocking);
        this.mNightModeLocked = resources.getBoolean(R.bool.config_mobile_data_capable);
        PackageManager packageManager = context.getPackageManager();
        this.mTelevision =
                packageManager.hasSystemFeature("android.hardware.type.television")
                        || packageManager.hasSystemFeature("android.software.leanback");
        this.mCar = packageManager.hasSystemFeature("android.hardware.type.automotive");
        this.mWatch = packageManager.hasSystemFeature("android.hardware.type.watch");
        SystemServerInitThreadPool.submit(
                "UiModeManager.onStart",
                new Runnable() { // from class:
                                 // com.android.server.UiModeManagerService$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        UiModeManagerService uiModeManagerService = UiModeManagerService.this;
                        Context context2 = context;
                        Resources resources2 = resources;
                        synchronized (uiModeManagerService.mLock) {
                            try {
                                TwilightManager twilightManager =
                                        (TwilightManager)
                                                uiModeManagerService.getLocalService(
                                                        TwilightManager.class);
                                if (twilightManager != null) {
                                    uiModeManagerService.mTwilightManager = twilightManager;
                                }
                                uiModeManagerService.updateNightModeFromSettingsLocked(
                                        context2, resources2, UserHandle.getCallingUserId());
                                uiModeManagerService.updateSystemProperties();
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }
                });
        publishBinderService("uimode", this.mService);
        publishLocalService(LocalService.class, this.mLocalService);
    }

    @Override // com.android.server.SystemService
    public final void onUserSwitching(
            SystemService.TargetUser targetUser, SystemService.TargetUser targetUser2) {
        this.mCurrentUser = targetUser2.getUserIdentifier();
        if (this.mNightMode.mNightModeValue == 0) {
            Settings.Secure.putIntForUser(
                    getContext().getContentResolver(),
                    "ui_night_mode_last_computed",
                    this.mComputedNightMode ? 1 : 0,
                    targetUser.getUserIdentifier());
        }
    }

    public final void persistNightMode(int i) {
        if (this.mCarModeEnabled || this.mCar) {
            return;
        }
        Settings.Secure.putIntForUser(
                getContext().getContentResolver(),
                "ui_night_mode",
                this.mNightMode.mNightModeValue,
                i);
        Settings.Secure.putLongForUser(
                getContext().getContentResolver(),
                "ui_night_mode_custom_type",
                this.mNightModeCustomType,
                i);
        Settings.System.putLongForUser(
                getContext().getContentResolver(),
                "display_night_theme_on_time",
                getCustomTimeToMinute(this.mCustomAutoNightModeStartMilliseconds),
                i);
        Settings.System.putLongForUser(
                getContext().getContentResolver(),
                "display_night_theme_off_time",
                getCustomTimeToMinute(this.mCustomAutoNightModeEndMilliseconds),
                i);
    }

    public final void persistNightModeOverrides(int i) {
        if (this.mCarModeEnabled || this.mCar) {
            return;
        }
        Settings.Secure.putIntForUser(
                getContext().getContentResolver(),
                "ui_night_mode_override_on",
                this.mOverrideNightModeOn ? 1 : 0,
                i);
        Settings.Secure.putIntForUser(
                getContext().getContentResolver(),
                "ui_night_mode_override_off",
                this.mOverrideNightModeOff ? 1 : 0,
                i);
    }

    public final void persistNightModeSettingDB(int i) {
        int i2 = this.mNightMode.mNightModeValue;
        if (i2 == 0) {
            if (Settings.System.getIntForUser(
                            getContext().getContentResolver(),
                            "display_night_theme_scheduled",
                            0,
                            i)
                    != 1) {
                Settings.System.putIntForUser(
                        getContext().getContentResolver(), "display_night_theme_scheduled", 1, i);
            }
            this.mHandler.postDelayed(new AnonymousClass16(this, 0), 500L);
            this.mAutoModeChangeImmediately = true;
            return;
        }
        if (i2 == 1) {
            Settings.System.putIntForUser(
                    getContext().getContentResolver(), "display_night_theme", 0, i);
            Settings.System.putIntForUser(
                    getContext().getContentResolver(), "display_night_theme_scheduled", 0, i);
            this.mComputedNightMode = false;
        } else if (i2 == 2) {
            Settings.System.putIntForUser(
                    getContext().getContentResolver(), "display_night_theme", 1, i);
            Settings.System.putIntForUser(
                    getContext().getContentResolver(), "display_night_theme_scheduled", 0, i);
            this.mComputedNightMode = false;
        } else if (i2 == 3
                && Settings.System.getIntForUser(
                                getContext().getContentResolver(),
                                "display_night_theme_scheduled",
                                0,
                                i)
                        != 1) {
            Settings.System.putIntForUser(
                    getContext().getContentResolver(), "display_night_theme_scheduled", 1, i);
        }
    }

    public final int populateWithRelevantActivePackageNames(int i, List list) {
        ArrayList arrayList = (ArrayList) list;
        arrayList.clear();
        if (this.mProjectionHolders == null) {
            return 0;
        }
        int i2 = 0;
        for (int i3 = 0; i3 < this.mProjectionHolders.size(); i3++) {
            int keyAt = this.mProjectionHolders.keyAt(i3);
            List list2 = (List) this.mProjectionHolders.valueAt(i3);
            if ((i & keyAt) != 0) {
                ArrayList arrayList2 = new ArrayList();
                Iterator it = list2.iterator();
                while (it.hasNext()) {
                    arrayList2.add(((ProjectionHolder) it.next()).mPackageName);
                }
                if (arrayList.addAll(arrayList2)) {
                    i2 |= keyAt;
                }
            }
        }
        return i2;
    }

    public final void registerDeviceInactiveListenerLocked() {
        if (this.mPowerSave) {
            return;
        }
        this.mWaitForDeviceInactive = true;
        IntentFilter intentFilter = new IntentFilter("android.intent.action.SCREEN_OFF");
        intentFilter.addAction("android.intent.action.DREAMING_STARTED");
        getContext().registerReceiver(this.mDeviceInactiveListener, intentFilter);
    }

    public final boolean resetNightModeOverrideLocked() {
        if (!this.mOverrideNightModeOff && !this.mOverrideNightModeOn) {
            return false;
        }
        this.mOverrideNightModeOff = false;
        this.mOverrideNightModeOn = false;
        persistNightModeOverrides(this.mOverrideNightModeUser);
        this.mOverrideNightModeUser = 0;
        return true;
    }

    /* JADX WARN: Type inference failed for: r0v5, types: [java.time.ZonedDateTime] */
    public final void scheduleNextCustomTimeListener() {
        LocalDateTime of;
        this.mAlarmManager.cancel(this.mCustomTimeListener);
        LocalDateTime now = LocalDateTime.now();
        if (TimeUtils.isTimeBetween(
                LocalTime.now(),
                this.mCustomAutoNightModeStartMilliseconds,
                this.mCustomAutoNightModeEndMilliseconds)) {
            of = LocalDateTime.of(now.toLocalDate(), this.mCustomAutoNightModeEndMilliseconds);
            if (of.isBefore(now)) {
                of = of.plusDays(1L);
            }
        } else {
            of = LocalDateTime.of(now.toLocalDate(), this.mCustomAutoNightModeStartMilliseconds);
            if (of.isBefore(now)) {
                of = of.plusDays(1L);
            }
        }
        this.mAlarmManager.setExact(
                1,
                of.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(),
                "UiModeManager",
                this.mCustomTimeListener,
                null);
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0097  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x00a4 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:32:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void sendConfigurationAndStartDreamOrDockAppLocked(java.lang.String r21) {
        /*
            r20 = this;
            r1 = r20
            r2 = r21
            java.lang.String r3 = "UiModeManager"
            java.lang.String r4 = "Could not start dock app: "
            r5 = 0
            r1.mHoldingConfiguration = r5
            r20.updateConfigurationLocked()
            r6 = 1
            if (r2 == 0) goto L8f
            android.content.Intent r15 = new android.content.Intent
            java.lang.String r0 = "android.intent.action.MAIN"
            r15.<init>(r0)
            r15.addCategory(r2)
            r0 = 270532608(0x10200000, float:3.1554436E-29)
            r15.setFlags(r0)
            android.content.Context r0 = r20.getContext()
            boolean r0 = android.service.dreams.Sandman.shouldStartDockApp(r0, r15)
            if (r0 == 0) goto L8f
            android.app.IActivityTaskManager r7 = android.app.ActivityTaskManager.getService()     // Catch: android.os.RemoteException -> L7e
            android.content.Context r0 = r20.getContext()     // Catch: android.os.RemoteException -> L7e
            java.lang.String r9 = r0.getBasePackageName()     // Catch: android.os.RemoteException -> L7e
            android.content.Context r0 = r20.getContext()     // Catch: android.os.RemoteException -> L7e
            java.lang.String r10 = r0.getAttributionTag()     // Catch: android.os.RemoteException -> L7e
            android.content.res.Configuration r0 = r1.mConfiguration     // Catch: android.os.RemoteException -> L7e
            r18 = 0
            r19 = -2
            r8 = 0
            r12 = 0
            r13 = 0
            r14 = 0
            r16 = 0
            r17 = 0
            r11 = r15
            r5 = r15
            r15 = r16
            r16 = r17
            r17 = r0
            int r0 = r7.startActivityWithConfig(r8, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18, r19)     // Catch: android.os.RemoteException -> L7c
            boolean r7 = android.app.ActivityManager.isStartResultSuccessful(r0)     // Catch: android.os.RemoteException -> L7c
            if (r7 == 0) goto L60
            r0 = r6
            goto L90
        L60:
            r7 = -91
            if (r0 == r7) goto L8f
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch: android.os.RemoteException -> L7c
            r7.<init>(r4)     // Catch: android.os.RemoteException -> L7c
            r7.append(r5)     // Catch: android.os.RemoteException -> L7c
            java.lang.String r8 = ", startActivityWithConfig result "
            r7.append(r8)     // Catch: android.os.RemoteException -> L7c
            r7.append(r0)     // Catch: android.os.RemoteException -> L7c
            java.lang.String r0 = r7.toString()     // Catch: android.os.RemoteException -> L7c
            android.util.Slog.e(r3, r0)     // Catch: android.os.RemoteException -> L7c
            goto L8f
        L7c:
            r0 = move-exception
            goto L80
        L7e:
            r0 = move-exception
            r5 = r15
        L80:
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>(r4)
            r7.append(r5)
            java.lang.String r4 = r7.toString()
            android.util.Slog.e(r3, r4, r0)
        L8f:
            r0 = 0
        L90:
            r20.applyConfigurationExternallyLocked()
            boolean r3 = r1.mDreamsDisabledByAmbientModeSuppression
            if (r3 == 0) goto La1
            android.os.PowerManagerInternal r3 = r1.mLocalPowerManager
            boolean r3 = r3.isAmbientDisplaySuppressed()
            if (r3 == 0) goto La1
            r5 = r6
            goto La2
        La1:
            r5 = 0
        La2:
            if (r2 == 0) goto Lc8
            if (r0 != 0) goto Lc8
            if (r5 != 0) goto Lc8
            boolean r0 = r1.mStartDreamImmediatelyOnDock
            if (r0 != 0) goto Lbc
            com.android.server.wm.WindowManagerInternal r0 = r1.mWindowManager
            boolean r0 = r0.isKeyguardShowingAndNotOccluded()
            if (r0 != 0) goto Lbc
            android.os.PowerManager r0 = r1.mPowerManager
            boolean r0 = r0.isInteractive()
            if (r0 != 0) goto Lc8
        Lbc:
            android.content.Context r0 = r20.getContext()
            com.android.server.UiModeManagerService$Injector r1 = r1.mInjector
            r1.getClass()
            android.service.dreams.Sandman.startDreamWhenDockedIfAppropriate(r0)
        Lc8:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.UiModeManagerService.sendConfigurationAndStartDreamOrDockAppLocked(java.lang.String):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:26:0x0099, code lost:

       if (((java.lang.String) ((java.util.HashMap) r9.mCarModePackagePriority).get(java.lang.Integer.valueOf(r11))).equals(r12) == false) goto L19;
    */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x009b, code lost:

       if (r13 != false) goto L20;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void setCarModeLocked(int r10, int r11, java.lang.String r12, boolean r13) {
        /*
            Method dump skipped, instructions count: 326
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.UiModeManagerService.setCarModeLocked(int, int,"
                    + " java.lang.String, boolean):void");
    }

    public void setDreamsDisabledByAmbientModeSuppression(boolean z) {
        this.mDreamsDisabledByAmbientModeSuppression = z;
    }

    public void setStartDreamImmediatelyOnDock(boolean z) {
        this.mStartDreamImmediatelyOnDock = z;
    }

    public final boolean shouldApplyAutomaticChangesImmediately() {
        return this.mCar
                || !this.mPowerManager.isInteractive()
                || this.mNightModeCustomType == 1
                || this.mDreamManagerInternal.isDreaming();
    }

    public final void unregisterDeviceInactiveListenerLocked() {
        this.mWaitForDeviceInactive = false;
        try {
            getContext().unregisterReceiver(this.mDeviceInactiveListener);
        } catch (IllegalArgumentException unused) {
        }
    }

    public final void updateComputedNightModeLocked(boolean z) {
        TwilightManager twilightManager;
        AnonymousClass1 anonymousClass1 = this.mNightMode;
        int i = anonymousClass1.mNightModeValue;
        boolean z2 = false;
        if (i != 2 && i != 1) {
            if (this.mOverrideNightModeOn && !z) {
                z = true;
            } else if (this.mOverrideNightModeOff && z) {
                z = false;
            }
        }
        if (Flags.modesApi()) {
            int i2 = this.mAttentionModeThemeOverlay;
            if (i2 == 1001) {
                z2 = true;
            } else if (i2 != 1002) {
                z2 = z;
            }
            this.mComputedNightMode = z2;
        } else {
            this.mComputedNightMode = z;
        }
        if (anonymousClass1.mNightModeValue == 0
                && ((twilightManager = this.mTwilightManager) == null
                        || ((TwilightService.AnonymousClass1) twilightManager)
                                        .getLastTwilightState()
                                == null)) {
            return;
        }
        resetNightModeOverrideLocked();
    }

    public final void updateConfigurationLocked() {
        int i;
        int i2 = this.mDefaultUiModeType;
        if (!this.mUiModeLocked) {
            if (!this.mDesktopModeEnabled && (!CoreRune.MT_NEW_DEX || !this.mNewDexModeEnabled)) {
                if (this.mTelevision) {
                    i2 = 4;
                } else if (this.mWatch) {
                    i2 = 6;
                } else if (this.mCarModeEnabled) {
                    i2 = 3;
                } else if (!isDeskDockState(this.mDockState)) {
                    if (this.mVrHeadset) {
                        i2 = 7;
                    }
                }
            }
            i2 = 2;
        }
        AnonymousClass1 anonymousClass1 = this.mNightMode;
        int i3 = anonymousClass1.mNightModeValue;
        if (i3 == 2 || i3 == 1) {
            updateComputedNightModeLocked(i3 == 2);
        }
        int i4 = anonymousClass1.mNightModeValue;
        AnonymousClass5 anonymousClass5 = this.mTwilightListener;
        if (i4 == 0) {
            boolean z = this.mComputedNightMode;
            TwilightManager twilightManager = this.mTwilightManager;
            if (twilightManager != null) {
                ((TwilightService.AnonymousClass1) twilightManager)
                        .registerListener(anonymousClass5, this.mHandler);
                TwilightState lastTwilightState =
                        ((TwilightService.AnonymousClass1) this.mTwilightManager)
                                .getLastTwilightState();
                z =
                        lastTwilightState == null
                                ? this.mComputedNightMode
                                : lastTwilightState.isNight();
            }
            updateComputedNightModeLocked(z);
            if (z != this.mComputedNightMode) {
                this.mWaitForDeviceInactive = false;
            }
            updateThemeImmediately();
            unregisterDeviceInactiveListenerLocked();
        } else {
            TwilightManager twilightManager2 = this.mTwilightManager;
            if (twilightManager2 != null) {
                ((TwilightService.AnonymousClass1) twilightManager2)
                        .unregisterListener(anonymousClass5);
            }
        }
        int i5 = anonymousClass1.mNightModeValue;
        AnonymousClass2 anonymousClass2 = this.mOnTimeChangedHandler;
        if (i5 != 3) {
            try {
                getContext().unregisterReceiver(anonymousClass2);
            } catch (IllegalArgumentException unused) {
            }
        } else if (this.mNightModeCustomType == 1) {
            updateComputedNightModeLocked(this.mLastBedtimeRequestedNightMode);
        } else {
            IntentFilter intentFilter = new IntentFilter("android.intent.action.TIME_SET");
            intentFilter.addAction("android.intent.action.TIMEZONE_CHANGED");
            getContext().registerReceiver(anonymousClass2, intentFilter);
            updateComputedNightModeLocked(
                    TimeUtils.isTimeBetween(
                            LocalTime.now(),
                            this.mCustomAutoNightModeStartMilliseconds,
                            this.mCustomAutoNightModeEndMilliseconds));
            scheduleNextCustomTimeListener();
            updateThemeImmediately();
        }
        boolean z2 = this.mPowerSave;
        if (!z2 || this.mCarModeEnabled || this.mCar) {
            boolean z3 = this.mComputedNightMode;
            i = (i2 | (z3 ? 32 : 16)) & (z3 ? -17 : -33);
        } else {
            i = (i2 & (-17)) | 32;
        }
        this.mCurUiMode = i;
        if (this.mHoldingConfiguration) {
            return;
        }
        if (!this.mWaitForDeviceInactive || z2) {
            this.mConfiguration.uiMode = i;
        }
    }

    public final boolean updateContrastLocked() {
        float floatForUser =
                Settings.Secure.getFloatForUser(
                        getContext().getContentResolver(),
                        "contrast_level",
                        FullScreenMagnificationGestureHandler.MAX_SCALE,
                        this.mCurrentUser);
        if (Math.abs(
                        ((Float)
                                                this.mContrasts.get(
                                                        this.mCurrentUser,
                                                        Float.valueOf(Float.MAX_VALUE)))
                                        .floatValue()
                                - floatForUser)
                < 1.0E-10d) {
            return false;
        }
        this.mContrasts.put(this.mCurrentUser, Float.valueOf(floatForUser));
        return true;
    }

    public final void updateLocked(int i, int i2) {
        String str;
        int i3 = this.mLastBroadcastState;
        String str2 = null;
        if (i3 == 2) {
            adjustStatusBarCarModeLocked();
            str = UiModeManager.ACTION_EXIT_CAR_MODE;
        } else {
            str = isDeskDockState(i3) ? UiModeManager.ACTION_EXIT_DESK_MODE : null;
        }
        boolean z = false;
        if (this.mCarModeEnabled) {
            if (this.mLastBroadcastState != 2) {
                adjustStatusBarCarModeLocked();
                if (str != null) {
                    getContext()
                            .sendBroadcastAsUser(
                                    new Intent(str).addFlags(268435456), UserHandle.ALL);
                }
                this.mLastBroadcastState = 2;
                str = UiModeManager.ACTION_ENTER_CAR_MODE;
            }
            str = null;
        } else if (isDeskDockState(this.mDockState)) {
            if (!isDeskDockState(this.mLastBroadcastState)) {
                if (str != null) {
                    getContext()
                            .sendBroadcastAsUser(
                                    new Intent(str).addFlags(268435456), UserHandle.ALL);
                }
                this.mLastBroadcastState = this.mDockState;
                str = UiModeManager.ACTION_ENTER_DESK_MODE;
            }
            str = null;
        } else {
            this.mLastBroadcastState = 0;
        }
        if (str != null) {
            Intent intent = new Intent(str);
            intent.putExtra("enableFlags", i);
            intent.putExtra("disableFlags", i2);
            intent.addFlags(268435456);
            getContext()
                    .sendOrderedBroadcastAsUser(
                            intent,
                            UserHandle.CURRENT,
                            null,
                            this.mResultReceiver,
                            null,
                            -1,
                            null,
                            null);
            this.mHoldingConfiguration = true;
            updateConfigurationLocked();
        } else {
            if (this.mCarModeEnabled) {
                if (this.mEnableCarDockLaunch && (i & 1) != 0) {
                    str2 = "android.intent.category.CAR_DOCK";
                }
            } else if (isDeskDockState(this.mDockState)) {
                if ((i & 1) != 0) {
                    str2 = "android.intent.category.DESK_DOCK";
                }
            } else if ((i2 & 1) != 0) {
                str2 = "android.intent.category.HOME";
            }
            sendConfigurationAndStartDreamOrDockAppLocked(str2);
        }
        if (this.mCharging
                && ((this.mCarModeEnabled
                                && this.mCarModeKeepsScreenOn
                                && (this.mCarModeEnableFlags & 2) == 0)
                        || (this.mCurUiMode == 2 && this.mDeskModeKeepsScreenOn))) {
            z = true;
        }
        if (z != this.mWakeLock.isHeld()) {
            if (z) {
                this.mWakeLock.acquire();
            } else {
                this.mWakeLock.release();
            }
        }
    }

    public final void updateNightModeFromSettingsLocked(
            Context context, Resources resources, int i) {
        if (this.mCarModeEnabled || this.mCar) {
            return;
        }
        int i2 = Settings.Global.getInt(getContext().getContentResolver(), "low_power", -1);
        AnonymousClass1 anonymousClass1 = this.mNightMode;
        if (i2 == 1
                && Settings.Global.getInt(
                                getContext().getContentResolver(),
                                "pms_settings_dark_mode_enabled",
                                -1)
                        == 1) {
            anonymousClass1.set(2);
        } else {
            int intForUser =
                    Settings.Secure.getIntForUser(
                            context.getContentResolver(),
                            "last_secure_ui_night_mode_power_mode",
                            -1,
                            i);
            if (intForUser != -1) {
                anonymousClass1.set(intForUser);
                Settings.Secure.putIntForUser(
                        context.getContentResolver(),
                        "last_secure_ui_night_mode_power_mode",
                        -1,
                        i);
            } else {
                anonymousClass1.set(
                        Settings.Secure.getIntForUser(
                                context.getContentResolver(),
                                "ui_night_mode",
                                resources.getInteger(
                                        R.integer.config_displayWhiteBalanceTransitionTimeDecrease),
                                i));
            }
        }
        this.mNightModeCustomType =
                Settings.Secure.getIntForUser(
                        context.getContentResolver(), "ui_night_mode_custom_type", -1, i);
        this.mOverrideNightModeOn =
                Settings.Secure.getIntForUser(
                                context.getContentResolver(), "ui_night_mode_override_on", 0, i)
                        != 0;
        this.mOverrideNightModeOff =
                Settings.Secure.getIntForUser(
                                context.getContentResolver(), "ui_night_mode_override_off", 0, i)
                        != 0;
        long longForUser =
                Settings.System.getLongForUser(
                        context.getContentResolver(),
                        "display_night_theme_on_time",
                        getCustomTimeToMinute(this.DEFAULT_CUSTOM_NIGHT_START_TIME),
                        i);
        long longForUser2 =
                Settings.System.getLongForUser(
                        context.getContentResolver(),
                        "display_night_theme_off_time",
                        getCustomTimeToMinute(this.DEFAULT_CUSTOM_NIGHT_END_TIME),
                        i);
        this.mCustomAutoNightModeStartMilliseconds =
                LocalTime.ofNanoOfDay(longForUser * 60000000000L);
        this.mCustomAutoNightModeEndMilliseconds =
                LocalTime.ofNanoOfDay(longForUser2 * 60000000000L);
        if (anonymousClass1.mNightModeValue == 0) {
            this.mComputedNightMode =
                    Settings.Secure.getIntForUser(
                                    context.getContentResolver(),
                                    "ui_night_mode_last_computed",
                                    0,
                                    i)
                            != 0;
        }
        LogWrapper.i("updateNightModeFromSettings : " + anonymousClass1 + " userID : " + i);
        persistNightModeSettingDB(i);
    }

    public final void updateSystemProperties() {
        int intForUser =
                Settings.Secure.getIntForUser(
                        getContext().getContentResolver(),
                        "ui_night_mode",
                        this.mNightMode.mNightModeValue,
                        0);
        if (intForUser == 0 || intForUser == 3) {
            intForUser = 2;
        }
        SystemProperties.set("persist.sys.theme", Integer.toString(intForUser));
    }

    public final void updateThemeImmediately() {
        boolean z =
                Settings.System.getIntForUser(
                                getContext().getContentResolver(), "display_night_theme", 0, -2)
                        == 1;
        boolean z2 = this.mComputedNightMode;
        if (z == z2) {
            return;
        }
        if (!z2
                && Settings.System.getInt(
                                getContext().getContentResolver(), "minimal_battery_use", 0)
                        != 1) {
            this.mHandler.postDelayed(new AnonymousClass16(this, 1), 500L);
        }
        Settings.System.putIntForUser(
                getContext().getContentResolver(),
                "display_night_theme",
                this.mComputedNightMode ? 1 : 0,
                -2);
    }

    public final void verifySetupWizardCompleted() {
        Context context = getContext();
        int callingUserId = UserHandle.getCallingUserId();
        if (Settings.Secure.getIntForUser(
                        getContext().getContentResolver(),
                        "user_setup_complete",
                        0,
                        UserHandle.getCallingUserId())
                == 1) {
            return;
        }
        context.getContentResolver()
                .registerContentObserver(
                        Settings.Secure.getUriFor("user_setup_complete"),
                        false,
                        this.mSetupWizardObserver,
                        callingUserId);
    }
}
