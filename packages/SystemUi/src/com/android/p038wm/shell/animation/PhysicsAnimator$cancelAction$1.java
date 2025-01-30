package com.android.p038wm.shell.animation;

import androidx.dynamicanimation.animation.FlingAnimation;
import androidx.dynamicanimation.animation.FloatPropertyCompat;
import androidx.dynamicanimation.animation.SpringAnimation;
import java.util.Set;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public /* synthetic */ class PhysicsAnimator$cancelAction$1 extends FunctionReferenceImpl implements Function1 {
    public PhysicsAnimator$cancelAction$1(Object obj) {
        super(1, obj, PhysicsAnimator.class, "cancelInternal", "cancelInternal$frameworks__base__libs__WindowManager__Shell__android_common__WindowManager_Shell(Ljava/util/Set;)V", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Object obj) {
        PhysicsAnimator physicsAnimator = (PhysicsAnimator) this.receiver;
        physicsAnimator.getClass();
        for (FloatPropertyCompat floatPropertyCompat : (Set) obj) {
            FlingAnimation flingAnimation = (FlingAnimation) physicsAnimator.flingAnimations.get(floatPropertyCompat);
            if (flingAnimation != null) {
                flingAnimation.cancel();
            }
            SpringAnimation springAnimation = (SpringAnimation) physicsAnimator.springAnimations.get(floatPropertyCompat);
            if (springAnimation != null) {
                springAnimation.cancel();
            }
        }
        return Unit.INSTANCE;
    }
}
