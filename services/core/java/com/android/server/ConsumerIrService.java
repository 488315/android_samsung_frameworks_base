package com.android.server;

import android.content.Context;
import android.hardware.IConsumerIrService;
import android.hardware.ir.ConsumerIrFreqRange;
import android.hardware.ir.IConsumerIr;
import android.hardware.ir.IConsumerIr$Stub$Proxy;
import android.os.IBinder;
import android.os.IInterface;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Slog;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public class ConsumerIrService extends IConsumerIrService.Stub {
    public final IConsumerIr mAidlService;
    public final Object mHalLock = new Object();
    public final boolean mHasNativeHal;

    public ConsumerIrService(Context context) {
        IConsumerIr iConsumerIr = null;
        this.mAidlService = null;
        ((PowerManager) context.getSystemService("power"))
                .newWakeLock(1, "ConsumerIrService")
                .setReferenceCounted(true);
        StringBuilder sb = new StringBuilder();
        String str = IConsumerIr.DESCRIPTOR;
        sb.append(str);
        sb.append("/default");
        IBinder waitForDeclaredService = ServiceManager.waitForDeclaredService(sb.toString());
        if (waitForDeclaredService != null) {
            IInterface queryLocalInterface = waitForDeclaredService.queryLocalInterface(str);
            if (queryLocalInterface == null || !(queryLocalInterface instanceof IConsumerIr)) {
                IConsumerIr$Stub$Proxy iConsumerIr$Stub$Proxy = new IConsumerIr$Stub$Proxy();
                iConsumerIr$Stub$Proxy.mRemote = waitForDeclaredService;
                iConsumerIr = iConsumerIr$Stub$Proxy;
            } else {
                iConsumerIr = (IConsumerIr) queryLocalInterface;
            }
        }
        this.mAidlService = iConsumerIr;
        boolean hidlHalService = iConsumerIr == null ? getHidlHalService() : true;
        this.mHasNativeHal = hidlHalService;
        if (context.getPackageManager().hasSystemFeature("android.hardware.consumerir")) {
            if (!hidlHalService) {
                throw new RuntimeException("FEATURE_CONSUMER_IR present, but no IR HAL loaded!");
            }
        } else if (hidlHalService) {
            throw new RuntimeException("IR HAL present, but FEATURE_CONSUMER_IR is not set!");
        }
    }

    private static native boolean getHidlHalService();

    private static native int[] halGetCarrierFrequencies();

    private static native int halTransmit(int i, int[] iArr);

    public final int[] getCarrierFrequencies() {
        super.getCarrierFrequencies_enforcePermission();
        if (!this.mHasNativeHal) {
            throw new UnsupportedOperationException("IR emitter not available");
        }
        synchronized (this.mHalLock) {
            try {
                IConsumerIr iConsumerIr = this.mAidlService;
                if (iConsumerIr == null) {
                    return halGetCarrierFrequencies();
                }
                try {
                    ConsumerIrFreqRange[] carrierFreqs =
                            ((IConsumerIr$Stub$Proxy) iConsumerIr).getCarrierFreqs();
                    if (carrierFreqs.length <= 0) {
                        Slog.e("ConsumerIrService", "Error getting carrier frequencies.");
                    }
                    int[] iArr = new int[carrierFreqs.length * 2];
                    for (int i = 0; i < carrierFreqs.length; i++) {
                        int i2 = i * 2;
                        ConsumerIrFreqRange consumerIrFreqRange = carrierFreqs[i];
                        iArr[i2] = consumerIrFreqRange.minHz;
                        iArr[i2 + 1] = consumerIrFreqRange.maxHz;
                    }
                    return iArr;
                } catch (RemoteException unused) {
                    return null;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean hasIrEmitter() {
        return this.mHasNativeHal;
    }

    public final void transmit(String str, int i, int[] iArr) {
        super.transmit_enforcePermission();
        long j = 0;
        for (int i2 : iArr) {
            if (i2 <= 0) {
                throw new IllegalArgumentException("Non-positive IR slice");
            }
            j += i2;
        }
        if (j > 2000000) {
            throw new IllegalArgumentException("IR pattern too long");
        }
        if (!this.mHasNativeHal) {
            throw new UnsupportedOperationException("IR emitter not available");
        }
        synchronized (this.mHalLock) {
            try {
                IConsumerIr iConsumerIr = this.mAidlService;
                if (iConsumerIr != null) {
                    try {
                        ((IConsumerIr$Stub$Proxy) iConsumerIr).transmit(i, iArr);
                    } catch (RemoteException unused) {
                        Slog.e("ConsumerIrService", "Error transmitting frequency: " + i);
                    }
                } else {
                    int halTransmit = halTransmit(i, iArr);
                    if (halTransmit < 0) {
                        Slog.e("ConsumerIrService", "Error transmitting: " + halTransmit);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
