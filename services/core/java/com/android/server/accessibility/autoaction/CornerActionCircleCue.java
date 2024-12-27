package com.android.server.accessibility.autoaction;

import android.R;
import android.content.Context;
import android.os.Handler;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class CornerActionCircleCue {
    public Context mContext;
    public final Display mDisplay;
    public final ImageView mDragPointImageView;
    public long mDurationTime;
    public final int mNavigationBarHeight;
    public final WindowManager.LayoutParams mParams;
    public final ImageView mProgressImageView;
    public final float mScale;
    public final ImageView mStandbyImageView;
    public final View mView;
    public final int mWidth;
    public final WindowManager mWindowManager;
    public final Handler mHandler = new Handler();
    public final Thread mUiThread = Thread.currentThread();

    public CornerActionCircleCue(Context context, int i) {
        this.mNavigationBarHeight = 0;
        this.mContext = context;
        if (context != null) {
            float f = context.getDisplay().getDisplayId() == 2 ? 0.5f : 1.0f;
            this.mScale = f;
            this.mDurationTime = 0L;
            WindowManager windowManager = (WindowManager) this.mContext.getSystemService("window");
            this.mWindowManager = windowManager;
            this.mDisplay = windowManager.getDefaultDisplay();
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            this.mParams = layoutParams;
            layoutParams.width = -2;
            layoutParams.height = -2;
            layoutParams.type = 2024;
            layoutParams.flags = 1816;
            layoutParams.format = -3;
            layoutParams.gravity = 51;
            layoutParams.samsungFlags |= 131072;
            layoutParams.layoutInDisplayCutoutMode = 1;
            View inflate =
                    LayoutInflater.from(this.mContext)
                            .inflate(R.layout.decor_caption, (ViewGroup) null);
            this.mView = inflate;
            inflate.bringToFront();
            this.mView.invalidate();
            this.mView.setVisibility(8);
            ImageView imageView = (ImageView) this.mView.findViewById(16909861);
            ImageView imageView2 = (ImageView) this.mView.findViewById(16909863);
            imageView.setVisibility(8);
            imageView2.setVisibility(8);
            this.mStandbyImageView = (ImageView) this.mView.findViewById(16909867);
            this.mProgressImageView = (ImageView) this.mView.findViewById(16909865);
            this.mDragPointImageView = (ImageView) this.mView.findViewById(16909864);
            this.mStandbyImageView.setScaleX(f);
            this.mStandbyImageView.setScaleY(f);
            this.mProgressImageView.setScaleX(FullScreenMagnificationGestureHandler.MAX_SCALE);
            this.mProgressImageView.setScaleY(FullScreenMagnificationGestureHandler.MAX_SCALE);
            if (i == 0) {
                this.mStandbyImageView.setVisibility(0);
                this.mProgressImageView.setVisibility(0);
                this.mDragPointImageView.setVisibility(8);
            } else if (i == 1) {
                this.mStandbyImageView.setVisibility(8);
                this.mProgressImageView.setVisibility(8);
                this.mDragPointImageView.setVisibility(0);
            }
            this.mView.measure(0, 0);
            this.mWidth = this.mView.getMeasuredWidth();
            this.mWindowManager.addView(this.mView, this.mParams);
            this.mNavigationBarHeight =
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
