package com.android.p038wm.shell.animation;

import android.util.ArrayMap;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.FlingAnimation;
import androidx.dynamicanimation.animation.FloatPropertyCompat;
import com.android.p038wm.shell.animation.PhysicsAnimator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__MutableCollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
        CollectionsKt__MutableCollectionsKt.filterInPlace$CollectionsKt__MutableCollectionsKt((List) arrayList, new Function1() { // from class: com.android.wm.shell.animation.PhysicsAnimator$configureDynamicAnimation$2.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                boolean z2;
                PhysicsAnimator.InternalListener internalListener = (PhysicsAnimator.InternalListener) obj;
                FloatPropertyCompat floatPropertyCompat2 = FloatPropertyCompat.this;
                boolean z3 = z;
                float f3 = f;
                float f4 = f2;
                boolean z4 = dynamicAnimation2 instanceof FlingAnimation;
                Set set = internalListener.properties;
                if (set.contains(floatPropertyCompat2)) {
                    internalListener.numPropertiesAnimating--;
                    internalListener.maybeDispatchUpdates();
                    ArrayMap arrayMap = internalListener.undispatchedUpdates;
                    if (arrayMap.containsKey(floatPropertyCompat2)) {
                        for (PhysicsAnimator.UpdateListener updateListener : internalListener.updateListeners) {
                            new ArrayMap().put(floatPropertyCompat2, arrayMap.get(floatPropertyCompat2));
                            Unit unit = Unit.INSTANCE;
                            updateListener.onAnimationUpdateForProperty(internalListener.target);
                        }
                        arrayMap.remove(floatPropertyCompat2);
                    }
                    PhysicsAnimator physicsAnimator2 = PhysicsAnimator.this;
                    z2 = !physicsAnimator2.arePropertiesAnimating(set);
                    Iterator it = internalListener.endListeners.iterator();
                    while (it.hasNext()) {
                        ((PhysicsAnimator.EndListener) it.next()).onAnimationEnd(internalListener.target, floatPropertyCompat2, z4, z3, f3, f4);
                        if (physicsAnimator2.isPropertyAnimating(floatPropertyCompat2)) {
                        }
                    }
                    if (z2 && !z3) {
                        Iterator it2 = internalListener.endActions.iterator();
                        while (it2.hasNext()) {
                            ((Function0) it2.next()).invoke();
                        }
                    }
                    return Boolean.valueOf(z2);
                }
                z2 = false;
                return Boolean.valueOf(z2);
            }
        }, true);
        ArrayMap arrayMap = physicsAnimator.springAnimations;
        FloatPropertyCompat floatPropertyCompat2 = this.$property;
        Object obj = arrayMap.get(floatPropertyCompat2);
        DynamicAnimation dynamicAnimation3 = this.$anim;
        if (Intrinsics.areEqual(obj, dynamicAnimation3)) {
            arrayMap.remove(floatPropertyCompat2);
        }
        ArrayMap arrayMap2 = physicsAnimator.flingAnimations;
        if (Intrinsics.areEqual(arrayMap2.get(floatPropertyCompat2), dynamicAnimation3)) {
            arrayMap2.remove(floatPropertyCompat2);
        }
    }
}
