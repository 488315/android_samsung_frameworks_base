package com.android.server.credentials;

import android.credentials.CredentialProviderInfo;
import android.credentials.selection.DisabledProviderData;

import java.util.function.Function;

public final /* synthetic */ class CredentialManagerUi$$ExternalSyntheticLambda1
        implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return new DisabledProviderData(
                ((CredentialProviderInfo) obj).getComponentName().flattenToString());
    }
}
