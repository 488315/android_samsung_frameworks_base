package com.android.systemui.dreams;

import android.animation.Animator;
import android.view.View;
import com.android.systemui.ambient.statusbar.ui.AmbientStatusBarViewController;
import com.android.systemui.complication.ComplicationHostViewController;
import com.android.systemui.dreams.ui.viewmodel.DreamViewModel;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.Logger;
import com.android.systemui.statusbar.BlurUtils;
import com.android.systemui.statusbar.CrossFadeHelper;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class DreamOverlayAnimationsController {
    public final DreamViewModel dreamViewModel;
    public final Logger logger;
    public Animator mAnimator;
    public final BlurUtils mBlurUtils;
    public final ComplicationHostViewController mComplicationHostViewController;
    public final Map mCurrentAlphaAtPosition = new LinkedHashMap();
    public float mCurrentBlurRadius;
    public final int mDreamBlurRadius;
    public final long mDreamInBlurAnimDurationMs;
    public final long mDreamInComplicationsAnimDurationMs;
    public final int mDreamInTranslationYDistance;
    public final long mDreamInTranslationYDurationMs;
    public final DreamOverlayStateController mOverlayStateController;
    public final AmbientStatusBarViewController mStatusBarViewController;
    public View view;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public DreamOverlayAnimationsController(BlurUtils blurUtils, ComplicationHostViewController complicationHostViewController, AmbientStatusBarViewController ambientStatusBarViewController, DreamOverlayStateController dreamOverlayStateController, int i, DreamViewModel dreamViewModel, long j, long j2, int i2, long j3, LogBuffer logBuffer) {
        this.mBlurUtils = blurUtils;
        this.mComplicationHostViewController = complicationHostViewController;
        this.mStatusBarViewController = ambientStatusBarViewController;
        this.mOverlayStateController = dreamOverlayStateController;
        this.mDreamBlurRadius = i;
        this.dreamViewModel = dreamViewModel;
        this.mDreamInBlurAnimDurationMs = j;
        this.mDreamInComplicationsAnimDurationMs = j2;
        this.mDreamInTranslationYDistance = i2;
        this.mDreamInTranslationYDurationMs = j3;
        this.logger = new Logger(logBuffer, "DreamOverlayAnimationsController");
    }

    public static final void access$setElementsAlphaAtPosition(DreamOverlayAnimationsController dreamOverlayAnimationsController, float f, int i, boolean z) {
        dreamOverlayAnimationsController.mCurrentAlphaAtPosition.put(Integer.valueOf(i), Float.valueOf(f));
        for (View view : dreamOverlayAnimationsController.mComplicationHostViewController.getViewsAtPosition(i)) {
            if (z) {
                CrossFadeHelper.fadeOut(view, 1 - f, false);
            } else {
                CrossFadeHelper.fadeIn(view, f, false);
            }
        }
        if (i == 1) {
            dreamOverlayAnimationsController.mStatusBarViewController.setFadeAmount(f, z);
        }
    }

    public static final void access$setElementsTranslationYAtPosition(DreamOverlayAnimationsController dreamOverlayAnimationsController, float f, int i) {
        Iterator it = dreamOverlayAnimationsController.mComplicationHostViewController.getViewsAtPosition(i).iterator();
        while (it.hasNext()) {
            ((View) it.next()).setTranslationY(f);
        }
        if (i == 1) {
            dreamOverlayAnimationsController.mStatusBarViewController.setTranslationY(f);
        }
    }

    public final void cancelAnimations() {
        Animator animator = this.mAnimator;
        if (animator != null) {
            animator.cancel();
        }
        this.mAnimator = null;
        DreamOverlayStateController dreamOverlayStateController = this.mOverlayStateController;
        dreamOverlayStateController.getClass();
        dreamOverlayStateController.modifyState(1, 8);
    }
}
