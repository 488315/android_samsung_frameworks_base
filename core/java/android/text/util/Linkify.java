package android.text.util;

import android.app.ActivityThread;
import android.content.Context;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.media.MediaMetrics;
import android.telephony.PhoneNumberUtils;
import android.telephony.TelephonyManager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.text.style.URLSpan;
import android.util.EventLog;
import android.util.Log;
import android.util.Patterns;
import android.webkit.WebView;
import android.widget.TextView;
import com.android.i18n.phonenumbers.PhoneNumberMatch;
import com.android.i18n.phonenumbers.PhoneNumberUtil;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Locale;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import libcore.util.EmptyArray;

/* loaded from: classes3.dex */
public class Linkify {

    @Deprecated
    public static final int ALL = 15;
    public static final int EMAIL_ADDRESSES = 2;
    private static final char KOR_CURRENCY_SYMBOL = 8361;
    private static final char KOR_CURRENCY_WON = 50896;
    private static final String LOG_TAG = "Linkify";

    @Deprecated
    public static final int MAP_ADDRESSES = 8;
    public static final int PHONE_NUMBERS = 4;
    private static final int PHONE_NUMBER_MINIMUM_DIGITS = 5;

    @Deprecated(forRemoval = true, since = "15.5")
    public static final int SEM_ALL_MIXED_CJK = 20490;

    @Deprecated(forRemoval = true, since = "15.5")
    public static final int SEM_ALL_MIXED_KOR = 24586;

    @Deprecated(forRemoval = true, since = "15.5")
    public static final int SEM_PHONE_NUMBERS_CJK = 32768;

    @Deprecated(forRemoval = true, since = "15.5")
    public static final int SEM_PHONE_NUMBERS_KOR = 16384;

    @Deprecated(forRemoval = true, since = "15.5")
    public static final int SEM_WEB_URLS_CJK = 4096;

    @Deprecated(forRemoval = true, since = "15.5")
    public static final int SEM_WEB_URLS_KOR = 8192;
    public static final int WEB_URLS = 1;
    public static final MatchFilter sUrlMatchFilter = new MatchFilter() { // from class: android.text.util.Linkify.1
        @Override // android.text.util.Linkify.MatchFilter
        public final boolean acceptMatch(CharSequence s, int start, int end) {
            if (start == 0 || s.charAt(start - 1) != '@') {
                return true;
            }
            return false;
        }
    };
    public static final MatchFilter sPhoneNumberMatchFilter = new MatchFilter() { // from class: android.text.util.Linkify.2
        @Override // android.text.util.Linkify.MatchFilter
        public final boolean acceptMatch(CharSequence s, int start, int end) {
            int digitCount = 0;
            for (int i = start; i < end; i++) {
                if (Character.isDigit(s.charAt(i)) && (digitCount = digitCount + 1) >= 5) {
                    return true;
                }
            }
            return false;
        }
    };
    public static final TransformFilter sPhoneNumberTransformFilter = new TransformFilter() { // from class: android.text.util.Linkify.3
        @Override // android.text.util.Linkify.TransformFilter
        public final String transformUrl(Matcher match, String url) {
            return Patterns.digitsAndPlusOnly(match);
        }
    };
    private static final Function<String, URLSpan> DEFAULT_SPAN_FACTORY = new Function() { // from class: android.text.util.Linkify$$ExternalSyntheticLambda0
        @Override // java.util.function.Function
        public final Object apply(Object obj) {
            return Linkify.lambda$static$0((String) obj);
        }
    };

    @Retention(RetentionPolicy.SOURCE)
    public @interface LinkifyMask {
    }

    public interface MatchFilter {
        boolean acceptMatch(CharSequence charSequence, int i, int i2);
    }

    public interface TransformFilter {
        String transformUrl(Matcher matcher, String str);
    }

    public static final boolean addLinks(Spannable text, int mask) {
        return addLinks(text, mask, null, null);
    }

    public static final boolean addLinks(Spannable text, int mask, Function<String, URLSpan> urlSpanFactory) {
        return addLinks(text, mask, null, urlSpanFactory);
    }

