package com.android.server.accessibility;

import android.R;
import android.content.Context;
import android.os.Handler;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class SamsungAccessibilityTappingUI {
    public Context mContext;
    public final Display mDisplay;
    public final ImageView mIgnoreImageView;
    public final WindowManager.LayoutParams mParams;
    public final ImageView mProgressImageView;
    public final ImageView mStandbyImageView;
    public final ImageView mTapImageView;
    public final WindowManager mWindowManager;
    public final int navigationBarHeight;
    public final int size;
    public final View view;
    public final Handler mHandler = new Handler();
    public final Thread mUiThread = Thread.currentThread();

    public SamsungAccessibilityTappingUI(Context context) {
        this.navigationBarHeight = 0;
        this.mContext = context;
        if (context != null) {
            this.size =
                    context.getResources()
                            .getDimensionPixelSize(R.dimen.config_inCallNotificationVolume);
            float f = this.mContext.getDisplay().getDisplayId() == 2 ? 0.5f : 1.0f;
            WindowManager windowManager = (WindowManager) this.mContext.getSystemService("window");
            this.mWindowManager = windowManager;
            this.mDisplay = windowManager.getDefaultDisplay();
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            this.mParams = layoutParams;
            layoutParams.width = -2;
            layoutParams.height = -2;
            layoutParams.type = 2006;
            layoutParams.flags = 1800;
            layoutParams.format = -3;
            layoutParams.gravity = 51;
            layoutParams.samsungFlags |= 131072;
            layoutParams.layoutInDisplayCutoutMode = 1;
            View inflate =
                    LayoutInflater.from(this.mContext)
                            .inflate(R.layout.decor_caption, (ViewGroup) null);
            this.view = inflate;
            inflate.bringToFront();
            this.view.invalidate();
            this.view.setVisibility(8);
            this.mStandbyImageView = (ImageView) this.view.findViewById(16909867);
            this.mProgressImageView = (ImageView) this.view.findViewById(16909865);
            this.mIgnoreImageView = (ImageView) this.view.findViewById(16909861);
            this.mTapImageView = (ImageView) this.view.findViewById(16909863);
            this.mStandbyImageView.setVisibility(8);
            this.mProgressImageView.setVisibility(8);
            this.mIgnoreImageView.setVisibility(0);
            this.mTapImageView.setVisibility(0);
            this.mIgnoreImageView.setScaleX(f);
            this.mIgnoreImageView.setScaleY(f);
            this.mTapImageView.setScaleX(f);
            this.mTapImageView.setScaleY(f);
            this.mWindowManager.addView(this.view, this.mParams);
            this.navigationBarHeight =
                    this.mContext.getResources().getBoolean(R.bool.config_sms_decode_gsm_8bit_data)
                            ? this.mContext
                                    .getResources()
                                    .getDimensionPixelSize(R.dimen.resolver_max_collapsed_height)
                            : 0;
        }
    }

    public final void finalize() {
        super.finalize();
    }

    public final void runOnUiThread(Runnable runnable) {
        if (Thread.currentThread() != this.mUiThread) {
            this.mHandler.post(runnable);
        } else {
            runnable.run();
        }
    }
}
