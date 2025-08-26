package com.android.wm.shell.controlpanel.activity;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import com.android.systemui.R;
import com.android.wm.shell.controlpanel.utils.ControlPanelUtils;
import com.samsung.android.widget.SemTipPopup;

/* loaded from: classes3.dex */
public class ToolbarTipPopup {
    public final Context mContext;
    public final WindowManager.LayoutParams mTipPopUpWindowLp;
    public SemTipPopup mTipPopup;
    public FrameLayout mView;
    public final WindowManager mWindowManager;

    public ToolbarTipPopup(Context context) {
        this.mContext = context;
        WindowManager windowManager = (WindowManager) context.getSystemService("window");
        this.mWindowManager = windowManager;
        this.mView = (FrameLayout) LayoutInflater.from(context).inflate(R.layout.toolbar_tip_popup_view, (ViewGroup) null);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-1, -1, 2008, 16777496, -3);
        this.mTipPopUpWindowLp = layoutParams;
        layoutParams.setTitle("ToolBarTipPopup");
        Log.d("ToolBarTipPopup", "addView: mView=" + this.mView);
        windowManager.addView(this.mView, this.mTipPopUpWindowLp);
        if (this.mTipPopup == null) {
            this.mTipPopup = new SemTipPopup(this.mView);
        }
    }

    public final void requestShowPopUp(final int i, final int i2) {
        SemTipPopup semTipPopup = this.mTipPopup;
        if (semTipPopup == null || semTipPopup.isShowing()) {
            return;
        }
        final boolean z = true;
        final boolean z2 = true;
        if (this.mView.isAttachedToWindow()) {
            showTipPopUp(i, i2, true, true);
        } else {
            this.mView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: com.android.wm.shell.controlpanel.activity.ToolbarTipPopup.1
                @Override // android.view.View.OnAttachStateChangeListener
                public final void onViewAttachedToWindow(View view) {
                    ToolbarTipPopup.this.showTipPopUp(i, i2, z, z2);
                }

                @Override // android.view.View.OnAttachStateChangeListener
                public final void onViewDetachedFromWindow(View view) {
                }
            });
        }
    }

    public final void showTipPopUp(int i, int i2, boolean z, boolean z2) {
        SemTipPopup semTipPopup = this.mTipPopup;
        if (semTipPopup == null || semTipPopup.isShowing()) {
            return;
        }
        this.mTipPopup.setBackgroundColorWithAlpha(this.mContext.getColor(R.color.toolbar_tip_popup_bg_color));
        this.mTipPopup.setMessageTextColor(this.mContext.getColor(R.color.toolbar_tip_popup_text_color));
        this.mTipPopup.setExpanded(z);
        this.mTipPopup.setTargetPosition(i, i2);
        this.mTipPopup.setOnStateChangeListener(new SemTipPopup.OnStateChangeListener() { // from class: com.android.wm.shell.controlpanel.activity.ToolbarTipPopup$$ExternalSyntheticLambda0
            public final void onStateChanged(int i3) {
                ToolbarTipPopup toolbarTipPopup = this.f$0;
                if (i3 != 0) {
                    toolbarTipPopup.getClass();
                    return;
                }
                SemTipPopup semTipPopup2 = toolbarTipPopup.mTipPopup;
                if (semTipPopup2 != null) {
                    semTipPopup2.dismiss(true);
                    toolbarTipPopup.mTipPopup = null;
                }
                FrameLayout frameLayout = toolbarTipPopup.mView;
                if (frameLayout == null) {
                    return;
                }
                toolbarTipPopup.mWindowManager.removeView(frameLayout);
                toolbarTipPopup.mView = null;
            }
        });
        this.mTipPopup.setMessage(this.mContext.getString(R.string.toolbar_drag_tip));
        this.mTipPopup.setOutsideTouchEnabled(z2);
        this.mTipPopup.setPopupWindowClippingEnabled(z);
        if (ControlPanelUtils.isTypeFold()) {
            this.mTipPopup.show(3);
        } else {
            this.mTipPopup.show(0);
        }
    }
}
