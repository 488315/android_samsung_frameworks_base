package com.android.server.health;

import android.hardware.health.HealthInfo;
import android.hardware.health.Translate;
import android.hardware.health.V2_0.IHealth;
import android.hidl.manager.V1_0.IServiceManager;
import android.hidl.manager.V1_0.IServiceNotification;
import android.os.BatteryProperty;
import android.os.HandlerThread;
import android.os.RemoteException;
import android.os.Trace;
import android.util.MutableInt;
import android.util.Slog;
import com.android.server.health.HealthServiceWrapperHidl;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import vendor.samsung.hardware.health.V2_0.ISehHealth;

/* loaded from: classes2.dex */
public final class HealthServiceWrapperHidl extends HealthServiceWrapper {
    public Callback mCallback;
    public IHealthSupplier mHealthSupplier;
    public String mInstanceName;
    public final IServiceNotification mNotification = new Notification();
    public final HandlerThread mHandlerThread = new HandlerThread("HealthServiceHwbinder");
    public final AtomicReference mLastService = new AtomicReference();

    public interface Callback {
        void onRegistration(IHealth iHealth, IHealth iHealth2, String str);
    }

    public class Mutable {
        public Object value;

        public Mutable() {
        }
    }

    public static void traceBegin(String str) {
        Trace.traceBegin(524288L, str);
    }

    public static void traceEnd() {
        Trace.traceEnd(524288L);
    }

    @Override // com.android.server.health.HealthServiceWrapper
    public int getProperty(int i, final BatteryProperty batteryProperty) {
        traceBegin("HealthGetProperty");
        try {
            IHealth iHealth = (IHealth) this.mLastService.get();
            if (iHealth == null) {
                throw new RemoteException("no health service");
            }
            final MutableInt mutableInt = new MutableInt(1);
            switch (i) {
                case 1:
                    iHealth.getChargeCounter(new IHealth.getChargeCounterCallback() { // from class: com.android.server.health.HealthServiceWrapperHidl$$ExternalSyntheticLambda0
                        @Override // android.hardware.health.V2_0.IHealth.getChargeCounterCallback
                        public final void onValues(int i2, int i3) {
                            HealthServiceWrapperHidl.lambda$getProperty$0(mutableInt, batteryProperty, i2, i3);
                        }
                    });
                    break;
                case 2:
                    iHealth.getCurrentNow(new IHealth.getCurrentNowCallback() { // from class: com.android.server.health.HealthServiceWrapperHidl$$ExternalSyntheticLambda1
                        @Override // android.hardware.health.V2_0.IHealth.getCurrentNowCallback
                        public final void onValues(int i2, int i3) {
                            HealthServiceWrapperHidl.lambda$getProperty$1(mutableInt, batteryProperty, i2, i3);
                        }
                    });
                    break;
                case 3:
                    iHealth.getCurrentAverage(new IHealth.getCurrentAverageCallback() { // from class: com.android.server.health.HealthServiceWrapperHidl$$ExternalSyntheticLambda2
                        @Override // android.hardware.health.V2_0.IHealth.getCurrentAverageCallback
                        public final void onValues(int i2, int i3) {
                            HealthServiceWrapperHidl.lambda$getProperty$2(mutableInt, batteryProperty, i2, i3);
                        }
                    });
                    break;
                case 4:
                    iHealth.getCapacity(new IHealth.getCapacityCallback() { // from class: com.android.server.health.HealthServiceWrapperHidl$$ExternalSyntheticLambda3
                        @Override // android.hardware.health.V2_0.IHealth.getCapacityCallback
                        public final void onValues(int i2, int i3) {
                            HealthServiceWrapperHidl.lambda$getProperty$3(mutableInt, batteryProperty, i2, i3);
                        }
                    });
                    break;
                case 5:
                    iHealth.getEnergyCounter(new IHealth.getEnergyCounterCallback() { // from class: com.android.server.health.HealthServiceWrapperHidl$$ExternalSyntheticLambda5
                        @Override // android.hardware.health.V2_0.IHealth.getEnergyCounterCallback
                        public final void onValues(int i2, long j) {
                            HealthServiceWrapperHidl.lambda$getProperty$5(mutableInt, batteryProperty, i2, j);
                        }
                    });
                    break;
                case 6:
                    iHealth.getChargeStatus(new IHealth.getChargeStatusCallback() { // from class: com.android.server.health.HealthServiceWrapperHidl$$ExternalSyntheticLambda4
                        @Override // android.hardware.health.V2_0.IHealth.getChargeStatusCallback
                        public final void onValues(int i2, int i3) {
                            HealthServiceWrapperHidl.lambda$getProperty$4(mutableInt, batteryProperty, i2, i3);
                        }
                    });
                    break;
            }
            return mutableInt.value;
        } finally {
            traceEnd();
        }
    }

