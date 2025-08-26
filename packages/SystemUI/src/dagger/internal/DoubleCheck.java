package dagger.internal;

import dagger.Lazy;

/* loaded from: classes4.dex */
public final class DoubleCheck implements Provider, Lazy {
    public static final Object UNINITIALIZED = new Object();
    public volatile Object instance = UNINITIALIZED;
    public volatile Provider provider;

    private DoubleCheck(Provider provider) {
        this.provider = provider;
    }

    public static Lazy lazy(Provider provider) {
        if (provider instanceof Lazy) {
            return (Lazy) provider;
        }
        provider.getClass();
        return new DoubleCheck(provider);
    }

    public static Provider provider(Provider provider) {
        provider.getClass();
        return provider instanceof DoubleCheck ? provider : new DoubleCheck(provider);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        Object obj;
        Object obj2 = this.instance;
        Object obj3 = UNINITIALIZED;
        if (obj2 != obj3) {
            return obj2;
        }
        synchronized (this) {
            obj = this.instance;
            if (obj == obj3) {
                obj = this.provider.get();
                Object obj4 = this.instance;
                if (obj4 != obj3 && obj4 != obj) {
                    throw new IllegalStateException("Scoped provider was invoked recursively returning different results: " + obj4 + " & " + obj + ". This is likely due to a circular dependency.");
                }
                this.instance = obj;
                this.provider = null;
            }
        }
        return obj;
    }
}
