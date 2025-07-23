package com.android.systemui.qs.panelresource;

import android.content.Context;
import android.hardware.display.DisplayManager;
import com.android.keyguard.SecurityUtils$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.qs.panelresource.SecQSPanelResourceCommon;
import com.android.systemui.util.DeviceState;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class SecQSPanelResourceNarrowPicker extends SecQSPanelResourceNormalPicker {
    public final SecQSPanelResourceCommon common;

    public SecQSPanelResourceNarrowPicker(SecQSPanelResourceCommon secQSPanelResourceCommon) {
        super(secQSPanelResourceCommon);
        this.common = secQSPanelResourceCommon;
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourceNormalPicker
    public final int getBrightnessTileLayoutBetweenMargin(Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        return (int) (SecQSPanelResourceCommon.Companion.m2886float(R.dimen.sec_brightness_tile_between_margin_fold, context) * DeviceState.getDisplayWidth(context));
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourceNormalPicker
    public final int getButtonsWidth(Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        return Math.max((int) (SecQSPanelResourceCommon.Companion.m2886float(R.dimen.qs_button_container_width_ratio_fold_sub, context) * DeviceState.getDisplayWidth(context)), context.getResources().getDimensionPixelSize(R.dimen.sec_qs_button_container_size));
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourceNormalPicker
    public final int getDateButtonContainerPadding(Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        if (SecQSPanelResourceCommon.Companion.isPortrait(context)) {
            return (int) SecurityUtils$$ExternalSyntheticOutline0.m(context, R.dimen.qqs_panel_side_padding_fold_sub, DeviceState.getDisplayWidth(context));
        }
        return 0;
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourceNormalPicker
    public final int getDetailContentViewMaxHeight(Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        return SecQSPanelResourceCommon.Companion.dp(R.dimen.qs_detail_content_height_fold_sub, context);
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourceNormalPicker
    public final int getLabelHeight(Context context) {
        Number valueOf;
        SecQSPanelResourceCommon.Companion companion = SecQSPanelResourceCommon.Companion;
        companion.getClass();
        int displayHeight = DeviceState.getDisplayHeight(context);
        Object systemService = context.getSystemService("display");
        DisplayManager displayManager = systemService instanceof DisplayManager ? (DisplayManager) systemService : null;
        if (displayManager != null ? displayManager.semIsFitToActiveDisplay() : false) {
            companion.getClass();
            float f = context.getResources().getFloat(R.dimen.qs_label_height_ratio);
            companion.getClass();
            valueOf = Double.valueOf(Math.max(f * displayHeight, context.getResources().getDimensionPixelSize(R.dimen.sec_qs_label_height)));
        } else {
            companion.getClass();
            valueOf = Float.valueOf(context.getResources().getFloat(R.dimen.qs_label_height_ratio_fold_sub) * displayHeight);
        }
        return valueOf.intValue();
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourceNormalPicker
    public final int getMediaDeviceBarTouchAreaBetweenPadding(Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        return SecQSPanelResourceCommon.Companion.dp(R.dimen.qspanel_media_device_touch_line_between_padding_fold_sub, context);
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourceNormalPicker
    public final int getNotificationSidePadding(Context context, boolean z) {
        SecQSPanelResourceCommon.Companion.getClass();
        int displayWidth = DeviceState.getDisplayWidth(context);
        if (!SecQSPanelResourceCommon.Companion.isLandscape(context)) {
            return (int) (z ? SecurityUtils$$ExternalSyntheticOutline0.m(context, R.dimen.notification_side_padding_portrait_ratio, displayWidth) : SecurityUtils$$ExternalSyntheticOutline0.m(context, R.dimen.qs_clock_side_padding_fold_sub, displayWidth) - context.getResources().getDimensionPixelSize(R.dimen.sec_style_qs_header_clock_buttons_start_padding));
        }
        float f = displayWidth;
        return (int) ((SecurityUtils$$ExternalSyntheticOutline0.m(context, R.dimen.qs_panel_width_landscape_ratio, f) - (context.getResources().getFloat(R.dimen.qqs_panel_width_landscape_ratio) * f)) / 2.0f);
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourceNormalPicker
    public final int getPanelSidePadding(Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        if (SecQSPanelResourceCommon.Companion.isPortrait(context)) {
            return getPanelStartEndPadding(context.getResources().getConfiguration().orientation, context);
        }
        return 0;
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourceNormalPicker
    public final int getPanelStartEndPadding(int i, Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        int m2886float = (int) (SecQSPanelResourceCommon.Companion.m2886float(R.dimen.qqs_panel_side_padding_fold_sub, context) * DeviceState.getDisplayWidth(context));
        if (i == 1) {
            return m2886float;
        }
        return 0;
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourceNormalPicker
    public final int getPanelWidth(Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        int displayWidth = DeviceState.getDisplayWidth(context);
        return SecQSPanelResourceCommon.Companion.isLandscape(context) ? (int) SecurityUtils$$ExternalSyntheticOutline0.m(context, R.dimen.qs_panel_width_landscape_ratio_fold_sub, displayWidth) : displayWidth;
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourceNormalPicker
    public final int getQQSPanelSidePadding(Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        if (!SecQSPanelResourceCommon.Companion.isLandscape(context)) {
            return 0;
        }
        float displayWidth = DeviceState.getDisplayWidth(context);
        return (int) ((SecurityUtils$$ExternalSyntheticOutline0.m(context, R.dimen.qs_panel_width_landscape_ratio, displayWidth) - (context.getResources().getFloat(R.dimen.qqs_panel_width_landscape_ratio) * displayWidth)) / 2.0f);
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourceNormalPicker
    public final int getQQSPanelStartEndPadding(int i, Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        int m2886float = (int) (SecQSPanelResourceCommon.Companion.m2886float(R.dimen.qqs_panel_side_padding_fold_sub, context) * DeviceState.getDisplayWidth(context));
        if (i == 1) {
            return m2886float;
        }
        return 0;
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourceNormalPicker
    public final int getQSGuideContainerMargin(Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        return (int) (SecQSPanelResourceCommon.Companion.m2886float(R.dimen.qs_guide_dialog_container_margin, context) * DeviceState.getDisplayWidth(context));
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourceNormalPicker
    public final int getQSGuideImageHeight(Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        return (int) (SecQSPanelResourceCommon.Companion.m2886float(R.dimen.qs_guide_dialog_image_height_ratio, context) * DeviceState.getDisplayHeight(context));
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourceNormalPicker
    public final int getQSGuideImageWidth(Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        return (int) (SecQSPanelResourceCommon.Companion.m2886float(R.dimen.qs_guide_dialog_image_width_ratio, context) * DeviceState.getDisplayWidth(context));
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourceNormalPicker
    public final int getQSGuideWidth(Context context, boolean z) {
        SecQSPanelResourceCommon.Companion.getClass();
        return (int) (SecQSPanelResourceCommon.Companion.m2886float(R.dimen.qs_guide_dialog_width_ratio, context) * DeviceState.getDisplayWidth(context));
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourceNormalPicker
    public final int getQsTileColumn(Context context) {
        if (this.common.isEmergencyMode) {
            SecQSPanelResourceCommon.Companion.getClass();
            return Math.max(1, SecQSPanelResourceCommon.Companion.m2887int(R.integer.sec_quick_settings_num_columns_power_saving, context));
        }
        SecQSPanelResourceCommon.Companion.getClass();
        return SecQSPanelResourceCommon.Companion.m2887int(R.integer.sec_quick_settings_num_columns_fold_sub, context);
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourceNormalPicker
    public final int getQuickSettingExtraSidePadding(Context context) {
        return 0;
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourceNormalPicker
    public final int getShadeHeaderHeight(Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        return SecQSPanelResourceCommon.Companion.dp(R.dimen.large_screen_shade_header_height, context);
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourceNormalPicker
    public final int getShadeHeaderSidePadding(Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        if (SecQSPanelResourceCommon.Companion.isPortrait(context)) {
            return (int) SecurityUtils$$ExternalSyntheticOutline0.m(context, R.dimen.qqs_panel_side_padding_fold_sub, DeviceState.getDisplayWidth(context));
        }
        return 0;
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourceNormalPicker
    public final int getShadeHeaderWidth(Context context) {
        return getPanelWidth(context);
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourceNormalPicker
    public final int getTileExpandedSidePadding(Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        return SecQSPanelResourceCommon.Companion.dp(R.dimen.tile_chunk_layout_expanded_side_padding_fold_sub, context);
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourceNormalPicker
    public final int getTileLabelStartMargin(Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        return SecQSPanelResourceCommon.Companion.dp(R.dimen.large_tile_temp_label_margin_start, context);
    }
}
