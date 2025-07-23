package gov.nist.javax.sip.parser;

import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.header.SubscriptionState;
import javax.sip.InvalidArgumentException;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class SubscriptionStateParser extends HeaderParser {
    public SubscriptionStateParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() {
        SubscriptionState subscriptionState = new SubscriptionState();
        headerName(2104);
        subscriptionState.setHeaderName("Subscription-State");
        this.lexer.match(4095);
        subscriptionState.setState(this.lexer.currentMatch.tokenValue);
        while (this.lexer.lookAhead(0) == ';') {
            this.lexer.match(59);
            this.lexer.SPorHT();
            this.lexer.match(4095);
            String str = this.lexer.currentMatch.tokenValue;
            if (str.equalsIgnoreCase("reason")) {
                this.lexer.match(61);
                this.lexer.SPorHT();
                this.lexer.match(4095);
                subscriptionState.setReasonCode(this.lexer.currentMatch.tokenValue);
            } else if (str.equalsIgnoreCase("expires")) {
                this.lexer.match(61);
                this.lexer.SPorHT();
                this.lexer.match(4095);
                try {
                    subscriptionState.setExpires(Integer.parseInt(this.lexer.currentMatch.tokenValue));
                } catch (NumberFormatException e) {
                    throw createParseException(e.getMessage());
                } catch (InvalidArgumentException e2) {
                    throw createParseException(e2.getMessage());
                }
            } else if (str.equalsIgnoreCase("retry-after")) {
                this.lexer.match(61);
                this.lexer.SPorHT();
                this.lexer.match(4095);
                try {
                    subscriptionState.setRetryAfter(Integer.parseInt(this.lexer.currentMatch.tokenValue));
                } catch (NumberFormatException e3) {
                    throw createParseException(e3.getMessage());
                } catch (InvalidArgumentException e4) {
                    throw createParseException(e4.getMessage());
                }
            } else {
                this.lexer.match(61);
                this.lexer.SPorHT();
                this.lexer.match(4095);
                subscriptionState.setParameter(str, this.lexer.currentMatch.tokenValue);
            }
            this.lexer.SPorHT();
        }
        return subscriptionState;
    }

    public SubscriptionStateParser(Lexer lexer) {
        super(lexer);
    }
}
