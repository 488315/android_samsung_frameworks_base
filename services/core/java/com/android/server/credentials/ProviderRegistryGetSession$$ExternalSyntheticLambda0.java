package com.android.server.credentials;

import java.util.function.Function;

public final /* synthetic */ class ProviderRegistryGetSession$$ExternalSyntheticLambda0
        implements Function {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        CredentialDescriptionRegistry.FilterResult filterResult =
                (CredentialDescriptionRegistry.FilterResult) obj;
        switch (this.$r8$classId) {
        }
        return filterResult.mCredentialEntries.stream();
    }
}
