package com.android.server.uri;

import android.app.StatsManager;
import android.content.Context;
import android.util.SparseArray;
import com.android.internal.util.ConcurrentUtils;
import com.android.internal.util.FrameworkStatsLog;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public final class UriMetricsHelper {
    public static final StatsManager.PullAtomMetadata DAILY_PULL_METADATA = new StatsManager.PullAtomMetadata.Builder().setCoolDownMillis(TimeUnit.DAYS.toMillis(1)).build();
    public final Context mContext;
    public final PersistentUriGrantsProvider mPersistentUriGrantsProvider;

    public interface PersistentUriGrantsProvider {
        ArrayList providePersistentUriGrants();
    }

    public UriMetricsHelper(Context context, PersistentUriGrantsProvider persistentUriGrantsProvider) {
        this.mContext = context;
        this.mPersistentUriGrantsProvider = persistentUriGrantsProvider;
    }

    public void registerPuller() {
        ((StatsManager) this.mContext.getSystemService(StatsManager.class)).setPullAtomCallback(FrameworkStatsLog.PERSISTENT_URI_PERMISSIONS_AMOUNT_PER_PACKAGE, DAILY_PULL_METADATA, ConcurrentUtils.DIRECT_EXECUTOR, new StatsManager.StatsPullAtomCallback() { // from class: com.android.server.uri.UriMetricsHelper$$ExternalSyntheticLambda0
            public final int onPullAtom(int i, List list) {
                int lambda$registerPuller$0;
                lambda$registerPuller$0 = UriMetricsHelper.this.lambda$registerPuller$0(i, list);
                return lambda$registerPuller$0;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int lambda$registerPuller$0(int i, List list) {
        reportPersistentUriPermissionsPerPackage(list);
        return 0;
    }

    public void reportPersistentUriFlushed(int i) {
        FrameworkStatsLog.write(FrameworkStatsLog.PERSISTENT_URI_PERMISSIONS_FLUSHED, i);
    }

    public final void reportPersistentUriPermissionsPerPackage(List list) {
        ArrayList providePersistentUriGrants = this.mPersistentUriGrantsProvider.providePersistentUriGrants();
        SparseArray sparseArray = new SparseArray();
        int size = providePersistentUriGrants.size();
        for (int i = 0; i < size; i++) {
            int i2 = ((UriPermission) providePersistentUriGrants.get(i)).targetUid;
            sparseArray.put(i2, Integer.valueOf(((Integer) sparseArray.get(i2, 0)).intValue() + 1));
        }
        int size2 = sparseArray.size();
        for (int i3 = 0; i3 < size2; i3++) {
            list.add(FrameworkStatsLog.buildStatsEvent(FrameworkStatsLog.PERSISTENT_URI_PERMISSIONS_AMOUNT_PER_PACKAGE, sparseArray.keyAt(i3), ((Integer) sparseArray.valueAt(i3)).intValue()));
        }
    }
}
