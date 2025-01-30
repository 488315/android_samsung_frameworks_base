package gov.nist.javax.sip.parser;

import com.sec.ims.settings.ImsProfile;
import com.sec.ims.volte2.data.VolteConstants;
import gov.nist.core.LexerCore;
import java.util.Hashtable;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class Lexer extends LexerCore {
    public static final /* synthetic */ int $r8$clinit = 0;

    public Lexer(String str, String str2) {
        super(str, str2);
        selectLexer(str);
    }

    @Override // gov.nist.core.LexerCore
    public final void selectLexer(String str) {
        Hashtable hashtable = LexerCore.lexerTables;
        synchronized (hashtable) {
            Hashtable hashtable2 = (Hashtable) hashtable.get(str);
            this.currentLexer = hashtable2;
            if (hashtable2 == null) {
                Hashtable hashtable3 = (Hashtable) hashtable.get(str);
                this.currentLexer = hashtable3;
                if (hashtable3 == null) {
                    Hashtable hashtable4 = new Hashtable();
                    this.currentLexer = hashtable4;
                    hashtable.put(str, hashtable4);
                }
                if (str.equals("method_keywordLexer")) {
                    addKeyword(2052, "REGISTER");
                    addKeyword(2054, "ACK");
                    addKeyword(2056, "OPTIONS");
                    addKeyword(2055, "BYE");
                    addKeyword(2053, "INVITE");
                    addKeyword(2051, "sip");
                    addKeyword(2136, "sips");
                    addKeyword(VolteConstants.ErrorCode.CALL_NOT_ACCEPTABLE_DIVERT, "SUBSCRIBE");
                    addKeyword(VolteConstants.ErrorCode.NETWORK_UNREACHABLE, "NOTIFY");
                    addKeyword(2118, "MESSAGE");
                    addKeyword(2115, "PUBLISH");
                } else if (str.equals("command_keywordLexer")) {
                    addKeyword(2058, "Error-Info");
                    addKeyword(2113, "Allow-Events");
                    addKeyword(2112, "Authentication-Info");
                    addKeyword(2111, "Event");
                    addKeyword(2110, "Min-Expires");
                    addKeyword(2108, "RSeq");
                    addKeyword(2109, "RAck");
                    addKeyword(2107, "Reason");
                    addKeyword(VolteConstants.ErrorCode.SESSION_EXPIRED, "Reply-To");
                    addKeyword(2104, "Subscription-State");
                    addKeyword(2103, "Timestamp");
                    addKeyword(2059, "In-Reply-To");
                    addKeyword(2060, "MIME-Version");
                    addKeyword(2061, "Alert-Info");
                    addKeyword(2062, "From");
                    addKeyword(2063, "To");
                    addKeyword(2114, "Refer-To");
                    addKeyword(2064, "Via");
                    addKeyword(2065, "User-Agent");
                    addKeyword(2066, "Server");
                    addKeyword(2067, "Accept-Encoding");
                    addKeyword(2068, "Accept");
                    addKeyword(2069, "Allow");
                    addKeyword(2070, "Route");
                    addKeyword(2071, "Authorization");
                    addKeyword(2072, "Proxy-Authorization");
                    addKeyword(2073, "Retry-After");
                    addKeyword(2074, "Proxy-Require");
                    addKeyword(2075, "Content-Language");
                    addKeyword(2076, "Unsupported");
                    addKeyword(2068, "Supported");
                    addKeyword(2078, "Warning");
                    addKeyword(2079, "Max-Forwards");
                    addKeyword(2080, "Date");
                    addKeyword(2081, "Priority");
                    addKeyword(2082, "Proxy-Authenticate");
                    addKeyword(2083, "Content-Encoding");
                    addKeyword(2084, "Content-Length");
                    addKeyword(2085, "Subject");
                    addKeyword(2086, "Content-Type");
                    addKeyword(2087, "Contact");
                    addKeyword(2088, "Call-ID");
                    addKeyword(2089, "Require");
                    addKeyword(2090, "Expires");
                    addKeyword(2092, "Record-Route");
                    addKeyword(2093, "Organization");
                    addKeyword(2094, "CSeq");
                    addKeyword(2095, "Accept-Language");
                    addKeyword(2096, "WWW-Authenticate");
                    addKeyword(2099, "Call-Info");
                    addKeyword(2100, "Content-Disposition");
                    addKeyword(2068, ImsProfile.TIMER_NAME_K);
                    addKeyword(2086, ImsProfile.TIMER_NAME_C);
                    addKeyword(2083, ImsProfile.TIMER_NAME_E);
                    addKeyword(2062, ImsProfile.TIMER_NAME_F);
                    addKeyword(2088, ImsProfile.TIMER_NAME_I);
                    addKeyword(2087, "M");
                    addKeyword(2084, "L");
                    addKeyword(2085, "S");
                    addKeyword(2063, "T");
                    addKeyword(2113, "U");
                    addKeyword(2064, "V");
                    addKeyword(2114, "R");
                    addKeyword(2111, "O");
                    addKeyword(2133, "X");
                    addKeyword(2116, "SIP-ETag");
                    addKeyword(2117, "SIP-If-Match");
                    addKeyword(2133, "Session-Expires");
                    addKeyword(2134, "Min-SE");
                    addKeyword(2132, "Referred-By");
                    addKeyword(2135, "Replaces");
                    addKeyword(2140, "Join");
                    addKeyword(2119, "Path");
                    addKeyword(2120, "Service-Route");
                    addKeyword(2121, "P-Asserted-Identity");
                    addKeyword(2122, "P-Preferred-Identity");
                    addKeyword(2126, "Privacy");
                    addKeyword(2128, "P-Called-Party-ID");
                    addKeyword(2129, "P-Associated-URI");
                    addKeyword(2123, "P-Visited-Network-ID");
                    addKeyword(2124, "P-Charging-Function-Addresses");
                    addKeyword(2125, "P-Charging-Vector");
                    addKeyword(2127, "P-Access-Network-Info");
                    addKeyword(2130, "P-Media-Authorization");
                    addKeyword(2137, "Security-Server");
                    addKeyword(2139, "Security-Verify");
                    addKeyword(2138, "Security-Client");
                    addKeyword(2141, "P-User-Database");
                    addKeyword(2142, "P-Profile-Key");
                    addKeyword(2143, "P-Served-User");
                    addKeyword(2144, "P-Preferred-Service");
                    addKeyword(2145, "P-Asserted-Service");
                    addKeyword(2146, "References");
                } else if (str.equals("status_lineLexer")) {
                    addKeyword(2051, "sip");
                } else if (str.equals("request_lineLexer")) {
                    addKeyword(2051, "sip");
                } else if (str.equals("sip_urlLexer")) {
                    addKeyword(2105, "tel");
                    addKeyword(2051, "sip");
                    addKeyword(2136, "sips");
                }
            }
        }
    }
}
