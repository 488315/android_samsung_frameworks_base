package gov.nist.javax.sip.parser;

import gov.nist.core.LexerCore;
import gov.nist.core.ParserCore;
import gov.nist.javax.sip.header.ExtensionHeaderImpl;
import gov.nist.javax.sip.header.SIPHeader;
import java.util.Calendar;
import java.util.TimeZone;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class HeaderParser extends Parser {
    public HeaderParser(String str) {
        this.lexer = new Lexer("command_keywordLexer", str);
    }

    public final Calendar date() {
        try {
            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
            int parseInt = Integer.parseInt(this.lexer.number());
            if (parseInt <= 0 || parseInt > 31) {
                throw createParseException("Bad day ");
            }
            calendar.set(5, parseInt);
            this.lexer.match(32);
            String lowerCase = this.lexer.ttoken().toLowerCase();
            if (lowerCase.equals("jan")) {
                calendar.set(2, 0);
            } else if (lowerCase.equals("feb")) {
                calendar.set(2, 1);
            } else if (lowerCase.equals("mar")) {
                calendar.set(2, 2);
            } else if (lowerCase.equals("apr")) {
                calendar.set(2, 3);
            } else if (lowerCase.equals("may")) {
                calendar.set(2, 4);
            } else if (lowerCase.equals("jun")) {
                calendar.set(2, 5);
            } else if (lowerCase.equals("jul")) {
                calendar.set(2, 6);
            } else if (lowerCase.equals("aug")) {
                calendar.set(2, 7);
            } else if (lowerCase.equals("sep")) {
                calendar.set(2, 8);
            } else if (lowerCase.equals("oct")) {
                calendar.set(2, 9);
            } else if (lowerCase.equals("nov")) {
                calendar.set(2, 10);
            } else if (lowerCase.equals("dec")) {
                calendar.set(2, 11);
            }
            this.lexer.match(32);
            calendar.set(1, Integer.parseInt(this.lexer.number()));
            return calendar;
        } catch (Exception unused) {
            throw createParseException("bad date field");
        }
    }

    public final void headerName(int i) {
        this.lexer.match(i);
        this.lexer.SPorHT();
        this.lexer.match(58);
        this.lexer.SPorHT();
    }

    public SIPHeader parse() {
        int i;
        String str;
        String nextToken = this.lexer.getNextToken(':');
        this.lexer.consume(1);
        LexerCore lexerCore = this.lexer;
        int i2 = lexerCore.ptr;
        while (true) {
            int i3 = lexerCore.ptr;
            i = lexerCore.bufferLen;
            str = lexerCore.buffer;
            if (i3 >= i || str.charAt(i3) == '\n') {
                break;
            }
            lexerCore.ptr++;
        }
        int i4 = lexerCore.ptr;
        if (i4 < i && str.charAt(i4) == '\n') {
            lexerCore.ptr++;
        }
        String trim = str.substring(i2, lexerCore.ptr).trim();
        ExtensionHeaderImpl extensionHeaderImpl = new ExtensionHeaderImpl(nextToken);
        extensionHeaderImpl.setValue(trim);
        return extensionHeaderImpl;
    }

    public final void time(Calendar calendar) {
        try {
            calendar.set(11, Integer.parseInt(this.lexer.number()));
            this.lexer.match(58);
            calendar.set(12, Integer.parseInt(this.lexer.number()));
            this.lexer.match(58);
            calendar.set(13, Integer.parseInt(this.lexer.number()));
        } catch (Exception unused) {
            throw createParseException("error processing time ");
        }
    }

    public final void wkday() {
        ParserCore.dbg_enter();
        try {
            String lowerCase = this.lexer.ttoken().toLowerCase();
            if ("Mon".equalsIgnoreCase(lowerCase)) {
                return;
            }
            if ("Tue".equalsIgnoreCase(lowerCase)) {
                return;
            }
            if ("Wed".equalsIgnoreCase(lowerCase)) {
                return;
            }
            if ("Thu".equalsIgnoreCase(lowerCase)) {
                return;
            }
            if ("Fri".equalsIgnoreCase(lowerCase)) {
                return;
            }
            if ("Sat".equalsIgnoreCase(lowerCase)) {
                return;
            }
            if ("Sun".equalsIgnoreCase(lowerCase)) {
            } else {
                throw createParseException("bad wkday");
            }
        } finally {
            ParserCore.dbg_leave();
        }
    }

    public HeaderParser(Lexer lexer) {
        this.lexer = lexer;
        lexer.selectLexer("command_keywordLexer");
    }
}
