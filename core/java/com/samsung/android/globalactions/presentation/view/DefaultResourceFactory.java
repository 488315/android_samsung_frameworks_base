package com.samsung.android.globalactions.presentation.view;

import com.android.internal.C4337R;

/* loaded from: classes5.dex */
public class DefaultResourceFactory implements ResourceFactory {
    @Override // com.samsung.android.globalactions.presentation.view.ResourceFactory
    public int get(ResourceType type) {
        if (type == ResourceType.ID_ITEM_LIST) {
            return C4337R.id.sec_global_actions_item_list;
        }
        if (type == ResourceType.ID_ITEM_LIST_LAND) {
            return C4337R.id.sec_global_actions_item_list_land;
        }
        if (type == ResourceType.ID_STATE) {
            return C4337R.id.sec_global_actions_state;
        }
        if (type == ResourceType.ID_DESCRIPTION) {
            return C4337R.id.sec_global_actions_description;
        }
        if (type == ResourceType.ID_DESCRIPTION_TEXT) {
            return C4337R.id.sec_global_actions_description_text;
        }
        if (type == ResourceType.ID_LABEL) {
            return C4337R.id.sec_global_actions_label;
        }
        if (type == ResourceType.ID_ICON) {
            return C4337R.id.sec_global_actions_icon;
        }
        if (type == ResourceType.ID_ICON_LABEL) {
            return C4337R.id.sec_global_actions_icon_label_view;
        }
        if (type == ResourceType.ID_BOTTOM_BUTTON_VIEW) {
            return C4337R.id.sec_global_actions_bottom;
        }
        if (type == ResourceType.ID_CONFIRMATION_VIEW) {
            return C4337R.id.sec_global_actions_confirmation;
        }
        if (type == ResourceType.LAYOUT_ROOT_VIEW) {
            return C4337R.layout.sec_global_actions_wrapped;
        }
        if (type == ResourceType.LAYOUT_BOTTOM_VIEW) {
            return C4337R.layout.sec_global_actions_bottomview;
        }
        if (type == ResourceType.LAYOUT_BUGREPORT_VIEW) {
            return C4337R.layout.sec_global_actions_bugreportview;
        }
        if (type == ResourceType.LAYOUT_ITEM_LIST_VIEW) {
            return C4337R.layout.sec_global_actions_item;
        }
        if (type == ResourceType.LAYOUT_BLUR_BACKGROUND) {
            return C4337R.layout.sec_global_actions_blur_background;
        }
        if (type == ResourceType.DRAWABLE_POWEROFF) {
            return C4337R.drawable.tw_ic_do_poweroff;
        }
        if (type == ResourceType.DRAWABLE_RESTART) {
            return C4337R.drawable.tw_ic_do_restart;
        }
        if (type == ResourceType.DRAWABLE_SAFEMODE) {
            return C4337R.drawable.tw_ic_do_safemode;
        }
        if (type == ResourceType.DRAWABLE_EMERGENCY) {
            return C4337R.drawable.tw_ic_do_emergencymode;
        }
        if (type == ResourceType.DRAWABLE_EMERGENCY_CALL) {
            return C4337R.drawable.tw_ic_do_emergencysos;
        }
        if (type == ResourceType.DRAWABLE_MEDICAL_INFO) {
            return C4337R.drawable.tw_ic_do_medicalinfo;
        }
        if (type == ResourceType.DRAWABLE_LOCKDOWN) {
            return C4337R.drawable.tw_ic_do_lockdown;
        }
        if (type == ResourceType.DRAWABLE_ICON_BG_FOCUSED) {
            return C4337R.drawable.sec_global_actions_icon_bg_focused;
        }
        if (type == ResourceType.DRAWABLE_ICON_RIPPLE) {
            return C4337R.drawable.sec_global_actions_icon_ripple;
        }
        if (type == ResourceType.INTEGER_FORCE_RESTART_TIME) {
            return C4337R.integer.sec_global_actions_force_restart_time;
        }
        if (type == ResourceType.DIMEN_BUGREPORT_BOTTOM_MARGIN) {
            return C4337R.dimen.sec_global_actions_bottom_textview_bottom_margin_portrait;
        }
        if (type == ResourceType.DIMEN_BUGREPORT_BOTTOM_MARGIN_LAND) {
            return C4337R.dimen.sec_global_actions_bottom_textview_bottom_margin_landscape;
        }
        if (type == ResourceType.DIMEN_NAVIGATIONBAR_HEIGHT) {
            return C4337R.dimen.navigation_bar_height;
        }
        return 0;
    }
}
