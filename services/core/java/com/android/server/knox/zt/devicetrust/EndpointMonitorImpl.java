package com.android.server.knox.zt.devicetrust;

import android.app.ActivityManager;
import android.app.ActivityManagerInternal;
import android.app.IActivityManager;
import android.app.IProcessObserver;
import android.content.Context;
import android.net.util.NetdService$$ExternalSyntheticOutline0;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.util.Log;

import com.android.server.DirEncryptService$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.VpnManagerService$$ExternalSyntheticOutline0;
import com.android.server.accounts.AccountsDb$CeDatabaseHelper$$ExternalSyntheticOutline0;
import com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticOutline0;
import com.android.server.knox.zt.devicetrust.data.AppBindingData;
import com.android.server.knox.zt.devicetrust.data.AppDyingData;
import com.android.server.knox.zt.devicetrust.data.EndpointData;
import com.android.server.knox.zt.devicetrust.task.AbnormalPacketsMonitoring;
import com.android.server.knox.zt.devicetrust.task.AppProcessMonitoring;
import com.android.server.knox.zt.devicetrust.task.ExecveMonitoring;
import com.android.server.knox.zt.devicetrust.task.HandleableMonitoringTask;
import com.android.server.knox.zt.devicetrust.task.InsecurePortsMonitoring;
import com.android.server.knox.zt.devicetrust.task.LocalNetworkPktMonitoring;
import com.android.server.knox.zt.devicetrust.task.MonitoringTask;
import com.android.server.knox.zt.devicetrust.task.PacketMonitoring;
import com.android.server.knox.zt.devicetrust.task.PrivilegeEscalationMonitoring;
import com.android.server.knox.zt.devicetrust.task.ProcessMonitoring;
import com.android.server.knox.zt.devicetrust.task.ReschedulableMonitoringTask;
import com.android.server.knox.zt.devicetrust.task.SocketStateMonitoring;
import com.android.server.knox.zt.devicetrust.task.SystemCallMonitoring;
import com.android.server.knox.zt.devicetrust.task.TaskRescheduler;
import com.android.server.knox.zt.networktrust.KnoxNetworkEventService;

