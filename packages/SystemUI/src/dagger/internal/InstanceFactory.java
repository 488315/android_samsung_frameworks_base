package dagger.internal;

import dagger.Lazy;

/* loaded from: classes4.dex */
public final class InstanceFactory implements Provider, Lazy {
    public final Object instance;

    static {
        new InstanceFactory(null);
    }

    private InstanceFactory(Object obj) {
        this.instance = obj;
    }

    public static InstanceFactory create(Object obj) {
        obj.getClass();
        return new InstanceFactory(obj);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return this.instance;
    }
}
