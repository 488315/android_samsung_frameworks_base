package com.android.server.pm;

import android.R;
import android.app.ActivityManager;
import android.app.AppGlobals;
import android.app.AppOpsManager;
import android.app.BroadcastOptions;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PackageDeleteObserver;
import android.app.admin.DevicePolicyEventLogger;
import android.app.admin.DevicePolicyManager;
import android.app.admin.DevicePolicyManagerInternal;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.p000pm.PackageManagerInternal;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageInstaller;
import android.content.pm.IPackageInstallerCallback;
import android.content.pm.IPackageInstallerSession;
import android.content.pm.PackageInfo;
import android.content.pm.PackageInstaller;
import android.content.pm.PackageManager;
import android.content.pm.ParceledListSlice;
import android.content.pm.VersionedPackage;
import android.content.pm.parsing.FrameworkParsingPackageUtils;
import android.graphics.Bitmap;
import android.net.Uri;
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
import android.os.Process;
import android.os.RemoteCallback;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.SELinux;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.os.storage.StorageManager;
import android.p005os.IInstalld;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.text.TextUtils;
import android.util.ArrayMap;
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
import android.util.Xml;
import com.android.internal.content.InstallLocationUtils;
import com.android.internal.notification.SystemNotificationChannels;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.ImageUtils;
import com.android.internal.util.IndentingPrintWriter;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.IoThread;
import com.android.server.LocalServices;
import com.android.server.SystemConfig;
import com.android.server.SystemService;
import com.android.server.SystemServiceManager;
import com.android.server.pm.PackageInstallerService;
import com.android.server.pm.PackageInstallerSession;
import com.android.server.pm.pkg.PackageStateInternal;
import com.android.server.pm.utils.RequestThrottle;
import com.samsung.android.core.pm.containerservice.PackageHelperExt;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.knox.restriction.IRestrictionPolicy;
import com.samsung.android.rune.PMRune;
import java.io.CharArrayWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.security.SecureRandom;
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
import java.util.function.Consumer;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import libcore.io.IoUtils;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes3.dex */
public class PackageInstallerService extends IPackageInstaller.Stub implements PackageSessionProvider {
    public final ApexManager mApexManager;
    public AppOpsManager mAppOps;
    public final Callbacks mCallbacks;
    public final Context mContext;
    public final GentleUpdateHelper mGentleUpdateHelper;
    public final Handler mInstallHandler;
    public final HandlerThread mInstallThread;
    public final PackageManagerService mPm;
    public final PackageManagerInternal mPmInternal;
    public final PackageSessionVerifier mSessionVerifier;
    public final File mSessionsDir;
    public final AtomicFile mSessionsFile;
    public final StagingManager mStagingManager;
    public static final boolean LOGD = Log.isLoggable("PackageInstaller", 3);
    public static final boolean DEBUG = Build.IS_DEBUGGABLE;
    public static final Set INSTALLER_CHANGEABLE_APP_OP_PERMISSIONS = Set.of("android.permission.USE_FULL_SCREEN_INTENT");
    public static final FilenameFilter sStageFilter = new FilenameFilter() { // from class: com.android.server.pm.PackageInstallerService.1
        @Override // java.io.FilenameFilter
        public boolean accept(File file, String str) {
            return PackageInstallerService.isStageName(str);
        }
    };
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
    public Object mCreateLock = new Object();
    public final RequestThrottle mSettingsWriteRequest = new RequestThrottle(IoThread.getHandler(), new Supplier() { // from class: com.android.server.pm.PackageInstallerService$$ExternalSyntheticLambda5
        @Override // java.util.function.Supplier
        public final Object get() {
            Boolean lambda$new$0;
            lambda$new$0 = PackageInstallerService.this.lambda$new$0();
            return lambda$new$0;
        }
    });

    public static /* synthetic */ boolean lambda$registerCallback$5(int i, int i2) {
        return i == i2;
    }

    public final class Lifecycle extends SystemService {
        public final PackageInstallerService mPackageInstallerService;

        @Override // com.android.server.SystemService
        public void onStart() {
        }

        public Lifecycle(Context context, PackageInstallerService packageInstallerService) {
            super(context);
            this.mPackageInstallerService = packageInstallerService;
        }

