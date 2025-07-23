package com.android.wm.shell.shared.animation;

import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.FloatPropertyCompat;
import com.android.wm.shell.shared.animation.PhysicsAnimator;
import java.util.Set;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class PhysicsAnimator$configureDynamicAnimation$1 implements DynamicAnimation.OnAnimationUpdateListener {
    public final /* synthetic */ FloatPropertyCompat $property;
    public final /* synthetic */ PhysicsAnimator this$0;

    public PhysicsAnimator$configureDynamicAnimation$1(PhysicsAnimator physicsAnimator, FloatPropertyCompat floatPropertyCompat) {
        this.this$0 = physicsAnimator;
        this.$property = floatPropertyCompat;
    }

    @Override // androidx.dynamicanimation.animation.DynamicAnimation.OnAnimationUpdateListener
    public final void onAnimationUpdate(DynamicAnimation dynamicAnimation, float f, float f2) {
        PhysicsAnimator physicsAnimator = this.this$0;
        int size = physicsAnimator.internalListeners.size();
        for (int i = 0; i < size; i++) {
            PhysicsAnimator.InternalListener internalListener = (PhysicsAnimator.InternalListener) physicsAnimator.internalListeners.get(i);
            Set set = internalListener.properties;
            FloatPropertyCompat floatPropertyCompat = this.$property;
            if (set.contains(floatPropertyCompat)) {
                internalListener.undispatchedUpdates.put(floatPropertyCompat, new PhysicsAnimator.AnimationUpdate(f, f2));
                internalListener.maybeDispatchUpdates();
            }
        }
    }
}
