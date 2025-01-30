package com.android.systemui.statusbar.policy;

import android.content.Context;
import android.util.AttributeSet;
import com.android.app.animation.Interpolators;
import com.android.keyguard.AlphaOptimizedLinearLayout;
import com.android.settingslib.animation.AppearAnimationUtils;
import com.android.settingslib.animation.DisappearAnimationUtils;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class KeyguardUserSwitcherListView extends AlphaOptimizedLinearLayout {
    public static final /* synthetic */ int $r8$clinit = 0;
    public boolean mAnimating;
    public final AppearAnimationUtils mAppearAnimationUtils;
    public final DisappearAnimationUtils mDisappearAnimationUtils;

    public KeyguardUserSwitcherListView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mAppearAnimationUtils = new AppearAnimationUtils(context, 220L, -0.5f, 0.5f, Interpolators.FAST_OUT_SLOW_IN);
        this.mDisappearAnimationUtils = new DisappearAnimationUtils(context, 220L, 0.2f, 0.2f, Interpolators.FAST_OUT_SLOW_IN_REVERSE);
    }
}