    public static /* synthetic */ void lambda$getProperty$0(MutableInt mutableInt, BatteryProperty batteryProperty, int i, int i2) {
        mutableInt.value = i;
        if (i == 0) {
            batteryProperty.setLong(i2);
        }
    }

    public static /* synthetic */ void lambda$getProperty$1(MutableInt mutableInt, BatteryProperty batteryProperty, int i, int i2) {
        mutableInt.value = i;
        if (i == 0) {
            batteryProperty.setLong(i2);
        }
    }

    public static /* synthetic */ void lambda$getProperty$2(MutableInt mutableInt, BatteryProperty batteryProperty, int i, int i2) {
        mutableInt.value = i;
        if (i == 0) {
            batteryProperty.setLong(i2);
        }
    }

    public static /* synthetic */ void lambda$getProperty$3(MutableInt mutableInt, BatteryProperty batteryProperty, int i, int i2) {
        mutableInt.value = i;
        if (i == 0) {
            batteryProperty.setLong(i2);
        }
    }

    public static /* synthetic */ void lambda$getProperty$4(MutableInt mutableInt, BatteryProperty batteryProperty, int i, int i2) {
        mutableInt.value = i;
        if (i == 0) {
            batteryProperty.setLong(i2);
        }
    }

    public static /* synthetic */ void lambda$getProperty$5(MutableInt mutableInt, BatteryProperty batteryProperty, int i, long j) {
        mutableInt.value = i;
        if (i == 0) {
            batteryProperty.setLong(j);
        }
    }

