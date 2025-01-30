package gov.nist.javax.sip.header;

import android.support.v4.media.AbstractC0000x2c234b15;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class Supported extends SIPHeader {
    private static final long serialVersionUID = -7679667592702854542L;
    protected String optionTag;

    public Supported() {
        super("Supported");
        this.optionTag = null;
    }

    @Override // gov.nist.javax.sip.header.SIPHeader, gov.nist.core.GenericObject
    public final String encode() {
        String m16m = AbstractResolvableFuture$$ExternalSyntheticOutline0.m16m(new StringBuilder(), this.headerName, ":");
        if (this.optionTag != null) {
            StringBuilder m2m = AbstractC0000x2c234b15.m2m(m16m, " ");
            m2m.append(this.optionTag);
            m16m = m2m.toString();
        }
        return AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m(m16m, "\r\n");
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
