package gov.nist.javax.sip.header;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
