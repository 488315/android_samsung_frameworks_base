package com.android.server.credentials;

import android.credentials.CredentialOption;
import android.service.credentials.BeginGetCredentialOption;
import android.service.credentials.BeginGetCredentialRequest;
import java.util.Map;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class ProviderGetSession$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ BeginGetCredentialRequest.Builder f$0;
    public final /* synthetic */ Map f$1;

    public /* synthetic */ ProviderGetSession$$ExternalSyntheticLambda0(BeginGetCredentialRequest.Builder builder, Map map) {
        this.f$0 = builder;
        this.f$1 = map;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        BeginGetCredentialRequest.Builder builder = this.f$0;
        Map map = this.f$1;
        CredentialOption credentialOption = (CredentialOption) obj;
        String generateUniqueId = ProviderSession.generateUniqueId();
        builder.addBeginGetCredentialOption(new BeginGetCredentialOption(generateUniqueId, credentialOption.getType(), credentialOption.getCandidateQueryData()));
        map.put(generateUniqueId, credentialOption);
    }
}
