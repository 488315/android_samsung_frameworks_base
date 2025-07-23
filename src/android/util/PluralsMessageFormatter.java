package android.util;

import android.content.res.Resources;
import android.icu.text.MessageFormat;
import java.util.Map;

/* loaded from: classes4.dex */
public class PluralsMessageFormatter {
    public static String format(Resources resources, Map<String, Object> map, int i) {
        return new MessageFormat(resources.getString(i)).format(map);
    }
}
