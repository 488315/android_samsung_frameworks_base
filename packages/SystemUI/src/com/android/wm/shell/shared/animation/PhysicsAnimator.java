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

    public interface EndListener {
        void onAnimationEnd(Object obj, FloatPropertyCompat floatPropertyCompat, boolean z, boolean z2, float f, float f2);
    }

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
            ((PhysicsAnimator$cancelAction$1) function1).mo781invoke(this.flingAnimations.keySet());
        }
        if (this.springAnimations.size() > 0) {
            ((PhysicsAnimator$cancelAction$1) function1).mo781invoke(this.springAnimations.keySet());
        }
    }

    public final void flingThenSpring(FloatPropertyCompat floatPropertyCompat, float f, FlingConfig flingConfig, SpringConfig springConfig, boolean z) {
        float fMin = f;
        Object obj = this.weakTarget.get();
        if (obj == null) {
            Log.w("PhysicsAnimator", "Trying to animate a GC-ed target.");
            return;
        }
        FlingConfig flingConfig2 = new FlingConfig(flingConfig.friction, flingConfig.min, flingConfig.max, flingConfig.startVelocity);
        SpringConfig springConfig2 = new SpringConfig(springConfig.stiffness, springConfig.dampingRatio, springConfig.startVelocity, springConfig.finalPosition);
        float f2 = fMin < 0.0f ? flingConfig.min : flingConfig.max;
        if (!z || f2 >= Float.MAX_VALUE || f2 <= -3.4028235E38f) {
            flingConfig2.startVelocity = fMin;
        } else {
            float value = floatPropertyCompat.getValue(obj);
            float f3 = flingConfig.friction * 4.2f;
            float f4 = (fMin / f3) + value;
            float f5 = flingConfig.min;
            float f6 = flingConfig.max;
            float f7 = (f5 + f6) / 2;
            if ((fMin < 0.0f && f4 > f7) || (fMin > 0.0f && f4 < f7)) {
                if (f4 >= f7) {
                    f5 = f6;
                }
                if (f5 < Float.MAX_VALUE && f5 > -3.4028235E38f) {
                    spring(floatPropertyCompat, f5, fMin, springConfig);
                    return;
                }
            }
            float value2 = f2 - floatPropertyCompat.getValue(obj);
            float f8 = f3 * value2;
            if (value2 > 0.0f && fMin >= 0.0f) {
                fMin = Math.max(f8, fMin);
            } else if (value2 < 0.0f && fMin <= 0.0f) {
                fMin = Math.min(f8, fMin);
            }
            flingConfig2.startVelocity = fMin;
            springConfig2.finalPosition = f2;
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
        Set setKeySet = this.springAnimations.keySet();
        Set setKeySet2 = this.flingAnimations.keySet();
        Set mutableSet = CollectionsKt___CollectionsKt.toMutableSet(setKeySet);
        CollectionsKt__MutableCollectionsKt.addAll(setKeySet2, mutableSet);
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
        public FlingConfig(float f) {
            FlingConfig flingConfig = PhysicsAnimatorKt.globalDefaultFling;
            this(f, flingConfig.min, flingConfig.max);
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

    public final class SpringConfig {
        public final float dampingRatio;
        public float finalPosition;
        public float startVelocity;
        public final float stiffness;

        /* JADX WARN: Illegal instructions before constructor call */
        public SpringConfig() {
            SpringConfig springConfig = PhysicsAnimatorKt.globalDefaultSpring;
            this(springConfig.stiffness, springConfig.dampingRatio);
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
        public /* synthetic */ SpringConfig(float f, float f2, float f3, float f4, int i, DefaultConstructorMarker defaultConstructorMarker) {
            f3 = (i & 4) != 0 ? 0.0f : f3;
            if ((i & 8) != 0) {
                WeakHashMap weakHashMap = PhysicsAnimatorKt.animators;
                f4 = -3.4028235E38f;
            }
            this(f, f2, f3, f4);
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
        List listFilterNotNull = ArraysKt___ArraysKt.filterNotNull(runnableArr);
        ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(listFilterNotNull, 10));
        Iterator it = listFilterNotNull.iterator();
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
        ((PhysicsAnimator$cancelAction$1) this.cancelAction).mo781invoke(ArraysKt___ArraysKt.toSet(floatPropertyCompatArr));
    }
}
