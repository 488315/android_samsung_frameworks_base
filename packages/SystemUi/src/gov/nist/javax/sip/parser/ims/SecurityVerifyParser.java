package gov.nist.javax.sip.parser.ims;

import gov.nist.core.ParserCore;
import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.header.ims.SecurityVerify;
import gov.nist.javax.sip.header.ims.SecurityVerifyList;
import gov.nist.javax.sip.parser.Lexer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class SecurityVerifyParser extends SecurityAgreeParser {
    public SecurityVerifyParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() {
        ParserCore.dbg_enter();
        try {
            headerName(2139);
            return (SecurityVerifyList) parse(new SecurityVerify());
        } finally {
            ParserCore.dbg_leave();
        }
    }

    public SecurityVerifyParser(Lexer lexer) {
        super(lexer);
    }
}
