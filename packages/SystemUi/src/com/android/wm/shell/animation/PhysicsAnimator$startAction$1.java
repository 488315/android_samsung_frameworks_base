package com.android.wm.shell.animation;

import android.util.ArrayMap;
import android.util.Log;
import androidx.dynamicanimation.animation.FlingAnimation;
import androidx.dynamicanimation.animation.FloatPropertyCompat;
import androidx.dynamicanimation.animation.SpringAnimation;
import com.android.wm.shell.animation.PhysicsAnimator;
import com.android.wm.shell.animation.PhysicsAnimator.InternalListener;
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

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public /* synthetic */ class PhysicsAnimator$startAction$1 extends FunctionReferenceImpl implements Function0 {
    public PhysicsAnimator$startAction$1(Object obj) {
        super(0, obj, PhysicsAnimator.class, "startInternal", "startInternal$frameworks__base__libs__WindowManager__Shell__android_common__WindowManager_Shell()V", 0);
    }

    @Override // kotlin.jvm.functions.Function0
    public final /* bridge */ /* synthetic */ Object invoke() {
        invoke$3();
        return Unit.INSTANCE;
    }

    public final void invoke$3() {
        ArrayList arrayList;
        Iterator it;
        final PhysicsAnimator physicsAnimator = (PhysicsAnimator) this.receiver;
        final Object obj = physicsAnimator.weakTarget.get();
        if (obj == null) {
            Log.w("PhysicsAnimator", "Trying to animate a GC-ed object.");
            return;
        }
        ArrayList arrayList2 = new ArrayList();
        ArrayMap arrayMap = physicsAnimator.springConfigs;
        Set keySet = arrayMap.keySet();
        ArrayMap arrayMap2 = physicsAnimator.flingConfigs;
        Set keySet2 = arrayMap2.keySet();
        Set mutableSet = CollectionsKt___CollectionsKt.toMutableSet(keySet);
        CollectionsKt__MutableCollectionsKt.addAll(keySet2, mutableSet);
        Iterator it2 = mutableSet.iterator();
        while (true) {
            boolean hasNext = it2.hasNext();
            arrayList = physicsAnimator.endListeners;
            if (!hasNext) {
                break;
            }
            final FloatPropertyCompat floatPropertyCompat = (FloatPropertyCompat) it2.next();
            final PhysicsAnimator.FlingConfig flingConfig = (PhysicsAnimator.FlingConfig) arrayMap2.get(floatPropertyCompat);
            final PhysicsAnimator.SpringConfig springConfig = (PhysicsAnimator.SpringConfig) arrayMap.get(floatPropertyCompat);
            final float value = floatPropertyCompat.getValue(obj);
            if (flingConfig != null) {
                it = it2;
                arrayList2.add(new Function0() { // from class: com.android.wm.shell.animation.PhysicsAnimator$startInternal$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        PhysicsAnimator.FlingConfig flingConfig2 = PhysicsAnimator.FlingConfig.this;
                        float f = value;
                        flingConfig2.min = Math.min(f, flingConfig2.min);
                        flingConfig2.max = Math.max(f, flingConfig2.max);
                        physicsAnimator.cancel(floatPropertyCompat);
                        PhysicsAnimator physicsAnimator2 = physicsAnimator;
                        FloatPropertyCompat floatPropertyCompat2 = floatPropertyCompat;
                        Object obj2 = obj;
                        ArrayMap arrayMap3 = physicsAnimator2.flingAnimations;
                        Object obj3 = arrayMap3.get(floatPropertyCompat2);
                        Object obj4 = obj3;
                        if (obj3 == null) {
                            FlingAnimation flingAnimation = new FlingAnimation(obj2, floatPropertyCompat2);
                            flingAnimation.addUpdateListener(new PhysicsAnimator$configureDynamicAnimation$1(physicsAnimator2, floatPropertyCompat2));
                            flingAnimation.addEndListener(new PhysicsAnimator$configureDynamicAnimation$2(physicsAnimator2, floatPropertyCompat2, flingAnimation));
                            arrayMap3.put(floatPropertyCompat2, flingAnimation);
                            obj4 = flingAnimation;
                        }
                        FlingAnimation flingAnimation2 = (FlingAnimation) obj4;
                        PhysicsAnimator.FlingConfig flingConfig3 = PhysicsAnimator.FlingConfig.this;
                        float f2 = flingConfig3.friction;
                        if (f2 <= 0.0f) {
                            throw new IllegalArgumentException("Friction must be positive");
                        }
                        flingAnimation2.mFlingForce.mFriction = f2 * (-4.2f);
                        flingAnimation2.mMinValue = flingConfig3.min;
                        flingAnimation2.mMaxValue = flingConfig3.max;
                        flingAnimation2.mVelocity = flingConfig3.startVelocity;
                        flingAnimation2.start();
                        return Unit.INSTANCE;
                    }
                });
            } else {
                it = it2;
            }
            if (springConfig != null) {
                if (flingConfig == null) {
                    ArrayMap arrayMap3 = physicsAnimator.springAnimations;
                    Object obj2 = arrayMap3.get(floatPropertyCompat);
                    Object obj3 = obj2;
                    if (obj2 == null) {
                        SpringAnimation springAnimation = new SpringAnimation(obj, floatPropertyCompat);
                        springAnimation.addUpdateListener(new PhysicsAnimator$configureDynamicAnimation$1(physicsAnimator, floatPropertyCompat));
                        springAnimation.addEndListener(new PhysicsAnimator$configureDynamicAnimation$2(physicsAnimator, floatPropertyCompat, springAnimation));
                        arrayMap3.put(floatPropertyCompat, springAnimation);
                        obj3 = springAnimation;
                    }
                    SpringAnimation springAnimation2 = (SpringAnimation) obj3;
                    springConfig.m228xe3030f23(springAnimation2);
                    arrayList2.add(new PhysicsAnimator$startInternal$2(springAnimation2));
                } else {
                    final float f = flingConfig.min;
                    final float f2 = flingConfig.max;
                    arrayList.add(0, new PhysicsAnimator.EndListener() { // from class: com.android.wm.shell.animation.PhysicsAnimator$startInternal$3
                        @Override // com.android.wm.shell.animation.PhysicsAnimator.EndListener
                        public final void onAnimationEnd(Object obj4, FloatPropertyCompat floatPropertyCompat2, boolean z, boolean z2, float f3, float f4) {
                            FloatPropertyCompat floatPropertyCompat3 = FloatPropertyCompat.this;
                            if (Intrinsics.areEqual(floatPropertyCompat2, floatPropertyCompat3) && z && !z2) {
                                boolean z3 = Math.abs(f4) > 0.0f;
                                float f5 = f;
                                float f6 = f2;
                                boolean z4 = !(f5 <= f3 && f3 <= f6);
                                if (z3 || z4) {
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
                                        } else if (z4) {
                                            if (f3 >= f5) {
                                                f5 = f6;
                                            }
                                            springConfig2.finalPosition = f5;
                                        }
                                    }
                                    PhysicsAnimator.Companion companion = PhysicsAnimator.Companion;
                                    PhysicsAnimator physicsAnimator2 = physicsAnimator;
                                    ArrayMap arrayMap4 = physicsAnimator2.springAnimations;
                                    Object obj5 = arrayMap4.get(floatPropertyCompat3);
                                    Object obj6 = obj5;
                                    if (obj5 == null) {
                                        SpringAnimation springAnimation3 = new SpringAnimation(obj4, floatPropertyCompat3);
                                        springAnimation3.addUpdateListener(new PhysicsAnimator$configureDynamicAnimation$1(physicsAnimator2, floatPropertyCompat3));
                                        springAnimation3.addEndListener(new PhysicsAnimator$configureDynamicAnimation$2(physicsAnimator2, floatPropertyCompat3, springAnimation3));
                                        arrayMap4.put(floatPropertyCompat3, springAnimation3);
                                        obj6 = springAnimation3;
                                    }
                                    SpringAnimation springAnimation4 = (SpringAnimation) obj6;
                                    springConfig2.m228xe3030f23(springAnimation4);
                                    springAnimation4.start();
                                }
                            }
                        }
                    });
                }
            }
            it2 = it;
        }
        ArrayList arrayList3 = physicsAnimator.internalListeners;
        Set keySet3 = arrayMap.keySet();
        Set keySet4 = arrayMap2.keySet();
        Set mutableSet2 = CollectionsKt___CollectionsKt.toMutableSet(keySet3);
        CollectionsKt__MutableCollectionsKt.addAll(keySet4, mutableSet2);
        ArrayList arrayList4 = physicsAnimator.updateListeners;
        ArrayList arrayList5 = new ArrayList(arrayList4);
        ArrayList arrayList6 = new ArrayList(arrayList);
        ArrayList arrayList7 = physicsAnimator.endActions;
        arrayList3.add(physicsAnimator.new InternalListener(obj, mutableSet2, arrayList5, arrayList6, new ArrayList(arrayList7)));
        Iterator it3 = arrayList2.iterator();
        while (it3.hasNext()) {
            ((Function0) it3.next()).invoke();
        }
        arrayMap.clear();
        arrayMap2.clear();
        arrayList4.clear();
        arrayList.clear();
        arrayList7.clear();
    }
}
