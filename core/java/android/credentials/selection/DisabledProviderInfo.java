package android.credentials.selection;

import android.annotation.SystemApi;

import com.android.internal.util.Preconditions;

@SystemApi
/* loaded from: classes.dex */
public final class DisabledProviderInfo {
    private final String mProviderName;

    public DisabledProviderInfo(String providerName) {
        this.mProviderName = (String) Preconditions.checkStringNotEmpty(providerName);
    }

    public String getProviderName() {
        return this.mProviderName;
    }
}
