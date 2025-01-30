package com.android.server.people.data;

import android.net.Uri;
import android.util.ArrayMap;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ScheduledExecutorService;
import java.util.function.Function;
import java.util.function.Predicate;

/* loaded from: classes2.dex */
public class EventStore {
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

    public synchronized void loadFromDisk() {
        for (int i = 0; i < this.mEventsCategoryDirs.size(); i++) {
            ((Map) this.mEventHistoryMaps.get(i)).putAll(EventHistoryImpl.eventHistoriesImplFromDisk((File) this.mEventsCategoryDirs.get(i), this.mScheduledExecutorService));
        }
    }

    public synchronized void saveToDisk() {
        Iterator it = this.mEventHistoryMaps.iterator();
        while (it.hasNext()) {
            Iterator it2 = ((Map) it.next()).values().iterator();
            while (it2.hasNext()) {
                ((EventHistoryImpl) it2.next()).saveToDisk();
            }
        }
    }

    public synchronized EventHistory getEventHistory(int i, String str) {
        return (EventHistory) ((Map) this.mEventHistoryMaps.get(i)).get(str);
    }

    public synchronized EventHistoryImpl getOrCreateEventHistory(final int i, final String str) {
        return (EventHistoryImpl) ((Map) this.mEventHistoryMaps.get(i)).computeIfAbsent(str, new Function() { // from class: com.android.server.people.data.EventStore$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                EventHistoryImpl lambda$getOrCreateEventHistory$0;
                lambda$getOrCreateEventHistory$0 = EventStore.this.lambda$getOrCreateEventHistory$0(i, str, (String) obj);
                return lambda$getOrCreateEventHistory$0;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ EventHistoryImpl lambda$getOrCreateEventHistory$0(int i, String str, String str2) {
        return new EventHistoryImpl(new File((File) this.mEventsCategoryDirs.get(i), Uri.encode(str)), this.mScheduledExecutorService);
    }

    public synchronized void deleteEventHistory(int i, String str) {
        EventHistoryImpl eventHistoryImpl = (EventHistoryImpl) ((Map) this.mEventHistoryMaps.get(i)).remove(str);
        if (eventHistoryImpl != null) {
            eventHistoryImpl.onDestroy();
        }
    }

    public synchronized void deleteEventHistories(int i) {
        Iterator it = ((Map) this.mEventHistoryMaps.get(i)).values().iterator();
        while (it.hasNext()) {
            ((EventHistoryImpl) it.next()).onDestroy();
        }
        ((Map) this.mEventHistoryMaps.get(i)).clear();
    }

    public synchronized void pruneOldEvents() {
        Iterator it = this.mEventHistoryMaps.iterator();
        while (it.hasNext()) {
            Iterator it2 = ((Map) it.next()).values().iterator();
            while (it2.hasNext()) {
                ((EventHistoryImpl) it2.next()).pruneOldEvents();
            }
        }
    }

    public synchronized void pruneOrphanEventHistories(int i, Predicate predicate) {
        Set<String> keySet = ((Map) this.mEventHistoryMaps.get(i)).keySet();
        ArrayList arrayList = new ArrayList();
        for (String str : keySet) {
            if (!predicate.test(str)) {
                arrayList.add(str);
            }
        }
        Map map = (Map) this.mEventHistoryMaps.get(i);
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            EventHistoryImpl eventHistoryImpl = (EventHistoryImpl) map.remove((String) it.next());
            if (eventHistoryImpl != null) {
                eventHistoryImpl.onDestroy();
            }
        }
    }

    public synchronized void onDestroy() {
        Iterator it = this.mEventHistoryMaps.iterator();
        while (it.hasNext()) {
            Iterator it2 = ((Map) it.next()).values().iterator();
            while (it2.hasNext()) {
                ((EventHistoryImpl) it2.next()).onDestroy();
            }
        }
    }
}
