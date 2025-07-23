package gov.nist.javax.sip.header.extensions;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import androidx.compose.ui.autofill.PopulateViewStructure_androidKt$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import gov.nist.javax.sip.address.AddressImpl;
import gov.nist.javax.sip.header.AddressParametersHeader;
import javax.sip.header.Header;
import javax.sip.header.HeaderAddress;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        StringBuilder m = PopulateViewStructure_androidKt$$ExternalSyntheticOutline0.m(addressImpl.getAddressType() == 2 ? "<" : "");
        m.append(this.address.encode());
        String sb = m.toString();
        if (this.address.getAddressType() == 2) {
            sb = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(sb, ">");
        }
        if (this.parameters.isEmpty()) {
            return sb;
        }
        StringBuilder m2 = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(sb, ";");
        m2.append(this.parameters.encode());
        return m2.toString();
    }
}