    @Override // com.android.server.health.HealthServiceWrapper
    public void scheduleUpdate() {
        getHandlerThread().getThreadHandler().post(new Runnable() { // from class: com.android.server.health.HealthServiceWrapperHidl$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                HealthServiceWrapperHidl.this.lambda$scheduleUpdate$6();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scheduleUpdate$6() {
        IHealth iHealth;
        traceBegin("HealthScheduleUpdate");
        try {
            try {
                iHealth = (IHealth) this.mLastService.get();
            } catch (RemoteException e) {
                Slog.e("HealthServiceWrapperHidl", "Cannot call update on health HAL", e);
            }
            if (iHealth == null) {
                Slog.e("HealthServiceWrapperHidl", "no health service");
            } else {
                iHealth.update();
            }
        } finally {
            traceEnd();
        }
    }

    @Override // com.android.server.health.HealthServiceWrapper
    public HealthInfo getHealthInfo() {
        IHealth iHealth = (IHealth) this.mLastService.get();
        if (iHealth == null) {
            return null;
        }
        final Mutable mutable = new Mutable();
        iHealth.getHealthInfo(new IHealth.getHealthInfoCallback() { // from class: com.android.server.health.HealthServiceWrapperHidl$$ExternalSyntheticLambda7
            @Override // android.hardware.health.V2_0.IHealth.getHealthInfoCallback
            public final void onValues(int i, android.hardware.health.V2_0.HealthInfo healthInfo) {
                HealthServiceWrapperHidl.lambda$getHealthInfo$7(HealthServiceWrapperHidl.Mutable.this, i, healthInfo);
            }
        });
        return (HealthInfo) mutable.value;
    }

    public static /* synthetic */ void lambda$getHealthInfo$7(Mutable mutable, int i, android.hardware.health.V2_0.HealthInfo healthInfo) {
        if (i == 0) {
            mutable.value = Translate.h2aTranslate(healthInfo.legacy);
        }
    }

    @Override // com.android.server.health.HealthServiceWrapper
    public void sehWriteEnableToParam(int i, boolean z, String str) {
        try {
            boolean z2 = true;
            ISehHealth service = ISehHealth.getService("default", true);
            if (service == null) {
                throw new RemoteException("no seh health service");
            }
            if (service.sehWriteEnableToParam(i, z) != 0) {
                z2 = false;
            }
            Slog.d("HealthServiceWrapperHidl", "sehWriteEnableToParam[" + str + "]: , result : " + z2 + ", offset: " + i + ", enable: " + z);
        } catch (Exception e) {
            Slog.e("HealthServiceWrapperHidl", "Exception in sehWriteEnableToParam[" + str + "]: , offset: " + i + ", enable: " + z);
            e.printStackTrace();
        }
    }

    public HealthServiceWrapperHidl(Callback callback, IServiceManagerSupplier iServiceManagerSupplier, IHealthSupplier iHealthSupplier) {
        IHealth iHealth;
        if (iServiceManagerSupplier == null) {
            throw null;
        }
        if (iHealthSupplier == null) {
            throw null;
        }
        this.mHealthSupplier = iHealthSupplier;
        traceBegin("HealthInitGetService_default");
        try {
            iHealth = iHealthSupplier.get("default");
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
        String str = this.mInstanceName;
        if (str == null || iHealth == null) {
            throw new NoSuchElementException(String.format("IHealth service instance %s isn't available. Perhaps no permission?", "default"));
        }
        if (callback != null) {
            this.mCallback = callback;
            callback.onRegistration(null, iHealth, str);
        }
        traceBegin("HealthInitRegisterNotification");
        this.mHandlerThread.start();
        try {
            iServiceManagerSupplier.get().registerForNotifications("android.hardware.health@2.0::IHealth", this.mInstanceName, this.mNotification);
            traceEnd();
            Slog.i("HealthServiceWrapperHidl", "health: HealthServiceWrapper listening to instance " + this.mInstanceName);
        } finally {
            traceEnd();
        }
    }

    @Override // com.android.server.health.HealthServiceWrapper
    public HandlerThread getHandlerThread() {
        return this.mHandlerThread;
    }

    public interface IServiceManagerSupplier {
        default IServiceManager get() {
            return IServiceManager.getService();
        }
    }

    public interface IHealthSupplier {
        default IHealth get(String str) {
            return IHealth.getService(str, true);
        }
    }

    public class Notification extends IServiceNotification.Stub {
        public Notification() {
        }

        @Override // android.hidl.manager.V1_0.IServiceNotification
        public final void onRegistration(String str, String str2, boolean z) {
            if ("android.hardware.health@2.0::IHealth".equals(str) && HealthServiceWrapperHidl.this.mInstanceName.equals(str2)) {
                HealthServiceWrapperHidl.this.mHandlerThread.getThreadHandler().post(new Runnable() { // from class: com.android.server.health.HealthServiceWrapperHidl.Notification.1
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            IHealth iHealth = HealthServiceWrapperHidl.this.mHealthSupplier.get(HealthServiceWrapperHidl.this.mInstanceName);
                            IHealth iHealth2 = (IHealth) HealthServiceWrapperHidl.this.mLastService.getAndSet(iHealth);
                            if (Objects.equals(iHealth, iHealth2)) {
                                return;
                            }
                            Slog.i("HealthServiceWrapperHidl", "health: new instance registered " + HealthServiceWrapperHidl.this.mInstanceName);
                            if (HealthServiceWrapperHidl.this.mCallback == null) {
                                return;
                            }
                            HealthServiceWrapperHidl.this.mCallback.onRegistration(iHealth2, iHealth, HealthServiceWrapperHidl.this.mInstanceName);
                        } catch (RemoteException | NoSuchElementException e) {
                            Slog.e("HealthServiceWrapperHidl", "health: Cannot get instance '" + HealthServiceWrapperHidl.this.mInstanceName + "': " + e.getMessage() + ". Perhaps no permission?");
                        }
                    }
                });
            }
        }
    }
}
