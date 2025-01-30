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

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class ViaParser extends HeaderParser {
    public ViaParser(String str) {
        super(str);
    }

    public final NameValue nameValue$1() {
        String str;
        this.lexer.match(4095);
        LexerCore lexerCore = this.lexer;
        Token token = lexerCore.currentMatch;
        lexerCore.SPorHT();
        try {
            boolean z = false;
            if (this.lexer.lookAhead(0) != '=') {
                return new NameValue(token.tokenValue.toLowerCase(), null);
            }
            this.lexer.consume(1);
            this.lexer.SPorHT();
            if (token.tokenValue.compareToIgnoreCase("received") == 0) {
                str = this.lexer.byteStringNoSemicolon();
            } else if (this.lexer.lookAhead(0) == '\"') {
                str = this.lexer.quotedString();
                z = true;
            } else {
                this.lexer.match(4095);
                str = this.lexer.currentMatch.tokenValue;
            }
            NameValue nameValue = new NameValue(token.tokenValue.toLowerCase(), str);
            if (z) {
                nameValue.setQuotedValue();
            }
            return nameValue;
        } catch (ParseException unused) {
            return new NameValue(token.tokenValue, null);
        } catch (Throwable th) {
            throw th;
        }
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() {
        ViaList viaList = new ViaList();
        this.lexer.match(2064);
        this.lexer.SPorHT();
        this.lexer.match(58);
        this.lexer.SPorHT();
        do {
            Via via = new Via();
            parseVia(via);
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

    public final void parseVia(Via via) {
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
            NameValue nameValue$1 = nameValue$1();
            if (nameValue$1.getName().equals("branch") && ((String) nameValue$1.getValueAsObject()) == null) {
                throw new ParseException("null branch Id", this.lexer.ptr);
            }
            via.setParameter(nameValue$1);
            this.lexer.SPorHT();
        }
        if (this.lexer.lookAhead(0) == '(') {
            this.lexer.selectLexer("charLexer");
            this.lexer.consume(1);
            StringBuffer stringBuffer = new StringBuffer();
            while (true) {
                char lookAhead = this.lexer.lookAhead(0);
                if (lookAhead == ')') {
                    this.lexer.consume(1);
                    break;
                }
                if (lookAhead == '\\') {
                    stringBuffer.append(this.lexer.currentMatch.tokenValue);
                    this.lexer.consume(1);
                    stringBuffer.append(this.lexer.currentMatch.tokenValue);
                    this.lexer.consume(1);
                } else {
                    if (lookAhead == '\n') {
                        break;
                    }
                    stringBuffer.append(lookAhead);
                    this.lexer.consume(1);
                }
            }
            via.setComment(stringBuffer.toString());
        }
    }

    public ViaParser(Lexer lexer) {
        super(lexer);
    }
}
