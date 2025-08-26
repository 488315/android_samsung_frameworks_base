package android.telephony.ims.compat;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.ims.compat.feature.ImsFeature;
import android.telephony.ims.compat.feature.MMTelFeature;
import android.telephony.ims.compat.feature.RcsFeature;
import android.util.Log;
import android.util.SparseArray;
import com.android.ims.internal.IImsFeatureStatusCallback;
import com.android.ims.internal.IImsMMTelFeature;
import com.android.ims.internal.IImsRcsFeature;
import com.android.ims.internal.IImsServiceController;

/* loaded from: classes4.dex */
public class ImsService extends Service {
    private static final String LOG_TAG = "ImsService(Compat)";
    public static final String SERVICE_INTERFACE = "android.telephony.ims.compat.ImsService";
    private final SparseArray<SparseArray<ImsFeature>> mFeaturesBySlot = new SparseArray<>();
    protected final IBinder mImsServiceController = new IImsServiceController.Stub() { // from class: android.telephony.ims.compat.ImsService.1
        @Override // com.android.ims.internal.IImsServiceController
        public IImsMMTelFeature createEmergencyMMTelFeature(int i) {
            return ImsService.this.createEmergencyMMTelFeatureInternal(i);
        }

        @Override // com.android.ims.internal.IImsServiceController
        public IImsMMTelFeature createMMTelFeature(int i) {
            return ImsService.this.createMMTelFeatureInternal(i);
        }

        @Override // com.android.ims.internal.IImsServiceController
        public IImsRcsFeature createRcsFeature(int i) {
            return ImsService.this.createRcsFeatureInternal(i);
        }

        @Override // com.android.ims.internal.IImsServiceController
        public void removeImsFeature(int i, int i2) {
            ImsService.this.removeImsFeature(i, i2);
        }

        @Override // com.android.ims.internal.IImsServiceController
        public void addFeatureStatusCallback(int i, int i2, IImsFeatureStatusCallback iImsFeatureStatusCallback) {
            ImsService.this.addImsFeatureStatusCallback(i, i2, iImsFeatureStatusCallback);
        }

        @Override // com.android.ims.internal.IImsServiceController
        public void removeFeatureStatusCallback(int i, int i2, IImsFeatureStatusCallback iImsFeatureStatusCallback) {
            ImsService.this.removeImsFeatureStatusCallback(i, i2, iImsFeatureStatusCallback);
        }
    };

    public MMTelFeature onCreateEmergencyMMTelImsFeature(int i) {
        return null;
    }

    public MMTelFeature onCreateMMTelImsFeature(int i) {
        return null;
    }

    public RcsFeature onCreateRcsFeature(int i) {
        return null;
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        if (!SERVICE_INTERFACE.equals(intent.getAction())) {
            return null;
        }
        Log.i(LOG_TAG, "ImsService(Compat) Bound.");
        return this.mImsServiceController;
    }

    public SparseArray<ImsFeature> getFeatures(int i) {
        return this.mFeaturesBySlot.get(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public IImsMMTelFeature createEmergencyMMTelFeatureInternal(int i) {
        MMTelFeature mMTelFeatureOnCreateEmergencyMMTelImsFeature = onCreateEmergencyMMTelImsFeature(i);
        if (mMTelFeatureOnCreateEmergencyMMTelImsFeature == null) {
            return null;
        }
        setupFeature(mMTelFeatureOnCreateEmergencyMMTelImsFeature, i, 0);
        return mMTelFeatureOnCreateEmergencyMMTelImsFeature.getBinder();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public IImsMMTelFeature createMMTelFeatureInternal(int i) {
        MMTelFeature mMTelFeatureOnCreateMMTelImsFeature = onCreateMMTelImsFeature(i);
        if (mMTelFeatureOnCreateMMTelImsFeature == null) {
            return null;
        }
        setupFeature(mMTelFeatureOnCreateMMTelImsFeature, i, 1);
        return mMTelFeatureOnCreateMMTelImsFeature.getBinder();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public IImsRcsFeature createRcsFeatureInternal(int i) {
        RcsFeature rcsFeatureOnCreateRcsFeature = onCreateRcsFeature(i);
        if (rcsFeatureOnCreateRcsFeature == null) {
            return null;
        }
        setupFeature(rcsFeatureOnCreateRcsFeature, i, 2);
        return rcsFeatureOnCreateRcsFeature.getBinder();
    }

    private void setupFeature(ImsFeature imsFeature, int i, int i2) {
        imsFeature.setContext(this);
        imsFeature.setSlotId(i);
        addImsFeature(i, i2, imsFeature);
        imsFeature.onFeatureReady();
    }

    private void addImsFeature(int i, int i2, ImsFeature imsFeature) {
        synchronized (this.mFeaturesBySlot) {
            SparseArray<ImsFeature> sparseArray = this.mFeaturesBySlot.get(i);
            if (sparseArray == null) {
                sparseArray = new SparseArray<>();
                this.mFeaturesBySlot.put(i, sparseArray);
            }
            sparseArray.put(i2, imsFeature);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addImsFeatureStatusCallback(int i, int i2, IImsFeatureStatusCallback iImsFeatureStatusCallback) {
        synchronized (this.mFeaturesBySlot) {
            SparseArray<ImsFeature> sparseArray = this.mFeaturesBySlot.get(i);
            if (sparseArray == null) {
                Log.w(LOG_TAG, "Can not add ImsFeatureStatusCallback. No ImsFeatures exist on slot " + i);
            } else {
                ImsFeature imsFeature = sparseArray.get(i2);
                if (imsFeature != null) {
                    imsFeature.addImsFeatureStatusCallback(iImsFeatureStatusCallback);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeImsFeatureStatusCallback(int i, int i2, IImsFeatureStatusCallback iImsFeatureStatusCallback) {
        synchronized (this.mFeaturesBySlot) {
            SparseArray<ImsFeature> sparseArray = this.mFeaturesBySlot.get(i);
            if (sparseArray == null) {
                Log.w(LOG_TAG, "Can not remove ImsFeatureStatusCallback. No ImsFeatures exist on slot " + i);
            } else {
                ImsFeature imsFeature = sparseArray.get(i2);
                if (imsFeature != null) {
                    imsFeature.removeImsFeatureStatusCallback(iImsFeatureStatusCallback);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeImsFeature(int i, int i2) {
        synchronized (this.mFeaturesBySlot) {
            SparseArray<ImsFeature> sparseArray = this.mFeaturesBySlot.get(i);
            if (sparseArray == null) {
                Log.w(LOG_TAG, "Can not remove ImsFeature. No ImsFeatures exist on slot " + i);
                return;
            }
            ImsFeature imsFeature = sparseArray.get(i2);
            if (imsFeature == null) {
                Log.w(LOG_TAG, "Can not remove ImsFeature. No feature with type " + i2 + " exists on slot " + i);
                return;
            }
            imsFeature.onFeatureRemoved();
            sparseArray.remove(i2);
        }
    }
}
