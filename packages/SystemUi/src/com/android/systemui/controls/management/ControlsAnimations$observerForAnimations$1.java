package com.android.systemui.controls.management;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import com.android.systemui.R;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ControlsAnimations$observerForAnimations$1 implements LifecycleObserver {
    public final /* synthetic */ ViewGroup $view;
    public final /* synthetic */ Window $window;
    public boolean showAnimation;

    public ControlsAnimations$observerForAnimations$1(Intent intent, ViewGroup viewGroup, boolean z, Window window) {
        this.$view = viewGroup;
        this.$window = window;
        this.showAnimation = intent.getBooleanExtra("extra_animate", false);
        viewGroup.setTransitionGroup(true);
        viewGroup.setTransitionAlpha(0.0f);
        if (ControlsAnimations.translationY == -1.0f) {
            if (z) {
                ControlsAnimations.translationY = viewGroup.getContext().getResources().getDimensionPixelSize(R.dimen.global_actions_controls_y_translation);
            } else {
                ControlsAnimations.translationY = 0.0f;
            }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public final void enterAnimation() {
        if (this.showAnimation) {
            ControlsAnimations.INSTANCE.getClass();
            ControlsAnimations.enterAnimation(this.$view).start();
            this.showAnimation = false;
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public final void resetAnimation() {
        this.$view.setTranslationY(0.0f);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public final void setup() {
        Window window = this.$window;
        window.setAllowEnterTransitionOverlap(true);
        ControlsAnimations controlsAnimations = ControlsAnimations.INSTANCE;
        ViewGroup viewGroup = this.$view;
        int id = viewGroup.getId();
        controlsAnimations.getClass();
        ControlsAnimations$enterWindowTransition$1 controlsAnimations$enterWindowTransition$1 = new Function1() { // from class: com.android.systemui.controls.management.ControlsAnimations$enterWindowTransition$1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                ControlsAnimations.INSTANCE.getClass();
                return ControlsAnimations.enterAnimation((View) obj);
            }
        };
        WindowTransition windowTransition = new WindowTransition(controlsAnimations$enterWindowTransition$1);
        windowTransition.addTarget(id);
        window.setEnterTransition(windowTransition);
        int id2 = viewGroup.getId();
        ControlsAnimations$exitWindowTransition$1 controlsAnimations$exitWindowTransition$1 = new Function1() { // from class: com.android.systemui.controls.management.ControlsAnimations$exitWindowTransition$1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ControlsAnimations.exitAnimation((View) obj, null);
            }
        };
        WindowTransition windowTransition2 = new WindowTransition(controlsAnimations$exitWindowTransition$1);
        windowTransition2.addTarget(id2);
        window.setExitTransition(windowTransition2);
        int id3 = viewGroup.getId();
        WindowTransition windowTransition3 = new WindowTransition(controlsAnimations$enterWindowTransition$1);
        windowTransition3.addTarget(id3);
        window.setReenterTransition(windowTransition3);
        int id4 = viewGroup.getId();
        WindowTransition windowTransition4 = new WindowTransition(controlsAnimations$exitWindowTransition$1);
        windowTransition4.addTarget(id4);
        window.setReturnTransition(windowTransition4);
    }
}
