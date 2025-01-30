package gov.nist.javax.sip.parser;

import com.sec.ims.volte2.data.VolteConstants;
import gov.nist.javax.sip.header.AddressParametersHeader;
import gov.nist.javax.sip.header.ReplyTo;
import gov.nist.javax.sip.header.SIPHeader;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class ReplyToParser extends AddressParametersParser {
    public ReplyToParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() {
        ReplyTo replyTo = new ReplyTo();
        headerName(VolteConstants.ErrorCode.SESSION_EXPIRED);
        replyTo.setHeaderName("Reply-To");
        parse((AddressParametersHeader) replyTo);
        return replyTo;
    }

    public ReplyToParser(Lexer lexer) {
        super(lexer);
    }
}
