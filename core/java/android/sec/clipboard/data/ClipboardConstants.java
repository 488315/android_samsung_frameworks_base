package android.sec.clipboard.data;

import android.net.Uri;
import android.os.Build;
import android.os.SystemProperties;

/* loaded from: classes3.dex */
public class ClipboardConstants {
  public static final int ADD_ITEM = 1;
  public static final int CAT_USERID_ADJUST_FACTOR = 1000;
  public static final String CLIPBOARD_AUTH =
      "com.samsung.android.honeyboard.provider.RichcontentProvider";
  public static final String CLIPBOARD_DELETE_CLIPS_PATH = "/data/semclipboard/deletedClips.xml";
  public static final String CLIPBOARD_DRAGNDROP = "clipboarddragNdrop";
  public static final String CLIPBOARD_REMOTE_FILE = "remote_file";
  public static final String CLIPBOARD_REMOTE_PATH = "/data/semclipboard/remote";
  public static final String CLIPBOARD_REMOTE_SEND_PATH = "/data/semclipboard/remote_send";
  public static final String CLIPBOARD_ROOT_PATH = "/data/semclipboard";
  public static final String CLIPBOARD_ROOT_PATH_TEMP = "/data/semclipboard/temp/";
  public static final String CLIPBOARD_SHARED_PATH = "/shared";
  public static final String CLIPBOARD_URI =
      "content://com.samsung.android.honeyboard.provider.RichcontentProvider/clipboard";
  public static final String CLIP_HTML = "clip_html";
  public static final String CLIP_LABEL = "clip_label";
  public static final String CLIP_MIMETYPES = "clip_mimetypes";
  public static final String CLIP_TEXT = "clip_text";
  public static final String CLIP_URI = "clip_uri";
  public static final String CLIP_URI_LIST = "clip_uri_list";
  public static final String COLUMN_CALLER_APP_UID = "caller_app_uid";
  public static final String COLUMN_EXTRA_STR_1 = "extra_str1";
  public static final String COLUMN_EXTRA_STR_2 = "extra_str2";
  public static final String COLUMN_LOCKED = "locked";
  public static final String COLUMN_TIMESTAMP = "time_stamp";
  public static final String COLUMN_TYPE = "type";
  public static final String COLUMN_USER_ID = "user_id";
  public static final int DELETEALL_ITEM = 3;
  public static final int DELETE_ACTION_ALL = 3;
  public static final int DELETE_ACTION_CLIP = 2;
  public static final int DELETE_ACTION_NONE = 1;
  public static final int DELETE_ITEM = 2;
  public static final int DFP_UNSUPPORTED_FILE_SIZE = 1000;
  public static final String DIRECT_CLIP = "direct_clip";
  public static final int DOP_CONNECTED = 3;
  public static final String DOP_CONNECTION_STATE = "dexonpc_connection_state";
  public static final String DOP_LABEL_FILE = "startDoPDrag";
  public static final String DOP_LABEL_TEXT_HTML = "startDoPCopy";
  public static final int DUAL_MESSENGER_USERID_END = 99;
  public static final int DUAL_MESSENGER_USERID_START = 95;
  public static final String HTML_PREVIEW_IMAGE_NAME = "previewhtmlclipboarditem";
  public static final String INTENT_RESPONSE_RESTORE_CLIPBOARD =
      "com.samsung.android.intent.action.RESPONSE_RESTORE_CLIP_BOARD";
  public static final String IS_MIGRATION = "is_migration";
  public static final String KNOX_CLIPBOARD_DELETE_CLIPS_PATH =
      "/com.sec.knox.bridge/shared_prefs/deletedClips.xml";
  public static final int LOG_LEN = 20;
  public static final int MAX_DATA_COUNT = 40;
  public static final int MAX_DATA_LOCK_COUNT = 20;
  public static final String MDE_EXTRA_VALUE = "com.microsoft.appmanager";
  public static final String MULTIWINDOW_DRAGNDROP = "MultiWindow_DragDrop";
  public static final int MULTI_USERID_END = 99;
  public static final int MULTI_USERID_START = 10;
  public static final int NORMAL_CLIP = 0;
  public static final int PC_CLIP = 1;
  public static final String PC_CLIP_EXTRA_VALUE = "PCCLIP";
  public static final int PERSONA_CATEGORY_END = 1194;
  public static final int PERSONA_CATEGORY_START = 1010;
  public static final int PERSONA_USERID_END = 194;
  public static final int PERSONA_USERID_START = 10;
  public static final int PROTECTION_SET = 1;
  public static final int PROTECTION_UNCHANGE = -1;
  public static final int PROTECTION_UNSET = 0;
  public static final String RCP_AUTHORITY = "com.sec.knox.rcppolicyprovider";
  public static final String RCP_TABLE_NAME = "RCP_DATA";
  public static final int SAFETY_STRING_LENGTH = 131072;
  public static final String THUMBNAIL_SUFFIX = "_thum.jpg";
  public static final String USER_ADDED = "ADDED";
  public static final String USER_REMOVED = "REMOVED";
  public static final String USER_SWITCHED = "SWITCHED";
  public static boolean DEBUG = "eng".equals(Build.TYPE);
  public static boolean INFO_DEBUG = "eng".equals(Build.TYPE);
  public static final boolean KNOX_V1_ENABLED =
      "1".equals(SystemProperties.get("ro.config.knox", "0"));
  public static final boolean KNOX_V2_ENABLED =
      "v30".equals(SystemProperties.get("ro.config.knox", "0"));
  public static final Uri CLIPBOARD_ALLOWED_URI =
      Uri.parse("content://com.sec.knox.provider/RestrictionPolicy1/isClipboardAllowed");
  public static final Uri CLIPBOARD_SHARED_ALLOWED_URI =
      Uri.parse("content://com.sec.knox.provider/RestrictionPolicy1/isClipboardShareAllowed");
  public static final String RCP_URL = "content://com.sec.knox.rcppolicyprovider/RCP_DATA";
  public static final Uri RCP_CONTENT_URI = Uri.parse(RCP_URL);
  public static final Uri CLIPBOARD_ALLOWED_DENYLIST_APP_URI =
      Uri.parse(
          "content://com.sec.knox.provider2/ApplicationPolicy/getPackagesFromDisableClipboardBlackList");
  public static final Uri CLIPBOARD_ALLOWED_ALLOWLIST_APP_URI =
      Uri.parse(
          "content://com.sec.knox.provider2/ApplicationPolicy/getPackagesFromDisableClipboardWhiteList");
}
