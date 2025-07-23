package android.telephony.ims.stub;

import android.annotation.SystemApi;
import android.net.Uri;
import android.telephony.ims.ImsException;
import android.telephony.ims.RcsContactUceCapability;
import android.telephony.ims.SipDetails;
import java.util.Set;

@SystemApi
/* loaded from: classes4.dex */
public interface CapabilityExchangeEventListener {

    public interface OptionsRequestCallback {
        void onRespondToCapabilityRequest(RcsContactUceCapability rcsContactUceCapability, boolean z);

        void onRespondToCapabilityRequestWithError(int i, String str);
    }

    default void onPublishUpdated(SipDetails sipDetails) throws ImsException {
    }

    void onRemoteCapabilityRequest(Uri uri, Set<String> set, OptionsRequestCallback optionsRequestCallback) throws ImsException;

    void onRequestPublishCapabilities(int i) throws ImsException;

    void onUnpublish() throws ImsException;

    @Deprecated
    default void onPublishUpdated(int i, String str, int i2, String str2) throws ImsException {
        onPublishUpdated(new SipDetails.Builder(2).setSipResponseCode(i, str).setSipResponseReasonHeader(i2, str2).build());
    }
}