import com.samsung.android.knox.zt.devicetrust.EndpointMonitorConst;
import com.samsung.android.knox.zt.devicetrust.IEndpointMonitorListener;
import com.samsung.android.knox.zt.internal.IKnoxZtInternalService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class EndpointMonitorImpl {
    public static final String KZT_FW_PKG_NAME = "com.samsung.android.knox.zt.framework";
    public static final String TAG = "EndpointMonitorImpl";
    public final ActivityManagerInternal mAmInternal;
    public long mBootTimeNanos;
    public final Injector mInjector;
    public final EndpointMonitorInternal mInternal;
    public volatile int mKztFrameworkPid;
    public final IProcessObserver mProcessObserver;
    public final AtomicBoolean mProcessObserverRegistered;
    public final MonitoringSession mSession;
    public final Object mSessionLock;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Injector {
        public final long mBootTimeNanos;
        public Context mContext;
        public Handler mHandler;
        public HandlerThread mHandlerThread;
        public final EndpointMonitorNative mNative;
        public final OemNetdAdapter mOemNetdAdapterImpl;
        public final TaskRescheduler mTaskRescheduler;
        public IKnoxZtInternalService mZtInternalService;

        public Injector() {
            this.mBootTimeNanos =
                    (System.currentTimeMillis() * 1000000) - SystemClock.elapsedRealtimeNanos();
            this.mNative = new EndpointMonitorNative();
            this.mTaskRescheduler = new TaskRescheduler();
            this.mOemNetdAdapterImpl = new OemNetdAdapterImpl();
        }

        public Injector(Context context) {
            this.mBootTimeNanos =
                    (System.currentTimeMillis() * 1000000) - SystemClock.elapsedRealtimeNanos();
            EndpointMonitorNative endpointMonitorNative = new EndpointMonitorNative();
            this.mNative = endpointMonitorNative;
            this.mTaskRescheduler = new TaskRescheduler();
            this.mOemNetdAdapterImpl = new OemNetdAdapterImpl(context, endpointMonitorNative);
            this.mContext = context;
        }

        public final IActivityManager getActivityManager() {
            return ActivityManager.getService();
        }

        public final ActivityManagerInternal getActivityManagerInternal() {
            return (ActivityManagerInternal)
                    LocalServices.getService(ActivityManagerInternal.class);
        }

        public final AppMonitor getAppMonitor() {
            return AppMonitor.get();
        }

        public final long getBootTimeNanos() {
            return this.mBootTimeNanos;
        }

        public final Context getContext() {
            return this.mContext;
        }

        public final synchronized Handler getHandler() {
            try {
                if (this.mHandler == null) {
                    this.mHandler = new Handler(getHandlerThread().getLooper());
                }
            } catch (Throwable th) {
                throw th;
            }
            return this.mHandler;
        }

        public final synchronized HandlerThread getHandlerThread() {
            try {
                if (this.mHandlerThread == null) {
                    HandlerThread handlerThread = new HandlerThread(EndpointMonitorImpl.TAG, 10);
                    this.mHandlerThread = handlerThread;
                    handlerThread.start();
                }
            } catch (Throwable th) {
                throw th;
            }
            return this.mHandlerThread;
        }

        public final KnoxNetworkEventService getKnoxNetworkEventService() {
            KnoxNetworkEventService knoxNetworkEventService;
            Context context = this.mContext;
            synchronized (KnoxNetworkEventService.class) {
                try {
                    if (KnoxNetworkEventService.mInstance == null) {
                        KnoxNetworkEventService.mInstance = new KnoxNetworkEventService(context);
                    }
                    knoxNetworkEventService = KnoxNetworkEventService.mInstance;
                } catch (Throwable th) {
                    throw th;
                }
            }
            return knoxNetworkEventService;
        }

        public final EndpointMonitorNative getNative() {
            return this.mNative;
        }

        public final OemNetdAdapter getOemNetdAdapter() {
            return this.mOemNetdAdapterImpl;
        }

        public final TaskRescheduler getTaskRescheduler() {
            return this.mTaskRescheduler;
        }

        public final IKnoxZtInternalService getZtInternalService() {
            if (this.mZtInternalService == null) {
                this.mZtInternalService =
                        IKnoxZtInternalService.Stub.asInterface(
                                ServiceManager.getService("knoxztinternal"));
            }
            return this.mZtInternalService;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class LocalService extends EndpointMonitorInternal {
        public LocalService() {}

        public final void reportApplicationBinding(long j, int i, int i2, String str, String str2) {
            EndpointMonitorImpl.this.mSession.findAndHandle(
                    7,
                    new AppBindingData(
                                    EndpointMonitorConst.TRACE_EVENT_APP_BINDING,
                                    j * 1000000,
                                    i,
                                    i2,
                                    str,
                                    str2)
                            .adjustTime(EndpointMonitorImpl.this.mBootTimeNanos));
        }

        public final void reportApplicationDying(long j, int i, int i2, String str, long j2) {
            EndpointMonitorImpl.this.mSession.findAndHandle(
                    7,
                    new AppDyingData(
                                    EndpointMonitorConst.TRACE_EVENT_APP_DYING,
                                    j * 1000000,
                                    i,
                                    i2,
                                    str,
                                    j2)
                            .adjustTime(EndpointMonitorImpl.this.mBootTimeNanos));
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    class MonitoringSession {
        public final Object mLock;
        public final Map mTasks = new HashMap();

        public MonitoringSession(Object obj) {
            this.mLock = obj;
        }

        public static void lambda$findByUid$0(
                int i, List list, Integer num, MonitoringTask monitoringTask) {
            if (monitoringTask.mUid == i) {
                list.add(monitoringTask);
            }
        }

        public final void add(MonitoringTask monitoringTask) {
            synchronized (this.mLock) {
                this.mTasks.put(Integer.valueOf(monitoringTask.mType), monitoringTask);
            }
        }

        public final boolean contains() {
            boolean z;
            synchronized (this.mLock) {
                z = this.mTasks.size() > 0;
            }
            return z;
        }

        public final boolean contains(int i) {
            boolean containsKey;
            synchronized (this.mLock) {
                containsKey = this.mTasks.containsKey(Integer.valueOf(i));
            }
            return containsKey;
        }

        public final boolean containsWithUid(int i) {
            synchronized (this.mLock) {
                try {
                    Iterator it = this.mTasks.values().iterator();
                    while (it.hasNext()) {
                        if (((MonitoringTask) it.next()).mUid == i) {
                            return true;
                        }
                    }
                    return false;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final MonitoringTask find(int i) {
            MonitoringTask monitoringTask;
            synchronized (this.mLock) {
                monitoringTask = (MonitoringTask) this.mTasks.get(Integer.valueOf(i));
            }
            return monitoringTask;
        }

        public final void findAndHandle(int i, EndpointData endpointData) {
            MonitoringTask find = find(i);
            if (find instanceof HandleableMonitoringTask) {
                ((HandleableMonitoringTask) find).handle(endpointData);
            }
        }

        public final List findByUid(final int i) {
            final ArrayList arrayList = new ArrayList();
            synchronized (this.mLock) {
                this.mTasks.forEach(
                        new BiConsumer() { // from class:
                                           // com.android.server.knox.zt.devicetrust.EndpointMonitorImpl$MonitoringSession$$ExternalSyntheticLambda0
                            @Override // java.util.function.BiConsumer
                            public final void accept(Object obj, Object obj2) {
                                EndpointMonitorImpl.MonitoringSession.lambda$findByUid$0(
                                        i, arrayList, (Integer) obj, (MonitoringTask) obj2);
                            }
                        });
            }
            return arrayList;
        }

        public final void remove(int i) {
            synchronized (this.mLock) {
                this.mTasks.remove(Integer.valueOf(i));
            }
        }
    }

    public EndpointMonitorImpl() {
        this(new Injector());
    }

    public EndpointMonitorImpl(Context context) {
        this(new Injector(context));
    }

    public EndpointMonitorImpl(Injector injector) {
        this.mInjector = injector;
        this.mBootTimeNanos = injector.mBootTimeNanos;
        Object obj = new Object();
        this.mSessionLock = obj;
        this.mSession = new MonitoringSession(obj);
        LocalService localService = new LocalService();
        this.mInternal = localService;
        LocalServices.addService(EndpointMonitorInternal.class, localService);
        this.mAmInternal = injector.getActivityManagerInternal();
        this.mProcessObserverRegistered = new AtomicBoolean(false);
        this.mProcessObserver =
                new IProcessObserver
                        .Stub() { // from class:
                                  // com.android.server.knox.zt.devicetrust.EndpointMonitorImpl.1
                    public final void onForegroundActivitiesChanged(int i, int i2, boolean z) {}

                    public final void onForegroundServicesChanged(int i, int i2, int i3) {}

                    public final void onProcessDied(int i, int i2) {
                        if (i2 == 1000
                                && EndpointMonitorImpl.this.mSession.containsWithUid(i2)
                                && EndpointMonitorImpl.this.mKztFrameworkPid != 0
                                && EndpointMonitorImpl.this.mKztFrameworkPid == i) {
                            EndpointMonitorImpl.this.stopMonitoring(i2);
                            EndpointMonitorImpl.this.mKztFrameworkPid = 0;
                        }
                    }

                    public final void onProcessStarted(
                            int i, int i2, int i3, String str, String str2) {}
                };
    }

    public final boolean containsNetworkEventFlag(int i) {
        return (32768 & i) > 0 || (65536 & i) > 0 || (131072 & i) > 0;
    }

    public final MonitoringTask createMonitoringTask(
            int i,
            int i2,
            int i3,
            int i4,
            int i5,
            IEndpointMonitorListener iEndpointMonitorListener,
            Predicate predicate) {
        MonitoringTask rescheduleMonitoringTask =
                rescheduleMonitoringTask(i, i2, i3, i4, i5, iEndpointMonitorListener, predicate);
        if (rescheduleMonitoringTask instanceof ReschedulableMonitoringTask) {
            VpnManagerService$$ExternalSyntheticOutline0.m(
                    new StringBuilder("Task rescheduled = "),
                    ((ReschedulableMonitoringTask) rescheduleMonitoringTask).mFingerprint,
                    TAG);
            return rescheduleMonitoringTask;
        }
        switch (i) {
            case 1:
                return new SystemCallMonitoring(
                        1, i2, i3, i4, i5, iEndpointMonitorListener, predicate, this.mInjector);
            case 2:
            case 4:
            case 6:
            case 8:
            case 9:
            case 13:
            default:
                return rescheduleMonitoringTask;
            case 3:
                return new SocketStateMonitoring(
                        3, i2, i3, i4, i5, iEndpointMonitorListener, predicate, this.mInjector);
            case 5:
                return new PacketMonitoring(
                        5, i2, i3, i4, i5, iEndpointMonitorListener, predicate, this.mInjector);
            case 7:
                return new AppProcessMonitoring(
                        i2, i3, i4, i5, iEndpointMonitorListener, predicate, this.mInjector);
            case 10:
                return new ExecveMonitoring(
                        i2, i3, i4, i5, iEndpointMonitorListener, predicate, this.mInjector);
            case 11:
                return new ProcessMonitoring(
                        i2, i3, i4, i5, iEndpointMonitorListener, predicate, this.mInjector);
            case 12:
                return new PrivilegeEscalationMonitoring(
                        i2, i3, i4, i5, iEndpointMonitorListener, predicate, this.mInjector);
            case 14:
                return new InsecurePortsMonitoring(
                        i2, i, i3, i4, i5, iEndpointMonitorListener, this.mInjector);
            case 15:
                return new AbnormalPacketsMonitoring(
                        i2, i, i3, i4, i5, iEndpointMonitorListener, this.mInjector);
            case 16:
                return new LocalNetworkPktMonitoring(
                        i2, i, i3, i4, i5, iEndpointMonitorListener, this.mInjector);
        }
    }

    public final Predicate getFilter(Bundle bundle) {
        if (bundle.getBoolean(EndpointMonitorConst.OPT_TRACE_APPLICATION_ONLY, false)) {
            return new EndpointMonitorImpl$$ExternalSyntheticLambda0();
        }
        return null;
    }

    public final int getFlags(int i, Bundle bundle) {
        if (i == 1) {
            return bundle.getInt("flags", 0);
        }
        if (i == 3) {
            return 32;
        }
        if (i == 5) {
            return 64;
        }
        if (i == 7) {
            return 128;
        }
        if (i == 4) {
            return 2048;
        }
        if (i == 10) {
            return 4096;
        }
        if (i == 11) {
            return 8192;
        }
        if (i == 12) {
            return EndpointMonitorConst.FLAG_TRACING_PROCESS_PERMISSIONS_MODIFICATION;
        }
        if (i == 14) {
            return 32768;
        }
        return i == 15
                ? EndpointMonitorConst.FLAG_TRACING_NETWORK_EVENT_ABNORMAL_PKT
                : i == 16 ? 131072 : 0;
    }

    public final int getNetworkEventTypeByFlag(int i) {
        if ((32768 & i) > 0) {
            return 14;
        }
        if ((65536 & i) > 0) {
            return 15;
        }
        return (131072 & i) > 0 ? 16 : -1;
    }

    public final int prepare(int i, int i2) {
        if (i2 == 1000) {
            int callingPid = Binder.getCallingPid();
            if (KZT_FW_PKG_NAME.equals(this.mAmInternal.getPackageNameByPid(callingPid))) {
                this.mKztFrameworkPid = callingPid;
            } else {
                this.mKztFrameworkPid = 0;
            }
        }
        if ((i & 64) > 0) {
            return this.mInjector.mOemNetdAdapterImpl.attachProbes(i);
        }
        if ((i & 4096) > 0
                || (i & 8192) > 0
                || (i & EndpointMonitorConst.FLAG_TRACING_PROCESS_PERMISSIONS_MODIFICATION) > 0
                || (i & 32) > 0) {
            return this.mInjector.mNative.setOffsets();
        }
        if (containsNetworkEventFlag(i)) {
            try {
                Log.i(TAG, "prepare() startMonitoringNetworkEvents() flags = " + i);
                KnoxNetworkEventService knoxNetworkEventService =
                        this.mInjector.getKnoxNetworkEventService();
                int networkEventTypeByFlag = getNetworkEventTypeByFlag(i);
                synchronized (knoxNetworkEventService) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("eventType", networkEventTypeByFlag);
                    KnoxNetworkEventService.KnoxNwEventHandler knoxNwEventHandler =
                            knoxNetworkEventService.mHandler;
                    if (knoxNwEventHandler != null) {
                        knoxNetworkEventService.mHandler.sendMessage(
                                Message.obtain(knoxNwEventHandler, 1, 0, 0, bundle));
                    }
                }
            } catch (Exception e) {
                DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                        e, "prepare() startMonitoringNetworkEvents error ", TAG);
                return -1;
            }
        }
        return 0;
    }

    public final void registerProcessObserverLocked() {
        if (!this.mSession.contains() || this.mProcessObserverRegistered.getAndSet(true)) {
            return;
        }
        try {
            this.mInjector.getClass();
            ActivityManager.getService().registerProcessObserver(this.mProcessObserver);
        } catch (RemoteException e) {
            NetdService$$ExternalSyntheticOutline0.m(
                    "Failed to register process observer: ", e, TAG);
        }
    }

    public final MonitoringTask rescheduleMonitoringTask(
            int i,
            int i2,
            int i3,
            int i4,
            int i5,
            IEndpointMonitorListener iEndpointMonitorListener,
            Predicate predicate) {
        return this.mInjector.mTaskRescheduler.reschedule(
                i, i2, i3, i4, i5, iEndpointMonitorListener, predicate);
    }

    public final int reset(int i) {
        if ((i & 64) > 0) {
            return this.mInjector.mOemNetdAdapterImpl.detachProbes(i);
        }
        if (containsNetworkEventFlag(i)) {
            try {
                Log.i(TAG, "reset() disableNetworkEventMonitoring called");
                KnoxNetworkEventService knoxNetworkEventService =
                        this.mInjector.getKnoxNetworkEventService();
                int networkEventTypeByFlag = getNetworkEventTypeByFlag(i);
                synchronized (knoxNetworkEventService) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("eventType", networkEventTypeByFlag);
                    KnoxNetworkEventService.KnoxNwEventHandler knoxNwEventHandler =
                            knoxNetworkEventService.mHandler;
                    if (knoxNwEventHandler != null) {
                        knoxNetworkEventService.mHandler.sendMessage(
                                Message.obtain(knoxNwEventHandler, 2, 0, 0, bundle));
                    }
                }
            } catch (Exception e) {
                DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                        e, "reset() disableNetworkEventMonitoring error ", TAG);
                return -1;
            }
        }
        return 0;
    }

    public final int startMonitoring(
            int i, int i2, Bundle bundle, IEndpointMonitorListener iEndpointMonitorListener) {
        int flags;
        String str = TAG;
        AccountsDb$CeDatabaseHelper$$ExternalSyntheticOutline0.m(
                i, i2, "startMonitoring() - type : ", ", reqId : ", str);
        if (bundle == null
                || iEndpointMonitorListener == null
                || !EndpointMonitorConst.validateTraceType(i)
                || (flags = getFlags(i, bundle)) <= 0) {
            return -2;
        }
        int i3 = bundle.getInt("mode", 3);
        if (!EndpointMonitorConst.validateMode(i3)) {
            return -2;
        }
        int i4 = bundle.getInt("extras", 0);
        Predicate filter = getFilter(bundle);
        synchronized (this.mSessionLock) {
            try {
                if (this.mSession.contains(i)) {
                    Log.e(str, "Failed :: Task already running");
                    return -4;
                }
                int prepare = prepare(flags, i2);
                if (prepare != 0) {
                    Log.e(str, "prepare(" + prepare + ")");
                    return -5;
                }
                int startTracing = startTracing(flags);
                if (startTracing == 0) {
                    return startMonitoringTask(
                            createMonitoringTask(
                                    i, i2, i3, flags, i4, iEndpointMonitorListener, filter));
                }
                Log.e(str, "startTracing(" + startTracing + ")");
                return -5;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:6:0x0025  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int startMonitoringTask(
            com.android.server.knox.zt.devicetrust.task.MonitoringTask r3) {
        /*
            r2 = this;
            boolean r0 = r3 instanceof com.android.server.knox.zt.devicetrust.task.ReschedulableMonitoringTask
            if (r0 == 0) goto Lb
            r0 = r3
            com.android.server.knox.zt.devicetrust.task.ReschedulableMonitoringTask r0 = (com.android.server.knox.zt.devicetrust.task.ReschedulableMonitoringTask) r0
            r0.schedule()
            goto L20
        Lb:
            boolean r0 = r3 instanceof com.android.server.knox.zt.devicetrust.task.SchedulableMonitoringTask
            if (r0 == 0) goto L16
            r0 = r3
            com.android.server.knox.zt.devicetrust.task.SchedulableMonitoringTask r0 = (com.android.server.knox.zt.devicetrust.task.SchedulableMonitoringTask) r0
            r0.schedule()
            goto L20
        L16:
            boolean r0 = r3 instanceof com.android.server.knox.zt.devicetrust.task.HandleableMonitoringTask
            if (r0 == 0) goto L22
            r0 = r3
            com.android.server.knox.zt.devicetrust.task.HandleableMonitoringTask r0 = (com.android.server.knox.zt.devicetrust.task.HandleableMonitoringTask) r0
            r0.establish()
        L20:
            r0 = 0
            goto L23
        L22:
            r0 = -5
        L23:
            if (r0 != 0) goto L2d
            com.android.server.knox.zt.devicetrust.EndpointMonitorImpl$MonitoringSession r1 = r2.mSession
            r1.add(r3)
            r2.registerProcessObserverLocked()
        L2d:
            return r0
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.knox.zt.devicetrust.EndpointMonitorImpl.startMonitoringTask(com.android.server.knox.zt.devicetrust.task.MonitoringTask):int");
    }

    public final int startTracing(int i) {
        if (!containsNetworkEventFlag(i)) {
            return this.mInjector.mNative.startTracing(i);
        }
        Log.i(TAG, "prepare() startNetworkEventLogging called");
        return this.mInjector.mNative.startNetworkEventLogging(getNetworkEventTypeByFlag(i));
    }

    public final int stopMonitoring(int i, int i2) {
        String str = TAG;
        AccountsDb$CeDatabaseHelper$$ExternalSyntheticOutline0.m(
                i, i2, "stopMonitoring() - type : ", ", reqId : ", str);
        if (!EndpointMonitorConst.validateTraceType(i)) {
            return -2;
        }
        synchronized (this.mSessionLock) {
            try {
                MonitoringTask find = this.mSession.find(i);
                if (find == null) {
                    Log.e(str, "Session not found");
                    return 0;
                }
                if (!find.checkPermission(i2)) {
                    return -1;
                }
                return stopMonitoringInner(find);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void stopMonitoring(int i) {
        String str = TAG;
        DirEncryptService$$ExternalSyntheticOutline0.m(i, "stopMonitoring() - reqId : ", str);
        synchronized (this.mSessionLock) {
            try {
                List<MonitoringTask> findByUid = this.mSession.findByUid(i);
                if (findByUid != null && findByUid.size() != 0) {
                    for (MonitoringTask monitoringTask : findByUid) {
                        Log.d(
                                TAG,
                                String.format(
                                        "stopMonitoring() - Task : %s, Result : %d",
                                        monitoringTask.getTag(),
                                        Integer.valueOf(stopMonitoringInner(monitoringTask))));
                    }
                    return;
                }
                Log.e(str, "Session not found");
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final int stopMonitoringInner(MonitoringTask monitoringTask) {
        int reset = reset(monitoringTask.mFlags);
        if (reset != 0) {
            AudioDeviceInventory$$ExternalSyntheticOutline0.m(reset, "reset(", ")", TAG);
        }
        int stopMonitoringTask = stopMonitoringTask(monitoringTask);
        if (stopMonitoringTask != 0) {
            AudioDeviceInventory$$ExternalSyntheticOutline0.m(
                    stopMonitoringTask, "stopMonitoringTask(", ")", TAG);
        }
        int stopTracing = stopTracing(monitoringTask.mFlags);
        if (stopTracing != 0) {
            AudioDeviceInventory$$ExternalSyntheticOutline0.m(
                    stopTracing, "stopTracing(", ")", TAG);
        }
        return stopTracing;
    }

    /* JADX WARN: Removed duplicated region for block: B:6:0x0025  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int stopMonitoringTask(
            com.android.server.knox.zt.devicetrust.task.MonitoringTask r3) {
        /*
            r2 = this;
            boolean r0 = r3 instanceof com.android.server.knox.zt.devicetrust.task.ReschedulableMonitoringTask
            if (r0 == 0) goto Lb
            r0 = r3
            com.android.server.knox.zt.devicetrust.task.ReschedulableMonitoringTask r0 = (com.android.server.knox.zt.devicetrust.task.ReschedulableMonitoringTask) r0
            r0.keep()
            goto L20
        Lb:
            boolean r0 = r3 instanceof com.android.server.knox.zt.devicetrust.task.SchedulableMonitoringTask
            if (r0 == 0) goto L16
            r0 = r3
            com.android.server.knox.zt.devicetrust.task.SchedulableMonitoringTask r0 = (com.android.server.knox.zt.devicetrust.task.SchedulableMonitoringTask) r0
            r0.terminate()
            goto L20
        L16:
            boolean r0 = r3 instanceof com.android.server.knox.zt.devicetrust.task.HandleableMonitoringTask
            if (r0 == 0) goto L22
            r0 = r3
            com.android.server.knox.zt.devicetrust.task.HandleableMonitoringTask r0 = (com.android.server.knox.zt.devicetrust.task.HandleableMonitoringTask) r0
            r0.release()
        L20:
            r0 = 0
            goto L23
        L22:
            r0 = -5
        L23:
            if (r0 != 0) goto L2f
            com.android.server.knox.zt.devicetrust.EndpointMonitorImpl$MonitoringSession r1 = r2.mSession
            int r3 = r3.mType
            r1.remove(r3)
            r2.unregisterProcessObserverLocked()
        L2f:
            return r0
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.knox.zt.devicetrust.EndpointMonitorImpl.stopMonitoringTask(com.android.server.knox.zt.devicetrust.task.MonitoringTask):int");
    }

    public final int stopTracing(int i) {
        return this.mInjector.mNative.stopTracing(i);
    }

    public final void unregisterProcessObserverLocked() {
        if (this.mSession.contains() || !this.mProcessObserverRegistered.getAndSet(false)) {
            return;
        }
        try {
            this.mInjector.getClass();
            ActivityManager.getService().unregisterProcessObserver(this.mProcessObserver);
        } catch (RemoteException e) {
            NetdService$$ExternalSyntheticOutline0.m(
                    "Failed to unregister process observer: ", e, TAG);
        }
    }
}
