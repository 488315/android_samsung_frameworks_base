package gov.nist.javax.sip.header;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import javax.sip.header.Header;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class UserAgent extends SIPHeader implements Header {
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
