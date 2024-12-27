package com.android.server.credentials;

import android.credentials.CredentialOption;
import java.util.HashSet;
import java.util.function.Function;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class CredentialManagerService$$ExternalSyntheticLambda2 implements Function {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        CredentialOption credentialOption = (CredentialOption) obj;
        switch (this.$r8$classId) {
            case 0:
                return new HashSet(credentialOption.getCredentialRetrievalData().getStringArrayList("android.credentials.GetCredentialOption.SUPPORTED_ELEMENT_KEYS"));
            default:
                return credentialOption.getType();
        }
    }
}
