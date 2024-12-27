package com.android.server.people.data;

import android.util.Slog;
import android.util.SparseArray;
import android.util.proto.ProtoInputStream;
import android.util.proto.ProtoOutputStream;

import com.android.internal.util.CollectionUtils;

import com.google.android.collect.Lists;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */
class EventHistoryImpl$EventsProtoDiskReadWriter$$ExternalSyntheticLambda0
        implements AbstractProtoDiskReadWriter.ProtoStreamWriter,
                AbstractProtoDiskReadWriter.ProtoStreamReader {
    public final /* synthetic */ int $r8$classId;

    @Override // com.android.server.people.data.AbstractProtoDiskReadWriter.ProtoStreamReader
    public Object read(ProtoInputStream protoInputStream) {
        switch (this.$r8$classId) {
            case 2:
                SparseArray sparseArray = new SparseArray();
                while (protoInputStream.nextField() != -1) {
                    try {
                        if (protoInputStream.getFieldNumber() == 1) {
                            long start = protoInputStream.start(2246267895809L);
                            EventIndex eventIndex = EventIndex.EMPTY;
                            int i = 0;
                            while (protoInputStream.nextField() != -1) {
                                int fieldNumber = protoInputStream.getFieldNumber();
                                if (fieldNumber == 1) {
                                    i = protoInputStream.readInt(1120986464257L);
                                } else if (fieldNumber != 2) {
                                    Slog.w(
                                            "EventHistoryImpl$EventIndexesProtoDiskReadWriter",
                                            "Could not read undefined field: "
                                                    + protoInputStream.getFieldNumber());
                                } else {
                                    long start2 = protoInputStream.start(1146756268034L);
                                    eventIndex = EventIndex.readFromProto(protoInputStream);
                                    protoInputStream.end(start2);
                                }
                            }
                            sparseArray.append(i, eventIndex);
                            protoInputStream.end(start);
                        }
                    } catch (IOException e) {
                        Slog.e(
                                "EventHistoryImpl$EventIndexesProtoDiskReadWriter",
                                "Failed to read protobuf input stream.",
                                e);
                    }
                }
                return sparseArray;
            default:
                ArrayList newArrayList = Lists.newArrayList();
                while (protoInputStream.nextField() != -1) {
                    try {
                        if (protoInputStream.getFieldNumber() == 1) {
                            long start3 = protoInputStream.start(2246267895809L);
                            Event readFromProto = Event.readFromProto(protoInputStream);
                            protoInputStream.end(start3);
                            newArrayList.add(readFromProto);
                        }
                    } catch (IOException e2) {
                        Slog.e(
                                "EventHistoryImpl$EventsProtoDiskReadWriter",
                                "Failed to read protobuf input stream.",
                                e2);
                    }
                }
                EventList eventList = new EventList();
                Iterator it = newArrayList.iterator();
                while (it.hasNext()) {
                    eventList.add((Event) it.next());
                }
                return eventList;
        }
    }

    @Override // com.android.server.people.data.AbstractProtoDiskReadWriter.ProtoStreamWriter
    public void write(ProtoOutputStream protoOutputStream, Object obj) {
        switch (this.$r8$classId) {
            case 0:
                for (Event event : CollectionUtils.copyOf(((EventList) obj).mEvents)) {
                    long start = protoOutputStream.start(2246267895809L);
                    protoOutputStream.write(1120986464257L, event.mType);
                    protoOutputStream.write(1112396529666L, event.mTimestamp);
                    protoOutputStream.write(1120986464259L, event.mDurationSeconds);
                    protoOutputStream.end(start);
                }
                return;
            default:
                SparseArray sparseArray = (SparseArray) obj;
                for (int i = 0; i < sparseArray.size(); i++) {
                    int keyAt = sparseArray.keyAt(i);
                    EventIndex eventIndex = (EventIndex) sparseArray.valueAt(i);
                    long start2 = protoOutputStream.start(2246267895809L);
                    protoOutputStream.write(1120986464257L, keyAt);
                    long start3 = protoOutputStream.start(1146756268034L);
                    synchronized (eventIndex) {
                        try {
                            for (long j : eventIndex.mEventBitmaps) {
                                protoOutputStream.write(2211908157441L, j);
                            }
                            protoOutputStream.write(1112396529666L, eventIndex.mLastUpdatedTime);
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    protoOutputStream.end(start3);
                    protoOutputStream.end(start2);
                }
                return;
        }
    }
}
