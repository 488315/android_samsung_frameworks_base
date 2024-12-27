package com.android.server.pm;

import android.R;
import android.app.ActivityManager;
import android.app.ActivityManagerInternal;
import android.app.AppGlobals;
import android.app.IUidObserver;
import android.app.IUriGrantsManager;
import android.app.UidObserver;
import android.app.UriGrantsManager;
import android.app.role.OnRoleHoldersChangedListener;
import android.app.role.RoleManager;
import android.app.usage.UsageStatsManagerInternal;
import android.appwidget.AppWidgetProviderInfo;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentProvider;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.ComponentInfo;
import android.content.pm.IPackageManager;
import android.content.pm.IShortcutService;
import android.content.pm.LauncherApps;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManagerInternal;
import android.content.pm.ParceledListSlice;
import android.content.pm.ResolveInfo;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.content.pm.ShortcutServiceInternal;
import android.content.pm.UserPackage;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.Icon;
import android.hardware.audio.common.V2_0.AudioOffloadInfo$$ExternalSyntheticOutline0;
import android.hardware.broadcastradio.V2_0.AmFmBandRange$$ExternalSyntheticOutline0;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.SELinux;
import android.os.ServiceManager;
import android.os.ShellCallback;
import android.os.ShellCommand;
import android.os.SystemClock;
import android.os.Trace;
import android.os.UserHandle;
import android.provider.DeviceConfig;
import android.provider.Settings;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.text.TextUtils;
import android.text.format.TimeMigrationUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.EventLog;
import android.util.KeyValueListParser;
import android.util.Log;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.util.SparseLongArray;
import android.util.TypedValue;
import android.util.Xml;
import android.view.IWindowManager;

import com.android.internal.hidden_from_bootclasspath.android.os.Flags;
import com.android.internal.infra.AndroidFuture;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.os.IParcelFileDescriptorFactory;
import com.android.internal.util.CollectionUtils;
import com.android.internal.util.DumpUtils;
import com.android.internal.util.Preconditions;
import com.android.internal.util.StatLogger;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.DualAppManagerService$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.StorageManagerService$$ExternalSyntheticOutline0;
import com.android.server.SystemService;
import com.android.server.UserspaceRebootLogger$$ExternalSyntheticOutline0;
import com.android.server.VpnManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.accounts.AccountManagerService$$ExternalSyntheticOutline0;
import com.android.server.uri.UriGrantsManagerInternal;
import com.android.server.uri.UriGrantsManagerService;

import libcore.io.IoUtils;

import com.samsung.android.app.SemDualAppManager;
import com.samsung.android.rune.PMRune;
import com.samsung.android.server.pm.PmLog;
import com.samsung.android.server.pm.ShortcutThread;
import com.samsung.android.server.pm.monetization.MonetizationUtils;

