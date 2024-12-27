package com.android.server.biometrics.sensors;

import android.content.Context;
import android.hardware.biometrics.common.OperationContext;
import android.os.IBinder;

import com.android.server.biometrics.log.BiometricContext;
import com.android.server.biometrics.log.BiometricContextProvider;
import com.android.server.biometrics.log.BiometricLogger;
import com.android.server.biometrics.log.OperationContextExt;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class HalClientMonitor extends BaseClientMonitor {
    public final Supplier mLazyDaemon;
    public final OperationContextExt mOperationContext;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.biometrics.sensors.HalClientMonitor$1, reason: invalid class name */
    public final class AnonymousClass1 implements ClientMonitorCallback {
        public AnonymousClass1() {}

        @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
        public final void onClientFinished(BaseClientMonitor baseClientMonitor, boolean z) {
            HalClientMonitor.this.unsubscribeBiometricContext();
        }
    }

    public HalClientMonitor(
            Context context,
            Supplier supplier,
            IBinder iBinder,
            ClientMonitorCallbackConverter clientMonitorCallbackConverter,
            int i,
            String str,
            int i2,
            int i3,
            BiometricLogger biometricLogger,
            BiometricContext biometricContext,
            boolean z) {
        super(
                context,
                iBinder,
                clientMonitorCallbackConverter,
                i,
                str,
                i2,
                i3,
                biometricLogger,
                biometricContext);
        this.mLazyDaemon = supplier;
        int i4 = 0;
        if (clientMonitorCallbackConverter != null) {
            if (clientMonitorCallbackConverter.mFaceServiceReceiver != null) {
                i4 = 8;
            } else if (clientMonitorCallbackConverter.mFingerprintServiceReceiver != null) {
                i4 = 2;
            }
        }
        this.mOperationContext =
                new OperationContextExt(new OperationContext(), isBiometricPrompt(), i4, z);
    }

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    public void destroy() {
        super.destroy();
        unsubscribeBiometricContext();
    }

    public final OperationContextExt getOperationContext() {
        BiometricContext biometricContext = this.mBiometricContext;
        OperationContextExt operationContextExt = this.mOperationContext;
        boolean isCryptoOperation = isCryptoOperation();
        BiometricContextProvider biometricContextProvider =
                (BiometricContextProvider) biometricContext;
        biometricContextProvider.getClass();
        operationContextExt.update(biometricContextProvider, isCryptoOperation);
        return operationContextExt;
    }

    public final boolean isBiometricPrompt() {
        return this.mCookie != 0;
    }

    public abstract void startHalOperation();

    public abstract void unableToStart();

    public final void unsubscribeBiometricContext() {
        BiometricContext biometricContext = this.mBiometricContext;
        ((ConcurrentHashMap) ((BiometricContextProvider) biometricContext).mSubscribers)
                .remove(this.mOperationContext);
    }
}
