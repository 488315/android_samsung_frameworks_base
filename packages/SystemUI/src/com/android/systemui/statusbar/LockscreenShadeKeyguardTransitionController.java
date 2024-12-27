package com.android.systemui.statusbar;

import android.content.Context;
import android.util.IndentingPrintWriter;
import com.android.systemui.R;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.media.controls.ui.controller.MediaHierarchyManager;
import com.android.systemui.shade.domain.interactor.ShadeLockscreenInteractor;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.SplitShadeStateController;

public final class LockscreenShadeKeyguardTransitionController extends AbstractLockscreenShadeTransitionController {
    public float alpha;
    public float alphaProgress;
    public int alphaTransitionDistance;
    public int keyguardTransitionDistance;
    public int keyguardTransitionOffset;
    public final ShadeLockscreenInteractor shadeLockscreenInteractor;
    public float statusBarAlpha;

    public interface Factory {
        LockscreenShadeKeyguardTransitionController create(ShadeLockscreenInteractor shadeLockscreenInteractor);
    }

    public LockscreenShadeKeyguardTransitionController(MediaHierarchyManager mediaHierarchyManager, ShadeLockscreenInteractor shadeLockscreenInteractor, Context context, ConfigurationController configurationController, DumpManager dumpManager, SplitShadeStateController splitShadeStateController) {
        super(context, configurationController, dumpManager, splitShadeStateController);
        this.shadeLockscreenInteractor = shadeLockscreenInteractor;
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
        indentingPrintWriter.println("translationProgress: 0.0");
        indentingPrintWriter.println("translationY: 0");
    }

    @Override // com.android.systemui.statusbar.AbstractLockscreenShadeTransitionController
    public final void updateResources$1() {
        this.alphaTransitionDistance = this.context.getResources().getDimensionPixelSize(R.dimen.lockscreen_shade_npvc_keyguard_content_alpha_transition_distance);
        this.keyguardTransitionDistance = this.context.getResources().getDimensionPixelSize(R.dimen.lockscreen_shade_keyguard_transition_distance);
        this.keyguardTransitionOffset = this.context.getResources().getDimensionPixelSize(R.dimen.lockscreen_shade_keyguard_transition_vertical_offset);
    }
}
