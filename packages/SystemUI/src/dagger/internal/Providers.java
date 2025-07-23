package dagger.internal;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class Providers {
    private Providers() {
    }

    public static Provider asDaggerProvider(final javax.inject.Provider provider) {
        provider.getClass();
        return provider instanceof Provider ? (Provider) provider : new Provider() { // from class: dagger.internal.Providers.1
            @Override // javax.inject.Provider
            public final Object get() {
                return javax.inject.Provider.this.get();
            }
        };
    }
}
