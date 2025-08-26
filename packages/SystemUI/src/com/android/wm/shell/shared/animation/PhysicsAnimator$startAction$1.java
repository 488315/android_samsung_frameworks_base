package com.android.wm.shell.shared.animation;

import android.util.ArrayMap;
import android.util.Log;
import androidx.dynamicanimation.animation.FlingAnimation;
import androidx.dynamicanimation.animation.FloatPropertyCompat;
import androidx.dynamicanimation.animation.SpringAnimation;
import com.android.wm.shell.shared.animation.PhysicsAnimator;
import com.android.wm.shell.shared.animation.PhysicsAnimator.InternalListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.WeakHashMap;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__MutableCollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
final /* synthetic */ class PhysicsAnimator$startAction$1 extends FunctionReferenceImpl implements Function0 {
    public PhysicsAnimator$startAction$1(Object obj) {
        super(0, obj, PhysicsAnimator.class, "startInternal", "startInternal$frameworks__base__libs__WindowManager__Shell__shared__android_common__WindowManager_Shell_shared()V", 0);
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        int i;
        Object obj;
        final PhysicsAnimator physicsAnimator;
        final FloatPropertyCompat floatPropertyCompat;
        PhysicsAnimator physicsAnimator2 = (PhysicsAnimator) this.receiver;
        Object obj2 = physicsAnimator2.weakTarget.get();
        if (obj2 == null) {
            Log.w("PhysicsAnimator", "Trying to animate a GC-ed object.");
        } else {
            ArrayList arrayList = new ArrayList();
            Set setKeySet = physicsAnimator2.springConfigs.keySet();
            Set setKeySet2 = physicsAnimator2.flingConfigs.keySet();
            Set mutableSet = CollectionsKt___CollectionsKt.toMutableSet(setKeySet);
            CollectionsKt__MutableCollectionsKt.addAll(setKeySet2, mutableSet);
            Iterator it = mutableSet.iterator();
            while (true) {
                i = 0;
                if (!it.hasNext()) {
                    break;
                }
                final FloatPropertyCompat floatPropertyCompat2 = (FloatPropertyCompat) it.next();
                final PhysicsAnimator.FlingConfig flingConfig = (PhysicsAnimator.FlingConfig) physicsAnimator2.flingConfigs.get(floatPropertyCompat2);
                final PhysicsAnimator.SpringConfig springConfig = (PhysicsAnimator.SpringConfig) physicsAnimator2.springConfigs.get(floatPropertyCompat2);
                final float value = floatPropertyCompat2.getValue(obj2);
                if (flingConfig != null) {
                    final Object obj3 = obj2;
                    physicsAnimator = physicsAnimator2;
                    Function0 function0 = new Function0() { // from class: com.android.wm.shell.shared.animation.PhysicsAnimator$$ExternalSyntheticLambda0
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            PhysicsAnimator.Companion companion = PhysicsAnimator.Companion;
                            PhysicsAnimator.FlingConfig flingConfig2 = flingConfig;
                            float f = flingConfig2.min;
                            float f2 = value;
                            flingConfig2.min = Math.min(f2, f);
                            flingConfig2.max = Math.max(f2, flingConfig2.max);
                            FloatPropertyCompat floatPropertyCompat3 = floatPropertyCompat2;
                            PhysicsAnimator physicsAnimator3 = physicsAnimator;
                            physicsAnimator3.cancel(floatPropertyCompat3);
                            ArrayMap arrayMap = physicsAnimator3.flingAnimations;
                            Object obj4 = arrayMap.get(floatPropertyCompat3);
                            Object obj5 = obj4;
                            if (obj4 == null) {
                                FlingAnimation flingAnimation = new FlingAnimation(obj3, floatPropertyCompat3);
                                flingAnimation.addUpdateListener(new PhysicsAnimator$configureDynamicAnimation$1(physicsAnimator3, floatPropertyCompat3));
                                flingAnimation.addEndListener(new PhysicsAnimator$configureDynamicAnimation$2(physicsAnimator3, floatPropertyCompat3, flingAnimation));
                                arrayMap.put(floatPropertyCompat3, flingAnimation);
                                obj5 = flingAnimation;
                            }
                            FlingAnimation flingAnimation2 = (FlingAnimation) obj5;
                            float f3 = flingConfig2.friction;
                            if (f3 <= 0.0f) {
                                throw new IllegalArgumentException("Friction must be positive");
                            }
                            flingAnimation2.mFlingForce.mFriction = f3 * (-4.2f);
                            flingAnimation2.mMinValue = flingConfig2.min;
                            flingAnimation2.mMaxValue = flingConfig2.max;
                            flingAnimation2.mVelocity = flingConfig2.startVelocity;
                            flingAnimation2.start();
                            return Unit.INSTANCE;
                        }
                    };
                    flingConfig = flingConfig;
                    floatPropertyCompat = floatPropertyCompat2;
                    obj = obj3;
                    arrayList.add(function0);
                } else {
                    obj = obj2;
                    physicsAnimator = physicsAnimator2;
                    floatPropertyCompat = floatPropertyCompat2;
                }
                if (springConfig != null) {
                    if (flingConfig == null) {
                        ArrayMap arrayMap = physicsAnimator.springAnimations;
                        Object obj4 = arrayMap.get(floatPropertyCompat);
                        Object obj5 = obj4;
                        if (obj4 == null) {
                            SpringAnimation springAnimation = new SpringAnimation(obj, floatPropertyCompat);
                            springAnimation.addUpdateListener(new PhysicsAnimator$configureDynamicAnimation$1(physicsAnimator, floatPropertyCompat));
                            springAnimation.addEndListener(new PhysicsAnimator$configureDynamicAnimation$2(physicsAnimator, floatPropertyCompat, springAnimation));
                            arrayMap.put(floatPropertyCompat, springAnimation);
                            obj5 = springAnimation;
                        }
                        SpringAnimation springAnimation2 = (SpringAnimation) obj5;
                        springConfig.applyToAnimation$frameworks__base__libs__WindowManager__Shell__shared__android_common__WindowManager_Shell_shared(springAnimation2);
                        arrayList.add(new PhysicsAnimator$startInternal$2(springAnimation2));
                    } else {
                        final PhysicsAnimator physicsAnimator3 = physicsAnimator;
                        final float f = flingConfig.min;
                        final float f2 = flingConfig.max;
                        ArrayList arrayList2 = physicsAnimator3.endListeners;
                        PhysicsAnimator.EndListener endListener = new PhysicsAnimator.EndListener() { // from class: com.android.wm.shell.shared.animation.PhysicsAnimator$startInternal$3
                            @Override // com.android.wm.shell.shared.animation.PhysicsAnimator.EndListener
                            public final void onAnimationEnd(Object obj6, FloatPropertyCompat floatPropertyCompat3, boolean z, boolean z2, float f3, float f4) {
                                FloatPropertyCompat floatPropertyCompat4 = floatPropertyCompat;
                                if (Intrinsics.areEqual(floatPropertyCompat3, floatPropertyCompat4) && z && !z2) {
                                    boolean z3 = Math.abs(f4) > 0.0f;
                                    float f5 = f;
                                    float f6 = f2;
                                    boolean z4 = f5 <= f3 && f3 <= f6;
                                    if (z3 || !z4) {
                                        PhysicsAnimator.SpringConfig springConfig2 = springConfig;
                                        springConfig2.startVelocity = f4;
                                        float f7 = springConfig2.finalPosition;
                                        WeakHashMap weakHashMap = PhysicsAnimatorKt.animators;
                                        if (f7 == -3.4028235E38f) {
                                            if (z3) {
                                                if (f4 >= 0.0f) {
                                                    f5 = f6;
                                                }
                                                springConfig2.finalPosition = f5;
                                            } else if (!z4) {
                                                if (f3 >= f5) {
                                                    f5 = f6;
                                                }
                                                springConfig2.finalPosition = f5;
                                            }
                                        }
                                        PhysicsAnimator.Companion companion = PhysicsAnimator.Companion;
                                        PhysicsAnimator physicsAnimator4 = physicsAnimator3;
                                        ArrayMap arrayMap2 = physicsAnimator4.springAnimations;
                                        Object obj7 = arrayMap2.get(floatPropertyCompat4);
                                        Object obj8 = obj7;
                                        if (obj7 == null) {
                                            SpringAnimation springAnimation3 = new SpringAnimation(obj6, floatPropertyCompat4);
                                            springAnimation3.addUpdateListener(new PhysicsAnimator$configureDynamicAnimation$1(physicsAnimator4, floatPropertyCompat4));
                                            springAnimation3.addEndListener(new PhysicsAnimator$configureDynamicAnimation$2(physicsAnimator4, floatPropertyCompat4, springAnimation3));
                                            arrayMap2.put(floatPropertyCompat4, springAnimation3);
                                            obj8 = springAnimation3;
                                        }
                                        SpringAnimation springAnimation4 = (SpringAnimation) obj8;
                                        springConfig2.applyToAnimation$frameworks__base__libs__WindowManager__Shell__shared__android_common__WindowManager_Shell_shared(springAnimation4);
                                        springAnimation4.start();
                                    }
                                }
                            }
                        };
                        physicsAnimator = physicsAnimator3;
                        arrayList2.add(0, endListener);
                    }
                }
                physicsAnimator2 = physicsAnimator;
                obj2 = obj;
            }
            Object obj6 = obj2;
            PhysicsAnimator physicsAnimator4 = physicsAnimator2;
            ArrayList arrayList3 = physicsAnimator4.internalListeners;
            Set setKeySet3 = physicsAnimator4.springConfigs.keySet();
            Set setKeySet4 = physicsAnimator4.flingConfigs.keySet();
            Set mutableSet2 = CollectionsKt___CollectionsKt.toMutableSet(setKeySet3);
            CollectionsKt__MutableCollectionsKt.addAll(setKeySet4, mutableSet2);
            arrayList3.add(physicsAnimator4.new InternalListener(obj6, mutableSet2, new ArrayList(physicsAnimator4.updateListeners), new ArrayList(physicsAnimator4.endListeners), new ArrayList(physicsAnimator4.endActions)));
            int size = arrayList.size();
            while (i < size) {
                Object obj7 = arrayList.get(i);
                i++;
                ((Function0) obj7).invoke();
            }
            physicsAnimator4.springConfigs.clear();
            physicsAnimator4.flingConfigs.clear();
            physicsAnimator4.updateListeners.clear();
            physicsAnimator4.endListeners.clear();
            physicsAnimator4.endActions.clear();
        }
        return Unit.INSTANCE;
    }
}
