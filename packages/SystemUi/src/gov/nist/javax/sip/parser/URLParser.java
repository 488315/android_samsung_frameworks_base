package gov.nist.javax.sip.parser;

import gov.nist.core.HostNameParser;
import gov.nist.core.LexerCore;
import gov.nist.core.NameValue;
import gov.nist.core.NameValueList;
import gov.nist.core.StringTokenizer;
import gov.nist.core.Token;
import gov.nist.javax.sip.address.GenericURI;
import gov.nist.javax.sip.address.SipUri;
import gov.nist.javax.sip.address.TelURLImpl;
import gov.nist.javax.sip.address.TelephoneNumber;
import java.text.ParseException;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class URLParser extends Parser {
    public URLParser(String str) {
        this.lexer = new Lexer("sip_urlLexer", str);
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0025  */
    /* JADX WARN: Removed duplicated region for block: B:19:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static boolean isUnreserved(char c) {
        boolean z;
        if (StringTokenizer.isAlphaDigit(c)) {
            return true;
        }
        if (c != '!' && c != '_' && c != '~' && c != '-' && c != '.') {
            switch (c) {
                case '\'':
                case '(':
                case ')':
                case '*':
                    break;
                default:
                    z = false;
                    break;
            }
            return !z;
        }
        z = true;
        if (!z) {
        }
    }

    public final String base_phone_number() {
        StringBuffer stringBuffer = new StringBuffer();
        int i = 0;
        while (true) {
            if (!this.lexer.hasMoreChars()) {
                break;
            }
            char lookAhead = this.lexer.lookAhead(0);
            if (StringTokenizer.isDigit(lookAhead) || lookAhead == '-' || lookAhead == '.' || lookAhead == '(' || lookAhead == ')') {
                this.lexer.consume(1);
                stringBuffer.append(lookAhead);
                i++;
            } else if (i <= 0) {
                throw createParseException("unexpected " + lookAhead);
            }
        }
        return stringBuffer.toString();
    }

    public final TelephoneNumber global_phone_number(boolean z) {
        TelephoneNumber telephoneNumber = new TelephoneNumber();
        telephoneNumber.setGlobal(true);
        this.lexer.match(43);
        telephoneNumber.setPhoneNumber(base_phone_number());
        if (this.lexer.hasMoreChars() && this.lexer.lookAhead(0) == ';' && z) {
            this.lexer.consume(1);
            telephoneNumber.setParameters(tel_parameters());
        }
        return telephoneNumber;
    }

    public final boolean isEscaped() {
        try {
            if (this.lexer.lookAhead(0) == '%' && StringTokenizer.isHexDigit(this.lexer.lookAhead(1))) {
                return StringTokenizer.isHexDigit(this.lexer.lookAhead(2));
            }
            return false;
        } catch (Exception unused) {
            return false;
        }
    }

    public final String local_number() {
        StringBuffer stringBuffer = new StringBuffer();
        int i = 0;
        while (true) {
            if (!this.lexer.hasMoreChars()) {
                break;
            }
            char lookAhead = this.lexer.lookAhead(0);
            if (lookAhead == '*' || lookAhead == '#' || lookAhead == '-' || lookAhead == '.' || lookAhead == '(' || lookAhead == ')' || StringTokenizer.isHexDigit(lookAhead)) {
                this.lexer.consume(1);
                stringBuffer.append(lookAhead);
                i++;
            } else if (i <= 0) {
                throw createParseException("unexepcted " + lookAhead);
            }
        }
        return stringBuffer.toString();
    }

    public final String paramNameOrValue() {
        int i = this.lexer.ptr;
        while (this.lexer.hasMoreChars()) {
            char lookAhead = this.lexer.lookAhead(0);
            if (!(lookAhead == '$' || lookAhead == '&' || lookAhead == '+' || lookAhead == '/' || lookAhead == ':' || lookAhead == '[' || lookAhead == ']') && !isUnreserved(lookAhead)) {
                if (!isEscaped()) {
                    break;
                }
                this.lexer.consume(3);
            } else {
                this.lexer.consume(1);
            }
        }
        LexerCore lexerCore = this.lexer;
        return lexerCore.buffer.substring(i, lexerCore.ptr);
    }

    public final String password() {
        int i = this.lexer.ptr;
        while (true) {
            char lookAhead = this.lexer.lookAhead(0);
            if ((lookAhead == '$' || lookAhead == '&' || lookAhead == '=' || lookAhead == '+' || lookAhead == ',') || isUnreserved(lookAhead)) {
                this.lexer.consume(1);
            } else {
                if (!isEscaped()) {
                    LexerCore lexerCore = this.lexer;
                    return lexerCore.buffer.substring(i, lexerCore.ptr);
                }
                this.lexer.consume(3);
            }
        }
    }

    public final NameValue qheader() {
        boolean z;
        String nextToken = this.lexer.getNextToken('=');
        this.lexer.consume(1);
        StringBuffer stringBuffer = new StringBuffer();
        while (this.lexer.hasMoreChars()) {
            char lookAhead = this.lexer.lookAhead(0);
            if (lookAhead != '!' && lookAhead != '\"' && lookAhead != '$' && lookAhead != ':' && lookAhead != '?' && lookAhead != '[' && lookAhead != ']' && lookAhead != '_' && lookAhead != '~') {
                switch (lookAhead) {
                    default:
                        switch (lookAhead) {
                            case '-':
                            case '.':
                            case '/':
                                break;
                            default:
                                z = false;
                                break;
                        }
                    case '(':
                    case ')':
                    case '*':
                    case '+':
                        z = true;
                        break;
                }
                if (!z || StringTokenizer.isAlphaDigit(lookAhead)) {
                    this.lexer.consume(1);
                    stringBuffer.append(lookAhead);
                } else {
                    if (lookAhead != '%') {
                        return new NameValue(nextToken, stringBuffer.toString(), false);
                    }
                    StringBuffer stringBuffer2 = new StringBuffer();
                    char lookAhead2 = this.lexer.lookAhead(0);
                    char lookAhead3 = this.lexer.lookAhead(1);
                    char lookAhead4 = this.lexer.lookAhead(2);
                    if (lookAhead2 != '%' || !StringTokenizer.isHexDigit(lookAhead3) || !StringTokenizer.isHexDigit(lookAhead4)) {
                        throw createParseException("escaped");
                    }
                    this.lexer.consume(3);
                    stringBuffer2.append(lookAhead2);
                    stringBuffer2.append(lookAhead3);
                    stringBuffer2.append(lookAhead4);
                    stringBuffer.append(stringBuffer2.toString());
                }
            }
            z = true;
            if (z) {
            }
            this.lexer.consume(1);
            stringBuffer.append(lookAhead);
        }
        return new NameValue(nextToken, stringBuffer.toString(), false);
    }

    public final SipUri sipURL(boolean z) {
        String str;
        String str2;
        SipUri sipUri = new SipUri();
        int i = 2136;
        if (this.lexer.peekNextToken(1)[0].tokenType == 2136) {
            str = "sips";
        } else {
            i = 2051;
            str = "sip";
        }
        try {
            this.lexer.match(i);
            this.lexer.match(58);
            sipUri.setScheme(str);
            int i2 = this.lexer.ptr;
            String user = user();
            if (this.lexer.lookAhead(0) == ':') {
                this.lexer.consume(1);
                str2 = password();
            } else {
                str2 = null;
            }
            if (this.lexer.lookAhead(0) == '@') {
                this.lexer.consume(1);
                sipUri.setUser(user);
                if (str2 != null) {
                    sipUri.setUserPassword(str2);
                }
            } else {
                this.lexer.ptr = i2;
            }
            sipUri.setHostPort(new HostNameParser((Lexer) this.lexer).hostPort(false));
            this.lexer.selectLexer("charLexer");
            while (this.lexer.hasMoreChars() && this.lexer.lookAhead(0) == ';' && z) {
                this.lexer.consume(1);
                NameValue uriParam = uriParam();
                if (uriParam != null) {
                    sipUri.setUriParameter(uriParam);
                }
            }
            if (this.lexer.hasMoreChars() && this.lexer.lookAhead(0) == '?') {
                this.lexer.consume(1);
                while (this.lexer.hasMoreChars()) {
                    sipUri.setQHeader(qheader());
                    if (this.lexer.hasMoreChars() && this.lexer.lookAhead(0) != '&') {
                        break;
                    }
                    this.lexer.consume(1);
                }
            }
            return sipUri;
        } catch (RuntimeException unused) {
            throw new ParseException("Invalid URL: " + this.lexer.buffer, -1);
        }
    }

    public final TelURLImpl telURL(boolean z) {
        TelephoneNumber telephoneNumber;
        this.lexer.match(2105);
        this.lexer.match(58);
        this.lexer.selectLexer("charLexer");
        char lookAhead = this.lexer.lookAhead(0);
        if (lookAhead == '+') {
            telephoneNumber = global_phone_number(z);
        } else {
            if (!StringTokenizer.isHexDigit(lookAhead) && lookAhead != '#' && lookAhead != '*' && lookAhead != '-' && lookAhead != '.' && lookAhead != '(' && lookAhead != ')') {
                throw createParseException("unexpected char " + lookAhead);
            }
            TelephoneNumber telephoneNumber2 = new TelephoneNumber();
            telephoneNumber2.setGlobal(false);
            telephoneNumber2.setPhoneNumber(local_number());
            if (this.lexer.hasMoreChars() && this.lexer.peekNextToken(1)[0].tokenType == 59 && z) {
                this.lexer.consume(1);
                telephoneNumber2.setParameters(tel_parameters());
            }
            telephoneNumber = telephoneNumber2;
        }
        TelURLImpl telURLImpl = new TelURLImpl();
        telURLImpl.setTelephoneNumber(telephoneNumber);
        return telURLImpl;
    }

    public final NameValueList tel_parameters() {
        NameValue nameValue;
        String str;
        NameValueList nameValueList = new NameValueList();
        while (true) {
            String paramNameOrValue = paramNameOrValue();
            if (paramNameOrValue.equalsIgnoreCase("phone-context")) {
                this.lexer.match(61);
                char lookAhead = this.lexer.lookAhead(0);
                if (lookAhead == '+') {
                    this.lexer.consume(1);
                    str = "+" + base_phone_number();
                } else {
                    if (!StringTokenizer.isAlphaDigit(lookAhead)) {
                        throw new ParseException("Invalid phone-context:" + lookAhead, -1);
                    }
                    str = this.lexer.match(4095).tokenValue;
                }
                nameValue = new NameValue("phone-context", str, false);
            } else if (this.lexer.lookAhead(0) == '=') {
                this.lexer.consume(1);
                nameValue = new NameValue(paramNameOrValue, paramNameOrValue(), false);
            } else {
                nameValue = new NameValue(paramNameOrValue, "", true);
            }
            nameValueList.set(nameValue);
            if (this.lexer.lookAhead(0) != ';') {
                return nameValueList;
            }
            this.lexer.consume(1);
        }
    }

    public final NameValue uriParam() {
        String str = "";
        String paramNameOrValue = paramNameOrValue();
        boolean z = false;
        if (this.lexer.lookAhead(0) == '=') {
            this.lexer.consume(1);
            str = paramNameOrValue();
        } else {
            z = true;
        }
        if (paramNameOrValue.length() == 0 && (str == null || str.length() == 0)) {
            return null;
        }
        return new NameValue(paramNameOrValue, str, z);
    }

    public final GenericURI uriReference(boolean z) {
        Token[] peekNextToken = this.lexer.peekNextToken(2);
        Token token = peekNextToken[0];
        Token token2 = peekNextToken[1];
        int i = token.tokenType;
        if (i == 2051 || i == 2136) {
            if (token2.tokenType == 58) {
                return sipURL(z);
            }
            throw createParseException("Expecting ':'");
        }
        if (i == 2105) {
            if (token2.tokenType == 58) {
                return telURL(z);
            }
            throw createParseException("Expecting ':'");
        }
        try {
            return new GenericURI(uricString());
        } catch (ParseException e) {
            throw createParseException(e.getMessage());
        }
    }

    public final String uricString() {
        StringBuffer stringBuffer = new StringBuffer();
        while (true) {
            String str = null;
            try {
                char lookAhead = this.lexer.lookAhead(0);
                if (isUnreserved(lookAhead)) {
                    this.lexer.consume(1);
                    str = String.valueOf(lookAhead);
                } else {
                    if (lookAhead == '$' || lookAhead == '&' || lookAhead == '/' || lookAhead == '=' || lookAhead == '+' || lookAhead == ',' || lookAhead == ':' || lookAhead == ';' || lookAhead == '?' || lookAhead == '@') {
                        this.lexer.consume(1);
                        str = String.valueOf(lookAhead);
                    } else if (isEscaped()) {
                        LexerCore lexerCore = this.lexer;
                        int i = lexerCore.ptr;
                        String substring = lexerCore.buffer.substring(i, i + 3);
                        this.lexer.consume(3);
                        str = substring;
                    }
                }
            } catch (Exception unused) {
            }
            if (str != null) {
                stringBuffer.append(str);
            } else {
                if (this.lexer.lookAhead(0) != '[') {
                    return stringBuffer.toString();
                }
                stringBuffer.append(new HostNameParser((Lexer) this.lexer).hostPort(false).encode());
            }
        }
    }

    public final String user() {
        int i = this.lexer.ptr;
        while (this.lexer.hasMoreChars()) {
            char lookAhead = this.lexer.lookAhead(0);
            if (!isUnreserved(lookAhead)) {
                if (!(lookAhead == '#' || lookAhead == '$' || lookAhead == '&' || lookAhead == '/' || lookAhead == ';' || lookAhead == '=' || lookAhead == '?' || lookAhead == '+' || lookAhead == ',')) {
                    if (!isEscaped()) {
                        break;
                    }
                    this.lexer.consume(3);
                }
            }
            this.lexer.consume(1);
        }
        LexerCore lexerCore = this.lexer;
        return lexerCore.buffer.substring(i, lexerCore.ptr);
    }

    public URLParser(Lexer lexer) {
        this.lexer = lexer;
        lexer.selectLexer("sip_urlLexer");
    }
}
