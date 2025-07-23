package com.android.wm.shell.shared.bubbles;

import android.graphics.RectF;
import android.view.View;
import androidx.core.animation.ObjectAnimator;
import com.android.wm.shell.bubbles.bar.BubbleBarLayerView;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public abstract class BaseBubblePinController {
    public RectF dismissZone;
    public ObjectAnimator dropTargetAnimator;
    public boolean initialLocationOnLeft;
    public BubbleBarLayerView.LocationChangeListener listener;
    public boolean onLeft;
    public int screenCenterX;
    public final Function0 screenSizeProvider;
    public boolean stuckToDismissTarget;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public static /* synthetic */ void getDROP_TARGET_ALPHA_IN_DURATION$annotations() {
        }

        public static /* synthetic */ void getDROP_TARGET_ALPHA_OUT_DURATION$annotations() {
        }
    }

    static {
        new Companion(null);
    }

    public BaseBubblePinController(Function0 function0) {
        this.screenSizeProvider = function0;
    }

    public final void animateIn(View view) {
        ObjectAnimator objectAnimator = this.dropTargetAnimator;
        if (objectAnimator != null) {
            objectAnimator.cancel();
        }
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, View.ALPHA, 1.0f);
        ofFloat.m894setDuration(150L);
        ofFloat.addListener(new BaseBubblePinController$addEndAction$1(new Runnable() { // from class: com.android.wm.shell.shared.bubbles.BaseBubblePinController$animateIn$1
            @Override // java.lang.Runnable
            public final void run() {
                BaseBubblePinController.this.dropTargetAnimator = null;
            }
        }));
        this.dropTargetAnimator = ofFloat;
        ofFloat.start();
    }

    public final void animateOut(View view, final Runnable runnable) {
        ObjectAnimator objectAnimator = this.dropTargetAnimator;
        if (objectAnimator != null) {
            objectAnimator.cancel();
        }
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, View.ALPHA, 0.0f);
        ofFloat.m894setDuration(100L);
        ofFloat.addListener(new BaseBubblePinController$addEndAction$1(new Runnable() { // from class: com.android.wm.shell.shared.bubbles.BaseBubblePinController$animateOut$1
            @Override // java.lang.Runnable
            public final void run() {
                Runnable runnable2 = runnable;
                if (runnable2 != null) {
                    runnable2.run();
                }
                this.dropTargetAnimator = null;
            }
        }));
        this.dropTargetAnimator = ofFloat;
        ofFloat.start();
    }

    public abstract void removeDropTargetView(View view);

    public abstract void updateLocation(BubbleBarLocation bubbleBarLocation);
}
