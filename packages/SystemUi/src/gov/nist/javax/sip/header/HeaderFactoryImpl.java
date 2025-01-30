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

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class HeaderFactoryImpl {
    public HeaderFactoryImpl() {
        Boolean.getBoolean("gov.nist.core.STRIP_ADDR_SCOPES");
    }

    /* JADX WARN: Removed duplicated region for block: B:38:0x00a7 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:78:0x009a A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static Header createHeader(String str, String str2) {
        int indexOf;
        String trim;
        String substring;
        HeaderParser headerParser;
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(str);
        stringBuffer.append(":");
        stringBuffer.append(str2);
        String stringBuffer2 = stringBuffer.toString();
        new StringMsgParser();
        String trim2 = stringBuffer2.trim();
        int length = trim2.length() - 1;
        int i = 0;
        while (trim2.charAt(i) <= ' ') {
            try {
                i++;
            } catch (ArrayIndexOutOfBoundsException unused) {
                throw new ParseException("Empty header.", 0);
            }
        }
        while (trim2.charAt(length) <= ' ') {
            length--;
        }
        StringBuffer stringBuffer3 = new StringBuffer(length + 1);
        boolean z = false;
        int i2 = i;
        while (i <= length) {
            char charAt = trim2.charAt(i);
            if (charAt == '\r' || charAt == '\n') {
                if (!z) {
                    stringBuffer3.append(trim2.substring(i2, i));
                    z = true;
                }
            } else if (z) {
                if (charAt == ' ' || charAt == '\t') {
                    stringBuffer3.append(' ');
                    i2 = i + 1;
                    z = false;
                } else {
                    z = false;
                    i2 = i;
                }
            }
            i++;
        }
        stringBuffer3.append(trim2.substring(i2, i));
        stringBuffer3.append('\n');
        String stringBuffer4 = stringBuffer3.toString();
        Hashtable hashtable = ParserFactory.parserTable;
        int i3 = Lexer.$r8$clinit;
        if (stringBuffer4 != null) {
            try {
                indexOf = stringBuffer4.indexOf(":");
            } catch (IndexOutOfBoundsException unused2) {
            }
            if (indexOf >= 1) {
                trim = stringBuffer4.substring(0, indexOf).trim();
                if (stringBuffer4 != null) {
                    try {
                        substring = stringBuffer4.substring(stringBuffer4.indexOf(":") + 1);
                    } catch (IndexOutOfBoundsException unused3) {
                    }
                    if (trim != null || substring == null) {
                        throw new ParseException("The header name or value is null", 0);
                    }
                    Class cls = (Class) ParserFactory.parserTable.get(SIPHeaderNamesCache.toLowerCase(trim));
                    if (cls != null) {
                        try {
                            Hashtable hashtable2 = ParserFactory.parserConstructorCache;
                            Constructor constructor = (Constructor) hashtable2.get(cls);
                            if (constructor == null) {
                                constructor = cls.getConstructor(ParserFactory.constructorArgs);
                                hashtable2.put(cls, constructor);
                            }
                            headerParser = (HeaderParser) constructor.newInstance(stringBuffer4);
                        } catch (Exception e) {
                            InternalErrorHandler.handleException(e);
                            throw null;
                        }
                    } else {
                        headerParser = new HeaderParser(stringBuffer4);
                    }
                    if (headerParser == null) {
                        throw new ParseException("could not create parser", 0);
                    }
                    Header parse = headerParser.parse();
                    if (parse instanceof SIPHeaderList) {
                        SIPHeaderList sIPHeaderList = (SIPHeaderList) parse;
                        if (sIPHeaderList.size() > 1) {
                            throw new ParseException("Only singleton allowed ".concat(stringBuffer2), 0);
                        }
                        if (sIPHeaderList.size() != 0) {
                            return sIPHeaderList.getFirst();
                        }
                        try {
                            parse = (Header) ((SIPHeaderList) parse).getMyClass().newInstance();
                        } catch (IllegalAccessException e2) {
                            e2.printStackTrace();
                            return null;
                        } catch (InstantiationException e3) {
                            e3.printStackTrace();
                            return null;
                        }
                    }
                    return parse;
                }
                substring = null;
                if (trim != null) {
                }
                throw new ParseException("The header name or value is null", 0);
            }
        }
        trim = null;
        if (stringBuffer4 != null) {
        }
        substring = null;
        if (trim != null) {
        }
        throw new ParseException("The header name or value is null", 0);
    }
}
