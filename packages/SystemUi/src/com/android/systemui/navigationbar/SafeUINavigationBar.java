package com.android.systemui.navigationbar;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import com.android.systemui.CoreStartable;
import com.android.systemui.R;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
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
        Context context = safeUINavigationBarView.mContext;
        View inflate = LayoutInflater.from(context).inflate(R.layout.safe_mode_navigation_bar, (ViewGroup) null);
        safeUINavigationBarView.mView = inflate;
        inflate.findViewById(R.id.prev_btn_area).setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.navigationbar.SafeUINavigationBarView$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SafeUINavigationBarView.this.getClass();
                SafeUINavigationBarView.sendEvent(0);
                SafeUINavigationBarView.sendEvent(1);
            }
        });
        try {
            WindowManager windowManager = safeUINavigationBarView.mWindowManager;
            View view = safeUINavigationBarView.mView;
            context.getResources().getConfiguration().windowConfiguration.getRotation();
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
