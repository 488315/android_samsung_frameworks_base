package com.android.server.people.data;

import java.io.File;
import java.util.concurrent.ScheduledExecutorService;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class PackageData {
    public final ConversationStore mConversationStore;
    public final EventStore mEventStore;
    public final Predicate mIsDefaultDialerPredicate;
    public final Predicate mIsDefaultSmsAppPredicate;
    public final File mPackageDataDir;
    public final String mPackageName;
    public final int mUserId;

    public PackageData(
            String str,
            int i,
            Predicate predicate,
            Predicate predicate2,
            ScheduledExecutorService scheduledExecutorService,
            File file) {
        this.mPackageName = str;
        this.mUserId = i;
        File file2 = new File(file, str);
        this.mPackageDataDir = file2;
        file2.mkdirs();
        this.mConversationStore = new ConversationStore(file2, scheduledExecutorService);
        this.mEventStore = new EventStore(file2, scheduledExecutorService);
        this.mIsDefaultDialerPredicate = predicate;
        this.mIsDefaultSmsAppPredicate = predicate2;
    }
}
