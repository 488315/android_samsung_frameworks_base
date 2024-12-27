package com.android.systemui.util.ui;

import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public interface AnimatedValue<T> {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Animating<T> implements AnimatedValue<T> {
        public static final int $stable = 0;
        private final Function0 onStopAnimating;
        private final T value;

        public Animating(T t, Function0 function0) {
            this.value = t;
            this.onStopAnimating = function0;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ Animating copy$default(Animating animating, Object obj, Function0 function0, int i, Object obj2) {
            if ((i & 1) != 0) {
                obj = animating.value;
            }
            if ((i & 2) != 0) {
                function0 = animating.onStopAnimating;
            }
            return animating.copy(obj, function0);
        }

        public final T component1() {
            return this.value;
        }

        public final Function0 component2() {
            return this.onStopAnimating;
        }

        public final Animating<T> copy(T t, Function0 function0) {
            return new Animating<>(t, function0);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Animating)) {
                return false;
            }
            Animating animating = (Animating) obj;
            return Intrinsics.areEqual(this.value, animating.value) && Intrinsics.areEqual(this.onStopAnimating, animating.onStopAnimating);
        }

        public final Function0 getOnStopAnimating() {
            return this.onStopAnimating;
        }

        public final T getValue() {
            return this.value;
        }

        public int hashCode() {
            T t = this.value;
            return this.onStopAnimating.hashCode() + ((t == null ? 0 : t.hashCode()) * 31);
        }

        public String toString() {
            return "Animating(value=" + this.value + ", onStopAnimating=" + this.onStopAnimating + ")";
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class NotAnimating<T> implements AnimatedValue<T> {
        public static final int $stable = 0;
        private final T value;

        public NotAnimating(T t) {
            this.value = t;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ NotAnimating copy$default(NotAnimating notAnimating, Object obj, int i, Object obj2) {
            if ((i & 1) != 0) {
                obj = notAnimating.value;
            }
            return notAnimating.copy(obj);
        }

        public final T component1() {
            return this.value;
        }

        public final NotAnimating<T> copy(T t) {
            return new NotAnimating<>(t);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof NotAnimating) && Intrinsics.areEqual(this.value, ((NotAnimating) obj).value);
        }

        public final T getValue() {
            return this.value;
        }

        public int hashCode() {
            T t = this.value;
            if (t == null) {
                return 0;
            }
            return t.hashCode();
        }

        public String toString() {
            return "NotAnimating(value=" + this.value + ")";
        }
    }
}
