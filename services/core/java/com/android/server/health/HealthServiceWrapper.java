package com.android.server.health;

import android.hardware.health.HealthInfo;
import android.os.BatteryProperty;
import android.os.HandlerThread;
import android.os.RemoteException;

import com.android.server.BatteryService$$ExternalSyntheticLambda11;

import java.util.NoSuchElementException;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class HealthServiceWrapper {

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.health.HealthServiceWrapper$1, reason: invalid class name */
    public final class AnonymousClass1
            implements HealthServiceWrapperAidl.ServiceManagerStub,
                    HealthServiceWrapperHidl.IServiceManagerSupplier,
                    HealthServiceWrapperHidl.IHealthSupplier {}

    public static HealthServiceWrapper create(
            BatteryService$$ExternalSyntheticLambda11 batteryService$$ExternalSyntheticLambda11) {
        HealthHalCallbackHidl healthHalCallbackHidl = null;
        HealthRegCallbackAidl healthRegCallbackAidl =
                batteryService$$ExternalSyntheticLambda11 == null
                        ? null
                        : new HealthRegCallbackAidl(batteryService$$ExternalSyntheticLambda11);
        AnonymousClass1 anonymousClass1 = new AnonymousClass1();
        if (batteryService$$ExternalSyntheticLambda11 != null) {
            healthHalCallbackHidl = new HealthHalCallbackHidl();
            healthHalCallbackHidl.mCallback = batteryService$$ExternalSyntheticLambda11;
        }
        return create(
                healthRegCallbackAidl,
                anonymousClass1,
                healthHalCallbackHidl,
                new AnonymousClass1(),
                new AnonymousClass1());
    }

    public static HealthServiceWrapper create(
            HealthRegCallbackAidl healthRegCallbackAidl,
            HealthServiceWrapperAidl.ServiceManagerStub serviceManagerStub,
            HealthServiceWrapperHidl.Callback callback,
            HealthServiceWrapperHidl.IServiceManagerSupplier iServiceManagerSupplier,
            HealthServiceWrapperHidl.IHealthSupplier iHealthSupplier)
            throws RemoteException, NoSuchElementException {
        try {
            return new HealthServiceWrapperAidl(healthRegCallbackAidl, serviceManagerStub);
        } catch (NoSuchElementException unused) {
            return new HealthServiceWrapperHidl(callback, iServiceManagerSupplier, iHealthSupplier);
        }
    }

    public abstract HandlerThread getHandlerThread();

    public abstract HealthInfo getHealthInfo();

    public abstract int getProperty(int i, BatteryProperty batteryProperty);

    public abstract void scheduleUpdate();

    public abstract void sehWriteEnableToParam(int i, String str, boolean z);
}
