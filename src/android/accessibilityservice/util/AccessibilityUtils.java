package android.accessibilityservice.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

/* loaded from: classes.dex */
public final class AccessibilityUtils {
    private static final String ANCHOR_TAG = "a";
    private static final String IMG_PREFIX = "R.drawable.";
    private static final List<String> UNSUPPORTED_TAG_LIST = new ArrayList(Collections.singletonList("a"));

    private AccessibilityUtils() {
    }

    public static String getFilteredHtmlText(String str) {
        for (String str2 : UNSUPPORTED_TAG_LIST) {
            String str3 = "(?i)<" + str2 + "(\\s+|>)";
            str = Pattern.compile("(?i)</" + str2 + "\\s*>").matcher(Pattern.compile(str3).matcher(str).replaceAll("<invalidtag ")).replaceAll("</invalidtag>");
        }
        return Pattern.compile("(?i)<img\\s+(?!src\\s*=\\s*\"(?-i)R.drawable.)").matcher(str).replaceAll("<invalidtag ");
    }

    public static Drawable loadSafeAnimatedImage(Context context, ApplicationInfo applicationInfo, int i) {
        Drawable drawable;
        if (i == 0 || (drawable = context.getPackageManager().getDrawable(applicationInfo.packageName, i, applicationInfo)) == null) {
            return null;
        }
        boolean z = drawable.getIntrinsicWidth() > getScreenWidthPixels(context);
        boolean z2 = drawable.getIntrinsicHeight() > getScreenHeightPixels(context);
        if (z || z2) {
            return null;
        }
        return drawable;
    }

    private static int getScreenWidthPixels(Context context) {
        return Math.round(TypedValue.applyDimension(1, r2.getConfiguration().screenWidthDp, context.getResources().getDisplayMetrics()));
    }

    private static int getScreenHeightPixels(Context context) {
        return Math.round(TypedValue.applyDimension(1, r2.getConfiguration().screenHeightDp, context.getResources().getDisplayMetrics()));
    }
}
