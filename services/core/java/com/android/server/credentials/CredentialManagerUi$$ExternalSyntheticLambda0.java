package com.android.server.credentials;

import android.credentials.CredentialProviderInfo;

import java.util.function.Predicate;

public final /* synthetic */ class CredentialManagerUi$$ExternalSyntheticLambda0
        implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return !((CredentialProviderInfo) obj).isEnabled();
    }
}
