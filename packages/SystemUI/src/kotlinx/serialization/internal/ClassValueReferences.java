package kotlinx.serialization.internal;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class ClassValueReferences extends ClassValue {
    @Override // java.lang.ClassValue
    public final Object computeValue(Class cls) {
        return new MutableSoftReference();
    }
}
