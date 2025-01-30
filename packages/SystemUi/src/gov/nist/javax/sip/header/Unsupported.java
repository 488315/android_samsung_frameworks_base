package gov.nist.javax.sip.header;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class Unsupported extends SIPHeader {
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
