package android.metrics;

import android.annotation.SystemApi;
import android.util.EventLog;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.nano.MetricsProto;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

@SystemApi
/* loaded from: classes3.dex */
public class MetricsReader {
    private Queue<LogMaker> mPendingQueue = new LinkedList();
    private Queue<LogMaker> mSeenQueue = new LinkedList();
    private int[] LOGTAGS = {524292};
    private LogReader mReader = new LogReader();
    private int mCheckpointTag = -1;

    public void setLogReader(LogReader logReader) {
        this.mReader = logReader;
    }

    public void read(long j) {
        Object[] objArr;
        ArrayList arrayList = new ArrayList();
        try {
            this.mReader.readEvents(this.LOGTAGS, j, arrayList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.mPendingQueue.clear();
        this.mSeenQueue.clear();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            Event event = (Event) it.next();
            long timeMillis = event.getTimeMillis();
            Object data = event.getData();
            if (data instanceof Object[]) {
                objArr = (Object[]) data;
            } else {
                objArr = new Object[]{data};
            }
            LogMaker processId = new LogMaker(objArr).setTimestamp(timeMillis).setUid(event.getUid()).setProcessId(event.getProcessId());
            if (processId.getCategory() == 920) {
                if (processId.getSubtype() == this.mCheckpointTag) {
                    this.mPendingQueue.clear();
                }
            } else {
                this.mPendingQueue.offer(processId);
            }
        }
    }

    public void checkpoint() {
        int currentTimeMillis = (int) (System.currentTimeMillis() % 2147483647L);
        this.mCheckpointTag = currentTimeMillis;
        this.mReader.writeCheckpoint(currentTimeMillis);
        this.mPendingQueue.clear();
        this.mSeenQueue.clear();
    }

    public void reset() {
        this.mSeenQueue.addAll(this.mPendingQueue);
        this.mPendingQueue.clear();
        this.mCheckpointTag = -1;
        Queue<LogMaker> queue = this.mPendingQueue;
        this.mPendingQueue = this.mSeenQueue;
        this.mSeenQueue = queue;
    }

    public boolean hasNext() {
        return !this.mPendingQueue.isEmpty();
    }

    public LogMaker next() {
        LogMaker poll = this.mPendingQueue.poll();
        if (poll != null) {
            this.mSeenQueue.offer(poll);
        }
        return poll;
    }

    public static class Event {
        Object mData;
        int mPid;
        long mTimeMillis;
        int mUid;

        public Event(long j, int i, int i2, Object obj) {
            this.mTimeMillis = j;
            this.mPid = i;
            this.mUid = i2;
            this.mData = obj;
        }

        Event(EventLog.Event event) {
            this.mTimeMillis = TimeUnit.MILLISECONDS.convert(event.getTimeNanos(), TimeUnit.NANOSECONDS);
            this.mPid = event.getProcessId();
            this.mUid = event.getUid();
            this.mData = event.getData();
        }

        public long getTimeMillis() {
            return this.mTimeMillis;
        }

        public int getProcessId() {
            return this.mPid;
        }

        public int getUid() {
            return this.mUid;
        }

        public Object getData() {
            return this.mData;
        }

        public void setData(Object obj) {
            this.mData = obj;
        }
    }

    public static class LogReader {
        public void readEvents(int[] iArr, long j, Collection<Event> collection) throws IOException {
            ArrayList arrayList = new ArrayList();
            EventLog.readEventsOnWrapping(iArr, TimeUnit.NANOSECONDS.convert(j, TimeUnit.MILLISECONDS), arrayList);
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                collection.add(new Event((EventLog.Event) it.next()));
            }
        }

        public void writeCheckpoint(int i) {
            new MetricsLogger().action(MetricsProto.MetricsEvent.METRICS_CHECKPOINT, i);
        }
    }
}
