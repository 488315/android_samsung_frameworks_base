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

/* loaded from: classes4.dex */
public class URLParser extends Parser {
    public URLParser(String str) {
        this.lexer = new Lexer("sip_urlLexer", str);
    }

    public static boolean isUnreserved(char c) {
        if (StringTokenizer.isAlphaDigit(c) || c == '!' || c == '_' || c == '~' || c == '-' || c == '.') {
            return true;
        }
        switch (c) {
            case '\'':
            case '(':
            case ')':
            case '*':
                return true;
            default:
                return false;
        }
    }

    public final String base_phone_number() throws ParseException {
        StringBuffer stringBuffer = new StringBuffer();
        int i = 0;
        while (true) {
            if (!this.lexer.hasMoreChars()) {
                break;
            }
            char cLookAhead = this.lexer.lookAhead(0);
            if (StringTokenizer.isDigit(cLookAhead) || cLookAhead == '-' || cLookAhead == '.' || cLookAhead == '(' || cLookAhead == ')') {
                this.lexer.consume(1);
                stringBuffer.append(cLookAhead);
                i++;
            } else if (i <= 0) {
                throw createParseException("unexpected " + cLookAhead);
            }
        }
        return stringBuffer.toString();
    }

    public final boolean isEscaped() {
        try {
            if (this.lexer.lookAhead(0) == '%' && StringTokenizer.isHexDigit(this.lexer.lookAhead(1))) {
                if (StringTokenizer.isHexDigit(this.lexer.lookAhead(2))) {
                    return true;
                }
            }
        } catch (Exception unused) {
        }
        return false;
    }

