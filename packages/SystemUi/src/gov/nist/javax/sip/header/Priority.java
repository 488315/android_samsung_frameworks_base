package gov.nist.javax.sip.header;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class Priority extends SIPHeader {
    private static final long serialVersionUID = 3837543366074322106L;
    protected String priority;

    public Priority() {
        super("Priority");
    }

    @Override // gov.nist.javax.sip.header.SIPHeader
    public final String encodeBody() {
        return this.priority;
    }

    public final void setPriority(String str) {
        if (str == null) {
            throw new NullPointerException("JAIN-SIP Exception,Priority, setPriority(), the priority parameter is null");
        }
        this.priority = str;
    }
}
