package com.android.server.credentials;

import java.util.function.Function;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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
