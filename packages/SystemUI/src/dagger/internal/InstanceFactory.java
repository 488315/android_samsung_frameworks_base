package dagger.internal;

import dagger.Lazy;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
