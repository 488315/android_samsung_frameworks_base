package android.telephony.ims.compat.stub;

import android.os.Bundle;
import android.os.RemoteException;
import android.telephony.ims.ImsCallForwardInfo;
import android.telephony.ims.ImsReasonInfo;
import android.telephony.ims.ImsSsData;
import android.telephony.ims.ImsSsInfo;
import com.android.ims.internal.IImsUt;
import com.android.ims.internal.IImsUtListener;

/* loaded from: classes4.dex */
public class ImsUtListenerImplBase extends IImsUtListener.Stub {
    @Override // com.android.ims.internal.IImsUtListener
    public void lineIdentificationSupplementaryServiceResponse(int i, ImsSsInfo imsSsInfo) {
    }

    @Override // com.android.ims.internal.IImsUtListener
    public void onSupplementaryServiceIndication(ImsSsData imsSsData) {
    }

    @Override // com.android.ims.internal.IImsUtListener
    public void utConfigurationCallBarringQueried(IImsUt iImsUt, int i, ImsSsInfo[] imsSsInfoArr) throws RemoteException {
    }

    @Override // com.android.ims.internal.IImsUtListener
    public void utConfigurationCallForwardQueried(IImsUt iImsUt, int i, ImsCallForwardInfo[] imsCallForwardInfoArr) throws RemoteException {
    }

    @Override // com.android.ims.internal.IImsUtListener
    public void utConfigurationCallWaitingQueried(IImsUt iImsUt, int i, ImsSsInfo[] imsSsInfoArr) throws RemoteException {
    }

    @Override // com.android.ims.internal.IImsUtListener
    public void utConfigurationQueried(IImsUt iImsUt, int i, Bundle bundle) throws RemoteException {
    }

    @Override // com.android.ims.internal.IImsUtListener
    public void utConfigurationQueryFailed(IImsUt iImsUt, int i, ImsReasonInfo imsReasonInfo) throws RemoteException {
    }

    @Override // com.android.ims.internal.IImsUtListener
    public void utConfigurationUpdateFailed(IImsUt iImsUt, int i, ImsReasonInfo imsReasonInfo) throws RemoteException {
    }

    @Override // com.android.ims.internal.IImsUtListener
    public void utConfigurationUpdated(IImsUt iImsUt, int i) throws RemoteException {
    }
}
