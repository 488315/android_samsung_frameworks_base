package kotlin.jvm.internal;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class PackageReference implements ClassBasedDeclarationContainer {
    public final Class jClass;

    public PackageReference(Class<?> cls, String str) {
        this.jClass = cls;
    }

    public final boolean equals(Object obj) {
        return (obj instanceof PackageReference) && Intrinsics.areEqual(this.jClass, ((PackageReference) obj).jClass);
    }

    @Override // kotlin.jvm.internal.ClassBasedDeclarationContainer
    public final Class getJClass() {
        return this.jClass;
    }

    public final int hashCode() {
        return this.jClass.hashCode();
    }

    public final String toString() {
        return this.jClass + " (Kotlin reflection is not available)";
    }
}
