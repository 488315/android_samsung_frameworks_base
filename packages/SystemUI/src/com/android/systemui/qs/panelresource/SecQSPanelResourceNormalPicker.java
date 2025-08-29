package com.android.systemui.qs.panelresource;

import android.content.Context;
import android.content.res.Resources;
import com.android.keyguard.SecurityUtils$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.qs.SecQSPanelController;
import com.android.systemui.qs.panelresource.SecQSPanelResourceCommon;
import com.android.systemui.tuner.TunerService;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.SettingsHelper;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.text.StringsKt__StringsJVMKt;
import kotlin.text.StringsKt__StringsKt;

/* loaded from: classes2.dex */
public class SecQSPanelResourceNormalPicker {
    public final SecQSPanelResourceCommon common;
    public int cutoutHeight;
    public int cutoutHeightLandscape;
    public int navBarHeight;
    public int navBarHeightLandscape;
    public SecQSPanelController qsPanelController;
    public float qsTransitionX;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public SecQSPanelResourceNormalPicker(SecQSPanelResourceCommon secQSPanelResourceCommon) {
        this.common = secQSPanelResourceCommon;
    }

    public static String getFilteredTiles(Context context, String str) throws Resources.NotFoundException {
        if (!QpRune.QUICK_TILE_HIDE_FROM_BAR || ((TunerService) Dependency.sDependency.getDependencyInner(TunerService.class)).getValue(0, "hide_smart_view_large_tile_on_panel") != 1) {
            return str;
        }
        return StringsKt__StringsJVMKt.replace$default(str, context.getResources().getString(R.string.sec_removable_bar_tile) + ",", "");
    }

    public int getAlbumArtWidth(Context context) {
        int panelWidth = getPanelWidth(context);
        SecQSPanelResourceCommon.Companion.getClass();
        return SecQSPanelResourceCommon.Companion.isLandscape(context) ? ((panelWidth - context.getResources().getDimensionPixelSize(R.dimen.large_tile_between_margin)) + 1) / 2 : panelWidth - (getPanelSidePadding(context) * 2);
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
        return (int) (SecQSPanelResourceCommon.Companion.m2901float(R.dimen.sec_brightness_tile_between_margin, context) * DeviceState.getDisplayWidth(context));
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
        return context.getResources().getDimensionPixelSize(R.dimen.sec_style_qs_header_status_bar_side_padding);
    }

    public int getDateButtonContainerTopMargin(Context context) {
        return context.getResources().getDimensionPixelSize(R.dimen.sec_qs_buttons_container_margin_top);
    }

    public int getDetailContentViewMaxHeight(Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        return SecQSPanelResourceCommon.Companion.dp(R.dimen.qs_detail_content_maxheight, context);
    }

    public int getDetailContentViewMinHeight(Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        return SecQSPanelResourceCommon.Companion.dp(R.dimen.qs_detail_content_minheight, context);
    }

