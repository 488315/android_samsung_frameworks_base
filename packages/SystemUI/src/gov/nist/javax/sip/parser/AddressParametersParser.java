package gov.nist.javax.sip.parser;

import gov.nist.core.LexerCore;
import gov.nist.core.ParserCore;
import gov.nist.javax.sip.header.AddressParametersHeader;
import gov.nist.javax.sip.header.ParametersHeader;
import java.text.ParseException;

/* loaded from: classes4.dex */
public class AddressParametersParser extends ParametersParser {
    public AddressParametersParser(Lexer lexer) {
        super(lexer);
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0040 A[Catch: ParseException -> 0x0047, all -> 0x0049, TRY_LEAVE, TryCatch #1 {ParseException -> 0x0047, blocks: (B:3:0x0003, B:8:0x002d, B:11:0x003c, B:12:0x0040), top: B:21:0x0003, outer: #0 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void parse(AddressParametersHeader addressParametersHeader) {
        ParserCore.dbg_enter();
        try {
            try {
                addressParametersHeader.setAddress(new AddressParser((Lexer) this.lexer).address());
                this.lexer.SPorHT();
                boolean zIsTokenChar = false;
                char cLookAhead = this.lexer.lookAhead(0);
                if (!this.lexer.hasMoreChars() || cLookAhead == 0 || cLookAhead == '\n') {
                    parse((ParametersHeader) addressParametersHeader);
                } else {
                    LexerCore lexerCore = this.lexer;
                    lexerCore.getClass();
                    try {
                        zIsTokenChar = LexerCore.isTokenChar(lexerCore.lookAhead(0));
                    } catch (ParseException unused) {
                    }
                    if (zIsTokenChar) {
                        parseNameValueList(addressParametersHeader);
                    }
                }
            } catch (ParseException e) {
                throw e;
            }
        } finally {
            ParserCore.dbg_leave();
        }
    }

    public AddressParametersParser(String str) {
        super(str);
    }
}
