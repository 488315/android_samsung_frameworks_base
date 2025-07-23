package android.telephony.ims.aidl;

import android.os.RemoteException;
import android.telephony.ims.ImsException;
import android.telephony.ims.stub.RcsCapabilityExchangeImplBase;
import java.util.List;

/* loaded from: classes4.dex */
public class RcsOptionsResponseAidlWrapper implements RcsCapabilityExchangeImplBase.OptionsResponseCallback {
    private final IOptionsResponseCallback mResponseBinder;

    public RcsOptionsResponseAidlWrapper(IOptionsResponseCallback iOptionsResponseCallback) {
        this.mResponseBinder = iOptionsResponseCallback;
    }

    @Override // android.telephony.ims.stub.RcsCapabilityExchangeImplBase.OptionsResponseCallback
    public void onCommandError(int i) {
        try {
            this.mResponseBinder.onCommandError(i);
        } catch (RemoteException unused) {
        }
    }

    @Override // android.telephony.ims.stub.RcsCapabilityExchangeImplBase.OptionsResponseCallback
    public void onNetworkResponse(int i, String str, List<String> list) throws ImsException {
        try {
            this.mResponseBinder.onNetworkResponse(i, str, list);
        } catch (RemoteException unused) {
        }
    }
}