import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public final class ShortcutService extends IShortcutService.Stub {
    public static final AnonymousClass1 ACTIVITY_NOT_EXPORTED;
    static final int DEFAULT_ICON_PERSIST_QUALITY = 100;
    static final int DEFAULT_MAX_ICON_DIMENSION_DP = 96;
    static final int DEFAULT_MAX_ICON_DIMENSION_LOWRAM_DP = 48;
    static final int DEFAULT_MAX_SHORTCUTS_PER_ACTIVITY = 15;
    static final int DEFAULT_MAX_SHORTCUTS_PER_APP = 100;
    static final int DEFAULT_MAX_UPDATES_PER_INTERVAL = 10;
    static final long DEFAULT_RESET_INTERVAL_SEC = 86400;
    static final int DEFAULT_SAVE_DELAY_MS = 3000;
    static final String DIRECTORY_DUMP = "shortcut_dump";
    static final String DIRECTORY_PER_USER = "shortcut_service";
    static final String FILENAME_BASE_STATE = "shortcut_service.xml";
    static final String FILENAME_USER_PACKAGES = "shortcuts.xml";
    static final String FILENAME_USER_PACKAGES_RESERVE_COPY = "shortcuts.xml.reservecopy";
    public static final AnonymousClass1 PACKAGE_NOT_INSTALLED;
    public final ActivityManagerInternal mActivityManagerInternal;
    public final AtomicBoolean mBootCompleted;
    public ComponentName mChooserActivity;
    public final Context mContext;
    public List mDirtyUserIds;
    public final Handler mHandler;
    public final IPackageManager mIPackageManager;
    public Bitmap.CompressFormat mIconPersistFormat;
    public int mIconPersistQuality;
    public final boolean mIsAppSearchEnabled;
    public int mLastLockedUser;
    public Exception mLastWtfStacktrace;
    public final ArrayList mListeners;
    public int mMaxIconDimension;
    public int mMaxShortcuts;
    public int mMaxShortcutsPerApp;
    public int mMaxUpdatesPerInterval;
    public final MetricsLogger mMetricsLogger;
    public final Object mNonPersistentUsersLock;
    public final AnonymousClass3 mOnRoleHoldersChangedListener;
    public final PackageManagerInternal mPackageManagerInternal;
    final BroadcastReceiver mPackageMonitor;
    public final AtomicLong mRawLastResetTime;
    public final AnonymousClass5 mReceiver;
    public long mResetInterval;
    public final RoleManager mRoleManager;
    public int mSaveDelayMillis;
    public final ShortcutService$$ExternalSyntheticLambda9 mSaveDirtyInfoRunner;
    public final Object mServiceLock;
    public final ArrayList mShortcutChangeCallbacks;
    public final ShortcutDumpFiles mShortcutDumpFiles;
    public final SparseArray mShortcutNonPersistentUsers;
    public final ShortcutRequestPinProcessor mShortcutRequestPinProcessor;
    public final AtomicBoolean mShutdown;
    public final AnonymousClass5 mShutdownReceiver;
    public final AtomicBoolean mSmartSwitchBackupAllowed = new AtomicBoolean(false);
    public final StatLogger mStatLogger;
    public final SparseLongArray mUidLastForegroundElapsedTime;
    public final AnonymousClass4 mUidObserver;
    public final SparseIntArray mUidState;
    public final SparseBooleanArray mUnlockedUsers;
    public final IUriGrantsManager mUriGrantsManager;
    public final UriGrantsManagerInternal mUriGrantsManagerInternal;
    public final IBinder mUriPermissionOwner;
    public final UsageStatsManagerInternal mUsageStatsManagerInternal;
    public final UserManagerInternal mUserManagerInternal;
    public final SparseArray mUsers;
    public int mWtfCount;
    public final Object mWtfLock;
    static final String DEFAULT_ICON_PERSIST_FORMAT = Bitmap.CompressFormat.PNG.name();
    public static final AtomicBoolean sIsEmergencyMode = new AtomicBoolean();
    public static final List EMPTY_RESOLVE_INFO = new ArrayList(0);
    public static final ShortcutService$$ExternalSyntheticLambda12 ACTIVITY_NOT_INSTALLED =
            new ShortcutService$$ExternalSyntheticLambda12(2);

    /* renamed from: com.android.server.pm.ShortcutService$3, reason: invalid class name */
    public final class AnonymousClass3 implements OnRoleHoldersChangedListener {
        public AnonymousClass3() {}

        public final void onRoleHoldersChanged(String str, UserHandle userHandle) {
            if ("android.app.role.HOME".equals(str)) {
                ShortcutService.this.injectPostToHandler(
                        new ShortcutService$$ExternalSyntheticLambda5(1, this, userHandle));
            }
            PmLog.logDebugInfoAndLogcat(
                    "Role holders for "
                            + str
                            + " changed. holders: "
                            + ShortcutService.this.mRoleManager.getRoleHoldersAsUser(
                                    str, userHandle)
                            + ", UserId: "
                            + userHandle.getIdentifier());
            if (PMRune.PM_BADGE_ON_MONETIZED_APP_SUPPORTED) {
                try {
                    if (ShortcutService.this.mIPackageManager.isFirstBoot()
                            && "android.app.role.SMS".equals(str)) {
                        MonetizationUtils monetizationUtils =
                                MonetizationUtils.getInstance(ShortcutService.this.mContext);
                        if (monetizationUtils.mIsTruecallerSettingsUpdated) {
                            return;
                        }
                        monetizationUtils.modifyAppState(0, "com.truecaller");
                        monetizationUtils.mIsTruecallerSettingsUpdated = true;
                    }
                } catch (RemoteException unused) {
                }
            }
        }
    }

    /* renamed from: com.android.server.pm.ShortcutService$4, reason: invalid class name */
    public final class AnonymousClass4 extends UidObserver {
        public AnonymousClass4() {}

        public final void onUidGone(final int i, boolean z) {
            ShortcutService.this.injectPostToHandler(
                    new Runnable() { // from class:
                                     // com.android.server.pm.ShortcutService$4$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            ShortcutService.AnonymousClass4 anonymousClass4 =
                                    ShortcutService.AnonymousClass4.this;
                            ShortcutService.this.handleOnUidStateChanged(i, 20);
                        }
                    });
        }

        public final void onUidStateChanged(final int i, final int i2, long j, int i3) {
            ShortcutService.this.injectPostToHandler(
                    new Runnable() { // from class:
                                     // com.android.server.pm.ShortcutService$4$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            ShortcutService.AnonymousClass4 anonymousClass4 =
                                    ShortcutService.AnonymousClass4.this;
                            ShortcutService.this.handleOnUidStateChanged(i, i2);
                        }
                    });
        }
    }

    class CommandException extends Exception {
        public CommandException(String str) {
            super(str);
        }
    }

    interface ConfigConstants {}

    public final class DumpFilter {
        public boolean mDumpCheckIn = false;
        public boolean mCheckInClear = false;
        public boolean mDumpMain = true;
        public boolean mDumpUid = false;
        public boolean mDumpFiles = false;
        public boolean mDumpDetails = true;
        public final List mPackagePatterns = new ArrayList();
        public final List mUsers = new ArrayList();

        public final boolean isPackageMatch(String str) {
            if (((ArrayList) this.mPackagePatterns).size() == 0) {
                return true;
            }
            for (int i = 0; i < ((ArrayList) this.mPackagePatterns).size(); i++) {
                if (((Pattern) ((ArrayList) this.mPackagePatterns).get(i)).matcher(str).find()) {
                    return true;
                }
            }
            return false;
        }

        public final boolean isUserMatch(int i) {
            if (((ArrayList) this.mUsers).size() == 0) {
                return true;
            }
            for (int i2 = 0; i2 < ((ArrayList) this.mUsers).size(); i2++) {
                if (((Integer) ((ArrayList) this.mUsers).get(i2)).intValue() == i) {
                    return true;
                }
            }
            return false;
        }
    }

    class FileOutputStreamWithPath extends FileOutputStream {
        public final File mFile;

        public FileOutputStreamWithPath(File file) {
            super(file);
            this.mFile = file;
        }
    }

    class InvalidFileFormatException extends Exception {}

    public final class Lifecycle extends SystemService {
        public final ShortcutService mService;

        public Lifecycle(Context context) {
            super(context);
            ShortcutThread shortcutThread;
            synchronized (ShortcutThread.sLock) {
                ShortcutThread.ensureThreadLocked();
                shortcutThread = ShortcutThread.sInstance;
            }
            this.mService = new ShortcutService(context, shortcutThread.getLooper(), false);
        }

        @Override // com.android.server.SystemService
        public final void onBootPhase(int i) {
            ShortcutService shortcutService = this.mService;
            shortcutService.getClass();
            Slog.d("ShortcutService", "onBootPhase: " + i);
            if (i != 480) {
                if (i != 1000) {
                    return;
                }
                shortcutService.mBootCompleted.set(true);
            } else {
                synchronized (shortcutService.mServiceLock) {
                    shortcutService.updateConfigurationLocked(
                            shortcutService.injectShortcutManagerConstants());
                    shortcutService.loadBaseStateLocked();
                }
            }
        }

        @Override // com.android.server.SystemService
        public final void onStart() {
            publishBinderService("shortcut", this.mService);
        }

        @Override // com.android.server.SystemService
        public final void onUserStopping(SystemService.TargetUser targetUser) {
            this.mService.handleStopUser(targetUser.getUserIdentifier());
        }

        @Override // com.android.server.SystemService
        public final void onUserUnlocking(SystemService.TargetUser targetUser) {
            final ShortcutService shortcutService = this.mService;
            final int userIdentifier = targetUser.getUserIdentifier();
            shortcutService.getClass();
            Slog.d("ShortcutService", "handleUnlockUser: user=" + userIdentifier);
            synchronized (shortcutService.mUnlockedUsers) {
                shortcutService.mUnlockedUsers.put(userIdentifier, true);
            }
            final long time = shortcutService.mStatLogger.getTime();
            new Thread(
                            new Runnable() { // from class:
                                             // com.android.server.pm.ShortcutService$$ExternalSyntheticLambda7
                                @Override // java.lang.Runnable
                                public final void run() {
                                    ShortcutService shortcutService2 = ShortcutService.this;
                                    long j = time;
                                    int i = userIdentifier;
                                    shortcutService2.getClass();
                                    Trace.traceBegin(524288L, "shortcutHandleUnlockUser");
                                    synchronized (shortcutService2.mServiceLock) {
                                        shortcutService2.logDurationStat(15, j);
                                        shortcutService2.getUserShortcutsLocked(i);
                                    }
                                    Trace.traceEnd(524288L);
                                }
                            })
                    .start();
        }
    }

    public final class LocalService extends ShortcutServiceInternal {
        public LocalService() {}

        public static ParcelFileDescriptor getShortcutIconParcelFileDescriptor(
                ShortcutPackage shortcutPackage, ShortcutInfo shortcutInfo) {
            String bitmapPath;
            if (shortcutInfo == null || !shortcutInfo.hasIconFile()) {
                return null;
            }
            synchronized (shortcutPackage.mPackageItemLock) {
                bitmapPath =
                        (shortcutPackage.mShortcutBitmapSaver.waitForAllSavesLocked()
                                        && shortcutInfo.hasIconFile())
                                ? shortcutInfo.getBitmapPath()
                                : null;
            }
            if (bitmapPath == null) {
                Slog.w("ShortcutService", "null bitmap detected in getShortcutIconFd()");
                return null;
            }
            try {
                return ParcelFileDescriptor.open(new File(bitmapPath), 268435456);
            } catch (FileNotFoundException unused) {
                Slog.e("ShortcutService", "Icon file not found: ".concat(bitmapPath));
                return null;
            }
        }

        public final void addListener(
                ShortcutServiceInternal.ShortcutChangeListener shortcutChangeListener) {
            synchronized (ShortcutService.this.mServiceLock) {
                ArrayList arrayList = ShortcutService.this.mListeners;
                Objects.requireNonNull(shortcutChangeListener);
                arrayList.add(shortcutChangeListener);
            }
        }

        public final void addShortcutChangeCallback(
                LauncherApps.ShortcutChangeCallback shortcutChangeCallback) {
            synchronized (ShortcutService.this.mServiceLock) {
                ArrayList arrayList = ShortcutService.this.mShortcutChangeCallbacks;
                Objects.requireNonNull(shortcutChangeCallback);
                arrayList.add(shortcutChangeCallback);
            }
        }

        public final boolean areShortcutsSupportedOnHomeScreen(int i) {
            return ShortcutService.this.areShortcutsSupportedOnHomeScreen(i);
        }

        public final void cacheShortcuts(
                int i, String str, String str2, List list, int i2, int i3) {
            updateCachedShortcutsInternal(i, i2, i3, str2, list, true);
        }

        public final Intent[] createShortcutIntents(
                int i, String str, String str2, String str3, int i2, int i3, int i4) {
            Preconditions.checkStringNotEmpty(str2, "packageName can't be empty");
            Preconditions.checkStringNotEmpty(str3, "shortcutId can't be empty");
            synchronized (ShortcutService.this.mServiceLock) {
                try {
                    ShortcutService.this.throwIfUserLockedL(i2);
                    ShortcutService.this.throwIfUserLockedL(i);
                    ShortcutService.this
                            .getLauncherShortcutsLocked(i2, i, str)
                            .attemptToRestoreIfNeededAndSave();
                    boolean canSeeAnyPinnedShortcut =
                            ShortcutService.this.canSeeAnyPinnedShortcut(i, i3, i4, str);
                    ShortcutInfo shortcutInfoLocked =
                            getShortcutInfoLocked(str, i, str2, str3, i2, canSeeAnyPinnedShortcut);
                    if (shortcutInfoLocked != null
                            && shortcutInfoLocked.isEnabled()
                            && (shortcutInfoLocked.isAlive() || canSeeAnyPinnedShortcut)) {
                        return shortcutInfoLocked.getIntents();
                    }
                    Log.e("ShortcutService", "Shortcut " + str3 + " does not exist or disabled");
                    return null;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void createShortcutIntentsAsync(
                int i,
                String str,
                String str2,
                String str3,
                int i2,
                int i3,
                int i4,
                AndroidFuture androidFuture) {
            Preconditions.checkStringNotEmpty(str2, "packageName can't be empty");
            Preconditions.checkStringNotEmpty(str3, "shortcutId can't be empty");
            synchronized (ShortcutService.this.mServiceLock) {
                try {
                    ShortcutService.this.throwIfUserLockedL(i2);
                    ShortcutService.this.throwIfUserLockedL(i);
                    ShortcutService.this
                            .getLauncherShortcutsLocked(i2, i, str)
                            .attemptToRestoreIfNeededAndSave();
                    boolean canSeeAnyPinnedShortcut =
                            ShortcutService.this.canSeeAnyPinnedShortcut(i, i3, i4, str);
                    ShortcutInfo shortcutInfoLocked =
                            getShortcutInfoLocked(str, i, str2, str3, i2, canSeeAnyPinnedShortcut);
                    if (shortcutInfoLocked == null) {
                        getShortcutInfoAsync(
                                i,
                                str2,
                                str3,
                                i2,
                                new ShortcutService$$ExternalSyntheticLambda6(2, androidFuture));
                        return;
                    }
                    if (shortcutInfoLocked.isEnabled()
                            && (shortcutInfoLocked.isAlive() || canSeeAnyPinnedShortcut)) {
                        androidFuture.complete(shortcutInfoLocked.getIntents());
                        return;
                    }
                    Log.e("ShortcutService", "Shortcut " + str3 + " does not exist or disabled");
                    androidFuture.complete((Object) null);
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final List getShareTargets(String str, IntentFilter intentFilter, int i) {
            return ShortcutService.this.getShareTargets(str, intentFilter, i).getList();
        }

        public final ParcelFileDescriptor getShortcutIconFd(
                int i, String str, String str2, String str3, int i2) {
            Objects.requireNonNull(str, "callingPackage");
            Objects.requireNonNull(str2, "packageName");
            Objects.requireNonNull(str3, "shortcutId");
            synchronized (ShortcutService.this.mServiceLock) {
                try {
                    ShortcutService.this.throwIfUserLockedL(i2);
                    ShortcutService.this.throwIfUserLockedL(i);
                    ShortcutService.this
                            .getLauncherShortcutsLocked(i2, i, str)
                            .attemptToRestoreIfNeededAndSave();
                    ShortcutPackage packageShortcutsIfExists =
                            ShortcutService.this
                                    .getUserShortcutsLocked(i2)
                                    .getPackageShortcutsIfExists(str2);
                    if (packageShortcutsIfExists == null) {
                        return null;
                    }
                    ShortcutInfo findShortcutById = packageShortcutsIfExists.findShortcutById(str3);
                    if (findShortcutById == null) {
                        return null;
                    }
                    return getShortcutIconParcelFileDescriptor(
                            packageShortcutsIfExists, findShortcutById);
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void getShortcutIconFdAsync(
                int i, String str, String str2, String str3, int i2, AndroidFuture androidFuture) {
            Objects.requireNonNull(str, "callingPackage");
            Objects.requireNonNull(str2, "packageName");
            Objects.requireNonNull(str3, "shortcutId");
            synchronized (ShortcutService.this.mServiceLock) {
                try {
                    ShortcutService.this.throwIfUserLockedL(i2);
                    ShortcutService.this.throwIfUserLockedL(i);
                    ShortcutService.this
                            .getLauncherShortcutsLocked(i2, i, str)
                            .attemptToRestoreIfNeededAndSave();
                    ShortcutPackage packageShortcutsIfExists =
                            ShortcutService.this
                                    .getUserShortcutsLocked(i2)
                                    .getPackageShortcutsIfExists(str2);
                    if (packageShortcutsIfExists == null) {
                        androidFuture.complete((Object) null);
                        return;
                    }
                    ShortcutInfo findShortcutById = packageShortcutsIfExists.findShortcutById(str3);
                    if (findShortcutById != null) {
                        androidFuture.complete(
                                getShortcutIconParcelFileDescriptor(
                                        packageShortcutsIfExists, findShortcutById));
                    } else {
                        getShortcutInfoAsync(
                                i,
                                str2,
                                str3,
                                i2,
                                new ShortcutService$$ExternalSyntheticLambda21(
                                        this, androidFuture, packageShortcutsIfExists));
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final int getShortcutIconResId(int i, String str, String str2, String str3, int i2) {
            Objects.requireNonNull(str, "callingPackage");
            Objects.requireNonNull(str2, "packageName");
            Objects.requireNonNull(str3, "shortcutId");
            synchronized (ShortcutService.this.mServiceLock) {
                try {
                    ShortcutService.this.throwIfUserLockedL(i2);
                    ShortcutService.this.throwIfUserLockedL(i);
                    ShortcutService.this
                            .getLauncherShortcutsLocked(i2, i, str)
                            .attemptToRestoreIfNeededAndSave();
                    ShortcutPackage packageShortcutsIfExists =
                            ShortcutService.this
                                    .getUserShortcutsLocked(i2)
                                    .getPackageShortcutsIfExists(str2);
                    int i3 = 0;
                    if (packageShortcutsIfExists == null) {
                        return 0;
                    }
                    ShortcutInfo findShortcutById = packageShortcutsIfExists.findShortcutById(str3);
                    if (findShortcutById != null && findShortcutById.hasIconResource()) {
                        i3 = findShortcutById.getIconResourceId();
                    }
                    return i3;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final String getShortcutIconUri(
                int i, String str, String str2, String str3, int i2) {
            Objects.requireNonNull(str, "launcherPackage");
            Objects.requireNonNull(str2, "packageName");
            Objects.requireNonNull(str3, "shortcutId");
            synchronized (ShortcutService.this.mServiceLock) {
                try {
                    ShortcutService.this.throwIfUserLockedL(i2);
                    ShortcutService.this.throwIfUserLockedL(i);
                    ShortcutService.this
                            .getLauncherShortcutsLocked(i2, i, str)
                            .attemptToRestoreIfNeededAndSave();
                    ShortcutPackage packageShortcutsIfExists =
                            ShortcutService.this
                                    .getUserShortcutsLocked(i2)
                                    .getPackageShortcutsIfExists(str2);
                    if (packageShortcutsIfExists == null) {
                        return null;
                    }
                    ShortcutInfo findShortcutById = packageShortcutsIfExists.findShortcutById(str3);
                    if (findShortcutById == null) {
                        return null;
                    }
                    return getShortcutIconUriInternal(i, str, str2, findShortcutById, i2);
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void getShortcutIconUriAsync(
                final int i,
                final String str,
                final String str2,
                String str3,
                final int i2,
                final AndroidFuture androidFuture) {
            Objects.requireNonNull(str, "launcherPackage");
            Objects.requireNonNull(str2, "packageName");
            Objects.requireNonNull(str3, "shortcutId");
            synchronized (ShortcutService.this.mServiceLock) {
                try {
                    ShortcutService.this.throwIfUserLockedL(i2);
                    ShortcutService.this.throwIfUserLockedL(i);
                    ShortcutService.this
                            .getLauncherShortcutsLocked(i2, i, str)
                            .attemptToRestoreIfNeededAndSave();
                    ShortcutPackage packageShortcutsIfExists =
                            ShortcutService.this
                                    .getUserShortcutsLocked(i2)
                                    .getPackageShortcutsIfExists(str2);
                    if (packageShortcutsIfExists == null) {
                        androidFuture.complete((Object) null);
                        return;
                    }
                    ShortcutInfo findShortcutById = packageShortcutsIfExists.findShortcutById(str3);
                    if (findShortcutById != null) {
                        androidFuture.complete(
                                getShortcutIconUriInternal(i, str, str2, findShortcutById, i2));
                    } else {
                        getShortcutInfoAsync(
                                i,
                                str2,
                                str3,
                                i2,
                                new Consumer() { // from class:
                                                 // com.android.server.pm.ShortcutService$LocalService$$ExternalSyntheticLambda2
                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj) {
                                        String shortcutIconUriInternal;
                                        ShortcutService.LocalService localService =
                                                ShortcutService.LocalService.this;
                                        AndroidFuture androidFuture2 = androidFuture;
                                        int i3 = i;
                                        String str4 = str;
                                        String str5 = str2;
                                        int i4 = i2;
                                        ShortcutInfo shortcutInfo = (ShortcutInfo) obj;
                                        if (shortcutInfo == null) {
                                            localService.getClass();
                                            shortcutIconUriInternal = null;
                                        } else {
                                            shortcutIconUriInternal =
                                                    localService.getShortcutIconUriInternal(
                                                            i3, str4, str5, shortcutInfo, i4);
                                        }
                                        androidFuture2.complete(shortcutIconUriInternal);
                                    }
                                });
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final String getShortcutIconUriInternal(
                int i, String str, String str2, ShortcutInfo shortcutInfo, int i2) {
            if (!shortcutInfo.hasIconUri()) {
                return null;
            }
            String iconUri = shortcutInfo.getIconUri();
            if (iconUri == null) {
                Slog.w("ShortcutService", "null uri detected in getShortcutIconUri()");
                return null;
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                int packageUid =
                        ShortcutService.this.mPackageManagerInternal.getPackageUid(
                                str2, 268435456L, i2);
                ShortcutService shortcutService = ShortcutService.this;
                shortcutService.mUriGrantsManager.grantUriPermissionFromOwner(
                        shortcutService.mUriPermissionOwner,
                        packageUid,
                        str,
                        Uri.parse(iconUri),
                        1,
                        i2,
                        i);
                return iconUri;
            } catch (Exception e) {
                Slog.e(
                        "ShortcutService",
                        "Failed to grant uri access to " + str + " for " + iconUri,
                        e);
                return null;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void getShortcutInfoAsync(
                int i, String str, String str2, int i2, Consumer consumer) {
            ShortcutPackage packageShortcutsIfExists;
            Preconditions.checkStringNotEmpty(str, "packageName");
            Preconditions.checkStringNotEmpty(str2, "shortcutId");
            ShortcutService.this.throwIfUserLockedL(i2);
            ShortcutService.this.throwIfUserLockedL(i);
            synchronized (ShortcutService.this.mServiceLock) {
                packageShortcutsIfExists =
                        ShortcutService.this
                                .getUserShortcutsLocked(i2)
                                .getPackageShortcutsIfExists(str);
            }
            if (packageShortcutsIfExists == null) {
                consumer.accept(null);
                return;
            }
            Set singleton = Collections.singleton(str2);
            ShortcutService$$ExternalSyntheticLambda6 shortcutService$$ExternalSyntheticLambda6 =
                    new ShortcutService$$ExternalSyntheticLambda6(4, consumer);
            if (packageShortcutsIfExists.isAppSearchEnabled()) {
                ShortcutPackage.runAsSystem(
                        new ShortcutPackage$$ExternalSyntheticLambda28(
                                packageShortcutsIfExists,
                                singleton,
                                shortcutService$$ExternalSyntheticLambda6));
            } else {
                shortcutService$$ExternalSyntheticLambda6.accept(Collections.emptyList());
            }
        }

        public final ShortcutInfo getShortcutInfoLocked(
                String str, int i, String str2, String str3, int i2, boolean z) {
            Preconditions.checkStringNotEmpty(str2, "packageName");
            Preconditions.checkStringNotEmpty(str3, "shortcutId");
            ShortcutService.this.throwIfUserLockedL(i2);
            ShortcutService.this.throwIfUserLockedL(i);
            ShortcutPackage packageShortcutsIfExists =
                    ShortcutService.this
                            .getUserShortcutsLocked(i2)
                            .getPackageShortcutsIfExists(str2);
            if (packageShortcutsIfExists == null) {
                return null;
            }
            ArrayList arrayList = new ArrayList(1);
            packageShortcutsIfExists.findAll(
                    arrayList,
                    new ShortcutService$$ExternalSyntheticLambda30(2, str3),
                    0,
                    str,
                    i,
                    z);
            if (arrayList.size() == 0) {
                return null;
            }
            return (ShortcutInfo) arrayList.get(0);
        }

        public final String getShortcutStartingThemeResName(
                int i, String str, String str2, String str3, int i2) {
            Objects.requireNonNull(str, "callingPackage");
            Objects.requireNonNull(str2, "packageName");
            Objects.requireNonNull(str3, "shortcutId");
            synchronized (ShortcutService.this.mServiceLock) {
                try {
                    ShortcutService.this.throwIfUserLockedL(i2);
                    ShortcutService.this.throwIfUserLockedL(i);
                    ShortcutService.this
                            .getLauncherShortcutsLocked(i2, i, str)
                            .attemptToRestoreIfNeededAndSave();
                    ShortcutPackage packageShortcutsIfExists =
                            ShortcutService.this
                                    .getUserShortcutsLocked(i2)
                                    .getPackageShortcutsIfExists(str2);
                    if (packageShortcutsIfExists == null) {
                        return null;
                    }
                    ShortcutInfo findShortcutById = packageShortcutsIfExists.findShortcutById(str3);
                    return findShortcutById != null
                            ? findShortcutById.getStartingThemeResName()
                            : null;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final List getShortcuts(
                final int i,
                final String str,
                final long j,
                String str2,
                List list,
                final List list2,
                final ComponentName componentName,
                final int i2,
                final int i3,
                final int i4,
                final int i5) {
            Object obj;
            final ArrayList arrayList;
            ApplicationInfo applicationInfo;
            StringBuilder m =
                    StorageManagerService$$ExternalSyntheticOutline0.m(
                            i3, "Getting shortcuts for launcher= ", str, "user=", " pkg=");
            m.append(str2);
            Slog.d("ShortcutService", m.toString());
            ArrayList arrayList2 = new ArrayList();
            int i6 =
                    (!"com.sec.android.app.desktoplauncher".equals(str)
                                    || (applicationInfo =
                                                    ShortcutService.this.getApplicationInfo(str, i))
                                            == null
                                    || (applicationInfo.flags & 129) == 0)
                            ? 27
                            : 25;
            if ((i2 & 4) != 0) {
                i6 = 4;
            } else if ((i2 & 2048) != 0) {
                i6 &= -17;
            }
            final int i7 = i6;
            List list3 = str2 == null ? null : list;
            Object obj2 = ShortcutService.this.mServiceLock;
            synchronized (obj2) {
                try {
                    try {
                        ShortcutService.this.throwIfUserLockedL(i3);
                        ShortcutService.this.throwIfUserLockedL(i);
                        ShortcutService.this
                                .getLauncherShortcutsLocked(i3, i, str)
                                .attemptToRestoreIfNeededAndSave();
                        if (str2 != null) {
                            obj = obj2;
                            arrayList = arrayList2;
                            getShortcutsInnerLocked(
                                    i,
                                    str,
                                    str2,
                                    list3,
                                    list2,
                                    j,
                                    componentName,
                                    i2,
                                    i3,
                                    arrayList2,
                                    i7,
                                    i4,
                                    i5);
                        } else {
                            obj = obj2;
                            arrayList = arrayList2;
                            final List list4 = list3;
                            ShortcutService.this
                                    .getUserShortcutsLocked(i3)
                                    .forAllPackages(
                                            new Consumer() { // from class:
                                                             // com.android.server.pm.ShortcutService$LocalService$$ExternalSyntheticLambda5
                                                @Override // java.util.function.Consumer
                                                public final void accept(Object obj3) {
                                                    ShortcutService.LocalService localService =
                                                            ShortcutService.LocalService.this;
                                                    int i8 = i;
                                                    String str3 = str;
                                                    List list5 = list4;
                                                    List list6 = list2;
                                                    long j2 = j;
                                                    ComponentName componentName2 = componentName;
                                                    int i9 = i2;
                                                    int i10 = i3;
                                                    ArrayList arrayList3 = arrayList;
                                                    int i11 = i7;
                                                    int i12 = i4;
                                                    int i13 = i5;
                                                    localService.getClass();
                                                    localService.getShortcutsInnerLocked(
                                                            i8,
                                                            str3,
                                                            ((ShortcutPackage) obj3).mPackageName,
                                                            list5,
                                                            list6,
                                                            j2,
                                                            componentName2,
                                                            i9,
                                                            i10,
                                                            arrayList3,
                                                            i11,
                                                            i12,
                                                            i13);
                                                }
                                            });
                        }
                        ShortcutService.this.getClass();
                        for (int size = arrayList.size() - 1; size >= 0; size--) {
                            ((ShortcutInfo) arrayList.get(size)).setReturnedByServer();
                        }
                        return arrayList;
                    } catch (Throwable th) {
                        th = th;
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    throw th;
                }
            }
            throw th;
        }

        public final void getShortcutsAsync(
                int i,
                String str,
                long j,
                String str2,
                List list,
                List list2,
                ComponentName componentName,
                int i2,
                int i3,
                int i4,
                int i5,
                AndroidFuture androidFuture) {
            ShortcutPackage packageShortcutsIfExists;
            List shortcuts =
                    getShortcuts(i, str, j, str2, list, list2, componentName, i2, i3, i4, i5);
            if (list == null || str2 == null || ((ArrayList) shortcuts).size() >= list.size()) {
                androidFuture.complete(shortcuts);
                return;
            }
            synchronized (ShortcutService.this.mServiceLock) {
                packageShortcutsIfExists =
                        ShortcutService.this
                                .getUserShortcutsLocked(i3)
                                .getPackageShortcutsIfExists(str2);
            }
            if (packageShortcutsIfExists == null) {
                androidFuture.complete(shortcuts);
                return;
            }
            ArraySet arraySet = new ArraySet(list);
            ((List)
                            shortcuts.stream()
                                    .map(new ShortcutPackage$$ExternalSyntheticLambda51(2))
                                    .collect(Collectors.toList()))
                    .forEach(new ShortcutService$$ExternalSyntheticLambda6(3, arraySet));
            ShortcutService$$ExternalSyntheticLambda22 shortcutService$$ExternalSyntheticLambda22 =
                    new ShortcutService$$ExternalSyntheticLambda22(
                            (i2 & 4) != 0 ? 4 : (i2 & 2048) != 0 ? 11 : 27,
                            shortcuts,
                            androidFuture);
            if (packageShortcutsIfExists.isAppSearchEnabled()) {
                ShortcutPackage.runAsSystem(
                        new ShortcutPackage$$ExternalSyntheticLambda28(
                                packageShortcutsIfExists,
                                arraySet,
                                shortcutService$$ExternalSyntheticLambda22));
            } else {
                shortcutService$$ExternalSyntheticLambda22.accept(Collections.emptyList());
            }
        }

        public final void getShortcutsInnerLocked(
                int i,
                String str,
                String str2,
                List list,
                List list2,
                final long j,
                final ComponentName componentName,
                int i2,
                int i3,
                ArrayList arrayList,
                int i4,
                int i5,
                int i6) {
            final ArraySet arraySet = list == null ? null : new ArraySet(list);
            ShortcutPackage packageShortcutsIfExists =
                    ShortcutService.this
                            .getUserShortcutsLocked(i3)
                            .getPackageShortcutsIfExists(str2);
            if (packageShortcutsIfExists == null) {
                DualAppManagerService$$ExternalSyntheticOutline0.m(
                        "getShortcutsInnerLocked() returned empty results because ",
                        str2,
                        " isn't loaded",
                        "ShortcutService");
                return;
            }
            boolean z =
                    ShortcutService.this.canSeeAnyPinnedShortcut(i, i5, i6, str)
                            && (i2 & 1024) != 0;
            int i7 = i2 | (z ? 2 : 0);
            final ArraySet arraySet2 = list2 != null ? new ArraySet(list2) : null;
            final boolean z2 = (i7 & 1) != 0;
            final boolean z3 = (i7 & 2) != 0;
            final boolean z4 = (i7 & 8) != 0;
            final boolean z5 = (i7 & 16) != 0;
            final boolean z6 = z;
            packageShortcutsIfExists.findAll(
                    arrayList,
                    new Predicate() { // from class:
                                      // com.android.server.pm.ShortcutService$LocalService$$ExternalSyntheticLambda10
                        @Override // java.util.function.Predicate
                        public final boolean test(Object obj) {
                            long j2 = j;
                            ArraySet arraySet3 = arraySet;
                            ArraySet arraySet4 = arraySet2;
                            ComponentName componentName2 = componentName;
                            boolean z7 = z2;
                            boolean z8 = z3;
                            boolean z9 = z6;
                            boolean z10 = z4;
                            boolean z11 = z5;
                            ShortcutInfo shortcutInfo = (ShortcutInfo) obj;
                            if (shortcutInfo.getLastChangedTimestamp() < j2) {
                                return false;
                            }
                            if (arraySet3 != null && !arraySet3.contains(shortcutInfo.getId())) {
                                return false;
                            }
                            if (arraySet4 != null
                                    && !arraySet4.contains(shortcutInfo.getLocusId())) {
                                return false;
                            }
                            if (componentName2 == null
                                    || shortcutInfo.getActivity() == null
                                    || shortcutInfo.getActivity().equals(componentName2)) {
                                return (z7 && shortcutInfo.isDynamic())
                                        || ((z8 || z9) && shortcutInfo.isPinned())
                                        || ((z10 && shortcutInfo.isDeclaredInManifest())
                                                || (z11 && shortcutInfo.isCached()));
                            }
                            return false;
                        }
                    },
                    i4,
                    str,
                    i,
                    z);
        }

        public final boolean hasShortcutHostPermission(int i, String str, int i2, int i3) {
            ShortcutService shortcutService = ShortcutService.this;
            if (shortcutService.canSeeAnyPinnedShortcut(i, i2, i3, str)) {
                return true;
            }
            long time = shortcutService.mStatLogger.getTime();
            try {
                return shortcutService.hasShortcutHostPermissionInner(str, i);
            } finally {
                shortcutService.logDurationStat(4, time);
            }
        }

        public final boolean isForegroundDefaultLauncher(String str, int i) {
            Objects.requireNonNull(str);
            String defaultLauncher =
                    ShortcutService.this.getDefaultLauncher(UserHandle.getUserId(i));
            if (defaultLauncher == null || !str.equals(defaultLauncher)) {
                return false;
            }
            synchronized (ShortcutService.this.mServiceLock) {
                try {
                    return ShortcutService.this.isUidForegroundLocked(i);
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final boolean isPinnedByCaller(int i, String str, String str2, String str3, int i2) {
            boolean z;
            Preconditions.checkStringNotEmpty(str2, "packageName");
            Preconditions.checkStringNotEmpty(str3, "shortcutId");
            synchronized (ShortcutService.this.mServiceLock) {
                try {
                    ShortcutService.this.throwIfUserLockedL(i2);
                    ShortcutService.this.throwIfUserLockedL(i);
                    ShortcutService.this
                            .getLauncherShortcutsLocked(i2, i, str)
                            .attemptToRestoreIfNeededAndSave();
                    ShortcutInfo shortcutInfoLocked =
                            getShortcutInfoLocked(str, i, str2, str3, i2, false);
                    z = shortcutInfoLocked != null && shortcutInfoLocked.isPinned();
                } finally {
                }
            }
            return z;
        }

        public final boolean isRequestPinItemSupported(int i, int i2) {
            return ShortcutService.this.isRequestPinItemSupported(i, i2);
        }

        public final boolean isSharingShortcut(
                int i, String str, String str2, String str3, int i2, IntentFilter intentFilter) {
            Preconditions.checkStringNotEmpty(str, "callingPackage");
            Preconditions.checkStringNotEmpty(str2, "packageName");
            Preconditions.checkStringNotEmpty(str3, "shortcutId");
            ShortcutService shortcutService = ShortcutService.this;
            shortcutService.verifyCaller(i, str);
            if (!shortcutService.isCallerSystem()) {
                shortcutService.injectEnforceCallingPermission(
                        "android.permission.MANAGE_APP_PREDICTIONS", "isSharingShortcut");
            }
            synchronized (shortcutService.mServiceLock) {
                try {
                    shortcutService.throwIfUserLockedL(i2);
                    shortcutService.throwIfUserLockedL(i);
                    ArrayList arrayList =
                            (ArrayList)
                                    shortcutService
                                            .getUserShortcutsLocked(i2)
                                            .getPackageShortcuts(str2)
                                            .getMatchingShareTargets(intentFilter, null);
                    int size = arrayList.size();
                    for (int i3 = 0; i3 < size; i3++) {
                        if (((ShortcutManager.ShareShortcutInfo) arrayList.get(i3))
                                .getShortcutInfo()
                                .getId()
                                .equals(str3)) {
                            return true;
                        }
                    }
                    return false;
                } finally {
                }
            }
        }

        public final void pinShortcuts(int i, String str, String str2, List list, int i2) {
            ShortcutPackage packageShortcutsIfExists;
            ArrayList arrayList;
            List prepareChangedShortcuts;
            Preconditions.checkStringNotEmpty(str2, "packageName");
            Objects.requireNonNull(list, "shortcutIds");
            synchronized (ShortcutService.this.mServiceLock) {
                try {
                    ShortcutService.this.throwIfUserLockedL(i2);
                    ShortcutService.this.throwIfUserLockedL(i);
                    ShortcutLauncher launcherShortcutsLocked =
                            ShortcutService.this.getLauncherShortcutsLocked(i2, i, str);
                    launcherShortcutsLocked.attemptToRestoreIfNeededAndSave();
                    packageShortcutsIfExists =
                            ShortcutService.this
                                    .getUserShortcutsLocked(i2)
                                    .getPackageShortcutsIfExists(str2);
                    if (packageShortcutsIfExists != null) {
                        arrayList = new ArrayList();
                        packageShortcutsIfExists.findAll(
                                arrayList,
                                new ShortcutService$$ExternalSyntheticLambda12(3),
                                4,
                                str,
                                i,
                                false);
                    } else {
                        arrayList = null;
                    }
                    ArraySet pinnedShortcutIds =
                            launcherShortcutsLocked.getPinnedShortcutIds(i2, str2);
                    launcherShortcutsLocked.pinShortcuts(i2, str2, false, list);
                    if (pinnedShortcutIds != null && arrayList != null) {
                        for (int i3 = 0; i3 < arrayList.size(); i3++) {
                            pinnedShortcutIds.remove(((ShortcutInfo) arrayList.get(i3)).getId());
                        }
                    }
                    ShortcutService shortcutService = ShortcutService.this;
                    ArraySet arraySet = new ArraySet(list);
                    shortcutService.getClass();
                    prepareChangedShortcuts =
                            ShortcutService.prepareChangedShortcuts(
                                    pinnedShortcutIds,
                                    arraySet,
                                    arrayList,
                                    packageShortcutsIfExists);
                } catch (Throwable th) {
                    throw th;
                }
            }
            if (packageShortcutsIfExists != null) {
                ShortcutService.this.packageShortcutsChanged(
                        packageShortcutsIfExists, prepareChangedShortcuts, arrayList);
            }
            ShortcutService.this.verifyStates();
        }

        public final boolean requestPinAppWidget(
                String str,
                AppWidgetProviderInfo appWidgetProviderInfo,
                Bundle bundle,
                IntentSender intentSender,
                int i) {
            Objects.requireNonNull(appWidgetProviderInfo);
            ShortcutService shortcutService = ShortcutService.this;
            return shortcutService.requestPinItem(
                    str,
                    i,
                    null,
                    appWidgetProviderInfo,
                    bundle,
                    intentSender,
                    shortcutService.injectBinderCallingPid(),
                    shortcutService.injectBinderCallingUid(),
                    0);
        }

        public final void setShortcutHostPackage(String str, String str2, int i) {
            ShortcutService shortcutService = ShortcutService.this;
            synchronized (shortcutService.mNonPersistentUsersLock) {
                ShortcutNonPersistentUser shortcutNonPersistentUser =
                        (ShortcutNonPersistentUser)
                                shortcutService.mShortcutNonPersistentUsers.get(i);
                if (shortcutNonPersistentUser == null) {
                    shortcutNonPersistentUser = new ShortcutNonPersistentUser(i);
                    shortcutService.mShortcutNonPersistentUsers.put(i, shortcutNonPersistentUser);
                }
                if (str2 != null) {
                    shortcutNonPersistentUser.mHostPackages.put(str, str2);
                } else {
                    shortcutNonPersistentUser.mHostPackages.remove(str);
                }
                shortcutNonPersistentUser.mHostPackageSet.clear();
                for (int i2 = 0; i2 < shortcutNonPersistentUser.mHostPackages.size(); i2++) {
                    shortcutNonPersistentUser.mHostPackageSet.add(
                            (String) shortcutNonPersistentUser.mHostPackages.valueAt(i2));
                }
            }
        }

        public final void uncacheShortcuts(
                int i, String str, String str2, List list, int i2, int i3) {
            updateCachedShortcutsInternal(i, i2, i3, str2, list, false);
        }

        public final void updateCachedShortcutsInternal(
                int i, int i2, int i3, String str, List list, boolean z) {
            int i4;
            ArrayList arrayList;
            ShortcutInfo shortcutInfo;
            ArrayList arrayList2;
            ShortcutInfo shortcutInfo2;
            Preconditions.checkStringNotEmpty(str, "packageName");
            Objects.requireNonNull(list, "shortcutIds");
            int i5 = 0;
            int i6 = 1;
            Preconditions.checkState((1610629120 & i3) != 0, "invalid cacheFlags");
            synchronized (ShortcutService.this.mServiceLock) {
                try {
                    ShortcutService.this.throwIfUserLockedL(i2);
                    ShortcutService.this.throwIfUserLockedL(i);
                    int size = list.size();
                    ShortcutPackage packageShortcutsIfExists =
                            ShortcutService.this
                                    .getUserShortcutsLocked(i2)
                                    .getPackageShortcutsIfExists(str);
                    if (size != 0 && packageShortcutsIfExists != null) {
                        ShortcutInfo shortcutInfo3 = null;
                        ArrayList arrayList3 = null;
                        ArrayList arrayList4 = null;
                        while (i5 < size) {
                            String str2 =
                                    (String)
                                            Preconditions.checkStringNotEmpty(
                                                    (String) list.get(i5));
                            ShortcutInfo findShortcutById =
                                    packageShortcutsIfExists.findShortcutById(str2);
                            if (findShortcutById != null && z != findShortcutById.hasFlags(i3)) {
                                if (!z) {
                                    findShortcutById.clearFlags(i3);
                                    if (findShortcutById.isDynamic()
                                            || findShortcutById.isCached()) {
                                        shortcutInfo = findShortcutById;
                                        arrayList2 = arrayList3;
                                        arrayList = arrayList4;
                                        shortcutInfo2 = null;
                                    } else {
                                        if (packageShortcutsIfExists.findShortcutById(str2)
                                                != null) {
                                            packageShortcutsIfExists.mutateShortcut(
                                                    str2,
                                                    shortcutInfo3,
                                                    new ShortcutPackage$$ExternalSyntheticLambda14(
                                                            0));
                                        }
                                        shortcutInfo = findShortcutById;
                                        arrayList2 = arrayList3;
                                        arrayList = arrayList4;
                                        shortcutInfo2 =
                                                packageShortcutsIfExists.deleteOrDisableWithId(
                                                        str2, false, false, true, 0, false);
                                    }
                                    if (shortcutInfo2 == null) {
                                        arrayList4 =
                                                arrayList == null ? new ArrayList(1) : arrayList;
                                        arrayList4.add(shortcutInfo);
                                        arrayList3 = arrayList2;
                                        i4 = 1;
                                    } else {
                                        ArrayList arrayList5 = arrayList2;
                                        if (arrayList5 == null) {
                                            i4 = 1;
                                            arrayList3 = new ArrayList(1);
                                        } else {
                                            i4 = 1;
                                            arrayList3 = arrayList5;
                                        }
                                        arrayList3.add(shortcutInfo2);
                                        arrayList4 = arrayList;
                                    }
                                } else if (findShortcutById.isLongLived()) {
                                    findShortcutById.addFlags(i3);
                                    if (arrayList4 == null) {
                                        arrayList4 = new ArrayList(i6);
                                    }
                                    arrayList4.add(findShortcutById);
                                    i4 = i6;
                                } else {
                                    Log.w(
                                            "ShortcutService",
                                            "Only long lived shortcuts can get cached. Ignoring id "
                                                    + findShortcutById.getId());
                                }
                                i5++;
                                i6 = i4;
                                shortcutInfo3 = null;
                            }
                            i4 = i6;
                            arrayList = arrayList4;
                            arrayList3 = arrayList3;
                            arrayList4 = arrayList;
                            i5++;
                            i6 = i4;
                            shortcutInfo3 = null;
                        }
                        ShortcutService.this.packageShortcutsChanged(
                                packageShortcutsIfExists, arrayList4, arrayList3);
                        ShortcutService.this.verifyStates();
                    }
                } finally {
                }
            }
        }
    }

    public final class MyShellCommand extends ShellCommand {
        public int mUserId = 0;
        public int mShortcutMatchFlags = 15;

        public MyShellCommand() {}

        public final void handleClearShortcuts() {
            synchronized (ShortcutService.this.mServiceLock) {
                parseOptionsLocked();
                String nextArgRequired = getNextArgRequired();
                Slog.i(
                        "ShellCommand",
                        "cmd: handleClearShortcuts: user" + this.mUserId + ", " + nextArgRequired);
                ShortcutService.this.cleanUpPackageForAllLoadedUsers(
                        this.mUserId, nextArgRequired, true);
            }
        }

        public final void handleGetDefaultLauncher() {
            synchronized (ShortcutService.this.mServiceLock) {
                try {
                    parseOptionsLocked();
                    String defaultLauncher = ShortcutService.this.getDefaultLauncher(this.mUserId);
                    if (defaultLauncher == null) {
                        throw new CommandException(
                                "Failed to get the default launcher for user " + this.mUserId);
                    }
                    ArrayList arrayList = new ArrayList();
                    ShortcutService shortcutService = ShortcutService.this;
                    ((PackageManagerService.PackageManagerInternalImpl)
                                    shortcutService.mPackageManagerInternal)
                            .mService
                            .snapshotComputer()
                            .getHomeActivitiesAsUser(
                                    shortcutService.mUserManagerInternal.getProfileParentId(
                                            this.mUserId),
                                    arrayList);
                    Iterator it = arrayList.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        ComponentInfo componentInfo = ((ResolveInfo) it.next()).getComponentInfo();
                        if (componentInfo.packageName.equals(defaultLauncher)) {
                            getOutPrintWriter()
                                    .println("Launcher: " + componentInfo.getComponentName());
                            break;
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void handleGetShortcuts() {
            synchronized (ShortcutService.this.mServiceLock) {
                try {
                    parseOptionsLocked();
                    String nextArgRequired = getNextArgRequired();
                    Slog.i(
                            "ShellCommand",
                            "cmd: handleGetShortcuts: user="
                                    + this.mUserId
                                    + ", flags="
                                    + this.mShortcutMatchFlags
                                    + ", package="
                                    + nextArgRequired);
                    ShortcutPackage packageShortcutsIfExists =
                            ShortcutService.this
                                    .getUserShortcutsLocked(this.mUserId)
                                    .getPackageShortcutsIfExists(nextArgRequired);
                    if (packageShortcutsIfExists == null) {
                        return;
                    }
                    final PrintWriter outPrintWriter = getOutPrintWriter();
                    int i = this.mShortcutMatchFlags;
                    boolean z = true;
                    int i2 = (i & 2) != 0 ? 1 : 0;
                    boolean z2 = (i & 4) != 0;
                    boolean z3 = (i & 1) != 0;
                    if ((i & 8) == 0) {
                        z = false;
                    }
                    final int i3 = (z2 ? 2 : 0) | i2 | (z3 ? 32 : 0) | (z ? 1610629120 : 0);
                    packageShortcutsIfExists.forEachShortcut(
                            new Consumer() { // from class:
                                             // com.android.server.pm.ShortcutPackage$$ExternalSyntheticLambda53
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj) {
                                    int i4 = i3;
                                    PrintWriter printWriter = outPrintWriter;
                                    ShortcutInfo shortcutInfo = (ShortcutInfo) obj;
                                    if ((i4 & shortcutInfo.getFlags()) != 0) {
                                        printWriter.println(shortcutInfo.toDumpString(""));
                                    }
                                }
                            });
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void handleOverrideConfig() {
            String nextArgRequired = getNextArgRequired();
            Slog.i("ShellCommand", "cmd: handleOverrideConfig: " + nextArgRequired);
            synchronized (ShortcutService.this.mServiceLock) {
                try {
                    if (!ShortcutService.this.updateConfigurationLocked(nextArgRequired)) {
                        throw new CommandException(
                                "override-config failed.  See logcat for details.");
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void handleResetThrottling() {
            synchronized (ShortcutService.this.mServiceLock) {
                parseOptionsLocked();
                Slog.i("ShellCommand", "cmd: handleResetThrottling: user=" + this.mUserId);
                ShortcutService.this.resetThrottlingInner(this.mUserId);
            }
        }

        public final void handleUnloadUser() {
            synchronized (ShortcutService.this.mServiceLock) {
                parseOptionsLocked();
                Slog.i("ShellCommand", "cmd: handleUnloadUser: user=" + this.mUserId);
                ShortcutService.this.handleStopUser(this.mUserId);
            }
        }

        public final int onCommand(String str) {
            char c;
            if (str == null) {
                return handleDefaultCommands(str);
            }
            PrintWriter outPrintWriter = getOutPrintWriter();
            try {
                switch (str.hashCode()) {
                    case -1610733672:
                        if (str.equals("has-shortcut-access")) {
                            c = '\t';
                            break;
                        }
                        c = 65535;
                        break;
                    case -1117067818:
                        if (str.equals("verify-states")) {
                            c = '\b';
                            break;
                        }
                        c = 65535;
                        break;
                    case -749565587:
                        if (str.equals("clear-shortcuts")) {
                            c = 6;
                            break;
                        }
                        c = 65535;
                        break;
                    case -276993226:
                        if (str.equals("get-shortcuts")) {
                            c = 7;
                            break;
                        }
                        c = 65535;
                        break;
                    case -139706031:
                        if (str.equals("reset-all-throttling")) {
                            c = 1;
                            break;
                        }
                        c = 65535;
                        break;
                    case -76794781:
                        if (str.equals("override-config")) {
                            c = 2;
                            break;
                        }
                        c = 65535;
                        break;
                    case 188791973:
                        if (str.equals("reset-throttling")) {
                            c = 0;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1190495043:
                        if (str.equals("get-default-launcher")) {
                            c = 4;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1411888601:
                        if (str.equals("unload-user")) {
                            c = 5;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1964247424:
                        if (str.equals("reset-config")) {
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
                        handleResetThrottling();
                        outPrintWriter.println("Success");
                        return 0;
                    case 1:
                        Slog.i("ShellCommand", "cmd: handleResetAllThrottling");
                        ShortcutService shortcutService = ShortcutService.this;
                        shortcutService.mRawLastResetTime.set(
                                shortcutService.injectCurrentTimeMillis());
                        shortcutService.scheduleSaveInner(-10000);
                        Slog.i(
                                "ShortcutService",
                                "ShortcutManager: throttling counter reset for all users");
                        outPrintWriter.println("Success");
                        return 0;
                    case 2:
                        handleOverrideConfig();
                        outPrintWriter.println("Success");
                        return 0;
                    case 3:
                        Slog.i("ShellCommand", "cmd: handleResetConfig");
                        synchronized (ShortcutService.this.mServiceLock) {
                            ShortcutService shortcutService2 = ShortcutService.this;
                            shortcutService2.updateConfigurationLocked(
                                    shortcutService2.injectShortcutManagerConstants());
                        }
                        outPrintWriter.println("Success");
                        return 0;
                    case 4:
                        handleGetDefaultLauncher();
                        outPrintWriter.println("Success");
                        return 0;
                    case 5:
                        handleUnloadUser();
                        outPrintWriter.println("Success");
                        return 0;
                    case 6:
                        handleClearShortcuts();
                        outPrintWriter.println("Success");
                        return 0;
                    case 7:
                        handleGetShortcuts();
                        outPrintWriter.println("Success");
                        return 0;
                    case '\b':
                        try {
                            ShortcutService shortcutService3 = ShortcutService.this;
                            synchronized (shortcutService3.mServiceLock) {
                                shortcutService3.forEachLoadedUserLocked(
                                        new ShortcutService$$ExternalSyntheticLambda4(1));
                            }
                            outPrintWriter.println("Success");
                            return 0;
                        } catch (Throwable th) {
                            throw new CommandException(
                                    th.getMessage() + "\n" + Log.getStackTraceString(th));
                        }
                    case '\t':
                        synchronized (ShortcutService.this.mServiceLock) {
                            parseOptionsLocked();
                            getOutPrintWriter()
                                    .println(
                                            Boolean.toString(
                                                    ShortcutService.this
                                                            .hasShortcutHostPermissionInner(
                                                                    getNextArgRequired(),
                                                                    this.mUserId)));
                        }
                        outPrintWriter.println("Success");
                        return 0;
                    default:
                        return handleDefaultCommands(str);
                }
            } catch (CommandException e) {
                outPrintWriter.println("Error: " + e.getMessage());
                return 1;
            }
        }

        public final void onHelp() {
            PrintWriter outPrintWriter = getOutPrintWriter();
            outPrintWriter.println("Usage: cmd shortcut COMMAND [options ...]");
            outPrintWriter.println();
            outPrintWriter.println("cmd shortcut reset-throttling [--user USER_ID]");
            outPrintWriter.println("    Reset throttling for all packages and users");
            outPrintWriter.println();
            outPrintWriter.println("cmd shortcut reset-all-throttling");
            outPrintWriter.println("    Reset the throttling state for all users");
            outPrintWriter.println();
            outPrintWriter.println("cmd shortcut override-config CONFIG");
            outPrintWriter.println(
                    "    Override the configuration for testing (will last until reboot)");
            outPrintWriter.println();
            outPrintWriter.println("cmd shortcut reset-config");
            outPrintWriter.println("    Reset the configuration set with \"update-config\"");
            outPrintWriter.println();
            outPrintWriter.println(
                    "[Deprecated] cmd shortcut get-default-launcher [--user USER_ID]");
            outPrintWriter.println("    Show the default launcher");
            outPrintWriter.println(
                    "    Note: This command is deprecated. Callers should query the default"
                        + " launcher from RoleManager instead.");
            outPrintWriter.println();
            outPrintWriter.println("cmd shortcut unload-user [--user USER_ID]");
            outPrintWriter.println("    Unload a user from the memory");
            outPrintWriter.println("    (This should not affect any observable behavior)");
            outPrintWriter.println();
            outPrintWriter.println("cmd shortcut clear-shortcuts [--user USER_ID] PACKAGE");
            outPrintWriter.println(
                    "    Remove all shortcuts from a package, including pinned shortcuts");
            outPrintWriter.println();
            outPrintWriter.println(
                    "cmd shortcut get-shortcuts [--user USER_ID] [--flags FLAGS] PACKAGE");
            outPrintWriter.println(
                    "    Show the shortcuts for a package that match the given flags");
            outPrintWriter.println();
            outPrintWriter.println("cmd shortcut has-shortcut-access [--user USER_ID] PACKAGE");
            outPrintWriter.println(
                    "    Prints \"true\" if the package can access shortcuts, \"false\" otherwise");
            outPrintWriter.println();
        }

        public final void parseOptionsLocked() {
            while (true) {
                String nextOption = getNextOption();
                if (nextOption == null) {
                    return;
                }
                if (nextOption.equals("--flags")) {
                    this.mShortcutMatchFlags = Integer.parseInt(getNextArgRequired());
                } else {
                    if (!nextOption.equals("--user")) {
                        throw new CommandException("Unknown option: ".concat(nextOption));
                    }
                    int parseUserArg = UserHandle.parseUserArg(getNextArgRequired());
                    this.mUserId = parseUserArg;
                    if (!ShortcutService.this.isUserUnlockedL(parseUserArg)) {
                        throw new CommandException(
                                AmFmBandRange$$ExternalSyntheticOutline0.m(
                                        this.mUserId,
                                        new StringBuilder("User "),
                                        " is not running or locked"));
                    }
                }
            }
        }
    }

    public final class SettingChangeObserver extends ContentObserver {
        public Context mContext;

        @Override // android.database.ContentObserver
        public final void onChange(boolean z) {
            boolean z2 =
                    Settings.System.getInt(this.mContext.getContentResolver(), "emergency_mode", 0)
                            == 1;
            AtomicBoolean atomicBoolean = ShortcutService.sIsEmergencyMode;
            if (atomicBoolean.get() != z2) {
                Slog.d(
                        "ShortcutService",
                        "EmergencyMode changes: " + atomicBoolean.get() + " -> " + z2);
                atomicBoolean.set(z2);
            }
        }
    }

    interface Stats {}

    /* renamed from: -$$Nest$mhandlePackageAdded, reason: not valid java name */
    public static void m774$$Nest$mhandlePackageAdded(
            ShortcutService shortcutService, String str, int i) {
        shortcutService.getClass();
        Slog.d(
                "ShortcutService",
                String.format("handlePackageAdded: %s user=%d", str, Integer.valueOf(i)));
        synchronized (shortcutService.mServiceLock) {
            ShortcutUser userShortcutsLocked = shortcutService.getUserShortcutsLocked(i);
            userShortcutsLocked.forAllPackageItems(
                    new ShortcutUser$$ExternalSyntheticLambda6(
                            i, str, new ShortcutUser$$ExternalSyntheticLambda0(1)));
            userShortcutsLocked.rescanPackageIfNeeded(str, true);
        }
        shortcutService.verifyStates();
    }

    /* renamed from: -$$Nest$mhandlePackageChanged, reason: not valid java name */
    public static void m775$$Nest$mhandlePackageChanged(
            ShortcutService shortcutService, String str, int i) {
        if (!shortcutService.isPackageInstalled(i, str)) {
            Slog.d(
                    "ShortcutService",
                    String.format("handlePackageRemoved: %s user=%d", str, Integer.valueOf(i)));
            shortcutService.cleanUpPackageForAllLoadedUsers(i, str, false);
            shortcutService.verifyStates();
        } else {
            Slog.d(
                    "ShortcutService",
                    String.format("handlePackageChanged: %s user=%d", str, Integer.valueOf(i)));
            synchronized (shortcutService.mServiceLock) {
                shortcutService.getUserShortcutsLocked(i).rescanPackageIfNeeded(str, true);
            }
            shortcutService.verifyStates();
        }
    }

    /* renamed from: -$$Nest$mhandlePackageDataCleared, reason: not valid java name */
    public static void m776$$Nest$mhandlePackageDataCleared(
            ShortcutService shortcutService, String str, int i) {
        shortcutService.getClass();
        Slog.d(
                "ShortcutService",
                String.format("handlePackageDataCleared: %s user=%d", str, Integer.valueOf(i)));
        shortcutService.cleanUpPackageForAllLoadedUsers(i, str, true);
        shortcutService.verifyStates();
    }

    /* renamed from: -$$Nest$mhandlePackageUpdateFinished, reason: not valid java name */
    public static void m777$$Nest$mhandlePackageUpdateFinished(
            ShortcutService shortcutService, String str, int i) {
        shortcutService.getClass();
        Slog.d(
                "ShortcutService",
                String.format("handlePackageUpdateFinished: %s user=%d", str, Integer.valueOf(i)));
        synchronized (shortcutService.mServiceLock) {
            try {
                ShortcutUser userShortcutsLocked = shortcutService.getUserShortcutsLocked(i);
                userShortcutsLocked.forAllPackageItems(
                        new ShortcutUser$$ExternalSyntheticLambda6(
                                i, str, new ShortcutUser$$ExternalSyntheticLambda0(1)));
                if (shortcutService.isPackageInstalled(i, str)) {
                    userShortcutsLocked.rescanPackageIfNeeded(str, true);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        shortcutService.verifyStates();
    }

    /* JADX WARN: Type inference failed for: r0v4, types: [com.android.server.pm.ShortcutService$1] */
    /* JADX WARN: Type inference failed for: r0v6, types: [com.android.server.pm.ShortcutService$1] */
    static {
        final int i = 0;
        ACTIVITY_NOT_EXPORTED =
                new Predicate() { // from class: com.android.server.pm.ShortcutService.1
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        switch (i) {
                            case 0:
                                return !((ResolveInfo) obj).activityInfo.exported;
                            default:
                                PackageInfo packageInfo = (PackageInfo) obj;
                                return true
                                        ^ (packageInfo != null
                                                && ShortcutService.isInstalled(
                                                        packageInfo.applicationInfo));
                        }
                    }
                };
        final int i2 = 1;
        PACKAGE_NOT_INSTALLED =
                new Predicate() { // from class: com.android.server.pm.ShortcutService.1
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        switch (i2) {
                            case 0:
                                return !((ResolveInfo) obj).activityInfo.exported;
                            default:
                                PackageInfo packageInfo = (PackageInfo) obj;
                                return true
                                        ^ (packageInfo != null
                                                && ShortcutService.isInstalled(
                                                        packageInfo.applicationInfo));
                        }
                    }
                };
    }

    /* JADX WARN: Type inference failed for: r2v17, types: [com.android.server.pm.ShortcutService$$ExternalSyntheticLambda9] */
    public ShortcutService(Context context, Looper looper, boolean z) {
        Object obj = new Object();
        this.mServiceLock = obj;
        this.mNonPersistentUsersLock = new Object();
        this.mWtfLock = new Object();
        this.mListeners = new ArrayList(1);
        this.mShortcutChangeCallbacks = new ArrayList(1);
        this.mRawLastResetTime = new AtomicLong(0L);
        this.mUsers = new SparseArray();
        this.mShortcutNonPersistentUsers = new SparseArray();
        this.mUidState = new SparseIntArray();
        this.mUidLastForegroundElapsedTime = new SparseLongArray();
        this.mDirtyUserIds = new ArrayList();
        this.mBootCompleted = new AtomicBoolean();
        this.mShutdown = new AtomicBoolean();
        this.mUnlockedUsers = new SparseBooleanArray();
        this.mStatLogger =
                new StatLogger(
                        new String[] {
                            "getHomeActivities()",
                            "Launcher permission check",
                            "getPackageInfo()",
                            "getPackageInfo(SIG)",
                            "getApplicationInfo",
                            "cleanupDanglingBitmaps",
                            "getActivity+metadata",
                            "getInstalledPackages",
                            "checkPackageChanges",
                            "getApplicationResources",
                            "resourceNameLookup",
                            "getLauncherActivity",
                            "checkLauncherActivity",
                            "isActivityEnabled",
                            "packageUpdateCheck",
                            "asyncPreloadUserDelay",
                            "getDefaultLauncher()"
                        });
        this.mWtfCount = 0;
        this.mMetricsLogger = new MetricsLogger();
        this.mOnRoleHoldersChangedListener = new AnonymousClass3();
        this.mUidObserver = new AnonymousClass4();
        this.mSaveDirtyInfoRunner =
                new Runnable() { // from class:
                                 // com.android.server.pm.ShortcutService$$ExternalSyntheticLambda9
                    @Override // java.lang.Runnable
                    public final void run() {
                        ShortcutService.this.saveDirtyInfo();
                    }
                };
        this.mLastLockedUser = -1;
        final int i = 0;
        BroadcastReceiver broadcastReceiver =
                new BroadcastReceiver(this) { // from class: com.android.server.pm.ShortcutService.5
                    public final /* synthetic */ ShortcutService this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        long j;
                        ShortcutService shortcutService;
                        char c;
                        ShortcutService shortcutService2;
                        switch (i) {
                            case 0:
                                if (this.this$0.mBootCompleted.get()) {
                                    try {
                                        if ("android.intent.action.LOCALE_CHANGED"
                                                .equals(intent.getAction())) {
                                            this.this$0.handleLocaleChanged(false);
                                            return;
                                        }
                                        return;
                                    } catch (Exception e) {
                                        this.this$0.wtf("Exception in mReceiver.onReceive", e);
                                        return;
                                    }
                                }
                                return;
                            case 1:
                                int intExtra =
                                        intent.getIntExtra(
                                                "android.intent.extra.user_handle", -10000);
                                if (intExtra == -10000) {
                                    Slog.w(
                                            "ShortcutService",
                                            "Intent broadcast does not contain user handle: "
                                                    + intent);
                                    return;
                                }
                                String action = intent.getAction();
                                this.this$0.getClass();
                                long clearCallingIdentity = Binder.clearCallingIdentity();
                                try {
                                    try {
                                    } catch (Exception e2) {
                                        e = e2;
                                        j = clearCallingIdentity;
                                    } catch (Throwable th) {
                                        th = th;
                                        this.this$0.getClass();
                                        Binder.restoreCallingIdentity(clearCallingIdentity);
                                        throw th;
                                    }
                                    synchronized (this.this$0.mServiceLock) {
                                        try {
                                            try {
                                            } catch (Throwable th2) {
                                                th = th2;
                                                while (true) {
                                                    try {
                                                        throw th;
                                                    } catch (Throwable th3) {
                                                        th = th3;
                                                    }
                                                }
                                            }
                                        } catch (Exception e3) {
                                            e = e3;
                                            this.this$0.wtf(
                                                    "Exception in mPackageMonitor.onReceive", e);
                                            shortcutService = this.this$0;
                                            shortcutService.getClass();
                                            Binder.restoreCallingIdentity(j);
                                            return;
                                        }
                                        if (this.this$0.isUserUnlockedL(intExtra)) {
                                            Uri data = intent.getData();
                                            String schemeSpecificPart =
                                                    data != null
                                                            ? data.getSchemeSpecificPart()
                                                            : null;
                                            if (schemeSpecificPart != null) {
                                                boolean booleanExtra =
                                                        intent.getBooleanExtra(
                                                                "android.intent.extra.REPLACING",
                                                                false);
                                                j = clearCallingIdentity;
                                                boolean booleanExtra2 =
                                                        intent.getBooleanExtra(
                                                                "android.intent.extra.ARCHIVAL",
                                                                false);
                                                Slog.d(
                                                        "ShortcutService",
                                                        "received package broadcast intent: "
                                                                + intent);
                                                switch (action.hashCode()) {
                                                    case 172491798:
                                                        if (action.equals(
                                                                "android.intent.action.PACKAGE_CHANGED")) {
                                                            c = 2;
                                                            break;
                                                        }
                                                        c = 65535;
                                                        break;
                                                    case 267468725:
                                                        if (action.equals(
                                                                "android.intent.action.PACKAGE_DATA_CLEARED")) {
                                                            c = 3;
                                                            break;
                                                        }
                                                        c = 65535;
                                                        break;
                                                    case 525384130:
                                                        if (action.equals(
                                                                "android.intent.action.PACKAGE_REMOVED")) {
                                                            c = 1;
                                                            break;
                                                        }
                                                        c = 65535;
                                                        break;
                                                    case 1544582882:
                                                        if (action.equals(
                                                                "android.intent.action.PACKAGE_ADDED")) {
                                                            c = 0;
                                                            break;
                                                        }
                                                        c = 65535;
                                                        break;
                                                    default:
                                                        c = 65535;
                                                        break;
                                                }
                                                if (c != 0) {
                                                    if (c != 1) {
                                                        if (c == 2) {
                                                            int intExtra2 =
                                                                    intent.getIntExtra(
                                                                            "EM_PKG_HADNLER_ID",
                                                                            -1);
                                                            if (intExtra2 != -1) {
                                                                Slog.d(
                                                                        "ShortcutService",
                                                                        "Skip PACKAGE_CHANGED"
                                                                            + " intent from"
                                                                            + " Emergency mode: "
                                                                                + intExtra2
                                                                                + ", pkg: "
                                                                                + schemeSpecificPart);
                                                            } else {
                                                                Slog.d(
                                                                        "ShortcutService",
                                                                        "changing package: "
                                                                                + schemeSpecificPart
                                                                                + " userId"
                                                                                + intExtra);
                                                                ShortcutService
                                                                        .m775$$Nest$mhandlePackageChanged(
                                                                                this.this$0,
                                                                                schemeSpecificPart,
                                                                                intExtra);
                                                            }
                                                        } else if (c == 3) {
                                                            Slog.d(
                                                                    "ShortcutService",
                                                                    "clearing data for package: "
                                                                            + schemeSpecificPart
                                                                            + " userId"
                                                                            + intExtra);
                                                            ShortcutService
                                                                    .m776$$Nest$mhandlePackageDataCleared(
                                                                            this.this$0,
                                                                            schemeSpecificPart,
                                                                            intExtra);
                                                        }
                                                    } else if (!booleanExtra
                                                            || (booleanExtra && booleanExtra2)) {
                                                        if (!booleanExtra) {
                                                            Slog.d(
                                                                    "ShortcutService",
                                                                    "removing package: "
                                                                            + schemeSpecificPart
                                                                            + " userId"
                                                                            + intExtra);
                                                        } else if (booleanExtra2) {
                                                            Slog.d(
                                                                    "ShortcutService",
                                                                    "archiving package: "
                                                                            + schemeSpecificPart
                                                                            + " userId"
                                                                            + intExtra);
                                                        }
                                                        ShortcutService shortcutService3 =
                                                                this.this$0;
                                                        shortcutService3.getClass();
                                                        Slog.d(
                                                                "ShortcutService",
                                                                String.format(
                                                                        "handlePackageRemoved: %s"
                                                                            + " user=%d",
                                                                        schemeSpecificPart,
                                                                        Integer.valueOf(intExtra)));
                                                        shortcutService3
                                                                .cleanUpPackageForAllLoadedUsers(
                                                                        intExtra,
                                                                        schemeSpecificPart,
                                                                        false);
                                                        shortcutService3.verifyStates();
                                                    }
                                                } else if (booleanExtra) {
                                                    Slog.d(
                                                            "ShortcutService",
                                                            "replacing package: "
                                                                    + schemeSpecificPart
                                                                    + " userId"
                                                                    + intExtra);
                                                    ShortcutService
                                                            .m777$$Nest$mhandlePackageUpdateFinished(
                                                                    this.this$0,
                                                                    schemeSpecificPart,
                                                                    intExtra);
                                                } else {
                                                    Slog.d(
                                                            "ShortcutService",
                                                            "adding package: "
                                                                    + schemeSpecificPart
                                                                    + " userId"
                                                                    + intExtra);
                                                    ShortcutService.m774$$Nest$mhandlePackageAdded(
                                                            this.this$0,
                                                            schemeSpecificPart,
                                                            intExtra);
                                                }
                                                shortcutService = this.this$0;
                                                shortcutService.getClass();
                                                Binder.restoreCallingIdentity(j);
                                                return;
                                            }
                                            Slog.w(
                                                    "ShortcutService",
                                                    "Intent broadcast does not contain package"
                                                        + " name: "
                                                            + intent);
                                            shortcutService2 = this.this$0;
                                        } else {
                                            shortcutService2 = this.this$0;
                                        }
                                    }
                                    shortcutService2.getClass();
                                    Binder.restoreCallingIdentity(clearCallingIdentity);
                                    return;
                                } catch (Throwable th4) {
                                    th = th4;
                                    this.this$0.getClass();
                                    Binder.restoreCallingIdentity(clearCallingIdentity);
                                    throw th;
                                }
                            default:
                                Slog.d("ShortcutService", "Shutdown broadcast received.");
                                synchronized (this.this$0.mServiceLock) {
                                    try {
                                        ShortcutService shortcutService4 = this.this$0;
                                        if (shortcutService4.mHandler.hasCallbacks(
                                                shortcutService4.mSaveDirtyInfoRunner)) {
                                            ShortcutService shortcutService5 = this.this$0;
                                            shortcutService5.mHandler.removeCallbacks(
                                                    shortcutService5.mSaveDirtyInfoRunner);
                                            this.this$0.forEachLoadedUserLocked(
                                                    new ShortcutService$$ExternalSyntheticLambda4(
                                                            10));
                                            this.this$0.saveDirtyInfo();
                                        }
                                        this.this$0.mShutdown.set(true);
                                    } finally {
                                    }
                                }
                                return;
                        }
                    }
                };
        final int i2 = 1;
        BroadcastReceiver broadcastReceiver2 =
                new BroadcastReceiver(this) { // from class: com.android.server.pm.ShortcutService.5
                    public final /* synthetic */ ShortcutService this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        long j;
                        ShortcutService shortcutService;
                        char c;
                        ShortcutService shortcutService2;
                        switch (i2) {
                            case 0:
                                if (this.this$0.mBootCompleted.get()) {
                                    try {
                                        if ("android.intent.action.LOCALE_CHANGED"
                                                .equals(intent.getAction())) {
                                            this.this$0.handleLocaleChanged(false);
                                            return;
                                        }
                                        return;
                                    } catch (Exception e) {
                                        this.this$0.wtf("Exception in mReceiver.onReceive", e);
                                        return;
                                    }
                                }
                                return;
                            case 1:
                                int intExtra =
                                        intent.getIntExtra(
                                                "android.intent.extra.user_handle", -10000);
                                if (intExtra == -10000) {
                                    Slog.w(
                                            "ShortcutService",
                                            "Intent broadcast does not contain user handle: "
                                                    + intent);
                                    return;
                                }
                                String action = intent.getAction();
                                this.this$0.getClass();
                                long clearCallingIdentity = Binder.clearCallingIdentity();
                                try {
                                    try {
                                    } catch (Exception e2) {
                                        e = e2;
                                        j = clearCallingIdentity;
                                    } catch (Throwable th) {
                                        th = th;
                                        this.this$0.getClass();
                                        Binder.restoreCallingIdentity(clearCallingIdentity);
                                        throw th;
                                    }
                                    synchronized (this.this$0.mServiceLock) {
                                        try {
                                            try {
                                            } catch (Throwable th2) {
                                                th = th2;
                                                while (true) {
                                                    try {
                                                        throw th;
                                                    } catch (Throwable th3) {
                                                        th = th3;
                                                    }
                                                }
                                            }
                                        } catch (Exception e3) {
                                            e = e3;
                                            this.this$0.wtf(
                                                    "Exception in mPackageMonitor.onReceive", e);
                                            shortcutService = this.this$0;
                                            shortcutService.getClass();
                                            Binder.restoreCallingIdentity(j);
                                            return;
                                        }
                                        if (this.this$0.isUserUnlockedL(intExtra)) {
                                            Uri data = intent.getData();
                                            String schemeSpecificPart =
                                                    data != null
                                                            ? data.getSchemeSpecificPart()
                                                            : null;
                                            if (schemeSpecificPart != null) {
                                                boolean booleanExtra =
                                                        intent.getBooleanExtra(
                                                                "android.intent.extra.REPLACING",
                                                                false);
                                                j = clearCallingIdentity;
                                                boolean booleanExtra2 =
                                                        intent.getBooleanExtra(
                                                                "android.intent.extra.ARCHIVAL",
                                                                false);
                                                Slog.d(
                                                        "ShortcutService",
                                                        "received package broadcast intent: "
                                                                + intent);
                                                switch (action.hashCode()) {
                                                    case 172491798:
                                                        if (action.equals(
                                                                "android.intent.action.PACKAGE_CHANGED")) {
                                                            c = 2;
                                                            break;
                                                        }
                                                        c = 65535;
                                                        break;
                                                    case 267468725:
                                                        if (action.equals(
                                                                "android.intent.action.PACKAGE_DATA_CLEARED")) {
                                                            c = 3;
                                                            break;
                                                        }
                                                        c = 65535;
                                                        break;
                                                    case 525384130:
                                                        if (action.equals(
                                                                "android.intent.action.PACKAGE_REMOVED")) {
                                                            c = 1;
                                                            break;
                                                        }
                                                        c = 65535;
                                                        break;
                                                    case 1544582882:
                                                        if (action.equals(
                                                                "android.intent.action.PACKAGE_ADDED")) {
                                                            c = 0;
                                                            break;
                                                        }
                                                        c = 65535;
                                                        break;
                                                    default:
                                                        c = 65535;
                                                        break;
                                                }
                                                if (c != 0) {
                                                    if (c != 1) {
                                                        if (c == 2) {
                                                            int intExtra2 =
                                                                    intent.getIntExtra(
                                                                            "EM_PKG_HADNLER_ID",
                                                                            -1);
                                                            if (intExtra2 != -1) {
                                                                Slog.d(
                                                                        "ShortcutService",
                                                                        "Skip PACKAGE_CHANGED"
                                                                            + " intent from"
                                                                            + " Emergency mode: "
                                                                                + intExtra2
                                                                                + ", pkg: "
                                                                                + schemeSpecificPart);
                                                            } else {
                                                                Slog.d(
                                                                        "ShortcutService",
                                                                        "changing package: "
                                                                                + schemeSpecificPart
                                                                                + " userId"
                                                                                + intExtra);
                                                                ShortcutService
                                                                        .m775$$Nest$mhandlePackageChanged(
                                                                                this.this$0,
                                                                                schemeSpecificPart,
                                                                                intExtra);
                                                            }
                                                        } else if (c == 3) {
                                                            Slog.d(
                                                                    "ShortcutService",
                                                                    "clearing data for package: "
                                                                            + schemeSpecificPart
                                                                            + " userId"
                                                                            + intExtra);
                                                            ShortcutService
                                                                    .m776$$Nest$mhandlePackageDataCleared(
                                                                            this.this$0,
                                                                            schemeSpecificPart,
                                                                            intExtra);
                                                        }
                                                    } else if (!booleanExtra
                                                            || (booleanExtra && booleanExtra2)) {
                                                        if (!booleanExtra) {
                                                            Slog.d(
                                                                    "ShortcutService",
                                                                    "removing package: "
                                                                            + schemeSpecificPart
                                                                            + " userId"
                                                                            + intExtra);
                                                        } else if (booleanExtra2) {
                                                            Slog.d(
                                                                    "ShortcutService",
                                                                    "archiving package: "
                                                                            + schemeSpecificPart
                                                                            + " userId"
                                                                            + intExtra);
                                                        }
                                                        ShortcutService shortcutService3 =
                                                                this.this$0;
                                                        shortcutService3.getClass();
                                                        Slog.d(
                                                                "ShortcutService",
                                                                String.format(
                                                                        "handlePackageRemoved: %s"
                                                                            + " user=%d",
                                                                        schemeSpecificPart,
                                                                        Integer.valueOf(intExtra)));
                                                        shortcutService3
                                                                .cleanUpPackageForAllLoadedUsers(
                                                                        intExtra,
                                                                        schemeSpecificPart,
                                                                        false);
                                                        shortcutService3.verifyStates();
                                                    }
                                                } else if (booleanExtra) {
                                                    Slog.d(
                                                            "ShortcutService",
                                                            "replacing package: "
                                                                    + schemeSpecificPart
                                                                    + " userId"
                                                                    + intExtra);
                                                    ShortcutService
                                                            .m777$$Nest$mhandlePackageUpdateFinished(
                                                                    this.this$0,
                                                                    schemeSpecificPart,
                                                                    intExtra);
                                                } else {
                                                    Slog.d(
                                                            "ShortcutService",
                                                            "adding package: "
                                                                    + schemeSpecificPart
                                                                    + " userId"
                                                                    + intExtra);
                                                    ShortcutService.m774$$Nest$mhandlePackageAdded(
                                                            this.this$0,
                                                            schemeSpecificPart,
                                                            intExtra);
                                                }
                                                shortcutService = this.this$0;
                                                shortcutService.getClass();
                                                Binder.restoreCallingIdentity(j);
                                                return;
                                            }
                                            Slog.w(
                                                    "ShortcutService",
                                                    "Intent broadcast does not contain package"
                                                        + " name: "
                                                            + intent);
                                            shortcutService2 = this.this$0;
                                        } else {
                                            shortcutService2 = this.this$0;
                                        }
                                    }
                                    shortcutService2.getClass();
                                    Binder.restoreCallingIdentity(clearCallingIdentity);
                                    return;
                                } catch (Throwable th4) {
                                    th = th4;
                                    this.this$0.getClass();
                                    Binder.restoreCallingIdentity(clearCallingIdentity);
                                    throw th;
                                }
                            default:
                                Slog.d("ShortcutService", "Shutdown broadcast received.");
                                synchronized (this.this$0.mServiceLock) {
                                    try {
                                        ShortcutService shortcutService4 = this.this$0;
                                        if (shortcutService4.mHandler.hasCallbacks(
                                                shortcutService4.mSaveDirtyInfoRunner)) {
                                            ShortcutService shortcutService5 = this.this$0;
                                            shortcutService5.mHandler.removeCallbacks(
                                                    shortcutService5.mSaveDirtyInfoRunner);
                                            this.this$0.forEachLoadedUserLocked(
                                                    new ShortcutService$$ExternalSyntheticLambda4(
                                                            10));
                                            this.this$0.saveDirtyInfo();
                                        }
                                        this.this$0.mShutdown.set(true);
                                    } finally {
                                    }
                                }
                                return;
                        }
                    }
                };
        this.mPackageMonitor = broadcastReceiver2;
        final int i3 = 2;
        BroadcastReceiver broadcastReceiver3 =
                new BroadcastReceiver(this) { // from class: com.android.server.pm.ShortcutService.5
                    public final /* synthetic */ ShortcutService this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        long j;
                        ShortcutService shortcutService;
                        char c;
                        ShortcutService shortcutService2;
                        switch (i3) {
                            case 0:
                                if (this.this$0.mBootCompleted.get()) {
                                    try {
                                        if ("android.intent.action.LOCALE_CHANGED"
                                                .equals(intent.getAction())) {
                                            this.this$0.handleLocaleChanged(false);
                                            return;
                                        }
                                        return;
                                    } catch (Exception e) {
                                        this.this$0.wtf("Exception in mReceiver.onReceive", e);
                                        return;
                                    }
                                }
                                return;
                            case 1:
                                int intExtra =
                                        intent.getIntExtra(
                                                "android.intent.extra.user_handle", -10000);
                                if (intExtra == -10000) {
                                    Slog.w(
                                            "ShortcutService",
                                            "Intent broadcast does not contain user handle: "
                                                    + intent);
                                    return;
                                }
                                String action = intent.getAction();
                                this.this$0.getClass();
                                long clearCallingIdentity = Binder.clearCallingIdentity();
                                try {
                                    try {
                                    } catch (Exception e2) {
                                        e = e2;
                                        j = clearCallingIdentity;
                                    } catch (Throwable th) {
                                        th = th;
                                        this.this$0.getClass();
                                        Binder.restoreCallingIdentity(clearCallingIdentity);
                                        throw th;
                                    }
                                    synchronized (this.this$0.mServiceLock) {
                                        try {
                                            try {
                                            } catch (Throwable th2) {
                                                th = th2;
                                                while (true) {
                                                    try {
                                                        throw th;
                                                    } catch (Throwable th3) {
                                                        th = th3;
                                                    }
                                                }
                                            }
                                        } catch (Exception e3) {
                                            e = e3;
                                            this.this$0.wtf(
                                                    "Exception in mPackageMonitor.onReceive", e);
                                            shortcutService = this.this$0;
                                            shortcutService.getClass();
                                            Binder.restoreCallingIdentity(j);
                                            return;
                                        }
                                        if (this.this$0.isUserUnlockedL(intExtra)) {
                                            Uri data = intent.getData();
                                            String schemeSpecificPart =
                                                    data != null
                                                            ? data.getSchemeSpecificPart()
                                                            : null;
                                            if (schemeSpecificPart != null) {
                                                boolean booleanExtra =
                                                        intent.getBooleanExtra(
                                                                "android.intent.extra.REPLACING",
                                                                false);
                                                j = clearCallingIdentity;
                                                boolean booleanExtra2 =
                                                        intent.getBooleanExtra(
                                                                "android.intent.extra.ARCHIVAL",
                                                                false);
                                                Slog.d(
                                                        "ShortcutService",
                                                        "received package broadcast intent: "
                                                                + intent);
                                                switch (action.hashCode()) {
                                                    case 172491798:
                                                        if (action.equals(
                                                                "android.intent.action.PACKAGE_CHANGED")) {
                                                            c = 2;
                                                            break;
                                                        }
                                                        c = 65535;
                                                        break;
                                                    case 267468725:
                                                        if (action.equals(
                                                                "android.intent.action.PACKAGE_DATA_CLEARED")) {
                                                            c = 3;
                                                            break;
                                                        }
                                                        c = 65535;
                                                        break;
                                                    case 525384130:
                                                        if (action.equals(
                                                                "android.intent.action.PACKAGE_REMOVED")) {
                                                            c = 1;
                                                            break;
                                                        }
                                                        c = 65535;
                                                        break;
                                                    case 1544582882:
                                                        if (action.equals(
                                                                "android.intent.action.PACKAGE_ADDED")) {
                                                            c = 0;
                                                            break;
                                                        }
                                                        c = 65535;
                                                        break;
                                                    default:
                                                        c = 65535;
                                                        break;
                                                }
                                                if (c != 0) {
                                                    if (c != 1) {
                                                        if (c == 2) {
                                                            int intExtra2 =
                                                                    intent.getIntExtra(
                                                                            "EM_PKG_HADNLER_ID",
                                                                            -1);
                                                            if (intExtra2 != -1) {
                                                                Slog.d(
                                                                        "ShortcutService",
                                                                        "Skip PACKAGE_CHANGED"
                                                                            + " intent from"
                                                                            + " Emergency mode: "
                                                                                + intExtra2
                                                                                + ", pkg: "
                                                                                + schemeSpecificPart);
                                                            } else {
                                                                Slog.d(
                                                                        "ShortcutService",
                                                                        "changing package: "
                                                                                + schemeSpecificPart
                                                                                + " userId"
                                                                                + intExtra);
                                                                ShortcutService
                                                                        .m775$$Nest$mhandlePackageChanged(
                                                                                this.this$0,
                                                                                schemeSpecificPart,
                                                                                intExtra);
                                                            }
                                                        } else if (c == 3) {
                                                            Slog.d(
                                                                    "ShortcutService",
                                                                    "clearing data for package: "
                                                                            + schemeSpecificPart
                                                                            + " userId"
                                                                            + intExtra);
                                                            ShortcutService
                                                                    .m776$$Nest$mhandlePackageDataCleared(
                                                                            this.this$0,
                                                                            schemeSpecificPart,
                                                                            intExtra);
                                                        }
                                                    } else if (!booleanExtra
                                                            || (booleanExtra && booleanExtra2)) {
                                                        if (!booleanExtra) {
                                                            Slog.d(
                                                                    "ShortcutService",
                                                                    "removing package: "
                                                                            + schemeSpecificPart
                                                                            + " userId"
                                                                            + intExtra);
                                                        } else if (booleanExtra2) {
                                                            Slog.d(
                                                                    "ShortcutService",
                                                                    "archiving package: "
                                                                            + schemeSpecificPart
                                                                            + " userId"
                                                                            + intExtra);
                                                        }
                                                        ShortcutService shortcutService3 =
                                                                this.this$0;
                                                        shortcutService3.getClass();
                                                        Slog.d(
                                                                "ShortcutService",
                                                                String.format(
                                                                        "handlePackageRemoved: %s"
                                                                            + " user=%d",
                                                                        schemeSpecificPart,
                                                                        Integer.valueOf(intExtra)));
                                                        shortcutService3
                                                                .cleanUpPackageForAllLoadedUsers(
                                                                        intExtra,
                                                                        schemeSpecificPart,
                                                                        false);
                                                        shortcutService3.verifyStates();
                                                    }
                                                } else if (booleanExtra) {
                                                    Slog.d(
                                                            "ShortcutService",
                                                            "replacing package: "
                                                                    + schemeSpecificPart
                                                                    + " userId"
                                                                    + intExtra);
                                                    ShortcutService
                                                            .m777$$Nest$mhandlePackageUpdateFinished(
                                                                    this.this$0,
                                                                    schemeSpecificPart,
                                                                    intExtra);
                                                } else {
                                                    Slog.d(
                                                            "ShortcutService",
                                                            "adding package: "
                                                                    + schemeSpecificPart
                                                                    + " userId"
                                                                    + intExtra);
                                                    ShortcutService.m774$$Nest$mhandlePackageAdded(
                                                            this.this$0,
                                                            schemeSpecificPart,
                                                            intExtra);
                                                }
                                                shortcutService = this.this$0;
                                                shortcutService.getClass();
                                                Binder.restoreCallingIdentity(j);
                                                return;
                                            }
                                            Slog.w(
                                                    "ShortcutService",
                                                    "Intent broadcast does not contain package"
                                                        + " name: "
                                                            + intent);
                                            shortcutService2 = this.this$0;
                                        } else {
                                            shortcutService2 = this.this$0;
                                        }
                                    }
                                    shortcutService2.getClass();
                                    Binder.restoreCallingIdentity(clearCallingIdentity);
                                    return;
                                } catch (Throwable th4) {
                                    th = th4;
                                    this.this$0.getClass();
                                    Binder.restoreCallingIdentity(clearCallingIdentity);
                                    throw th;
                                }
                            default:
                                Slog.d("ShortcutService", "Shutdown broadcast received.");
                                synchronized (this.this$0.mServiceLock) {
                                    try {
                                        ShortcutService shortcutService4 = this.this$0;
                                        if (shortcutService4.mHandler.hasCallbacks(
                                                shortcutService4.mSaveDirtyInfoRunner)) {
                                            ShortcutService shortcutService5 = this.this$0;
                                            shortcutService5.mHandler.removeCallbacks(
                                                    shortcutService5.mSaveDirtyInfoRunner);
                                            this.this$0.forEachLoadedUserLocked(
                                                    new ShortcutService$$ExternalSyntheticLambda4(
                                                            10));
                                            this.this$0.saveDirtyInfo();
                                        }
                                        this.this$0.mShutdown.set(true);
                                    } finally {
                                    }
                                }
                                return;
                        }
                    }
                };
        Objects.requireNonNull(context);
        this.mContext = context;
        LocalServices.addService(ShortcutServiceInternal.class, new LocalService());
        Handler handler = new Handler(looper);
        this.mHandler = handler;
        this.mIPackageManager = AppGlobals.getPackageManager();
        PackageManagerInternal packageManagerInternal =
                (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
        Objects.requireNonNull(packageManagerInternal);
        this.mPackageManagerInternal = packageManagerInternal;
        UserManagerInternal userManagerInternal =
                (UserManagerInternal) LocalServices.getService(UserManagerInternal.class);
        Objects.requireNonNull(userManagerInternal);
        this.mUserManagerInternal = userManagerInternal;
        UsageStatsManagerInternal usageStatsManagerInternal =
                (UsageStatsManagerInternal)
                        LocalServices.getService(UsageStatsManagerInternal.class);
        Objects.requireNonNull(usageStatsManagerInternal);
        this.mUsageStatsManagerInternal = usageStatsManagerInternal;
        ActivityManagerInternal activityManagerInternal =
                (ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class);
        Objects.requireNonNull(activityManagerInternal);
        this.mActivityManagerInternal = activityManagerInternal;
        IUriGrantsManager service = UriGrantsManager.getService();
        Objects.requireNonNull(service);
        this.mUriGrantsManager = service;
        UriGrantsManagerInternal uriGrantsManagerInternal =
                (UriGrantsManagerInternal) LocalServices.getService(UriGrantsManagerInternal.class);
        Objects.requireNonNull(uriGrantsManagerInternal);
        this.mUriGrantsManagerInternal = uriGrantsManagerInternal;
        this.mUriPermissionOwner =
                ((UriGrantsManagerService.LocalService) uriGrantsManagerInternal)
                        .newUriPermissionOwner("ShortcutService");
        RoleManager roleManager = (RoleManager) context.getSystemService(RoleManager.class);
        Objects.requireNonNull(roleManager);
        this.mRoleManager = roleManager;
        this.mShortcutRequestPinProcessor = new ShortcutRequestPinProcessor(this, obj);
        this.mShortcutDumpFiles = new ShortcutDumpFiles(this);
        this.mIsAppSearchEnabled =
                DeviceConfig.getBoolean("systemui", "shortcut_appsearch_integration", false)
                        && !injectIsLowRamDevice();
        if (z) {
            return;
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter.addAction("android.intent.action.PACKAGE_CHANGED");
        intentFilter.addAction("android.intent.action.PACKAGE_DATA_CLEARED");
        intentFilter.addDataScheme("package");
        intentFilter.setPriority(1000);
        UserHandle userHandle = UserHandle.ALL;
        context.registerReceiverAsUser(broadcastReceiver2, userHandle, intentFilter, null, handler);
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction("android.intent.action.LOCALE_CHANGED");
        intentFilter2.setPriority(1000);
        context.registerReceiverAsUser(broadcastReceiver, userHandle, intentFilter2, null, handler);
        IntentFilter intentFilter3 = new IntentFilter();
        intentFilter3.addAction("android.intent.action.ACTION_SHUTDOWN");
        intentFilter3.setPriority(1000);
        context.registerReceiverAsUser(
                broadcastReceiver3, UserHandle.SYSTEM, intentFilter3, null, handler);
        try {
            SettingChangeObserver settingChangeObserver = new SettingChangeObserver(new Handler());
            settingChangeObserver.mContext = context;
            settingChangeObserver.onChange(true);
            context.getContentResolver()
                    .registerContentObserver(
                            Settings.System.getUriFor("emergency_mode"),
                            false,
                            settingChangeObserver);
        } catch (Exception unused) {
        }
        injectRegisterUidObserver(this.mUidObserver, 3);
        injectRegisterRoleHoldersListener(this.mOnRoleHoldersChangedListener);
    }

    public static void addShortcutIdsToSet(ArraySet arraySet, List list) {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        int size = list.size();
        for (int i = 0; i < size; i++) {
            arraySet.add(((ShortcutInfo) list.get(i)).getId());
        }
    }

    public static void assignImplicitRanks(List list) {
        for (int size = list.size() - 1; size >= 0; size--) {
            ((ShortcutInfo) list.get(size)).setImplicitRank(size);
        }
    }

    public static boolean isInstalled(ApplicationInfo applicationInfo) {
        return applicationInfo != null
                && (applicationInfo.enabled || sIsEmergencyMode.get())
                && (applicationInfo.flags & 8388608) != 0;
    }

    public static long parseLongAttribute(
            TypedXmlPullParser typedXmlPullParser, String str, long j) {
        String attributeValue = typedXmlPullParser.getAttributeValue((String) null, str);
        if (TextUtils.isEmpty(attributeValue)) {
            return j;
        }
        try {
            return Long.parseLong(attributeValue);
        } catch (NumberFormatException unused) {
            BootReceiver$$ExternalSyntheticOutline0.m(
                    "Error parsing long ", attributeValue, "ShortcutService");
            return j;
        }
    }

    public static List prepareChangedShortcuts(
            ArraySet arraySet, ArraySet arraySet2, List list, ShortcutPackage shortcutPackage) {
        if (shortcutPackage == null) {
            return null;
        }
        if (CollectionUtils.isEmpty(arraySet) && CollectionUtils.isEmpty(arraySet2)) {
            return null;
        }
        ArraySet arraySet3 = new ArraySet();
        if (!CollectionUtils.isEmpty(arraySet)) {
            arraySet3.addAll(arraySet);
        }
        if (!CollectionUtils.isEmpty(arraySet2)) {
            arraySet3.addAll(arraySet2);
        }
        if (!CollectionUtils.isEmpty(list)) {
            list.removeIf(new ShortcutService$$ExternalSyntheticLambda30(0, arraySet3));
        }
        ArrayList arrayList = new ArrayList();
        shortcutPackage.findAll(
                arrayList,
                new ShortcutService$$ExternalSyntheticLambda30(1, arraySet3),
                4,
                null,
                0,
                false);
        return arrayList;
    }

    public static List removeNonKeyFields(List list) {
        if (CollectionUtils.isEmpty(list)) {
            return list;
        }
        int size = list.size();
        ArrayList arrayList = new ArrayList(size);
        for (int i = 0; i < size; i++) {
            ShortcutInfo shortcutInfo = (ShortcutInfo) list.get(i);
            if (shortcutInfo.hasKeyFieldsOnly()) {
                arrayList.add(shortcutInfo);
            } else {
                arrayList.add(shortcutInfo.clone(4));
            }
        }
        return arrayList;
    }

    public static Bitmap shrinkBitmap(int i, Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        if (width <= i && height <= i) {
            return bitmap;
        }
        int max = Math.max(width, height);
        int i2 = (width * i) / max;
        int i3 = (height * i) / max;
        Bitmap createBitmap = Bitmap.createBitmap(i2, i3, Bitmap.Config.ARGB_8888);
        new Canvas(createBitmap)
                .drawBitmap(
                        bitmap,
                        (Rect) null,
                        new RectF(
                                FullScreenMagnificationGestureHandler.MAX_SCALE,
                                FullScreenMagnificationGestureHandler.MAX_SCALE,
                                i2,
                                i3),
                        (Paint) null);
        return createBitmap;
    }

    public static void throwForInvalidTag(int i, String str) {
        throw new IOException(
                String.format("Invalid tag '%s' found at depth %d", str, Integer.valueOf(i)));
    }

    public static void warnForInvalidTag(int i, String str) {
        Slog.w(
                "ShortcutService",
                String.format("Invalid tag '%s' found at depth %d", str, Integer.valueOf(i)));
    }

    public static void writeAttr(TypedXmlSerializer typedXmlSerializer, String str, long j) {
        writeAttr(typedXmlSerializer, str, String.valueOf(j));
    }

    public static void writeAttr(
            TypedXmlSerializer typedXmlSerializer, String str, CharSequence charSequence) {
        if (TextUtils.isEmpty(charSequence)) {
            return;
        }
        typedXmlSerializer.attribute((String) null, str, charSequence.toString());
    }

    public static void writeAttr(TypedXmlSerializer typedXmlSerializer, String str, boolean z) {
        if (z) {
            writeAttr(typedXmlSerializer, str, "1");
        } else {
            writeAttr(typedXmlSerializer, str, "0");
        }
    }

    public final boolean addDynamicShortcuts(
            String str, ParceledListSlice parceledListSlice, int i) {
        verifyCaller(i, str);
        boolean injectHasUnlimitedShortcutsApiCallsPermission =
                injectHasUnlimitedShortcutsApiCallsPermission(
                        injectBinderCallingPid(), injectBinderCallingUid());
        List list = parceledListSlice.getList();
        verifyShortcutInfoPackages(str, list);
        int size = list.size();
        synchronized (this.mServiceLock) {
            try {
                throwIfUserLockedL(i);
                ShortcutPackage packageShortcutsForPublisherLocked =
                        getPackageShortcutsForPublisherLocked(i, str);
                packageShortcutsForPublisherLocked.ensureImmutableShortcutsNotIncluded(list);
                ShortcutPackage.ensureNoBitmapIconIfShortcutIsLongLived(list);
                fillInDefaultActivity(list);
                packageShortcutsForPublisherLocked.enforceShortcutCountsBeforeOperation(1, list);
                packageShortcutsForPublisherLocked.forEachShortcutMutate(
                        new ShortcutPackage$$ExternalSyntheticLambda14(1));
                assignImplicitRanks(list);
                if (!packageShortcutsForPublisherLocked.tryApiCall(
                        injectHasUnlimitedShortcutsApiCallsPermission)) {
                    return false;
                }
                ArrayList arrayList = null;
                for (int i2 = 0; i2 < size; i2++) {
                    ShortcutInfo shortcutInfo = (ShortcutInfo) list.get(i2);
                    fixUpIncomingShortcutInfo(shortcutInfo, false, false);
                    shortcutInfo.setRankChanged();
                    packageShortcutsForPublisherLocked.addOrReplaceDynamicShortcut(shortcutInfo);
                    if (arrayList == null) {
                        arrayList = new ArrayList(1);
                    }
                    arrayList.add(shortcutInfo);
                }
                packageShortcutsForPublisherLocked.adjustRanks();
                packageShortcutsChanged(packageShortcutsForPublisherLocked, arrayList, null);
                verifyStates();
                return true;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void allowSmartSwitchBackup(boolean z) {
        this.mSmartSwitchBackupAllowed.set(z);
    }

    public final void applyRestore(byte[] bArr, int i) {
        if (!isCallerSystem()) {
            throw new SecurityException("Caller must be system");
        }
        AnyMotionDetector$$ExternalSyntheticOutline0.m(i, "Restoring user ", "ShortcutService");
        synchronized (this.mServiceLock) {
            try {
                if (!isUserUnlockedL(i)) {
                    wtf("Can't restore: user " + i + " is locked or not running", null);
                    return;
                }
                final int i2 = 0;
                this.mShortcutDumpFiles.save(
                        "restore-0-start.txt",
                        new Consumer(
                                this) { // from class:
                                        // com.android.server.pm.ShortcutService$$ExternalSyntheticLambda0
                            public final /* synthetic */ ShortcutService f$0;

                            {
                                this.f$0 = this;
                            }

                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                int i3 = i2;
                                ShortcutService shortcutService = this.f$0;
                                PrintWriter printWriter = (PrintWriter) obj;
                                shortcutService.getClass();
                                switch (i3) {
                                    case 0:
                                        printWriter.print("Start time: ");
                                        printWriter.print(
                                                TimeMigrationUtils.formatMillisWithFixedFormat(
                                                        shortcutService.injectCurrentTimeMillis()));
                                        printWriter.println();
                                        break;
                                    case 1:
                                        shortcutService.dumpInner(
                                                printWriter, new ShortcutService.DumpFilter());
                                        break;
                                    default:
                                        printWriter.print("Finish time: ");
                                        printWriter.print(
                                                TimeMigrationUtils.formatMillisWithFixedFormat(
                                                        shortcutService.injectCurrentTimeMillis()));
                                        printWriter.println();
                                        break;
                                }
                            }
                        });
                ShortcutDumpFiles shortcutDumpFiles = this.mShortcutDumpFiles;
                shortcutDumpFiles.getClass();
                shortcutDumpFiles.save(
                        "restore-1-payload.xml",
                        new ShortcutDumpFiles$$ExternalSyntheticLambda0(bArr));
                try {
                    ShortcutUser loadUserInternal =
                            loadUserInternal(i, new ByteArrayInputStream(bArr), true);
                    final int i3 = 1;
                    this.mShortcutDumpFiles.save(
                            "restore-2.txt",
                            new Consumer(
                                    this) { // from class:
                                            // com.android.server.pm.ShortcutService$$ExternalSyntheticLambda0
                                public final /* synthetic */ ShortcutService f$0;

                                {
                                    this.f$0 = this;
                                }

                                @Override // java.util.function.Consumer
                                public final void accept(Object obj) {
                                    int i32 = i3;
                                    ShortcutService shortcutService = this.f$0;
                                    PrintWriter printWriter = (PrintWriter) obj;
                                    shortcutService.getClass();
                                    switch (i32) {
                                        case 0:
                                            printWriter.print("Start time: ");
                                            printWriter.print(
                                                    TimeMigrationUtils.formatMillisWithFixedFormat(
                                                            shortcutService
                                                                    .injectCurrentTimeMillis()));
                                            printWriter.println();
                                            break;
                                        case 1:
                                            shortcutService.dumpInner(
                                                    printWriter, new ShortcutService.DumpFilter());
                                            break;
                                        default:
                                            printWriter.print("Finish time: ");
                                            printWriter.print(
                                                    TimeMigrationUtils.formatMillisWithFixedFormat(
                                                            shortcutService
                                                                    .injectCurrentTimeMillis()));
                                            printWriter.println();
                                            break;
                                    }
                                }
                            });
                    getUserShortcutsLocked(i).mergeRestoredFile(loadUserInternal);
                    final int i4 = 1;
                    this.mShortcutDumpFiles.save(
                            "restore-3.txt",
                            new Consumer(
                                    this) { // from class:
                                            // com.android.server.pm.ShortcutService$$ExternalSyntheticLambda0
                                public final /* synthetic */ ShortcutService f$0;

                                {
                                    this.f$0 = this;
                                }

                                @Override // java.util.function.Consumer
                                public final void accept(Object obj) {
                                    int i32 = i4;
                                    ShortcutService shortcutService = this.f$0;
                                    PrintWriter printWriter = (PrintWriter) obj;
                                    shortcutService.getClass();
                                    switch (i32) {
                                        case 0:
                                            printWriter.print("Start time: ");
                                            printWriter.print(
                                                    TimeMigrationUtils.formatMillisWithFixedFormat(
                                                            shortcutService
                                                                    .injectCurrentTimeMillis()));
                                            printWriter.println();
                                            break;
                                        case 1:
                                            shortcutService.dumpInner(
                                                    printWriter, new ShortcutService.DumpFilter());
                                            break;
                                        default:
                                            printWriter.print("Finish time: ");
                                            printWriter.print(
                                                    TimeMigrationUtils.formatMillisWithFixedFormat(
                                                            shortcutService
                                                                    .injectCurrentTimeMillis()));
                                            printWriter.println();
                                            break;
                                    }
                                }
                            });
                    rescanUpdatedPackagesLocked(i, 0L);
                    final int i5 = 1;
                    this.mShortcutDumpFiles.save(
                            "restore-4.txt",
                            new Consumer(
                                    this) { // from class:
                                            // com.android.server.pm.ShortcutService$$ExternalSyntheticLambda0
                                public final /* synthetic */ ShortcutService f$0;

                                {
                                    this.f$0 = this;
                                }

                                @Override // java.util.function.Consumer
                                public final void accept(Object obj) {
                                    int i32 = i5;
                                    ShortcutService shortcutService = this.f$0;
                                    PrintWriter printWriter = (PrintWriter) obj;
                                    shortcutService.getClass();
                                    switch (i32) {
                                        case 0:
                                            printWriter.print("Start time: ");
                                            printWriter.print(
                                                    TimeMigrationUtils.formatMillisWithFixedFormat(
                                                            shortcutService
                                                                    .injectCurrentTimeMillis()));
                                            printWriter.println();
                                            break;
                                        case 1:
                                            shortcutService.dumpInner(
                                                    printWriter, new ShortcutService.DumpFilter());
                                            break;
                                        default:
                                            printWriter.print("Finish time: ");
                                            printWriter.print(
                                                    TimeMigrationUtils.formatMillisWithFixedFormat(
                                                            shortcutService
                                                                    .injectCurrentTimeMillis()));
                                            printWriter.println();
                                            break;
                                    }
                                }
                            });
                    final int i6 = 2;
                    this.mShortcutDumpFiles.save(
                            "restore-5-finish.txt",
                            new Consumer(
                                    this) { // from class:
                                            // com.android.server.pm.ShortcutService$$ExternalSyntheticLambda0
                                public final /* synthetic */ ShortcutService f$0;

                                {
                                    this.f$0 = this;
                                }

                                @Override // java.util.function.Consumer
                                public final void accept(Object obj) {
                                    int i32 = i6;
                                    ShortcutService shortcutService = this.f$0;
                                    PrintWriter printWriter = (PrintWriter) obj;
                                    shortcutService.getClass();
                                    switch (i32) {
                                        case 0:
                                            printWriter.print("Start time: ");
                                            printWriter.print(
                                                    TimeMigrationUtils.formatMillisWithFixedFormat(
                                                            shortcutService
                                                                    .injectCurrentTimeMillis()));
                                            printWriter.println();
                                            break;
                                        case 1:
                                            shortcutService.dumpInner(
                                                    printWriter, new ShortcutService.DumpFilter());
                                            break;
                                        default:
                                            printWriter.print("Finish time: ");
                                            printWriter.print(
                                                    TimeMigrationUtils.formatMillisWithFixedFormat(
                                                            shortcutService
                                                                    .injectCurrentTimeMillis()));
                                            printWriter.println();
                                            break;
                                    }
                                }
                            });
                    saveUser(i);
                } catch (InvalidFileFormatException | IOException | XmlPullParserException e) {
                    Slog.w("ShortcutService", "Restoration failed.", e);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final int applyRestoreSmartSwitch(ParcelFileDescriptor parcelFileDescriptor, int i) {
        PmLog.logDebugInfoAndLogcat("Start Shortcut Restoration");
        injectEnforceCallingPermission("com.samsung.android.scloud.backup.lib.write", null);
        synchronized (this.mServiceLock) {
            try {
                if (!isUserUnlockedL(i)) {
                    PmLog.logDebugInfoAndLogcat("Shortcut Restoration failed : user locked - " + i);
                    return 1;
                }
                ShortcutUser userShortcutsLocked = getUserShortcutsLocked(i);
                allowSmartSwitchBackup(true);
                try {
                    try {
                        ParcelFileDescriptor.AutoCloseInputStream autoCloseInputStream =
                                new ParcelFileDescriptor.AutoCloseInputStream(parcelFileDescriptor);
                        try {
                            try {
                                ShortcutUser loadUserInternal =
                                        loadUserInternal(i, autoCloseInputStream, true);
                                allowSmartSwitchBackup(true);
                                IoUtils.closeQuietly(autoCloseInputStream);
                                userShortcutsLocked.mergeRestoredFile(loadUserInternal);
                                rescanUpdatedPackagesLocked(i, 0L);
                                saveUser(i);
                                allowSmartSwitchBackup(false);
                                PmLog.logDebugInfoAndLogcat("Success Shortcut Restoration");
                                return 0;
                            } finally {
                                IoUtils.closeQuietly(autoCloseInputStream);
                            }
                        } catch (InvalidFileFormatException
                                | IOException
                                | XmlPullParserException e) {
                            PmLog.logDebugInfoAndLogcat("Shortcut Restoration failed : " + e);
                            allowSmartSwitchBackup(false);
                            return 1;
                        }
                    } catch (Exception e2) {
                        PmLog.logDebugInfoAndLogcat("Shortcut Restoration failed : " + e2);
                        allowSmartSwitchBackup(false);
                        return 1;
                    }
                } catch (Throwable th) {
                    allowSmartSwitchBackup(false);
                    throw th;
                }
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }

    public boolean areShortcutsSupportedOnHomeScreen(int i) {
        boolean z;
        if (!Flags.allowPrivateProfile()
                || !android.multiuser.Flags.disablePrivateSpaceItemsOnHome()
                || !android.multiuser.Flags.enablePrivateSpaceFeatures()) {
            return true;
        }
        long time = this.mStatLogger.getTime();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            synchronized (this.mServiceLock) {
                try {
                    z =
                            this.mUserManagerInternal.getUserProperties(i) != null
                                    ? !r9.areItemsRestrictedOnHomeScreen()
                                    : false;
                } finally {
                }
            }
            return z;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            logDurationStat(16, time);
        }
    }

    public final boolean canSeeAnyPinnedShortcut(int i, int i2, int i3, String str) {
        boolean contains;
        if (injectHasAccessShortcutsPermission(i2, i3)) {
            return true;
        }
        synchronized (this.mNonPersistentUsersLock) {
            ShortcutNonPersistentUser shortcutNonPersistentUser =
                    (ShortcutNonPersistentUser) this.mShortcutNonPersistentUsers.get(i);
            if (shortcutNonPersistentUser == null) {
                shortcutNonPersistentUser = new ShortcutNonPersistentUser(i);
                this.mShortcutNonPersistentUsers.put(i, shortcutNonPersistentUser);
            }
            contains = shortcutNonPersistentUser.mHostPackageSet.contains(str);
        }
        return contains;
    }

    public void checkPackageChanges(int i) {
        boolean z;
        Slog.d("ShortcutService", "checkPackageChanges() ownerUserId=" + i);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            z =
                    IWindowManager.Stub.asInterface(ServiceManager.getService("window"))
                            .isSafeModeEnabled();
            Binder.restoreCallingIdentity(clearCallingIdentity);
        } catch (RemoteException unused) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            z = false;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
        if (z) {
            Slog.i("ShortcutService", "Safe mode, skipping checkPackageChanges()");
            return;
        }
        long time = this.mStatLogger.getTime();
        try {
            final ArrayList arrayList = new ArrayList();
            synchronized (this.mServiceLock) {
                try {
                    ShortcutUser userShortcutsLocked = getUserShortcutsLocked(i);
                    userShortcutsLocked.forAllPackageItems(
                            new Consumer() { // from class:
                                             // com.android.server.pm.ShortcutService$$ExternalSyntheticLambda15
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj) {
                                    ShortcutService shortcutService = ShortcutService.this;
                                    ArrayList arrayList2 = arrayList;
                                    ShortcutPackageItem shortcutPackageItem =
                                            (ShortcutPackageItem) obj;
                                    shortcutService.getClass();
                                    if (shortcutPackageItem.mPackageInfo.mIsShadow) {
                                        return;
                                    }
                                    String str = shortcutPackageItem.mPackageName;
                                    int i2 = shortcutPackageItem.mPackageUserId;
                                    if (shortcutService.isPackageInstalled(i2, str)) {
                                        return;
                                    }
                                    arrayList2.add(UserPackage.of(i2, str));
                                }
                            });
                    if (arrayList.size() > 0) {
                        for (int size = arrayList.size() - 1; size >= 0; size--) {
                            UserPackage userPackage = (UserPackage) arrayList.get(size);
                            cleanUpPackageLocked(
                                    userPackage.packageName, i, userPackage.userId, false);
                        }
                    }
                    rescanUpdatedPackagesLocked(i, userShortcutsLocked.mLastAppScanTime);
                } finally {
                }
            }
            logDurationStat(8, time);
            verifyStates();
        } catch (Throwable th2) {
            logDurationStat(8, time);
            throw th2;
        }
    }

    public final void cleanUpPackageForAllLoadedUsers(
            final int i, final String str, final boolean z) {
        synchronized (this.mServiceLock) {
            forEachLoadedUserLocked(
                    new Consumer() { // from class:
                                     // com.android.server.pm.ShortcutService$$ExternalSyntheticLambda33
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            ShortcutService shortcutService = ShortcutService.this;
                            String str2 = str;
                            int i2 = i;
                            boolean z2 = z;
                            shortcutService.getClass();
                            shortcutService.cleanUpPackageLocked(
                                    str2, ((ShortcutUser) obj).mUserId, i2, z2);
                        }
                    });
        }
    }

    public void cleanUpPackageLocked(final String str, int i, final int i2, boolean z) {
        ShortcutPackage shortcutPackage;
        boolean z2 = this.mUsers.get(i) != null;
        ShortcutUser userShortcutsLocked = getUserShortcutsLocked(i);
        if (i2 == i) {
            shortcutPackage = (ShortcutPackage) userShortcutsLocked.mPackages.remove(str);
            if (shortcutPackage != null && shortcutPackage.isAppSearchEnabled()) {
                ShortcutPackage.runAsSystem(
                        new ShortcutPackage$$ExternalSyntheticLambda26(shortcutPackage));
            }
            userShortcutsLocked.mService.cleanupBitmapsForPackage(userShortcutsLocked.mUserId, str);
        } else {
            shortcutPackage = null;
        }
        boolean z3 = shortcutPackage != null;
        ShortcutLauncher shortcutLauncher =
                (ShortcutLauncher) userShortcutsLocked.mLaunchers.remove(UserPackage.of(i2, str));
        userShortcutsLocked.forAllLaunchers(
                new Consumer() { // from class:
                                 // com.android.server.pm.ShortcutService$$ExternalSyntheticLambda3
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        String str2 = str;
                        int i3 = i2;
                        ShortcutLauncher shortcutLauncher2 = (ShortcutLauncher) obj;
                        synchronized (shortcutLauncher2.mPackageItemLock) {
                            shortcutLauncher2.mPinnedShortcuts.remove(UserPackage.of(i3, str2));
                        }
                    }
                });
        userShortcutsLocked.forAllPackages(new ShortcutService$$ExternalSyntheticLambda4(0));
        if (z3) {
            injectPostToHandler(new ShortcutService$$ExternalSyntheticLambda10(this, str, i));
        }
        if (z && i2 == i) {
            userShortcutsLocked.rescanPackageIfNeeded(str, true);
        }
        if (!z && i2 == i) {
            injectPostToHandler(
                    new ShortcutService$$ExternalSyntheticLambda5(
                            0, shortcutPackage, shortcutLauncher));
        }
        if (z2) {
            return;
        }
        unloadUserLocked(i);
    }

    public final void cleanupBitmapsForPackage(int i, String str) {
        File file = new File(getUserBitmapFilePath(i), str);
        if (file.isDirectory()) {
            if (FileUtils.deleteContents(file) && file.delete()) {
                return;
            }
            Slog.w("ShortcutService", "Unable to remove directory " + file);
        }
    }

    public final void cleanupDanglingBitmapDirectoriesLocked(int i) {
        ArraySet arraySet;
        File[] listFiles;
        long time = this.mStatLogger.getTime();
        ArrayMap arrayMap = new ArrayMap();
        synchronized (this.mServiceLock) {
            getUserShortcutsLocked(i)
                    .forAllPackages(new ShortcutService$$ExternalSyntheticLambda6(1, arrayMap));
        }
        getUserShortcutsLocked(i);
        File[] listFiles2 = getUserBitmapFilePath(i).listFiles();
        if (listFiles2 == null) {
            return;
        }
        for (File file : listFiles2) {
            if (file.isDirectory()) {
                String name = file.getName();
                ShortcutPackage shortcutPackage = (ShortcutPackage) arrayMap.get(name);
                if (shortcutPackage == null) {
                    cleanupBitmapsForPackage(i, name);
                } else {
                    synchronized (shortcutPackage.mPackageItemLock) {
                        shortcutPackage.mShortcutBitmapSaver.waitForAllSavesLocked();
                        arraySet = new ArraySet(1);
                        shortcutPackage.forEachShortcut(
                                new ShortcutPackage$$ExternalSyntheticLambda24(1, arraySet));
                        listFiles = file.listFiles();
                    }
                    shortcutPackage.mShortcutUser.mService.injectPostToHandlerDebounced(
                            shortcutPackage,
                            new ShortcutPackage$$ExternalSyntheticLambda28(
                                    listFiles, arraySet, file));
                }
            }
        }
        logDurationStat(5, time);
    }

    public final void createShortcutResultIntent(
            String str, ShortcutInfo shortcutInfo, int i, AndroidFuture androidFuture) {
        Intent intent;
        Objects.requireNonNull(shortcutInfo);
        Preconditions.checkArgument(shortcutInfo.isEnabled(), "Shortcut must be enabled");
        verifyCaller(i, str);
        verifyShortcutInfoPackage(str, shortcutInfo);
        synchronized (this.mServiceLock) {
            throwIfUserLockedL(i);
            ShortcutRequestPinProcessor shortcutRequestPinProcessor =
                    this.mShortcutRequestPinProcessor;
            ShortcutService shortcutService = shortcutRequestPinProcessor.mService;
            int profileParentId = shortcutService.mUserManagerInternal.getProfileParentId(i);
            String defaultLauncher = shortcutService.getDefaultLauncher(profileParentId);
            intent = null;
            if (defaultLauncher == null) {
                Log.e("ShortcutService", "Default launcher not found.");
            } else {
                shortcutService.throwIfUserLockedL(profileParentId);
                intent =
                        new Intent()
                                .putExtra(
                                        "android.content.pm.extra.PIN_ITEM_REQUEST",
                                        shortcutRequestPinProcessor.requestPinShortcutLocked(
                                                shortcutInfo,
                                                null,
                                                defaultLauncher,
                                                profileParentId));
            }
        }
        verifyStates();
        androidFuture.complete(intent);
    }

    public final void disableShortcuts(
            String str, List list, CharSequence charSequence, int i, int i2) {
        ShortcutPackage packageShortcutsForPublisherLocked;
        ArrayList arrayList;
        ArrayList arrayList2;
        boolean z;
        verifyCaller(i2, str);
        Objects.requireNonNull(list, "shortcutIds must be provided");
        synchronized (this.mServiceLock) {
            try {
                throwIfUserLockedL(i2);
                packageShortcutsForPublisherLocked = getPackageShortcutsForPublisherLocked(i2, str);
                packageShortcutsForPublisherLocked.ensureImmutableShortcutsNotIncludedWithIds(list);
                String charSequence2 = charSequence == null ? null : charSequence.toString();
                boolean z2 = true;
                ArrayList arrayList3 = null;
                arrayList = null;
                int size = list.size() - 1;
                while (size >= 0) {
                    String str2 =
                            (String) Preconditions.checkStringNotEmpty((String) list.get(size));
                    if (packageShortcutsForPublisherLocked.isShortcutExistsAndVisibleToPublisher(
                            str2)) {
                        ArrayList arrayList4 = arrayList3;
                        ShortcutInfo deleteOrDisableWithId =
                                packageShortcutsForPublisherLocked.deleteOrDisableWithId(
                                        str2, true, false, true, 1, false);
                        packageShortcutsForPublisherLocked.mutateShortcut(
                                str2,
                                null,
                                new ShortcutPackage$$ExternalSyntheticLambda5(
                                        packageShortcutsForPublisherLocked, charSequence2, i, 0));
                        if (deleteOrDisableWithId == null) {
                            if (arrayList == null) {
                                arrayList = new ArrayList(1);
                            }
                            arrayList.add(
                                    packageShortcutsForPublisherLocked.findShortcutById(str2));
                            arrayList3 = arrayList4;
                            z = true;
                        } else {
                            if (arrayList4 == null) {
                                z = true;
                                arrayList3 = new ArrayList(1);
                            } else {
                                z = true;
                                arrayList3 = arrayList4;
                            }
                            arrayList3.add(deleteOrDisableWithId);
                        }
                    } else {
                        z = z2;
                    }
                    size--;
                    z2 = z;
                }
                arrayList2 = arrayList3;
                packageShortcutsForPublisherLocked.adjustRanks();
            } catch (Throwable th) {
                throw th;
            }
        }
        packageShortcutsChanged(packageShortcutsForPublisherLocked, arrayList, arrayList2);
        verifyStates();
    }

    public final void dump(
            FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        if (DumpUtils.checkDumpAndUsageStatsPermission(
                this.mContext, "ShortcutService", printWriter)) {
            dumpNoCheck(fileDescriptor, printWriter, strArr);
        }
    }

    public final void dumpInner(PrintWriter printWriter, DumpFilter dumpFilter) {
        synchronized (this.mServiceLock) {
            if (dumpFilter.mDumpDetails) {
                long injectCurrentTimeMillis = injectCurrentTimeMillis();
                printWriter.print("Now: [");
                printWriter.print(injectCurrentTimeMillis);
                printWriter.print("] ");
                printWriter.print(
                        TimeMigrationUtils.formatMillisWithFixedFormat(injectCurrentTimeMillis));
                printWriter.print("  Raw last reset: [");
                printWriter.print(this.mRawLastResetTime.get());
                printWriter.print("] ");
                printWriter.print(
                        TimeMigrationUtils.formatMillisWithFixedFormat(
                                this.mRawLastResetTime.get()));
                updateTimesLocked();
                long j = this.mRawLastResetTime.get();
                printWriter.print("  Last reset: [");
                printWriter.print(j);
                printWriter.print("] ");
                printWriter.print(TimeMigrationUtils.formatMillisWithFixedFormat(j));
                updateTimesLocked();
                long j2 = this.mRawLastResetTime.get() + this.mResetInterval;
                printWriter.print("  Next reset: [");
                printWriter.print(j2);
                printWriter.print("] ");
                printWriter.print(TimeMigrationUtils.formatMillisWithFixedFormat(j2));
                printWriter.println();
                printWriter.println();
                printWriter.print("  Config:");
                printWriter.print("    Max icon dim: ");
                printWriter.println(this.mMaxIconDimension);
                printWriter.print("    Icon format: ");
                printWriter.println(this.mIconPersistFormat);
                printWriter.print("    Icon quality: ");
                printWriter.println(this.mIconPersistQuality);
                printWriter.print("    saveDelayMillis: ");
                printWriter.println(this.mSaveDelayMillis);
                printWriter.print("    resetInterval: ");
                printWriter.println(this.mResetInterval);
                printWriter.print("    maxUpdatesPerInterval: ");
                printWriter.println(this.mMaxUpdatesPerInterval);
                printWriter.print("    maxShortcutsPerActivity: ");
                printWriter.println(this.mMaxShortcuts);
                printWriter.println();
                this.mStatLogger.dump(printWriter, "  ");
                synchronized (this.mWtfLock) {
                    try {
                        printWriter.println();
                        printWriter.print("  #Failures: ");
                        printWriter.println(this.mWtfCount);
                        if (this.mLastWtfStacktrace != null) {
                            printWriter.print("  Last failure stack trace: ");
                            printWriter.println(Log.getStackTraceString(this.mLastWtfStacktrace));
                        }
                    } finally {
                    }
                }
                printWriter.println();
            }
            for (int i = 0; i < this.mUsers.size(); i++) {
                ShortcutUser shortcutUser = (ShortcutUser) this.mUsers.valueAt(i);
                if (dumpFilter.isUserMatch(shortcutUser.mUserId)) {
                    shortcutUser.dump(printWriter, dumpFilter);
                    printWriter.println();
                }
            }
            for (int i2 = 0; i2 < this.mShortcutNonPersistentUsers.size(); i2++) {
                ShortcutNonPersistentUser shortcutNonPersistentUser =
                        (ShortcutNonPersistentUser) this.mShortcutNonPersistentUsers.valueAt(i2);
                if (dumpFilter.isUserMatch(shortcutNonPersistentUser.mUserId)) {
                    shortcutNonPersistentUser.dump(printWriter, dumpFilter);
                    printWriter.println();
                }
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:40:0x00fc, code lost:

       if (r2 >= r9.length) goto L150;
    */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x00fe, code lost:

       r3 = r2 + 1;
       ((java.util.ArrayList) r7.mPackagePatterns).add(java.util.regex.Pattern.compile(java.util.regex.Pattern.quote(r9[r2])));
    */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x00bf, code lost:

       r2 = r3;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void dumpNoCheck(
            java.io.FileDescriptor r7, java.io.PrintWriter r8, java.lang.String[] r9) {
        /*
            Method dump skipped, instructions count: 487
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.pm.ShortcutService.dumpNoCheck(java.io.FileDescriptor,"
                    + " java.io.PrintWriter, java.lang.String[]):void");
    }

    public final void enableShortcuts(String str, List list, int i) {
        ShortcutPackage packageShortcutsForPublisherLocked;
        ArrayList arrayList;
        verifyCaller(i, str);
        Objects.requireNonNull(list, "shortcutIds must be provided");
        synchronized (this.mServiceLock) {
            try {
                throwIfUserLockedL(i);
                packageShortcutsForPublisherLocked = getPackageShortcutsForPublisherLocked(i, str);
                packageShortcutsForPublisherLocked.ensureImmutableShortcutsNotIncludedWithIds(list);
                arrayList = null;
                for (int size = list.size() - 1; size >= 0; size--) {
                    String str2 =
                            (String) Preconditions.checkStringNotEmpty((String) list.get(size));
                    if (packageShortcutsForPublisherLocked.isShortcutExistsAndVisibleToPublisher(
                            str2)) {
                        packageShortcutsForPublisherLocked.mutateShortcut(
                                str2,
                                null,
                                new ShortcutPackage$$ExternalSyntheticLambda10(
                                        0, packageShortcutsForPublisherLocked));
                        if (arrayList == null) {
                            arrayList = new ArrayList(1);
                        }
                        arrayList.add(packageShortcutsForPublisherLocked.findShortcutById(str2));
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        packageShortcutsChanged(packageShortcutsForPublisherLocked, arrayList, null);
        verifyStates();
    }

    public final void fillInDefaultActivity(List list) {
        ComponentName componentName = null;
        for (int size = list.size() - 1; size >= 0; size--) {
            ShortcutInfo shortcutInfo = (ShortcutInfo) list.get(size);
            if (shortcutInfo.getActivity() == null) {
                if (componentName == null) {
                    componentName =
                            injectGetDefaultMainActivity(
                                    shortcutInfo.getUserId(), shortcutInfo.getPackage());
                    Preconditions.checkState(
                            componentName != null,
                            "Launcher activity not found for package " + shortcutInfo.getPackage());
                }
                shortcutInfo.setActivity(componentName);
            }
        }
    }

    public final void fixUpIncomingShortcutInfo(ShortcutInfo shortcutInfo, boolean z, boolean z2) {
        int type;
        if (shortcutInfo.isReturnedByServer()) {
            Log.w(
                    "ShortcutService",
                    "Re-publishing ShortcutInfo returned by server is not supported. Some"
                        + " information such as icon may lost from shortcut.");
        }
        if (shortcutInfo.getActivity() != null) {
            Preconditions.checkState(
                    shortcutInfo.getPackage().equals(shortcutInfo.getActivity().getPackageName()),
                    "Cannot publish shortcut: activity "
                            + shortcutInfo.getActivity()
                            + " does not belong to package "
                            + shortcutInfo.getPackage());
            Preconditions.checkState(
                    injectIsMainActivity(shortcutInfo.getUserId(), shortcutInfo.getActivity()),
                    "Cannot publish shortcut: activity "
                            + shortcutInfo.getActivity()
                            + " is not main activity");
        }
        if (!z) {
            shortcutInfo.enforceMandatoryFields(z2);
            if (!z2) {
                Preconditions.checkState(
                        shortcutInfo.getActivity() != null,
                        "Cannot publish shortcut: target activity is not set");
            }
        }
        if (shortcutInfo.getIcon() != null) {
            ShortcutInfo.validateIcon(shortcutInfo.getIcon());
            int injectBinderCallingUid = injectBinderCallingUid();
            Icon icon = shortcutInfo.getIcon();
            if (icon != null && ((type = icon.getType()) == 4 || type == 6)) {
                Uri uri = icon.getUri();
                ((UriGrantsManagerService.LocalService) this.mUriGrantsManagerInternal)
                        .checkGrantUriPermission(
                                injectBinderCallingUid,
                                shortcutInfo.getPackage(),
                                ContentProvider.getUriWithoutUserId(uri),
                                1,
                                ContentProvider.getUserIdFromUri(
                                        uri, UserHandle.getUserId(injectBinderCallingUid)));
            }
        }
        shortcutInfo.replaceFlags(shortcutInfo.getFlags() & 8192);
    }

    public final void fixUpShortcutResourceNamesAndValues(ShortcutInfo shortcutInfo) {
        Resources injectGetResourcesForApplicationAsUser =
                injectGetResourcesForApplicationAsUser(
                        shortcutInfo.getUserId(), shortcutInfo.getPackage());
        if (injectGetResourcesForApplicationAsUser != null) {
            long time = this.mStatLogger.getTime();
            try {
                shortcutInfo.lookupAndFillInResourceNames(injectGetResourcesForApplicationAsUser);
                logDurationStat(10, time);
                shortcutInfo.resolveResourceStrings(injectGetResourcesForApplicationAsUser);
            } catch (Throwable th) {
                logDurationStat(10, time);
                throw th;
            }
        }
    }

    public final void forEachLoadedUserLocked(Consumer consumer) {
        for (int size = this.mUsers.size() - 1; size >= 0; size--) {
            consumer.accept((ShortcutUser) this.mUsers.valueAt(size));
        }
    }

    public final ApplicationInfo getApplicationInfo(String str, int i) {
        ApplicationInfo injectApplicationInfoWithUninstalled =
                injectApplicationInfoWithUninstalled(str, i);
        if (isInstalled(injectApplicationInfoWithUninstalled)) {
            return injectApplicationInfoWithUninstalled;
        }
        return null;
    }

    public final byte[] getBackupPayload(int i) {
        if (!isCallerSystem()) {
            throw new SecurityException("Caller must be system");
        }
        synchronized (this.mServiceLock) {
            try {
                if (!isUserUnlockedL(i)) {
                    wtf("Can't backup: user " + i + " is locked or not running", null);
                    return null;
                }
                ShortcutUser userShortcutsLocked = getUserShortcutsLocked(i);
                userShortcutsLocked.forAllPackageItems(
                        new ShortcutService$$ExternalSyntheticLambda4(5));
                userShortcutsLocked.forAllPackages(
                        new ShortcutService$$ExternalSyntheticLambda4(6));
                userShortcutsLocked.forAllLaunchers(
                        new ShortcutService$$ExternalSyntheticLambda4(7));
                scheduleSaveInner(i);
                saveDirtyInfo();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(32768);
                try {
                    saveUserInternalLocked(byteArrayOutputStream, true, i);
                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                    ShortcutDumpFiles shortcutDumpFiles = this.mShortcutDumpFiles;
                    shortcutDumpFiles.getClass();
                    shortcutDumpFiles.save(
                            "backup-1-payload.txt",
                            new ShortcutDumpFiles$$ExternalSyntheticLambda0(byteArray));
                    return byteArray;
                } catch (IOException | XmlPullParserException e) {
                    Slog.w("ShortcutService", "Backup failed.", e);
                    return null;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final ParcelFileDescriptor getBackupShortcut(int i) {
        PmLog.logDebugInfoAndLogcat("Start Shortcut Backup");
        injectEnforceCallingPermission("com.samsung.android.scloud.backup.lib.write", null);
        synchronized (this.mServiceLock) {
            try {
                if (!isUserUnlockedL(i)) {
                    PmLog.logDebugInfoAndLogcat("Shortcut Backup failed : user locked - " + i);
                    return null;
                }
                ShortcutUser userShortcutsLocked = getUserShortcutsLocked(i);
                allowSmartSwitchBackup(true);
                try {
                    userShortcutsLocked.forAllPackageItems(
                            new ShortcutService$$ExternalSyntheticLambda4(2));
                    userShortcutsLocked.forAllLaunchers(
                            new ShortcutService$$ExternalSyntheticLambda4(3));
                    scheduleSaveInner(i);
                    saveDirtyInfo();
                    File file = new File("/data/misc/shortcutbnr");
                    File file2 = new File(file, "shortcut.br");
                    try {
                        if (!file.exists()) {
                            file.mkdirs();
                        }
                        if (file2.exists()) {
                            file2.delete();
                        }
                        try {
                            try {
                                FileOutputStream fileOutputStream = new FileOutputStream(file2);
                                try {
                                    saveUserInternalLocked(fileOutputStream, true, i);
                                    FileDescriptor open =
                                            Os.open(
                                                    file2.getAbsolutePath(),
                                                    OsConstants.O_RDONLY,
                                                    402);
                                    fileOutputStream.close();
                                    PmLog.logDebugInfoAndLogcat("Success Shortcut Backup");
                                    ParcelFileDescriptor parcelFileDescriptor =
                                            new ParcelFileDescriptor(open);
                                    allowSmartSwitchBackup(false);
                                    return parcelFileDescriptor;
                                } catch (Throwable th) {
                                    try {
                                        fileOutputStream.close();
                                    } catch (Throwable th2) {
                                        th.addSuppressed(th2);
                                    }
                                    throw th;
                                }
                            } catch (IOException | XmlPullParserException e) {
                                PmLog.logDebugInfoAndLogcat("Shortcut Backup failed : " + e);
                                allowSmartSwitchBackup(false);
                                return null;
                            }
                        } catch (Exception e2) {
                            PmLog.logDebugInfoAndLogcat("Shortcut Backup failed : " + e2);
                            allowSmartSwitchBackup(false);
                            return null;
                        }
                    } catch (Exception e3) {
                        PmLog.logDebugInfoAndLogcat("Shortcut Backup failed : " + e3);
                        allowSmartSwitchBackup(false);
                        return null;
                    }
                } catch (Throwable th3) {
                    allowSmartSwitchBackup(false);
                    throw th3;
                }
            } catch (Throwable th4) {
                throw th4;
            }
        }
    }

    public final ResilientAtomicFile getBaseStateFile() {
        return new ResilientAtomicFile(
                new File(injectSystemDataPath(), FILENAME_BASE_STATE),
                new File(injectSystemDataPath(), "shortcut_service.xml.backup"),
                new File(injectSystemDataPath(), "shortcut_service.xml.reservecopy"),
                505,
                "base shortcut",
                null);
    }

    public final String[] getBitmapPathList(int i) {
        injectEnforceCallingPermission("com.samsung.android.scloud.backup.lib.write", null);
        ArrayList arrayList = new ArrayList();
        getUserShortcutsLocked(i)
                .forAllPackages(new ShortcutService$$ExternalSyntheticLambda6(0, arrayList));
        return (String[]) arrayList.toArray(new String[arrayList.size()]);
    }

    public final String getDefaultLauncher(int i) {
        long time = this.mStatLogger.getTime();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            synchronized (this.mServiceLock) {
                throwIfUserLockedL(i);
                ShortcutUser userShortcutsLocked = getUserShortcutsLocked(i);
                String str = userShortcutsLocked.mCachedLauncher;
                if (str != null) {
                    return str;
                }
                long time2 = this.mStatLogger.getTime();
                String injectGetHomeRoleHolderAsUser =
                        injectGetHomeRoleHolderAsUser(
                                this.mUserManagerInternal.getProfileParentId(i));
                logDurationStat(0, time2);
                if (injectGetHomeRoleHolderAsUser != null) {
                    userShortcutsLocked.mCachedLauncher = injectGetHomeRoleHolderAsUser;
                } else {
                    Slog.e("ShortcutService", "Default launcher not found. user: " + i);
                }
                return injectGetHomeRoleHolderAsUser;
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            logDurationStat(16, time);
        }
    }

    public final int getIconMaxDimensions(String str, int i) {
        int i2;
        verifyCaller(i, str);
        synchronized (this.mServiceLock) {
            i2 = this.mMaxIconDimension;
        }
        return i2;
    }

    public Bitmap.CompressFormat getIconPersistFormatForTest() {
        return this.mIconPersistFormat;
    }

    public int getIconPersistQualityForTest() {
        return this.mIconPersistQuality;
    }

    public final List getInstalledPackages(int i) {
        long time = this.mStatLogger.getTime();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                List injectGetPackagesWithUninstalled = injectGetPackagesWithUninstalled(i);
                injectGetPackagesWithUninstalled.removeIf(PACKAGE_NOT_INSTALLED);
                return injectGetPackagesWithUninstalled;
            } catch (RemoteException e) {
                Slog.wtf("ShortcutService", "RemoteException", e);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                logDurationStat(7, time);
                return null;
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            logDurationStat(7, time);
        }
    }

    public ShortcutLauncher getLauncherShortcutForTest(String str, int i) {
        synchronized (this.mServiceLock) {
            try {
                ShortcutUser shortcutUser = (ShortcutUser) this.mUsers.get(i);
                if (shortcutUser == null) {
                    return null;
                }
                return (ShortcutLauncher)
                        shortcutUser.getAllLaunchersForTest().get(UserPackage.of(i, str));
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final ShortcutLauncher getLauncherShortcutsLocked(int i, int i2, String str) {
        ShortcutUser userShortcutsLocked = getUserShortcutsLocked(i);
        UserPackage of = UserPackage.of(i2, str);
        ShortcutLauncher shortcutLauncher =
                (ShortcutLauncher) userShortcutsLocked.mLaunchers.get(of);
        if (shortcutLauncher == null) {
            shortcutLauncher =
                    new ShortcutLauncher(userShortcutsLocked, userShortcutsLocked.mUserId, str, i2);
            userShortcutsLocked.mLaunchers.put(of, shortcutLauncher);
        }
        shortcutLauncher.attemptToRestoreIfNeededAndSave();
        return shortcutLauncher;
    }

    public int getMaxIconDimensionForTest() {
        return this.mMaxIconDimension;
    }

    public final int getMaxShortcutCountPerActivity(String str, int i) {
        verifyCaller(i, str);
        return this.mMaxShortcuts;
    }

    public int getMaxShortcutsForTest() {
        return this.mMaxShortcuts;
    }

    public int getMaxUpdatesPerIntervalForTest() {
        return this.mMaxUpdatesPerInterval;
    }

    public final PackageInfo getPackageInfo(String str, int i, boolean z) {
        PackageInfo injectPackageInfoWithUninstalled = injectPackageInfoWithUninstalled(str, i, z);
        if (injectPackageInfoWithUninstalled == null
                || !isInstalled(injectPackageInfoWithUninstalled.applicationInfo)) {
            return null;
        }
        return injectPackageInfoWithUninstalled;
    }

    public ShortcutInfo getPackageShortcutForTest(String str, String str2, int i) {
        synchronized (this.mServiceLock) {
            try {
                ShortcutPackage packageShortcutForTest = getPackageShortcutForTest(str, i);
                if (packageShortcutForTest == null) {
                    return null;
                }
                return packageShortcutForTest.findShortcutById(str2);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public ShortcutPackage getPackageShortcutForTest(String str, int i) {
        synchronized (this.mServiceLock) {
            try {
                ShortcutUser shortcutUser = (ShortcutUser) this.mUsers.get(i);
                if (shortcutUser == null) {
                    return null;
                }
                return (ShortcutPackage) shortcutUser.getAllPackagesForTest().get(str);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final ShortcutPackage getPackageShortcutsForPublisherLocked(int i, String str) {
        ShortcutPackage packageShortcuts = getUserShortcutsLocked(i).getPackageShortcuts(str);
        ShortcutUser shortcutUser = packageShortcuts.mShortcutUser;
        shortcutUser.detectLocaleChange(false);
        shortcutUser.rescanPackageIfNeeded(str, false);
        return packageShortcuts;
    }

    public final long getRateLimitResetTime(String str, int i) {
        long j;
        verifyCaller(i, str);
        synchronized (this.mServiceLock) {
            throwIfUserLockedL(i);
            updateTimesLocked();
            j = this.mRawLastResetTime.get() + this.mResetInterval;
        }
        return j;
    }

    public final int getRemainingCallCount(String str, int i) {
        int apiCallCount;
        verifyCaller(i, str);
        boolean injectHasUnlimitedShortcutsApiCallsPermission =
                injectHasUnlimitedShortcutsApiCallsPermission(
                        injectBinderCallingPid(), injectBinderCallingUid());
        synchronized (this.mServiceLock) {
            throwIfUserLockedL(i);
            apiCallCount =
                    this.mMaxUpdatesPerInterval
                            - getPackageShortcutsForPublisherLocked(i, str)
                                    .getApiCallCount(injectHasUnlimitedShortcutsApiCallsPermission);
        }
        return apiCallCount;
    }

    public long getResetIntervalForTest() {
        return this.mResetInterval;
    }

    public final ParceledListSlice getShareTargets(String str, IntentFilter intentFilter, int i) {
        ParceledListSlice parceledListSlice;
        Preconditions.checkStringNotEmpty(str, "packageName");
        Objects.requireNonNull(intentFilter, "intentFilter");
        int injectBinderCallingUid = injectBinderCallingUid();
        ComponentName injectChooserActivity = injectChooserActivity();
        if (injectChooserActivity == null
                || UserHandle.getAppId(
                                injectGetPackageUid(0, injectChooserActivity.getPackageName()))
                        != UserHandle.getAppId(injectBinderCallingUid)) {
            verifyCaller(i, str);
        }
        if (!isCallerSystem()) {
            injectEnforceCallingPermission(
                    "android.permission.MANAGE_APP_PREDICTIONS", "getShareTargets");
        }
        ComponentName injectChooserActivity2 = injectChooserActivity();
        String packageName =
                injectChooserActivity2 != null
                        ? injectChooserActivity2.getPackageName()
                        : this.mContext.getPackageName();
        synchronized (this.mServiceLock) {
            throwIfUserLockedL(i);
            ArrayList arrayList = new ArrayList();
            getUserShortcutsLocked(i)
                    .forAllPackages(
                            new ShortcutService$$ExternalSyntheticLambda21(
                                    arrayList, intentFilter, packageName));
            parceledListSlice = new ParceledListSlice(arrayList);
        }
        return parceledListSlice;
    }

    public final IParcelFileDescriptorFactory getShortcutBitmapsFileDescriptor() {
        injectEnforceCallingPermission("com.samsung.android.scloud.backup.lib.write", null);
        return new IParcelFileDescriptorFactory
                .Stub() { // from class: com.android.server.pm.ShortcutService.8
            public final ParcelFileDescriptor open(String str, int i) {
                String[] split = str.split(":");
                if (split.length < 2) {
                    throw new IllegalArgumentException("Invalid filename: ".concat(str));
                }
                if (!FileUtils.isValidExtFilename(split[0])
                        || !FileUtils.isValidExtFilename(split[1])) {
                    Log.d("ShortcutService", "Invalid filename: " + split[0] + ", " + split[1]);
                    throw new IllegalArgumentException("Invalid filename: ".concat(str));
                }
                StringBuilder sb = new StringBuilder("filename: ");
                sb.append(split[0]);
                sb.append("/");
                VpnManagerService$$ExternalSyntheticOutline0.m(sb, split[1], "ShortcutService");
                try {
                    File file =
                            new File(
                                    new File(
                                            ShortcutService.this.getUserBitmapFilePath(0),
                                            split[0]),
                                    split[1]);
                    FileDescriptor open =
                            Os.open(file.getAbsolutePath(), OsConstants.O_RDONLY, 402);
                    Log.d("ShortcutService", "openfile: " + file.getAbsolutePath());
                    return new ParcelFileDescriptor(open);
                } catch (ErrnoException e) {
                    Log.d("ShortcutService", "Failed to open: " + e.getMessage());
                    throw new RemoteException("Failed to open: " + e.getMessage());
                }
            }
        };
    }

    public ShortcutRequestPinProcessor getShortcutRequestPinProcessorForTest() {
        return this.mShortcutRequestPinProcessor;
    }

    public final ParceledListSlice getShortcuts(String str, int i, int i2) {
        ParceledListSlice parceledListSlice;
        verifyCaller(i2, str);
        synchronized (this.mServiceLock) {
            throwIfUserLockedL(i2);
            final int i3 =
                    ((i & 2) != 0 ? 1 : 0)
                            | ((i & 4) != 0 ? 2 : 0)
                            | ((i & 1) != 0 ? 32 : 0)
                            | ((i & 8) != 0 ? 1610629120 : 0);
            Predicate predicate =
                    new Predicate() { // from class:
                                      // com.android.server.pm.ShortcutService$$ExternalSyntheticLambda8
                        @Override // java.util.function.Predicate
                        public final boolean test(Object obj) {
                            ShortcutInfo shortcutInfo = (ShortcutInfo) obj;
                            return shortcutInfo.isVisibleToPublisher()
                                    && (i3 & shortcutInfo.getFlags()) != 0;
                        }
                    };
            ArrayList arrayList = new ArrayList();
            getPackageShortcutsForPublisherLocked(i2, str)
                    .findAll(arrayList, predicate, 9, null, 0, false);
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                ((ShortcutInfo) arrayList.get(size)).setReturnedByServer();
            }
            parceledListSlice = new ParceledListSlice(arrayList);
        }
        return parceledListSlice;
    }

    public SparseArray getShortcutsForTest() {
        return this.mUsers;
    }

    public final File getUserBitmapFilePath(int i) {
        return new File(injectUserDataPath(i), "bitmaps");
    }

    public final ResilientAtomicFile getUserFile(int i) {
        return new ResilientAtomicFile(
                new File(injectUserDataPath(i), FILENAME_USER_PACKAGES),
                new File(injectUserDataPath(i), "shortcuts.xml.backup"),
                new File(injectUserDataPath(i), FILENAME_USER_PACKAGES_RESERVE_COPY),
                505,
                "user shortcut",
                null);
    }

    public final ShortcutUser getUserShortcutsLocked(int i) {
        if (isUserUnlockedL(i)) {
            this.mLastLockedUser = -1;
        } else if (i != this.mLastLockedUser) {
            wtf("User still locked", null);
            this.mLastLockedUser = i;
        }
        ShortcutUser shortcutUser = (ShortcutUser) this.mUsers.get(i);
        if (shortcutUser == null) {
            shortcutUser = loadUserLocked(i);
            if (shortcutUser == null) {
                shortcutUser = new ShortcutUser(this, i);
            }
            this.mUsers.put(i, shortcutUser);
            checkPackageChanges(i);
        }
        return shortcutUser;
    }

    public final void handleLocaleChanged(final boolean z) {
        scheduleSaveInner(-10000);
        synchronized (this.mServiceLock) {
            try {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    forEachLoadedUserLocked(
                            new Consumer() { // from class:
                                             // com.android.server.pm.ShortcutService$$ExternalSyntheticLambda11
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj) {
                                    ((ShortcutUser) obj).detectLocaleChange(z);
                                }
                            });
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void handleOnUidStateChanged(int i, int i2) {
        Trace.traceBegin(524288L, "shortcutHandleOnUidStateChanged");
        synchronized (this.mServiceLock) {
            try {
                this.mUidState.put(i, i2);
                if (i2 <= 5) {
                    this.mUidLastForegroundElapsedTime.put(i, injectElapsedRealtime());
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        Trace.traceEnd(524288L);
    }

    public final void handleStopUser(int i) {
        AnyMotionDetector$$ExternalSyntheticOutline0.m(
                i, "handleStopUser: user=", "ShortcutService");
        Trace.traceBegin(524288L, "shortcutHandleStopUser");
        synchronized (this.mServiceLock) {
            unloadUserLocked(i);
            synchronized (this.mUnlockedUsers) {
                this.mUnlockedUsers.put(i, false);
            }
        }
        Trace.traceEnd(524288L);
    }

    public final boolean hasShareTargets(String str, String str2, int i) {
        boolean z;
        verifyCaller(i, str);
        if (!isCallerSystem()) {
            injectEnforceCallingPermission(
                    "android.permission.MANAGE_APP_PREDICTIONS", "hasShareTargets");
        }
        synchronized (this.mServiceLock) {
            throwIfUserLockedL(i);
            ShortcutPackage packageShortcuts = getUserShortcutsLocked(i).getPackageShortcuts(str2);
            synchronized (packageShortcuts.mPackageItemLock) {
                z = !packageShortcuts.mShareTargets.isEmpty();
            }
        }
        return z;
    }

    public boolean hasShortcutHostPermissionInner(String str, int i) {
        synchronized (this.mServiceLock) {
            try {
                throwIfUserLockedL(i);
                String defaultLauncher = getDefaultLauncher(i);
                if (defaultLauncher == null) {
                    return false;
                }
                return defaultLauncher.equals(str);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public ApplicationInfo injectApplicationInfoWithUninstalled(String str, int i) {
        long time = this.mStatLogger.getTime();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                return this.mIPackageManager.getApplicationInfo(str, 537666048L, i);
            } catch (RemoteException e) {
                Slog.wtf("ShortcutService", "RemoteException", e);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                logDurationStat(3, time);
                return null;
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            logDurationStat(3, time);
        }
    }

    public int injectBinderCallingPid() {
        return IShortcutService.Stub.getCallingPid();
    }

    public int injectBinderCallingUid() {
        return IShortcutService.Stub.getCallingUid();
    }

    public ComponentName injectChooserActivity() {
        if (this.mChooserActivity == null) {
            this.mChooserActivity =
                    ComponentName.unflattenFromString(
                            this.mContext
                                    .getResources()
                                    .getString(R.string.crossSimFormat_spn_cross_sim_calling));
        }
        return this.mChooserActivity;
    }

    public long injectCurrentTimeMillis() {
        return System.currentTimeMillis();
    }

    public int injectDipToPixel(int i) {
        return (int)
                TypedValue.applyDimension(1, i, this.mContext.getResources().getDisplayMetrics());
    }

    public long injectElapsedRealtime() {
        return SystemClock.elapsedRealtime();
    }

    public void injectEnforceCallingPermission(String str, String str2) {
        this.mContext.enforceCallingPermission(str, str2);
    }

    public ActivityInfo injectGetActivityInfoWithMetadataWithUninstalled(
            ComponentName componentName, int i) {
        long time = this.mStatLogger.getTime();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                return this.mIPackageManager.getActivityInfo(componentName, 537666176L, i);
            } catch (RemoteException e) {
                Slog.wtf("ShortcutService", "RemoteException", e);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                logDurationStat(6, time);
                return null;
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            logDurationStat(6, time);
        }
    }

    public final ComponentName injectGetDefaultMainActivity(int i, String str) {
        long time = this.mStatLogger.getTime();
        try {
            Intent intent = new Intent("android.intent.action.MAIN");
            intent.addCategory("android.intent.category.LAUNCHER");
            ComponentName componentName = null;
            List queryActivities = queryActivities(intent, str, null, i);
            if (queryActivities.size() != 0) {
                componentName =
                        ((ResolveInfo) queryActivities.get(0)).activityInfo.getComponentName();
            }
            return componentName;
        } finally {
            logDurationStat(11, time);
        }
    }

    public String injectGetHomeRoleHolderAsUser(int i) {
        List roleHoldersAsUser =
                this.mRoleManager.getRoleHoldersAsUser("android.app.role.HOME", UserHandle.of(i));
        if (roleHoldersAsUser.isEmpty()) {
            return null;
        }
        return (String) roleHoldersAsUser.get(0);
    }

    public final int injectGetPackageUid(int i, String str) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                return this.mIPackageManager.getPackageUid(str, 537666048L, i);
            } catch (RemoteException e) {
                Slog.wtf("ShortcutService", "RemoteException", e);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return -1;
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public List injectGetPackagesWithUninstalled(int i) throws RemoteException {
        ParceledListSlice installedPackages =
                this.mIPackageManager.getInstalledPackages(537666048L, i);
        return installedPackages == null ? Collections.emptyList() : installedPackages.getList();
    }

    public final ComponentName injectGetPinConfirmationActivity(int i, int i2, String str) {
        Iterator it =
                queryActivities(
                                i,
                                new Intent(
                                                i2 == 1
                                                        ? "android.content.pm.action.CONFIRM_PIN_SHORTCUT"
                                                        : "android.content.pm.action.CONFIRM_PIN_APPWIDGET")
                                        .setPackage(str),
                                false)
                        .iterator();
        if (it.hasNext()) {
            return ((ResolveInfo) it.next()).activityInfo.getComponentName();
        }
        return null;
    }

    public final Resources injectGetResourcesForApplicationAsUser(int i, String str) {
        long time = this.mStatLogger.getTime();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                return this.mContext
                        .createContextAsUser(UserHandle.of(i), 0)
                        .getPackageManager()
                        .getResourcesForApplication(str);
            } catch (PackageManager.NameNotFoundException unused) {
                Slog.e(
                        "ShortcutService",
                        "Resources of package " + str + " for user " + i + " not found");
                Binder.restoreCallingIdentity(clearCallingIdentity);
                logDurationStat(9, time);
                return null;
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            logDurationStat(9, time);
        }
    }

    public boolean injectHasAccessShortcutsPermission(int i, int i2) {
        return this.mContext.checkPermission("android.permission.ACCESS_SHORTCUTS", i, i2) == 0;
    }

    public boolean injectHasUnlimitedShortcutsApiCallsPermission(int i, int i2) {
        return this.mContext.checkPermission(
                        "android.permission.UNLIMITED_SHORTCUTS_API_CALLS", i, i2)
                == 0;
    }

    public boolean injectIsActivityEnabledAndExported(ComponentName componentName, int i) {
        long time = this.mStatLogger.getTime();
        try {
            return queryActivities(new Intent(), componentName.getPackageName(), componentName, i)
                            .size()
                    > 0;
        } finally {
            logDurationStat(13, time);
        }
    }

    public boolean injectIsLowRamDevice() {
        return ActivityManager.isLowRamDeviceStatic();
    }

    public final boolean injectIsMainActivity(int i, ComponentName componentName) {
        if (sIsEmergencyMode.get()) {
            return true;
        }
        long time = this.mStatLogger.getTime();
        try {
            if (componentName == null) {
                wtf("null activity detected", null);
                return false;
            }
            if ("android.__dummy__".equals(componentName.getClassName())) {
                return true;
            }
            Intent intent = new Intent("android.intent.action.MAIN");
            intent.addCategory("android.intent.category.LAUNCHER");
            return queryActivities(intent, componentName.getPackageName(), componentName, i).size()
                    > 0;
        } finally {
            logDurationStat(12, time);
        }
    }

    public PackageInfo injectPackageInfoWithUninstalled(String str, int i, boolean z) {
        long time = this.mStatLogger.getTime();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                PackageInfo packageInfo =
                        this.mIPackageManager.getPackageInfo(
                                str, (z ? 134217728 : 0) | 537666048, i);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                logDurationStat(z ? 2 : 1, time);
                return packageInfo;
            } catch (RemoteException e) {
                Slog.wtf("ShortcutService", "RemoteException", e);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                logDurationStat(z ? 2 : 1, time);
                return null;
            }
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            logDurationStat(z ? 2 : 1, time);
            throw th;
        }
    }

    public final void injectPostToHandler(Runnable runnable) {
        this.mHandler.post(runnable);
    }

    public final void injectPostToHandlerDebounced(Object obj, Runnable runnable) {
        Objects.requireNonNull(obj);
        Objects.requireNonNull(runnable);
        synchronized (this.mServiceLock) {
            this.mHandler.removeCallbacksAndMessages(obj);
            this.mHandler.postDelayed(runnable, obj, 100L);
        }
    }

    public void injectRegisterRoleHoldersListener(
            OnRoleHoldersChangedListener onRoleHoldersChangedListener) {
        this.mRoleManager.addOnRoleHoldersChangedListenerAsUser(
                this.mContext.getMainExecutor(), onRoleHoldersChangedListener, UserHandle.ALL);
    }

    public void injectRegisterUidObserver(IUidObserver iUidObserver, int i) {
        try {
            ActivityManager.getService().registerUidObserver(iUidObserver, i, -1, (String) null);
        } catch (RemoteException unused) {
        }
    }

    public String injectShortcutManagerConstants() {
        return Settings.Global.getString(
                this.mContext.getContentResolver(), "shortcut_manager_constants");
    }

    public boolean injectShouldPerformVerification() {
        return false;
    }

    public File injectSystemDataPath() {
        return Environment.getDataSystemDirectory();
    }

    public long injectUptimeMillis() {
        return SystemClock.uptimeMillis();
    }

    public File injectUserDataPath(int i) {
        return new File(Environment.getDataSystemCeDirectory(i), DIRECTORY_PER_USER);
    }

    public final boolean isCallerSystem() {
        return UserHandle.isSameApp(injectBinderCallingUid(), 1000);
    }

    public final boolean isPackageInstalled(int i, String str) {
        return getApplicationInfo(str, i) != null;
    }

    public final boolean isRequestPinItemSupported(int i, int i2) {
        if (!isCallerSystem() && UserHandle.getUserId(injectBinderCallingUid()) != i) {
            throw new SecurityException("Invalid user-ID");
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return this.mShortcutRequestPinProcessor.getRequestPinConfirmationActivity(i, i2)
                    != null;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean isUidForegroundLocked(int i) {
        return i == 1000
                || this.mUidState.get(i, 20) <= 5
                || this.mActivityManagerInternal.getUidProcessState(i) <= 5;
    }

    public final boolean isUserUnlockedL(int i) {
        synchronized (this.mUnlockedUsers) {
            try {
                if (this.mUnlockedUsers.get(i)) {
                    return true;
                }
                return this.mUserManagerInternal.isUserUnlockingOrUnlocked(i);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void loadBaseStateLocked() {
        FileInputStream openRead;
        this.mRawLastResetTime.set(0L);
        ResilientAtomicFile baseStateFile = getBaseStateFile();
        try {
            Slog.d("ShortcutService", "Loading from " + baseStateFile.mFile);
            try {
                try {
                    openRead = baseStateFile.openRead();
                } catch (FileNotFoundException unused) {
                }
                if (openRead == null) {
                    throw new FileNotFoundException(baseStateFile.mFile.getAbsolutePath());
                }
                TypedXmlPullParser resolvePullParser = Xml.resolvePullParser(openRead);
                while (true) {
                    int next = resolvePullParser.next();
                    if (next == 1) {
                        break;
                    }
                    if (next == 2) {
                        int depth = resolvePullParser.getDepth();
                        String name = resolvePullParser.getName();
                        if (depth != 1) {
                            if (name.hashCode() == -68726522 && name.equals("last_reset_time")) {
                                this.mRawLastResetTime.set(
                                        parseLongAttribute(resolvePullParser, "value", 0L));
                            }
                            Slog.e("ShortcutService", "Invalid tag: " + name);
                        } else if (!"root".equals(name)) {
                            Slog.e("ShortcutService", "Invalid root tag: " + name);
                            baseStateFile.close();
                            return;
                        }
                    }
                }
                baseStateFile.close();
                updateTimesLocked();
                this.mRawLastResetTime.get();
            } catch (IOException | XmlPullParserException e) {
                baseStateFile.failRead(null, e);
                loadBaseStateLocked();
                baseStateFile.close();
            }
        } catch (Throwable th) {
            try {
                baseStateFile.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:47:0x00cc  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x00e4 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.android.server.pm.ShortcutUser loadUserInternal(
            final int r12, java.io.InputStream r13, final boolean r14) {
        /*
            Method dump skipped, instructions count: 301
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled: com.android.server.pm.ShortcutService.loadUserInternal(int,"
                    + " java.io.InputStream, boolean):com.android.server.pm.ShortcutUser");
    }

    public final ShortcutUser loadUserLocked(int i) {
        FileInputStream openRead;
        ResilientAtomicFile userFile = getUserFile(i);
        FileInputStream fileInputStream = null;
        try {
            try {
                Slog.d("ShortcutService", "Loading from " + userFile);
                openRead = userFile.openRead();
            } catch (Exception e) {
                e = e;
            }
            try {
                if (openRead != null) {
                    ShortcutUser loadUserInternal = loadUserInternal(i, openRead, false);
                    userFile.close();
                    return loadUserInternal;
                }
                Slog.d("ShortcutService", "Not found " + userFile);
                userFile.close();
                return null;
            } catch (Exception e2) {
                e = e2;
                fileInputStream = openRead;
                userFile.failRead(fileInputStream, e);
                ShortcutUser loadUserLocked = loadUserLocked(i);
                userFile.close();
                return loadUserLocked;
            }
        } catch (Throwable th) {
            if (userFile != null) {
                try {
                    userFile.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public final void logDurationStat(int i, long j) {
        this.mStatLogger.logDurationStat(i, j);
    }

    public final void onApplicationActive(String str, int i) {
        if (!isCallerSystem() && !isCallerSystem()) {
            injectEnforceCallingPermission(
                    "android.permission.RESET_SHORTCUT_MANAGER_THROTTLING", null);
        }
        synchronized (this.mServiceLock) {
            try {
                if (isUserUnlockedL(i)) {
                    ShortcutPackage packageShortcuts =
                            getUserShortcutsLocked(i).getPackageShortcuts(str);
                    packageShortcuts.mApiCallCount = 0;
                    packageShortcuts.mLastResetTime = 0L;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void onShellCommand(
            FileDescriptor fileDescriptor,
            FileDescriptor fileDescriptor2,
            FileDescriptor fileDescriptor3,
            String[] strArr,
            ShellCallback shellCallback,
            ResultReceiver resultReceiver) {
        int injectBinderCallingUid = injectBinderCallingUid();
        if (injectBinderCallingUid != 2000 && injectBinderCallingUid != 0) {
            throw new SecurityException("Caller must be shell");
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            resultReceiver.send(
                    new MyShellCommand()
                            .exec(
                                    this,
                                    fileDescriptor,
                                    fileDescriptor2,
                                    fileDescriptor3,
                                    strArr,
                                    shellCallback,
                                    resultReceiver),
                    null);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final FileOutputStreamWithPath openIconFileForWrite(int i, ShortcutInfo shortcutInfo) {
        File file = new File(getUserBitmapFilePath(i), shortcutInfo.getPackage());
        if (!file.isDirectory()) {
            file.mkdirs();
            if (!file.isDirectory()) {
                throw new IOException(
                        AccountManagerService$$ExternalSyntheticOutline0.m(
                                file, "Unable to create directory "));
            }
            SELinux.restorecon(file);
        }
        String valueOf = String.valueOf(injectCurrentTimeMillis());
        int i2 = 0;
        while (true) {
            File file2 =
                    new File(
                            file,
                            AudioOffloadInfo$$ExternalSyntheticOutline0.m(
                                    new StringBuilder(),
                                    i2 == 0
                                            ? valueOf
                                            : VpnManagerService$$ExternalSyntheticOutline0.m(
                                                    i2, valueOf, "_"),
                                    ".png"));
            if (!file2.exists()) {
                return new FileOutputStreamWithPath(file2);
            }
            i2++;
        }
    }

    public final FileOutputStream openIconFileForWriteSmartSwitch(String str, String str2) {
        File file = new File(getUserBitmapFilePath(0), str);
        if (!file.isDirectory()) {
            file.mkdirs();
            if (!file.isDirectory()) {
                Slog.d("ShortcutService", "Unable to create directory " + file);
                throw new IOException(
                        AccountManagerService$$ExternalSyntheticOutline0.m(
                                file, "Unable to create directory "));
            }
            SELinux.restorecon(file);
        }
        File file2 = new File(file, str2);
        if (!file2.exists()) {
            return new FileOutputStream(file2);
        }
        Slog.d("ShortcutService", "Unable to create file - already exists " + file2);
        throw new IOException(
                AccountManagerService$$ExternalSyntheticOutline0.m(
                        file2, "Unable to create file - already exists "));
    }

    public final void packageShortcutsChanged(
            ShortcutPackage shortcutPackage, List list, List list2) {
        Objects.requireNonNull(shortcutPackage);
        final int i = shortcutPackage.mPackageUserId;
        final String str = shortcutPackage.mPackageName;
        injectPostToHandlerDebounced(
                shortcutPackage, new ShortcutService$$ExternalSyntheticLambda10(this, str, i));
        final List removeNonKeyFields = removeNonKeyFields(list);
        final List removeNonKeyFields2 = removeNonKeyFields(list2);
        final UserHandle of = UserHandle.of(i);
        injectPostToHandler(
                new Runnable() { // from class:
                                 // com.android.server.pm.ShortcutService$$ExternalSyntheticLambda20
                    @Override // java.lang.Runnable
                    public final void run() {
                        ShortcutService shortcutService = ShortcutService.this;
                        int i2 = i;
                        List list3 = removeNonKeyFields;
                        String str2 = str;
                        UserHandle userHandle = of;
                        List list4 = removeNonKeyFields2;
                        shortcutService.getClass();
                        try {
                            synchronized (shortcutService.mServiceLock) {
                                try {
                                    if (shortcutService.isUserUnlockedL(i2)) {
                                        ArrayList arrayList =
                                                new ArrayList(
                                                        shortcutService.mShortcutChangeCallbacks);
                                        for (int size = arrayList.size() - 1; size >= 0; size--) {
                                            if (!CollectionUtils.isEmpty(list3)) {
                                                ((LauncherApps.ShortcutChangeCallback)
                                                                arrayList.get(size))
                                                        .onShortcutsAddedOrUpdated(
                                                                str2, list3, userHandle);
                                            }
                                            if (!CollectionUtils.isEmpty(list4)) {
                                                ((LauncherApps.ShortcutChangeCallback)
                                                                arrayList.get(size))
                                                        .onShortcutsRemoved(
                                                                str2, list4, userHandle);
                                            }
                                        }
                                    }
                                } finally {
                                }
                            }
                        } catch (Exception unused) {
                        }
                    }
                });
        shortcutPackage.scheduleSave();
    }

    public final void pushDynamicShortcut(String str, ShortcutInfo shortcutInfo, int i) {
        List list;
        verifyCaller(i, str);
        verifyShortcutInfoPackage(str, shortcutInfo);
        ArrayList arrayList = new ArrayList();
        synchronized (this.mServiceLock) {
            try {
                throwIfUserLockedL(i);
                ShortcutPackage packageShortcutsForPublisherLocked =
                        getPackageShortcutsForPublisherLocked(i, str);
                ShortcutPackage.ensureNotImmutable(
                        packageShortcutsForPublisherLocked.findShortcutById(shortcutInfo.getId()));
                fillInDefaultActivity(Arrays.asList(shortcutInfo));
                if (!shortcutInfo.hasRank()) {
                    shortcutInfo.setRank(0);
                }
                packageShortcutsForPublisherLocked.forEachShortcutMutate(
                        new ShortcutPackage$$ExternalSyntheticLambda14(1));
                shortcutInfo.setImplicitRank(0);
                fixUpIncomingShortcutInfo(shortcutInfo, false, false);
                shortcutInfo.setRankChanged();
                if (!packageShortcutsForPublisherLocked.pushDynamicShortcut(
                        shortcutInfo, arrayList)) {
                    list = null;
                } else {
                    if (arrayList.isEmpty()) {
                        return;
                    }
                    list = Collections.singletonList((ShortcutInfo) arrayList.get(0));
                    arrayList.clear();
                }
                arrayList.add(shortcutInfo);
                packageShortcutsForPublisherLocked.adjustRanks();
                packageShortcutsChanged(packageShortcutsForPublisherLocked, arrayList, list);
                packageShortcutsForPublisherLocked.reportShortcutUsed(
                        this.mUsageStatsManagerInternal, shortcutInfo.getId());
                verifyStates();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final List queryActivities(final int i, Intent intent, boolean z) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            List queryIntentActivitiesAsUser =
                    this.mContext
                            .getPackageManager()
                            .queryIntentActivitiesAsUser(intent, 537666048, i);
            if (queryIntentActivitiesAsUser == null || queryIntentActivitiesAsUser.size() == 0) {
                return EMPTY_RESOLVE_INFO;
            }
            queryIntentActivitiesAsUser.removeIf(ACTIVITY_NOT_INSTALLED);
            queryIntentActivitiesAsUser.removeIf(
                    new Predicate() { // from class:
                        // com.android.server.pm.ShortcutService$$ExternalSyntheticLambda28
                        /* JADX WARN: Code restructure failed: missing block: B:23:0x0031, code lost:

                           if (r6.enabled != false) goto L19;
                        */
                        /* JADX WARN: Code restructure failed: missing block: B:24:0x0033, code lost:

                           if (r5 == 1) goto L19;
                        */
                        @Override // java.util.function.Predicate
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                            To view partially-correct code enable 'Show inconsistent code' option in preferences
                        */
                        public final boolean test(java.lang.Object r6) {
                            /*
                                r5 = this;
                                com.android.server.pm.ShortcutService r0 = com.android.server.pm.ShortcutService.this
                                int r5 = r2
                                android.content.pm.ResolveInfo r6 = (android.content.pm.ResolveInfo) r6
                                r0.getClass()
                                android.content.pm.ActivityInfo r6 = r6.activityInfo
                                if (r6 == 0) goto L18
                                android.content.pm.ApplicationInfo r1 = r6.applicationInfo
                                if (r1 == 0) goto L18
                                int r1 = r1.flags
                                r1 = r1 & 129(0x81, float:1.81E-43)
                                if (r1 == 0) goto L18
                                goto L35
                            L18:
                                r1 = 1
                                if (r6 != 0) goto L1c
                                goto L44
                            L1c:
                                long r2 = android.os.Binder.clearCallingIdentity()
                                android.content.pm.IPackageManager r0 = r0.mIPackageManager     // Catch: java.lang.Throwable -> L37 android.os.RemoteException -> L39
                                android.content.ComponentName r4 = r6.getComponentName()     // Catch: java.lang.Throwable -> L37 android.os.RemoteException -> L39
                                int r5 = r0.getComponentEnabledSetting(r4, r5)     // Catch: java.lang.Throwable -> L37 android.os.RemoteException -> L39
                                android.os.Binder.restoreCallingIdentity(r2)
                                if (r5 != 0) goto L33
                                boolean r6 = r6.enabled
                                if (r6 != 0) goto L35
                            L33:
                                if (r5 != r1) goto L44
                            L35:
                                r1 = 0
                                goto L44
                            L37:
                                r5 = move-exception
                                goto L45
                            L39:
                                r5 = move-exception
                                java.lang.String r6 = "ShortcutService"
                                java.lang.String r0 = "RemoteException"
                                android.util.Slog.wtf(r6, r0, r5)     // Catch: java.lang.Throwable -> L37
                                android.os.Binder.restoreCallingIdentity(r2)
                            L44:
                                return r1
                            L45:
                                android.os.Binder.restoreCallingIdentity(r2)
                                throw r5
                            */
                            throw new UnsupportedOperationException(
                                    "Method not decompiled:"
                                        + " com.android.server.pm.ShortcutService$$ExternalSyntheticLambda28.test(java.lang.Object):boolean");
                        }
                    });
            if (z) {
                queryIntentActivitiesAsUser.removeIf(ACTIVITY_NOT_EXPORTED);
            }
            return queryIntentActivitiesAsUser;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public List queryActivities(Intent intent, String str, ComponentName componentName, int i) {
        Objects.requireNonNull(str);
        intent.setPackage(str);
        if (componentName != null) {
            intent.setComponent(componentName);
        }
        return queryActivities(i, intent, true);
    }

    public final void removeAllDynamicShortcuts(String str, int i) {
        ShortcutPackage packageShortcutsForPublisherLocked;
        List deleteAllDynamicShortcuts;
        List prepareChangedShortcuts;
        verifyCaller(i, str);
        ArrayList arrayList = new ArrayList();
        synchronized (this.mServiceLock) {
            throwIfUserLockedL(i);
            packageShortcutsForPublisherLocked = getPackageShortcutsForPublisherLocked(i, str);
            packageShortcutsForPublisherLocked.findAll(
                    arrayList,
                    new ShortcutService$$ExternalSyntheticLambda12(1),
                    4,
                    null,
                    0,
                    false);
            deleteAllDynamicShortcuts =
                    packageShortcutsForPublisherLocked.deleteAllDynamicShortcuts();
            ArraySet arraySet = new ArraySet();
            addShortcutIdsToSet(arraySet, arrayList);
            ArraySet arraySet2 = new ArraySet();
            addShortcutIdsToSet(arraySet2, null);
            prepareChangedShortcuts =
                    prepareChangedShortcuts(
                            arraySet,
                            arraySet2,
                            deleteAllDynamicShortcuts,
                            packageShortcutsForPublisherLocked);
        }
        packageShortcutsChanged(
                packageShortcutsForPublisherLocked,
                prepareChangedShortcuts,
                deleteAllDynamicShortcuts);
        verifyStates();
    }

    public final void removeDynamicShortcuts(String str, List list, int i) {
        ShortcutPackage packageShortcutsForPublisherLocked;
        ArrayList arrayList;
        ArrayList arrayList2;
        verifyCaller(i, str);
        Objects.requireNonNull(list, "shortcutIds must be provided");
        synchronized (this.mServiceLock) {
            try {
                throwIfUserLockedL(i);
                packageShortcutsForPublisherLocked = getPackageShortcutsForPublisherLocked(i, str);
                packageShortcutsForPublisherLocked.ensureImmutableShortcutsNotIncludedWithIds(list);
                arrayList = null;
                arrayList2 = null;
                for (int size = list.size() - 1; size >= 0; size--) {
                    String str2 =
                            (String) Preconditions.checkStringNotEmpty((String) list.get(size));
                    if (packageShortcutsForPublisherLocked.isShortcutExistsAndVisibleToPublisher(
                            str2)) {
                        ShortcutInfo deleteOrDisableWithId =
                                packageShortcutsForPublisherLocked.deleteOrDisableWithId(
                                        str2, false, false, true, 0, false);
                        if (deleteOrDisableWithId == null) {
                            if (arrayList == null) {
                                arrayList = new ArrayList(1);
                            }
                            arrayList.add(
                                    packageShortcutsForPublisherLocked.findShortcutById(str2));
                        } else {
                            if (arrayList2 == null) {
                                arrayList2 = new ArrayList(1);
                            }
                            arrayList2.add(deleteOrDisableWithId);
                        }
                    }
                }
                packageShortcutsForPublisherLocked.adjustRanks();
            } catch (Throwable th) {
                throw th;
            }
        }
        packageShortcutsChanged(packageShortcutsForPublisherLocked, arrayList, arrayList2);
        verifyStates();
    }

    public final void removeLongLivedShortcuts(String str, List list, int i) {
        ShortcutPackage packageShortcutsForPublisherLocked;
        ArrayList arrayList;
        ArrayList arrayList2;
        verifyCaller(i, str);
        Objects.requireNonNull(list, "shortcutIds must be provided");
        synchronized (this.mServiceLock) {
            try {
                throwIfUserLockedL(i);
                packageShortcutsForPublisherLocked = getPackageShortcutsForPublisherLocked(i, str);
                packageShortcutsForPublisherLocked.ensureImmutableShortcutsNotIncludedWithIds(list);
                int size = list.size() - 1;
                ShortcutInfo shortcutInfo = null;
                arrayList = null;
                arrayList2 = null;
                while (size >= 0) {
                    String str2 =
                            (String) Preconditions.checkStringNotEmpty((String) list.get(size));
                    if (packageShortcutsForPublisherLocked.isShortcutExistsAndVisibleToPublisher(
                            str2)) {
                        if (packageShortcutsForPublisherLocked.findShortcutById(str2) != null) {
                            packageShortcutsForPublisherLocked.mutateShortcut(
                                    str2,
                                    shortcutInfo,
                                    new ShortcutPackage$$ExternalSyntheticLambda14(0));
                        }
                        ShortcutInfo deleteOrDisableWithId =
                                packageShortcutsForPublisherLocked.deleteOrDisableWithId(
                                        str2, false, false, true, 0, false);
                        if (deleteOrDisableWithId != null) {
                            if (arrayList2 == null) {
                                arrayList2 = new ArrayList(1);
                            }
                            arrayList2.add(deleteOrDisableWithId);
                        } else {
                            if (arrayList == null) {
                                arrayList = new ArrayList(1);
                            }
                            arrayList.add(
                                    packageShortcutsForPublisherLocked.findShortcutById(str2));
                        }
                    }
                    size--;
                    shortcutInfo = null;
                }
                packageShortcutsForPublisherLocked.adjustRanks();
            } catch (Throwable th) {
                throw th;
            }
        }
        packageShortcutsChanged(packageShortcutsForPublisherLocked, arrayList, arrayList2);
        verifyStates();
    }

    public final void reportShortcutUsed(String str, String str2, int i) {
        verifyCaller(i, str);
        Objects.requireNonNull(str2);
        synchronized (this.mServiceLock) {
            try {
                throwIfUserLockedL(i);
                ShortcutPackage packageShortcutsForPublisherLocked =
                        getPackageShortcutsForPublisherLocked(i, str);
                if (packageShortcutsForPublisherLocked.findShortcutById(str2) != null) {
                    packageShortcutsForPublisherLocked.reportShortcutUsed(
                            this.mUsageStatsManagerInternal, str2);
                    return;
                }
                Log.w(
                        "ShortcutService",
                        "reportShortcutUsed: package " + str + " doesn't have shortcut " + str2);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean requestPinItem(
            String str,
            int i,
            ShortcutInfo shortcutInfo,
            AppWidgetProviderInfo appWidgetProviderInfo,
            Bundle bundle,
            IntentSender intentSender,
            int i2,
            int i3,
            int i4) {
        boolean requestPinItemLocked;
        ShortcutPackage packageShortcutsForPublisherLocked;
        ShortcutInfo findShortcutById;
        verifyCaller(i, str);
        if (shortcutInfo == null || !injectHasAccessShortcutsPermission(i2, i3)) {
            verifyShortcutInfoPackage(str, shortcutInfo);
        }
        synchronized (this.mServiceLock) {
            try {
                throwIfUserLockedL(i);
                Preconditions.checkState(
                        isUidForegroundLocked(i3),
                        "Calling application must have a foreground activity or a foreground"
                            + " service");
                if (shortcutInfo != null
                        && (findShortcutById =
                                        (packageShortcutsForPublisherLocked =
                                                        getPackageShortcutsForPublisherLocked(
                                                                i, shortcutInfo.getPackage()))
                                                .findShortcutById(shortcutInfo.getId()))
                                != null
                        && !findShortcutById.isVisibleToPublisher()) {
                    Objects.requireNonNull(
                            packageShortcutsForPublisherLocked.findShortcutById(
                                    shortcutInfo.getId()));
                    packageShortcutsForPublisherLocked.mShortcutUser.mService
                            .fixUpIncomingShortcutInfo(shortcutInfo, false, true);
                    shortcutInfo.addFlags(2);
                    packageShortcutsForPublisherLocked.forceReplaceShortcutInner(shortcutInfo);
                    packageShortcutsForPublisherLocked.adjustRanks();
                    packageShortcutsChanged(
                            packageShortcutsForPublisherLocked,
                            Collections.singletonList(shortcutInfo),
                            null);
                }
                requestPinItemLocked =
                        this.mShortcutRequestPinProcessor.requestPinItemLocked(
                                shortcutInfo, appWidgetProviderInfo, bundle, i, intentSender, i4);
            } catch (Throwable th) {
                throw th;
            }
        }
        verifyStates();
        return requestPinItemLocked;
    }

    public final void requestPinShortcut(
            String str,
            ShortcutInfo shortcutInfo,
            IntentSender intentSender,
            int i,
            AndroidFuture androidFuture) {
        Objects.requireNonNull(shortcutInfo);
        Preconditions.checkArgument(shortcutInfo.isEnabled(), "Shortcut must be enabled");
        Preconditions.checkArgument(
                true ^ shortcutInfo.isExcludedFromSurfaces(1),
                "Shortcut excluded from launcher cannot be pinned");
        androidFuture.complete(
                String.valueOf(
                        requestPinItem(
                                str,
                                i,
                                shortcutInfo,
                                null,
                                null,
                                intentSender,
                                injectBinderCallingPid(),
                                injectBinderCallingUid(),
                                0)));
    }

    public final void requestPinShortcutAsDisplay(
            String str,
            ShortcutInfo shortcutInfo,
            IntentSender intentSender,
            int i,
            int i2,
            AndroidFuture androidFuture) {
        Objects.requireNonNull(shortcutInfo);
        Preconditions.checkArgument(shortcutInfo.isEnabled(), "Shortcut must be enabled");
        Preconditions.checkArgument(
                true ^ shortcutInfo.isExcludedFromSurfaces(1),
                "Shortcut excluded from launcher cannot be pinned");
        androidFuture.complete(
                String.valueOf(
                        requestPinItem(
                                str,
                                i,
                                shortcutInfo,
                                null,
                                null,
                                intentSender,
                                injectBinderCallingPid(),
                                injectBinderCallingUid(),
                                i2)));
    }

    public final void rescanUpdatedPackagesLocked(int i, long j) {
        Slog.d("ShortcutService", "rescan updated package user=" + i + " last scanned=" + j);
        ShortcutUser userShortcutsLocked = getUserShortcutsLocked(i);
        long injectCurrentTimeMillis = injectCurrentTimeMillis();
        boolean equals =
                Build.FINGERPRINT.equals(userShortcutsLocked.mLastAppScanOsFingerprint) ^ true;
        ShortcutService$$ExternalSyntheticLambda22 shortcutService$$ExternalSyntheticLambda22 =
                new ShortcutService$$ExternalSyntheticLambda22(this, userShortcutsLocked, i);
        StringBuilder m =
                UserspaceRebootLogger$$ExternalSyntheticOutline0.m(
                        i, "forUpdatedPackages for user ", j, ", lastScanTime=");
        m.append(" afterOta=");
        m.append(equals);
        Slog.d("ShortcutService", m.toString());
        List installedPackages = getInstalledPackages(i);
        for (int size = installedPackages.size() - 1; size >= 0; size--) {
            PackageInfo packageInfo = (PackageInfo) installedPackages.get(size);
            if (equals || packageInfo.lastUpdateTime >= j) {
                StringBuilder sb = new StringBuilder("Found updated package ");
                sb.append(packageInfo.packageName);
                sb.append(" updateTime=");
                BatteryService$$ExternalSyntheticOutline0.m(
                        sb, packageInfo.lastUpdateTime, "ShortcutService");
                shortcutService$$ExternalSyntheticLambda22.accept(packageInfo.applicationInfo);
            }
        }
        userShortcutsLocked.mLastAppScanTime = injectCurrentTimeMillis;
        userShortcutsLocked.mLastAppScanOsFingerprint = Build.FINGERPRINT;
        scheduleSaveInner(i);
    }

    public final void resetThrottling() {
        int injectBinderCallingUid;
        if (!isCallerSystem()
                && (injectBinderCallingUid = injectBinderCallingUid()) != 2000
                && injectBinderCallingUid != 0) {
            throw new SecurityException("Caller must be system or shell");
        }
        resetThrottlingInner(UserHandle.getUserId(injectBinderCallingUid()));
    }

    public final void resetThrottlingInner(int i) {
        synchronized (this.mServiceLock) {
            try {
                if (!isUserUnlockedL(i)) {
                    Log.w("ShortcutService", "User " + i + " is locked or not running");
                    return;
                }
                ShortcutUser userShortcutsLocked = getUserShortcutsLocked(i);
                for (int size = userShortcutsLocked.mPackages.size() - 1; size >= 0; size--) {
                    ((ShortcutPackage) userShortcutsLocked.mPackages.valueAt(size)).mApiCallCount =
                            0;
                }
                scheduleSaveInner(i);
                Slog.i(
                        "ShortcutService",
                        "ShortcutManager: throttling counter reset for user " + i);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void restoreBitmapsFromBackupService(
            ParcelFileDescriptor parcelFileDescriptor, String str, String str2) {
        injectEnforceCallingPermission("com.samsung.android.scloud.backup.lib.write", null);
        try {
            ParcelFileDescriptor.AutoCloseInputStream autoCloseInputStream =
                    new ParcelFileDescriptor.AutoCloseInputStream(parcelFileDescriptor);
            try {
                FileOutputStream openIconFileForWriteSmartSwitch =
                        openIconFileForWriteSmartSwitch(str, str2);
                try {
                    byte[] bArr = new byte[1024];
                    while (true) {
                        int read = autoCloseInputStream.read(bArr);
                        if (read <= 0) {
                            openIconFileForWriteSmartSwitch.close();
                            autoCloseInputStream.close();
                            return;
                        }
                        openIconFileForWriteSmartSwitch.write(bArr, 0, read);
                    }
                } finally {
                }
            } finally {
            }
        } catch (Exception unused) {
        }
    }

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
    jadx.core.utils.exceptions.JadxRuntimeException: Can't find top splitter block for handler:B:32:0x0065
    	at jadx.core.utils.BlockUtils.getTopSplitterForHandler(BlockUtils.java:1179)
    	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.collectHandlerRegions(ExcHandlersRegionMaker.java:53)
    	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.process(ExcHandlersRegionMaker.java:38)
    	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:27)
    */
    public void saveBaseState() {
        /*
            r7 = this;
            java.lang.String r0 = "Saving to "
            com.android.server.pm.ResilientAtomicFile r1 = r7.getBaseStateFile()
            java.lang.String r2 = "ShortcutService"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L5d
            r3.<init>(r0)     // Catch: java.lang.Throwable -> L5d
            java.io.File r0 = r1.mFile     // Catch: java.lang.Throwable -> L5d
            r3.append(r0)     // Catch: java.lang.Throwable -> L5d
            java.lang.String r0 = r3.toString()     // Catch: java.lang.Throwable -> L5d
            android.util.Slog.d(r2, r0)     // Catch: java.lang.Throwable -> L5d
            r0 = 0
            java.lang.Object r2 = r7.mServiceLock     // Catch: java.lang.Throwable -> L5d java.io.IOException -> L68
            monitor-enter(r2)     // Catch: java.lang.Throwable -> L5d java.io.IOException -> L68
            java.io.FileOutputStream r3 = r1.startWrite()     // Catch: java.lang.Throwable -> L65
            monitor-exit(r2)     // Catch: java.lang.Throwable -> L62
            com.android.modules.utils.TypedXmlSerializer r2 = android.util.Xml.resolveSerializer(r3)     // Catch: java.lang.Throwable -> L5d java.io.IOException -> L5f
            java.lang.Boolean r4 = java.lang.Boolean.TRUE     // Catch: java.lang.Throwable -> L5d java.io.IOException -> L5f
            r2.startDocument(r0, r4)     // Catch: java.lang.Throwable -> L5d java.io.IOException -> L5f
            java.lang.String r4 = "root"
            r2.startTag(r0, r4)     // Catch: java.lang.Throwable -> L5d java.io.IOException -> L5f
            java.lang.String r4 = "last_reset_time"
            java.util.concurrent.atomic.AtomicLong r7 = r7.mRawLastResetTime     // Catch: java.lang.Throwable -> L5d java.io.IOException -> L5f
            long r5 = r7.get()     // Catch: java.lang.Throwable -> L5d java.io.IOException -> L5f
            java.lang.String r7 = java.lang.Long.toString(r5)     // Catch: java.lang.Throwable -> L5d java.io.IOException -> L5f
            boolean r5 = android.text.TextUtils.isEmpty(r7)     // Catch: java.lang.Throwable -> L5d java.io.IOException -> L5f
            if (r5 == 0) goto L44
            goto L50
        L44:
            r2.startTag(r0, r4)     // Catch: java.lang.Throwable -> L5d java.io.IOException -> L5f
            java.lang.String r5 = "value"
            r2.attribute(r0, r5, r7)     // Catch: java.lang.Throwable -> L5d java.io.IOException -> L5f
            r2.endTag(r0, r4)     // Catch: java.lang.Throwable -> L5d java.io.IOException -> L5f
        L50:
            java.lang.String r7 = "root"
            r2.endTag(r0, r7)     // Catch: java.lang.Throwable -> L5d java.io.IOException -> L5f
            r2.endDocument()     // Catch: java.lang.Throwable -> L5d java.io.IOException -> L5f
            r1.finishWrite(r3)     // Catch: java.lang.Throwable -> L5d java.io.IOException -> L5f
            goto L84
        L5d:
            r7 = move-exception
            goto L88
        L5f:
            r7 = move-exception
            r0 = r3
            goto L69
        L62:
            r7 = move-exception
            r0 = r3
            goto L66
        L65:
            r7 = move-exception
        L66:
            monitor-exit(r2)     // Catch: java.lang.Throwable -> L65
            throw r7     // Catch: java.lang.Throwable -> L5d java.io.IOException -> L68
        L68:
            r7 = move-exception
        L69:
            java.lang.String r2 = "ShortcutService"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L5d
            r3.<init>()     // Catch: java.lang.Throwable -> L5d
            java.lang.String r4 = "Failed to write to file "
            r3.append(r4)     // Catch: java.lang.Throwable -> L5d
            java.io.File r4 = r1.mFile     // Catch: java.lang.Throwable -> L5d
            r3.append(r4)     // Catch: java.lang.Throwable -> L5d
            java.lang.String r3 = r3.toString()     // Catch: java.lang.Throwable -> L5d
            android.util.Slog.e(r2, r3, r7)     // Catch: java.lang.Throwable -> L5d
            r1.failWrite(r0)     // Catch: java.lang.Throwable -> L5d
        L84:
            r1.close()
            return
        L88:
            r1.close()     // Catch: java.lang.Throwable -> L8c
            goto L90
        L8c:
            r0 = move-exception
            r7.addSuppressed(r0)
        L90:
            throw r7
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.pm.ShortcutService.saveBaseState():void");
    }

    public void saveDirtyInfo() {
        List list;
        Slog.d("ShortcutService", "saveDirtyInfo");
        if (this.mShutdown.get()) {
            return;
        }
        try {
            try {
                Trace.traceBegin(524288L, "shortcutSaveDirtyInfo");
                ArrayList arrayList = new ArrayList();
                synchronized (this.mServiceLock) {
                    list = this.mDirtyUserIds;
                    this.mDirtyUserIds = arrayList;
                }
                for (int size = list.size() - 1; size >= 0; size--) {
                    int intValue = ((Integer) list.get(size)).intValue();
                    if (intValue == -10000) {
                        saveBaseState();
                    } else {
                        saveUser(intValue);
                    }
                }
            } catch (Exception e) {
                wtf("Exception in saveDirtyInfo", e);
            }
            Trace.traceEnd(524288L);
        } catch (Throwable th) {
            Trace.traceEnd(524288L);
            throw th;
        }
    }

    public final void saveIconAndFixUpShortcutLocked(
            ShortcutPackage shortcutPackage, ShortcutInfo shortcutInfo) {
        if (shortcutInfo.hasIconFile()
                || shortcutInfo.hasIconResource()
                || shortcutInfo.hasIconUri()) {
            return;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            shortcutPackage.removeIcon(shortcutInfo);
            Icon icon = shortcutInfo.getIcon();
            if (icon == null) {
                return;
            }
            int i = this.mMaxIconDimension;
            try {
                int type = icon.getType();
                if (type == 1) {
                    icon.getBitmap();
                } else {
                    if (type == 2) {
                        if (!shortcutInfo.getPackage().equals(icon.getResPackage())) {
                            throw new IllegalArgumentException(
                                    "Icon resource must reside in shortcut owner package");
                        }
                        shortcutInfo.setIconResourceId(icon.getResId());
                        shortcutInfo.addFlags(4);
                        return;
                    }
                    if (type == 4) {
                        shortcutInfo.setIconUri(icon.getUriString());
                        shortcutInfo.addFlags(32768);
                        return;
                    } else {
                        if (type != 5) {
                            if (type != 6) {
                                throw ShortcutInfo.getInvalidIconException();
                            }
                            shortcutInfo.setIconUri(icon.getUriString());
                            shortcutInfo.addFlags(33280);
                            return;
                        }
                        icon.getBitmap();
                        i =
                                (int)
                                        (((AdaptiveIconDrawable.getExtraInsetFraction() * 2.0f)
                                                        + 1.0f)
                                                * i);
                    }
                }
                Bitmap.CompressFormat compressFormat = this.mIconPersistFormat;
                int i2 = this.mIconPersistQuality;
                synchronized (shortcutPackage.mPackageItemLock) {
                    shortcutPackage.mShortcutBitmapSaver.saveBitmapLocked(
                            shortcutInfo, i, compressFormat, i2);
                }
            } finally {
                shortcutInfo.clearIcon();
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x005a  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x006f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void saveUser(int r17) {
        /*
            Method dump skipped, instructions count: 321
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled: com.android.server.pm.ShortcutService.saveUser(int):void");
    }

    public final void saveUserInternalLocked(OutputStream outputStream, boolean z, int i) {
        TypedXmlSerializer resolveSerializer;
        if (z) {
            resolveSerializer = Xml.newFastSerializer();
            resolveSerializer.setOutput(outputStream, StandardCharsets.UTF_8.name());
        } else {
            resolveSerializer = Xml.resolveSerializer(outputStream);
        }
        resolveSerializer.startDocument((String) null, Boolean.TRUE);
        ShortcutUser userShortcutsLocked = getUserShortcutsLocked(i);
        resolveSerializer.startTag((String) null, "user");
        if (z) {
            userShortcutsLocked.mService.getClass();
            writeAttr(resolveSerializer, "restore-from-fp", Build.FINGERPRINT);
        } else {
            writeAttr(resolveSerializer, "locales", userShortcutsLocked.mKnownLocales);
            writeAttr(
                    resolveSerializer, "last-app-scan-time2", userShortcutsLocked.mLastAppScanTime);
            writeAttr(
                    resolveSerializer,
                    "last-app-scan-fp",
                    userShortcutsLocked.mLastAppScanOsFingerprint);
            writeAttr(
                    resolveSerializer,
                    "restore-from-fp",
                    userShortcutsLocked.mRestoreFromOsFingerprint);
        }
        int size = userShortcutsLocked.mLaunchers.size();
        for (int i2 = 0; i2 < size; i2++) {
            ShortcutPackageItem shortcutPackageItem =
                    (ShortcutPackageItem) userShortcutsLocked.mLaunchers.valueAt(i2);
            if (!z) {
                shortcutPackageItem.scheduleSave();
            } else if (shortcutPackageItem.mPackageUserId == shortcutPackageItem.getOwnerUserId()) {
                shortcutPackageItem.waitForBitmapSaves();
                shortcutPackageItem.saveToXml(resolveSerializer, z);
            }
        }
        int size2 = userShortcutsLocked.mPackages.size();
        for (int i3 = 0; i3 < size2; i3++) {
            ShortcutPackageItem shortcutPackageItem2 =
                    (ShortcutPackageItem) userShortcutsLocked.mPackages.valueAt(i3);
            if (!z) {
                shortcutPackageItem2.scheduleSave();
            } else if (shortcutPackageItem2.mPackageUserId
                    == shortcutPackageItem2.getOwnerUserId()) {
                shortcutPackageItem2.waitForBitmapSaves();
                shortcutPackageItem2.saveToXml(resolveSerializer, z);
            }
        }
        resolveSerializer.endTag((String) null, "user");
        resolveSerializer.endDocument();
        outputStream.flush();
    }

    public final void scheduleSaveInner(int i) {
        AnyMotionDetector$$ExternalSyntheticOutline0.m(
                i, "Scheduling to save for ", "ShortcutService");
        synchronized (this.mServiceLock) {
            try {
                if (!((ArrayList) this.mDirtyUserIds).contains(Integer.valueOf(i))) {
                    ((ArrayList) this.mDirtyUserIds).add(Integer.valueOf(i));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        this.mHandler.removeCallbacks(this.mSaveDirtyInfoRunner);
        this.mHandler.postDelayed(this.mSaveDirtyInfoRunner, this.mSaveDelayMillis);
    }

    public final boolean setDynamicShortcuts(
            String str, ParceledListSlice parceledListSlice, int i) {
        verifyCaller(i, str);
        boolean injectHasUnlimitedShortcutsApiCallsPermission =
                injectHasUnlimitedShortcutsApiCallsPermission(
                        injectBinderCallingPid(), injectBinderCallingUid());
        List list = parceledListSlice.getList();
        verifyShortcutInfoPackages(str, list);
        int size = list.size();
        synchronized (this.mServiceLock) {
            try {
                throwIfUserLockedL(i);
                ShortcutPackage packageShortcutsForPublisherLocked =
                        getPackageShortcutsForPublisherLocked(i, str);
                packageShortcutsForPublisherLocked.ensureImmutableShortcutsNotIncluded(list);
                ShortcutPackage.ensureNoBitmapIconIfShortcutIsLongLived(list);
                fillInDefaultActivity(list);
                packageShortcutsForPublisherLocked.enforceShortcutCountsBeforeOperation(0, list);
                if (!packageShortcutsForPublisherLocked.tryApiCall(
                        injectHasUnlimitedShortcutsApiCallsPermission)) {
                    return false;
                }
                packageShortcutsForPublisherLocked.forEachShortcutMutate(
                        new ShortcutPackage$$ExternalSyntheticLambda14(1));
                assignImplicitRanks(list);
                for (int i2 = 0; i2 < size; i2++) {
                    fixUpIncomingShortcutInfo((ShortcutInfo) list.get(i2), false, false);
                }
                ArrayList arrayList = new ArrayList();
                packageShortcutsForPublisherLocked.findAll(
                        arrayList,
                        new ShortcutService$$ExternalSyntheticLambda12(0),
                        4,
                        null,
                        0,
                        false);
                List deleteAllDynamicShortcuts =
                        packageShortcutsForPublisherLocked.deleteAllDynamicShortcuts();
                for (int i3 = 0; i3 < size; i3++) {
                    packageShortcutsForPublisherLocked.addOrReplaceDynamicShortcut(
                            (ShortcutInfo) list.get(i3));
                }
                packageShortcutsForPublisherLocked.adjustRanks();
                ArraySet arraySet = new ArraySet();
                addShortcutIdsToSet(arraySet, arrayList);
                ArraySet arraySet2 = new ArraySet();
                addShortcutIdsToSet(arraySet2, list);
                packageShortcutsChanged(
                        packageShortcutsForPublisherLocked,
                        prepareChangedShortcuts(
                                arraySet,
                                arraySet2,
                                deleteAllDynamicShortcuts,
                                packageShortcutsForPublisherLocked),
                        deleteAllDynamicShortcuts);
                verifyStates();
                return true;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void throwIfUserLockedL(int i) {
        if (!isUserUnlockedL(i)) {
            throw new IllegalStateException(
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                            i, "User ", " is locked or not running"));
        }
    }

    public final void unloadUserLocked(int i) {
        Slog.d("ShortcutService", "unloadUserLocked: user=" + i);
        getUserShortcutsLocked(i).cancelAllInFlightTasks();
        saveDirtyInfo();
        this.mUsers.delete(i);
    }

    public boolean updateConfigurationLocked(String str) {
        boolean z;
        KeyValueListParser keyValueListParser = new KeyValueListParser(',');
        try {
            keyValueListParser.setString(str);
            z = true;
        } catch (IllegalArgumentException e) {
            Slog.e("ShortcutService", "Bad shortcut manager settings", e);
            z = false;
        }
        this.mSaveDelayMillis =
                Math.max(0, (int) keyValueListParser.getLong("save_delay_ms", 3000L));
        this.mResetInterval =
                Math.max(
                        1L,
                        keyValueListParser.getLong("reset_interval_sec", DEFAULT_RESET_INTERVAL_SEC)
                                * 1000);
        this.mMaxUpdatesPerInterval =
                Math.max(0, (int) keyValueListParser.getLong("max_updates_per_interval", 10L));
        this.mMaxShortcuts = Math.max(0, (int) keyValueListParser.getLong("max_shortcuts", 15L));
        this.mMaxShortcutsPerApp =
                Math.max(0, (int) keyValueListParser.getLong("max_shortcuts_per_app", 100L));
        this.mMaxIconDimension =
                injectDipToPixel(
                        Math.max(
                                1,
                                (int)
                                        (injectIsLowRamDevice()
                                                ? keyValueListParser.getLong(
                                                        "max_icon_dimension_dp_lowram", 48L)
                                                : keyValueListParser.getLong(
                                                        "max_icon_dimension_dp", 96L))));
        this.mIconPersistFormat =
                Bitmap.CompressFormat.valueOf(
                        keyValueListParser.getString("icon_format", DEFAULT_ICON_PERSIST_FORMAT));
        this.mIconPersistQuality = (int) keyValueListParser.getLong("icon_quality", 100L);
        return z;
    }

    public void updatePackageShortcutForTest(String str, String str2, int i, Consumer consumer) {
        synchronized (this.mServiceLock) {
            try {
                ShortcutPackage packageShortcutForTest = getPackageShortcutForTest(str, i);
                if (packageShortcutForTest == null) {
                    return;
                }
                consumer.accept(packageShortcutForTest.findShortcutById(str2));
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean updateShortcuts(String str, ParceledListSlice parceledListSlice, int i) {
        verifyCaller(i, str);
        boolean injectHasUnlimitedShortcutsApiCallsPermission =
                injectHasUnlimitedShortcutsApiCallsPermission(
                        injectBinderCallingPid(), injectBinderCallingUid());
        List list = parceledListSlice.getList();
        verifyShortcutInfoPackages(str, list);
        int size = list.size();
        final ArrayList arrayList = new ArrayList(1);
        synchronized (this.mServiceLock) {
            try {
                throwIfUserLockedL(i);
                final ShortcutPackage packageShortcutsForPublisherLocked =
                        getPackageShortcutsForPublisherLocked(i, str);
                packageShortcutsForPublisherLocked.ensureImmutableShortcutsNotIncluded(list);
                ShortcutPackage.ensureNoBitmapIconIfShortcutIsLongLived(list);
                ShortcutPackage.ensureAllShortcutsVisibleToLauncher(list);
                packageShortcutsForPublisherLocked.enforceShortcutCountsBeforeOperation(2, list);
                if (!packageShortcutsForPublisherLocked.tryApiCall(
                        injectHasUnlimitedShortcutsApiCallsPermission)) {
                    return false;
                }
                packageShortcutsForPublisherLocked.forEachShortcutMutate(
                        new ShortcutPackage$$ExternalSyntheticLambda14(1));
                assignImplicitRanks(list);
                for (int i2 = 0; i2 < size; i2++) {
                    final ShortcutInfo shortcutInfo = (ShortcutInfo) list.get(i2);
                    fixUpIncomingShortcutInfo(shortcutInfo, true, false);
                    packageShortcutsForPublisherLocked.mutateShortcut(
                            shortcutInfo.getId(),
                            null,
                            new Consumer() { // from class:
                                // com.android.server.pm.ShortcutService$$ExternalSyntheticLambda14
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj) {
                                    ShortcutService shortcutService = ShortcutService.this;
                                    ShortcutInfo shortcutInfo2 = shortcutInfo;
                                    ShortcutPackage shortcutPackage =
                                            packageShortcutsForPublisherLocked;
                                    List list2 = arrayList;
                                    ShortcutInfo shortcutInfo3 = (ShortcutInfo) obj;
                                    shortcutService.getClass();
                                    if (shortcutInfo3 == null
                                            || !shortcutInfo3.isVisibleToPublisher()) {
                                        return;
                                    }
                                    if (shortcutInfo3.isEnabled() != shortcutInfo2.isEnabled()) {
                                        Slog.w(
                                                "ShortcutService",
                                                "ShortcutInfo.enabled cannot be changed with"
                                                    + " updateShortcuts()");
                                    }
                                    if (shortcutInfo3.isLongLived()
                                            != shortcutInfo2.isLongLived()) {
                                        Slog.w(
                                                "ShortcutService",
                                                "ShortcutInfo.longLived cannot be changed with"
                                                    + " updateShortcuts()");
                                    }
                                    if (shortcutInfo2.hasRank()) {
                                        shortcutInfo3.setRankChanged();
                                        shortcutInfo3.setImplicitRank(
                                                shortcutInfo2.getImplicitRank());
                                    }
                                    boolean z = shortcutInfo2.getIcon() != null;
                                    if (z) {
                                        shortcutPackage.removeIcon(shortcutInfo3);
                                    }
                                    shortcutInfo3.copyNonNullFieldsFrom(shortcutInfo2);
                                    shortcutInfo3.setTimestamp(
                                            shortcutService.injectCurrentTimeMillis());
                                    if (z) {
                                        shortcutService.saveIconAndFixUpShortcutLocked(
                                                shortcutPackage, shortcutInfo3);
                                    }
                                    if (z || shortcutInfo2.hasStringResources()) {
                                        shortcutService.fixUpShortcutResourceNamesAndValues(
                                                shortcutInfo3);
                                    }
                                    list2.add(shortcutInfo3);
                                }
                            });
                }
                packageShortcutsForPublisherLocked.adjustRanks();
                if (arrayList.isEmpty()) {
                    arrayList = null;
                }
                packageShortcutsChanged(packageShortcutsForPublisherLocked, arrayList, null);
                verifyStates();
                return true;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void updateTimesLocked() {
        long injectCurrentTimeMillis = injectCurrentTimeMillis();
        long j = this.mRawLastResetTime.get();
        if (j != 0) {
            if (injectCurrentTimeMillis < j) {
                if (injectCurrentTimeMillis >= 1420070400) {
                    Slog.w("ShortcutService", "Clock rewound");
                }
                injectCurrentTimeMillis = j;
            } else {
                long j2 = this.mResetInterval;
                if (j + j2 <= injectCurrentTimeMillis) {
                    injectCurrentTimeMillis = ((injectCurrentTimeMillis / j2) * j2) + (j % j2);
                }
                injectCurrentTimeMillis = j;
            }
        }
        this.mRawLastResetTime.set(injectCurrentTimeMillis);
        if (j != injectCurrentTimeMillis) {
            scheduleSaveInner(-10000);
        }
    }

    public final void verifyCaller(int i, String str) {
        Preconditions.checkStringNotEmpty(str, "packageName");
        if (isCallerSystem()) {
            return;
        }
        int injectBinderCallingUid = injectBinderCallingUid();
        if (UserHandle.getUserId(injectBinderCallingUid) != i) {
            throw new SecurityException("Invalid user-ID");
        }
        if (injectGetPackageUid(i, str) != injectBinderCallingUid) {
            throw new SecurityException("Calling package name mismatch");
        }
        ApplicationInfo applicationInfo = getApplicationInfo(str, i);
        Preconditions.checkState(
                !(applicationInfo != null && applicationInfo.isInstantApp()),
                "Ephemeral apps can't use ShortcutManager");
    }

    public final void verifyShortcutInfoPackage(String str, ShortcutInfo shortcutInfo) {
        if (shortcutInfo == null) {
            return;
        }
        if (!Objects.equals(str, shortcutInfo.getPackage())) {
            EventLog.writeEvent(1397638484, "109824443", -1, "");
            throw new SecurityException("Shortcut package name mismatch");
        }
        int injectBinderCallingUid = injectBinderCallingUid();
        if (UserHandle.getUserId(injectBinderCallingUid) != shortcutInfo.getUserId()) {
            if (UserHandle.getUserId(injectBinderCallingUid) != 0
                    || !SemDualAppManager.isDualAppId(shortcutInfo.getUserId())
                    || !SemDualAppManager.isInstalledWhitelistedPackage(
                            shortcutInfo.getPackage())) {
                throw new SecurityException("User-ID in shortcut doesn't match the caller");
            }
        }
    }

    public final void verifyShortcutInfoPackages(String str, List list) {
        int size = list.size();
        for (int i = 0; i < size; i++) {
            verifyShortcutInfoPackage(str, (ShortcutInfo) list.get(i));
        }
    }

    public final void verifyStates() {
        if (injectShouldPerformVerification()) {
            synchronized (this.mServiceLock) {
                forEachLoadedUserLocked(new ShortcutService$$ExternalSyntheticLambda4(1));
            }
        }
    }

    public void waitForBitmapSavesForTest() {
        synchronized (this.mServiceLock) {
            forEachLoadedUserLocked(new ShortcutService$$ExternalSyntheticLambda4(4));
        }
    }

    public final void wtf(String str, Throwable th) {
        if (th == null) {
            th = new RuntimeException("Stacktrace");
        }
        synchronized (this.mWtfLock) {
            this.mWtfCount++;
            this.mLastWtfStacktrace = new Exception("Last failure was logged here:");
        }
        Slog.wtf("ShortcutService", str, th);
    }
}
