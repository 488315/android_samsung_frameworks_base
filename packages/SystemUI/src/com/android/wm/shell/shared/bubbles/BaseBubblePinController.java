package com.android.wm.shell.shared.bubbles;

import android.graphics.RectF;
import android.view.View;
import androidx.core.animation.ObjectAnimator;
import com.android.wm.shell.bubbles.bar.BubbleBarLayerView;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

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
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(view, View.ALPHA, 1.0f);
        objectAnimatorOfFloat.m896setDuration(150L);
        objectAnimatorOfFloat.addListener(new BaseBubblePinController$addEndAction$1(new Runnable() { // from class: com.android.wm.shell.shared.bubbles.BaseBubblePinController.animateIn.1
            @Override // java.lang.Runnable
            public final void run() {
                BaseBubblePinController.this.dropTargetAnimator = null;
            }
        }));
        this.dropTargetAnimator = objectAnimatorOfFloat;
        objectAnimatorOfFloat.start();
    }

    public final void animateOut(View view, final Runnable runnable) {
        ObjectAnimator objectAnimator = this.dropTargetAnimator;
        if (objectAnimator != null) {
            objectAnimator.cancel();
        }
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(view, View.ALPHA, 0.0f);
        objectAnimatorOfFloat.m896setDuration(100L);
        objectAnimatorOfFloat.addListener(new BaseBubblePinController$addEndAction$1(new Runnable() { // from class: com.android.wm.shell.shared.bubbles.BaseBubblePinController.animateOut.1
            @Override // java.lang.Runnable
            public final void run() {
                Runnable runnable2 = runnable;
                if (runnable2 != null) {
                    runnable2.run();
                }
                this.dropTargetAnimator = null;
            }
        }));
        this.dropTargetAnimator = objectAnimatorOfFloat;
        objectAnimatorOfFloat.start();
    }

    public abstract void removeDropTargetView(View view);

    public abstract void updateLocation(BubbleBarLocation bubbleBarLocation);
}
