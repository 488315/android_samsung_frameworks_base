package android.telephony.ims.aidl;

import android.net.Uri;
import android.os.RemoteException;
import android.telephony.ims.ImsException;
import android.telephony.ims.RcsContactTerminatedReason;
import android.telephony.ims.SipDetails;
import android.telephony.ims.stub.RcsCapabilityExchangeImplBase;
import android.util.Pair;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class RcsSubscribeResponseAidlWrapper implements RcsCapabilityExchangeImplBase.SubscribeResponseCallback {
    private final ISubscribeResponseCallback mResponseBinder;

    public RcsSubscribeResponseAidlWrapper(ISubscribeResponseCallback iSubscribeResponseCallback) {
        this.mResponseBinder = iSubscribeResponseCallback;
    }

    @Override // android.telephony.ims.stub.RcsCapabilityExchangeImplBase.SubscribeResponseCallback
    public void onCommandError(int i) throws ImsException {
        try {
            this.mResponseBinder.onCommandError(i);
        } catch (RemoteException e) {
            throw new ImsException(e.getMessage(), 1);
        }
    }

    @Override // android.telephony.ims.stub.RcsCapabilityExchangeImplBase.SubscribeResponseCallback
    @Deprecated
    public void onNetworkResponse(int i, String str) throws ImsException {
        try {
            this.mResponseBinder.onNetworkResponse(new SipDetails.Builder(3).setSipResponseCode(i, str).build());
        } catch (RemoteException e) {
            throw new ImsException(e.getMessage(), 1);
        }
    }

    @Override // android.telephony.ims.stub.RcsCapabilityExchangeImplBase.SubscribeResponseCallback
    @Deprecated
    public void onNetworkResponse(int i, String str, int i2, String str2) throws ImsException {
        try {
            this.mResponseBinder.onNetworkResponse(new SipDetails.Builder(3).setSipResponseCode(i, str).setSipResponseReasonHeader(i2, str2).build());
        } catch (RemoteException e) {
            throw new ImsException(e.getMessage(), 1);
        }
    }

    @Override // android.telephony.ims.stub.RcsCapabilityExchangeImplBase.SubscribeResponseCallback
    public void onNetworkResponse(SipDetails sipDetails) throws ImsException {
        try {
            this.mResponseBinder.onNetworkResponse(sipDetails);
        } catch (RemoteException e) {
            throw new ImsException(e.getMessage(), 1);
        }
    }

    @Override // android.telephony.ims.stub.RcsCapabilityExchangeImplBase.SubscribeResponseCallback
    public void onNotifyCapabilitiesUpdate(List<String> list) throws ImsException {
        try {
            this.mResponseBinder.onNotifyCapabilitiesUpdate(list);
        } catch (RemoteException e) {
            throw new ImsException(e.getMessage(), 1);
        }
    }

    @Override // android.telephony.ims.stub.RcsCapabilityExchangeImplBase.SubscribeResponseCallback
    public void onResourceTerminated(List<Pair<Uri, String>> list) throws ImsException {
        try {
            this.mResponseBinder.onResourceTerminated(getTerminatedReasonList(list));
        } catch (RemoteException e) {
            throw new ImsException(e.getMessage(), 1);
        }
    }

    private List<RcsContactTerminatedReason> getTerminatedReasonList(List<Pair<Uri, String>> list) {
        ArrayList arrayList = new ArrayList();
        if (list != null) {
            for (Pair<Uri, String> pair : list) {
                arrayList.add(new RcsContactTerminatedReason(pair.first, pair.second));
            }
        }
        return arrayList;
    }

    @Override // android.telephony.ims.stub.RcsCapabilityExchangeImplBase.SubscribeResponseCallback
    public void onTerminated(String str, long j) throws ImsException {
        try {
            this.mResponseBinder.onTerminated(str, j);
        } catch (RemoteException e) {
            throw new ImsException(e.getMessage(), 1);
        }
    }
}
