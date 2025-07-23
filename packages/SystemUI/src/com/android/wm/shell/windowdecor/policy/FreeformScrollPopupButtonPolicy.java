package com.android.wm.shell.windowdecor.policy;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.view.ViewGroup;
import com.android.systemui.R;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.windowdecor.DesktopModeWindowDecorViewModel;
import com.android.wm.shell.windowdecor.MenuPopupAnimator;
import com.android.wm.shell.windowdecor.widget.PopupHorizontalScrollView;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class FreeformScrollPopupButtonPolicy extends FreeformPopupButtonPolicy {
    public FreeformScrollPopupButtonPolicy(ActivityManager.RunningTaskInfo runningTaskInfo, Context context, DisplayController displayController, boolean z, boolean z2) {
        super(runningTaskInfo, context, displayController, z, z2);
    }

    @Override // com.android.wm.shell.windowdecor.policy.FreeformPopupButtonPolicy, com.android.wm.shell.windowdecor.policy.PopupButtonPolicy
    public final int getLayoutResId() {
        return R.layout.mw_handle_popup_menu_min_freeform;
    }

    @Override // com.android.wm.shell.windowdecor.policy.FreeformPopupButtonPolicy, com.android.wm.shell.windowdecor.policy.PopupButtonPolicy
    public final int getVisibleButtonCount() {
        return 4;
    }

    @Override // com.android.wm.shell.windowdecor.policy.PopupButtonPolicy
    public final void setupRootView(Context context, View view, View.OnTouchListener onTouchListener, View.OnClickListener onClickListener) {
        PopupHorizontalScrollView popupHorizontalScrollView = (PopupHorizontalScrollView) view.findViewById(R.id.handle_menu_scroll);
        if (popupHorizontalScrollView != null) {
            popupHorizontalScrollView.mEventListener = (DesktopModeWindowDecorViewModel.DesktopModeTouchEventListener) onTouchListener;
        }
        ViewGroup viewGroup = (ViewGroup) view;
        this.mRootView = viewGroup;
        viewGroup.setElevation(WindowDecorButtonPolicy.loadDimensionPixelSize(((PopupButtonPolicy) this).mContext.getResources(), R.dimen.mw_handle_menu_shadow));
        ViewGroup viewGroup2 = this.mRootView;
        int color = context.getResources().getColor(this.mIsNightMode ? R.color.mw_caption_background_color_dark : R.color.mw_caption_background_color_light, null);
        GradientDrawable gradientDrawable = (GradientDrawable) viewGroup2.getBackground();
        if (gradientDrawable != null) {
            gradientDrawable.setColor(color);
        }
        setupCaptionButtonState(context, (ViewGroup) view.findViewById(R.id.button_container), null, onClickListener);
        this.mPopupWidth = calculatePopupWidth();
        this.mPopupHeight = calculatePopupHeight();
        this.mMenuPopupAnimator = new MenuPopupAnimator(this.mRootView, this.mPopupWidth, this.mPopupHeight);
    }
}
