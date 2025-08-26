package kotlinx.serialization.internal;

/* loaded from: classes4.dex */
public final class ClassValueReferences extends ClassValue {
    @Override // java.lang.ClassValue
    public final Object computeValue(Class cls) {
        return new MutableSoftReference();
    }
}
