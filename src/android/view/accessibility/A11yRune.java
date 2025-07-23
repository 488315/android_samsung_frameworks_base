package android.view.accessibility;

import android.content.ContentProviderClient;
import android.database.Cursor;
import android.net.Uri;
import android.os.SemSystemProperties;
import com.samsung.android.feature.SemFloatingFeature;

/* loaded from: classes4.dex */
public class A11yRune {
    public static final boolean A11Y_ADV_BOOL_FLASH_NOTIFICATION = true;
    public static final boolean A11Y_COLOR_BOOL_SUPPORT_AMOLED_DISPLAY = true;
    public static final boolean A11Y_COLOR_BOOL_SUPPORT_COLOR_BLIND = true;
    public static final boolean A11Y_COLOR_BOOL_SUPPORT_COLOR_FILTER = true;
    public static final boolean A11Y_COLOR_BOOL_SUPPORT_COLOR_FILTER_MDNIE_HW;
    public static final boolean A11Y_COLOR_BOOL_SUPPORT_COLOR_RELUMINO = true;
    public static final boolean A11Y_COLOR_BOOL_SUPPORT_DMC_COLORWEAKNESS;
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
    public static final boolean A11Y_COMMON_BOOL_SUPPORT_MULTI_FOLD = false;
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
    private static final int FIRST_API_LEVEL;
    static final String[] SELECT_PROJECTION;
    private static final String TAG = "A11yRune";
    static final int VALUE_INDEX = 2;
    private static Cursor cursor;
    private static ContentProviderClient mClient;
    private static String value;

    static {
        int i = SemSystemProperties.getInt("ro.product.first_api_level", 20);
        FIRST_API_LEVEL = i;
        boolean z = false;
        A11Y_COLOR_BOOL_SUPPORT_DMC_COLORWEAKNESS = false;
        A11Y_COLOR_BOOL_SUPPORT_COLOR_FILTER_MDNIE_HW = i < 35;
        if (SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_FRAMEWORK_SUPPORT_FOLDABLE_TYPE_FLIP") && SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_LOCKSCREEN_CONFIG_SUBDISPLAY_POLICY").contains("LARGESCREEN")) {
            z = true;
        }
        A11Y_COMMON_BOOL_SUPPORT_LARGE_COVER_SCREEN_FLIP = z;
        CONTENT_URI = Uri.parse("content://com.samsung.accessibility.provider/a11ysettings");
        SELECT_PROJECTION = new String[]{"_id", "name", "value"};
        value = null;
        cursor = null;
        mClient = null;
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x0035, code lost:
    
        if (r6 != null) goto L30;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x005e, code lost:
    
        return android.view.accessibility.A11yRune.value;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0059, code lost:
    
        r6.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x0046, code lost:
    
        if (r6 == null) goto L31;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x0057, code lost:
    
        if (r6 == null) goto L31;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String readDataFromAccessibilityProvider(android.content.Context r6, java.lang.String r7) {
        /*
            android.content.ContentResolver r6 = r6.getContentResolver()     // Catch: android.database.CursorIndexOutOfBoundsException -> L38 android.os.RemoteException -> L49 java.lang.Throwable -> L5f
            android.net.Uri r1 = android.view.accessibility.A11yRune.CONTENT_URI     // Catch: android.database.CursorIndexOutOfBoundsException -> L38 android.os.RemoteException -> L49 java.lang.Throwable -> L5f
            android.content.ContentProviderClient r0 = r6.acquireContentProviderClient(r1)     // Catch: android.database.CursorIndexOutOfBoundsException -> L38 android.os.RemoteException -> L49 java.lang.Throwable -> L5f
            android.view.accessibility.A11yRune.mClient = r0     // Catch: android.database.CursorIndexOutOfBoundsException -> L38 android.os.RemoteException -> L49 java.lang.Throwable -> L5f
            if (r0 == 0) goto L2c
            java.lang.String[] r2 = android.view.accessibility.A11yRune.SELECT_PROJECTION     // Catch: android.database.CursorIndexOutOfBoundsException -> L38 android.os.RemoteException -> L49 java.lang.Throwable -> L5f
            java.lang.String r3 = "name=?"
            java.lang.String[] r4 = new java.lang.String[]{r7}     // Catch: android.database.CursorIndexOutOfBoundsException -> L38 android.os.RemoteException -> L49 java.lang.Throwable -> L5f
            r5 = 0
            android.database.Cursor r6 = r0.query(r1, r2, r3, r4, r5)     // Catch: android.database.CursorIndexOutOfBoundsException -> L38 android.os.RemoteException -> L49 java.lang.Throwable -> L5f
            android.view.accessibility.A11yRune.cursor = r6     // Catch: android.database.CursorIndexOutOfBoundsException -> L38 android.os.RemoteException -> L49 java.lang.Throwable -> L5f
            if (r6 == 0) goto L2c
            r6.moveToFirst()     // Catch: android.database.CursorIndexOutOfBoundsException -> L38 android.os.RemoteException -> L49 java.lang.Throwable -> L5f
            android.database.Cursor r6 = android.view.accessibility.A11yRune.cursor     // Catch: android.database.CursorIndexOutOfBoundsException -> L38 android.os.RemoteException -> L49 java.lang.Throwable -> L5f
            r7 = 2
            java.lang.String r6 = r6.getString(r7)     // Catch: android.database.CursorIndexOutOfBoundsException -> L38 android.os.RemoteException -> L49 java.lang.Throwable -> L5f
            android.view.accessibility.A11yRune.value = r6     // Catch: android.database.CursorIndexOutOfBoundsException -> L38 android.os.RemoteException -> L49 java.lang.Throwable -> L5f
        L2c:
            android.database.Cursor r6 = android.view.accessibility.A11yRune.cursor
            if (r6 == 0) goto L33
            r6.close()
        L33:
            android.content.ContentProviderClient r6 = android.view.accessibility.A11yRune.mClient
            if (r6 == 0) goto L5c
            goto L59
        L38:
            r0 = move-exception
            r6 = r0
            r6.printStackTrace()     // Catch: java.lang.Throwable -> L5f
            android.database.Cursor r6 = android.view.accessibility.A11yRune.cursor
            if (r6 == 0) goto L44
            r6.close()
        L44:
            android.content.ContentProviderClient r6 = android.view.accessibility.A11yRune.mClient
            if (r6 == 0) goto L5c
            goto L59
        L49:
            r0 = move-exception
            r6 = r0
            r6.printStackTrace()     // Catch: java.lang.Throwable -> L5f
            android.database.Cursor r6 = android.view.accessibility.A11yRune.cursor
            if (r6 == 0) goto L55
            r6.close()
        L55:
            android.content.ContentProviderClient r6 = android.view.accessibility.A11yRune.mClient
            if (r6 == 0) goto L5c
        L59:
            r6.close()
        L5c:
            java.lang.String r6 = android.view.accessibility.A11yRune.value
            return r6
        L5f:
            r0 = move-exception
            r6 = r0
            android.database.Cursor r7 = android.view.accessibility.A11yRune.cursor
            if (r7 == 0) goto L68
            r7.close()
        L68:
            android.content.ContentProviderClient r7 = android.view.accessibility.A11yRune.mClient
            if (r7 == 0) goto L6f
            r7.close()
        L6f:
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: android.view.accessibility.A11yRune.readDataFromAccessibilityProvider(android.content.Context, java.lang.String):java.lang.String");
    }

    public static Uri getUriFor(Uri uri, String str) {
        return Uri.withAppendedPath(uri, str);
    }

    public static Uri getUriFor(String str) {
        return getUriFor(CONTENT_URI, str);
    }
}
