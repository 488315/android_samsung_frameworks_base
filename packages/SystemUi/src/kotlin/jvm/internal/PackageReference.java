package kotlin.jvm.internal;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class PackageReference implements ClassBasedDeclarationContainer {
    public final Class jClass;

    public PackageReference(Class<?> cls, String str) {
        this.jClass = cls;
    }

    public final boolean equals(Object obj) {
        if (obj instanceof PackageReference) {
            if (Intrinsics.areEqual(this.jClass, ((PackageReference) obj).jClass)) {
                return true;
            }
        }
        return false;
    }

    @Override // kotlin.jvm.internal.ClassBasedDeclarationContainer
    public final Class getJClass() {
        return this.jClass;
    }

    public final int hashCode() {
        return this.jClass.hashCode();
    }

    public final String toString() {
        return this.jClass.toString() + " (Kotlin reflection is not available)";
    }
}
