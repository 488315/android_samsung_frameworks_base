package gov.nist.javax.sip.parser;

import gov.nist.core.NameValue;
import gov.nist.javax.sip.header.AddressParametersHeader;
import gov.nist.javax.sip.header.ParametersHeader;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
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
