package com.android.server.print;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.drawable.Icon;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.UserHandle;
import android.print.IPrintSpooler;
import android.print.IPrintSpoolerCallbacks;
import android.print.IPrintSpoolerClient;
import android.print.PrintJobId;
import android.print.PrintJobInfo;
import android.print.PrinterId;
import android.util.Slog;
import android.util.TimedRemoteCaller;

import com.android.internal.util.function.pooled.PooledLambda;

import libcore.io.IoUtils;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.concurrent.TimeoutException;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class RemotePrintSpooler {
    public static final long BIND_SPOOLER_SERVICE_TIMEOUT;
    public final UserState mCallbacks;
    public boolean mCanUnbind;
    public final Context mContext;
    public boolean mDestroyed;
    public final Intent mIntent;
    public boolean mIsBinding;
    public boolean mIsLowPriority;
    public IPrintSpooler mRemoteInstance;
    public final UserHandle mUserHandle;
    public final Object mLock = new Object();
    public final SetPrintJobTagCaller mGetPrintJobInfosCaller = new SetPrintJobTagCaller(4);
    public final SetPrintJobTagCaller mGetPrintJobInfoCaller = new SetPrintJobTagCaller(3);
    public final SetPrintJobTagCaller mSetPrintJobStatusCaller = new SetPrintJobTagCaller(6);
    public final SetPrintJobTagCaller mSetPrintJobTagCaller = new SetPrintJobTagCaller(0);
    public final SetPrintJobTagCaller mCustomPrinterIconLoadedCaller = new SetPrintJobTagCaller(5);
    public final SetPrintJobTagCaller mClearCustomPrinterIconCache = new SetPrintJobTagCaller(1);
    public final SetPrintJobTagCaller mGetCustomPrinterIconCaller = new SetPrintJobTagCaller(2);
    public final MyServiceConnection mServiceConnection = new MyServiceConnection();
    public final PrintSpoolerClient mClient = new PrintSpoolerClient(this);

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class MyServiceConnection implements ServiceConnection {
        public MyServiceConnection() {}

        @Override // android.content.ServiceConnection
        public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            synchronized (RemotePrintSpooler.this.mLock) {
                RemotePrintSpooler.this.mRemoteInstance = IPrintSpooler.Stub.asInterface(iBinder);
                RemotePrintSpooler remotePrintSpooler = RemotePrintSpooler.this;
                remotePrintSpooler.getClass();
                try {
                    remotePrintSpooler.mRemoteInstance.setClient(remotePrintSpooler.mClient);
                } catch (RemoteException e) {
                    Slog.d("RemotePrintSpooler", "Error setting print spooler client", e);
                }
                RemotePrintSpooler.this.mLock.notifyAll();
            }
        }

        @Override // android.content.ServiceConnection
        public final void onServiceDisconnected(ComponentName componentName) {
            synchronized (RemotePrintSpooler.this.mLock) {
                try {
                    IPrintSpooler iPrintSpooler = RemotePrintSpooler.this.mRemoteInstance;
                    if (iPrintSpooler != null) {
                        try {
                            iPrintSpooler.setClient((IPrintSpoolerClient) null);
                        } catch (RemoteException e) {
                            Slog.d("RemotePrintSpooler", "Error clearing print spooler client", e);
                        }
                        RemotePrintSpooler.this.mRemoteInstance = null;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PrintSpoolerClient extends IPrintSpoolerClient.Stub {
        public final WeakReference mWeakSpooler;

        public PrintSpoolerClient(RemotePrintSpooler remotePrintSpooler) {
            this.mWeakSpooler = new WeakReference(remotePrintSpooler);
        }

        public final void onAllPrintJobsForServiceHandled(ComponentName componentName) {
            RemotePrintService remotePrintService;
            RemotePrintSpooler remotePrintSpooler = (RemotePrintSpooler) this.mWeakSpooler.get();
            if (remotePrintSpooler != null) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    UserState userState = remotePrintSpooler.mCallbacks;
                    synchronized (userState.mLock) {
                        userState.throwIfDestroyedLocked();
                        remotePrintService =
                                (RemotePrintService) userState.mActiveServices.get(componentName);
                    }
                    if (remotePrintService != null) {
                        Handler.getMain()
                                .sendMessage(
                                        PooledLambda.obtainMessage(
                                                new RemotePrintService$$ExternalSyntheticLambda0(2),
                                                remotePrintService));
                    }
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }

        public final void onAllPrintJobsHandled() {
            RemotePrintSpooler remotePrintSpooler = (RemotePrintSpooler) this.mWeakSpooler.get();
            if (remotePrintSpooler != null) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    synchronized (remotePrintSpooler.mLock) {
                        remotePrintSpooler.throwIfDestroyedLocked();
                        remotePrintSpooler.unbindLocked();
                    }
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }

        public final void onPrintJobQueued(PrintJobInfo printJobInfo) {
            RemotePrintSpooler remotePrintSpooler = (RemotePrintSpooler) this.mWeakSpooler.get();
            if (remotePrintSpooler != null) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    remotePrintSpooler.mCallbacks.onPrintJobQueued(printJobInfo);
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }

        public final void onPrintJobStateChanged(PrintJobInfo printJobInfo) {
            RemotePrintSpooler remotePrintSpooler = (RemotePrintSpooler) this.mWeakSpooler.get();
            if (remotePrintSpooler != null) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    RemotePrintSpooler.m853$$Nest$monPrintJobStateChanged(
                            remotePrintSpooler, printJobInfo);
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SetPrintJobTagCaller extends TimedRemoteCaller {
        public final AnonymousClass1 mCallback;

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        /* renamed from: com.android.server.print.RemotePrintSpooler$SetPrintJobTagCaller$1, reason: invalid class name */
        public final class AnonymousClass1 extends IPrintSpoolerCallbacks.Stub {
            public final /* synthetic */ int $r8$classId = 0;
            public final /* synthetic */ Object this$0;

            public AnonymousClass1(SetPrintJobTagCaller setPrintJobTagCaller) {
                this.this$0 = setPrintJobTagCaller;
            }

            public AnonymousClass1(SetPrintJobTagCaller setPrintJobTagCaller, byte b) {
                this.this$0 = setPrintJobTagCaller;
            }

            public AnonymousClass1(SetPrintJobTagCaller setPrintJobTagCaller, byte b, byte b2) {
                this.this$0 = setPrintJobTagCaller;
            }

            public AnonymousClass1(SetPrintJobTagCaller setPrintJobTagCaller, byte b, boolean z) {
                this.this$0 = setPrintJobTagCaller;
            }

            public AnonymousClass1(SetPrintJobTagCaller setPrintJobTagCaller, char c) {
                this.this$0 = setPrintJobTagCaller;
            }

            public AnonymousClass1(SetPrintJobTagCaller setPrintJobTagCaller, int i) {
                this.this$0 = setPrintJobTagCaller;
            }

            public AnonymousClass1(SetPrintJobTagCaller setPrintJobTagCaller, short s) {
                this.this$0 = setPrintJobTagCaller;
            }

            public void customPrinterIconCacheCleared(int i) {
                switch (this.$r8$classId) {
                    case 1:
                        ((SetPrintJobTagCaller) this.this$0).onRemoteMethodResult(null, i);
                        break;
                }
            }

            public final void
                    customPrinterIconCacheCleared$com$android$server$print$RemotePrintSpooler$BasePrintSpoolerServiceCallbacks(
                            int i) {}

            public final void onCancelPrintJobResult(boolean z, int i) {}

            public void onCustomPrinterIconCached(int i) {
                switch (this.$r8$classId) {
                    case 5:
                        ((SetPrintJobTagCaller) this.this$0).onRemoteMethodResult(null, i);
                        break;
                }
            }

            public final void
                    onCustomPrinterIconCached$com$android$server$print$RemotePrintSpooler$BasePrintSpoolerServiceCallbacks(
                            int i) {}

            public void onGetCustomPrinterIconResult(Icon icon, int i) {
                switch (this.$r8$classId) {
                    case 2:
                        ((SetPrintJobTagCaller) this.this$0).onRemoteMethodResult(icon, i);
                        break;
                }
            }

            public final void
                    onGetCustomPrinterIconResult$com$android$server$print$RemotePrintSpooler$BasePrintSpoolerServiceCallbacks(
                            Icon icon, int i) {}

            public void onGetPrintJobInfoResult(PrintJobInfo printJobInfo, int i) {
                switch (this.$r8$classId) {
                    case 3:
                        ((SetPrintJobTagCaller) this.this$0).onRemoteMethodResult(printJobInfo, i);
                        break;
                }
            }

            public final void
                    onGetPrintJobInfoResult$com$android$server$print$RemotePrintSpooler$BasePrintSpoolerServiceCallbacks(
                            PrintJobInfo printJobInfo, int i) {}

            public void onGetPrintJobInfosResult(List list, int i) {
                switch (this.$r8$classId) {
                    case 4:
                        ((SetPrintJobTagCaller) this.this$0).onRemoteMethodResult(list, i);
                        break;
                }
            }

            public final void
                    onGetPrintJobInfosResult$com$android$server$print$RemotePrintSpooler$BasePrintSpoolerServiceCallbacks(
                            List list, int i) {}

            public void onSetPrintJobStateResult(boolean z, int i) {
                switch (this.$r8$classId) {
                    case 6:
                        ((SetPrintJobTagCaller) this.this$0)
                                .onRemoteMethodResult(Boolean.valueOf(z), i);
                        break;
                }
            }

            public final void
                    onSetPrintJobStateResult$com$android$server$print$RemotePrintSpooler$BasePrintSpoolerServiceCallbacks(
                            boolean z, int i) {}

            public void onSetPrintJobTagResult(boolean z, int i) {
                switch (this.$r8$classId) {
                    case 0:
                        ((SetPrintJobTagCaller) this.this$0)
                                .onRemoteMethodResult(Boolean.valueOf(z), i);
                        break;
                }
            }

            public final void
                    onSetPrintJobTagResult$com$android$server$print$RemotePrintSpooler$BasePrintSpoolerServiceCallbacks(
                            boolean z, int i) {}
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public SetPrintJobTagCaller(int i) {
            super(5000L);
            switch (i) {
                case 1:
                    super(5000L);
                    this.mCallback = new AnonymousClass1(this, (byte) 0);
                    break;
                case 2:
                    super(5000L);
                    this.mCallback = new AnonymousClass1(this, (char) 0);
                    break;
                case 3:
                    super(5000L);
                    this.mCallback = new AnonymousClass1(this, 0);
                    break;
                case 4:
                    super(5000L);
                    this.mCallback = new AnonymousClass1(this, (short) 0);
                    break;
                case 5:
                    super(5000L);
                    this.mCallback = new AnonymousClass1(this, (byte) 0, false);
                    break;
                case 6:
                    super(5000L);
                    this.mCallback = new AnonymousClass1(this, (byte) 0, (byte) 0);
                    break;
                default:
                    this.mCallback = new AnonymousClass1(this);
                    break;
            }
        }

        public void clearCustomPrinterIconCache(IPrintSpooler iPrintSpooler) {
            int onBeforeRemoteCall = onBeforeRemoteCall();
            iPrintSpooler.clearCustomPrinterIconCache(this.mCallback, onBeforeRemoteCall);
        }

        public Icon getCustomPrinterIcon(IPrintSpooler iPrintSpooler, PrinterId printerId) {
            int onBeforeRemoteCall = onBeforeRemoteCall();
            iPrintSpooler.getCustomPrinterIcon(printerId, this.mCallback, onBeforeRemoteCall);
            return (Icon) getResultTimed(onBeforeRemoteCall);
        }

        public PrintJobInfo getPrintJobInfo(
                IPrintSpooler iPrintSpooler, PrintJobId printJobId, int i) {
            int onBeforeRemoteCall = onBeforeRemoteCall();
            iPrintSpooler.getPrintJobInfo(printJobId, this.mCallback, i, onBeforeRemoteCall);
            return (PrintJobInfo) getResultTimed(onBeforeRemoteCall);
        }

        public List getPrintJobInfos(
                IPrintSpooler iPrintSpooler, ComponentName componentName, int i, int i2) {
            int onBeforeRemoteCall = onBeforeRemoteCall();
            iPrintSpooler.getPrintJobInfos(
                    this.mCallback, componentName, i, i2, onBeforeRemoteCall);
            return (List) getResultTimed(onBeforeRemoteCall);
        }

        public void onCustomPrinterIconLoaded(
                IPrintSpooler iPrintSpooler, PrinterId printerId, Icon icon) {
            int onBeforeRemoteCall = onBeforeRemoteCall();
            iPrintSpooler.onCustomPrinterIconLoaded(
                    printerId, icon, this.mCallback, onBeforeRemoteCall);
        }

        public boolean setPrintJobState(
                IPrintSpooler iPrintSpooler, PrintJobId printJobId, int i, String str) {
            int onBeforeRemoteCall = onBeforeRemoteCall();
            iPrintSpooler.setPrintJobState(printJobId, i, str, this.mCallback, onBeforeRemoteCall);
            return ((Boolean) getResultTimed(onBeforeRemoteCall)).booleanValue();
        }

        public boolean setPrintJobTag(
                IPrintSpooler iPrintSpooler, PrintJobId printJobId, String str) {
            int onBeforeRemoteCall = onBeforeRemoteCall();
            iPrintSpooler.setPrintJobTag(printJobId, str, this.mCallback, onBeforeRemoteCall);
            return ((Boolean) getResultTimed(onBeforeRemoteCall)).booleanValue();
        }
    }

    /* renamed from: -$$Nest$monPrintJobStateChanged, reason: not valid java name */
    public static void m853$$Nest$monPrintJobStateChanged(
            RemotePrintSpooler remotePrintSpooler, PrintJobInfo printJobInfo) {
        UserState userState = remotePrintSpooler.mCallbacks;
        UserState.PrintJobForAppCache printJobForAppCache = userState.mPrintJobForAppCache;
        synchronized (UserState.this.mLock) {
            try {
                List list =
                        (List)
                                printJobForAppCache.mPrintJobsForRunningApp.get(
                                        printJobInfo.getAppId());
                if (list != null) {
                    int size = list.size();
                    for (int i = 0; i < size; i++) {
                        if (((PrintJobInfo) list.get(i)).getId().equals(printJobInfo.getId())) {
                            list.set(i, printJobInfo);
                        }
                    }
                }
            } finally {
            }
        }
        Handler.getMain()
                .sendMessage(
                        PooledLambda.obtainMessage(
                                new UserState$$ExternalSyntheticLambda4(0),
                                userState,
                                printJobInfo.getId(),
                                PooledLambda.obtainSupplier(printJobInfo.getAppId())
                                        .recycleOnUse()));
    }

    static {
        BIND_SPOOLER_SERVICE_TIMEOUT = Build.IS_ENG ? 120000L : 10000L;
    }

    public RemotePrintSpooler(Context context, int i, boolean z, UserState userState) {
        this.mContext = context;
        this.mUserHandle = new UserHandle(i);
        this.mCallbacks = userState;
        this.mIsLowPriority = z;
        Intent intent = new Intent();
        this.mIntent = intent;
        intent.setComponent(
                new ComponentName(
                        "com.android.printspooler",
                        "com.android.printspooler.model.PrintSpoolerService"));
    }

    public final void bindLocked() {
        Object obj;
        while (true) {
            boolean z = this.mIsBinding;
            obj = this.mLock;
            if (!z) {
                break;
            } else {
                obj.wait();
            }
        }
        if (this.mRemoteInstance != null) {
            return;
        }
        this.mIsBinding = true;
        try {
            this.mContext.bindServiceAsUser(
                    this.mIntent,
                    this.mServiceConnection,
                    this.mIsLowPriority ? 1 : 67108865,
                    this.mUserHandle);
            long uptimeMillis = SystemClock.uptimeMillis();
            while (this.mRemoteInstance == null) {
                long uptimeMillis2 =
                        BIND_SPOOLER_SERVICE_TIMEOUT - (SystemClock.uptimeMillis() - uptimeMillis);
                if (uptimeMillis2 <= 0) {
                    throw new TimeoutException("Cannot get spooler!");
                }
                obj.wait(uptimeMillis2);
            }
            this.mCanUnbind = true;
        } finally {
            this.mIsBinding = false;
            obj.notifyAll();
        }
    }

    public final void clearCustomPrinterIconCache() {
        throwIfCalledOnMainThread();
        synchronized (this.mLock) {
            throwIfDestroyedLocked();
            this.mCanUnbind = false;
        }
        try {
            try {
                this.mClearCustomPrinterIconCache.clearCustomPrinterIconCache(
                        getRemoteInstanceLazy());
                synchronized (this.mLock) {
                    this.mCanUnbind = true;
                    this.mLock.notifyAll();
                }
            } catch (RemoteException | InterruptedException | TimeoutException e) {
                Slog.e("RemotePrintSpooler", "Error clearing custom printer icon cache.", e);
                synchronized (this.mLock) {
                    this.mCanUnbind = true;
                    this.mLock.notifyAll();
                }
            }
        } catch (Throwable th) {
            synchronized (this.mLock) {
                this.mCanUnbind = true;
                this.mLock.notifyAll();
                throw th;
            }
        }
    }

    public final PrintJobInfo getPrintJobInfo(PrintJobId printJobId, int i) {
        throwIfCalledOnMainThread();
        synchronized (this.mLock) {
            throwIfDestroyedLocked();
            this.mCanUnbind = false;
        }
        try {
            try {
                PrintJobInfo printJobInfo =
                        this.mGetPrintJobInfoCaller.getPrintJobInfo(
                                getRemoteInstanceLazy(), printJobId, i);
                synchronized (this.mLock) {
                    this.mCanUnbind = true;
                    this.mLock.notifyAll();
                }
                return printJobInfo;
            } catch (RemoteException | InterruptedException | TimeoutException e) {
                Slog.e("RemotePrintSpooler", "Error getting print job info.", e);
                synchronized (this.mLock) {
                    this.mCanUnbind = true;
                    this.mLock.notifyAll();
                    return null;
                }
            }
        } catch (Throwable th) {
            synchronized (this.mLock) {
                this.mCanUnbind = true;
                this.mLock.notifyAll();
                throw th;
            }
        }
    }

    public final List getPrintJobInfos(int i, int i2, ComponentName componentName) {
        throwIfCalledOnMainThread();
        synchronized (this.mLock) {
            throwIfDestroyedLocked();
            this.mCanUnbind = false;
        }
        try {
            try {
                List printJobInfos =
                        this.mGetPrintJobInfosCaller.getPrintJobInfos(
                                getRemoteInstanceLazy(), componentName, i, i2);
                synchronized (this.mLock) {
                    this.mCanUnbind = true;
                    this.mLock.notifyAll();
                }
                return printJobInfos;
            } catch (RemoteException | InterruptedException | TimeoutException e) {
                Slog.e("RemotePrintSpooler", "Error getting print jobs.", e);
                synchronized (this.mLock) {
                    this.mCanUnbind = true;
                    this.mLock.notifyAll();
                    return null;
                }
            }
        } catch (Throwable th) {
            synchronized (this.mLock) {
                this.mCanUnbind = true;
                this.mLock.notifyAll();
                throw th;
            }
        }
    }

    public final IPrintSpooler getRemoteInstanceLazy() {
        synchronized (this.mLock) {
            try {
                IPrintSpooler iPrintSpooler = this.mRemoteInstance;
                if (iPrintSpooler != null) {
                    return iPrintSpooler;
                }
                bindLocked();
                return this.mRemoteInstance;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void onCustomPrinterIconLoaded(PrinterId printerId, Icon icon) {
        throwIfCalledOnMainThread();
        synchronized (this.mLock) {
            throwIfDestroyedLocked();
            this.mCanUnbind = false;
        }
        try {
            try {
                this.mCustomPrinterIconLoadedCaller.onCustomPrinterIconLoaded(
                        getRemoteInstanceLazy(), printerId, icon);
                synchronized (this.mLock) {
                    this.mCanUnbind = true;
                    this.mLock.notifyAll();
                }
            } catch (RemoteException | InterruptedException | TimeoutException e) {
                Slog.e("RemotePrintSpooler", "Error loading new custom printer icon.", e);
                synchronized (this.mLock) {
                    this.mCanUnbind = true;
                    this.mLock.notifyAll();
                }
            }
        } catch (Throwable th) {
            synchronized (this.mLock) {
                this.mCanUnbind = true;
                this.mLock.notifyAll();
                throw th;
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void pruneApprovedPrintServices(List list) {
        Object obj;
        throwIfCalledOnMainThread();
        synchronized (this.mLock) {
            throwIfDestroyedLocked();
            this.mCanUnbind = false;
        }
        try {
            try {
                getRemoteInstanceLazy().pruneApprovedPrintServices(list);
                synchronized (this.mLock) {
                    this.mCanUnbind = true;
                    obj = this.mLock;
                    obj.notifyAll();
                }
                this = obj;
            } catch (RemoteException | InterruptedException | TimeoutException e) {
                Slog.e("RemotePrintSpooler", "Error pruning approved print services.", e);
                synchronized (this.mLock) {
                    this.mCanUnbind = true;
                    Object obj2 = this.mLock;
                    obj2.notifyAll();
                    this = obj2;
                }
            }
        } catch (Throwable th) {
            synchronized (this.mLock) {
                this.mCanUnbind = true;
                this.mLock.notifyAll();
                throw th;
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void setPrintJobCancelling(PrintJobId printJobId) {
        Object obj;
        throwIfCalledOnMainThread();
        synchronized (this.mLock) {
            throwIfDestroyedLocked();
            this.mCanUnbind = false;
        }
        try {
            try {
                getRemoteInstanceLazy().setPrintJobCancelling(printJobId, true);
                synchronized (this.mLock) {
                    this.mCanUnbind = true;
                    obj = this.mLock;
                    obj.notifyAll();
                }
                this = obj;
            } catch (RemoteException | InterruptedException | TimeoutException e) {
                Slog.e("RemotePrintSpooler", "Error setting print job cancelling.", e);
                synchronized (this.mLock) {
                    this.mCanUnbind = true;
                    Object obj2 = this.mLock;
                    obj2.notifyAll();
                    this = obj2;
                }
            }
        } catch (Throwable th) {
            synchronized (this.mLock) {
                this.mCanUnbind = true;
                this.mLock.notifyAll();
                throw th;
            }
        }
    }

    public final boolean setPrintJobState(PrintJobId printJobId, int i, String str) {
        throwIfCalledOnMainThread();
        synchronized (this.mLock) {
            throwIfDestroyedLocked();
            this.mCanUnbind = false;
        }
        try {
            try {
                boolean printJobState =
                        this.mSetPrintJobStatusCaller.setPrintJobState(
                                getRemoteInstanceLazy(), printJobId, i, str);
                synchronized (this.mLock) {
                    this.mCanUnbind = true;
                    this.mLock.notifyAll();
                }
                return printJobState;
            } catch (RemoteException | InterruptedException | TimeoutException e) {
                Slog.e("RemotePrintSpooler", "Error setting print job state.", e);
                synchronized (this.mLock) {
                    this.mCanUnbind = true;
                    this.mLock.notifyAll();
                    return false;
                }
            }
        } catch (Throwable th) {
            synchronized (this.mLock) {
                this.mCanUnbind = true;
                this.mLock.notifyAll();
                throw th;
            }
        }
    }

    public final boolean setPrintJobTag(PrintJobId printJobId, String str) {
        throwIfCalledOnMainThread();
        synchronized (this.mLock) {
            throwIfDestroyedLocked();
            this.mCanUnbind = false;
        }
        try {
            try {
                boolean printJobTag =
                        this.mSetPrintJobTagCaller.setPrintJobTag(
                                getRemoteInstanceLazy(), printJobId, str);
                synchronized (this.mLock) {
                    this.mCanUnbind = true;
                    this.mLock.notifyAll();
                }
                return printJobTag;
            } catch (RemoteException | InterruptedException | TimeoutException e) {
                Slog.e("RemotePrintSpooler", "Error setting print job tag.", e);
                synchronized (this.mLock) {
                    this.mCanUnbind = true;
                    this.mLock.notifyAll();
                    return false;
                }
            }
        } catch (Throwable th) {
            synchronized (this.mLock) {
                this.mCanUnbind = true;
                this.mLock.notifyAll();
                throw th;
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void setProgress(PrintJobId printJobId, float f) {
        Object obj;
        throwIfCalledOnMainThread();
        synchronized (this.mLock) {
            throwIfDestroyedLocked();
            this.mCanUnbind = false;
        }
        try {
            try {
                getRemoteInstanceLazy().setProgress(printJobId, f);
                synchronized (this.mLock) {
                    this.mCanUnbind = true;
                    obj = this.mLock;
                    obj.notifyAll();
                }
                this = obj;
            } catch (RemoteException | InterruptedException | TimeoutException e) {
                Slog.e("RemotePrintSpooler", "Error setting progress.", e);
                synchronized (this.mLock) {
                    this.mCanUnbind = true;
                    Object obj2 = this.mLock;
                    obj2.notifyAll();
                    this = obj2;
                }
            }
        } catch (Throwable th) {
            synchronized (this.mLock) {
                this.mCanUnbind = true;
                this.mLock.notifyAll();
                throw th;
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void setStatus(PrintJobId printJobId, int i, CharSequence charSequence) {
        Object obj;
        throwIfCalledOnMainThread();
        synchronized (this.mLock) {
            throwIfDestroyedLocked();
            this.mCanUnbind = false;
        }
        try {
            try {
                getRemoteInstanceLazy().setStatusRes(printJobId, i, charSequence);
                synchronized (this.mLock) {
                    this.mCanUnbind = true;
                    obj = this.mLock;
                    obj.notifyAll();
                }
                this = obj;
            } catch (RemoteException | InterruptedException | TimeoutException e) {
                Slog.e("RemotePrintSpooler", "Error setting status.", e);
                synchronized (this.mLock) {
                    this.mCanUnbind = true;
                    Object obj2 = this.mLock;
                    obj2.notifyAll();
                    this = obj2;
                }
            }
        } catch (Throwable th) {
            synchronized (this.mLock) {
                this.mCanUnbind = true;
                this.mLock.notifyAll();
                throw th;
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void setStatus(PrintJobId printJobId, CharSequence charSequence) {
        Object obj;
        throwIfCalledOnMainThread();
        synchronized (this.mLock) {
            throwIfDestroyedLocked();
            this.mCanUnbind = false;
        }
        try {
            try {
                getRemoteInstanceLazy().setStatus(printJobId, charSequence);
                synchronized (this.mLock) {
                    this.mCanUnbind = true;
                    obj = this.mLock;
                    obj.notifyAll();
                }
                this = obj;
            } catch (RemoteException | InterruptedException | TimeoutException e) {
                Slog.e("RemotePrintSpooler", "Error setting status.", e);
                synchronized (this.mLock) {
                    this.mCanUnbind = true;
                    Object obj2 = this.mLock;
                    obj2.notifyAll();
                    this = obj2;
                }
            }
        } catch (Throwable th) {
            synchronized (this.mLock) {
                this.mCanUnbind = true;
                this.mLock.notifyAll();
                throw th;
            }
        }
    }

    public final void throwIfCalledOnMainThread() {
        if (Thread.currentThread() == this.mContext.getMainLooper().getThread()) {
            throw new RuntimeException("Cannot invoke on the main thread");
        }
    }

    public final void throwIfDestroyedLocked() {
        if (this.mDestroyed) {
            throw new IllegalStateException("Cannot interact with a destroyed instance.");
        }
    }

    public final void unbindLocked() {
        if (this.mRemoteInstance == null) {
            return;
        }
        while (!this.mCanUnbind) {
            try {
                this.mLock.wait();
            } catch (InterruptedException unused) {
            }
        }
        try {
            this.mRemoteInstance.setClient((IPrintSpoolerClient) null);
        } catch (RemoteException e) {
            Slog.d("RemotePrintSpooler", "Error clearing print spooler client", e);
        }
        this.mRemoteInstance = null;
        this.mContext.unbindService(this.mServiceConnection);
    }

    public final void writePrintJobData(
            ParcelFileDescriptor parcelFileDescriptor, PrintJobId printJobId) {
        throwIfCalledOnMainThread();
        synchronized (this.mLock) {
            throwIfDestroyedLocked();
            this.mCanUnbind = false;
        }
        try {
            try {
                getRemoteInstanceLazy().writePrintJobData(parcelFileDescriptor, printJobId);
                IoUtils.closeQuietly(parcelFileDescriptor);
                synchronized (this.mLock) {
                    this.mCanUnbind = true;
                    this.mLock.notifyAll();
                }
            } catch (RemoteException | InterruptedException | TimeoutException e) {
                Slog.e("RemotePrintSpooler", "Error writing print job data.", e);
                IoUtils.closeQuietly(parcelFileDescriptor);
                synchronized (this.mLock) {
                    this.mCanUnbind = true;
                    this.mLock.notifyAll();
                }
            }
        } catch (Throwable th) {
            IoUtils.closeQuietly(parcelFileDescriptor);
            synchronized (this.mLock) {
                this.mCanUnbind = true;
                this.mLock.notifyAll();
                throw th;
            }
        }
    }
}
