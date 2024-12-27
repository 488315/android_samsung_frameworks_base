package com.android.systemui.qs.panelresource;

import android.content.Context;
import com.android.keyguard.SecurityUtils$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.qs.SecQSPanelController;
import com.android.systemui.qs.panelresource.SecQSPanelResourceCommon;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.SettingsHelper;
import kotlin.jvm.internal.DefaultConstructorMarker;

public class SecQSPanelResourcePhonePicker {
    public final SecQSPanelResourceCommon common;
    public int cutoutHeight;
    public int cutoutHeightLandscape;
    public int navBarHeight;
    public int navBarHeightLandscape;
    public SecQSPanelController qsPanelController;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public SecQSPanelResourcePhonePicker(SecQSPanelResourceCommon secQSPanelResourceCommon) {
        this.common = secQSPanelResourceCommon;
    }

    public int getAlbumArtWidth(Context context) {
        int panelWidth;
        int panelSidePadding = getPanelSidePadding(context);
        SecQSPanelResourceCommon.Companion.getClass();
        if (SecQSPanelResourceCommon.Companion.isLandscape(context)) {
            panelWidth = getPanelWidth(context) / 2;
        } else {
            panelWidth = getPanelWidth(context);
            panelSidePadding *= 2;
        }
        return panelWidth - panelSidePadding;
    }

    public int getAvailableDisplayHeight(Context context) {
        SecQSPanelResourceCommon.Companion companion = SecQSPanelResourceCommon.Companion;
        companion.getClass();
        int displayHeight = DeviceState.getDisplayHeight(context);
        companion.getClass();
        return ((displayHeight - (SecQSPanelResourceCommon.Companion.isPortrait(context) ? this.navBarHeight : this.navBarHeightLandscape)) - (SecQSPanelResourceCommon.Companion.isPortrait(context) ? this.cutoutHeight : this.cutoutHeightLandscape)) - context.getResources().getDimensionPixelSize(R.dimen.sec_style_qs_header_status_bar_height);
    }

    public String getBottomBarTileList(int i, Context context) {
        int i2 = i == 1 ? R.string.sec_bottom_bar_tiles_default : R.string.sec_bottom_bar_tiles_default_land;
        SecQSPanelResourceCommon.Companion.getClass();
        return SecQSPanelResourceCommon.Companion.string(i2, context);
    }

    public int getBrightnessTileLayoutBetweenMargin(Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        return (int) (SecQSPanelResourceCommon.Companion.m2073float(R.dimen.sec_brightness_tile_between_margin, context) * DeviceState.getDisplayWidth(context));
    }

    public int getButtonsWidth(Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        return (int) Math.max(SecQSPanelResourceCommon.Companion.isLandscape(context) ? context.getResources().getDimensionPixelSize(R.dimen.sec_qs_button_container_touch_area) : SecurityUtils$$ExternalSyntheticOutline0.m(context, R.dimen.qs_button_container_width_ratio, DeviceState.getDisplayWidth(context)), context.getResources().getDimensionPixelSize(R.dimen.sec_qs_button_container_size));
    }

    public int getDateButtonContainerPadding(Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        if (SecQSPanelResourceCommon.Companion.isLandscape(context)) {
            return 0;
        }
        return (int) SecurityUtils$$ExternalSyntheticOutline0.m(context, R.dimen.sec_date_button_container_padding_ratio, DeviceState.getDisplayWidth(context));
    }

    public int getDetailContentViewMaxHeight(Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        return SecQSPanelResourceCommon.Companion.dp(R.dimen.qs_detail_content_maxheight, context);
    }

    public int getDetailContentViewMinHeight(Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        return SecQSPanelResourceCommon.Companion.dp(R.dimen.qs_detail_content_minheight, context);
    }

    public int getDetailSidePadding(Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        if (SecQSPanelResourceCommon.Companion.isLandscape(context)) {
            return 0;
        }
        return context.getResources().getDimensionPixelSize(R.dimen.qs_detail_side_padding);
    }

    public int getLabelHeight(Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        return SecQSPanelResourceCommon.Companion.dp(R.dimen.sec_qs_label_height, context);
    }

    public int getMediaDeviceBarTouchAreaBetweenPadding(Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        return SecQSPanelResourceCommon.Companion.dp(R.dimen.qspanel_media_device_touch_line_between_padding, context);
    }

    public int getMediaPlayerCollapsedHeight(Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        return SecQSPanelResourceCommon.Companion.dp(R.dimen.sec_qs_media_player_height_collapsed, context);
    }

    public int getNotificationSidePadding(Context context, boolean z) {
        float m;
        SecQSPanelResourceCommon.Companion.getClass();
        int displayWidth = DeviceState.getDisplayWidth(context);
        if (SecQSPanelResourceCommon.Companion.isLandscape(context)) {
            float f = displayWidth;
            m = (SecurityUtils$$ExternalSyntheticOutline0.m(context, R.dimen.qs_panel_width_landscape_ratio, f) - (context.getResources().getFloat(R.dimen.qqs_panel_width_landscape_ratio) * f)) / 2.0f;
        } else {
            m = SecurityUtils$$ExternalSyntheticOutline0.m(context, z ? R.dimen.notification_side_padding_portrait_ratio : R.dimen.notification_panel_side_padding_portrait_ratio, displayWidth);
        }
        return (int) m;
    }

