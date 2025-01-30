package gov.nist.javax.sip.message;

import gov.nist.javax.sip.header.CSeq;
import gov.nist.javax.sip.header.RequestLine;
import java.util.HashSet;
import java.util.Hashtable;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class SIPRequest extends SIPMessage {
    public static final Hashtable nameTable;
    private static final long serialVersionUID = 3360720013577322927L;
    private RequestLine requestLine;

    static {
        HashSet hashSet = new HashSet();
        nameTable = new Hashtable();
        hashSet.add("INVITE");
        hashSet.add("UPDATE");
        hashSet.add("SUBSCRIBE");
        hashSet.add("NOTIFY");
        hashSet.add("REFER");
        putName("INVITE");
        putName("BYE");
        putName("CANCEL");
        putName("ACK");
        putName("PRACK");
        putName("INFO");
        putName("MESSAGE");
        putName("NOTIFY");
        putName("OPTIONS");
        putName("PRACK");
        putName("PUBLISH");
        putName("REFER");
        putName("REGISTER");
        putName("SUBSCRIBE");
        putName("UPDATE");
    }

    public static String getCannonicalName(String str) {
        Hashtable hashtable = nameTable;
        return hashtable.containsKey(str) ? (String) hashtable.get(str) : str;
    }

    public static void putName(String str) {
        nameTable.put(str, str);
    }

    @Override // gov.nist.javax.sip.message.SIPMessage, gov.nist.core.GenericObject
    public final Object clone() {
        SIPRequest sIPRequest = (SIPRequest) super.clone();
        RequestLine requestLine = this.requestLine;
        if (requestLine != null) {
            sIPRequest.requestLine = (RequestLine) requestLine.clone();
        }
        return sIPRequest;
    }

    @Override // gov.nist.javax.sip.message.SIPMessage, gov.nist.core.GenericObject
    public final String encode() {
        CSeq cSeq;
        RequestLine requestLine = this.requestLine;
        if (requestLine == null) {
            return this.nullRequest ? "\r\n\r\n" : super.encode();
        }
        if (requestLine.getMethod() == null && (cSeq = this.cSeqHeader) != null) {
            this.requestLine.setMethod(getCannonicalName(cSeq.getMethod()));
        }
        return this.requestLine.encode() + super.encode();
    }

    @Override // gov.nist.javax.sip.message.SIPMessage, gov.nist.core.GenericObject
    public final boolean equals(Object obj) {
        return SIPRequest.class.equals(obj.getClass()) && this.requestLine.equals(((SIPRequest) obj).requestLine) && super.equals(obj);
    }

    public final String toString() {
        return encode();
    }
}
