package com.android.server.credentials;

import android.credentials.CredentialOption;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class CredentialManagerService$$ExternalSyntheticLambda1 implements Predicate {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        CredentialOption credentialOption = (CredentialOption) obj;
        switch (this.$r8$classId) {
            case 0:
                if (credentialOption.getAllowedProviders() == null || credentialOption.getAllowedProviders().isEmpty()) {
                }
                break;
            case 1:
                if (credentialOption.getCredentialRetrievalData().getStringArrayList("android.credentials.GetCredentialOption.SUPPORTED_ELEMENT_KEYS") != null) {
                }
                break;
            default:
                if (credentialOption.getCredentialRetrievalData().getStringArrayList("android.credentials.GetCredentialOption.SUPPORTED_ELEMENT_KEYS") == null) {
                }
                break;
        }
        return false;
    }
}
