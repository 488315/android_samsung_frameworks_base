package com.android.server.credentials;

import android.credentials.CredentialOption;

import java.util.HashSet;
import java.util.function.Function;

public final /* synthetic */ class CredentialManagerService$$ExternalSyntheticLambda2
        implements Function {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        CredentialOption credentialOption = (CredentialOption) obj;
        switch (this.$r8$classId) {
            case 0:
                return new HashSet(
                        credentialOption
                                .getCredentialRetrievalData()
                                .getStringArrayList(
                                        "android.credentials.GetCredentialOption.SUPPORTED_ELEMENT_KEYS"));
            default:
                return credentialOption.getType();
        }
    }
}
