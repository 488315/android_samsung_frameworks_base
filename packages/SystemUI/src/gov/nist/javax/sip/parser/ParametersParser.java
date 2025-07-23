package gov.nist.javax.sip.parser;

import gov.nist.core.NameValue;
import gov.nist.javax.sip.header.AddressParametersHeader;
import gov.nist.javax.sip.header.ParametersHeader;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class ParametersParser extends HeaderParser {
    public ParametersParser(Lexer lexer) {
        super(lexer);
    }

    public final void parse(ParametersHeader parametersHeader) {
        this.lexer.SPorHT();
        while (this.lexer.lookAhead(0) == ';') {
            this.lexer.consume(1);
            this.lexer.SPorHT();
            parametersHeader.setParameter(nameValue());
            this.lexer.SPorHT();
        }
    }

    public final void parseNameValueList(AddressParametersHeader addressParametersHeader) {
        addressParametersHeader.removeParameters();
        while (true) {
            this.lexer.SPorHT();
            NameValue nameValue = nameValue();
            addressParametersHeader.setParameter(nameValue.getName(), (String) nameValue.getValueAsObject());
            this.lexer.SPorHT();
            if (this.lexer.lookAhead(0) != ';') {
                return;
            } else {
                this.lexer.consume(1);
            }
        }
    }

    public ParametersParser(String str) {
        super(str);
    }
}
