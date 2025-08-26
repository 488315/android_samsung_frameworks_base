package dagger.internal;

/* loaded from: classes4.dex */
public final class Providers {
    private Providers() {
    }

    public static Provider asDaggerProvider(final javax.inject.Provider provider) {
        provider.getClass();
        return provider instanceof Provider ? (Provider) provider : new Provider() { // from class: dagger.internal.Providers.1
            @Override // javax.inject.Provider
            public final Object get() {
                return provider.get();
            }
        };
    }
}
