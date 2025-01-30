package com.android.systemui.biometrics;

import com.android.systemui.shade.ShadeExpansionChangeEvent;
import com.android.systemui.shade.ShadeExpansionListener;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class UdfpsAnimationViewController$shadeExpansionListener$1 implements ShadeExpansionListener {
    public final /* synthetic */ UdfpsAnimationView $view;
    public final /* synthetic */ UdfpsAnimationViewController this$0;

    public UdfpsAnimationViewController$shadeExpansionListener$1(UdfpsAnimationViewController udfpsAnimationViewController, UdfpsAnimationView udfpsAnimationView) {
        this.this$0 = udfpsAnimationViewController;
        this.$view = udfpsAnimationView;
    }

    @Override // com.android.systemui.shade.ShadeExpansionListener
    public final void onPanelExpansionChanged(ShadeExpansionChangeEvent shadeExpansionChangeEvent) {
        boolean z = shadeExpansionChangeEvent.expanded;
        float f = shadeExpansionChangeEvent.fraction;
        boolean z2 = z && f > 0.0f;
        UdfpsAnimationViewController udfpsAnimationViewController = this.this$0;
        udfpsAnimationViewController.notificationShadeVisible = z2;
        UdfpsAnimationView udfpsAnimationView = this.$view;
        udfpsAnimationView.mNotificationShadeExpansion = f;
        udfpsAnimationView.updateAlpha();
        udfpsAnimationViewController.updatePauseAuth();
    }
}
