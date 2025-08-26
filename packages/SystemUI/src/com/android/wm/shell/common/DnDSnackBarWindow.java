package com.android.wm.shell.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.LinearLayout;
import com.android.systemui.R;

/* loaded from: classes3.dex */
public class DnDSnackBarWindow extends LinearLayout {
    public static final /* synthetic */ int $r8$clinit = 0;
    public DnDSnackBarController mCallbacks;
    public WindowManager.LayoutParams mLp;
    public int mMarginBottom;
    public WindowManager mWindowManager;

    public DnDSnackBarWindow(Context context) {
        super(context);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final boolean dispatchKeyEvent(KeyEvent keyEvent) {
        hide();
        return true;
    }

    public final void hide() {
        DnDSnackBarController dnDSnackBarController = this.mCallbacks;
        dnDSnackBarController.mWasShownSnackBar = true;
        SharedPreferences.Editor editorEdit = dnDSnackBarController.mSnackBarPref.edit();
        editorEdit.putBoolean("snack_bar_shown", dnDSnackBarController.mWasShownSnackBar);
        editorEdit.apply();
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
