package gov.nist.javax.sip.header;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import androidx.compose.ui.autofill.PopulateViewStructure_androidKt$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import gov.nist.javax.sip.address.AddressImpl;
import javax.sip.header.Header;
import javax.sip.header.HeaderAddress;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        StringBuilder m = PopulateViewStructure_androidKt$$ExternalSyntheticOutline0.m(this.address.getAddressType() == 2 ? "<" : "");
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

    public ReplyTo(AddressImpl addressImpl) {
        super("Reply-To");
        this.address = addressImpl;
    }
}
