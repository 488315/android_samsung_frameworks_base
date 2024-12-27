package com.android.server.print;

import android.app.ActivityOptions;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.ParceledListSlice;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.UserHandle;
import android.print.IPrintDocumentAdapter;
import android.print.IPrintJobStateChangeListener;
import android.print.IPrintServicesChangeListener;
import android.print.IPrinterDiscoveryObserver;
import android.print.PrintAttributes;
import android.print.PrintJobId;
import android.print.PrintJobInfo;
import android.print.PrinterId;
import android.print.PrinterInfo;
import android.printservice.PrintServiceInfo;
import android.printservice.recommendation.IRecommendationsChangeListener;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import android.util.Slog;
import android.util.SparseArray;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.os.BackgroundThread;
import com.android.internal.print.DumpUtils;
import com.android.internal.util.dump.DualDumpOutputStream;
import com.android.internal.util.function.pooled.PooledLambda;
import com.android.server.print.UserState;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeoutException;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class UserState {
    public final Context mContext;
    public boolean mDestroyed;
    public boolean mIsInstantServiceAllowed;
    public final Object mLock;
    public List mPrintJobStateChangeListenerRecords;
    public List mPrintServiceRecommendations;
    public List mPrintServiceRecommendationsChangeListenerRecords;
    public RemotePrintServiceRecommendationService mPrintServiceRecommendationsService;
    public List mPrintServicesChangeListenerRecords;
    public AnonymousClass1 mPrinterDiscoverySession;
    public final RemotePrintSpooler mSpooler;
    public final int mUserId;
    public final TextUtils.SimpleStringSplitter mStringColonSplitter = new TextUtils.SimpleStringSplitter(':');
    public final Intent mQueryIntent = new Intent("android.printservice.PrintService");
    public final ArrayMap mActiveServices = new ArrayMap();
    public final List mInstalledServices = new ArrayList();
    public final Set mDisabledServices = new ArraySet();
    public final PrintJobForAppCache mPrintJobForAppCache = new PrintJobForAppCache();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.print.UserState$1, reason: invalid class name */
    public final class AnonymousClass1 {
        public boolean mIsDestroyed;
        public final /* synthetic */ UserState this$0$1;
        public final ArrayMap mPrinters = new ArrayMap();
        public final UserState$PrinterDiscoverySessionMediator$1 mDiscoveryObservers = new RemoteCallbackList() { // from class: com.android.server.print.UserState$PrinterDiscoverySessionMediator$1
            @Override // android.os.RemoteCallbackList
            public final void onCallbackDied(IInterface iInterface) {
                IPrinterDiscoveryObserver iPrinterDiscoveryObserver = (IPrinterDiscoveryObserver) iInterface;
                synchronized (UserState.AnonymousClass1.this.this$0$1.mLock) {
                    UserState.AnonymousClass1.this.stopPrinterDiscoveryLocked(iPrinterDiscoveryObserver);
                    UserState.AnonymousClass1 anonymousClass1 = UserState.AnonymousClass1.this;
                    UserState$PrinterDiscoverySessionMediator$1 userState$PrinterDiscoverySessionMediator$1 = anonymousClass1.mDiscoveryObservers;
                    userState$PrinterDiscoverySessionMediator$1.unregister(iPrinterDiscoveryObserver);
                    if (userState$PrinterDiscoverySessionMediator$1.getRegisteredCallbackCount() == 0) {
                        anonymousClass1.destroyLocked();
                    }
                }
            }
        };
        public final List mStartedPrinterDiscoveryTokens = new ArrayList();
        public final List mStateTrackedPrinters = new ArrayList();

        /* JADX WARN: Type inference failed for: r0v1, types: [com.android.server.print.UserState$PrinterDiscoverySessionMediator$1] */
        public AnonymousClass1() {
            this.this$0$1 = UserState.this;
            Handler.getMain().sendMessage(PooledLambda.obtainMessage(new UserState$$ExternalSyntheticLambda1(2), this, new ArrayList(UserState.this.mActiveServices.values())));
        }

        public final void addObserverLocked(IPrinterDiscoveryObserver iPrinterDiscoveryObserver) {
            register(iPrinterDiscoveryObserver);
            if (this.mPrinters.isEmpty()) {
                return;
            }
            Handler.getMain().sendMessage(PooledLambda.obtainMessage(new UserState$$ExternalSyntheticLambda4(4), this, iPrinterDiscoveryObserver, new ArrayList(this.mPrinters.values())));
        }

        public final void destroyLocked() {
            UserState userState;
            if (this.mIsDestroyed) {
                Log.w("UserState", "Not destroying - session destroyed");
                return;
            }
            this.mIsDestroyed = true;
            int size = ((ArrayList) this.mStateTrackedPrinters).size();
            int i = 0;
            while (true) {
                userState = this.this$0$1;
                if (i >= size) {
                    break;
                }
                userState.stopPrinterStateTracking((PrinterId) ((ArrayList) this.mStateTrackedPrinters).get(i));
                i++;
            }
            int size2 = ((ArrayList) this.mStartedPrinterDiscoveryTokens).size();
            for (int i2 = 0; i2 < size2; i2++) {
                stopPrinterDiscoveryLocked(IPrinterDiscoveryObserver.Stub.asInterface((IBinder) ((ArrayList) this.mStartedPrinterDiscoveryTokens).get(i2)));
            }
            Handler.getMain().sendMessage(PooledLambda.obtainMessage(new UserState$$ExternalSyntheticLambda1(7), this, new ArrayList(userState.mActiveServices.values())));
        }

        public final void dumpLocked(DualDumpOutputStream dualDumpOutputStream) {
            UserState userState = this.this$0$1;
            dualDumpOutputStream.write("is_destroyed", 1133871366145L, userState.mDestroyed);
            dualDumpOutputStream.write("is_printer_discovery_in_progress", 1133871366146L, !((ArrayList) this.mStartedPrinterDiscoveryTokens).isEmpty());
            UserState$PrinterDiscoverySessionMediator$1 userState$PrinterDiscoverySessionMediator$1 = this.mDiscoveryObservers;
            int beginBroadcast = userState$PrinterDiscoverySessionMediator$1.beginBroadcast();
            for (int i = 0; i < beginBroadcast; i++) {
                dualDumpOutputStream.write("printer_discovery_observers", 2237677961219L, userState$PrinterDiscoverySessionMediator$1.getBroadcastItem(i).toString());
            }
            userState$PrinterDiscoverySessionMediator$1.finishBroadcast();
            int size = ((ArrayList) this.mStartedPrinterDiscoveryTokens).size();
            for (int i2 = 0; i2 < size; i2++) {
                dualDumpOutputStream.write("discovery_requests", 2237677961220L, ((IBinder) ((ArrayList) this.mStartedPrinterDiscoveryTokens).get(i2)).toString());
            }
            int size2 = ((ArrayList) this.mStateTrackedPrinters).size();
            for (int i3 = 0; i3 < size2; i3++) {
                DumpUtils.writePrinterId(dualDumpOutputStream, "tracked_printer_requests", 2246267895813L, (PrinterId) ((ArrayList) this.mStateTrackedPrinters).get(i3));
            }
            int size3 = this.mPrinters.size();
            for (int i4 = 0; i4 < size3; i4++) {
                DumpUtils.writePrinterInfo(userState.mContext, dualDumpOutputStream, "printer", 2246267895814L, (PrinterInfo) this.mPrinters.valueAt(i4));
            }
        }

        public final void handleDispatchPrintersAdded(List list) {
            UserState$PrinterDiscoverySessionMediator$1 userState$PrinterDiscoverySessionMediator$1 = this.mDiscoveryObservers;
            int beginBroadcast = userState$PrinterDiscoverySessionMediator$1.beginBroadcast();
            for (int i = 0; i < beginBroadcast; i++) {
                try {
                    userState$PrinterDiscoverySessionMediator$1.getBroadcastItem(i).onPrintersAdded(new ParceledListSlice(list));
                } catch (RemoteException e) {
                    Log.e("UserState", "Error sending added printers", e);
                }
            }
            userState$PrinterDiscoverySessionMediator$1.finishBroadcast();
        }

        public final void onCustomPrinterIconLoadedLocked(PrinterId printerId) {
            if (this.mIsDestroyed) {
                Log.w("UserState", "Not updating printer - session destroyed");
                return;
            }
            PrinterInfo printerInfo = (PrinterInfo) this.mPrinters.get(printerId);
            if (printerInfo != null) {
                PrinterInfo build = new PrinterInfo.Builder(printerInfo).incCustomPrinterIconGen().build();
                this.mPrinters.put(printerId, build);
                ArrayList arrayList = new ArrayList(1);
                arrayList.add(build);
                Handler.getMain().sendMessage(PooledLambda.obtainMessage(new UserState$$ExternalSyntheticLambda1(3), this, arrayList));
            }
        }

        public final void onPrintersAddedLocked(List list) {
            if (this.mIsDestroyed) {
                Log.w("UserState", "Not adding printers - session destroyed");
                return;
            }
            int size = list.size();
            ArrayList arrayList = null;
            for (int i = 0; i < size; i++) {
                PrinterInfo printerInfo = (PrinterInfo) list.get(i);
                PrinterInfo printerInfo2 = (PrinterInfo) this.mPrinters.put(printerInfo.getId(), printerInfo);
                if (printerInfo2 == null || !printerInfo2.equals(printerInfo)) {
                    if (arrayList == null) {
                        arrayList = new ArrayList();
                    }
                    arrayList.add(printerInfo);
                }
            }
            if (arrayList != null) {
                Handler.getMain().sendMessage(PooledLambda.obtainMessage(new UserState$$ExternalSyntheticLambda1(4), this, arrayList));
            }
        }

        public final void onPrintersRemovedLocked(List list) {
            if (this.mIsDestroyed) {
                Log.w("UserState", "Not removing printers - session destroyed");
                return;
            }
            int size = list.size();
            ArrayList arrayList = null;
            for (int i = 0; i < size; i++) {
                PrinterId printerId = (PrinterId) list.get(i);
                if (this.mPrinters.remove(printerId) != null) {
                    if (arrayList == null) {
                        arrayList = new ArrayList();
                    }
                    arrayList.add(printerId);
                }
            }
            if (arrayList != null) {
                Handler.getMain().sendMessage(PooledLambda.obtainMessage(new UserState$$ExternalSyntheticLambda1(9), this, arrayList));
            }
        }

        public final void startPrinterDiscoveryLocked(IPrinterDiscoveryObserver iPrinterDiscoveryObserver, List list) {
            if (this.mIsDestroyed) {
                Log.w("UserState", "Not starting dicovery - session destroyed");
                return;
            }
            boolean z = !((ArrayList) this.mStartedPrinterDiscoveryTokens).isEmpty();
            ((ArrayList) this.mStartedPrinterDiscoveryTokens).add(iPrinterDiscoveryObserver.asBinder());
            UserState userState = this.this$0$1;
            if (z && list != null && !list.isEmpty()) {
                userState.validatePrinters(list);
            } else {
                if (((ArrayList) this.mStartedPrinterDiscoveryTokens).size() > 1) {
                    return;
                }
                Handler.getMain().sendMessage(PooledLambda.obtainMessage(new UserState$$ExternalSyntheticLambda4(3), this, new ArrayList(userState.mActiveServices.values()), list));
            }
        }

        public final void startPrinterStateTrackingLocked(PrinterId printerId) {
            RemotePrintService remotePrintService;
            if (this.mIsDestroyed) {
                Log.w("UserState", "Not starting printer state tracking - session destroyed");
                return;
            }
            if (((ArrayList) this.mStartedPrinterDiscoveryTokens).isEmpty()) {
                return;
            }
            boolean contains = ((ArrayList) this.mStateTrackedPrinters).contains(printerId);
            ((ArrayList) this.mStateTrackedPrinters).add(printerId);
            if (contains || (remotePrintService = (RemotePrintService) this.this$0$1.mActiveServices.get(printerId.getServiceName())) == null) {
                return;
            }
            Handler.getMain().sendMessage(PooledLambda.obtainMessage(new UserState$$ExternalSyntheticLambda4(5), this, remotePrintService, printerId));
        }

        public final void stopPrinterDiscoveryLocked(IPrinterDiscoveryObserver iPrinterDiscoveryObserver) {
            if (this.mIsDestroyed) {
                Log.w("UserState", "Not stopping dicovery - session destroyed");
                return;
            }
            if (((ArrayList) this.mStartedPrinterDiscoveryTokens).remove(iPrinterDiscoveryObserver.asBinder()) && ((ArrayList) this.mStartedPrinterDiscoveryTokens).isEmpty()) {
                Handler.getMain().sendMessage(PooledLambda.obtainMessage(new UserState$$ExternalSyntheticLambda1(8), this, new ArrayList(this.this$0$1.mActiveServices.values())));
            }
        }

        public final void stopPrinterStateTrackingLocked(PrinterId printerId) {
            RemotePrintService remotePrintService;
            if (this.mIsDestroyed) {
                Log.w("UserState", "Not stopping printer state tracking - session destroyed");
            } else {
                if (((ArrayList) this.mStartedPrinterDiscoveryTokens).isEmpty() || !((ArrayList) this.mStateTrackedPrinters).remove(printerId) || (remotePrintService = (RemotePrintService) this.this$0$1.mActiveServices.get(printerId.getServiceName())) == null) {
                    return;
                }
                Handler.getMain().sendMessage(PooledLambda.obtainMessage(new UserState$$ExternalSyntheticLambda4(2), this, remotePrintService, printerId));
            }
        }

        public final void validatePrintersLocked(List list) {
            if (this.mIsDestroyed) {
                Log.w("UserState", "Not validating pritners - session destroyed");
                return;
            }
            ArrayList arrayList = new ArrayList(list);
            while (!arrayList.isEmpty()) {
                Iterator it = arrayList.iterator();
                ArrayList arrayList2 = new ArrayList();
                ComponentName componentName = null;
                while (it.hasNext()) {
                    PrinterId printerId = (PrinterId) it.next();
                    if (printerId != null) {
                        if (arrayList2.isEmpty()) {
                            arrayList2.add(printerId);
                            componentName = printerId.getServiceName();
                            it.remove();
                        } else if (printerId.getServiceName().equals(componentName)) {
                            arrayList2.add(printerId);
                            it.remove();
                        }
                    }
                }
                RemotePrintService remotePrintService = (RemotePrintService) this.this$0$1.mActiveServices.get(componentName);
                if (remotePrintService != null) {
                    Handler.getMain().sendMessage(PooledLambda.obtainMessage(new UserState$$ExternalSyntheticLambda4(1), this, remotePrintService, arrayList2));
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.print.UserState$2, reason: invalid class name */
    public final class AnonymousClass2 implements IBinder.DeathRecipient {
        public final int appId;
        public final IPrintJobStateChangeListener listener;

        public AnonymousClass2(IPrintJobStateChangeListener iPrintJobStateChangeListener, int i) {
            this.listener = iPrintJobStateChangeListener;
            this.appId = i;
            iPrintJobStateChangeListener.asBinder().linkToDeath(this, 0);
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            this.listener.asBinder().unlinkToDeath(this, 0);
            synchronized (UserState.this.mLock) {
                List list = UserState.this.mPrintJobStateChangeListenerRecords;
                if (list != null) {
                    ((ArrayList) list).remove(this);
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.print.UserState$3, reason: invalid class name */
    public final class AnonymousClass3 implements IBinder.DeathRecipient {
        public final /* synthetic */ int $r8$classId;
        public final IInterface listener;
        public final /* synthetic */ UserState this$0;

        public AnonymousClass3(IInterface iInterface) {
            this.listener = iInterface;
            iInterface.asBinder().linkToDeath(this, 0);
        }

        /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
        public AnonymousClass3(UserState userState, IPrintServicesChangeListener iPrintServicesChangeListener) {
            this(iPrintServicesChangeListener);
            this.$r8$classId = 0;
            this.this$0 = userState;
        }

        /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
        public AnonymousClass3(UserState userState, IRecommendationsChangeListener iRecommendationsChangeListener) {
            this(iRecommendationsChangeListener);
            this.$r8$classId = 1;
            this.this$0 = userState;
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            this.listener.asBinder().unlinkToDeath(this, 0);
            switch (this.$r8$classId) {
                case 0:
                    synchronized (this.this$0.mLock) {
                        List list = this.this$0.mPrintServicesChangeListenerRecords;
                        if (list != null) {
                            ((ArrayList) list).remove(this);
                        }
                    }
                    return;
                default:
                    synchronized (this.this$0.mLock) {
                        List list2 = this.this$0.mPrintServiceRecommendationsChangeListenerRecords;
                        if (list2 != null) {
                            ((ArrayList) list2).remove(this);
                        }
                    }
                    return;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PrintJobForAppCache {
        public final SparseArray mPrintJobsForRunningApp = new SparseArray();

        public PrintJobForAppCache() {
        }

        public final void dumpLocked(DualDumpOutputStream dualDumpOutputStream) {
            int size = this.mPrintJobsForRunningApp.size();
            int i = 0;
            while (i < size) {
                int keyAt = this.mPrintJobsForRunningApp.keyAt(i);
                List list = (List) this.mPrintJobsForRunningApp.valueAt(i);
                int size2 = list.size();
                int i2 = 0;
                while (i2 < size2) {
                    long start = dualDumpOutputStream.start("cached_print_jobs", 2246267895813L);
                    dualDumpOutputStream.write("app_id", 1120986464257L, keyAt);
                    DumpUtils.writePrintJobInfo(UserState.this.mContext, dualDumpOutputStream, "print_job", 1146756268034L, (PrintJobInfo) list.get(i2));
                    dualDumpOutputStream.end(start);
                    i2++;
                    i = i;
                }
                i++;
            }
        }
    }

    public UserState(Context context, int i, Object obj, boolean z) {
        this.mContext = context;
        this.mUserId = i;
        this.mLock = obj;
        this.mSpooler = new RemotePrintSpooler(context, i, z, this);
        synchronized (obj) {
            readInstalledPrintServicesLocked();
            upgradePersistentStateIfNeeded();
            readDisabledPrintServicesLocked();
        }
        prunePrintServices();
        synchronized (obj) {
            onConfigurationChangedLocked();
        }
    }

    public final void addPrintServiceRecommendationsChangeListener(IRecommendationsChangeListener iRecommendationsChangeListener) {
        synchronized (this.mLock) {
            try {
                throwIfDestroyedLocked();
                if (this.mPrintServiceRecommendationsChangeListenerRecords == null) {
                    this.mPrintServiceRecommendationsChangeListenerRecords = new ArrayList();
                    this.mPrintServiceRecommendationsService = new RemotePrintServiceRecommendationService(this.mContext, UserHandle.of(this.mUserId), this);
                }
                this.mPrintServiceRecommendationsChangeListenerRecords.add(new AnonymousClass3(this, iRecommendationsChangeListener));
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void cancelPrintJob(PrintJobId printJobId, int i) {
        RemotePrintService remotePrintService;
        PrintJobInfo printJobInfo = this.mSpooler.getPrintJobInfo(printJobId, i);
        if (printJobInfo == null) {
            return;
        }
        this.mSpooler.setPrintJobCancelling(printJobId);
        if (printJobInfo.getState() == 6) {
            this.mSpooler.setPrintJobState(printJobId, 7, null);
            return;
        }
        PrinterId printerId = printJobInfo.getPrinterId();
        if (printerId != null) {
            ComponentName serviceName = printerId.getServiceName();
            synchronized (this.mLock) {
                remotePrintService = (RemotePrintService) this.mActiveServices.get(serviceName);
            }
            if (remotePrintService == null) {
                return;
            }
            Handler.getMain().sendMessage(PooledLambda.obtainMessage(new RemotePrintService$$ExternalSyntheticLambda1(3), remotePrintService, printJobInfo));
        }
    }

    public final void destroyLocked() {
        throwIfDestroyedLocked();
        RemotePrintSpooler remotePrintSpooler = this.mSpooler;
        remotePrintSpooler.throwIfCalledOnMainThread();
        synchronized (remotePrintSpooler.mLock) {
            remotePrintSpooler.throwIfDestroyedLocked();
            remotePrintSpooler.unbindLocked();
            remotePrintSpooler.mDestroyed = true;
            remotePrintSpooler.mCanUnbind = false;
        }
        for (RemotePrintService remotePrintService : this.mActiveServices.values()) {
            remotePrintService.getClass();
            Handler.getMain().sendMessage(PooledLambda.obtainMessage(new RemotePrintService$$ExternalSyntheticLambda0(3), remotePrintService));
        }
        this.mActiveServices.clear();
        ((ArrayList) this.mInstalledServices).clear();
        ((ArraySet) this.mDisabledServices).clear();
        AnonymousClass1 anonymousClass1 = this.mPrinterDiscoverySession;
        if (anonymousClass1 != null) {
            anonymousClass1.destroyLocked();
            this.mPrinterDiscoverySession = null;
        }
        this.mDestroyed = true;
    }

    public final void failScheduledPrintJobsForServiceInternal(ComponentName componentName) {
        RemotePrintSpooler remotePrintSpooler = this.mSpooler;
        List printJobInfos = remotePrintSpooler.getPrintJobInfos(-4, -2, componentName);
        if (printJobInfos == null) {
            return;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            int size = printJobInfos.size();
            for (int i = 0; i < size; i++) {
                remotePrintSpooler.setPrintJobState(((PrintJobInfo) printJobInfos.get(i)).getId(), 6, this.mContext.getString(17042517));
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final Icon getCustomPrinterIcon(PrinterId printerId) {
        Icon icon;
        RemotePrintService remotePrintService;
        RemotePrintSpooler remotePrintSpooler = this.mSpooler;
        remotePrintSpooler.throwIfCalledOnMainThread();
        synchronized (remotePrintSpooler.mLock) {
            remotePrintSpooler.throwIfDestroyedLocked();
            remotePrintSpooler.mCanUnbind = false;
        }
        try {
            try {
                icon = remotePrintSpooler.mGetCustomPrinterIconCaller.getCustomPrinterIcon(remotePrintSpooler.getRemoteInstanceLazy(), printerId);
                synchronized (remotePrintSpooler.mLock) {
                    remotePrintSpooler.mCanUnbind = true;
                    remotePrintSpooler.mLock.notifyAll();
                }
            } catch (RemoteException | InterruptedException | TimeoutException e) {
                Slog.e("RemotePrintSpooler", "Error getting custom printer icon.", e);
                synchronized (remotePrintSpooler.mLock) {
                    remotePrintSpooler.mCanUnbind = true;
                    remotePrintSpooler.mLock.notifyAll();
                    icon = null;
                }
            }
            if (icon == null && (remotePrintService = (RemotePrintService) this.mActiveServices.get(printerId.getServiceName())) != null) {
                Handler.getMain().sendMessage(PooledLambda.obtainMessage(new RemotePrintService$$ExternalSyntheticLambda1(0), remotePrintService, printerId));
            }
            return icon;
        } catch (Throwable th) {
            synchronized (remotePrintSpooler.mLock) {
                remotePrintSpooler.mCanUnbind = true;
                remotePrintSpooler.mLock.notifyAll();
                throw th;
            }
        }
    }

    public final ArrayList getInstalledComponents() {
        ArrayList arrayList = new ArrayList();
        int size = this.mInstalledServices.size();
        for (int i = 0; i < size; i++) {
            ServiceInfo serviceInfo = ((PrintServiceInfo) this.mInstalledServices.get(i)).getResolveInfo().serviceInfo;
            arrayList.add(new ComponentName(serviceInfo.packageName, serviceInfo.name));
        }
        return arrayList;
    }

    public final PrintJobInfo getPrintJobInfo(PrintJobId printJobId, int i) {
        PrintJobInfo printJobInfo;
        PrintJobForAppCache printJobForAppCache = this.mPrintJobForAppCache;
        synchronized (UserState.this.mLock) {
            try {
                List list = (List) printJobForAppCache.mPrintJobsForRunningApp.get(i);
                if (list != null) {
                    int size = list.size();
                    for (int i2 = 0; i2 < size; i2++) {
                        printJobInfo = (PrintJobInfo) list.get(i2);
                        if (!printJobInfo.getId().equals(printJobId)) {
                        }
                    }
                }
                printJobInfo = null;
            } finally {
            }
        }
        if (printJobInfo == null) {
            printJobInfo = this.mSpooler.getPrintJobInfo(printJobId, i);
        }
        if (printJobInfo != null) {
            printJobInfo.setTag(null);
            printJobInfo.setAdvancedOptions(null);
        }
        return printJobInfo;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r6v3, types: [java.util.List] */
    public final List getPrintJobInfos(int i) {
        int i2;
        ArrayList arrayList;
        ArrayList arrayList2;
        PrintJobForAppCache printJobForAppCache = this.mPrintJobForAppCache;
        synchronized (UserState.this.mLock) {
            try {
                if (i == -2) {
                    int size = printJobForAppCache.mPrintJobsForRunningApp.size();
                    int i3 = 0;
                    arrayList = null;
                    while (i3 < size) {
                        List list = (List) printJobForAppCache.mPrintJobsForRunningApp.valueAt(i3);
                        if (arrayList == null) {
                            arrayList = new ArrayList();
                        }
                        arrayList.addAll(list);
                        i3++;
                        arrayList = arrayList;
                    }
                } else {
                    List list2 = (List) printJobForAppCache.mPrintJobsForRunningApp.get(i);
                    if (list2 != null) {
                        arrayList = new ArrayList();
                        arrayList.addAll(list2);
                    } else {
                        arrayList = null;
                    }
                }
                if (arrayList == null) {
                    arrayList2 = Collections.emptyList();
                }
            } finally {
            }
        }
        ArrayMap arrayMap = new ArrayMap();
        int size2 = arrayList2.size();
        for (int i4 = 0; i4 < size2; i4++) {
            PrintJobInfo printJobInfo = (PrintJobInfo) arrayList2.get(i4);
            arrayMap.put(printJobInfo.getId(), printJobInfo);
            printJobInfo.setTag(null);
            printJobInfo.setAdvancedOptions(null);
        }
        List printJobInfos = this.mSpooler.getPrintJobInfos(-1, i, null);
        if (printJobInfos != null) {
            int size3 = printJobInfos.size();
            for (i2 = 0; i2 < size3; i2++) {
                PrintJobInfo printJobInfo2 = (PrintJobInfo) printJobInfos.get(i2);
                arrayMap.put(printJobInfo2.getId(), printJobInfo2);
                printJobInfo2.setTag(null);
                printJobInfo2.setAdvancedOptions(null);
            }
        }
        return new ArrayList(arrayMap.values());
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0049 A[Catch: all -> 0x004f, TryCatch #0 {all -> 0x004f, blocks: (B:4:0x0003, B:6:0x000f, B:8:0x003d, B:12:0x0054, B:14:0x0049, B:15:0x0051, B:17:0x0042, B:22:0x0057), top: B:3:0x0003 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.util.List getPrintServices(int r9) {
        /*
            r8 = this;
            java.lang.Object r0 = r8.mLock
            monitor-enter(r0)
            java.util.List r1 = r8.mInstalledServices     // Catch: java.lang.Throwable -> L4f
            java.util.ArrayList r1 = (java.util.ArrayList) r1     // Catch: java.lang.Throwable -> L4f
            int r1 = r1.size()     // Catch: java.lang.Throwable -> L4f
            r2 = 0
            r3 = 0
        Ld:
            if (r3 >= r1) goto L57
            java.util.List r4 = r8.mInstalledServices     // Catch: java.lang.Throwable -> L4f
            java.util.ArrayList r4 = (java.util.ArrayList) r4     // Catch: java.lang.Throwable -> L4f
            java.lang.Object r4 = r4.get(r3)     // Catch: java.lang.Throwable -> L4f
            android.printservice.PrintServiceInfo r4 = (android.printservice.PrintServiceInfo) r4     // Catch: java.lang.Throwable -> L4f
            android.content.ComponentName r5 = new android.content.ComponentName     // Catch: java.lang.Throwable -> L4f
            android.content.pm.ResolveInfo r6 = r4.getResolveInfo()     // Catch: java.lang.Throwable -> L4f
            android.content.pm.ServiceInfo r6 = r6.serviceInfo     // Catch: java.lang.Throwable -> L4f
            java.lang.String r6 = r6.packageName     // Catch: java.lang.Throwable -> L4f
            android.content.pm.ResolveInfo r7 = r4.getResolveInfo()     // Catch: java.lang.Throwable -> L4f
            android.content.pm.ServiceInfo r7 = r7.serviceInfo     // Catch: java.lang.Throwable -> L4f
            java.lang.String r7 = r7.name     // Catch: java.lang.Throwable -> L4f
            r5.<init>(r6, r7)     // Catch: java.lang.Throwable -> L4f
            android.util.ArrayMap r6 = r8.mActiveServices     // Catch: java.lang.Throwable -> L4f
            boolean r5 = r6.containsKey(r5)     // Catch: java.lang.Throwable -> L4f
            r4.setIsEnabled(r5)     // Catch: java.lang.Throwable -> L4f
            boolean r5 = r4.isEnabled()     // Catch: java.lang.Throwable -> L4f
            if (r5 == 0) goto L42
            r5 = r9 & 1
            if (r5 != 0) goto L47
            goto L54
        L42:
            r5 = r9 & 2
            if (r5 != 0) goto L47
            goto L54
        L47:
            if (r2 != 0) goto L51
            java.util.ArrayList r2 = new java.util.ArrayList     // Catch: java.lang.Throwable -> L4f
            r2.<init>()     // Catch: java.lang.Throwable -> L4f
            goto L51
        L4f:
            r8 = move-exception
            goto L59
        L51:
            r2.add(r4)     // Catch: java.lang.Throwable -> L4f
        L54:
            int r3 = r3 + 1
            goto Ld
        L57:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L4f
            return r2
        L59:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L4f
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.print.UserState.getPrintServices(int):java.util.List");
    }

    public final void onConfigurationChangedLocked() {
        ArrayList installedComponents = getInstalledComponents();
        int size = installedComponents.size();
        for (int i = 0; i < size; i++) {
            ComponentName componentName = (ComponentName) installedComponents.get(i);
            if (this.mDisabledServices.contains(componentName)) {
                RemotePrintService remotePrintService = (RemotePrintService) this.mActiveServices.remove(componentName);
                if (remotePrintService != null) {
                    removeServiceLocked(remotePrintService);
                }
            } else if (!this.mActiveServices.containsKey(componentName)) {
                RemotePrintService remotePrintService2 = new RemotePrintService(this.mContext, componentName, this.mUserId, this.mSpooler, this);
                this.mActiveServices.put(remotePrintService2.mComponentName, remotePrintService2);
                AnonymousClass1 anonymousClass1 = this.mPrinterDiscoverySession;
                if (anonymousClass1 != null) {
                    if (anonymousClass1.mIsDestroyed) {
                        Log.w("UserState", "Not updating added service - session destroyed");
                    } else {
                        Handler.getMain().sendMessage(PooledLambda.obtainMessage(new UserState$$ExternalSyntheticLambda0(2), remotePrintService2));
                        if (!((ArrayList) anonymousClass1.mStartedPrinterDiscoveryTokens).isEmpty()) {
                            Handler.getMain().sendMessage(PooledLambda.obtainMessage(new UserState$$ExternalSyntheticLambda1(5), remotePrintService2, (Object) null));
                        }
                        int size2 = ((ArrayList) anonymousClass1.mStateTrackedPrinters).size();
                        for (int i2 = 0; i2 < size2; i2++) {
                            PrinterId printerId = (PrinterId) ((ArrayList) anonymousClass1.mStateTrackedPrinters).get(i2);
                            if (printerId.getServiceName().equals(remotePrintService2.mComponentName)) {
                                Handler.getMain().sendMessage(PooledLambda.obtainMessage(new UserState$$ExternalSyntheticLambda1(6), remotePrintService2, printerId));
                            }
                        }
                    }
                }
            }
        }
        Iterator it = this.mActiveServices.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            ComponentName componentName2 = (ComponentName) entry.getKey();
            RemotePrintService remotePrintService3 = (RemotePrintService) entry.getValue();
            if (!installedComponents.contains(componentName2)) {
                removeServiceLocked(remotePrintService3);
                it.remove();
            }
        }
        Handler.getMain().sendMessage(PooledLambda.obtainMessage(new UserState$$ExternalSyntheticLambda0(0), this));
    }

    public final void onPrintJobQueued(PrintJobInfo printJobInfo) {
        RemotePrintService remotePrintService;
        synchronized (this.mLock) {
            throwIfDestroyedLocked();
            remotePrintService = (RemotePrintService) this.mActiveServices.get(printJobInfo.getPrinterId().getServiceName());
        }
        if (remotePrintService != null) {
            Handler.getMain().sendMessage(PooledLambda.obtainMessage(new RemotePrintService$$ExternalSyntheticLambda1(2), remotePrintService, printJobInfo));
        } else {
            this.mSpooler.setPrintJobState(printJobInfo.getId(), 6, this.mContext.getString(17042517));
        }
    }

    public final Bundle print(String str, IPrintDocumentAdapter iPrintDocumentAdapter, PrintAttributes printAttributes, String str2, final int i) {
        PrintJobInfo printJobInfo = new PrintJobInfo();
        printJobInfo.setId(new PrintJobId());
        printJobInfo.setAppId(i);
        printJobInfo.setLabel(str);
        printJobInfo.setAttributes(printAttributes);
        printJobInfo.setState(1);
        printJobInfo.setCopies(1);
        printJobInfo.setCreationTime(System.currentTimeMillis());
        final PrintJobForAppCache printJobForAppCache = this.mPrintJobForAppCache;
        final IBinder asBinder = iPrintDocumentAdapter.asBinder();
        printJobForAppCache.getClass();
        try {
            asBinder.linkToDeath(new IBinder.DeathRecipient() { // from class: com.android.server.print.UserState.PrintJobForAppCache.1
                @Override // android.os.IBinder.DeathRecipient
                public final void binderDied() {
                    asBinder.unlinkToDeath(this, 0);
                    synchronized (UserState.this.mLock) {
                        PrintJobForAppCache.this.mPrintJobsForRunningApp.remove(i);
                    }
                }
            }, 0);
            synchronized (UserState.this.mLock) {
                try {
                    List list = (List) printJobForAppCache.mPrintJobsForRunningApp.get(i);
                    if (list == null) {
                        list = new ArrayList();
                        printJobForAppCache.mPrintJobsForRunningApp.put(i, list);
                    }
                    list.add(printJobInfo);
                } catch (Throwable th) {
                    throw th;
                }
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                Intent intent = new Intent("android.print.PRINT_DIALOG");
                intent.setData(Uri.fromParts("printjob", printJobInfo.getId().flattenToString(), null));
                intent.putExtra("android.print.intent.extra.EXTRA_PRINT_DOCUMENT_ADAPTER", iPrintDocumentAdapter.asBinder());
                intent.putExtra("android.print.intent.extra.EXTRA_PRINT_JOB", printJobInfo);
                intent.putExtra("android.intent.extra.PACKAGE_NAME", str2);
                IntentSender intentSender = PendingIntent.getActivityAsUser(this.mContext, 0, intent, 1409286144, ActivityOptions.makeBasic().setPendingIntentCreatorBackgroundActivityStartMode(2).toBundle(), new UserHandle(this.mUserId)).getIntentSender();
                Bundle bundle = new Bundle();
                bundle.putParcelable("android.print.intent.extra.EXTRA_PRINT_JOB", printJobInfo);
                bundle.putParcelable("android.print.intent.extra.EXTRA_PRINT_DIALOG_INTENT", intentSender);
                return bundle;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        } catch (RemoteException unused) {
            return null;
        }
    }

    public final void prunePrintServices() {
        ArrayList installedComponents;
        synchronized (this.mLock) {
            try {
                installedComponents = getInstalledComponents();
                if (this.mDisabledServices.retainAll(installedComponents)) {
                    writeDisabledPrintServicesLocked(this.mDisabledServices);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        this.mSpooler.pruneApprovedPrintServices(installedComponents);
    }

    public final void readDisabledPrintServicesLocked() {
        HashSet hashSet = new HashSet();
        readPrintServicesFromSettingLocked("disabled_print_services", hashSet);
        if (hashSet.equals(this.mDisabledServices)) {
            return;
        }
        this.mDisabledServices.clear();
        this.mDisabledServices.addAll(hashSet);
    }

    public final void readInstalledPrintServicesLocked() {
        HashSet hashSet = new HashSet();
        List queryIntentServicesAsUser = this.mContext.getPackageManager().queryIntentServicesAsUser(this.mQueryIntent, this.mIsInstantServiceAllowed ? 276824196 : 268435588, this.mUserId);
        int size = queryIntentServicesAsUser.size();
        for (int i = 0; i < size; i++) {
            ResolveInfo resolveInfo = (ResolveInfo) queryIntentServicesAsUser.get(i);
            if ("android.permission.BIND_PRINT_SERVICE".equals(resolveInfo.serviceInfo.permission)) {
                hashSet.add(PrintServiceInfo.create(this.mContext, resolveInfo));
            } else {
                ServiceInfo serviceInfo = resolveInfo.serviceInfo;
                Slog.w("UserState", "Skipping print service " + new ComponentName(serviceInfo.packageName, serviceInfo.name).flattenToShortString() + " since it does not require permission android.permission.BIND_PRINT_SERVICE");
            }
        }
        this.mInstalledServices.clear();
        this.mInstalledServices.addAll(hashSet);
    }

    public final void readPrintServicesFromSettingLocked(String str, Set set) {
        ComponentName unflattenFromString;
        String stringForUser = Settings.Secure.getStringForUser(this.mContext.getContentResolver(), str, this.mUserId);
        if (TextUtils.isEmpty(stringForUser)) {
            return;
        }
        TextUtils.SimpleStringSplitter simpleStringSplitter = this.mStringColonSplitter;
        simpleStringSplitter.setString(stringForUser);
        while (simpleStringSplitter.hasNext()) {
            String next = simpleStringSplitter.next();
            if (!TextUtils.isEmpty(next) && (unflattenFromString = ComponentName.unflattenFromString(next)) != null) {
                ((HashSet) set).add(unflattenFromString);
            }
        }
    }

    public final void removeObsoletePrintJobs() {
        RemotePrintSpooler remotePrintSpooler = this.mSpooler;
        remotePrintSpooler.throwIfCalledOnMainThread();
        synchronized (remotePrintSpooler.mLock) {
            remotePrintSpooler.throwIfDestroyedLocked();
            remotePrintSpooler.mCanUnbind = false;
        }
        try {
            try {
                remotePrintSpooler.getRemoteInstanceLazy().removeObsoletePrintJobs();
                synchronized (remotePrintSpooler.mLock) {
                    remotePrintSpooler.mCanUnbind = true;
                    remotePrintSpooler.mLock.notifyAll();
                }
            } catch (RemoteException | InterruptedException | TimeoutException e) {
                Slog.e("RemotePrintSpooler", "Error removing obsolete print jobs .", e);
                synchronized (remotePrintSpooler.mLock) {
                    remotePrintSpooler.mCanUnbind = true;
                    remotePrintSpooler.mLock.notifyAll();
                }
            }
        } catch (Throwable th) {
            synchronized (remotePrintSpooler.mLock) {
                remotePrintSpooler.mCanUnbind = true;
                remotePrintSpooler.mLock.notifyAll();
                throw th;
            }
        }
    }

    public final void removePrintJobStateChangeListener(IPrintJobStateChangeListener iPrintJobStateChangeListener) {
        synchronized (this.mLock) {
            try {
                throwIfDestroyedLocked();
                List list = this.mPrintJobStateChangeListenerRecords;
                if (list == null) {
                    return;
                }
                int size = list.size();
                int i = 0;
                while (true) {
                    if (i >= size) {
                        break;
                    }
                    AnonymousClass2 anonymousClass2 = (AnonymousClass2) this.mPrintJobStateChangeListenerRecords.get(i);
                    if (anonymousClass2.listener.asBinder().equals(iPrintJobStateChangeListener.asBinder())) {
                        anonymousClass2.listener.asBinder().unlinkToDeath(anonymousClass2, 0);
                        this.mPrintJobStateChangeListenerRecords.remove(i);
                        break;
                    }
                    i++;
                }
                if (this.mPrintJobStateChangeListenerRecords.isEmpty()) {
                    this.mPrintJobStateChangeListenerRecords = null;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void removePrintServiceRecommendationsChangeListener(IRecommendationsChangeListener iRecommendationsChangeListener) {
        synchronized (this.mLock) {
            try {
                throwIfDestroyedLocked();
                List list = this.mPrintServiceRecommendationsChangeListenerRecords;
                if (list == null) {
                    return;
                }
                int size = ((ArrayList) list).size();
                int i = 0;
                while (true) {
                    if (i >= size) {
                        break;
                    }
                    AnonymousClass3 anonymousClass3 = (AnonymousClass3) ((ArrayList) this.mPrintServiceRecommendationsChangeListenerRecords).get(i);
                    if (anonymousClass3.listener.asBinder().equals(iRecommendationsChangeListener.asBinder())) {
                        anonymousClass3.listener.asBinder().unlinkToDeath(anonymousClass3, 0);
                        ((ArrayList) this.mPrintServiceRecommendationsChangeListenerRecords).remove(i);
                        break;
                    }
                    i++;
                }
                if (((ArrayList) this.mPrintServiceRecommendationsChangeListenerRecords).isEmpty()) {
                    this.mPrintServiceRecommendationsChangeListenerRecords = null;
                    this.mPrintServiceRecommendations = null;
                    this.mPrintServiceRecommendationsService.close();
                    this.mPrintServiceRecommendationsService = null;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void removePrintServicesChangeListener(IPrintServicesChangeListener iPrintServicesChangeListener) {
        synchronized (this.mLock) {
            try {
                throwIfDestroyedLocked();
                List list = this.mPrintServicesChangeListenerRecords;
                if (list == null) {
                    return;
                }
                int size = ((ArrayList) list).size();
                int i = 0;
                while (true) {
                    if (i >= size) {
                        break;
                    }
                    AnonymousClass3 anonymousClass3 = (AnonymousClass3) ((ArrayList) this.mPrintServicesChangeListenerRecords).get(i);
                    if (anonymousClass3.listener.asBinder().equals(iPrintServicesChangeListener.asBinder())) {
                        anonymousClass3.listener.asBinder().unlinkToDeath(anonymousClass3, 0);
                        ((ArrayList) this.mPrintServicesChangeListenerRecords).remove(i);
                        break;
                    }
                    i++;
                }
                if (((ArrayList) this.mPrintServicesChangeListenerRecords).isEmpty()) {
                    this.mPrintServicesChangeListenerRecords = null;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void removeServiceLocked(RemotePrintService remotePrintService) {
        ComponentName componentName = remotePrintService.mComponentName;
        if (Looper.getMainLooper().isCurrentThread()) {
            BackgroundThread.getHandler().sendMessage(PooledLambda.obtainMessage(new UserState$$ExternalSyntheticLambda1(0), this, componentName));
        } else {
            failScheduledPrintJobsForServiceInternal(componentName);
        }
        AnonymousClass1 anonymousClass1 = this.mPrinterDiscoverySession;
        if (anonymousClass1 == null) {
            Handler.getMain().sendMessage(PooledLambda.obtainMessage(new RemotePrintService$$ExternalSyntheticLambda0(3), remotePrintService));
            return;
        }
        if (anonymousClass1.mIsDestroyed) {
            Log.w("UserState", "Not updating removed service - session destroyed");
            return;
        }
        ComponentName componentName2 = remotePrintService.mComponentName;
        if (!anonymousClass1.mPrinters.isEmpty()) {
            int size = anonymousClass1.mPrinters.size();
            ArrayList arrayList = null;
            for (int i = 0; i < size; i++) {
                PrinterId printerId = (PrinterId) anonymousClass1.mPrinters.keyAt(i);
                if (printerId.getServiceName().equals(componentName2)) {
                    if (arrayList == null) {
                        arrayList = new ArrayList();
                    }
                    arrayList.add(printerId);
                }
            }
            if (arrayList != null) {
                int size2 = arrayList.size();
                for (int i2 = 0; i2 < size2; i2++) {
                    anonymousClass1.mPrinters.remove(arrayList.get(i2));
                }
                Handler.getMain().sendMessage(PooledLambda.obtainMessage(new UserState$$ExternalSyntheticLambda1(9), anonymousClass1, arrayList));
            }
        }
        Handler.getMain().sendMessage(PooledLambda.obtainMessage(new RemotePrintService$$ExternalSyntheticLambda0(3), remotePrintService));
    }

    public final void setPrintServiceEnabled(ComponentName componentName, boolean z) {
        synchronized (this.mLock) {
            boolean z2 = false;
            try {
                if (z) {
                    z2 = ((ArraySet) this.mDisabledServices).remove(componentName);
                } else {
                    int size = ((ArrayList) this.mInstalledServices).size();
                    int i = 0;
                    while (true) {
                        if (i >= size) {
                            break;
                        }
                        if (((PrintServiceInfo) ((ArrayList) this.mInstalledServices).get(i)).getComponentName().equals(componentName)) {
                            ((ArraySet) this.mDisabledServices).add(componentName);
                            z2 = true;
                            break;
                        }
                        i++;
                    }
                }
                if (z2) {
                    writeDisabledPrintServicesLocked(this.mDisabledServices);
                    MetricsLogger.action(this.mContext, 511, !z ? 1 : 0);
                    onConfigurationChangedLocked();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void stopPrinterStateTracking(PrinterId printerId) {
        synchronized (this.mLock) {
            try {
                throwIfDestroyedLocked();
                if (this.mActiveServices.isEmpty()) {
                    return;
                }
                AnonymousClass1 anonymousClass1 = this.mPrinterDiscoverySession;
                if (anonymousClass1 == null) {
                    return;
                }
                anonymousClass1.stopPrinterStateTrackingLocked(printerId);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void throwIfDestroyedLocked() {
        if (this.mDestroyed) {
            throw new IllegalStateException("Cannot interact with a destroyed instance.");
        }
    }

    public final void updateIfNeededLocked() {
        throwIfDestroyedLocked();
        readInstalledPrintServicesLocked();
        readDisabledPrintServicesLocked();
        onConfigurationChangedLocked();
    }

    public final void upgradePersistentStateIfNeeded() {
        ContentResolver contentResolver = this.mContext.getContentResolver();
        int i = this.mUserId;
        if (Settings.Secure.getStringForUser(contentResolver, "enabled_print_services", i) != null) {
            HashSet hashSet = new HashSet();
            readPrintServicesFromSettingLocked("enabled_print_services", hashSet);
            ArraySet arraySet = new ArraySet();
            int size = this.mInstalledServices.size();
            for (int i2 = 0; i2 < size; i2++) {
                ComponentName componentName = ((PrintServiceInfo) this.mInstalledServices.get(i2)).getComponentName();
                if (!hashSet.contains(componentName)) {
                    arraySet.add(componentName);
                }
            }
            writeDisabledPrintServicesLocked(arraySet);
            Settings.Secure.putStringForUser(this.mContext.getContentResolver(), "enabled_print_services", null, i);
        }
    }

    public final void validatePrinters(List list) {
        synchronized (this.mLock) {
            try {
                throwIfDestroyedLocked();
                if (this.mActiveServices.isEmpty()) {
                    return;
                }
                AnonymousClass1 anonymousClass1 = this.mPrinterDiscoverySession;
                if (anonymousClass1 == null) {
                    return;
                }
                anonymousClass1.validatePrintersLocked(list);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void writeDisabledPrintServicesLocked(Set set) {
        StringBuilder sb = new StringBuilder();
        Iterator it = set.iterator();
        while (it.hasNext()) {
            ComponentName componentName = (ComponentName) it.next();
            if (sb.length() > 0) {
                sb.append(':');
            }
            sb.append(componentName.flattenToShortString());
        }
        Settings.Secure.putStringForUser(this.mContext.getContentResolver(), "disabled_print_services", sb.toString(), this.mUserId);
    }
}
