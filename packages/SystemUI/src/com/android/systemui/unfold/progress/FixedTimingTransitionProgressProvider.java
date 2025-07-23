package com.android.systemui.unfold.progress;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.util.FloatProperty;
import com.android.systemui.unfold.UnfoldTransitionProgressProvider;
import com.android.systemui.unfold.updates.DeviceFoldStateProvider;
import com.android.systemui.unfold.updates.FoldStateProvider;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class FixedTimingTransitionProgressProvider implements UnfoldTransitionProgressProvider, FoldStateProvider.FoldUpdatesListener {
    public final ObjectAnimator animator;
    public final List listeners;
    public float transitionProgress;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class AnimationProgressProperty extends FloatProperty {
        public static final AnimationProgressProperty INSTANCE = new AnimationProgressProperty();

        private AnimationProgressProperty() {
            super("animation_progress");
        }

        @Override // android.util.Property
        public final Float get(Object obj) {
            return Float.valueOf(((FixedTimingTransitionProgressProvider) obj).transitionProgress);
        }

        @Override // android.util.FloatProperty
        public final void setValue(Object obj, float f) {
            FixedTimingTransitionProgressProvider fixedTimingTransitionProgressProvider = (FixedTimingTransitionProgressProvider) obj;
            ArrayList arrayList = (ArrayList) fixedTimingTransitionProgressProvider.listeners;
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj2 = arrayList.get(i);
                i++;
                ((UnfoldTransitionProgressProvider.TransitionProgressListener) obj2).onTransitionProgress(f);
            }
            fixedTimingTransitionProgressProvider.transitionProgress = f;
        }
    }

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

    public FixedTimingTransitionProgressProvider(FoldStateProvider foldStateProvider) {
        AnimatorListener animatorListener = new AnimatorListener();
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this, AnimationProgressProperty.INSTANCE, 0.0f, 1.0f);
        ofFloat.setDuration(400L);
        ofFloat.addListener(animatorListener);
        this.animator = ofFloat;
        this.listeners = new ArrayList();
        DeviceFoldStateProvider deviceFoldStateProvider = (DeviceFoldStateProvider) foldStateProvider;
        deviceFoldStateProvider.addCallback(this);
        deviceFoldStateProvider.start();
    }

    @Override // com.android.systemui.unfold.util.CallbackController
    public final void addCallback(Object obj) {
        ((ArrayList) this.listeners).add((UnfoldTransitionProgressProvider.TransitionProgressListener) obj);
    }

    @Override // com.android.systemui.unfold.updates.FoldStateProvider.FoldUpdatesListener
    public final void onFoldUpdate(int i) {
        if (i == 4) {
            this.animator.cancel();
        }
    }

    @Override // com.android.systemui.unfold.updates.FoldStateProvider.FoldUpdatesListener
    public final void onUnfoldedScreenAvailable() {
        this.animator.start();
    }

    @Override // com.android.systemui.unfold.util.CallbackController
    public final void removeCallback(Object obj) {
        ((ArrayList) this.listeners).remove((UnfoldTransitionProgressProvider.TransitionProgressListener) obj);
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class AnimatorListener implements Animator.AnimatorListener {
        public AnimatorListener() {
        }

        @Override // android.animation.Animator.AnimatorListener
        public final void onAnimationEnd(Animator animator) {
            Iterator it = FixedTimingTransitionProgressProvider.this.listeners.iterator();
            while (it.hasNext()) {
                ((UnfoldTransitionProgressProvider.TransitionProgressListener) it.next()).onTransitionFinished();
            }
        }

        @Override // android.animation.Animator.AnimatorListener
        public final void onAnimationStart(Animator animator) {
            Iterator it = FixedTimingTransitionProgressProvider.this.listeners.iterator();
            while (it.hasNext()) {
                ((UnfoldTransitionProgressProvider.TransitionProgressListener) it.next()).onTransitionStarted();
            }
            Iterator it2 = FixedTimingTransitionProgressProvider.this.listeners.iterator();
            while (it2.hasNext()) {
                ((UnfoldTransitionProgressProvider.TransitionProgressListener) it2.next()).onTransitionFinishing();
            }
        }

        @Override // android.animation.Animator.AnimatorListener
        public final void onAnimationCancel(Animator animator) {
        }

        @Override // android.animation.Animator.AnimatorListener
        public final void onAnimationRepeat(Animator animator) {
        }
    }
}
