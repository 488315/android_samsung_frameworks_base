package com.android.server.credentials;

import android.credentials.CredentialOption;
import android.service.credentials.BeginGetCredentialOption;
import android.service.credentials.BeginGetCredentialRequest;

import java.util.Map;
import java.util.function.Consumer;

public final /* synthetic */ class ProviderGetSession$$ExternalSyntheticLambda0
        implements Consumer {
    public final /* synthetic */ BeginGetCredentialRequest.Builder f$0;
    public final /* synthetic */ Map f$1;

    public /* synthetic */ ProviderGetSession$$ExternalSyntheticLambda0(
            BeginGetCredentialRequest.Builder builder, Map map) {
        this.f$0 = builder;
        this.f$1 = map;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        BeginGetCredentialRequest.Builder builder = this.f$0;
        Map map = this.f$1;
        CredentialOption credentialOption = (CredentialOption) obj;
        String generateUniqueId = ProviderSession.generateUniqueId();
        builder.addBeginGetCredentialOption(
                new BeginGetCredentialOption(
                        generateUniqueId,
                        credentialOption.getType(),
                        credentialOption.getCandidateQueryData()));
        map.put(generateUniqueId, credentialOption);
    }
}
