package com.android.server.pm;

import android.R;
import android.apex.ApexSessionInfo;
import android.app.ActivityManager;
import android.app.AppGlobals;
import android.app.AppOpsManager;
import android.app.BroadcastOptions;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PackageDeleteObserver;
import android.app.PendingIntent;
import android.app.admin.DevicePolicyEventLogger;
import android.app.admin.DevicePolicyManager;
import android.app.admin.DevicePolicyManagerInternal;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.ApplicationInfo;
import android.content.pm.ArchivedPackageParcel;
import android.content.pm.IPackageInstaller;
import android.content.pm.IPackageInstallerCallback;
import android.content.pm.IPackageInstallerSession;
import android.content.pm.PackageInfo;
import android.content.pm.PackageInstaller;
import android.content.pm.PackageManager;
import android.content.pm.PackageManagerInternal;
import android.content.pm.ParceledListSlice;
import android.content.pm.VersionedPackage;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.graphics.Bitmap;
import android.graphics.PorterDuffColorFilter;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.net.Uri;
import android.net.shared.InitialConfiguration$$ExternalSyntheticOutline0;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.ParcelableException;
import android.os.RemoteCallback;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.SELinux;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.storage.StorageManager;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.AtomicFile;
import android.util.Base64;
import android.util.ExceptionUtils;
import android.util.Log;
import android.util.Pair;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.util.TimingsTraceLog;
import android.util.Xml;

import com.android.internal.content.InstallLocationUtils;
import com.android.internal.hidden_from_bootclasspath.android.content.pm.Flags;
import com.android.internal.notification.SystemNotificationChannels;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.FunctionalUtils;
import com.android.internal.util.ImageUtils;
import com.android.internal.util.IndentingPrintWriter;
import com.android.internal.util.Preconditions;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.HeimdAllFsService$$ExternalSyntheticOutline0;
import com.android.server.IoThread;
import com.android.server.LocalServices;
import com.android.server.SystemService;
import com.android.server.SystemServiceManager;
import com.android.server.SystemServiceManager$$ExternalSyntheticOutline0;
import com.android.server.UiModeManagerService$13$$ExternalSyntheticOutline0;
import com.android.server.accounts.AccountManagerService$$ExternalSyntheticOutline0;
import com.android.server.pm.utils.RequestThrottle;

import libcore.io.IoUtils;

import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.rune.PMRune;

import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.IntPredicate;

