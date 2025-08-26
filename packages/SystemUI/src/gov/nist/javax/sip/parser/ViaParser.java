package gov.nist.javax.sip.parser;

import gov.nist.core.HostNameParser;
import gov.nist.core.LexerCore;
import gov.nist.core.NameValue;
import gov.nist.core.Token;
import gov.nist.javax.sip.header.Protocol;
import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.header.Via;
import gov.nist.javax.sip.header.ViaList;
import java.text.ParseException;

/* loaded from: classes4.dex */
public class ViaParser extends HeaderParser {
    public ViaParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() throws ParseException {
        NameValue nameValue;
        String strQuotedString;
        ViaList viaList = new ViaList();
        this.lexer.match(2064);
        this.lexer.SPorHT();
        this.lexer.match(58);
        this.lexer.SPorHT();
        do {
            Via via = new Via();
            this.lexer.match(4095);
            LexerCore lexerCore = this.lexer;
            Token token = lexerCore.currentMatch;
            lexerCore.SPorHT();
            this.lexer.match(47);
            this.lexer.SPorHT();
            this.lexer.match(4095);
            this.lexer.SPorHT();
            LexerCore lexerCore2 = this.lexer;
            Token token2 = lexerCore2.currentMatch;
            lexerCore2.SPorHT();
            this.lexer.match(47);
            this.lexer.SPorHT();
            this.lexer.match(4095);
            this.lexer.SPorHT();
            LexerCore lexerCore3 = this.lexer;
            Token token3 = lexerCore3.currentMatch;
            lexerCore3.SPorHT();
            Protocol protocol = new Protocol();
            protocol.setProtocolName(token.tokenValue);
            protocol.setProtocolVersion(token2.tokenValue);
            protocol.setTransport(token3.tokenValue);
            via.setSentProtocol(protocol);
            via.setSentBy(new HostNameParser((Lexer) this.lexer).hostPort(true));
            this.lexer.SPorHT();
            while (this.lexer.lookAhead(0) == ';') {
                this.lexer.consume(1);
                this.lexer.SPorHT();
                this.lexer.match(4095);
                LexerCore lexerCore4 = this.lexer;
                Token token4 = lexerCore4.currentMatch;
                lexerCore4.SPorHT();
                try {
                    boolean z = false;
                    if (this.lexer.lookAhead(0) == '=') {
                        this.lexer.consume(1);
                        this.lexer.SPorHT();
                        if (token4.tokenValue.compareToIgnoreCase("received") == 0) {
                            strQuotedString = this.lexer.byteStringNoSemicolon();
                        } else if (this.lexer.lookAhead(0) == '\"') {
                            strQuotedString = this.lexer.quotedString();
                            z = true;
                        } else {
                            this.lexer.match(4095);
                            strQuotedString = this.lexer.currentMatch.tokenValue;
                        }
                        nameValue = new NameValue(token4.tokenValue.toLowerCase(), strQuotedString);
                        if (z) {
                            nameValue.setQuotedValue();
                        }
                    } else {
                        nameValue = new NameValue(token4.tokenValue.toLowerCase(), null);
                    }
                } catch (ParseException unused) {
                    nameValue = new NameValue(token4.tokenValue, null);
                }
                if (nameValue.getName().equals("branch") && ((String) nameValue.getValueAsObject()) == null) {
                    throw new ParseException("null branch Id", this.lexer.ptr);
                }
                via.setParameter(nameValue);
                this.lexer.SPorHT();
            }
            if (this.lexer.lookAhead(0) == '(') {
                this.lexer.selectLexer("charLexer");
                this.lexer.consume(1);
                StringBuffer stringBuffer = new StringBuffer();
                while (true) {
                    char cLookAhead = this.lexer.lookAhead(0);
                    if (cLookAhead == ')') {
                        this.lexer.consume(1);
                        break;
                    }
                    if (cLookAhead == '\\') {
                        stringBuffer.append(this.lexer.currentMatch.tokenValue);
                        this.lexer.consume(1);
                        stringBuffer.append(this.lexer.currentMatch.tokenValue);
                        this.lexer.consume(1);
                    } else {
                        if (cLookAhead == '\n') {
                            break;
                        }
                        stringBuffer.append(cLookAhead);
                        this.lexer.consume(1);
                    }
                }
                via.setComment(stringBuffer.toString());
            }
            viaList.add((SIPHeader) via);
            this.lexer.SPorHT();
            if (this.lexer.lookAhead(0) == ',') {
                this.lexer.consume(1);
                this.lexer.SPorHT();
            }
        } while (this.lexer.lookAhead(0) != '\n');
        this.lexer.match(10);
        return viaList;
    }

    public ViaParser(Lexer lexer) {
        super(lexer);
    }
}
