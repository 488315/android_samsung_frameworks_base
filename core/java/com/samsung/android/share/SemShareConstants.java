package com.samsung.android.share;

import android.net.Uri;

import com.samsung.android.feature.SemFloatingFeature;

public class SemShareConstants {
    public static final String ACTION_REQ_AUTH =
            "com.samsung.android.coreapps.easysignup.ACTION_REQ_AUTH";
    public static final int DISABLE = 0;
    public static final String DMA_SURVEY_DETAIL_TRACKING_ID = "4G7-399-565156";
    public static final String DMA_SURVEY_DMA_ACTION =
            "com.sec.android.diagmonagent.intent.USE_APP_FEATURE_SURVEY";
    public static final String DMA_SURVEY_DMA_PACKAGE = "com.sec.android.diagmonagent";
    public static final String DMA_SURVEY_FEATURE_RESOLVER = "RSVL";
    public static final String DMA_SURVEY_FEATURE_TRACKING_ID = "tracking_id";
    public static final String DMA_SURVEY_KEY_RESOLVER_ACTION = "act";
    public static final String DMA_SURVEY_KEY_RESOLVER_CALLEE = "callee";
    public static final String DMA_SURVEY_KEY_RESOLVER_CALLER = "caller";
    public static final String DMA_SURVEY_KEY_RESOLVER_MIME = "mime";
    public static final String DMA_SURVEY_KEY_RESOLVER_MIME_CALLEE = "mime_callee";
    public static final String DMA_SURVEY_KEY_RESOLVER_ONCE_ALWAYS = "onc_alwy";
    public static final String DMA_SURVEY_VALUE_RESOLVER_ONCE_ALWAYS_ALWAYS = "0";
    public static final String DMA_SURVEY_VALUE_RESOLVER_ONCE_ALWAYS_ONCE = "1";
    public static final int ENABLE = 1;
    public static final boolean ENABLE_BIXBY =
            SemFloatingFeature.getInstance()
                    .getBoolean("SEC_FLOATING_FEATURE_COMMON_SUPPORT_BIXBY");
    public static final boolean ENABLE_QUICKCONNECT_D2D;
    public static final boolean ENABLE_SURVEY_MODE = true;
    public static final String GALLERY_PACKAGE = "com.sec.android.gallery3d";
    public static final String HTTP_CONN_REQUEST_METHOD = "GET";
    public static final String HTTP_CONN_USER_AGENT =
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko)"
                + " Chrome/51.0.2704.103 Safari/537.36";
    public static final int HTTP_CONN_WEB_PREVIEW_TIMEOUT = 10000;
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
    public static final String METADATA_RESOLVER_RANKING_PRIORITY_KEY =
            "com.samsung.android.resolver.ranking_priority";
    public static final String NEARBY_SHARE_COMP =
            "com.google.android.gms/com.google.android.gms.nearby.sharing.ShareSheetActivity";
    public static final String NEARBY_SHARE_INTERNAL_SHARE_ACTIVITY =
            "com.google.android.gms.nearby.sharing.InternalShareSheetActivity";
    public static final String NEARBY_SHARE_PKG = "com.google.android.gms";
    public static final Uri NEARBY_SHARE_SLICE_URI;
    public static final String PRIVATE_SHARE_CLASS =
            "com.samsung.android.privacy.view.MainActivity";
    public static final String PRIVATE_SHARE_COMP =
            "com.samsung.android.privateshare/com.samsung.android.privacy.view.MainActivity";
    public static final String PRIVATE_SHARE_PKG = "com.samsung.android.privateshare";
    public static final String QUICK_CONNECT_PKG = "com.samsung.android.oneconnect";
    public static final Uri QUICK_SHARE_SLICE_URI;
    public static final Uri QUICK_SHARE_TRANSCODE_SLICE_URI;
    public static final String REGULAR_CONTENT = "content=[\"'](.*?)[\"']";
    public static final String REGULAR_ITEMPROP_IMAGE = "itemprop=[\"']image[\"']";
    public static final String REGULAR_META_TAG = "<meta(.*?)>";
    public static final String REGULAR_NAME_DESCRIPTION = "name=[\"']description[\"']";
    public static final String REGULAR_NAME_TITLE = "name=title";
    public static final String REGULAR_PROPERTY_OG_DESCRIPTION =
            "property=[\"']og:description[\"']";
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
    public static final Uri SELECT_APP_PROVIDER_AUTHORITY_URI;
    public static final String SELECT_APP_SCREEN_CLASS =
            "com.samsung.android.settings.share.SelectAppActivity";
    public static final String SELECT_APP_SCREEN_PKG = "com.android.settings";
    public static final String SELECT_LABEL_CR_CALL_METHOD =
            "com.samsung.android.settings.share.label.GetData";
    public static final String SELECT_LAST_LOCALE = "last_locale";
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
    public static final Uri SHARE_STAR_AUTHORITY_URI;
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
    public static final String SMART_VIEW_COMP =
            "com.samsung.android.smartmirroring/com.samsung.android.smartmirroring.CastingDialog";
    public static final int SUPPORT_BIXBY;
    public static final int SUPPORT_BUTTONS;
    public static final int SUPPORT_DEVICE_SHARE;
    public static final int SUPPORT_FEATURE_BASE = 1;
    public static final int SUPPORT_LOGGING;
    public static final int SUPPORT_RESOLVER_GUIDE;
    public static final int SUPPORT_SHARE_LINK;
    private static int SUPPORT_SHIFT = 0;
    public static final int SUPPORT_SHOW_BUTTON_SHAPES;
    public static final String SURVERY_PERMISSION =
            "com.samsung.android.providers.context.permission.WRITE_USE_APP_FEATURE_SURVEY";
    public static final String SURVEY_CONTENT_DIMENSION = "dimension";
    public static final String SURVEY_CONTENT_EXTRA = "extra";
    public static final String SURVEY_CONTENT_FEATURE = "feature";
    public static final String SURVEY_CONTENT_TYPE = "type";
    public static final String SURVEY_CONTENT_TYPE_VALUE = "ev";
    public static final String SURVEY_EXTRA_OWN_PACKAGE = "pkg_name";
    public static final String SURVEY_EXTRA_OWN_PACKAGE_VALUE = "sharevia";
    public static final int TRANSITION_ENTER_TYPE = 0;
    public static final int TRANSITION_EXIT_TYPE = 1;

    static {
        SUPPORT_SHIFT = 1;
        int i = SUPPORT_SHIFT;
        SUPPORT_SHIFT = i + 1;
        SUPPORT_BUTTONS = 1 << i;
        int i2 = SUPPORT_SHIFT;
        SUPPORT_SHIFT = i2 + 1;
        SUPPORT_SHOW_BUTTON_SHAPES = 1 << i2;
        int i3 = SUPPORT_SHIFT;
        SUPPORT_SHIFT = i3 + 1;
        SUPPORT_RESOLVER_GUIDE = 1 << i3;
        int i4 = SUPPORT_SHIFT;
        SUPPORT_SHIFT = i4 + 1;
        SUPPORT_LOGGING = 1 << i4;
        int i5 = SUPPORT_SHIFT;
        SUPPORT_SHIFT = i5 + 1;
        SUPPORT_SHARE_LINK = 1 << i5;
        int i6 = SUPPORT_SHIFT;
        SUPPORT_SHIFT = i6 + 1;
        SUPPORT_BIXBY = 1 << i6;
        int i7 = SUPPORT_SHIFT;
        SUPPORT_SHIFT = i7 + 1;
        SUPPORT_DEVICE_SHARE = 1 << i7;
        ENABLE_QUICKCONNECT_D2D =
                SemFloatingFeature.getInstance()
                        .getBoolean("SEC_FLOATING_FEATURE_QUICKCONNECT_SUPPORT_D2D", false);
        QUICK_SHARE_SLICE_URI =
                Uri.parse("content://com.samsung.android.app.sharelive.sliceprovider");
        QUICK_SHARE_TRANSCODE_SLICE_URI =
                Uri.parse("content://com.samsung.android.app.sharelive.sliceprovider/transcoding");
        NEARBY_SHARE_SLICE_URI = Uri.parse("content://com.google.android.gms.nearby.sharing/scan");
        SHARE_STAR_AUTHORITY_URI =
                Uri.parse("content://com.samsung.android.app.sharestar.ShareStarProvider");
        SELECT_APP_PROVIDER_AUTHORITY_URI =
                Uri.parse("content://com.samsung.android.settings.share.ShareOptionProvider");
    }
}
