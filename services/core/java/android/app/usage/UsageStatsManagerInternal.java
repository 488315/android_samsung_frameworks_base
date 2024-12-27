package android.app.usage;

import android.os.PersistableBundle;
import android.os.UserHandle;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class UsageStatsManagerInternal {

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AppUsageLimitData {
        public final long mTotalUsageLimit;
        public final long mUsageRemaining;

        public AppUsageLimitData(long j, long j2) {
            this.mTotalUsageLimit = j;
            this.mUsageRemaining = j2;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface UsageEventListener {
        void onUsageEvent(int i, UsageEvents.Event event);
    }

    public abstract void applyRestoredPayload(int i, String str, byte[] bArr);

    public abstract int getAppStandbyBucket(int i, String str, long j);

    public abstract byte[] getBackupPayload(int i, String str);

    public abstract void registerListener(UsageEventListener usageEventListener);

    public abstract void reportEvent(int i, int i2, String str);

    public abstract void reportNotificationPosted(String str, UserHandle userHandle, long j);

    public abstract void reportNotificationUpdated(String str, UserHandle userHandle, long j);

    public abstract void reportUserInteractionEvent(
            String str, int i, PersistableBundle persistableBundle);
}
