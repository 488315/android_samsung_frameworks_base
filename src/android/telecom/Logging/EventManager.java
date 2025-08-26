package android.telecom.Logging;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.telecom.Log;
import android.telecom.Logging.EventManager;
import android.telecom.Logging.SessionManager;
import android.text.TextUtils;
import android.util.Pair;
import com.android.internal.util.IndentingPrintWriter;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.IllegalFormatException;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;
import java.util.function.ToLongFunction;

/* loaded from: classes4.dex */
public class EventManager {
    public static final int DEFAULT_EVENTS_TO_CACHE = 10;
    public static final String TAG = "Logging.Events";
    private SessionManager.ISessionIdQueryHandler mSessionIdHandler;
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
    private static final Object mSync = new Object();
    private final Map<Loggable, EventRecord> mCallEventRecordMap = new HashMap();
    private LinkedBlockingQueue<EventRecord> mEventRecords = new LinkedBlockingQueue<>(10);
    private List<EventListener> mEventListeners = new ArrayList();
    private final Map<String, List<TimedEventPair>> requestResponsePairs = new HashMap();

    public interface EventListener {
        void eventRecordAdded(EventRecord eventRecord);
    }

    public interface Loggable {
        String getDescription();

        String getId();
    }

    public static class TimedEventPair {
        private static final long DEFAULT_TIMEOUT = 3000;
        String mName;
        String mRequest;
        String mResponse;
        long mTimeoutMillis;

        public TimedEventPair(String str, String str2, String str3) {
            this.mTimeoutMillis = 3000L;
            this.mRequest = str;
            this.mResponse = str2;
            this.mName = str3;
        }

        public TimedEventPair(String str, String str2, String str3, long j) {
            this.mRequest = str;
            this.mResponse = str2;
            this.mName = str3;
            this.mTimeoutMillis = j;
        }
    }

