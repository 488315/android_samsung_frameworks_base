package com.android.systemui.navigationbar;

import android.content.Context;
import android.hardware.input.InputManager;
import android.os.SystemClock;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import com.android.systemui.CoreStartable;
import com.android.systemui.R;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class SafeUINavigationBar implements CoreStartable {
    public final Context mContext;
    public final WindowManager mWindowManager;

    public SafeUINavigationBar(Context context, WindowManager windowManager) {
        this.mContext = context;
        this.mWindowManager = windowManager;
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        Log.d("SafeUINavigationBar", NetworkAnalyticsConstants.DataPoints.OPEN_TIME);
        final SafeUINavigationBarView safeUINavigationBarView = new SafeUINavigationBarView(this.mContext, this.mWindowManager);
        View inflate = LayoutInflater.from(safeUINavigationBarView.mContext).inflate(R.layout.safe_mode_navigation_bar, (ViewGroup) null);
        safeUINavigationBarView.mView = inflate;
        inflate.requireViewById(R.id.prev_btn_area).setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.navigationbar.SafeUINavigationBarView$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SafeUINavigationBarView.this.getClass();
                long uptimeMillis = SystemClock.uptimeMillis();
                InputManager.getInstance().injectInputEvent(new KeyEvent(uptimeMillis, uptimeMillis, 0, 4, 0, 0, -1, 0, 72, 257), 0);
                long uptimeMillis2 = SystemClock.uptimeMillis();
                InputManager.getInstance().injectInputEvent(new KeyEvent(uptimeMillis2, uptimeMillis2, 1, 4, 0, 0, -1, 0, 72, 257), 0);
            }
        });
        try {
            WindowManager windowManager = safeUINavigationBarView.mWindowManager;
            View view = safeUINavigationBarView.mView;
            safeUINavigationBarView.mContext.getResources().getConfiguration().windowConfiguration.getRotation();
            WindowManager.LayoutParams barLayoutParamsForRotation = safeUINavigationBarView.getBarLayoutParamsForRotation();
            barLayoutParamsForRotation.paramsForRotation = new WindowManager.LayoutParams[4];
            for (int i = 0; i <= 3; i++) {
                barLayoutParamsForRotation.paramsForRotation[i] = safeUINavigationBarView.getBarLayoutParamsForRotation();
            }
            windowManager.addView(view, barLayoutParamsForRotation);
        } catch (RuntimeException e) {
            Log.e("SafeUINavigationBarView", "NavigationBar addView Exception :", e);
        }
    }
}
