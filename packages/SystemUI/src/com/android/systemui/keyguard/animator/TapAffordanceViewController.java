package com.android.systemui.keyguard.animator;

import android.util.Log;
import android.view.View;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class TapAffordanceViewController extends ViewAnimationController {
    public static final /* synthetic */ int $r8$clinit = 0;
    public boolean isTapAnimationRunning;
    public final TapAffordanceViewController$restoreSpringAnimRunnable$1 restoreSpringAnimRunnable;
    public final List restoreSpringAnimationList;
    public final List tapAffordanceViews;
    public final List tapSpringAnimationList;

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

    /* JADX WARN: Type inference failed for: r1v6, types: [com.android.systemui.keyguard.animator.TapAffordanceViewController$restoreSpringAnimRunnable$1] */
    public TapAffordanceViewController(KeyguardTouchAnimator keyguardTouchAnimator) {
        super(keyguardTouchAnimator);
        this.tapAffordanceViews = Collections.singletonList(6);
        this.tapSpringAnimationList = new ArrayList();
        this.restoreSpringAnimationList = new ArrayList();
        this.restoreSpringAnimRunnable = new Runnable() { // from class: com.android.systemui.keyguard.animator.TapAffordanceViewController$restoreSpringAnimRunnable$1
            @Override // java.lang.Runnable
            public final void run() {
                KeyguardTouchAnimator keyguardTouchAnimator2;
                TapAffordanceViewController tapAffordanceViewController = TapAffordanceViewController.this;
                int i = TapAffordanceViewController.$r8$clinit;
                tapAffordanceViewController.getClass();
                Log.d("KeyguardTouchAnimator", "restoreTapAffordanceAnimation");
                tapAffordanceViewController.isTapAnimationRunning = false;
                ((ArrayList) tapAffordanceViewController.tapSpringAnimationList).clear();
                List list = tapAffordanceViewController.tapAffordanceViews;
                ArrayList arrayList = new ArrayList();
                Iterator it = list.iterator();
                while (true) {
                    boolean hasNext = it.hasNext();
                    keyguardTouchAnimator2 = tapAffordanceViewController.keyguardTouchAnimator;
                    if (!hasNext) {
                        break;
                    }
                    Object next = it.next();
                    if (keyguardTouchAnimator2.hasView$frameworks__base__packages__SystemUI__android_common__SystemUI_core(((Number) next).intValue())) {
                        arrayList.add(next);
                    }
                }
                ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList, 10));
                Iterator it2 = arrayList.iterator();
                while (it2.hasNext()) {
                    arrayList2.add(keyguardTouchAnimator2.getView$frameworks__base__packages__SystemUI__android_common__SystemUI_core(((Number) it2.next()).intValue()));
                }
                ArrayList arrayList3 = new ArrayList();
                Iterator it3 = arrayList2.iterator();
                while (it3.hasNext()) {
                    Object next2 = it3.next();
                    if (((View) next2).getVisibility() == 0) {
                        arrayList3.add(next2);
                    }
                }
                Iterator it4 = arrayList3.iterator();
                while (it4.hasNext()) {
                    View view = (View) it4.next();
                    SpringAnimation springAnimation = new SpringAnimation(view, DynamicAnimation.SCALE_X);
                    springAnimation.mSpring = ActionUpOrCancelHandler$$ExternalSyntheticOutline0.m(350.0f, 0.78f);
                    springAnimation.animateToFinalPosition(1.0f);
                    ((ArrayList) tapAffordanceViewController.restoreSpringAnimationList).add(springAnimation);
                    SpringAnimation springAnimation2 = new SpringAnimation(view, DynamicAnimation.SCALE_Y);
                    springAnimation2.mSpring = ActionUpOrCancelHandler$$ExternalSyntheticOutline0.m(350.0f, 0.78f);
                    springAnimation2.animateToFinalPosition(1.0f);
                    ((ArrayList) tapAffordanceViewController.restoreSpringAnimationList).add(springAnimation2);
                }
            }
        };
    }
}