    private static boolean addLinks(Spannable text, int mask, Context context, Function<String, URLSpan> urlSpanFactory) {
        String str;
        String str2;
        String str3;
        Spannable spannable;
        Iterator<LinkSpec> it;
        if (text != null && containsUnsupportedCharacters(text.toString())) {
            EventLog.writeEvent(1397638484, "116321860", -1, "");
            return false;
        }
        if (mask == 0) {
            return false;
        }
        URLSpan[] old = (URLSpan[]) text.getSpans(0, text.length(), URLSpan.class);
        for (int i = old.length - 1; i >= 0; i--) {
            text.removeSpan(old[i]);
        }
        ArrayList<LinkSpec> links = new ArrayList<>();
        if ((mask & 1) != 0) {
            gatherLinks(links, text, Patterns.AUTOLINK_WEB_URL, new String[]{"http://", "https://", "rtsp://", "ftp://"}, sUrlMatchFilter, null);
            Iterator<LinkSpec> it2 = links.iterator();
            while (it2.hasNext()) {
                checkBracketsPairs(text.toString(), it2.next());
            }
        }
        if ((mask & 4096) == 0) {
            str = "www.";
            str2 = "://";
            str3 = MediaMetrics.SEPARATOR;
        } else {
            MatchFilter matchFilter = sUrlMatchFilter;
            str = "www.";
            str2 = "://";
            str3 = MediaMetrics.SEPARATOR;
            gatherLinks(links, text, Patterns.WEB_URL_EX, new String[]{"http://", "https://", "rtsp://", "ftp://"}, matchFilter, null);
            Iterator<LinkSpec> it3 = links.iterator();
            while (it3.hasNext()) {
                LinkSpec link = it3.next();
                String linkedText = text.toString().substring(link.start, link.end).toLowerCase();
                if (linkedText.contains(str) && !linkedText.startsWith(str) && !linkedText.startsWith("http://") && !linkedText.startsWith("https://") && !linkedText.startsWith("rtsp://") && !linkedText.startsWith("ftp://")) {
                    link.start += linkedText.indexOf(str);
                    link.url = link.url.substring(0, link.url.indexOf(str2) + 3) + text.toString().substring(link.start, link.end);
                } else if (linkedText.contains("wap.") && !linkedText.startsWith("wap.") && !linkedText.startsWith("http://") && !linkedText.startsWith("https://") && !linkedText.startsWith("rtsp://") && !linkedText.startsWith("ftp://")) {
                    link.start += linkedText.indexOf("wap.");
                    link.url = link.url.substring(0, link.url.indexOf(str2) + 3) + text.toString().substring(link.start, link.end);
                }
                int position = linkedText.lastIndexOf(str3);
                if (position < 0 || position >= linkedText.length() - 1) {
                    it = it3;
                } else if (linkedText.startsWith("http://api.map.baidu.com/marker?location=")) {
                    it = it3;
                } else {
                    char[] chars = linkedText.substring(position + 1).toCharArray();
                    int i2 = 0;
                    while (true) {
                        if (i2 >= chars.length) {
                            it = it3;
                            break;
                        }
                        it = it3;
                        if (chars[i2] >= 128) {
                            break;
                        }
                        i2++;
                        it3 = it;
                    }
                    if (i2 < chars.length) {
                        link.end -= chars.length - i2;
                        link.url = link.url.substring(0, link.url.length() - (chars.length - i2));
                    }
                }
                checkBracketsPairs(text.toString(), link);
                it3 = it;
            }
        }
        if ((mask & 8192) != 0) {
            gatherLinks(links, text, Patterns.AUTOLINK_WEB_URL_KR, new String[]{"http://", "https://", "rtsp://", "ftp://"}, sUrlMatchFilter, null);
            int j = 0;
            while (j < links.size()) {
                LinkSpec link2 = links.get(j);
                String linkedText2 = text.toString().substring(link2.start, link2.end).toLowerCase();
                if (linkedText2.endsWith(".ht") && text.length() >= link2.end + 2 && "tp".equalsIgnoreCase(text.toString().substring(link2.end, link2.end + 2))) {
                    if (links.size() > j + 1) {
                        LinkSpec linkNext = links.get(j + 1);
                        if ((linkNext.start == link2.end + 6 && text.length() > link2.end + 5 && "tp://".equalsIgnoreCase(text.toString().substring(link2.end, link2.end + 5))) || (linkNext.start == link2.end + 7 && text.length() > link2.end + 6 && "tps://".equalsIgnoreCase(text.toString().substring(link2.end, link2.end + 6)))) {
                            linkNext.start = link2.end - 2;
                            linkNext.url = text.toString().substring(linkNext.start, linkNext.end);
                            links.set(j + 1, linkNext);
                        }
                    }
                    links.remove(link2);
                    if (j > 0) {
                        j--;
                    }
                } else {
                    if (linkedText2.contains(str) && !linkedText2.startsWith(str) && !linkedText2.startsWith("http://") && !linkedText2.startsWith("https://") && !linkedText2.startsWith("rtsp://") && !linkedText2.startsWith("ftp://")) {
                        link2.start += linkedText2.indexOf(str);
                        link2.url = link2.url.substring(0, link2.url.indexOf(str2) + 3) + text.toString().substring(link2.start, link2.end);
                    }
                    int position2 = linkedText2.lastIndexOf(str3);
                    if (position2 >= 0 && position2 < linkedText2.length() - 1) {
                        char[] chars2 = linkedText2.substring(position2 + 1).toCharArray();
                        int i3 = 0;
                        while (i3 < chars2.length) {
                            String linkedText3 = linkedText2;
                            if (chars2[i3] >= 128) {
                                break;
                            }
                            i3++;
                            linkedText2 = linkedText3;
                        }
                        if (i3 < chars2.length && i3 > 0 && chars2[i3 - 1] != '/') {
                            link2.end -= chars2.length - i3;
                            link2.url = link2.url.substring(0, link2.url.length() - (chars2.length - i3));
                        }
                    }
                    checkBracketsPairs(text.toString(), link2);
                    j++;
                }
            }
        }
        int j2 = mask & 2;
        if (j2 != 0) {
            gatherLinks(links, text, Patterns.AUTOLINK_EMAIL_ADDRESS, new String[]{"mailto:"}, null, null);
        }
        if ((49156 & mask) == 0) {
            spannable = text;
        } else {
            spannable = text;
            gatherTelLinks(links, spannable, context);
        }
        if ((mask & 8) != 0) {
            gatherMapLinks(links, spannable);
        }
        pruneOverlaps(links);
        if (links.size() == 0) {
            return false;
        }
        Iterator<LinkSpec> it4 = links.iterator();
        while (it4.hasNext()) {
            LinkSpec link3 = it4.next();
            applyLink(link3.url, link3.start, link3.end, spannable, urlSpanFactory);
        }
        return true;
    }

