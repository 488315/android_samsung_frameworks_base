package com.android.server.people.data;

import android.net.Uri;
import android.os.FileUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.SparseArray;

import com.android.internal.util.CollectionUtils;
import com.android.server.backup.BackupManagerConstants;
import com.android.server.cpu.CpuInfoReader$$ExternalSyntheticLambda0;

import com.samsung.android.knox.custom.LauncherConfigurationInternal;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ScheduledExecutorService;

public final class EventHistoryImpl implements EventHistory {
    public final EventsProtoDiskReadWriter mEventIndexesProtoDiskReadWriter;
    public final EventsProtoDiskReadWriter mEventsProtoDiskReadWriter;
    public final Injector mInjector;
    public long mLastPruneTime;
    public final File mRootDir;
    public final ScheduledExecutorService mScheduledExecutorService;
    public final SparseArray mEventIndexArray = new SparseArray();
    public final EventList mRecentEvents = new EventList();

    public final class EventsProtoDiskReadWriter extends AbstractProtoDiskReadWriter {
        public final /* synthetic */ int $r8$classId;

        public /* synthetic */ EventsProtoDiskReadWriter(
                File file, ScheduledExecutorService scheduledExecutorService, int i) {
            super(file, scheduledExecutorService);
            this.$r8$classId = i;
        }

        @Override // com.android.server.people.data.AbstractProtoDiskReadWriter
        public final AbstractProtoDiskReadWriter.ProtoStreamReader protoStreamReader() {
            switch (this.$r8$classId) {
                case 0:
                    return new EventHistoryImpl$EventsProtoDiskReadWriter$$ExternalSyntheticLambda0(
                            3);
                default:
                    return new EventHistoryImpl$EventsProtoDiskReadWriter$$ExternalSyntheticLambda0(
                            2);
            }
        }

        @Override // com.android.server.people.data.AbstractProtoDiskReadWriter
        public final AbstractProtoDiskReadWriter.ProtoStreamWriter protoStreamWriter() {
            switch (this.$r8$classId) {
                case 0:
                    return new EventHistoryImpl$EventsProtoDiskReadWriter$$ExternalSyntheticLambda0(
                            0);
                default:
                    return new EventHistoryImpl$EventsProtoDiskReadWriter$$ExternalSyntheticLambda0(
                            1);
            }
        }
    }

    class Injector {}

    public EventHistoryImpl(
            Injector injector, File file, ScheduledExecutorService scheduledExecutorService) {
        this.mInjector = injector;
        this.mScheduledExecutorService = scheduledExecutorService;
        injector.getClass();
        this.mLastPruneTime = System.currentTimeMillis();
        this.mRootDir = file;
        File file2 = new File(file, "events");
        EventsProtoDiskReadWriter eventsProtoDiskReadWriter =
                new EventsProtoDiskReadWriter(file2, scheduledExecutorService, 0);
        file2.mkdirs();
        this.mEventsProtoDiskReadWriter = eventsProtoDiskReadWriter;
        File file3 = new File(file, "indexes");
        EventsProtoDiskReadWriter eventsProtoDiskReadWriter2 =
                new EventsProtoDiskReadWriter(file3, scheduledExecutorService, 1);
        file3.mkdirs();
        this.mEventIndexesProtoDiskReadWriter = eventsProtoDiskReadWriter2;
    }

    public static Map eventHistoriesImplFromDisk(
            Injector injector, File file, ScheduledExecutorService scheduledExecutorService) {
        ArrayMap arrayMap = new ArrayMap();
        File[] listFiles = file.listFiles(new CpuInfoReader$$ExternalSyntheticLambda0(2));
        if (listFiles == null) {
            return arrayMap;
        }
        for (File file2 : listFiles) {
            File[] listFiles2 = file2.listFiles(new EventHistoryImpl$$ExternalSyntheticLambda1());
            if (listFiles2 != null && listFiles2.length == 2) {
                EventHistoryImpl eventHistoryImpl =
                        new EventHistoryImpl(injector, file2, scheduledExecutorService);
                eventHistoryImpl.loadFromDisk();
                arrayMap.put(Uri.decode(file2.getName()), eventHistoryImpl);
            }
        }
        return arrayMap;
    }

