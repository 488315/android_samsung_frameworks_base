package com.android.systemui.qs.panelresource;

import android.content.Context;
import androidx.appcompat.animation.SeslRecoilAnimator$Holder$$ExternalSyntheticOutline0;
import com.android.keyguard.SecurityUtils$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.qs.panelresource.SecQSPanelResourceCommon;
import com.android.systemui.util.DeviceState;
import kotlin.Pair;

/* loaded from: classes2.dex */
public final class SecQSPanelResourceWidePicker extends SecQSPanelResourceNormalPicker {
    public final SecQSPanelResourceCommon common;

    public SecQSPanelResourceWidePicker(SecQSPanelResourceCommon secQSPanelResourceCommon) {
        super(secQSPanelResourceCommon);
        this.common = secQSPanelResourceCommon;
    }

    public static int getFoldPanelPadding(Context context) {
        float fM;
        SecQSPanelResourceCommon.Companion.getClass();
        int displayWidth = DeviceState.getDisplayWidth(context);
        if (SecQSPanelResourceCommon.Companion.isLandscape(context)) {
            float f = displayWidth;
            float fM2 = SecurityUtils$$ExternalSyntheticOutline0.m(context, R.dimen.qs_panel_width_landscape_ratio_fold, f);
            if (!DeviceState.isSmartViewFitToActiveDisplay()) {
                f *= 0.5597f;
            }
            fM = (fM2 - f) / 2.0f;
        } else {
            fM = SecurityUtils$$ExternalSyntheticOutline0.m(context, R.dimen.notification_side_padding_portrait_ratio_fold, displayWidth);
        }
        return (int) fM;
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourceNormalPicker
    public final int getBrightnessTileLayoutBetweenMargin(Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        return (int) (SecQSPanelResourceCommon.Companion.m2903float(R.dimen.sec_brightness_tile_between_margin_fold, context) * DeviceState.getDisplayWidth(context));
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourceNormalPicker
    public final int getButtonsWidth(Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        return Math.max((int) (SecQSPanelResourceCommon.Companion.m2903float(R.dimen.qs_button_container_width_ratio_fold, context) * DeviceState.getDisplayWidth(context)), context.getResources().getDimensionPixelSize(R.dimen.sec_qs_button_container_size));
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourceNormalPicker
    public final int getDateButtonContainerPadding(Context context) {
        return 0;
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourceNormalPicker
    public final int getDetailContentViewMaxHeight(Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        return SecQSPanelResourceCommon.Companion.dp(R.dimen.qs_detail_content_height_fold, context);
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourceNormalPicker
    public final int getDetailSidePadding(Context context) {
        int panelWidth = getPanelWidth(context);
        SecQSPanelResourceCommon.Companion.getClass();
        return (panelWidth - context.getResources().getDimensionPixelSize(R.dimen.qs_detail_side_padding_fold)) / 2;
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourceNormalPicker
    public final int getLabelHeight(Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        return (int) Math.max(SecQSPanelResourceCommon.Companion.m2903float(R.dimen.qs_label_height_ratio_fold, context) * DeviceState.getDisplayHeight(context), context.getResources().getDimensionPixelSize(R.dimen.sec_qs_label_height_fold));
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourceNormalPicker
    public final int getMediaDeviceBarTouchAreaBetweenPadding(Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        return SecQSPanelResourceCommon.Companion.dp(R.dimen.qspanel_media_device_touch_line_between_padding_fold, context);
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourceNormalPicker
    public final int getNotificationSidePadding(Context context, boolean z) {
        SecQSPanelResourceCommon.Companion.getClass();
        int displayWidth = DeviceState.getDisplayWidth(context);
        if (!SecQSPanelResourceCommon.Companion.isLandscape(context)) {
            return getQuickSettingExtraSidePadding(context);
        }
        float f = displayWidth;
        return (int) ((SecurityUtils$$ExternalSyntheticOutline0.m(context, R.dimen.qs_panel_width_landscape_ratio_fold, f) - (!DeviceState.isSmartViewFitToActiveDisplay() ? f * 0.6204f : DeviceState.getDisplayHeight(context))) / 2.0f);
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
        SecQSPanelResourceCommon.Companion companion = SecQSPanelResourceCommon.Companion;
        int foldPanelPadding = getFoldPanelPadding(context);
        companion.getClass();
        if (i == 1) {
            return foldPanelPadding;
        }
        return 0;
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourceNormalPicker
    public final int getPanelWidth(Context context) {
        float f;
        float f2;
        SecQSPanelResourceCommon.Companion.getClass();
        Pair pair = new Pair(Boolean.valueOf(SecQSPanelResourceCommon.Companion.isLandscape(context)), Integer.valueOf(DeviceState.getDisplayWidth(context)));
        boolean zBooleanValue = ((Boolean) pair.component1()).booleanValue();
        int iIntValue = ((Number) pair.component2()).intValue();
        if (DeviceState.isSmartViewFitToActiveDisplay()) {
            return zBooleanValue ? (int) (iIntValue * 0.735f) : iIntValue;
        }
        int quickSettingExtraSidePadding = getQuickSettingExtraSidePadding(context) * 2;
        if (zBooleanValue) {
            f = iIntValue;
            f2 = 0.8097f;
        } else {
            f = iIntValue;
            f2 = 0.7f;
        }
        return quickSettingExtraSidePadding + ((int) (f * f2));
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourceNormalPicker
    public final int getQQSPanelSidePadding(Context context) {
        float fM;
        SecQSPanelResourceCommon.Companion.getClass();
        int displayWidth = DeviceState.getDisplayWidth(context);
        if (SecQSPanelResourceCommon.Companion.isLandscape(context)) {
            float f = displayWidth;
            fM = SeslRecoilAnimator$Holder$$ExternalSyntheticOutline0.m(f, 0.6204f, SecurityUtils$$ExternalSyntheticOutline0.m(context, R.dimen.qs_panel_width_landscape_ratio_fold, f), 2.0f);
        } else {
            fM = SecurityUtils$$ExternalSyntheticOutline0.m(context, R.dimen.notification_side_padding_portrait_ratio_fold, displayWidth * (-1));
        }
        return (int) fM;
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourceNormalPicker
    public final int getQQSPanelStartEndPadding(int i, Context context) {
        SecQSPanelResourceCommon.Companion companion = SecQSPanelResourceCommon.Companion;
        int foldPanelPadding = getFoldPanelPadding(context) + getNotificationSidePadding(context, false);
        companion.getClass();
        if (i == 1) {
            return foldPanelPadding;
        }
        return 0;
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourceNormalPicker
    public final int getQSGuideContainerMargin(Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        return (int) (SecQSPanelResourceCommon.Companion.m2903float(R.dimen.qs_guide_dialog_fold_container_margin, context) * DeviceState.getDisplayWidth(context));
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourceNormalPicker
    public final int getQSGuideImageHeight(Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        return (int) (SecQSPanelResourceCommon.Companion.m2903float(R.dimen.qs_guide_dialog_fold_image_height_ratio, context) * DeviceState.getDisplayHeight(context));
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourceNormalPicker
    public final int getQSGuideImageWidth(Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        return (int) (SecQSPanelResourceCommon.Companion.m2903float(R.dimen.qs_guide_dialog_fold_image_width_ratio, context) * DeviceState.getDisplayWidth(context));
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourceNormalPicker
    public final int getQSGuideWidth(Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        return (int) (SecQSPanelResourceCommon.Companion.m2903float(R.dimen.qs_guide_dialog_fold_width_ratio, context) * DeviceState.getDisplayWidth(context));
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourceNormalPicker
    public final int getQsTileColumn(Context context) {
        if (this.common.isEmergencyMode) {
            SecQSPanelResourceCommon.Companion.getClass();
            return Math.max(1, SecQSPanelResourceCommon.Companion.m2904int(R.integer.sec_quick_settings_num_columns_power_saving, context));
        }
        SecQSPanelResourceCommon.Companion.getClass();
        return SecQSPanelResourceCommon.Companion.m2904int(R.integer.sec_quick_settings_num_columns_fold, context);
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourceNormalPicker
    public final int getQuickSettingExtraSidePadding(Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        return (int) (((Math.sqrt(2.0d) * 0.43f) - 0.5f) * SecQSPanelResourceCommon.Companion.dp(R.dimen.sec_style_qs_tile_icon_size, context));
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourceNormalPicker
    public final int getShadeHeaderHeight(Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        return SecQSPanelResourceCommon.Companion.dp(R.dimen.large_screen_shade_header_height_fold, context);
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourceNormalPicker
    public final int getShadeHeaderSidePadding(Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        return SecQSPanelResourceCommon.Companion.dp(R.dimen.large_screen_shade_header_side_padding, context);
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourceNormalPicker
    public final int getShadeHeaderWidth(Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        return DeviceState.getDisplayWidth(context);
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourceNormalPicker
    public final int getTileExpandedSidePadding(Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        return SecQSPanelResourceCommon.Companion.dp(R.dimen.tile_chunk_layout_expanded_side_padding, context);
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourceNormalPicker
    public final int getTileLabelStartMargin(Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        return SecQSPanelResourceCommon.Companion.dp(R.dimen.large_tile_temp_label_margin_start_fold, context);
    }
}