    public int getDetailHeaderHeight(Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        return SecQSPanelResourceCommon.Companion.dp(R.dimen.sec_qs_detail_header_height, context);
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

    public int getNotificationBottomPadding(Context context) {
        return 0;
    }

    public int getNotificationSidePadding(Context context, boolean z) {
        Number numberValueOf;
        SecQSPanelResourceCommon.Companion.getClass();
        int displayWidth = DeviceState.getDisplayWidth(context);
        if (SecQSPanelResourceCommon.Companion.isLandscape(context)) {
            float f = displayWidth;
            numberValueOf = Float.valueOf((SecurityUtils$$ExternalSyntheticOutline0.m(context, R.dimen.qs_panel_width_landscape_ratio, f) - (context.getResources().getFloat(R.dimen.qqs_panel_width_landscape_ratio) * f)) / 2.0f);
        } else if (z) {
            numberValueOf = Float.valueOf(context.getResources().getFloat(R.dimen.notification_side_padding_portrait_ratio) * displayWidth);
        } else {
            numberValueOf = Integer.valueOf(context.getResources().getDimensionPixelSize(R.dimen.sec_style_qs_header_status_bar_side_padding));
        }
        return numberValueOf.intValue();
    }

    public int getPanelHeight(Context context) {
        return getAvailableDisplayHeight(context);
    }

    public int getPanelSidePadding(Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        if (SecQSPanelResourceCommon.Companion.isLandscape(context)) {
            return 0;
        }
        return context.getResources().getDimensionPixelSize(R.dimen.sec_qs_side_padding);
    }

    public int getPanelStartEndPadding(int i, Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        int iDp = SecQSPanelResourceCommon.Companion.dp(R.dimen.sec_qs_side_padding, context);
        if (i == 1) {
            return iDp;
        }
        return 0;
    }

    public int getPanelWidth(Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        int displayWidth = DeviceState.getDisplayWidth(context);
        return SecQSPanelResourceCommon.Companion.isPortrait(context) ? displayWidth : (int) SecurityUtils$$ExternalSyntheticOutline0.m(context, R.dimen.qs_panel_width_ratio, displayWidth);
    }

    public int getPopOverBlankSpace(Context context) {
        return 0;
    }

    public int getPopOverMargin(Context context) {
        return 0;
    }

    public int getQQSPanelSidePadding(Context context) {
        Number numberValueOf;
        SecQSPanelResourceCommon.Companion.getClass();
        int displayWidth = DeviceState.getDisplayWidth(context);
        if (SecQSPanelResourceCommon.Companion.isLandscape(context)) {
            float f = displayWidth;
            numberValueOf = Float.valueOf((SecurityUtils$$ExternalSyntheticOutline0.m(context, R.dimen.qs_panel_width_landscape_ratio, f) - (context.getResources().getFloat(R.dimen.qqs_panel_width_landscape_ratio) * f)) / 2.0f);
        } else {
            numberValueOf = 0;
        }
        return numberValueOf.intValue();
    }

    public int getQQSPanelStartEndPadding(int i, Context context) {
        return getPanelStartEndPadding(i, context);
    }

    public int getQSGuideContainerMargin(Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        return (int) (SecQSPanelResourceCommon.Companion.m2901float(R.dimen.qs_guide_dialog_container_margin, context) * DeviceState.getDisplayWidth(context));
    }

    public int getQSGuideImageHeight(Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        return (int) (SecQSPanelResourceCommon.Companion.m2901float(R.dimen.qs_guide_dialog_image_height_ratio, context) * DeviceState.getDisplayHeight(context));
    }

    public int getQSGuideImageWidth(Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        return (int) (SecQSPanelResourceCommon.Companion.m2901float(R.dimen.qs_guide_dialog_image_width_ratio, context) * DeviceState.getDisplayWidth(context));
    }

    public int getQSGuideWidth(Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        return (int) (SecQSPanelResourceCommon.Companion.m2901float(R.dimen.qs_guide_dialog_width_ratio, context) * DeviceState.getDisplayWidth(context));
    }

    public int getQsTileColumn(Context context) {
        if (this.common.isEmergencyMode) {
            SecQSPanelResourceCommon.Companion.getClass();
            return Math.max(1, SecQSPanelResourceCommon.Companion.m2902int(R.integer.sec_quick_settings_num_columns_power_saving, context));
        }
        SecQSPanelResourceCommon.Companion.getClass();
        return SecQSPanelResourceCommon.Companion.m2902int(R.integer.sec_quick_settings_num_columns, context);
    }

    public int getQuickSettingExtraSidePadding(Context context) {
        return 0;
    }

    public int getShadeHeaderHeight(Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        return SecQSPanelResourceCommon.Companion.dp(R.dimen.large_screen_shade_header_height, context);
    }

    public int getShadeHeaderSidePadding(Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        if (SecQSPanelResourceCommon.Companion.isLandscape(context)) {
            return 0;
        }
        return context.getResources().getDimensionPixelSize(R.dimen.sec_style_qs_header_status_bar_side_padding);
    }

    public int getShadeHeaderWidth(Context context) {
        return getPanelWidth(context);
    }

    public String getSmartViewBarTileList(int i, Context context) {
        int i2 = i == 1 ? R.string.sec_smartview_bar_tile_default : R.string.sec_smartview_bar_tile_default_land;
        SecQSPanelResourceCommon.Companion.getClass();
        return getFilteredTiles(context, SecQSPanelResourceCommon.Companion.string(i2, context));
    }

    public int getTileChunkWidth(Context context) {
        return getPanelWidth(context) - (getPanelSidePadding(context) * 2);
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
        int iDp = SecQSPanelResourceCommon.Companion.dp(R.dimen.tile_expanded_width, context);
        int iM = (int) SecurityUtils$$ExternalSyntheticOutline0.m(context, R.dimen.qs_tile_expanded_width_ratio, getTileChunkWidth(context));
        if (iDp < iM) {
            iDp = iM;
        }
        SecQSPanelResourceCommon secQSPanelResourceCommon = this.common;
        return ((SettingsHelper) secQSPanelResourceCommon.settingsHelper$delegate.getValue()).isQSButtonGridPopupEnabled() ? (int) (iDp * secQSPanelResourceCommon.tileExpandedWidthRatio) : iDp;
    }

    public int getTileLabelStartMargin(Context context) {
        SecQSPanelResourceCommon.Companion.getClass();
        return SecQSPanelResourceCommon.Companion.dp(R.dimen.large_tile_temp_label_margin_start, context);
    }

    public String getTopBarTileList(int i, Context context) {
        if (i == 1) {
            SecQSPanelResourceCommon.Companion.getClass();
            return SecQSPanelResourceCommon.Companion.string(R.string.sec_top_bar_tiles_default, context);
        }
        SecQSPanelResourceCommon.Companion.getClass();
        return getFilteredTiles(context, SecQSPanelResourceCommon.Companion.string(R.string.sec_top_bar_tiles_default_land, context));
    }

    public int getTopBarTileNum(int i, Context context) {
        String strString;
        if (i == 1) {
            SecQSPanelResourceCommon.Companion.getClass();
            strString = SecQSPanelResourceCommon.Companion.string(R.string.sec_top_bar_tiles_default, context);
        } else {
            SecQSPanelResourceCommon.Companion.getClass();
            strString = SecQSPanelResourceCommon.Companion.string(R.string.sec_top_bar_tiles_default_land, context);
        }
        return StringsKt__StringsKt.split$default(strString, new String[]{","}, 0, 6).size();
    }
}
