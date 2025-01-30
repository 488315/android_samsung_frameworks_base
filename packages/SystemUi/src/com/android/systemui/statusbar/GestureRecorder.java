package com.android.systemui.statusbar;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class GestureRecorder {
    public final String mLogfile;
    public int mLastSaveLen = -1;
    public final HandlerC25071 mHandler = new Handler() { // from class: com.android.systemui.statusbar.GestureRecorder.1
        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            if (message.what == 6351) {
                GestureRecorder gestureRecorder = GestureRecorder.this;
                synchronized (gestureRecorder.mGestures) {
                    try {
                        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(gestureRecorder.mLogfile, true));
                        bufferedWriter.append((CharSequence) (gestureRecorder.toJsonLocked() + "\n"));
                        bufferedWriter.close();
                        gestureRecorder.mGestures.clear();
                        Gesture gesture = gestureRecorder.mCurrentGesture;
                        if (gesture != null) {
                            gestureRecorder.mGestures.add(gesture);
                        }
                        String.format("Wrote %d complete gestures to %s", Integer.valueOf(gestureRecorder.mLastSaveLen), gestureRecorder.mLogfile);
                    } catch (IOException e) {
                        Log.e("GestureRecorder", String.format("Couldn't write gestures to %s", gestureRecorder.mLogfile), e);
                        gestureRecorder.mLastSaveLen = -1;
                    }
                }
            }
        }
    };
    public final LinkedList mGestures = new LinkedList();
    public Gesture mCurrentGesture = null;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Gesture {
        public final LinkedList mRecords = new LinkedList();
        public final HashSet mTags = new HashSet();

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public abstract class Record {
            public long time;

            public Record(Gesture gesture) {
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class TagRecord extends Record {
            public final String info;
            public final String tag;

            public TagRecord(Gesture gesture, long j, String str, String str2) {
                super(gesture);
                this.time = j;
                this.tag = str;
                this.info = str2;
            }
        }

        public Gesture(GestureRecorder gestureRecorder) {
        }
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.systemui.statusbar.GestureRecorder$1] */
    public GestureRecorder(String str) {
        this.mLogfile = str;
    }

    public final String toJsonLocked() {
        StringBuilder sb = new StringBuilder("[");
        Iterator it = this.mGestures.iterator();
        while (it.hasNext()) {
            ((Gesture) it.next()).getClass();
        }
        this.mLastSaveLen = 0;
        sb.append("]");
        return sb.toString();
    }
}
