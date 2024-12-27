package com.android.systemui.keyguard.animator;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.util.Log;
import android.view.View;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.LsRune;
import com.android.systemui.keyguard.KeyguardEditModeControllerImpl;
import com.android.systemui.util.SystemUIAnalytics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public final class FullScreenViewController extends ViewAnimationController {
    public AnimatorSet fullScreenAnimatorSet;
    public final List fullScreenViews;
    public boolean isFullScreenModeShown;
    public boolean isFullscreenModeEnabled;
    public final FullScreenViewController$longPressCallback$1 longPressCallback;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public abstract class CustomAnimatorListenerAdapter extends AnimatorListenerAdapter {
        public boolean isCancelled;

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public final void onAnimationCancel(Animator animator) {
            this.isCancelled = true;
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public final void onAnimationStart(Animator animator) {
            this.isCancelled = false;
        }
    }

    static {
        new Companion(null);
    }

    public FullScreenViewController(KeyguardTouchAnimator keyguardTouchAnimator) {
        super(keyguardTouchAnimator);
        this.fullScreenViews = CollectionsKt__CollectionsKt.listOf(1, 2, 3, 4, 5, 6, 0, 7, 8, 9, 10, 12);
        this.longPressCallback = new Runnable() { // from class: com.android.systemui.keyguard.animator.FullScreenViewController$longPressCallback$1
            @Override // java.lang.Runnable
            public final void run() {
                FullScreenViewController.this.setFullScreenMode$frameworks__base__packages__SystemUI__android_common__SystemUI_core(true);
            }
        };
        this.fullScreenAnimatorSet = new AnimatorSet();
    }

    public final void setFullScreenMode$frameworks__base__packages__SystemUI__android_common__SystemUI_core(final boolean z) {
        this.isFullscreenModeEnabled = z;
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("setFullScreenMode enabled=", "KeyguardTouchAnimator", z);
        if (this.fullScreenAnimatorSet.isRunning()) {
            this.fullScreenAnimatorSet.cancel();
        }
        if (isKeyguardState()) {
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.setInterpolator(z ? this.SINE_OUT_33 : this.SINE_IN_33);
            animatorSet.setDuration(z ? 150L : 300L);
            this.fullScreenAnimatorSet = animatorSet;
            animatorSet.addListener(new CustomAnimatorListenerAdapter() { // from class: com.android.systemui.keyguard.animator.FullScreenViewController$setFullScreenMode$1$1
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    if (!z || this.isCancelled) {
                        return;
                    }
                    this.isFullScreenModeShown = true;
                }
            });
            float f = z ? 0.95f : 1.0f;
            float f2 = z ? 0.0f : 1.0f;
            KeyguardTouchAnimator keyguardTouchAnimator = this.keyguardTouchAnimator;
            View view$frameworks__base__packages__SystemUI__android_common__SystemUI_core = keyguardTouchAnimator.hasView$frameworks__base__packages__SystemUI__android_common__SystemUI_core(0) ? keyguardTouchAnimator.getView$frameworks__base__packages__SystemUI__android_common__SystemUI_core(0) : null;
            List list = this.fullScreenViews;
            ArrayList arrayList = new ArrayList();
            for (Object obj : list) {
                if (keyguardTouchAnimator.hasView$frameworks__base__packages__SystemUI__android_common__SystemUI_core(((Number) obj).intValue())) {
                    arrayList.add(obj);
                }
            }
            ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList, 10));
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                arrayList2.add(keyguardTouchAnimator.getView$frameworks__base__packages__SystemUI__android_common__SystemUI_core(((Number) it.next()).intValue()));
            }
            ArrayList arrayList3 = new ArrayList();
            Iterator it2 = arrayList2.iterator();
            while (it2.hasNext()) {
                Object next = it2.next();
                if (((View) next).getVisibility() == 0) {
                    arrayList3.add(next);
                }
            }
            Iterator it3 = arrayList3.iterator();
            while (it3.hasNext()) {
                View view = (View) it3.next();
                setViewAnimation(animatorSet, view, (Intrinsics.areEqual(view$frameworks__base__packages__SystemUI__android_common__SystemUI_core, view) && z) ? -1.0f : f, f2);
            }
            animatorSet.start();
            keyguardTouchAnimator.loggingInjector.getClass();
            if (z) {
                System.currentTimeMillis();
            } else {
                SystemUIAnalytics.sendEventLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_VIEW_WALLPAPAER_ONLY);
            }
            KeyguardTouchSecurityInjector keyguardTouchSecurityInjector = keyguardTouchAnimator.securityInjector;
            keyguardTouchSecurityInjector.getClass();
            if (LsRune.SECURITY_FINGERPRINT_IN_DISPLAY) {
                KeyguardUpdateMonitor keyguardUpdateMonitor = keyguardTouchSecurityInjector.mKeyguardUpdateMonitor;
                if (keyguardUpdateMonitor.isFingerprintOptionEnabled()) {
                    if (z) {
                        Log.d("KeyguardFingerprint", "onFullScreenModeChanged is true. FP will be stop!");
                        keyguardUpdateMonitor.stopListeningForFingerprint();
                    } else {
                        Log.d("KeyguardFingerprint", "onFullScreenModeChanged is false. FP will be start!");
                        keyguardUpdateMonitor.updateFingerprintListeningState(2);
                    }
                }
            }
            ((KeyguardEditModeControllerImpl) keyguardTouchAnimator.keyguardEditModeController).onFullscreenModeChanged(keyguardTouchAnimator.getParentView$frameworks__base__packages__SystemUI__android_common__SystemUI_core().getContext(), this.isFullscreenModeEnabled);
        }
    }
}
