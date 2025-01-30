package com.android.systemui.globalactions.presentation.view;

import com.android.systemui.R;
import com.samsung.android.globalactions.presentation.view.ResourceFactory;
import com.samsung.android.globalactions.presentation.view.ResourceType;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SystemUIResourceFactory implements ResourceFactory {
    public final int get(ResourceType resourceType) {
        if (resourceType == ResourceType.ID_ITEM_LIST) {
            return R.id.sec_global_actions_item_list;
        }
        if (resourceType == ResourceType.ID_ITEM_LIST_LAND) {
            return R.id.sec_global_actions_item_list_land;
        }
        if (resourceType == ResourceType.ID_STATE) {
            return R.id.sec_global_actions_state;
        }
        if (resourceType == ResourceType.ID_DESCRIPTION) {
            return R.id.sec_global_actions_description;
        }
        if (resourceType == ResourceType.ID_DESCRIPTION_TEXT) {
            return R.id.sec_global_actions_description_text;
        }
        if (resourceType == ResourceType.ID_LABEL) {
            return R.id.sec_global_actions_label;
        }
        if (resourceType == ResourceType.ID_ICON) {
            return R.id.sec_global_actions_icon;
        }
        if (resourceType == ResourceType.ID_ICON_LABEL) {
            return R.id.sec_global_actions_icon_label_view;
        }
        if (resourceType == ResourceType.ID_TOP_VIEW) {
            return R.id.sec_global_actions_top_container;
        }
        if (resourceType == ResourceType.ID_BOTTOM_BUTTON_VIEW) {
            return R.id.sec_global_actions_bottom_button_list;
        }
        if (resourceType == ResourceType.ID_BOTTOM_BUTTON_CONTAINER) {
            return R.id.sec_global_actions_bottom_container;
        }
        if (resourceType == ResourceType.ID_FORCE_RESTART_TEXT_VIEW) {
            return R.id.sec_global_actions_bottom_force_restart_msg_view;
        }
        if (resourceType == ResourceType.ID_CONFIRMATION_VIEW) {
            return R.id.sec_global_actions_confirmation;
        }
        if (resourceType == ResourceType.ID_BUGREPORT_VIEW) {
            return R.id.sec_global_actions_bugreportview;
        }
        if (resourceType == ResourceType.ID_SIDE_COVER_BACKGROUND) {
            return R.id.side_cover_background;
        }
        if (resourceType == ResourceType.ID_SIDE_COVER_ITEM_LIST) {
            return R.id.side_cover_item_list;
        }
        if (resourceType == ResourceType.ID_SIDE_COVER_CONFIRM) {
            return R.id.side_cover_confirm;
        }
        if (resourceType == ResourceType.ID_COVER_ITEM_WRAPPED) {
            return R.id.sec_global_actions_cover_item_wrapped;
        }
        if (resourceType == ResourceType.ID_COVER_BTN_BACKGROUND) {
            return R.id.sec_global_actions_cover_background;
        }
        if (resourceType == ResourceType.ID_COVER_ANIM) {
            return R.id.sec_global_actions_cover_anim;
        }
        if (resourceType == ResourceType.ID_COVER_TEXT) {
            return R.id.sec_global_actions_cover_text;
        }
        if (resourceType == ResourceType.ID_COVER_TEXT_BACKGROUND) {
            return R.id.sec_global_actions_cover_text_background;
        }
        if (resourceType == ResourceType.ID_COVER_ICON) {
            return R.id.sec_global_actions_cover_icon;
        }
        if (resourceType == ResourceType.ID_SIDEKEY_SETTINGS_VIEW) {
            return R.id.sec_global_actions_key_settings;
        }
        if (resourceType == ResourceType.ID_SCREEN_CAPTURE_POPUP) {
            return R.id.sec_global_actions_screen_capture_popup;
        }
        if (resourceType == ResourceType.ID_MINI_BACKGROUND) {
            return R.id.sec_global_actions_mini_sview_background;
        }
        if (resourceType == ResourceType.ID_MINI_SVIEW_COVER_ITEM) {
            return R.id.sec_global_actions_mini_sview_cover_item;
        }
        if (resourceType == ResourceType.ID_MINI_SVIEW_COVER_ITEM_ICON) {
            return R.id.sec_global_actions_mini_sview_cover_item_icon;
        }
        if (resourceType == ResourceType.ID_MINI_SVIEW_COVER_ITEM_TEXT) {
            return R.id.sec_global_actions_mini_sview_cover_item_text;
        }
        if (resourceType == ResourceType.ID_MINI_SVIEW_COVER_CONFIRM) {
            return R.id.sec_global_actions_mini_sview_cover_confirm;
        }
        if (resourceType == ResourceType.ID_FRONT_COVER_ITEM) {
            return R.id.sec_global_actions_front_cover_item;
        }
        if (resourceType == ResourceType.ID_FRONT_COVER_COFIRM) {
            return R.id.sec_global_actions_front_cover_confirm;
        }
        if (resourceType == ResourceType.LAYOUT_ROOT_VIEW) {
            return R.layout.sec_global_actions_wrapped;
        }
        if (resourceType == ResourceType.LAYOUT_TOP_VIEW) {
            return R.layout.sec_global_actions_topview;
        }
        if (resourceType == ResourceType.LAYOUT_BOTTOM_VIEW) {
            return R.layout.sec_global_actions_bottomview;
        }
        if (resourceType == ResourceType.LAYOUT_BUGREPORT_VIEW) {
            return R.layout.sec_global_actions_bugreportview;
        }
        if (resourceType == ResourceType.LAYOUT_ITEM_LIST_VIEW) {
            return R.layout.sec_global_actions_item;
        }
        if (resourceType == ResourceType.LAYOUT_SIDE_COVER_ROOT_VIEW) {
            return R.layout.sec_global_actions_side_cover_wrapped;
        }
        if (resourceType == ResourceType.LAYOUT_SIDE_COVER_ITEM_LIST_VIEW) {
            return R.layout.sec_global_actions_side_cover_item;
        }
        if (resourceType == ResourceType.LAYOUT_SIDE_COVER_NOTIFICATION) {
            return R.layout.sec_global_actions_side_cover_notification;
        }
        if (resourceType == ResourceType.LAYOUT_SIDEKEY_SETTINGS_VIEW) {
            return R.layout.sec_global_actions_keysettingsview;
        }
        if (resourceType == ResourceType.LAYOUT_FORCE_RESTART_TEXT_VIEW) {
            return R.layout.sec_global_actions_bottomtextview;
        }
        if (resourceType == ResourceType.LAYOUT_SCREEN_CAPTURE_POPUP) {
            return R.layout.sec_global_actions_screen_capture_popup;
        }
        if (resourceType == ResourceType.LAYOUT_MINI_SVIEW_COVER_VIEW) {
            return R.layout.sec_global_actions_mini_sview_cover;
        }
        if (resourceType == ResourceType.LAYOUT_MINI_SVIEW_COVER_ITEM) {
            return R.layout.sec_global_actions_mini_sview_cover_item;
        }
        if (resourceType == ResourceType.LAYOUT_MINI_SVIEW_COVER_NOTIFICATION) {
            return R.layout.sec_global_actions_mini_sview_notification;
        }
        if (resourceType == ResourceType.LAYOUT_FRONT_COVER_VIEW) {
            return R.layout.sec_global_actions_front_cover;
        }
        if (resourceType == ResourceType.LAYOUT_FRONT_COVER_ITEM) {
            return R.layout.sec_global_actions_front_cover_item;
        }
        if (resourceType == ResourceType.LAYOUT_FRONT_LARGE_COVER_VIEW) {
            return R.layout.sec_global_actions_large_front_cover;
        }
        if (resourceType == ResourceType.LAYOUT_FRONT_LARGE_COVER_ITEM) {
            return R.layout.sec_global_actions_large_front_cover_item;
        }
        if (resourceType == ResourceType.LAYOUT_FRONT_COVER_NOTIFICATION) {
            return R.layout.sec_global_actions_front_cover_notification;
        }
        if (resourceType == ResourceType.LAYOUT_BLUR_BACKGROUND) {
            return R.layout.sec_global_actions_blur_background;
        }
        if (resourceType == ResourceType.DRAWABLE_POWEROFF) {
            return R.drawable.tw_ic_do_poweroff;
        }
        if (resourceType == ResourceType.DRAWABLE_COVER_POWER_OFF_BG) {
            return R.drawable.bg_deviceoption_poweroff;
        }
        if (resourceType == ResourceType.DRAWABLE_COVER_POWER_OFF_ICON) {
            return R.drawable.ic_deviceoption_poweroff;
        }
        if (resourceType == ResourceType.DRAWABLE_RESTART) {
            return R.drawable.tw_ic_do_restart;
        }
        if (resourceType == ResourceType.DRAWABLE_COVER_RESTART_BG) {
            return R.drawable.bg_deviceoption_restart;
        }
        if (resourceType == ResourceType.DRAWABLE_COVER_RESTART_ICON) {
            return R.drawable.ic_deviceoption_restart;
        }
        if (resourceType == ResourceType.DRAWABLE_SAFEMODE) {
            return R.drawable.tw_ic_do_safemode;
        }
        if (resourceType == ResourceType.DRAWABLE_EMERGENCY) {
            return R.drawable.tw_ic_do_emergencymode;
        }
        if (resourceType == ResourceType.DRAWABLE_EMERGENCY_CALL) {
            return R.drawable.tw_ic_do_emergencysos;
        }
        if (resourceType == ResourceType.DRAWABLE_MEDICAL_INFO) {
            return R.drawable.tw_ic_do_medicalinfo;
        }
        if (resourceType == ResourceType.DRAWABLE_LOCKDOWN) {
            return R.drawable.tw_ic_do_lockdown;
        }
        if (resourceType == ResourceType.DRAWABLE_PROKIOSK) {
            return R.drawable.tw_ic_do_prokioskmode;
        }
        if (resourceType == ResourceType.DRAWABLE_DATAMODE) {
            return R.drawable.tw_ic_do_mobiledata;
        }
        if (resourceType == ResourceType.DRAWABLE_ENDSESSION) {
            return R.drawable.tw_ic_do_end_session;
        }
        if (resourceType == ResourceType.DRAWABLE_ICON_BG_FOCUSED) {
            return R.drawable.sec_global_actions_icon_bg_focused;
        }
        if (resourceType == ResourceType.DRAWABLE_ICON_RIPPLE) {
            return R.drawable.sec_global_actions_icon_ripple;
        }
        if (resourceType == ResourceType.DRAWABLE_BG_RAISED_BTN_DARK) {
            return R.drawable.bg_raised_btn_dark;
        }
        if (resourceType == ResourceType.DRAWABLE_BG_RAISED_BTN_LIGHT) {
            return R.drawable.bg_raised_btn_light;
        }
        if (resourceType == ResourceType.DRAWABLE_SIDEKEY_SETTINGS_RIPPLE_DARK) {
            return R.drawable.sec_global_actions_keysettings_dark_ripple;
        }
        if (resourceType == ResourceType.DRAWABLE_SIDEKEY_SETTINGS_RIPPLE_LIGHT) {
            return R.drawable.sec_global_actions_keysettings_light_ripple;
        }
        if (resourceType == ResourceType.INTEGER_FORCE_RESTART_TIME) {
            return R.integer.sec_global_actions_force_restart_time;
        }
        if (resourceType == ResourceType.DIMEN_BIXBY_SETTINGS_TOP_MARGIN) {
            return R.dimen.sec_global_actions_top_view_bixby_settings_top_margin;
        }
        if (resourceType == ResourceType.DIMEN_BIXBY_SETTINGS_TOP_MARGIN_LAND) {
            return R.dimen.sec_global_actions_top_view_bixby_settings_top_margin_land;
        }
        if (resourceType == ResourceType.DIMEN_BIXBY_SETTINGS_RIGHT_MARGIN) {
            return R.dimen.sec_global_actions_top_view_bixby_settings_right_margin;
        }
        if (resourceType == ResourceType.DIMEN_BIXBY_SETTINGS_RIGHT_MARGIN_LAND) {
            return R.dimen.sec_global_actions_top_view_bixby_settings_right_margin_land;
        }
        if (resourceType == ResourceType.DIMEN_BOTTOMBUTTONVIEW_BOTTOM_MARGIN_LAND) {
            return R.dimen.sec_global_actions_bottom_view_margin_bottom_land;
        }
        if (resourceType == ResourceType.DIMEN_BUGREPORT_BOTTOM_MARGIN) {
            return R.dimen.sec_global_actions_bugreportview_bottom_margin_portrait;
        }
        if (resourceType == ResourceType.DIMEN_BUGREPORT_BOTTOM_MARGIN_TASK) {
            return R.dimen.sec_global_actions_bugreportview_bottom_margin_portrait_task;
        }
        if (resourceType == ResourceType.DIMEN_BUGREPORT_BOTTOM_MARGIN_LAND) {
            return R.dimen.sec_global_actions_bugreportview_bottom_margin_landscape;
        }
        if (resourceType == ResourceType.DIMEN_SIDE_COVER_WIDTH) {
            return R.dimen.sec_global_actions_side_cover_width;
        }
        if (resourceType == ResourceType.DIMEN_SIDEKEY_SETTINGS_BOTTOM_MARGIN_2BTNS) {
            return R.dimen.f702x846a31d4;
        }
        if (resourceType == ResourceType.DIMEN_SIDEKEY_SETTINGS_BOTTOM_MARGIN_1BTN) {
            return R.dimen.sec_global_actions_keysettings_bottom_margin_portrait_one_button;
        }
        if (resourceType == ResourceType.DIMEN_SIDEKEY_SETTINGS_BOTTOM_MARGIN_LAND) {
            return R.dimen.sec_global_actions_keysettings_bottom_margin_landscape;
        }
        if (resourceType == ResourceType.DIMEN_SIDEKEY_SETTINGS_RIGHT_MARGIN_LAND) {
            return R.dimen.sec_global_actions_keysettings_right_margin_landscape;
        }
        if (resourceType == ResourceType.DIMEN_MINI_SVIEW_COVER_HEIGHT) {
            return R.dimen.sec_global_actions_mini_sview_cover_height;
        }
        if (resourceType == ResourceType.DIMEN_MINI_SVIEW_COVER_TOP_MARGIN) {
            return R.dimen.sec_global_actions_mini_sview_cover_top_margin;
        }
        if (resourceType == ResourceType.DIMEN_MINI_SVIEW_COVER_SIDE_MARGIN) {
            return R.dimen.sec_global_actions_mini_sview_cover_side_margin;
        }
        if (resourceType == ResourceType.DIMEN_MINI_OPEN_COVER_TOP_MARGIN) {
            return R.dimen.sec_global_actions_mini_open_cover_top_margin;
        }
        if (resourceType == ResourceType.DIMEN_FRONT_LARGE_COVER_VERTICAL_SPACE) {
            return R.dimen.sec_global_actions_front_large_cover_vertical_spacing;
        }
        if (resourceType == ResourceType.DIMEN_FRONT_LARGE_COVER_HORIZONTAL_SPACE) {
            return R.dimen.sec_global_actions_front_large_cover_horizontal_spacing;
        }
        return 0;
    }
}
