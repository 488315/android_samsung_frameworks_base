package gov.nist.javax.sip.header;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import androidx.compose.ui.autofill.PopulateViewStructure_androidKt$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import gov.nist.javax.sip.address.AddressImpl;
import javax.sip.header.Header;
import javax.sip.header.HeaderAddress;

/* loaded from: classes4.dex */
public final class ReplyTo extends AddressParametersHeader implements HeaderAddress, Header {
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
        StringBuilder sbM = PopulateViewStructure_androidKt$$ExternalSyntheticOutline0.m(this.address.getAddressType() == 2 ? "<" : "");
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

    public ReplyTo(AddressImpl addressImpl) {
        super("Reply-To");
        this.address = addressImpl;
    }
}
