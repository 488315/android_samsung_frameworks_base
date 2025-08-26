package dagger.internal;

/* loaded from: classes4.dex */
public final class DelegateFactory implements Provider {
    public Provider delegate;

    public static void setDelegate(Provider provider, Provider provider2) {
        DelegateFactory delegateFactory = (DelegateFactory) provider;
        provider2.getClass();
        if (delegateFactory.delegate != null) {
            throw new IllegalStateException();
        }
        delegateFactory.delegate = provider2;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        Provider provider = this.delegate;
        if (provider != null) {
            return provider.get();
        }
        throw new IllegalStateException();
    }
}
