package gov.nist.javax.sip.parser.ims;

import gov.nist.core.ParserCore;
import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.header.ims.SecurityClient;
import gov.nist.javax.sip.header.ims.SecurityClientList;
import gov.nist.javax.sip.parser.Lexer;

/* loaded from: classes4.dex */
public class SecurityClientParser extends SecurityAgreeParser {
    public SecurityClientParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() {
        ParserCore.dbg_enter();
        try {
            headerName(2138);
            return (SecurityClientList) parse(new SecurityClient());
        } finally {
            ParserCore.dbg_leave();
        }
    }

    public SecurityClientParser(Lexer lexer) {
        super(lexer);
    }
}
