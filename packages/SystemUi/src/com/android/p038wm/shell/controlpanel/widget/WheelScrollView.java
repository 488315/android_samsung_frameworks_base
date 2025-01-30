package com.android.p038wm.shell.controlpanel.widget;

import android.content.Context;
import android.provider.Settings;
import android.view.View;
import android.view.WindowManager;
import androidx.appcompat.widget.MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0;
import com.android.p038wm.shell.controlpanel.activity.FloatingUI;
import com.android.p038wm.shell.controlpanel.utils.ControlPanelUtils;
import com.android.systemui.R;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class WheelScrollView extends FloatingUI {
    public CustomWheelView mCustomWheelView;
    public final boolean mIsMediaPanel;

    public WheelScrollView(Context context, boolean z) {
        super(context);
        this.mIsMediaPanel = z;
    }

    @Override // com.android.p038wm.shell.controlpanel.activity.FloatingUI
    public final void connectUIObject() {
        View inflate = View.inflate(this.mContext, R.layout.wheel_scroll_layout, null);
        this.mOverlayView = inflate;
        this.mCustomWheelView = (CustomWheelView) inflate.findViewById(R.id.custom_wheel_view);
    }

    public final int getPixel(int i) {
        return this.mContext.getResources().getDimensionPixelSize(i);
    }

    @Override // com.android.p038wm.shell.controlpanel.activity.FloatingUI
    public final void setAppendLayoutParams() {
        this.mLayoutParam.setFitInsetsTypes(0);
        this.mLayoutParam.semAddExtensionFlags(QuickStepContract.SYSUI_STATE_BUBBLES_MANAGE_MENU_EXPANDED);
        this.mLayoutParam.layoutInDisplayCutoutMode = 3;
        Context context = this.mContext;
        int displayX = ControlPanelUtils.getDisplayX(context);
        int displayY = ControlPanelUtils.getDisplayY(context);
        int i = Settings.Global.getInt(context.getContentResolver(), "flex_mode_scroll_wheel_pos", 2);
        boolean isTypeFold = ControlPanelUtils.isTypeFold();
        boolean z = this.mIsMediaPanel;
        if (isTypeFold) {
            if (z) {
                this.mLayoutParam.height = ((int) ((displayY * 30.4d) / 100.0d)) - (getPixel(R.dimen.scroll_wheel_margin) * 2);
                this.mLayoutParam.y = getPixel(R.dimen.scroll_wheel_margin) + getPixel(R.dimen.basic_panel_top_margin_land);
            } else {
                double d = displayY;
                this.mLayoutParam.height = ((int) ((37.1d * d) / 100.0d)) - (getPixel(R.dimen.scroll_wheel_margin) * 2);
                this.mLayoutParam.y = (((displayY / 2) - getPixel(R.dimen.basic_panel_top_margin_land)) - ((int) ((d * 18.55d) / 100.0d))) - (this.mLayoutParam.height / 2);
            }
            this.mLayoutParam.width = getPixel(R.dimen.scroll_wheel_width);
            if (i == 2) {
                this.mLayoutParam.x = getPixel(R.dimen.scroll_wheel_right_margin_land);
            } else {
                this.mLayoutParam.x = getPixel(R.dimen.scroll_wheel_left_margin_without_panel_width_land) + ((int) ((displayX * 6.76d) / 100.0d));
            }
            this.mLayoutParam.gravity = (i == 2 ? 5 : 3) | (z ? 48 : 80);
            if (MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0.m11m(context) == 1) {
                WindowManager.LayoutParams layoutParams = this.mLayoutParam;
                layoutParams.x = ((displayX - layoutParams.width) - ((int) ((displayX * 6.76d) / 100.0d))) - getPixel(R.dimen.scroll_wheel_left_margin_without_panel_width_land);
            }
        } else {
            if (z) {
                this.mLayoutParam.y = getPixel(R.dimen.scroll_wheel_margin) + getPixel(R.dimen.touchpad_with_media_bottom_margin);
            } else {
                this.mLayoutParam.y = getPixel(R.dimen.scroll_wheel_margin) + getPixel(R.dimen.touchpad_bottom_margin);
            }
            if (i == 2) {
                this.mLayoutParam.gravity = 85;
            } else if (i == 1) {
                this.mLayoutParam.gravity = 83;
            }
            this.mLayoutParam.width = getPixel(R.dimen.scroll_wheel_width);
            this.mLayoutParam.x = getPixel(R.dimen.scroll_wheel_outer_margin);
            WindowManager.LayoutParams layoutParams2 = this.mLayoutParam;
            layoutParams2.height = (((displayY / 2) - layoutParams2.y) - getPixel(R.dimen.touchpad_top_margin)) - (getPixel(R.dimen.scroll_wheel_margin) * 2);
        }
        this.mLayoutParam.setTitle("FlexPanelWheelScrollView");
    }

    @Override // com.android.p038wm.shell.controlpanel.activity.FloatingUI
    public final void fadeInAnimation() {
    }
}
