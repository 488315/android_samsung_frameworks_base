package android.telephony.ims.compat.stub;

import android.content.Context;
import android.content.Intent;
import android.os.RemoteException;
import android.util.Log;
import com.android.ims.ImsConfig;
import com.android.ims.ImsConfigListener;
import com.android.ims.internal.IImsConfig;
import java.lang.ref.WeakReference;
import java.util.HashMap;

/* loaded from: classes4.dex */
public class ImsConfigImplBase {
    private static final String TAG = "ImsConfigImplBase";
    ImsConfigStub mImsConfigStub;

    public void getFeatureValue(int i, int i2, ImsConfigListener imsConfigListener) throws RemoteException {
    }

    public String getProvisionedStringValue(int i) throws RemoteException {
        return null;
    }

    public int getProvisionedValue(int i) throws RemoteException {
        return -1;
    }

    public void getVideoQuality(ImsConfigListener imsConfigListener) throws RemoteException {
    }

    public boolean getVolteProvisioned() throws RemoteException {
        return false;
    }

    public void setFeatureValue(int i, int i2, int i3, ImsConfigListener imsConfigListener) throws RemoteException {
    }

    public int setProvisionedStringValue(int i, String str) throws RemoteException {
        return 1;
    }

    public int setProvisionedValue(int i, int i2) throws RemoteException {
        return 1;
    }

    public void setVideoQuality(int i, ImsConfigListener imsConfigListener) throws RemoteException {
    }

    public ImsConfigImplBase(Context context) {
        this.mImsConfigStub = new ImsConfigStub(this, context);
    }

    public IImsConfig getIImsConfig() {
        return this.mImsConfigStub;
    }

    public final void notifyProvisionedValueChanged(int i, int i2) {
        this.mImsConfigStub.updateCachedValue(i, i2, true);
    }

    public final void notifyProvisionedValueChanged(int i, String str) {
        this.mImsConfigStub.updateCachedValue(i, str, true);
    }

    public static class ImsConfigStub extends IImsConfig.Stub {
        Context mContext;
        WeakReference<ImsConfigImplBase> mImsConfigImplBaseWeakReference;
        private HashMap<Integer, Integer> mProvisionedIntValue = new HashMap<>();
        private HashMap<Integer, String> mProvisionedStringValue = new HashMap<>();

        public ImsConfigStub(ImsConfigImplBase imsConfigImplBase, Context context) {
            this.mContext = context;
            this.mImsConfigImplBaseWeakReference = new WeakReference<>(imsConfigImplBase);
        }

        @Override // com.android.ims.internal.IImsConfig
        public synchronized int getProvisionedValue(int i) throws RemoteException {
            if (this.mProvisionedIntValue.containsKey(Integer.valueOf(i))) {
                return this.mProvisionedIntValue.get(Integer.valueOf(i)).intValue();
            }
            int provisionedValue = getImsConfigImpl().getProvisionedValue(i);
            if (provisionedValue != -1) {
                updateCachedValue(i, provisionedValue, false);
            }
            return provisionedValue;
        }

        @Override // com.android.ims.internal.IImsConfig
        public synchronized String getProvisionedStringValue(int i) throws RemoteException {
            if (this.mProvisionedIntValue.containsKey(Integer.valueOf(i))) {
                return this.mProvisionedStringValue.get(Integer.valueOf(i));
            }
            String provisionedStringValue = getImsConfigImpl().getProvisionedStringValue(i);
            if (provisionedStringValue != null) {
                updateCachedValue(i, provisionedStringValue, false);
            }
            return provisionedStringValue;
        }

        @Override // com.android.ims.internal.IImsConfig
        public synchronized int setProvisionedValue(int i, int i2) throws RemoteException {
            int provisionedValue;
            this.mProvisionedIntValue.remove(Integer.valueOf(i));
            provisionedValue = getImsConfigImpl().setProvisionedValue(i, i2);
            if (provisionedValue == 0) {
                updateCachedValue(i, i2, true);
            } else {
                Log.d(ImsConfigImplBase.TAG, "Set provision value of " + i + " to " + i2 + " failed with error code " + provisionedValue);
            }
            return provisionedValue;
        }

        @Override // com.android.ims.internal.IImsConfig
        public synchronized int setProvisionedStringValue(int i, String str) throws RemoteException {
            int provisionedStringValue;
            this.mProvisionedStringValue.remove(Integer.valueOf(i));
            provisionedStringValue = getImsConfigImpl().setProvisionedStringValue(i, str);
            if (provisionedStringValue == 0) {
                updateCachedValue(i, str, true);
            }
            return provisionedStringValue;
        }

        @Override // com.android.ims.internal.IImsConfig
        public void getFeatureValue(int i, int i2, ImsConfigListener imsConfigListener) throws RemoteException {
            getImsConfigImpl().getFeatureValue(i, i2, imsConfigListener);
        }

        @Override // com.android.ims.internal.IImsConfig
        public void setFeatureValue(int i, int i2, int i3, ImsConfigListener imsConfigListener) throws RemoteException {
            getImsConfigImpl().setFeatureValue(i, i2, i3, imsConfigListener);
        }

        @Override // com.android.ims.internal.IImsConfig
        public boolean getVolteProvisioned() throws RemoteException {
            return getImsConfigImpl().getVolteProvisioned();
        }

        @Override // com.android.ims.internal.IImsConfig
        public void getVideoQuality(ImsConfigListener imsConfigListener) throws RemoteException {
            getImsConfigImpl().getVideoQuality(imsConfigListener);
        }

        @Override // com.android.ims.internal.IImsConfig
        public void setVideoQuality(int i, ImsConfigListener imsConfigListener) throws RemoteException {
            getImsConfigImpl().setVideoQuality(i, imsConfigListener);
        }

        private ImsConfigImplBase getImsConfigImpl() throws RemoteException {
            ImsConfigImplBase imsConfigImplBase = this.mImsConfigImplBaseWeakReference.get();
            if (imsConfigImplBase != null) {
                return imsConfigImplBase;
            }
            throw new RemoteException("Fail to get ImsConfigImpl");
        }

        private void sendImsConfigChangedIntent(int i, int i2) {
            sendImsConfigChangedIntent(i, Integer.toString(i2));
        }

        private void sendImsConfigChangedIntent(int i, String str) {
            Intent intent = new Intent(ImsConfig.ACTION_IMS_CONFIG_CHANGED);
            intent.putExtra(ImsConfig.EXTRA_CHANGED_ITEM, i);
            intent.putExtra("value", str);
            Context context = this.mContext;
            if (context != null) {
                context.sendBroadcast(intent);
            }
        }

        protected synchronized void updateCachedValue(int i, int i2, boolean z) {
            this.mProvisionedIntValue.put(Integer.valueOf(i), Integer.valueOf(i2));
            if (z) {
                sendImsConfigChangedIntent(i, i2);
            }
        }

        protected synchronized void updateCachedValue(int i, String str, boolean z) {
            this.mProvisionedStringValue.put(Integer.valueOf(i), str);
            if (z) {
                sendImsConfigChangedIntent(i, str);
            }
        }
    }
}
