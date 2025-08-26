package com.samsung.android.globalactions.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Insets;
import android.view.WindowInsets;
import android.view.WindowManager;
import com.android.internal.R;

/* loaded from: classes6.dex */
public class WindowManagerUtils {
    static final int NAVIGATIONBAR_BOTTOM = 3;
    static final int NAVIGATIONBAR_LEFT = 1;
    static final int NAVIGATIONBAR_RIGHT = 2;
    private static final String TAG = "WindowManagerUtils";
    private Context mContext;
    private LogWrapper mLogWrapper;

    public WindowManagerUtils(Context context, LogWrapper logWrapper) {
        this.mContext = context;
        this.mLogWrapper = logWrapper;
    }

    private Insets getWindowInsets(Context context, int i) {
        return ((WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE)).getCurrentWindowMetrics().getWindowInsets().getInsetsIgnoringVisibility(i);
    }

    public int getNavBarPosition() throws Resources.NotFoundException {
        int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.navigation_bar_height);
        Insets windowInsets = getWindowInsets(this.mContext, WindowInsets.Type.navigationBars());
        if (windowInsets.left >= dimensionPixelSize) {
            return 1;
        }
        return windowInsets.right >= dimensionPixelSize ? 2 : 3;
    }
}
