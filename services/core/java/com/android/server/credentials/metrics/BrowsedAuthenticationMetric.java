package com.android.server.credentials.metrics;

import com.android.server.credentials.metrics.shared.ResponseCollective;

import java.util.Map;

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
