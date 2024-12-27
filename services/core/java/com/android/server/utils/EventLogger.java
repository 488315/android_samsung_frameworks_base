package com.android.server.utils;

import android.util.Log;
import android.util.Slog;

import com.android.internal.util.dump.DualDumpOutputStream;
import com.android.server.usb.DualOutputStreamDumpSink;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class EventLogger {
    public final ArrayDeque mEvents;
    public final int mMemSize;
    public final String mTag;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class Event {
        public static final SimpleDateFormat sFormat =
                new SimpleDateFormat("MM-dd HH:mm:ss:SSS", Locale.US);
        public final long mTimestamp = System.currentTimeMillis();

        public abstract String eventToString();

        public Event printLog(int i, String str) {
            if (i == 0) {
                Log.i(str, eventToString());
            } else if (i == 1) {
                Log.e(str, eventToString());
            } else if (i != 2) {
                Log.v(str, eventToString());
            } else {
                Log.w(str, eventToString());
            }
            return this;
        }

        public final void printSlog(int i, String str) {
            if (i == 0) {
                Slog.i(str, ((StringEvent) this).mDescription);
                return;
            }
            if (i == 1) {
                Slog.e(str, ((StringEvent) this).mDescription);
            } else if (i != 2) {
                Slog.v(str, ((StringEvent) this).mDescription);
            } else {
                Slog.w(str, ((StringEvent) this).mDescription);
            }
        }

        public final String toString() {
            return sFormat.format(new Date(this.mTimestamp)) + " " + eventToString();
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class StringEvent extends Event {
        public final String mDescription;

        public StringEvent(String str) {
            this.mDescription = str;
        }

        @Override // com.android.server.utils.EventLogger.Event
        public final String eventToString() {
            return this.mDescription;
        }
    }

    public EventLogger(int i, String str) {
        this.mEvents = new ArrayDeque(i);
        this.mMemSize = i;
        this.mTag = str;
    }

    public final synchronized void dump(DualOutputStreamDumpSink dualOutputStreamDumpSink) {
        String str = this.mTag;
        ArrayList arrayList = new ArrayList(this.mEvents);
        DualDumpOutputStream dualDumpOutputStream = dualOutputStreamDumpSink.mDumpOutputStream;
        long j = dualOutputStreamDumpSink.mId;
        dualDumpOutputStream.write("USB Event Log", j, str);
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            dualOutputStreamDumpSink.mDumpOutputStream.write(
                    "USB Event", j, ((Event) it.next()).toString());
        }
    }

    public final synchronized void dump(PrintWriter printWriter) {
        dump(printWriter, "");
    }

    public final synchronized void dump(PrintWriter printWriter, String str) {
        String str2 = "Events log: ";
        String str3 = this.mTag;
        if (str3 != null) {
            str2 = "Events log: ".concat(str3);
        }
        printWriter.println(str2);
        Iterator it = this.mEvents.iterator();
        while (it.hasNext()) {
            printWriter.println(str + ((Event) it.next()).toString());
        }
    }

    public final synchronized void enqueue(Event event) {
        try {
            if (this.mEvents.size() >= this.mMemSize) {
                this.mEvents.removeFirst();
            }
            this.mEvents.addLast(event);
        } catch (Throwable th) {
            throw th;
        }
    }

    public final synchronized void enqueueAndLog(int i, String str, String str2) {
        StringEvent stringEvent = new StringEvent(str);
        stringEvent.printLog(i, str2);
        enqueue(stringEvent);
    }
}
