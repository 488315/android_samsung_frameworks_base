package com.android.wm.shell.controlpanel.activity;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import androidx.appcompat.widget.MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.wm.shell.controlpanel.utils.ControlPanelUtils;
import com.samsung.systemui.splugins.volume.VolumePanelValues;

/* loaded from: classes3.dex */
public class TouchPad extends FloatingUI {
    public View mCenterText;
    public final boolean mIsMediaPanel;
    public View mTouchPadBg;
    public View mTouchPadLine;

    public TouchPad(Context context, boolean z) {
        super(context);
        this.mIsMediaPanel = z;
    }

    @Override // com.android.wm.shell.controlpanel.activity.FloatingUI
    public final void connectUIObject() {
        View viewInflate = View.inflate(this.mContext, R.layout.cursorcontrol_panel, null);
        this.mOverlayView = viewInflate;
        this.mTouchPadBg = viewInflate.findViewById(R.id.touch_pad_bg);
        this.mCenterText = this.mOverlayView.findViewById(R.id.center_text);
        View viewFindViewById = this.mOverlayView.findViewById(R.id.touch_pad_line);
        this.mTouchPadLine = viewFindViewById;
        viewFindViewById.setVisibility(4);
    }

    @Override // com.android.wm.shell.controlpanel.activity.FloatingUI
    public final void fadeInAnimation() {
        startFadeInAnimation(this.mTouchPadBg, false);
        startFadeInAnimation(this.mCenterText, false);
        new Handler(Looper.getMainLooper()).postDelayed(new TouchPad$$ExternalSyntheticLambda0(this, 0), 100L);
    }

    public final int getPixel(int i) {
        return this.mContext.getResources().getDimensionPixelSize(i);
    }

    @Override // com.android.wm.shell.controlpanel.activity.FloatingUI
    public final void setAppendLayoutParams() {
        this.mLayoutParam.setFitInsetsTypes(0);
        this.mLayoutParam.semAddExtensionFlags(VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS);
        this.mLayoutParam.layoutInDisplayCutoutMode = 3;
        int displayX = ControlPanelUtils.getDisplayX(this.mContext);
        int displayY = ControlPanelUtils.getDisplayY(this.mContext);
        Settings.Global.getInt(this.mContext.getContentResolver(), "flex_mode_scroll_wheel_pos", 2);
        boolean zIsTypeFold = ControlPanelUtils.isTypeFold();
        boolean z = this.mIsMediaPanel;
        if (zIsTypeFold) {
            if (z) {
                WindowManager.LayoutParams layoutParams = this.mLayoutParam;
                layoutParams.height = (int) ((displayY * 30.4d) / 100.0d);
                layoutParams.y = getPixel(R.dimen.basic_panel_top_margin_land);
                this.mLayoutParam.gravity = 51;
            } else {
                WindowManager.LayoutParams layoutParams2 = this.mLayoutParam;
                double d = displayY;
                layoutParams2.height = (int) ((37.1d * d) / 100.0d);
                int pixel = ((displayY / 2) - getPixel(R.dimen.basic_panel_top_margin_land)) - ((int) ((d * 18.55d) / 100.0d));
                WindowManager.LayoutParams layoutParams3 = this.mLayoutParam;
                layoutParams2.y = pixel - (layoutParams3.height / 2);
                layoutParams3.gravity = 83;
            }
            this.mLayoutParam.x = getPixel(R.dimen.touchpad_left_margin_land);
            WindowManager.LayoutParams layoutParams4 = this.mLayoutParam;
            layoutParams4.width = (displayX - layoutParams4.x) - getPixel(R.dimen.touchpad_right_margin_land);
            if (MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0.m(this.mContext) == 1) {
                WindowManager.LayoutParams layoutParams5 = this.mLayoutParam;
                layoutParams5.x = (displayX - layoutParams5.width) - getPixel(R.dimen.touchpad_left_margin_land);
            }
        } else {
            this.mLayoutParam.y = z ? getPixel(R.dimen.touchpad_with_media_bottom_margin) : getPixel(R.dimen.touchpad_bottom_margin);
            WindowManager.LayoutParams layoutParams6 = this.mLayoutParam;
            layoutParams6.width = (int) ((displayX * 88.9d) / 100.0d);
            layoutParams6.gravity = 81;
            layoutParams6.height = ((displayY / 2) - layoutParams6.y) - getPixel(R.dimen.touchpad_top_margin);
        }
        this.mLayoutParam.setTitle("FlexPanelTouchPad");
    }

    public final void startFadeInAnimation(View view, boolean z) {
        Animation animationLoadAnimation = AnimationUtils.loadAnimation(this.mContext, R.anim.fadein);
        if (z) {
            view.setVisibility(0);
        }
        view.startAnimation(animationLoadAnimation);
    }
}
