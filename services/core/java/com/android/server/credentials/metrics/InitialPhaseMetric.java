package com.android.server.credentials.metrics;

import com.android.server.audio.AudioService$$ExternalSyntheticLambda1;

import java.util.LinkedHashMap;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class InitialPhaseMetric {
    public final int mSessionIdCaller;
    public int mApiName = ApiName.UNKNOWN.getMetricCode();
    public int mCallerUid = -1;
    public long mCredentialServiceStartedTimeNanoseconds = -1;
    public boolean mOriginSpecified = false;
    public Map mRequestCounts = new LinkedHashMap();
    public int mAutofillSessionId = -1;
    public int mAutofillRequestId = -1;

    public InitialPhaseMetric(int i) {
        this.mSessionIdCaller = i;
    }

    public final int[] getUniqueRequestCounts() {
        return this.mRequestCounts.values().stream()
                .mapToInt(new AudioService$$ExternalSyntheticLambda1(2))
                .toArray();
    }

    public final String[] getUniqueRequestStrings() {
        String[] strArr = new String[this.mRequestCounts.keySet().size()];
        this.mRequestCounts.keySet().toArray(strArr);
        return strArr;
    }
}
