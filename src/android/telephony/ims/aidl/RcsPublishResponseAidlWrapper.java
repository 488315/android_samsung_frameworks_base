package android.telephony.ims.aidl;

import android.os.RemoteException;
import android.telephony.ims.ImsException;
import android.telephony.ims.SipDetails;
import android.telephony.ims.stub.RcsCapabilityExchangeImplBase;

/* loaded from: classes4.dex */
public class RcsPublishResponseAidlWrapper implements RcsCapabilityExchangeImplBase.PublishResponseCallback {
    private final IPublishResponseCallback mResponseBinder;

    public RcsPublishResponseAidlWrapper(IPublishResponseCallback iPublishResponseCallback) {
        this.mResponseBinder = iPublishResponseCallback;
    }

    @Override // android.telephony.ims.stub.RcsCapabilityExchangeImplBase.PublishResponseCallback
    public void onCommandError(int i) throws ImsException {
        try {
            this.mResponseBinder.onCommandError(i);
        } catch (RemoteException e) {
            throw new ImsException(e.getMessage(), 1);
        }
    }

    @Override // android.telephony.ims.stub.RcsCapabilityExchangeImplBase.PublishResponseCallback
    @Deprecated
    public void onNetworkResponse(int i, String str) throws ImsException {
        try {
            this.mResponseBinder.onNetworkResponse(new SipDetails.Builder(2).setSipResponseCode(i, str).build());
        } catch (RemoteException e) {
            throw new ImsException(e.getMessage(), 1);
        }
    }

    @Override // android.telephony.ims.stub.RcsCapabilityExchangeImplBase.PublishResponseCallback
    @Deprecated
    public void onNetworkResponse(int i, String str, int i2, String str2) throws ImsException {
        try {
            this.mResponseBinder.onNetworkResponse(new SipDetails.Builder(2).setSipResponseCode(i, str).setSipResponseReasonHeader(i2, str2).build());
        } catch (RemoteException e) {
            throw new ImsException(e.getMessage(), 1);
        }
    }

    @Override // android.telephony.ims.stub.RcsCapabilityExchangeImplBase.PublishResponseCallback
    public void onNetworkResponse(SipDetails sipDetails) throws ImsException {
        try {
            this.mResponseBinder.onNetworkResponse(sipDetails);
        } catch (RemoteException e) {
            throw new ImsException(e.getMessage(), 1);
        }
    }
}
