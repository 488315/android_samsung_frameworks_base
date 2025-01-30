package dagger.internal;

import dagger.Lazy;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class InstanceFactory implements Provider, Lazy {
    public final Object instance;

    static {
        new InstanceFactory(null);
    }

    private InstanceFactory(Object obj) {
        this.instance = obj;
    }

    public static InstanceFactory create(Object obj) {
        if (obj != null) {
            return new InstanceFactory(obj);
        }
        throw new NullPointerException("instance cannot be null");
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return this.instance;
    }
}
