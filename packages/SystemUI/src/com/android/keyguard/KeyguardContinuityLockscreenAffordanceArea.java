package com.android.keyguard;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import com.airbnb.lottie.LottieAnimationView;
import com.android.systemui.R;
import com.android.systemui.wallpaper.WallpaperUtils;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class KeyguardContinuityLockscreenAffordanceArea extends LinearLayout {
    public LottieAnimationView mContinuityLottieView;

    public KeyguardContinuityLockscreenAffordanceArea(Context context) {
        this(context, null);
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mContinuityLottieView = (LottieAnimationView) findViewById(R.id.keyguard_indication_continuity_vi_animation_view);
        if (WallpaperUtils.isWhiteKeyguardWallpaper("bottom")) {
            this.mContinuityLottieView.setAnimation("lockscreenAffordance_light.json");
        } else {
            this.mContinuityLottieView.setAnimation("lockscreenAffordance_dark.json");
        }
    }

    @Override // android.view.View
    public final void setVisibility(int i) {
        super.setVisibility(i);
        if (i == 0) {
            if (WallpaperUtils.isWhiteKeyguardWallpaper("bottom")) {
                this.mContinuityLottieView.setAnimation("lockscreenAffordance_light.json");
            } else {
                this.mContinuityLottieView.setAnimation("lockscreenAffordance_dark.json");
            }
        }
    }

    public KeyguardContinuityLockscreenAffordanceArea(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }
}
