package com.android.keyguard;

import android.content.Context;
import com.android.systemui.keyguard.ui.view.KeyguardRootView;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.shade.NotificationShadeWindowView;
import com.android.systemui.unfold.UnfoldTransitionProgressProvider;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;

/* loaded from: classes.dex */
public final class KeyguardUnfoldTransition {
    public final Context context;
    public final KeyguardUnfoldTransition$$ExternalSyntheticLambda1 filterKeyguard;
    public final KeyguardUnfoldTransition$$ExternalSyntheticLambda2 filterKeyguardAndSplitShadeOnly;
    public final KeyguardRootView keyguardRootView;
    public final NotificationShadeWindowView shadeWindowView;
    public final Lazy shortcutButtonsAnimator$delegate;
    public boolean statusViewCentered;
    public final Lazy translateAnimator$delegate;

    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.keyguard.KeyguardUnfoldTransition$$ExternalSyntheticLambda1] */
    public KeyguardUnfoldTransition(Context context, KeyguardRootView keyguardRootView, NotificationShadeWindowView notificationShadeWindowView, final StatusBarStateController statusBarStateController, UnfoldTransitionProgressProvider unfoldTransitionProgressProvider) {
        this.context = context;
        this.keyguardRootView = keyguardRootView;
        this.shadeWindowView = notificationShadeWindowView;
        this.filterKeyguardAndSplitShadeOnly = new KeyguardUnfoldTransition$$ExternalSyntheticLambda2(statusBarStateController, this);
        this.filterKeyguard = new Function0() { // from class: com.android.keyguard.KeyguardUnfoldTransition$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return Boolean.valueOf(statusBarStateController.getState() == 1);
            }
        };
        this.translateAnimator$delegate = LazyKt__LazyJVMKt.lazy(new KeyguardUnfoldTransition$$ExternalSyntheticLambda2(this, unfoldTransitionProgressProvider, 0));
        this.shortcutButtonsAnimator$delegate = LazyKt__LazyJVMKt.lazy(new KeyguardUnfoldTransition$$ExternalSyntheticLambda2(this, unfoldTransitionProgressProvider, 1));
    }
}
