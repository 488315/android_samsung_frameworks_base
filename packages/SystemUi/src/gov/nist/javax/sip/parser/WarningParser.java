package gov.nist.javax.sip.parser;

import gov.nist.core.LexerCore;
import gov.nist.core.Token;
import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.header.Warning;
import gov.nist.javax.sip.header.WarningList;
import javax.sip.InvalidArgumentException;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class WarningParser extends HeaderParser {
    public WarningParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() {
        WarningList warningList = new WarningList();
        headerName(2078);
        while (this.lexer.lookAhead(0) != '\n') {
            Warning warning = new Warning();
            warning.setHeaderName("Warning");
            this.lexer.match(4095);
            try {
                warning.setCode(Integer.parseInt(this.lexer.currentMatch.tokenValue));
                this.lexer.SPorHT();
                this.lexer.match(4095);
                LexerCore lexerCore = this.lexer;
                Token token = lexerCore.currentMatch;
                if (lexerCore.lookAhead(0) == ':') {
                    this.lexer.match(58);
                    this.lexer.match(4095);
                    warning.setAgent(token.tokenValue + ":" + this.lexer.currentMatch.tokenValue);
                } else {
                    warning.setAgent(token.tokenValue);
                }
                this.lexer.SPorHT();
                warning.setText(this.lexer.quotedString());
                this.lexer.SPorHT();
                warningList.add((SIPHeader) warning);
                while (this.lexer.lookAhead(0) == ',') {
                    this.lexer.match(44);
                    this.lexer.SPorHT();
                    Warning warning2 = new Warning();
                    this.lexer.match(4095);
                    try {
                        warning2.setCode(Integer.parseInt(this.lexer.currentMatch.tokenValue));
                        this.lexer.SPorHT();
                        this.lexer.match(4095);
                        LexerCore lexerCore2 = this.lexer;
                        Token token2 = lexerCore2.currentMatch;
                        if (lexerCore2.lookAhead(0) == ':') {
                            this.lexer.match(58);
                            this.lexer.match(4095);
                            warning2.setAgent(token2.tokenValue + ":" + this.lexer.currentMatch.tokenValue);
                        } else {
                            warning2.setAgent(token2.tokenValue);
                        }
                        this.lexer.SPorHT();
                        warning2.setText(this.lexer.quotedString());
                        this.lexer.SPorHT();
                        warningList.add((SIPHeader) warning2);
                    } catch (NumberFormatException e) {
                        throw createParseException(e.getMessage());
                    } catch (InvalidArgumentException e2) {
                        throw createParseException(e2.getMessage());
                    }
                }
            } catch (NumberFormatException e3) {
                throw createParseException(e3.getMessage());
            } catch (InvalidArgumentException e4) {
                throw createParseException(e4.getMessage());
            }
        }
        return warningList;
    }

    public WarningParser(Lexer lexer) {
        super(lexer);
    }
}
