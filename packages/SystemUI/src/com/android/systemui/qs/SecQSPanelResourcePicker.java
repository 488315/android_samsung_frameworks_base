package com.android.systemui.qs;

import android.content.Context;
import com.android.keyguard.StrongAuthPopup$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.qs.panelresource.SecQSPanelResourceCommon;
import com.android.systemui.qs.panelresource.SecQSPanelResourcePhonePicker;
import com.android.systemui.qs.panelresource.SecQSPanelResourcePickHelper;
import com.android.systemui.tuner.TunerService;
import kotlin.text.Regex;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SecQSPanelResourcePicker {
    public final SecQSPanelResourcePickHelper resourcePickHelper = new SecQSPanelResourcePickHelper();

    public final int getBrightnessBarContainerHeight(Context context) {
        this.resourcePickHelper.getTargetPicker().getClass();
        SecQSPanelResourceCommon.Companion.getClass();
        return SecQSPanelResourceCommon.Companion.dp(R.dimen.brightness_slider_expanded_height, context);
    }

    public final int getBrightnessBarExpandedHeight(Context context) {
        this.resourcePickHelper.getTargetPicker().getClass();
        SecQSPanelResourceCommon.Companion.getClass();
        return SecQSPanelResourceCommon.Companion.dp(R.dimen.brightness_slider_expanded_height, context);
    }

    public final int getBrightnessBarHeight(Context context) {
        this.resourcePickHelper.getTargetPicker().getClass();
        SecQSPanelResourceCommon.Companion.getClass();
        return SecQSPanelResourceCommon.Companion.dp(R.dimen.brightness_slider_height, context);
    }

    public final int getHeaderTranslationXDiff(Context context) {
        int qQSPanelSidePadding = this.resourcePickHelper.getTargetPicker().getQQSPanelSidePadding(context);
        if (context.getResources().getConfiguration().orientation == 2) {
            return 0;
        }
        return qQSPanelSidePadding;
    }

    public final int getNavBarHeight(Context context) {
        SecQSPanelResourcePhonePicker targetPicker = this.resourcePickHelper.getTargetPicker();
        targetPicker.getClass();
        SecQSPanelResourceCommon.Companion.getClass();
        return SecQSPanelResourceCommon.Companion.isPortrait(context) ? targetPicker.navBarHeight : targetPicker.navBarHeightLandscape;
    }

    public final int getNoBGTileIconSize(Context context) {
        this.resourcePickHelper.getTargetPicker().getClass();
        SecQSPanelResourceCommon.Companion.getClass();
        return SecQSPanelResourceCommon.Companion.dp(R.dimen.sec_style_qs_no_bg_tile_icon_size, context);
    }

    public final int getNoBGTileIconStartMargin(Context context) {
        this.resourcePickHelper.getTargetPicker().getClass();
        SecQSPanelResourceCommon.Companion.getClass();
        return SecQSPanelResourceCommon.Companion.dp(R.dimen.large_no_bg_tile_temp_icon_margin_start, context);
    }

    public final int getNoBGTileLabelStartMargin(Context context) {
        this.resourcePickHelper.getTargetPicker().getClass();
        SecQSPanelResourceCommon.Companion.getClass();
        return SecQSPanelResourceCommon.Companion.dp(R.dimen.large_no_bg_tile_temp_label_margin_start, context);
    }

    public final int getPanelWidth(Context context) {
        return this.resourcePickHelper.getTargetPicker().getPanelWidth(context);
    }

    public final int getQsScrollerTopMargin(Context context) {
        this.resourcePickHelper.getTargetPicker().getClass();
        SecQSPanelResourceCommon.Companion.getClass();
        return StrongAuthPopup$$ExternalSyntheticOutline0.m(context, R.dimen.sec_qs_buttons_container_margin_bottom, StrongAuthPopup$$ExternalSyntheticOutline0.m(context, R.dimen.sec_qs_buttons_container_margin_top, SecQSPanelResourceCommon.Companion.dp(R.dimen.sec_qs_buttons_container_height, context)));
    }

    public final int getQuickQSCommonBottomMargin(Context context) {
        this.resourcePickHelper.getTargetPicker().getClass();
        SecQSPanelResourceCommon.Companion.getClass();
        return SecQSPanelResourceCommon.Companion.dp(R.dimen.quick_qs_common_bottom_margin, context);
    }

    public final String getSmartViewBarTileList(Context context) {
        this.resourcePickHelper.getTargetPicker().getClass();
        SecQSPanelResourceCommon.Companion.getClass();
        String string = SecQSPanelResourceCommon.Companion.string(R.string.sec_smartview_bar_tile_default, context);
        return (QpRune.QUICK_TILE_HIDE_FROM_BAR && ((TunerService) Dependency.sDependency.getDependencyInner(TunerService.class)).getValue(0, "hide_smart_view_large_tile_on_panel") == 1) ? new Regex(context.getResources().getString(R.string.sec_removable_bar_tile).concat(",")).replace(string, "") : string;
    }

    public final int getTileIconSize(Context context) {
        this.resourcePickHelper.getTargetPicker().getClass();
        SecQSPanelResourceCommon.Companion.getClass();
        return SecQSPanelResourceCommon.Companion.dp(R.dimen.sec_style_qs_tile_icon_size, context);
    }

    public final int getTouchIconSize(Context context) {
        this.resourcePickHelper.getTargetPicker().getClass();
        SecQSPanelResourceCommon.Companion.getClass();
        return SecQSPanelResourceCommon.Companion.dp(R.dimen.qs_tile_touch_size, context);
    }
}