    public final String paramNameOrValue() {
        int i = this.lexer.ptr;
        while (this.lexer.hasMoreChars()) {
            char cLookAhead = this.lexer.lookAhead(0);
            if (cLookAhead != '$' && cLookAhead != '&' && cLookAhead != '+' && cLookAhead != '/' && cLookAhead != ':' && cLookAhead != '[' && cLookAhead != ']' && !isUnreserved(cLookAhead)) {
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
            char cLookAhead = this.lexer.lookAhead(0);
            if (cLookAhead == '$' || cLookAhead == '&' || cLookAhead == '=' || cLookAhead == '+' || cLookAhead == ',' || isUnreserved(cLookAhead)) {
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

    public final NameValue qheader() throws ParseException {
        String nextToken = this.lexer.getNextToken('=');
        this.lexer.consume(1);
        StringBuffer stringBuffer = new StringBuffer();
        while (this.lexer.hasMoreChars()) {
            char cLookAhead = this.lexer.lookAhead(0);
            if (cLookAhead != '!' && cLookAhead != '\"' && cLookAhead != '$' && cLookAhead != ':' && cLookAhead != '?' && cLookAhead != '[' && cLookAhead != ']' && cLookAhead != '_' && cLookAhead != '~') {
                switch (cLookAhead) {
                    case '(':
                    case ')':
                    case '*':
                    case '+':
                        break;
                    default:
                        switch (cLookAhead) {
                            case '-':
                            case '.':
                            case '/':
                                break;
                            default:
                                if (StringTokenizer.isAlphaDigit(cLookAhead)) {
                                    break;
                                } else {
                                    if (cLookAhead != '%') {
                                        return new NameValue(nextToken, stringBuffer.toString(), false);
                                    }
                                    StringBuffer stringBuffer2 = new StringBuffer();
                                    char cLookAhead2 = this.lexer.lookAhead(0);
                                    char cLookAhead3 = this.lexer.lookAhead(1);
                                    char cLookAhead4 = this.lexer.lookAhead(2);
                                    if (cLookAhead2 != '%' || !StringTokenizer.isHexDigit(cLookAhead3) || !StringTokenizer.isHexDigit(cLookAhead4)) {
                                        throw createParseException("escaped");
                                    }
                                    this.lexer.consume(3);
                                    stringBuffer2.append(cLookAhead2);
                                    stringBuffer2.append(cLookAhead3);
                                    stringBuffer2.append(cLookAhead4);
                                    stringBuffer.append(stringBuffer2.toString());
                                }
                                break;
                        }
                }
            }
            this.lexer.consume(1);
            stringBuffer.append(cLookAhead);
        }
        return new NameValue(nextToken, stringBuffer.toString(), false);
    }

    public final SipUri sipURL(boolean z) throws ParseException {
        String str;
        String strPassword;
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
            String strUser = user();
            if (this.lexer.lookAhead(0) == ':') {
                this.lexer.consume(1);
                strPassword = password();
            } else {
                strPassword = null;
            }
            if (this.lexer.lookAhead(0) == '@') {
                this.lexer.consume(1);
                sipUri.setUser(strUser);
                if (strPassword != null) {
                    sipUri.setUserPassword(strPassword);
                }
            } else {
                this.lexer.ptr = i2;
            }
            sipUri.setHostPort(new HostNameParser((Lexer) this.lexer).hostPort(false));
            this.lexer.selectLexer("charLexer");
            while (this.lexer.hasMoreChars() && this.lexer.lookAhead(0) == ';' && z) {
                this.lexer.consume(1);
                NameValue nameValueUriParam = uriParam();
                if (nameValueUriParam != null) {
                    sipUri.setUriParameter(nameValueUriParam);
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

    public final TelURLImpl telURL(boolean z) throws ParseException {
        TelephoneNumber telephoneNumber;
        this.lexer.match(2105);
        this.lexer.match(58);
        this.lexer.selectLexer("charLexer");
        char cLookAhead = this.lexer.lookAhead(0);
        if (cLookAhead == '+') {
            telephoneNumber = new TelephoneNumber();
            telephoneNumber.setGlobal(true);
            this.lexer.match(43);
            telephoneNumber.setPhoneNumber(base_phone_number());
            if (this.lexer.hasMoreChars() && this.lexer.lookAhead(0) == ';' && z) {
                this.lexer.consume(1);
                telephoneNumber.setParameters(tel_parameters());
            }
        } else {
            if (!StringTokenizer.isHexDigit(cLookAhead) && cLookAhead != '#' && cLookAhead != '*' && cLookAhead != '-' && cLookAhead != '.' && cLookAhead != '(' && cLookAhead != ')') {
                throw createParseException("unexpected char " + cLookAhead);
            }
            telephoneNumber = new TelephoneNumber();
            telephoneNumber.setGlobal(false);
            StringBuffer stringBuffer = new StringBuffer();
            int i = 0;
            while (true) {
                if (!this.lexer.hasMoreChars()) {
                    break;
                }
                char cLookAhead2 = this.lexer.lookAhead(0);
                if (cLookAhead2 == '*' || cLookAhead2 == '#' || cLookAhead2 == '-' || cLookAhead2 == '.' || cLookAhead2 == '(' || cLookAhead2 == ')' || StringTokenizer.isHexDigit(cLookAhead2)) {
                    this.lexer.consume(1);
                    stringBuffer.append(cLookAhead2);
                    i++;
                } else if (i <= 0) {
                    throw createParseException("unexepcted " + cLookAhead2);
                }
            }
            telephoneNumber.setPhoneNumber(stringBuffer.toString());
            if (this.lexer.hasMoreChars() && this.lexer.peekNextToken(1)[0].tokenType == 59 && z) {
                this.lexer.consume(1);
                telephoneNumber.setParameters(tel_parameters());
            }
        }
        TelURLImpl telURLImpl = new TelURLImpl();
        telURLImpl.setTelephoneNumber(telephoneNumber);
        return telURLImpl;
    }

    public final NameValueList tel_parameters() throws ParseException {
        NameValue nameValue;
        String str;
        NameValueList nameValueList = new NameValueList();
        while (true) {
            String strParamNameOrValue = paramNameOrValue();
            if (strParamNameOrValue.equalsIgnoreCase("phone-context")) {
                this.lexer.match(61);
                char cLookAhead = this.lexer.lookAhead(0);
                if (cLookAhead == '+') {
                    this.lexer.consume(1);
                    str = "+" + base_phone_number();
                } else {
                    if (!StringTokenizer.isAlphaDigit(cLookAhead)) {
                        throw new ParseException("Invalid phone-context:" + cLookAhead, -1);
                    }
                    str = this.lexer.match(4095).tokenValue;
                }
                nameValue = new NameValue("phone-context", str, false);
            } else if (this.lexer.lookAhead(0) == '=') {
                this.lexer.consume(1);
                nameValue = new NameValue(strParamNameOrValue, paramNameOrValue(), false);
            } else {
                nameValue = new NameValue(strParamNameOrValue, "", true);
            }
            nameValueList.set(nameValue);
            if (this.lexer.lookAhead(0) != ';') {
                return nameValueList;
            }
            this.lexer.consume(1);
        }
    }

    public final NameValue uriParam() {
        String strParamNameOrValue = "";
        String strParamNameOrValue2 = paramNameOrValue();
        boolean z = false;
        if (this.lexer.lookAhead(0) == '=') {
            this.lexer.consume(1);
            strParamNameOrValue = paramNameOrValue();
        } else {
            z = true;
        }
        if (strParamNameOrValue2.length() == 0 && (strParamNameOrValue == null || strParamNameOrValue.length() == 0)) {
            return null;
        }
        return new NameValue(strParamNameOrValue2, strParamNameOrValue, z);
    }

    public final GenericURI uriReference(boolean z) throws ParseException {
        Token[] tokenArrPeekNextToken = this.lexer.peekNextToken(2);
        Token token = tokenArrPeekNextToken[0];
        Token token2 = tokenArrPeekNextToken[1];
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
        StringBuffer stringBuffer = new StringBuffer();
        while (true) {
            String strValueOf = null;
            try {
                char cLookAhead = this.lexer.lookAhead(0);
                if (isUnreserved(cLookAhead) || cLookAhead == '$' || cLookAhead == '&' || cLookAhead == '/' || cLookAhead == '=' || cLookAhead == '+' || cLookAhead == ',' || cLookAhead == ':' || cLookAhead == ';' || cLookAhead == '?' || cLookAhead == '@') {
                    this.lexer.consume(1);
                    strValueOf = String.valueOf(cLookAhead);
                } else if (isEscaped()) {
                    LexerCore lexerCore = this.lexer;
                    int i2 = lexerCore.ptr;
                    String strSubstring = lexerCore.buffer.substring(i2, i2 + 3);
                    this.lexer.consume(3);
                    strValueOf = strSubstring;
                }
            } catch (Exception unused) {
            }
            if (strValueOf != null) {
                stringBuffer.append(strValueOf);
            } else {
                if (this.lexer.lookAhead(0) != '[') {
                    try {
                        return new GenericURI(stringBuffer.toString());
                    } catch (ParseException e) {
                        throw createParseException(e.getMessage());
                    }
                }
                stringBuffer.append(new HostNameParser((Lexer) this.lexer).hostPort(false).encode());
            }
        }
    }

    public final String user() {
        int i = this.lexer.ptr;
        while (this.lexer.hasMoreChars()) {
            char cLookAhead = this.lexer.lookAhead(0);
            if (!isUnreserved(cLookAhead)) {
                if (!(cLookAhead == '#' || cLookAhead == '$' || cLookAhead == '&' || cLookAhead == '/' || cLookAhead == ';' || cLookAhead == '=' || cLookAhead == '?' || cLookAhead == '+' || cLookAhead == ',')) {
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
