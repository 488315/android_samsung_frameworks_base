package com.android.server.am;

import android.app.ContentProviderHolder;
import android.app.IApplicationThread;
import android.content.ComponentName;
import android.content.IContentProvider;
import android.content.pm.ApplicationInfo;
import android.content.pm.ProviderInfo;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.ArrayMap;
import android.util.EventLog;
import android.util.Slog;

import com.android.internal.app.procstats.AssociationState;
import com.android.internal.app.procstats.ProcessStats;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.ProfileService$1$$ExternalSyntheticOutline0;

import java.io.PrintWriter;
import java.util.ArrayList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class ContentProviderRecord implements ComponentName.WithComponentName {
    public final ApplicationInfo appInfo;
    public final ArrayList connections = new ArrayList();
    public int externalProcessNoHandleCount;
    public ArrayMap externalProcessTokenToHandle;
    public final ProviderInfo info;
    public ProcessRecord launchingApp;
    public int mRestartCount;
    public final ComponentName name;
    public final boolean noReleaseNeeded;
    public ProcessRecord proc;
    public IContentProvider provider;
    public final ActivityManagerService service;
    public String shortStringName;
    public final boolean singleton;
    public String stringName;
    public final int uid;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ExternalProcessHandle implements IBinder.DeathRecipient {
        public int mAcquisitionCount;
        public AssociationState.SourceState mAssociation;
        public final String mOwningProcessName;
        public final int mOwningUid;
        public Object mProcStatsLock;
        public final IBinder mToken;

        public ExternalProcessHandle(IBinder iBinder, int i, String str) {
            this.mToken = iBinder;
            this.mOwningUid = i;
            this.mOwningProcessName = str;
            try {
                iBinder.linkToDeath(this, 0);
            } catch (RemoteException e) {
                Slog.e(
                        "ExternalProcessHanldle",
                        "Couldn't register for death for token: " + this.mToken,
                        e);
            }
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            ActivityManagerService activityManagerService = ContentProviderRecord.this.service;
            ActivityManagerService.boostPriorityForLockedSection();
            synchronized (activityManagerService) {
                try {
                    if (ContentProviderRecord.this.hasExternalProcessHandles()
                            && ContentProviderRecord.this.externalProcessTokenToHandle.get(
                                            this.mToken)
                                    != null) {
                        ContentProviderRecord.this.removeExternalProcessHandleInternalLocked(
                                this.mToken);
                    }
                } catch (Throwable th) {
                    ActivityManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            ActivityManagerService.resetPriorityAfterLockedSection();
        }

        public final void startAssociationIfNeeded(ContentProviderRecord contentProviderRecord) {
            if (this.mAssociation != null || contentProviderRecord.proc == null) {
                return;
            }
            if (contentProviderRecord.appInfo.uid == this.mOwningUid
                    && contentProviderRecord.info.processName.equals(this.mOwningProcessName)) {
                return;
            }
            ProcessStats.ProcessStateHolder processStateHolder =
                    contentProviderRecord.proc.mPkgList.get(
                            contentProviderRecord.name.getPackageName());
            if (processStateHolder == null) {
                Slog.wtf(
                        "ActivityManager",
                        "No package in referenced provider "
                                + contentProviderRecord.name.toShortString()
                                + ": proc="
                                + contentProviderRecord.proc);
                return;
            }
            if (processStateHolder.pkg != null) {
                Object obj = contentProviderRecord.proc.mService.mProcessStats.mLock;
                this.mProcStatsLock = obj;
                synchronized (obj) {
                    this.mAssociation =
                            processStateHolder
                                    .pkg
                                    .getAssociationStateLocked(
                                            processStateHolder.state,
                                            contentProviderRecord.name.getClassName())
                                    .startSource(
                                            this.mOwningUid,
                                            this.mOwningProcessName,
                                            (String) null);
                }
                return;
            }
            Slog.wtf(
                    "ActivityManager",
                    "Inactive holder in referenced provider "
                            + contentProviderRecord.name.toShortString()
                            + ": proc="
                            + contentProviderRecord.proc);
        }
    }

    public ContentProviderRecord(
            ActivityManagerService activityManagerService,
            ProviderInfo providerInfo,
            ApplicationInfo applicationInfo,
            ComponentName componentName,
            boolean z) {
        this.service = activityManagerService;
        this.info = providerInfo;
        this.uid = applicationInfo.uid;
        this.appInfo = applicationInfo;
        this.name = componentName;
        this.singleton = z;
        this.noReleaseNeeded = "system".equals(applicationInfo.processName);
    }

    public final boolean canRunHere(ProcessRecord processRecord) {
        ProviderInfo providerInfo = this.info;
        return (providerInfo.multiprocess
                        || providerInfo.processName.equals(processRecord.processName))
                && this.uid == processRecord.info.uid;
    }

    public final void dump(PrintWriter printWriter, String str, boolean z) {
        if (z) {
            printWriter.print(str);
            printWriter.print("package=");
            printWriter.print(this.info.applicationInfo.packageName);
            printWriter.print(" process=");
            printWriter.println(this.info.processName);
        }
        printWriter.print(str);
        printWriter.print("proc=");
        printWriter.println(this.proc);
        if (this.launchingApp != null) {
            printWriter.print(str);
            printWriter.print("launchingApp=");
            printWriter.println(this.launchingApp);
        }
        if (z) {
            printWriter.print(str);
            printWriter.print("uid=");
            printWriter.print(this.uid);
            printWriter.print(" provider=");
            printWriter.println(this.provider);
        }
        if (this.singleton) {
            printWriter.print(str);
            printWriter.print("singleton=");
            printWriter.println(this.singleton);
        }
        printWriter.print(str);
        printWriter.print("authority=");
        printWriter.println(this.info.authority);
        if (z) {
            ProviderInfo providerInfo = this.info;
            if (providerInfo.isSyncable
                    || providerInfo.multiprocess
                    || providerInfo.initOrder != 0) {
                printWriter.print(str);
                printWriter.print("isSyncable=");
                printWriter.print(this.info.isSyncable);
                printWriter.print(" multiprocess=");
                printWriter.print(this.info.multiprocess);
                printWriter.print(" initOrder=");
                printWriter.println(this.info.initOrder);
            }
        }
        if (z) {
            if (hasExternalProcessHandles()) {
                printWriter.print(str);
                printWriter.print("externals:");
                if (this.externalProcessTokenToHandle != null) {
                    printWriter.print(" w/token=");
                    printWriter.print(this.externalProcessTokenToHandle.size());
                }
                if (this.externalProcessNoHandleCount > 0) {
                    printWriter.print(" notoken=");
                    printWriter.print(this.externalProcessNoHandleCount);
                }
                printWriter.println();
            }
        } else if (this.connections.size() > 0 || this.externalProcessNoHandleCount > 0) {
            printWriter.print(str);
            printWriter.print(this.connections.size());
            printWriter.print(" connections, ");
            printWriter.print(this.externalProcessNoHandleCount);
            printWriter.println(" external handles");
        }
        if (this.connections.size() > 0) {
            if (z) {
                printWriter.print(str);
                printWriter.println("Connections:");
            }
            for (int i = 0; i < this.connections.size(); i++) {
                ContentProviderConnection contentProviderConnection =
                        (ContentProviderConnection) this.connections.get(i);
                printWriter.print(str);
                printWriter.print("  -> ");
                StringBuilder sb = new StringBuilder(128);
                contentProviderConnection.toClientString(sb);
                printWriter.println(sb.toString());
                if (contentProviderConnection.provider != this) {
                    printWriter.print(str);
                    printWriter.print("    *** WRONG PROVIDER: ");
                    printWriter.println(contentProviderConnection.provider);
                }
            }
        }
    }

    public final ComponentName getComponentName() {
        return this.name;
    }

    public final boolean hasExternalProcessHandles() {
        return this.externalProcessTokenToHandle != null || this.externalProcessNoHandleCount > 0;
    }

    public final ContentProviderHolder newHolder(
            ContentProviderConnection contentProviderConnection, boolean z) {
        ContentProviderHolder contentProviderHolder = new ContentProviderHolder(this.info);
        contentProviderHolder.provider = this.provider;
        contentProviderHolder.noReleaseNeeded = this.noReleaseNeeded;
        contentProviderHolder.connection = contentProviderConnection;
        contentProviderHolder.mLocal = z;
        return contentProviderHolder;
    }

    public final void onProviderPublishStatusLocked(boolean z) {
        ProcessRecord processRecord;
        int size = this.connections.size();
        for (int i = 0; i < size; i++) {
            ContentProviderConnection contentProviderConnection =
                    (ContentProviderConnection) this.connections.get(i);
            if (contentProviderConnection.waiting
                    && (processRecord = contentProviderConnection.client) != null) {
                if (!z) {
                    if (this.launchingApp == null) {
                        StringBuilder sb = new StringBuilder("Unable to launch app ");
                        sb.append(this.appInfo.packageName);
                        sb.append("/");
                        sb.append(this.appInfo.uid);
                        sb.append(" for provider ");
                        ProfileService$1$$ExternalSyntheticOutline0.m(
                                sb,
                                this.info.authority,
                                ": launching app became null",
                                "ActivityManager");
                        int userId = UserHandle.getUserId(this.appInfo.uid);
                        ApplicationInfo applicationInfo = this.appInfo;
                        EventLog.writeEvent(
                                30036,
                                Integer.valueOf(userId),
                                applicationInfo.packageName,
                                Integer.valueOf(applicationInfo.uid),
                                this.info.authority);
                    } else {
                        Slog.wtf(
                                "ActivityManager",
                                "Timeout waiting for provider "
                                        + this.appInfo.packageName
                                        + "/"
                                        + this.appInfo.uid
                                        + " for provider "
                                        + this.info.authority
                                        + " caller="
                                        + processRecord);
                    }
                }
                IApplicationThread iApplicationThread = processRecord.mThread;
                if (iApplicationThread != null) {
                    try {
                        iApplicationThread.notifyContentProviderPublishStatus(
                                newHolder(z ? contentProviderConnection : null, false),
                                this.info.authority,
                                contentProviderConnection.mExpectedUserId,
                                z);
                    } catch (RemoteException unused) {
                    }
                }
            }
            contentProviderConnection.waiting = false;
        }
    }

    public final void removeExternalProcessHandleInternalLocked(IBinder iBinder) {
        ExternalProcessHandle externalProcessHandle =
                (ExternalProcessHandle) this.externalProcessTokenToHandle.get(iBinder);
        externalProcessHandle.mToken.unlinkToDeath(externalProcessHandle, 0);
        if (externalProcessHandle.mAssociation != null) {
            synchronized (externalProcessHandle.mProcStatsLock) {
                externalProcessHandle.mAssociation.stop();
            }
            externalProcessHandle.mAssociation = null;
        }
        this.externalProcessTokenToHandle.remove(iBinder);
        if (this.externalProcessTokenToHandle.size() == 0) {
            this.externalProcessTokenToHandle = null;
        }
    }

    public final boolean removeExternalProcessHandleLocked(IBinder iBinder) {
        ExternalProcessHandle externalProcessHandle;
        if (!hasExternalProcessHandles()) {
            return false;
        }
        ArrayMap arrayMap = this.externalProcessTokenToHandle;
        if (arrayMap == null
                || (externalProcessHandle = (ExternalProcessHandle) arrayMap.get(iBinder))
                        == null) {
            this.externalProcessNoHandleCount--;
            return true;
        }
        int i = externalProcessHandle.mAcquisitionCount - 1;
        externalProcessHandle.mAcquisitionCount = i;
        if (i != 0) {
            return false;
        }
        removeExternalProcessHandleInternalLocked(iBinder);
        return true;
    }

    public final void setProcess(ProcessRecord processRecord) {
        this.proc = processRecord;
        int size = this.connections.size();
        while (true) {
            size--;
            if (size < 0) {
                break;
            }
            ContentProviderConnection contentProviderConnection =
                    (ContentProviderConnection) this.connections.get(size);
            if (processRecord != null) {
                contentProviderConnection.startAssociationIfNeeded();
            } else if (contentProviderConnection.association != null) {
                synchronized (contentProviderConnection.mProcStatsLock) {
                    contentProviderConnection.association.stop();
                }
                contentProviderConnection.association = null;
            } else {
                continue;
            }
        }
        ArrayMap arrayMap = this.externalProcessTokenToHandle;
        if (arrayMap != null) {
            for (int size2 = arrayMap.size() - 1; size2 >= 0; size2--) {
                ExternalProcessHandle externalProcessHandle =
                        (ExternalProcessHandle) this.externalProcessTokenToHandle.valueAt(size2);
                if (processRecord != null) {
                    externalProcessHandle.startAssociationIfNeeded(this);
                } else if (externalProcessHandle.mAssociation != null) {
                    synchronized (externalProcessHandle.mProcStatsLock) {
                        externalProcessHandle.mAssociation.stop();
                    }
                    externalProcessHandle.mAssociation = null;
                } else {
                    continue;
                }
            }
        }
    }

    public final String toShortString() {
        String str = this.shortStringName;
        if (str != null) {
            return str;
        }
        StringBuilder sb = new StringBuilder(128);
        sb.append(Integer.toHexString(System.identityHashCode(this)));
        sb.append('/');
        sb.append(this.name.flattenToShortString());
        String sb2 = sb.toString();
        this.shortStringName = sb2;
        return sb2;
    }

    public final String toString() {
        String str = this.stringName;
        if (str != null) {
            return str;
        }
        StringBuilder m = BootReceiver$$ExternalSyntheticOutline0.m(128, "ContentProviderRecord{");
        m.append(Integer.toHexString(System.identityHashCode(this)));
        m.append(" u");
        m.append(UserHandle.getUserId(this.uid));
        m.append(' ');
        m.append(this.name.flattenToShortString());
        m.append('}');
        String sb = m.toString();
        this.stringName = sb;
        return sb;
    }
}
