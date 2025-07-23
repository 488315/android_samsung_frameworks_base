package android.media;

import android.text.TextUtils;
import android.util.Log;
import com.android.internal.util.Preconditions;

/* loaded from: classes2.dex */
public class MediaRouter2Utils {
    static final String SEPARATOR = ":";
    static final String TAG = "MR2Utils";

    public static String toUniqueId(String str, String str2) {
        Preconditions.checkArgument((TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) ? false : true);
        return str + ":" + str2;
    }

    public static String getProviderId(String str) {
        if (TextUtils.isEmpty(str)) {
            Log.w(TAG, "getProviderId: uniqueId shouldn't be empty");
            return null;
        }
        int indexOf = str.indexOf(":");
        if (indexOf == -1) {
            return null;
        }
        String substring = str.substring(0, indexOf);
        if (TextUtils.isEmpty(substring)) {
            return null;
        }
        return substring;
    }

    public static String getOriginalId(String str) {
        int i;
        if (TextUtils.isEmpty(str)) {
            Log.w(TAG, "getOriginalId: uniqueId shouldn't be empty");
            return null;
        }
        int indexOf = str.indexOf(":");
        if (indexOf == -1 || (i = indexOf + 1) >= str.length()) {
            return null;
        }
        String substring = str.substring(i);
        if (TextUtils.isEmpty(substring)) {
            return null;
        }
        return substring;
    }
}