    public static boolean containsUnsupportedCharacters(String text) {
        if (text.contains("\u202c")) {
            Log.m96e(LOG_TAG, "Unsupported character for applying links: u202C");
            return true;
        }
        if (text.contains("\u202d")) {
            Log.m96e(LOG_TAG, "Unsupported character for applying links: u202D");
            return true;
        }
        if (text.contains("\u202e")) {
            Log.m96e(LOG_TAG, "Unsupported character for applying links: u202E");
            return true;
        }
        return false;
    }

    public static final boolean addLinks(TextView text, int mask) {
        if (mask == 0) {
            return false;
        }
        Context context = text.getContext();
        CharSequence t = text.getText();
        if (t instanceof Spannable) {
            if (!addLinks((Spannable) t, mask, context, null)) {
                return false;
            }
            addLinkMovementMethod(text);
            return true;
        }
        SpannableString s = SpannableString.valueOf(t);
        if (!addLinks(s, mask, context, null)) {
            return false;
        }
        addLinkMovementMethod(text);
        text.setText(s);
        return true;
    }

    private static final void addLinkMovementMethod(TextView t) {
        MovementMethod m = t.getMovementMethod();
        if ((m == null || !(m instanceof LinkMovementMethod)) && t.getLinksClickable()) {
            t.setMovementMethod(LinkMovementMethod.getInstance());
        }
    }

    public static final void addLinks(TextView text, Pattern pattern, String scheme) {
        addLinks(text, pattern, scheme, (String[]) null, (MatchFilter) null, (TransformFilter) null);
    }

    public static final void addLinks(TextView text, Pattern pattern, String scheme, MatchFilter matchFilter, TransformFilter transformFilter) {
        addLinks(text, pattern, scheme, (String[]) null, matchFilter, transformFilter);
    }

    public static final void addLinks(TextView text, Pattern pattern, String defaultScheme, String[] schemes, MatchFilter matchFilter, TransformFilter transformFilter) {
        SpannableString spannable = SpannableString.valueOf(text.getText());
        boolean linksAdded = addLinks(spannable, pattern, defaultScheme, schemes, matchFilter, transformFilter);
        if (linksAdded) {
            text.setText(spannable);
            addLinkMovementMethod(text);
        }
    }

    public static final boolean addLinks(Spannable text, Pattern pattern, String scheme) {
        return addLinks(text, pattern, scheme, (String[]) null, (MatchFilter) null, (TransformFilter) null);
    }

