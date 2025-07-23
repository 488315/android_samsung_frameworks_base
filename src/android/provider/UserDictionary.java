package android.provider;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import java.util.Locale;

/* loaded from: classes3.dex */
public class UserDictionary {
    public static final String AUTHORITY = "user_dictionary";
    public static final Uri CONTENT_URI = Uri.parse("content://user_dictionary");
    private static final int FREQUENCY_MAX = 255;
    private static final int FREQUENCY_MIN = 0;

    public static class Words implements BaseColumns {
        public static final String APP_ID = "appid";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.google.userword";
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.google.userword";
        public static final Uri CONTENT_URI = Uri.parse("content://user_dictionary/words");
        public static final String DEFAULT_SORT_ORDER = "frequency DESC";
        public static final String FREQUENCY = "frequency";
        public static final String LOCALE = "locale";

        @Deprecated
        public static final int LOCALE_TYPE_ALL = 0;

        @Deprecated
        public static final int LOCALE_TYPE_CURRENT = 1;
        public static final String SHORTCUT = "shortcut";
        public static final String WORD = "word";
        public static final String _ID = "_id";

        @Deprecated
        public static void addWord(Context context, String str, int i, int i2) {
            if (i2 == 0 || i2 == 1) {
                addWord(context, str, i, null, i2 == 1 ? Locale.getDefault() : null);
            }
        }

        public static void addWord(Context context, String str, int i, String str2, Locale locale) {
            ContentResolver contentResolver = context.getContentResolver();
            if (TextUtils.isEmpty(str)) {
                return;
            }
            if (i < 0) {
                i = 0;
            }
            if (i > 255) {
                i = 255;
            }
            ContentValues contentValues = new ContentValues(5);
            contentValues.put(WORD, str);
            contentValues.put("frequency", Integer.valueOf(i));
            contentValues.put("locale", locale == null ? null : locale.toString());
            contentValues.put(APP_ID, (Integer) 0);
            contentValues.put("shortcut", str2);
            contentResolver.insert(CONTENT_URI, contentValues);
        }
    }
}
