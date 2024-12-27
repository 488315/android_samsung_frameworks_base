package com.android.server.print;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ParceledListSlice;
import android.graphics.drawable.Icon;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.os.UserHandle;
import android.print.PrintJobId;
import android.print.PrintJobInfo;
import android.print.PrinterId;
import android.print.PrinterInfo;
import android.printservice.IPrintService;
import android.printservice.IPrintServiceClient;
import android.util.Slog;

import com.android.internal.os.BackgroundThread;
import com.android.internal.util.dump.DualDumpOutputStream;
import com.android.internal.util.dump.DumpUtils;
import com.android.internal.util.function.pooled.PooledLambda;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class RemotePrintService implements IBinder.DeathRecipient {
    public boolean mBinding;
    public final UserState mCallbacks;
    public final ComponentName mComponentName;
    public final Context mContext;
    public boolean mDestroyed;
    public List mDiscoveryPriorityList;
    public boolean mHasActivePrintJobs;
    public boolean mHasPrinterDiscoverySession;
    public final Intent mIntent;
    public IPrintService mPrintService;
    public boolean mServiceDied;
    public final RemotePrintSpooler mSpooler;
    public List mTrackedPrinterList;
    public final int mUserId;
    public final Object mLock = new Object();
    public final List mPendingCommands = new ArrayList();
    public final RemoteServiceConneciton mServiceConnection = new RemoteServiceConneciton();
    public final RemotePrintServiceClient mPrintServiceClient = new RemotePrintServiceClient(this);

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.print.RemotePrintService$1, reason: invalid class name */
    public final class AnonymousClass1 implements Runnable {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ RemotePrintService this$0;

        public /* synthetic */ AnonymousClass1(RemotePrintService remotePrintService, int i) {
            this.$r8$classId = i;
            this.this$0 = remotePrintService;
        }

        @Override // java.lang.Runnable
        public final void run() {
            switch (this.$r8$classId) {
                case 0:
                    this.this$0.handleOnAllPrintJobsHandled();
                    break;
                case 1:
                    this.this$0.handleCreatePrinterDiscoverySession();
                    break;
                case 2:
                    this.this$0.handleDestroyPrinterDiscoverySession();
                    break;
                default:
                    this.this$0.handleStopPrinterDiscovery();
                    break;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.print.RemotePrintService$2, reason: invalid class name */
    public final class AnonymousClass2 implements Runnable {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ RemotePrintService this$0;
        public final /* synthetic */ PrintJobInfo val$printJob;

        public /* synthetic */ AnonymousClass2(
                RemotePrintService remotePrintService, PrintJobInfo printJobInfo, int i) {
            this.$r8$classId = i;
            this.this$0 = remotePrintService;
            this.val$printJob = printJobInfo;
        }

        @Override // java.lang.Runnable
        public final void run() {
            switch (this.$r8$classId) {
                case 0:
                    this.this$0.handleRequestCancelPrintJob(this.val$printJob);
                    break;
                default:
                    this.this$0.handleOnPrintJobQueued(this.val$printJob);
                    break;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.print.RemotePrintService$6, reason: invalid class name */
    public final class AnonymousClass6 implements Runnable {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ RemotePrintService this$0;
        public final /* synthetic */ List val$priorityList;

        public /* synthetic */ AnonymousClass6(
                RemotePrintService remotePrintService, List list, int i) {
            this.$r8$classId = i;
            this.this$0 = remotePrintService;
            this.val$priorityList = list;
        }

        @Override // java.lang.Runnable
        public final void run() {
            switch (this.$r8$classId) {
                case 0:
                    this.this$0.handleStartPrinterDiscovery(this.val$priorityList);
                    break;
                default:
                    this.this$0.handleValidatePrinters(this.val$priorityList);
                    break;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.print.RemotePrintService$9, reason: invalid class name */
    public final class AnonymousClass9 implements Runnable {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ RemotePrintService this$0;
        public final /* synthetic */ PrinterId val$printerId;

        public /* synthetic */ AnonymousClass9(
                RemotePrintService remotePrintService, PrinterId printerId, int i) {
            this.$r8$classId = i;
            this.this$0 = remotePrintService;
            this.val$printerId = printerId;
        }

        @Override // java.lang.Runnable
        public final void run() {
            switch (this.$r8$classId) {
                case 0:
                    this.this$0.handleStartPrinterStateTracking(this.val$printerId);
                    break;
                default:
                    this.this$0.handleStopPrinterStateTracking(this.val$printerId);
                    break;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class RemotePrintServiceClient extends IPrintServiceClient.Stub {
        public final WeakReference mWeakService;

        public RemotePrintServiceClient(RemotePrintService remotePrintService) {
            this.mWeakService = new WeakReference(remotePrintService);
        }

        public static void throwIfPrinterIdTampered(
                ComponentName componentName, PrinterId printerId) {
            if (printerId == null || !printerId.getServiceName().equals(componentName)) {
                throw new IllegalArgumentException("Invalid printer id: " + printerId);
            }
        }

        public final PrintJobInfo getPrintJobInfo(PrintJobId printJobId) {
            RemotePrintService remotePrintService = (RemotePrintService) this.mWeakService.get();
            if (remotePrintService == null) {
                return null;
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return remotePrintService.mSpooler.getPrintJobInfo(printJobId, -2);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final List getPrintJobInfos() {
            RemotePrintService remotePrintService = (RemotePrintService) this.mWeakService.get();
            if (remotePrintService == null) {
                return null;
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return remotePrintService.mSpooler.getPrintJobInfos(
                        -4, -2, remotePrintService.mComponentName);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void onCustomPrinterIconLoaded(PrinterId printerId, Icon icon) {
            RemotePrintService remotePrintService = (RemotePrintService) this.mWeakService.get();
            if (remotePrintService != null) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    UserState userState = remotePrintService.mCallbacks;
                    userState.mSpooler.onCustomPrinterIconLoaded(printerId, icon);
                    synchronized (userState.mLock) {
                        try {
                            userState.throwIfDestroyedLocked();
                            UserState.AnonymousClass1 anonymousClass1 =
                                    userState.mPrinterDiscoverySession;
                            if (anonymousClass1 != null) {
                                anonymousClass1.onCustomPrinterIconLoadedLocked(printerId);
                            }
                        } finally {
                        }
                    }
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }

        public final void onPrintersAdded(ParceledListSlice parceledListSlice) {
            RemotePrintService remotePrintService = (RemotePrintService) this.mWeakService.get();
            if (remotePrintService != null) {
                List list = parceledListSlice.getList();
                ComponentName componentName = remotePrintService.mComponentName;
                int size = list.size();
                for (int i = 0; i < size; i++) {
                    throwIfPrinterIdTampered(componentName, ((PrinterInfo) list.get(i)).getId());
                }
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    UserState userState = remotePrintService.mCallbacks;
                    synchronized (userState.mLock) {
                        try {
                            userState.throwIfDestroyedLocked();
                            if (!userState.mActiveServices.isEmpty()) {
                                UserState.AnonymousClass1 anonymousClass1 =
                                        userState.mPrinterDiscoverySession;
                                if (anonymousClass1 != null) {
                                    anonymousClass1.onPrintersAddedLocked(list);
                                }
                            }
                        } finally {
                        }
                    }
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }

        public final void onPrintersRemoved(ParceledListSlice parceledListSlice) {
            RemotePrintService remotePrintService = (RemotePrintService) this.mWeakService.get();
            if (remotePrintService != null) {
                List list = parceledListSlice.getList();
                ComponentName componentName = remotePrintService.mComponentName;
                int size = list.size();
                for (int i = 0; i < size; i++) {
                    throwIfPrinterIdTampered(componentName, (PrinterId) list.get(i));
                }
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    UserState userState = remotePrintService.mCallbacks;
                    synchronized (userState.mLock) {
                        try {
                            userState.throwIfDestroyedLocked();
                            if (!userState.mActiveServices.isEmpty()) {
                                UserState.AnonymousClass1 anonymousClass1 =
                                        userState.mPrinterDiscoverySession;
                                if (anonymousClass1 != null) {
                                    anonymousClass1.onPrintersRemovedLocked(list);
                                }
                            }
                        } finally {
                        }
                    }
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }

        public final boolean setPrintJobState(PrintJobId printJobId, int i, String str) {
            RemotePrintService remotePrintService = (RemotePrintService) this.mWeakService.get();
            if (remotePrintService == null) {
                return false;
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return remotePrintService.mSpooler.setPrintJobState(printJobId, i, str);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final boolean setPrintJobTag(PrintJobId printJobId, String str) {
            RemotePrintService remotePrintService = (RemotePrintService) this.mWeakService.get();
            if (remotePrintService == null) {
                return false;
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return remotePrintService.mSpooler.setPrintJobTag(printJobId, str);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void setProgress(PrintJobId printJobId, float f) {
            RemotePrintService remotePrintService = (RemotePrintService) this.mWeakService.get();
            if (remotePrintService != null) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    remotePrintService.mSpooler.setProgress(printJobId, f);
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }

        public final void setStatus(PrintJobId printJobId, CharSequence charSequence) {
            RemotePrintService remotePrintService = (RemotePrintService) this.mWeakService.get();
            if (remotePrintService != null) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    remotePrintService.mSpooler.setStatus(printJobId, charSequence);
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }

        public final void setStatusRes(PrintJobId printJobId, int i, CharSequence charSequence) {
            RemotePrintService remotePrintService = (RemotePrintService) this.mWeakService.get();
            if (remotePrintService != null) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    remotePrintService.mSpooler.setStatus(printJobId, i, charSequence);
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }

        public final void writePrintJobData(
                ParcelFileDescriptor parcelFileDescriptor, PrintJobId printJobId) {
            RemotePrintService remotePrintService = (RemotePrintService) this.mWeakService.get();
            if (remotePrintService != null) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    remotePrintService.mSpooler.writePrintJobData(parcelFileDescriptor, printJobId);
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class RemoteServiceConneciton implements ServiceConnection {
        public RemoteServiceConneciton() {}

        @Override // android.content.ServiceConnection
        public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            List list;
            List list2;
            RemotePrintService remotePrintService = RemotePrintService.this;
            if (remotePrintService.mDestroyed || !remotePrintService.mBinding) {
                remotePrintService.mContext.unbindService(remotePrintService.mServiceConnection);
                return;
            }
            remotePrintService.mBinding = false;
            remotePrintService.mPrintService = IPrintService.Stub.asInterface(iBinder);
            try {
                iBinder.linkToDeath(RemotePrintService.this, 0);
                try {
                    RemotePrintService remotePrintService2 = RemotePrintService.this;
                    remotePrintService2.mPrintService.setClient(
                            remotePrintService2.mPrintServiceClient);
                    RemotePrintService remotePrintService3 = RemotePrintService.this;
                    if (remotePrintService3.mServiceDied
                            && remotePrintService3.mHasPrinterDiscoverySession) {
                        remotePrintService3.handleCreatePrinterDiscoverySession();
                    }
                    RemotePrintService remotePrintService4 = RemotePrintService.this;
                    if (remotePrintService4.mServiceDied
                            && (list2 = remotePrintService4.mDiscoveryPriorityList) != null) {
                        remotePrintService4.handleStartPrinterDiscovery(list2);
                    }
                    synchronized (RemotePrintService.this.mLock) {
                        try {
                            RemotePrintService remotePrintService5 = RemotePrintService.this;
                            if (remotePrintService5.mServiceDied
                                    && (list = remotePrintService5.mTrackedPrinterList) != null) {
                                int size = ((ArrayList) list).size();
                                for (int i = 0; i < size; i++) {
                                    RemotePrintService remotePrintService6 =
                                            RemotePrintService.this;
                                    remotePrintService6.handleStartPrinterStateTracking(
                                            (PrinterId)
                                                    ((ArrayList)
                                                                    remotePrintService6
                                                                            .mTrackedPrinterList)
                                                            .get(i));
                                }
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    while (!((ArrayList) RemotePrintService.this.mPendingCommands).isEmpty()) {
                        ((Runnable)
                                        ((ArrayList) RemotePrintService.this.mPendingCommands)
                                                .remove(0))
                                .run();
                    }
                    RemotePrintService remotePrintService7 = RemotePrintService.this;
                    if (!remotePrintService7.mHasPrinterDiscoverySession
                            && !remotePrintService7.mHasActivePrintJobs) {
                        remotePrintService7.ensureUnbound();
                    }
                    RemotePrintService.this.mServiceDied = false;
                } catch (RemoteException e) {
                    Slog.e("RemotePrintService", "Error setting client for: " + iBinder, e);
                    RemotePrintService.this.handleBinderDied();
                }
            } catch (RemoteException unused) {
                RemotePrintService.this.handleBinderDied();
            }
        }

        @Override // android.content.ServiceConnection
        public final void onServiceDisconnected(ComponentName componentName) {
            RemotePrintService.this.mBinding = true;
        }
    }

    public RemotePrintService(
            Context context,
            ComponentName componentName,
            int i,
            RemotePrintSpooler remotePrintSpooler,
            UserState userState) {
        this.mContext = context;
        this.mCallbacks = userState;
        this.mComponentName = componentName;
        this.mIntent = new Intent().setComponent(componentName);
        this.mUserId = i;
        this.mSpooler = remotePrintSpooler;
    }

    @Override // android.os.IBinder.DeathRecipient
    public final void binderDied() {
        Handler.getMain()
                .sendMessage(
                        PooledLambda.obtainMessage(
                                new RemotePrintService$$ExternalSyntheticLambda0(0), this));
    }

    public final void dump(DualDumpOutputStream dualDumpOutputStream) {
        DumpUtils.writeComponentName(
                dualDumpOutputStream, "component_name", 1146756268033L, this.mComponentName);
        dualDumpOutputStream.write("is_destroyed", 1133871366146L, this.mDestroyed);
        dualDumpOutputStream.write("is_bound", 1133871366147L, isBound());
        dualDumpOutputStream.write(
                "has_discovery_session", 1133871366148L, this.mHasPrinterDiscoverySession);
        dualDumpOutputStream.write(
                "has_active_print_jobs", 1133871366149L, this.mHasActivePrintJobs);
        dualDumpOutputStream.write(
                "is_discovering_printers", 1133871366150L, this.mDiscoveryPriorityList != null);
        synchronized (this.mLock) {
            try {
                List list = this.mTrackedPrinterList;
                if (list != null) {
                    int size = ((ArrayList) list).size();
                    for (int i = 0; i < size; i++) {
                        com.android.internal.print.DumpUtils.writePrinterId(
                                dualDumpOutputStream,
                                "tracked_printers",
                                2246267895815L,
                                (PrinterId) ((ArrayList) this.mTrackedPrinterList).get(i));
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void ensureBound() {
        if (isBound() || this.mBinding) {
            return;
        }
        this.mBinding = true;
        if (this.mContext.bindServiceAsUser(
                this.mIntent, this.mServiceConnection, 71307265, new UserHandle(this.mUserId))) {
            return;
        }
        this.mBinding = false;
        if (this.mServiceDied) {
            return;
        }
        handleBinderDied();
    }

    public final void ensureUnbound() {
        if (isBound() || this.mBinding) {
            this.mBinding = false;
            ((ArrayList) this.mPendingCommands).clear();
            this.mHasActivePrintJobs = false;
            this.mHasPrinterDiscoverySession = false;
            this.mDiscoveryPriorityList = null;
            synchronized (this.mLock) {
                this.mTrackedPrinterList = null;
            }
            if (isBound()) {
                try {
                    this.mPrintService.setClient((IPrintServiceClient) null);
                } catch (RemoteException unused) {
                }
                this.mPrintService.asBinder().unlinkToDeath(this, 0);
                this.mPrintService = null;
                this.mContext.unbindService(this.mServiceConnection);
            }
        }
    }

    public final void handleBinderDied() {
        IPrintService iPrintService = this.mPrintService;
        if (iPrintService != null) {
            iPrintService.asBinder().unlinkToDeath(this, 0);
        }
        this.mPrintService = null;
        this.mServiceDied = true;
        UserState userState = this.mCallbacks;
        synchronized (userState.mLock) {
            try {
                userState.throwIfDestroyedLocked();
                if (userState.mActiveServices.isEmpty()) {
                    return;
                }
                ComponentName componentName = this.mComponentName;
                if (Looper.getMainLooper().isCurrentThread()) {
                    BackgroundThread.getHandler()
                            .sendMessage(
                                    PooledLambda.obtainMessage(
                                            new UserState$$ExternalSyntheticLambda1(0),
                                            userState,
                                            componentName));
                } else {
                    userState.failScheduledPrintJobsForServiceInternal(componentName);
                }
                Handler.getMain()
                        .sendMessage(
                                PooledLambda.obtainMessage(
                                        new RemotePrintService$$ExternalSyntheticLambda0(2), this));
                userState.mActiveServices.remove(this.mComponentName);
                Handler.getMain()
                        .sendMessageDelayed(
                                PooledLambda.obtainMessage(
                                        new UserState$$ExternalSyntheticLambda0(1), userState),
                                500L);
                UserState.AnonymousClass1 anonymousClass1 = userState.mPrinterDiscoverySession;
                if (anonymousClass1 == null) {
                    return;
                }
                anonymousClass1.this$0$1.removeServiceLocked(this);
            } finally {
            }
        }
    }

    public final void handleCreatePrinterDiscoverySession() {
        this.mHasPrinterDiscoverySession = true;
        if (isBound()) {
            try {
                this.mPrintService.createPrinterDiscoverySession();
                return;
            } catch (RemoteException e) {
                Slog.e("RemotePrintService", "Error creating printer discovery session.", e);
                return;
            }
        }
        ensureBound();
        ((ArrayList) this.mPendingCommands).add(new AnonymousClass1(this, 1));
    }

    public final void handleDestroyPrinterDiscoverySession() {
        this.mHasPrinterDiscoverySession = false;
        if (isBound()) {
            try {
                this.mPrintService.destroyPrinterDiscoverySession();
            } catch (RemoteException e) {
                Slog.e("RemotePrintService", "Error destroying printer dicovery session.", e);
            }
            if (this.mHasActivePrintJobs) {
                return;
            }
            ensureUnbound();
            return;
        }
        if (this.mServiceDied && !this.mHasActivePrintJobs) {
            ensureUnbound();
            return;
        }
        ensureBound();
        ((ArrayList) this.mPendingCommands).add(new AnonymousClass1(this, 2));
    }

    public final void handleOnAllPrintJobsHandled() {
        this.mHasActivePrintJobs = false;
        if (isBound()) {
            if (this.mHasPrinterDiscoverySession) {
                return;
            }
            ensureUnbound();
        } else {
            if (this.mServiceDied && !this.mHasPrinterDiscoverySession) {
                ensureUnbound();
                return;
            }
            ensureBound();
            ((ArrayList) this.mPendingCommands).add(new AnonymousClass1(this, 0));
        }
    }

    public final void handleOnPrintJobQueued(PrintJobInfo printJobInfo) {
        this.mHasActivePrintJobs = true;
        if (isBound()) {
            try {
                this.mPrintService.onPrintJobQueued(printJobInfo);
                return;
            } catch (RemoteException e) {
                Slog.e("RemotePrintService", "Error announcing queued pring job.", e);
                return;
            }
        }
        ensureBound();
        ((ArrayList) this.mPendingCommands).add(new AnonymousClass2(this, printJobInfo, 1));
    }

    public final void handleRequestCancelPrintJob(PrintJobInfo printJobInfo) {
        if (isBound()) {
            try {
                this.mPrintService.requestCancelPrintJob(printJobInfo);
                return;
            } catch (RemoteException e) {
                Slog.e("RemotePrintService", "Error canceling a pring job.", e);
                return;
            }
        }
        ensureBound();
        ((ArrayList) this.mPendingCommands).add(new AnonymousClass2(this, printJobInfo, 0));
    }

    public final void handleRequestCustomPrinterIcon(final PrinterId printerId) {
        if (!isBound()) {
            ensureBound();
            ((ArrayList) this.mPendingCommands)
                    .add(
                            new Runnable() { // from class:
                                             // com.android.server.print.RemotePrintService$$ExternalSyntheticLambda9
                                @Override // java.lang.Runnable
                                public final void run() {
                                    RemotePrintService.this.handleRequestCustomPrinterIcon(
                                            printerId);
                                }
                            });
            return;
        }
        try {
            this.mPrintService.requestCustomPrinterIcon(printerId);
        } catch (RemoteException e) {
            Slog.e("RemotePrintService", "Error requesting icon for " + printerId, e);
        }
    }

    public final void handleStartPrinterDiscovery(List list) {
        ArrayList arrayList = new ArrayList();
        this.mDiscoveryPriorityList = arrayList;
        if (list != null) {
            arrayList.addAll(list);
        }
        if (isBound()) {
            try {
                this.mPrintService.startPrinterDiscovery(list);
                return;
            } catch (RemoteException e) {
                Slog.e("RemotePrintService", "Error starting printer dicovery.", e);
                return;
            }
        }
        ensureBound();
        ((ArrayList) this.mPendingCommands).add(new AnonymousClass6(this, list, 0));
    }

    public final void handleStartPrinterStateTracking(PrinterId printerId) {
        synchronized (this.mLock) {
            try {
                if (this.mTrackedPrinterList == null) {
                    this.mTrackedPrinterList = new ArrayList();
                }
                ((ArrayList) this.mTrackedPrinterList).add(printerId);
            } catch (Throwable th) {
                throw th;
            }
        }
        if (isBound()) {
            try {
                this.mPrintService.startPrinterStateTracking(printerId);
                return;
            } catch (RemoteException e) {
                Slog.e("RemotePrintService", "Error requesting start printer tracking.", e);
                return;
            }
        }
        ensureBound();
        ((ArrayList) this.mPendingCommands).add(new AnonymousClass9(this, printerId, 0));
    }

    public final void handleStopPrinterDiscovery() {
        this.mDiscoveryPriorityList = null;
        if (!isBound()) {
            ensureBound();
            ((ArrayList) this.mPendingCommands).add(new AnonymousClass1(this, 3));
            return;
        }
        stopTrackingAllPrinters();
        try {
            this.mPrintService.stopPrinterDiscovery();
        } catch (RemoteException e) {
            Slog.e("RemotePrintService", "Error stopping printer discovery.", e);
        }
    }

    public final void handleStopPrinterStateTracking(PrinterId printerId) {
        synchronized (this.mLock) {
            try {
                List list = this.mTrackedPrinterList;
                if (list != null && list.remove(printerId)) {
                    if (this.mTrackedPrinterList.isEmpty()) {
                        this.mTrackedPrinterList = null;
                    }
                    if (isBound()) {
                        try {
                            this.mPrintService.stopPrinterStateTracking(printerId);
                            return;
                        } catch (RemoteException e) {
                            Slog.e(
                                    "RemotePrintService",
                                    "Error requesting stop printer tracking.",
                                    e);
                            return;
                        }
                    }
                    ensureBound();
                    ((ArrayList) this.mPendingCommands)
                            .add(new AnonymousClass9(this, printerId, 1));
                }
            } finally {
            }
        }
    }

    public final void handleValidatePrinters(List list) {
        if (isBound()) {
            try {
                this.mPrintService.validatePrinters(list);
                return;
            } catch (RemoteException e) {
                Slog.e("RemotePrintService", "Error requesting printers validation.", e);
                return;
            }
        }
        ensureBound();
        ((ArrayList) this.mPendingCommands).add(new AnonymousClass6(this, list, 1));
    }

    public final boolean isBound() {
        return this.mPrintService != null;
    }

    public final void stopTrackingAllPrinters() {
        synchronized (this.mLock) {
            try {
                List list = this.mTrackedPrinterList;
                if (list == null) {
                    return;
                }
                for (int size = ((ArrayList) list).size() - 1; size >= 0; size--) {
                    PrinterId printerId =
                            (PrinterId) ((ArrayList) this.mTrackedPrinterList).get(size);
                    if (printerId.getServiceName().equals(this.mComponentName)) {
                        handleStopPrinterStateTracking(printerId);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
