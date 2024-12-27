package com.android.systemui.util.ui;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class AnimatableEvent<T> {
    public static final int $stable = 0;
    private final boolean startAnimating;
    private final T value;

    public AnimatableEvent(T t, boolean z) {
        this.value = t;
        this.startAnimating = z;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ AnimatableEvent copy$default(AnimatableEvent animatableEvent, Object obj, boolean z, int i, Object obj2) {
        if ((i & 1) != 0) {
            obj = animatableEvent.value;
        }
        if ((i & 2) != 0) {
            z = animatableEvent.startAnimating;
        }
        return animatableEvent.copy(obj, z);
    }

    public final T component1() {
        return this.value;
    }

    public final boolean component2() {
        return this.startAnimating;
    }

    public final AnimatableEvent<T> copy(T t, boolean z) {
        return new AnimatableEvent<>(t, z);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AnimatableEvent)) {
            return false;
        }
        AnimatableEvent animatableEvent = (AnimatableEvent) obj;
        return Intrinsics.areEqual(this.value, animatableEvent.value) && this.startAnimating == animatableEvent.startAnimating;
    }

    public final boolean getStartAnimating() {
        return this.startAnimating;
    }

    public final T getValue() {
        return this.value;
    }

    public int hashCode() {
        T t = this.value;
        return Boolean.hashCode(this.startAnimating) + ((t == null ? 0 : t.hashCode()) * 31);
    }

    public String toString() {
        return "AnimatableEvent(value=" + this.value + ", startAnimating=" + this.startAnimating + ")";
    }
}
