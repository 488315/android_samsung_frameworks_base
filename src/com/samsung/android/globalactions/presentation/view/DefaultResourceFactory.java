package com.samsung.android.globalactions.presentation.view;

import com.android.internal.R;

/* loaded from: classes6.dex */
public class DefaultResourceFactory implements ResourceFactory {
    @Override // com.samsung.android.globalactions.presentation.view.ResourceFactory
    public int get(ResourceType resourceType) {
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
        if (resourceType == ResourceType.ID_BOTTOM_BUTTON_VIEW) {
            return R.id.sec_global_actions_bottom;
        }
        if (resourceType == ResourceType.ID_CONFIRMATION_VIEW) {
            return R.id.sec_global_actions_confirmation;
        }
        if (resourceType == ResourceType.LAYOUT_ROOT_VIEW) {
            return R.layout.sec_global_actions_wrapped;
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
        if (resourceType == ResourceType.LAYOUT_BLUR_BACKGROUND) {
            return R.layout.sec_global_actions_blur_background;
        }
        if (resourceType == ResourceType.DRAWABLE_POWEROFF) {
            return R.drawable.tw_ic_do_poweroff;
        }
        if (resourceType == ResourceType.DRAWABLE_RESTART) {
            return R.drawable.tw_ic_do_restart;
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
        if (resourceType == ResourceType.DRAWABLE_ICON_BG_FOCUSED) {
            return R.drawable.sec_global_actions_icon_bg_focused;
        }
        if (resourceType == ResourceType.DRAWABLE_ICON_RIPPLE) {
            return R.drawable.sec_global_actions_icon_ripple;
        }
        if (resourceType == ResourceType.INTEGER_FORCE_RESTART_TIME) {
            return R.integer.sec_global_actions_force_restart_time;
        }
        if (resourceType == ResourceType.DIMEN_BUGREPORT_BOTTOM_MARGIN) {
            return R.dimen.sec_global_actions_bottom_textview_bottom_margin_portrait;
        }
        if (resourceType == ResourceType.DIMEN_BUGREPORT_BOTTOM_MARGIN_LAND) {
            return R.dimen.sec_global_actions_bottom_textview_bottom_margin_landscape;
        }
        if (resourceType == ResourceType.DIMEN_NAVIGATIONBAR_HEIGHT) {
            return R.dimen.navigation_bar_height;
        }
        return 0;
    }
}
