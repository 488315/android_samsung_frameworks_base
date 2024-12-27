package com.android.systemui.qs.panelresource;

import android.content.Context;
import com.android.keyguard.SecurityUtils$$ExternalSyntheticOutline0;
import com.android.keyguard.StrongAuthPopup$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.qs.panelresource.SecQSPanelResourceCommon;
import com.android.systemui.util.DeviceState;

public final class SecQSPanelResourceTabletPicker extends SecQSPanelResourcePhonePicker {
    public final SecQSPanelResourceCommon common;

    public SecQSPanelResourceTabletPicker(SecQSPanelResourceCommon secQSPanelResourceCommon) {
        super(secQSPanelResourceCommon);
        this.common = secQSPanelResourceCommon;
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourcePhonePicker
    public final int getAlbumArtWidth(Context context) {
        int panelWidth = getPanelWidth(context);
        SecQSPanelResourceCommon.Companion.getClass();
        return panelWidth - (context.getResources().getDimensionPixelSize(R.dimen.sec_media_player_side_padding_tablet) * 2);
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourcePhonePicker
    public final int getAvailableDisplayHeight(Context context) {
        SecQSPanelResourceCommon.Companion companion = SecQSPanelResourceCommon.Companion;
        companion.getClass();
        int displayHeight = DeviceState.getDisplayHeight(context);
        if (!SecQSPanelResourceCommon.Companion.isLandscape(context)) {
            return displayHeight;
        }
        companion.getClass();
        return (displayHeight - (SecQSPanelResourceCommon.Companion.isPortrait(context) ? this.navBarHeight : this.navBarHeightLandscape)) - context.getResources().getDimensionPixelSize(R.dimen.status_bar_height);
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourcePhonePicker
    public final String getBottomBarTileList(int i, Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        return SecQSPanelResourceCommon.Companion.string(R.string.sec_bottom_bar_tiles_default, context);
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourcePhonePicker
    public final int getBrightnessTileLayoutBetweenMargin(Context context) {
        float panelWidth = getPanelWidth(context);
        SecQSPanelResourceCommon.Companion.getClass();
        return (int) (context.getResources().getFloat(R.dimen.sec_brightness_tile_between_margin_tablet) * panelWidth);
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourcePhonePicker
    public final int getButtonsWidth(Context context) {
        float panelWidth = getPanelWidth(context);
        SecQSPanelResourceCommon.Companion.getClass();
        return (int) Math.max(context.getResources().getFloat(R.dimen.qs_button_container_width_ratio_tablet) * panelWidth, context.getResources().getDimensionPixelSize(R.dimen.sec_qs_button_container_size));
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourcePhonePicker
    public final int getDateButtonContainerPadding(Context context) {
        float panelWidth = getPanelWidth(context);
        SecQSPanelResourceCommon.Companion.getClass();
        return (int) (context.getResources().getFloat(R.dimen.sec_date_button_container_padding_ratio) * panelWidth);
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourcePhonePicker
    public final int getDetailContentViewMaxHeight(Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        return SecQSPanelResourceCommon.Companion.dp(R.dimen.qs_detail_content_height_tablet, context);
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourcePhonePicker
    public final int getDetailContentViewMinHeight(Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        return SecQSPanelResourceCommon.Companion.dp(R.dimen.qs_detail_content_height_tablet, context);
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourcePhonePicker
    public final int getDetailSidePadding(Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        return context.getResources().getDimensionPixelSize(R.dimen.qs_detail_side_padding_tablet);
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourcePhonePicker
    public final int getLabelHeight(Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        return SecQSPanelResourceCommon.Companion.dp(R.dimen.sec_qs_label_height_tablet, context);
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourcePhonePicker
    public final int getMediaDeviceBarTouchAreaBetweenPadding(Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        return SecQSPanelResourceCommon.Companion.dp(R.dimen.qspanel_media_device_touch_line_between_padding_tablet, context);
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourcePhonePicker
    public final int getMediaPlayerCollapsedHeight(Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        return SecQSPanelResourceCommon.Companion.dp(R.dimen.sec_qs_media_player_height_collapsed_tablet, context);
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourcePhonePicker
    public final int getNotificationSidePadding(Context context, boolean z) {
        SecQSPanelResourceCommon.Companion.getClass();
        return SecQSPanelResourceCommon.Companion.dp(R.dimen.notification_side_paddings_for_tablet, context);
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourcePhonePicker
    public final int getPanelHeight(Context context) {
        int availableDisplayHeight = getAvailableDisplayHeight(context);
        SecQSPanelResourceCommon.Companion.getClass();
        return SecQSPanelResourceCommon.Companion.isPortrait(context) ? StrongAuthPopup$$ExternalSyntheticOutline0.m(context, R.dimen.sec_style_qs_header_status_bar_height, (int) SecurityUtils$$ExternalSyntheticOutline0.m(context, R.dimen.qs_panel_height_ratio_tablet, availableDisplayHeight)) : availableDisplayHeight;
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourcePhonePicker
    public final int getPanelSidePadding(Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        return SecQSPanelResourceCommon.Companion.dp(R.dimen.sec_qs_side_padding, context);
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourcePhonePicker
    public final int getPanelWidth(Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        return SecQSPanelResourceCommon.Companion.dp(R.dimen.qqs_panel_width_tablet, context);
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourcePhonePicker
    public final int getQQSPanelSidePadding(Context context) {
        return 0;
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourcePhonePicker
    public final int getQsTileColumn(Context context) {
        if (this.common.isEmergencyMode) {
            SecQSPanelResourceCommon.Companion.getClass();
            return Math.max(1, SecQSPanelResourceCommon.Companion.m2074int(R.integer.sec_quick_settings_num_columns_power_saving, context));
        }
        SecQSPanelResourceCommon.Companion.getClass();
        return SecQSPanelResourceCommon.Companion.m2074int(R.integer.sec_quick_settings_num_columns_tablet, context);
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourcePhonePicker
    public final int getQsTileMinNum(Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        return SecQSPanelResourceCommon.Companion.m2074int(R.integer.quick_qs_tile_min_num_tablet, context);
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourcePhonePicker
    public final int getQuickQsTileNum(Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        return context.getResources().getInteger(R.integer.sec_quick_qs_panel_max_columns_tablet);
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourcePhonePicker
    public final int getShadeHeaderHeight(Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        return SecQSPanelResourceCommon.Companion.dp(R.dimen.large_screen_shade_header_height_tablet, context);
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourcePhonePicker
    public final int getTileExpandedHeight(Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        return SecQSPanelResourceCommon.Companion.dp(R.dimen.tile_expanded_height_tablet, context);
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourcePhonePicker
    public final String getTopBarTileList(int i, Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        return SecQSPanelResourceCommon.Companion.string(R.string.sec_top_bar_tiles_default, context);
    }
}