        @Override // com.android.server.SystemService
        public void onBootPhase(int i) {
            if (i == 550) {
                this.mPackageInstallerService.onBroadcastReady();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Boolean lambda$new$0() {
        Boolean valueOf;
        synchronized (this.mSessions) {
            valueOf = Boolean.valueOf(writeSessionsLocked());
        }
        return valueOf;
    }

    public PackageInstallerService(Context context, PackageManagerService packageManagerService, Supplier supplier) {
        this.mContext = context;
        this.mPm = packageManagerService;
        HandlerThread handlerThread = new HandlerThread("PackageInstaller");
        this.mInstallThread = handlerThread;
        handlerThread.start();
        this.mInstallHandler = new Handler(handlerThread.getLooper());
        this.mCallbacks = new Callbacks(handlerThread.getLooper());
        this.mSessionsFile = new AtomicFile(new File(Environment.getDataSystemDirectory(), "install_sessions.xml"), "package-session");
        File file = new File(Environment.getDataSystemDirectory(), "install_sessions");
        this.mSessionsDir = file;
        file.mkdirs();
        ApexManager apexManager = ApexManager.getInstance();
        this.mApexManager = apexManager;
        this.mStagingManager = new StagingManager(context);
        this.mPmInternal = (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
        this.mSessionVerifier = new PackageSessionVerifier(context, packageManagerService, apexManager, supplier, handlerThread.getLooper());
        this.mGentleUpdateHelper = new GentleUpdateHelper(context, handlerThread.getLooper(), new AppStateHelper(context));
        ((SystemServiceManager) LocalServices.getService(SystemServiceManager.class)).startService(new Lifecycle(context, this));
    }

    public StagingManager getStagingManager() {
        return this.mStagingManager;
    }

    public boolean okToSendBroadcasts() {
        return this.mOkToSendBroadcasts;
    }

    public void systemReady() {
        this.mAppOps = (AppOpsManager) this.mContext.getSystemService(AppOpsManager.class);
        this.mStagingManager.systemReady();
        this.mGentleUpdateHelper.systemReady();
        synchronized (this.mSessions) {
            readSessionsLocked();
            expireSessionsLocked();
            reconcileStagesLocked(StorageManager.UUID_PRIVATE_INTERNAL);
            ArraySet newArraySet = newArraySet(this.mSessionsDir.listFiles());
            for (int i = 0; i < this.mSessions.size(); i++) {
                newArraySet.remove(buildAppIconFile(((PackageInstallerSession) this.mSessions.valueAt(i)).sessionId));
            }
            Iterator it = newArraySet.iterator();
            while (it.hasNext()) {
                File file = (File) it.next();
                Slog.w("PackageInstaller", "Deleting orphan icon " + file);
                file.delete();
            }
            this.mSettingsWriteRequest.runNow();
        }
        if (PMRune.PM_SPEED_INSTALL) {
            File file2 = new File(Environment.getDataDirectory(), "apk-tmp");
            if (file2.exists()) {
                FileUtils.deleteContents(file2);
            }
        }
    }

    public final void onBroadcastReady() {
        this.mOkToSendBroadcasts = true;
    }

    public void restoreAndApplyStagedSessionIfNeeded() {
        ArrayList arrayList = new ArrayList();
        synchronized (this.mSessions) {
            for (int i = 0; i < this.mSessions.size(); i++) {
                PackageInstallerSession packageInstallerSession = (PackageInstallerSession) this.mSessions.valueAt(i);
                if (packageInstallerSession.isStaged()) {
                    PackageInstallerSession.StagedSession stagedSession = packageInstallerSession.mStagedSession;
                    if (!stagedSession.isInTerminalState() && stagedSession.hasParentSessionId() && getSession(stagedSession.getParentSessionId()) == null) {
                        stagedSession.setSessionFailed(-128, "An orphan staged session " + stagedSession.sessionId() + " is found, parent " + stagedSession.getParentSessionId() + " is missing");
                    } else if (!stagedSession.hasParentSessionId() && stagedSession.isCommitted() && !stagedSession.isInTerminalState()) {
                        arrayList.add(stagedSession);
                    }
                }
            }
        }
        this.mStagingManager.restoreSessions(arrayList, this.mPm.isDeviceUpgrading());
    }

    public final void reconcileStagesLocked(String str) {
        ArraySet stagingDirsOnVolume = getStagingDirsOnVolume(str);
        for (int i = 0; i < this.mSessions.size(); i++) {
            stagingDirsOnVolume.remove(((PackageInstallerSession) this.mSessions.valueAt(i)).stageDir);
        }
        removeStagingDirs(stagingDirsOnVolume);
    }

    public final ArraySet getStagingDirsOnVolume(String str) {
        ArraySet newArraySet = newArraySet(getTmpSessionDir(str).listFiles(sStageFilter));
        newArraySet.addAll(newArraySet(Environment.getDataStagingDirectory(str).listFiles()));
        return newArraySet;
    }

    public final void removeStagingDirs(ArraySet arraySet) {
        RemovePackageHelper removePackageHelper = new RemovePackageHelper(this.mPm);
        Iterator it = arraySet.iterator();
        while (it.hasNext()) {
            File file = (File) it.next();
            Slog.w("PackageInstaller", "Deleting orphan stage " + file);
            removePackageHelper.removeCodePath(file);
        }
    }

    public void onPrivateVolumeMounted(String str) {
        synchronized (this.mSessions) {
            reconcileStagesLocked(str);
        }
    }

    public void onSecureContainersAvailable() {
        synchronized (this.mSessions) {
            ArraySet arraySet = new ArraySet();
            for (String str : PackageHelperExt.getSecureContainerList()) {
                if (isStageName(str)) {
                    arraySet.add(str);
                }
            }
            for (int i = 0; i < this.mSessions.size(); i++) {
                String str2 = ((PackageInstallerSession) this.mSessions.valueAt(i)).stageCid;
                if (arraySet.remove(str2)) {
                    PackageHelperExt.mountSdDir(str2, AsecInstallHelper.getEncryptKey(), 1000);
                }
            }
            Iterator it = arraySet.iterator();
            while (it.hasNext()) {
                String str3 = (String) it.next();
                Slog.i("PackageInstaller", "Deleting orphan container " + str3);
                PackageHelperExt.destroySdDir(str3);
            }
        }
    }

    public void freeStageDirs(String str) {
        ArraySet stagingDirsOnVolume = getStagingDirsOnVolume(str);
        long currentTimeMillis = System.currentTimeMillis();
        synchronized (this.mSessions) {
            for (int i = 0; i < this.mSessions.size(); i++) {
                PackageInstallerSession packageInstallerSession = (PackageInstallerSession) this.mSessions.valueAt(i);
                if (stagingDirsOnVolume.contains(packageInstallerSession.stageDir)) {
                    if (currentTimeMillis - packageInstallerSession.createdMillis >= 28800000) {
                        PackageInstallerSession packageInstallerSession2 = !packageInstallerSession.hasParentSessionId() ? packageInstallerSession : (PackageInstallerSession) this.mSessions.get(packageInstallerSession.getParentSessionId());
                        if (packageInstallerSession2 == null) {
                            Slog.e("PackageInstaller", "freeStageDirs: found an orphaned session: " + packageInstallerSession.sessionId + " parent=" + packageInstallerSession.getParentSessionId());
                        } else if (!packageInstallerSession2.isDestroyed()) {
                            packageInstallerSession2.abandon();
                        }
                    } else {
                        stagingDirsOnVolume.remove(packageInstallerSession.stageDir);
                    }
                }
            }
        }
        removeStagingDirs(stagingDirsOnVolume);
    }

    public File allocateStageDirLegacy(String str, boolean z) {
        File buildTmpSessionDir;
        synchronized (this.mSessions) {
            try {
                try {
                    int allocateSessionIdLocked = allocateSessionIdLocked();
                    this.mLegacySessions.put(allocateSessionIdLocked, true);
                    buildTmpSessionDir = buildTmpSessionDir(allocateSessionIdLocked, str);
                    prepareStageDir(buildTmpSessionDir);
                } catch (IllegalStateException e) {
                    throw new IOException(e);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return buildTmpSessionDir;
    }

    public String allocateExternalStageCidLegacy() {
        String str;
        synchronized (this.mSessions) {
            int allocateSessionIdLocked = allocateSessionIdLocked();
            this.mLegacySessions.put(allocateSessionIdLocked, true);
            str = "smdl" + allocateSessionIdLocked + ".tmp";
        }
        return str;
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
                                PackageInstallerSession readFromXml = PackageInstallerSession.readFromXml(resolvePullParser, this.mInternalCallback, this.mContext, this.mPm, this.mInstallThread.getLooper(), this.mStagingManager, this.mSessionsDir, this, this.mSilentUpdatePolicy);
                                this.mSessions.put(readFromXml.sessionId, readFromXml);
                                this.mAllocatedSessions.put(readFromXml.sessionId, true);
                            } catch (Exception e) {
                                Slog.e("PackageInstaller", "Could not read session", e);
                            }
                        }
                    }
                } catch (Throwable th) {
                    IoUtils.closeQuietly(fileInputStream);
                    throw th;
                }
            } catch (FileNotFoundException unused) {
            }
        } catch (IOException | ArrayIndexOutOfBoundsException | XmlPullParserException e2) {
            Slog.wtf("PackageInstaller", "Failed reading install sessions", e2);
        }
        IoUtils.closeQuietly(fileInputStream);
        for (int i = 0; i < this.mSessions.size(); i++) {
            ((PackageInstallerSession) this.mSessions.valueAt(i)).onAfterSessionRead(this.mSessions);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0043, code lost:
    
        if (r7 >= 1814400000) goto L17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0081, code lost:
    
        if (isValidSmartSwitchSession(r4.getInstallerUid(), r5) == false) goto L17;
     */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0086  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x009f A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void expireSessionsLocked() {
        SparseArray clone = this.mSessions.clone();
        this.mSmartSwitchUid = -1;
        int size = clone.size();
        for (int i = 0; i < size; i++) {
            PackageInstallerSession packageInstallerSession = (PackageInstallerSession) clone.valueAt(i);
            if (!packageInstallerSession.hasParentSessionId()) {
                long currentTimeMillis = System.currentTimeMillis() - packageInstallerSession.createdMillis;
                long currentTimeMillis2 = System.currentTimeMillis() - packageInstallerSession.getUpdatedMillis();
                boolean z = true;
                if (packageInstallerSession.isStaged()) {
                    if (packageInstallerSession.isStagedAndInTerminalState()) {
                    }
                    if (!z) {
                        Slog.w("PackageInstaller", "Remove old session: " + packageInstallerSession.sessionId);
                        removeActiveSession(packageInstallerSession);
                    }
                } else {
                    if (currentTimeMillis >= 259200000) {
                        Slog.w("PackageInstaller", "Abandoning old session created at " + packageInstallerSession.createdMillis);
                    } else {
                        if (packageInstallerSession.getInstallSource() != null) {
                            if ("com.sec.android.easyMover".equals(packageInstallerSession.getInstallSource().mInitiatingPackageName)) {
                            }
                        }
                        if (!z) {
                        }
                    }
                    z = false;
                    if (!z) {
                    }
                }
            }
        }
    }

    public final void removeActiveSession(PackageInstallerSession packageInstallerSession) {
        this.mSessions.remove(packageInstallerSession.sessionId);
        addHistoricalSessionLocked(packageInstallerSession);
        for (PackageInstallerSession packageInstallerSession2 : packageInstallerSession.getChildSessions()) {
            this.mSessions.remove(packageInstallerSession2.sessionId);
            addHistoricalSessionLocked(packageInstallerSession2);
        }
    }

    public final void addHistoricalSessionLocked(PackageInstallerSession packageInstallerSession) {
        CharArrayWriter charArrayWriter = new CharArrayWriter();
        packageInstallerSession.dump(new IndentingPrintWriter(charArrayWriter, "    "));
        if (this.mHistoricalSessions.size() > 500) {
            Slog.d("PackageInstaller", "Historical sessions size reaches threshold, clear the oldest");
            this.mHistoricalSessions.subList(0, 400).clear();
        }
        this.mHistoricalSessions.add(charArrayWriter.toString());
        int installerUid = packageInstallerSession.getInstallerUid();
        SparseIntArray sparseIntArray = this.mHistoricalSessionsByInstaller;
        sparseIntArray.put(installerUid, sparseIntArray.get(installerUid) + 1);
    }

    public final boolean writeSessionsLocked() {
        if (LOGD) {
            Slog.v("PackageInstaller", "writeSessionsLocked()");
        }
        FileOutputStream fileOutputStream = null;
        try {
            FileOutputStream startWrite = this.mSessionsFile.startWrite();
            try {
                TypedXmlSerializer resolveSerializer = Xml.resolveSerializer(startWrite);
                resolveSerializer.startDocument((String) null, Boolean.TRUE);
                resolveSerializer.startTag((String) null, "sessions");
                int size = this.mSessions.size();
                for (int i = 0; i < size; i++) {
                    ((PackageInstallerSession) this.mSessions.valueAt(i)).write(resolveSerializer, this.mSessionsDir);
                }
                resolveSerializer.endTag((String) null, "sessions");
                resolveSerializer.endDocument();
                this.mSessionsFile.finishWrite(startWrite);
                return true;
            } catch (IOException unused) {
                fileOutputStream = startWrite;
                if (fileOutputStream != null) {
                    this.mSessionsFile.failWrite(fileOutputStream);
                }
                return false;
            }
        } catch (IOException unused2) {
        }
    }

    public final File buildAppIconFile(int i) {
        return new File(this.mSessionsDir, "app_icon." + i + ".png");
    }

    public int createSession(PackageInstaller.SessionParams sessionParams, String str, String str2, int i) {
        try {
            return createSessionInternal(sessionParams, str, str2, i);
        } catch (IOException e) {
            throw ExceptionUtils.wrap(e);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:304:0x0523  */
    /* JADX WARN: Removed duplicated region for block: B:307:0x052c  */
    /* JADX WARN: Removed duplicated region for block: B:318:0x05ae A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:330:0x0528  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int createSessionInternal(PackageInstaller.SessionParams sessionParams, String str, String str2, int i) {
        int allocateSessionIdLocked;
        File file;
        String str3;
        int packageUid;
        DevicePolicyManagerInternal devicePolicyManagerInternal;
        int i2;
        long clearCallingIdentity;
        int launcherLargeIconSize;
        int launcherLargeIconSize2;
        String[] packagesForUid;
        ApplicationInfo applicationInfo;
        int callingUid = Binder.getCallingUid();
        Computer snapshotComputer = this.mPm.snapshotComputer();
        snapshotComputer.enforceCrossUserPermission(callingUid, i, true, true, "createSession");
        if (this.mPm.isUserRestricted(i, "no_install_apps")) {
            throw new SecurityException("User restriction prevents installing");
        }
        if (sessionParams.dataLoaderParams != null && this.mContext.checkCallingOrSelfPermission("com.android.permission.USE_INSTALLER_V2") != 0) {
            throw new SecurityException("You need the com.android.permission.USE_INSTALLER_V2 permission to use a data loader");
        }
        if (!isFromApprovedInstaller(callingUid, i)) {
            Log.e("PackageInstaller", "not an approved installer");
            throw new IOException("not an approved installer");
        }
        Log.i("PackageInstaller", " getting through the check " + sessionParams + " user id " + i);
        if (sessionParams.installReason == 5 && this.mContext.checkCallingOrSelfPermission("android.permission.MANAGE_ROLLBACKS") != 0 && this.mContext.checkCallingOrSelfPermission("android.permission.TEST_MANAGE_ROLLBACKS") != 0) {
            throw new SecurityException("INSTALL_REASON_ROLLBACK requires the MANAGE_ROLLBACKS permission or the TEST_MANAGE_ROLLBACKS permission");
        }
        String str4 = sessionParams.appPackageName;
        if (str4 != null && !isValidPackageName(str4)) {
            sessionParams.appPackageName = null;
        }
        sessionParams.appLabel = (String) TextUtils.trimToSize(sessionParams.appLabel, 1000);
        String str5 = sessionParams.installerPackageName;
        if (str5 != null && !isValidPackageName(str5)) {
            sessionParams.installerPackageName = null;
        }
        String str6 = (str == null || isValidPackageName(str)) ? str : null;
        String str7 = sessionParams.installerPackageName;
        if (str7 == null) {
            str7 = str6;
        }
        if (PackageManagerServiceUtils.isRootOrShell(callingUid) || PackageInstallerSession.isSystemDataLoaderInstallation(sessionParams) || PackageManagerServiceUtils.isAdoptedShell(callingUid, this.mContext)) {
            sessionParams.installFlags |= 32;
            str6 = "com.android.shell";
        } else {
            if (callingUid != 1000) {
                this.mAppOps.checkPackage(callingUid, str6);
            }
            if (!TextUtils.equals(str7, str6) && this.mContext.checkCallingOrSelfPermission("android.permission.INSTALL_PACKAGES") != 0) {
                this.mAppOps.checkPackage(callingUid, str7);
            }
            int i3 = (sessionParams.installFlags & (-33) & (-65)) | 2;
            sessionParams.installFlags = i3;
            if ((i3 & 65536) != 0 && !this.mPm.isCallerVerifier(snapshotComputer, callingUid)) {
                sessionParams.installFlags &= -65537;
            }
            if (this.mContext.checkCallingOrSelfPermission("android.permission.INSTALL_TEST_ONLY_PACKAGE") != 0) {
                sessionParams.installFlags &= -5;
            }
        }
        String str8 = str6;
        if (PMRune.PM_INSTALL_TO_SDCARD && sessionParams.sizeBytes > 0 && AsecInstallHelper.canInstallOnExternal(str8, sessionParams.installLocation, i)) {
            String str9 = sessionParams.appPackageName;
            if (!((str9 == null || (applicationInfo = snapshotComputer.getApplicationInfo(str9, 0L, 0)) == null || (applicationInfo.flags & 1) == 0) ? false : true)) {
                Slog.w("PackageInstaller", "App size: " + sessionParams.sizeBytes);
                sessionParams.setSize(sessionParams.sizeBytes * 2);
                clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    if (InstallLocationUtils.fitsOnExternal(this.mContext, sessionParams)) {
                        sessionParams.installFlags |= 8;
                        sessionParams.installLocation |= 8;
                        Slog.w("PackageInstaller", "modification done for package " + sessionParams.appPackageName);
                    } else if (sessionParams.sizeBytes > 0) {
                        Context context = this.mContext;
                        String str10 = sessionParams.appLabel;
                        if (str10 == null) {
                            str10 = "Application";
                        }
                        Notification buildNotification = buildNotification(context, str10);
                        if (buildNotification != null) {
                            NotificationManager notificationManager = (NotificationManager) this.mContext.getSystemService("notification");
                            clearCallingIdentity = Binder.clearCallingIdentity();
                            notificationManager.notify(str8, 23, buildNotification);
                        }
                    }
                    sessionParams.setSize(sessionParams.sizeBytes / 2);
                } finally {
                }
            }
        }
        int i4 = sessionParams.originatingUid;
        String str11 = (i4 == -1 || i4 == callingUid || (packagesForUid = snapshotComputer.getPackagesForUid(i4)) == null || packagesForUid.length <= 0) ? null : packagesForUid[0];
        if (Build.IS_DEBUGGABLE || PackageManagerServiceUtils.isSystemOrRoot(callingUid)) {
            sessionParams.installFlags |= 1048576;
        } else {
            sessionParams.installFlags &= -1048577;
        }
        if (this.mDisableVerificationForUid != -1) {
            if (callingUid == this.mDisableVerificationForUid) {
                sessionParams.installFlags |= 524288;
            } else {
                sessionParams.installFlags &= -524289;
            }
            this.mDisableVerificationForUid = -1;
        } else {
            int i5 = sessionParams.installFlags;
            if ((i5 & 36) != 36) {
                sessionParams.installFlags = i5 & (-524289);
            }
        }
        boolean z = (sessionParams.installFlags & IInstalld.FLAG_CLEAR_APP_DATA_KEEP_ART_PROFILES) != 0;
        if (z) {
            if (this.mContext.checkCallingOrSelfPermission("android.permission.INSTALL_PACKAGE_UPDATES") == -1 && this.mContext.checkCallingOrSelfPermission("android.permission.INSTALL_PACKAGES") == -1) {
                throw new SecurityException("Not allowed to perform APEX updates");
            }
        } else if (sessionParams.isStaged) {
            this.mContext.enforceCallingOrSelfPermission("android.permission.INSTALL_PACKAGES", "PackageInstaller");
        }
        if (z) {
            if (!this.mApexManager.isApexSupported()) {
                throw new IllegalArgumentException("This device doesn't support the installation of APEX files");
            }
            if (sessionParams.isMultiPackage) {
                throw new IllegalArgumentException("A multi-session can't be set as APEX.");
            }
            if (PackageManagerServiceUtils.isSystemOrRootOrShell(callingUid) || this.mBypassNextAllowedApexUpdateCheck) {
                sessionParams.installFlags |= 8388608;
            } else {
                sessionParams.installFlags &= -8388609;
            }
        }
        if (Binder.getCallingPid() != Process.myPid()) {
            clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                if (this.mPm.isUserRestricted(0, "no_non_market_app_by_knox")) {
                    Log.i("PackageInstaller", "UserManager.DISALLOW_NON_MARKET_APP_BY_KNOX : true");
                    if (str8 != null && (str8.equals("com.google.android.packageinstaller") || str8.equals("com.samsung.android.packageinstaller"))) {
                        IRestrictionPolicy asInterface = IRestrictionPolicy.Stub.asInterface(ServiceManager.getService("restriction_policy"));
                        if (asInterface != null) {
                            try {
                                asInterface.showRestrictionToast(this.mContext.getString(R.string.cfTemplateRegistered));
                            } catch (RemoteException unused) {
                            }
                        }
                        throw new IOException("Admin doesn't allow installation from unknown sources");
                    }
                    if (snapshotComputer.checkUidPermission("android.permission.INSTALL_PACKAGES", callingUid) != 0 && snapshotComputer.checkUidPermission("android.permission.INSTALL_PACKAGE_UPDATES", callingUid) != 0) {
                        throw new IOException("Admin doesn't allow installation from unknown sources");
                    }
                    if (callingUid == 2000) {
                        throw new IOException("Admin doesn't allow installation from unknown sources");
                    }
                } else {
                    Log.i("PackageInstaller", "UserManager.DISALLOW_NON_MARKET_APP_BY_KNOX : false");
                }
            } finally {
            }
        }
        if ((sessionParams.installFlags & 16777216) != 0 && !PackageManagerServiceUtils.isSystemOrRootOrShell(callingUid) && !Build.IS_DEBUGGABLE) {
            sessionParams.installFlags &= -16777217;
        }
        if ((sessionParams.installFlags & IInstalld.FLAG_FREE_CACHE_DEFY_TARGET_FREE_BYTES) != 0 && !PackageManagerServiceUtils.isSystemOrRootOrShell(callingUid) && (snapshotComputer.getFlagsForUid(callingUid) & 1) == 0) {
            throw new SecurityException("Only system apps could use the PackageManager.INSTALL_INSTANT_APP flag.");
        }
        if (sessionParams.isStaged && !PackageManagerServiceUtils.isSystemOrRootOrShell(callingUid) && !this.mBypassNextStagedInstallerCheck && !isStagedInstallerAllowed(str7)) {
            throw new SecurityException("Installer not allowed to commit staged install");
        }
        if (z && !PackageManagerServiceUtils.isSystemOrRootOrShell(callingUid) && !this.mBypassNextStagedInstallerCheck && !isStagedInstallerAllowed(str7)) {
            throw new SecurityException("Installer not allowed to commit non-staged APEX install");
        }
        this.mBypassNextStagedInstallerCheck = false;
        this.mBypassNextAllowedApexUpdateCheck = false;
        if (!sessionParams.isMultiPackage) {
            boolean z2 = this.mContext.checkCallingOrSelfPermission("android.permission.INSTALL_GRANT_RUNTIME_PERMISSIONS") == 0;
            if ((sessionParams.installFlags & 256) != 0 && !z2) {
                throw new SecurityException("You need the android.permission.INSTALL_GRANT_RUNTIME_PERMISSIONS permission to use the PackageManager.INSTALL_GRANT_ALL_REQUESTED_PERMISSIONS flag");
            }
            ArrayMap permissionStates = sessionParams.getPermissionStates();
            if (!permissionStates.isEmpty() && !z2) {
                for (int i6 = 0; i6 < permissionStates.size(); i6++) {
                    if (!INSTALLER_CHANGEABLE_APP_OP_PERMISSIONS.contains((String) permissionStates.keyAt(i6))) {
                        throw new SecurityException("You need the android.permission.INSTALL_GRANT_RUNTIME_PERMISSIONS permission to grant runtime permissions for a session");
                    }
                }
            }
            if (sessionParams.appIcon == null || (sessionParams.appIcon.getWidth() <= (launcherLargeIconSize2 = (launcherLargeIconSize = ((ActivityManager) this.mContext.getSystemService("activity")).getLauncherLargeIconSize()) * 2) && sessionParams.appIcon.getHeight() <= launcherLargeIconSize2)) {
                i2 = 1;
            } else {
                i2 = 1;
                sessionParams.appIcon = Bitmap.createScaledBitmap(sessionParams.appIcon, launcherLargeIconSize, launcherLargeIconSize, true);
            }
            int i7 = sessionParams.mode;
            if (i7 != i2 && i7 != 2) {
                throw new IllegalArgumentException("Invalid install mode: " + sessionParams.mode);
            }
            int i8 = sessionParams.installFlags;
            if ((i8 & 16) != 0) {
                if (!InstallLocationUtils.fitsOnInternal(this.mContext, sessionParams)) {
                    throw new IOException("No suitable internal storage available");
                }
            } else if ((i8 & 8) != 0) {
                clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    if (!InstallLocationUtils.fitsOnExternal(this.mContext, sessionParams)) {
                        throw new IOException("No suitable external storage available");
                    }
                } finally {
                }
            } else if ((i8 & 512) != 0) {
                sessionParams.installFlags = i8 | 16;
            } else {
                sessionParams.installFlags = i8 | 16;
                clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    sessionParams.volumeUuid = InstallLocationUtils.resolveInstallVolume(this.mContext, sessionParams);
                } finally {
                }
            }
        }
        synchronized (this.mSessions) {
            int sessionCount = getSessionCount(this.mSessions, callingUid);
            if (this.mContext.checkCallingOrSelfPermission("android.permission.INSTALL_PACKAGES") == 0) {
                if (sessionCount >= 1024) {
                    throw new IllegalStateException("Too many active sessions for UID " + callingUid);
                }
            } else if (sessionCount >= 50) {
                throw new IllegalStateException("Too many active sessions for UID " + callingUid);
            }
            if (this.mHistoricalSessionsByInstaller.get(callingUid) >= 1048576) {
                throw new IllegalStateException("Too many historical sessions for UID " + callingUid);
            }
            allocateSessionIdLocked = allocateSessionIdLocked();
        }
        long currentTimeMillis = System.currentTimeMillis();
        if (sessionParams.isMultiPackage) {
            file = null;
        } else if ((sessionParams.installFlags & 16) != 0) {
            file = buildSessionDir(allocateSessionIdLocked, sessionParams);
        } else {
            str3 = buildExternalStageCid(allocateSessionIdLocked);
            file = null;
            if (callingUid != 1000 && !this.mPmInternal.isPlatformSigned(str8)) {
                sessionParams.sessionFlags &= -33554433;
            }
            if (sessionParams.forceQueryableOverride && !PackageManagerServiceUtils.isRootOrShell(callingUid)) {
                sessionParams.forceQueryableOverride = false;
            }
            packageUid = str7 == null ? snapshotComputer.getPackageUid(str7, 0L, i) : -1;
            if (packageUid == -1) {
                str7 = null;
            }
            devicePolicyManagerInternal = (DevicePolicyManagerInternal) LocalServices.getService(DevicePolicyManagerInternal.class);
            if (devicePolicyManagerInternal != null && devicePolicyManagerInternal.isUserOrganizationManaged(i)) {
                sessionParams.installFlags |= 67108864;
            }
            if (!z || this.mContext.checkCallingOrSelfPermission("android.permission.ENFORCE_UPDATE_OWNERSHIP") == -1) {
                sessionParams.installFlags = (-33554433) & sessionParams.installFlags;
            }
            PackageInstallerSession packageInstallerSession = new PackageInstallerSession(this.mInternalCallback, this.mContext, this.mPm, this, this.mSilentUpdatePolicy, this.mInstallThread.getLooper(), this.mStagingManager, allocateSessionIdLocked, i, callingUid, InstallSource.create(str8, str11, str7, packageUid, str7, str2, sessionParams.packageSource), sessionParams, currentTimeMillis, 0L, file, str3, null, null, false, false, false, false, null, -1, false, false, false, 0, "");
            synchronized (this.mSessions) {
                this.mSessions.put(allocateSessionIdLocked, packageInstallerSession);
            }
            this.mPm.addInstallerPackageName(packageInstallerSession.getInstallSource());
            this.mCallbacks.notifySessionCreated(packageInstallerSession.sessionId, packageInstallerSession.userId);
            this.mSettingsWriteRequest.schedule();
            if (LOGD) {
                Slog.d("PackageInstaller", "Created session id=" + allocateSessionIdLocked + " staged=" + sessionParams.isStaged);
            }
            return allocateSessionIdLocked;
        }
        str3 = null;
        if (callingUid != 1000) {
            sessionParams.sessionFlags &= -33554433;
        }
        if (sessionParams.forceQueryableOverride) {
            sessionParams.forceQueryableOverride = false;
        }
        if (str7 == null) {
        }
        if (packageUid == -1) {
        }
        devicePolicyManagerInternal = (DevicePolicyManagerInternal) LocalServices.getService(DevicePolicyManagerInternal.class);
        if (devicePolicyManagerInternal != null) {
            sessionParams.installFlags |= 67108864;
        }
        if (!z) {
        }
        sessionParams.installFlags = (-33554433) & sessionParams.installFlags;
        PackageInstallerSession packageInstallerSession2 = new PackageInstallerSession(this.mInternalCallback, this.mContext, this.mPm, this, this.mSilentUpdatePolicy, this.mInstallThread.getLooper(), this.mStagingManager, allocateSessionIdLocked, i, callingUid, InstallSource.create(str8, str11, str7, packageUid, str7, str2, sessionParams.packageSource), sessionParams, currentTimeMillis, 0L, file, str3, null, null, false, false, false, false, null, -1, false, false, false, 0, "");
        synchronized (this.mSessions) {
        }
    }

    public final void assertIsAllowedCaller() {
        int callingUid = Binder.getCallingUid();
        if (!PackageManagerServiceUtils.isSystemOrRootOrShell(callingUid) && this.mPm.snapshotComputer().checkUidPermission("android.permission.INSTALL_PACKAGES", callingUid) != 0) {
            throw new SecurityException("Caller not authorised to use this method !!");
        }
    }

    public final File getNextCodePath(File file) {
        File file2;
        SecureRandom secureRandom = new SecureRandom();
        byte[] bArr = new byte[16];
        do {
            secureRandom.nextBytes(bArr);
            file2 = new File(file, Base64.encodeToString(bArr, 10));
        } while (file2.exists());
        return file2;
    }

    public final File prepareCustomCopyDir() {
        try {
            File file = new File(Environment.getDataDirectory(), "apk-tmp");
            if (!file.exists()) {
                Os.mkdir(file.getAbsolutePath(), 511);
                Os.chmod(file.getAbsolutePath(), 511);
            }
            File file2 = new File(file, Integer.toString(Binder.getCallingUid()));
            if (!file2.exists()) {
                Os.mkdir(file2.getAbsolutePath(), 511);
                Os.chmod(file2.getAbsolutePath(), 511);
            }
            File nextCodePath = getNextCodePath(file2);
            Os.mkdir(nextCodePath.getAbsolutePath(), 511);
            Os.chmod(nextCodePath.getAbsolutePath(), 511);
            return nextCodePath;
        } catch (ErrnoException e) {
            e.printStackTrace();
            throw new IOException("Failed to prepare temp session dir", e);
        }
    }

    public ParcelFileDescriptor requestCopy(String str, long j) {
        File prepareCustomCopyDir;
        assertIsAllowedCaller();
        try {
            synchronized (this.mCreateLock) {
                prepareCustomCopyDir = prepareCustomCopyDir();
            }
            if (!FileUtils.isValidExtFilename(str)) {
                throw new IllegalArgumentException("Invalid name: " + str);
            }
            File file = new File(prepareCustomCopyDir, str);
            try {
                ParcelFileDescriptor parcelFileDescriptor = new ParcelFileDescriptor(Os.open(file.getAbsolutePath(), OsConstants.O_CREAT | OsConstants.O_WRONLY, 438));
                Os.chmod(file.getAbsolutePath(), 438);
                if (prepareCustomCopyDir != null && j > 0) {
                    ((StorageManager) this.mContext.getSystemService(StorageManager.class)).allocateBytes(parcelFileDescriptor.getFileDescriptor(), j, 0);
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

    public final boolean isStagedInstallerAllowed(String str) {
        return SystemConfig.getInstance().getWhitelistedStagedInstallers().contains(str);
    }

    public void updateSessionAppIcon(int i, Bitmap bitmap) {
        int launcherLargeIconSize;
        int launcherLargeIconSize2;
        synchronized (this.mSessions) {
            PackageInstallerSession packageInstallerSession = (PackageInstallerSession) this.mSessions.get(i);
            if (packageInstallerSession == null || !isCallingUidOwner(packageInstallerSession)) {
                throw new SecurityException("Caller has no access to session " + i);
            }
            if (bitmap != null && (bitmap.getWidth() > (launcherLargeIconSize2 = (launcherLargeIconSize = ((ActivityManager) this.mContext.getSystemService("activity")).getLauncherLargeIconSize()) * 2) || bitmap.getHeight() > launcherLargeIconSize2)) {
                bitmap = Bitmap.createScaledBitmap(bitmap, launcherLargeIconSize, launcherLargeIconSize, true);
            }
            PackageInstaller.SessionParams sessionParams = packageInstallerSession.params;
            sessionParams.appIcon = bitmap;
            sessionParams.appIconLastModified = -1L;
            this.mInternalCallback.onSessionBadgingChanged(packageInstallerSession);
        }
    }

    public void updateSessionAppLabel(int i, String str) {
        synchronized (this.mSessions) {
            PackageInstallerSession packageInstallerSession = (PackageInstallerSession) this.mSessions.get(i);
            if (packageInstallerSession == null || !isCallingUidOwner(packageInstallerSession)) {
                throw new SecurityException("Caller has no access to session " + i);
            }
            if (!str.equals(packageInstallerSession.params.appLabel)) {
                packageInstallerSession.params.appLabel = str;
                this.mInternalCallback.onSessionBadgingChanged(packageInstallerSession);
            }
        }
    }

    public void abandonSession(int i) {
        synchronized (this.mSessions) {
            PackageInstallerSession packageInstallerSession = (PackageInstallerSession) this.mSessions.get(i);
            if (packageInstallerSession == null || !isCallingUidOwner(packageInstallerSession)) {
                throw new SecurityException("Caller has no access to session " + i);
            }
            packageInstallerSession.abandon();
        }
    }

    public IPackageInstallerSession openSession(int i) {
        try {
            return openSessionInternal(i);
        } catch (IOException e) {
            throw ExceptionUtils.wrap(e);
        }
    }

    public IPackageInstallerSession openSessionQuick(int i, String str) {
        try {
            return openSessionInternalQuick(i, str);
        } catch (IOException e) {
            throw ExceptionUtils.wrap(e);
        }
    }

    public final boolean checkOpenSessionAccess(PackageInstallerSession packageInstallerSession) {
        if (packageInstallerSession == null) {
            return false;
        }
        if (isCallingUidOwner(packageInstallerSession)) {
            return true;
        }
        return packageInstallerSession.isSealed() && this.mContext.checkCallingOrSelfPermission("android.permission.PACKAGE_VERIFICATION_AGENT") == 0;
    }

    public final IPackageInstallerSession openSessionInternal(int i) {
        PackageInstallerSession packageInstallerSession;
        synchronized (this.mSessions) {
            packageInstallerSession = (PackageInstallerSession) this.mSessions.get(i);
            if (!checkOpenSessionAccess(packageInstallerSession)) {
                throw new SecurityException("Caller has no access to session " + i);
            }
            packageInstallerSession.open();
        }
        return packageInstallerSession;
    }

    public final IPackageInstallerSession openSessionInternalQuick(int i, String str) {
        PackageInstallerSession packageInstallerSession;
        synchronized (this.mSessions) {
            packageInstallerSession = (PackageInstallerSession) this.mSessions.get(i);
            if (!checkOpenSessionAccess(packageInstallerSession)) {
                throw new SecurityException("Caller has no access to session " + i);
            }
            packageInstallerSession.openQuick(str);
        }
        return packageInstallerSession;
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

    public static boolean isStageName(String str) {
        return (str.startsWith("vmdl") && str.endsWith(".tmp")) || (str.startsWith("smdl") && str.endsWith(".tmp")) || str.startsWith("smdl2tmp");
    }

    public static int tryParseSessionId(String str) {
        if (!str.startsWith("vmdl") || !str.endsWith(".tmp")) {
            throw new IllegalArgumentException("Not a temporary session directory");
        }
        return Integer.parseInt(str.substring(4, str.length() - 4));
    }

    public static boolean isValidPackageName(String str) {
        return str.length() <= 255 && FrameworkParsingPackageUtils.validateName(str, false, true) == null;
    }

    public final File getTmpSessionDir(String str) {
        return Environment.getDataAppDirectory(str);
    }

    public final File buildTmpSessionDir(int i, String str) {
        return new File(getTmpSessionDir(str), "vmdl" + i + ".tmp");
    }

    public final File buildSessionDir(int i, PackageInstaller.SessionParams sessionParams) {
        if (sessionParams.isStaged || (sessionParams.installFlags & IInstalld.FLAG_CLEAR_APP_DATA_KEEP_ART_PROFILES) != 0) {
            return new File(Environment.getDataStagingDirectory(sessionParams.volumeUuid), "session_" + i);
        }
        File buildTmpSessionDir = buildTmpSessionDir(i, sessionParams.volumeUuid);
        if (!DEBUG || Objects.equals(Integer.valueOf(tryParseSessionId(buildTmpSessionDir.getName())), Integer.valueOf(i))) {
            return buildTmpSessionDir;
        }
        throw new RuntimeException("session folder format is off: " + buildTmpSessionDir.getName() + " (" + i + ")");
    }

    public static void prepareStageDir(File file) {
        if (file.exists()) {
            throw new IOException("Session dir already exists: " + file);
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
            StringBuilder sb = new StringBuilder();
            sb.append("Failed to SELinux.restorecon session dir, path: [");
            sb.append(canonicalPath);
            sb.append("], ctx: [");
            sb.append(fileSelabelLookup);
            sb.append("]. Retrying via SELinux.fileSelabelLookup/SELinux.setFileContext: ");
            sb.append(fileContext ? "SUCCESS" : "FAILURE");
            Slog.e("PackageInstaller", sb.toString());
            if (fileContext) {
                return;
            }
            throw new IOException("Failed to restorecon session dir: " + file);
        } catch (ErrnoException e) {
            throw new IOException("Failed to prepare session dir: " + file, e);
        }
    }

    public static void prepareStageDirQuick(File file, String str) {
        if (file.exists()) {
            throw new IOException("Session dir already exists: " + file);
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
        StringBuilder sb = new StringBuilder();
        sb.append("Failed to SELinux.restorecon session dir, path: [");
        sb.append(str);
        sb.append("], ctx: [");
        sb.append(fileSelabelLookup);
        sb.append("]. Retrying via SELinux.fileSelabelLookup/SELinux.setFileContext: ");
        sb.append(fileContext ? "SUCCESS" : "FAILURE");
        Slog.e("PackageInstaller", sb.toString());
        if (fileContext) {
            return;
        }
        throw new IOException("Failed to restorecon session dir: " + str);
    }

    public final String buildExternalStageCid(int i) {
        return "smdl" + i + ".tmp";
    }

    public static void prepareExternalStageCid(String str, long j) {
        if (PackageHelperExt.createSdDir(j, str, AsecInstallHelper.getEncryptKey(), 1000, true) != null) {
            return;
        }
        throw new IOException("Failed to create session cid: " + str);
    }

    /* renamed from: shouldFilterSession, reason: merged with bridge method [inline-methods] and merged with bridge method [inline-methods] */
    public final boolean lambda$getStagedSessions$1(Computer computer, int i, PackageInstaller.SessionInfo sessionInfo) {
        return (sessionInfo == null || i == sessionInfo.getInstallerUid() || computer.canQueryPackage(i, sessionInfo.getAppPackageName())) ? false : true;
    }

    public PackageInstaller.SessionInfo getSessionInfo(int i) {
        PackageInstaller.SessionInfo generateInfoForCaller;
        int callingUid = Binder.getCallingUid();
        synchronized (this.mSessions) {
            PackageInstallerSession packageInstallerSession = (PackageInstallerSession) this.mSessions.get(i);
            generateInfoForCaller = (packageInstallerSession == null || (packageInstallerSession.isStaged() && packageInstallerSession.isDestroyed())) ? null : packageInstallerSession.generateInfoForCaller(true, callingUid);
        }
        if (lambda$getStagedSessions$1(this.mPm.snapshotComputer(), callingUid, generateInfoForCaller)) {
            return null;
        }
        return generateInfoForCaller;
    }

    public ParceledListSlice getStagedSessions() {
        final int callingUid = Binder.getCallingUid();
        ArrayList arrayList = new ArrayList();
        synchronized (this.mSessions) {
            for (int i = 0; i < this.mSessions.size(); i++) {
                PackageInstallerSession packageInstallerSession = (PackageInstallerSession) this.mSessions.valueAt(i);
                if (packageInstallerSession.isStaged() && !packageInstallerSession.isDestroyed()) {
                    arrayList.add(packageInstallerSession.generateInfoForCaller(false, callingUid));
                }
            }
        }
        final Computer snapshotComputer = this.mPm.snapshotComputer();
        arrayList.removeIf(new Predicate() { // from class: com.android.server.pm.PackageInstallerService$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$getStagedSessions$1;
                lambda$getStagedSessions$1 = PackageInstallerService.this.lambda$getStagedSessions$1(snapshotComputer, callingUid, (PackageInstaller.SessionInfo) obj);
                return lambda$getStagedSessions$1;
            }
        });
        return new ParceledListSlice(arrayList);
    }

    public ParceledListSlice getAllSessions(int i) {
        final int callingUid = Binder.getCallingUid();
        final Computer snapshotComputer = this.mPm.snapshotComputer();
        snapshotComputer.enforceCrossUserPermission(callingUid, i, true, false, "getAllSessions");
        ArrayList arrayList = new ArrayList();
        synchronized (this.mSessions) {
            for (int i2 = 0; i2 < this.mSessions.size(); i2++) {
                PackageInstallerSession packageInstallerSession = (PackageInstallerSession) this.mSessions.valueAt(i2);
                if (packageInstallerSession.userId == i && !packageInstallerSession.hasParentSessionId() && (!packageInstallerSession.isStaged() || !packageInstallerSession.isDestroyed())) {
                    arrayList.add(packageInstallerSession.generateInfoForCaller(false, callingUid));
                }
            }
        }
        arrayList.removeIf(new Predicate() { // from class: com.android.server.pm.PackageInstallerService$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$getAllSessions$2;
                lambda$getAllSessions$2 = PackageInstallerService.this.lambda$getAllSessions$2(snapshotComputer, callingUid, (PackageInstaller.SessionInfo) obj);
                return lambda$getAllSessions$2;
            }
        });
        return new ParceledListSlice(arrayList);
    }

    public ParceledListSlice getMySessions(String str, int i) {
        Computer snapshotComputer = this.mPm.snapshotComputer();
        int callingUid = Binder.getCallingUid();
        snapshotComputer.enforceCrossUserPermission(callingUid, i, true, false, "getMySessions");
        this.mAppOps.checkPackage(callingUid, str);
        ArrayList arrayList = new ArrayList();
        synchronized (this.mSessions) {
            for (int i2 = 0; i2 < this.mSessions.size(); i2++) {
                PackageInstallerSession packageInstallerSession = (PackageInstallerSession) this.mSessions.valueAt(i2);
                PackageInstaller.SessionInfo generateInfoForCaller = packageInstallerSession.generateInfoForCaller(false, 1000);
                if (Objects.equals(generateInfoForCaller.getInstallerPackageName(), str) && packageInstallerSession.userId == i && !packageInstallerSession.hasParentSessionId() && isCallingUidOwner(packageInstallerSession)) {
                    arrayList.add(generateInfoForCaller);
                }
            }
        }
        return new ParceledListSlice(arrayList);
    }

    public void uninstall(VersionedPackage versionedPackage, String str, int i, IntentSender intentSender, int i2) {
        Computer snapshotComputer = this.mPm.snapshotComputer();
        int callingUid = Binder.getCallingUid();
        snapshotComputer.enforceCrossUserPermission(callingUid, i2, true, true, "uninstall");
        if (!PackageManagerServiceUtils.isRootOrShell(callingUid)) {
            this.mAppOps.checkPackage(callingUid, str);
        }
        DevicePolicyManagerInternal devicePolicyManagerInternal = (DevicePolicyManagerInternal) LocalServices.getService(DevicePolicyManagerInternal.class);
        boolean z = devicePolicyManagerInternal != null && devicePolicyManagerInternal.canSilentlyInstallPackage(str, callingUid);
        PackageDeleteObserverAdapter packageDeleteObserverAdapter = new PackageDeleteObserverAdapter(this.mContext, intentSender, versionedPackage.getPackageName(), z, i2);
        if (this.mContext.checkCallingOrSelfPermission("android.permission.DELETE_PACKAGES") == 0) {
            this.mPm.deletePackageVersioned(versionedPackage, packageDeleteObserverAdapter.getBinder(), i2, i);
            return;
        }
        if (z) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                this.mPm.deletePackageVersioned(versionedPackage, packageDeleteObserverAdapter.getBinder(), i2, i);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                DevicePolicyEventLogger.createEvent(113).setAdmin(str).write();
                return;
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }
        if (snapshotComputer.getApplicationInfo(str, 0L, i2).targetSdkVersion >= 28) {
            this.mContext.enforceCallingOrSelfPermission("android.permission.REQUEST_DELETE_PACKAGES", null);
        }
        Intent intent = new Intent("android.intent.action.UNINSTALL_PACKAGE");
        intent.setData(Uri.fromParts("package", versionedPackage.getPackageName(), null));
        intent.putExtra("android.content.pm.extra.CALLBACK", (Parcelable) new PackageManager.UninstallCompleteCallback(packageDeleteObserverAdapter.getBinder().asBinder()));
        packageDeleteObserverAdapter.onUserActionRequired(intent);
    }

    public void uninstallExistingPackage(VersionedPackage versionedPackage, String str, IntentSender intentSender, int i) {
        int callingUid = Binder.getCallingUid();
        this.mContext.enforceCallingOrSelfPermission("android.permission.DELETE_PACKAGES", null);
        this.mPm.snapshotComputer().enforceCrossUserPermission(callingUid, i, true, true, "uninstall");
        if (!PackageManagerServiceUtils.isRootOrShell(callingUid)) {
            this.mAppOps.checkPackage(callingUid, str);
        }
        this.mPm.deleteExistingPackageAsUser(versionedPackage, new PackageDeleteObserverAdapter(this.mContext, intentSender, versionedPackage.getPackageName(), false, i).getBinder(), i);
    }

    public void installExistingPackage(String str, int i, int i2, IntentSender intentSender, int i3, List list) {
        Pair installExistingPackageAsUser = new InstallPackageHelper(this.mPm).installExistingPackageAsUser(str, i3, i, i2, list, intentSender);
        int intValue = ((Integer) installExistingPackageAsUser.first).intValue();
        IntentSender intentSender2 = (IntentSender) installExistingPackageAsUser.second;
        if (intentSender2 != null) {
            InstallPackageHelper.onInstallComplete(intValue, this.mContext, intentSender2);
        }
    }

    public void setPermissionsResult(int i, boolean z) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.INSTALL_PACKAGES", "PackageInstaller");
        synchronized (this.mSessions) {
            PackageInstallerSession packageInstallerSession = (PackageInstallerSession) this.mSessions.get(i);
            if (packageInstallerSession != null) {
                packageInstallerSession.setPermissionsResult(z);
            }
        }
    }

