package com.android.server.pm;

import android.apex.ApexInfo;
import android.apex.ApexSessionParams;
import android.content.Context;
import android.content.pm.ApexStagedEvent;
import android.content.pm.IStagedApexObserver;
import android.content.pm.PackageInstaller;
import android.content.pm.PackageManagerInternal;
import android.hardware.broadcastradio.V2_0.AmFmBandRange$$ExternalSyntheticOutline0;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.IntArray;
import android.util.Slog;
import android.util.SparseArray;
import android.util.TimingsTraceLog;
import com.android.internal.content.InstallLocationUtils;
import com.android.internal.os.BackgroundThread;
import com.android.internal.util.Preconditions;
import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.CustomizedBinderCallsStatsInternal$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.SystemService;
import com.android.server.accessibility.magnification.MagnificationConnectionManager$$ExternalSyntheticOutline0;
import com.android.server.pm.ApexManager;
import com.android.server.pm.PackageInstallerSession;
import com.android.server.pm.pkg.PackageStateInternal;
import com.android.server.pm.pkg.PackageStateUtils;
import com.android.server.rollback.RollbackManagerInternal;
import com.android.server.rollback.RollbackManagerServiceImpl;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class StagingManager {
    public final ApexManager mApexManager;
    public final CompletableFuture mBootCompleted;
    public final Context mContext;
    public final List mFailedPackageNames;
    public final String mFailureReason;
    public final File mFailureReasonFile;
    public String mNativeFailureReason;
    public final PowerManager mPowerManager;
    public final List mStagedApexObservers;
    public final SparseArray mStagedSessions;
    public final List mSuccessfulStagedSessionIds;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Lifecycle extends SystemService {
        public static StagingManager sStagingManager;

        public Lifecycle(Context context) {
            super(context);
        }

        @Override // com.android.server.SystemService
        public final void onBootPhase(int i) {
            StagingManager stagingManager;
            if (i != 1000 || (stagingManager = sStagingManager) == null) {
                return;
            }
            synchronized (stagingManager.mSuccessfulStagedSessionIds) {
                for (int i2 = 0; i2 < ((ArrayList) stagingManager.mSuccessfulStagedSessionIds).size(); i2++) {
                    try {
                        stagingManager.mApexManager.markStagedSessionSuccessful(((Integer) ((ArrayList) stagingManager.mSuccessfulStagedSessionIds).get(i2)).intValue());
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                if (!((ArrayList) stagingManager.mSuccessfulStagedSessionIds).isEmpty()) {
                    SystemProperties.set("sys.staged_apex.state", "success");
                }
            }
            ApexManager.ApexManagerImpl apexManagerImpl = (ApexManager.ApexManagerImpl) sStagingManager.mApexManager;
            apexManagerImpl.getClass();
            try {
                apexManagerImpl.waitForApexService().markBootCompleted();
            } catch (RemoteException e) {
                Slog.e("ApexManager", "Unable to contact apexservice", e);
            }
        }

        @Override // com.android.server.SystemService
        public final void onStart() {
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface StagedSession {
    }

    public StagingManager(Context context, ApexManager apexManager) {
        File file = new File("/metadata/staged-install/failure_reason.txt");
        this.mFailureReasonFile = file;
        this.mStagedSessions = new SparseArray();
        this.mFailedPackageNames = new ArrayList();
        this.mSuccessfulStagedSessionIds = new ArrayList();
        this.mStagedApexObservers = new ArrayList();
        this.mBootCompleted = new CompletableFuture();
        this.mContext = context;
        this.mApexManager = apexManager;
        this.mPowerManager = (PowerManager) context.getSystemService("power");
        if (file.exists()) {
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                try {
                    this.mFailureReason = bufferedReader.readLine();
                    bufferedReader.close();
                } finally {
                }
            } catch (Exception unused) {
            }
        }
    }

    public static List extractApexSessions(StagedSession stagedSession) {
        ArrayList arrayList = new ArrayList();
        PackageInstallerSession.StagedSession stagedSession2 = (PackageInstallerSession.StagedSession) stagedSession;
        if (PackageInstallerSession.this.params.isMultiPackage) {
            Iterator it = stagedSession2.getChildSessions().iterator();
            while (it.hasNext()) {
                PackageInstallerSession.StagedSession stagedSession3 = (PackageInstallerSession.StagedSession) ((StagedSession) it.next());
                if (stagedSession3.containsApexSession()) {
                    arrayList.add(stagedSession3);
                }
            }
        } else {
            arrayList.add(stagedSession2);
        }
        return arrayList;
    }

    public final void abortCheckpoint(String str, boolean z, boolean z2) {
        ApexManager apexManager = this.mApexManager;
        Slog.e("StagingManager", str);
        if (z) {
            try {
                if (z2) {
                    try {
                        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(this.mFailureReasonFile));
                        try {
                            bufferedWriter.write(str);
                            bufferedWriter.close();
                        } finally {
                        }
                    } catch (Exception e) {
                        Slog.w("StagingManager", "Failed to save failure reason: ", e);
                    }
                    apexManager.getClass();
                    apexManager.revertActiveSessions();
                    InstallLocationUtils.getStorageManager().abortChanges("abort-staged-install", false);
                }
            } catch (Exception e2) {
                Slog.wtf("StagingManager", "Failed to abort checkpoint", e2);
                apexManager.getClass();
                apexManager.revertActiveSessions();
                this.mPowerManager.reboot(null);
            }
        }
    }

    public final void abortSession(StagedSession stagedSession) {
        synchronized (this.mStagedSessions) {
            this.mStagedSessions.remove(PackageInstallerSession.this.sessionId);
        }
    }

    public void commitSession(StagedSession stagedSession) {
        createSession(stagedSession);
        PackageInstallerSession.StagedSession stagedSession2 = (PackageInstallerSession.StagedSession) stagedSession;
        if (stagedSession2.isSessionReady() && stagedSession2.containsApexSession()) {
            notifyStagedApexObservers();
        }
    }

    public void createSession(StagedSession stagedSession) {
        synchronized (this.mStagedSessions) {
            this.mStagedSessions.append(PackageInstallerSession.this.sessionId, stagedSession);
        }
    }

    public final String getReasonForRevert() {
        String str = this.mFailureReason;
        if (!TextUtils.isEmpty(str)) {
            return str;
        }
        if (TextUtils.isEmpty(this.mNativeFailureReason)) {
            return "";
        }
        return "Session reverted due to crashing native process: " + this.mNativeFailureReason;
    }

    public Map getStagedApexInfos(StagedSession stagedSession) {
        ApexInfo[] apexInfoArr;
        Preconditions.checkArgument(stagedSession != null, "Session is null");
        PackageInstallerSession.StagedSession stagedSession2 = (PackageInstallerSession.StagedSession) stagedSession;
        boolean hasParentSessionId = true ^ PackageInstallerSession.this.hasParentSessionId();
        StringBuilder sb = new StringBuilder();
        PackageInstallerSession packageInstallerSession = PackageInstallerSession.this;
        sb.append(packageInstallerSession.sessionId);
        sb.append(" session has parent session");
        Preconditions.checkArgument(hasParentSessionId, sb.toString());
        Preconditions.checkArgument(stagedSession2.containsApexSession(), packageInstallerSession.sessionId + " session does not contain apex");
        if (!stagedSession2.isSessionReady() || packageInstallerSession.isDestroyed()) {
            return Collections.emptyMap();
        }
        ApexSessionParams apexSessionParams = new ApexSessionParams();
        apexSessionParams.sessionId = packageInstallerSession.sessionId;
        IntArray intArray = new IntArray();
        if (PackageInstallerSession.this.params.isMultiPackage) {
            Iterator it = stagedSession2.getChildSessions().iterator();
            while (it.hasNext()) {
                PackageInstallerSession.StagedSession stagedSession3 = (PackageInstallerSession.StagedSession) ((StagedSession) it.next());
                if (stagedSession3.isApexSession()) {
                    intArray.add(PackageInstallerSession.this.sessionId);
                }
            }
        }
        apexSessionParams.childSessionIds = intArray.toArray();
        ApexManager.ApexManagerImpl apexManagerImpl = (ApexManager.ApexManagerImpl) this.mApexManager;
        apexManagerImpl.getClass();
        try {
            apexInfoArr = apexManagerImpl.waitForApexService().getStagedApexInfos(apexSessionParams);
        } catch (RemoteException e) {
            Slog.w("ApexManager", "Unable to contact apexservice" + e.getMessage());
            throw new RuntimeException(e);
        } catch (Exception e2) {
            MagnificationConnectionManager$$ExternalSyntheticOutline0.m(e2, new StringBuilder("Failed to collect staged apex infos"), "ApexManager");
            apexInfoArr = new ApexInfo[0];
        }
        ArrayMap arrayMap = new ArrayMap();
        for (ApexInfo apexInfo : apexInfoArr) {
            arrayMap.put(apexInfo.moduleName, apexInfo);
        }
        return arrayMap;
    }

    public final List getStagedApexModuleNames() {
        ArrayList arrayList = new ArrayList();
        synchronized (this.mStagedSessions) {
            for (int i = 0; i < this.mStagedSessions.size(); i++) {
                try {
                    PackageInstallerSession.StagedSession stagedSession = (PackageInstallerSession.StagedSession) ((StagedSession) this.mStagedSessions.valueAt(i));
                    if (stagedSession.isSessionReady() && !PackageInstallerSession.this.isDestroyed() && !PackageInstallerSession.this.hasParentSessionId() && stagedSession.containsApexSession()) {
                        arrayList.addAll(getStagedApexInfos(stagedSession).keySet());
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
        return arrayList;
    }

    public final void notifyStagedApexObservers() {
        synchronized (this.mStagedApexObservers) {
            Iterator it = ((ArrayList) this.mStagedApexObservers).iterator();
            while (it.hasNext()) {
                IStagedApexObserver iStagedApexObserver = (IStagedApexObserver) it.next();
                ApexStagedEvent apexStagedEvent = new ApexStagedEvent();
                apexStagedEvent.stagedApexModuleNames = (String[]) ((ArrayList) getStagedApexModuleNames()).toArray(new String[0]);
                try {
                    iStagedApexObserver.onApexStaged(apexStagedEvent);
                } catch (RemoteException e) {
                    Slog.w("StagingManager", "Failed to contact the observer " + e.getMessage());
                }
            }
        }
    }

    public void onBootCompletedBroadcastReceived() {
        this.mBootCompleted.complete(null);
        BackgroundThread.getExecutor().execute(new StagingManager$$ExternalSyntheticLambda0(0, this));
    }

    public final void onInstallationFailure(StagedSession stagedSession, PackageManagerException packageManagerException, boolean z, boolean z2) {
        PackageInstallerSession.StagedSession stagedSession2 = (PackageInstallerSession.StagedSession) stagedSession;
        stagedSession2.setSessionFailed(packageManagerException.error, packageManagerException.getMessage());
        abortCheckpoint("Failed to install sessionId: " + PackageInstallerSession.this.sessionId + " Error: " + packageManagerException.getMessage(), z, z2);
        if (stagedSession2.containsApexSession()) {
            if (!this.mApexManager.revertActiveSessions()) {
                Slog.e("StagingManager", "Failed to abort APEXd session");
            } else {
                Slog.e("StagingManager", "Successfully aborted apexd session. Rebooting device in order to revert to the previous state of APEXd.");
                this.mPowerManager.reboot(null);
            }
        }
    }

    public final void resumeSession(StagedSession stagedSession, boolean z, boolean z2) {
        int i;
        String str;
        StringBuilder sb = new StringBuilder("Resuming session ");
        PackageInstallerSession.StagedSession stagedSession2 = (PackageInstallerSession.StagedSession) stagedSession;
        sb.append(PackageInstallerSession.this.sessionId);
        Slog.d("StagingManager", sb.toString());
        boolean containsApexSession = stagedSession2.containsApexSession();
        if (z && !z2) {
            String m = AmFmBandRange$$ExternalSyntheticOutline0.m(PackageInstallerSession.this.sessionId, new StringBuilder("Reverting back to safe state. Marking "), " as failed.");
            String reasonForRevert = getReasonForRevert();
            if (!TextUtils.isEmpty(reasonForRevert)) {
                m = AnyMotionDetector$$ExternalSyntheticOutline0.m(m, " Reason for revert: ", reasonForRevert);
            }
            Slog.d("StagingManager", m);
            stagedSession2.setSessionFailed(-110, m);
            return;
        }
        if (containsApexSession) {
            ArrayList arrayList = (ArrayList) extractApexSessions(stagedSession);
            if (!arrayList.isEmpty()) {
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    String packageName = PackageInstallerSession.this.getPackageName();
                    ApexManager.ApexManagerImpl apexManagerImpl = (ApexManager.ApexManagerImpl) this.mApexManager;
                    synchronized (apexManagerImpl.mLock) {
                        try {
                            Preconditions.checkState(apexManagerImpl.mPackageNameToApexModuleName != null, "APEX packages have not been scanned");
                            String str2 = (String) apexManagerImpl.mPackageNameToApexModuleName.get(packageName);
                            str = str2 == null ? null : (String) ((ArrayMap) apexManagerImpl.mErrorWithApkInApex).get(str2);
                        } finally {
                        }
                    }
                    if (str != null) {
                        throw new PackageManagerException(-128, BootReceiver$$ExternalSyntheticOutline0.m("Failed to install apk-in-apex of ", packageName, " : ", str));
                    }
                }
            }
            if (PackageInstallerSession.this.params.isMultiPackage) {
                ArraySet arraySet = new ArraySet();
                Iterator it2 = stagedSession2.getChildSessions().iterator();
                while (it2.hasNext()) {
                    PackageInstallerSession.StagedSession stagedSession3 = (PackageInstallerSession.StagedSession) ((StagedSession) it2.next());
                    if (!stagedSession3.isApexSession()) {
                        arraySet.add(PackageInstallerSession.this.getPackageName());
                    }
                }
                Iterator it3 = ((ArrayList) extractApexSessions(stagedSession2)).iterator();
                while (it3.hasNext()) {
                    PackageInstallerSession.StagedSession stagedSession4 = (PackageInstallerSession.StagedSession) ((StagedSession) it3.next());
                    String packageName2 = PackageInstallerSession.this.getPackageName();
                    for (String str3 : this.mApexManager.getApksInApex(packageName2)) {
                        if (!arraySet.add(str3)) {
                            StringBuilder m2 = DumpUtils$$ExternalSyntheticOutline0.m("Package: ", packageName2, " in session: ");
                            m2.append(PackageInstallerSession.this.sessionId);
                            m2.append(" has duplicate apk-in-apex: ");
                            m2.append(str3);
                            throw new PackageManagerException(-128, m2.toString(), null);
                        }
                    }
                }
            }
            PackageInstaller.SessionParams sessionParams = PackageInstallerSession.this.params;
            if ((sessionParams.installFlags & 262144) != 0 || sessionParams.installReason == 5) {
                ArrayList arrayList2 = (ArrayList) extractApexSessions(stagedSession);
                if (!arrayList2.isEmpty()) {
                    int[] userIds = ((UserManagerInternal) LocalServices.getService(UserManagerInternal.class)).getUserIds();
                    RollbackManagerInternal rollbackManagerInternal = (RollbackManagerInternal) LocalServices.getService(RollbackManagerInternal.class);
                    int size = arrayList2.size();
                    for (int i2 = 0; i2 < size; i2++) {
                        String packageName3 = PackageInstallerSession.this.getPackageName();
                        List userHandles = UserHandle.toUserHandles(userIds);
                        RollbackManagerServiceImpl rollbackManagerServiceImpl = (RollbackManagerServiceImpl) rollbackManagerInternal;
                        rollbackManagerServiceImpl.assertNotInWorkerThread();
                        rollbackManagerServiceImpl.snapshotAndRestoreUserData(packageName3, UserHandle.fromUserHandles(userHandles), 0, 0L, null, 0);
                        List apksInApex = this.mApexManager.getApksInApex(packageName3);
                        int size2 = apksInApex.size();
                        int i3 = 0;
                        while (i3 < size2) {
                            String str4 = (String) apksInApex.get(i3);
                            PackageManagerInternal packageManagerInternal = (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
                            if (packageManagerInternal.getPackage(str4) == null) {
                                Slog.e("StagingManager", "Could not find package: " + str4 + "for snapshotting/restoring user data.");
                            } else {
                                PackageStateInternal packageStateInternal = packageManagerInternal.getPackageStateInternal(str4);
                                if (packageStateInternal != null) {
                                    PackageSetting packageSetting = (PackageSetting) packageStateInternal;
                                    int i4 = packageSetting.mAppId;
                                    long ceDataInode = packageStateInternal.getUserStateOrDefault(0).getCeDataInode();
                                    int[] queryInstalledUsers = PackageStateUtils.queryInstalledUsers(packageStateInternal, userIds);
                                    String seInfo = packageSetting.getSeInfo();
                                    List userHandles2 = UserHandle.toUserHandles(queryInstalledUsers);
                                    rollbackManagerServiceImpl.assertNotInWorkerThread();
                                    i = i3;
                                    rollbackManagerServiceImpl.snapshotAndRestoreUserData(str4, UserHandle.fromUserHandles(userHandles2), i4, ceDataInode, seInfo, 0);
                                    i3 = i + 1;
                                }
                            }
                            i = i3;
                            i3 = i + 1;
                        }
                    }
                }
            }
            CustomizedBinderCallsStatsInternal$$ExternalSyntheticOutline0.m(new StringBuilder("APEX packages in session "), PackageInstallerSession.this.sessionId, " were successfully activated. Proceeding with APK packages, if any", "StagingManager");
        }
        Slog.d("StagingManager", "Installing APK packages in session " + PackageInstallerSession.this.sessionId);
        TimingsTraceLog timingsTraceLog = new TimingsTraceLog("StagingManagerTiming", 262144L);
        timingsTraceLog.traceBegin("installApksInSession");
        try {
            ((PackageInstallerSession.StagedSession) stagedSession).installSession().get();
            timingsTraceLog.traceEnd();
            if (containsApexSession) {
                if (z) {
                    synchronized (this.mSuccessfulStagedSessionIds) {
                        ((ArrayList) this.mSuccessfulStagedSessionIds).add(Integer.valueOf(PackageInstallerSession.this.sessionId));
                    }
                } else {
                    this.mApexManager.markStagedSessionSuccessful(PackageInstallerSession.this.sessionId);
                    SystemProperties.set("sys.staged_apex.state", "success");
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e2) {
            throw ((PackageManagerException) e2.getCause());
        }
    }
}
