package android.net;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/* loaded from: classes3.dex */
public class MailTo {
    private static final String BODY = "body";
    private static final String CC = "cc";
    public static final String MAILTO_SCHEME = "mailto:";
    private static final String SUBJECT = "subject";
    private static final String TO = "to";
    private HashMap<String, String> mHeaders = new HashMap<>();

    public static boolean isMailTo(String str) {
        return str != null && str.startsWith("mailto:");
    }

    public static MailTo parse(String str) throws ParseException {
        str.getClass();
        if (!isMailTo(str)) {
            throw new ParseException("Not a mailto scheme");
        }
        Uri uri = Uri.parse(str.substring(7));
        MailTo mailTo = new MailTo();
        String query = uri.getQuery();
        if (query != null) {
            for (String str2 : query.split("&")) {
                String[] strArrSplit = str2.split("=");
                if (strArrSplit.length != 0) {
                    mailTo.mHeaders.put(Uri.decode(strArrSplit[0]).toLowerCase(Locale.ROOT), strArrSplit.length > 1 ? Uri.decode(strArrSplit[1]) : null);
                }
            }
        }
        String path = uri.getPath();
        if (path != null) {
            String to = mailTo.getTo();
            if (to != null) {
                path = path + ", " + to;
            }
            mailTo.mHeaders.put(TO, path);
        }
        return mailTo;
    }

    public String getTo() {
        return this.mHeaders.get(TO);
    }

    public String getCc() {
        return this.mHeaders.get(CC);
    }

    public String getSubject() {
        return this.mHeaders.get("subject");
    }

    public String getBody() {
        return this.mHeaders.get("body");
    }

    public Map<String, String> getHeaders() {
        return this.mHeaders;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("mailto:?");
        for (Map.Entry<String, String> entry : this.mHeaders.entrySet()) {
            sb.append(Uri.encode(entry.getKey()));
            sb.append('=');
            sb.append(Uri.encode(entry.getValue()));
            sb.append('&');
        }
        return sb.toString();
    }

    private MailTo() {
    }
}
