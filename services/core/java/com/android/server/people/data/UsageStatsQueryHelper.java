package com.android.server.people.data;

import android.app.usage.UsageEvents;
import android.app.usage.UsageStatsManagerInternal;
import android.content.ComponentName;
import android.content.LocusId;
import android.util.ArrayMap;
import com.android.server.LocalServices;
import com.android.server.people.data.DataManager;
import com.android.server.people.data.Event;
import java.util.Map;
import java.util.function.Function;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class UsageStatsQueryHelper {
    public final DataManager.UsageStatsQueryRunnable mEventListener;
    public long mLastEventTimestamp;
    public final Function mPackageDataGetter;
    public final int mUserId;
    public final Map mConvoStartEvents = new ArrayMap();
    public final UsageStatsManagerInternal mUsageStatsManagerInternal = (UsageStatsManagerInternal) LocalServices.getService(UsageStatsManagerInternal.class);

    public UsageStatsQueryHelper(int i, DataManager$UsageStatsQueryRunnable$$ExternalSyntheticLambda0 dataManager$UsageStatsQueryRunnable$$ExternalSyntheticLambda0, DataManager.UsageStatsQueryRunnable usageStatsQueryRunnable) {
        this.mUserId = i;
        this.mPackageDataGetter = dataManager$UsageStatsQueryRunnable$$ExternalSyntheticLambda0;
        this.mEventListener = usageStatsQueryRunnable;
    }

    public final void onInAppConversationEnded(PackageData packageData, UsageEvents.Event event) {
        UsageEvents.Event event2 = (UsageEvents.Event) ((ArrayMap) this.mConvoStartEvents).remove(new ComponentName(event.getPackageName(), event.getClassName()));
        if (event2 == null || event2.getTimeStamp() >= event.getTimeStamp()) {
            return;
        }
        long timeStamp = event.getTimeStamp() - event2.getTimeStamp();
        Event.Builder builder = new Event.Builder(event2.getTimeStamp(), 13);
        builder.mDurationSeconds = (int) (timeStamp / 1000);
        Event event3 = new Event(builder);
        LocusId locusId = new LocusId(event2.getLocusId());
        ConversationInfo conversationByLocusId = packageData.mConversationStore.getConversationByLocusId(locusId);
        if (conversationByLocusId == null) {
            return;
        }
        packageData.mEventStore.getOrCreateEventHistory(1, locusId.getId()).addEvent(event3);
        this.mEventListener.onEvent(packageData, conversationByLocusId, event3);
    }
}
