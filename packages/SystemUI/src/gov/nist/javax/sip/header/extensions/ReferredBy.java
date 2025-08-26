package gov.nist.javax.sip.header.extensions;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import androidx.compose.ui.autofill.PopulateViewStructure_androidKt$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import gov.nist.javax.sip.address.AddressImpl;
import gov.nist.javax.sip.header.AddressParametersHeader;
import javax.sip.header.Header;
import javax.sip.header.HeaderAddress;

/* loaded from: classes4.dex */
public final class ReferredBy extends AddressParametersHeader implements Header, HeaderAddress {
    private static final long serialVersionUID = 3134344915465784267L;

    public ReferredBy() {
        super("Referred-By");
    }

    @Override // gov.nist.javax.sip.header.SIPHeader
    public final String encodeBody() {
        AddressImpl addressImpl = this.address;
        if (addressImpl == null) {
            return null;
        }
        StringBuilder sbM = PopulateViewStructure_androidKt$$ExternalSyntheticOutline0.m(addressImpl.getAddressType() == 2 ? "<" : "");
        sbM.append(this.address.encode());
        String string = sbM.toString();
        if (this.address.getAddressType() == 2) {
            string = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(string, ">");
        }
        if (this.parameters.isEmpty()) {
            return string;
        }
        StringBuilder sbM2 = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(string, ";");
        sbM2.append(this.parameters.encode());
        return sbM2.toString();
    }
}
