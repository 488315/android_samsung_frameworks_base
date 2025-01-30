package android.view.accessibility;

import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.net.Uri;
import android.os.RemoteException;
import com.samsung.android.feature.SemFloatingFeature;

/* loaded from: classes4.dex */
public class A11yRune {
  public static final boolean A11Y_ADV_BOOL_FLASH_NOTIFICATION = true;
  public static final boolean A11Y_COLOR_BOOL_SUPPORT_AMOLED_DISPLAY = true;
  public static final boolean A11Y_COLOR_BOOL_SUPPORT_COLOR_BLIND = true;
  public static final boolean A11Y_COLOR_BOOL_SUPPORT_COLOR_FILTER = true;
  public static final boolean A11Y_COLOR_BOOL_SUPPORT_COLOR_FILTER_NOT_MDNIE = false;
  public static final boolean A11Y_COLOR_BOOL_SUPPORT_COLOR_LENS = true;
  public static final boolean A11Y_COLOR_BOOL_SUPPORT_COLOR_RELUMINO = true;
  public static final boolean A11Y_COLOR_BOOL_SUPPORT_DMC_COLORWEAKNESS = true;
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

  static {
    A11Y_COMMON_BOOL_SUPPORT_LARGE_COVER_SCREEN_FLIP =
        SemFloatingFeature.getInstance()
                .getBoolean("SEC_FLOATING_FEATURE_FRAMEWORK_SUPPORT_FOLDABLE_TYPE_FLIP")
            && SemFloatingFeature.getInstance()
                .getString("SEC_FLOATING_FEATURE_LOCKSCREEN_CONFIG_SUBDISPLAY_POLICY")
                .contains("LARGESCREEN");
    CONTENT_URI = Uri.parse("content://com.samsung.accessibility.provider/a11ysettings");
    SELECT_PROJECTION = new String[] {"_id", "name", "value"};
    value = null;
    cursor = null;
    mClient = null;
  }

  /* JADX WARN: Code restructure failed: missing block: B:12:0x0035, code lost:

     if (r0 != null) goto L30;
  */
  /* JADX WARN: Code restructure failed: missing block: B:14:0x005e, code lost:

     return android.view.accessibility.A11yRune.value;
  */
  /* JADX WARN: Code restructure failed: missing block: B:16:0x0059, code lost:

     r0.close();
  */
  /* JADX WARN: Code restructure failed: missing block: B:31:0x0047, code lost:

     if (r0 == null) goto L31;
  */
  /* JADX WARN: Code restructure failed: missing block: B:38:0x0057, code lost:

     if (r0 == null) goto L31;
  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public static String readDataFromAccessibilityProvider(Context context, String key) {
    ContentProviderClient contentProviderClient;
    try {
      try {
        ContentResolver resolver = context.getContentResolver();
        Uri uri = CONTENT_URI;
        ContentProviderClient acquireContentProviderClient =
            resolver.acquireContentProviderClient(uri);
        mClient = acquireContentProviderClient;
        if (acquireContentProviderClient != null) {
          Cursor query =
              acquireContentProviderClient.query(
                  uri, SELECT_PROJECTION, "name=?", new String[] {key}, null);
          cursor = query;
          if (query != null) {
            query.moveToFirst();
            value = cursor.getString(2);
          }
        }
        Cursor cursor2 = cursor;
        if (cursor2 != null) {
          cursor2.close();
        }
        contentProviderClient = mClient;
      } catch (CursorIndexOutOfBoundsException e) {
        e.printStackTrace();
        Cursor cursor3 = cursor;
        if (cursor3 != null) {
          cursor3.close();
        }
        contentProviderClient = mClient;
      } catch (RemoteException e2) {
        e2.printStackTrace();
        Cursor cursor4 = cursor;
        if (cursor4 != null) {
          cursor4.close();
        }
        contentProviderClient = mClient;
      }
    } catch (Throwable th) {
      Cursor cursor5 = cursor;
      if (cursor5 != null) {
        cursor5.close();
      }
      ContentProviderClient contentProviderClient2 = mClient;
      if (contentProviderClient2 != null) {
        contentProviderClient2.close();
      }
      throw th;
    }
  }

  public static Uri getUriFor(Uri uri, String name) {
    return Uri.withAppendedPath(uri, name);
  }

  public static Uri getUriFor(String name) {
    return getUriFor(CONTENT_URI, name);
  }
}
