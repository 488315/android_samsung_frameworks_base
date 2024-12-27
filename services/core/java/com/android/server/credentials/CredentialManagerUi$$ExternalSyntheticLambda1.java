package com.android.server.credentials;

import android.credentials.CredentialProviderInfo;
import android.credentials.selection.DisabledProviderData;
import java.util.function.Function;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class CredentialManagerUi$$ExternalSyntheticLambda1 implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return new DisabledProviderData(((CredentialProviderInfo) obj).getComponentName().flattenToString());
    }
}