    public final boolean isValidForInstallConstraints(PackageStateInternal packageStateInternal, String str) {
        return TextUtils.equals(packageStateInternal.getInstallSource().mInstallerPackageName, str) || TextUtils.equals(packageStateInternal.getInstallSource().mUpdateOwnerPackageName, str);
    }

    public final CompletableFuture checkInstallConstraintsInternal(String str, List list, PackageInstaller.InstallConstraints installConstraints, long j) {
        Objects.requireNonNull(list);
        Objects.requireNonNull(installConstraints);
        Computer snapshotComputer = this.mPm.snapshotComputer();
        int callingUid = Binder.getCallingUid();
        if (!TextUtils.equals(snapshotComputer.getNameForUid(callingUid), str)) {
            throw new SecurityException("The installerPackageName set by the caller doesn't match the caller's own package name.");
        }
        if (!PackageManagerServiceUtils.isSystemOrRootOrShell(callingUid)) {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                String str2 = (String) it.next();
                PackageStateInternal packageStateInternal = snapshotComputer.getPackageStateInternal(str2);
                if (packageStateInternal == null || !isValidForInstallConstraints(packageStateInternal, str)) {
                    throw new SecurityException("Caller has no access to package " + str2);
                }
            }
        }
        return this.mGentleUpdateHelper.checkInstallConstraints(list, installConstraints, j);
    }

