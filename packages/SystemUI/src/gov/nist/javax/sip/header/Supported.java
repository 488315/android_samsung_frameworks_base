package gov.nist.javax.sip.header;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import javax.sip.header.Header;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class Supported extends SIPHeader implements Header {
    private static final long serialVersionUID = -7679667592702854542L;
    protected String optionTag;

    public Supported() {
        super("Supported");
        this.optionTag = null;
    }

    @Override // gov.nist.javax.sip.header.SIPHeader, gov.nist.core.GenericObject
    public final String encode() {
        String m = TransitionKt$$ExternalSyntheticOutline0.m(new StringBuilder(), this.headerName, ":");
        if (this.optionTag != null) {
            StringBuilder m2 = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(m, " ");
            m2.append(this.optionTag);
            m = m2.toString();
        }
        return AbstractResolvableFuture$$ExternalSyntheticOutline0.m(m, "\r\n");
    }

    @Override // gov.nist.javax.sip.header.SIPHeader
    public final String encodeBody() {
        String str = this.optionTag;
        return str != null ? str : "";
    }

    public final void setOptionTag(String str) {
        if (str == null) {
            throw new NullPointerException("JAIN-SIP Exception, Supported, setOptionTag(), the optionTag parameter is null");
        }
        this.optionTag = str;
    }

    public Supported(String str) {
        super("Supported");
        this.optionTag = str;
    }
}
