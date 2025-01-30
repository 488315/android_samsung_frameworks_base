package gov.nist.javax.sip.header;

import android.support.v4.media.AbstractC0000x2c234b15;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.constraintlayout.core.ArrayLinkedVariables$$ExternalSyntheticOutline0;
import gov.nist.javax.sip.address.AddressImpl;
import javax.sip.header.HeaderAddress;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class ReplyTo extends AddressParametersHeader implements HeaderAddress {
    private static final long serialVersionUID = -9103698729465531373L;

    public ReplyTo() {
        super("Reply-To");
    }

    @Override // gov.nist.javax.sip.header.SIPHeader, gov.nist.core.GenericObject
    public final String encode() {
        return this.headerName + ": " + encodeBody() + "\r\n";
    }

    @Override // gov.nist.javax.sip.header.SIPHeader
    public final String encodeBody() {
        StringBuilder m18m = ArrayLinkedVariables$$ExternalSyntheticOutline0.m18m(this.address.getAddressType() == 2 ? "<" : "");
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

    public ReplyTo(AddressImpl addressImpl) {
        super("Reply-To");
        this.address = addressImpl;
    }
}
