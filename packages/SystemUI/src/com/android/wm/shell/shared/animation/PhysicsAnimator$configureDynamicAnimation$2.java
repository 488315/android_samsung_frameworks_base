package com.android.wm.shell.shared.animation;

import android.util.ArrayMap;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.FlingAnimation;
import androidx.dynamicanimation.animation.FloatPropertyCompat;
import com.android.wm.shell.shared.animation.PhysicsAnimator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__MutableCollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
public final class PhysicsAnimator$configureDynamicAnimation$2 implements DynamicAnimation.OnAnimationEndListener {
    public final /* synthetic */ DynamicAnimation $anim;
    public final /* synthetic */ FloatPropertyCompat $property;
    public final /* synthetic */ PhysicsAnimator this$0;

    public PhysicsAnimator$configureDynamicAnimation$2(PhysicsAnimator physicsAnimator, FloatPropertyCompat floatPropertyCompat, DynamicAnimation dynamicAnimation) {
        this.this$0 = physicsAnimator;
        this.$property = floatPropertyCompat;
        this.$anim = dynamicAnimation;
    }

    @Override // androidx.dynamicanimation.animation.DynamicAnimation.OnAnimationEndListener
    public final void onAnimationEnd(DynamicAnimation dynamicAnimation, final boolean z, final float f, final float f2) {
        PhysicsAnimator physicsAnimator = this.this$0;
        ArrayList arrayList = physicsAnimator.internalListeners;
        final FloatPropertyCompat floatPropertyCompat = this.$property;
        final DynamicAnimation dynamicAnimation2 = this.$anim;
        CollectionsKt__MutableCollectionsKt.removeAll(arrayList, new Function1() { // from class: com.android.wm.shell.shared.animation.PhysicsAnimator$configureDynamicAnimation$2$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                boolean z2;
                PhysicsAnimator.InternalListener internalListener = (PhysicsAnimator.InternalListener) obj;
                boolean z3 = dynamicAnimation2 instanceof FlingAnimation;
                Set set = internalListener.properties;
                FloatPropertyCompat floatPropertyCompat2 = floatPropertyCompat;
                if (set.contains(floatPropertyCompat2)) {
                    internalListener.numPropertiesAnimating--;
                    internalListener.maybeDispatchUpdates();
                    if (internalListener.undispatchedUpdates.containsKey(floatPropertyCompat2)) {
                        for (PhysicsAnimator.UpdateListener updateListener : internalListener.updateListeners) {
                            new ArrayMap().put(floatPropertyCompat2, internalListener.undispatchedUpdates.get(floatPropertyCompat2));
                            Unit unit = Unit.INSTANCE;
                            updateListener.onAnimationUpdateForProperty(internalListener.target);
                        }
                        internalListener.undispatchedUpdates.remove(floatPropertyCompat2);
                    }
                    Set set2 = internalListener.properties;
                    PhysicsAnimator physicsAnimator2 = PhysicsAnimator.this;
                    boolean zArePropertiesAnimating = physicsAnimator2.arePropertiesAnimating(set2);
                    z2 = !zArePropertiesAnimating;
                    Iterator it = internalListener.endListeners.iterator();
                    do {
                        boolean zHasNext = it.hasNext();
                        boolean z4 = z;
                        if (zHasNext) {
                            ((PhysicsAnimator.EndListener) it.next()).onAnimationEnd(internalListener.target, floatPropertyCompat2, z3, z4, f, f2);
                        } else if (!zArePropertiesAnimating && !z4) {
                            Iterator it2 = internalListener.endActions.iterator();
                            while (it2.hasNext()) {
                                ((Function0) it2.next()).invoke();
                            }
                        }
                    } while (!physicsAnimator2.isPropertyAnimating(floatPropertyCompat2));
                    z2 = false;
                } else {
                    z2 = false;
                }
                return Boolean.valueOf(z2);
            }
        });
        ArrayMap arrayMap = physicsAnimator.springAnimations;
        FloatPropertyCompat floatPropertyCompat2 = this.$property;
        Object obj = arrayMap.get(floatPropertyCompat2);
        DynamicAnimation dynamicAnimation3 = this.$anim;
        if (Intrinsics.areEqual(obj, dynamicAnimation3)) {
            physicsAnimator.springAnimations.remove(floatPropertyCompat2);
        }
        if (Intrinsics.areEqual(physicsAnimator.flingAnimations.get(floatPropertyCompat2), dynamicAnimation3)) {
            physicsAnimator.flingAnimations.remove(floatPropertyCompat2);
        }
    }
}
