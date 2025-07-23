package gov.nist.javax.sip.header;

import javax.sip.header.Header;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class Unsupported extends SIPHeader implements Header {
    private static final long serialVersionUID = -2479414149440236199L;
    protected String optionTag;

    public Unsupported() {
        super("Unsupported");
    }

    @Override // gov.nist.javax.sip.header.SIPHeader
    public final String encodeBody() {
        return this.optionTag;
    }

    public final void setOptionTag(String str) {
        if (str == null) {
            throw new NullPointerException("JAIN-SIP Exception,  Unsupported, setOptionTag(), The option tag parameter is null");
        }
        this.optionTag = str;
    }

    public Unsupported(String str) {
        super("Unsupported");
        this.optionTag = str;
    }
}
