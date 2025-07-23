package android.text.util;

import android.app.ActivityThread;
import android.content.Context;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.media.MediaMetrics;
import android.telephony.PhoneNumberUtils;
import android.telephony.TelephonyManager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
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

/* loaded from: classes4.dex */
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
        public final boolean acceptMatch(CharSequence charSequence, int i, int i2) {
            return i == 0 || charSequence.charAt(i - 1) != '@';
        }
    };
    public static final MatchFilter sPhoneNumberMatchFilter = new MatchFilter() { // from class: android.text.util.Linkify.2
        @Override // android.text.util.Linkify.MatchFilter
        public final boolean acceptMatch(CharSequence charSequence, int i, int i2) {
            int i3 = 0;
            while (i < i2) {
                if (Character.isDigit(charSequence.charAt(i)) && (i3 = i3 + 1) >= 5) {
                    return true;
                }
                i++;
            }
            return false;
        }
    };
    public static final TransformFilter sPhoneNumberTransformFilter = new TransformFilter() { // from class: android.text.util.Linkify.3
        @Override // android.text.util.Linkify.TransformFilter
        public final String transformUrl(Matcher matcher, String str) {
            return Patterns.digitsAndPlusOnly(matcher);
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

    public static final boolean addLinks(Spannable spannable, int i) {
        return addLinks(spannable, i, null, null);
    }

    public static final boolean addLinks(Spannable spannable, int i, Function<String, URLSpan> function) {
        return addLinks(spannable, i, null, function);
    }

    private static boolean addLinks(Spannable spannable, int i, Context context, Function<String, URLSpan> function) {
        boolean z;
        String str;
        Spannable spannable2;
        int i2 = 0;
        if (spannable != null && containsUnsupportedCharacters(spannable.toString())) {
            EventLog.writeEvent(1397638484, "116321860", -1, "");
            return false;
        }
        if (i == 0) {
            return false;
        }
        URLSpan[] uRLSpanArr = (URLSpan[]) spannable.getSpans(0, spannable.length(), URLSpan.class);
        for (int length = uRLSpanArr.length - 1; length >= 0; length--) {
            spannable.removeSpan(uRLSpanArr[length]);
        }
        ArrayList arrayList = new ArrayList();
        if ((i & 1) != 0) {
            gatherLinks(arrayList, spannable, Patterns.AUTOLINK_WEB_URL, new String[]{"http://", "https://", "rtsp://", "ftp://"}, sUrlMatchFilter, null);
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                checkBracketsPairs(spannable.toString(), (LinkSpec) it.next());
            }
        }
        if ((i & 4096) != 0) {
            z = true;
            str = "www.";
            gatherLinks(arrayList, spannable, Patterns.WEB_URL_EX, new String[]{"http://", "https://", "rtsp://", "ftp://"}, sUrlMatchFilter, null);
            Iterator it2 = arrayList.iterator();
            while (it2.hasNext()) {
                LinkSpec linkSpec = (LinkSpec) it2.next();
                String lowerCase = spannable.toString().substring(linkSpec.start, linkSpec.end).toLowerCase();
                if (lowerCase.contains(str) && !lowerCase.startsWith(str) && !lowerCase.startsWith("http://") && !lowerCase.startsWith("https://") && !lowerCase.startsWith("rtsp://") && !lowerCase.startsWith("ftp://")) {
                    linkSpec.start += lowerCase.indexOf(str);
                    linkSpec.url = linkSpec.url.substring(i2, linkSpec.url.indexOf("://") + 3) + spannable.toString().substring(linkSpec.start, linkSpec.end);
                } else if (lowerCase.contains("wap.") && !lowerCase.startsWith("wap.") && !lowerCase.startsWith("http://") && !lowerCase.startsWith("https://") && !lowerCase.startsWith("rtsp://") && !lowerCase.startsWith("ftp://")) {
                    linkSpec.start += lowerCase.indexOf("wap.");
                    linkSpec.url = linkSpec.url.substring(0, linkSpec.url.indexOf("://") + 3) + spannable.toString().substring(linkSpec.start, linkSpec.end);
                }
                int lastIndexOf = lowerCase.lastIndexOf(MediaMetrics.SEPARATOR);
                if (lastIndexOf >= 0 && lastIndexOf < lowerCase.length() - 1 && !lowerCase.startsWith("http://api.map.baidu.com/marker?location=")) {
                    char[] charArray = lowerCase.substring(lastIndexOf + 1).toCharArray();
                    int i3 = 0;
                    while (i3 < charArray.length && charArray[i3] < 128) {
                        i3++;
                    }
                    if (i3 < charArray.length) {
                        linkSpec.end -= charArray.length - i3;
                        linkSpec.url = linkSpec.url.substring(0, linkSpec.url.length() - (charArray.length - i3));
                    }
                }
                checkBracketsPairs(spannable.toString(), linkSpec);
                i2 = 0;
            }
        } else {
            z = true;
            str = "www.";
        }
        if ((i & 8192) != 0) {
            gatherLinks(arrayList, spannable, Patterns.AUTOLINK_WEB_URL_KR, new String[]{"http://", "https://", "rtsp://", "ftp://"}, sUrlMatchFilter, null);
            int i4 = 0;
            while (i4 < arrayList.size()) {
                LinkSpec linkSpec2 = (LinkSpec) arrayList.get(i4);
                String lowerCase2 = spannable.toString().substring(linkSpec2.start, linkSpec2.end).toLowerCase();
                if (lowerCase2.endsWith(".ht") && spannable.length() >= linkSpec2.end + 2 && "tp".equalsIgnoreCase(spannable.toString().substring(linkSpec2.end, linkSpec2.end + 2))) {
                    int i5 = i4 + 1;
                    if (arrayList.size() > i5) {
                        LinkSpec linkSpec3 = (LinkSpec) arrayList.get(i5);
                        if ((linkSpec3.start == linkSpec2.end + 6 && spannable.length() > linkSpec2.end + 5 && "tp://".equalsIgnoreCase(spannable.toString().substring(linkSpec2.end, linkSpec2.end + 5))) || (linkSpec3.start == linkSpec2.end + 7 && spannable.length() > linkSpec2.end + 6 && "tps://".equalsIgnoreCase(spannable.toString().substring(linkSpec2.end, linkSpec2.end + 6)))) {
                            linkSpec3.start = linkSpec2.end - 2;
                            linkSpec3.url = spannable.toString().substring(linkSpec3.start, linkSpec3.end);
                            arrayList.set(i5, linkSpec3);
                        }
                    }
                    arrayList.remove(linkSpec2);
                    if (i4 > 0) {
                        i4--;
                    }
                } else {
                    if (lowerCase2.contains(str) && !lowerCase2.startsWith(str) && !lowerCase2.startsWith("http://") && !lowerCase2.startsWith("https://") && !lowerCase2.startsWith("rtsp://") && !lowerCase2.startsWith("ftp://")) {
                        linkSpec2.start += lowerCase2.indexOf(str);
                        linkSpec2.url = linkSpec2.url.substring(0, linkSpec2.url.indexOf("://") + 3) + spannable.toString().substring(linkSpec2.start, linkSpec2.end);
                    }
                    int lastIndexOf2 = lowerCase2.lastIndexOf(MediaMetrics.SEPARATOR);
                    if (lastIndexOf2 >= 0 && lastIndexOf2 < lowerCase2.length() - 1) {
                        char[] charArray2 = lowerCase2.substring(lastIndexOf2 + 1).toCharArray();
                        int i6 = 0;
                        while (i6 < charArray2.length && charArray2[i6] < 128) {
                            i6++;
                        }
                        if (i6 < charArray2.length && i6 > 0 && charArray2[i6 - 1] != '/') {
                            linkSpec2.end -= charArray2.length - i6;
                            linkSpec2.url = linkSpec2.url.substring(0, linkSpec2.url.length() - (charArray2.length - i6));
                        }
                    }
                    checkBracketsPairs(spannable.toString(), linkSpec2);
                    i4++;
                }
            }
        }
        if ((i & 2) != 0) {
            spannable2 = spannable;
            gatherLinks(arrayList, spannable2, Patterns.AUTOLINK_EMAIL_ADDRESS, new String[]{"mailto:"}, null, null);
        } else {
            spannable2 = spannable;
        }
        if ((49156 & i) != 0) {
            gatherTelLinks(arrayList, spannable2, context);
        }
        if ((i & 8) != 0) {
            gatherMapLinks(arrayList, spannable2);
        }
        pruneOverlaps(arrayList);
        if (arrayList.size() == 0) {
            return false;
        }
        Iterator it3 = arrayList.iterator();
        while (it3.hasNext()) {
            LinkSpec linkSpec4 = (LinkSpec) it3.next();
            applyLink(linkSpec4.url, linkSpec4.start, linkSpec4.end, spannable2, function);
        }
        return z;
    }

    public static boolean containsUnsupportedCharacters(String str) {
        if (str.contains("\u202c")) {
            Log.e(LOG_TAG, "Unsupported character for applying links: u202C");
            return true;
        }
        if (str.contains("\u202d")) {
            Log.e(LOG_TAG, "Unsupported character for applying links: u202D");
            return true;
        }
        if (!str.contains("\u202e")) {
            return false;
        }
        Log.e(LOG_TAG, "Unsupported character for applying links: u202E");
        return true;
    }

    public static final boolean addLinks(TextView textView, int i) {
        if (i == 0) {
            return false;
        }
        Context context = textView.getContext();
        CharSequence text = textView.getText();
        if (text instanceof Spannable) {
            if (!addLinks((Spannable) text, i, context, null)) {
                return false;
            }
            addLinkMovementMethod(textView);
            return true;
        }
        SpannableString valueOf = SpannableString.valueOf(text);
        if (!addLinks(valueOf, i, context, null)) {
            return false;
        }
        addLinkMovementMethod(textView);
        textView.lambda$setTextAsync$0(valueOf);
        return true;
    }

    private static final void addLinkMovementMethod(TextView textView) {
        MovementMethod movementMethod = textView.getMovementMethod();
        if ((movementMethod == null || !(movementMethod instanceof LinkMovementMethod)) && textView.getLinksClickable()) {
            textView.setMovementMethod(LinkMovementMethod.getInstance());
        }
    }

    public static final void addLinks(TextView textView, Pattern pattern, String str) {
        addLinks(textView, pattern, str, (String[]) null, (MatchFilter) null, (TransformFilter) null);
    }

    public static final void addLinks(TextView textView, Pattern pattern, String str, MatchFilter matchFilter, TransformFilter transformFilter) {
        addLinks(textView, pattern, str, (String[]) null, matchFilter, transformFilter);
    }

    public static final void addLinks(TextView textView, Pattern pattern, String str, String[] strArr, MatchFilter matchFilter, TransformFilter transformFilter) {
        SpannableString valueOf = SpannableString.valueOf(textView.getText());
        if (addLinks(valueOf, pattern, str, strArr, matchFilter, transformFilter)) {
            textView.lambda$setTextAsync$0(valueOf);
            addLinkMovementMethod(textView);
        }
    }

    public static final boolean addLinks(Spannable spannable, Pattern pattern, String str) {
        return addLinks(spannable, pattern, str, (String[]) null, (MatchFilter) null, (TransformFilter) null);
    }

    public static final boolean addLinks(Spannable spannable, Pattern pattern, String str, MatchFilter matchFilter, TransformFilter transformFilter) {
        return addLinks(spannable, pattern, str, (String[]) null, matchFilter, transformFilter);
    }

    public static final boolean addLinks(Spannable spannable, Pattern pattern, String str, String[] strArr, MatchFilter matchFilter, TransformFilter transformFilter) {
        return addLinks(spannable, pattern, str, strArr, matchFilter, transformFilter, null);
    }

    public static final boolean addLinks(Spannable spannable, Pattern pattern, String str, String[] strArr, MatchFilter matchFilter, TransformFilter transformFilter, Function<String, URLSpan> function) {
        String lowerCase;
        if (spannable != null && containsUnsupportedCharacters(spannable.toString())) {
            EventLog.writeEvent(1397638484, "116321860", -1, "");
            return false;
        }
        if (str == null) {
            str = "";
        }
        if (strArr == null || strArr.length < 1) {
            strArr = EmptyArray.STRING;
        }
        String[] strArr2 = new String[strArr.length + 1];
        strArr2[0] = str.toLowerCase(Locale.ROOT);
        int i = 0;
        while (i < strArr.length) {
            String str2 = strArr[i];
            i++;
            if (str2 == null) {
                lowerCase = "";
            } else {
                lowerCase = str2.toLowerCase(Locale.ROOT);
            }
            strArr2[i] = lowerCase;
        }
        Matcher matcher = pattern.matcher(spannable);
        boolean z = false;
        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            if (matchFilter != null ? matchFilter.acceptMatch(spannable, start, end) : true) {
                applyLink(makeUrl(matcher.group(0), strArr2, matcher, transformFilter), start, end, spannable, function);
                z = true;
            }
        }
        return z;
    }

    private static void applyLink(String str, int i, int i2, Spannable spannable, Function<String, URLSpan> function) {
        if (function == null) {
            function = DEFAULT_SPAN_FACTORY;
        }
        spannable.setSpan(function.apply(str), i, i2, 33);
    }

    private static final String makeUrl(String str, String[] strArr, Matcher matcher, TransformFilter transformFilter) {
        boolean z;
        if (transformFilter != null) {
            str = transformFilter.transformUrl(matcher, str);
        }
        String str2 = str;
        int i = 0;
        while (true) {
            if (i >= strArr.length) {
                z = false;
                break;
            }
            String str3 = strArr[i];
            if (str2.regionMatches(true, 0, str3, 0, str3.length())) {
                String str4 = strArr[i];
                z = true;
                if (!str2.regionMatches(false, 0, str4, 0, str4.length())) {
                    str2 = strArr[i] + str2.substring(strArr[i].length());
                }
            } else {
                i++;
            }
        }
        if (z || strArr.length <= 0) {
            return str2;
        }
        return strArr[0] + str2;
    }

    private static final void gatherLinks(ArrayList<LinkSpec> arrayList, Spannable spannable, Pattern pattern, String[] strArr, MatchFilter matchFilter, TransformFilter transformFilter) {
        Matcher matcher = pattern.matcher(spannable);
        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            if (matchFilter == null || matchFilter.acceptMatch(spannable, start, end)) {
                LinkSpec linkSpec = new LinkSpec();
                linkSpec.url = makeUrl(matcher.group(0), strArr, matcher, transformFilter);
                linkSpec.start = start;
                linkSpec.end = end;
                arrayList.add(linkSpec);
            }
        }
    }

    private static void gatherTelLinks(ArrayList<LinkSpec> arrayList, Spannable spannable, Context context) {
        PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
        if (context == null) {
            context = ActivityThread.currentApplication();
        }
        String country = Locale.getDefault().getCountry();
        if (context != null) {
            String simCountryIso = ((TelephonyManager) context.getSystemService(TelephonyManager.class)).getSimCountryIso();
            if (!TextUtils.isEmpty(simCountryIso)) {
                country = simCountryIso.toUpperCase(Locale.US);
            }
        }
        String str = country;
        for (PhoneNumberMatch phoneNumberMatch : phoneNumberUtil.findNumbers(spannable.toString(), str, PhoneNumberUtil.Leniency.POSSIBLE, Long.MAX_VALUE)) {
            LinkSpec linkSpec = new LinkSpec();
            linkSpec.url = WebView.SCHEME_TEL + PhoneNumberUtils.normalizeNumber(phoneNumberMatch.rawString());
            linkSpec.start = phoneNumberMatch.start();
            linkSpec.end = phoneNumberMatch.end();
            String rawString = phoneNumberMatch.rawString();
            if ((rawString.charAt(0) == '[' && !rawString.contains(NavigationBarInflaterView.SIZE_MOD_END)) || (rawString.charAt(0) == '(' && !rawString.contains(NavigationBarInflaterView.KEY_CODE_END))) {
                linkSpec.start++;
            } else if (rawString.charAt(0) == '+' && rawString.charAt(1) == '+') {
                linkSpec.start++;
            }
            if (!"KR".equals(str) || needToAddLink(spannable.toString(), phoneNumberMatch.rawString(), linkSpec.start, linkSpec.end)) {
                arrayList.add(linkSpec);
            }
        }
    }

    private static final void gatherMapLinks(ArrayList<LinkSpec> arrayList, Spannable spannable) {
        int indexOf;
        String spannable2 = spannable.toString();
        int i = 0;
        while (true) {
            try {
                String findAddress = WebView.findAddress(spannable2);
                if (findAddress != null && (indexOf = spannable2.indexOf(findAddress)) >= 0) {
                    LinkSpec linkSpec = new LinkSpec();
                    int length = findAddress.length() + indexOf;
                    linkSpec.start = indexOf + i;
                    i += length;
                    linkSpec.end = i;
                    spannable2 = spannable2.substring(length);
                    try {
                        linkSpec.url = WebView.SCHEME_GEO + URLEncoder.encode(findAddress, "UTF-8");
                        arrayList.add(linkSpec);
                    } catch (UnsupportedEncodingException unused) {
                    }
                }
                return;
            } catch (UnsupportedOperationException unused2) {
                return;
            }
        }
    }

    private static final void pruneOverlaps(ArrayList<LinkSpec> arrayList) {
        int i;
        Collections.sort(arrayList, new Comparator<LinkSpec>() { // from class: android.text.util.Linkify.4
            @Override // java.util.Comparator
            public final int compare(LinkSpec linkSpec, LinkSpec linkSpec2) {
                if (linkSpec.start < linkSpec2.start) {
                    return -1;
                }
                if (linkSpec.start <= linkSpec2.start && linkSpec.end >= linkSpec2.end) {
                    return linkSpec.end > linkSpec2.end ? -1 : 0;
                }
                return 1;
            }
        });
        int size = arrayList.size();
        int i2 = 0;
        while (i2 < size - 1) {
            LinkSpec linkSpec = arrayList.get(i2);
            int i3 = i2 + 1;
            LinkSpec linkSpec2 = arrayList.get(i3);
            if (linkSpec.start <= linkSpec2.start && linkSpec.end > linkSpec2.start) {
                if (linkSpec2.end > linkSpec.end && linkSpec.end - linkSpec.start <= linkSpec2.end - linkSpec2.start) {
                    i = linkSpec.end - linkSpec.start < linkSpec2.end - linkSpec2.start ? i2 : -1;
                } else {
                    i = i3;
                }
                if (i != -1) {
                    arrayList.remove(i);
                    size--;
                }
            }
            i2 = i3;
        }
    }

    static /* synthetic */ URLSpan lambda$static$0(String str) {
        return new URLSpan(str);
    }

    private static boolean needToAddLink(String str, String str2, int i, int i2) {
        char c;
        char c2;
        if (!str2.contains("/") && !str2.contains("~")) {
            int length = str.length();
            if (i2 < length) {
                c2 = str.charAt(i2);
                int i3 = i2 + 1;
                c = i3 < length ? str.charAt(i3) : (char) 0;
            } else {
                c = 0;
                c2 = 0;
            }
            if (c2 != 50896 && c2 != 8361 && (c2 != ' ' || (c != 50896 && c != 8361))) {
                if (PhoneNumberUtils.normalizeNumber(str2).length() != 3) {
                    return true;
                }
                if ((i >= 1 ? str.charAt(i - 1) : (char) 0) != ',' && c2 != ',' && str2.charAt(0) == '1' && str2.charAt(1) == '1' && (str2.charAt(2) == '2' || str2.charAt(2) == '9')) {
                    return true;
                }
            }
        }
        return false;
    }

    private static void checkBracketsPairs(String str, LinkSpec linkSpec) {
        int i = linkSpec.end - linkSpec.start;
        String substring = str.substring(linkSpec.start, linkSpec.end);
        if (substring.charAt(i - 1) != ')') {
            return;
        }
        int i2 = 0;
        for (int i3 = 0; i3 < i; i3++) {
            if (substring.charAt(i3) == ')') {
                i2++;
            } else if (substring.charAt(i3) == '(') {
                i2--;
            }
        }
        if (i2 > 0) {
            linkSpec.end -= i2;
            linkSpec.url = linkSpec.url.substring(0, linkSpec.url.length() - i2);
        }
    }
}
