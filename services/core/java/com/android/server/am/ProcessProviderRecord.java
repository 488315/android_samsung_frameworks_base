package com.android.server.am;

import android.util.ArrayMap;
import java.util.ArrayList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class ProcessProviderRecord {
    public final ProcessRecord mApp;
    public final ActivityManagerService mService;
    public long mLastProviderTime = Long.MIN_VALUE;
    public final ArrayMap mPubProviders = new ArrayMap();
    public final ArrayList mConProviders = new ArrayList();

    public ProcessProviderRecord(ProcessRecord processRecord) {
        this.mApp = processRecord;
        this.mService = processRecord.mService;
    }

    public final boolean hasProvider(String str) {
        return this.mPubProviders.containsKey(str);
    }
}
