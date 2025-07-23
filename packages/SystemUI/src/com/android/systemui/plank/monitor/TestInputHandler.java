package com.android.systemui.plank.monitor;

import android.content.Context;
import android.graphics.Point;
import com.android.systemui.plank.monitor.TestInputMonitor;
import com.samsung.android.view.SemWindowManager;
import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class TestInputHandler implements TestInputMonitor.EventHandler {
    public final Point displaySize;
    public final Context mContext;
    public final List mEventHistory;
    public long mLastEventTime;
    public long mStartEventTime;
    public int mStartX;
    public int mStartY;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public TestInputHandler(Context context) {
        this.mContext = context;
        Point point = new Point();
        this.displaySize = point;
        this.mEventHistory = new ArrayList();
        SemWindowManager.getInstance().getInitialDisplaySize(point);
    }

    public final void addEvent(EventData eventData) {
        synchronized (this.mEventHistory) {
            ((ArrayList) this.mEventHistory).add(eventData);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x0058, code lost:
    
        if (r18.mStartY <= r2.y) goto L19;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0068, code lost:
    
        if (r18.mStartY <= r2.x) goto L25;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onEventHandler(android.view.MotionEvent r19) {
        /*
            Method dump skipped, instructions count: 273
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.plank.monitor.TestInputHandler.onEventHandler(android.view.MotionEvent):void");
    }
}
