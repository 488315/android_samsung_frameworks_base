package com.android.wm.shell.animation;

import android.util.ArrayMap;
import android.util.Log;
import androidx.dynamicanimation.animation.FlingAnimation;
import androidx.dynamicanimation.animation.FloatPropertyCompat;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import com.android.settingslib.udfps.UdfpsOverlayParams$$ExternalSyntheticOutline0;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.WeakHashMap;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt__MutableCollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class PhysicsAnimator {
    public static final Companion Companion = new Companion(null);
    public static final Function1 instanceConstructor = PhysicsAnimator$Companion$instanceConstructor$1.INSTANCE;
    public final Function1 cancelAction;
    public final SpringConfig defaultSpring;
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
            return "AnimationUpdate(value=" + this.value + ", velocity=" + this.velocity + ")";
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static PhysicsAnimator getInstance(Object obj) {
            WeakHashMap weakHashMap = PhysicsAnimatorKt.animators;
            if (!weakHashMap.containsKey(obj)) {
                weakHashMap.put(obj, ((PhysicsAnimator$Companion$instanceConstructor$1) PhysicsAnimator.instanceConstructor).invoke(obj));
            }
            return (PhysicsAnimator) weakHashMap.get(obj);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface EndListener {
        void onAnimationEnd(Object obj, FloatPropertyCompat floatPropertyCompat, boolean z, boolean z2, float f, float f2);
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
            ArrayMap arrayMap = this.undispatchedUpdates;
            if (arrayMap.size() < this.numPropertiesAnimating || arrayMap.size() <= 0) {
                return;
            }
            for (UpdateListener updateListener : this.updateListeners) {
                new ArrayMap(arrayMap);
                updateListener.onAnimationUpdateForProperty(this.target);
            }
            arrayMap.clear();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    public static final PhysicsAnimator getInstance(Object obj) {
        Companion.getClass();
        return Companion.getInstance(obj);
    }

    public final boolean arePropertiesAnimating(Set set) {
        if (set.isEmpty()) {
            return false;
        }
        Iterator it = set.iterator();
        while (it.hasNext()) {
            if (isPropertyAnimating((FloatPropertyCompat) it.next())) {
                return true;
            }
        }
        return false;
    }

    public final void cancel() {
        ArrayMap arrayMap = this.flingAnimations;
        int size = arrayMap.size();
        Function1 function1 = this.cancelAction;
        if (size > 0) {
            ((PhysicsAnimator$cancelAction$1) function1).invoke(arrayMap.keySet());
        }
        ArrayMap arrayMap2 = this.springAnimations;
        if (arrayMap2.size() > 0) {
            ((PhysicsAnimator$cancelAction$1) function1).invoke(arrayMap2.keySet());
        }
    }

    public final PhysicsAnimator flingThenSpring(FloatPropertyCompat floatPropertyCompat, float f, FlingConfig flingConfig, SpringConfig springConfig, boolean z) {
        float f2 = f;
        Object obj = this.weakTarget.get();
        if (obj == null) {
            Log.w("PhysicsAnimator", "Trying to animate a GC-ed target.");
            return this;
        }
        float f3 = flingConfig.min;
        float f4 = flingConfig.max;
        float f5 = flingConfig.startVelocity;
        float f6 = flingConfig.friction;
        FlingConfig flingConfig2 = new FlingConfig(f6, f3, f4, f5);
        SpringConfig springConfig2 = new SpringConfig(springConfig.stiffness, springConfig.dampingRatio, springConfig.startVelocity, springConfig.finalPosition);
        float f7 = f2 < 0.0f ? flingConfig.min : flingConfig.max;
        if (z) {
            if (f7 < Float.MAX_VALUE && f7 > -3.4028235E38f) {
                float f8 = f6 * 4.2f;
                float value = (f2 / f8) + floatPropertyCompat.getValue(obj);
                float f9 = flingConfig.min;
                float f10 = flingConfig.max;
                float f11 = (f9 + f10) / 2;
                if ((f2 < 0.0f && value > f11) || (f2 > 0.0f && value < f11)) {
                    if (value >= f11) {
                        f9 = f10;
                    }
                    if (f9 < Float.MAX_VALUE && f9 > -3.4028235E38f) {
                        spring(floatPropertyCompat, f9, f2, springConfig);
                        return this;
                    }
                }
                float value2 = f7 - floatPropertyCompat.getValue(obj);
                float f12 = f8 * value2;
                if (value2 > 0.0f && f2 >= 0.0f) {
                    f2 = Math.max(f12, f2);
                } else if (value2 < 0.0f && f2 <= 0.0f) {
                    f2 = Math.min(f12, f2);
                }
                flingConfig2.startVelocity = f2;
                springConfig2.finalPosition = f7;
                this.flingConfigs.put(floatPropertyCompat, flingConfig2);
                this.springConfigs.put(floatPropertyCompat, springConfig2);
                return this;
            }
        }
        flingConfig2.startVelocity = f2;
        this.flingConfigs.put(floatPropertyCompat, flingConfig2);
        this.springConfigs.put(floatPropertyCompat, springConfig2);
        return this;
    }

    public final boolean isPropertyAnimating(FloatPropertyCompat floatPropertyCompat) {
        SpringAnimation springAnimation = (SpringAnimation) this.springAnimations.get(floatPropertyCompat);
        if (!(springAnimation != null ? springAnimation.mRunning : false)) {
            FlingAnimation flingAnimation = (FlingAnimation) this.flingAnimations.get(floatPropertyCompat);
            if (!(flingAnimation != null ? flingAnimation.mRunning : false)) {
                return false;
            }
        }
        return true;
    }

    public final boolean isRunning() {
        Set keySet = this.springAnimations.keySet();
        Set keySet2 = this.flingAnimations.keySet();
        Set mutableSet = CollectionsKt___CollectionsKt.toMutableSet(keySet);
        CollectionsKt__MutableCollectionsKt.addAll(keySet2, mutableSet);
        return arePropertiesAnimating(mutableSet);
    }

    public final void spring(FloatPropertyCompat floatPropertyCompat, float f, float f2, SpringConfig springConfig) {
        WeakHashMap weakHashMap = PhysicsAnimatorKt.animators;
        this.springConfigs.put(floatPropertyCompat, new SpringConfig(springConfig.stiffness, springConfig.dampingRatio, f2, f));
    }

    public final void start() {
        ((PhysicsAnimator$startAction$1) this.startAction).invoke();
    }

    public final void withEndActions(Runnable... runnableArr) {
        ArrayList arrayList = this.endActions;
        List filterNotNull = ArraysKt___ArraysKt.filterNotNull(runnableArr);
        ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(filterNotNull, 10));
        Iterator it = ((ArrayList) filterNotNull).iterator();
        while (it.hasNext()) {
            arrayList2.add(new PhysicsAnimator$withEndActions$1$1((Runnable) it.next()));
        }
        arrayList.addAll(arrayList2);
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
            return Float.hashCode(this.startVelocity) + UdfpsOverlayParams$$ExternalSyntheticOutline0.m90m(this.max, UdfpsOverlayParams$$ExternalSyntheticOutline0.m90m(this.min, Float.hashCode(this.friction) * 31, 31), 31);
        }

        public final String toString() {
            return "FlingConfig(friction=" + this.friction + ", min=" + this.min + ", max=" + this.max + ", startVelocity=" + this.startVelocity + ")";
        }

        /* JADX WARN: Illegal instructions before constructor call */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public FlingConfig(float f) {
            this(f, r0.min, r0.max);
            FlingConfig flingConfig = PhysicsAnimatorKt.globalDefaultFling;
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SpringConfig {
        public final float dampingRatio;
        public float finalPosition;
        public float startVelocity;
        public final float stiffness;

        /* JADX WARN: Illegal instructions before constructor call */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public SpringConfig() {
            this(r0.stiffness, r0.dampingRatio);
            SpringConfig springConfig = PhysicsAnimatorKt.globalDefaultSpring;
        }

        /* renamed from: applyToAnimation$frameworks__base__libs__WindowManager__Shell__android_common__WindowManager_Shell */
        public final void m228xe3030f23(SpringAnimation springAnimation) {
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
            return Float.hashCode(this.finalPosition) + UdfpsOverlayParams$$ExternalSyntheticOutline0.m90m(this.startVelocity, UdfpsOverlayParams$$ExternalSyntheticOutline0.m90m(this.dampingRatio, Float.hashCode(this.stiffness) * 31, 31), 31);
        }

        public final String toString() {
            return "SpringConfig(stiffness=" + this.stiffness + ", dampingRatio=" + this.dampingRatio + ", startVelocity=" + this.startVelocity + ", finalPosition=" + this.finalPosition + ")";
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
        */
        public /* synthetic */ SpringConfig(float f, float f2, float f3, float f4, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(f, f2, f3, f4);
            f3 = (i & 4) != 0 ? 0.0f : f3;
            if ((i & 8) != 0) {
                WeakHashMap weakHashMap = PhysicsAnimatorKt.animators;
                f4 = -3.4028235E38f;
            }
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

    public final void cancel(FloatPropertyCompat... floatPropertyCompatArr) {
        ((PhysicsAnimator$cancelAction$1) this.cancelAction).invoke(ArraysKt___ArraysKt.toSet(floatPropertyCompatArr));
    }
}
