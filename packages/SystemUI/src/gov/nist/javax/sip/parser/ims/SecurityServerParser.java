package gov.nist.javax.sip.parser.ims;

import gov.nist.core.ParserCore;
import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.header.ims.SecurityServer;
import gov.nist.javax.sip.header.ims.SecurityServerList;
import gov.nist.javax.sip.parser.Lexer;

/* loaded from: classes4.dex */
public class SecurityServerParser extends SecurityAgreeParser {
    public SecurityServerParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() {
        ParserCore.dbg_enter();
        try {
            headerName(2137);
            return (SecurityServerList) parse(new SecurityServer());
        } finally {
            ParserCore.dbg_leave();
        }
    }

    public SecurityServerParser(Lexer lexer) {
        super(lexer);
    }
}
