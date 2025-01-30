package com.android.p038wm.shell.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.LinearLayout;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class DnDSnackBarWindow extends LinearLayout {
    public static final /* synthetic */ int $r8$clinit = 0;
    public SnackBarCallbacks mCallbacks;
    public WindowManager.LayoutParams mLp;
    public int mMarginBottom;
    public WindowManager mWindowManager;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface SnackBarCallbacks {
    }

    public DnDSnackBarWindow(Context context) {
        super(context);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final boolean dispatchKeyEvent(KeyEvent keyEvent) {
        hide();
        return true;
    }

    public final void hide() {
        DnDSnackBarController dnDSnackBarController = (DnDSnackBarController) this.mCallbacks;
        dnDSnackBarController.mWasShownSnackBar = true;
        SharedPreferences.Editor edit = dnDSnackBarController.mSnackBarPref.edit();
        edit.putBoolean("snack_bar_shown", dnDSnackBarController.mWasShownSnackBar);
        edit.apply();
        this.mWindowManager.removeViewImmediate(this);
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mWindowManager = (WindowManager) ((LinearLayout) this).mContext.getSystemService("window");
        this.mMarginBottom = (int) ((LinearLayout) this).mContext.getResources().getDimension(R.dimen.snack_bar_margin_bottom);
    }

    public DnDSnackBarWindow(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }
}
