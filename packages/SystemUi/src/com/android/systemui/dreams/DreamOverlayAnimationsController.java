package com.android.systemui.dreams;

import android.animation.Animator;
import android.view.View;
import androidx.constraintlayout.core.widgets.ConstraintWidget$$ExternalSyntheticOutline0;
import com.android.systemui.complication.ComplicationHostViewController;
import com.android.systemui.keyguard.ui.viewmodel.DreamingToLockscreenTransitionViewModel;
import com.android.systemui.statusbar.BlurUtils;
import com.android.systemui.statusbar.CrossFadeHelper;
import com.android.systemui.statusbar.policy.ConfigurationController;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class DreamOverlayAnimationsController {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ConfigurationController configController;
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
    public final DreamLogger mLogger;
    public final DreamOverlayStateController mOverlayStateController;
    public final DreamOverlayStatusBarViewController mStatusBarViewController;
    public final DreamingToLockscreenTransitionViewModel transitionViewModel;
    public View view;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ConfigurationBasedDimensions {
        public final int translationYPx;

        public ConfigurationBasedDimensions(int i) {
            this.translationYPx = i;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof ConfigurationBasedDimensions) && this.translationYPx == ((ConfigurationBasedDimensions) obj).translationYPx;
        }

        public final int hashCode() {
            return Integer.hashCode(this.translationYPx);
        }

        public final String toString() {
            return ConstraintWidget$$ExternalSyntheticOutline0.m19m(new StringBuilder("ConfigurationBasedDimensions(translationYPx="), this.translationYPx, ")");
        }
    }

    static {
        new Companion(null);
    }

    public DreamOverlayAnimationsController(BlurUtils blurUtils, ComplicationHostViewController complicationHostViewController, DreamOverlayStatusBarViewController dreamOverlayStatusBarViewController, DreamOverlayStateController dreamOverlayStateController, int i, DreamingToLockscreenTransitionViewModel dreamingToLockscreenTransitionViewModel, ConfigurationController configurationController, long j, long j2, int i2, long j3, DreamLogger dreamLogger) {
        this.mBlurUtils = blurUtils;
        this.mComplicationHostViewController = complicationHostViewController;
        this.mStatusBarViewController = dreamOverlayStatusBarViewController;
        this.mOverlayStateController = dreamOverlayStateController;
        this.mDreamBlurRadius = i;
        this.transitionViewModel = dreamingToLockscreenTransitionViewModel;
        this.configController = configurationController;
        this.mDreamInBlurAnimDurationMs = j;
        this.mDreamInComplicationsAnimDurationMs = j2;
        this.mDreamInTranslationYDistance = i2;
        this.mDreamInTranslationYDurationMs = j3;
        this.mLogger = dreamLogger;
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
            DreamOverlayStatusBarViewController dreamOverlayStatusBarViewController = dreamOverlayAnimationsController.mStatusBarViewController;
            dreamOverlayStatusBarViewController.updateVisibility();
            if (((DreamOverlayStatusBarView) dreamOverlayStatusBarViewController.mView).getVisibility() != 0) {
                return;
            }
            if (z) {
                CrossFadeHelper.fadeOut(dreamOverlayStatusBarViewController.mView, 1.0f - f, false);
            } else {
                CrossFadeHelper.fadeIn(dreamOverlayStatusBarViewController.mView, f, false);
            }
        }
    }

    public static final void access$setElementsTranslationYAtPosition(DreamOverlayAnimationsController dreamOverlayAnimationsController, float f, int i) {
        Iterator it = dreamOverlayAnimationsController.mComplicationHostViewController.getViewsAtPosition(i).iterator();
        while (it.hasNext()) {
            ((View) it.next()).setTranslationY(f);
        }
        if (i == 1) {
            ((DreamOverlayStatusBarView) dreamOverlayAnimationsController.mStatusBarViewController.mView).setTranslationY(f);
        }
    }
}
