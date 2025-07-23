package android.telephony.ims;

import android.annotation.SystemApi;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import com.android.ims.internal.IImsUtListener;

@SystemApi
/* loaded from: classes4.dex */
public class ImsUtListener {

    @Deprecated
    public static final String BUNDLE_KEY_CLIR = "queryClir";

    @Deprecated
    public static final String BUNDLE_KEY_SSINFO = "imsSsInfo";
    private static final String LOG_TAG = "ImsUtListener";
    private IImsUtListener mServiceInterface;

    public void onUtConfigurationUpdated(int i) {
        try {
            this.mServiceInterface.utConfigurationUpdated(null, i);
        } catch (RemoteException unused) {
            Log.w(LOG_TAG, "utConfigurationUpdated: remote exception");
        }
    }

    public void onUtConfigurationUpdateFailed(int i, ImsReasonInfo imsReasonInfo) {
        try {
            this.mServiceInterface.utConfigurationUpdateFailed(null, i, imsReasonInfo);
        } catch (RemoteException unused) {
            Log.w(LOG_TAG, "utConfigurationUpdateFailed: remote exception");
        }
    }

    @Deprecated
    public void onUtConfigurationQueried(int i, Bundle bundle) {
        try {
            this.mServiceInterface.utConfigurationQueried(null, i, bundle);
        } catch (RemoteException unused) {
            Log.w(LOG_TAG, "utConfigurationQueried: remote exception");
        }
    }

    public void onLineIdentificationSupplementaryServiceResponse(int i, ImsSsInfo imsSsInfo) {
        try {
            this.mServiceInterface.lineIdentificationSupplementaryServiceResponse(i, imsSsInfo);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void onUtConfigurationQueryFailed(int i, ImsReasonInfo imsReasonInfo) {
        try {
            this.mServiceInterface.utConfigurationQueryFailed(null, i, imsReasonInfo);
        } catch (RemoteException unused) {
            Log.w(LOG_TAG, "utConfigurationQueryFailed: remote exception");
        }
    }

    public void onUtConfigurationCallBarringQueried(int i, ImsSsInfo[] imsSsInfoArr) {
        try {
            this.mServiceInterface.utConfigurationCallBarringQueried(null, i, imsSsInfoArr);
        } catch (RemoteException unused) {
            Log.w(LOG_TAG, "utConfigurationCallBarringQueried: remote exception");
        }
    }

    public void onUtConfigurationCallForwardQueried(int i, ImsCallForwardInfo[] imsCallForwardInfoArr) {
        try {
            this.mServiceInterface.utConfigurationCallForwardQueried(null, i, imsCallForwardInfoArr);
        } catch (RemoteException unused) {
            Log.w(LOG_TAG, "utConfigurationCallForwardQueried: remote exception");
        }
    }

    public void onUtConfigurationCallWaitingQueried(int i, ImsSsInfo[] imsSsInfoArr) {
        try {
            this.mServiceInterface.utConfigurationCallWaitingQueried(null, i, imsSsInfoArr);
        } catch (RemoteException unused) {
            Log.w(LOG_TAG, "utConfigurationCallWaitingQueried: remote exception");
        }
    }

    public void onSupplementaryServiceIndication(ImsSsData imsSsData) {
        try {
            this.mServiceInterface.onSupplementaryServiceIndication(imsSsData);
        } catch (RemoteException unused) {
            Log.w(LOG_TAG, "onSupplementaryServiceIndication: remote exception");
        }
    }

    public ImsUtListener(IImsUtListener iImsUtListener) {
        this.mServiceInterface = iImsUtListener;
    }

    public IImsUtListener getListenerInterface() {
        return this.mServiceInterface;
    }
}
