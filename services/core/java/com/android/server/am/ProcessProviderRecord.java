package com.android.server.am;

import android.util.ArrayMap;

import java.util.ArrayList;

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
