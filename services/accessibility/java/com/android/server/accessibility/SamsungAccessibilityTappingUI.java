package com.android.server.accessibility;

import android.R;
import android.content.Context;
import android.os.Handler;
import android.p005os.IInstalld;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import com.android.server.display.DisplayPowerController2;

/* loaded from: classes.dex */
public class SamsungAccessibilityTappingUI {
    public long durationTime;
    public Context mContext;
    public Display mDisplay;
    public ImageView mIgnoreImageView;
    public WindowManager.LayoutParams mParams;
    public ImageView mProgressImageView;
    public ImageView mStandbyImageView;
    public ImageView mTapImageView;
    public WindowManager mWindowManager;
    public int navigationBarHeight;
    public float scale;
    public int size;
    public int tappingType;
    public View view;
    public final Handler mHandler = new Handler();
    public final Thread mUiThread = Thread.currentThread();

    public SamsungAccessibilityTappingUI(Context context, int i) {
        this.navigationBarHeight = 0;
        this.mContext = context;
        if (context != null) {
            this.tappingType = i;
            this.size = context.getResources().getDimensionPixelSize(R.dimen.chooser_max_collapsed_height);
            this.durationTime = 0L;
            this.scale = this.mContext.getDisplay().getDisplayId() == 2 ? 0.5f : 1.0f;
            makeView();
            this.navigationBarHeight = getNavigationBarHeight();
        }
    }

    public void finalize() {
        super.finalize();
    }

    public final void makeView() {
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
        layoutParams.samsungFlags |= IInstalld.FLAG_CLEAR_APP_DATA_KEEP_ART_PROFILES;
        layoutParams.layoutInDisplayCutoutMode = 1;
        View inflate = LayoutInflater.from(this.mContext).inflate(R.layout.date_picker_view_animator_material, (ViewGroup) null);
        this.view = inflate;
        inflate.bringToFront();
        this.view.invalidate();
        this.view.setVisibility(8);
        this.mStandbyImageView = (ImageView) this.view.findViewById(16909855);
        this.mProgressImageView = (ImageView) this.view.findViewById(16909853);
        this.mIgnoreImageView = (ImageView) this.view.findViewById(16909849);
        this.mTapImageView = (ImageView) this.view.findViewById(16909851);
        int i = this.tappingType;
        if (i == 0) {
            this.mStandbyImageView.setVisibility(0);
            this.mProgressImageView.setVisibility(0);
            this.mIgnoreImageView.setVisibility(8);
            this.mTapImageView.setVisibility(8);
            this.mStandbyImageView.setScaleX(this.scale);
            this.mStandbyImageView.setScaleY(this.scale);
            this.mProgressImageView.setScaleX(DisplayPowerController2.RATE_FROM_DOZE_TO_ON);
            this.mProgressImageView.setScaleY(DisplayPowerController2.RATE_FROM_DOZE_TO_ON);
        } else if (i == 1) {
            this.mStandbyImageView.setVisibility(8);
            this.mProgressImageView.setVisibility(8);
            this.mIgnoreImageView.setVisibility(0);
            this.mTapImageView.setVisibility(0);
            this.mIgnoreImageView.setScaleX(this.scale);
            this.mIgnoreImageView.setScaleY(this.scale);
            this.mTapImageView.setScaleX(this.scale);
            this.mTapImageView.setScaleY(this.scale);
        }
        this.mWindowManager.addView(this.view, this.mParams);
    }

    public void updateView(float f, float f2) {
        if (this.mDisplay.getRotation() == 3) {
            this.mParams.x = (((int) f) - (this.size / 2)) - this.navigationBarHeight;
        } else {
            this.mParams.x = ((int) f) - (this.size / 2);
        }
        this.mParams.y = ((int) f2) - (this.size / 2);
        runOnUiThread(new Runnable() { // from class: com.android.server.accessibility.SamsungAccessibilityTappingUI$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                SamsungAccessibilityTappingUI.this.lambda$updateView$0();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateView$0() {
        this.mWindowManager.updateViewLayout(this.view, this.mParams);
    }

    public void setIgnoreView(final boolean z) {
        runOnUiThread(new Runnable() { // from class: com.android.server.accessibility.SamsungAccessibilityTappingUI$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                SamsungAccessibilityTappingUI.this.lambda$setIgnoreView$1(z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setIgnoreView$1(boolean z) {
        if (z) {
            this.mTapImageView.setVisibility(8);
            this.mIgnoreImageView.setVisibility(0);
        } else {
            this.mTapImageView.setVisibility(0);
            this.mIgnoreImageView.setVisibility(8);
        }
    }

    public void setViewOnOff(final boolean z) {
        runOnUiThread(new Runnable() { // from class: com.android.server.accessibility.SamsungAccessibilityTappingUI$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                SamsungAccessibilityTappingUI.this.lambda$setViewOnOff$2(z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setViewOnOff$2(boolean z) {
        if (z) {
            this.view.setVisibility(0);
        } else {
            this.view.setVisibility(8);
        }
    }

    public void destroy() {
        this.mContext = null;
        this.mWindowManager.removeView(this.view);
        this.mStandbyImageView.setImageBitmap(null);
        this.mProgressImageView.setImageBitmap(null);
        this.mIgnoreImageView.setImageBitmap(null);
        this.mTapImageView.setImageBitmap(null);
    }

    public final int getNavigationBarHeight() {
        if (this.mContext.getResources().getBoolean(R.bool.config_predictShowStartingSurface)) {
            return this.mContext.getResources().getDimensionPixelSize(R.dimen.notification_custom_view_max_image_height_low_ram);
        }
        return 0;
    }

    public final void runOnUiThread(Runnable runnable) {
        if (Thread.currentThread() != this.mUiThread) {
            this.mHandler.post(runnable);
        } else {
            runnable.run();
        }
    }
}
