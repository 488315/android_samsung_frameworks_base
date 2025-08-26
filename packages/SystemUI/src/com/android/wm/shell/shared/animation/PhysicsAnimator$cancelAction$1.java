package com.android.wm.shell.shared.animation;

import androidx.dynamicanimation.animation.FlingAnimation;
import androidx.dynamicanimation.animation.FloatPropertyCompat;
import androidx.dynamicanimation.animation.SpringAnimation;
import java.util.Set;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* loaded from: classes3.dex */
final /* synthetic */ class PhysicsAnimator$cancelAction$1 extends FunctionReferenceImpl implements Function1 {
    public PhysicsAnimator$cancelAction$1(Object obj) {
        super(1, obj, PhysicsAnimator.class, "cancelInternal", "cancelInternal$frameworks__base__libs__WindowManager__Shell__shared__android_common__WindowManager_Shell_shared(Ljava/util/Set;)V", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
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
