package gov.nist.javax.sip.header;

import gov.nist.core.InternalErrorHandler;
import gov.nist.javax.sip.parser.HeaderParser;
import gov.nist.javax.sip.parser.Lexer;
import gov.nist.javax.sip.parser.ParserFactory;
import gov.nist.javax.sip.parser.StringMsgParser;
import java.lang.reflect.Constructor;
import java.text.ParseException;
import java.util.Hashtable;
import javax.sip.header.Header;

/* loaded from: classes4.dex */
public class HeaderFactoryImpl {
    public HeaderFactoryImpl() {
        Boolean.getBoolean("gov.nist.core.STRIP_ADDR_SCOPES");
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x0084  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static Header createHeader(String str, String str2) throws NoSuchMethodException, SecurityException, ParseException {
        String strTrim;
        String strSubstring;
        HeaderParser headerParser;
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(str);
        stringBuffer.append(":");
        stringBuffer.append(str2);
        String string = stringBuffer.toString();
        new StringMsgParser();
        String strTrim2 = string.trim();
        int length = strTrim2.length() - 1;
        int i = 0;
        while (strTrim2.charAt(i) <= ' ') {
            try {
                i++;
            } catch (ArrayIndexOutOfBoundsException unused) {
                throw new ParseException("Empty header.", 0);
            }
        }
        while (strTrim2.charAt(length) <= ' ') {
            length--;
        }
        StringBuffer stringBuffer2 = new StringBuffer(length + 1);
        boolean z = false;
        int i2 = i;
        while (i <= length) {
            char cCharAt = strTrim2.charAt(i);
            if (cCharAt == '\r' || cCharAt == '\n') {
                if (!z) {
                    stringBuffer2.append(strTrim2.substring(i2, i));
                    z = true;
                }
            } else if (z) {
                if (cCharAt == ' ' || cCharAt == '\t') {
                    stringBuffer2.append(' ');
                    i2 = i + 1;
                    z = false;
                } else {
                    z = false;
                    i2 = i;
                }
            }
            i++;
        }
        stringBuffer2.append(strTrim2.substring(i2, i));
        stringBuffer2.append('\n');
        String string2 = stringBuffer2.toString();
        Hashtable hashtable = ParserFactory.parserTable;
        int i3 = Lexer.$r8$clinit;
        if (string2 == null) {
            strTrim = null;
        } else {
            try {
                int iIndexOf = string2.indexOf(":");
                if (iIndexOf >= 1) {
                    strTrim = string2.substring(0, iIndexOf).trim();
                }
            } catch (IndexOutOfBoundsException unused2) {
            }
        }
        if (string2 == null) {
            strSubstring = null;
        } else {
            try {
                strSubstring = string2.substring(string2.indexOf(":") + 1);
            } catch (IndexOutOfBoundsException unused3) {
            }
        }
        if (strTrim == null || strSubstring == null) {
            throw new ParseException("The header name or value is null", 0);
        }
        Class cls = (Class) ParserFactory.parserTable.get(SIPHeaderNamesCache.toLowerCase(strTrim));
        if (cls != null) {
            try {
                Hashtable hashtable2 = ParserFactory.parserConstructorCache;
                Constructor constructor = (Constructor) hashtable2.get(cls);
                if (constructor == null) {
                    constructor = cls.getConstructor(ParserFactory.constructorArgs);
                    hashtable2.put(cls, constructor);
                }
                headerParser = (HeaderParser) constructor.newInstance(string2);
            } catch (Exception e) {
                InternalErrorHandler.handleException(e);
                throw null;
            }
        } else {
            headerParser = new HeaderParser(string2);
        }
        if (headerParser == null) {
            throw new ParseException("could not create parser", 0);
        }
        Header header = headerParser.parse();
        if (header instanceof SIPHeaderList) {
            SIPHeaderList sIPHeaderList = (SIPHeaderList) header;
            if (sIPHeaderList.hlist.size() > 1) {
                throw new ParseException("Only singleton allowed ".concat(string), 0);
            }
            if (sIPHeaderList.hlist.size() != 0) {
                return sIPHeaderList.mo3438getFirst();
            }
            try {
                header = (Header) ((SIPHeaderList) header).getMyClass().newInstance();
            } catch (IllegalAccessException e2) {
                e2.printStackTrace();
                return null;
            } catch (InstantiationException e3) {
                e3.printStackTrace();
                return null;
            }
        }
        return header;
    }
}
