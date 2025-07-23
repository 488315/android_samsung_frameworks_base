package android.app.smartspace;

import android.app.smartspace.uitemplatedata.Text;
import android.text.TextUtils;

/* loaded from: classes.dex */
public final class SmartspaceUtils {
    private SmartspaceUtils() {
    }

    public static boolean isEmpty(Text text) {
        return text == null || TextUtils.isEmpty(text.getText());
    }

    public static boolean isEqual(Text text, Text text2) {
        if (text == null && text2 == null) {
            return true;
        }
        if (text == null || text2 == null) {
            return false;
        }
        return text.equals(text2);
    }

    public static boolean isEqual(CharSequence charSequence, CharSequence charSequence2) {
        if (charSequence == null && charSequence2 == null) {
            return true;
        }
        if (charSequence == null || charSequence2 == null) {
            return false;
        }
        return charSequence.toString().contentEquals(charSequence2);
    }
}