    public final synchronized void addEvent(Event event) {
        pruneOldEvents();
        addEventInMemory(event);
        this.mEventsProtoDiskReadWriter.scheduleSave("recent", this.mRecentEvents);
        this.mEventIndexesProtoDiskReadWriter.scheduleSave(
                LauncherConfigurationInternal.KEY_INDEX_INT, this.mEventIndexArray);
    }

    public final synchronized void addEventInMemory(Event event) {
        try {
            EventIndex eventIndex = (EventIndex) this.mEventIndexArray.get(event.mType);
            if (eventIndex == null) {
                this.mInjector.getClass();
                eventIndex = new EventIndex();
                this.mEventIndexArray.put(event.mType, eventIndex);
            }
            eventIndex.addEvent(event.mTimestamp);
            this.mRecentEvents.add(event);
        } catch (Throwable th) {
            throw th;
        }
    }

    @Override // com.android.server.people.data.EventHistory
    public final synchronized EventIndex getEventIndex(int i) {
        EventIndex eventIndex;
        try {
            EventIndex eventIndex2 = (EventIndex) this.mEventIndexArray.get(i);
            if (eventIndex2 != null) {
                eventIndex = new EventIndex(eventIndex2);
            } else {
                this.mInjector.getClass();
                eventIndex = new EventIndex();
            }
        } catch (Throwable th) {
            throw th;
        }
        return eventIndex;
    }

    @Override // com.android.server.people.data.EventHistory
    public final synchronized EventIndex getEventIndex(Set set) {
        EventIndex eventIndex;
        this.mInjector.getClass();
        eventIndex = new EventIndex();
        Iterator it = ((ArraySet) set).iterator();
        while (it.hasNext()) {
            EventIndex eventIndex2 =
                    (EventIndex) this.mEventIndexArray.get(((Integer) it.next()).intValue());
            if (eventIndex2 != null) {
                eventIndex = EventIndex.combine(eventIndex, eventIndex2);
            }
        }
        return eventIndex;
    }

    public synchronized void loadFromDisk() {
        this.mScheduledExecutorService.execute(
                new Runnable() { // from class:
                                 // com.android.server.people.data.EventHistoryImpl$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        EventHistoryImpl eventHistoryImpl = EventHistoryImpl.this;
                        synchronized (eventHistoryImpl) {
                            try {
                                EventList eventList =
                                        (EventList)
                                                eventHistoryImpl.mEventsProtoDiskReadWriter.read(
                                                        "recent");
                                if (eventList != null) {
                                    eventHistoryImpl.mInjector.getClass();
                                    eventList.removeOldEvents(
                                            System.currentTimeMillis()
                                                    - BackupManagerConstants
                                                            .DEFAULT_KEY_VALUE_BACKUP_INTERVAL_MILLISECONDS);
                                    EventList eventList2 = eventHistoryImpl.mRecentEvents;
                                    List copyOf = CollectionUtils.copyOf(eventList.mEvents);
                                    eventList2.getClass();
                                    Iterator it = copyOf.iterator();
                                    while (it.hasNext()) {
                                        eventList2.add((Event) it.next());
                                    }
                                }
                                SparseArray sparseArray =
                                        (SparseArray)
                                                eventHistoryImpl.mEventIndexesProtoDiskReadWriter
                                                        .read(
                                                                LauncherConfigurationInternal
                                                                        .KEY_INDEX_INT);
                                if (sparseArray != null) {
                                    for (int i = 0; i < sparseArray.size(); i++) {
                                        eventHistoryImpl.mEventIndexArray.put(
                                                sparseArray.keyAt(i),
                                                (EventIndex) sparseArray.valueAt(i));
                                    }
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }
                });
    }

    public final synchronized void onDestroy() {
        this.mEventIndexArray.clear();
        ((ArrayList) this.mRecentEvents.mEvents).clear();
        this.mEventsProtoDiskReadWriter.delete("recent");
        this.mEventIndexesProtoDiskReadWriter.delete(LauncherConfigurationInternal.KEY_INDEX_INT);
        FileUtils.deleteContentsAndDir(this.mRootDir);
    }

    public final synchronized void pruneOldEvents() {
        this.mInjector.getClass();
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - this.mLastPruneTime > 900000) {
            this.mRecentEvents.removeOldEvents(
                    currentTimeMillis
                            - BackupManagerConstants
                                    .DEFAULT_KEY_VALUE_BACKUP_INTERVAL_MILLISECONDS);
            this.mLastPruneTime = currentTimeMillis;
        }
    }
}
