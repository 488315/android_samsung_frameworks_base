package gov.nist.javax.sip.parser;

import gov.nist.core.LexerCore;
import gov.nist.core.Token;
import gov.nist.javax.sip.header.AuthenticationHeader;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class ChallengeParser extends HeaderParser {
    public ChallengeParser(String str) {
        super(str);
    }

    public final void parse(AuthenticationHeader authenticationHeader) {
        this.lexer.SPorHT();
        this.lexer.match(4095);
        LexerCore lexerCore = this.lexer;
        Token token = lexerCore.currentMatch;
        lexerCore.SPorHT();
        authenticationHeader.setScheme(token.tokenValue);
        while (this.lexer.lookAhead(0) != '\n') {
            authenticationHeader.setParameter(nameValue());
            this.lexer.SPorHT();
            char lookAhead = this.lexer.lookAhead(0);
            if (lookAhead == '\n' || lookAhead == 0) {
                return;
            }
            this.lexer.match(44);
            this.lexer.SPorHT();
        }
    }

    public ChallengeParser(Lexer lexer) {
        super(lexer);
    }
}
