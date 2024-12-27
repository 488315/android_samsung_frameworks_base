package com.android.server.credentials;

import android.credentials.CredentialOption;

import java.util.function.Predicate;

public final /* synthetic */ class CredentialManagerService$$ExternalSyntheticLambda1
        implements Predicate {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        CredentialOption credentialOption = (CredentialOption) obj;
        switch (this.$r8$classId) {
            case 0:
                if (credentialOption.getAllowedProviders() == null
                        || credentialOption.getAllowedProviders().isEmpty()) {}
                break;
            case 1:
                if (credentialOption
                                .getCredentialRetrievalData()
                                .getStringArrayList(
                                        "android.credentials.GetCredentialOption.SUPPORTED_ELEMENT_KEYS")
                        != null) {}
                break;
            default:
                if (credentialOption
                                .getCredentialRetrievalData()
                                .getStringArrayList(
                                        "android.credentials.GetCredentialOption.SUPPORTED_ELEMENT_KEYS")
                        == null) {}
                break;
        }
        return false;
    }
}
