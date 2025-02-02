package com.samsung.android.share;

import android.net.Uri;
import com.samsung.android.feature.SemFloatingFeature;

/* loaded from: classes5.dex */
public class SemShareConstants {
  public static final String ACTION_REQ_AUTH =
      "com.samsung.android.coreapps.easysignup.ACTION_REQ_AUTH";
  public static final String DIRECT_SHARE_SETTINGS_KEY = "direct_share";
  public static final int DISABLE = 0;
  public static final int DMA_SUPPORT_VERSION = 540000000;
  public static final String DMA_SURVEY_DETAIL_INCLUDE_QUIS_VALUE_OFF = "1";
  public static final String DMA_SURVEY_DETAIL_INCLUDE_QUIS_VALUE_ON = "0";
  public static final String DMA_SURVEY_DETAIL_NEARBY_SHARE_VALUE_APP_ITEM = "a";
  public static final String DMA_SURVEY_DETAIL_NEARBY_SHARE_VALUE_NORMAL_ITEM = "b";
  public static final String DMA_SURVEY_DETAIL_NEARBY_SHARE_VALUE_UWB_ITEM = "c";
  public static final String DMA_SURVEY_DETAIL_PRELOAD_SERVICES_VALUE_NEARBY_SHARE = "b";
  public static final String DMA_SURVEY_DETAIL_PROFILE_VALUE_PERSONAL = "a";
  public static final String DMA_SURVEY_DETAIL_PROFILE_VALUE_WORK = "b";
  public static final String DMA_SURVEY_DETAIL_QUICK_SHARE_VALUE_APP_ITEM = "a";
  public static final String DMA_SURVEY_DETAIL_QUICK_SHARE_VALUE_NORMAL_ITEM = "b";
  public static final String DMA_SURVEY_DETAIL_QUICK_SHARE_VALUE_UWB_ITEM = "c";
  public static final String DMA_SURVEY_DETAIL_REMOVE_EXIF_VALUE_OFF = "0";
  public static final String DMA_SURVEY_DETAIL_REMOVE_EXIF_VALUE_ON = "1";
  public static final String DMA_SURVEY_DETAIL_RESOLVER_ONCE_ALWAYS_VALUE_ALWAYS = "0";
  public static final String DMA_SURVEY_DETAIL_RESOLVER_ONCE_ALWAYS_VALUE_ONCE = "1";
  public static final String DMA_SURVEY_DETAIL_SHAREVIA_VALUE_ALL_APPS = "e";
  public static final String DMA_SURVEY_DETAIL_SHAREVIA_VALUE_DIRECT_SHARE = "c";
  public static final String DMA_SURVEY_DETAIL_SHAREVIA_VALUE_SHARE_LIVE = "a";
  public static final String DMA_SURVEY_DETAIL_SHAREVIA_VALUE_SHARE_NEARBY_SHARE = "b";
  public static final String DMA_SURVEY_DETAIL_SHAREVIA_VALUE_SUGGESTED_APPS = "d";
  public static final String DMA_SURVEY_DETAIL_TRACKING_ID = "4G7-399-565156";
  public static final String DMA_SURVEY_DMA_ACTION =
      "com.sec.android.diagmonagent.intent.USE_APP_FEATURE_SURVEY";
  public static final String DMA_SURVEY_DMA_PACKAGE = "com.sec.android.diagmonagent";
  public static final String DMA_SURVEY_FEATURE_ALL_APPS = "ALAP";
  public static final String DMA_SURVEY_FEATURE_APPLIST = "APPP";
  public static final String DMA_SURVEY_FEATURE_CALLER = "CALL";
  public static final String DMA_SURVEY_FEATURE_CROSSAPP = "CROS";
  public static final String DMA_SURVEY_FEATURE_DIRECTSHARE = "DEEP";
  public static final String DMA_SURVEY_FEATURE_DISMISS_ACTION = "DISS";
  public static final String DMA_SURVEY_FEATURE_DISMISS_ACTION_VALUE_BACK = "0";
  public static final String DMA_SURVEY_FEATURE_DISMISS_ACTION_VALUE_DRAG = "1";
  public static final String DMA_SURVEY_FEATURE_EDIT_APPS = "EDAP";
  public static final String DMA_SURVEY_FEATURE_EDIT_APPS_ADD = "0";
  public static final String DMA_SURVEY_FEATURE_EDIT_APPS_REMOVE = "1";
  public static final String DMA_SURVEY_FEATURE_EDIT_CONVERT_OPTION = "CONV_OPT";
  public static final String DMA_SURVEY_FEATURE_EDIT_CONVERT_OPTION_VALUE_OFF = "1";
  public static final String DMA_SURVEY_FEATURE_EDIT_CONVERT_OPTION_VALUE_ON = "0";
  public static final String DMA_SURVEY_FEATURE_EDIT_CONVERT_POPUP = "CONV";
  public static final String DMA_SURVEY_FEATURE_EDIT_CONVERT_POPUP_VALUE = "1";
  public static final String DMA_SURVEY_FEATURE_ENTER_EDIT = "EDIT";
  public static final String DMA_SURVEY_FEATURE_ENTER_EDIT_VALUE = "1";
  public static final String DMA_SURVEY_FEATURE_FAVORITE_APPS_COUNT = "FVAP";
  public static final String DMA_SURVEY_FEATURE_INCLUDE_QUIS = "QUIS";
  public static final String DMA_SURVEY_FEATURE_INTENT_MIME_TYPE = "TYPE";
  public static final String DMA_SURVEY_FEATURE_NEARBY_SHARE = "NEAR";
  public static final String DMA_SURVEY_FEATURE_OPEN = "OPEN";
  public static final String DMA_SURVEY_FEATURE_PINNED_COUNT = "PNCT";
  public static final String DMA_SURVEY_FEATURE_PIN_APPS = "PINN";
  public static final String DMA_SURVEY_FEATURE_PRELOAD_SERVICES = "SEVC";
  public static final String DMA_SURVEY_FEATURE_PRIVATE_SHARE_CATEGORY = "PV_SHR";
  public static final String DMA_SURVEY_FEATURE_PRIVATE_SHARE_DETECT = "PV_SHR_DETECT";
  public static final String DMA_SURVEY_FEATURE_PROFILE = "PROF";
  public static final String DMA_SURVEY_FEATURE_QUICK_SHARE = "QUIK";
  public static final String DMA_SURVEY_FEATURE_REMOVE_EXIF = "EXIF";
  public static final String DMA_SURVEY_FEATURE_RESOLVER_ONCE_ALWAYS = "RVOA";
  public static final String DMA_SURVEY_FEATURE_RESOLVER_PICKED_APP = "RVAP";
  public static final String DMA_SURVEY_FEATURE_SHAREVIA = "SHAR";
  public static final String DMA_SURVEY_FEATURE_SMART_SHARE_KEY_DETAIL = "Detail";
  public static final String DMA_SURVEY_FEATURE_SMART_SHARE_KEY_EFFECT = "Effect";
  public static final String DMA_SURVEY_FEATURE_SMART_SHARE_KEY_TARGET_APP = "Target";
  public static final String DMA_SURVEY_FEATURE_SMART_SHARE_NO_EFFECT = "None";
  public static final String DMA_SURVEY_FEATURE_SMART_SHARE_SHOW_SUGGESTIONS = "SS_TRAY";
  public static final String DMA_SURVEY_FEATURE_SMART_SHARE_SUGGESTION_APPLIED = "SS_APLY";
  public static final String DMA_SURVEY_FEATURE_SMART_SHARE_TOGGLE_OFF = "SS_TGOF";
  public static final String DMA_SURVEY_FEATURE_SMART_SHARE_TOGGLE_ON = "SS_TGON";
  public static final String DMA_SURVEY_FEATURE_SMART_SHARE_TOTAL_SHARE = "Total";
  public static final String DMA_SURVEY_FEATURE_SUGGESTED_APPS = "SUGS";
  public static final String DMA_SURVEY_FEATURE_THUMBNAIL_ACTION = "THUM";
  public static final String DMA_SURVEY_FEATURE_TRACKING_ID = "tracking_id";
  public static final String DMA_SURVEY_STYLE_CROSSAPP = "%s_to_%s";
  public static final String DMA_SURVEY_STYLE_UNKNOWN_PACKAGE = "UnknownPackage";
  public static final int ENABLE = 1;
  public static final boolean ENABLE_SURVEY_MODE = true;
  public static final String EXTRA_CHOOSER_CONTENT_COUNT = "sem_extra_chooser_content_count";
  public static final String EXTRA_CHOOSER_CONTENT_SIZE = "sem_extra_chooser_content_size";
  public static final String EXTRA_CHOOSER_CONVERT_VIDEO_OPTION =
      "sem_extra_chooser_convert_video_option";
  public static final String EXTRA_CHOOSER_DROPLIST = "extra_chooser_droplist";
  public static final String EXTRA_CHOOSER_ENABLE_LARGE_PREVIEW =
      "sem_extra_chooser_enable_large_preview";
  public static final String EXTRA_CHOOSER_SLICE_PROVIDER_URI =
      "sem_extra_chooser_slice_provider_uri";
  public static final String EXTRA_CHOOSER_SLOW_MOTION_ORIGINAL_URI =
      "sem_extra_chooser_slow_motion_original_uri";
  public static final String EXTRA_CHOOSER_TRANSCODE_OPTION = "sem_extra_chooser_option_transcode";
  public static final String EXTRA_SAFE_FORWARD = "extra_safe_forward";
  public static final String GALLERY_PACKAGE = "com.sec.android.gallery3d";
  public static final String HTTP_CONN_REQUEST_METHOD = "GET";
  public static final String HTTP_CONN_USER_AGENT =
      "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko)"
          + " Chrome/51.0.2704.103 Safari/537.36";
  public static final int HTTP_CONN_WEB_PREVIEW_TIMEOUT = 10000;
  public static final String INTENT_ACTION_LAUNCHER_CHANGED =
      "com.sec.android.intent.action.LAUNCHER_CHANGED";
  public static final String INTENT_ACTION_SHARELINK_SEND =
      "com.samsung.android.app.simplesharing.action.REQUEST_LINK_SEND";
  public static final String INTENT_ACTION_SHARELINK_TIP =
      "com.samsung.android.app.simplesharing.intent.ACTION_VIEW_TIP_VIEW";
  public static final String INTENT_ACTION_SHARE_LIVE_EXTRA_INTENT =
      "com.samsung.intent.chooser.EXTRA_INTENT";
  public static final String INTENT_ACTION_SHARE_LIVE_TIP_VIEW =
      "com.samsung.android.app.sharelive.action.TIP_VIEW";
  public static final String INTENT_ACTION_TARGET_DEVICE_UPDATE =
      "com.samsung.android.chooser.DEVICE_UPDATE";
  public static final String INTENT_EXTRA_APP_LIST = "extra_app_list";
  public static final String INTENT_EXTRA_CALLER_RESOLVE_INFO_LIST =
      "extra_caller_resolveinfo_list";
  public static final String INTENT_EXTRA_CALLER_TARGET_LIST = "extra_caller_target_list";
  public static final String INTENT_EXTRA_CHOOSER_CONVERT_VIDEO_OPTION_RESULT =
      "sem_extra_chooser_option_convert_compatible_video";
  public static final String INTENT_EXTRA_CHOOSER_INCLUDE_ORIGINAL_OPTION_RESULT =
      "sem_extra_chooser_option_include_original_result";
  public static final String INTENT_EXTRA_CHOOSER_SHARE_DEVICE_ID = "deviceId";
  public static final String INTENT_EXTRA_CHOOSER_SHARE_LIVE_TIP = "only_d2d_tip";
  public static final String INTENT_EXTRA_CHOOSER_SHARE_PROGRESS =
      "com.samsung.intent.chooser.SHARE_PROGRESS";
  public static final String INTENT_EXTRA_CHOOSER_SHARE_SHARE_ID = "shareId";
  public static final String INTENT_EXTRA_CHOOSER_SHARE_STATUS =
      "com.samsung.intent.chooser.SHARE_STATUS";
  public static final String INTENT_EXTRA_CHOOSER_SHARE_STATUS_LABEL =
      "com.samsung.intent.chooser.SHARE_STATUS_LABEL";
  public static final String INTENT_EXTRA_SHARE_UWB_AOA =
      "com.samsung.intent.chooser.SHARE_UWB_AOA";
  public static final String INTENT_EXTRA_SHARE_UWB_DEVICE =
      "com.samsung.intent.chooser.SHARE_UWB_DEVICE";
  public static final float MAX_FONT_SCALE = 1.2f;
  public static final String METADATA_DISPLAY_PRIORITY_KEY = "com.sec.android.display_priority";
  public static final String METADATA_RESOLVER_RANKING_PRIORITY_KEY =
      "com.samsung.android.resolver.ranking_priority";
  public static final String METADATA_SUBSTITUTE_SHARE_TARGET_APP_NAME_AND_ICON =
      "com.samsung.android.SUBSTITUTE_SHARE_TARGET_APP_NAME_AND_ICON";
  public static final String NEARBY_SHARE_COMP =
      "com.google.android.gms/com.google.android.gms.nearby.sharing.ShareSheetActivity";
  public static final String NEARBY_SHARE_INTERNAL_SHARE_ACTIVITY =
      "com.google.android.gms.nearby.sharing.InternalShareSheetActivity";
  public static final String NEARBY_SHARE_PKG = "com.google.android.gms";
  public static final String PRIVATE_SHARE_CLASS = "com.samsung.android.privacy.view.MainActivity";
  public static final String PRIVATE_SHARE_COMP =
      "com.samsung.android.privateshare/com.samsung.android.privacy.view.MainActivity";
  public static final String PRIVATE_SHARE_PKG = "com.samsung.android.privateshare";
  public static final String QUICK_CONNECT_PKG = "com.samsung.android.oneconnect";
  public static final String REGULAR_CONTENT = "content=[\"'](.*?)[\"']";
  public static final String REGULAR_ITEMPROP_IMAGE = "itemprop=[\"']image[\"']";
  public static final String REGULAR_META_TAG = "<meta(.*?)>";
  public static final String REGULAR_NAME_DESCRIPTION = "name=[\"']description[\"']";
  public static final String REGULAR_NAME_TITLE = "name=title";
  public static final String REGULAR_PROPERTY_OG_DESCRIPTION = "property=[\"']og:description[\"']";
  public static final String REGULAR_PROPERTY_OG_IMAGE = "property=[\"']og:image[\"']";
  public static final String REGULAR_PROPERTY_OG_TITLE = "property=og:title";
  public static final String REGULAR_TAG_DESCRIPTION = "<description>(.*?)</description>";
  public static final String REGULAR_TAG_TITLE = "<title>(.*?)</title>";
  public static final String SAMSUNG_CONNECT_COMP =
      "com.samsung.android.oneconnect/com.samsung.android.oneconnect.ui.contentssharing.ContentsSharingActivity";
  public static final int SAMSUNG_CONNECT_VERSION_SUPPORTING_SHARE_TO_DEVICE = 150000000;
  public static final String SECURE_SETTINGS_MULTI_DEVICE_PACKAGE =
      "sem_chooser_multi_device_support_pkg";
  public static final String SELECT_APP_CR_CALL_ARG_CHOOSER = "Chooser";
  public static final String SELECT_APP_CR_CALL_METHOD =
      "com.samsung.android.settings.share.GetData";
  public static final String SELECT_APP_KEY_ACTIVITY_NAME = "activityName";
  public static final String SELECT_APP_KEY_ALL_APPS_ARRAY_RESULT = "all_array_result";
  public static final String SELECT_APP_KEY_APPLICATION_LABEL = "applicationLabel";
  public static final String SELECT_APP_KEY_ARRAY_RESULT = "array_result";
  public static final String SELECT_APP_KEY_MAIN_LABEL = "main_label";
  public static final String SELECT_APP_KEY_PACKAGE_NAME = "packageName";
  public static final String SELECT_APP_KEY_RANK_APP_ITEM_ARRAY = "rank_app_item_array";
  public static final String SELECT_APP_KEY_RESOLVED_LABEL = "resolvedLabel";
  public static final String SELECT_APP_KEY_SUB_LABEL = "sub_label";
  public static final String SELECT_APP_KEY_USER_ID = "uid";
  public static final String SELECT_APP_PROVIDER_AUTHORITY =
      "com.samsung.android.settings.share.ShareOptionProvider";
  public static final String SELECT_APP_SCREEN_CLASS =
      "com.samsung.android.settings.share.SelectAppActivity";
  public static final String SELECT_APP_SCREEN_PKG = "com.android.settings";
  public static final String SELECT_LABEL_CR_CALL_METHOD =
      "com.samsung.android.settings.share.label.GetData";
  public static final String SELECT_LAST_LOCALE = "last_locale";
  public static final String SEM_PIN_CORRUPT_KEY_SUFFIX = "_corrupt";
  public static final String SEM_SHARED_ELEMENT_CHOOSER_PREVIEW_IMAGE =
      "sem_shared_element_chooser_preview_image";
  public static final int SEP_VERSION_8_5 = 80500;
  public static final String SHARED_ALBUM_COMP =
      "com.sec.android.gallery3d/com.samsung.android.gallery.app.activity.SharingsAlbumChoiceActivity";
  public static final String SHARE_LIVE_COMP =
      "com.samsung.android.app.sharelive/com.samsung.android.app.sharelive.presentation.main.MainActivity";
  public static final String SHARE_LIVE_MORE_BUTTON_DEVICE_ID = "more_button";
  public static final String SHARE_LIVE_PKG = "com.samsung.android.app.sharelive";
  public static final String SHARE_STAR_AUTHORITY =
      "com.samsung.android.app.sharestar.ShareStarProvider";
  public static final String SHARE_STAR_CR_CALL_ARG_CHOOSER = "Chooser";
  public static final String SHARE_STAR_CR_CALL_ARG_RESOLVER = "Resolver";
  public static final String SHARE_STAR_CR_CALL_METHOD =
      "com.samsung.android.app.sharestar.GetData";
  public static final String SHARE_STAR_KEY_ACTIVITYNAME = "activityName";
  public static final String SHARE_STAR_KEY_ADD_SELL_ALL_APPS_ITEM = "add_see_all_apps_item";
  public static final String SHARE_STAR_KEY_APPLABEL = "appLabel";
  public static final String SHARE_STAR_KEY_ARRAY_RESULT = "array_result";
  public static final String SHARE_STAR_KEY_DIRECT_FAVORITE_ITEM_ARRAY =
      "direct_favorite_item_array";
  public static final String SHARE_STAR_KEY_DROP_APP_ITEM_ARRAY = "drop_app_item_array";
  public static final String SHARE_STAR_KEY_EXCLUDE_APP_ITEM_ARRAY = "exclude_app_item_array";
  public static final String SHARE_STAR_KEY_ITEMLABEL = "itemLabel";
  public static final String SHARE_STAR_KEY_MAX_RANK_ITEMS_SIZE = "max_rank_items_size";
  public static final String SHARE_STAR_KEY_MAX_TARGET_PER_APP = "max_target_per_app";
  public static final String SHARE_STAR_KEY_PACKAGENAME = "packageName";
  public static final String SHARE_STAR_KEY_RANK_APP_ITEM_ARRAY = "rank_app_item_array";
  public static final String SHARE_STAR_KEY_SCORE = "score";
  public static final String SHARE_STAR_KEY_SHOW_ALL_APP_ALPHABET_ORDER_LIST =
      "show_all_app_alphabet_order_list";
  public static final String SHARE_STAR_KEY_SHOW_COPTY_BUTTON = "show_copy_button";
  public static final String SHARE_STAR_KEY_SHOW_LOCATION_TAG_BUTTON = "show_location_tag_button";
  public static final String SHARE_STAR_KEY_SHOW_NEARBY_CHIP_BUTTON = "show_nearby_chip_button";
  public static final String SHARE_STAR_KEY_SHOW_NEARYBY = "show_nearby";
  public static final String SHARE_STAR_KEY_SHOW_PREVIEW_INNER_VIEW = "show_preview_inner_view";
  public static final String SHARE_STAR_KEY_SHOW_QUICK_SHARE_DEVICES =
      "show_quickshare_on_direct_share";
  public static final String SHARE_STAR_KEY_SHOW_SHARE_LINK_CHIP_BUTTON =
      "show_share_link_chip_button";
  public static final String SHARE_STAR_KEY_SHOW_SHARE_LIVE = "show_share_live";
  public static final String SHARE_STAR_KEY_SHOW_TITLE_PREVIEW = "show_title_preview";
  public static final String SHARE_STAR_KEY_SUPPORT_ONE_UI_VERSION =
      "share_star_support_one_ui_version";
  public static final String SHARE_STAR_PKG = "com.samsung.android.app.homestar";
  public static final String SHARE_STAR_SEE_ALL_CLS =
      "com.samsung.android.app.sharestar.activities.SeeAllAppsActivity";
  public static final int SHARE_STAR_SUPPORT_ONE_UI_VERSION = 40;
  public static final String SIMPLE_SHARING_COMP =
      "com.samsung.android.app.simplesharing/com.samsung.android.app.simplesharing.linksharing.ui.broker.BrokerActivity";
  public static final String SIMPLE_SHARING_FORCE_DISABLE = "simple_sharing_force_disable";
  public static final String SIMPLE_SHARING_PANEL_COMPONENT_CLASS_NAME =
      "com.samsung.android.app.simplesharing.sharepanel.SharePanelComponent";
  public static final String SIMPLE_SHARING_PKG = "com.samsung.android.app.simplesharing";
  public static final String SMART_VIEW_APPNAME = "Smart View";
  public static final String SMART_VIEW_COMP =
      "com.samsung.android.smartmirroring/com.samsung.android.smartmirroring.CastingDialog";
  public static final int SUPPORT_BIXBY;
  public static final int SUPPORT_DEVICE_SHARE;
  public static final int SUPPORT_DIRECT_SHARE;
  public static final int SUPPORT_FEATURE_BASE = 1;
  public static final int SUPPORT_LOGGING;
  public static final int SUPPORT_RESOLVER_GUIDE;
  public static final int SUPPORT_SHARE_LINK;
  private static int SUPPORT_SHIFT = 0;
  public static final int SUPPORT_SHOW_BUTTON_SHAPES;
  public static final String SURVERY_ACTION =
      "com.samsung.android.providers.context.log.action.USE_APP_FEATURE_SURVEY";
  public static final String SURVERY_EXTRA_DATA = "data";
  public static final String SURVERY_EXTRA_OWN_PACKAGE = "pkg_name";
  public static final String SURVERY_EXTRA_OWN_PACKAGE_VALUE = "sharevia";
  public static final String SURVERY_PERMISSION =
      "com.samsung.android.providers.context.permission.WRITE_USE_APP_FEATURE_SURVEY";
  public static final String SURVEY_APP_NAME = "com.android.internal.app.resolveractivity";
  public static final String SURVEY_CONTENT_APPID = "app_id";
  public static final String SURVEY_CONTENT_DIMENSION = "dimension";
  public static final String SURVEY_CONTENT_EXTRA = "extra";
  public static final String SURVEY_CONTENT_FEATURE = "feature";
  public static final String SURVEY_CONTENT_TYPE = "type";
  public static final String SURVEY_CONTENT_TYPE_VALUE = "ev";
  public static final String SURVEY_FEATURE_APPLIST = "APPP";
  public static final String SURVEY_FEATURE_CROSSAPP = "CROS";
  public static final String SURVEY_FEATURE_DIRECTSHARE = "DEEP";
  public static final String SURVEY_FEATURE_MOREACTION = "MORE";
  public static final String SURVEY_FEATURE_SECURE_FOLDER = "SVSF";
  public static final String SURVEY_FEATURE_SHARELINK = "SLNK";
  public static final String SURVEY_TARGET_PACKAGE = "com.samsung.android.providers.context";
  public static final int TRANSITION_ENTER_TYPE = 0;
  public static final int TRANSITION_EXIT_TYPE = 1;
  public static final boolean ENABLE_BIXBY =
      SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_COMMON_SUPPORT_BIXBY");
  public static final int SUPPORT_BUTTONS = 1 << 1;
  public static final String[] DMA_SURVEY_FEATURE_THUMBNAIL_ACTION_TYPES = {
    "Select", "Deselect", "Flick"
  };
  public static final String[] DMA_SURVEY_FEATURE_SMART_SHARE_EFFECTS = {
    "Remaster", "Crop", "Tilt", "Rotate"
  };
  public static final boolean ENABLE_QUICKCONNECT_D2D =
      SemFloatingFeature.getInstance()
          .getBoolean("SEC_FLOATING_FEATURE_QUICKCONNECT_SUPPORT_D2D", false);
  public static final Uri QUICK_SHARE_SLICE_URI =
      Uri.parse("content://com.samsung.android.app.sharelive.sliceprovider");
  public static final Uri QUICK_SHARE_TRANSCODE_SLICE_URI =
      Uri.parse("content://com.samsung.android.app.sharelive.sliceprovider/transcoding");
  public static final Uri NEARBY_SHARE_SLICE_URI =
      Uri.parse("content://com.google.android.gms.nearby.sharing/scan");
  public static final Uri SHARE_STAR_AUTHORITY_URI =
      Uri.parse("content://com.samsung.android.app.sharestar.ShareStarProvider");
  public static final Uri SELECT_APP_PROVIDER_AUTHORITY_URI =
      Uri.parse("content://com.samsung.android.settings.share.ShareOptionProvider");

  static {
    SUPPORT_SHIFT = 1;
    int i = 1 + 1;
    SUPPORT_SHIFT = i;
    int i2 = i + 1;
    SUPPORT_SHIFT = i2;
    SUPPORT_SHOW_BUTTON_SHAPES = 1 << i;
    int i3 = i2 + 1;
    SUPPORT_SHIFT = i3;
    SUPPORT_RESOLVER_GUIDE = 1 << i2;
    int i4 = i3 + 1;
    SUPPORT_SHIFT = i4;
    SUPPORT_LOGGING = 1 << i3;
    int i5 = i4 + 1;
    SUPPORT_SHIFT = i5;
    SUPPORT_SHARE_LINK = 1 << i4;
    int i6 = i5 + 1;
    SUPPORT_SHIFT = i6;
    SUPPORT_BIXBY = 1 << i5;
    int i7 = i6 + 1;
    SUPPORT_SHIFT = i7;
    SUPPORT_DEVICE_SHARE = 1 << i6;
    SUPPORT_SHIFT = i7 + 1;
    SUPPORT_DIRECT_SHARE = 1 << i7;
  }
}
