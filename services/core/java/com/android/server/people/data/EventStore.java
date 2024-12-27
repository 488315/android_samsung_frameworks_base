package com.android.server.people.data;

import android.net.Uri;
import android.util.ArrayMap;
import com.android.server.people.data.EventHistoryImpl;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ScheduledExecutorService;
import java.util.function.Function;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class EventStore {
    public final List mEventHistoryMaps;
    public final List mEventsCategoryDirs;
    public final ScheduledExecutorService mScheduledExecutorService;

    public EventStore(File file, ScheduledExecutorService scheduledExecutorService) {
        ArrayList arrayList = new ArrayList();
        this.mEventHistoryMaps = arrayList;
        ArrayList arrayList2 = new ArrayList();
        this.mEventsCategoryDirs = arrayList2;
        arrayList.add(0, new ArrayMap());
        arrayList.add(1, new ArrayMap());
        arrayList.add(2, new ArrayMap());
        arrayList.add(3, new ArrayMap());
        arrayList.add(4, new ArrayMap());
        File file2 = new File(file, "event");
        arrayList2.add(0, new File(file2, "shortcut"));
        arrayList2.add(1, new File(file2, "locus"));
        arrayList2.add(2, new File(file2, "call"));
        arrayList2.add(3, new File(file2, "sms"));
        arrayList2.add(4, new File(file2, "class"));
        this.mScheduledExecutorService = scheduledExecutorService;
    }

    public final synchronized void deleteEventHistories(int i) {
        try {
            Iterator it = ((Map) ((ArrayList) this.mEventHistoryMaps).get(i)).values().iterator();
            while (it.hasNext()) {
                ((EventHistoryImpl) it.next()).onDestroy();
            }
            ((Map) ((ArrayList) this.mEventHistoryMaps).get(i)).clear();
        } catch (Throwable th) {
            throw th;
        }
    }

    public final synchronized void deleteEventHistory(int i, String str) {
        EventHistoryImpl eventHistoryImpl = (EventHistoryImpl) ((Map) ((ArrayList) this.mEventHistoryMaps).get(i)).remove(str);
        if (eventHistoryImpl != null) {
            eventHistoryImpl.onDestroy();
        }
    }

    public final synchronized EventHistory getEventHistory(int i, String str) {
        return (EventHistory) ((Map) ((ArrayList) this.mEventHistoryMaps).get(i)).get(str);
    }

    public final synchronized EventHistoryImpl getOrCreateEventHistory(final int i, final String str) {
        return (EventHistoryImpl) ((Map) ((ArrayList) this.mEventHistoryMaps).get(i)).computeIfAbsent(str, new Function() { // from class: com.android.server.people.data.EventStore$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                EventStore eventStore = EventStore.this;
                int i2 = i;
                String str2 = str;
                eventStore.getClass();
                return new EventHistoryImpl(new EventHistoryImpl.Injector(), new File((File) ((ArrayList) eventStore.mEventsCategoryDirs).get(i2), Uri.encode(str2)), eventStore.mScheduledExecutorService);
            }
        });
    }

    public final synchronized void pruneOrphanEventHistories(int i, Predicate predicate) {
        try {
            Set<String> keySet = ((Map) ((ArrayList) this.mEventHistoryMaps).get(i)).keySet();
            ArrayList arrayList = new ArrayList();
            for (String str : keySet) {
                if (!predicate.test(str)) {
                    arrayList.add(str);
                }
            }
            Map map = (Map) ((ArrayList) this.mEventHistoryMaps).get(i);
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                EventHistoryImpl eventHistoryImpl = (EventHistoryImpl) map.remove((String) it.next());
                if (eventHistoryImpl != null) {
                    eventHistoryImpl.onDestroy();
                }
            }
        } catch (Throwable th) {
            throw th;
        }
    }
}
