package android.credentials.selection;

import android.annotation.SystemApi;
import com.android.internal.util.Preconditions;

@SystemApi
/* loaded from: classes.dex */
public final class DisabledProviderInfo {
    private final String mProviderName;

    public DisabledProviderInfo(String str) {
        this.mProviderName = (String) Preconditions.checkStringNotEmpty(str);
    }

    public String getProviderName() {
        return this.mProviderName;
    }
}
