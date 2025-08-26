package android.content.om;

import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes.dex */
public class SamsungThemeUtils {
    private static final String TAG = "SamsungThemeUtils";

    public static String[] removeSamsungThemeOverlays(String[] strArr) {
        if (strArr == null || strArr.length <= 0) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (String str : strArr) {
            if (str != null && !str.startsWith(SamsungThemeConstants.PATH_OVERLAY_CURRENT_STYLE)) {
                arrayList.add(str);
            }
        }
        return (String[]) arrayList.toArray(new String[0]);
    }

    public static String[] removeSamsungThemeOverlaysForCover(String[] strArr) {
        if (strArr == null || strArr.length <= 0) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (String str : strArr) {
            if (str != null) {
                boolean zStartsWith = str.startsWith(SamsungThemeConstants.PATH_OVERLAY_CURRENT_STYLE);
                if (zStartsWith && hasAllowPostfixforCover(str)) {
                    return strArr;
                }
                if (zStartsWith && hasAllowSystemUIforCover(str)) {
                    return removeOnlySystemUIOverlay(strArr);
                }
                if (!zStartsWith) {
                    arrayList.add(str);
                }
            }
        }
        return (String[]) arrayList.toArray(new String[0]);
    }

    private static boolean hasAllowPostfixforCover(String str) {
        Iterator<String> it = SamsungThemeConstants.allowPostfixForCover.iterator();
        while (it.hasNext()) {
            String next = it.next();
            if (str != null && str.endsWith(next)) {
                return true;
            }
        }
        return false;
    }

    private static boolean hasAllowSystemUIforCover(String str) {
        Iterator<String> it = SamsungThemeConstants.allowSystemUIForCover.iterator();
        while (it.hasNext()) {
            String next = it.next();
            if (str != null && str.endsWith(next)) {
                return true;
            }
        }
        return false;
    }

    private static String[] removeOnlySystemUIOverlay(String[] strArr) {
        ArrayList arrayList = new ArrayList();
        for (String str : strArr) {
            if (str != null && (!str.startsWith(SamsungThemeConstants.PATH_OVERLAY_CURRENT_STYLE) || !str.endsWith(SamsungThemeConstants.THEME_OVERLAY_SYSTEMUI_POSTFIX))) {
                arrayList.add(str);
            }
        }
        return (String[]) arrayList.toArray(new String[0]);
    }
}
