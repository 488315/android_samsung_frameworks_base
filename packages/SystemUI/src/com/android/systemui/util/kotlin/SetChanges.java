package com.android.systemui.util.kotlin;

import java.util.Set;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SetChanges<T> {
    public static final int $stable = 8;
    private final Set<T> added;
    private final Set<T> removed;

    /* JADX WARN: Multi-variable type inference failed */
    public SetChanges(Set<? extends T> set, Set<? extends T> set2) {
        this.removed = set;
        this.added = set2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ SetChanges copy$default(SetChanges setChanges, Set set, Set set2, int i, Object obj) {
        if ((i & 1) != 0) {
            set = setChanges.removed;
        }
        if ((i & 2) != 0) {
            set2 = setChanges.added;
        }
        return setChanges.copy(set, set2);
    }

    public final Set<T> component1() {
        return this.removed;
    }

    public final Set<T> component2() {
        return this.added;
    }

    public final SetChanges<T> copy(Set<? extends T> set, Set<? extends T> set2) {
        return new SetChanges<>(set, set2);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SetChanges)) {
            return false;
        }
        SetChanges setChanges = (SetChanges) obj;
        return Intrinsics.areEqual(this.removed, setChanges.removed) && Intrinsics.areEqual(this.added, setChanges.added);
    }

    public final Set<T> getAdded() {
        return this.added;
    }

    public final Set<T> getRemoved() {
        return this.removed;
    }

    public int hashCode() {
        return this.added.hashCode() + (this.removed.hashCode() * 31);
    }

    public String toString() {
        return "SetChanges(removed=" + this.removed + ", added=" + this.added + ")";
    }
}
