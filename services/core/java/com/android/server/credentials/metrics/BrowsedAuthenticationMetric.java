package com.android.server.credentials.metrics;

import com.android.server.credentials.metrics.shared.ResponseCollective;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class BrowsedAuthenticationMetric {
    public final int mSessionIdProvider;
    public int mProviderUid = -1;
    public ResponseCollective mAuthEntryCollective = new ResponseCollective(Map.of(), Map.of());
    public boolean mHasException = false;
    public final String mFrameworkException = "";
    public int mProviderStatus = -1;
    public boolean mAuthReturned = false;

    public BrowsedAuthenticationMetric(int i) {
        this.mSessionIdProvider = i;
    }
}
