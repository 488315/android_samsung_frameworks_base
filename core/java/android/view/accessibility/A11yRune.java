package android.view.accessibility;

import android.content.ContentProviderClient;
import android.database.Cursor;
import android.net.Uri;
import android.os.SemSystemProperties;

import com.samsung.android.feature.SemFloatingFeature;

public class A11yRune {
    public static final boolean A11Y_ADV_BOOL_FLASH_NOTIFICATION = true;
    public static final boolean A11Y_COLOR_BOOL_SUPPORT_AMOLED_DISPLAY = true;
    public static final boolean A11Y_COLOR_BOOL_SUPPORT_COLOR_BLIND = true;
    public static final boolean A11Y_COLOR_BOOL_SUPPORT_COLOR_FILTER = true;
    public static final boolean A11Y_COLOR_BOOL_SUPPORT_COLOR_FILTER_MDNIE_HW;
    public static final boolean A11Y_COLOR_BOOL_SUPPORT_COLOR_RELUMINO = true;
    public static final boolean A11Y_COLOR_BOOL_SUPPORT_MDNIE_HW = true;
    public static final boolean A11Y_COLOR_BOOL_SUPPORT_MDNIE_SW = false;
    public static final boolean A11Y_COMMON_BOOL_FIX_STYLE_BUG = true;
    public static final boolean A11Y_COMMON_BOOL_GET_WINDOWS_MAIN_DISPLAY = true;
    public static final boolean A11Y_COMMON_BOOL_LOG_FOR_DEBUG = true;
    public static final boolean A11Y_COMMON_BOOL_ONEHANDMODE_INITIALIZE_INPUTFILTER = true;
    public static final boolean A11Y_COMMON_BOOL_SAMSUNG_A11Y = true;
    public static final boolean A11Y_COMMON_BOOL_SAMSUNG_USER_STATE_FLAG = true;
    public static final boolean A11Y_COMMON_BOOL_SKIP_CHECKING_WINDOW_BOUND_FOR_WINDOWLESS = true;
    public static final boolean A11Y_COMMON_BOOL_SUPPORT_A11Y_HELPER = true;
    public static final boolean A11Y_COMMON_BOOL_SUPPORT_A11Y_LOGGER = true;
    public static final boolean A11Y_COMMON_BOOL_SUPPORT_ACCESSIBILITY_PROFILE = true;
    public static final boolean A11Y_COMMON_BOOL_SUPPORT_DIRECT_ACCESS = true;
    public static final boolean A11Y_COMMON_BOOL_SUPPORT_DUAL_DISPLAY_FOLD = false;
    public static final boolean A11Y_COMMON_BOOL_SUPPORT_EXCLUSIVE_TASK_MANAGER = true;
    public static final boolean A11Y_COMMON_BOOL_SUPPORT_EXTRA_DISPLAY = false;
    public static final boolean A11Y_COMMON_BOOL_SUPPORT_GESTURE_NAVI_A11Y_BUTTON = true;
    public static final boolean A11Y_COMMON_BOOL_SUPPORT_GESTURE_TO_STOP_TALKBACK = true;
    public static final boolean A11Y_COMMON_BOOL_SUPPORT_LARGE_COVER_SCREEN_FLIP;
    public static final boolean A11Y_COMMON_BOOL_SUPPORT_WINDOWINFO_RAW_TYPE = true;
    public static final boolean A11Y_DEX_BOOL_SUPPORT_ACCESSIBILITY_SHORTCUT = true;
    public static final boolean A11Y_DEX_BOOL_SUPPORT_ACTION_AFTER_POINTER_STOPS = true;
    public static final boolean A11Y_DEX_BOOL_SUPPORT_ASSISTANT_MENU = true;
    public static final boolean A11Y_DEX_BOOL_SUPPORT_CLICK_AFTER_POINTER_STOPS = true;
    public static final boolean A11Y_DEX_BOOL_SUPPORT_EASY_SCREEN = true;
    public static final boolean A11Y_DEX_BOOL_SUPPORT_IGNORE_REPEAT = true;
    public static final boolean A11Y_DEX_BOOL_SUPPORT_INTERACTION_CONTROL = true;
    public static final boolean A11Y_DEX_BOOL_SUPPORT_STICKY_KEYS = true;
    public static final boolean A11Y_DEX_BOOL_SUPPORT_STICKY_KEYS_USING_GOOGLE_FEATURE = true;
    public static final boolean A11Y_DEX_BOOL_SUPPORT_TAP_DURATION = true;
    public static final boolean A11Y_DEX_BOOL_SUPPORT_UNIVERSAL_SWITCH = true;
    public static final boolean A11Y_GOOGLE_BOOL_EXCEPTION_BUG_FIX = true;
    public static final boolean A11Y_HEARING_BOOL_SUPPORT_AMPLIFY_AMBIENT_SOUND = true;
    public static final boolean A11Y_MAGNIFICATION_BOOL_SUPPORT_FULLSCREEN_MAGNIFICATION = true;
    public static final boolean A11Y_MAGNIFICATION_BOOL_SUPPORT_SPEN = true;
    public static final boolean A11Y_MAGNIFICATION_BOOL_SUPPORT_THUMBNAIL = true;
    public static final boolean A11Y_MAGNIFICATION_BOOL_SUPPORT_WINDOW_MAGNIFICATION = true;
    public static final boolean A11Y_SHORTCUT_BOOL_BLOCK_SHORTCUT_ON_ENABLED_ACCESSCONTROL = true;
    public static final boolean A11Y_SHORTCUT_BOOL_CHANGE_DEFAULT_TALKBACK_SERVICE = true;
    public static final boolean A11Y_SHORTCUT_BOOL_COMBINE_A11Y_BUTTON_AND_SHORTCUT_KEY = true;
    public static final boolean A11Y_SHORTCUT_BOOL_SUPPORT_DIRECT_ACCESS = true;
    public static final boolean A11Y_SHORTCUT_SUPPORT_BOOL_SAMSUNG_FLOATING_BUTTON = true;
    public static final boolean A11Y_SHORTCUT_SUPPORT_BOOL_SAMSUNG_QUICK_SETTINGS = true;
    public static final boolean A11Y_TALKBACK_BOOL_APPLY_DELAY_FOR_INTERACTION_END = true;
    public static final boolean A11Y_VISIBILITY_BOOL_SUPPORT_EXTRA_DIM = true;
    public static final boolean A11Y_VISIBILITY_BOOL_SUPPORT_HIGH_CONTRAST_FONT = true;
    public static final boolean A11Y_VISIBILITY_BOOL_SUPPORT_REMOVE_ANIMATION = true;
    public static final boolean A11Y_VOICE_BOOL_BUGFIX = true;
    public static final boolean A11Y_VOICE_BOOL_SUPPORT_CURSOR_CONTROL = true;
    public static final boolean A11Y_VOICE_BOOL_SUPPORT_DARK_SCREEN = true;
    public static final boolean A11Y_VOICE_BOOL_SUPPORT_FOCUS_INDICATOR_MULTI_DENSITY = true;
    public static final boolean A11Y_VOICE_BOOL_SUPPORT_IMPROVE_DOUBLE_TAP_RECOGNITION = true;
    public static final boolean A11Y_VOICE_BOOL_SUPPORT_SIP = true;
    public static final boolean ACCOUNT_COMMON_BOOL_DUAL_APP = true;
    static final Uri CONTENT_URI;
    static final String[] SELECT_PROJECTION;
    private static final String TAG = "A11yRune";
    static final int VALUE_INDEX = 2;
    private static Cursor cursor;
    private static ContentProviderClient mClient;
    private static String value;
    private static final int FIRST_API_LEVEL =
            SemSystemProperties.getInt("ro.product.first_api_level", 20);
    public static final boolean A11Y_COLOR_BOOL_SUPPORT_DMC_COLORWEAKNESS =
            String.valueOf("3").equals("0");