    public void addRequestResponsePair(TimedEventPair timedEventPair) {
        if (this.requestResponsePairs.containsKey(timedEventPair.mRequest)) {
            this.requestResponsePairs.get(timedEventPair.mRequest).add(timedEventPair);
            return;
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(timedEventPair);
        this.requestResponsePairs.put(timedEventPair.mRequest, arrayList);
    }

    public static class Event {
        public Object data;
        public String eventId;
        public String sessionId;
        public long time;
        public final String timestampString;

        public Event(String str, String str2, long j, Object obj) {
            this.eventId = str;
            this.sessionId = str2;
            this.time = j;
            this.timestampString = ZonedDateTime.ofInstant(Instant.ofEpochMilli(j), ZoneId.systemDefault()).format(EventManager.DATE_TIME_FORMATTER);
            this.data = obj;
        }
    }

    public class EventRecord {
        private final List<Event> mEvents = Collections.synchronizedList(new ArrayList());
        private final Loggable mRecordEntry;

        public class EventTiming extends TimedEvent<String> {
            public String name;
            public long time;

            public EventTiming(EventRecord eventRecord, String str, long j) {
                this.name = str;
                this.time = j;
            }

            @Override // android.telecom.Logging.TimedEvent
            public String getKey() {
                return this.name;
            }

            @Override // android.telecom.Logging.TimedEvent
            public long getTime() {
                return this.time;
            }
        }

        private class PendingResponse {
            String name;
            String requestEventId;
            long requestEventTimeMillis;
            long timeoutMillis;

            public PendingResponse(EventRecord eventRecord, String str, long j, long j2, String str2) {
                this.requestEventId = str;
                this.requestEventTimeMillis = j;
                this.timeoutMillis = j2;
                this.name = str2;
            }
        }

        public EventRecord(Loggable loggable) {
            this.mRecordEntry = loggable;
        }

        public Loggable getRecordEntry() {
            return this.mRecordEntry;
        }

        public void addEvent(String str, String str2, Object obj) {
            this.mEvents.add(new Event(str, str2, System.currentTimeMillis(), obj));
            Log.i("Event", "RecordEntry %s: %s, %s", this.mRecordEntry.getId(), str, obj);
        }

        public List<Event> getEvents() {
            return new ArrayList(this.mEvents);
        }

        public List<EventTiming> extractEventTimings() {
            if (this.mEvents == null) {
                return Collections.EMPTY_LIST;
            }
            ArrayList arrayList = new ArrayList();
            HashMap map = new HashMap();
            synchronized (this.mEvents) {
                for (Event event : this.mEvents) {
                    if (EventManager.this.requestResponsePairs.containsKey(event.eventId)) {
                        for (TimedEventPair timedEventPair : (List) EventManager.this.requestResponsePairs.get(event.eventId)) {
                            map.put(timedEventPair.mResponse, new PendingResponse(this, event.eventId, event.time, timedEventPair.mTimeoutMillis, timedEventPair.mName));
                        }
                    }
                    PendingResponse pendingResponse = (PendingResponse) map.remove(event.eventId);
                    if (pendingResponse != null) {
                        long j = event.time - pendingResponse.requestEventTimeMillis;
                        if (j < pendingResponse.timeoutMillis) {
                            arrayList.add(new EventTiming(this, pendingResponse.name, j));
                        }
                    }
                }
            }
            return arrayList;
        }

        public void dump(IndentingPrintWriter indentingPrintWriter) {
            EventRecord eventRecord;
            indentingPrintWriter.print(this.mRecordEntry.getDescription());
            indentingPrintWriter.increaseIndent();
            for (Event event : getEvents()) {
                indentingPrintWriter.print(event.timestampString);
                indentingPrintWriter.print(" - ");
                indentingPrintWriter.print(event.eventId);
                if (event.data != null) {
                    indentingPrintWriter.print(" (");
                    Object obj = event.data;
                    if ((obj instanceof Loggable) && (eventRecord = (EventRecord) EventManager.this.mCallEventRecordMap.get(obj)) != null) {
                        obj = "RecordEntry " + eventRecord.mRecordEntry.getId();
                    }
                    indentingPrintWriter.print(obj);
                    indentingPrintWriter.print(NavigationBarInflaterView.KEY_CODE_END);
                }
                if (!TextUtils.isEmpty(event.sessionId)) {
                    indentingPrintWriter.print(":");
                    indentingPrintWriter.print(event.sessionId);
                }
                indentingPrintWriter.println();
            }
            indentingPrintWriter.println("Timings (average for this call, milliseconds):");
            indentingPrintWriter.increaseIndent();
            Map mapAverageTimings = EventTiming.averageTimings(extractEventTimings());
            ArrayList<String> arrayList = new ArrayList(mapAverageTimings.keySet());
            Collections.sort(arrayList);
            for (String str : arrayList) {
                indentingPrintWriter.printf("%s: %.2f\n", new Object[]{str, mapAverageTimings.get(str)});
            }
            indentingPrintWriter.decreaseIndent();
            indentingPrintWriter.decreaseIndent();
        }
    }

    public EventManager(SessionManager.ISessionIdQueryHandler iSessionIdQueryHandler) {
        this.mSessionIdHandler = iSessionIdQueryHandler;
    }

    public void event(Loggable loggable, String str, Object obj) {
        String sessionId = this.mSessionIdHandler.getSessionId();
        if (loggable == null) {
            Log.i(TAG, "Non-call EVENT: %s, %s", str, obj);
            return;
        }
        synchronized (this.mEventRecords) {
            if (!this.mCallEventRecordMap.containsKey(loggable)) {
                addEventRecord(new EventRecord(loggable));
            }
            EventRecord eventRecord = this.mCallEventRecordMap.get(loggable);
            if (eventRecord == null) {
                Log.i(TAG, "No EventRecord in CallEventRecordMap", new Object[0]);
            } else {
                eventRecord.addEvent(str, sessionId, obj);
            }
        }
    }

    public void event(Loggable loggable, String str, String str2, Object... objArr) {
        if (objArr != null) {
            try {
                if (objArr.length != 0) {
                    str2 = String.format(Locale.US, str2, objArr);
                }
            } catch (IllegalFormatException e) {
                Log.e(this, e, "IllegalFormatException: formatString='%s' numArgs=%d", str2, Integer.valueOf(objArr.length));
                str2 = str2 + " (An error occurred while formatting the message.)";
            }
        }
        event(loggable, str, str2);
    }

    public void dumpEvents(IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.println("Historical Events:");
        indentingPrintWriter.increaseIndent();
        Iterator<EventRecord> it = this.mEventRecords.iterator();
        while (it.hasNext()) {
            it.next().dump(indentingPrintWriter);
        }
        indentingPrintWriter.decreaseIndent();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void dumpEventsTimeline(IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.println("Historical Events (sorted by time):");
        ArrayList<Pair> arrayList = new ArrayList();
        Iterator<EventRecord> it = this.mEventRecords.iterator();
        while (it.hasNext()) {
            EventRecord next = it.next();
            Iterator<Event> it2 = next.getEvents().iterator();
            while (it2.hasNext()) {
                arrayList.add(new Pair(next.getRecordEntry(), it2.next()));
            }
        }
        arrayList.sort(Comparator.comparingLong(new ToLongFunction() { // from class: android.telecom.Logging.EventManager$$ExternalSyntheticLambda0
            @Override // java.util.function.ToLongFunction
            public final long applyAsLong(Object obj) {
                return ((EventManager.Event) ((Pair) obj).second).time;
            }
        }));
        indentingPrintWriter.increaseIndent();
        for (Pair pair : arrayList) {
            indentingPrintWriter.print(((Event) pair.second).timestampString);
            indentingPrintWriter.print(",");
            indentingPrintWriter.print(((Loggable) pair.first).getId());
            indentingPrintWriter.print(",");
            indentingPrintWriter.print(((Event) pair.second).eventId);
            indentingPrintWriter.print(",");
            indentingPrintWriter.println(((Event) pair.second).data);
        }
        indentingPrintWriter.decreaseIndent();
    }

    public void changeEventCacheSize(int i) {
        LinkedBlockingQueue<EventRecord> linkedBlockingQueue = this.mEventRecords;
        this.mEventRecords = new LinkedBlockingQueue<>(i);
        this.mCallEventRecordMap.clear();
        linkedBlockingQueue.forEach(new Consumer() { // from class: android.telecom.Logging.EventManager$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.lambda$changeEventCacheSize$1((EventManager.EventRecord) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$changeEventCacheSize$1(EventRecord eventRecord) {
        EventRecord eventRecordPoll;
        Loggable recordEntry = eventRecord.getRecordEntry();
        if (this.mEventRecords.remainingCapacity() == 0 && (eventRecordPoll = this.mEventRecords.poll()) != null) {
            this.mCallEventRecordMap.remove(eventRecordPoll.getRecordEntry());
        }
        this.mEventRecords.add(eventRecord);
        this.mCallEventRecordMap.put(recordEntry, eventRecord);
    }

    public void registerEventListener(EventListener eventListener) {
        if (eventListener != null) {
            synchronized (mSync) {
                this.mEventListeners.add(eventListener);
            }
        }
    }

    public LinkedBlockingQueue<EventRecord> getEventRecords() {
        return this.mEventRecords;
    }

    public Map<Loggable, EventRecord> getCallEventRecordMap() {
        return this.mCallEventRecordMap;
    }

    private void addEventRecord(EventRecord eventRecord) {
        EventRecord eventRecordPoll;
        Loggable recordEntry = eventRecord.getRecordEntry();
        if (this.mEventRecords.remainingCapacity() == 0 && (eventRecordPoll = this.mEventRecords.poll()) != null) {
            this.mCallEventRecordMap.remove(eventRecordPoll.getRecordEntry());
        }
        try {
            this.mEventRecords.add(eventRecord);
            this.mCallEventRecordMap.put(recordEntry, eventRecord);
            synchronized (mSync) {
                Iterator<EventListener> it = this.mEventListeners.iterator();
                while (it.hasNext()) {
                    it.next().eventRecordAdded(eventRecord);
                }
            }
        } catch (IllegalStateException unused) {
            Log.i(TAG, "addEventRecord - Can't add new record", new Object[0]);
        }
    }
}