    public void checkInstallConstraints(String str, List list, PackageInstaller.InstallConstraints installConstraints, final RemoteCallback remoteCallback) {
        Objects.requireNonNull(remoteCallback);
        checkInstallConstraintsInternal(str, list, installConstraints, 0L).thenAccept(new Consumer() { // from class: com.android.server.pm.PackageInstallerService$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                PackageInstallerService.lambda$checkInstallConstraints$3(remoteCallback, (PackageInstaller.InstallConstraintsResult) obj);
            }
        });
    }

    public static /* synthetic */ void lambda$checkInstallConstraints$3(RemoteCallback remoteCallback, PackageInstaller.InstallConstraintsResult installConstraintsResult) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(KnoxCustomManagerService.SPCM_KEY_RESULT, installConstraintsResult);
        remoteCallback.sendResult(bundle);
    }

    public void waitForInstallConstraints(String str, final List list, final PackageInstaller.InstallConstraints installConstraints, final IntentSender intentSender, long j) {
        Objects.requireNonNull(intentSender);
        if (j < 0 || j > 604800000) {
            throw new IllegalArgumentException("Invalid timeoutMillis=" + j);
        }
        checkInstallConstraintsInternal(str, list, installConstraints, j).thenAccept(new Consumer() { // from class: com.android.server.pm.PackageInstallerService$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                PackageInstallerService.this.lambda$waitForInstallConstraints$4(list, installConstraints, intentSender, (PackageInstaller.InstallConstraintsResult) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$waitForInstallConstraints$4(List list, PackageInstaller.InstallConstraints installConstraints, IntentSender intentSender, PackageInstaller.InstallConstraintsResult installConstraintsResult) {
        Intent intent = new Intent();
        intent.putExtra("android.intent.extra.PACKAGES", (String[]) list.toArray(new String[0]));
        intent.putExtra("android.content.pm.extra.INSTALL_CONSTRAINTS", installConstraints);
        intent.putExtra("android.content.pm.extra.INSTALL_CONSTRAINTS_RESULT", installConstraintsResult);
        try {
            BroadcastOptions makeBasic = BroadcastOptions.makeBasic();
            makeBasic.setPendingIntentBackgroundActivityLaunchAllowed(false);
            intentSender.sendIntent(this.mContext, 0, intent, null, null, null, makeBasic.toBundle());
        } catch (IntentSender.SendIntentException unused) {
        }
    }

    public void registerCallback(IPackageInstallerCallback iPackageInstallerCallback, final int i) {
        this.mPm.snapshotComputer().enforceCrossUserPermission(Binder.getCallingUid(), i, true, false, "registerCallback");
        registerCallback(iPackageInstallerCallback, new IntPredicate() { // from class: com.android.server.pm.PackageInstallerService$$ExternalSyntheticLambda2
            @Override // java.util.function.IntPredicate
            public final boolean test(int i2) {
                boolean lambda$registerCallback$5;
                lambda$registerCallback$5 = PackageInstallerService.lambda$registerCallback$5(i, i2);
                return lambda$registerCallback$5;
            }
        });
    }

    public void registerCallback(IPackageInstallerCallback iPackageInstallerCallback, IntPredicate intPredicate) {
        this.mCallbacks.register(iPackageInstallerCallback, new BroadcastCookie(Binder.getCallingUid(), intPredicate));
    }

    public void unregisterCallback(IPackageInstallerCallback iPackageInstallerCallback) {
        this.mCallbacks.unregister(iPackageInstallerCallback);
    }

    @Override // com.android.server.pm.PackageSessionProvider
    public PackageInstallerSession getSession(int i) {
        PackageInstallerSession packageInstallerSession;
        synchronized (this.mSessions) {
            packageInstallerSession = (PackageInstallerSession) this.mSessions.get(i);
        }
        return packageInstallerSession;
    }

    @Override // com.android.server.pm.PackageSessionProvider
    public PackageSessionVerifier getSessionVerifier() {
        return this.mSessionVerifier;
    }

    public GentleUpdateHelper getGentleUpdateHelper() {
        return this.mGentleUpdateHelper;
    }

    public void bypassNextStagedInstallerCheck(boolean z) {
        if (!PackageManagerServiceUtils.isSystemOrRootOrShell(Binder.getCallingUid())) {
            throw new SecurityException("Caller not allowed to bypass staged installer check");
        }
        this.mBypassNextStagedInstallerCheck = z;
    }

    public void bypassNextAllowedApexUpdateCheck(boolean z) {
        if (!PackageManagerServiceUtils.isSystemOrRootOrShell(Binder.getCallingUid())) {
            throw new SecurityException("Caller not allowed to bypass allowed apex update check");
        }
        this.mBypassNextAllowedApexUpdateCheck = z;
    }

    public void disableVerificationForUid(int i) {
        if (!PackageManagerServiceUtils.isSystemOrRootOrShell(Binder.getCallingUid())) {
            throw new SecurityException("Operation not allowed for caller");
        }
        this.mDisableVerificationForUid = i;
    }

    public void setAllowUnlimitedSilentUpdates(String str) {
        if (!PackageManagerServiceUtils.isSystemOrRootOrShell(Binder.getCallingUid())) {
            throw new SecurityException("Caller not allowed to unlimite silent updates");
        }
        this.mSilentUpdatePolicy.setAllowUnlimitedSilentUpdates(str);
    }

    public void setSilentUpdatesThrottleTime(long j) {
        if (!PackageManagerServiceUtils.isSystemOrRootOrShell(Binder.getCallingUid())) {
            throw new SecurityException("Caller not allowed to set silent updates throttle time");
        }
        this.mSilentUpdatePolicy.setSilentUpdatesThrottleTime(j);
    }

    public static int getSessionCount(SparseArray sparseArray, int i) {
        int size = sparseArray.size();
        int i2 = 0;
        for (int i3 = 0; i3 < size; i3++) {
            if (((PackageInstallerSession) sparseArray.valueAt(i3)).getInstallerUid() == i) {
                i2++;
            }
        }
        return i2;
    }

    public final boolean isCallingUidOwner(PackageInstallerSession packageInstallerSession) {
        int callingUid = Binder.getCallingUid();
        if (callingUid == 0) {
            return true;
        }
        return packageInstallerSession != null && callingUid == packageInstallerSession.getInstallerUid();
    }

    public final boolean shouldFilterSession(Computer computer, int i, int i2) {
        PackageInstallerSession session = getSession(i2);
        return (session == null || i == session.getInstallerUid() || computer.canQueryPackage(i, session.getPackageName())) ? false : true;
    }

    public class PackageDeleteObserverAdapter extends PackageDeleteObserver {
        public final Context mContext;
        public final Notification mNotification;
        public final String mPackageName;
        public final IntentSender mTarget;

        public PackageDeleteObserverAdapter(Context context, IntentSender intentSender, String str, boolean z, int i) {
            this.mContext = context;
            this.mTarget = intentSender;
            this.mPackageName = str;
            if (z) {
                this.mNotification = PackageInstallerService.buildSuccessNotification(context, getDeviceOwnerDeletedPackageMsg(), str, i);
            } else {
                this.mNotification = null;
            }
        }

        public final String getDeviceOwnerDeletedPackageMsg() {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return ((DevicePolicyManager) this.mContext.getSystemService(DevicePolicyManager.class)).getResources().getString("Core.PACKAGE_DELETED_BY_DO", new Supplier() { // from class: com.android.server.pm.PackageInstallerService$PackageDeleteObserverAdapter$$ExternalSyntheticLambda0
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        String lambda$getDeviceOwnerDeletedPackageMsg$0;
                        lambda$getDeviceOwnerDeletedPackageMsg$0 = PackageInstallerService.PackageDeleteObserverAdapter.this.lambda$getDeviceOwnerDeletedPackageMsg$0();
                        return lambda$getDeviceOwnerDeletedPackageMsg$0;
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ String lambda$getDeviceOwnerDeletedPackageMsg$0() {
            return this.mContext.getString(R.string.roamingText11);
        }

        public void onUserActionRequired(Intent intent) {
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
                this.mTarget.sendIntent(this.mContext, 0, intent2, null, null, null, makeBasic.toBundle());
            } catch (IntentSender.SendIntentException unused) {
            }
        }

        public void onPackageDeleted(String str, int i, String str2) {
            if (1 == i && this.mNotification != null) {
                ((NotificationManager) this.mContext.getSystemService("notification")).notify(str, 21, this.mNotification);
            }
            if (this.mTarget == null) {
                return;
            }
            Intent intent = new Intent();
            intent.putExtra("android.content.pm.extra.PACKAGE_NAME", this.mPackageName);
            intent.putExtra("android.content.pm.extra.STATUS", PackageManager.deleteStatusToPublicStatus(i));
            intent.putExtra("android.content.pm.extra.STATUS_MESSAGE", PackageManager.deleteStatusToString(i, str2));
            intent.putExtra("android.content.pm.extra.LEGACY_STATUS", i);
            try {
                BroadcastOptions makeBasic = BroadcastOptions.makeBasic();
                makeBasic.setPendingIntentBackgroundActivityLaunchAllowed(false);
                this.mTarget.sendIntent(this.mContext, 0, intent, null, null, null, makeBasic.toBundle());
            } catch (IntentSender.SendIntentException unused) {
            }
        }
    }

    public static Notification buildSuccessNotification(Context context, String str, String str2, int i) {
        PackageInfo packageInfo;
        try {
            packageInfo = AppGlobals.getPackageManager().getPackageInfo(str2, 67108864L, i);
        } catch (RemoteException unused) {
            packageInfo = null;
        }
        if (packageInfo == null || packageInfo.applicationInfo == null) {
            Slog.w("PackageInstaller", "Notification not built for package: " + str2);
            return null;
        }
        PackageManager packageManager = context.getPackageManager();
        return new Notification.Builder(context, SystemNotificationChannels.DEVICE_ADMIN).setSmallIcon(R.drawable.ic_bt_network_pan).setColor(context.getResources().getColor(R.color.system_notification_accent_color)).setContentTitle(packageInfo.applicationInfo.loadLabel(packageManager)).setContentText(str).setStyle(new Notification.BigTextStyle().bigText(str)).setLargeIcon(ImageUtils.buildScaledBitmap(packageInfo.applicationInfo.loadIcon(packageManager), context.getResources().getDimensionPixelSize(R.dimen.notification_large_icon_width), context.getResources().getDimensionPixelSize(R.dimen.notification_large_icon_height))).build();
    }

    public static Notification buildNotification(Context context, String str) {
        String string = context.getResources().getString(R.string.permdesc_foregroundServiceSpecialUse);
        String str2 = str + " " + context.getResources().getString(R.string.permdesc_foregroundServiceRemoteMessaging);
        return new Notification.Builder(context, SystemNotificationChannels.DEVICE_ADMIN).setSmallIcon(17304206).setColor(context.getResources().getColor(R.color.system_notification_accent_color)).setContentTitle(string).setContentText(str2).setStyle(new Notification.BigTextStyle().bigText(str2)).build();
    }

    public static ArraySet newArraySet(Object... objArr) {
        ArraySet arraySet = new ArraySet();
        if (objArr != null) {
            arraySet.ensureCapacity(objArr.length);
            Collections.addAll(arraySet, objArr);
        }
        return arraySet;
    }

    public final class BroadcastCookie {
        public final int callingUid;
        public final IntPredicate userCheck;

        public BroadcastCookie(int i, IntPredicate intPredicate) {
            this.callingUid = i;
            this.userCheck = intPredicate;
        }
    }

    public class Callbacks extends Handler {
        public final RemoteCallbackList mCallbacks;

        public Callbacks(Looper looper) {
            super(looper);
            this.mCallbacks = new RemoteCallbackList();
        }

        public void register(IPackageInstallerCallback iPackageInstallerCallback, BroadcastCookie broadcastCookie) {
            this.mCallbacks.register(iPackageInstallerCallback, broadcastCookie);
        }

        public void unregister(IPackageInstallerCallback iPackageInstallerCallback) {
            this.mCallbacks.unregister(iPackageInstallerCallback);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int i = message.arg1;
            int i2 = message.arg2;
            int beginBroadcast = this.mCallbacks.beginBroadcast();
            Computer snapshotComputer = PackageInstallerService.this.mPm.snapshotComputer();
            for (int i3 = 0; i3 < beginBroadcast; i3++) {
                IPackageInstallerCallback iPackageInstallerCallback = (IPackageInstallerCallback) this.mCallbacks.getBroadcastItem(i3);
                BroadcastCookie broadcastCookie = (BroadcastCookie) this.mCallbacks.getBroadcastCookie(i3);
                if (broadcastCookie.userCheck.test(i2) && !PackageInstallerService.this.shouldFilterSession(snapshotComputer, broadcastCookie.callingUid, i)) {
                    try {
                        invokeCallback(iPackageInstallerCallback, message);
                    } catch (RemoteException unused) {
                    }
                }
            }
            this.mCallbacks.finishBroadcast();
        }

        public final void invokeCallback(IPackageInstallerCallback iPackageInstallerCallback, Message message) {
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
                iPackageInstallerCallback.onSessionActiveChanged(i, ((Boolean) message.obj).booleanValue());
            } else if (i2 == 4) {
                iPackageInstallerCallback.onSessionProgressChanged(i, ((Float) message.obj).floatValue());
            } else {
                if (i2 != 5) {
                    return;
                }
                iPackageInstallerCallback.onSessionFinished(i, ((Boolean) message.obj).booleanValue());
            }
        }

        public final void notifySessionCreated(int i, int i2) {
            obtainMessage(1, i, i2).sendToTarget();
        }

        public final void notifySessionBadgingChanged(int i, int i2) {
            obtainMessage(2, i, i2).sendToTarget();
        }

        public final void notifySessionActiveChanged(int i, int i2, boolean z) {
            obtainMessage(3, i, i2, Boolean.valueOf(z)).sendToTarget();
        }

        public final void notifySessionProgressChanged(int i, int i2, float f) {
            obtainMessage(4, i, i2, Float.valueOf(f)).sendToTarget();
        }

        public void notifySessionFinished(int i, int i2, boolean z) {
            obtainMessage(5, i, i2, Boolean.valueOf(z)).sendToTarget();
        }
    }

    public class ParentChildSessionMap {
        public final Comparator mSessionCreationComparator;
        public TreeMap mSessionMap;

        public static /* synthetic */ long lambda$new$0(PackageInstallerSession packageInstallerSession) {
            if (packageInstallerSession != null) {
                return packageInstallerSession.createdMillis;
            }
            return -1L;
        }

        public static /* synthetic */ int lambda$new$1(PackageInstallerSession packageInstallerSession) {
            if (packageInstallerSession != null) {
                return packageInstallerSession.sessionId;
            }
            return -1;
        }

        public ParentChildSessionMap() {
            Comparator thenComparingInt = Comparator.comparingLong(new ToLongFunction() { // from class: com.android.server.pm.PackageInstallerService$ParentChildSessionMap$$ExternalSyntheticLambda0
                @Override // java.util.function.ToLongFunction
                public final long applyAsLong(Object obj) {
                    long lambda$new$0;
                    lambda$new$0 = PackageInstallerService.ParentChildSessionMap.lambda$new$0((PackageInstallerSession) obj);
                    return lambda$new$0;
                }
            }).thenComparingInt(new ToIntFunction() { // from class: com.android.server.pm.PackageInstallerService$ParentChildSessionMap$$ExternalSyntheticLambda1
                @Override // java.util.function.ToIntFunction
                public final int applyAsInt(Object obj) {
                    int lambda$new$1;
                    lambda$new$1 = PackageInstallerService.ParentChildSessionMap.lambda$new$1((PackageInstallerSession) obj);
                    return lambda$new$1;
                }
            });
            this.mSessionCreationComparator = thenComparingInt;
            this.mSessionMap = new TreeMap(thenComparingInt);
        }

        public boolean containsSession() {
            return !this.mSessionMap.isEmpty();
        }

        public final void addParentSession(PackageInstallerSession packageInstallerSession) {
            if (this.mSessionMap.containsKey(packageInstallerSession)) {
                return;
            }
            this.mSessionMap.put(packageInstallerSession, new TreeSet(this.mSessionCreationComparator));
        }

        public final void addChildSession(PackageInstallerSession packageInstallerSession, PackageInstallerSession packageInstallerSession2) {
            addParentSession(packageInstallerSession2);
            ((TreeSet) this.mSessionMap.get(packageInstallerSession2)).add(packageInstallerSession);
        }

        public void addSession(PackageInstallerSession packageInstallerSession, PackageInstallerSession packageInstallerSession2) {
            if (packageInstallerSession.hasParentSessionId()) {
                addChildSession(packageInstallerSession, packageInstallerSession2);
            } else {
                addParentSession(packageInstallerSession);
            }
        }

        public void dump(String str, IndentingPrintWriter indentingPrintWriter) {
            indentingPrintWriter.println(str + " install sessions:");
            indentingPrintWriter.increaseIndent();
            for (Map.Entry entry : this.mSessionMap.entrySet()) {
                PackageInstallerSession packageInstallerSession = (PackageInstallerSession) entry.getKey();
                if (packageInstallerSession != null) {
                    indentingPrintWriter.print(str + " ");
                    packageInstallerSession.dump(indentingPrintWriter);
                    indentingPrintWriter.println();
                    indentingPrintWriter.increaseIndent();
                }
                Iterator it = ((TreeSet) entry.getValue()).iterator();
                while (it.hasNext()) {
                    PackageInstallerSession packageInstallerSession2 = (PackageInstallerSession) it.next();
                    indentingPrintWriter.print(str + " Child ");
                    packageInstallerSession2.dump(indentingPrintWriter);
                    indentingPrintWriter.println();
                }
                indentingPrintWriter.decreaseIndent();
            }
            indentingPrintWriter.println();
            indentingPrintWriter.decreaseIndent();
        }
    }

    public void dump(IndentingPrintWriter indentingPrintWriter) {
        synchronized (this.mSessions) {
            ParentChildSessionMap parentChildSessionMap = new ParentChildSessionMap();
            ParentChildSessionMap parentChildSessionMap2 = new ParentChildSessionMap();
            ParentChildSessionMap parentChildSessionMap3 = new ParentChildSessionMap();
            int size = this.mSessions.size();
            for (int i = 0; i < size; i++) {
                PackageInstallerSession packageInstallerSession = (PackageInstallerSession) this.mSessions.valueAt(i);
                PackageInstallerSession session = packageInstallerSession.hasParentSessionId() ? getSession(packageInstallerSession.getParentSessionId()) : packageInstallerSession;
                if (session == null) {
                    parentChildSessionMap2.addSession(packageInstallerSession, session);
                } else if (session.isStagedAndInTerminalState()) {
                    parentChildSessionMap3.addSession(packageInstallerSession, session);
                } else {
                    parentChildSessionMap.addSession(packageInstallerSession, session);
                }
            }
            parentChildSessionMap.dump("Active", indentingPrintWriter);
            if (parentChildSessionMap2.containsSession()) {
                parentChildSessionMap2.dump("Orphaned", indentingPrintWriter);
            }
            parentChildSessionMap3.dump("Finalized", indentingPrintWriter);
            indentingPrintWriter.println("Historical install sessions:");
            indentingPrintWriter.increaseIndent();
            int size2 = this.mHistoricalSessions.size();
            for (int i2 = 0; i2 < size2; i2++) {
                indentingPrintWriter.print((String) this.mHistoricalSessions.get(i2));
                indentingPrintWriter.println();
            }
            indentingPrintWriter.println();
            indentingPrintWriter.decreaseIndent();
            indentingPrintWriter.println("Legacy install sessions:");
            indentingPrintWriter.increaseIndent();
            indentingPrintWriter.println(this.mLegacySessions.toString());
            indentingPrintWriter.println();
            indentingPrintWriter.decreaseIndent();
        }
        this.mSilentUpdatePolicy.dump(indentingPrintWriter);
        this.mGentleUpdateHelper.dump(indentingPrintWriter);
    }

    public class InternalCallback {
        public InternalCallback() {
        }

        public void onSessionBadgingChanged(PackageInstallerSession packageInstallerSession) {
            PackageInstallerService.this.mCallbacks.notifySessionBadgingChanged(packageInstallerSession.sessionId, packageInstallerSession.userId);
            PackageInstallerService.this.mSettingsWriteRequest.schedule();
        }

        public void onSessionActiveChanged(PackageInstallerSession packageInstallerSession, boolean z) {
            PackageInstallerService.this.mCallbacks.notifySessionActiveChanged(packageInstallerSession.sessionId, packageInstallerSession.userId, z);
        }

        public void onSessionProgressChanged(PackageInstallerSession packageInstallerSession, float f) {
            PackageInstallerService.this.mCallbacks.notifySessionProgressChanged(packageInstallerSession.sessionId, packageInstallerSession.userId, f);
        }

        public void onSessionChanged(PackageInstallerSession packageInstallerSession) {
            packageInstallerSession.markUpdated();
            PackageInstallerService.this.mSettingsWriteRequest.schedule();
            if (PackageInstallerService.this.mOkToSendBroadcasts && !packageInstallerSession.isDestroyed() && packageInstallerSession.isStaged()) {
                PackageInstallerService.this.sendSessionUpdatedBroadcast(packageInstallerSession.generateInfoForCaller(false, 1000), packageInstallerSession.userId);
            }
        }

        public void onSessionFinished(final PackageInstallerSession packageInstallerSession, final boolean z) {
            PackageInstallerService.this.mCallbacks.notifySessionFinished(packageInstallerSession.sessionId, packageInstallerSession.userId, z);
            PackageInstallerService.this.mInstallHandler.post(new Runnable() { // from class: com.android.server.pm.PackageInstallerService.InternalCallback.1
                /* JADX WARN: Removed duplicated region for block: B:20:0x004a A[Catch: all -> 0x0075, TryCatch #0 {, blocks: (B:9:0x0024, B:11:0x002c, B:13:0x0034, B:15:0x003c, B:20:0x004a, B:22:0x0053, B:24:0x0065, B:25:0x0068, B:26:0x0073), top: B:8:0x0024 }] */
                @Override // java.lang.Runnable
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public void run() {
                    boolean z2;
                    if (packageInstallerSession.isStaged() && !z) {
                        PackageInstallerService.this.mStagingManager.abortSession(packageInstallerSession.mStagedSession);
                    }
                    synchronized (PackageInstallerService.this.mSessions) {
                        if (!packageInstallerSession.hasParentSessionId()) {
                            if (packageInstallerSession.isStaged() && !packageInstallerSession.isDestroyed() && packageInstallerSession.isCommitted()) {
                                z2 = false;
                                if (z2) {
                                    PackageInstallerService.this.removeActiveSession(packageInstallerSession);
                                }
                            }
                            z2 = true;
                            if (z2) {
                            }
                        }
                        File buildAppIconFile = PackageInstallerService.this.buildAppIconFile(packageInstallerSession.sessionId);
                        if (buildAppIconFile.exists()) {
                            buildAppIconFile.delete();
                        }
                        PackageInstallerService.this.mSettingsWriteRequest.runNow();
                    }
                }
            });
        }

        public void onSessionPrepared(PackageInstallerSession packageInstallerSession) {
            PackageInstallerService.this.mSettingsWriteRequest.schedule();
        }

        public void onSessionSealedBlocking(PackageInstallerSession packageInstallerSession) {
            PackageInstallerService.this.mSettingsWriteRequest.runNow();
        }
    }

    public final void sendSessionUpdatedBroadcast(PackageInstaller.SessionInfo sessionInfo, int i) {
        if (TextUtils.isEmpty(sessionInfo.installerPackageName)) {
            return;
        }
        this.mContext.sendBroadcastAsUser(new Intent("android.content.pm.action.SESSION_UPDATED").putExtra("android.content.pm.extra.SESSION", sessionInfo).setPackage(sessionInfo.installerPackageName), UserHandle.of(i));
        if ("com.sec.android.app.samsungapps".equals(sessionInfo.installerPackageName)) {
            this.mContext.sendBroadcastAsUser(new Intent("android.content.pm.action.SESSION_UPDATED").putExtra("android.content.pm.extra.SESSION", sessionInfo).setPackage("android"), UserHandle.of(i));
        }
    }

    public void onInstallerPackageDeleted(int i, int i2) {
        synchronized (this.mSessions) {
            for (int i3 = 0; i3 < this.mSessions.size(); i3++) {
                PackageInstallerSession packageInstallerSession = (PackageInstallerSession) this.mSessions.valueAt(i3);
                if (matchesInstaller(packageInstallerSession, i, i2)) {
                    if (packageInstallerSession.hasParentSessionId()) {
                        packageInstallerSession = (PackageInstallerSession) this.mSessions.get(packageInstallerSession.getParentSessionId());
                    }
                    if (packageInstallerSession != null && matchesInstaller(packageInstallerSession, i, i2) && !packageInstallerSession.isDestroyed()) {
                        packageInstallerSession.abandon();
                    }
                }
            }
        }
    }

    public final boolean matchesInstaller(PackageInstallerSession packageInstallerSession, int i, int i2) {
        int installerUid = packageInstallerSession.getInstallerUid();
        return i == -1 ? UserHandle.getAppId(installerUid) == i : UserHandle.getUid(i2, i) == installerUid;
    }

    public boolean isFromApprovedInstaller(int i, int i2) {
        return isFromApprovedInstaller(i, i2, false);
    }

    public boolean isFromApprovedInstaller(int i, int i2, boolean z) {
        return PersonaServiceHelper.isCallerApprovedToInstall(this.mContext, i, i2, z);
    }

    public void setUnknownSourceConfirmResult(int i, boolean z) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.INSTALL_PACKAGES", "PackageInstaller");
        synchronized (this.mSessions) {
            PackageInstallerSession packageInstallerSession = (PackageInstallerSession) this.mSessions.get(i);
            if (packageInstallerSession != null) {
                packageInstallerSession.setUnknownSourceConfirmResult(z);
            }
        }
    }

    public final boolean isValidSmartSwitchSession(int i, long j) {
        PackageInfo packageInfo;
        if (this.mSmartSwitchUid <= -1) {
            this.mSmartSwitchUid = 0;
            try {
                PackageManager packageManager = this.mContext.getPackageManager();
                ApplicationInfo applicationInfo = packageManager == null ? null : packageManager.getApplicationInfo("com.sec.android.easyMover", 128);
                int i2 = applicationInfo == null ? 0 : applicationInfo.uid;
                this.mSmartSwitchUid = i2;
                if (i2 > 0 && ((packageInfo = packageManager.getPackageInfo("com.sec.android.easyMover", 0)) == null || packageInfo.versionCode < 300000000)) {
                    this.mSmartSwitchUid = 0;
                }
            } catch (PackageManager.NameNotFoundException e) {
                Slog.w("PackageInstaller", "isValidSmartSwitchSession " + e.getStackTrace());
            }
        }
        int i3 = this.mSmartSwitchUid;
        return i3 > 0 && i3 == i && j < 21600000;
    }
}
