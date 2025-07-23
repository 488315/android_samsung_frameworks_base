package com.android.systemui.qs;

import android.content.Context;
import com.android.keyguard.StrongAuthPopup$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.qs.bar.ColoredBGHelper;
import com.android.systemui.qs.panelresource.SecQSPanelResourceCommon;
import com.android.systemui.qs.panelresource.SecQSPanelResourceNormalPicker;
import com.android.systemui.qs.panelresource.SecQSPanelResourcePickHelper;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class SecQSPanelResourcePicker {
    public final SecQSPanelResourcePickHelper resourcePickHelper = new SecQSPanelResourcePickHelper();

    public SecQSPanelResourcePicker(ColoredBGHelper coloredBGHelper) {
    }

    public static boolean isNightMode(Context context) {
        return (context.getResources().getConfiguration().uiMode & 48) == 32;
    }

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

    public final int getNavBarHeight(Context context) {
        SecQSPanelResourceNormalPicker targetPicker = this.resourcePickHelper.getTargetPicker();
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

    public final int getPanelSidePadding(Context context) {
        return this.resourcePickHelper.getTargetPicker().getPanelSidePadding(context);
    }

    public final int getPanelWidth(Context context) {
        return this.resourcePickHelper.getTargetPicker().getPanelWidth(context);
    }

    public final int getQQSPanelSidePadding(Context context) {
        return this.resourcePickHelper.getTargetPicker().getQQSPanelSidePadding(context);
    }

    public final float getQsFrameX() {
        return this.resourcePickHelper.getTargetPicker().qsTransitionX;
    }

    public final int getQsScrollerTopMargin(Context context) {
        SecQSPanelResourceNormalPicker targetPicker = this.resourcePickHelper.getTargetPicker();
        targetPicker.getClass();
        SecQSPanelResourceCommon.Companion.getClass();
        return StrongAuthPopup$$ExternalSyntheticOutline0.m(context, R.dimen.sec_qs_buttons_container_margin_bottom, targetPicker.getDateButtonContainerTopMargin(context) + SecQSPanelResourceCommon.Companion.dp(R.dimen.sec_qs_buttons_container_height, context));
    }

    public final int getQsTileMinNum(Context context) {
        return this.resourcePickHelper.getTargetPicker().getQsTileMinNum(context);
    }

    public final int getQuickQSCommonBottomMargin(Context context) {
        this.resourcePickHelper.getTargetPicker().getClass();
        SecQSPanelResourceCommon.Companion.getClass();
        return SecQSPanelResourceCommon.Companion.dp(R.dimen.quick_qs_common_bottom_margin, context);
    }

    public final int getTileIconSize(Context context) {
        this.resourcePickHelper.getTargetPicker().getClass();
        SecQSPanelResourceCommon.Companion.getClass();
        return SecQSPanelResourceCommon.Companion.dp(R.dimen.sec_style_qs_tile_icon_size, context);
    }

    public final int getTileIconStartMargin(Context context) {
        this.resourcePickHelper.getTargetPicker().getClass();
        SecQSPanelResourceCommon.Companion.getClass();
        return SecQSPanelResourceCommon.Companion.dp(R.dimen.large_tile_temp_icon_margin_start, context);
    }

    public final int getTileImageSize(Context context) {
        this.resourcePickHelper.getTargetPicker().getClass();
        SecQSPanelResourceCommon.Companion.getClass();
        return SecQSPanelResourceCommon.Companion.dp(R.dimen.sec_style_qs_tile_image_size, context);
    }

    public final int getTouchIconSize(Context context) {
        this.resourcePickHelper.getTargetPicker().getClass();
        SecQSPanelResourceCommon.Companion.getClass();
        return SecQSPanelResourceCommon.Companion.dp(R.dimen.qs_tile_touch_size, context);
    }
}
