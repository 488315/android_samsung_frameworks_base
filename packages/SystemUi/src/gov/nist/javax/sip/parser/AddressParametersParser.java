package gov.nist.javax.sip.parser;

import gov.nist.core.LexerCore;
import gov.nist.core.ParserCore;
import gov.nist.javax.sip.header.AddressParametersHeader;
import gov.nist.javax.sip.header.ParametersHeader;
import java.text.ParseException;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class AddressParametersParser extends ParametersParser {
    public AddressParametersParser(Lexer lexer) {
        super(lexer);
    }

    public final void parse(AddressParametersHeader addressParametersHeader) {
        ParserCore.dbg_enter();
        try {
            try {
                addressParametersHeader.setAddress(new AddressParser((Lexer) this.lexer).address());
                this.lexer.SPorHT();
                boolean z = false;
                char lookAhead = this.lexer.lookAhead(0);
                if (this.lexer.hasMoreChars() && lookAhead != 0 && lookAhead != '\n') {
                    LexerCore lexerCore = this.lexer;
                    lexerCore.getClass();
                    try {
                        z = LexerCore.isTokenChar(lexerCore.lookAhead(0));
                    } catch (ParseException unused) {
                    }
                    if (z) {
                        parseNameValueList(addressParametersHeader);
                    }
                }
                parse((ParametersHeader) addressParametersHeader);
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
