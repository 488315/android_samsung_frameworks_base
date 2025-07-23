package android.hardware;

import android.content.Context;
import android.hardware.IConsumerIrService;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;

/* loaded from: classes2.dex */
public final class ConsumerIrManager {
    private static final String TAG = "ConsumerIr";
    private final String mPackageName;
    private final IConsumerIrService mService = IConsumerIrService.Stub.asInterface(ServiceManager.getServiceOrThrow(Context.CONSUMER_IR_SERVICE));

    public ConsumerIrManager(Context context) throws ServiceManager.ServiceNotFoundException {
        this.mPackageName = context.getPackageName();
    }

    public boolean hasIrEmitter() {
        IConsumerIrService iConsumerIrService = this.mService;
        if (iConsumerIrService == null) {
            Log.w(TAG, "no consumer ir service.");
            return false;
        }
        try {
            return iConsumerIrService.hasIrEmitter();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void transmit(int i, int[] iArr) {
        IConsumerIrService iConsumerIrService = this.mService;
        if (iConsumerIrService == null) {
            Log.w(TAG, "failed to transmit; no consumer ir service.");
            return;
        }
        try {
            iConsumerIrService.transmit(this.mPackageName, i, iArr);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public final class CarrierFrequencyRange {
        private final int mMaxFrequency;
        private final int mMinFrequency;

        public CarrierFrequencyRange(ConsumerIrManager consumerIrManager, int i, int i2) {
            this.mMinFrequency = i;
            this.mMaxFrequency = i2;
        }

        public int getMinFrequency() {
            return this.mMinFrequency;
        }

        public int getMaxFrequency() {
            return this.mMaxFrequency;
        }
    }

    public CarrierFrequencyRange[] getCarrierFrequencies() {
        IConsumerIrService iConsumerIrService = this.mService;
        if (iConsumerIrService == null) {
            Log.w(TAG, "no consumer ir service.");
            return null;
        }
        try {
            int[] carrierFrequencies = iConsumerIrService.getCarrierFrequencies();
            if (carrierFrequencies.length % 2 != 0) {
                Log.w(TAG, "consumer ir service returned an uneven number of frequencies.");
                return null;
            }
            CarrierFrequencyRange[] carrierFrequencyRangeArr = new CarrierFrequencyRange[carrierFrequencies.length / 2];
            for (int i = 0; i < carrierFrequencies.length; i += 2) {
                carrierFrequencyRangeArr[i / 2] = new CarrierFrequencyRange(this, carrierFrequencies[i], carrierFrequencies[i + 1]);
            }
            return carrierFrequencyRangeArr;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
