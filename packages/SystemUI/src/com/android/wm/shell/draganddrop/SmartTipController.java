package com.android.wm.shell.draganddrop;

import android.content.Context;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import com.android.systemui.R;
import com.samsung.android.widget.SemTipPopup;

/* loaded from: classes3.dex */
public class SmartTipController {
    public final Context mContext;
    public final Rect mDisplayBounds = new Rect();
    public int mGapWithContent;
    public final SmartTip mHelpTip;
    public int mInitialX;
    public boolean mShown;
    public int mSurfaceHeight;

    public SmartTipController(Context context) {
        this.mContext = context;
        this.mHelpTip = new SmartTip(context, "ctwHelpTip", "CtwSmartTipPopup", "helpTipCount", R.string.drag_and_split_help_tip_msg, R.integer.drag_and_split_help_tip_limit_count, R.layout.drag_and_split_help_tip_view);
    }

    public final void dismissHelpTipIfPossible() {
        this.mShown = false;
        SmartTip smartTip = this.mHelpTip;
        if (smartTip.mShowRequested) {
            SemTipPopup semTipPopup = smartTip.mTipPopup;
            if (semTipPopup != null) {
                semTipPopup.dismiss(true);
                smartTip.mTipPopup = null;
            }
            Log.d("SmartTip" + smartTip.mTitle, "removeView: mView=" + smartTip.mRootView);
            View view = smartTip.mRootView;
            if (view != null) {
                smartTip.mWindowManager.removeView(view);
                smartTip.mRootView = null;
            }
            smartTip.mShowRequested = false;
        }
    }
}
