package com.android.server.health;

import android.hardware.health.HealthInfo;
import android.hardware.health.V2_0.IHealth;
import android.hidl.manager.V1_0.IServiceManager;
import android.hidl.manager.V1_0.IServiceNotification;
import android.os.BatteryManagerInternal;
import android.os.BatteryProperty;
import android.os.HandlerThread;
import android.os.HwBinder;
import android.os.IHwBinder;
import android.os.IHwInterface;
import android.os.RemoteException;
import android.os.Trace;
import android.util.MutableInt;
import android.util.Slog;

import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.StorageManagerService$$ExternalSyntheticOutline0;

import vendor.samsung.hardware.health.V2_0.ISehHealth$Proxy;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class HealthServiceWrapperHidl extends HealthServiceWrapper {
    public final Callback mCallback;
    public final IHealthSupplier mHealthSupplier;
    public final String mInstanceName;
    public final Notification mNotification = new Notification();
    public final HandlerThread mHandlerThread = new HandlerThread("HealthServiceHwbinder");
    public final AtomicReference mLastService = new AtomicReference();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface Callback {}

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface IHealthSupplier {
        static IHealth get(String str) {
            IHwBinder service =
                    HwBinder.getService("android.hardware.health@2.0::IHealth", str, true);
            if (service == null) {
                return null;
            }
            IHwInterface queryLocalInterface =
                    service.queryLocalInterface("android.hardware.health@2.0::IHealth");
            if (queryLocalInterface != null && (queryLocalInterface instanceof IHealth)) {
                return (IHealth) queryLocalInterface;
            }
            IHealth.Proxy proxy = new IHealth.Proxy();
            proxy.mRemote = service;
            try {
                Iterator it = proxy.interfaceChain().iterator();
                while (it.hasNext()) {
                    if (((String) it.next()).equals("android.hardware.health@2.0::IHealth")) {
                        return proxy;
                    }
                }
                return null;
            } catch (RemoteException unused) {
                return null;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface IServiceManagerSupplier {}

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Mutable {
        public HealthInfo value;
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Notification extends IServiceNotification.Stub {
        public Notification() {}

        @Override // android.hidl.manager.V1_0.IServiceNotification
        public final void onRegistration(String str, String str2, boolean z) {
            if ("android.hardware.health@2.0::IHealth".equals(str)
                    && HealthServiceWrapperHidl.this.mInstanceName.equals(str2)) {
                HealthServiceWrapperHidl.this
                        .mHandlerThread
                        .getThreadHandler()
                        .post(
                                new Runnable() { // from class:
                                                 // com.android.server.health.HealthServiceWrapperHidl.Notification.1
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        try {
                                            HealthServiceWrapperHidl healthServiceWrapperHidl =
                                                    HealthServiceWrapperHidl.this;
                                            IHealthSupplier iHealthSupplier =
                                                    healthServiceWrapperHidl.mHealthSupplier;
                                            String str3 = healthServiceWrapperHidl.mInstanceName;
                                            iHealthSupplier.getClass();
                                            IHealth iHealth = IHealthSupplier.get(str3);
                                            IHealth iHealth2 =
                                                    (IHealth)
                                                            HealthServiceWrapperHidl.this
                                                                    .mLastService.getAndSet(
                                                                    iHealth);
                                            if (Objects.equals(iHealth, iHealth2)) {
                                                return;
                                            }
                                            Slog.i(
                                                    "HealthServiceWrapperHidl",
                                                    "health: new instance registered "
                                                            + HealthServiceWrapperHidl.this
                                                                    .mInstanceName);
                                            Callback callback =
                                                    HealthServiceWrapperHidl.this.mCallback;
                                            if (callback == null) {
                                                return;
                                            }
                                            ((HealthHalCallbackHidl) callback)
                                                    .onRegistration(iHealth2, iHealth);
                                        } catch (RemoteException | NoSuchElementException e) {
                                            Slog.e(
                                                    "HealthServiceWrapperHidl",
                                                    "health: Cannot get instance '"
                                                            + HealthServiceWrapperHidl.this
                                                                    .mInstanceName
                                                            + "': "
                                                            + e.getMessage()
                                                            + ". Perhaps no permission?");
                                        }
                                    }
                                });
            }
        }
    }

    public HealthServiceWrapperHidl(
            Callback callback,
            IServiceManagerSupplier iServiceManagerSupplier,
            IHealthSupplier iHealthSupplier)
            throws RemoteException, NoSuchElementException, NullPointerException {
        IHealth iHealth;
        if (iServiceManagerSupplier == null) {
            throw null;
        }
        if (iHealthSupplier == null) {
            throw null;
        }
        this.mHealthSupplier = iHealthSupplier;
        Trace.traceBegin(524288L, "HealthInitGetService_default");
        try {
            iHealth = IHealthSupplier.get("default");
        } catch (NoSuchElementException unused) {
            traceEnd();
            iHealth = null;
        } catch (Throwable th) {
            throw th;
        }
        Slog.d("HealthServiceWrapperHidl", "HealthServiceWrapperHidl: newService: " + iHealth);
        if (iHealth != null) {
            this.mInstanceName = "default";
            this.mLastService.set(iHealth);
        }
        if (this.mInstanceName == null || iHealth == null) {
            throw new NoSuchElementException(
                    "IHealth service instance default isn't available. Perhaps no permission?");
        }
        if (callback != null) {
            this.mCallback = callback;
            ((HealthHalCallbackHidl) callback).onRegistration(null, iHealth);
        }
        Trace.traceBegin(524288L, "HealthInitRegisterNotification");
        this.mHandlerThread.start();
        try {
            IServiceManager.getService()
                    .registerForNotifications(
                            "android.hardware.health@2.0::IHealth",
                            this.mInstanceName,
                            this.mNotification);
            traceEnd();
            DeviceIdleController$$ExternalSyntheticOutline0.m(
                    new StringBuilder("health: HealthServiceWrapper listening to instance "),
                    this.mInstanceName,
                    "HealthServiceWrapperHidl");
        } finally {
            traceEnd();
        }
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
        Mutable mutable = new Mutable();
        iHealth.getHealthInfo(new HealthServiceWrapperHidl$$ExternalSyntheticLambda2(mutable));
        return mutable.value;
    }

    @Override // com.android.server.health.HealthServiceWrapper
    public final int getProperty(int i, BatteryProperty batteryProperty) {
        Trace.traceBegin(524288L, "HealthGetProperty");
        try {
            IHealth iHealth = (IHealth) this.mLastService.get();
            if (iHealth == null) {
                throw new RemoteException("no health service");
            }
            MutableInt mutableInt = new MutableInt(1);
            if (i != 10) {
                switch (i) {
                    case 1:
                        iHealth.getChargeCounter(
                                new HealthServiceWrapperHidl$$ExternalSyntheticLambda1(
                                        mutableInt, batteryProperty));
                        break;
                    case 2:
                        iHealth.getCurrentNow(
                                new HealthServiceWrapperHidl$$ExternalSyntheticLambda1(
                                        mutableInt, batteryProperty));
                        break;
                    case 3:
                        iHealth.getCurrentAverage(
                                new HealthServiceWrapperHidl$$ExternalSyntheticLambda1(
                                        mutableInt, batteryProperty));
                        break;
                    case 4:
                        iHealth.getCapacity(
                                new HealthServiceWrapperHidl$$ExternalSyntheticLambda1(
                                        mutableInt, batteryProperty));
                        break;
                    case 5:
                        iHealth.getEnergyCounter(
                                new HealthServiceWrapperHidl$$ExternalSyntheticLambda1(
                                        mutableInt, batteryProperty));
                        break;
                    case 6:
                        iHealth.getChargeStatus(
                                new HealthServiceWrapperHidl$$ExternalSyntheticLambda1(
                                        mutableInt, batteryProperty));
                        break;
                }
            } else {
                int batteryStateOfHealth =
                        ((BatteryManagerInternal)
                                        LocalServices.getService(BatteryManagerInternal.class))
                                .getBatteryStateOfHealth();
                if (batteryStateOfHealth > -1) {
                    mutableInt.value = 0;
                    batteryProperty.setLong(batteryStateOfHealth);
                }
            }
            int i2 = mutableInt.value;
            traceEnd();
            return i2;
        } catch (Throwable th) {
            traceEnd();
            throw th;
        }
    }

    @Override // com.android.server.health.HealthServiceWrapper
    public final void scheduleUpdate() {
        getHandlerThread()
                .getThreadHandler()
                .post(
                        new Runnable() { // from class:
                                         // com.android.server.health.HealthServiceWrapperHidl$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                HealthServiceWrapperHidl healthServiceWrapperHidl =
                                        HealthServiceWrapperHidl.this;
                                healthServiceWrapperHidl.getClass();
                                Trace.traceBegin(524288L, "HealthScheduleUpdate");
                                try {
                                    try {
                                        IHealth iHealth =
                                                (IHealth)
                                                        healthServiceWrapperHidl.mLastService.get();
                                        if (iHealth == null) {
                                            Slog.e("HealthServiceWrapperHidl", "no health service");
                                        } else {
                                            iHealth.update();
                                        }
                                    } catch (RemoteException e) {
                                        Slog.e(
                                                "HealthServiceWrapperHidl",
                                                "Cannot call update on health HAL",
                                                e);
                                    }
                                } finally {
                                    HealthServiceWrapperHidl.traceEnd();
                                }
                            }
                        });
    }

    @Override // com.android.server.health.HealthServiceWrapper
    public final void sehWriteEnableToParam(int i, String str, boolean z) {
        try {
            boolean z2 = true;
            IHwBinder service =
                    HwBinder.getService(
                            "vendor.samsung.hardware.health@2.0::ISehHealth", "default", true);
            ISehHealth$Proxy iSehHealth$Proxy = null;
            if (service != null) {
                IHwInterface queryLocalInterface =
                        service.queryLocalInterface(
                                "vendor.samsung.hardware.health@2.0::ISehHealth");
                if (queryLocalInterface == null
                        || !(queryLocalInterface instanceof ISehHealth$Proxy)) {
                    ISehHealth$Proxy iSehHealth$Proxy2 = new ISehHealth$Proxy();
                    iSehHealth$Proxy2.mRemote = service;
                    try {
                        Iterator it = iSehHealth$Proxy2.interfaceChain().iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                break;
                            } else if (((String) it.next())
                                    .equals("vendor.samsung.hardware.health@2.0::ISehHealth")) {
                                iSehHealth$Proxy = iSehHealth$Proxy2;
                                break;
                            }
                        }
                    } catch (RemoteException unused) {
                    }
                } else {
                    iSehHealth$Proxy = (ISehHealth$Proxy) queryLocalInterface;
                }
            }
            if (iSehHealth$Proxy == null) {
                throw new RemoteException("no seh health service");
            }
            if (iSehHealth$Proxy.sehWriteEnableToParam(i, z) != 0) {
                z2 = false;
            }
            Slog.d(
                    "HealthServiceWrapperHidl",
                    "sehWriteEnableToParam["
                            + str
                            + "]: , result : "
                            + z2
                            + ", offset: "
                            + i
                            + ", enable: "
                            + z);
        } catch (Exception e) {
            StringBuilder m =
                    StorageManagerService$$ExternalSyntheticOutline0.m(
                            i,
                            "Exception in sehWriteEnableToParam[",
                            str,
                            "]: , offset: ",
                            ", enable: ");
            m.append(z);
            Slog.e("HealthServiceWrapperHidl", m.toString());
            e.printStackTrace();
        }
    }
}
