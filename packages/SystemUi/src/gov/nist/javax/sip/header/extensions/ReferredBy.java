package gov.nist.javax.sip.header.extensions;

import android.support.v4.media.AbstractC0000x2c234b15;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.constraintlayout.core.ArrayLinkedVariables$$ExternalSyntheticOutline0;
import gov.nist.javax.sip.address.AddressImpl;
import gov.nist.javax.sip.header.AddressParametersHeader;
import javax.sip.header.HeaderAddress;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class ReferredBy extends AddressParametersHeader implements HeaderAddress {
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
        StringBuilder m18m = ArrayLinkedVariables$$ExternalSyntheticOutline0.m18m(addressImpl.getAddressType() == 2 ? "<" : "");
        m18m.append(this.address.encode());
        String sb = m18m.toString();
        if (this.address.getAddressType() == 2) {
            sb = AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m(sb, ">");
        }
        if (this.parameters.isEmpty()) {
            return sb;
        }
        StringBuilder m2m = AbstractC0000x2c234b15.m2m(sb, ";");
        m2m.append(this.parameters.encode());
        return m2m.toString();
    }
}
