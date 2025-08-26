package dagger.internal;

/* loaded from: classes4.dex */
public final class Preconditions {
    private Preconditions() {
    }

    public static void checkBuilderRequirement(Class cls, Object obj) {
        if (obj != null) {
            return;
        }
        throw new IllegalStateException(cls.getCanonicalName() + " must be set");
    }
}
