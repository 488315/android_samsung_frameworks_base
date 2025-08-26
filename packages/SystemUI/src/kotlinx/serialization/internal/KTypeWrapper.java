package kotlinx.serialization.internal;

import java.util.List;
import kotlin.jvm.internal.ClassBasedDeclarationContainer;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KClass;
import kotlin.reflect.KType;

/* loaded from: classes4.dex */
public final class KTypeWrapper implements KType {
    public final KType origin;

    public KTypeWrapper(KType kType) {
        this.origin = kType;
    }

    public final boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        KTypeWrapper kTypeWrapper = obj instanceof KTypeWrapper ? (KTypeWrapper) obj : null;
        if (!Intrinsics.areEqual(this.origin, kTypeWrapper != null ? kTypeWrapper.origin : null)) {
            return false;
        }
        KClass classifier = getClassifier();
        if (!(classifier instanceof KClass)) {
            return false;
        }
        KType kType = obj instanceof KType ? (KType) obj : null;
        KClass classifier2 = kType != null ? ((KTypeWrapper) kType).getClassifier() : null;
        if (classifier2 == null || !(classifier2 instanceof KClass)) {
            return false;
        }
        return Intrinsics.areEqual(((ClassBasedDeclarationContainer) classifier).getJClass(), ((ClassBasedDeclarationContainer) classifier2).getJClass());
    }

    public final List getArguments() {
        return ((KTypeWrapper) this.origin).getArguments();
    }

    public final KClass getClassifier() {
        return ((KTypeWrapper) this.origin).getClassifier();
    }

    public final int hashCode() {
        return this.origin.hashCode();
    }

    public final boolean isMarkedNullable() {
        return ((KTypeWrapper) this.origin).isMarkedNullable();
    }

    public final String toString() {
        return "KTypeWrapper: " + this.origin;
    }
}
