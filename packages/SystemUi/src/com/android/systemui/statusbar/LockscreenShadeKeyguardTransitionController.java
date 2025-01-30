package com.android.systemui.statusbar;

import android.content.Context;
import android.util.IndentingPrintWriter;
import android.util.MathUtils;
import android.view.animation.PathInterpolator;
import com.android.app.animation.Interpolators;
import com.android.systemui.R;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.shade.NotificationPanelViewController;
import com.android.systemui.shade.ShadeViewController;
import com.android.systemui.statusbar.phone.KeyguardStatusBarViewController;
import com.android.systemui.statusbar.policy.ConfigurationController;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class LockscreenShadeKeyguardTransitionController extends AbstractLockscreenShadeTransitionController {
    public float alpha;
    public float alphaProgress;
    public int alphaTransitionDistance;
    public int keyguardTransitionDistance;
    public int keyguardTransitionOffset;
    public final ShadeViewController notificationPanelController;
    public float statusBarAlpha;
    public int translationY;
    public float translationYProgress;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Factory {
        LockscreenShadeKeyguardTransitionController create(ShadeViewController shadeViewController);
    }

    public LockscreenShadeKeyguardTransitionController(ShadeViewController shadeViewController, Context context, ConfigurationController configurationController, DumpManager dumpManager) {
        super(context, configurationController, dumpManager);
        this.notificationPanelController = shadeViewController;
    }

    @Override // com.android.systemui.statusbar.AbstractLockscreenShadeTransitionController
    public final void dump(IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.println("LockscreenShadeKeyguardTransitionController:");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.println("Resources:");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.println("alphaTransitionDistance: " + this.alphaTransitionDistance);
        indentingPrintWriter.println("keyguardTransitionDistance: " + this.keyguardTransitionDistance);
        indentingPrintWriter.println("keyguardTransitionOffset: " + this.keyguardTransitionOffset);
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println("State:");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.println("dragDownAmount: " + this.dragDownAmount);
        indentingPrintWriter.println("alpha: " + this.alpha);
        indentingPrintWriter.println("alphaProgress: " + this.alphaProgress);
        indentingPrintWriter.println("statusBarAlpha: " + this.statusBarAlpha);
        indentingPrintWriter.println("translationProgress: " + this.translationYProgress);
        indentingPrintWriter.println("translationY: " + this.translationY);
    }

    @Override // com.android.systemui.statusbar.AbstractLockscreenShadeTransitionController
    public final void onDragDownAmountChanged(float f) {
        int i;
        float saturate = MathUtils.saturate(f / this.alphaTransitionDistance);
        this.alphaProgress = saturate;
        this.alpha = 1.0f - saturate;
        if (this.useSplitShade) {
            float saturate2 = MathUtils.saturate(f / this.keyguardTransitionDistance);
            this.translationYProgress = saturate2;
            i = (int) (saturate2 * this.keyguardTransitionOffset);
        } else {
            i = 0;
        }
        this.translationY = i;
        float f2 = this.alpha;
        NotificationPanelViewController notificationPanelViewController = (NotificationPanelViewController) this.notificationPanelController;
        notificationPanelViewController.getClass();
        float interpolation = ((PathInterpolator) Interpolators.ALPHA_IN).getInterpolation(f2);
        notificationPanelViewController.mKeyguardOnlyContentAlpha = interpolation;
        notificationPanelViewController.mKeyguardOnlyTransitionTranslationY = i;
        if (notificationPanelViewController.mBarState == 1) {
            notificationPanelViewController.mBottomAreaShadeAlpha = interpolation;
            notificationPanelViewController.updateKeyguardBottomAreaAlpha();
        }
        notificationPanelViewController.updateClock();
        float f3 = this.useSplitShade ? this.alpha : -1.0f;
        this.statusBarAlpha = f3;
        KeyguardStatusBarViewController keyguardStatusBarViewController = notificationPanelViewController.mKeyguardStatusBarViewController;
        keyguardStatusBarViewController.mExplicitAlpha = f3;
        keyguardStatusBarViewController.updateViewState();
    }

    @Override // com.android.systemui.statusbar.AbstractLockscreenShadeTransitionController
    public final void updateResources() {
        Context context = this.context;
        this.alphaTransitionDistance = context.getResources().getDimensionPixelSize(R.dimen.lockscreen_shade_npvc_keyguard_content_alpha_transition_distance);
        this.keyguardTransitionDistance = context.getResources().getDimensionPixelSize(R.dimen.lockscreen_shade_keyguard_transition_distance);
        this.keyguardTransitionOffset = context.getResources().getDimensionPixelSize(R.dimen.lockscreen_shade_keyguard_transition_vertical_offset);
    }
}
