package gov.nist.javax.sip.parser;

import gov.nist.javax.sip.parser.extensions.JoinParser;
import gov.nist.javax.sip.parser.extensions.MinSEParser;
import gov.nist.javax.sip.parser.extensions.ReferencesParser;
import gov.nist.javax.sip.parser.extensions.ReferredByParser;
import gov.nist.javax.sip.parser.extensions.ReplacesParser;
import gov.nist.javax.sip.parser.extensions.SessionExpiresParser;
import gov.nist.javax.sip.parser.ims.PAccessNetworkInfoParser;
import gov.nist.javax.sip.parser.ims.PAssertedIdentityParser;
import gov.nist.javax.sip.parser.ims.PAssociatedURIParser;
import gov.nist.javax.sip.parser.ims.PCalledPartyIDParser;
import gov.nist.javax.sip.parser.ims.PChargingFunctionAddressesParser;
import gov.nist.javax.sip.parser.ims.PChargingVectorParser;
import gov.nist.javax.sip.parser.ims.PMediaAuthorizationParser;
import gov.nist.javax.sip.parser.ims.PPreferredIdentityParser;
import gov.nist.javax.sip.parser.ims.PVisitedNetworkIDParser;
import gov.nist.javax.sip.parser.ims.PathParser;
import gov.nist.javax.sip.parser.ims.PrivacyParser;
import gov.nist.javax.sip.parser.ims.SecurityClientParser;
import gov.nist.javax.sip.parser.ims.SecurityServerParser;
import gov.nist.javax.sip.parser.ims.SecurityVerifyParser;
import gov.nist.javax.sip.parser.ims.ServiceRouteParser;
import java.util.Hashtable;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class ParserFactory {
    public static final Class[] constructorArgs;
    public static final Hashtable parserConstructorCache;
    public static final Hashtable parserTable;

    static {
        Hashtable hashtable = new Hashtable();
        parserTable = hashtable;
        parserConstructorCache = new Hashtable();
        constructorArgs = new Class[]{String.class};
        ParserFactory$$ExternalSyntheticOutline0.m275m("Reply-To", hashtable, ReplyToParser.class, "In-Reply-To", InReplyToParser.class);
        ParserFactory$$ExternalSyntheticOutline0.m275m("Accept-Encoding", hashtable, AcceptEncodingParser.class, "Accept-Language", AcceptLanguageParser.class);
        hashtable.put("t", ToParser.class);
        hashtable.put("To".toLowerCase(), ToParser.class);
        hashtable.put("From".toLowerCase(), FromParser.class);
        hashtable.put("f", FromParser.class);
        ParserFactory$$ExternalSyntheticOutline0.m275m("CSeq", hashtable, CSeqParser.class, "Via", ViaParser.class);
        hashtable.put("v", ViaParser.class);
        hashtable.put("Contact".toLowerCase(), ContactParser.class);
        hashtable.put("m", ContactParser.class);
        hashtable.put("Content-Type".toLowerCase(), ContentTypeParser.class);
        hashtable.put("c", ContentTypeParser.class);
        hashtable.put("Content-Length".toLowerCase(), ContentLengthParser.class);
        hashtable.put("l", ContentLengthParser.class);
        hashtable.put("Authorization".toLowerCase(), AuthorizationParser.class);
        ParserFactory$$ExternalSyntheticOutline0.m275m("WWW-Authenticate", hashtable, WWWAuthenticateParser.class, "Call-ID", CallIDParser.class);
        hashtable.put("i", CallIDParser.class);
        hashtable.put("Route".toLowerCase(), RouteParser.class);
        ParserFactory$$ExternalSyntheticOutline0.m275m("Record-Route", hashtable, RecordRouteParser.class, "Date", DateParser.class);
        ParserFactory$$ExternalSyntheticOutline0.m275m("Proxy-Authorization", hashtable, ProxyAuthorizationParser.class, "Proxy-Authenticate", ProxyAuthenticateParser.class);
        ParserFactory$$ExternalSyntheticOutline0.m275m("Retry-After", hashtable, RetryAfterParser.class, "Require", RequireParser.class);
        ParserFactory$$ExternalSyntheticOutline0.m275m("Proxy-Require", hashtable, ProxyRequireParser.class, "Timestamp", TimeStampParser.class);
        ParserFactory$$ExternalSyntheticOutline0.m275m("Unsupported", hashtable, UnsupportedParser.class, "User-Agent", UserAgentParser.class);
        hashtable.put("Supported".toLowerCase(), SupportedParser.class);
        hashtable.put("k", SupportedParser.class);
        ParserFactory$$ExternalSyntheticOutline0.m275m("Server", hashtable, ServerParser.class, "Subject", SubjectParser.class);
        hashtable.put("s", SubjectParser.class);
        hashtable.put("Subscription-State".toLowerCase(), SubscriptionStateParser.class);
        ParserFactory$$ExternalSyntheticOutline0.m275m("Max-Forwards", hashtable, MaxForwardsParser.class, "MIME-Version", MimeVersionParser.class);
        ParserFactory$$ExternalSyntheticOutline0.m275m("Min-Expires", hashtable, MinExpiresParser.class, "Organization", OrganizationParser.class);
        ParserFactory$$ExternalSyntheticOutline0.m275m("Priority", hashtable, PriorityParser.class, "RAck", RAckParser.class);
        ParserFactory$$ExternalSyntheticOutline0.m275m("RSeq", hashtable, RSeqParser.class, "Reason", ReasonParser.class);
        ParserFactory$$ExternalSyntheticOutline0.m275m("Warning", hashtable, WarningParser.class, "Expires", ExpiresParser.class);
        hashtable.put("Event".toLowerCase(), EventParser.class);
        hashtable.put("o", EventParser.class);
        ParserFactory$$ExternalSyntheticOutline0.m275m("Error-Info", hashtable, ErrorInfoParser.class, "Content-Language", ContentLanguageParser.class);
        hashtable.put("Content-Encoding".toLowerCase(), ContentEncodingParser.class);
        hashtable.put("e", ContentEncodingParser.class);
        ParserFactory$$ExternalSyntheticOutline0.m275m("Content-Disposition", hashtable, ContentDispositionParser.class, "Call-Info", CallInfoParser.class);
        ParserFactory$$ExternalSyntheticOutline0.m275m("Authentication-Info", hashtable, AuthenticationInfoParser.class, "Allow", AllowParser.class);
        hashtable.put("Allow-Events".toLowerCase(), AllowEventsParser.class);
        hashtable.put("u", AllowEventsParser.class);
        ParserFactory$$ExternalSyntheticOutline0.m275m("Alert-Info", hashtable, AlertInfoParser.class, "Accept", AcceptParser.class);
        hashtable.put("Refer-To".toLowerCase(), ReferToParser.class);
        hashtable.put("r", ReferToParser.class);
        ParserFactory$$ExternalSyntheticOutline0.m275m("SIP-ETag", hashtable, SIPETagParser.class, "SIP-If-Match", SIPIfMatchParser.class);
        ParserFactory$$ExternalSyntheticOutline0.m275m("P-Access-Network-Info", hashtable, PAccessNetworkInfoParser.class, "P-Asserted-Identity", PAssertedIdentityParser.class);
        ParserFactory$$ExternalSyntheticOutline0.m275m("P-Preferred-Identity", hashtable, PPreferredIdentityParser.class, "P-Charging-Vector", PChargingVectorParser.class);
        ParserFactory$$ExternalSyntheticOutline0.m275m("P-Charging-Function-Addresses", hashtable, PChargingFunctionAddressesParser.class, "P-Media-Authorization", PMediaAuthorizationParser.class);
        ParserFactory$$ExternalSyntheticOutline0.m275m("Path", hashtable, PathParser.class, "Privacy", PrivacyParser.class);
        ParserFactory$$ExternalSyntheticOutline0.m275m("Service-Route", hashtable, ServiceRouteParser.class, "P-Visited-Network-ID", PVisitedNetworkIDParser.class);
        ParserFactory$$ExternalSyntheticOutline0.m275m("P-Associated-URI", hashtable, PAssociatedURIParser.class, "P-Called-Party-ID", PCalledPartyIDParser.class);
        ParserFactory$$ExternalSyntheticOutline0.m275m("Security-Server", hashtable, SecurityServerParser.class, "Security-Client", SecurityClientParser.class);
        ParserFactory$$ExternalSyntheticOutline0.m275m("Security-Verify", hashtable, SecurityVerifyParser.class, "Referred-By", ReferredByParser.class);
        hashtable.put("b", ReferToParser.class);
        hashtable.put("Session-Expires".toLowerCase(), SessionExpiresParser.class);
        hashtable.put("x", SessionExpiresParser.class);
        hashtable.put("Min-SE".toLowerCase(), MinSEParser.class);
        ParserFactory$$ExternalSyntheticOutline0.m275m("Replaces", hashtable, ReplacesParser.class, "Join", JoinParser.class);
        hashtable.put("References".toLowerCase(), ReferencesParser.class);
    }
}
