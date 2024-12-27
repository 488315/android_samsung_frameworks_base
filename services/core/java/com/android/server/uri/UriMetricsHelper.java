package com.android.server.uri;

import android.app.StatsManager;
import android.content.Context;

import java.util.concurrent.TimeUnit;

public final class UriMetricsHelper {
    public static final StatsManager.PullAtomMetadata DAILY_PULL_METADATA =
            new StatsManager.PullAtomMetadata.Builder()
                    .setCoolDownMillis(TimeUnit.DAYS.toMillis(1))
                    .build();
    public final Context mContext;
    public final PersistentUriGrantsProvider mPersistentUriGrantsProvider;

    public interface PersistentUriGrantsProvider {}

    public UriMetricsHelper(Context context, UriGrantsManagerService uriGrantsManagerService) {
        this.mContext = context;
        this.mPersistentUriGrantsProvider = uriGrantsManagerService;
    }
}