    public int getPanelHeight(Context context) {
        return getAvailableDisplayHeight(context);
    }

    public int getPanelSidePadding(Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        if (SecQSPanelResourceCommon.Companion.isLandscape(context)) {
            return 0;
        }
        return getPanelStartEndPadding(context.getResources().getConfiguration().orientation, context);
    }

    public int getPanelStartEndPadding(int i, Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        int dp = SecQSPanelResourceCommon.Companion.dp(R.dimen.sec_qs_side_padding, context);
        if (i == 1) {
            return dp;
        }
        return 0;
    }

    public int getPanelWidth(Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        int displayWidth = DeviceState.getDisplayWidth(context);
        return SecQSPanelResourceCommon.Companion.isPortrait(context) ? displayWidth : (int) SecurityUtils$$ExternalSyntheticOutline0.m(context, R.dimen.qs_panel_width_ratio, displayWidth);
    }

    public int getQQSPanelSidePadding(Context context) {
        float m;
        SecQSPanelResourceCommon.Companion.getClass();
        int displayWidth = DeviceState.getDisplayWidth(context);
        if (SecQSPanelResourceCommon.Companion.isLandscape(context)) {
            float f = displayWidth;
            m = (SecurityUtils$$ExternalSyntheticOutline0.m(context, R.dimen.qs_panel_width_landscape_ratio, f) - (context.getResources().getFloat(R.dimen.qqs_panel_width_landscape_ratio) * f)) / 2.0f;
        } else {
            m = SecurityUtils$$ExternalSyntheticOutline0.m(context, R.dimen.qqs_panel_side_padding, displayWidth) - context.getResources().getDimensionPixelSize(R.dimen.sec_qs_side_padding);
        }
        return (int) m;
    }

    public int getQSGuideContainerMargin(Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        return (int) SecurityUtils$$ExternalSyntheticOutline0.m(context, R.dimen.qs_guide_dialog_container_margin, DeviceState.getDisplayWidth(context));
    }

    public int getQSGuideImageHeight(Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        return (int) SecurityUtils$$ExternalSyntheticOutline0.m(context, R.dimen.qs_guide_dialog_image_height_ratio, DeviceState.getDisplayHeight(context));
    }

    public int getQSGuideImageWidth(Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        return (int) SecurityUtils$$ExternalSyntheticOutline0.m(context, R.dimen.qs_guide_dialog_image_width_ratio, DeviceState.getDisplayWidth(context));
    }

    public int getQSGuideWidth(Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        return (int) SecurityUtils$$ExternalSyntheticOutline0.m(context, R.dimen.qs_guide_dialog_width_ratio, DeviceState.getDisplayWidth(context));
    }

    public int getQsTileColumn(Context context) {
        if (this.common.isEmergencyMode) {
            SecQSPanelResourceCommon.Companion.getClass();
            return Math.max(1, SecQSPanelResourceCommon.Companion.m2074int(R.integer.sec_quick_settings_num_columns_power_saving, context));
        }
        SecQSPanelResourceCommon.Companion.getClass();
        return SecQSPanelResourceCommon.Companion.m2074int(R.integer.sec_quick_settings_num_columns, context);
    }

    public int getQsTileMinNum(Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        return SecQSPanelResourceCommon.Companion.m2074int(R.integer.quick_qs_tile_min_num, context);
    }

    public int getQuickQsTileNum(Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        return context.getResources().getInteger(R.integer.sec_quick_qs_panel_max_columns);
    }

    public int getQuickSettingExtraSidePadding(Context context) {
        return 0;
    }

    public int getShadeHeaderHeight(Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        return SecQSPanelResourceCommon.Companion.dp(R.dimen.large_screen_shade_header_height, context);
    }

    public int getTileExpandedHeight(Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        return SecQSPanelResourceCommon.Companion.dp(R.dimen.tile_expanded_height, context);
    }

    public int getTileExpandedSidePadding(Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        return SecQSPanelResourceCommon.Companion.dp(R.dimen.tile_chunk_layout_expanded_side_padding, context);
    }

    public int getTileExpandedWidth(Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        int dp = SecQSPanelResourceCommon.Companion.dp(R.dimen.tile_expanded_width, context);
        SecQSPanelResourceCommon secQSPanelResourceCommon = this.common;
        return ((SettingsHelper) secQSPanelResourceCommon.settingsHelper$delegate.getValue()).isQSButtonGridPopupEnabled() ? (int) (dp * secQSPanelResourceCommon.tileExpandedWidthRatio) : dp;
    }

    public int getTileIconStartMargin(Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        return SecQSPanelResourceCommon.Companion.dp(R.dimen.large_tile_temp_icon_margin_start, context);
    }

    public int getTileLabelStartMargin(Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        return SecQSPanelResourceCommon.Companion.dp(R.dimen.large_tile_temp_label_margin_start, context);
    }

    public String getTopBarTileList(int i, Context context) {
        int i2 = i == 1 ? R.string.sec_top_bar_tiles_default : R.string.sec_top_bar_tiles_default_land;
        SecQSPanelResourceCommon.Companion.getClass();
        return SecQSPanelResourceCommon.Companion.string(i2, context);
    }
}
