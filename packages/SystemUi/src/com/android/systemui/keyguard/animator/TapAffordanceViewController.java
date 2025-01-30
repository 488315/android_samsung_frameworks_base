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

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class TapAffordanceViewController extends ViewAnimationController {
    public static final /* synthetic */ int $r8$clinit = 0;
    public boolean isTapAnimationRunning;
    public final TapAffordanceViewController$restoreSpringAnimRunnable$1 restoreSpringAnimRunnable;
    public final List restoreSpringAnimationList;
    public final List tapAffordanceViews;
    public final List tapSpringAnimationList;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
                TapAffordanceViewController tapAffordanceViewController = TapAffordanceViewController.this;
                int i = TapAffordanceViewController.$r8$clinit;
                tapAffordanceViewController.getClass();
                Log.d("KeyguardTouchAnimator", "restoreTapAffordanceAnimation");
                tapAffordanceViewController.isTapAnimationRunning = false;
                ((ArrayList) tapAffordanceViewController.tapSpringAnimationList).clear();
                ArrayList arrayList = new ArrayList();
                for (Object obj : tapAffordanceViewController.tapAffordanceViews) {
                    if (tapAffordanceViewController.hasView(((Number) obj).intValue())) {
                        arrayList.add(obj);
                    }
                }
                ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList, 10));
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    arrayList2.add(tapAffordanceViewController.getView(((Number) it.next()).intValue()));
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
                    SpringAnimation springAnimation = new SpringAnimation(view, DynamicAnimation.SCALE_X);
                    springAnimation.mSpring = ActionUpOrCancelHandler$$ExternalSyntheticOutline0.m145m(350.0f, 0.78f);
                    springAnimation.animateToFinalPosition(1.0f);
                    ArrayList arrayList4 = (ArrayList) tapAffordanceViewController.restoreSpringAnimationList;
                    arrayList4.add(springAnimation);
                    SpringAnimation springAnimation2 = new SpringAnimation(view, DynamicAnimation.SCALE_Y);
                    springAnimation2.mSpring = ActionUpOrCancelHandler$$ExternalSyntheticOutline0.m145m(350.0f, 0.78f);
                    springAnimation2.animateToFinalPosition(1.0f);
                    arrayList4.add(springAnimation2);
                }
            }
        };
    }
}
