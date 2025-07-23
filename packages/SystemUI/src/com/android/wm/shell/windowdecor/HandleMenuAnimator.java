package com.android.wm.shell.windowdecor;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.view.ViewGroupKt$children$1;
import com.android.systemui.R;
import com.android.wm.shell.shared.animation.Interpolators;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class HandleMenuAnimator {
    public final List animators = new ArrayList();
    public final ViewGroup appInfoPill;
    public final float captionHeight;
    public final View handleMenu;
    public final int menuWidth;
    public final ViewGroup moreActionsPill;
    public final ViewGroup openInAppOrBrowserPill;
    public AnimatorSet runningAnimation;
    public final ViewGroup windowingPill;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    public HandleMenuAnimator(View view, int i, float f) {
        this.handleMenu = view;
        this.menuWidth = i;
        this.captionHeight = f;
        this.appInfoPill = (ViewGroup) view.requireViewById(R.id.app_info_pill);
        this.windowingPill = (ViewGroup) view.requireViewById(R.id.windowing_pill);
        this.moreActionsPill = (ViewGroup) view.requireViewById(R.id.more_actions_pill);
        this.openInAppOrBrowserPill = (ViewGroup) view.requireViewById(R.id.open_in_app_or_browser_pill);
    }

    public final void animateAppInfoPillFadeOut() {
        Iterator it = new ViewGroupKt$children$1(this.appInfoPill).iterator();
        while (it.hasNext()) {
            View view = (View) it.next();
            List list = this.animators;
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, (Property<View, Float>) View.ALPHA, 0.0f);
            ofFloat.setStartDelay(25L);
            ofFloat.setDuration(25L);
            list.add(ofFloat);
        }
    }

    public final void animateAppInfoPillOpen() {
        List list = this.animators;
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.appInfoPill, (Property<ViewGroup, Float>) View.TRANSLATION_Z, 1.0f);
        ofFloat.setStartDelay(33L);
        ofFloat.setDuration(83L);
        list.add(ofFloat);
        Iterator it = new ViewGroupKt$children$1(this.appInfoPill).iterator();
        while (it.hasNext()) {
            View view = (View) it.next();
            List list2 = this.animators;
            ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(view, (Property<View, Float>) View.ALPHA, 1.0f);
            ofFloat2.setStartDelay(67L);
            ofFloat2.setDuration(100L);
            list2.add(ofFloat2);
        }
    }

    public final void animateMoreActionsPillOpen() {
        List list = this.animators;
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.moreActionsPill, (Property<ViewGroup, Float>) View.SCALE_X, 0.5f, 1.0f);
        ofFloat.setStartDelay(50L);
        ofFloat.setDuration(180L);
        list.add(ofFloat);
        List list2 = this.animators;
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(this.moreActionsPill, (Property<ViewGroup, Float>) View.SCALE_Y, 0.5f, 1.0f);
        ofFloat2.setStartDelay(50L);
        ofFloat2.setDuration(180L);
        list2.add(ofFloat2);
        List list3 = this.animators;
        ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(this.moreActionsPill, (Property<ViewGroup, Float>) View.ALPHA, 1.0f);
        ofFloat3.setStartDelay(133L);
        ofFloat3.setDuration(150L);
        list3.add(ofFloat3);
        List list4 = this.animators;
        ObjectAnimator ofFloat4 = ObjectAnimator.ofFloat(this.moreActionsPill, (Property<ViewGroup, Float>) View.TRANSLATION_Z, 1.0f);
        ofFloat4.setStartDelay(33L);
        ofFloat4.setDuration(83L);
        list4.add(ofFloat4);
        Iterator it = new ViewGroupKt$children$1(this.moreActionsPill).iterator();
        while (it.hasNext()) {
            View view = (View) it.next();
            List list5 = this.animators;
            ObjectAnimator ofFloat5 = ObjectAnimator.ofFloat(view, (Property<View, Float>) View.ALPHA, 1.0f);
            ofFloat5.setStartDelay(133L);
            ofFloat5.setDuration(167L);
            ofFloat5.setInterpolator(Interpolators.FAST_OUT_SLOW_IN);
            list5.add(ofFloat5);
        }
    }

    public final void animateOpenInAppOrBrowserPill() {
        List list = this.animators;
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.openInAppOrBrowserPill, (Property<ViewGroup, Float>) View.SCALE_X, 0.5f, 1.0f);
        ofFloat.setStartDelay(50L);
        ofFloat.setDuration(180L);
        list.add(ofFloat);
        List list2 = this.animators;
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(this.openInAppOrBrowserPill, (Property<ViewGroup, Float>) View.SCALE_Y, 0.5f, 1.0f);
        ofFloat2.setStartDelay(50L);
        ofFloat2.setDuration(180L);
        list2.add(ofFloat2);
        List list3 = this.animators;
        ViewGroup viewGroup = this.openInAppOrBrowserPill;
        Property property = View.ALPHA;
        ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(viewGroup, (Property<ViewGroup, Float>) property, 1.0f);
        ofFloat3.setStartDelay(133L);
        ofFloat3.setDuration(150L);
        list3.add(ofFloat3);
        List list4 = this.animators;
        ObjectAnimator ofFloat4 = ObjectAnimator.ofFloat(this.openInAppOrBrowserPill, (Property<ViewGroup, Float>) View.TRANSLATION_Z, 1.0f);
        ofFloat4.setStartDelay(33L);
        ofFloat4.setDuration(83L);
        list4.add(ofFloat4);
        View requireViewById = this.openInAppOrBrowserPill.requireViewById(R.id.open_in_app_or_browser_button);
        List list5 = this.animators;
        ObjectAnimator ofFloat5 = ObjectAnimator.ofFloat(requireViewById, (Property<View, Float>) property, 1.0f);
        ofFloat5.setStartDelay(133L);
        ofFloat5.setDuration(167L);
        ofFloat5.setInterpolator(Interpolators.FAST_OUT_SLOW_IN);
        list5.add(ofFloat5);
    }

    public final void animateWindowingPillOpen() {
        List list = this.animators;
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.windowingPill, (Property<ViewGroup, Float>) View.SCALE_X, 0.5f, 1.0f);
        ofFloat.setStartDelay(50L);
        ofFloat.setDuration(180L);
        list.add(ofFloat);
        List list2 = this.animators;
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(this.windowingPill, (Property<ViewGroup, Float>) View.SCALE_Y, 0.5f, 1.0f);
        ofFloat2.setStartDelay(50L);
        ofFloat2.setDuration(180L);
        list2.add(ofFloat2);
        List list3 = this.animators;
        ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(this.windowingPill, (Property<ViewGroup, Float>) View.ALPHA, 1.0f);
        ofFloat3.setStartDelay(133L);
        ofFloat3.setDuration(150L);
        list3.add(ofFloat3);
        List list4 = this.animators;
        ObjectAnimator ofFloat4 = ObjectAnimator.ofFloat(this.windowingPill, (Property<ViewGroup, Float>) View.TRANSLATION_Z, 1.0f);
        ofFloat4.setStartDelay(33L);
        ofFloat4.setDuration(83L);
        list4.add(ofFloat4);
        Iterator it = new ViewGroupKt$children$1(this.windowingPill).iterator();
        while (it.hasNext()) {
            View view = (View) it.next();
            List list5 = this.animators;
            ObjectAnimator ofFloat5 = ObjectAnimator.ofFloat(view, (Property<View, Float>) View.ALPHA, 1.0f);
            ofFloat5.setStartDelay(133L);
            ofFloat5.setDuration(167L);
            ofFloat5.setInterpolator(Interpolators.FAST_OUT_SLOW_IN);
            list5.add(ofFloat5);
        }
    }

    public final void moreActionsPillClose() {
        List list = this.animators;
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.moreActionsPill, (Property<ViewGroup, Float>) View.SCALE_X, 0.5f);
        ofFloat.setDuration(50L);
        list.add(ofFloat);
        List list2 = this.animators;
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(this.moreActionsPill, (Property<ViewGroup, Float>) View.SCALE_Y, 0.5f);
        ofFloat2.setDuration(50L);
        list2.add(ofFloat2);
        List list3 = this.animators;
        ViewGroup viewGroup = this.moreActionsPill;
        Property property = View.ALPHA;
        ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(viewGroup, (Property<ViewGroup, Float>) property, 0.0f);
        ofFloat3.setDuration(50L);
        list3.add(ofFloat3);
        List list4 = this.animators;
        ObjectAnimator ofFloat4 = ObjectAnimator.ofFloat(this.moreActionsPill, (Property<ViewGroup, Float>) property, 0.0f);
        ofFloat4.setDuration(50L);
        list4.add(ofFloat4);
        float f = (-this.captionHeight) / 2;
        List list5 = this.animators;
        ObjectAnimator ofFloat5 = ObjectAnimator.ofFloat(this.moreActionsPill, (Property<ViewGroup, Float>) View.TRANSLATION_Y, f);
        ofFloat5.setDuration(50L);
        list5.add(ofFloat5);
    }

    public final void openInAppOrBrowserPillClose() {
        List list = this.animators;
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.openInAppOrBrowserPill, (Property<ViewGroup, Float>) View.SCALE_X, 0.5f);
        ofFloat.setDuration(50L);
        list.add(ofFloat);
        List list2 = this.animators;
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(this.openInAppOrBrowserPill, (Property<ViewGroup, Float>) View.SCALE_Y, 0.5f);
        ofFloat2.setDuration(50L);
        list2.add(ofFloat2);
        List list3 = this.animators;
        ViewGroup viewGroup = this.openInAppOrBrowserPill;
        Property property = View.ALPHA;
        ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(viewGroup, (Property<ViewGroup, Float>) property, 0.0f);
        ofFloat3.setDuration(50L);
        list3.add(ofFloat3);
        List list4 = this.animators;
        ObjectAnimator ofFloat4 = ObjectAnimator.ofFloat(this.openInAppOrBrowserPill, (Property<ViewGroup, Float>) property, 0.0f);
        ofFloat4.setDuration(50L);
        list4.add(ofFloat4);
        float f = (-this.captionHeight) / 2;
        List list5 = this.animators;
        ObjectAnimator ofFloat5 = ObjectAnimator.ofFloat(this.openInAppOrBrowserPill, (Property<ViewGroup, Float>) View.TRANSLATION_Y, f);
        ofFloat5.setDuration(50L);
        list5.add(ofFloat5);
    }

    public final void prepareMenuForAnimation() {
        Iterator it = new ViewGroupKt$children$1(this.appInfoPill).iterator();
        while (it.hasNext()) {
            ((View) it.next()).setAlpha(0.0f);
        }
        this.windowingPill.setAlpha(0.0f);
        this.moreActionsPill.setAlpha(0.0f);
        this.openInAppOrBrowserPill.setAlpha(0.0f);
        View view = this.handleMenu;
        int i = this.menuWidth;
        view.setPivotX(i / 2.0f);
        this.handleMenu.setPivotY(0.0f);
        this.windowingPill.setPivotX(i / 2.0f);
        this.windowingPill.setPivotY(this.appInfoPill.getMeasuredHeight());
        this.moreActionsPill.setPivotX(i / 2.0f);
        this.moreActionsPill.setPivotY(this.appInfoPill.getMeasuredHeight());
        this.openInAppOrBrowserPill.setPivotX(i / 2.0f);
        this.openInAppOrBrowserPill.setPivotY(this.appInfoPill.getMeasuredHeight());
    }

    public final void runAnimations(final Function0 function0) {
        AnimatorSet animatorSet = this.runningAnimation;
        if (animatorSet != null) {
            animatorSet.removeAllListeners();
            animatorSet.cancel();
        }
        AnimatorSet animatorSet2 = new AnimatorSet();
        animatorSet2.playTogether(this.animators);
        ((ArrayList) this.animators).clear();
        animatorSet2.addListener(new Animator.AnimatorListener() { // from class: com.android.wm.shell.windowdecor.HandleMenuAnimator$runAnimations$lambda$51$$inlined$doOnEnd$1
            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                Function0 function02 = Function0.this;
                if (function02 != null) {
                    function02.invoke();
                }
                this.runningAnimation = null;
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationRepeat(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator) {
            }
        });
        animatorSet2.start();
        this.runningAnimation = animatorSet2;
    }

    public final void windowingPillClose() {
        List list = this.animators;
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.windowingPill, (Property<ViewGroup, Float>) View.SCALE_X, 0.5f);
        ofFloat.setDuration(50L);
        list.add(ofFloat);
        List list2 = this.animators;
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(this.windowingPill, (Property<ViewGroup, Float>) View.SCALE_Y, 0.5f);
        ofFloat2.setDuration(50L);
        list2.add(ofFloat2);
        List list3 = this.animators;
        ViewGroup viewGroup = this.windowingPill;
        Property property = View.ALPHA;
        ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(viewGroup, (Property<ViewGroup, Float>) property, 0.0f);
        ofFloat3.setDuration(50L);
        list3.add(ofFloat3);
        List list4 = this.animators;
        ObjectAnimator ofFloat4 = ObjectAnimator.ofFloat(this.windowingPill, (Property<ViewGroup, Float>) property, 0.0f);
        ofFloat4.setDuration(50L);
        list4.add(ofFloat4);
    }
}
