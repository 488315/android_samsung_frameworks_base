package com.samsung.systemui.splugins.pluginlock;

import com.samsung.systemui.splugins.SPlugin;
import com.samsung.systemui.splugins.annotations.ProvidesInterface;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@ProvidesInterface(action = PluginLock.ACTION, version = PluginLock.VERSION)
/* loaded from: classes3.dex */
public interface PluginLock extends SPlugin {
    public static final String ACTION = "com.samsung.systemui.action.PLUGIN_LOCK";
    public static final String ACTION_BACK_KEY = "action_back_key";
    public static final String ACTION_COVER_CLOSED = "cover_closed";
    public static final String ACTION_DATA_CLEAR = "action_data_clear";
    public static final String ACTION_LID_SWITCH = "lid_switch";
    public static final String ACTION_LOCK_STYLE_CHANGED = "action_lock_style_changed";
    public static final String ACTION_WALLPAPER_STATE_CHANGED = "wallpaper_state_changed";
    public static final int ALLOWED_NUMBER_BASE_BASIC = 10;
    public static final int ALLOWED_NUMBER_BASE_DYNAMIC = 10000;
    public static final int DIFF_VALUE_NOTIFICATION_SETTING = 1;
    public static final int DYNAMIC_LOCK_MODE = 2;
    public static final int ITEM_CLOCK = 0;
    public static final int ITEM_SECURE = 1;
    public static final String KEY_ACTION = "action";
    public static final String KEY_LOCK_STAR_CLOCK = "key_lock_star_clock";
    public static final String KEY_MUSIC_AVAILABLE = "key_music_available";
    public static final String KEY_MUSIC_AVAILABLE_LAND = "key_music_available_land";
    public static final String KEY_MUSIC_END_PADDING = "key_music_end_padding";
    public static final String KEY_MUSIC_END_PADDING_LAND = "key_music_end_padding_land";
    public static final String KEY_MUSIC_GRAVITY = "key_music_gravity";
    public static final String KEY_MUSIC_GRAVITY_LAND = "key_music_gravity_land";
    public static final String KEY_MUSIC_START_PADDING = "key_music_start_padding";
    public static final String KEY_MUSIC_START_PADDING_LAND = "key_music_start_padding_land";
    public static final String KEY_MUSIC_TOP_PADDING = "key_music_top_padding";
    public static final String KEY_MUSIC_TOP_PADDING_LAND = "key_music_top_padding_land";
    public static final String KEY_MUSIC_VISIBILITY = "key_music_visibility";
    public static final String KEY_MUSIC_VISIBILITY_LAND = "key_music_visibility_land";
    public static final String KEY_PAGE_AVAILABLE = "key_page_available";
    public static final String KEY_PAGE_AVAILABLE_LAND = "key_page_available_land";
    public static final String KEY_PAGE_BOTTOM_PADDING = "key_page_bottom_padding";
    public static final String KEY_PAGE_BOTTOM_PADDING_LAND = "key_page_bottom_padding_land";
    public static final String KEY_PAGE_END_PADDING = "key_page_end_padding";
    public static final String KEY_PAGE_END_PADDING_LAND = "key_page_end_padding_land";
    public static final String KEY_PAGE_GRAVITY = "key_page_gravity";
    public static final String KEY_PAGE_GRAVITY_LAND = "key_page_gravity_land";
    public static final String KEY_PAGE_SCALE = "key_page_scale";
    public static final String KEY_PAGE_SCALE_LAND = "key_page_scale_land";
    public static final String KEY_PAGE_START_PADDING = "key_page_start_padding";
    public static final String KEY_PAGE_START_PADDING_LAND = "key_page_start_padding_land";
    public static final String KEY_PAGE_TOP_PADDING = "key_page_top_padding";
    public static final String KEY_PAGE_TOP_PADDING_LAND = "key_page_top_padding_land";
    public static final String KEY_SCREEN = "screen";
    public static final String KEY_STATE = "state";
    public static final String KEY_VALUE = "value";
    public static final String KEY_VISIBILITY = "key_visibility";
    public static final String KEY_VISIBILITY_LAND = "key_visibility_land";
    public static final int LOCK_STAR_MODE = 1;
    public static final int MAJOR_VERSION = 35;
    public static final int MINOR_VERSION = 0;
    public static final int MODE_DEFAULT = 0;
    public static final int MODE_OVERLAY_VIEW = 1;
    public static final int NON_SWIPE_MODE_ANGLE = 45;
    public static final int NON_SWIPE_MODE_DOWN = 8;
    public static final int NON_SWIPE_MODE_LEFT = 1;
    public static final int NON_SWIPE_MODE_LONG_TAP = 32;
    public static final int NON_SWIPE_MODE_NONE = 0;
    public static final int NON_SWIPE_MODE_RIGHT = 2;
    public static final int NON_SWIPE_MODE_TAP = 16;
    public static final int NON_SWIPE_MODE_UP = 4;
    public static final int ORIGIN_DLS = 0;
    public static final int ORIGIN_LOCK_STAR = 1;
    public static final int PAUSE = 0;
    public static final int RESUME = 1;
    public static final int SCREEN_MAIN = 0;
    public static final int SCREEN_SUB = 1;
    public static final int TYPE_BLOCK_CAPTURE_LONG_TOUCH = 2;
    public static final int TYPE_BLOCK_CAPTURE_NONE = 0;
    public static final int TYPE_BLOCK_CAPTURE_SYSTEM_UI = 1;
    public static final int TYPE_NOTIFICATION_CARD = 1;
    public static final int TYPE_NOTIFICATION_DEFAULT = 0;
    public static final int TYPE_NOTIFICATION_ICONS_ONLY = 2;
    public static final int TYPE_RECOVER_BASIC = 1;
    public static final int TYPE_RECOVER_STICKY = 2;
    public static final int TYPE_UPDATE_STYLE_FADE = 0;
    public static final int TYPE_UPDATE_STYLE_NO_EFFECT = 1;
    public static final int TYPE_WALLPAPER_CURRENT = -2;
    public static final int TYPE_WALLPAPER_CURRENT_ALL = -3;
    public static final int TYPE_WALLPAPER_HOME = 10;
    public static final int TYPE_WALLPAPER_HOME_GIF = 12;
    public static final int TYPE_WALLPAPER_HOME_IMAGE = 11;
    public static final int TYPE_WALLPAPER_HOME_SUB = 20;
    public static final int TYPE_WALLPAPER_HOME_SUB_GIF = 22;
    public static final int TYPE_WALLPAPER_HOME_SUB_IMAGE = 21;
    public static final int TYPE_WALLPAPER_HOME_SUB_VIDEO = 23;
    public static final int TYPE_WALLPAPER_HOME_VIDEO = 13;
    public static final int TYPE_WALLPAPER_IMAGE = 1;
    public static final int TYPE_WALLPAPER_SOURCE_PATH = 0;
    public static final int TYPE_WALLPAPER_SOURCE_RES_ID = 1;
    public static final int TYPE_WALLPAPER_SOURCE_URI = 2;
    public static final int TYPE_WALLPAPER_VIDEO = 2;
    public static final int VERSION = 3500;
    public static final int VERSION_SUPPORT_MULTIPLE = 1100;

    PluginLockBasicManager getBasicManager();

    PluginLockTouchManager getTouchManager();
}
