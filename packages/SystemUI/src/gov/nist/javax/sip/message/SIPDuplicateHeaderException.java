package gov.nist.javax.sip.message;

import gov.nist.javax.sip.header.SIPHeader;
import java.text.ParseException;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class SIPDuplicateHeaderException extends ParseException {
    private static final long serialVersionUID = 8241107266407879291L;
    protected SIPHeader sipHeader;
    protected SIPMessage sipMessage;

    public SIPDuplicateHeaderException(String str) {
        super(str, 0);
    }
}
