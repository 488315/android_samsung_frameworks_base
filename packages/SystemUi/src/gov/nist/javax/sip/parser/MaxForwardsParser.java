package gov.nist.javax.sip.parser;

import gov.nist.javax.sip.header.MaxForwards;
import gov.nist.javax.sip.header.SIPHeader;
import javax.sip.InvalidArgumentException;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class MaxForwardsParser extends HeaderParser {
    public MaxForwardsParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() {
        try {
            MaxForwards maxForwards = new MaxForwards();
            headerName(2079);
            maxForwards.setMaxForwards(Integer.parseInt(this.lexer.number()));
            this.lexer.SPorHT();
            this.lexer.match(10);
            return maxForwards;
        } catch (NumberFormatException e) {
            throw createParseException(e.getMessage());
        } catch (InvalidArgumentException e2) {
            throw createParseException(e2.getMessage());
        }
    }

    public MaxForwardsParser(Lexer lexer) {
        super(lexer);
    }
}
