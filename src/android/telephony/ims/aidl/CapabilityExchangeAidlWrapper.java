package android.telephony.ims.aidl;

import android.net.Uri;
import android.os.Binder;
import android.os.RemoteException;
import android.telephony.ims.ImsException;
import android.telephony.ims.RcsContactUceCapability;
import android.telephony.ims.SipDetails;
import android.telephony.ims.aidl.IOptionsRequestCallback;
import android.telephony.ims.stub.CapabilityExchangeEventListener;
import android.util.Log;
import java.util.ArrayList;
import java.util.Set;

/* loaded from: classes4.dex */
public class CapabilityExchangeAidlWrapper implements CapabilityExchangeEventListener {
    private static final String LOG_TAG = "CapExchangeListener";
    private final ICapabilityExchangeEventListener mListenerBinder;

    public CapabilityExchangeAidlWrapper(ICapabilityExchangeEventListener iCapabilityExchangeEventListener) {
        this.mListenerBinder = iCapabilityExchangeEventListener;
    }

    @Override // android.telephony.ims.stub.CapabilityExchangeEventListener
    public void onRequestPublishCapabilities(int i) throws ImsException {
        ICapabilityExchangeEventListener iCapabilityExchangeEventListener = this.mListenerBinder;
        if (iCapabilityExchangeEventListener == null) {
            return;
        }
        try {
            iCapabilityExchangeEventListener.onRequestPublishCapabilities(i);
        } catch (RemoteException e) {
            Log.w(LOG_TAG, "request publish capabilities exception: " + e);
            throw new ImsException("Remote is not available", 1);
        }
    }

    @Override // android.telephony.ims.stub.CapabilityExchangeEventListener
    public void onUnpublish() throws ImsException {
        ICapabilityExchangeEventListener iCapabilityExchangeEventListener = this.mListenerBinder;
        if (iCapabilityExchangeEventListener == null) {
            return;
        }
        try {
            iCapabilityExchangeEventListener.onUnpublish();
        } catch (RemoteException e) {
            Log.w(LOG_TAG, "Unpublish exception: " + e);
            throw new ImsException("Remote is not available", 1);
        }
    }

    @Override // android.telephony.ims.stub.CapabilityExchangeEventListener
    @Deprecated
    public void onPublishUpdated(int i, String str, int i2, String str2) throws ImsException {
        ICapabilityExchangeEventListener iCapabilityExchangeEventListener = this.mListenerBinder;
        if (iCapabilityExchangeEventListener == null) {
            return;
        }
        try {
            iCapabilityExchangeEventListener.onPublishUpdated(new SipDetails.Builder(2).setSipResponseCode(i, str).setSipResponseReasonHeader(i2, str2).build());
        } catch (RemoteException e) {
            Log.w(LOG_TAG, "onPublishUpdated exception: " + e);
            throw new ImsException("Remote is not available", 1);
        }
    }

    @Override // android.telephony.ims.stub.CapabilityExchangeEventListener
    public void onPublishUpdated(SipDetails sipDetails) throws ImsException {
        ICapabilityExchangeEventListener iCapabilityExchangeEventListener = this.mListenerBinder;
        if (iCapabilityExchangeEventListener == null) {
            return;
        }
        try {
            iCapabilityExchangeEventListener.onPublishUpdated(sipDetails);
        } catch (RemoteException e) {
            Log.w(LOG_TAG, "onPublishUpdated exception: " + e);
            throw new ImsException("Remote is not available", 1);
        }
    }

    @Override // android.telephony.ims.stub.CapabilityExchangeEventListener
    public void onRemoteCapabilityRequest(Uri uri, Set<String> set, final CapabilityExchangeEventListener.OptionsRequestCallback optionsRequestCallback) throws ImsException {
        ICapabilityExchangeEventListener iCapabilityExchangeEventListener = this.mListenerBinder;
        if (iCapabilityExchangeEventListener == null) {
            return;
        }
        try {
            iCapabilityExchangeEventListener.onRemoteCapabilityRequest(uri, new ArrayList(set), new IOptionsRequestCallback.Stub(this) { // from class: android.telephony.ims.aidl.CapabilityExchangeAidlWrapper.1
                @Override // android.telephony.ims.aidl.IOptionsRequestCallback
                public void respondToCapabilityRequest(RcsContactUceCapability rcsContactUceCapability, boolean z) {
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        optionsRequestCallback.onRespondToCapabilityRequest(rcsContactUceCapability, z);
                    } finally {
                        restoreCallingIdentity(clearCallingIdentity);
                    }
                }

                @Override // android.telephony.ims.aidl.IOptionsRequestCallback
                public void respondToCapabilityRequestWithError(int i, String str) {
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        optionsRequestCallback.onRespondToCapabilityRequestWithError(i, str);
                    } finally {
                        restoreCallingIdentity(clearCallingIdentity);
                    }
                }
            });
        } catch (RemoteException e) {
            Log.w(LOG_TAG, "Remote capability request exception: " + e);
            throw new ImsException("Remote is not available", 1);
        }
    }
}
