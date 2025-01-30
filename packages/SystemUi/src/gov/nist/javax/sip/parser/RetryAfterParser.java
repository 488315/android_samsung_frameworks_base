package gov.nist.javax.sip.parser;

import gov.nist.javax.sip.header.RetryAfter;
import gov.nist.javax.sip.header.SIPHeader;
import javax.sip.InvalidArgumentException;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class RetryAfterParser extends HeaderParser {
    public RetryAfterParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() {
        RetryAfter retryAfter = new RetryAfter();
        headerName(2073);
        try {
            retryAfter.setRetryAfter(Integer.parseInt(this.lexer.number()));
            this.lexer.SPorHT();
            if (this.lexer.lookAhead(0) == '(') {
                retryAfter.setComment(this.lexer.comment());
            }
            this.lexer.SPorHT();
            while (this.lexer.lookAhead(0) == ';') {
                this.lexer.match(59);
                this.lexer.SPorHT();
                this.lexer.match(4095);
                String str = this.lexer.currentMatch.tokenValue;
                if (str.equalsIgnoreCase("duration")) {
                    this.lexer.match(61);
                    this.lexer.SPorHT();
                    try {
                        retryAfter.setDuration(Integer.parseInt(this.lexer.number()));
                    } catch (NumberFormatException e) {
                        throw createParseException(e.getMessage());
                    } catch (InvalidArgumentException e2) {
                        throw createParseException(e2.getMessage());
                    }
                } else {
                    this.lexer.SPorHT();
                    this.lexer.match(61);
                    this.lexer.SPorHT();
                    this.lexer.match(4095);
                    retryAfter.setParameter(str, this.lexer.currentMatch.tokenValue);
                }
                this.lexer.SPorHT();
            }
            return retryAfter;
        } catch (NumberFormatException e3) {
            throw createParseException(e3.getMessage());
        } catch (InvalidArgumentException e4) {
            throw createParseException(e4.getMessage());
        }
    }

    public RetryAfterParser(Lexer lexer) {
        super(lexer);
    }
}
