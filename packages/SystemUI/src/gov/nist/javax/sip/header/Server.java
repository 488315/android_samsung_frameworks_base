package gov.nist.javax.sip.header;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import javax.sip.header.Header;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class Server extends SIPHeader implements Header {
    private static final long serialVersionUID = -3587764149383342973L;
    protected List productTokens;

    public Server() {
        super("Server");
        this.productTokens = new LinkedList();
    }

    public final void addProductToken(String str) {
        this.productTokens.add(str);
    }

    @Override // gov.nist.javax.sip.header.SIPHeader
    public final String encodeBody() {
        StringBuffer stringBuffer = new StringBuffer();
        ListIterator listIterator = this.productTokens.listIterator();
        while (listIterator.hasNext()) {
            stringBuffer.append((String) listIterator.next());
            if (!listIterator.hasNext()) {
                break;
            }
            stringBuffer.append('/');
        }
        return stringBuffer.toString();
    }
}