    public static final boolean addLinks(Spannable spannable, Pattern pattern, String scheme, MatchFilter matchFilter, TransformFilter transformFilter) {
        return addLinks(spannable, pattern, scheme, (String[]) null, matchFilter, transformFilter);
    }

    public static final boolean addLinks(Spannable spannable, Pattern pattern, String defaultScheme, String[] schemes, MatchFilter matchFilter, TransformFilter transformFilter) {
        return addLinks(spannable, pattern, defaultScheme, schemes, matchFilter, transformFilter, null);
    }

    public static final boolean addLinks(Spannable spannable, Pattern pattern, String defaultScheme, String[] schemes, MatchFilter matchFilter, TransformFilter transformFilter, Function<String, URLSpan> urlSpanFactory) {
        if (spannable != null && containsUnsupportedCharacters(spannable.toString())) {
            EventLog.writeEvent(1397638484, "116321860", -1, "");
            return false;
        }
        if (defaultScheme == null) {
            defaultScheme = "";
        }
        if (schemes == null || schemes.length < 1) {
            schemes = EmptyArray.STRING;
        }
        String[] schemesCopy = new String[schemes.length + 1];
        schemesCopy[0] = defaultScheme.toLowerCase(Locale.ROOT);
        for (int index = 0; index < schemes.length; index++) {
            String scheme = schemes[index];
            schemesCopy[index + 1] = scheme == null ? "" : scheme.toLowerCase(Locale.ROOT);
        }
        boolean hasMatches = false;
        Matcher m = pattern.matcher(spannable);
        while (m.find()) {
            int start = m.start();
            int end = m.end();
            boolean allowed = true;
            if (matchFilter != null) {
                allowed = matchFilter.acceptMatch(spannable, start, end);
            }
            if (allowed) {
                String url = makeUrl(m.group(0), schemesCopy, m, transformFilter);
                applyLink(url, start, end, spannable, urlSpanFactory);
                hasMatches = true;
            }
        }
        return hasMatches;
    }

    private static void applyLink(String url, int start, int end, Spannable text, Function<String, URLSpan> urlSpanFactory) {
        if (urlSpanFactory == null) {
            urlSpanFactory = DEFAULT_SPAN_FACTORY;
        }
        URLSpan span = urlSpanFactory.apply(url);
        text.setSpan(span, start, end, 33);
    }

    private static final String makeUrl(String url, String[] prefixes, Matcher matcher, TransformFilter filter) {
        if (filter != null) {
            url = filter.transformUrl(matcher, url);
        }
        boolean hasPrefix = false;
        int i = 0;
        while (true) {
            if (i >= prefixes.length) {
                break;
            }
            if (!url.regionMatches(true, 0, prefixes[i], 0, prefixes[i].length())) {
                i++;
            } else {
                hasPrefix = true;
                if (!url.regionMatches(false, 0, prefixes[i], 0, prefixes[i].length())) {
                    url = prefixes[i] + url.substring(prefixes[i].length());
                }
            }
        }
        if (!hasPrefix && prefixes.length > 0) {
            return prefixes[0] + url;
        }
        return url;
    }

    private static final void gatherLinks(ArrayList<LinkSpec> links, Spannable s, Pattern pattern, String[] schemes, MatchFilter matchFilter, TransformFilter transformFilter) {
        Matcher m = pattern.matcher(s);
        while (m.find()) {
            int start = m.start();
            int end = m.end();
            if (matchFilter == null || matchFilter.acceptMatch(s, start, end)) {
                LinkSpec spec = new LinkSpec();
                String url = makeUrl(m.group(0), schemes, m, transformFilter);
                spec.url = url;
                spec.start = start;
                spec.end = end;
                links.add(spec);
            }
        }
    }

    private static void gatherTelLinks(ArrayList<LinkSpec> links, Spannable s, Context context) {
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        Context ctx = context != null ? context : ActivityThread.currentApplication();
        String regionCode = ctx != null ? ((TelephonyManager) ctx.getSystemService(TelephonyManager.class)).getSimCountryIso().toUpperCase(Locale.US) : Locale.getDefault().getCountry();
        Iterable<PhoneNumberMatch> matches = phoneUtil.findNumbers(s.toString(), regionCode, PhoneNumberUtil.Leniency.POSSIBLE, Long.MAX_VALUE);
        for (PhoneNumberMatch match : matches) {
            LinkSpec spec = new LinkSpec();
            spec.url = WebView.SCHEME_TEL + PhoneNumberUtils.normalizeNumber(match.rawString());
            spec.start = match.start();
            spec.end = match.end();
            String rawString = match.rawString();
            if ((rawString.charAt(0) == '[' && !rawString.contains(NavigationBarInflaterView.SIZE_MOD_END)) || (rawString.charAt(0) == '(' && !rawString.contains(NavigationBarInflaterView.KEY_CODE_END))) {
                spec.start++;
            } else if (rawString.charAt(0) == '+' && rawString.charAt(1) == '+') {
                spec.start++;
            }
            if (!"KR".equals(regionCode) || needToAddLink(s.toString(), match.rawString(), spec.start, spec.end)) {
                links.add(spec);
            }
        }
    }

