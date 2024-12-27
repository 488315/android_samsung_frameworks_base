package com.android.systemui.qs.panelresource;

import android.content.Context;
import android.hardware.display.DisplayManager;
import com.android.keyguard.SecurityUtils$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.qs.panelresource.SecQSPanelResourceCommon;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.SettingsHelper;
import kotlin.Pair;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class SecQSPanelResourceFoldPicker extends SecQSPanelResourcePhonePicker {
    public final SecQSPanelResourceCommon common;

    public SecQSPanelResourceFoldPicker(SecQSPanelResourceCommon secQSPanelResourceCommon) {
        super(secQSPanelResourceCommon);
        this.common = secQSPanelResourceCommon;
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourcePhonePicker
    public final int getBrightnessTileLayoutBetweenMargin(Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        return (int) (SecQSPanelResourceCommon.Companion.m2073float(R.dimen.sec_brightness_tile_between_margin_fold, context) * DeviceState.getDisplayWidth(context));
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourcePhonePicker
    public final int getButtonsWidth(Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        return Math.max((int) (DeviceState.getDisplayWidth(context) * (DeviceState.isSubDisplay(context) ? SecQSPanelResourceCommon.Companion.m2073float(R.dimen.qs_button_container_width_ratio_fold_sub, context) : SecQSPanelResourceCommon.Companion.m2073float(R.dimen.qs_button_container_width_ratio_fold, context))), SecQSPanelResourceCommon.Companion.dp(R.dimen.sec_qs_button_container_size, context));
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourcePhonePicker
    public final int getDateButtonContainerPadding(Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        if (SecQSPanelResourceCommon.Companion.isLandscape(context)) {
            return 0;
        }
        return getNotificationSidePadding(context, false);
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourcePhonePicker
    public final int getDetailContentViewMaxHeight(Context context) {
        if (DeviceState.isSubDisplay(context)) {
            SecQSPanelResourceCommon.Companion.getClass();
            return SecQSPanelResourceCommon.Companion.dp(R.dimen.qs_detail_content_height_fold_sub, context);
        }
        SecQSPanelResourceCommon.Companion.getClass();
        return SecQSPanelResourceCommon.Companion.dp(R.dimen.qs_detail_content_height_fold, context);
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourcePhonePicker
    public final int getDetailSidePadding(Context context) {
        if (DeviceState.isSubDisplay(context)) {
            return super.getDetailSidePadding(context);
        }
        int panelWidth = getPanelWidth(context);
        SecQSPanelResourceCommon.Companion.getClass();
        return (panelWidth - context.getResources().getDimensionPixelSize(R.dimen.qs_detail_side_padding_fold)) / 2;
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourcePhonePicker
    public final int getLabelHeight(Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        int displayHeight = DeviceState.getDisplayHeight(context);
        if (!DeviceState.isSubDisplay(context)) {
            return (int) Math.max(SecQSPanelResourceCommon.Companion.m2073float(R.dimen.qs_label_height_ratio_fold, context) * displayHeight, context.getResources().getDimensionPixelSize(R.dimen.sec_qs_label_height_fold));
        }
        Object systemService = context.getSystemService("display");
        DisplayManager displayManager = systemService instanceof DisplayManager ? (DisplayManager) systemService : null;
        return (displayManager != null ? displayManager.semIsFitToActiveDisplay() : false ? Double.valueOf(Math.max(SecurityUtils$$ExternalSyntheticOutline0.m(context, R.dimen.qs_label_height_ratio, displayHeight), context.getResources().getDimensionPixelSize(R.dimen.sec_qs_label_height))) : Float.valueOf(context.getResources().getFloat(R.dimen.qs_label_height_ratio_fold_sub) * displayHeight)).intValue();
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourcePhonePicker
    public final int getMediaDeviceBarTouchAreaBetweenPadding(Context context) {
        if (DeviceState.isSubDisplay(context)) {
            SecQSPanelResourceCommon.Companion.getClass();
            return SecQSPanelResourceCommon.Companion.dp(R.dimen.qspanel_media_device_touch_line_between_padding_fold_sub, context);
        }
        SecQSPanelResourceCommon.Companion.getClass();
        return SecQSPanelResourceCommon.Companion.dp(R.dimen.qspanel_media_device_touch_line_between_padding_fold, context);
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourcePhonePicker
    public final int getNotificationSidePadding(Context context, boolean z) {
        float f;
        SecQSPanelResourceCommon.Companion.getClass();
        int displayWidth = DeviceState.getDisplayWidth(context);
        if (!SecQSPanelResourceCommon.Companion.isLandscape(context)) {
            return (int) (DeviceState.isSubDisplay(context) ? SecurityUtils$$ExternalSyntheticOutline0.m(context, R.dimen.notification_side_padding_portrait_ratio, displayWidth) : SecurityUtils$$ExternalSyntheticOutline0.m(context, R.dimen.notification_side_padding_portrait_ratio_fold, displayWidth));
        }
        float m = DeviceState.isSubDisplay(context) ? SecurityUtils$$ExternalSyntheticOutline0.m(context, R.dimen.qs_panel_width_landscape_ratio, displayWidth) : SecurityUtils$$ExternalSyntheticOutline0.m(context, R.dimen.qs_panel_width_landscape_ratio_fold, displayWidth);
        if (DeviceState.isSubDisplay(context)) {
            f = SecurityUtils$$ExternalSyntheticOutline0.m(context, R.dimen.qqs_panel_width_landscape_ratio, displayWidth);
        } else {
            int displayWidth2 = DeviceState.getDisplayWidth(context);
            f = DeviceState.isSmartViewFitToActiveDisplay() ^ true ? displayWidth2 * 0.5597f : displayWidth2;
        }
        return (int) ((m - f) / 2.0f);
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourcePhonePicker
    public final int getPanelStartEndPadding(int i, Context context) {
        int notificationSidePadding;
        if (DeviceState.isSubDisplay(context)) {
            SecQSPanelResourceCommon.Companion.getClass();
            notificationSidePadding = (int) (SecQSPanelResourceCommon.Companion.m2073float(R.dimen.qqs_panel_side_padding_fold_sub, context) * DeviceState.getDisplayWidth(context));
        } else {
            notificationSidePadding = getNotificationSidePadding(context, false);
        }
        SecQSPanelResourceCommon.Companion.getClass();
        if (i == 1) {
            return notificationSidePadding;
        }
        return 0;
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourcePhonePicker
    public final int getPanelWidth(Context context) {
        float f;
        float f2;
        float f3;
        SecQSPanelResourceCommon.Companion.getClass();
        Pair pair = new Pair(Boolean.valueOf(SecQSPanelResourceCommon.Companion.isPortrait(context)), Integer.valueOf(DeviceState.getDisplayWidth(context)));
        boolean booleanValue = ((Boolean) pair.component1()).booleanValue();
        int intValue = ((Number) pair.component2()).intValue();
        if (DeviceState.isSubDisplay(context)) {
            if (booleanValue) {
                return intValue;
            }
            f = SecurityUtils$$ExternalSyntheticOutline0.m(context, R.dimen.qs_panel_width_landscape_ratio_fold_sub, intValue);
        } else {
            if (!DeviceState.isSmartViewFitToActiveDisplay()) {
                int quickSettingExtraSidePadding = getQuickSettingExtraSidePadding(context) * 2;
                if (booleanValue) {
                    f2 = intValue;
                    f3 = 0.7f;
                } else {
                    f2 = intValue;
                    f3 = 0.8097f;
                }
                return quickSettingExtraSidePadding + ((int) (f2 * f3));
            }
            if (booleanValue) {
                return intValue;
            }
            f = intValue * 0.735f;
        }
        return (int) f;
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourcePhonePicker
    public final int getQQSPanelSidePadding(Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        if (!SecQSPanelResourceCommon.Companion.isLandscape(context)) {
            return 0;
        }
        int displayWidth = DeviceState.getDisplayWidth(context);
        return (int) (((DeviceState.isSubDisplay(context) ? SecurityUtils$$ExternalSyntheticOutline0.m(context, R.dimen.qs_panel_width_landscape_ratio, displayWidth) : SecurityUtils$$ExternalSyntheticOutline0.m(context, R.dimen.qs_panel_width_landscape_ratio_fold, displayWidth)) - (DeviceState.isSubDisplay(context) ? SecurityUtils$$ExternalSyntheticOutline0.m(context, R.dimen.qqs_panel_width_landscape_ratio, displayWidth) : DeviceState.getDisplayWidth(context) * 0.5597f)) / 2.0f);
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourcePhonePicker
    public final int getQSGuideContainerMargin(Context context) {
        float m;
        if (DeviceState.isSubDisplay(context)) {
            SecQSPanelResourceCommon.Companion.getClass();
            m = SecurityUtils$$ExternalSyntheticOutline0.m(context, R.dimen.qs_guide_dialog_container_margin, DeviceState.getDisplayWidth(context));
        } else {
            SecQSPanelResourceCommon.Companion.getClass();
            m = SecurityUtils$$ExternalSyntheticOutline0.m(context, R.dimen.qs_guide_dialog_fold_container_margin, DeviceState.getDisplayWidth(context));
        }
        return (int) m;
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourcePhonePicker
    public final int getQSGuideImageHeight(Context context) {
        float m;
        if (DeviceState.isSubDisplay(context)) {
            SecQSPanelResourceCommon.Companion.getClass();
            m = SecurityUtils$$ExternalSyntheticOutline0.m(context, R.dimen.qs_guide_dialog_image_height_ratio, DeviceState.getDisplayHeight(context));
        } else {
            SecQSPanelResourceCommon.Companion.getClass();
            m = SecurityUtils$$ExternalSyntheticOutline0.m(context, R.dimen.qs_guide_dialog_fold_image_height_ratio, DeviceState.getDisplayHeight(context));
        }
        return (int) m;
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourcePhonePicker
    public final int getQSGuideImageWidth(Context context) {
        float m;
        if (DeviceState.isSubDisplay(context)) {
            SecQSPanelResourceCommon.Companion.getClass();
            m = SecurityUtils$$ExternalSyntheticOutline0.m(context, R.dimen.qs_guide_dialog_image_width_ratio, DeviceState.getDisplayWidth(context));
        } else {
            SecQSPanelResourceCommon.Companion.getClass();
            m = SecurityUtils$$ExternalSyntheticOutline0.m(context, R.dimen.qs_guide_dialog_fold_image_width_ratio, DeviceState.getDisplayWidth(context));
        }
        return (int) m;
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourcePhonePicker
    public final int getQSGuideWidth(Context context) {
        float m;
        if (DeviceState.isSubDisplay(context)) {
            SecQSPanelResourceCommon.Companion.getClass();
            m = SecurityUtils$$ExternalSyntheticOutline0.m(context, R.dimen.qs_guide_dialog_width_ratio, DeviceState.getDisplayWidth(context));
        } else {
            SecQSPanelResourceCommon.Companion.getClass();
            m = SecurityUtils$$ExternalSyntheticOutline0.m(context, R.dimen.qs_guide_dialog_fold_width_ratio, DeviceState.getDisplayWidth(context));
        }
        return (int) m;
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourcePhonePicker
    public final int getQsTileColumn(Context context) {
        if (this.common.isEmergencyMode) {
            SecQSPanelResourceCommon.Companion.getClass();
            return Math.max(1, SecQSPanelResourceCommon.Companion.m2074int(R.integer.sec_quick_settings_num_columns_power_saving, context));
        }
        if (DeviceState.isSubDisplay(context)) {
            SecQSPanelResourceCommon.Companion.getClass();
            return SecQSPanelResourceCommon.Companion.m2074int(R.integer.sec_quick_settings_num_columns_fold_sub, context);
        }
        SecQSPanelResourceCommon.Companion.getClass();
        return SecQSPanelResourceCommon.Companion.m2074int(R.integer.sec_quick_settings_num_columns_fold, context);
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourcePhonePicker
    public final int getQuickQsTileNum(Context context) {
        if (DeviceState.isSubDisplay(context)) {
            SecQSPanelResourceCommon.Companion.getClass();
            return context.getResources().getInteger(R.integer.sec_quick_qs_panel_max_columns_fold_sub);
        }
        SecQSPanelResourceCommon.Companion.getClass();
        return context.getResources().getInteger(R.integer.sec_quick_qs_panel_max_columns_fold);
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourcePhonePicker
    public final int getQuickSettingExtraSidePadding(Context context) {
        if (DeviceState.isSubDisplay(context)) {
            return 0;
        }
        SecQSPanelResourceCommon.Companion.getClass();
        return (int) (((Math.sqrt(2.0d) * 0.43f) - 0.5f) * SecQSPanelResourceCommon.Companion.dp(R.dimen.sec_style_qs_tile_icon_size, context));
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourcePhonePicker
    public final int getShadeHeaderHeight(Context context) {
        if (DeviceState.isSubDisplay(context)) {
            SecQSPanelResourceCommon.Companion.getClass();
            return SecQSPanelResourceCommon.Companion.dp(R.dimen.large_screen_shade_header_height, context);
        }
        SecQSPanelResourceCommon.Companion.getClass();
        return SecQSPanelResourceCommon.Companion.dp(R.dimen.large_screen_shade_header_height_fold, context);
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourcePhonePicker
    public final int getTileExpandedSidePadding(Context context) {
        if (DeviceState.isSubDisplay(context)) {
            SecQSPanelResourceCommon.Companion.getClass();
            return SecQSPanelResourceCommon.Companion.dp(R.dimen.tile_chunk_layout_expanded_side_padding_fold_sub, context);
        }
        SecQSPanelResourceCommon.Companion.getClass();
        return SecQSPanelResourceCommon.Companion.dp(R.dimen.tile_chunk_layout_expanded_side_padding, context);
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourcePhonePicker
    public final int getTileExpandedWidth(Context context) {
        int dp;
        if (DeviceState.isSubDisplay(context)) {
            SecQSPanelResourceCommon.Companion.getClass();
            dp = SecQSPanelResourceCommon.Companion.dp(R.dimen.tile_expanded_width_fold_sub, context);
        } else {
            SecQSPanelResourceCommon.Companion.getClass();
            dp = SecQSPanelResourceCommon.Companion.dp(R.dimen.tile_expanded_width_fold, context);
        }
        SecQSPanelResourceCommon secQSPanelResourceCommon = this.common;
        return ((SettingsHelper) secQSPanelResourceCommon.settingsHelper$delegate.getValue()).isQSButtonGridPopupEnabled() ? (int) (dp * secQSPanelResourceCommon.tileExpandedWidthRatio) : dp;
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourcePhonePicker
    public final int getTileIconStartMargin(Context context) {
        if (DeviceState.isSubDisplay(context)) {
            SecQSPanelResourceCommon.Companion.getClass();
            return SecQSPanelResourceCommon.Companion.dp(R.dimen.large_tile_temp_icon_margin_start, context);
        }
        SecQSPanelResourceCommon.Companion.getClass();
        return SecQSPanelResourceCommon.Companion.dp(R.dimen.large_tile_temp_icon_margin_start_fold, context);
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourcePhonePicker
    public final int getTileLabelStartMargin(Context context) {
        if (DeviceState.isSubDisplay(context)) {
            SecQSPanelResourceCommon.Companion.getClass();
            return SecQSPanelResourceCommon.Companion.dp(R.dimen.large_tile_temp_label_margin_start, context);
        }
        SecQSPanelResourceCommon.Companion.getClass();
        return SecQSPanelResourceCommon.Companion.dp(R.dimen.large_tile_temp_label_margin_start_fold, context);
    }
}
