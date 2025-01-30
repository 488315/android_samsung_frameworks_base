package gov.nist.javax.sip.header;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class UserAgent extends SIPHeader {
    private static final long serialVersionUID = 4561239179796364295L;
    protected List productTokens;

    public UserAgent() {
        super("User-Agent");
        this.productTokens = new LinkedList();
    }

    public final void addProductToken(String str) {
        this.productTokens.add(str);
    }

    @Override // gov.nist.core.GenericObject
    public final Object clone() {
        UserAgent userAgent = (UserAgent) super.clone();
        if (this.productTokens != null) {
            userAgent.productTokens = new LinkedList(this.productTokens);
        }
        return userAgent;
    }

    @Override // gov.nist.javax.sip.header.SIPHeader
    public final String encodeBody() {
        StringBuffer stringBuffer = new StringBuffer();
        ListIterator listIterator = this.productTokens.listIterator();
        while (listIterator.hasNext()) {
            stringBuffer.append((String) listIterator.next());
        }
        return stringBuffer.toString();
    }
}
