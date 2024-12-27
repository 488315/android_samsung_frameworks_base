package com.android.server.health;

import android.hardware.audio.common.V2_0.AudioOffloadInfo$$ExternalSyntheticOutline0;
import android.hardware.health.HealthInfo;
import android.hardware.health.IHealth;
import android.os.BatteryManagerInternal;
import android.os.BatteryProperty;
import android.os.Binder;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.IInterface;
import android.os.IServiceCallback;
import android.os.RemoteException;
import android.os.ServiceSpecificException;
import android.os.Trace;
import android.util.Slog;

import com.android.internal.hidden_from_bootclasspath.android.os.Flags;
import com.android.server.LocalServices;
import com.android.server.StorageManagerService$$ExternalSyntheticOutline0;

import vendor.samsung.hardware.health.ISehHealth;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class HealthServiceWrapperAidl extends HealthServiceWrapper {
    static final String SERVICE_NAME =
            AudioOffloadInfo$$ExternalSyntheticOutline0.m(
                    new StringBuilder(), IHealth.DESCRIPTOR, "/default");
    public final HandlerThread mHandlerThread = new HandlerThread("HealthServiceBinder");
    public final AtomicReference mLastSehService;
    public final AtomicReference mLastService;
    public final HealthRegCallbackAidl mRegCallback;
    public final ServiceCallback mServiceCallback;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ServiceCallback extends IServiceCallback.Stub {
        public ServiceCallback() {}

        public final void onRegistration(String str, final IBinder iBinder) {
            if (HealthServiceWrapperAidl.SERVICE_NAME.equals(str)) {
                HealthServiceWrapperAidl.this
                        .getHandlerThread()
                        .getThreadHandler()
                        .post(
                                new Runnable() { // from class:
                                    // com.android.server.health.HealthServiceWrapperAidl$ServiceCallback$$ExternalSyntheticLambda0
                                    /* JADX WARN: Multi-variable type inference failed */
                                    /* JADX WARN: Type inference failed for: r4v3, types: [android.hardware.health.IHealth] */
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        IHealth.Stub.Proxy proxy;
                                        ISehHealth iSehHealth;
                                        HealthServiceWrapperAidl.ServiceCallback serviceCallback =
                                                HealthServiceWrapperAidl.ServiceCallback.this;
                                        IBinder iBinder2 = iBinder;
                                        serviceCallback.getClass();
                                        IBinder allowBlocking = Binder.allowBlocking(iBinder2);
                                        int i = IHealth.Stub.$r8$clinit;
                                        ISehHealth iSehHealth2 = null;
                                        if (allowBlocking == null) {
                                            proxy = null;
                                        } else {
                                            IInterface queryLocalInterface =
                                                    allowBlocking.queryLocalInterface(
                                                            IHealth.DESCRIPTOR);
                                            if (queryLocalInterface == null
                                                    || !(queryLocalInterface instanceof IHealth)) {
                                                IHealth.Stub.Proxy proxy2 =
                                                        new IHealth.Stub.Proxy();
                                                proxy2.mRemote = allowBlocking;
                                                proxy = proxy2;
                                            } else {
                                                proxy = (IHealth) queryLocalInterface;
                                            }
                                        }
                                        IHealth iHealth =
                                                (IHealth)
                                                        HealthServiceWrapperAidl.this.mLastService
                                                                .getAndSet(proxy);
                                        if (Objects.equals(
                                                iBinder2,
                                                iHealth != null
                                                        ? ((IHealth.Stub.Proxy) iHealth).mRemote
                                                        : null)) {
                                            return;
                                        }
                                        try {
                                            IBinder extension =
                                                    Binder.allowBlocking(iBinder2).getExtension();
                                            int i2 = ISehHealth.Stub.$r8$clinit;
                                            if (extension != null) {
                                                IInterface queryLocalInterface2 =
                                                        extension.queryLocalInterface(
                                                                ISehHealth.DESCRIPTOR);
                                                if (queryLocalInterface2 == null
                                                        || !(queryLocalInterface2
                                                                instanceof ISehHealth)) {
                                                    ISehHealth.Stub.Proxy proxy3 =
                                                            new ISehHealth.Stub.Proxy();
                                                    proxy3.mRemote = extension;
                                                    iSehHealth = proxy3;
                                                } else {
                                                    iSehHealth = (ISehHealth) queryLocalInterface2;
                                                }
                                                iSehHealth2 = iSehHealth;
                                            }
                                        } catch (Exception e) {
                                            Slog.e(
                                                    "HealthServiceWrapperAidl",
                                                    "Unable to getExtension for health ",
                                                    e);
                                        }
                                        if (iSehHealth2 == null) {
                                            Slog.e(
                                                    "HealthServiceWrapperAidl",
                                                    "HealthServiceWrapperAidl: ServiceCallback:"
                                                        + " newSehService: "
                                                            + iSehHealth2);
                                            throw new NoSuchElementException(
                                                    "ServiceCallback: ISehHealth service instance"
                                                        + " isn't available. Perhaps no"
                                                        + " permission?");
                                        }
                                        ISehHealth iSehHealth3 =
                                                (ISehHealth)
                                                        HealthServiceWrapperAidl.this
                                                                .mLastSehService.getAndSet(
                                                                iSehHealth2);
                                        Slog.i(
                                                "HealthServiceWrapperAidl",
                                                "New health AIDL HAL service registered");
                                        HealthRegCallbackAidl healthRegCallbackAidl =
                                                HealthServiceWrapperAidl.this.mRegCallback;
                                        if (healthRegCallbackAidl != null) {
                                            healthRegCallbackAidl.onRegistration(
                                                    iSehHealth3, iSehHealth2);
                                        }
                                    }
                                });
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface ServiceManagerStub {}

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
    jadx.core.utils.exceptions.JadxRuntimeException: Can't find top splitter block for handler:B:26:0x0094
    	at jadx.core.utils.BlockUtils.getTopSplitterForHandler(BlockUtils.java:1179)
    	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.collectHandlerRegions(ExcHandlersRegionMaker.java:53)
    	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.process(ExcHandlersRegionMaker.java:38)
    	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:27)
    */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:14:0x00a3  */
    /* JADX WARN: Type inference failed for: r7v3, types: [android.hardware.health.IHealth] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public HealthServiceWrapperAidl(
            com.android.server.health.HealthRegCallbackAidl r10,
            com.android.server.health.HealthServiceWrapperAidl.ServiceManagerStub r11) {
        /*
            r9 = this;
            java.lang.String r0 = "HealthServiceWrapperAidl"
            java.lang.String r1 = "HealthServiceWrapperAidl: newService: "
            r9.<init>()
            android.os.HandlerThread r2 = new android.os.HandlerThread
            java.lang.String r3 = "HealthServiceBinder"
            r2.<init>(r3)
            r9.mHandlerThread = r2
            java.util.concurrent.atomic.AtomicReference r2 = new java.util.concurrent.atomic.AtomicReference
            r2.<init>()
            r9.mLastService = r2
            com.android.server.health.HealthServiceWrapperAidl$ServiceCallback r3 = new com.android.server.health.HealthServiceWrapperAidl$ServiceCallback
            r3.<init>()
            r9.mServiceCallback = r3
            java.util.concurrent.atomic.AtomicReference r3 = new java.util.concurrent.atomic.AtomicReference
            r3.<init>()
            r9.mLastSehService = r3
            r3 = 524288(0x80000, double:2.590327E-318)
            java.lang.String r5 = "HealthInitGetServiceAidl"
            android.os.Trace.traceBegin(r3, r5)
            java.lang.String r5 = com.android.server.health.HealthServiceWrapperAidl.SERVICE_NAME     // Catch: java.lang.Throwable -> Lce
            r11.getClass()     // Catch: java.lang.Throwable -> Lce
            android.os.IBinder r11 = android.os.ServiceManager.waitForDeclaredService(r5)     // Catch: java.lang.Throwable -> Lce
            int r6 = android.hardware.health.IHealth.Stub.$r8$clinit     // Catch: java.lang.Throwable -> Lce
            r6 = 0
            if (r11 != 0) goto L3d
            r7 = r6
            goto L53
        L3d:
            java.lang.String r7 = android.hardware.health.IHealth.DESCRIPTOR     // Catch: java.lang.Throwable -> Lce
            android.os.IInterface r7 = r11.queryLocalInterface(r7)     // Catch: java.lang.Throwable -> Lce
            if (r7 == 0) goto L4c
            boolean r8 = r7 instanceof android.hardware.health.IHealth     // Catch: java.lang.Throwable -> Lce
            if (r8 == 0) goto L4c
            android.hardware.health.IHealth r7 = (android.hardware.health.IHealth) r7     // Catch: java.lang.Throwable -> Lce
            goto L53
        L4c:
            android.hardware.health.IHealth$Stub$Proxy r7 = new android.hardware.health.IHealth$Stub$Proxy     // Catch: java.lang.Throwable -> Lce
            r7.<init>()     // Catch: java.lang.Throwable -> Lce
            r7.mRemote = r11     // Catch: java.lang.Throwable -> Lce
        L53:
            java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> Lce
            r11.<init>(r1)     // Catch: java.lang.Throwable -> Lce
            r11.append(r7)     // Catch: java.lang.Throwable -> Lce
            java.lang.String r11 = r11.toString()     // Catch: java.lang.Throwable -> Lce
            android.util.Slog.d(r0, r11)     // Catch: java.lang.Throwable -> Lce
            traceEnd()
            if (r7 == 0) goto Lc6
            r2.set(r7)
            android.os.IBinder r11 = android.os.ServiceManager.waitForDeclaredService(r5)
            android.os.IBinder r11 = android.os.Binder.allowBlocking(r11)
            if (r11 == 0) goto L99
            android.os.IBinder r11 = r11.getExtension()     // Catch: android.os.RemoteException -> L94
            int r1 = vendor.samsung.hardware.health.ISehHealth.Stub.$r8$clinit     // Catch: android.os.RemoteException -> L94
            if (r11 != 0) goto L7d
            goto L99
        L7d:
            java.lang.String r1 = vendor.samsung.hardware.health.ISehHealth.DESCRIPTOR     // Catch: android.os.RemoteException -> L94
            android.os.IInterface r1 = r11.queryLocalInterface(r1)     // Catch: android.os.RemoteException -> L94
            if (r1 == 0) goto L8c
            boolean r2 = r1 instanceof vendor.samsung.hardware.health.ISehHealth     // Catch: android.os.RemoteException -> L94
            if (r2 == 0) goto L8c
            vendor.samsung.hardware.health.ISehHealth r1 = (vendor.samsung.hardware.health.ISehHealth) r1     // Catch: android.os.RemoteException -> L94
            goto L9a
        L8c:
            vendor.samsung.hardware.health.ISehHealth$Stub$Proxy r1 = new vendor.samsung.hardware.health.ISehHealth$Stub$Proxy     // Catch: android.os.RemoteException -> L94
            r1.<init>()     // Catch: android.os.RemoteException -> L94
            r1.mRemote = r11     // Catch: android.os.RemoteException -> L94
            goto L9a
        L94:
            java.lang.String r11 = "Unable to register DeathRecipient for null"
            android.util.Slog.e(r0, r11)
        L99:
            r1 = r6
        L9a:
            java.util.concurrent.atomic.AtomicReference r11 = r9.mLastSehService
            r11.set(r1)
            r9.mRegCallback = r10
            if (r10 == 0) goto La6
            r10.onRegistration(r6, r1)
        La6:
            java.lang.String r10 = "HealthInitRegisterNotificationAidl"
            android.os.Trace.traceBegin(r3, r10)
            android.os.HandlerThread r10 = r9.mHandlerThread
            r10.start()
            java.lang.String r10 = com.android.server.health.HealthServiceWrapperAidl.SERVICE_NAME     // Catch: java.lang.Throwable -> Lc1
            com.android.server.health.HealthServiceWrapperAidl$ServiceCallback r9 = r9.mServiceCallback     // Catch: java.lang.Throwable -> Lc1
            android.os.ServiceManager.registerForNotifications(r10, r9)     // Catch: java.lang.Throwable -> Lc1
            traceEnd()
            java.lang.String r9 = "health: HealthServiceWrapper listening to AIDL HAL"
            android.util.Slog.i(r0, r9)
            return
        Lc1:
            r9 = move-exception
            traceEnd()
            throw r9
        Lc6:
            java.util.NoSuchElementException r9 = new java.util.NoSuchElementException
            java.lang.String r10 = "IHealth service instance isn't available. Perhaps no permission?"
            r9.<init>(r10)
            throw r9
        Lce:
            r9 = move-exception
            traceEnd()
            throw r9
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.health.HealthServiceWrapperAidl.<init>(com.android.server.health.HealthRegCallbackAidl,"
                    + " com.android.server.health.HealthServiceWrapperAidl$ServiceManagerStub):void");
    }

    public static void traceEnd() {
        Trace.traceEnd(524288L);
    }

    @Override // com.android.server.health.HealthServiceWrapper
    public HandlerThread getHandlerThread() {
        return this.mHandlerThread;
    }

    @Override // com.android.server.health.HealthServiceWrapper
    public final HealthInfo getHealthInfo() {
        IHealth iHealth = (IHealth) this.mLastService.get();
        if (iHealth == null) {
            return null;
        }
        try {
            return ((IHealth.Stub.Proxy) iHealth).getHealthInfo();
        } catch (UnsupportedOperationException | ServiceSpecificException unused) {
            return null;
        }
    }

    @Override // com.android.server.health.HealthServiceWrapper
    public final int getProperty(int i, BatteryProperty batteryProperty) {
        Trace.traceBegin(524288L, "HealthGetPropertyAidl");
        try {
            return getPropertyInternal(i, batteryProperty);
        } finally {
            traceEnd();
        }
    }

    public final int getPropertyInternal(int i, BatteryProperty batteryProperty) {
        int batteryStateOfHealth;
        IHealth iHealth = (IHealth) this.mLastService.get();
        if (iHealth == null) {
            throw new RemoteException("no health service");
        }
        try {
            switch (i) {
                case 1:
                    batteryProperty.setLong(((IHealth.Stub.Proxy) iHealth).getChargeCounterUah());
                    break;
                case 2:
                    batteryProperty.setLong(
                            ((IHealth.Stub.Proxy) iHealth).getCurrentNowMicroamps());
                    break;
                case 3:
                    batteryProperty.setLong(
                            ((IHealth.Stub.Proxy) iHealth).getCurrentAverageMicroamps());
                    break;
                case 4:
                    batteryProperty.setLong(((IHealth.Stub.Proxy) iHealth).getCapacity());
                    break;
                case 5:
                    batteryProperty.setLong(((IHealth.Stub.Proxy) iHealth).getEnergyCounterNwh());
                    break;
                case 6:
                    batteryProperty.setLong(((IHealth.Stub.Proxy) iHealth).getChargeStatus());
                    break;
                case 7:
                    batteryProperty.setLong(
                            ((IHealth.Stub.Proxy) iHealth)
                                    .getBatteryHealthData()
                                    .batteryManufacturingDateSeconds);
                    break;
                case 8:
                    batteryProperty.setLong(
                            ((IHealth.Stub.Proxy) iHealth)
                                    .getBatteryHealthData()
                                    .batteryFirstUsageSeconds);
                    break;
                case 9:
                    batteryProperty.setLong(((IHealth.Stub.Proxy) iHealth).getChargingPolicy());
                    break;
                case 10:
                    batteryProperty.setLong(
                            ((IHealth.Stub.Proxy) iHealth)
                                    .getBatteryHealthData()
                                    .batteryStateOfHealth);
                    break;
                case 11:
                    if (Flags.batteryPartStatusApi()) {
                        batteryProperty.setString(
                                ((IHealth.Stub.Proxy) iHealth)
                                        .getBatteryHealthData()
                                        .batterySerialNumber);
                        break;
                    }
                    break;
                case 12:
                    if (Flags.batteryPartStatusApi()) {
                        batteryProperty.setLong(
                                ((IHealth.Stub.Proxy) iHealth)
                                        .getBatteryHealthData()
                                        .batteryPartStatus);
                        break;
                    }
                    break;
                default:
                    return 0;
            }
            return 0;
        } catch (RemoteException unused) {
            if (10 != i
                    || (batteryStateOfHealth =
                                    ((BatteryManagerInternal)
                                                    LocalServices.getService(
                                                            BatteryManagerInternal.class))
                                            .getBatteryStateOfHealth())
                            <= -1) {
                return -3;
            }
            batteryProperty.setLong(batteryStateOfHealth);
            return 0;
        } catch (UnsupportedOperationException unused2) {
            return -1;
        } catch (ServiceSpecificException unused3) {
            return -2;
        }
    }

    @Override // com.android.server.health.HealthServiceWrapper
    public final void scheduleUpdate() {
        getHandlerThread()
                .getThreadHandler()
                .post(
                        new Runnable() { // from class:
                                         // com.android.server.health.HealthServiceWrapperAidl$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                HealthServiceWrapperAidl healthServiceWrapperAidl =
                                        HealthServiceWrapperAidl.this;
                                healthServiceWrapperAidl.getClass();
                                Trace.traceBegin(524288L, "HealthScheduleUpdate");
                                try {
                                    try {
                                        IHealth iHealth =
                                                (IHealth)
                                                        healthServiceWrapperAidl.mLastService.get();
                                        if (iHealth == null) {
                                            Slog.e("HealthServiceWrapperAidl", "no health service");
                                        } else {
                                            ((IHealth.Stub.Proxy) iHealth).update();
                                        }
                                    } catch (RemoteException | ServiceSpecificException e) {
                                        Slog.e(
                                                "HealthServiceWrapperAidl",
                                                "Cannot call update on health AIDL HAL",
                                                e);
                                    }
                                } finally {
                                    HealthServiceWrapperAidl.traceEnd();
                                }
                            }
                        });
    }

    @Override // com.android.server.health.HealthServiceWrapper
    public final void sehWriteEnableToParam(int i, String str, boolean z) {
        try {
            ISehHealth iSehHealth = (ISehHealth) this.mLastSehService.get();
            if (iSehHealth == null) {
                throw new RemoteException("no seh health service");
            }
            ((ISehHealth.Stub.Proxy) iSehHealth).sehWriteEnableToParam(i, z);
            Slog.d(
                    "HealthServiceWrapperAidl",
                    "sehWriteEnableToParam[" + str + "]: , offset: " + i + ", enable: " + z);
        } catch (Exception e) {
            StringBuilder m =
                    StorageManagerService$$ExternalSyntheticOutline0.m(
                            i,
                            "Exception in sehWriteEnableToParam[",
                            str,
                            "]: , offset: ",
                            ", enable: ");
            m.append(z);
            Slog.e("HealthServiceWrapperAidl", m.toString());
            e.printStackTrace();
        }
    }
}
