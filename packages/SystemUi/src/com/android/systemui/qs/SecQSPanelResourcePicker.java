package com.android.systemui.qs;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import com.android.systemui.Dependency;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.tuner.TunerService;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.SettingsHelper;
import dagger.Lazy;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SecQSPanelResourcePicker {
    public Drawable mCapturedBlurredBackground;
    public final KnoxStateMonitor mKnoxStateMonitor;
    public SecQSPanelController mQsPanelController;
    public final SettingsHelper mSettingsHelper;
    public final Lazy mShadeHeaderControllerLazy;
    public int mCutoutHeight = 0;
    public int mCutoutHeightLandscape = 0;
    public int mNavBarHeight = 0;
    public int mNavBarHeightLandscape = 0;
    public float mTileExpandedWidthRatio = 1.0f;
    public boolean mDataUsageLabelVisible = false;

    public SecQSPanelResourcePicker(KeyguardStateController keyguardStateController, KnoxStateMonitor knoxStateMonitor, SettingsHelper settingsHelper, Lazy lazy) {
        this.mKnoxStateMonitor = knoxStateMonitor;
        this.mSettingsHelper = settingsHelper;
        this.mShadeHeaderControllerLazy = lazy;
    }

    public static String getBottomBarTileList(Context context) {
        Resources resources = context.getResources();
        return QpRune.QUICK_TABLET ? getTrimmedRemovableTile(context, resources.getString(R.string.sec_bottom_bar_tiles_default_tablet)) : getTrimmedRemovableTile(context, resources.getString(R.string.sec_bottom_bar_tiles_default));
    }

    public static int getBrightnessBarHeight(Context context) {
        return context.getResources().getDimensionPixelSize(R.dimen.brightness_slider_height);
    }

    public static int getBrightnessIconSize(Context context) {
        return context.getResources().getDimensionPixelSize(R.dimen.brightness_slider_icon_size);
    }

    public static int getBrightnessTileLayoutBetweenMargin(Context context) {
        Resources resources = context.getResources();
        return QpRune.QUICK_TABLET ? resources.getDimensionPixelSize(R.dimen.brightness_tile_between_margin_tablet) : resources.getDimensionPixelSize(R.dimen.brightness_tile_between_margin);
    }

    public static int getBrightnessTileLayoutHeight(Context context) {
        Resources resources = context.getResources();
        return QpRune.QUICK_TABLET ? resources.getDimensionPixelSize(R.dimen.brightness_tile_layout_height_tablet) : resources.getDimensionPixelSize(R.dimen.brightness_tile_layout_height);
    }

    public static int getBrightnessTileLayoutRightMargin(Context context) {
        Resources resources = context.getResources();
        return QpRune.QUICK_TABLET ? resources.getDimensionPixelSize(R.dimen.brightness_tile_layout_right_margin_tablet) : resources.getDimensionPixelSize(R.dimen.brightness_tile_layout_right_margin);
    }

    public static int getButtonsWidth(Context context) {
        float displayWidth = DeviceState.getDisplayWidth(context);
        Resources resources = context.getResources();
        return Math.max((int) ((QpRune.QUICK_TABLET ? resources.getFloat(R.dimen.qs_button_container_width_ratio_tablet) : resources.getFloat(R.dimen.qs_button_container_width_ratio)) * displayWidth), resources.getDimensionPixelSize(R.dimen.sec_qs_button_container_size));
    }

    public static int getLabelHeight(Context context) {
        float max;
        int displayHeight = DeviceState.getDisplayHeight(context);
        Resources resources = context.getResources();
        if (QpRune.QUICK_TABLET) {
            max = resources.getDimensionPixelSize(R.dimen.sec_qs_label_height_tablet);
        } else {
            max = Math.max(resources.getFloat(R.dimen.qs_label_height_ratio) * displayHeight, resources.getDimensionPixelSize(R.dimen.sec_qs_label_height));
        }
        return (int) max;
    }

    public static int getNotificationSidePadding(Context context) {
        float f;
        int displayWidth = DeviceState.getDisplayWidth(context);
        Resources resources = context.getResources();
        if (QpRune.QUICK_TABLET) {
            return resources.getDimensionPixelSize(R.dimen.notification_side_paddings_for_tablet);
        }
        if (isLandscape(context)) {
            float f2 = displayWidth;
            f = ((resources.getFloat(R.dimen.qs_panel_width_landscape_ratio) * f2) - (resources.getFloat(R.dimen.qqs_panel_width_landscape_ratio) * f2)) / 2.0f;
        } else {
            f = resources.getFloat(R.dimen.notification_side_padding_portrait_ratio) * displayWidth;
        }
        return (int) f;
    }

    public static int getPanelSidePadding(Context context) {
        Resources resources = context.getResources();
        if (QpRune.QUICK_TABLET) {
            return resources.getDimensionPixelSize(R.dimen.qqs_panel_side_padding_tablet);
        }
        if (isLandscape(context)) {
            return 0;
        }
        return getPanelStartEndPadding(context);
    }

    public static int getPanelStartEndPadding(Context context) {
        Resources resources = context.getResources();
        int displayWidth = DeviceState.getDisplayWidth(context);
        boolean z = QpRune.QUICK_TABLET;
        int dimensionPixelSize = z ? resources.getDimensionPixelSize(R.dimen.qqs_panel_side_padding_tablet) : (int) (resources.getFloat(R.dimen.qqs_panel_side_padding) * displayWidth);
        boolean z2 = resources.getConfiguration().orientation == 1;
        if (z || z2) {
            return dimensionPixelSize;
        }
        return 0;
    }

    public static int getPanelWidth(Context context) {
        float f;
        boolean isPortrait = isPortrait(context);
        int displayWidth = DeviceState.getDisplayWidth(context);
        DeviceState.getDisplayHeight(context);
        if (QpRune.QUICK_TABLET) {
            f = context.getResources().getDimensionPixelSize(R.dimen.qqs_panel_width_tablet);
        } else if (isPortrait) {
            f = displayWidth;
        } else {
            f = context.getResources().getFloat(R.dimen.qs_panel_width_ratio) * displayWidth;
        }
        return (int) f;
    }

    public static int getQQSPanelSidePadding(Context context) {
        if (QpRune.QUICK_TABLET || !isLandscape(context)) {
            return 0;
        }
        Resources resources = context.getResources();
        float displayWidth = DeviceState.getDisplayWidth(context);
        return (int) (((resources.getFloat(R.dimen.qs_panel_width_landscape_ratio) * displayWidth) - (resources.getFloat(R.dimen.qqs_panel_width_landscape_ratio) * displayWidth)) / 2.0f);
    }

    public static int getQsTileMinNum(Context context) {
        Resources resources = context.getResources();
        return QpRune.QUICK_TABLET ? resources.getInteger(R.integer.quick_qs_tile_min_num_tablet) : resources.getInteger(R.integer.quick_qs_tile_min_num);
    }

    public static int getQuickQSCommonBottomMargin(Context context) {
        return context.getResources().getDimensionPixelSize(R.dimen.quick_qs_common_bottom_margin);
    }

    public static int getTileExpandedHeight(Context context) {
        Resources resources = context.getResources();
        float dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.tile_expanded_height);
        if (QpRune.QUICK_TABLET) {
            return resources.getDimensionPixelSize(R.dimen.tile_expanded_height_tablet);
        }
        return Math.max(getLabelHeight(context) + getTouchIconHeight(context), (int) dimensionPixelSize);
    }

    public static int getTileIconSize(Context context) {
        return context.getResources().getDimensionPixelSize(R.dimen.sec_style_qs_tile_icon_size);
    }

    public static String getTopBarTileList(Context context) {
        Resources resources = context.getResources();
        return QpRune.QUICK_TABLET ? getTrimmedRemovableTile(context, resources.getString(R.string.sec_top_bar_tiles_default_tablet)) : getTrimmedRemovableTile(context, resources.getString(R.string.sec_top_bar_tiles_default));
    }

    public static int getTouchIconHeight(Context context) {
        return (int) Math.max(QpRune.QUICK_TABLET ? r1.getDimensionPixelSize(R.dimen.sec_qs_touch_icon_height_tablet) : DeviceState.getDisplayHeight(context) * context.getResources().getFloat(R.dimen.qs_tile_touch_area_height_ratio), getTileIconSize(context));
    }

    public static String getTrimmedRemovableTile(Context context, String str) {
        if (!QpRune.QUICK_HIDE_TILE_FROM_BAR || str == null) {
            return str;
        }
        if (((TunerService) Dependency.get(TunerService.class)).getValue(0, "hide_smart_view_large_tile_on_panel") != 1) {
            return str;
        }
        String string = context.getResources().getString(R.string.sec_removable_bar_tile);
        return str.replaceAll(string + ",", "").replaceAll("," + string, "").replaceAll(string, "");
    }

    public static boolean isLandscape(Context context) {
        return context.getResources().getConfiguration().orientation == 2;
    }

    public static boolean isNightMode(Context context) {
        return (context.getResources().getConfiguration().uiMode & 48) == 32;
    }

    public static boolean isPortrait(Context context) {
        return context.getResources().getConfiguration().orientation == 1;
    }

    public final int getAvailableDisplayHeight(Context context) {
        int navBarHeight;
        int dimensionPixelSize;
        int displayHeight = DeviceState.getDisplayHeight(context);
        Resources resources = context.getResources();
        boolean z = QpRune.QUICK_TABLET;
        if (z && isLandscape(context)) {
            navBarHeight = displayHeight - getNavBarHeight(context);
            dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.status_bar_height);
        } else {
            if (z) {
                return displayHeight;
            }
            navBarHeight = (displayHeight - getNavBarHeight(context)) - (isPortrait(context) ? this.mCutoutHeight : this.mCutoutHeightLandscape);
            dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.sec_style_qs_header_status_bar_height);
        }
        return navBarHeight - dimensionPixelSize;
    }

    public final int getNavBarHeight(Context context) {
        return isPortrait(context) ? this.mNavBarHeight : this.mNavBarHeightLandscape;
    }

    public final int getPanelHeight(Context context) {
        int availableDisplayHeight = getAvailableDisplayHeight(context);
        if (!QpRune.QUICK_TABLET || !isPortrait(context)) {
            return availableDisplayHeight;
        }
        return ((int) (context.getResources().getFloat(R.dimen.qs_panel_height_ratio_tablet) * availableDisplayHeight)) + context.getResources().getDimensionPixelSize(R.dimen.sec_style_qs_header_status_bar_height);
    }

    public final int getQsTileColumn(Context context) {
        Resources resources = context.getResources();
        return this.mSettingsHelper.isEmergencyMode() ? Math.max(1, resources.getInteger(R.integer.sec_quick_settings_num_columns_power_saving)) : QpRune.QUICK_TABLET ? resources.getInteger(R.integer.sec_quick_settings_num_columns_tablet) : resources.getInteger(R.integer.sec_quick_settings_num_columns);
    }

    public final int getTileExpandedWidth(Context context) {
        int dimensionPixelSize = QpRune.QUICK_TABLET ? context.getResources().getDimensionPixelSize(R.dimen.tile_expanded_width_tablet) : context.getResources().getDimensionPixelSize(R.dimen.tile_expanded_width);
        return ((SettingsHelper) Dependency.get(SettingsHelper.class)).isQSButtonGridPopupEnabled() ? (int) (dimensionPixelSize * this.mTileExpandedWidthRatio) : dimensionPixelSize;
    }
}
