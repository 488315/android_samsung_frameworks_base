package gov.nist.javax.sip.header;

/* loaded from: classes4.dex */
public class AcceptEncodingList extends SIPHeaderList<AcceptEncoding> {
    public AcceptEncodingList() {
        super(AcceptEncoding.class, "Accept-Encoding");
    }

    @Override // gov.nist.javax.sip.header.SIPHeaderList, gov.nist.core.GenericObject
    public final Object clone() {
        AcceptEncodingList acceptEncodingList = new AcceptEncodingList();
        acceptEncodingList.clonehlist(this.hlist);
        return acceptEncodingList;
    }
}
