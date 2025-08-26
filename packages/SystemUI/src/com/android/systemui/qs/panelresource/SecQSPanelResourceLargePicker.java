package com.android.systemui.qs.panelresource;

import android.content.Context;
import com.android.keyguard.SecurityUtils$$ExternalSyntheticOutline0;
import com.android.keyguard.StrongAuthPopup$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.qs.panelresource.SecQSPanelResourceCommon;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.SettingsHelper;
import kotlin.text.StringsKt__StringsKt;

/* loaded from: classes2.dex */
public final class SecQSPanelResourceLargePicker extends SecQSPanelResourceNormalPicker {
    public final SecQSPanelResourceCommon common;

    public SecQSPanelResourceLargePicker(SecQSPanelResourceCommon secQSPanelResourceCommon) {
        super(secQSPanelResourceCommon);
        this.common = secQSPanelResourceCommon;
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourceNormalPicker
    public final int getAlbumArtWidth(Context context) {
        return getPanelWidth(context);
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourceNormalPicker
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

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourceNormalPicker
    public final String getBottomBarTileList(int i, Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        return SecQSPanelResourceCommon.Companion.string(R.string.sec_bottom_bar_tiles_default, context);
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourceNormalPicker
    public final int getBrightnessTileLayoutBetweenMargin(Context context) {
        float panelWidth = getPanelWidth(context);
        SecQSPanelResourceCommon.Companion.getClass();
        return (int) (context.getResources().getFloat(R.dimen.sec_brightness_tile_between_margin_tablet) * panelWidth);
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourceNormalPicker
    public final int getButtonsWidth(Context context) {
        float panelWidth = getPanelWidth(context);
        SecQSPanelResourceCommon.Companion companion = SecQSPanelResourceCommon.Companion;
        companion.getClass();
        double d = context.getResources().getFloat(R.dimen.qs_button_container_width_ratio_tablet) * panelWidth;
        companion.getClass();
        return (int) Math.max(d, context.getResources().getDimensionPixelSize(R.dimen.sec_qs_button_container_size));
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourceNormalPicker
    public final int getDateButtonContainerPadding(Context context) {
        return getPopOverMargin(context);
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourceNormalPicker
    public final int getDateButtonContainerTopMargin(Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        return SecQSPanelResourceCommon.Companion.dp(R.dimen.qs_pop_over_top_margin, context);
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourceNormalPicker
    public final int getDetailContentViewMaxHeight(Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        return (int) (SecQSPanelResourceCommon.Companion.dp(R.dimen.qs_pop_over_blur_detail_height, context) * 0.68d);
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourceNormalPicker
    public final int getDetailContentViewMinHeight(Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        return SecQSPanelResourceCommon.Companion.dp(R.dimen.qs_detail_content_minheight_tablet, context);
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourceNormalPicker
    public final int getDetailHeaderHeight(Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        return SecQSPanelResourceCommon.Companion.dp(R.dimen.sec_qs_detail_header_height_tablet, context);
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourceNormalPicker
    public final int getDetailSidePadding(Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        return SecQSPanelResourceCommon.Companion.dp(R.dimen.qs_detail_side_padding_tablet, context);
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourceNormalPicker
    public final int getLabelHeight(Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        return SecQSPanelResourceCommon.Companion.dp(R.dimen.sec_qs_label_height_tablet, context);
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourceNormalPicker
    public final int getMediaDeviceBarTouchAreaBetweenPadding(Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        return SecQSPanelResourceCommon.Companion.dp(R.dimen.qspanel_media_device_touch_line_between_padding_tablet, context);
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourceNormalPicker
    public final int getMediaPlayerCollapsedHeight(Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        return SecQSPanelResourceCommon.Companion.dp(R.dimen.sec_qs_media_player_height_collapsed_tablet, context);
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourceNormalPicker
    public final int getNotificationBottomPadding(Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        return (int) (SecQSPanelResourceCommon.Companion.m2903float(R.dimen.sec_notification_stack_scroller_bottom_padding_ratio, context) * DeviceState.getDisplayHeight(context));
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourceNormalPicker
    public final int getNotificationSidePadding(Context context, boolean z) {
        float f;
        float f2;
        int panelWidth = getPanelWidth(context);
        if (z) {
            f = panelWidth;
            SecQSPanelResourceCommon.Companion.getClass();
            f2 = context.getResources().getFloat(R.dimen.sec_notification_side_padding_pop_over);
        } else {
            f = panelWidth;
            SecQSPanelResourceCommon.Companion.getClass();
            f2 = context.getResources().getFloat(R.dimen.qs_clock_side_padding_tablet);
        }
        return (int) (f2 * f);
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourceNormalPicker
    public final int getPanelHeight(Context context) {
        int availableDisplayHeight = getAvailableDisplayHeight(context);
        SecQSPanelResourceCommon.Companion.getClass();
        return SecQSPanelResourceCommon.Companion.isPortrait(context) ? StrongAuthPopup$$ExternalSyntheticOutline0.m(context, R.dimen.sec_style_qs_header_status_bar_height, (int) SecurityUtils$$ExternalSyntheticOutline0.m(context, R.dimen.qs_panel_height_ratio_tablet, availableDisplayHeight)) : availableDisplayHeight;
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourceNormalPicker
    public final int getPanelSidePadding(Context context) {
        return 0;
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourceNormalPicker
    public final int getPanelStartEndPadding(int i, Context context) {
        return 0;
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourceNormalPicker
    public final int getPanelWidth(Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        return SecQSPanelResourceCommon.Companion.dp(R.dimen.qqs_panel_width_tablet, context);
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourceNormalPicker
    public final int getPopOverBlankSpace(Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        return (int) (SecQSPanelResourceCommon.Companion.m2903float(R.dimen.qs_pop_over_blank_space, context) * DeviceState.getDisplayHeight(context));
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourceNormalPicker
    public final int getPopOverMargin(Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        return SecQSPanelResourceCommon.Companion.dp(R.dimen.qs_pop_over_margin, context);
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourceNormalPicker
    public final int getQQSPanelSidePadding(Context context) {
        float panelWidth = getPanelWidth(context);
        SecQSPanelResourceCommon.Companion.getClass();
        return (int) (context.getResources().getFloat(R.dimen.sec_notification_side_padding_pop_over) * panelWidth);
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourceNormalPicker
    public final int getQQSPanelStartEndPadding(int i, Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        return SecQSPanelResourceCommon.Companion.dp(R.dimen.sec_style_qqs_start_end_padding, context);
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourceNormalPicker
    public final int getQSGuideContainerMargin(Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        return SecQSPanelResourceCommon.Companion.dp(R.dimen.qs_guide_dialog_tablet_container_margin, context);
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourceNormalPicker
    public final int getQSGuideImageHeight(Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        return SecQSPanelResourceCommon.Companion.dp(R.dimen.qs_guide_dialog_tablet_image_height, context);
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourceNormalPicker
    public final int getQSGuideImageWidth(Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        return SecQSPanelResourceCommon.Companion.dp(R.dimen.qs_guide_dialog_tablet_image_width, context);
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourceNormalPicker
    public final int getQSGuideWidth(Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        return SecQSPanelResourceCommon.Companion.dp(R.dimen.qs_guide_dialog_tablet_panel_width, context);
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourceNormalPicker
    public final int getQsTileColumn(Context context) {
        if (this.common.isEmergencyMode) {
            SecQSPanelResourceCommon.Companion.getClass();
            return Math.max(1, SecQSPanelResourceCommon.Companion.m2904int(R.integer.sec_quick_settings_num_columns_power_saving, context));
        }
        SecQSPanelResourceCommon.Companion.getClass();
        return SecQSPanelResourceCommon.Companion.m2904int(R.integer.sec_quick_settings_num_columns_tablet, context);
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourceNormalPicker
    public final int getQuickSettingExtraSidePadding(Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        return (int) (((Math.sqrt(2.0d) * 0.43f) - 0.5f) * SecQSPanelResourceCommon.Companion.dp(R.dimen.sec_style_qs_tile_icon_size, context));
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourceNormalPicker
    public final int getShadeHeaderHeight(Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        return getPopOverBlankSpace(context) + SecQSPanelResourceCommon.Companion.dp(R.dimen.large_screen_shade_header_height_tablet, context);
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
    public final String getSmartViewBarTileList(int i, Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        return SecQSPanelResourceNormalPicker.getFilteredTiles(context, SecQSPanelResourceCommon.Companion.string(R.string.sec_smartview_bar_tile_default, context));
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourceNormalPicker
    public final int getTileChunkWidth(Context context) {
        return getPanelWidth(context) - (getPopOverMargin(context) * 2);
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourceNormalPicker
    public final int getTileExpandedHeight(Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        return SecQSPanelResourceCommon.Companion.dp(R.dimen.tile_expanded_height_tablet, context);
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourceNormalPicker
    public final int getTileExpandedSidePadding(Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        return SecQSPanelResourceCommon.Companion.dp(R.dimen.tile_chunk_layout_expanded_side_padding_tablet, context);
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourceNormalPicker
    public final int getTileExpandedWidth(Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        int iDp = SecQSPanelResourceCommon.Companion.dp(R.dimen.tile_expanded_width_tablet, context);
        SecQSPanelResourceCommon secQSPanelResourceCommon = this.common;
        return ((SettingsHelper) secQSPanelResourceCommon.settingsHelper$delegate.getValue()).isQSButtonGridPopupEnabled() ? (int) (iDp * secQSPanelResourceCommon.tileExpandedWidthRatio) : iDp;
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourceNormalPicker
    public final String getTopBarTileList(int i, Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        return SecQSPanelResourceCommon.Companion.string(R.string.sec_top_bar_tiles_default, context);
    }

    @Override // com.android.systemui.qs.panelresource.SecQSPanelResourceNormalPicker
    public final int getTopBarTileNum(int i, Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        return StringsKt__StringsKt.split$default(SecQSPanelResourceCommon.Companion.string(R.string.sec_top_bar_tiles_default, context), new String[]{","}, 0, 6).size();
    }
}
