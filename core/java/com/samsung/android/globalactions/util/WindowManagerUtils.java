package com.samsung.android.globalactions.util;

import android.content.Context;
import android.graphics.Insets;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.WindowMetrics;

import com.android.internal.R;

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

    private Insets getWindowInsets(Context context, int typeMask) {
        WindowManager windowManager =
                (WindowManager)
                        context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        WindowMetrics metrics = windowManager.getCurrentWindowMetrics();
        return metrics.getWindowInsets().getInsets(typeMask);
    }

    public int getNavBarPosition() {
        int navigationBarHeight =
                this.mContext.getResources().getDimensionPixelSize(R.dimen.navigation_bar_height);
        Insets insets = getWindowInsets(this.mContext, WindowInsets.Type.navigationBars());
        if (insets.left >= navigationBarHeight) {
            return 1;
        }
        if (insets.right >= navigationBarHeight) {
            return 2;
        }
        return 3;
    }
}