public final class PackageInstallerService extends IPackageInstaller.Stub
        implements PackageSessionProvider {
    public final ApexManager mApexManager;
    public AppOpsManager mAppOps;
    public final Callbacks mCallbacks;
    public final Context mContext;
    public final GentleUpdateHelper mGentleUpdateHelper;
    public final Handler mInstallHandler;
    public final HandlerThread mInstallThread;
    public final PackageArchiver mPackageArchiver;
    public final PackageManagerService mPm;
    public final PackageManagerInternal mPmInternal;
    public final PackageSessionVerifier mSessionVerifier;
    public final File mSessionsDir;
    public final AtomicFile mSessionsFile;
    public final StagingManager mStagingManager;
    public static final boolean LOGD = Log.isLoggable("PackageInstaller", 3);
    public static final boolean DEBUG = Build.IS_DEBUGGABLE;
    public static final Set INSTALLER_CHANGEABLE_APP_OP_PERMISSIONS =
            Set.of("android.permission.USE_FULL_SCREEN_INTENT");
    public static final AnonymousClass1 sStageFilter = new AnonymousClass1();
    public volatile boolean mOkToSendBroadcasts = false;
    public volatile boolean mBypassNextStagedInstallerCheck = false;
    public volatile boolean mBypassNextAllowedApexUpdateCheck = false;
    public volatile int mDisableVerificationForUid = -1;
    public final InternalCallback mInternalCallback = new InternalCallback();
    public final Random mRandom = new SecureRandom();
    public final SparseBooleanArray mAllocatedSessions = new SparseBooleanArray();
    public final SparseArray mSessions = new SparseArray();
    public final List mHistoricalSessions = new ArrayList();
    public final SparseIntArray mHistoricalSessionsByInstaller = new SparseIntArray();
    public final SparseBooleanArray mLegacySessions = new SparseBooleanArray();
    public final SilentUpdatePolicy mSilentUpdatePolicy = new SilentUpdatePolicy();
    public int mSmartSwitchUid = -1;
    public final Object mCreateLock = new Object();
    public final RequestThrottle mSettingsWriteRequest =
            new RequestThrottle(
                    IoThread.getHandler(),
                    new PackageInstallerService$$ExternalSyntheticLambda7(0, this));

    /* renamed from: com.android.server.pm.PackageInstallerService$1, reason: invalid class name */
    public final class AnonymousClass1 implements FilenameFilter {
        @Override // java.io.FilenameFilter
        public final boolean accept(File file, String str) {
            return PackageInstallerService.isStageName(str);
        }
    }

    public final class BroadcastCookie {
        public final int callingUid;
        public final IntPredicate userCheck;

        public BroadcastCookie(int i, IntPredicate intPredicate) {
            this.callingUid = i;
            this.userCheck = intPredicate;
        }
    }

    public final class Callbacks extends Handler {
        public static final /* synthetic */ int $r8$clinit = 0;
        public final RemoteCallbackList mCallbacks;

        public Callbacks(Looper looper) {
            super(looper);
            this.mCallbacks = new RemoteCallbackList();
        }

        public static void invokeCallback(
                IPackageInstallerCallback iPackageInstallerCallback, Message message) {
            int i = message.arg1;
            int i2 = message.what;
            if (i2 == 1) {
                iPackageInstallerCallback.onSessionCreated(i);
                return;
            }
            if (i2 == 2) {
                iPackageInstallerCallback.onSessionBadgingChanged(i);
                return;
            }
            if (i2 == 3) {
                iPackageInstallerCallback.onSessionActiveChanged(
                        i, ((Boolean) message.obj).booleanValue());
            } else if (i2 == 4) {
                iPackageInstallerCallback.onSessionProgressChanged(
                        i, ((Float) message.obj).floatValue());
            } else {
                if (i2 != 5) {
                    return;
                }
                iPackageInstallerCallback.onSessionFinished(
                        i, ((Boolean) message.obj).booleanValue());
            }
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            int i = message.arg1;
            int i2 = message.arg2;
            int beginBroadcast = this.mCallbacks.beginBroadcast();
            PackageInstallerService packageInstallerService = PackageInstallerService.this;
            Computer snapshotComputer = packageInstallerService.mPm.snapshotComputer();
            for (int i3 = 0; i3 < beginBroadcast; i3++) {
                IPackageInstallerCallback broadcastItem = this.mCallbacks.getBroadcastItem(i3);
                BroadcastCookie broadcastCookie =
                        (BroadcastCookie) this.mCallbacks.getBroadcastCookie(i3);
                if (broadcastCookie.userCheck.test(i2)) {
                    PackageInstallerSession session = packageInstallerService.getSession(i);
                    if (session != null) {
                        int installerUid = session.getInstallerUid();
                        int i4 = broadcastCookie.callingUid;
                        if (i4 != installerUid
                                && !snapshotComputer.canQueryPackage(
                                        i4, session.getPackageName())) {}
                    }
                    try {
                        invokeCallback(broadcastItem, message);
                    } catch (RemoteException unused) {
                    }
                }
            }
            this.mCallbacks.finishBroadcast();
        }
    }

    public class InternalCallback {
        public InternalCallback() {}

        public final void onSessionChanged(PackageInstallerSession packageInstallerSession) {
            synchronized (packageInstallerSession.mLock) {
                packageInstallerSession.updatedMillis = System.currentTimeMillis();
            }
            PackageInstallerService.this.mSettingsWriteRequest.schedule();
            if (PackageInstallerService.this.mOkToSendBroadcasts
                    && !packageInstallerSession.isDestroyed()
                    && packageInstallerSession.params.isStaged) {
                PackageInstallerService packageInstallerService = PackageInstallerService.this;
                PackageInstaller.SessionInfo generateInfoForCaller =
                        packageInstallerSession.generateInfoForCaller(1000, false);
                int i = packageInstallerSession.userId;
                packageInstallerService.getClass();
                if (TextUtils.isEmpty(generateInfoForCaller.installerPackageName)) {
                    return;
                }
                packageInstallerService.mContext.sendBroadcastAsUser(
                        new Intent("android.content.pm.action.SESSION_UPDATED")
                                .putExtra("android.content.pm.extra.SESSION", generateInfoForCaller)
                                .setPackage(generateInfoForCaller.installerPackageName),
                        UserHandle.of(i));
                if ("com.sec.android.app.samsungapps"
                        .equals(generateInfoForCaller.installerPackageName)) {
                    packageInstallerService.mContext.sendBroadcastAsUser(
                            new Intent("android.content.pm.action.SESSION_UPDATED")
                                    .putExtra(
                                            "android.content.pm.extra.SESSION",
                                            generateInfoForCaller)
                                    .setPackage("android"),
                            UserHandle.of(i));
                }
            }
        }
    }

    public final class Lifecycle extends SystemService {
        public final PackageInstallerService mPackageInstallerService;

        public Lifecycle(Context context, PackageInstallerService packageInstallerService) {
            super(context);
            this.mPackageInstallerService = packageInstallerService;
        }

        @Override // com.android.server.SystemService
        public final void onBootPhase(int i) {
            if (i == 550) {
                this.mPackageInstallerService.mOkToSendBroadcasts = true;
            }
        }

        @Override // com.android.server.SystemService
        public final void onStart() {}
    }

    public final class PackageDeleteObserverAdapter extends PackageDeleteObserver {
        public final Context mContext;
        public final int mFlags;
        public final Notification mNotification;
        public final PackageArchiver mPackageArchiver;
        public final String mPackageName;
        public final IntentSender mTarget;
        public final int mUserId;

        public PackageDeleteObserverAdapter(
                int i, Context context, IntentSender intentSender, String str) {
            this(context, intentSender, str, false, i, null, 0);
        }

        public PackageDeleteObserverAdapter(
                Context context,
                IntentSender intentSender,
                String str,
                boolean z,
                int i,
                PackageArchiver packageArchiver,
                int i2) {
            this.mContext = context;
            this.mTarget = intentSender;
            this.mPackageName = str;
            if (z) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    String string =
                            ((DevicePolicyManager)
                                            context.getSystemService(DevicePolicyManager.class))
                                    .getResources()
                                    .getString(
                                            "Core.PACKAGE_DELETED_BY_DO",
                                            new PackageInstallerService$$ExternalSyntheticLambda7(
                                                    1, this));
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    this.mNotification =
                            PackageInstallerService.buildSuccessNotification(
                                    i, context, string, str);
                } catch (Throwable th) {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    throw th;
                }
            } else {
                this.mNotification = null;
            }
            this.mUserId = i;
            this.mPackageArchiver = packageArchiver;
            this.mFlags = i2;
        }

        public final void onPackageDeleted(String str, int i, String str2) {
            PackageSetting packageLPr;
            if (1 == i && this.mNotification != null) {
                ((NotificationManager) this.mContext.getSystemService("notification"))
                        .notify(str, 21, this.mNotification);
            }
            PackageArchiver packageArchiver = this.mPackageArchiver;
            if (packageArchiver != null && 1 != i && (this.mFlags & 16) != 0) {
                String str3 = this.mPackageName;
                int i2 = this.mUserId;
                PackageManagerTracedLock packageManagerTracedLock = packageArchiver.mPm.mLock;
                boolean z = PackageManagerService.DEBUG_COMPRESSION;
                synchronized (packageManagerTracedLock) {
                    try {
                        packageLPr = packageArchiver.mPm.mSettings.getPackageLPr(str3);
                    } catch (Throwable th) {
                        boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                        throw th;
                    }
                }
                packageArchiver.clearArchiveState(packageLPr, i2);
            }
            if (this.mTarget == null) {
                return;
            }
            Intent intent = new Intent();
            intent.putExtra("android.content.pm.extra.PACKAGE_NAME", this.mPackageName);
            intent.putExtra(
                    "android.content.pm.extra.STATUS",
                    PackageManager.deleteStatusToPublicStatus(i));
            intent.putExtra(
                    "android.content.pm.extra.STATUS_MESSAGE",
                    PackageManager.deleteStatusToString(i, str2));
            intent.putExtra("android.content.pm.extra.LEGACY_STATUS", i);
            try {
                BroadcastOptions makeBasic = BroadcastOptions.makeBasic();
                makeBasic.setPendingIntentBackgroundActivityLaunchAllowed(false);
                this.mTarget.sendIntent(
                        this.mContext, 0, intent, null, null, null, makeBasic.toBundle());
            } catch (IntentSender.SendIntentException unused) {
            }
        }

        public final void onUserActionRequired(Intent intent) {
            if (this.mTarget == null) {
                return;
            }
            Intent intent2 = new Intent();
            intent2.putExtra("android.content.pm.extra.PACKAGE_NAME", this.mPackageName);
            intent2.putExtra("android.content.pm.extra.STATUS", -1);
            intent2.putExtra("android.intent.extra.INTENT", intent);
            try {
                BroadcastOptions makeBasic = BroadcastOptions.makeBasic();
                makeBasic.setPendingIntentBackgroundActivityLaunchAllowed(false);
                this.mTarget.sendIntent(
                        this.mContext, 0, intent2, null, null, null, makeBasic.toBundle());
            } catch (IntentSender.SendIntentException unused) {
            }
        }
    }

    public final class ParentChildSessionMap {
        public final Comparator mSessionCreationComparator;
        public final TreeMap mSessionMap;

        public ParentChildSessionMap() {
            Comparator thenComparingInt =
                    Comparator.comparingLong(
                                    new PackageInstallerService$ParentChildSessionMap$$ExternalSyntheticLambda0())
                            .thenComparingInt(
                                    new PackageInstallerService$ParentChildSessionMap$$ExternalSyntheticLambda1());
            this.mSessionCreationComparator = thenComparingInt;
            this.mSessionMap = new TreeMap(thenComparingInt);
        }

        public final void addSession(
                PackageInstallerSession packageInstallerSession,
                PackageInstallerSession packageInstallerSession2) {
            if (packageInstallerSession.hasParentSessionId()) {
                if (!this.mSessionMap.containsKey(packageInstallerSession2)) {
                    this.mSessionMap.put(
                            packageInstallerSession2, new TreeSet(this.mSessionCreationComparator));
                }
                ((TreeSet) this.mSessionMap.get(packageInstallerSession2))
                        .add(packageInstallerSession);
            } else {
                if (this.mSessionMap.containsKey(packageInstallerSession)) {
                    return;
                }
                this.mSessionMap.put(
                        packageInstallerSession, new TreeSet(this.mSessionCreationComparator));
            }
        }

        public final void dump(String str, IndentingPrintWriter indentingPrintWriter) {
            indentingPrintWriter.println(str.concat(" install sessions:"));
            indentingPrintWriter.increaseIndent();
            for (Map.Entry entry : this.mSessionMap.entrySet()) {
                PackageInstallerSession packageInstallerSession =
                        (PackageInstallerSession) entry.getKey();
                if (packageInstallerSession != null) {
                    indentingPrintWriter.print(str.concat(" "));
                    synchronized (packageInstallerSession.mLock) {
                        packageInstallerSession.dumpLocked(indentingPrintWriter);
                    }
                    indentingPrintWriter.println();
                    indentingPrintWriter.increaseIndent();
                }
                Iterator it = ((TreeSet) entry.getValue()).iterator();
                while (it.hasNext()) {
                    PackageInstallerSession packageInstallerSession2 =
                            (PackageInstallerSession) it.next();
                    indentingPrintWriter.print(str.concat(" Child "));
                    synchronized (packageInstallerSession2.mLock) {
                        packageInstallerSession2.dumpLocked(indentingPrintWriter);
                    }
                    indentingPrintWriter.println();
                }
                indentingPrintWriter.decreaseIndent();
            }
            indentingPrintWriter.println();
            indentingPrintWriter.decreaseIndent();
        }
    }

    public PackageInstallerService(
            Context context,
            PackageManagerService packageManagerService,
            PackageManagerService$$ExternalSyntheticLambda55
                    packageManagerService$$ExternalSyntheticLambda55) {
        this.mContext = context;
        this.mPm = packageManagerService;
        HandlerThread handlerThread = new HandlerThread("PackageInstaller");
        this.mInstallThread = handlerThread;
        handlerThread.start();
        this.mInstallHandler = new Handler(handlerThread.getLooper());
        this.mCallbacks = new Callbacks(handlerThread.getLooper());
        this.mSessionsFile =
                new AtomicFile(
                        new File(Environment.getDataSystemDirectory(), "install_sessions.xml"),
                        "package-session");
        File file = new File(Environment.getDataSystemDirectory(), "install_sessions");
        this.mSessionsDir = file;
        file.mkdirs();
        ApexManager apexManager = ApexManager.getInstance();
        this.mApexManager = apexManager;
        this.mStagingManager = new StagingManager(context, ApexManager.getInstance());
        this.mPmInternal =
                (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
        this.mSessionVerifier =
                new PackageSessionVerifier(
                        context,
                        packageManagerService,
                        apexManager,
                        packageManagerService$$ExternalSyntheticLambda55,
                        handlerThread.getLooper());
        this.mGentleUpdateHelper =
                new GentleUpdateHelper(
                        context, handlerThread.getLooper(), new AppStateHelper(context));
        this.mPackageArchiver = new PackageArchiver(context, packageManagerService);
        ((SystemServiceManager) LocalServices.getService(SystemServiceManager.class))
                .startService(new Lifecycle(context, this));
    }

    public static Notification buildSuccessNotification(
            int i, Context context, String str, String str2) {
        PackageInfo packageInfo;
        try {
            packageInfo = AppGlobals.getPackageManager().getPackageInfo(str2, 67108864L, i);
        } catch (RemoteException unused) {
            packageInfo = null;
        }
        if (packageInfo == null || packageInfo.applicationInfo == null) {
            HeimdAllFsService$$ExternalSyntheticOutline0.m(
                    "Notification not built for package: ", str2, "PackageInstaller");
            return null;
        }
        PackageManager packageManager = context.getPackageManager();
        return new Notification.Builder(context, SystemNotificationChannels.DEVICE_ADMIN)
                .setSmallIcon(R.drawable.ic_corp_badge_color)
                .setColor(context.getResources().getColor(R.color.system_notification_accent_color))
                .setContentTitle(packageInfo.applicationInfo.loadLabel(packageManager))
                .setContentText(str)
                .setStyle(new Notification.BigTextStyle().bigText(str))
                .setLargeIcon(
                        ImageUtils.buildScaledBitmap(
                                packageInfo.applicationInfo.loadIcon(packageManager),
                                context.getResources()
                                        .getDimensionPixelSize(
                                                R.dimen.notification_large_icon_width),
                                context.getResources()
                                        .getDimensionPixelSize(
                                                R.dimen.notification_large_icon_height)))
                .build();
    }

    public static boolean isCallingUidOwner(PackageInstallerSession packageInstallerSession) {
        int callingUid = Binder.getCallingUid();
        if (callingUid == 0) {
            return true;
        }
        return packageInstallerSession != null
                && callingUid == packageInstallerSession.getInstallerUid();
    }

    public static boolean isStageName(String str) {
        return (str.startsWith("vmdl") && str.endsWith(".tmp"))
                || (str.startsWith("smdl") && str.endsWith(".tmp"))
                || str.startsWith("smdl2tmp");
    }

    public static boolean isValidDraftSession(
            PackageInstallerSession packageInstallerSession, String str, int i, int i2) {
        PackageInstaller.SessionParams sessionParams = packageInstallerSession.params;
        return (sessionParams.installFlags & 536870912) != 0
                && str.equals(sessionParams.appPackageName)
                && packageInstallerSession.userId == i2
                && i == packageInstallerSession.getInstallerUid();
    }

    public static File prepareCustomCopyDir() {
        File file;
        try {
            File file2 = new File(Environment.getDataDirectory(), "apk-tmp");
            if (!file2.exists()) {
                Os.mkdir(file2.getAbsolutePath(), 511);
                Os.chmod(file2.getAbsolutePath(), 511);
            }
            File file3 = new File(file2, Integer.toString(Binder.getCallingUid()));
            if (!file3.exists()) {
                Os.mkdir(file3.getAbsolutePath(), 511);
                Os.chmod(file3.getAbsolutePath(), 511);
            }
            SecureRandom secureRandom = new SecureRandom();
            byte[] bArr = new byte[16];
            do {
                secureRandom.nextBytes(bArr);
                file = new File(file3, Base64.encodeToString(bArr, 10));
            } while (file.exists());
            Os.mkdir(file.getAbsolutePath(), 511);
            Os.chmod(file.getAbsolutePath(), 511);
            return file;
        } catch (ErrnoException e) {
            e.printStackTrace();
            throw new IOException("Failed to prepare temp session dir", e);
        }
    }

    public static void prepareStageDir(File file) {
        if (file.exists()) {
            throw new IOException(
                    AccountManagerService$$ExternalSyntheticOutline0.m(
                            file, "Session dir already exists: "));
        }
        try {
            Os.mkdir(file.getAbsolutePath(), 509);
            Os.chmod(file.getAbsolutePath(), 509);
            if (SELinux.restorecon(file)) {
                return;
            }
            String canonicalPath = file.getCanonicalPath();
            String fileSelabelLookup = SELinux.fileSelabelLookup(canonicalPath);
            boolean fileContext = SELinux.setFileContext(canonicalPath, fileSelabelLookup);
            BinaryTransparencyService$$ExternalSyntheticOutline0.m$1(
                    InitialConfiguration$$ExternalSyntheticOutline0.m(
                            "Failed to SELinux.restorecon session dir, path: [",
                            canonicalPath,
                            "], ctx: [",
                            fileSelabelLookup,
                            "]. Retrying via SELinux.fileSelabelLookup/SELinux.setFileContext: "),
                    fileContext ? "SUCCESS" : "FAILURE",
                    "PackageInstaller");
            if (!fileContext) {
                throw new IOException(
                        AccountManagerService$$ExternalSyntheticOutline0.m(
                                file, "Failed to restorecon session dir: "));
            }
        } catch (ErrnoException e) {
            throw new IOException(
                    AccountManagerService$$ExternalSyntheticOutline0.m(
                            file, "Failed to prepare session dir: "),
                    e);
        }
    }

    public static void prepareStageDirQuick(File file, String str) {
        if (file.exists()) {
            throw new IOException(
                    AccountManagerService$$ExternalSyntheticOutline0.m(
                            file, "Session dir already exists: "));
        }
        try {
            File file2 = new File(str);
            Os.chmod(file2.getAbsolutePath(), 509);
            for (File file3 : file2.listFiles()) {
                Os.chmod(file3.getAbsolutePath(), FrameworkStatsLog.VBMETA_DIGEST_REPORTED);
            }
            Os.rename(file2.getAbsolutePath(), file.getAbsolutePath());
            if (SELinux.restoreconRecursive(file)) {
                return;
            }
            setSelinuxContext(file.getCanonicalPath());
            for (File file4 : file.listFiles()) {
                setSelinuxContext(file4.getAbsolutePath());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException("Failed to rename temp dir", e);
        }
    }

    public static void setSelinuxContext(String str) {
        String fileSelabelLookup = SELinux.fileSelabelLookup(str);
        boolean fileContext = SELinux.setFileContext(str, fileSelabelLookup);
        BinaryTransparencyService$$ExternalSyntheticOutline0.m$1(
                InitialConfiguration$$ExternalSyntheticOutline0.m(
                        "Failed to SELinux.restorecon session dir, path: [",
                        str,
                        "], ctx: [",
                        fileSelabelLookup,
                        "]. Retrying via SELinux.fileSelabelLookup/SELinux.setFileContext: "),
                fileContext ? "SUCCESS" : "FAILURE",
                "PackageInstaller");
        if (!fileContext) {
            throw new IOException(
                    ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(
                            "Failed to restorecon session dir: ", str));
        }
    }

    public static boolean shouldFilterSession(
            Computer computer, int i, PackageInstaller.SessionInfo sessionInfo) {
        return (sessionInfo == null
                        || i == sessionInfo.getInstallerUid()
                        || computer.canQueryPackage(i, sessionInfo.getAppPackageName()))
                ? false
                : true;
    }

    public final void abandonSession(int i) {
        synchronized (this.mSessions) {
            try {
                PackageInstallerSession packageInstallerSession =
                        (PackageInstallerSession) this.mSessions.get(i);
                if (packageInstallerSession == null
                        || !isCallingUidOwner(packageInstallerSession)) {
                    throw new SecurityException("Caller has no access to session " + i);
                }
                packageInstallerSession.abandon();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void addHistoricalSessionLocked(PackageInstallerSession packageInstallerSession) {
        float f;
        float f2;
        if (((ArrayList) this.mHistoricalSessions).size() > 500) {
            Slog.d(
                    "PackageInstaller",
                    "Historical sessions size reaches threshold, clear the oldest");
            ((ArrayList) this.mHistoricalSessions).subList(0, 400).clear();
        }
        List list = this.mHistoricalSessions;
        synchronized (packageInstallerSession.mProgressLock) {
            f = packageInstallerSession.mProgress;
            f2 = packageInstallerSession.mClientProgress;
        }
        synchronized (packageInstallerSession.mLock) {
            try {
                try {
                    ((ArrayList) list)
                            .add(
                                    new PackageInstallerHistoricalSession(
                                            packageInstallerSession.sessionId,
                                            packageInstallerSession.userId,
                                            packageInstallerSession.mOriginalInstallerUid,
                                            packageInstallerSession.mOriginalInstallerPackageName,
                                            packageInstallerSession.mInstallSource,
                                            packageInstallerSession.mInstallerUid,
                                            packageInstallerSession.createdMillis,
                                            packageInstallerSession.updatedMillis,
                                            packageInstallerSession.committedMillis,
                                            packageInstallerSession.stageDir,
                                            packageInstallerSession.stageCid,
                                            f2,
                                            f,
                                            packageInstallerSession.mCommitted.get(),
                                            packageInstallerSession.mPreapprovalRequested.get(),
                                            packageInstallerSession.mSealed,
                                            packageInstallerSession.mPermissionsManuallyAccepted,
                                            packageInstallerSession.mStageDirInUse,
                                            packageInstallerSession.mDestroyed,
                                            packageInstallerSession.mFds.size(),
                                            packageInstallerSession.mBridges.size(),
                                            packageInstallerSession.mFinalStatus,
                                            packageInstallerSession.mFinalMessage,
                                            packageInstallerSession.params,
                                            packageInstallerSession.mParentSessionId,
                                            packageInstallerSession.getChildSessionIdsLocked(),
                                            packageInstallerSession.mSessionApplied,
                                            packageInstallerSession.mSessionFailed,
                                            packageInstallerSession.mSessionReady,
                                            packageInstallerSession.mSessionErrorCode,
                                            packageInstallerSession.mSessionErrorMessage,
                                            packageInstallerSession.mPreapprovalDetails,
                                            packageInstallerSession.mPreVerifiedDomains,
                                            packageInstallerSession.mPackageName));
                    int installerUid = packageInstallerSession.getInstallerUid();
                    SparseIntArray sparseIntArray = this.mHistoricalSessionsByInstaller;
                    sparseIntArray.put(installerUid, sparseIntArray.get(installerUid) + 1);
                } catch (Throwable th) {
                    th = th;
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                throw th;
            }
        }
    }

    public final int allocateSessionIdLocked() {
        int i = 0;
        while (true) {
            int nextInt = this.mRandom.nextInt(2147483646) + 1;
            if (!this.mAllocatedSessions.get(nextInt, false)) {
                this.mAllocatedSessions.put(nextInt, true);
                return nextInt;
            }
            int i2 = i + 1;
            if (i >= 32) {
                throw new IllegalStateException("Failed to allocate session ID");
            }
            i = i2;
        }
    }

    public final File allocateStageDirLegacy(String str) {
        File file;
        synchronized (this.mSessions) {
            try {
                try {
                    int allocateSessionIdLocked = allocateSessionIdLocked();
                    this.mLegacySessions.put(allocateSessionIdLocked, true);
                    file =
                            new File(
                                    Environment.getDataAppDirectory(str),
                                    "vmdl" + allocateSessionIdLocked + ".tmp");
                    prepareStageDir(file);
                } catch (IllegalStateException e) {
                    throw new IOException(e);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return file;
    }

    public final void bypassNextAllowedApexUpdateCheck(boolean z) {
        if (!PackageManagerServiceUtils.isSystemOrRootOrShell(Binder.getCallingUid())) {
            throw new SecurityException("Caller not allowed to bypass allowed apex update check");
        }
        this.mBypassNextAllowedApexUpdateCheck = z;
    }

    public final void bypassNextStagedInstallerCheck(boolean z) {
        if (!PackageManagerServiceUtils.isSystemOrRootOrShell(Binder.getCallingUid())) {
            throw new SecurityException("Caller not allowed to bypass staged installer check");
        }
        this.mBypassNextStagedInstallerCheck = z;
    }

    public final void checkInstallConstraints(
            String str,
            List list,
            PackageInstaller.InstallConstraints installConstraints,
            final RemoteCallback remoteCallback) {
        Objects.requireNonNull(remoteCallback);
        checkInstallConstraintsInternal(str, list, installConstraints, 0L)
                .thenAccept(
                        new Consumer() { // from class:
                                         // com.android.server.pm.PackageInstallerService$$ExternalSyntheticLambda4
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                RemoteCallback remoteCallback2 = remoteCallback;
                                Bundle bundle = new Bundle();
                                bundle.putParcelable(
                                        KnoxCustomManagerService.SPCM_KEY_RESULT,
                                        (PackageInstaller.InstallConstraintsResult) obj);
                                remoteCallback2.sendResult(bundle);
                            }
                        });
    }

    public final CompletableFuture checkInstallConstraintsInternal(
            String str,
            final List list,
            final PackageInstaller.InstallConstraints installConstraints,
            final long j) {
        Objects.requireNonNull(list);
        Objects.requireNonNull(installConstraints);
        Computer snapshotComputer = this.mPm.snapshotComputer();
        int callingUid = Binder.getCallingUid();
        if (!TextUtils.equals(snapshotComputer.getNameForUid(callingUid), str)) {
            throw new SecurityException(
                    "The installerPackageName set by the caller doesn't match the caller's own"
                        + " package name.");
        }
        if (!PackageManagerServiceUtils.isSystemOrRootOrShell(callingUid)) {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                String str2 = (String) it.next();
                PackageSetting packageStateInternal =
                        snapshotComputer.getPackageStateInternal(str2);
                if (packageStateInternal != null) {
                    boolean z =
                            this.mPm
                                                    .snapshotComputer()
                                                    .checkUidPermission(
                                                            "android.permission.INSTALL_SELF_UPDATES",
                                                            callingUid)
                                            == 0
                                    && TextUtils.equals(str2, str);
                    if (TextUtils.equals(
                                    packageStateInternal.installSource.mInstallerPackageName, str)
                            || TextUtils.equals(
                                    packageStateInternal.installSource.mUpdateOwnerPackageName, str)
                            || z) {}
                }
                throw new SecurityException(
                        ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(
                                "Caller has no access to package ", str2));
            }
        }
        final GentleUpdateHelper gentleUpdateHelper = this.mGentleUpdateHelper;
        gentleUpdateHelper.getClass();
        final CompletableFuture completableFuture = new CompletableFuture();
        gentleUpdateHelper.mHandler.post(
                new Runnable() { // from class:
                                 // com.android.server.pm.GentleUpdateHelper$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        CompletableFuture completedFuture;
                        final GentleUpdateHelper gentleUpdateHelper2 = GentleUpdateHelper.this;
                        List list2 = list;
                        PackageInstaller.InstallConstraints installConstraints2 =
                                installConstraints;
                        CompletableFuture completableFuture2 = completableFuture;
                        long j2 = j;
                        gentleUpdateHelper2.getClass();
                        final GentleUpdateHelper.PendingInstallConstraintsCheck
                                pendingInstallConstraintsCheck =
                                        new GentleUpdateHelper.PendingInstallConstraintsCheck(
                                                list2, installConstraints2, completableFuture2, j2);
                        if (installConstraints2.isDeviceIdleRequired()) {
                            completedFuture = new CompletableFuture();
                            gentleUpdateHelper2.mPendingIdleFutures.add(completedFuture);
                            gentleUpdateHelper2.scheduleIdleJob();
                            gentleUpdateHelper2.mHandler.postDelayed(
                                    new GentleUpdateHelper$$ExternalSyntheticLambda0(
                                            2, completedFuture),
                                    GentleUpdateHelper.PENDING_CHECK_MILLIS);
                        } else {
                            completedFuture = CompletableFuture.completedFuture(Boolean.FALSE);
                        }
                        completedFuture.thenAccept(
                                new Consumer() { // from class:
                                                 // com.android.server.pm.GentleUpdateHelper$$ExternalSyntheticLambda4
                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj) {
                                        GentleUpdateHelper gentleUpdateHelper3 =
                                                GentleUpdateHelper.this;
                                        GentleUpdateHelper.PendingInstallConstraintsCheck
                                                pendingInstallConstraintsCheck2 =
                                                        pendingInstallConstraintsCheck;
                                        Handler handler = gentleUpdateHelper3.mHandler;
                                        Preconditions.checkState(
                                                handler.getLooper().isCurrentThread());
                                        if (gentleUpdateHelper3.processPendingCheck(
                                                pendingInstallConstraintsCheck2,
                                                ((Boolean) obj).booleanValue())) {
                                            return;
                                        }
                                        gentleUpdateHelper3.mPendingChecks.add(
                                                pendingInstallConstraintsCheck2);
                                        gentleUpdateHelper3.scheduleIdleJob();
                                        handler.postDelayed(
                                                new GentleUpdateHelper$$ExternalSyntheticLambda3(
                                                        gentleUpdateHelper3,
                                                        pendingInstallConstraintsCheck2),
                                                Math.max(
                                                        pendingInstallConstraintsCheck2.mFinishTime
                                                                - SystemClock.elapsedRealtime(),
                                                        0L));
                                    }
                                });
                    }
                });
        return completableFuture;
    }

    public final boolean checkOpenSessionAccess(PackageInstallerSession packageInstallerSession) {
        if (packageInstallerSession != null
                && (packageInstallerSession.params.installFlags & 536870912) == 0) {
            if (isCallingUidOwner(packageInstallerSession)) {
                return true;
            }
            if (packageInstallerSession.isSealed()
                    && this.mContext.checkCallingOrSelfPermission(
                                    "android.permission.PACKAGE_VERIFICATION_AGENT")
                            == 0) {
                return true;
            }
        }
        return false;
    }

    public final int createSession(
            PackageInstaller.SessionParams sessionParams, String str, String str2, int i) {
        try {
            if (sessionParams.dataLoaderParams != null
                    && this.mContext.checkCallingOrSelfPermission(
                                    "com.android.permission.USE_INSTALLER_V2")
                            != 0) {
                throw new SecurityException(
                        "You need the com.android.permission.USE_INSTALLER_V2 permission to use a"
                            + " data loader");
            }
            sessionParams.installFlags &= -536870913;
            return createSessionInternal(sessionParams, str, str2, Binder.getCallingUid(), i);
        } catch (IOException e) {
            throw ExceptionUtils.wrap(e);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:112:0x027a  */
    /* JADX WARN: Removed duplicated region for block: B:118:0x02a1  */
    /* JADX WARN: Removed duplicated region for block: B:128:0x02dd  */
    /* JADX WARN: Removed duplicated region for block: B:145:0x0318  */
    /* JADX WARN: Removed duplicated region for block: B:147:0x031d  */
    /* JADX WARN: Removed duplicated region for block: B:154:0x034a  */
    /* JADX WARN: Removed duplicated region for block: B:166:0x0381  */
    /* JADX WARN: Removed duplicated region for block: B:254:0x04d3  */
    /* JADX WARN: Removed duplicated region for block: B:320:0x05c3  */
    /* JADX WARN: Removed duplicated region for block: B:323:0x05cf  */
    /* JADX WARN: Removed duplicated region for block: B:326:0x05d7 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:394:0x07a4 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:435:0x05d2  */
    /* JADX WARN: Removed duplicated region for block: B:436:0x05cb  */
    /* JADX WARN: Removed duplicated region for block: B:437:0x033b  */
    /* JADX WARN: Removed duplicated region for block: B:440:0x031a  */
    /* JADX WARN: Removed duplicated region for block: B:446:0x02d6  */
    /* JADX WARN: Removed duplicated region for block: B:448:0x028e  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x0197  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x01b4  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int createSessionInternal(
            android.content.pm.PackageInstaller.SessionParams r40,
            java.lang.String r41,
            java.lang.String r42,
            int r43,
            int r44) {
        /*
            Method dump skipped, instructions count: 2106
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.pm.PackageInstallerService.createSessionInternal(android.content.pm.PackageInstaller$SessionParams,"
                    + " java.lang.String, java.lang.String, int, int):int");
    }

    public final void disableVerificationForUid(int i) {
        if (!PackageManagerServiceUtils.isSystemOrRootOrShell(Binder.getCallingUid())) {
            throw new SecurityException("Operation not allowed for caller");
        }
        this.mDisableVerificationForUid = i;
    }

    public final void dump(IndentingPrintWriter indentingPrintWriter) {
        int i;
        synchronized (this.mSessions) {
            try {
                ParentChildSessionMap parentChildSessionMap = new ParentChildSessionMap();
                ParentChildSessionMap parentChildSessionMap2 = new ParentChildSessionMap();
                ParentChildSessionMap parentChildSessionMap3 = new ParentChildSessionMap();
                int size = this.mSessions.size();
                for (int i2 = 0; i2 < size; i2++) {
                    PackageInstallerSession packageInstallerSession =
                            (PackageInstallerSession) this.mSessions.valueAt(i2);
                    PackageInstallerSession session =
                            packageInstallerSession.hasParentSessionId()
                                    ? getSession(packageInstallerSession.getParentSessionId())
                                    : packageInstallerSession;
                    if (session == null) {
                        parentChildSessionMap2.addSession(packageInstallerSession, session);
                    } else if (session.isStagedAndInTerminalState()) {
                        parentChildSessionMap3.addSession(packageInstallerSession, session);
                    } else {
                        parentChildSessionMap.addSession(packageInstallerSession, session);
                    }
                }
                parentChildSessionMap.dump("Active", indentingPrintWriter);
                if (!parentChildSessionMap2.mSessionMap.isEmpty()) {
                    parentChildSessionMap2.dump("Orphaned", indentingPrintWriter);
                }
                parentChildSessionMap3.dump("Finalized", indentingPrintWriter);
                indentingPrintWriter.println("Historical install sessions:");
                indentingPrintWriter.increaseIndent();
                int size2 = ((ArrayList) this.mHistoricalSessions).size();
                for (int i3 = 0; i3 < size2; i3++) {
                    ((PackageInstallerHistoricalSession)
                                    ((ArrayList) this.mHistoricalSessions).get(i3))
                            .dump(indentingPrintWriter);
                    indentingPrintWriter.println();
                }
                indentingPrintWriter.println();
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.println("Legacy install sessions:");
                indentingPrintWriter.increaseIndent();
                indentingPrintWriter.println(this.mLegacySessions.toString());
                indentingPrintWriter.println();
                indentingPrintWriter.decreaseIndent();
            } finally {
            }
        }
        SilentUpdatePolicy silentUpdatePolicy = this.mSilentUpdatePolicy;
        synchronized (silentUpdatePolicy.mSilentUpdateInfos) {
            try {
                if (!silentUpdatePolicy.mSilentUpdateInfos.isEmpty()) {
                    indentingPrintWriter.println("Last silent updated Infos:");
                    indentingPrintWriter.increaseIndent();
                    int size3 = silentUpdatePolicy.mSilentUpdateInfos.size();
                    for (int i4 = 0; i4 < size3; i4++) {
                        Pair pair = (Pair) silentUpdatePolicy.mSilentUpdateInfos.keyAt(i4);
                        if (pair != null) {
                            indentingPrintWriter.printPair("installerPackageName", pair.first);
                            indentingPrintWriter.printPair("packageName", pair.second);
                            indentingPrintWriter.printPair(
                                    "silentUpdatedMillis",
                                    silentUpdatePolicy.mSilentUpdateInfos.valueAt(i4));
                            indentingPrintWriter.println();
                        }
                    }
                    indentingPrintWriter.decreaseIndent();
                }
            } finally {
            }
        }
        GentleUpdateHelper gentleUpdateHelper = this.mGentleUpdateHelper;
        gentleUpdateHelper.getClass();
        indentingPrintWriter.println("Gentle update with constraints info:");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.printPair(
                "hasPendingIdleJob", Boolean.valueOf(gentleUpdateHelper.mHasPendingIdleJob));
        indentingPrintWriter.println();
        indentingPrintWriter.printPair(
                "Num of PendingIdleFutures",
                Integer.valueOf(gentleUpdateHelper.mPendingIdleFutures.size()));
        indentingPrintWriter.println();
        ArrayDeque clone = gentleUpdateHelper.mPendingChecks.clone();
        int size4 = clone.size();
        indentingPrintWriter.printPair("Num of PendingChecks", Integer.valueOf(size4));
        indentingPrintWriter.println();
        indentingPrintWriter.increaseIndent();
        for (i = 0; i < size4; i++) {
            indentingPrintWriter.print(i);
            indentingPrintWriter.print(":");
            GentleUpdateHelper.PendingInstallConstraintsCheck pendingInstallConstraintsCheck =
                    (GentleUpdateHelper.PendingInstallConstraintsCheck) clone.remove();
            indentingPrintWriter.printPair(
                    "packageNames", pendingInstallConstraintsCheck.packageNames);
            indentingPrintWriter.println();
            indentingPrintWriter.printPair(
                    "finishTime", Long.valueOf(pendingInstallConstraintsCheck.mFinishTime));
            indentingPrintWriter.println();
            indentingPrintWriter.printPair(
                    "constraints notInCallRequired",
                    Boolean.valueOf(
                            pendingInstallConstraintsCheck.constraints.isNotInCallRequired()));
            indentingPrintWriter.println();
            indentingPrintWriter.printPair(
                    "constraints deviceIdleRequired",
                    Boolean.valueOf(
                            pendingInstallConstraintsCheck.constraints.isDeviceIdleRequired()));
            indentingPrintWriter.println();
            indentingPrintWriter.printPair(
                    "constraints appNotForegroundRequired",
                    Boolean.valueOf(
                            pendingInstallConstraintsCheck.constraints
                                    .isAppNotForegroundRequired()));
            indentingPrintWriter.println();
            indentingPrintWriter.printPair(
                    "constraints appNotInteractingRequired",
                    Boolean.valueOf(
                            pendingInstallConstraintsCheck.constraints
                                    .isAppNotInteractingRequired()));
            indentingPrintWriter.println();
            indentingPrintWriter.printPair(
                    "constraints appNotTopVisibleRequired",
                    Boolean.valueOf(
                            pendingInstallConstraintsCheck.constraints
                                    .isAppNotTopVisibleRequired()));
            indentingPrintWriter.println();
        }
    }

    public final void expireSessionsLocked() {
        long j;
        SparseArray clone = this.mSessions.clone();
        this.mSmartSwitchUid = -1;
        int size = clone.size();
        for (int i = 0; i < size; i++) {
            PackageInstallerSession packageInstallerSession =
                    (PackageInstallerSession) clone.valueAt(i);
            if (!packageInstallerSession.hasParentSessionId()) {
                long currentTimeMillis =
                        System.currentTimeMillis() - packageInstallerSession.createdMillis;
                long currentTimeMillis2 = System.currentTimeMillis();
                synchronized (packageInstallerSession.mLock) {
                    j = packageInstallerSession.updatedMillis;
                }
                long j2 = currentTimeMillis2 - j;
                if (!packageInstallerSession.params.isStaged) {
                    if (currentTimeMillis >= 259200000) {
                        Slog.w(
                                "PackageInstaller",
                                "Abandoning old session created at "
                                        + packageInstallerSession.createdMillis);
                    } else if (packageInstallerSession.getInstallSource() != null
                            && "com.sec.android.easyMover"
                                    .equals(
                                            packageInstallerSession.getInstallSource()
                                                    .mInitiatingPackageName)) {
                        int installerUid = packageInstallerSession.getInstallerUid();
                        if (this.mSmartSwitchUid <= -1) {
                            this.mSmartSwitchUid = 0;
                            try {
                                PackageManager packageManager = this.mContext.getPackageManager();
                                ApplicationInfo applicationInfo =
                                        packageManager == null
                                                ? null
                                                : packageManager.getApplicationInfo(
                                                        "com.sec.android.easyMover", 128);
                                int i2 = applicationInfo == null ? 0 : applicationInfo.uid;
                                this.mSmartSwitchUid = i2;
                                if (i2 > 0) {
                                    PackageInfo packageInfo =
                                            packageManager.getPackageInfo(
                                                    "com.sec.android.easyMover", 0);
                                    if (packageInfo != null) {
                                        if (packageInfo.versionCode < 300000000) {}
                                    }
                                    this.mSmartSwitchUid = 0;
                                }
                            } catch (PackageManager.NameNotFoundException e) {
                                Slog.w(
                                        "PackageInstaller",
                                        "isValidSmartSwitchSession " + e.getStackTrace());
                            }
                        }
                        int i3 = this.mSmartSwitchUid;
                        if (i3 > 0 && i3 == installerUid && currentTimeMillis < 21600000) {}
                    }
                    Slog.w(
                            "PackageInstaller",
                            "Remove old session: " + packageInstallerSession.sessionId);
                    removeActiveSession(packageInstallerSession);
                } else if (packageInstallerSession.isStagedAndInTerminalState()) {
                    if (j2 < 1814400000) {}
                    Slog.w(
                            "PackageInstaller",
                            "Remove old session: " + packageInstallerSession.sessionId);
                    removeActiveSession(packageInstallerSession);
                }
            }
        }
    }

    public final ParceledListSlice getAllSessions(int i) {
        int callingUid = Binder.getCallingUid();
        Computer snapshotComputer = this.mPm.snapshotComputer();
        snapshotComputer.enforceCrossUserPermission("getAllSessions", callingUid, i, true, false);
        ArrayList arrayList = new ArrayList();
        synchronized (this.mSessions) {
            for (int i2 = 0; i2 < this.mSessions.size(); i2++) {
                try {
                    PackageInstallerSession packageInstallerSession =
                            (PackageInstallerSession) this.mSessions.valueAt(i2);
                    if (packageInstallerSession.userId == i) {
                        if (!packageInstallerSession.hasParentSessionId()) {
                            if (PMRune.PM_INSTALL_TO_SDCARD
                                    && packageInstallerSession.stageCid != null) {}
                            if (!packageInstallerSession.params.isStaged
                                    || !packageInstallerSession.isDestroyed()) {
                                arrayList.add(
                                        packageInstallerSession.generateInfoForCaller(
                                                callingUid, false));
                            }
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
        arrayList.removeIf(
                new PackageInstallerService$$ExternalSyntheticLambda0(
                        this, snapshotComputer, callingUid, 1));
        return new ParceledListSlice(arrayList);
    }

    public final int getExistingDraftSessionIdInternal(
            int i, PackageInstaller.SessionParams sessionParams, int i2) {
        PackageSetting packageStateInternal;
        String str = sessionParams.appPackageName;
        PorterDuffColorFilter porterDuffColorFilter = PackageArchiver.OPACITY_LAYER_FILTER;
        if (Flags.archiving()
                && i != -1
                && str != null
                && (packageStateInternal =
                                this.mPm.snapshotComputer().getPackageStateInternal(1000, str))
                        != null
                && PackageArchiver.isArchived(packageStateInternal.getUserStateOrDefault(i2))) {
            int i3 = sessionParams.unarchiveId;
            if (i3 > 0) {
                PackageInstallerSession packageInstallerSession =
                        (PackageInstallerSession) this.mSessions.get(i3);
                if (packageInstallerSession == null
                        || !isValidDraftSession(packageInstallerSession, str, i, i2)) {
                    return -1;
                }
                return packageInstallerSession.sessionId;
            }
            for (int i4 = 0; i4 < this.mSessions.size(); i4++) {
                PackageInstallerSession packageInstallerSession2 =
                        (PackageInstallerSession) this.mSessions.valueAt(i4);
                if (packageInstallerSession2 != null
                        && isValidDraftSession(packageInstallerSession2, str, i, i2)) {
                    return packageInstallerSession2.sessionId;
                }
            }
        }
        return -1;
    }

    public final ParceledListSlice getMySessions(String str, int i) {
        Computer snapshotComputer = this.mPm.snapshotComputer();
        int callingUid = Binder.getCallingUid();
        snapshotComputer.enforceCrossUserPermission("getMySessions", callingUid, i, true, false);
        this.mAppOps.checkPackage(callingUid, str);
        ArrayList arrayList = new ArrayList();
        synchronized (this.mSessions) {
            for (int i2 = 0; i2 < this.mSessions.size(); i2++) {
                try {
                    PackageInstallerSession packageInstallerSession =
                            (PackageInstallerSession) this.mSessions.valueAt(i2);
                    PackageInstaller.SessionInfo generateInfoForCaller =
                            packageInstallerSession.generateInfoForCaller(1000, false);
                    if (Objects.equals(generateInfoForCaller.getInstallerPackageName(), str)
                            && packageInstallerSession.userId == i
                            && !packageInstallerSession.hasParentSessionId()
                            && isCallingUidOwner(packageInstallerSession)
                            && (packageInstallerSession.params.installFlags & 536870912) == 0) {
                        arrayList.add(generateInfoForCaller);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
        return new ParceledListSlice(arrayList);
    }

    public final PackageInstallerSession getSession(int i) {
        PackageInstallerSession packageInstallerSession;
        synchronized (this.mSessions) {
            packageInstallerSession = (PackageInstallerSession) this.mSessions.get(i);
        }
        return packageInstallerSession;
    }

    public final PackageInstaller.SessionInfo getSessionInfo(int i) {
        PackageInstaller.SessionInfo sessionInfo;
        int callingUid = Binder.getCallingUid();
        synchronized (this.mSessions) {
            try {
                PackageInstallerSession packageInstallerSession =
                        (PackageInstallerSession) this.mSessions.get(i);
                if (packageInstallerSession == null
                        || (packageInstallerSession.params.isStaged
                                && packageInstallerSession.isDestroyed())) {
                    sessionInfo = null;
                }
                sessionInfo = packageInstallerSession.generateInfoForCaller(callingUid, true);
            } catch (Throwable th) {
                throw th;
            }
        }
        if (shouldFilterSession(this.mPm.snapshotComputer(), callingUid, sessionInfo)) {
            return null;
        }
        return sessionInfo;
    }

    public final ParceledListSlice getStagedSessions() {
        int callingUid = Binder.getCallingUid();
        ArrayList arrayList = new ArrayList();
        synchronized (this.mSessions) {
            for (int i = 0; i < this.mSessions.size(); i++) {
                try {
                    PackageInstallerSession packageInstallerSession =
                            (PackageInstallerSession) this.mSessions.valueAt(i);
                    if (packageInstallerSession.params.isStaged
                            && !packageInstallerSession.isDestroyed()) {
                        arrayList.add(
                                packageInstallerSession.generateInfoForCaller(callingUid, false));
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
        arrayList.removeIf(
                new PackageInstallerService$$ExternalSyntheticLambda0(
                        this, this.mPm.snapshotComputer(), callingUid, 2));
        return new ParceledListSlice(arrayList);
    }

    public final void installExistingPackage(
            String str, int i, int i2, IntentSender intentSender, int i3, List list) {
        Pair installExistingPackageAsUser =
                this.mPm.mInstallPackageHelper.installExistingPackageAsUser(
                        str, i3, i, i2, intentSender);
        int intValue = ((Integer) installExistingPackageAsUser.first).intValue();
        IntentSender intentSender2 = (IntentSender) installExistingPackageAsUser.second;
        if (intentSender2 != null) {
            InstallPackageHelper.onInstallComplete(intValue, this.mContext, intentSender2);
        }
    }

    public final void installPackageArchived(
            final ArchivedPackageParcel archivedPackageParcel,
            final PackageInstaller.SessionParams sessionParams,
            final IntentSender intentSender,
            final String str,
            UserHandle userHandle) {
        Objects.requireNonNull(sessionParams);
        Objects.requireNonNull(archivedPackageParcel);
        Objects.requireNonNull(intentSender);
        Objects.requireNonNull(str);
        Objects.requireNonNull(userHandle);
        Slog.i(
                "PackageInstaller",
                TextUtils.formatSimple(
                        "Requested archived install of package %s for user %s.",
                        new Object[] {
                            archivedPackageParcel.packageName,
                            Integer.valueOf(userHandle.getIdentifier())
                        }));
        int callingUid = Binder.getCallingUid();
        final int identifier = userHandle.getIdentifier();
        this.mPm
                .snapshotComputer()
                .enforceCrossUserPermission(
                        "installPackageArchived", callingUid, identifier, true, true);
        if (this.mContext.checkCallingOrSelfPermission("android.permission.INSTALL_PACKAGES")
                != 0) {
            throw new SecurityException(
                    "You need the com.android.permission.INSTALL_PACKAGES permission to request"
                        + " archived package install");
        }
        sessionParams.installFlags |= 134217728;
        if (sessionParams.dataLoaderParams != null) {
            throw new IllegalArgumentException(
                    "Incompatible session param: dataLoaderParams has to be null");
        }
        sessionParams.setDataLoaderParams(
                PackageManagerShellCommandDataLoader.getStreamingDataLoaderParams(null));
        final PackageManagerShellCommandDataLoader.Metadata forArchived =
                PackageManagerShellCommandDataLoader.Metadata.forArchived(archivedPackageParcel);
        Binder.withCleanCallingIdentity(
                new FunctionalUtils
                        .ThrowingRunnable() { // from class:
                                              // com.android.server.pm.PackageInstallerService$$ExternalSyntheticLambda1
                    public final void runOrThrow() {
                        PackageInstallerService packageInstallerService =
                                PackageInstallerService.this;
                        PackageInstaller.SessionParams sessionParams2 = sessionParams;
                        String str2 = str;
                        int i = identifier;
                        PackageManagerShellCommandDataLoader.Metadata metadata = forArchived;
                        IntentSender intentSender2 = intentSender;
                        ArchivedPackageParcel archivedPackageParcel2 = archivedPackageParcel;
                        packageInstallerService.getClass();
                        PackageInstallerSession packageInstallerSession = null;
                        try {
                            try {
                                packageInstallerSession =
                                        packageInstallerService.openSessionInternal(
                                                packageInstallerService.createSessionInternal(
                                                        sessionParams2,
                                                        str2,
                                                        null,
                                                        Binder.getCallingUid(),
                                                        i));
                                packageInstallerSession.addFile(
                                        0, "base", 0L, metadata.toByteArray(), null);
                                packageInstallerSession.commit(intentSender2, false);
                                Slog.i(
                                        "PackageInstaller",
                                        TextUtils.formatSimple(
                                                "Installed archived app %s.",
                                                new Object[] {archivedPackageParcel2.packageName}));
                                packageInstallerSession.close();
                            } catch (IOException e) {
                                throw ExceptionUtils.wrap(e);
                            }
                        } catch (Throwable th) {
                            if (packageInstallerSession != null) {
                                packageInstallerSession.close();
                            }
                            throw th;
                        }
                    }
                });
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x002e  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x003d A[Catch: all -> 0x005a, TryCatch #0 {all -> 0x005a, blocks: (B:5:0x0004, B:7:0x000c, B:9:0x001b, B:12:0x0028, B:16:0x003d, B:18:0x0043, B:21:0x0050, B:23:0x0056, B:28:0x004a, B:32:0x002f, B:25:0x005c, B:34:0x0022, B:38:0x005f), top: B:4:0x0004 }] */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0056 A[Catch: all -> 0x005a, TryCatch #0 {all -> 0x005a, blocks: (B:5:0x0004, B:7:0x000c, B:9:0x001b, B:12:0x0028, B:16:0x003d, B:18:0x0043, B:21:0x0050, B:23:0x0056, B:28:0x004a, B:32:0x002f, B:25:0x005c, B:34:0x0022, B:38:0x005f), top: B:4:0x0004 }] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x005c A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:31:0x005c A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:32:0x002f A[Catch: all -> 0x005a, TryCatch #0 {all -> 0x005a, blocks: (B:5:0x0004, B:7:0x000c, B:9:0x001b, B:12:0x0028, B:16:0x003d, B:18:0x0043, B:21:0x0050, B:23:0x0056, B:28:0x004a, B:32:0x002f, B:25:0x005c, B:34:0x0022, B:38:0x005f), top: B:4:0x0004 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onInstallerPackageDeleted(int r7, int r8) {
        /*
            r6 = this;
            android.util.SparseArray r0 = r6.mSessions
            monitor-enter(r0)
            r1 = 0
        L4:
            android.util.SparseArray r2 = r6.mSessions     // Catch: java.lang.Throwable -> L5a
            int r2 = r2.size()     // Catch: java.lang.Throwable -> L5a
            if (r1 >= r2) goto L5f
            android.util.SparseArray r2 = r6.mSessions     // Catch: java.lang.Throwable -> L5a
            java.lang.Object r2 = r2.valueAt(r1)     // Catch: java.lang.Throwable -> L5a
            com.android.server.pm.PackageInstallerSession r2 = (com.android.server.pm.PackageInstallerSession) r2     // Catch: java.lang.Throwable -> L5a
            int r3 = r2.getInstallerUid()     // Catch: java.lang.Throwable -> L5a
            r4 = -1
            if (r7 != r4) goto L22
            int r3 = android.os.UserHandle.getAppId(r3)     // Catch: java.lang.Throwable -> L5a
            if (r3 != r7) goto L5c
            goto L28
        L22:
            int r5 = android.os.UserHandle.getUid(r8, r7)     // Catch: java.lang.Throwable -> L5a
            if (r5 != r3) goto L5c
        L28:
            boolean r3 = r2.hasParentSessionId()     // Catch: java.lang.Throwable -> L5a
            if (r3 != 0) goto L2f
            goto L3b
        L2f:
            android.util.SparseArray r3 = r6.mSessions     // Catch: java.lang.Throwable -> L5a
            int r2 = r2.getParentSessionId()     // Catch: java.lang.Throwable -> L5a
            java.lang.Object r2 = r3.get(r2)     // Catch: java.lang.Throwable -> L5a
            com.android.server.pm.PackageInstallerSession r2 = (com.android.server.pm.PackageInstallerSession) r2     // Catch: java.lang.Throwable -> L5a
        L3b:
            if (r2 == 0) goto L5c
            int r3 = r2.getInstallerUid()     // Catch: java.lang.Throwable -> L5a
            if (r7 != r4) goto L4a
            int r3 = android.os.UserHandle.getAppId(r3)     // Catch: java.lang.Throwable -> L5a
            if (r3 != r7) goto L5c
            goto L50
        L4a:
            int r4 = android.os.UserHandle.getUid(r8, r7)     // Catch: java.lang.Throwable -> L5a
            if (r4 != r3) goto L5c
        L50:
            boolean r3 = r2.isDestroyed()     // Catch: java.lang.Throwable -> L5a
            if (r3 != 0) goto L5c
            r2.abandon()     // Catch: java.lang.Throwable -> L5a
            goto L5c
        L5a:
            r6 = move-exception
            goto L61
        L5c:
            int r1 = r1 + 1
            goto L4
        L5f:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L5a
            return
        L61:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L5a
            throw r6
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.pm.PackageInstallerService.onInstallerPackageDeleted(int,"
                    + " int):void");
    }

    public final IPackageInstallerSession openSession(int i) {
        try {
            return openSessionInternal(i);
        } catch (IOException e) {
            throw ExceptionUtils.wrap(e);
        }
    }

    public final PackageInstallerSession openSessionInternal(int i) {
        PackageInstallerSession packageInstallerSession;
        synchronized (this.mSessions) {
            try {
                packageInstallerSession = (PackageInstallerSession) this.mSessions.get(i);
                if (!checkOpenSessionAccess(packageInstallerSession)) {
                    throw new SecurityException("Caller has no access to session " + i);
                }
                packageInstallerSession.open();
            } catch (Throwable th) {
                throw th;
            }
        }
        return packageInstallerSession;
    }

    public final PackageInstallerSession openSessionInternalQuick(int i, String str) {
        PackageInstallerSession packageInstallerSession;
        synchronized (this.mSessions) {
            try {
                packageInstallerSession = (PackageInstallerSession) this.mSessions.get(i);
                if (!checkOpenSessionAccess(packageInstallerSession)) {
                    throw new SecurityException("Caller has no access to session " + i);
                }
                packageInstallerSession.openQuick(str);
            } catch (Throwable th) {
                throw th;
            }
        }
        return packageInstallerSession;
    }

    public final IPackageInstallerSession openSessionQuick(int i, String str) {
        try {
            return openSessionInternalQuick(i, str);
        } catch (IOException e) {
            throw ExceptionUtils.wrap(e);
        }
    }

    public final void readSessionsLocked() {
        if (LOGD) {
            Slog.v("PackageInstaller", "readSessionsLocked()");
        }
        this.mSessions.clear();
        FileInputStream fileInputStream = null;
        try {
            try {
                try {
                    fileInputStream = this.mSessionsFile.openRead();
                    TypedXmlPullParser resolvePullParser = Xml.resolvePullParser(fileInputStream);
                    while (true) {
                        int next = resolvePullParser.next();
                        if (next == 1) {
                            break;
                        }
                        if (next == 2 && "session".equals(resolvePullParser.getName())) {
                            try {
                                PackageInstallerSession readFromXml =
                                        PackageInstallerSession.readFromXml(
                                                resolvePullParser,
                                                this.mInternalCallback,
                                                this.mContext,
                                                this.mPm,
                                                this.mInstallThread.getLooper(),
                                                this.mStagingManager,
                                                this.mSessionsDir,
                                                this,
                                                this.mSilentUpdatePolicy);
                                this.mSessions.put(readFromXml.sessionId, readFromXml);
                                this.mAllocatedSessions.put(readFromXml.sessionId, true);
                            } catch (Exception e) {
                                Slog.e("PackageInstaller", "Could not read session", e);
                            }
                        }
                    }
                } catch (FileNotFoundException unused) {
                }
            } finally {
                IoUtils.closeQuietly(fileInputStream);
            }
        } catch (IOException | ArrayIndexOutOfBoundsException | XmlPullParserException e2) {
            Slog.wtf("PackageInstaller", "Failed reading install sessions", e2);
        }
        IoUtils.closeQuietly(fileInputStream);
        for (int i = 0; i < this.mSessions.size(); i++) {
            PackageInstallerSession packageInstallerSession =
                    (PackageInstallerSession) this.mSessions.valueAt(i);
            SparseArray sparseArray = this.mSessions;
            synchronized (packageInstallerSession.mLock) {
                try {
                    for (int size = packageInstallerSession.mChildSessions.size() - 1;
                            size >= 0;
                            size--) {
                        int keyAt = packageInstallerSession.mChildSessions.keyAt(size);
                        PackageInstallerSession packageInstallerSession2 =
                                (PackageInstallerSession) sparseArray.get(keyAt);
                        if (packageInstallerSession2 != null) {
                            packageInstallerSession.mChildSessions.setValueAt(
                                    size, packageInstallerSession2);
                        } else {
                            Slog.e(
                                    "PackageInstallerSession",
                                    "Child session not existed: " + keyAt);
                            packageInstallerSession.mChildSessions.removeAt(size);
                        }
                    }
                } catch (PackageManagerException e3) {
                    Slog.e("PackageInstallerSession", "Package not valid", e3);
                } finally {
                }
                if (packageInstallerSession.mShouldBeSealed
                        && !packageInstallerSession.isStagedAndInTerminalState()) {
                    packageInstallerSession.sealLocked();
                    PackageInstaller.SessionParams sessionParams = packageInstallerSession.params;
                    if (!sessionParams.isMultiPackage
                            && sessionParams.isStaged
                            && packageInstallerSession.mCommitted.get()) {
                        PackageInstallerSession packageInstallerSession3 =
                                packageInstallerSession.hasParentSessionId()
                                        ? (PackageInstallerSession)
                                                sparseArray.get(
                                                        packageInstallerSession
                                                                .getParentSessionId())
                                        : packageInstallerSession;
                        if (packageInstallerSession3 != null
                                && !packageInstallerSession3.isStagedAndInTerminalState()) {
                            if (packageInstallerSession.isApexSession()) {
                                packageInstallerSession.validateApexInstallLocked();
                            } else {
                                packageInstallerSession.validateApkInstallLocked();
                            }
                        }
                    }
                }
            }
        }
    }

    public final void reconcileStagesLocked(String str) {
        File[] listFiles = Environment.getDataAppDirectory(str).listFiles(sStageFilter);
        ArraySet arraySet = new ArraySet();
        if (listFiles != null) {
            arraySet.ensureCapacity(listFiles.length);
            Collections.addAll(arraySet, listFiles);
        }
        File[] listFiles2 = Environment.getDataStagingDirectory(str).listFiles();
        ArraySet arraySet2 = new ArraySet();
        if (listFiles2 != null) {
            arraySet2.ensureCapacity(listFiles2.length);
            Collections.addAll(arraySet2, listFiles2);
        }
        arraySet.addAll(arraySet2);
        for (int i = 0; i < this.mSessions.size(); i++) {
            arraySet.remove(((PackageInstallerSession) this.mSessions.valueAt(i)).stageDir);
        }
        removeStagingDirs(arraySet);
    }

    public final void registerCallback(
            IPackageInstallerCallback iPackageInstallerCallback, final int i) {
        this.mPm
                .snapshotComputer()
                .enforceCrossUserPermission(
                        "registerCallback", Binder.getCallingUid(), i, true, false);
        IntPredicate intPredicate =
                new IntPredicate() { // from class:
                                     // com.android.server.pm.PackageInstallerService$$ExternalSyntheticLambda5
                    @Override // java.util.function.IntPredicate
                    public final boolean test(int i2) {
                        return i == i2;
                    }
                };
        Callbacks callbacks = this.mCallbacks;
        callbacks.mCallbacks.register(
                iPackageInstallerCallback,
                new BroadcastCookie(Binder.getCallingUid(), intPredicate));
    }

    public final void removeActiveSession(PackageInstallerSession packageInstallerSession) {
        this.mSessions.remove(packageInstallerSession.sessionId);
        addHistoricalSessionLocked(packageInstallerSession);
        for (PackageInstallerSession packageInstallerSession2 :
                packageInstallerSession.getChildSessions()) {
            this.mSessions.remove(packageInstallerSession2.sessionId);
            addHistoricalSessionLocked(packageInstallerSession2);
        }
    }

    public final void removeStagingDirs(ArraySet arraySet) {
        Iterator it = arraySet.iterator();
        while (it.hasNext()) {
            File file = (File) it.next();
            Slog.w("PackageInstaller", "Deleting orphan stage " + file);
            this.mPm.mRemovePackageHelper.removeCodePath(file);
        }
    }

    public final void reportUnarchivalStatus(
            int i, int i2, long j, PendingIntent pendingIntent, UserHandle userHandle) {
        Objects.requireNonNull(userHandle);
        if (i2 == 1) {
            Objects.requireNonNull(pendingIntent);
        }
        if (i2 == 2 && j <= 0) {
            throw new IllegalStateException(
                    "Insufficient storage error set, but requiredStorageBytes unspecified.");
        }
        if (i2 != 2 && j > 0) {
            throw new IllegalStateException(
                    TextUtils.formatSimple(
                            "requiredStorageBytes set, but error is %s.",
                            new Object[] {Integer.valueOf(i2)}));
        }
        if (!List.of(0, 1, 2, 3, 4, 5, 100).contains(Integer.valueOf(i2))) {
            throw new IllegalStateException(
                    VibrationParam$1$$ExternalSyntheticOutline0.m(
                            i2, "Invalid status code passed "));
        }
        int identifier = userHandle.getIdentifier();
        int callingUid = Binder.getCallingUid();
        synchronized (this.mSessions) {
            try {
                PackageInstallerSession packageInstallerSession =
                        (PackageInstallerSession) this.mSessions.get(i);
                if (packageInstallerSession == null
                        || packageInstallerSession.userId != identifier
                        || packageInstallerSession.params.appPackageName == null) {
                    throw new ParcelableException(
                            new PackageManager.NameNotFoundException(
                                    TextUtils.formatSimple(
                                            "No valid session with unarchival ID %s found for user"
                                                + " %s.",
                                            new Object[] {
                                                Integer.valueOf(i), Integer.valueOf(identifier)
                                            })));
                }
                if (!isCallingUidOwner(packageInstallerSession)) {
                    throw new SecurityException(
                            TextUtils.formatSimple(
                                    "The caller UID %s does not have access to the session with"
                                        + " unarchiveId %d.",
                                    new Object[] {
                                        Integer.valueOf(callingUid), Integer.valueOf(i)
                                    }));
                }
                packageInstallerSession.reportUnarchivalStatus(i2, i, j, pendingIntent);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void requestArchive(
            String str, String str2, int i, IntentSender intentSender, UserHandle userHandle) {
        this.mPackageArchiver.requestArchive(str, str2, i, intentSender, userHandle);
    }

    public final ParcelFileDescriptor requestCopy(String str, long j) {
        File prepareCustomCopyDir;
        int callingUid = Binder.getCallingUid();
        if (!PackageManagerServiceUtils.isSystemOrRootOrShell(callingUid)
                && this.mPm
                                .snapshotComputer()
                                .checkUidPermission(
                                        "android.permission.INSTALL_PACKAGES", callingUid)
                        != 0) {
            throw new SecurityException("Caller not authorised to use this method !!");
        }
        try {
            synchronized (this.mCreateLock) {
                prepareCustomCopyDir = prepareCustomCopyDir();
            }
            if (!FileUtils.isValidExtFilename(str)) {
                throw new IllegalArgumentException("Invalid name: " + str);
            }
            File file = new File(prepareCustomCopyDir, str);
            try {
                ParcelFileDescriptor parcelFileDescriptor =
                        new ParcelFileDescriptor(
                                Os.open(
                                        file.getAbsolutePath(),
                                        OsConstants.O_CREAT | OsConstants.O_WRONLY,
                                        438));
                Os.chmod(file.getAbsolutePath(), 438);
                if (j > 0) {
                    ((StorageManager) this.mContext.getSystemService(StorageManager.class))
                            .allocateBytes(parcelFileDescriptor.getFileDescriptor(), j, 0);
                }
                return parcelFileDescriptor;
            } catch (ErrnoException e) {
                e.printStackTrace();
                throw new IOException("Failed to open tempStageDir", e);
            }
        } catch (IOException e2) {
            throw ExceptionUtils.wrap(e2);
        }
    }

    public final void requestUnarchive(
            String str, String str2, IntentSender intentSender, UserHandle userHandle) {
        this.mPackageArchiver.requestUnarchive(str, str2, intentSender, userHandle, false);
    }

    public final void restoreAndApplyStagedSessionIfNeeded() {
        int i;
        boolean z;
        boolean z2;
        ArrayList arrayList = new ArrayList();
        synchronized (this.mSessions) {
            i = 0;
            for (int i2 = 0; i2 < this.mSessions.size(); i2++) {
                try {
                    PackageInstallerSession packageInstallerSession =
                            (PackageInstallerSession) this.mSessions.valueAt(i2);
                    if (packageInstallerSession.params.isStaged) {
                        PackageInstallerSession.StagedSession stagedSession =
                                packageInstallerSession.mStagedSession;
                        if (!stagedSession.isInTerminalState()
                                && PackageInstallerSession.this.hasParentSessionId()
                                && getSession(PackageInstallerSession.this.getParentSessionId())
                                        == null) {
                            stagedSession.setSessionFailed(
                                    -128,
                                    "An orphan staged session "
                                            + PackageInstallerSession.this.sessionId
                                            + " is found, parent "
                                            + PackageInstallerSession.this.getParentSessionId()
                                            + " is missing");
                        } else if (!PackageInstallerSession.this.hasParentSessionId()
                                && PackageInstallerSession.this.mCommitted.get()
                                && !stagedSession.isInTerminalState()) {
                            arrayList.add(stagedSession);
                        }
                    }
                } finally {
                }
            }
        }
        StagingManager stagingManager = this.mStagingManager;
        boolean isDeviceUpgrading = this.mPm.isDeviceUpgrading();
        stagingManager.getClass();
        TimingsTraceLog timingsTraceLog = new TimingsTraceLog("StagingManagerTiming", 262144L);
        timingsTraceLog.traceBegin("restoreSessions");
        if (SystemProperties.getBoolean("sys.boot_completed", false)) {
            return;
        }
        for (int i3 = 0; i3 < arrayList.size(); i3++) {
            StagingManager.StagedSession stagedSession2 =
                    (StagingManager.StagedSession) arrayList.get(i3);
            PackageInstallerSession.StagedSession stagedSession3 =
                    (PackageInstallerSession.StagedSession) stagedSession2;
            Preconditions.checkArgument(
                    !PackageInstallerSession.this.hasParentSessionId(),
                    PackageInstallerSession.this.sessionId + " is a child session");
            Preconditions.checkArgument(
                    PackageInstallerSession.this.mCommitted.get(),
                    PackageInstallerSession.this.sessionId + " is not committed");
            Preconditions.checkArgument(
                    true ^ stagedSession3.isInTerminalState(),
                    PackageInstallerSession.this.sessionId + " is in terminal state");
            stagingManager.createSession(stagedSession2);
        }
        if (isDeviceUpgrading) {
            while (i < arrayList.size()) {
                ((PackageInstallerSession.StagedSession)
                                ((StagingManager.StagedSession) arrayList.get(i)))
                        .setSessionFailed(-128, "Build fingerprint has changed");
                i++;
            }
            return;
        }
        try {
            boolean supportsCheckpoint =
                    InstallLocationUtils.getStorageManager().supportsCheckpoint();
            boolean needsCheckpoint = InstallLocationUtils.getStorageManager().needsCheckpoint();
            if (arrayList.size() > 1 && !supportsCheckpoint) {
                throw new IllegalStateException(
                        "Detected multiple staged sessions on a device without fs-checkpoint"
                            + " support");
            }
            int size = arrayList.size();
            int i4 = 0;
            while (i4 < size) {
                StagingManager.StagedSession stagedSession4 =
                        (StagingManager.StagedSession) arrayList.get(i4);
                PackageInstallerSession.StagedSession stagedSession5 =
                        (PackageInstallerSession.StagedSession) stagedSession4;
                if (PackageInstallerSession.this.isDestroyed()) {
                    PackageInstallerSession.this.abandon();
                    arrayList.set(
                            i4,
                            (StagingManager.StagedSession) arrayList.set(size - 1, stagedSession4));
                } else if (stagedSession5.isSessionReady()) {
                    i4++;
                } else {
                    SystemServiceManager$$ExternalSyntheticOutline0.m(
                            new StringBuilder("Restart verification for session="),
                            PackageInstallerSession.this.sessionId,
                            "StagingManager");
                    stagingManager.mBootCompleted.thenRun(
                            (Runnable)
                                    new StagingManager$$ExternalSyntheticLambda0(
                                            1, stagedSession4));
                    arrayList.set(
                            i4,
                            (StagingManager.StagedSession) arrayList.set(size - 1, stagedSession4));
                }
                size--;
            }
            arrayList.subList(size, arrayList.size()).clear();
            ApexManager.ApexManagerImpl apexManagerImpl =
                    (ApexManager.ApexManagerImpl) stagingManager.mApexManager;
            apexManagerImpl.getClass();
            try {
                ApexSessionInfo[] sessions = apexManagerImpl.waitForApexService().getSessions();
                SparseArray sparseArray = new SparseArray(sessions.length);
                for (ApexSessionInfo apexSessionInfo : sessions) {
                    sparseArray.put(apexSessionInfo.sessionId, apexSessionInfo);
                }
                boolean z3 = false;
                boolean z4 = false;
                for (int i5 = 0; i5 < arrayList.size(); i5++) {
                    PackageInstallerSession.StagedSession stagedSession6 =
                            (PackageInstallerSession.StagedSession)
                                    ((StagingManager.StagedSession) arrayList.get(i5));
                    if (stagedSession6.containsApexSession()) {
                        ApexSessionInfo apexSessionInfo2 =
                                (ApexSessionInfo)
                                        sparseArray.get(PackageInstallerSession.this.sessionId);
                        if (apexSessionInfo2 == null || (z2 = apexSessionInfo2.isUnknown)) {
                            stagedSession6.setSessionFailed(
                                    -128,
                                    "apexd did not know anything about a staged session supposed to"
                                        + " be activated");
                        } else if (apexSessionInfo2.isActivationFailed
                                || z2
                                || apexSessionInfo2.isReverted
                                || apexSessionInfo2.isRevertInProgress
                                || apexSessionInfo2.isRevertFailed) {
                            if (!TextUtils.isEmpty(apexSessionInfo2.crashingNativeProcess)) {
                                String str = apexSessionInfo2.crashingNativeProcess;
                                synchronized (stagingManager.mFailedPackageNames) {
                                    try {
                                        stagingManager.mNativeFailureReason = str;
                                        if (PackageInstallerSession.this.getPackageName() != null) {
                                            ((ArrayList) stagingManager.mFailedPackageNames)
                                                    .add(
                                                            PackageInstallerSession.this
                                                                    .getPackageName());
                                        }
                                    } finally {
                                    }
                                }
                            }
                            String str2 = "APEX activation failed.";
                            String reasonForRevert = stagingManager.getReasonForRevert();
                            if (!TextUtils.isEmpty(reasonForRevert)) {
                                str2 =
                                        ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(
                                                "APEX activation failed. Reason: ",
                                                reasonForRevert);
                            } else if (!TextUtils.isEmpty(apexSessionInfo2.errorMessage)) {
                                str2 =
                                        "APEX activation failed. Error: "
                                                + apexSessionInfo2.errorMessage;
                            }
                            Slog.d("StagingManager", str2);
                            stagedSession6.setSessionFailed(-128, str2);
                        } else if (apexSessionInfo2.isActivated || apexSessionInfo2.isSuccess) {
                            z3 = true;
                        } else if (apexSessionInfo2.isStaged) {
                            stagedSession6.setSessionFailed(
                                    -128,
                                    "Staged session "
                                            + PackageInstallerSession.this.sessionId
                                            + " at boot didn't activate nor fail. Marking it as"
                                            + " failed anyway.");
                        } else {
                            UiModeManagerService$13$$ExternalSyntheticOutline0.m(
                                    new StringBuilder("Apex session "),
                                    PackageInstallerSession.this.sessionId,
                                    " is in impossible state",
                                    "StagingManager");
                            stagedSession6.setSessionFailed(-128, "Impossible state");
                        }
                        z4 = true;
                    }
                }
                if (z3 && z4) {
                    stagingManager.abortCheckpoint(
                            "Found both applied and failed apex sessions",
                            supportsCheckpoint,
                            needsCheckpoint);
                    return;
                }
                if (z4) {
                    while (i < arrayList.size()) {
                        PackageInstallerSession.StagedSession stagedSession7 =
                                (PackageInstallerSession.StagedSession)
                                        ((StagingManager.StagedSession) arrayList.get(i));
                        PackageInstallerSession packageInstallerSession2 =
                                PackageInstallerSession.this;
                        synchronized (packageInstallerSession2.mLock) {
                            z = packageInstallerSession2.mSessionFailed;
                        }
                        if (!z) {
                            stagedSession7.setSessionFailed(-128, "Another apex session failed");
                        }
                        i++;
                    }
                    return;
                }
                while (i < arrayList.size()) {
                    StagingManager.StagedSession stagedSession8 =
                            (StagingManager.StagedSession) arrayList.get(i);
                    try {
                        stagingManager.resumeSession(
                                stagedSession8, supportsCheckpoint, needsCheckpoint);
                    } catch (PackageManagerException e) {
                        stagingManager.onInstallationFailure(
                                stagedSession8, e, supportsCheckpoint, needsCheckpoint);
                    } catch (Exception e2) {
                        Slog.e(
                                "StagingManager",
                                "Staged install failed due to unhandled exception",
                                e2);
                        stagingManager.onInstallationFailure(
                                stagedSession8,
                                new PackageManagerException(
                                        -110,
                                        "Staged install failed due to unhandled exception: " + e2),
                                supportsCheckpoint,
                                needsCheckpoint);
                    }
                    i++;
                }
                timingsTraceLog.traceEnd();
            } catch (RemoteException e3) {
                Slog.e("ApexManager", "Unable to contact apexservice", e3);
                throw new RuntimeException(e3);
            }
        } catch (RemoteException e4) {
            throw new IllegalStateException("Failed to get checkpoint status", e4);
        }
    }

    public final void setAllowUnlimitedSilentUpdates(String str) {
        if (!PackageManagerServiceUtils.isSystemOrRootOrShell(Binder.getCallingUid())) {
            throw new SecurityException("Caller not allowed to unlimite silent updates");
        }
        SilentUpdatePolicy silentUpdatePolicy = this.mSilentUpdatePolicy;
        synchronized (silentUpdatePolicy.mSilentUpdateInfos) {
            if (str == null) {
                try {
                    silentUpdatePolicy.mSilentUpdateInfos.clear();
                } catch (Throwable th) {
                    throw th;
                }
            }
            silentUpdatePolicy.mAllowUnlimitedSilentUpdatesInstaller = str;
        }
    }

    public final void setPermissionsResult(int i, boolean z) {
        setPermissionsResult_enforcePermission();
        synchronized (this.mSessions) {
            try {
                PackageInstallerSession packageInstallerSession =
                        (PackageInstallerSession) this.mSessions.get(i);
                if (packageInstallerSession != null) {
                    packageInstallerSession.setPermissionsResult(z);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void setSilentUpdatesThrottleTime(long j) {
        if (!PackageManagerServiceUtils.isSystemOrRootOrShell(Binder.getCallingUid())) {
            throw new SecurityException("Caller not allowed to set silent updates throttle time");
        }
        SilentUpdatePolicy silentUpdatePolicy = this.mSilentUpdatePolicy;
        synchronized (silentUpdatePolicy.mSilentUpdateInfos) {
            try {
                silentUpdatePolicy.mSilentUpdateThrottleTimeMs =
                        j >= 0
                                ? TimeUnit.SECONDS.toMillis(j)
                                : SilentUpdatePolicy.SILENT_UPDATE_THROTTLE_TIME_MS;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void setUnknownSourceConfirmResult(int i, boolean z) {
        this.mContext.enforceCallingOrSelfPermission(
                "android.permission.INSTALL_PACKAGES", "PackageInstaller");
        synchronized (this.mSessions) {
            try {
                PackageInstallerSession packageInstallerSession =
                        (PackageInstallerSession) this.mSessions.get(i);
                if (packageInstallerSession != null) {
                    packageInstallerSession.setUnknownSourceConfirmResult(z);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void uninstall(
            VersionedPackage versionedPackage,
            String str,
            int i,
            IntentSender intentSender,
            int i2) {
        uninstall(
                versionedPackage,
                str,
                i,
                intentSender,
                i2,
                Binder.getCallingUid(),
                Binder.getCallingPid());
    }

    public final void uninstall(
            VersionedPackage versionedPackage,
            String str,
            int i,
            IntentSender intentSender,
            int i2,
            int i3,
            int i4) {
        Computer snapshotComputer = this.mPm.snapshotComputer();
        snapshotComputer.enforceCrossUserPermission("uninstall", i3, i2, true, true);
        if (!PackageManagerServiceUtils.isRootOrShell(i3)) {
            this.mAppOps.checkPackage(i3, str);
        }
        DevicePolicyManagerInternal devicePolicyManagerInternal =
                (DevicePolicyManagerInternal)
                        LocalServices.getService(DevicePolicyManagerInternal.class);
        boolean z =
                (devicePolicyManagerInternal != null
                                && devicePolicyManagerInternal.canSilentlyInstallPackage(str, i3))
                        || PackageInstallerSession.isEmergencyInstallerEnabled(
                                i2, i3, snapshotComputer, versionedPackage.getPackageName());
        PackageDeleteObserverAdapter packageDeleteObserverAdapter =
                new PackageDeleteObserverAdapter(
                        this.mContext,
                        intentSender,
                        versionedPackage.getPackageName(),
                        z,
                        i2,
                        this.mPackageArchiver,
                        i);
        if (this.mContext.checkPermission("android.permission.DELETE_PACKAGES", i4, i3) == 0) {
            this.mPm.mDeletePackageHelper.deletePackageVersionedInternal(
                    versionedPackage, packageDeleteObserverAdapter.getBinder(), i2, i, false);
            return;
        }
        if (z) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                this.mPm.mDeletePackageHelper.deletePackageVersionedInternal(
                        versionedPackage, packageDeleteObserverAdapter.getBinder(), i2, i, false);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                DevicePolicyEventLogger.createEvent(113).setAdmin(str).write();
                return;
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }
        if (snapshotComputer.getApplicationInfo(str, 0L, i2).targetSdkVersion >= 28) {
            this.mContext.enforcePermission(
                    "android.permission.REQUEST_DELETE_PACKAGES", i4, i3, null);
        }
        Intent intent = new Intent("android.intent.action.UNINSTALL_PACKAGE");
        intent.setData(Uri.fromParts("package", versionedPackage.getPackageName(), null));
        intent.putExtra(
                "android.content.pm.extra.CALLBACK",
                (Parcelable)
                        new PackageManager.UninstallCompleteCallback(
                                packageDeleteObserverAdapter.getBinder().asBinder()));
        if ((i & 16) != 0) {
            intent.putExtra("android.content.pm.extra.DELETE_FLAGS", i);
        }
        packageDeleteObserverAdapter.onUserActionRequired(intent);
    }

    public final void uninstallExistingPackage(
            VersionedPackage versionedPackage, String str, IntentSender intentSender, int i) {
        int callingUid = Binder.getCallingUid();
        this.mContext.enforceCallingOrSelfPermission("android.permission.DELETE_PACKAGES", null);
        this.mPm
                .snapshotComputer()
                .enforceCrossUserPermission("uninstall", callingUid, i, true, true);
        if (!PackageManagerServiceUtils.isRootOrShell(callingUid)) {
            this.mAppOps.checkPackage(callingUid, str);
        }
        this.mPm.deleteExistingPackageAsUser(
                versionedPackage,
                new PackageDeleteObserverAdapter(
                                i, this.mContext, intentSender, versionedPackage.getPackageName())
                        .getBinder(),
                i);
    }

    public final void unregisterCallback(IPackageInstallerCallback iPackageInstallerCallback) {
        this.mCallbacks.mCallbacks.unregister(iPackageInstallerCallback);
    }

    public final void updateSessionAppIcon(int i, Bitmap bitmap) {
        synchronized (this.mSessions) {
            try {
                PackageInstallerSession packageInstallerSession =
                        (PackageInstallerSession) this.mSessions.get(i);
                if (packageInstallerSession == null
                        || !isCallingUidOwner(packageInstallerSession)) {
                    throw new SecurityException("Caller has no access to session " + i);
                }
                if (bitmap != null) {
                    int launcherLargeIconSize =
                            ((ActivityManager) this.mContext.getSystemService("activity"))
                                    .getLauncherLargeIconSize();
                    int i2 = launcherLargeIconSize * 2;
                    if (bitmap.getWidth() <= i2) {
                        if (bitmap.getHeight() > i2) {}
                    }
                    bitmap =
                            Bitmap.createScaledBitmap(
                                    bitmap, launcherLargeIconSize, launcherLargeIconSize, true);
                }
                PackageInstaller.SessionParams sessionParams = packageInstallerSession.params;
                sessionParams.appIcon = bitmap;
                sessionParams.appIconLastModified = -1L;
                PackageInstallerService packageInstallerService = PackageInstallerService.this;
                Callbacks callbacks = packageInstallerService.mCallbacks;
                int i3 = packageInstallerSession.sessionId;
                int i4 = packageInstallerSession.userId;
                int i5 = Callbacks.$r8$clinit;
                callbacks.obtainMessage(2, i3, i4).sendToTarget();
                packageInstallerService.mSettingsWriteRequest.schedule();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void updateSessionAppLabel(int i, String str) {
        synchronized (this.mSessions) {
            try {
                PackageInstallerSession packageInstallerSession =
                        (PackageInstallerSession) this.mSessions.get(i);
                if (packageInstallerSession == null
                        || !isCallingUidOwner(packageInstallerSession)) {
                    throw new SecurityException("Caller has no access to session " + i);
                }
                if (!str.equals(packageInstallerSession.params.appLabel)) {
                    packageInstallerSession.params.appLabel = str;
                    PackageInstallerService packageInstallerService = PackageInstallerService.this;
                    Callbacks callbacks = packageInstallerService.mCallbacks;
                    int i2 = packageInstallerSession.sessionId;
                    int i3 = packageInstallerSession.userId;
                    int i4 = Callbacks.$r8$clinit;
                    callbacks.obtainMessage(2, i2, i3).sendToTarget();
                    packageInstallerService.mSettingsWriteRequest.schedule();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void waitForInstallConstraints(
            String str,
            final List list,
            final PackageInstaller.InstallConstraints installConstraints,
            final IntentSender intentSender,
            long j) {
        Objects.requireNonNull(intentSender);
        if (j < 0 || j > 604800000) {
            throw new IllegalArgumentException(
                    DeviceIdleController$$ExternalSyntheticOutline0.m(j, "Invalid timeoutMillis="));
        }
        checkInstallConstraintsInternal(str, list, installConstraints, j)
                .thenAccept(
                        new Consumer() { // from class:
                                         // com.android.server.pm.PackageInstallerService$$ExternalSyntheticLambda2
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                PackageInstallerService packageInstallerService =
                                        PackageInstallerService.this;
                                List list2 = list;
                                PackageInstaller.InstallConstraints installConstraints2 =
                                        installConstraints;
                                IntentSender intentSender2 = intentSender;
                                packageInstallerService.getClass();
                                Intent intent = new Intent();
                                intent.putExtra(
                                        "android.intent.extra.PACKAGES",
                                        (String[]) list2.toArray(new String[0]));
                                intent.putExtra(
                                        "android.content.pm.extra.INSTALL_CONSTRAINTS",
                                        installConstraints2);
                                intent.putExtra(
                                        "android.content.pm.extra.INSTALL_CONSTRAINTS_RESULT",
                                        (PackageInstaller.InstallConstraintsResult) obj);
                                try {
                                    BroadcastOptions makeBasic = BroadcastOptions.makeBasic();
                                    makeBasic.setPendingIntentBackgroundActivityLaunchAllowed(
                                            false);
                                    intentSender2.sendIntent(
                                            packageInstallerService.mContext,
                                            0,
                                            intent,
                                            null,
                                            null,
                                            null,
                                            makeBasic.toBundle());
                                } catch (IntentSender.SendIntentException unused) {
                                }
                            }
                        });
    }
}
