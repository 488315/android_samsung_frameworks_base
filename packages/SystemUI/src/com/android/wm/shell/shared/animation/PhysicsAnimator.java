package com.android.wm.shell.shared.animation;

import android.util.ArrayMap;
import android.util.Log;
import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import androidx.compose.foundation.shape.DpCornerSize$$ExternalSyntheticOutline0;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.FlingAnimation;
import androidx.dynamicanimation.animation.FloatPropertyCompat;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.WeakHashMap;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt__MutableCollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class PhysicsAnimator {
    public static final Companion Companion = new Companion(null);
    public static final PhysicsAnimator$$ExternalSyntheticLambda1 onAnimatorCreated = new PhysicsAnimator$$ExternalSyntheticLambda1();
    public final Function1 cancelAction;
    public SpringConfig defaultSpring;
    public final ArrayList endActions;
    public final ArrayList endListeners;
    public final ArrayMap flingAnimations;
    public final ArrayMap flingConfigs;
    public final ArrayList internalListeners;
    public final ArrayMap springAnimations;
    public final ArrayMap springConfigs;
    public final Function0 startAction;
    public final ArrayList updateListeners;
    public final WeakReference weakTarget;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class AnimationUpdate {
        public final float value;
        public final float velocity;

        public AnimationUpdate(float f, float f2) {
            this.value = f;
            this.velocity = f2;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof AnimationUpdate)) {
                return false;
            }
            AnimationUpdate animationUpdate = (AnimationUpdate) obj;
            return Float.compare(this.value, animationUpdate.value) == 0 && Float.compare(this.velocity, animationUpdate.velocity) == 0;
        }

        public final int hashCode() {
            return Float.hashCode(this.velocity) + (Float.hashCode(this.value) * 31);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("AnimationUpdate(value=");
            sb.append(this.value);
            sb.append(", velocity=");
            return DpCornerSize$$ExternalSyntheticOutline0.m(this.velocity, ")", sb);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static PhysicsAnimator getInstance(Object obj) {
            WeakHashMap weakHashMap = PhysicsAnimatorKt.animators;
            if (!weakHashMap.containsKey(obj)) {
                PhysicsAnimator physicsAnimator = new PhysicsAnimator(obj, null);
                PhysicsAnimator.onAnimatorCreated.getClass();
                Unit unit = Unit.INSTANCE;
                weakHashMap.put(obj, physicsAnimator);
            }
            return (PhysicsAnimator) weakHashMap.get(obj);
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface EndListener {
        void onAnimationEnd(Object obj, FloatPropertyCompat floatPropertyCompat, boolean z, boolean z2, float f, float f2);
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class InternalListener {
        public final List endActions;
        public final List endListeners;
        public int numPropertiesAnimating;
        public final Set properties;
        public final Object target;
        public final ArrayMap undispatchedUpdates = new ArrayMap();
        public final List updateListeners;

        public InternalListener(Object obj, Set<? extends FloatPropertyCompat> set, List<? extends UpdateListener> list, List<? extends EndListener> list2, List<? extends Function0> list3) {
            this.target = obj;
            this.properties = set;
            this.updateListeners = list;
            this.endListeners = list2;
            this.endActions = list3;
            this.numPropertiesAnimating = set.size();
        }

        public final void maybeDispatchUpdates() {
            if (this.undispatchedUpdates.size() < this.numPropertiesAnimating || this.undispatchedUpdates.size() <= 0) {
                return;
            }
            for (UpdateListener updateListener : this.updateListeners) {
                new ArrayMap(this.undispatchedUpdates);
                updateListener.onAnimationUpdateForProperty(this.target);
            }
            this.undispatchedUpdates.clear();
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface UpdateListener {
        void onAnimationUpdateForProperty(Object obj);
    }

    public /* synthetic */ PhysicsAnimator(Object obj, DefaultConstructorMarker defaultConstructorMarker) {
        this(obj);
    }

    public static final float estimateFlingEndValue(float f, float f2, FlingConfig flingConfig) {
        Companion.getClass();
        return Math.min(flingConfig.max, Math.max(flingConfig.min, f + (f2 / (flingConfig.friction * 4.2f))));
    }

    public final boolean arePropertiesAnimating(Set set) {
        Set set2 = set;
        if ((set2 instanceof Collection) && set2.isEmpty()) {
            return false;
        }
        Iterator it = set2.iterator();
        while (it.hasNext()) {
            if (isPropertyAnimating((FloatPropertyCompat) it.next())) {
                return true;
            }
        }
        return false;
    }

    public final void cancel() {
        int size = this.flingAnimations.size();
        Function1 function1 = this.cancelAction;
        if (size > 0) {
            ((PhysicsAnimator$cancelAction$1) function1).mo779invoke(this.flingAnimations.keySet());
        }
        if (this.springAnimations.size() > 0) {
            ((PhysicsAnimator$cancelAction$1) function1).mo779invoke(this.springAnimations.keySet());
        }
    }

    public final void flingThenSpring(FloatPropertyCompat floatPropertyCompat, float f, FlingConfig flingConfig, SpringConfig springConfig, boolean z) {
        float f2 = f;
        Object obj = this.weakTarget.get();
        if (obj == null) {
            Log.w("PhysicsAnimator", "Trying to animate a GC-ed target.");
            return;
        }
        FlingConfig flingConfig2 = new FlingConfig(flingConfig.friction, flingConfig.min, flingConfig.max, flingConfig.startVelocity);
        SpringConfig springConfig2 = new SpringConfig(springConfig.stiffness, springConfig.dampingRatio, springConfig.startVelocity, springConfig.finalPosition);
        float f3 = f2 < 0.0f ? flingConfig.min : flingConfig.max;
        if (!z || f3 >= Float.MAX_VALUE || f3 <= -3.4028235E38f) {
            flingConfig2.startVelocity = f2;
        } else {
            float value = floatPropertyCompat.getValue(obj);
            float f4 = flingConfig.friction * 4.2f;
            float f5 = (f2 / f4) + value;
            float f6 = flingConfig.min;
            float f7 = flingConfig.max;
            float f8 = (f6 + f7) / 2;
            if ((f2 < 0.0f && f5 > f8) || (f2 > 0.0f && f5 < f8)) {
                if (f5 >= f8) {
                    f6 = f7;
                }
                if (f6 < Float.MAX_VALUE && f6 > -3.4028235E38f) {
                    spring(floatPropertyCompat, f6, f2, springConfig);
                    return;
                }
            }
            float value2 = f3 - floatPropertyCompat.getValue(obj);
            float f9 = f4 * value2;
            if (value2 > 0.0f && f2 >= 0.0f) {
                f2 = Math.max(f9, f2);
            } else if (value2 < 0.0f && f2 <= 0.0f) {
                f2 = Math.min(f9, f2);
            }
            flingConfig2.startVelocity = f2;
            springConfig2.finalPosition = f3;
        }
        this.flingConfigs.put(floatPropertyCompat, flingConfig2);
        this.springConfigs.put(floatPropertyCompat, springConfig2);
    }

    public final boolean isPropertyAnimating(FloatPropertyCompat floatPropertyCompat) {
        SpringAnimation springAnimation = (SpringAnimation) this.springAnimations.get(floatPropertyCompat);
        if (springAnimation != null ? springAnimation.mRunning : false) {
            return true;
        }
        FlingAnimation flingAnimation = (FlingAnimation) this.flingAnimations.get(floatPropertyCompat);
        return flingAnimation != null ? flingAnimation.mRunning : false;
    }

    public final boolean isRunning() {
        Set keySet = this.springAnimations.keySet();
        Set keySet2 = this.flingAnimations.keySet();
        Set mutableSet = CollectionsKt___CollectionsKt.toMutableSet(keySet);
        CollectionsKt__MutableCollectionsKt.addAll(keySet2, mutableSet);
        return arePropertiesAnimating(mutableSet);
    }

    public final void spring(DynamicAnimation.ViewProperty viewProperty, float f) {
        spring(viewProperty, f, 0.0f, this.defaultSpring);
    }

    public final void start() {
        ((PhysicsAnimator$startAction$1) this.startAction).invoke();
    }

    public final void withEndActions(Function0... function0Arr) {
        this.endActions.addAll(ArraysKt___ArraysKt.filterNotNull(function0Arr));
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class FlingConfig {
        public final float friction;
        public float max;
        public float min;
        public float startVelocity;

        public FlingConfig() {
            this(PhysicsAnimatorKt.globalDefaultFling.friction);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof FlingConfig)) {
                return false;
            }
            FlingConfig flingConfig = (FlingConfig) obj;
            return Float.compare(this.friction, flingConfig.friction) == 0 && Float.compare(this.min, flingConfig.min) == 0 && Float.compare(this.max, flingConfig.max) == 0 && Float.compare(this.startVelocity, flingConfig.startVelocity) == 0;
        }

        public final int hashCode() {
            return Float.hashCode(this.startVelocity) + FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.max, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.min, Float.hashCode(this.friction) * 31, 31), 31);
        }

        public final String toString() {
            return "FlingConfig(friction=" + this.friction + ", min=" + this.min + ", max=" + this.max + ", startVelocity=" + this.startVelocity + ")";
        }

        /* JADX WARN: Illegal instructions before constructor call */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public FlingConfig(float r3) {
            /*
                r2 = this;
                com.android.wm.shell.shared.animation.PhysicsAnimator$FlingConfig r0 = com.android.wm.shell.shared.animation.PhysicsAnimatorKt.globalDefaultFling
                float r1 = r0.min
                float r0 = r0.max
                r2.<init>(r3, r1, r0)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.shared.animation.PhysicsAnimator.FlingConfig.<init>(float):void");
        }

        public FlingConfig(float f, float f2, float f3, float f4) {
            this.friction = f;
            this.min = f2;
            this.max = f3;
            this.startVelocity = f4;
        }

        public FlingConfig(float f, float f2, float f3) {
            this(f, f2, f3, 0.0f);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class SpringConfig {
        public final float dampingRatio;
        public float finalPosition;
        public float startVelocity;
        public final float stiffness;

        /* JADX WARN: Illegal instructions before constructor call */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public SpringConfig() {
            /*
                r2 = this;
                com.android.wm.shell.shared.animation.PhysicsAnimator$SpringConfig r0 = com.android.wm.shell.shared.animation.PhysicsAnimatorKt.globalDefaultSpring
                float r1 = r0.stiffness
                float r0 = r0.dampingRatio
                r2.<init>(r1, r0)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.shared.animation.PhysicsAnimator.SpringConfig.<init>():void");
        }

        public final void applyToAnimation$frameworks__base__libs__WindowManager__Shell__shared__android_common__WindowManager_Shell_shared(SpringAnimation springAnimation) {
            SpringForce springForce = springAnimation.mSpring;
            if (springForce == null) {
                springForce = new SpringForce();
            }
            springForce.setStiffness(this.stiffness);
            springForce.setDampingRatio(this.dampingRatio);
            springForce.mFinalPosition = this.finalPosition;
            springAnimation.mSpring = springForce;
            float f = this.startVelocity;
            if (f == 0.0f) {
                return;
            }
            springAnimation.mVelocity = f;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof SpringConfig)) {
                return false;
            }
            SpringConfig springConfig = (SpringConfig) obj;
            return Float.compare(this.stiffness, springConfig.stiffness) == 0 && Float.compare(this.dampingRatio, springConfig.dampingRatio) == 0 && Float.compare(this.startVelocity, springConfig.startVelocity) == 0 && Float.compare(this.finalPosition, springConfig.finalPosition) == 0;
        }

        public final int hashCode() {
            return Float.hashCode(this.finalPosition) + FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.startVelocity, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.dampingRatio, Float.hashCode(this.stiffness) * 31, 31), 31);
        }

        public final String toString() {
            float f = this.startVelocity;
            float f2 = this.finalPosition;
            StringBuilder sb = new StringBuilder("SpringConfig(stiffness=");
            sb.append(this.stiffness);
            sb.append(", dampingRatio=");
            sb.append(this.dampingRatio);
            sb.append(", startVelocity=");
            sb.append(f);
            sb.append(", finalPosition=");
            return DpCornerSize$$ExternalSyntheticOutline0.m(f2, ")", sb);
        }

        public SpringConfig(float f, float f2, float f3, float f4) {
            this.stiffness = f;
            this.dampingRatio = f2;
            this.startVelocity = f3;
            this.finalPosition = f4;
        }

        /* JADX WARN: Illegal instructions before constructor call */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public /* synthetic */ SpringConfig(float r1, float r2, float r3, float r4, int r5, kotlin.jvm.internal.DefaultConstructorMarker r6) {
            /*
                r0 = this;
                r6 = r5 & 4
                if (r6 == 0) goto L5
                r3 = 0
            L5:
                r5 = r5 & 8
                if (r5 == 0) goto Le
                java.util.WeakHashMap r4 = com.android.wm.shell.shared.animation.PhysicsAnimatorKt.animators
                r4 = -8388609(0xffffffffff7fffff, float:-3.4028235E38)
            Le:
                r0.<init>(r1, r2, r3, r4)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.shared.animation.PhysicsAnimator.SpringConfig.<init>(float, float, float, float, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
        }

        public SpringConfig(float f, float f2) {
            this(f, f2, 0.0f, 0.0f, 8, null);
        }
    }

    private PhysicsAnimator(Object obj) {
        this.weakTarget = new WeakReference(obj);
        this.springAnimations = new ArrayMap();
        this.flingAnimations = new ArrayMap();
        this.springConfigs = new ArrayMap();
        this.flingConfigs = new ArrayMap();
        this.updateListeners = new ArrayList();
        this.endListeners = new ArrayList();
        this.endActions = new ArrayList();
        this.defaultSpring = PhysicsAnimatorKt.globalDefaultSpring;
        this.internalListeners = new ArrayList();
        this.startAction = new PhysicsAnimator$startAction$1(this);
        this.cancelAction = new PhysicsAnimator$cancelAction$1(this);
    }

    public final void withEndActions(Runnable... runnableArr) {
        ArrayList arrayList = this.endActions;
        List filterNotNull = ArraysKt___ArraysKt.filterNotNull(runnableArr);
        ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(filterNotNull, 10));
        Iterator it = filterNotNull.iterator();
        while (it.hasNext()) {
            arrayList2.add(new PhysicsAnimator$withEndActions$1$1((Runnable) it.next()));
        }
        arrayList.addAll(arrayList2);
    }

    public final void spring(FloatPropertyCompat floatPropertyCompat, float f, float f2, SpringConfig springConfig) {
        float f3 = springConfig.stiffness;
        WeakHashMap weakHashMap = PhysicsAnimatorKt.animators;
        this.springConfigs.put(floatPropertyCompat, new SpringConfig(f3, springConfig.dampingRatio, f2, f));
    }

    public final void cancel(FloatPropertyCompat... floatPropertyCompatArr) {
        ((PhysicsAnimator$cancelAction$1) this.cancelAction).mo779invoke(ArraysKt___ArraysKt.toSet(floatPropertyCompatArr));
    }
}
