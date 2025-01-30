package gov.nist.javax.sip.header;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class Server extends SIPHeader {
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
