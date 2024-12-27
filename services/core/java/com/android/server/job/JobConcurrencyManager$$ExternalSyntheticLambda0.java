package com.android.server.job;

import java.util.function.Consumer;

public final /* synthetic */ class JobConcurrencyManager$$ExternalSyntheticLambda0
        implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        JobConcurrencyManager.PackageStats packageStats = (JobConcurrencyManager.PackageStats) obj;
        packageStats.numStagedRegular = 0;
        packageStats.numStagedEj = 0;
    }
}
