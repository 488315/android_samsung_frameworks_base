package com.android.systemui.keyguard.animator;

import android.util.Log;
import android.view.View;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;
import com.android.keyguard.ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public final class TapAffordanceViewController extends ViewAnimationController {
    public static final /* synthetic */ int $r8$clinit = 0;
    public boolean isTapAnimationRunning;
    public final TapAffordanceViewController$restoreSpringAnimRunnable$1 restoreSpringAnimRunnable;
    public final List restoreSpringAnimationList;
    public final List tapAffordanceViews;
    public final List tapSpringAnimationList;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
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
                TapAffordanceViewController tapAffordanceViewController = this.this$0;
                int i = TapAffordanceViewController.$r8$clinit;
                tapAffordanceViewController.getClass();
                Log.d("KeyguardTouchAnimator", "restoreTapAffordanceAnimation");
                int i2 = 0;
                tapAffordanceViewController.isTapAnimationRunning = false;
                ((ArrayList) tapAffordanceViewController.tapSpringAnimationList).clear();
                List list = tapAffordanceViewController.tapAffordanceViews;
                ArrayList arrayList = new ArrayList();
                Iterator it = list.iterator();
                while (true) {
                    boolean zHasNext = it.hasNext();
                    keyguardTouchAnimator2 = tapAffordanceViewController.keyguardTouchAnimator;
                    if (!zHasNext) {
                        break;
                    }
                    Object next = it.next();
                    if (keyguardTouchAnimator2.hasView$frameworks__base__packages__SystemUI__android_common__SystemUI_core(((Number) next).intValue())) {
                        arrayList.add(next);
                    }
                }
                ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList, 10));
                int size = arrayList.size();
                int i3 = 0;
                while (i3 < size) {
                    Object obj = arrayList.get(i3);
                    i3++;
                    arrayList2.add(keyguardTouchAnimator2.getView$frameworks__base__packages__SystemUI__android_common__SystemUI_core(((Number) obj).intValue()));
                }
                ArrayList arrayList3 = new ArrayList();
                int size2 = arrayList2.size();
                int i4 = 0;
                while (i4 < size2) {
                    Object obj2 = arrayList2.get(i4);
                    i4++;
                    if (((View) obj2).getVisibility() == 0) {
                        arrayList3.add(obj2);
                    }
                }
                int size3 = arrayList3.size();
                while (i2 < size3) {
                    Object obj3 = arrayList3.get(i2);
                    i2++;
                    View view = (View) obj3;
                    SpringAnimation springAnimation = new SpringAnimation(view, DynamicAnimation.SCALE_X);
                    springAnimation.mSpring = ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(350.0f, 0.78f);
                    springAnimation.animateToFinalPosition(1.0f);
                    ((ArrayList) tapAffordanceViewController.restoreSpringAnimationList).add(springAnimation);
                    SpringAnimation springAnimation2 = new SpringAnimation(view, DynamicAnimation.SCALE_Y);
                    springAnimation2.mSpring = ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(350.0f, 0.78f);
                    springAnimation2.animateToFinalPosition(1.0f);
                    ((ArrayList) tapAffordanceViewController.restoreSpringAnimationList).add(springAnimation2);
                }
            }
        };
    }
}
