package com.android.systemui.media.controls.ui.animation;

import android.graphics.drawable.Animatable2;
import android.graphics.drawable.Drawable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.jvm.functions.Function0;

public final class AnimationBindHandler extends Animatable2.AnimationCallback {
    public Integer rebindId;
    public final List onAnimationsComplete = new ArrayList();
    public final List registrations = new ArrayList();

    public final boolean isAnimationRunning() {
        List list = this.registrations;
        if ((list instanceof Collection) && list.isEmpty()) {
            return false;
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            if (((Animatable2) it.next()).isRunning()) {
                return true;
            }
        }
        return false;
    }

    @Override // android.graphics.drawable.Animatable2.AnimationCallback
    public final void onAnimationEnd(Drawable drawable) {
        super.onAnimationEnd(drawable);
        if (isAnimationRunning()) {
            return;
        }
        Iterator it = this.onAnimationsComplete.iterator();
        while (it.hasNext()) {
            ((Function0) it.next()).invoke();
        }
        ((ArrayList) this.onAnimationsComplete).clear();
    }

    public final void tryRegister(Drawable drawable) {
        if (drawable instanceof Animatable2) {
            Animatable2 animatable2 = (Animatable2) drawable;
            animatable2.registerAnimationCallback(this);
            ((ArrayList) this.registrations).add(animatable2);
        }
    }

    public final void unregisterAll() {
        Iterator it = this.registrations.iterator();
        while (it.hasNext()) {
            ((Animatable2) it.next()).unregisterAnimationCallback(this);
        }
        ((ArrayList) this.registrations).clear();
    }
}
