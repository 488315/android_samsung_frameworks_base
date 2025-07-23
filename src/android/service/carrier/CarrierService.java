package android.service.carrier;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PersistableBundle;
import android.os.ResultReceiver;
import android.service.carrier.ICarrierService;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyRegistryManager;
import android.util.Log;
import java.io.FileDescriptor;
import java.io.PrintWriter;

/* loaded from: classes3.dex */
public abstract class CarrierService extends Service {
    public static final String CARRIER_SERVICE_INTERFACE = "android.service.carrier.CarrierService";
    private static final String LOG_TAG = "CarrierService";
    private final ICarrierService.Stub mStubWrapper = new ICarrierServiceWrapper();

    @Deprecated
    public abstract PersistableBundle onLoadConfig(CarrierIdentifier carrierIdentifier);

    public PersistableBundle onLoadConfig(int i, CarrierIdentifier carrierIdentifier) {
        return onLoadConfig(carrierIdentifier);
    }

    @Deprecated
    public final void notifyCarrierNetworkChange(boolean z) {
        TelephonyRegistryManager telephonyRegistryManager = (TelephonyRegistryManager) getSystemService(Context.TELEPHONY_REGISTRY_SERVICE);
        if (telephonyRegistryManager != null) {
            telephonyRegistryManager.notifyCarrierNetworkChange(z);
        }
    }

    public final void notifyCarrierNetworkChange(int i, boolean z) {
        TelephonyRegistryManager telephonyRegistryManager = (TelephonyRegistryManager) getSystemService(TelephonyRegistryManager.class);
        if (telephonyRegistryManager != null) {
            telephonyRegistryManager.notifyCarrierNetworkChange(i, z);
        }
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return this.mStubWrapper;
    }

    public class ICarrierServiceWrapper extends ICarrierService.Stub {
        public static final String KEY_CONFIG_BUNDLE = "config_bundle";
        public static final int RESULT_ERROR = 1;
        public static final int RESULT_OK = 0;

        public ICarrierServiceWrapper() {
        }

        @Override // android.service.carrier.ICarrierService
        public void getCarrierConfig(int i, CarrierIdentifier carrierIdentifier, ResultReceiver resultReceiver) {
            try {
                int subscriptionId = SubscriptionManager.getSubscriptionId(i);
                Bundle bundle = new Bundle();
                bundle.putParcelable(KEY_CONFIG_BUNDLE, CarrierService.this.onLoadConfig(subscriptionId, carrierIdentifier));
                resultReceiver.send(0, bundle);
            } catch (Exception e) {
                Log.e(CarrierService.LOG_TAG, "Error in onLoadConfig: " + e.getMessage(), e);
                resultReceiver.send(1, null);
            }
        }

        @Override // android.os.Binder
        protected void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            CarrierService.this.dump(fileDescriptor, printWriter, strArr);
        }
    }
}
