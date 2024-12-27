package android.sec.clipboard.util;

import android.text.TextUtils;
import java.util.regex.Pattern;

/* loaded from: classes3.dex */
public class HtmlUtils {
    private static final String HTML_ENTITY = "&[^;]+;";
    public static final String HTML_LINE_FEED = "&#10;";
    private static final Pattern HTML_PATTERN = Pattern.compile("(</?\\w+((\\s+\\w+(\\s*=\\s*(?:\".*?\"|'.*?'|[^'\">\\s]+))?)+\\s*|\\s*)/?>)|(&[^;]+;)", 32);
    private static final String TAG_ALL = "</?\\w+((\\s+\\w+(\\s*=\\s*(?:\".*?\"|'.*?'|[^'\">\\s]+))?)+\\s*|\\s*)/?>";

    public static boolean isHtml(String s) {
        if (TextUtils.isEmpty(s)) {
            return false;
        }
        return HTML_PATTERN.matcher(s).find();
    }
}