    static {
        A11Y_COLOR_BOOL_SUPPORT_COLOR_FILTER_MDNIE_HW = FIRST_API_LEVEL < 35;
        A11Y_COMMON_BOOL_SUPPORT_LARGE_COVER_SCREEN_FLIP =
                SemFloatingFeature.getInstance()
                                .getBoolean(
                                        "SEC_FLOATING_FEATURE_FRAMEWORK_SUPPORT_FOLDABLE_TYPE_FLIP")
                        && SemFloatingFeature.getInstance()
                                .getString(
                                        "SEC_FLOATING_FEATURE_LOCKSCREEN_CONFIG_SUBDISPLAY_POLICY")
                                .contains("LARGESCREEN");
        CONTENT_URI = Uri.parse("content://com.samsung.accessibility.provider/a11ysettings");
        SELECT_PROJECTION = new String[] {"_id", "name", "value"};
        value = null;
        cursor = null;
        mClient = null;
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x0041, code lost:

       if (android.view.accessibility.A11yRune.mClient != null) goto L30;
    */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0070, code lost:

       return android.view.accessibility.A11yRune.value;
    */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0069, code lost:

       android.view.accessibility.A11yRune.mClient.close();
    */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x0055, code lost:

       if (android.view.accessibility.A11yRune.mClient == null) goto L31;
    */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x0067, code lost:

       if (android.view.accessibility.A11yRune.mClient == null) goto L31;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String readDataFromAccessibilityProvider(
            android.content.Context r8, java.lang.String r9) {
        /*
            android.content.ContentResolver r0 = r8.getContentResolver()     // Catch: java.lang.Throwable -> L44 android.database.CursorIndexOutOfBoundsException -> L46 android.os.RemoteException -> L58
            android.net.Uri r1 = android.view.accessibility.A11yRune.CONTENT_URI     // Catch: java.lang.Throwable -> L44 android.database.CursorIndexOutOfBoundsException -> L46 android.os.RemoteException -> L58
            android.content.ContentProviderClient r1 = r0.acquireContentProviderClient(r1)     // Catch: java.lang.Throwable -> L44 android.database.CursorIndexOutOfBoundsException -> L46 android.os.RemoteException -> L58
            android.view.accessibility.A11yRune.mClient = r1     // Catch: java.lang.Throwable -> L44 android.database.CursorIndexOutOfBoundsException -> L46 android.os.RemoteException -> L58
            android.content.ContentProviderClient r1 = android.view.accessibility.A11yRune.mClient     // Catch: java.lang.Throwable -> L44 android.database.CursorIndexOutOfBoundsException -> L46 android.os.RemoteException -> L58
            if (r1 == 0) goto L36
            android.content.ContentProviderClient r2 = android.view.accessibility.A11yRune.mClient     // Catch: java.lang.Throwable -> L44 android.database.CursorIndexOutOfBoundsException -> L46 android.os.RemoteException -> L58
            android.net.Uri r3 = android.view.accessibility.A11yRune.CONTENT_URI     // Catch: java.lang.Throwable -> L44 android.database.CursorIndexOutOfBoundsException -> L46 android.os.RemoteException -> L58
            java.lang.String[] r4 = android.view.accessibility.A11yRune.SELECT_PROJECTION     // Catch: java.lang.Throwable -> L44 android.database.CursorIndexOutOfBoundsException -> L46 android.os.RemoteException -> L58
            java.lang.String r5 = "name=?"
            java.lang.String[] r6 = new java.lang.String[]{r9}     // Catch: java.lang.Throwable -> L44 android.database.CursorIndexOutOfBoundsException -> L46 android.os.RemoteException -> L58
            r7 = 0
            android.database.Cursor r1 = r2.query(r3, r4, r5, r6, r7)     // Catch: java.lang.Throwable -> L44 android.database.CursorIndexOutOfBoundsException -> L46 android.os.RemoteException -> L58
            android.view.accessibility.A11yRune.cursor = r1     // Catch: java.lang.Throwable -> L44 android.database.CursorIndexOutOfBoundsException -> L46 android.os.RemoteException -> L58
            android.database.Cursor r1 = android.view.accessibility.A11yRune.cursor     // Catch: java.lang.Throwable -> L44 android.database.CursorIndexOutOfBoundsException -> L46 android.os.RemoteException -> L58
            if (r1 == 0) goto L36
            android.database.Cursor r1 = android.view.accessibility.A11yRune.cursor     // Catch: java.lang.Throwable -> L44 android.database.CursorIndexOutOfBoundsException -> L46 android.os.RemoteException -> L58
            r1.moveToFirst()     // Catch: java.lang.Throwable -> L44 android.database.CursorIndexOutOfBoundsException -> L46 android.os.RemoteException -> L58
            android.database.Cursor r1 = android.view.accessibility.A11yRune.cursor     // Catch: java.lang.Throwable -> L44 android.database.CursorIndexOutOfBoundsException -> L46 android.os.RemoteException -> L58
            r2 = 2
            java.lang.String r1 = r1.getString(r2)     // Catch: java.lang.Throwable -> L44 android.database.CursorIndexOutOfBoundsException -> L46 android.os.RemoteException -> L58
            android.view.accessibility.A11yRune.value = r1     // Catch: java.lang.Throwable -> L44 android.database.CursorIndexOutOfBoundsException -> L46 android.os.RemoteException -> L58
        L36:
            android.database.Cursor r0 = android.view.accessibility.A11yRune.cursor
            if (r0 == 0) goto L3f
            android.database.Cursor r0 = android.view.accessibility.A11yRune.cursor
            r0.close()
        L3f:
            android.content.ContentProviderClient r0 = android.view.accessibility.A11yRune.mClient
            if (r0 == 0) goto L6e
            goto L69
        L44:
            r0 = move-exception
            goto L71
        L46:
            r0 = move-exception
            r0.printStackTrace()     // Catch: java.lang.Throwable -> L44
            android.database.Cursor r0 = android.view.accessibility.A11yRune.cursor
            if (r0 == 0) goto L53
            android.database.Cursor r0 = android.view.accessibility.A11yRune.cursor
            r0.close()
        L53:
            android.content.ContentProviderClient r0 = android.view.accessibility.A11yRune.mClient
            if (r0 == 0) goto L6e
            goto L69
        L58:
            r0 = move-exception
            r0.printStackTrace()     // Catch: java.lang.Throwable -> L44
            android.database.Cursor r0 = android.view.accessibility.A11yRune.cursor
            if (r0 == 0) goto L65
            android.database.Cursor r0 = android.view.accessibility.A11yRune.cursor
            r0.close()
        L65:
            android.content.ContentProviderClient r0 = android.view.accessibility.A11yRune.mClient
            if (r0 == 0) goto L6e
        L69:
            android.content.ContentProviderClient r0 = android.view.accessibility.A11yRune.mClient
            r0.close()
        L6e:
            java.lang.String r0 = android.view.accessibility.A11yRune.value
            return r0
        L71:
            android.database.Cursor r1 = android.view.accessibility.A11yRune.cursor
            if (r1 == 0) goto L7a
            android.database.Cursor r1 = android.view.accessibility.A11yRune.cursor
            r1.close()
        L7a:
            android.content.ContentProviderClient r1 = android.view.accessibility.A11yRune.mClient
            if (r1 == 0) goto L83
            android.content.ContentProviderClient r1 = android.view.accessibility.A11yRune.mClient
            r1.close()
        L83:
            throw r0
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " android.view.accessibility.A11yRune.readDataFromAccessibilityProvider(android.content.Context,"
                    + " java.lang.String):java.lang.String");
    }

    public static Uri getUriFor(Uri uri, String name) {
        return Uri.withAppendedPath(uri, name);
    }

    public static Uri getUriFor(String name) {
        return getUriFor(CONTENT_URI, name);
    }
}