    private static final void gatherMapLinks(ArrayList<LinkSpec> links, Spannable s) {
        int start;
        String string = s.toString();
        int base = 0;
        while (true) {
            try {
                String address = WebView.findAddress(string);
                if (address != null && (start = string.indexOf(address)) >= 0) {
                    LinkSpec spec = new LinkSpec();
                    int length = address.length();
                    int end = start + length;
                    spec.start = base + start;
                    spec.end = base + end;
                    string = string.substring(end);
                    base += end;
                    try {
                        String encodedAddress = URLEncoder.encode(address, "UTF-8");
                        spec.url = WebView.SCHEME_GEO + encodedAddress;
                        links.add(spec);
                    } catch (UnsupportedEncodingException e) {
                    }
                }
                return;
            } catch (UnsupportedOperationException e2) {
                return;
            }
        }
    }

    private static final void pruneOverlaps(ArrayList<LinkSpec> links) {
        Comparator<LinkSpec> c = new Comparator<LinkSpec>() { // from class: android.text.util.Linkify.4
            @Override // java.util.Comparator
            public final int compare(LinkSpec a, LinkSpec b) {
                if (a.start < b.start) {
                    return -1;
                }
                if (a.start <= b.start && a.end >= b.end) {
                    return a.end > b.end ? -1 : 0;
                }
                return 1;
            }
        };
        Collections.sort(links, c);
        int len = links.size();
        int i = 0;
        while (i < len - 1) {
            LinkSpec a = links.get(i);
            LinkSpec b = links.get(i + 1);
            int remove = -1;
            if (a.start <= b.start && a.end > b.start) {
                if (b.end <= a.end) {
                    remove = i + 1;
                } else if (a.end - a.start > b.end - b.start) {
                    remove = i + 1;
                } else if (a.end - a.start < b.end - b.start) {
                    remove = i;
                }
                if (remove != -1) {
                    links.remove(remove);
                    len--;
                }
            }
            i++;
        }
    }

    static /* synthetic */ URLSpan lambda$static$0(String string) {
        return new URLSpan(string);
    }

    private static boolean needToAddLink(String rawStr, String matchStr, int start, int end) {
        if (matchStr.contains("/") || matchStr.contains("~")) {
            return false;
        }
        int length = rawStr.length();
        char charAfterMatch1 = 0;
        char charAfterMatch2 = 0;
        if (end < length) {
            charAfterMatch1 = rawStr.charAt(end);
            if (end + 1 < length) {
                charAfterMatch2 = rawStr.charAt(end + 1);
            }
        }
        if (charAfterMatch1 == 50896 || charAfterMatch1 == 8361 || (charAfterMatch1 == ' ' && (charAfterMatch2 == 50896 || charAfterMatch2 == 8361))) {
            return false;
        }
        if (PhoneNumberUtils.normalizeNumber(matchStr).length() != 3) {
            return true;
        }
        char charBeforeMatch = 0;
        if (start >= 1) {
            charBeforeMatch = rawStr.charAt(start - 1);
        }
        return charBeforeMatch != ',' && charAfterMatch1 != ',' && matchStr.charAt(0) == '1' && matchStr.charAt(1) == '1' && (matchStr.charAt(2) == '2' || matchStr.charAt(2) == '9');
    }

    private static void checkBracketsPairs(String rawStr, LinkSpec link) {
        int end = link.end - link.start;
        String matchStr = rawStr.substring(link.start, link.end);
        if (matchStr.charAt(end - 1) != ')') {
            return;
        }
        int bracketsCount = 0;
        for (int i = 0; i < end; i++) {
            if (matchStr.charAt(i) == ')') {
                bracketsCount++;
            } else if (matchStr.charAt(i) == '(') {
                bracketsCount--;
            }
        }
        if (bracketsCount > 0) {
            link.end -= bracketsCount;
            link.url = link.url.substring(0, link.url.length() - bracketsCount);
        }
    }
}
