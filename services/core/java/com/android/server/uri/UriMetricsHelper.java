package com.android.server.uri;

import android.app.StatsManager;
import android.content.Context;

import java.util.concurrent.TimeUnit;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class UriMetricsHelper {
    public static final StatsManager.PullAtomMetadata DAILY_PULL_METADATA =
            new StatsManager.PullAtomMetadata.Builder()
                    .setCoolDownMillis(TimeUnit.DAYS.toMillis(1))
                    .build();
    public final Context mContext;
    public final PersistentUriGrantsProvider mPersistentUriGrantsProvider;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface PersistentUriGrantsProvider {}

    public UriMetricsHelper(Context context, UriGrantsManagerService uriGrantsManagerService) {
        this.mContext = context;
        this.mPersistentUriGrantsProvider = uriGrantsManagerService;
    }
}
