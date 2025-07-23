package dagger.internal;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class SingleCheck implements Provider {
    public static final Object UNINITIALIZED = new Object();
    public volatile Object instance = UNINITIALIZED;
    public volatile Provider provider;

    private SingleCheck(Provider provider) {
        this.provider = provider;
    }

    public static Provider provider(Provider provider) {
        return !(provider instanceof SingleCheck) ? provider instanceof DoubleCheck ? provider : new SingleCheck(provider) : provider;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        Object obj = this.instance;
        if (obj != UNINITIALIZED) {
            return obj;
        }
        Provider provider = this.provider;
        if (provider == null) {
            return this.instance;
        }
        Object obj2 = provider.get();
        this.instance = obj2;
        this.provider = null;
        return obj2;
    }
}
